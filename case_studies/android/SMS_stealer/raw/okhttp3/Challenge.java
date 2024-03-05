package okhttp3;

import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

public final class Challenge {
  private final Charset charset;
  
  private final String realm;
  
  private final String scheme;
  
  public Challenge(String paramString1, String paramString2) {
    this(paramString1, paramString2, Util.ISO_8859_1);
  }
  
  private Challenge(String paramString1, String paramString2, Charset paramCharset) {
    if (paramString1 != null) {
      if (paramString2 != null) {
        if (paramCharset != null) {
          this.scheme = paramString1;
          this.realm = paramString2;
          this.charset = paramCharset;
          return;
        } 
        throw new NullPointerException("charset == null");
      } 
      throw new NullPointerException("realm == null");
    } 
    throw new NullPointerException("scheme == null");
  }
  
  public Charset charset() {
    return this.charset;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    if (paramObject instanceof Challenge) {
      paramObject = paramObject;
      if (((Challenge)paramObject).scheme.equals(this.scheme) && ((Challenge)paramObject).realm.equals(this.realm) && ((Challenge)paramObject).charset.equals(this.charset))
        return true; 
    } 
    return false;
  }
  
  public int hashCode() {
    return ((899 + this.realm.hashCode()) * 31 + this.scheme.hashCode()) * 31 + this.charset.hashCode();
  }
  
  public String realm() {
    return this.realm;
  }
  
  public String scheme() {
    return this.scheme;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.scheme);
    stringBuilder.append(" realm=\"");
    stringBuilder.append(this.realm);
    stringBuilder.append("\" charset=\"");
    stringBuilder.append(this.charset);
    stringBuilder.append("\"");
    return stringBuilder.toString();
  }
  
  public Challenge withCharset(Charset paramCharset) {
    return new Challenge(this.scheme, this.realm, paramCharset);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Challenge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */