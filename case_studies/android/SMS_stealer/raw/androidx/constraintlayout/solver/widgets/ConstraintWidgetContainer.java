package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.solver.widgets.analyzer.Direct;
import androidx.constraintlayout.solver.widgets.analyzer.Grouping;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ConstraintWidgetContainer extends WidgetContainer {
  private static final boolean DEBUG = false;
  
  static final boolean DEBUG_GRAPH = false;
  
  private static final boolean DEBUG_LAYOUT = false;
  
  private static final int MAX_ITERATIONS = 8;
  
  static int mycounter;
  
  private WeakReference<ConstraintAnchor> horizontalWrapMax = null;
  
  private WeakReference<ConstraintAnchor> horizontalWrapMin = null;
  
  BasicMeasure mBasicMeasureSolver = new BasicMeasure(this);
  
  int mDebugSolverPassCount = 0;
  
  public DependencyGraph mDependencyGraph = new DependencyGraph(this);
  
  public boolean mGroupsWrapOptimized = false;
  
  private boolean mHeightMeasuredTooSmall = false;
  
  ChainHead[] mHorizontalChainsArray = new ChainHead[4];
  
  public int mHorizontalChainsSize = 0;
  
  public boolean mHorizontalWrapOptimized = false;
  
  private boolean mIsRtl = false;
  
  public BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
  
  protected BasicMeasure.Measurer mMeasurer = null;
  
  public Metrics mMetrics;
  
  private int mOptimizationLevel = 257;
  
  int mPaddingBottom;
  
  int mPaddingLeft;
  
  int mPaddingRight;
  
  int mPaddingTop;
  
  public boolean mSkipSolver = false;
  
  protected LinearSystem mSystem = new LinearSystem();
  
  ChainHead[] mVerticalChainsArray = new ChainHead[4];
  
  public int mVerticalChainsSize = 0;
  
  public boolean mVerticalWrapOptimized = false;
  
  private boolean mWidthMeasuredTooSmall = false;
  
  public int mWrapFixedHeight = 0;
  
  public int mWrapFixedWidth = 0;
  
  private WeakReference<ConstraintAnchor> verticalWrapMax = null;
  
  private WeakReference<ConstraintAnchor> verticalWrapMin = null;
  
  public ConstraintWidgetContainer() {}
  
  public ConstraintWidgetContainer(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
  }
  
  public ConstraintWidgetContainer(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public ConstraintWidgetContainer(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    setDebugName(paramString);
  }
  
  private void addHorizontalChain(ConstraintWidget paramConstraintWidget) {
    int i = this.mHorizontalChainsSize;
    ChainHead[] arrayOfChainHead = this.mHorizontalChainsArray;
    if (i + 1 >= arrayOfChainHead.length)
      this.mHorizontalChainsArray = Arrays.<ChainHead>copyOf(arrayOfChainHead, arrayOfChainHead.length * 2); 
    this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(paramConstraintWidget, 0, isRtl());
    this.mHorizontalChainsSize++;
  }
  
  private void addMaxWrap(ConstraintAnchor paramConstraintAnchor, SolverVariable paramSolverVariable) {
    SolverVariable solverVariable = this.mSystem.createObjectVariable(paramConstraintAnchor);
    this.mSystem.addGreaterThan(paramSolverVariable, solverVariable, 0, 5);
  }
  
  private void addMinWrap(ConstraintAnchor paramConstraintAnchor, SolverVariable paramSolverVariable) {
    SolverVariable solverVariable = this.mSystem.createObjectVariable(paramConstraintAnchor);
    this.mSystem.addGreaterThan(solverVariable, paramSolverVariable, 0, 5);
  }
  
  private void addVerticalChain(ConstraintWidget paramConstraintWidget) {
    int i = this.mVerticalChainsSize;
    ChainHead[] arrayOfChainHead = this.mVerticalChainsArray;
    if (i + 1 >= arrayOfChainHead.length)
      this.mVerticalChainsArray = Arrays.<ChainHead>copyOf(arrayOfChainHead, arrayOfChainHead.length * 2); 
    this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(paramConstraintWidget, 1, isRtl());
    this.mVerticalChainsSize++;
  }
  
  public static boolean measure(ConstraintWidget paramConstraintWidget, BasicMeasure.Measurer paramMeasurer, BasicMeasure.Measure paramMeasure, int paramInt) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    if (paramMeasurer == null)
      return false; 
    paramMeasure.horizontalBehavior = paramConstraintWidget.getHorizontalDimensionBehaviour();
    paramMeasure.verticalBehavior = paramConstraintWidget.getVerticalDimensionBehaviour();
    paramMeasure.horizontalDimension = paramConstraintWidget.getWidth();
    paramMeasure.verticalDimension = paramConstraintWidget.getHeight();
    paramMeasure.measuredNeedsSolverPass = false;
    paramMeasure.measureStrategy = paramInt;
    if (paramMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (paramMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (paramInt != 0 && paramConstraintWidget.mDimensionRatio > 0.0F) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    if (bool1 && paramConstraintWidget.mDimensionRatio > 0.0F) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    int i = paramInt;
    if (paramInt != 0) {
      i = paramInt;
      if (paramConstraintWidget.hasDanglingDimension(0)) {
        i = paramInt;
        if (paramConstraintWidget.mMatchConstraintDefaultWidth == 0) {
          i = paramInt;
          if (!bool3) {
            paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (bool1 && paramConstraintWidget.mMatchConstraintDefaultHeight == 0)
              paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED; 
            i = 0;
          } 
        } 
      } 
    } 
    paramInt = bool1;
    if (bool1) {
      paramInt = bool1;
      if (paramConstraintWidget.hasDanglingDimension(1)) {
        paramInt = bool1;
        if (paramConstraintWidget.mMatchConstraintDefaultHeight == 0) {
          paramInt = bool1;
          if (!bool2) {
            paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (i != 0 && paramConstraintWidget.mMatchConstraintDefaultWidth == 0)
              paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED; 
            paramInt = 0;
          } 
        } 
      } 
    } 
    if (paramConstraintWidget.isResolvedHorizontally()) {
      paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      i = 0;
    } 
    if (paramConstraintWidget.isResolvedVertically()) {
      paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      paramInt = 0;
    } 
    if (bool3)
      if (paramConstraintWidget.mResolvedMatchConstraintDefault[0] == 4) {
        paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      } else if (paramInt == 0) {
        if (paramMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
          paramInt = paramMeasure.verticalDimension;
        } else {
          paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
          paramMeasurer.measure(paramConstraintWidget, paramMeasure);
          paramInt = paramMeasure.measuredHeight;
        } 
        paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        if (paramConstraintWidget.mDimensionRatioSide == 0 || paramConstraintWidget.mDimensionRatioSide == -1) {
          paramMeasure.horizontalDimension = (int)(paramConstraintWidget.getDimensionRatio() * paramInt);
        } else {
          paramMeasure.horizontalDimension = (int)(paramConstraintWidget.getDimensionRatio() / paramInt);
        } 
      }  
    if (bool2)
      if (paramConstraintWidget.mResolvedMatchConstraintDefault[1] == 4) {
        paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      } else if (i == 0) {
        if (paramMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
          paramInt = paramMeasure.horizontalDimension;
        } else {
          paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
          paramMeasurer.measure(paramConstraintWidget, paramMeasure);
          paramInt = paramMeasure.measuredWidth;
        } 
        paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        if (paramConstraintWidget.mDimensionRatioSide == 0 || paramConstraintWidget.mDimensionRatioSide == -1) {
          paramMeasure.verticalDimension = (int)(paramInt / paramConstraintWidget.getDimensionRatio());
          paramMeasurer.measure(paramConstraintWidget, paramMeasure);
          paramConstraintWidget.setWidth(paramMeasure.measuredWidth);
          paramConstraintWidget.setHeight(paramMeasure.measuredHeight);
          paramConstraintWidget.setHasBaseline(paramMeasure.measuredHasBaseline);
          paramConstraintWidget.setBaselineDistance(paramMeasure.measuredBaseline);
          paramMeasure.measureStrategy = BasicMeasure.Measure.SELF_DIMENSIONS;
          return paramMeasure.measuredNeedsSolverPass;
        } 
        paramMeasure.verticalDimension = (int)(paramInt * paramConstraintWidget.getDimensionRatio());
      }  
    paramMeasurer.measure(paramConstraintWidget, paramMeasure);
    paramConstraintWidget.setWidth(paramMeasure.measuredWidth);
    paramConstraintWidget.setHeight(paramMeasure.measuredHeight);
    paramConstraintWidget.setHasBaseline(paramMeasure.measuredHasBaseline);
    paramConstraintWidget.setBaselineDistance(paramMeasure.measuredBaseline);
    paramMeasure.measureStrategy = BasicMeasure.Measure.SELF_DIMENSIONS;
    return paramMeasure.measuredNeedsSolverPass;
  }
  
  private void resetChains() {
    this.mHorizontalChainsSize = 0;
    this.mVerticalChainsSize = 0;
  }
  
  void addChain(ConstraintWidget paramConstraintWidget, int paramInt) {
    if (paramInt == 0) {
      addHorizontalChain(paramConstraintWidget);
    } else if (paramInt == 1) {
      addVerticalChain(paramConstraintWidget);
    } 
  }
  
  public boolean addChildrenToSolver(LinearSystem paramLinearSystem) {
    boolean bool1 = optimizeFor(64);
    addToSolver(paramLinearSystem, bool1);
    int i = this.mChildren.size();
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      ConstraintWidget constraintWidget = this.mChildren.get(b);
      constraintWidget.setInBarrier(0, false);
      constraintWidget.setInBarrier(1, false);
      if (constraintWidget instanceof Barrier)
        bool = true; 
      b++;
    } 
    if (bool)
      for (b = 0; b < i; b++) {
        ConstraintWidget constraintWidget = this.mChildren.get(b);
        if (constraintWidget instanceof Barrier)
          ((Barrier)constraintWidget).markWidgets(); 
      }  
    for (b = 0; b < i; b++) {
      ConstraintWidget constraintWidget = this.mChildren.get(b);
      if (constraintWidget.addFirst())
        constraintWidget.addToSolver(paramLinearSystem, bool1); 
    } 
    if (LinearSystem.USE_DEPENDENCY_ORDERING) {
      HashSet<ConstraintWidget> hashSet = new HashSet();
      for (b = 0; b < i; b++) {
        ConstraintWidget constraintWidget = this.mChildren.get(b);
        if (!constraintWidget.addFirst())
          hashSet.add(constraintWidget); 
      } 
      if (getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        b = 0;
      } else {
        b = 1;
      } 
      addChildrenToSolverByDependency(this, paramLinearSystem, hashSet, b, false);
      for (ConstraintWidget constraintWidget : hashSet) {
        Optimizer.checkMatchParent(this, paramLinearSystem, constraintWidget);
        constraintWidget.addToSolver(paramLinearSystem, bool1);
      } 
    } else {
      for (b = 0; b < i; b++) {
        ConstraintWidget constraintWidget = this.mChildren.get(b);
        if (constraintWidget instanceof ConstraintWidgetContainer) {
          ConstraintWidget.DimensionBehaviour dimensionBehaviour1 = constraintWidget.mListDimensionBehaviors[0];
          ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.mListDimensionBehaviors[1];
          if (dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED); 
          if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED); 
          constraintWidget.addToSolver(paramLinearSystem, bool1);
          if (dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            constraintWidget.setHorizontalDimensionBehaviour(dimensionBehaviour1); 
          if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            constraintWidget.setVerticalDimensionBehaviour(dimensionBehaviour2); 
        } else {
          Optimizer.checkMatchParent(this, paramLinearSystem, constraintWidget);
          if (!constraintWidget.addFirst())
            constraintWidget.addToSolver(paramLinearSystem, bool1); 
        } 
      } 
    } 
    if (this.mHorizontalChainsSize > 0)
      Chain.applyChainConstraints(this, paramLinearSystem, null, 0); 
    if (this.mVerticalChainsSize > 0)
      Chain.applyChainConstraints(this, paramLinearSystem, null, 1); 
    return true;
  }
  
  public void addHorizontalWrapMaxVariable(ConstraintAnchor paramConstraintAnchor) {
    WeakReference<ConstraintAnchor> weakReference = this.horizontalWrapMax;
    if (weakReference == null || weakReference.get() == null || paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.horizontalWrapMax.get()).getFinalValue())
      this.horizontalWrapMax = new WeakReference<ConstraintAnchor>(paramConstraintAnchor); 
  }
  
  public void addHorizontalWrapMinVariable(ConstraintAnchor paramConstraintAnchor) {
    WeakReference<ConstraintAnchor> weakReference = this.horizontalWrapMin;
    if (weakReference == null || weakReference.get() == null || paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.horizontalWrapMin.get()).getFinalValue())
      this.horizontalWrapMin = new WeakReference<ConstraintAnchor>(paramConstraintAnchor); 
  }
  
  void addVerticalWrapMaxVariable(ConstraintAnchor paramConstraintAnchor) {
    WeakReference<ConstraintAnchor> weakReference = this.verticalWrapMax;
    if (weakReference == null || weakReference.get() == null || paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.verticalWrapMax.get()).getFinalValue())
      this.verticalWrapMax = new WeakReference<ConstraintAnchor>(paramConstraintAnchor); 
  }
  
  void addVerticalWrapMinVariable(ConstraintAnchor paramConstraintAnchor) {
    WeakReference<ConstraintAnchor> weakReference = this.verticalWrapMin;
    if (weakReference == null || weakReference.get() == null || paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.verticalWrapMin.get()).getFinalValue())
      this.verticalWrapMin = new WeakReference<ConstraintAnchor>(paramConstraintAnchor); 
  }
  
  public void defineTerminalWidgets() {
    this.mDependencyGraph.defineTerminalWidgets(getHorizontalDimensionBehaviour(), getVerticalDimensionBehaviour());
  }
  
  public boolean directMeasure(boolean paramBoolean) {
    return this.mDependencyGraph.directMeasure(paramBoolean);
  }
  
  public boolean directMeasureSetup(boolean paramBoolean) {
    return this.mDependencyGraph.directMeasureSetup(paramBoolean);
  }
  
  public boolean directMeasureWithOrientation(boolean paramBoolean, int paramInt) {
    return this.mDependencyGraph.directMeasureWithOrientation(paramBoolean, paramInt);
  }
  
  public void fillMetrics(Metrics paramMetrics) {
    this.mMetrics = paramMetrics;
    this.mSystem.fillMetrics(paramMetrics);
  }
  
  public ArrayList<Guideline> getHorizontalGuidelines() {
    ArrayList<ConstraintWidget> arrayList = new ArrayList();
    int i = this.mChildren.size();
    for (byte b = 0; b < i; b++) {
      ConstraintWidget constraintWidget = this.mChildren.get(b);
      if (constraintWidget instanceof Guideline) {
        constraintWidget = constraintWidget;
        if (constraintWidget.getOrientation() == 0)
          arrayList.add(constraintWidget); 
      } 
    } 
    return (ArrayList)arrayList;
  }
  
  public BasicMeasure.Measurer getMeasurer() {
    return this.mMeasurer;
  }
  
  public int getOptimizationLevel() {
    return this.mOptimizationLevel;
  }
  
  public LinearSystem getSystem() {
    return this.mSystem;
  }
  
  public String getType() {
    return "ConstraintLayout";
  }
  
  public ArrayList<Guideline> getVerticalGuidelines() {
    ArrayList<ConstraintWidget> arrayList = new ArrayList();
    int i = this.mChildren.size();
    for (byte b = 0; b < i; b++) {
      ConstraintWidget constraintWidget = this.mChildren.get(b);
      if (constraintWidget instanceof Guideline) {
        constraintWidget = constraintWidget;
        if (constraintWidget.getOrientation() == 1)
          arrayList.add(constraintWidget); 
      } 
    } 
    return (ArrayList)arrayList;
  }
  
  public boolean handlesInternalConstraints() {
    return false;
  }
  
  public void invalidateGraph() {
    this.mDependencyGraph.invalidateGraph();
  }
  
  public void invalidateMeasures() {
    this.mDependencyGraph.invalidateMeasures();
  }
  
  public boolean isHeightMeasuredTooSmall() {
    return this.mHeightMeasuredTooSmall;
  }
  
  public boolean isRtl() {
    return this.mIsRtl;
  }
  
  public boolean isWidthMeasuredTooSmall() {
    return this.mWidthMeasuredTooSmall;
  }
  
  public void layout() {
    int i;
    Object object;
    int m;
    int n;
    boolean bool;
    this.mX = 0;
    this.mY = 0;
    this.mWidthMeasuredTooSmall = false;
    this.mHeightMeasuredTooSmall = false;
    int i1 = this.mChildren.size();
    int j = Math.max(0, getWidth());
    int k = Math.max(0, getHeight());
    ConstraintWidget.DimensionBehaviour dimensionBehaviour1 = this.mListDimensionBehaviors[1];
    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.mListDimensionBehaviors[0];
    Metrics metrics = this.mMetrics;
    if (metrics != null)
      metrics.layouts++; 
    if (Optimizer.enabled(this.mOptimizationLevel, 1)) {
      Direct.solvingPass(this, getMeasurer());
      for (i = 0; i < i1; i++) {
        ConstraintWidget constraintWidget = this.mChildren.get(i);
        if (constraintWidget.isMeasureRequested() && !(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Barrier) && !(constraintWidget instanceof VirtualLayout) && !constraintWidget.isInVirtualLayout()) {
          ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget.getDimensionBehaviour(0);
          ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = constraintWidget.getDimensionBehaviour(1);
          if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight != 1) {
            m = 1;
          } else {
            m = 0;
          } 
          if (!m) {
            BasicMeasure.Measure measure = new BasicMeasure.Measure();
            measure(constraintWidget, this.mMeasurer, measure, BasicMeasure.Measure.SELF_DIMENSIONS);
          } 
        } 
      } 
    } 
    if (i1 > 2 && (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && Optimizer.enabled(this.mOptimizationLevel, 1024) && Grouping.simpleSolvingPass(this, getMeasurer())) {
      i = j;
      if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        if (j < getWidth() && j > 0) {
          setWidth(j);
          this.mWidthMeasuredTooSmall = true;
          i = j;
        } else {
          i = getWidth();
        }  
      j = k;
      if (dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        if (k < getHeight() && k > 0) {
          setHeight(k);
          this.mHeightMeasuredTooSmall = true;
          j = k;
        } else {
          j = getHeight();
        }  
      n = i;
      i = 1;
      m = j;
    } else {
      i = 0;
      m = k;
      n = j;
    } 
    if (optimizeFor(64) || optimizeFor(128)) {
      j = 1;
    } else {
      j = 0;
    } 
    this.mSystem.graphOptimizer = false;
    this.mSystem.newgraphOptimizer = false;
    if (this.mOptimizationLevel != 0 && j != 0)
      this.mSystem.newgraphOptimizer = true; 
    ArrayList<ConstraintWidget> arrayList = this.mChildren;
    if (getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      bool = true;
    } else {
      bool = false;
    } 
    resetChains();
    for (j = 0; j < i1; j++) {
      ConstraintWidget constraintWidget = this.mChildren.get(j);
      if (constraintWidget instanceof WidgetContainer)
        ((WidgetContainer)constraintWidget).layout(); 
    } 
    boolean bool2 = optimizeFor(64);
    j = 0;
    boolean bool1 = true;
    while (bool1) {
      int i3 = object + 1;
      boolean bool3 = bool1;
      try {
        this.mSystem.reset();
        bool3 = bool1;
        resetChains();
        bool3 = bool1;
        createObjectVariables(this.mSystem);
        for (byte b = 0; b < i1; b++) {
          bool3 = bool1;
          ((ConstraintWidget)this.mChildren.get(b)).createObjectVariables(this.mSystem);
        } 
        bool3 = bool1;
        bool1 = addChildrenToSolver(this.mSystem);
        bool3 = bool1;
        if (this.verticalWrapMin != null) {
          bool3 = bool1;
          if (this.verticalWrapMin.get() != null) {
            bool3 = bool1;
            addMinWrap(this.verticalWrapMin.get(), this.mSystem.createObjectVariable(this.mTop));
            bool3 = bool1;
            this.verticalWrapMin = null;
          } 
        } 
        bool3 = bool1;
        if (this.verticalWrapMax != null) {
          bool3 = bool1;
          if (this.verticalWrapMax.get() != null) {
            bool3 = bool1;
            addMaxWrap(this.verticalWrapMax.get(), this.mSystem.createObjectVariable(this.mBottom));
            bool3 = bool1;
            this.verticalWrapMax = null;
          } 
        } 
        bool3 = bool1;
        if (this.horizontalWrapMin != null) {
          bool3 = bool1;
          if (this.horizontalWrapMin.get() != null) {
            bool3 = bool1;
            addMinWrap(this.horizontalWrapMin.get(), this.mSystem.createObjectVariable(this.mLeft));
            bool3 = bool1;
            this.horizontalWrapMin = null;
          } 
        } 
        bool3 = bool1;
        if (this.horizontalWrapMax != null) {
          bool3 = bool1;
          if (this.horizontalWrapMax.get() != null) {
            bool3 = bool1;
            addMaxWrap(this.horizontalWrapMax.get(), this.mSystem.createObjectVariable(this.mRight));
            bool3 = bool1;
            this.horizontalWrapMax = null;
          } 
        } 
        bool3 = bool1;
        if (bool1) {
          bool3 = bool1;
          this.mSystem.minimize();
          bool3 = bool1;
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EXCEPTION : ");
        stringBuilder.append(exception);
        printStream.println(stringBuilder.toString());
      } 
      if (bool3) {
        updateChildrenFromSolver(this.mSystem, Optimizer.flags);
      } else {
        updateFromSolver(this.mSystem, bool2);
        for (byte b = 0; b < i1; b++)
          ((ConstraintWidget)this.mChildren.get(b)).updateFromSolver(this.mSystem, bool2); 
      } 
      if (bool && i3 < 8 && Optimizer.flags[2]) {
        k = 0;
        int i5 = 0;
        int i4 = 0;
        while (k < i1) {
          ConstraintWidget constraintWidget = this.mChildren.get(k);
          i5 = Math.max(i5, constraintWidget.mX + constraintWidget.getWidth());
          i4 = Math.max(i4, constraintWidget.mY + constraintWidget.getHeight());
          k++;
        } 
        i5 = Math.max(this.mMinWidth, i5);
        k = Math.max(this.mMinHeight, i4);
        if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && getWidth() < i5) {
          setWidth(i5);
          this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
          bool1 = true;
          i4 = 1;
        } else {
          bool1 = false;
          i4 = i;
        } 
        bool3 = bool1;
        i = i4;
        if (dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          bool3 = bool1;
          i = i4;
          if (getHeight() < k) {
            setHeight(k);
            this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            bool3 = true;
            i = 1;
          } 
        } 
      } else {
        bool3 = false;
      } 
      int i2 = Math.max(this.mMinWidth, getWidth());
      if (i2 > getWidth()) {
        setWidth(i2);
        this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
        bool3 = true;
        i = 1;
      } 
      i2 = Math.max(this.mMinHeight, getHeight());
      if (i2 > getHeight()) {
        setHeight(i2);
        this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
        bool3 = true;
        i = 1;
      } 
      boolean bool4 = bool3;
      k = i;
      if (i == 0) {
        bool1 = bool3;
        i2 = i;
        if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          bool1 = bool3;
          i2 = i;
          if (n > 0) {
            bool1 = bool3;
            i2 = i;
            if (getWidth() > n) {
              this.mWidthMeasuredTooSmall = true;
              this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
              setWidth(n);
              bool1 = true;
              i2 = 1;
            } 
          } 
        } 
        bool4 = bool1;
        k = i2;
        if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          bool4 = bool1;
          k = i2;
          if (m > 0) {
            bool4 = bool1;
            k = i2;
            if (getHeight() > m) {
              this.mHeightMeasuredTooSmall = true;
              this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
              setHeight(m);
              i = 1;
              bool1 = true;
              continue;
            } 
          } 
        } 
      } 
      bool1 = bool4;
      i = k;
      continue;
      object = SYNTHETIC_LOCAL_VARIABLE_8;
    } 
    this.mChildren = arrayList;
    if (i != 0) {
      this.mListDimensionBehaviors[0] = dimensionBehaviour2;
      this.mListDimensionBehaviors[1] = dimensionBehaviour1;
    } 
    resetSolverVariables(this.mSystem.getCache());
  }
  
  public long measure(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9) {
    this.mPaddingLeft = paramInt8;
    this.mPaddingTop = paramInt9;
    return this.mBasicMeasureSolver.solverMeasure(this, paramInt1, paramInt8, paramInt9, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
  }
  
  public boolean optimizeFor(int paramInt) {
    boolean bool;
    if ((this.mOptimizationLevel & paramInt) == paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void reset() {
    this.mSystem.reset();
    this.mPaddingLeft = 0;
    this.mPaddingRight = 0;
    this.mPaddingTop = 0;
    this.mPaddingBottom = 0;
    this.mSkipSolver = false;
    super.reset();
  }
  
  public void setMeasurer(BasicMeasure.Measurer paramMeasurer) {
    this.mMeasurer = paramMeasurer;
    this.mDependencyGraph.setMeasurer(paramMeasurer);
  }
  
  public void setOptimizationLevel(int paramInt) {
    this.mOptimizationLevel = paramInt;
    LinearSystem.USE_DEPENDENCY_ORDERING = optimizeFor(512);
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mPaddingLeft = paramInt1;
    this.mPaddingTop = paramInt2;
    this.mPaddingRight = paramInt3;
    this.mPaddingBottom = paramInt4;
  }
  
  public void setRtl(boolean paramBoolean) {
    this.mIsRtl = paramBoolean;
  }
  
  public void updateChildrenFromSolver(LinearSystem paramLinearSystem, boolean[] paramArrayOfboolean) {
    byte b = 0;
    paramArrayOfboolean[2] = false;
    boolean bool = optimizeFor(64);
    updateFromSolver(paramLinearSystem, bool);
    int i = this.mChildren.size();
    while (b < i) {
      ((ConstraintWidget)this.mChildren.get(b)).updateFromSolver(paramLinearSystem, bool);
      b++;
    } 
  }
  
  public void updateFromRuns(boolean paramBoolean1, boolean paramBoolean2) {
    super.updateFromRuns(paramBoolean1, paramBoolean2);
    int i = this.mChildren.size();
    for (byte b = 0; b < i; b++)
      ((ConstraintWidget)this.mChildren.get(b)).updateFromRuns(paramBoolean1, paramBoolean2); 
  }
  
  public void updateHierarchy() {
    this.mBasicMeasureSolver.updateHierarchy(this);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\ConstraintWidgetContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */