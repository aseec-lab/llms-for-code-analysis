package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ActionProvider;
import java.util.ArrayList;

class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
  private static final String TAG = "ActionMenuPresenter";
  
  private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
  
  ActionButtonSubmenu mActionButtonPopup;
  
  private int mActionItemWidthLimit;
  
  private boolean mExpandedActionViewsExclusive;
  
  private int mMaxItems;
  
  private boolean mMaxItemsSet;
  
  private int mMinCellSize;
  
  int mOpenSubMenuId;
  
  OverflowMenuButton mOverflowButton;
  
  OverflowPopup mOverflowPopup;
  
  private Drawable mPendingOverflowIcon;
  
  private boolean mPendingOverflowIconSet;
  
  private ActionMenuPopupCallback mPopupCallback;
  
  final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
  
  OpenOverflowRunnable mPostedOpenRunnable;
  
  private boolean mReserveOverflow;
  
  private boolean mReserveOverflowSet;
  
  private boolean mStrictWidthLimit;
  
  private int mWidthLimit;
  
  private boolean mWidthLimitSet;
  
  public ActionMenuPresenter(Context paramContext) {
    super(paramContext, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
  }
  
  private View findViewForItem(MenuItem paramMenuItem) {
    ViewGroup viewGroup = (ViewGroup)this.mMenuView;
    if (viewGroup == null)
      return null; 
    int i = viewGroup.getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = viewGroup.getChildAt(b);
      if (view instanceof MenuView.ItemView && ((MenuView.ItemView)view).getItemData() == paramMenuItem)
        return view; 
    } 
    return null;
  }
  
  public void bindItemView(MenuItemImpl paramMenuItemImpl, MenuView.ItemView paramItemView) {
    paramItemView.initialize(paramMenuItemImpl, 0);
    ActionMenuView actionMenuView = (ActionMenuView)this.mMenuView;
    ActionMenuItemView actionMenuItemView = (ActionMenuItemView)paramItemView;
    actionMenuItemView.setItemInvoker(actionMenuView);
    if (this.mPopupCallback == null)
      this.mPopupCallback = new ActionMenuPopupCallback(); 
    actionMenuItemView.setPopupCallback(this.mPopupCallback);
  }
  
  public boolean dismissPopupMenus() {
    return hideOverflowMenu() | hideSubMenus();
  }
  
  public boolean filterLeftoverView(ViewGroup paramViewGroup, int paramInt) {
    return (paramViewGroup.getChildAt(paramInt) == this.mOverflowButton) ? false : super.filterLeftoverView(paramViewGroup, paramInt);
  }
  
  public boolean flagActionItems() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mMenu : Landroidx/appcompat/view/menu/MenuBuilder;
    //   4: ifnull -> 26
    //   7: aload_0
    //   8: getfield mMenu : Landroidx/appcompat/view/menu/MenuBuilder;
    //   11: invokevirtual getVisibleItems : ()Ljava/util/ArrayList;
    //   14: astore #17
    //   16: aload #17
    //   18: invokevirtual size : ()I
    //   21: istore #4
    //   23: goto -> 32
    //   26: aconst_null
    //   27: astore #17
    //   29: iconst_0
    //   30: istore #4
    //   32: aload_0
    //   33: getfield mMaxItems : I
    //   36: istore_1
    //   37: aload_0
    //   38: getfield mActionItemWidthLimit : I
    //   41: istore #10
    //   43: iconst_0
    //   44: iconst_0
    //   45: invokestatic makeMeasureSpec : (II)I
    //   48: istore #11
    //   50: aload_0
    //   51: getfield mMenuView : Landroidx/appcompat/view/menu/MenuView;
    //   54: checkcast android/view/ViewGroup
    //   57: astore #18
    //   59: iconst_0
    //   60: istore #5
    //   62: iconst_0
    //   63: istore #6
    //   65: iconst_0
    //   66: istore_2
    //   67: iconst_0
    //   68: istore_3
    //   69: iload #5
    //   71: iload #4
    //   73: if_icmpge -> 152
    //   76: aload #17
    //   78: iload #5
    //   80: invokevirtual get : (I)Ljava/lang/Object;
    //   83: checkcast androidx/appcompat/view/menu/MenuItemImpl
    //   86: astore #19
    //   88: aload #19
    //   90: invokevirtual requiresActionButton : ()Z
    //   93: ifeq -> 102
    //   96: iinc #2, 1
    //   99: goto -> 119
    //   102: aload #19
    //   104: invokevirtual requestsActionButton : ()Z
    //   107: ifeq -> 116
    //   110: iinc #3, 1
    //   113: goto -> 119
    //   116: iconst_1
    //   117: istore #6
    //   119: iload_1
    //   120: istore #7
    //   122: aload_0
    //   123: getfield mExpandedActionViewsExclusive : Z
    //   126: ifeq -> 143
    //   129: iload_1
    //   130: istore #7
    //   132: aload #19
    //   134: invokevirtual isActionViewExpanded : ()Z
    //   137: ifeq -> 143
    //   140: iconst_0
    //   141: istore #7
    //   143: iinc #5, 1
    //   146: iload #7
    //   148: istore_1
    //   149: goto -> 69
    //   152: iload_1
    //   153: istore #5
    //   155: aload_0
    //   156: getfield mReserveOverflow : Z
    //   159: ifeq -> 182
    //   162: iload #6
    //   164: ifne -> 177
    //   167: iload_1
    //   168: istore #5
    //   170: iload_3
    //   171: iload_2
    //   172: iadd
    //   173: iload_1
    //   174: if_icmple -> 182
    //   177: iload_1
    //   178: iconst_1
    //   179: isub
    //   180: istore #5
    //   182: iload #5
    //   184: iload_2
    //   185: isub
    //   186: istore_1
    //   187: aload_0
    //   188: getfield mActionButtonGroups : Landroid/util/SparseBooleanArray;
    //   191: astore #19
    //   193: aload #19
    //   195: invokevirtual clear : ()V
    //   198: aload_0
    //   199: getfield mStrictWidthLimit : Z
    //   202: ifeq -> 228
    //   205: aload_0
    //   206: getfield mMinCellSize : I
    //   209: istore_2
    //   210: iload #10
    //   212: iload_2
    //   213: idiv
    //   214: istore_3
    //   215: iload_2
    //   216: iload #10
    //   218: iload_2
    //   219: irem
    //   220: iload_3
    //   221: idiv
    //   222: iadd
    //   223: istore #8
    //   225: goto -> 233
    //   228: iconst_0
    //   229: istore #8
    //   231: iconst_0
    //   232: istore_3
    //   233: iconst_0
    //   234: istore #9
    //   236: iconst_0
    //   237: istore_2
    //   238: iload #10
    //   240: istore #6
    //   242: iload #4
    //   244: istore #10
    //   246: iload #9
    //   248: iload #10
    //   250: if_icmpge -> 718
    //   253: aload #17
    //   255: iload #9
    //   257: invokevirtual get : (I)Ljava/lang/Object;
    //   260: checkcast androidx/appcompat/view/menu/MenuItemImpl
    //   263: astore #20
    //   265: aload #20
    //   267: invokevirtual requiresActionButton : ()Z
    //   270: ifeq -> 375
    //   273: aload_0
    //   274: aload #20
    //   276: aconst_null
    //   277: aload #18
    //   279: invokevirtual getItemView : (Landroidx/appcompat/view/menu/MenuItemImpl;Landroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    //   282: astore #21
    //   284: aload_0
    //   285: getfield mStrictWidthLimit : Z
    //   288: ifeq -> 308
    //   291: iload_3
    //   292: aload #21
    //   294: iload #8
    //   296: iload_3
    //   297: iload #11
    //   299: iconst_0
    //   300: invokestatic measureChildForCells : (Landroid/view/View;IIII)I
    //   303: isub
    //   304: istore_3
    //   305: goto -> 317
    //   308: aload #21
    //   310: iload #11
    //   312: iload #11
    //   314: invokevirtual measure : (II)V
    //   317: aload #21
    //   319: invokevirtual getMeasuredWidth : ()I
    //   322: istore #5
    //   324: iload #6
    //   326: iload #5
    //   328: isub
    //   329: istore #7
    //   331: iload_2
    //   332: istore #4
    //   334: iload_2
    //   335: ifne -> 342
    //   338: iload #5
    //   340: istore #4
    //   342: aload #20
    //   344: invokevirtual getGroupId : ()I
    //   347: istore_2
    //   348: iload_2
    //   349: ifeq -> 359
    //   352: aload #19
    //   354: iload_2
    //   355: iconst_1
    //   356: invokevirtual put : (IZ)V
    //   359: aload #20
    //   361: iconst_1
    //   362: invokevirtual setIsActionButton : (Z)V
    //   365: iload #7
    //   367: istore #6
    //   369: iload #4
    //   371: istore_2
    //   372: goto -> 712
    //   375: aload #20
    //   377: invokevirtual requestsActionButton : ()Z
    //   380: ifeq -> 706
    //   383: aload #20
    //   385: invokevirtual getGroupId : ()I
    //   388: istore #12
    //   390: aload #19
    //   392: iload #12
    //   394: invokevirtual get : (I)Z
    //   397: istore #16
    //   399: iload_1
    //   400: ifgt -> 408
    //   403: iload #16
    //   405: ifeq -> 430
    //   408: iload #6
    //   410: ifle -> 430
    //   413: aload_0
    //   414: getfield mStrictWidthLimit : Z
    //   417: ifeq -> 424
    //   420: iload_3
    //   421: ifle -> 430
    //   424: iconst_1
    //   425: istore #13
    //   427: goto -> 433
    //   430: iconst_0
    //   431: istore #13
    //   433: iload #13
    //   435: istore #15
    //   437: iload #13
    //   439: istore #14
    //   441: iload #6
    //   443: istore #7
    //   445: iload_3
    //   446: istore #5
    //   448: iload_2
    //   449: istore #4
    //   451: iload #13
    //   453: ifeq -> 583
    //   456: aload_0
    //   457: aload #20
    //   459: aconst_null
    //   460: aload #18
    //   462: invokevirtual getItemView : (Landroidx/appcompat/view/menu/MenuItemImpl;Landroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    //   465: astore #21
    //   467: aload_0
    //   468: getfield mStrictWidthLimit : Z
    //   471: ifeq -> 510
    //   474: aload #21
    //   476: iload #8
    //   478: iload_3
    //   479: iload #11
    //   481: iconst_0
    //   482: invokestatic measureChildForCells : (Landroid/view/View;IIII)I
    //   485: istore #5
    //   487: iload_3
    //   488: iload #5
    //   490: isub
    //   491: istore #4
    //   493: iload #4
    //   495: istore_3
    //   496: iload #5
    //   498: ifne -> 519
    //   501: iconst_0
    //   502: istore #15
    //   504: iload #4
    //   506: istore_3
    //   507: goto -> 519
    //   510: aload #21
    //   512: iload #11
    //   514: iload #11
    //   516: invokevirtual measure : (II)V
    //   519: aload #21
    //   521: invokevirtual getMeasuredWidth : ()I
    //   524: istore #5
    //   526: iload #6
    //   528: iload #5
    //   530: isub
    //   531: istore #7
    //   533: iload_2
    //   534: istore #4
    //   536: iload_2
    //   537: ifne -> 544
    //   540: iload #5
    //   542: istore #4
    //   544: aload_0
    //   545: getfield mStrictWidthLimit : Z
    //   548: ifeq -> 559
    //   551: iload #7
    //   553: iflt -> 572
    //   556: goto -> 567
    //   559: iload #7
    //   561: iload #4
    //   563: iadd
    //   564: ifle -> 572
    //   567: iconst_1
    //   568: istore_2
    //   569: goto -> 574
    //   572: iconst_0
    //   573: istore_2
    //   574: iload #15
    //   576: iload_2
    //   577: iand
    //   578: istore #14
    //   580: iload_3
    //   581: istore #5
    //   583: iload #14
    //   585: ifeq -> 606
    //   588: iload #12
    //   590: ifeq -> 606
    //   593: aload #19
    //   595: iload #12
    //   597: iconst_1
    //   598: invokevirtual put : (IZ)V
    //   601: iload_1
    //   602: istore_2
    //   603: goto -> 682
    //   606: iload_1
    //   607: istore_2
    //   608: iload #16
    //   610: ifeq -> 682
    //   613: aload #19
    //   615: iload #12
    //   617: iconst_0
    //   618: invokevirtual put : (IZ)V
    //   621: iconst_0
    //   622: istore_3
    //   623: iload_1
    //   624: istore_2
    //   625: iload_3
    //   626: iload #9
    //   628: if_icmpge -> 682
    //   631: aload #17
    //   633: iload_3
    //   634: invokevirtual get : (I)Ljava/lang/Object;
    //   637: checkcast androidx/appcompat/view/menu/MenuItemImpl
    //   640: astore #21
    //   642: iload_1
    //   643: istore_2
    //   644: aload #21
    //   646: invokevirtual getGroupId : ()I
    //   649: iload #12
    //   651: if_icmpne -> 674
    //   654: iload_1
    //   655: istore_2
    //   656: aload #21
    //   658: invokevirtual isActionButton : ()Z
    //   661: ifeq -> 668
    //   664: iload_1
    //   665: iconst_1
    //   666: iadd
    //   667: istore_2
    //   668: aload #21
    //   670: iconst_0
    //   671: invokevirtual setIsActionButton : (Z)V
    //   674: iinc #3, 1
    //   677: iload_2
    //   678: istore_1
    //   679: goto -> 623
    //   682: iload_2
    //   683: istore_1
    //   684: iload #14
    //   686: ifeq -> 693
    //   689: iload_2
    //   690: iconst_1
    //   691: isub
    //   692: istore_1
    //   693: aload #20
    //   695: iload #14
    //   697: invokevirtual setIsActionButton : (Z)V
    //   700: iload #5
    //   702: istore_3
    //   703: goto -> 365
    //   706: aload #20
    //   708: iconst_0
    //   709: invokevirtual setIsActionButton : (Z)V
    //   712: iinc #9, 1
    //   715: goto -> 246
    //   718: iconst_1
    //   719: ireturn
  }
  
  public View getItemView(MenuItemImpl paramMenuItemImpl, View paramView, ViewGroup paramViewGroup) {
    boolean bool;
    View view = paramMenuItemImpl.getActionView();
    if (view == null || paramMenuItemImpl.hasCollapsibleActionView())
      view = super.getItemView(paramMenuItemImpl, paramView, paramViewGroup); 
    if (paramMenuItemImpl.isActionViewExpanded()) {
      bool = true;
    } else {
      bool = false;
    } 
    view.setVisibility(bool);
    ActionMenuView actionMenuView = (ActionMenuView)paramViewGroup;
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    if (!actionMenuView.checkLayoutParams(layoutParams))
      view.setLayoutParams((ViewGroup.LayoutParams)actionMenuView.generateLayoutParams(layoutParams)); 
    return view;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup) {
    MenuView menuView2 = this.mMenuView;
    MenuView menuView1 = super.getMenuView(paramViewGroup);
    if (menuView2 != menuView1)
      ((ActionMenuView)menuView1).setPresenter(this); 
    return menuView1;
  }
  
  public Drawable getOverflowIcon() {
    OverflowMenuButton overflowMenuButton = this.mOverflowButton;
    return (overflowMenuButton != null) ? overflowMenuButton.getDrawable() : (this.mPendingOverflowIconSet ? this.mPendingOverflowIcon : null);
  }
  
  public boolean hideOverflowMenu() {
    if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
      ((View)this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
      this.mPostedOpenRunnable = null;
      return true;
    } 
    OverflowPopup overflowPopup = this.mOverflowPopup;
    if (overflowPopup != null) {
      overflowPopup.dismiss();
      return true;
    } 
    return false;
  }
  
  public boolean hideSubMenus() {
    ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
    if (actionButtonSubmenu != null) {
      actionButtonSubmenu.dismiss();
      return true;
    } 
    return false;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {
    super.initForMenu(paramContext, paramMenuBuilder);
    Resources resources = paramContext.getResources();
    ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(paramContext);
    if (!this.mReserveOverflowSet)
      this.mReserveOverflow = actionBarPolicy.showsOverflowMenuButton(); 
    if (!this.mWidthLimitSet)
      this.mWidthLimit = actionBarPolicy.getEmbeddedMenuWidthLimit(); 
    if (!this.mMaxItemsSet)
      this.mMaxItems = actionBarPolicy.getMaxActionButtons(); 
    int i = this.mWidthLimit;
    if (this.mReserveOverflow) {
      if (this.mOverflowButton == null) {
        OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
        this.mOverflowButton = overflowMenuButton;
        if (this.mPendingOverflowIconSet) {
          overflowMenuButton.setImageDrawable(this.mPendingOverflowIcon);
          this.mPendingOverflowIcon = null;
          this.mPendingOverflowIconSet = false;
        } 
        int j = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mOverflowButton.measure(j, j);
      } 
      i -= this.mOverflowButton.getMeasuredWidth();
    } else {
      this.mOverflowButton = null;
    } 
    this.mActionItemWidthLimit = i;
    this.mMinCellSize = (int)((resources.getDisplayMetrics()).density * 56.0F);
  }
  
  public boolean isOverflowMenuShowPending() {
    return (this.mPostedOpenRunnable != null || isOverflowMenuShowing());
  }
  
  public boolean isOverflowMenuShowing() {
    boolean bool;
    OverflowPopup overflowPopup = this.mOverflowPopup;
    if (overflowPopup != null && overflowPopup.isShowing()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOverflowReserved() {
    return this.mReserveOverflow;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    dismissPopupMenus();
    super.onCloseMenu(paramMenuBuilder, paramBoolean);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    if (!this.mMaxItemsSet)
      this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons(); 
    if (this.mMenu != null)
      this.mMenu.onItemsChanged(true); 
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState))
      return; 
    paramParcelable = paramParcelable;
    if (((SavedState)paramParcelable).openSubMenuId > 0) {
      MenuItem menuItem = this.mMenu.findItem(((SavedState)paramParcelable).openSubMenuId);
      if (menuItem != null)
        onSubMenuSelected((SubMenuBuilder)menuItem.getSubMenu()); 
    } 
  }
  
  public Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState();
    savedState.openSubMenuId = this.mOpenSubMenuId;
    return savedState;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    boolean bool = paramSubMenuBuilder.hasVisibleItems();
    boolean bool1 = false;
    if (!bool)
      return false; 
    SubMenuBuilder subMenuBuilder;
    for (subMenuBuilder = paramSubMenuBuilder; subMenuBuilder.getParentMenu() != this.mMenu; subMenuBuilder = (SubMenuBuilder)subMenuBuilder.getParentMenu());
    View view = findViewForItem(subMenuBuilder.getItem());
    if (view == null)
      return false; 
    this.mOpenSubMenuId = paramSubMenuBuilder.getItem().getItemId();
    int i = paramSubMenuBuilder.size();
    byte b = 0;
    while (true) {
      bool = bool1;
      if (b < i) {
        MenuItem menuItem = paramSubMenuBuilder.getItem(b);
        if (menuItem.isVisible() && menuItem.getIcon() != null) {
          bool = true;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    ActionButtonSubmenu actionButtonSubmenu = new ActionButtonSubmenu(this.mContext, paramSubMenuBuilder, view);
    this.mActionButtonPopup = actionButtonSubmenu;
    actionButtonSubmenu.setForceShowIcon(bool);
    this.mActionButtonPopup.show();
    super.onSubMenuSelected(paramSubMenuBuilder);
    return true;
  }
  
  public void onSubUiVisibilityChanged(boolean paramBoolean) {
    if (paramBoolean) {
      super.onSubMenuSelected(null);
    } else if (this.mMenu != null) {
      this.mMenu.close(false);
    } 
  }
  
  public void setExpandedActionViewsExclusive(boolean paramBoolean) {
    this.mExpandedActionViewsExclusive = paramBoolean;
  }
  
  public void setItemLimit(int paramInt) {
    this.mMaxItems = paramInt;
    this.mMaxItemsSet = true;
  }
  
  public void setMenuView(ActionMenuView paramActionMenuView) {
    this.mMenuView = paramActionMenuView;
    paramActionMenuView.initialize(this.mMenu);
  }
  
  public void setOverflowIcon(Drawable paramDrawable) {
    OverflowMenuButton overflowMenuButton = this.mOverflowButton;
    if (overflowMenuButton != null) {
      overflowMenuButton.setImageDrawable(paramDrawable);
    } else {
      this.mPendingOverflowIconSet = true;
      this.mPendingOverflowIcon = paramDrawable;
    } 
  }
  
  public void setReserveOverflow(boolean paramBoolean) {
    this.mReserveOverflow = paramBoolean;
    this.mReserveOverflowSet = true;
  }
  
  public void setWidthLimit(int paramInt, boolean paramBoolean) {
    this.mWidthLimit = paramInt;
    this.mStrictWidthLimit = paramBoolean;
    this.mWidthLimitSet = true;
  }
  
  public boolean shouldIncludeItem(int paramInt, MenuItemImpl paramMenuItemImpl) {
    return paramMenuItemImpl.isActionButton();
  }
  
  public boolean showOverflowMenu() {
    if (this.mReserveOverflow && !isOverflowMenuShowing() && this.mMenu != null && this.mMenuView != null && this.mPostedOpenRunnable == null && !this.mMenu.getNonActionItems().isEmpty()) {
      this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, (View)this.mOverflowButton, true));
      ((View)this.mMenuView).post(this.mPostedOpenRunnable);
      return true;
    } 
    return false;
  }
  
  public void updateMenuView(boolean paramBoolean) {
    super.updateMenuView(paramBoolean);
    ((View)this.mMenuView).requestLayout();
    MenuBuilder<MenuItemImpl> menuBuilder = this.mMenu;
    byte b = 0;
    if (menuBuilder != null) {
      ArrayList<MenuItemImpl> arrayList = this.mMenu.getActionItems();
      int j = arrayList.size();
      for (byte b1 = 0; b1 < j; b1++) {
        ActionProvider actionProvider = ((MenuItemImpl)arrayList.get(b1)).getSupportActionProvider();
        if (actionProvider != null)
          actionProvider.setSubUiVisibilityListener(this); 
      } 
    } 
    if (this.mMenu != null) {
      ArrayList arrayList = this.mMenu.getNonActionItems();
    } else {
      menuBuilder = null;
    } 
    int i = b;
    if (this.mReserveOverflow) {
      i = b;
      if (menuBuilder != null) {
        int j = menuBuilder.size();
        if (j == 1) {
          i = ((MenuItemImpl)menuBuilder.get(0)).isActionViewExpanded() ^ true;
        } else {
          i = b;
          if (j > 0)
            i = 1; 
        } 
      } 
    } 
    if (i != 0) {
      if (this.mOverflowButton == null)
        this.mOverflowButton = new OverflowMenuButton(this.mSystemContext); 
      ViewGroup viewGroup = (ViewGroup)this.mOverflowButton.getParent();
      if (viewGroup != this.mMenuView) {
        if (viewGroup != null)
          viewGroup.removeView((View)this.mOverflowButton); 
        viewGroup = (ActionMenuView)this.mMenuView;
        viewGroup.addView((View)this.mOverflowButton, (ViewGroup.LayoutParams)viewGroup.generateOverflowButtonLayoutParams());
      } 
    } else {
      OverflowMenuButton overflowMenuButton = this.mOverflowButton;
      if (overflowMenuButton != null && overflowMenuButton.getParent() == this.mMenuView)
        ((ViewGroup)this.mMenuView).removeView((View)this.mOverflowButton); 
    } 
    ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
  }
  
  private class ActionButtonSubmenu extends MenuPopupHelper {
    final ActionMenuPresenter this$0;
    
    public ActionButtonSubmenu(Context param1Context, SubMenuBuilder param1SubMenuBuilder, View param1View) {
      super(param1Context, (MenuBuilder)param1SubMenuBuilder, param1View, false, R.attr.actionOverflowMenuStyle);
      if (!((MenuItemImpl)param1SubMenuBuilder.getItem()).isActionButton()) {
        ActionMenuPresenter.OverflowMenuButton overflowMenuButton;
        if (ActionMenuPresenter.this.mOverflowButton == null) {
          View view = (View)ActionMenuPresenter.this.mMenuView;
        } else {
          overflowMenuButton = ActionMenuPresenter.this.mOverflowButton;
        } 
        setAnchorView((View)overflowMenuButton);
      } 
      setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
    }
    
    protected void onDismiss() {
      ActionMenuPresenter.this.mActionButtonPopup = null;
      ActionMenuPresenter.this.mOpenSubMenuId = 0;
      super.onDismiss();
    }
  }
  
  private class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
    final ActionMenuPresenter this$0;
    
    public ShowableListMenu getPopup() {
      ShowableListMenu showableListMenu;
      if (ActionMenuPresenter.this.mActionButtonPopup != null) {
        showableListMenu = (ShowableListMenu)ActionMenuPresenter.this.mActionButtonPopup.getPopup();
      } else {
        showableListMenu = null;
      } 
      return showableListMenu;
    }
  }
  
  private class OpenOverflowRunnable implements Runnable {
    private ActionMenuPresenter.OverflowPopup mPopup;
    
    final ActionMenuPresenter this$0;
    
    public OpenOverflowRunnable(ActionMenuPresenter.OverflowPopup param1OverflowPopup) {
      this.mPopup = param1OverflowPopup;
    }
    
    public void run() {
      if (ActionMenuPresenter.this.mMenu != null)
        ActionMenuPresenter.this.mMenu.changeMenuMode(); 
      View view = (View)ActionMenuPresenter.this.mMenuView;
      if (view != null && view.getWindowToken() != null && this.mPopup.tryShow())
        ActionMenuPresenter.this.mOverflowPopup = this.mPopup; 
      ActionMenuPresenter.this.mPostedOpenRunnable = null;
    }
  }
  
  private class OverflowMenuButton extends AppCompatImageView implements ActionMenuView.ActionMenuChildView {
    final ActionMenuPresenter this$0;
    
    public OverflowMenuButton(Context param1Context) {
      super(param1Context, (AttributeSet)null, R.attr.actionOverflowButtonStyle);
      setClickable(true);
      setFocusable(true);
      setVisibility(0);
      setEnabled(true);
      TooltipCompat.setTooltipText((View)this, getContentDescription());
      setOnTouchListener(new ForwardingListener((View)this) {
            final ActionMenuPresenter.OverflowMenuButton this$1;
            
            final ActionMenuPresenter val$this$0;
            
            public ShowableListMenu getPopup() {
              return (ShowableListMenu)((ActionMenuPresenter.this.mOverflowPopup == null) ? null : ActionMenuPresenter.this.mOverflowPopup.getPopup());
            }
            
            public boolean onForwardingStarted() {
              ActionMenuPresenter.this.showOverflowMenu();
              return true;
            }
            
            public boolean onForwardingStopped() {
              if (ActionMenuPresenter.this.mPostedOpenRunnable != null)
                return false; 
              ActionMenuPresenter.this.hideOverflowMenu();
              return true;
            }
          });
    }
    
    public boolean needsDividerAfter() {
      return false;
    }
    
    public boolean needsDividerBefore() {
      return false;
    }
    
    public boolean performClick() {
      if (super.performClick())
        return true; 
      playSoundEffect(0);
      ActionMenuPresenter.this.showOverflowMenu();
      return true;
    }
    
    protected boolean setFrame(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      boolean bool = super.setFrame(param1Int1, param1Int2, param1Int3, param1Int4);
      Drawable drawable1 = getDrawable();
      Drawable drawable2 = getBackground();
      if (drawable1 != null && drawable2 != null) {
        int j = getWidth();
        param1Int2 = getHeight();
        param1Int1 = Math.max(j, param1Int2) / 2;
        int k = getPaddingLeft();
        int i = getPaddingRight();
        param1Int3 = getPaddingTop();
        param1Int4 = getPaddingBottom();
        i = (j + k - i) / 2;
        param1Int2 = (param1Int2 + param1Int3 - param1Int4) / 2;
        DrawableCompat.setHotspotBounds(drawable2, i - param1Int1, param1Int2 - param1Int1, i + param1Int1, param1Int2 + param1Int1);
      } 
      return bool;
    }
  }
  
  class null extends ForwardingListener {
    final ActionMenuPresenter.OverflowMenuButton this$1;
    
    final ActionMenuPresenter val$this$0;
    
    null(View param1View) {
      super(param1View);
    }
    
    public ShowableListMenu getPopup() {
      return (ShowableListMenu)((ActionMenuPresenter.this.mOverflowPopup == null) ? null : ActionMenuPresenter.this.mOverflowPopup.getPopup());
    }
    
    public boolean onForwardingStarted() {
      ActionMenuPresenter.this.showOverflowMenu();
      return true;
    }
    
    public boolean onForwardingStopped() {
      if (ActionMenuPresenter.this.mPostedOpenRunnable != null)
        return false; 
      ActionMenuPresenter.this.hideOverflowMenu();
      return true;
    }
  }
  
  private class OverflowPopup extends MenuPopupHelper {
    final ActionMenuPresenter this$0;
    
    public OverflowPopup(Context param1Context, MenuBuilder param1MenuBuilder, View param1View, boolean param1Boolean) {
      super(param1Context, param1MenuBuilder, param1View, param1Boolean, R.attr.actionOverflowMenuStyle);
      setGravity(8388613);
      setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
    }
    
    protected void onDismiss() {
      if (ActionMenuPresenter.this.mMenu != null)
        ActionMenuPresenter.this.mMenu.close(); 
      ActionMenuPresenter.this.mOverflowPopup = null;
      super.onDismiss();
    }
  }
  
  private class PopupPresenterCallback implements MenuPresenter.Callback {
    final ActionMenuPresenter this$0;
    
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {
      if (param1MenuBuilder instanceof SubMenuBuilder)
        param1MenuBuilder.getRootMenu().close(false); 
      MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
      if (callback != null)
        callback.onCloseMenu(param1MenuBuilder, param1Boolean); 
    }
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      MenuBuilder menuBuilder = ActionMenuPresenter.this.mMenu;
      boolean bool = false;
      if (param1MenuBuilder == menuBuilder)
        return false; 
      ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)param1MenuBuilder).getItem().getItemId();
      MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
      if (callback != null)
        bool = callback.onOpenSubMenu(param1MenuBuilder); 
      return bool;
    }
  }
  
  private static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public ActionMenuPresenter.SavedState createFromParcel(Parcel param2Parcel) {
          return new ActionMenuPresenter.SavedState(param2Parcel);
        }
        
        public ActionMenuPresenter.SavedState[] newArray(int param2Int) {
          return new ActionMenuPresenter.SavedState[param2Int];
        }
      };
    
    public int openSubMenuId;
    
    SavedState() {}
    
    SavedState(Parcel param1Parcel) {
      this.openSubMenuId = param1Parcel.readInt();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.openSubMenuId);
    }
  }
  
  class null implements Parcelable.Creator<SavedState> {
    public ActionMenuPresenter.SavedState createFromParcel(Parcel param1Parcel) {
      return new ActionMenuPresenter.SavedState(param1Parcel);
    }
    
    public ActionMenuPresenter.SavedState[] newArray(int param1Int) {
      return new ActionMenuPresenter.SavedState[param1Int];
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\ActionMenuPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */