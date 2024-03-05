package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public final class CallServerInterceptor implements Interceptor {
  private final boolean forWebSocket;
  
  public CallServerInterceptor(boolean paramBoolean) {
    this.forWebSocket = paramBoolean;
  }
  
  public Response intercept(Interceptor.Chain paramChain) throws IOException {
    Response.Builder builder1;
    RealInterceptorChain realInterceptorChain = (RealInterceptorChain)paramChain;
    HttpCodec httpCodec = realInterceptorChain.httpStream();
    StreamAllocation streamAllocation = realInterceptorChain.streamAllocation();
    RealConnection realConnection = (RealConnection)realInterceptorChain.connection();
    Request request = realInterceptorChain.request();
    long l = System.currentTimeMillis();
    realInterceptorChain.eventListener().requestHeadersStart(realInterceptorChain.call());
    httpCodec.writeRequestHeaders(request);
    realInterceptorChain.eventListener().requestHeadersEnd(realInterceptorChain.call(), request);
    boolean bool = HttpMethod.permitsRequestBody(request.method());
    Interceptor.Chain chain = null;
    Response.Builder builder2 = null;
    paramChain = chain;
    if (bool) {
      paramChain = chain;
      if (request.body() != null) {
        if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
          httpCodec.flushRequest();
          realInterceptorChain.eventListener().responseHeadersStart(realInterceptorChain.call());
          builder2 = httpCodec.readResponseHeaders(true);
        } 
        if (builder2 == null) {
          realInterceptorChain.eventListener().requestBodyStart(realInterceptorChain.call());
          CountingSink countingSink = new CountingSink(httpCodec.createRequestBody(request, request.body().contentLength()));
          BufferedSink bufferedSink = Okio.buffer((Sink)countingSink);
          request.body().writeTo(bufferedSink);
          bufferedSink.close();
          realInterceptorChain.eventListener().requestBodyEnd(realInterceptorChain.call(), countingSink.successfulCount);
          builder1 = builder2;
        } else {
          builder1 = builder2;
          if (!realConnection.isMultiplexed()) {
            streamAllocation.noNewStreams();
            builder1 = builder2;
          } 
        } 
      } 
    } 
    httpCodec.finishRequest();
    builder2 = builder1;
    if (builder1 == null) {
      realInterceptorChain.eventListener().responseHeadersStart(realInterceptorChain.call());
      builder2 = httpCodec.readResponseHeaders(false);
    } 
    Response response = builder2.request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
    int j = response.code();
    int i = j;
    if (j == 100) {
      response = httpCodec.readResponseHeaders(false).request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
      i = response.code();
    } 
    realInterceptorChain.eventListener().responseHeadersEnd(realInterceptorChain.call(), response);
    if (this.forWebSocket && i == 101) {
      response = response.newBuilder().body(Util.EMPTY_RESPONSE).build();
    } else {
      response = response.newBuilder().body(httpCodec.openResponseBody(response)).build();
    } 
    if ("close".equalsIgnoreCase(response.request().header("Connection")) || "close".equalsIgnoreCase(response.header("Connection")))
      streamAllocation.noNewStreams(); 
    if ((i != 204 && i != 205) || response.body().contentLength() <= 0L)
      return response; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("HTTP ");
    stringBuilder.append(i);
    stringBuilder.append(" had non-zero Content-Length: ");
    stringBuilder.append(response.body().contentLength());
    throw new ProtocolException(stringBuilder.toString());
  }
  
  static final class CountingSink extends ForwardingSink {
    long successfulCount;
    
    CountingSink(Sink param1Sink) {
      super(param1Sink);
    }
    
    public void write(Buffer param1Buffer, long param1Long) throws IOException {
      super.write(param1Buffer, param1Long);
      this.successfulCount += param1Long;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\CallServerInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */