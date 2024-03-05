package com.androidnetworking.core;

import android.os.Process;
import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory implements ThreadFactory {
  private final int mThreadPriority;
  
  public PriorityThreadFactory(int paramInt) {
    this.mThreadPriority = paramInt;
  }
  
  public Thread newThread(final Runnable runnable) {
    return new Thread(new Runnable() {
          final PriorityThreadFactory this$0;
          
          final Runnable val$runnable;
          
          public void run() {
            try {
              Process.setThreadPriority(PriorityThreadFactory.this.mThreadPriority);
            } finally {
              Exception exception;
            } 
            runnable.run();
          }
        });
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\core\PriorityThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */