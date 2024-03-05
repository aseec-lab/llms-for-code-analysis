package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class AsyncTimeout extends Timeout {
  private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60L);
  
  private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
  
  private static final int TIMEOUT_WRITE_SIZE = 65536;
  
  @Nullable
  static AsyncTimeout head;
  
  private boolean inQueue;
  
  @Nullable
  private AsyncTimeout next;
  
  private long timeoutAt;
  
  @Nullable
  static AsyncTimeout awaitTimeout() throws InterruptedException {
    AsyncTimeout asyncTimeout1 = head.next;
    AsyncTimeout asyncTimeout2 = null;
    if (asyncTimeout1 == null) {
      long l1 = System.nanoTime();
      AsyncTimeout.class.wait(IDLE_TIMEOUT_MILLIS);
      asyncTimeout1 = asyncTimeout2;
      if (head.next == null) {
        asyncTimeout1 = asyncTimeout2;
        if (System.nanoTime() - l1 >= IDLE_TIMEOUT_NANOS)
          asyncTimeout1 = head; 
      } 
      return asyncTimeout1;
    } 
    long l = asyncTimeout1.remainingNanos(System.nanoTime());
    if (l > 0L) {
      long l1 = l / 1000000L;
      AsyncTimeout.class.wait(l1, (int)(l - 1000000L * l1));
      return null;
    } 
    head.next = asyncTimeout1.next;
    asyncTimeout1.next = null;
    return asyncTimeout1;
  }
  
  private static boolean cancelScheduledTimeout(AsyncTimeout paramAsyncTimeout) {
    // Byte code:
    //   0: ldc okio/AsyncTimeout
    //   2: monitorenter
    //   3: getstatic okio/AsyncTimeout.head : Lokio/AsyncTimeout;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnull -> 45
    //   11: aload_1
    //   12: getfield next : Lokio/AsyncTimeout;
    //   15: aload_0
    //   16: if_acmpne -> 37
    //   19: aload_1
    //   20: aload_0
    //   21: getfield next : Lokio/AsyncTimeout;
    //   24: putfield next : Lokio/AsyncTimeout;
    //   27: aload_0
    //   28: aconst_null
    //   29: putfield next : Lokio/AsyncTimeout;
    //   32: ldc okio/AsyncTimeout
    //   34: monitorexit
    //   35: iconst_0
    //   36: ireturn
    //   37: aload_1
    //   38: getfield next : Lokio/AsyncTimeout;
    //   41: astore_1
    //   42: goto -> 7
    //   45: ldc okio/AsyncTimeout
    //   47: monitorexit
    //   48: iconst_1
    //   49: ireturn
    //   50: astore_0
    //   51: ldc okio/AsyncTimeout
    //   53: monitorexit
    //   54: aload_0
    //   55: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	50	finally
    //   11	32	50	finally
    //   37	42	50	finally
  }
  
  private long remainingNanos(long paramLong) {
    return this.timeoutAt - paramLong;
  }
  
  private static void scheduleTimeout(AsyncTimeout paramAsyncTimeout, long paramLong, boolean paramBoolean) {
    // Byte code:
    //   0: ldc okio/AsyncTimeout
    //   2: monitorenter
    //   3: getstatic okio/AsyncTimeout.head : Lokio/AsyncTimeout;
    //   6: ifnonnull -> 39
    //   9: new okio/AsyncTimeout
    //   12: astore #7
    //   14: aload #7
    //   16: invokespecial <init> : ()V
    //   19: aload #7
    //   21: putstatic okio/AsyncTimeout.head : Lokio/AsyncTimeout;
    //   24: new okio/AsyncTimeout$Watchdog
    //   27: astore #7
    //   29: aload #7
    //   31: invokespecial <init> : ()V
    //   34: aload #7
    //   36: invokevirtual start : ()V
    //   39: invokestatic nanoTime : ()J
    //   42: lstore #5
    //   44: lload_1
    //   45: lconst_0
    //   46: lcmp
    //   47: istore #4
    //   49: iload #4
    //   51: ifeq -> 79
    //   54: iload_3
    //   55: ifeq -> 79
    //   58: aload_0
    //   59: lload_1
    //   60: aload_0
    //   61: invokevirtual deadlineNanoTime : ()J
    //   64: lload #5
    //   66: lsub
    //   67: invokestatic min : (JJ)J
    //   70: lload #5
    //   72: ladd
    //   73: putfield timeoutAt : J
    //   76: goto -> 107
    //   79: iload #4
    //   81: ifeq -> 95
    //   84: aload_0
    //   85: lload_1
    //   86: lload #5
    //   88: ladd
    //   89: putfield timeoutAt : J
    //   92: goto -> 107
    //   95: iload_3
    //   96: ifeq -> 187
    //   99: aload_0
    //   100: aload_0
    //   101: invokevirtual deadlineNanoTime : ()J
    //   104: putfield timeoutAt : J
    //   107: aload_0
    //   108: lload #5
    //   110: invokespecial remainingNanos : (J)J
    //   113: lstore_1
    //   114: getstatic okio/AsyncTimeout.head : Lokio/AsyncTimeout;
    //   117: astore #7
    //   119: aload #7
    //   121: getfield next : Lokio/AsyncTimeout;
    //   124: ifnull -> 155
    //   127: lload_1
    //   128: aload #7
    //   130: getfield next : Lokio/AsyncTimeout;
    //   133: lload #5
    //   135: invokespecial remainingNanos : (J)J
    //   138: lcmp
    //   139: ifge -> 145
    //   142: goto -> 155
    //   145: aload #7
    //   147: getfield next : Lokio/AsyncTimeout;
    //   150: astore #7
    //   152: goto -> 119
    //   155: aload_0
    //   156: aload #7
    //   158: getfield next : Lokio/AsyncTimeout;
    //   161: putfield next : Lokio/AsyncTimeout;
    //   164: aload #7
    //   166: aload_0
    //   167: putfield next : Lokio/AsyncTimeout;
    //   170: aload #7
    //   172: getstatic okio/AsyncTimeout.head : Lokio/AsyncTimeout;
    //   175: if_acmpne -> 183
    //   178: ldc okio/AsyncTimeout
    //   180: invokevirtual notify : ()V
    //   183: ldc okio/AsyncTimeout
    //   185: monitorexit
    //   186: return
    //   187: new java/lang/AssertionError
    //   190: astore_0
    //   191: aload_0
    //   192: invokespecial <init> : ()V
    //   195: aload_0
    //   196: athrow
    //   197: astore_0
    //   198: ldc okio/AsyncTimeout
    //   200: monitorexit
    //   201: aload_0
    //   202: athrow
    // Exception table:
    //   from	to	target	type
    //   3	39	197	finally
    //   39	44	197	finally
    //   58	76	197	finally
    //   84	92	197	finally
    //   99	107	197	finally
    //   107	119	197	finally
    //   119	142	197	finally
    //   145	152	197	finally
    //   155	183	197	finally
    //   187	197	197	finally
  }
  
  public final void enter() {
    if (!this.inQueue) {
      long l = timeoutNanos();
      boolean bool = hasDeadline();
      if (l == 0L && !bool)
        return; 
      this.inQueue = true;
      scheduleTimeout(this, l, bool);
      return;
    } 
    throw new IllegalStateException("Unbalanced enter/exit");
  }
  
  final IOException exit(IOException paramIOException) throws IOException {
    return !exit() ? paramIOException : newTimeoutException(paramIOException);
  }
  
  final void exit(boolean paramBoolean) throws IOException {
    if (!exit() || !paramBoolean)
      return; 
    throw newTimeoutException(null);
  }
  
  public final boolean exit() {
    if (!this.inQueue)
      return false; 
    this.inQueue = false;
    return cancelScheduledTimeout(this);
  }
  
  protected IOException newTimeoutException(@Nullable IOException paramIOException) {
    InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
    if (paramIOException != null)
      interruptedIOException.initCause(paramIOException); 
    return interruptedIOException;
  }
  
  public final Sink sink(final Sink sink) {
    return new Sink() {
        final AsyncTimeout this$0;
        
        final Sink val$sink;
        
        public void close() throws IOException {
          Exception exception;
          AsyncTimeout.this.enter();
          try {
            sink.close();
            AsyncTimeout.this.exit(true);
            return;
          } catch (IOException null) {
            throw AsyncTimeout.this.exit(exception);
          } finally {}
          AsyncTimeout.this.exit(false);
          throw exception;
        }
        
        public void flush() throws IOException {
          Exception exception;
          AsyncTimeout.this.enter();
          try {
            sink.flush();
            AsyncTimeout.this.exit(true);
            return;
          } catch (IOException null) {
            throw AsyncTimeout.this.exit(exception);
          } finally {}
          AsyncTimeout.this.exit(false);
          throw exception;
        }
        
        public Timeout timeout() {
          return AsyncTimeout.this;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("AsyncTimeout.sink(");
          stringBuilder.append(sink);
          stringBuilder.append(")");
          return stringBuilder.toString();
        }
        
        public void write(Buffer param1Buffer, long param1Long) throws IOException {
          Util.checkOffsetAndCount(param1Buffer.size, 0L, param1Long);
          while (true) {
            long l = 0L;
            if (param1Long > 0L) {
              long l1;
              Segment segment = param1Buffer.head;
              while (true) {
                l1 = l;
                if (l < 65536L) {
                  l += (segment.limit - segment.pos);
                  if (l >= param1Long) {
                    l1 = param1Long;
                    break;
                  } 
                  segment = segment.next;
                  continue;
                } 
                break;
              } 
              AsyncTimeout.this.enter();
              try {
                sink.write(param1Buffer, l1);
                param1Long -= l1;
                AsyncTimeout.this.exit(true);
                continue;
              } catch (IOException iOException) {
                throw AsyncTimeout.this.exit(iOException);
              } finally {}
              AsyncTimeout.this.exit(false);
              throw param1Buffer;
            } 
            break;
          } 
        }
      };
  }
  
  public final Source source(final Source source) {
    return new Source() {
        final AsyncTimeout this$0;
        
        final Source val$source;
        
        public void close() throws IOException {
          Exception exception;
          try {
            source.close();
            AsyncTimeout.this.exit(true);
            return;
          } catch (IOException null) {
            throw AsyncTimeout.this.exit(exception);
          } finally {}
          AsyncTimeout.this.exit(false);
          throw exception;
        }
        
        public long read(Buffer param1Buffer, long param1Long) throws IOException {
          AsyncTimeout.this.enter();
          try {
            param1Long = source.read(param1Buffer, param1Long);
            AsyncTimeout.this.exit(true);
            return param1Long;
          } catch (IOException iOException) {
            throw AsyncTimeout.this.exit(iOException);
          } finally {}
          AsyncTimeout.this.exit(false);
          throw param1Buffer;
        }
        
        public Timeout timeout() {
          return AsyncTimeout.this;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("AsyncTimeout.source(");
          stringBuilder.append(source);
          stringBuilder.append(")");
          return stringBuilder.toString();
        }
      };
  }
  
  protected void timedOut() {}
  
  private static final class Watchdog extends Thread {
    Watchdog() {
      super("Okio Watchdog");
      setDaemon(true);
    }
    
    public void run() {
      // Byte code:
      //   0: ldc okio/AsyncTimeout
      //   2: monitorenter
      //   3: invokestatic awaitTimeout : ()Lokio/AsyncTimeout;
      //   6: astore_1
      //   7: aload_1
      //   8: ifnonnull -> 17
      //   11: ldc okio/AsyncTimeout
      //   13: monitorexit
      //   14: goto -> 0
      //   17: aload_1
      //   18: getstatic okio/AsyncTimeout.head : Lokio/AsyncTimeout;
      //   21: if_acmpne -> 32
      //   24: aconst_null
      //   25: putstatic okio/AsyncTimeout.head : Lokio/AsyncTimeout;
      //   28: ldc okio/AsyncTimeout
      //   30: monitorexit
      //   31: return
      //   32: ldc okio/AsyncTimeout
      //   34: monitorexit
      //   35: aload_1
      //   36: invokevirtual timedOut : ()V
      //   39: goto -> 0
      //   42: astore_1
      //   43: ldc okio/AsyncTimeout
      //   45: monitorexit
      //   46: aload_1
      //   47: athrow
      //   48: astore_1
      //   49: goto -> 0
      // Exception table:
      //   from	to	target	type
      //   0	3	48	java/lang/InterruptedException
      //   3	7	42	finally
      //   11	14	42	finally
      //   17	31	42	finally
      //   32	35	42	finally
      //   35	39	48	java/lang/InterruptedException
      //   43	46	42	finally
      //   46	48	48	java/lang/InterruptedException
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\AsyncTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */