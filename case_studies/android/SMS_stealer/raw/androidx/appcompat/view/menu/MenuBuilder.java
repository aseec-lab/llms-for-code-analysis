package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ActionProvider;
import androidx.core.view.ViewConfigurationCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuBuilder implements SupportMenu {
  private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
  
  private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
  
  private static final String PRESENTER_KEY = "android:menu:presenters";
  
  private static final String TAG = "MenuBuilder";
  
  private static final int[] sCategoryToOrder = new int[] { 1, 4, 5, 3, 2, 0 };
  
  private ArrayList<MenuItemImpl> mActionItems;
  
  private Callback mCallback;
  
  private final Context mContext;
  
  private ContextMenu.ContextMenuInfo mCurrentMenuInfo;
  
  private int mDefaultShowAsAction = 0;
  
  private MenuItemImpl mExpandedItem;
  
  private boolean mGroupDividerEnabled = false;
  
  Drawable mHeaderIcon;
  
  CharSequence mHeaderTitle;
  
  View mHeaderView;
  
  private boolean mIsActionItemsStale;
  
  private boolean mIsClosing = false;
  
  private boolean mIsVisibleItemsStale;
  
  private ArrayList<MenuItemImpl> mItems;
  
  private boolean mItemsChangedWhileDispatchPrevented = false;
  
  private ArrayList<MenuItemImpl> mNonActionItems;
  
  private boolean mOptionalIconsVisible = false;
  
  private boolean mOverrideVisibleItems;
  
  private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList<WeakReference<MenuPresenter>>();
  
  private boolean mPreventDispatchingItemsChanged = false;
  
  private boolean mQwertyMode;
  
  private final Resources mResources;
  
  private boolean mShortcutsVisible;
  
  private boolean mStructureChangedWhileDispatchPrevented = false;
  
  private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList<MenuItemImpl>();
  
  private ArrayList<MenuItemImpl> mVisibleItems;
  
  public MenuBuilder(Context paramContext) {
    this.mContext = paramContext;
    this.mResources = paramContext.getResources();
    this.mItems = new ArrayList<MenuItemImpl>();
    this.mVisibleItems = new ArrayList<MenuItemImpl>();
    this.mIsVisibleItemsStale = true;
    this.mActionItems = new ArrayList<MenuItemImpl>();
    this.mNonActionItems = new ArrayList<MenuItemImpl>();
    this.mIsActionItemsStale = true;
    setShortcutsVisibleInner(true);
  }
  
  private MenuItemImpl createNewMenuItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence, int paramInt5) {
    return new MenuItemImpl(this, paramInt1, paramInt2, paramInt3, paramInt4, paramCharSequence, paramInt5);
  }
  
  private void dispatchPresenterUpdate(boolean paramBoolean) {
    if (this.mPresenters.isEmpty())
      return; 
    stopDispatchingItemsChanged();
    for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
      MenuPresenter menuPresenter = weakReference.get();
      if (menuPresenter == null) {
        this.mPresenters.remove(weakReference);
        continue;
      } 
      menuPresenter.updateMenuView(paramBoolean);
    } 
    startDispatchingItemsChanged();
  }
  
  private void dispatchRestoreInstanceState(Bundle paramBundle) {
    SparseArray sparseArray = paramBundle.getSparseParcelableArray("android:menu:presenters");
    if (sparseArray != null && !this.mPresenters.isEmpty())
      for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
        MenuPresenter menuPresenter = weakReference.get();
        if (menuPresenter == null) {
          this.mPresenters.remove(weakReference);
          continue;
        } 
        int i = menuPresenter.getId();
        if (i > 0) {
          Parcelable parcelable = (Parcelable)sparseArray.get(i);
          if (parcelable != null)
            menuPresenter.onRestoreInstanceState(parcelable); 
        } 
      }  
  }
  
  private void dispatchSaveInstanceState(Bundle paramBundle) {
    if (this.mPresenters.isEmpty())
      return; 
    SparseArray sparseArray = new SparseArray();
    for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
      MenuPresenter menuPresenter = weakReference.get();
      if (menuPresenter == null) {
        this.mPresenters.remove(weakReference);
        continue;
      } 
      int i = menuPresenter.getId();
      if (i > 0) {
        Parcelable parcelable = menuPresenter.onSaveInstanceState();
        if (parcelable != null)
          sparseArray.put(i, parcelable); 
      } 
    } 
    paramBundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
  }
  
  private boolean dispatchSubMenuSelected(SubMenuBuilder paramSubMenuBuilder, MenuPresenter paramMenuPresenter) {
    boolean bool2 = this.mPresenters.isEmpty();
    boolean bool1 = false;
    if (bool2)
      return false; 
    if (paramMenuPresenter != null)
      bool1 = paramMenuPresenter.onSubMenuSelected(paramSubMenuBuilder); 
    for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
      MenuPresenter menuPresenter = weakReference.get();
      if (menuPresenter == null) {
        this.mPresenters.remove(weakReference);
        continue;
      } 
      if (!bool1)
        bool1 = menuPresenter.onSubMenuSelected(paramSubMenuBuilder); 
    } 
    return bool1;
  }
  
  private static int findInsertIndex(ArrayList<MenuItemImpl> paramArrayList, int paramInt) {
    for (int i = paramArrayList.size() - 1; i >= 0; i--) {
      if (((MenuItemImpl)paramArrayList.get(i)).getOrdering() <= paramInt)
        return i + 1; 
    } 
    return 0;
  }
  
  private static int getOrdering(int paramInt) {
    int i = (0xFFFF0000 & paramInt) >> 16;
    if (i >= 0) {
      int[] arrayOfInt = sCategoryToOrder;
      if (i < arrayOfInt.length)
        return paramInt & 0xFFFF | arrayOfInt[i] << 16; 
    } 
    throw new IllegalArgumentException("order does not contain a valid category.");
  }
  
  private void removeItemAtInt(int paramInt, boolean paramBoolean) {
    if (paramInt >= 0 && paramInt < this.mItems.size()) {
      this.mItems.remove(paramInt);
      if (paramBoolean)
        onItemsChanged(true); 
    } 
  }
  
  private void setHeaderInternal(int paramInt1, CharSequence paramCharSequence, int paramInt2, Drawable paramDrawable, View paramView) {
    Resources resources = getResources();
    if (paramView != null) {
      this.mHeaderView = paramView;
      this.mHeaderTitle = null;
      this.mHeaderIcon = null;
    } else {
      if (paramInt1 > 0) {
        this.mHeaderTitle = resources.getText(paramInt1);
      } else if (paramCharSequence != null) {
        this.mHeaderTitle = paramCharSequence;
      } 
      if (paramInt2 > 0) {
        this.mHeaderIcon = ContextCompat.getDrawable(getContext(), paramInt2);
      } else if (paramDrawable != null) {
        this.mHeaderIcon = paramDrawable;
      } 
      this.mHeaderView = null;
    } 
    onItemsChanged(false);
  }
  
  private void setShortcutsVisibleInner(boolean paramBoolean) {
    boolean bool = true;
    if (paramBoolean && (this.mResources.getConfiguration()).keyboard != 1 && ViewConfigurationCompat.shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration.get(this.mContext), this.mContext)) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    } 
    this.mShortcutsVisible = paramBoolean;
  }
  
  public MenuItem add(int paramInt) {
    return addInternal(0, 0, 0, this.mResources.getString(paramInt));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return addInternal(paramInt1, paramInt2, paramInt3, this.mResources.getString(paramInt4));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    return addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
  }
  
  public MenuItem add(CharSequence paramCharSequence) {
    return addInternal(0, 0, 0, paramCharSequence);
  }
  
  public int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem) {
    byte b1;
    PackageManager packageManager = this.mContext.getPackageManager();
    byte b3 = 0;
    List<ResolveInfo> list = packageManager.queryIntentActivityOptions(paramComponentName, paramArrayOfIntent, paramIntent, 0);
    if (list != null) {
      b1 = list.size();
    } else {
      b1 = 0;
    } 
    byte b2 = b3;
    if ((paramInt4 & 0x1) == 0) {
      removeGroup(paramInt1);
      b2 = b3;
    } 
    while (b2 < b1) {
      ResolveInfo resolveInfo = list.get(b2);
      if (resolveInfo.specificIndex < 0) {
        intent = paramIntent;
      } else {
        intent = paramArrayOfIntent[resolveInfo.specificIndex];
      } 
      Intent intent = new Intent(intent);
      intent.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
      MenuItem menuItem = add(paramInt1, paramInt2, paramInt3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent);
      if (paramArrayOfMenuItem != null && resolveInfo.specificIndex >= 0)
        paramArrayOfMenuItem[resolveInfo.specificIndex] = menuItem; 
      b2++;
    } 
    return b1;
  }
  
  protected MenuItem addInternal(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    int i = getOrdering(paramInt3);
    MenuItemImpl menuItemImpl = createNewMenuItem(paramInt1, paramInt2, paramInt3, i, paramCharSequence, this.mDefaultShowAsAction);
    ContextMenu.ContextMenuInfo contextMenuInfo = this.mCurrentMenuInfo;
    if (contextMenuInfo != null)
      menuItemImpl.setMenuInfo(contextMenuInfo); 
    ArrayList<MenuItemImpl> arrayList = this.mItems;
    arrayList.add(findInsertIndex(arrayList, i), menuItemImpl);
    onItemsChanged(true);
    return (MenuItem)menuItemImpl;
  }
  
  public void addMenuPresenter(MenuPresenter paramMenuPresenter) {
    addMenuPresenter(paramMenuPresenter, this.mContext);
  }
  
  public void addMenuPresenter(MenuPresenter paramMenuPresenter, Context paramContext) {
    this.mPresenters.add(new WeakReference<MenuPresenter>(paramMenuPresenter));
    paramMenuPresenter.initForMenu(paramContext, this);
    this.mIsActionItemsStale = true;
  }
  
  public SubMenu addSubMenu(int paramInt) {
    return addSubMenu(0, 0, 0, this.mResources.getString(paramInt));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return addSubMenu(paramInt1, paramInt2, paramInt3, this.mResources.getString(paramInt4));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    MenuItemImpl menuItemImpl = (MenuItemImpl)addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
    SubMenuBuilder subMenuBuilder = new SubMenuBuilder(this.mContext, this, menuItemImpl);
    menuItemImpl.setSubMenu(subMenuBuilder);
    return subMenuBuilder;
  }
  
  public SubMenu addSubMenu(CharSequence paramCharSequence) {
    return addSubMenu(0, 0, 0, paramCharSequence);
  }
  
  public void changeMenuMode() {
    Callback callback = this.mCallback;
    if (callback != null)
      callback.onMenuModeChange(this); 
  }
  
  public void clear() {
    MenuItemImpl menuItemImpl = this.mExpandedItem;
    if (menuItemImpl != null)
      collapseItemActionView(menuItemImpl); 
    this.mItems.clear();
    onItemsChanged(true);
  }
  
  public void clearAll() {
    this.mPreventDispatchingItemsChanged = true;
    clear();
    clearHeader();
    this.mPresenters.clear();
    this.mPreventDispatchingItemsChanged = false;
    this.mItemsChangedWhileDispatchPrevented = false;
    this.mStructureChangedWhileDispatchPrevented = false;
    onItemsChanged(true);
  }
  
  public void clearHeader() {
    this.mHeaderIcon = null;
    this.mHeaderTitle = null;
    this.mHeaderView = null;
    onItemsChanged(false);
  }
  
  public void close() {
    close(true);
  }
  
  public final void close(boolean paramBoolean) {
    if (this.mIsClosing)
      return; 
    this.mIsClosing = true;
    for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
      MenuPresenter menuPresenter = weakReference.get();
      if (menuPresenter == null) {
        this.mPresenters.remove(weakReference);
        continue;
      } 
      menuPresenter.onCloseMenu(this, paramBoolean);
    } 
    this.mIsClosing = false;
  }
  
  public boolean collapseItemActionView(MenuItemImpl paramMenuItemImpl) {
    boolean bool4 = this.mPresenters.isEmpty();
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool1 = bool2;
    if (!bool4)
      if (this.mExpandedItem != paramMenuItemImpl) {
        bool1 = bool2;
      } else {
        stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> iterator = this.mPresenters.iterator();
        bool1 = bool3;
        while (true) {
          bool2 = bool1;
          if (iterator.hasNext()) {
            WeakReference<MenuPresenter> weakReference = iterator.next();
            MenuPresenter menuPresenter = weakReference.get();
            if (menuPresenter == null) {
              this.mPresenters.remove(weakReference);
              continue;
            } 
            bool2 = menuPresenter.collapseItemActionView(this, paramMenuItemImpl);
            bool1 = bool2;
            if (bool2)
              break; 
            continue;
          } 
          break;
        } 
        startDispatchingItemsChanged();
        bool1 = bool2;
        if (bool2) {
          this.mExpandedItem = null;
          bool1 = bool2;
        } 
      }  
    return bool1;
  }
  
  boolean dispatchMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem) {
    boolean bool;
    Callback callback = this.mCallback;
    if (callback != null && callback.onMenuItemSelected(paramMenuBuilder, paramMenuItem)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean expandItemActionView(MenuItemImpl paramMenuItemImpl) {
    boolean bool2 = this.mPresenters.isEmpty();
    boolean bool1 = false;
    if (bool2)
      return false; 
    stopDispatchingItemsChanged();
    Iterator<WeakReference<MenuPresenter>> iterator = this.mPresenters.iterator();
    while (true) {
      bool2 = bool1;
      if (iterator.hasNext()) {
        WeakReference<MenuPresenter> weakReference = iterator.next();
        MenuPresenter menuPresenter = weakReference.get();
        if (menuPresenter == null) {
          this.mPresenters.remove(weakReference);
          continue;
        } 
        bool2 = menuPresenter.expandItemActionView(this, paramMenuItemImpl);
        bool1 = bool2;
        if (bool2)
          break; 
        continue;
      } 
      break;
    } 
    startDispatchingItemsChanged();
    if (bool2)
      this.mExpandedItem = paramMenuItemImpl; 
    return bool2;
  }
  
  public int findGroupIndex(int paramInt) {
    return findGroupIndex(paramInt, 0);
  }
  
  public int findGroupIndex(int paramInt1, int paramInt2) {
    int j = size();
    int i = paramInt2;
    if (paramInt2 < 0)
      i = 0; 
    while (i < j) {
      if (((MenuItemImpl)this.mItems.get(i)).getGroupId() == paramInt1)
        return i; 
      i++;
    } 
    return -1;
  }
  
  public MenuItem findItem(int paramInt) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      MenuItemImpl menuItemImpl = this.mItems.get(b);
      if (menuItemImpl.getItemId() == paramInt)
        return (MenuItem)menuItemImpl; 
      if (menuItemImpl.hasSubMenu()) {
        MenuItem menuItem = menuItemImpl.getSubMenu().findItem(paramInt);
        if (menuItem != null)
          return menuItem; 
      } 
    } 
    return null;
  }
  
  public int findItemIndex(int paramInt) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      if (((MenuItemImpl)this.mItems.get(b)).getItemId() == paramInt)
        return b; 
    } 
    return -1;
  }
  
  MenuItemImpl findItemWithShortcutForKey(int paramInt, KeyEvent paramKeyEvent) {
    ArrayList<MenuItemImpl> arrayList = this.mTempShortcutItemList;
    arrayList.clear();
    findItemsWithShortcutForKey(arrayList, paramInt, paramKeyEvent);
    if (arrayList.isEmpty())
      return null; 
    int j = paramKeyEvent.getMetaState();
    KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
    paramKeyEvent.getKeyData(keyData);
    int i = arrayList.size();
    if (i == 1)
      return arrayList.get(0); 
    boolean bool = isQwertyMode();
    for (byte b = 0; b < i; b++) {
      char c;
      MenuItemImpl menuItemImpl = arrayList.get(b);
      if (bool) {
        c = menuItemImpl.getAlphabeticShortcut();
      } else {
        c = menuItemImpl.getNumericShortcut();
      } 
      if ((c == keyData.meta[0] && (j & 0x2) == 0) || (c == keyData.meta[2] && (j & 0x2) != 0) || (bool && c == '\b' && paramInt == 67))
        return menuItemImpl; 
    } 
    return null;
  }
  
  void findItemsWithShortcutForKey(List<MenuItemImpl> paramList, int paramInt, KeyEvent paramKeyEvent) {
    boolean bool = isQwertyMode();
    int i = paramKeyEvent.getModifiers();
    KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
    if (!paramKeyEvent.getKeyData(keyData) && paramInt != 67)
      return; 
    int j = this.mItems.size();
    for (byte b = 0; b < j; b++) {
      char c;
      int k;
      MenuItemImpl menuItemImpl = this.mItems.get(b);
      if (menuItemImpl.hasSubMenu())
        ((MenuBuilder)menuItemImpl.getSubMenu()).findItemsWithShortcutForKey(paramList, paramInt, paramKeyEvent); 
      if (bool) {
        c = menuItemImpl.getAlphabeticShortcut();
      } else {
        c = menuItemImpl.getNumericShortcut();
      } 
      if (bool) {
        k = menuItemImpl.getAlphabeticModifiers();
      } else {
        k = menuItemImpl.getNumericModifiers();
      } 
      if ((i & 0x1100F) == (k & 0x1100F)) {
        k = 1;
      } else {
        k = 0;
      } 
      if (k != 0 && c != '\000' && (c == keyData.meta[0] || c == keyData.meta[2] || (bool && c == '\b' && paramInt == 67)) && menuItemImpl.isEnabled())
        paramList.add(menuItemImpl); 
    } 
  }
  
  public void flagActionItems() {
    ArrayList<MenuItemImpl> arrayList = getVisibleItems();
    if (!this.mIsActionItemsStale)
      return; 
    Iterator<WeakReference<MenuPresenter>> iterator = this.mPresenters.iterator();
    boolean bool;
    for (bool = false; iterator.hasNext(); bool |= menuPresenter.flagActionItems()) {
      WeakReference<MenuPresenter> weakReference = iterator.next();
      MenuPresenter menuPresenter = weakReference.get();
      if (menuPresenter == null) {
        this.mPresenters.remove(weakReference);
        continue;
      } 
    } 
    if (bool) {
      this.mActionItems.clear();
      this.mNonActionItems.clear();
      int i = arrayList.size();
      for (bool = false; bool < i; bool++) {
        MenuItemImpl menuItemImpl = arrayList.get(bool);
        if (menuItemImpl.isActionButton()) {
          this.mActionItems.add(menuItemImpl);
        } else {
          this.mNonActionItems.add(menuItemImpl);
        } 
      } 
    } else {
      this.mActionItems.clear();
      this.mNonActionItems.clear();
      this.mNonActionItems.addAll(getVisibleItems());
    } 
    this.mIsActionItemsStale = false;
  }
  
  public ArrayList<MenuItemImpl> getActionItems() {
    flagActionItems();
    return this.mActionItems;
  }
  
  protected String getActionViewStatesKey() {
    return "android:menu:actionviewstates";
  }
  
  public Context getContext() {
    return this.mContext;
  }
  
  public MenuItemImpl getExpandedItem() {
    return this.mExpandedItem;
  }
  
  public Drawable getHeaderIcon() {
    return this.mHeaderIcon;
  }
  
  public CharSequence getHeaderTitle() {
    return this.mHeaderTitle;
  }
  
  public View getHeaderView() {
    return this.mHeaderView;
  }
  
  public MenuItem getItem(int paramInt) {
    return (MenuItem)this.mItems.get(paramInt);
  }
  
  public ArrayList<MenuItemImpl> getNonActionItems() {
    flagActionItems();
    return this.mNonActionItems;
  }
  
  boolean getOptionalIconsVisible() {
    return this.mOptionalIconsVisible;
  }
  
  Resources getResources() {
    return this.mResources;
  }
  
  public MenuBuilder getRootMenu() {
    return this;
  }
  
  public ArrayList<MenuItemImpl> getVisibleItems() {
    if (!this.mIsVisibleItemsStale)
      return this.mVisibleItems; 
    this.mVisibleItems.clear();
    int i = this.mItems.size();
    for (byte b = 0; b < i; b++) {
      MenuItemImpl menuItemImpl = this.mItems.get(b);
      if (menuItemImpl.isVisible())
        this.mVisibleItems.add(menuItemImpl); 
    } 
    this.mIsVisibleItemsStale = false;
    this.mIsActionItemsStale = true;
    return this.mVisibleItems;
  }
  
  public boolean hasVisibleItems() {
    if (this.mOverrideVisibleItems)
      return true; 
    int i = size();
    for (byte b = 0; b < i; b++) {
      if (((MenuItemImpl)this.mItems.get(b)).isVisible())
        return true; 
    } 
    return false;
  }
  
  public boolean isGroupDividerEnabled() {
    return this.mGroupDividerEnabled;
  }
  
  boolean isQwertyMode() {
    return this.mQwertyMode;
  }
  
  public boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent) {
    boolean bool;
    if (findItemWithShortcutForKey(paramInt, paramKeyEvent) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isShortcutsVisible() {
    return this.mShortcutsVisible;
  }
  
  void onItemActionRequestChanged(MenuItemImpl paramMenuItemImpl) {
    this.mIsActionItemsStale = true;
    onItemsChanged(true);
  }
  
  void onItemVisibleChanged(MenuItemImpl paramMenuItemImpl) {
    this.mIsVisibleItemsStale = true;
    onItemsChanged(true);
  }
  
  public void onItemsChanged(boolean paramBoolean) {
    if (!this.mPreventDispatchingItemsChanged) {
      if (paramBoolean) {
        this.mIsVisibleItemsStale = true;
        this.mIsActionItemsStale = true;
      } 
      dispatchPresenterUpdate(paramBoolean);
    } else {
      this.mItemsChangedWhileDispatchPrevented = true;
      if (paramBoolean)
        this.mStructureChangedWhileDispatchPrevented = true; 
    } 
  }
  
  public boolean performIdentifierAction(int paramInt1, int paramInt2) {
    return performItemAction(findItem(paramInt1), paramInt2);
  }
  
  public boolean performItemAction(MenuItem paramMenuItem, int paramInt) {
    return performItemAction(paramMenuItem, null, paramInt);
  }
  
  public boolean performItemAction(MenuItem paramMenuItem, MenuPresenter paramMenuPresenter, int paramInt) {
    boolean bool;
    boolean bool1;
    MenuItemImpl menuItemImpl = (MenuItemImpl)paramMenuItem;
    if (menuItemImpl == null || !menuItemImpl.isEnabled())
      return false; 
    boolean bool2 = menuItemImpl.invoke();
    ActionProvider actionProvider = menuItemImpl.getSupportActionProvider();
    if (actionProvider != null && actionProvider.hasSubMenu()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (menuItemImpl.hasCollapsibleActionView()) {
      bool2 |= menuItemImpl.expandActionView();
      bool1 = bool2;
      if (bool2) {
        close(true);
        bool1 = bool2;
      } 
    } else {
      if (menuItemImpl.hasSubMenu() || bool) {
        if ((paramInt & 0x4) == 0)
          close(false); 
        if (!menuItemImpl.hasSubMenu())
          menuItemImpl.setSubMenu(new SubMenuBuilder(getContext(), this, menuItemImpl)); 
        SubMenuBuilder subMenuBuilder = (SubMenuBuilder)menuItemImpl.getSubMenu();
        if (bool)
          actionProvider.onPrepareSubMenu(subMenuBuilder); 
        bool2 |= dispatchSubMenuSelected(subMenuBuilder, paramMenuPresenter);
        boolean bool3 = bool2;
        if (!bool2) {
          close(true);
          bool3 = bool2;
        } 
        return bool3;
      } 
      bool1 = bool2;
      if ((paramInt & 0x1) == 0) {
        close(true);
        bool1 = bool2;
      } 
    } 
    return bool1;
  }
  
  public boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2) {
    boolean bool;
    MenuItemImpl menuItemImpl = findItemWithShortcutForKey(paramInt1, paramKeyEvent);
    if (menuItemImpl != null) {
      bool = performItemAction((MenuItem)menuItemImpl, paramInt2);
    } else {
      bool = false;
    } 
    if ((paramInt2 & 0x2) != 0)
      close(true); 
    return bool;
  }
  
  public void removeGroup(int paramInt) {
    int i = findGroupIndex(paramInt);
    if (i >= 0) {
      int j = this.mItems.size();
      for (byte b = 0; b < j - i && ((MenuItemImpl)this.mItems.get(i)).getGroupId() == paramInt; b++)
        removeItemAtInt(i, false); 
      onItemsChanged(true);
    } 
  }
  
  public void removeItem(int paramInt) {
    removeItemAtInt(findItemIndex(paramInt), true);
  }
  
  public void removeItemAt(int paramInt) {
    removeItemAtInt(paramInt, true);
  }
  
  public void removeMenuPresenter(MenuPresenter paramMenuPresenter) {
    for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
      MenuPresenter menuPresenter = weakReference.get();
      if (menuPresenter == null || menuPresenter == paramMenuPresenter)
        this.mPresenters.remove(weakReference); 
    } 
  }
  
  public void restoreActionViewStates(Bundle paramBundle) {
    if (paramBundle == null)
      return; 
    SparseArray sparseArray = paramBundle.getSparseParcelableArray(getActionViewStatesKey());
    int j = size();
    int i;
    for (i = 0; i < j; i++) {
      MenuItem menuItem = getItem(i);
      View view = menuItem.getActionView();
      if (view != null && view.getId() != -1)
        view.restoreHierarchyState(sparseArray); 
      if (menuItem.hasSubMenu())
        ((SubMenuBuilder)menuItem.getSubMenu()).restoreActionViewStates(paramBundle); 
    } 
    i = paramBundle.getInt("android:menu:expandedactionview");
    if (i > 0) {
      MenuItem menuItem = findItem(i);
      if (menuItem != null)
        menuItem.expandActionView(); 
    } 
  }
  
  public void restorePresenterStates(Bundle paramBundle) {
    dispatchRestoreInstanceState(paramBundle);
  }
  
  public void saveActionViewStates(Bundle paramBundle) {
    int i = size();
    SparseArray sparseArray = null;
    byte b = 0;
    while (b < i) {
      MenuItem menuItem = getItem(b);
      View view = menuItem.getActionView();
      SparseArray sparseArray1 = sparseArray;
      if (view != null) {
        sparseArray1 = sparseArray;
        if (view.getId() != -1) {
          SparseArray sparseArray2 = sparseArray;
          if (sparseArray == null)
            sparseArray2 = new SparseArray(); 
          view.saveHierarchyState(sparseArray2);
          sparseArray1 = sparseArray2;
          if (menuItem.isActionViewExpanded()) {
            paramBundle.putInt("android:menu:expandedactionview", menuItem.getItemId());
            sparseArray1 = sparseArray2;
          } 
        } 
      } 
      if (menuItem.hasSubMenu())
        ((SubMenuBuilder)menuItem.getSubMenu()).saveActionViewStates(paramBundle); 
      b++;
      sparseArray = sparseArray1;
    } 
    if (sparseArray != null)
      paramBundle.putSparseParcelableArray(getActionViewStatesKey(), sparseArray); 
  }
  
  public void savePresenterStates(Bundle paramBundle) {
    dispatchSaveInstanceState(paramBundle);
  }
  
  public void setCallback(Callback paramCallback) {
    this.mCallback = paramCallback;
  }
  
  public void setCurrentMenuInfo(ContextMenu.ContextMenuInfo paramContextMenuInfo) {
    this.mCurrentMenuInfo = paramContextMenuInfo;
  }
  
  public MenuBuilder setDefaultShowAsAction(int paramInt) {
    this.mDefaultShowAsAction = paramInt;
    return this;
  }
  
  void setExclusiveItemChecked(MenuItem paramMenuItem) {
    int i = paramMenuItem.getGroupId();
    int j = this.mItems.size();
    stopDispatchingItemsChanged();
    for (byte b = 0; b < j; b++) {
      MenuItemImpl menuItemImpl = this.mItems.get(b);
      if (menuItemImpl.getGroupId() == i && menuItemImpl.isExclusiveCheckable() && menuItemImpl.isCheckable()) {
        boolean bool;
        if (menuItemImpl == paramMenuItem) {
          bool = true;
        } else {
          bool = false;
        } 
        menuItemImpl.setCheckedInt(bool);
      } 
    } 
    startDispatchingItemsChanged();
  }
  
  public void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    int i = this.mItems.size();
    for (byte b = 0; b < i; b++) {
      MenuItemImpl menuItemImpl = this.mItems.get(b);
      if (menuItemImpl.getGroupId() == paramInt) {
        menuItemImpl.setExclusiveCheckable(paramBoolean2);
        menuItemImpl.setCheckable(paramBoolean1);
      } 
    } 
  }
  
  public void setGroupDividerEnabled(boolean paramBoolean) {
    this.mGroupDividerEnabled = paramBoolean;
  }
  
  public void setGroupEnabled(int paramInt, boolean paramBoolean) {
    int i = this.mItems.size();
    for (byte b = 0; b < i; b++) {
      MenuItemImpl menuItemImpl = this.mItems.get(b);
      if (menuItemImpl.getGroupId() == paramInt)
        menuItemImpl.setEnabled(paramBoolean); 
    } 
  }
  
  public void setGroupVisible(int paramInt, boolean paramBoolean) {
    int i = this.mItems.size();
    byte b = 0;
    boolean bool;
    for (bool = false; b < i; bool = bool1) {
      MenuItemImpl menuItemImpl = this.mItems.get(b);
      boolean bool1 = bool;
      if (menuItemImpl.getGroupId() == paramInt) {
        bool1 = bool;
        if (menuItemImpl.setVisibleInt(paramBoolean))
          bool1 = true; 
      } 
      b++;
    } 
    if (bool)
      onItemsChanged(true); 
  }
  
  protected MenuBuilder setHeaderIconInt(int paramInt) {
    setHeaderInternal(0, null, paramInt, null, null);
    return this;
  }
  
  protected MenuBuilder setHeaderIconInt(Drawable paramDrawable) {
    setHeaderInternal(0, null, 0, paramDrawable, null);
    return this;
  }
  
  protected MenuBuilder setHeaderTitleInt(int paramInt) {
    setHeaderInternal(paramInt, null, 0, null, null);
    return this;
  }
  
  protected MenuBuilder setHeaderTitleInt(CharSequence paramCharSequence) {
    setHeaderInternal(0, paramCharSequence, 0, null, null);
    return this;
  }
  
  protected MenuBuilder setHeaderViewInt(View paramView) {
    setHeaderInternal(0, null, 0, null, paramView);
    return this;
  }
  
  public void setOptionalIconsVisible(boolean paramBoolean) {
    this.mOptionalIconsVisible = paramBoolean;
  }
  
  public void setOverrideVisibleItems(boolean paramBoolean) {
    this.mOverrideVisibleItems = paramBoolean;
  }
  
  public void setQwertyMode(boolean paramBoolean) {
    this.mQwertyMode = paramBoolean;
    onItemsChanged(false);
  }
  
  public void setShortcutsVisible(boolean paramBoolean) {
    if (this.mShortcutsVisible == paramBoolean)
      return; 
    setShortcutsVisibleInner(paramBoolean);
    onItemsChanged(false);
  }
  
  public int size() {
    return this.mItems.size();
  }
  
  public void startDispatchingItemsChanged() {
    this.mPreventDispatchingItemsChanged = false;
    if (this.mItemsChangedWhileDispatchPrevented) {
      this.mItemsChangedWhileDispatchPrevented = false;
      onItemsChanged(this.mStructureChangedWhileDispatchPrevented);
    } 
  }
  
  public void stopDispatchingItemsChanged() {
    if (!this.mPreventDispatchingItemsChanged) {
      this.mPreventDispatchingItemsChanged = true;
      this.mItemsChangedWhileDispatchPrevented = false;
      this.mStructureChangedWhileDispatchPrevented = false;
    } 
  }
  
  public static interface Callback {
    boolean onMenuItemSelected(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem);
    
    void onMenuModeChange(MenuBuilder param1MenuBuilder);
  }
  
  public static interface ItemInvoker {
    boolean invokeItem(MenuItemImpl param1MenuItemImpl);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\view\menu\MenuBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */