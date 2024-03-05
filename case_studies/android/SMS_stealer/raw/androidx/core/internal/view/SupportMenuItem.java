package androidx.core.internal.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.MenuItem;
import android.view.View;
import androidx.core.view.ActionProvider;

public interface SupportMenuItem extends MenuItem {
  public static final int SHOW_AS_ACTION_ALWAYS = 2;
  
  public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
  
  public static final int SHOW_AS_ACTION_IF_ROOM = 1;
  
  public static final int SHOW_AS_ACTION_NEVER = 0;
  
  public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
  
  boolean collapseActionView();
  
  boolean expandActionView();
  
  View getActionView();
  
  int getAlphabeticModifiers();
  
  CharSequence getContentDescription();
  
  ColorStateList getIconTintList();
  
  PorterDuff.Mode getIconTintMode();
  
  int getNumericModifiers();
  
  ActionProvider getSupportActionProvider();
  
  CharSequence getTooltipText();
  
  boolean isActionViewExpanded();
  
  boolean requiresActionButton();
  
  boolean requiresOverflow();
  
  MenuItem setActionView(int paramInt);
  
  MenuItem setActionView(View paramView);
  
  MenuItem setAlphabeticShortcut(char paramChar, int paramInt);
  
  SupportMenuItem setContentDescription(CharSequence paramCharSequence);
  
  MenuItem setIconTintList(ColorStateList paramColorStateList);
  
  MenuItem setIconTintMode(PorterDuff.Mode paramMode);
  
  MenuItem setNumericShortcut(char paramChar, int paramInt);
  
  MenuItem setShortcut(char paramChar1, char paramChar2, int paramInt1, int paramInt2);
  
  void setShowAsAction(int paramInt);
  
  MenuItem setShowAsActionFlags(int paramInt);
  
  SupportMenuItem setSupportActionProvider(ActionProvider paramActionProvider);
  
  SupportMenuItem setTooltipText(CharSequence paramCharSequence);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\internal\view\SupportMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */