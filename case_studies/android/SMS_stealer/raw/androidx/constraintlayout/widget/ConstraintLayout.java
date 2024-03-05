package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout extends ViewGroup {
  private static final boolean DEBUG = false;
  
  private static final boolean DEBUG_DRAW_CONSTRAINTS = false;
  
  public static final int DESIGN_INFO_ID = 0;
  
  private static final boolean MEASURE = false;
  
  private static final String TAG = "ConstraintLayout";
  
  private static final boolean USE_CONSTRAINTS_HELPER = true;
  
  public static final String VERSION = "ConstraintLayout-2.0.4";
  
  SparseArray<View> mChildrenByIds = new SparseArray();
  
  private ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList<ConstraintHelper>(4);
  
  protected ConstraintLayoutStates mConstraintLayoutSpec = null;
  
  private ConstraintSet mConstraintSet = null;
  
  private int mConstraintSetId = -1;
  
  private ConstraintsChangedListener mConstraintsChangedListener;
  
  private HashMap<String, Integer> mDesignIds = new HashMap<String, Integer>();
  
  protected boolean mDirtyHierarchy = true;
  
  private int mLastMeasureHeight = -1;
  
  int mLastMeasureHeightMode = 0;
  
  int mLastMeasureHeightSize = -1;
  
  private int mLastMeasureWidth = -1;
  
  int mLastMeasureWidthMode = 0;
  
  int mLastMeasureWidthSize = -1;
  
  protected ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
  
  private int mMaxHeight = Integer.MAX_VALUE;
  
  private int mMaxWidth = Integer.MAX_VALUE;
  
  Measurer mMeasurer = new Measurer(this);
  
  private Metrics mMetrics;
  
  private int mMinHeight = 0;
  
  private int mMinWidth = 0;
  
  private int mOnMeasureHeightMeasureSpec = 0;
  
  private int mOnMeasureWidthMeasureSpec = 0;
  
  private int mOptimizationLevel = 257;
  
  private SparseArray<ConstraintWidget> mTempMapIdToWidget = new SparseArray();
  
  public ConstraintLayout(Context paramContext) {
    super(paramContext);
    init((AttributeSet)null, 0, 0);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet, 0, 0);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet, paramInt, 0);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramAttributeSet, paramInt1, paramInt2);
  }
  
  private int getPaddingWidth() {
    int j = getPaddingLeft();
    int i = 0;
    j = Math.max(0, j) + Math.max(0, getPaddingRight());
    if (Build.VERSION.SDK_INT >= 17) {
      i = Math.max(0, getPaddingStart());
      i = Math.max(0, getPaddingEnd()) + i;
    } 
    if (i > 0)
      j = i; 
    return j;
  }
  
  private final ConstraintWidget getTargetWidget(int paramInt) {
    ConstraintWidget constraintWidget;
    if (paramInt == 0)
      return (ConstraintWidget)this.mLayoutWidget; 
    View view2 = (View)this.mChildrenByIds.get(paramInt);
    View view1 = view2;
    if (view2 == null) {
      view2 = findViewById(paramInt);
      view1 = view2;
      if (view2 != null) {
        view1 = view2;
        if (view2 != this) {
          view1 = view2;
          if (view2.getParent() == this) {
            onViewAdded(view2);
            view1 = view2;
          } 
        } 
      } 
    } 
    if (view1 == this)
      return (ConstraintWidget)this.mLayoutWidget; 
    if (view1 == null) {
      view1 = null;
    } else {
      constraintWidget = ((LayoutParams)view1.getLayoutParams()).widget;
    } 
    return constraintWidget;
  }
  
  private void init(AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    this.mLayoutWidget.setCompanionWidget(this);
    this.mLayoutWidget.setMeasurer(this.mMeasurer);
    this.mChildrenByIds.put(getId(), this);
    this.mConstraintSet = null;
    if (paramAttributeSet != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout, paramInt1, paramInt2);
      paramInt2 = typedArray.getIndexCount();
      for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
        int i = typedArray.getIndex(paramInt1);
        if (i == R.styleable.ConstraintLayout_Layout_android_minWidth) {
          this.mMinWidth = typedArray.getDimensionPixelOffset(i, this.mMinWidth);
        } else if (i == R.styleable.ConstraintLayout_Layout_android_minHeight) {
          this.mMinHeight = typedArray.getDimensionPixelOffset(i, this.mMinHeight);
        } else if (i == R.styleable.ConstraintLayout_Layout_android_maxWidth) {
          this.mMaxWidth = typedArray.getDimensionPixelOffset(i, this.mMaxWidth);
        } else if (i == R.styleable.ConstraintLayout_Layout_android_maxHeight) {
          this.mMaxHeight = typedArray.getDimensionPixelOffset(i, this.mMaxHeight);
        } else if (i == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
          this.mOptimizationLevel = typedArray.getInt(i, this.mOptimizationLevel);
        } else if (i == R.styleable.ConstraintLayout_Layout_layoutDescription) {
          i = typedArray.getResourceId(i, 0);
          if (i != 0)
            try {
              parseLayoutDescription(i);
            } catch (android.content.res.Resources.NotFoundException notFoundException) {
              this.mConstraintLayoutSpec = null;
            }  
        } else if (i == R.styleable.ConstraintLayout_Layout_constraintSet) {
          i = typedArray.getResourceId(i, 0);
          try {
            ConstraintSet constraintSet = new ConstraintSet();
            this();
            this.mConstraintSet = constraintSet;
            constraintSet.load(getContext(), i);
          } catch (android.content.res.Resources.NotFoundException notFoundException) {
            this.mConstraintSet = null;
          } 
          this.mConstraintSetId = i;
        } 
      } 
      typedArray.recycle();
    } 
    this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
  }
  
  private void markHierarchyDirty() {
    this.mDirtyHierarchy = true;
    this.mLastMeasureWidth = -1;
    this.mLastMeasureHeight = -1;
    this.mLastMeasureWidthSize = -1;
    this.mLastMeasureHeightSize = -1;
    this.mLastMeasureWidthMode = 0;
    this.mLastMeasureHeightMode = 0;
  }
  
  private void setChildrenConstraints() {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual isInEditMode : ()Z
    //   4: istore #4
    //   6: aload_0
    //   7: invokevirtual getChildCount : ()I
    //   10: istore_2
    //   11: iconst_0
    //   12: istore_1
    //   13: iload_1
    //   14: iload_2
    //   15: if_icmpge -> 48
    //   18: aload_0
    //   19: aload_0
    //   20: iload_1
    //   21: invokevirtual getChildAt : (I)Landroid/view/View;
    //   24: invokevirtual getViewWidget : (Landroid/view/View;)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   27: astore #5
    //   29: aload #5
    //   31: ifnonnull -> 37
    //   34: goto -> 42
    //   37: aload #5
    //   39: invokevirtual reset : ()V
    //   42: iinc #1, 1
    //   45: goto -> 13
    //   48: iload #4
    //   50: ifeq -> 143
    //   53: iconst_0
    //   54: istore_1
    //   55: iload_1
    //   56: iload_2
    //   57: if_icmpge -> 143
    //   60: aload_0
    //   61: iload_1
    //   62: invokevirtual getChildAt : (I)Landroid/view/View;
    //   65: astore #7
    //   67: aload_0
    //   68: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   71: aload #7
    //   73: invokevirtual getId : ()I
    //   76: invokevirtual getResourceName : (I)Ljava/lang/String;
    //   79: astore #6
    //   81: aload_0
    //   82: iconst_0
    //   83: aload #6
    //   85: aload #7
    //   87: invokevirtual getId : ()I
    //   90: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   93: invokevirtual setDesignInformation : (ILjava/lang/Object;Ljava/lang/Object;)V
    //   96: aload #6
    //   98: bipush #47
    //   100: invokevirtual indexOf : (I)I
    //   103: istore_3
    //   104: aload #6
    //   106: astore #5
    //   108: iload_3
    //   109: iconst_m1
    //   110: if_icmpeq -> 123
    //   113: aload #6
    //   115: iload_3
    //   116: iconst_1
    //   117: iadd
    //   118: invokevirtual substring : (I)Ljava/lang/String;
    //   121: astore #5
    //   123: aload_0
    //   124: aload #7
    //   126: invokevirtual getId : ()I
    //   129: invokespecial getTargetWidget : (I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   132: aload #5
    //   134: invokevirtual setDebugName : (Ljava/lang/String;)V
    //   137: iinc #1, 1
    //   140: goto -> 55
    //   143: aload_0
    //   144: getfield mConstraintSetId : I
    //   147: iconst_m1
    //   148: if_icmpeq -> 203
    //   151: iconst_0
    //   152: istore_1
    //   153: iload_1
    //   154: iload_2
    //   155: if_icmpge -> 203
    //   158: aload_0
    //   159: iload_1
    //   160: invokevirtual getChildAt : (I)Landroid/view/View;
    //   163: astore #5
    //   165: aload #5
    //   167: invokevirtual getId : ()I
    //   170: aload_0
    //   171: getfield mConstraintSetId : I
    //   174: if_icmpne -> 197
    //   177: aload #5
    //   179: instanceof androidx/constraintlayout/widget/Constraints
    //   182: ifeq -> 197
    //   185: aload_0
    //   186: aload #5
    //   188: checkcast androidx/constraintlayout/widget/Constraints
    //   191: invokevirtual getConstraintSet : ()Landroidx/constraintlayout/widget/ConstraintSet;
    //   194: putfield mConstraintSet : Landroidx/constraintlayout/widget/ConstraintSet;
    //   197: iinc #1, 1
    //   200: goto -> 153
    //   203: aload_0
    //   204: getfield mConstraintSet : Landroidx/constraintlayout/widget/ConstraintSet;
    //   207: astore #5
    //   209: aload #5
    //   211: ifnull -> 221
    //   214: aload #5
    //   216: aload_0
    //   217: iconst_1
    //   218: invokevirtual applyToInternal : (Landroidx/constraintlayout/widget/ConstraintLayout;Z)V
    //   221: aload_0
    //   222: getfield mLayoutWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   225: invokevirtual removeAllChildren : ()V
    //   228: aload_0
    //   229: getfield mConstraintHelpers : Ljava/util/ArrayList;
    //   232: invokevirtual size : ()I
    //   235: istore_3
    //   236: iload_3
    //   237: ifle -> 268
    //   240: iconst_0
    //   241: istore_1
    //   242: iload_1
    //   243: iload_3
    //   244: if_icmpge -> 268
    //   247: aload_0
    //   248: getfield mConstraintHelpers : Ljava/util/ArrayList;
    //   251: iload_1
    //   252: invokevirtual get : (I)Ljava/lang/Object;
    //   255: checkcast androidx/constraintlayout/widget/ConstraintHelper
    //   258: aload_0
    //   259: invokevirtual updatePreLayout : (Landroidx/constraintlayout/widget/ConstraintLayout;)V
    //   262: iinc #1, 1
    //   265: goto -> 242
    //   268: iconst_0
    //   269: istore_1
    //   270: iload_1
    //   271: iload_2
    //   272: if_icmpge -> 305
    //   275: aload_0
    //   276: iload_1
    //   277: invokevirtual getChildAt : (I)Landroid/view/View;
    //   280: astore #5
    //   282: aload #5
    //   284: instanceof androidx/constraintlayout/widget/Placeholder
    //   287: ifeq -> 299
    //   290: aload #5
    //   292: checkcast androidx/constraintlayout/widget/Placeholder
    //   295: aload_0
    //   296: invokevirtual updatePreLayout : (Landroidx/constraintlayout/widget/ConstraintLayout;)V
    //   299: iinc #1, 1
    //   302: goto -> 270
    //   305: aload_0
    //   306: getfield mTempMapIdToWidget : Landroid/util/SparseArray;
    //   309: invokevirtual clear : ()V
    //   312: aload_0
    //   313: getfield mTempMapIdToWidget : Landroid/util/SparseArray;
    //   316: iconst_0
    //   317: aload_0
    //   318: getfield mLayoutWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   321: invokevirtual put : (ILjava/lang/Object;)V
    //   324: aload_0
    //   325: getfield mTempMapIdToWidget : Landroid/util/SparseArray;
    //   328: aload_0
    //   329: invokevirtual getId : ()I
    //   332: aload_0
    //   333: getfield mLayoutWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   336: invokevirtual put : (ILjava/lang/Object;)V
    //   339: iconst_0
    //   340: istore_1
    //   341: iload_1
    //   342: iload_2
    //   343: if_icmpge -> 381
    //   346: aload_0
    //   347: iload_1
    //   348: invokevirtual getChildAt : (I)Landroid/view/View;
    //   351: astore #5
    //   353: aload_0
    //   354: aload #5
    //   356: invokevirtual getViewWidget : (Landroid/view/View;)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   359: astore #6
    //   361: aload_0
    //   362: getfield mTempMapIdToWidget : Landroid/util/SparseArray;
    //   365: aload #5
    //   367: invokevirtual getId : ()I
    //   370: aload #6
    //   372: invokevirtual put : (ILjava/lang/Object;)V
    //   375: iinc #1, 1
    //   378: goto -> 341
    //   381: iconst_0
    //   382: istore_1
    //   383: iload_1
    //   384: iload_2
    //   385: if_icmpge -> 452
    //   388: aload_0
    //   389: iload_1
    //   390: invokevirtual getChildAt : (I)Landroid/view/View;
    //   393: astore #7
    //   395: aload_0
    //   396: aload #7
    //   398: invokevirtual getViewWidget : (Landroid/view/View;)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   401: astore #6
    //   403: aload #6
    //   405: ifnonnull -> 411
    //   408: goto -> 446
    //   411: aload #7
    //   413: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   416: checkcast androidx/constraintlayout/widget/ConstraintLayout$LayoutParams
    //   419: astore #5
    //   421: aload_0
    //   422: getfield mLayoutWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   425: aload #6
    //   427: invokevirtual add : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;)V
    //   430: aload_0
    //   431: iload #4
    //   433: aload #7
    //   435: aload #6
    //   437: aload #5
    //   439: aload_0
    //   440: getfield mTempMapIdToWidget : Landroid/util/SparseArray;
    //   443: invokevirtual applyConstraintsFromLayoutParams : (ZLandroid/view/View;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;Landroid/util/SparseArray;)V
    //   446: iinc #1, 1
    //   449: goto -> 383
    //   452: return
    //   453: astore #5
    //   455: goto -> 137
    // Exception table:
    //   from	to	target	type
    //   67	104	453	android/content/res/Resources$NotFoundException
    //   113	123	453	android/content/res/Resources$NotFoundException
    //   123	137	453	android/content/res/Resources$NotFoundException
  }
  
  private boolean updateHierarchy() {
    boolean bool1;
    int i = getChildCount();
    boolean bool2 = false;
    byte b = 0;
    while (true) {
      bool1 = bool2;
      if (b < i) {
        if (getChildAt(b).isLayoutRequested()) {
          bool1 = true;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    if (bool1)
      setChildrenConstraints(); 
    return bool1;
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (Build.VERSION.SDK_INT < 14)
      onViewAdded(paramView); 
  }
  
  protected void applyConstraintsFromLayoutParams(boolean paramBoolean, View paramView, ConstraintWidget paramConstraintWidget, LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray) {
    paramLayoutParams.validate();
    paramLayoutParams.helped = false;
    paramConstraintWidget.setVisibility(paramView.getVisibility());
    if (paramLayoutParams.isInPlaceholder) {
      paramConstraintWidget.setInPlaceholder(true);
      paramConstraintWidget.setVisibility(8);
    } 
    paramConstraintWidget.setCompanionWidget(paramView);
    if (paramView instanceof ConstraintHelper)
      ((ConstraintHelper)paramView).resolveRtl(paramConstraintWidget, this.mLayoutWidget.isRtl()); 
    if (paramLayoutParams.isGuideline) {
      Guideline guideline = (Guideline)paramConstraintWidget;
      int i = paramLayoutParams.resolvedGuideBegin;
      int j = paramLayoutParams.resolvedGuideEnd;
      float f = paramLayoutParams.resolvedGuidePercent;
      if (Build.VERSION.SDK_INT < 17) {
        i = paramLayoutParams.guideBegin;
        j = paramLayoutParams.guideEnd;
        f = paramLayoutParams.guidePercent;
      } 
      if (f != -1.0F) {
        guideline.setGuidePercent(f);
      } else if (i != -1) {
        guideline.setGuideBegin(i);
      } else if (j != -1) {
        guideline.setGuideEnd(j);
      } 
    } else {
      int i1 = paramLayoutParams.resolvedLeftToLeft;
      int j = paramLayoutParams.resolvedLeftToRight;
      int m = paramLayoutParams.resolvedRightToLeft;
      int k = paramLayoutParams.resolvedRightToRight;
      int i = paramLayoutParams.resolveGoneLeftMargin;
      int n = paramLayoutParams.resolveGoneRightMargin;
      float f = paramLayoutParams.resolvedHorizontalBias;
      if (Build.VERSION.SDK_INT < 17) {
        k = paramLayoutParams.leftToLeft;
        m = paramLayoutParams.leftToRight;
        int i3 = paramLayoutParams.rightToLeft;
        i1 = paramLayoutParams.rightToRight;
        int i2 = paramLayoutParams.goneLeftMargin;
        n = paramLayoutParams.goneRightMargin;
        f = paramLayoutParams.horizontalBias;
        i = k;
        j = m;
        if (k == -1) {
          i = k;
          j = m;
          if (m == -1)
            if (paramLayoutParams.startToStart != -1) {
              i = paramLayoutParams.startToStart;
              j = m;
            } else {
              i = k;
              j = m;
              if (paramLayoutParams.startToEnd != -1) {
                j = paramLayoutParams.startToEnd;
                i = k;
              } 
            }  
        } 
        m = i3;
        k = i1;
        if (i3 == -1) {
          m = i3;
          k = i1;
          if (i1 == -1)
            if (paramLayoutParams.endToStart != -1) {
              m = paramLayoutParams.endToStart;
              k = i1;
            } else {
              m = i3;
              k = i1;
              if (paramLayoutParams.endToEnd != -1) {
                k = paramLayoutParams.endToEnd;
                m = i3;
              } 
            }  
        } 
        i1 = i;
        i = i2;
      } 
      if (paramLayoutParams.circleConstraint != -1) {
        ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.circleConstraint);
        if (constraintWidget != null)
          paramConstraintWidget.connectCircularConstraint(constraintWidget, paramLayoutParams.circleAngle, paramLayoutParams.circleRadius); 
      } else {
        if (i1 != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(i1);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, constraintWidget, ConstraintAnchor.Type.LEFT, paramLayoutParams.leftMargin, i); 
        } else if (j != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(j);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, constraintWidget, ConstraintAnchor.Type.RIGHT, paramLayoutParams.leftMargin, i); 
        } 
        if (m != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(m);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, constraintWidget, ConstraintAnchor.Type.LEFT, paramLayoutParams.rightMargin, n); 
        } else if (k != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(k);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, constraintWidget, ConstraintAnchor.Type.RIGHT, paramLayoutParams.rightMargin, n); 
        } 
        if (paramLayoutParams.topToTop != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.topToTop);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, constraintWidget, ConstraintAnchor.Type.TOP, paramLayoutParams.topMargin, paramLayoutParams.goneTopMargin); 
        } else if (paramLayoutParams.topToBottom != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.topToBottom);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, constraintWidget, ConstraintAnchor.Type.BOTTOM, paramLayoutParams.topMargin, paramLayoutParams.goneTopMargin); 
        } 
        if (paramLayoutParams.bottomToTop != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.bottomToTop);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, constraintWidget, ConstraintAnchor.Type.TOP, paramLayoutParams.bottomMargin, paramLayoutParams.goneBottomMargin); 
        } else if (paramLayoutParams.bottomToBottom != -1) {
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.bottomToBottom);
          if (constraintWidget != null)
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, constraintWidget, ConstraintAnchor.Type.BOTTOM, paramLayoutParams.bottomMargin, paramLayoutParams.goneBottomMargin); 
        } 
        if (paramLayoutParams.baselineToBaseline != -1) {
          paramView = (View)this.mChildrenByIds.get(paramLayoutParams.baselineToBaseline);
          ConstraintWidget constraintWidget = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.baselineToBaseline);
          if (constraintWidget != null && paramView != null && paramView.getLayoutParams() instanceof LayoutParams) {
            LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
            paramLayoutParams.needsBaseline = true;
            layoutParams.needsBaseline = true;
            paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE), 0, -1, true);
            paramConstraintWidget.setHasBaseline(true);
            layoutParams.widget.setHasBaseline(true);
            paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP).reset();
            paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
          } 
        } 
        if (f >= 0.0F)
          paramConstraintWidget.setHorizontalBiasPercent(f); 
        if (paramLayoutParams.verticalBias >= 0.0F)
          paramConstraintWidget.setVerticalBiasPercent(paramLayoutParams.verticalBias); 
      } 
      if (paramBoolean && (paramLayoutParams.editorAbsoluteX != -1 || paramLayoutParams.editorAbsoluteY != -1))
        paramConstraintWidget.setOrigin(paramLayoutParams.editorAbsoluteX, paramLayoutParams.editorAbsoluteY); 
      if (!paramLayoutParams.horizontalDimensionFixed) {
        if (paramLayoutParams.width == -1) {
          if (paramLayoutParams.constrainedWidth) {
            paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          } else {
            paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
          } 
          (paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT)).mMargin = paramLayoutParams.leftMargin;
          (paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT)).mMargin = paramLayoutParams.rightMargin;
        } else {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          paramConstraintWidget.setWidth(0);
        } 
      } else {
        paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidget.setWidth(paramLayoutParams.width);
        if (paramLayoutParams.width == -2)
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT); 
      } 
      if (!paramLayoutParams.verticalDimensionFixed) {
        if (paramLayoutParams.height == -1) {
          if (paramLayoutParams.constrainedHeight) {
            paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          } else {
            paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
          } 
          (paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP)).mMargin = paramLayoutParams.topMargin;
          (paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM)).mMargin = paramLayoutParams.bottomMargin;
        } else {
          paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          paramConstraintWidget.setHeight(0);
        } 
      } else {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidget.setHeight(paramLayoutParams.height);
        if (paramLayoutParams.height == -2)
          paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT); 
      } 
      paramConstraintWidget.setDimensionRatio(paramLayoutParams.dimensionRatio);
      paramConstraintWidget.setHorizontalWeight(paramLayoutParams.horizontalWeight);
      paramConstraintWidget.setVerticalWeight(paramLayoutParams.verticalWeight);
      paramConstraintWidget.setHorizontalChainStyle(paramLayoutParams.horizontalChainStyle);
      paramConstraintWidget.setVerticalChainStyle(paramLayoutParams.verticalChainStyle);
      paramConstraintWidget.setHorizontalMatchStyle(paramLayoutParams.matchConstraintDefaultWidth, paramLayoutParams.matchConstraintMinWidth, paramLayoutParams.matchConstraintMaxWidth, paramLayoutParams.matchConstraintPercentWidth);
      paramConstraintWidget.setVerticalMatchStyle(paramLayoutParams.matchConstraintDefaultHeight, paramLayoutParams.matchConstraintMinHeight, paramLayoutParams.matchConstraintMaxHeight, paramLayoutParams.matchConstraintPercentHeight);
    } 
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {
    ArrayList<ConstraintHelper> arrayList = this.mConstraintHelpers;
    if (arrayList != null) {
      int i = arrayList.size();
      if (i > 0)
        for (byte b = 0; b < i; b++)
          ((ConstraintHelper)this.mConstraintHelpers.get(b)).updatePreDraw(this);  
    } 
    super.dispatchDraw(paramCanvas);
    if (isInEditMode()) {
      int i = getChildCount();
      float f1 = getWidth();
      float f2 = getHeight();
      for (byte b = 0; b < i; b++) {
        View view = getChildAt(b);
        if (view.getVisibility() != 8) {
          Object object = view.getTag();
          if (object != null && object instanceof String) {
            object = ((String)object).split(",");
            if (object.length == 4) {
              int k = Integer.parseInt((String)object[0]);
              int n = Integer.parseInt((String)object[1]);
              int m = Integer.parseInt((String)object[2]);
              int j = Integer.parseInt((String)object[3]);
              k = (int)(k / 1080.0F * f1);
              n = (int)(n / 1920.0F * f2);
              m = (int)(m / 1080.0F * f1);
              j = (int)(j / 1920.0F * f2);
              object = new Paint();
              object.setColor(-65536);
              float f5 = k;
              float f6 = n;
              float f3 = (k + m);
              paramCanvas.drawLine(f5, f6, f3, f6, (Paint)object);
              float f4 = (n + j);
              paramCanvas.drawLine(f3, f6, f3, f4, (Paint)object);
              paramCanvas.drawLine(f3, f4, f5, f4, (Paint)object);
              paramCanvas.drawLine(f5, f4, f5, f6, (Paint)object);
              object.setColor(-16711936);
              paramCanvas.drawLine(f5, f6, f3, f4, (Paint)object);
              paramCanvas.drawLine(f5, f4, f3, f6, (Paint)object);
            } 
          } 
        } 
      } 
    } 
  }
  
  public void fillMetrics(Metrics paramMetrics) {
    this.mMetrics = paramMetrics;
    this.mLayoutWidget.fillMetrics(paramMetrics);
  }
  
  public void forceLayout() {
    markHierarchyDirty();
    super.forceLayout();
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-2, -2);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (ViewGroup.LayoutParams)new LayoutParams(paramLayoutParams);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  public Object getDesignInformation(int paramInt, Object<String, Integer> paramObject) {
    if (paramInt == 0 && paramObject instanceof String) {
      String str = (String)paramObject;
      paramObject = (Object<String, Integer>)this.mDesignIds;
      if (paramObject != null && paramObject.containsKey(str))
        return this.mDesignIds.get(str); 
    } 
    return null;
  }
  
  public int getMaxHeight() {
    return this.mMaxHeight;
  }
  
  public int getMaxWidth() {
    return this.mMaxWidth;
  }
  
  public int getMinHeight() {
    return this.mMinHeight;
  }
  
  public int getMinWidth() {
    return this.mMinWidth;
  }
  
  public int getOptimizationLevel() {
    return this.mLayoutWidget.getOptimizationLevel();
  }
  
  public View getViewById(int paramInt) {
    return (View)this.mChildrenByIds.get(paramInt);
  }
  
  public final ConstraintWidget getViewWidget(View paramView) {
    ConstraintWidget constraintWidget;
    if (paramView == this)
      return (ConstraintWidget)this.mLayoutWidget; 
    if (paramView == null) {
      paramView = null;
    } else {
      constraintWidget = ((LayoutParams)paramView.getLayoutParams()).widget;
    } 
    return constraintWidget;
  }
  
  protected boolean isRtl() {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (i >= 17) {
      if (((getContext().getApplicationInfo()).flags & 0x400000) != 0) {
        i = 1;
      } else {
        i = 0;
      } 
      bool1 = bool2;
      if (i != 0) {
        bool1 = bool2;
        if (1 == getLayoutDirection())
          bool1 = true; 
      } 
    } 
    return bool1;
  }
  
  public void loadLayoutDescription(int paramInt) {
    if (paramInt != 0) {
      try {
        ConstraintLayoutStates constraintLayoutStates = new ConstraintLayoutStates();
        this(getContext(), this, paramInt);
        this.mConstraintLayoutSpec = constraintLayoutStates;
      } catch (android.content.res.Resources.NotFoundException notFoundException) {
        this.mConstraintLayoutSpec = null;
      } 
    } else {
      this.mConstraintLayoutSpec = null;
    } 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt3 = getChildCount();
    paramBoolean = isInEditMode();
    paramInt2 = 0;
    for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++) {
      View view = getChildAt(paramInt1);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      ConstraintWidget constraintWidget = layoutParams.widget;
      if ((view.getVisibility() != 8 || layoutParams.isGuideline || layoutParams.isHelper || layoutParams.isVirtualGroup || paramBoolean) && !layoutParams.isInPlaceholder) {
        int i = constraintWidget.getX();
        int j = constraintWidget.getY();
        paramInt4 = constraintWidget.getWidth() + i;
        int k = constraintWidget.getHeight() + j;
        view.layout(i, j, paramInt4, k);
        if (view instanceof Placeholder) {
          View view1 = ((Placeholder)view).getContent();
          if (view1 != null) {
            view1.setVisibility(0);
            view1.layout(i, j, paramInt4, k);
          } 
        } 
      } 
    } 
    paramInt3 = this.mConstraintHelpers.size();
    if (paramInt3 > 0)
      for (paramInt1 = paramInt2; paramInt1 < paramInt3; paramInt1++)
        ((ConstraintHelper)this.mConstraintHelpers.get(paramInt1)).updatePostLayout(this);  
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    if (!this.mDirtyHierarchy) {
      int i = getChildCount();
      for (byte b = 0; b < i; b++) {
        if (getChildAt(b).isLayoutRequested()) {
          this.mDirtyHierarchy = true;
          break;
        } 
      } 
    } 
    if (!this.mDirtyHierarchy) {
      if (this.mOnMeasureWidthMeasureSpec == paramInt1 && this.mOnMeasureHeightMeasureSpec == paramInt2) {
        resolveMeasuredDimension(paramInt1, paramInt2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
        return;
      } 
      if (this.mOnMeasureWidthMeasureSpec == paramInt1 && View.MeasureSpec.getMode(paramInt1) == 1073741824 && View.MeasureSpec.getMode(paramInt2) == Integer.MIN_VALUE && View.MeasureSpec.getMode(this.mOnMeasureHeightMeasureSpec) == Integer.MIN_VALUE && View.MeasureSpec.getSize(paramInt2) >= this.mLayoutWidget.getHeight()) {
        this.mOnMeasureWidthMeasureSpec = paramInt1;
        this.mOnMeasureHeightMeasureSpec = paramInt2;
        resolveMeasuredDimension(paramInt1, paramInt2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
        return;
      } 
    } 
    this.mOnMeasureWidthMeasureSpec = paramInt1;
    this.mOnMeasureHeightMeasureSpec = paramInt2;
    this.mLayoutWidget.setRtl(isRtl());
    if (this.mDirtyHierarchy) {
      this.mDirtyHierarchy = false;
      if (updateHierarchy())
        this.mLayoutWidget.updateHierarchy(); 
    } 
    resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, paramInt1, paramInt2);
    resolveMeasuredDimension(paramInt1, paramInt2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
  }
  
  public void onViewAdded(View paramView) {
    if (Build.VERSION.SDK_INT >= 14)
      super.onViewAdded(paramView); 
    ConstraintWidget constraintWidget = getViewWidget(paramView);
    if (paramView instanceof Guideline && !(constraintWidget instanceof Guideline)) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      layoutParams.widget = (ConstraintWidget)new Guideline();
      layoutParams.isGuideline = true;
      ((Guideline)layoutParams.widget).setOrientation(layoutParams.orientation);
    } 
    if (paramView instanceof ConstraintHelper) {
      ConstraintHelper constraintHelper = (ConstraintHelper)paramView;
      constraintHelper.validateParams();
      ((LayoutParams)paramView.getLayoutParams()).isHelper = true;
      if (!this.mConstraintHelpers.contains(constraintHelper))
        this.mConstraintHelpers.add(constraintHelper); 
    } 
    this.mChildrenByIds.put(paramView.getId(), paramView);
    this.mDirtyHierarchy = true;
  }
  
  public void onViewRemoved(View paramView) {
    if (Build.VERSION.SDK_INT >= 14)
      super.onViewRemoved(paramView); 
    this.mChildrenByIds.remove(paramView.getId());
    ConstraintWidget constraintWidget = getViewWidget(paramView);
    this.mLayoutWidget.remove(constraintWidget);
    this.mConstraintHelpers.remove(paramView);
    this.mDirtyHierarchy = true;
  }
  
  protected void parseLayoutDescription(int paramInt) {
    this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, paramInt);
  }
  
  public void removeView(View paramView) {
    super.removeView(paramView);
    if (Build.VERSION.SDK_INT < 14)
      onViewRemoved(paramView); 
  }
  
  public void requestLayout() {
    markHierarchyDirty();
    super.requestLayout();
  }
  
  protected void resolveMeasuredDimension(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2) {
    int i = this.mMeasurer.paddingHeight;
    paramInt3 += this.mMeasurer.paddingWidth;
    paramInt4 += i;
    if (Build.VERSION.SDK_INT >= 11) {
      paramInt1 = resolveSizeAndState(paramInt3, paramInt1, 0);
      paramInt3 = resolveSizeAndState(paramInt4, paramInt2, 0);
      paramInt2 = Math.min(this.mMaxWidth, paramInt1 & 0xFFFFFF);
      paramInt3 = Math.min(this.mMaxHeight, paramInt3 & 0xFFFFFF);
      paramInt1 = paramInt2;
      if (paramBoolean1)
        paramInt1 = paramInt2 | 0x1000000; 
      paramInt2 = paramInt3;
      if (paramBoolean2)
        paramInt2 = paramInt3 | 0x1000000; 
      setMeasuredDimension(paramInt1, paramInt2);
      this.mLastMeasureWidth = paramInt1;
      this.mLastMeasureHeight = paramInt2;
    } else {
      setMeasuredDimension(paramInt3, paramInt4);
      this.mLastMeasureWidth = paramInt3;
      this.mLastMeasureHeight = paramInt4;
    } 
  }
  
  protected void resolveSystem(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt1, int paramInt2, int paramInt3) {
    int j = View.MeasureSpec.getMode(paramInt2);
    int i1 = View.MeasureSpec.getSize(paramInt2);
    int i = View.MeasureSpec.getMode(paramInt3);
    int n = View.MeasureSpec.getSize(paramInt3);
    int k = Math.max(0, getPaddingTop());
    int i3 = Math.max(0, getPaddingBottom());
    int m = k + i3;
    int i2 = getPaddingWidth();
    this.mMeasurer.captureLayoutInfos(paramInt2, paramInt3, k, i3, i2, m);
    if (Build.VERSION.SDK_INT >= 17) {
      paramInt2 = Math.max(0, getPaddingStart());
      paramInt3 = Math.max(0, getPaddingEnd());
      if (paramInt2 > 0 || paramInt3 > 0) {
        if (isRtl())
          paramInt2 = paramInt3; 
      } else {
        paramInt2 = Math.max(0, getPaddingLeft());
      } 
    } else {
      paramInt2 = Math.max(0, getPaddingLeft());
    } 
    paramInt3 = i1 - i2;
    m = n - m;
    setSelfDimensionBehaviour(paramConstraintWidgetContainer, j, paramInt3, i, m);
    paramConstraintWidgetContainer.measure(paramInt1, j, paramInt3, i, m, this.mLastMeasureWidth, this.mLastMeasureHeight, paramInt2, k);
  }
  
  public void setConstraintSet(ConstraintSet paramConstraintSet) {
    this.mConstraintSet = paramConstraintSet;
  }
  
  public void setDesignInformation(int paramInt, Object paramObject1, Object paramObject2) {
    if (paramInt == 0 && paramObject1 instanceof String && paramObject2 instanceof Integer) {
      if (this.mDesignIds == null)
        this.mDesignIds = new HashMap<String, Integer>(); 
      String str = (String)paramObject1;
      paramInt = str.indexOf("/");
      paramObject1 = str;
      if (paramInt != -1)
        paramObject1 = str.substring(paramInt + 1); 
      paramInt = ((Integer)paramObject2).intValue();
      this.mDesignIds.put(paramObject1, Integer.valueOf(paramInt));
    } 
  }
  
  public void setId(int paramInt) {
    this.mChildrenByIds.remove(getId());
    super.setId(paramInt);
    this.mChildrenByIds.put(getId(), this);
  }
  
  public void setMaxHeight(int paramInt) {
    if (paramInt == this.mMaxHeight)
      return; 
    this.mMaxHeight = paramInt;
    requestLayout();
  }
  
  public void setMaxWidth(int paramInt) {
    if (paramInt == this.mMaxWidth)
      return; 
    this.mMaxWidth = paramInt;
    requestLayout();
  }
  
  public void setMinHeight(int paramInt) {
    if (paramInt == this.mMinHeight)
      return; 
    this.mMinHeight = paramInt;
    requestLayout();
  }
  
  public void setMinWidth(int paramInt) {
    if (paramInt == this.mMinWidth)
      return; 
    this.mMinWidth = paramInt;
    requestLayout();
  }
  
  public void setOnConstraintsChanged(ConstraintsChangedListener paramConstraintsChangedListener) {
    this.mConstraintsChangedListener = paramConstraintsChangedListener;
    ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
    if (constraintLayoutStates != null)
      constraintLayoutStates.setOnConstraintsChanged(paramConstraintsChangedListener); 
  }
  
  public void setOptimizationLevel(int paramInt) {
    this.mOptimizationLevel = paramInt;
    this.mLayoutWidget.setOptimizationLevel(paramInt);
  }
  
  protected void setSelfDimensionBehaviour(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mMeasurer : Landroidx/constraintlayout/widget/ConstraintLayout$Measurer;
    //   4: getfield paddingHeight : I
    //   7: istore #7
    //   9: aload_0
    //   10: getfield mMeasurer : Landroidx/constraintlayout/widget/ConstraintLayout$Measurer;
    //   13: getfield paddingWidth : I
    //   16: istore #6
    //   18: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   21: astore #9
    //   23: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   26: astore #10
    //   28: aload_0
    //   29: invokevirtual getChildCount : ()I
    //   32: istore #8
    //   34: iload_2
    //   35: ldc_w -2147483648
    //   38: if_icmpeq -> 102
    //   41: iload_2
    //   42: ifeq -> 72
    //   45: iload_2
    //   46: ldc_w 1073741824
    //   49: if_icmpeq -> 57
    //   52: iconst_0
    //   53: istore_3
    //   54: goto -> 129
    //   57: aload_0
    //   58: getfield mMaxWidth : I
    //   61: iload #6
    //   63: isub
    //   64: iload_3
    //   65: invokestatic min : (II)I
    //   68: istore_3
    //   69: goto -> 129
    //   72: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   75: astore #11
    //   77: aload #11
    //   79: astore #9
    //   81: iload #8
    //   83: ifne -> 52
    //   86: iconst_0
    //   87: aload_0
    //   88: getfield mMinWidth : I
    //   91: invokestatic max : (II)I
    //   94: istore_3
    //   95: aload #11
    //   97: astore #9
    //   99: goto -> 129
    //   102: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   105: astore #11
    //   107: aload #11
    //   109: astore #9
    //   111: iload #8
    //   113: ifne -> 129
    //   116: iconst_0
    //   117: aload_0
    //   118: getfield mMinWidth : I
    //   121: invokestatic max : (II)I
    //   124: istore_3
    //   125: aload #11
    //   127: astore #9
    //   129: iload #4
    //   131: ldc_w -2147483648
    //   134: if_icmpeq -> 204
    //   137: iload #4
    //   139: ifeq -> 173
    //   142: iload #4
    //   144: ldc_w 1073741824
    //   147: if_icmpeq -> 156
    //   150: iconst_0
    //   151: istore #5
    //   153: goto -> 232
    //   156: aload_0
    //   157: getfield mMaxHeight : I
    //   160: iload #7
    //   162: isub
    //   163: iload #5
    //   165: invokestatic min : (II)I
    //   168: istore #5
    //   170: goto -> 232
    //   173: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   176: astore #11
    //   178: aload #11
    //   180: astore #10
    //   182: iload #8
    //   184: ifne -> 150
    //   187: iconst_0
    //   188: aload_0
    //   189: getfield mMinHeight : I
    //   192: invokestatic max : (II)I
    //   195: istore #5
    //   197: aload #11
    //   199: astore #10
    //   201: goto -> 232
    //   204: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   207: astore #11
    //   209: aload #11
    //   211: astore #10
    //   213: iload #8
    //   215: ifne -> 232
    //   218: iconst_0
    //   219: aload_0
    //   220: getfield mMinHeight : I
    //   223: invokestatic max : (II)I
    //   226: istore #5
    //   228: aload #11
    //   230: astore #10
    //   232: iload_3
    //   233: aload_1
    //   234: invokevirtual getWidth : ()I
    //   237: if_icmpne -> 249
    //   240: iload #5
    //   242: aload_1
    //   243: invokevirtual getHeight : ()I
    //   246: if_icmpeq -> 253
    //   249: aload_1
    //   250: invokevirtual invalidateMeasures : ()V
    //   253: aload_1
    //   254: iconst_0
    //   255: invokevirtual setX : (I)V
    //   258: aload_1
    //   259: iconst_0
    //   260: invokevirtual setY : (I)V
    //   263: aload_1
    //   264: aload_0
    //   265: getfield mMaxWidth : I
    //   268: iload #6
    //   270: isub
    //   271: invokevirtual setMaxWidth : (I)V
    //   274: aload_1
    //   275: aload_0
    //   276: getfield mMaxHeight : I
    //   279: iload #7
    //   281: isub
    //   282: invokevirtual setMaxHeight : (I)V
    //   285: aload_1
    //   286: iconst_0
    //   287: invokevirtual setMinWidth : (I)V
    //   290: aload_1
    //   291: iconst_0
    //   292: invokevirtual setMinHeight : (I)V
    //   295: aload_1
    //   296: aload #9
    //   298: invokevirtual setHorizontalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   301: aload_1
    //   302: iload_3
    //   303: invokevirtual setWidth : (I)V
    //   306: aload_1
    //   307: aload #10
    //   309: invokevirtual setVerticalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   312: aload_1
    //   313: iload #5
    //   315: invokevirtual setHeight : (I)V
    //   318: aload_1
    //   319: aload_0
    //   320: getfield mMinWidth : I
    //   323: iload #6
    //   325: isub
    //   326: invokevirtual setMinWidth : (I)V
    //   329: aload_1
    //   330: aload_0
    //   331: getfield mMinHeight : I
    //   334: iload #7
    //   336: isub
    //   337: invokevirtual setMinHeight : (I)V
    //   340: return
  }
  
  public void setState(int paramInt1, int paramInt2, int paramInt3) {
    ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
    if (constraintLayoutStates != null)
      constraintLayoutStates.updateConstraints(paramInt1, paramInt2, paramInt3); 
  }
  
  public boolean shouldDelayChildPressedState() {
    return false;
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    public static final int BASELINE = 5;
    
    public static final int BOTTOM = 4;
    
    public static final int CHAIN_PACKED = 2;
    
    public static final int CHAIN_SPREAD = 0;
    
    public static final int CHAIN_SPREAD_INSIDE = 1;
    
    public static final int END = 7;
    
    public static final int HORIZONTAL = 0;
    
    public static final int LEFT = 1;
    
    public static final int MATCH_CONSTRAINT = 0;
    
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    
    public static final int PARENT_ID = 0;
    
    public static final int RIGHT = 2;
    
    public static final int START = 6;
    
    public static final int TOP = 3;
    
    public static final int UNSET = -1;
    
    public static final int VERTICAL = 1;
    
    public int baselineToBaseline = -1;
    
    public int bottomToBottom = -1;
    
    public int bottomToTop = -1;
    
    public float circleAngle = 0.0F;
    
    public int circleConstraint = -1;
    
    public int circleRadius = 0;
    
    public boolean constrainedHeight = false;
    
    public boolean constrainedWidth = false;
    
    public String constraintTag = null;
    
    public String dimensionRatio = null;
    
    int dimensionRatioSide = 1;
    
    float dimensionRatioValue = 0.0F;
    
    public int editorAbsoluteX = -1;
    
    public int editorAbsoluteY = -1;
    
    public int endToEnd = -1;
    
    public int endToStart = -1;
    
    public int goneBottomMargin = -1;
    
    public int goneEndMargin = -1;
    
    public int goneLeftMargin = -1;
    
    public int goneRightMargin = -1;
    
    public int goneStartMargin = -1;
    
    public int goneTopMargin = -1;
    
    public int guideBegin = -1;
    
    public int guideEnd = -1;
    
    public float guidePercent = -1.0F;
    
    public boolean helped = false;
    
    public float horizontalBias = 0.5F;
    
    public int horizontalChainStyle = 0;
    
    boolean horizontalDimensionFixed = true;
    
    public float horizontalWeight = -1.0F;
    
    boolean isGuideline = false;
    
    boolean isHelper = false;
    
    boolean isInPlaceholder = false;
    
    boolean isVirtualGroup = false;
    
    public int leftToLeft = -1;
    
    public int leftToRight = -1;
    
    public int matchConstraintDefaultHeight = 0;
    
    public int matchConstraintDefaultWidth = 0;
    
    public int matchConstraintMaxHeight = 0;
    
    public int matchConstraintMaxWidth = 0;
    
    public int matchConstraintMinHeight = 0;
    
    public int matchConstraintMinWidth = 0;
    
    public float matchConstraintPercentHeight = 1.0F;
    
    public float matchConstraintPercentWidth = 1.0F;
    
    boolean needsBaseline = false;
    
    public int orientation = -1;
    
    int resolveGoneLeftMargin = -1;
    
    int resolveGoneRightMargin = -1;
    
    int resolvedGuideBegin;
    
    int resolvedGuideEnd;
    
    float resolvedGuidePercent;
    
    float resolvedHorizontalBias = 0.5F;
    
    int resolvedLeftToLeft = -1;
    
    int resolvedLeftToRight = -1;
    
    int resolvedRightToLeft = -1;
    
    int resolvedRightToRight = -1;
    
    public int rightToLeft = -1;
    
    public int rightToRight = -1;
    
    public int startToEnd = -1;
    
    public int startToStart = -1;
    
    public int topToBottom = -1;
    
    public int topToTop = -1;
    
    public float verticalBias = 0.5F;
    
    public int verticalChainStyle = 0;
    
    boolean verticalDimensionFixed = true;
    
    public float verticalWeight = -1.0F;
    
    ConstraintWidget widget = new ConstraintWidget();
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: aload_2
      //   3: invokespecial <init> : (Landroid/content/Context;Landroid/util/AttributeSet;)V
      //   6: aload_0
      //   7: iconst_m1
      //   8: putfield guideBegin : I
      //   11: aload_0
      //   12: iconst_m1
      //   13: putfield guideEnd : I
      //   16: aload_0
      //   17: ldc -1.0
      //   19: putfield guidePercent : F
      //   22: aload_0
      //   23: iconst_m1
      //   24: putfield leftToLeft : I
      //   27: aload_0
      //   28: iconst_m1
      //   29: putfield leftToRight : I
      //   32: aload_0
      //   33: iconst_m1
      //   34: putfield rightToLeft : I
      //   37: aload_0
      //   38: iconst_m1
      //   39: putfield rightToRight : I
      //   42: aload_0
      //   43: iconst_m1
      //   44: putfield topToTop : I
      //   47: aload_0
      //   48: iconst_m1
      //   49: putfield topToBottom : I
      //   52: aload_0
      //   53: iconst_m1
      //   54: putfield bottomToTop : I
      //   57: aload_0
      //   58: iconst_m1
      //   59: putfield bottomToBottom : I
      //   62: aload_0
      //   63: iconst_m1
      //   64: putfield baselineToBaseline : I
      //   67: aload_0
      //   68: iconst_m1
      //   69: putfield circleConstraint : I
      //   72: aload_0
      //   73: iconst_0
      //   74: putfield circleRadius : I
      //   77: aload_0
      //   78: fconst_0
      //   79: putfield circleAngle : F
      //   82: aload_0
      //   83: iconst_m1
      //   84: putfield startToEnd : I
      //   87: aload_0
      //   88: iconst_m1
      //   89: putfield startToStart : I
      //   92: aload_0
      //   93: iconst_m1
      //   94: putfield endToStart : I
      //   97: aload_0
      //   98: iconst_m1
      //   99: putfield endToEnd : I
      //   102: aload_0
      //   103: iconst_m1
      //   104: putfield goneLeftMargin : I
      //   107: aload_0
      //   108: iconst_m1
      //   109: putfield goneTopMargin : I
      //   112: aload_0
      //   113: iconst_m1
      //   114: putfield goneRightMargin : I
      //   117: aload_0
      //   118: iconst_m1
      //   119: putfield goneBottomMargin : I
      //   122: aload_0
      //   123: iconst_m1
      //   124: putfield goneStartMargin : I
      //   127: aload_0
      //   128: iconst_m1
      //   129: putfield goneEndMargin : I
      //   132: aload_0
      //   133: ldc 0.5
      //   135: putfield horizontalBias : F
      //   138: aload_0
      //   139: ldc 0.5
      //   141: putfield verticalBias : F
      //   144: aload_0
      //   145: aconst_null
      //   146: putfield dimensionRatio : Ljava/lang/String;
      //   149: aload_0
      //   150: fconst_0
      //   151: putfield dimensionRatioValue : F
      //   154: aload_0
      //   155: iconst_1
      //   156: putfield dimensionRatioSide : I
      //   159: aload_0
      //   160: ldc -1.0
      //   162: putfield horizontalWeight : F
      //   165: aload_0
      //   166: ldc -1.0
      //   168: putfield verticalWeight : F
      //   171: aload_0
      //   172: iconst_0
      //   173: putfield horizontalChainStyle : I
      //   176: aload_0
      //   177: iconst_0
      //   178: putfield verticalChainStyle : I
      //   181: aload_0
      //   182: iconst_0
      //   183: putfield matchConstraintDefaultWidth : I
      //   186: aload_0
      //   187: iconst_0
      //   188: putfield matchConstraintDefaultHeight : I
      //   191: aload_0
      //   192: iconst_0
      //   193: putfield matchConstraintMinWidth : I
      //   196: aload_0
      //   197: iconst_0
      //   198: putfield matchConstraintMinHeight : I
      //   201: aload_0
      //   202: iconst_0
      //   203: putfield matchConstraintMaxWidth : I
      //   206: aload_0
      //   207: iconst_0
      //   208: putfield matchConstraintMaxHeight : I
      //   211: aload_0
      //   212: fconst_1
      //   213: putfield matchConstraintPercentWidth : F
      //   216: aload_0
      //   217: fconst_1
      //   218: putfield matchConstraintPercentHeight : F
      //   221: aload_0
      //   222: iconst_m1
      //   223: putfield editorAbsoluteX : I
      //   226: aload_0
      //   227: iconst_m1
      //   228: putfield editorAbsoluteY : I
      //   231: aload_0
      //   232: iconst_m1
      //   233: putfield orientation : I
      //   236: aload_0
      //   237: iconst_0
      //   238: putfield constrainedWidth : Z
      //   241: aload_0
      //   242: iconst_0
      //   243: putfield constrainedHeight : Z
      //   246: aload_0
      //   247: aconst_null
      //   248: putfield constraintTag : Ljava/lang/String;
      //   251: aload_0
      //   252: iconst_1
      //   253: putfield horizontalDimensionFixed : Z
      //   256: aload_0
      //   257: iconst_1
      //   258: putfield verticalDimensionFixed : Z
      //   261: aload_0
      //   262: iconst_0
      //   263: putfield needsBaseline : Z
      //   266: aload_0
      //   267: iconst_0
      //   268: putfield isGuideline : Z
      //   271: aload_0
      //   272: iconst_0
      //   273: putfield isHelper : Z
      //   276: aload_0
      //   277: iconst_0
      //   278: putfield isInPlaceholder : Z
      //   281: aload_0
      //   282: iconst_0
      //   283: putfield isVirtualGroup : Z
      //   286: aload_0
      //   287: iconst_m1
      //   288: putfield resolvedLeftToLeft : I
      //   291: aload_0
      //   292: iconst_m1
      //   293: putfield resolvedLeftToRight : I
      //   296: aload_0
      //   297: iconst_m1
      //   298: putfield resolvedRightToLeft : I
      //   301: aload_0
      //   302: iconst_m1
      //   303: putfield resolvedRightToRight : I
      //   306: aload_0
      //   307: iconst_m1
      //   308: putfield resolveGoneLeftMargin : I
      //   311: aload_0
      //   312: iconst_m1
      //   313: putfield resolveGoneRightMargin : I
      //   316: aload_0
      //   317: ldc 0.5
      //   319: putfield resolvedHorizontalBias : F
      //   322: aload_0
      //   323: new androidx/constraintlayout/solver/widgets/ConstraintWidget
      //   326: dup
      //   327: invokespecial <init> : ()V
      //   330: putfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   333: aload_0
      //   334: iconst_0
      //   335: putfield helped : Z
      //   338: aload_1
      //   339: aload_2
      //   340: getstatic androidx/constraintlayout/widget/R$styleable.ConstraintLayout_Layout : [I
      //   343: invokevirtual obtainStyledAttributes : (Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;
      //   346: astore_1
      //   347: aload_1
      //   348: invokevirtual getIndexCount : ()I
      //   351: istore #7
      //   353: iconst_0
      //   354: istore #5
      //   356: iload #5
      //   358: iload #7
      //   360: if_icmpge -> 2094
      //   363: aload_1
      //   364: iload #5
      //   366: invokevirtual getIndex : (I)I
      //   369: istore #6
      //   371: getstatic androidx/constraintlayout/widget/ConstraintLayout$LayoutParams$Table.map : Landroid/util/SparseIntArray;
      //   374: iload #6
      //   376: invokevirtual get : (I)I
      //   379: istore #8
      //   381: iload #8
      //   383: tableswitch default -> 548, 1 -> 2074, 2 -> 2036, 3 -> 2019, 4 -> 1977, 5 -> 1960, 6 -> 1943, 7 -> 1926, 8 -> 1888, 9 -> 1850, 10 -> 1812, 11 -> 1774, 12 -> 1736, 13 -> 1698, 14 -> 1660, 15 -> 1622, 16 -> 1584, 17 -> 1546, 18 -> 1508, 19 -> 1470, 20 -> 1432, 21 -> 1415, 22 -> 1398, 23 -> 1381, 24 -> 1364, 25 -> 1347, 26 -> 1330, 27 -> 1313, 28 -> 1296, 29 -> 1279, 30 -> 1262, 31 -> 1228, 32 -> 1194, 33 -> 1152, 34 -> 1110, 35 -> 1084, 36 -> 1042, 37 -> 1000, 38 -> 974
      //   548: iload #8
      //   550: tableswitch default -> 596, 44 -> 708, 45 -> 691, 46 -> 674, 47 -> 660, 48 -> 646, 49 -> 629, 50 -> 612, 51 -> 599
      //   596: goto -> 2088
      //   599: aload_0
      //   600: aload_1
      //   601: iload #6
      //   603: invokevirtual getString : (I)Ljava/lang/String;
      //   606: putfield constraintTag : Ljava/lang/String;
      //   609: goto -> 2088
      //   612: aload_0
      //   613: aload_1
      //   614: iload #6
      //   616: aload_0
      //   617: getfield editorAbsoluteY : I
      //   620: invokevirtual getDimensionPixelOffset : (II)I
      //   623: putfield editorAbsoluteY : I
      //   626: goto -> 2088
      //   629: aload_0
      //   630: aload_1
      //   631: iload #6
      //   633: aload_0
      //   634: getfield editorAbsoluteX : I
      //   637: invokevirtual getDimensionPixelOffset : (II)I
      //   640: putfield editorAbsoluteX : I
      //   643: goto -> 2088
      //   646: aload_0
      //   647: aload_1
      //   648: iload #6
      //   650: iconst_0
      //   651: invokevirtual getInt : (II)I
      //   654: putfield verticalChainStyle : I
      //   657: goto -> 2088
      //   660: aload_0
      //   661: aload_1
      //   662: iload #6
      //   664: iconst_0
      //   665: invokevirtual getInt : (II)I
      //   668: putfield horizontalChainStyle : I
      //   671: goto -> 2088
      //   674: aload_0
      //   675: aload_1
      //   676: iload #6
      //   678: aload_0
      //   679: getfield verticalWeight : F
      //   682: invokevirtual getFloat : (IF)F
      //   685: putfield verticalWeight : F
      //   688: goto -> 2088
      //   691: aload_0
      //   692: aload_1
      //   693: iload #6
      //   695: aload_0
      //   696: getfield horizontalWeight : F
      //   699: invokevirtual getFloat : (IF)F
      //   702: putfield horizontalWeight : F
      //   705: goto -> 2088
      //   708: aload_1
      //   709: iload #6
      //   711: invokevirtual getString : (I)Ljava/lang/String;
      //   714: astore_2
      //   715: aload_0
      //   716: aload_2
      //   717: putfield dimensionRatio : Ljava/lang/String;
      //   720: aload_0
      //   721: ldc_w NaN
      //   724: putfield dimensionRatioValue : F
      //   727: aload_0
      //   728: iconst_m1
      //   729: putfield dimensionRatioSide : I
      //   732: aload_2
      //   733: ifnull -> 2088
      //   736: aload_2
      //   737: invokevirtual length : ()I
      //   740: istore #8
      //   742: aload_0
      //   743: getfield dimensionRatio : Ljava/lang/String;
      //   746: bipush #44
      //   748: invokevirtual indexOf : (I)I
      //   751: istore #6
      //   753: iload #6
      //   755: ifle -> 817
      //   758: iload #6
      //   760: iload #8
      //   762: iconst_1
      //   763: isub
      //   764: if_icmpge -> 817
      //   767: aload_0
      //   768: getfield dimensionRatio : Ljava/lang/String;
      //   771: iconst_0
      //   772: iload #6
      //   774: invokevirtual substring : (II)Ljava/lang/String;
      //   777: astore_2
      //   778: aload_2
      //   779: ldc_w 'W'
      //   782: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
      //   785: ifeq -> 796
      //   788: aload_0
      //   789: iconst_0
      //   790: putfield dimensionRatioSide : I
      //   793: goto -> 811
      //   796: aload_2
      //   797: ldc_w 'H'
      //   800: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
      //   803: ifeq -> 811
      //   806: aload_0
      //   807: iconst_1
      //   808: putfield dimensionRatioSide : I
      //   811: iinc #6, 1
      //   814: goto -> 820
      //   817: iconst_0
      //   818: istore #6
      //   820: aload_0
      //   821: getfield dimensionRatio : Ljava/lang/String;
      //   824: bipush #58
      //   826: invokevirtual indexOf : (I)I
      //   829: istore #9
      //   831: iload #9
      //   833: iflt -> 946
      //   836: iload #9
      //   838: iload #8
      //   840: iconst_1
      //   841: isub
      //   842: if_icmpge -> 946
      //   845: aload_0
      //   846: getfield dimensionRatio : Ljava/lang/String;
      //   849: iload #6
      //   851: iload #9
      //   853: invokevirtual substring : (II)Ljava/lang/String;
      //   856: astore_2
      //   857: aload_0
      //   858: getfield dimensionRatio : Ljava/lang/String;
      //   861: iload #9
      //   863: iconst_1
      //   864: iadd
      //   865: invokevirtual substring : (I)Ljava/lang/String;
      //   868: astore #10
      //   870: aload_2
      //   871: invokevirtual length : ()I
      //   874: ifle -> 2088
      //   877: aload #10
      //   879: invokevirtual length : ()I
      //   882: ifle -> 2088
      //   885: aload_2
      //   886: invokestatic parseFloat : (Ljava/lang/String;)F
      //   889: fstore_3
      //   890: aload #10
      //   892: invokestatic parseFloat : (Ljava/lang/String;)F
      //   895: fstore #4
      //   897: fload_3
      //   898: fconst_0
      //   899: fcmpl
      //   900: ifle -> 2088
      //   903: fload #4
      //   905: fconst_0
      //   906: fcmpl
      //   907: ifle -> 2088
      //   910: aload_0
      //   911: getfield dimensionRatioSide : I
      //   914: iconst_1
      //   915: if_icmpne -> 932
      //   918: aload_0
      //   919: fload #4
      //   921: fload_3
      //   922: fdiv
      //   923: invokestatic abs : (F)F
      //   926: putfield dimensionRatioValue : F
      //   929: goto -> 2088
      //   932: aload_0
      //   933: fload_3
      //   934: fload #4
      //   936: fdiv
      //   937: invokestatic abs : (F)F
      //   940: putfield dimensionRatioValue : F
      //   943: goto -> 2088
      //   946: aload_0
      //   947: getfield dimensionRatio : Ljava/lang/String;
      //   950: iload #6
      //   952: invokevirtual substring : (I)Ljava/lang/String;
      //   955: astore_2
      //   956: aload_2
      //   957: invokevirtual length : ()I
      //   960: ifle -> 2088
      //   963: aload_0
      //   964: aload_2
      //   965: invokestatic parseFloat : (Ljava/lang/String;)F
      //   968: putfield dimensionRatioValue : F
      //   971: goto -> 2088
      //   974: aload_0
      //   975: fconst_0
      //   976: aload_1
      //   977: iload #6
      //   979: aload_0
      //   980: getfield matchConstraintPercentHeight : F
      //   983: invokevirtual getFloat : (IF)F
      //   986: invokestatic max : (FF)F
      //   989: putfield matchConstraintPercentHeight : F
      //   992: aload_0
      //   993: iconst_2
      //   994: putfield matchConstraintDefaultHeight : I
      //   997: goto -> 2088
      //   1000: aload_0
      //   1001: aload_1
      //   1002: iload #6
      //   1004: aload_0
      //   1005: getfield matchConstraintMaxHeight : I
      //   1008: invokevirtual getDimensionPixelSize : (II)I
      //   1011: putfield matchConstraintMaxHeight : I
      //   1014: goto -> 2088
      //   1017: astore_2
      //   1018: aload_1
      //   1019: iload #6
      //   1021: aload_0
      //   1022: getfield matchConstraintMaxHeight : I
      //   1025: invokevirtual getInt : (II)I
      //   1028: bipush #-2
      //   1030: if_icmpne -> 2088
      //   1033: aload_0
      //   1034: bipush #-2
      //   1036: putfield matchConstraintMaxHeight : I
      //   1039: goto -> 2088
      //   1042: aload_0
      //   1043: aload_1
      //   1044: iload #6
      //   1046: aload_0
      //   1047: getfield matchConstraintMinHeight : I
      //   1050: invokevirtual getDimensionPixelSize : (II)I
      //   1053: putfield matchConstraintMinHeight : I
      //   1056: goto -> 2088
      //   1059: astore_2
      //   1060: aload_1
      //   1061: iload #6
      //   1063: aload_0
      //   1064: getfield matchConstraintMinHeight : I
      //   1067: invokevirtual getInt : (II)I
      //   1070: bipush #-2
      //   1072: if_icmpne -> 2088
      //   1075: aload_0
      //   1076: bipush #-2
      //   1078: putfield matchConstraintMinHeight : I
      //   1081: goto -> 2088
      //   1084: aload_0
      //   1085: fconst_0
      //   1086: aload_1
      //   1087: iload #6
      //   1089: aload_0
      //   1090: getfield matchConstraintPercentWidth : F
      //   1093: invokevirtual getFloat : (IF)F
      //   1096: invokestatic max : (FF)F
      //   1099: putfield matchConstraintPercentWidth : F
      //   1102: aload_0
      //   1103: iconst_2
      //   1104: putfield matchConstraintDefaultWidth : I
      //   1107: goto -> 2088
      //   1110: aload_0
      //   1111: aload_1
      //   1112: iload #6
      //   1114: aload_0
      //   1115: getfield matchConstraintMaxWidth : I
      //   1118: invokevirtual getDimensionPixelSize : (II)I
      //   1121: putfield matchConstraintMaxWidth : I
      //   1124: goto -> 2088
      //   1127: astore_2
      //   1128: aload_1
      //   1129: iload #6
      //   1131: aload_0
      //   1132: getfield matchConstraintMaxWidth : I
      //   1135: invokevirtual getInt : (II)I
      //   1138: bipush #-2
      //   1140: if_icmpne -> 2088
      //   1143: aload_0
      //   1144: bipush #-2
      //   1146: putfield matchConstraintMaxWidth : I
      //   1149: goto -> 2088
      //   1152: aload_0
      //   1153: aload_1
      //   1154: iload #6
      //   1156: aload_0
      //   1157: getfield matchConstraintMinWidth : I
      //   1160: invokevirtual getDimensionPixelSize : (II)I
      //   1163: putfield matchConstraintMinWidth : I
      //   1166: goto -> 2088
      //   1169: astore_2
      //   1170: aload_1
      //   1171: iload #6
      //   1173: aload_0
      //   1174: getfield matchConstraintMinWidth : I
      //   1177: invokevirtual getInt : (II)I
      //   1180: bipush #-2
      //   1182: if_icmpne -> 2088
      //   1185: aload_0
      //   1186: bipush #-2
      //   1188: putfield matchConstraintMinWidth : I
      //   1191: goto -> 2088
      //   1194: aload_1
      //   1195: iload #6
      //   1197: iconst_0
      //   1198: invokevirtual getInt : (II)I
      //   1201: istore #6
      //   1203: aload_0
      //   1204: iload #6
      //   1206: putfield matchConstraintDefaultHeight : I
      //   1209: iload #6
      //   1211: iconst_1
      //   1212: if_icmpne -> 2088
      //   1215: ldc_w 'ConstraintLayout'
      //   1218: ldc_w 'layout_constraintHeight_default="wrap" is deprecated.\\nUse layout_height="WRAP_CONTENT" and layout_constrainedHeight="true" instead.'
      //   1221: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
      //   1224: pop
      //   1225: goto -> 2088
      //   1228: aload_1
      //   1229: iload #6
      //   1231: iconst_0
      //   1232: invokevirtual getInt : (II)I
      //   1235: istore #6
      //   1237: aload_0
      //   1238: iload #6
      //   1240: putfield matchConstraintDefaultWidth : I
      //   1243: iload #6
      //   1245: iconst_1
      //   1246: if_icmpne -> 2088
      //   1249: ldc_w 'ConstraintLayout'
      //   1252: ldc_w 'layout_constraintWidth_default="wrap" is deprecated.\\nUse layout_width="WRAP_CONTENT" and layout_constrainedWidth="true" instead.'
      //   1255: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
      //   1258: pop
      //   1259: goto -> 2088
      //   1262: aload_0
      //   1263: aload_1
      //   1264: iload #6
      //   1266: aload_0
      //   1267: getfield verticalBias : F
      //   1270: invokevirtual getFloat : (IF)F
      //   1273: putfield verticalBias : F
      //   1276: goto -> 2088
      //   1279: aload_0
      //   1280: aload_1
      //   1281: iload #6
      //   1283: aload_0
      //   1284: getfield horizontalBias : F
      //   1287: invokevirtual getFloat : (IF)F
      //   1290: putfield horizontalBias : F
      //   1293: goto -> 2088
      //   1296: aload_0
      //   1297: aload_1
      //   1298: iload #6
      //   1300: aload_0
      //   1301: getfield constrainedHeight : Z
      //   1304: invokevirtual getBoolean : (IZ)Z
      //   1307: putfield constrainedHeight : Z
      //   1310: goto -> 2088
      //   1313: aload_0
      //   1314: aload_1
      //   1315: iload #6
      //   1317: aload_0
      //   1318: getfield constrainedWidth : Z
      //   1321: invokevirtual getBoolean : (IZ)Z
      //   1324: putfield constrainedWidth : Z
      //   1327: goto -> 2088
      //   1330: aload_0
      //   1331: aload_1
      //   1332: iload #6
      //   1334: aload_0
      //   1335: getfield goneEndMargin : I
      //   1338: invokevirtual getDimensionPixelSize : (II)I
      //   1341: putfield goneEndMargin : I
      //   1344: goto -> 2088
      //   1347: aload_0
      //   1348: aload_1
      //   1349: iload #6
      //   1351: aload_0
      //   1352: getfield goneStartMargin : I
      //   1355: invokevirtual getDimensionPixelSize : (II)I
      //   1358: putfield goneStartMargin : I
      //   1361: goto -> 2088
      //   1364: aload_0
      //   1365: aload_1
      //   1366: iload #6
      //   1368: aload_0
      //   1369: getfield goneBottomMargin : I
      //   1372: invokevirtual getDimensionPixelSize : (II)I
      //   1375: putfield goneBottomMargin : I
      //   1378: goto -> 2088
      //   1381: aload_0
      //   1382: aload_1
      //   1383: iload #6
      //   1385: aload_0
      //   1386: getfield goneRightMargin : I
      //   1389: invokevirtual getDimensionPixelSize : (II)I
      //   1392: putfield goneRightMargin : I
      //   1395: goto -> 2088
      //   1398: aload_0
      //   1399: aload_1
      //   1400: iload #6
      //   1402: aload_0
      //   1403: getfield goneTopMargin : I
      //   1406: invokevirtual getDimensionPixelSize : (II)I
      //   1409: putfield goneTopMargin : I
      //   1412: goto -> 2088
      //   1415: aload_0
      //   1416: aload_1
      //   1417: iload #6
      //   1419: aload_0
      //   1420: getfield goneLeftMargin : I
      //   1423: invokevirtual getDimensionPixelSize : (II)I
      //   1426: putfield goneLeftMargin : I
      //   1429: goto -> 2088
      //   1432: aload_1
      //   1433: iload #6
      //   1435: aload_0
      //   1436: getfield endToEnd : I
      //   1439: invokevirtual getResourceId : (II)I
      //   1442: istore #8
      //   1444: aload_0
      //   1445: iload #8
      //   1447: putfield endToEnd : I
      //   1450: iload #8
      //   1452: iconst_m1
      //   1453: if_icmpne -> 2088
      //   1456: aload_0
      //   1457: aload_1
      //   1458: iload #6
      //   1460: iconst_m1
      //   1461: invokevirtual getInt : (II)I
      //   1464: putfield endToEnd : I
      //   1467: goto -> 2088
      //   1470: aload_1
      //   1471: iload #6
      //   1473: aload_0
      //   1474: getfield endToStart : I
      //   1477: invokevirtual getResourceId : (II)I
      //   1480: istore #8
      //   1482: aload_0
      //   1483: iload #8
      //   1485: putfield endToStart : I
      //   1488: iload #8
      //   1490: iconst_m1
      //   1491: if_icmpne -> 2088
      //   1494: aload_0
      //   1495: aload_1
      //   1496: iload #6
      //   1498: iconst_m1
      //   1499: invokevirtual getInt : (II)I
      //   1502: putfield endToStart : I
      //   1505: goto -> 2088
      //   1508: aload_1
      //   1509: iload #6
      //   1511: aload_0
      //   1512: getfield startToStart : I
      //   1515: invokevirtual getResourceId : (II)I
      //   1518: istore #8
      //   1520: aload_0
      //   1521: iload #8
      //   1523: putfield startToStart : I
      //   1526: iload #8
      //   1528: iconst_m1
      //   1529: if_icmpne -> 2088
      //   1532: aload_0
      //   1533: aload_1
      //   1534: iload #6
      //   1536: iconst_m1
      //   1537: invokevirtual getInt : (II)I
      //   1540: putfield startToStart : I
      //   1543: goto -> 2088
      //   1546: aload_1
      //   1547: iload #6
      //   1549: aload_0
      //   1550: getfield startToEnd : I
      //   1553: invokevirtual getResourceId : (II)I
      //   1556: istore #8
      //   1558: aload_0
      //   1559: iload #8
      //   1561: putfield startToEnd : I
      //   1564: iload #8
      //   1566: iconst_m1
      //   1567: if_icmpne -> 2088
      //   1570: aload_0
      //   1571: aload_1
      //   1572: iload #6
      //   1574: iconst_m1
      //   1575: invokevirtual getInt : (II)I
      //   1578: putfield startToEnd : I
      //   1581: goto -> 2088
      //   1584: aload_1
      //   1585: iload #6
      //   1587: aload_0
      //   1588: getfield baselineToBaseline : I
      //   1591: invokevirtual getResourceId : (II)I
      //   1594: istore #8
      //   1596: aload_0
      //   1597: iload #8
      //   1599: putfield baselineToBaseline : I
      //   1602: iload #8
      //   1604: iconst_m1
      //   1605: if_icmpne -> 2088
      //   1608: aload_0
      //   1609: aload_1
      //   1610: iload #6
      //   1612: iconst_m1
      //   1613: invokevirtual getInt : (II)I
      //   1616: putfield baselineToBaseline : I
      //   1619: goto -> 2088
      //   1622: aload_1
      //   1623: iload #6
      //   1625: aload_0
      //   1626: getfield bottomToBottom : I
      //   1629: invokevirtual getResourceId : (II)I
      //   1632: istore #8
      //   1634: aload_0
      //   1635: iload #8
      //   1637: putfield bottomToBottom : I
      //   1640: iload #8
      //   1642: iconst_m1
      //   1643: if_icmpne -> 2088
      //   1646: aload_0
      //   1647: aload_1
      //   1648: iload #6
      //   1650: iconst_m1
      //   1651: invokevirtual getInt : (II)I
      //   1654: putfield bottomToBottom : I
      //   1657: goto -> 2088
      //   1660: aload_1
      //   1661: iload #6
      //   1663: aload_0
      //   1664: getfield bottomToTop : I
      //   1667: invokevirtual getResourceId : (II)I
      //   1670: istore #8
      //   1672: aload_0
      //   1673: iload #8
      //   1675: putfield bottomToTop : I
      //   1678: iload #8
      //   1680: iconst_m1
      //   1681: if_icmpne -> 2088
      //   1684: aload_0
      //   1685: aload_1
      //   1686: iload #6
      //   1688: iconst_m1
      //   1689: invokevirtual getInt : (II)I
      //   1692: putfield bottomToTop : I
      //   1695: goto -> 2088
      //   1698: aload_1
      //   1699: iload #6
      //   1701: aload_0
      //   1702: getfield topToBottom : I
      //   1705: invokevirtual getResourceId : (II)I
      //   1708: istore #8
      //   1710: aload_0
      //   1711: iload #8
      //   1713: putfield topToBottom : I
      //   1716: iload #8
      //   1718: iconst_m1
      //   1719: if_icmpne -> 2088
      //   1722: aload_0
      //   1723: aload_1
      //   1724: iload #6
      //   1726: iconst_m1
      //   1727: invokevirtual getInt : (II)I
      //   1730: putfield topToBottom : I
      //   1733: goto -> 2088
      //   1736: aload_1
      //   1737: iload #6
      //   1739: aload_0
      //   1740: getfield topToTop : I
      //   1743: invokevirtual getResourceId : (II)I
      //   1746: istore #8
      //   1748: aload_0
      //   1749: iload #8
      //   1751: putfield topToTop : I
      //   1754: iload #8
      //   1756: iconst_m1
      //   1757: if_icmpne -> 2088
      //   1760: aload_0
      //   1761: aload_1
      //   1762: iload #6
      //   1764: iconst_m1
      //   1765: invokevirtual getInt : (II)I
      //   1768: putfield topToTop : I
      //   1771: goto -> 2088
      //   1774: aload_1
      //   1775: iload #6
      //   1777: aload_0
      //   1778: getfield rightToRight : I
      //   1781: invokevirtual getResourceId : (II)I
      //   1784: istore #8
      //   1786: aload_0
      //   1787: iload #8
      //   1789: putfield rightToRight : I
      //   1792: iload #8
      //   1794: iconst_m1
      //   1795: if_icmpne -> 2088
      //   1798: aload_0
      //   1799: aload_1
      //   1800: iload #6
      //   1802: iconst_m1
      //   1803: invokevirtual getInt : (II)I
      //   1806: putfield rightToRight : I
      //   1809: goto -> 2088
      //   1812: aload_1
      //   1813: iload #6
      //   1815: aload_0
      //   1816: getfield rightToLeft : I
      //   1819: invokevirtual getResourceId : (II)I
      //   1822: istore #8
      //   1824: aload_0
      //   1825: iload #8
      //   1827: putfield rightToLeft : I
      //   1830: iload #8
      //   1832: iconst_m1
      //   1833: if_icmpne -> 2088
      //   1836: aload_0
      //   1837: aload_1
      //   1838: iload #6
      //   1840: iconst_m1
      //   1841: invokevirtual getInt : (II)I
      //   1844: putfield rightToLeft : I
      //   1847: goto -> 2088
      //   1850: aload_1
      //   1851: iload #6
      //   1853: aload_0
      //   1854: getfield leftToRight : I
      //   1857: invokevirtual getResourceId : (II)I
      //   1860: istore #8
      //   1862: aload_0
      //   1863: iload #8
      //   1865: putfield leftToRight : I
      //   1868: iload #8
      //   1870: iconst_m1
      //   1871: if_icmpne -> 2088
      //   1874: aload_0
      //   1875: aload_1
      //   1876: iload #6
      //   1878: iconst_m1
      //   1879: invokevirtual getInt : (II)I
      //   1882: putfield leftToRight : I
      //   1885: goto -> 2088
      //   1888: aload_1
      //   1889: iload #6
      //   1891: aload_0
      //   1892: getfield leftToLeft : I
      //   1895: invokevirtual getResourceId : (II)I
      //   1898: istore #8
      //   1900: aload_0
      //   1901: iload #8
      //   1903: putfield leftToLeft : I
      //   1906: iload #8
      //   1908: iconst_m1
      //   1909: if_icmpne -> 2088
      //   1912: aload_0
      //   1913: aload_1
      //   1914: iload #6
      //   1916: iconst_m1
      //   1917: invokevirtual getInt : (II)I
      //   1920: putfield leftToLeft : I
      //   1923: goto -> 2088
      //   1926: aload_0
      //   1927: aload_1
      //   1928: iload #6
      //   1930: aload_0
      //   1931: getfield guidePercent : F
      //   1934: invokevirtual getFloat : (IF)F
      //   1937: putfield guidePercent : F
      //   1940: goto -> 2088
      //   1943: aload_0
      //   1944: aload_1
      //   1945: iload #6
      //   1947: aload_0
      //   1948: getfield guideEnd : I
      //   1951: invokevirtual getDimensionPixelOffset : (II)I
      //   1954: putfield guideEnd : I
      //   1957: goto -> 2088
      //   1960: aload_0
      //   1961: aload_1
      //   1962: iload #6
      //   1964: aload_0
      //   1965: getfield guideBegin : I
      //   1968: invokevirtual getDimensionPixelOffset : (II)I
      //   1971: putfield guideBegin : I
      //   1974: goto -> 2088
      //   1977: aload_1
      //   1978: iload #6
      //   1980: aload_0
      //   1981: getfield circleAngle : F
      //   1984: invokevirtual getFloat : (IF)F
      //   1987: ldc_w 360.0
      //   1990: frem
      //   1991: fstore_3
      //   1992: aload_0
      //   1993: fload_3
      //   1994: putfield circleAngle : F
      //   1997: fload_3
      //   1998: fconst_0
      //   1999: fcmpg
      //   2000: ifge -> 2088
      //   2003: aload_0
      //   2004: ldc_w 360.0
      //   2007: fload_3
      //   2008: fsub
      //   2009: ldc_w 360.0
      //   2012: frem
      //   2013: putfield circleAngle : F
      //   2016: goto -> 2088
      //   2019: aload_0
      //   2020: aload_1
      //   2021: iload #6
      //   2023: aload_0
      //   2024: getfield circleRadius : I
      //   2027: invokevirtual getDimensionPixelSize : (II)I
      //   2030: putfield circleRadius : I
      //   2033: goto -> 2088
      //   2036: aload_1
      //   2037: iload #6
      //   2039: aload_0
      //   2040: getfield circleConstraint : I
      //   2043: invokevirtual getResourceId : (II)I
      //   2046: istore #8
      //   2048: aload_0
      //   2049: iload #8
      //   2051: putfield circleConstraint : I
      //   2054: iload #8
      //   2056: iconst_m1
      //   2057: if_icmpne -> 2088
      //   2060: aload_0
      //   2061: aload_1
      //   2062: iload #6
      //   2064: iconst_m1
      //   2065: invokevirtual getInt : (II)I
      //   2068: putfield circleConstraint : I
      //   2071: goto -> 2088
      //   2074: aload_0
      //   2075: aload_1
      //   2076: iload #6
      //   2078: aload_0
      //   2079: getfield orientation : I
      //   2082: invokevirtual getInt : (II)I
      //   2085: putfield orientation : I
      //   2088: iinc #5, 1
      //   2091: goto -> 356
      //   2094: aload_1
      //   2095: invokevirtual recycle : ()V
      //   2098: aload_0
      //   2099: invokevirtual validate : ()V
      //   2102: return
      //   2103: astore_2
      //   2104: goto -> 2088
      // Exception table:
      //   from	to	target	type
      //   885	897	2103	java/lang/NumberFormatException
      //   910	929	2103	java/lang/NumberFormatException
      //   932	943	2103	java/lang/NumberFormatException
      //   963	971	2103	java/lang/NumberFormatException
      //   1000	1014	1017	java/lang/Exception
      //   1042	1056	1059	java/lang/Exception
      //   1110	1124	1127	java/lang/Exception
      //   1152	1166	1169	java/lang/Exception
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.guideBegin = param1LayoutParams.guideBegin;
      this.guideEnd = param1LayoutParams.guideEnd;
      this.guidePercent = param1LayoutParams.guidePercent;
      this.leftToLeft = param1LayoutParams.leftToLeft;
      this.leftToRight = param1LayoutParams.leftToRight;
      this.rightToLeft = param1LayoutParams.rightToLeft;
      this.rightToRight = param1LayoutParams.rightToRight;
      this.topToTop = param1LayoutParams.topToTop;
      this.topToBottom = param1LayoutParams.topToBottom;
      this.bottomToTop = param1LayoutParams.bottomToTop;
      this.bottomToBottom = param1LayoutParams.bottomToBottom;
      this.baselineToBaseline = param1LayoutParams.baselineToBaseline;
      this.circleConstraint = param1LayoutParams.circleConstraint;
      this.circleRadius = param1LayoutParams.circleRadius;
      this.circleAngle = param1LayoutParams.circleAngle;
      this.startToEnd = param1LayoutParams.startToEnd;
      this.startToStart = param1LayoutParams.startToStart;
      this.endToStart = param1LayoutParams.endToStart;
      this.endToEnd = param1LayoutParams.endToEnd;
      this.goneLeftMargin = param1LayoutParams.goneLeftMargin;
      this.goneTopMargin = param1LayoutParams.goneTopMargin;
      this.goneRightMargin = param1LayoutParams.goneRightMargin;
      this.goneBottomMargin = param1LayoutParams.goneBottomMargin;
      this.goneStartMargin = param1LayoutParams.goneStartMargin;
      this.goneEndMargin = param1LayoutParams.goneEndMargin;
      this.horizontalBias = param1LayoutParams.horizontalBias;
      this.verticalBias = param1LayoutParams.verticalBias;
      this.dimensionRatio = param1LayoutParams.dimensionRatio;
      this.dimensionRatioValue = param1LayoutParams.dimensionRatioValue;
      this.dimensionRatioSide = param1LayoutParams.dimensionRatioSide;
      this.horizontalWeight = param1LayoutParams.horizontalWeight;
      this.verticalWeight = param1LayoutParams.verticalWeight;
      this.horizontalChainStyle = param1LayoutParams.horizontalChainStyle;
      this.verticalChainStyle = param1LayoutParams.verticalChainStyle;
      this.constrainedWidth = param1LayoutParams.constrainedWidth;
      this.constrainedHeight = param1LayoutParams.constrainedHeight;
      this.matchConstraintDefaultWidth = param1LayoutParams.matchConstraintDefaultWidth;
      this.matchConstraintDefaultHeight = param1LayoutParams.matchConstraintDefaultHeight;
      this.matchConstraintMinWidth = param1LayoutParams.matchConstraintMinWidth;
      this.matchConstraintMaxWidth = param1LayoutParams.matchConstraintMaxWidth;
      this.matchConstraintMinHeight = param1LayoutParams.matchConstraintMinHeight;
      this.matchConstraintMaxHeight = param1LayoutParams.matchConstraintMaxHeight;
      this.matchConstraintPercentWidth = param1LayoutParams.matchConstraintPercentWidth;
      this.matchConstraintPercentHeight = param1LayoutParams.matchConstraintPercentHeight;
      this.editorAbsoluteX = param1LayoutParams.editorAbsoluteX;
      this.editorAbsoluteY = param1LayoutParams.editorAbsoluteY;
      this.orientation = param1LayoutParams.orientation;
      this.horizontalDimensionFixed = param1LayoutParams.horizontalDimensionFixed;
      this.verticalDimensionFixed = param1LayoutParams.verticalDimensionFixed;
      this.needsBaseline = param1LayoutParams.needsBaseline;
      this.isGuideline = param1LayoutParams.isGuideline;
      this.resolvedLeftToLeft = param1LayoutParams.resolvedLeftToLeft;
      this.resolvedLeftToRight = param1LayoutParams.resolvedLeftToRight;
      this.resolvedRightToLeft = param1LayoutParams.resolvedRightToLeft;
      this.resolvedRightToRight = param1LayoutParams.resolvedRightToRight;
      this.resolveGoneLeftMargin = param1LayoutParams.resolveGoneLeftMargin;
      this.resolveGoneRightMargin = param1LayoutParams.resolveGoneRightMargin;
      this.resolvedHorizontalBias = param1LayoutParams.resolvedHorizontalBias;
      this.constraintTag = param1LayoutParams.constraintTag;
      this.widget = param1LayoutParams.widget;
    }
    
    public String getConstraintTag() {
      return this.constraintTag;
    }
    
    public ConstraintWidget getConstraintWidget() {
      return this.widget;
    }
    
    public void reset() {
      ConstraintWidget constraintWidget = this.widget;
      if (constraintWidget != null)
        constraintWidget.reset(); 
    }
    
    public void resolveLayoutDirection(int param1Int) {
      // Byte code:
      //   0: aload_0
      //   1: getfield leftMargin : I
      //   4: istore #4
      //   6: aload_0
      //   7: getfield rightMargin : I
      //   10: istore #5
      //   12: getstatic android/os/Build$VERSION.SDK_INT : I
      //   15: istore #6
      //   17: iconst_0
      //   18: istore_3
      //   19: iload #6
      //   21: bipush #17
      //   23: if_icmplt -> 44
      //   26: aload_0
      //   27: iload_1
      //   28: invokespecial resolveLayoutDirection : (I)V
      //   31: iconst_1
      //   32: aload_0
      //   33: invokevirtual getLayoutDirection : ()I
      //   36: if_icmpne -> 44
      //   39: iconst_1
      //   40: istore_1
      //   41: goto -> 46
      //   44: iconst_0
      //   45: istore_1
      //   46: aload_0
      //   47: iconst_m1
      //   48: putfield resolvedRightToLeft : I
      //   51: aload_0
      //   52: iconst_m1
      //   53: putfield resolvedRightToRight : I
      //   56: aload_0
      //   57: iconst_m1
      //   58: putfield resolvedLeftToLeft : I
      //   61: aload_0
      //   62: iconst_m1
      //   63: putfield resolvedLeftToRight : I
      //   66: aload_0
      //   67: iconst_m1
      //   68: putfield resolveGoneLeftMargin : I
      //   71: aload_0
      //   72: iconst_m1
      //   73: putfield resolveGoneRightMargin : I
      //   76: aload_0
      //   77: aload_0
      //   78: getfield goneLeftMargin : I
      //   81: putfield resolveGoneLeftMargin : I
      //   84: aload_0
      //   85: aload_0
      //   86: getfield goneRightMargin : I
      //   89: putfield resolveGoneRightMargin : I
      //   92: aload_0
      //   93: aload_0
      //   94: getfield horizontalBias : F
      //   97: putfield resolvedHorizontalBias : F
      //   100: aload_0
      //   101: aload_0
      //   102: getfield guideBegin : I
      //   105: putfield resolvedGuideBegin : I
      //   108: aload_0
      //   109: aload_0
      //   110: getfield guideEnd : I
      //   113: putfield resolvedGuideEnd : I
      //   116: aload_0
      //   117: aload_0
      //   118: getfield guidePercent : F
      //   121: putfield resolvedGuidePercent : F
      //   124: iload_1
      //   125: ifeq -> 354
      //   128: aload_0
      //   129: getfield startToEnd : I
      //   132: istore_1
      //   133: iload_1
      //   134: iconst_m1
      //   135: if_icmpeq -> 148
      //   138: aload_0
      //   139: iload_1
      //   140: putfield resolvedRightToLeft : I
      //   143: iconst_1
      //   144: istore_1
      //   145: goto -> 171
      //   148: aload_0
      //   149: getfield startToStart : I
      //   152: istore #6
      //   154: iload_3
      //   155: istore_1
      //   156: iload #6
      //   158: iconst_m1
      //   159: if_icmpeq -> 171
      //   162: aload_0
      //   163: iload #6
      //   165: putfield resolvedRightToRight : I
      //   168: goto -> 143
      //   171: aload_0
      //   172: getfield endToStart : I
      //   175: istore_3
      //   176: iload_3
      //   177: iconst_m1
      //   178: if_icmpeq -> 188
      //   181: aload_0
      //   182: iload_3
      //   183: putfield resolvedLeftToRight : I
      //   186: iconst_1
      //   187: istore_1
      //   188: aload_0
      //   189: getfield endToEnd : I
      //   192: istore_3
      //   193: iload_3
      //   194: iconst_m1
      //   195: if_icmpeq -> 205
      //   198: aload_0
      //   199: iload_3
      //   200: putfield resolvedLeftToLeft : I
      //   203: iconst_1
      //   204: istore_1
      //   205: aload_0
      //   206: getfield goneStartMargin : I
      //   209: istore_3
      //   210: iload_3
      //   211: iconst_m1
      //   212: if_icmpeq -> 220
      //   215: aload_0
      //   216: iload_3
      //   217: putfield resolveGoneRightMargin : I
      //   220: aload_0
      //   221: getfield goneEndMargin : I
      //   224: istore_3
      //   225: iload_3
      //   226: iconst_m1
      //   227: if_icmpeq -> 235
      //   230: aload_0
      //   231: iload_3
      //   232: putfield resolveGoneLeftMargin : I
      //   235: iload_1
      //   236: ifeq -> 249
      //   239: aload_0
      //   240: fconst_1
      //   241: aload_0
      //   242: getfield horizontalBias : F
      //   245: fsub
      //   246: putfield resolvedHorizontalBias : F
      //   249: aload_0
      //   250: getfield isGuideline : Z
      //   253: ifeq -> 444
      //   256: aload_0
      //   257: getfield orientation : I
      //   260: iconst_1
      //   261: if_icmpne -> 444
      //   264: aload_0
      //   265: getfield guidePercent : F
      //   268: fstore_2
      //   269: fload_2
      //   270: ldc -1.0
      //   272: fcmpl
      //   273: ifeq -> 296
      //   276: aload_0
      //   277: fconst_1
      //   278: fload_2
      //   279: fsub
      //   280: putfield resolvedGuidePercent : F
      //   283: aload_0
      //   284: iconst_m1
      //   285: putfield resolvedGuideBegin : I
      //   288: aload_0
      //   289: iconst_m1
      //   290: putfield resolvedGuideEnd : I
      //   293: goto -> 444
      //   296: aload_0
      //   297: getfield guideBegin : I
      //   300: istore_1
      //   301: iload_1
      //   302: iconst_m1
      //   303: if_icmpeq -> 325
      //   306: aload_0
      //   307: iload_1
      //   308: putfield resolvedGuideEnd : I
      //   311: aload_0
      //   312: iconst_m1
      //   313: putfield resolvedGuideBegin : I
      //   316: aload_0
      //   317: ldc -1.0
      //   319: putfield resolvedGuidePercent : F
      //   322: goto -> 444
      //   325: aload_0
      //   326: getfield guideEnd : I
      //   329: istore_1
      //   330: iload_1
      //   331: iconst_m1
      //   332: if_icmpeq -> 444
      //   335: aload_0
      //   336: iload_1
      //   337: putfield resolvedGuideBegin : I
      //   340: aload_0
      //   341: iconst_m1
      //   342: putfield resolvedGuideEnd : I
      //   345: aload_0
      //   346: ldc -1.0
      //   348: putfield resolvedGuidePercent : F
      //   351: goto -> 444
      //   354: aload_0
      //   355: getfield startToEnd : I
      //   358: istore_1
      //   359: iload_1
      //   360: iconst_m1
      //   361: if_icmpeq -> 369
      //   364: aload_0
      //   365: iload_1
      //   366: putfield resolvedLeftToRight : I
      //   369: aload_0
      //   370: getfield startToStart : I
      //   373: istore_1
      //   374: iload_1
      //   375: iconst_m1
      //   376: if_icmpeq -> 384
      //   379: aload_0
      //   380: iload_1
      //   381: putfield resolvedLeftToLeft : I
      //   384: aload_0
      //   385: getfield endToStart : I
      //   388: istore_1
      //   389: iload_1
      //   390: iconst_m1
      //   391: if_icmpeq -> 399
      //   394: aload_0
      //   395: iload_1
      //   396: putfield resolvedRightToLeft : I
      //   399: aload_0
      //   400: getfield endToEnd : I
      //   403: istore_1
      //   404: iload_1
      //   405: iconst_m1
      //   406: if_icmpeq -> 414
      //   409: aload_0
      //   410: iload_1
      //   411: putfield resolvedRightToRight : I
      //   414: aload_0
      //   415: getfield goneStartMargin : I
      //   418: istore_1
      //   419: iload_1
      //   420: iconst_m1
      //   421: if_icmpeq -> 429
      //   424: aload_0
      //   425: iload_1
      //   426: putfield resolveGoneLeftMargin : I
      //   429: aload_0
      //   430: getfield goneEndMargin : I
      //   433: istore_1
      //   434: iload_1
      //   435: iconst_m1
      //   436: if_icmpeq -> 444
      //   439: aload_0
      //   440: iload_1
      //   441: putfield resolveGoneRightMargin : I
      //   444: aload_0
      //   445: getfield endToStart : I
      //   448: iconst_m1
      //   449: if_icmpne -> 614
      //   452: aload_0
      //   453: getfield endToEnd : I
      //   456: iconst_m1
      //   457: if_icmpne -> 614
      //   460: aload_0
      //   461: getfield startToStart : I
      //   464: iconst_m1
      //   465: if_icmpne -> 614
      //   468: aload_0
      //   469: getfield startToEnd : I
      //   472: iconst_m1
      //   473: if_icmpne -> 614
      //   476: aload_0
      //   477: getfield rightToLeft : I
      //   480: istore_1
      //   481: iload_1
      //   482: iconst_m1
      //   483: if_icmpeq -> 512
      //   486: aload_0
      //   487: iload_1
      //   488: putfield resolvedRightToLeft : I
      //   491: aload_0
      //   492: getfield rightMargin : I
      //   495: ifgt -> 545
      //   498: iload #5
      //   500: ifle -> 545
      //   503: aload_0
      //   504: iload #5
      //   506: putfield rightMargin : I
      //   509: goto -> 545
      //   512: aload_0
      //   513: getfield rightToRight : I
      //   516: istore_1
      //   517: iload_1
      //   518: iconst_m1
      //   519: if_icmpeq -> 545
      //   522: aload_0
      //   523: iload_1
      //   524: putfield resolvedRightToRight : I
      //   527: aload_0
      //   528: getfield rightMargin : I
      //   531: ifgt -> 545
      //   534: iload #5
      //   536: ifle -> 545
      //   539: aload_0
      //   540: iload #5
      //   542: putfield rightMargin : I
      //   545: aload_0
      //   546: getfield leftToLeft : I
      //   549: istore_1
      //   550: iload_1
      //   551: iconst_m1
      //   552: if_icmpeq -> 581
      //   555: aload_0
      //   556: iload_1
      //   557: putfield resolvedLeftToLeft : I
      //   560: aload_0
      //   561: getfield leftMargin : I
      //   564: ifgt -> 614
      //   567: iload #4
      //   569: ifle -> 614
      //   572: aload_0
      //   573: iload #4
      //   575: putfield leftMargin : I
      //   578: goto -> 614
      //   581: aload_0
      //   582: getfield leftToRight : I
      //   585: istore_1
      //   586: iload_1
      //   587: iconst_m1
      //   588: if_icmpeq -> 614
      //   591: aload_0
      //   592: iload_1
      //   593: putfield resolvedLeftToRight : I
      //   596: aload_0
      //   597: getfield leftMargin : I
      //   600: ifgt -> 614
      //   603: iload #4
      //   605: ifle -> 614
      //   608: aload_0
      //   609: iload #4
      //   611: putfield leftMargin : I
      //   614: return
    }
    
    public void setWidgetDebugName(String param1String) {
      this.widget.setDebugName(param1String);
    }
    
    public void validate() {
      this.isGuideline = false;
      this.horizontalDimensionFixed = true;
      this.verticalDimensionFixed = true;
      if (this.width == -2 && this.constrainedWidth) {
        this.horizontalDimensionFixed = false;
        if (this.matchConstraintDefaultWidth == 0)
          this.matchConstraintDefaultWidth = 1; 
      } 
      if (this.height == -2 && this.constrainedHeight) {
        this.verticalDimensionFixed = false;
        if (this.matchConstraintDefaultHeight == 0)
          this.matchConstraintDefaultHeight = 1; 
      } 
      if (this.width == 0 || this.width == -1) {
        this.horizontalDimensionFixed = false;
        if (this.width == 0 && this.matchConstraintDefaultWidth == 1) {
          this.width = -2;
          this.constrainedWidth = true;
        } 
      } 
      if (this.height == 0 || this.height == -1) {
        this.verticalDimensionFixed = false;
        if (this.height == 0 && this.matchConstraintDefaultHeight == 1) {
          this.height = -2;
          this.constrainedHeight = true;
        } 
      } 
      if (this.guidePercent != -1.0F || this.guideBegin != -1 || this.guideEnd != -1) {
        this.isGuideline = true;
        this.horizontalDimensionFixed = true;
        this.verticalDimensionFixed = true;
        if (!(this.widget instanceof Guideline))
          this.widget = (ConstraintWidget)new Guideline(); 
        ((Guideline)this.widget).setOrientation(this.orientation);
      } 
    }
    
    private static class Table {
      public static final int ANDROID_ORIENTATION = 1;
      
      public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
      
      public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
      
      public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
      
      public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
      
      public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
      
      public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
      
      public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
      
      public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
      
      public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
      
      public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
      
      public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
      
      public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
      
      public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
      
      public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
      
      public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
      
      public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
      
      public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
      
      public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
      
      public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
      
      public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
      
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
      
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
      
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
      
      public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
      
      public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
      
      public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
      
      public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
      
      public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
      
      public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
      
      public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
      
      public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
      
      public static final int LAYOUT_CONSTRAINT_TAG = 51;
      
      public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
      
      public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
      
      public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
      
      public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
      
      public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
      
      public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
      
      public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
      
      public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
      
      public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
      
      public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
      
      public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
      
      public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
      
      public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
      
      public static final int LAYOUT_GONE_MARGIN_END = 26;
      
      public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
      
      public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
      
      public static final int LAYOUT_GONE_MARGIN_START = 25;
      
      public static final int LAYOUT_GONE_MARGIN_TOP = 22;
      
      public static final int UNUSED = 0;
      
      public static final SparseIntArray map;
      
      static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        map = sparseIntArray;
        sparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
        map.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
        map.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
        map.append(R.styleable.ConstraintLayout_Layout_android_orientation, 1);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
        map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
        map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
        map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
        map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
        map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
        map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
        map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTag, 51);
      }
    }
  }
  
  private static class Table {
    public static final int ANDROID_ORIENTATION = 1;
    
    public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
    
    public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
    
    public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
    
    public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
    
    public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
    
    public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
    
    public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
    
    public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
    
    public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
    
    public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
    
    public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
    
    public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
    
    public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
    
    public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
    
    public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
    
    public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
    
    public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
    
    public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
    
    public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
    
    public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
    
    public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
    
    public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
    
    public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
    
    public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
    
    public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
    
    public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
    
    public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
    
    public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
    
    public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
    
    public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
    
    public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
    
    public static final int LAYOUT_CONSTRAINT_TAG = 51;
    
    public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
    
    public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
    
    public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
    
    public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
    
    public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
    
    public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
    
    public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
    
    public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
    
    public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
    
    public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
    
    public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
    
    public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
    
    public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
    
    public static final int LAYOUT_GONE_MARGIN_END = 26;
    
    public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
    
    public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
    
    public static final int LAYOUT_GONE_MARGIN_START = 25;
    
    public static final int LAYOUT_GONE_MARGIN_TOP = 22;
    
    public static final int UNUSED = 0;
    
    public static final SparseIntArray map;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      map = sparseIntArray;
      sparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
      map.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
      map.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
      map.append(R.styleable.ConstraintLayout_Layout_android_orientation, 1);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
      map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
      map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
      map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
      map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
      map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
      map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
      map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTag, 51);
    }
  }
  
  class Measurer implements BasicMeasure.Measurer {
    ConstraintLayout layout;
    
    int layoutHeightSpec;
    
    int layoutWidthSpec;
    
    int paddingBottom;
    
    int paddingHeight;
    
    int paddingTop;
    
    int paddingWidth;
    
    final ConstraintLayout this$0;
    
    public Measurer(ConstraintLayout param1ConstraintLayout1) {
      this.layout = param1ConstraintLayout1;
    }
    
    private boolean isSimilarSpec(int param1Int1, int param1Int2, int param1Int3) {
      if (param1Int1 == param1Int2)
        return true; 
      int i = View.MeasureSpec.getMode(param1Int1);
      View.MeasureSpec.getSize(param1Int1);
      param1Int1 = View.MeasureSpec.getMode(param1Int2);
      param1Int2 = View.MeasureSpec.getSize(param1Int2);
      return (param1Int1 == 1073741824 && (i == Integer.MIN_VALUE || i == 0) && param1Int3 == param1Int2);
    }
    
    public void captureLayoutInfos(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
      this.paddingTop = param1Int3;
      this.paddingBottom = param1Int4;
      this.paddingWidth = param1Int5;
      this.paddingHeight = param1Int6;
      this.layoutWidthSpec = param1Int1;
      this.layoutHeightSpec = param1Int2;
    }
    
    public final void didMeasures() {
      int i = this.layout.getChildCount();
      boolean bool = false;
      byte b;
      for (b = 0; b < i; b++) {
        View view = this.layout.getChildAt(b);
        if (view instanceof Placeholder)
          ((Placeholder)view).updatePostMeasure(this.layout); 
      } 
      i = this.layout.mConstraintHelpers.size();
      if (i > 0)
        for (b = bool; b < i; b++)
          ((ConstraintHelper)this.layout.mConstraintHelpers.get(b)).updatePostMeasure(this.layout);  
    }
    
    public final void measure(ConstraintWidget param1ConstraintWidget, BasicMeasure.Measure param1Measure) {
      // Byte code:
      //   0: aload_1
      //   1: ifnonnull -> 5
      //   4: return
      //   5: aload_1
      //   6: invokevirtual getVisibility : ()I
      //   9: bipush #8
      //   11: if_icmpne -> 37
      //   14: aload_1
      //   15: invokevirtual isInPlaceholder : ()Z
      //   18: ifne -> 37
      //   21: aload_2
      //   22: iconst_0
      //   23: putfield measuredWidth : I
      //   26: aload_2
      //   27: iconst_0
      //   28: putfield measuredHeight : I
      //   31: aload_2
      //   32: iconst_0
      //   33: putfield measuredBaseline : I
      //   36: return
      //   37: aload_1
      //   38: invokevirtual getParent : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   41: ifnonnull -> 45
      //   44: return
      //   45: aload_2
      //   46: getfield horizontalBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   49: astore #21
      //   51: aload_2
      //   52: getfield verticalBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   55: astore #20
      //   57: aload_2
      //   58: getfield horizontalDimension : I
      //   61: istore #6
      //   63: aload_2
      //   64: getfield verticalDimension : I
      //   67: istore #7
      //   69: aload_0
      //   70: getfield paddingTop : I
      //   73: aload_0
      //   74: getfield paddingBottom : I
      //   77: iadd
      //   78: istore #8
      //   80: aload_0
      //   81: getfield paddingWidth : I
      //   84: istore #5
      //   86: aload_1
      //   87: invokevirtual getCompanionWidget : ()Ljava/lang/Object;
      //   90: checkcast android/view/View
      //   93: astore #19
      //   95: getstatic androidx/constraintlayout/widget/ConstraintLayout$1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour : [I
      //   98: aload #21
      //   100: invokevirtual ordinal : ()I
      //   103: iaload
      //   104: istore #4
      //   106: iload #4
      //   108: iconst_1
      //   109: if_icmpeq -> 322
      //   112: iload #4
      //   114: iconst_2
      //   115: if_icmpeq -> 306
      //   118: iload #4
      //   120: iconst_3
      //   121: if_icmpeq -> 286
      //   124: iload #4
      //   126: iconst_4
      //   127: if_icmpeq -> 136
      //   130: iconst_0
      //   131: istore #4
      //   133: goto -> 331
      //   136: aload_0
      //   137: getfield layoutWidthSpec : I
      //   140: iload #5
      //   142: bipush #-2
      //   144: invokestatic getChildMeasureSpec : (III)I
      //   147: istore #6
      //   149: aload_1
      //   150: getfield mMatchConstraintDefaultWidth : I
      //   153: iconst_1
      //   154: if_icmpne -> 163
      //   157: iconst_1
      //   158: istore #5
      //   160: goto -> 166
      //   163: iconst_0
      //   164: istore #5
      //   166: aload_2
      //   167: getfield measureStrategy : I
      //   170: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.TRY_GIVEN_DIMENSIONS : I
      //   173: if_icmpeq -> 190
      //   176: iload #6
      //   178: istore #4
      //   180: aload_2
      //   181: getfield measureStrategy : I
      //   184: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.USE_GIVEN_DIMENSIONS : I
      //   187: if_icmpne -> 331
      //   190: aload #19
      //   192: invokevirtual getMeasuredHeight : ()I
      //   195: aload_1
      //   196: invokevirtual getHeight : ()I
      //   199: if_icmpne -> 208
      //   202: iconst_1
      //   203: istore #4
      //   205: goto -> 211
      //   208: iconst_0
      //   209: istore #4
      //   211: aload_2
      //   212: getfield measureStrategy : I
      //   215: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.USE_GIVEN_DIMENSIONS : I
      //   218: if_icmpeq -> 260
      //   221: iload #5
      //   223: ifeq -> 260
      //   226: iload #5
      //   228: ifeq -> 236
      //   231: iload #4
      //   233: ifne -> 260
      //   236: aload #19
      //   238: instanceof androidx/constraintlayout/widget/Placeholder
      //   241: ifne -> 260
      //   244: aload_1
      //   245: invokevirtual isResolvedHorizontally : ()Z
      //   248: ifeq -> 254
      //   251: goto -> 260
      //   254: iconst_0
      //   255: istore #5
      //   257: goto -> 263
      //   260: iconst_1
      //   261: istore #5
      //   263: iload #6
      //   265: istore #4
      //   267: iload #5
      //   269: ifeq -> 331
      //   272: aload_1
      //   273: invokevirtual getWidth : ()I
      //   276: ldc 1073741824
      //   278: invokestatic makeMeasureSpec : (II)I
      //   281: istore #4
      //   283: goto -> 331
      //   286: aload_0
      //   287: getfield layoutWidthSpec : I
      //   290: iload #5
      //   292: aload_1
      //   293: invokevirtual getHorizontalMargin : ()I
      //   296: iadd
      //   297: iconst_m1
      //   298: invokestatic getChildMeasureSpec : (III)I
      //   301: istore #4
      //   303: goto -> 331
      //   306: aload_0
      //   307: getfield layoutWidthSpec : I
      //   310: iload #5
      //   312: bipush #-2
      //   314: invokestatic getChildMeasureSpec : (III)I
      //   317: istore #4
      //   319: goto -> 331
      //   322: iload #6
      //   324: ldc 1073741824
      //   326: invokestatic makeMeasureSpec : (II)I
      //   329: istore #4
      //   331: getstatic androidx/constraintlayout/widget/ConstraintLayout$1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour : [I
      //   334: aload #20
      //   336: invokevirtual ordinal : ()I
      //   339: iaload
      //   340: istore #5
      //   342: iload #5
      //   344: iconst_1
      //   345: if_icmpeq -> 558
      //   348: iload #5
      //   350: iconst_2
      //   351: if_icmpeq -> 542
      //   354: iload #5
      //   356: iconst_3
      //   357: if_icmpeq -> 522
      //   360: iload #5
      //   362: iconst_4
      //   363: if_icmpeq -> 372
      //   366: iconst_0
      //   367: istore #5
      //   369: goto -> 567
      //   372: aload_0
      //   373: getfield layoutHeightSpec : I
      //   376: iload #8
      //   378: bipush #-2
      //   380: invokestatic getChildMeasureSpec : (III)I
      //   383: istore #7
      //   385: aload_1
      //   386: getfield mMatchConstraintDefaultHeight : I
      //   389: iconst_1
      //   390: if_icmpne -> 399
      //   393: iconst_1
      //   394: istore #6
      //   396: goto -> 402
      //   399: iconst_0
      //   400: istore #6
      //   402: aload_2
      //   403: getfield measureStrategy : I
      //   406: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.TRY_GIVEN_DIMENSIONS : I
      //   409: if_icmpeq -> 426
      //   412: iload #7
      //   414: istore #5
      //   416: aload_2
      //   417: getfield measureStrategy : I
      //   420: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.USE_GIVEN_DIMENSIONS : I
      //   423: if_icmpne -> 567
      //   426: aload #19
      //   428: invokevirtual getMeasuredWidth : ()I
      //   431: aload_1
      //   432: invokevirtual getWidth : ()I
      //   435: if_icmpne -> 444
      //   438: iconst_1
      //   439: istore #5
      //   441: goto -> 447
      //   444: iconst_0
      //   445: istore #5
      //   447: aload_2
      //   448: getfield measureStrategy : I
      //   451: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.USE_GIVEN_DIMENSIONS : I
      //   454: if_icmpeq -> 496
      //   457: iload #6
      //   459: ifeq -> 496
      //   462: iload #6
      //   464: ifeq -> 472
      //   467: iload #5
      //   469: ifne -> 496
      //   472: aload #19
      //   474: instanceof androidx/constraintlayout/widget/Placeholder
      //   477: ifne -> 496
      //   480: aload_1
      //   481: invokevirtual isResolvedVertically : ()Z
      //   484: ifeq -> 490
      //   487: goto -> 496
      //   490: iconst_0
      //   491: istore #6
      //   493: goto -> 499
      //   496: iconst_1
      //   497: istore #6
      //   499: iload #7
      //   501: istore #5
      //   503: iload #6
      //   505: ifeq -> 567
      //   508: aload_1
      //   509: invokevirtual getHeight : ()I
      //   512: ldc 1073741824
      //   514: invokestatic makeMeasureSpec : (II)I
      //   517: istore #5
      //   519: goto -> 567
      //   522: aload_0
      //   523: getfield layoutHeightSpec : I
      //   526: iload #8
      //   528: aload_1
      //   529: invokevirtual getVerticalMargin : ()I
      //   532: iadd
      //   533: iconst_m1
      //   534: invokestatic getChildMeasureSpec : (III)I
      //   537: istore #5
      //   539: goto -> 567
      //   542: aload_0
      //   543: getfield layoutHeightSpec : I
      //   546: iload #8
      //   548: bipush #-2
      //   550: invokestatic getChildMeasureSpec : (III)I
      //   553: istore #5
      //   555: goto -> 567
      //   558: iload #7
      //   560: ldc 1073741824
      //   562: invokestatic makeMeasureSpec : (II)I
      //   565: istore #5
      //   567: aload_1
      //   568: invokevirtual getParent : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   571: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
      //   574: astore #22
      //   576: aload #22
      //   578: ifnull -> 739
      //   581: aload_0
      //   582: getfield this$0 : Landroidx/constraintlayout/widget/ConstraintLayout;
      //   585: invokestatic access$000 : (Landroidx/constraintlayout/widget/ConstraintLayout;)I
      //   588: sipush #256
      //   591: invokestatic enabled : (II)Z
      //   594: ifeq -> 739
      //   597: aload #19
      //   599: invokevirtual getMeasuredWidth : ()I
      //   602: aload_1
      //   603: invokevirtual getWidth : ()I
      //   606: if_icmpne -> 739
      //   609: aload #19
      //   611: invokevirtual getMeasuredWidth : ()I
      //   614: aload #22
      //   616: invokevirtual getWidth : ()I
      //   619: if_icmpge -> 739
      //   622: aload #19
      //   624: invokevirtual getMeasuredHeight : ()I
      //   627: aload_1
      //   628: invokevirtual getHeight : ()I
      //   631: if_icmpne -> 739
      //   634: aload #19
      //   636: invokevirtual getMeasuredHeight : ()I
      //   639: aload #22
      //   641: invokevirtual getHeight : ()I
      //   644: if_icmpge -> 739
      //   647: aload #19
      //   649: invokevirtual getBaseline : ()I
      //   652: aload_1
      //   653: invokevirtual getBaselineDistance : ()I
      //   656: if_icmpne -> 739
      //   659: aload_1
      //   660: invokevirtual isMeasureRequested : ()Z
      //   663: ifne -> 739
      //   666: aload_0
      //   667: aload_1
      //   668: invokevirtual getLastHorizontalMeasureSpec : ()I
      //   671: iload #4
      //   673: aload_1
      //   674: invokevirtual getWidth : ()I
      //   677: invokespecial isSimilarSpec : (III)Z
      //   680: ifeq -> 706
      //   683: aload_0
      //   684: aload_1
      //   685: invokevirtual getLastVerticalMeasureSpec : ()I
      //   688: iload #5
      //   690: aload_1
      //   691: invokevirtual getHeight : ()I
      //   694: invokespecial isSimilarSpec : (III)Z
      //   697: ifeq -> 706
      //   700: iconst_1
      //   701: istore #6
      //   703: goto -> 709
      //   706: iconst_0
      //   707: istore #6
      //   709: iload #6
      //   711: ifeq -> 739
      //   714: aload_2
      //   715: aload_1
      //   716: invokevirtual getWidth : ()I
      //   719: putfield measuredWidth : I
      //   722: aload_2
      //   723: aload_1
      //   724: invokevirtual getHeight : ()I
      //   727: putfield measuredHeight : I
      //   730: aload_2
      //   731: aload_1
      //   732: invokevirtual getBaselineDistance : ()I
      //   735: putfield measuredBaseline : I
      //   738: return
      //   739: aload #21
      //   741: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   744: if_acmpne -> 753
      //   747: iconst_1
      //   748: istore #6
      //   750: goto -> 756
      //   753: iconst_0
      //   754: istore #6
      //   756: aload #20
      //   758: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   761: if_acmpne -> 770
      //   764: iconst_1
      //   765: istore #7
      //   767: goto -> 773
      //   770: iconst_0
      //   771: istore #7
      //   773: aload #20
      //   775: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   778: if_acmpeq -> 798
      //   781: aload #20
      //   783: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   786: if_acmpne -> 792
      //   789: goto -> 798
      //   792: iconst_0
      //   793: istore #10
      //   795: goto -> 801
      //   798: iconst_1
      //   799: istore #10
      //   801: aload #21
      //   803: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   806: if_acmpeq -> 826
      //   809: aload #21
      //   811: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
      //   814: if_acmpne -> 820
      //   817: goto -> 826
      //   820: iconst_0
      //   821: istore #11
      //   823: goto -> 829
      //   826: iconst_1
      //   827: istore #11
      //   829: iload #6
      //   831: ifeq -> 849
      //   834: aload_1
      //   835: getfield mDimensionRatio : F
      //   838: fconst_0
      //   839: fcmpl
      //   840: ifle -> 849
      //   843: iconst_1
      //   844: istore #12
      //   846: goto -> 852
      //   849: iconst_0
      //   850: istore #12
      //   852: iload #7
      //   854: ifeq -> 872
      //   857: aload_1
      //   858: getfield mDimensionRatio : F
      //   861: fconst_0
      //   862: fcmpl
      //   863: ifle -> 872
      //   866: iconst_1
      //   867: istore #13
      //   869: goto -> 875
      //   872: iconst_0
      //   873: istore #13
      //   875: aload #19
      //   877: ifnonnull -> 881
      //   880: return
      //   881: aload #19
      //   883: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
      //   886: checkcast androidx/constraintlayout/widget/ConstraintLayout$LayoutParams
      //   889: astore #21
      //   891: aload_2
      //   892: getfield measureStrategy : I
      //   895: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.TRY_GIVEN_DIMENSIONS : I
      //   898: if_icmpeq -> 950
      //   901: aload_2
      //   902: getfield measureStrategy : I
      //   905: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.USE_GIVEN_DIMENSIONS : I
      //   908: if_icmpeq -> 950
      //   911: iload #6
      //   913: ifeq -> 950
      //   916: aload_1
      //   917: getfield mMatchConstraintDefaultWidth : I
      //   920: ifne -> 950
      //   923: iload #7
      //   925: ifeq -> 950
      //   928: aload_1
      //   929: getfield mMatchConstraintDefaultHeight : I
      //   932: ifeq -> 938
      //   935: goto -> 950
      //   938: iconst_0
      //   939: istore #6
      //   941: iconst_0
      //   942: istore #10
      //   944: iconst_0
      //   945: istore #9
      //   947: goto -> 1331
      //   950: aload #19
      //   952: instanceof androidx/constraintlayout/widget/VirtualLayout
      //   955: ifeq -> 988
      //   958: aload_1
      //   959: instanceof androidx/constraintlayout/solver/widgets/VirtualLayout
      //   962: ifeq -> 988
      //   965: aload_1
      //   966: checkcast androidx/constraintlayout/solver/widgets/VirtualLayout
      //   969: astore #20
      //   971: aload #19
      //   973: checkcast androidx/constraintlayout/widget/VirtualLayout
      //   976: aload #20
      //   978: iload #4
      //   980: iload #5
      //   982: invokevirtual onMeasure : (Landroidx/constraintlayout/solver/widgets/VirtualLayout;II)V
      //   985: goto -> 997
      //   988: aload #19
      //   990: iload #4
      //   992: iload #5
      //   994: invokevirtual measure : (II)V
      //   997: aload_1
      //   998: iload #4
      //   1000: iload #5
      //   1002: invokevirtual setLastMeasureSpec : (II)V
      //   1005: aload #19
      //   1007: invokevirtual getMeasuredWidth : ()I
      //   1010: istore #15
      //   1012: aload #19
      //   1014: invokevirtual getMeasuredHeight : ()I
      //   1017: istore #14
      //   1019: aload #19
      //   1021: invokevirtual getBaseline : ()I
      //   1024: istore #16
      //   1026: aload_1
      //   1027: getfield mMatchConstraintMinWidth : I
      //   1030: ifle -> 1047
      //   1033: aload_1
      //   1034: getfield mMatchConstraintMinWidth : I
      //   1037: iload #15
      //   1039: invokestatic max : (II)I
      //   1042: istore #7
      //   1044: goto -> 1051
      //   1047: iload #15
      //   1049: istore #7
      //   1051: iload #7
      //   1053: istore #6
      //   1055: aload_1
      //   1056: getfield mMatchConstraintMaxWidth : I
      //   1059: ifle -> 1073
      //   1062: aload_1
      //   1063: getfield mMatchConstraintMaxWidth : I
      //   1066: iload #7
      //   1068: invokestatic min : (II)I
      //   1071: istore #6
      //   1073: aload_1
      //   1074: getfield mMatchConstraintMinHeight : I
      //   1077: ifle -> 1094
      //   1080: aload_1
      //   1081: getfield mMatchConstraintMinHeight : I
      //   1084: iload #14
      //   1086: invokestatic max : (II)I
      //   1089: istore #7
      //   1091: goto -> 1098
      //   1094: iload #14
      //   1096: istore #7
      //   1098: iload #7
      //   1100: istore #9
      //   1102: aload_1
      //   1103: getfield mMatchConstraintMaxHeight : I
      //   1106: ifle -> 1120
      //   1109: aload_1
      //   1110: getfield mMatchConstraintMaxHeight : I
      //   1113: iload #7
      //   1115: invokestatic min : (II)I
      //   1118: istore #9
      //   1120: iload #6
      //   1122: istore #8
      //   1124: iload #9
      //   1126: istore #7
      //   1128: aload_0
      //   1129: getfield this$0 : Landroidx/constraintlayout/widget/ConstraintLayout;
      //   1132: invokestatic access$000 : (Landroidx/constraintlayout/widget/ConstraintLayout;)I
      //   1135: iconst_1
      //   1136: invokestatic enabled : (II)Z
      //   1139: ifne -> 1223
      //   1142: iload #12
      //   1144: ifeq -> 1176
      //   1147: iload #10
      //   1149: ifeq -> 1176
      //   1152: aload_1
      //   1153: getfield mDimensionRatio : F
      //   1156: fstore_3
      //   1157: iload #9
      //   1159: i2f
      //   1160: fload_3
      //   1161: fmul
      //   1162: ldc_w 0.5
      //   1165: fadd
      //   1166: f2i
      //   1167: istore #8
      //   1169: iload #9
      //   1171: istore #7
      //   1173: goto -> 1223
      //   1176: iload #6
      //   1178: istore #8
      //   1180: iload #9
      //   1182: istore #7
      //   1184: iload #13
      //   1186: ifeq -> 1223
      //   1189: iload #6
      //   1191: istore #8
      //   1193: iload #9
      //   1195: istore #7
      //   1197: iload #11
      //   1199: ifeq -> 1223
      //   1202: aload_1
      //   1203: getfield mDimensionRatio : F
      //   1206: fstore_3
      //   1207: iload #6
      //   1209: i2f
      //   1210: fload_3
      //   1211: fdiv
      //   1212: ldc_w 0.5
      //   1215: fadd
      //   1216: f2i
      //   1217: istore #7
      //   1219: iload #6
      //   1221: istore #8
      //   1223: iload #15
      //   1225: iload #8
      //   1227: if_icmpne -> 1255
      //   1230: iload #16
      //   1232: istore #6
      //   1234: iload #8
      //   1236: istore #10
      //   1238: iload #7
      //   1240: istore #9
      //   1242: iload #14
      //   1244: iload #7
      //   1246: if_icmpeq -> 1252
      //   1249: goto -> 1255
      //   1252: goto -> 1331
      //   1255: iload #15
      //   1257: iload #8
      //   1259: if_icmpeq -> 1271
      //   1262: iload #8
      //   1264: ldc 1073741824
      //   1266: invokestatic makeMeasureSpec : (II)I
      //   1269: istore #4
      //   1271: iload #14
      //   1273: iload #7
      //   1275: if_icmpeq -> 1290
      //   1278: iload #7
      //   1280: ldc 1073741824
      //   1282: invokestatic makeMeasureSpec : (II)I
      //   1285: istore #5
      //   1287: goto -> 1290
      //   1290: aload #19
      //   1292: iload #4
      //   1294: iload #5
      //   1296: invokevirtual measure : (II)V
      //   1299: aload_1
      //   1300: iload #4
      //   1302: iload #5
      //   1304: invokevirtual setLastMeasureSpec : (II)V
      //   1307: aload #19
      //   1309: invokevirtual getMeasuredWidth : ()I
      //   1312: istore #10
      //   1314: aload #19
      //   1316: invokevirtual getMeasuredHeight : ()I
      //   1319: istore #9
      //   1321: aload #19
      //   1323: invokevirtual getBaseline : ()I
      //   1326: istore #6
      //   1328: goto -> 1252
      //   1331: iload #6
      //   1333: iconst_m1
      //   1334: if_icmpeq -> 1343
      //   1337: iconst_1
      //   1338: istore #17
      //   1340: goto -> 1346
      //   1343: iconst_0
      //   1344: istore #17
      //   1346: iload #10
      //   1348: aload_2
      //   1349: getfield horizontalDimension : I
      //   1352: if_icmpne -> 1373
      //   1355: iload #9
      //   1357: aload_2
      //   1358: getfield verticalDimension : I
      //   1361: if_icmpeq -> 1367
      //   1364: goto -> 1373
      //   1367: iconst_0
      //   1368: istore #18
      //   1370: goto -> 1376
      //   1373: iconst_1
      //   1374: istore #18
      //   1376: aload_2
      //   1377: iload #18
      //   1379: putfield measuredNeedsSolverPass : Z
      //   1382: aload #21
      //   1384: getfield needsBaseline : Z
      //   1387: ifeq -> 1393
      //   1390: iconst_1
      //   1391: istore #17
      //   1393: iload #17
      //   1395: ifeq -> 1418
      //   1398: iload #6
      //   1400: iconst_m1
      //   1401: if_icmpeq -> 1418
      //   1404: aload_1
      //   1405: invokevirtual getBaselineDistance : ()I
      //   1408: iload #6
      //   1410: if_icmpeq -> 1418
      //   1413: aload_2
      //   1414: iconst_1
      //   1415: putfield measuredNeedsSolverPass : Z
      //   1418: aload_2
      //   1419: iload #10
      //   1421: putfield measuredWidth : I
      //   1424: aload_2
      //   1425: iload #9
      //   1427: putfield measuredHeight : I
      //   1430: aload_2
      //   1431: iload #17
      //   1433: putfield measuredHasBaseline : Z
      //   1436: aload_2
      //   1437: iload #6
      //   1439: putfield measuredBaseline : I
      //   1442: return
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\widget\ConstraintLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */