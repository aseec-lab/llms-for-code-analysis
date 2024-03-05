




















from __future__ import absolute_import, division

__docformat__ = "restructuredtext en"

from .streambase import StreamBase
from .jid import JID
from .settings import XMPPSettings
from .constants import STANZA_CLIENT_NS

class ClientStream(StreamBase):
    

    
    def __init__(self, jid, stanza_route, handlers, settings = None):
        

        if handlers is None:
            handlers = []
        if settings is None:
            settings = XMPPSettings()
        if "resource" not in settings:
            settings["resource"] = jid.resource
        StreamBase.__init__(self, STANZA_CLIENT_NS, stanza_route,
                                                        handlers, settings)
        self.me = JID(jid.local, jid.domain)

    def initiate(self, transport, to = None):
        

        if to is None:
            to = JID(self.me.domain)
        return StreamBase.initiate(self, transport, to)

    def receive(self, transport, myname = None):
        

        if myname is None:
            myname = JID(self.me.domain)
        return StreamBase.receive(self, transport, myname)

    def fix_out_stanza(self, stanza):
        

        StreamBase.fix_out_stanza(self, stanza)
        if self.initiator:
            if stanza.from_jid:
                stanza.from_jid = None
        else:
            if not stanza.from_jid:
                stanza.from_jid = self.me

    def fix_in_stanza(self, stanza):
        

        StreamBase.fix_in_stanza(self, stanza)
        if not self.initiator:
            if stanza.from_jid != self.peer:
                stanza.set_from(self.peer)


