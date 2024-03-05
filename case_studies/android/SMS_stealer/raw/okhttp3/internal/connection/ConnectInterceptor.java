package okhttp3.internal.connection;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealInterceptorChain;

public final class ConnectInterceptor implements Interceptor {
  public final OkHttpClient client;
  
  public ConnectInterceptor(OkHttpClient paramOkHttpClient) {
    this.client = paramOkHttpClient;
  }
  
  public Response intercept(Interceptor.Chain paramChain) throws IOException {
    RealInterceptorChain realInterceptorChain = (RealInterceptorChain)paramChain;
    Request request = realInterceptorChain.request();
    StreamAllocation streamAllocation = realInterceptorChain.streamAllocation();
    boolean bool = request.method().equals("GET");
    return realInterceptorChain.proceed(request, streamAllocation, streamAllocation.newStream(this.client, paramChain, bool ^ true), streamAllocation.connection());
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\connection\ConnectInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */