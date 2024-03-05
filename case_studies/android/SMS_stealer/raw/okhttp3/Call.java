package okhttp3;

import java.io.IOException;

public interface Call extends Cloneable {
  void cancel();
  
  Call clone();
  
  void enqueue(Callback paramCallback);
  
  Response execute() throws IOException;
  
  boolean isCanceled();
  
  boolean isExecuted();
  
  Request request();
  
  public static interface Factory {
    Call newCall(Request param1Request);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Call.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */