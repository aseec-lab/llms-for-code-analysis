package androidx.core.view;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

public final class ViewParentCompat {
  private static final String TAG = "ViewParentCompat";
  
  private static int[] sTempNestedScrollConsumed;
  
  private static int[] getTempNestedScrollConsumed() {
    int[] arrayOfInt = sTempNestedScrollConsumed;
    if (arrayOfInt == null) {
      sTempNestedScrollConsumed = new int[2];
    } else {
      arrayOfInt[0] = 0;
      arrayOfInt[1] = 0;
    } 
    return sTempNestedScrollConsumed;
  }
  
  public static void notifySubtreeAccessibilityStateChanged(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      paramViewParent.notifySubtreeAccessibilityStateChanged(paramView1, paramView2, paramInt); 
  }
  
  public static boolean onNestedFling(ViewParent paramViewParent, View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    StringBuilder stringBuilder;
    if (Build.VERSION.SDK_INT >= 21) {
      try {
        return paramViewParent.onNestedFling(paramView, paramFloat1, paramFloat2, paramBoolean);
      } catch (AbstractMethodError abstractMethodError) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(paramViewParent);
        stringBuilder.append(" does not implement interface method onNestedFling");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
      } 
    } else if (paramViewParent instanceof NestedScrollingParent) {
      return ((NestedScrollingParent)paramViewParent).onNestedFling((View)stringBuilder, paramFloat1, paramFloat2, paramBoolean);
    } 
    return false;
  }
  
  public static boolean onNestedPreFling(ViewParent paramViewParent, View paramView, float paramFloat1, float paramFloat2) {
    if (Build.VERSION.SDK_INT >= 21) {
      try {
        return paramViewParent.onNestedPreFling(paramView, paramFloat1, paramFloat2);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(paramViewParent);
        stringBuilder.append(" does not implement interface method onNestedPreFling");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
      } 
    } else if (paramViewParent instanceof NestedScrollingParent) {
      return ((NestedScrollingParent)paramViewParent).onNestedPreFling((View)abstractMethodError, paramFloat1, paramFloat2);
    } 
    return false;
  }
  
  public static void onNestedPreScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint) {
    onNestedPreScroll(paramViewParent, paramView, paramInt1, paramInt2, paramArrayOfint, 0);
  }
  
  public static void onNestedPreScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
    if (paramViewParent instanceof NestedScrollingParent2) {
      ((NestedScrollingParent2)paramViewParent).onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfint, paramInt3);
    } else if (paramInt3 == 0) {
      StringBuilder stringBuilder;
      if (Build.VERSION.SDK_INT >= 21) {
        try {
          paramViewParent.onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfint);
        } catch (AbstractMethodError abstractMethodError) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("ViewParent ");
          stringBuilder.append(paramViewParent);
          stringBuilder.append(" does not implement interface method onNestedPreScroll");
          Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
        } 
      } else if (paramViewParent instanceof NestedScrollingParent) {
        ((NestedScrollingParent)paramViewParent).onNestedPreScroll((View)stringBuilder, paramInt1, paramInt2, (int[])abstractMethodError);
      } 
    } 
  }
  
  public static void onNestedScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    onNestedScroll(paramViewParent, paramView, paramInt1, paramInt2, paramInt3, paramInt4, 0, getTempNestedScrollConsumed());
  }
  
  public static void onNestedScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    onNestedScroll(paramViewParent, paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, getTempNestedScrollConsumed());
  }
  
  public static void onNestedScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
    if (paramViewParent instanceof NestedScrollingParent3) {
      ((NestedScrollingParent3)paramViewParent).onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfint);
    } else {
      paramArrayOfint[0] = paramArrayOfint[0] + paramInt3;
      paramArrayOfint[1] = paramArrayOfint[1] + paramInt4;
      if (paramViewParent instanceof NestedScrollingParent2) {
        ((NestedScrollingParent2)paramViewParent).onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      } else if (paramInt5 == 0) {
        if (Build.VERSION.SDK_INT >= 21) {
          try {
            paramViewParent.onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
          } catch (AbstractMethodError abstractMethodError) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(paramViewParent);
            stringBuilder.append(" does not implement interface method onNestedScroll");
            Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
          } 
        } else if (paramViewParent instanceof NestedScrollingParent) {
          ((NestedScrollingParent)paramViewParent).onNestedScroll((View)abstractMethodError, paramInt1, paramInt2, paramInt3, paramInt4);
        } 
      } 
    } 
  }
  
  public static void onNestedScrollAccepted(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt) {
    onNestedScrollAccepted(paramViewParent, paramView1, paramView2, paramInt, 0);
  }
  
  public static void onNestedScrollAccepted(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt1, int paramInt2) {
    if (paramViewParent instanceof NestedScrollingParent2) {
      ((NestedScrollingParent2)paramViewParent).onNestedScrollAccepted(paramView1, paramView2, paramInt1, paramInt2);
    } else if (paramInt2 == 0) {
      StringBuilder stringBuilder;
      if (Build.VERSION.SDK_INT >= 21) {
        try {
          paramViewParent.onNestedScrollAccepted(paramView1, paramView2, paramInt1);
        } catch (AbstractMethodError abstractMethodError) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("ViewParent ");
          stringBuilder.append(paramViewParent);
          stringBuilder.append(" does not implement interface method onNestedScrollAccepted");
          Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
        } 
      } else if (paramViewParent instanceof NestedScrollingParent) {
        ((NestedScrollingParent)paramViewParent).onNestedScrollAccepted((View)abstractMethodError, (View)stringBuilder, paramInt1);
      } 
    } 
  }
  
  public static boolean onStartNestedScroll(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt) {
    return onStartNestedScroll(paramViewParent, paramView1, paramView2, paramInt, 0);
  }
  
  public static boolean onStartNestedScroll(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt1, int paramInt2) {
    if (paramViewParent instanceof NestedScrollingParent2)
      return ((NestedScrollingParent2)paramViewParent).onStartNestedScroll(paramView1, paramView2, paramInt1, paramInt2); 
    if (paramInt2 == 0) {
      StringBuilder stringBuilder;
      if (Build.VERSION.SDK_INT >= 21) {
        try {
          return paramViewParent.onStartNestedScroll(paramView1, paramView2, paramInt1);
        } catch (AbstractMethodError abstractMethodError) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("ViewParent ");
          stringBuilder.append(paramViewParent);
          stringBuilder.append(" does not implement interface method onStartNestedScroll");
          Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
        } 
      } else if (paramViewParent instanceof NestedScrollingParent) {
        return ((NestedScrollingParent)paramViewParent).onStartNestedScroll((View)stringBuilder, (View)abstractMethodError, paramInt1);
      } 
    } 
    return false;
  }
  
  public static void onStopNestedScroll(ViewParent paramViewParent, View paramView) {
    onStopNestedScroll(paramViewParent, paramView, 0);
  }
  
  public static void onStopNestedScroll(ViewParent paramViewParent, View paramView, int paramInt) {
    if (paramViewParent instanceof NestedScrollingParent2) {
      ((NestedScrollingParent2)paramViewParent).onStopNestedScroll(paramView, paramInt);
    } else if (paramInt == 0) {
      if (Build.VERSION.SDK_INT >= 21) {
        try {
          paramViewParent.onStopNestedScroll(paramView);
        } catch (AbstractMethodError abstractMethodError) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("ViewParent ");
          stringBuilder.append(paramViewParent);
          stringBuilder.append(" does not implement interface method onStopNestedScroll");
          Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
        } 
      } else if (paramViewParent instanceof NestedScrollingParent) {
        ((NestedScrollingParent)paramViewParent).onStopNestedScroll((View)abstractMethodError);
      } 
    } 
  }
  
  @Deprecated
  public static boolean requestSendAccessibilityEvent(ViewParent paramViewParent, View paramView, AccessibilityEvent paramAccessibilityEvent) {
    return paramViewParent.requestSendAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\ViewParentCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */