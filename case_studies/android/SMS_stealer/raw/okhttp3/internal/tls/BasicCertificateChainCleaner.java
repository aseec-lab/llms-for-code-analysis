package okhttp3.internal.tls;

import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class BasicCertificateChainCleaner extends CertificateChainCleaner {
  private static final int MAX_SIGNERS = 9;
  
  private final TrustRootIndex trustRootIndex;
  
  public BasicCertificateChainCleaner(TrustRootIndex paramTrustRootIndex) {
    this.trustRootIndex = paramTrustRootIndex;
  }
  
  private boolean verifySignature(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2) {
    if (!paramX509Certificate1.getIssuerDN().equals(paramX509Certificate2.getSubjectDN()))
      return false; 
    try {
      paramX509Certificate1.verify(paramX509Certificate2.getPublicKey());
      return true;
    } catch (GeneralSecurityException generalSecurityException) {
      return false;
    } 
  }
  
  public List<Certificate> clean(List<Certificate> paramList, String paramString) throws SSLPeerUnverifiedException {
    StringBuilder stringBuilder2;
    ArrayDeque<Certificate> arrayDeque = new ArrayDeque<Certificate>(paramList);
    ArrayList<X509Certificate> arrayList = new ArrayList();
    arrayList.add(arrayDeque.removeFirst());
    byte b = 0;
    boolean bool = false;
    while (b < 9) {
      X509Certificate x509Certificate1 = arrayList.get(arrayList.size() - 1);
      X509Certificate x509Certificate2 = this.trustRootIndex.findByIssuerAndSignature(x509Certificate1);
      if (x509Certificate2 != null) {
        if (arrayList.size() > 1 || !x509Certificate1.equals(x509Certificate2))
          arrayList.add(x509Certificate2); 
        if (verifySignature(x509Certificate2, x509Certificate2))
          return (List)arrayList; 
        bool = true;
      } else {
        Iterator<Certificate> iterator = arrayDeque.iterator();
        while (iterator.hasNext()) {
          X509Certificate x509Certificate = (X509Certificate)iterator.next();
          if (verifySignature(x509Certificate1, x509Certificate)) {
            iterator.remove();
            arrayList.add(x509Certificate);
            continue;
          } 
        } 
        if (bool)
          return (List)arrayList; 
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Failed to find a trusted cert that signed ");
        stringBuilder2.append(x509Certificate1);
        throw new SSLPeerUnverifiedException(stringBuilder2.toString());
      } 
      b++;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Certificate chain too long: ");
    stringBuilder1.append(stringBuilder2);
    throw new SSLPeerUnverifiedException(stringBuilder1.toString());
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (paramObject == this)
      return true; 
    if (!(paramObject instanceof BasicCertificateChainCleaner) || !((BasicCertificateChainCleaner)paramObject).trustRootIndex.equals(this.trustRootIndex))
      bool = false; 
    return bool;
  }
  
  public int hashCode() {
    return this.trustRootIndex.hashCode();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\tls\BasicCertificateChainCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */