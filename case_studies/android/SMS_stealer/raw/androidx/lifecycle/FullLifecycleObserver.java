package androidx.lifecycle;

interface FullLifecycleObserver extends LifecycleObserver {
  void onCreate(LifecycleOwner paramLifecycleOwner);
  
  void onDestroy(LifecycleOwner paramLifecycleOwner);
  
  void onPause(LifecycleOwner paramLifecycleOwner);
  
  void onResume(LifecycleOwner paramLifecycleOwner);
  
  void onStart(LifecycleOwner paramLifecycleOwner);
  
  void onStop(LifecycleOwner paramLifecycleOwner);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\FullLifecycleObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */