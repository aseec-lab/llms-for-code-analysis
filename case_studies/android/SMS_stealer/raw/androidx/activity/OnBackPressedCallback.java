package androidx.activity;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class OnBackPressedCallback {
  private CopyOnWriteArrayList<Cancellable> mCancellables = new CopyOnWriteArrayList<Cancellable>();
  
  private boolean mEnabled;
  
  public OnBackPressedCallback(boolean paramBoolean) {
    this.mEnabled = paramBoolean;
  }
  
  void addCancellable(Cancellable paramCancellable) {
    this.mCancellables.add(paramCancellable);
  }
  
  public abstract void handleOnBackPressed();
  
  public final boolean isEnabled() {
    return this.mEnabled;
  }
  
  public final void remove() {
    Iterator<Cancellable> iterator = this.mCancellables.iterator();
    while (iterator.hasNext())
      ((Cancellable)iterator.next()).cancel(); 
  }
  
  void removeCancellable(Cancellable paramCancellable) {
    this.mCancellables.remove(paramCancellable);
  }
  
  public final void setEnabled(boolean paramBoolean) {
    this.mEnabled = paramBoolean;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\activity\OnBackPressedCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */