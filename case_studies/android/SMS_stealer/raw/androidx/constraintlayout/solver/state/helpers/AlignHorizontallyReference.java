package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;

public class AlignHorizontallyReference extends HelperReference {
  private float mBias = 0.5F;
  
  private Object mEndToEnd;
  
  private Object mEndToStart;
  
  private Object mStartToEnd;
  
  private Object mStartToStart;
  
  public AlignHorizontallyReference(State paramState) {
    super(paramState, State.Helper.ALIGN_VERTICALLY);
  }
  
  public void apply() {
    for (ConstraintReference constraintReference : this.mReferences) {
      constraintReference = this.mState.constraints(constraintReference);
      constraintReference.clearHorizontal();
      Object object = this.mStartToStart;
      if (object != null) {
        constraintReference.startToStart(object);
      } else {
        object = this.mStartToEnd;
        if (object != null) {
          constraintReference.startToEnd(object);
        } else {
          constraintReference.startToStart(State.PARENT);
        } 
      } 
      object = this.mEndToStart;
      if (object != null) {
        constraintReference.endToStart(object);
      } else {
        object = this.mEndToEnd;
        if (object != null) {
          constraintReference.endToEnd(object);
        } else {
          constraintReference.endToEnd(State.PARENT);
        } 
      } 
      float f = this.mBias;
      if (f != 0.5F)
        constraintReference.horizontalBias(f); 
    } 
  }
  
  public void bias(float paramFloat) {
    this.mBias = paramFloat;
  }
  
  public void endToEnd(Object paramObject) {
    this.mEndToEnd = paramObject;
  }
  
  public void endToStart(Object paramObject) {
    this.mEndToStart = paramObject;
  }
  
  public void startToEnd(Object paramObject) {
    this.mStartToEnd = paramObject;
  }
  
  public void startToStart(Object paramObject) {
    this.mStartToStart = paramObject;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\helpers\AlignHorizontallyReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */