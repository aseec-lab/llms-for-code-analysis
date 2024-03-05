package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;

public class VirtualLayout extends HelperWidget {
  protected BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
  
  private int mMeasuredHeight = 0;
  
  private int mMeasuredWidth = 0;
  
  BasicMeasure.Measurer mMeasurer = null;
  
  private boolean mNeedsCallFromSolver = false;
  
  private int mPaddingBottom = 0;
  
  private int mPaddingEnd = 0;
  
  private int mPaddingLeft = 0;
  
  private int mPaddingRight = 0;
  
  private int mPaddingStart = 0;
  
  private int mPaddingTop = 0;
  
  private int mResolvedPaddingLeft = 0;
  
  private int mResolvedPaddingRight = 0;
  
  public void applyRtl(boolean paramBoolean) {
    if (this.mPaddingStart > 0 || this.mPaddingEnd > 0)
      if (paramBoolean) {
        this.mResolvedPaddingLeft = this.mPaddingEnd;
        this.mResolvedPaddingRight = this.mPaddingStart;
      } else {
        this.mResolvedPaddingLeft = this.mPaddingStart;
        this.mResolvedPaddingRight = this.mPaddingEnd;
      }  
  }
  
  public void captureWidgets() {
    for (byte b = 0; b < this.mWidgetsCount; b++) {
      ConstraintWidget constraintWidget = this.mWidgets[b];
      if (constraintWidget != null)
        constraintWidget.setInVirtualLayout(true); 
    } 
  }
  
  public int getMeasuredHeight() {
    return this.mMeasuredHeight;
  }
  
  public int getMeasuredWidth() {
    return this.mMeasuredWidth;
  }
  
  public int getPaddingBottom() {
    return this.mPaddingBottom;
  }
  
  public int getPaddingLeft() {
    return this.mResolvedPaddingLeft;
  }
  
  public int getPaddingRight() {
    return this.mResolvedPaddingRight;
  }
  
  public int getPaddingTop() {
    return this.mPaddingTop;
  }
  
  public void measure(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  protected void measure(ConstraintWidget paramConstraintWidget, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, int paramInt1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2, int paramInt2) {
    while (this.mMeasurer == null && getParent() != null)
      this.mMeasurer = ((ConstraintWidgetContainer)getParent()).getMeasurer(); 
    this.mMeasure.horizontalBehavior = paramDimensionBehaviour1;
    this.mMeasure.verticalBehavior = paramDimensionBehaviour2;
    this.mMeasure.horizontalDimension = paramInt1;
    this.mMeasure.verticalDimension = paramInt2;
    this.mMeasurer.measure(paramConstraintWidget, this.mMeasure);
    paramConstraintWidget.setWidth(this.mMeasure.measuredWidth);
    paramConstraintWidget.setHeight(this.mMeasure.measuredHeight);
    paramConstraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
    paramConstraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
  }
  
  protected boolean measureChildren() {
    BasicMeasure.Measurer measurer;
    if (this.mParent != null) {
      measurer = ((ConstraintWidgetContainer)this.mParent).getMeasurer();
    } else {
      measurer = null;
    } 
    if (measurer == null)
      return false; 
    byte b = 0;
    while (true) {
      int i = this.mWidgetsCount;
      boolean bool = true;
      if (b < i) {
        ConstraintWidget constraintWidget = this.mWidgets[b];
        if (constraintWidget != null && !(constraintWidget instanceof Guideline)) {
          ConstraintWidget.DimensionBehaviour dimensionBehaviour1 = constraintWidget.getDimensionBehaviour(0);
          ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.getDimensionBehaviour(1);
          if (dimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultWidth == 1 || dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultHeight == 1)
            bool = false; 
          if (!bool) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviour1;
            if (dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
              dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT; 
            dimensionBehaviour1 = dimensionBehaviour2;
            if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
              dimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT; 
            this.mMeasure.horizontalBehavior = dimensionBehaviour;
            this.mMeasure.verticalBehavior = dimensionBehaviour1;
            this.mMeasure.horizontalDimension = constraintWidget.getWidth();
            this.mMeasure.verticalDimension = constraintWidget.getHeight();
            measurer.measure(constraintWidget, this.mMeasure);
            constraintWidget.setWidth(this.mMeasure.measuredWidth);
            constraintWidget.setHeight(this.mMeasure.measuredHeight);
            constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
          } 
        } 
        b++;
        continue;
      } 
      return true;
    } 
  }
  
  public boolean needSolverPass() {
    return this.mNeedsCallFromSolver;
  }
  
  protected void needsCallbackFromSolver(boolean paramBoolean) {
    this.mNeedsCallFromSolver = paramBoolean;
  }
  
  public void setMeasure(int paramInt1, int paramInt2) {
    this.mMeasuredWidth = paramInt1;
    this.mMeasuredHeight = paramInt2;
  }
  
  public void setPadding(int paramInt) {
    this.mPaddingLeft = paramInt;
    this.mPaddingTop = paramInt;
    this.mPaddingRight = paramInt;
    this.mPaddingBottom = paramInt;
    this.mPaddingStart = paramInt;
    this.mPaddingEnd = paramInt;
  }
  
  public void setPaddingBottom(int paramInt) {
    this.mPaddingBottom = paramInt;
  }
  
  public void setPaddingEnd(int paramInt) {
    this.mPaddingEnd = paramInt;
  }
  
  public void setPaddingLeft(int paramInt) {
    this.mPaddingLeft = paramInt;
    this.mResolvedPaddingLeft = paramInt;
  }
  
  public void setPaddingRight(int paramInt) {
    this.mPaddingRight = paramInt;
    this.mResolvedPaddingRight = paramInt;
  }
  
  public void setPaddingStart(int paramInt) {
    this.mPaddingStart = paramInt;
    this.mResolvedPaddingLeft = paramInt;
    this.mResolvedPaddingRight = paramInt;
  }
  
  public void setPaddingTop(int paramInt) {
    this.mPaddingTop = paramInt;
  }
  
  public void updateConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer) {
    captureWidgets();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\VirtualLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */