package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager extends ViewGroup {
  private static final int CLOSE_ENOUGH = 2;
  
  private static final Comparator<ItemInfo> COMPARATOR;
  
  private static final boolean DEBUG = false;
  
  private static final int DEFAULT_GUTTER_SIZE = 16;
  
  private static final int DEFAULT_OFFSCREEN_PAGES = 1;
  
  private static final int DRAW_ORDER_DEFAULT = 0;
  
  private static final int DRAW_ORDER_FORWARD = 1;
  
  private static final int DRAW_ORDER_REVERSE = 2;
  
  private static final int INVALID_POINTER = -1;
  
  static final int[] LAYOUT_ATTRS = new int[] { 16842931 };
  
  private static final int MAX_SETTLE_DURATION = 600;
  
  private static final int MIN_DISTANCE_FOR_FLING = 25;
  
  private static final int MIN_FLING_VELOCITY = 400;
  
  public static final int SCROLL_STATE_DRAGGING = 1;
  
  public static final int SCROLL_STATE_IDLE = 0;
  
  public static final int SCROLL_STATE_SETTLING = 2;
  
  private static final String TAG = "ViewPager";
  
  private static final boolean USE_CACHE = false;
  
  private static final Interpolator sInterpolator;
  
  private static final ViewPositionComparator sPositionComparator;
  
  private int mActivePointerId = -1;
  
  PagerAdapter mAdapter;
  
  private List<OnAdapterChangeListener> mAdapterChangeListeners;
  
  private int mBottomPageBounds;
  
  private boolean mCalledSuper;
  
  private int mChildHeightMeasureSpec;
  
  private int mChildWidthMeasureSpec;
  
  private int mCloseEnough;
  
  int mCurItem;
  
  private int mDecorChildCount;
  
  private int mDefaultGutterSize;
  
  private int mDrawingOrder;
  
  private ArrayList<View> mDrawingOrderedChildren;
  
  private final Runnable mEndScrollRunnable = new Runnable() {
      final ViewPager this$0;
      
      public void run() {
        ViewPager.this.setScrollState(0);
        ViewPager.this.populate();
      }
    };
  
  private int mExpectedAdapterCount;
  
  private long mFakeDragBeginTime;
  
  private boolean mFakeDragging;
  
  private boolean mFirstLayout = true;
  
  private float mFirstOffset = -3.4028235E38F;
  
  private int mFlingDistance;
  
  private int mGutterSize;
  
  private boolean mInLayout;
  
  private float mInitialMotionX;
  
  private float mInitialMotionY;
  
  private OnPageChangeListener mInternalPageChangeListener;
  
  private boolean mIsBeingDragged;
  
  private boolean mIsScrollStarted;
  
  private boolean mIsUnableToDrag;
  
  private final ArrayList<ItemInfo> mItems = new ArrayList<ItemInfo>();
  
  private float mLastMotionX;
  
  private float mLastMotionY;
  
  private float mLastOffset = Float.MAX_VALUE;
  
  private EdgeEffect mLeftEdge;
  
  private Drawable mMarginDrawable;
  
  private int mMaximumVelocity;
  
  private int mMinimumVelocity;
  
  private boolean mNeedCalculatePageOffsets = false;
  
  private PagerObserver mObserver;
  
  private int mOffscreenPageLimit = 1;
  
  private OnPageChangeListener mOnPageChangeListener;
  
  private List<OnPageChangeListener> mOnPageChangeListeners;
  
  private int mPageMargin;
  
  private PageTransformer mPageTransformer;
  
  private int mPageTransformerLayerType;
  
  private boolean mPopulatePending;
  
  private Parcelable mRestoredAdapterState = null;
  
  private ClassLoader mRestoredClassLoader = null;
  
  private int mRestoredCurItem = -1;
  
  private EdgeEffect mRightEdge;
  
  private int mScrollState = 0;
  
  private Scroller mScroller;
  
  private boolean mScrollingCacheEnabled;
  
  private final ItemInfo mTempItem = new ItemInfo();
  
  private final Rect mTempRect = new Rect();
  
  private int mTopPageBounds;
  
  private int mTouchSlop;
  
  private VelocityTracker mVelocityTracker;
  
  static {
    COMPARATOR = new Comparator<ItemInfo>() {
        public int compare(ViewPager.ItemInfo param1ItemInfo1, ViewPager.ItemInfo param1ItemInfo2) {
          return param1ItemInfo1.position - param1ItemInfo2.position;
        }
      };
    sInterpolator = new Interpolator() {
        public float getInterpolation(float param1Float) {
          param1Float--;
          return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
        }
      };
    sPositionComparator = new ViewPositionComparator();
  }
  
  public ViewPager(Context paramContext) {
    super(paramContext);
    initViewPager();
  }
  
  public ViewPager(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initViewPager();
  }
  
  private void calculatePageOffsets(ItemInfo paramItemInfo1, int paramInt, ItemInfo paramItemInfo2) {
    float f2;
    int m = this.mAdapter.getCount();
    int i = getClientWidth();
    if (i > 0) {
      f2 = this.mPageMargin / i;
    } else {
      f2 = 0.0F;
    } 
    if (paramItemInfo2 != null) {
      i = paramItemInfo2.position;
      if (i < paramItemInfo1.position) {
        f1 = paramItemInfo2.offset + paramItemInfo2.widthFactor + f2;
        i++;
        byte b = 0;
        while (i <= paramItemInfo1.position && b < this.mItems.size()) {
          float f;
          int n;
          paramItemInfo2 = this.mItems.get(b);
          while (true) {
            n = i;
            f = f1;
            if (i > paramItemInfo2.position) {
              n = i;
              f = f1;
              if (b < this.mItems.size() - 1) {
                paramItemInfo2 = this.mItems.get(++b);
                continue;
              } 
            } 
            break;
          } 
          while (n < paramItemInfo2.position) {
            f += this.mAdapter.getPageWidth(n) + f2;
            n++;
          } 
          paramItemInfo2.offset = f;
          f1 = f + paramItemInfo2.widthFactor + f2;
          i = n + 1;
        } 
      } else if (i > paramItemInfo1.position) {
        int n = this.mItems.size() - 1;
        f1 = paramItemInfo2.offset;
        while (--i >= paramItemInfo1.position && n >= 0) {
          float f;
          int i1;
          paramItemInfo2 = this.mItems.get(n);
          while (true) {
            i1 = i;
            f = f1;
            if (i < paramItemInfo2.position) {
              i1 = i;
              f = f1;
              if (n > 0) {
                paramItemInfo2 = this.mItems.get(--n);
                continue;
              } 
            } 
            break;
          } 
          while (i1 > paramItemInfo2.position) {
            f -= this.mAdapter.getPageWidth(i1) + f2;
            i1--;
          } 
          f1 = f - paramItemInfo2.widthFactor + f2;
          paramItemInfo2.offset = f1;
          i = i1 - 1;
        } 
      } 
    } 
    int k = this.mItems.size();
    float f3 = paramItemInfo1.offset;
    i = paramItemInfo1.position - 1;
    if (paramItemInfo1.position == 0) {
      f1 = paramItemInfo1.offset;
    } else {
      f1 = -3.4028235E38F;
    } 
    this.mFirstOffset = f1;
    int j = paramItemInfo1.position;
    if (j == --m) {
      f1 = paramItemInfo1.offset + paramItemInfo1.widthFactor - 1.0F;
    } else {
      f1 = Float.MAX_VALUE;
    } 
    this.mLastOffset = f1;
    j = paramInt - 1;
    float f1 = f3;
    while (j >= 0) {
      paramItemInfo2 = this.mItems.get(j);
      while (i > paramItemInfo2.position) {
        f1 -= this.mAdapter.getPageWidth(i) + f2;
        i--;
      } 
      f1 -= paramItemInfo2.widthFactor + f2;
      paramItemInfo2.offset = f1;
      if (paramItemInfo2.position == 0)
        this.mFirstOffset = f1; 
      j--;
      i--;
    } 
    f1 = paramItemInfo1.offset + paramItemInfo1.widthFactor + f2;
    j = paramItemInfo1.position + 1;
    i = paramInt + 1;
    for (paramInt = j; i < k; paramInt++) {
      paramItemInfo1 = this.mItems.get(i);
      while (paramInt < paramItemInfo1.position) {
        f1 += this.mAdapter.getPageWidth(paramInt) + f2;
        paramInt++;
      } 
      if (paramItemInfo1.position == m)
        this.mLastOffset = paramItemInfo1.widthFactor + f1 - 1.0F; 
      paramItemInfo1.offset = f1;
      f1 += paramItemInfo1.widthFactor + f2;
      i++;
    } 
    this.mNeedCalculatePageOffsets = false;
  }
  
  private void completeScroll(boolean paramBoolean) {
    boolean bool;
    if (this.mScrollState == 2) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      setScrollingCacheEnabled(false);
      if ((this.mScroller.isFinished() ^ true) != 0) {
        this.mScroller.abortAnimation();
        int k = getScrollX();
        int m = getScrollY();
        int i = this.mScroller.getCurrX();
        int j = this.mScroller.getCurrY();
        if (k != i || m != j) {
          scrollTo(i, j);
          if (i != k)
            pageScrolled(i); 
        } 
      } 
    } 
    this.mPopulatePending = false;
    for (byte b = 0; b < this.mItems.size(); b++) {
      ItemInfo itemInfo = this.mItems.get(b);
      if (itemInfo.scrolling) {
        itemInfo.scrolling = false;
        bool = true;
      } 
    } 
    if (bool)
      if (paramBoolean) {
        ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
      } else {
        this.mEndScrollRunnable.run();
      }  
  }
  
  private int determineTargetPage(int paramInt1, float paramFloat, int paramInt2, int paramInt3) {
    if (Math.abs(paramInt3) > this.mFlingDistance && Math.abs(paramInt2) > this.mMinimumVelocity) {
      if (paramInt2 <= 0)
        paramInt1++; 
    } else {
      float f;
      if (paramInt1 >= this.mCurItem) {
        f = 0.4F;
      } else {
        f = 0.6F;
      } 
      paramInt1 += (int)(paramFloat + f);
    } 
    paramInt2 = paramInt1;
    if (this.mItems.size() > 0) {
      ItemInfo itemInfo1 = this.mItems.get(0);
      ArrayList<ItemInfo> arrayList = this.mItems;
      ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
      paramInt2 = Math.max(itemInfo1.position, Math.min(paramInt1, itemInfo2.position));
    } 
    return paramInt2;
  }
  
  private void dispatchOnPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
    OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
    if (onPageChangeListener2 != null)
      onPageChangeListener2.onPageScrolled(paramInt1, paramFloat, paramInt2); 
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null) {
      byte b = 0;
      int i = list.size();
      while (b < i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(b);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageScrolled(paramInt1, paramFloat, paramInt2); 
        b++;
      } 
    } 
    OnPageChangeListener onPageChangeListener1 = this.mInternalPageChangeListener;
    if (onPageChangeListener1 != null)
      onPageChangeListener1.onPageScrolled(paramInt1, paramFloat, paramInt2); 
  }
  
  private void dispatchOnPageSelected(int paramInt) {
    OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
    if (onPageChangeListener2 != null)
      onPageChangeListener2.onPageSelected(paramInt); 
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null) {
      byte b = 0;
      int i = list.size();
      while (b < i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(b);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageSelected(paramInt); 
        b++;
      } 
    } 
    OnPageChangeListener onPageChangeListener1 = this.mInternalPageChangeListener;
    if (onPageChangeListener1 != null)
      onPageChangeListener1.onPageSelected(paramInt); 
  }
  
  private void dispatchOnScrollStateChanged(int paramInt) {
    OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
    if (onPageChangeListener2 != null)
      onPageChangeListener2.onPageScrollStateChanged(paramInt); 
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null) {
      byte b = 0;
      int i = list.size();
      while (b < i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(b);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageScrollStateChanged(paramInt); 
        b++;
      } 
    } 
    OnPageChangeListener onPageChangeListener1 = this.mInternalPageChangeListener;
    if (onPageChangeListener1 != null)
      onPageChangeListener1.onPageScrollStateChanged(paramInt); 
  }
  
  private void enableLayers(boolean paramBoolean) {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      boolean bool;
      if (paramBoolean) {
        bool = this.mPageTransformerLayerType;
      } else {
        bool = false;
      } 
      getChildAt(b).setLayerType(bool, null);
    } 
  }
  
  private void endDrag() {
    this.mIsBeingDragged = false;
    this.mIsUnableToDrag = false;
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  private Rect getChildRectInPagerCoordinates(Rect paramRect, View paramView) {
    Rect rect = paramRect;
    if (paramRect == null)
      rect = new Rect(); 
    if (paramView == null) {
      rect.set(0, 0, 0, 0);
      return rect;
    } 
    rect.left = paramView.getLeft();
    rect.right = paramView.getRight();
    rect.top = paramView.getTop();
    rect.bottom = paramView.getBottom();
    ViewParent viewParent = paramView.getParent();
    while (viewParent instanceof ViewGroup && viewParent != this) {
      ViewGroup viewGroup = (ViewGroup)viewParent;
      rect.left += viewGroup.getLeft();
      rect.right += viewGroup.getRight();
      rect.top += viewGroup.getTop();
      rect.bottom += viewGroup.getBottom();
      ViewParent viewParent1 = viewGroup.getParent();
    } 
    return rect;
  }
  
  private int getClientWidth() {
    return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
  }
  
  private ItemInfo infoForCurrentScrollPosition() {
    float f1;
    float f2;
    int i = getClientWidth();
    float f3 = 0.0F;
    if (i > 0) {
      f1 = getScrollX() / i;
    } else {
      f1 = 0.0F;
    } 
    if (i > 0) {
      f2 = this.mPageMargin / i;
    } else {
      f2 = 0.0F;
    } 
    ItemInfo itemInfo = null;
    float f4 = 0.0F;
    int j = -1;
    i = 0;
    boolean bool = true;
    while (i < this.mItems.size()) {
      ItemInfo itemInfo2 = this.mItems.get(i);
      int k = i;
      ItemInfo itemInfo1 = itemInfo2;
      if (!bool) {
        int m = itemInfo2.position;
        j++;
        k = i;
        itemInfo1 = itemInfo2;
        if (m != j) {
          itemInfo1 = this.mTempItem;
          itemInfo1.offset = f3 + f4 + f2;
          itemInfo1.position = j;
          itemInfo1.widthFactor = this.mAdapter.getPageWidth(itemInfo1.position);
          k = i - 1;
        } 
      } 
      f3 = itemInfo1.offset;
      f4 = itemInfo1.widthFactor;
      if (bool || f1 >= f3) {
        if (f1 < f4 + f3 + f2 || k == this.mItems.size() - 1)
          return itemInfo1; 
        j = itemInfo1.position;
        f4 = itemInfo1.widthFactor;
        i = k + 1;
        bool = false;
        itemInfo = itemInfo1;
        continue;
      } 
      return itemInfo;
    } 
    return itemInfo;
  }
  
  private static boolean isDecorView(View paramView) {
    boolean bool;
    if (paramView.getClass().getAnnotation(DecorView.class) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean isGutterDrag(float paramFloat1, float paramFloat2) {
    boolean bool;
    if ((paramFloat1 < this.mGutterSize && paramFloat2 > 0.0F) || (paramFloat1 > (getWidth() - this.mGutterSize) && paramFloat2 < 0.0F)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId) {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      this.mLastMotionX = paramMotionEvent.getX(i);
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      VelocityTracker velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null)
        velocityTracker.clear(); 
    } 
  }
  
  private boolean pageScrolled(int paramInt) {
    if (this.mItems.size() == 0) {
      if (this.mFirstLayout)
        return false; 
      this.mCalledSuper = false;
      onPageScrolled(0, 0.0F, 0);
      if (this.mCalledSuper)
        return false; 
      throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    } 
    ItemInfo itemInfo = infoForCurrentScrollPosition();
    int k = getClientWidth();
    int j = this.mPageMargin;
    float f2 = j;
    float f1 = k;
    f2 /= f1;
    int i = itemInfo.position;
    f1 = (paramInt / f1 - itemInfo.offset) / (itemInfo.widthFactor + f2);
    paramInt = (int)((k + j) * f1);
    this.mCalledSuper = false;
    onPageScrolled(i, f1, paramInt);
    if (this.mCalledSuper)
      return true; 
    throw new IllegalStateException("onPageScrolled did not call superclass implementation");
  }
  
  private boolean performDrag(float paramFloat) {
    boolean bool1;
    float f1 = this.mLastMotionX;
    this.mLastMotionX = paramFloat;
    float f2 = getScrollX() + f1 - paramFloat;
    float f3 = getClientWidth();
    paramFloat = this.mFirstOffset * f3;
    f1 = this.mLastOffset * f3;
    ArrayList<ItemInfo> arrayList1 = this.mItems;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool2 = false;
    ItemInfo itemInfo1 = arrayList1.get(0);
    ArrayList<ItemInfo> arrayList2 = this.mItems;
    ItemInfo itemInfo2 = arrayList2.get(arrayList2.size() - 1);
    if (itemInfo1.position != 0) {
      paramFloat = itemInfo1.offset * f3;
      i = 0;
    } else {
      i = 1;
    } 
    if (itemInfo2.position != this.mAdapter.getCount() - 1) {
      f1 = itemInfo2.offset * f3;
      bool1 = false;
    } else {
      bool1 = true;
    } 
    if (f2 < paramFloat) {
      if (i) {
        this.mLeftEdge.onPull(Math.abs(paramFloat - f2) / f3);
        bool2 = true;
      } 
    } else {
      bool2 = bool4;
      paramFloat = f2;
      if (f2 > f1) {
        bool2 = bool3;
        if (bool1) {
          this.mRightEdge.onPull(Math.abs(f2 - f1) / f3);
          bool2 = true;
        } 
        paramFloat = f1;
      } 
    } 
    f1 = this.mLastMotionX;
    int i = (int)paramFloat;
    this.mLastMotionX = f1 + paramFloat - i;
    scrollTo(i, getScrollY());
    pageScrolled(i);
    return bool2;
  }
  
  private void recomputeScrollPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt2 > 0 && !this.mItems.isEmpty()) {
      if (!this.mScroller.isFinished()) {
        this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
      } else {
        int i = getPaddingLeft();
        int j = getPaddingRight();
        int k = getPaddingLeft();
        int m = getPaddingRight();
        scrollTo((int)(getScrollX() / (paramInt2 - k - m + paramInt4) * (paramInt1 - i - j + paramInt3)), getScrollY());
      } 
    } else {
      float f;
      ItemInfo itemInfo = infoForPosition(this.mCurItem);
      if (itemInfo != null) {
        f = Math.min(itemInfo.offset, this.mLastOffset);
      } else {
        f = 0.0F;
      } 
      paramInt1 = (int)(f * (paramInt1 - getPaddingLeft() - getPaddingRight()));
      if (paramInt1 != getScrollX()) {
        completeScroll(false);
        scrollTo(paramInt1, getScrollY());
      } 
    } 
  }
  
  private void removeNonDecorViews() {
    for (int i = 0; i < getChildCount(); i = j + 1) {
      int j = i;
      if (!((LayoutParams)getChildAt(i).getLayoutParams()).isDecor) {
        removeViewAt(i);
        j = i - 1;
      } 
    } 
  }
  
  private void requestParentDisallowInterceptTouchEvent(boolean paramBoolean) {
    ViewParent viewParent = getParent();
    if (viewParent != null)
      viewParent.requestDisallowInterceptTouchEvent(paramBoolean); 
  }
  
  private boolean resetTouch() {
    this.mActivePointerId = -1;
    endDrag();
    this.mLeftEdge.onRelease();
    this.mRightEdge.onRelease();
    return (this.mLeftEdge.isFinished() || this.mRightEdge.isFinished());
  }
  
  private void scrollToItem(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2) {
    boolean bool;
    ItemInfo itemInfo = infoForPosition(paramInt1);
    if (itemInfo != null) {
      bool = (int)(getClientWidth() * Math.max(this.mFirstOffset, Math.min(itemInfo.offset, this.mLastOffset)));
    } else {
      bool = false;
    } 
    if (paramBoolean1) {
      smoothScrollTo(bool, 0, paramInt2);
      if (paramBoolean2)
        dispatchOnPageSelected(paramInt1); 
    } else {
      if (paramBoolean2)
        dispatchOnPageSelected(paramInt1); 
      completeScroll(false);
      scrollTo(bool, 0);
      pageScrolled(bool);
    } 
  }
  
  private void setScrollingCacheEnabled(boolean paramBoolean) {
    if (this.mScrollingCacheEnabled != paramBoolean)
      this.mScrollingCacheEnabled = paramBoolean; 
  }
  
  private void sortChildDrawingOrder() {
    if (this.mDrawingOrder != 0) {
      ArrayList<View> arrayList = this.mDrawingOrderedChildren;
      if (arrayList == null) {
        this.mDrawingOrderedChildren = new ArrayList<View>();
      } else {
        arrayList.clear();
      } 
      int i = getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = getChildAt(b);
        this.mDrawingOrderedChildren.add(view);
      } 
      Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
    } 
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2) {
    int j = paramArrayList.size();
    int i = getDescendantFocusability();
    if (i != 393216)
      for (byte b = 0; b < getChildCount(); b++) {
        View view = getChildAt(b);
        if (view.getVisibility() == 0) {
          ItemInfo itemInfo = infoForChild(view);
          if (itemInfo != null && itemInfo.position == this.mCurItem)
            view.addFocusables(paramArrayList, paramInt1, paramInt2); 
        } 
      }  
    if (i != 262144 || j == paramArrayList.size()) {
      if (!isFocusable())
        return; 
      if ((paramInt2 & 0x1) == 1 && isInTouchMode() && !isFocusableInTouchMode())
        return; 
      if (paramArrayList != null)
        paramArrayList.add(this); 
    } 
  }
  
  ItemInfo addNewItem(int paramInt1, int paramInt2) {
    ItemInfo itemInfo = new ItemInfo();
    itemInfo.position = paramInt1;
    itemInfo.object = this.mAdapter.instantiateItem(this, paramInt1);
    itemInfo.widthFactor = this.mAdapter.getPageWidth(paramInt1);
    if (paramInt2 < 0 || paramInt2 >= this.mItems.size()) {
      this.mItems.add(itemInfo);
      return itemInfo;
    } 
    this.mItems.add(paramInt2, itemInfo);
    return itemInfo;
  }
  
  public void addOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener) {
    if (this.mAdapterChangeListeners == null)
      this.mAdapterChangeListeners = new ArrayList<OnAdapterChangeListener>(); 
    this.mAdapterChangeListeners.add(paramOnAdapterChangeListener);
  }
  
  public void addOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    if (this.mOnPageChangeListeners == null)
      this.mOnPageChangeListeners = new ArrayList<OnPageChangeListener>(); 
    this.mOnPageChangeListeners.add(paramOnPageChangeListener);
  }
  
  public void addTouchables(ArrayList<View> paramArrayList) {
    for (byte b = 0; b < getChildCount(); b++) {
      View view = getChildAt(b);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem)
          view.addTouchables(paramArrayList); 
      } 
    } 
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    ViewGroup.LayoutParams layoutParams = paramLayoutParams;
    if (!checkLayoutParams(paramLayoutParams))
      layoutParams = generateLayoutParams(paramLayoutParams); 
    paramLayoutParams = layoutParams;
    ((LayoutParams)paramLayoutParams).isDecor |= isDecorView(paramView);
    if (this.mInLayout) {
      if (paramLayoutParams == null || !((LayoutParams)paramLayoutParams).isDecor) {
        ((LayoutParams)paramLayoutParams).needsMeasure = true;
        addViewInLayout(paramView, paramInt, layoutParams);
        return;
      } 
      throw new IllegalStateException("Cannot add pager decor view during layout");
    } 
    super.addView(paramView, paramInt, layoutParams);
  }
  
  public boolean arrowScroll(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual findFocus : ()Landroid/view/View;
    //   4: astore #6
    //   6: iconst_0
    //   7: istore #4
    //   9: aload #6
    //   11: aload_0
    //   12: if_acmpne -> 21
    //   15: aconst_null
    //   16: astore #5
    //   18: goto -> 194
    //   21: aload #6
    //   23: astore #5
    //   25: aload #6
    //   27: ifnull -> 194
    //   30: aload #6
    //   32: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   35: astore #5
    //   37: aload #5
    //   39: instanceof android/view/ViewGroup
    //   42: ifeq -> 68
    //   45: aload #5
    //   47: aload_0
    //   48: if_acmpne -> 56
    //   51: iconst_1
    //   52: istore_2
    //   53: goto -> 70
    //   56: aload #5
    //   58: invokeinterface getParent : ()Landroid/view/ViewParent;
    //   63: astore #5
    //   65: goto -> 37
    //   68: iconst_0
    //   69: istore_2
    //   70: aload #6
    //   72: astore #5
    //   74: iload_2
    //   75: ifne -> 194
    //   78: new java/lang/StringBuilder
    //   81: dup
    //   82: invokespecial <init> : ()V
    //   85: astore #7
    //   87: aload #7
    //   89: aload #6
    //   91: invokevirtual getClass : ()Ljava/lang/Class;
    //   94: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   97: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: aload #6
    //   103: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   106: astore #5
    //   108: aload #5
    //   110: instanceof android/view/ViewGroup
    //   113: ifeq -> 151
    //   116: aload #7
    //   118: ldc_w ' => '
    //   121: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload #7
    //   127: aload #5
    //   129: invokevirtual getClass : ()Ljava/lang/Class;
    //   132: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   135: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: aload #5
    //   141: invokeinterface getParent : ()Landroid/view/ViewParent;
    //   146: astore #5
    //   148: goto -> 108
    //   151: new java/lang/StringBuilder
    //   154: dup
    //   155: invokespecial <init> : ()V
    //   158: astore #5
    //   160: aload #5
    //   162: ldc_w 'arrowScroll tried to find focus based on non-child current focused view '
    //   165: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: pop
    //   169: aload #5
    //   171: aload #7
    //   173: invokevirtual toString : ()Ljava/lang/String;
    //   176: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: pop
    //   180: ldc 'ViewPager'
    //   182: aload #5
    //   184: invokevirtual toString : ()Ljava/lang/String;
    //   187: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   190: pop
    //   191: goto -> 15
    //   194: invokestatic getInstance : ()Landroid/view/FocusFinder;
    //   197: aload_0
    //   198: aload #5
    //   200: iload_1
    //   201: invokevirtual findNextFocus : (Landroid/view/ViewGroup;Landroid/view/View;I)Landroid/view/View;
    //   204: astore #6
    //   206: aload #6
    //   208: ifnull -> 344
    //   211: aload #6
    //   213: aload #5
    //   215: if_acmpeq -> 344
    //   218: iload_1
    //   219: bipush #17
    //   221: if_icmpne -> 281
    //   224: aload_0
    //   225: aload_0
    //   226: getfield mTempRect : Landroid/graphics/Rect;
    //   229: aload #6
    //   231: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   234: getfield left : I
    //   237: istore_3
    //   238: aload_0
    //   239: aload_0
    //   240: getfield mTempRect : Landroid/graphics/Rect;
    //   243: aload #5
    //   245: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   248: getfield left : I
    //   251: istore_2
    //   252: aload #5
    //   254: ifnull -> 271
    //   257: iload_3
    //   258: iload_2
    //   259: if_icmplt -> 271
    //   262: aload_0
    //   263: invokevirtual pageLeft : ()Z
    //   266: istore #4
    //   268: goto -> 278
    //   271: aload #6
    //   273: invokevirtual requestFocus : ()Z
    //   276: istore #4
    //   278: goto -> 384
    //   281: iload_1
    //   282: bipush #66
    //   284: if_icmpne -> 384
    //   287: aload_0
    //   288: aload_0
    //   289: getfield mTempRect : Landroid/graphics/Rect;
    //   292: aload #6
    //   294: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   297: getfield left : I
    //   300: istore_2
    //   301: aload_0
    //   302: aload_0
    //   303: getfield mTempRect : Landroid/graphics/Rect;
    //   306: aload #5
    //   308: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   311: getfield left : I
    //   314: istore_3
    //   315: aload #5
    //   317: ifnull -> 334
    //   320: iload_2
    //   321: iload_3
    //   322: if_icmpgt -> 334
    //   325: aload_0
    //   326: invokevirtual pageRight : ()Z
    //   329: istore #4
    //   331: goto -> 278
    //   334: aload #6
    //   336: invokevirtual requestFocus : ()Z
    //   339: istore #4
    //   341: goto -> 278
    //   344: iload_1
    //   345: bipush #17
    //   347: if_icmpeq -> 378
    //   350: iload_1
    //   351: iconst_1
    //   352: if_icmpne -> 358
    //   355: goto -> 378
    //   358: iload_1
    //   359: bipush #66
    //   361: if_icmpeq -> 369
    //   364: iload_1
    //   365: iconst_2
    //   366: if_icmpne -> 384
    //   369: aload_0
    //   370: invokevirtual pageRight : ()Z
    //   373: istore #4
    //   375: goto -> 384
    //   378: aload_0
    //   379: invokevirtual pageLeft : ()Z
    //   382: istore #4
    //   384: iload #4
    //   386: ifeq -> 397
    //   389: aload_0
    //   390: iload_1
    //   391: invokestatic getContantForFocusDirection : (I)I
    //   394: invokevirtual playSoundEffect : (I)V
    //   397: iload #4
    //   399: ireturn
  }
  
  public boolean beginFakeDrag() {
    if (this.mIsBeingDragged)
      return false; 
    this.mFakeDragging = true;
    setScrollState(1);
    this.mLastMotionX = 0.0F;
    this.mInitialMotionX = 0.0F;
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    } else {
      velocityTracker.clear();
    } 
    long l = SystemClock.uptimeMillis();
    MotionEvent motionEvent = MotionEvent.obtain(l, l, 0, 0.0F, 0.0F, 0);
    this.mVelocityTracker.addMovement(motionEvent);
    motionEvent.recycle();
    this.mFakeDragBeginTime = l;
    return true;
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) {
    boolean bool1 = paramView instanceof ViewGroup;
    boolean bool = true;
    if (bool1) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      int k = paramView.getScrollX();
      int j = paramView.getScrollY();
      for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
        View view = viewGroup.getChildAt(i);
        int m = paramInt2 + k;
        if (m >= view.getLeft() && m < view.getRight()) {
          int n = paramInt3 + j;
          if (n >= view.getTop() && n < view.getBottom() && canScroll(view, true, paramInt1, m - view.getLeft(), n - view.getTop()))
            return true; 
        } 
      } 
    } 
    if (paramBoolean && paramView.canScrollHorizontally(-paramInt1)) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    } 
    return paramBoolean;
  }
  
  public boolean canScrollHorizontally(int paramInt) {
    PagerAdapter pagerAdapter = this.mAdapter;
    boolean bool2 = false;
    boolean bool1 = false;
    if (pagerAdapter == null)
      return false; 
    int j = getClientWidth();
    int i = getScrollX();
    if (paramInt < 0) {
      if (i > (int)(j * this.mFirstOffset))
        bool1 = true; 
      return bool1;
    } 
    bool1 = bool2;
    if (paramInt > 0) {
      bool1 = bool2;
      if (i < (int)(j * this.mLastOffset))
        bool1 = true; 
    } 
    return bool1;
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
  
  public void clearOnPageChangeListeners() {
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null)
      list.clear(); 
  }
  
  public void computeScroll() {
    this.mIsScrollStarted = true;
    if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
      int m = getScrollX();
      int k = getScrollY();
      int i = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      if (m != i || k != j) {
        scrollTo(i, j);
        if (!pageScrolled(i)) {
          this.mScroller.abortAnimation();
          scrollTo(0, j);
        } 
      } 
      ViewCompat.postInvalidateOnAnimation((View)this);
      return;
    } 
    completeScroll(true);
  }
  
  void dataSetChanged() {
    Object object1;
    byte b;
    Object object2;
    int i;
    boolean bool2;
    int m = this.mAdapter.getCount();
    this.mExpectedAdapterCount = m;
    if (this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < m) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    int j = this.mCurItem;
    int k = 0;
    boolean bool1 = false;
    while (k < this.mItems.size()) {
      ItemInfo itemInfo = this.mItems.get(k);
      int i2 = this.mAdapter.getItemPosition(itemInfo.object);
      if (i2 == -1) {
        Object object3 = object2;
        int i3 = k;
        Object object4 = object1;
        continue;
      } 
      if (i2 == -2) {
        byte b2;
        this.mItems.remove(k);
        int i3 = k - 1;
        Object object = object1;
        if (object1 == null) {
          this.mAdapter.startUpdate(this);
          b2 = 1;
        } 
        this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
        k = i3;
        b = b2;
        if (this.mCurItem == itemInfo.position) {
          i = Math.max(0, Math.min(this.mCurItem, m - 1));
          b = b2;
          k = i3;
        } 
      } else {
        int i3 = i;
        int i4 = k;
        byte b2 = b;
        if (itemInfo.position != i2) {
          if (itemInfo.position == this.mCurItem)
            i = i2; 
          itemInfo.position = i2;
        } else {
          continue;
        } 
      } 
      bool2 = true;
      int n = i;
      int i1 = k;
      byte b1 = b;
      continue;
      k = SYNTHETIC_LOCAL_VARIABLE_7 + 1;
      object2 = SYNTHETIC_LOCAL_VARIABLE_5;
      object1 = SYNTHETIC_LOCAL_VARIABLE_6;
    } 
    if (b)
      this.mAdapter.finishUpdate(this); 
    Collections.sort(this.mItems, COMPARATOR);
    if (bool2) {
      k = getChildCount();
      for (b = 0; b < k; b++) {
        LayoutParams layoutParams = (LayoutParams)getChildAt(b).getLayoutParams();
        if (!layoutParams.isDecor)
          layoutParams.widthFactor = 0.0F; 
      } 
      setCurrentItemInternal(i, false, true);
      requestLayout();
    } 
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    return (super.dispatchKeyEvent(paramKeyEvent) || executeKeyEvent(paramKeyEvent));
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    if (paramAccessibilityEvent.getEventType() == 4096)
      return super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent); 
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem && view.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent))
          return true; 
      } 
    } 
    return false;
  }
  
  float distanceInfluenceForSnapDuration(float paramFloat) {
    return (float)Math.sin(((paramFloat - 0.5F) * 0.47123894F));
  }
  
  public void draw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial draw : (Landroid/graphics/Canvas;)V
    //   5: aload_0
    //   6: invokevirtual getOverScrollMode : ()I
    //   9: istore #4
    //   11: iconst_0
    //   12: istore_3
    //   13: iconst_0
    //   14: istore_2
    //   15: iload #4
    //   17: ifeq -> 66
    //   20: iload #4
    //   22: iconst_1
    //   23: if_icmpne -> 49
    //   26: aload_0
    //   27: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   30: astore #8
    //   32: aload #8
    //   34: ifnull -> 49
    //   37: aload #8
    //   39: invokevirtual getCount : ()I
    //   42: iconst_1
    //   43: if_icmple -> 49
    //   46: goto -> 66
    //   49: aload_0
    //   50: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   53: invokevirtual finish : ()V
    //   56: aload_0
    //   57: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   60: invokevirtual finish : ()V
    //   63: goto -> 257
    //   66: aload_0
    //   67: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   70: invokevirtual isFinished : ()Z
    //   73: ifne -> 155
    //   76: aload_1
    //   77: invokevirtual save : ()I
    //   80: istore_3
    //   81: aload_0
    //   82: invokevirtual getHeight : ()I
    //   85: aload_0
    //   86: invokevirtual getPaddingTop : ()I
    //   89: isub
    //   90: aload_0
    //   91: invokevirtual getPaddingBottom : ()I
    //   94: isub
    //   95: istore_2
    //   96: aload_0
    //   97: invokevirtual getWidth : ()I
    //   100: istore #4
    //   102: aload_1
    //   103: ldc_w 270.0
    //   106: invokevirtual rotate : (F)V
    //   109: aload_1
    //   110: iload_2
    //   111: ineg
    //   112: aload_0
    //   113: invokevirtual getPaddingTop : ()I
    //   116: iadd
    //   117: i2f
    //   118: aload_0
    //   119: getfield mFirstOffset : F
    //   122: iload #4
    //   124: i2f
    //   125: fmul
    //   126: invokevirtual translate : (FF)V
    //   129: aload_0
    //   130: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   133: iload_2
    //   134: iload #4
    //   136: invokevirtual setSize : (II)V
    //   139: iconst_0
    //   140: aload_0
    //   141: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   144: aload_1
    //   145: invokevirtual draw : (Landroid/graphics/Canvas;)Z
    //   148: ior
    //   149: istore_2
    //   150: aload_1
    //   151: iload_3
    //   152: invokevirtual restoreToCount : (I)V
    //   155: iload_2
    //   156: istore_3
    //   157: aload_0
    //   158: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   161: invokevirtual isFinished : ()Z
    //   164: ifne -> 257
    //   167: aload_1
    //   168: invokevirtual save : ()I
    //   171: istore #4
    //   173: aload_0
    //   174: invokevirtual getWidth : ()I
    //   177: istore #7
    //   179: aload_0
    //   180: invokevirtual getHeight : ()I
    //   183: istore #5
    //   185: aload_0
    //   186: invokevirtual getPaddingTop : ()I
    //   189: istore #6
    //   191: aload_0
    //   192: invokevirtual getPaddingBottom : ()I
    //   195: istore_3
    //   196: aload_1
    //   197: ldc_w 90.0
    //   200: invokevirtual rotate : (F)V
    //   203: aload_1
    //   204: aload_0
    //   205: invokevirtual getPaddingTop : ()I
    //   208: ineg
    //   209: i2f
    //   210: aload_0
    //   211: getfield mLastOffset : F
    //   214: fconst_1
    //   215: fadd
    //   216: fneg
    //   217: iload #7
    //   219: i2f
    //   220: fmul
    //   221: invokevirtual translate : (FF)V
    //   224: aload_0
    //   225: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   228: iload #5
    //   230: iload #6
    //   232: isub
    //   233: iload_3
    //   234: isub
    //   235: iload #7
    //   237: invokevirtual setSize : (II)V
    //   240: iload_2
    //   241: aload_0
    //   242: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   245: aload_1
    //   246: invokevirtual draw : (Landroid/graphics/Canvas;)Z
    //   249: ior
    //   250: istore_3
    //   251: aload_1
    //   252: iload #4
    //   254: invokevirtual restoreToCount : (I)V
    //   257: iload_3
    //   258: ifeq -> 265
    //   261: aload_0
    //   262: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   265: return
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    Drawable drawable = this.mMarginDrawable;
    if (drawable != null && drawable.isStateful())
      drawable.setState(getDrawableState()); 
  }
  
  public void endFakeDrag() {
    if (this.mFakeDragging) {
      if (this.mAdapter != null) {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        int j = (int)velocityTracker.getXVelocity(this.mActivePointerId);
        this.mPopulatePending = true;
        int i = getClientWidth();
        int k = getScrollX();
        ItemInfo itemInfo = infoForCurrentScrollPosition();
        setCurrentItemInternal(determineTargetPage(itemInfo.position, (k / i - itemInfo.offset) / itemInfo.widthFactor, j, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, j);
      } 
      endDrag();
      this.mFakeDragging = false;
      return;
    } 
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getAction : ()I
    //   4: ifne -> 118
    //   7: aload_1
    //   8: invokevirtual getKeyCode : ()I
    //   11: istore_2
    //   12: iload_2
    //   13: bipush #21
    //   15: if_icmpeq -> 92
    //   18: iload_2
    //   19: bipush #22
    //   21: if_icmpeq -> 66
    //   24: iload_2
    //   25: bipush #61
    //   27: if_icmpeq -> 33
    //   30: goto -> 118
    //   33: aload_1
    //   34: invokevirtual hasNoModifiers : ()Z
    //   37: ifeq -> 49
    //   40: aload_0
    //   41: iconst_2
    //   42: invokevirtual arrowScroll : (I)Z
    //   45: istore_3
    //   46: goto -> 120
    //   49: aload_1
    //   50: iconst_1
    //   51: invokevirtual hasModifiers : (I)Z
    //   54: ifeq -> 118
    //   57: aload_0
    //   58: iconst_1
    //   59: invokevirtual arrowScroll : (I)Z
    //   62: istore_3
    //   63: goto -> 120
    //   66: aload_1
    //   67: iconst_2
    //   68: invokevirtual hasModifiers : (I)Z
    //   71: ifeq -> 82
    //   74: aload_0
    //   75: invokevirtual pageRight : ()Z
    //   78: istore_3
    //   79: goto -> 120
    //   82: aload_0
    //   83: bipush #66
    //   85: invokevirtual arrowScroll : (I)Z
    //   88: istore_3
    //   89: goto -> 120
    //   92: aload_1
    //   93: iconst_2
    //   94: invokevirtual hasModifiers : (I)Z
    //   97: ifeq -> 108
    //   100: aload_0
    //   101: invokevirtual pageLeft : ()Z
    //   104: istore_3
    //   105: goto -> 120
    //   108: aload_0
    //   109: bipush #17
    //   111: invokevirtual arrowScroll : (I)Z
    //   114: istore_3
    //   115: goto -> 120
    //   118: iconst_0
    //   119: istore_3
    //   120: iload_3
    //   121: ireturn
  }
  
  public void fakeDragBy(float paramFloat) {
    if (this.mFakeDragging) {
      if (this.mAdapter == null)
        return; 
      this.mLastMotionX += paramFloat;
      float f2 = getScrollX() - paramFloat;
      float f3 = getClientWidth();
      paramFloat = this.mFirstOffset * f3;
      float f1 = this.mLastOffset * f3;
      ItemInfo itemInfo1 = this.mItems.get(0);
      ArrayList<ItemInfo> arrayList = this.mItems;
      ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
      if (itemInfo1.position != 0)
        paramFloat = itemInfo1.offset * f3; 
      if (itemInfo2.position != this.mAdapter.getCount() - 1)
        f1 = itemInfo2.offset * f3; 
      if (f2 >= paramFloat) {
        paramFloat = f2;
        if (f2 > f1)
          paramFloat = f1; 
      } 
      f1 = this.mLastMotionX;
      int i = (int)paramFloat;
      this.mLastMotionX = f1 + paramFloat - i;
      scrollTo(i, getScrollY());
      pageScrolled(i);
      long l = SystemClock.uptimeMillis();
      MotionEvent motionEvent = MotionEvent.obtain(this.mFakeDragBeginTime, l, 2, this.mLastMotionX, 0.0F, 0);
      this.mVelocityTracker.addMovement(motionEvent);
      motionEvent.recycle();
      return;
    } 
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return generateDefaultLayoutParams();
  }
  
  public PagerAdapter getAdapter() {
    return this.mAdapter;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2) {
    int i = paramInt2;
    if (this.mDrawingOrder == 2)
      i = paramInt1 - 1 - paramInt2; 
    return ((LayoutParams)((View)this.mDrawingOrderedChildren.get(i)).getLayoutParams()).childIndex;
  }
  
  public int getCurrentItem() {
    return this.mCurItem;
  }
  
  public int getOffscreenPageLimit() {
    return this.mOffscreenPageLimit;
  }
  
  public int getPageMargin() {
    return this.mPageMargin;
  }
  
  ItemInfo infoForAnyChild(View paramView) {
    while (true) {
      ViewParent viewParent = paramView.getParent();
      if (viewParent != this) {
        if (viewParent != null) {
          if (!(viewParent instanceof View))
            return null; 
          paramView = (View)viewParent;
          continue;
        } 
        continue;
      } 
      return infoForChild(paramView);
    } 
  }
  
  ItemInfo infoForChild(View paramView) {
    for (byte b = 0; b < this.mItems.size(); b++) {
      ItemInfo itemInfo = this.mItems.get(b);
      if (this.mAdapter.isViewFromObject(paramView, itemInfo.object))
        return itemInfo; 
    } 
    return null;
  }
  
  ItemInfo infoForPosition(int paramInt) {
    for (byte b = 0; b < this.mItems.size(); b++) {
      ItemInfo itemInfo = this.mItems.get(b);
      if (itemInfo.position == paramInt)
        return itemInfo; 
    } 
    return null;
  }
  
  void initViewPager() {
    setWillNotDraw(false);
    setDescendantFocusability(262144);
    setFocusable(true);
    Context context = getContext();
    this.mScroller = new Scroller(context, sInterpolator);
    ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
    float f = (context.getResources().getDisplayMetrics()).density;
    this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    this.mMinimumVelocity = (int)(400.0F * f);
    this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    this.mLeftEdge = new EdgeEffect(context);
    this.mRightEdge = new EdgeEffect(context);
    this.mFlingDistance = (int)(25.0F * f);
    this.mCloseEnough = (int)(2.0F * f);
    this.mDefaultGutterSize = (int)(f * 16.0F);
    ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
    if (ViewCompat.getImportantForAccessibility((View)this) == 0)
      ViewCompat.setImportantForAccessibility((View)this, 1); 
    ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener() {
          private final Rect mTempRect = new Rect();
          
          final ViewPager this$0;
          
          public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
            param1WindowInsetsCompat = ViewCompat.onApplyWindowInsets(param1View, param1WindowInsetsCompat);
            if (param1WindowInsetsCompat.isConsumed())
              return param1WindowInsetsCompat; 
            Rect rect = this.mTempRect;
            rect.left = param1WindowInsetsCompat.getSystemWindowInsetLeft();
            rect.top = param1WindowInsetsCompat.getSystemWindowInsetTop();
            rect.right = param1WindowInsetsCompat.getSystemWindowInsetRight();
            rect.bottom = param1WindowInsetsCompat.getSystemWindowInsetBottom();
            byte b = 0;
            int i = ViewPager.this.getChildCount();
            while (b < i) {
              WindowInsetsCompat windowInsetsCompat = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(b), param1WindowInsetsCompat);
              rect.left = Math.min(windowInsetsCompat.getSystemWindowInsetLeft(), rect.left);
              rect.top = Math.min(windowInsetsCompat.getSystemWindowInsetTop(), rect.top);
              rect.right = Math.min(windowInsetsCompat.getSystemWindowInsetRight(), rect.right);
              rect.bottom = Math.min(windowInsetsCompat.getSystemWindowInsetBottom(), rect.bottom);
              b++;
            } 
            return param1WindowInsetsCompat.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
          }
        });
  }
  
  public boolean isFakeDragging() {
    return this.mFakeDragging;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow() {
    removeCallbacks(this.mEndScrollRunnable);
    Scroller scroller = this.mScroller;
    if (scroller != null && !scroller.isFinished())
      this.mScroller.abortAnimation(); 
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
      int n = getScrollX();
      int m = getWidth();
      float f1 = this.mPageMargin;
      float f3 = m;
      float f2 = f1 / f3;
      ArrayList<ItemInfo> arrayList = this.mItems;
      byte b = 0;
      ItemInfo itemInfo = arrayList.get(0);
      f1 = itemInfo.offset;
      int k = this.mItems.size();
      int i = itemInfo.position;
      int j = ((ItemInfo)this.mItems.get(k - 1)).position;
      while (i < j) {
        float f;
        ItemInfo itemInfo1;
        while (i > itemInfo.position && b < k) {
          ArrayList<ItemInfo> arrayList1 = this.mItems;
          itemInfo1 = arrayList1.get(++b);
        } 
        if (i == itemInfo1.position) {
          f = (itemInfo1.offset + itemInfo1.widthFactor) * f3;
          f1 = itemInfo1.offset + itemInfo1.widthFactor + f2;
        } else {
          float f4 = this.mAdapter.getPageWidth(i);
          f = f1 + f4 + f2;
          f4 = (f1 + f4) * f3;
          f1 = f;
          f = f4;
        } 
        if (this.mPageMargin + f > n) {
          this.mMarginDrawable.setBounds(Math.round(f), this.mTopPageBounds, Math.round(this.mPageMargin + f), this.mBottomPageBounds);
          this.mMarginDrawable.draw(paramCanvas);
        } 
        if (f > (n + m))
          break; 
        i++;
      } 
    } 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction() & 0xFF;
    if (i == 3 || i == 1) {
      resetTouch();
      return false;
    } 
    if (i != 0) {
      if (this.mIsBeingDragged)
        return true; 
      if (this.mIsUnableToDrag)
        return false; 
    } 
    if (i != 0) {
      if (i != 2) {
        if (i == 6)
          onSecondaryPointerUp(paramMotionEvent); 
      } else {
        i = this.mActivePointerId;
        if (i != -1) {
          i = paramMotionEvent.findPointerIndex(i);
          float f2 = paramMotionEvent.getX(i);
          float f5 = f2 - this.mLastMotionX;
          float f1 = Math.abs(f5);
          float f3 = paramMotionEvent.getY(i);
          float f4 = Math.abs(f3 - this.mInitialMotionY);
          i = f5 cmp 0.0F;
          if (i != 0 && !isGutterDrag(this.mLastMotionX, f5) && canScroll((View)this, false, (int)f5, (int)f2, (int)f3)) {
            this.mLastMotionX = f2;
            this.mLastMotionY = f3;
            this.mIsUnableToDrag = true;
            return false;
          } 
          if (f1 > this.mTouchSlop && f1 * 0.5F > f4) {
            this.mIsBeingDragged = true;
            requestParentDisallowInterceptTouchEvent(true);
            setScrollState(1);
            f1 = this.mInitialMotionX;
            f4 = this.mTouchSlop;
            if (i > 0) {
              f1 += f4;
            } else {
              f1 -= f4;
            } 
            this.mLastMotionX = f1;
            this.mLastMotionY = f3;
            setScrollingCacheEnabled(true);
          } else if (f4 > this.mTouchSlop) {
            this.mIsUnableToDrag = true;
          } 
          if (this.mIsBeingDragged && performDrag(f2))
            ViewCompat.postInvalidateOnAnimation((View)this); 
        } 
      } 
    } else {
      float f = paramMotionEvent.getX();
      this.mInitialMotionX = f;
      this.mLastMotionX = f;
      f = paramMotionEvent.getY();
      this.mInitialMotionY = f;
      this.mLastMotionY = f;
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      this.mIsUnableToDrag = false;
      this.mIsScrollStarted = true;
      this.mScroller.computeScrollOffset();
      if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
        this.mScroller.abortAnimation();
        this.mPopulatePending = false;
        populate();
        this.mIsBeingDragged = true;
        requestParentDisallowInterceptTouchEvent(true);
        setScrollState(1);
      } else {
        completeScroll(false);
        this.mIsBeingDragged = false;
      } 
    } 
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    return this.mIsBeingDragged;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int k = getChildCount();
    int m = paramInt3 - paramInt1;
    int n = paramInt4 - paramInt2;
    paramInt2 = getPaddingLeft();
    paramInt1 = getPaddingTop();
    paramInt4 = getPaddingRight();
    paramInt3 = getPaddingBottom();
    int i1 = getScrollX();
    int j = 0;
    int i;
    for (i = 0; j < k; i = i2) {
      View view = getChildAt(j);
      int i6 = paramInt2;
      int i5 = paramInt1;
      int i4 = paramInt4;
      int i3 = paramInt3;
      int i2 = i;
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        i6 = paramInt2;
        i5 = paramInt1;
        i4 = paramInt4;
        i3 = paramInt3;
        i2 = i;
        if (layoutParams.isDecor) {
          i2 = layoutParams.gravity & 0x7;
          i4 = layoutParams.gravity & 0x70;
          if (i2 != 1) {
            if (i2 != 3) {
              if (i2 != 5) {
                i2 = paramInt2;
                i3 = paramInt2;
                paramInt2 = i2;
              } else {
                i2 = m - paramInt4 - view.getMeasuredWidth();
                paramInt4 += view.getMeasuredWidth();
                i3 = i2;
              } 
            } else {
              i2 = view.getMeasuredWidth() + paramInt2;
              i3 = paramInt2;
              paramInt2 = i2;
            } 
          } else {
            i2 = Math.max((m - view.getMeasuredWidth()) / 2, paramInt2);
            i3 = i2;
          } 
          if (i4 != 16) {
            if (i4 != 48) {
              if (i4 != 80) {
                i4 = paramInt1;
                i2 = paramInt1;
                paramInt1 = i4;
              } else {
                i2 = n - paramInt3 - view.getMeasuredHeight();
                paramInt3 += view.getMeasuredHeight();
              } 
            } else {
              i4 = view.getMeasuredHeight() + paramInt1;
              i2 = paramInt1;
              paramInt1 = i4;
            } 
          } else {
            i2 = Math.max((n - view.getMeasuredHeight()) / 2, paramInt1);
          } 
          i3 += i1;
          view.layout(i3, i2, view.getMeasuredWidth() + i3, i2 + view.getMeasuredHeight());
          i2 = i + 1;
          i3 = paramInt3;
          i4 = paramInt4;
          i5 = paramInt1;
          i6 = paramInt2;
        } 
      } 
      j++;
      paramInt2 = i6;
      paramInt1 = i5;
      paramInt4 = i4;
      paramInt3 = i3;
    } 
    for (byte b = 0; b < k; b++) {
      View view = getChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.isDecor) {
          ItemInfo itemInfo = infoForChild(view);
          if (itemInfo != null) {
            float f = (m - paramInt2 - paramInt4);
            j = (int)(itemInfo.offset * f) + paramInt2;
            if (layoutParams.needsMeasure) {
              layoutParams.needsMeasure = false;
              view.measure(View.MeasureSpec.makeMeasureSpec((int)(f * layoutParams.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec(n - paramInt1 - paramInt3, 1073741824));
            } 
            view.layout(j, paramInt1, view.getMeasuredWidth() + j, view.getMeasuredHeight() + paramInt1);
          } 
        } 
      } 
    } 
    this.mTopPageBounds = paramInt1;
    this.mBottomPageBounds = n - paramInt3;
    this.mDecorChildCount = i;
    if (this.mFirstLayout)
      scrollToItem(this.mCurItem, false, 0, false); 
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: iload_1
    //   3: invokestatic getDefaultSize : (II)I
    //   6: iconst_0
    //   7: iload_2
    //   8: invokestatic getDefaultSize : (II)I
    //   11: invokevirtual setMeasuredDimension : (II)V
    //   14: aload_0
    //   15: invokevirtual getMeasuredWidth : ()I
    //   18: istore_1
    //   19: aload_0
    //   20: iload_1
    //   21: bipush #10
    //   23: idiv
    //   24: aload_0
    //   25: getfield mDefaultGutterSize : I
    //   28: invokestatic min : (II)I
    //   31: putfield mGutterSize : I
    //   34: iload_1
    //   35: aload_0
    //   36: invokevirtual getPaddingLeft : ()I
    //   39: isub
    //   40: aload_0
    //   41: invokevirtual getPaddingRight : ()I
    //   44: isub
    //   45: istore_1
    //   46: aload_0
    //   47: invokevirtual getMeasuredHeight : ()I
    //   50: aload_0
    //   51: invokevirtual getPaddingTop : ()I
    //   54: isub
    //   55: aload_0
    //   56: invokevirtual getPaddingBottom : ()I
    //   59: isub
    //   60: istore_2
    //   61: aload_0
    //   62: invokevirtual getChildCount : ()I
    //   65: istore #11
    //   67: iconst_0
    //   68: istore #5
    //   70: iconst_1
    //   71: istore #8
    //   73: ldc_w 1073741824
    //   76: istore #10
    //   78: iload #5
    //   80: iload #11
    //   82: if_icmpge -> 415
    //   85: aload_0
    //   86: iload #5
    //   88: invokevirtual getChildAt : (I)Landroid/view/View;
    //   91: astore #13
    //   93: iload_1
    //   94: istore_3
    //   95: iload_2
    //   96: istore #4
    //   98: aload #13
    //   100: invokevirtual getVisibility : ()I
    //   103: bipush #8
    //   105: if_icmpeq -> 404
    //   108: aload #13
    //   110: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   113: checkcast androidx/viewpager/widget/ViewPager$LayoutParams
    //   116: astore #12
    //   118: iload_1
    //   119: istore_3
    //   120: iload_2
    //   121: istore #4
    //   123: aload #12
    //   125: ifnull -> 404
    //   128: iload_1
    //   129: istore_3
    //   130: iload_2
    //   131: istore #4
    //   133: aload #12
    //   135: getfield isDecor : Z
    //   138: ifeq -> 404
    //   141: aload #12
    //   143: getfield gravity : I
    //   146: bipush #7
    //   148: iand
    //   149: istore_3
    //   150: aload #12
    //   152: getfield gravity : I
    //   155: bipush #112
    //   157: iand
    //   158: istore #4
    //   160: iload #4
    //   162: bipush #48
    //   164: if_icmpeq -> 183
    //   167: iload #4
    //   169: bipush #80
    //   171: if_icmpne -> 177
    //   174: goto -> 183
    //   177: iconst_0
    //   178: istore #7
    //   180: goto -> 186
    //   183: iconst_1
    //   184: istore #7
    //   186: iload #8
    //   188: istore #6
    //   190: iload_3
    //   191: iconst_3
    //   192: if_icmpeq -> 210
    //   195: iload_3
    //   196: iconst_5
    //   197: if_icmpne -> 207
    //   200: iload #8
    //   202: istore #6
    //   204: goto -> 210
    //   207: iconst_0
    //   208: istore #6
    //   210: ldc_w -2147483648
    //   213: istore_3
    //   214: iload #7
    //   216: ifeq -> 227
    //   219: ldc_w 1073741824
    //   222: istore #4
    //   224: goto -> 249
    //   227: iload_3
    //   228: istore #4
    //   230: iload #6
    //   232: ifeq -> 249
    //   235: ldc_w 1073741824
    //   238: istore #8
    //   240: iload_3
    //   241: istore #4
    //   243: iload #8
    //   245: istore_3
    //   246: goto -> 253
    //   249: ldc_w -2147483648
    //   252: istore_3
    //   253: aload #12
    //   255: getfield width : I
    //   258: bipush #-2
    //   260: if_icmpeq -> 301
    //   263: aload #12
    //   265: getfield width : I
    //   268: iconst_m1
    //   269: if_icmpeq -> 282
    //   272: aload #12
    //   274: getfield width : I
    //   277: istore #4
    //   279: goto -> 285
    //   282: iload_1
    //   283: istore #4
    //   285: ldc_w 1073741824
    //   288: istore #9
    //   290: iload #4
    //   292: istore #8
    //   294: iload #9
    //   296: istore #4
    //   298: goto -> 304
    //   301: iload_1
    //   302: istore #8
    //   304: aload #12
    //   306: getfield height : I
    //   309: bipush #-2
    //   311: if_icmpeq -> 337
    //   314: aload #12
    //   316: getfield height : I
    //   319: iconst_m1
    //   320: if_icmpeq -> 332
    //   323: aload #12
    //   325: getfield height : I
    //   328: istore_3
    //   329: goto -> 346
    //   332: iload_2
    //   333: istore_3
    //   334: goto -> 346
    //   337: iload_2
    //   338: istore #9
    //   340: iload_3
    //   341: istore #10
    //   343: iload #9
    //   345: istore_3
    //   346: aload #13
    //   348: iload #8
    //   350: iload #4
    //   352: invokestatic makeMeasureSpec : (II)I
    //   355: iload_3
    //   356: iload #10
    //   358: invokestatic makeMeasureSpec : (II)I
    //   361: invokevirtual measure : (II)V
    //   364: iload #7
    //   366: ifeq -> 383
    //   369: iload_2
    //   370: aload #13
    //   372: invokevirtual getMeasuredHeight : ()I
    //   375: isub
    //   376: istore #4
    //   378: iload_1
    //   379: istore_3
    //   380: goto -> 404
    //   383: iload_1
    //   384: istore_3
    //   385: iload_2
    //   386: istore #4
    //   388: iload #6
    //   390: ifeq -> 404
    //   393: iload_1
    //   394: aload #13
    //   396: invokevirtual getMeasuredWidth : ()I
    //   399: isub
    //   400: istore_3
    //   401: iload_2
    //   402: istore #4
    //   404: iinc #5, 1
    //   407: iload_3
    //   408: istore_1
    //   409: iload #4
    //   411: istore_2
    //   412: goto -> 70
    //   415: aload_0
    //   416: iload_1
    //   417: ldc_w 1073741824
    //   420: invokestatic makeMeasureSpec : (II)I
    //   423: putfield mChildWidthMeasureSpec : I
    //   426: aload_0
    //   427: iload_2
    //   428: ldc_w 1073741824
    //   431: invokestatic makeMeasureSpec : (II)I
    //   434: putfield mChildHeightMeasureSpec : I
    //   437: aload_0
    //   438: iconst_1
    //   439: putfield mInLayout : Z
    //   442: aload_0
    //   443: invokevirtual populate : ()V
    //   446: iconst_0
    //   447: istore_2
    //   448: aload_0
    //   449: iconst_0
    //   450: putfield mInLayout : Z
    //   453: aload_0
    //   454: invokevirtual getChildCount : ()I
    //   457: istore_3
    //   458: iload_2
    //   459: iload_3
    //   460: if_icmpge -> 533
    //   463: aload_0
    //   464: iload_2
    //   465: invokevirtual getChildAt : (I)Landroid/view/View;
    //   468: astore #13
    //   470: aload #13
    //   472: invokevirtual getVisibility : ()I
    //   475: bipush #8
    //   477: if_icmpeq -> 527
    //   480: aload #13
    //   482: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   485: checkcast androidx/viewpager/widget/ViewPager$LayoutParams
    //   488: astore #12
    //   490: aload #12
    //   492: ifnull -> 503
    //   495: aload #12
    //   497: getfield isDecor : Z
    //   500: ifne -> 527
    //   503: aload #13
    //   505: iload_1
    //   506: i2f
    //   507: aload #12
    //   509: getfield widthFactor : F
    //   512: fmul
    //   513: f2i
    //   514: ldc_w 1073741824
    //   517: invokestatic makeMeasureSpec : (II)I
    //   520: aload_0
    //   521: getfield mChildHeightMeasureSpec : I
    //   524: invokevirtual measure : (II)V
    //   527: iinc #2, 1
    //   530: goto -> 458
    //   533: return
  }
  
  protected void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
    int i = this.mDecorChildCount;
    boolean bool = false;
    if (i > 0) {
      int k = getScrollX();
      i = getPaddingLeft();
      int j = getPaddingRight();
      int n = getWidth();
      int m = getChildCount();
      for (byte b = 0; b < m; b++) {
        View view = getChildAt(b);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.isDecor) {
          int i1 = layoutParams.gravity & 0x7;
          if (i1 != 1) {
            if (i1 != 3) {
              if (i1 != 5) {
                int i2 = i;
                i1 = i;
                i = i2;
              } else {
                i1 = n - j - view.getMeasuredWidth();
                j += view.getMeasuredWidth();
              } 
            } else {
              int i2 = view.getWidth() + i;
              i1 = i;
              i = i2;
            } 
          } else {
            i1 = Math.max((n - view.getMeasuredWidth()) / 2, i);
          } 
          i1 = i1 + k - view.getLeft();
          if (i1 != 0)
            view.offsetLeftAndRight(i1); 
        } 
      } 
    } 
    dispatchOnPageScrolled(paramInt1, paramFloat, paramInt2);
    if (this.mPageTransformer != null) {
      paramInt2 = getScrollX();
      i = getChildCount();
      for (paramInt1 = bool; paramInt1 < i; paramInt1++) {
        View view = getChildAt(paramInt1);
        if (!((LayoutParams)view.getLayoutParams()).isDecor) {
          paramFloat = (view.getLeft() - paramInt2) / getClientWidth();
          this.mPageTransformer.transformPage(view, paramFloat);
        } 
      } 
    } 
    this.mCalledSuper = true;
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect) {
    byte b;
    int i = getChildCount();
    int j = -1;
    if ((paramInt & 0x2) != 0) {
      j = i;
      i = 0;
      b = 1;
    } else {
      i--;
      b = -1;
    } 
    while (i != j) {
      View view = getChildAt(i);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem && view.requestFocus(paramInt, paramRect))
          return true; 
      } 
      i += b;
    } 
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    PagerAdapter pagerAdapter = this.mAdapter;
    if (pagerAdapter != null) {
      pagerAdapter.restoreState(savedState.adapterState, savedState.loader);
      setCurrentItemInternal(savedState.position, false, true);
    } else {
      this.mRestoredCurItem = savedState.position;
      this.mRestoredAdapterState = savedState.adapterState;
      this.mRestoredClassLoader = savedState.loader;
    } 
  }
  
  public Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    savedState.position = this.mCurItem;
    PagerAdapter pagerAdapter = this.mAdapter;
    if (pagerAdapter != null)
      savedState.adapterState = pagerAdapter.saveState(); 
    return (Parcelable)savedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3) {
      paramInt2 = this.mPageMargin;
      recomputeScrollPosition(paramInt1, paramInt3, paramInt2, paramInt2);
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mFakeDragging : Z
    //   4: ifeq -> 9
    //   7: iconst_1
    //   8: ireturn
    //   9: aload_1
    //   10: invokevirtual getAction : ()I
    //   13: istore #6
    //   15: iconst_0
    //   16: istore #9
    //   18: iload #6
    //   20: ifne -> 32
    //   23: aload_1
    //   24: invokevirtual getEdgeFlags : ()I
    //   27: ifeq -> 32
    //   30: iconst_0
    //   31: ireturn
    //   32: aload_0
    //   33: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   36: astore #10
    //   38: aload #10
    //   40: ifnull -> 613
    //   43: aload #10
    //   45: invokevirtual getCount : ()I
    //   48: ifne -> 54
    //   51: goto -> 613
    //   54: aload_0
    //   55: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   58: ifnonnull -> 68
    //   61: aload_0
    //   62: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   65: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   68: aload_0
    //   69: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   72: aload_1
    //   73: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   76: aload_1
    //   77: invokevirtual getAction : ()I
    //   80: sipush #255
    //   83: iand
    //   84: istore #6
    //   86: iload #6
    //   88: ifeq -> 547
    //   91: iload #6
    //   93: iconst_1
    //   94: if_icmpeq -> 407
    //   97: iload #6
    //   99: iconst_2
    //   100: if_icmpeq -> 205
    //   103: iload #6
    //   105: iconst_3
    //   106: if_icmpeq -> 178
    //   109: iload #6
    //   111: iconst_5
    //   112: if_icmpeq -> 149
    //   115: iload #6
    //   117: bipush #6
    //   119: if_icmpeq -> 125
    //   122: goto -> 602
    //   125: aload_0
    //   126: aload_1
    //   127: invokespecial onSecondaryPointerUp : (Landroid/view/MotionEvent;)V
    //   130: aload_0
    //   131: aload_1
    //   132: aload_1
    //   133: aload_0
    //   134: getfield mActivePointerId : I
    //   137: invokevirtual findPointerIndex : (I)I
    //   140: invokevirtual getX : (I)F
    //   143: putfield mLastMotionX : F
    //   146: goto -> 602
    //   149: aload_1
    //   150: invokevirtual getActionIndex : ()I
    //   153: istore #6
    //   155: aload_0
    //   156: aload_1
    //   157: iload #6
    //   159: invokevirtual getX : (I)F
    //   162: putfield mLastMotionX : F
    //   165: aload_0
    //   166: aload_1
    //   167: iload #6
    //   169: invokevirtual getPointerId : (I)I
    //   172: putfield mActivePointerId : I
    //   175: goto -> 602
    //   178: aload_0
    //   179: getfield mIsBeingDragged : Z
    //   182: ifeq -> 602
    //   185: aload_0
    //   186: aload_0
    //   187: getfield mCurItem : I
    //   190: iconst_1
    //   191: iconst_0
    //   192: iconst_0
    //   193: invokespecial scrollToItem : (IZIZ)V
    //   196: aload_0
    //   197: invokespecial resetTouch : ()Z
    //   200: istore #9
    //   202: goto -> 602
    //   205: aload_0
    //   206: getfield mIsBeingDragged : Z
    //   209: ifne -> 377
    //   212: aload_1
    //   213: aload_0
    //   214: getfield mActivePointerId : I
    //   217: invokevirtual findPointerIndex : (I)I
    //   220: istore #6
    //   222: iload #6
    //   224: iconst_m1
    //   225: if_icmpne -> 237
    //   228: aload_0
    //   229: invokespecial resetTouch : ()Z
    //   232: istore #9
    //   234: goto -> 602
    //   237: aload_1
    //   238: iload #6
    //   240: invokevirtual getX : (I)F
    //   243: fstore_2
    //   244: fload_2
    //   245: aload_0
    //   246: getfield mLastMotionX : F
    //   249: fsub
    //   250: invokestatic abs : (F)F
    //   253: fstore #4
    //   255: aload_1
    //   256: iload #6
    //   258: invokevirtual getY : (I)F
    //   261: fstore_3
    //   262: fload_3
    //   263: aload_0
    //   264: getfield mLastMotionY : F
    //   267: fsub
    //   268: invokestatic abs : (F)F
    //   271: fstore #5
    //   273: fload #4
    //   275: aload_0
    //   276: getfield mTouchSlop : I
    //   279: i2f
    //   280: fcmpl
    //   281: ifle -> 377
    //   284: fload #4
    //   286: fload #5
    //   288: fcmpl
    //   289: ifle -> 377
    //   292: aload_0
    //   293: iconst_1
    //   294: putfield mIsBeingDragged : Z
    //   297: aload_0
    //   298: iconst_1
    //   299: invokespecial requestParentDisallowInterceptTouchEvent : (Z)V
    //   302: aload_0
    //   303: getfield mInitialMotionX : F
    //   306: fstore #4
    //   308: fload_2
    //   309: fload #4
    //   311: fsub
    //   312: fconst_0
    //   313: fcmpl
    //   314: ifle -> 329
    //   317: fload #4
    //   319: aload_0
    //   320: getfield mTouchSlop : I
    //   323: i2f
    //   324: fadd
    //   325: fstore_2
    //   326: goto -> 338
    //   329: fload #4
    //   331: aload_0
    //   332: getfield mTouchSlop : I
    //   335: i2f
    //   336: fsub
    //   337: fstore_2
    //   338: aload_0
    //   339: fload_2
    //   340: putfield mLastMotionX : F
    //   343: aload_0
    //   344: fload_3
    //   345: putfield mLastMotionY : F
    //   348: aload_0
    //   349: iconst_1
    //   350: invokevirtual setScrollState : (I)V
    //   353: aload_0
    //   354: iconst_1
    //   355: invokespecial setScrollingCacheEnabled : (Z)V
    //   358: aload_0
    //   359: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   362: astore #10
    //   364: aload #10
    //   366: ifnull -> 377
    //   369: aload #10
    //   371: iconst_1
    //   372: invokeinterface requestDisallowInterceptTouchEvent : (Z)V
    //   377: aload_0
    //   378: getfield mIsBeingDragged : Z
    //   381: ifeq -> 602
    //   384: iconst_0
    //   385: aload_0
    //   386: aload_1
    //   387: aload_1
    //   388: aload_0
    //   389: getfield mActivePointerId : I
    //   392: invokevirtual findPointerIndex : (I)I
    //   395: invokevirtual getX : (I)F
    //   398: invokespecial performDrag : (F)Z
    //   401: ior
    //   402: istore #9
    //   404: goto -> 602
    //   407: aload_0
    //   408: getfield mIsBeingDragged : Z
    //   411: ifeq -> 602
    //   414: aload_0
    //   415: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   418: astore #10
    //   420: aload #10
    //   422: sipush #1000
    //   425: aload_0
    //   426: getfield mMaximumVelocity : I
    //   429: i2f
    //   430: invokevirtual computeCurrentVelocity : (IF)V
    //   433: aload #10
    //   435: aload_0
    //   436: getfield mActivePointerId : I
    //   439: invokevirtual getXVelocity : (I)F
    //   442: f2i
    //   443: istore #7
    //   445: aload_0
    //   446: iconst_1
    //   447: putfield mPopulatePending : Z
    //   450: aload_0
    //   451: invokespecial getClientWidth : ()I
    //   454: istore #6
    //   456: aload_0
    //   457: invokevirtual getScrollX : ()I
    //   460: istore #8
    //   462: aload_0
    //   463: invokespecial infoForCurrentScrollPosition : ()Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   466: astore #10
    //   468: aload_0
    //   469: getfield mPageMargin : I
    //   472: i2f
    //   473: fstore_3
    //   474: iload #6
    //   476: i2f
    //   477: fstore_2
    //   478: fload_3
    //   479: fload_2
    //   480: fdiv
    //   481: fstore_3
    //   482: aload_0
    //   483: aload_0
    //   484: aload #10
    //   486: getfield position : I
    //   489: iload #8
    //   491: i2f
    //   492: fload_2
    //   493: fdiv
    //   494: aload #10
    //   496: getfield offset : F
    //   499: fsub
    //   500: aload #10
    //   502: getfield widthFactor : F
    //   505: fload_3
    //   506: fadd
    //   507: fdiv
    //   508: iload #7
    //   510: aload_1
    //   511: aload_1
    //   512: aload_0
    //   513: getfield mActivePointerId : I
    //   516: invokevirtual findPointerIndex : (I)I
    //   519: invokevirtual getX : (I)F
    //   522: aload_0
    //   523: getfield mInitialMotionX : F
    //   526: fsub
    //   527: f2i
    //   528: invokespecial determineTargetPage : (IFII)I
    //   531: iconst_1
    //   532: iconst_1
    //   533: iload #7
    //   535: invokevirtual setCurrentItemInternal : (IZZI)V
    //   538: aload_0
    //   539: invokespecial resetTouch : ()Z
    //   542: istore #9
    //   544: goto -> 602
    //   547: aload_0
    //   548: getfield mScroller : Landroid/widget/Scroller;
    //   551: invokevirtual abortAnimation : ()V
    //   554: aload_0
    //   555: iconst_0
    //   556: putfield mPopulatePending : Z
    //   559: aload_0
    //   560: invokevirtual populate : ()V
    //   563: aload_1
    //   564: invokevirtual getX : ()F
    //   567: fstore_2
    //   568: aload_0
    //   569: fload_2
    //   570: putfield mInitialMotionX : F
    //   573: aload_0
    //   574: fload_2
    //   575: putfield mLastMotionX : F
    //   578: aload_1
    //   579: invokevirtual getY : ()F
    //   582: fstore_2
    //   583: aload_0
    //   584: fload_2
    //   585: putfield mInitialMotionY : F
    //   588: aload_0
    //   589: fload_2
    //   590: putfield mLastMotionY : F
    //   593: aload_0
    //   594: aload_1
    //   595: iconst_0
    //   596: invokevirtual getPointerId : (I)I
    //   599: putfield mActivePointerId : I
    //   602: iload #9
    //   604: ifeq -> 611
    //   607: aload_0
    //   608: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   611: iconst_1
    //   612: ireturn
    //   613: iconst_0
    //   614: ireturn
  }
  
  boolean pageLeft() {
    int i = this.mCurItem;
    if (i > 0) {
      setCurrentItem(i - 1, true);
      return true;
    } 
    return false;
  }
  
  boolean pageRight() {
    PagerAdapter pagerAdapter = this.mAdapter;
    if (pagerAdapter != null && this.mCurItem < pagerAdapter.getCount() - 1) {
      setCurrentItem(this.mCurItem + 1, true);
      return true;
    } 
    return false;
  }
  
  void populate() {
    populate(this.mCurItem);
  }
  
  void populate(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mCurItem : I
    //   4: istore #5
    //   6: iload #5
    //   8: iload_1
    //   9: if_icmpeq -> 28
    //   12: aload_0
    //   13: iload #5
    //   15: invokevirtual infoForPosition : (I)Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   18: astore #14
    //   20: aload_0
    //   21: iload_1
    //   22: putfield mCurItem : I
    //   25: goto -> 31
    //   28: aconst_null
    //   29: astore #14
    //   31: aload_0
    //   32: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   35: ifnonnull -> 43
    //   38: aload_0
    //   39: invokespecial sortChildDrawingOrder : ()V
    //   42: return
    //   43: aload_0
    //   44: getfield mPopulatePending : Z
    //   47: ifeq -> 55
    //   50: aload_0
    //   51: invokespecial sortChildDrawingOrder : ()V
    //   54: return
    //   55: aload_0
    //   56: invokevirtual getWindowToken : ()Landroid/os/IBinder;
    //   59: ifnonnull -> 63
    //   62: return
    //   63: aload_0
    //   64: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   67: aload_0
    //   68: invokevirtual startUpdate : (Landroid/view/ViewGroup;)V
    //   71: aload_0
    //   72: getfield mOffscreenPageLimit : I
    //   75: istore_1
    //   76: iconst_0
    //   77: aload_0
    //   78: getfield mCurItem : I
    //   81: iload_1
    //   82: isub
    //   83: invokestatic max : (II)I
    //   86: istore #11
    //   88: aload_0
    //   89: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   92: invokevirtual getCount : ()I
    //   95: istore #9
    //   97: iload #9
    //   99: iconst_1
    //   100: isub
    //   101: aload_0
    //   102: getfield mCurItem : I
    //   105: iload_1
    //   106: iadd
    //   107: invokestatic min : (II)I
    //   110: istore #10
    //   112: iload #9
    //   114: aload_0
    //   115: getfield mExpectedAdapterCount : I
    //   118: if_icmpne -> 1192
    //   121: iconst_0
    //   122: istore_1
    //   123: iload_1
    //   124: aload_0
    //   125: getfield mItems : Ljava/util/ArrayList;
    //   128: invokevirtual size : ()I
    //   131: if_icmpge -> 180
    //   134: aload_0
    //   135: getfield mItems : Ljava/util/ArrayList;
    //   138: iload_1
    //   139: invokevirtual get : (I)Ljava/lang/Object;
    //   142: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   145: astore #13
    //   147: aload #13
    //   149: getfield position : I
    //   152: aload_0
    //   153: getfield mCurItem : I
    //   156: if_icmplt -> 174
    //   159: aload #13
    //   161: getfield position : I
    //   164: aload_0
    //   165: getfield mCurItem : I
    //   168: if_icmpne -> 180
    //   171: goto -> 183
    //   174: iinc #1, 1
    //   177: goto -> 123
    //   180: aconst_null
    //   181: astore #13
    //   183: aload #13
    //   185: astore #15
    //   187: aload #13
    //   189: ifnonnull -> 212
    //   192: aload #13
    //   194: astore #15
    //   196: iload #9
    //   198: ifle -> 212
    //   201: aload_0
    //   202: aload_0
    //   203: getfield mCurItem : I
    //   206: iload_1
    //   207: invokevirtual addNewItem : (II)Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   210: astore #15
    //   212: aload #15
    //   214: ifnull -> 976
    //   217: iload_1
    //   218: iconst_1
    //   219: isub
    //   220: istore #5
    //   222: iload #5
    //   224: iflt -> 244
    //   227: aload_0
    //   228: getfield mItems : Ljava/util/ArrayList;
    //   231: iload #5
    //   233: invokevirtual get : (I)Ljava/lang/Object;
    //   236: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   239: astore #13
    //   241: goto -> 247
    //   244: aconst_null
    //   245: astore #13
    //   247: aload_0
    //   248: invokespecial getClientWidth : ()I
    //   251: istore #12
    //   253: iload #12
    //   255: ifgt -> 264
    //   258: fconst_0
    //   259: fstore #4
    //   261: goto -> 283
    //   264: fconst_2
    //   265: aload #15
    //   267: getfield widthFactor : F
    //   270: fsub
    //   271: aload_0
    //   272: invokevirtual getPaddingLeft : ()I
    //   275: i2f
    //   276: iload #12
    //   278: i2f
    //   279: fdiv
    //   280: fadd
    //   281: fstore #4
    //   283: aload_0
    //   284: getfield mCurItem : I
    //   287: iconst_1
    //   288: isub
    //   289: istore #8
    //   291: fconst_0
    //   292: fstore_3
    //   293: iload #8
    //   295: iflt -> 579
    //   298: fload_3
    //   299: fload #4
    //   301: fcmpl
    //   302: iflt -> 428
    //   305: iload #8
    //   307: iload #11
    //   309: if_icmpge -> 428
    //   312: aload #13
    //   314: ifnonnull -> 320
    //   317: goto -> 579
    //   320: iload_1
    //   321: istore #7
    //   323: iload #5
    //   325: istore #6
    //   327: aload #13
    //   329: astore #16
    //   331: fload_3
    //   332: fstore_2
    //   333: iload #8
    //   335: aload #13
    //   337: getfield position : I
    //   340: if_icmpne -> 560
    //   343: iload_1
    //   344: istore #7
    //   346: iload #5
    //   348: istore #6
    //   350: aload #13
    //   352: astore #16
    //   354: fload_3
    //   355: fstore_2
    //   356: aload #13
    //   358: getfield scrolling : Z
    //   361: ifne -> 560
    //   364: aload_0
    //   365: getfield mItems : Ljava/util/ArrayList;
    //   368: iload #5
    //   370: invokevirtual remove : (I)Ljava/lang/Object;
    //   373: pop
    //   374: aload_0
    //   375: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   378: aload_0
    //   379: iload #8
    //   381: aload #13
    //   383: getfield object : Ljava/lang/Object;
    //   386: invokevirtual destroyItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   389: iinc #5, -1
    //   392: iinc #1, -1
    //   395: iload_1
    //   396: istore #6
    //   398: iload #5
    //   400: istore #7
    //   402: fload_3
    //   403: fstore_2
    //   404: iload #5
    //   406: iflt -> 539
    //   409: aload_0
    //   410: getfield mItems : Ljava/util/ArrayList;
    //   413: iload #5
    //   415: invokevirtual get : (I)Ljava/lang/Object;
    //   418: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   421: astore #13
    //   423: fload_3
    //   424: fstore_2
    //   425: goto -> 549
    //   428: aload #13
    //   430: ifnull -> 487
    //   433: iload #8
    //   435: aload #13
    //   437: getfield position : I
    //   440: if_icmpne -> 487
    //   443: fload_3
    //   444: aload #13
    //   446: getfield widthFactor : F
    //   449: fadd
    //   450: fstore_3
    //   451: iinc #5, -1
    //   454: iload_1
    //   455: istore #6
    //   457: iload #5
    //   459: istore #7
    //   461: fload_3
    //   462: fstore_2
    //   463: iload #5
    //   465: iflt -> 539
    //   468: aload_0
    //   469: getfield mItems : Ljava/util/ArrayList;
    //   472: iload #5
    //   474: invokevirtual get : (I)Ljava/lang/Object;
    //   477: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   480: astore #13
    //   482: fload_3
    //   483: fstore_2
    //   484: goto -> 549
    //   487: fload_3
    //   488: aload_0
    //   489: iload #8
    //   491: iload #5
    //   493: iconst_1
    //   494: iadd
    //   495: invokevirtual addNewItem : (II)Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   498: getfield widthFactor : F
    //   501: fadd
    //   502: fstore_3
    //   503: iinc #1, 1
    //   506: iload_1
    //   507: istore #6
    //   509: iload #5
    //   511: istore #7
    //   513: fload_3
    //   514: fstore_2
    //   515: iload #5
    //   517: iflt -> 539
    //   520: aload_0
    //   521: getfield mItems : Ljava/util/ArrayList;
    //   524: iload #5
    //   526: invokevirtual get : (I)Ljava/lang/Object;
    //   529: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   532: astore #13
    //   534: fload_3
    //   535: fstore_2
    //   536: goto -> 549
    //   539: aconst_null
    //   540: astore #13
    //   542: iload #7
    //   544: istore #5
    //   546: iload #6
    //   548: istore_1
    //   549: aload #13
    //   551: astore #16
    //   553: iload #5
    //   555: istore #6
    //   557: iload_1
    //   558: istore #7
    //   560: iinc #8, -1
    //   563: iload #7
    //   565: istore_1
    //   566: iload #6
    //   568: istore #5
    //   570: aload #16
    //   572: astore #13
    //   574: fload_2
    //   575: fstore_3
    //   576: goto -> 293
    //   579: aload #15
    //   581: getfield widthFactor : F
    //   584: fstore_3
    //   585: iload_1
    //   586: iconst_1
    //   587: iadd
    //   588: istore #6
    //   590: fload_3
    //   591: fconst_2
    //   592: fcmpg
    //   593: ifge -> 950
    //   596: iload #6
    //   598: aload_0
    //   599: getfield mItems : Ljava/util/ArrayList;
    //   602: invokevirtual size : ()I
    //   605: if_icmpge -> 625
    //   608: aload_0
    //   609: getfield mItems : Ljava/util/ArrayList;
    //   612: iload #6
    //   614: invokevirtual get : (I)Ljava/lang/Object;
    //   617: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   620: astore #13
    //   622: goto -> 628
    //   625: aconst_null
    //   626: astore #13
    //   628: iload #12
    //   630: ifgt -> 639
    //   633: fconst_0
    //   634: fstore #4
    //   636: goto -> 652
    //   639: aload_0
    //   640: invokevirtual getPaddingRight : ()I
    //   643: i2f
    //   644: iload #12
    //   646: i2f
    //   647: fdiv
    //   648: fconst_2
    //   649: fadd
    //   650: fstore #4
    //   652: aload_0
    //   653: getfield mCurItem : I
    //   656: istore #5
    //   658: aload #13
    //   660: astore #16
    //   662: iload #5
    //   664: iconst_1
    //   665: iadd
    //   666: istore #7
    //   668: iload #7
    //   670: iload #9
    //   672: if_icmpge -> 950
    //   675: fload_3
    //   676: fload #4
    //   678: fcmpl
    //   679: iflt -> 807
    //   682: iload #7
    //   684: iload #10
    //   686: if_icmple -> 807
    //   689: aload #16
    //   691: ifnonnull -> 697
    //   694: goto -> 950
    //   697: fload_3
    //   698: fstore_2
    //   699: iload #6
    //   701: istore #5
    //   703: aload #16
    //   705: astore #13
    //   707: iload #7
    //   709: aload #16
    //   711: getfield position : I
    //   714: if_icmpne -> 933
    //   717: fload_3
    //   718: fstore_2
    //   719: iload #6
    //   721: istore #5
    //   723: aload #16
    //   725: astore #13
    //   727: aload #16
    //   729: getfield scrolling : Z
    //   732: ifne -> 933
    //   735: aload_0
    //   736: getfield mItems : Ljava/util/ArrayList;
    //   739: iload #6
    //   741: invokevirtual remove : (I)Ljava/lang/Object;
    //   744: pop
    //   745: aload_0
    //   746: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   749: aload_0
    //   750: iload #7
    //   752: aload #16
    //   754: getfield object : Ljava/lang/Object;
    //   757: invokevirtual destroyItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   760: fload_3
    //   761: fstore_2
    //   762: iload #6
    //   764: istore #5
    //   766: iload #6
    //   768: aload_0
    //   769: getfield mItems : Ljava/util/ArrayList;
    //   772: invokevirtual size : ()I
    //   775: if_icmpge -> 801
    //   778: aload_0
    //   779: getfield mItems : Ljava/util/ArrayList;
    //   782: iload #6
    //   784: invokevirtual get : (I)Ljava/lang/Object;
    //   787: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   790: astore #13
    //   792: fload_3
    //   793: fstore_2
    //   794: iload #6
    //   796: istore #5
    //   798: goto -> 933
    //   801: aconst_null
    //   802: astore #13
    //   804: goto -> 933
    //   807: aload #16
    //   809: ifnull -> 874
    //   812: iload #7
    //   814: aload #16
    //   816: getfield position : I
    //   819: if_icmpne -> 874
    //   822: fload_3
    //   823: aload #16
    //   825: getfield widthFactor : F
    //   828: fadd
    //   829: fstore_3
    //   830: iinc #6, 1
    //   833: fload_3
    //   834: fstore_2
    //   835: iload #6
    //   837: istore #5
    //   839: iload #6
    //   841: aload_0
    //   842: getfield mItems : Ljava/util/ArrayList;
    //   845: invokevirtual size : ()I
    //   848: if_icmpge -> 801
    //   851: aload_0
    //   852: getfield mItems : Ljava/util/ArrayList;
    //   855: iload #6
    //   857: invokevirtual get : (I)Ljava/lang/Object;
    //   860: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   863: astore #13
    //   865: fload_3
    //   866: fstore_2
    //   867: iload #6
    //   869: istore #5
    //   871: goto -> 933
    //   874: aload_0
    //   875: iload #7
    //   877: iload #6
    //   879: invokevirtual addNewItem : (II)Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   882: astore #13
    //   884: iinc #6, 1
    //   887: fload_3
    //   888: aload #13
    //   890: getfield widthFactor : F
    //   893: fadd
    //   894: fstore_3
    //   895: fload_3
    //   896: fstore_2
    //   897: iload #6
    //   899: istore #5
    //   901: iload #6
    //   903: aload_0
    //   904: getfield mItems : Ljava/util/ArrayList;
    //   907: invokevirtual size : ()I
    //   910: if_icmpge -> 801
    //   913: aload_0
    //   914: getfield mItems : Ljava/util/ArrayList;
    //   917: iload #6
    //   919: invokevirtual get : (I)Ljava/lang/Object;
    //   922: checkcast androidx/viewpager/widget/ViewPager$ItemInfo
    //   925: astore #13
    //   927: iload #6
    //   929: istore #5
    //   931: fload_3
    //   932: fstore_2
    //   933: fload_2
    //   934: fstore_3
    //   935: iload #5
    //   937: istore #6
    //   939: aload #13
    //   941: astore #16
    //   943: iload #7
    //   945: istore #5
    //   947: goto -> 662
    //   950: aload_0
    //   951: aload #15
    //   953: iload_1
    //   954: aload #14
    //   956: invokespecial calculatePageOffsets : (Landroidx/viewpager/widget/ViewPager$ItemInfo;ILandroidx/viewpager/widget/ViewPager$ItemInfo;)V
    //   959: aload_0
    //   960: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   963: aload_0
    //   964: aload_0
    //   965: getfield mCurItem : I
    //   968: aload #15
    //   970: getfield object : Ljava/lang/Object;
    //   973: invokevirtual setPrimaryItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   976: aload_0
    //   977: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   980: aload_0
    //   981: invokevirtual finishUpdate : (Landroid/view/ViewGroup;)V
    //   984: aload_0
    //   985: invokevirtual getChildCount : ()I
    //   988: istore #5
    //   990: iconst_0
    //   991: istore_1
    //   992: iload_1
    //   993: iload #5
    //   995: if_icmpge -> 1078
    //   998: aload_0
    //   999: iload_1
    //   1000: invokevirtual getChildAt : (I)Landroid/view/View;
    //   1003: astore #14
    //   1005: aload #14
    //   1007: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1010: checkcast androidx/viewpager/widget/ViewPager$LayoutParams
    //   1013: astore #13
    //   1015: aload #13
    //   1017: iload_1
    //   1018: putfield childIndex : I
    //   1021: aload #13
    //   1023: getfield isDecor : Z
    //   1026: ifne -> 1072
    //   1029: aload #13
    //   1031: getfield widthFactor : F
    //   1034: fconst_0
    //   1035: fcmpl
    //   1036: ifne -> 1072
    //   1039: aload_0
    //   1040: aload #14
    //   1042: invokevirtual infoForChild : (Landroid/view/View;)Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   1045: astore #14
    //   1047: aload #14
    //   1049: ifnull -> 1072
    //   1052: aload #13
    //   1054: aload #14
    //   1056: getfield widthFactor : F
    //   1059: putfield widthFactor : F
    //   1062: aload #13
    //   1064: aload #14
    //   1066: getfield position : I
    //   1069: putfield position : I
    //   1072: iinc #1, 1
    //   1075: goto -> 992
    //   1078: aload_0
    //   1079: invokespecial sortChildDrawingOrder : ()V
    //   1082: aload_0
    //   1083: invokevirtual hasFocus : ()Z
    //   1086: ifeq -> 1191
    //   1089: aload_0
    //   1090: invokevirtual findFocus : ()Landroid/view/View;
    //   1093: astore #13
    //   1095: aload #13
    //   1097: ifnull -> 1111
    //   1100: aload_0
    //   1101: aload #13
    //   1103: invokevirtual infoForAnyChild : (Landroid/view/View;)Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   1106: astore #13
    //   1108: goto -> 1114
    //   1111: aconst_null
    //   1112: astore #13
    //   1114: aload #13
    //   1116: ifnull -> 1131
    //   1119: aload #13
    //   1121: getfield position : I
    //   1124: aload_0
    //   1125: getfield mCurItem : I
    //   1128: if_icmpeq -> 1191
    //   1131: iconst_0
    //   1132: istore_1
    //   1133: iload_1
    //   1134: aload_0
    //   1135: invokevirtual getChildCount : ()I
    //   1138: if_icmpge -> 1191
    //   1141: aload_0
    //   1142: iload_1
    //   1143: invokevirtual getChildAt : (I)Landroid/view/View;
    //   1146: astore #13
    //   1148: aload_0
    //   1149: aload #13
    //   1151: invokevirtual infoForChild : (Landroid/view/View;)Landroidx/viewpager/widget/ViewPager$ItemInfo;
    //   1154: astore #14
    //   1156: aload #14
    //   1158: ifnull -> 1185
    //   1161: aload #14
    //   1163: getfield position : I
    //   1166: aload_0
    //   1167: getfield mCurItem : I
    //   1170: if_icmpne -> 1185
    //   1173: aload #13
    //   1175: iconst_2
    //   1176: invokevirtual requestFocus : (I)Z
    //   1179: ifeq -> 1185
    //   1182: goto -> 1191
    //   1185: iinc #1, 1
    //   1188: goto -> 1133
    //   1191: return
    //   1192: aload_0
    //   1193: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   1196: aload_0
    //   1197: invokevirtual getId : ()I
    //   1200: invokevirtual getResourceName : (I)Ljava/lang/String;
    //   1203: astore #13
    //   1205: goto -> 1219
    //   1208: astore #13
    //   1210: aload_0
    //   1211: invokevirtual getId : ()I
    //   1214: invokestatic toHexString : (I)Ljava/lang/String;
    //   1217: astore #13
    //   1219: new java/lang/StringBuilder
    //   1222: dup
    //   1223: invokespecial <init> : ()V
    //   1226: astore #14
    //   1228: aload #14
    //   1230: ldc_w 'The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: '
    //   1233: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1236: pop
    //   1237: aload #14
    //   1239: aload_0
    //   1240: getfield mExpectedAdapterCount : I
    //   1243: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1246: pop
    //   1247: aload #14
    //   1249: ldc_w ', found: '
    //   1252: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1255: pop
    //   1256: aload #14
    //   1258: iload #9
    //   1260: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1263: pop
    //   1264: aload #14
    //   1266: ldc_w ' Pager id: '
    //   1269: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1272: pop
    //   1273: aload #14
    //   1275: aload #13
    //   1277: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1280: pop
    //   1281: aload #14
    //   1283: ldc_w ' Pager class: '
    //   1286: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1289: pop
    //   1290: aload #14
    //   1292: aload_0
    //   1293: invokevirtual getClass : ()Ljava/lang/Class;
    //   1296: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1299: pop
    //   1300: aload #14
    //   1302: ldc_w ' Problematic adapter: '
    //   1305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1308: pop
    //   1309: aload #14
    //   1311: aload_0
    //   1312: getfield mAdapter : Landroidx/viewpager/widget/PagerAdapter;
    //   1315: invokevirtual getClass : ()Ljava/lang/Class;
    //   1318: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1321: pop
    //   1322: new java/lang/IllegalStateException
    //   1325: dup
    //   1326: aload #14
    //   1328: invokevirtual toString : ()Ljava/lang/String;
    //   1331: invokespecial <init> : (Ljava/lang/String;)V
    //   1334: athrow
    // Exception table:
    //   from	to	target	type
    //   1192	1205	1208	android/content/res/Resources$NotFoundException
  }
  
  public void removeOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener) {
    List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
    if (list != null)
      list.remove(paramOnAdapterChangeListener); 
  }
  
  public void removeOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null)
      list.remove(paramOnPageChangeListener); 
  }
  
  public void removeView(View paramView) {
    if (this.mInLayout) {
      removeViewInLayout(paramView);
    } else {
      super.removeView(paramView);
    } 
  }
  
  public void setAdapter(PagerAdapter paramPagerAdapter) {
    PagerAdapter pagerAdapter = this.mAdapter;
    byte b = 0;
    if (pagerAdapter != null) {
      pagerAdapter.setViewPagerObserver(null);
      this.mAdapter.startUpdate(this);
      for (byte b1 = 0; b1 < this.mItems.size(); b1++) {
        ItemInfo itemInfo = this.mItems.get(b1);
        this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
      } 
      this.mAdapter.finishUpdate(this);
      this.mItems.clear();
      removeNonDecorViews();
      this.mCurItem = 0;
      scrollTo(0, 0);
    } 
    pagerAdapter = this.mAdapter;
    this.mAdapter = paramPagerAdapter;
    this.mExpectedAdapterCount = 0;
    if (paramPagerAdapter != null) {
      if (this.mObserver == null)
        this.mObserver = new PagerObserver(); 
      this.mAdapter.setViewPagerObserver(this.mObserver);
      this.mPopulatePending = false;
      boolean bool = this.mFirstLayout;
      this.mFirstLayout = true;
      this.mExpectedAdapterCount = this.mAdapter.getCount();
      if (this.mRestoredCurItem >= 0) {
        this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
        setCurrentItemInternal(this.mRestoredCurItem, false, true);
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
      } else if (!bool) {
        populate();
      } else {
        requestLayout();
      } 
    } 
    List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
    if (list != null && !list.isEmpty()) {
      int i = this.mAdapterChangeListeners.size();
      for (byte b1 = b; b1 < i; b1++)
        ((OnAdapterChangeListener)this.mAdapterChangeListeners.get(b1)).onAdapterChanged(this, pagerAdapter, paramPagerAdapter); 
    } 
  }
  
  public void setCurrentItem(int paramInt) {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, this.mFirstLayout ^ true, false);
  }
  
  public void setCurrentItem(int paramInt, boolean paramBoolean) {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, paramBoolean, false);
  }
  
  void setCurrentItemInternal(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    setCurrentItemInternal(paramInt, paramBoolean1, paramBoolean2, 0);
  }
  
  void setCurrentItemInternal(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2) {
    int i;
    PagerAdapter pagerAdapter = this.mAdapter;
    boolean bool = false;
    if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    if (!paramBoolean2 && this.mCurItem == paramInt1 && this.mItems.size() != 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    if (paramInt1 < 0) {
      i = 0;
    } else {
      i = paramInt1;
      if (paramInt1 >= this.mAdapter.getCount())
        i = this.mAdapter.getCount() - 1; 
    } 
    paramInt1 = this.mOffscreenPageLimit;
    int j = this.mCurItem;
    if (i > j + paramInt1 || i < j - paramInt1)
      for (paramInt1 = 0; paramInt1 < this.mItems.size(); paramInt1++)
        ((ItemInfo)this.mItems.get(paramInt1)).scrolling = true;  
    paramBoolean2 = bool;
    if (this.mCurItem != i)
      paramBoolean2 = true; 
    if (this.mFirstLayout) {
      this.mCurItem = i;
      if (paramBoolean2)
        dispatchOnPageSelected(i); 
      requestLayout();
    } else {
      populate(i);
      scrollToItem(i, paramBoolean1, paramInt2, paramBoolean2);
    } 
  }
  
  OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    OnPageChangeListener onPageChangeListener = this.mInternalPageChangeListener;
    this.mInternalPageChangeListener = paramOnPageChangeListener;
    return onPageChangeListener;
  }
  
  public void setOffscreenPageLimit(int paramInt) {
    int i = paramInt;
    if (paramInt < 1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Requested offscreen page limit ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" too small; defaulting to ");
      stringBuilder.append(1);
      Log.w("ViewPager", stringBuilder.toString());
      i = 1;
    } 
    if (i != this.mOffscreenPageLimit) {
      this.mOffscreenPageLimit = i;
      populate();
    } 
  }
  
  @Deprecated
  public void setOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    this.mOnPageChangeListener = paramOnPageChangeListener;
  }
  
  public void setPageMargin(int paramInt) {
    int j = this.mPageMargin;
    this.mPageMargin = paramInt;
    int i = getWidth();
    recomputeScrollPosition(i, i, paramInt, j);
    requestLayout();
  }
  
  public void setPageMarginDrawable(int paramInt) {
    setPageMarginDrawable(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setPageMarginDrawable(Drawable paramDrawable) {
    boolean bool;
    this.mMarginDrawable = paramDrawable;
    if (paramDrawable != null)
      refreshDrawableState(); 
    if (paramDrawable == null) {
      bool = true;
    } else {
      bool = false;
    } 
    setWillNotDraw(bool);
    invalidate();
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer) {
    setPageTransformer(paramBoolean, paramPageTransformer, 2);
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer, int paramInt) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    byte b = 1;
    if (paramPageTransformer != null) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (this.mPageTransformer != null) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    if (bool2 != bool3) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.mPageTransformer = paramPageTransformer;
    setChildrenDrawingOrderEnabled(bool2);
    if (bool2) {
      if (paramBoolean)
        b = 2; 
      this.mDrawingOrder = b;
      this.mPageTransformerLayerType = paramInt;
    } else {
      this.mDrawingOrder = 0;
    } 
    if (bool1)
      populate(); 
  }
  
  void setScrollState(int paramInt) {
    if (this.mScrollState == paramInt)
      return; 
    this.mScrollState = paramInt;
    if (this.mPageTransformer != null) {
      boolean bool;
      if (paramInt != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      enableLayers(bool);
    } 
    dispatchOnScrollStateChanged(paramInt);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2) {
    smoothScrollTo(paramInt1, paramInt2, 0);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, int paramInt3) {
    int i;
    if (getChildCount() == 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    Scroller scroller = this.mScroller;
    if (scroller != null && !scroller.isFinished()) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      if (this.mIsScrollStarted) {
        i = this.mScroller.getCurrX();
      } else {
        i = this.mScroller.getStartX();
      } 
      this.mScroller.abortAnimation();
      setScrollingCacheEnabled(false);
    } else {
      i = getScrollX();
    } 
    int j = getScrollY();
    int k = paramInt1 - i;
    paramInt2 -= j;
    if (k == 0 && paramInt2 == 0) {
      completeScroll(false);
      populate();
      setScrollState(0);
      return;
    } 
    setScrollingCacheEnabled(true);
    setScrollState(2);
    paramInt1 = getClientWidth();
    int m = paramInt1 / 2;
    float f2 = Math.abs(k);
    float f1 = paramInt1;
    float f3 = Math.min(1.0F, f2 * 1.0F / f1);
    f2 = m;
    f3 = distanceInfluenceForSnapDuration(f3);
    paramInt1 = Math.abs(paramInt3);
    if (paramInt1 > 0) {
      paramInt1 = Math.round(Math.abs((f2 + f3 * f2) / paramInt1) * 1000.0F) * 4;
    } else {
      f2 = this.mAdapter.getPageWidth(this.mCurItem);
      paramInt1 = (int)((Math.abs(k) / (f1 * f2 + this.mPageMargin) + 1.0F) * 100.0F);
    } 
    paramInt1 = Math.min(paramInt1, 600);
    this.mIsScrollStarted = false;
    this.mScroller.startScroll(i, j, k, paramInt2, paramInt1);
    ViewCompat.postInvalidateOnAnimation((View)this);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (super.verifyDrawable(paramDrawable) || paramDrawable == this.mMarginDrawable);
  }
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE})
  public static @interface DecorView {}
  
  static class ItemInfo {
    Object object;
    
    float offset;
    
    int position;
    
    boolean scrolling;
    
    float widthFactor;
  }
  
  public static class LayoutParams extends ViewGroup.LayoutParams {
    int childIndex;
    
    public int gravity;
    
    public boolean isDecor;
    
    boolean needsMeasure;
    
    int position;
    
    float widthFactor = 0.0F;
    
    public LayoutParams() {
      super(-1, -1);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, ViewPager.LAYOUT_ATTRS);
      this.gravity = typedArray.getInteger(0, 48);
      typedArray.recycle();
    }
  }
  
  class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
    final ViewPager this$0;
    
    private boolean canScroll() {
      PagerAdapter pagerAdapter = ViewPager.this.mAdapter;
      boolean bool = true;
      if (pagerAdapter == null || ViewPager.this.mAdapter.getCount() <= 1)
        bool = false; 
      return bool;
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(ViewPager.class.getName());
      param1AccessibilityEvent.setScrollable(canScroll());
      if (param1AccessibilityEvent.getEventType() == 4096 && ViewPager.this.mAdapter != null) {
        param1AccessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount());
        param1AccessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
        param1AccessibilityEvent.setToIndex(ViewPager.this.mCurItem);
      } 
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      param1AccessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
      param1AccessibilityNodeInfoCompat.setScrollable(canScroll());
      if (ViewPager.this.canScrollHorizontally(1))
        param1AccessibilityNodeInfoCompat.addAction(4096); 
      if (ViewPager.this.canScrollHorizontally(-1))
        param1AccessibilityNodeInfoCompat.addAction(8192); 
    }
    
    public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
      if (super.performAccessibilityAction(param1View, param1Int, param1Bundle))
        return true; 
      if (param1Int != 4096) {
        if (param1Int != 8192)
          return false; 
        if (ViewPager.this.canScrollHorizontally(-1)) {
          ViewPager viewPager = ViewPager.this;
          viewPager.setCurrentItem(viewPager.mCurItem - 1);
          return true;
        } 
        return false;
      } 
      if (ViewPager.this.canScrollHorizontally(1)) {
        ViewPager viewPager = ViewPager.this;
        viewPager.setCurrentItem(viewPager.mCurItem + 1);
        return true;
      } 
      return false;
    }
  }
  
  public static interface OnAdapterChangeListener {
    void onAdapterChanged(ViewPager param1ViewPager, PagerAdapter param1PagerAdapter1, PagerAdapter param1PagerAdapter2);
  }
  
  public static interface OnPageChangeListener {
    void onPageScrollStateChanged(int param1Int);
    
    void onPageScrolled(int param1Int1, float param1Float, int param1Int2);
    
    void onPageSelected(int param1Int);
  }
  
  public static interface PageTransformer {
    void transformPage(View param1View, float param1Float);
  }
  
  private class PagerObserver extends DataSetObserver {
    final ViewPager this$0;
    
    public void onChanged() {
      ViewPager.this.dataSetChanged();
    }
    
    public void onInvalidated() {
      ViewPager.this.dataSetChanged();
    }
  }
  
  public static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public ViewPager.SavedState createFromParcel(Parcel param2Parcel) {
          return new ViewPager.SavedState(param2Parcel, null);
        }
        
        public ViewPager.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new ViewPager.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public ViewPager.SavedState[] newArray(int param2Int) {
          return new ViewPager.SavedState[param2Int];
        }
      };
    
    Parcelable adapterState;
    
    ClassLoader loader;
    
    int position;
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      ClassLoader classLoader = param1ClassLoader;
      if (param1ClassLoader == null)
        classLoader = getClass().getClassLoader(); 
      this.position = param1Parcel.readInt();
      this.adapterState = param1Parcel.readParcelable(classLoader);
      this.loader = classLoader;
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("FragmentPager.SavedState{");
      stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      stringBuilder.append(" position=");
      stringBuilder.append(this.position);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.position);
      param1Parcel.writeParcelable(this.adapterState, param1Int);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public ViewPager.SavedState createFromParcel(Parcel param1Parcel) {
      return new ViewPager.SavedState(param1Parcel, null);
    }
    
    public ViewPager.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new ViewPager.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public ViewPager.SavedState[] newArray(int param1Int) {
      return new ViewPager.SavedState[param1Int];
    }
  }
  
  public static class SimpleOnPageChangeListener implements OnPageChangeListener {
    public void onPageScrollStateChanged(int param1Int) {}
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {}
    
    public void onPageSelected(int param1Int) {}
  }
  
  static class ViewPositionComparator implements Comparator<View> {
    public int compare(View param1View1, View param1View2) {
      ViewPager.LayoutParams layoutParams1 = (ViewPager.LayoutParams)param1View1.getLayoutParams();
      ViewPager.LayoutParams layoutParams2 = (ViewPager.LayoutParams)param1View2.getLayoutParams();
      if (layoutParams1.isDecor != layoutParams2.isDecor) {
        byte b;
        if (layoutParams1.isDecor) {
          b = 1;
        } else {
          b = -1;
        } 
        return b;
      } 
      return layoutParams1.position - layoutParams2.position;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\viewpager\widget\ViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */