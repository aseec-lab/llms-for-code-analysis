package androidx.core.view;

import android.os.Build;
import android.view.View;
import android.view.Window;

public final class WindowCompat {
  public static final int FEATURE_ACTION_BAR = 8;
  
  public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
  
  public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
  
  public static <T extends View> T requireViewById(Window paramWindow, int paramInt) {
    if (Build.VERSION.SDK_INT >= 28)
      return (T)paramWindow.requireViewById(paramInt); 
    View view = paramWindow.findViewById(paramInt);
    if (view != null)
      return (T)view; 
    throw new IllegalArgumentException("ID does not reference a View inside this Window");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\WindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */