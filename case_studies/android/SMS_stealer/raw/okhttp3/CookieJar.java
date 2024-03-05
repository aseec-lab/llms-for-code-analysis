package okhttp3;

import java.util.Collections;
import java.util.List;

public interface CookieJar {
  public static final CookieJar NO_COOKIES = new CookieJar() {
      public List<Cookie> loadForRequest(HttpUrl param1HttpUrl) {
        return Collections.emptyList();
      }
      
      public void saveFromResponse(HttpUrl param1HttpUrl, List<Cookie> param1List) {}
    };
  
  List<Cookie> loadForRequest(HttpUrl paramHttpUrl);
  
  void saveFromResponse(HttpUrl paramHttpUrl, List<Cookie> paramList);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\CookieJar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */