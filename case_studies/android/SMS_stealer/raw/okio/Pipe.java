package okio;

import java.io.IOException;

public final class Pipe {
  final Buffer buffer = new Buffer();
  
  final long maxBufferSize;
  
  private final Sink sink = new PipeSink();
  
  boolean sinkClosed;
  
  private final Source source = new PipeSource();
  
  boolean sourceClosed;
  
  public Pipe(long paramLong) {
    if (paramLong >= 1L) {
      this.maxBufferSize = paramLong;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("maxBufferSize < 1: ");
    stringBuilder.append(paramLong);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public Sink sink() {
    return this.sink;
  }
  
  public Source source() {
    return this.source;
  }
  
  final class PipeSink implements Sink {
    final Pipe this$0;
    
    final Timeout timeout = new Timeout();
    
    public void close() throws IOException {
      synchronized (Pipe.this.buffer) {
        if (Pipe.this.sinkClosed)
          return; 
        if (!Pipe.this.sourceClosed || Pipe.this.buffer.size() <= 0L) {
          Pipe.this.sinkClosed = true;
          Pipe.this.buffer.notifyAll();
          return;
        } 
        IOException iOException = new IOException();
        this("source is closed");
        throw iOException;
      } 
    }
    
    public void flush() throws IOException {
      synchronized (Pipe.this.buffer) {
        if (!Pipe.this.sinkClosed) {
          if (!Pipe.this.sourceClosed || Pipe.this.buffer.size() <= 0L)
            return; 
          IOException iOException = new IOException();
          this("source is closed");
          throw iOException;
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this("closed");
        throw illegalStateException;
      } 
    }
    
    public Timeout timeout() {
      return this.timeout;
    }
    
    public void write(Buffer param1Buffer, long param1Long) throws IOException {
      synchronized (Pipe.this.buffer) {
        if (!Pipe.this.sinkClosed) {
          while (param1Long > 0L) {
            if (!Pipe.this.sourceClosed) {
              long l = Pipe.this.maxBufferSize - Pipe.this.buffer.size();
              if (l == 0L) {
                this.timeout.waitUntilNotified(Pipe.this.buffer);
                continue;
              } 
              l = Math.min(l, param1Long);
              Pipe.this.buffer.write(param1Buffer, l);
              param1Long -= l;
              Pipe.this.buffer.notifyAll();
              continue;
            } 
            IOException iOException = new IOException();
            this("source is closed");
            throw iOException;
          } 
          return;
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this("closed");
        throw illegalStateException;
      } 
    }
  }
  
  final class PipeSource implements Source {
    final Pipe this$0;
    
    final Timeout timeout = new Timeout();
    
    public void close() throws IOException {
      synchronized (Pipe.this.buffer) {
        Pipe.this.sourceClosed = true;
        Pipe.this.buffer.notifyAll();
        return;
      } 
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      synchronized (Pipe.this.buffer) {
        if (!Pipe.this.sourceClosed) {
          while (Pipe.this.buffer.size() == 0L) {
            if (Pipe.this.sinkClosed)
              return -1L; 
            this.timeout.waitUntilNotified(Pipe.this.buffer);
          } 
          param1Long = Pipe.this.buffer.read(param1Buffer, param1Long);
          Pipe.this.buffer.notifyAll();
          return param1Long;
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this("closed");
        throw illegalStateException;
      } 
    }
    
    public Timeout timeout() {
      return this.timeout;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\Pipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */