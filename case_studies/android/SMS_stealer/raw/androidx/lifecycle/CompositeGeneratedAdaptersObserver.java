package androidx.lifecycle;

class CompositeGeneratedAdaptersObserver implements LifecycleEventObserver {
  private final GeneratedAdapter[] mGeneratedAdapters;
  
  CompositeGeneratedAdaptersObserver(GeneratedAdapter[] paramArrayOfGeneratedAdapter) {
    this.mGeneratedAdapters = paramArrayOfGeneratedAdapter;
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent) {
    MethodCallsLogger methodCallsLogger = new MethodCallsLogger();
    GeneratedAdapter[] arrayOfGeneratedAdapter = this.mGeneratedAdapters;
    int i = arrayOfGeneratedAdapter.length;
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++)
      arrayOfGeneratedAdapter[b].callMethods(paramLifecycleOwner, paramEvent, false, methodCallsLogger); 
    arrayOfGeneratedAdapter = this.mGeneratedAdapters;
    i = arrayOfGeneratedAdapter.length;
    for (b = bool; b < i; b++)
      arrayOfGeneratedAdapter[b].callMethods(paramLifecycleOwner, paramEvent, true, methodCallsLogger); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\CompositeGeneratedAdaptersObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */