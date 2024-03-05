package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun extends WidgetRun {
  private int chainStyle;
  
  ArrayList<WidgetRun> widgets = new ArrayList<WidgetRun>();
  
  public ChainRun(ConstraintWidget paramConstraintWidget, int paramInt) {
    super(paramConstraintWidget);
    this.orientation = paramInt;
    build();
  }
  
  private void build() {
    int i;
    ConstraintWidget constraintWidget2 = this.widget;
    ConstraintWidget constraintWidget1;
    for (constraintWidget1 = constraintWidget2.getPreviousChainMember(this.orientation); constraintWidget1 != null; constraintWidget1 = constraintWidget) {
      ConstraintWidget constraintWidget = constraintWidget1.getPreviousChainMember(this.orientation);
      constraintWidget2 = constraintWidget1;
    } 
    this.widget = constraintWidget2;
    this.widgets.add(constraintWidget2.getRun(this.orientation));
    for (constraintWidget1 = constraintWidget2.getNextChainMember(this.orientation); constraintWidget1 != null; constraintWidget1 = constraintWidget1.getNextChainMember(this.orientation))
      this.widgets.add(constraintWidget1.getRun(this.orientation)); 
    for (WidgetRun widgetRun : this.widgets) {
      if (this.orientation == 0) {
        widgetRun.widget.horizontalChainRun = this;
        continue;
      } 
      if (this.orientation == 1)
        widgetRun.widget.verticalChainRun = this; 
    } 
    if (this.orientation == 0 && ((ConstraintWidgetContainer)this.widget.getParent()).isRtl()) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i && this.widgets.size() > 1) {
      ArrayList<WidgetRun> arrayList = this.widgets;
      this.widget = ((WidgetRun)arrayList.get(arrayList.size() - 1)).widget;
    } 
    if (this.orientation == 0) {
      i = this.widget.getHorizontalChainStyle();
    } else {
      i = this.widget.getVerticalChainStyle();
    } 
    this.chainStyle = i;
  }
  
  private ConstraintWidget getFirstVisibleWidget() {
    for (byte b = 0; b < this.widgets.size(); b++) {
      WidgetRun widgetRun = this.widgets.get(b);
      if (widgetRun.widget.getVisibility() != 8)
        return widgetRun.widget; 
    } 
    return null;
  }
  
  private ConstraintWidget getLastVisibleWidget() {
    for (int i = this.widgets.size() - 1; i >= 0; i--) {
      WidgetRun widgetRun = this.widgets.get(i);
      if (widgetRun.widget.getVisibility() != 8)
        return widgetRun.widget; 
    } 
    return null;
  }
  
  void apply() {
    DependencyNode dependencyNode;
    Iterator<WidgetRun> iterator = this.widgets.iterator();
    while (iterator.hasNext())
      ((WidgetRun)iterator.next()).apply(); 
    int i = this.widgets.size();
    if (i < 1)
      return; 
    ConstraintWidget constraintWidget2 = ((WidgetRun)this.widgets.get(0)).widget;
    ConstraintWidget constraintWidget1 = ((WidgetRun)this.widgets.get(i - 1)).widget;
    if (this.orientation == 0) {
      ConstraintAnchor constraintAnchor2 = constraintWidget2.mLeft;
      ConstraintAnchor constraintAnchor1 = constraintWidget1.mRight;
      dependencyNode = getTarget(constraintAnchor2, 0);
      i = constraintAnchor2.getMargin();
      ConstraintWidget constraintWidget = getFirstVisibleWidget();
      if (constraintWidget != null)
        i = constraintWidget.mLeft.getMargin(); 
      if (dependencyNode != null)
        addTarget(this.start, dependencyNode, i); 
      dependencyNode = getTarget(constraintAnchor1, 0);
      i = constraintAnchor1.getMargin();
      constraintWidget1 = getLastVisibleWidget();
      if (constraintWidget1 != null)
        i = constraintWidget1.mRight.getMargin(); 
      if (dependencyNode != null)
        addTarget(this.end, dependencyNode, -i); 
    } else {
      ConstraintAnchor constraintAnchor2 = ((ConstraintWidget)dependencyNode).mTop;
      ConstraintAnchor constraintAnchor1 = constraintWidget1.mBottom;
      dependencyNode = getTarget(constraintAnchor2, 1);
      i = constraintAnchor2.getMargin();
      ConstraintWidget constraintWidget4 = getFirstVisibleWidget();
      if (constraintWidget4 != null)
        i = constraintWidget4.mTop.getMargin(); 
      if (dependencyNode != null)
        addTarget(this.start, dependencyNode, i); 
      dependencyNode = getTarget(constraintAnchor1, 1);
      i = constraintAnchor1.getMargin();
      ConstraintWidget constraintWidget3 = getLastVisibleWidget();
      if (constraintWidget3 != null)
        i = constraintWidget3.mBottom.getMargin(); 
      if (dependencyNode != null)
        addTarget(this.end, dependencyNode, -i); 
    } 
    this.start.updateDelegate = this;
    this.end.updateDelegate = this;
  }
  
  public void applyToWidget() {
    for (byte b = 0; b < this.widgets.size(); b++)
      ((WidgetRun)this.widgets.get(b)).applyToWidget(); 
  }
  
  void clear() {
    this.runGroup = null;
    Iterator<WidgetRun> iterator = this.widgets.iterator();
    while (iterator.hasNext())
      ((WidgetRun)iterator.next()).clear(); 
  }
  
  public long getWrapDimension() {
    int i = this.widgets.size();
    long l = 0L;
    for (byte b = 0; b < i; b++) {
      WidgetRun widgetRun = this.widgets.get(b);
      l = l + widgetRun.start.margin + widgetRun.getWrapDimension() + widgetRun.end.margin;
    } 
    return l;
  }
  
  void reset() {
    this.start.resolved = false;
    this.end.resolved = false;
  }
  
  boolean supportsWrapComputation() {
    int i = this.widgets.size();
    for (byte b = 0; b < i; b++) {
      if (!((WidgetRun)this.widgets.get(b)).supportsWrapComputation())
        return false; 
    } 
    return true;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("ChainRun ");
    if (this.orientation == 0) {
      str = "horizontal : ";
    } else {
      str = "vertical : ";
    } 
    stringBuilder.append(str);
    String str = stringBuilder.toString();
    for (WidgetRun widgetRun : this.widgets) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append("<");
      str = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(widgetRun);
      String str1 = stringBuilder2.toString();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str1);
      stringBuilder1.append("> ");
      str = stringBuilder1.toString();
    } 
    return str;
  }
  
  public void update(Dependency paramDependency) {
    // Byte code:
    //   0: aload_0
    //   1: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   4: getfield resolved : Z
    //   7: ifeq -> 2511
    //   10: aload_0
    //   11: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   14: getfield resolved : Z
    //   17: ifne -> 23
    //   20: goto -> 2511
    //   23: aload_0
    //   24: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   27: invokevirtual getParent : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   30: astore_1
    //   31: aload_1
    //   32: ifnull -> 54
    //   35: aload_1
    //   36: instanceof androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   39: ifeq -> 54
    //   42: aload_1
    //   43: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidgetContainer
    //   46: invokevirtual isRtl : ()Z
    //   49: istore #21
    //   51: goto -> 57
    //   54: iconst_0
    //   55: istore #21
    //   57: aload_0
    //   58: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   61: getfield value : I
    //   64: aload_0
    //   65: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   68: getfield value : I
    //   71: isub
    //   72: istore #20
    //   74: aload_0
    //   75: getfield widgets : Ljava/util/ArrayList;
    //   78: invokevirtual size : ()I
    //   81: istore #19
    //   83: iconst_0
    //   84: istore #5
    //   86: iconst_m1
    //   87: istore #6
    //   89: iload #5
    //   91: iload #19
    //   93: if_icmpge -> 129
    //   96: iload #5
    //   98: istore #14
    //   100: aload_0
    //   101: getfield widgets : Ljava/util/ArrayList;
    //   104: iload #5
    //   106: invokevirtual get : (I)Ljava/lang/Object;
    //   109: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   112: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   115: invokevirtual getVisibility : ()I
    //   118: bipush #8
    //   120: if_icmpne -> 132
    //   123: iinc #5, 1
    //   126: goto -> 86
    //   129: iconst_m1
    //   130: istore #14
    //   132: iload #19
    //   134: iconst_1
    //   135: isub
    //   136: istore #18
    //   138: iload #18
    //   140: istore #5
    //   142: iload #6
    //   144: istore #15
    //   146: iload #5
    //   148: iflt -> 184
    //   151: aload_0
    //   152: getfield widgets : Ljava/util/ArrayList;
    //   155: iload #5
    //   157: invokevirtual get : (I)Ljava/lang/Object;
    //   160: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   163: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   166: invokevirtual getVisibility : ()I
    //   169: bipush #8
    //   171: if_icmpne -> 180
    //   174: iinc #5, -1
    //   177: goto -> 142
    //   180: iload #5
    //   182: istore #15
    //   184: iconst_0
    //   185: istore #9
    //   187: iload #9
    //   189: iconst_2
    //   190: if_icmpge -> 648
    //   193: iconst_0
    //   194: istore #10
    //   196: iconst_0
    //   197: istore #7
    //   199: iconst_0
    //   200: istore #5
    //   202: iconst_0
    //   203: istore #8
    //   205: fconst_0
    //   206: fstore_2
    //   207: iload #10
    //   209: iload #19
    //   211: if_icmpge -> 616
    //   214: aload_0
    //   215: getfield widgets : Ljava/util/ArrayList;
    //   218: iload #10
    //   220: invokevirtual get : (I)Ljava/lang/Object;
    //   223: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   226: astore_1
    //   227: aload_1
    //   228: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   231: invokevirtual getVisibility : ()I
    //   234: bipush #8
    //   236: if_icmpne -> 250
    //   239: iload #7
    //   241: istore #6
    //   243: iload #5
    //   245: istore #11
    //   247: goto -> 602
    //   250: iload #8
    //   252: iconst_1
    //   253: iadd
    //   254: istore #16
    //   256: iload #7
    //   258: istore #6
    //   260: iload #10
    //   262: ifle -> 288
    //   265: iload #7
    //   267: istore #6
    //   269: iload #10
    //   271: iload #14
    //   273: if_icmplt -> 288
    //   276: iload #7
    //   278: aload_1
    //   279: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   282: getfield margin : I
    //   285: iadd
    //   286: istore #6
    //   288: aload_1
    //   289: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   292: getfield value : I
    //   295: istore #11
    //   297: aload_1
    //   298: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   301: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   304: if_acmpeq -> 313
    //   307: iconst_1
    //   308: istore #8
    //   310: goto -> 316
    //   313: iconst_0
    //   314: istore #8
    //   316: iload #8
    //   318: ifeq -> 394
    //   321: aload_0
    //   322: getfield orientation : I
    //   325: ifne -> 345
    //   328: aload_1
    //   329: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   332: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   335: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   338: getfield resolved : Z
    //   341: ifne -> 345
    //   344: return
    //   345: iload #8
    //   347: istore #13
    //   349: iload #11
    //   351: istore #12
    //   353: iload #5
    //   355: istore #7
    //   357: aload_0
    //   358: getfield orientation : I
    //   361: iconst_1
    //   362: if_icmpne -> 470
    //   365: iload #8
    //   367: istore #13
    //   369: iload #11
    //   371: istore #12
    //   373: iload #5
    //   375: istore #7
    //   377: aload_1
    //   378: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   381: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   384: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   387: getfield resolved : Z
    //   390: ifne -> 470
    //   393: return
    //   394: aload_1
    //   395: getfield matchConstraintsType : I
    //   398: iconst_1
    //   399: if_icmpne -> 433
    //   402: iload #9
    //   404: ifne -> 433
    //   407: aload_1
    //   408: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   411: getfield wrapValue : I
    //   414: istore #7
    //   416: iload #5
    //   418: iconst_1
    //   419: iadd
    //   420: istore #8
    //   422: iload #7
    //   424: istore #5
    //   426: iload #8
    //   428: istore #7
    //   430: goto -> 463
    //   433: iload #8
    //   435: istore #13
    //   437: iload #11
    //   439: istore #12
    //   441: iload #5
    //   443: istore #7
    //   445: aload_1
    //   446: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   449: getfield resolved : Z
    //   452: ifeq -> 470
    //   455: iload #5
    //   457: istore #7
    //   459: iload #11
    //   461: istore #5
    //   463: iconst_1
    //   464: istore #13
    //   466: iload #5
    //   468: istore #12
    //   470: iload #13
    //   472: ifne -> 528
    //   475: iload #7
    //   477: iconst_1
    //   478: iadd
    //   479: istore #8
    //   481: aload_1
    //   482: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   485: getfield mWeight : [F
    //   488: aload_0
    //   489: getfield orientation : I
    //   492: faload
    //   493: fstore #4
    //   495: iload #6
    //   497: istore #5
    //   499: iload #8
    //   501: istore #7
    //   503: fload_2
    //   504: fstore_3
    //   505: fload #4
    //   507: fconst_0
    //   508: fcmpl
    //   509: iflt -> 537
    //   512: fload_2
    //   513: fload #4
    //   515: fadd
    //   516: fstore_3
    //   517: iload #6
    //   519: istore #5
    //   521: iload #8
    //   523: istore #7
    //   525: goto -> 537
    //   528: iload #6
    //   530: iload #12
    //   532: iadd
    //   533: istore #5
    //   535: fload_2
    //   536: fstore_3
    //   537: iload #5
    //   539: istore #6
    //   541: iload #7
    //   543: istore #11
    //   545: iload #16
    //   547: istore #8
    //   549: fload_3
    //   550: fstore_2
    //   551: iload #10
    //   553: iload #18
    //   555: if_icmpge -> 602
    //   558: iload #5
    //   560: istore #6
    //   562: iload #7
    //   564: istore #11
    //   566: iload #16
    //   568: istore #8
    //   570: fload_3
    //   571: fstore_2
    //   572: iload #10
    //   574: iload #15
    //   576: if_icmpge -> 602
    //   579: iload #5
    //   581: aload_1
    //   582: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   585: getfield margin : I
    //   588: ineg
    //   589: iadd
    //   590: istore #6
    //   592: fload_3
    //   593: fstore_2
    //   594: iload #16
    //   596: istore #8
    //   598: iload #7
    //   600: istore #11
    //   602: iinc #10, 1
    //   605: iload #6
    //   607: istore #7
    //   609: iload #11
    //   611: istore #5
    //   613: goto -> 207
    //   616: iload #7
    //   618: iload #20
    //   620: if_icmplt -> 637
    //   623: iload #5
    //   625: ifne -> 631
    //   628: goto -> 637
    //   631: iinc #9, 1
    //   634: goto -> 187
    //   637: iload #8
    //   639: istore #16
    //   641: iload #5
    //   643: istore #6
    //   645: goto -> 659
    //   648: iconst_0
    //   649: istore #16
    //   651: iconst_0
    //   652: istore #7
    //   654: iconst_0
    //   655: istore #6
    //   657: fconst_0
    //   658: fstore_2
    //   659: aload_0
    //   660: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   663: getfield value : I
    //   666: istore #8
    //   668: iload #21
    //   670: ifeq -> 682
    //   673: aload_0
    //   674: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   677: getfield value : I
    //   680: istore #8
    //   682: iload #8
    //   684: istore #5
    //   686: iload #7
    //   688: iload #20
    //   690: if_icmple -> 735
    //   693: iload #21
    //   695: ifeq -> 718
    //   698: iload #8
    //   700: iload #7
    //   702: iload #20
    //   704: isub
    //   705: i2f
    //   706: fconst_2
    //   707: fdiv
    //   708: ldc 0.5
    //   710: fadd
    //   711: f2i
    //   712: iadd
    //   713: istore #5
    //   715: goto -> 735
    //   718: iload #8
    //   720: iload #7
    //   722: iload #20
    //   724: isub
    //   725: i2f
    //   726: fconst_2
    //   727: fdiv
    //   728: ldc 0.5
    //   730: fadd
    //   731: f2i
    //   732: isub
    //   733: istore #5
    //   735: iload #6
    //   737: ifle -> 1293
    //   740: iload #20
    //   742: iload #7
    //   744: isub
    //   745: i2f
    //   746: fstore_3
    //   747: fload_3
    //   748: iload #6
    //   750: i2f
    //   751: fdiv
    //   752: ldc 0.5
    //   754: fadd
    //   755: f2i
    //   756: istore #11
    //   758: iconst_0
    //   759: istore #17
    //   761: iconst_0
    //   762: istore #8
    //   764: iload #7
    //   766: istore #9
    //   768: iload #5
    //   770: istore #10
    //   772: iload #17
    //   774: iload #19
    //   776: if_icmpge -> 1096
    //   779: aload_0
    //   780: getfield widgets : Ljava/util/ArrayList;
    //   783: iload #17
    //   785: invokevirtual get : (I)Ljava/lang/Object;
    //   788: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   791: astore_1
    //   792: aload_1
    //   793: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   796: invokevirtual getVisibility : ()I
    //   799: bipush #8
    //   801: if_icmpne -> 807
    //   804: goto -> 1082
    //   807: aload_1
    //   808: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   811: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   814: if_acmpne -> 1082
    //   817: aload_1
    //   818: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   821: getfield resolved : Z
    //   824: ifne -> 1082
    //   827: fload_2
    //   828: fconst_0
    //   829: fcmpl
    //   830: ifle -> 858
    //   833: aload_1
    //   834: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   837: getfield mWeight : [F
    //   840: aload_0
    //   841: getfield orientation : I
    //   844: faload
    //   845: fload_3
    //   846: fmul
    //   847: fload_2
    //   848: fdiv
    //   849: ldc 0.5
    //   851: fadd
    //   852: f2i
    //   853: istore #5
    //   855: goto -> 862
    //   858: iload #11
    //   860: istore #5
    //   862: aload_0
    //   863: getfield orientation : I
    //   866: ifne -> 971
    //   869: aload_1
    //   870: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   873: getfield mMatchConstraintMaxWidth : I
    //   876: istore #13
    //   878: aload_1
    //   879: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   882: getfield mMatchConstraintMinWidth : I
    //   885: istore #12
    //   887: aload_1
    //   888: getfield matchConstraintsType : I
    //   891: iconst_1
    //   892: if_icmpne -> 912
    //   895: iload #5
    //   897: aload_1
    //   898: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   901: getfield wrapValue : I
    //   904: invokestatic min : (II)I
    //   907: istore #7
    //   909: goto -> 916
    //   912: iload #5
    //   914: istore #7
    //   916: iload #12
    //   918: iload #7
    //   920: invokestatic max : (II)I
    //   923: istore #7
    //   925: iload #7
    //   927: istore #12
    //   929: iload #13
    //   931: ifle -> 943
    //   934: iload #13
    //   936: iload #7
    //   938: invokestatic min : (II)I
    //   941: istore #12
    //   943: iload #5
    //   945: istore #13
    //   947: iload #8
    //   949: istore #7
    //   951: iload #12
    //   953: iload #5
    //   955: if_icmpeq -> 1070
    //   958: iload #8
    //   960: iconst_1
    //   961: iadd
    //   962: istore #7
    //   964: iload #12
    //   966: istore #13
    //   968: goto -> 1070
    //   971: aload_1
    //   972: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   975: getfield mMatchConstraintMaxHeight : I
    //   978: istore #13
    //   980: aload_1
    //   981: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   984: getfield mMatchConstraintMinHeight : I
    //   987: istore #12
    //   989: aload_1
    //   990: getfield matchConstraintsType : I
    //   993: iconst_1
    //   994: if_icmpne -> 1014
    //   997: iload #5
    //   999: aload_1
    //   1000: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1003: getfield wrapValue : I
    //   1006: invokestatic min : (II)I
    //   1009: istore #7
    //   1011: goto -> 1018
    //   1014: iload #5
    //   1016: istore #7
    //   1018: iload #12
    //   1020: iload #7
    //   1022: invokestatic max : (II)I
    //   1025: istore #7
    //   1027: iload #7
    //   1029: istore #12
    //   1031: iload #13
    //   1033: ifle -> 1045
    //   1036: iload #13
    //   1038: iload #7
    //   1040: invokestatic min : (II)I
    //   1043: istore #12
    //   1045: iload #5
    //   1047: istore #13
    //   1049: iload #8
    //   1051: istore #7
    //   1053: iload #12
    //   1055: iload #5
    //   1057: if_icmpeq -> 1070
    //   1060: iload #8
    //   1062: iconst_1
    //   1063: iadd
    //   1064: istore #7
    //   1066: iload #12
    //   1068: istore #13
    //   1070: aload_1
    //   1071: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1074: iload #13
    //   1076: invokevirtual resolve : (I)V
    //   1079: goto -> 1086
    //   1082: iload #8
    //   1084: istore #7
    //   1086: iinc #17, 1
    //   1089: iload #7
    //   1091: istore #8
    //   1093: goto -> 772
    //   1096: iload #8
    //   1098: ifle -> 1241
    //   1101: iload #6
    //   1103: iload #8
    //   1105: isub
    //   1106: istore #9
    //   1108: iconst_0
    //   1109: istore #6
    //   1111: iconst_0
    //   1112: istore #5
    //   1114: iload #6
    //   1116: iload #19
    //   1118: if_icmpge -> 1234
    //   1121: aload_0
    //   1122: getfield widgets : Ljava/util/ArrayList;
    //   1125: iload #6
    //   1127: invokevirtual get : (I)Ljava/lang/Object;
    //   1130: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   1133: astore_1
    //   1134: aload_1
    //   1135: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1138: invokevirtual getVisibility : ()I
    //   1141: bipush #8
    //   1143: if_icmpne -> 1149
    //   1146: goto -> 1228
    //   1149: iload #5
    //   1151: istore #7
    //   1153: iload #6
    //   1155: ifle -> 1181
    //   1158: iload #5
    //   1160: istore #7
    //   1162: iload #6
    //   1164: iload #14
    //   1166: if_icmplt -> 1181
    //   1169: iload #5
    //   1171: aload_1
    //   1172: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1175: getfield margin : I
    //   1178: iadd
    //   1179: istore #7
    //   1181: iload #7
    //   1183: aload_1
    //   1184: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1187: getfield value : I
    //   1190: iadd
    //   1191: istore #7
    //   1193: iload #7
    //   1195: istore #5
    //   1197: iload #6
    //   1199: iload #18
    //   1201: if_icmpge -> 1228
    //   1204: iload #7
    //   1206: istore #5
    //   1208: iload #6
    //   1210: iload #15
    //   1212: if_icmpge -> 1228
    //   1215: iload #7
    //   1217: aload_1
    //   1218: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1221: getfield margin : I
    //   1224: ineg
    //   1225: iadd
    //   1226: istore #5
    //   1228: iinc #6, 1
    //   1231: goto -> 1114
    //   1234: iload #9
    //   1236: istore #6
    //   1238: goto -> 1245
    //   1241: iload #9
    //   1243: istore #5
    //   1245: aload_0
    //   1246: getfield chainStyle : I
    //   1249: iconst_2
    //   1250: if_icmpne -> 1278
    //   1253: iload #8
    //   1255: ifne -> 1278
    //   1258: aload_0
    //   1259: iconst_0
    //   1260: putfield chainStyle : I
    //   1263: iload #5
    //   1265: istore #7
    //   1267: iload #6
    //   1269: istore #9
    //   1271: iload #10
    //   1273: istore #5
    //   1275: goto -> 1297
    //   1278: iload #5
    //   1280: istore #7
    //   1282: iload #6
    //   1284: istore #9
    //   1286: iload #10
    //   1288: istore #5
    //   1290: goto -> 1297
    //   1293: iload #6
    //   1295: istore #9
    //   1297: iload #7
    //   1299: iload #20
    //   1301: if_icmple -> 1309
    //   1304: aload_0
    //   1305: iconst_2
    //   1306: putfield chainStyle : I
    //   1309: iload #16
    //   1311: ifle -> 1331
    //   1314: iload #9
    //   1316: ifne -> 1331
    //   1319: iload #14
    //   1321: iload #15
    //   1323: if_icmpne -> 1331
    //   1326: aload_0
    //   1327: iconst_2
    //   1328: putfield chainStyle : I
    //   1331: aload_0
    //   1332: getfield chainStyle : I
    //   1335: istore #6
    //   1337: iload #6
    //   1339: iconst_1
    //   1340: if_icmpne -> 1754
    //   1343: iload #16
    //   1345: iconst_1
    //   1346: if_icmple -> 1364
    //   1349: iload #20
    //   1351: iload #7
    //   1353: isub
    //   1354: iload #16
    //   1356: iconst_1
    //   1357: isub
    //   1358: idiv
    //   1359: istore #6
    //   1361: goto -> 1385
    //   1364: iload #16
    //   1366: iconst_1
    //   1367: if_icmpne -> 1382
    //   1370: iload #20
    //   1372: iload #7
    //   1374: isub
    //   1375: iconst_2
    //   1376: idiv
    //   1377: istore #6
    //   1379: goto -> 1385
    //   1382: iconst_0
    //   1383: istore #6
    //   1385: iload #6
    //   1387: istore #8
    //   1389: iload #9
    //   1391: ifle -> 1397
    //   1394: iconst_0
    //   1395: istore #8
    //   1397: iconst_0
    //   1398: istore #6
    //   1400: iload #5
    //   1402: istore #7
    //   1404: iload #6
    //   1406: iload #19
    //   1408: if_icmpge -> 2511
    //   1411: iload #21
    //   1413: ifeq -> 1428
    //   1416: iload #19
    //   1418: iload #6
    //   1420: iconst_1
    //   1421: iadd
    //   1422: isub
    //   1423: istore #5
    //   1425: goto -> 1432
    //   1428: iload #6
    //   1430: istore #5
    //   1432: aload_0
    //   1433: getfield widgets : Ljava/util/ArrayList;
    //   1436: iload #5
    //   1438: invokevirtual get : (I)Ljava/lang/Object;
    //   1441: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   1444: astore_1
    //   1445: aload_1
    //   1446: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1449: invokevirtual getVisibility : ()I
    //   1452: bipush #8
    //   1454: if_icmpne -> 1482
    //   1457: aload_1
    //   1458: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1461: iload #7
    //   1463: invokevirtual resolve : (I)V
    //   1466: aload_1
    //   1467: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1470: iload #7
    //   1472: invokevirtual resolve : (I)V
    //   1475: iload #7
    //   1477: istore #5
    //   1479: goto -> 1744
    //   1482: iload #7
    //   1484: istore #5
    //   1486: iload #6
    //   1488: ifle -> 1513
    //   1491: iload #21
    //   1493: ifeq -> 1506
    //   1496: iload #7
    //   1498: iload #8
    //   1500: isub
    //   1501: istore #5
    //   1503: goto -> 1513
    //   1506: iload #7
    //   1508: iload #8
    //   1510: iadd
    //   1511: istore #5
    //   1513: iload #5
    //   1515: istore #7
    //   1517: iload #6
    //   1519: ifle -> 1565
    //   1522: iload #5
    //   1524: istore #7
    //   1526: iload #6
    //   1528: iload #14
    //   1530: if_icmplt -> 1565
    //   1533: iload #21
    //   1535: ifeq -> 1553
    //   1538: iload #5
    //   1540: aload_1
    //   1541: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1544: getfield margin : I
    //   1547: isub
    //   1548: istore #7
    //   1550: goto -> 1565
    //   1553: iload #5
    //   1555: aload_1
    //   1556: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1559: getfield margin : I
    //   1562: iadd
    //   1563: istore #7
    //   1565: iload #21
    //   1567: ifeq -> 1582
    //   1570: aload_1
    //   1571: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1574: iload #7
    //   1576: invokevirtual resolve : (I)V
    //   1579: goto -> 1591
    //   1582: aload_1
    //   1583: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1586: iload #7
    //   1588: invokevirtual resolve : (I)V
    //   1591: aload_1
    //   1592: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1595: getfield value : I
    //   1598: istore #9
    //   1600: iload #9
    //   1602: istore #5
    //   1604: aload_1
    //   1605: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1608: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1611: if_acmpne -> 1635
    //   1614: iload #9
    //   1616: istore #5
    //   1618: aload_1
    //   1619: getfield matchConstraintsType : I
    //   1622: iconst_1
    //   1623: if_icmpne -> 1635
    //   1626: aload_1
    //   1627: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1630: getfield wrapValue : I
    //   1633: istore #5
    //   1635: iload #21
    //   1637: ifeq -> 1650
    //   1640: iload #7
    //   1642: iload #5
    //   1644: isub
    //   1645: istore #7
    //   1647: goto -> 1657
    //   1650: iload #7
    //   1652: iload #5
    //   1654: iadd
    //   1655: istore #7
    //   1657: iload #21
    //   1659: ifeq -> 1674
    //   1662: aload_1
    //   1663: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1666: iload #7
    //   1668: invokevirtual resolve : (I)V
    //   1671: goto -> 1683
    //   1674: aload_1
    //   1675: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1678: iload #7
    //   1680: invokevirtual resolve : (I)V
    //   1683: aload_1
    //   1684: iconst_1
    //   1685: putfield resolved : Z
    //   1688: iload #7
    //   1690: istore #5
    //   1692: iload #6
    //   1694: iload #18
    //   1696: if_icmpge -> 1744
    //   1699: iload #7
    //   1701: istore #5
    //   1703: iload #6
    //   1705: iload #15
    //   1707: if_icmpge -> 1744
    //   1710: iload #21
    //   1712: ifeq -> 1731
    //   1715: iload #7
    //   1717: aload_1
    //   1718: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1721: getfield margin : I
    //   1724: ineg
    //   1725: isub
    //   1726: istore #5
    //   1728: goto -> 1744
    //   1731: iload #7
    //   1733: aload_1
    //   1734: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1737: getfield margin : I
    //   1740: ineg
    //   1741: iadd
    //   1742: istore #5
    //   1744: iinc #6, 1
    //   1747: iload #5
    //   1749: istore #7
    //   1751: goto -> 1404
    //   1754: iload #6
    //   1756: ifne -> 2115
    //   1759: iload #20
    //   1761: iload #7
    //   1763: isub
    //   1764: iload #16
    //   1766: iconst_1
    //   1767: iadd
    //   1768: idiv
    //   1769: istore #8
    //   1771: iload #9
    //   1773: ifle -> 1779
    //   1776: iconst_0
    //   1777: istore #8
    //   1779: iconst_0
    //   1780: istore #6
    //   1782: iload #6
    //   1784: iload #19
    //   1786: if_icmpge -> 2511
    //   1789: iload #21
    //   1791: ifeq -> 1806
    //   1794: iload #19
    //   1796: iload #6
    //   1798: iconst_1
    //   1799: iadd
    //   1800: isub
    //   1801: istore #7
    //   1803: goto -> 1810
    //   1806: iload #6
    //   1808: istore #7
    //   1810: aload_0
    //   1811: getfield widgets : Ljava/util/ArrayList;
    //   1814: iload #7
    //   1816: invokevirtual get : (I)Ljava/lang/Object;
    //   1819: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   1822: astore_1
    //   1823: aload_1
    //   1824: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1827: invokevirtual getVisibility : ()I
    //   1830: bipush #8
    //   1832: if_icmpne -> 1856
    //   1835: aload_1
    //   1836: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1839: iload #5
    //   1841: invokevirtual resolve : (I)V
    //   1844: aload_1
    //   1845: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1848: iload #5
    //   1850: invokevirtual resolve : (I)V
    //   1853: goto -> 2109
    //   1856: iload #21
    //   1858: ifeq -> 1871
    //   1861: iload #5
    //   1863: iload #8
    //   1865: isub
    //   1866: istore #7
    //   1868: goto -> 1878
    //   1871: iload #5
    //   1873: iload #8
    //   1875: iadd
    //   1876: istore #7
    //   1878: iload #7
    //   1880: istore #5
    //   1882: iload #6
    //   1884: ifle -> 1930
    //   1887: iload #7
    //   1889: istore #5
    //   1891: iload #6
    //   1893: iload #14
    //   1895: if_icmplt -> 1930
    //   1898: iload #21
    //   1900: ifeq -> 1918
    //   1903: iload #7
    //   1905: aload_1
    //   1906: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1909: getfield margin : I
    //   1912: isub
    //   1913: istore #5
    //   1915: goto -> 1930
    //   1918: iload #7
    //   1920: aload_1
    //   1921: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1924: getfield margin : I
    //   1927: iadd
    //   1928: istore #5
    //   1930: iload #21
    //   1932: ifeq -> 1947
    //   1935: aload_1
    //   1936: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1939: iload #5
    //   1941: invokevirtual resolve : (I)V
    //   1944: goto -> 1956
    //   1947: aload_1
    //   1948: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   1951: iload #5
    //   1953: invokevirtual resolve : (I)V
    //   1956: aload_1
    //   1957: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1960: getfield value : I
    //   1963: istore #9
    //   1965: iload #9
    //   1967: istore #7
    //   1969: aload_1
    //   1970: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1973: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1976: if_acmpne -> 2005
    //   1979: iload #9
    //   1981: istore #7
    //   1983: aload_1
    //   1984: getfield matchConstraintsType : I
    //   1987: iconst_1
    //   1988: if_icmpne -> 2005
    //   1991: iload #9
    //   1993: aload_1
    //   1994: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1997: getfield wrapValue : I
    //   2000: invokestatic min : (II)I
    //   2003: istore #7
    //   2005: iload #21
    //   2007: ifeq -> 2020
    //   2010: iload #5
    //   2012: iload #7
    //   2014: isub
    //   2015: istore #7
    //   2017: goto -> 2027
    //   2020: iload #5
    //   2022: iload #7
    //   2024: iadd
    //   2025: istore #7
    //   2027: iload #21
    //   2029: ifeq -> 2044
    //   2032: aload_1
    //   2033: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2036: iload #7
    //   2038: invokevirtual resolve : (I)V
    //   2041: goto -> 2053
    //   2044: aload_1
    //   2045: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2048: iload #7
    //   2050: invokevirtual resolve : (I)V
    //   2053: iload #7
    //   2055: istore #5
    //   2057: iload #6
    //   2059: iload #18
    //   2061: if_icmpge -> 2109
    //   2064: iload #7
    //   2066: istore #5
    //   2068: iload #6
    //   2070: iload #15
    //   2072: if_icmpge -> 2109
    //   2075: iload #21
    //   2077: ifeq -> 2096
    //   2080: iload #7
    //   2082: aload_1
    //   2083: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2086: getfield margin : I
    //   2089: ineg
    //   2090: isub
    //   2091: istore #5
    //   2093: goto -> 2109
    //   2096: iload #7
    //   2098: aload_1
    //   2099: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2102: getfield margin : I
    //   2105: ineg
    //   2106: iadd
    //   2107: istore #5
    //   2109: iinc #6, 1
    //   2112: goto -> 1782
    //   2115: iload #6
    //   2117: iconst_2
    //   2118: if_icmpne -> 2511
    //   2121: aload_0
    //   2122: getfield orientation : I
    //   2125: ifne -> 2139
    //   2128: aload_0
    //   2129: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2132: invokevirtual getHorizontalBiasPercent : ()F
    //   2135: fstore_2
    //   2136: goto -> 2147
    //   2139: aload_0
    //   2140: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2143: invokevirtual getVerticalBiasPercent : ()F
    //   2146: fstore_2
    //   2147: fload_2
    //   2148: fstore_3
    //   2149: iload #21
    //   2151: ifeq -> 2158
    //   2154: fconst_1
    //   2155: fload_2
    //   2156: fsub
    //   2157: fstore_3
    //   2158: iload #20
    //   2160: iload #7
    //   2162: isub
    //   2163: i2f
    //   2164: fload_3
    //   2165: fmul
    //   2166: ldc 0.5
    //   2168: fadd
    //   2169: f2i
    //   2170: istore #6
    //   2172: iload #6
    //   2174: iflt -> 2182
    //   2177: iload #9
    //   2179: ifle -> 2185
    //   2182: iconst_0
    //   2183: istore #6
    //   2185: iload #21
    //   2187: ifeq -> 2200
    //   2190: iload #5
    //   2192: iload #6
    //   2194: isub
    //   2195: istore #5
    //   2197: goto -> 2207
    //   2200: iload #5
    //   2202: iload #6
    //   2204: iadd
    //   2205: istore #5
    //   2207: iconst_0
    //   2208: istore #6
    //   2210: iload #6
    //   2212: iload #19
    //   2214: if_icmpge -> 2511
    //   2217: iload #21
    //   2219: ifeq -> 2234
    //   2222: iload #19
    //   2224: iload #6
    //   2226: iconst_1
    //   2227: iadd
    //   2228: isub
    //   2229: istore #7
    //   2231: goto -> 2238
    //   2234: iload #6
    //   2236: istore #7
    //   2238: aload_0
    //   2239: getfield widgets : Ljava/util/ArrayList;
    //   2242: iload #7
    //   2244: invokevirtual get : (I)Ljava/lang/Object;
    //   2247: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   2250: astore_1
    //   2251: aload_1
    //   2252: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   2255: invokevirtual getVisibility : ()I
    //   2258: bipush #8
    //   2260: if_icmpne -> 2284
    //   2263: aload_1
    //   2264: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2267: iload #5
    //   2269: invokevirtual resolve : (I)V
    //   2272: aload_1
    //   2273: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2276: iload #5
    //   2278: invokevirtual resolve : (I)V
    //   2281: goto -> 2505
    //   2284: iload #5
    //   2286: istore #7
    //   2288: iload #6
    //   2290: ifle -> 2336
    //   2293: iload #5
    //   2295: istore #7
    //   2297: iload #6
    //   2299: iload #14
    //   2301: if_icmplt -> 2336
    //   2304: iload #21
    //   2306: ifeq -> 2324
    //   2309: iload #5
    //   2311: aload_1
    //   2312: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2315: getfield margin : I
    //   2318: isub
    //   2319: istore #7
    //   2321: goto -> 2336
    //   2324: iload #5
    //   2326: aload_1
    //   2327: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2330: getfield margin : I
    //   2333: iadd
    //   2334: istore #7
    //   2336: iload #21
    //   2338: ifeq -> 2353
    //   2341: aload_1
    //   2342: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2345: iload #7
    //   2347: invokevirtual resolve : (I)V
    //   2350: goto -> 2362
    //   2353: aload_1
    //   2354: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2357: iload #7
    //   2359: invokevirtual resolve : (I)V
    //   2362: aload_1
    //   2363: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   2366: getfield value : I
    //   2369: istore #5
    //   2371: aload_1
    //   2372: getfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2375: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   2378: if_acmpne -> 2401
    //   2381: aload_1
    //   2382: getfield matchConstraintsType : I
    //   2385: iconst_1
    //   2386: if_icmpne -> 2401
    //   2389: aload_1
    //   2390: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   2393: getfield wrapValue : I
    //   2396: istore #5
    //   2398: goto -> 2401
    //   2401: iload #21
    //   2403: ifeq -> 2416
    //   2406: iload #7
    //   2408: iload #5
    //   2410: isub
    //   2411: istore #7
    //   2413: goto -> 2423
    //   2416: iload #7
    //   2418: iload #5
    //   2420: iadd
    //   2421: istore #7
    //   2423: iload #21
    //   2425: ifeq -> 2440
    //   2428: aload_1
    //   2429: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2432: iload #7
    //   2434: invokevirtual resolve : (I)V
    //   2437: goto -> 2449
    //   2440: aload_1
    //   2441: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2444: iload #7
    //   2446: invokevirtual resolve : (I)V
    //   2449: iload #7
    //   2451: istore #5
    //   2453: iload #6
    //   2455: iload #18
    //   2457: if_icmpge -> 2505
    //   2460: iload #7
    //   2462: istore #5
    //   2464: iload #6
    //   2466: iload #15
    //   2468: if_icmpge -> 2505
    //   2471: iload #21
    //   2473: ifeq -> 2492
    //   2476: iload #7
    //   2478: aload_1
    //   2479: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2482: getfield margin : I
    //   2485: ineg
    //   2486: isub
    //   2487: istore #5
    //   2489: goto -> 2505
    //   2492: iload #7
    //   2494: aload_1
    //   2495: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   2498: getfield margin : I
    //   2501: ineg
    //   2502: iadd
    //   2503: istore #5
    //   2505: iinc #6, 1
    //   2508: goto -> 2210
    //   2511: return
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\ChainRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */