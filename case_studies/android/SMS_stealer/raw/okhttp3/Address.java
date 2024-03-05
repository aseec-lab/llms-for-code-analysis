package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.internal.Util;

public final class Address {
  @Nullable
  final CertificatePinner certificatePinner;
  
  final List<ConnectionSpec> connectionSpecs;
  
  final Dns dns;
  
  @Nullable
  final HostnameVerifier hostnameVerifier;
  
  final List<Protocol> protocols;
  
  @Nullable
  final Proxy proxy;
  
  final Authenticator proxyAuthenticator;
  
  final ProxySelector proxySelector;
  
  final SocketFactory socketFactory;
  
  @Nullable
  final SSLSocketFactory sslSocketFactory;
  
  final HttpUrl url;
  
  public Address(String paramString, int paramInt, Dns paramDns, SocketFactory paramSocketFactory, @Nullable SSLSocketFactory paramSSLSocketFactory, @Nullable HostnameVerifier paramHostnameVerifier, @Nullable CertificatePinner paramCertificatePinner, Authenticator paramAuthenticator, @Nullable Proxy paramProxy, List<Protocol> paramList, List<ConnectionSpec> paramList1, ProxySelector paramProxySelector) {
    String str;
    HttpUrl.Builder builder = new HttpUrl.Builder();
    if (paramSSLSocketFactory != null) {
      str = "https";
    } else {
      str = "http";
    } 
    this.url = builder.scheme(str).host(paramString).port(paramInt).build();
    if (paramDns != null) {
      this.dns = paramDns;
      if (paramSocketFactory != null) {
        this.socketFactory = paramSocketFactory;
        if (paramAuthenticator != null) {
          this.proxyAuthenticator = paramAuthenticator;
          if (paramList != null) {
            this.protocols = Util.immutableList(paramList);
            if (paramList1 != null) {
              this.connectionSpecs = Util.immutableList(paramList1);
              if (paramProxySelector != null) {
                this.proxySelector = paramProxySelector;
                this.proxy = paramProxy;
                this.sslSocketFactory = paramSSLSocketFactory;
                this.hostnameVerifier = paramHostnameVerifier;
                this.certificatePinner = paramCertificatePinner;
                return;
              } 
              throw new NullPointerException("proxySelector == null");
            } 
            throw new NullPointerException("connectionSpecs == null");
          } 
          throw new NullPointerException("protocols == null");
        } 
        throw new NullPointerException("proxyAuthenticator == null");
      } 
      throw new NullPointerException("socketFactory == null");
    } 
    throw new NullPointerException("dns == null");
  }
  
  @Nullable
  public CertificatePinner certificatePinner() {
    return this.certificatePinner;
  }
  
  public List<ConnectionSpec> connectionSpecs() {
    return this.connectionSpecs;
  }
  
  public Dns dns() {
    return this.dns;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    if (paramObject instanceof Address) {
      HttpUrl httpUrl = this.url;
      paramObject = paramObject;
      if (httpUrl.equals(((Address)paramObject).url) && equalsNonHost((Address)paramObject))
        return true; 
    } 
    return false;
  }
  
  boolean equalsNonHost(Address paramAddress) {
    boolean bool;
    if (this.dns.equals(paramAddress.dns) && this.proxyAuthenticator.equals(paramAddress.proxyAuthenticator) && this.protocols.equals(paramAddress.protocols) && this.connectionSpecs.equals(paramAddress.connectionSpecs) && this.proxySelector.equals(paramAddress.proxySelector) && Util.equal(this.proxy, paramAddress.proxy) && Util.equal(this.sslSocketFactory, paramAddress.sslSocketFactory) && Util.equal(this.hostnameVerifier, paramAddress.hostnameVerifier) && Util.equal(this.certificatePinner, paramAddress.certificatePinner) && url().port() == paramAddress.url().port()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int hashCode() {
    byte b1;
    byte b2;
    byte b3;
    int k = this.url.hashCode();
    int i2 = this.dns.hashCode();
    int j = this.proxyAuthenticator.hashCode();
    int n = this.protocols.hashCode();
    int m = this.connectionSpecs.hashCode();
    int i1 = this.proxySelector.hashCode();
    Proxy proxy = this.proxy;
    int i = 0;
    if (proxy != null) {
      b1 = proxy.hashCode();
    } else {
      b1 = 0;
    } 
    SSLSocketFactory sSLSocketFactory = this.sslSocketFactory;
    if (sSLSocketFactory != null) {
      b2 = sSLSocketFactory.hashCode();
    } else {
      b2 = 0;
    } 
    HostnameVerifier hostnameVerifier = this.hostnameVerifier;
    if (hostnameVerifier != null) {
      b3 = hostnameVerifier.hashCode();
    } else {
      b3 = 0;
    } 
    CertificatePinner certificatePinner = this.certificatePinner;
    if (certificatePinner != null)
      i = certificatePinner.hashCode(); 
    return (((((((((527 + k) * 31 + i2) * 31 + j) * 31 + n) * 31 + m) * 31 + i1) * 31 + b1) * 31 + b2) * 31 + b3) * 31 + i;
  }
  
  @Nullable
  public HostnameVerifier hostnameVerifier() {
    return this.hostnameVerifier;
  }
  
  public List<Protocol> protocols() {
    return this.protocols;
  }
  
  @Nullable
  public Proxy proxy() {
    return this.proxy;
  }
  
  public Authenticator proxyAuthenticator() {
    return this.proxyAuthenticator;
  }
  
  public ProxySelector proxySelector() {
    return this.proxySelector;
  }
  
  public SocketFactory socketFactory() {
    return this.socketFactory;
  }
  
  @Nullable
  public SSLSocketFactory sslSocketFactory() {
    return this.sslSocketFactory;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Address{");
    stringBuilder.append(this.url.host());
    stringBuilder.append(":");
    stringBuilder.append(this.url.port());
    if (this.proxy != null) {
      stringBuilder.append(", proxy=");
      stringBuilder.append(this.proxy);
    } else {
      stringBuilder.append(", proxySelector=");
      stringBuilder.append(this.proxySelector);
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public HttpUrl url() {
    return this.url;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Address.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */