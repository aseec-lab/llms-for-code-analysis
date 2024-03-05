package androidx.lifecycle;

class ReflectiveGenericLifecycleObserver implements LifecycleEventObserver {
  private final ClassesInfoCache.CallbackInfo mInfo;
  
  private final Object mWrapped;
  
  ReflectiveGenericLifecycleObserver(Object paramObject) {
    this.mWrapped = paramObject;
    this.mInfo = ClassesInfoCache.sInstance.getInfo(this.mWrapped.getClass());
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent) {
    this.mInfo.invokeCallbacks(paramLifecycleOwner, paramEvent, this.mWrapped);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\ReflectiveGenericLifecycleObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */