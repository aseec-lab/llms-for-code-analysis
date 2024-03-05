package com.androidnetworking.internal;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.core.ANExecutor;
import com.androidnetworking.core.Core;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ANRequestQueue {
  private static ANRequestQueue sInstance;
  
  private final Set<ANRequest> mCurrentRequests = Collections.newSetFromMap(new ConcurrentHashMap<ANRequest, Boolean>());
  
  private AtomicInteger mSequenceGenerator = new AtomicInteger();
  
  private void cancel(RequestFilter paramRequestFilter, boolean paramBoolean) {
    try {
      Iterator<ANRequest> iterator = this.mCurrentRequests.iterator();
      while (iterator.hasNext()) {
        ANRequest aNRequest = iterator.next();
        if (paramRequestFilter.apply(aNRequest)) {
          aNRequest.cancel(paramBoolean);
          if (aNRequest.isCanceled()) {
            aNRequest.destroy();
            iterator.remove();
          } 
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static ANRequestQueue getInstance() {
    // Byte code:
    //   0: getstatic com/androidnetworking/internal/ANRequestQueue.sInstance : Lcom/androidnetworking/internal/ANRequestQueue;
    //   3: ifnonnull -> 39
    //   6: ldc com/androidnetworking/internal/ANRequestQueue
    //   8: monitorenter
    //   9: getstatic com/androidnetworking/internal/ANRequestQueue.sInstance : Lcom/androidnetworking/internal/ANRequestQueue;
    //   12: ifnonnull -> 27
    //   15: new com/androidnetworking/internal/ANRequestQueue
    //   18: astore_0
    //   19: aload_0
    //   20: invokespecial <init> : ()V
    //   23: aload_0
    //   24: putstatic com/androidnetworking/internal/ANRequestQueue.sInstance : Lcom/androidnetworking/internal/ANRequestQueue;
    //   27: ldc com/androidnetworking/internal/ANRequestQueue
    //   29: monitorexit
    //   30: goto -> 39
    //   33: astore_0
    //   34: ldc com/androidnetworking/internal/ANRequestQueue
    //   36: monitorexit
    //   37: aload_0
    //   38: athrow
    //   39: getstatic com/androidnetworking/internal/ANRequestQueue.sInstance : Lcom/androidnetworking/internal/ANRequestQueue;
    //   42: areturn
    // Exception table:
    //   from	to	target	type
    //   9	27	33	finally
    //   27	30	33	finally
    //   34	37	33	finally
  }
  
  public static void initialize() {
    getInstance();
  }
  
  private boolean isRequestWithTheGivenTag(ANRequest paramANRequest, Object paramObject) {
    return (paramANRequest.getTag() == null) ? false : ((paramANRequest.getTag() instanceof String && paramObject instanceof String) ? ((String)paramANRequest.getTag()).equals(paramObject) : paramANRequest.getTag().equals(paramObject));
  }
  
  public ANRequest addRequest(ANRequest paramANRequest) {
    try {
      this.mCurrentRequests.add(paramANRequest);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    try {
      paramANRequest.setSequenceNumber(getSequenceNumber());
      if (paramANRequest.getPriority() == Priority.IMMEDIATE) {
        ANExecutor aNExecutor = Core.getInstance().getExecutorSupplier().forImmediateNetworkTasks();
        InternalRunnable internalRunnable = new InternalRunnable();
        this(paramANRequest);
        paramANRequest.setFuture(aNExecutor.submit(internalRunnable));
      } else {
        ANExecutor aNExecutor = Core.getInstance().getExecutorSupplier().forNetworkTasks();
        InternalRunnable internalRunnable = new InternalRunnable();
        this(paramANRequest);
        paramANRequest.setFuture(aNExecutor.submit(internalRunnable));
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return paramANRequest;
  }
  
  public void cancelAll(boolean paramBoolean) {
    try {
      Iterator<ANRequest> iterator = this.mCurrentRequests.iterator();
      while (iterator.hasNext()) {
        ANRequest aNRequest = iterator.next();
        aNRequest.cancel(paramBoolean);
        if (aNRequest.isCanceled()) {
          aNRequest.destroy();
          iterator.remove();
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void cancelRequestWithGivenTag(Object paramObject, boolean paramBoolean) {
    if (paramObject == null)
      return; 
    try {
      RequestFilter requestFilter = new RequestFilter() {
          final ANRequestQueue this$0;
          
          final Object val$tag;
          
          public boolean apply(ANRequest param1ANRequest) {
            return ANRequestQueue.this.isRequestWithTheGivenTag(param1ANRequest, tag);
          }
        };
      super(this, paramObject);
      cancel(requestFilter, paramBoolean);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void finish(ANRequest paramANRequest) {
    try {
      this.mCurrentRequests.remove(paramANRequest);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public int getSequenceNumber() {
    return this.mSequenceGenerator.incrementAndGet();
  }
  
  public boolean isRequestRunning(Object paramObject) {
    try {
      for (ANRequest aNRequest : this.mCurrentRequests) {
        if (isRequestWithTheGivenTag(aNRequest, paramObject)) {
          boolean bool = aNRequest.isRunning();
          if (bool)
            return true; 
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return false;
  }
  
  public static interface RequestFilter {
    boolean apply(ANRequest param1ANRequest);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\ANRequestQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */