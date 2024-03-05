package androidx.activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;

public final class OnBackPressedDispatcher {
  private final Runnable mFallbackOnBackPressed;
  
  final ArrayDeque<OnBackPressedCallback> mOnBackPressedCallbacks = new ArrayDeque<OnBackPressedCallback>();
  
  public OnBackPressedDispatcher() {
    this(null);
  }
  
  public OnBackPressedDispatcher(Runnable paramRunnable) {
    this.mFallbackOnBackPressed = paramRunnable;
  }
  
  public void addCallback(OnBackPressedCallback paramOnBackPressedCallback) {
    addCancellableCallback(paramOnBackPressedCallback);
  }
  
  public void addCallback(LifecycleOwner paramLifecycleOwner, OnBackPressedCallback paramOnBackPressedCallback) {
    Lifecycle lifecycle = paramLifecycleOwner.getLifecycle();
    if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED)
      return; 
    paramOnBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable(lifecycle, paramOnBackPressedCallback));
  }
  
  Cancellable addCancellableCallback(OnBackPressedCallback paramOnBackPressedCallback) {
    this.mOnBackPressedCallbacks.add(paramOnBackPressedCallback);
    OnBackPressedCancellable onBackPressedCancellable = new OnBackPressedCancellable(paramOnBackPressedCallback);
    paramOnBackPressedCallback.addCancellable(onBackPressedCancellable);
    return onBackPressedCancellable;
  }
  
  public boolean hasEnabledCallbacks() {
    Iterator<OnBackPressedCallback> iterator = this.mOnBackPressedCallbacks.descendingIterator();
    while (iterator.hasNext()) {
      if (((OnBackPressedCallback)iterator.next()).isEnabled())
        return true; 
    } 
    return false;
  }
  
  public void onBackPressed() {
    Iterator<OnBackPressedCallback> iterator = this.mOnBackPressedCallbacks.descendingIterator();
    while (iterator.hasNext()) {
      OnBackPressedCallback onBackPressedCallback = iterator.next();
      if (onBackPressedCallback.isEnabled()) {
        onBackPressedCallback.handleOnBackPressed();
        return;
      } 
    } 
    Runnable runnable = this.mFallbackOnBackPressed;
    if (runnable != null)
      runnable.run(); 
  }
  
  private class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, Cancellable {
    private Cancellable mCurrentCancellable;
    
    private final Lifecycle mLifecycle;
    
    private final OnBackPressedCallback mOnBackPressedCallback;
    
    final OnBackPressedDispatcher this$0;
    
    LifecycleOnBackPressedCancellable(Lifecycle param1Lifecycle, OnBackPressedCallback param1OnBackPressedCallback) {
      this.mLifecycle = param1Lifecycle;
      this.mOnBackPressedCallback = param1OnBackPressedCallback;
      param1Lifecycle.addObserver((LifecycleObserver)this);
    }
    
    public void cancel() {
      this.mLifecycle.removeObserver((LifecycleObserver)this);
      this.mOnBackPressedCallback.removeCancellable(this);
      Cancellable cancellable = this.mCurrentCancellable;
      if (cancellable != null) {
        cancellable.cancel();
        this.mCurrentCancellable = null;
      } 
    }
    
    public void onStateChanged(LifecycleOwner param1LifecycleOwner, Lifecycle.Event param1Event) {
      if (param1Event == Lifecycle.Event.ON_START) {
        this.mCurrentCancellable = OnBackPressedDispatcher.this.addCancellableCallback(this.mOnBackPressedCallback);
      } else if (param1Event == Lifecycle.Event.ON_STOP) {
        Cancellable cancellable = this.mCurrentCancellable;
        if (cancellable != null)
          cancellable.cancel(); 
      } else if (param1Event == Lifecycle.Event.ON_DESTROY) {
        cancel();
      } 
    }
  }
  
  private class OnBackPressedCancellable implements Cancellable {
    private final OnBackPressedCallback mOnBackPressedCallback;
    
    final OnBackPressedDispatcher this$0;
    
    OnBackPressedCancellable(OnBackPressedCallback param1OnBackPressedCallback) {
      this.mOnBackPressedCallback = param1OnBackPressedCallback;
    }
    
    public void cancel() {
      OnBackPressedDispatcher.this.mOnBackPressedCallbacks.remove(this.mOnBackPressedCallback);
      this.mOnBackPressedCallback.removeCancellable(this);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\activity\OnBackPressedDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */