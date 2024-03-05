package androidx.core.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.core.internal.view.SupportMenuItem;

public final class MenuItemCompat {
  @Deprecated
  public static final int SHOW_AS_ACTION_ALWAYS = 2;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_IF_ROOM = 1;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_NEVER = 0;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
  
  private static final String TAG = "MenuItemCompat";
  
  @Deprecated
  public static boolean collapseActionView(MenuItem paramMenuItem) {
    return paramMenuItem.collapseActionView();
  }
  
  @Deprecated
  public static boolean expandActionView(MenuItem paramMenuItem) {
    return paramMenuItem.expandActionView();
  }
  
  public static ActionProvider getActionProvider(MenuItem paramMenuItem) {
    if (paramMenuItem instanceof SupportMenuItem)
      return ((SupportMenuItem)paramMenuItem).getSupportActionProvider(); 
    Log.w("MenuItemCompat", "getActionProvider: item does not implement SupportMenuItem; returning null");
    return null;
  }
  
  @Deprecated
  public static View getActionView(MenuItem paramMenuItem) {
    return paramMenuItem.getActionView();
  }
  
  public static int getAlphabeticModifiers(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getAlphabeticModifiers() : ((Build.VERSION.SDK_INT >= 26) ? paramMenuItem.getAlphabeticModifiers() : 0);
  }
  
  public static CharSequence getContentDescription(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getContentDescription() : ((Build.VERSION.SDK_INT >= 26) ? paramMenuItem.getContentDescription() : null);
  }
  
  public static ColorStateList getIconTintList(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getIconTintList() : ((Build.VERSION.SDK_INT >= 26) ? paramMenuItem.getIconTintList() : null);
  }
  
  public static PorterDuff.Mode getIconTintMode(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getIconTintMode() : ((Build.VERSION.SDK_INT >= 26) ? paramMenuItem.getIconTintMode() : null);
  }
  
  public static int getNumericModifiers(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getNumericModifiers() : ((Build.VERSION.SDK_INT >= 26) ? paramMenuItem.getNumericModifiers() : 0);
  }
  
  public static CharSequence getTooltipText(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getTooltipText() : ((Build.VERSION.SDK_INT >= 26) ? paramMenuItem.getTooltipText() : null);
  }
  
  @Deprecated
  public static boolean isActionViewExpanded(MenuItem paramMenuItem) {
    return paramMenuItem.isActionViewExpanded();
  }
  
  public static MenuItem setActionProvider(MenuItem paramMenuItem, ActionProvider paramActionProvider) {
    if (paramMenuItem instanceof SupportMenuItem)
      return (MenuItem)((SupportMenuItem)paramMenuItem).setSupportActionProvider(paramActionProvider); 
    Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
    return paramMenuItem;
  }
  
  @Deprecated
  public static MenuItem setActionView(MenuItem paramMenuItem, int paramInt) {
    return paramMenuItem.setActionView(paramInt);
  }
  
  @Deprecated
  public static MenuItem setActionView(MenuItem paramMenuItem, View paramView) {
    return paramMenuItem.setActionView(paramView);
  }
  
  public static void setAlphabeticShortcut(MenuItem paramMenuItem, char paramChar, int paramInt) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setAlphabeticShortcut(paramChar, paramInt);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramMenuItem.setAlphabeticShortcut(paramChar, paramInt);
    } 
  }
  
  public static void setContentDescription(MenuItem paramMenuItem, CharSequence paramCharSequence) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setContentDescription(paramCharSequence);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramMenuItem.setContentDescription(paramCharSequence);
    } 
  }
  
  public static void setIconTintList(MenuItem paramMenuItem, ColorStateList paramColorStateList) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setIconTintList(paramColorStateList);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramMenuItem.setIconTintList(paramColorStateList);
    } 
  }
  
  public static void setIconTintMode(MenuItem paramMenuItem, PorterDuff.Mode paramMode) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setIconTintMode(paramMode);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramMenuItem.setIconTintMode(paramMode);
    } 
  }
  
  public static void setNumericShortcut(MenuItem paramMenuItem, char paramChar, int paramInt) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setNumericShortcut(paramChar, paramInt);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramMenuItem.setNumericShortcut(paramChar, paramInt);
    } 
  }
  
  @Deprecated
  public static MenuItem setOnActionExpandListener(MenuItem paramMenuItem, final OnActionExpandListener listener) {
    return paramMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
          final MenuItemCompat.OnActionExpandListener val$listener;
          
          public boolean onMenuItemActionCollapse(MenuItem param1MenuItem) {
            return listener.onMenuItemActionCollapse(param1MenuItem);
          }
          
          public boolean onMenuItemActionExpand(MenuItem param1MenuItem) {
            return listener.onMenuItemActionExpand(param1MenuItem);
          }
        });
  }
  
  public static void setShortcut(MenuItem paramMenuItem, char paramChar1, char paramChar2, int paramInt1, int paramInt2) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setShortcut(paramChar1, paramChar2, paramInt1, paramInt2);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramMenuItem.setShortcut(paramChar1, paramChar2, paramInt1, paramInt2);
    } 
  }
  
  @Deprecated
  public static void setShowAsAction(MenuItem paramMenuItem, int paramInt) {
    paramMenuItem.setShowAsAction(paramInt);
  }
  
  public static void setTooltipText(MenuItem paramMenuItem, CharSequence paramCharSequence) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setTooltipText(paramCharSequence);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramMenuItem.setTooltipText(paramCharSequence);
    } 
  }
  
  @Deprecated
  public static interface OnActionExpandListener {
    boolean onMenuItemActionCollapse(MenuItem param1MenuItem);
    
    boolean onMenuItemActionExpand(MenuItem param1MenuItem);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\MenuItemCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */