package okhttp3;

import javax.annotation.Nullable;
import okio.ByteString;

public interface WebSocket {
  void cancel();
  
  boolean close(int paramInt, @Nullable String paramString);
  
  long queueSize();
  
  Request request();
  
  boolean send(String paramString);
  
  boolean send(ByteString paramByteString);
  
  public static interface Factory {
    WebSocket newWebSocket(Request param1Request, WebSocketListener param1WebSocketListener);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\WebSocket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */