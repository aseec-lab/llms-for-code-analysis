package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;

public class AlignVerticallyReference extends HelperReference {
  private float mBias = 0.5F;
  
  private Object mBottomToBottom;
  
  private Object mBottomToTop;
  
  private Object mTopToBottom;
  
  private Object mTopToTop;
  
  public AlignVerticallyReference(State paramState) {
    super(paramState, State.Helper.ALIGN_VERTICALLY);
  }
  
  public void apply() {
    for (ConstraintReference constraintReference : this.mReferences) {
      constraintReference = this.mState.constraints(constraintReference);
      constraintReference.clearVertical();
      Object object = this.mTopToTop;
      if (object != null) {
        constraintReference.topToTop(object);
      } else {
        object = this.mTopToBottom;
        if (object != null) {
          constraintReference.topToBottom(object);
        } else {
          constraintReference.topToTop(State.PARENT);
        } 
      } 
      object = this.mBottomToTop;
      if (object != null) {
        constraintReference.bottomToTop(object);
      } else {
        object = this.mBottomToBottom;
        if (object != null) {
          constraintReference.bottomToBottom(object);
        } else {
          constraintReference.bottomToBottom(State.PARENT);
        } 
      } 
      float f = this.mBias;
      if (f != 0.5F)
        constraintReference.verticalBias(f); 
    } 
  }
  
  public void bias(float paramFloat) {
    this.mBias = paramFloat;
  }
  
  public void bottomToBottom(Object paramObject) {
    this.mBottomToBottom = paramObject;
  }
  
  public void bottomToTop(Object paramObject) {
    this.mBottomToTop = paramObject;
  }
  
  public void topToBottom(Object paramObject) {
    this.mTopToBottom = paramObject;
  }
  
  public void topToTop(Object paramObject) {
    this.mTopToTop = paramObject;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\helpers\AlignVerticallyReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */