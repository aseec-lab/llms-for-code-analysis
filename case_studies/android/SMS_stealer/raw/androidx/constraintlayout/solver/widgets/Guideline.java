package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.HashMap;

public class Guideline extends ConstraintWidget {
  public static final int HORIZONTAL = 0;
  
  public static final int RELATIVE_BEGIN = 1;
  
  public static final int RELATIVE_END = 2;
  
  public static final int RELATIVE_PERCENT = 0;
  
  public static final int RELATIVE_UNKNWON = -1;
  
  public static final int VERTICAL = 1;
  
  private ConstraintAnchor mAnchor = this.mTop;
  
  private int mMinimumPosition;
  
  private int mOrientation;
  
  protected int mRelativeBegin = -1;
  
  protected int mRelativeEnd = -1;
  
  protected float mRelativePercent = -1.0F;
  
  private boolean resolved;
  
  public Guideline() {
    byte b = 0;
    this.mOrientation = 0;
    this.mMinimumPosition = 0;
    this.mAnchors.clear();
    this.mAnchors.add(this.mAnchor);
    int i = this.mListAnchors.length;
    while (b < i) {
      this.mListAnchors[b] = this.mAnchor;
      b++;
    } 
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean) {
    boolean bool1;
    SolverVariable solverVariable;
    ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer)getParent();
    if (constraintWidgetContainer == null)
      return; 
    ConstraintAnchor constraintAnchor1 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.LEFT);
    ConstraintAnchor constraintAnchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.RIGHT);
    ConstraintWidget constraintWidget = this.mParent;
    boolean bool2 = true;
    if (constraintWidget != null && this.mParent.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (this.mOrientation == 0) {
      constraintAnchor1 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.TOP);
      constraintAnchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BOTTOM);
      if (this.mParent != null && this.mParent.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        bool1 = bool2;
      } else {
        bool1 = false;
      } 
    } 
    if (this.resolved && this.mAnchor.hasFinalValue()) {
      SolverVariable solverVariable1 = paramLinearSystem.createObjectVariable(this.mAnchor);
      paramLinearSystem.addEquality(solverVariable1, this.mAnchor.getFinalValue());
      if (this.mRelativeBegin != -1) {
        if (bool1)
          paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(constraintAnchor2), solverVariable1, 0, 5); 
      } else if (this.mRelativeEnd != -1 && bool1) {
        solverVariable = paramLinearSystem.createObjectVariable(constraintAnchor2);
        paramLinearSystem.addGreaterThan(solverVariable1, paramLinearSystem.createObjectVariable(constraintAnchor1), 0, 5);
        paramLinearSystem.addGreaterThan(solverVariable, solverVariable1, 0, 5);
      } 
      this.resolved = false;
      return;
    } 
    if (this.mRelativeBegin != -1) {
      SolverVariable solverVariable1 = paramLinearSystem.createObjectVariable(this.mAnchor);
      paramLinearSystem.addEquality(solverVariable1, paramLinearSystem.createObjectVariable(constraintAnchor1), this.mRelativeBegin, 8);
      if (bool1)
        paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(solverVariable), solverVariable1, 0, 5); 
    } else if (this.mRelativeEnd != -1) {
      SolverVariable solverVariable1 = paramLinearSystem.createObjectVariable(this.mAnchor);
      solverVariable = paramLinearSystem.createObjectVariable(solverVariable);
      paramLinearSystem.addEquality(solverVariable1, solverVariable, -this.mRelativeEnd, 8);
      if (bool1) {
        paramLinearSystem.addGreaterThan(solverVariable1, paramLinearSystem.createObjectVariable(constraintAnchor1), 0, 5);
        paramLinearSystem.addGreaterThan(solverVariable, solverVariable1, 0, 5);
      } 
    } else if (this.mRelativePercent != -1.0F) {
      paramLinearSystem.addConstraint(LinearSystem.createRowDimensionPercent(paramLinearSystem, paramLinearSystem.createObjectVariable(this.mAnchor), paramLinearSystem.createObjectVariable(solverVariable), this.mRelativePercent));
    } 
  }
  
  public boolean allowedInBarrier() {
    return true;
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap) {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = paramConstraintWidget;
    this.mRelativePercent = ((Guideline)paramConstraintWidget).mRelativePercent;
    this.mRelativeBegin = ((Guideline)paramConstraintWidget).mRelativeBegin;
    this.mRelativeEnd = ((Guideline)paramConstraintWidget).mRelativeEnd;
    setOrientation(((Guideline)paramConstraintWidget).mOrientation);
  }
  
  public void cyclePosition() {
    if (this.mRelativeBegin != -1) {
      inferRelativePercentPosition();
    } else if (this.mRelativePercent != -1.0F) {
      inferRelativeEndPosition();
    } else if (this.mRelativeEnd != -1) {
      inferRelativeBeginPosition();
    } 
  }
  
  public ConstraintAnchor getAnchor() {
    return this.mAnchor;
  }
  
  public ConstraintAnchor getAnchor(ConstraintAnchor.Type paramType) {
    switch (paramType) {
      default:
        throw new AssertionError(paramType.name());
      case null:
      case null:
      case null:
      case null:
      case null:
        return null;
      case null:
      case null:
        if (this.mOrientation == 0)
          return this.mAnchor; 
      case null:
      case null:
        break;
    } 
    if (this.mOrientation == 1)
      return this.mAnchor; 
  }
  
  public int getOrientation() {
    return this.mOrientation;
  }
  
  public int getRelativeBegin() {
    return this.mRelativeBegin;
  }
  
  public int getRelativeBehaviour() {
    return (this.mRelativePercent != -1.0F) ? 0 : ((this.mRelativeBegin != -1) ? 1 : ((this.mRelativeEnd != -1) ? 2 : -1));
  }
  
  public int getRelativeEnd() {
    return this.mRelativeEnd;
  }
  
  public float getRelativePercent() {
    return this.mRelativePercent;
  }
  
  public String getType() {
    return "Guideline";
  }
  
  void inferRelativeBeginPosition() {
    int i = getX();
    if (this.mOrientation == 0)
      i = getY(); 
    setGuideBegin(i);
  }
  
  void inferRelativeEndPosition() {
    int i = getParent().getWidth() - getX();
    if (this.mOrientation == 0)
      i = getParent().getHeight() - getY(); 
    setGuideEnd(i);
  }
  
  void inferRelativePercentPosition() {
    float f = getX() / getParent().getWidth();
    if (this.mOrientation == 0)
      f = getY() / getParent().getHeight(); 
    setGuidePercent(f);
  }
  
  public boolean isPercent() {
    boolean bool;
    if (this.mRelativePercent != -1.0F && this.mRelativeBegin == -1 && this.mRelativeEnd == -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isResolvedHorizontally() {
    return this.resolved;
  }
  
  public boolean isResolvedVertically() {
    return this.resolved;
  }
  
  public void setFinalValue(int paramInt) {
    this.mAnchor.setFinalValue(paramInt);
    this.resolved = true;
  }
  
  public void setGuideBegin(int paramInt) {
    if (paramInt > -1) {
      this.mRelativePercent = -1.0F;
      this.mRelativeBegin = paramInt;
      this.mRelativeEnd = -1;
    } 
  }
  
  public void setGuideEnd(int paramInt) {
    if (paramInt > -1) {
      this.mRelativePercent = -1.0F;
      this.mRelativeBegin = -1;
      this.mRelativeEnd = paramInt;
    } 
  }
  
  public void setGuidePercent(float paramFloat) {
    if (paramFloat > -1.0F) {
      this.mRelativePercent = paramFloat;
      this.mRelativeBegin = -1;
      this.mRelativeEnd = -1;
    } 
  }
  
  public void setGuidePercent(int paramInt) {
    setGuidePercent(paramInt / 100.0F);
  }
  
  public void setMinimumPosition(int paramInt) {
    this.mMinimumPosition = paramInt;
  }
  
  public void setOrientation(int paramInt) {
    if (this.mOrientation == paramInt)
      return; 
    this.mOrientation = paramInt;
    this.mAnchors.clear();
    if (this.mOrientation == 1) {
      this.mAnchor = this.mLeft;
    } else {
      this.mAnchor = this.mTop;
    } 
    this.mAnchors.add(this.mAnchor);
    int i = this.mListAnchors.length;
    for (paramInt = 0; paramInt < i; paramInt++)
      this.mListAnchors[paramInt] = this.mAnchor; 
  }
  
  public void updateFromSolver(LinearSystem paramLinearSystem, boolean paramBoolean) {
    if (getParent() == null)
      return; 
    int i = paramLinearSystem.getObjectVariableValue(this.mAnchor);
    if (this.mOrientation == 1) {
      setX(i);
      setY(0);
      setHeight(getParent().getHeight());
      setWidth(0);
    } else {
      setX(0);
      setY(i);
      setWidth(getParent().getWidth());
      setHeight(0);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\Guideline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */