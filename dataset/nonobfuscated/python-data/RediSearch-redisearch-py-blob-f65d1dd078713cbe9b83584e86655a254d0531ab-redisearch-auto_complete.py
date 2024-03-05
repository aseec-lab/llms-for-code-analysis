from redis import Redis, ConnectionPool
from six.moves import xrange

from ._util import to_string

class Suggestion(object):
    

    def __init__(self, string, score=1.0, payload=None):
        self.string = to_string(string)
        self.payload = to_string(payload)
        self.score = score

    def __repr__(self):
        return self.string


class SuggestionParser(object):
    

    def __init__(self, with_scores, with_payloads, ret):
        self.with_scores = with_scores
        self.with_payloads = with_payloads

        if with_scores and with_payloads:
            self.sugsize = 3
            self._scoreidx = 1
            self._payloadidx = 2
        elif with_scores:
            self.sugsize = 2
            self._scoreidx = 1
        elif with_payloads:
            self.sugsize = 2
            self._payloadidx = 1
        else:
            self.sugsize = 1
            self._scoreidx = -1

        self._sugs = ret

    def __iter__(self):
        for i in xrange(0, len(self._sugs), self.sugsize):
            ss = self._sugs[i]
            score = float(self._sugs[i + self._scoreidx]) if self.with_scores else 1.0
            payload = self._sugs[i + self._payloadidx] if self.with_payloads else None
            yield Suggestion(ss, score, payload)


class AutoCompleter(object):
    


    SUGADD_COMMAND = "FT.SUGADD"
    SUGDEL_COMMAND = "FT.SUGDEL"
    SUGLEN_COMMAND = "FT.SUGLEN"
    SUGGET_COMMAND = "FT.SUGGET"

    INCR = 'INCR'
    WITHSCORES = 'WITHSCORES'
    FUZZY = 'FUZZY'
    WITHPAYLOADS = 'WITHPAYLOADS'

    def __init__(self, key, host='localhost', port=6379, conn = None):
        


        self.key = key
        self.redis = conn if conn is not None else Redis(
            connection_pool = ConnectionPool(host=host, port=port))

    def add_suggestions(self,  *suggestions, **kwargs):
        

        pipe = self.redis.pipeline()
        for sug in suggestions:
            args = [AutoCompleter.SUGADD_COMMAND, self.key, sug.string, sug.score]
            if kwargs.get('increment'):
                args.append(AutoCompleter.INCR)
            if sug.payload:
                args.append('PAYLOAD')
                args.append(sug.payload)

            pipe.execute_command(*args)

        return pipe.execute()[-1]



    def len(self):
        

        return self.redis.execute_command(AutoCompleter.SUGLEN_COMMAND, self.key)

    def delete(self, string):
        

        return self.redis.execute_command(AutoCompleter.SUGDEL_COMMAND, self.key, string)

    def get_suggestions(self, prefix, fuzzy = False, num = 10, with_scores = False, with_payloads=False):
        


        args = [AutoCompleter.SUGGET_COMMAND, self.key, prefix, 'MAX', num]
        if fuzzy:
            args.append(AutoCompleter.FUZZY)
        if with_scores:
            args.append(AutoCompleter.WITHSCORES)
        if with_payloads:
            args.append(AutoCompleter.WITHPAYLOADS)

        ret = self.redis.execute_command(*args)
        results = []
        if not ret:
            return results

        parser = SuggestionParser(with_scores, with_payloads, ret)
        return [s for s in parser]