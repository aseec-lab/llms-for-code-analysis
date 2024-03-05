



















from __future__ import absolute_import, division

__docformat__ = "restructuredtext en"

import threading
import socket
import logging

try:
    from socket import SOMAXCONN
except ImportError:
    SOMAXCONN = 5

from ..mainloop.interfaces import IOHandler, HandlerReady
from ..exceptions import PyXMPPIOError

logger = logging.getLogger("pyxmpp2.server.listener")

from ..transport import BLOCKING_ERRORS



class TCPListener(IOHandler):
    

    _socket = None
    def __init__(self, family, address, target):
        

        self._socket = None
        self._lock = threading.RLock()
        self._target = target
        sock = socket.socket(family, socket.SOCK_STREAM)
        try:
            sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            sock.bind(address)
        except:
            sock.close()
            raise
        self._socket = sock

    def __del__(self):
        if self._socket:
            self._socket.close()
            self._socket = None

    def close(self):
        with self._lock:
            if self._socket:
                self._socket.close()
                self._socket = None

    def prepare(self):
        

        with self._lock:
            if self._socket:
                self._socket.listen(SOMAXCONN)
                self._socket.setblocking(False)
            return HandlerReady()

    def fileno(self):
        

        with self._lock:
            if self._socket:
                return self._socket.fileno()

    def is_readable(self):
        with self._lock:
            return self._socket is not None

    def wait_for_readability(self):
        with self._lock:
            return self._socket is not None

    def is_writable(self):
        return False

    def wait_for_writability(self):
        return False

    def handle_write(self):
        return

    def handle_read(self):
        

        with self._lock:
            logger.debug("handle_read()")
            if self._socket is None:
                return
            while True:
                try:
                    sock, address = self._socket.accept()
                except socket.error, err:
                    if err.args[0] in BLOCKING_ERRORS:
                        break
                    else:
                        raise
                logger.debug("Accepted connection from: {0!r}".format(address))
                self._target(sock, address)

    def handle_hup(self):
        self.close()

    def handle_err(self):
        self.close()
        raise PyXMPPIOError("Unhandled error on socket")

    def handle_nval(self):
        self.close()
        raise PyXMPPIOError("Invalid file descriptor used in main event loop")
