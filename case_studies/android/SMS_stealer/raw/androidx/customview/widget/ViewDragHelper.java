package androidx.customview.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import java.util.Arrays;

public class ViewDragHelper {
  private static final int BASE_SETTLE_DURATION = 256;
  
  public static final int DIRECTION_ALL = 3;
  
  public static final int DIRECTION_HORIZONTAL = 1;
  
  public static final int DIRECTION_VERTICAL = 2;
  
  public static final int EDGE_ALL = 15;
  
  public static final int EDGE_BOTTOM = 8;
  
  public static final int EDGE_LEFT = 1;
  
  public static final int EDGE_RIGHT = 2;
  
  private static final int EDGE_SIZE = 20;
  
  public static final int EDGE_TOP = 4;
  
  public static final int INVALID_POINTER = -1;
  
  private static final int MAX_SETTLE_DURATION = 600;
  
  public static final int STATE_DRAGGING = 1;
  
  public static final int STATE_IDLE = 0;
  
  public static final int STATE_SETTLING = 2;
  
  private static final String TAG = "ViewDragHelper";
  
  private static final Interpolator sInterpolator = new Interpolator() {
      public float getInterpolation(float param1Float) {
        param1Float--;
        return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
      }
    };
  
  private int mActivePointerId = -1;
  
  private final Callback mCallback;
  
  private View mCapturedView;
  
  private int mDragState;
  
  private int[] mEdgeDragsInProgress;
  
  private int[] mEdgeDragsLocked;
  
  private int mEdgeSize;
  
  private int[] mInitialEdgesTouched;
  
  private float[] mInitialMotionX;
  
  private float[] mInitialMotionY;
  
  private float[] mLastMotionX;
  
  private float[] mLastMotionY;
  
  private float mMaxVelocity;
  
  private float mMinVelocity;
  
  private final ViewGroup mParentView;
  
  private int mPointersDown;
  
  private boolean mReleaseInProgress;
  
  private OverScroller mScroller;
  
  private final Runnable mSetIdleRunnable = new Runnable() {
      final ViewDragHelper this$0;
      
      public void run() {
        ViewDragHelper.this.setDragState(0);
      }
    };
  
  private int mTouchSlop;
  
  private int mTrackingEdges;
  
  private VelocityTracker mVelocityTracker;
  
  private ViewDragHelper(Context paramContext, ViewGroup paramViewGroup, Callback paramCallback) {
    if (paramViewGroup != null) {
      if (paramCallback != null) {
        this.mParentView = paramViewGroup;
        this.mCallback = paramCallback;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
        this.mEdgeSize = (int)((paramContext.getResources().getDisplayMetrics()).density * 20.0F + 0.5F);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(paramContext, sInterpolator);
        return;
      } 
      throw new IllegalArgumentException("Callback may not be null");
    } 
    throw new IllegalArgumentException("Parent view may not be null");
  }
  
  private boolean checkNewEdgeDrag(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) {
    paramFloat1 = Math.abs(paramFloat1);
    paramFloat2 = Math.abs(paramFloat2);
    int i = this.mInitialEdgesTouched[paramInt1];
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((i & paramInt2) == paramInt2) {
      bool1 = bool2;
      if ((this.mTrackingEdges & paramInt2) != 0) {
        bool1 = bool2;
        if ((this.mEdgeDragsLocked[paramInt1] & paramInt2) != paramInt2) {
          bool1 = bool2;
          if ((this.mEdgeDragsInProgress[paramInt1] & paramInt2) != paramInt2) {
            i = this.mTouchSlop;
            if (paramFloat1 <= i && paramFloat2 <= i) {
              bool1 = bool2;
            } else {
              if (paramFloat1 < paramFloat2 * 0.5F && this.mCallback.onEdgeLock(paramInt2)) {
                int[] arrayOfInt = this.mEdgeDragsLocked;
                arrayOfInt[paramInt1] = arrayOfInt[paramInt1] | paramInt2;
                return false;
              } 
              bool1 = bool2;
              if ((this.mEdgeDragsInProgress[paramInt1] & paramInt2) == 0) {
                bool1 = bool2;
                if (paramFloat1 > this.mTouchSlop)
                  bool1 = true; 
              } 
            } 
          } 
        } 
      } 
    } 
    return bool1;
  }
  
  private boolean checkTouchSlop(View paramView, float paramFloat1, float paramFloat2) {
    int i;
    boolean bool1;
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool2 = false;
    if (paramView == null)
      return false; 
    if (this.mCallback.getViewHorizontalDragRange(paramView) > 0) {
      i = 1;
    } else {
      i = 0;
    } 
    if (this.mCallback.getViewVerticalDragRange(paramView) > 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (i && bool1) {
      i = this.mTouchSlop;
      if (paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 > (i * i))
        bool2 = true; 
      return bool2;
    } 
    if (i != 0) {
      bool2 = bool4;
      if (Math.abs(paramFloat1) > this.mTouchSlop)
        bool2 = true; 
      return bool2;
    } 
    bool2 = bool3;
    if (bool1) {
      bool2 = bool3;
      if (Math.abs(paramFloat2) > this.mTouchSlop)
        bool2 = true; 
    } 
    return bool2;
  }
  
  private float clampMag(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = Math.abs(paramFloat1);
    if (f < paramFloat2)
      return 0.0F; 
    if (f > paramFloat3) {
      if (paramFloat1 <= 0.0F)
        paramFloat3 = -paramFloat3; 
      return paramFloat3;
    } 
    return paramFloat1;
  }
  
  private int clampMag(int paramInt1, int paramInt2, int paramInt3) {
    int i = Math.abs(paramInt1);
    if (i < paramInt2)
      return 0; 
    if (i > paramInt3) {
      if (paramInt1 <= 0)
        paramInt3 = -paramInt3; 
      return paramInt3;
    } 
    return paramInt1;
  }
  
  private void clearMotionHistory() {
    float[] arrayOfFloat = this.mInitialMotionX;
    if (arrayOfFloat == null)
      return; 
    Arrays.fill(arrayOfFloat, 0.0F);
    Arrays.fill(this.mInitialMotionY, 0.0F);
    Arrays.fill(this.mLastMotionX, 0.0F);
    Arrays.fill(this.mLastMotionY, 0.0F);
    Arrays.fill(this.mInitialEdgesTouched, 0);
    Arrays.fill(this.mEdgeDragsInProgress, 0);
    Arrays.fill(this.mEdgeDragsLocked, 0);
    this.mPointersDown = 0;
  }
  
  private void clearMotionHistory(int paramInt) {
    if (this.mInitialMotionX != null && isPointerDown(paramInt)) {
      this.mInitialMotionX[paramInt] = 0.0F;
      this.mInitialMotionY[paramInt] = 0.0F;
      this.mLastMotionX[paramInt] = 0.0F;
      this.mLastMotionY[paramInt] = 0.0F;
      this.mInitialEdgesTouched[paramInt] = 0;
      this.mEdgeDragsInProgress[paramInt] = 0;
      this.mEdgeDragsLocked[paramInt] = 0;
      this.mPointersDown = (1 << paramInt ^ 0xFFFFFFFF) & this.mPointersDown;
    } 
  }
  
  private int computeAxisDuration(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 == 0)
      return 0; 
    int j = this.mParentView.getWidth();
    int i = j / 2;
    float f2 = Math.min(1.0F, Math.abs(paramInt1) / j);
    float f1 = i;
    f2 = distanceInfluenceForSnapDuration(f2);
    paramInt2 = Math.abs(paramInt2);
    if (paramInt2 > 0) {
      paramInt1 = Math.round(Math.abs((f1 + f2 * f1) / paramInt2) * 1000.0F) * 4;
    } else {
      paramInt1 = (int)((Math.abs(paramInt1) / paramInt3 + 1.0F) * 256.0F);
    } 
    return Math.min(paramInt1, 600);
  }
  
  private int computeSettleDuration(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    float f2;
    paramInt3 = clampMag(paramInt3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    paramInt4 = clampMag(paramInt4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    int k = Math.abs(paramInt1);
    int m = Math.abs(paramInt2);
    int i1 = Math.abs(paramInt3);
    int j = Math.abs(paramInt4);
    int i = i1 + j;
    int n = k + m;
    if (paramInt3 != 0) {
      f2 = i1;
      f1 = i;
    } else {
      f2 = k;
      f1 = n;
    } 
    float f3 = f2 / f1;
    if (paramInt4 != 0) {
      f2 = j;
      f1 = i;
    } else {
      f2 = m;
      f1 = n;
    } 
    float f1 = f2 / f1;
    paramInt1 = computeAxisDuration(paramInt1, paramInt3, this.mCallback.getViewHorizontalDragRange(paramView));
    paramInt2 = computeAxisDuration(paramInt2, paramInt4, this.mCallback.getViewVerticalDragRange(paramView));
    return (int)(paramInt1 * f3 + paramInt2 * f1);
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, float paramFloat, Callback paramCallback) {
    ViewDragHelper viewDragHelper = create(paramViewGroup, paramCallback);
    viewDragHelper.mTouchSlop = (int)(viewDragHelper.mTouchSlop * 1.0F / paramFloat);
    return viewDragHelper;
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, Callback paramCallback) {
    return new ViewDragHelper(paramViewGroup.getContext(), paramViewGroup, paramCallback);
  }
  
  private void dispatchViewReleased(float paramFloat1, float paramFloat2) {
    this.mReleaseInProgress = true;
    this.mCallback.onViewReleased(this.mCapturedView, paramFloat1, paramFloat2);
    this.mReleaseInProgress = false;
    if (this.mDragState == 1)
      setDragState(0); 
  }
  
  private float distanceInfluenceForSnapDuration(float paramFloat) {
    return (float)Math.sin(((paramFloat - 0.5F) * 0.47123894F));
  }
  
  private void dragTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int j = this.mCapturedView.getLeft();
    int k = this.mCapturedView.getTop();
    int i = paramInt1;
    if (paramInt3 != 0) {
      i = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, paramInt1, paramInt3);
      ViewCompat.offsetLeftAndRight(this.mCapturedView, i - j);
    } 
    paramInt1 = paramInt2;
    if (paramInt4 != 0) {
      paramInt1 = this.mCallback.clampViewPositionVertical(this.mCapturedView, paramInt2, paramInt4);
      ViewCompat.offsetTopAndBottom(this.mCapturedView, paramInt1 - k);
    } 
    if (paramInt3 != 0 || paramInt4 != 0)
      this.mCallback.onViewPositionChanged(this.mCapturedView, i, paramInt1, i - j, paramInt1 - k); 
  }
  
  private void ensureMotionHistorySizeForId(int paramInt) {
    float[] arrayOfFloat = this.mInitialMotionX;
    if (arrayOfFloat == null || arrayOfFloat.length <= paramInt) {
      float[] arrayOfFloat3 = new float[++paramInt];
      arrayOfFloat = new float[paramInt];
      float[] arrayOfFloat1 = new float[paramInt];
      float[] arrayOfFloat2 = new float[paramInt];
      int[] arrayOfInt2 = new int[paramInt];
      int[] arrayOfInt3 = new int[paramInt];
      int[] arrayOfInt1 = new int[paramInt];
      float[] arrayOfFloat4 = this.mInitialMotionX;
      if (arrayOfFloat4 != null) {
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat3, 0, arrayOfFloat4.length);
        arrayOfFloat4 = this.mInitialMotionY;
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat, 0, arrayOfFloat4.length);
        arrayOfFloat4 = this.mLastMotionX;
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat1, 0, arrayOfFloat4.length);
        arrayOfFloat4 = this.mLastMotionY;
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat2, 0, arrayOfFloat4.length);
        int[] arrayOfInt = this.mInitialEdgesTouched;
        System.arraycopy(arrayOfInt, 0, arrayOfInt2, 0, arrayOfInt.length);
        arrayOfInt = this.mEdgeDragsInProgress;
        System.arraycopy(arrayOfInt, 0, arrayOfInt3, 0, arrayOfInt.length);
        arrayOfInt = this.mEdgeDragsLocked;
        System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, arrayOfInt.length);
      } 
      this.mInitialMotionX = arrayOfFloat3;
      this.mInitialMotionY = arrayOfFloat;
      this.mLastMotionX = arrayOfFloat1;
      this.mLastMotionY = arrayOfFloat2;
      this.mInitialEdgesTouched = arrayOfInt2;
      this.mEdgeDragsInProgress = arrayOfInt3;
      this.mEdgeDragsLocked = arrayOfInt1;
    } 
  }
  
  private boolean forceSettleCapturedViewAt(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = this.mCapturedView.getLeft();
    int j = this.mCapturedView.getTop();
    paramInt1 -= i;
    paramInt2 -= j;
    if (paramInt1 == 0 && paramInt2 == 0) {
      this.mScroller.abortAnimation();
      setDragState(0);
      return false;
    } 
    paramInt3 = computeSettleDuration(this.mCapturedView, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mScroller.startScroll(i, j, paramInt1, paramInt2, paramInt3);
    setDragState(2);
    return true;
  }
  
  private int getEdgesTouched(int paramInt1, int paramInt2) {
    if (paramInt1 < this.mParentView.getLeft() + this.mEdgeSize) {
      j = 1;
    } else {
      j = 0;
    } 
    int i = j;
    if (paramInt2 < this.mParentView.getTop() + this.mEdgeSize)
      i = j | 0x4; 
    int j = i;
    if (paramInt1 > this.mParentView.getRight() - this.mEdgeSize)
      j = i | 0x2; 
    paramInt1 = j;
    if (paramInt2 > this.mParentView.getBottom() - this.mEdgeSize)
      paramInt1 = j | 0x8; 
    return paramInt1;
  }
  
  private boolean isValidPointerForActionMove(int paramInt) {
    if (!isPointerDown(paramInt)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Ignoring pointerId=");
      stringBuilder.append(paramInt);
      stringBuilder.append(" because ACTION_DOWN was not received ");
      stringBuilder.append("for this pointer before ACTION_MOVE. It likely happened because ");
      stringBuilder.append(" ViewDragHelper did not receive all the events in the event stream.");
      Log.e("ViewDragHelper", stringBuilder.toString());
      return false;
    } 
    return true;
  }
  
  private void releaseViewForPointerUp() {
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
    dispatchViewReleased(clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
  }
  
  private void reportNewEdgeDrags(float paramFloat1, float paramFloat2, int paramInt) {
    int j = 1;
    if (!checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 1))
      j = 0; 
    int i = j;
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 4))
      i = j | 0x4; 
    j = i;
    if (checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 2))
      j = i | 0x2; 
    i = j;
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 8))
      i = j | 0x8; 
    if (i != 0) {
      int[] arrayOfInt = this.mEdgeDragsInProgress;
      arrayOfInt[paramInt] = arrayOfInt[paramInt] | i;
      this.mCallback.onEdgeDragStarted(i, paramInt);
    } 
  }
  
  private void saveInitialMotion(float paramFloat1, float paramFloat2, int paramInt) {
    ensureMotionHistorySizeForId(paramInt);
    float[] arrayOfFloat = this.mInitialMotionX;
    this.mLastMotionX[paramInt] = paramFloat1;
    arrayOfFloat[paramInt] = paramFloat1;
    arrayOfFloat = this.mInitialMotionY;
    this.mLastMotionY[paramInt] = paramFloat2;
    arrayOfFloat[paramInt] = paramFloat2;
    this.mInitialEdgesTouched[paramInt] = getEdgesTouched((int)paramFloat1, (int)paramFloat2);
    this.mPointersDown |= 1 << paramInt;
  }
  
  private void saveLastMotion(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getPointerCount();
    for (byte b = 0; b < i; b++) {
      int j = paramMotionEvent.getPointerId(b);
      if (isValidPointerForActionMove(j)) {
        float f1 = paramMotionEvent.getX(b);
        float f2 = paramMotionEvent.getY(b);
        this.mLastMotionX[j] = f1;
        this.mLastMotionY[j] = f2;
      } 
    } 
  }
  
  public void abort() {
    cancel();
    if (this.mDragState == 2) {
      int m = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      this.mScroller.abortAnimation();
      int i = this.mScroller.getCurrX();
      int k = this.mScroller.getCurrY();
      this.mCallback.onViewPositionChanged(this.mCapturedView, i, k, i - m, k - j);
    } 
    setDragState(0);
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    boolean bool1 = paramView instanceof ViewGroup;
    boolean bool = true;
    if (bool1) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      int k = paramView.getScrollX();
      int j = paramView.getScrollY();
      for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
        View view = viewGroup.getChildAt(i);
        int m = paramInt3 + k;
        if (m >= view.getLeft() && m < view.getRight()) {
          int n = paramInt4 + j;
          if (n >= view.getTop() && n < view.getBottom() && canScroll(view, true, paramInt1, paramInt2, m - view.getLeft(), n - view.getTop()))
            return true; 
        } 
      } 
    } 
    if (paramBoolean) {
      paramBoolean = bool;
      if (!paramView.canScrollHorizontally(-paramInt1)) {
        if (paramView.canScrollVertically(-paramInt2))
          return bool; 
      } else {
        return paramBoolean;
      } 
    } 
    return false;
  }
  
  public void cancel() {
    this.mActivePointerId = -1;
    clearMotionHistory();
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  public void captureChildView(View paramView, int paramInt) {
    if (paramView.getParent() == this.mParentView) {
      this.mCapturedView = paramView;
      this.mActivePointerId = paramInt;
      this.mCallback.onViewCaptured(paramView, paramInt);
      setDragState(1);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
    stringBuilder.append(this.mParentView);
    stringBuilder.append(")");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean checkTouchSlop(int paramInt) {
    int i = this.mInitialMotionX.length;
    for (byte b = 0; b < i; b++) {
      if (checkTouchSlop(paramInt, b))
        return true; 
    } 
    return false;
  }
  
  public boolean checkTouchSlop(int paramInt1, int paramInt2) {
    boolean bool1;
    boolean bool = isPointerDown(paramInt2);
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool2 = false;
    if (!bool)
      return false; 
    if ((paramInt1 & 0x1) == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if ((paramInt1 & 0x2) == 2) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    float f2 = this.mLastMotionX[paramInt2] - this.mInitialMotionX[paramInt2];
    float f1 = this.mLastMotionY[paramInt2] - this.mInitialMotionY[paramInt2];
    if (bool1 && paramInt1 != 0) {
      paramInt1 = this.mTouchSlop;
      if (f2 * f2 + f1 * f1 > (paramInt1 * paramInt1))
        bool2 = true; 
      return bool2;
    } 
    if (bool1) {
      bool2 = bool4;
      if (Math.abs(f2) > this.mTouchSlop)
        bool2 = true; 
      return bool2;
    } 
    bool2 = bool3;
    if (paramInt1 != 0) {
      bool2 = bool3;
      if (Math.abs(f1) > this.mTouchSlop)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public boolean continueSettling(boolean paramBoolean) {
    int i = this.mDragState;
    boolean bool = false;
    if (i == 2) {
      boolean bool2 = this.mScroller.computeScrollOffset();
      int m = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      int k = m - this.mCapturedView.getLeft();
      i = j - this.mCapturedView.getTop();
      if (k != 0)
        ViewCompat.offsetLeftAndRight(this.mCapturedView, k); 
      if (i != 0)
        ViewCompat.offsetTopAndBottom(this.mCapturedView, i); 
      if (k != 0 || i != 0)
        this.mCallback.onViewPositionChanged(this.mCapturedView, m, j, k, i); 
      boolean bool1 = bool2;
      if (bool2) {
        bool1 = bool2;
        if (m == this.mScroller.getFinalX()) {
          bool1 = bool2;
          if (j == this.mScroller.getFinalY()) {
            this.mScroller.abortAnimation();
            bool1 = false;
          } 
        } 
      } 
      if (!bool1)
        if (paramBoolean) {
          this.mParentView.post(this.mSetIdleRunnable);
        } else {
          setDragState(0);
        }  
    } 
    paramBoolean = bool;
    if (this.mDragState == 2)
      paramBoolean = true; 
    return paramBoolean;
  }
  
  public View findTopChildUnder(int paramInt1, int paramInt2) {
    for (int i = this.mParentView.getChildCount() - 1; i >= 0; i--) {
      View view = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
      if (paramInt1 >= view.getLeft() && paramInt1 < view.getRight() && paramInt2 >= view.getTop() && paramInt2 < view.getBottom())
        return view; 
    } 
    return null;
  }
  
  public void flingCapturedView(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mReleaseInProgress) {
      this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId), paramInt1, paramInt3, paramInt2, paramInt4);
      setDragState(2);
      return;
    } 
    throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
  }
  
  public int getActivePointerId() {
    return this.mActivePointerId;
  }
  
  public View getCapturedView() {
    return this.mCapturedView;
  }
  
  public int getEdgeSize() {
    return this.mEdgeSize;
  }
  
  public float getMinVelocity() {
    return this.mMinVelocity;
  }
  
  public int getTouchSlop() {
    return this.mTouchSlop;
  }
  
  public int getViewDragState() {
    return this.mDragState;
  }
  
  public boolean isCapturedViewUnder(int paramInt1, int paramInt2) {
    return isViewUnder(this.mCapturedView, paramInt1, paramInt2);
  }
  
  public boolean isEdgeTouched(int paramInt) {
    int i = this.mInitialEdgesTouched.length;
    for (byte b = 0; b < i; b++) {
      if (isEdgeTouched(paramInt, b))
        return true; 
    } 
    return false;
  }
  
  public boolean isEdgeTouched(int paramInt1, int paramInt2) {
    boolean bool;
    if (isPointerDown(paramInt2) && (paramInt1 & this.mInitialEdgesTouched[paramInt2]) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isPointerDown(int paramInt) {
    int i = this.mPointersDown;
    boolean bool = true;
    if ((1 << paramInt & i) == 0)
      bool = false; 
    return bool;
  }
  
  public boolean isViewUnder(View paramView, int paramInt1, int paramInt2) {
    boolean bool2 = false;
    if (paramView == null)
      return false; 
    boolean bool1 = bool2;
    if (paramInt1 >= paramView.getLeft()) {
      bool1 = bool2;
      if (paramInt1 < paramView.getRight()) {
        bool1 = bool2;
        if (paramInt2 >= paramView.getTop()) {
          bool1 = bool2;
          if (paramInt2 < paramView.getBottom())
            bool1 = true; 
        } 
      } 
    } 
    return bool1;
  }
  
  public void processTouchEvent(MotionEvent paramMotionEvent) {
    int m = paramMotionEvent.getActionMasked();
    int k = paramMotionEvent.getActionIndex();
    if (m == 0)
      cancel(); 
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int j = 0;
    int i = 0;
    if (m != 0) {
      if (m != 1) {
        if (m != 2) {
          if (m != 3) {
            if (m != 5) {
              if (m == 6) {
                j = paramMotionEvent.getPointerId(k);
                if (this.mDragState == 1 && j == this.mActivePointerId) {
                  k = paramMotionEvent.getPointerCount();
                  while (true) {
                    if (i < k) {
                      m = paramMotionEvent.getPointerId(i);
                      if (m != this.mActivePointerId) {
                        float f1 = paramMotionEvent.getX(i);
                        float f2 = paramMotionEvent.getY(i);
                        View view1 = findTopChildUnder((int)f1, (int)f2);
                        View view2 = this.mCapturedView;
                        if (view1 == view2 && tryCaptureViewForDrag(view2, m)) {
                          i = this.mActivePointerId;
                          break;
                        } 
                      } 
                      i++;
                      continue;
                    } 
                    i = -1;
                    break;
                  } 
                  if (i == -1)
                    releaseViewForPointerUp(); 
                } 
                clearMotionHistory(j);
              } 
            } else {
              i = paramMotionEvent.getPointerId(k);
              float f1 = paramMotionEvent.getX(k);
              float f2 = paramMotionEvent.getY(k);
              saveInitialMotion(f1, f2, i);
              if (this.mDragState == 0) {
                tryCaptureViewForDrag(findTopChildUnder((int)f1, (int)f2), i);
                j = this.mInitialEdgesTouched[i];
                k = this.mTrackingEdges;
                if ((j & k) != 0)
                  this.mCallback.onEdgeTouched(j & k, i); 
              } else if (isCapturedViewUnder((int)f1, (int)f2)) {
                tryCaptureViewForDrag(this.mCapturedView, i);
              } 
            } 
          } else {
            if (this.mDragState == 1)
              dispatchViewReleased(0.0F, 0.0F); 
            cancel();
          } 
        } else if (this.mDragState == 1) {
          if (isValidPointerForActionMove(this.mActivePointerId)) {
            i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
            float f2 = paramMotionEvent.getX(i);
            float f1 = paramMotionEvent.getY(i);
            float[] arrayOfFloat = this.mLastMotionX;
            j = this.mActivePointerId;
            i = (int)(f2 - arrayOfFloat[j]);
            j = (int)(f1 - this.mLastMotionY[j]);
            dragTo(this.mCapturedView.getLeft() + i, this.mCapturedView.getTop() + j, i, j);
            saveLastMotion(paramMotionEvent);
          } 
        } else {
          k = paramMotionEvent.getPointerCount();
          for (i = j; i < k; i++) {
            j = paramMotionEvent.getPointerId(i);
            if (isValidPointerForActionMove(j)) {
              float f4 = paramMotionEvent.getX(i);
              float f1 = paramMotionEvent.getY(i);
              float f3 = f4 - this.mInitialMotionX[j];
              float f2 = f1 - this.mInitialMotionY[j];
              reportNewEdgeDrags(f3, f2, j);
              if (this.mDragState == 1)
                break; 
              View view = findTopChildUnder((int)f4, (int)f1);
              if (checkTouchSlop(view, f3, f2) && tryCaptureViewForDrag(view, j))
                break; 
            } 
          } 
          saveLastMotion(paramMotionEvent);
        } 
      } else {
        if (this.mDragState == 1)
          releaseViewForPointerUp(); 
        cancel();
      } 
    } else {
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      k = paramMotionEvent.getPointerId(0);
      View view = findTopChildUnder((int)f1, (int)f2);
      saveInitialMotion(f1, f2, k);
      tryCaptureViewForDrag(view, k);
      j = this.mInitialEdgesTouched[k];
      i = this.mTrackingEdges;
      if ((j & i) != 0)
        this.mCallback.onEdgeTouched(j & i, k); 
    } 
  }
  
  void setDragState(int paramInt) {
    this.mParentView.removeCallbacks(this.mSetIdleRunnable);
    if (this.mDragState != paramInt) {
      this.mDragState = paramInt;
      this.mCallback.onViewDragStateChanged(paramInt);
      if (this.mDragState == 0)
        this.mCapturedView = null; 
    } 
  }
  
  public void setEdgeTrackingEnabled(int paramInt) {
    this.mTrackingEdges = paramInt;
  }
  
  public void setMinVelocity(float paramFloat) {
    this.mMinVelocity = paramFloat;
  }
  
  public boolean settleCapturedViewAt(int paramInt1, int paramInt2) {
    if (this.mReleaseInProgress)
      return forceSettleCapturedViewAt(paramInt1, paramInt2, (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId)); 
    throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
  }
  
  public boolean shouldInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getActionMasked : ()I
    //   4: istore #6
    //   6: aload_1
    //   7: invokevirtual getActionIndex : ()I
    //   10: istore #7
    //   12: iload #6
    //   14: ifne -> 21
    //   17: aload_0
    //   18: invokevirtual cancel : ()V
    //   21: aload_0
    //   22: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   25: ifnonnull -> 35
    //   28: aload_0
    //   29: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   32: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   35: aload_0
    //   36: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   39: aload_1
    //   40: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   43: iload #6
    //   45: ifeq -> 521
    //   48: iload #6
    //   50: iconst_1
    //   51: if_icmpeq -> 514
    //   54: iload #6
    //   56: iconst_2
    //   57: if_icmpeq -> 210
    //   60: iload #6
    //   62: iconst_3
    //   63: if_icmpeq -> 514
    //   66: iload #6
    //   68: iconst_5
    //   69: if_icmpeq -> 95
    //   72: iload #6
    //   74: bipush #6
    //   76: if_icmpeq -> 82
    //   79: goto -> 616
    //   82: aload_0
    //   83: aload_1
    //   84: iload #7
    //   86: invokevirtual getPointerId : (I)I
    //   89: invokespecial clearMotionHistory : (I)V
    //   92: goto -> 79
    //   95: aload_1
    //   96: iload #7
    //   98: invokevirtual getPointerId : (I)I
    //   101: istore #6
    //   103: aload_1
    //   104: iload #7
    //   106: invokevirtual getX : (I)F
    //   109: fstore_2
    //   110: aload_1
    //   111: iload #7
    //   113: invokevirtual getY : (I)F
    //   116: fstore_3
    //   117: aload_0
    //   118: fload_2
    //   119: fload_3
    //   120: iload #6
    //   122: invokespecial saveInitialMotion : (FFI)V
    //   125: aload_0
    //   126: getfield mDragState : I
    //   129: istore #7
    //   131: iload #7
    //   133: ifne -> 176
    //   136: aload_0
    //   137: getfield mInitialEdgesTouched : [I
    //   140: iload #6
    //   142: iaload
    //   143: istore #7
    //   145: aload_0
    //   146: getfield mTrackingEdges : I
    //   149: istore #8
    //   151: iload #7
    //   153: iload #8
    //   155: iand
    //   156: ifeq -> 79
    //   159: aload_0
    //   160: getfield mCallback : Landroidx/customview/widget/ViewDragHelper$Callback;
    //   163: iload #7
    //   165: iload #8
    //   167: iand
    //   168: iload #6
    //   170: invokevirtual onEdgeTouched : (II)V
    //   173: goto -> 79
    //   176: iload #7
    //   178: iconst_2
    //   179: if_icmpne -> 79
    //   182: aload_0
    //   183: fload_2
    //   184: f2i
    //   185: fload_3
    //   186: f2i
    //   187: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   190: astore_1
    //   191: aload_1
    //   192: aload_0
    //   193: getfield mCapturedView : Landroid/view/View;
    //   196: if_acmpne -> 79
    //   199: aload_0
    //   200: aload_1
    //   201: iload #6
    //   203: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   206: pop
    //   207: goto -> 79
    //   210: aload_0
    //   211: getfield mInitialMotionX : [F
    //   214: ifnull -> 79
    //   217: aload_0
    //   218: getfield mInitialMotionY : [F
    //   221: ifnonnull -> 227
    //   224: goto -> 79
    //   227: aload_1
    //   228: invokevirtual getPointerCount : ()I
    //   231: istore #8
    //   233: iconst_0
    //   234: istore #6
    //   236: iload #6
    //   238: iload #8
    //   240: if_icmpge -> 506
    //   243: aload_1
    //   244: iload #6
    //   246: invokevirtual getPointerId : (I)I
    //   249: istore #9
    //   251: aload_0
    //   252: iload #9
    //   254: invokespecial isValidPointerForActionMove : (I)Z
    //   257: ifne -> 263
    //   260: goto -> 500
    //   263: aload_1
    //   264: iload #6
    //   266: invokevirtual getX : (I)F
    //   269: fstore_3
    //   270: aload_1
    //   271: iload #6
    //   273: invokevirtual getY : (I)F
    //   276: fstore_2
    //   277: fload_3
    //   278: aload_0
    //   279: getfield mInitialMotionX : [F
    //   282: iload #9
    //   284: faload
    //   285: fsub
    //   286: fstore #4
    //   288: fload_2
    //   289: aload_0
    //   290: getfield mInitialMotionY : [F
    //   293: iload #9
    //   295: faload
    //   296: fsub
    //   297: fstore #5
    //   299: aload_0
    //   300: fload_3
    //   301: f2i
    //   302: fload_2
    //   303: f2i
    //   304: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   307: astore #17
    //   309: aload #17
    //   311: ifnull -> 333
    //   314: aload_0
    //   315: aload #17
    //   317: fload #4
    //   319: fload #5
    //   321: invokespecial checkTouchSlop : (Landroid/view/View;FF)Z
    //   324: ifeq -> 333
    //   327: iconst_1
    //   328: istore #7
    //   330: goto -> 336
    //   333: iconst_0
    //   334: istore #7
    //   336: iload #7
    //   338: ifeq -> 460
    //   341: aload #17
    //   343: invokevirtual getLeft : ()I
    //   346: istore #10
    //   348: fload #4
    //   350: f2i
    //   351: istore #11
    //   353: aload_0
    //   354: getfield mCallback : Landroidx/customview/widget/ViewDragHelper$Callback;
    //   357: aload #17
    //   359: iload #10
    //   361: iload #11
    //   363: iadd
    //   364: iload #11
    //   366: invokevirtual clampViewPositionHorizontal : (Landroid/view/View;II)I
    //   369: istore #11
    //   371: aload #17
    //   373: invokevirtual getTop : ()I
    //   376: istore #12
    //   378: fload #5
    //   380: f2i
    //   381: istore #13
    //   383: aload_0
    //   384: getfield mCallback : Landroidx/customview/widget/ViewDragHelper$Callback;
    //   387: aload #17
    //   389: iload #12
    //   391: iload #13
    //   393: iadd
    //   394: iload #13
    //   396: invokevirtual clampViewPositionVertical : (Landroid/view/View;II)I
    //   399: istore #15
    //   401: aload_0
    //   402: getfield mCallback : Landroidx/customview/widget/ViewDragHelper$Callback;
    //   405: aload #17
    //   407: invokevirtual getViewHorizontalDragRange : (Landroid/view/View;)I
    //   410: istore #13
    //   412: aload_0
    //   413: getfield mCallback : Landroidx/customview/widget/ViewDragHelper$Callback;
    //   416: aload #17
    //   418: invokevirtual getViewVerticalDragRange : (Landroid/view/View;)I
    //   421: istore #14
    //   423: iload #13
    //   425: ifeq -> 440
    //   428: iload #13
    //   430: ifle -> 460
    //   433: iload #11
    //   435: iload #10
    //   437: if_icmpne -> 460
    //   440: iload #14
    //   442: ifeq -> 506
    //   445: iload #14
    //   447: ifle -> 460
    //   450: iload #15
    //   452: iload #12
    //   454: if_icmpne -> 460
    //   457: goto -> 506
    //   460: aload_0
    //   461: fload #4
    //   463: fload #5
    //   465: iload #9
    //   467: invokespecial reportNewEdgeDrags : (FFI)V
    //   470: aload_0
    //   471: getfield mDragState : I
    //   474: iconst_1
    //   475: if_icmpne -> 481
    //   478: goto -> 506
    //   481: iload #7
    //   483: ifeq -> 500
    //   486: aload_0
    //   487: aload #17
    //   489: iload #9
    //   491: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   494: ifeq -> 500
    //   497: goto -> 506
    //   500: iinc #6, 1
    //   503: goto -> 236
    //   506: aload_0
    //   507: aload_1
    //   508: invokespecial saveLastMotion : (Landroid/view/MotionEvent;)V
    //   511: goto -> 79
    //   514: aload_0
    //   515: invokevirtual cancel : ()V
    //   518: goto -> 79
    //   521: aload_1
    //   522: invokevirtual getX : ()F
    //   525: fstore_2
    //   526: aload_1
    //   527: invokevirtual getY : ()F
    //   530: fstore_3
    //   531: aload_1
    //   532: iconst_0
    //   533: invokevirtual getPointerId : (I)I
    //   536: istore #8
    //   538: aload_0
    //   539: fload_2
    //   540: fload_3
    //   541: iload #8
    //   543: invokespecial saveInitialMotion : (FFI)V
    //   546: aload_0
    //   547: fload_2
    //   548: f2i
    //   549: fload_3
    //   550: f2i
    //   551: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   554: astore_1
    //   555: aload_1
    //   556: aload_0
    //   557: getfield mCapturedView : Landroid/view/View;
    //   560: if_acmpne -> 579
    //   563: aload_0
    //   564: getfield mDragState : I
    //   567: iconst_2
    //   568: if_icmpne -> 579
    //   571: aload_0
    //   572: aload_1
    //   573: iload #8
    //   575: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   578: pop
    //   579: aload_0
    //   580: getfield mInitialEdgesTouched : [I
    //   583: iload #8
    //   585: iaload
    //   586: istore #6
    //   588: aload_0
    //   589: getfield mTrackingEdges : I
    //   592: istore #7
    //   594: iload #6
    //   596: iload #7
    //   598: iand
    //   599: ifeq -> 616
    //   602: aload_0
    //   603: getfield mCallback : Landroidx/customview/widget/ViewDragHelper$Callback;
    //   606: iload #6
    //   608: iload #7
    //   610: iand
    //   611: iload #8
    //   613: invokevirtual onEdgeTouched : (II)V
    //   616: iconst_0
    //   617: istore #16
    //   619: aload_0
    //   620: getfield mDragState : I
    //   623: iconst_1
    //   624: if_icmpne -> 630
    //   627: iconst_1
    //   628: istore #16
    //   630: iload #16
    //   632: ireturn
  }
  
  public boolean smoothSlideViewTo(View paramView, int paramInt1, int paramInt2) {
    this.mCapturedView = paramView;
    this.mActivePointerId = -1;
    boolean bool = forceSettleCapturedViewAt(paramInt1, paramInt2, 0, 0);
    if (!bool && this.mDragState == 0 && this.mCapturedView != null)
      this.mCapturedView = null; 
    return bool;
  }
  
  boolean tryCaptureViewForDrag(View paramView, int paramInt) {
    if (paramView == this.mCapturedView && this.mActivePointerId == paramInt)
      return true; 
    if (paramView != null && this.mCallback.tryCaptureView(paramView, paramInt)) {
      this.mActivePointerId = paramInt;
      captureChildView(paramView, paramInt);
      return true;
    } 
    return false;
  }
  
  public static abstract class Callback {
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int getOrderedChildIndex(int param1Int) {
      return param1Int;
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      return 0;
    }
    
    public int getViewVerticalDragRange(View param1View) {
      return 0;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {}
    
    public boolean onEdgeLock(int param1Int) {
      return false;
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {}
    
    public void onViewCaptured(View param1View, int param1Int) {}
    
    public void onViewDragStateChanged(int param1Int) {}
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {}
    
    public abstract boolean tryCaptureView(View param1View, int param1Int);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\customview\widget\ViewDragHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */