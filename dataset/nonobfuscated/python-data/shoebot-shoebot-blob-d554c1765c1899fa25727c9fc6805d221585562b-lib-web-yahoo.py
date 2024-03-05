







import urllib
import xml.dom.minidom

from url import URLAccumulator, HTTP403Forbidden
from html import replace_entities
from cache import Cache

def clear_cache():
    Cache("yahoo").clear()



YAHOO_ID         = "Bsx0rSzV34HQ9sXprWCaAWCHCINnLFtRF_4wahO1tiVEPpFSltMdqkM1z6Xubg"



YAHOO_SEARCH     = "search"
YAHOO_IMAGES     = "images"
YAHOO_NEWS       = "news"
YAHOO_SPELLING   = "spelling"



class YahooError(Exception):
    def __str__(self): return str(self.__class__) 
    
class YahooLimitError(YahooError):
    
    def __str__(self): return str(self.__class__) 



def license_key(id=None):
    
    global YAHOO_ID
    if id != None:
        YAHOO_ID = id
    return YAHOO_ID



def format_data(s):
    
    

    
    return s.encode("utf-8")


    
class YahooResult:

    

    
    def __init__(self):
        
        self.title       = None
        self.url         = None
        self.description = None
        self.type        = None
        self.date        = None
        self.width       = None 
        self.height      = None 
        self.source      = None 
        self.language    = None 

    def __repr__(self):
        
        s = format_data(self.url)
        return s




class YahooResults(list):
    
    

    
    def __init__(self, q, data, service=YAHOO_SEARCH):
        
        self.query = q
        self.total = 0
        if data == "": return
        dom = xml.dom.minidom.parseString(data)
        doc = dom.childNodes[0]
        self.total = int(doc.attributes["totalResultsAvailable"].value)
        
        for r in doc.getElementsByTagName('Result'):
            
            item = YahooResult()
            item.title        = self._parse(r, 'Title')
            item.url          = self._parse(r, 'Url')
            item.description  = self._parse(r, 'Summary')
            
            if service == YAHOO_SEARCH:
                item.type     = self._parse(r, 'MimeType')
                item.date     = self._parse(r, 'ModificationDate')
            if service == YAHOO_IMAGES:
                item.type     = self._parse(r, 'FileFormat')
                item.width    = int(self._parse(r, 'Width'))
                item.height   = int(self._parse(r, 'Height'))
            if service == YAHOO_NEWS:
                item.date     = self._parse(r, 'ModificationDate')
                item.source   = self._parse(r, 'NewsSourceUrl')
                item.language = self._parse(r, 'Language')
            
            self.append(item)
            
    def _parse(self, e, tag):
        
        

        
        tags = e.getElementsByTagName(tag)
        children = tags[0].childNodes
        if len(children) != 1: return None
        assert children[0].nodeType == xml.dom.minidom.Element.TEXT_NODE
        
        s = children[0].nodeValue
        s = format_data(s)
        s = replace_entities(s)
        
        return s
        
    def __cmp__(self, other):
        
        

    
        if self.total > other.total:
            return 1
        elif self.total < other.total: 
            return -1
        else:
            return 0



class YahooSearch(YahooResults, URLAccumulator):
    
    def __init__(self, q, start=1, count=10, service=YAHOO_SEARCH, context=None, 
                 wait=10, asynchronous=False, cached=True):

        

        
        self.query = q
        self.service = service
        
        if cached:
            cache = "yahoo"
        else:
            cache = None

        url = "http://search.yahooapis.com/"
        if service == YAHOO_SEARCH and context == None : url += "WebSearchService/V1/webSearch?"
        if service == YAHOO_SEARCH and context != None : url += "WebSearchService/V1/contextSearch?"
        if service == YAHOO_IMAGES   :  url += "ImageSearchService/V1/imageSearch?"
        if service == YAHOO_NEWS     :  url += "NewsSearchService/V1/newsSearch?"
        if service == YAHOO_SPELLING :  url += "WebSearchService/V1/spellingSuggestion?"
        arg = urllib.urlencode((("appid", YAHOO_ID), 
                                ("query", q),
                                ("start", start),
                                ("results", count),
                                ("context", unicode(context))))
        
        url += arg
        URLAccumulator.__init__(self, url, wait, asynchronous, cache, ".xml")
        
    def load(self, data):
        
        if str(self.error.__class__) == str(HTTP403Forbidden().__class__):
            self.error = YahooLimitError()
            
        YahooResults.__init__(self, self.query, data, self.service)



def search(q, start=1, count=10, context=None, wait=10, asynchronous=False, cached=False):
    
    

    
    service = YAHOO_SEARCH
    return YahooSearch(q, start, count, service, context, wait, asynchronous, cached)  

def search_images(q, start=1, count=10, wait=10, asynchronous=False, cached=False):
    
    

    
    service = YAHOO_IMAGES
    return YahooSearch(q, start, count, service, None, wait, asynchronous, cached)   

def search_news(q, start=1, count=10, wait=10, asynchronous=False, cached=False):
    
    

    
    service = YAHOO_NEWS
    return YahooSearch(q, start, count, service, None, wait, asynchronous, cached)  



class YahooSpelling(YahooSearch):
    
    def __init__(self, q, wait, asynchronous, cached):
        
        service = YAHOO_SPELLING
        YahooSearch.__init__(self, q, 1, 1, service, None, wait, asynchronous, cached)
        
    def load(self, data):
        
        dom = xml.dom.minidom.parseString(data)
        doc = dom.childNodes[0]
        r = doc.getElementsByTagName('Result')
        if len(r) > 0:
            r = r[0].childNodes[0].nodeValue
            r = format_data(r)
        else:
            r = q
        
        self.append(r)

def suggest_spelling(q, wait=10, asynchronous=False, cached=False):
    
    

    
    return YahooSpelling(q, wait, asynchronous, cached)



def sort(words, context="", strict=True, relative=True, service=YAHOO_SEARCH,
         wait=10, asynchronous=False, cached=False):
    
    

    
    results = []
    for word in words:
        q = word + " " + context
        q.strip()
        if strict: q = "\""+q+"\""
        r = YahooSearch(q, 1, 1, service, context, wait, asynchronous, cached)
        results.append(r)
        
    results.sort(YahooResults.__cmp__)
    results.reverse()
    
    if relative and len(results) > 0:
        sum = 0.000000000000000001
        for r in results: sum += r.total
        for r in results: r.total /= float(sum)
    
    results = [(r.query, r.total) for r in results]
    return results






































#    print item.title