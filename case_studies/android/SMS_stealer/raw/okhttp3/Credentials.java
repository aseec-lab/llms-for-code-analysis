package okhttp3;

import java.nio.charset.Charset;
import okhttp3.internal.Util;
import okio.ByteString;

public final class Credentials {
  public static String basic(String paramString1, String paramString2) {
    return basic(paramString1, paramString2, Util.ISO_8859_1);
  }
  
  public static String basic(String paramString1, String paramString2, Charset paramCharset) {
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString1);
    stringBuilder2.append(":");
    stringBuilder2.append(paramString2);
    paramString1 = ByteString.encodeString(stringBuilder2.toString(), paramCharset).base64();
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Basic ");
    stringBuilder1.append(paramString1);
    return stringBuilder1.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Credentials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */