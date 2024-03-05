package androidx.core.widget;

import android.os.Build;
import android.view.View;
import android.widget.ListPopupWindow;

public final class ListPopupWindowCompat {
  public static View.OnTouchListener createDragToOpenListener(ListPopupWindow paramListPopupWindow, View paramView) {
    return (Build.VERSION.SDK_INT >= 19) ? paramListPopupWindow.createDragToOpenListener(paramView) : null;
  }
  
  @Deprecated
  public static View.OnTouchListener createDragToOpenListener(Object paramObject, View paramView) {
    return createDragToOpenListener((ListPopupWindow)paramObject, paramView);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\widget\ListPopupWindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */