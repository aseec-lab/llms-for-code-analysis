package okhttp3.internal.ws;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

final class WebSocketReader {
  boolean closed;
  
  private final Buffer controlFrameBuffer = new Buffer();
  
  final FrameCallback frameCallback;
  
  long frameLength;
  
  final boolean isClient;
  
  boolean isControlFrame;
  
  boolean isFinalFrame;
  
  private final Buffer.UnsafeCursor maskCursor;
  
  private final byte[] maskKey;
  
  private final Buffer messageFrameBuffer = new Buffer();
  
  int opcode;
  
  final BufferedSource source;
  
  WebSocketReader(boolean paramBoolean, BufferedSource paramBufferedSource, FrameCallback paramFrameCallback) {
    if (paramBufferedSource != null) {
      if (paramFrameCallback != null) {
        byte[] arrayOfByte;
        Buffer.UnsafeCursor unsafeCursor;
        this.isClient = paramBoolean;
        this.source = paramBufferedSource;
        this.frameCallback = paramFrameCallback;
        paramFrameCallback = null;
        if (paramBoolean) {
          paramBufferedSource = null;
        } else {
          arrayOfByte = new byte[4];
        } 
        this.maskKey = arrayOfByte;
        if (paramBoolean) {
          FrameCallback frameCallback = paramFrameCallback;
        } else {
          unsafeCursor = new Buffer.UnsafeCursor();
        } 
        this.maskCursor = unsafeCursor;
        return;
      } 
      throw new NullPointerException("frameCallback == null");
    } 
    throw new NullPointerException("source == null");
  }
  
  private void readControlFrame() throws IOException {
    StringBuilder stringBuilder;
    long l = this.frameLength;
    if (l > 0L) {
      this.source.readFully(this.controlFrameBuffer, l);
      if (!this.isClient) {
        this.controlFrameBuffer.readAndWriteUnsafe(this.maskCursor);
        this.maskCursor.seek(0L);
        WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
        this.maskCursor.close();
      } 
    } 
    switch (this.opcode) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown control opcode: ");
        stringBuilder.append(Integer.toHexString(this.opcode));
        throw new ProtocolException(stringBuilder.toString());
      case 10:
        this.frameCallback.onReadPong(this.controlFrameBuffer.readByteString());
        return;
      case 9:
        this.frameCallback.onReadPing(this.controlFrameBuffer.readByteString());
        return;
      case 8:
        break;
    } 
    short s = 1005;
    l = this.controlFrameBuffer.size();
    if (l != 1L) {
      String str;
      if (l != 0L) {
        s = this.controlFrameBuffer.readShort();
        str = this.controlFrameBuffer.readUtf8();
        String str1 = WebSocketProtocol.closeCodeExceptionMessage(s);
        if (str1 != null)
          throw new ProtocolException(str1); 
      } else {
        str = "";
      } 
      this.frameCallback.onReadClose(s, str);
      this.closed = true;
      return;
    } 
    throw new ProtocolException("Malformed close payload length of 1.");
  }
  
  private void readHeader() throws IOException {
    if (!this.closed) {
      long l = this.source.timeout().timeoutNanos();
      this.source.timeout().clearTimeout();
      try {
        boolean bool1;
        byte b = this.source.readByte();
        int i = b & 0xFF;
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
        this.opcode = i & 0xF;
        boolean bool2 = true;
        if ((i & 0x80) != 0) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        this.isFinalFrame = bool1;
        if ((i & 0x8) != 0) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        this.isControlFrame = bool1;
        if (!bool1 || this.isFinalFrame) {
          boolean bool;
          if ((i & 0x40) != 0) {
            b = 1;
          } else {
            b = 0;
          } 
          if ((i & 0x20) != 0) {
            bool = true;
          } else {
            bool = false;
          } 
          if ((i & 0x10) != 0) {
            i = 1;
          } else {
            i = 0;
          } 
          if (b == 0 && !bool && i == 0) {
            int j = this.source.readByte() & 0xFF;
            if ((j & 0x80) != 0) {
              bool1 = bool2;
            } else {
              bool1 = false;
            } 
            if (bool1 == this.isClient) {
              String str;
              if (this.isClient) {
                str = "Server-sent frames must not be masked.";
              } else {
                str = "Client-sent frames must be masked.";
              } 
              throw new ProtocolException(str);
            } 
            l = (j & 0x7F);
            this.frameLength = l;
            if (l == 126L) {
              this.frameLength = this.source.readShort() & 0xFFFFL;
            } else if (l == 127L) {
              l = this.source.readLong();
              this.frameLength = l;
              if (l < 0L) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Frame length 0x");
                stringBuilder.append(Long.toHexString(this.frameLength));
                stringBuilder.append(" > 0x7FFFFFFFFFFFFFFF");
                throw new ProtocolException(stringBuilder.toString());
              } 
            } 
            if (!this.isControlFrame || this.frameLength <= 125L)
              return; 
            throw new ProtocolException("Control frame must be less than 125B.");
          } 
          throw new ProtocolException("Reserved flags are unsupported.");
        } 
        throw new ProtocolException("Control frames must be final.");
      } finally {
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
      } 
    } 
    throw new IOException("closed");
  }
  
  private void readMessage() throws IOException {
    while (!this.closed) {
      long l = this.frameLength;
      if (l > 0L) {
        this.source.readFully(this.messageFrameBuffer, l);
        if (!this.isClient) {
          this.messageFrameBuffer.readAndWriteUnsafe(this.maskCursor);
          this.maskCursor.seek(this.messageFrameBuffer.size() - this.frameLength);
          WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
          this.maskCursor.close();
        } 
      } 
      if (this.isFinalFrame)
        return; 
      readUntilNonControlFrame();
      if (this.opcode == 0)
        continue; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Expected continuation opcode. Got: ");
      stringBuilder.append(Integer.toHexString(this.opcode));
      throw new ProtocolException(stringBuilder.toString());
    } 
    throw new IOException("closed");
  }
  
  private void readMessageFrame() throws IOException {
    int i = this.opcode;
    if (i == 1 || i == 2) {
      readMessage();
      if (i == 1) {
        this.frameCallback.onReadMessage(this.messageFrameBuffer.readUtf8());
      } else {
        this.frameCallback.onReadMessage(this.messageFrameBuffer.readByteString());
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Unknown opcode: ");
    stringBuilder.append(Integer.toHexString(i));
    throw new ProtocolException(stringBuilder.toString());
  }
  
  private void readUntilNonControlFrame() throws IOException {
    while (!this.closed) {
      readHeader();
      if (!this.isControlFrame)
        break; 
      readControlFrame();
    } 
  }
  
  void processNextFrame() throws IOException {
    readHeader();
    if (this.isControlFrame) {
      readControlFrame();
    } else {
      readMessageFrame();
    } 
  }
  
  public static interface FrameCallback {
    void onReadClose(int param1Int, String param1String);
    
    void onReadMessage(String param1String) throws IOException;
    
    void onReadMessage(ByteString param1ByteString) throws IOException;
    
    void onReadPing(ByteString param1ByteString);
    
    void onReadPong(ByteString param1ByteString);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\ws\WebSocketReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */