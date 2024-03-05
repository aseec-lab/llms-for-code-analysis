package okhttp3;

import java.lang.ref.Reference;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.platform.Platform;

public final class ConnectionPool {
  static final boolean $assertionsDisabled = false;
  
  private static final Executor executor = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp ConnectionPool", true));
  
  private final Runnable cleanupRunnable = new Runnable() {
      final ConnectionPool this$0;
      
      public void run() {
        while (true) {
          long l = ConnectionPool.this.cleanup(System.nanoTime());
          if (l == -1L)
            return; 
          if (l > 0L) {
            long l1 = l / 1000000L;
            synchronized (ConnectionPool.this) {
              ConnectionPool.this.wait(l1, (int)(l - 1000000L * l1));
            } 
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_5} */
          } 
        } 
      }
    };
  
  boolean cleanupRunning;
  
  private final Deque<RealConnection> connections = new ArrayDeque<RealConnection>();
  
  private final long keepAliveDurationNs;
  
  private final int maxIdleConnections;
  
  final RouteDatabase routeDatabase = new RouteDatabase();
  
  public ConnectionPool() {
    this(5, 5L, TimeUnit.MINUTES);
  }
  
  public ConnectionPool(int paramInt, long paramLong, TimeUnit paramTimeUnit) {
    this.maxIdleConnections = paramInt;
    this.keepAliveDurationNs = paramTimeUnit.toNanos(paramLong);
    if (paramLong > 0L)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("keepAliveDuration <= 0: ");
    stringBuilder.append(paramLong);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private int pruneAndGetAllocationCount(RealConnection paramRealConnection, long paramLong) {
    List<Reference> list = paramRealConnection.allocations;
    byte b = 0;
    while (b < list.size()) {
      Reference reference = list.get(b);
      if (reference.get() != null) {
        b++;
        continue;
      } 
      StreamAllocation.StreamAllocationReference streamAllocationReference = (StreamAllocation.StreamAllocationReference)reference;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("A connection to ");
      stringBuilder.append(paramRealConnection.route().address().url());
      stringBuilder.append(" was leaked. Did you forget to close a response body?");
      String str = stringBuilder.toString();
      Platform.get().logCloseableLeak(str, streamAllocationReference.callStackTrace);
      list.remove(b);
      paramRealConnection.noNewStreams = true;
      if (list.isEmpty()) {
        paramRealConnection.idleAtNanos = paramLong - this.keepAliveDurationNs;
        return 0;
      } 
    } 
    return list.size();
  }
  
  long cleanup(long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield connections : Ljava/util/Deque;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore #12
    //   13: aconst_null
    //   14: astore #10
    //   16: ldc2_w -9223372036854775808
    //   19: lstore #6
    //   21: iconst_0
    //   22: istore #4
    //   24: iconst_0
    //   25: istore_3
    //   26: aload #12
    //   28: invokeinterface hasNext : ()Z
    //   33: ifeq -> 106
    //   36: aload #12
    //   38: invokeinterface next : ()Ljava/lang/Object;
    //   43: checkcast okhttp3/internal/connection/RealConnection
    //   46: astore #11
    //   48: aload_0
    //   49: aload #11
    //   51: lload_1
    //   52: invokespecial pruneAndGetAllocationCount : (Lokhttp3/internal/connection/RealConnection;J)I
    //   55: ifle -> 64
    //   58: iinc #3, 1
    //   61: goto -> 26
    //   64: iload #4
    //   66: iconst_1
    //   67: iadd
    //   68: istore #5
    //   70: lload_1
    //   71: aload #11
    //   73: getfield idleAtNanos : J
    //   76: lsub
    //   77: lstore #8
    //   79: iload #5
    //   81: istore #4
    //   83: lload #8
    //   85: lload #6
    //   87: lcmp
    //   88: ifle -> 26
    //   91: aload #11
    //   93: astore #10
    //   95: lload #8
    //   97: lstore #6
    //   99: iload #5
    //   101: istore #4
    //   103: goto -> 26
    //   106: lload #6
    //   108: aload_0
    //   109: getfield keepAliveDurationNs : J
    //   112: lcmp
    //   113: ifge -> 169
    //   116: iload #4
    //   118: aload_0
    //   119: getfield maxIdleConnections : I
    //   122: if_icmple -> 128
    //   125: goto -> 169
    //   128: iload #4
    //   130: ifle -> 145
    //   133: aload_0
    //   134: getfield keepAliveDurationNs : J
    //   137: lstore_1
    //   138: aload_0
    //   139: monitorexit
    //   140: lload_1
    //   141: lload #6
    //   143: lsub
    //   144: lreturn
    //   145: iload_3
    //   146: ifle -> 158
    //   149: aload_0
    //   150: getfield keepAliveDurationNs : J
    //   153: lstore_1
    //   154: aload_0
    //   155: monitorexit
    //   156: lload_1
    //   157: lreturn
    //   158: aload_0
    //   159: iconst_0
    //   160: putfield cleanupRunning : Z
    //   163: aload_0
    //   164: monitorexit
    //   165: ldc2_w -1
    //   168: lreturn
    //   169: aload_0
    //   170: getfield connections : Ljava/util/Deque;
    //   173: aload #10
    //   175: invokeinterface remove : (Ljava/lang/Object;)Z
    //   180: pop
    //   181: aload_0
    //   182: monitorexit
    //   183: aload #10
    //   185: invokevirtual socket : ()Ljava/net/Socket;
    //   188: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   191: lconst_0
    //   192: lreturn
    //   193: astore #10
    //   195: aload_0
    //   196: monitorexit
    //   197: aload #10
    //   199: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	193	finally
    //   26	58	193	finally
    //   70	79	193	finally
    //   106	125	193	finally
    //   133	140	193	finally
    //   149	156	193	finally
    //   158	165	193	finally
    //   169	183	193	finally
    //   195	197	193	finally
  }
  
  boolean connectionBecameIdle(RealConnection paramRealConnection) {
    if (paramRealConnection.noNewStreams || this.maxIdleConnections == 0) {
      this.connections.remove(paramRealConnection);
      return true;
    } 
    notifyAll();
    return false;
  }
  
  public int connectionCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield connections : Ljava/util/Deque;
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
  
  @Nullable
  Socket deduplicate(Address paramAddress, StreamAllocation paramStreamAllocation) {
    for (RealConnection realConnection : this.connections) {
      if (realConnection.isEligible(paramAddress, null) && realConnection.isMultiplexed() && realConnection != paramStreamAllocation.connection())
        return paramStreamAllocation.releaseAndAcquire(realConnection); 
    } 
    return null;
  }
  
  public void evictAll() {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_1
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield connections : Ljava/util/Deque;
    //   14: invokeinterface iterator : ()Ljava/util/Iterator;
    //   19: astore_2
    //   20: aload_2
    //   21: invokeinterface hasNext : ()Z
    //   26: ifeq -> 73
    //   29: aload_2
    //   30: invokeinterface next : ()Ljava/lang/Object;
    //   35: checkcast okhttp3/internal/connection/RealConnection
    //   38: astore_3
    //   39: aload_3
    //   40: getfield allocations : Ljava/util/List;
    //   43: invokeinterface isEmpty : ()Z
    //   48: ifeq -> 20
    //   51: aload_3
    //   52: iconst_1
    //   53: putfield noNewStreams : Z
    //   56: aload_1
    //   57: aload_3
    //   58: invokeinterface add : (Ljava/lang/Object;)Z
    //   63: pop
    //   64: aload_2
    //   65: invokeinterface remove : ()V
    //   70: goto -> 20
    //   73: aload_0
    //   74: monitorexit
    //   75: aload_1
    //   76: invokeinterface iterator : ()Ljava/util/Iterator;
    //   81: astore_1
    //   82: aload_1
    //   83: invokeinterface hasNext : ()Z
    //   88: ifeq -> 109
    //   91: aload_1
    //   92: invokeinterface next : ()Ljava/lang/Object;
    //   97: checkcast okhttp3/internal/connection/RealConnection
    //   100: invokevirtual socket : ()Ljava/net/Socket;
    //   103: invokestatic closeQuietly : (Ljava/net/Socket;)V
    //   106: goto -> 82
    //   109: return
    //   110: astore_1
    //   111: aload_0
    //   112: monitorexit
    //   113: aload_1
    //   114: athrow
    // Exception table:
    //   from	to	target	type
    //   10	20	110	finally
    //   20	70	110	finally
    //   73	75	110	finally
    //   111	113	110	finally
  }
  
  @Nullable
  RealConnection get(Address paramAddress, StreamAllocation paramStreamAllocation, Route paramRoute) {
    for (RealConnection realConnection : this.connections) {
      if (realConnection.isEligible(paramAddress, paramRoute)) {
        paramStreamAllocation.acquire(realConnection, true);
        return realConnection;
      } 
    } 
    return null;
  }
  
  public int idleConnectionCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_0
    //   3: istore_1
    //   4: aload_0
    //   5: getfield connections : Ljava/util/Deque;
    //   8: invokeinterface iterator : ()Ljava/util/Iterator;
    //   13: astore_3
    //   14: aload_3
    //   15: invokeinterface hasNext : ()Z
    //   20: ifeq -> 51
    //   23: aload_3
    //   24: invokeinterface next : ()Ljava/lang/Object;
    //   29: checkcast okhttp3/internal/connection/RealConnection
    //   32: getfield allocations : Ljava/util/List;
    //   35: invokeinterface isEmpty : ()Z
    //   40: istore_2
    //   41: iload_2
    //   42: ifeq -> 14
    //   45: iinc #1, 1
    //   48: goto -> 14
    //   51: aload_0
    //   52: monitorexit
    //   53: iload_1
    //   54: ireturn
    //   55: astore_3
    //   56: aload_0
    //   57: monitorexit
    //   58: aload_3
    //   59: athrow
    // Exception table:
    //   from	to	target	type
    //   4	14	55	finally
    //   14	41	55	finally
  }
  
  void put(RealConnection paramRealConnection) {
    if (!this.cleanupRunning) {
      this.cleanupRunning = true;
      executor.execute(this.cleanupRunnable);
    } 
    this.connections.add(paramRealConnection);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\ConnectionPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */