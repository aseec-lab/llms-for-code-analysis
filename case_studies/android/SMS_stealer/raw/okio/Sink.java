package okio;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public interface Sink extends Closeable, Flushable {
  void close() throws IOException;
  
  void flush() throws IOException;
  
  Timeout timeout();
  
  void write(Buffer paramBuffer, long paramLong) throws IOException;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\Sink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */