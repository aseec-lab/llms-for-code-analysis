package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public class HorizontalWidgetRun extends WidgetRun {
  private static int[] tempDimensions = new int[2];
  
  public HorizontalWidgetRun(ConstraintWidget paramConstraintWidget) {
    super(paramConstraintWidget);
    this.start.type = DependencyNode.Type.LEFT;
    this.end.type = DependencyNode.Type.RIGHT;
    this.orientation = 0;
  }
  
  private void computeInsetRatio(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5) {
    paramInt1 = paramInt2 - paramInt1;
    paramInt2 = paramInt4 - paramInt3;
    if (paramInt5 != -1) {
      if (paramInt5 != 0) {
        if (paramInt5 == 1) {
          paramInt2 = (int)(paramInt1 * paramFloat + 0.5F);
          paramArrayOfint[0] = paramInt1;
          paramArrayOfint[1] = paramInt2;
        } 
      } else {
        paramArrayOfint[0] = (int)(paramInt2 * paramFloat + 0.5F);
        paramArrayOfint[1] = paramInt2;
      } 
    } else {
      paramInt4 = (int)(paramInt2 * paramFloat + 0.5F);
      paramInt3 = (int)(paramInt1 / paramFloat + 0.5F);
      if (paramInt4 <= paramInt1) {
        paramArrayOfint[0] = paramInt4;
        paramArrayOfint[1] = paramInt2;
      } else if (paramInt3 <= paramInt2) {
        paramArrayOfint[0] = paramInt1;
        paramArrayOfint[1] = paramInt3;
      } 
    } 
  }
  
  void apply() {
    if (this.widget.measured)
      this.dimension.resolve(this.widget.getWidth()); 
    if (!this.dimension.resolved) {
      this.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
      if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
          ConstraintWidget constraintWidget = this.widget.getParent();
          if ((constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int k = constraintWidget.getWidth();
            int j = this.widget.mLeft.getMargin();
            int i = this.widget.mRight.getMargin();
            addTarget(this.start, constraintWidget.horizontalRun.start, this.widget.mLeft.getMargin());
            addTarget(this.end, constraintWidget.horizontalRun.end, -this.widget.mRight.getMargin());
            this.dimension.resolve(k - j - i);
            return;
          } 
        } 
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED)
          this.dimension.resolve(this.widget.getWidth()); 
      } 
    } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
      ConstraintWidget constraintWidget = this.widget.getParent();
      if ((constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
        addTarget(this.start, constraintWidget.horizontalRun.start, this.widget.mLeft.getMargin());
        addTarget(this.end, constraintWidget.horizontalRun.end, -this.widget.mRight.getMargin());
        return;
      } 
    } 
    if (this.dimension.resolved && this.widget.measured) {
      if ((this.widget.mListAnchors[0]).mTarget != null && (this.widget.mListAnchors[1]).mTarget != null) {
        if (this.widget.isInHorizontalChain()) {
          this.start.margin = this.widget.mListAnchors[0].getMargin();
          this.end.margin = -this.widget.mListAnchors[1].getMargin();
        } else {
          DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[0]);
          if (dependencyNode != null)
            addTarget(this.start, dependencyNode, this.widget.mListAnchors[0].getMargin()); 
          dependencyNode = getTarget(this.widget.mListAnchors[1]);
          if (dependencyNode != null)
            addTarget(this.end, dependencyNode, -this.widget.mListAnchors[1].getMargin()); 
          this.start.delegateToWidgetRun = true;
          this.end.delegateToWidgetRun = true;
        } 
      } else if ((this.widget.mListAnchors[0]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[0]);
        if (dependencyNode != null) {
          addTarget(this.start, dependencyNode, this.widget.mListAnchors[0].getMargin());
          addTarget(this.end, this.start, this.dimension.value);
        } 
      } else if ((this.widget.mListAnchors[1]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[1]);
        if (dependencyNode != null) {
          addTarget(this.end, dependencyNode, -this.widget.mListAnchors[1].getMargin());
          addTarget(this.start, this.end, -this.dimension.value);
        } 
      } else if (!(this.widget instanceof androidx.constraintlayout.solver.widgets.Helper) && this.widget.getParent() != null && (this.widget.getAnchor(ConstraintAnchor.Type.CENTER)).mTarget == null) {
        DependencyNode dependencyNode = (this.widget.getParent()).horizontalRun.start;
        addTarget(this.start, dependencyNode, this.widget.getX());
        addTarget(this.end, this.start, this.dimension.value);
      } 
    } else {
      if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        int i = this.widget.mMatchConstraintDefaultWidth;
        if (i != 2) {
          if (i == 3)
            if (this.widget.mMatchConstraintDefaultHeight == 3) {
              this.start.updateDelegate = this;
              this.end.updateDelegate = this;
              this.widget.verticalRun.start.updateDelegate = this;
              this.widget.verticalRun.end.updateDelegate = this;
              this.dimension.updateDelegate = this;
              if (this.widget.isInVerticalChain()) {
                this.dimension.targets.add(this.widget.verticalRun.dimension);
                this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                this.widget.verticalRun.dimension.updateDelegate = this;
                this.dimension.targets.add(this.widget.verticalRun.start);
                this.dimension.targets.add(this.widget.verticalRun.end);
                this.widget.verticalRun.start.dependencies.add(this.dimension);
                this.widget.verticalRun.end.dependencies.add(this.dimension);
              } else if (this.widget.isInHorizontalChain()) {
                this.widget.verticalRun.dimension.targets.add(this.dimension);
                this.dimension.dependencies.add(this.widget.verticalRun.dimension);
              } else {
                this.widget.verticalRun.dimension.targets.add(this.dimension);
              } 
            } else {
              DimensionDependency dimensionDependency = this.widget.verticalRun.dimension;
              this.dimension.targets.add(dimensionDependency);
              dimensionDependency.dependencies.add(this.dimension);
              this.widget.verticalRun.start.dependencies.add(this.dimension);
              this.widget.verticalRun.end.dependencies.add(this.dimension);
              this.dimension.delegateToWidgetRun = true;
              this.dimension.dependencies.add(this.start);
              this.dimension.dependencies.add(this.end);
              this.start.targets.add(this.dimension);
              this.end.targets.add(this.dimension);
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
      } 
      if ((this.widget.mListAnchors[0]).mTarget != null && (this.widget.mListAnchors[1]).mTarget != null) {
        if (this.widget.isInHorizontalChain()) {
          this.start.margin = this.widget.mListAnchors[0].getMargin();
          this.end.margin = -this.widget.mListAnchors[1].getMargin();
        } else {
          DependencyNode dependencyNode2 = getTarget(this.widget.mListAnchors[0]);
          DependencyNode dependencyNode1 = getTarget(this.widget.mListAnchors[1]);
          dependencyNode2.addDependency(this);
          dependencyNode1.addDependency(this);
          this.mRunType = WidgetRun.RunType.CENTER;
        } 
      } else if ((this.widget.mListAnchors[0]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[0]);
        if (dependencyNode != null) {
          addTarget(this.start, dependencyNode, this.widget.mListAnchors[0].getMargin());
          addTarget(this.end, this.start, 1, this.dimension);
        } 
      } else if ((this.widget.mListAnchors[1]).mTarget != null) {
        DependencyNode dependencyNode = getTarget(this.widget.mListAnchors[1]);
        if (dependencyNode != null) {
          addTarget(this.end, dependencyNode, -this.widget.mListAnchors[1].getMargin());
          addTarget(this.start, this.end, -1, this.dimension);
        } 
      } else if (!(this.widget instanceof androidx.constraintlayout.solver.widgets.Helper) && this.widget.getParent() != null) {
        DependencyNode dependencyNode = (this.widget.getParent()).horizontalRun.start;
        addTarget(this.start, dependencyNode, this.widget.getX());
        addTarget(this.end, this.start, 1, this.dimension);
      } 
    } 
  }
  
  public void applyToWidget() {
    if (this.start.resolved)
      this.widget.setX(this.start.value); 
  }
  
  void clear() {
    this.runGroup = null;
    this.start.clear();
    this.end.clear();
    this.dimension.clear();
    this.resolved = false;
  }
  
  void reset() {
    this.resolved = false;
    this.start.clear();
    this.start.resolved = false;
    this.end.clear();
    this.end.resolved = false;
    this.dimension.resolved = false;
  }
  
  boolean supportsWrapComputation() {
    return (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) ? ((this.widget.mMatchConstraintDefaultWidth == 0)) : true;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("HorizontalRun ");
    stringBuilder.append(this.widget.getDebugName());
    return stringBuilder.toString();
  }
  
  public void update(Dependency paramDependency) {
    // Byte code:
    //   0: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun$1.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType : [I
    //   3: aload_0
    //   4: getfield mRunType : Landroidx/constraintlayout/solver/widgets/analyzer/WidgetRun$RunType;
    //   7: invokevirtual ordinal : ()I
    //   10: iaload
    //   11: istore #4
    //   13: iload #4
    //   15: iconst_1
    //   16: if_icmpeq -> 63
    //   19: iload #4
    //   21: iconst_2
    //   22: if_icmpeq -> 55
    //   25: iload #4
    //   27: iconst_3
    //   28: if_icmpeq -> 34
    //   31: goto -> 68
    //   34: aload_0
    //   35: aload_1
    //   36: aload_0
    //   37: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   40: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   43: aload_0
    //   44: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   47: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   50: iconst_0
    //   51: invokevirtual updateRunCenter : (Landroidx/constraintlayout/solver/widgets/analyzer/Dependency;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)V
    //   54: return
    //   55: aload_0
    //   56: aload_1
    //   57: invokevirtual updateRunEnd : (Landroidx/constraintlayout/solver/widgets/analyzer/Dependency;)V
    //   60: goto -> 68
    //   63: aload_0
    //   64: aload_1
    //   65: invokevirtual updateRunStart : (Landroidx/constraintlayout/solver/widgets/analyzer/Dependency;)V
    //   68: aload_0
    //   69: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   72: getfield resolved : Z
    //   75: ifne -> 1593
    //   78: aload_0
    //   79: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   82: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   85: if_acmpne -> 1593
    //   88: aload_0
    //   89: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   92: getfield mMatchConstraintDefaultWidth : I
    //   95: istore #4
    //   97: iload #4
    //   99: iconst_2
    //   100: if_icmpeq -> 1532
    //   103: iload #4
    //   105: iconst_3
    //   106: if_icmpeq -> 112
    //   109: goto -> 1593
    //   112: aload_0
    //   113: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   116: getfield mMatchConstraintDefaultHeight : I
    //   119: ifeq -> 266
    //   122: aload_0
    //   123: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   126: getfield mMatchConstraintDefaultHeight : I
    //   129: iconst_3
    //   130: if_icmpne -> 136
    //   133: goto -> 266
    //   136: aload_0
    //   137: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   140: invokevirtual getDimensionRatioSide : ()I
    //   143: istore #4
    //   145: iload #4
    //   147: iconst_m1
    //   148: if_icmpeq -> 220
    //   151: iload #4
    //   153: ifeq -> 194
    //   156: iload #4
    //   158: iconst_1
    //   159: if_icmpeq -> 168
    //   162: iconst_0
    //   163: istore #4
    //   165: goto -> 254
    //   168: aload_0
    //   169: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   172: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   175: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   178: getfield value : I
    //   181: i2f
    //   182: fstore_3
    //   183: aload_0
    //   184: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   187: invokevirtual getDimensionRatio : ()F
    //   190: fstore_2
    //   191: goto -> 243
    //   194: aload_0
    //   195: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   198: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   201: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   204: getfield value : I
    //   207: i2f
    //   208: aload_0
    //   209: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   212: invokevirtual getDimensionRatio : ()F
    //   215: fdiv
    //   216: fstore_2
    //   217: goto -> 247
    //   220: aload_0
    //   221: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   224: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   227: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   230: getfield value : I
    //   233: i2f
    //   234: fstore_3
    //   235: aload_0
    //   236: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   239: invokevirtual getDimensionRatio : ()F
    //   242: fstore_2
    //   243: fload_3
    //   244: fload_2
    //   245: fmul
    //   246: fstore_2
    //   247: fload_2
    //   248: ldc 0.5
    //   250: fadd
    //   251: f2i
    //   252: istore #4
    //   254: aload_0
    //   255: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   258: iload #4
    //   260: invokevirtual resolve : (I)V
    //   263: goto -> 1593
    //   266: aload_0
    //   267: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   270: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   273: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   276: astore #13
    //   278: aload_0
    //   279: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   282: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   285: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   288: astore_1
    //   289: aload_0
    //   290: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   293: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   296: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   299: ifnull -> 308
    //   302: iconst_1
    //   303: istore #4
    //   305: goto -> 311
    //   308: iconst_0
    //   309: istore #4
    //   311: aload_0
    //   312: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   315: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   318: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   321: ifnull -> 330
    //   324: iconst_1
    //   325: istore #5
    //   327: goto -> 333
    //   330: iconst_0
    //   331: istore #5
    //   333: aload_0
    //   334: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   337: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   340: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   343: ifnull -> 352
    //   346: iconst_1
    //   347: istore #6
    //   349: goto -> 355
    //   352: iconst_0
    //   353: istore #6
    //   355: aload_0
    //   356: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   359: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   362: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   365: ifnull -> 374
    //   368: iconst_1
    //   369: istore #7
    //   371: goto -> 377
    //   374: iconst_0
    //   375: istore #7
    //   377: aload_0
    //   378: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   381: invokevirtual getDimensionRatioSide : ()I
    //   384: istore #8
    //   386: iload #4
    //   388: ifeq -> 999
    //   391: iload #5
    //   393: ifeq -> 999
    //   396: iload #6
    //   398: ifeq -> 999
    //   401: iload #7
    //   403: ifeq -> 999
    //   406: aload_0
    //   407: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   410: invokevirtual getDimensionRatio : ()F
    //   413: fstore_2
    //   414: aload #13
    //   416: getfield resolved : Z
    //   419: ifeq -> 599
    //   422: aload_1
    //   423: getfield resolved : Z
    //   426: ifeq -> 599
    //   429: aload_0
    //   430: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   433: getfield readyToSolve : Z
    //   436: ifeq -> 598
    //   439: aload_0
    //   440: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   443: getfield readyToSolve : Z
    //   446: ifne -> 452
    //   449: goto -> 598
    //   452: aload_0
    //   453: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   456: getfield targets : Ljava/util/List;
    //   459: iconst_0
    //   460: invokeinterface get : (I)Ljava/lang/Object;
    //   465: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   468: getfield value : I
    //   471: istore #7
    //   473: aload_0
    //   474: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   477: getfield margin : I
    //   480: istore #10
    //   482: aload_0
    //   483: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   486: getfield targets : Ljava/util/List;
    //   489: iconst_0
    //   490: invokeinterface get : (I)Ljava/lang/Object;
    //   495: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   498: getfield value : I
    //   501: istore #4
    //   503: aload_0
    //   504: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   507: getfield margin : I
    //   510: istore #5
    //   512: aload #13
    //   514: getfield value : I
    //   517: istore #11
    //   519: aload #13
    //   521: getfield margin : I
    //   524: istore #6
    //   526: aload_1
    //   527: getfield value : I
    //   530: istore #9
    //   532: aload_1
    //   533: getfield margin : I
    //   536: istore #12
    //   538: aload_0
    //   539: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   542: iload #7
    //   544: iload #10
    //   546: iadd
    //   547: iload #4
    //   549: iload #5
    //   551: isub
    //   552: iload #11
    //   554: iload #6
    //   556: iadd
    //   557: iload #9
    //   559: iload #12
    //   561: isub
    //   562: fload_2
    //   563: iload #8
    //   565: invokespecial computeInsetRatio : ([IIIIIFI)V
    //   568: aload_0
    //   569: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   572: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   575: iconst_0
    //   576: iaload
    //   577: invokevirtual resolve : (I)V
    //   580: aload_0
    //   581: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   584: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   587: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   590: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   593: iconst_1
    //   594: iaload
    //   595: invokevirtual resolve : (I)V
    //   598: return
    //   599: aload_0
    //   600: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   603: getfield resolved : Z
    //   606: ifeq -> 787
    //   609: aload_0
    //   610: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   613: getfield resolved : Z
    //   616: ifeq -> 787
    //   619: aload #13
    //   621: getfield readyToSolve : Z
    //   624: ifeq -> 786
    //   627: aload_1
    //   628: getfield readyToSolve : Z
    //   631: ifne -> 637
    //   634: goto -> 786
    //   637: aload_0
    //   638: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   641: getfield value : I
    //   644: istore #11
    //   646: aload_0
    //   647: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   650: getfield margin : I
    //   653: istore #12
    //   655: aload_0
    //   656: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   659: getfield value : I
    //   662: istore #4
    //   664: aload_0
    //   665: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   668: getfield margin : I
    //   671: istore #7
    //   673: aload #13
    //   675: getfield targets : Ljava/util/List;
    //   678: iconst_0
    //   679: invokeinterface get : (I)Ljava/lang/Object;
    //   684: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   687: getfield value : I
    //   690: istore #10
    //   692: aload #13
    //   694: getfield margin : I
    //   697: istore #9
    //   699: aload_1
    //   700: getfield targets : Ljava/util/List;
    //   703: iconst_0
    //   704: invokeinterface get : (I)Ljava/lang/Object;
    //   709: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   712: getfield value : I
    //   715: istore #5
    //   717: aload_1
    //   718: getfield margin : I
    //   721: istore #6
    //   723: aload_0
    //   724: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   727: iload #11
    //   729: iload #12
    //   731: iadd
    //   732: iload #4
    //   734: iload #7
    //   736: isub
    //   737: iload #10
    //   739: iload #9
    //   741: iadd
    //   742: iload #5
    //   744: iload #6
    //   746: isub
    //   747: fload_2
    //   748: iload #8
    //   750: invokespecial computeInsetRatio : ([IIIIIFI)V
    //   753: aload_0
    //   754: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   757: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   760: iconst_0
    //   761: iaload
    //   762: invokevirtual resolve : (I)V
    //   765: aload_0
    //   766: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   769: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   772: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   775: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   778: iconst_1
    //   779: iaload
    //   780: invokevirtual resolve : (I)V
    //   783: goto -> 787
    //   786: return
    //   787: aload_0
    //   788: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   791: getfield readyToSolve : Z
    //   794: ifeq -> 998
    //   797: aload_0
    //   798: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   801: getfield readyToSolve : Z
    //   804: ifeq -> 998
    //   807: aload #13
    //   809: getfield readyToSolve : Z
    //   812: ifeq -> 998
    //   815: aload_1
    //   816: getfield readyToSolve : Z
    //   819: ifne -> 825
    //   822: goto -> 998
    //   825: aload_0
    //   826: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   829: getfield targets : Ljava/util/List;
    //   832: iconst_0
    //   833: invokeinterface get : (I)Ljava/lang/Object;
    //   838: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   841: getfield value : I
    //   844: istore #4
    //   846: aload_0
    //   847: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   850: getfield margin : I
    //   853: istore #11
    //   855: aload_0
    //   856: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   859: getfield targets : Ljava/util/List;
    //   862: iconst_0
    //   863: invokeinterface get : (I)Ljava/lang/Object;
    //   868: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   871: getfield value : I
    //   874: istore #5
    //   876: aload_0
    //   877: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   880: getfield margin : I
    //   883: istore #9
    //   885: aload #13
    //   887: getfield targets : Ljava/util/List;
    //   890: iconst_0
    //   891: invokeinterface get : (I)Ljava/lang/Object;
    //   896: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   899: getfield value : I
    //   902: istore #12
    //   904: aload #13
    //   906: getfield margin : I
    //   909: istore #7
    //   911: aload_1
    //   912: getfield targets : Ljava/util/List;
    //   915: iconst_0
    //   916: invokeinterface get : (I)Ljava/lang/Object;
    //   921: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   924: getfield value : I
    //   927: istore #6
    //   929: aload_1
    //   930: getfield margin : I
    //   933: istore #10
    //   935: aload_0
    //   936: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   939: iload #4
    //   941: iload #11
    //   943: iadd
    //   944: iload #5
    //   946: iload #9
    //   948: isub
    //   949: iload #12
    //   951: iload #7
    //   953: iadd
    //   954: iload #6
    //   956: iload #10
    //   958: isub
    //   959: fload_2
    //   960: iload #8
    //   962: invokespecial computeInsetRatio : ([IIIIIFI)V
    //   965: aload_0
    //   966: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   969: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   972: iconst_0
    //   973: iaload
    //   974: invokevirtual resolve : (I)V
    //   977: aload_0
    //   978: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   981: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   984: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   987: getstatic androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun.tempDimensions : [I
    //   990: iconst_1
    //   991: iaload
    //   992: invokevirtual resolve : (I)V
    //   995: goto -> 1593
    //   998: return
    //   999: iload #4
    //   1001: ifeq -> 1273
    //   1004: iload #6
    //   1006: ifeq -> 1273
    //   1009: aload_0
    //   1010: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1013: getfield readyToSolve : Z
    //   1016: ifeq -> 1272
    //   1019: aload_0
    //   1020: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1023: getfield readyToSolve : Z
    //   1026: ifne -> 1032
    //   1029: goto -> 1272
    //   1032: aload_0
    //   1033: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1036: invokevirtual getDimensionRatio : ()F
    //   1039: fstore_2
    //   1040: aload_0
    //   1041: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1044: getfield targets : Ljava/util/List;
    //   1047: iconst_0
    //   1048: invokeinterface get : (I)Ljava/lang/Object;
    //   1053: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1056: getfield value : I
    //   1059: aload_0
    //   1060: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1063: getfield margin : I
    //   1066: iadd
    //   1067: istore #5
    //   1069: aload_0
    //   1070: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1073: getfield targets : Ljava/util/List;
    //   1076: iconst_0
    //   1077: invokeinterface get : (I)Ljava/lang/Object;
    //   1082: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1085: getfield value : I
    //   1088: aload_0
    //   1089: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1092: getfield margin : I
    //   1095: isub
    //   1096: istore #4
    //   1098: iload #8
    //   1100: iconst_m1
    //   1101: if_icmpeq -> 1195
    //   1104: iload #8
    //   1106: ifeq -> 1195
    //   1109: iload #8
    //   1111: iconst_1
    //   1112: if_icmpeq -> 1118
    //   1115: goto -> 1593
    //   1118: aload_0
    //   1119: iload #4
    //   1121: iload #5
    //   1123: isub
    //   1124: iconst_0
    //   1125: invokevirtual getLimitedDimension : (II)I
    //   1128: istore #4
    //   1130: iload #4
    //   1132: i2f
    //   1133: fload_2
    //   1134: fdiv
    //   1135: ldc 0.5
    //   1137: fadd
    //   1138: f2i
    //   1139: istore #6
    //   1141: aload_0
    //   1142: iload #6
    //   1144: iconst_1
    //   1145: invokevirtual getLimitedDimension : (II)I
    //   1148: istore #5
    //   1150: iload #6
    //   1152: iload #5
    //   1154: if_icmpeq -> 1168
    //   1157: iload #5
    //   1159: i2f
    //   1160: fload_2
    //   1161: fmul
    //   1162: ldc 0.5
    //   1164: fadd
    //   1165: f2i
    //   1166: istore #4
    //   1168: aload_0
    //   1169: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1172: iload #4
    //   1174: invokevirtual resolve : (I)V
    //   1177: aload_0
    //   1178: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1181: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1184: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1187: iload #5
    //   1189: invokevirtual resolve : (I)V
    //   1192: goto -> 1593
    //   1195: aload_0
    //   1196: iload #4
    //   1198: iload #5
    //   1200: isub
    //   1201: iconst_0
    //   1202: invokevirtual getLimitedDimension : (II)I
    //   1205: istore #4
    //   1207: iload #4
    //   1209: i2f
    //   1210: fload_2
    //   1211: fmul
    //   1212: ldc 0.5
    //   1214: fadd
    //   1215: f2i
    //   1216: istore #6
    //   1218: aload_0
    //   1219: iload #6
    //   1221: iconst_1
    //   1222: invokevirtual getLimitedDimension : (II)I
    //   1225: istore #5
    //   1227: iload #6
    //   1229: iload #5
    //   1231: if_icmpeq -> 1245
    //   1234: iload #5
    //   1236: i2f
    //   1237: fload_2
    //   1238: fdiv
    //   1239: ldc 0.5
    //   1241: fadd
    //   1242: f2i
    //   1243: istore #4
    //   1245: aload_0
    //   1246: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1249: iload #4
    //   1251: invokevirtual resolve : (I)V
    //   1254: aload_0
    //   1255: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1258: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1261: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1264: iload #5
    //   1266: invokevirtual resolve : (I)V
    //   1269: goto -> 1593
    //   1272: return
    //   1273: iload #5
    //   1275: ifeq -> 1593
    //   1278: iload #7
    //   1280: ifeq -> 1593
    //   1283: aload #13
    //   1285: getfield readyToSolve : Z
    //   1288: ifeq -> 1531
    //   1291: aload_1
    //   1292: getfield readyToSolve : Z
    //   1295: ifne -> 1301
    //   1298: goto -> 1531
    //   1301: aload_0
    //   1302: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1305: invokevirtual getDimensionRatio : ()F
    //   1308: fstore_2
    //   1309: aload #13
    //   1311: getfield targets : Ljava/util/List;
    //   1314: iconst_0
    //   1315: invokeinterface get : (I)Ljava/lang/Object;
    //   1320: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1323: getfield value : I
    //   1326: aload #13
    //   1328: getfield margin : I
    //   1331: iadd
    //   1332: istore #5
    //   1334: aload_1
    //   1335: getfield targets : Ljava/util/List;
    //   1338: iconst_0
    //   1339: invokeinterface get : (I)Ljava/lang/Object;
    //   1344: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1347: getfield value : I
    //   1350: aload_1
    //   1351: getfield margin : I
    //   1354: isub
    //   1355: istore #4
    //   1357: iload #8
    //   1359: iconst_m1
    //   1360: if_icmpeq -> 1454
    //   1363: iload #8
    //   1365: ifeq -> 1377
    //   1368: iload #8
    //   1370: iconst_1
    //   1371: if_icmpeq -> 1454
    //   1374: goto -> 1593
    //   1377: aload_0
    //   1378: iload #4
    //   1380: iload #5
    //   1382: isub
    //   1383: iconst_1
    //   1384: invokevirtual getLimitedDimension : (II)I
    //   1387: istore #4
    //   1389: iload #4
    //   1391: i2f
    //   1392: fload_2
    //   1393: fmul
    //   1394: ldc 0.5
    //   1396: fadd
    //   1397: f2i
    //   1398: istore #6
    //   1400: aload_0
    //   1401: iload #6
    //   1403: iconst_0
    //   1404: invokevirtual getLimitedDimension : (II)I
    //   1407: istore #5
    //   1409: iload #6
    //   1411: iload #5
    //   1413: if_icmpeq -> 1427
    //   1416: iload #5
    //   1418: i2f
    //   1419: fload_2
    //   1420: fdiv
    //   1421: ldc 0.5
    //   1423: fadd
    //   1424: f2i
    //   1425: istore #4
    //   1427: aload_0
    //   1428: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1431: iload #5
    //   1433: invokevirtual resolve : (I)V
    //   1436: aload_0
    //   1437: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1440: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1443: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1446: iload #4
    //   1448: invokevirtual resolve : (I)V
    //   1451: goto -> 1593
    //   1454: aload_0
    //   1455: iload #4
    //   1457: iload #5
    //   1459: isub
    //   1460: iconst_1
    //   1461: invokevirtual getLimitedDimension : (II)I
    //   1464: istore #4
    //   1466: iload #4
    //   1468: i2f
    //   1469: fload_2
    //   1470: fdiv
    //   1471: ldc 0.5
    //   1473: fadd
    //   1474: f2i
    //   1475: istore #6
    //   1477: aload_0
    //   1478: iload #6
    //   1480: iconst_0
    //   1481: invokevirtual getLimitedDimension : (II)I
    //   1484: istore #5
    //   1486: iload #6
    //   1488: iload #5
    //   1490: if_icmpeq -> 1504
    //   1493: iload #5
    //   1495: i2f
    //   1496: fload_2
    //   1497: fmul
    //   1498: ldc 0.5
    //   1500: fadd
    //   1501: f2i
    //   1502: istore #4
    //   1504: aload_0
    //   1505: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1508: iload #5
    //   1510: invokevirtual resolve : (I)V
    //   1513: aload_0
    //   1514: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1517: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1520: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1523: iload #4
    //   1525: invokevirtual resolve : (I)V
    //   1528: goto -> 1593
    //   1531: return
    //   1532: aload_0
    //   1533: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1536: invokevirtual getParent : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1539: astore_1
    //   1540: aload_1
    //   1541: ifnull -> 1593
    //   1544: aload_1
    //   1545: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1548: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1551: getfield resolved : Z
    //   1554: ifeq -> 1593
    //   1557: aload_0
    //   1558: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1561: getfield mMatchConstraintPercentWidth : F
    //   1564: fstore_2
    //   1565: aload_1
    //   1566: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1569: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1572: getfield value : I
    //   1575: i2f
    //   1576: fload_2
    //   1577: fmul
    //   1578: ldc 0.5
    //   1580: fadd
    //   1581: f2i
    //   1582: istore #4
    //   1584: aload_0
    //   1585: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1588: iload #4
    //   1590: invokevirtual resolve : (I)V
    //   1593: aload_0
    //   1594: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1597: getfield readyToSolve : Z
    //   1600: ifeq -> 2133
    //   1603: aload_0
    //   1604: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1607: getfield readyToSolve : Z
    //   1610: ifne -> 1616
    //   1613: goto -> 2133
    //   1616: aload_0
    //   1617: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1620: getfield resolved : Z
    //   1623: ifeq -> 1647
    //   1626: aload_0
    //   1627: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1630: getfield resolved : Z
    //   1633: ifeq -> 1647
    //   1636: aload_0
    //   1637: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1640: getfield resolved : Z
    //   1643: ifeq -> 1647
    //   1646: return
    //   1647: aload_0
    //   1648: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1651: getfield resolved : Z
    //   1654: ifne -> 1782
    //   1657: aload_0
    //   1658: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1661: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1664: if_acmpne -> 1782
    //   1667: aload_0
    //   1668: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1671: getfield mMatchConstraintDefaultWidth : I
    //   1674: ifne -> 1782
    //   1677: aload_0
    //   1678: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1681: invokevirtual isInHorizontalChain : ()Z
    //   1684: ifne -> 1782
    //   1687: aload_0
    //   1688: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1691: getfield targets : Ljava/util/List;
    //   1694: iconst_0
    //   1695: invokeinterface get : (I)Ljava/lang/Object;
    //   1700: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1703: astore_1
    //   1704: aload_0
    //   1705: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1708: getfield targets : Ljava/util/List;
    //   1711: iconst_0
    //   1712: invokeinterface get : (I)Ljava/lang/Object;
    //   1717: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1720: astore #13
    //   1722: aload_1
    //   1723: getfield value : I
    //   1726: aload_0
    //   1727: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1730: getfield margin : I
    //   1733: iadd
    //   1734: istore #4
    //   1736: aload #13
    //   1738: getfield value : I
    //   1741: aload_0
    //   1742: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1745: getfield margin : I
    //   1748: iadd
    //   1749: istore #5
    //   1751: aload_0
    //   1752: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1755: iload #4
    //   1757: invokevirtual resolve : (I)V
    //   1760: aload_0
    //   1761: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1764: iload #5
    //   1766: invokevirtual resolve : (I)V
    //   1769: aload_0
    //   1770: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1773: iload #5
    //   1775: iload #4
    //   1777: isub
    //   1778: invokevirtual resolve : (I)V
    //   1781: return
    //   1782: aload_0
    //   1783: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1786: getfield resolved : Z
    //   1789: ifne -> 1971
    //   1792: aload_0
    //   1793: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1796: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1799: if_acmpne -> 1971
    //   1802: aload_0
    //   1803: getfield matchConstraintsType : I
    //   1806: iconst_1
    //   1807: if_icmpne -> 1971
    //   1810: aload_0
    //   1811: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1814: getfield targets : Ljava/util/List;
    //   1817: invokeinterface size : ()I
    //   1822: ifle -> 1971
    //   1825: aload_0
    //   1826: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1829: getfield targets : Ljava/util/List;
    //   1832: invokeinterface size : ()I
    //   1837: ifle -> 1971
    //   1840: aload_0
    //   1841: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1844: getfield targets : Ljava/util/List;
    //   1847: iconst_0
    //   1848: invokeinterface get : (I)Ljava/lang/Object;
    //   1853: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1856: astore_1
    //   1857: aload_0
    //   1858: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1861: getfield targets : Ljava/util/List;
    //   1864: iconst_0
    //   1865: invokeinterface get : (I)Ljava/lang/Object;
    //   1870: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1873: astore #13
    //   1875: aload_1
    //   1876: getfield value : I
    //   1879: istore #4
    //   1881: aload_0
    //   1882: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1885: getfield margin : I
    //   1888: istore #5
    //   1890: aload #13
    //   1892: getfield value : I
    //   1895: aload_0
    //   1896: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1899: getfield margin : I
    //   1902: iadd
    //   1903: iload #4
    //   1905: iload #5
    //   1907: iadd
    //   1908: isub
    //   1909: aload_0
    //   1910: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1913: getfield wrapValue : I
    //   1916: invokestatic min : (II)I
    //   1919: istore #4
    //   1921: aload_0
    //   1922: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1925: getfield mMatchConstraintMaxWidth : I
    //   1928: istore #6
    //   1930: aload_0
    //   1931: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1934: getfield mMatchConstraintMinWidth : I
    //   1937: iload #4
    //   1939: invokestatic max : (II)I
    //   1942: istore #5
    //   1944: iload #5
    //   1946: istore #4
    //   1948: iload #6
    //   1950: ifle -> 1962
    //   1953: iload #6
    //   1955: iload #5
    //   1957: invokestatic min : (II)I
    //   1960: istore #4
    //   1962: aload_0
    //   1963: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1966: iload #4
    //   1968: invokevirtual resolve : (I)V
    //   1971: aload_0
    //   1972: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1975: getfield resolved : Z
    //   1978: ifne -> 1982
    //   1981: return
    //   1982: aload_0
    //   1983: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1986: getfield targets : Ljava/util/List;
    //   1989: iconst_0
    //   1990: invokeinterface get : (I)Ljava/lang/Object;
    //   1995: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   1998: astore #13
    //   2000: aload_0
    //   2001: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2004: getfield targets : Ljava/util/List;
    //   2007: iconst_0
    //   2008: invokeinterface get : (I)Ljava/lang/Object;
    //   2013: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   2016: astore_1
    //   2017: aload #13
    //   2019: getfield value : I
    //   2022: aload_0
    //   2023: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2026: getfield margin : I
    //   2029: iadd
    //   2030: istore #5
    //   2032: aload_1
    //   2033: getfield value : I
    //   2036: aload_0
    //   2037: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2040: getfield margin : I
    //   2043: iadd
    //   2044: istore #4
    //   2046: aload_0
    //   2047: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2050: invokevirtual getHorizontalBiasPercent : ()F
    //   2053: fstore_2
    //   2054: aload #13
    //   2056: aload_1
    //   2057: if_acmpne -> 2076
    //   2060: aload #13
    //   2062: getfield value : I
    //   2065: istore #5
    //   2067: aload_1
    //   2068: getfield value : I
    //   2071: istore #4
    //   2073: ldc 0.5
    //   2075: fstore_2
    //   2076: aload_0
    //   2077: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   2080: getfield value : I
    //   2083: istore #6
    //   2085: aload_0
    //   2086: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2089: iload #5
    //   2091: i2f
    //   2092: ldc 0.5
    //   2094: fadd
    //   2095: iload #4
    //   2097: iload #5
    //   2099: isub
    //   2100: iload #6
    //   2102: isub
    //   2103: i2f
    //   2104: fload_2
    //   2105: fmul
    //   2106: fadd
    //   2107: f2i
    //   2108: invokevirtual resolve : (I)V
    //   2111: aload_0
    //   2112: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2115: aload_0
    //   2116: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2119: getfield value : I
    //   2122: aload_0
    //   2123: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   2126: getfield value : I
    //   2129: iadd
    //   2130: invokevirtual resolve : (I)V
    //   2133: return
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\HorizontalWidgetRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */