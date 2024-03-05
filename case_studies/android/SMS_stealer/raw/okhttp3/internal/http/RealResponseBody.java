package okhttp3.internal.http;

import javax.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public final class RealResponseBody extends ResponseBody {
  private final long contentLength;
  
  @Nullable
  private final String contentTypeString;
  
  private final BufferedSource source;
  
  public RealResponseBody(@Nullable String paramString, long paramLong, BufferedSource paramBufferedSource) {
    this.contentTypeString = paramString;
    this.contentLength = paramLong;
    this.source = paramBufferedSource;
  }
  
  public long contentLength() {
    return this.contentLength;
  }
  
  public MediaType contentType() {
    String str = this.contentTypeString;
    if (str != null) {
      MediaType mediaType = MediaType.parse(str);
    } else {
      str = null;
    } 
    return (MediaType)str;
  }
  
  public BufferedSource source() {
    return this.source;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\RealResponseBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */