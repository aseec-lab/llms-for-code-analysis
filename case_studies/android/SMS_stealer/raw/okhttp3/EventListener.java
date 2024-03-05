package okhttp3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import javax.annotation.Nullable;

public abstract class EventListener {
  public static final EventListener NONE = new EventListener() {
    
    };
  
  static Factory factory(final EventListener listener) {
    return new Factory() {
        final EventListener val$listener;
        
        public EventListener create(Call param1Call) {
          return listener;
        }
      };
  }
  
  public void callEnd(Call paramCall) {}
  
  public void callFailed(Call paramCall, IOException paramIOException) {}
  
  public void callStart(Call paramCall) {}
  
  public void connectEnd(Call paramCall, InetSocketAddress paramInetSocketAddress, Proxy paramProxy, @Nullable Protocol paramProtocol) {}
  
  public void connectFailed(Call paramCall, InetSocketAddress paramInetSocketAddress, Proxy paramProxy, @Nullable Protocol paramProtocol, IOException paramIOException) {}
  
  public void connectStart(Call paramCall, InetSocketAddress paramInetSocketAddress, Proxy paramProxy) {}
  
  public void connectionAcquired(Call paramCall, Connection paramConnection) {}
  
  public void connectionReleased(Call paramCall, Connection paramConnection) {}
  
  public void dnsEnd(Call paramCall, String paramString, List<InetAddress> paramList) {}
  
  public void dnsStart(Call paramCall, String paramString) {}
  
  public void requestBodyEnd(Call paramCall, long paramLong) {}
  
  public void requestBodyStart(Call paramCall) {}
  
  public void requestHeadersEnd(Call paramCall, Request paramRequest) {}
  
  public void requestHeadersStart(Call paramCall) {}
  
  public void responseBodyEnd(Call paramCall, long paramLong) {}
  
  public void responseBodyStart(Call paramCall) {}
  
  public void responseHeadersEnd(Call paramCall, Response paramResponse) {}
  
  public void responseHeadersStart(Call paramCall) {}
  
  public void secureConnectEnd(Call paramCall, @Nullable Handshake paramHandshake) {}
  
  public void secureConnectStart(Call paramCall) {}
  
  public static interface Factory {
    EventListener create(Call param1Call);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\EventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */