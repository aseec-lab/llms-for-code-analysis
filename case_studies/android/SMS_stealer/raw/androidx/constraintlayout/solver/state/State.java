package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.state.helpers.AlignHorizontallyReference;
import androidx.constraintlayout.solver.state.helpers.AlignVerticallyReference;
import androidx.constraintlayout.solver.state.helpers.BarrierReference;
import androidx.constraintlayout.solver.state.helpers.GuidelineReference;
import androidx.constraintlayout.solver.state.helpers.HorizontalChainReference;
import androidx.constraintlayout.solver.state.helpers.VerticalChainReference;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.HashMap;
import java.util.Iterator;

public class State {
  static final int CONSTRAINT_RATIO = 2;
  
  static final int CONSTRAINT_SPREAD = 0;
  
  static final int CONSTRAINT_WRAP = 1;
  
  public static final Integer PARENT = Integer.valueOf(0);
  
  static final int UNKNOWN = -1;
  
  protected HashMap<Object, HelperReference> mHelperReferences = new HashMap<Object, HelperReference>();
  
  public final ConstraintReference mParent;
  
  protected HashMap<Object, Reference> mReferences = new HashMap<Object, Reference>();
  
  private int numHelpers;
  
  public State() {
    ConstraintReference constraintReference = new ConstraintReference(this);
    this.mParent = constraintReference;
    this.numHelpers = 0;
    this.mReferences.put(PARENT, constraintReference);
  }
  
  private String createHelperKey() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("__HELPER_KEY_");
    int i = this.numHelpers;
    this.numHelpers = i + 1;
    stringBuilder.append(i);
    stringBuilder.append("__");
    return stringBuilder.toString();
  }
  
  public void apply(ConstraintWidgetContainer paramConstraintWidgetContainer) {
    paramConstraintWidgetContainer.removeAllChildren();
    this.mParent.getWidth().apply(this, (ConstraintWidget)paramConstraintWidgetContainer, 0);
    this.mParent.getHeight().apply(this, (ConstraintWidget)paramConstraintWidgetContainer, 1);
    for (Object object : this.mHelperReferences.keySet()) {
      HelperWidget helperWidget = ((HelperReference)this.mHelperReferences.get(object)).getHelperWidget();
      if (helperWidget != null) {
        Reference reference2 = this.mReferences.get(object);
        Reference reference1 = reference2;
        if (reference2 == null)
          reference1 = constraints(object); 
        reference1.setConstraintWidget((ConstraintWidget)helperWidget);
      } 
    } 
    for (ConstraintWidget constraintWidget : this.mReferences.keySet()) {
      Reference reference = this.mReferences.get(constraintWidget);
      if (reference != this.mParent) {
        constraintWidget = reference.getConstraintWidget();
        constraintWidget.setParent(null);
        if (reference instanceof GuidelineReference)
          reference.apply(); 
        paramConstraintWidgetContainer.add(constraintWidget);
        continue;
      } 
      reference.setConstraintWidget((ConstraintWidget)paramConstraintWidgetContainer);
    } 
    for (Iterator<Object> iterator : (Iterable<Iterator<Object>>)this.mHelperReferences.keySet()) {
      HelperReference helperReference = this.mHelperReferences.get(iterator);
      if (helperReference.getHelperWidget() != null) {
        for (Reference reference : helperReference.mReferences) {
          reference = this.mReferences.get(reference);
          helperReference.getHelperWidget().add(reference.getConstraintWidget());
        } 
        helperReference.apply();
      } 
    } 
    for (Object object : this.mReferences.keySet())
      ((Reference)this.mReferences.get(object)).apply(); 
  }
  
  public BarrierReference barrier(Object paramObject, Direction paramDirection) {
    paramObject = helper(paramObject, Helper.BARRIER);
    paramObject.setBarrierDirection(paramDirection);
    return (BarrierReference)paramObject;
  }
  
  public AlignHorizontallyReference centerHorizontally(Object... paramVarArgs) {
    AlignHorizontallyReference alignHorizontallyReference = (AlignHorizontallyReference)helper(null, Helper.ALIGN_HORIZONTALLY);
    alignHorizontallyReference.add(paramVarArgs);
    return alignHorizontallyReference;
  }
  
  public AlignVerticallyReference centerVertically(Object... paramVarArgs) {
    AlignVerticallyReference alignVerticallyReference = (AlignVerticallyReference)helper(null, Helper.ALIGN_VERTICALLY);
    alignVerticallyReference.add(paramVarArgs);
    return alignVerticallyReference;
  }
  
  public ConstraintReference constraints(Object paramObject) {
    Reference reference2 = this.mReferences.get(paramObject);
    Reference reference1 = reference2;
    if (reference2 == null) {
      reference1 = createConstraintReference(paramObject);
      this.mReferences.put(paramObject, reference1);
      reference1.setKey(paramObject);
    } 
    return (reference1 instanceof ConstraintReference) ? (ConstraintReference)reference1 : null;
  }
  
  public int convertDimension(Object paramObject) {
    return (paramObject instanceof Float) ? ((Float)paramObject).intValue() : ((paramObject instanceof Integer) ? ((Integer)paramObject).intValue() : 0);
  }
  
  public ConstraintReference createConstraintReference(Object paramObject) {
    return new ConstraintReference(this);
  }
  
  public void directMapping() {
    for (Object object : this.mReferences.keySet())
      constraints(object).setView(object); 
  }
  
  public GuidelineReference guideline(Object paramObject, int paramInt) {
    GuidelineReference guidelineReference;
    Reference reference2 = this.mReferences.get(paramObject);
    Reference reference1 = reference2;
    if (reference2 == null) {
      guidelineReference = new GuidelineReference(this);
      guidelineReference.setOrientation(paramInt);
      guidelineReference.setKey(paramObject);
      this.mReferences.put(paramObject, guidelineReference);
    } 
    return guidelineReference;
  }
  
  public State height(Dimension paramDimension) {
    return setHeight(paramDimension);
  }
  
  public HelperReference helper(Object paramObject, Helper paramHelper) {
    Object object = paramObject;
    if (paramObject == null)
      object = createHelperKey(); 
    HelperReference helperReference = this.mHelperReferences.get(object);
    paramObject = helperReference;
    if (helperReference == null) {
      int i = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Helper[paramHelper.ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4) {
              if (i != 5) {
                paramObject = new HelperReference(this, paramHelper);
              } else {
                paramObject = new BarrierReference(this);
              } 
            } else {
              paramObject = new AlignVerticallyReference(this);
            } 
          } else {
            paramObject = new AlignHorizontallyReference(this);
          } 
        } else {
          paramObject = new VerticalChainReference(this);
        } 
      } else {
        paramObject = new HorizontalChainReference(this);
      } 
      this.mHelperReferences.put(object, paramObject);
    } 
    return (HelperReference)paramObject;
  }
  
  public HorizontalChainReference horizontalChain(Object... paramVarArgs) {
    HorizontalChainReference horizontalChainReference = (HorizontalChainReference)helper(null, Helper.HORIZONTAL_CHAIN);
    horizontalChainReference.add(paramVarArgs);
    return horizontalChainReference;
  }
  
  public GuidelineReference horizontalGuideline(Object paramObject) {
    return guideline(paramObject, 0);
  }
  
  public void map(Object paramObject1, Object paramObject2) {
    constraints(paramObject1).setView(paramObject2);
  }
  
  Reference reference(Object paramObject) {
    return this.mReferences.get(paramObject);
  }
  
  public void reset() {
    this.mHelperReferences.clear();
  }
  
  public State setHeight(Dimension paramDimension) {
    this.mParent.setHeight(paramDimension);
    return this;
  }
  
  public State setWidth(Dimension paramDimension) {
    this.mParent.setWidth(paramDimension);
    return this;
  }
  
  public VerticalChainReference verticalChain(Object... paramVarArgs) {
    VerticalChainReference verticalChainReference = (VerticalChainReference)helper(null, Helper.VERTICAL_CHAIN);
    verticalChainReference.add(paramVarArgs);
    return verticalChainReference;
  }
  
  public GuidelineReference verticalGuideline(Object paramObject) {
    return guideline(paramObject, 1);
  }
  
  public State width(Dimension paramDimension) {
    return setWidth(paramDimension);
  }
  
  public enum Chain {
    PACKED, SPREAD, SPREAD_INSIDE;
    
    private static final Chain[] $VALUES;
    
    static {
      Chain chain = new Chain("PACKED", 2);
      PACKED = chain;
      $VALUES = new Chain[] { SPREAD, SPREAD_INSIDE, chain };
    }
  }
  
  public enum Constraint {
    LEFT_TO_LEFT, BASELINE_TO_BASELINE, BOTTOM_TO_BOTTOM, BOTTOM_TO_TOP, CENTER_HORIZONTALLY, CENTER_VERTICALLY, END_TO_END, END_TO_START, LEFT_TO_RIGHT, RIGHT_TO_LEFT, RIGHT_TO_RIGHT, START_TO_END, START_TO_START, TOP_TO_BOTTOM, TOP_TO_TOP;
    
    private static final Constraint[] $VALUES;
    
    static {
      END_TO_START = new Constraint("END_TO_START", 6);
      END_TO_END = new Constraint("END_TO_END", 7);
      TOP_TO_TOP = new Constraint("TOP_TO_TOP", 8);
      TOP_TO_BOTTOM = new Constraint("TOP_TO_BOTTOM", 9);
      BOTTOM_TO_TOP = new Constraint("BOTTOM_TO_TOP", 10);
      BOTTOM_TO_BOTTOM = new Constraint("BOTTOM_TO_BOTTOM", 11);
      BASELINE_TO_BASELINE = new Constraint("BASELINE_TO_BASELINE", 12);
      CENTER_HORIZONTALLY = new Constraint("CENTER_HORIZONTALLY", 13);
      Constraint constraint = new Constraint("CENTER_VERTICALLY", 14);
      CENTER_VERTICALLY = constraint;
      $VALUES = new Constraint[] { 
          LEFT_TO_LEFT, LEFT_TO_RIGHT, RIGHT_TO_LEFT, RIGHT_TO_RIGHT, START_TO_START, START_TO_END, END_TO_START, END_TO_END, TOP_TO_TOP, TOP_TO_BOTTOM, 
          BOTTOM_TO_TOP, BOTTOM_TO_BOTTOM, BASELINE_TO_BASELINE, CENTER_HORIZONTALLY, constraint };
    }
  }
  
  public enum Direction {
    LEFT, RIGHT, START, TOP, BOTTOM, END;
    
    private static final Direction[] $VALUES;
    
    static {
      Direction direction = new Direction("BOTTOM", 5);
      BOTTOM = direction;
      $VALUES = new Direction[] { LEFT, RIGHT, START, END, TOP, direction };
    }
  }
  
  public enum Helper {
    HORIZONTAL_CHAIN, LAYER, VERTICAL_CHAIN, ALIGN_HORIZONTALLY, ALIGN_VERTICALLY, BARRIER, FLOW;
    
    private static final Helper[] $VALUES;
    
    static {
      Helper helper = new Helper("FLOW", 6);
      FLOW = helper;
      $VALUES = new Helper[] { HORIZONTAL_CHAIN, VERTICAL_CHAIN, ALIGN_HORIZONTALLY, ALIGN_VERTICALLY, BARRIER, LAYER, helper };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\state\State.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */