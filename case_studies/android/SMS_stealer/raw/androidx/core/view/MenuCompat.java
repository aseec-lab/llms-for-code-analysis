package androidx.core.view;

import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.internal.view.SupportMenu;

public final class MenuCompat {
  public static void setGroupDividerEnabled(Menu paramMenu, boolean paramBoolean) {
    if (paramMenu instanceof SupportMenu) {
      ((SupportMenu)paramMenu).setGroupDividerEnabled(paramBoolean);
    } else if (Build.VERSION.SDK_INT >= 28) {
      paramMenu.setGroupDividerEnabled(paramBoolean);
    } 
  }
  
  @Deprecated
  public static void setShowAsAction(MenuItem paramMenuItem, int paramInt) {
    paramMenuItem.setShowAsAction(paramInt);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\MenuCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */