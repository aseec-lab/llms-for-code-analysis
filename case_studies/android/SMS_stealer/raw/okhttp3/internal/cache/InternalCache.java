package okhttp3.internal.cache;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

public interface InternalCache {
  Response get(Request paramRequest) throws IOException;
  
  CacheRequest put(Response paramResponse) throws IOException;
  
  void remove(Request paramRequest) throws IOException;
  
  void trackConditionalCacheHit();
  
  void trackResponse(CacheStrategy paramCacheStrategy);
  
  void update(Response paramResponse1, Response paramResponse2);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\cache\InternalCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */