package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.R;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class CascadingMenuPopup extends MenuPopup implements MenuPresenter, View.OnKeyListener, PopupWindow.OnDismissListener {
  static final int HORIZ_POSITION_LEFT = 0;
  
  static final int HORIZ_POSITION_RIGHT = 1;
  
  private static final int ITEM_LAYOUT = R.layout.abc_cascading_menu_item_layout;
  
  static final int SUBMENU_TIMEOUT_MS = 200;
  
  private View mAnchorView;
  
  private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() {
      final CascadingMenuPopup this$0;
      
      public void onViewAttachedToWindow(View param1View) {}
      
      public void onViewDetachedFromWindow(View param1View) {
        if (CascadingMenuPopup.this.mTreeObserver != null) {
          if (!CascadingMenuPopup.this.mTreeObserver.isAlive())
            CascadingMenuPopup.this.mTreeObserver = param1View.getViewTreeObserver(); 
          CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener);
        } 
        param1View.removeOnAttachStateChangeListener(this);
      }
    };
  
  private final Context mContext;
  
  private int mDropDownGravity = 0;
  
  private boolean mForceShowIcon;
  
  final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
      final CascadingMenuPopup this$0;
      
      public void onGlobalLayout() {
        if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.mShowingMenus.size() > 0 && !((CascadingMenuPopup.CascadingMenuInfo)CascadingMenuPopup.this.mShowingMenus.get(0)).window.isModal()) {
          View view = CascadingMenuPopup.this.mShownAnchorView;
          if (view == null || !view.isShown()) {
            CascadingMenuPopup.this.dismiss();
            return;
          } 
          Iterator<CascadingMenuPopup.CascadingMenuInfo> iterator = CascadingMenuPopup.this.mShowingMenus.iterator();
          while (iterator.hasNext())
            ((CascadingMenuPopup.CascadingMenuInfo)iterator.next()).window.show(); 
        } 
      }
    };
  
  private boolean mHasXOffset;
  
  private boolean mHasYOffset;
  
  private int mLastPosition;
  
  private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener() {
      final CascadingMenuPopup this$0;
      
      public void onItemHoverEnter(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
        // Byte code:
        //   0: aload_0
        //   1: getfield this$0 : Landroidx/appcompat/view/menu/CascadingMenuPopup;
        //   4: getfield mSubMenuHoverHandler : Landroid/os/Handler;
        //   7: astore #8
        //   9: aconst_null
        //   10: astore #7
        //   12: aload #8
        //   14: aconst_null
        //   15: invokevirtual removeCallbacksAndMessages : (Ljava/lang/Object;)V
        //   18: aload_0
        //   19: getfield this$0 : Landroidx/appcompat/view/menu/CascadingMenuPopup;
        //   22: getfield mShowingMenus : Ljava/util/List;
        //   25: invokeinterface size : ()I
        //   30: istore #4
        //   32: iconst_0
        //   33: istore_3
        //   34: iload_3
        //   35: iload #4
        //   37: if_icmpge -> 72
        //   40: aload_1
        //   41: aload_0
        //   42: getfield this$0 : Landroidx/appcompat/view/menu/CascadingMenuPopup;
        //   45: getfield mShowingMenus : Ljava/util/List;
        //   48: iload_3
        //   49: invokeinterface get : (I)Ljava/lang/Object;
        //   54: checkcast androidx/appcompat/view/menu/CascadingMenuPopup$CascadingMenuInfo
        //   57: getfield menu : Landroidx/appcompat/view/menu/MenuBuilder;
        //   60: if_acmpne -> 66
        //   63: goto -> 74
        //   66: iinc #3, 1
        //   69: goto -> 34
        //   72: iconst_m1
        //   73: istore_3
        //   74: iload_3
        //   75: iconst_m1
        //   76: if_icmpne -> 80
        //   79: return
        //   80: iinc #3, 1
        //   83: iload_3
        //   84: aload_0
        //   85: getfield this$0 : Landroidx/appcompat/view/menu/CascadingMenuPopup;
        //   88: getfield mShowingMenus : Ljava/util/List;
        //   91: invokeinterface size : ()I
        //   96: if_icmpge -> 117
        //   99: aload_0
        //   100: getfield this$0 : Landroidx/appcompat/view/menu/CascadingMenuPopup;
        //   103: getfield mShowingMenus : Ljava/util/List;
        //   106: iload_3
        //   107: invokeinterface get : (I)Ljava/lang/Object;
        //   112: checkcast androidx/appcompat/view/menu/CascadingMenuPopup$CascadingMenuInfo
        //   115: astore #7
        //   117: new androidx/appcompat/view/menu/CascadingMenuPopup$3$1
        //   120: dup
        //   121: aload_0
        //   122: aload #7
        //   124: aload_2
        //   125: aload_1
        //   126: invokespecial <init> : (Landroidx/appcompat/view/menu/CascadingMenuPopup$3;Landroidx/appcompat/view/menu/CascadingMenuPopup$CascadingMenuInfo;Landroid/view/MenuItem;Landroidx/appcompat/view/menu/MenuBuilder;)V
        //   129: astore_2
        //   130: invokestatic uptimeMillis : ()J
        //   133: lstore #5
        //   135: aload_0
        //   136: getfield this$0 : Landroidx/appcompat/view/menu/CascadingMenuPopup;
        //   139: getfield mSubMenuHoverHandler : Landroid/os/Handler;
        //   142: aload_2
        //   143: aload_1
        //   144: lload #5
        //   146: ldc2_w 200
        //   149: ladd
        //   150: invokevirtual postAtTime : (Ljava/lang/Runnable;Ljava/lang/Object;J)Z
        //   153: pop
        //   154: return
      }
      
      public void onItemHoverExit(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
        CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(param1MenuBuilder);
      }
    };
  
  private final int mMenuMaxWidth;
  
  private PopupWindow.OnDismissListener mOnDismissListener;
  
  private final boolean mOverflowOnly;
  
  private final List<MenuBuilder> mPendingMenus = new ArrayList<MenuBuilder>();
  
  private final int mPopupStyleAttr;
  
  private final int mPopupStyleRes;
  
  private MenuPresenter.Callback mPresenterCallback;
  
  private int mRawDropDownGravity = 0;
  
  boolean mShouldCloseImmediately;
  
  private boolean mShowTitle;
  
  final List<CascadingMenuInfo> mShowingMenus = new ArrayList<CascadingMenuInfo>();
  
  View mShownAnchorView;
  
  final Handler mSubMenuHoverHandler;
  
  ViewTreeObserver mTreeObserver;
  
  private int mXOffset;
  
  private int mYOffset;
  
  public CascadingMenuPopup(Context paramContext, View paramView, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.mContext = paramContext;
    this.mAnchorView = paramView;
    this.mPopupStyleAttr = paramInt1;
    this.mPopupStyleRes = paramInt2;
    this.mOverflowOnly = paramBoolean;
    this.mForceShowIcon = false;
    this.mLastPosition = getInitialMenuPosition();
    Resources resources = paramContext.getResources();
    this.mMenuMaxWidth = Math.max((resources.getDisplayMetrics()).widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    this.mSubMenuHoverHandler = new Handler();
  }
  
  private MenuPopupWindow createPopupWindow() {
    MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
    menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
    menuPopupWindow.setOnItemClickListener(this);
    menuPopupWindow.setOnDismissListener(this);
    menuPopupWindow.setAnchorView(this.mAnchorView);
    menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
    menuPopupWindow.setModal(true);
    menuPopupWindow.setInputMethodMode(2);
    return menuPopupWindow;
  }
  
  private int findIndexOfAddedMenu(MenuBuilder paramMenuBuilder) {
    int i = this.mShowingMenus.size();
    for (byte b = 0; b < i; b++) {
      if (paramMenuBuilder == ((CascadingMenuInfo)this.mShowingMenus.get(b)).menu)
        return b; 
    } 
    return -1;
  }
  
  private MenuItem findMenuItemForSubmenu(MenuBuilder paramMenuBuilder1, MenuBuilder paramMenuBuilder2) {
    int i = paramMenuBuilder1.size();
    for (byte b = 0; b < i; b++) {
      MenuItem menuItem = paramMenuBuilder1.getItem(b);
      if (menuItem.hasSubMenu() && paramMenuBuilder2 == menuItem.getSubMenu())
        return menuItem; 
    } 
    return null;
  }
  
  private View findParentViewForSubmenu(CascadingMenuInfo paramCascadingMenuInfo, MenuBuilder paramMenuBuilder) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: getfield menu : Landroidx/appcompat/view/menu/MenuBuilder;
    //   5: aload_2
    //   6: invokespecial findMenuItemForSubmenu : (Landroidx/appcompat/view/menu/MenuBuilder;Landroidx/appcompat/view/menu/MenuBuilder;)Landroid/view/MenuItem;
    //   9: astore_2
    //   10: aload_2
    //   11: ifnonnull -> 16
    //   14: aconst_null
    //   15: areturn
    //   16: aload_1
    //   17: invokevirtual getListView : ()Landroid/widget/ListView;
    //   20: astore #7
    //   22: aload #7
    //   24: invokevirtual getAdapter : ()Landroid/widget/ListAdapter;
    //   27: astore_1
    //   28: aload_1
    //   29: instanceof android/widget/HeaderViewListAdapter
    //   32: istore #6
    //   34: iconst_0
    //   35: istore_3
    //   36: iload #6
    //   38: ifeq -> 63
    //   41: aload_1
    //   42: checkcast android/widget/HeaderViewListAdapter
    //   45: astore_1
    //   46: aload_1
    //   47: invokevirtual getHeadersCount : ()I
    //   50: istore #4
    //   52: aload_1
    //   53: invokevirtual getWrappedAdapter : ()Landroid/widget/ListAdapter;
    //   56: checkcast androidx/appcompat/view/menu/MenuAdapter
    //   59: astore_1
    //   60: goto -> 71
    //   63: aload_1
    //   64: checkcast androidx/appcompat/view/menu/MenuAdapter
    //   67: astore_1
    //   68: iconst_0
    //   69: istore #4
    //   71: aload_1
    //   72: invokevirtual getCount : ()I
    //   75: istore #5
    //   77: iload_3
    //   78: iload #5
    //   80: if_icmpge -> 101
    //   83: aload_2
    //   84: aload_1
    //   85: iload_3
    //   86: invokevirtual getItem : (I)Landroidx/appcompat/view/menu/MenuItemImpl;
    //   89: if_acmpne -> 95
    //   92: goto -> 103
    //   95: iinc #3, 1
    //   98: goto -> 77
    //   101: iconst_m1
    //   102: istore_3
    //   103: iload_3
    //   104: iconst_m1
    //   105: if_icmpne -> 110
    //   108: aconst_null
    //   109: areturn
    //   110: iload_3
    //   111: iload #4
    //   113: iadd
    //   114: aload #7
    //   116: invokevirtual getFirstVisiblePosition : ()I
    //   119: isub
    //   120: istore_3
    //   121: iload_3
    //   122: iflt -> 144
    //   125: iload_3
    //   126: aload #7
    //   128: invokevirtual getChildCount : ()I
    //   131: if_icmplt -> 137
    //   134: goto -> 144
    //   137: aload #7
    //   139: iload_3
    //   140: invokevirtual getChildAt : (I)Landroid/view/View;
    //   143: areturn
    //   144: aconst_null
    //   145: areturn
  }
  
  private int getInitialMenuPosition() {
    int i = ViewCompat.getLayoutDirection(this.mAnchorView);
    boolean bool = true;
    if (i == 1)
      bool = false; 
    return bool;
  }
  
  private int getNextMenuPosition(int paramInt) {
    List<CascadingMenuInfo> list = this.mShowingMenus;
    ListView listView = ((CascadingMenuInfo)list.get(list.size() - 1)).getListView();
    int[] arrayOfInt = new int[2];
    listView.getLocationOnScreen(arrayOfInt);
    Rect rect = new Rect();
    this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
    return (this.mLastPosition == 1) ? ((arrayOfInt[0] + listView.getWidth() + paramInt > rect.right) ? 0 : 1) : ((arrayOfInt[0] - paramInt < 0) ? 1 : 0);
  }
  
  private void showMenu(MenuBuilder paramMenuBuilder) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mContext : Landroid/content/Context;
    //   4: invokestatic from : (Landroid/content/Context;)Landroid/view/LayoutInflater;
    //   7: astore #9
    //   9: new androidx/appcompat/view/menu/MenuAdapter
    //   12: dup
    //   13: aload_1
    //   14: aload #9
    //   16: aload_0
    //   17: getfield mOverflowOnly : Z
    //   20: getstatic androidx/appcompat/view/menu/CascadingMenuPopup.ITEM_LAYOUT : I
    //   23: invokespecial <init> : (Landroidx/appcompat/view/menu/MenuBuilder;Landroid/view/LayoutInflater;ZI)V
    //   26: astore #6
    //   28: aload_0
    //   29: invokevirtual isShowing : ()Z
    //   32: ifne -> 51
    //   35: aload_0
    //   36: getfield mForceShowIcon : Z
    //   39: ifeq -> 51
    //   42: aload #6
    //   44: iconst_1
    //   45: invokevirtual setForceShowIcon : (Z)V
    //   48: goto -> 67
    //   51: aload_0
    //   52: invokevirtual isShowing : ()Z
    //   55: ifeq -> 67
    //   58: aload #6
    //   60: aload_1
    //   61: invokestatic shouldPreserveIconSpacing : (Landroidx/appcompat/view/menu/MenuBuilder;)Z
    //   64: invokevirtual setForceShowIcon : (Z)V
    //   67: aload #6
    //   69: aconst_null
    //   70: aload_0
    //   71: getfield mContext : Landroid/content/Context;
    //   74: aload_0
    //   75: getfield mMenuMaxWidth : I
    //   78: invokestatic measureIndividualMenuWidth : (Landroid/widget/ListAdapter;Landroid/view/ViewGroup;Landroid/content/Context;I)I
    //   81: istore_3
    //   82: aload_0
    //   83: invokespecial createPopupWindow : ()Landroidx/appcompat/widget/MenuPopupWindow;
    //   86: astore #8
    //   88: aload #8
    //   90: aload #6
    //   92: invokevirtual setAdapter : (Landroid/widget/ListAdapter;)V
    //   95: aload #8
    //   97: iload_3
    //   98: invokevirtual setContentWidth : (I)V
    //   101: aload #8
    //   103: aload_0
    //   104: getfield mDropDownGravity : I
    //   107: invokevirtual setDropDownGravity : (I)V
    //   110: aload_0
    //   111: getfield mShowingMenus : Ljava/util/List;
    //   114: invokeinterface size : ()I
    //   119: ifle -> 161
    //   122: aload_0
    //   123: getfield mShowingMenus : Ljava/util/List;
    //   126: astore #6
    //   128: aload #6
    //   130: aload #6
    //   132: invokeinterface size : ()I
    //   137: iconst_1
    //   138: isub
    //   139: invokeinterface get : (I)Ljava/lang/Object;
    //   144: checkcast androidx/appcompat/view/menu/CascadingMenuPopup$CascadingMenuInfo
    //   147: astore #6
    //   149: aload_0
    //   150: aload #6
    //   152: aload_1
    //   153: invokespecial findParentViewForSubmenu : (Landroidx/appcompat/view/menu/CascadingMenuPopup$CascadingMenuInfo;Landroidx/appcompat/view/menu/MenuBuilder;)Landroid/view/View;
    //   156: astore #7
    //   158: goto -> 167
    //   161: aconst_null
    //   162: astore #6
    //   164: aconst_null
    //   165: astore #7
    //   167: aload #7
    //   169: ifnull -> 392
    //   172: aload #8
    //   174: iconst_0
    //   175: invokevirtual setTouchModal : (Z)V
    //   178: aload #8
    //   180: aconst_null
    //   181: invokevirtual setEnterTransition : (Ljava/lang/Object;)V
    //   184: aload_0
    //   185: iload_3
    //   186: invokespecial getNextMenuPosition : (I)I
    //   189: istore_2
    //   190: iload_2
    //   191: iconst_1
    //   192: if_icmpne -> 201
    //   195: iconst_1
    //   196: istore #4
    //   198: goto -> 204
    //   201: iconst_0
    //   202: istore #4
    //   204: aload_0
    //   205: iload_2
    //   206: putfield mLastPosition : I
    //   209: getstatic android/os/Build$VERSION.SDK_INT : I
    //   212: bipush #26
    //   214: if_icmplt -> 232
    //   217: aload #8
    //   219: aload #7
    //   221: invokevirtual setAnchorView : (Landroid/view/View;)V
    //   224: iconst_0
    //   225: istore_2
    //   226: iconst_0
    //   227: istore #5
    //   229: goto -> 320
    //   232: iconst_2
    //   233: newarray int
    //   235: astore #11
    //   237: aload_0
    //   238: getfield mAnchorView : Landroid/view/View;
    //   241: aload #11
    //   243: invokevirtual getLocationOnScreen : ([I)V
    //   246: iconst_2
    //   247: newarray int
    //   249: astore #10
    //   251: aload #7
    //   253: aload #10
    //   255: invokevirtual getLocationOnScreen : ([I)V
    //   258: aload_0
    //   259: getfield mDropDownGravity : I
    //   262: bipush #7
    //   264: iand
    //   265: iconst_5
    //   266: if_icmpne -> 299
    //   269: aload #11
    //   271: iconst_0
    //   272: aload #11
    //   274: iconst_0
    //   275: iaload
    //   276: aload_0
    //   277: getfield mAnchorView : Landroid/view/View;
    //   280: invokevirtual getWidth : ()I
    //   283: iadd
    //   284: iastore
    //   285: aload #10
    //   287: iconst_0
    //   288: aload #10
    //   290: iconst_0
    //   291: iaload
    //   292: aload #7
    //   294: invokevirtual getWidth : ()I
    //   297: iadd
    //   298: iastore
    //   299: aload #10
    //   301: iconst_0
    //   302: iaload
    //   303: aload #11
    //   305: iconst_0
    //   306: iaload
    //   307: isub
    //   308: istore #5
    //   310: aload #10
    //   312: iconst_1
    //   313: iaload
    //   314: aload #11
    //   316: iconst_1
    //   317: iaload
    //   318: isub
    //   319: istore_2
    //   320: aload_0
    //   321: getfield mDropDownGravity : I
    //   324: iconst_5
    //   325: iand
    //   326: iconst_5
    //   327: if_icmpne -> 347
    //   330: iload #4
    //   332: ifeq -> 338
    //   335: goto -> 358
    //   338: aload #7
    //   340: invokevirtual getWidth : ()I
    //   343: istore_3
    //   344: goto -> 366
    //   347: iload #4
    //   349: ifeq -> 366
    //   352: aload #7
    //   354: invokevirtual getWidth : ()I
    //   357: istore_3
    //   358: iload #5
    //   360: iload_3
    //   361: iadd
    //   362: istore_3
    //   363: goto -> 371
    //   366: iload #5
    //   368: iload_3
    //   369: isub
    //   370: istore_3
    //   371: aload #8
    //   373: iload_3
    //   374: invokevirtual setHorizontalOffset : (I)V
    //   377: aload #8
    //   379: iconst_1
    //   380: invokevirtual setOverlapAnchor : (Z)V
    //   383: aload #8
    //   385: iload_2
    //   386: invokevirtual setVerticalOffset : (I)V
    //   389: goto -> 433
    //   392: aload_0
    //   393: getfield mHasXOffset : Z
    //   396: ifeq -> 408
    //   399: aload #8
    //   401: aload_0
    //   402: getfield mXOffset : I
    //   405: invokevirtual setHorizontalOffset : (I)V
    //   408: aload_0
    //   409: getfield mHasYOffset : Z
    //   412: ifeq -> 424
    //   415: aload #8
    //   417: aload_0
    //   418: getfield mYOffset : I
    //   421: invokevirtual setVerticalOffset : (I)V
    //   424: aload #8
    //   426: aload_0
    //   427: invokevirtual getEpicenterBounds : ()Landroid/graphics/Rect;
    //   430: invokevirtual setEpicenterBounds : (Landroid/graphics/Rect;)V
    //   433: new androidx/appcompat/view/menu/CascadingMenuPopup$CascadingMenuInfo
    //   436: dup
    //   437: aload #8
    //   439: aload_1
    //   440: aload_0
    //   441: getfield mLastPosition : I
    //   444: invokespecial <init> : (Landroidx/appcompat/widget/MenuPopupWindow;Landroidx/appcompat/view/menu/MenuBuilder;I)V
    //   447: astore #7
    //   449: aload_0
    //   450: getfield mShowingMenus : Ljava/util/List;
    //   453: aload #7
    //   455: invokeinterface add : (Ljava/lang/Object;)Z
    //   460: pop
    //   461: aload #8
    //   463: invokevirtual show : ()V
    //   466: aload #8
    //   468: invokevirtual getListView : ()Landroid/widget/ListView;
    //   471: astore #7
    //   473: aload #7
    //   475: aload_0
    //   476: invokevirtual setOnKeyListener : (Landroid/view/View$OnKeyListener;)V
    //   479: aload #6
    //   481: ifnonnull -> 556
    //   484: aload_0
    //   485: getfield mShowTitle : Z
    //   488: ifeq -> 556
    //   491: aload_1
    //   492: invokevirtual getHeaderTitle : ()Ljava/lang/CharSequence;
    //   495: ifnull -> 556
    //   498: aload #9
    //   500: getstatic androidx/appcompat/R$layout.abc_popup_menu_header_item_layout : I
    //   503: aload #7
    //   505: iconst_0
    //   506: invokevirtual inflate : (ILandroid/view/ViewGroup;Z)Landroid/view/View;
    //   509: checkcast android/widget/FrameLayout
    //   512: astore #9
    //   514: aload #9
    //   516: ldc_w 16908310
    //   519: invokevirtual findViewById : (I)Landroid/view/View;
    //   522: checkcast android/widget/TextView
    //   525: astore #6
    //   527: aload #9
    //   529: iconst_0
    //   530: invokevirtual setEnabled : (Z)V
    //   533: aload #6
    //   535: aload_1
    //   536: invokevirtual getHeaderTitle : ()Ljava/lang/CharSequence;
    //   539: invokevirtual setText : (Ljava/lang/CharSequence;)V
    //   542: aload #7
    //   544: aload #9
    //   546: aconst_null
    //   547: iconst_0
    //   548: invokevirtual addHeaderView : (Landroid/view/View;Ljava/lang/Object;Z)V
    //   551: aload #8
    //   553: invokevirtual show : ()V
    //   556: return
  }
  
  public void addMenu(MenuBuilder paramMenuBuilder) {
    paramMenuBuilder.addMenuPresenter(this, this.mContext);
    if (isShowing()) {
      showMenu(paramMenuBuilder);
    } else {
      this.mPendingMenus.add(paramMenuBuilder);
    } 
  }
  
  protected boolean closeMenuOnSubMenuOpened() {
    return false;
  }
  
  public void dismiss() {
    int i = this.mShowingMenus.size();
    if (i > 0) {
      CascadingMenuInfo[] arrayOfCascadingMenuInfo = this.mShowingMenus.<CascadingMenuInfo>toArray(new CascadingMenuInfo[i]);
      while (--i >= 0) {
        CascadingMenuInfo cascadingMenuInfo = arrayOfCascadingMenuInfo[i];
        if (cascadingMenuInfo.window.isShowing())
          cascadingMenuInfo.window.dismiss(); 
        i--;
      } 
    } 
  }
  
  public boolean flagActionItems() {
    return false;
  }
  
  public ListView getListView() {
    ListView listView;
    if (this.mShowingMenus.isEmpty()) {
      listView = null;
    } else {
      List<CascadingMenuInfo> list = this.mShowingMenus;
      listView = ((CascadingMenuInfo)list.get(list.size() - 1)).getListView();
    } 
    return listView;
  }
  
  public boolean isShowing() {
    int i = this.mShowingMenus.size();
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (i > 0) {
      bool1 = bool2;
      if (((CascadingMenuInfo)this.mShowingMenus.get(0)).window.isShowing())
        bool1 = true; 
    } 
    return bool1;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    int j = findIndexOfAddedMenu(paramMenuBuilder);
    if (j < 0)
      return; 
    int i = j + 1;
    if (i < this.mShowingMenus.size())
      ((CascadingMenuInfo)this.mShowingMenus.get(i)).menu.close(false); 
    CascadingMenuInfo cascadingMenuInfo = this.mShowingMenus.remove(j);
    cascadingMenuInfo.menu.removeMenuPresenter(this);
    if (this.mShouldCloseImmediately) {
      cascadingMenuInfo.window.setExitTransition(null);
      cascadingMenuInfo.window.setAnimationStyle(0);
    } 
    cascadingMenuInfo.window.dismiss();
    i = this.mShowingMenus.size();
    if (i > 0) {
      this.mLastPosition = ((CascadingMenuInfo)this.mShowingMenus.get(i - 1)).position;
    } else {
      this.mLastPosition = getInitialMenuPosition();
    } 
    if (i == 0) {
      dismiss();
      MenuPresenter.Callback callback = this.mPresenterCallback;
      if (callback != null)
        callback.onCloseMenu(paramMenuBuilder, true); 
      ViewTreeObserver viewTreeObserver = this.mTreeObserver;
      if (viewTreeObserver != null) {
        if (viewTreeObserver.isAlive())
          this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener); 
        this.mTreeObserver = null;
      } 
      this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
      this.mOnDismissListener.onDismiss();
    } else if (paramBoolean) {
      ((CascadingMenuInfo)this.mShowingMenus.get(0)).menu.close(false);
    } 
  }
  
  public void onDismiss() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mShowingMenus : Ljava/util/List;
    //   4: invokeinterface size : ()I
    //   9: istore_2
    //   10: iconst_0
    //   11: istore_1
    //   12: iload_1
    //   13: iload_2
    //   14: if_icmpge -> 50
    //   17: aload_0
    //   18: getfield mShowingMenus : Ljava/util/List;
    //   21: iload_1
    //   22: invokeinterface get : (I)Ljava/lang/Object;
    //   27: checkcast androidx/appcompat/view/menu/CascadingMenuPopup$CascadingMenuInfo
    //   30: astore_3
    //   31: aload_3
    //   32: getfield window : Landroidx/appcompat/widget/MenuPopupWindow;
    //   35: invokevirtual isShowing : ()Z
    //   38: ifne -> 44
    //   41: goto -> 52
    //   44: iinc #1, 1
    //   47: goto -> 12
    //   50: aconst_null
    //   51: astore_3
    //   52: aload_3
    //   53: ifnull -> 64
    //   56: aload_3
    //   57: getfield menu : Landroidx/appcompat/view/menu/MenuBuilder;
    //   60: iconst_0
    //   61: invokevirtual close : (Z)V
    //   64: return
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getAction() == 1 && paramInt == 82) {
      dismiss();
      return true;
    } 
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {}
  
  public Parcelable onSaveInstanceState() {
    return null;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    for (CascadingMenuInfo cascadingMenuInfo : this.mShowingMenus) {
      if (paramSubMenuBuilder == cascadingMenuInfo.menu) {
        cascadingMenuInfo.getListView().requestFocus();
        return true;
      } 
    } 
    if (paramSubMenuBuilder.hasVisibleItems()) {
      addMenu(paramSubMenuBuilder);
      MenuPresenter.Callback callback = this.mPresenterCallback;
      if (callback != null)
        callback.onOpenSubMenu(paramSubMenuBuilder); 
      return true;
    } 
    return false;
  }
  
  public void setAnchorView(View paramView) {
    if (this.mAnchorView != paramView) {
      this.mAnchorView = paramView;
      this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(paramView));
    } 
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {
    this.mPresenterCallback = paramCallback;
  }
  
  public void setForceShowIcon(boolean paramBoolean) {
    this.mForceShowIcon = paramBoolean;
  }
  
  public void setGravity(int paramInt) {
    if (this.mRawDropDownGravity != paramInt) {
      this.mRawDropDownGravity = paramInt;
      this.mDropDownGravity = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this.mAnchorView));
    } 
  }
  
  public void setHorizontalOffset(int paramInt) {
    this.mHasXOffset = true;
    this.mXOffset = paramInt;
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setShowTitle(boolean paramBoolean) {
    this.mShowTitle = paramBoolean;
  }
  
  public void setVerticalOffset(int paramInt) {
    this.mHasYOffset = true;
    this.mYOffset = paramInt;
  }
  
  public void show() {
    if (isShowing())
      return; 
    Iterator<MenuBuilder> iterator = this.mPendingMenus.iterator();
    while (iterator.hasNext())
      showMenu(iterator.next()); 
    this.mPendingMenus.clear();
    View view = this.mAnchorView;
    this.mShownAnchorView = view;
    if (view != null) {
      boolean bool;
      if (this.mTreeObserver == null) {
        bool = true;
      } else {
        bool = false;
      } 
      ViewTreeObserver viewTreeObserver = this.mShownAnchorView.getViewTreeObserver();
      this.mTreeObserver = viewTreeObserver;
      if (bool)
        viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener); 
      this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
    } 
  }
  
  public void updateMenuView(boolean paramBoolean) {
    Iterator<CascadingMenuInfo> iterator = this.mShowingMenus.iterator();
    while (iterator.hasNext())
      toMenuAdapter(((CascadingMenuInfo)iterator.next()).getListView().getAdapter()).notifyDataSetChanged(); 
  }
  
  private static class CascadingMenuInfo {
    public final MenuBuilder menu;
    
    public final int position;
    
    public final MenuPopupWindow window;
    
    public CascadingMenuInfo(MenuPopupWindow param1MenuPopupWindow, MenuBuilder param1MenuBuilder, int param1Int) {
      this.window = param1MenuPopupWindow;
      this.menu = param1MenuBuilder;
      this.position = param1Int;
    }
    
    public ListView getListView() {
      return this.window.getListView();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface HorizPosition {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\view\menu\CascadingMenuPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */