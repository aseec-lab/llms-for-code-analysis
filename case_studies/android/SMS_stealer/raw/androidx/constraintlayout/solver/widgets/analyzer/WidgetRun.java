package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public abstract class WidgetRun implements Dependency {
  DimensionDependency dimension = new DimensionDependency(this);
  
  protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
  
  public DependencyNode end = new DependencyNode(this);
  
  protected RunType mRunType = RunType.NONE;
  
  public int matchConstraintsType;
  
  public int orientation = 0;
  
  boolean resolved = false;
  
  RunGroup runGroup;
  
  public DependencyNode start = new DependencyNode(this);
  
  ConstraintWidget widget;
  
  public WidgetRun(ConstraintWidget paramConstraintWidget) {
    this.widget = paramConstraintWidget;
  }
  
  private void resolveDimension(int paramInt1, int paramInt2) {
    int i = this.matchConstraintsType;
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i == 3 && (this.widget.horizontalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.horizontalRun.matchConstraintsType != 3 || this.widget.verticalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.verticalRun.matchConstraintsType != 3)) {
            VerticalWidgetRun verticalWidgetRun;
            HorizontalWidgetRun horizontalWidgetRun;
            ConstraintWidget constraintWidget = this.widget;
            if (paramInt1 == 0) {
              verticalWidgetRun = constraintWidget.verticalRun;
            } else {
              horizontalWidgetRun = ((ConstraintWidget)verticalWidgetRun).horizontalRun;
            } 
            if (horizontalWidgetRun.dimension.resolved) {
              float f = this.widget.getDimensionRatio();
              if (paramInt1 == 1) {
                paramInt1 = (int)(horizontalWidgetRun.dimension.value / f + 0.5F);
              } else {
                paramInt1 = (int)(f * horizontalWidgetRun.dimension.value + 0.5F);
              } 
              this.dimension.resolve(paramInt1);
            } 
          } 
        } else {
          ConstraintWidget constraintWidget = this.widget.getParent();
          if (constraintWidget != null) {
            HorizontalWidgetRun horizontalWidgetRun;
            VerticalWidgetRun verticalWidgetRun;
            if (paramInt1 == 0) {
              horizontalWidgetRun = constraintWidget.horizontalRun;
            } else {
              verticalWidgetRun = ((ConstraintWidget)horizontalWidgetRun).verticalRun;
            } 
            if (verticalWidgetRun.dimension.resolved) {
              float f;
              ConstraintWidget constraintWidget1 = this.widget;
              if (paramInt1 == 0) {
                f = constraintWidget1.mMatchConstraintPercentWidth;
              } else {
                f = constraintWidget1.mMatchConstraintPercentHeight;
              } 
              paramInt2 = (int)(verticalWidgetRun.dimension.value * f + 0.5F);
              this.dimension.resolve(getLimitedDimension(paramInt2, paramInt1));
            } 
          } 
        } 
      } else {
        paramInt1 = getLimitedDimension(this.dimension.wrapValue, paramInt1);
        this.dimension.resolve(Math.min(paramInt1, paramInt2));
      } 
    } else {
      this.dimension.resolve(getLimitedDimension(paramInt2, paramInt1));
    } 
  }
  
  protected final void addTarget(DependencyNode paramDependencyNode1, DependencyNode paramDependencyNode2, int paramInt) {
    paramDependencyNode1.targets.add(paramDependencyNode2);
    paramDependencyNode1.margin = paramInt;
    paramDependencyNode2.dependencies.add(paramDependencyNode1);
  }
  
  protected final void addTarget(DependencyNode paramDependencyNode1, DependencyNode paramDependencyNode2, int paramInt, DimensionDependency paramDimensionDependency) {
    paramDependencyNode1.targets.add(paramDependencyNode2);
    paramDependencyNode1.targets.add(this.dimension);
    paramDependencyNode1.marginFactor = paramInt;
    paramDependencyNode1.marginDependency = paramDimensionDependency;
    paramDependencyNode2.dependencies.add(paramDependencyNode1);
    paramDimensionDependency.dependencies.add(paramDependencyNode1);
  }
  
  abstract void apply();
  
  abstract void applyToWidget();
  
  abstract void clear();
  
  protected final int getLimitedDimension(int paramInt1, int paramInt2) {
    if (paramInt2 == 0) {
      int i = this.widget.mMatchConstraintMaxWidth;
      paramInt2 = Math.max(this.widget.mMatchConstraintMinWidth, paramInt1);
      if (i > 0)
        paramInt2 = Math.min(i, paramInt1); 
      i = paramInt1;
      if (paramInt2 != paramInt1)
        return paramInt2; 
    } else {
      int i = this.widget.mMatchConstraintMaxHeight;
      paramInt2 = Math.max(this.widget.mMatchConstraintMinHeight, paramInt1);
      if (i > 0)
        paramInt2 = Math.min(i, paramInt1); 
      i = paramInt1;
      if (paramInt2 != paramInt1)
        return paramInt2; 
    } 
    return SYNTHETIC_LOCAL_VARIABLE_3;
  }
  
  protected final DependencyNode getTarget(ConstraintAnchor paramConstraintAnchor) {
    DependencyNode dependencyNode;
    ConstraintAnchor constraintAnchor = paramConstraintAnchor.mTarget;
    ConstraintAnchor.Type type2 = null;
    if (constraintAnchor == null)
      return null; 
    ConstraintWidget constraintWidget = paramConstraintAnchor.mTarget.mOwner;
    ConstraintAnchor.Type type1 = paramConstraintAnchor.mTarget.mType;
    int i = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[type1.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i != 5) {
              type1 = type2;
            } else {
              dependencyNode = constraintWidget.verticalRun.end;
            } 
          } else {
            dependencyNode = constraintWidget.verticalRun.baseline;
          } 
        } else {
          dependencyNode = constraintWidget.verticalRun.start;
        } 
      } else {
        dependencyNode = constraintWidget.horizontalRun.end;
      } 
    } else {
      dependencyNode = constraintWidget.horizontalRun.start;
    } 
    return dependencyNode;
  }
  
  protected final DependencyNode getTarget(ConstraintAnchor paramConstraintAnchor, int paramInt) {
    // Byte code:
    //   0: aload_1
    //   1: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   4: astore_3
    //   5: aconst_null
    //   6: astore #4
    //   8: aload_3
    //   9: ifnonnull -> 14
    //   12: aconst_null
    //   13: areturn
    //   14: aload_1
    //   15: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   18: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   21: astore_3
    //   22: iload_2
    //   23: ifne -> 34
    //   26: aload_3
    //   27: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   30: astore_3
    //   31: goto -> 39
    //   34: aload_3
    //   35: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   38: astore_3
    //   39: aload_1
    //   40: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   43: getfield mType : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   46: astore_1
    //   47: getstatic androidx/constraintlayout/solver/widgets/analyzer/WidgetRun$1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type : [I
    //   50: aload_1
    //   51: invokevirtual ordinal : ()I
    //   54: iaload
    //   55: istore_2
    //   56: iload_2
    //   57: iconst_1
    //   58: if_icmpeq -> 90
    //   61: iload_2
    //   62: iconst_2
    //   63: if_icmpeq -> 82
    //   66: iload_2
    //   67: iconst_3
    //   68: if_icmpeq -> 90
    //   71: iload_2
    //   72: iconst_5
    //   73: if_icmpeq -> 82
    //   76: aload #4
    //   78: astore_1
    //   79: goto -> 95
    //   82: aload_3
    //   83: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   86: astore_1
    //   87: goto -> 95
    //   90: aload_3
    //   91: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   94: astore_1
    //   95: aload_1
    //   96: areturn
  }
  
  public long getWrapDimension() {
    return this.dimension.resolved ? this.dimension.value : 0L;
  }
  
  public boolean isCenterConnection() {
    int j = this.start.targets.size();
    boolean bool = false;
    byte b = 0;
    int i;
    for (i = 0; b < j; i = k) {
      int k = i;
      if (((DependencyNode)this.start.targets.get(b)).run != this)
        k = i + 1; 
      b++;
    } 
    j = this.end.targets.size();
    b = 0;
    while (b < j) {
      int k = i;
      if (((DependencyNode)this.end.targets.get(b)).run != this)
        k = i + 1; 
      b++;
      i = k;
    } 
    if (i >= 2)
      bool = true; 
    return bool;
  }
  
  public boolean isDimensionResolved() {
    return this.dimension.resolved;
  }
  
  public boolean isResolved() {
    return this.resolved;
  }
  
  abstract void reset();
  
  abstract boolean supportsWrapComputation();
  
  public void update(Dependency paramDependency) {}
  
  protected void updateRunCenter(Dependency paramDependency, ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt) {
    DependencyNode dependencyNode = getTarget(paramConstraintAnchor1);
    paramDependency = getTarget(paramConstraintAnchor2);
    if (dependencyNode.resolved && ((DependencyNode)paramDependency).resolved) {
      float f;
      int i = dependencyNode.value + paramConstraintAnchor1.getMargin();
      int j = ((DependencyNode)paramDependency).value - paramConstraintAnchor2.getMargin();
      int k = j - i;
      if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        resolveDimension(paramInt, k); 
      if (!this.dimension.resolved)
        return; 
      if (this.dimension.value == k) {
        this.start.resolve(i);
        this.end.resolve(j);
        return;
      } 
      ConstraintWidget constraintWidget = this.widget;
      if (paramInt == 0) {
        f = constraintWidget.getHorizontalBiasPercent();
      } else {
        f = constraintWidget.getVerticalBiasPercent();
      } 
      paramInt = j;
      if (dependencyNode == paramDependency) {
        i = dependencyNode.value;
        paramInt = ((DependencyNode)paramDependency).value;
        f = 0.5F;
      } 
      j = this.dimension.value;
      this.start.resolve((int)(i + 0.5F + (paramInt - i - j) * f));
      this.end.resolve(this.start.value + this.dimension.value);
    } 
  }
  
  protected void updateRunEnd(Dependency paramDependency) {}
  
  protected void updateRunStart(Dependency paramDependency) {}
  
  public long wrapSize(int paramInt) {
    if (this.dimension.resolved) {
      long l = this.dimension.value;
      if (isCenterConnection()) {
        paramInt = this.start.margin - this.end.margin;
      } else if (paramInt == 0) {
        paramInt = this.start.margin;
      } else {
        l -= this.end.margin;
        return l;
      } 
      l += paramInt;
      return l;
    } 
    return 0L;
  }
  
  enum RunType {
    CENTER, END, NONE, START;
    
    private static final RunType[] $VALUES;
    
    static {
      RunType runType = new RunType("CENTER", 3);
      CENTER = runType;
      $VALUES = new RunType[] { NONE, START, END, runType };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\WidgetRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */