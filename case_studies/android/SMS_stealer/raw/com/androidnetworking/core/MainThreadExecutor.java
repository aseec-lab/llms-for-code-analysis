package com.androidnetworking.core;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
  private final Handler handler = new Handler(Looper.getMainLooper());
  
  public void execute(Runnable paramRunnable) {
    this.handler.post(paramRunnable);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\core\MainThreadExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */