package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import java.lang.reflect.Method;

public class MenuPopupWindow extends ListPopupWindow implements MenuItemHoverListener {
  private static final String TAG = "MenuPopupWindow";
  
  private static Method sSetTouchModalMethod;
  
  private MenuItemHoverListener mHoverListener;
  
  static {
    try {
      if (Build.VERSION.SDK_INT <= 28)
        sSetTouchModalMethod = PopupWindow.class.getDeclaredMethod("setTouchModal", new Class[] { boolean.class }); 
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
    } 
  }
  
  public MenuPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  DropDownListView createDropDownListView(Context paramContext, boolean paramBoolean) {
    MenuDropDownListView menuDropDownListView = new MenuDropDownListView(paramContext, paramBoolean);
    menuDropDownListView.setHoverListener(this);
    return menuDropDownListView;
  }
  
  public void onItemHoverEnter(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem) {
    MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
    if (menuItemHoverListener != null)
      menuItemHoverListener.onItemHoverEnter(paramMenuBuilder, paramMenuItem); 
  }
  
  public void onItemHoverExit(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem) {
    MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
    if (menuItemHoverListener != null)
      menuItemHoverListener.onItemHoverExit(paramMenuBuilder, paramMenuItem); 
  }
  
  public void setEnterTransition(Object paramObject) {
    if (Build.VERSION.SDK_INT >= 23)
      this.mPopup.setEnterTransition((Transition)paramObject); 
  }
  
  public void setExitTransition(Object paramObject) {
    if (Build.VERSION.SDK_INT >= 23)
      this.mPopup.setExitTransition((Transition)paramObject); 
  }
  
  public void setHoverListener(MenuItemHoverListener paramMenuItemHoverListener) {
    this.mHoverListener = paramMenuItemHoverListener;
  }
  
  public void setTouchModal(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT <= 28) {
      Method method = sSetTouchModalMethod;
      if (method != null)
        try {
          method.invoke(this.mPopup, new Object[] { Boolean.valueOf(paramBoolean) });
        } catch (Exception exception) {
          Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
        }  
    } else {
      this.mPopup.setTouchModal(paramBoolean);
    } 
  }
  
  public static class MenuDropDownListView extends DropDownListView {
    final int mAdvanceKey;
    
    private MenuItemHoverListener mHoverListener;
    
    private MenuItem mHoveredMenuItem;
    
    final int mRetreatKey;
    
    public MenuDropDownListView(Context param1Context, boolean param1Boolean) {
      super(param1Context, param1Boolean);
      Configuration configuration = param1Context.getResources().getConfiguration();
      if (Build.VERSION.SDK_INT >= 17 && 1 == configuration.getLayoutDirection()) {
        this.mAdvanceKey = 21;
        this.mRetreatKey = 22;
      } else {
        this.mAdvanceKey = 22;
        this.mRetreatKey = 21;
      } 
    }
    
    public void clearSelection() {
      setSelection(-1);
    }
    
    public boolean onHoverEvent(MotionEvent param1MotionEvent) {
      if (this.mHoverListener != null) {
        int i;
        MenuItemImpl menuItemImpl;
        MenuAdapter menuAdapter;
        ListAdapter listAdapter = getAdapter();
        if (listAdapter instanceof HeaderViewListAdapter) {
          headerViewListAdapter1 = (HeaderViewListAdapter)listAdapter;
          i = headerViewListAdapter1.getHeadersCount();
          menuAdapter = (MenuAdapter)headerViewListAdapter1.getWrappedAdapter();
        } else {
          i = 0;
          menuAdapter = (MenuAdapter)headerViewListAdapter1;
        } 
        HeaderViewListAdapter headerViewListAdapter2 = null;
        HeaderViewListAdapter headerViewListAdapter1 = headerViewListAdapter2;
        if (param1MotionEvent.getAction() != 10) {
          int j = pointToPosition((int)param1MotionEvent.getX(), (int)param1MotionEvent.getY());
          headerViewListAdapter1 = headerViewListAdapter2;
          if (j != -1) {
            i = j - i;
            headerViewListAdapter1 = headerViewListAdapter2;
            if (i >= 0) {
              headerViewListAdapter1 = headerViewListAdapter2;
              if (i < menuAdapter.getCount())
                menuItemImpl = menuAdapter.getItem(i); 
            } 
          } 
        } 
        MenuItem menuItem = this.mHoveredMenuItem;
        if (menuItem != menuItemImpl) {
          MenuBuilder menuBuilder = menuAdapter.getAdapterMenu();
          if (menuItem != null)
            this.mHoverListener.onItemHoverExit(menuBuilder, menuItem); 
          this.mHoveredMenuItem = (MenuItem)menuItemImpl;
          if (menuItemImpl != null)
            this.mHoverListener.onItemHoverEnter(menuBuilder, (MenuItem)menuItemImpl); 
        } 
      } 
      return super.onHoverEvent(param1MotionEvent);
    }
    
    public boolean onKeyDown(int param1Int, KeyEvent param1KeyEvent) {
      ListMenuItemView listMenuItemView = (ListMenuItemView)getSelectedView();
      if (listMenuItemView != null && param1Int == this.mAdvanceKey) {
        if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu())
          performItemClick((View)listMenuItemView, getSelectedItemPosition(), getSelectedItemId()); 
        return true;
      } 
      if (listMenuItemView != null && param1Int == this.mRetreatKey) {
        setSelection(-1);
        ((MenuAdapter)getAdapter()).getAdapterMenu().close(false);
        return true;
      } 
      return super.onKeyDown(param1Int, param1KeyEvent);
    }
    
    public void setHoverListener(MenuItemHoverListener param1MenuItemHoverListener) {
      this.mHoverListener = param1MenuItemHoverListener;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\MenuPopupWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */