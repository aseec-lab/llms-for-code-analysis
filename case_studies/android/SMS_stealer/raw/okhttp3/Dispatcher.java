package okhttp3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.annotation.Nullable;

public final class Dispatcher {
  @Nullable
  private ExecutorService executorService;
  
  @Nullable
  private Runnable idleCallback;
  
  private int maxRequests = 64;
  
  private int maxRequestsPerHost = 5;
  
  private final Deque<RealCall.AsyncCall> readyAsyncCalls = new ArrayDeque<RealCall.AsyncCall>();
  
  private final Deque<RealCall.AsyncCall> runningAsyncCalls = new ArrayDeque<RealCall.AsyncCall>();
  
  private final Deque<RealCall> runningSyncCalls = new ArrayDeque<RealCall>();
  
  public Dispatcher() {}
  
  public Dispatcher(ExecutorService paramExecutorService) {
    this.executorService = paramExecutorService;
  }
  
  private <T> void finished(Deque<T> paramDeque, T paramT, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: aload_2
    //   4: invokeinterface remove : (Ljava/lang/Object;)Z
    //   9: ifeq -> 49
    //   12: iload_3
    //   13: ifeq -> 20
    //   16: aload_0
    //   17: invokespecial promoteCalls : ()V
    //   20: aload_0
    //   21: invokevirtual runningCallsCount : ()I
    //   24: istore #4
    //   26: aload_0
    //   27: getfield idleCallback : Ljava/lang/Runnable;
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: iload #4
    //   35: ifne -> 48
    //   38: aload_1
    //   39: ifnull -> 48
    //   42: aload_1
    //   43: invokeinterface run : ()V
    //   48: return
    //   49: new java/lang/AssertionError
    //   52: astore_1
    //   53: aload_1
    //   54: ldc 'Call wasn't in-flight!'
    //   56: invokespecial <init> : (Ljava/lang/Object;)V
    //   59: aload_1
    //   60: athrow
    //   61: astore_1
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_1
    //   65: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	61	finally
    //   16	20	61	finally
    //   20	33	61	finally
    //   49	61	61	finally
    //   62	64	61	finally
  }
  
  private void promoteCalls() {
    if (this.runningAsyncCalls.size() >= this.maxRequests)
      return; 
    if (this.readyAsyncCalls.isEmpty())
      return; 
    Iterator<RealCall.AsyncCall> iterator = this.readyAsyncCalls.iterator();
    while (iterator.hasNext()) {
      RealCall.AsyncCall asyncCall = iterator.next();
      if (runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
        iterator.remove();
        this.runningAsyncCalls.add(asyncCall);
        executorService().execute((Runnable)asyncCall);
      } 
      if (this.runningAsyncCalls.size() >= this.maxRequests)
        break; 
    } 
  }
  
  private int runningCallsForHost(RealCall.AsyncCall paramAsyncCall) {
    Iterator<RealCall.AsyncCall> iterator = this.runningAsyncCalls.iterator();
    byte b = 0;
    while (iterator.hasNext()) {
      RealCall.AsyncCall asyncCall = iterator.next();
      if (!(asyncCall.get()).forWebSocket && asyncCall.host().equals(paramAsyncCall.host()))
        b++; 
    } 
    return b;
  }
  
  public void cancelAll() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield readyAsyncCalls : Ljava/util/Deque;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore_1
    //   12: aload_1
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 39
    //   21: aload_1
    //   22: invokeinterface next : ()Ljava/lang/Object;
    //   27: checkcast okhttp3/RealCall$AsyncCall
    //   30: invokevirtual get : ()Lokhttp3/RealCall;
    //   33: invokevirtual cancel : ()V
    //   36: goto -> 12
    //   39: aload_0
    //   40: getfield runningAsyncCalls : Ljava/util/Deque;
    //   43: invokeinterface iterator : ()Ljava/util/Iterator;
    //   48: astore_1
    //   49: aload_1
    //   50: invokeinterface hasNext : ()Z
    //   55: ifeq -> 76
    //   58: aload_1
    //   59: invokeinterface next : ()Ljava/lang/Object;
    //   64: checkcast okhttp3/RealCall$AsyncCall
    //   67: invokevirtual get : ()Lokhttp3/RealCall;
    //   70: invokevirtual cancel : ()V
    //   73: goto -> 49
    //   76: aload_0
    //   77: getfield runningSyncCalls : Ljava/util/Deque;
    //   80: invokeinterface iterator : ()Ljava/util/Iterator;
    //   85: astore_1
    //   86: aload_1
    //   87: invokeinterface hasNext : ()Z
    //   92: ifeq -> 110
    //   95: aload_1
    //   96: invokeinterface next : ()Ljava/lang/Object;
    //   101: checkcast okhttp3/RealCall
    //   104: invokevirtual cancel : ()V
    //   107: goto -> 86
    //   110: aload_0
    //   111: monitorexit
    //   112: return
    //   113: astore_1
    //   114: aload_0
    //   115: monitorexit
    //   116: aload_1
    //   117: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	113	finally
    //   12	36	113	finally
    //   39	49	113	finally
    //   49	73	113	finally
    //   76	86	113	finally
    //   86	107	113	finally
  }
  
  void enqueue(RealCall.AsyncCall paramAsyncCall) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield runningAsyncCalls : Ljava/util/Deque;
    //   6: invokeinterface size : ()I
    //   11: aload_0
    //   12: getfield maxRequests : I
    //   15: if_icmpge -> 54
    //   18: aload_0
    //   19: aload_1
    //   20: invokespecial runningCallsForHost : (Lokhttp3/RealCall$AsyncCall;)I
    //   23: aload_0
    //   24: getfield maxRequestsPerHost : I
    //   27: if_icmpge -> 54
    //   30: aload_0
    //   31: getfield runningAsyncCalls : Ljava/util/Deque;
    //   34: aload_1
    //   35: invokeinterface add : (Ljava/lang/Object;)Z
    //   40: pop
    //   41: aload_0
    //   42: invokevirtual executorService : ()Ljava/util/concurrent/ExecutorService;
    //   45: aload_1
    //   46: invokeinterface execute : (Ljava/lang/Runnable;)V
    //   51: goto -> 65
    //   54: aload_0
    //   55: getfield readyAsyncCalls : Ljava/util/Deque;
    //   58: aload_1
    //   59: invokeinterface add : (Ljava/lang/Object;)Z
    //   64: pop
    //   65: aload_0
    //   66: monitorexit
    //   67: return
    //   68: astore_1
    //   69: aload_0
    //   70: monitorexit
    //   71: aload_1
    //   72: athrow
    // Exception table:
    //   from	to	target	type
    //   2	51	68	finally
    //   54	65	68	finally
  }
  
  void executed(RealCall paramRealCall) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield runningSyncCalls : Ljava/util/Deque;
    //   6: aload_1
    //   7: invokeinterface add : (Ljava/lang/Object;)Z
    //   12: pop
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  public ExecutorService executorService() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield executorService : Ljava/util/concurrent/ExecutorService;
    //   6: ifnonnull -> 48
    //   9: new java/util/concurrent/ThreadPoolExecutor
    //   12: astore_3
    //   13: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   16: astore_2
    //   17: new java/util/concurrent/SynchronousQueue
    //   20: astore_1
    //   21: aload_1
    //   22: invokespecial <init> : ()V
    //   25: aload_3
    //   26: iconst_0
    //   27: ldc 2147483647
    //   29: ldc2_w 60
    //   32: aload_2
    //   33: aload_1
    //   34: ldc 'OkHttp Dispatcher'
    //   36: iconst_0
    //   37: invokestatic threadFactory : (Ljava/lang/String;Z)Ljava/util/concurrent/ThreadFactory;
    //   40: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V
    //   43: aload_0
    //   44: aload_3
    //   45: putfield executorService : Ljava/util/concurrent/ExecutorService;
    //   48: aload_0
    //   49: getfield executorService : Ljava/util/concurrent/ExecutorService;
    //   52: astore_1
    //   53: aload_0
    //   54: monitorexit
    //   55: aload_1
    //   56: areturn
    //   57: astore_1
    //   58: aload_0
    //   59: monitorexit
    //   60: aload_1
    //   61: athrow
    // Exception table:
    //   from	to	target	type
    //   2	48	57	finally
    //   48	53	57	finally
  }
  
  void finished(RealCall.AsyncCall paramAsyncCall) {
    finished(this.runningAsyncCalls, paramAsyncCall, true);
  }
  
  void finished(RealCall paramRealCall) {
    finished(this.runningSyncCalls, paramRealCall, false);
  }
  
  public int getMaxRequests() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield maxRequests : I
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
  
  public int getMaxRequestsPerHost() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield maxRequestsPerHost : I
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
  
  public List<Call> queuedCalls() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new java/util/ArrayList
    //   5: astore_1
    //   6: aload_1
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: getfield readyAsyncCalls : Ljava/util/Deque;
    //   14: invokeinterface iterator : ()Ljava/util/Iterator;
    //   19: astore_2
    //   20: aload_2
    //   21: invokeinterface hasNext : ()Z
    //   26: ifeq -> 51
    //   29: aload_1
    //   30: aload_2
    //   31: invokeinterface next : ()Ljava/lang/Object;
    //   36: checkcast okhttp3/RealCall$AsyncCall
    //   39: invokevirtual get : ()Lokhttp3/RealCall;
    //   42: invokeinterface add : (Ljava/lang/Object;)Z
    //   47: pop
    //   48: goto -> 20
    //   51: aload_1
    //   52: invokestatic unmodifiableList : (Ljava/util/List;)Ljava/util/List;
    //   55: astore_1
    //   56: aload_0
    //   57: monitorexit
    //   58: aload_1
    //   59: areturn
    //   60: astore_1
    //   61: aload_0
    //   62: monitorexit
    //   63: aload_1
    //   64: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	60	finally
    //   20	48	60	finally
    //   51	56	60	finally
  }
  
  public int queuedCallsCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield readyAsyncCalls : Ljava/util/Deque;
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
  
  public List<Call> runningCalls() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new java/util/ArrayList
    //   5: astore_2
    //   6: aload_2
    //   7: invokespecial <init> : ()V
    //   10: aload_2
    //   11: aload_0
    //   12: getfield runningSyncCalls : Ljava/util/Deque;
    //   15: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   20: pop
    //   21: aload_0
    //   22: getfield runningAsyncCalls : Ljava/util/Deque;
    //   25: invokeinterface iterator : ()Ljava/util/Iterator;
    //   30: astore_1
    //   31: aload_1
    //   32: invokeinterface hasNext : ()Z
    //   37: ifeq -> 62
    //   40: aload_2
    //   41: aload_1
    //   42: invokeinterface next : ()Ljava/lang/Object;
    //   47: checkcast okhttp3/RealCall$AsyncCall
    //   50: invokevirtual get : ()Lokhttp3/RealCall;
    //   53: invokeinterface add : (Ljava/lang/Object;)Z
    //   58: pop
    //   59: goto -> 31
    //   62: aload_2
    //   63: invokestatic unmodifiableList : (Ljava/util/List;)Ljava/util/List;
    //   66: astore_1
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_1
    //   70: areturn
    //   71: astore_1
    //   72: aload_0
    //   73: monitorexit
    //   74: aload_1
    //   75: athrow
    // Exception table:
    //   from	to	target	type
    //   2	31	71	finally
    //   31	59	71	finally
    //   62	67	71	finally
  }
  
  public int runningCallsCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield runningAsyncCalls : Ljava/util/Deque;
    //   6: invokeinterface size : ()I
    //   11: istore_1
    //   12: aload_0
    //   13: getfield runningSyncCalls : Ljava/util/Deque;
    //   16: invokeinterface size : ()I
    //   21: istore_2
    //   22: aload_0
    //   23: monitorexit
    //   24: iload_1
    //   25: iload_2
    //   26: iadd
    //   27: ireturn
    //   28: astore_3
    //   29: aload_0
    //   30: monitorexit
    //   31: aload_3
    //   32: athrow
    // Exception table:
    //   from	to	target	type
    //   2	22	28	finally
  }
  
  public void setIdleCallback(@Nullable Runnable paramRunnable) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield idleCallback : Ljava/lang/Runnable;
    //   7: aload_0
    //   8: monitorexit
    //   9: return
    //   10: astore_1
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_1
    //   14: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	10	finally
  }
  
  public void setMaxRequests(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: iconst_1
    //   4: if_icmplt -> 23
    //   7: aload_0
    //   8: iload_1
    //   9: putfield maxRequests : I
    //   12: aload_0
    //   13: invokespecial promoteCalls : ()V
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: astore_2
    //   20: goto -> 58
    //   23: new java/lang/IllegalArgumentException
    //   26: astore_2
    //   27: new java/lang/StringBuilder
    //   30: astore_3
    //   31: aload_3
    //   32: invokespecial <init> : ()V
    //   35: aload_3
    //   36: ldc 'max < 1: '
    //   38: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: aload_3
    //   43: iload_1
    //   44: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual toString : ()Ljava/lang/String;
    //   53: invokespecial <init> : (Ljava/lang/String;)V
    //   56: aload_2
    //   57: athrow
    //   58: aload_0
    //   59: monitorexit
    //   60: aload_2
    //   61: athrow
    // Exception table:
    //   from	to	target	type
    //   7	16	19	finally
    //   23	58	19	finally
  }
  
  public void setMaxRequestsPerHost(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: iconst_1
    //   4: if_icmplt -> 23
    //   7: aload_0
    //   8: iload_1
    //   9: putfield maxRequestsPerHost : I
    //   12: aload_0
    //   13: invokespecial promoteCalls : ()V
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: astore_2
    //   20: goto -> 58
    //   23: new java/lang/IllegalArgumentException
    //   26: astore_3
    //   27: new java/lang/StringBuilder
    //   30: astore_2
    //   31: aload_2
    //   32: invokespecial <init> : ()V
    //   35: aload_2
    //   36: ldc 'max < 1: '
    //   38: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: aload_2
    //   43: iload_1
    //   44: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload_3
    //   49: aload_2
    //   50: invokevirtual toString : ()Ljava/lang/String;
    //   53: invokespecial <init> : (Ljava/lang/String;)V
    //   56: aload_3
    //   57: athrow
    //   58: aload_0
    //   59: monitorexit
    //   60: aload_2
    //   61: athrow
    // Exception table:
    //   from	to	target	type
    //   7	16	19	finally
    //   23	58	19	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Dispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */