package okhttp3.internal.platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Protocol;
import okhttp3.internal.Util;

final class Jdk9Platform extends Platform {
  final Method getProtocolMethod;
  
  final Method setProtocolMethod;
  
  Jdk9Platform(Method paramMethod1, Method paramMethod2) {
    this.setProtocolMethod = paramMethod1;
    this.getProtocolMethod = paramMethod2;
  }
  
  public static Jdk9Platform buildIfSupported() {
    try {
      return new Jdk9Platform(SSLParameters.class.getMethod("setApplicationProtocols", new Class[] { String[].class }), SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]));
    } catch (NoSuchMethodException noSuchMethodException) {
      return null;
    } 
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList) {
    try {
      SSLParameters sSLParameters = paramSSLSocket.getSSLParameters();
      paramList = (List)alpnProtocolNames(paramList);
      this.setProtocolMethod.invoke(sSLParameters, new Object[] { paramList.toArray(new String[paramList.size()]) });
      paramSSLSocket.setSSLParameters(sSLParameters);
      return;
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw Util.assertionError("unable to set ssl parameters", invocationTargetException);
  }
  
  @Nullable
  public String getSelectedProtocol(SSLSocket paramSSLSocket) {
    try {
      String str = (String)this.getProtocolMethod.invoke(paramSSLSocket, new Object[0]);
      if (str != null) {
        boolean bool = str.equals("");
        if (!bool)
          return str; 
      } 
      return null;
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw Util.assertionError("unable to get selected protocols", invocationTargetException);
  }
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory) {
    throw new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on JDK 9+");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\platform\Jdk9Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */