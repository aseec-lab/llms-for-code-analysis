package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.HelperWidget;

public class BarrierReference extends HelperReference {
  private Barrier mBarrierWidget;
  
  private State.Direction mDirection;
  
  private int mMargin;
  
  public BarrierReference(State paramState) {
    super(paramState, State.Helper.BARRIER);
  }
  
  public void apply() {
    getHelperWidget();
    int i = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Direction[this.mDirection.ordinal()];
    byte b2 = 0;
    byte b1 = b2;
    switch (i) {
      default:
        b1 = b2;
        break;
      case 6:
        b1 = 3;
        break;
      case 5:
        b1 = 2;
        break;
      case 3:
      case 4:
        b1 = 1;
        break;
      case 1:
      case 2:
        break;
    } 
    this.mBarrierWidget.setBarrierType(b1);
    this.mBarrierWidget.setMargin(this.mMargin);
  }
  
  public HelperWidget getHelperWidget() {
    if (this.mBarrierWidget == null)
      this.mBarrierWidget = new Barrier(); 
    return (HelperWidget)this.mBarrierWidget;
  }
  
  public void margin(int paramInt) {
    this.mMargin = paramInt;
  }
  
  public void margin(Object paramObject) {
    margin(this.mState.convertDimension(paramObject));
  }
  
  public void setBarrierDirection(State.Direction paramDirection) {
    this.mDirection = paramDirection;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\helpers\BarrierReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */