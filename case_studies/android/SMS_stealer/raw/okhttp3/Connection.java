package okhttp3;

import java.net.Socket;
import javax.annotation.Nullable;

public interface Connection {
  @Nullable
  Handshake handshake();
  
  Protocol protocol();
  
  Route route();
  
  Socket socket();
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */