package okhttp3;

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import okhttp3.internal.Util;

public final class Handshake {
  private final CipherSuite cipherSuite;
  
  private final List<Certificate> localCertificates;
  
  private final List<Certificate> peerCertificates;
  
  private final TlsVersion tlsVersion;
  
  private Handshake(TlsVersion paramTlsVersion, CipherSuite paramCipherSuite, List<Certificate> paramList1, List<Certificate> paramList2) {
    this.tlsVersion = paramTlsVersion;
    this.cipherSuite = paramCipherSuite;
    this.peerCertificates = paramList1;
    this.localCertificates = paramList2;
  }
  
  public static Handshake get(SSLSession paramSSLSession) {
    String str = paramSSLSession.getCipherSuite();
    if (str != null) {
      CipherSuite cipherSuite = CipherSuite.forJavaName(str);
      str = paramSSLSession.getProtocol();
      if (str != null) {
        List<?> list1;
        List<?> list2;
        TlsVersion tlsVersion = TlsVersion.forJavaName(str);
        try {
          Certificate[] arrayOfCertificate1 = paramSSLSession.getPeerCertificates();
        } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
          sSLPeerUnverifiedException = null;
        } 
        if (sSLPeerUnverifiedException != null) {
          list2 = Util.immutableList((Object[])sSLPeerUnverifiedException);
        } else {
          list2 = Collections.emptyList();
        } 
        Certificate[] arrayOfCertificate = paramSSLSession.getLocalCertificates();
        if (arrayOfCertificate != null) {
          list1 = Util.immutableList((Object[])arrayOfCertificate);
        } else {
          list1 = Collections.emptyList();
        } 
        return new Handshake(tlsVersion, cipherSuite, (List)list2, (List)list1);
      } 
      throw new IllegalStateException("tlsVersion == null");
    } 
    throw new IllegalStateException("cipherSuite == null");
  }
  
  public static Handshake get(TlsVersion paramTlsVersion, CipherSuite paramCipherSuite, List<Certificate> paramList1, List<Certificate> paramList2) {
    if (paramTlsVersion != null) {
      if (paramCipherSuite != null)
        return new Handshake(paramTlsVersion, paramCipherSuite, Util.immutableList(paramList1), Util.immutableList(paramList2)); 
      throw new NullPointerException("cipherSuite == null");
    } 
    throw new NullPointerException("tlsVersion == null");
  }
  
  public CipherSuite cipherSuite() {
    return this.cipherSuite;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    boolean bool = paramObject instanceof Handshake;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    bool = bool1;
    if (this.tlsVersion.equals(((Handshake)paramObject).tlsVersion)) {
      bool = bool1;
      if (this.cipherSuite.equals(((Handshake)paramObject).cipherSuite)) {
        bool = bool1;
        if (this.peerCertificates.equals(((Handshake)paramObject).peerCertificates)) {
          bool = bool1;
          if (this.localCertificates.equals(((Handshake)paramObject).localCertificates))
            bool = true; 
        } 
      } 
    } 
    return bool;
  }
  
  public int hashCode() {
    return (((527 + this.tlsVersion.hashCode()) * 31 + this.cipherSuite.hashCode()) * 31 + this.peerCertificates.hashCode()) * 31 + this.localCertificates.hashCode();
  }
  
  public List<Certificate> localCertificates() {
    return this.localCertificates;
  }
  
  @Nullable
  public Principal localPrincipal() {
    Principal principal;
    if (!this.localCertificates.isEmpty()) {
      principal = ((X509Certificate)this.localCertificates.get(0)).getSubjectX500Principal();
    } else {
      principal = null;
    } 
    return principal;
  }
  
  public List<Certificate> peerCertificates() {
    return this.peerCertificates;
  }
  
  @Nullable
  public Principal peerPrincipal() {
    Principal principal;
    if (!this.peerCertificates.isEmpty()) {
      principal = ((X509Certificate)this.peerCertificates.get(0)).getSubjectX500Principal();
    } else {
      principal = null;
    } 
    return principal;
  }
  
  public TlsVersion tlsVersion() {
    return this.tlsVersion;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Handshake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */