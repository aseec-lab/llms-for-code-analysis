package okhttp3;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;

public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
  static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS;
  
  static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableList((Object[])new Protocol[] { Protocol.HTTP_2, Protocol.HTTP_1_1 });
  
  final Authenticator authenticator;
  
  @Nullable
  final Cache cache;
  
  @Nullable
  final CertificateChainCleaner certificateChainCleaner;
  
  final CertificatePinner certificatePinner;
  
  final int connectTimeout;
  
  final ConnectionPool connectionPool;
  
  final List<ConnectionSpec> connectionSpecs;
  
  final CookieJar cookieJar;
  
  final Dispatcher dispatcher;
  
  final Dns dns;
  
  final EventListener.Factory eventListenerFactory;
  
  final boolean followRedirects;
  
  final boolean followSslRedirects;
  
  final HostnameVerifier hostnameVerifier;
  
  final List<Interceptor> interceptors;
  
  @Nullable
  final InternalCache internalCache;
  
  final List<Interceptor> networkInterceptors;
  
  final int pingInterval;
  
  final List<Protocol> protocols;
  
  @Nullable
  final Proxy proxy;
  
  final Authenticator proxyAuthenticator;
  
  final ProxySelector proxySelector;
  
  final int readTimeout;
  
  final boolean retryOnConnectionFailure;
  
  final SocketFactory socketFactory;
  
  @Nullable
  final SSLSocketFactory sslSocketFactory;
  
  final int writeTimeout;
  
  static {
    DEFAULT_CONNECTION_SPECS = Util.immutableList((Object[])new ConnectionSpec[] { ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT });
    Internal.instance = new Internal() {
        public void addLenient(Headers.Builder param1Builder, String param1String) {
          param1Builder.addLenient(param1String);
        }
        
        public void addLenient(Headers.Builder param1Builder, String param1String1, String param1String2) {
          param1Builder.addLenient(param1String1, param1String2);
        }
        
        public void apply(ConnectionSpec param1ConnectionSpec, SSLSocket param1SSLSocket, boolean param1Boolean) {
          param1ConnectionSpec.apply(param1SSLSocket, param1Boolean);
        }
        
        public int code(Response.Builder param1Builder) {
          return param1Builder.code;
        }
        
        public boolean connectionBecameIdle(ConnectionPool param1ConnectionPool, RealConnection param1RealConnection) {
          return param1ConnectionPool.connectionBecameIdle(param1RealConnection);
        }
        
        public Socket deduplicate(ConnectionPool param1ConnectionPool, Address param1Address, StreamAllocation param1StreamAllocation) {
          return param1ConnectionPool.deduplicate(param1Address, param1StreamAllocation);
        }
        
        public boolean equalsNonHost(Address param1Address1, Address param1Address2) {
          return param1Address1.equalsNonHost(param1Address2);
        }
        
        public RealConnection get(ConnectionPool param1ConnectionPool, Address param1Address, StreamAllocation param1StreamAllocation, Route param1Route) {
          return param1ConnectionPool.get(param1Address, param1StreamAllocation, param1Route);
        }
        
        public HttpUrl getHttpUrlChecked(String param1String) throws MalformedURLException, UnknownHostException {
          return HttpUrl.getChecked(param1String);
        }
        
        public Call newWebSocketCall(OkHttpClient param1OkHttpClient, Request param1Request) {
          return RealCall.newRealCall(param1OkHttpClient, param1Request, true);
        }
        
        public void put(ConnectionPool param1ConnectionPool, RealConnection param1RealConnection) {
          param1ConnectionPool.put(param1RealConnection);
        }
        
        public RouteDatabase routeDatabase(ConnectionPool param1ConnectionPool) {
          return param1ConnectionPool.routeDatabase;
        }
        
        public void setCache(OkHttpClient.Builder param1Builder, InternalCache param1InternalCache) {
          param1Builder.setInternalCache(param1InternalCache);
        }
        
        public StreamAllocation streamAllocation(Call param1Call) {
          return ((RealCall)param1Call).streamAllocation();
        }
      };
  }
  
  public OkHttpClient() {
    this(new Builder());
  }
  
  OkHttpClient(Builder paramBuilder) {
    this.dispatcher = paramBuilder.dispatcher;
    this.proxy = paramBuilder.proxy;
    this.protocols = paramBuilder.protocols;
    this.connectionSpecs = paramBuilder.connectionSpecs;
    this.interceptors = Util.immutableList(paramBuilder.interceptors);
    this.networkInterceptors = Util.immutableList(paramBuilder.networkInterceptors);
    this.eventListenerFactory = paramBuilder.eventListenerFactory;
    this.proxySelector = paramBuilder.proxySelector;
    this.cookieJar = paramBuilder.cookieJar;
    this.cache = paramBuilder.cache;
    this.internalCache = paramBuilder.internalCache;
    this.socketFactory = paramBuilder.socketFactory;
    Iterator<ConnectionSpec> iterator = this.connectionSpecs.iterator();
    label23: while (true) {
      boolean bool = false;
      while (iterator.hasNext()) {
        ConnectionSpec connectionSpec = iterator.next();
        if (bool || connectionSpec.isTls()) {
          bool = true;
          continue;
        } 
        continue label23;
      } 
      if (paramBuilder.sslSocketFactory != null || !bool) {
        this.sslSocketFactory = paramBuilder.sslSocketFactory;
        this.certificateChainCleaner = paramBuilder.certificateChainCleaner;
      } else {
        X509TrustManager x509TrustManager = systemDefaultTrustManager();
        this.sslSocketFactory = systemDefaultSslSocketFactory(x509TrustManager);
        this.certificateChainCleaner = CertificateChainCleaner.get(x509TrustManager);
      } 
      this.hostnameVerifier = paramBuilder.hostnameVerifier;
      this.certificatePinner = paramBuilder.certificatePinner.withCertificateChainCleaner(this.certificateChainCleaner);
      this.proxyAuthenticator = paramBuilder.proxyAuthenticator;
      this.authenticator = paramBuilder.authenticator;
      this.connectionPool = paramBuilder.connectionPool;
      this.dns = paramBuilder.dns;
      this.followSslRedirects = paramBuilder.followSslRedirects;
      this.followRedirects = paramBuilder.followRedirects;
      this.retryOnConnectionFailure = paramBuilder.retryOnConnectionFailure;
      this.connectTimeout = paramBuilder.connectTimeout;
      this.readTimeout = paramBuilder.readTimeout;
      this.writeTimeout = paramBuilder.writeTimeout;
      this.pingInterval = paramBuilder.pingInterval;
      if (!this.interceptors.contains(null)) {
        if (!this.networkInterceptors.contains(null))
          return; 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Null network interceptor: ");
        stringBuilder1.append(this.networkInterceptors);
        throw new IllegalStateException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Null interceptor: ");
      stringBuilder.append(this.interceptors);
      throw new IllegalStateException(stringBuilder.toString());
    } 
  }
  
  private SSLSocketFactory systemDefaultSslSocketFactory(X509TrustManager paramX509TrustManager) {
    try {
      SSLContext sSLContext = Platform.get().getSSLContext();
      sSLContext.init(null, new TrustManager[] { paramX509TrustManager }, null);
      return sSLContext.getSocketFactory();
    } catch (GeneralSecurityException generalSecurityException) {
      throw Util.assertionError("No System TLS", generalSecurityException);
    } 
  }
  
  private X509TrustManager systemDefaultTrustManager() {
    try {
      TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      trustManagerFactory.init((KeyStore)null);
      TrustManager[] arrayOfTrustManager = trustManagerFactory.getTrustManagers();
      if (arrayOfTrustManager.length == 1 && arrayOfTrustManager[0] instanceof X509TrustManager)
        return (X509TrustManager)arrayOfTrustManager[0]; 
      IllegalStateException illegalStateException = new IllegalStateException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Unexpected default trust managers:");
      stringBuilder.append(Arrays.toString((Object[])arrayOfTrustManager));
      this(stringBuilder.toString());
      throw illegalStateException;
    } catch (GeneralSecurityException generalSecurityException) {
      throw Util.assertionError("No System TLS", generalSecurityException);
    } 
  }
  
  public Authenticator authenticator() {
    return this.authenticator;
  }
  
  public Cache cache() {
    return this.cache;
  }
  
  public CertificatePinner certificatePinner() {
    return this.certificatePinner;
  }
  
  public int connectTimeoutMillis() {
    return this.connectTimeout;
  }
  
  public ConnectionPool connectionPool() {
    return this.connectionPool;
  }
  
  public List<ConnectionSpec> connectionSpecs() {
    return this.connectionSpecs;
  }
  
  public CookieJar cookieJar() {
    return this.cookieJar;
  }
  
  public Dispatcher dispatcher() {
    return this.dispatcher;
  }
  
  public Dns dns() {
    return this.dns;
  }
  
  public EventListener.Factory eventListenerFactory() {
    return this.eventListenerFactory;
  }
  
  public boolean followRedirects() {
    return this.followRedirects;
  }
  
  public boolean followSslRedirects() {
    return this.followSslRedirects;
  }
  
  public HostnameVerifier hostnameVerifier() {
    return this.hostnameVerifier;
  }
  
  public List<Interceptor> interceptors() {
    return this.interceptors;
  }
  
  InternalCache internalCache() {
    InternalCache internalCache;
    Cache cache = this.cache;
    if (cache != null) {
      internalCache = cache.internalCache;
    } else {
      internalCache = this.internalCache;
    } 
    return internalCache;
  }
  
  public List<Interceptor> networkInterceptors() {
    return this.networkInterceptors;
  }
  
  public Builder newBuilder() {
    return new Builder(this);
  }
  
  public Call newCall(Request paramRequest) {
    return RealCall.newRealCall(this, paramRequest, false);
  }
  
  public WebSocket newWebSocket(Request paramRequest, WebSocketListener paramWebSocketListener) {
    RealWebSocket realWebSocket = new RealWebSocket(paramRequest, paramWebSocketListener, new Random(), this.pingInterval);
    realWebSocket.connect(this);
    return (WebSocket)realWebSocket;
  }
  
  public int pingIntervalMillis() {
    return this.pingInterval;
  }
  
  public List<Protocol> protocols() {
    return this.protocols;
  }
  
  public Proxy proxy() {
    return this.proxy;
  }
  
  public Authenticator proxyAuthenticator() {
    return this.proxyAuthenticator;
  }
  
  public ProxySelector proxySelector() {
    return this.proxySelector;
  }
  
  public int readTimeoutMillis() {
    return this.readTimeout;
  }
  
  public boolean retryOnConnectionFailure() {
    return this.retryOnConnectionFailure;
  }
  
  public SocketFactory socketFactory() {
    return this.socketFactory;
  }
  
  public SSLSocketFactory sslSocketFactory() {
    return this.sslSocketFactory;
  }
  
  public int writeTimeoutMillis() {
    return this.writeTimeout;
  }
  
  public static final class Builder {
    Authenticator authenticator;
    
    @Nullable
    Cache cache;
    
    @Nullable
    CertificateChainCleaner certificateChainCleaner;
    
    CertificatePinner certificatePinner;
    
    int connectTimeout;
    
    ConnectionPool connectionPool;
    
    List<ConnectionSpec> connectionSpecs;
    
    CookieJar cookieJar;
    
    Dispatcher dispatcher = new Dispatcher();
    
    Dns dns;
    
    EventListener.Factory eventListenerFactory;
    
    boolean followRedirects;
    
    boolean followSslRedirects;
    
    HostnameVerifier hostnameVerifier;
    
    final List<Interceptor> interceptors = new ArrayList<Interceptor>();
    
    @Nullable
    InternalCache internalCache;
    
    final List<Interceptor> networkInterceptors = new ArrayList<Interceptor>();
    
    int pingInterval;
    
    List<Protocol> protocols;
    
    @Nullable
    Proxy proxy;
    
    Authenticator proxyAuthenticator;
    
    ProxySelector proxySelector;
    
    int readTimeout;
    
    boolean retryOnConnectionFailure;
    
    SocketFactory socketFactory;
    
    @Nullable
    SSLSocketFactory sslSocketFactory;
    
    int writeTimeout;
    
    public Builder() {
      this.protocols = OkHttpClient.DEFAULT_PROTOCOLS;
      this.connectionSpecs = OkHttpClient.DEFAULT_CONNECTION_SPECS;
      this.eventListenerFactory = EventListener.factory(EventListener.NONE);
      this.proxySelector = ProxySelector.getDefault();
      this.cookieJar = CookieJar.NO_COOKIES;
      this.socketFactory = SocketFactory.getDefault();
      this.hostnameVerifier = (HostnameVerifier)OkHostnameVerifier.INSTANCE;
      this.certificatePinner = CertificatePinner.DEFAULT;
      this.proxyAuthenticator = Authenticator.NONE;
      this.authenticator = Authenticator.NONE;
      this.connectionPool = new ConnectionPool();
      this.dns = Dns.SYSTEM;
      this.followSslRedirects = true;
      this.followRedirects = true;
      this.retryOnConnectionFailure = true;
      this.connectTimeout = 10000;
      this.readTimeout = 10000;
      this.writeTimeout = 10000;
      this.pingInterval = 0;
    }
    
    Builder(OkHttpClient param1OkHttpClient) {
      this.proxy = param1OkHttpClient.proxy;
      this.protocols = param1OkHttpClient.protocols;
      this.connectionSpecs = param1OkHttpClient.connectionSpecs;
      this.interceptors.addAll(param1OkHttpClient.interceptors);
      this.networkInterceptors.addAll(param1OkHttpClient.networkInterceptors);
      this.eventListenerFactory = param1OkHttpClient.eventListenerFactory;
      this.proxySelector = param1OkHttpClient.proxySelector;
      this.cookieJar = param1OkHttpClient.cookieJar;
      this.internalCache = param1OkHttpClient.internalCache;
      this.cache = param1OkHttpClient.cache;
      this.socketFactory = param1OkHttpClient.socketFactory;
      this.sslSocketFactory = param1OkHttpClient.sslSocketFactory;
      this.certificateChainCleaner = param1OkHttpClient.certificateChainCleaner;
      this.hostnameVerifier = param1OkHttpClient.hostnameVerifier;
      this.certificatePinner = param1OkHttpClient.certificatePinner;
      this.proxyAuthenticator = param1OkHttpClient.proxyAuthenticator;
      this.authenticator = param1OkHttpClient.authenticator;
      this.connectionPool = param1OkHttpClient.connectionPool;
      this.dns = param1OkHttpClient.dns;
      this.followSslRedirects = param1OkHttpClient.followSslRedirects;
      this.followRedirects = param1OkHttpClient.followRedirects;
      this.retryOnConnectionFailure = param1OkHttpClient.retryOnConnectionFailure;
      this.connectTimeout = param1OkHttpClient.connectTimeout;
      this.readTimeout = param1OkHttpClient.readTimeout;
      this.writeTimeout = param1OkHttpClient.writeTimeout;
      this.pingInterval = param1OkHttpClient.pingInterval;
    }
    
    public Builder addInterceptor(Interceptor param1Interceptor) {
      if (param1Interceptor != null) {
        this.interceptors.add(param1Interceptor);
        return this;
      } 
      throw new IllegalArgumentException("interceptor == null");
    }
    
    public Builder addNetworkInterceptor(Interceptor param1Interceptor) {
      if (param1Interceptor != null) {
        this.networkInterceptors.add(param1Interceptor);
        return this;
      } 
      throw new IllegalArgumentException("interceptor == null");
    }
    
    public Builder authenticator(Authenticator param1Authenticator) {
      if (param1Authenticator != null) {
        this.authenticator = param1Authenticator;
        return this;
      } 
      throw new NullPointerException("authenticator == null");
    }
    
    public OkHttpClient build() {
      return new OkHttpClient(this);
    }
    
    public Builder cache(@Nullable Cache param1Cache) {
      this.cache = param1Cache;
      this.internalCache = null;
      return this;
    }
    
    public Builder certificatePinner(CertificatePinner param1CertificatePinner) {
      if (param1CertificatePinner != null) {
        this.certificatePinner = param1CertificatePinner;
        return this;
      } 
      throw new NullPointerException("certificatePinner == null");
    }
    
    public Builder connectTimeout(long param1Long, TimeUnit param1TimeUnit) {
      this.connectTimeout = Util.checkDuration("timeout", param1Long, param1TimeUnit);
      return this;
    }
    
    public Builder connectionPool(ConnectionPool param1ConnectionPool) {
      if (param1ConnectionPool != null) {
        this.connectionPool = param1ConnectionPool;
        return this;
      } 
      throw new NullPointerException("connectionPool == null");
    }
    
    public Builder connectionSpecs(List<ConnectionSpec> param1List) {
      this.connectionSpecs = Util.immutableList(param1List);
      return this;
    }
    
    public Builder cookieJar(CookieJar param1CookieJar) {
      if (param1CookieJar != null) {
        this.cookieJar = param1CookieJar;
        return this;
      } 
      throw new NullPointerException("cookieJar == null");
    }
    
    public Builder dispatcher(Dispatcher param1Dispatcher) {
      if (param1Dispatcher != null) {
        this.dispatcher = param1Dispatcher;
        return this;
      } 
      throw new IllegalArgumentException("dispatcher == null");
    }
    
    public Builder dns(Dns param1Dns) {
      if (param1Dns != null) {
        this.dns = param1Dns;
        return this;
      } 
      throw new NullPointerException("dns == null");
    }
    
    public Builder eventListener(EventListener param1EventListener) {
      if (param1EventListener != null) {
        this.eventListenerFactory = EventListener.factory(param1EventListener);
        return this;
      } 
      throw new NullPointerException("eventListener == null");
    }
    
    public Builder eventListenerFactory(EventListener.Factory param1Factory) {
      if (param1Factory != null) {
        this.eventListenerFactory = param1Factory;
        return this;
      } 
      throw new NullPointerException("eventListenerFactory == null");
    }
    
    public Builder followRedirects(boolean param1Boolean) {
      this.followRedirects = param1Boolean;
      return this;
    }
    
    public Builder followSslRedirects(boolean param1Boolean) {
      this.followSslRedirects = param1Boolean;
      return this;
    }
    
    public Builder hostnameVerifier(HostnameVerifier param1HostnameVerifier) {
      if (param1HostnameVerifier != null) {
        this.hostnameVerifier = param1HostnameVerifier;
        return this;
      } 
      throw new NullPointerException("hostnameVerifier == null");
    }
    
    public List<Interceptor> interceptors() {
      return this.interceptors;
    }
    
    public List<Interceptor> networkInterceptors() {
      return this.networkInterceptors;
    }
    
    public Builder pingInterval(long param1Long, TimeUnit param1TimeUnit) {
      this.pingInterval = Util.checkDuration("interval", param1Long, param1TimeUnit);
      return this;
    }
    
    public Builder protocols(List<Protocol> param1List) {
      param1List = new ArrayList<Protocol>(param1List);
      if (param1List.contains(Protocol.HTTP_1_1)) {
        if (!param1List.contains(Protocol.HTTP_1_0)) {
          if (!param1List.contains(null)) {
            param1List.remove(Protocol.SPDY_3);
            this.protocols = Collections.unmodifiableList(param1List);
            return this;
          } 
          throw new IllegalArgumentException("protocols must not contain null");
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("protocols must not contain http/1.0: ");
        stringBuilder1.append(param1List);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("protocols doesn't contain http/1.1: ");
      stringBuilder.append(param1List);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder proxy(@Nullable Proxy param1Proxy) {
      this.proxy = param1Proxy;
      return this;
    }
    
    public Builder proxyAuthenticator(Authenticator param1Authenticator) {
      if (param1Authenticator != null) {
        this.proxyAuthenticator = param1Authenticator;
        return this;
      } 
      throw new NullPointerException("proxyAuthenticator == null");
    }
    
    public Builder proxySelector(ProxySelector param1ProxySelector) {
      this.proxySelector = param1ProxySelector;
      return this;
    }
    
    public Builder readTimeout(long param1Long, TimeUnit param1TimeUnit) {
      this.readTimeout = Util.checkDuration("timeout", param1Long, param1TimeUnit);
      return this;
    }
    
    public Builder retryOnConnectionFailure(boolean param1Boolean) {
      this.retryOnConnectionFailure = param1Boolean;
      return this;
    }
    
    void setInternalCache(@Nullable InternalCache param1InternalCache) {
      this.internalCache = param1InternalCache;
      this.cache = null;
    }
    
    public Builder socketFactory(SocketFactory param1SocketFactory) {
      if (param1SocketFactory != null) {
        this.socketFactory = param1SocketFactory;
        return this;
      } 
      throw new NullPointerException("socketFactory == null");
    }
    
    public Builder sslSocketFactory(SSLSocketFactory param1SSLSocketFactory) {
      if (param1SSLSocketFactory != null) {
        this.sslSocketFactory = param1SSLSocketFactory;
        this.certificateChainCleaner = Platform.get().buildCertificateChainCleaner(param1SSLSocketFactory);
        return this;
      } 
      throw new NullPointerException("sslSocketFactory == null");
    }
    
    public Builder sslSocketFactory(SSLSocketFactory param1SSLSocketFactory, X509TrustManager param1X509TrustManager) {
      if (param1SSLSocketFactory != null) {
        if (param1X509TrustManager != null) {
          this.sslSocketFactory = param1SSLSocketFactory;
          this.certificateChainCleaner = CertificateChainCleaner.get(param1X509TrustManager);
          return this;
        } 
        throw new NullPointerException("trustManager == null");
      } 
      throw new NullPointerException("sslSocketFactory == null");
    }
    
    public Builder writeTimeout(long param1Long, TimeUnit param1TimeUnit) {
      this.writeTimeout = Util.checkDuration("timeout", param1Long, param1TimeUnit);
      return this;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\OkHttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */