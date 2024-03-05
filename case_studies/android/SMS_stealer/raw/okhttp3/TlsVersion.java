package okhttp3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TlsVersion {
  SSL_3_0,
  TLS_1_0,
  TLS_1_1,
  TLS_1_2,
  TLS_1_3("TLSv1.3");
  
  private static final TlsVersion[] $VALUES;
  
  final String javaName;
  
  static {
    TLS_1_2 = new TlsVersion("TLS_1_2", 1, "TLSv1.2");
    TLS_1_1 = new TlsVersion("TLS_1_1", 2, "TLSv1.1");
    TLS_1_0 = new TlsVersion("TLS_1_0", 3, "TLSv1");
    TlsVersion tlsVersion = new TlsVersion("SSL_3_0", 4, "SSLv3");
    SSL_3_0 = tlsVersion;
    $VALUES = new TlsVersion[] { TLS_1_3, TLS_1_2, TLS_1_1, TLS_1_0, tlsVersion };
  }
  
  TlsVersion(String paramString1) {
    this.javaName = paramString1;
  }
  
  public static TlsVersion forJavaName(String paramString) {
    int i = paramString.hashCode();
    if (i != 79201641) {
      if (i != 79923350) {
        switch (i) {
          default:
            i = -1;
            break;
          case -503070501:
            if (paramString.equals("TLSv1.3")) {
              i = 0;
              break;
            } 
          case -503070502:
            if (paramString.equals("TLSv1.2")) {
              i = 1;
              break;
            } 
          case -503070503:
            if (paramString.equals("TLSv1.1")) {
              i = 2;
              break;
            } 
        } 
      } else if (paramString.equals("TLSv1")) {
        i = 3;
      } else {
      
      } 
    } else if (paramString.equals("SSLv3")) {
      i = 4;
    } else {
    
    } 
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i == 4)
              return SSL_3_0; 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected TLS version: ");
            stringBuilder.append(paramString);
            throw new IllegalArgumentException(stringBuilder.toString());
          } 
          return TLS_1_0;
        } 
        return TLS_1_1;
      } 
      return TLS_1_2;
    } 
    return TLS_1_3;
  }
  
  static List<TlsVersion> forJavaNames(String... paramVarArgs) {
    ArrayList<TlsVersion> arrayList = new ArrayList(paramVarArgs.length);
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++)
      arrayList.add(forJavaName(paramVarArgs[b])); 
    return Collections.unmodifiableList(arrayList);
  }
  
  public String javaName() {
    return this.javaName;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\TlsVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */