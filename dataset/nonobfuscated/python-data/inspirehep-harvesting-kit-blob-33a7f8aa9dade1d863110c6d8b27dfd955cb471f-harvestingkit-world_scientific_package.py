


















from __future__ import print_function

import os
import sys
import datetime

from xml.dom.minidom import parse

from harvestingkit.minidom_utils import (get_value_in_tag,
                                         xml_to_text)
from harvestingkit.utils import (
    collapse_initials,
    fix_title_capitalization,
    convert_html_subscripts_to_latex,
    safe_title
)
from harvestingkit.bibrecord import (
    record_add_field,
    create_record,
    record_xml_output,
)
from harvestingkit.jats_package import JatsPackage


class DateNotFoundException(Exception):

    



class WorldScientific(JatsPackage):

    


    def __init__(self, journal_mappings={}):
        

        self.url_prefix = "http://www.worldscientific.com/doi/pdf"
        super(WorldScientific, self).__init__(journal_mappings)

    def _get_date(self):
        def _extract_date(date):
            year = get_value_in_tag(date, 'year')
            month = get_value_in_tag(date, 'month').zfill(2)
            month = month if month != '00' else '01'
            day = get_value_in_tag(date, 'day').zfill(2)
            day = day if day != '00' else '01'
            return '%s-%s-%s' % (year, month, day)

        for date in self.document.getElementsByTagName('date'):
            if date.getAttribute('date-type') == 'published':
                return _extract_date(date)
        for date in self.document.getElementsByTagName('pub-date'):
            if date.getAttribute('pub-type') == 'ppub':
                return _extract_date(date)
        for date in self.document.getElementsByTagName('pub-date'):
            if date.getAttribute('pub-type') == 'epub':
                return _extract_date(date)
        for date in self.document.getElementsByTagName('pub-date'):
            if not date.getAttribute('pub-type'):
                return _extract_date(date)

        
        raise DateNotFoundException

    def get_date(self, filename):
        

        try:
            self.document = parse(filename)
            return self._get_date()
        except DateNotFoundException:
            print("Date problem found in {0}".format(filename))
            return datetime.datetime.strftime(datetime.datetime.now(),
                                              "%Y-%m-%d")

    def _get_authors(self):
        authors = []
        for contrib in self.document.getElementsByTagName('contrib'):
            if contrib.getAttribute('contrib-type') == 'author':
                surname = get_value_in_tag(contrib, 'surname')
                given_names = get_value_in_tag(contrib, 'given-names')
                given_names = collapse_initials(given_names)
                name = '%s, %s' % (surname, given_names)
                name = safe_title(name)
                affiliations = []
                for aff in contrib.getElementsByTagName('aff'):
                    affiliations.append(xml_to_text(aff))
                emails = []
                for email in contrib.getElementsByTagName('email'):
                    emails.append(xml_to_text(email))
                collaborations = []
                for collaboration in contrib.getElementsByTagName("collab"):
                    collaborations.append(xml_to_text(collaboration))
                authors.append((name, affiliations, emails, collaborations))
        return authors

    def _add_authors(self, rec):
        authors = self._get_authors()
        first_author = True
        collaboration_added = False
        for author in authors:
            subfields = [('a', author[0])]
            if author[1]:
                for aff in author[1]:
                    subfields.append(('v', aff))
            if author[2]:
                for email in author[2]:
                    subfields.append(('m', email))
            if first_author:
                record_add_field(rec, '100', subfields=subfields)
                first_author = False
            else:
                record_add_field(rec, '700', subfields=subfields)
            if author[3] and not collaboration_added:
                collaborations = []
                for collab in author[3]:
                    collab_stripped = collab.replace("for the", "").strip()
                    if collab_stripped not in collaborations:
                        collaborations.append(collab_stripped)
                        record_add_field(rec, '710', subfields=[("g", collab_stripped)])
                collaboration_added = True

    def _get_related_article(self):
        for tag in self.document.getElementsByTagName('related-article'):
            if tag.getAttribute('ext-link-type') == 'doi':
                attributes = tag.attributes.keysNS()
                for attribute in attributes:
                    if attribute[1] == 'href':
                        return tag.getAttributeNS(*attribute)
        return ''

    def get_collection(self, journal):
        

        conference = ''
        for tag in self.document.getElementsByTagName('conference'):
            conference = xml_to_text(tag)
        if conference or journal == "International Journal of Modern Physics: Conference Series":
            return [('a', 'HEP'), ('a', 'ConferencePaper')]
        elif self._get_article_type() == "review-article":
            return [('a', 'HEP'), ('a', 'Review')]
        else:
            return [('a', 'HEP'), ('a', 'Published')]

    def get_record(self, filename, ref_extract_callback=None):
        

        self.document = parse(filename)

        article_type = self._get_article_type()
        if article_type not in ['research-article',
                                'corrected-article',
                                'original-article',
                                'introduction',
                                'letter',
                                'correction',
                                'addendum',
                                'review-article',
                                'rapid-communications']:
            return ""

        rec = create_record()
        title, subtitle, notes = self._get_title()
        subfields = []
        if subtitle:
            subfields.append(('b', subtitle))
        if title:
            title = fix_title_capitalization(title)
            subfields.append(('a', title))
            record_add_field(rec, '245', subfields=subfields)
        for note_id in notes:
            note = self._get_note(note_id)
            if note:
                record_add_field(rec, '500', subfields=[('a', note)])
        keywords = self._get_keywords()
        for keyword in keywords:
            record_add_field(rec, '653', ind1='1', subfields=[('a', keyword),
                                                              ('9', 'author')])
        journal, volume, issue, year, date, doi, page,\
            fpage, lpage = self._get_publication_information()
        if date:
            record_add_field(rec, '260', subfields=[('c', date),
                                                    ('t', 'published')])
        if doi:
            record_add_field(rec, '024', ind1='7', subfields=[('a', doi),
                                                              ('2', 'DOI')])
        abstract = self._get_abstract()
        if abstract:
            abstract = convert_html_subscripts_to_latex(abstract)
            record_add_field(rec, '520', subfields=[('a', abstract),
                                                    ('9', 'World Scientific')])
        license, license_type, license_url = self._get_license()
        subfields = []
        if license:
            subfields.append(('a', license))
        if license_url:
            subfields.append(('u', license_url))
        if subfields:
            record_add_field(rec, '540', subfields=subfields)
        if license_type == 'open-access':
            self._attach_fulltext(rec, doi)
        number_of_pages = self._get_page_count()
        if number_of_pages:
            record_add_field(rec, '300', subfields=[('a', number_of_pages)])
        c_holder, c_year, c_statement = self._get_copyright()
        if c_holder and c_year:
            record_add_field(rec, '542', subfields=[('d', c_holder),
                                                    ('g', c_year),
                                                    ('e', 'Article')])
        elif c_statement:
            record_add_field(rec, '542', subfields=[('f', c_statement),
                                                    ('e', 'Article')])
        subfields = []
        if journal:
            subfields.append(('p', journal))
        if issue:
            subfields.append(('n', issue))
        if volume:
            subfields.append(('v', volume))
        if fpage and lpage:
            subfields.append(('c', '%s-%s' % (fpage,
                                              lpage)))
        elif page:
            subfields.append(('c', page))
        if year:
            subfields.append(('y', year))
        if article_type == 'correction':
            subfields.append(('m', 'Erratum'))
        elif article_type == 'addendum':
            subfields.append(('m', 'Addendum'))
        record_add_field(rec, '773', subfields=subfields)

        collections = self.get_collection(journal)
        for collection in collections:
            record_add_field(rec, '980', subfields=[collection])

        self._add_authors(rec)
        if article_type in ['correction',
                            'addendum']:
            related_article = self._get_related_article()
            if related_article:
                record_add_field(rec, '024', ind1='7', subfields=[('a', related_article),
                                                                  ('2', 'DOI')])
        try:
            return record_xml_output(rec)
        except UnicodeDecodeError:
            message = "Found a bad char in the file for the article " + doi
            sys.stderr.write(message)
            return ""

    def _attach_fulltext(self, rec, doi):
        

        url = os.path.join(self.url_prefix, doi)
        record_add_field(rec, 'FFT',
                         subfields=[('a', url),
                                    ('t', 'INSPIRE-PUBLIC'),
                                    ('d', 'Fulltext')])
if __name__ == '__main__':
    filename = sys.argv[1]
    ws = WorldScientific()
    print(ws.get_record(filename))
