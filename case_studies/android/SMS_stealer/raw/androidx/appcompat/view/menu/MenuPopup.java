package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

abstract class MenuPopup implements ShowableListMenu, MenuPresenter, AdapterView.OnItemClickListener {
  private Rect mEpicenterBounds;
  
  protected static int measureIndividualMenuWidth(ListAdapter paramListAdapter, ViewGroup paramViewGroup, Context paramContext, int paramInt) {
    byte b = 0;
    int m = View.MeasureSpec.makeMeasureSpec(0, 0);
    int k = View.MeasureSpec.makeMeasureSpec(0, 0);
    int n = paramListAdapter.getCount();
    ViewGroup viewGroup2 = null;
    int i = 0;
    int j = 0;
    ViewGroup viewGroup1 = paramViewGroup;
    paramViewGroup = viewGroup2;
    while (b < n) {
      FrameLayout frameLayout2;
      int i2 = paramListAdapter.getItemViewType(b);
      int i1 = j;
      if (i2 != j) {
        paramViewGroup = null;
        i1 = i2;
      } 
      viewGroup2 = viewGroup1;
      if (viewGroup1 == null)
        frameLayout2 = new FrameLayout(paramContext); 
      View view = paramListAdapter.getView(b, (View)paramViewGroup, (ViewGroup)frameLayout2);
      view.measure(m, k);
      i2 = view.getMeasuredWidth();
      if (i2 >= paramInt)
        return paramInt; 
      j = i;
      if (i2 > i)
        j = i2; 
      b++;
      i = j;
      j = i1;
      FrameLayout frameLayout1 = frameLayout2;
    } 
    return i;
  }
  
  protected static boolean shouldPreserveIconSpacing(MenuBuilder paramMenuBuilder) {
    boolean bool1;
    int i = paramMenuBuilder.size();
    boolean bool2 = false;
    byte b = 0;
    while (true) {
      bool1 = bool2;
      if (b < i) {
        MenuItem menuItem = paramMenuBuilder.getItem(b);
        if (menuItem.isVisible() && menuItem.getIcon() != null) {
          bool1 = true;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    return bool1;
  }
  
  protected static MenuAdapter toMenuAdapter(ListAdapter paramListAdapter) {
    return (paramListAdapter instanceof HeaderViewListAdapter) ? (MenuAdapter)((HeaderViewListAdapter)paramListAdapter).getWrappedAdapter() : (MenuAdapter)paramListAdapter;
  }
  
  public abstract void addMenu(MenuBuilder paramMenuBuilder);
  
  protected boolean closeMenuOnSubMenuOpened() {
    return true;
  }
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  public Rect getEpicenterBounds() {
    return this.mEpicenterBounds;
  }
  
  public int getId() {
    return 0;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup) {
    throw new UnsupportedOperationException("MenuPopups manage their own views");
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {}
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
    ListAdapter listAdapter = (ListAdapter)paramAdapterView.getAdapter();
    MenuBuilder menuBuilder = (toMenuAdapter(listAdapter)).mAdapterMenu;
    MenuItem menuItem = (MenuItem)listAdapter.getItem(paramInt);
    if (closeMenuOnSubMenuOpened()) {
      paramInt = 0;
    } else {
      paramInt = 4;
    } 
    menuBuilder.performItemAction(menuItem, this, paramInt);
  }
  
  public abstract void setAnchorView(View paramView);
  
  public void setEpicenterBounds(Rect paramRect) {
    this.mEpicenterBounds = paramRect;
  }
  
  public abstract void setForceShowIcon(boolean paramBoolean);
  
  public abstract void setGravity(int paramInt);
  
  public abstract void setHorizontalOffset(int paramInt);
  
  public abstract void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener);
  
  public abstract void setShowTitle(boolean paramBoolean);
  
  public abstract void setVerticalOffset(int paramInt);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\view\menu\MenuPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */