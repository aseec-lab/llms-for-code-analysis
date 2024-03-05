package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import java.util.ArrayList;

public class BasicMeasure {
  public static final int AT_MOST = -2147483648;
  
  private static final boolean DEBUG = false;
  
  public static final int EXACTLY = 1073741824;
  
  public static final int FIXED = -3;
  
  public static final int MATCH_PARENT = -1;
  
  private static final int MODE_SHIFT = 30;
  
  public static final int UNSPECIFIED = 0;
  
  public static final int WRAP_CONTENT = -2;
  
  private ConstraintWidgetContainer constraintWidgetContainer;
  
  private Measure mMeasure = new Measure();
  
  private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<ConstraintWidget>();
  
  public BasicMeasure(ConstraintWidgetContainer paramConstraintWidgetContainer) {
    this.constraintWidgetContainer = paramConstraintWidgetContainer;
  }
  
  private boolean measure(Measurer paramMeasurer, ConstraintWidget paramConstraintWidget, int paramInt) {
    boolean bool;
    this.mMeasure.horizontalBehavior = paramConstraintWidget.getHorizontalDimensionBehaviour();
    this.mMeasure.verticalBehavior = paramConstraintWidget.getVerticalDimensionBehaviour();
    this.mMeasure.horizontalDimension = paramConstraintWidget.getWidth();
    this.mMeasure.verticalDimension = paramConstraintWidget.getHeight();
    this.mMeasure.measuredNeedsSolverPass = false;
    this.mMeasure.measureStrategy = paramInt;
    if (this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      bool = true;
    } else {
      bool = false;
    } 
    if (this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (bool && paramConstraintWidget.mDimensionRatio > 0.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    if (paramInt != 0 && paramConstraintWidget.mDimensionRatio > 0.0F) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (bool && paramConstraintWidget.mResolvedMatchConstraintDefault[0] == 4)
      this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED; 
    if (paramInt != 0 && paramConstraintWidget.mResolvedMatchConstraintDefault[1] == 4)
      this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED; 
    paramMeasurer.measure(paramConstraintWidget, this.mMeasure);
    paramConstraintWidget.setWidth(this.mMeasure.measuredWidth);
    paramConstraintWidget.setHeight(this.mMeasure.measuredHeight);
    paramConstraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
    paramConstraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    this.mMeasure.measureStrategy = Measure.SELF_DIMENSIONS;
    return this.mMeasure.measuredNeedsSolverPass;
  }
  
  private void measureChildren(ConstraintWidgetContainer paramConstraintWidgetContainer) {
    // Byte code:
    //   0: aload_1
    //   1: getfield mChildren : Ljava/util/ArrayList;
    //   4: invokevirtual size : ()I
    //   7: istore #6
    //   9: aload_1
    //   10: bipush #64
    //   12: invokevirtual optimizeFor : (I)Z
    //   15: istore #7
    //   17: aload_1
    //   18: invokevirtual getMeasurer : ()Landroidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measurer;
    //   21: astore #8
    //   23: iconst_0
    //   24: istore #4
    //   26: iload #4
    //   28: iload #6
    //   30: if_icmpge -> 390
    //   33: aload_1
    //   34: getfield mChildren : Ljava/util/ArrayList;
    //   37: iload #4
    //   39: invokevirtual get : (I)Ljava/lang/Object;
    //   42: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   45: astore #9
    //   47: aload #9
    //   49: instanceof androidx/constraintlayout/solver/widgets/Guideline
    //   52: ifeq -> 58
    //   55: goto -> 384
    //   58: aload #9
    //   60: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   63: ifeq -> 69
    //   66: goto -> 384
    //   69: aload #9
    //   71: invokevirtual isInVirtualLayout : ()Z
    //   74: ifeq -> 80
    //   77: goto -> 384
    //   80: iload #7
    //   82: ifeq -> 132
    //   85: aload #9
    //   87: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   90: ifnull -> 132
    //   93: aload #9
    //   95: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   98: ifnull -> 132
    //   101: aload #9
    //   103: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   106: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   109: getfield resolved : Z
    //   112: ifeq -> 132
    //   115: aload #9
    //   117: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   120: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   123: getfield resolved : Z
    //   126: ifeq -> 132
    //   129: goto -> 384
    //   132: aload #9
    //   134: iconst_0
    //   135: invokevirtual getDimensionBehaviour : (I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   138: astore #10
    //   140: iconst_1
    //   141: istore #5
    //   143: aload #9
    //   145: iconst_1
    //   146: invokevirtual getDimensionBehaviour : (I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   149: astore #11
    //   151: aload #10
    //   153: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   156: if_acmpne -> 190
    //   159: aload #9
    //   161: getfield mMatchConstraintDefaultWidth : I
    //   164: iconst_1
    //   165: if_icmpeq -> 190
    //   168: aload #11
    //   170: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   173: if_acmpne -> 190
    //   176: aload #9
    //   178: getfield mMatchConstraintDefaultHeight : I
    //   181: iconst_1
    //   182: if_icmpeq -> 190
    //   185: iconst_1
    //   186: istore_2
    //   187: goto -> 192
    //   190: iconst_0
    //   191: istore_2
    //   192: iload_2
    //   193: istore_3
    //   194: iload_2
    //   195: ifne -> 338
    //   198: iload_2
    //   199: istore_3
    //   200: aload_1
    //   201: iconst_1
    //   202: invokevirtual optimizeFor : (I)Z
    //   205: ifeq -> 338
    //   208: iload_2
    //   209: istore_3
    //   210: aload #9
    //   212: instanceof androidx/constraintlayout/solver/widgets/VirtualLayout
    //   215: ifne -> 338
    //   218: iload_2
    //   219: istore_3
    //   220: aload #10
    //   222: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   225: if_acmpne -> 260
    //   228: iload_2
    //   229: istore_3
    //   230: aload #9
    //   232: getfield mMatchConstraintDefaultWidth : I
    //   235: ifne -> 260
    //   238: iload_2
    //   239: istore_3
    //   240: aload #11
    //   242: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   245: if_acmpeq -> 260
    //   248: iload_2
    //   249: istore_3
    //   250: aload #9
    //   252: invokevirtual isInHorizontalChain : ()Z
    //   255: ifne -> 260
    //   258: iconst_1
    //   259: istore_3
    //   260: iload_3
    //   261: istore_2
    //   262: aload #11
    //   264: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   267: if_acmpne -> 302
    //   270: iload_3
    //   271: istore_2
    //   272: aload #9
    //   274: getfield mMatchConstraintDefaultHeight : I
    //   277: ifne -> 302
    //   280: iload_3
    //   281: istore_2
    //   282: aload #10
    //   284: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   287: if_acmpeq -> 302
    //   290: iload_3
    //   291: istore_2
    //   292: aload #9
    //   294: invokevirtual isInHorizontalChain : ()Z
    //   297: ifne -> 302
    //   300: iconst_1
    //   301: istore_2
    //   302: aload #10
    //   304: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   307: if_acmpeq -> 320
    //   310: iload_2
    //   311: istore_3
    //   312: aload #11
    //   314: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   317: if_acmpne -> 338
    //   320: iload_2
    //   321: istore_3
    //   322: aload #9
    //   324: getfield mDimensionRatio : F
    //   327: fconst_0
    //   328: fcmpl
    //   329: ifle -> 338
    //   332: iload #5
    //   334: istore_2
    //   335: goto -> 340
    //   338: iload_3
    //   339: istore_2
    //   340: iload_2
    //   341: ifeq -> 347
    //   344: goto -> 384
    //   347: aload_0
    //   348: aload #8
    //   350: aload #9
    //   352: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.SELF_DIMENSIONS : I
    //   355: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measurer;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)Z
    //   358: pop
    //   359: aload_1
    //   360: getfield mMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   363: ifnull -> 384
    //   366: aload_1
    //   367: getfield mMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   370: astore #9
    //   372: aload #9
    //   374: aload #9
    //   376: getfield measuredWidgets : J
    //   379: lconst_1
    //   380: ladd
    //   381: putfield measuredWidgets : J
    //   384: iinc #4, 1
    //   387: goto -> 26
    //   390: aload #8
    //   392: invokeinterface didMeasures : ()V
    //   397: return
  }
  
  private void solveLinearSystem(ConstraintWidgetContainer paramConstraintWidgetContainer, String paramString, int paramInt1, int paramInt2) {
    int i = paramConstraintWidgetContainer.getMinWidth();
    int j = paramConstraintWidgetContainer.getMinHeight();
    paramConstraintWidgetContainer.setMinWidth(0);
    paramConstraintWidgetContainer.setMinHeight(0);
    paramConstraintWidgetContainer.setWidth(paramInt1);
    paramConstraintWidgetContainer.setHeight(paramInt2);
    paramConstraintWidgetContainer.setMinWidth(i);
    paramConstraintWidgetContainer.setMinHeight(j);
    this.constraintWidgetContainer.layout();
  }
  
  public long solverMeasure(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9) {
    Measurer measurer = paramConstraintWidgetContainer.getMeasurer();
    paramInt9 = paramConstraintWidgetContainer.mChildren.size();
    int j = paramConstraintWidgetContainer.getWidth();
    int i = paramConstraintWidgetContainer.getHeight();
    boolean bool = Optimizer.enabled(paramInt1, 128);
    if (bool || Optimizer.enabled(paramInt1, 64)) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    paramInt3 = paramInt1;
    if (paramInt1 != 0) {
      paramInt2 = 0;
      while (true) {
        paramInt3 = paramInt1;
        if (paramInt2 < paramInt9) {
          ConstraintWidget constraintWidget = paramConstraintWidgetContainer.mChildren.get(paramInt2);
          if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            paramInt3 = 1;
          } else {
            paramInt3 = 0;
          } 
          if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            paramInt8 = 1;
          } else {
            paramInt8 = 0;
          } 
          if (paramInt3 != 0 && paramInt8 != 0 && constraintWidget.getDimensionRatio() > 0.0F) {
            paramInt3 = 1;
          } else {
            paramInt3 = 0;
          } 
          if ((constraintWidget.isInHorizontalChain() && paramInt3 != 0) || (constraintWidget.isInVerticalChain() && paramInt3 != 0) || constraintWidget instanceof VirtualLayout || constraintWidget.isInHorizontalChain() || constraintWidget.isInVerticalChain()) {
            paramInt3 = 0;
            break;
          } 
          paramInt2++;
          continue;
        } 
        break;
      } 
    } 
    if (paramInt3 != 0 && LinearSystem.sMetrics != null) {
      Metrics metrics = LinearSystem.sMetrics;
      metrics.measures++;
    } 
    if ((paramInt4 == 1073741824 && paramInt6 == 1073741824) || bool) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    int k = paramInt3 & paramInt1;
    if (k != 0) {
      boolean bool1;
      paramInt1 = Math.min(paramConstraintWidgetContainer.getMaxWidth(), paramInt5);
      paramInt2 = Math.min(paramConstraintWidgetContainer.getMaxHeight(), paramInt7);
      if (paramInt4 == 1073741824 && paramConstraintWidgetContainer.getWidth() != paramInt1) {
        paramConstraintWidgetContainer.setWidth(paramInt1);
        paramConstraintWidgetContainer.invalidateGraph();
      } 
      if (paramInt6 == 1073741824 && paramConstraintWidgetContainer.getHeight() != paramInt2) {
        paramConstraintWidgetContainer.setHeight(paramInt2);
        paramConstraintWidgetContainer.invalidateGraph();
      } 
      if (paramInt4 == 1073741824 && paramInt6 == 1073741824) {
        bool1 = paramConstraintWidgetContainer.directMeasure(bool);
        paramInt1 = 2;
      } else {
        bool1 = paramConstraintWidgetContainer.directMeasureSetup(bool);
        if (paramInt4 == 1073741824) {
          bool1 &= paramConstraintWidgetContainer.directMeasureWithOrientation(bool, 0);
          paramInt1 = 1;
        } else {
          paramInt1 = 0;
        } 
        if (paramInt6 == 1073741824) {
          bool1 = paramConstraintWidgetContainer.directMeasureWithOrientation(bool, 1) & bool1;
          paramInt1++;
        } 
      } 
      bool = bool1;
      paramInt2 = paramInt1;
      if (bool1) {
        boolean bool2;
        if (paramInt4 == 1073741824) {
          bool = true;
        } else {
          bool = false;
        } 
        if (paramInt6 == 1073741824) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        paramConstraintWidgetContainer.updateFromRuns(bool, bool2);
        bool = bool1;
        paramInt2 = paramInt1;
      } 
    } else {
      bool = false;
      paramInt2 = 0;
    } 
    if (!bool || paramInt2 != 2) {
      paramInt4 = paramConstraintWidgetContainer.getOptimizationLevel();
      if (paramInt9 > 0)
        measureChildren(paramConstraintWidgetContainer); 
      updateHierarchy(paramConstraintWidgetContainer);
      paramInt7 = this.mVariableDimensionsWidgets.size();
      if (paramInt9 > 0)
        solveLinearSystem(paramConstraintWidgetContainer, "First pass", j, i); 
      if (paramInt7 > 0) {
        boolean bool1;
        int m;
        boolean bool2;
        if (paramConstraintWidgetContainer.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          paramInt8 = 1;
        } else {
          paramInt8 = 0;
        } 
        if (paramConstraintWidgetContainer.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          paramInt9 = 1;
        } else {
          paramInt9 = 0;
        } 
        paramInt2 = Math.max(paramConstraintWidgetContainer.getWidth(), this.constraintWidgetContainer.getMinWidth());
        paramInt1 = Math.max(paramConstraintWidgetContainer.getHeight(), this.constraintWidgetContainer.getMinHeight());
        paramInt6 = 0;
        paramInt3 = 0;
        while (paramInt6 < paramInt7) {
          boolean bool3;
          ConstraintWidget constraintWidget = this.mVariableDimensionsWidgets.get(paramInt6);
          if (!(constraintWidget instanceof VirtualLayout)) {
            paramInt5 = paramInt3;
          } else {
            int i1 = constraintWidget.getWidth();
            int n = constraintWidget.getHeight();
            bool3 = measure(measurer, constraintWidget, Measure.TRY_GIVEN_DIMENSIONS) | paramInt3;
            if (paramConstraintWidgetContainer.mMetrics != null) {
              Metrics metrics = paramConstraintWidgetContainer.mMetrics;
              metrics.measuredMatchWidgets++;
            } 
            paramInt3 = constraintWidget.getWidth();
            m = constraintWidget.getHeight();
            if (paramInt3 != i1) {
              constraintWidget.setWidth(paramInt3);
              paramInt3 = paramInt2;
              if (paramInt8 != 0) {
                paramInt3 = paramInt2;
                if (constraintWidget.getRight() > paramInt2)
                  paramInt3 = Math.max(paramInt2, constraintWidget.getRight() + constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin()); 
              } 
              bool3 = true;
              paramInt2 = paramInt3;
            } 
            paramInt3 = paramInt1;
            if (m != n) {
              constraintWidget.setHeight(m);
              paramInt3 = paramInt1;
              if (paramInt9 != 0) {
                paramInt3 = paramInt1;
                if (constraintWidget.getBottom() > paramInt1)
                  paramInt3 = Math.max(paramInt1, constraintWidget.getBottom() + constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin()); 
              } 
              bool3 = true;
            } 
            bool3 |= ((VirtualLayout)constraintWidget).needSolverPass();
            paramInt1 = paramInt3;
          } 
          paramInt6++;
          bool1 = bool3;
        } 
        byte b = 0;
        paramInt5 = k;
        while (true) {
          m = paramInt2;
          k = paramInt1;
          bool2 = bool1;
          if (b < 2) {
            k = 0;
            boolean bool3 = bool1;
            int n = paramInt7;
            while (k < n) {
              boolean bool4;
              ConstraintWidget constraintWidget = this.mVariableDimensionsWidgets.get(k);
              if ((constraintWidget instanceof androidx.constraintlayout.solver.widgets.Helper && !(constraintWidget instanceof VirtualLayout)) || constraintWidget instanceof androidx.constraintlayout.solver.widgets.Guideline || constraintWidget.getVisibility() == 8 || (paramInt5 != 0 && constraintWidget.horizontalRun.dimension.resolved && constraintWidget.verticalRun.dimension.resolved) || constraintWidget instanceof VirtualLayout) {
                m = paramInt2;
                bool4 = bool3;
              } else {
                int i4 = constraintWidget.getWidth();
                int i2 = constraintWidget.getHeight();
                m = constraintWidget.getBaselineDistance();
                paramInt7 = Measure.TRY_GIVEN_DIMENSIONS;
                if (b == 1)
                  paramInt7 = Measure.USE_GIVEN_DIMENSIONS; 
                bool4 = measure(measurer, constraintWidget, paramInt7) | bool3;
                if (paramConstraintWidgetContainer.mMetrics != null) {
                  Metrics metrics = paramConstraintWidgetContainer.mMetrics;
                  metrics.measuredMatchWidgets++;
                } 
                int i5 = constraintWidget.getWidth();
                int i3 = constraintWidget.getHeight();
                int i1 = paramInt2;
                if (i5 != i4) {
                  constraintWidget.setWidth(i5);
                  i1 = paramInt2;
                  if (paramInt8 != 0) {
                    i1 = paramInt2;
                    if (constraintWidget.getRight() > paramInt2)
                      i1 = Math.max(paramInt2, constraintWidget.getRight() + constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin()); 
                  } 
                  bool4 = true;
                } 
                paramInt2 = paramInt1;
                if (i3 != i2) {
                  constraintWidget.setHeight(i3);
                  paramInt2 = paramInt1;
                  if (paramInt9 != 0) {
                    paramInt2 = paramInt1;
                    if (constraintWidget.getBottom() > paramInt1)
                      paramInt2 = Math.max(paramInt1, constraintWidget.getBottom() + constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin()); 
                  } 
                  bool4 = true;
                } 
                if (constraintWidget.hasBaseline() && m != constraintWidget.getBaselineDistance()) {
                  bool4 = true;
                  m = i1;
                  paramInt1 = paramInt2;
                } else {
                  paramInt1 = paramInt2;
                  m = i1;
                } 
              } 
              k++;
              paramInt2 = m;
              bool3 = bool4;
            } 
            m = paramInt2;
            k = paramInt1;
            bool2 = bool3;
            if (bool3) {
              solveLinearSystem(paramConstraintWidgetContainer, "intermediate pass", j, i);
              b++;
              bool3 = false;
              paramInt7 = n;
              boolean bool4 = bool3;
              continue;
            } 
          } 
          break;
        } 
        if (bool2) {
          solveLinearSystem(paramConstraintWidgetContainer, "2nd pass", j, i);
          if (paramConstraintWidgetContainer.getWidth() < m) {
            paramConstraintWidgetContainer.setWidth(m);
            paramInt1 = 1;
          } else {
            paramInt1 = 0;
          } 
          if (paramConstraintWidgetContainer.getHeight() < k) {
            paramConstraintWidgetContainer.setHeight(k);
            paramInt1 = 1;
          } 
          if (paramInt1 != 0)
            solveLinearSystem(paramConstraintWidgetContainer, "3rd pass", j, i); 
        } 
        paramInt1 = paramInt4;
      } else {
        paramInt1 = paramInt4;
      } 
      paramConstraintWidgetContainer.setOptimizationLevel(paramInt1);
    } 
    return 0L;
  }
  
  public void updateHierarchy(ConstraintWidgetContainer paramConstraintWidgetContainer) {
    this.mVariableDimensionsWidgets.clear();
    int i = paramConstraintWidgetContainer.mChildren.size();
    for (byte b = 0; b < i; b++) {
      ConstraintWidget constraintWidget = paramConstraintWidgetContainer.mChildren.get(b);
      if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        this.mVariableDimensionsWidgets.add(constraintWidget); 
    } 
    paramConstraintWidgetContainer.invalidateGraph();
  }
  
  public static class Measure {
    public static int SELF_DIMENSIONS = 0;
    
    public static int TRY_GIVEN_DIMENSIONS = 1;
    
    public static int USE_GIVEN_DIMENSIONS = 2;
    
    public ConstraintWidget.DimensionBehaviour horizontalBehavior;
    
    public int horizontalDimension;
    
    public int measureStrategy;
    
    public int measuredBaseline;
    
    public boolean measuredHasBaseline;
    
    public int measuredHeight;
    
    public boolean measuredNeedsSolverPass;
    
    public int measuredWidth;
    
    public ConstraintWidget.DimensionBehaviour verticalBehavior;
    
    public int verticalDimension;
  }
  
  public static interface Measurer {
    void didMeasures();
    
    void measure(ConstraintWidget param1ConstraintWidget, BasicMeasure.Measure param1Measure);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\BasicMeasure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */