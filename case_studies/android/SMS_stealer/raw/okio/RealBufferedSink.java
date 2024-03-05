package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

final class RealBufferedSink implements BufferedSink {
  public final Buffer buffer = new Buffer();
  
  boolean closed;
  
  public final Sink sink;
  
  RealBufferedSink(Sink paramSink) {
    if (paramSink != null) {
      this.sink = paramSink;
      return;
    } 
    throw new NullPointerException("sink == null");
  }
  
  public Buffer buffer() {
    return this.buffer;
  }
  
  public void close() throws IOException {
    if (this.closed)
      return; 
    Exception exception2 = null;
    Exception exception1 = exception2;
    try {
      if (this.buffer.size > 0L) {
        this.sink.write(this.buffer, this.buffer.size);
        exception1 = exception2;
      } 
    } finally {}
    try {
    
    } finally {
      Exception exception = null;
      exception2 = exception1;
    } 
    this.closed = true;
    if (exception2 != null)
      Util.sneakyRethrow(exception2); 
  }
  
  public BufferedSink emit() throws IOException {
    if (!this.closed) {
      long l = this.buffer.size();
      if (l > 0L)
        this.sink.write(this.buffer, l); 
      return this;
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink emitCompleteSegments() throws IOException {
    if (!this.closed) {
      long l = this.buffer.completeSegmentByteCount();
      if (l > 0L)
        this.sink.write(this.buffer, l); 
      return this;
    } 
    throw new IllegalStateException("closed");
  }
  
  public void flush() throws IOException {
    if (!this.closed) {
      if (this.buffer.size > 0L) {
        Sink sink = this.sink;
        Buffer buffer = this.buffer;
        sink.write(buffer, buffer.size);
      } 
      this.sink.flush();
      return;
    } 
    throw new IllegalStateException("closed");
  }
  
  public boolean isOpen() {
    return this.closed ^ true;
  }
  
  public OutputStream outputStream() {
    return new OutputStream() {
        final RealBufferedSink this$0;
        
        public void close() throws IOException {
          RealBufferedSink.this.close();
        }
        
        public void flush() throws IOException {
          if (!RealBufferedSink.this.closed)
            RealBufferedSink.this.flush(); 
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(RealBufferedSink.this);
          stringBuilder.append(".outputStream()");
          return stringBuilder.toString();
        }
        
        public void write(int param1Int) throws IOException {
          if (!RealBufferedSink.this.closed) {
            RealBufferedSink.this.buffer.writeByte((byte)param1Int);
            RealBufferedSink.this.emitCompleteSegments();
            return;
          } 
          throw new IOException("closed");
        }
        
        public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
          if (!RealBufferedSink.this.closed) {
            RealBufferedSink.this.buffer.write(param1ArrayOfbyte, param1Int1, param1Int2);
            RealBufferedSink.this.emitCompleteSegments();
            return;
          } 
          throw new IOException("closed");
        }
      };
  }
  
  public Timeout timeout() {
    return this.sink.timeout();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("buffer(");
    stringBuilder.append(this.sink);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public int write(ByteBuffer paramByteBuffer) throws IOException {
    if (!this.closed) {
      int i = this.buffer.write(paramByteBuffer);
      emitCompleteSegments();
      return i;
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink write(ByteString paramByteString) throws IOException {
    if (!this.closed) {
      this.buffer.write(paramByteString);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink write(Source paramSource, long paramLong) throws IOException {
    while (paramLong > 0L) {
      long l = paramSource.read(this.buffer, paramLong);
      if (l != -1L) {
        paramLong -= l;
        emitCompleteSegments();
        continue;
      } 
      throw new EOFException();
    } 
    return this;
  }
  
  public BufferedSink write(byte[] paramArrayOfbyte) throws IOException {
    if (!this.closed) {
      this.buffer.write(paramArrayOfbyte);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (!this.closed) {
      this.buffer.write(paramArrayOfbyte, paramInt1, paramInt2);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public void write(Buffer paramBuffer, long paramLong) throws IOException {
    if (!this.closed) {
      this.buffer.write(paramBuffer, paramLong);
      emitCompleteSegments();
      return;
    } 
    throw new IllegalStateException("closed");
  }
  
  public long writeAll(Source paramSource) throws IOException {
    if (paramSource != null) {
      long l = 0L;
      while (true) {
        long l1 = paramSource.read(this.buffer, 8192L);
        if (l1 != -1L) {
          l += l1;
          emitCompleteSegments();
          continue;
        } 
        return l;
      } 
    } 
    throw new IllegalArgumentException("source == null");
  }
  
  public BufferedSink writeByte(int paramInt) throws IOException {
    if (!this.closed) {
      this.buffer.writeByte(paramInt);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeDecimalLong(long paramLong) throws IOException {
    if (!this.closed) {
      this.buffer.writeDecimalLong(paramLong);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeHexadecimalUnsignedLong(long paramLong) throws IOException {
    if (!this.closed) {
      this.buffer.writeHexadecimalUnsignedLong(paramLong);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeInt(int paramInt) throws IOException {
    if (!this.closed) {
      this.buffer.writeInt(paramInt);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeIntLe(int paramInt) throws IOException {
    if (!this.closed) {
      this.buffer.writeIntLe(paramInt);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeLong(long paramLong) throws IOException {
    if (!this.closed) {
      this.buffer.writeLong(paramLong);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeLongLe(long paramLong) throws IOException {
    if (!this.closed) {
      this.buffer.writeLongLe(paramLong);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeShort(int paramInt) throws IOException {
    if (!this.closed) {
      this.buffer.writeShort(paramInt);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeShortLe(int paramInt) throws IOException {
    if (!this.closed) {
      this.buffer.writeShortLe(paramInt);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeString(String paramString, int paramInt1, int paramInt2, Charset paramCharset) throws IOException {
    if (!this.closed) {
      this.buffer.writeString(paramString, paramInt1, paramInt2, paramCharset);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeString(String paramString, Charset paramCharset) throws IOException {
    if (!this.closed) {
      this.buffer.writeString(paramString, paramCharset);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeUtf8(String paramString) throws IOException {
    if (!this.closed) {
      this.buffer.writeUtf8(paramString);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeUtf8(String paramString, int paramInt1, int paramInt2) throws IOException {
    if (!this.closed) {
      this.buffer.writeUtf8(paramString, paramInt1, paramInt2);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeUtf8CodePoint(int paramInt) throws IOException {
    if (!this.closed) {
      this.buffer.writeUtf8CodePoint(paramInt);
      return emitCompleteSegments();
    } 
    throw new IllegalStateException("closed");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\RealBufferedSink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */