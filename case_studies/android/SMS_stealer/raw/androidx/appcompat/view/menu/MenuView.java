package androidx.appcompat.view.menu;

import android.graphics.drawable.Drawable;

public interface MenuView {
  int getWindowAnimations();
  
  void initialize(MenuBuilder paramMenuBuilder);
  
  public static interface ItemView {
    MenuItemImpl getItemData();
    
    void initialize(MenuItemImpl param1MenuItemImpl, int param1Int);
    
    boolean prefersCondensedTitle();
    
    void setCheckable(boolean param1Boolean);
    
    void setChecked(boolean param1Boolean);
    
    void setEnabled(boolean param1Boolean);
    
    void setIcon(Drawable param1Drawable);
    
    void setShortcut(boolean param1Boolean, char param1Char);
    
    void setTitle(CharSequence param1CharSequence);
    
    boolean showsIcon();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\view\menu\MenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */