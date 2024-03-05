package com.androidnetworking.interceptors;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;
import okio.Sink;

public class GzipRequestInterceptor implements Interceptor {
  private RequestBody forceContentLength(final RequestBody requestBody) throws IOException {
    final Buffer buffer = new Buffer();
    requestBody.writeTo((BufferedSink)buffer);
    return new RequestBody() {
        final GzipRequestInterceptor this$0;
        
        final Buffer val$buffer;
        
        final RequestBody val$requestBody;
        
        public long contentLength() {
          return buffer.size();
        }
        
        public MediaType contentType() {
          return requestBody.contentType();
        }
        
        public void writeTo(BufferedSink param1BufferedSink) throws IOException {
          param1BufferedSink.write(buffer.snapshot());
        }
      };
  }
  
  private RequestBody gzip(final RequestBody body) {
    return new RequestBody() {
        final GzipRequestInterceptor this$0;
        
        final RequestBody val$body;
        
        public long contentLength() {
          return -1L;
        }
        
        public MediaType contentType() {
          return body.contentType();
        }
        
        public void writeTo(BufferedSink param1BufferedSink) throws IOException {
          param1BufferedSink = Okio.buffer((Sink)new GzipSink((Sink)param1BufferedSink));
          body.writeTo(param1BufferedSink);
          param1BufferedSink.close();
        }
      };
  }
  
  public Response intercept(Interceptor.Chain paramChain) throws IOException {
    Request request = paramChain.request();
    return (request.body() == null || request.header("Content-Encoding") != null) ? paramChain.proceed(request) : paramChain.proceed(request.newBuilder().header("Content-Encoding", "gzip").method(request.method(), forceContentLength(gzip(request.body()))).build());
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\interceptors\GzipRequestInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */