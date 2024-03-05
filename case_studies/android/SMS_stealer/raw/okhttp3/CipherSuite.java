package okhttp3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class CipherSuite {
  private static final Map<String, CipherSuite> INSTANCES;
  
  static final Comparator<String> ORDER_BY_NAME = new Comparator<String>() {
      public int compare(String param1String1, String param1String2) {
        int j = Math.min(param1String1.length(), param1String2.length());
        int i = 4;
        while (true) {
          byte b = -1;
          if (i < j) {
            char c2 = param1String1.charAt(i);
            char c1 = param1String2.charAt(i);
            if (c2 != c1) {
              if (c2 >= c1)
                b = 1; 
              return b;
            } 
            i++;
            continue;
          } 
          j = param1String1.length();
          i = param1String2.length();
          if (j != i) {
            if (j >= i)
              b = 1; 
            return b;
          } 
          return 0;
        } 
      }
    };
  
  public static final CipherSuite TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_CBC_SHA256;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_DSS_WITH_DES_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_CBC_SHA256;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA;
  
  public static final CipherSuite TLS_DHE_RSA_WITH_DES_CBC_SHA;
  
  public static final CipherSuite TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA;
  
  public static final CipherSuite TLS_DH_anon_EXPORT_WITH_RC4_40_MD5;
  
  public static final CipherSuite TLS_DH_anon_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_DH_anon_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_DH_anon_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_DH_anon_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_DH_anon_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_DH_anon_WITH_AES_256_CBC_SHA256;
  
  public static final CipherSuite TLS_DH_anon_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_DH_anon_WITH_DES_CBC_SHA;
  
  public static final CipherSuite TLS_DH_anon_WITH_RC4_128_MD5;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_NULL_SHA;
  
  public static final CipherSuite TLS_ECDHE_ECDSA_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_NULL_SHA;
  
  public static final CipherSuite TLS_ECDHE_RSA_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_NULL_SHA;
  
  public static final CipherSuite TLS_ECDH_ECDSA_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_NULL_SHA;
  
  public static final CipherSuite TLS_ECDH_RSA_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_anon_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_anon_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_ECDH_anon_WITH_NULL_SHA;
  
  public static final CipherSuite TLS_ECDH_anon_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
  
  public static final CipherSuite TLS_FALLBACK_SCSV;
  
  public static final CipherSuite TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5;
  
  public static final CipherSuite TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA;
  
  public static final CipherSuite TLS_KRB5_EXPORT_WITH_RC4_40_MD5;
  
  public static final CipherSuite TLS_KRB5_EXPORT_WITH_RC4_40_SHA;
  
  public static final CipherSuite TLS_KRB5_WITH_3DES_EDE_CBC_MD5;
  
  public static final CipherSuite TLS_KRB5_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_KRB5_WITH_DES_CBC_MD5;
  
  public static final CipherSuite TLS_KRB5_WITH_DES_CBC_SHA;
  
  public static final CipherSuite TLS_KRB5_WITH_RC4_128_MD5;
  
  public static final CipherSuite TLS_KRB5_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_PSK_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_PSK_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_PSK_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_PSK_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_RSA_EXPORT_WITH_DES40_CBC_SHA;
  
  public static final CipherSuite TLS_RSA_EXPORT_WITH_RC4_40_MD5;
  
  public static final CipherSuite TLS_RSA_WITH_3DES_EDE_CBC_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA256;
  
  public static final CipherSuite TLS_RSA_WITH_AES_128_GCM_SHA256;
  
  public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA256;
  
  public static final CipherSuite TLS_RSA_WITH_AES_256_GCM_SHA384;
  
  public static final CipherSuite TLS_RSA_WITH_CAMELLIA_128_CBC_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_CAMELLIA_256_CBC_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_DES_CBC_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_NULL_MD5;
  
  public static final CipherSuite TLS_RSA_WITH_NULL_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_NULL_SHA256;
  
  public static final CipherSuite TLS_RSA_WITH_RC4_128_MD5;
  
  public static final CipherSuite TLS_RSA_WITH_RC4_128_SHA;
  
  public static final CipherSuite TLS_RSA_WITH_SEED_CBC_SHA;
  
  final String javaName;
  
  static {
    INSTANCES = new TreeMap<String, CipherSuite>(ORDER_BY_NAME);
    TLS_RSA_WITH_NULL_MD5 = of("SSL_RSA_WITH_NULL_MD5", 1);
    TLS_RSA_WITH_NULL_SHA = of("SSL_RSA_WITH_NULL_SHA", 2);
    TLS_RSA_EXPORT_WITH_RC4_40_MD5 = of("SSL_RSA_EXPORT_WITH_RC4_40_MD5", 3);
    TLS_RSA_WITH_RC4_128_MD5 = of("SSL_RSA_WITH_RC4_128_MD5", 4);
    TLS_RSA_WITH_RC4_128_SHA = of("SSL_RSA_WITH_RC4_128_SHA", 5);
    TLS_RSA_EXPORT_WITH_DES40_CBC_SHA = of("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", 8);
    TLS_RSA_WITH_DES_CBC_SHA = of("SSL_RSA_WITH_DES_CBC_SHA", 9);
    TLS_RSA_WITH_3DES_EDE_CBC_SHA = of("SSL_RSA_WITH_3DES_EDE_CBC_SHA", 10);
    TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA = of("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", 17);
    TLS_DHE_DSS_WITH_DES_CBC_SHA = of("SSL_DHE_DSS_WITH_DES_CBC_SHA", 18);
    TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = of("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19);
    TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA = of("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", 20);
    TLS_DHE_RSA_WITH_DES_CBC_SHA = of("SSL_DHE_RSA_WITH_DES_CBC_SHA", 21);
    TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = of("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22);
    TLS_DH_anon_EXPORT_WITH_RC4_40_MD5 = of("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", 23);
    TLS_DH_anon_WITH_RC4_128_MD5 = of("SSL_DH_anon_WITH_RC4_128_MD5", 24);
    TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA = of("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", 25);
    TLS_DH_anon_WITH_DES_CBC_SHA = of("SSL_DH_anon_WITH_DES_CBC_SHA", 26);
    TLS_DH_anon_WITH_3DES_EDE_CBC_SHA = of("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", 27);
    TLS_KRB5_WITH_DES_CBC_SHA = of("TLS_KRB5_WITH_DES_CBC_SHA", 30);
    TLS_KRB5_WITH_3DES_EDE_CBC_SHA = of("TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31);
    TLS_KRB5_WITH_RC4_128_SHA = of("TLS_KRB5_WITH_RC4_128_SHA", 32);
    TLS_KRB5_WITH_DES_CBC_MD5 = of("TLS_KRB5_WITH_DES_CBC_MD5", 34);
    TLS_KRB5_WITH_3DES_EDE_CBC_MD5 = of("TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35);
    TLS_KRB5_WITH_RC4_128_MD5 = of("TLS_KRB5_WITH_RC4_128_MD5", 36);
    TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA = of("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38);
    TLS_KRB5_EXPORT_WITH_RC4_40_SHA = of("TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40);
    TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5 = of("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41);
    TLS_KRB5_EXPORT_WITH_RC4_40_MD5 = of("TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43);
    TLS_RSA_WITH_AES_128_CBC_SHA = of("TLS_RSA_WITH_AES_128_CBC_SHA", 47);
    TLS_DHE_DSS_WITH_AES_128_CBC_SHA = of("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50);
    TLS_DHE_RSA_WITH_AES_128_CBC_SHA = of("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51);
    TLS_DH_anon_WITH_AES_128_CBC_SHA = of("TLS_DH_anon_WITH_AES_128_CBC_SHA", 52);
    TLS_RSA_WITH_AES_256_CBC_SHA = of("TLS_RSA_WITH_AES_256_CBC_SHA", 53);
    TLS_DHE_DSS_WITH_AES_256_CBC_SHA = of("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56);
    TLS_DHE_RSA_WITH_AES_256_CBC_SHA = of("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57);
    TLS_DH_anon_WITH_AES_256_CBC_SHA = of("TLS_DH_anon_WITH_AES_256_CBC_SHA", 58);
    TLS_RSA_WITH_NULL_SHA256 = of("TLS_RSA_WITH_NULL_SHA256", 59);
    TLS_RSA_WITH_AES_128_CBC_SHA256 = of("TLS_RSA_WITH_AES_128_CBC_SHA256", 60);
    TLS_RSA_WITH_AES_256_CBC_SHA256 = of("TLS_RSA_WITH_AES_256_CBC_SHA256", 61);
    TLS_DHE_DSS_WITH_AES_128_CBC_SHA256 = of("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64);
    TLS_RSA_WITH_CAMELLIA_128_CBC_SHA = of("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", 65);
    TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA = of("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", 68);
    TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA = of("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", 69);
    TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 = of("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", 103);
    TLS_DHE_DSS_WITH_AES_256_CBC_SHA256 = of("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", 106);
    TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 = of("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", 107);
    TLS_DH_anon_WITH_AES_128_CBC_SHA256 = of("TLS_DH_anon_WITH_AES_128_CBC_SHA256", 108);
    TLS_DH_anon_WITH_AES_256_CBC_SHA256 = of("TLS_DH_anon_WITH_AES_256_CBC_SHA256", 109);
    TLS_RSA_WITH_CAMELLIA_256_CBC_SHA = of("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", 132);
    TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA = of("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", 135);
    TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA = of("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", 136);
    TLS_PSK_WITH_RC4_128_SHA = of("TLS_PSK_WITH_RC4_128_SHA", 138);
    TLS_PSK_WITH_3DES_EDE_CBC_SHA = of("TLS_PSK_WITH_3DES_EDE_CBC_SHA", 139);
    TLS_PSK_WITH_AES_128_CBC_SHA = of("TLS_PSK_WITH_AES_128_CBC_SHA", 140);
    TLS_PSK_WITH_AES_256_CBC_SHA = of("TLS_PSK_WITH_AES_256_CBC_SHA", 141);
    TLS_RSA_WITH_SEED_CBC_SHA = of("TLS_RSA_WITH_SEED_CBC_SHA", 150);
    TLS_RSA_WITH_AES_128_GCM_SHA256 = of("TLS_RSA_WITH_AES_128_GCM_SHA256", 156);
    TLS_RSA_WITH_AES_256_GCM_SHA384 = of("TLS_RSA_WITH_AES_256_GCM_SHA384", 157);
    TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 = of("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", 158);
    TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 = of("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", 159);
    TLS_DHE_DSS_WITH_AES_128_GCM_SHA256 = of("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", 162);
    TLS_DHE_DSS_WITH_AES_256_GCM_SHA384 = of("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", 163);
    TLS_DH_anon_WITH_AES_128_GCM_SHA256 = of("TLS_DH_anon_WITH_AES_128_GCM_SHA256", 166);
    TLS_DH_anon_WITH_AES_256_GCM_SHA384 = of("TLS_DH_anon_WITH_AES_256_GCM_SHA384", 167);
    TLS_EMPTY_RENEGOTIATION_INFO_SCSV = of("TLS_EMPTY_RENEGOTIATION_INFO_SCSV", 255);
    TLS_FALLBACK_SCSV = of("TLS_FALLBACK_SCSV", 22016);
    TLS_ECDH_ECDSA_WITH_NULL_SHA = of("TLS_ECDH_ECDSA_WITH_NULL_SHA", 49153);
    TLS_ECDH_ECDSA_WITH_RC4_128_SHA = of("TLS_ECDH_ECDSA_WITH_RC4_128_SHA", 49154);
    TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA = of("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", 49155);
    TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA = of("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", 49156);
    TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA = of("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", 49157);
    TLS_ECDHE_ECDSA_WITH_NULL_SHA = of("TLS_ECDHE_ECDSA_WITH_NULL_SHA", 49158);
    TLS_ECDHE_ECDSA_WITH_RC4_128_SHA = of("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", 49159);
    TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA = of("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", 49160);
    TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA = of("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", 49161);
    TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA = of("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", 49162);
    TLS_ECDH_RSA_WITH_NULL_SHA = of("TLS_ECDH_RSA_WITH_NULL_SHA", 49163);
    TLS_ECDH_RSA_WITH_RC4_128_SHA = of("TLS_ECDH_RSA_WITH_RC4_128_SHA", 49164);
    TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA = of("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", 49165);
    TLS_ECDH_RSA_WITH_AES_128_CBC_SHA = of("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", 49166);
    TLS_ECDH_RSA_WITH_AES_256_CBC_SHA = of("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", 49167);
    TLS_ECDHE_RSA_WITH_NULL_SHA = of("TLS_ECDHE_RSA_WITH_NULL_SHA", 49168);
    TLS_ECDHE_RSA_WITH_RC4_128_SHA = of("TLS_ECDHE_RSA_WITH_RC4_128_SHA", 49169);
    TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA = of("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", 49170);
    TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = of("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", 49171);
    TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = of("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", 49172);
    TLS_ECDH_anon_WITH_NULL_SHA = of("TLS_ECDH_anon_WITH_NULL_SHA", 49173);
    TLS_ECDH_anon_WITH_RC4_128_SHA = of("TLS_ECDH_anon_WITH_RC4_128_SHA", 49174);
    TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA = of("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", 49175);
    TLS_ECDH_anon_WITH_AES_128_CBC_SHA = of("TLS_ECDH_anon_WITH_AES_128_CBC_SHA", 49176);
    TLS_ECDH_anon_WITH_AES_256_CBC_SHA = of("TLS_ECDH_anon_WITH_AES_256_CBC_SHA", 49177);
    TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = of("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", 49187);
    TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 = of("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", 49188);
    TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256 = of("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", 49189);
    TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384 = of("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", 49190);
    TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 = of("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", 49191);
    TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 = of("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", 49192);
    TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256 = of("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", 49193);
    TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384 = of("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", 49194);
    TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = of("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", 49195);
    TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = of("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", 49196);
    TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256 = of("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", 49197);
    TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384 = of("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", 49198);
    TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = of("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", 49199);
    TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = of("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", 49200);
    TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256 = of("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", 49201);
    TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384 = of("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", 49202);
    TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA = of("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA", 49205);
    TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA = of("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA", 49206);
    TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = of("TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52392);
    TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 = of("TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", 52393);
  }
  
  private CipherSuite(String paramString) {
    if (paramString != null) {
      this.javaName = paramString;
      return;
    } 
    throw null;
  }
  
  public static CipherSuite forJavaName(String paramString) {
    // Byte code:
    //   0: ldc okhttp3/CipherSuite
    //   2: monitorenter
    //   3: getstatic okhttp3/CipherSuite.INSTANCES : Ljava/util/Map;
    //   6: aload_0
    //   7: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   12: checkcast okhttp3/CipherSuite
    //   15: astore_2
    //   16: aload_2
    //   17: astore_1
    //   18: aload_2
    //   19: ifnonnull -> 42
    //   22: new okhttp3/CipherSuite
    //   25: astore_1
    //   26: aload_1
    //   27: aload_0
    //   28: invokespecial <init> : (Ljava/lang/String;)V
    //   31: getstatic okhttp3/CipherSuite.INSTANCES : Ljava/util/Map;
    //   34: aload_0
    //   35: aload_1
    //   36: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   41: pop
    //   42: ldc okhttp3/CipherSuite
    //   44: monitorexit
    //   45: aload_1
    //   46: areturn
    //   47: astore_0
    //   48: ldc okhttp3/CipherSuite
    //   50: monitorexit
    //   51: aload_0
    //   52: athrow
    // Exception table:
    //   from	to	target	type
    //   3	16	47	finally
    //   22	42	47	finally
  }
  
  static List<CipherSuite> forJavaNames(String... paramVarArgs) {
    ArrayList<CipherSuite> arrayList = new ArrayList(paramVarArgs.length);
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++)
      arrayList.add(forJavaName(paramVarArgs[b])); 
    return Collections.unmodifiableList(arrayList);
  }
  
  private static CipherSuite of(String paramString, int paramInt) {
    return forJavaName(paramString);
  }
  
  public String javaName() {
    return this.javaName;
  }
  
  public String toString() {
    return this.javaName;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\CipherSuite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */