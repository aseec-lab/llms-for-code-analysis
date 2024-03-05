package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends ViewGroup {
  private static final String TAG = "Toolbar";
  
  private MenuPresenter.Callback mActionMenuPresenterCallback;
  
  int mButtonGravity;
  
  ImageButton mCollapseButtonView;
  
  private CharSequence mCollapseDescription;
  
  private Drawable mCollapseIcon;
  
  private boolean mCollapsible;
  
  private int mContentInsetEndWithActions;
  
  private int mContentInsetStartWithNavigation;
  
  private RtlSpacingHelper mContentInsets;
  
  private boolean mEatingHover;
  
  private boolean mEatingTouch;
  
  View mExpandedActionView;
  
  private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
  
  private int mGravity = 8388627;
  
  private final ArrayList<View> mHiddenViews = new ArrayList<View>();
  
  private ImageView mLogoView;
  
  private int mMaxButtonHeight;
  
  private MenuBuilder.Callback mMenuBuilderCallback;
  
  private ActionMenuView mMenuView;
  
  private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() {
      final Toolbar this$0;
      
      public boolean onMenuItemClick(MenuItem param1MenuItem) {
        return (Toolbar.this.mOnMenuItemClickListener != null) ? Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(param1MenuItem) : false;
      }
    };
  
  private ImageButton mNavButtonView;
  
  OnMenuItemClickListener mOnMenuItemClickListener;
  
  private ActionMenuPresenter mOuterActionMenuPresenter;
  
  private Context mPopupContext;
  
  private int mPopupTheme;
  
  private final Runnable mShowOverflowMenuRunnable = new Runnable() {
      final Toolbar this$0;
      
      public void run() {
        Toolbar.this.showOverflowMenu();
      }
    };
  
  private CharSequence mSubtitleText;
  
  private int mSubtitleTextAppearance;
  
  private ColorStateList mSubtitleTextColor;
  
  private TextView mSubtitleTextView;
  
  private final int[] mTempMargins = new int[2];
  
  private final ArrayList<View> mTempViews = new ArrayList<View>();
  
  private int mTitleMarginBottom;
  
  private int mTitleMarginEnd;
  
  private int mTitleMarginStart;
  
  private int mTitleMarginTop;
  
  private CharSequence mTitleText;
  
  private int mTitleTextAppearance;
  
  private ColorStateList mTitleTextColor;
  
  private TextView mTitleTextView;
  
  private ToolbarWidgetWrapper mWrapper;
  
  public Toolbar(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public Toolbar(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.toolbarStyle);
  }
  
  public Toolbar(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, R.styleable.Toolbar, paramInt, 0);
    ViewCompat.saveAttributeDataForStyleable((View)this, paramContext, R.styleable.Toolbar, paramAttributeSet, tintTypedArray.getWrappedTypeArray(), paramInt, 0);
    this.mTitleTextAppearance = tintTypedArray.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
    this.mSubtitleTextAppearance = tintTypedArray.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
    this.mGravity = tintTypedArray.getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
    this.mButtonGravity = tintTypedArray.getInteger(R.styleable.Toolbar_buttonGravity, 48);
    int i = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
    paramInt = i;
    if (tintTypedArray.hasValue(R.styleable.Toolbar_titleMargins))
      paramInt = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, i); 
    this.mTitleMarginBottom = paramInt;
    this.mTitleMarginTop = paramInt;
    this.mTitleMarginEnd = paramInt;
    this.mTitleMarginStart = paramInt;
    paramInt = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
    if (paramInt >= 0)
      this.mTitleMarginStart = paramInt; 
    paramInt = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1);
    if (paramInt >= 0)
      this.mTitleMarginEnd = paramInt; 
    paramInt = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1);
    if (paramInt >= 0)
      this.mTitleMarginTop = paramInt; 
    paramInt = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1);
    if (paramInt >= 0)
      this.mTitleMarginBottom = paramInt; 
    this.mMaxButtonHeight = tintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
    int k = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, -2147483648);
    i = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, -2147483648);
    int j = tintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
    paramInt = tintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
    ensureContentInsets();
    this.mContentInsets.setAbsolute(j, paramInt);
    if (k != Integer.MIN_VALUE || i != Integer.MIN_VALUE)
      this.mContentInsets.setRelative(k, i); 
    this.mContentInsetStartWithNavigation = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, -2147483648);
    this.mContentInsetEndWithActions = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, -2147483648);
    this.mCollapseIcon = tintTypedArray.getDrawable(R.styleable.Toolbar_collapseIcon);
    this.mCollapseDescription = tintTypedArray.getText(R.styleable.Toolbar_collapseContentDescription);
    CharSequence charSequence3 = tintTypedArray.getText(R.styleable.Toolbar_title);
    if (!TextUtils.isEmpty(charSequence3))
      setTitle(charSequence3); 
    charSequence3 = tintTypedArray.getText(R.styleable.Toolbar_subtitle);
    if (!TextUtils.isEmpty(charSequence3))
      setSubtitle(charSequence3); 
    this.mPopupContext = getContext();
    setPopupTheme(tintTypedArray.getResourceId(R.styleable.Toolbar_popupTheme, 0));
    Drawable drawable2 = tintTypedArray.getDrawable(R.styleable.Toolbar_navigationIcon);
    if (drawable2 != null)
      setNavigationIcon(drawable2); 
    CharSequence charSequence2 = tintTypedArray.getText(R.styleable.Toolbar_navigationContentDescription);
    if (!TextUtils.isEmpty(charSequence2))
      setNavigationContentDescription(charSequence2); 
    Drawable drawable1 = tintTypedArray.getDrawable(R.styleable.Toolbar_logo);
    if (drawable1 != null)
      setLogo(drawable1); 
    CharSequence charSequence1 = tintTypedArray.getText(R.styleable.Toolbar_logoDescription);
    if (!TextUtils.isEmpty(charSequence1))
      setLogoDescription(charSequence1); 
    if (tintTypedArray.hasValue(R.styleable.Toolbar_titleTextColor))
      setTitleTextColor(tintTypedArray.getColorStateList(R.styleable.Toolbar_titleTextColor)); 
    if (tintTypedArray.hasValue(R.styleable.Toolbar_subtitleTextColor))
      setSubtitleTextColor(tintTypedArray.getColorStateList(R.styleable.Toolbar_subtitleTextColor)); 
    if (tintTypedArray.hasValue(R.styleable.Toolbar_menu))
      inflateMenu(tintTypedArray.getResourceId(R.styleable.Toolbar_menu, 0)); 
    tintTypedArray.recycle();
  }
  
  private void addCustomViewsWithGravity(List<View> paramList, int paramInt) {
    int i = ViewCompat.getLayoutDirection((View)this);
    boolean bool = false;
    if (i == 1) {
      i = 1;
    } else {
      i = 0;
    } 
    int k = getChildCount();
    int j = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    paramList.clear();
    paramInt = bool;
    if (i != 0) {
      for (paramInt = k - 1; paramInt >= 0; paramInt--) {
        View view = getChildAt(paramInt);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mViewType == 0 && shouldLayout(view) && getChildHorizontalGravity(layoutParams.gravity) == j)
          paramList.add(view); 
      } 
    } else {
      while (paramInt < k) {
        View view = getChildAt(paramInt);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mViewType == 0 && shouldLayout(view) && getChildHorizontalGravity(layoutParams.gravity) == j)
          paramList.add(view); 
        paramInt++;
      } 
    } 
  }
  
  private void addSystemView(View paramView, boolean paramBoolean) {
    LayoutParams layoutParams;
    ViewGroup.LayoutParams layoutParams1 = paramView.getLayoutParams();
    if (layoutParams1 == null) {
      layoutParams = generateDefaultLayoutParams();
    } else if (!checkLayoutParams((ViewGroup.LayoutParams)layoutParams)) {
      layoutParams = generateLayoutParams((ViewGroup.LayoutParams)layoutParams);
    } else {
      layoutParams = layoutParams;
    } 
    layoutParams.mViewType = 1;
    if (paramBoolean && this.mExpandedActionView != null) {
      paramView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.mHiddenViews.add(paramView);
    } else {
      addView(paramView, (ViewGroup.LayoutParams)layoutParams);
    } 
  }
  
  private void ensureContentInsets() {
    if (this.mContentInsets == null)
      this.mContentInsets = new RtlSpacingHelper(); 
  }
  
  private void ensureLogoView() {
    if (this.mLogoView == null)
      this.mLogoView = new AppCompatImageView(getContext()); 
  }
  
  private void ensureMenu() {
    ensureMenuView();
    if (this.mMenuView.peekMenu() == null) {
      MenuBuilder menuBuilder = (MenuBuilder)this.mMenuView.getMenu();
      if (this.mExpandedMenuPresenter == null)
        this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter(); 
      this.mMenuView.setExpandedActionViewsExclusive(true);
      menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
    } 
  }
  
  private void ensureMenuView() {
    if (this.mMenuView == null) {
      ActionMenuView actionMenuView = new ActionMenuView(getContext());
      this.mMenuView = actionMenuView;
      actionMenuView.setPopupTheme(this.mPopupTheme);
      this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
      this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
      LayoutParams layoutParams = generateDefaultLayoutParams();
      layoutParams.gravity = 0x800005 | this.mButtonGravity & 0x70;
      this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      addSystemView((View)this.mMenuView, false);
    } 
  }
  
  private void ensureNavButtonView() {
    if (this.mNavButtonView == null) {
      this.mNavButtonView = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
      LayoutParams layoutParams = generateDefaultLayoutParams();
      layoutParams.gravity = 0x800003 | this.mButtonGravity & 0x70;
      this.mNavButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    } 
  }
  
  private int getChildHorizontalGravity(int paramInt) {
    int i = ViewCompat.getLayoutDirection((View)this);
    int j = GravityCompat.getAbsoluteGravity(paramInt, i) & 0x7;
    if (j != 1) {
      paramInt = 3;
      if (j != 3 && j != 5) {
        if (i == 1)
          paramInt = 5; 
        return paramInt;
      } 
    } 
    return j;
  }
  
  private int getChildTop(View paramView, int paramInt) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int j = paramView.getMeasuredHeight();
    if (paramInt > 0) {
      paramInt = (j - paramInt) / 2;
    } else {
      paramInt = 0;
    } 
    int i = getChildVerticalGravity(layoutParams.gravity);
    if (i != 48) {
      if (i != 80) {
        int k = getPaddingTop();
        int m = getPaddingBottom();
        paramInt = getHeight();
        i = (paramInt - k - m - j) / 2;
        if (i < layoutParams.topMargin) {
          paramInt = layoutParams.topMargin;
        } else {
          j = paramInt - m - j - i - k;
          paramInt = i;
          if (j < layoutParams.bottomMargin)
            paramInt = Math.max(0, i - layoutParams.bottomMargin - j); 
        } 
        return k + paramInt;
      } 
      return getHeight() - getPaddingBottom() - j - layoutParams.bottomMargin - paramInt;
    } 
    return getPaddingTop() - paramInt;
  }
  
  private int getChildVerticalGravity(int paramInt) {
    int i = paramInt & 0x70;
    paramInt = i;
    if (i != 16) {
      paramInt = i;
      if (i != 48) {
        paramInt = i;
        if (i != 80)
          paramInt = this.mGravity & 0x70; 
      } 
    } 
    return paramInt;
  }
  
  private int getHorizontalMargins(View paramView) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(marginLayoutParams) + MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
  }
  
  private MenuInflater getMenuInflater() {
    return (MenuInflater)new SupportMenuInflater(getContext());
  }
  
  private int getVerticalMargins(View paramView) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
  }
  
  private int getViewListMeasuredWidth(List<View> paramList, int[] paramArrayOfint) {
    int k = paramArrayOfint[0];
    int j = paramArrayOfint[1];
    int m = paramList.size();
    byte b = 0;
    int i = 0;
    while (b < m) {
      View view = paramList.get(b);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      k = layoutParams.leftMargin - k;
      j = layoutParams.rightMargin - j;
      int n = Math.max(0, k);
      int i1 = Math.max(0, j);
      k = Math.max(0, -k);
      j = Math.max(0, -j);
      i += n + view.getMeasuredWidth() + i1;
      b++;
    } 
    return i;
  }
  
  private boolean isChildOrHidden(View paramView) {
    return (paramView.getParent() == this || this.mHiddenViews.contains(paramView));
  }
  
  private int layoutChildLeft(View paramView, int paramInt1, int[] paramArrayOfint, int paramInt2) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = layoutParams.leftMargin - paramArrayOfint[0];
    paramInt1 += Math.max(0, i);
    paramArrayOfint[0] = Math.max(0, -i);
    i = getChildTop(paramView, paramInt2);
    paramInt2 = paramView.getMeasuredWidth();
    paramView.layout(paramInt1, i, paramInt1 + paramInt2, paramView.getMeasuredHeight() + i);
    return paramInt1 + paramInt2 + layoutParams.rightMargin;
  }
  
  private int layoutChildRight(View paramView, int paramInt1, int[] paramArrayOfint, int paramInt2) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = layoutParams.rightMargin - paramArrayOfint[1];
    paramInt1 -= Math.max(0, i);
    paramArrayOfint[1] = Math.max(0, -i);
    paramInt2 = getChildTop(paramView, paramInt2);
    i = paramView.getMeasuredWidth();
    paramView.layout(paramInt1 - i, paramInt2, paramInt1, paramView.getMeasuredHeight() + paramInt2);
    return paramInt1 - i + layoutParams.leftMargin;
  }
  
  private int measureChildCollapseMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    int k = marginLayoutParams.leftMargin - paramArrayOfint[0];
    int j = marginLayoutParams.rightMargin - paramArrayOfint[1];
    int i = Math.max(0, k) + Math.max(0, j);
    paramArrayOfint[0] = Math.max(0, -k);
    paramArrayOfint[1] = Math.max(0, -j);
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + i + paramInt2, marginLayoutParams.width), getChildMeasureSpec(paramInt3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + paramInt4, marginLayoutParams.height));
    return paramView.getMeasuredWidth() + i;
  }
  
  private void measureChildConstrained(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    int i = getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + paramInt2, marginLayoutParams.width);
    paramInt2 = getChildMeasureSpec(paramInt3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + paramInt4, marginLayoutParams.height);
    paramInt3 = View.MeasureSpec.getMode(paramInt2);
    paramInt1 = paramInt2;
    if (paramInt3 != 1073741824) {
      paramInt1 = paramInt2;
      if (paramInt5 >= 0) {
        paramInt1 = paramInt5;
        if (paramInt3 != 0)
          paramInt1 = Math.min(View.MeasureSpec.getSize(paramInt2), paramInt5); 
        paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824);
      } 
    } 
    paramView.measure(i, paramInt1);
  }
  
  private void postShowOverflowMenu() {
    removeCallbacks(this.mShowOverflowMenuRunnable);
    post(this.mShowOverflowMenuRunnable);
  }
  
  private boolean shouldCollapse() {
    if (!this.mCollapsible)
      return false; 
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if (shouldLayout(view) && view.getMeasuredWidth() > 0 && view.getMeasuredHeight() > 0)
        return false; 
    } 
    return true;
  }
  
  private boolean shouldLayout(View paramView) {
    boolean bool;
    if (paramView != null && paramView.getParent() == this && paramView.getVisibility() != 8) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void addChildrenForExpandedActionView() {
    for (int i = this.mHiddenViews.size() - 1; i >= 0; i--)
      addView(this.mHiddenViews.get(i)); 
    this.mHiddenViews.clear();
  }
  
  public boolean canShowOverflowMenu() {
    if (getVisibility() == 0) {
      ActionMenuView actionMenuView = this.mMenuView;
      if (actionMenuView != null && actionMenuView.isOverflowReserved())
        return true; 
    } 
    return false;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    boolean bool;
    if (super.checkLayoutParams(paramLayoutParams) && paramLayoutParams instanceof LayoutParams) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void collapseActionView() {
    MenuItemImpl menuItemImpl;
    ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
    if (expandedActionViewMenuPresenter == null) {
      expandedActionViewMenuPresenter = null;
    } else {
      menuItemImpl = expandedActionViewMenuPresenter.mCurrentExpandedItem;
    } 
    if (menuItemImpl != null)
      menuItemImpl.collapseActionView(); 
  }
  
  public void dismissPopupMenus() {
    ActionMenuView actionMenuView = this.mMenuView;
    if (actionMenuView != null)
      actionMenuView.dismissPopupMenus(); 
  }
  
  void ensureCollapseButtonView() {
    if (this.mCollapseButtonView == null) {
      AppCompatImageButton appCompatImageButton = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
      this.mCollapseButtonView = appCompatImageButton;
      appCompatImageButton.setImageDrawable(this.mCollapseIcon);
      this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
      LayoutParams layoutParams = generateDefaultLayoutParams();
      layoutParams.gravity = 0x800003 | this.mButtonGravity & 0x70;
      layoutParams.mViewType = 2;
      this.mCollapseButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.mCollapseButtonView.setOnClickListener(new View.OnClickListener() {
            final Toolbar this$0;
            
            public void onClick(View param1View) {
              Toolbar.this.collapseActionView();
            }
          });
    } 
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-2, -2);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (paramLayoutParams instanceof LayoutParams) ? new LayoutParams((LayoutParams)paramLayoutParams) : ((paramLayoutParams instanceof ActionBar.LayoutParams) ? new LayoutParams((ActionBar.LayoutParams)paramLayoutParams) : ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams) ? new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams) : new LayoutParams(paramLayoutParams)));
  }
  
  public CharSequence getCollapseContentDescription() {
    ImageButton imageButton = this.mCollapseButtonView;
    if (imageButton != null) {
      CharSequence charSequence = imageButton.getContentDescription();
    } else {
      imageButton = null;
    } 
    return (CharSequence)imageButton;
  }
  
  public Drawable getCollapseIcon() {
    ImageButton imageButton = this.mCollapseButtonView;
    if (imageButton != null) {
      Drawable drawable = imageButton.getDrawable();
    } else {
      imageButton = null;
    } 
    return (Drawable)imageButton;
  }
  
  public int getContentInsetEnd() {
    boolean bool;
    RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
    if (rtlSpacingHelper != null) {
      bool = rtlSpacingHelper.getEnd();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getContentInsetEndWithActions() {
    int i = this.mContentInsetEndWithActions;
    if (i == Integer.MIN_VALUE)
      i = getContentInsetEnd(); 
    return i;
  }
  
  public int getContentInsetLeft() {
    boolean bool;
    RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
    if (rtlSpacingHelper != null) {
      bool = rtlSpacingHelper.getLeft();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getContentInsetRight() {
    boolean bool;
    RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
    if (rtlSpacingHelper != null) {
      bool = rtlSpacingHelper.getRight();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getContentInsetStart() {
    boolean bool;
    RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
    if (rtlSpacingHelper != null) {
      bool = rtlSpacingHelper.getStart();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getContentInsetStartWithNavigation() {
    int i = this.mContentInsetStartWithNavigation;
    if (i == Integer.MIN_VALUE)
      i = getContentInsetStart(); 
    return i;
  }
  
  public int getCurrentContentInsetEnd() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mMenuView : Landroidx/appcompat/widget/ActionMenuView;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull -> 30
    //   9: aload_2
    //   10: invokevirtual peekMenu : ()Landroidx/appcompat/view/menu/MenuBuilder;
    //   13: astore_2
    //   14: aload_2
    //   15: ifnull -> 30
    //   18: aload_2
    //   19: invokevirtual hasVisibleItems : ()Z
    //   22: ifeq -> 30
    //   25: iconst_1
    //   26: istore_1
    //   27: goto -> 32
    //   30: iconst_0
    //   31: istore_1
    //   32: iload_1
    //   33: ifeq -> 55
    //   36: aload_0
    //   37: invokevirtual getContentInsetEnd : ()I
    //   40: aload_0
    //   41: getfield mContentInsetEndWithActions : I
    //   44: iconst_0
    //   45: invokestatic max : (II)I
    //   48: invokestatic max : (II)I
    //   51: istore_1
    //   52: goto -> 60
    //   55: aload_0
    //   56: invokevirtual getContentInsetEnd : ()I
    //   59: istore_1
    //   60: iload_1
    //   61: ireturn
  }
  
  public int getCurrentContentInsetLeft() {
    int i;
    if (ViewCompat.getLayoutDirection((View)this) == 1) {
      i = getCurrentContentInsetEnd();
    } else {
      i = getCurrentContentInsetStart();
    } 
    return i;
  }
  
  public int getCurrentContentInsetRight() {
    int i;
    if (ViewCompat.getLayoutDirection((View)this) == 1) {
      i = getCurrentContentInsetStart();
    } else {
      i = getCurrentContentInsetEnd();
    } 
    return i;
  }
  
  public int getCurrentContentInsetStart() {
    int i;
    if (getNavigationIcon() != null) {
      i = Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
    } else {
      i = getContentInsetStart();
    } 
    return i;
  }
  
  public Drawable getLogo() {
    ImageView imageView = this.mLogoView;
    if (imageView != null) {
      Drawable drawable = imageView.getDrawable();
    } else {
      imageView = null;
    } 
    return (Drawable)imageView;
  }
  
  public CharSequence getLogoDescription() {
    ImageView imageView = this.mLogoView;
    if (imageView != null) {
      CharSequence charSequence = imageView.getContentDescription();
    } else {
      imageView = null;
    } 
    return (CharSequence)imageView;
  }
  
  public Menu getMenu() {
    ensureMenu();
    return this.mMenuView.getMenu();
  }
  
  public CharSequence getNavigationContentDescription() {
    ImageButton imageButton = this.mNavButtonView;
    if (imageButton != null) {
      CharSequence charSequence = imageButton.getContentDescription();
    } else {
      imageButton = null;
    } 
    return (CharSequence)imageButton;
  }
  
  public Drawable getNavigationIcon() {
    ImageButton imageButton = this.mNavButtonView;
    if (imageButton != null) {
      Drawable drawable = imageButton.getDrawable();
    } else {
      imageButton = null;
    } 
    return (Drawable)imageButton;
  }
  
  ActionMenuPresenter getOuterActionMenuPresenter() {
    return this.mOuterActionMenuPresenter;
  }
  
  public Drawable getOverflowIcon() {
    ensureMenu();
    return this.mMenuView.getOverflowIcon();
  }
  
  Context getPopupContext() {
    return this.mPopupContext;
  }
  
  public int getPopupTheme() {
    return this.mPopupTheme;
  }
  
  public CharSequence getSubtitle() {
    return this.mSubtitleText;
  }
  
  final TextView getSubtitleTextView() {
    return this.mSubtitleTextView;
  }
  
  public CharSequence getTitle() {
    return this.mTitleText;
  }
  
  public int getTitleMarginBottom() {
    return this.mTitleMarginBottom;
  }
  
  public int getTitleMarginEnd() {
    return this.mTitleMarginEnd;
  }
  
  public int getTitleMarginStart() {
    return this.mTitleMarginStart;
  }
  
  public int getTitleMarginTop() {
    return this.mTitleMarginTop;
  }
  
  final TextView getTitleTextView() {
    return this.mTitleTextView;
  }
  
  public DecorToolbar getWrapper() {
    if (this.mWrapper == null)
      this.mWrapper = new ToolbarWidgetWrapper(this, true); 
    return this.mWrapper;
  }
  
  public boolean hasExpandedActionView() {
    boolean bool;
    ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
    if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean hideOverflowMenu() {
    boolean bool;
    ActionMenuView actionMenuView = this.mMenuView;
    if (actionMenuView != null && actionMenuView.hideOverflowMenu()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void inflateMenu(int paramInt) {
    getMenuInflater().inflate(paramInt, getMenu());
  }
  
  public boolean isOverflowMenuShowPending() {
    boolean bool;
    ActionMenuView actionMenuView = this.mMenuView;
    if (actionMenuView != null && actionMenuView.isOverflowMenuShowPending()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOverflowMenuShowing() {
    boolean bool;
    ActionMenuView actionMenuView = this.mMenuView;
    if (actionMenuView != null && actionMenuView.isOverflowMenuShowing()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isTitleTruncated() {
    TextView textView = this.mTitleTextView;
    if (textView == null)
      return false; 
    Layout layout = textView.getLayout();
    if (layout == null)
      return false; 
    int i = layout.getLineCount();
    for (byte b = 0; b < i; b++) {
      if (layout.getEllipsisCount(b) > 0)
        return true; 
    } 
    return false;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    removeCallbacks(this.mShowOverflowMenuRunnable);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getActionMasked();
    if (i == 9)
      this.mEatingHover = false; 
    if (!this.mEatingHover) {
      boolean bool = super.onHoverEvent(paramMotionEvent);
      if (i == 9 && !bool)
        this.mEatingHover = true; 
    } 
    if (i == 10 || i == 3)
      this.mEatingHover = false; 
    return true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    boolean bool;
    if (ViewCompat.getLayoutDirection((View)this) == 1) {
      j = 1;
    } else {
      j = 0;
    } 
    int m = getWidth();
    int i3 = getHeight();
    int i = getPaddingLeft();
    int n = getPaddingRight();
    int i1 = getPaddingTop();
    int i2 = getPaddingBottom();
    int k = m - n;
    int[] arrayOfInt = this.mTempMargins;
    arrayOfInt[1] = 0;
    arrayOfInt[0] = 0;
    paramInt1 = ViewCompat.getMinimumHeight((View)this);
    if (paramInt1 >= 0) {
      bool = Math.min(paramInt1, paramInt4 - paramInt2);
    } else {
      bool = false;
    } 
    if (shouldLayout((View)this.mNavButtonView)) {
      if (j) {
        paramInt4 = layoutChildRight((View)this.mNavButtonView, k, arrayOfInt, bool);
        paramInt3 = i;
      } else {
        paramInt3 = layoutChildLeft((View)this.mNavButtonView, i, arrayOfInt, bool);
        paramInt4 = k;
      } 
    } else {
      paramInt3 = i;
      paramInt4 = k;
    } 
    paramInt2 = paramInt3;
    paramInt1 = paramInt4;
    if (shouldLayout((View)this.mCollapseButtonView))
      if (j) {
        paramInt1 = layoutChildRight((View)this.mCollapseButtonView, paramInt4, arrayOfInt, bool);
        paramInt2 = paramInt3;
      } else {
        paramInt2 = layoutChildLeft((View)this.mCollapseButtonView, paramInt3, arrayOfInt, bool);
        paramInt1 = paramInt4;
      }  
    paramInt4 = paramInt2;
    paramInt3 = paramInt1;
    if (shouldLayout((View)this.mMenuView))
      if (j) {
        paramInt4 = layoutChildLeft((View)this.mMenuView, paramInt2, arrayOfInt, bool);
        paramInt3 = paramInt1;
      } else {
        paramInt3 = layoutChildRight((View)this.mMenuView, paramInt1, arrayOfInt, bool);
        paramInt4 = paramInt2;
      }  
    paramInt2 = getCurrentContentInsetLeft();
    paramInt1 = getCurrentContentInsetRight();
    arrayOfInt[0] = Math.max(0, paramInt2 - paramInt4);
    arrayOfInt[1] = Math.max(0, paramInt1 - k - paramInt3);
    paramInt2 = Math.max(paramInt4, paramInt2);
    paramInt3 = Math.min(paramInt3, k - paramInt1);
    paramInt4 = paramInt2;
    paramInt1 = paramInt3;
    if (shouldLayout(this.mExpandedActionView))
      if (j) {
        paramInt1 = layoutChildRight(this.mExpandedActionView, paramInt3, arrayOfInt, bool);
        paramInt4 = paramInt2;
      } else {
        paramInt4 = layoutChildLeft(this.mExpandedActionView, paramInt2, arrayOfInt, bool);
        paramInt1 = paramInt3;
      }  
    paramInt3 = paramInt4;
    paramInt2 = paramInt1;
    if (shouldLayout((View)this.mLogoView))
      if (j) {
        paramInt2 = layoutChildRight((View)this.mLogoView, paramInt1, arrayOfInt, bool);
        paramInt3 = paramInt4;
      } else {
        paramInt3 = layoutChildLeft((View)this.mLogoView, paramInt4, arrayOfInt, bool);
        paramInt2 = paramInt1;
      }  
    paramBoolean = shouldLayout((View)this.mTitleTextView);
    boolean bool1 = shouldLayout((View)this.mSubtitleTextView);
    if (paramBoolean) {
      LayoutParams layoutParams = (LayoutParams)this.mTitleTextView.getLayoutParams();
      paramInt1 = layoutParams.topMargin + this.mTitleTextView.getMeasuredHeight() + layoutParams.bottomMargin + 0;
    } else {
      paramInt1 = 0;
    } 
    if (bool1) {
      LayoutParams layoutParams = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
      paramInt1 += layoutParams.topMargin + this.mSubtitleTextView.getMeasuredHeight() + layoutParams.bottomMargin;
    } 
    if (paramBoolean || bool1) {
      TextView textView1;
      TextView textView2;
      if (paramBoolean) {
        textView1 = this.mTitleTextView;
      } else {
        textView1 = this.mSubtitleTextView;
      } 
      if (bool1) {
        textView2 = this.mSubtitleTextView;
      } else {
        textView2 = this.mTitleTextView;
      } 
      LayoutParams layoutParams1 = (LayoutParams)textView1.getLayoutParams();
      LayoutParams layoutParams2 = (LayoutParams)textView2.getLayoutParams();
      if ((paramBoolean && this.mTitleTextView.getMeasuredWidth() > 0) || (bool1 && this.mSubtitleTextView.getMeasuredWidth() > 0)) {
        paramInt4 = 1;
      } else {
        paramInt4 = 0;
      } 
      k = this.mGravity & 0x70;
      if (k != 48) {
        if (k != 80) {
          k = (i3 - i1 - i2 - paramInt1) / 2;
          if (k < layoutParams1.topMargin + this.mTitleMarginTop) {
            paramInt1 = layoutParams1.topMargin + this.mTitleMarginTop;
          } else {
            i2 = i3 - i2 - paramInt1 - k - i1;
            paramInt1 = k;
            if (i2 < layoutParams1.bottomMargin + this.mTitleMarginBottom)
              paramInt1 = Math.max(0, k - layoutParams2.bottomMargin + this.mTitleMarginBottom - i2); 
          } 
          paramInt1 = i1 + paramInt1;
        } else {
          paramInt1 = i3 - i2 - layoutParams2.bottomMargin - this.mTitleMarginBottom - paramInt1;
        } 
      } else {
        paramInt1 = getPaddingTop() + layoutParams1.topMargin + this.mTitleMarginTop;
      } 
      if (j) {
        if (paramInt4 != 0) {
          j = this.mTitleMarginStart;
        } else {
          j = 0;
        } 
        j -= arrayOfInt[1];
        paramInt2 -= Math.max(0, j);
        arrayOfInt[1] = Math.max(0, -j);
        if (paramBoolean) {
          layoutParams1 = (LayoutParams)this.mTitleTextView.getLayoutParams();
          k = paramInt2 - this.mTitleTextView.getMeasuredWidth();
          j = this.mTitleTextView.getMeasuredHeight() + paramInt1;
          this.mTitleTextView.layout(k, paramInt1, paramInt2, j);
          paramInt1 = k - this.mTitleMarginEnd;
          j += layoutParams1.bottomMargin;
        } else {
          k = paramInt2;
          j = paramInt1;
          paramInt1 = k;
        } 
        if (bool1) {
          layoutParams1 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
          j += layoutParams1.topMargin;
          k = this.mSubtitleTextView.getMeasuredWidth();
          i1 = this.mSubtitleTextView.getMeasuredHeight();
          this.mSubtitleTextView.layout(paramInt2 - k, j, paramInt2, i1 + j);
          j = paramInt2 - this.mTitleMarginEnd;
          k = layoutParams1.bottomMargin;
        } else {
          j = paramInt2;
        } 
        if (paramInt4 != 0)
          paramInt2 = Math.min(paramInt1, j); 
      } else {
        if (paramInt4 != 0) {
          j = this.mTitleMarginStart;
        } else {
          j = 0;
        } 
        j -= arrayOfInt[0];
        paramInt3 += Math.max(0, j);
        arrayOfInt[0] = Math.max(0, -j);
        if (paramBoolean) {
          layoutParams1 = (LayoutParams)this.mTitleTextView.getLayoutParams();
          j = this.mTitleTextView.getMeasuredWidth() + paramInt3;
          k = this.mTitleTextView.getMeasuredHeight() + paramInt1;
          this.mTitleTextView.layout(paramInt3, paramInt1, j, k);
          j += this.mTitleMarginEnd;
          paramInt1 = k + layoutParams1.bottomMargin;
        } else {
          j = paramInt3;
        } 
        if (bool1) {
          layoutParams1 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
          k = paramInt1 + layoutParams1.topMargin;
          i1 = this.mSubtitleTextView.getMeasuredWidth() + paramInt3;
          paramInt1 = this.mSubtitleTextView.getMeasuredHeight();
          this.mSubtitleTextView.layout(paramInt3, k, i1, paramInt1 + k);
          k = i1 + this.mTitleMarginEnd;
          paramInt1 = layoutParams1.bottomMargin;
        } else {
          k = paramInt3;
        } 
        paramInt1 = paramInt3;
        paramInt3 = paramInt2;
        if (paramInt4 != 0) {
          paramInt1 = Math.max(j, k);
          paramInt3 = paramInt2;
        } 
        paramInt4 = 0;
        addCustomViewsWithGravity(this.mTempViews, 3);
        j = this.mTempViews.size();
        paramInt2 = 0;
      } 
    } 
    paramInt1 = paramInt3;
    paramInt3 = paramInt2;
    paramInt4 = 0;
    addCustomViewsWithGravity(this.mTempViews, 3);
    int j = this.mTempViews.size();
    paramInt2 = 0;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    int[] arrayOfInt = this.mTempMargins;
    boolean bool1 = ViewUtils.isLayoutRtl((View)this);
    boolean bool = false;
    if (bool1) {
      i1 = 1;
      n = 0;
    } else {
      i1 = 0;
      n = 1;
    } 
    if (shouldLayout((View)this.mNavButtonView)) {
      measureChildConstrained((View)this.mNavButtonView, paramInt1, 0, paramInt2, 0, this.mMaxButtonHeight);
      i = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins((View)this.mNavButtonView);
      m = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins((View)this.mNavButtonView));
      k = View.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
    } else {
      i = 0;
      m = 0;
      k = 0;
    } 
    int i2 = i;
    int j = m;
    int i = k;
    if (shouldLayout((View)this.mCollapseButtonView)) {
      measureChildConstrained((View)this.mCollapseButtonView, paramInt1, 0, paramInt2, 0, this.mMaxButtonHeight);
      i2 = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins((View)this.mCollapseButtonView);
      j = Math.max(m, this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins((View)this.mCollapseButtonView));
      i = View.combineMeasuredStates(k, this.mCollapseButtonView.getMeasuredState());
    } 
    int k = getCurrentContentInsetStart();
    int m = 0 + Math.max(k, i2);
    arrayOfInt[i1] = Math.max(0, k - i2);
    if (shouldLayout((View)this.mMenuView)) {
      measureChildConstrained((View)this.mMenuView, paramInt1, m, paramInt2, 0, this.mMaxButtonHeight);
      k = this.mMenuView.getMeasuredWidth() + getHorizontalMargins((View)this.mMenuView);
      j = Math.max(j, this.mMenuView.getMeasuredHeight() + getVerticalMargins((View)this.mMenuView));
      i = View.combineMeasuredStates(i, this.mMenuView.getMeasuredState());
    } else {
      k = 0;
    } 
    i2 = getCurrentContentInsetEnd();
    int i1 = m + Math.max(i2, k);
    arrayOfInt[n] = Math.max(0, i2 - k);
    int n = j;
    k = i;
    m = i1;
    if (shouldLayout(this.mExpandedActionView)) {
      m = i1 + measureChildCollapseMargins(this.mExpandedActionView, paramInt1, i1, paramInt2, 0, arrayOfInt);
      n = Math.max(j, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
      k = View.combineMeasuredStates(i, this.mExpandedActionView.getMeasuredState());
    } 
    j = n;
    i = k;
    i1 = m;
    if (shouldLayout((View)this.mLogoView)) {
      i1 = m + measureChildCollapseMargins((View)this.mLogoView, paramInt1, m, paramInt2, 0, arrayOfInt);
      j = Math.max(n, this.mLogoView.getMeasuredHeight() + getVerticalMargins((View)this.mLogoView));
      i = View.combineMeasuredStates(k, this.mLogoView.getMeasuredState());
    } 
    int i3 = getChildCount();
    k = 0;
    m = j;
    j = k;
    while (j < i3) {
      View view = getChildAt(j);
      i2 = m;
      n = i;
      k = i1;
      if (((LayoutParams)view.getLayoutParams()).mViewType == 0)
        if (!shouldLayout(view)) {
          i2 = m;
          n = i;
          k = i1;
        } else {
          k = i1 + measureChildCollapseMargins(view, paramInt1, i1, paramInt2, 0, arrayOfInt);
          i2 = Math.max(m, view.getMeasuredHeight() + getVerticalMargins(view));
          n = View.combineMeasuredStates(i, view.getMeasuredState());
        }  
      j++;
      m = i2;
      i = n;
      i1 = k;
    } 
    i2 = this.mTitleMarginTop + this.mTitleMarginBottom;
    n = this.mTitleMarginStart + this.mTitleMarginEnd;
    if (shouldLayout((View)this.mTitleTextView)) {
      measureChildCollapseMargins((View)this.mTitleTextView, paramInt1, i1 + n, paramInt2, i2, arrayOfInt);
      j = this.mTitleTextView.getMeasuredWidth();
      i3 = getHorizontalMargins((View)this.mTitleTextView);
      k = this.mTitleTextView.getMeasuredHeight();
      int i4 = getVerticalMargins((View)this.mTitleTextView);
      i = View.combineMeasuredStates(i, this.mTitleTextView.getMeasuredState());
      k += i4;
      j += i3;
    } else {
      j = 0;
      k = 0;
    } 
    if (shouldLayout((View)this.mSubtitleTextView)) {
      j = Math.max(j, measureChildCollapseMargins((View)this.mSubtitleTextView, paramInt1, i1 + n, paramInt2, k + i2, arrayOfInt));
      k += this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins((View)this.mSubtitleTextView);
      i = View.combineMeasuredStates(i, this.mSubtitleTextView.getMeasuredState());
    } 
    m = Math.max(m, k);
    i3 = getPaddingLeft();
    i2 = getPaddingRight();
    k = getPaddingTop();
    n = getPaddingBottom();
    j = View.resolveSizeAndState(Math.max(i1 + j + i3 + i2, getSuggestedMinimumWidth()), paramInt1, 0xFF000000 & i);
    paramInt1 = View.resolveSizeAndState(Math.max(m + k + n, getSuggestedMinimumHeight()), paramInt2, i << 16);
    if (shouldCollapse())
      paramInt1 = bool; 
    setMeasuredDimension(j, paramInt1);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    ActionMenuView actionMenuView = this.mMenuView;
    if (actionMenuView != null) {
      MenuBuilder menuBuilder = actionMenuView.peekMenu();
    } else {
      actionMenuView = null;
    } 
    if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && actionMenuView != null) {
      MenuItem menuItem = actionMenuView.findItem(savedState.expandedMenuItemId);
      if (menuItem != null)
        menuItem.expandActionView(); 
    } 
    if (savedState.isOverflowOpen)
      postShowOverflowMenu(); 
  }
  
  public void onRtlPropertiesChanged(int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      super.onRtlPropertiesChanged(paramInt); 
    ensureContentInsets();
    RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
    boolean bool = true;
    if (paramInt != 1)
      bool = false; 
    rtlSpacingHelper.setDirection(bool);
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
    if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null)
      savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId(); 
    savedState.isOverflowOpen = isOverflowMenuShowing();
    return (Parcelable)savedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getActionMasked();
    if (i == 0)
      this.mEatingTouch = false; 
    if (!this.mEatingTouch) {
      boolean bool = super.onTouchEvent(paramMotionEvent);
      if (i == 0 && !bool)
        this.mEatingTouch = true; 
    } 
    if (i == 1 || i == 3)
      this.mEatingTouch = false; 
    return true;
  }
  
  void removeChildrenForExpandedActionView() {
    for (int i = getChildCount() - 1; i >= 0; i--) {
      View view = getChildAt(i);
      if (((LayoutParams)view.getLayoutParams()).mViewType != 2 && view != this.mMenuView) {
        removeViewAt(i);
        this.mHiddenViews.add(view);
      } 
    } 
  }
  
  public void setCollapseContentDescription(int paramInt) {
    CharSequence charSequence;
    if (paramInt != 0) {
      charSequence = getContext().getText(paramInt);
    } else {
      charSequence = null;
    } 
    setCollapseContentDescription(charSequence);
  }
  
  public void setCollapseContentDescription(CharSequence paramCharSequence) {
    if (!TextUtils.isEmpty(paramCharSequence))
      ensureCollapseButtonView(); 
    ImageButton imageButton = this.mCollapseButtonView;
    if (imageButton != null)
      imageButton.setContentDescription(paramCharSequence); 
  }
  
  public void setCollapseIcon(int paramInt) {
    setCollapseIcon(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setCollapseIcon(Drawable paramDrawable) {
    if (paramDrawable != null) {
      ensureCollapseButtonView();
      this.mCollapseButtonView.setImageDrawable(paramDrawable);
    } else {
      ImageButton imageButton = this.mCollapseButtonView;
      if (imageButton != null)
        imageButton.setImageDrawable(this.mCollapseIcon); 
    } 
  }
  
  public void setCollapsible(boolean paramBoolean) {
    this.mCollapsible = paramBoolean;
    requestLayout();
  }
  
  public void setContentInsetEndWithActions(int paramInt) {
    int i = paramInt;
    if (paramInt < 0)
      i = Integer.MIN_VALUE; 
    if (i != this.mContentInsetEndWithActions) {
      this.mContentInsetEndWithActions = i;
      if (getNavigationIcon() != null)
        requestLayout(); 
    } 
  }
  
  public void setContentInsetStartWithNavigation(int paramInt) {
    int i = paramInt;
    if (paramInt < 0)
      i = Integer.MIN_VALUE; 
    if (i != this.mContentInsetStartWithNavigation) {
      this.mContentInsetStartWithNavigation = i;
      if (getNavigationIcon() != null)
        requestLayout(); 
    } 
  }
  
  public void setContentInsetsAbsolute(int paramInt1, int paramInt2) {
    ensureContentInsets();
    this.mContentInsets.setAbsolute(paramInt1, paramInt2);
  }
  
  public void setContentInsetsRelative(int paramInt1, int paramInt2) {
    ensureContentInsets();
    this.mContentInsets.setRelative(paramInt1, paramInt2);
  }
  
  public void setLogo(int paramInt) {
    setLogo(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setLogo(Drawable paramDrawable) {
    if (paramDrawable != null) {
      ensureLogoView();
      if (!isChildOrHidden((View)this.mLogoView))
        addSystemView((View)this.mLogoView, true); 
    } else {
      ImageView imageView1 = this.mLogoView;
      if (imageView1 != null && isChildOrHidden((View)imageView1)) {
        removeView((View)this.mLogoView);
        this.mHiddenViews.remove(this.mLogoView);
      } 
    } 
    ImageView imageView = this.mLogoView;
    if (imageView != null)
      imageView.setImageDrawable(paramDrawable); 
  }
  
  public void setLogoDescription(int paramInt) {
    setLogoDescription(getContext().getText(paramInt));
  }
  
  public void setLogoDescription(CharSequence paramCharSequence) {
    if (!TextUtils.isEmpty(paramCharSequence))
      ensureLogoView(); 
    ImageView imageView = this.mLogoView;
    if (imageView != null)
      imageView.setContentDescription(paramCharSequence); 
  }
  
  public void setMenu(MenuBuilder paramMenuBuilder, ActionMenuPresenter paramActionMenuPresenter) {
    if (paramMenuBuilder == null && this.mMenuView == null)
      return; 
    ensureMenuView();
    MenuBuilder menuBuilder = this.mMenuView.peekMenu();
    if (menuBuilder == paramMenuBuilder)
      return; 
    if (menuBuilder != null) {
      menuBuilder.removeMenuPresenter((MenuPresenter)this.mOuterActionMenuPresenter);
      menuBuilder.removeMenuPresenter(this.mExpandedMenuPresenter);
    } 
    if (this.mExpandedMenuPresenter == null)
      this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter(); 
    paramActionMenuPresenter.setExpandedActionViewsExclusive(true);
    if (paramMenuBuilder != null) {
      paramMenuBuilder.addMenuPresenter((MenuPresenter)paramActionMenuPresenter, this.mPopupContext);
      paramMenuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
    } else {
      paramActionMenuPresenter.initForMenu(this.mPopupContext, null);
      this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
      paramActionMenuPresenter.updateMenuView(true);
      this.mExpandedMenuPresenter.updateMenuView(true);
    } 
    this.mMenuView.setPopupTheme(this.mPopupTheme);
    this.mMenuView.setPresenter(paramActionMenuPresenter);
    this.mOuterActionMenuPresenter = paramActionMenuPresenter;
  }
  
  public void setMenuCallbacks(MenuPresenter.Callback paramCallback, MenuBuilder.Callback paramCallback1) {
    this.mActionMenuPresenterCallback = paramCallback;
    this.mMenuBuilderCallback = paramCallback1;
    ActionMenuView actionMenuView = this.mMenuView;
    if (actionMenuView != null)
      actionMenuView.setMenuCallbacks(paramCallback, paramCallback1); 
  }
  
  public void setNavigationContentDescription(int paramInt) {
    CharSequence charSequence;
    if (paramInt != 0) {
      charSequence = getContext().getText(paramInt);
    } else {
      charSequence = null;
    } 
    setNavigationContentDescription(charSequence);
  }
  
  public void setNavigationContentDescription(CharSequence paramCharSequence) {
    if (!TextUtils.isEmpty(paramCharSequence))
      ensureNavButtonView(); 
    ImageButton imageButton = this.mNavButtonView;
    if (imageButton != null)
      imageButton.setContentDescription(paramCharSequence); 
  }
  
  public void setNavigationIcon(int paramInt) {
    setNavigationIcon(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setNavigationIcon(Drawable paramDrawable) {
    if (paramDrawable != null) {
      ensureNavButtonView();
      if (!isChildOrHidden((View)this.mNavButtonView))
        addSystemView((View)this.mNavButtonView, true); 
    } else {
      ImageButton imageButton1 = this.mNavButtonView;
      if (imageButton1 != null && isChildOrHidden((View)imageButton1)) {
        removeView((View)this.mNavButtonView);
        this.mHiddenViews.remove(this.mNavButtonView);
      } 
    } 
    ImageButton imageButton = this.mNavButtonView;
    if (imageButton != null)
      imageButton.setImageDrawable(paramDrawable); 
  }
  
  public void setNavigationOnClickListener(View.OnClickListener paramOnClickListener) {
    ensureNavButtonView();
    this.mNavButtonView.setOnClickListener(paramOnClickListener);
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener) {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable) {
    ensureMenu();
    this.mMenuView.setOverflowIcon(paramDrawable);
  }
  
  public void setPopupTheme(int paramInt) {
    if (this.mPopupTheme != paramInt) {
      this.mPopupTheme = paramInt;
      if (paramInt == 0) {
        this.mPopupContext = getContext();
      } else {
        this.mPopupContext = (Context)new ContextThemeWrapper(getContext(), paramInt);
      } 
    } 
  }
  
  public void setSubtitle(int paramInt) {
    setSubtitle(getContext().getText(paramInt));
  }
  
  public void setSubtitle(CharSequence paramCharSequence) {
    if (!TextUtils.isEmpty(paramCharSequence)) {
      if (this.mSubtitleTextView == null) {
        Context context = getContext();
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        this.mSubtitleTextView = appCompatTextView;
        appCompatTextView.setSingleLine();
        this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        int i = this.mSubtitleTextAppearance;
        if (i != 0)
          this.mSubtitleTextView.setTextAppearance(context, i); 
        ColorStateList colorStateList = this.mSubtitleTextColor;
        if (colorStateList != null)
          this.mSubtitleTextView.setTextColor(colorStateList); 
      } 
      if (!isChildOrHidden((View)this.mSubtitleTextView))
        addSystemView((View)this.mSubtitleTextView, true); 
    } else {
      TextView textView1 = this.mSubtitleTextView;
      if (textView1 != null && isChildOrHidden((View)textView1)) {
        removeView((View)this.mSubtitleTextView);
        this.mHiddenViews.remove(this.mSubtitleTextView);
      } 
    } 
    TextView textView = this.mSubtitleTextView;
    if (textView != null)
      textView.setText(paramCharSequence); 
    this.mSubtitleText = paramCharSequence;
  }
  
  public void setSubtitleTextAppearance(Context paramContext, int paramInt) {
    this.mSubtitleTextAppearance = paramInt;
    TextView textView = this.mSubtitleTextView;
    if (textView != null)
      textView.setTextAppearance(paramContext, paramInt); 
  }
  
  public void setSubtitleTextColor(int paramInt) {
    setSubtitleTextColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setSubtitleTextColor(ColorStateList paramColorStateList) {
    this.mSubtitleTextColor = paramColorStateList;
    TextView textView = this.mSubtitleTextView;
    if (textView != null)
      textView.setTextColor(paramColorStateList); 
  }
  
  public void setTitle(int paramInt) {
    setTitle(getContext().getText(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    if (!TextUtils.isEmpty(paramCharSequence)) {
      if (this.mTitleTextView == null) {
        Context context = getContext();
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        this.mTitleTextView = appCompatTextView;
        appCompatTextView.setSingleLine();
        this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        int i = this.mTitleTextAppearance;
        if (i != 0)
          this.mTitleTextView.setTextAppearance(context, i); 
        ColorStateList colorStateList = this.mTitleTextColor;
        if (colorStateList != null)
          this.mTitleTextView.setTextColor(colorStateList); 
      } 
      if (!isChildOrHidden((View)this.mTitleTextView))
        addSystemView((View)this.mTitleTextView, true); 
    } else {
      TextView textView1 = this.mTitleTextView;
      if (textView1 != null && isChildOrHidden((View)textView1)) {
        removeView((View)this.mTitleTextView);
        this.mHiddenViews.remove(this.mTitleTextView);
      } 
    } 
    TextView textView = this.mTitleTextView;
    if (textView != null)
      textView.setText(paramCharSequence); 
    this.mTitleText = paramCharSequence;
  }
  
  public void setTitleMargin(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mTitleMarginStart = paramInt1;
    this.mTitleMarginTop = paramInt2;
    this.mTitleMarginEnd = paramInt3;
    this.mTitleMarginBottom = paramInt4;
    requestLayout();
  }
  
  public void setTitleMarginBottom(int paramInt) {
    this.mTitleMarginBottom = paramInt;
    requestLayout();
  }
  
  public void setTitleMarginEnd(int paramInt) {
    this.mTitleMarginEnd = paramInt;
    requestLayout();
  }
  
  public void setTitleMarginStart(int paramInt) {
    this.mTitleMarginStart = paramInt;
    requestLayout();
  }
  
  public void setTitleMarginTop(int paramInt) {
    this.mTitleMarginTop = paramInt;
    requestLayout();
  }
  
  public void setTitleTextAppearance(Context paramContext, int paramInt) {
    this.mTitleTextAppearance = paramInt;
    TextView textView = this.mTitleTextView;
    if (textView != null)
      textView.setTextAppearance(paramContext, paramInt); 
  }
  
  public void setTitleTextColor(int paramInt) {
    setTitleTextColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setTitleTextColor(ColorStateList paramColorStateList) {
    this.mTitleTextColor = paramColorStateList;
    TextView textView = this.mTitleTextView;
    if (textView != null)
      textView.setTextColor(paramColorStateList); 
  }
  
  public boolean showOverflowMenu() {
    boolean bool;
    ActionMenuView actionMenuView = this.mMenuView;
    if (actionMenuView != null && actionMenuView.showOverflowMenu()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private class ExpandedActionViewMenuPresenter implements MenuPresenter {
    MenuItemImpl mCurrentExpandedItem;
    
    MenuBuilder mMenu;
    
    final Toolbar this$0;
    
    public boolean collapseItemActionView(MenuBuilder param1MenuBuilder, MenuItemImpl param1MenuItemImpl) {
      if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView)
        ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed(); 
      Toolbar toolbar = Toolbar.this;
      toolbar.removeView(toolbar.mExpandedActionView);
      toolbar = Toolbar.this;
      toolbar.removeView((View)toolbar.mCollapseButtonView);
      Toolbar.this.mExpandedActionView = null;
      Toolbar.this.addChildrenForExpandedActionView();
      this.mCurrentExpandedItem = null;
      Toolbar.this.requestLayout();
      param1MenuItemImpl.setActionViewExpanded(false);
      return true;
    }
    
    public boolean expandItemActionView(MenuBuilder param1MenuBuilder, MenuItemImpl param1MenuItemImpl) {
      Toolbar.this.ensureCollapseButtonView();
      ViewParent viewParent2 = Toolbar.this.mCollapseButtonView.getParent();
      Toolbar toolbar1 = Toolbar.this;
      if (viewParent2 != toolbar1) {
        if (viewParent2 instanceof ViewGroup)
          ((ViewGroup)viewParent2).removeView((View)toolbar1.mCollapseButtonView); 
        toolbar1 = Toolbar.this;
        toolbar1.addView((View)toolbar1.mCollapseButtonView);
      } 
      Toolbar.this.mExpandedActionView = param1MenuItemImpl.getActionView();
      this.mCurrentExpandedItem = param1MenuItemImpl;
      ViewParent viewParent1 = Toolbar.this.mExpandedActionView.getParent();
      Toolbar toolbar2 = Toolbar.this;
      if (viewParent1 != toolbar2) {
        if (viewParent1 instanceof ViewGroup)
          ((ViewGroup)viewParent1).removeView(toolbar2.mExpandedActionView); 
        Toolbar.LayoutParams layoutParams = Toolbar.this.generateDefaultLayoutParams();
        layoutParams.gravity = 0x800003 | Toolbar.this.mButtonGravity & 0x70;
        layoutParams.mViewType = 2;
        Toolbar.this.mExpandedActionView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        Toolbar toolbar = Toolbar.this;
        toolbar.addView(toolbar.mExpandedActionView);
      } 
      Toolbar.this.removeChildrenForExpandedActionView();
      Toolbar.this.requestLayout();
      param1MenuItemImpl.setActionViewExpanded(true);
      if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView)
        ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded(); 
      return true;
    }
    
    public boolean flagActionItems() {
      return false;
    }
    
    public int getId() {
      return 0;
    }
    
    public MenuView getMenuView(ViewGroup param1ViewGroup) {
      return null;
    }
    
    public void initForMenu(Context param1Context, MenuBuilder param1MenuBuilder) {
      MenuBuilder menuBuilder = this.mMenu;
      if (menuBuilder != null) {
        MenuItemImpl menuItemImpl = this.mCurrentExpandedItem;
        if (menuItemImpl != null)
          menuBuilder.collapseItemActionView(menuItemImpl); 
      } 
      this.mMenu = param1MenuBuilder;
    }
    
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {}
    
    public void onRestoreInstanceState(Parcelable param1Parcelable) {}
    
    public Parcelable onSaveInstanceState() {
      return null;
    }
    
    public boolean onSubMenuSelected(SubMenuBuilder param1SubMenuBuilder) {
      return false;
    }
    
    public void setCallback(MenuPresenter.Callback param1Callback) {}
    
    public void updateMenuView(boolean param1Boolean) {
      if (this.mCurrentExpandedItem != null) {
        MenuBuilder menuBuilder = this.mMenu;
        boolean bool2 = false;
        boolean bool1 = bool2;
        if (menuBuilder != null) {
          int i = menuBuilder.size();
          byte b = 0;
          while (true) {
            bool1 = bool2;
            if (b < i) {
              if (this.mMenu.getItem(b) == this.mCurrentExpandedItem) {
                bool1 = true;
                break;
              } 
              b++;
              continue;
            } 
            break;
          } 
        } 
        if (!bool1)
          collapseItemActionView(this.mMenu, this.mCurrentExpandedItem); 
      } 
    }
  }
  
  public static class LayoutParams extends ActionBar.LayoutParams {
    static final int CUSTOM = 0;
    
    static final int EXPANDED = 2;
    
    static final int SYSTEM = 1;
    
    int mViewType = 0;
    
    public LayoutParams(int param1Int) {
      this(-2, -1, param1Int);
    }
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
      this.gravity = 8388627;
    }
    
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3) {
      super(param1Int1, param1Int2);
      this.gravity = param1Int3;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super((ViewGroup.LayoutParams)param1MarginLayoutParams);
      copyMarginsFromCompat(param1MarginLayoutParams);
    }
    
    public LayoutParams(ActionBar.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.mViewType = param1LayoutParams.mViewType;
    }
    
    void copyMarginsFromCompat(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      this.leftMargin = param1MarginLayoutParams.leftMargin;
      this.topMargin = param1MarginLayoutParams.topMargin;
      this.rightMargin = param1MarginLayoutParams.rightMargin;
      this.bottomMargin = param1MarginLayoutParams.bottomMargin;
    }
  }
  
  public static interface OnMenuItemClickListener {
    boolean onMenuItemClick(MenuItem param1MenuItem);
  }
  
  public static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public Toolbar.SavedState createFromParcel(Parcel param2Parcel) {
          return new Toolbar.SavedState(param2Parcel, null);
        }
        
        public Toolbar.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new Toolbar.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public Toolbar.SavedState[] newArray(int param2Int) {
          return new Toolbar.SavedState[param2Int];
        }
      };
    
    int expandedMenuItemId;
    
    boolean isOverflowOpen;
    
    public SavedState(Parcel param1Parcel) {
      this(param1Parcel, null);
    }
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      boolean bool;
      this.expandedMenuItemId = param1Parcel.readInt();
      if (param1Parcel.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.isOverflowOpen = bool;
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.expandedMenuItemId);
      param1Parcel.writeInt(this.isOverflowOpen);
    }
  }
  
  class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public Toolbar.SavedState createFromParcel(Parcel param1Parcel) {
      return new Toolbar.SavedState(param1Parcel, null);
    }
    
    public Toolbar.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new Toolbar.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public Toolbar.SavedState[] newArray(int param1Int) {
      return new Toolbar.SavedState[param1Int];
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\Toolbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */