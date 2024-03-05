package androidx.appcompat.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug.CapturedViewProperty;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.view.ActionProvider;

public final class MenuItemImpl implements SupportMenuItem {
  private static final int CHECKABLE = 1;
  
  private static final int CHECKED = 2;
  
  private static final int ENABLED = 16;
  
  private static final int EXCLUSIVE = 4;
  
  private static final int HIDDEN = 8;
  
  private static final int IS_ACTION = 32;
  
  static final int NO_ICON = 0;
  
  private static final int SHOW_AS_ACTION_MASK = 3;
  
  private static final String TAG = "MenuItemImpl";
  
  private ActionProvider mActionProvider;
  
  private View mActionView;
  
  private final int mCategoryOrder;
  
  private MenuItem.OnMenuItemClickListener mClickListener;
  
  private CharSequence mContentDescription;
  
  private int mFlags = 16;
  
  private final int mGroup;
  
  private boolean mHasIconTint = false;
  
  private boolean mHasIconTintMode = false;
  
  private Drawable mIconDrawable;
  
  private int mIconResId = 0;
  
  private ColorStateList mIconTintList = null;
  
  private PorterDuff.Mode mIconTintMode = null;
  
  private final int mId;
  
  private Intent mIntent;
  
  private boolean mIsActionViewExpanded = false;
  
  private Runnable mItemCallback;
  
  MenuBuilder mMenu;
  
  private ContextMenu.ContextMenuInfo mMenuInfo;
  
  private boolean mNeedToApplyIconTint = false;
  
  private MenuItem.OnActionExpandListener mOnActionExpandListener;
  
  private final int mOrdering;
  
  private char mShortcutAlphabeticChar;
  
  private int mShortcutAlphabeticModifiers = 4096;
  
  private char mShortcutNumericChar;
  
  private int mShortcutNumericModifiers = 4096;
  
  private int mShowAsAction = 0;
  
  private SubMenuBuilder mSubMenu;
  
  private CharSequence mTitle;
  
  private CharSequence mTitleCondensed;
  
  private CharSequence mTooltipText;
  
  MenuItemImpl(MenuBuilder paramMenuBuilder, int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence, int paramInt5) {
    this.mMenu = paramMenuBuilder;
    this.mId = paramInt2;
    this.mGroup = paramInt1;
    this.mCategoryOrder = paramInt3;
    this.mOrdering = paramInt4;
    this.mTitle = paramCharSequence;
    this.mShowAsAction = paramInt5;
  }
  
  private static void appendModifier(StringBuilder paramStringBuilder, int paramInt1, int paramInt2, String paramString) {
    if ((paramInt1 & paramInt2) == paramInt2)
      paramStringBuilder.append(paramString); 
  }
  
  private Drawable applyIconTintIfNecessary(Drawable paramDrawable) {
    // Byte code:
    //   0: aload_1
    //   1: astore_2
    //   2: aload_1
    //   3: ifnull -> 74
    //   6: aload_1
    //   7: astore_2
    //   8: aload_0
    //   9: getfield mNeedToApplyIconTint : Z
    //   12: ifeq -> 74
    //   15: aload_0
    //   16: getfield mHasIconTint : Z
    //   19: ifne -> 31
    //   22: aload_1
    //   23: astore_2
    //   24: aload_0
    //   25: getfield mHasIconTintMode : Z
    //   28: ifeq -> 74
    //   31: aload_1
    //   32: invokestatic wrap : (Landroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/Drawable;
    //   35: invokevirtual mutate : ()Landroid/graphics/drawable/Drawable;
    //   38: astore_2
    //   39: aload_0
    //   40: getfield mHasIconTint : Z
    //   43: ifeq -> 54
    //   46: aload_2
    //   47: aload_0
    //   48: getfield mIconTintList : Landroid/content/res/ColorStateList;
    //   51: invokestatic setTintList : (Landroid/graphics/drawable/Drawable;Landroid/content/res/ColorStateList;)V
    //   54: aload_0
    //   55: getfield mHasIconTintMode : Z
    //   58: ifeq -> 69
    //   61: aload_2
    //   62: aload_0
    //   63: getfield mIconTintMode : Landroid/graphics/PorterDuff$Mode;
    //   66: invokestatic setTintMode : (Landroid/graphics/drawable/Drawable;Landroid/graphics/PorterDuff$Mode;)V
    //   69: aload_0
    //   70: iconst_0
    //   71: putfield mNeedToApplyIconTint : Z
    //   74: aload_2
    //   75: areturn
  }
  
  public void actionFormatChanged() {
    this.mMenu.onItemActionRequestChanged(this);
  }
  
  public boolean collapseActionView() {
    if ((this.mShowAsAction & 0x8) == 0)
      return false; 
    if (this.mActionView == null)
      return true; 
    MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
    return (onActionExpandListener == null || onActionExpandListener.onMenuItemActionCollapse((MenuItem)this)) ? this.mMenu.collapseItemActionView(this) : false;
  }
  
  public boolean expandActionView() {
    if (!hasCollapsibleActionView())
      return false; 
    MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
    return (onActionExpandListener == null || onActionExpandListener.onMenuItemActionExpand((MenuItem)this)) ? this.mMenu.expandItemActionView(this) : false;
  }
  
  public ActionProvider getActionProvider() {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
  }
  
  public View getActionView() {
    View view = this.mActionView;
    if (view != null)
      return view; 
    ActionProvider actionProvider = this.mActionProvider;
    if (actionProvider != null) {
      View view1 = actionProvider.onCreateActionView((MenuItem)this);
      this.mActionView = view1;
      return view1;
    } 
    return null;
  }
  
  public int getAlphabeticModifiers() {
    return this.mShortcutAlphabeticModifiers;
  }
  
  public char getAlphabeticShortcut() {
    return this.mShortcutAlphabeticChar;
  }
  
  Runnable getCallback() {
    return this.mItemCallback;
  }
  
  public CharSequence getContentDescription() {
    return this.mContentDescription;
  }
  
  public int getGroupId() {
    return this.mGroup;
  }
  
  public Drawable getIcon() {
    Drawable drawable = this.mIconDrawable;
    if (drawable != null)
      return applyIconTintIfNecessary(drawable); 
    if (this.mIconResId != 0) {
      drawable = AppCompatResources.getDrawable(this.mMenu.getContext(), this.mIconResId);
      this.mIconResId = 0;
      this.mIconDrawable = drawable;
      return applyIconTintIfNecessary(drawable);
    } 
    return null;
  }
  
  public ColorStateList getIconTintList() {
    return this.mIconTintList;
  }
  
  public PorterDuff.Mode getIconTintMode() {
    return this.mIconTintMode;
  }
  
  public Intent getIntent() {
    return this.mIntent;
  }
  
  @CapturedViewProperty
  public int getItemId() {
    return this.mId;
  }
  
  public ContextMenu.ContextMenuInfo getMenuInfo() {
    return this.mMenuInfo;
  }
  
  public int getNumericModifiers() {
    return this.mShortcutNumericModifiers;
  }
  
  public char getNumericShortcut() {
    return this.mShortcutNumericChar;
  }
  
  public int getOrder() {
    return this.mCategoryOrder;
  }
  
  public int getOrdering() {
    return this.mOrdering;
  }
  
  char getShortcut() {
    char c;
    if (this.mMenu.isQwertyMode()) {
      c = this.mShortcutAlphabeticChar;
    } else {
      c = this.mShortcutNumericChar;
    } 
    return c;
  }
  
  String getShortcutLabel() {
    int i;
    char c = getShortcut();
    if (c == '\000')
      return ""; 
    Resources resources = this.mMenu.getContext().getResources();
    StringBuilder stringBuilder = new StringBuilder();
    if (ViewConfiguration.get(this.mMenu.getContext()).hasPermanentMenuKey())
      stringBuilder.append(resources.getString(R.string.abc_prepend_shortcut_label)); 
    if (this.mMenu.isQwertyMode()) {
      i = this.mShortcutAlphabeticModifiers;
    } else {
      i = this.mShortcutNumericModifiers;
    } 
    appendModifier(stringBuilder, i, 65536, resources.getString(R.string.abc_menu_meta_shortcut_label));
    appendModifier(stringBuilder, i, 4096, resources.getString(R.string.abc_menu_ctrl_shortcut_label));
    appendModifier(stringBuilder, i, 2, resources.getString(R.string.abc_menu_alt_shortcut_label));
    appendModifier(stringBuilder, i, 1, resources.getString(R.string.abc_menu_shift_shortcut_label));
    appendModifier(stringBuilder, i, 4, resources.getString(R.string.abc_menu_sym_shortcut_label));
    appendModifier(stringBuilder, i, 8, resources.getString(R.string.abc_menu_function_shortcut_label));
    if (c != '\b') {
      if (c != '\n') {
        if (c != ' ') {
          stringBuilder.append(c);
        } else {
          stringBuilder.append(resources.getString(R.string.abc_menu_space_shortcut_label));
        } 
      } else {
        stringBuilder.append(resources.getString(R.string.abc_menu_enter_shortcut_label));
      } 
    } else {
      stringBuilder.append(resources.getString(R.string.abc_menu_delete_shortcut_label));
    } 
    return stringBuilder.toString();
  }
  
  public SubMenu getSubMenu() {
    return this.mSubMenu;
  }
  
  public ActionProvider getSupportActionProvider() {
    return this.mActionProvider;
  }
  
  @CapturedViewProperty
  public CharSequence getTitle() {
    return this.mTitle;
  }
  
  public CharSequence getTitleCondensed() {
    CharSequence charSequence1 = this.mTitleCondensed;
    if (charSequence1 == null)
      charSequence1 = this.mTitle; 
    CharSequence charSequence2 = charSequence1;
    if (Build.VERSION.SDK_INT < 18) {
      charSequence2 = charSequence1;
      if (charSequence1 != null) {
        charSequence2 = charSequence1;
        if (!(charSequence1 instanceof String))
          charSequence2 = charSequence1.toString(); 
      } 
    } 
    return charSequence2;
  }
  
  CharSequence getTitleForItemView(MenuView.ItemView paramItemView) {
    CharSequence charSequence;
    if (paramItemView != null && paramItemView.prefersCondensedTitle()) {
      charSequence = getTitleCondensed();
    } else {
      charSequence = getTitle();
    } 
    return charSequence;
  }
  
  public CharSequence getTooltipText() {
    return this.mTooltipText;
  }
  
  public boolean hasCollapsibleActionView() {
    int i = this.mShowAsAction;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((i & 0x8) != 0) {
      if (this.mActionView == null) {
        ActionProvider actionProvider = this.mActionProvider;
        if (actionProvider != null)
          this.mActionView = actionProvider.onCreateActionView((MenuItem)this); 
      } 
      bool1 = bool2;
      if (this.mActionView != null)
        bool1 = true; 
    } 
    return bool1;
  }
  
  public boolean hasSubMenu() {
    boolean bool;
    if (this.mSubMenu != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean invoke() {
    MenuItem.OnMenuItemClickListener onMenuItemClickListener = this.mClickListener;
    if (onMenuItemClickListener != null && onMenuItemClickListener.onMenuItemClick((MenuItem)this))
      return true; 
    MenuBuilder menuBuilder = this.mMenu;
    if (menuBuilder.dispatchMenuItemSelected(menuBuilder, (MenuItem)this))
      return true; 
    Runnable runnable = this.mItemCallback;
    if (runnable != null) {
      runnable.run();
      return true;
    } 
    if (this.mIntent != null)
      try {
        this.mMenu.getContext().startActivity(this.mIntent);
        return true;
      } catch (ActivityNotFoundException activityNotFoundException) {
        Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", (Throwable)activityNotFoundException);
      }  
    ActionProvider actionProvider = this.mActionProvider;
    return (actionProvider != null && actionProvider.onPerformDefaultAction());
  }
  
  public boolean isActionButton() {
    boolean bool;
    if ((this.mFlags & 0x20) == 32) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isActionViewExpanded() {
    return this.mIsActionViewExpanded;
  }
  
  public boolean isCheckable() {
    int i = this.mFlags;
    boolean bool = true;
    if ((i & 0x1) != 1)
      bool = false; 
    return bool;
  }
  
  public boolean isChecked() {
    boolean bool;
    if ((this.mFlags & 0x2) == 2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isEnabled() {
    boolean bool;
    if ((this.mFlags & 0x10) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isExclusiveCheckable() {
    boolean bool;
    if ((this.mFlags & 0x4) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isVisible() {
    ActionProvider actionProvider = this.mActionProvider;
    boolean bool2 = true;
    boolean bool1 = true;
    if (actionProvider != null && actionProvider.overridesItemVisibility()) {
      if ((this.mFlags & 0x8) != 0 || !this.mActionProvider.isVisible())
        bool1 = false; 
      return bool1;
    } 
    if ((this.mFlags & 0x8) == 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    return bool1;
  }
  
  public boolean requestsActionButton() {
    int i = this.mShowAsAction;
    boolean bool = true;
    if ((i & 0x1) != 1)
      bool = false; 
    return bool;
  }
  
  public boolean requiresActionButton() {
    boolean bool;
    if ((this.mShowAsAction & 0x2) == 2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean requiresOverflow() {
    boolean bool;
    if (!requiresActionButton() && !requestsActionButton()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public MenuItem setActionProvider(ActionProvider paramActionProvider) {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
  }
  
  public SupportMenuItem setActionView(int paramInt) {
    Context context = this.mMenu.getContext();
    setActionView(LayoutInflater.from(context).inflate(paramInt, (ViewGroup)new LinearLayout(context), false));
    return this;
  }
  
  public SupportMenuItem setActionView(View paramView) {
    this.mActionView = paramView;
    this.mActionProvider = null;
    if (paramView != null && paramView.getId() == -1) {
      int i = this.mId;
      if (i > 0)
        paramView.setId(i); 
    } 
    this.mMenu.onItemActionRequestChanged(this);
    return this;
  }
  
  public void setActionViewExpanded(boolean paramBoolean) {
    this.mIsActionViewExpanded = paramBoolean;
    this.mMenu.onItemsChanged(false);
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar) {
    if (this.mShortcutAlphabeticChar == paramChar)
      return (MenuItem)this; 
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar);
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar, int paramInt) {
    if (this.mShortcutAlphabeticChar == paramChar && this.mShortcutAlphabeticModifiers == paramInt)
      return (MenuItem)this; 
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar);
    this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(paramInt);
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setCallback(Runnable paramRunnable) {
    this.mItemCallback = paramRunnable;
    return (MenuItem)this;
  }
  
  public MenuItem setCheckable(boolean paramBoolean) {
    int i = this.mFlags;
    int j = paramBoolean | i & 0xFFFFFFFE;
    this.mFlags = j;
    if (i != j)
      this.mMenu.onItemsChanged(false); 
    return (MenuItem)this;
  }
  
  public MenuItem setChecked(boolean paramBoolean) {
    if ((this.mFlags & 0x4) != 0) {
      this.mMenu.setExclusiveItemChecked((MenuItem)this);
    } else {
      setCheckedInt(paramBoolean);
    } 
    return (MenuItem)this;
  }
  
  void setCheckedInt(boolean paramBoolean) {
    int i;
    int j = this.mFlags;
    if (paramBoolean) {
      i = 2;
    } else {
      i = 0;
    } 
    i |= j & 0xFFFFFFFD;
    this.mFlags = i;
    if (j != i)
      this.mMenu.onItemsChanged(false); 
  }
  
  public SupportMenuItem setContentDescription(CharSequence paramCharSequence) {
    this.mContentDescription = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setEnabled(boolean paramBoolean) {
    if (paramBoolean) {
      this.mFlags |= 0x10;
    } else {
      this.mFlags &= 0xFFFFFFEF;
    } 
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public void setExclusiveCheckable(boolean paramBoolean) {
    boolean bool;
    int i = this.mFlags;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mFlags = bool | i & 0xFFFFFFFB;
  }
  
  public MenuItem setIcon(int paramInt) {
    this.mIconDrawable = null;
    this.mIconResId = paramInt;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setIcon(Drawable paramDrawable) {
    this.mIconResId = 0;
    this.mIconDrawable = paramDrawable;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setIconTintList(ColorStateList paramColorStateList) {
    this.mIconTintList = paramColorStateList;
    this.mHasIconTint = true;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setIconTintMode(PorterDuff.Mode paramMode) {
    this.mIconTintMode = paramMode;
    this.mHasIconTintMode = true;
    this.mNeedToApplyIconTint = true;
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setIntent(Intent paramIntent) {
    this.mIntent = paramIntent;
    return (MenuItem)this;
  }
  
  public void setIsActionButton(boolean paramBoolean) {
    if (paramBoolean) {
      this.mFlags |= 0x20;
    } else {
      this.mFlags &= 0xFFFFFFDF;
    } 
  }
  
  void setMenuInfo(ContextMenu.ContextMenuInfo paramContextMenuInfo) {
    this.mMenuInfo = paramContextMenuInfo;
  }
  
  public MenuItem setNumericShortcut(char paramChar) {
    if (this.mShortcutNumericChar == paramChar)
      return (MenuItem)this; 
    this.mShortcutNumericChar = paramChar;
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setNumericShortcut(char paramChar, int paramInt) {
    if (this.mShortcutNumericChar == paramChar && this.mShortcutNumericModifiers == paramInt)
      return (MenuItem)this; 
    this.mShortcutNumericChar = paramChar;
    this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(paramInt);
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener) {
    this.mOnActionExpandListener = paramOnActionExpandListener;
    return (MenuItem)this;
  }
  
  public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener) {
    this.mClickListener = paramOnMenuItemClickListener;
    return (MenuItem)this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2) {
    this.mShortcutNumericChar = paramChar1;
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar2);
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2, int paramInt1, int paramInt2) {
    this.mShortcutNumericChar = paramChar1;
    this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(paramInt1);
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar2);
    this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(paramInt2);
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public void setShowAsAction(int paramInt) {
    int i = paramInt & 0x3;
    if (i == 0 || i == 1 || i == 2) {
      this.mShowAsAction = paramInt;
      this.mMenu.onItemActionRequestChanged(this);
      return;
    } 
    throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
  }
  
  public SupportMenuItem setShowAsActionFlags(int paramInt) {
    setShowAsAction(paramInt);
    return this;
  }
  
  public void setSubMenu(SubMenuBuilder paramSubMenuBuilder) {
    this.mSubMenu = paramSubMenuBuilder;
    paramSubMenuBuilder.setHeaderTitle(getTitle());
  }
  
  public SupportMenuItem setSupportActionProvider(ActionProvider paramActionProvider) {
    ActionProvider actionProvider = this.mActionProvider;
    if (actionProvider != null)
      actionProvider.reset(); 
    this.mActionView = null;
    this.mActionProvider = paramActionProvider;
    this.mMenu.onItemsChanged(true);
    paramActionProvider = this.mActionProvider;
    if (paramActionProvider != null)
      paramActionProvider.setVisibilityListener(new ActionProvider.VisibilityListener() {
            final MenuItemImpl this$0;
            
            public void onActionProviderVisibilityChanged(boolean param1Boolean) {
              MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this);
            }
          }); 
    return this;
  }
  
  public MenuItem setTitle(int paramInt) {
    return setTitle(this.mMenu.getContext().getString(paramInt));
  }
  
  public MenuItem setTitle(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    SubMenuBuilder subMenuBuilder = this.mSubMenu;
    if (subMenuBuilder != null)
      subMenuBuilder.setHeaderTitle(paramCharSequence); 
    return (MenuItem)this;
  }
  
  public MenuItem setTitleCondensed(CharSequence paramCharSequence) {
    this.mTitleCondensed = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    return (MenuItem)this;
  }
  
  public SupportMenuItem setTooltipText(CharSequence paramCharSequence) {
    this.mTooltipText = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public MenuItem setVisible(boolean paramBoolean) {
    if (setVisibleInt(paramBoolean))
      this.mMenu.onItemVisibleChanged(this); 
    return (MenuItem)this;
  }
  
  boolean setVisibleInt(boolean paramBoolean) {
    int i;
    int j = this.mFlags;
    boolean bool = false;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 8;
    } 
    i |= j & 0xFFFFFFF7;
    this.mFlags = i;
    paramBoolean = bool;
    if (j != i)
      paramBoolean = true; 
    return paramBoolean;
  }
  
  public boolean shouldShowIcon() {
    return this.mMenu.getOptionalIconsVisible();
  }
  
  boolean shouldShowShortcut() {
    boolean bool;
    if (this.mMenu.isShortcutsVisible() && getShortcut() != '\000') {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean showsTextAsAction() {
    boolean bool;
    if ((this.mShowAsAction & 0x4) == 4) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    CharSequence charSequence = this.mTitle;
    if (charSequence != null) {
      charSequence = charSequence.toString();
    } else {
      charSequence = null;
    } 
    return (String)charSequence;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\view\menu\MenuItemImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */