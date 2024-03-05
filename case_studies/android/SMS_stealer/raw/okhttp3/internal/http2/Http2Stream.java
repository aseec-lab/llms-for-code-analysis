package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2Stream {
  static final boolean $assertionsDisabled = false;
  
  long bytesLeftInWriteWindow;
  
  final Http2Connection connection;
  
  ErrorCode errorCode = null;
  
  private boolean hasResponseHeaders;
  
  final int id;
  
  final StreamTimeout readTimeout = new StreamTimeout();
  
  private final List<Header> requestHeaders;
  
  private List<Header> responseHeaders;
  
  final FramingSink sink;
  
  private final FramingSource source;
  
  long unacknowledgedBytesRead = 0L;
  
  final StreamTimeout writeTimeout = new StreamTimeout();
  
  Http2Stream(int paramInt, Http2Connection paramHttp2Connection, boolean paramBoolean1, boolean paramBoolean2, List<Header> paramList) {
    if (paramHttp2Connection != null) {
      if (paramList != null) {
        this.id = paramInt;
        this.connection = paramHttp2Connection;
        this.bytesLeftInWriteWindow = paramHttp2Connection.peerSettings.getInitialWindowSize();
        this.source = new FramingSource(paramHttp2Connection.okHttpSettings.getInitialWindowSize());
        this.sink = new FramingSink();
        this.source.finished = paramBoolean2;
        this.sink.finished = paramBoolean1;
        this.requestHeaders = paramList;
        return;
      } 
      throw new NullPointerException("requestHeaders == null");
    } 
    throw new NullPointerException("connection == null");
  }
  
  private boolean closeInternal(ErrorCode paramErrorCode) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   6: ifnull -> 13
    //   9: aload_0
    //   10: monitorexit
    //   11: iconst_0
    //   12: ireturn
    //   13: aload_0
    //   14: getfield source : Lokhttp3/internal/http2/Http2Stream$FramingSource;
    //   17: getfield finished : Z
    //   20: ifeq -> 37
    //   23: aload_0
    //   24: getfield sink : Lokhttp3/internal/http2/Http2Stream$FramingSink;
    //   27: getfield finished : Z
    //   30: ifeq -> 37
    //   33: aload_0
    //   34: monitorexit
    //   35: iconst_0
    //   36: ireturn
    //   37: aload_0
    //   38: aload_1
    //   39: putfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   42: aload_0
    //   43: invokevirtual notifyAll : ()V
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_0
    //   49: getfield connection : Lokhttp3/internal/http2/Http2Connection;
    //   52: aload_0
    //   53: getfield id : I
    //   56: invokevirtual removeStream : (I)Lokhttp3/internal/http2/Http2Stream;
    //   59: pop
    //   60: iconst_1
    //   61: ireturn
    //   62: astore_1
    //   63: aload_0
    //   64: monitorexit
    //   65: aload_1
    //   66: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	62	finally
    //   13	35	62	finally
    //   37	48	62	finally
    //   63	65	62	finally
  }
  
  void addBytesToWriteWindow(long paramLong) {
    this.bytesLeftInWriteWindow += paramLong;
    if (paramLong > 0L)
      notifyAll(); 
  }
  
  void cancelStreamIfNecessary() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield source : Lokhttp3/internal/http2/Http2Stream$FramingSource;
    //   6: getfield finished : Z
    //   9: ifne -> 47
    //   12: aload_0
    //   13: getfield source : Lokhttp3/internal/http2/Http2Stream$FramingSource;
    //   16: getfield closed : Z
    //   19: ifeq -> 47
    //   22: aload_0
    //   23: getfield sink : Lokhttp3/internal/http2/Http2Stream$FramingSink;
    //   26: getfield finished : Z
    //   29: ifne -> 42
    //   32: aload_0
    //   33: getfield sink : Lokhttp3/internal/http2/Http2Stream$FramingSink;
    //   36: getfield closed : Z
    //   39: ifeq -> 47
    //   42: iconst_1
    //   43: istore_1
    //   44: goto -> 49
    //   47: iconst_0
    //   48: istore_1
    //   49: aload_0
    //   50: invokevirtual isOpen : ()Z
    //   53: istore_2
    //   54: aload_0
    //   55: monitorexit
    //   56: iload_1
    //   57: ifeq -> 70
    //   60: aload_0
    //   61: getstatic okhttp3/internal/http2/ErrorCode.CANCEL : Lokhttp3/internal/http2/ErrorCode;
    //   64: invokevirtual close : (Lokhttp3/internal/http2/ErrorCode;)V
    //   67: goto -> 86
    //   70: iload_2
    //   71: ifne -> 86
    //   74: aload_0
    //   75: getfield connection : Lokhttp3/internal/http2/Http2Connection;
    //   78: aload_0
    //   79: getfield id : I
    //   82: invokevirtual removeStream : (I)Lokhttp3/internal/http2/Http2Stream;
    //   85: pop
    //   86: return
    //   87: astore_3
    //   88: aload_0
    //   89: monitorexit
    //   90: aload_3
    //   91: athrow
    // Exception table:
    //   from	to	target	type
    //   2	42	87	finally
    //   49	56	87	finally
    //   88	90	87	finally
  }
  
  void checkOutNotClosed() throws IOException {
    if (!this.sink.closed) {
      if (!this.sink.finished) {
        if (this.errorCode == null)
          return; 
        throw new StreamResetException(this.errorCode);
      } 
      throw new IOException("stream finished");
    } 
    throw new IOException("stream closed");
  }
  
  public void close(ErrorCode paramErrorCode) throws IOException {
    if (!closeInternal(paramErrorCode))
      return; 
    this.connection.writeSynReset(this.id, paramErrorCode);
  }
  
  public void closeLater(ErrorCode paramErrorCode) {
    if (!closeInternal(paramErrorCode))
      return; 
    this.connection.writeSynResetLater(this.id, paramErrorCode);
  }
  
  public Http2Connection getConnection() {
    return this.connection;
  }
  
  public ErrorCode getErrorCode() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   6: astore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: aload_1
    //   10: areturn
    //   11: astore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_1
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public int getId() {
    return this.id;
  }
  
  public List<Header> getRequestHeaders() {
    return this.requestHeaders;
  }
  
  public Sink getSink() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield hasResponseHeaders : Z
    //   6: ifne -> 31
    //   9: aload_0
    //   10: invokevirtual isLocallyInitiated : ()Z
    //   13: ifeq -> 19
    //   16: goto -> 31
    //   19: new java/lang/IllegalStateException
    //   22: astore_1
    //   23: aload_1
    //   24: ldc 'reply before requesting the sink'
    //   26: invokespecial <init> : (Ljava/lang/String;)V
    //   29: aload_1
    //   30: athrow
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_0
    //   34: getfield sink : Lokhttp3/internal/http2/Http2Stream$FramingSink;
    //   37: areturn
    //   38: astore_1
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_1
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	38	finally
    //   19	31	38	finally
    //   31	33	38	finally
    //   39	41	38	finally
  }
  
  public Source getSource() {
    return this.source;
  }
  
  public boolean isLocallyInitiated() {
    boolean bool;
    int i = this.id;
    boolean bool1 = true;
    if ((i & 0x1) == 1) {
      bool = true;
    } else {
      bool = false;
    } 
    if (this.connection.client == bool) {
      bool = bool1;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOpen() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull -> 15
    //   11: aload_0
    //   12: monitorexit
    //   13: iconst_0
    //   14: ireturn
    //   15: aload_0
    //   16: getfield source : Lokhttp3/internal/http2/Http2Stream$FramingSource;
    //   19: getfield finished : Z
    //   22: ifne -> 35
    //   25: aload_0
    //   26: getfield source : Lokhttp3/internal/http2/Http2Stream$FramingSource;
    //   29: getfield closed : Z
    //   32: ifeq -> 68
    //   35: aload_0
    //   36: getfield sink : Lokhttp3/internal/http2/Http2Stream$FramingSink;
    //   39: getfield finished : Z
    //   42: ifne -> 55
    //   45: aload_0
    //   46: getfield sink : Lokhttp3/internal/http2/Http2Stream$FramingSink;
    //   49: getfield closed : Z
    //   52: ifeq -> 68
    //   55: aload_0
    //   56: getfield hasResponseHeaders : Z
    //   59: istore_1
    //   60: iload_1
    //   61: ifeq -> 68
    //   64: aload_0
    //   65: monitorexit
    //   66: iconst_0
    //   67: ireturn
    //   68: aload_0
    //   69: monitorexit
    //   70: iconst_1
    //   71: ireturn
    //   72: astore_2
    //   73: aload_0
    //   74: monitorexit
    //   75: aload_2
    //   76: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	72	finally
    //   15	35	72	finally
    //   35	55	72	finally
    //   55	60	72	finally
  }
  
  public Timeout readTimeout() {
    return (Timeout)this.readTimeout;
  }
  
  void receiveData(BufferedSource paramBufferedSource, int paramInt) throws IOException {
    this.source.receive(paramBufferedSource, paramInt);
  }
  
  void receiveFin() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield source : Lokhttp3/internal/http2/Http2Stream$FramingSource;
    //   6: iconst_1
    //   7: putfield finished : Z
    //   10: aload_0
    //   11: invokevirtual isOpen : ()Z
    //   14: istore_1
    //   15: aload_0
    //   16: invokevirtual notifyAll : ()V
    //   19: aload_0
    //   20: monitorexit
    //   21: iload_1
    //   22: ifne -> 37
    //   25: aload_0
    //   26: getfield connection : Lokhttp3/internal/http2/Http2Connection;
    //   29: aload_0
    //   30: getfield id : I
    //   33: invokevirtual removeStream : (I)Lokhttp3/internal/http2/Http2Stream;
    //   36: pop
    //   37: return
    //   38: astore_2
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_2
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   2	21	38	finally
    //   39	41	38	finally
  }
  
  void receiveHeaders(List<Header> paramList) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_1
    //   3: istore_2
    //   4: aload_0
    //   5: iconst_1
    //   6: putfield hasResponseHeaders : Z
    //   9: aload_0
    //   10: getfield responseHeaders : Ljava/util/List;
    //   13: ifnonnull -> 33
    //   16: aload_0
    //   17: aload_1
    //   18: putfield responseHeaders : Ljava/util/List;
    //   21: aload_0
    //   22: invokevirtual isOpen : ()Z
    //   25: istore_2
    //   26: aload_0
    //   27: invokevirtual notifyAll : ()V
    //   30: goto -> 73
    //   33: new java/util/ArrayList
    //   36: astore_3
    //   37: aload_3
    //   38: invokespecial <init> : ()V
    //   41: aload_3
    //   42: aload_0
    //   43: getfield responseHeaders : Ljava/util/List;
    //   46: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   51: pop
    //   52: aload_3
    //   53: aconst_null
    //   54: invokeinterface add : (Ljava/lang/Object;)Z
    //   59: pop
    //   60: aload_3
    //   61: aload_1
    //   62: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   67: pop
    //   68: aload_0
    //   69: aload_3
    //   70: putfield responseHeaders : Ljava/util/List;
    //   73: aload_0
    //   74: monitorexit
    //   75: iload_2
    //   76: ifne -> 91
    //   79: aload_0
    //   80: getfield connection : Lokhttp3/internal/http2/Http2Connection;
    //   83: aload_0
    //   84: getfield id : I
    //   87: invokevirtual removeStream : (I)Lokhttp3/internal/http2/Http2Stream;
    //   90: pop
    //   91: return
    //   92: astore_1
    //   93: aload_0
    //   94: monitorexit
    //   95: aload_1
    //   96: athrow
    // Exception table:
    //   from	to	target	type
    //   4	30	92	finally
    //   33	73	92	finally
    //   73	75	92	finally
    //   93	95	92	finally
  }
  
  void receiveRstStream(ErrorCode paramErrorCode) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   6: ifnonnull -> 18
    //   9: aload_0
    //   10: aload_1
    //   11: putfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   14: aload_0
    //   15: invokevirtual notifyAll : ()V
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: astore_1
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_1
    //   25: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	21	finally
  }
  
  public void sendResponseHeaders(List<Header> paramList, boolean paramBoolean) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 59
    //   4: iconst_0
    //   5: istore_3
    //   6: aload_0
    //   7: monitorenter
    //   8: aload_0
    //   9: iconst_1
    //   10: putfield hasResponseHeaders : Z
    //   13: iload_2
    //   14: ifne -> 27
    //   17: aload_0
    //   18: getfield sink : Lokhttp3/internal/http2/Http2Stream$FramingSink;
    //   21: iconst_1
    //   22: putfield finished : Z
    //   25: iconst_1
    //   26: istore_3
    //   27: aload_0
    //   28: monitorexit
    //   29: aload_0
    //   30: getfield connection : Lokhttp3/internal/http2/Http2Connection;
    //   33: aload_0
    //   34: getfield id : I
    //   37: iload_3
    //   38: aload_1
    //   39: invokevirtual writeSynReply : (IZLjava/util/List;)V
    //   42: iload_3
    //   43: ifeq -> 53
    //   46: aload_0
    //   47: getfield connection : Lokhttp3/internal/http2/Http2Connection;
    //   50: invokevirtual flush : ()V
    //   53: return
    //   54: astore_1
    //   55: aload_0
    //   56: monitorexit
    //   57: aload_1
    //   58: athrow
    //   59: new java/lang/NullPointerException
    //   62: dup
    //   63: ldc 'responseHeaders == null'
    //   65: invokespecial <init> : (Ljava/lang/String;)V
    //   68: athrow
    // Exception table:
    //   from	to	target	type
    //   8	13	54	finally
    //   17	25	54	finally
    //   27	29	54	finally
    //   55	57	54	finally
  }
  
  public List<Header> takeResponseHeaders() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isLocallyInitiated : ()Z
    //   6: ifeq -> 86
    //   9: aload_0
    //   10: getfield readTimeout : Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   13: invokevirtual enter : ()V
    //   16: aload_0
    //   17: getfield responseHeaders : Ljava/util/List;
    //   20: ifnonnull -> 37
    //   23: aload_0
    //   24: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   27: ifnonnull -> 37
    //   30: aload_0
    //   31: invokevirtual waitForIo : ()V
    //   34: goto -> 16
    //   37: aload_0
    //   38: getfield readTimeout : Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   41: invokevirtual exitAndThrowIfTimedOut : ()V
    //   44: aload_0
    //   45: getfield responseHeaders : Ljava/util/List;
    //   48: astore_1
    //   49: aload_1
    //   50: ifnull -> 62
    //   53: aload_0
    //   54: aconst_null
    //   55: putfield responseHeaders : Ljava/util/List;
    //   58: aload_0
    //   59: monitorexit
    //   60: aload_1
    //   61: areturn
    //   62: new okhttp3/internal/http2/StreamResetException
    //   65: astore_1
    //   66: aload_1
    //   67: aload_0
    //   68: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   71: invokespecial <init> : (Lokhttp3/internal/http2/ErrorCode;)V
    //   74: aload_1
    //   75: athrow
    //   76: astore_1
    //   77: aload_0
    //   78: getfield readTimeout : Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   81: invokevirtual exitAndThrowIfTimedOut : ()V
    //   84: aload_1
    //   85: athrow
    //   86: new java/lang/IllegalStateException
    //   89: astore_1
    //   90: aload_1
    //   91: ldc 'servers cannot read response headers'
    //   93: invokespecial <init> : (Ljava/lang/String;)V
    //   96: aload_1
    //   97: athrow
    //   98: astore_1
    //   99: aload_0
    //   100: monitorexit
    //   101: aload_1
    //   102: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	98	finally
    //   16	34	76	finally
    //   37	49	98	finally
    //   53	58	98	finally
    //   62	76	98	finally
    //   77	86	98	finally
    //   86	98	98	finally
  }
  
  void waitForIo() throws InterruptedIOException {
    try {
      wait();
      return;
    } catch (InterruptedException interruptedException) {
      throw new InterruptedIOException();
    } 
  }
  
  public Timeout writeTimeout() {
    return (Timeout)this.writeTimeout;
  }
  
  final class FramingSink implements Sink {
    static final boolean $assertionsDisabled = false;
    
    private static final long EMIT_BUFFER_SIZE = 16384L;
    
    boolean closed;
    
    boolean finished;
    
    private final Buffer sendBuffer = new Buffer();
    
    final Http2Stream this$0;
    
    private void emitFrame(boolean param1Boolean) throws IOException {
      synchronized (Http2Stream.this) {
        Http2Stream.this.writeTimeout.enter();
        try {
          while (Http2Stream.this.bytesLeftInWriteWindow <= 0L && !this.finished && !this.closed && Http2Stream.this.errorCode == null)
            Http2Stream.this.waitForIo(); 
          Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
          Http2Stream.this.checkOutNotClosed();
          long l = Math.min(Http2Stream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
          Http2Stream http2Stream = Http2Stream.this;
          http2Stream.bytesLeftInWriteWindow -= l;
          Http2Stream.this.writeTimeout.enter();
        } finally {
          Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
        } 
      } 
    }
    
    public void close() throws IOException {
      synchronized (Http2Stream.this) {
        if (this.closed)
          return; 
        if (!Http2Stream.this.sink.finished)
          if (this.sendBuffer.size() > 0L) {
            while (this.sendBuffer.size() > 0L)
              emitFrame(true); 
          } else {
            Http2Stream.this.connection.writeData(Http2Stream.this.id, true, null, 0L);
          }  
        synchronized (Http2Stream.this) {
          this.closed = true;
          Http2Stream.this.connection.flush();
          Http2Stream.this.cancelStreamIfNecessary();
          return;
        } 
      } 
    }
    
    public void flush() throws IOException {
      synchronized (Http2Stream.this) {
        Http2Stream.this.checkOutNotClosed();
        while (this.sendBuffer.size() > 0L) {
          emitFrame(false);
          Http2Stream.this.connection.flush();
        } 
        return;
      } 
    }
    
    public Timeout timeout() {
      return (Timeout)Http2Stream.this.writeTimeout;
    }
    
    public void write(Buffer param1Buffer, long param1Long) throws IOException {
      this.sendBuffer.write(param1Buffer, param1Long);
      while (this.sendBuffer.size() >= 16384L)
        emitFrame(false); 
    }
  }
  
  private final class FramingSource implements Source {
    static final boolean $assertionsDisabled = false;
    
    boolean closed;
    
    boolean finished;
    
    private final long maxByteCount;
    
    private final Buffer readBuffer = new Buffer();
    
    private final Buffer receiveBuffer = new Buffer();
    
    final Http2Stream this$0;
    
    FramingSource(long param1Long) {
      this.maxByteCount = param1Long;
    }
    
    private void checkNotClosed() throws IOException {
      if (!this.closed) {
        if (Http2Stream.this.errorCode == null)
          return; 
        throw new StreamResetException(Http2Stream.this.errorCode);
      } 
      throw new IOException("stream closed");
    }
    
    private void waitUntilReadable() throws IOException {
      Http2Stream.this.readTimeout.enter();
      try {
        while (this.readBuffer.size() == 0L && !this.finished && !this.closed && Http2Stream.this.errorCode == null)
          Http2Stream.this.waitForIo(); 
        return;
      } finally {
        Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
      } 
    }
    
    public void close() throws IOException {
      synchronized (Http2Stream.this) {
        this.closed = true;
        this.readBuffer.clear();
        Http2Stream.this.notifyAll();
        Http2Stream.this.cancelStreamIfNecessary();
        return;
      } 
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      if (param1Long >= 0L)
        synchronized (Http2Stream.this) {
          waitUntilReadable();
          checkNotClosed();
          if (this.readBuffer.size() == 0L)
            return -1L; 
          param1Long = this.readBuffer.read(param1Buffer, Math.min(param1Long, this.readBuffer.size()));
          Http2Stream http2Stream = Http2Stream.this;
          http2Stream.unacknowledgedBytesRead += param1Long;
          if (Http2Stream.this.unacknowledgedBytesRead >= (Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2)) {
            Http2Stream.this.connection.writeWindowUpdateLater(Http2Stream.this.id, Http2Stream.this.unacknowledgedBytesRead);
            Http2Stream.this.unacknowledgedBytesRead = 0L;
          } 
          synchronized (Http2Stream.this.connection) {
            Http2Connection http2Connection = Http2Stream.this.connection;
            http2Connection.unacknowledgedBytesRead += param1Long;
            if (Http2Stream.this.connection.unacknowledgedBytesRead >= (Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2)) {
              Http2Stream.this.connection.writeWindowUpdateLater(0, Http2Stream.this.connection.unacknowledgedBytesRead);
              Http2Stream.this.connection.unacknowledgedBytesRead = 0L;
            } 
            return param1Long;
          } 
        }  
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("byteCount < 0: ");
      stringBuilder.append(param1Long);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    void receive(BufferedSource param1BufferedSource, long param1Long) throws IOException {
      while (param1Long > 0L) {
        synchronized (Http2Stream.this) {
          boolean bool1;
          boolean bool = this.finished;
          long l1 = this.readBuffer.size();
          long l2 = this.maxByteCount;
          boolean bool2 = true;
          if (l1 + param1Long > l2) {
            bool1 = true;
          } else {
            bool1 = false;
          } 
          if (bool1) {
            param1BufferedSource.skip(param1Long);
            Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
            return;
          } 
          if (bool) {
            param1BufferedSource.skip(param1Long);
            return;
          } 
          l1 = param1BufferedSource.read(this.receiveBuffer, param1Long);
          if (l1 != -1L) {
            param1Long -= l1;
            synchronized (Http2Stream.this) {
              if (this.readBuffer.size() == 0L) {
                bool1 = bool2;
              } else {
                bool1 = false;
              } 
              this.readBuffer.writeAll((Source)this.receiveBuffer);
              if (bool1)
                Http2Stream.this.notifyAll(); 
            } 
            continue;
          } 
          throw new EOFException();
        } 
      } 
    }
    
    public Timeout timeout() {
      return (Timeout)Http2Stream.this.readTimeout;
    }
  }
  
  class StreamTimeout extends AsyncTimeout {
    final Http2Stream this$0;
    
    public void exitAndThrowIfTimedOut() throws IOException {
      if (!exit())
        return; 
      throw newTimeoutException(null);
    }
    
    protected IOException newTimeoutException(IOException param1IOException) {
      SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
      if (param1IOException != null)
        socketTimeoutException.initCause(param1IOException); 
      return socketTimeoutException;
    }
    
    protected void timedOut() {
      Http2Stream.this.closeLater(ErrorCode.CANCEL);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\Http2Stream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */