package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;

public class Optimizer {
  static final int FLAG_CHAIN_DANGLING = 1;
  
  static final int FLAG_RECOMPUTE_BOUNDS = 2;
  
  static final int FLAG_USE_OPTIMIZE = 0;
  
  public static final int OPTIMIZATION_BARRIER = 2;
  
  public static final int OPTIMIZATION_CACHE_MEASURES = 256;
  
  public static final int OPTIMIZATION_CHAIN = 4;
  
  public static final int OPTIMIZATION_DEPENDENCY_ORDERING = 512;
  
  public static final int OPTIMIZATION_DIMENSIONS = 8;
  
  public static final int OPTIMIZATION_DIRECT = 1;
  
  public static final int OPTIMIZATION_GRAPH = 64;
  
  public static final int OPTIMIZATION_GRAPH_WRAP = 128;
  
  public static final int OPTIMIZATION_GROUPING = 1024;
  
  public static final int OPTIMIZATION_GROUPS = 32;
  
  public static final int OPTIMIZATION_NONE = 0;
  
  public static final int OPTIMIZATION_RATIO = 16;
  
  public static final int OPTIMIZATION_STANDARD = 257;
  
  static boolean[] flags = new boolean[3];
  
  static void checkMatchParent(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, ConstraintWidget paramConstraintWidget) {
    paramConstraintWidget.mHorizontalResolution = -1;
    paramConstraintWidget.mVerticalResolution = -1;
    if (paramConstraintWidgetContainer.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && paramConstraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
      int i = paramConstraintWidget.mLeft.mMargin;
      int j = paramConstraintWidgetContainer.getWidth() - paramConstraintWidget.mRight.mMargin;
      paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
      paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
      paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, i);
      paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, j);
      paramConstraintWidget.mHorizontalResolution = 2;
      paramConstraintWidget.setHorizontalDimension(i, j);
    } 
    if (paramConstraintWidgetContainer.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && paramConstraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
      int j = paramConstraintWidget.mTop.mMargin;
      int i = paramConstraintWidgetContainer.getHeight() - paramConstraintWidget.mBottom.mMargin;
      paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
      paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
      paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, j);
      paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i);
      if (paramConstraintWidget.mBaselineDistance > 0 || paramConstraintWidget.getVisibility() == 8) {
        paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
        paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, paramConstraintWidget.mBaselineDistance + j);
      } 
      paramConstraintWidget.mVerticalResolution = 2;
      paramConstraintWidget.setVerticalDimension(j, i);
    } 
  }
  
  public static final boolean enabled(int paramInt1, int paramInt2) {
    boolean bool;
    if ((paramInt1 & paramInt2) == paramInt2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\Optimizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */