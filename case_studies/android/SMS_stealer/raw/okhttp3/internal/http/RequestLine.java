package okhttp3.internal.http;

import java.net.Proxy;
import okhttp3.HttpUrl;
import okhttp3.Request;

public final class RequestLine {
  public static String get(Request paramRequest, Proxy.Type paramType) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramRequest.method());
    stringBuilder.append(' ');
    if (includeAuthorityInRequestLine(paramRequest, paramType)) {
      stringBuilder.append(paramRequest.url());
    } else {
      stringBuilder.append(requestPath(paramRequest.url()));
    } 
    stringBuilder.append(" HTTP/1.1");
    return stringBuilder.toString();
  }
  
  private static boolean includeAuthorityInRequestLine(Request paramRequest, Proxy.Type paramType) {
    boolean bool;
    if (!paramRequest.isHttps() && paramType == Proxy.Type.HTTP) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static String requestPath(HttpUrl paramHttpUrl) {
    String str2 = paramHttpUrl.encodedPath();
    String str3 = paramHttpUrl.encodedQuery();
    String str1 = str2;
    if (str3 != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append('?');
      stringBuilder.append(str3);
      str1 = stringBuilder.toString();
    } 
    return str1;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\RequestLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */