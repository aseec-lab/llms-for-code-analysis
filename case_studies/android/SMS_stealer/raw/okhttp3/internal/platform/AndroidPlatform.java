package okhttp3.internal.platform;

import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

class AndroidPlatform extends Platform {
  private static final int MAX_LOG_LENGTH = 4000;
  
  private final CloseGuard closeGuard = CloseGuard.get();
  
  private final OptionalMethod<Socket> getAlpnSelectedProtocol;
  
  private final OptionalMethod<Socket> setAlpnProtocols;
  
  private final OptionalMethod<Socket> setHostname;
  
  private final OptionalMethod<Socket> setUseSessionTickets;
  
  private final Class<?> sslParametersClass;
  
  AndroidPlatform(Class<?> paramClass, OptionalMethod<Socket> paramOptionalMethod1, OptionalMethod<Socket> paramOptionalMethod2, OptionalMethod<Socket> paramOptionalMethod3, OptionalMethod<Socket> paramOptionalMethod4) {
    this.sslParametersClass = paramClass;
    this.setUseSessionTickets = paramOptionalMethod1;
    this.setHostname = paramOptionalMethod2;
    this.getAlpnSelectedProtocol = paramOptionalMethod3;
    this.setAlpnProtocols = paramOptionalMethod4;
  }
  
  private boolean api23IsCleartextTrafficPermitted(String paramString, Class<?> paramClass, Object paramObject) throws InvocationTargetException, IllegalAccessException {
    try {
      return ((Boolean)paramClass.getMethod("isCleartextTrafficPermitted", new Class[0]).invoke(paramObject, new Object[0])).booleanValue();
    } catch (NoSuchMethodException noSuchMethodException) {
      return super.isCleartextTrafficPermitted(paramString);
    } 
  }
  
  private boolean api24IsCleartextTrafficPermitted(String paramString, Class<?> paramClass, Object paramObject) throws InvocationTargetException, IllegalAccessException {
    try {
      return ((Boolean)paramClass.getMethod("isCleartextTrafficPermitted", new Class[] { String.class }).invoke(paramObject, new Object[] { paramString })).booleanValue();
    } catch (NoSuchMethodException noSuchMethodException) {
      return api23IsCleartextTrafficPermitted(paramString, paramClass, paramObject);
    } 
  }
  
  public static Platform buildIfSupported() {
    // Byte code:
    //   0: ldc 'com.android.org.conscrypt.SSLParametersImpl'
    //   2: invokestatic forName : (Ljava/lang/String;)Ljava/lang/Class;
    //   5: astore_0
    //   6: goto -> 19
    //   9: astore_0
    //   10: ldc 'org.apache.harmony.xnet.provider.jsse.SSLParametersImpl'
    //   12: invokestatic forName : (Ljava/lang/String;)Ljava/lang/Class;
    //   15: astore_0
    //   16: goto -> 6
    //   19: new okhttp3/internal/platform/OptionalMethod
    //   22: astore #4
    //   24: aload #4
    //   26: aconst_null
    //   27: ldc 'setUseSessionTickets'
    //   29: iconst_1
    //   30: anewarray java/lang/Class
    //   33: dup
    //   34: iconst_0
    //   35: getstatic java/lang/Boolean.TYPE : Ljava/lang/Class;
    //   38: aastore
    //   39: invokespecial <init> : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   42: new okhttp3/internal/platform/OptionalMethod
    //   45: astore_3
    //   46: aload_3
    //   47: aconst_null
    //   48: ldc 'setHostname'
    //   50: iconst_1
    //   51: anewarray java/lang/Class
    //   54: dup
    //   55: iconst_0
    //   56: ldc java/lang/String
    //   58: aastore
    //   59: invokespecial <init> : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   62: invokestatic supportsAlpn : ()Z
    //   65: ifeq -> 107
    //   68: new okhttp3/internal/platform/OptionalMethod
    //   71: astore_2
    //   72: aload_2
    //   73: ldc [B
    //   75: ldc 'getAlpnSelectedProtocol'
    //   77: iconst_0
    //   78: anewarray java/lang/Class
    //   81: invokespecial <init> : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   84: new okhttp3/internal/platform/OptionalMethod
    //   87: astore_1
    //   88: aload_1
    //   89: aconst_null
    //   90: ldc 'setAlpnProtocols'
    //   92: iconst_1
    //   93: anewarray java/lang/Class
    //   96: dup
    //   97: iconst_0
    //   98: ldc [B
    //   100: aastore
    //   101: invokespecial <init> : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   104: goto -> 111
    //   107: aconst_null
    //   108: astore_2
    //   109: aconst_null
    //   110: astore_1
    //   111: new okhttp3/internal/platform/AndroidPlatform
    //   114: dup
    //   115: aload_0
    //   116: aload #4
    //   118: aload_3
    //   119: aload_2
    //   120: aload_1
    //   121: invokespecial <init> : (Ljava/lang/Class;Lokhttp3/internal/platform/OptionalMethod;Lokhttp3/internal/platform/OptionalMethod;Lokhttp3/internal/platform/OptionalMethod;Lokhttp3/internal/platform/OptionalMethod;)V
    //   124: astore_0
    //   125: aload_0
    //   126: areturn
    //   127: astore_0
    //   128: aconst_null
    //   129: areturn
    // Exception table:
    //   from	to	target	type
    //   0	6	9	java/lang/ClassNotFoundException
    //   10	16	127	java/lang/ClassNotFoundException
    //   19	104	127	java/lang/ClassNotFoundException
    //   111	125	127	java/lang/ClassNotFoundException
  }
  
  private static boolean supportsAlpn() {
    if (Security.getProvider("GMSCore_OpenSSL") != null)
      return true; 
    try {
      Class.forName("android.net.Network");
      return true;
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    } 
  }
  
  public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager paramX509TrustManager) {
    try {
      Class<?> clazz = Class.forName("android.net.http.X509TrustManagerExtensions");
      return new AndroidCertificateChainCleaner(clazz.getConstructor(new Class[] { X509TrustManager.class }, ).newInstance(new Object[] { paramX509TrustManager }, ), clazz.getMethod("checkServerTrusted", new Class[] { X509Certificate[].class, String.class, String.class }));
    } catch (Exception exception) {
      return super.buildCertificateChainCleaner(paramX509TrustManager);
    } 
  }
  
  public TrustRootIndex buildTrustRootIndex(X509TrustManager paramX509TrustManager) {
    try {
      Method method = paramX509TrustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[] { X509Certificate.class });
      method.setAccessible(true);
      return new AndroidTrustRootIndex(paramX509TrustManager, method);
    } catch (NoSuchMethodException noSuchMethodException) {
      return super.buildTrustRootIndex(paramX509TrustManager);
    } 
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList) {
    if (paramString != null) {
      this.setUseSessionTickets.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { Boolean.valueOf(true) });
      this.setHostname.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { paramString });
    } 
    OptionalMethod<Socket> optionalMethod = this.setAlpnProtocols;
    if (optionalMethod != null && optionalMethod.isSupported(paramSSLSocket)) {
      byte[] arrayOfByte = concatLengthPrefixed(paramList);
      this.setAlpnProtocols.invokeWithoutCheckedException(paramSSLSocket, new Object[] { arrayOfByte });
    } 
  }
  
  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException {
    try {
      paramSocket.connect(paramInetSocketAddress, paramInt);
      return;
    } catch (AssertionError assertionError) {
      if (Util.isAndroidGetsocknameError(assertionError))
        throw new IOException(assertionError); 
      throw assertionError;
    } catch (SecurityException securityException) {
      IOException iOException = new IOException("Exception in connect");
      iOException.initCause(securityException);
      throw iOException;
    } catch (ClassCastException classCastException) {
      if (Build.VERSION.SDK_INT == 26) {
        IOException iOException = new IOException("Exception in connect");
        iOException.initCause(classCastException);
        throw iOException;
      } 
      throw classCastException;
    } 
  }
  
  @Nullable
  public String getSelectedProtocol(SSLSocket paramSSLSocket) {
    String str;
    OptionalMethod<Socket> optionalMethod = this.getAlpnSelectedProtocol;
    SSLSocket sSLSocket = null;
    if (optionalMethod == null)
      return null; 
    if (!optionalMethod.isSupported(paramSSLSocket))
      return null; 
    byte[] arrayOfByte = (byte[])this.getAlpnSelectedProtocol.invokeWithoutCheckedException(paramSSLSocket, new Object[0]);
    paramSSLSocket = sSLSocket;
    if (arrayOfByte != null)
      str = new String(arrayOfByte, Util.UTF_8); 
    return str;
  }
  
  public Object getStackTraceForCloseable(String paramString) {
    return this.closeGuard.createAndOpen(paramString);
  }
  
  public boolean isCleartextTrafficPermitted(String paramString) {
    try {
      Class<?> clazz = Class.forName("android.security.NetworkSecurityPolicy");
      return api24IsCleartextTrafficPermitted(paramString, clazz, clazz.getMethod("getInstance", new Class[0]).invoke((Object)null, new Object[0]));
    } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
      return super.isCleartextTrafficPermitted(paramString);
    } catch (IllegalAccessException illegalAccessException) {
      throw Util.assertionError("unable to determine cleartext support", illegalAccessException);
    } catch (IllegalArgumentException illegalArgumentException) {
      throw Util.assertionError("unable to determine cleartext support", illegalArgumentException);
    } catch (InvocationTargetException invocationTargetException) {
      throw Util.assertionError("unable to determine cleartext support", invocationTargetException);
    } 
  }
  
  public void log(int paramInt, String paramString, Throwable paramThrowable) {
    byte b = 5;
    if (paramInt != 5)
      b = 3; 
    String str = paramString;
    if (paramThrowable != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append('\n');
      stringBuilder.append(Log.getStackTraceString(paramThrowable));
      str = stringBuilder.toString();
    } 
    paramInt = 0;
    int i = str.length();
    label23: while (paramInt < i) {
      int j = str.indexOf('\n', paramInt);
      if (j == -1)
        j = i; 
      while (true) {
        int k = Math.min(j, paramInt + 4000);
        Log.println(b, "OkHttp", str.substring(paramInt, k));
        if (k >= j) {
          paramInt = k + 1;
          continue label23;
        } 
        paramInt = k;
      } 
    } 
  }
  
  public void logCloseableLeak(String paramString, Object paramObject) {
    if (!this.closeGuard.warnIfOpen(paramObject))
      log(5, paramString, (Throwable)null); 
  }
  
  protected X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory) {
    Object object = readFieldOrNull(paramSSLSocketFactory, (Class)this.sslParametersClass, "sslParameters");
    classNotFoundException = (ClassNotFoundException)object;
    if (object == null)
      try {
        classNotFoundException = readFieldOrNull(paramSSLSocketFactory, (Class)Class.forName("com.google.android.gms.org.conscrypt.SSLParametersImpl", false, paramSSLSocketFactory.getClass().getClassLoader()), "sslParameters");
      } catch (ClassNotFoundException classNotFoundException) {
        return super.trustManager(paramSSLSocketFactory);
      }  
    X509TrustManager x509TrustManager = readFieldOrNull(classNotFoundException, X509TrustManager.class, "x509TrustManager");
    return (x509TrustManager != null) ? x509TrustManager : readFieldOrNull(classNotFoundException, X509TrustManager.class, "trustManager");
  }
  
  static final class AndroidCertificateChainCleaner extends CertificateChainCleaner {
    private final Method checkServerTrusted;
    
    private final Object x509TrustManagerExtensions;
    
    AndroidCertificateChainCleaner(Object param1Object, Method param1Method) {
      this.x509TrustManagerExtensions = param1Object;
      this.checkServerTrusted = param1Method;
    }
    
    public List<Certificate> clean(List<Certificate> param1List, String param1String) throws SSLPeerUnverifiedException {
      try {
        X509Certificate[] arrayOfX509Certificate = param1List.<X509Certificate>toArray(new X509Certificate[param1List.size()]);
        return (List)this.checkServerTrusted.invoke(this.x509TrustManagerExtensions, new Object[] { arrayOfX509Certificate, "RSA", param1String });
      } catch (InvocationTargetException invocationTargetException) {
        SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException(invocationTargetException.getMessage());
        sSLPeerUnverifiedException.initCause(invocationTargetException);
        throw sSLPeerUnverifiedException;
      } catch (IllegalAccessException illegalAccessException) {
        throw new AssertionError(illegalAccessException);
      } 
    }
    
    public boolean equals(Object param1Object) {
      return param1Object instanceof AndroidCertificateChainCleaner;
    }
    
    public int hashCode() {
      return 0;
    }
  }
  
  static final class AndroidTrustRootIndex implements TrustRootIndex {
    private final Method findByIssuerAndSignatureMethod;
    
    private final X509TrustManager trustManager;
    
    AndroidTrustRootIndex(X509TrustManager param1X509TrustManager, Method param1Method) {
      this.findByIssuerAndSignatureMethod = param1Method;
      this.trustManager = param1X509TrustManager;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (param1Object == this)
        return true; 
      if (!(param1Object instanceof AndroidTrustRootIndex))
        return false; 
      param1Object = param1Object;
      if (!this.trustManager.equals(((AndroidTrustRootIndex)param1Object).trustManager) || !this.findByIssuerAndSignatureMethod.equals(((AndroidTrustRootIndex)param1Object).findByIssuerAndSignatureMethod))
        bool = false; 
      return bool;
    }
    
    public X509Certificate findByIssuerAndSignature(X509Certificate param1X509Certificate) {
      X509Certificate x509Certificate1;
      X509Certificate x509Certificate2 = null;
      try {
        TrustAnchor trustAnchor = (TrustAnchor)this.findByIssuerAndSignatureMethod.invoke(this.trustManager, new Object[] { param1X509Certificate });
        param1X509Certificate = x509Certificate2;
        if (trustAnchor != null)
          param1X509Certificate = trustAnchor.getTrustedCert(); 
      } catch (IllegalAccessException illegalAccessException) {
        throw Util.assertionError("unable to get issues and signature", illegalAccessException);
      } catch (InvocationTargetException invocationTargetException) {
        x509Certificate1 = x509Certificate2;
      } 
      return x509Certificate1;
    }
    
    public int hashCode() {
      return this.trustManager.hashCode() + this.findByIssuerAndSignatureMethod.hashCode() * 31;
    }
  }
  
  static final class CloseGuard {
    private final Method getMethod;
    
    private final Method openMethod;
    
    private final Method warnIfOpenMethod;
    
    CloseGuard(Method param1Method1, Method param1Method2, Method param1Method3) {
      this.getMethod = param1Method1;
      this.openMethod = param1Method2;
      this.warnIfOpenMethod = param1Method3;
    }
    
    static CloseGuard get() {
      Method method2;
      Method method1 = null;
      try {
        Class<?> clazz = Class.forName("dalvik.system.CloseGuard");
        Method method4 = clazz.getMethod("get", new Class[0]);
        method2 = clazz.getMethod("open", new Class[] { String.class });
        Method method3 = clazz.getMethod("warnIfOpen", new Class[0]);
        method1 = method4;
      } catch (Exception exception) {
        exception = null;
        method2 = null;
      } 
      return new CloseGuard(method1, method2, (Method)exception);
    }
    
    Object createAndOpen(String param1String) {
      Method method = this.getMethod;
      if (method != null)
        try {
          Object object = method.invoke((Object)null, new Object[0]);
          this.openMethod.invoke(object, new Object[] { param1String });
          return object;
        } catch (Exception exception) {} 
      return null;
    }
    
    boolean warnIfOpen(Object param1Object) {
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (param1Object != null)
        try {
          this.warnIfOpenMethod.invoke(param1Object, new Object[0]);
          bool1 = true;
        } catch (Exception exception) {
          bool1 = bool2;
        }  
      return bool1;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\platform\AndroidPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */