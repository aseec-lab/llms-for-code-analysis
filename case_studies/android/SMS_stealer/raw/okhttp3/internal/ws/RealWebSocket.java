package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.StreamAllocation;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

public final class RealWebSocket implements WebSocket, WebSocketReader.FrameCallback {
  static final boolean $assertionsDisabled = false;
  
  private static final long CANCEL_AFTER_CLOSE_MILLIS = 60000L;
  
  private static final long MAX_QUEUE_SIZE = 16777216L;
  
  private static final List<Protocol> ONLY_HTTP1 = Collections.singletonList(Protocol.HTTP_1_1);
  
  private boolean awaitingPong;
  
  private Call call;
  
  private ScheduledFuture<?> cancelFuture;
  
  private boolean enqueuedClose;
  
  private ScheduledExecutorService executor;
  
  private boolean failed;
  
  private final String key;
  
  final WebSocketListener listener;
  
  private final ArrayDeque<Object> messageAndCloseQueue;
  
  private final Request originalRequest;
  
  private final long pingIntervalMillis;
  
  private final ArrayDeque<ByteString> pongQueue;
  
  private long queueSize;
  
  private final Random random;
  
  private WebSocketReader reader;
  
  private int receivedCloseCode;
  
  private String receivedCloseReason;
  
  private int receivedPingCount;
  
  private int receivedPongCount;
  
  private int sentPingCount;
  
  private Streams streams;
  
  private WebSocketWriter writer;
  
  private final Runnable writerRunnable;
  
  public RealWebSocket(Request paramRequest, WebSocketListener paramWebSocketListener, Random paramRandom, long paramLong) {
    byte[] arrayOfByte;
    this.pongQueue = new ArrayDeque<ByteString>();
    this.messageAndCloseQueue = new ArrayDeque();
    this.receivedCloseCode = -1;
    if ("GET".equals(paramRequest.method())) {
      this.originalRequest = paramRequest;
      this.listener = paramWebSocketListener;
      this.random = paramRandom;
      this.pingIntervalMillis = paramLong;
      arrayOfByte = new byte[16];
      paramRandom.nextBytes(arrayOfByte);
      this.key = ByteString.of(arrayOfByte).base64();
      this.writerRunnable = new Runnable() {
          final RealWebSocket this$0;
          
          public void run() {
            try {
              while (true) {
                boolean bool = RealWebSocket.this.writeOneFrame();
                if (bool)
                  continue; 
                break;
              } 
            } catch (IOException iOException) {
              RealWebSocket.this.failWebSocket(iOException, null);
            } 
          }
        };
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Request must be GET: ");
    stringBuilder.append(arrayOfByte.method());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private void runWriter() {
    ScheduledExecutorService scheduledExecutorService = this.executor;
    if (scheduledExecutorService != null)
      scheduledExecutorService.execute(this.writerRunnable); 
  }
  
  private boolean send(ByteString paramByteString, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failed : Z
    //   6: ifne -> 94
    //   9: aload_0
    //   10: getfield enqueuedClose : Z
    //   13: ifeq -> 19
    //   16: goto -> 94
    //   19: aload_0
    //   20: getfield queueSize : J
    //   23: aload_1
    //   24: invokevirtual size : ()I
    //   27: i2l
    //   28: ladd
    //   29: ldc2_w 16777216
    //   32: lcmp
    //   33: ifle -> 49
    //   36: aload_0
    //   37: sipush #1001
    //   40: aconst_null
    //   41: invokevirtual close : (ILjava/lang/String;)Z
    //   44: pop
    //   45: aload_0
    //   46: monitorexit
    //   47: iconst_0
    //   48: ireturn
    //   49: aload_0
    //   50: aload_0
    //   51: getfield queueSize : J
    //   54: aload_1
    //   55: invokevirtual size : ()I
    //   58: i2l
    //   59: ladd
    //   60: putfield queueSize : J
    //   63: aload_0
    //   64: getfield messageAndCloseQueue : Ljava/util/ArrayDeque;
    //   67: astore #4
    //   69: new okhttp3/internal/ws/RealWebSocket$Message
    //   72: astore_3
    //   73: aload_3
    //   74: iload_2
    //   75: aload_1
    //   76: invokespecial <init> : (ILokio/ByteString;)V
    //   79: aload #4
    //   81: aload_3
    //   82: invokevirtual add : (Ljava/lang/Object;)Z
    //   85: pop
    //   86: aload_0
    //   87: invokespecial runWriter : ()V
    //   90: aload_0
    //   91: monitorexit
    //   92: iconst_1
    //   93: ireturn
    //   94: aload_0
    //   95: monitorexit
    //   96: iconst_0
    //   97: ireturn
    //   98: astore_1
    //   99: aload_0
    //   100: monitorexit
    //   101: aload_1
    //   102: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	98	finally
    //   19	45	98	finally
    //   49	90	98	finally
  }
  
  void awaitTermination(int paramInt, TimeUnit paramTimeUnit) throws InterruptedException {
    this.executor.awaitTermination(paramInt, paramTimeUnit);
  }
  
  public void cancel() {
    this.call.cancel();
  }
  
  void checkResponse(Response paramResponse) throws ProtocolException {
    StringBuilder stringBuilder1;
    if (paramResponse.code() == 101) {
      StringBuilder stringBuilder;
      String str = paramResponse.header("Connection");
      if ("Upgrade".equalsIgnoreCase(str)) {
        str = paramResponse.header("Upgrade");
        if ("websocket".equalsIgnoreCase(str)) {
          String str1 = paramResponse.header("Sec-WebSocket-Accept");
          stringBuilder = new StringBuilder();
          stringBuilder.append(this.key);
          stringBuilder.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
          String str2 = ByteString.encodeUtf8(stringBuilder.toString()).sha1().base64();
          if (str2.equals(str1))
            return; 
          stringBuilder = new StringBuilder();
          stringBuilder.append("Expected 'Sec-WebSocket-Accept' header value '");
          stringBuilder.append(str2);
          stringBuilder.append("' but was '");
          stringBuilder.append(str1);
          stringBuilder.append("'");
          throw new ProtocolException(stringBuilder.toString());
        } 
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("Expected 'Upgrade' header value 'websocket' but was '");
        stringBuilder3.append((String)stringBuilder);
        stringBuilder3.append("'");
        throw new ProtocolException(stringBuilder3.toString());
      } 
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected 'Connection' header value 'Upgrade' but was '");
      stringBuilder1.append((String)stringBuilder);
      stringBuilder1.append("'");
      throw new ProtocolException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Expected HTTP 101 response but was '");
    stringBuilder2.append(stringBuilder1.code());
    stringBuilder2.append(" ");
    stringBuilder2.append(stringBuilder1.message());
    stringBuilder2.append("'");
    throw new ProtocolException(stringBuilder2.toString());
  }
  
  public boolean close(int paramInt, String paramString) {
    return close(paramInt, paramString, 60000L);
  }
  
  boolean close(int paramInt, String paramString, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: invokestatic validateCloseCode : (I)V
    //   6: aconst_null
    //   7: astore #5
    //   9: aload_2
    //   10: ifnull -> 79
    //   13: aload_2
    //   14: invokestatic encodeUtf8 : (Ljava/lang/String;)Lokio/ByteString;
    //   17: astore #5
    //   19: aload #5
    //   21: invokevirtual size : ()I
    //   24: i2l
    //   25: ldc2_w 123
    //   28: lcmp
    //   29: ifgt -> 35
    //   32: goto -> 79
    //   35: new java/lang/IllegalArgumentException
    //   38: astore #5
    //   40: new java/lang/StringBuilder
    //   43: astore #6
    //   45: aload #6
    //   47: invokespecial <init> : ()V
    //   50: aload #6
    //   52: ldc_w 'reason.size() > 123: '
    //   55: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload #6
    //   61: aload_2
    //   62: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: pop
    //   66: aload #5
    //   68: aload #6
    //   70: invokevirtual toString : ()Ljava/lang/String;
    //   73: invokespecial <init> : (Ljava/lang/String;)V
    //   76: aload #5
    //   78: athrow
    //   79: aload_0
    //   80: getfield failed : Z
    //   83: ifne -> 134
    //   86: aload_0
    //   87: getfield enqueuedClose : Z
    //   90: ifeq -> 96
    //   93: goto -> 134
    //   96: aload_0
    //   97: iconst_1
    //   98: putfield enqueuedClose : Z
    //   101: aload_0
    //   102: getfield messageAndCloseQueue : Ljava/util/ArrayDeque;
    //   105: astore #6
    //   107: new okhttp3/internal/ws/RealWebSocket$Close
    //   110: astore_2
    //   111: aload_2
    //   112: iload_1
    //   113: aload #5
    //   115: lload_3
    //   116: invokespecial <init> : (ILokio/ByteString;J)V
    //   119: aload #6
    //   121: aload_2
    //   122: invokevirtual add : (Ljava/lang/Object;)Z
    //   125: pop
    //   126: aload_0
    //   127: invokespecial runWriter : ()V
    //   130: aload_0
    //   131: monitorexit
    //   132: iconst_1
    //   133: ireturn
    //   134: aload_0
    //   135: monitorexit
    //   136: iconst_0
    //   137: ireturn
    //   138: astore_2
    //   139: aload_0
    //   140: monitorexit
    //   141: aload_2
    //   142: athrow
    // Exception table:
    //   from	to	target	type
    //   2	6	138	finally
    //   13	32	138	finally
    //   35	79	138	finally
    //   79	93	138	finally
    //   96	130	138	finally
  }
  
  public void connect(OkHttpClient paramOkHttpClient) {
    OkHttpClient okHttpClient = paramOkHttpClient.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
    final Request request = this.originalRequest.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header("Sec-WebSocket-Key", this.key).header("Sec-WebSocket-Version", "13").build();
    Call call = Internal.instance.newWebSocketCall(okHttpClient, request);
    this.call = call;
    call.enqueue(new Callback() {
          final RealWebSocket this$0;
          
          final Request val$request;
          
          public void onFailure(Call param1Call, IOException param1IOException) {
            RealWebSocket.this.failWebSocket(param1IOException, null);
          }
          
          public void onResponse(Call param1Call, Response param1Response) {
            String str;
            try {
              RealWebSocket.this.checkResponse(param1Response);
              StreamAllocation streamAllocation = Internal.instance.streamAllocation(param1Call);
              streamAllocation.noNewStreams();
              RealWebSocket.Streams streams = streamAllocation.connection().newWebSocketStreams(streamAllocation);
              try {
                RealWebSocket.this.listener.onOpen(RealWebSocket.this, param1Response);
                StringBuilder stringBuilder = new StringBuilder();
                this();
                stringBuilder.append("OkHttp WebSocket ");
                stringBuilder.append(request.url().redact());
                str = stringBuilder.toString();
                RealWebSocket.this.initReaderAndWriter(str, streams);
                streamAllocation.connection().socket().setSoTimeout(0);
                RealWebSocket.this.loopReader();
              } catch (Exception exception) {
                RealWebSocket.this.failWebSocket(exception, null);
              } 
              return;
            } catch (ProtocolException protocolException) {
              RealWebSocket.this.failWebSocket(protocolException, (Response)str);
              Util.closeQuietly((Closeable)str);
              return;
            } 
          }
        });
  }
  
  public void failWebSocket(Exception paramException, @Nullable Response paramResponse) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failed : Z
    //   6: ifeq -> 12
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield failed : Z
    //   17: aload_0
    //   18: getfield streams : Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   21: astore_3
    //   22: aload_0
    //   23: aconst_null
    //   24: putfield streams : Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   27: aload_0
    //   28: getfield cancelFuture : Ljava/util/concurrent/ScheduledFuture;
    //   31: ifnull -> 45
    //   34: aload_0
    //   35: getfield cancelFuture : Ljava/util/concurrent/ScheduledFuture;
    //   38: iconst_0
    //   39: invokeinterface cancel : (Z)Z
    //   44: pop
    //   45: aload_0
    //   46: getfield executor : Ljava/util/concurrent/ScheduledExecutorService;
    //   49: ifnull -> 61
    //   52: aload_0
    //   53: getfield executor : Ljava/util/concurrent/ScheduledExecutorService;
    //   56: invokeinterface shutdown : ()V
    //   61: aload_0
    //   62: monitorexit
    //   63: aload_0
    //   64: getfield listener : Lokhttp3/WebSocketListener;
    //   67: aload_0
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual onFailure : (Lokhttp3/WebSocket;Ljava/lang/Throwable;Lokhttp3/Response;)V
    //   73: aload_3
    //   74: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   77: return
    //   78: astore_1
    //   79: aload_3
    //   80: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   83: aload_1
    //   84: athrow
    //   85: astore_1
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_1
    //   89: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	85	finally
    //   12	45	85	finally
    //   45	61	85	finally
    //   61	63	85	finally
    //   63	73	78	finally
    //   86	88	85	finally
  }
  
  public void initReaderAndWriter(String paramString, Streams paramStreams) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_2
    //   4: putfield streams : Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   7: new okhttp3/internal/ws/WebSocketWriter
    //   10: astore_3
    //   11: aload_3
    //   12: aload_2
    //   13: getfield client : Z
    //   16: aload_2
    //   17: getfield sink : Lokio/BufferedSink;
    //   20: aload_0
    //   21: getfield random : Ljava/util/Random;
    //   24: invokespecial <init> : (ZLokio/BufferedSink;Ljava/util/Random;)V
    //   27: aload_0
    //   28: aload_3
    //   29: putfield writer : Lokhttp3/internal/ws/WebSocketWriter;
    //   32: new java/util/concurrent/ScheduledThreadPoolExecutor
    //   35: astore_3
    //   36: aload_3
    //   37: iconst_1
    //   38: aload_1
    //   39: iconst_0
    //   40: invokestatic threadFactory : (Ljava/lang/String;Z)Ljava/util/concurrent/ThreadFactory;
    //   43: invokespecial <init> : (ILjava/util/concurrent/ThreadFactory;)V
    //   46: aload_0
    //   47: aload_3
    //   48: putfield executor : Ljava/util/concurrent/ScheduledExecutorService;
    //   51: aload_0
    //   52: getfield pingIntervalMillis : J
    //   55: lconst_0
    //   56: lcmp
    //   57: ifeq -> 88
    //   60: new okhttp3/internal/ws/RealWebSocket$PingRunnable
    //   63: astore_1
    //   64: aload_1
    //   65: aload_0
    //   66: invokespecial <init> : (Lokhttp3/internal/ws/RealWebSocket;)V
    //   69: aload_3
    //   70: aload_1
    //   71: aload_0
    //   72: getfield pingIntervalMillis : J
    //   75: aload_0
    //   76: getfield pingIntervalMillis : J
    //   79: getstatic java/util/concurrent/TimeUnit.MILLISECONDS : Ljava/util/concurrent/TimeUnit;
    //   82: invokeinterface scheduleAtFixedRate : (Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;
    //   87: pop
    //   88: aload_0
    //   89: getfield messageAndCloseQueue : Ljava/util/ArrayDeque;
    //   92: invokevirtual isEmpty : ()Z
    //   95: ifne -> 102
    //   98: aload_0
    //   99: invokespecial runWriter : ()V
    //   102: aload_0
    //   103: monitorexit
    //   104: aload_0
    //   105: new okhttp3/internal/ws/WebSocketReader
    //   108: dup
    //   109: aload_2
    //   110: getfield client : Z
    //   113: aload_2
    //   114: getfield source : Lokio/BufferedSource;
    //   117: aload_0
    //   118: invokespecial <init> : (ZLokio/BufferedSource;Lokhttp3/internal/ws/WebSocketReader$FrameCallback;)V
    //   121: putfield reader : Lokhttp3/internal/ws/WebSocketReader;
    //   124: return
    //   125: astore_1
    //   126: aload_0
    //   127: monitorexit
    //   128: aload_1
    //   129: athrow
    // Exception table:
    //   from	to	target	type
    //   2	88	125	finally
    //   88	102	125	finally
    //   102	104	125	finally
    //   126	128	125	finally
  }
  
  public void loopReader() throws IOException {
    while (this.receivedCloseCode == -1)
      this.reader.processNextFrame(); 
  }
  
  public void onReadClose(int paramInt, String paramString) {
    // Byte code:
    //   0: iload_1
    //   1: iconst_m1
    //   2: if_icmpeq -> 152
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield receivedCloseCode : I
    //   11: iconst_m1
    //   12: if_icmpne -> 134
    //   15: aload_0
    //   16: iload_1
    //   17: putfield receivedCloseCode : I
    //   20: aload_0
    //   21: aload_2
    //   22: putfield receivedCloseReason : Ljava/lang/String;
    //   25: aload_0
    //   26: getfield enqueuedClose : Z
    //   29: istore_3
    //   30: aconst_null
    //   31: astore #5
    //   33: aload #5
    //   35: astore #4
    //   37: iload_3
    //   38: ifeq -> 93
    //   41: aload #5
    //   43: astore #4
    //   45: aload_0
    //   46: getfield messageAndCloseQueue : Ljava/util/ArrayDeque;
    //   49: invokevirtual isEmpty : ()Z
    //   52: ifeq -> 93
    //   55: aload_0
    //   56: getfield streams : Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   59: astore #4
    //   61: aload_0
    //   62: aconst_null
    //   63: putfield streams : Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   66: aload_0
    //   67: getfield cancelFuture : Ljava/util/concurrent/ScheduledFuture;
    //   70: ifnull -> 84
    //   73: aload_0
    //   74: getfield cancelFuture : Ljava/util/concurrent/ScheduledFuture;
    //   77: iconst_0
    //   78: invokeinterface cancel : (Z)Z
    //   83: pop
    //   84: aload_0
    //   85: getfield executor : Ljava/util/concurrent/ScheduledExecutorService;
    //   88: invokeinterface shutdown : ()V
    //   93: aload_0
    //   94: monitorexit
    //   95: aload_0
    //   96: getfield listener : Lokhttp3/WebSocketListener;
    //   99: aload_0
    //   100: iload_1
    //   101: aload_2
    //   102: invokevirtual onClosing : (Lokhttp3/WebSocket;ILjava/lang/String;)V
    //   105: aload #4
    //   107: ifnull -> 120
    //   110: aload_0
    //   111: getfield listener : Lokhttp3/WebSocketListener;
    //   114: aload_0
    //   115: iload_1
    //   116: aload_2
    //   117: invokevirtual onClosed : (Lokhttp3/WebSocket;ILjava/lang/String;)V
    //   120: aload #4
    //   122: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   125: return
    //   126: astore_2
    //   127: aload #4
    //   129: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   132: aload_2
    //   133: athrow
    //   134: new java/lang/IllegalStateException
    //   137: astore_2
    //   138: aload_2
    //   139: ldc_w 'already closed'
    //   142: invokespecial <init> : (Ljava/lang/String;)V
    //   145: aload_2
    //   146: athrow
    //   147: astore_2
    //   148: aload_0
    //   149: monitorexit
    //   150: aload_2
    //   151: athrow
    //   152: new java/lang/IllegalArgumentException
    //   155: dup
    //   156: invokespecial <init> : ()V
    //   159: athrow
    // Exception table:
    //   from	to	target	type
    //   7	30	147	finally
    //   45	84	147	finally
    //   84	93	147	finally
    //   93	95	147	finally
    //   95	105	126	finally
    //   110	120	126	finally
    //   134	147	147	finally
    //   148	150	147	finally
  }
  
  public void onReadMessage(String paramString) throws IOException {
    this.listener.onMessage(this, paramString);
  }
  
  public void onReadMessage(ByteString paramByteString) throws IOException {
    this.listener.onMessage(this, paramByteString);
  }
  
  public void onReadPing(ByteString paramByteString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failed : Z
    //   6: ifne -> 55
    //   9: aload_0
    //   10: getfield enqueuedClose : Z
    //   13: ifeq -> 29
    //   16: aload_0
    //   17: getfield messageAndCloseQueue : Ljava/util/ArrayDeque;
    //   20: invokevirtual isEmpty : ()Z
    //   23: ifeq -> 29
    //   26: goto -> 55
    //   29: aload_0
    //   30: getfield pongQueue : Ljava/util/ArrayDeque;
    //   33: aload_1
    //   34: invokevirtual add : (Ljava/lang/Object;)Z
    //   37: pop
    //   38: aload_0
    //   39: invokespecial runWriter : ()V
    //   42: aload_0
    //   43: aload_0
    //   44: getfield receivedPingCount : I
    //   47: iconst_1
    //   48: iadd
    //   49: putfield receivedPingCount : I
    //   52: aload_0
    //   53: monitorexit
    //   54: return
    //   55: aload_0
    //   56: monitorexit
    //   57: return
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	58	finally
    //   29	52	58	finally
  }
  
  public void onReadPong(ByteString paramByteString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_0
    //   4: getfield receivedPongCount : I
    //   7: iconst_1
    //   8: iadd
    //   9: putfield receivedPongCount : I
    //   12: aload_0
    //   13: iconst_0
    //   14: putfield awaitingPong : Z
    //   17: aload_0
    //   18: monitorexit
    //   19: return
    //   20: astore_1
    //   21: aload_0
    //   22: monitorexit
    //   23: aload_1
    //   24: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	20	finally
  }
  
  boolean pong(ByteString paramByteString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failed : Z
    //   6: ifne -> 46
    //   9: aload_0
    //   10: getfield enqueuedClose : Z
    //   13: ifeq -> 29
    //   16: aload_0
    //   17: getfield messageAndCloseQueue : Ljava/util/ArrayDeque;
    //   20: invokevirtual isEmpty : ()Z
    //   23: ifeq -> 29
    //   26: goto -> 46
    //   29: aload_0
    //   30: getfield pongQueue : Ljava/util/ArrayDeque;
    //   33: aload_1
    //   34: invokevirtual add : (Ljava/lang/Object;)Z
    //   37: pop
    //   38: aload_0
    //   39: invokespecial runWriter : ()V
    //   42: aload_0
    //   43: monitorexit
    //   44: iconst_1
    //   45: ireturn
    //   46: aload_0
    //   47: monitorexit
    //   48: iconst_0
    //   49: ireturn
    //   50: astore_1
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	50	finally
    //   29	42	50	finally
  }
  
  boolean processNextFrame() throws IOException {
    boolean bool = false;
    try {
      this.reader.processNextFrame();
      int i = this.receivedCloseCode;
      if (i == -1)
        bool = true; 
      return bool;
    } catch (Exception exception) {
      failWebSocket(exception, null);
      return false;
    } 
  }
  
  public long queueSize() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield queueSize : J
    //   6: lstore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: lload_1
    //   10: lreturn
    //   11: astore_3
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_3
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  int receivedPingCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield receivedPingCount : I
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
  
  int receivedPongCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield receivedPongCount : I
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
  
  public Request request() {
    return this.originalRequest;
  }
  
  public boolean send(String paramString) {
    if (paramString != null)
      return send(ByteString.encodeUtf8(paramString), 1); 
    throw new NullPointerException("text == null");
  }
  
  public boolean send(ByteString paramByteString) {
    if (paramByteString != null)
      return send(paramByteString, 2); 
    throw new NullPointerException("bytes == null");
  }
  
  int sentPingCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield sentPingCount : I
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
  
  void tearDown() throws InterruptedException {
    ScheduledFuture<?> scheduledFuture = this.cancelFuture;
    if (scheduledFuture != null)
      scheduledFuture.cancel(false); 
    this.executor.shutdown();
    this.executor.awaitTermination(10L, TimeUnit.SECONDS);
  }
  
  boolean writeOneFrame() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failed : Z
    //   6: ifeq -> 13
    //   9: aload_0
    //   10: monitorexit
    //   11: iconst_0
    //   12: ireturn
    //   13: aload_0
    //   14: getfield writer : Lokhttp3/internal/ws/WebSocketWriter;
    //   17: astore #6
    //   19: aload_0
    //   20: getfield pongQueue : Ljava/util/ArrayDeque;
    //   23: invokevirtual poll : ()Ljava/lang/Object;
    //   26: checkcast okio/ByteString
    //   29: astore #7
    //   31: iconst_m1
    //   32: istore_1
    //   33: aconst_null
    //   34: astore_3
    //   35: aload #7
    //   37: ifnonnull -> 162
    //   40: aload_0
    //   41: getfield messageAndCloseQueue : Ljava/util/ArrayDeque;
    //   44: invokevirtual poll : ()Ljava/lang/Object;
    //   47: astore_2
    //   48: aload_2
    //   49: instanceof okhttp3/internal/ws/RealWebSocket$Close
    //   52: ifeq -> 140
    //   55: aload_0
    //   56: getfield receivedCloseCode : I
    //   59: istore_1
    //   60: aload_0
    //   61: getfield receivedCloseReason : Ljava/lang/String;
    //   64: astore #4
    //   66: iload_1
    //   67: iconst_m1
    //   68: if_icmpeq -> 99
    //   71: aload_0
    //   72: getfield streams : Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   75: astore #5
    //   77: aload_0
    //   78: aconst_null
    //   79: putfield streams : Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   82: aload_0
    //   83: getfield executor : Ljava/util/concurrent/ScheduledExecutorService;
    //   86: invokeinterface shutdown : ()V
    //   91: aload_2
    //   92: astore_3
    //   93: aload #5
    //   95: astore_2
    //   96: goto -> 167
    //   99: aload_0
    //   100: getfield executor : Ljava/util/concurrent/ScheduledExecutorService;
    //   103: astore_3
    //   104: new okhttp3/internal/ws/RealWebSocket$CancelRunnable
    //   107: astore #5
    //   109: aload #5
    //   111: aload_0
    //   112: invokespecial <init> : (Lokhttp3/internal/ws/RealWebSocket;)V
    //   115: aload_0
    //   116: aload_3
    //   117: aload #5
    //   119: aload_2
    //   120: checkcast okhttp3/internal/ws/RealWebSocket$Close
    //   123: getfield cancelAfterCloseMillis : J
    //   126: getstatic java/util/concurrent/TimeUnit.MILLISECONDS : Ljava/util/concurrent/TimeUnit;
    //   129: invokeinterface schedule : (Ljava/lang/Runnable;JLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;
    //   134: putfield cancelFuture : Ljava/util/concurrent/ScheduledFuture;
    //   137: goto -> 151
    //   140: aload_2
    //   141: ifnonnull -> 148
    //   144: aload_0
    //   145: monitorexit
    //   146: iconst_0
    //   147: ireturn
    //   148: aconst_null
    //   149: astore #4
    //   151: aconst_null
    //   152: astore #5
    //   154: aload_2
    //   155: astore_3
    //   156: aload #5
    //   158: astore_2
    //   159: goto -> 167
    //   162: aconst_null
    //   163: astore_2
    //   164: aconst_null
    //   165: astore #4
    //   167: aload_0
    //   168: monitorexit
    //   169: aload #7
    //   171: ifnull -> 184
    //   174: aload #6
    //   176: aload #7
    //   178: invokevirtual writePong : (Lokio/ByteString;)V
    //   181: goto -> 304
    //   184: aload_3
    //   185: instanceof okhttp3/internal/ws/RealWebSocket$Message
    //   188: ifeq -> 264
    //   191: aload_3
    //   192: checkcast okhttp3/internal/ws/RealWebSocket$Message
    //   195: getfield data : Lokio/ByteString;
    //   198: astore #4
    //   200: aload #6
    //   202: aload_3
    //   203: checkcast okhttp3/internal/ws/RealWebSocket$Message
    //   206: getfield formatOpcode : I
    //   209: aload #4
    //   211: invokevirtual size : ()I
    //   214: i2l
    //   215: invokevirtual newMessageSink : (IJ)Lokio/Sink;
    //   218: invokestatic buffer : (Lokio/Sink;)Lokio/BufferedSink;
    //   221: astore_3
    //   222: aload_3
    //   223: aload #4
    //   225: invokeinterface write : (Lokio/ByteString;)Lokio/BufferedSink;
    //   230: pop
    //   231: aload_3
    //   232: invokeinterface close : ()V
    //   237: aload_0
    //   238: monitorenter
    //   239: aload_0
    //   240: aload_0
    //   241: getfield queueSize : J
    //   244: aload #4
    //   246: invokevirtual size : ()I
    //   249: i2l
    //   250: lsub
    //   251: putfield queueSize : J
    //   254: aload_0
    //   255: monitorexit
    //   256: goto -> 304
    //   259: astore_3
    //   260: aload_0
    //   261: monitorexit
    //   262: aload_3
    //   263: athrow
    //   264: aload_3
    //   265: instanceof okhttp3/internal/ws/RealWebSocket$Close
    //   268: ifeq -> 310
    //   271: aload_3
    //   272: checkcast okhttp3/internal/ws/RealWebSocket$Close
    //   275: astore_3
    //   276: aload #6
    //   278: aload_3
    //   279: getfield code : I
    //   282: aload_3
    //   283: getfield reason : Lokio/ByteString;
    //   286: invokevirtual writeClose : (ILokio/ByteString;)V
    //   289: aload_2
    //   290: ifnull -> 304
    //   293: aload_0
    //   294: getfield listener : Lokhttp3/WebSocketListener;
    //   297: aload_0
    //   298: iload_1
    //   299: aload #4
    //   301: invokevirtual onClosed : (Lokhttp3/WebSocket;ILjava/lang/String;)V
    //   304: aload_2
    //   305: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   308: iconst_1
    //   309: ireturn
    //   310: new java/lang/AssertionError
    //   313: astore_3
    //   314: aload_3
    //   315: invokespecial <init> : ()V
    //   318: aload_3
    //   319: athrow
    //   320: astore_3
    //   321: aload_2
    //   322: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   325: aload_3
    //   326: athrow
    //   327: astore_2
    //   328: aload_0
    //   329: monitorexit
    //   330: aload_2
    //   331: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	327	finally
    //   13	31	327	finally
    //   40	66	327	finally
    //   71	91	327	finally
    //   99	137	327	finally
    //   144	146	327	finally
    //   167	169	327	finally
    //   174	181	320	finally
    //   184	239	320	finally
    //   239	256	259	finally
    //   260	262	259	finally
    //   262	264	320	finally
    //   264	289	320	finally
    //   293	304	320	finally
    //   310	320	320	finally
    //   328	330	327	finally
  }
  
  void writePingFrame() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failed : Z
    //   6: ifeq -> 12
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: aload_0
    //   13: getfield writer : Lokhttp3/internal/ws/WebSocketWriter;
    //   16: astore_2
    //   17: aload_0
    //   18: getfield awaitingPong : Z
    //   21: ifeq -> 32
    //   24: aload_0
    //   25: getfield sentPingCount : I
    //   28: istore_1
    //   29: goto -> 34
    //   32: iconst_m1
    //   33: istore_1
    //   34: aload_0
    //   35: aload_0
    //   36: getfield sentPingCount : I
    //   39: iconst_1
    //   40: iadd
    //   41: putfield sentPingCount : I
    //   44: aload_0
    //   45: iconst_1
    //   46: putfield awaitingPong : Z
    //   49: aload_0
    //   50: monitorexit
    //   51: iload_1
    //   52: iconst_m1
    //   53: if_icmpeq -> 122
    //   56: new java/lang/StringBuilder
    //   59: dup
    //   60: invokespecial <init> : ()V
    //   63: astore_2
    //   64: aload_2
    //   65: ldc_w 'sent ping but didn't receive pong within '
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: pop
    //   72: aload_2
    //   73: aload_0
    //   74: getfield pingIntervalMillis : J
    //   77: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload_2
    //   82: ldc_w 'ms (after '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: aload_2
    //   90: iload_1
    //   91: iconst_1
    //   92: isub
    //   93: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload_2
    //   98: ldc_w ' successful ping/pongs)'
    //   101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: pop
    //   105: aload_0
    //   106: new java/net/SocketTimeoutException
    //   109: dup
    //   110: aload_2
    //   111: invokevirtual toString : ()Ljava/lang/String;
    //   114: invokespecial <init> : (Ljava/lang/String;)V
    //   117: aconst_null
    //   118: invokevirtual failWebSocket : (Ljava/lang/Exception;Lokhttp3/Response;)V
    //   121: return
    //   122: aload_2
    //   123: getstatic okio/ByteString.EMPTY : Lokio/ByteString;
    //   126: invokevirtual writePing : (Lokio/ByteString;)V
    //   129: goto -> 139
    //   132: astore_2
    //   133: aload_0
    //   134: aload_2
    //   135: aconst_null
    //   136: invokevirtual failWebSocket : (Ljava/lang/Exception;Lokhttp3/Response;)V
    //   139: return
    //   140: astore_2
    //   141: aload_0
    //   142: monitorexit
    //   143: aload_2
    //   144: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	140	finally
    //   12	29	140	finally
    //   34	51	140	finally
    //   122	129	132	java/io/IOException
    //   141	143	140	finally
  }
  
  final class CancelRunnable implements Runnable {
    final RealWebSocket this$0;
    
    public void run() {
      RealWebSocket.this.cancel();
    }
  }
  
  static final class Close {
    final long cancelAfterCloseMillis;
    
    final int code;
    
    final ByteString reason;
    
    Close(int param1Int, ByteString param1ByteString, long param1Long) {
      this.code = param1Int;
      this.reason = param1ByteString;
      this.cancelAfterCloseMillis = param1Long;
    }
  }
  
  static final class Message {
    final ByteString data;
    
    final int formatOpcode;
    
    Message(int param1Int, ByteString param1ByteString) {
      this.formatOpcode = param1Int;
      this.data = param1ByteString;
    }
  }
  
  private final class PingRunnable implements Runnable {
    final RealWebSocket this$0;
    
    public void run() {
      RealWebSocket.this.writePingFrame();
    }
  }
  
  public static abstract class Streams implements Closeable {
    public final boolean client;
    
    public final BufferedSink sink;
    
    public final BufferedSource source;
    
    public Streams(boolean param1Boolean, BufferedSource param1BufferedSource, BufferedSink param1BufferedSink) {
      this.client = param1Boolean;
      this.source = param1BufferedSource;
      this.sink = param1BufferedSink;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\ws\RealWebSocket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */