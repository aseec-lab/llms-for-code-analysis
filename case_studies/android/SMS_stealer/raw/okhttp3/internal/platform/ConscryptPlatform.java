package okhttp3.internal.platform;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Protocol;
import org.conscrypt.Conscrypt;
import org.conscrypt.OpenSSLProvider;

public class ConscryptPlatform extends Platform {
  public static Platform buildIfSupported() {
    try {
      Class.forName("org.conscrypt.ConscryptEngineSocket");
      if (!Conscrypt.isAvailable())
        return null; 
      Conscrypt.setUseEngineSocketByDefault(true);
      return new ConscryptPlatform();
    } catch (ClassNotFoundException classNotFoundException) {
      return null;
    } 
  }
  
  private Provider getProvider() {
    return (Provider)new OpenSSLProvider();
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList) {
    if (Conscrypt.isConscrypt(paramSSLSocket)) {
      if (paramString != null) {
        Conscrypt.setUseSessionTickets(paramSSLSocket, true);
        Conscrypt.setHostname(paramSSLSocket, paramString);
      } 
      Conscrypt.setApplicationProtocols(paramSSLSocket, Platform.alpnProtocolNames(paramList).<String>toArray(new String[0]));
    } else {
      super.configureTlsExtensions(paramSSLSocket, paramString, paramList);
    } 
  }
  
  public SSLContext getSSLContext() {
    try {
      return SSLContext.getInstance("TLS", getProvider());
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new IllegalStateException("No TLS provider", noSuchAlgorithmException);
    } 
  }
  
  @Nullable
  public String getSelectedProtocol(SSLSocket paramSSLSocket) {
    return Conscrypt.isConscrypt(paramSSLSocket) ? Conscrypt.getApplicationProtocol(paramSSLSocket) : super.getSelectedProtocol(paramSSLSocket);
  }
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory) {
    if (!Conscrypt.isConscrypt(paramSSLSocketFactory))
      return super.trustManager(paramSSLSocketFactory); 
    try {
      paramSSLSocketFactory = readFieldOrNull(paramSSLSocketFactory, Object.class, "sslParameters");
      return (paramSSLSocketFactory != null) ? readFieldOrNull(paramSSLSocketFactory, X509TrustManager.class, "x509TrustManager") : null;
    } catch (Exception exception) {
      throw new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on Conscrypt", exception);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\platform\ConscryptPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */