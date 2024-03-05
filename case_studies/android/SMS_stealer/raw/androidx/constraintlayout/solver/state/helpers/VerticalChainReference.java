package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

public class VerticalChainReference extends ChainReference {
  private Object mBottomToBottom;
  
  private Object mBottomToTop;
  
  private Object mTopToBottom;
  
  private Object mTopToTop;
  
  public VerticalChainReference(State paramState) {
    super(paramState, State.Helper.VERTICAL_CHAIN);
  }
  
  public void apply() {
    for (Object object1 : this.mReferences)
      this.mState.constraints(object1).clearVertical(); 
    Iterator<Object> iterator = this.mReferences.iterator();
    ConstraintReference constraintReference = null;
    Object object;
    for (object = null; iterator.hasNext(); object = object1) {
      ConstraintReference constraintReference1 = (ConstraintReference)iterator.next();
      constraintReference1 = this.mState.constraints(constraintReference1);
      Object object1 = object;
      if (object == null) {
        object = this.mTopToTop;
        if (object != null) {
          constraintReference1.topToTop(object);
        } else {
          object = this.mTopToBottom;
          if (object != null) {
            constraintReference1.topToBottom(object);
          } else {
            constraintReference1.topToTop(State.PARENT);
          } 
        } 
        object1 = constraintReference1;
      } 
      if (constraintReference != null) {
        constraintReference.bottomToTop(constraintReference1.getKey());
        constraintReference1.topToBottom(constraintReference.getKey());
      } 
      constraintReference = constraintReference1;
    } 
    if (constraintReference != null) {
      Object object1 = this.mBottomToTop;
      if (object1 != null) {
        constraintReference.bottomToTop(object1);
      } else {
        object1 = this.mBottomToBottom;
        if (object1 != null) {
          constraintReference.bottomToBottom(object1);
        } else {
          constraintReference.bottomToBottom(State.PARENT);
        } 
      } 
    } 
    if (object != null && this.mBias != 0.5F)
      object.verticalBias(this.mBias); 
    int i = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Chain[this.mStyle.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i == 3)
          object.setVerticalChainStyle(2); 
      } else {
        object.setVerticalChainStyle(1);
      } 
    } else {
      object.setVerticalChainStyle(0);
    } 
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


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\helpers\VerticalChainReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */