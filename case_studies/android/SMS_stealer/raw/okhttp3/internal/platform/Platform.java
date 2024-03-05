package okhttp3.internal.platform;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.BasicTrustRootIndex;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;

public class Platform {
  public static final int INFO = 4;
  
  private static final Platform PLATFORM = findPlatform();
  
  public static final int WARN = 5;
  
  private static final Logger logger = Logger.getLogger(OkHttpClient.class.getName());
  
  public static List<String> alpnProtocolNames(List<Protocol> paramList) {
    ArrayList<String> arrayList = new ArrayList(paramList.size());
    int i = paramList.size();
    for (byte b = 0; b < i; b++) {
      Protocol protocol = paramList.get(b);
      if (protocol != Protocol.HTTP_1_0)
        arrayList.add(protocol.toString()); 
    } 
    return arrayList;
  }
  
  static byte[] concatLengthPrefixed(List<Protocol> paramList) {
    Buffer buffer = new Buffer();
    int i = paramList.size();
    for (byte b = 0; b < i; b++) {
      Protocol protocol = paramList.get(b);
      if (protocol != Protocol.HTTP_1_0) {
        buffer.writeByte(protocol.toString().length());
        buffer.writeUtf8(protocol.toString());
      } 
    } 
    return buffer.readByteArray();
  }
  
  private static Platform findPlatform() {
    Platform platform = AndroidPlatform.buildIfSupported();
    if (platform != null)
      return platform; 
    if (isConscryptPreferred()) {
      platform = ConscryptPlatform.buildIfSupported();
      if (platform != null)
        return platform; 
    } 
    platform = Jdk9Platform.buildIfSupported();
    if (platform != null)
      return platform; 
    platform = JdkWithJettyBootPlatform.buildIfSupported();
    return (platform != null) ? platform : new Platform();
  }
  
  public static Platform get() {
    return PLATFORM;
  }
  
  public static boolean isConscryptPreferred() {
    return "conscrypt".equals(System.getProperty("okhttp.platform")) ? true : "Conscrypt".equals(Security.getProviders()[0].getName());
  }
  
  static <T> T readFieldOrNull(Object paramObject, Class<T> paramClass, String paramString) {
    Class<?> clazz = paramObject.getClass();
    while (clazz != Object.class) {
      try {
        Field field = clazz.getDeclaredField(paramString);
        field.setAccessible(true);
        null = field.get(paramObject);
        return (null == null || !paramClass.isInstance(null)) ? null : paramClass.cast(null);
      } catch (NoSuchFieldException noSuchFieldException) {
        clazz = clazz.getSuperclass();
      } catch (IllegalAccessException illegalAccessException) {
        throw new AssertionError();
      } 
    } 
    if (!paramString.equals("delegate")) {
      illegalAccessException = readFieldOrNull(illegalAccessException, Object.class, "delegate");
      if (illegalAccessException != null)
        return readFieldOrNull(illegalAccessException, paramClass, paramString); 
    } 
    return null;
  }
  
  public void afterHandshake(SSLSocket paramSSLSocket) {}
  
  public CertificateChainCleaner buildCertificateChainCleaner(SSLSocketFactory paramSSLSocketFactory) {
    X509TrustManager x509TrustManager = trustManager(paramSSLSocketFactory);
    if (x509TrustManager != null)
      return buildCertificateChainCleaner(x509TrustManager); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Unable to extract the trust manager on ");
    stringBuilder.append(get());
    stringBuilder.append(", sslSocketFactory is ");
    stringBuilder.append(paramSSLSocketFactory.getClass());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager paramX509TrustManager) {
    return (CertificateChainCleaner)new BasicCertificateChainCleaner(buildTrustRootIndex(paramX509TrustManager));
  }
  
  public TrustRootIndex buildTrustRootIndex(X509TrustManager paramX509TrustManager) {
    return (TrustRootIndex)new BasicTrustRootIndex(paramX509TrustManager.getAcceptedIssuers());
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList) {}
  
  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException {
    paramSocket.connect(paramInetSocketAddress, paramInt);
  }
  
  public String getPrefix() {
    return "OkHttp";
  }
  
  public SSLContext getSSLContext() {
    try {
      return SSLContext.getInstance("TLS");
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new IllegalStateException("No TLS provider", noSuchAlgorithmException);
    } 
  }
  
  @Nullable
  public String getSelectedProtocol(SSLSocket paramSSLSocket) {
    return null;
  }
  
  public Object getStackTraceForCloseable(String paramString) {
    return logger.isLoggable(Level.FINE) ? new Throwable(paramString) : null;
  }
  
  public boolean isCleartextTrafficPermitted(String paramString) {
    return true;
  }
  
  public void log(int paramInt, String paramString, Throwable paramThrowable) {
    Level level;
    if (paramInt == 5) {
      level = Level.WARNING;
    } else {
      level = Level.INFO;
    } 
    logger.log(level, paramString, paramThrowable);
  }
  
  public void logCloseableLeak(String paramString, Object paramObject) {
    String str = paramString;
    if (paramObject == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(" To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);");
      str = stringBuilder.toString();
    } 
    log(5, str, (Throwable)paramObject);
  }
  
  protected X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory) {
    try {
      paramSSLSocketFactory = readFieldOrNull(paramSSLSocketFactory, Class.forName("sun.security.ssl.SSLContextImpl"), "context");
      return (paramSSLSocketFactory == null) ? null : readFieldOrNull(paramSSLSocketFactory, X509TrustManager.class, "trustManager");
    } catch (ClassNotFoundException classNotFoundException) {
      return null;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\platform\Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */