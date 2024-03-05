package okhttp3;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.internal.Util;

public final class ConnectionSpec {
  private static final CipherSuite[] APPROVED_CIPHER_SUITES = new CipherSuite[] { 
      CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, 
      CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA };
  
  public static final ConnectionSpec CLEARTEXT;
  
  public static final ConnectionSpec COMPATIBLE_TLS;
  
  public static final ConnectionSpec MODERN_TLS = (new Builder(true)).cipherSuites(APPROVED_CIPHER_SUITES).tlsVersions(new TlsVersion[] { TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0 }).supportsTlsExtensions(true).build();
  
  @Nullable
  final String[] cipherSuites;
  
  final boolean supportsTlsExtensions;
  
  final boolean tls;
  
  @Nullable
  final String[] tlsVersions;
  
  static {
    COMPATIBLE_TLS = (new Builder(MODERN_TLS)).tlsVersions(new TlsVersion[] { TlsVersion.TLS_1_0 }).supportsTlsExtensions(true).build();
    CLEARTEXT = (new Builder(false)).build();
  }
  
  ConnectionSpec(Builder paramBuilder) {
    this.tls = paramBuilder.tls;
    this.cipherSuites = paramBuilder.cipherSuites;
    this.tlsVersions = paramBuilder.tlsVersions;
    this.supportsTlsExtensions = paramBuilder.supportsTlsExtensions;
  }
  
  private ConnectionSpec supportedSpec(SSLSocket paramSSLSocket, boolean paramBoolean) {
    String[] arrayOfString2;
    String[] arrayOfString3;
    if (this.cipherSuites != null) {
      arrayOfString2 = Util.intersect(CipherSuite.ORDER_BY_NAME, paramSSLSocket.getEnabledCipherSuites(), this.cipherSuites);
    } else {
      arrayOfString2 = paramSSLSocket.getEnabledCipherSuites();
    } 
    if (this.tlsVersions != null) {
      arrayOfString3 = Util.intersect(Util.NATURAL_ORDER, paramSSLSocket.getEnabledProtocols(), this.tlsVersions);
    } else {
      arrayOfString3 = paramSSLSocket.getEnabledProtocols();
    } 
    String[] arrayOfString4 = paramSSLSocket.getSupportedCipherSuites();
    int i = Util.indexOf(CipherSuite.ORDER_BY_NAME, arrayOfString4, "TLS_FALLBACK_SCSV");
    String[] arrayOfString1 = arrayOfString2;
    if (paramBoolean) {
      arrayOfString1 = arrayOfString2;
      if (i != -1)
        arrayOfString1 = Util.concat(arrayOfString2, arrayOfString4[i]); 
    } 
    return (new Builder(this)).cipherSuites(arrayOfString1).tlsVersions(arrayOfString3).build();
  }
  
  void apply(SSLSocket paramSSLSocket, boolean paramBoolean) {
    ConnectionSpec connectionSpec = supportedSpec(paramSSLSocket, paramBoolean);
    String[] arrayOfString = connectionSpec.tlsVersions;
    if (arrayOfString != null)
      paramSSLSocket.setEnabledProtocols(arrayOfString); 
    arrayOfString = connectionSpec.cipherSuites;
    if (arrayOfString != null)
      paramSSLSocket.setEnabledCipherSuites(arrayOfString); 
  }
  
  @Nullable
  public List<CipherSuite> cipherSuites() {
    String[] arrayOfString = this.cipherSuites;
    if (arrayOfString != null) {
      List<CipherSuite> list = CipherSuite.forJavaNames(arrayOfString);
    } else {
      arrayOfString = null;
    } 
    return (List<CipherSuite>)arrayOfString;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    if (!(paramObject instanceof ConnectionSpec))
      return false; 
    if (paramObject == this)
      return true; 
    paramObject = paramObject;
    boolean bool = this.tls;
    if (bool != ((ConnectionSpec)paramObject).tls)
      return false; 
    if (bool) {
      if (!Arrays.equals((Object[])this.cipherSuites, (Object[])((ConnectionSpec)paramObject).cipherSuites))
        return false; 
      if (!Arrays.equals((Object[])this.tlsVersions, (Object[])((ConnectionSpec)paramObject).tlsVersions))
        return false; 
      if (this.supportsTlsExtensions != ((ConnectionSpec)paramObject).supportsTlsExtensions)
        return false; 
    } 
    return true;
  }
  
  public int hashCode() {
    byte b;
    if (this.tls) {
      b = ((527 + Arrays.hashCode((Object[])this.cipherSuites)) * 31 + Arrays.hashCode((Object[])this.tlsVersions)) * 31 + (this.supportsTlsExtensions ^ true);
    } else {
      b = 17;
    } 
    return b;
  }
  
  public boolean isCompatible(SSLSocket paramSSLSocket) {
    return !this.tls ? false : ((this.tlsVersions != null && !Util.nonEmptyIntersection(Util.NATURAL_ORDER, this.tlsVersions, paramSSLSocket.getEnabledProtocols())) ? false : (!(this.cipherSuites != null && !Util.nonEmptyIntersection(CipherSuite.ORDER_BY_NAME, this.cipherSuites, paramSSLSocket.getEnabledCipherSuites()))));
  }
  
  public boolean isTls() {
    return this.tls;
  }
  
  public boolean supportsTlsExtensions() {
    return this.supportsTlsExtensions;
  }
  
  @Nullable
  public List<TlsVersion> tlsVersions() {
    String[] arrayOfString = this.tlsVersions;
    if (arrayOfString != null) {
      List<TlsVersion> list = TlsVersion.forJavaNames(arrayOfString);
    } else {
      arrayOfString = null;
    } 
    return (List<TlsVersion>)arrayOfString;
  }
  
  public String toString() {
    String str1;
    if (!this.tls)
      return "ConnectionSpec()"; 
    String[] arrayOfString = this.cipherSuites;
    String str2 = "[all enabled]";
    if (arrayOfString != null) {
      str1 = cipherSuites().toString();
    } else {
      str1 = "[all enabled]";
    } 
    if (this.tlsVersions != null)
      str2 = tlsVersions().toString(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("ConnectionSpec(cipherSuites=");
    stringBuilder.append(str1);
    stringBuilder.append(", tlsVersions=");
    stringBuilder.append(str2);
    stringBuilder.append(", supportsTlsExtensions=");
    stringBuilder.append(this.supportsTlsExtensions);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public static final class Builder {
    @Nullable
    String[] cipherSuites;
    
    boolean supportsTlsExtensions;
    
    boolean tls;
    
    @Nullable
    String[] tlsVersions;
    
    public Builder(ConnectionSpec param1ConnectionSpec) {
      this.tls = param1ConnectionSpec.tls;
      this.cipherSuites = param1ConnectionSpec.cipherSuites;
      this.tlsVersions = param1ConnectionSpec.tlsVersions;
      this.supportsTlsExtensions = param1ConnectionSpec.supportsTlsExtensions;
    }
    
    Builder(boolean param1Boolean) {
      this.tls = param1Boolean;
    }
    
    public Builder allEnabledCipherSuites() {
      if (this.tls) {
        this.cipherSuites = null;
        return this;
      } 
      throw new IllegalStateException("no cipher suites for cleartext connections");
    }
    
    public Builder allEnabledTlsVersions() {
      if (this.tls) {
        this.tlsVersions = null;
        return this;
      } 
      throw new IllegalStateException("no TLS versions for cleartext connections");
    }
    
    public ConnectionSpec build() {
      return new ConnectionSpec(this);
    }
    
    public Builder cipherSuites(String... param1VarArgs) {
      if (this.tls) {
        if (param1VarArgs.length != 0) {
          this.cipherSuites = (String[])param1VarArgs.clone();
          return this;
        } 
        throw new IllegalArgumentException("At least one cipher suite is required");
      } 
      throw new IllegalStateException("no cipher suites for cleartext connections");
    }
    
    public Builder cipherSuites(CipherSuite... param1VarArgs) {
      if (this.tls) {
        String[] arrayOfString = new String[param1VarArgs.length];
        for (byte b = 0; b < param1VarArgs.length; b++)
          arrayOfString[b] = (param1VarArgs[b]).javaName; 
        return cipherSuites(arrayOfString);
      } 
      throw new IllegalStateException("no cipher suites for cleartext connections");
    }
    
    public Builder supportsTlsExtensions(boolean param1Boolean) {
      if (this.tls) {
        this.supportsTlsExtensions = param1Boolean;
        return this;
      } 
      throw new IllegalStateException("no TLS extensions for cleartext connections");
    }
    
    public Builder tlsVersions(String... param1VarArgs) {
      if (this.tls) {
        if (param1VarArgs.length != 0) {
          this.tlsVersions = (String[])param1VarArgs.clone();
          return this;
        } 
        throw new IllegalArgumentException("At least one TLS version is required");
      } 
      throw new IllegalStateException("no TLS versions for cleartext connections");
    }
    
    public Builder tlsVersions(TlsVersion... param1VarArgs) {
      if (this.tls) {
        String[] arrayOfString = new String[param1VarArgs.length];
        for (byte b = 0; b < param1VarArgs.length; b++)
          arrayOfString[b] = (param1VarArgs[b]).javaName; 
        return tlsVersions(arrayOfString);
      } 
      throw new IllegalStateException("no TLS versions for cleartext connections");
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\ConnectionSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */