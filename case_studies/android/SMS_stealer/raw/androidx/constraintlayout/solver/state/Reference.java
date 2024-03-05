package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public interface Reference {
  void apply();
  
  ConstraintWidget getConstraintWidget();
  
  Object getKey();
  
  void setConstraintWidget(ConstraintWidget paramConstraintWidget);
  
  void setKey(Object paramObject);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\Reference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */