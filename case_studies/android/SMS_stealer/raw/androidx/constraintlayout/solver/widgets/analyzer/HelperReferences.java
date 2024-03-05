package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.Iterator;

class HelperReferences extends WidgetRun {
  public HelperReferences(ConstraintWidget paramConstraintWidget) {
    super(paramConstraintWidget);
  }
  
  private void addDependency(DependencyNode paramDependencyNode) {
    this.start.dependencies.add(paramDependencyNode);
    paramDependencyNode.targets.add(this.start);
  }
  
  void apply() {
    if (this.widget instanceof Barrier) {
      this.start.delegateToWidgetRun = true;
      Barrier barrier = (Barrier)this.widget;
      int i = barrier.getBarrierType();
      boolean bool = barrier.allowsGoneWidget();
      byte b1 = 0;
      boolean bool1 = false;
      boolean bool2 = false;
      byte b2 = 0;
      if (i != 0) {
        if (i != 1) {
          if (i != 2) {
            if (i == 3) {
              this.start.type = DependencyNode.Type.BOTTOM;
              for (b1 = b2; b1 < barrier.mWidgetsCount; b1++) {
                ConstraintWidget constraintWidget = barrier.mWidgets[b1];
                if (bool || constraintWidget.getVisibility() != 8) {
                  DependencyNode dependencyNode = constraintWidget.verticalRun.end;
                  dependencyNode.dependencies.add(this.start);
                  this.start.targets.add(dependencyNode);
                } 
              } 
              addDependency(this.widget.verticalRun.start);
              addDependency(this.widget.verticalRun.end);
            } 
          } else {
            this.start.type = DependencyNode.Type.TOP;
            while (b1 < barrier.mWidgetsCount) {
              ConstraintWidget constraintWidget = barrier.mWidgets[b1];
              if (bool || constraintWidget.getVisibility() != 8) {
                DependencyNode dependencyNode = constraintWidget.verticalRun.start;
                dependencyNode.dependencies.add(this.start);
                this.start.targets.add(dependencyNode);
              } 
              b1++;
            } 
            addDependency(this.widget.verticalRun.start);
            addDependency(this.widget.verticalRun.end);
          } 
        } else {
          this.start.type = DependencyNode.Type.RIGHT;
          for (b1 = bool1; b1 < barrier.mWidgetsCount; b1++) {
            ConstraintWidget constraintWidget = barrier.mWidgets[b1];
            if (bool || constraintWidget.getVisibility() != 8) {
              DependencyNode dependencyNode = constraintWidget.horizontalRun.end;
              dependencyNode.dependencies.add(this.start);
              this.start.targets.add(dependencyNode);
            } 
          } 
          addDependency(this.widget.horizontalRun.start);
          addDependency(this.widget.horizontalRun.end);
        } 
      } else {
        this.start.type = DependencyNode.Type.LEFT;
        for (b1 = bool2; b1 < barrier.mWidgetsCount; b1++) {
          ConstraintWidget constraintWidget = barrier.mWidgets[b1];
          if (bool || constraintWidget.getVisibility() != 8) {
            DependencyNode dependencyNode = constraintWidget.horizontalRun.start;
            dependencyNode.dependencies.add(this.start);
            this.start.targets.add(dependencyNode);
          } 
        } 
        addDependency(this.widget.horizontalRun.start);
        addDependency(this.widget.horizontalRun.end);
      } 
    } 
  }
  
  public void applyToWidget() {
    if (this.widget instanceof Barrier) {
      int i = ((Barrier)this.widget).getBarrierType();
      if (i == 0 || i == 1) {
        this.widget.setX(this.start.value);
        return;
      } 
      this.widget.setY(this.start.value);
    } 
  }
  
  void clear() {
    this.runGroup = null;
    this.start.clear();
  }
  
  void reset() {
    this.start.resolved = false;
  }
  
  boolean supportsWrapComputation() {
    return false;
  }
  
  public void update(Dependency paramDependency) {
    Object object1;
    Object object2;
    Barrier barrier = (Barrier)this.widget;
    int i = barrier.getBarrierType();
    Iterator<DependencyNode> iterator = this.start.targets.iterator();
    byte b = 0;
    byte b1 = -1;
    while (true) {
      while (true)
        break; 
      if (b < SYNTHETIC_LOCAL_VARIABLE_5) {
        object1 = SYNTHETIC_LOCAL_VARIABLE_5;
        object2 = SYNTHETIC_LOCAL_VARIABLE_3;
      } 
    } 
    if (i == 0 || i == 2) {
      this.start.resolve(object2 + barrier.getMargin());
      return;
    } 
    this.start.resolve(object1 + barrier.getMargin());
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\HelperReferences.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */