





from wsgidav import __version__, compat, util

import logging
import socket
import sys
import threading
import time
import traceback


__docformat__ = "reStructuredText"


try:
    from http import client as http_client  
except ImportError:
    import httplib as http_client

try:
    from http import server as BaseHTTPServer  
except ImportError:
    import BaseHTTPServer

try:
    import socketserver  
except ImportError:
    import SocketServer as socketserver


_logger = util.get_module_logger(__name__)

_version = 1.0

SERVER_ERROR = 



class ExtHandler(BaseHTTPServer.BaseHTTPRequestHandler):

    _SUPPORTED_METHODS = [
        "HEAD",
        "GET",
        "PUT",
        "POST",
        "OPTIONS",
        "TRACE",
        "DELETE",
        "PROPFIND",
        "PROPPATCH",
        "MKCOL",
        "COPY",
        "MOVE",
        "LOCK",
        "UNLOCK",
    ]

    
    protocol_version = "HTTP/1.1"

    server_version = "WsgiDAV/{} ExtServer/{} {} Python {}".format(
        __version__,
        _version,
        BaseHTTPServer.BaseHTTPRequestHandler.server_version,
        util.PYTHON_VERSION,
    )

    def log_message(self, *args):
        pass

    

    def log_request(self, *args):
        pass

    

    def getApp(self):
        
        _protocol, _host, path, _parameters, query, _fragment = compat.urlparse(
            "http://dummyhost{}".format(self.path), allow_fragments=False
        )
        
        for appPath, app in self.server.wsgiApplications:
            if path.startswith(appPath):
                
                path_info = path[len(appPath) :]
                if len(path_info) > 0:
                    if not path_info.startswith("/"):
                        path_info = "/" + path_info
                if appPath.endswith("/"):
                    script_name = appPath[:-1]
                else:
                    script_name = appPath
                
                return app, script_name, path_info, query
        return None, None, None, None

    def handlerFunctionClosure(self, name):
        def handlerFunction(*args, **kwargs):
            self.do_method()

        return handlerFunction

    def do_method(self):
        app, script_name, path_info, query = self.getApp()
        if not app:
            self.send_error(404, "Application not found.")
            return
        self.runWSGIApp(app, script_name, path_info, query)

    def __getattr__(self, name):
        if len(name) > 3 and name[0:3] == "do_" and name[3:] in self._SUPPORTED_METHODS:
            return self.handlerFunctionClosure(name)
        elif name == "_headers_buffer":
            
            raise AttributeError
        self.send_error(501, "Method Not Implemented.")
        return

    def runWSGIApp(self, application, script_name, path_info, query):
        
        

        if self.command == "PUT":
            pass  

        env = {
            "wsgi.version": (1, 0),
            "wsgi.url_scheme": "http",
            "wsgi.input": self.rfile,
            "wsgi.errors": sys.stderr,
            "wsgi.multithread": 1,
            "wsgi.multiprocess": 0,
            "wsgi.run_once": 0,
            "REQUEST_METHOD": self.command,
            "SCRIPT_NAME": script_name,
            "PATH_INFO": path_info,
            "QUERY_STRING": query,
            "CONTENT_TYPE": self.headers.get("Content-Type", ""),
            "CONTENT_LENGTH": self.headers.get("Content-Length", ""),
            "REMOTE_ADDR": self.client_address[0],
            "SERVER_NAME": self.server.server_address[0],
            "SERVER_PORT": compat.to_native(self.server.server_address[1]),
            "SERVER_PROTOCOL": self.request_version,
        }
        for httpHeader, httpValue in self.headers.items():
            if not httpHeader.lower() in ("content-type", "content-length"):
                env["HTTP_{}".format(httpHeader.replace("-", "_").upper())] = httpValue

        
        self.wsgiSentHeaders = 0
        self.wsgiHeaders = []

        try:
            
            _logger.debug("runWSGIApp application()...")
            result = application(env, self.wsgiStartResponse)
            try:
                for data in result:
                    if data:
                        self.wsgiWriteData(data)
                    else:
                        _logger.debug("runWSGIApp empty data")
            finally:
                _logger.debug("runWSGIApp finally.")
                if hasattr(result, "close"):
                    result.close()
        except Exception:
            _logger.debug("runWSGIApp caught exception...")
            errorMsg = compat.StringIO()
            traceback.print_exc(file=errorMsg)
            logging.error(errorMsg.getvalue())
            if not self.wsgiSentHeaders:
                self.wsgiStartResponse(
                    "500 Server Error", [("Content-type", "text/html")]
                )
            self.wsgiWriteData(SERVER_ERROR)

        if not self.wsgiSentHeaders:
            
            
            
            self.wsgiWriteData(b"")
        return

    def wsgiStartResponse(self, response_status, response_headers, exc_info=None):
        _logger.debug(
            "wsgiStartResponse({}, {}, {})".format(
                response_status, response_headers, exc_info
            )
        )
        if self.wsgiSentHeaders:
            raise Exception("Headers already sent and start_response called again!")
        
        self.wsgiHeaders = (response_status, response_headers)
        return self.wsgiWriteData

    def wsgiWriteData(self, data):
        if not self.wsgiSentHeaders:
            status, headers = self.wsgiHeaders
            
            statusCode = status[: status.find(" ")]
            statusMsg = status[status.find(" ") + 1 :]
            _logger.debug(
                "wsgiWriteData: send headers '{!r}', {!r}".format(status, headers)
            )
            self.send_response(int(statusCode), statusMsg)
            for header, value in headers:
                self.send_header(header, value)
            self.end_headers()
            self.wsgiSentHeaders = 1
        
        
        _logger.debug(
            "wsgiWriteData: write {} bytes: '{!r}'...".format(
                len(data), compat.to_native(data[:50])
            )
        )
        if compat.is_unicode(data):  
            _logger.info("ext_wsgiutils_server: Got unicode data: {!r}".format(data))
            
            data = compat.to_bytes(data)

        try:
            self.wfile.write(data)
        except socket.error as e:
            
            
            
            if e.args[0] in (10053, 10054):
                _logger.info("*** Caught socket.error: ", e, file=sys.stderr)
            else:
                raise


class ExtServer(socketserver.ThreadingMixIn, BaseHTTPServer.HTTPServer):
    def handle_error(self, request, client_address):
        

        ei = sys.exc_info()
        e = ei[1]
        
        
        
        if e.args[0] in (10053, 10054):
            _logger.error("*** Caught socket.error: {}".format(e))
            return
        
        
        _logger.error("-" * 40, file=sys.stderr)
        _logger.error(
            "<{}> Exception happened during processing of request from {}".format(
                threading.currentThread().ident, client_address
            )
        )
        _logger.error(client_address, file=sys.stderr)
        traceback.print_exc()
        _logger.error("-" * 40, file=sys.stderr)
        _logger.error(request, file=sys.stderr)

    

    def stop_serve_forever(self):
        

        assert hasattr(
            self, "stop_request"
        ), "serve_forever_stoppable() must be called before"
        assert not self.stop_request, "stop_serve_forever() must only be called once"

        
        self.stop_request = True
        time.sleep(0.1)
        if self.stopped:
            
            return

        
        def _shutdownHandler(self):
            

            
            self.send_response(200)
            self.end_headers()
            self.server.stop_request = True

        if not hasattr(ExtHandler, "do_SHUTDOWN"):
            ExtHandler.do_SHUTDOWN = _shutdownHandler

        
        (host, port) = self.server_address
        
        conn = http_client.HTTPConnection("{}:{}".format(host, port))
        conn.request("SHUTDOWN", "/")
        
        conn.getresponse()
        
        assert self.stop_request

    def serve_forever_stoppable(self):
        

        self.stop_request = False
        self.stopped = False

        while not self.stop_request:
            self.handle_request()

        
        self.stopped = True

    def __init__(self, serverAddress, wsgiApplications, serveFiles=1):
        BaseHTTPServer.HTTPServer.__init__(self, serverAddress, ExtHandler)
        appList = []
        for urlPath, wsgiApp in wsgiApplications.items():
            appList.append((urlPath, wsgiApp))
        self.wsgiApplications = appList
        self.serveFiles = serveFiles
        self.serverShuttingDown = 0


def serve(conf, app):
    host = conf.get("host", "localhost")
    port = int(conf.get("port", 8080))
    server = ExtServer((host, port), {"": app})
    server_version = ExtHandler.server_version
    if conf.get("verbose") >= 1:
        _logger.info("Running {}".format(server_version))
        if host in ("", "0.0.0.0"):
            (hostname, _aliaslist, ipaddrlist) = socket.gethostbyname_ex(
                socket.gethostname()
            )
            _logger.info(
                "Serving at {}, port {} (host='{}' {})...".format(
                    host, port, hostname, ipaddrlist
                )
            )
        else:
            _logger.info("Serving at {}, port {}...".format(host, port))
    server.serve_forever()





if __name__ == "__main__":
    raise RuntimeError("Use run_server.py")
