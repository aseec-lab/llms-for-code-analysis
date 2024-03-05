package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;

abstract class BaseMenuWrapper {
  final Context mContext;
  
  private SimpleArrayMap<SupportMenuItem, MenuItem> mMenuItems;
  
  private SimpleArrayMap<SupportSubMenu, SubMenu> mSubMenus;
  
  BaseMenuWrapper(Context paramContext) {
    this.mContext = paramContext;
  }
  
  final MenuItem getMenuItemWrapper(MenuItem paramMenuItem) {
    MenuItem menuItem = paramMenuItem;
    if (paramMenuItem instanceof SupportMenuItem) {
      SupportMenuItem supportMenuItem = (SupportMenuItem)paramMenuItem;
      if (this.mMenuItems == null)
        this.mMenuItems = new SimpleArrayMap(); 
      paramMenuItem = (MenuItem)this.mMenuItems.get(paramMenuItem);
      menuItem = paramMenuItem;
      if (paramMenuItem == null) {
        menuItem = new MenuItemWrapperICS(this.mContext, supportMenuItem);
        this.mMenuItems.put(supportMenuItem, menuItem);
      } 
    } 
    return menuItem;
  }
  
  final SubMenu getSubMenuWrapper(SubMenu paramSubMenu) {
    if (paramSubMenu instanceof SupportSubMenu) {
      SupportSubMenu supportSubMenu = (SupportSubMenu)paramSubMenu;
      if (this.mSubMenus == null)
        this.mSubMenus = new SimpleArrayMap(); 
      SubMenu subMenu = (SubMenu)this.mSubMenus.get(supportSubMenu);
      paramSubMenu = subMenu;
      if (subMenu == null) {
        paramSubMenu = new SubMenuWrapperICS(this.mContext, supportSubMenu);
        this.mSubMenus.put(supportSubMenu, paramSubMenu);
      } 
      return paramSubMenu;
    } 
    return paramSubMenu;
  }
  
  final void internalClear() {
    SimpleArrayMap<SupportMenuItem, MenuItem> simpleArrayMap1 = this.mMenuItems;
    if (simpleArrayMap1 != null)
      simpleArrayMap1.clear(); 
    SimpleArrayMap<SupportSubMenu, SubMenu> simpleArrayMap = this.mSubMenus;
    if (simpleArrayMap != null)
      simpleArrayMap.clear(); 
  }
  
  final void internalRemoveGroup(int paramInt) {
    if (this.mMenuItems == null)
      return; 
    for (int i = 0; i < this.mMenuItems.size(); i = j + 1) {
      int j = i;
      if (((SupportMenuItem)this.mMenuItems.keyAt(i)).getGroupId() == paramInt) {
        this.mMenuItems.removeAt(i);
        j = i - 1;
      } 
    } 
  }
  
  final void internalRemoveItem(int paramInt) {
    if (this.mMenuItems == null)
      return; 
    for (byte b = 0; b < this.mMenuItems.size(); b++) {
      if (((SupportMenuItem)this.mMenuItems.keyAt(b)).getItemId() == paramInt) {
        this.mMenuItems.removeAt(b);
        break;
      } 
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\view\menu\BaseMenuWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */