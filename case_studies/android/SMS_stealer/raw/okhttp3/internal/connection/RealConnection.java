package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http1.Http1Codec;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Codec;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public final class RealConnection extends Http2Connection.Listener implements Connection {
  private static final int MAX_TUNNEL_ATTEMPTS = 21;
  
  private static final String NPE_THROW_WITH_NULL = "throw with null exception";
  
  public int allocationLimit = 1;
  
  public final List<Reference<StreamAllocation>> allocations = new ArrayList<Reference<StreamAllocation>>();
  
  private final ConnectionPool connectionPool;
  
  private Handshake handshake;
  
  private Http2Connection http2Connection;
  
  public long idleAtNanos = Long.MAX_VALUE;
  
  public boolean noNewStreams;
  
  private Protocol protocol;
  
  private Socket rawSocket;
  
  private final Route route;
  
  private BufferedSink sink;
  
  private Socket socket;
  
  private BufferedSource source;
  
  public int successCount;
  
  public RealConnection(ConnectionPool paramConnectionPool, Route paramRoute) {
    this.connectionPool = paramConnectionPool;
    this.route = paramRoute;
  }
  
  private void connectSocket(int paramInt1, int paramInt2, Call paramCall, EventListener paramEventListener) throws IOException {
    Socket socket;
    Proxy proxy = this.route.proxy();
    Address address = this.route.address();
    if (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.HTTP) {
      socket = address.socketFactory().createSocket();
    } else {
      socket = new Socket(proxy);
    } 
    this.rawSocket = socket;
    paramEventListener.connectStart(paramCall, this.route.socketAddress(), proxy);
    this.rawSocket.setSoTimeout(paramInt2);
    try {
      Platform.get().connectSocket(this.rawSocket, this.route.socketAddress(), paramInt1);
      try {
        this.source = Okio.buffer(Okio.source(this.rawSocket));
        this.sink = Okio.buffer(Okio.sink(this.rawSocket));
      } catch (NullPointerException nullPointerException) {
        if ("throw with null exception".equals(nullPointerException.getMessage()))
          throw new IOException(nullPointerException); 
      } 
      return;
    } catch (ConnectException connectException1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to connect to ");
      stringBuilder.append(this.route.socketAddress());
      ConnectException connectException2 = new ConnectException(stringBuilder.toString());
      connectException2.initCause(connectException1);
      throw connectException2;
    } 
  }
  
  private void connectTls(ConnectionSpecSelector paramConnectionSpecSelector) throws IOException {
    X509Certificate x509Certificate;
    Address address = this.route.address();
    SSLSocketFactory sSLSocketFactory = address.sslSocketFactory();
    Handshake handshake = null;
    ConnectionSpec connectionSpec = null;
    SSLSession sSLSession = null;
    try {
      SSLSocket sSLSocket = (SSLSocket)sSLSocketFactory.createSocket(this.rawSocket, address.url().host(), address.url().port(), true);
      try {
        connectionSpec = paramConnectionSpecSelector.configureSecureSocket(sSLSocket);
        if (connectionSpec.supportsTlsExtensions())
          Platform.get().configureTlsExtensions(sSLSocket, address.url().host(), address.protocols()); 
        sSLSocket.startHandshake();
        SSLSession sSLSession1 = sSLSocket.getSession();
        if (isValid(sSLSession1)) {
          handshake = Handshake.get(sSLSession1);
          if (address.hostnameVerifier().verify(address.url().host(), sSLSession1)) {
            String str;
            Protocol protocol;
            address.certificatePinner().check(address.url().host(), handshake.peerCertificates());
            sSLSession1 = sSLSession;
            if (connectionSpec.supportsTlsExtensions())
              str = Platform.get().getSelectedProtocol(sSLSocket); 
            this.socket = sSLSocket;
            this.source = Okio.buffer(Okio.source(sSLSocket));
            this.sink = Okio.buffer(Okio.sink(this.socket));
            this.handshake = handshake;
            if (str != null) {
              protocol = Protocol.get(str);
            } else {
              protocol = Protocol.HTTP_1_1;
            } 
            this.protocol = protocol;
            return;
          } 
          x509Certificate = handshake.peerCertificates().get(0);
          SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException();
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("Hostname ");
          stringBuilder.append(address.url().host());
          stringBuilder.append(" not verified:\n    certificate: ");
          stringBuilder.append(CertificatePinner.pin(x509Certificate));
          stringBuilder.append("\n    DN: ");
          stringBuilder.append(x509Certificate.getSubjectDN().getName());
          stringBuilder.append("\n    subjectAltNames: ");
          stringBuilder.append(OkHostnameVerifier.allSubjectAltNames(x509Certificate));
          this(stringBuilder.toString());
          throw sSLPeerUnverifiedException;
        } 
        IOException iOException = new IOException();
        this("a valid ssl session was not established");
        throw iOException;
      } catch (AssertionError assertionError1) {
        SSLSocket sSLSocket1 = sSLSocket;
      } finally {
        AssertionError assertionError1;
        paramConnectionSpecSelector = null;
      } 
    } catch (AssertionError assertionError) {
      X509Certificate x509Certificate1 = x509Certificate;
    } finally {}
    ConnectionSpecSelector connectionSpecSelector = paramConnectionSpecSelector;
    if (Util.isAndroidGetsocknameError(assertionError)) {
      connectionSpecSelector = paramConnectionSpecSelector;
      IOException iOException = new IOException();
      connectionSpecSelector = paramConnectionSpecSelector;
      this(assertionError);
      connectionSpecSelector = paramConnectionSpecSelector;
      throw iOException;
    } 
    connectionSpecSelector = paramConnectionSpecSelector;
    throw assertionError;
  }
  
  private void connectTunnel(int paramInt1, int paramInt2, int paramInt3, Call paramCall, EventListener paramEventListener) throws IOException {
    Request request = createTunnelRequest();
    HttpUrl httpUrl = request.url();
    for (byte b = 0; b < 21; b++) {
      connectSocket(paramInt1, paramInt2, paramCall, paramEventListener);
      request = createTunnel(paramInt2, paramInt3, request, httpUrl);
      if (request == null)
        break; 
      Util.closeQuietly(this.rawSocket);
      this.rawSocket = null;
      this.sink = null;
      this.source = null;
      paramEventListener.connectEnd(paramCall, this.route.socketAddress(), this.route.proxy(), null);
    } 
  }
  
  private Request createTunnel(int paramInt1, int paramInt2, Request paramRequest, HttpUrl paramHttpUrl) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("CONNECT ");
    stringBuilder.append(Util.hostHeader(paramHttpUrl, true));
    stringBuilder.append(" HTTP/1.1");
    String str = stringBuilder.toString();
    while (true) {
      Http1Codec http1Codec = new Http1Codec(null, null, this.source, this.sink);
      this.source.timeout().timeout(paramInt1, TimeUnit.MILLISECONDS);
      this.sink.timeout().timeout(paramInt2, TimeUnit.MILLISECONDS);
      http1Codec.writeRequest(paramRequest.headers(), str);
      http1Codec.finishRequest();
      Response response = http1Codec.readResponseHeaders(false).request(paramRequest).build();
      long l2 = HttpHeaders.contentLength(response);
      long l1 = l2;
      if (l2 == -1L)
        l1 = 0L; 
      Source source = http1Codec.newFixedLengthSource(l1);
      Util.skipAll(source, 2147483647, TimeUnit.MILLISECONDS);
      source.close();
      int i = response.code();
      if (i != 200) {
        if (i == 407) {
          Request request = this.route.address().proxyAuthenticator().authenticate(this.route, response);
          if (request != null) {
            if ("close".equalsIgnoreCase(response.header("Connection")))
              return request; 
            continue;
          } 
          throw new IOException("Failed to authenticate with proxy");
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Unexpected response code for CONNECT: ");
        stringBuilder1.append(response.code());
        throw new IOException(stringBuilder1.toString());
      } 
      if (this.source.buffer().exhausted() && this.sink.buffer().exhausted())
        return null; 
      throw new IOException("TLS tunnel buffered too many bytes!");
    } 
  }
  
  private Request createTunnelRequest() {
    return (new Request.Builder()).url(this.route.address().url()).header("Host", Util.hostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", Version.userAgent()).build();
  }
  
  private void establishProtocol(ConnectionSpecSelector paramConnectionSpecSelector, int paramInt, Call paramCall, EventListener paramEventListener) throws IOException {
    if (this.route.address().sslSocketFactory() == null) {
      this.protocol = Protocol.HTTP_1_1;
      this.socket = this.rawSocket;
      return;
    } 
    paramEventListener.secureConnectStart(paramCall);
    connectTls(paramConnectionSpecSelector);
    paramEventListener.secureConnectEnd(paramCall, this.handshake);
    if (this.protocol == Protocol.HTTP_2) {
      this.socket.setSoTimeout(0);
      Http2Connection http2Connection = (new Http2Connection.Builder(true)).socket(this.socket, this.route.address().url().host(), this.source, this.sink).listener(this).pingIntervalMillis(paramInt).build();
      this.http2Connection = http2Connection;
      http2Connection.start();
    } 
  }
  
  private boolean isValid(SSLSession paramSSLSession) {
    boolean bool;
    if (!"NONE".equals(paramSSLSession.getProtocol()) && !"SSL_NULL_WITH_NULL_NULL".equals(paramSSLSession.getCipherSuite())) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static RealConnection testConnection(ConnectionPool paramConnectionPool, Route paramRoute, Socket paramSocket, long paramLong) {
    RealConnection realConnection = new RealConnection(paramConnectionPool, paramRoute);
    realConnection.socket = paramSocket;
    realConnection.idleAtNanos = paramLong;
    return realConnection;
  }
  
  public void cancel() {
    Util.closeQuietly(this.rawSocket);
  }
  
  public void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Call paramCall, EventListener paramEventListener) {
    // Byte code:
    //   0: aload_0
    //   1: getfield protocol : Lokhttp3/Protocol;
    //   4: ifnonnull -> 458
    //   7: aload_0
    //   8: getfield route : Lokhttp3/Route;
    //   11: invokevirtual address : ()Lokhttp3/Address;
    //   14: invokevirtual connectionSpecs : ()Ljava/util/List;
    //   17: astore #8
    //   19: new okhttp3/internal/connection/ConnectionSpecSelector
    //   22: dup
    //   23: aload #8
    //   25: invokespecial <init> : (Ljava/util/List;)V
    //   28: astore #10
    //   30: aload_0
    //   31: getfield route : Lokhttp3/Route;
    //   34: invokevirtual address : ()Lokhttp3/Address;
    //   37: invokevirtual sslSocketFactory : ()Ljavax/net/ssl/SSLSocketFactory;
    //   40: ifnonnull -> 158
    //   43: aload #8
    //   45: getstatic okhttp3/ConnectionSpec.CLEARTEXT : Lokhttp3/ConnectionSpec;
    //   48: invokeinterface contains : (Ljava/lang/Object;)Z
    //   53: ifeq -> 140
    //   56: aload_0
    //   57: getfield route : Lokhttp3/Route;
    //   60: invokevirtual address : ()Lokhttp3/Address;
    //   63: invokevirtual url : ()Lokhttp3/HttpUrl;
    //   66: invokevirtual host : ()Ljava/lang/String;
    //   69: astore #8
    //   71: invokestatic get : ()Lokhttp3/internal/platform/Platform;
    //   74: aload #8
    //   76: invokevirtual isCleartextTrafficPermitted : (Ljava/lang/String;)Z
    //   79: ifeq -> 85
    //   82: goto -> 158
    //   85: new java/lang/StringBuilder
    //   88: dup
    //   89: invokespecial <init> : ()V
    //   92: astore #6
    //   94: aload #6
    //   96: ldc_w 'CLEARTEXT communication to '
    //   99: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload #6
    //   105: aload #8
    //   107: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload #6
    //   113: ldc_w ' not permitted by network security policy'
    //   116: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: new okhttp3/internal/connection/RouteException
    //   123: dup
    //   124: new java/net/UnknownServiceException
    //   127: dup
    //   128: aload #6
    //   130: invokevirtual toString : ()Ljava/lang/String;
    //   133: invokespecial <init> : (Ljava/lang/String;)V
    //   136: invokespecial <init> : (Ljava/io/IOException;)V
    //   139: athrow
    //   140: new okhttp3/internal/connection/RouteException
    //   143: dup
    //   144: new java/net/UnknownServiceException
    //   147: dup
    //   148: ldc_w 'CLEARTEXT communication not enabled for client'
    //   151: invokespecial <init> : (Ljava/lang/String;)V
    //   154: invokespecial <init> : (Ljava/io/IOException;)V
    //   157: athrow
    //   158: aconst_null
    //   159: astore #9
    //   161: aload_0
    //   162: getfield route : Lokhttp3/Route;
    //   165: invokevirtual requiresTunnel : ()Z
    //   168: ifeq -> 199
    //   171: aload_0
    //   172: iload_1
    //   173: iload_2
    //   174: iload_3
    //   175: aload #6
    //   177: aload #7
    //   179: invokespecial connectTunnel : (IIILokhttp3/Call;Lokhttp3/EventListener;)V
    //   182: aload_0
    //   183: getfield rawSocket : Ljava/net/Socket;
    //   186: astore #8
    //   188: aload #8
    //   190: ifnonnull -> 196
    //   193: goto -> 246
    //   196: goto -> 209
    //   199: aload_0
    //   200: iload_1
    //   201: iload_2
    //   202: aload #6
    //   204: aload #7
    //   206: invokespecial connectSocket : (IILokhttp3/Call;Lokhttp3/EventListener;)V
    //   209: aload_0
    //   210: aload #10
    //   212: iload #4
    //   214: aload #6
    //   216: aload #7
    //   218: invokespecial establishProtocol : (Lokhttp3/internal/connection/ConnectionSpecSelector;ILokhttp3/Call;Lokhttp3/EventListener;)V
    //   221: aload #7
    //   223: aload #6
    //   225: aload_0
    //   226: getfield route : Lokhttp3/Route;
    //   229: invokevirtual socketAddress : ()Ljava/net/InetSocketAddress;
    //   232: aload_0
    //   233: getfield route : Lokhttp3/Route;
    //   236: invokevirtual proxy : ()Ljava/net/Proxy;
    //   239: aload_0
    //   240: getfield protocol : Lokhttp3/Protocol;
    //   243: invokevirtual connectEnd : (Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;)V
    //   246: aload_0
    //   247: getfield route : Lokhttp3/Route;
    //   250: invokevirtual requiresTunnel : ()Z
    //   253: ifeq -> 284
    //   256: aload_0
    //   257: getfield rawSocket : Ljava/net/Socket;
    //   260: ifnull -> 266
    //   263: goto -> 284
    //   266: new okhttp3/internal/connection/RouteException
    //   269: dup
    //   270: new java/net/ProtocolException
    //   273: dup
    //   274: ldc_w 'Too many tunnel connections attempted: 21'
    //   277: invokespecial <init> : (Ljava/lang/String;)V
    //   280: invokespecial <init> : (Ljava/io/IOException;)V
    //   283: athrow
    //   284: aload_0
    //   285: getfield http2Connection : Lokhttp3/internal/http2/Http2Connection;
    //   288: ifnull -> 325
    //   291: aload_0
    //   292: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   295: astore #7
    //   297: aload #7
    //   299: monitorenter
    //   300: aload_0
    //   301: aload_0
    //   302: getfield http2Connection : Lokhttp3/internal/http2/Http2Connection;
    //   305: invokevirtual maxConcurrentStreams : ()I
    //   308: putfield allocationLimit : I
    //   311: aload #7
    //   313: monitorexit
    //   314: goto -> 325
    //   317: astore #6
    //   319: aload #7
    //   321: monitorexit
    //   322: aload #6
    //   324: athrow
    //   325: return
    //   326: astore #8
    //   328: goto -> 338
    //   331: astore #8
    //   333: goto -> 338
    //   336: astore #8
    //   338: aload_0
    //   339: getfield socket : Ljava/net/Socket;
    //   342: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   345: aload_0
    //   346: getfield rawSocket : Ljava/net/Socket;
    //   349: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   352: aload_0
    //   353: aconst_null
    //   354: putfield socket : Ljava/net/Socket;
    //   357: aload_0
    //   358: aconst_null
    //   359: putfield rawSocket : Ljava/net/Socket;
    //   362: aload_0
    //   363: aconst_null
    //   364: putfield source : Lokio/BufferedSource;
    //   367: aload_0
    //   368: aconst_null
    //   369: putfield sink : Lokio/BufferedSink;
    //   372: aload_0
    //   373: aconst_null
    //   374: putfield handshake : Lokhttp3/Handshake;
    //   377: aload_0
    //   378: aconst_null
    //   379: putfield protocol : Lokhttp3/Protocol;
    //   382: aload_0
    //   383: aconst_null
    //   384: putfield http2Connection : Lokhttp3/internal/http2/Http2Connection;
    //   387: aload #7
    //   389: aload #6
    //   391: aload_0
    //   392: getfield route : Lokhttp3/Route;
    //   395: invokevirtual socketAddress : ()Ljava/net/InetSocketAddress;
    //   398: aload_0
    //   399: getfield route : Lokhttp3/Route;
    //   402: invokevirtual proxy : ()Ljava/net/Proxy;
    //   405: aconst_null
    //   406: aload #8
    //   408: invokevirtual connectFailed : (Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;Ljava/io/IOException;)V
    //   411: aload #9
    //   413: ifnonnull -> 430
    //   416: new okhttp3/internal/connection/RouteException
    //   419: dup
    //   420: aload #8
    //   422: invokespecial <init> : (Ljava/io/IOException;)V
    //   425: astore #9
    //   427: goto -> 437
    //   430: aload #9
    //   432: aload #8
    //   434: invokevirtual addConnectException : (Ljava/io/IOException;)V
    //   437: iload #5
    //   439: ifeq -> 455
    //   442: aload #10
    //   444: aload #8
    //   446: invokevirtual connectionFailed : (Ljava/io/IOException;)Z
    //   449: ifeq -> 455
    //   452: goto -> 161
    //   455: aload #9
    //   457: athrow
    //   458: new java/lang/IllegalStateException
    //   461: dup
    //   462: ldc_w 'already connected'
    //   465: invokespecial <init> : (Ljava/lang/String;)V
    //   468: athrow
    // Exception table:
    //   from	to	target	type
    //   161	188	336	java/io/IOException
    //   199	209	331	java/io/IOException
    //   209	246	326	java/io/IOException
    //   300	314	317	finally
    //   319	322	317	finally
  }
  
  public Handshake handshake() {
    return this.handshake;
  }
  
  public boolean isEligible(Address paramAddress, @Nullable Route paramRoute) {
    if (this.allocations.size() < this.allocationLimit && !this.noNewStreams) {
      if (!Internal.instance.equalsNonHost(this.route.address(), paramAddress))
        return false; 
      if (paramAddress.url().host().equals(route().address().url().host()))
        return true; 
      if (this.http2Connection == null)
        return false; 
      if (paramRoute == null)
        return false; 
      if (paramRoute.proxy().type() != Proxy.Type.DIRECT)
        return false; 
      if (this.route.proxy().type() != Proxy.Type.DIRECT)
        return false; 
      if (!this.route.socketAddress().equals(paramRoute.socketAddress()))
        return false; 
      if (paramRoute.address().hostnameVerifier() != OkHostnameVerifier.INSTANCE)
        return false; 
      if (!supportsUrl(paramAddress.url()))
        return false; 
      try {
        paramAddress.certificatePinner().check(paramAddress.url().host(), handshake().peerCertificates());
        return true;
      } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {}
    } 
    return false;
  }
  
  public boolean isHealthy(boolean paramBoolean) {
    if (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown())
      return false; 
    Http2Connection http2Connection = this.http2Connection;
    if (http2Connection != null)
      return http2Connection.isShutdown() ^ true; 
    if (paramBoolean)
      try {
        int i = this.socket.getSoTimeout();
        try {
          this.socket.setSoTimeout(1);
          paramBoolean = this.source.exhausted();
          if (paramBoolean)
            return false; 
          return true;
        } finally {
          this.socket.setSoTimeout(i);
        } 
      } catch (SocketTimeoutException socketTimeoutException) {
      
      } catch (IOException iOException) {
        return false;
      }  
    return true;
  }
  
  public boolean isMultiplexed() {
    boolean bool;
    if (this.http2Connection != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public HttpCodec newCodec(OkHttpClient paramOkHttpClient, Interceptor.Chain paramChain, StreamAllocation paramStreamAllocation) throws SocketException {
    if (this.http2Connection != null)
      return (HttpCodec)new Http2Codec(paramOkHttpClient, paramChain, paramStreamAllocation, this.http2Connection); 
    this.socket.setSoTimeout(paramChain.readTimeoutMillis());
    this.source.timeout().timeout(paramChain.readTimeoutMillis(), TimeUnit.MILLISECONDS);
    this.sink.timeout().timeout(paramChain.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
    return (HttpCodec)new Http1Codec(paramOkHttpClient, paramStreamAllocation, this.source, this.sink);
  }
  
  public RealWebSocket.Streams newWebSocketStreams(final StreamAllocation streamAllocation) {
    return new RealWebSocket.Streams(true, this.source, this.sink) {
        final RealConnection this$0;
        
        final StreamAllocation val$streamAllocation;
        
        public void close() throws IOException {
          StreamAllocation streamAllocation = streamAllocation;
          streamAllocation.streamFinished(true, streamAllocation.codec(), -1L, null);
        }
      };
  }
  
  public void onSettings(Http2Connection paramHttp2Connection) {
    synchronized (this.connectionPool) {
      this.allocationLimit = paramHttp2Connection.maxConcurrentStreams();
      return;
    } 
  }
  
  public void onStream(Http2Stream paramHttp2Stream) throws IOException {
    paramHttp2Stream.close(ErrorCode.REFUSED_STREAM);
  }
  
  public Protocol protocol() {
    return this.protocol;
  }
  
  public Route route() {
    return this.route;
  }
  
  public Socket socket() {
    return this.socket;
  }
  
  public boolean supportsUrl(HttpUrl paramHttpUrl) {
    int j = paramHttpUrl.port();
    int i = this.route.address().url().port();
    boolean bool = false;
    if (j != i)
      return false; 
    if (!paramHttpUrl.host().equals(this.route.address().url().host())) {
      boolean bool1 = bool;
      if (this.handshake != null) {
        bool1 = bool;
        if (OkHostnameVerifier.INSTANCE.verify(paramHttpUrl.host(), this.handshake.peerCertificates().get(0)))
          bool1 = true; 
      } 
      return bool1;
    } 
    return true;
  }
  
  public String toString() {
    String str;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Connection{");
    stringBuilder.append(this.route.address().url().host());
    stringBuilder.append(":");
    stringBuilder.append(this.route.address().url().port());
    stringBuilder.append(", proxy=");
    stringBuilder.append(this.route.proxy());
    stringBuilder.append(" hostAddress=");
    stringBuilder.append(this.route.socketAddress());
    stringBuilder.append(" cipherSuite=");
    Handshake handshake = this.handshake;
    if (handshake != null) {
      CipherSuite cipherSuite = handshake.cipherSuite();
    } else {
      str = "none";
    } 
    stringBuilder.append(str);
    stringBuilder.append(" protocol=");
    stringBuilder.append(this.protocol);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\connection\RealConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */