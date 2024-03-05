package okhttp3;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public final class MediaType {
  private static final Pattern PARAMETER;
  
  private static final String QUOTED = "\"([^\"]*)\"";
  
  private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
  
  private static final Pattern TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
  
  @Nullable
  private final String charset;
  
  private final String mediaType;
  
  private final String subtype;
  
  private final String type;
  
  static {
    PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
  }
  
  private MediaType(String paramString1, String paramString2, String paramString3, @Nullable String paramString4) {
    this.mediaType = paramString1;
    this.type = paramString2;
    this.subtype = paramString3;
    this.charset = paramString4;
  }
  
  @Nullable
  public static MediaType parse(String paramString) {
    String str1;
    Matcher matcher1 = TYPE_SUBTYPE.matcher(paramString);
    if (!matcher1.lookingAt())
      return null; 
    String str3 = matcher1.group(1).toLowerCase(Locale.US);
    String str2 = matcher1.group(2).toLowerCase(Locale.US);
    Matcher matcher3 = PARAMETER.matcher(paramString);
    int i = matcher1.end();
    Matcher matcher2 = null;
    while (i < paramString.length()) {
      String str4;
      matcher3.region(i, paramString.length());
      if (!matcher3.lookingAt())
        return null; 
      String str5 = matcher3.group(1);
      matcher1 = matcher2;
      if (str5 != null)
        if (!str5.equalsIgnoreCase("charset")) {
          matcher1 = matcher2;
        } else {
          str5 = matcher3.group(2);
          if (str5 != null) {
            str4 = str5;
            if (str5.startsWith("'")) {
              str4 = str5;
              if (str5.endsWith("'")) {
                str4 = str5;
                if (str5.length() > 2)
                  str4 = str5.substring(1, str5.length() - 1); 
              } 
            } 
          } else {
            str4 = matcher3.group(3);
          } 
          if (matcher2 != null && !str4.equalsIgnoreCase((String)matcher2))
            return null; 
        }  
      i = matcher3.end();
      str1 = str4;
    } 
    return new MediaType(paramString, str3, str2, str1);
  }
  
  @Nullable
  public Charset charset() {
    return charset(null);
  }
  
  @Nullable
  public Charset charset(@Nullable Charset paramCharset) {
    Charset charset = paramCharset;
    try {
      if (this.charset != null)
        charset = Charset.forName(this.charset); 
    } catch (IllegalArgumentException illegalArgumentException) {
      charset = paramCharset;
    } 
    return charset;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    boolean bool;
    if (paramObject instanceof MediaType && ((MediaType)paramObject).mediaType.equals(this.mediaType)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int hashCode() {
    return this.mediaType.hashCode();
  }
  
  public String subtype() {
    return this.subtype;
  }
  
  public String toString() {
    return this.mediaType;
  }
  
  public String type() {
    return this.type;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\MediaType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */