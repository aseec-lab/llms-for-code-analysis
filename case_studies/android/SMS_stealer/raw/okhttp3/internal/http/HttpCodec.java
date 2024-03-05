package okhttp3.internal.http;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Sink;

public interface HttpCodec {
  public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;
  
  void cancel();
  
  Sink createRequestBody(Request paramRequest, long paramLong);
  
  void finishRequest() throws IOException;
  
  void flushRequest() throws IOException;
  
  ResponseBody openResponseBody(Response paramResponse) throws IOException;
  
  Response.Builder readResponseHeaders(boolean paramBoolean) throws IOException;
  
  void writeRequestHeaders(Request paramRequest) throws IOException;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\HttpCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */