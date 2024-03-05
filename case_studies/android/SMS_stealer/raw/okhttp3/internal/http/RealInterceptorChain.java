package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;

public final class RealInterceptorChain implements Interceptor.Chain {
  private final Call call;
  
  private int calls;
  
  private final int connectTimeout;
  
  private final RealConnection connection;
  
  private final EventListener eventListener;
  
  private final HttpCodec httpCodec;
  
  private final int index;
  
  private final List<Interceptor> interceptors;
  
  private final int readTimeout;
  
  private final Request request;
  
  private final StreamAllocation streamAllocation;
  
  private final int writeTimeout;
  
  public RealInterceptorChain(List<Interceptor> paramList, StreamAllocation paramStreamAllocation, HttpCodec paramHttpCodec, RealConnection paramRealConnection, int paramInt1, Request paramRequest, Call paramCall, EventListener paramEventListener, int paramInt2, int paramInt3, int paramInt4) {
    this.interceptors = paramList;
    this.connection = paramRealConnection;
    this.streamAllocation = paramStreamAllocation;
    this.httpCodec = paramHttpCodec;
    this.index = paramInt1;
    this.request = paramRequest;
    this.call = paramCall;
    this.eventListener = paramEventListener;
    this.connectTimeout = paramInt2;
    this.readTimeout = paramInt3;
    this.writeTimeout = paramInt4;
  }
  
  public Call call() {
    return this.call;
  }
  
  public int connectTimeoutMillis() {
    return this.connectTimeout;
  }
  
  public Connection connection() {
    return (Connection)this.connection;
  }
  
  public EventListener eventListener() {
    return this.eventListener;
  }
  
  public HttpCodec httpStream() {
    return this.httpCodec;
  }
  
  public Response proceed(Request paramRequest) throws IOException {
    return proceed(paramRequest, this.streamAllocation, this.httpCodec, this.connection);
  }
  
  public Response proceed(Request paramRequest, StreamAllocation paramStreamAllocation, HttpCodec paramHttpCodec, RealConnection paramRealConnection) throws IOException {
    if (this.index < this.interceptors.size()) {
      this.calls++;
      if (this.httpCodec == null || this.connection.supportsUrl(paramRequest.url())) {
        if (this.httpCodec == null || this.calls <= 1) {
          RealInterceptorChain realInterceptorChain = new RealInterceptorChain(this.interceptors, paramStreamAllocation, paramHttpCodec, paramRealConnection, this.index + 1, paramRequest, this.call, this.eventListener, this.connectTimeout, this.readTimeout, this.writeTimeout);
          Interceptor interceptor = this.interceptors.get(this.index);
          Response response = interceptor.intercept(realInterceptorChain);
          if (paramHttpCodec == null || this.index + 1 >= this.interceptors.size() || realInterceptorChain.calls == 1) {
            if (response != null) {
              if (response.body() != null)
                return response; 
              StringBuilder stringBuilder4 = new StringBuilder();
              stringBuilder4.append("interceptor ");
              stringBuilder4.append(interceptor);
              stringBuilder4.append(" returned a response with no body");
              throw new IllegalStateException(stringBuilder4.toString());
            } 
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("interceptor ");
            stringBuilder3.append(interceptor);
            stringBuilder3.append(" returned null");
            throw new NullPointerException(stringBuilder3.toString());
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("network interceptor ");
          stringBuilder2.append(interceptor);
          stringBuilder2.append(" must call proceed() exactly once");
          throw new IllegalStateException(stringBuilder2.toString());
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("network interceptor ");
        stringBuilder1.append(this.interceptors.get(this.index - 1));
        stringBuilder1.append(" must call proceed() exactly once");
        throw new IllegalStateException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("network interceptor ");
      stringBuilder.append(this.interceptors.get(this.index - 1));
      stringBuilder.append(" must retain the same host and port");
      throw new IllegalStateException(stringBuilder.toString());
    } 
    throw new AssertionError();
  }
  
  public int readTimeoutMillis() {
    return this.readTimeout;
  }
  
  public Request request() {
    return this.request;
  }
  
  public StreamAllocation streamAllocation() {
    return this.streamAllocation;
  }
  
  public Interceptor.Chain withConnectTimeout(int paramInt, TimeUnit paramTimeUnit) {
    paramInt = Util.checkDuration("timeout", paramInt, paramTimeUnit);
    return new RealInterceptorChain(this.interceptors, this.streamAllocation, this.httpCodec, this.connection, this.index, this.request, this.call, this.eventListener, paramInt, this.readTimeout, this.writeTimeout);
  }
  
  public Interceptor.Chain withReadTimeout(int paramInt, TimeUnit paramTimeUnit) {
    paramInt = Util.checkDuration("timeout", paramInt, paramTimeUnit);
    return new RealInterceptorChain(this.interceptors, this.streamAllocation, this.httpCodec, this.connection, this.index, this.request, this.call, this.eventListener, this.connectTimeout, paramInt, this.writeTimeout);
  }
  
  public Interceptor.Chain withWriteTimeout(int paramInt, TimeUnit paramTimeUnit) {
    paramInt = Util.checkDuration("timeout", paramInt, paramTimeUnit);
    return new RealInterceptorChain(this.interceptors, this.streamAllocation, this.httpCodec, this.connection, this.index, this.request, this.call, this.eventListener, this.connectTimeout, this.readTimeout, paramInt);
  }
  
  public int writeTimeoutMillis() {
    return this.writeTimeout;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\RealInterceptorChain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */