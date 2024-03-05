package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpCodec;

public final class StreamAllocation {
  static final boolean $assertionsDisabled = false;
  
  public final Address address;
  
  public final Call call;
  
  private final Object callStackTrace;
  
  private boolean canceled;
  
  private HttpCodec codec;
  
  private RealConnection connection;
  
  private final ConnectionPool connectionPool;
  
  public final EventListener eventListener;
  
  private int refusedStreamCount;
  
  private boolean released;
  
  private boolean reportedAcquired;
  
  private Route route;
  
  private RouteSelector.Selection routeSelection;
  
  private final RouteSelector routeSelector;
  
  public StreamAllocation(ConnectionPool paramConnectionPool, Address paramAddress, Call paramCall, EventListener paramEventListener, Object paramObject) {
    this.connectionPool = paramConnectionPool;
    this.address = paramAddress;
    this.call = paramCall;
    this.eventListener = paramEventListener;
    this.routeSelector = new RouteSelector(paramAddress, routeDatabase(), paramCall, paramEventListener);
    this.callStackTrace = paramObject;
  }
  
  private Socket deallocate(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    // Byte code:
    //   0: aconst_null
    //   1: astore #5
    //   3: iload_3
    //   4: ifeq -> 12
    //   7: aload_0
    //   8: aconst_null
    //   9: putfield codec : Lokhttp3/internal/http/HttpCodec;
    //   12: iload_2
    //   13: ifeq -> 21
    //   16: aload_0
    //   17: iconst_1
    //   18: putfield released : Z
    //   21: aload_0
    //   22: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   25: astore #6
    //   27: aload #5
    //   29: astore #4
    //   31: aload #6
    //   33: ifnull -> 148
    //   36: iload_1
    //   37: ifeq -> 46
    //   40: aload #6
    //   42: iconst_1
    //   43: putfield noNewStreams : Z
    //   46: aload #5
    //   48: astore #4
    //   50: aload_0
    //   51: getfield codec : Lokhttp3/internal/http/HttpCodec;
    //   54: ifnonnull -> 148
    //   57: aload_0
    //   58: getfield released : Z
    //   61: ifne -> 78
    //   64: aload #5
    //   66: astore #4
    //   68: aload_0
    //   69: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   72: getfield noNewStreams : Z
    //   75: ifeq -> 148
    //   78: aload_0
    //   79: aload_0
    //   80: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   83: invokespecial release : (Lokhttp3/internal/connection/RealConnection;)V
    //   86: aload_0
    //   87: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   90: getfield allocations : Ljava/util/List;
    //   93: invokeinterface isEmpty : ()Z
    //   98: ifeq -> 140
    //   101: aload_0
    //   102: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   105: invokestatic nanoTime : ()J
    //   108: putfield idleAtNanos : J
    //   111: getstatic okhttp3/internal/Internal.instance : Lokhttp3/internal/Internal;
    //   114: aload_0
    //   115: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   118: aload_0
    //   119: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   122: invokevirtual connectionBecameIdle : (Lokhttp3/ConnectionPool;Lokhttp3/internal/connection/RealConnection;)Z
    //   125: ifeq -> 140
    //   128: aload_0
    //   129: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   132: invokevirtual socket : ()Ljava/net/Socket;
    //   135: astore #4
    //   137: goto -> 143
    //   140: aconst_null
    //   141: astore #4
    //   143: aload_0
    //   144: aconst_null
    //   145: putfield connection : Lokhttp3/internal/connection/RealConnection;
    //   148: aload #4
    //   150: areturn
  }
  
  private RealConnection findConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   4: astore #14
    //   6: aload #14
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield released : Z
    //   13: ifne -> 639
    //   16: aload_0
    //   17: getfield codec : Lokhttp3/internal/http/HttpCodec;
    //   20: ifnonnull -> 624
    //   23: aload_0
    //   24: getfield canceled : Z
    //   27: ifne -> 609
    //   30: aload_0
    //   31: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   34: astore #10
    //   36: aload_0
    //   37: invokespecial releaseIfNoNewStreams : ()Ljava/net/Socket;
    //   40: astore #15
    //   42: aload_0
    //   43: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   46: astore #11
    //   48: aconst_null
    //   49: astore #13
    //   51: aload #11
    //   53: ifnull -> 68
    //   56: aload_0
    //   57: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   60: astore #11
    //   62: aconst_null
    //   63: astore #10
    //   65: goto -> 71
    //   68: aconst_null
    //   69: astore #11
    //   71: aload #10
    //   73: astore #12
    //   75: aload_0
    //   76: getfield reportedAcquired : Z
    //   79: ifne -> 85
    //   82: aconst_null
    //   83: astore #12
    //   85: aload #11
    //   87: ifnonnull -> 138
    //   90: getstatic okhttp3/internal/Internal.instance : Lokhttp3/internal/Internal;
    //   93: aload_0
    //   94: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   97: aload_0
    //   98: getfield address : Lokhttp3/Address;
    //   101: aload_0
    //   102: aconst_null
    //   103: invokevirtual get : (Lokhttp3/ConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/StreamAllocation;Lokhttp3/Route;)Lokhttp3/internal/connection/RealConnection;
    //   106: pop
    //   107: aload_0
    //   108: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   111: ifnull -> 129
    //   114: aload_0
    //   115: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   118: astore #11
    //   120: aconst_null
    //   121: astore #10
    //   123: iconst_1
    //   124: istore #6
    //   126: goto -> 144
    //   129: aload_0
    //   130: getfield route : Lokhttp3/Route;
    //   133: astore #10
    //   135: goto -> 141
    //   138: aconst_null
    //   139: astore #10
    //   141: iconst_0
    //   142: istore #6
    //   144: aload #14
    //   146: monitorexit
    //   147: aload #15
    //   149: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   152: aload #12
    //   154: ifnull -> 170
    //   157: aload_0
    //   158: getfield eventListener : Lokhttp3/EventListener;
    //   161: aload_0
    //   162: getfield call : Lokhttp3/Call;
    //   165: aload #12
    //   167: invokevirtual connectionReleased : (Lokhttp3/Call;Lokhttp3/Connection;)V
    //   170: iload #6
    //   172: ifeq -> 188
    //   175: aload_0
    //   176: getfield eventListener : Lokhttp3/EventListener;
    //   179: aload_0
    //   180: getfield call : Lokhttp3/Call;
    //   183: aload #11
    //   185: invokevirtual connectionAcquired : (Lokhttp3/Call;Lokhttp3/Connection;)V
    //   188: aload #11
    //   190: ifnull -> 196
    //   193: aload #11
    //   195: areturn
    //   196: aload #10
    //   198: ifnonnull -> 237
    //   201: aload_0
    //   202: getfield routeSelection : Lokhttp3/internal/connection/RouteSelector$Selection;
    //   205: astore #12
    //   207: aload #12
    //   209: ifnull -> 220
    //   212: aload #12
    //   214: invokevirtual hasNext : ()Z
    //   217: ifne -> 237
    //   220: aload_0
    //   221: aload_0
    //   222: getfield routeSelector : Lokhttp3/internal/connection/RouteSelector;
    //   225: invokevirtual next : ()Lokhttp3/internal/connection/RouteSelector$Selection;
    //   228: putfield routeSelection : Lokhttp3/internal/connection/RouteSelector$Selection;
    //   231: iconst_1
    //   232: istore #8
    //   234: goto -> 240
    //   237: iconst_0
    //   238: istore #8
    //   240: aload_0
    //   241: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   244: astore #14
    //   246: aload #14
    //   248: monitorenter
    //   249: aload_0
    //   250: getfield canceled : Z
    //   253: ifne -> 586
    //   256: aload #11
    //   258: astore #12
    //   260: iload #6
    //   262: istore #7
    //   264: iload #8
    //   266: ifeq -> 368
    //   269: aload_0
    //   270: getfield routeSelection : Lokhttp3/internal/connection/RouteSelector$Selection;
    //   273: invokevirtual getAll : ()Ljava/util/List;
    //   276: astore #15
    //   278: aload #15
    //   280: invokeinterface size : ()I
    //   285: istore #9
    //   287: iconst_0
    //   288: istore #8
    //   290: aload #11
    //   292: astore #12
    //   294: iload #6
    //   296: istore #7
    //   298: iload #8
    //   300: iload #9
    //   302: if_icmpge -> 368
    //   305: aload #15
    //   307: iload #8
    //   309: invokeinterface get : (I)Ljava/lang/Object;
    //   314: checkcast okhttp3/Route
    //   317: astore #16
    //   319: getstatic okhttp3/internal/Internal.instance : Lokhttp3/internal/Internal;
    //   322: aload_0
    //   323: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   326: aload_0
    //   327: getfield address : Lokhttp3/Address;
    //   330: aload_0
    //   331: aload #16
    //   333: invokevirtual get : (Lokhttp3/ConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/StreamAllocation;Lokhttp3/Route;)Lokhttp3/internal/connection/RealConnection;
    //   336: pop
    //   337: aload_0
    //   338: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   341: ifnull -> 362
    //   344: aload_0
    //   345: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   348: astore #12
    //   350: aload_0
    //   351: aload #16
    //   353: putfield route : Lokhttp3/Route;
    //   356: iconst_1
    //   357: istore #7
    //   359: goto -> 368
    //   362: iinc #8, 1
    //   365: goto -> 290
    //   368: aload #12
    //   370: astore #11
    //   372: iload #7
    //   374: ifne -> 433
    //   377: aload #10
    //   379: astore #11
    //   381: aload #10
    //   383: ifnonnull -> 395
    //   386: aload_0
    //   387: getfield routeSelection : Lokhttp3/internal/connection/RouteSelector$Selection;
    //   390: invokevirtual next : ()Lokhttp3/Route;
    //   393: astore #11
    //   395: aload_0
    //   396: aload #11
    //   398: putfield route : Lokhttp3/Route;
    //   401: aload_0
    //   402: iconst_0
    //   403: putfield refusedStreamCount : I
    //   406: new okhttp3/internal/connection/RealConnection
    //   409: astore #10
    //   411: aload #10
    //   413: aload_0
    //   414: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   417: aload #11
    //   419: invokespecial <init> : (Lokhttp3/ConnectionPool;Lokhttp3/Route;)V
    //   422: aload_0
    //   423: aload #10
    //   425: iconst_0
    //   426: invokevirtual acquire : (Lokhttp3/internal/connection/RealConnection;Z)V
    //   429: aload #10
    //   431: astore #11
    //   433: aload #14
    //   435: monitorexit
    //   436: iload #7
    //   438: ifeq -> 457
    //   441: aload_0
    //   442: getfield eventListener : Lokhttp3/EventListener;
    //   445: aload_0
    //   446: getfield call : Lokhttp3/Call;
    //   449: aload #11
    //   451: invokevirtual connectionAcquired : (Lokhttp3/Call;Lokhttp3/Connection;)V
    //   454: aload #11
    //   456: areturn
    //   457: aload #11
    //   459: iload_1
    //   460: iload_2
    //   461: iload_3
    //   462: iload #4
    //   464: iload #5
    //   466: aload_0
    //   467: getfield call : Lokhttp3/Call;
    //   470: aload_0
    //   471: getfield eventListener : Lokhttp3/EventListener;
    //   474: invokevirtual connect : (IIIIZLokhttp3/Call;Lokhttp3/EventListener;)V
    //   477: aload_0
    //   478: invokespecial routeDatabase : ()Lokhttp3/internal/connection/RouteDatabase;
    //   481: aload #11
    //   483: invokevirtual route : ()Lokhttp3/Route;
    //   486: invokevirtual connected : (Lokhttp3/Route;)V
    //   489: aload_0
    //   490: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   493: astore #14
    //   495: aload #14
    //   497: monitorenter
    //   498: aload_0
    //   499: iconst_1
    //   500: putfield reportedAcquired : Z
    //   503: getstatic okhttp3/internal/Internal.instance : Lokhttp3/internal/Internal;
    //   506: aload_0
    //   507: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   510: aload #11
    //   512: invokevirtual put : (Lokhttp3/ConnectionPool;Lokhttp3/internal/connection/RealConnection;)V
    //   515: aload #11
    //   517: astore #10
    //   519: aload #13
    //   521: astore #12
    //   523: aload #11
    //   525: invokevirtual isMultiplexed : ()Z
    //   528: ifeq -> 554
    //   531: getstatic okhttp3/internal/Internal.instance : Lokhttp3/internal/Internal;
    //   534: aload_0
    //   535: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   538: aload_0
    //   539: getfield address : Lokhttp3/Address;
    //   542: aload_0
    //   543: invokevirtual deduplicate : (Lokhttp3/ConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/StreamAllocation;)Ljava/net/Socket;
    //   546: astore #12
    //   548: aload_0
    //   549: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   552: astore #10
    //   554: aload #14
    //   556: monitorexit
    //   557: aload #12
    //   559: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   562: aload_0
    //   563: getfield eventListener : Lokhttp3/EventListener;
    //   566: aload_0
    //   567: getfield call : Lokhttp3/Call;
    //   570: aload #10
    //   572: invokevirtual connectionAcquired : (Lokhttp3/Call;Lokhttp3/Connection;)V
    //   575: aload #10
    //   577: areturn
    //   578: astore #10
    //   580: aload #14
    //   582: monitorexit
    //   583: aload #10
    //   585: athrow
    //   586: new java/io/IOException
    //   589: astore #10
    //   591: aload #10
    //   593: ldc 'Canceled'
    //   595: invokespecial <init> : (Ljava/lang/String;)V
    //   598: aload #10
    //   600: athrow
    //   601: astore #10
    //   603: aload #14
    //   605: monitorexit
    //   606: aload #10
    //   608: athrow
    //   609: new java/io/IOException
    //   612: astore #10
    //   614: aload #10
    //   616: ldc 'Canceled'
    //   618: invokespecial <init> : (Ljava/lang/String;)V
    //   621: aload #10
    //   623: athrow
    //   624: new java/lang/IllegalStateException
    //   627: astore #10
    //   629: aload #10
    //   631: ldc 'codec != null'
    //   633: invokespecial <init> : (Ljava/lang/String;)V
    //   636: aload #10
    //   638: athrow
    //   639: new java/lang/IllegalStateException
    //   642: astore #10
    //   644: aload #10
    //   646: ldc 'released'
    //   648: invokespecial <init> : (Ljava/lang/String;)V
    //   651: aload #10
    //   653: athrow
    //   654: astore #10
    //   656: aload #14
    //   658: monitorexit
    //   659: aload #10
    //   661: athrow
    // Exception table:
    //   from	to	target	type
    //   9	48	654	finally
    //   56	62	654	finally
    //   75	82	654	finally
    //   90	120	654	finally
    //   129	135	654	finally
    //   144	147	654	finally
    //   249	256	601	finally
    //   269	287	601	finally
    //   305	356	601	finally
    //   386	395	601	finally
    //   395	429	601	finally
    //   433	436	601	finally
    //   498	515	578	finally
    //   523	554	578	finally
    //   554	557	578	finally
    //   580	583	578	finally
    //   586	601	601	finally
    //   603	606	601	finally
    //   609	624	654	finally
    //   624	639	654	finally
    //   639	654	654	finally
    //   656	659	654	finally
  }
  
  private RealConnection findHealthyConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
    while (true) {
      null = findConnection(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean1);
      synchronized (this.connectionPool) {
        if (null.successCount == 0)
          return null; 
        if (!null.isHealthy(paramBoolean2)) {
          noNewStreams();
          continue;
        } 
        return null;
      } 
    } 
  }
  
  private void release(RealConnection paramRealConnection) {
    int i = paramRealConnection.allocations.size();
    for (byte b = 0; b < i; b++) {
      if (((Reference<StreamAllocation>)paramRealConnection.allocations.get(b)).get() == this) {
        paramRealConnection.allocations.remove(b);
        return;
      } 
    } 
    throw new IllegalStateException();
  }
  
  private Socket releaseIfNoNewStreams() {
    RealConnection realConnection = this.connection;
    return (realConnection != null && realConnection.noNewStreams) ? deallocate(false, false, true) : null;
  }
  
  private RouteDatabase routeDatabase() {
    return Internal.instance.routeDatabase(this.connectionPool);
  }
  
  public void acquire(RealConnection paramRealConnection, boolean paramBoolean) {
    if (this.connection == null) {
      this.connection = paramRealConnection;
      this.reportedAcquired = paramBoolean;
      paramRealConnection.allocations.add(new StreamAllocationReference(this, this.callStackTrace));
      return;
    } 
    throw new IllegalStateException();
  }
  
  public void cancel() {
    synchronized (this.connectionPool) {
      this.canceled = true;
      HttpCodec httpCodec = this.codec;
      RealConnection realConnection = this.connection;
      if (httpCodec != null) {
        httpCodec.cancel();
      } else if (realConnection != null) {
        realConnection.cancel();
      } 
      return;
    } 
  }
  
  public HttpCodec codec() {
    synchronized (this.connectionPool) {
      return this.codec;
    } 
  }
  
  public RealConnection connection() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield connection : Lokhttp3/internal/connection/RealConnection;
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
  
  public boolean hasMoreRoutes() {
    if (this.route == null) {
      RouteSelector.Selection selection = this.routeSelection;
      return ((selection != null && selection.hasNext()) || this.routeSelector.hasNext());
    } 
    return true;
  }
  
  public HttpCodec newStream(OkHttpClient paramOkHttpClient, Interceptor.Chain paramChain, boolean paramBoolean) {
    int k = paramChain.connectTimeoutMillis();
    int m = paramChain.readTimeoutMillis();
    int j = paramChain.writeTimeoutMillis();
    int i = paramOkHttpClient.pingIntervalMillis();
    boolean bool = paramOkHttpClient.retryOnConnectionFailure();
    try {
      null = findHealthyConnection(k, m, j, i, bool, paramBoolean).newCodec(paramOkHttpClient, paramChain, this);
      synchronized (this.connectionPool) {
        this.codec = null;
        return null;
      } 
    } catch (IOException iOException) {
      throw new RouteException(iOException);
    } 
  }
  
  public void noNewStreams() {
    synchronized (this.connectionPool) {
      RealConnection realConnection = this.connection;
      Socket socket = deallocate(true, false, false);
      if (this.connection != null)
        realConnection = null; 
      Util.closeQuietly(socket);
      if (realConnection != null)
        this.eventListener.connectionReleased(this.call, realConnection); 
      return;
    } 
  }
  
  public void release() {
    synchronized (this.connectionPool) {
      RealConnection realConnection = this.connection;
      Socket socket = deallocate(false, true, false);
      if (this.connection != null)
        realConnection = null; 
      Util.closeQuietly(socket);
      if (realConnection != null)
        this.eventListener.connectionReleased(this.call, realConnection); 
      return;
    } 
  }
  
  public Socket releaseAndAcquire(RealConnection paramRealConnection) {
    if (this.codec == null && this.connection.allocations.size() == 1) {
      Reference<StreamAllocation> reference = this.connection.allocations.get(0);
      Socket socket = deallocate(true, false, false);
      this.connection = paramRealConnection;
      paramRealConnection.allocations.add(reference);
      return socket;
    } 
    throw new IllegalStateException();
  }
  
  public Route route() {
    return this.route;
  }
  
  public void streamFailed(IOException paramIOException) {
    // Byte code:
    //   0: aload_0
    //   1: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   4: astore #5
    //   6: aload #5
    //   8: monitorenter
    //   9: aload_1
    //   10: instanceof okhttp3/internal/http2/StreamResetException
    //   13: istore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: iload_2
    //   17: ifeq -> 71
    //   20: aload_1
    //   21: checkcast okhttp3/internal/http2/StreamResetException
    //   24: astore_1
    //   25: aload_1
    //   26: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   29: getstatic okhttp3/internal/http2/ErrorCode.REFUSED_STREAM : Lokhttp3/internal/http2/ErrorCode;
    //   32: if_acmpne -> 45
    //   35: aload_0
    //   36: aload_0
    //   37: getfield refusedStreamCount : I
    //   40: iconst_1
    //   41: iadd
    //   42: putfield refusedStreamCount : I
    //   45: aload_1
    //   46: getfield errorCode : Lokhttp3/internal/http2/ErrorCode;
    //   49: getstatic okhttp3/internal/http2/ErrorCode.REFUSED_STREAM : Lokhttp3/internal/http2/ErrorCode;
    //   52: if_acmpne -> 63
    //   55: aload_0
    //   56: getfield refusedStreamCount : I
    //   59: iconst_1
    //   60: if_icmple -> 138
    //   63: aload_0
    //   64: aconst_null
    //   65: putfield route : Lokhttp3/Route;
    //   68: goto -> 133
    //   71: aload_0
    //   72: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   75: ifnull -> 138
    //   78: aload_0
    //   79: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   82: invokevirtual isMultiplexed : ()Z
    //   85: ifeq -> 95
    //   88: aload_1
    //   89: instanceof okhttp3/internal/http2/ConnectionShutdownException
    //   92: ifeq -> 138
    //   95: aload_0
    //   96: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   99: getfield successCount : I
    //   102: ifne -> 133
    //   105: aload_0
    //   106: getfield route : Lokhttp3/Route;
    //   109: ifnull -> 128
    //   112: aload_1
    //   113: ifnull -> 128
    //   116: aload_0
    //   117: getfield routeSelector : Lokhttp3/internal/connection/RouteSelector;
    //   120: aload_0
    //   121: getfield route : Lokhttp3/Route;
    //   124: aload_1
    //   125: invokevirtual connectFailed : (Lokhttp3/Route;Ljava/io/IOException;)V
    //   128: aload_0
    //   129: aconst_null
    //   130: putfield route : Lokhttp3/Route;
    //   133: iconst_1
    //   134: istore_2
    //   135: goto -> 140
    //   138: iconst_0
    //   139: istore_2
    //   140: aload_0
    //   141: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   144: astore #4
    //   146: aload_0
    //   147: iload_2
    //   148: iconst_0
    //   149: iconst_1
    //   150: invokespecial deallocate : (ZZZ)Ljava/net/Socket;
    //   153: astore #6
    //   155: aload_3
    //   156: astore_1
    //   157: aload_0
    //   158: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   161: ifnonnull -> 179
    //   164: aload_0
    //   165: getfield reportedAcquired : Z
    //   168: ifne -> 176
    //   171: aload_3
    //   172: astore_1
    //   173: goto -> 179
    //   176: aload #4
    //   178: astore_1
    //   179: aload #5
    //   181: monitorexit
    //   182: aload #6
    //   184: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   187: aload_1
    //   188: ifnull -> 203
    //   191: aload_0
    //   192: getfield eventListener : Lokhttp3/EventListener;
    //   195: aload_0
    //   196: getfield call : Lokhttp3/Call;
    //   199: aload_1
    //   200: invokevirtual connectionReleased : (Lokhttp3/Call;Lokhttp3/Connection;)V
    //   203: return
    //   204: astore_1
    //   205: aload #5
    //   207: monitorexit
    //   208: aload_1
    //   209: athrow
    // Exception table:
    //   from	to	target	type
    //   9	14	204	finally
    //   20	45	204	finally
    //   45	63	204	finally
    //   63	68	204	finally
    //   71	95	204	finally
    //   95	112	204	finally
    //   116	128	204	finally
    //   128	133	204	finally
    //   140	155	204	finally
    //   157	171	204	finally
    //   179	182	204	finally
    //   205	208	204	finally
  }
  
  public void streamFinished(boolean paramBoolean, HttpCodec paramHttpCodec, long paramLong, IOException paramIOException) {
    // Byte code:
    //   0: aload_0
    //   1: getfield eventListener : Lokhttp3/EventListener;
    //   4: aload_0
    //   5: getfield call : Lokhttp3/Call;
    //   8: lload_3
    //   9: invokevirtual responseBodyEnd : (Lokhttp3/Call;J)V
    //   12: aload_0
    //   13: getfield connectionPool : Lokhttp3/ConnectionPool;
    //   16: astore #6
    //   18: aload #6
    //   20: monitorenter
    //   21: aload_2
    //   22: ifnull -> 141
    //   25: aload_2
    //   26: aload_0
    //   27: getfield codec : Lokhttp3/internal/http/HttpCodec;
    //   30: if_acmpne -> 141
    //   33: iload_1
    //   34: ifne -> 52
    //   37: aload_0
    //   38: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   41: astore_2
    //   42: aload_2
    //   43: aload_2
    //   44: getfield successCount : I
    //   47: iconst_1
    //   48: iadd
    //   49: putfield successCount : I
    //   52: aload_0
    //   53: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   56: astore_2
    //   57: aload_0
    //   58: iload_1
    //   59: iconst_0
    //   60: iconst_1
    //   61: invokespecial deallocate : (ZZZ)Ljava/net/Socket;
    //   64: astore #7
    //   66: aload_0
    //   67: getfield connection : Lokhttp3/internal/connection/RealConnection;
    //   70: ifnull -> 75
    //   73: aconst_null
    //   74: astore_2
    //   75: aload_0
    //   76: getfield released : Z
    //   79: istore_1
    //   80: aload #6
    //   82: monitorexit
    //   83: aload #7
    //   85: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   88: aload_2
    //   89: ifnull -> 104
    //   92: aload_0
    //   93: getfield eventListener : Lokhttp3/EventListener;
    //   96: aload_0
    //   97: getfield call : Lokhttp3/Call;
    //   100: aload_2
    //   101: invokevirtual connectionReleased : (Lokhttp3/Call;Lokhttp3/Connection;)V
    //   104: aload #5
    //   106: ifnull -> 125
    //   109: aload_0
    //   110: getfield eventListener : Lokhttp3/EventListener;
    //   113: aload_0
    //   114: getfield call : Lokhttp3/Call;
    //   117: aload #5
    //   119: invokevirtual callFailed : (Lokhttp3/Call;Ljava/io/IOException;)V
    //   122: goto -> 140
    //   125: iload_1
    //   126: ifeq -> 140
    //   129: aload_0
    //   130: getfield eventListener : Lokhttp3/EventListener;
    //   133: aload_0
    //   134: getfield call : Lokhttp3/Call;
    //   137: invokevirtual callEnd : (Lokhttp3/Call;)V
    //   140: return
    //   141: new java/lang/IllegalStateException
    //   144: astore #7
    //   146: new java/lang/StringBuilder
    //   149: astore #5
    //   151: aload #5
    //   153: invokespecial <init> : ()V
    //   156: aload #5
    //   158: ldc_w 'expected '
    //   161: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: aload #5
    //   167: aload_0
    //   168: getfield codec : Lokhttp3/internal/http/HttpCodec;
    //   171: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: aload #5
    //   177: ldc_w ' but was '
    //   180: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   183: pop
    //   184: aload #5
    //   186: aload_2
    //   187: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload #7
    //   193: aload #5
    //   195: invokevirtual toString : ()Ljava/lang/String;
    //   198: invokespecial <init> : (Ljava/lang/String;)V
    //   201: aload #7
    //   203: athrow
    //   204: astore_2
    //   205: aload #6
    //   207: monitorexit
    //   208: aload_2
    //   209: athrow
    // Exception table:
    //   from	to	target	type
    //   25	33	204	finally
    //   37	52	204	finally
    //   52	66	204	finally
    //   66	73	204	finally
    //   75	83	204	finally
    //   141	204	204	finally
    //   205	208	204	finally
  }
  
  public String toString() {
    String str;
    RealConnection realConnection = connection();
    if (realConnection != null) {
      str = realConnection.toString();
    } else {
      str = this.address.toString();
    } 
    return str;
  }
  
  public static final class StreamAllocationReference extends WeakReference<StreamAllocation> {
    public final Object callStackTrace;
    
    StreamAllocationReference(StreamAllocation param1StreamAllocation, Object param1Object) {
      super(param1StreamAllocation);
      this.callStackTrace = param1Object;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\connection\StreamAllocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */