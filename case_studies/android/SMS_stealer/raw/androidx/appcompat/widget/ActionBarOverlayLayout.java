package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.OverScroller;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.core.graphics.Insets;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActionBarOverlayLayout extends ViewGroup implements DecorContentParent, NestedScrollingParent, NestedScrollingParent2, NestedScrollingParent3 {
  private static final int ACTION_BAR_ANIMATE_DELAY = 600;
  
  static final int[] ATTRS = new int[] { R.attr.actionBarSize, 16842841 };
  
  private static final String TAG = "ActionBarOverlayLayout";
  
  private int mActionBarHeight;
  
  ActionBarContainer mActionBarTop;
  
  private ActionBarVisibilityCallback mActionBarVisibilityCallback;
  
  private final Runnable mAddActionBarHideOffset = new Runnable() {
      final ActionBarOverlayLayout this$0;
      
      public void run() {
        ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
        ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
        actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(-ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener((Animator.AnimatorListener)ActionBarOverlayLayout.this.mTopAnimatorListener);
      }
    };
  
  boolean mAnimatingForFling;
  
  private final Rect mBaseContentInsets = new Rect();
  
  private WindowInsetsCompat mBaseInnerInsets = WindowInsetsCompat.CONSUMED;
  
  private final Rect mBaseInnerInsetsRect = new Rect();
  
  private ContentFrameLayout mContent;
  
  private final Rect mContentInsets = new Rect();
  
  ViewPropertyAnimator mCurrentActionBarTopAnimator;
  
  private DecorToolbar mDecorToolbar;
  
  private OverScroller mFlingEstimator;
  
  private boolean mHasNonEmbeddedTabs;
  
  private boolean mHideOnContentScroll;
  
  private int mHideOnContentScrollReference;
  
  private boolean mIgnoreWindowContentOverlay;
  
  private WindowInsetsCompat mInnerInsets = WindowInsetsCompat.CONSUMED;
  
  private final Rect mInnerInsetsRect = new Rect();
  
  private final Rect mLastBaseContentInsets = new Rect();
  
  private WindowInsetsCompat mLastBaseInnerInsets = WindowInsetsCompat.CONSUMED;
  
  private final Rect mLastBaseInnerInsetsRect = new Rect();
  
  private WindowInsetsCompat mLastInnerInsets = WindowInsetsCompat.CONSUMED;
  
  private final Rect mLastInnerInsetsRect = new Rect();
  
  private int mLastSystemUiVisibility;
  
  private boolean mOverlayMode;
  
  private final NestedScrollingParentHelper mParentHelper;
  
  private final Runnable mRemoveActionBarHideOffset = new Runnable() {
      final ActionBarOverlayLayout this$0;
      
      public void run() {
        ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
        ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
        actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(0.0F).setListener((Animator.AnimatorListener)ActionBarOverlayLayout.this.mTopAnimatorListener);
      }
    };
  
  final AnimatorListenerAdapter mTopAnimatorListener = new AnimatorListenerAdapter() {
      final ActionBarOverlayLayout this$0;
      
      public void onAnimationCancel(Animator param1Animator) {
        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
        ActionBarOverlayLayout.this.mAnimatingForFling = false;
      }
      
      public void onAnimationEnd(Animator param1Animator) {
        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
        ActionBarOverlayLayout.this.mAnimatingForFling = false;
      }
    };
  
  private Drawable mWindowContentOverlay;
  
  private int mWindowVisibility = 0;
  
  public ActionBarOverlayLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ActionBarOverlayLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext);
    this.mParentHelper = new NestedScrollingParentHelper(this);
  }
  
  private void addActionBarHideOffset() {
    haltActionBarHideOffsetAnimations();
    this.mAddActionBarHideOffset.run();
  }
  
  private boolean applyInsets(View paramView, Rect paramRect, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    boolean bool1;
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    boolean bool2 = true;
    if (paramBoolean1 && layoutParams.leftMargin != paramRect.left) {
      layoutParams.leftMargin = paramRect.left;
      bool1 = true;
    } else {
      bool1 = false;
    } 
    paramBoolean1 = bool1;
    if (paramBoolean2) {
      paramBoolean1 = bool1;
      if (layoutParams.topMargin != paramRect.top) {
        layoutParams.topMargin = paramRect.top;
        paramBoolean1 = true;
      } 
    } 
    paramBoolean2 = paramBoolean1;
    if (paramBoolean4) {
      paramBoolean2 = paramBoolean1;
      if (layoutParams.rightMargin != paramRect.right) {
        layoutParams.rightMargin = paramRect.right;
        paramBoolean2 = true;
      } 
    } 
    if (paramBoolean3 && layoutParams.bottomMargin != paramRect.bottom) {
      layoutParams.bottomMargin = paramRect.bottom;
      paramBoolean1 = bool2;
    } else {
      paramBoolean1 = paramBoolean2;
    } 
    return paramBoolean1;
  }
  
  private DecorToolbar getDecorToolbar(View paramView) {
    if (paramView instanceof DecorToolbar)
      return (DecorToolbar)paramView; 
    if (paramView instanceof Toolbar)
      return ((Toolbar)paramView).getWrapper(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Can't make a decor toolbar out of ");
    stringBuilder.append(paramView.getClass().getSimpleName());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  private void init(Context paramContext) {
    TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(ATTRS);
    boolean bool2 = false;
    this.mActionBarHeight = typedArray.getDimensionPixelSize(0, 0);
    Drawable drawable = typedArray.getDrawable(1);
    this.mWindowContentOverlay = drawable;
    if (drawable == null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    setWillNotDraw(bool1);
    typedArray.recycle();
    boolean bool1 = bool2;
    if ((paramContext.getApplicationInfo()).targetSdkVersion < 19)
      bool1 = true; 
    this.mIgnoreWindowContentOverlay = bool1;
    this.mFlingEstimator = new OverScroller(paramContext);
  }
  
  private void postAddActionBarHideOffset() {
    haltActionBarHideOffsetAnimations();
    postDelayed(this.mAddActionBarHideOffset, 600L);
  }
  
  private void postRemoveActionBarHideOffset() {
    haltActionBarHideOffsetAnimations();
    postDelayed(this.mRemoveActionBarHideOffset, 600L);
  }
  
  private void removeActionBarHideOffset() {
    haltActionBarHideOffsetAnimations();
    this.mRemoveActionBarHideOffset.run();
  }
  
  private boolean shouldHideActionBarOnFling(float paramFloat) {
    boolean bool;
    this.mFlingEstimator.fling(0, 0, 0, (int)paramFloat, 0, 0, -2147483648, 2147483647);
    if (this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean canShowOverflowMenu() {
    pullChildren();
    return this.mDecorToolbar.canShowOverflowMenu();
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void dismissPopups() {
    pullChildren();
    this.mDecorToolbar.dismissPopupMenus();
  }
  
  public void draw(Canvas paramCanvas) {
    super.draw(paramCanvas);
    if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
      byte b;
      if (this.mActionBarTop.getVisibility() == 0) {
        b = (int)(this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5F);
      } else {
        b = 0;
      } 
      this.mWindowContentOverlay.setBounds(0, b, getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + b);
      this.mWindowContentOverlay.draw(paramCanvas);
    } 
  }
  
  protected boolean fitSystemWindows(Rect paramRect) {
    if (Build.VERSION.SDK_INT >= 21)
      return super.fitSystemWindows(paramRect); 
    pullChildren();
    boolean bool = applyInsets((View)this.mActionBarTop, paramRect, true, true, false, true);
    this.mBaseInnerInsetsRect.set(paramRect);
    ViewUtils.computeFitSystemWindows((View)this, this.mBaseInnerInsetsRect, this.mBaseContentInsets);
    if (!this.mLastBaseInnerInsetsRect.equals(this.mBaseInnerInsetsRect)) {
      this.mLastBaseInnerInsetsRect.set(this.mBaseInnerInsetsRect);
      bool = true;
    } 
    if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
      this.mLastBaseContentInsets.set(this.mBaseContentInsets);
      bool = true;
    } 
    if (bool)
      requestLayout(); 
    return true;
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-1, -1);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (ViewGroup.LayoutParams)new LayoutParams(paramLayoutParams);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  public int getActionBarHideOffset() {
    boolean bool;
    ActionBarContainer actionBarContainer = this.mActionBarTop;
    if (actionBarContainer != null) {
      bool = -((int)actionBarContainer.getTranslationY());
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getNestedScrollAxes() {
    return this.mParentHelper.getNestedScrollAxes();
  }
  
  public CharSequence getTitle() {
    pullChildren();
    return this.mDecorToolbar.getTitle();
  }
  
  void haltActionBarHideOffsetAnimations() {
    removeCallbacks(this.mRemoveActionBarHideOffset);
    removeCallbacks(this.mAddActionBarHideOffset);
    ViewPropertyAnimator viewPropertyAnimator = this.mCurrentActionBarTopAnimator;
    if (viewPropertyAnimator != null)
      viewPropertyAnimator.cancel(); 
  }
  
  public boolean hasIcon() {
    pullChildren();
    return this.mDecorToolbar.hasIcon();
  }
  
  public boolean hasLogo() {
    pullChildren();
    return this.mDecorToolbar.hasLogo();
  }
  
  public boolean hideOverflowMenu() {
    pullChildren();
    return this.mDecorToolbar.hideOverflowMenu();
  }
  
  public void initFeature(int paramInt) {
    pullChildren();
    if (paramInt != 2) {
      if (paramInt != 5) {
        if (paramInt == 109)
          setOverlayMode(true); 
      } else {
        this.mDecorToolbar.initIndeterminateProgress();
      } 
    } else {
      this.mDecorToolbar.initProgress();
    } 
  }
  
  public boolean isHideOnContentScrollEnabled() {
    return this.mHideOnContentScroll;
  }
  
  public boolean isInOverlayMode() {
    return this.mOverlayMode;
  }
  
  public boolean isOverflowMenuShowPending() {
    pullChildren();
    return this.mDecorToolbar.isOverflowMenuShowPending();
  }
  
  public boolean isOverflowMenuShowing() {
    pullChildren();
    return this.mDecorToolbar.isOverflowMenuShowing();
  }
  
  public WindowInsets onApplyWindowInsets(WindowInsets paramWindowInsets) {
    pullChildren();
    WindowInsetsCompat windowInsetsCompat1 = WindowInsetsCompat.toWindowInsetsCompat(paramWindowInsets);
    Rect rect = new Rect(windowInsetsCompat1.getSystemWindowInsetLeft(), windowInsetsCompat1.getSystemWindowInsetTop(), windowInsetsCompat1.getSystemWindowInsetRight(), windowInsetsCompat1.getSystemWindowInsetBottom());
    boolean bool1 = applyInsets((View)this.mActionBarTop, rect, true, true, false, true);
    ViewCompat.computeSystemWindowInsets((View)this, windowInsetsCompat1, this.mBaseContentInsets);
    WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat1.inset(this.mBaseContentInsets.left, this.mBaseContentInsets.top, this.mBaseContentInsets.right, this.mBaseContentInsets.bottom);
    this.mBaseInnerInsets = windowInsetsCompat2;
    boolean bool2 = this.mLastBaseInnerInsets.equals(windowInsetsCompat2);
    boolean bool = true;
    if (!bool2) {
      this.mLastBaseInnerInsets = this.mBaseInnerInsets;
      bool1 = true;
    } 
    if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
      this.mLastBaseContentInsets.set(this.mBaseContentInsets);
      bool1 = bool;
    } 
    if (bool1)
      requestLayout(); 
    return windowInsetsCompat1.consumeDisplayCutout().consumeSystemWindowInsets().consumeStableInsets().toWindowInsets();
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    init(getContext());
    ViewCompat.requestApplyInsets((View)this);
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    haltActionBarHideOffsetAnimations();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt2 = getChildCount();
    paramInt4 = getPaddingLeft();
    paramInt3 = getPaddingTop();
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
      View view = getChildAt(paramInt1);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int m = view.getMeasuredWidth();
        int j = view.getMeasuredHeight();
        int k = layoutParams.leftMargin + paramInt4;
        int i = layoutParams.topMargin + paramInt3;
        view.layout(k, i, m + k, j + i);
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    pullChildren();
    measureChildWithMargins((View)this.mActionBarTop, paramInt1, 0, paramInt2, 0);
    LayoutParams layoutParams = (LayoutParams)this.mActionBarTop.getLayoutParams();
    int i1 = Math.max(0, this.mActionBarTop.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
    int n = Math.max(0, this.mActionBarTop.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
    int m = View.combineMeasuredStates(0, this.mActionBarTop.getMeasuredState());
    if ((ViewCompat.getWindowSystemUiVisibility((View)this) & 0x100) != 0) {
      j = 1;
    } else {
      j = 0;
    } 
    if (j) {
      int i2 = this.mActionBarHeight;
      i = i2;
      if (this.mHasNonEmbeddedTabs) {
        i = i2;
        if (this.mActionBarTop.getTabContainer() != null)
          i = i2 + this.mActionBarHeight; 
      } 
    } else if (this.mActionBarTop.getVisibility() != 8) {
      i = this.mActionBarTop.getMeasuredHeight();
    } else {
      i = 0;
    } 
    this.mContentInsets.set(this.mBaseContentInsets);
    if (Build.VERSION.SDK_INT >= 21) {
      this.mInnerInsets = this.mBaseInnerInsets;
    } else {
      this.mInnerInsetsRect.set(this.mBaseInnerInsetsRect);
    } 
    if (!this.mOverlayMode && !j) {
      Rect rect = this.mContentInsets;
      rect.top += i;
      rect = this.mContentInsets;
      rect.bottom += 0;
      if (Build.VERSION.SDK_INT >= 21)
        this.mInnerInsets = this.mInnerInsets.inset(0, i, 0, 0); 
    } else if (Build.VERSION.SDK_INT >= 21) {
      Insets insets = Insets.of(this.mInnerInsets.getSystemWindowInsetLeft(), this.mInnerInsets.getSystemWindowInsetTop() + i, this.mInnerInsets.getSystemWindowInsetRight(), this.mInnerInsets.getSystemWindowInsetBottom() + 0);
      this.mInnerInsets = (new WindowInsetsCompat.Builder(this.mInnerInsets)).setSystemWindowInsets(insets).build();
    } else {
      Rect rect = this.mInnerInsetsRect;
      rect.top += i;
      rect = this.mInnerInsetsRect;
      rect.bottom += 0;
    } 
    applyInsets((View)this.mContent, this.mContentInsets, true, true, true, true);
    if (Build.VERSION.SDK_INT >= 21 && !this.mLastInnerInsets.equals(this.mInnerInsets)) {
      WindowInsetsCompat windowInsetsCompat = this.mInnerInsets;
      this.mLastInnerInsets = windowInsetsCompat;
      ViewCompat.dispatchApplyWindowInsets((View)this.mContent, windowInsetsCompat);
    } else if (Build.VERSION.SDK_INT < 21 && !this.mLastInnerInsetsRect.equals(this.mInnerInsetsRect)) {
      this.mLastInnerInsetsRect.set(this.mInnerInsetsRect);
      this.mContent.dispatchFitSystemWindows(this.mInnerInsetsRect);
    } 
    measureChildWithMargins((View)this.mContent, paramInt1, 0, paramInt2, 0);
    layoutParams = (LayoutParams)this.mContent.getLayoutParams();
    int i = Math.max(i1, this.mContent.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
    n = Math.max(n, this.mContent.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
    int k = View.combineMeasuredStates(m, this.mContent.getMeasuredState());
    int j = getPaddingLeft();
    m = getPaddingRight();
    n = Math.max(n + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight());
    setMeasuredDimension(View.resolveSizeAndState(Math.max(i + j + m, getSuggestedMinimumWidth()), paramInt1, k), View.resolveSizeAndState(n, paramInt2, k << 16));
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    if (!this.mHideOnContentScroll || !paramBoolean)
      return false; 
    if (shouldHideActionBarOnFling(paramFloat2)) {
      addActionBarHideOffset();
    } else {
      removeActionBarHideOffset();
    } 
    this.mAnimatingForFling = true;
    return true;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2) {
    return false;
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint) {}
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
    if (paramInt3 == 0)
      onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfint); 
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt1 = this.mHideOnContentScrollReference + paramInt2;
    this.mHideOnContentScrollReference = paramInt1;
    setActionBarHideOffset(paramInt1);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramInt5 == 0)
      onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
    onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt) {
    this.mParentHelper.onNestedScrollAccepted(paramView1, paramView2, paramInt);
    this.mHideOnContentScrollReference = getActionBarHideOffset();
    haltActionBarHideOffsetAnimations();
    ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
    if (actionBarVisibilityCallback != null)
      actionBarVisibilityCallback.onContentScrollStarted(); 
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2) {
    if (paramInt2 == 0)
      onNestedScrollAccepted(paramView1, paramView2, paramInt1); 
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt) {
    return ((paramInt & 0x2) == 0 || this.mActionBarTop.getVisibility() != 0) ? false : this.mHideOnContentScroll;
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2) {
    boolean bool;
    if (paramInt2 == 0 && onStartNestedScroll(paramView1, paramView2, paramInt1)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void onStopNestedScroll(View paramView) {
    if (this.mHideOnContentScroll && !this.mAnimatingForFling)
      if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
        postRemoveActionBarHideOffset();
      } else {
        postAddActionBarHideOffset();
      }  
    ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
    if (actionBarVisibilityCallback != null)
      actionBarVisibilityCallback.onContentScrollStopped(); 
  }
  
  public void onStopNestedScroll(View paramView, int paramInt) {
    if (paramInt == 0)
      onStopNestedScroll(paramView); 
  }
  
  public void onWindowSystemUiVisibilityChanged(int paramInt) {
    boolean bool1;
    if (Build.VERSION.SDK_INT >= 16)
      super.onWindowSystemUiVisibilityChanged(paramInt); 
    pullChildren();
    int i = this.mLastSystemUiVisibility;
    this.mLastSystemUiVisibility = paramInt;
    boolean bool2 = false;
    if ((paramInt & 0x4) == 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if ((paramInt & 0x100) != 0)
      bool2 = true; 
    ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
    if (actionBarVisibilityCallback != null) {
      actionBarVisibilityCallback.enableContentAnimations(bool2 ^ true);
      if (bool1 || !bool2) {
        this.mActionBarVisibilityCallback.showForSystem();
      } else {
        this.mActionBarVisibilityCallback.hideForSystem();
      } 
    } 
    if (((i ^ paramInt) & 0x100) != 0 && this.mActionBarVisibilityCallback != null)
      ViewCompat.requestApplyInsets((View)this); 
  }
  
  protected void onWindowVisibilityChanged(int paramInt) {
    super.onWindowVisibilityChanged(paramInt);
    this.mWindowVisibility = paramInt;
    ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
    if (actionBarVisibilityCallback != null)
      actionBarVisibilityCallback.onWindowVisibilityChanged(paramInt); 
  }
  
  void pullChildren() {
    if (this.mContent == null) {
      this.mContent = (ContentFrameLayout)findViewById(R.id.action_bar_activity_content);
      this.mActionBarTop = (ActionBarContainer)findViewById(R.id.action_bar_container);
      this.mDecorToolbar = getDecorToolbar(findViewById(R.id.action_bar));
    } 
  }
  
  public void restoreToolbarHierarchyState(SparseArray<Parcelable> paramSparseArray) {
    pullChildren();
    this.mDecorToolbar.restoreHierarchyState(paramSparseArray);
  }
  
  public void saveToolbarHierarchyState(SparseArray<Parcelable> paramSparseArray) {
    pullChildren();
    this.mDecorToolbar.saveHierarchyState(paramSparseArray);
  }
  
  public void setActionBarHideOffset(int paramInt) {
    haltActionBarHideOffsetAnimations();
    paramInt = Math.max(0, Math.min(paramInt, this.mActionBarTop.getHeight()));
    this.mActionBarTop.setTranslationY(-paramInt);
  }
  
  public void setActionBarVisibilityCallback(ActionBarVisibilityCallback paramActionBarVisibilityCallback) {
    this.mActionBarVisibilityCallback = paramActionBarVisibilityCallback;
    if (getWindowToken() != null) {
      this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
      int i = this.mLastSystemUiVisibility;
      if (i != 0) {
        onWindowSystemUiVisibilityChanged(i);
        ViewCompat.requestApplyInsets((View)this);
      } 
    } 
  }
  
  public void setHasNonEmbeddedTabs(boolean paramBoolean) {
    this.mHasNonEmbeddedTabs = paramBoolean;
  }
  
  public void setHideOnContentScrollEnabled(boolean paramBoolean) {
    if (paramBoolean != this.mHideOnContentScroll) {
      this.mHideOnContentScroll = paramBoolean;
      if (!paramBoolean) {
        haltActionBarHideOffsetAnimations();
        setActionBarHideOffset(0);
      } 
    } 
  }
  
  public void setIcon(int paramInt) {
    pullChildren();
    this.mDecorToolbar.setIcon(paramInt);
  }
  
  public void setIcon(Drawable paramDrawable) {
    pullChildren();
    this.mDecorToolbar.setIcon(paramDrawable);
  }
  
  public void setLogo(int paramInt) {
    pullChildren();
    this.mDecorToolbar.setLogo(paramInt);
  }
  
  public void setMenu(Menu paramMenu, MenuPresenter.Callback paramCallback) {
    pullChildren();
    this.mDecorToolbar.setMenu(paramMenu, paramCallback);
  }
  
  public void setMenuPrepared() {
    pullChildren();
    this.mDecorToolbar.setMenuPrepared();
  }
  
  public void setOverlayMode(boolean paramBoolean) {
    this.mOverlayMode = paramBoolean;
    if (paramBoolean && (getContext().getApplicationInfo()).targetSdkVersion < 19) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    this.mIgnoreWindowContentOverlay = paramBoolean;
  }
  
  public void setShowingForActionMode(boolean paramBoolean) {}
  
  public void setUiOptions(int paramInt) {}
  
  public void setWindowCallback(Window.Callback paramCallback) {
    pullChildren();
    this.mDecorToolbar.setWindowCallback(paramCallback);
  }
  
  public void setWindowTitle(CharSequence paramCharSequence) {
    pullChildren();
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public boolean shouldDelayChildPressedState() {
    return false;
  }
  
  public boolean showOverflowMenu() {
    pullChildren();
    return this.mDecorToolbar.showOverflowMenu();
  }
  
  public static interface ActionBarVisibilityCallback {
    void enableContentAnimations(boolean param1Boolean);
    
    void hideForSystem();
    
    void onContentScrollStarted();
    
    void onContentScrollStopped();
    
    void onWindowVisibilityChanged(int param1Int);
    
    void showForSystem();
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\ActionBarOverlayLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */