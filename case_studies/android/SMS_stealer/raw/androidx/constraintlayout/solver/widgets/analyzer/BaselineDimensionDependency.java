package androidx.constraintlayout.solver.widgets.analyzer;

class BaselineDimensionDependency extends DimensionDependency {
  public BaselineDimensionDependency(WidgetRun paramWidgetRun) {
    super(paramWidgetRun);
  }
  
  public void update(DependencyNode paramDependencyNode) {
    ((VerticalWidgetRun)this.run).baseline.margin = this.run.widget.getBaselineDistance();
    this.resolved = true;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\BaselineDimensionDependency.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */