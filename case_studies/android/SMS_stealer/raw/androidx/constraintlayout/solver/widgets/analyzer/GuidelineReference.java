package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;

class GuidelineReference extends WidgetRun {
  public GuidelineReference(ConstraintWidget paramConstraintWidget) {
    super(paramConstraintWidget);
    paramConstraintWidget.horizontalRun.clear();
    paramConstraintWidget.verticalRun.clear();
    this.orientation = ((Guideline)paramConstraintWidget).getOrientation();
  }
  
  private void addDependency(DependencyNode paramDependencyNode) {
    this.start.dependencies.add(paramDependencyNode);
    paramDependencyNode.targets.add(this.start);
  }
  
  void apply() {
    Guideline guideline = (Guideline)this.widget;
    int i = guideline.getRelativeBegin();
    int j = guideline.getRelativeEnd();
    guideline.getRelativePercent();
    if (guideline.getOrientation() == 1) {
      if (i != -1) {
        this.start.targets.add(this.widget.mParent.horizontalRun.start);
        this.widget.mParent.horizontalRun.start.dependencies.add(this.start);
        this.start.margin = i;
      } else if (j != -1) {
        this.start.targets.add(this.widget.mParent.horizontalRun.end);
        this.widget.mParent.horizontalRun.end.dependencies.add(this.start);
        this.start.margin = -j;
      } else {
        this.start.delegateToWidgetRun = true;
        this.start.targets.add(this.widget.mParent.horizontalRun.end);
        this.widget.mParent.horizontalRun.end.dependencies.add(this.start);
      } 
      addDependency(this.widget.horizontalRun.start);
      addDependency(this.widget.horizontalRun.end);
    } else {
      if (i != -1) {
        this.start.targets.add(this.widget.mParent.verticalRun.start);
        this.widget.mParent.verticalRun.start.dependencies.add(this.start);
        this.start.margin = i;
      } else if (j != -1) {
        this.start.targets.add(this.widget.mParent.verticalRun.end);
        this.widget.mParent.verticalRun.end.dependencies.add(this.start);
        this.start.margin = -j;
      } else {
        this.start.delegateToWidgetRun = true;
        this.start.targets.add(this.widget.mParent.verticalRun.end);
        this.widget.mParent.verticalRun.end.dependencies.add(this.start);
      } 
      addDependency(this.widget.verticalRun.start);
      addDependency(this.widget.verticalRun.end);
    } 
  }
  
  public void applyToWidget() {
    if (((Guideline)this.widget).getOrientation() == 1) {
      this.widget.setX(this.start.value);
    } else {
      this.widget.setY(this.start.value);
    } 
  }
  
  void clear() {
    this.start.clear();
  }
  
  void reset() {
    this.start.resolved = false;
    this.end.resolved = false;
  }
  
  boolean supportsWrapComputation() {
    return false;
  }
  
  public void update(Dependency paramDependency) {
    if (!this.start.readyToSolve)
      return; 
    if (this.start.resolved)
      return; 
    DependencyNode dependencyNode = this.start.targets.get(0);
    Guideline guideline = (Guideline)this.widget;
    int i = (int)(dependencyNode.value * guideline.getRelativePercent() + 0.5F);
    this.start.resolve(i);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\GuidelineReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */