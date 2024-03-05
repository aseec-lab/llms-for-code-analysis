package okhttp3.internal.ws;

import java.io.IOException;
import java.util.Random;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Timeout;

final class WebSocketWriter {
  boolean activeWriter;
  
  final Buffer buffer = new Buffer();
  
  final FrameSink frameSink = new FrameSink();
  
  final boolean isClient;
  
  private final Buffer.UnsafeCursor maskCursor;
  
  private final byte[] maskKey;
  
  final Random random;
  
  final BufferedSink sink;
  
  final Buffer sinkBuffer;
  
  boolean writerClosed;
  
  WebSocketWriter(boolean paramBoolean, BufferedSink paramBufferedSink, Random paramRandom) {
    if (paramBufferedSink != null) {
      if (paramRandom != null) {
        Buffer.UnsafeCursor unsafeCursor;
        this.isClient = paramBoolean;
        this.sink = paramBufferedSink;
        this.sinkBuffer = paramBufferedSink.buffer();
        this.random = paramRandom;
        paramRandom = null;
        if (paramBoolean) {
          byte[] arrayOfByte = new byte[4];
        } else {
          paramBufferedSink = null;
        } 
        this.maskKey = (byte[])paramBufferedSink;
        Random random = paramRandom;
        if (paramBoolean)
          unsafeCursor = new Buffer.UnsafeCursor(); 
        this.maskCursor = unsafeCursor;
        return;
      } 
      throw new NullPointerException("random == null");
    } 
    throw new NullPointerException("sink == null");
  }
  
  private void writeControlFrame(int paramInt, ByteString paramByteString) throws IOException {
    if (!this.writerClosed) {
      int i = paramByteString.size();
      if (i <= 125L) {
        this.sinkBuffer.writeByte(paramInt | 0x80);
        if (this.isClient) {
          this.sinkBuffer.writeByte(i | 0x80);
          this.random.nextBytes(this.maskKey);
          this.sinkBuffer.write(this.maskKey);
          if (i > 0) {
            long l = this.sinkBuffer.size();
            this.sinkBuffer.write(paramByteString);
            this.sinkBuffer.readAndWriteUnsafe(this.maskCursor);
            this.maskCursor.seek(l);
            WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
            this.maskCursor.close();
          } 
        } else {
          this.sinkBuffer.writeByte(i);
          this.sinkBuffer.write(paramByteString);
        } 
        this.sink.flush();
        return;
      } 
      throw new IllegalArgumentException("Payload size must be less than or equal to 125");
    } 
    throw new IOException("closed");
  }
  
  Sink newMessageSink(int paramInt, long paramLong) {
    if (!this.activeWriter) {
      this.activeWriter = true;
      this.frameSink.formatOpcode = paramInt;
      this.frameSink.contentLength = paramLong;
      this.frameSink.isFirstFrame = true;
      this.frameSink.closed = false;
      return this.frameSink;
    } 
    throw new IllegalStateException("Another message writer is active. Did you call close()?");
  }
  
  void writeClose(int paramInt, ByteString paramByteString) throws IOException {
    ByteString byteString = ByteString.EMPTY;
    if (paramInt != 0 || paramByteString != null) {
      if (paramInt != 0)
        WebSocketProtocol.validateCloseCode(paramInt); 
      Buffer buffer = new Buffer();
      buffer.writeShort(paramInt);
      if (paramByteString != null)
        buffer.write(paramByteString); 
      byteString = buffer.readByteString();
    } 
    try {
      writeControlFrame(8, byteString);
      return;
    } finally {
      this.writerClosed = true;
    } 
  }
  
  void writeMessageFrame(int paramInt, long paramLong, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    if (!this.writerClosed) {
      boolean bool = false;
      if (!paramBoolean1)
        paramInt = 0; 
      int i = paramInt;
      if (paramBoolean2)
        i = paramInt | 0x80; 
      this.sinkBuffer.writeByte(i);
      paramInt = bool;
      if (this.isClient)
        paramInt = 128; 
      if (paramLong <= 125L) {
        i = (int)paramLong;
        this.sinkBuffer.writeByte(i | paramInt);
      } else if (paramLong <= 65535L) {
        this.sinkBuffer.writeByte(paramInt | 0x7E);
        this.sinkBuffer.writeShort((int)paramLong);
      } else {
        this.sinkBuffer.writeByte(paramInt | 0x7F);
        this.sinkBuffer.writeLong(paramLong);
      } 
      if (this.isClient) {
        this.random.nextBytes(this.maskKey);
        this.sinkBuffer.write(this.maskKey);
        if (paramLong > 0L) {
          long l = this.sinkBuffer.size();
          this.sinkBuffer.write(this.buffer, paramLong);
          this.sinkBuffer.readAndWriteUnsafe(this.maskCursor);
          this.maskCursor.seek(l);
          WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
          this.maskCursor.close();
        } 
      } else {
        this.sinkBuffer.write(this.buffer, paramLong);
      } 
      this.sink.emit();
      return;
    } 
    throw new IOException("closed");
  }
  
  void writePing(ByteString paramByteString) throws IOException {
    writeControlFrame(9, paramByteString);
  }
  
  void writePong(ByteString paramByteString) throws IOException {
    writeControlFrame(10, paramByteString);
  }
  
  final class FrameSink implements Sink {
    boolean closed;
    
    long contentLength;
    
    int formatOpcode;
    
    boolean isFirstFrame;
    
    final WebSocketWriter this$0;
    
    public void close() throws IOException {
      if (!this.closed) {
        WebSocketWriter webSocketWriter = WebSocketWriter.this;
        webSocketWriter.writeMessageFrame(this.formatOpcode, webSocketWriter.buffer.size(), this.isFirstFrame, true);
        this.closed = true;
        WebSocketWriter.this.activeWriter = false;
        return;
      } 
      throw new IOException("closed");
    }
    
    public void flush() throws IOException {
      if (!this.closed) {
        WebSocketWriter webSocketWriter = WebSocketWriter.this;
        webSocketWriter.writeMessageFrame(this.formatOpcode, webSocketWriter.buffer.size(), this.isFirstFrame, false);
        this.isFirstFrame = false;
        return;
      } 
      throw new IOException("closed");
    }
    
    public Timeout timeout() {
      return WebSocketWriter.this.sink.timeout();
    }
    
    public void write(Buffer param1Buffer, long param1Long) throws IOException {
      if (!this.closed) {
        boolean bool;
        WebSocketWriter.this.buffer.write(param1Buffer, param1Long);
        if (this.isFirstFrame && this.contentLength != -1L && WebSocketWriter.this.buffer.size() > this.contentLength - 8192L) {
          bool = true;
        } else {
          bool = false;
        } 
        param1Long = WebSocketWriter.this.buffer.completeSegmentByteCount();
        if (param1Long > 0L && !bool) {
          WebSocketWriter.this.writeMessageFrame(this.formatOpcode, param1Long, this.isFirstFrame, false);
          this.isFirstFrame = false;
        } 
        return;
      } 
      throw new IOException("closed");
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\ws\WebSocketWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */