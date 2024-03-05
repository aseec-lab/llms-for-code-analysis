package com.androidnetworking.core;

import android.net.NetworkInfo;
import com.androidnetworking.common.Priority;
import com.androidnetworking.internal.InternalRunnable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ANExecutor extends ThreadPoolExecutor {
  private static final int DEFAULT_THREAD_COUNT = 3;
  
  ANExecutor(int paramInt, ThreadFactory paramThreadFactory) {
    super(paramInt, paramInt, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), paramThreadFactory);
  }
  
  private void setThreadCount(int paramInt) {
    setCorePoolSize(paramInt);
    setMaximumPoolSize(paramInt);
  }
  
  void adjustThreadCount(NetworkInfo paramNetworkInfo) {
    if (paramNetworkInfo == null || !paramNetworkInfo.isConnectedOrConnecting()) {
      setThreadCount(3);
      return;
    } 
    int i = paramNetworkInfo.getType();
    if (i != 0) {
      if (i != 1 && i != 6 && i != 9) {
        setThreadCount(3);
      } else {
        setThreadCount(4);
      } 
    } else {
      i = paramNetworkInfo.getSubtype();
      switch (i) {
        default:
          switch (i) {
            default:
              setThreadCount(3);
              return;
            case 13:
            case 14:
            case 15:
              setThreadCount(3);
              return;
            case 12:
              break;
          } 
        case 3:
        case 4:
        case 5:
        case 6:
          setThreadCount(2);
          return;
        case 1:
        case 2:
          break;
      } 
      setThreadCount(1);
    } 
  }
  
  public Future<?> submit(Runnable paramRunnable) {
    paramRunnable = new AndroidNetworkingFutureTask((InternalRunnable)paramRunnable);
    execute(paramRunnable);
    return (Future<?>)paramRunnable;
  }
  
  private static final class AndroidNetworkingFutureTask extends FutureTask<InternalRunnable> implements Comparable<AndroidNetworkingFutureTask> {
    private final InternalRunnable hunter;
    
    public AndroidNetworkingFutureTask(InternalRunnable param1InternalRunnable) {
      super((Runnable)param1InternalRunnable, null);
      this.hunter = param1InternalRunnable;
    }
    
    public int compareTo(AndroidNetworkingFutureTask param1AndroidNetworkingFutureTask) {
      int i;
      Priority priority1 = this.hunter.getPriority();
      Priority priority2 = param1AndroidNetworkingFutureTask.hunter.getPriority();
      if (priority1 == priority2) {
        i = this.hunter.sequence - param1AndroidNetworkingFutureTask.hunter.sequence;
      } else {
        i = priority2.ordinal() - priority1.ordinal();
      } 
      return i;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\core\ANExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */