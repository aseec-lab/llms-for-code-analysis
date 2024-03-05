package androidx.core.view;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.R;

public final class ViewGroupCompat {
  public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
  
  public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;
  
  public static int getLayoutMode(ViewGroup paramViewGroup) {
    return (Build.VERSION.SDK_INT >= 18) ? paramViewGroup.getLayoutMode() : 0;
  }
  
  public static int getNestedScrollAxes(ViewGroup paramViewGroup) {
    return (Build.VERSION.SDK_INT >= 21) ? paramViewGroup.getNestedScrollAxes() : ((paramViewGroup instanceof NestedScrollingParent) ? ((NestedScrollingParent)paramViewGroup).getNestedScrollAxes() : 0);
  }
  
  public static boolean isTransitionGroup(ViewGroup paramViewGroup) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramViewGroup.isTransitionGroup(); 
    Boolean bool = (Boolean)paramViewGroup.getTag(R.id.tag_transition_group);
    return ((bool != null && bool.booleanValue()) || paramViewGroup.getBackground() != null || ViewCompat.getTransitionName((View)paramViewGroup) != null);
  }
  
  @Deprecated
  public static boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent) {
    return paramViewGroup.onRequestSendAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public static void setLayoutMode(ViewGroup paramViewGroup, int paramInt) {
    if (Build.VERSION.SDK_INT >= 18)
      paramViewGroup.setLayoutMode(paramInt); 
  }
  
  @Deprecated
  public static void setMotionEventSplittingEnabled(ViewGroup paramViewGroup, boolean paramBoolean) {
    paramViewGroup.setMotionEventSplittingEnabled(paramBoolean);
  }
  
  public static void setTransitionGroup(ViewGroup paramViewGroup, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramViewGroup.setTransitionGroup(paramBoolean);
    } else {
      paramViewGroup.setTag(R.id.tag_transition_group, Boolean.valueOf(paramBoolean));
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\ViewGroupCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */