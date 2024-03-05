package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.analyzer.Grouping;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HelperWidget extends ConstraintWidget implements Helper {
  public ConstraintWidget[] mWidgets = new ConstraintWidget[4];
  
  public int mWidgetsCount = 0;
  
  public void add(ConstraintWidget paramConstraintWidget) {
    if (paramConstraintWidget != this && paramConstraintWidget != null) {
      int i = this.mWidgetsCount;
      ConstraintWidget[] arrayOfConstraintWidget = this.mWidgets;
      if (i + 1 > arrayOfConstraintWidget.length)
        this.mWidgets = Arrays.<ConstraintWidget>copyOf(arrayOfConstraintWidget, arrayOfConstraintWidget.length * 2); 
      arrayOfConstraintWidget = this.mWidgets;
      i = this.mWidgetsCount;
      arrayOfConstraintWidget[i] = paramConstraintWidget;
      this.mWidgetsCount = i + 1;
    } 
  }
  
  public void addDependents(ArrayList<WidgetGroup> paramArrayList, int paramInt, WidgetGroup paramWidgetGroup) {
    byte b1;
    byte b3 = 0;
    byte b2 = 0;
    while (true) {
      b1 = b3;
      if (b2 < this.mWidgetsCount) {
        paramWidgetGroup.add(this.mWidgets[b2]);
        b2++;
        continue;
      } 
      break;
    } 
    while (b1 < this.mWidgetsCount) {
      Grouping.findDependents(this.mWidgets[b1], paramInt, paramArrayList, paramWidgetGroup);
      b1++;
    } 
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap) {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = paramConstraintWidget;
    byte b = 0;
    this.mWidgetsCount = 0;
    int i = ((HelperWidget)paramConstraintWidget).mWidgetsCount;
    while (b < i) {
      add(paramHashMap.get(((HelperWidget)paramConstraintWidget).mWidgets[b]));
      b++;
    } 
  }
  
  public int findGroupInDependents(int paramInt) {
    for (byte b = 0; b < this.mWidgetsCount; b++) {
      ConstraintWidget constraintWidget = this.mWidgets[b];
      if (paramInt == 0 && constraintWidget.horizontalGroup != -1)
        return constraintWidget.horizontalGroup; 
      if (paramInt == 1 && constraintWidget.verticalGroup != -1)
        return constraintWidget.verticalGroup; 
    } 
    return -1;
  }
  
  public void removeAllIds() {
    this.mWidgetsCount = 0;
    Arrays.fill((Object[])this.mWidgets, (Object)null);
  }
  
  public void updateConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer) {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\HelperWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */