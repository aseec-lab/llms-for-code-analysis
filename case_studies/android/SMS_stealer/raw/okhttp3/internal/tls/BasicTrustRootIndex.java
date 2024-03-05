package okhttp3.internal.tls;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public final class BasicTrustRootIndex implements TrustRootIndex {
  private final Map<X500Principal, Set<X509Certificate>> subjectToCaCerts = new LinkedHashMap<X500Principal, Set<X509Certificate>>();
  
  public BasicTrustRootIndex(X509Certificate... paramVarArgs) {
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++) {
      X509Certificate x509Certificate = paramVarArgs[b];
      X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
      Set<X509Certificate> set2 = this.subjectToCaCerts.get(x500Principal);
      Set<X509Certificate> set1 = set2;
      if (set2 == null) {
        set1 = new LinkedHashSet(1);
        this.subjectToCaCerts.put(x500Principal, set1);
      } 
      set1.add(x509Certificate);
    } 
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (paramObject == this)
      return true; 
    if (!(paramObject instanceof BasicTrustRootIndex) || !((BasicTrustRootIndex)paramObject).subjectToCaCerts.equals(this.subjectToCaCerts))
      bool = false; 
    return bool;
  }
  
  public X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate) {
    X500Principal x500Principal = paramX509Certificate.getIssuerX500Principal();
    Set set = this.subjectToCaCerts.get(x500Principal);
    if (set == null)
      return null; 
    for (X509Certificate x509Certificate : set) {
      PublicKey publicKey = x509Certificate.getPublicKey();
      try {
        paramX509Certificate.verify(publicKey);
        return x509Certificate;
      } catch (Exception exception) {}
    } 
    return null;
  }
  
  public int hashCode() {
    return this.subjectToCaCerts.hashCode();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\tls\BasicTrustRootIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */