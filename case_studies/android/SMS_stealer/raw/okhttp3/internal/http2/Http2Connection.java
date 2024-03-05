package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Protocol;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class Http2Connection implements Closeable {
  static final boolean $assertionsDisabled = false;
  
  private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
  
  private static final ExecutorService listenerExecutor = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Http2Connection", true));
  
  private boolean awaitingPong;
  
  long bytesLeftInWriteWindow;
  
  final boolean client;
  
  final Set<Integer> currentPushRequests;
  
  final String hostname;
  
  int lastGoodStreamId;
  
  final Listener listener;
  
  int nextStreamId;
  
  Settings okHttpSettings;
  
  final Settings peerSettings;
  
  private final ExecutorService pushExecutor;
  
  final PushObserver pushObserver;
  
  final ReaderRunnable readerRunnable;
  
  boolean receivedInitialPeerSettings;
  
  boolean shutdown;
  
  final Socket socket;
  
  final Map<Integer, Http2Stream> streams;
  
  long unacknowledgedBytesRead;
  
  final Http2Writer writer;
  
  private final ScheduledExecutorService writerExecutor;
  
  Http2Connection(Builder paramBuilder) {
    byte b;
    this.streams = new LinkedHashMap<Integer, Http2Stream>();
    this.unacknowledgedBytesRead = 0L;
    this.okHttpSettings = new Settings();
    this.peerSettings = new Settings();
    this.receivedInitialPeerSettings = false;
    this.currentPushRequests = new LinkedHashSet<Integer>();
    this.pushObserver = paramBuilder.pushObserver;
    this.client = paramBuilder.client;
    this.listener = paramBuilder.listener;
    if (paramBuilder.client) {
      b = 1;
    } else {
      b = 2;
    } 
    this.nextStreamId = b;
    if (paramBuilder.client)
      this.nextStreamId += 2; 
    if (paramBuilder.client)
      this.okHttpSettings.set(7, 16777216); 
    this.hostname = paramBuilder.hostname;
    this.writerExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(Util.format("OkHttp %s Writer", new Object[] { this.hostname }), false));
    if (paramBuilder.pingIntervalMillis != 0)
      this.writerExecutor.scheduleAtFixedRate((Runnable)new PingRunnable(false, 0, 0), paramBuilder.pingIntervalMillis, paramBuilder.pingIntervalMillis, TimeUnit.MILLISECONDS); 
    this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory(Util.format("OkHttp %s Push Observer", new Object[] { this.hostname }), true));
    this.peerSettings.set(7, 65535);
    this.peerSettings.set(5, 16384);
    this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize();
    this.socket = paramBuilder.socket;
    this.writer = new Http2Writer(paramBuilder.sink, this.client);
    this.readerRunnable = new ReaderRunnable(new Http2Reader(paramBuilder.source, this.client));
  }
  
  private void failConnection() {
    try {
      close(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR);
    } catch (IOException iOException) {}
  }
  
  private Http2Stream newStream(int paramInt, List<Header> paramList, boolean paramBoolean) throws IOException {
    // Byte code:
    //   0: iload_3
    //   1: iconst_1
    //   2: ixor
    //   3: istore #6
    //   5: aload_0
    //   6: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   9: astore #7
    //   11: aload #7
    //   13: monitorenter
    //   14: aload_0
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield nextStreamId : I
    //   20: ldc 1073741823
    //   22: if_icmple -> 32
    //   25: aload_0
    //   26: getstatic okhttp3/internal/http2/ErrorCode.REFUSED_STREAM : Lokhttp3/internal/http2/ErrorCode;
    //   29: invokevirtual shutdown : (Lokhttp3/internal/http2/ErrorCode;)V
    //   32: aload_0
    //   33: getfield shutdown : Z
    //   36: ifne -> 203
    //   39: aload_0
    //   40: getfield nextStreamId : I
    //   43: istore #5
    //   45: aload_0
    //   46: aload_0
    //   47: getfield nextStreamId : I
    //   50: iconst_2
    //   51: iadd
    //   52: putfield nextStreamId : I
    //   55: new okhttp3/internal/http2/Http2Stream
    //   58: astore #8
    //   60: aload #8
    //   62: iload #5
    //   64: aload_0
    //   65: iload #6
    //   67: iconst_0
    //   68: aload_2
    //   69: invokespecial <init> : (ILokhttp3/internal/http2/Http2Connection;ZZLjava/util/List;)V
    //   72: iload_3
    //   73: ifeq -> 104
    //   76: aload_0
    //   77: getfield bytesLeftInWriteWindow : J
    //   80: lconst_0
    //   81: lcmp
    //   82: ifeq -> 104
    //   85: aload #8
    //   87: getfield bytesLeftInWriteWindow : J
    //   90: lconst_0
    //   91: lcmp
    //   92: ifne -> 98
    //   95: goto -> 104
    //   98: iconst_0
    //   99: istore #4
    //   101: goto -> 107
    //   104: iconst_1
    //   105: istore #4
    //   107: aload #8
    //   109: invokevirtual isOpen : ()Z
    //   112: ifeq -> 132
    //   115: aload_0
    //   116: getfield streams : Ljava/util/Map;
    //   119: iload #5
    //   121: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   124: aload #8
    //   126: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   131: pop
    //   132: aload_0
    //   133: monitorexit
    //   134: iload_1
    //   135: ifne -> 154
    //   138: aload_0
    //   139: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   142: iload #6
    //   144: iload #5
    //   146: iload_1
    //   147: aload_2
    //   148: invokevirtual synStream : (ZIILjava/util/List;)V
    //   151: goto -> 172
    //   154: aload_0
    //   155: getfield client : Z
    //   158: ifne -> 190
    //   161: aload_0
    //   162: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   165: iload_1
    //   166: iload #5
    //   168: aload_2
    //   169: invokevirtual pushPromise : (IILjava/util/List;)V
    //   172: aload #7
    //   174: monitorexit
    //   175: iload #4
    //   177: ifeq -> 187
    //   180: aload_0
    //   181: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   184: invokevirtual flush : ()V
    //   187: aload #8
    //   189: areturn
    //   190: new java/lang/IllegalArgumentException
    //   193: astore_2
    //   194: aload_2
    //   195: ldc_w 'client streams shouldn't have associated stream IDs'
    //   198: invokespecial <init> : (Ljava/lang/String;)V
    //   201: aload_2
    //   202: athrow
    //   203: new okhttp3/internal/http2/ConnectionShutdownException
    //   206: astore_2
    //   207: aload_2
    //   208: invokespecial <init> : ()V
    //   211: aload_2
    //   212: athrow
    //   213: astore_2
    //   214: aload_0
    //   215: monitorexit
    //   216: aload_2
    //   217: athrow
    //   218: astore_2
    //   219: aload #7
    //   221: monitorexit
    //   222: aload_2
    //   223: athrow
    // Exception table:
    //   from	to	target	type
    //   14	16	218	finally
    //   16	32	213	finally
    //   32	72	213	finally
    //   76	95	213	finally
    //   107	132	213	finally
    //   132	134	213	finally
    //   138	151	218	finally
    //   154	172	218	finally
    //   172	175	218	finally
    //   190	203	218	finally
    //   203	213	213	finally
    //   214	216	213	finally
    //   216	218	218	finally
    //   219	222	218	finally
  }
  
  void addBytesToWriteWindow(long paramLong) {
    this.bytesLeftInWriteWindow += paramLong;
    if (paramLong > 0L)
      notifyAll(); 
  }
  
  void awaitPong() throws IOException, InterruptedException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield awaitingPong : Z
    //   6: ifeq -> 16
    //   9: aload_0
    //   10: invokevirtual wait : ()V
    //   13: goto -> 2
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: astore_1
    //   20: aload_0
    //   21: monitorexit
    //   22: aload_1
    //   23: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	19	finally
  }
  
  public void close() throws IOException {
    close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
  }
  
  void close(ErrorCode paramErrorCode1, ErrorCode paramErrorCode2) throws IOException {
    // Byte code:
    //   0: aconst_null
    //   1: astore #6
    //   3: aload_0
    //   4: aload_1
    //   5: invokevirtual shutdown : (Lokhttp3/internal/http2/ErrorCode;)V
    //   8: aconst_null
    //   9: astore_1
    //   10: goto -> 14
    //   13: astore_1
    //   14: aload_0
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield streams : Ljava/util/Map;
    //   20: invokeinterface isEmpty : ()Z
    //   25: ifne -> 68
    //   28: aload_0
    //   29: getfield streams : Ljava/util/Map;
    //   32: invokeinterface values : ()Ljava/util/Collection;
    //   37: aload_0
    //   38: getfield streams : Ljava/util/Map;
    //   41: invokeinterface size : ()I
    //   46: anewarray okhttp3/internal/http2/Http2Stream
    //   49: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   54: checkcast [Lokhttp3/internal/http2/Http2Stream;
    //   57: astore #6
    //   59: aload_0
    //   60: getfield streams : Ljava/util/Map;
    //   63: invokeinterface clear : ()V
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_1
    //   71: astore #5
    //   73: aload #6
    //   75: ifnull -> 134
    //   78: aload #6
    //   80: arraylength
    //   81: istore #4
    //   83: iconst_0
    //   84: istore_3
    //   85: aload_1
    //   86: astore #5
    //   88: iload_3
    //   89: iload #4
    //   91: if_icmpge -> 134
    //   94: aload #6
    //   96: iload_3
    //   97: aaload
    //   98: astore #5
    //   100: aload #5
    //   102: aload_2
    //   103: invokevirtual close : (Lokhttp3/internal/http2/ErrorCode;)V
    //   106: aload_1
    //   107: astore #5
    //   109: goto -> 125
    //   112: astore #7
    //   114: aload_1
    //   115: astore #5
    //   117: aload_1
    //   118: ifnull -> 125
    //   121: aload #7
    //   123: astore #5
    //   125: iinc #3, 1
    //   128: aload #5
    //   130: astore_1
    //   131: goto -> 85
    //   134: aload_0
    //   135: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   138: invokevirtual close : ()V
    //   141: aload #5
    //   143: astore_1
    //   144: goto -> 158
    //   147: astore_2
    //   148: aload #5
    //   150: astore_1
    //   151: aload #5
    //   153: ifnonnull -> 158
    //   156: aload_2
    //   157: astore_1
    //   158: aload_0
    //   159: getfield socket : Ljava/net/Socket;
    //   162: invokevirtual close : ()V
    //   165: goto -> 169
    //   168: astore_1
    //   169: aload_0
    //   170: getfield writerExecutor : Ljava/util/concurrent/ScheduledExecutorService;
    //   173: invokeinterface shutdown : ()V
    //   178: aload_0
    //   179: getfield pushExecutor : Ljava/util/concurrent/ExecutorService;
    //   182: invokeinterface shutdown : ()V
    //   187: aload_1
    //   188: ifnonnull -> 192
    //   191: return
    //   192: aload_1
    //   193: athrow
    //   194: astore_1
    //   195: aload_0
    //   196: monitorexit
    //   197: aload_1
    //   198: athrow
    // Exception table:
    //   from	to	target	type
    //   3	8	13	java/io/IOException
    //   16	68	194	finally
    //   68	70	194	finally
    //   100	106	112	java/io/IOException
    //   134	141	147	java/io/IOException
    //   158	165	168	java/io/IOException
    //   195	197	194	finally
  }
  
  public void flush() throws IOException {
    this.writer.flush();
  }
  
  public Protocol getProtocol() {
    return Protocol.HTTP_2;
  }
  
  Http2Stream getStream(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield streams : Ljava/util/Map;
    //   6: iload_1
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   15: checkcast okhttp3/internal/http2/Http2Stream
    //   18: astore_2
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_2
    //   22: areturn
    //   23: astore_2
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_2
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	23	finally
  }
  
  public boolean isShutdown() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield shutdown : Z
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public int maxConcurrentStreams() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield peerSettings : Lokhttp3/internal/http2/Settings;
    //   6: ldc 2147483647
    //   8: invokevirtual getMaxConcurrentStreams : (I)I
    //   11: istore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: iload_1
    //   15: ireturn
    //   16: astore_2
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_2
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	16	finally
  }
  
  public Http2Stream newStream(List<Header> paramList, boolean paramBoolean) throws IOException {
    return newStream(0, paramList, paramBoolean);
  }
  
  public int openStreamCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield streams : Ljava/util/Map;
    //   6: invokeinterface size : ()I
    //   11: istore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: iload_1
    //   15: ireturn
    //   16: astore_2
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_2
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	16	finally
  }
  
  void pushDataLater(final int streamId, BufferedSource paramBufferedSource, final int byteCount, final boolean inFinished) throws IOException {
    final Buffer buffer = new Buffer();
    long l = byteCount;
    paramBufferedSource.require(l);
    paramBufferedSource.read(buffer, l);
    if (buffer.size() == l) {
      this.pushExecutor.execute((Runnable)new NamedRunnable("OkHttp %s Push Data[%s]", new Object[] { this.hostname, Integer.valueOf(streamId) }) {
            final Http2Connection this$0;
            
            final Buffer val$buffer;
            
            final int val$byteCount;
            
            final boolean val$inFinished;
            
            final int val$streamId;
            
            public void execute() {
              try {
                boolean bool = Http2Connection.this.pushObserver.onData(streamId, (BufferedSource)buffer, byteCount, inFinished);
                if (bool)
                  Http2Connection.this.writer.rstStream(streamId, ErrorCode.CANCEL); 
                if (bool || inFinished)
                  synchronized (Http2Connection.this) {
                    Http2Connection.this.currentPushRequests.remove(Integer.valueOf(streamId));
                  }  
              } catch (IOException iOException) {}
            }
          });
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(buffer.size());
    stringBuilder.append(" != ");
    stringBuilder.append(byteCount);
    throw new IOException(stringBuilder.toString());
  }
  
  void pushHeadersLater(int paramInt, List<Header> paramList, boolean paramBoolean) {
    try {
      ExecutorService executorService = this.pushExecutor;
      NamedRunnable namedRunnable = new NamedRunnable() {
          final Http2Connection this$0;
          
          final boolean val$inFinished;
          
          final List val$requestHeaders;
          
          final int val$streamId;
          
          public void execute() {
            // Byte code:
            //   0: aload_0
            //   1: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
            //   4: getfield pushObserver : Lokhttp3/internal/http2/PushObserver;
            //   7: aload_0
            //   8: getfield val$streamId : I
            //   11: aload_0
            //   12: getfield val$requestHeaders : Ljava/util/List;
            //   15: aload_0
            //   16: getfield val$inFinished : Z
            //   19: invokeinterface onHeaders : (ILjava/util/List;Z)Z
            //   24: istore_1
            //   25: iload_1
            //   26: ifeq -> 46
            //   29: aload_0
            //   30: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
            //   33: getfield writer : Lokhttp3/internal/http2/Http2Writer;
            //   36: aload_0
            //   37: getfield val$streamId : I
            //   40: getstatic okhttp3/internal/http2/ErrorCode.CANCEL : Lokhttp3/internal/http2/ErrorCode;
            //   43: invokevirtual rstStream : (ILokhttp3/internal/http2/ErrorCode;)V
            //   46: iload_1
            //   47: ifne -> 57
            //   50: aload_0
            //   51: getfield val$inFinished : Z
            //   54: ifeq -> 94
            //   57: aload_0
            //   58: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
            //   61: astore_2
            //   62: aload_2
            //   63: monitorenter
            //   64: aload_0
            //   65: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
            //   68: getfield currentPushRequests : Ljava/util/Set;
            //   71: aload_0
            //   72: getfield val$streamId : I
            //   75: invokestatic valueOf : (I)Ljava/lang/Integer;
            //   78: invokeinterface remove : (Ljava/lang/Object;)Z
            //   83: pop
            //   84: aload_2
            //   85: monitorexit
            //   86: goto -> 94
            //   89: astore_3
            //   90: aload_2
            //   91: monitorexit
            //   92: aload_3
            //   93: athrow
            //   94: return
            //   95: astore_2
            //   96: goto -> 94
            // Exception table:
            //   from	to	target	type
            //   29	46	95	java/io/IOException
            //   50	57	95	java/io/IOException
            //   57	64	95	java/io/IOException
            //   64	86	89	finally
            //   90	92	89	finally
            //   92	94	95	java/io/IOException
          }
        };
      super(this, "OkHttp %s Push Headers[%s]", new Object[] { this.hostname, Integer.valueOf(paramInt) }, paramInt, paramList, paramBoolean);
      executorService.execute((Runnable)namedRunnable);
    } catch (RejectedExecutionException rejectedExecutionException) {}
  }
  
  void pushRequestLater(int paramInt, List<Header> paramList) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield currentPushRequests : Ljava/util/Set;
    //   6: iload_1
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokeinterface contains : (Ljava/lang/Object;)Z
    //   15: ifeq -> 29
    //   18: aload_0
    //   19: iload_1
    //   20: getstatic okhttp3/internal/http2/ErrorCode.PROTOCOL_ERROR : Lokhttp3/internal/http2/ErrorCode;
    //   23: invokevirtual writeSynResetLater : (ILokhttp3/internal/http2/ErrorCode;)V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: aload_0
    //   30: getfield currentPushRequests : Ljava/util/Set;
    //   33: iload_1
    //   34: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   37: invokeinterface add : (Ljava/lang/Object;)Z
    //   42: pop
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_0
    //   46: getfield pushExecutor : Ljava/util/concurrent/ExecutorService;
    //   49: astore_3
    //   50: new okhttp3/internal/http2/Http2Connection$3
    //   53: astore #4
    //   55: aload #4
    //   57: aload_0
    //   58: ldc_w 'OkHttp %s Push Request[%s]'
    //   61: iconst_2
    //   62: anewarray java/lang/Object
    //   65: dup
    //   66: iconst_0
    //   67: aload_0
    //   68: getfield hostname : Ljava/lang/String;
    //   71: aastore
    //   72: dup
    //   73: iconst_1
    //   74: iload_1
    //   75: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   78: aastore
    //   79: iload_1
    //   80: aload_2
    //   81: invokespecial <init> : (Lokhttp3/internal/http2/Http2Connection;Ljava/lang/String;[Ljava/lang/Object;ILjava/util/List;)V
    //   84: aload_3
    //   85: aload #4
    //   87: invokeinterface execute : (Ljava/lang/Runnable;)V
    //   92: return
    //   93: astore_2
    //   94: aload_0
    //   95: monitorexit
    //   96: aload_2
    //   97: athrow
    //   98: astore_2
    //   99: goto -> 92
    // Exception table:
    //   from	to	target	type
    //   2	28	93	finally
    //   29	45	93	finally
    //   45	92	98	java/util/concurrent/RejectedExecutionException
    //   94	96	93	finally
  }
  
  void pushResetLater(final int streamId, final ErrorCode errorCode) {
    this.pushExecutor.execute((Runnable)new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[] { this.hostname, Integer.valueOf(streamId) }) {
          final Http2Connection this$0;
          
          final ErrorCode val$errorCode;
          
          final int val$streamId;
          
          public void execute() {
            Http2Connection.this.pushObserver.onReset(streamId, errorCode);
            synchronized (Http2Connection.this) {
              Http2Connection.this.currentPushRequests.remove(Integer.valueOf(streamId));
              return;
            } 
          }
        });
  }
  
  public Http2Stream pushStream(int paramInt, List<Header> paramList, boolean paramBoolean) throws IOException {
    if (!this.client)
      return newStream(paramInt, paramList, paramBoolean); 
    throw new IllegalStateException("Client cannot push requests.");
  }
  
  boolean pushedStream(int paramInt) {
    boolean bool = true;
    if (paramInt == 0 || (paramInt & 0x1) != 0)
      bool = false; 
    return bool;
  }
  
  Http2Stream removeStream(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield streams : Ljava/util/Map;
    //   6: iload_1
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   15: checkcast okhttp3/internal/http2/Http2Stream
    //   18: astore_2
    //   19: aload_0
    //   20: invokevirtual notifyAll : ()V
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_2
    //   26: areturn
    //   27: astore_2
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_2
    //   31: athrow
    // Exception table:
    //   from	to	target	type
    //   2	23	27	finally
  }
  
  public void setSettings(Settings paramSettings) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield shutdown : Z
    //   13: ifne -> 37
    //   16: aload_0
    //   17: getfield okHttpSettings : Lokhttp3/internal/http2/Settings;
    //   20: aload_1
    //   21: invokevirtual merge : (Lokhttp3/internal/http2/Settings;)V
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_0
    //   27: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   30: aload_1
    //   31: invokevirtual settings : (Lokhttp3/internal/http2/Settings;)V
    //   34: aload_2
    //   35: monitorexit
    //   36: return
    //   37: new okhttp3/internal/http2/ConnectionShutdownException
    //   40: astore_1
    //   41: aload_1
    //   42: invokespecial <init> : ()V
    //   45: aload_1
    //   46: athrow
    //   47: astore_1
    //   48: aload_0
    //   49: monitorexit
    //   50: aload_1
    //   51: athrow
    //   52: astore_1
    //   53: aload_2
    //   54: monitorexit
    //   55: aload_1
    //   56: athrow
    // Exception table:
    //   from	to	target	type
    //   7	9	52	finally
    //   9	26	47	finally
    //   26	36	52	finally
    //   37	47	47	finally
    //   48	50	47	finally
    //   50	52	52	finally
    //   53	55	52	finally
  }
  
  public void shutdown(ErrorCode paramErrorCode) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield shutdown : Z
    //   13: ifeq -> 21
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_3
    //   19: monitorexit
    //   20: return
    //   21: aload_0
    //   22: iconst_1
    //   23: putfield shutdown : Z
    //   26: aload_0
    //   27: getfield lastGoodStreamId : I
    //   30: istore_2
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_0
    //   34: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   37: iload_2
    //   38: aload_1
    //   39: getstatic okhttp3/internal/Util.EMPTY_BYTE_ARRAY : [B
    //   42: invokevirtual goAway : (ILokhttp3/internal/http2/ErrorCode;[B)V
    //   45: aload_3
    //   46: monitorexit
    //   47: return
    //   48: astore_1
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_1
    //   52: athrow
    //   53: astore_1
    //   54: aload_3
    //   55: monitorexit
    //   56: aload_1
    //   57: athrow
    // Exception table:
    //   from	to	target	type
    //   7	9	53	finally
    //   9	18	48	finally
    //   18	20	53	finally
    //   21	33	48	finally
    //   33	47	53	finally
    //   49	51	48	finally
    //   51	53	53	finally
    //   54	56	53	finally
  }
  
  public void start() throws IOException {
    start(true);
  }
  
  void start(boolean paramBoolean) throws IOException {
    if (paramBoolean) {
      this.writer.connectionPreface();
      this.writer.settings(this.okHttpSettings);
      int i = this.okHttpSettings.getInitialWindowSize();
      if (i != 65535)
        this.writer.windowUpdate(0, (i - 65535)); 
    } 
    (new Thread((Runnable)this.readerRunnable)).start();
  }
  
  public void writeData(int paramInt, boolean paramBoolean, Buffer paramBuffer, long paramLong) throws IOException {
    long l = paramLong;
    if (paramLong == 0L) {
      this.writer.data(paramBoolean, paramInt, paramBuffer, 0);
      return;
    } 
    /* monitor enter ThisExpression{ObjectType{okhttp3/internal/http2/Http2Connection}} */
    while (l > 0L) {
      try {
        IOException iOException;
        boolean bool;
        while (this.bytesLeftInWriteWindow <= 0L) {
          if (this.streams.containsKey(Integer.valueOf(paramInt))) {
            wait();
            continue;
          } 
          iOException = new IOException();
          this("stream closed");
          throw iOException;
        } 
        int i = Math.min((int)Math.min(l, this.bytesLeftInWriteWindow), this.writer.maxDataLength());
        paramLong = this.bytesLeftInWriteWindow;
        long l1 = i;
        this.bytesLeftInWriteWindow = paramLong - l1;
        /* monitor exit ThisExpression{ObjectType{okhttp3/internal/http2/Http2Connection}} */
        l -= l1;
        Http2Writer http2Writer = this.writer;
        if (paramBoolean && l == 0L) {
          bool = true;
        } else {
          bool = false;
        } 
        http2Writer.data(bool, paramInt, (Buffer)iOException, i);
        continue;
      } catch (InterruptedException interruptedException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException();
        this();
        throw interruptedIOException;
      } finally {}
      /* monitor exit ThisExpression{ObjectType{okhttp3/internal/http2/Http2Connection}} */
      throw paramBuffer;
    } 
  }
  
  void writePing(boolean paramBoolean, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: ifne -> 36
    //   4: aload_0
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield awaitingPong : Z
    //   10: istore #4
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield awaitingPong : Z
    //   17: aload_0
    //   18: monitorexit
    //   19: iload #4
    //   21: ifeq -> 36
    //   24: aload_0
    //   25: invokespecial failConnection : ()V
    //   28: return
    //   29: astore #5
    //   31: aload_0
    //   32: monitorexit
    //   33: aload #5
    //   35: athrow
    //   36: aload_0
    //   37: getfield writer : Lokhttp3/internal/http2/Http2Writer;
    //   40: iload_1
    //   41: iload_2
    //   42: iload_3
    //   43: invokevirtual ping : (ZII)V
    //   46: goto -> 55
    //   49: astore #5
    //   51: aload_0
    //   52: invokespecial failConnection : ()V
    //   55: return
    // Exception table:
    //   from	to	target	type
    //   6	19	29	finally
    //   31	33	29	finally
    //   36	46	49	java/io/IOException
  }
  
  void writePingAndAwaitPong() throws IOException, InterruptedException {
    writePing(false, 1330343787, -257978967);
    awaitPong();
  }
  
  void writeSynReply(int paramInt, boolean paramBoolean, List<Header> paramList) throws IOException {
    this.writer.synReply(paramBoolean, paramInt, paramList);
  }
  
  void writeSynReset(int paramInt, ErrorCode paramErrorCode) throws IOException {
    this.writer.rstStream(paramInt, paramErrorCode);
  }
  
  void writeSynResetLater(int paramInt, ErrorCode paramErrorCode) {
    try {
      ScheduledExecutorService scheduledExecutorService = this.writerExecutor;
      NamedRunnable namedRunnable = new NamedRunnable() {
          final Http2Connection this$0;
          
          final ErrorCode val$errorCode;
          
          final int val$streamId;
          
          public void execute() {
            try {
              Http2Connection.this.writeSynReset(streamId, errorCode);
            } catch (IOException iOException) {
              Http2Connection.this.failConnection();
            } 
          }
        };
      super(this, "OkHttp %s stream %d", new Object[] { this.hostname, Integer.valueOf(paramInt) }, paramInt, paramErrorCode);
      scheduledExecutorService.execute((Runnable)namedRunnable);
    } catch (RejectedExecutionException rejectedExecutionException) {}
  }
  
  void writeWindowUpdateLater(int paramInt, long paramLong) {
    try {
      ScheduledExecutorService scheduledExecutorService = this.writerExecutor;
      NamedRunnable namedRunnable = new NamedRunnable() {
          final Http2Connection this$0;
          
          final int val$streamId;
          
          final long val$unacknowledgedBytesRead;
          
          public void execute() {
            try {
              Http2Connection.this.writer.windowUpdate(streamId, unacknowledgedBytesRead);
            } catch (IOException iOException) {
              Http2Connection.this.failConnection();
            } 
          }
        };
      super(this, "OkHttp Window Update %s stream %d", new Object[] { this.hostname, Integer.valueOf(paramInt) }, paramInt, paramLong);
      scheduledExecutorService.execute((Runnable)namedRunnable);
    } catch (RejectedExecutionException rejectedExecutionException) {}
  }
  
  public static class Builder {
    boolean client;
    
    String hostname;
    
    Http2Connection.Listener listener = Http2Connection.Listener.REFUSE_INCOMING_STREAMS;
    
    int pingIntervalMillis;
    
    PushObserver pushObserver = PushObserver.CANCEL;
    
    BufferedSink sink;
    
    Socket socket;
    
    BufferedSource source;
    
    public Builder(boolean param1Boolean) {
      this.client = param1Boolean;
    }
    
    public Http2Connection build() {
      return new Http2Connection(this);
    }
    
    public Builder listener(Http2Connection.Listener param1Listener) {
      this.listener = param1Listener;
      return this;
    }
    
    public Builder pingIntervalMillis(int param1Int) {
      this.pingIntervalMillis = param1Int;
      return this;
    }
    
    public Builder pushObserver(PushObserver param1PushObserver) {
      this.pushObserver = param1PushObserver;
      return this;
    }
    
    public Builder socket(Socket param1Socket) throws IOException {
      return socket(param1Socket, ((InetSocketAddress)param1Socket.getRemoteSocketAddress()).getHostName(), Okio.buffer(Okio.source(param1Socket)), Okio.buffer(Okio.sink(param1Socket)));
    }
    
    public Builder socket(Socket param1Socket, String param1String, BufferedSource param1BufferedSource, BufferedSink param1BufferedSink) {
      this.socket = param1Socket;
      this.hostname = param1String;
      this.source = param1BufferedSource;
      this.sink = param1BufferedSink;
      return this;
    }
  }
  
  public static abstract class Listener {
    public static final Listener REFUSE_INCOMING_STREAMS = new Listener() {
        public void onStream(Http2Stream param2Http2Stream) throws IOException {
          param2Http2Stream.close(ErrorCode.REFUSED_STREAM);
        }
      };
    
    public void onSettings(Http2Connection param1Http2Connection) {}
    
    public abstract void onStream(Http2Stream param1Http2Stream) throws IOException;
  }
  
  final class null extends Listener {
    public void onStream(Http2Stream param1Http2Stream) throws IOException {
      param1Http2Stream.close(ErrorCode.REFUSED_STREAM);
    }
  }
  
  final class PingRunnable extends NamedRunnable {
    final int payload1;
    
    final int payload2;
    
    final boolean reply;
    
    final Http2Connection this$0;
    
    PingRunnable(boolean param1Boolean, int param1Int1, int param1Int2) {
      super("OkHttp %s ping %08x%08x", new Object[] { this$0.hostname, Integer.valueOf(param1Int1), Integer.valueOf(param1Int2) });
      this.reply = param1Boolean;
      this.payload1 = param1Int1;
      this.payload2 = param1Int2;
    }
    
    public void execute() {
      Http2Connection.this.writePing(this.reply, this.payload1, this.payload2);
    }
  }
  
  class ReaderRunnable extends NamedRunnable implements Http2Reader.Handler {
    final Http2Reader reader;
    
    final Http2Connection this$0;
    
    ReaderRunnable(Http2Reader param1Http2Reader) {
      super("OkHttp %s", new Object[] { this$0.hostname });
      this.reader = param1Http2Reader;
    }
    
    private void applyAndAckSettings(Settings param1Settings) {
      try {
        ScheduledExecutorService scheduledExecutorService = Http2Connection.this.writerExecutor;
        NamedRunnable namedRunnable = new NamedRunnable() {
            final Http2Connection.ReaderRunnable this$1;
            
            final Settings val$peerSettings;
            
            public void execute() {
              try {
                Http2Connection.this.writer.applyAndAckSettings(peerSettings);
              } catch (IOException iOException) {
                Http2Connection.this.failConnection();
              } 
            }
          };
        super(this, "OkHttp %s ACK Settings", new Object[] { this.this$0.hostname }, param1Settings);
        scheduledExecutorService.execute((Runnable)namedRunnable);
      } catch (RejectedExecutionException rejectedExecutionException) {}
    }
    
    public void ackSettings() {}
    
    public void alternateService(int param1Int1, String param1String1, ByteString param1ByteString, String param1String2, int param1Int2, long param1Long) {}
    
    public void data(boolean param1Boolean, int param1Int1, BufferedSource param1BufferedSource, int param1Int2) throws IOException {
      if (Http2Connection.this.pushedStream(param1Int1)) {
        Http2Connection.this.pushDataLater(param1Int1, param1BufferedSource, param1Int2, param1Boolean);
        return;
      } 
      Http2Stream http2Stream = Http2Connection.this.getStream(param1Int1);
      if (http2Stream == null) {
        Http2Connection.this.writeSynResetLater(param1Int1, ErrorCode.PROTOCOL_ERROR);
        param1BufferedSource.skip(param1Int2);
        return;
      } 
      http2Stream.receiveData(param1BufferedSource, param1Int2);
      if (param1Boolean)
        http2Stream.receiveFin(); 
    }
    
    protected void execute() {
      // Byte code:
      //   0: getstatic okhttp3/internal/http2/ErrorCode.INTERNAL_ERROR : Lokhttp3/internal/http2/ErrorCode;
      //   3: astore_3
      //   4: getstatic okhttp3/internal/http2/ErrorCode.INTERNAL_ERROR : Lokhttp3/internal/http2/ErrorCode;
      //   7: astore #5
      //   9: aload_3
      //   10: astore_1
      //   11: aload_3
      //   12: astore_2
      //   13: aload_0
      //   14: getfield reader : Lokhttp3/internal/http2/Http2Reader;
      //   17: aload_0
      //   18: invokevirtual readConnectionPreface : (Lokhttp3/internal/http2/Http2Reader$Handler;)V
      //   21: aload_3
      //   22: astore_1
      //   23: aload_3
      //   24: astore_2
      //   25: aload_0
      //   26: getfield reader : Lokhttp3/internal/http2/Http2Reader;
      //   29: iconst_0
      //   30: aload_0
      //   31: invokevirtual nextFrame : (ZLokhttp3/internal/http2/Http2Reader$Handler;)Z
      //   34: ifeq -> 40
      //   37: goto -> 21
      //   40: aload_3
      //   41: astore_1
      //   42: aload_3
      //   43: astore_2
      //   44: getstatic okhttp3/internal/http2/ErrorCode.NO_ERROR : Lokhttp3/internal/http2/ErrorCode;
      //   47: astore_3
      //   48: aload_3
      //   49: astore_1
      //   50: aload_3
      //   51: astore_2
      //   52: getstatic okhttp3/internal/http2/ErrorCode.CANCEL : Lokhttp3/internal/http2/ErrorCode;
      //   55: astore #4
      //   57: aload_0
      //   58: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
      //   61: astore_1
      //   62: aload_3
      //   63: astore_2
      //   64: aload #4
      //   66: astore_3
      //   67: goto -> 92
      //   70: astore_2
      //   71: goto -> 106
      //   74: astore_1
      //   75: aload_2
      //   76: astore_1
      //   77: getstatic okhttp3/internal/http2/ErrorCode.PROTOCOL_ERROR : Lokhttp3/internal/http2/ErrorCode;
      //   80: astore_2
      //   81: aload_2
      //   82: astore_1
      //   83: getstatic okhttp3/internal/http2/ErrorCode.PROTOCOL_ERROR : Lokhttp3/internal/http2/ErrorCode;
      //   86: astore_3
      //   87: aload_0
      //   88: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
      //   91: astore_1
      //   92: aload_1
      //   93: aload_2
      //   94: aload_3
      //   95: invokevirtual close : (Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;)V
      //   98: aload_0
      //   99: getfield reader : Lokhttp3/internal/http2/Http2Reader;
      //   102: invokestatic closeQuietly : (Ljava/io/Closeable;)V
      //   105: return
      //   106: aload_0
      //   107: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
      //   110: aload_1
      //   111: aload #5
      //   113: invokevirtual close : (Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;)V
      //   116: aload_0
      //   117: getfield reader : Lokhttp3/internal/http2/Http2Reader;
      //   120: invokestatic closeQuietly : (Ljava/io/Closeable;)V
      //   123: aload_2
      //   124: athrow
      //   125: astore_1
      //   126: goto -> 98
      //   129: astore_1
      //   130: goto -> 116
      // Exception table:
      //   from	to	target	type
      //   13	21	74	java/io/IOException
      //   13	21	70	finally
      //   25	37	74	java/io/IOException
      //   25	37	70	finally
      //   44	48	74	java/io/IOException
      //   44	48	70	finally
      //   52	57	74	java/io/IOException
      //   52	57	70	finally
      //   57	62	125	java/io/IOException
      //   77	81	70	finally
      //   83	87	70	finally
      //   87	92	125	java/io/IOException
      //   92	98	125	java/io/IOException
      //   106	116	129	java/io/IOException
    }
    
    public void goAway(int param1Int, ErrorCode param1ErrorCode, ByteString param1ByteString) {
      Http2Connection http2Connection;
      Http2Stream http2Stream;
      param1ByteString.size();
      synchronized (Http2Connection.this) {
        Http2Stream[] arrayOfHttp2Stream = (Http2Stream[])Http2Connection.this.streams.values().toArray((Object[])new Http2Stream[Http2Connection.this.streams.size()]);
        Http2Connection.this.shutdown = true;
        int i = arrayOfHttp2Stream.length;
        for (byte b = 0; b < i; b++) {
          http2Stream = arrayOfHttp2Stream[b];
          if (http2Stream.getId() > param1Int && http2Stream.isLocallyInitiated()) {
            http2Stream.receiveRstStream(ErrorCode.REFUSED_STREAM);
            Http2Connection.this.removeStream(http2Stream.getId());
          } 
        } 
        return;
      } 
    }
    
    public void headers(boolean param1Boolean, int param1Int1, int param1Int2, List<Header> param1List) {
      if (Http2Connection.this.pushedStream(param1Int1)) {
        Http2Connection.this.pushHeadersLater(param1Int1, param1List, param1Boolean);
        return;
      } 
      synchronized (Http2Connection.this) {
        ExecutorService executorService;
        Http2Stream http2Stream = Http2Connection.this.getStream(param1Int1);
        if (http2Stream == null) {
          if (Http2Connection.this.shutdown)
            return; 
          if (param1Int1 <= Http2Connection.this.lastGoodStreamId)
            return; 
          if (param1Int1 % 2 == Http2Connection.this.nextStreamId % 2)
            return; 
          http2Stream = new Http2Stream();
          this(param1Int1, Http2Connection.this, false, param1Boolean, param1List);
          Http2Connection.this.lastGoodStreamId = param1Int1;
          Http2Connection.this.streams.put(Integer.valueOf(param1Int1), http2Stream);
          executorService = Http2Connection.listenerExecutor;
          NamedRunnable namedRunnable = new NamedRunnable() {
              final Http2Connection.ReaderRunnable this$1;
              
              final Http2Stream val$newStream;
              
              public void execute() {
                try {
                  Http2Connection.this.listener.onStream(newStream);
                } catch (IOException iOException) {
                  Platform platform = Platform.get();
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append("Http2Connection.Listener failure for ");
                  stringBuilder.append(Http2Connection.this.hostname);
                  platform.log(4, stringBuilder.toString(), iOException);
                  try {
                    newStream.close(ErrorCode.PROTOCOL_ERROR);
                  } catch (IOException iOException1) {}
                } 
              }
            };
          super(this, "OkHttp %s stream %d", new Object[] { this.this$0.hostname, Integer.valueOf(param1Int1) }, http2Stream);
          executorService.execute((Runnable)namedRunnable);
          return;
        } 
        http2Stream.receiveHeaders((List<Header>)executorService);
        if (param1Boolean)
          http2Stream.receiveFin(); 
        return;
      } 
    }
    
    public void ping(boolean param1Boolean, int param1Int1, int param1Int2) {
      if (param1Boolean) {
        synchronized (Http2Connection.this) {
          Http2Connection.access$302(Http2Connection.this, false);
          Http2Connection.this.notifyAll();
        } 
      } else {
        try {
          ScheduledExecutorService scheduledExecutorService = Http2Connection.this.writerExecutor;
          Http2Connection.PingRunnable pingRunnable = new Http2Connection.PingRunnable();
          this(true, param1Int1, param1Int2);
          scheduledExecutorService.execute((Runnable)pingRunnable);
        } catch (RejectedExecutionException rejectedExecutionException) {}
      } 
    }
    
    public void priority(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {}
    
    public void pushPromise(int param1Int1, int param1Int2, List<Header> param1List) {
      Http2Connection.this.pushRequestLater(param1Int2, param1List);
    }
    
    public void rstStream(int param1Int, ErrorCode param1ErrorCode) {
      if (Http2Connection.this.pushedStream(param1Int)) {
        Http2Connection.this.pushResetLater(param1Int, param1ErrorCode);
        return;
      } 
      Http2Stream http2Stream = Http2Connection.this.removeStream(param1Int);
      if (http2Stream != null)
        http2Stream.receiveRstStream(param1ErrorCode); 
    }
    
    public void settings(boolean param1Boolean, Settings param1Settings) {
      synchronized (Http2Connection.this) {
        Http2Stream[] arrayOfHttp2Stream;
        long l;
        int i = Http2Connection.this.peerSettings.getInitialWindowSize();
        if (param1Boolean)
          Http2Connection.this.peerSettings.clear(); 
        Http2Connection.this.peerSettings.merge(param1Settings);
        applyAndAckSettings(param1Settings);
        int j = Http2Connection.this.peerSettings.getInitialWindowSize();
        param1Settings = null;
        if (j != -1 && j != i) {
          long l1 = (j - i);
          if (!Http2Connection.this.receivedInitialPeerSettings) {
            Http2Connection.this.addBytesToWriteWindow(l1);
            Http2Connection.this.receivedInitialPeerSettings = true;
          } 
          l = l1;
          if (!Http2Connection.this.streams.isEmpty()) {
            arrayOfHttp2Stream = (Http2Stream[])Http2Connection.this.streams.values().toArray((Object[])new Http2Stream[Http2Connection.this.streams.size()]);
            l = l1;
          } 
        } else {
          l = 0L;
        } 
        ExecutorService executorService = Http2Connection.listenerExecutor;
        NamedRunnable namedRunnable = new NamedRunnable() {
            final Http2Connection.ReaderRunnable this$1;
            
            public void execute() {
              Http2Connection.this.listener.onSettings(Http2Connection.this);
            }
          };
        String str = Http2Connection.this.hostname;
        i = 0;
        super(this, "OkHttp %s settings", new Object[] { str });
        executorService.execute((Runnable)namedRunnable);
        if (arrayOfHttp2Stream != null && l != 0L) {
          j = arrayOfHttp2Stream.length;
          while (i < j) {
            synchronized (arrayOfHttp2Stream[i]) {
              null.addBytesToWriteWindow(l);
              i++;
            } 
          } 
        } 
        return;
      } 
    }
    
    public void windowUpdate(int param1Int, long param1Long) {
      // Byte code:
      //   0: iload_1
      //   1: ifne -> 52
      //   4: aload_0
      //   5: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
      //   8: astore #4
      //   10: aload #4
      //   12: monitorenter
      //   13: aload_0
      //   14: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
      //   17: astore #5
      //   19: aload #5
      //   21: aload #5
      //   23: getfield bytesLeftInWriteWindow : J
      //   26: lload_2
      //   27: ladd
      //   28: putfield bytesLeftInWriteWindow : J
      //   31: aload_0
      //   32: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
      //   35: invokevirtual notifyAll : ()V
      //   38: aload #4
      //   40: monitorexit
      //   41: goto -> 90
      //   44: astore #5
      //   46: aload #4
      //   48: monitorexit
      //   49: aload #5
      //   51: athrow
      //   52: aload_0
      //   53: getfield this$0 : Lokhttp3/internal/http2/Http2Connection;
      //   56: iload_1
      //   57: invokevirtual getStream : (I)Lokhttp3/internal/http2/Http2Stream;
      //   60: astore #5
      //   62: aload #5
      //   64: ifnull -> 90
      //   67: aload #5
      //   69: monitorenter
      //   70: aload #5
      //   72: lload_2
      //   73: invokevirtual addBytesToWriteWindow : (J)V
      //   76: aload #5
      //   78: monitorexit
      //   79: goto -> 90
      //   82: astore #4
      //   84: aload #5
      //   86: monitorexit
      //   87: aload #4
      //   89: athrow
      //   90: return
      // Exception table:
      //   from	to	target	type
      //   13	41	44	finally
      //   46	49	44	finally
      //   70	79	82	finally
      //   84	87	82	finally
    }
  }
  
  class null extends NamedRunnable {
    final Http2Connection.ReaderRunnable this$1;
    
    final Http2Stream val$newStream;
    
    null(String param1String, Object[] param1ArrayOfObject) {
      super(param1String, param1ArrayOfObject);
    }
    
    public void execute() {
      try {
        Http2Connection.this.listener.onStream(newStream);
      } catch (IOException iOException) {
        Platform platform = Platform.get();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Http2Connection.Listener failure for ");
        stringBuilder.append(Http2Connection.this.hostname);
        platform.log(4, stringBuilder.toString(), iOException);
        try {
          newStream.close(ErrorCode.PROTOCOL_ERROR);
        } catch (IOException iOException1) {}
      } 
    }
  }
  
  class null extends NamedRunnable {
    final Http2Connection.ReaderRunnable this$1;
    
    null(String param1String, Object... param1VarArgs) {
      super(param1String, param1VarArgs);
    }
    
    public void execute() {
      Http2Connection.this.listener.onSettings(Http2Connection.this);
    }
  }
  
  class null extends NamedRunnable {
    final Http2Connection.ReaderRunnable this$1;
    
    final Settings val$peerSettings;
    
    null(String param1String, Object[] param1ArrayOfObject) {
      super(param1String, param1ArrayOfObject);
    }
    
    public void execute() {
      try {
        Http2Connection.this.writer.applyAndAckSettings(peerSettings);
      } catch (IOException iOException) {
        Http2Connection.this.failConnection();
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\Http2Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */