package okhttp3;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public interface Interceptor {
  Response intercept(Chain paramChain) throws IOException;
  
  public static interface Chain {
    Call call();
    
    int connectTimeoutMillis();
    
    @Nullable
    Connection connection();
    
    Response proceed(Request param1Request) throws IOException;
    
    int readTimeoutMillis();
    
    Request request();
    
    Chain withConnectTimeout(int param1Int, TimeUnit param1TimeUnit);
    
    Chain withReadTimeout(int param1Int, TimeUnit param1TimeUnit);
    
    Chain withWriteTimeout(int param1Int, TimeUnit param1TimeUnit);
    
    int writeTimeoutMillis();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Interceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */