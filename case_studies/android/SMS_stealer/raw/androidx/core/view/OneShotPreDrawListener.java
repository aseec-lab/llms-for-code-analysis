package androidx.core.view;

import android.view.View;
import android.view.ViewTreeObserver;

public final class OneShotPreDrawListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
  private final Runnable mRunnable;
  
  private final View mView;
  
  private ViewTreeObserver mViewTreeObserver;
  
  private OneShotPreDrawListener(View paramView, Runnable paramRunnable) {
    this.mView = paramView;
    this.mViewTreeObserver = paramView.getViewTreeObserver();
    this.mRunnable = paramRunnable;
  }
  
  public static OneShotPreDrawListener add(View paramView, Runnable paramRunnable) {
    if (paramView != null) {
      if (paramRunnable != null) {
        OneShotPreDrawListener oneShotPreDrawListener = new OneShotPreDrawListener(paramView, paramRunnable);
        paramView.getViewTreeObserver().addOnPreDrawListener(oneShotPreDrawListener);
        paramView.addOnAttachStateChangeListener(oneShotPreDrawListener);
        return oneShotPreDrawListener;
      } 
      throw new NullPointerException("runnable == null");
    } 
    throw new NullPointerException("view == null");
  }
  
  public boolean onPreDraw() {
    removeListener();
    this.mRunnable.run();
    return true;
  }
  
  public void onViewAttachedToWindow(View paramView) {
    this.mViewTreeObserver = paramView.getViewTreeObserver();
  }
  
  public void onViewDetachedFromWindow(View paramView) {
    removeListener();
  }
  
  public void removeListener() {
    if (this.mViewTreeObserver.isAlive()) {
      this.mViewTreeObserver.removeOnPreDrawListener(this);
    } else {
      this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
    } 
    this.mView.removeOnAttachStateChangeListener(this);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\OneShotPreDrawListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */