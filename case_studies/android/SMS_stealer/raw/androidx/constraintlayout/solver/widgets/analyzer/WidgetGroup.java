package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.Chain;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WidgetGroup {
  private static final boolean DEBUG = false;
  
  static int count;
  
  boolean authoritative = false;
  
  int id = -1;
  
  private int moveTo = -1;
  
  int orientation = 0;
  
  ArrayList<MeasureResult> results = null;
  
  ArrayList<ConstraintWidget> widgets = new ArrayList<ConstraintWidget>();
  
  public WidgetGroup(int paramInt) {
    int i = count;
    count = i + 1;
    this.id = i;
    this.orientation = paramInt;
  }
  
  private boolean contains(ConstraintWidget paramConstraintWidget) {
    return this.widgets.contains(paramConstraintWidget);
  }
  
  private String getOrientationString() {
    int i = this.orientation;
    return (i == 0) ? "Horizontal" : ((i == 1) ? "Vertical" : ((i == 2) ? "Both" : "Unknown"));
  }
  
  private int measureWrap(int paramInt, ConstraintWidget paramConstraintWidget) {
    ConstraintWidget.DimensionBehaviour dimensionBehaviour = paramConstraintWidget.getDimensionBehaviour(paramInt);
    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || dimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
      if (paramInt == 0) {
        paramInt = paramConstraintWidget.getWidth();
      } else {
        paramInt = paramConstraintWidget.getHeight();
      } 
      return paramInt;
    } 
    return -1;
  }
  
  private int solverMeasure(LinearSystem paramLinearSystem, ArrayList<ConstraintWidget> paramArrayList, int paramInt) {
    byte b = 0;
    ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer)((ConstraintWidget)paramArrayList.get(0)).getParent();
    paramLinearSystem.reset();
    constraintWidgetContainer.addToSolver(paramLinearSystem, false);
    int i;
    for (i = 0; i < paramArrayList.size(); i++)
      ((ConstraintWidget)paramArrayList.get(i)).addToSolver(paramLinearSystem, false); 
    if (paramInt == 0 && constraintWidgetContainer.mHorizontalChainsSize > 0)
      Chain.applyChainConstraints(constraintWidgetContainer, paramLinearSystem, paramArrayList, 0); 
    if (paramInt == 1 && constraintWidgetContainer.mVerticalChainsSize > 0)
      Chain.applyChainConstraints(constraintWidgetContainer, paramLinearSystem, paramArrayList, 1); 
    try {
      paramLinearSystem.minimize();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    this.results = new ArrayList<MeasureResult>();
    for (i = b; i < paramArrayList.size(); i++) {
      MeasureResult measureResult = new MeasureResult(paramArrayList.get(i), paramLinearSystem, paramInt);
      this.results.add(measureResult);
    } 
    if (paramInt == 0) {
      paramInt = paramLinearSystem.getObjectVariableValue(constraintWidgetContainer.mLeft);
      i = paramLinearSystem.getObjectVariableValue(constraintWidgetContainer.mRight);
      paramLinearSystem.reset();
      return i - paramInt;
    } 
    paramInt = paramLinearSystem.getObjectVariableValue(constraintWidgetContainer.mTop);
    i = paramLinearSystem.getObjectVariableValue(constraintWidgetContainer.mBottom);
    paramLinearSystem.reset();
    return i - paramInt;
  }
  
  public boolean add(ConstraintWidget paramConstraintWidget) {
    if (this.widgets.contains(paramConstraintWidget))
      return false; 
    this.widgets.add(paramConstraintWidget);
    return true;
  }
  
  public void apply() {
    if (this.results == null)
      return; 
    if (!this.authoritative)
      return; 
    for (byte b = 0; b < this.results.size(); b++)
      ((MeasureResult)this.results.get(b)).apply(); 
  }
  
  public void cleanup(ArrayList<WidgetGroup> paramArrayList) {
    int i = this.widgets.size();
    if (this.moveTo != -1 && i > 0)
      for (byte b = 0; b < paramArrayList.size(); b++) {
        WidgetGroup widgetGroup = paramArrayList.get(b);
        if (this.moveTo == widgetGroup.id)
          moveTo(this.orientation, widgetGroup); 
      }  
    if (i == 0)
      paramArrayList.remove(this); 
  }
  
  public void clear() {
    this.widgets.clear();
  }
  
  public int getId() {
    return this.id;
  }
  
  public int getOrientation() {
    return this.orientation;
  }
  
  public boolean intersectWith(WidgetGroup paramWidgetGroup) {
    for (byte b = 0; b < this.widgets.size(); b++) {
      if (paramWidgetGroup.contains(this.widgets.get(b)))
        return true; 
    } 
    return false;
  }
  
  public boolean isAuthoritative() {
    return this.authoritative;
  }
  
  public int measureWrap(LinearSystem paramLinearSystem, int paramInt) {
    return (this.widgets.size() == 0) ? 0 : solverMeasure(paramLinearSystem, this.widgets, paramInt);
  }
  
  public void moveTo(int paramInt, WidgetGroup paramWidgetGroup) {
    for (ConstraintWidget constraintWidget : this.widgets) {
      paramWidgetGroup.add(constraintWidget);
      if (paramInt == 0) {
        constraintWidget.horizontalGroup = paramWidgetGroup.getId();
        continue;
      } 
      constraintWidget.verticalGroup = paramWidgetGroup.getId();
    } 
    this.moveTo = paramWidgetGroup.id;
  }
  
  public void setAuthoritative(boolean paramBoolean) {
    this.authoritative = paramBoolean;
  }
  
  public void setOrientation(int paramInt) {
    this.orientation = paramInt;
  }
  
  public int size() {
    return this.widgets.size();
  }
  
  public String toString() {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(getOrientationString());
    stringBuilder1.append(" [");
    stringBuilder1.append(this.id);
    stringBuilder1.append("] <");
    String str = stringBuilder1.toString();
    for (ConstraintWidget constraintWidget : this.widgets) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(" ");
      stringBuilder.append(constraintWidget.getDebugName());
      str = stringBuilder.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str);
    stringBuilder2.append(" >");
    return stringBuilder2.toString();
  }
  
  class MeasureResult {
    int baseline;
    
    int bottom;
    
    int left;
    
    int orientation;
    
    int right;
    
    final WidgetGroup this$0;
    
    int top;
    
    WeakReference<ConstraintWidget> widgetRef;
    
    public MeasureResult(ConstraintWidget param1ConstraintWidget, LinearSystem param1LinearSystem, int param1Int) {
      this.widgetRef = new WeakReference<ConstraintWidget>(param1ConstraintWidget);
      this.left = param1LinearSystem.getObjectVariableValue(param1ConstraintWidget.mLeft);
      this.top = param1LinearSystem.getObjectVariableValue(param1ConstraintWidget.mTop);
      this.right = param1LinearSystem.getObjectVariableValue(param1ConstraintWidget.mRight);
      this.bottom = param1LinearSystem.getObjectVariableValue(param1ConstraintWidget.mBottom);
      this.baseline = param1LinearSystem.getObjectVariableValue(param1ConstraintWidget.mBaseline);
      this.orientation = param1Int;
    }
    
    public void apply() {
      ConstraintWidget constraintWidget = this.widgetRef.get();
      if (constraintWidget != null)
        constraintWidget.setFinalFrame(this.left, this.top, this.right, this.bottom, this.baseline, this.orientation); 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\WidgetGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */