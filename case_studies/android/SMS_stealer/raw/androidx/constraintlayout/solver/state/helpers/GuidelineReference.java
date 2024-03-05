package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.Reference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;

public class GuidelineReference implements Reference {
  private Object key;
  
  private int mEnd = -1;
  
  private Guideline mGuidelineWidget;
  
  private int mOrientation;
  
  private float mPercent = 0.0F;
  
  private int mStart = -1;
  
  final State mState;
  
  public GuidelineReference(State paramState) {
    this.mState = paramState;
  }
  
  public void apply() {
    this.mGuidelineWidget.setOrientation(this.mOrientation);
    int i = this.mStart;
    if (i != -1) {
      this.mGuidelineWidget.setGuideBegin(i);
    } else {
      i = this.mEnd;
      if (i != -1) {
        this.mGuidelineWidget.setGuideEnd(i);
      } else {
        this.mGuidelineWidget.setGuidePercent(this.mPercent);
      } 
    } 
  }
  
  public void end(Object paramObject) {
    this.mStart = -1;
    this.mEnd = this.mState.convertDimension(paramObject);
    this.mPercent = 0.0F;
  }
  
  public ConstraintWidget getConstraintWidget() {
    if (this.mGuidelineWidget == null)
      this.mGuidelineWidget = new Guideline(); 
    return (ConstraintWidget)this.mGuidelineWidget;
  }
  
  public Object getKey() {
    return this.key;
  }
  
  public int getOrientation() {
    return this.mOrientation;
  }
  
  public void percent(float paramFloat) {
    this.mStart = -1;
    this.mEnd = -1;
    this.mPercent = paramFloat;
  }
  
  public void setConstraintWidget(ConstraintWidget paramConstraintWidget) {
    if (paramConstraintWidget instanceof Guideline) {
      this.mGuidelineWidget = (Guideline)paramConstraintWidget;
    } else {
      this.mGuidelineWidget = null;
    } 
  }
  
  public void setKey(Object paramObject) {
    this.key = paramObject;
  }
  
  public void setOrientation(int paramInt) {
    this.mOrientation = paramInt;
  }
  
  public void start(Object paramObject) {
    this.mStart = this.mState.convertDimension(paramObject);
    this.mEnd = -1;
    this.mPercent = 0.0F;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\helpers\GuidelineReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */