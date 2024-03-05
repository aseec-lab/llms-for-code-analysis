package androidx.arch.core.executor;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultTaskExecutor extends TaskExecutor {
  private final ExecutorService mDiskIO = Executors.newFixedThreadPool(2, new ThreadFactory() {
        private static final String THREAD_NAME_STEM = "arch_disk_io_%d";
        
        private final AtomicInteger mThreadId = new AtomicInteger(0);
        
        final DefaultTaskExecutor this$0;
        
        public Thread newThread(Runnable param1Runnable) {
          param1Runnable = new Thread(param1Runnable);
          param1Runnable.setName(String.format("arch_disk_io_%d", new Object[] { Integer.valueOf(this.mThreadId.getAndIncrement()) }));
          return (Thread)param1Runnable;
        }
      });
  
  private final Object mLock = new Object();
  
  private volatile Handler mMainHandler;
  
  public void executeOnDiskIO(Runnable paramRunnable) {
    this.mDiskIO.execute(paramRunnable);
  }
  
  public boolean isMainThread() {
    boolean bool;
    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void postToMainThread(Runnable paramRunnable) {
    if (this.mMainHandler == null)
      synchronized (this.mLock) {
        if (this.mMainHandler == null) {
          Handler handler = new Handler();
          this(Looper.getMainLooper());
          this.mMainHandler = handler;
        } 
      }  
    this.mMainHandler.post(paramRunnable);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\arch\core\executor\DefaultTaskExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */