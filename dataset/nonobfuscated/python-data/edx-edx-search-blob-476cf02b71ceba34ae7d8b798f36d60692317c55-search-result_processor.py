

from __future__ import absolute_import
import inspect
from itertools import chain
import json
import logging
import re
import shlex
import textwrap

from django.conf import settings
from django.core.serializers.json import DjangoJSONEncoder

import six
from .utils import _load_class

DESIRED_EXCERPT_LENGTH = 100
ELLIPSIS = '<span class="search-results-ellipsis"></span>'


log = logging.getLogger(__name__)  


class SearchResultProcessor(object):

    


    _results_fields = {}
    _match_phrase = None

    def __init__(self, dictionary, match_phrase):
        self._results_fields = dictionary
        self._match_phrase = match_phrase

    @staticmethod
    def strings_in_dictionary(dictionary):
        

        strings = [value for value in six.itervalues(dictionary) if not isinstance(value, dict)]
        for child_dict in [dv for dv in six.itervalues(dictionary) if isinstance(dv, dict)]:
            strings.extend(SearchResultProcessor.strings_in_dictionary(child_dict))
        return strings

    @staticmethod
    def find_matches(strings, words, length_hoped):
        

        lower_words = [w.lower() for w in words]

        def has_match(string):
            

            lower_string = string.lower()
            for test_word in lower_words:
                if test_word in lower_string:
                    return True
            return False

        shortened_strings = [textwrap.wrap(s) for s in strings]
        short_string_list = list(chain.from_iterable(shortened_strings))
        matches = [ms for ms in short_string_list if has_match(ms)]

        cumulative_len = 0
        break_at = None
        for idx, match in enumerate(matches):
            cumulative_len += len(match)
            if cumulative_len >= length_hoped:
                break_at = idx
                break

        return matches[0:break_at]

    @staticmethod
    def decorate_matches(match_in, match_word):
        

        matches = re.finditer(match_word, match_in, re.IGNORECASE)
        for matched_string in set([match.group() for match in matches]):
            match_in = match_in.replace(
                matched_string,
                getattr(settings, "SEARCH_MATCH_DECORATION", u"<b>{}</b>").format(matched_string)
            )
        return match_in

    
    def should_remove(self, user):  
        

        return False

    def add_properties(self):
        

        for property_name in [p[0] for p in inspect.getmembers(self.__class__) if isinstance(p[1], property)]:
            self._results_fields[property_name] = getattr(self, property_name, None)

    @classmethod
    def process_result(cls, dictionary, match_phrase, user):
        

        result_processor = _load_class(getattr(settings, "SEARCH_RESULT_PROCESSOR", None), cls)
        srp = result_processor(dictionary, match_phrase)
        if srp.should_remove(user):
            return None
        try:
            srp.add_properties()
        
        except Exception as ex:  
            log.exception("error processing properties for %s - %s: will remove from results",
                          json.dumps(dictionary, cls=DjangoJSONEncoder), str(ex))
            return None
        return dictionary

    @property
    def excerpt(self):
        

        if "content" not in self._results_fields:
            return None

        match_phrases = [self._match_phrase]
        if six.PY2:
            separate_phrases = [
                phrase.decode('utf-8')
                for phrase in shlex.split(self._match_phrase.encode('utf-8'))
            ]
        else:
            separate_phrases = [
                phrase
                for phrase in shlex.split(self._match_phrase)
            ]
        if len(separate_phrases) > 1:
            match_phrases.extend(separate_phrases)
        else:
            match_phrases = separate_phrases

        matches = SearchResultProcessor.find_matches(
            SearchResultProcessor.strings_in_dictionary(self._results_fields["content"]),
            match_phrases,
            DESIRED_EXCERPT_LENGTH
        )
        excerpt_text = ELLIPSIS.join(matches)

        for match_word in match_phrases:
            excerpt_text = SearchResultProcessor.decorate_matches(excerpt_text, match_word)

        return excerpt_text
