package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.TextView;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Flow;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.core.view.NestedScrollingParent3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
  private static final boolean DEBUG = false;
  
  public static final int DEBUG_SHOW_NONE = 0;
  
  public static final int DEBUG_SHOW_PATH = 2;
  
  public static final int DEBUG_SHOW_PROGRESS = 1;
  
  private static final float EPSILON = 1.0E-5F;
  
  public static boolean IS_IN_EDIT_MODE = false;
  
  static final int MAX_KEY_FRAMES = 50;
  
  static final String TAG = "MotionLayout";
  
  public static final int TOUCH_UP_COMPLETE = 0;
  
  public static final int TOUCH_UP_COMPLETE_TO_END = 2;
  
  public static final int TOUCH_UP_COMPLETE_TO_START = 1;
  
  public static final int TOUCH_UP_DECELERATE = 4;
  
  public static final int TOUCH_UP_DECELERATE_AND_COMPLETE = 5;
  
  public static final int TOUCH_UP_STOP = 3;
  
  public static final int VELOCITY_LAYOUT = 1;
  
  public static final int VELOCITY_POST_LAYOUT = 0;
  
  public static final int VELOCITY_STATIC_LAYOUT = 3;
  
  public static final int VELOCITY_STATIC_POST_LAYOUT = 2;
  
  boolean firstDown = true;
  
  private float lastPos;
  
  private float lastY;
  
  private long mAnimationStartTime = 0L;
  
  private int mBeginState = -1;
  
  private RectF mBoundsCheck = new RectF();
  
  int mCurrentState = -1;
  
  int mDebugPath = 0;
  
  private DecelerateInterpolator mDecelerateLogic = new DecelerateInterpolator();
  
  private DesignTool mDesignTool;
  
  DevModeDraw mDevModeDraw;
  
  private int mEndState = -1;
  
  int mEndWrapHeight;
  
  int mEndWrapWidth;
  
  HashMap<View, MotionController> mFrameArrayList = new HashMap<View, MotionController>();
  
  private int mFrames = 0;
  
  int mHeightMeasureMode;
  
  private boolean mInLayout = false;
  
  boolean mInTransition = false;
  
  boolean mIndirectTransition = false;
  
  private boolean mInteractionEnabled = true;
  
  Interpolator mInterpolator;
  
  boolean mIsAnimating = false;
  
  private boolean mKeepAnimating = false;
  
  private KeyCache mKeyCache = new KeyCache();
  
  private long mLastDrawTime = -1L;
  
  private float mLastFps = 0.0F;
  
  private int mLastHeightMeasureSpec = 0;
  
  int mLastLayoutHeight;
  
  int mLastLayoutWidth;
  
  float mLastVelocity = 0.0F;
  
  private int mLastWidthMeasureSpec = 0;
  
  private float mListenerPosition = 0.0F;
  
  private int mListenerState = 0;
  
  protected boolean mMeasureDuringTransition = false;
  
  Model mModel = new Model();
  
  private boolean mNeedsFireTransitionCompleted = false;
  
  int mOldHeight;
  
  int mOldWidth;
  
  private ArrayList<MotionHelper> mOnHideHelpers = null;
  
  private ArrayList<MotionHelper> mOnShowHelpers = null;
  
  float mPostInterpolationPosition;
  
  private View mRegionView = null;
  
  MotionScene mScene;
  
  float mScrollTargetDT;
  
  float mScrollTargetDX;
  
  float mScrollTargetDY;
  
  long mScrollTargetTime;
  
  int mStartWrapHeight;
  
  int mStartWrapWidth;
  
  private StateCache mStateCache;
  
  private StopLogic mStopLogic = new StopLogic();
  
  private boolean mTemporalInterpolator = false;
  
  ArrayList<Integer> mTransitionCompleted = new ArrayList<Integer>();
  
  private float mTransitionDuration = 1.0F;
  
  float mTransitionGoalPosition = 0.0F;
  
  private boolean mTransitionInstantly;
  
  float mTransitionLastPosition = 0.0F;
  
  private long mTransitionLastTime;
  
  private TransitionListener mTransitionListener;
  
  private ArrayList<TransitionListener> mTransitionListeners = null;
  
  float mTransitionPosition = 0.0F;
  
  TransitionState mTransitionState = TransitionState.UNDEFINED;
  
  boolean mUndergoingMotion = false;
  
  int mWidthMeasureMode;
  
  public MotionLayout(Context paramContext) {
    super(paramContext);
    init((AttributeSet)null);
  }
  
  public MotionLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }
  
  public MotionLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }
  
  private void checkStructure() {
    MotionScene motionScene = this.mScene;
    if (motionScene == null) {
      Log.e("MotionLayout", "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
      return;
    } 
    int i = motionScene.getStartId();
    motionScene = this.mScene;
    checkStructure(i, motionScene.getConstraintSet(motionScene.getStartId()));
    SparseIntArray sparseIntArray2 = new SparseIntArray();
    SparseIntArray sparseIntArray1 = new SparseIntArray();
    for (MotionScene.Transition transition : this.mScene.getDefinedTransitions()) {
      if (transition == this.mScene.mCurrentTransition)
        Log.v("MotionLayout", "CHECK: CURRENT"); 
      checkStructure(transition);
      int j = transition.getStartConstraintSetId();
      i = transition.getEndConstraintSetId();
      String str1 = Debug.getName(getContext(), j);
      String str2 = Debug.getName(getContext(), i);
      if (sparseIntArray2.get(j) == i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: two transitions with the same start and end ");
        stringBuilder.append(str1);
        stringBuilder.append("->");
        stringBuilder.append(str2);
        Log.e("MotionLayout", stringBuilder.toString());
      } 
      if (sparseIntArray1.get(i) == j) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: you can't have reverse transitions");
        stringBuilder.append(str1);
        stringBuilder.append("->");
        stringBuilder.append(str2);
        Log.e("MotionLayout", stringBuilder.toString());
      } 
      sparseIntArray2.put(j, i);
      sparseIntArray1.put(i, j);
      if (this.mScene.getConstraintSet(j) == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" no such constraintSetStart ");
        stringBuilder.append(str1);
        Log.e("MotionLayout", stringBuilder.toString());
      } 
      if (this.mScene.getConstraintSet(i) == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" no such constraintSetEnd ");
        stringBuilder.append(str1);
        Log.e("MotionLayout", stringBuilder.toString());
      } 
    } 
  }
  
  private void checkStructure(int paramInt, ConstraintSet paramConstraintSet) {
    String str = Debug.getName(getContext(), paramInt);
    int j = getChildCount();
    int i = 0;
    for (paramInt = 0; paramInt < j; paramInt++) {
      View view = getChildAt(paramInt);
      int k = view.getId();
      if (k == -1) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: ");
        stringBuilder.append(str);
        stringBuilder.append(" ALL VIEWS SHOULD HAVE ID's ");
        stringBuilder.append(view.getClass().getName());
        stringBuilder.append(" does not!");
        Log.w("MotionLayout", stringBuilder.toString());
      } 
      if (paramConstraintSet.getConstraint(k) == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: ");
        stringBuilder.append(str);
        stringBuilder.append(" NO CONSTRAINTS for ");
        stringBuilder.append(Debug.getName(view));
        Log.w("MotionLayout", stringBuilder.toString());
      } 
    } 
    int[] arrayOfInt = paramConstraintSet.getKnownIds();
    for (paramInt = i; paramInt < arrayOfInt.length; paramInt++) {
      i = arrayOfInt[paramInt];
      String str1 = Debug.getName(getContext(), i);
      if (findViewById(arrayOfInt[paramInt]) == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: ");
        stringBuilder.append(str);
        stringBuilder.append(" NO View matches id ");
        stringBuilder.append(str1);
        Log.w("MotionLayout", stringBuilder.toString());
      } 
      if (paramConstraintSet.getHeight(i) == -1) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: ");
        stringBuilder.append(str);
        stringBuilder.append("(");
        stringBuilder.append(str1);
        stringBuilder.append(") no LAYOUT_HEIGHT");
        Log.w("MotionLayout", stringBuilder.toString());
      } 
      if (paramConstraintSet.getWidth(i) == -1) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: ");
        stringBuilder.append(str);
        stringBuilder.append("(");
        stringBuilder.append(str1);
        stringBuilder.append(") no LAYOUT_HEIGHT");
        Log.w("MotionLayout", stringBuilder.toString());
      } 
    } 
  }
  
  private void checkStructure(MotionScene.Transition paramTransition) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("CHECK: transition = ");
    stringBuilder.append(paramTransition.debugString(getContext()));
    Log.v("MotionLayout", stringBuilder.toString());
    stringBuilder = new StringBuilder();
    stringBuilder.append("CHECK: transition.setDuration = ");
    stringBuilder.append(paramTransition.getDuration());
    Log.v("MotionLayout", stringBuilder.toString());
    if (paramTransition.getStartConstraintSetId() == paramTransition.getEndConstraintSetId())
      Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!"); 
  }
  
  private void computeCurrentPositions() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      MotionController motionController = this.mFrameArrayList.get(view);
      if (motionController != null)
        motionController.setStartCurrentState(view); 
    } 
  }
  
  private void debugPos() {
    for (byte b = 0; b < getChildCount(); b++) {
      View view = getChildAt(b);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(" ");
      stringBuilder.append(Debug.getLocation());
      stringBuilder.append(" ");
      stringBuilder.append(Debug.getName((View)this));
      stringBuilder.append(" ");
      stringBuilder.append(Debug.getName(getContext(), this.mCurrentState));
      stringBuilder.append(" ");
      stringBuilder.append(Debug.getName(view));
      stringBuilder.append(view.getLeft());
      stringBuilder.append(" ");
      stringBuilder.append(view.getTop());
      Log.v("MotionLayout", stringBuilder.toString());
    } 
  }
  
  private void evaluateLayout() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTransitionGoalPosition : F
    //   4: aload_0
    //   5: getfield mTransitionLastPosition : F
    //   8: fsub
    //   9: invokestatic signum : (F)F
    //   12: fstore_3
    //   13: aload_0
    //   14: invokevirtual getNanoTime : ()J
    //   17: lstore #7
    //   19: aload_0
    //   20: getfield mInterpolator : Landroid/view/animation/Interpolator;
    //   23: instanceof androidx/constraintlayout/motion/utils/StopLogic
    //   26: ifne -> 52
    //   29: lload #7
    //   31: aload_0
    //   32: getfield mTransitionLastTime : J
    //   35: lsub
    //   36: l2f
    //   37: fload_3
    //   38: fmul
    //   39: ldc_w 1.0E-9
    //   42: fmul
    //   43: aload_0
    //   44: getfield mTransitionDuration : F
    //   47: fdiv
    //   48: fstore_1
    //   49: goto -> 54
    //   52: fconst_0
    //   53: fstore_1
    //   54: aload_0
    //   55: getfield mTransitionLastPosition : F
    //   58: fload_1
    //   59: fadd
    //   60: fstore_2
    //   61: aload_0
    //   62: getfield mTransitionInstantly : Z
    //   65: ifeq -> 73
    //   68: aload_0
    //   69: getfield mTransitionGoalPosition : F
    //   72: fstore_2
    //   73: iconst_0
    //   74: istore #5
    //   76: fload_3
    //   77: fconst_0
    //   78: fcmpl
    //   79: istore #6
    //   81: iload #6
    //   83: ifle -> 95
    //   86: fload_2
    //   87: aload_0
    //   88: getfield mTransitionGoalPosition : F
    //   91: fcmpl
    //   92: ifge -> 110
    //   95: fload_3
    //   96: fconst_0
    //   97: fcmpg
    //   98: ifgt -> 121
    //   101: fload_2
    //   102: aload_0
    //   103: getfield mTransitionGoalPosition : F
    //   106: fcmpg
    //   107: ifgt -> 121
    //   110: aload_0
    //   111: getfield mTransitionGoalPosition : F
    //   114: fstore_2
    //   115: iconst_1
    //   116: istore #4
    //   118: goto -> 124
    //   121: iconst_0
    //   122: istore #4
    //   124: aload_0
    //   125: getfield mInterpolator : Landroid/view/animation/Interpolator;
    //   128: astore #9
    //   130: fload_2
    //   131: fstore_1
    //   132: aload #9
    //   134: ifnull -> 183
    //   137: fload_2
    //   138: fstore_1
    //   139: iload #4
    //   141: ifne -> 183
    //   144: aload_0
    //   145: getfield mTemporalInterpolator : Z
    //   148: ifeq -> 174
    //   151: aload #9
    //   153: lload #7
    //   155: aload_0
    //   156: getfield mAnimationStartTime : J
    //   159: lsub
    //   160: l2f
    //   161: ldc_w 1.0E-9
    //   164: fmul
    //   165: invokeinterface getInterpolation : (F)F
    //   170: fstore_1
    //   171: goto -> 183
    //   174: aload #9
    //   176: fload_2
    //   177: invokeinterface getInterpolation : (F)F
    //   182: fstore_1
    //   183: iload #6
    //   185: ifle -> 197
    //   188: fload_1
    //   189: aload_0
    //   190: getfield mTransitionGoalPosition : F
    //   193: fcmpl
    //   194: ifge -> 216
    //   197: fload_1
    //   198: fstore_2
    //   199: fload_3
    //   200: fconst_0
    //   201: fcmpg
    //   202: ifgt -> 221
    //   205: fload_1
    //   206: fstore_2
    //   207: fload_1
    //   208: aload_0
    //   209: getfield mTransitionGoalPosition : F
    //   212: fcmpg
    //   213: ifgt -> 221
    //   216: aload_0
    //   217: getfield mTransitionGoalPosition : F
    //   220: fstore_2
    //   221: aload_0
    //   222: fload_2
    //   223: putfield mPostInterpolationPosition : F
    //   226: aload_0
    //   227: invokevirtual getChildCount : ()I
    //   230: istore #6
    //   232: aload_0
    //   233: invokevirtual getNanoTime : ()J
    //   236: lstore #7
    //   238: iload #5
    //   240: istore #4
    //   242: iload #4
    //   244: iload #6
    //   246: if_icmpge -> 297
    //   249: aload_0
    //   250: iload #4
    //   252: invokevirtual getChildAt : (I)Landroid/view/View;
    //   255: astore #9
    //   257: aload_0
    //   258: getfield mFrameArrayList : Ljava/util/HashMap;
    //   261: aload #9
    //   263: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   266: checkcast androidx/constraintlayout/motion/widget/MotionController
    //   269: astore #10
    //   271: aload #10
    //   273: ifnull -> 291
    //   276: aload #10
    //   278: aload #9
    //   280: fload_2
    //   281: lload #7
    //   283: aload_0
    //   284: getfield mKeyCache : Landroidx/constraintlayout/motion/widget/KeyCache;
    //   287: invokevirtual interpolate : (Landroid/view/View;FJLandroidx/constraintlayout/motion/widget/KeyCache;)Z
    //   290: pop
    //   291: iinc #4, 1
    //   294: goto -> 242
    //   297: aload_0
    //   298: getfield mMeasureDuringTransition : Z
    //   301: ifeq -> 308
    //   304: aload_0
    //   305: invokevirtual requestLayout : ()V
    //   308: return
  }
  
  private void fireTransitionChange() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTransitionListener : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionListener;
    //   4: ifnonnull -> 23
    //   7: aload_0
    //   8: getfield mTransitionListeners : Ljava/util/ArrayList;
    //   11: astore_2
    //   12: aload_2
    //   13: ifnull -> 219
    //   16: aload_2
    //   17: invokevirtual isEmpty : ()Z
    //   20: ifne -> 219
    //   23: aload_0
    //   24: getfield mListenerPosition : F
    //   27: aload_0
    //   28: getfield mTransitionPosition : F
    //   31: fcmpl
    //   32: ifeq -> 219
    //   35: aload_0
    //   36: getfield mListenerState : I
    //   39: iconst_m1
    //   40: if_icmpeq -> 121
    //   43: aload_0
    //   44: getfield mTransitionListener : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionListener;
    //   47: astore_2
    //   48: aload_2
    //   49: ifnull -> 67
    //   52: aload_2
    //   53: aload_0
    //   54: aload_0
    //   55: getfield mBeginState : I
    //   58: aload_0
    //   59: getfield mEndState : I
    //   62: invokeinterface onTransitionStarted : (Landroidx/constraintlayout/motion/widget/MotionLayout;II)V
    //   67: aload_0
    //   68: getfield mTransitionListeners : Ljava/util/ArrayList;
    //   71: astore_2
    //   72: aload_2
    //   73: ifnull -> 116
    //   76: aload_2
    //   77: invokevirtual iterator : ()Ljava/util/Iterator;
    //   80: astore_2
    //   81: aload_2
    //   82: invokeinterface hasNext : ()Z
    //   87: ifeq -> 116
    //   90: aload_2
    //   91: invokeinterface next : ()Ljava/lang/Object;
    //   96: checkcast androidx/constraintlayout/motion/widget/MotionLayout$TransitionListener
    //   99: aload_0
    //   100: aload_0
    //   101: getfield mBeginState : I
    //   104: aload_0
    //   105: getfield mEndState : I
    //   108: invokeinterface onTransitionStarted : (Landroidx/constraintlayout/motion/widget/MotionLayout;II)V
    //   113: goto -> 81
    //   116: aload_0
    //   117: iconst_1
    //   118: putfield mIsAnimating : Z
    //   121: aload_0
    //   122: iconst_m1
    //   123: putfield mListenerState : I
    //   126: aload_0
    //   127: getfield mTransitionPosition : F
    //   130: fstore_1
    //   131: aload_0
    //   132: fload_1
    //   133: putfield mListenerPosition : F
    //   136: aload_0
    //   137: getfield mTransitionListener : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionListener;
    //   140: astore_2
    //   141: aload_2
    //   142: ifnull -> 161
    //   145: aload_2
    //   146: aload_0
    //   147: aload_0
    //   148: getfield mBeginState : I
    //   151: aload_0
    //   152: getfield mEndState : I
    //   155: fload_1
    //   156: invokeinterface onTransitionChange : (Landroidx/constraintlayout/motion/widget/MotionLayout;IIF)V
    //   161: aload_0
    //   162: getfield mTransitionListeners : Ljava/util/ArrayList;
    //   165: astore_2
    //   166: aload_2
    //   167: ifnull -> 214
    //   170: aload_2
    //   171: invokevirtual iterator : ()Ljava/util/Iterator;
    //   174: astore_2
    //   175: aload_2
    //   176: invokeinterface hasNext : ()Z
    //   181: ifeq -> 214
    //   184: aload_2
    //   185: invokeinterface next : ()Ljava/lang/Object;
    //   190: checkcast androidx/constraintlayout/motion/widget/MotionLayout$TransitionListener
    //   193: aload_0
    //   194: aload_0
    //   195: getfield mBeginState : I
    //   198: aload_0
    //   199: getfield mEndState : I
    //   202: aload_0
    //   203: getfield mTransitionPosition : F
    //   206: invokeinterface onTransitionChange : (Landroidx/constraintlayout/motion/widget/MotionLayout;IIF)V
    //   211: goto -> 175
    //   214: aload_0
    //   215: iconst_1
    //   216: putfield mIsAnimating : Z
    //   219: return
  }
  
  private void fireTransitionStarted(MotionLayout paramMotionLayout, int paramInt1, int paramInt2) {
    TransitionListener transitionListener = this.mTransitionListener;
    if (transitionListener != null)
      transitionListener.onTransitionStarted(this, paramInt1, paramInt2); 
    ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
    if (arrayList != null) {
      Iterator<TransitionListener> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((TransitionListener)iterator.next()).onTransitionStarted(paramMotionLayout, paramInt1, paramInt2); 
    } 
  }
  
  private boolean handlesTouchEvent(float paramFloat1, float paramFloat2, View paramView, MotionEvent paramMotionEvent) {
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      int i = viewGroup.getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = viewGroup.getChildAt(b);
        if (handlesTouchEvent(paramView.getLeft() + paramFloat1, paramView.getTop() + paramFloat2, view, paramMotionEvent))
          return true; 
      } 
    } 
    this.mBoundsCheck.set(paramView.getLeft() + paramFloat1, paramView.getTop() + paramFloat2, paramFloat1 + paramView.getRight(), paramFloat2 + paramView.getBottom());
    if (paramMotionEvent.getAction() == 0) {
      if (this.mBoundsCheck.contains(paramMotionEvent.getX(), paramMotionEvent.getY()) && paramView.onTouchEvent(paramMotionEvent))
        return true; 
    } else if (paramView.onTouchEvent(paramMotionEvent)) {
      return true;
    } 
    return false;
  }
  
  private void init(AttributeSet paramAttributeSet) {
    IS_IN_EDIT_MODE = isInEditMode();
    if (paramAttributeSet != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.MotionLayout);
      int i = typedArray.getIndexCount();
      byte b = 0;
      boolean bool;
      for (bool = true; b < i; bool = bool1) {
        boolean bool1;
        int j = typedArray.getIndex(b);
        if (j == R.styleable.MotionLayout_layoutDescription) {
          j = typedArray.getResourceId(j, -1);
          this.mScene = new MotionScene(getContext(), this, j);
          bool1 = bool;
        } else if (j == R.styleable.MotionLayout_currentState) {
          this.mCurrentState = typedArray.getResourceId(j, -1);
          bool1 = bool;
        } else if (j == R.styleable.MotionLayout_motionProgress) {
          this.mTransitionGoalPosition = typedArray.getFloat(j, 0.0F);
          this.mInTransition = true;
          bool1 = bool;
        } else if (j == R.styleable.MotionLayout_applyMotionScene) {
          bool1 = typedArray.getBoolean(j, bool);
        } else if (j == R.styleable.MotionLayout_showPaths) {
          bool1 = bool;
          if (this.mDebugPath == 0) {
            if (typedArray.getBoolean(j, false)) {
              j = 2;
            } else {
              j = 0;
            } 
            this.mDebugPath = j;
            bool1 = bool;
          } 
        } else {
          bool1 = bool;
          if (j == R.styleable.MotionLayout_motionDebug) {
            this.mDebugPath = typedArray.getInt(j, 0);
            bool1 = bool;
          } 
        } 
        b++;
      } 
      typedArray.recycle();
      if (this.mScene == null)
        Log.e("MotionLayout", "WARNING NO app:layoutDescription tag"); 
      if (!bool)
        this.mScene = null; 
    } 
    if (this.mDebugPath != 0)
      checkStructure(); 
    if (this.mCurrentState == -1) {
      MotionScene motionScene = this.mScene;
      if (motionScene != null) {
        this.mCurrentState = motionScene.getStartId();
        this.mBeginState = this.mScene.getStartId();
        this.mEndState = this.mScene.getEndId();
      } 
    } 
  }
  
  private void processTransitionCompleted() {
    if (this.mTransitionListener == null) {
      ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
      if (arrayList == null || arrayList.isEmpty())
        return; 
    } 
    this.mIsAnimating = false;
    for (Integer integer : this.mTransitionCompleted) {
      TransitionListener transitionListener = this.mTransitionListener;
      if (transitionListener != null)
        transitionListener.onTransitionCompleted(this, integer.intValue()); 
      ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
      if (arrayList != null) {
        Iterator<TransitionListener> iterator = arrayList.iterator();
        while (iterator.hasNext())
          ((TransitionListener)iterator.next()).onTransitionCompleted(this, integer.intValue()); 
      } 
    } 
    this.mTransitionCompleted.clear();
  }
  
  private void setupMotionViews() {
    int j = getChildCount();
    this.mModel.build();
    boolean bool2 = true;
    this.mInTransition = true;
    int k = getWidth();
    int i = getHeight();
    int m = this.mScene.gatPathMotionArc();
    byte b2 = 0;
    boolean bool1 = false;
    if (m != -1)
      for (byte b = 0; b < j; b++) {
        MotionController motionController = this.mFrameArrayList.get(getChildAt(b));
        if (motionController != null)
          motionController.setPathMotionArc(m); 
      }  
    byte b1;
    for (b1 = 0; b1 < j; b1++) {
      MotionController motionController = this.mFrameArrayList.get(getChildAt(b1));
      if (motionController != null) {
        this.mScene.getKeyFrames(motionController);
        motionController.setup(k, i, this.mTransitionDuration, getNanoTime());
      } 
    } 
    float f = this.mScene.getStaggered();
    if (f != 0.0F) {
      if (f < 0.0D) {
        b1 = 1;
      } else {
        b1 = 0;
      } 
      float f4 = Math.abs(f);
      float f2 = -3.4028235E38F;
      float f3 = Float.MAX_VALUE;
      i = 0;
      float f1 = Float.MAX_VALUE;
      f = -3.4028235E38F;
      while (true) {
        if (i < j) {
          MotionController motionController = this.mFrameArrayList.get(getChildAt(i));
          if (!Float.isNaN(motionController.mMotionStagger)) {
            i = bool2;
            break;
          } 
          float f5 = motionController.getFinalX();
          float f6 = motionController.getFinalY();
          if (b1 != 0) {
            f5 = f6 - f5;
          } else {
            f5 = f6 + f5;
          } 
          f1 = Math.min(f1, f5);
          f = Math.max(f, f5);
          i++;
          continue;
        } 
        i = 0;
        break;
      } 
      if (i != 0) {
        b2 = 0;
        f = f3;
        f1 = f2;
        while (true) {
          i = bool1;
          if (b2 < j) {
            MotionController motionController = this.mFrameArrayList.get(getChildAt(b2));
            f2 = f1;
            float f5 = f;
            if (!Float.isNaN(motionController.mMotionStagger)) {
              f5 = Math.min(f, motionController.mMotionStagger);
              f2 = Math.max(f1, motionController.mMotionStagger);
            } 
            b2++;
            f1 = f2;
            f = f5;
            continue;
          } 
          break;
        } 
        while (i < j) {
          MotionController motionController = this.mFrameArrayList.get(getChildAt(i));
          if (!Float.isNaN(motionController.mMotionStagger)) {
            motionController.mStaggerScale = 1.0F / (1.0F - f4);
            if (b1 != 0) {
              motionController.mStaggerOffset = f4 - (f1 - motionController.mMotionStagger) / (f1 - f) * f4;
            } else {
              motionController.mStaggerOffset = f4 - (motionController.mMotionStagger - f) * f4 / (f1 - f);
            } 
          } 
          i++;
        } 
      } else {
        while (b2 < j) {
          MotionController motionController = this.mFrameArrayList.get(getChildAt(b2));
          float f5 = motionController.getFinalX();
          f2 = motionController.getFinalY();
          if (b1 != 0) {
            f5 = f2 - f5;
          } else {
            f5 = f2 + f5;
          } 
          motionController.mStaggerScale = 1.0F / (1.0F - f4);
          motionController.mStaggerOffset = f4 - (f5 - f1) * f4 / (f - f1);
          b2++;
        } 
      } 
    } 
  }
  
  private static boolean willJump(float paramFloat1, float paramFloat2, float paramFloat3) {
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramFloat1 > 0.0F) {
      float f1 = paramFloat1 / paramFloat3;
      if (paramFloat2 + paramFloat1 * f1 - paramFloat3 * f1 * f1 / 2.0F > 1.0F) {
        bool1 = bool2;
      } else {
        bool1 = false;
      } 
      return bool1;
    } 
    float f = -paramFloat1 / paramFloat3;
    if (paramFloat2 + paramFloat1 * f + paramFloat3 * f * f / 2.0F >= 0.0F)
      bool1 = false; 
    return bool1;
  }
  
  public void addTransitionListener(TransitionListener paramTransitionListener) {
    if (this.mTransitionListeners == null)
      this.mTransitionListeners = new ArrayList<TransitionListener>(); 
    this.mTransitionListeners.add(paramTransitionListener);
  }
  
  void animateTo(float paramFloat) {
    if (this.mScene == null)
      return; 
    float f1 = this.mTransitionLastPosition;
    float f2 = this.mTransitionPosition;
    if (f1 != f2 && this.mTransitionInstantly)
      this.mTransitionLastPosition = f2; 
    f1 = this.mTransitionLastPosition;
    if (f1 == paramFloat)
      return; 
    this.mTemporalInterpolator = false;
    this.mTransitionGoalPosition = paramFloat;
    this.mTransitionDuration = this.mScene.getDuration() / 1000.0F;
    setProgress(this.mTransitionGoalPosition);
    this.mInterpolator = this.mScene.getInterpolator();
    this.mTransitionInstantly = false;
    this.mAnimationStartTime = getNanoTime();
    this.mInTransition = true;
    this.mTransitionPosition = f1;
    this.mTransitionLastPosition = f1;
    invalidate();
  }
  
  void disableAutoTransition(boolean paramBoolean) {
    MotionScene motionScene = this.mScene;
    if (motionScene == null)
      return; 
    motionScene.disableAutoTransition(paramBoolean);
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {
    evaluate(false);
    super.dispatchDraw(paramCanvas);
    if (this.mScene == null)
      return; 
    if ((this.mDebugPath & 0x1) == 1 && !isInEditMode()) {
      this.mFrames++;
      long l1 = getNanoTime();
      long l2 = this.mLastDrawTime;
      if (l2 != -1L) {
        l2 = l1 - l2;
        if (l2 > 200000000L) {
          this.mLastFps = (int)(this.mFrames / (float)l2 * 1.0E-9F * 100.0F) / 100.0F;
          this.mFrames = 0;
          this.mLastDrawTime = l1;
        } 
      } else {
        this.mLastDrawTime = l1;
      } 
      Paint paint = new Paint();
      paint.setTextSize(42.0F);
      float f = (int)(getProgress() * 1000.0F) / 10.0F;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(this.mLastFps);
      stringBuilder1.append(" fps ");
      stringBuilder1.append(Debug.getState(this, this.mBeginState));
      stringBuilder1.append(" -> ");
      String str = stringBuilder1.toString();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(Debug.getState(this, this.mEndState));
      stringBuilder2.append(" (progress: ");
      stringBuilder2.append(f);
      stringBuilder2.append(" ) state=");
      int i = this.mCurrentState;
      if (i == -1) {
        str = "undefined";
      } else {
        str = Debug.getState(this, i);
      } 
      stringBuilder2.append(str);
      str = stringBuilder2.toString();
      paint.setColor(-16777216);
      paramCanvas.drawText(str, 11.0F, (getHeight() - 29), paint);
      paint.setColor(-7864184);
      paramCanvas.drawText(str, 10.0F, (getHeight() - 30), paint);
    } 
    if (this.mDebugPath > 1) {
      if (this.mDevModeDraw == null)
        this.mDevModeDraw = new DevModeDraw(); 
      this.mDevModeDraw.draw(paramCanvas, this.mFrameArrayList, this.mScene.getDuration(), this.mDebugPath);
    } 
  }
  
  public void enableTransition(int paramInt, boolean paramBoolean) {
    MotionScene.Transition transition = getTransition(paramInt);
    if (paramBoolean) {
      transition.setEnable(true);
      return;
    } 
    if (transition == this.mScene.mCurrentTransition)
      for (MotionScene.Transition transition1 : this.mScene.getTransitionsWithState(this.mCurrentState)) {
        if (transition1.isEnabled()) {
          this.mScene.mCurrentTransition = transition1;
          break;
        } 
      }  
    transition.setEnable(false);
  }
  
  void evaluate(boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTransitionLastTime : J
    //   4: ldc2_w -1
    //   7: lcmp
    //   8: ifne -> 19
    //   11: aload_0
    //   12: aload_0
    //   13: invokevirtual getNanoTime : ()J
    //   16: putfield mTransitionLastTime : J
    //   19: aload_0
    //   20: getfield mTransitionLastPosition : F
    //   23: fstore_2
    //   24: fload_2
    //   25: fconst_0
    //   26: fcmpl
    //   27: ifle -> 41
    //   30: fload_2
    //   31: fconst_1
    //   32: fcmpg
    //   33: ifge -> 41
    //   36: aload_0
    //   37: iconst_m1
    //   38: putfield mCurrentState : I
    //   41: aload_0
    //   42: getfield mKeepAnimating : Z
    //   45: istore #17
    //   47: iconst_1
    //   48: istore #9
    //   50: iconst_1
    //   51: istore #10
    //   53: iconst_0
    //   54: istore #11
    //   56: iconst_0
    //   57: istore #8
    //   59: iload #17
    //   61: ifne -> 95
    //   64: iload #11
    //   66: istore #7
    //   68: aload_0
    //   69: getfield mInTransition : Z
    //   72: ifeq -> 998
    //   75: iload_1
    //   76: ifne -> 95
    //   79: iload #11
    //   81: istore #7
    //   83: aload_0
    //   84: getfield mTransitionGoalPosition : F
    //   87: aload_0
    //   88: getfield mTransitionLastPosition : F
    //   91: fcmpl
    //   92: ifeq -> 998
    //   95: aload_0
    //   96: getfield mTransitionGoalPosition : F
    //   99: aload_0
    //   100: getfield mTransitionLastPosition : F
    //   103: fsub
    //   104: invokestatic signum : (F)F
    //   107: fstore #5
    //   109: aload_0
    //   110: invokevirtual getNanoTime : ()J
    //   113: lstore #15
    //   115: aload_0
    //   116: getfield mInterpolator : Landroid/view/animation/Interpolator;
    //   119: instanceof androidx/constraintlayout/motion/widget/MotionInterpolator
    //   122: ifne -> 156
    //   125: lload #15
    //   127: aload_0
    //   128: getfield mTransitionLastTime : J
    //   131: lsub
    //   132: l2f
    //   133: fload #5
    //   135: fmul
    //   136: ldc_w 1.0E-9
    //   139: fmul
    //   140: aload_0
    //   141: getfield mTransitionDuration : F
    //   144: fdiv
    //   145: fstore #4
    //   147: aload_0
    //   148: fload #4
    //   150: putfield mLastVelocity : F
    //   153: goto -> 159
    //   156: fconst_0
    //   157: fstore #4
    //   159: aload_0
    //   160: getfield mTransitionLastPosition : F
    //   163: fload #4
    //   165: fadd
    //   166: fstore_3
    //   167: aload_0
    //   168: getfield mTransitionInstantly : Z
    //   171: ifeq -> 179
    //   174: aload_0
    //   175: getfield mTransitionGoalPosition : F
    //   178: fstore_3
    //   179: fload #5
    //   181: fconst_0
    //   182: fcmpl
    //   183: istore #11
    //   185: iload #11
    //   187: ifle -> 199
    //   190: fload_3
    //   191: aload_0
    //   192: getfield mTransitionGoalPosition : F
    //   195: fcmpl
    //   196: ifge -> 215
    //   199: fload #5
    //   201: fconst_0
    //   202: fcmpg
    //   203: ifgt -> 231
    //   206: fload_3
    //   207: aload_0
    //   208: getfield mTransitionGoalPosition : F
    //   211: fcmpg
    //   212: ifgt -> 231
    //   215: aload_0
    //   216: getfield mTransitionGoalPosition : F
    //   219: fstore_3
    //   220: aload_0
    //   221: iconst_0
    //   222: putfield mInTransition : Z
    //   225: iconst_1
    //   226: istore #7
    //   228: goto -> 234
    //   231: iconst_0
    //   232: istore #7
    //   234: aload_0
    //   235: fload_3
    //   236: putfield mTransitionLastPosition : F
    //   239: aload_0
    //   240: fload_3
    //   241: putfield mTransitionPosition : F
    //   244: aload_0
    //   245: lload #15
    //   247: putfield mTransitionLastTime : J
    //   250: aload_0
    //   251: getfield mInterpolator : Landroid/view/animation/Interpolator;
    //   254: astore #18
    //   256: fload_3
    //   257: fstore_2
    //   258: aload #18
    //   260: ifnull -> 489
    //   263: fload_3
    //   264: fstore_2
    //   265: iload #7
    //   267: ifne -> 489
    //   270: aload_0
    //   271: getfield mTemporalInterpolator : Z
    //   274: ifeq -> 428
    //   277: aload #18
    //   279: lload #15
    //   281: aload_0
    //   282: getfield mAnimationStartTime : J
    //   285: lsub
    //   286: l2f
    //   287: ldc_w 1.0E-9
    //   290: fmul
    //   291: invokeinterface getInterpolation : (F)F
    //   296: fstore #4
    //   298: aload_0
    //   299: fload #4
    //   301: putfield mTransitionLastPosition : F
    //   304: aload_0
    //   305: lload #15
    //   307: putfield mTransitionLastTime : J
    //   310: aload_0
    //   311: getfield mInterpolator : Landroid/view/animation/Interpolator;
    //   314: astore #18
    //   316: fload #4
    //   318: fstore_2
    //   319: aload #18
    //   321: instanceof androidx/constraintlayout/motion/widget/MotionInterpolator
    //   324: ifeq -> 489
    //   327: aload #18
    //   329: checkcast androidx/constraintlayout/motion/widget/MotionInterpolator
    //   332: invokevirtual getVelocity : ()F
    //   335: fstore #6
    //   337: aload_0
    //   338: fload #6
    //   340: putfield mLastVelocity : F
    //   343: fload #6
    //   345: invokestatic abs : (F)F
    //   348: aload_0
    //   349: getfield mTransitionDuration : F
    //   352: fmul
    //   353: ldc 1.0E-5
    //   355: fcmpg
    //   356: ifgt -> 364
    //   359: aload_0
    //   360: iconst_0
    //   361: putfield mInTransition : Z
    //   364: fload #4
    //   366: fstore_3
    //   367: fload #6
    //   369: fconst_0
    //   370: fcmpl
    //   371: ifle -> 396
    //   374: fload #4
    //   376: fstore_3
    //   377: fload #4
    //   379: fconst_1
    //   380: fcmpl
    //   381: iflt -> 396
    //   384: aload_0
    //   385: fconst_1
    //   386: putfield mTransitionLastPosition : F
    //   389: aload_0
    //   390: iconst_0
    //   391: putfield mInTransition : Z
    //   394: fconst_1
    //   395: fstore_3
    //   396: fload_3
    //   397: fstore_2
    //   398: fload #6
    //   400: fconst_0
    //   401: fcmpg
    //   402: ifge -> 489
    //   405: fload_3
    //   406: fstore_2
    //   407: fload_3
    //   408: fconst_0
    //   409: fcmpg
    //   410: ifgt -> 489
    //   413: aload_0
    //   414: fconst_0
    //   415: putfield mTransitionLastPosition : F
    //   418: aload_0
    //   419: iconst_0
    //   420: putfield mInTransition : Z
    //   423: fconst_0
    //   424: fstore_2
    //   425: goto -> 489
    //   428: aload #18
    //   430: fload_3
    //   431: invokeinterface getInterpolation : (F)F
    //   436: fstore_2
    //   437: aload_0
    //   438: getfield mInterpolator : Landroid/view/animation/Interpolator;
    //   441: astore #18
    //   443: aload #18
    //   445: instanceof androidx/constraintlayout/motion/widget/MotionInterpolator
    //   448: ifeq -> 466
    //   451: aload_0
    //   452: aload #18
    //   454: checkcast androidx/constraintlayout/motion/widget/MotionInterpolator
    //   457: invokevirtual getVelocity : ()F
    //   460: putfield mLastVelocity : F
    //   463: goto -> 489
    //   466: aload_0
    //   467: aload #18
    //   469: fload_3
    //   470: fload #4
    //   472: fadd
    //   473: invokeinterface getInterpolation : (F)F
    //   478: fload_2
    //   479: fsub
    //   480: fload #5
    //   482: fmul
    //   483: fload #4
    //   485: fdiv
    //   486: putfield mLastVelocity : F
    //   489: aload_0
    //   490: getfield mLastVelocity : F
    //   493: invokestatic abs : (F)F
    //   496: ldc 1.0E-5
    //   498: fcmpl
    //   499: ifle -> 509
    //   502: aload_0
    //   503: getstatic androidx/constraintlayout/motion/widget/MotionLayout$TransitionState.MOVING : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;
    //   506: invokevirtual setState : (Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;)V
    //   509: iload #11
    //   511: ifle -> 523
    //   514: fload_2
    //   515: aload_0
    //   516: getfield mTransitionGoalPosition : F
    //   519: fcmpl
    //   520: ifge -> 543
    //   523: fload_2
    //   524: fstore_3
    //   525: fload #5
    //   527: fconst_0
    //   528: fcmpg
    //   529: ifgt -> 553
    //   532: fload_2
    //   533: fstore_3
    //   534: fload_2
    //   535: aload_0
    //   536: getfield mTransitionGoalPosition : F
    //   539: fcmpg
    //   540: ifgt -> 553
    //   543: aload_0
    //   544: getfield mTransitionGoalPosition : F
    //   547: fstore_3
    //   548: aload_0
    //   549: iconst_0
    //   550: putfield mInTransition : Z
    //   553: fload_3
    //   554: fconst_1
    //   555: fcmpl
    //   556: istore #12
    //   558: iload #12
    //   560: ifge -> 569
    //   563: fload_3
    //   564: fconst_0
    //   565: fcmpg
    //   566: ifgt -> 581
    //   569: aload_0
    //   570: iconst_0
    //   571: putfield mInTransition : Z
    //   574: aload_0
    //   575: getstatic androidx/constraintlayout/motion/widget/MotionLayout$TransitionState.FINISHED : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;
    //   578: invokevirtual setState : (Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;)V
    //   581: aload_0
    //   582: invokevirtual getChildCount : ()I
    //   585: istore #13
    //   587: aload_0
    //   588: iconst_0
    //   589: putfield mKeepAnimating : Z
    //   592: aload_0
    //   593: invokevirtual getNanoTime : ()J
    //   596: lstore #15
    //   598: aload_0
    //   599: fload_3
    //   600: putfield mPostInterpolationPosition : F
    //   603: iconst_0
    //   604: istore #7
    //   606: iload #7
    //   608: iload #13
    //   610: if_icmpge -> 671
    //   613: aload_0
    //   614: iload #7
    //   616: invokevirtual getChildAt : (I)Landroid/view/View;
    //   619: astore #19
    //   621: aload_0
    //   622: getfield mFrameArrayList : Ljava/util/HashMap;
    //   625: aload #19
    //   627: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   630: checkcast androidx/constraintlayout/motion/widget/MotionController
    //   633: astore #18
    //   635: aload #18
    //   637: ifnull -> 665
    //   640: aload_0
    //   641: getfield mKeepAnimating : Z
    //   644: istore_1
    //   645: aload_0
    //   646: aload #18
    //   648: aload #19
    //   650: fload_3
    //   651: lload #15
    //   653: aload_0
    //   654: getfield mKeyCache : Landroidx/constraintlayout/motion/widget/KeyCache;
    //   657: invokevirtual interpolate : (Landroid/view/View;FJLandroidx/constraintlayout/motion/widget/KeyCache;)Z
    //   660: iload_1
    //   661: ior
    //   662: putfield mKeepAnimating : Z
    //   665: iinc #7, 1
    //   668: goto -> 606
    //   671: iload #11
    //   673: ifle -> 685
    //   676: fload_3
    //   677: aload_0
    //   678: getfield mTransitionGoalPosition : F
    //   681: fcmpl
    //   682: ifge -> 701
    //   685: fload #5
    //   687: fconst_0
    //   688: fcmpg
    //   689: ifgt -> 707
    //   692: fload_3
    //   693: aload_0
    //   694: getfield mTransitionGoalPosition : F
    //   697: fcmpg
    //   698: ifgt -> 707
    //   701: iconst_1
    //   702: istore #7
    //   704: goto -> 710
    //   707: iconst_0
    //   708: istore #7
    //   710: aload_0
    //   711: getfield mKeepAnimating : Z
    //   714: ifne -> 736
    //   717: aload_0
    //   718: getfield mInTransition : Z
    //   721: ifne -> 736
    //   724: iload #7
    //   726: ifeq -> 736
    //   729: aload_0
    //   730: getstatic androidx/constraintlayout/motion/widget/MotionLayout$TransitionState.FINISHED : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;
    //   733: invokevirtual setState : (Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;)V
    //   736: aload_0
    //   737: getfield mMeasureDuringTransition : Z
    //   740: ifeq -> 747
    //   743: aload_0
    //   744: invokevirtual requestLayout : ()V
    //   747: aload_0
    //   748: iload #7
    //   750: iconst_1
    //   751: ixor
    //   752: aload_0
    //   753: getfield mKeepAnimating : Z
    //   756: ior
    //   757: putfield mKeepAnimating : Z
    //   760: iload #8
    //   762: istore #7
    //   764: fload_3
    //   765: fconst_0
    //   766: fcmpg
    //   767: ifgt -> 828
    //   770: aload_0
    //   771: getfield mBeginState : I
    //   774: istore #13
    //   776: iload #8
    //   778: istore #7
    //   780: iload #13
    //   782: iconst_m1
    //   783: if_icmpeq -> 828
    //   786: iload #8
    //   788: istore #7
    //   790: aload_0
    //   791: getfield mCurrentState : I
    //   794: iload #13
    //   796: if_icmpeq -> 828
    //   799: aload_0
    //   800: iload #13
    //   802: putfield mCurrentState : I
    //   805: aload_0
    //   806: getfield mScene : Landroidx/constraintlayout/motion/widget/MotionScene;
    //   809: iload #13
    //   811: invokevirtual getConstraintSet : (I)Landroidx/constraintlayout/widget/ConstraintSet;
    //   814: aload_0
    //   815: invokevirtual applyCustomAttributes : (Landroidx/constraintlayout/widget/ConstraintLayout;)V
    //   818: aload_0
    //   819: getstatic androidx/constraintlayout/motion/widget/MotionLayout$TransitionState.FINISHED : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;
    //   822: invokevirtual setState : (Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;)V
    //   825: iconst_1
    //   826: istore #7
    //   828: iload #7
    //   830: istore #8
    //   832: fload_3
    //   833: f2d
    //   834: dconst_1
    //   835: dcmpl
    //   836: iflt -> 891
    //   839: aload_0
    //   840: getfield mCurrentState : I
    //   843: istore #13
    //   845: aload_0
    //   846: getfield mEndState : I
    //   849: istore #14
    //   851: iload #7
    //   853: istore #8
    //   855: iload #13
    //   857: iload #14
    //   859: if_icmpeq -> 891
    //   862: aload_0
    //   863: iload #14
    //   865: putfield mCurrentState : I
    //   868: aload_0
    //   869: getfield mScene : Landroidx/constraintlayout/motion/widget/MotionScene;
    //   872: iload #14
    //   874: invokevirtual getConstraintSet : (I)Landroidx/constraintlayout/widget/ConstraintSet;
    //   877: aload_0
    //   878: invokevirtual applyCustomAttributes : (Landroidx/constraintlayout/widget/ConstraintLayout;)V
    //   881: aload_0
    //   882: getstatic androidx/constraintlayout/motion/widget/MotionLayout$TransitionState.FINISHED : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;
    //   885: invokevirtual setState : (Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;)V
    //   888: iconst_1
    //   889: istore #8
    //   891: aload_0
    //   892: getfield mKeepAnimating : Z
    //   895: ifne -> 941
    //   898: aload_0
    //   899: getfield mInTransition : Z
    //   902: ifeq -> 908
    //   905: goto -> 941
    //   908: iload #11
    //   910: ifle -> 918
    //   913: iload #12
    //   915: ifeq -> 931
    //   918: fload #5
    //   920: fconst_0
    //   921: fcmpg
    //   922: ifge -> 945
    //   925: fload_3
    //   926: fconst_0
    //   927: fcmpl
    //   928: ifne -> 945
    //   931: aload_0
    //   932: getstatic androidx/constraintlayout/motion/widget/MotionLayout$TransitionState.FINISHED : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;
    //   935: invokevirtual setState : (Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionState;)V
    //   938: goto -> 945
    //   941: aload_0
    //   942: invokevirtual invalidate : ()V
    //   945: aload_0
    //   946: getfield mKeepAnimating : Z
    //   949: ifne -> 969
    //   952: aload_0
    //   953: getfield mInTransition : Z
    //   956: ifeq -> 969
    //   959: iload #11
    //   961: ifle -> 969
    //   964: iload #12
    //   966: ifeq -> 990
    //   969: iload #8
    //   971: istore #7
    //   973: fload #5
    //   975: fconst_0
    //   976: fcmpg
    //   977: ifge -> 998
    //   980: iload #8
    //   982: istore #7
    //   984: fload_3
    //   985: fconst_0
    //   986: fcmpl
    //   987: ifne -> 998
    //   990: aload_0
    //   991: invokevirtual onNewStateAttachHandlers : ()V
    //   994: iload #8
    //   996: istore #7
    //   998: aload_0
    //   999: getfield mTransitionLastPosition : F
    //   1002: fstore_2
    //   1003: fload_2
    //   1004: fconst_1
    //   1005: fcmpl
    //   1006: iflt -> 1042
    //   1009: aload_0
    //   1010: getfield mCurrentState : I
    //   1013: aload_0
    //   1014: getfield mEndState : I
    //   1017: if_icmpeq -> 1027
    //   1020: iload #10
    //   1022: istore #7
    //   1024: goto -> 1027
    //   1027: aload_0
    //   1028: aload_0
    //   1029: getfield mEndState : I
    //   1032: putfield mCurrentState : I
    //   1035: iload #7
    //   1037: istore #8
    //   1039: goto -> 1081
    //   1042: iload #7
    //   1044: istore #8
    //   1046: fload_2
    //   1047: fconst_0
    //   1048: fcmpg
    //   1049: ifgt -> 1081
    //   1052: aload_0
    //   1053: getfield mCurrentState : I
    //   1056: aload_0
    //   1057: getfield mBeginState : I
    //   1060: if_icmpeq -> 1070
    //   1063: iload #9
    //   1065: istore #7
    //   1067: goto -> 1070
    //   1070: aload_0
    //   1071: aload_0
    //   1072: getfield mBeginState : I
    //   1075: putfield mCurrentState : I
    //   1078: goto -> 1035
    //   1081: aload_0
    //   1082: aload_0
    //   1083: getfield mNeedsFireTransitionCompleted : Z
    //   1086: iload #8
    //   1088: ior
    //   1089: putfield mNeedsFireTransitionCompleted : Z
    //   1092: iload #8
    //   1094: ifeq -> 1108
    //   1097: aload_0
    //   1098: getfield mInLayout : Z
    //   1101: ifne -> 1108
    //   1104: aload_0
    //   1105: invokevirtual requestLayout : ()V
    //   1108: aload_0
    //   1109: aload_0
    //   1110: getfield mTransitionLastPosition : F
    //   1113: putfield mTransitionPosition : F
    //   1116: return
  }
  
  protected void fireTransitionCompleted() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTransitionListener : Landroidx/constraintlayout/motion/widget/MotionLayout$TransitionListener;
    //   4: ifnonnull -> 23
    //   7: aload_0
    //   8: getfield mTransitionListeners : Ljava/util/ArrayList;
    //   11: astore_3
    //   12: aload_3
    //   13: ifnull -> 103
    //   16: aload_3
    //   17: invokevirtual isEmpty : ()Z
    //   20: ifne -> 103
    //   23: aload_0
    //   24: getfield mListenerState : I
    //   27: iconst_m1
    //   28: if_icmpne -> 103
    //   31: aload_0
    //   32: aload_0
    //   33: getfield mCurrentState : I
    //   36: putfield mListenerState : I
    //   39: aload_0
    //   40: getfield mTransitionCompleted : Ljava/util/ArrayList;
    //   43: invokevirtual isEmpty : ()Z
    //   46: ifne -> 74
    //   49: aload_0
    //   50: getfield mTransitionCompleted : Ljava/util/ArrayList;
    //   53: astore_3
    //   54: aload_3
    //   55: aload_3
    //   56: invokevirtual size : ()I
    //   59: iconst_1
    //   60: isub
    //   61: invokevirtual get : (I)Ljava/lang/Object;
    //   64: checkcast java/lang/Integer
    //   67: invokevirtual intValue : ()I
    //   70: istore_1
    //   71: goto -> 76
    //   74: iconst_m1
    //   75: istore_1
    //   76: aload_0
    //   77: getfield mCurrentState : I
    //   80: istore_2
    //   81: iload_1
    //   82: iload_2
    //   83: if_icmpeq -> 103
    //   86: iload_2
    //   87: iconst_m1
    //   88: if_icmpeq -> 103
    //   91: aload_0
    //   92: getfield mTransitionCompleted : Ljava/util/ArrayList;
    //   95: iload_2
    //   96: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   99: invokevirtual add : (Ljava/lang/Object;)Z
    //   102: pop
    //   103: aload_0
    //   104: invokespecial processTransitionCompleted : ()V
    //   107: return
  }
  
  public void fireTrigger(int paramInt, boolean paramBoolean, float paramFloat) {
    TransitionListener transitionListener = this.mTransitionListener;
    if (transitionListener != null)
      transitionListener.onTransitionTrigger(this, paramInt, paramBoolean, paramFloat); 
    ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
    if (arrayList != null) {
      Iterator<TransitionListener> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((TransitionListener)iterator.next()).onTransitionTrigger(this, paramInt, paramBoolean, paramFloat); 
    } 
  }
  
  void getAnchorDpDt(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOffloat) {
    HashMap<View, MotionController> hashMap = this.mFrameArrayList;
    View view = getViewById(paramInt);
    MotionController motionController = hashMap.get(view);
    if (motionController != null) {
      motionController.getDpDt(paramFloat1, paramFloat2, paramFloat3, paramArrayOffloat);
      paramFloat3 = view.getY();
      paramFloat2 = this.lastPos;
      this.lastPos = paramFloat1;
      this.lastY = paramFloat3;
    } else {
      String str;
      if (view == null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("");
        stringBuilder1.append(paramInt);
        str = stringBuilder1.toString();
      } else {
        str = view.getContext().getResources().getResourceName(paramInt);
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("WARNING could not find view id ");
      stringBuilder.append(str);
      Log.w("MotionLayout", stringBuilder.toString());
    } 
  }
  
  public ConstraintSet getConstraintSet(int paramInt) {
    MotionScene motionScene = this.mScene;
    return (motionScene == null) ? null : motionScene.getConstraintSet(paramInt);
  }
  
  public int[] getConstraintSetIds() {
    MotionScene motionScene = this.mScene;
    return (motionScene == null) ? null : motionScene.getConstraintSetIds();
  }
  
  String getConstraintSetNames(int paramInt) {
    MotionScene motionScene = this.mScene;
    return (motionScene == null) ? null : motionScene.lookUpConstraintName(paramInt);
  }
  
  public int getCurrentState() {
    return this.mCurrentState;
  }
  
  public void getDebugMode(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = true;
    } 
    this.mDebugPath = bool;
    invalidate();
  }
  
  public ArrayList<MotionScene.Transition> getDefinedTransitions() {
    MotionScene motionScene = this.mScene;
    return (motionScene == null) ? null : motionScene.getDefinedTransitions();
  }
  
  public DesignTool getDesignTool() {
    if (this.mDesignTool == null)
      this.mDesignTool = new DesignTool(this); 
    return this.mDesignTool;
  }
  
  public int getEndState() {
    return this.mEndState;
  }
  
  protected long getNanoTime() {
    return System.nanoTime();
  }
  
  public float getProgress() {
    return this.mTransitionLastPosition;
  }
  
  public int getStartState() {
    return this.mBeginState;
  }
  
  public float getTargetPosition() {
    return this.mTransitionGoalPosition;
  }
  
  public MotionScene.Transition getTransition(int paramInt) {
    return this.mScene.getTransitionById(paramInt);
  }
  
  public Bundle getTransitionState() {
    if (this.mStateCache == null)
      this.mStateCache = new StateCache(); 
    this.mStateCache.recordState();
    return this.mStateCache.getTransitionState();
  }
  
  public long getTransitionTimeMs() {
    MotionScene motionScene = this.mScene;
    if (motionScene != null)
      this.mTransitionDuration = motionScene.getDuration() / 1000.0F; 
    return (long)(this.mTransitionDuration * 1000.0F);
  }
  
  public float getVelocity() {
    return this.mLastVelocity;
  }
  
  public void getViewVelocity(View paramView, float paramFloat1, float paramFloat2, float[] paramArrayOffloat, int paramInt) {
    float f1 = this.mLastVelocity;
    float f2 = this.mTransitionLastPosition;
    if (this.mInterpolator != null) {
      float f = Math.signum(this.mTransitionGoalPosition - f2);
      f1 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition + 1.0E-5F);
      f2 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition);
      f1 = f * (f1 - f2) / 1.0E-5F / this.mTransitionDuration;
    } 
    Interpolator interpolator = this.mInterpolator;
    if (interpolator instanceof MotionInterpolator)
      f1 = ((MotionInterpolator)interpolator).getVelocity(); 
    MotionController motionController = this.mFrameArrayList.get(paramView);
    if ((paramInt & 0x1) == 0) {
      motionController.getPostLayoutDvDp(f2, paramView.getWidth(), paramView.getHeight(), paramFloat1, paramFloat2, paramArrayOffloat);
    } else {
      motionController.getDpDt(f2, paramFloat1, paramFloat2, paramArrayOffloat);
    } 
    if (paramInt < 2) {
      paramArrayOffloat[0] = paramArrayOffloat[0] * f1;
      paramArrayOffloat[1] = paramArrayOffloat[1] * f1;
    } 
  }
  
  public boolean isAttachedToWindow() {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 19)
      return super.isAttachedToWindow(); 
    if (getWindowToken() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isInteractionEnabled() {
    return this.mInteractionEnabled;
  }
  
  public void loadLayoutDescription(int paramInt) {
    if (paramInt != 0) {
      try {
        MotionScene motionScene = new MotionScene();
        this(getContext(), this, paramInt);
        this.mScene = motionScene;
        if (Build.VERSION.SDK_INT < 19 || isAttachedToWindow()) {
          this.mScene.readFallback(this);
          this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
          rebuildScene();
          this.mScene.setRtl(isRtl());
        } 
      } catch (Exception exception) {
        throw new IllegalArgumentException("unable to parse MotionScene file", exception);
      } 
    } else {
      this.mScene = null;
    } 
  }
  
  int lookUpConstraintId(String paramString) {
    MotionScene motionScene = this.mScene;
    return (motionScene == null) ? 0 : motionScene.lookUpConstraintId(paramString);
  }
  
  protected MotionTracker obtainVelocityTracker() {
    return MyTracker.obtain();
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    MotionScene motionScene = this.mScene;
    if (motionScene != null) {
      int i = this.mCurrentState;
      if (i != -1) {
        ConstraintSet constraintSet = motionScene.getConstraintSet(i);
        this.mScene.readFallback(this);
        if (constraintSet != null)
          constraintSet.applyTo(this); 
        this.mBeginState = this.mCurrentState;
      } 
    } 
    onNewStateAttachHandlers();
    StateCache stateCache = this.mStateCache;
    if (stateCache != null) {
      stateCache.apply();
    } else {
      MotionScene motionScene1 = this.mScene;
      if (motionScene1 != null && motionScene1.mCurrentTransition != null && this.mScene.mCurrentTransition.getAutoTransition() == 4) {
        transitionToEnd();
        setState(TransitionState.SETUP);
        setState(TransitionState.MOVING);
      } 
    } 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    MotionScene motionScene = this.mScene;
    if (motionScene != null && this.mInteractionEnabled) {
      MotionScene.Transition transition = motionScene.mCurrentTransition;
      if (transition != null && transition.isEnabled()) {
        TouchResponse touchResponse = transition.getTouchResponse();
        if (touchResponse != null) {
          if (paramMotionEvent.getAction() == 0) {
            RectF rectF = touchResponse.getTouchRegion((ViewGroup)this, new RectF());
            if (rectF != null && !rectF.contains(paramMotionEvent.getX(), paramMotionEvent.getY()))
              return false; 
          } 
          int i = touchResponse.getTouchRegionId();
          if (i != -1) {
            View view = this.mRegionView;
            if (view == null || view.getId() != i)
              this.mRegionView = findViewById(i); 
            view = this.mRegionView;
            if (view != null) {
              this.mBoundsCheck.set(view.getLeft(), this.mRegionView.getTop(), this.mRegionView.getRight(), this.mRegionView.getBottom());
              if (this.mBoundsCheck.contains(paramMotionEvent.getX(), paramMotionEvent.getY()) && !handlesTouchEvent(0.0F, 0.0F, this.mRegionView, paramMotionEvent))
                return onTouchEvent(paramMotionEvent); 
            } 
          } 
        } 
      } 
    } 
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mInLayout = true;
    try {
      if (this.mScene == null) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      } 
      paramInt1 = paramInt3 - paramInt1;
      paramInt2 = paramInt4 - paramInt2;
      if (this.mLastLayoutWidth != paramInt1 || this.mLastLayoutHeight != paramInt2) {
        rebuildScene();
        evaluate(true);
      } 
      this.mLastLayoutWidth = paramInt1;
      this.mLastLayoutHeight = paramInt2;
      this.mOldWidth = paramInt1;
      this.mOldHeight = paramInt2;
      return;
    } finally {
      this.mInLayout = false;
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    if (this.mScene == null) {
      super.onMeasure(paramInt1, paramInt2);
      return;
    } 
    int i = this.mLastWidthMeasureSpec;
    int j = 0;
    if (i != paramInt1 || this.mLastHeightMeasureSpec != paramInt2) {
      i = 1;
    } else {
      i = 0;
    } 
    if (this.mNeedsFireTransitionCompleted) {
      this.mNeedsFireTransitionCompleted = false;
      onNewStateAttachHandlers();
      processTransitionCompleted();
      i = 1;
    } 
    if (this.mDirtyHierarchy)
      i = 1; 
    this.mLastWidthMeasureSpec = paramInt1;
    this.mLastHeightMeasureSpec = paramInt2;
    int m = this.mScene.getStartId();
    int k = this.mScene.getEndId();
    if ((i != 0 || this.mModel.isNotConfiguredWith(m, k)) && this.mBeginState != -1) {
      super.onMeasure(paramInt1, paramInt2);
      this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(m), this.mScene.getConstraintSet(k));
      this.mModel.reEvaluateState();
      this.mModel.setMeasuredId(m, k);
      paramInt1 = j;
    } else {
      paramInt1 = 1;
    } 
    if (this.mMeasureDuringTransition || paramInt1 != 0) {
      i = getPaddingTop();
      paramInt2 = getPaddingBottom();
      paramInt1 = getPaddingLeft();
      j = getPaddingRight();
      paramInt1 = this.mLayoutWidget.getWidth() + paramInt1 + j;
      paramInt2 = this.mLayoutWidget.getHeight() + i + paramInt2;
      i = this.mWidthMeasureMode;
      if (i == Integer.MIN_VALUE || i == 0) {
        paramInt1 = this.mStartWrapWidth;
        paramInt1 = (int)(paramInt1 + this.mPostInterpolationPosition * (this.mEndWrapWidth - paramInt1));
        requestLayout();
      } 
      i = this.mHeightMeasureMode;
      if (i == Integer.MIN_VALUE || i == 0) {
        paramInt2 = this.mStartWrapHeight;
        paramInt2 = (int)(paramInt2 + this.mPostInterpolationPosition * (this.mEndWrapHeight - paramInt2));
        requestLayout();
      } 
      setMeasuredDimension(paramInt1, paramInt2);
    } 
    evaluateLayout();
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return false;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2) {
    return false;
  }
  
  public void onNestedPreScroll(final View target, int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
    MotionScene motionScene = this.mScene;
    if (motionScene != null && motionScene.mCurrentTransition != null) {
      if (!this.mScene.mCurrentTransition.isEnabled())
        return; 
      MotionScene.Transition transition = this.mScene.mCurrentTransition;
      if (transition != null && transition.isEnabled()) {
        TouchResponse touchResponse = transition.getTouchResponse();
        if (touchResponse != null) {
          paramInt3 = touchResponse.getTouchRegionId();
          if (paramInt3 != -1 && target.getId() != paramInt3)
            return; 
        } 
      } 
      MotionScene motionScene1 = this.mScene;
      if (motionScene1 != null && motionScene1.getMoveWhenScrollAtTop()) {
        float f = this.mTransitionPosition;
        if ((f == 1.0F || f == 0.0F) && target.canScrollVertically(-1))
          return; 
      } 
      if (transition.getTouchResponse() != null && (this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 0x1) != 0) {
        float f = this.mScene.getProgressDirection(paramInt1, paramInt2);
        if ((this.mTransitionLastPosition <= 0.0F && f < 0.0F) || (this.mTransitionLastPosition >= 1.0F && f > 0.0F)) {
          if (Build.VERSION.SDK_INT >= 21) {
            target.setNestedScrollingEnabled(false);
            target.post(new Runnable() {
                  final MotionLayout this$0;
                  
                  final View val$target;
                  
                  public void run() {
                    target.setNestedScrollingEnabled(true);
                  }
                });
          } 
          return;
        } 
      } 
      float f2 = this.mTransitionPosition;
      long l = getNanoTime();
      float f1 = paramInt1;
      this.mScrollTargetDX = f1;
      float f3 = paramInt2;
      this.mScrollTargetDY = f3;
      this.mScrollTargetDT = (float)((l - this.mScrollTargetTime) * 1.0E-9D);
      this.mScrollTargetTime = l;
      this.mScene.processScrollMove(f1, f3);
      if (f2 != this.mTransitionPosition) {
        paramArrayOfint[0] = paramInt1;
        paramArrayOfint[1] = paramInt2;
      } 
      evaluate(false);
      if (paramArrayOfint[0] != 0 || paramArrayOfint[1] != 0)
        this.mUndergoingMotion = true; 
    } 
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
    if (this.mUndergoingMotion || paramInt1 != 0 || paramInt2 != 0) {
      paramArrayOfint[0] = paramArrayOfint[0] + paramInt3;
      paramArrayOfint[1] = paramArrayOfint[1] + paramInt4;
    } 
    this.mUndergoingMotion = false;
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2) {}
  
  void onNewStateAttachHandlers() {
    MotionScene motionScene = this.mScene;
    if (motionScene == null)
      return; 
    if (motionScene.autoTransition(this, this.mCurrentState)) {
      requestLayout();
      return;
    } 
    int i = this.mCurrentState;
    if (i != -1)
      this.mScene.addOnClickListeners(this, i); 
    if (this.mScene.supportTouch())
      this.mScene.setupTouch(); 
  }
  
  public void onRtlPropertiesChanged(int paramInt) {
    MotionScene motionScene = this.mScene;
    if (motionScene != null)
      motionScene.setRtl(isRtl()); 
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2) {
    MotionScene motionScene = this.mScene;
    return !(motionScene == null || motionScene.mCurrentTransition == null || this.mScene.mCurrentTransition.getTouchResponse() == null || (this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 0x2) != 0);
  }
  
  public void onStopNestedScroll(View paramView, int paramInt) {
    MotionScene motionScene = this.mScene;
    if (motionScene == null)
      return; 
    float f2 = this.mScrollTargetDX;
    float f1 = this.mScrollTargetDT;
    motionScene.processScrollUp(f2 / f1, this.mScrollTargetDY / f1);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    MotionScene motionScene = this.mScene;
    if (motionScene != null && this.mInteractionEnabled && motionScene.supportTouch()) {
      MotionScene.Transition transition = this.mScene.mCurrentTransition;
      if (transition != null && !transition.isEnabled())
        return super.onTouchEvent(paramMotionEvent); 
      this.mScene.processTouchEvent(paramMotionEvent, getCurrentState(), this);
      return true;
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void onViewAdded(View paramView) {
    super.onViewAdded(paramView);
    if (paramView instanceof MotionHelper) {
      MotionHelper motionHelper = (MotionHelper)paramView;
      if (this.mTransitionListeners == null)
        this.mTransitionListeners = new ArrayList<TransitionListener>(); 
      this.mTransitionListeners.add(motionHelper);
      if (motionHelper.isUsedOnShow()) {
        if (this.mOnShowHelpers == null)
          this.mOnShowHelpers = new ArrayList<MotionHelper>(); 
        this.mOnShowHelpers.add(motionHelper);
      } 
      if (motionHelper.isUseOnHide()) {
        if (this.mOnHideHelpers == null)
          this.mOnHideHelpers = new ArrayList<MotionHelper>(); 
        this.mOnHideHelpers.add(motionHelper);
      } 
    } 
  }
  
  public void onViewRemoved(View paramView) {
    super.onViewRemoved(paramView);
    ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
    if (arrayList != null)
      arrayList.remove(paramView); 
    arrayList = this.mOnHideHelpers;
    if (arrayList != null)
      arrayList.remove(paramView); 
  }
  
  protected void parseLayoutDescription(int paramInt) {
    this.mConstraintLayoutSpec = null;
  }
  
  @Deprecated
  public void rebuildMotion() {
    Log.e("MotionLayout", "This method is deprecated. Please call rebuildScene() instead.");
    rebuildScene();
  }
  
  public void rebuildScene() {
    this.mModel.reEvaluateState();
    invalidate();
  }
  
  public boolean removeTransitionListener(TransitionListener paramTransitionListener) {
    ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
    return (arrayList == null) ? false : arrayList.remove(paramTransitionListener);
  }
  
  public void requestLayout() {
    if (!this.mMeasureDuringTransition && this.mCurrentState == -1) {
      MotionScene motionScene = this.mScene;
      if (motionScene != null && motionScene.mCurrentTransition != null && this.mScene.mCurrentTransition.getLayoutDuringTransition() == 0)
        return; 
    } 
    super.requestLayout();
  }
  
  public void setDebugMode(int paramInt) {
    this.mDebugPath = paramInt;
    invalidate();
  }
  
  public void setInteractionEnabled(boolean paramBoolean) {
    this.mInteractionEnabled = paramBoolean;
  }
  
  public void setInterpolatedProgress(float paramFloat) {
    if (this.mScene != null) {
      setState(TransitionState.MOVING);
      Interpolator interpolator = this.mScene.getInterpolator();
      if (interpolator != null) {
        setProgress(interpolator.getInterpolation(paramFloat));
        return;
      } 
    } 
    setProgress(paramFloat);
  }
  
  public void setOnHide(float paramFloat) {
    ArrayList<MotionHelper> arrayList = this.mOnHideHelpers;
    if (arrayList != null) {
      int i = arrayList.size();
      for (byte b = 0; b < i; b++)
        ((MotionHelper)this.mOnHideHelpers.get(b)).setProgress(paramFloat); 
    } 
  }
  
  public void setOnShow(float paramFloat) {
    ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
    if (arrayList != null) {
      int i = arrayList.size();
      for (byte b = 0; b < i; b++)
        ((MotionHelper)this.mOnShowHelpers.get(b)).setProgress(paramFloat); 
    } 
  }
  
  public void setProgress(float paramFloat) {
    int i = paramFloat cmp 0.0F;
    if (i < 0 || paramFloat > 1.0F)
      Log.w("MotionLayout", "Warning! Progress is defined for values between 0.0 and 1.0 inclusive"); 
    if (!isAttachedToWindow()) {
      if (this.mStateCache == null)
        this.mStateCache = new StateCache(); 
      this.mStateCache.setProgress(paramFloat);
      return;
    } 
    if (i <= 0) {
      this.mCurrentState = this.mBeginState;
      if (this.mTransitionLastPosition == 0.0F)
        setState(TransitionState.FINISHED); 
    } else if (paramFloat >= 1.0F) {
      this.mCurrentState = this.mEndState;
      if (this.mTransitionLastPosition == 1.0F)
        setState(TransitionState.FINISHED); 
    } else {
      this.mCurrentState = -1;
      setState(TransitionState.MOVING);
    } 
    if (this.mScene == null)
      return; 
    this.mTransitionInstantly = true;
    this.mTransitionGoalPosition = paramFloat;
    this.mTransitionPosition = paramFloat;
    this.mTransitionLastTime = -1L;
    this.mAnimationStartTime = -1L;
    this.mInterpolator = null;
    this.mInTransition = true;
    invalidate();
  }
  
  public void setProgress(float paramFloat1, float paramFloat2) {
    if (!isAttachedToWindow()) {
      if (this.mStateCache == null)
        this.mStateCache = new StateCache(); 
      this.mStateCache.setProgress(paramFloat1);
      this.mStateCache.setVelocity(paramFloat2);
      return;
    } 
    setProgress(paramFloat1);
    setState(TransitionState.MOVING);
    this.mLastVelocity = paramFloat2;
    animateTo(1.0F);
  }
  
  public void setScene(MotionScene paramMotionScene) {
    this.mScene = paramMotionScene;
    paramMotionScene.setRtl(isRtl());
    rebuildScene();
  }
  
  public void setState(int paramInt1, int paramInt2, int paramInt3) {
    setState(TransitionState.SETUP);
    this.mCurrentState = paramInt1;
    this.mBeginState = -1;
    this.mEndState = -1;
    if (this.mConstraintLayoutSpec != null) {
      this.mConstraintLayoutSpec.updateConstraints(paramInt1, paramInt2, paramInt3);
    } else {
      MotionScene motionScene = this.mScene;
      if (motionScene != null)
        motionScene.getConstraintSet(paramInt1).applyTo(this); 
    } 
  }
  
  void setState(TransitionState paramTransitionState) {
    if (paramTransitionState == TransitionState.FINISHED && this.mCurrentState == -1)
      return; 
    TransitionState transitionState = this.mTransitionState;
    this.mTransitionState = paramTransitionState;
    if (transitionState == TransitionState.MOVING && paramTransitionState == TransitionState.MOVING)
      fireTransitionChange(); 
    int i = null.$SwitchMap$androidx$constraintlayout$motion$widget$MotionLayout$TransitionState[transitionState.ordinal()];
    if (i != 1 && i != 2) {
      if (i == 3 && paramTransitionState == TransitionState.FINISHED)
        fireTransitionCompleted(); 
    } else {
      if (paramTransitionState == TransitionState.MOVING)
        fireTransitionChange(); 
      if (paramTransitionState == TransitionState.FINISHED)
        fireTransitionCompleted(); 
    } 
  }
  
  public void setTransition(int paramInt) {
    if (this.mScene != null) {
      MotionScene.Transition transition = getTransition(paramInt);
      this.mBeginState = transition.getStartConstraintSetId();
      this.mEndState = transition.getEndConstraintSetId();
      if (!isAttachedToWindow()) {
        if (this.mStateCache == null)
          this.mStateCache = new StateCache(); 
        this.mStateCache.setStartState(this.mBeginState);
        this.mStateCache.setEndState(this.mEndState);
        return;
      } 
      float f1 = Float.NaN;
      int i = this.mCurrentState;
      paramInt = this.mBeginState;
      float f2 = 0.0F;
      if (i == paramInt) {
        f1 = 0.0F;
      } else if (i == this.mEndState) {
        f1 = 1.0F;
      } 
      this.mScene.setTransition(transition);
      this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
      rebuildScene();
      if (!Float.isNaN(f1))
        f2 = f1; 
      this.mTransitionLastPosition = f2;
      if (Float.isNaN(f1)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Debug.getLocation());
        stringBuilder.append(" transitionToStart ");
        Log.v("MotionLayout", stringBuilder.toString());
        transitionToStart();
      } else {
        setProgress(f1);
      } 
    } 
  }
  
  public void setTransition(int paramInt1, int paramInt2) {
    if (!isAttachedToWindow()) {
      if (this.mStateCache == null)
        this.mStateCache = new StateCache(); 
      this.mStateCache.setStartState(paramInt1);
      this.mStateCache.setEndState(paramInt2);
      return;
    } 
    MotionScene motionScene = this.mScene;
    if (motionScene != null) {
      this.mBeginState = paramInt1;
      this.mEndState = paramInt2;
      motionScene.setTransition(paramInt1, paramInt2);
      this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(paramInt1), this.mScene.getConstraintSet(paramInt2));
      rebuildScene();
      this.mTransitionLastPosition = 0.0F;
      transitionToStart();
    } 
  }
  
  protected void setTransition(MotionScene.Transition paramTransition) {
    long l;
    this.mScene.setTransition(paramTransition);
    setState(TransitionState.SETUP);
    if (this.mCurrentState == this.mScene.getEndId()) {
      this.mTransitionLastPosition = 1.0F;
      this.mTransitionPosition = 1.0F;
      this.mTransitionGoalPosition = 1.0F;
    } else {
      this.mTransitionLastPosition = 0.0F;
      this.mTransitionPosition = 0.0F;
      this.mTransitionGoalPosition = 0.0F;
    } 
    if (paramTransition.isTransitionFlag(1)) {
      l = -1L;
    } else {
      l = getNanoTime();
    } 
    this.mTransitionLastTime = l;
    int i = this.mScene.getStartId();
    int j = this.mScene.getEndId();
    if (i == this.mBeginState && j == this.mEndState)
      return; 
    this.mBeginState = i;
    this.mEndState = j;
    this.mScene.setTransition(i, j);
    this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
    this.mModel.setMeasuredId(this.mBeginState, this.mEndState);
    this.mModel.reEvaluateState();
    rebuildScene();
  }
  
  public void setTransitionDuration(int paramInt) {
    MotionScene motionScene = this.mScene;
    if (motionScene == null) {
      Log.e("MotionLayout", "MotionScene not defined");
      return;
    } 
    motionScene.setDuration(paramInt);
  }
  
  public void setTransitionListener(TransitionListener paramTransitionListener) {
    this.mTransitionListener = paramTransitionListener;
  }
  
  public void setTransitionState(Bundle paramBundle) {
    if (this.mStateCache == null)
      this.mStateCache = new StateCache(); 
    this.mStateCache.setTransitionState(paramBundle);
    if (isAttachedToWindow())
      this.mStateCache.apply(); 
  }
  
  public String toString() {
    Context context = getContext();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Debug.getName(context, this.mBeginState));
    stringBuilder.append("->");
    stringBuilder.append(Debug.getName(context, this.mEndState));
    stringBuilder.append(" (pos:");
    stringBuilder.append(this.mTransitionLastPosition);
    stringBuilder.append(" Dpos/Dt:");
    stringBuilder.append(this.mLastVelocity);
    return stringBuilder.toString();
  }
  
  public void touchAnimateTo(int paramInt, float paramFloat1, float paramFloat2) {
    if (this.mScene == null)
      return; 
    if (this.mTransitionLastPosition == paramFloat1)
      return; 
    this.mTemporalInterpolator = true;
    this.mAnimationStartTime = getNanoTime();
    this.mTransitionDuration = this.mScene.getDuration() / 1000.0F;
    this.mTransitionGoalPosition = paramFloat1;
    this.mInTransition = true;
    if (paramInt != 0 && paramInt != 1 && paramInt != 2) {
      if (paramInt != 4) {
        if (paramInt == 5)
          if (willJump(paramFloat2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration())) {
            this.mDecelerateLogic.config(paramFloat2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
            this.mInterpolator = this.mDecelerateLogic;
          } else {
            this.mStopLogic.config(this.mTransitionLastPosition, paramFloat1, paramFloat2, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
            this.mLastVelocity = 0.0F;
            paramInt = this.mCurrentState;
            this.mTransitionGoalPosition = paramFloat1;
            this.mCurrentState = paramInt;
            this.mInterpolator = (Interpolator)this.mStopLogic;
          }  
      } else {
        this.mDecelerateLogic.config(paramFloat2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
        this.mInterpolator = this.mDecelerateLogic;
      } 
    } else {
      if (paramInt == 1) {
        paramFloat1 = 0.0F;
      } else if (paramInt == 2) {
        paramFloat1 = 1.0F;
      } 
      this.mStopLogic.config(this.mTransitionLastPosition, paramFloat1, paramFloat2, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
      paramInt = this.mCurrentState;
      this.mTransitionGoalPosition = paramFloat1;
      this.mCurrentState = paramInt;
      this.mInterpolator = (Interpolator)this.mStopLogic;
    } 
    this.mTransitionInstantly = false;
    this.mAnimationStartTime = getNanoTime();
    invalidate();
  }
  
  public void transitionToEnd() {
    animateTo(1.0F);
  }
  
  public void transitionToStart() {
    animateTo(0.0F);
  }
  
  public void transitionToState(int paramInt) {
    if (!isAttachedToWindow()) {
      if (this.mStateCache == null)
        this.mStateCache = new StateCache(); 
      this.mStateCache.setEndState(paramInt);
      return;
    } 
    transitionToState(paramInt, -1, -1);
  }
  
  public void transitionToState(int paramInt1, int paramInt2, int paramInt3) {
    MotionScene motionScene = this.mScene;
    int i = paramInt1;
    if (motionScene != null) {
      i = paramInt1;
      if (motionScene.mStateSet != null) {
        paramInt2 = this.mScene.mStateSet.convertToConstraintSet(this.mCurrentState, paramInt1, paramInt2, paramInt3);
        i = paramInt1;
        if (paramInt2 != -1)
          i = paramInt2; 
      } 
    } 
    paramInt1 = this.mCurrentState;
    if (paramInt1 == i)
      return; 
    if (this.mBeginState == i) {
      animateTo(0.0F);
      return;
    } 
    if (this.mEndState == i) {
      animateTo(1.0F);
      return;
    } 
    this.mEndState = i;
    if (paramInt1 != -1) {
      setTransition(paramInt1, i);
      animateTo(1.0F);
      this.mTransitionLastPosition = 0.0F;
      transitionToEnd();
      return;
    } 
    paramInt3 = 0;
    this.mTemporalInterpolator = false;
    this.mTransitionGoalPosition = 1.0F;
    this.mTransitionPosition = 0.0F;
    this.mTransitionLastPosition = 0.0F;
    this.mTransitionLastTime = getNanoTime();
    this.mAnimationStartTime = getNanoTime();
    this.mTransitionInstantly = false;
    this.mInterpolator = null;
    this.mTransitionDuration = this.mScene.getDuration() / 1000.0F;
    this.mBeginState = -1;
    this.mScene.setTransition(-1, this.mEndState);
    this.mScene.getStartId();
    int j = getChildCount();
    this.mFrameArrayList.clear();
    for (paramInt1 = 0; paramInt1 < j; paramInt1++) {
      View view = getChildAt(paramInt1);
      MotionController motionController = new MotionController(view);
      this.mFrameArrayList.put(view, motionController);
    } 
    this.mInTransition = true;
    this.mModel.initFrom(this.mLayoutWidget, null, this.mScene.getConstraintSet(i));
    rebuildScene();
    this.mModel.build();
    computeCurrentPositions();
    paramInt2 = getWidth();
    i = getHeight();
    for (paramInt1 = 0; paramInt1 < j; paramInt1++) {
      MotionController motionController = this.mFrameArrayList.get(getChildAt(paramInt1));
      this.mScene.getKeyFrames(motionController);
      motionController.setup(paramInt2, i, this.mTransitionDuration, getNanoTime());
    } 
    float f = this.mScene.getStaggered();
    if (f != 0.0F) {
      float f1 = Float.MAX_VALUE;
      float f2 = -3.4028235E38F;
      paramInt2 = 0;
      while (true) {
        paramInt1 = paramInt3;
        if (paramInt2 < j) {
          MotionController motionController = this.mFrameArrayList.get(getChildAt(paramInt2));
          float f3 = motionController.getFinalX();
          f3 = motionController.getFinalY() + f3;
          f1 = Math.min(f1, f3);
          f2 = Math.max(f2, f3);
          paramInt2++;
          continue;
        } 
        break;
      } 
      while (paramInt1 < j) {
        MotionController motionController = this.mFrameArrayList.get(getChildAt(paramInt1));
        float f4 = motionController.getFinalX();
        float f3 = motionController.getFinalY();
        motionController.mStaggerScale = 1.0F / (1.0F - f);
        motionController.mStaggerOffset = f - (f4 + f3 - f1) * f / (f2 - f1);
        paramInt1++;
      } 
    } 
    this.mTransitionPosition = 0.0F;
    this.mTransitionLastPosition = 0.0F;
    this.mInTransition = true;
    invalidate();
  }
  
  public void updateState() {
    this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
    rebuildScene();
  }
  
  public void updateState(int paramInt, ConstraintSet paramConstraintSet) {
    MotionScene motionScene = this.mScene;
    if (motionScene != null)
      motionScene.setConstraintSet(paramInt, paramConstraintSet); 
    updateState();
    if (this.mCurrentState == paramInt)
      paramConstraintSet.applyTo(this); 
  }
  
  class DecelerateInterpolator extends MotionInterpolator {
    float currentP = 0.0F;
    
    float initalV = 0.0F;
    
    float maxA;
    
    final MotionLayout this$0;
    
    public void config(float param1Float1, float param1Float2, float param1Float3) {
      this.initalV = param1Float1;
      this.currentP = param1Float2;
      this.maxA = param1Float3;
    }
    
    public float getInterpolation(float param1Float) {
      float f2 = this.initalV;
      if (f2 > 0.0F) {
        float f6 = this.maxA;
        float f5 = param1Float;
        if (f2 / f6 < param1Float)
          f5 = f2 / f6; 
        MotionLayout.this.mLastVelocity = this.initalV - this.maxA * f5;
        f5 = this.initalV * f5 - this.maxA * f5 * f5 / 2.0F;
        param1Float = this.currentP;
        return f5 + param1Float;
      } 
      float f3 = -f2;
      float f4 = this.maxA;
      float f1 = param1Float;
      if (f3 / f4 < param1Float)
        f1 = -f2 / f4; 
      MotionLayout.this.mLastVelocity = this.initalV + this.maxA * f1;
      f1 = this.initalV * f1 + this.maxA * f1 * f1 / 2.0F;
      param1Float = this.currentP;
      return f1 + param1Float;
    }
    
    public float getVelocity() {
      return MotionLayout.this.mLastVelocity;
    }
  }
  
  private class DevModeDraw {
    private static final int DEBUG_PATH_TICKS_PER_MS = 16;
    
    final int DIAMOND_SIZE = 10;
    
    final int GRAPH_COLOR = -13391360;
    
    final int KEYFRAME_COLOR = -2067046;
    
    final int RED_COLOR = -21965;
    
    final int SHADOW_COLOR = 1996488704;
    
    Rect mBounds = new Rect();
    
    DashPathEffect mDashPathEffect;
    
    Paint mFillPaint;
    
    int mKeyFrameCount;
    
    float[] mKeyFramePoints;
    
    Paint mPaint;
    
    Paint mPaintGraph;
    
    Paint mPaintKeyframes;
    
    Path mPath;
    
    int[] mPathMode;
    
    float[] mPoints;
    
    boolean mPresentationMode = false;
    
    private float[] mRectangle;
    
    int mShadowTranslate = 1;
    
    Paint mTextPaint;
    
    final MotionLayout this$0;
    
    public DevModeDraw() {
      Paint paint2 = new Paint();
      this.mPaint = paint2;
      paint2.setAntiAlias(true);
      this.mPaint.setColor(-21965);
      this.mPaint.setStrokeWidth(2.0F);
      this.mPaint.setStyle(Paint.Style.STROKE);
      paint2 = new Paint();
      this.mPaintKeyframes = paint2;
      paint2.setAntiAlias(true);
      this.mPaintKeyframes.setColor(-2067046);
      this.mPaintKeyframes.setStrokeWidth(2.0F);
      this.mPaintKeyframes.setStyle(Paint.Style.STROKE);
      paint2 = new Paint();
      this.mPaintGraph = paint2;
      paint2.setAntiAlias(true);
      this.mPaintGraph.setColor(-13391360);
      this.mPaintGraph.setStrokeWidth(2.0F);
      this.mPaintGraph.setStyle(Paint.Style.STROKE);
      paint2 = new Paint();
      this.mTextPaint = paint2;
      paint2.setAntiAlias(true);
      this.mTextPaint.setColor(-13391360);
      this.mTextPaint.setTextSize((MotionLayout.this.getContext().getResources().getDisplayMetrics()).density * 12.0F);
      this.mRectangle = new float[8];
      Paint paint1 = new Paint();
      this.mFillPaint = paint1;
      paint1.setAntiAlias(true);
      DashPathEffect dashPathEffect = new DashPathEffect(new float[] { 4.0F, 8.0F }, 0.0F);
      this.mDashPathEffect = dashPathEffect;
      this.mPaintGraph.setPathEffect((PathEffect)dashPathEffect);
      this.mKeyFramePoints = new float[100];
      this.mPathMode = new int[50];
      if (this.mPresentationMode) {
        this.mPaint.setStrokeWidth(8.0F);
        this.mFillPaint.setStrokeWidth(8.0F);
        this.mPaintKeyframes.setStrokeWidth(8.0F);
        this.mShadowTranslate = 4;
      } 
    }
    
    private void drawBasicPath(Canvas param1Canvas) {
      param1Canvas.drawLines(this.mPoints, this.mPaint);
    }
    
    private void drawPathAsConfigured(Canvas param1Canvas) {
      byte b = 0;
      boolean bool2 = false;
      boolean bool1 = false;
      while (b < this.mKeyFrameCount) {
        if (this.mPathMode[b] == 1)
          bool2 = true; 
        if (this.mPathMode[b] == 2)
          bool1 = true; 
        b++;
      } 
      if (bool2)
        drawPathRelative(param1Canvas); 
      if (bool1)
        drawPathCartesian(param1Canvas); 
    }
    
    private void drawPathCartesian(Canvas param1Canvas) {
      float[] arrayOfFloat = this.mPoints;
      float f2 = arrayOfFloat[0];
      float f4 = arrayOfFloat[1];
      float f1 = arrayOfFloat[arrayOfFloat.length - 2];
      float f3 = arrayOfFloat[arrayOfFloat.length - 1];
      param1Canvas.drawLine(Math.min(f2, f1), Math.max(f4, f3), Math.max(f2, f1), Math.max(f4, f3), this.mPaintGraph);
      param1Canvas.drawLine(Math.min(f2, f1), Math.min(f4, f3), Math.min(f2, f1), Math.max(f4, f3), this.mPaintGraph);
    }
    
    private void drawPathCartesianTicks(Canvas param1Canvas, float param1Float1, float param1Float2) {
      float[] arrayOfFloat = this.mPoints;
      float f3 = arrayOfFloat[0];
      float f7 = arrayOfFloat[1];
      float f8 = arrayOfFloat[arrayOfFloat.length - 2];
      float f6 = arrayOfFloat[arrayOfFloat.length - 1];
      float f5 = Math.min(f3, f8);
      float f4 = Math.max(f7, f6);
      float f2 = param1Float1 - Math.min(f3, f8);
      float f1 = Math.max(f7, f6) - param1Float2;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("");
      stringBuilder2.append((int)((f2 * 100.0F / Math.abs(f8 - f3)) + 0.5D) / 100.0F);
      String str2 = stringBuilder2.toString();
      getTextBounds(str2, this.mTextPaint);
      param1Canvas.drawText(str2, f2 / 2.0F - (this.mBounds.width() / 2) + f5, param1Float2 - 20.0F, this.mTextPaint);
      param1Canvas.drawLine(param1Float1, param1Float2, Math.min(f3, f8), param1Float2, this.mPaintGraph);
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("");
      stringBuilder1.append((int)((f1 * 100.0F / Math.abs(f6 - f7)) + 0.5D) / 100.0F);
      String str1 = stringBuilder1.toString();
      getTextBounds(str1, this.mTextPaint);
      param1Canvas.drawText(str1, param1Float1 + 5.0F, f4 - f1 / 2.0F - (this.mBounds.height() / 2), this.mTextPaint);
      param1Canvas.drawLine(param1Float1, param1Float2, param1Float1, Math.max(f7, f6), this.mPaintGraph);
    }
    
    private void drawPathRelative(Canvas param1Canvas) {
      float[] arrayOfFloat = this.mPoints;
      param1Canvas.drawLine(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[arrayOfFloat.length - 2], arrayOfFloat[arrayOfFloat.length - 1], this.mPaintGraph);
    }
    
    private void drawPathRelativeTicks(Canvas param1Canvas, float param1Float1, float param1Float2) {
      float[] arrayOfFloat = this.mPoints;
      float f3 = arrayOfFloat[0];
      float f2 = arrayOfFloat[1];
      float f4 = arrayOfFloat[arrayOfFloat.length - 2];
      float f5 = arrayOfFloat[arrayOfFloat.length - 1];
      float f1 = (float)Math.hypot((f3 - f4), (f2 - f5));
      f4 -= f3;
      f5 -= f2;
      float f6 = ((param1Float1 - f3) * f4 + (param1Float2 - f2) * f5) / f1 * f1;
      f3 += f4 * f6;
      f4 = f2 + f6 * f5;
      Path path = new Path();
      path.moveTo(param1Float1, param1Float2);
      path.lineTo(f3, f4);
      f2 = (float)Math.hypot((f3 - param1Float1), (f4 - param1Float2));
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("");
      stringBuilder.append((int)(f2 * 100.0F / f1) / 100.0F);
      String str = stringBuilder.toString();
      getTextBounds(str, this.mTextPaint);
      param1Canvas.drawTextOnPath(str, path, f2 / 2.0F - (this.mBounds.width() / 2), -20.0F, this.mTextPaint);
      param1Canvas.drawLine(param1Float1, param1Float2, f3, f4, this.mPaintGraph);
    }
    
    private void drawPathScreenTicks(Canvas param1Canvas, float param1Float1, float param1Float2, int param1Int1, int param1Int2) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("");
      stringBuilder2.append((int)(((param1Float1 - (param1Int1 / 2)) * 100.0F / (MotionLayout.this.getWidth() - param1Int1)) + 0.5D) / 100.0F);
      String str2 = stringBuilder2.toString();
      getTextBounds(str2, this.mTextPaint);
      param1Canvas.drawText(str2, param1Float1 / 2.0F - (this.mBounds.width() / 2) + 0.0F, param1Float2 - 20.0F, this.mTextPaint);
      param1Canvas.drawLine(param1Float1, param1Float2, Math.min(0.0F, 1.0F), param1Float2, this.mPaintGraph);
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("");
      stringBuilder1.append((int)(((param1Float2 - (param1Int2 / 2)) * 100.0F / (MotionLayout.this.getHeight() - param1Int2)) + 0.5D) / 100.0F);
      String str1 = stringBuilder1.toString();
      getTextBounds(str1, this.mTextPaint);
      param1Canvas.drawText(str1, param1Float1 + 5.0F, 0.0F - param1Float2 / 2.0F - (this.mBounds.height() / 2), this.mTextPaint);
      param1Canvas.drawLine(param1Float1, param1Float2, param1Float1, Math.max(0.0F, 1.0F), this.mPaintGraph);
    }
    
    private void drawRectangle(Canvas param1Canvas, MotionController param1MotionController) {
      this.mPath.reset();
      for (byte b = 0; b <= 50; b++) {
        param1MotionController.buildRect(b / 50, this.mRectangle, 0);
        Path path2 = this.mPath;
        float[] arrayOfFloat3 = this.mRectangle;
        path2.moveTo(arrayOfFloat3[0], arrayOfFloat3[1]);
        path2 = this.mPath;
        arrayOfFloat3 = this.mRectangle;
        path2.lineTo(arrayOfFloat3[2], arrayOfFloat3[3]);
        Path path3 = this.mPath;
        float[] arrayOfFloat1 = this.mRectangle;
        path3.lineTo(arrayOfFloat1[4], arrayOfFloat1[5]);
        Path path1 = this.mPath;
        float[] arrayOfFloat2 = this.mRectangle;
        path1.lineTo(arrayOfFloat2[6], arrayOfFloat2[7]);
        this.mPath.close();
      } 
      this.mPaint.setColor(1140850688);
      param1Canvas.translate(2.0F, 2.0F);
      param1Canvas.drawPath(this.mPath, this.mPaint);
      param1Canvas.translate(-2.0F, -2.0F);
      this.mPaint.setColor(-65536);
      param1Canvas.drawPath(this.mPath, this.mPaint);
    }
    
    private void drawTicks(Canvas param1Canvas, int param1Int1, int param1Int2, MotionController param1MotionController) {
      boolean bool1;
      boolean bool2;
      if (param1MotionController.mView != null) {
        bool2 = param1MotionController.mView.getWidth();
        bool1 = param1MotionController.mView.getHeight();
      } else {
        bool2 = false;
        bool1 = false;
      } 
      for (byte b = 1; b < param1Int2 - 1; b++) {
        if (param1Int1 != 4 || this.mPathMode[b - 1] != 0) {
          float[] arrayOfFloat1 = this.mKeyFramePoints;
          int i = b * 2;
          float f2 = arrayOfFloat1[i];
          float f1 = arrayOfFloat1[i + 1];
          this.mPath.reset();
          this.mPath.moveTo(f2, f1 + 10.0F);
          this.mPath.lineTo(f2 + 10.0F, f1);
          this.mPath.lineTo(f2, f1 - 10.0F);
          this.mPath.lineTo(f2 - 10.0F, f1);
          this.mPath.close();
          i = b - 1;
          param1MotionController.getKeyFrame(i);
          if (param1Int1 == 4) {
            int[] arrayOfInt = this.mPathMode;
            if (arrayOfInt[i] == 1) {
              drawPathRelativeTicks(param1Canvas, f2 - 0.0F, f1 - 0.0F);
            } else if (arrayOfInt[i] == 2) {
              drawPathCartesianTicks(param1Canvas, f2 - 0.0F, f1 - 0.0F);
            } else if (arrayOfInt[i] == 3) {
              drawPathScreenTicks(param1Canvas, f2 - 0.0F, f1 - 0.0F, bool2, bool1);
            } 
            param1Canvas.drawPath(this.mPath, this.mFillPaint);
          } 
          if (param1Int1 == 2)
            drawPathRelativeTicks(param1Canvas, f2 - 0.0F, f1 - 0.0F); 
          if (param1Int1 == 3)
            drawPathCartesianTicks(param1Canvas, f2 - 0.0F, f1 - 0.0F); 
          if (param1Int1 == 6)
            drawPathScreenTicks(param1Canvas, f2 - 0.0F, f1 - 0.0F, bool2, bool1); 
          param1Canvas.drawPath(this.mPath, this.mFillPaint);
        } 
      } 
      float[] arrayOfFloat = this.mPoints;
      if (arrayOfFloat.length > 1) {
        param1Canvas.drawCircle(arrayOfFloat[0], arrayOfFloat[1], 8.0F, this.mPaintKeyframes);
        arrayOfFloat = this.mPoints;
        param1Canvas.drawCircle(arrayOfFloat[arrayOfFloat.length - 2], arrayOfFloat[arrayOfFloat.length - 1], 8.0F, this.mPaintKeyframes);
      } 
    }
    
    private void drawTranslation(Canvas param1Canvas, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      param1Canvas.drawRect(param1Float1, param1Float2, param1Float3, param1Float4, this.mPaintGraph);
      param1Canvas.drawLine(param1Float1, param1Float2, param1Float3, param1Float4, this.mPaintGraph);
    }
    
    public void draw(Canvas param1Canvas, HashMap<View, MotionController> param1HashMap, int param1Int1, int param1Int2) {
      if (param1HashMap != null && param1HashMap.size() != 0) {
        param1Canvas.save();
        if (!MotionLayout.this.isInEditMode() && (param1Int2 & 0x1) == 2) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(MotionLayout.this.getContext().getResources().getResourceName(MotionLayout.this.mEndState));
          stringBuilder.append(":");
          stringBuilder.append(MotionLayout.this.getProgress());
          String str = stringBuilder.toString();
          param1Canvas.drawText(str, 10.0F, (MotionLayout.this.getHeight() - 30), this.mTextPaint);
          param1Canvas.drawText(str, 11.0F, (MotionLayout.this.getHeight() - 29), this.mPaint);
        } 
        for (MotionController motionController : param1HashMap.values()) {
          int j = motionController.getDrawPath();
          int i = j;
          if (param1Int2 > 0) {
            i = j;
            if (j == 0)
              i = 1; 
          } 
          if (i == 0)
            continue; 
          this.mKeyFrameCount = motionController.buildKeyFrames(this.mKeyFramePoints, this.mPathMode);
          if (i >= 1) {
            j = param1Int1 / 16;
            float[] arrayOfFloat = this.mPoints;
            if (arrayOfFloat == null || arrayOfFloat.length != j * 2) {
              this.mPoints = new float[j * 2];
              this.mPath = new Path();
            } 
            int k = this.mShadowTranslate;
            param1Canvas.translate(k, k);
            this.mPaint.setColor(1996488704);
            this.mFillPaint.setColor(1996488704);
            this.mPaintKeyframes.setColor(1996488704);
            this.mPaintGraph.setColor(1996488704);
            motionController.buildPath(this.mPoints, j);
            drawAll(param1Canvas, i, this.mKeyFrameCount, motionController);
            this.mPaint.setColor(-21965);
            this.mPaintKeyframes.setColor(-2067046);
            this.mFillPaint.setColor(-2067046);
            this.mPaintGraph.setColor(-13391360);
            j = this.mShadowTranslate;
            param1Canvas.translate(-j, -j);
            drawAll(param1Canvas, i, this.mKeyFrameCount, motionController);
            if (i == 5)
              drawRectangle(param1Canvas, motionController); 
          } 
        } 
        param1Canvas.restore();
      } 
    }
    
    public void drawAll(Canvas param1Canvas, int param1Int1, int param1Int2, MotionController param1MotionController) {
      if (param1Int1 == 4)
        drawPathAsConfigured(param1Canvas); 
      if (param1Int1 == 2)
        drawPathRelative(param1Canvas); 
      if (param1Int1 == 3)
        drawPathCartesian(param1Canvas); 
      drawBasicPath(param1Canvas);
      drawTicks(param1Canvas, param1Int1, param1Int2, param1MotionController);
    }
    
    void getTextBounds(String param1String, Paint param1Paint) {
      param1Paint.getTextBounds(param1String, 0, param1String.length(), this.mBounds);
    }
  }
  
  class Model {
    ConstraintSet mEnd = null;
    
    int mEndId;
    
    ConstraintWidgetContainer mLayoutEnd = new ConstraintWidgetContainer();
    
    ConstraintWidgetContainer mLayoutStart = new ConstraintWidgetContainer();
    
    ConstraintSet mStart = null;
    
    int mStartId;
    
    final MotionLayout this$0;
    
    private void debugLayout(String param1String, ConstraintWidgetContainer param1ConstraintWidgetContainer) {
      View view = (View)param1ConstraintWidgetContainer.getCompanionWidget();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(param1String);
      stringBuilder2.append(" ");
      stringBuilder2.append(Debug.getName(view));
      String str = stringBuilder2.toString();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append("  ========= ");
      stringBuilder1.append(param1ConstraintWidgetContainer);
      Log.v("MotionLayout", stringBuilder1.toString());
      int i = param1ConstraintWidgetContainer.getChildren().size();
      for (byte b = 0; b < i; b++) {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append("[");
        stringBuilder1.append(b);
        stringBuilder1.append("] ");
        String str3 = stringBuilder1.toString();
        ConstraintWidget constraintWidget = param1ConstraintWidgetContainer.getChildren().get(b);
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        ConstraintAnchor constraintAnchor = constraintWidget.mTop.mTarget;
        String str2 = "_";
        if (constraintAnchor != null) {
          str1 = "T";
        } else {
          str1 = "_";
        } 
        stringBuilder4.append(str1);
        String str1 = stringBuilder4.toString();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(str1);
        if (constraintWidget.mBottom.mTarget != null) {
          str1 = "B";
        } else {
          str1 = "_";
        } 
        stringBuilder4.append(str1);
        str1 = stringBuilder4.toString();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(str1);
        if (constraintWidget.mLeft.mTarget != null) {
          str1 = "L";
        } else {
          str1 = "_";
        } 
        stringBuilder4.append(str1);
        str1 = stringBuilder4.toString();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(str1);
        str1 = str2;
        if (constraintWidget.mRight.mTarget != null)
          str1 = "R"; 
        stringBuilder4.append(str1);
        String str4 = stringBuilder4.toString();
        View view1 = (View)constraintWidget.getCompanionWidget();
        str2 = Debug.getName(view1);
        str1 = str2;
        if (view1 instanceof TextView) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str2);
          stringBuilder.append("(");
          stringBuilder.append(((TextView)view1).getText());
          stringBuilder.append(")");
          str1 = stringBuilder.toString();
        } 
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str3);
        stringBuilder3.append("  ");
        stringBuilder3.append(str1);
        stringBuilder3.append(" ");
        stringBuilder3.append(constraintWidget);
        stringBuilder3.append(" ");
        stringBuilder3.append(str4);
        Log.v("MotionLayout", stringBuilder3.toString());
      } 
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(" done. ");
      Log.v("MotionLayout", stringBuilder1.toString());
    }
    
    private void debugLayoutParam(String param1String, ConstraintLayout.LayoutParams param1LayoutParams) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(" ");
      if (param1LayoutParams.startToStart != -1) {
        str2 = "SS";
      } else {
        str2 = "__";
      } 
      stringBuilder2.append(str2);
      String str2 = stringBuilder2.toString();
      StringBuilder stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      int i = param1LayoutParams.startToEnd;
      String str3 = "|__";
      if (i != -1) {
        str2 = "|SE";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.endToStart != -1) {
        str2 = "|ES";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.endToEnd != -1) {
        str2 = "|EE";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.leftToLeft != -1) {
        str2 = "|LL";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.leftToRight != -1) {
        str2 = "|LR";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.rightToLeft != -1) {
        str2 = "|RL";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.rightToRight != -1) {
        str2 = "|RR";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.topToTop != -1) {
        str2 = "|TT";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.topToBottom != -1) {
        str2 = "|TB";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (param1LayoutParams.bottomToTop != -1) {
        str2 = "|BT";
      } else {
        str2 = "|__";
      } 
      stringBuilder3.append(str2);
      str2 = stringBuilder3.toString();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      str2 = str3;
      if (param1LayoutParams.bottomToBottom != -1)
        str2 = "|BB"; 
      stringBuilder3.append(str2);
      String str1 = stringBuilder3.toString();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(param1String);
      stringBuilder1.append(str1);
      Log.v("MotionLayout", stringBuilder1.toString());
    }
    
    private void debugWidget(String param1String, ConstraintWidget param1ConstraintWidget) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(" ");
      ConstraintAnchor constraintAnchor2 = param1ConstraintWidget.mTop.mTarget;
      String str4 = "B";
      String str3 = "__";
      if (constraintAnchor2 != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("T");
        if (param1ConstraintWidget.mTop.mTarget.mType == ConstraintAnchor.Type.TOP) {
          str2 = "T";
        } else {
          str2 = "B";
        } 
        stringBuilder.append(str2);
        str2 = stringBuilder.toString();
      } else {
        str2 = "__";
      } 
      stringBuilder2.append(str2);
      String str2 = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str2);
      if (param1ConstraintWidget.mBottom.mTarget != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("B");
        str2 = str4;
        if (param1ConstraintWidget.mBottom.mTarget.mType == ConstraintAnchor.Type.TOP)
          str2 = "T"; 
        stringBuilder.append(str2);
        str2 = stringBuilder.toString();
      } else {
        str2 = "__";
      } 
      stringBuilder2.append(str2);
      str2 = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str2);
      ConstraintAnchor constraintAnchor1 = param1ConstraintWidget.mLeft.mTarget;
      str4 = "R";
      if (constraintAnchor1 != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("L");
        if (param1ConstraintWidget.mLeft.mTarget.mType == ConstraintAnchor.Type.LEFT) {
          str1 = "L";
        } else {
          str1 = "R";
        } 
        stringBuilder.append(str1);
        str1 = stringBuilder.toString();
      } else {
        str1 = "__";
      } 
      stringBuilder2.append(str1);
      String str1 = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str1);
      str1 = str3;
      if (param1ConstraintWidget.mRight.mTarget != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("R");
        str1 = str4;
        if (param1ConstraintWidget.mRight.mTarget.mType == ConstraintAnchor.Type.LEFT)
          str1 = "L"; 
        stringBuilder.append(str1);
        str1 = stringBuilder.toString();
      } 
      stringBuilder2.append(str1);
      str3 = stringBuilder2.toString();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(param1String);
      stringBuilder1.append(str3);
      stringBuilder1.append(" ---  ");
      stringBuilder1.append(param1ConstraintWidget);
      Log.v("MotionLayout", stringBuilder1.toString());
    }
    
    private void setupConstraintWidget(ConstraintWidgetContainer param1ConstraintWidgetContainer, ConstraintSet param1ConstraintSet) {
      SparseArray sparseArray = new SparseArray();
      Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
      sparseArray.clear();
      sparseArray.put(0, param1ConstraintWidgetContainer);
      sparseArray.put(MotionLayout.this.getId(), param1ConstraintWidgetContainer);
      for (ConstraintWidget constraintWidget : param1ConstraintWidgetContainer.getChildren())
        sparseArray.put(((View)constraintWidget.getCompanionWidget()).getId(), constraintWidget); 
      for (ConstraintWidget constraintWidget : param1ConstraintWidgetContainer.getChildren()) {
        View view = (View)constraintWidget.getCompanionWidget();
        param1ConstraintSet.applyToLayoutParams(view.getId(), (ConstraintLayout.LayoutParams)layoutParams);
        constraintWidget.setWidth(param1ConstraintSet.getWidth(view.getId()));
        constraintWidget.setHeight(param1ConstraintSet.getHeight(view.getId()));
        if (view instanceof ConstraintHelper) {
          param1ConstraintSet.applyToHelper((ConstraintHelper)view, constraintWidget, (ConstraintLayout.LayoutParams)layoutParams, sparseArray);
          if (view instanceof Barrier)
            ((Barrier)view).validateParams(); 
        } 
        if (Build.VERSION.SDK_INT >= 17) {
          layoutParams.resolveLayoutDirection(MotionLayout.this.getLayoutDirection());
        } else {
          layoutParams.resolveLayoutDirection(0);
        } 
        MotionLayout.this.applyConstraintsFromLayoutParams(false, view, constraintWidget, (ConstraintLayout.LayoutParams)layoutParams, sparseArray);
        if (param1ConstraintSet.getVisibilityMode(view.getId()) == 1) {
          constraintWidget.setVisibility(view.getVisibility());
          continue;
        } 
        constraintWidget.setVisibility(param1ConstraintSet.getVisibility(view.getId()));
      } 
      for (ConstraintWidget constraintWidget : param1ConstraintWidgetContainer.getChildren()) {
        if (constraintWidget instanceof VirtualLayout) {
          ConstraintHelper constraintHelper = (ConstraintHelper)constraintWidget.getCompanionWidget();
          Helper helper = (Helper)constraintWidget;
          constraintHelper.updatePreLayout(param1ConstraintWidgetContainer, helper, sparseArray);
          ((VirtualLayout)helper).captureWidgets();
        } 
      } 
    }
    
    public void build() {
      byte b1;
      int i = MotionLayout.this.getChildCount();
      MotionLayout.this.mFrameArrayList.clear();
      byte b3 = 0;
      byte b2 = 0;
      while (true) {
        b1 = b3;
        if (b2 < i) {
          View view = MotionLayout.this.getChildAt(b2);
          MotionController motionController = new MotionController(view);
          MotionLayout.this.mFrameArrayList.put(view, motionController);
          b2++;
          continue;
        } 
        break;
      } 
      while (b1 < i) {
        View view = MotionLayout.this.getChildAt(b1);
        MotionController motionController = MotionLayout.this.mFrameArrayList.get(view);
        if (motionController != null) {
          if (this.mStart != null) {
            ConstraintWidget constraintWidget = getWidget(this.mLayoutStart, view);
            if (constraintWidget != null) {
              motionController.setStartState(constraintWidget, this.mStart);
            } else if (MotionLayout.this.mDebugPath != 0) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(Debug.getLocation());
              stringBuilder.append("no widget for  ");
              stringBuilder.append(Debug.getName(view));
              stringBuilder.append(" (");
              stringBuilder.append(view.getClass().getName());
              stringBuilder.append(")");
              Log.e("MotionLayout", stringBuilder.toString());
            } 
          } 
          if (this.mEnd != null) {
            ConstraintWidget constraintWidget = getWidget(this.mLayoutEnd, view);
            if (constraintWidget != null) {
              motionController.setEndState(constraintWidget, this.mEnd);
            } else if (MotionLayout.this.mDebugPath != 0) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(Debug.getLocation());
              stringBuilder.append("no widget for  ");
              stringBuilder.append(Debug.getName(view));
              stringBuilder.append(" (");
              stringBuilder.append(view.getClass().getName());
              stringBuilder.append(")");
              Log.e("MotionLayout", stringBuilder.toString());
            } 
          } 
        } 
        b1++;
      } 
    }
    
    void copy(ConstraintWidgetContainer param1ConstraintWidgetContainer1, ConstraintWidgetContainer param1ConstraintWidgetContainer2) {
      ArrayList arrayList = param1ConstraintWidgetContainer1.getChildren();
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put(param1ConstraintWidgetContainer1, param1ConstraintWidgetContainer2);
      param1ConstraintWidgetContainer2.getChildren().clear();
      param1ConstraintWidgetContainer2.copy((ConstraintWidget)param1ConstraintWidgetContainer1, hashMap);
      for (ConstraintWidget constraintWidget2 : arrayList) {
        ConstraintWidget constraintWidget1;
        if (constraintWidget2 instanceof Barrier) {
          Barrier barrier = new Barrier();
        } else if (constraintWidget2 instanceof Guideline) {
          Guideline guideline = new Guideline();
        } else if (constraintWidget2 instanceof Flow) {
          Flow flow = new Flow();
        } else if (constraintWidget2 instanceof Helper) {
          HelperWidget helperWidget = new HelperWidget();
        } else {
          constraintWidget1 = new ConstraintWidget();
        } 
        param1ConstraintWidgetContainer2.add(constraintWidget1);
        hashMap.put(constraintWidget2, constraintWidget1);
      } 
      for (ConstraintWidget constraintWidget : arrayList)
        ((ConstraintWidget)hashMap.get(constraintWidget)).copy(constraintWidget, hashMap); 
    }
    
    ConstraintWidget getWidget(ConstraintWidgetContainer param1ConstraintWidgetContainer, View param1View) {
      if (param1ConstraintWidgetContainer.getCompanionWidget() == param1View)
        return (ConstraintWidget)param1ConstraintWidgetContainer; 
      ArrayList<ConstraintWidget> arrayList = param1ConstraintWidgetContainer.getChildren();
      int i = arrayList.size();
      for (byte b = 0; b < i; b++) {
        ConstraintWidget constraintWidget = arrayList.get(b);
        if (constraintWidget.getCompanionWidget() == param1View)
          return constraintWidget; 
      } 
      return null;
    }
    
    void initFrom(ConstraintWidgetContainer param1ConstraintWidgetContainer, ConstraintSet param1ConstraintSet1, ConstraintSet param1ConstraintSet2) {
      this.mStart = param1ConstraintSet1;
      this.mEnd = param1ConstraintSet2;
      this.mLayoutStart = new ConstraintWidgetContainer();
      this.mLayoutEnd = new ConstraintWidgetContainer();
      this.mLayoutStart.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
      this.mLayoutEnd.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
      this.mLayoutStart.removeAllChildren();
      this.mLayoutEnd.removeAllChildren();
      copy(MotionLayout.this.mLayoutWidget, this.mLayoutStart);
      copy(MotionLayout.this.mLayoutWidget, this.mLayoutEnd);
      if (MotionLayout.this.mTransitionLastPosition > 0.5D) {
        if (param1ConstraintSet1 != null)
          setupConstraintWidget(this.mLayoutStart, param1ConstraintSet1); 
        setupConstraintWidget(this.mLayoutEnd, param1ConstraintSet2);
      } else {
        setupConstraintWidget(this.mLayoutEnd, param1ConstraintSet2);
        if (param1ConstraintSet1 != null)
          setupConstraintWidget(this.mLayoutStart, param1ConstraintSet1); 
      } 
      this.mLayoutStart.setRtl(MotionLayout.this.isRtl());
      this.mLayoutStart.updateHierarchy();
      this.mLayoutEnd.setRtl(MotionLayout.this.isRtl());
      this.mLayoutEnd.updateHierarchy();
      ViewGroup.LayoutParams layoutParams = MotionLayout.this.getLayoutParams();
      if (layoutParams != null) {
        if (layoutParams.width == -2) {
          this.mLayoutStart.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
          this.mLayoutEnd.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        } 
        if (layoutParams.height == -2) {
          this.mLayoutStart.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
          this.mLayoutEnd.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        } 
      } 
    }
    
    public boolean isNotConfiguredWith(int param1Int1, int param1Int2) {
      return (param1Int1 != this.mStartId || param1Int2 != this.mEndId);
    }
    
    public void measure(int param1Int1, int param1Int2) {
      boolean bool1;
      boolean bool2;
      int m = View.MeasureSpec.getMode(param1Int1);
      int j = View.MeasureSpec.getMode(param1Int2);
      MotionLayout.this.mWidthMeasureMode = m;
      MotionLayout.this.mHeightMeasureMode = j;
      int k = MotionLayout.this.getOptimizationLevel();
      if (MotionLayout.this.mCurrentState == MotionLayout.this.getStartState()) {
        MotionLayout.this.resolveSystem(this.mLayoutEnd, k, param1Int1, param1Int2);
        if (this.mStart != null)
          MotionLayout.this.resolveSystem(this.mLayoutStart, k, param1Int1, param1Int2); 
      } else {
        if (this.mStart != null)
          MotionLayout.this.resolveSystem(this.mLayoutStart, k, param1Int1, param1Int2); 
        MotionLayout.this.resolveSystem(this.mLayoutEnd, k, param1Int1, param1Int2);
      } 
      if (MotionLayout.this.getParent() instanceof MotionLayout && m == 1073741824 && j == 1073741824) {
        i = 0;
      } else {
        i = 1;
      } 
      if (i) {
        MotionLayout.this.mWidthMeasureMode = m;
        MotionLayout.this.mHeightMeasureMode = j;
        if (MotionLayout.this.mCurrentState == MotionLayout.this.getStartState()) {
          MotionLayout.this.resolveSystem(this.mLayoutEnd, k, param1Int1, param1Int2);
          if (this.mStart != null)
            MotionLayout.this.resolveSystem(this.mLayoutStart, k, param1Int1, param1Int2); 
        } else {
          if (this.mStart != null)
            MotionLayout.this.resolveSystem(this.mLayoutStart, k, param1Int1, param1Int2); 
          MotionLayout.this.resolveSystem(this.mLayoutEnd, k, param1Int1, param1Int2);
        } 
        MotionLayout.this.mStartWrapWidth = this.mLayoutStart.getWidth();
        MotionLayout.this.mStartWrapHeight = this.mLayoutStart.getHeight();
        MotionLayout.this.mEndWrapWidth = this.mLayoutEnd.getWidth();
        MotionLayout.this.mEndWrapHeight = this.mLayoutEnd.getHeight();
        MotionLayout motionLayout = MotionLayout.this;
        if (motionLayout.mStartWrapWidth != MotionLayout.this.mEndWrapWidth || MotionLayout.this.mStartWrapHeight != MotionLayout.this.mEndWrapHeight) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        motionLayout.mMeasureDuringTransition = bool1;
      } 
      int i = MotionLayout.this.mStartWrapWidth;
      j = MotionLayout.this.mStartWrapHeight;
      if (MotionLayout.this.mWidthMeasureMode == Integer.MIN_VALUE || MotionLayout.this.mWidthMeasureMode == 0)
        i = (int)(MotionLayout.this.mStartWrapWidth + MotionLayout.this.mPostInterpolationPosition * (MotionLayout.this.mEndWrapWidth - MotionLayout.this.mStartWrapWidth)); 
      if (MotionLayout.this.mHeightMeasureMode == Integer.MIN_VALUE || MotionLayout.this.mHeightMeasureMode == 0)
        j = (int)(MotionLayout.this.mStartWrapHeight + MotionLayout.this.mPostInterpolationPosition * (MotionLayout.this.mEndWrapHeight - MotionLayout.this.mStartWrapHeight)); 
      if (this.mLayoutStart.isWidthMeasuredTooSmall() || this.mLayoutEnd.isWidthMeasuredTooSmall()) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (this.mLayoutStart.isHeightMeasuredTooSmall() || this.mLayoutEnd.isHeightMeasuredTooSmall()) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      MotionLayout.this.resolveMeasuredDimension(param1Int1, param1Int2, i, j, bool1, bool2);
    }
    
    public void reEvaluateState() {
      measure(MotionLayout.this.mLastWidthMeasureSpec, MotionLayout.this.mLastHeightMeasureSpec);
      MotionLayout.this.setupMotionViews();
    }
    
    public void setMeasuredId(int param1Int1, int param1Int2) {
      this.mStartId = param1Int1;
      this.mEndId = param1Int2;
    }
  }
  
  protected static interface MotionTracker {
    void addMovement(MotionEvent param1MotionEvent);
    
    void clear();
    
    void computeCurrentVelocity(int param1Int);
    
    void computeCurrentVelocity(int param1Int, float param1Float);
    
    float getXVelocity();
    
    float getXVelocity(int param1Int);
    
    float getYVelocity();
    
    float getYVelocity(int param1Int);
    
    void recycle();
  }
  
  private static class MyTracker implements MotionTracker {
    private static MyTracker me = new MyTracker();
    
    VelocityTracker tracker;
    
    public static MyTracker obtain() {
      me.tracker = VelocityTracker.obtain();
      return me;
    }
    
    public void addMovement(MotionEvent param1MotionEvent) {
      VelocityTracker velocityTracker = this.tracker;
      if (velocityTracker != null)
        velocityTracker.addMovement(param1MotionEvent); 
    }
    
    public void clear() {
      VelocityTracker velocityTracker = this.tracker;
      if (velocityTracker != null)
        velocityTracker.clear(); 
    }
    
    public void computeCurrentVelocity(int param1Int) {
      VelocityTracker velocityTracker = this.tracker;
      if (velocityTracker != null)
        velocityTracker.computeCurrentVelocity(param1Int); 
    }
    
    public void computeCurrentVelocity(int param1Int, float param1Float) {
      VelocityTracker velocityTracker = this.tracker;
      if (velocityTracker != null)
        velocityTracker.computeCurrentVelocity(param1Int, param1Float); 
    }
    
    public float getXVelocity() {
      VelocityTracker velocityTracker = this.tracker;
      return (velocityTracker != null) ? velocityTracker.getXVelocity() : 0.0F;
    }
    
    public float getXVelocity(int param1Int) {
      VelocityTracker velocityTracker = this.tracker;
      return (velocityTracker != null) ? velocityTracker.getXVelocity(param1Int) : 0.0F;
    }
    
    public float getYVelocity() {
      VelocityTracker velocityTracker = this.tracker;
      return (velocityTracker != null) ? velocityTracker.getYVelocity() : 0.0F;
    }
    
    public float getYVelocity(int param1Int) {
      return (this.tracker != null) ? getYVelocity(param1Int) : 0.0F;
    }
    
    public void recycle() {
      VelocityTracker velocityTracker = this.tracker;
      if (velocityTracker != null) {
        velocityTracker.recycle();
        this.tracker = null;
      } 
    }
  }
  
  class StateCache {
    final String KeyEndState = "motion.EndState";
    
    final String KeyProgress = "motion.progress";
    
    final String KeyStartState = "motion.StartState";
    
    final String KeyVelocity = "motion.velocity";
    
    int endState = -1;
    
    float mProgress = Float.NaN;
    
    float mVelocity = Float.NaN;
    
    int startState = -1;
    
    final MotionLayout this$0;
    
    void apply() {
      if (this.startState != -1 || this.endState != -1) {
        int i = this.startState;
        if (i == -1) {
          MotionLayout.this.transitionToState(this.endState);
        } else {
          int j = this.endState;
          if (j == -1) {
            MotionLayout.this.setState(i, -1, -1);
          } else {
            MotionLayout.this.setTransition(i, j);
          } 
        } 
        MotionLayout.this.setState(MotionLayout.TransitionState.SETUP);
      } 
      if (Float.isNaN(this.mVelocity)) {
        if (Float.isNaN(this.mProgress))
          return; 
        MotionLayout.this.setProgress(this.mProgress);
        return;
      } 
      MotionLayout.this.setProgress(this.mProgress, this.mVelocity);
      this.mProgress = Float.NaN;
      this.mVelocity = Float.NaN;
      this.startState = -1;
      this.endState = -1;
    }
    
    public Bundle getTransitionState() {
      Bundle bundle = new Bundle();
      bundle.putFloat("motion.progress", this.mProgress);
      bundle.putFloat("motion.velocity", this.mVelocity);
      bundle.putInt("motion.StartState", this.startState);
      bundle.putInt("motion.EndState", this.endState);
      return bundle;
    }
    
    public void recordState() {
      this.endState = MotionLayout.this.mEndState;
      this.startState = MotionLayout.this.mBeginState;
      this.mVelocity = MotionLayout.this.getVelocity();
      this.mProgress = MotionLayout.this.getProgress();
    }
    
    public void setEndState(int param1Int) {
      this.endState = param1Int;
    }
    
    public void setProgress(float param1Float) {
      this.mProgress = param1Float;
    }
    
    public void setStartState(int param1Int) {
      this.startState = param1Int;
    }
    
    public void setTransitionState(Bundle param1Bundle) {
      this.mProgress = param1Bundle.getFloat("motion.progress");
      this.mVelocity = param1Bundle.getFloat("motion.velocity");
      this.startState = param1Bundle.getInt("motion.StartState");
      this.endState = param1Bundle.getInt("motion.EndState");
    }
    
    public void setVelocity(float param1Float) {
      this.mVelocity = param1Float;
    }
  }
  
  public static interface TransitionListener {
    void onTransitionChange(MotionLayout param1MotionLayout, int param1Int1, int param1Int2, float param1Float);
    
    void onTransitionCompleted(MotionLayout param1MotionLayout, int param1Int);
    
    void onTransitionStarted(MotionLayout param1MotionLayout, int param1Int1, int param1Int2);
    
    void onTransitionTrigger(MotionLayout param1MotionLayout, int param1Int, boolean param1Boolean, float param1Float);
  }
  
  enum TransitionState {
    FINISHED, MOVING, SETUP, UNDEFINED;
    
    private static final TransitionState[] $VALUES;
    
    static {
      MOVING = new TransitionState("MOVING", 2);
      TransitionState transitionState = new TransitionState("FINISHED", 3);
      FINISHED = transitionState;
      $VALUES = new TransitionState[] { UNDEFINED, SETUP, MOVING, transitionState };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\MotionLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */