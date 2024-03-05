package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

public class HorizontalChainReference extends ChainReference {
  private Object mEndToEnd;
  
  private Object mEndToStart;
  
  private Object mStartToEnd;
  
  private Object mStartToStart;
  
  public HorizontalChainReference(State paramState) {
    super(paramState, State.Helper.HORIZONTAL_CHAIN);
  }
  
  public void apply() {
    for (Object object1 : this.mReferences)
      this.mState.constraints(object1).clearHorizontal(); 
    Iterator<Object> iterator = this.mReferences.iterator();
    ConstraintReference constraintReference = null;
    Object object;
    for (object = null; iterator.hasNext(); object = object1) {
      ConstraintReference constraintReference1 = (ConstraintReference)iterator.next();
      constraintReference1 = this.mState.constraints(constraintReference1);
      Object object1 = object;
      if (object == null) {
        object = this.mStartToStart;
        if (object != null) {
          constraintReference1.startToStart(object);
        } else {
          object = this.mStartToEnd;
          if (object != null) {
            constraintReference1.startToEnd(object);
          } else {
            constraintReference1.startToStart(State.PARENT);
          } 
        } 
        object1 = constraintReference1;
      } 
      if (constraintReference != null) {
        constraintReference.endToStart(constraintReference1.getKey());
        constraintReference1.startToEnd(constraintReference.getKey());
      } 
      constraintReference = constraintReference1;
    } 
    if (constraintReference != null) {
      Object object1 = this.mEndToStart;
      if (object1 != null) {
        constraintReference.endToStart(object1);
      } else {
        object1 = this.mEndToEnd;
        if (object1 != null) {
          constraintReference.endToEnd(object1);
        } else {
          constraintReference.endToEnd(State.PARENT);
        } 
      } 
    } 
    if (object != null && this.mBias != 0.5F)
      object.horizontalBias(this.mBias); 
    int i = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Chain[this.mStyle.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i == 3)
          object.setHorizontalChainStyle(2); 
      } else {
        object.setHorizontalChainStyle(1);
      } 
    } else {
      object.setHorizontalChainStyle(0);
    } 
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


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\helpers\HorizontalChainReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */