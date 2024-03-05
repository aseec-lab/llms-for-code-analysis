package com.androidnetworking.core;

import java.util.concurrent.Executor;

public class DefaultExecutorSupplier implements ExecutorSupplier {
  public static final int DEFAULT_MAX_NUM_THREADS = Runtime.getRuntime().availableProcessors() * 2 + 1;
  
  private final ANExecutor mImmediateNetworkExecutor;
  
  private final Executor mMainThreadExecutor;
  
  private final ANExecutor mNetworkExecutor;
  
  public DefaultExecutorSupplier() {
    PriorityThreadFactory priorityThreadFactory = new PriorityThreadFactory(10);
    this.mNetworkExecutor = new ANExecutor(DEFAULT_MAX_NUM_THREADS, priorityThreadFactory);
    this.mImmediateNetworkExecutor = new ANExecutor(2, priorityThreadFactory);
    this.mMainThreadExecutor = new MainThreadExecutor();
  }
  
  public ANExecutor forImmediateNetworkTasks() {
    return this.mImmediateNetworkExecutor;
  }
  
  public Executor forMainThreadTasks() {
    return this.mMainThreadExecutor;
  }
  
  public ANExecutor forNetworkTasks() {
    return this.mNetworkExecutor;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\core\DefaultExecutorSupplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */