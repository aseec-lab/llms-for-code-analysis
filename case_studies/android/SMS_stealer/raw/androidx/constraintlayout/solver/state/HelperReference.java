package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.ArrayList;

public class HelperReference {
  private HelperWidget mHelperWidget;
  
  protected ArrayList<Object> mReferences = new ArrayList();
  
  protected final State mState;
  
  final State.Helper mType;
  
  public HelperReference(State paramState, State.Helper paramHelper) {
    this.mState = paramState;
    this.mType = paramHelper;
  }
  
  public HelperReference add(Object... paramVarArgs) {
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++) {
      Object object = paramVarArgs[b];
      this.mReferences.add(object);
    } 
    return this;
  }
  
  public void apply() {}
  
  public HelperWidget getHelperWidget() {
    return this.mHelperWidget;
  }
  
  public State.Helper getType() {
    return this.mType;
  }
  
  public void setHelperWidget(HelperWidget paramHelperWidget) {
    this.mHelperWidget = paramHelperWidget;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\HelperReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */