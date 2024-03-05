package com.androidnetworking.core;

public class Core {
  private static Core sInstance;
  
  private final ExecutorSupplier mExecutorSupplier = new DefaultExecutorSupplier();
  
  public static Core getInstance() {
    // Byte code:
    //   0: getstatic com/androidnetworking/core/Core.sInstance : Lcom/androidnetworking/core/Core;
    //   3: ifnonnull -> 39
    //   6: ldc com/androidnetworking/core/Core
    //   8: monitorenter
    //   9: getstatic com/androidnetworking/core/Core.sInstance : Lcom/androidnetworking/core/Core;
    //   12: ifnonnull -> 27
    //   15: new com/androidnetworking/core/Core
    //   18: astore_0
    //   19: aload_0
    //   20: invokespecial <init> : ()V
    //   23: aload_0
    //   24: putstatic com/androidnetworking/core/Core.sInstance : Lcom/androidnetworking/core/Core;
    //   27: ldc com/androidnetworking/core/Core
    //   29: monitorexit
    //   30: goto -> 39
    //   33: astore_0
    //   34: ldc com/androidnetworking/core/Core
    //   36: monitorexit
    //   37: aload_0
    //   38: athrow
    //   39: getstatic com/androidnetworking/core/Core.sInstance : Lcom/androidnetworking/core/Core;
    //   42: areturn
    // Exception table:
    //   from	to	target	type
    //   9	27	33	finally
    //   27	30	33	finally
    //   34	37	33	finally
  }
  
  public static void shutDown() {
    if (sInstance != null)
      sInstance = null; 
  }
  
  public ExecutorSupplier getExecutorSupplier() {
    return this.mExecutorSupplier;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\core\Core.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */