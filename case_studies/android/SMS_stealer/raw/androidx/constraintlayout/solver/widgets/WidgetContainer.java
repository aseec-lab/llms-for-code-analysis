package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import java.util.ArrayList;

public class WidgetContainer extends ConstraintWidget {
  public ArrayList<ConstraintWidget> mChildren = new ArrayList<ConstraintWidget>();
  
  public WidgetContainer() {}
  
  public WidgetContainer(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
  }
  
  public WidgetContainer(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void add(ConstraintWidget paramConstraintWidget) {
    this.mChildren.add(paramConstraintWidget);
    if (paramConstraintWidget.getParent() != null)
      ((WidgetContainer)paramConstraintWidget.getParent()).remove(paramConstraintWidget); 
    paramConstraintWidget.setParent(this);
  }
  
  public void add(ConstraintWidget... paramVarArgs) {
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++)
      add(paramVarArgs[b]); 
  }
  
  public ArrayList<ConstraintWidget> getChildren() {
    return this.mChildren;
  }
  
  public ConstraintWidgetContainer getRootConstraintContainer() {
    ConstraintWidgetContainer constraintWidgetContainer;
    ConstraintWidget constraintWidget = getParent();
    if (this instanceof ConstraintWidgetContainer) {
      constraintWidgetContainer = (ConstraintWidgetContainer)this;
    } else {
      constraintWidgetContainer = null;
    } 
    while (constraintWidget != null) {
      ConstraintWidget constraintWidget1 = constraintWidget.getParent();
      if (constraintWidget instanceof ConstraintWidgetContainer)
        constraintWidgetContainer = (ConstraintWidgetContainer)constraintWidget; 
      constraintWidget = constraintWidget1;
    } 
    return constraintWidgetContainer;
  }
  
  public void layout() {
    ArrayList<ConstraintWidget> arrayList = this.mChildren;
    if (arrayList == null)
      return; 
    int i = arrayList.size();
    for (byte b = 0; b < i; b++) {
      ConstraintWidget constraintWidget = this.mChildren.get(b);
      if (constraintWidget instanceof WidgetContainer)
        ((WidgetContainer)constraintWidget).layout(); 
    } 
  }
  
  public void remove(ConstraintWidget paramConstraintWidget) {
    this.mChildren.remove(paramConstraintWidget);
    paramConstraintWidget.reset();
  }
  
  public void removeAllChildren() {
    this.mChildren.clear();
  }
  
  public void reset() {
    this.mChildren.clear();
    super.reset();
  }
  
  public void resetSolverVariables(Cache paramCache) {
    super.resetSolverVariables(paramCache);
    int i = this.mChildren.size();
    for (byte b = 0; b < i; b++)
      ((ConstraintWidget)this.mChildren.get(b)).resetSolverVariables(paramCache); 
  }
  
  public void setOffset(int paramInt1, int paramInt2) {
    super.setOffset(paramInt1, paramInt2);
    paramInt2 = this.mChildren.size();
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++)
      ((ConstraintWidget)this.mChildren.get(paramInt1)).setOffset(getRootX(), getRootY()); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\WidgetContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */