package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.analyzer.ChainRun;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintWidget {
  public static final int ANCHOR_BASELINE = 4;
  
  public static final int ANCHOR_BOTTOM = 3;
  
  public static final int ANCHOR_LEFT = 0;
  
  public static final int ANCHOR_RIGHT = 1;
  
  public static final int ANCHOR_TOP = 2;
  
  private static final boolean AUTOTAG_CENTER = false;
  
  public static final int BOTH = 2;
  
  public static final int CHAIN_PACKED = 2;
  
  public static final int CHAIN_SPREAD = 0;
  
  public static final int CHAIN_SPREAD_INSIDE = 1;
  
  public static float DEFAULT_BIAS = 0.5F;
  
  static final int DIMENSION_HORIZONTAL = 0;
  
  static final int DIMENSION_VERTICAL = 1;
  
  protected static final int DIRECT = 2;
  
  public static final int GONE = 8;
  
  public static final int HORIZONTAL = 0;
  
  public static final int INVISIBLE = 4;
  
  public static final int MATCH_CONSTRAINT_PERCENT = 2;
  
  public static final int MATCH_CONSTRAINT_RATIO = 3;
  
  public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
  
  public static final int MATCH_CONSTRAINT_SPREAD = 0;
  
  public static final int MATCH_CONSTRAINT_WRAP = 1;
  
  protected static final int SOLVER = 1;
  
  public static final int UNKNOWN = -1;
  
  private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
  
  public static final int VERTICAL = 1;
  
  public static final int VISIBLE = 0;
  
  private static final int WRAP = -2;
  
  private boolean OPTIMIZE_WRAP = false;
  
  private boolean OPTIMIZE_WRAP_ON_RESOLVED = true;
  
  private boolean hasBaseline = false;
  
  public ChainRun horizontalChainRun;
  
  public int horizontalGroup;
  
  public HorizontalWidgetRun horizontalRun = null;
  
  private boolean inPlaceholder;
  
  public boolean[] isTerminalWidget = new boolean[] { true, true };
  
  protected ArrayList<ConstraintAnchor> mAnchors;
  
  public ConstraintAnchor mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
  
  int mBaselineDistance;
  
  public ConstraintAnchor mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
  
  boolean mBottomHasCentered;
  
  public ConstraintAnchor mCenter;
  
  ConstraintAnchor mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
  
  ConstraintAnchor mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
  
  private float mCircleConstraintAngle = 0.0F;
  
  private Object mCompanionWidget;
  
  private int mContainerItemSkip;
  
  private String mDebugName;
  
  public float mDimensionRatio;
  
  protected int mDimensionRatioSide;
  
  int mDistToBottom;
  
  int mDistToLeft;
  
  int mDistToRight;
  
  int mDistToTop;
  
  boolean mGroupsToSolver;
  
  int mHeight;
  
  float mHorizontalBiasPercent;
  
  boolean mHorizontalChainFixedPosition;
  
  int mHorizontalChainStyle;
  
  ConstraintWidget mHorizontalNextWidget;
  
  public int mHorizontalResolution = -1;
  
  boolean mHorizontalWrapVisited;
  
  private boolean mInVirtuaLayout = false;
  
  public boolean mIsHeightWrapContent;
  
  private boolean[] mIsInBarrier;
  
  public boolean mIsWidthWrapContent;
  
  private int mLastHorizontalMeasureSpec = 0;
  
  private int mLastVerticalMeasureSpec = 0;
  
  public ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
  
  boolean mLeftHasCentered;
  
  public ConstraintAnchor[] mListAnchors;
  
  public DimensionBehaviour[] mListDimensionBehaviors;
  
  protected ConstraintWidget[] mListNextMatchConstraintsWidget;
  
  public int mMatchConstraintDefaultHeight = 0;
  
  public int mMatchConstraintDefaultWidth = 0;
  
  public int mMatchConstraintMaxHeight = 0;
  
  public int mMatchConstraintMaxWidth = 0;
  
  public int mMatchConstraintMinHeight = 0;
  
  public int mMatchConstraintMinWidth = 0;
  
  public float mMatchConstraintPercentHeight = 1.0F;
  
  public float mMatchConstraintPercentWidth = 1.0F;
  
  private int[] mMaxDimension = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE };
  
  private boolean mMeasureRequested = true;
  
  protected int mMinHeight;
  
  protected int mMinWidth;
  
  protected ConstraintWidget[] mNextChainWidget;
  
  protected int mOffsetX;
  
  protected int mOffsetY;
  
  public ConstraintWidget mParent;
  
  int mRelX;
  
  int mRelY;
  
  float mResolvedDimensionRatio = 1.0F;
  
  int mResolvedDimensionRatioSide = -1;
  
  boolean mResolvedHasRatio = false;
  
  public int[] mResolvedMatchConstraintDefault = new int[2];
  
  public ConstraintAnchor mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
  
  boolean mRightHasCentered;
  
  public ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
  
  boolean mTopHasCentered;
  
  private String mType;
  
  float mVerticalBiasPercent;
  
  boolean mVerticalChainFixedPosition;
  
  int mVerticalChainStyle;
  
  ConstraintWidget mVerticalNextWidget;
  
  public int mVerticalResolution = -1;
  
  boolean mVerticalWrapVisited;
  
  private int mVisibility;
  
  public float[] mWeight;
  
  int mWidth;
  
  protected int mX;
  
  protected int mY;
  
  public boolean measured = false;
  
  private boolean resolvedHorizontal = false;
  
  private boolean resolvedVertical = false;
  
  public WidgetRun[] run = new WidgetRun[2];
  
  public ChainRun verticalChainRun;
  
  public int verticalGroup;
  
  public VerticalWidgetRun verticalRun = null;
  
  public ConstraintWidget() {
    ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    this.mCenter = constraintAnchor;
    this.mListAnchors = new ConstraintAnchor[] { this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor };
    this.mAnchors = new ArrayList<ConstraintAnchor>();
    this.mIsInBarrier = new boolean[2];
    this.mListDimensionBehaviors = new DimensionBehaviour[] { DimensionBehaviour.FIXED, DimensionBehaviour.FIXED };
    this.mParent = null;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mRelX = 0;
    this.mRelY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mDebugName = null;
    this.mType = null;
    this.mGroupsToSolver = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mWeight = new float[] { -1.0F, -1.0F };
    this.mListNextMatchConstraintsWidget = new ConstraintWidget[] { null, null };
    this.mNextChainWidget = new ConstraintWidget[] { null, null };
    this.mHorizontalNextWidget = null;
    this.mVerticalNextWidget = null;
    this.horizontalGroup = -1;
    this.verticalGroup = -1;
    addAnchors();
  }
  
  public ConstraintWidget(int paramInt1, int paramInt2) {
    this(0, 0, paramInt1, paramInt2);
  }
  
  public ConstraintWidget(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    this.mCenter = constraintAnchor;
    this.mListAnchors = new ConstraintAnchor[] { this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor };
    this.mAnchors = new ArrayList<ConstraintAnchor>();
    this.mIsInBarrier = new boolean[2];
    this.mListDimensionBehaviors = new DimensionBehaviour[] { DimensionBehaviour.FIXED, DimensionBehaviour.FIXED };
    this.mParent = null;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mRelX = 0;
    this.mRelY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mDebugName = null;
    this.mType = null;
    this.mGroupsToSolver = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mWeight = new float[] { -1.0F, -1.0F };
    this.mListNextMatchConstraintsWidget = new ConstraintWidget[] { null, null };
    this.mNextChainWidget = new ConstraintWidget[] { null, null };
    this.mHorizontalNextWidget = null;
    this.mVerticalNextWidget = null;
    this.horizontalGroup = -1;
    this.verticalGroup = -1;
    this.mX = paramInt1;
    this.mY = paramInt2;
    this.mWidth = paramInt3;
    this.mHeight = paramInt4;
    addAnchors();
  }
  
  public ConstraintWidget(String paramString) {
    ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    this.mCenter = constraintAnchor;
    this.mListAnchors = new ConstraintAnchor[] { this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor };
    this.mAnchors = new ArrayList<ConstraintAnchor>();
    this.mIsInBarrier = new boolean[2];
    this.mListDimensionBehaviors = new DimensionBehaviour[] { DimensionBehaviour.FIXED, DimensionBehaviour.FIXED };
    this.mParent = null;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mRelX = 0;
    this.mRelY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mDebugName = null;
    this.mType = null;
    this.mGroupsToSolver = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mWeight = new float[] { -1.0F, -1.0F };
    this.mListNextMatchConstraintsWidget = new ConstraintWidget[] { null, null };
    this.mNextChainWidget = new ConstraintWidget[] { null, null };
    this.mHorizontalNextWidget = null;
    this.mVerticalNextWidget = null;
    this.horizontalGroup = -1;
    this.verticalGroup = -1;
    addAnchors();
    setDebugName(paramString);
  }
  
  public ConstraintWidget(String paramString, int paramInt1, int paramInt2) {
    this(paramInt1, paramInt2);
    setDebugName(paramString);
  }
  
  public ConstraintWidget(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this(paramInt1, paramInt2, paramInt3, paramInt4);
    setDebugName(paramString);
  }
  
  private void addAnchors() {
    this.mAnchors.add(this.mLeft);
    this.mAnchors.add(this.mTop);
    this.mAnchors.add(this.mRight);
    this.mAnchors.add(this.mBottom);
    this.mAnchors.add(this.mCenterX);
    this.mAnchors.add(this.mCenterY);
    this.mAnchors.add(this.mCenter);
    this.mAnchors.add(this.mBaseline);
  }
  
  private void applyConstraints(LinearSystem paramLinearSystem, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, DimensionBehaviour paramDimensionBehaviour, boolean paramBoolean5, ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, boolean paramBoolean10, int paramInt5, int paramInt6, int paramInt7, int paramInt8, float paramFloat2, boolean paramBoolean11) {
    // Byte code:
    //   0: aload_1
    //   1: aload #10
    //   3: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   6: astore #39
    //   8: aload_1
    //   9: aload #11
    //   11: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   14: astore #37
    //   16: aload_1
    //   17: aload #10
    //   19: invokevirtual getTarget : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   22: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   25: astore #40
    //   27: aload_1
    //   28: aload #11
    //   30: invokevirtual getTarget : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   33: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   36: astore #38
    //   38: invokestatic getMetrics : ()Landroidx/constraintlayout/solver/Metrics;
    //   41: ifnull -> 61
    //   44: invokestatic getMetrics : ()Landroidx/constraintlayout/solver/Metrics;
    //   47: astore #36
    //   49: aload #36
    //   51: aload #36
    //   53: getfield nonresolvedWidgets : J
    //   56: lconst_1
    //   57: ladd
    //   58: putfield nonresolvedWidgets : J
    //   61: aload #10
    //   63: invokevirtual isConnected : ()Z
    //   66: istore #35
    //   68: aload #11
    //   70: invokevirtual isConnected : ()Z
    //   73: istore #33
    //   75: aload_0
    //   76: getfield mCenter : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   79: invokevirtual isConnected : ()Z
    //   82: istore #34
    //   84: iload #35
    //   86: ifeq -> 95
    //   89: iconst_1
    //   90: istore #29
    //   92: goto -> 98
    //   95: iconst_0
    //   96: istore #29
    //   98: iload #29
    //   100: istore #28
    //   102: iload #33
    //   104: ifeq -> 113
    //   107: iload #29
    //   109: iconst_1
    //   110: iadd
    //   111: istore #28
    //   113: iload #28
    //   115: istore #29
    //   117: iload #34
    //   119: ifeq -> 128
    //   122: iload #28
    //   124: iconst_1
    //   125: iadd
    //   126: istore #29
    //   128: iload #17
    //   130: ifeq -> 139
    //   133: iconst_3
    //   134: istore #30
    //   136: goto -> 143
    //   139: iload #22
    //   141: istore #30
    //   143: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour : [I
    //   146: aload #8
    //   148: invokevirtual ordinal : ()I
    //   151: iaload
    //   152: istore #22
    //   154: iload #22
    //   156: iconst_1
    //   157: if_icmpeq -> 178
    //   160: iload #22
    //   162: iconst_2
    //   163: if_icmpeq -> 178
    //   166: iload #22
    //   168: iconst_3
    //   169: if_icmpeq -> 178
    //   172: iload #22
    //   174: iconst_4
    //   175: if_icmpeq -> 184
    //   178: iconst_0
    //   179: istore #22
    //   181: goto -> 193
    //   184: iload #30
    //   186: iconst_4
    //   187: if_icmpeq -> 178
    //   190: iconst_1
    //   191: istore #22
    //   193: aload_0
    //   194: getfield mVisibility : I
    //   197: bipush #8
    //   199: if_icmpne -> 211
    //   202: iconst_0
    //   203: istore #13
    //   205: iconst_0
    //   206: istore #22
    //   208: goto -> 211
    //   211: iload #27
    //   213: ifeq -> 271
    //   216: iload #35
    //   218: ifne -> 242
    //   221: iload #33
    //   223: ifne -> 242
    //   226: iload #34
    //   228: ifne -> 242
    //   231: aload_1
    //   232: aload #39
    //   234: iload #12
    //   236: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   239: goto -> 271
    //   242: iload #35
    //   244: ifeq -> 271
    //   247: iload #33
    //   249: ifne -> 271
    //   252: aload_1
    //   253: aload #39
    //   255: aload #40
    //   257: aload #10
    //   259: invokevirtual getMargin : ()I
    //   262: bipush #8
    //   264: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   267: pop
    //   268: goto -> 271
    //   271: iload #22
    //   273: ifne -> 359
    //   276: iload #9
    //   278: ifeq -> 331
    //   281: aload_1
    //   282: aload #37
    //   284: aload #39
    //   286: iconst_0
    //   287: iconst_3
    //   288: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   291: pop
    //   292: iload #14
    //   294: ifle -> 309
    //   297: aload_1
    //   298: aload #37
    //   300: aload #39
    //   302: iload #14
    //   304: bipush #8
    //   306: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   309: iload #15
    //   311: ldc 2147483647
    //   313: if_icmpge -> 344
    //   316: aload_1
    //   317: aload #37
    //   319: aload #39
    //   321: iload #15
    //   323: bipush #8
    //   325: invokevirtual addLowerThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   328: goto -> 344
    //   331: aload_1
    //   332: aload #37
    //   334: aload #39
    //   336: iload #13
    //   338: bipush #8
    //   340: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   343: pop
    //   344: iload #25
    //   346: istore #12
    //   348: iload #24
    //   350: istore #25
    //   352: iload #22
    //   354: istore #28
    //   356: goto -> 817
    //   359: iload #29
    //   361: iconst_2
    //   362: if_icmpeq -> 435
    //   365: iload #17
    //   367: ifne -> 435
    //   370: iload #30
    //   372: iconst_1
    //   373: if_icmpeq -> 381
    //   376: iload #30
    //   378: ifne -> 435
    //   381: iload #24
    //   383: iload #13
    //   385: invokestatic max : (II)I
    //   388: istore #13
    //   390: iload #13
    //   392: istore #12
    //   394: iload #25
    //   396: ifle -> 408
    //   399: iload #25
    //   401: iload #13
    //   403: invokestatic min : (II)I
    //   406: istore #12
    //   408: aload_1
    //   409: aload #37
    //   411: aload #39
    //   413: iload #12
    //   415: bipush #8
    //   417: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   420: pop
    //   421: iconst_0
    //   422: istore #28
    //   424: iload #25
    //   426: istore #12
    //   428: iload #24
    //   430: istore #25
    //   432: goto -> 817
    //   435: iload #24
    //   437: istore #12
    //   439: iload #24
    //   441: bipush #-2
    //   443: if_icmpne -> 450
    //   446: iload #13
    //   448: istore #12
    //   450: iload #25
    //   452: bipush #-2
    //   454: if_icmpne -> 464
    //   457: iload #13
    //   459: istore #15
    //   461: goto -> 468
    //   464: iload #25
    //   466: istore #15
    //   468: iload #13
    //   470: istore #24
    //   472: iload #13
    //   474: ifle -> 490
    //   477: iload #13
    //   479: istore #24
    //   481: iload #30
    //   483: iconst_1
    //   484: if_icmpeq -> 490
    //   487: iconst_0
    //   488: istore #24
    //   490: iload #24
    //   492: istore #13
    //   494: iload #12
    //   496: ifle -> 520
    //   499: aload_1
    //   500: aload #37
    //   502: aload #39
    //   504: iload #12
    //   506: bipush #8
    //   508: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   511: iload #24
    //   513: iload #12
    //   515: invokestatic max : (II)I
    //   518: istore #13
    //   520: iload #15
    //   522: ifle -> 576
    //   525: iload_3
    //   526: ifeq -> 541
    //   529: iload #30
    //   531: iconst_1
    //   532: if_icmpne -> 541
    //   535: iconst_0
    //   536: istore #24
    //   538: goto -> 544
    //   541: iconst_1
    //   542: istore #24
    //   544: iload #24
    //   546: ifeq -> 564
    //   549: aload_1
    //   550: aload #37
    //   552: aload #39
    //   554: iload #15
    //   556: bipush #8
    //   558: invokevirtual addLowerThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   561: goto -> 564
    //   564: iload #13
    //   566: iload #15
    //   568: invokestatic min : (II)I
    //   571: istore #13
    //   573: goto -> 576
    //   576: iload #30
    //   578: iconst_1
    //   579: if_icmpne -> 669
    //   582: iload_3
    //   583: ifeq -> 602
    //   586: aload_1
    //   587: aload #37
    //   589: aload #39
    //   591: iload #13
    //   593: bipush #8
    //   595: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   598: pop
    //   599: goto -> 658
    //   602: iload #19
    //   604: ifeq -> 634
    //   607: aload_1
    //   608: aload #37
    //   610: aload #39
    //   612: iload #13
    //   614: iconst_5
    //   615: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   618: pop
    //   619: aload_1
    //   620: aload #37
    //   622: aload #39
    //   624: iload #13
    //   626: bipush #8
    //   628: invokevirtual addLowerThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   631: goto -> 658
    //   634: aload_1
    //   635: aload #37
    //   637: aload #39
    //   639: iload #13
    //   641: iconst_5
    //   642: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   645: pop
    //   646: aload_1
    //   647: aload #37
    //   649: aload #39
    //   651: iload #13
    //   653: bipush #8
    //   655: invokevirtual addLowerThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   658: iload #12
    //   660: istore #24
    //   662: iload #15
    //   664: istore #12
    //   666: goto -> 348
    //   669: iload #30
    //   671: iconst_2
    //   672: if_icmpne -> 802
    //   675: aload #10
    //   677: invokevirtual getType : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   680: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   683: if_acmpeq -> 735
    //   686: aload #10
    //   688: invokevirtual getType : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   691: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   694: if_acmpne -> 700
    //   697: goto -> 735
    //   700: aload_1
    //   701: aload_0
    //   702: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   705: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   708: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   711: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   714: astore #8
    //   716: aload_1
    //   717: aload_0
    //   718: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   721: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   724: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   727: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   730: astore #36
    //   732: goto -> 767
    //   735: aload_1
    //   736: aload_0
    //   737: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   740: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   743: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   746: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   749: astore #8
    //   751: aload_1
    //   752: aload_0
    //   753: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   756: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   759: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   762: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   765: astore #36
    //   767: aload_1
    //   768: aload_1
    //   769: invokevirtual createRow : ()Landroidx/constraintlayout/solver/ArrayRow;
    //   772: aload #37
    //   774: aload #39
    //   776: aload #36
    //   778: aload #8
    //   780: fload #26
    //   782: invokevirtual createRowDimensionRatio : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;F)Landroidx/constraintlayout/solver/ArrayRow;
    //   785: invokevirtual addConstraint : (Landroidx/constraintlayout/solver/ArrayRow;)V
    //   788: iload #12
    //   790: istore #25
    //   792: iconst_0
    //   793: istore #28
    //   795: iload #15
    //   797: istore #12
    //   799: goto -> 817
    //   802: iconst_1
    //   803: istore #5
    //   805: iload #22
    //   807: istore #28
    //   809: iload #12
    //   811: istore #25
    //   813: iload #15
    //   815: istore #12
    //   817: iload #27
    //   819: ifeq -> 2220
    //   822: iload #19
    //   824: ifeq -> 830
    //   827: goto -> 2220
    //   830: iload #35
    //   832: ifne -> 848
    //   835: iload #33
    //   837: ifne -> 848
    //   840: iload #34
    //   842: ifne -> 848
    //   845: goto -> 2115
    //   848: iload #35
    //   850: ifeq -> 861
    //   853: iload #33
    //   855: ifne -> 861
    //   858: goto -> 2115
    //   861: iload #35
    //   863: ifne -> 962
    //   866: iload #33
    //   868: ifeq -> 962
    //   871: aload_1
    //   872: aload #37
    //   874: aload #38
    //   876: aload #11
    //   878: invokevirtual getMargin : ()I
    //   881: ineg
    //   882: bipush #8
    //   884: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   887: pop
    //   888: iload_3
    //   889: ifeq -> 2115
    //   892: aload_0
    //   893: getfield OPTIMIZE_WRAP : Z
    //   896: ifeq -> 949
    //   899: aload #39
    //   901: getfield isFinalValue : Z
    //   904: ifeq -> 949
    //   907: aload_0
    //   908: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   911: astore #8
    //   913: aload #8
    //   915: ifnull -> 949
    //   918: aload #8
    //   920: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   923: astore #6
    //   925: iload_2
    //   926: ifeq -> 939
    //   929: aload #6
    //   931: aload #10
    //   933: invokevirtual addHorizontalWrapMinVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   936: goto -> 2115
    //   939: aload #6
    //   941: aload #10
    //   943: invokevirtual addVerticalWrapMinVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   946: goto -> 2115
    //   949: aload_1
    //   950: aload #39
    //   952: aload #6
    //   954: iconst_0
    //   955: iconst_5
    //   956: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   959: goto -> 2115
    //   962: iload #35
    //   964: ifeq -> 2115
    //   967: iload #33
    //   969: ifeq -> 2115
    //   972: aload #10
    //   974: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   977: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   980: astore #8
    //   982: aload #11
    //   984: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   987: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   990: astore #36
    //   992: aload_0
    //   993: invokevirtual getParent : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   996: astore #41
    //   998: bipush #6
    //   1000: istore #31
    //   1002: iload #28
    //   1004: ifeq -> 1438
    //   1007: iload #30
    //   1009: ifne -> 1163
    //   1012: iload #12
    //   1014: ifne -> 1092
    //   1017: iload #25
    //   1019: ifne -> 1092
    //   1022: aload #40
    //   1024: getfield isFinalValue : Z
    //   1027: ifeq -> 1072
    //   1030: aload #38
    //   1032: getfield isFinalValue : Z
    //   1035: ifeq -> 1072
    //   1038: aload_1
    //   1039: aload #39
    //   1041: aload #40
    //   1043: aload #10
    //   1045: invokevirtual getMargin : ()I
    //   1048: bipush #8
    //   1050: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1053: pop
    //   1054: aload_1
    //   1055: aload #37
    //   1057: aload #38
    //   1059: aload #11
    //   1061: invokevirtual getMargin : ()I
    //   1064: ineg
    //   1065: bipush #8
    //   1067: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1070: pop
    //   1071: return
    //   1072: iconst_0
    //   1073: istore #15
    //   1075: iconst_1
    //   1076: istore #22
    //   1078: iconst_0
    //   1079: istore #12
    //   1081: bipush #8
    //   1083: istore #23
    //   1085: bipush #8
    //   1087: istore #13
    //   1089: goto -> 1107
    //   1092: iconst_1
    //   1093: istore #15
    //   1095: iconst_0
    //   1096: istore #22
    //   1098: iconst_1
    //   1099: istore #12
    //   1101: iconst_5
    //   1102: istore #23
    //   1104: iconst_5
    //   1105: istore #13
    //   1107: aload #8
    //   1109: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1112: ifne -> 1129
    //   1115: aload #36
    //   1117: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1120: ifeq -> 1126
    //   1123: goto -> 1129
    //   1126: goto -> 1132
    //   1129: iconst_4
    //   1130: istore #13
    //   1132: bipush #6
    //   1134: istore #32
    //   1136: iload #23
    //   1138: istore #29
    //   1140: iload #15
    //   1142: istore #24
    //   1144: iload #12
    //   1146: istore #15
    //   1148: iload #22
    //   1150: istore #23
    //   1152: iload #29
    //   1154: istore #12
    //   1156: iload #32
    //   1158: istore #22
    //   1160: goto -> 1548
    //   1163: iload #30
    //   1165: iconst_1
    //   1166: if_icmpne -> 1185
    //   1169: iconst_1
    //   1170: istore #15
    //   1172: iconst_1
    //   1173: istore #24
    //   1175: iconst_0
    //   1176: istore #23
    //   1178: bipush #8
    //   1180: istore #12
    //   1182: goto -> 1541
    //   1185: iload #30
    //   1187: iconst_3
    //   1188: if_icmpne -> 1429
    //   1191: aload_0
    //   1192: getfield mResolvedDimensionRatioSide : I
    //   1195: iconst_m1
    //   1196: if_icmpne -> 1247
    //   1199: iload #20
    //   1201: ifeq -> 1224
    //   1204: bipush #8
    //   1206: istore #12
    //   1208: iload_3
    //   1209: ifeq -> 1218
    //   1212: iconst_5
    //   1213: istore #22
    //   1215: goto -> 1232
    //   1218: iconst_4
    //   1219: istore #22
    //   1221: goto -> 1232
    //   1224: bipush #8
    //   1226: istore #12
    //   1228: bipush #8
    //   1230: istore #22
    //   1232: iconst_1
    //   1233: istore #23
    //   1235: iconst_1
    //   1236: istore #24
    //   1238: iconst_1
    //   1239: istore #15
    //   1241: iconst_5
    //   1242: istore #13
    //   1244: goto -> 1548
    //   1247: iload #17
    //   1249: ifeq -> 1329
    //   1252: iload #23
    //   1254: iconst_2
    //   1255: if_icmpeq -> 1273
    //   1258: iload #23
    //   1260: iconst_1
    //   1261: if_icmpne -> 1267
    //   1264: goto -> 1273
    //   1267: iconst_0
    //   1268: istore #12
    //   1270: goto -> 1276
    //   1273: iconst_1
    //   1274: istore #12
    //   1276: iload #12
    //   1278: ifne -> 1291
    //   1281: bipush #8
    //   1283: istore #13
    //   1285: iconst_5
    //   1286: istore #12
    //   1288: goto -> 1297
    //   1291: iconst_5
    //   1292: istore #13
    //   1294: iconst_4
    //   1295: istore #12
    //   1297: iload #13
    //   1299: istore #22
    //   1301: iload #12
    //   1303: istore #13
    //   1305: iconst_1
    //   1306: istore #15
    //   1308: iconst_1
    //   1309: istore #24
    //   1311: iconst_1
    //   1312: istore #23
    //   1314: bipush #6
    //   1316: istore #29
    //   1318: iload #22
    //   1320: istore #12
    //   1322: iload #29
    //   1324: istore #22
    //   1326: goto -> 1548
    //   1329: iload #12
    //   1331: ifle -> 1344
    //   1334: iconst_5
    //   1335: istore #12
    //   1337: bipush #6
    //   1339: istore #22
    //   1341: goto -> 1232
    //   1344: iload #12
    //   1346: ifne -> 1417
    //   1349: iload #25
    //   1351: ifne -> 1417
    //   1354: iload #20
    //   1356: ifne -> 1382
    //   1359: iconst_1
    //   1360: istore #15
    //   1362: iconst_1
    //   1363: istore #24
    //   1365: iconst_1
    //   1366: istore #23
    //   1368: iconst_5
    //   1369: istore #12
    //   1371: bipush #6
    //   1373: istore #22
    //   1375: bipush #8
    //   1377: istore #13
    //   1379: goto -> 1548
    //   1382: aload #8
    //   1384: aload #41
    //   1386: if_acmpeq -> 1402
    //   1389: aload #36
    //   1391: aload #41
    //   1393: if_acmpeq -> 1402
    //   1396: iconst_4
    //   1397: istore #12
    //   1399: goto -> 1405
    //   1402: iconst_5
    //   1403: istore #12
    //   1405: iconst_1
    //   1406: istore #15
    //   1408: iconst_1
    //   1409: istore #24
    //   1411: iconst_1
    //   1412: istore #23
    //   1414: goto -> 1541
    //   1417: iconst_1
    //   1418: istore #15
    //   1420: iconst_1
    //   1421: istore #24
    //   1423: iconst_1
    //   1424: istore #23
    //   1426: goto -> 1538
    //   1429: iconst_0
    //   1430: istore #15
    //   1432: iconst_0
    //   1433: istore #24
    //   1435: goto -> 1535
    //   1438: aload #40
    //   1440: getfield isFinalValue : Z
    //   1443: ifeq -> 1529
    //   1446: aload #38
    //   1448: getfield isFinalValue : Z
    //   1451: ifeq -> 1529
    //   1454: aload_1
    //   1455: aload #39
    //   1457: aload #40
    //   1459: aload #10
    //   1461: invokevirtual getMargin : ()I
    //   1464: fload #16
    //   1466: aload #38
    //   1468: aload #37
    //   1470: aload #11
    //   1472: invokevirtual getMargin : ()I
    //   1475: bipush #8
    //   1477: invokevirtual addCentering : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1480: iload_3
    //   1481: ifeq -> 1528
    //   1484: iload #5
    //   1486: ifeq -> 1528
    //   1489: aload #11
    //   1491: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1494: ifnull -> 1507
    //   1497: aload #11
    //   1499: invokevirtual getMargin : ()I
    //   1502: istore #12
    //   1504: goto -> 1510
    //   1507: iconst_0
    //   1508: istore #12
    //   1510: aload #38
    //   1512: aload #7
    //   1514: if_acmpeq -> 1528
    //   1517: aload_1
    //   1518: aload #7
    //   1520: aload #37
    //   1522: iload #12
    //   1524: iconst_5
    //   1525: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1528: return
    //   1529: iconst_1
    //   1530: istore #15
    //   1532: iconst_1
    //   1533: istore #24
    //   1535: iconst_0
    //   1536: istore #23
    //   1538: iconst_5
    //   1539: istore #12
    //   1541: bipush #6
    //   1543: istore #22
    //   1545: iconst_4
    //   1546: istore #13
    //   1548: iload #15
    //   1550: ifeq -> 1576
    //   1553: aload #40
    //   1555: aload #38
    //   1557: if_acmpne -> 1576
    //   1560: aload #8
    //   1562: aload #41
    //   1564: if_acmpeq -> 1576
    //   1567: iconst_0
    //   1568: istore #15
    //   1570: iconst_0
    //   1571: istore #29
    //   1573: goto -> 1579
    //   1576: iconst_1
    //   1577: istore #29
    //   1579: iload #24
    //   1581: ifeq -> 1658
    //   1584: iload #28
    //   1586: ifne -> 1629
    //   1589: iload #18
    //   1591: ifne -> 1629
    //   1594: iload #20
    //   1596: ifne -> 1629
    //   1599: aload #40
    //   1601: aload #6
    //   1603: if_acmpne -> 1629
    //   1606: aload #38
    //   1608: aload #7
    //   1610: if_acmpne -> 1629
    //   1613: iconst_0
    //   1614: istore_3
    //   1615: bipush #8
    //   1617: istore #12
    //   1619: bipush #8
    //   1621: istore #22
    //   1623: iconst_0
    //   1624: istore #29
    //   1626: goto -> 1629
    //   1629: aload_1
    //   1630: aload #39
    //   1632: aload #40
    //   1634: aload #10
    //   1636: invokevirtual getMargin : ()I
    //   1639: fload #16
    //   1641: aload #38
    //   1643: aload #37
    //   1645: aload #11
    //   1647: invokevirtual getMargin : ()I
    //   1650: iload #22
    //   1652: invokevirtual addCentering : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1655: goto -> 1658
    //   1658: aload_0
    //   1659: getfield mVisibility : I
    //   1662: bipush #8
    //   1664: if_icmpne -> 1676
    //   1667: aload #11
    //   1669: invokevirtual hasDependents : ()Z
    //   1672: ifne -> 1676
    //   1675: return
    //   1676: iload #15
    //   1678: ifeq -> 1757
    //   1681: iload_3
    //   1682: ifeq -> 1723
    //   1685: aload #40
    //   1687: aload #38
    //   1689: if_acmpeq -> 1723
    //   1692: iload #28
    //   1694: ifne -> 1723
    //   1697: aload #8
    //   1699: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1702: ifne -> 1716
    //   1705: aload #36
    //   1707: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1710: ifeq -> 1723
    //   1713: goto -> 1716
    //   1716: bipush #6
    //   1718: istore #12
    //   1720: goto -> 1723
    //   1723: aload_1
    //   1724: aload #39
    //   1726: aload #40
    //   1728: aload #10
    //   1730: invokevirtual getMargin : ()I
    //   1733: iload #12
    //   1735: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1738: aload_1
    //   1739: aload #37
    //   1741: aload #38
    //   1743: aload #11
    //   1745: invokevirtual getMargin : ()I
    //   1748: ineg
    //   1749: iload #12
    //   1751: invokevirtual addLowerThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1754: goto -> 1757
    //   1757: iload_3
    //   1758: ifeq -> 1796
    //   1761: iload #21
    //   1763: ifeq -> 1796
    //   1766: aload #8
    //   1768: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1771: ifne -> 1796
    //   1774: aload #36
    //   1776: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1779: ifne -> 1796
    //   1782: bipush #6
    //   1784: istore #15
    //   1786: bipush #6
    //   1788: istore #12
    //   1790: iconst_1
    //   1791: istore #29
    //   1793: goto -> 1804
    //   1796: iload #12
    //   1798: istore #15
    //   1800: iload #13
    //   1802: istore #12
    //   1804: iload #29
    //   1806: ifeq -> 1998
    //   1809: iload #23
    //   1811: ifeq -> 1914
    //   1814: iload #20
    //   1816: ifeq -> 1824
    //   1819: iload #4
    //   1821: ifeq -> 1914
    //   1824: iload #31
    //   1826: istore #13
    //   1828: aload #8
    //   1830: aload #41
    //   1832: if_acmpeq -> 1853
    //   1835: aload #36
    //   1837: aload #41
    //   1839: if_acmpne -> 1849
    //   1842: iload #31
    //   1844: istore #13
    //   1846: goto -> 1853
    //   1849: iload #12
    //   1851: istore #13
    //   1853: aload #8
    //   1855: instanceof androidx/constraintlayout/solver/widgets/Guideline
    //   1858: ifne -> 1869
    //   1861: aload #36
    //   1863: instanceof androidx/constraintlayout/solver/widgets/Guideline
    //   1866: ifeq -> 1872
    //   1869: iconst_5
    //   1870: istore #13
    //   1872: aload #8
    //   1874: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1877: ifne -> 1888
    //   1880: aload #36
    //   1882: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   1885: ifeq -> 1891
    //   1888: iconst_5
    //   1889: istore #13
    //   1891: iload #20
    //   1893: ifeq -> 1902
    //   1896: iconst_5
    //   1897: istore #13
    //   1899: goto -> 1902
    //   1902: iload #13
    //   1904: iload #12
    //   1906: invokestatic max : (II)I
    //   1909: istore #13
    //   1911: goto -> 1918
    //   1914: iload #12
    //   1916: istore #13
    //   1918: iload #13
    //   1920: istore #12
    //   1922: iload_3
    //   1923: ifeq -> 1965
    //   1926: iload #15
    //   1928: iload #13
    //   1930: invokestatic min : (II)I
    //   1933: istore #12
    //   1935: iload #17
    //   1937: ifeq -> 1965
    //   1940: iload #20
    //   1942: ifne -> 1965
    //   1945: aload #8
    //   1947: aload #41
    //   1949: if_acmpeq -> 1959
    //   1952: aload #36
    //   1954: aload #41
    //   1956: if_acmpne -> 1965
    //   1959: iconst_4
    //   1960: istore #12
    //   1962: goto -> 1965
    //   1965: aload_1
    //   1966: aload #39
    //   1968: aload #40
    //   1970: aload #10
    //   1972: invokevirtual getMargin : ()I
    //   1975: iload #12
    //   1977: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1980: pop
    //   1981: aload_1
    //   1982: aload #37
    //   1984: aload #38
    //   1986: aload #11
    //   1988: invokevirtual getMargin : ()I
    //   1991: ineg
    //   1992: iload #12
    //   1994: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1997: pop
    //   1998: iload_3
    //   1999: ifeq -> 2040
    //   2002: aload #6
    //   2004: aload #40
    //   2006: if_acmpne -> 2019
    //   2009: aload #10
    //   2011: invokevirtual getMargin : ()I
    //   2014: istore #12
    //   2016: goto -> 2022
    //   2019: iconst_0
    //   2020: istore #12
    //   2022: aload #40
    //   2024: aload #6
    //   2026: if_acmpeq -> 2040
    //   2029: aload_1
    //   2030: aload #39
    //   2032: aload #6
    //   2034: iload #12
    //   2036: iconst_5
    //   2037: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2040: iload_3
    //   2041: istore #4
    //   2043: iload_3
    //   2044: ifeq -> 2118
    //   2047: iload_3
    //   2048: istore #4
    //   2050: iload #28
    //   2052: ifeq -> 2118
    //   2055: iload_3
    //   2056: istore #4
    //   2058: iload #14
    //   2060: ifne -> 2118
    //   2063: iload_3
    //   2064: istore #4
    //   2066: iload #25
    //   2068: ifne -> 2118
    //   2071: iload #28
    //   2073: ifeq -> 2099
    //   2076: iload #30
    //   2078: iconst_3
    //   2079: if_icmpne -> 2099
    //   2082: aload_1
    //   2083: aload #37
    //   2085: aload #39
    //   2087: iconst_0
    //   2088: bipush #8
    //   2090: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2093: iload_3
    //   2094: istore #4
    //   2096: goto -> 2118
    //   2099: aload_1
    //   2100: aload #37
    //   2102: aload #39
    //   2104: iconst_0
    //   2105: iconst_5
    //   2106: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2109: iload_3
    //   2110: istore #4
    //   2112: goto -> 2118
    //   2115: iload_3
    //   2116: istore #4
    //   2118: iload #4
    //   2120: ifeq -> 2219
    //   2123: iload #5
    //   2125: ifeq -> 2219
    //   2128: aload #11
    //   2130: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2133: ifnull -> 2146
    //   2136: aload #11
    //   2138: invokevirtual getMargin : ()I
    //   2141: istore #12
    //   2143: goto -> 2149
    //   2146: iconst_0
    //   2147: istore #12
    //   2149: aload #38
    //   2151: aload #7
    //   2153: if_acmpeq -> 2219
    //   2156: aload_0
    //   2157: getfield OPTIMIZE_WRAP : Z
    //   2160: ifeq -> 2208
    //   2163: aload #37
    //   2165: getfield isFinalValue : Z
    //   2168: ifeq -> 2208
    //   2171: aload_0
    //   2172: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2175: astore #6
    //   2177: aload #6
    //   2179: ifnull -> 2208
    //   2182: aload #6
    //   2184: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   2187: astore_1
    //   2188: iload_2
    //   2189: ifeq -> 2201
    //   2192: aload_1
    //   2193: aload #11
    //   2195: invokevirtual addHorizontalWrapMaxVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   2198: goto -> 2207
    //   2201: aload_1
    //   2202: aload #11
    //   2204: invokevirtual addVerticalWrapMaxVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   2207: return
    //   2208: aload_1
    //   2209: aload #7
    //   2211: aload #37
    //   2213: iload #12
    //   2215: iconst_5
    //   2216: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2219: return
    //   2220: iconst_1
    //   2221: istore #13
    //   2223: iload #29
    //   2225: iconst_2
    //   2226: if_icmpge -> 2366
    //   2229: iload_3
    //   2230: ifeq -> 2366
    //   2233: iload #5
    //   2235: ifeq -> 2366
    //   2238: aload_1
    //   2239: aload #39
    //   2241: aload #6
    //   2243: iconst_0
    //   2244: bipush #8
    //   2246: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2249: iload_2
    //   2250: ifne -> 2272
    //   2253: aload_0
    //   2254: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2257: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2260: ifnonnull -> 2266
    //   2263: goto -> 2272
    //   2266: iconst_0
    //   2267: istore #12
    //   2269: goto -> 2275
    //   2272: iconst_1
    //   2273: istore #12
    //   2275: iload_2
    //   2276: ifne -> 2350
    //   2279: aload_0
    //   2280: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2283: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2286: ifnull -> 2350
    //   2289: aload_0
    //   2290: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2293: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2296: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2299: astore #6
    //   2301: aload #6
    //   2303: getfield mDimensionRatio : F
    //   2306: fconst_0
    //   2307: fcmpl
    //   2308: ifeq -> 2344
    //   2311: aload #6
    //   2313: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2316: iconst_0
    //   2317: aaload
    //   2318: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2321: if_acmpne -> 2344
    //   2324: aload #6
    //   2326: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2329: iconst_1
    //   2330: aaload
    //   2331: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2334: if_acmpne -> 2344
    //   2337: iload #13
    //   2339: istore #12
    //   2341: goto -> 2350
    //   2344: iconst_0
    //   2345: istore #12
    //   2347: goto -> 2350
    //   2350: iload #12
    //   2352: ifeq -> 2366
    //   2355: aload_1
    //   2356: aload #7
    //   2358: aload #37
    //   2360: iconst_0
    //   2361: bipush #8
    //   2363: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2366: return
  }
  
  private boolean isChainHead(int paramInt) {
    paramInt *= 2;
    ConstraintAnchor constraintAnchor = (this.mListAnchors[paramInt]).mTarget;
    null = true;
    if (constraintAnchor != null) {
      ConstraintAnchor constraintAnchor1 = (this.mListAnchors[paramInt]).mTarget.mTarget;
      ConstraintAnchor[] arrayOfConstraintAnchor = this.mListAnchors;
      if (constraintAnchor1 != arrayOfConstraintAnchor[paramInt])
        if ((arrayOfConstraintAnchor[++paramInt]).mTarget != null && (this.mListAnchors[paramInt]).mTarget.mTarget == this.mListAnchors[paramInt])
          return null;  
    } 
    return false;
  }
  
  public void addChildrenToSolverByDependency(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, HashSet<ConstraintWidget> paramHashSet, int paramInt, boolean paramBoolean) {
    if (paramBoolean) {
      if (!paramHashSet.contains(this))
        return; 
      Optimizer.checkMatchParent(paramConstraintWidgetContainer, paramLinearSystem, this);
      paramHashSet.remove(this);
      addToSolver(paramLinearSystem, paramConstraintWidgetContainer.optimizeFor(64));
    } 
    if (paramInt == 0) {
      HashSet<ConstraintAnchor> hashSet = this.mLeft.getDependents();
      if (hashSet != null) {
        Iterator<ConstraintAnchor> iterator = hashSet.iterator();
        while (iterator.hasNext())
          ((ConstraintAnchor)iterator.next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true); 
      } 
      hashSet = this.mRight.getDependents();
      if (hashSet != null) {
        Iterator<ConstraintAnchor> iterator = hashSet.iterator();
        while (iterator.hasNext())
          ((ConstraintAnchor)iterator.next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true); 
      } 
    } else {
      HashSet<ConstraintAnchor> hashSet = this.mTop.getDependents();
      if (hashSet != null) {
        Iterator<ConstraintAnchor> iterator = hashSet.iterator();
        while (iterator.hasNext())
          ((ConstraintAnchor)iterator.next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true); 
      } 
      hashSet = this.mBottom.getDependents();
      if (hashSet != null) {
        Iterator<ConstraintAnchor> iterator = hashSet.iterator();
        while (iterator.hasNext())
          ((ConstraintAnchor)iterator.next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true); 
      } 
      hashSet = this.mBaseline.getDependents();
      if (hashSet != null) {
        Iterator<ConstraintAnchor> iterator = hashSet.iterator();
        while (true) {
          if (iterator.hasNext()) {
            ConstraintWidget constraintWidget = ((ConstraintAnchor)iterator.next()).mOwner;
            try {
              constraintWidget.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true);
            } finally {}
            continue;
          } 
          return;
        } 
      } 
    } 
  }
  
  boolean addFirst() {
    return (this instanceof VirtualLayout || this instanceof Guideline);
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean) {
    // Byte code:
    //   0: aload_1
    //   1: aload_0
    //   2: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   5: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   8: astore #28
    //   10: aload_1
    //   11: aload_0
    //   12: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   15: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   18: astore #27
    //   20: aload_1
    //   21: aload_0
    //   22: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   25: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   28: astore #29
    //   30: aload_1
    //   31: aload_0
    //   32: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   35: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   38: astore #26
    //   40: aload_1
    //   41: aload_0
    //   42: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   45: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   48: astore #30
    //   50: aload_0
    //   51: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   54: astore #24
    //   56: aload #24
    //   58: ifnull -> 132
    //   61: aload #24
    //   63: ifnull -> 85
    //   66: aload #24
    //   68: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   71: iconst_0
    //   72: aaload
    //   73: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   76: if_acmpne -> 85
    //   79: iconst_1
    //   80: istore #11
    //   82: goto -> 88
    //   85: iconst_0
    //   86: istore #11
    //   88: aload_0
    //   89: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   92: astore #24
    //   94: aload #24
    //   96: ifnull -> 118
    //   99: aload #24
    //   101: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   104: iconst_1
    //   105: aaload
    //   106: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   109: if_acmpne -> 118
    //   112: iconst_1
    //   113: istore #12
    //   115: goto -> 121
    //   118: iconst_0
    //   119: istore #12
    //   121: iload #11
    //   123: istore #14
    //   125: iload #12
    //   127: istore #13
    //   129: goto -> 138
    //   132: iconst_0
    //   133: istore #14
    //   135: iconst_0
    //   136: istore #13
    //   138: aload_0
    //   139: getfield mVisibility : I
    //   142: bipush #8
    //   144: if_icmpne -> 175
    //   147: aload_0
    //   148: invokevirtual hasDependencies : ()Z
    //   151: ifne -> 175
    //   154: aload_0
    //   155: getfield mIsInBarrier : [Z
    //   158: astore #24
    //   160: aload #24
    //   162: iconst_0
    //   163: baload
    //   164: ifne -> 175
    //   167: aload #24
    //   169: iconst_1
    //   170: baload
    //   171: ifne -> 175
    //   174: return
    //   175: aload_0
    //   176: getfield resolvedHorizontal : Z
    //   179: ifne -> 189
    //   182: aload_0
    //   183: getfield resolvedVertical : Z
    //   186: ifeq -> 439
    //   189: aload_0
    //   190: getfield resolvedHorizontal : Z
    //   193: ifeq -> 289
    //   196: aload_1
    //   197: aload #28
    //   199: aload_0
    //   200: getfield mX : I
    //   203: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   206: aload_1
    //   207: aload #27
    //   209: aload_0
    //   210: getfield mX : I
    //   213: aload_0
    //   214: getfield mWidth : I
    //   217: iadd
    //   218: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   221: iload #14
    //   223: ifeq -> 289
    //   226: aload_0
    //   227: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   230: astore #24
    //   232: aload #24
    //   234: ifnull -> 289
    //   237: aload_0
    //   238: getfield OPTIMIZE_WRAP_ON_RESOLVED : Z
    //   241: ifeq -> 272
    //   244: aload #24
    //   246: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   249: astore #24
    //   251: aload #24
    //   253: aload_0
    //   254: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   257: invokevirtual addVerticalWrapMinVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   260: aload #24
    //   262: aload_0
    //   263: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   266: invokevirtual addHorizontalWrapMaxVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   269: goto -> 289
    //   272: aload_1
    //   273: aload_1
    //   274: aload #24
    //   276: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   279: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   282: aload #27
    //   284: iconst_0
    //   285: iconst_5
    //   286: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   289: aload_0
    //   290: getfield resolvedVertical : Z
    //   293: ifeq -> 414
    //   296: aload_1
    //   297: aload #29
    //   299: aload_0
    //   300: getfield mY : I
    //   303: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   306: aload_1
    //   307: aload #26
    //   309: aload_0
    //   310: getfield mY : I
    //   313: aload_0
    //   314: getfield mHeight : I
    //   317: iadd
    //   318: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   321: aload_0
    //   322: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   325: invokevirtual hasDependents : ()Z
    //   328: ifeq -> 346
    //   331: aload_1
    //   332: aload #30
    //   334: aload_0
    //   335: getfield mY : I
    //   338: aload_0
    //   339: getfield mBaselineDistance : I
    //   342: iadd
    //   343: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   346: iload #13
    //   348: ifeq -> 414
    //   351: aload_0
    //   352: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   355: astore #24
    //   357: aload #24
    //   359: ifnull -> 414
    //   362: aload_0
    //   363: getfield OPTIMIZE_WRAP_ON_RESOLVED : Z
    //   366: ifeq -> 397
    //   369: aload #24
    //   371: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   374: astore #24
    //   376: aload #24
    //   378: aload_0
    //   379: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   382: invokevirtual addVerticalWrapMinVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   385: aload #24
    //   387: aload_0
    //   388: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   391: invokevirtual addVerticalWrapMaxVariable : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)V
    //   394: goto -> 414
    //   397: aload_1
    //   398: aload_1
    //   399: aload #24
    //   401: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   404: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   407: aload #26
    //   409: iconst_0
    //   410: iconst_5
    //   411: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   414: aload_0
    //   415: getfield resolvedHorizontal : Z
    //   418: ifeq -> 439
    //   421: aload_0
    //   422: getfield resolvedVertical : Z
    //   425: ifeq -> 439
    //   428: aload_0
    //   429: iconst_0
    //   430: putfield resolvedHorizontal : Z
    //   433: aload_0
    //   434: iconst_0
    //   435: putfield resolvedVertical : Z
    //   438: return
    //   439: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   442: ifnull -> 462
    //   445: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   448: astore #24
    //   450: aload #24
    //   452: aload #24
    //   454: getfield widgets : J
    //   457: lconst_1
    //   458: ladd
    //   459: putfield widgets : J
    //   462: iload_2
    //   463: ifeq -> 737
    //   466: aload_0
    //   467: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   470: astore #24
    //   472: aload #24
    //   474: ifnull -> 737
    //   477: aload_0
    //   478: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   481: ifnull -> 737
    //   484: aload #24
    //   486: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   489: getfield resolved : Z
    //   492: ifeq -> 737
    //   495: aload_0
    //   496: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   499: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   502: getfield resolved : Z
    //   505: ifeq -> 737
    //   508: aload_0
    //   509: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   512: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   515: getfield resolved : Z
    //   518: ifeq -> 737
    //   521: aload_0
    //   522: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   525: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   528: getfield resolved : Z
    //   531: ifeq -> 737
    //   534: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   537: ifnull -> 557
    //   540: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   543: astore #24
    //   545: aload #24
    //   547: aload #24
    //   549: getfield graphSolved : J
    //   552: lconst_1
    //   553: ladd
    //   554: putfield graphSolved : J
    //   557: aload_1
    //   558: aload #28
    //   560: aload_0
    //   561: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   564: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   567: getfield value : I
    //   570: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   573: aload_1
    //   574: aload #27
    //   576: aload_0
    //   577: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   580: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   583: getfield value : I
    //   586: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   589: aload_1
    //   590: aload #29
    //   592: aload_0
    //   593: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   596: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   599: getfield value : I
    //   602: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   605: aload_1
    //   606: aload #26
    //   608: aload_0
    //   609: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   612: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   615: getfield value : I
    //   618: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   621: aload_1
    //   622: aload #30
    //   624: aload_0
    //   625: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   628: getfield baseline : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   631: getfield value : I
    //   634: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   637: aload_0
    //   638: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   641: ifnull -> 726
    //   644: iload #14
    //   646: ifeq -> 685
    //   649: aload_0
    //   650: getfield isTerminalWidget : [Z
    //   653: iconst_0
    //   654: baload
    //   655: ifeq -> 685
    //   658: aload_0
    //   659: invokevirtual isInHorizontalChain : ()Z
    //   662: ifne -> 685
    //   665: aload_1
    //   666: aload_1
    //   667: aload_0
    //   668: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   671: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   674: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   677: aload #27
    //   679: iconst_0
    //   680: bipush #8
    //   682: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   685: iload #13
    //   687: ifeq -> 726
    //   690: aload_0
    //   691: getfield isTerminalWidget : [Z
    //   694: iconst_1
    //   695: baload
    //   696: ifeq -> 726
    //   699: aload_0
    //   700: invokevirtual isInVerticalChain : ()Z
    //   703: ifne -> 726
    //   706: aload_1
    //   707: aload_1
    //   708: aload_0
    //   709: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   712: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   715: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   718: aload #26
    //   720: iconst_0
    //   721: bipush #8
    //   723: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   726: aload_0
    //   727: iconst_0
    //   728: putfield resolvedHorizontal : Z
    //   731: aload_0
    //   732: iconst_0
    //   733: putfield resolvedVertical : Z
    //   736: return
    //   737: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   740: ifnull -> 760
    //   743: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   746: astore #24
    //   748: aload #24
    //   750: aload #24
    //   752: getfield linearSolved : J
    //   755: lconst_1
    //   756: ladd
    //   757: putfield linearSolved : J
    //   760: aload_0
    //   761: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   764: ifnull -> 961
    //   767: aload_0
    //   768: iconst_0
    //   769: invokespecial isChainHead : (I)Z
    //   772: ifeq -> 793
    //   775: aload_0
    //   776: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   779: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   782: aload_0
    //   783: iconst_0
    //   784: invokevirtual addChain : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)V
    //   787: iconst_1
    //   788: istore #11
    //   790: goto -> 799
    //   793: aload_0
    //   794: invokevirtual isInHorizontalChain : ()Z
    //   797: istore #11
    //   799: aload_0
    //   800: iconst_1
    //   801: invokespecial isChainHead : (I)Z
    //   804: ifeq -> 825
    //   807: aload_0
    //   808: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   811: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   814: aload_0
    //   815: iconst_1
    //   816: invokevirtual addChain : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)V
    //   819: iconst_1
    //   820: istore #12
    //   822: goto -> 831
    //   825: aload_0
    //   826: invokevirtual isInVerticalChain : ()Z
    //   829: istore #12
    //   831: iload #11
    //   833: ifne -> 889
    //   836: iload #14
    //   838: ifeq -> 889
    //   841: aload_0
    //   842: getfield mVisibility : I
    //   845: bipush #8
    //   847: if_icmpeq -> 889
    //   850: aload_0
    //   851: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   854: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   857: ifnonnull -> 889
    //   860: aload_0
    //   861: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   864: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   867: ifnonnull -> 889
    //   870: aload_1
    //   871: aload_1
    //   872: aload_0
    //   873: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   876: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   879: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   882: aload #27
    //   884: iconst_0
    //   885: iconst_1
    //   886: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   889: iload #12
    //   891: ifne -> 954
    //   894: iload #13
    //   896: ifeq -> 954
    //   899: aload_0
    //   900: getfield mVisibility : I
    //   903: bipush #8
    //   905: if_icmpeq -> 954
    //   908: aload_0
    //   909: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   912: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   915: ifnonnull -> 954
    //   918: aload_0
    //   919: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   922: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   925: ifnonnull -> 954
    //   928: aload_0
    //   929: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   932: ifnonnull -> 954
    //   935: aload_1
    //   936: aload_1
    //   937: aload_0
    //   938: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   941: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   944: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   947: aload #26
    //   949: iconst_0
    //   950: iconst_1
    //   951: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   954: iload #11
    //   956: istore #15
    //   958: goto -> 967
    //   961: iconst_0
    //   962: istore #12
    //   964: iconst_0
    //   965: istore #15
    //   967: aload_0
    //   968: getfield mWidth : I
    //   971: istore #6
    //   973: aload_0
    //   974: getfield mMinWidth : I
    //   977: istore #5
    //   979: iload #6
    //   981: istore #4
    //   983: iload #6
    //   985: iload #5
    //   987: if_icmpge -> 994
    //   990: iload #5
    //   992: istore #4
    //   994: aload_0
    //   995: getfield mHeight : I
    //   998: istore #6
    //   1000: aload_0
    //   1001: getfield mMinHeight : I
    //   1004: istore #7
    //   1006: iload #6
    //   1008: istore #5
    //   1010: iload #6
    //   1012: iload #7
    //   1014: if_icmpge -> 1021
    //   1017: iload #7
    //   1019: istore #5
    //   1021: aload_0
    //   1022: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1025: iconst_0
    //   1026: aaload
    //   1027: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1030: if_acmpeq -> 1039
    //   1033: iconst_1
    //   1034: istore #11
    //   1036: goto -> 1042
    //   1039: iconst_0
    //   1040: istore #11
    //   1042: aload_0
    //   1043: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1046: iconst_1
    //   1047: aaload
    //   1048: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1051: if_acmpeq -> 1060
    //   1054: iconst_1
    //   1055: istore #16
    //   1057: goto -> 1063
    //   1060: iconst_0
    //   1061: istore #16
    //   1063: aload_0
    //   1064: aload_0
    //   1065: getfield mDimensionRatioSide : I
    //   1068: putfield mResolvedDimensionRatioSide : I
    //   1071: aload_0
    //   1072: getfield mDimensionRatio : F
    //   1075: fstore_3
    //   1076: aload_0
    //   1077: fload_3
    //   1078: putfield mResolvedDimensionRatio : F
    //   1081: aload_0
    //   1082: getfield mMatchConstraintDefaultWidth : I
    //   1085: istore #7
    //   1087: aload_0
    //   1088: getfield mMatchConstraintDefaultHeight : I
    //   1091: istore #8
    //   1093: fload_3
    //   1094: fconst_0
    //   1095: fcmpl
    //   1096: ifle -> 1443
    //   1099: aload_0
    //   1100: getfield mVisibility : I
    //   1103: bipush #8
    //   1105: if_icmpeq -> 1443
    //   1108: iload #7
    //   1110: istore #6
    //   1112: aload_0
    //   1113: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1116: iconst_0
    //   1117: aaload
    //   1118: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1121: if_acmpne -> 1136
    //   1124: iload #7
    //   1126: istore #6
    //   1128: iload #7
    //   1130: ifne -> 1136
    //   1133: iconst_3
    //   1134: istore #6
    //   1136: iload #8
    //   1138: istore #7
    //   1140: aload_0
    //   1141: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1144: iconst_1
    //   1145: aaload
    //   1146: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1149: if_acmpne -> 1164
    //   1152: iload #8
    //   1154: istore #7
    //   1156: iload #8
    //   1158: ifne -> 1164
    //   1161: iconst_3
    //   1162: istore #7
    //   1164: aload_0
    //   1165: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1168: iconst_0
    //   1169: aaload
    //   1170: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1173: if_acmpne -> 1215
    //   1176: aload_0
    //   1177: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1180: iconst_1
    //   1181: aaload
    //   1182: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1185: if_acmpne -> 1215
    //   1188: iload #6
    //   1190: iconst_3
    //   1191: if_icmpne -> 1215
    //   1194: iload #7
    //   1196: iconst_3
    //   1197: if_icmpne -> 1215
    //   1200: aload_0
    //   1201: iload #14
    //   1203: iload #13
    //   1205: iload #11
    //   1207: iload #16
    //   1209: invokevirtual setupDimensionRatio : (ZZZZ)V
    //   1212: goto -> 1413
    //   1215: aload_0
    //   1216: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1219: iconst_0
    //   1220: aaload
    //   1221: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1224: if_acmpne -> 1307
    //   1227: iload #6
    //   1229: iconst_3
    //   1230: if_icmpne -> 1307
    //   1233: aload_0
    //   1234: iconst_0
    //   1235: putfield mResolvedDimensionRatioSide : I
    //   1238: aload_0
    //   1239: getfield mResolvedDimensionRatio : F
    //   1242: aload_0
    //   1243: getfield mHeight : I
    //   1246: i2f
    //   1247: fmul
    //   1248: f2i
    //   1249: istore #4
    //   1251: aload_0
    //   1252: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1255: iconst_1
    //   1256: aaload
    //   1257: astore #25
    //   1259: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1262: astore #24
    //   1264: iload #7
    //   1266: istore #8
    //   1268: aload #25
    //   1270: aload #24
    //   1272: if_acmpeq -> 1300
    //   1275: iload #5
    //   1277: istore #6
    //   1279: iconst_0
    //   1280: istore #11
    //   1282: iconst_4
    //   1283: istore #7
    //   1285: iload #4
    //   1287: istore #5
    //   1289: iload #6
    //   1291: istore #4
    //   1293: iload #8
    //   1295: istore #6
    //   1297: goto -> 1462
    //   1300: iload #4
    //   1302: istore #8
    //   1304: goto -> 1417
    //   1307: aload_0
    //   1308: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1311: iconst_1
    //   1312: aaload
    //   1313: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1316: if_acmpne -> 1413
    //   1319: iload #7
    //   1321: iconst_3
    //   1322: if_icmpne -> 1413
    //   1325: aload_0
    //   1326: iconst_1
    //   1327: putfield mResolvedDimensionRatioSide : I
    //   1330: aload_0
    //   1331: getfield mDimensionRatioSide : I
    //   1334: iconst_m1
    //   1335: if_icmpne -> 1348
    //   1338: aload_0
    //   1339: fconst_1
    //   1340: aload_0
    //   1341: getfield mResolvedDimensionRatio : F
    //   1344: fdiv
    //   1345: putfield mResolvedDimensionRatio : F
    //   1348: aload_0
    //   1349: getfield mResolvedDimensionRatio : F
    //   1352: aload_0
    //   1353: getfield mWidth : I
    //   1356: i2f
    //   1357: fmul
    //   1358: f2i
    //   1359: istore #8
    //   1361: aload_0
    //   1362: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1365: iconst_0
    //   1366: aaload
    //   1367: astore #25
    //   1369: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1372: astore #24
    //   1374: aload #25
    //   1376: aload #24
    //   1378: if_acmpeq -> 1402
    //   1381: iload #6
    //   1383: istore #7
    //   1385: iconst_0
    //   1386: istore #11
    //   1388: iconst_4
    //   1389: istore #6
    //   1391: iload #4
    //   1393: istore #5
    //   1395: iload #8
    //   1397: istore #4
    //   1399: goto -> 1462
    //   1402: iload #4
    //   1404: istore #5
    //   1406: iload #8
    //   1408: istore #4
    //   1410: goto -> 1425
    //   1413: iload #4
    //   1415: istore #8
    //   1417: iload #5
    //   1419: istore #4
    //   1421: iload #8
    //   1423: istore #5
    //   1425: iload #6
    //   1427: istore #8
    //   1429: iload #7
    //   1431: istore #6
    //   1433: iconst_1
    //   1434: istore #11
    //   1436: iload #8
    //   1438: istore #7
    //   1440: goto -> 1462
    //   1443: iload #8
    //   1445: istore #6
    //   1447: iload #4
    //   1449: istore #8
    //   1451: iload #5
    //   1453: istore #4
    //   1455: iconst_0
    //   1456: istore #11
    //   1458: iload #8
    //   1460: istore #5
    //   1462: aload_0
    //   1463: getfield mResolvedMatchConstraintDefault : [I
    //   1466: astore #24
    //   1468: aload #24
    //   1470: iconst_0
    //   1471: iload #7
    //   1473: iastore
    //   1474: aload #24
    //   1476: iconst_1
    //   1477: iload #6
    //   1479: iastore
    //   1480: aload_0
    //   1481: iload #11
    //   1483: putfield mResolvedHasRatio : Z
    //   1486: iload #11
    //   1488: ifeq -> 1514
    //   1491: aload_0
    //   1492: getfield mResolvedDimensionRatioSide : I
    //   1495: istore #8
    //   1497: iload #8
    //   1499: ifeq -> 1508
    //   1502: iload #8
    //   1504: iconst_m1
    //   1505: if_icmpne -> 1514
    //   1508: iconst_1
    //   1509: istore #17
    //   1511: goto -> 1517
    //   1514: iconst_0
    //   1515: istore #17
    //   1517: iload #11
    //   1519: ifeq -> 1546
    //   1522: aload_0
    //   1523: getfield mResolvedDimensionRatioSide : I
    //   1526: istore #8
    //   1528: iload #8
    //   1530: iconst_1
    //   1531: if_icmpeq -> 1540
    //   1534: iload #8
    //   1536: iconst_m1
    //   1537: if_icmpne -> 1546
    //   1540: iconst_1
    //   1541: istore #16
    //   1543: goto -> 1549
    //   1546: iconst_0
    //   1547: istore #16
    //   1549: aload_0
    //   1550: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1553: iconst_0
    //   1554: aaload
    //   1555: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1558: if_acmpne -> 1574
    //   1561: aload_0
    //   1562: instanceof androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   1565: ifeq -> 1574
    //   1568: iconst_1
    //   1569: istore #18
    //   1571: goto -> 1577
    //   1574: iconst_0
    //   1575: istore #18
    //   1577: iload #18
    //   1579: ifeq -> 1588
    //   1582: iconst_0
    //   1583: istore #5
    //   1585: goto -> 1588
    //   1588: aload_0
    //   1589: getfield mCenter : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1592: invokevirtual isConnected : ()Z
    //   1595: iconst_1
    //   1596: ixor
    //   1597: istore #20
    //   1599: aload_0
    //   1600: getfield mIsInBarrier : [Z
    //   1603: astore #24
    //   1605: aload #24
    //   1607: iconst_0
    //   1608: baload
    //   1609: istore #22
    //   1611: aload #24
    //   1613: iconst_1
    //   1614: baload
    //   1615: istore #21
    //   1617: aload_0
    //   1618: getfield mHorizontalResolution : I
    //   1621: iconst_2
    //   1622: if_icmpeq -> 1957
    //   1625: aload_0
    //   1626: getfield resolvedHorizontal : Z
    //   1629: ifne -> 1957
    //   1632: iload_2
    //   1633: ifeq -> 1761
    //   1636: aload_0
    //   1637: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1640: astore #24
    //   1642: aload #24
    //   1644: ifnull -> 1761
    //   1647: aload #24
    //   1649: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1652: getfield resolved : Z
    //   1655: ifeq -> 1761
    //   1658: aload_0
    //   1659: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1662: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1665: getfield resolved : Z
    //   1668: ifne -> 1674
    //   1671: goto -> 1761
    //   1674: iload_2
    //   1675: ifeq -> 1957
    //   1678: aload_1
    //   1679: aload #28
    //   1681: aload_0
    //   1682: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1685: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1688: getfield value : I
    //   1691: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   1694: aload_1
    //   1695: aload #27
    //   1697: aload_0
    //   1698: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1701: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1704: getfield value : I
    //   1707: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   1710: aload_0
    //   1711: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1714: ifnull -> 1957
    //   1717: iload #14
    //   1719: ifeq -> 1957
    //   1722: aload_0
    //   1723: getfield isTerminalWidget : [Z
    //   1726: iconst_0
    //   1727: baload
    //   1728: ifeq -> 1957
    //   1731: aload_0
    //   1732: invokevirtual isInHorizontalChain : ()Z
    //   1735: ifne -> 1957
    //   1738: aload_1
    //   1739: aload_1
    //   1740: aload_0
    //   1741: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1744: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1747: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   1750: aload #27
    //   1752: iconst_0
    //   1753: bipush #8
    //   1755: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1758: goto -> 1957
    //   1761: aload_0
    //   1762: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1765: astore #24
    //   1767: aload #24
    //   1769: ifnull -> 1786
    //   1772: aload_1
    //   1773: aload #24
    //   1775: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1778: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   1781: astore #24
    //   1783: goto -> 1789
    //   1786: aconst_null
    //   1787: astore #24
    //   1789: aload_0
    //   1790: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1793: astore #25
    //   1795: aload #25
    //   1797: ifnull -> 1814
    //   1800: aload_1
    //   1801: aload #25
    //   1803: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1806: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   1809: astore #25
    //   1811: goto -> 1817
    //   1814: aconst_null
    //   1815: astore #25
    //   1817: aload_0
    //   1818: getfield isTerminalWidget : [Z
    //   1821: iconst_0
    //   1822: baload
    //   1823: istore #23
    //   1825: aload_0
    //   1826: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1829: astore #31
    //   1831: aload #31
    //   1833: iconst_0
    //   1834: aaload
    //   1835: astore #34
    //   1837: aload_0
    //   1838: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1841: astore #32
    //   1843: aload_0
    //   1844: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1847: astore #33
    //   1849: aload_0
    //   1850: getfield mX : I
    //   1853: istore #9
    //   1855: aload_0
    //   1856: getfield mMinWidth : I
    //   1859: istore #8
    //   1861: aload_0
    //   1862: getfield mMaxDimension : [I
    //   1865: iconst_0
    //   1866: iaload
    //   1867: istore #10
    //   1869: aload_0
    //   1870: getfield mHorizontalBiasPercent : F
    //   1873: fstore_3
    //   1874: aload #31
    //   1876: iconst_1
    //   1877: aaload
    //   1878: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1881: if_acmpne -> 1890
    //   1884: iconst_1
    //   1885: istore #19
    //   1887: goto -> 1893
    //   1890: iconst_0
    //   1891: istore #19
    //   1893: aload_0
    //   1894: aload_1
    //   1895: iconst_1
    //   1896: iload #14
    //   1898: iload #13
    //   1900: iload #23
    //   1902: aload #25
    //   1904: aload #24
    //   1906: aload #34
    //   1908: iload #18
    //   1910: aload #32
    //   1912: aload #33
    //   1914: iload #9
    //   1916: iload #5
    //   1918: iload #8
    //   1920: iload #10
    //   1922: fload_3
    //   1923: iload #17
    //   1925: iload #19
    //   1927: iload #15
    //   1929: iload #12
    //   1931: iload #22
    //   1933: iload #7
    //   1935: iload #6
    //   1937: aload_0
    //   1938: getfield mMatchConstraintMinWidth : I
    //   1941: aload_0
    //   1942: getfield mMatchConstraintMaxWidth : I
    //   1945: aload_0
    //   1946: getfield mMatchConstraintPercentWidth : F
    //   1949: iload #20
    //   1951: invokespecial applyConstraints : (Landroidx/constraintlayout/solver/LinearSystem;ZZZZLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ZLandroidx/constraintlayout/solver/widgets/ConstraintAnchor;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;IIIIFZZZZZIIIIFZ)V
    //   1954: goto -> 1957
    //   1957: iload_2
    //   1958: ifeq -> 2115
    //   1961: aload_0
    //   1962: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1965: astore #24
    //   1967: aload #24
    //   1969: ifnull -> 2112
    //   1972: aload #24
    //   1974: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1977: getfield resolved : Z
    //   1980: ifeq -> 2112
    //   1983: aload_0
    //   1984: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1987: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1990: getfield resolved : Z
    //   1993: ifeq -> 2112
    //   1996: aload_0
    //   1997: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   2000: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2003: getfield value : I
    //   2006: istore #5
    //   2008: aload_1
    //   2009: aload #29
    //   2011: iload #5
    //   2013: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   2016: aload_0
    //   2017: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   2020: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2023: getfield value : I
    //   2026: istore #5
    //   2028: aload_1
    //   2029: aload #26
    //   2031: iload #5
    //   2033: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   2036: aload_1
    //   2037: aload #30
    //   2039: aload_0
    //   2040: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   2043: getfield baseline : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2046: getfield value : I
    //   2049: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   2052: aload_0
    //   2053: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2056: astore #24
    //   2058: aload #24
    //   2060: ifnull -> 2106
    //   2063: iload #12
    //   2065: ifne -> 2106
    //   2068: iload #13
    //   2070: ifeq -> 2106
    //   2073: aload_0
    //   2074: getfield isTerminalWidget : [Z
    //   2077: iconst_1
    //   2078: baload
    //   2079: ifeq -> 2103
    //   2082: aload_1
    //   2083: aload_1
    //   2084: aload #24
    //   2086: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2089: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   2092: aload #26
    //   2094: iconst_0
    //   2095: bipush #8
    //   2097: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2100: goto -> 2106
    //   2103: goto -> 2106
    //   2106: iconst_0
    //   2107: istore #5
    //   2109: goto -> 2118
    //   2112: goto -> 2115
    //   2115: iconst_1
    //   2116: istore #5
    //   2118: aload_0
    //   2119: getfield mVerticalResolution : I
    //   2122: iconst_2
    //   2123: if_icmpne -> 2132
    //   2126: iconst_0
    //   2127: istore #5
    //   2129: goto -> 2132
    //   2132: iload #5
    //   2134: ifeq -> 2504
    //   2137: aload_0
    //   2138: getfield resolvedVertical : Z
    //   2141: ifne -> 2504
    //   2144: aload_0
    //   2145: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2148: iconst_1
    //   2149: aaload
    //   2150: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2153: if_acmpne -> 2168
    //   2156: aload_0
    //   2157: instanceof androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   2160: ifeq -> 2168
    //   2163: iconst_1
    //   2164: istore_2
    //   2165: goto -> 2170
    //   2168: iconst_0
    //   2169: istore_2
    //   2170: iload_2
    //   2171: ifeq -> 2177
    //   2174: iconst_0
    //   2175: istore #4
    //   2177: aload_0
    //   2178: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2181: astore #24
    //   2183: aload #24
    //   2185: ifnull -> 2202
    //   2188: aload_1
    //   2189: aload #24
    //   2191: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2194: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   2197: astore #24
    //   2199: goto -> 2205
    //   2202: aconst_null
    //   2203: astore #24
    //   2205: aload_0
    //   2206: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2209: astore #25
    //   2211: aload #25
    //   2213: ifnull -> 2230
    //   2216: aload_1
    //   2217: aload #25
    //   2219: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2222: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   2225: astore #25
    //   2227: goto -> 2233
    //   2230: aconst_null
    //   2231: astore #25
    //   2233: aload_0
    //   2234: getfield mBaselineDistance : I
    //   2237: ifgt -> 2249
    //   2240: aload_0
    //   2241: getfield mVisibility : I
    //   2244: bipush #8
    //   2246: if_icmpne -> 2361
    //   2249: aload_0
    //   2250: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2253: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2256: ifnull -> 2322
    //   2259: aload_1
    //   2260: aload #30
    //   2262: aload #29
    //   2264: aload_0
    //   2265: invokevirtual getBaselineDistance : ()I
    //   2268: bipush #8
    //   2270: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   2273: pop
    //   2274: aload_1
    //   2275: aload #30
    //   2277: aload_1
    //   2278: aload_0
    //   2279: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2282: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2285: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   2288: iconst_0
    //   2289: bipush #8
    //   2291: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   2294: pop
    //   2295: iload #13
    //   2297: ifeq -> 2316
    //   2300: aload_1
    //   2301: aload #24
    //   2303: aload_1
    //   2304: aload_0
    //   2305: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2308: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   2311: iconst_0
    //   2312: iconst_5
    //   2313: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2316: iconst_0
    //   2317: istore #17
    //   2319: goto -> 2365
    //   2322: aload_0
    //   2323: getfield mVisibility : I
    //   2326: bipush #8
    //   2328: if_icmpne -> 2346
    //   2331: aload_1
    //   2332: aload #30
    //   2334: aload #29
    //   2336: iconst_0
    //   2337: bipush #8
    //   2339: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   2342: pop
    //   2343: goto -> 2361
    //   2346: aload_1
    //   2347: aload #30
    //   2349: aload #29
    //   2351: aload_0
    //   2352: invokevirtual getBaselineDistance : ()I
    //   2355: bipush #8
    //   2357: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   2360: pop
    //   2361: iload #20
    //   2363: istore #17
    //   2365: aload_0
    //   2366: getfield isTerminalWidget : [Z
    //   2369: iconst_1
    //   2370: baload
    //   2371: istore #19
    //   2373: aload_0
    //   2374: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2377: astore #33
    //   2379: aload #33
    //   2381: iconst_1
    //   2382: aaload
    //   2383: astore #31
    //   2385: aload_0
    //   2386: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2389: astore #32
    //   2391: aload_0
    //   2392: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2395: astore #30
    //   2397: aload_0
    //   2398: getfield mY : I
    //   2401: istore #8
    //   2403: aload_0
    //   2404: getfield mMinHeight : I
    //   2407: istore #9
    //   2409: aload_0
    //   2410: getfield mMaxDimension : [I
    //   2413: iconst_1
    //   2414: iaload
    //   2415: istore #5
    //   2417: aload_0
    //   2418: getfield mVerticalBiasPercent : F
    //   2421: fstore_3
    //   2422: aload #33
    //   2424: iconst_0
    //   2425: aaload
    //   2426: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2429: if_acmpne -> 2438
    //   2432: iconst_1
    //   2433: istore #18
    //   2435: goto -> 2441
    //   2438: iconst_0
    //   2439: istore #18
    //   2441: aload_0
    //   2442: aload_1
    //   2443: iconst_0
    //   2444: iload #13
    //   2446: iload #14
    //   2448: iload #19
    //   2450: aload #25
    //   2452: aload #24
    //   2454: aload #31
    //   2456: iload_2
    //   2457: aload #32
    //   2459: aload #30
    //   2461: iload #8
    //   2463: iload #4
    //   2465: iload #9
    //   2467: iload #5
    //   2469: fload_3
    //   2470: iload #16
    //   2472: iload #18
    //   2474: iload #12
    //   2476: iload #15
    //   2478: iload #21
    //   2480: iload #6
    //   2482: iload #7
    //   2484: aload_0
    //   2485: getfield mMatchConstraintMinHeight : I
    //   2488: aload_0
    //   2489: getfield mMatchConstraintMaxHeight : I
    //   2492: aload_0
    //   2493: getfield mMatchConstraintPercentHeight : F
    //   2496: iload #17
    //   2498: invokespecial applyConstraints : (Landroidx/constraintlayout/solver/LinearSystem;ZZZZLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ZLandroidx/constraintlayout/solver/widgets/ConstraintAnchor;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;IIIIFZZZZZIIIIFZ)V
    //   2501: goto -> 2504
    //   2504: iload #11
    //   2506: ifeq -> 2559
    //   2509: aload_0
    //   2510: getfield mResolvedDimensionRatioSide : I
    //   2513: iconst_1
    //   2514: if_icmpne -> 2538
    //   2517: aload_1
    //   2518: aload #26
    //   2520: aload #29
    //   2522: aload #27
    //   2524: aload #28
    //   2526: aload_0
    //   2527: getfield mResolvedDimensionRatio : F
    //   2530: bipush #8
    //   2532: invokevirtual addRatio : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;FI)V
    //   2535: goto -> 2559
    //   2538: aload_1
    //   2539: aload #27
    //   2541: aload #28
    //   2543: aload #26
    //   2545: aload #29
    //   2547: aload_0
    //   2548: getfield mResolvedDimensionRatio : F
    //   2551: bipush #8
    //   2553: invokevirtual addRatio : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;FI)V
    //   2556: goto -> 2559
    //   2559: aload_0
    //   2560: getfield mCenter : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2563: invokevirtual isConnected : ()Z
    //   2566: ifeq -> 2604
    //   2569: aload_1
    //   2570: aload_0
    //   2571: aload_0
    //   2572: getfield mCenter : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2575: invokevirtual getTarget : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2578: invokevirtual getOwner : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2581: aload_0
    //   2582: getfield mCircleConstraintAngle : F
    //   2585: ldc_w 90.0
    //   2588: fadd
    //   2589: f2d
    //   2590: invokestatic toRadians : (D)D
    //   2593: d2f
    //   2594: aload_0
    //   2595: getfield mCenter : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2598: invokevirtual getMargin : ()I
    //   2601: invokevirtual addCenterPoint : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;FI)V
    //   2604: aload_0
    //   2605: iconst_0
    //   2606: putfield resolvedHorizontal : Z
    //   2609: aload_0
    //   2610: iconst_0
    //   2611: putfield resolvedVertical : Z
    //   2614: return
  }
  
  public boolean allowedInBarrier() {
    boolean bool;
    if (this.mVisibility != 8) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2) {
    connect(paramType1, paramConstraintWidget, paramType2, 0);
  }
  
  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt) {
    // Byte code:
    //   0: aload_1
    //   1: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   4: if_acmpne -> 361
    //   7: aload_3
    //   8: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   11: if_acmpne -> 248
    //   14: aload_0
    //   15: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   18: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   21: astore_3
    //   22: aload_0
    //   23: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   26: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   29: astore_1
    //   30: aload_0
    //   31: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   34: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   37: astore #6
    //   39: aload_0
    //   40: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   43: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   46: astore #7
    //   48: iconst_1
    //   49: istore #5
    //   51: aload_3
    //   52: ifnull -> 62
    //   55: aload_3
    //   56: invokevirtual isConnected : ()Z
    //   59: ifne -> 73
    //   62: aload_1
    //   63: ifnull -> 79
    //   66: aload_1
    //   67: invokevirtual isConnected : ()Z
    //   70: ifeq -> 79
    //   73: iconst_0
    //   74: istore #4
    //   76: goto -> 106
    //   79: aload_0
    //   80: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   83: aload_2
    //   84: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   87: iconst_0
    //   88: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   91: aload_0
    //   92: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   95: aload_2
    //   96: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   99: iconst_0
    //   100: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   103: iconst_1
    //   104: istore #4
    //   106: aload #6
    //   108: ifnull -> 119
    //   111: aload #6
    //   113: invokevirtual isConnected : ()Z
    //   116: ifne -> 132
    //   119: aload #7
    //   121: ifnull -> 138
    //   124: aload #7
    //   126: invokevirtual isConnected : ()Z
    //   129: ifeq -> 138
    //   132: iconst_0
    //   133: istore #5
    //   135: goto -> 162
    //   138: aload_0
    //   139: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   142: aload_2
    //   143: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   146: iconst_0
    //   147: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   150: aload_0
    //   151: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   154: aload_2
    //   155: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   158: iconst_0
    //   159: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   162: iload #4
    //   164: ifeq -> 194
    //   167: iload #5
    //   169: ifeq -> 194
    //   172: aload_0
    //   173: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   176: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   179: aload_2
    //   180: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   183: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   186: iconst_0
    //   187: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   190: pop
    //   191: goto -> 897
    //   194: iload #4
    //   196: ifeq -> 221
    //   199: aload_0
    //   200: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   203: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   206: aload_2
    //   207: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   210: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   213: iconst_0
    //   214: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   217: pop
    //   218: goto -> 897
    //   221: iload #5
    //   223: ifeq -> 897
    //   226: aload_0
    //   227: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   230: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   233: aload_2
    //   234: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   237: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   240: iconst_0
    //   241: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   244: pop
    //   245: goto -> 897
    //   248: aload_3
    //   249: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   252: if_acmpeq -> 319
    //   255: aload_3
    //   256: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   259: if_acmpne -> 265
    //   262: goto -> 319
    //   265: aload_3
    //   266: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   269: if_acmpeq -> 279
    //   272: aload_3
    //   273: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   276: if_acmpne -> 897
    //   279: aload_0
    //   280: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   283: aload_2
    //   284: aload_3
    //   285: iconst_0
    //   286: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   289: aload_0
    //   290: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   293: aload_2
    //   294: aload_3
    //   295: iconst_0
    //   296: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   299: aload_0
    //   300: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   303: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   306: aload_2
    //   307: aload_3
    //   308: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   311: iconst_0
    //   312: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   315: pop
    //   316: goto -> 897
    //   319: aload_0
    //   320: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   323: aload_2
    //   324: aload_3
    //   325: iconst_0
    //   326: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   329: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   332: astore_1
    //   333: aload_0
    //   334: aload_1
    //   335: aload_2
    //   336: aload_3
    //   337: iconst_0
    //   338: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;I)V
    //   341: aload_0
    //   342: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   345: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   348: aload_2
    //   349: aload_3
    //   350: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   353: iconst_0
    //   354: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   357: pop
    //   358: goto -> 897
    //   361: aload_1
    //   362: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   365: if_acmpne -> 434
    //   368: aload_3
    //   369: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   372: if_acmpeq -> 382
    //   375: aload_3
    //   376: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   379: if_acmpne -> 434
    //   382: aload_0
    //   383: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   386: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   389: astore_1
    //   390: aload_2
    //   391: aload_3
    //   392: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   395: astore_2
    //   396: aload_0
    //   397: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   400: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   403: astore_3
    //   404: aload_1
    //   405: aload_2
    //   406: iconst_0
    //   407: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   410: pop
    //   411: aload_3
    //   412: aload_2
    //   413: iconst_0
    //   414: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   417: pop
    //   418: aload_0
    //   419: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   422: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   425: aload_2
    //   426: iconst_0
    //   427: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   430: pop
    //   431: goto -> 897
    //   434: aload_1
    //   435: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   438: if_acmpne -> 503
    //   441: aload_3
    //   442: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   445: if_acmpeq -> 455
    //   448: aload_3
    //   449: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   452: if_acmpne -> 503
    //   455: aload_2
    //   456: aload_3
    //   457: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   460: astore_1
    //   461: aload_0
    //   462: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   465: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   468: aload_1
    //   469: iconst_0
    //   470: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   473: pop
    //   474: aload_0
    //   475: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   478: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   481: aload_1
    //   482: iconst_0
    //   483: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   486: pop
    //   487: aload_0
    //   488: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   491: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   494: aload_1
    //   495: iconst_0
    //   496: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   499: pop
    //   500: goto -> 897
    //   503: aload_1
    //   504: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   507: if_acmpne -> 575
    //   510: aload_3
    //   511: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   514: if_acmpne -> 575
    //   517: aload_0
    //   518: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   521: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   524: aload_2
    //   525: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   528: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   531: iconst_0
    //   532: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   535: pop
    //   536: aload_0
    //   537: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   540: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   543: aload_2
    //   544: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   547: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   550: iconst_0
    //   551: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   554: pop
    //   555: aload_0
    //   556: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   559: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   562: aload_2
    //   563: aload_3
    //   564: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   567: iconst_0
    //   568: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   571: pop
    //   572: goto -> 897
    //   575: aload_1
    //   576: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   579: if_acmpne -> 647
    //   582: aload_3
    //   583: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   586: if_acmpne -> 647
    //   589: aload_0
    //   590: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   593: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   596: aload_2
    //   597: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   600: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   603: iconst_0
    //   604: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   607: pop
    //   608: aload_0
    //   609: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   612: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   615: aload_2
    //   616: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   619: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   622: iconst_0
    //   623: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   626: pop
    //   627: aload_0
    //   628: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   631: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   634: aload_2
    //   635: aload_3
    //   636: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   639: iconst_0
    //   640: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   643: pop
    //   644: goto -> 897
    //   647: aload_0
    //   648: aload_1
    //   649: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   652: astore #6
    //   654: aload_2
    //   655: aload_3
    //   656: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   659: astore_2
    //   660: aload #6
    //   662: aload_2
    //   663: invokevirtual isValidConnection : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;)Z
    //   666: ifeq -> 897
    //   669: aload_1
    //   670: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BASELINE : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   673: if_acmpne -> 714
    //   676: aload_0
    //   677: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   680: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   683: astore_1
    //   684: aload_0
    //   685: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   688: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   691: astore_3
    //   692: aload_1
    //   693: ifnull -> 700
    //   696: aload_1
    //   697: invokevirtual reset : ()V
    //   700: aload_3
    //   701: ifnull -> 708
    //   704: aload_3
    //   705: invokevirtual reset : ()V
    //   708: iconst_0
    //   709: istore #5
    //   711: goto -> 888
    //   714: aload_1
    //   715: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   718: if_acmpeq -> 812
    //   721: aload_1
    //   722: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   725: if_acmpne -> 731
    //   728: goto -> 812
    //   731: aload_1
    //   732: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   735: if_acmpeq -> 749
    //   738: iload #4
    //   740: istore #5
    //   742: aload_1
    //   743: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   746: if_acmpne -> 888
    //   749: aload_0
    //   750: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   753: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   756: astore_3
    //   757: aload_3
    //   758: invokevirtual getTarget : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   761: aload_2
    //   762: if_acmpeq -> 769
    //   765: aload_3
    //   766: invokevirtual reset : ()V
    //   769: aload_0
    //   770: aload_1
    //   771: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   774: invokevirtual getOpposite : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   777: astore_3
    //   778: aload_0
    //   779: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   782: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   785: astore_1
    //   786: iload #4
    //   788: istore #5
    //   790: aload_1
    //   791: invokevirtual isConnected : ()Z
    //   794: ifeq -> 888
    //   797: aload_3
    //   798: invokevirtual reset : ()V
    //   801: aload_1
    //   802: invokevirtual reset : ()V
    //   805: iload #4
    //   807: istore #5
    //   809: goto -> 888
    //   812: aload_0
    //   813: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BASELINE : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   816: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   819: astore_3
    //   820: aload_3
    //   821: ifnull -> 828
    //   824: aload_3
    //   825: invokevirtual reset : ()V
    //   828: aload_0
    //   829: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   832: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   835: astore_3
    //   836: aload_3
    //   837: invokevirtual getTarget : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   840: aload_2
    //   841: if_acmpeq -> 848
    //   844: aload_3
    //   845: invokevirtual reset : ()V
    //   848: aload_0
    //   849: aload_1
    //   850: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   853: invokevirtual getOpposite : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   856: astore_1
    //   857: aload_0
    //   858: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   861: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   864: astore_3
    //   865: iload #4
    //   867: istore #5
    //   869: aload_3
    //   870: invokevirtual isConnected : ()Z
    //   873: ifeq -> 888
    //   876: aload_1
    //   877: invokevirtual reset : ()V
    //   880: aload_3
    //   881: invokevirtual reset : ()V
    //   884: iload #4
    //   886: istore #5
    //   888: aload #6
    //   890: aload_2
    //   891: iload #5
    //   893: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
    //   896: pop
    //   897: return
    //   898: astore_1
    //   899: aload_1
    //   900: athrow
    // Exception table:
    //   from	to	target	type
    //   333	341	898	finally
  }
  
  public void connect(ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt) {
    if (paramConstraintAnchor1.getOwner() == this)
      connect(paramConstraintAnchor1.getType(), paramConstraintAnchor2.getOwner(), paramConstraintAnchor2.getType(), paramInt); 
  }
  
  public void connectCircularConstraint(ConstraintWidget paramConstraintWidget, float paramFloat, int paramInt) {
    immediateConnect(ConstraintAnchor.Type.CENTER, paramConstraintWidget, ConstraintAnchor.Type.CENTER, paramInt, 0);
    this.mCircleConstraintAngle = paramFloat;
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap) {
    int[] arrayOfInt1;
    ConstraintWidget constraintWidget1;
    this.mHorizontalResolution = paramConstraintWidget.mHorizontalResolution;
    this.mVerticalResolution = paramConstraintWidget.mVerticalResolution;
    this.mMatchConstraintDefaultWidth = paramConstraintWidget.mMatchConstraintDefaultWidth;
    this.mMatchConstraintDefaultHeight = paramConstraintWidget.mMatchConstraintDefaultHeight;
    int[] arrayOfInt2 = this.mResolvedMatchConstraintDefault;
    int[] arrayOfInt3 = paramConstraintWidget.mResolvedMatchConstraintDefault;
    arrayOfInt2[0] = arrayOfInt3[0];
    arrayOfInt2[1] = arrayOfInt3[1];
    this.mMatchConstraintMinWidth = paramConstraintWidget.mMatchConstraintMinWidth;
    this.mMatchConstraintMaxWidth = paramConstraintWidget.mMatchConstraintMaxWidth;
    this.mMatchConstraintMinHeight = paramConstraintWidget.mMatchConstraintMinHeight;
    this.mMatchConstraintMaxHeight = paramConstraintWidget.mMatchConstraintMaxHeight;
    this.mMatchConstraintPercentHeight = paramConstraintWidget.mMatchConstraintPercentHeight;
    this.mIsWidthWrapContent = paramConstraintWidget.mIsWidthWrapContent;
    this.mIsHeightWrapContent = paramConstraintWidget.mIsHeightWrapContent;
    this.mResolvedDimensionRatioSide = paramConstraintWidget.mResolvedDimensionRatioSide;
    this.mResolvedDimensionRatio = paramConstraintWidget.mResolvedDimensionRatio;
    arrayOfInt2 = paramConstraintWidget.mMaxDimension;
    this.mMaxDimension = Arrays.copyOf(arrayOfInt2, arrayOfInt2.length);
    this.mCircleConstraintAngle = paramConstraintWidget.mCircleConstraintAngle;
    this.hasBaseline = paramConstraintWidget.hasBaseline;
    this.inPlaceholder = paramConstraintWidget.inPlaceholder;
    this.mLeft.reset();
    this.mTop.reset();
    this.mRight.reset();
    this.mBottom.reset();
    this.mBaseline.reset();
    this.mCenterX.reset();
    this.mCenterY.reset();
    this.mCenter.reset();
    this.mListDimensionBehaviors = Arrays.<DimensionBehaviour>copyOf(this.mListDimensionBehaviors, 2);
    ConstraintWidget constraintWidget3 = this.mParent;
    arrayOfInt3 = null;
    if (constraintWidget3 == null) {
      constraintWidget3 = null;
    } else {
      constraintWidget3 = paramHashMap.get(paramConstraintWidget.mParent);
    } 
    this.mParent = constraintWidget3;
    this.mWidth = paramConstraintWidget.mWidth;
    this.mHeight = paramConstraintWidget.mHeight;
    this.mDimensionRatio = paramConstraintWidget.mDimensionRatio;
    this.mDimensionRatioSide = paramConstraintWidget.mDimensionRatioSide;
    this.mX = paramConstraintWidget.mX;
    this.mY = paramConstraintWidget.mY;
    this.mRelX = paramConstraintWidget.mRelX;
    this.mRelY = paramConstraintWidget.mRelY;
    this.mOffsetX = paramConstraintWidget.mOffsetX;
    this.mOffsetY = paramConstraintWidget.mOffsetY;
    this.mBaselineDistance = paramConstraintWidget.mBaselineDistance;
    this.mMinWidth = paramConstraintWidget.mMinWidth;
    this.mMinHeight = paramConstraintWidget.mMinHeight;
    this.mHorizontalBiasPercent = paramConstraintWidget.mHorizontalBiasPercent;
    this.mVerticalBiasPercent = paramConstraintWidget.mVerticalBiasPercent;
    this.mCompanionWidget = paramConstraintWidget.mCompanionWidget;
    this.mContainerItemSkip = paramConstraintWidget.mContainerItemSkip;
    this.mVisibility = paramConstraintWidget.mVisibility;
    this.mDebugName = paramConstraintWidget.mDebugName;
    this.mType = paramConstraintWidget.mType;
    this.mDistToTop = paramConstraintWidget.mDistToTop;
    this.mDistToLeft = paramConstraintWidget.mDistToLeft;
    this.mDistToRight = paramConstraintWidget.mDistToRight;
    this.mDistToBottom = paramConstraintWidget.mDistToBottom;
    this.mLeftHasCentered = paramConstraintWidget.mLeftHasCentered;
    this.mRightHasCentered = paramConstraintWidget.mRightHasCentered;
    this.mTopHasCentered = paramConstraintWidget.mTopHasCentered;
    this.mBottomHasCentered = paramConstraintWidget.mBottomHasCentered;
    this.mHorizontalWrapVisited = paramConstraintWidget.mHorizontalWrapVisited;
    this.mVerticalWrapVisited = paramConstraintWidget.mVerticalWrapVisited;
    this.mHorizontalChainStyle = paramConstraintWidget.mHorizontalChainStyle;
    this.mVerticalChainStyle = paramConstraintWidget.mVerticalChainStyle;
    this.mHorizontalChainFixedPosition = paramConstraintWidget.mHorizontalChainFixedPosition;
    this.mVerticalChainFixedPosition = paramConstraintWidget.mVerticalChainFixedPosition;
    float[] arrayOfFloat1 = this.mWeight;
    float[] arrayOfFloat2 = paramConstraintWidget.mWeight;
    arrayOfFloat1[0] = arrayOfFloat2[0];
    arrayOfFloat1[1] = arrayOfFloat2[1];
    ConstraintWidget[] arrayOfConstraintWidget2 = this.mListNextMatchConstraintsWidget;
    ConstraintWidget[] arrayOfConstraintWidget1 = paramConstraintWidget.mListNextMatchConstraintsWidget;
    arrayOfConstraintWidget2[0] = arrayOfConstraintWidget1[0];
    arrayOfConstraintWidget2[1] = arrayOfConstraintWidget1[1];
    arrayOfConstraintWidget2 = this.mNextChainWidget;
    arrayOfConstraintWidget1 = paramConstraintWidget.mNextChainWidget;
    arrayOfConstraintWidget2[0] = arrayOfConstraintWidget1[0];
    arrayOfConstraintWidget2[1] = arrayOfConstraintWidget1[1];
    ConstraintWidget constraintWidget2 = paramConstraintWidget.mHorizontalNextWidget;
    if (constraintWidget2 == null) {
      constraintWidget2 = null;
    } else {
      constraintWidget2 = paramHashMap.get(constraintWidget2);
    } 
    this.mHorizontalNextWidget = constraintWidget2;
    paramConstraintWidget = paramConstraintWidget.mVerticalNextWidget;
    if (paramConstraintWidget == null) {
      arrayOfInt1 = arrayOfInt3;
    } else {
      constraintWidget1 = paramHashMap.get(arrayOfInt1);
    } 
    this.mVerticalNextWidget = constraintWidget1;
  }
  
  public void createObjectVariables(LinearSystem paramLinearSystem) {
    paramLinearSystem.createObjectVariable(this.mLeft);
    paramLinearSystem.createObjectVariable(this.mTop);
    paramLinearSystem.createObjectVariable(this.mRight);
    paramLinearSystem.createObjectVariable(this.mBottom);
    if (this.mBaselineDistance > 0)
      paramLinearSystem.createObjectVariable(this.mBaseline); 
  }
  
  public void ensureMeasureRequested() {
    this.mMeasureRequested = true;
  }
  
  public void ensureWidgetRuns() {
    if (this.horizontalRun == null)
      this.horizontalRun = new HorizontalWidgetRun(this); 
    if (this.verticalRun == null)
      this.verticalRun = new VerticalWidgetRun(this); 
  }
  
  public ConstraintAnchor getAnchor(ConstraintAnchor.Type paramType) {
    switch (paramType) {
      default:
        throw new AssertionError(paramType.name());
      case null:
        return null;
      case null:
        return this.mCenterY;
      case null:
        return this.mCenterX;
      case null:
        return this.mCenter;
      case null:
        return this.mBaseline;
      case null:
        return this.mBottom;
      case null:
        return this.mRight;
      case null:
        return this.mTop;
      case null:
        break;
    } 
    return this.mLeft;
  }
  
  public ArrayList<ConstraintAnchor> getAnchors() {
    return this.mAnchors;
  }
  
  public int getBaselineDistance() {
    return this.mBaselineDistance;
  }
  
  public float getBiasPercent(int paramInt) {
    return (paramInt == 0) ? this.mHorizontalBiasPercent : ((paramInt == 1) ? this.mVerticalBiasPercent : -1.0F);
  }
  
  public int getBottom() {
    return getY() + this.mHeight;
  }
  
  public Object getCompanionWidget() {
    return this.mCompanionWidget;
  }
  
  public int getContainerItemSkip() {
    return this.mContainerItemSkip;
  }
  
  public String getDebugName() {
    return this.mDebugName;
  }
  
  public DimensionBehaviour getDimensionBehaviour(int paramInt) {
    return (paramInt == 0) ? getHorizontalDimensionBehaviour() : ((paramInt == 1) ? getVerticalDimensionBehaviour() : null);
  }
  
  public float getDimensionRatio() {
    return this.mDimensionRatio;
  }
  
  public int getDimensionRatioSide() {
    return this.mDimensionRatioSide;
  }
  
  public boolean getHasBaseline() {
    return this.hasBaseline;
  }
  
  public int getHeight() {
    return (this.mVisibility == 8) ? 0 : this.mHeight;
  }
  
  public float getHorizontalBiasPercent() {
    return this.mHorizontalBiasPercent;
  }
  
  public ConstraintWidget getHorizontalChainControlWidget() {
    boolean bool = isInHorizontalChain();
    ConstraintWidget constraintWidget = null;
    if (bool) {
      constraintWidget = this;
      ConstraintWidget constraintWidget1 = null;
      while (true) {
        if (constraintWidget1 == null && constraintWidget != null) {
          ConstraintWidget constraintWidget2;
          ConstraintAnchor constraintAnchor2;
          ConstraintAnchor constraintAnchor1 = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
          if (constraintAnchor1 == null) {
            constraintAnchor1 = null;
          } else {
            constraintAnchor1 = constraintAnchor1.getTarget();
          } 
          if (constraintAnchor1 == null) {
            constraintAnchor1 = null;
          } else {
            constraintWidget2 = constraintAnchor1.getOwner();
          } 
          if (constraintWidget2 == getParent())
            break; 
          if (constraintWidget2 == null) {
            constraintAnchor2 = null;
          } else {
            constraintAnchor2 = constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
          } 
          if (constraintAnchor2 != null && constraintAnchor2.getOwner() != constraintWidget) {
            constraintWidget1 = constraintWidget;
            continue;
          } 
          constraintWidget = constraintWidget2;
          continue;
        } 
        constraintWidget = constraintWidget1;
        break;
      } 
    } 
    return constraintWidget;
  }
  
  public int getHorizontalChainStyle() {
    return this.mHorizontalChainStyle;
  }
  
  public DimensionBehaviour getHorizontalDimensionBehaviour() {
    return this.mListDimensionBehaviors[0];
  }
  
  public int getHorizontalMargin() {
    ConstraintAnchor constraintAnchor = this.mLeft;
    int i = 0;
    if (constraintAnchor != null)
      i = 0 + constraintAnchor.mMargin; 
    constraintAnchor = this.mRight;
    int j = i;
    if (constraintAnchor != null)
      j = i + constraintAnchor.mMargin; 
    return j;
  }
  
  public int getLastHorizontalMeasureSpec() {
    return this.mLastHorizontalMeasureSpec;
  }
  
  public int getLastVerticalMeasureSpec() {
    return this.mLastVerticalMeasureSpec;
  }
  
  public int getLeft() {
    return getX();
  }
  
  public int getLength(int paramInt) {
    return (paramInt == 0) ? getWidth() : ((paramInt == 1) ? getHeight() : 0);
  }
  
  public int getMaxHeight() {
    return this.mMaxDimension[1];
  }
  
  public int getMaxWidth() {
    return this.mMaxDimension[0];
  }
  
  public int getMinHeight() {
    return this.mMinHeight;
  }
  
  public int getMinWidth() {
    return this.mMinWidth;
  }
  
  public ConstraintWidget getNextChainMember(int paramInt) {
    if (paramInt == 0) {
      if (this.mRight.mTarget != null) {
        ConstraintAnchor constraintAnchor2 = this.mRight.mTarget.mTarget;
        ConstraintAnchor constraintAnchor1 = this.mRight;
        if (constraintAnchor2 == constraintAnchor1)
          return constraintAnchor1.mTarget.mOwner; 
      } 
    } else if (paramInt == 1 && this.mBottom.mTarget != null) {
      ConstraintAnchor constraintAnchor2 = this.mBottom.mTarget.mTarget;
      ConstraintAnchor constraintAnchor1 = this.mBottom;
      if (constraintAnchor2 == constraintAnchor1)
        return constraintAnchor1.mTarget.mOwner; 
    } 
    return null;
  }
  
  public int getOptimizerWrapHeight() {
    int i = this.mHeight;
    int j = i;
    if (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
      if (this.mMatchConstraintDefaultHeight == 1) {
        i = Math.max(this.mMatchConstraintMinHeight, i);
      } else {
        i = this.mMatchConstraintMinHeight;
        if (i > 0) {
          this.mHeight = i;
        } else {
          i = 0;
        } 
      } 
      int k = this.mMatchConstraintMaxHeight;
      j = i;
      if (k > 0) {
        j = i;
        if (k < i)
          j = k; 
      } 
    } 
    return j;
  }
  
  public int getOptimizerWrapWidth() {
    int i = this.mWidth;
    int j = i;
    if (this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
      if (this.mMatchConstraintDefaultWidth == 1) {
        i = Math.max(this.mMatchConstraintMinWidth, i);
      } else {
        i = this.mMatchConstraintMinWidth;
        if (i > 0) {
          this.mWidth = i;
        } else {
          i = 0;
        } 
      } 
      int k = this.mMatchConstraintMaxWidth;
      j = i;
      if (k > 0) {
        j = i;
        if (k < i)
          j = k; 
      } 
    } 
    return j;
  }
  
  public ConstraintWidget getParent() {
    return this.mParent;
  }
  
  public ConstraintWidget getPreviousChainMember(int paramInt) {
    if (paramInt == 0) {
      if (this.mLeft.mTarget != null) {
        ConstraintAnchor constraintAnchor1 = this.mLeft.mTarget.mTarget;
        ConstraintAnchor constraintAnchor2 = this.mLeft;
        if (constraintAnchor1 == constraintAnchor2)
          return constraintAnchor2.mTarget.mOwner; 
      } 
    } else if (paramInt == 1 && this.mTop.mTarget != null) {
      ConstraintAnchor constraintAnchor2 = this.mTop.mTarget.mTarget;
      ConstraintAnchor constraintAnchor1 = this.mTop;
      if (constraintAnchor2 == constraintAnchor1)
        return constraintAnchor1.mTarget.mOwner; 
    } 
    return null;
  }
  
  int getRelativePositioning(int paramInt) {
    return (paramInt == 0) ? this.mRelX : ((paramInt == 1) ? this.mRelY : 0);
  }
  
  public int getRight() {
    return getX() + this.mWidth;
  }
  
  protected int getRootX() {
    return this.mX + this.mOffsetX;
  }
  
  protected int getRootY() {
    return this.mY + this.mOffsetY;
  }
  
  public WidgetRun getRun(int paramInt) {
    return (WidgetRun)((paramInt == 0) ? this.horizontalRun : ((paramInt == 1) ? this.verticalRun : null));
  }
  
  public int getTop() {
    return getY();
  }
  
  public String getType() {
    return this.mType;
  }
  
  public float getVerticalBiasPercent() {
    return this.mVerticalBiasPercent;
  }
  
  public ConstraintWidget getVerticalChainControlWidget() {
    boolean bool = isInVerticalChain();
    ConstraintWidget constraintWidget = null;
    if (bool) {
      constraintWidget = this;
      ConstraintWidget constraintWidget1 = null;
      while (true) {
        if (constraintWidget1 == null && constraintWidget != null) {
          ConstraintWidget constraintWidget2;
          ConstraintAnchor constraintAnchor2;
          ConstraintAnchor constraintAnchor1 = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
          if (constraintAnchor1 == null) {
            constraintAnchor1 = null;
          } else {
            constraintAnchor1 = constraintAnchor1.getTarget();
          } 
          if (constraintAnchor1 == null) {
            constraintAnchor1 = null;
          } else {
            constraintWidget2 = constraintAnchor1.getOwner();
          } 
          if (constraintWidget2 == getParent())
            break; 
          if (constraintWidget2 == null) {
            constraintAnchor2 = null;
          } else {
            constraintAnchor2 = constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
          } 
          if (constraintAnchor2 != null && constraintAnchor2.getOwner() != constraintWidget) {
            constraintWidget1 = constraintWidget;
            continue;
          } 
          constraintWidget = constraintWidget2;
          continue;
        } 
        constraintWidget = constraintWidget1;
        break;
      } 
    } 
    return constraintWidget;
  }
  
  public int getVerticalChainStyle() {
    return this.mVerticalChainStyle;
  }
  
  public DimensionBehaviour getVerticalDimensionBehaviour() {
    return this.mListDimensionBehaviors[1];
  }
  
  public int getVerticalMargin() {
    ConstraintAnchor constraintAnchor = this.mLeft;
    int i = 0;
    if (constraintAnchor != null)
      i = 0 + this.mTop.mMargin; 
    int j = i;
    if (this.mRight != null)
      j = i + this.mBottom.mMargin; 
    return j;
  }
  
  public int getVisibility() {
    return this.mVisibility;
  }
  
  public int getWidth() {
    return (this.mVisibility == 8) ? 0 : this.mWidth;
  }
  
  public int getX() {
    ConstraintWidget constraintWidget = this.mParent;
    return (constraintWidget != null && constraintWidget instanceof ConstraintWidgetContainer) ? (((ConstraintWidgetContainer)constraintWidget).mPaddingLeft + this.mX) : this.mX;
  }
  
  public int getY() {
    ConstraintWidget constraintWidget = this.mParent;
    return (constraintWidget != null && constraintWidget instanceof ConstraintWidgetContainer) ? (((ConstraintWidgetContainer)constraintWidget).mPaddingTop + this.mY) : this.mY;
  }
  
  public boolean hasBaseline() {
    return this.hasBaseline;
  }
  
  public boolean hasDanglingDimension(int paramInt) {
    byte b1;
    byte b2;
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramInt == 0) {
      if (this.mLeft.mTarget != null) {
        paramInt = 1;
      } else {
        paramInt = 0;
      } 
      if (this.mRight.mTarget != null) {
        b1 = 1;
      } else {
        b1 = 0;
      } 
      if (paramInt + b1 < 2) {
        bool1 = bool2;
      } else {
        bool1 = false;
      } 
      return bool1;
    } 
    if (this.mTop.mTarget != null) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (this.mBottom.mTarget != null) {
      b1 = 1;
    } else {
      b1 = 0;
    } 
    if (this.mBaseline.mTarget != null) {
      b2 = 1;
    } else {
      b2 = 0;
    } 
    if (paramInt + b1 + b2 >= 2)
      bool1 = false; 
    return bool1;
  }
  
  public boolean hasDependencies() {
    int i = this.mAnchors.size();
    for (byte b = 0; b < i; b++) {
      if (((ConstraintAnchor)this.mAnchors.get(b)).hasDependents())
        return true; 
    } 
    return false;
  }
  
  public void immediateConnect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt1, int paramInt2) {
    getAnchor(paramType1).connect(paramConstraintWidget.getAnchor(paramType2), paramInt1, paramInt2, true);
  }
  
  public boolean isHeightWrapContent() {
    return this.mIsHeightWrapContent;
  }
  
  public boolean isInHorizontalChain() {
    return ((this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) || (this.mRight.mTarget != null && this.mRight.mTarget.mTarget == this.mRight));
  }
  
  public boolean isInPlaceholder() {
    return this.inPlaceholder;
  }
  
  public boolean isInVerticalChain() {
    return ((this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) || (this.mBottom.mTarget != null && this.mBottom.mTarget.mTarget == this.mBottom));
  }
  
  public boolean isInVirtualLayout() {
    return this.mInVirtuaLayout;
  }
  
  public boolean isMeasureRequested() {
    boolean bool;
    if (this.mMeasureRequested && this.mVisibility != 8) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isResolvedHorizontally() {
    return (this.resolvedHorizontal || (this.mLeft.hasFinalValue() && this.mRight.hasFinalValue()));
  }
  
  public boolean isResolvedVertically() {
    return (this.resolvedVertical || (this.mTop.hasFinalValue() && this.mBottom.hasFinalValue()));
  }
  
  public boolean isRoot() {
    boolean bool;
    if (this.mParent == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isSpreadHeight() {
    int i = this.mMatchConstraintDefaultHeight;
    boolean bool = true;
    if (i != 0 || this.mDimensionRatio != 0.0F || this.mMatchConstraintMinHeight != 0 || this.mMatchConstraintMaxHeight != 0 || this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT)
      bool = false; 
    return bool;
  }
  
  public boolean isSpreadWidth() {
    int i = this.mMatchConstraintDefaultWidth;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (i == 0) {
      bool1 = bool2;
      if (this.mDimensionRatio == 0.0F) {
        bool1 = bool2;
        if (this.mMatchConstraintMinWidth == 0) {
          bool1 = bool2;
          if (this.mMatchConstraintMaxWidth == 0) {
            bool1 = bool2;
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT)
              bool1 = true; 
          } 
        } 
      } 
    } 
    return bool1;
  }
  
  public boolean isWidthWrapContent() {
    return this.mIsWidthWrapContent;
  }
  
  public boolean oppositeDimensionDependsOn(int paramInt) {
    boolean bool1;
    boolean bool2 = true;
    if (paramInt == 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    DimensionBehaviour[] arrayOfDimensionBehaviour = this.mListDimensionBehaviors;
    DimensionBehaviour dimensionBehaviour1 = arrayOfDimensionBehaviour[paramInt];
    DimensionBehaviour dimensionBehaviour2 = arrayOfDimensionBehaviour[bool1];
    if (dimensionBehaviour1 != DimensionBehaviour.MATCH_CONSTRAINT || dimensionBehaviour2 != DimensionBehaviour.MATCH_CONSTRAINT)
      bool2 = false; 
    return bool2;
  }
  
  public boolean oppositeDimensionsTied() {
    DimensionBehaviour[] arrayOfDimensionBehaviour = this.mListDimensionBehaviors;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (arrayOfDimensionBehaviour[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
      bool1 = bool2;
      if (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT)
        bool1 = true; 
    } 
    return bool1;
  }
  
  public void reset() {
    this.mLeft.reset();
    this.mTop.reset();
    this.mRight.reset();
    this.mBottom.reset();
    this.mBaseline.reset();
    this.mCenterX.reset();
    this.mCenterY.reset();
    this.mCenter.reset();
    this.mParent = null;
    this.mCircleConstraintAngle = 0.0F;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    this.mMinWidth = 0;
    this.mMinHeight = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mListDimensionBehaviors[0] = DimensionBehaviour.FIXED;
    this.mListDimensionBehaviors[1] = DimensionBehaviour.FIXED;
    this.mCompanionWidget = null;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mType = null;
    this.mHorizontalWrapVisited = false;
    this.mVerticalWrapVisited = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mHorizontalChainFixedPosition = false;
    this.mVerticalChainFixedPosition = false;
    float[] arrayOfFloat = this.mWeight;
    arrayOfFloat[0] = -1.0F;
    arrayOfFloat[1] = -1.0F;
    this.mHorizontalResolution = -1;
    this.mVerticalResolution = -1;
    int[] arrayOfInt = this.mMaxDimension;
    arrayOfInt[0] = Integer.MAX_VALUE;
    arrayOfInt[1] = Integer.MAX_VALUE;
    this.mMatchConstraintDefaultWidth = 0;
    this.mMatchConstraintDefaultHeight = 0;
    this.mMatchConstraintPercentWidth = 1.0F;
    this.mMatchConstraintPercentHeight = 1.0F;
    this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
    this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
    this.mMatchConstraintMinWidth = 0;
    this.mMatchConstraintMinHeight = 0;
    this.mResolvedHasRatio = false;
    this.mResolvedDimensionRatioSide = -1;
    this.mResolvedDimensionRatio = 1.0F;
    this.mGroupsToSolver = false;
    boolean[] arrayOfBoolean = this.isTerminalWidget;
    arrayOfBoolean[0] = true;
    arrayOfBoolean[1] = true;
    this.mInVirtuaLayout = false;
    arrayOfBoolean = this.mIsInBarrier;
    arrayOfBoolean[0] = false;
    arrayOfBoolean[1] = false;
    this.mMeasureRequested = true;
  }
  
  public void resetAllConstraints() {
    resetAnchors();
    setVerticalBiasPercent(DEFAULT_BIAS);
    setHorizontalBiasPercent(DEFAULT_BIAS);
  }
  
  public void resetAnchor(ConstraintAnchor paramConstraintAnchor) {
    if (getParent() != null && getParent() instanceof ConstraintWidgetContainer && ((ConstraintWidgetContainer)getParent()).handlesInternalConstraints())
      return; 
    ConstraintAnchor constraintAnchor5 = getAnchor(ConstraintAnchor.Type.LEFT);
    ConstraintAnchor constraintAnchor3 = getAnchor(ConstraintAnchor.Type.RIGHT);
    ConstraintAnchor constraintAnchor7 = getAnchor(ConstraintAnchor.Type.TOP);
    ConstraintAnchor constraintAnchor6 = getAnchor(ConstraintAnchor.Type.BOTTOM);
    ConstraintAnchor constraintAnchor4 = getAnchor(ConstraintAnchor.Type.CENTER);
    ConstraintAnchor constraintAnchor2 = getAnchor(ConstraintAnchor.Type.CENTER_X);
    ConstraintAnchor constraintAnchor1 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
    if (paramConstraintAnchor == constraintAnchor4) {
      if (constraintAnchor5.isConnected() && constraintAnchor3.isConnected() && constraintAnchor5.getTarget() == constraintAnchor3.getTarget()) {
        constraintAnchor5.reset();
        constraintAnchor3.reset();
      } 
      if (constraintAnchor7.isConnected() && constraintAnchor6.isConnected() && constraintAnchor7.getTarget() == constraintAnchor6.getTarget()) {
        constraintAnchor7.reset();
        constraintAnchor6.reset();
      } 
      this.mHorizontalBiasPercent = 0.5F;
      this.mVerticalBiasPercent = 0.5F;
    } else if (paramConstraintAnchor == constraintAnchor2) {
      if (constraintAnchor5.isConnected() && constraintAnchor3.isConnected() && constraintAnchor5.getTarget().getOwner() == constraintAnchor3.getTarget().getOwner()) {
        constraintAnchor5.reset();
        constraintAnchor3.reset();
      } 
      this.mHorizontalBiasPercent = 0.5F;
    } else if (paramConstraintAnchor == constraintAnchor1) {
      if (constraintAnchor7.isConnected() && constraintAnchor6.isConnected() && constraintAnchor7.getTarget().getOwner() == constraintAnchor6.getTarget().getOwner()) {
        constraintAnchor7.reset();
        constraintAnchor6.reset();
      } 
      this.mVerticalBiasPercent = 0.5F;
    } else if (paramConstraintAnchor == constraintAnchor5 || paramConstraintAnchor == constraintAnchor3) {
      if (constraintAnchor5.isConnected() && constraintAnchor5.getTarget() == constraintAnchor3.getTarget())
        constraintAnchor4.reset(); 
    } else if ((paramConstraintAnchor == constraintAnchor7 || paramConstraintAnchor == constraintAnchor6) && constraintAnchor7.isConnected() && constraintAnchor7.getTarget() == constraintAnchor6.getTarget()) {
      constraintAnchor4.reset();
    } 
    paramConstraintAnchor.reset();
  }
  
  public void resetAnchors() {
    ConstraintWidget constraintWidget = getParent();
    if (constraintWidget != null && constraintWidget instanceof ConstraintWidgetContainer && ((ConstraintWidgetContainer)getParent()).handlesInternalConstraints())
      return; 
    byte b = 0;
    int i = this.mAnchors.size();
    while (b < i) {
      ((ConstraintAnchor)this.mAnchors.get(b)).reset();
      b++;
    } 
  }
  
  public void resetFinalResolution() {
    byte b = 0;
    this.resolvedHorizontal = false;
    this.resolvedVertical = false;
    int i = this.mAnchors.size();
    while (b < i) {
      ((ConstraintAnchor)this.mAnchors.get(b)).resetFinalResolution();
      b++;
    } 
  }
  
  public void resetSolverVariables(Cache paramCache) {
    this.mLeft.resetSolverVariable(paramCache);
    this.mTop.resetSolverVariable(paramCache);
    this.mRight.resetSolverVariable(paramCache);
    this.mBottom.resetSolverVariable(paramCache);
    this.mBaseline.resetSolverVariable(paramCache);
    this.mCenter.resetSolverVariable(paramCache);
    this.mCenterX.resetSolverVariable(paramCache);
    this.mCenterY.resetSolverVariable(paramCache);
  }
  
  public void setBaselineDistance(int paramInt) {
    boolean bool;
    this.mBaselineDistance = paramInt;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.hasBaseline = bool;
  }
  
  public void setCompanionWidget(Object paramObject) {
    this.mCompanionWidget = paramObject;
  }
  
  public void setContainerItemSkip(int paramInt) {
    if (paramInt >= 0) {
      this.mContainerItemSkip = paramInt;
    } else {
      this.mContainerItemSkip = 0;
    } 
  }
  
  public void setDebugName(String paramString) {
    this.mDebugName = paramString;
  }
  
  public void setDebugSolverName(LinearSystem paramLinearSystem, String paramString) {
    this.mDebugName = paramString;
    SolverVariable solverVariable5 = paramLinearSystem.createObjectVariable(this.mLeft);
    SolverVariable solverVariable4 = paramLinearSystem.createObjectVariable(this.mTop);
    SolverVariable solverVariable3 = paramLinearSystem.createObjectVariable(this.mRight);
    SolverVariable solverVariable2 = paramLinearSystem.createObjectVariable(this.mBottom);
    StringBuilder stringBuilder5 = new StringBuilder();
    stringBuilder5.append(paramString);
    stringBuilder5.append(".left");
    solverVariable5.setName(stringBuilder5.toString());
    StringBuilder stringBuilder4 = new StringBuilder();
    stringBuilder4.append(paramString);
    stringBuilder4.append(".top");
    solverVariable4.setName(stringBuilder4.toString());
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append(paramString);
    stringBuilder3.append(".right");
    solverVariable3.setName(stringBuilder3.toString());
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append(".bottom");
    solverVariable2.setName(stringBuilder2.toString());
    SolverVariable solverVariable1 = paramLinearSystem.createObjectVariable(this.mBaseline);
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(paramString);
    stringBuilder1.append(".baseline");
    solverVariable1.setName(stringBuilder1.toString());
  }
  
  public void setDimension(int paramInt1, int paramInt2) {
    this.mWidth = paramInt1;
    int i = this.mMinWidth;
    if (paramInt1 < i)
      this.mWidth = i; 
    this.mHeight = paramInt2;
    paramInt1 = this.mMinHeight;
    if (paramInt2 < paramInt1)
      this.mHeight = paramInt1; 
  }
  
  public void setDimensionRatio(float paramFloat, int paramInt) {
    this.mDimensionRatio = paramFloat;
    this.mDimensionRatioSide = paramInt;
  }
  
  public void setDimensionRatio(String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 261
    //   4: aload_1
    //   5: invokevirtual length : ()I
    //   8: ifne -> 14
    //   11: goto -> 261
    //   14: iconst_m1
    //   15: istore #6
    //   17: aload_1
    //   18: invokevirtual length : ()I
    //   21: istore #8
    //   23: aload_1
    //   24: bipush #44
    //   26: invokevirtual indexOf : (I)I
    //   29: istore #9
    //   31: iconst_0
    //   32: istore #7
    //   34: iload #6
    //   36: istore #4
    //   38: iload #7
    //   40: istore #5
    //   42: iload #9
    //   44: ifle -> 114
    //   47: iload #6
    //   49: istore #4
    //   51: iload #7
    //   53: istore #5
    //   55: iload #9
    //   57: iload #8
    //   59: iconst_1
    //   60: isub
    //   61: if_icmpge -> 114
    //   64: aload_1
    //   65: iconst_0
    //   66: iload #9
    //   68: invokevirtual substring : (II)Ljava/lang/String;
    //   71: astore #10
    //   73: aload #10
    //   75: ldc_w 'W'
    //   78: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   81: ifeq -> 90
    //   84: iconst_0
    //   85: istore #4
    //   87: goto -> 108
    //   90: iload #6
    //   92: istore #4
    //   94: aload #10
    //   96: ldc_w 'H'
    //   99: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   102: ifeq -> 108
    //   105: iconst_1
    //   106: istore #4
    //   108: iload #9
    //   110: iconst_1
    //   111: iadd
    //   112: istore #5
    //   114: aload_1
    //   115: bipush #58
    //   117: invokevirtual indexOf : (I)I
    //   120: istore #6
    //   122: iload #6
    //   124: iflt -> 219
    //   127: iload #6
    //   129: iload #8
    //   131: iconst_1
    //   132: isub
    //   133: if_icmpge -> 219
    //   136: aload_1
    //   137: iload #5
    //   139: iload #6
    //   141: invokevirtual substring : (II)Ljava/lang/String;
    //   144: astore #10
    //   146: aload_1
    //   147: iload #6
    //   149: iconst_1
    //   150: iadd
    //   151: invokevirtual substring : (I)Ljava/lang/String;
    //   154: astore_1
    //   155: aload #10
    //   157: invokevirtual length : ()I
    //   160: ifle -> 241
    //   163: aload_1
    //   164: invokevirtual length : ()I
    //   167: ifle -> 241
    //   170: aload #10
    //   172: invokestatic parseFloat : (Ljava/lang/String;)F
    //   175: fstore_2
    //   176: aload_1
    //   177: invokestatic parseFloat : (Ljava/lang/String;)F
    //   180: fstore_3
    //   181: fload_2
    //   182: fconst_0
    //   183: fcmpl
    //   184: ifle -> 241
    //   187: fload_3
    //   188: fconst_0
    //   189: fcmpl
    //   190: ifle -> 241
    //   193: iload #4
    //   195: iconst_1
    //   196: if_icmpne -> 209
    //   199: fload_3
    //   200: fload_2
    //   201: fdiv
    //   202: invokestatic abs : (F)F
    //   205: fstore_2
    //   206: goto -> 243
    //   209: fload_2
    //   210: fload_3
    //   211: fdiv
    //   212: invokestatic abs : (F)F
    //   215: fstore_2
    //   216: goto -> 243
    //   219: aload_1
    //   220: iload #5
    //   222: invokevirtual substring : (I)Ljava/lang/String;
    //   225: astore_1
    //   226: aload_1
    //   227: invokevirtual length : ()I
    //   230: ifle -> 241
    //   233: aload_1
    //   234: invokestatic parseFloat : (Ljava/lang/String;)F
    //   237: fstore_2
    //   238: goto -> 243
    //   241: fconst_0
    //   242: fstore_2
    //   243: fload_2
    //   244: fconst_0
    //   245: fcmpl
    //   246: ifle -> 260
    //   249: aload_0
    //   250: fload_2
    //   251: putfield mDimensionRatio : F
    //   254: aload_0
    //   255: iload #4
    //   257: putfield mDimensionRatioSide : I
    //   260: return
    //   261: aload_0
    //   262: fconst_0
    //   263: putfield mDimensionRatio : F
    //   266: return
    //   267: astore_1
    //   268: goto -> 241
    // Exception table:
    //   from	to	target	type
    //   170	181	267	java/lang/NumberFormatException
    //   199	206	267	java/lang/NumberFormatException
    //   209	216	267	java/lang/NumberFormatException
    //   233	238	267	java/lang/NumberFormatException
  }
  
  public void setFinalBaseline(int paramInt) {
    if (!this.hasBaseline)
      return; 
    int i = paramInt - this.mBaselineDistance;
    int j = this.mHeight;
    this.mY = i;
    this.mTop.setFinalValue(i);
    this.mBottom.setFinalValue(j + i);
    this.mBaseline.setFinalValue(paramInt);
    this.resolvedVertical = true;
  }
  
  public void setFinalFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
    setBaselineDistance(paramInt5);
    if (paramInt6 == 0) {
      this.resolvedHorizontal = true;
      this.resolvedVertical = false;
    } else if (paramInt6 == 1) {
      this.resolvedHorizontal = false;
      this.resolvedVertical = true;
    } else if (paramInt6 == 2) {
      this.resolvedHorizontal = true;
      this.resolvedVertical = true;
    } else {
      this.resolvedHorizontal = false;
      this.resolvedVertical = false;
    } 
  }
  
  public void setFinalHorizontal(int paramInt1, int paramInt2) {
    this.mLeft.setFinalValue(paramInt1);
    this.mRight.setFinalValue(paramInt2);
    this.mX = paramInt1;
    this.mWidth = paramInt2 - paramInt1;
    this.resolvedHorizontal = true;
  }
  
  public void setFinalLeft(int paramInt) {
    this.mLeft.setFinalValue(paramInt);
    this.mX = paramInt;
  }
  
  public void setFinalTop(int paramInt) {
    this.mTop.setFinalValue(paramInt);
    this.mY = paramInt;
  }
  
  public void setFinalVertical(int paramInt1, int paramInt2) {
    this.mTop.setFinalValue(paramInt1);
    this.mBottom.setFinalValue(paramInt2);
    this.mY = paramInt1;
    this.mHeight = paramInt2 - paramInt1;
    if (this.hasBaseline)
      this.mBaseline.setFinalValue(paramInt1 + this.mBaselineDistance); 
    this.resolvedVertical = true;
  }
  
  public void setFrame(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt3 == 0) {
      setHorizontalDimension(paramInt1, paramInt2);
    } else if (paramInt3 == 1) {
      setVerticalDimension(paramInt1, paramInt2);
    } 
  }
  
  public void setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramInt3 - paramInt1;
    paramInt3 = paramInt4 - paramInt2;
    this.mX = paramInt1;
    this.mY = paramInt2;
    if (this.mVisibility == 8) {
      this.mWidth = 0;
      this.mHeight = 0;
      return;
    } 
    paramInt1 = i;
    if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED) {
      paramInt2 = this.mWidth;
      paramInt1 = i;
      if (i < paramInt2)
        paramInt1 = paramInt2; 
    } 
    paramInt2 = paramInt3;
    if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED) {
      paramInt4 = this.mHeight;
      paramInt2 = paramInt3;
      if (paramInt3 < paramInt4)
        paramInt2 = paramInt4; 
    } 
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
    paramInt1 = this.mMinHeight;
    if (paramInt2 < paramInt1)
      this.mHeight = paramInt1; 
    paramInt2 = this.mWidth;
    paramInt1 = this.mMinWidth;
    if (paramInt2 < paramInt1)
      this.mWidth = paramInt1; 
  }
  
  public void setGoneMargin(ConstraintAnchor.Type paramType, int paramInt) {
    int i = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[paramType.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i == 4)
            this.mBottom.mGoneMargin = paramInt; 
        } else {
          this.mRight.mGoneMargin = paramInt;
        } 
      } else {
        this.mTop.mGoneMargin = paramInt;
      } 
    } else {
      this.mLeft.mGoneMargin = paramInt;
    } 
  }
  
  public void setHasBaseline(boolean paramBoolean) {
    this.hasBaseline = paramBoolean;
  }
  
  public void setHeight(int paramInt) {
    this.mHeight = paramInt;
    int i = this.mMinHeight;
    if (paramInt < i)
      this.mHeight = i; 
  }
  
  public void setHeightWrapContent(boolean paramBoolean) {
    this.mIsHeightWrapContent = paramBoolean;
  }
  
  public void setHorizontalBiasPercent(float paramFloat) {
    this.mHorizontalBiasPercent = paramFloat;
  }
  
  public void setHorizontalChainStyle(int paramInt) {
    this.mHorizontalChainStyle = paramInt;
  }
  
  public void setHorizontalDimension(int paramInt1, int paramInt2) {
    this.mX = paramInt1;
    paramInt2 -= paramInt1;
    this.mWidth = paramInt2;
    paramInt1 = this.mMinWidth;
    if (paramInt2 < paramInt1)
      this.mWidth = paramInt1; 
  }
  
  public void setHorizontalDimensionBehaviour(DimensionBehaviour paramDimensionBehaviour) {
    this.mListDimensionBehaviors[0] = paramDimensionBehaviour;
  }
  
  public void setHorizontalMatchStyle(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
    this.mMatchConstraintDefaultWidth = paramInt1;
    this.mMatchConstraintMinWidth = paramInt2;
    paramInt1 = paramInt3;
    if (paramInt3 == Integer.MAX_VALUE)
      paramInt1 = 0; 
    this.mMatchConstraintMaxWidth = paramInt1;
    this.mMatchConstraintPercentWidth = paramFloat;
    if (paramFloat > 0.0F && paramFloat < 1.0F && this.mMatchConstraintDefaultWidth == 0)
      this.mMatchConstraintDefaultWidth = 2; 
  }
  
  public void setHorizontalWeight(float paramFloat) {
    this.mWeight[0] = paramFloat;
  }
  
  protected void setInBarrier(int paramInt, boolean paramBoolean) {
    this.mIsInBarrier[paramInt] = paramBoolean;
  }
  
  public void setInPlaceholder(boolean paramBoolean) {
    this.inPlaceholder = paramBoolean;
  }
  
  public void setInVirtualLayout(boolean paramBoolean) {
    this.mInVirtuaLayout = paramBoolean;
  }
  
  public void setLastMeasureSpec(int paramInt1, int paramInt2) {
    this.mLastHorizontalMeasureSpec = paramInt1;
    this.mLastVerticalMeasureSpec = paramInt2;
    setMeasureRequested(false);
  }
  
  public void setLength(int paramInt1, int paramInt2) {
    if (paramInt2 == 0) {
      setWidth(paramInt1);
    } else if (paramInt2 == 1) {
      setHeight(paramInt1);
    } 
  }
  
  public void setMaxHeight(int paramInt) {
    this.mMaxDimension[1] = paramInt;
  }
  
  public void setMaxWidth(int paramInt) {
    this.mMaxDimension[0] = paramInt;
  }
  
  public void setMeasureRequested(boolean paramBoolean) {
    this.mMeasureRequested = paramBoolean;
  }
  
  public void setMinHeight(int paramInt) {
    if (paramInt < 0) {
      this.mMinHeight = 0;
    } else {
      this.mMinHeight = paramInt;
    } 
  }
  
  public void setMinWidth(int paramInt) {
    if (paramInt < 0) {
      this.mMinWidth = 0;
    } else {
      this.mMinWidth = paramInt;
    } 
  }
  
  public void setOffset(int paramInt1, int paramInt2) {
    this.mOffsetX = paramInt1;
    this.mOffsetY = paramInt2;
  }
  
  public void setOrigin(int paramInt1, int paramInt2) {
    this.mX = paramInt1;
    this.mY = paramInt2;
  }
  
  public void setParent(ConstraintWidget paramConstraintWidget) {
    this.mParent = paramConstraintWidget;
  }
  
  void setRelativePositioning(int paramInt1, int paramInt2) {
    if (paramInt2 == 0) {
      this.mRelX = paramInt1;
    } else if (paramInt2 == 1) {
      this.mRelY = paramInt1;
    } 
  }
  
  public void setType(String paramString) {
    this.mType = paramString;
  }
  
  public void setVerticalBiasPercent(float paramFloat) {
    this.mVerticalBiasPercent = paramFloat;
  }
  
  public void setVerticalChainStyle(int paramInt) {
    this.mVerticalChainStyle = paramInt;
  }
  
  public void setVerticalDimension(int paramInt1, int paramInt2) {
    this.mY = paramInt1;
    paramInt1 = paramInt2 - paramInt1;
    this.mHeight = paramInt1;
    paramInt2 = this.mMinHeight;
    if (paramInt1 < paramInt2)
      this.mHeight = paramInt2; 
  }
  
  public void setVerticalDimensionBehaviour(DimensionBehaviour paramDimensionBehaviour) {
    this.mListDimensionBehaviors[1] = paramDimensionBehaviour;
  }
  
  public void setVerticalMatchStyle(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
    this.mMatchConstraintDefaultHeight = paramInt1;
    this.mMatchConstraintMinHeight = paramInt2;
    paramInt1 = paramInt3;
    if (paramInt3 == Integer.MAX_VALUE)
      paramInt1 = 0; 
    this.mMatchConstraintMaxHeight = paramInt1;
    this.mMatchConstraintPercentHeight = paramFloat;
    if (paramFloat > 0.0F && paramFloat < 1.0F && this.mMatchConstraintDefaultHeight == 0)
      this.mMatchConstraintDefaultHeight = 2; 
  }
  
  public void setVerticalWeight(float paramFloat) {
    this.mWeight[1] = paramFloat;
  }
  
  public void setVisibility(int paramInt) {
    this.mVisibility = paramInt;
  }
  
  public void setWidth(int paramInt) {
    this.mWidth = paramInt;
    int i = this.mMinWidth;
    if (paramInt < i)
      this.mWidth = i; 
  }
  
  public void setWidthWrapContent(boolean paramBoolean) {
    this.mIsWidthWrapContent = paramBoolean;
  }
  
  public void setX(int paramInt) {
    this.mX = paramInt;
  }
  
  public void setY(int paramInt) {
    this.mY = paramInt;
  }
  
  public void setupDimensionRatio(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    if (this.mResolvedDimensionRatioSide == -1)
      if (paramBoolean3 && !paramBoolean4) {
        this.mResolvedDimensionRatioSide = 0;
      } else if (!paramBoolean3 && paramBoolean4) {
        this.mResolvedDimensionRatioSide = 1;
        if (this.mDimensionRatioSide == -1)
          this.mResolvedDimensionRatio = 1.0F / this.mResolvedDimensionRatio; 
      }  
    if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
      this.mResolvedDimensionRatioSide = 1;
    } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
      this.mResolvedDimensionRatioSide = 0;
    } 
    if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected()))
      if (this.mTop.isConnected() && this.mBottom.isConnected()) {
        this.mResolvedDimensionRatioSide = 0;
      } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
        this.mResolvedDimensionRatio = 1.0F / this.mResolvedDimensionRatio;
        this.mResolvedDimensionRatioSide = 1;
      }  
    if (this.mResolvedDimensionRatioSide == -1)
      if (this.mMatchConstraintMinWidth > 0 && this.mMatchConstraintMinHeight == 0) {
        this.mResolvedDimensionRatioSide = 0;
      } else if (this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMinHeight > 0) {
        this.mResolvedDimensionRatio = 1.0F / this.mResolvedDimensionRatio;
        this.mResolvedDimensionRatioSide = 1;
      }  
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String str1 = this.mType;
    String str2 = "";
    if (str1 != null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("type: ");
      stringBuilder1.append(this.mType);
      stringBuilder1.append(" ");
      String str = stringBuilder1.toString();
    } else {
      str1 = "";
    } 
    stringBuilder.append(str1);
    str1 = str2;
    if (this.mDebugName != null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("id: ");
      stringBuilder1.append(this.mDebugName);
      stringBuilder1.append(" ");
      str1 = stringBuilder1.toString();
    } 
    stringBuilder.append(str1);
    stringBuilder.append("(");
    stringBuilder.append(this.mX);
    stringBuilder.append(", ");
    stringBuilder.append(this.mY);
    stringBuilder.append(") - (");
    stringBuilder.append(this.mWidth);
    stringBuilder.append(" x ");
    stringBuilder.append(this.mHeight);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public void updateFromRuns(boolean paramBoolean1, boolean paramBoolean2) {
    // Byte code:
    //   0: iload_1
    //   1: aload_0
    //   2: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   5: invokevirtual isResolved : ()Z
    //   8: iand
    //   9: istore #9
    //   11: iload_2
    //   12: aload_0
    //   13: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   16: invokevirtual isResolved : ()Z
    //   19: iand
    //   20: istore #8
    //   22: aload_0
    //   23: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   26: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   29: getfield value : I
    //   32: istore #5
    //   34: aload_0
    //   35: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   38: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   41: getfield value : I
    //   44: istore #4
    //   46: aload_0
    //   47: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   50: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   53: getfield value : I
    //   56: istore #6
    //   58: aload_0
    //   59: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   62: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   65: getfield value : I
    //   68: istore #7
    //   70: iload #6
    //   72: iload #5
    //   74: isub
    //   75: iflt -> 149
    //   78: iload #7
    //   80: iload #4
    //   82: isub
    //   83: iflt -> 149
    //   86: iload #5
    //   88: ldc_w -2147483648
    //   91: if_icmpeq -> 149
    //   94: iload #5
    //   96: ldc 2147483647
    //   98: if_icmpeq -> 149
    //   101: iload #4
    //   103: ldc_w -2147483648
    //   106: if_icmpeq -> 149
    //   109: iload #4
    //   111: ldc 2147483647
    //   113: if_icmpeq -> 149
    //   116: iload #6
    //   118: ldc_w -2147483648
    //   121: if_icmpeq -> 149
    //   124: iload #6
    //   126: ldc 2147483647
    //   128: if_icmpeq -> 149
    //   131: iload #7
    //   133: ldc_w -2147483648
    //   136: if_icmpeq -> 149
    //   139: iload #7
    //   141: istore_3
    //   142: iload #7
    //   144: ldc 2147483647
    //   146: if_icmpne -> 160
    //   149: iconst_0
    //   150: istore #5
    //   152: iconst_0
    //   153: istore #4
    //   155: iconst_0
    //   156: istore #6
    //   158: iconst_0
    //   159: istore_3
    //   160: iload #6
    //   162: iload #5
    //   164: isub
    //   165: istore #7
    //   167: iload_3
    //   168: iload #4
    //   170: isub
    //   171: istore #6
    //   173: iload #9
    //   175: ifeq -> 184
    //   178: aload_0
    //   179: iload #5
    //   181: putfield mX : I
    //   184: iload #8
    //   186: ifeq -> 195
    //   189: aload_0
    //   190: iload #4
    //   192: putfield mY : I
    //   195: aload_0
    //   196: getfield mVisibility : I
    //   199: bipush #8
    //   201: if_icmpne -> 215
    //   204: aload_0
    //   205: iconst_0
    //   206: putfield mWidth : I
    //   209: aload_0
    //   210: iconst_0
    //   211: putfield mHeight : I
    //   214: return
    //   215: iload #9
    //   217: ifeq -> 277
    //   220: iload #7
    //   222: istore_3
    //   223: aload_0
    //   224: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   227: iconst_0
    //   228: aaload
    //   229: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   232: if_acmpne -> 254
    //   235: aload_0
    //   236: getfield mWidth : I
    //   239: istore #4
    //   241: iload #7
    //   243: istore_3
    //   244: iload #7
    //   246: iload #4
    //   248: if_icmpge -> 254
    //   251: iload #4
    //   253: istore_3
    //   254: aload_0
    //   255: iload_3
    //   256: putfield mWidth : I
    //   259: aload_0
    //   260: getfield mMinWidth : I
    //   263: istore #4
    //   265: iload_3
    //   266: iload #4
    //   268: if_icmpge -> 277
    //   271: aload_0
    //   272: iload #4
    //   274: putfield mWidth : I
    //   277: iload #8
    //   279: ifeq -> 339
    //   282: iload #6
    //   284: istore_3
    //   285: aload_0
    //   286: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   289: iconst_1
    //   290: aaload
    //   291: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   294: if_acmpne -> 316
    //   297: aload_0
    //   298: getfield mHeight : I
    //   301: istore #4
    //   303: iload #6
    //   305: istore_3
    //   306: iload #6
    //   308: iload #4
    //   310: if_icmpge -> 316
    //   313: iload #4
    //   315: istore_3
    //   316: aload_0
    //   317: iload_3
    //   318: putfield mHeight : I
    //   321: aload_0
    //   322: getfield mMinHeight : I
    //   325: istore #4
    //   327: iload_3
    //   328: iload #4
    //   330: if_icmpge -> 339
    //   333: aload_0
    //   334: iload #4
    //   336: putfield mHeight : I
    //   339: return
  }
  
  public void updateFromSolver(LinearSystem paramLinearSystem, boolean paramBoolean) {
    // Byte code:
    //   0: aload_1
    //   1: aload_0
    //   2: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   5: invokevirtual getObjectVariableValue : (Ljava/lang/Object;)I
    //   8: istore #4
    //   10: aload_1
    //   11: aload_0
    //   12: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   15: invokevirtual getObjectVariableValue : (Ljava/lang/Object;)I
    //   18: istore #8
    //   20: aload_1
    //   21: aload_0
    //   22: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   25: invokevirtual getObjectVariableValue : (Ljava/lang/Object;)I
    //   28: istore #6
    //   30: aload_1
    //   31: aload_0
    //   32: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   35: invokevirtual getObjectVariableValue : (Ljava/lang/Object;)I
    //   38: istore #7
    //   40: iload #4
    //   42: istore #5
    //   44: iload #6
    //   46: istore_3
    //   47: iload_2
    //   48: ifeq -> 127
    //   51: aload_0
    //   52: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   55: astore_1
    //   56: iload #4
    //   58: istore #5
    //   60: iload #6
    //   62: istore_3
    //   63: aload_1
    //   64: ifnull -> 127
    //   67: iload #4
    //   69: istore #5
    //   71: iload #6
    //   73: istore_3
    //   74: aload_1
    //   75: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   78: getfield resolved : Z
    //   81: ifeq -> 127
    //   84: iload #4
    //   86: istore #5
    //   88: iload #6
    //   90: istore_3
    //   91: aload_0
    //   92: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   95: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   98: getfield resolved : Z
    //   101: ifeq -> 127
    //   104: aload_0
    //   105: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   108: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   111: getfield value : I
    //   114: istore #5
    //   116: aload_0
    //   117: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   120: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   123: getfield value : I
    //   126: istore_3
    //   127: iload #8
    //   129: istore #6
    //   131: iload #7
    //   133: istore #4
    //   135: iload_2
    //   136: ifeq -> 219
    //   139: aload_0
    //   140: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   143: astore_1
    //   144: iload #8
    //   146: istore #6
    //   148: iload #7
    //   150: istore #4
    //   152: aload_1
    //   153: ifnull -> 219
    //   156: iload #8
    //   158: istore #6
    //   160: iload #7
    //   162: istore #4
    //   164: aload_1
    //   165: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   168: getfield resolved : Z
    //   171: ifeq -> 219
    //   174: iload #8
    //   176: istore #6
    //   178: iload #7
    //   180: istore #4
    //   182: aload_0
    //   183: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   186: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   189: getfield resolved : Z
    //   192: ifeq -> 219
    //   195: aload_0
    //   196: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   199: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   202: getfield value : I
    //   205: istore #6
    //   207: aload_0
    //   208: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   211: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   214: getfield value : I
    //   217: istore #4
    //   219: iload_3
    //   220: iload #5
    //   222: isub
    //   223: iflt -> 298
    //   226: iload #4
    //   228: iload #6
    //   230: isub
    //   231: iflt -> 298
    //   234: iload #5
    //   236: ldc_w -2147483648
    //   239: if_icmpeq -> 298
    //   242: iload #5
    //   244: ldc 2147483647
    //   246: if_icmpeq -> 298
    //   249: iload #6
    //   251: ldc_w -2147483648
    //   254: if_icmpeq -> 298
    //   257: iload #6
    //   259: ldc 2147483647
    //   261: if_icmpeq -> 298
    //   264: iload_3
    //   265: ldc_w -2147483648
    //   268: if_icmpeq -> 298
    //   271: iload_3
    //   272: ldc 2147483647
    //   274: if_icmpeq -> 298
    //   277: iload #4
    //   279: ldc_w -2147483648
    //   282: if_icmpeq -> 298
    //   285: iload_3
    //   286: istore #7
    //   288: iload #4
    //   290: istore_3
    //   291: iload #4
    //   293: ldc 2147483647
    //   295: if_icmpne -> 309
    //   298: iconst_0
    //   299: istore_3
    //   300: iconst_0
    //   301: istore #5
    //   303: iconst_0
    //   304: istore #6
    //   306: iconst_0
    //   307: istore #7
    //   309: aload_0
    //   310: iload #5
    //   312: iload #6
    //   314: iload #7
    //   316: iload_3
    //   317: invokevirtual setFrame : (IIII)V
    //   320: return
  }
  
  public enum DimensionBehaviour {
    FIXED, MATCH_CONSTRAINT, MATCH_PARENT, WRAP_CONTENT;
    
    private static final DimensionBehaviour[] $VALUES;
    
    static {
      DimensionBehaviour dimensionBehaviour = new DimensionBehaviour("MATCH_PARENT", 3);
      MATCH_PARENT = dimensionBehaviour;
      $VALUES = new DimensionBehaviour[] { FIXED, WRAP_CONTENT, MATCH_CONSTRAINT, dimensionBehaviour };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\ConstraintWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */