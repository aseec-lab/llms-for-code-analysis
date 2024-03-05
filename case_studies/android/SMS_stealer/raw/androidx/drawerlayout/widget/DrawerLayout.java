package androidx.drawerlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup {
  private static final boolean ALLOW_EDGE_LOCK = false;
  
  static final boolean CAN_HIDE_DESCENDANTS;
  
  private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
  
  private static final int DEFAULT_SCRIM_COLOR = -1728053248;
  
  private static final int DRAWER_ELEVATION = 10;
  
  static final int[] LAYOUT_ATTRS;
  
  public static final int LOCK_MODE_LOCKED_CLOSED = 1;
  
  public static final int LOCK_MODE_LOCKED_OPEN = 2;
  
  public static final int LOCK_MODE_UNDEFINED = 3;
  
  public static final int LOCK_MODE_UNLOCKED = 0;
  
  private static final int MIN_DRAWER_MARGIN = 64;
  
  private static final int MIN_FLING_VELOCITY = 400;
  
  private static final int PEEK_DELAY = 160;
  
  private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
  
  public static final int STATE_DRAGGING = 1;
  
  public static final int STATE_IDLE = 0;
  
  public static final int STATE_SETTLING = 2;
  
  private static final String TAG = "DrawerLayout";
  
  private static final int[] THEME_ATTRS = new int[] { 16843828 };
  
  private static final float TOUCH_SLOP_SENSITIVITY = 1.0F;
  
  private final ChildAccessibilityDelegate mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
  
  private Rect mChildHitRect;
  
  private Matrix mChildInvertedMatrix;
  
  private boolean mChildrenCanceledTouch;
  
  private boolean mDisallowInterceptRequested;
  
  private boolean mDrawStatusBarBackground;
  
  private float mDrawerElevation;
  
  private int mDrawerState;
  
  private boolean mFirstLayout = true;
  
  private boolean mInLayout;
  
  private float mInitialMotionX;
  
  private float mInitialMotionY;
  
  private Object mLastInsets;
  
  private final ViewDragCallback mLeftCallback;
  
  private final ViewDragHelper mLeftDragger;
  
  private DrawerListener mListener;
  
  private List<DrawerListener> mListeners;
  
  private int mLockModeEnd = 3;
  
  private int mLockModeLeft = 3;
  
  private int mLockModeRight = 3;
  
  private int mLockModeStart = 3;
  
  private int mMinDrawerMargin;
  
  private final ArrayList<View> mNonDrawerViews;
  
  private final ViewDragCallback mRightCallback;
  
  private final ViewDragHelper mRightDragger;
  
  private int mScrimColor = -1728053248;
  
  private float mScrimOpacity;
  
  private Paint mScrimPaint = new Paint();
  
  private Drawable mShadowEnd = null;
  
  private Drawable mShadowLeft = null;
  
  private Drawable mShadowLeftResolved;
  
  private Drawable mShadowRight = null;
  
  private Drawable mShadowRightResolved;
  
  private Drawable mShadowStart = null;
  
  private Drawable mStatusBarBackground;
  
  private CharSequence mTitleLeft;
  
  private CharSequence mTitleRight;
  
  static {
    LAYOUT_ATTRS = new int[] { 16842931 };
    if (Build.VERSION.SDK_INT >= 19) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    CAN_HIDE_DESCENDANTS = bool1;
    if (Build.VERSION.SDK_INT >= 21) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    SET_DRAWER_SHADOW_FROM_ELEVATION = bool1;
  }
  
  public DrawerLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    setDescendantFocusability(262144);
    float f2 = (getResources().getDisplayMetrics()).density;
    this.mMinDrawerMargin = (int)(64.0F * f2 + 0.5F);
    float f1 = 400.0F * f2;
    this.mLeftCallback = new ViewDragCallback(3);
    this.mRightCallback = new ViewDragCallback(5);
    ViewDragHelper viewDragHelper = ViewDragHelper.create(this, 1.0F, this.mLeftCallback);
    this.mLeftDragger = viewDragHelper;
    viewDragHelper.setEdgeTrackingEnabled(1);
    this.mLeftDragger.setMinVelocity(f1);
    this.mLeftCallback.setDragger(this.mLeftDragger);
    viewDragHelper = ViewDragHelper.create(this, 1.0F, this.mRightCallback);
    this.mRightDragger = viewDragHelper;
    viewDragHelper.setEdgeTrackingEnabled(2);
    this.mRightDragger.setMinVelocity(f1);
    this.mRightCallback.setDragger(this.mRightDragger);
    setFocusableInTouchMode(true);
    ViewCompat.setImportantForAccessibility((View)this, 1);
    ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
    setMotionEventSplittingEnabled(false);
    if (ViewCompat.getFitsSystemWindows((View)this))
      if (Build.VERSION.SDK_INT >= 21) {
        setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
              final DrawerLayout this$0;
              
              public WindowInsets onApplyWindowInsets(View param1View, WindowInsets param1WindowInsets) {
                boolean bool;
                DrawerLayout drawerLayout = (DrawerLayout)param1View;
                if (param1WindowInsets.getSystemWindowInsetTop() > 0) {
                  bool = true;
                } else {
                  bool = false;
                } 
                drawerLayout.setChildInsets(param1WindowInsets, bool);
                return param1WindowInsets.consumeSystemWindowInsets();
              }
            });
        setSystemUiVisibility(1280);
        TypedArray typedArray = paramContext.obtainStyledAttributes(THEME_ATTRS);
        try {
          this.mStatusBarBackground = typedArray.getDrawable(0);
        } finally {
          typedArray.recycle();
        } 
      } else {
        this.mStatusBarBackground = null;
      }  
    this.mDrawerElevation = f2 * 10.0F;
    this.mNonDrawerViews = new ArrayList<View>();
  }
  
  private boolean dispatchTransformedGenericPointerEvent(MotionEvent paramMotionEvent, View paramView) {
    boolean bool;
    if (!paramView.getMatrix().isIdentity()) {
      paramMotionEvent = getTransformedMotionEvent(paramMotionEvent, paramView);
      bool = paramView.dispatchGenericMotionEvent(paramMotionEvent);
      paramMotionEvent.recycle();
    } else {
      float f1 = (getScrollX() - paramView.getLeft());
      float f2 = (getScrollY() - paramView.getTop());
      paramMotionEvent.offsetLocation(f1, f2);
      bool = paramView.dispatchGenericMotionEvent(paramMotionEvent);
      paramMotionEvent.offsetLocation(-f1, -f2);
    } 
    return bool;
  }
  
  private MotionEvent getTransformedMotionEvent(MotionEvent paramMotionEvent, View paramView) {
    float f2 = (getScrollX() - paramView.getLeft());
    float f1 = (getScrollY() - paramView.getTop());
    paramMotionEvent = MotionEvent.obtain(paramMotionEvent);
    paramMotionEvent.offsetLocation(f2, f1);
    Matrix matrix = paramView.getMatrix();
    if (!matrix.isIdentity()) {
      if (this.mChildInvertedMatrix == null)
        this.mChildInvertedMatrix = new Matrix(); 
      matrix.invert(this.mChildInvertedMatrix);
      paramMotionEvent.transform(this.mChildInvertedMatrix);
    } 
    return paramMotionEvent;
  }
  
  static String gravityToString(int paramInt) {
    return ((paramInt & 0x3) == 3) ? "LEFT" : (((paramInt & 0x5) == 5) ? "RIGHT" : Integer.toHexString(paramInt));
  }
  
  private static boolean hasOpaqueBackground(View paramView) {
    Drawable drawable = paramView.getBackground();
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (drawable != null) {
      bool1 = bool2;
      if (drawable.getOpacity() == -1)
        bool1 = true; 
    } 
    return bool1;
  }
  
  private boolean hasPeekingDrawer() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      if (((LayoutParams)getChildAt(b).getLayoutParams()).isPeeking)
        return true; 
    } 
    return false;
  }
  
  private boolean hasVisibleDrawer() {
    boolean bool;
    if (findVisibleDrawer() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static boolean includeChildForAccessibility(View paramView) {
    boolean bool;
    if (ViewCompat.getImportantForAccessibility(paramView) != 4 && ViewCompat.getImportantForAccessibility(paramView) != 2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean isInBoundsOfChild(float paramFloat1, float paramFloat2, View paramView) {
    if (this.mChildHitRect == null)
      this.mChildHitRect = new Rect(); 
    paramView.getHitRect(this.mChildHitRect);
    return this.mChildHitRect.contains((int)paramFloat1, (int)paramFloat2);
  }
  
  private boolean mirror(Drawable paramDrawable, int paramInt) {
    if (paramDrawable == null || !DrawableCompat.isAutoMirrored(paramDrawable))
      return false; 
    DrawableCompat.setLayoutDirection(paramDrawable, paramInt);
    return true;
  }
  
  private Drawable resolveLeftShadow() {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (i == 0) {
      Drawable drawable = this.mShadowStart;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowStart;
      } 
    } else {
      Drawable drawable = this.mShadowEnd;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowEnd;
      } 
    } 
    return this.mShadowLeft;
  }
  
  private Drawable resolveRightShadow() {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (i == 0) {
      Drawable drawable = this.mShadowEnd;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowEnd;
      } 
    } else {
      Drawable drawable = this.mShadowStart;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowStart;
      } 
    } 
    return this.mShadowRight;
  }
  
  private void resolveShadowDrawables() {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION)
      return; 
    this.mShadowLeftResolved = resolveLeftShadow();
    this.mShadowRightResolved = resolveRightShadow();
  }
  
  private void updateChildrenImportantForAccessibility(View paramView, boolean paramBoolean) {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if ((!paramBoolean && !isDrawerView(view)) || (paramBoolean && view == paramView)) {
        ViewCompat.setImportantForAccessibility(view, 1);
      } else {
        ViewCompat.setImportantForAccessibility(view, 4);
      } 
    } 
  }
  
  public void addDrawerListener(DrawerListener paramDrawerListener) {
    if (paramDrawerListener == null)
      return; 
    if (this.mListeners == null)
      this.mListeners = new ArrayList<DrawerListener>(); 
    this.mListeners.add(paramDrawerListener);
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2) {
    if (getDescendantFocusability() == 393216)
      return; 
    int j = getChildCount();
    boolean bool = false;
    byte b = 0;
    int i = 0;
    while (b < j) {
      View view = getChildAt(b);
      if (isDrawerView(view)) {
        if (isDrawerOpen(view)) {
          view.addFocusables(paramArrayList, paramInt1, paramInt2);
          i = 1;
        } 
      } else {
        this.mNonDrawerViews.add(view);
      } 
      b++;
    } 
    if (!i) {
      i = this.mNonDrawerViews.size();
      for (b = bool; b < i; b++) {
        View view = this.mNonDrawerViews.get(b);
        if (view.getVisibility() == 0)
          view.addFocusables(paramArrayList, paramInt1, paramInt2); 
      } 
    } 
    this.mNonDrawerViews.clear();
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (findOpenDrawer() != null || isDrawerView(paramView)) {
      ViewCompat.setImportantForAccessibility(paramView, 4);
    } else {
      ViewCompat.setImportantForAccessibility(paramView, 1);
    } 
    if (!CAN_HIDE_DESCENDANTS)
      ViewCompat.setAccessibilityDelegate(paramView, this.mChildAccessibilityDelegate); 
  }
  
  void cancelChildViewTouch() {
    if (!this.mChildrenCanceledTouch) {
      long l = SystemClock.uptimeMillis();
      MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      int i = getChildCount();
      for (byte b = 0; b < i; b++)
        getChildAt(b).dispatchTouchEvent(motionEvent); 
      motionEvent.recycle();
      this.mChildrenCanceledTouch = true;
    } 
  }
  
  boolean checkDrawerViewAbsoluteGravity(View paramView, int paramInt) {
    boolean bool;
    if ((getDrawerViewAbsoluteGravity(paramView) & paramInt) == paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    boolean bool;
    if (paramLayoutParams instanceof LayoutParams && super.checkLayoutParams(paramLayoutParams)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void closeDrawer(int paramInt) {
    closeDrawer(paramInt, true);
  }
  
  public void closeDrawer(int paramInt, boolean paramBoolean) {
    View view = findDrawerWithGravity(paramInt);
    if (view != null) {
      closeDrawer(view, paramBoolean);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No drawer view found with gravity ");
    stringBuilder.append(gravityToString(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void closeDrawer(View paramView) {
    closeDrawer(paramView, true);
  }
  
  public void closeDrawer(View paramView, boolean paramBoolean) {
    if (isDrawerView(paramView)) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      if (this.mFirstLayout) {
        layoutParams.onScreen = 0.0F;
        layoutParams.openState = 0;
      } else if (paramBoolean) {
        layoutParams.openState |= 0x4;
        if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
          this.mLeftDragger.smoothSlideViewTo(paramView, -paramView.getWidth(), paramView.getTop());
        } else {
          this.mRightDragger.smoothSlideViewTo(paramView, getWidth(), paramView.getTop());
        } 
      } else {
        moveDrawerToOffset(paramView, 0.0F);
        updateDrawerState(layoutParams.gravity, 0, paramView);
        paramView.setVisibility(4);
      } 
      invalidate();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a sliding drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void closeDrawers() {
    closeDrawers(false);
  }
  
  void closeDrawers(boolean paramBoolean) {
    int i;
    int k = getChildCount();
    byte b = 0;
    int j = 0;
    while (b < k) {
      int m;
      View view = getChildAt(b);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      int n = j;
      if (isDrawerView(view))
        if (paramBoolean && !layoutParams.isPeeking) {
          n = j;
        } else {
          int i1;
          n = view.getWidth();
          if (checkDrawerViewAbsoluteGravity(view, 3)) {
            i1 = this.mLeftDragger.smoothSlideViewTo(view, -n, view.getTop());
          } else {
            i1 = this.mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop());
          } 
          m = j | i1;
          layoutParams.isPeeking = false;
        }  
      b++;
      i = m;
    } 
    this.mLeftCallback.removeCallbacks();
    this.mRightCallback.removeCallbacks();
    if (i != 0)
      invalidate(); 
  }
  
  public void computeScroll() {
    int i = getChildCount();
    float f = 0.0F;
    for (byte b = 0; b < i; b++)
      f = Math.max(f, ((LayoutParams)getChildAt(b).getLayoutParams()).onScreen); 
    this.mScrimOpacity = f;
    boolean bool2 = this.mLeftDragger.continueSettling(true);
    boolean bool1 = this.mRightDragger.continueSettling(true);
    if (bool2 || bool1)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  public boolean dispatchGenericMotionEvent(MotionEvent paramMotionEvent) {
    if ((paramMotionEvent.getSource() & 0x2) == 0 || paramMotionEvent.getAction() == 10 || this.mScrimOpacity <= 0.0F)
      return super.dispatchGenericMotionEvent(paramMotionEvent); 
    int i = getChildCount();
    if (i != 0) {
      float f2 = paramMotionEvent.getX();
      float f1 = paramMotionEvent.getY();
      while (--i >= 0) {
        View view = getChildAt(i);
        if (isInBoundsOfChild(f2, f1, view) && !isContentView(view) && dispatchTransformedGenericPointerEvent(paramMotionEvent, view))
          return true; 
        i--;
      } 
    } 
    return false;
  }
  
  void dispatchOnDrawerClosed(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((layoutParams.openState & 0x1) == 1) {
      layoutParams.openState = 0;
      List<DrawerListener> list = this.mListeners;
      if (list != null)
        for (int i = list.size() - 1; i >= 0; i--)
          ((DrawerListener)this.mListeners.get(i)).onDrawerClosed(paramView);  
      updateChildrenImportantForAccessibility(paramView, false);
      if (hasWindowFocus()) {
        paramView = getRootView();
        if (paramView != null)
          paramView.sendAccessibilityEvent(32); 
      } 
    } 
  }
  
  void dispatchOnDrawerOpened(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((layoutParams.openState & 0x1) == 0) {
      layoutParams.openState = 1;
      List<DrawerListener> list = this.mListeners;
      if (list != null)
        for (int i = list.size() - 1; i >= 0; i--)
          ((DrawerListener)this.mListeners.get(i)).onDrawerOpened(paramView);  
      updateChildrenImportantForAccessibility(paramView, true);
      if (hasWindowFocus())
        sendAccessibilityEvent(32); 
    } 
  }
  
  void dispatchOnDrawerSlide(View paramView, float paramFloat) {
    List<DrawerListener> list = this.mListeners;
    if (list != null)
      for (int i = list.size() - 1; i >= 0; i--)
        ((DrawerListener)this.mListeners.get(i)).onDrawerSlide(paramView, paramFloat);  
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    int n = getHeight();
    boolean bool1 = isContentView(paramView);
    int i = getWidth();
    int m = paramCanvas.save();
    int k = 0;
    int j = i;
    if (bool1) {
      int i1 = getChildCount();
      k = 0;
      for (j = 0; k < i1; j = i2) {
        View view = getChildAt(k);
        int i3 = i;
        int i2 = j;
        if (view != paramView) {
          i3 = i;
          i2 = j;
          if (view.getVisibility() == 0) {
            i3 = i;
            i2 = j;
            if (hasOpaqueBackground(view)) {
              i3 = i;
              i2 = j;
              if (isDrawerView(view))
                if (view.getHeight() < n) {
                  i3 = i;
                  i2 = j;
                } else if (checkDrawerViewAbsoluteGravity(view, 3)) {
                  int i4 = view.getRight();
                  i3 = i;
                  i2 = j;
                  if (i4 > j) {
                    i2 = i4;
                    i3 = i;
                  } 
                } else {
                  int i4 = view.getLeft();
                  i3 = i;
                  i2 = j;
                  if (i4 < i) {
                    i3 = i4;
                    i2 = j;
                  } 
                }  
            } 
          } 
        } 
        k++;
        i = i3;
      } 
      paramCanvas.clipRect(j, 0, i, getHeight());
      k = j;
      j = i;
    } 
    boolean bool2 = super.drawChild(paramCanvas, paramView, paramLong);
    paramCanvas.restoreToCount(m);
    float f = this.mScrimOpacity;
    if (f > 0.0F && bool1) {
      int i1 = this.mScrimColor;
      i = (int)(((0xFF000000 & i1) >>> 24) * f);
      this.mScrimPaint.setColor(i1 & 0xFFFFFF | i << 24);
      paramCanvas.drawRect(k, 0.0F, j, getHeight(), this.mScrimPaint);
    } else if (this.mShadowLeftResolved != null && checkDrawerViewAbsoluteGravity(paramView, 3)) {
      j = this.mShadowLeftResolved.getIntrinsicWidth();
      i = paramView.getRight();
      k = this.mLeftDragger.getEdgeSize();
      f = Math.max(0.0F, Math.min(i / k, 1.0F));
      this.mShadowLeftResolved.setBounds(i, paramView.getTop(), j + i, paramView.getBottom());
      this.mShadowLeftResolved.setAlpha((int)(f * 255.0F));
      this.mShadowLeftResolved.draw(paramCanvas);
    } else if (this.mShadowRightResolved != null && checkDrawerViewAbsoluteGravity(paramView, 5)) {
      j = this.mShadowRightResolved.getIntrinsicWidth();
      int i1 = paramView.getLeft();
      i = getWidth();
      k = this.mRightDragger.getEdgeSize();
      f = Math.max(0.0F, Math.min((i - i1) / k, 1.0F));
      this.mShadowRightResolved.setBounds(i1 - j, paramView.getTop(), i1, paramView.getBottom());
      this.mShadowRightResolved.setAlpha((int)(f * 255.0F));
      this.mShadowRightResolved.draw(paramCanvas);
    } 
    return bool2;
  }
  
  View findDrawerWithGravity(int paramInt) {
    int i = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    int j = getChildCount();
    for (paramInt = 0; paramInt < j; paramInt++) {
      View view = getChildAt(paramInt);
      if ((getDrawerViewAbsoluteGravity(view) & 0x7) == (i & 0x7))
        return view; 
    } 
    return null;
  }
  
  View findOpenDrawer() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if ((((LayoutParams)view.getLayoutParams()).openState & 0x1) == 1)
        return view; 
    } 
    return null;
  }
  
  View findVisibleDrawer() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if (isDrawerView(view) && isDrawerVisible(view))
        return view; 
    } 
    return null;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return (ViewGroup.LayoutParams)new LayoutParams(-1, -1);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return (ViewGroup.LayoutParams)new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    LayoutParams layoutParams;
    if (paramLayoutParams instanceof LayoutParams) {
      layoutParams = new LayoutParams((LayoutParams)paramLayoutParams);
    } else if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      layoutParams = new LayoutParams(layoutParams);
    } else {
      layoutParams = new LayoutParams((ViewGroup.LayoutParams)layoutParams);
    } 
    return (ViewGroup.LayoutParams)layoutParams;
  }
  
  public float getDrawerElevation() {
    return SET_DRAWER_SHADOW_FROM_ELEVATION ? this.mDrawerElevation : 0.0F;
  }
  
  public int getDrawerLockMode(int paramInt) {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (paramInt != 3) {
      if (paramInt != 5) {
        if (paramInt != 8388611) {
          if (paramInt == 8388613) {
            paramInt = this.mLockModeEnd;
            if (paramInt != 3)
              return paramInt; 
            if (i == 0) {
              paramInt = this.mLockModeRight;
            } else {
              paramInt = this.mLockModeLeft;
            } 
            if (paramInt != 3)
              return paramInt; 
          } 
        } else {
          paramInt = this.mLockModeStart;
          if (paramInt != 3)
            return paramInt; 
          if (i == 0) {
            paramInt = this.mLockModeLeft;
          } else {
            paramInt = this.mLockModeRight;
          } 
          if (paramInt != 3)
            return paramInt; 
        } 
      } else {
        paramInt = this.mLockModeRight;
        if (paramInt != 3)
          return paramInt; 
        if (i == 0) {
          paramInt = this.mLockModeEnd;
        } else {
          paramInt = this.mLockModeStart;
        } 
        if (paramInt != 3)
          return paramInt; 
      } 
    } else {
      paramInt = this.mLockModeLeft;
      if (paramInt != 3)
        return paramInt; 
      if (i == 0) {
        paramInt = this.mLockModeStart;
      } else {
        paramInt = this.mLockModeEnd;
      } 
      if (paramInt != 3)
        return paramInt; 
    } 
    return 0;
  }
  
  public int getDrawerLockMode(View paramView) {
    if (isDrawerView(paramView))
      return getDrawerLockMode(((LayoutParams)paramView.getLayoutParams()).gravity); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public CharSequence getDrawerTitle(int paramInt) {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    return (paramInt == 3) ? this.mTitleLeft : ((paramInt == 5) ? this.mTitleRight : null);
  }
  
  int getDrawerViewAbsoluteGravity(View paramView) {
    return GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection((View)this));
  }
  
  float getDrawerViewOffset(View paramView) {
    return ((LayoutParams)paramView.getLayoutParams()).onScreen;
  }
  
  public Drawable getStatusBarBackgroundDrawable() {
    return this.mStatusBarBackground;
  }
  
  boolean isContentView(View paramView) {
    boolean bool;
    if (((LayoutParams)paramView.getLayoutParams()).gravity == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isDrawerOpen(int paramInt) {
    View view = findDrawerWithGravity(paramInt);
    return (view != null) ? isDrawerOpen(view) : false;
  }
  
  public boolean isDrawerOpen(View paramView) {
    if (isDrawerView(paramView)) {
      int i = ((LayoutParams)paramView.getLayoutParams()).openState;
      boolean bool = true;
      if ((i & 0x1) != 1)
        bool = false; 
      return bool;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  boolean isDrawerView(View paramView) {
    int i = GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(paramView));
    return ((i & 0x3) != 0) ? true : (((i & 0x5) != 0));
  }
  
  public boolean isDrawerVisible(int paramInt) {
    View view = findDrawerWithGravity(paramInt);
    return (view != null) ? isDrawerVisible(view) : false;
  }
  
  public boolean isDrawerVisible(View paramView) {
    if (isDrawerView(paramView)) {
      boolean bool;
      if (((LayoutParams)paramView.getLayoutParams()).onScreen > 0.0F) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  void moveDrawerToOffset(View paramView, float paramFloat) {
    float f1 = getDrawerViewOffset(paramView);
    float f2 = paramView.getWidth();
    int i = (int)(f1 * f2);
    i = (int)(f2 * paramFloat) - i;
    if (!checkDrawerViewAbsoluteGravity(paramView, 3))
      i = -i; 
    paramView.offsetLeftAndRight(i);
    setDrawerViewOffset(paramView, paramFloat);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
  }
  
  public void onDraw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial onDraw : (Landroid/graphics/Canvas;)V
    //   5: aload_0
    //   6: getfield mDrawStatusBarBackground : Z
    //   9: ifeq -> 75
    //   12: aload_0
    //   13: getfield mStatusBarBackground : Landroid/graphics/drawable/Drawable;
    //   16: ifnull -> 75
    //   19: getstatic android/os/Build$VERSION.SDK_INT : I
    //   22: bipush #21
    //   24: if_icmplt -> 47
    //   27: aload_0
    //   28: getfield mLastInsets : Ljava/lang/Object;
    //   31: astore_3
    //   32: aload_3
    //   33: ifnull -> 47
    //   36: aload_3
    //   37: checkcast android/view/WindowInsets
    //   40: invokevirtual getSystemWindowInsetTop : ()I
    //   43: istore_2
    //   44: goto -> 49
    //   47: iconst_0
    //   48: istore_2
    //   49: iload_2
    //   50: ifle -> 75
    //   53: aload_0
    //   54: getfield mStatusBarBackground : Landroid/graphics/drawable/Drawable;
    //   57: iconst_0
    //   58: iconst_0
    //   59: aload_0
    //   60: invokevirtual getWidth : ()I
    //   63: iload_2
    //   64: invokevirtual setBounds : (IIII)V
    //   67: aload_0
    //   68: getfield mStatusBarBackground : Landroid/graphics/drawable/Drawable;
    //   71: aload_1
    //   72: invokevirtual draw : (Landroid/graphics/Canvas;)V
    //   75: return
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getActionMasked : ()I
    //   4: istore #4
    //   6: aload_0
    //   7: getfield mLeftDragger : Landroidx/customview/widget/ViewDragHelper;
    //   10: aload_1
    //   11: invokevirtual shouldInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   14: istore #8
    //   16: aload_0
    //   17: getfield mRightDragger : Landroidx/customview/widget/ViewDragHelper;
    //   20: aload_1
    //   21: invokevirtual shouldInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   24: istore #7
    //   26: iconst_1
    //   27: istore #6
    //   29: iload #4
    //   31: ifeq -> 104
    //   34: iload #4
    //   36: iconst_1
    //   37: if_icmpeq -> 83
    //   40: iload #4
    //   42: iconst_2
    //   43: if_icmpeq -> 55
    //   46: iload #4
    //   48: iconst_3
    //   49: if_icmpeq -> 83
    //   52: goto -> 98
    //   55: aload_0
    //   56: getfield mLeftDragger : Landroidx/customview/widget/ViewDragHelper;
    //   59: iconst_3
    //   60: invokevirtual checkTouchSlop : (I)Z
    //   63: ifeq -> 98
    //   66: aload_0
    //   67: getfield mLeftCallback : Landroidx/drawerlayout/widget/DrawerLayout$ViewDragCallback;
    //   70: invokevirtual removeCallbacks : ()V
    //   73: aload_0
    //   74: getfield mRightCallback : Landroidx/drawerlayout/widget/DrawerLayout$ViewDragCallback;
    //   77: invokevirtual removeCallbacks : ()V
    //   80: goto -> 98
    //   83: aload_0
    //   84: iconst_1
    //   85: invokevirtual closeDrawers : (Z)V
    //   88: aload_0
    //   89: iconst_0
    //   90: putfield mDisallowInterceptRequested : Z
    //   93: aload_0
    //   94: iconst_0
    //   95: putfield mChildrenCanceledTouch : Z
    //   98: iconst_0
    //   99: istore #4
    //   101: goto -> 176
    //   104: aload_1
    //   105: invokevirtual getX : ()F
    //   108: fstore_2
    //   109: aload_1
    //   110: invokevirtual getY : ()F
    //   113: fstore_3
    //   114: aload_0
    //   115: fload_2
    //   116: putfield mInitialMotionX : F
    //   119: aload_0
    //   120: fload_3
    //   121: putfield mInitialMotionY : F
    //   124: aload_0
    //   125: getfield mScrimOpacity : F
    //   128: fconst_0
    //   129: fcmpl
    //   130: ifle -> 163
    //   133: aload_0
    //   134: getfield mLeftDragger : Landroidx/customview/widget/ViewDragHelper;
    //   137: fload_2
    //   138: f2i
    //   139: fload_3
    //   140: f2i
    //   141: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   144: astore_1
    //   145: aload_1
    //   146: ifnull -> 163
    //   149: aload_0
    //   150: aload_1
    //   151: invokevirtual isContentView : (Landroid/view/View;)Z
    //   154: ifeq -> 163
    //   157: iconst_1
    //   158: istore #4
    //   160: goto -> 166
    //   163: iconst_0
    //   164: istore #4
    //   166: aload_0
    //   167: iconst_0
    //   168: putfield mDisallowInterceptRequested : Z
    //   171: aload_0
    //   172: iconst_0
    //   173: putfield mChildrenCanceledTouch : Z
    //   176: iload #6
    //   178: istore #5
    //   180: iload #8
    //   182: iload #7
    //   184: ior
    //   185: ifne -> 225
    //   188: iload #6
    //   190: istore #5
    //   192: iload #4
    //   194: ifne -> 225
    //   197: iload #6
    //   199: istore #5
    //   201: aload_0
    //   202: invokespecial hasPeekingDrawer : ()Z
    //   205: ifne -> 225
    //   208: aload_0
    //   209: getfield mChildrenCanceledTouch : Z
    //   212: ifeq -> 222
    //   215: iload #6
    //   217: istore #5
    //   219: goto -> 225
    //   222: iconst_0
    //   223: istore #5
    //   225: iload #5
    //   227: ireturn
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 4 && hasVisibleDrawer()) {
      paramKeyEvent.startTracking();
      return true;
    } 
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    View view;
    if (paramInt == 4) {
      boolean bool;
      view = findVisibleDrawer();
      if (view != null && getDrawerLockMode(view) == 0)
        closeDrawers(); 
      if (view != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    } 
    return super.onKeyUp(paramInt, (KeyEvent)view);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mInLayout = true;
    int j = paramInt3 - paramInt1;
    int i = getChildCount();
    for (paramInt3 = 0; paramInt3 < i; paramInt3++) {
      View view = getChildAt(paramInt3);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (isContentView(view)) {
          view.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + view.getMeasuredWidth(), layoutParams.topMargin + view.getMeasuredHeight());
        } else {
          float f;
          int k;
          boolean bool;
          int m = view.getMeasuredWidth();
          int n = view.getMeasuredHeight();
          if (checkDrawerViewAbsoluteGravity(view, 3)) {
            paramInt1 = -m;
            f = m;
            k = paramInt1 + (int)(layoutParams.onScreen * f);
            f = (m + k) / f;
          } else {
            f = m;
            k = j - (int)(layoutParams.onScreen * f);
            f = (j - k) / f;
          } 
          if (f != layoutParams.onScreen) {
            bool = true;
          } else {
            bool = false;
          } 
          paramInt1 = layoutParams.gravity & 0x70;
          if (paramInt1 != 16) {
            if (paramInt1 != 80) {
              view.layout(k, layoutParams.topMargin, m + k, layoutParams.topMargin + n);
            } else {
              paramInt1 = paramInt4 - paramInt2;
              view.layout(k, paramInt1 - layoutParams.bottomMargin - view.getMeasuredHeight(), m + k, paramInt1 - layoutParams.bottomMargin);
            } 
          } else {
            int i2 = paramInt4 - paramInt2;
            int i1 = (i2 - n) / 2;
            if (i1 < layoutParams.topMargin) {
              paramInt1 = layoutParams.topMargin;
            } else {
              paramInt1 = i1;
              if (i1 + n > i2 - layoutParams.bottomMargin)
                paramInt1 = i2 - layoutParams.bottomMargin - n; 
            } 
            view.layout(k, paramInt1, m + k, n + paramInt1);
          } 
          if (bool)
            setDrawerViewOffset(view, f); 
          if (layoutParams.onScreen > 0.0F) {
            paramInt1 = 0;
          } else {
            paramInt1 = 4;
          } 
          if (view.getVisibility() != paramInt1)
            view.setVisibility(paramInt1); 
        } 
      } 
    } 
    this.mInLayout = false;
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: invokestatic getMode : (I)I
    //   4: istore #10
    //   6: iload_2
    //   7: invokestatic getMode : (I)I
    //   10: istore #9
    //   12: iload_1
    //   13: invokestatic getSize : (I)I
    //   16: istore #5
    //   18: iload_2
    //   19: invokestatic getSize : (I)I
    //   22: istore #6
    //   24: iload #10
    //   26: ldc_w 1073741824
    //   29: if_icmpne -> 48
    //   32: iload #5
    //   34: istore #7
    //   36: iload #6
    //   38: istore #8
    //   40: iload #9
    //   42: ldc_w 1073741824
    //   45: if_icmpeq -> 117
    //   48: aload_0
    //   49: invokevirtual isInEditMode : ()Z
    //   52: ifeq -> 816
    //   55: iload #10
    //   57: ldc_w -2147483648
    //   60: if_icmpne -> 66
    //   63: goto -> 76
    //   66: iload #10
    //   68: ifne -> 76
    //   71: sipush #300
    //   74: istore #5
    //   76: iload #9
    //   78: ldc_w -2147483648
    //   81: if_icmpne -> 95
    //   84: iload #5
    //   86: istore #7
    //   88: iload #6
    //   90: istore #8
    //   92: goto -> 117
    //   95: iload #5
    //   97: istore #7
    //   99: iload #6
    //   101: istore #8
    //   103: iload #9
    //   105: ifne -> 117
    //   108: sipush #300
    //   111: istore #8
    //   113: iload #5
    //   115: istore #7
    //   117: aload_0
    //   118: iload #7
    //   120: iload #8
    //   122: invokevirtual setMeasuredDimension : (II)V
    //   125: aload_0
    //   126: getfield mLastInsets : Ljava/lang/Object;
    //   129: ifnull -> 145
    //   132: aload_0
    //   133: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   136: ifeq -> 145
    //   139: iconst_1
    //   140: istore #9
    //   142: goto -> 148
    //   145: iconst_0
    //   146: istore #9
    //   148: aload_0
    //   149: invokestatic getLayoutDirection : (Landroid/view/View;)I
    //   152: istore #12
    //   154: aload_0
    //   155: invokevirtual getChildCount : ()I
    //   158: istore #13
    //   160: iconst_0
    //   161: istore #10
    //   163: iconst_0
    //   164: istore #6
    //   166: iconst_0
    //   167: istore #5
    //   169: iload #10
    //   171: iload #13
    //   173: if_icmpge -> 815
    //   176: aload_0
    //   177: iload #10
    //   179: invokevirtual getChildAt : (I)Landroid/view/View;
    //   182: astore #17
    //   184: aload #17
    //   186: invokevirtual getVisibility : ()I
    //   189: bipush #8
    //   191: if_icmpne -> 197
    //   194: goto -> 501
    //   197: aload #17
    //   199: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   202: checkcast androidx/drawerlayout/widget/DrawerLayout$LayoutParams
    //   205: astore #18
    //   207: iload #9
    //   209: ifeq -> 447
    //   212: aload #18
    //   214: getfield gravity : I
    //   217: iload #12
    //   219: invokestatic getAbsoluteGravity : (II)I
    //   222: istore #11
    //   224: aload #17
    //   226: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   229: ifeq -> 325
    //   232: getstatic android/os/Build$VERSION.SDK_INT : I
    //   235: bipush #21
    //   237: if_icmplt -> 447
    //   240: aload_0
    //   241: getfield mLastInsets : Ljava/lang/Object;
    //   244: checkcast android/view/WindowInsets
    //   247: astore #16
    //   249: iload #11
    //   251: iconst_3
    //   252: if_icmpne -> 281
    //   255: aload #16
    //   257: aload #16
    //   259: invokevirtual getSystemWindowInsetLeft : ()I
    //   262: aload #16
    //   264: invokevirtual getSystemWindowInsetTop : ()I
    //   267: iconst_0
    //   268: aload #16
    //   270: invokevirtual getSystemWindowInsetBottom : ()I
    //   273: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   276: astore #15
    //   278: goto -> 314
    //   281: aload #16
    //   283: astore #15
    //   285: iload #11
    //   287: iconst_5
    //   288: if_icmpne -> 314
    //   291: aload #16
    //   293: iconst_0
    //   294: aload #16
    //   296: invokevirtual getSystemWindowInsetTop : ()I
    //   299: aload #16
    //   301: invokevirtual getSystemWindowInsetRight : ()I
    //   304: aload #16
    //   306: invokevirtual getSystemWindowInsetBottom : ()I
    //   309: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   312: astore #15
    //   314: aload #17
    //   316: aload #15
    //   318: invokevirtual dispatchApplyWindowInsets : (Landroid/view/WindowInsets;)Landroid/view/WindowInsets;
    //   321: pop
    //   322: goto -> 447
    //   325: getstatic android/os/Build$VERSION.SDK_INT : I
    //   328: bipush #21
    //   330: if_icmplt -> 447
    //   333: aload_0
    //   334: getfield mLastInsets : Ljava/lang/Object;
    //   337: checkcast android/view/WindowInsets
    //   340: astore #16
    //   342: iload #11
    //   344: iconst_3
    //   345: if_icmpne -> 374
    //   348: aload #16
    //   350: aload #16
    //   352: invokevirtual getSystemWindowInsetLeft : ()I
    //   355: aload #16
    //   357: invokevirtual getSystemWindowInsetTop : ()I
    //   360: iconst_0
    //   361: aload #16
    //   363: invokevirtual getSystemWindowInsetBottom : ()I
    //   366: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   369: astore #15
    //   371: goto -> 407
    //   374: aload #16
    //   376: astore #15
    //   378: iload #11
    //   380: iconst_5
    //   381: if_icmpne -> 407
    //   384: aload #16
    //   386: iconst_0
    //   387: aload #16
    //   389: invokevirtual getSystemWindowInsetTop : ()I
    //   392: aload #16
    //   394: invokevirtual getSystemWindowInsetRight : ()I
    //   397: aload #16
    //   399: invokevirtual getSystemWindowInsetBottom : ()I
    //   402: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   405: astore #15
    //   407: aload #18
    //   409: aload #15
    //   411: invokevirtual getSystemWindowInsetLeft : ()I
    //   414: putfield leftMargin : I
    //   417: aload #18
    //   419: aload #15
    //   421: invokevirtual getSystemWindowInsetTop : ()I
    //   424: putfield topMargin : I
    //   427: aload #18
    //   429: aload #15
    //   431: invokevirtual getSystemWindowInsetRight : ()I
    //   434: putfield rightMargin : I
    //   437: aload #18
    //   439: aload #15
    //   441: invokevirtual getSystemWindowInsetBottom : ()I
    //   444: putfield bottomMargin : I
    //   447: aload_0
    //   448: aload #17
    //   450: invokevirtual isContentView : (Landroid/view/View;)Z
    //   453: ifeq -> 504
    //   456: aload #17
    //   458: iload #7
    //   460: aload #18
    //   462: getfield leftMargin : I
    //   465: isub
    //   466: aload #18
    //   468: getfield rightMargin : I
    //   471: isub
    //   472: ldc_w 1073741824
    //   475: invokestatic makeMeasureSpec : (II)I
    //   478: iload #8
    //   480: aload #18
    //   482: getfield topMargin : I
    //   485: isub
    //   486: aload #18
    //   488: getfield bottomMargin : I
    //   491: isub
    //   492: ldc_w 1073741824
    //   495: invokestatic makeMeasureSpec : (II)I
    //   498: invokevirtual measure : (II)V
    //   501: goto -> 735
    //   504: aload_0
    //   505: aload #17
    //   507: invokevirtual isDrawerView : (Landroid/view/View;)Z
    //   510: ifeq -> 741
    //   513: getstatic androidx/drawerlayout/widget/DrawerLayout.SET_DRAWER_SHADOW_FROM_ELEVATION : Z
    //   516: ifeq -> 545
    //   519: aload #17
    //   521: invokestatic getElevation : (Landroid/view/View;)F
    //   524: fstore_3
    //   525: aload_0
    //   526: getfield mDrawerElevation : F
    //   529: fstore #4
    //   531: fload_3
    //   532: fload #4
    //   534: fcmpl
    //   535: ifeq -> 545
    //   538: aload #17
    //   540: fload #4
    //   542: invokestatic setElevation : (Landroid/view/View;F)V
    //   545: aload_0
    //   546: aload #17
    //   548: invokevirtual getDrawerViewAbsoluteGravity : (Landroid/view/View;)I
    //   551: bipush #7
    //   553: iand
    //   554: istore #14
    //   556: iload #14
    //   558: iconst_3
    //   559: if_icmpne -> 568
    //   562: iconst_1
    //   563: istore #11
    //   565: goto -> 571
    //   568: iconst_0
    //   569: istore #11
    //   571: iload #11
    //   573: ifeq -> 581
    //   576: iload #6
    //   578: ifne -> 594
    //   581: iload #11
    //   583: ifne -> 671
    //   586: iload #5
    //   588: ifne -> 594
    //   591: goto -> 671
    //   594: new java/lang/StringBuilder
    //   597: dup
    //   598: invokespecial <init> : ()V
    //   601: astore #15
    //   603: aload #15
    //   605: ldc_w 'Child drawer has absolute gravity '
    //   608: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   611: pop
    //   612: aload #15
    //   614: iload #14
    //   616: invokestatic gravityToString : (I)Ljava/lang/String;
    //   619: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   622: pop
    //   623: aload #15
    //   625: ldc_w ' but this '
    //   628: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   631: pop
    //   632: aload #15
    //   634: ldc 'DrawerLayout'
    //   636: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   639: pop
    //   640: aload #15
    //   642: ldc_w ' already has a '
    //   645: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   648: pop
    //   649: aload #15
    //   651: ldc_w 'drawer view along that edge'
    //   654: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   657: pop
    //   658: new java/lang/IllegalStateException
    //   661: dup
    //   662: aload #15
    //   664: invokevirtual toString : ()Ljava/lang/String;
    //   667: invokespecial <init> : (Ljava/lang/String;)V
    //   670: athrow
    //   671: iload #11
    //   673: ifeq -> 682
    //   676: iconst_1
    //   677: istore #6
    //   679: goto -> 685
    //   682: iconst_1
    //   683: istore #5
    //   685: aload #17
    //   687: iload_1
    //   688: aload_0
    //   689: getfield mMinDrawerMargin : I
    //   692: aload #18
    //   694: getfield leftMargin : I
    //   697: iadd
    //   698: aload #18
    //   700: getfield rightMargin : I
    //   703: iadd
    //   704: aload #18
    //   706: getfield width : I
    //   709: invokestatic getChildMeasureSpec : (III)I
    //   712: iload_2
    //   713: aload #18
    //   715: getfield topMargin : I
    //   718: aload #18
    //   720: getfield bottomMargin : I
    //   723: iadd
    //   724: aload #18
    //   726: getfield height : I
    //   729: invokestatic getChildMeasureSpec : (III)I
    //   732: invokevirtual measure : (II)V
    //   735: iinc #10, 1
    //   738: goto -> 169
    //   741: new java/lang/StringBuilder
    //   744: dup
    //   745: invokespecial <init> : ()V
    //   748: astore #15
    //   750: aload #15
    //   752: ldc_w 'Child '
    //   755: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   758: pop
    //   759: aload #15
    //   761: aload #17
    //   763: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   766: pop
    //   767: aload #15
    //   769: ldc_w ' at index '
    //   772: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   775: pop
    //   776: aload #15
    //   778: iload #10
    //   780: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   783: pop
    //   784: aload #15
    //   786: ldc_w ' does not have a valid layout_gravity - must be Gravity.LEFT, '
    //   789: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   792: pop
    //   793: aload #15
    //   795: ldc_w 'Gravity.RIGHT or Gravity.NO_GRAVITY'
    //   798: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   801: pop
    //   802: new java/lang/IllegalStateException
    //   805: dup
    //   806: aload #15
    //   808: invokevirtual toString : ()Ljava/lang/String;
    //   811: invokespecial <init> : (Ljava/lang/String;)V
    //   814: athrow
    //   815: return
    //   816: new java/lang/IllegalArgumentException
    //   819: dup
    //   820: ldc_w 'DrawerLayout must be measured with MeasureSpec.EXACTLY.'
    //   823: invokespecial <init> : (Ljava/lang/String;)V
    //   826: athrow
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    if (savedState.openDrawerGravity != 0) {
      View view = findDrawerWithGravity(savedState.openDrawerGravity);
      if (view != null)
        openDrawer(view); 
    } 
    if (savedState.lockModeLeft != 3)
      setDrawerLockMode(savedState.lockModeLeft, 3); 
    if (savedState.lockModeRight != 3)
      setDrawerLockMode(savedState.lockModeRight, 5); 
    if (savedState.lockModeStart != 3)
      setDrawerLockMode(savedState.lockModeStart, 8388611); 
    if (savedState.lockModeEnd != 3)
      setDrawerLockMode(savedState.lockModeEnd, 8388613); 
  }
  
  public void onRtlPropertiesChanged(int paramInt) {
    resolveShadowDrawables();
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      LayoutParams layoutParams = (LayoutParams)getChildAt(b).getLayoutParams();
      int j = layoutParams.openState;
      boolean bool = true;
      if (j == 1) {
        j = 1;
      } else {
        j = 0;
      } 
      if (layoutParams.openState != 2)
        bool = false; 
      if (j != 0 || bool) {
        savedState.openDrawerGravity = layoutParams.gravity;
        break;
      } 
    } 
    savedState.lockModeLeft = this.mLockModeLeft;
    savedState.lockModeRight = this.mLockModeRight;
    savedState.lockModeStart = this.mLockModeStart;
    savedState.lockModeEnd = this.mLockModeEnd;
    return (Parcelable)savedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLeftDragger : Landroidx/customview/widget/ViewDragHelper;
    //   4: aload_1
    //   5: invokevirtual processTouchEvent : (Landroid/view/MotionEvent;)V
    //   8: aload_0
    //   9: getfield mRightDragger : Landroidx/customview/widget/ViewDragHelper;
    //   12: aload_1
    //   13: invokevirtual processTouchEvent : (Landroid/view/MotionEvent;)V
    //   16: aload_1
    //   17: invokevirtual getAction : ()I
    //   20: sipush #255
    //   23: iand
    //   24: istore #4
    //   26: iload #4
    //   28: ifeq -> 182
    //   31: iload #4
    //   33: iconst_1
    //   34: if_icmpeq -> 64
    //   37: iload #4
    //   39: iconst_3
    //   40: if_icmpeq -> 46
    //   43: goto -> 212
    //   46: aload_0
    //   47: iconst_1
    //   48: invokevirtual closeDrawers : (Z)V
    //   51: aload_0
    //   52: iconst_0
    //   53: putfield mDisallowInterceptRequested : Z
    //   56: aload_0
    //   57: iconst_0
    //   58: putfield mChildrenCanceledTouch : Z
    //   61: goto -> 212
    //   64: aload_1
    //   65: invokevirtual getX : ()F
    //   68: fstore_2
    //   69: aload_1
    //   70: invokevirtual getY : ()F
    //   73: fstore_3
    //   74: aload_0
    //   75: getfield mLeftDragger : Landroidx/customview/widget/ViewDragHelper;
    //   78: fload_2
    //   79: f2i
    //   80: fload_3
    //   81: f2i
    //   82: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   85: astore_1
    //   86: aload_1
    //   87: ifnull -> 165
    //   90: aload_0
    //   91: aload_1
    //   92: invokevirtual isContentView : (Landroid/view/View;)Z
    //   95: ifeq -> 165
    //   98: fload_2
    //   99: aload_0
    //   100: getfield mInitialMotionX : F
    //   103: fsub
    //   104: fstore_2
    //   105: fload_3
    //   106: aload_0
    //   107: getfield mInitialMotionY : F
    //   110: fsub
    //   111: fstore_3
    //   112: aload_0
    //   113: getfield mLeftDragger : Landroidx/customview/widget/ViewDragHelper;
    //   116: invokevirtual getTouchSlop : ()I
    //   119: istore #4
    //   121: fload_2
    //   122: fload_2
    //   123: fmul
    //   124: fload_3
    //   125: fload_3
    //   126: fmul
    //   127: fadd
    //   128: iload #4
    //   130: iload #4
    //   132: imul
    //   133: i2f
    //   134: fcmpg
    //   135: ifge -> 165
    //   138: aload_0
    //   139: invokevirtual findOpenDrawer : ()Landroid/view/View;
    //   142: astore_1
    //   143: aload_1
    //   144: ifnull -> 165
    //   147: aload_0
    //   148: aload_1
    //   149: invokevirtual getDrawerLockMode : (Landroid/view/View;)I
    //   152: iconst_2
    //   153: if_icmpne -> 159
    //   156: goto -> 165
    //   159: iconst_0
    //   160: istore #5
    //   162: goto -> 168
    //   165: iconst_1
    //   166: istore #5
    //   168: aload_0
    //   169: iload #5
    //   171: invokevirtual closeDrawers : (Z)V
    //   174: aload_0
    //   175: iconst_0
    //   176: putfield mDisallowInterceptRequested : Z
    //   179: goto -> 212
    //   182: aload_1
    //   183: invokevirtual getX : ()F
    //   186: fstore_2
    //   187: aload_1
    //   188: invokevirtual getY : ()F
    //   191: fstore_3
    //   192: aload_0
    //   193: fload_2
    //   194: putfield mInitialMotionX : F
    //   197: aload_0
    //   198: fload_3
    //   199: putfield mInitialMotionY : F
    //   202: aload_0
    //   203: iconst_0
    //   204: putfield mDisallowInterceptRequested : Z
    //   207: aload_0
    //   208: iconst_0
    //   209: putfield mChildrenCanceledTouch : Z
    //   212: iconst_1
    //   213: ireturn
  }
  
  public void openDrawer(int paramInt) {
    openDrawer(paramInt, true);
  }
  
  public void openDrawer(int paramInt, boolean paramBoolean) {
    View view = findDrawerWithGravity(paramInt);
    if (view != null) {
      openDrawer(view, paramBoolean);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No drawer view found with gravity ");
    stringBuilder.append(gravityToString(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void openDrawer(View paramView) {
    openDrawer(paramView, true);
  }
  
  public void openDrawer(View paramView, boolean paramBoolean) {
    if (isDrawerView(paramView)) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      if (this.mFirstLayout) {
        layoutParams.onScreen = 1.0F;
        layoutParams.openState = 1;
        updateChildrenImportantForAccessibility(paramView, true);
      } else if (paramBoolean) {
        layoutParams.openState |= 0x2;
        if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
          this.mLeftDragger.smoothSlideViewTo(paramView, 0, paramView.getTop());
        } else {
          this.mRightDragger.smoothSlideViewTo(paramView, getWidth() - paramView.getWidth(), paramView.getTop());
        } 
      } else {
        moveDrawerToOffset(paramView, 1.0F);
        updateDrawerState(layoutParams.gravity, 0, paramView);
        paramView.setVisibility(0);
      } 
      invalidate();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a sliding drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void removeDrawerListener(DrawerListener paramDrawerListener) {
    if (paramDrawerListener == null)
      return; 
    List<DrawerListener> list = this.mListeners;
    if (list == null)
      return; 
    list.remove(paramDrawerListener);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    this.mDisallowInterceptRequested = paramBoolean;
    if (paramBoolean)
      closeDrawers(true); 
  }
  
  public void requestLayout() {
    if (!this.mInLayout)
      super.requestLayout(); 
  }
  
  public void setChildInsets(Object paramObject, boolean paramBoolean) {
    this.mLastInsets = paramObject;
    this.mDrawStatusBarBackground = paramBoolean;
    if (!paramBoolean && getBackground() == null) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    setWillNotDraw(paramBoolean);
    requestLayout();
  }
  
  public void setDrawerElevation(float paramFloat) {
    this.mDrawerElevation = paramFloat;
    for (byte b = 0; b < getChildCount(); b++) {
      View view = getChildAt(b);
      if (isDrawerView(view))
        ViewCompat.setElevation(view, this.mDrawerElevation); 
    } 
  }
  
  @Deprecated
  public void setDrawerListener(DrawerListener paramDrawerListener) {
    DrawerListener drawerListener = this.mListener;
    if (drawerListener != null)
      removeDrawerListener(drawerListener); 
    if (paramDrawerListener != null)
      addDrawerListener(paramDrawerListener); 
    this.mListener = paramDrawerListener;
  }
  
  public void setDrawerLockMode(int paramInt) {
    setDrawerLockMode(paramInt, 3);
    setDrawerLockMode(paramInt, 5);
  }
  
  public void setDrawerLockMode(int paramInt1, int paramInt2) {
    int i = GravityCompat.getAbsoluteGravity(paramInt2, ViewCompat.getLayoutDirection((View)this));
    if (paramInt2 != 3) {
      if (paramInt2 != 5) {
        if (paramInt2 != 8388611) {
          if (paramInt2 == 8388613)
            this.mLockModeEnd = paramInt1; 
        } else {
          this.mLockModeStart = paramInt1;
        } 
      } else {
        this.mLockModeRight = paramInt1;
      } 
    } else {
      this.mLockModeLeft = paramInt1;
    } 
    if (paramInt1 != 0) {
      ViewDragHelper viewDragHelper;
      if (i == 3) {
        viewDragHelper = this.mLeftDragger;
      } else {
        viewDragHelper = this.mRightDragger;
      } 
      viewDragHelper.cancel();
    } 
    if (paramInt1 != 1) {
      if (paramInt1 == 2) {
        View view = findDrawerWithGravity(i);
        if (view != null)
          openDrawer(view); 
      } 
    } else {
      View view = findDrawerWithGravity(i);
      if (view != null)
        closeDrawer(view); 
    } 
  }
  
  public void setDrawerLockMode(int paramInt, View paramView) {
    if (isDrawerView(paramView)) {
      setDrawerLockMode(paramInt, ((LayoutParams)paramView.getLayoutParams()).gravity);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a ");
    stringBuilder.append("drawer with appropriate layout_gravity");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setDrawerShadow(int paramInt1, int paramInt2) {
    setDrawerShadow(ContextCompat.getDrawable(getContext(), paramInt1), paramInt2);
  }
  
  public void setDrawerShadow(Drawable paramDrawable, int paramInt) {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION)
      return; 
    if ((paramInt & 0x800003) == 8388611) {
      this.mShadowStart = paramDrawable;
    } else if ((paramInt & 0x800005) == 8388613) {
      this.mShadowEnd = paramDrawable;
    } else if ((paramInt & 0x3) == 3) {
      this.mShadowLeft = paramDrawable;
    } else if ((paramInt & 0x5) == 5) {
      this.mShadowRight = paramDrawable;
    } else {
      return;
    } 
    resolveShadowDrawables();
    invalidate();
  }
  
  public void setDrawerTitle(int paramInt, CharSequence paramCharSequence) {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    if (paramInt == 3) {
      this.mTitleLeft = paramCharSequence;
    } else if (paramInt == 5) {
      this.mTitleRight = paramCharSequence;
    } 
  }
  
  void setDrawerViewOffset(View paramView, float paramFloat) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (paramFloat == layoutParams.onScreen)
      return; 
    layoutParams.onScreen = paramFloat;
    dispatchOnDrawerSlide(paramView, paramFloat);
  }
  
  public void setScrimColor(int paramInt) {
    this.mScrimColor = paramInt;
    invalidate();
  }
  
  public void setStatusBarBackground(int paramInt) {
    Drawable drawable;
    if (paramInt != 0) {
      drawable = ContextCompat.getDrawable(getContext(), paramInt);
    } else {
      drawable = null;
    } 
    this.mStatusBarBackground = drawable;
    invalidate();
  }
  
  public void setStatusBarBackground(Drawable paramDrawable) {
    this.mStatusBarBackground = paramDrawable;
    invalidate();
  }
  
  public void setStatusBarBackgroundColor(int paramInt) {
    this.mStatusBarBackground = (Drawable)new ColorDrawable(paramInt);
    invalidate();
  }
  
  void updateDrawerState(int paramInt1, int paramInt2, View paramView) {
    int j = this.mLeftDragger.getViewDragState();
    int i = this.mRightDragger.getViewDragState();
    byte b = 2;
    if (j == 1 || i == 1) {
      paramInt1 = 1;
    } else {
      paramInt1 = b;
      if (j != 2)
        if (i == 2) {
          paramInt1 = b;
        } else {
          paramInt1 = 0;
        }  
    } 
    if (paramView != null && paramInt2 == 0) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      if (layoutParams.onScreen == 0.0F) {
        dispatchOnDrawerClosed(paramView);
      } else if (layoutParams.onScreen == 1.0F) {
        dispatchOnDrawerOpened(paramView);
      } 
    } 
    if (paramInt1 != this.mDrawerState) {
      this.mDrawerState = paramInt1;
      List<DrawerListener> list = this.mListeners;
      if (list != null)
        for (paramInt2 = list.size() - 1; paramInt2 >= 0; paramInt2--)
          ((DrawerListener)this.mListeners.get(paramInt2)).onDrawerStateChanged(paramInt1);  
    } 
  }
  
  static {
    boolean bool1;
    boolean bool2 = true;
  }
  
  class AccessibilityDelegate extends AccessibilityDelegateCompat {
    private final Rect mTmpRect = new Rect();
    
    final DrawerLayout this$0;
    
    private void addChildrenForAccessibility(AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat, ViewGroup param1ViewGroup) {
      int i = param1ViewGroup.getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = param1ViewGroup.getChildAt(b);
        if (DrawerLayout.includeChildForAccessibility(view))
          param1AccessibilityNodeInfoCompat.addChild(view); 
      } 
    }
    
    private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat1, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat2) {
      Rect rect = this.mTmpRect;
      param1AccessibilityNodeInfoCompat2.getBoundsInParent(rect);
      param1AccessibilityNodeInfoCompat1.setBoundsInParent(rect);
      param1AccessibilityNodeInfoCompat2.getBoundsInScreen(rect);
      param1AccessibilityNodeInfoCompat1.setBoundsInScreen(rect);
      param1AccessibilityNodeInfoCompat1.setVisibleToUser(param1AccessibilityNodeInfoCompat2.isVisibleToUser());
      param1AccessibilityNodeInfoCompat1.setPackageName(param1AccessibilityNodeInfoCompat2.getPackageName());
      param1AccessibilityNodeInfoCompat1.setClassName(param1AccessibilityNodeInfoCompat2.getClassName());
      param1AccessibilityNodeInfoCompat1.setContentDescription(param1AccessibilityNodeInfoCompat2.getContentDescription());
      param1AccessibilityNodeInfoCompat1.setEnabled(param1AccessibilityNodeInfoCompat2.isEnabled());
      param1AccessibilityNodeInfoCompat1.setClickable(param1AccessibilityNodeInfoCompat2.isClickable());
      param1AccessibilityNodeInfoCompat1.setFocusable(param1AccessibilityNodeInfoCompat2.isFocusable());
      param1AccessibilityNodeInfoCompat1.setFocused(param1AccessibilityNodeInfoCompat2.isFocused());
      param1AccessibilityNodeInfoCompat1.setAccessibilityFocused(param1AccessibilityNodeInfoCompat2.isAccessibilityFocused());
      param1AccessibilityNodeInfoCompat1.setSelected(param1AccessibilityNodeInfoCompat2.isSelected());
      param1AccessibilityNodeInfoCompat1.setLongClickable(param1AccessibilityNodeInfoCompat2.isLongClickable());
      param1AccessibilityNodeInfoCompat1.addAction(param1AccessibilityNodeInfoCompat2.getActions());
    }
    
    public boolean dispatchPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      List<CharSequence> list;
      CharSequence charSequence;
      if (param1AccessibilityEvent.getEventType() == 32) {
        list = param1AccessibilityEvent.getText();
        View view = DrawerLayout.this.findVisibleDrawer();
        if (view != null) {
          int i = DrawerLayout.this.getDrawerViewAbsoluteGravity(view);
          charSequence = DrawerLayout.this.getDrawerTitle(i);
          if (charSequence != null)
            list.add(charSequence); 
        } 
        return true;
      } 
      return super.dispatchPopulateAccessibilityEvent((View)list, (AccessibilityEvent)charSequence);
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(DrawerLayout.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      if (DrawerLayout.CAN_HIDE_DESCENDANTS) {
        super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      } else {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(param1AccessibilityNodeInfoCompat);
        super.onInitializeAccessibilityNodeInfo(param1View, accessibilityNodeInfoCompat);
        param1AccessibilityNodeInfoCompat.setSource(param1View);
        ViewParent viewParent = ViewCompat.getParentForAccessibility(param1View);
        if (viewParent instanceof View)
          param1AccessibilityNodeInfoCompat.setParent((View)viewParent); 
        copyNodeInfoNoChildren(param1AccessibilityNodeInfoCompat, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.recycle();
        addChildrenForAccessibility(param1AccessibilityNodeInfoCompat, (ViewGroup)param1View);
      } 
      param1AccessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
      param1AccessibilityNodeInfoCompat.setFocusable(false);
      param1AccessibilityNodeInfoCompat.setFocused(false);
      param1AccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
      param1AccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup param1ViewGroup, View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return (DrawerLayout.CAN_HIDE_DESCENDANTS || DrawerLayout.includeChildForAccessibility(param1View)) ? super.onRequestSendAccessibilityEvent(param1ViewGroup, param1View, param1AccessibilityEvent) : false;
    }
  }
  
  static final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      if (!DrawerLayout.includeChildForAccessibility(param1View))
        param1AccessibilityNodeInfoCompat.setParent(null); 
    }
  }
  
  public static interface DrawerListener {
    void onDrawerClosed(View param1View);
    
    void onDrawerOpened(View param1View);
    
    void onDrawerSlide(View param1View, float param1Float);
    
    void onDrawerStateChanged(int param1Int);
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    private static final int FLAG_IS_CLOSING = 4;
    
    private static final int FLAG_IS_OPENED = 1;
    
    private static final int FLAG_IS_OPENING = 2;
    
    public int gravity = 0;
    
    boolean isPeeking;
    
    float onScreen;
    
    int openState;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3) {
      this(param1Int1, param1Int2);
      this.gravity = param1Int3;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, DrawerLayout.LAYOUT_ATTRS);
      this.gravity = typedArray.getInt(0, 0);
      typedArray.recycle();
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.gravity = param1LayoutParams.gravity;
    }
  }
  
  protected static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public DrawerLayout.SavedState createFromParcel(Parcel param2Parcel) {
          return new DrawerLayout.SavedState(param2Parcel, null);
        }
        
        public DrawerLayout.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new DrawerLayout.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public DrawerLayout.SavedState[] newArray(int param2Int) {
          return new DrawerLayout.SavedState[param2Int];
        }
      };
    
    int lockModeEnd;
    
    int lockModeLeft;
    
    int lockModeRight;
    
    int lockModeStart;
    
    int openDrawerGravity = 0;
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      this.openDrawerGravity = param1Parcel.readInt();
      this.lockModeLeft = param1Parcel.readInt();
      this.lockModeRight = param1Parcel.readInt();
      this.lockModeStart = param1Parcel.readInt();
      this.lockModeEnd = param1Parcel.readInt();
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.openDrawerGravity);
      param1Parcel.writeInt(this.lockModeLeft);
      param1Parcel.writeInt(this.lockModeRight);
      param1Parcel.writeInt(this.lockModeStart);
      param1Parcel.writeInt(this.lockModeEnd);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public DrawerLayout.SavedState createFromParcel(Parcel param1Parcel) {
      return new DrawerLayout.SavedState(param1Parcel, null);
    }
    
    public DrawerLayout.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new DrawerLayout.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public DrawerLayout.SavedState[] newArray(int param1Int) {
      return new DrawerLayout.SavedState[param1Int];
    }
  }
  
  public static abstract class SimpleDrawerListener implements DrawerListener {
    public void onDrawerClosed(View param1View) {}
    
    public void onDrawerOpened(View param1View) {}
    
    public void onDrawerSlide(View param1View, float param1Float) {}
    
    public void onDrawerStateChanged(int param1Int) {}
  }
  
  private class ViewDragCallback extends ViewDragHelper.Callback {
    private final int mAbsGravity;
    
    private ViewDragHelper mDragger;
    
    private final Runnable mPeekRunnable = new Runnable() {
        final DrawerLayout.ViewDragCallback this$1;
        
        public void run() {
          DrawerLayout.ViewDragCallback.this.peekDrawer();
        }
      };
    
    final DrawerLayout this$0;
    
    ViewDragCallback(int param1Int) {
      this.mAbsGravity = param1Int;
    }
    
    private void closeOtherDrawer() {
      int i = this.mAbsGravity;
      byte b = 3;
      if (i == 3)
        b = 5; 
      View view = DrawerLayout.this.findDrawerWithGravity(b);
      if (view != null)
        DrawerLayout.this.closeDrawer(view); 
    }
    
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, 3))
        return Math.max(-param1View.getWidth(), Math.min(param1Int1, 0)); 
      param1Int2 = DrawerLayout.this.getWidth();
      return Math.max(param1Int2 - param1View.getWidth(), Math.min(param1Int1, param1Int2));
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return param1View.getTop();
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      boolean bool;
      if (DrawerLayout.this.isDrawerView(param1View)) {
        bool = param1View.getWidth();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {
      View view;
      if ((param1Int1 & 0x1) == 1) {
        view = DrawerLayout.this.findDrawerWithGravity(3);
      } else {
        view = DrawerLayout.this.findDrawerWithGravity(5);
      } 
      if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0)
        this.mDragger.captureChildView(view, param1Int2); 
    }
    
    public boolean onEdgeLock(int param1Int) {
      return false;
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {
      DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
    }
    
    public void onViewCaptured(View param1View, int param1Int) {
      ((DrawerLayout.LayoutParams)param1View.getLayoutParams()).isPeeking = false;
      closeOtherDrawer();
    }
    
    public void onViewDragStateChanged(int param1Int) {
      DrawerLayout.this.updateDrawerState(this.mAbsGravity, param1Int, this.mDragger.getCapturedView());
    }
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      float f;
      param1Int2 = param1View.getWidth();
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, 3)) {
        f = (param1Int1 + param1Int2);
      } else {
        f = (DrawerLayout.this.getWidth() - param1Int1);
      } 
      f /= param1Int2;
      DrawerLayout.this.setDrawerViewOffset(param1View, f);
      if (f == 0.0F) {
        param1Int1 = 4;
      } else {
        param1Int1 = 0;
      } 
      param1View.setVisibility(param1Int1);
      DrawerLayout.this.invalidate();
    }
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Landroidx/drawerlayout/widget/DrawerLayout;
      //   4: aload_1
      //   5: invokevirtual getDrawerViewOffset : (Landroid/view/View;)F
      //   8: fstore_3
      //   9: aload_1
      //   10: invokevirtual getWidth : ()I
      //   13: istore #6
      //   15: aload_0
      //   16: getfield this$0 : Landroidx/drawerlayout/widget/DrawerLayout;
      //   19: aload_1
      //   20: iconst_3
      //   21: invokevirtual checkDrawerViewAbsoluteGravity : (Landroid/view/View;I)Z
      //   24: ifeq -> 66
      //   27: fload_2
      //   28: fconst_0
      //   29: fcmpl
      //   30: istore #4
      //   32: iload #4
      //   34: ifgt -> 60
      //   37: iload #4
      //   39: ifne -> 52
      //   42: fload_3
      //   43: ldc 0.5
      //   45: fcmpl
      //   46: ifle -> 52
      //   49: goto -> 60
      //   52: iload #6
      //   54: ineg
      //   55: istore #4
      //   57: goto -> 109
      //   60: iconst_0
      //   61: istore #4
      //   63: goto -> 109
      //   66: aload_0
      //   67: getfield this$0 : Landroidx/drawerlayout/widget/DrawerLayout;
      //   70: invokevirtual getWidth : ()I
      //   73: istore #5
      //   75: fload_2
      //   76: fconst_0
      //   77: fcmpg
      //   78: iflt -> 102
      //   81: iload #5
      //   83: istore #4
      //   85: fload_2
      //   86: fconst_0
      //   87: fcmpl
      //   88: ifne -> 109
      //   91: iload #5
      //   93: istore #4
      //   95: fload_3
      //   96: ldc 0.5
      //   98: fcmpl
      //   99: ifle -> 109
      //   102: iload #5
      //   104: iload #6
      //   106: isub
      //   107: istore #4
      //   109: aload_0
      //   110: getfield mDragger : Landroidx/customview/widget/ViewDragHelper;
      //   113: iload #4
      //   115: aload_1
      //   116: invokevirtual getTop : ()I
      //   119: invokevirtual settleCapturedViewAt : (II)Z
      //   122: pop
      //   123: aload_0
      //   124: getfield this$0 : Landroidx/drawerlayout/widget/DrawerLayout;
      //   127: invokevirtual invalidate : ()V
      //   130: return
    }
    
    void peekDrawer() {
      View view;
      int k = this.mDragger.getEdgeSize();
      int i = this.mAbsGravity;
      int j = 0;
      if (i == 3) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i != 0) {
        view = DrawerLayout.this.findDrawerWithGravity(3);
        if (view != null)
          j = -view.getWidth(); 
        j += k;
      } else {
        view = DrawerLayout.this.findDrawerWithGravity(5);
        j = DrawerLayout.this.getWidth() - k;
      } 
      if (view != null && ((i != 0 && view.getLeft() < j) || (i == 0 && view.getLeft() > j)) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams)view.getLayoutParams();
        this.mDragger.smoothSlideViewTo(view, j, view.getTop());
        layoutParams.isPeeking = true;
        DrawerLayout.this.invalidate();
        closeOtherDrawer();
        DrawerLayout.this.cancelChildViewTouch();
      } 
    }
    
    public void removeCallbacks() {
      DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
    }
    
    public void setDragger(ViewDragHelper param1ViewDragHelper) {
      this.mDragger = param1ViewDragHelper;
    }
    
    public boolean tryCaptureView(View param1View, int param1Int) {
      boolean bool;
      if (DrawerLayout.this.isDrawerView(param1View) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(param1View) == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
  
  class null implements Runnable {
    final DrawerLayout.ViewDragCallback this$1;
    
    public void run() {
      this.this$1.peekDrawer();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\drawerlayout\widget\DrawerLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */