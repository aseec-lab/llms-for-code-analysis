package okhttp3;

import javax.annotation.Nullable;
import okio.ByteString;

public abstract class WebSocketListener {
  public void onClosed(WebSocket paramWebSocket, int paramInt, String paramString) {}
  
  public void onClosing(WebSocket paramWebSocket, int paramInt, String paramString) {}
  
  public void onFailure(WebSocket paramWebSocket, Throwable paramThrowable, @Nullable Response paramResponse) {}
  
  public void onMessage(WebSocket paramWebSocket, String paramString) {}
  
  public void onMessage(WebSocket paramWebSocket, ByteString paramByteString) {}
  
  public void onOpen(WebSocket paramWebSocket, Response paramResponse) {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\WebSocketListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */