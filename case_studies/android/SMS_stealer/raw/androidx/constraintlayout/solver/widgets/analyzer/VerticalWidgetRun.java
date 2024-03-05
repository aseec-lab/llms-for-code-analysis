package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public class VerticalWidgetRun extends WidgetRun {
  public DependencyNode baseline = new DependencyNode(this);
  
  DimensionDependency baselineDimension = null;
  
  public VerticalWidgetRun(ConstraintWidget paramConstraintWidget) {
    super(paramConstraintWidget);
    this.start.type = DependencyNode.Type.TOP;
    this.end.type = DependencyNode.Type.BOTTOM;
    this.baseline.type = DependencyNode.Type.BASELINE;
    this.orientation = 1;
  }
  
  void apply() {
    if (this.widget.measured)
      this.dimension.resolve(this.widget.getHeight()); 
    if (!this.dimension.resolved) {
      this.dimensionBehavior = this.widget.getVerticalDimensionBehaviour();
      if (this.widget.hasBaseline())
        this.baselineDimension = new BaselineDimensionDependency(this); 
      if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
          ConstraintWidget constraintWidget = this.widget.getParent();
          if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
            int k = constraintWidget.getHeight();
            int j = this.widget.mTop.getMargin();
            int i = this.widget.mBottom.getMargin();
            addTarget(this.start, constraintWidget.verticalRun.start, this.widget.mTop.getMargin());
            addTarget(this.end, constraintWidget.verticalRun.end, -this.widget.mBottom.getMargin());
            this.dimension.resolve(k - j - i);
            return;
          } 
        } 
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED)
          this.dimension.resolve(this.widget.getHeight()); 
      } 
    } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
      ConstraintWidget constraintWidget = this.widget.getParent();
      if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
        addTarget(this.start, constraintWidget.verticalRun.start, this.widget.mTop.getMargin());
        addTarget(this.end, constraintWidget.verticalRun.end, -this.widget.mBottom.getMargin());
        return;
      } 
    } 
    if (this.dimension.resolved && this.widget.measured) {
      if ((this.widget.mListAnchors[2]).mTarget != null && (this.widget.mListAnchors[3]).mTarget != null) {
        if (this.widget.isInVerticalChain()) {
          this.start.margin = this.widget.mListAnchors[2].getMargin();
          this.end.margin = -this.widget.mListAnchors[3].getMargin();
        } else {
          DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[2]);
          if (dependencyNode != null)
            addTarget(this.start, dependencyNode, this.widget.mListAnchors[2].getMargin()); 
          dependencyNode = getTarget(this.widget.mListAnchors[3]);
          if (dependencyNode != null)
            addTarget(this.end, dependencyNode, -this.widget.mListAnchors[3].getMargin()); 
          this.start.delegateToWidgetRun = true;
          this.end.delegateToWidgetRun = true;
        } 
        if (this.widget.hasBaseline())
          addTarget(this.baseline, this.start, this.widget.getBaselineDistance()); 
      } else if ((this.widget.mListAnchors[2]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[2]);
        if (dependencyNode != null) {
          addTarget(this.start, dependencyNode, this.widget.mListAnchors[2].getMargin());
          addTarget(this.end, this.start, this.dimension.value);
          if (this.widget.hasBaseline())
            addTarget(this.baseline, this.start, this.widget.getBaselineDistance()); 
        } 
      } else if ((this.widget.mListAnchors[3]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[3]);
        if (dependencyNode != null) {
          addTarget(this.end, dependencyNode, -this.widget.mListAnchors[3].getMargin());
          addTarget(this.start, this.end, -this.dimension.value);
        } 
        if (this.widget.hasBaseline())
          addTarget(this.baseline, this.start, this.widget.getBaselineDistance()); 
      } else if ((this.widget.mListAnchors[4]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[4]);
        if (dependencyNode != null) {
          addTarget(this.baseline, dependencyNode, 0);
          addTarget(this.start, this.baseline, -this.widget.getBaselineDistance());
          addTarget(this.end, this.start, this.dimension.value);
        } 
      } else if (!(this.widget instanceof androidx.constraintlayout.solver.widgets.Helper) && this.widget.getParent() != null && (this.widget.getAnchor(ConstraintAnchor.Type.CENTER)).mTarget == null) {
        DependencyNode dependencyNode = (this.widget.getParent()).verticalRun.start;
        addTarget(this.start, dependencyNode, this.widget.getY());
        addTarget(this.end, this.start, this.dimension.value);
        if (this.widget.hasBaseline())
          addTarget(this.baseline, this.start, this.widget.getBaselineDistance()); 
      } 
    } else {
      if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        int i = this.widget.mMatchConstraintDefaultHeight;
        if (i != 2) {
          if (i == 3 && !this.widget.isInVerticalChain() && this.widget.mMatchConstraintDefaultWidth != 3) {
            DimensionDependency dimensionDependency = this.widget.horizontalRun.dimension;
            this.dimension.targets.add(dimensionDependency);
            dimensionDependency.dependencies.add(this.dimension);
            this.dimension.delegateToWidgetRun = true;
            this.dimension.dependencies.add(this.start);
            this.dimension.dependencies.add(this.end);
          } 
        } else {
          ConstraintWidget constraintWidget = this.widget.getParent();
          if (constraintWidget != null) {
            DimensionDependency dimensionDependency = constraintWidget.verticalRun.dimension;
            this.dimension.targets.add(dimensionDependency);
            dimensionDependency.dependencies.add(this.dimension);
            this.dimension.delegateToWidgetRun = true;
            this.dimension.dependencies.add(this.start);
            this.dimension.dependencies.add(this.end);
          } 
        } 
      } else {
        this.dimension.addDependency(this);
      } 
      if ((this.widget.mListAnchors[2]).mTarget != null && (this.widget.mListAnchors[3]).mTarget != null) {
        if (this.widget.isInVerticalChain()) {
          this.start.margin = this.widget.mListAnchors[2].getMargin();
          this.end.margin = -this.widget.mListAnchors[3].getMargin();
        } else {
          DependencyNode dependencyNode1 = getTarget(this.widget.mListAnchors[2]);
          DependencyNode dependencyNode2 = getTarget(this.widget.mListAnchors[3]);
          dependencyNode1.addDependency(this);
          dependencyNode2.addDependency(this);
          this.mRunType = WidgetRun.RunType.CENTER;
        } 
        if (this.widget.hasBaseline())
          addTarget(this.baseline, this.start, 1, this.baselineDimension); 
      } else if ((this.widget.mListAnchors[2]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[2]);
        if (dependencyNode != null) {
          addTarget(this.start, dependencyNode, this.widget.mListAnchors[2].getMargin());
          addTarget(this.end, this.start, 1, this.dimension);
          if (this.widget.hasBaseline())
            addTarget(this.baseline, this.start, 1, this.baselineDimension); 
          if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.getDimensionRatio() > 0.0F && this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
            this.dimension.targets.add(this.widget.horizontalRun.dimension);
            this.dimension.updateDelegate = this;
          } 
        } 
      } else if ((this.widget.mListAnchors[3]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[3]);
        if (dependencyNode != null) {
          addTarget(this.end, dependencyNode, -this.widget.mListAnchors[3].getMargin());
          addTarget(this.start, this.end, -1, this.dimension);
          if (this.widget.hasBaseline())
            addTarget(this.baseline, this.start, 1, this.baselineDimension); 
        } 
      } else if ((this.widget.mListAnchors[4]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[4]);
        if (dependencyNode != null) {
          addTarget(this.baseline, dependencyNode, 0);
          addTarget(this.start, this.baseline, -1, this.baselineDimension);
          addTarget(this.end, this.start, 1, this.dimension);
        } 
      } else if (!(this.widget instanceof androidx.constraintlayout.solver.widgets.Helper) && this.widget.getParent() != null) {
        DependencyNode dependencyNode = (this.widget.getParent()).verticalRun.start;
        addTarget(this.start, dependencyNode, this.widget.getY());
        addTarget(this.end, this.start, 1, this.dimension);
        if (this.widget.hasBaseline())
          addTarget(this.baseline, this.start, 1, this.baselineDimension); 
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.getDimensionRatio() > 0.0F && this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
          this.dimension.targets.add(this.widget.horizontalRun.dimension);
          this.dimension.updateDelegate = this;
        } 
      } 
      if (this.dimension.targets.size() == 0)
        this.dimension.readyToSolve = true; 
    } 
  }
  
  public void applyToWidget() {
    if (this.start.resolved)
      this.widget.setY(this.start.value); 
  }
  
  void clear() {
    this.runGroup = null;
    this.start.clear();
    this.end.clear();
    this.baseline.clear();
    this.dimension.clear();
    this.resolved = false;
  }
  
  void reset() {
    this.resolved = false;
    this.start.clear();
    this.start.resolved = false;
    this.end.clear();
    this.end.resolved = false;
    this.baseline.clear();
    this.baseline.resolved = false;
    this.dimension.resolved = false;
  }
  
  boolean supportsWrapComputation() {
    return (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) ? ((this.widget.mMatchConstraintDefaultHeight == 0)) : true;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("VerticalRun ");
    stringBuilder.append(this.widget.getDebugName());
    return stringBuilder.toString();
  }
  
  public void update(Dependency paramDependency) {
    int i = null.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i == 3) {
          updateRunCenter(paramDependency, this.widget.mTop, this.widget.mBottom, 1);
          return;
        } 
      } else {
        updateRunEnd(paramDependency);
      } 
    } else {
      updateRunStart(paramDependency);
    } 
    if (this.dimension.readyToSolve && !this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      i = this.widget.mMatchConstraintDefaultHeight;
      if (i != 2) {
        if (i == 3 && this.widget.horizontalRun.dimension.resolved) {
          i = this.widget.getDimensionRatioSide();
          if (i != -1) {
            if (i != 0) {
              if (i != 1) {
                i = 0;
              } else {
                float f2 = this.widget.horizontalRun.dimension.value;
                float f1 = this.widget.getDimensionRatio();
                f1 = f2 / f1;
              } 
            } else {
              float f = this.widget.horizontalRun.dimension.value * this.widget.getDimensionRatio();
              i = (int)(f + 0.5F);
            } 
          } else {
            float f2 = this.widget.horizontalRun.dimension.value;
            float f1 = this.widget.getDimensionRatio();
            f1 = f2 / f1;
          } 
          this.dimension.resolve(i);
        } 
      } else {
        ConstraintWidget constraintWidget = this.widget.getParent();
        if (constraintWidget != null && constraintWidget.verticalRun.dimension.resolved) {
          float f = this.widget.mMatchConstraintPercentHeight;
          i = (int)(constraintWidget.verticalRun.dimension.value * f + 0.5F);
          this.dimension.resolve(i);
        } 
      } 
    } 
    if (this.start.readyToSolve && this.end.readyToSolve) {
      if (this.start.resolved && this.end.resolved && this.dimension.resolved)
        return; 
      if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.mMatchConstraintDefaultWidth == 0 && !this.widget.isInVerticalChain()) {
        DependencyNode dependencyNode = this.start.targets.get(0);
        paramDependency = this.end.targets.get(0);
        int j = dependencyNode.value + this.start.margin;
        i = ((DependencyNode)paramDependency).value + this.end.margin;
        this.start.resolve(j);
        this.end.resolve(i);
        this.dimension.resolve(i - j);
        return;
      } 
      if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
        paramDependency = this.start.targets.get(0);
        DependencyNode dependencyNode = this.end.targets.get(0);
        int j = ((DependencyNode)paramDependency).value;
        i = this.start.margin;
        i = dependencyNode.value + this.end.margin - j + i;
        if (i < this.dimension.wrapValue) {
          this.dimension.resolve(i);
        } else {
          this.dimension.resolve(this.dimension.wrapValue);
        } 
      } 
      if (!this.dimension.resolved)
        return; 
      if (this.start.targets.size() > 0 && this.end.targets.size() > 0) {
        paramDependency = this.start.targets.get(0);
        DependencyNode dependencyNode = this.end.targets.get(0);
        i = ((DependencyNode)paramDependency).value + this.start.margin;
        int j = dependencyNode.value + this.end.margin;
        float f = this.widget.getVerticalBiasPercent();
        if (paramDependency == dependencyNode) {
          i = ((DependencyNode)paramDependency).value;
          j = dependencyNode.value;
          f = 0.5F;
        } 
        int k = this.dimension.value;
        this.start.resolve((int)(i + 0.5F + (j - i - k) * f));
        this.end.resolve(this.start.value + this.dimension.value);
      } 
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\VerticalWidgetRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */