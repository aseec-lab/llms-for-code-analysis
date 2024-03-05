package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;

public final class GravityCompat {
  public static final int END = 8388613;
  
  public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
  
  public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
  
  public static final int START = 8388611;
  
  public static void apply(int paramInt1, int paramInt2, int paramInt3, Rect paramRect1, int paramInt4, int paramInt5, Rect paramRect2, int paramInt6) {
    if (Build.VERSION.SDK_INT >= 17) {
      Gravity.apply(paramInt1, paramInt2, paramInt3, paramRect1, paramInt4, paramInt5, paramRect2, paramInt6);
    } else {
      Gravity.apply(paramInt1, paramInt2, paramInt3, paramRect1, paramInt4, paramInt5, paramRect2);
    } 
  }
  
  public static void apply(int paramInt1, int paramInt2, int paramInt3, Rect paramRect1, Rect paramRect2, int paramInt4) {
    if (Build.VERSION.SDK_INT >= 17) {
      Gravity.apply(paramInt1, paramInt2, paramInt3, paramRect1, paramRect2, paramInt4);
    } else {
      Gravity.apply(paramInt1, paramInt2, paramInt3, paramRect1, paramRect2);
    } 
  }
  
  public static void applyDisplay(int paramInt1, Rect paramRect1, Rect paramRect2, int paramInt2) {
    if (Build.VERSION.SDK_INT >= 17) {
      Gravity.applyDisplay(paramInt1, paramRect1, paramRect2, paramInt2);
    } else {
      Gravity.applyDisplay(paramInt1, paramRect1, paramRect2);
    } 
  }
  
  public static int getAbsoluteGravity(int paramInt1, int paramInt2) {
    return (Build.VERSION.SDK_INT >= 17) ? Gravity.getAbsoluteGravity(paramInt1, paramInt2) : (paramInt1 & 0xFF7FFFFF);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\GravityCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */