package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

final class Http2Reader implements Closeable {
  static final Logger logger = Logger.getLogger(Http2.class.getName());
  
  private final boolean client;
  
  private final ContinuationSource continuation;
  
  final Hpack.Reader hpackReader;
  
  private final BufferedSource source;
  
  Http2Reader(BufferedSource paramBufferedSource, boolean paramBoolean) {
    this.source = paramBufferedSource;
    this.client = paramBoolean;
    this.continuation = new ContinuationSource(this.source);
    this.hpackReader = new Hpack.Reader(4096, this.continuation);
  }
  
  static int lengthWithoutPadding(int paramInt, byte paramByte, short paramShort) throws IOException {
    int i = paramInt;
    if ((paramByte & 0x8) != 0)
      i = paramInt - 1; 
    if (paramShort <= i)
      return (short)(i - paramShort); 
    throw Http2.ioException("PROTOCOL_ERROR padding %s > remaining length %s", new Object[] { Short.valueOf(paramShort), Integer.valueOf(i) });
  }
  
  private void readData(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    short s = 0;
    if (paramInt2 != 0) {
      boolean bool2;
      boolean bool1 = true;
      if ((paramByte & 0x1) != 0) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      if ((paramByte & 0x20) == 0)
        bool1 = false; 
      if (!bool1) {
        if ((paramByte & 0x8) != 0)
          s = (short)(this.source.readByte() & 0xFF); 
        paramInt1 = lengthWithoutPadding(paramInt1, paramByte, s);
        paramHandler.data(bool2, paramInt2, this.source, paramInt1);
        this.source.skip(s);
        return;
      } 
      throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
    } 
    throw Http2.ioException("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
  }
  
  private void readGoAway(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    if (paramInt1 >= 8) {
      if (paramInt2 == 0) {
        paramInt2 = this.source.readInt();
        int i = this.source.readInt();
        paramInt1 -= 8;
        ErrorCode errorCode = ErrorCode.fromHttp2(i);
        if (errorCode != null) {
          ByteString byteString = ByteString.EMPTY;
          if (paramInt1 > 0)
            byteString = this.source.readByteString(paramInt1); 
          paramHandler.goAway(paramInt2, errorCode, byteString);
          return;
        } 
        throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", new Object[] { Integer.valueOf(i) });
      } 
      throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
    } 
    throw Http2.ioException("TYPE_GOAWAY length < 8: %s", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private List<Header> readHeaderBlock(int paramInt1, short paramShort, byte paramByte, int paramInt2) throws IOException {
    ContinuationSource continuationSource = this.continuation;
    continuationSource.left = paramInt1;
    continuationSource.length = paramInt1;
    this.continuation.padding = paramShort;
    this.continuation.flags = paramByte;
    this.continuation.streamId = paramInt2;
    this.hpackReader.readHeaders();
    return this.hpackReader.getAndResetHeaderList();
  }
  
  private void readHeaders(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    short s = 0;
    if (paramInt2 != 0) {
      boolean bool;
      if ((paramByte & 0x1) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      if ((paramByte & 0x8) != 0)
        s = (short)(this.source.readByte() & 0xFF); 
      int i = paramInt1;
      if ((paramByte & 0x20) != 0) {
        readPriority(paramHandler, paramInt2);
        i = paramInt1 - 5;
      } 
      paramHandler.headers(bool, paramInt2, -1, readHeaderBlock(lengthWithoutPadding(i, paramByte, s), s, paramByte, paramInt2));
      return;
    } 
    throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
  }
  
  static int readMedium(BufferedSource paramBufferedSource) throws IOException {
    byte b2 = paramBufferedSource.readByte();
    byte b1 = paramBufferedSource.readByte();
    return paramBufferedSource.readByte() & 0xFF | (b2 & 0xFF) << 16 | (b1 & 0xFF) << 8;
  }
  
  private void readPing(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    boolean bool = false;
    if (paramInt1 == 8) {
      if (paramInt2 == 0) {
        paramInt1 = this.source.readInt();
        paramInt2 = this.source.readInt();
        if ((paramByte & 0x1) != 0)
          bool = true; 
        paramHandler.ping(bool, paramInt1, paramInt2);
        return;
      } 
      throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
    } 
    throw Http2.ioException("TYPE_PING length != 8: %s", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private void readPriority(Handler paramHandler, int paramInt) throws IOException {
    boolean bool;
    int i = this.source.readInt();
    if ((Integer.MIN_VALUE & i) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    paramHandler.priority(paramInt, i & Integer.MAX_VALUE, (this.source.readByte() & 0xFF) + 1, bool);
  }
  
  private void readPriority(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    if (paramInt1 == 5) {
      if (paramInt2 != 0) {
        readPriority(paramHandler, paramInt2);
        return;
      } 
      throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
    } 
    throw Http2.ioException("TYPE_PRIORITY length: %d != 5", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private void readPushPromise(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    short s = 0;
    if (paramInt2 != 0) {
      if ((paramByte & 0x8) != 0)
        s = (short)(this.source.readByte() & 0xFF); 
      paramHandler.pushPromise(paramInt2, this.source.readInt() & Integer.MAX_VALUE, readHeaderBlock(lengthWithoutPadding(paramInt1 - 4, paramByte, s), s, paramByte, paramInt2));
      return;
    } 
    throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
  }
  
  private void readRstStream(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    if (paramInt1 == 4) {
      if (paramInt2 != 0) {
        paramInt1 = this.source.readInt();
        ErrorCode errorCode = ErrorCode.fromHttp2(paramInt1);
        if (errorCode != null) {
          paramHandler.rstStream(paramInt2, errorCode);
          return;
        } 
        throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", new Object[] { Integer.valueOf(paramInt1) });
      } 
      throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
    } 
    throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private void readSettings(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    if (paramInt2 == 0) {
      if ((paramByte & 0x1) != 0) {
        if (paramInt1 == 0) {
          paramHandler.ackSettings();
          return;
        } 
        throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
      } 
      if (paramInt1 % 6 == 0) {
        Settings settings = new Settings();
        for (paramInt2 = 0; paramInt2 < paramInt1; paramInt2 += 6) {
          int i;
          int j = this.source.readShort() & 0xFFFF;
          int k = this.source.readInt();
          if (j != 2) {
            if (j != 3) {
              if (j != 4) {
                if (j != 5) {
                  i = j;
                } else if (k >= 16384 && k <= 16777215) {
                  i = j;
                } else {
                  throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", new Object[] { Integer.valueOf(k) });
                } 
              } else {
                paramByte = 7;
                if (k < 0)
                  throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]); 
              } 
            } else {
              paramByte = 4;
            } 
          } else {
            i = j;
            if (k != 0)
              if (k == 1) {
                i = j;
              } else {
                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
              }  
          } 
          settings.set(i, k);
        } 
        paramHandler.settings(false, settings);
        return;
      } 
      throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", new Object[] { Integer.valueOf(paramInt1) });
    } 
    throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
  }
  
  private void readWindowUpdate(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2) throws IOException {
    if (paramInt1 == 4) {
      long l = this.source.readInt() & 0x7FFFFFFFL;
      if (l != 0L) {
        paramHandler.windowUpdate(paramInt2, l);
        return;
      } 
      throw Http2.ioException("windowSizeIncrement was 0", new Object[] { Long.valueOf(l) });
    } 
    throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  public void close() throws IOException {
    this.source.close();
  }
  
  public boolean nextFrame(boolean paramBoolean, Handler paramHandler) throws IOException {
    try {
      this.source.require(9L);
      int i = readMedium(this.source);
      if (i >= 0 && i <= 16384) {
        byte b = (byte)(this.source.readByte() & 0xFF);
        if (!paramBoolean || b == 4) {
          byte b1 = (byte)(this.source.readByte() & 0xFF);
          int j = this.source.readInt() & Integer.MAX_VALUE;
          if (logger.isLoggable(Level.FINE))
            logger.fine(Http2.frameLog(true, j, i, b, b1)); 
          switch (b) {
            default:
              this.source.skip(i);
              return true;
            case 8:
              readWindowUpdate(paramHandler, i, b1, j);
              return true;
            case 7:
              readGoAway(paramHandler, i, b1, j);
              return true;
            case 6:
              readPing(paramHandler, i, b1, j);
              return true;
            case 5:
              readPushPromise(paramHandler, i, b1, j);
              return true;
            case 4:
              readSettings(paramHandler, i, b1, j);
              return true;
            case 3:
              readRstStream(paramHandler, i, b1, j);
              return true;
            case 2:
              readPriority(paramHandler, i, b1, j);
              return true;
            case 1:
              readHeaders(paramHandler, i, b1, j);
              return true;
            case 0:
              break;
          } 
          readData(paramHandler, i, b1, j);
          return true;
        } 
        throw Http2.ioException("Expected a SETTINGS frame but was %s", new Object[] { Byte.valueOf(b) });
      } 
      throw Http2.ioException("FRAME_SIZE_ERROR: %s", new Object[] { Integer.valueOf(i) });
    } catch (IOException iOException) {
      return false;
    } 
  }
  
  public void readConnectionPreface(Handler paramHandler) throws IOException {
    if (this.client) {
      if (!nextFrame(true, paramHandler))
        throw Http2.ioException("Required SETTINGS preface not received", new Object[0]); 
    } else {
      ByteString byteString = this.source.readByteString(Http2.CONNECTION_PREFACE.size());
      if (logger.isLoggable(Level.FINE))
        logger.fine(Util.format("<< CONNECTION %s", new Object[] { byteString.hex() })); 
      if (!Http2.CONNECTION_PREFACE.equals(byteString))
        throw Http2.ioException("Expected a connection header but was %s", new Object[] { byteString.utf8() }); 
    } 
  }
  
  static final class ContinuationSource implements Source {
    byte flags;
    
    int left;
    
    int length;
    
    short padding;
    
    private final BufferedSource source;
    
    int streamId;
    
    ContinuationSource(BufferedSource param1BufferedSource) {
      this.source = param1BufferedSource;
    }
    
    private void readContinuationHeader() throws IOException {
      int i = this.streamId;
      int j = Http2Reader.readMedium(this.source);
      this.left = j;
      this.length = j;
      byte b = (byte)(this.source.readByte() & 0xFF);
      this.flags = (byte)(this.source.readByte() & 0xFF);
      if (Http2Reader.logger.isLoggable(Level.FINE))
        Http2Reader.logger.fine(Http2.frameLog(true, this.streamId, this.length, b, this.flags)); 
      j = this.source.readInt() & Integer.MAX_VALUE;
      this.streamId = j;
      if (b == 9) {
        if (j == i)
          return; 
        throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
      } 
      throw Http2.ioException("%s != TYPE_CONTINUATION", new Object[] { Byte.valueOf(b) });
    }
    
    public void close() throws IOException {}
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      while (true) {
        int i = this.left;
        if (i == 0) {
          this.source.skip(this.padding);
          this.padding = 0;
          if ((this.flags & 0x4) != 0)
            return -1L; 
          readContinuationHeader();
          continue;
        } 
        param1Long = this.source.read(param1Buffer, Math.min(param1Long, i));
        if (param1Long == -1L)
          return -1L; 
        this.left = (int)(this.left - param1Long);
        return param1Long;
      } 
    }
    
    public Timeout timeout() {
      return this.source.timeout();
    }
  }
  
  static interface Handler {
    void ackSettings();
    
    void alternateService(int param1Int1, String param1String1, ByteString param1ByteString, String param1String2, int param1Int2, long param1Long);
    
    void data(boolean param1Boolean, int param1Int1, BufferedSource param1BufferedSource, int param1Int2) throws IOException;
    
    void goAway(int param1Int, ErrorCode param1ErrorCode, ByteString param1ByteString);
    
    void headers(boolean param1Boolean, int param1Int1, int param1Int2, List<Header> param1List);
    
    void ping(boolean param1Boolean, int param1Int1, int param1Int2);
    
    void priority(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean);
    
    void pushPromise(int param1Int1, int param1Int2, List<Header> param1List) throws IOException;
    
    void rstStream(int param1Int, ErrorCode param1ErrorCode);
    
    void settings(boolean param1Boolean, Settings param1Settings);
    
    void windowUpdate(int param1Int, long param1Long);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\Http2Reader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */