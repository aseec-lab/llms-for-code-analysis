package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public class Dimension {
  public static final Object FIXED_DIMENSION = new Object();
  
  public static final Object PARENT_DIMENSION;
  
  public static final Object PERCENT_DIMENSION;
  
  public static final Object SPREAD_DIMENSION;
  
  public static final Object WRAP_DIMENSION = new Object();
  
  private final int WRAP_CONTENT = -2;
  
  Object mInitialValue = WRAP_DIMENSION;
  
  boolean mIsSuggested = false;
  
  int mMax = Integer.MAX_VALUE;
  
  int mMin = 0;
  
  float mPercent = 1.0F;
  
  float mRatio = 1.0F;
  
  int mValue = 0;
  
  static {
    SPREAD_DIMENSION = new Object();
    PARENT_DIMENSION = new Object();
    PERCENT_DIMENSION = new Object();
  }
  
  private Dimension() {}
  
  private Dimension(Object paramObject) {
    this.mInitialValue = paramObject;
  }
  
  public static Dimension Fixed(int paramInt) {
    Dimension dimension = new Dimension(FIXED_DIMENSION);
    dimension.fixed(paramInt);
    return dimension;
  }
  
  public static Dimension Fixed(Object paramObject) {
    Dimension dimension = new Dimension(FIXED_DIMENSION);
    dimension.fixed(paramObject);
    return dimension;
  }
  
  public static Dimension Parent() {
    return new Dimension(PARENT_DIMENSION);
  }
  
  public static Dimension Percent(Object paramObject, float paramFloat) {
    Dimension dimension = new Dimension(PERCENT_DIMENSION);
    dimension.percent(paramObject, paramFloat);
    return dimension;
  }
  
  public static Dimension Spread() {
    return new Dimension(SPREAD_DIMENSION);
  }
  
  public static Dimension Suggested(int paramInt) {
    Dimension dimension = new Dimension();
    dimension.suggested(paramInt);
    return dimension;
  }
  
  public static Dimension Suggested(Object paramObject) {
    Dimension dimension = new Dimension();
    dimension.suggested(paramObject);
    return dimension;
  }
  
  public static Dimension Wrap() {
    return new Dimension(WRAP_DIMENSION);
  }
  
  public void apply(State paramState, ConstraintWidget paramConstraintWidget, int paramInt) {
    byte b = 2;
    if (paramInt == 0) {
      if (this.mIsSuggested) {
        paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
        Object object = this.mInitialValue;
        if (object == WRAP_DIMENSION) {
          paramInt = 1;
        } else if (object == PERCENT_DIMENSION) {
          paramInt = b;
        } else {
          paramInt = 0;
        } 
        paramConstraintWidget.setHorizontalMatchStyle(paramInt, this.mMin, this.mMax, this.mPercent);
      } else {
        paramInt = this.mMin;
        if (paramInt > 0)
          paramConstraintWidget.setMinWidth(paramInt); 
        paramInt = this.mMax;
        if (paramInt < Integer.MAX_VALUE)
          paramConstraintWidget.setMaxWidth(paramInt); 
        Object object = this.mInitialValue;
        if (object == WRAP_DIMENSION) {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        } else if (object == PARENT_DIMENSION) {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
        } else if (object == null) {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
          paramConstraintWidget.setWidth(this.mValue);
        } 
      } 
    } else if (this.mIsSuggested) {
      paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
      Object object = this.mInitialValue;
      if (object == WRAP_DIMENSION) {
        paramInt = 1;
      } else if (object == PERCENT_DIMENSION) {
        paramInt = b;
      } else {
        paramInt = 0;
      } 
      paramConstraintWidget.setVerticalMatchStyle(paramInt, this.mMin, this.mMax, this.mPercent);
    } else {
      paramInt = this.mMin;
      if (paramInt > 0)
        paramConstraintWidget.setMinHeight(paramInt); 
      paramInt = this.mMax;
      if (paramInt < Integer.MAX_VALUE)
        paramConstraintWidget.setMaxHeight(paramInt); 
      Object object = this.mInitialValue;
      if (object == WRAP_DIMENSION) {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
      } else if (object == PARENT_DIMENSION) {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
      } else if (object == null) {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidget.setHeight(this.mValue);
      } 
    } 
  }
  
  public Dimension fixed(int paramInt) {
    this.mInitialValue = null;
    this.mValue = paramInt;
    return this;
  }
  
  public Dimension fixed(Object paramObject) {
    this.mInitialValue = paramObject;
    if (paramObject instanceof Integer) {
      this.mValue = ((Integer)paramObject).intValue();
      this.mInitialValue = null;
    } 
    return this;
  }
  
  float getRatio() {
    return this.mRatio;
  }
  
  int getValue() {
    return this.mValue;
  }
  
  public Dimension max(int paramInt) {
    if (this.mMax >= 0)
      this.mMax = paramInt; 
    return this;
  }
  
  public Dimension max(Object paramObject) {
    Object object = WRAP_DIMENSION;
    if (paramObject == object && this.mIsSuggested) {
      this.mInitialValue = object;
      this.mMax = Integer.MAX_VALUE;
    } 
    return this;
  }
  
  public Dimension min(int paramInt) {
    if (paramInt >= 0)
      this.mMin = paramInt; 
    return this;
  }
  
  public Dimension min(Object paramObject) {
    if (paramObject == WRAP_DIMENSION)
      this.mMin = -2; 
    return this;
  }
  
  public Dimension percent(Object paramObject, float paramFloat) {
    this.mPercent = paramFloat;
    return this;
  }
  
  public Dimension ratio(float paramFloat) {
    return this;
  }
  
  void setRatio(float paramFloat) {
    this.mRatio = paramFloat;
  }
  
  void setValue(int paramInt) {
    this.mIsSuggested = false;
    this.mInitialValue = null;
    this.mValue = paramInt;
  }
  
  public Dimension suggested(int paramInt) {
    this.mIsSuggested = true;
    return this;
  }
  
  public Dimension suggested(Object paramObject) {
    this.mInitialValue = paramObject;
    this.mIsSuggested = true;
    return this;
  }
  
  public enum Type {
    FIXED, MATCH_CONSTRAINT, MATCH_PARENT, WRAP;
    
    private static final Type[] $VALUES;
    
    static {
      Type type = new Type("MATCH_CONSTRAINT", 3);
      MATCH_CONSTRAINT = type;
      $VALUES = new Type[] { FIXED, WRAP, MATCH_PARENT, type };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\Dimension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */