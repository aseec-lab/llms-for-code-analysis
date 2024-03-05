package okhttp3.internal.tls;

import java.security.cert.X509Certificate;

public interface TrustRootIndex {
  X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\tls\TrustRootIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */