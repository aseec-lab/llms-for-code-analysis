



















from __future__ import absolute_import, division

__docformat__="restructuredtext en"

raise ImportError("{0} is not yet rewritten for PyXMPP2".format(__name__))

import warnings

import libxml2

from ..xmlextra import common_doc,common_root
from ..jid import JID
from .. import cache

from ..utils import to_utf8
from ..objects import StanzaPayloadWrapperObject
from ..exceptions import ProtocolError

DISCO_NS="http://jabber.org/protocol/disco"
DISCO_ITEMS_NS=DISCO_NS+"
DISCO_INFO_NS=DISCO_NS+"

class DiscoItem(StanzaPayloadWrapperObject):
    

    def __init__(self,disco,xmlnode_or_jid,node=None,name=None,action=None):
        

        self.disco=disco
        if isinstance(xmlnode_or_jid,JID):
            if disco:
                self.xmlnode=disco.xmlnode.newChild(None,"item",None)
            else:
                self.xmlnode=common_root.newChild(None,"item",None)
                ns=self.xmlnode.newNs(DISCO_ITEMS_NS,None)
                self.xmlnode.setNs(ns)
            self.set_jid(xmlnode_or_jid)
            self.set_name(name)
            self.set_node(node)
            self.set_action(action)
        else:
            if disco is None:
                self.xmlnode=xmlnode_or_jid.copyNode(1)
            else:
                self.xmlnode=xmlnode_or_jid
            if name:
                self.set_name(name)
            if node:
                self.set_node(node)
            if action:
                self.set_action(action)
        self.xpath_ctxt=common_doc.xpathNewContext()
        self.xpath_ctxt.setContextNode(self.xmlnode)
        self.xpath_ctxt.xpathRegisterNs("d",DISCO_ITEMS_NS)

    def __del__(self):
        if self.disco is None:
            if self.xmlnode:
                self.xmlnode.unlinkNode()
                self.xmlnode.freeNode()
                self.xmlnode=None
        if self.xpath_ctxt:
            self.xpath_ctxt.xpathFreeContext()

    def __str__(self):
        return self.xmlnode.serialize()

    def remove(self):
        

        if self.disco is None:
            return
        self.xmlnode.unlinkNode()
        oldns=self.xmlnode.ns()
        ns=self.xmlnode.newNs(oldns.getContent(),None)
        self.xmlnode.replaceNs(oldns,ns)
        common_root.addChild(self.xmlnode())
        self.disco=None

    def get_name(self):
        

        name = self.xmlnode.prop("name")
        if name is None:
            return None
        return name.decode("utf-8")

    def set_name(self, name):
        

        if name is None:
            if self.xmlnode.hasProp("name"):
                self.xmlnode.unsetProp("name")
            return
        name = unicode(name)
        self.xmlnode.setProp("name", name.encode("utf-8"))

    name = property(get_name, set_name)

    def get_node(self):
        

        node = self.xmlnode.prop("node")
        if node is None:
            return None
        return node.decode("utf-8")

    def set_node(self,node):
        

        if node is None:
            if self.xmlnode.hasProp("node"):
                self.xmlnode.unsetProp("node")
            return
        node = unicode(node)
        self.xmlnode.setProp("node", node.encode("utf-8"))

    node = property(get_node, set_node)

    def get_action(self):
        

        action=self.xmlnode.prop("action")
        if action is None:
            return None
        return action.decode("utf-8")

    def set_action(self,action):
        

        if action is None:
            if self.xmlnode.hasProp("action"):
                self.xmlnode.unsetProp("action")
            return
        if action not in ("remove","update"):
            raise ValueError("Action must be 'update' or 'remove'")
        action = unicode(action)
        self.xmlnode.setProp("action", action.encode("utf-8"))

    action = property(get_action, set_action)

    def get_jid(self):
        

        jid = self.xmlnode.prop("jid")
        return JID( jid.decode("utf-8") )

    def set_jid(self,jid):
        

        self.xmlnode.setProp("jid", jid.as_unicode().encode("utf-8"))

    jid = property(get_jid, set_jid)

class DiscoIdentity(StanzaPayloadWrapperObject):
    

    def __init__(self, disco, xmlnode_or_name, item_category=None, item_type=None, replace=False):
        

        self.disco=disco
        if disco and replace:
            old=disco.xpath_ctxt.xpathEval("d:identity")
            if old:
                for n in old:
                    n.unlinkNode()
                    n.freeNode()
        if isinstance(xmlnode_or_name,libxml2.xmlNode):
            if disco is None:
                self.xmlnode=xmlnode_or_name.copyNode(1)
            else:
                self.xmlnode=xmlnode_or_name
        elif not item_category:
            raise ValueError("DiscoInfo requires category")
        elif not item_type:
            raise ValueError("DiscoInfo requires type")
        else:
            if disco:
                self.xmlnode=disco.xmlnode.newChild(None,"identity",None)
            else:
                self.xmlnode=common_root.newChild(None,"identity",None)
                ns=self.xmlnode.newNs(DISCO_INFO_NS,None)
                self.xmlnode.setNs(ns)
            self.set_name(xmlnode_or_name)
            self.set_category(item_category)
            self.set_type(item_type)
        self.xpath_ctxt=common_doc.xpathNewContext()
        self.xpath_ctxt.setContextNode(self.xmlnode)
        self.xpath_ctxt.xpathRegisterNs("d",DISCO_INFO_NS)

    def __del__(self):
        if self.disco is None:
            if self.xmlnode:
                self.xmlnode.unlinkNode()
                self.xmlnode.freeNode()
                self.xmlnode=None
        if self.xpath_ctxt:
            self.xpath_ctxt.xpathFreeContext()

    def __str__(self):
        return self.xmlnode.serialize()

    def remove(self):
        

        if self.disco is None:
            return
        self.xmlnode.unlinkNode()
        oldns=self.xmlnode.ns()
        ns=self.xmlnode.newNs(oldns.getContent(),None)
        self.xmlnode.replaceNs(oldns,ns)
        common_root.addChild(self.xmlnode())
        self.disco=None

    def get_name(self):
        

        var = self.xmlnode.prop("name")
        if not var:
            var = ""
        return var.decode("utf-8")

    def set_name(self,name):
        

        if not name:
            raise ValueError("name is required in DiscoIdentity")
        name = unicode(name)
        self.xmlnode.setProp("name", name.encode("utf-8"))

    name = property(get_name, set_name)

    def get_category(self):
        

        var = self.xmlnode.prop("category")
        if not var:
            var = "?"
        return var.decode("utf-8")

    def set_category(self, category):
        

        if not category:
            raise ValueError("Category is required in DiscoIdentity")
        category = unicode(category)
        self.xmlnode.setProp("category", category.encode("utf-8"))

    category = property(get_category, set_category)

    def get_type(self):
        

        item_type = self.xmlnode.prop("type")
        if not item_type:
            item_type = "?"
        return item_type.decode("utf-8")

    def set_type(self, item_type):
        

        if not item_type:
            raise ValueError("Type is required in DiscoIdentity")
        item_type = unicode(item_type)
        self.xmlnode.setProp("type", item_type.encode("utf-8"))

    type = property(get_type, set_type)

class DiscoItems(StanzaPayloadWrapperObject):
    

    def __init__(self,xmlnode_or_node=None):
        

        self.xmlnode=None
        self.xpath_ctxt=None
        if isinstance(xmlnode_or_node,libxml2.xmlNode):
            ns=xmlnode_or_node.ns()
            if ns.getContent() != DISCO_ITEMS_NS:
                raise ValueError("Bad disco-items namespace")
            self.xmlnode=xmlnode_or_node.docCopyNode(common_doc,1)
            common_root.addChild(self.xmlnode)
            self.ns=self.xmlnode.ns()
        else:
            self.xmlnode=common_root.newChild(None,"query",None)
            self.ns=self.xmlnode.newNs(DISCO_ITEMS_NS,None)
            self.xmlnode.setNs(self.ns)
            self.set_node(xmlnode_or_node)
        self.xpath_ctxt=common_doc.xpathNewContext()
        self.xpath_ctxt.setContextNode(self.xmlnode)
        self.xpath_ctxt.xpathRegisterNs("d",DISCO_ITEMS_NS)

    def __del__(self):
        if self.xmlnode:
            self.xmlnode.unlinkNode()
            self.xmlnode.freeNode()
            self.xmlnode=None
        if self.xpath_ctxt:
            self.xpath_ctxt.xpathFreeContext()
            self.xpath_ctxt=None

    def get_node(self):
        

        node = self.xmlnode.prop("node")
        if not node:
            return None
        return node.decode("utf-8")

    def set_node(self, node):
        

        if node is None:
            if self.xmlnode.hasProp("node"):
                self.xmlnode.unsetProp("node")
            return
        node = unicode(node)
        self.xmlnode.setProp("node", node.encode("utf-8"))

    node = property(get_node, set_node)

    def get_items(self):
        

        ret=[]
        l=self.xpath_ctxt.xpathEval("d:item")
        if l is not None:
            for i in l:
                ret.append(DiscoItem(self, i))
        return ret

    def set_items(self, item_list):
        

        for item in self.items:
            item.remove()
        for item in item_list:
            try:
                self.add_item(item.jid,item.node,item.name,item.action)
            except AttributeError:
                self.add_item(*item)

    items = property(get_items, set_items, doc = "List of `DiscoItems`")

    def invalidate_items(self):
        

        warnings.warn("DiscoItems.invalidate_items() is deprecated and not needed any more.", DeprecationWarning, stacklevel=1)

    def add_item(self,jid,node=None,name=None,action=None):
        

        return DiscoItem(self,jid,node,name,action)

    def has_item(self,jid,node=None):
        

        l=self.xpath_ctxt.xpathEval("d:item")
        if l is None:
            return False
        for it in l:
            di=DiscoItem(self,it)
            if di.jid==jid and di.node==node:
                return True
        return False

class DiscoInfo(StanzaPayloadWrapperObject):
    

    def __init__(self,xmlnode_or_node=None, parent=None, doc=None):
        

        self.xmlnode=None
        self.xpath_ctxt=None
        if not doc:
            doc=common_doc
        if not parent:
            parent=common_root
        if isinstance(xmlnode_or_node,libxml2.xmlNode):
            ns=xmlnode_or_node.ns()
            if ns.getContent() != DISCO_INFO_NS:
                raise ValueError("Bad disco-info namespace")
            self.xmlnode=xmlnode_or_node.docCopyNode(doc,1)
            parent.addChild(self.xmlnode)
        else:
            self.xmlnode=parent.newChild(None,"query",None)
            self.ns=self.xmlnode.newNs(DISCO_INFO_NS,None)
            self.xmlnode.setNs(self.ns)
            self.set_node(xmlnode_or_node)

        self.xpath_ctxt=doc.xpathNewContext()
        self.xpath_ctxt.setContextNode(self.xmlnode)
        self.xpath_ctxt.xpathRegisterNs("d",DISCO_INFO_NS)

    def __del__(self):
        if self.xmlnode:
            self.xmlnode.unlinkNode()
            self.xmlnode.freeNode()
            self.xmlnode=None
        if self.xpath_ctxt:
            self.xpath_ctxt.xpathFreeContext()
            self.xpath_ctxt=None

    def get_node(self):
        


        node=self.xmlnode.prop("node")
        if not node:
            return None
        return node.decode("utf-8")

    def set_node(self,node):
        

        if node is None:
            if self.xmlnode.hasProp("node"):
                self.xmlnode.unsetProp("node")
            return
        node = unicode(node)
        self.xmlnode.setProp("node", node.encode("utf-8"))

    node = property(get_node, set_node)

    def get_features(self):
        

        l = self.xpath_ctxt.xpathEval("d:feature")
        ret = []
        for f in l:
            if f.hasProp("var"):
                ret.append( f.prop("var").decode("utf-8") )
        return ret

    def set_features(self, features):
        

        for var in self.features:
            self.remove_feature(var)

        for var in features:
            self.add_feature(var)

    features = property(get_features, set_features)

    def has_feature(self,var):
        

        if not var:
            raise ValueError("var is None")
        if 
 not in var:
            expr=u"d:feature[@var='%s']" % (var,)
        else:
            raise ValueError("Invalid feature name")

        l=self.xpath_ctxt.xpathEval(to_utf8(expr))
        if l:
            return True
        else:
            return False

    def invalidate_features(self):
        

        warnings.warn("DiscoInfo.invalidate_features() is deprecated and not needed any more.", DeprecationWarning, stacklevel=1)

    def add_feature(self,var):
        

        if self.has_feature(var):
            return
        n=self.xmlnode.newChild(None, "feature", None)
        n.setProp("var", to_utf8(var))

    def remove_feature(self,var):
        

        if not var:
            raise ValueError("var is None")
        if 
 not in var:
            expr="d:feature[@var='%s']" % (var,)
        else:
            raise ValueError("Invalid feature name")

        l=self.xpath_ctxt.xpathEval(expr)
        if not l:
            return

        for f in l:
            f.unlinkNode()
            f.freeNode()

    def get_identities(self):
        

        ret=[]
        l=self.xpath_ctxt.xpathEval("d:identity")
        if l is not None:
            for i in l:
                ret.append(DiscoIdentity(self,i))
        return ret

    def set_identities(self,identities):
        

        for identity in self.identities:
            identity.remove()
        for identity in identities:
            try:
                self.add_identity(identity.name,identity.category,identity.type)
            except AttributeError:
                self.add_identity(*identity)

    identities = property(get_identities, set_identities)

    def identity_is(self,item_category,item_type=None):
        

        if not item_category:
            raise ValueError("bad category")
        if not item_type:
            type_expr=u""
        elif 
 not in type:
            type_expr=u" and @type='%s'" % (item_type,)
        else:
            raise ValueError("Invalid type name")
        if 
 not in item_category:
            expr=u"d:identity[@category='%s'%s]" % (item_category,type_expr)
        else:
            raise ValueError("Invalid category name")

        l=self.xpath_ctxt.xpathEval(to_utf8(expr))
        if l:
            return True
        else:
            return False

    def invalidate_identities(self):
        

        warnings.warn("DiscoInfo.invalidate_identities() is deprecated and not needed any more.", DeprecationWarning, stacklevel=1)

    def add_identity(self,item_name,item_category=None,item_type=None):
        

        return DiscoIdentity(self,item_name,item_category,item_type)

class DiscoCacheFetcherBase(cache.CacheFetcher):
    

    stream=None
    disco_class=None
    def fetch(self):
        

        from ..iq import Iq
        jid,node = self.address
        iq = Iq(to_jid = jid, stanza_type = "get")
        disco = self.disco_class(node)
        iq.add_content(disco.xmlnode)
        self.stream.set_response_handlers(iq,self.__response, self.__error,
                self.__timeout)
        self.stream.send(iq)

    def __response(self,stanza):
        

        try:
            d=self.disco_class(stanza.get_query())
            self.got_it(d)
        except ValueError,e:
            self.error(e)

    def __error(self,stanza):
        

        try:
            self.error(stanza.get_error())
        except ProtocolError:
            from ..error import StanzaErrorNode
            self.error(StanzaErrorNode("undefined-condition"))

    def __timeout(self,stanza):
        

        pass

def register_disco_cache_fetchers(cache_suite,stream):
    

    tmp=stream
    class DiscoInfoCacheFetcher(DiscoCacheFetcherBase):
        

        stream=tmp
        disco_class=DiscoInfo
    class DiscoItemsCacheFetcher(DiscoCacheFetcherBase):
        

        stream=tmp
        disco_class=DiscoItems
    cache_suite.register_fetcher(DiscoInfo,DiscoInfoCacheFetcher)
    cache_suite.register_fetcher(DiscoItems,DiscoItemsCacheFetcher)


