package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class DependencyGraph {
  private static final boolean USE_GROUPS = true;
  
  private ConstraintWidgetContainer container;
  
  private ConstraintWidgetContainer mContainer;
  
  ArrayList<RunGroup> mGroups = new ArrayList<RunGroup>();
  
  private BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
  
  private BasicMeasure.Measurer mMeasurer = null;
  
  private boolean mNeedBuildGraph = true;
  
  private boolean mNeedRedoMeasures = true;
  
  private ArrayList<WidgetRun> mRuns = new ArrayList<WidgetRun>();
  
  private ArrayList<RunGroup> runGroups = new ArrayList<RunGroup>();
  
  public DependencyGraph(ConstraintWidgetContainer paramConstraintWidgetContainer) {
    this.container = paramConstraintWidgetContainer;
    this.mContainer = paramConstraintWidgetContainer;
  }
  
  private void applyGroup(DependencyNode paramDependencyNode1, int paramInt1, int paramInt2, DependencyNode paramDependencyNode2, ArrayList<RunGroup> paramArrayList, RunGroup paramRunGroup) {
    WidgetRun widgetRun = paramDependencyNode1.run;
    if (widgetRun.runGroup == null && widgetRun != this.container.horizontalRun && widgetRun != this.container.verticalRun) {
      RunGroup runGroup = paramRunGroup;
      if (paramRunGroup == null) {
        runGroup = new RunGroup(widgetRun, paramInt2);
        paramArrayList.add(runGroup);
      } 
      widgetRun.runGroup = runGroup;
      runGroup.add(widgetRun);
      for (Dependency dependency : widgetRun.start.dependencies) {
        if (dependency instanceof DependencyNode)
          applyGroup((DependencyNode)dependency, paramInt1, 0, paramDependencyNode2, paramArrayList, runGroup); 
      } 
      for (Dependency dependency : widgetRun.end.dependencies) {
        if (dependency instanceof DependencyNode)
          applyGroup((DependencyNode)dependency, paramInt1, 1, paramDependencyNode2, paramArrayList, runGroup); 
      } 
      if (paramInt1 == 1 && widgetRun instanceof VerticalWidgetRun)
        for (Dependency dependency : ((VerticalWidgetRun)widgetRun).baseline.dependencies) {
          if (dependency instanceof DependencyNode)
            applyGroup((DependencyNode)dependency, paramInt1, 2, paramDependencyNode2, paramArrayList, runGroup); 
        }  
      for (DependencyNode dependencyNode : widgetRun.start.targets) {
        if (dependencyNode == paramDependencyNode2)
          runGroup.dual = true; 
        applyGroup(dependencyNode, paramInt1, 0, paramDependencyNode2, paramArrayList, runGroup);
      } 
      for (DependencyNode dependencyNode : widgetRun.end.targets) {
        if (dependencyNode == paramDependencyNode2)
          runGroup.dual = true; 
        applyGroup(dependencyNode, paramInt1, 1, paramDependencyNode2, paramArrayList, runGroup);
      } 
      if (paramInt1 == 1 && widgetRun instanceof VerticalWidgetRun) {
        Iterator<DependencyNode> iterator = ((VerticalWidgetRun)widgetRun).baseline.targets.iterator();
        while (true) {
          if (iterator.hasNext()) {
            DependencyNode dependencyNode = iterator.next();
            try {
              applyGroup(dependencyNode, paramInt1, 2, paramDependencyNode2, paramArrayList, runGroup);
            } finally {}
            continue;
          } 
          return;
        } 
      } 
    } 
  }
  
  private boolean basicMeasureWidgets(ConstraintWidgetContainer paramConstraintWidgetContainer) {
    // Byte code:
    //   0: aload_1
    //   1: getfield mChildren : Ljava/util/ArrayList;
    //   4: invokevirtual iterator : ()Ljava/util/Iterator;
    //   7: astore #12
    //   9: aload #12
    //   11: invokeinterface hasNext : ()Z
    //   16: ifeq -> 1619
    //   19: aload #12
    //   21: invokeinterface next : ()Ljava/lang/Object;
    //   26: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   29: astore #11
    //   31: aload #11
    //   33: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   36: iconst_0
    //   37: aaload
    //   38: astore #9
    //   40: aload #11
    //   42: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   45: iconst_1
    //   46: aaload
    //   47: astore #10
    //   49: aload #11
    //   51: invokevirtual getVisibility : ()I
    //   54: bipush #8
    //   56: if_icmpne -> 68
    //   59: aload #11
    //   61: iconst_1
    //   62: putfield measured : Z
    //   65: goto -> 9
    //   68: aload #11
    //   70: getfield mMatchConstraintPercentWidth : F
    //   73: fconst_1
    //   74: fcmpg
    //   75: ifge -> 92
    //   78: aload #9
    //   80: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   83: if_acmpne -> 92
    //   86: aload #11
    //   88: iconst_2
    //   89: putfield mMatchConstraintDefaultWidth : I
    //   92: aload #11
    //   94: getfield mMatchConstraintPercentHeight : F
    //   97: fconst_1
    //   98: fcmpg
    //   99: ifge -> 116
    //   102: aload #10
    //   104: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   107: if_acmpne -> 116
    //   110: aload #11
    //   112: iconst_2
    //   113: putfield mMatchConstraintDefaultHeight : I
    //   116: aload #11
    //   118: invokevirtual getDimensionRatio : ()F
    //   121: fconst_0
    //   122: fcmpl
    //   123: ifle -> 236
    //   126: aload #9
    //   128: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   131: if_acmpne -> 159
    //   134: aload #10
    //   136: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   139: if_acmpeq -> 150
    //   142: aload #10
    //   144: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   147: if_acmpne -> 159
    //   150: aload #11
    //   152: iconst_3
    //   153: putfield mMatchConstraintDefaultWidth : I
    //   156: goto -> 236
    //   159: aload #10
    //   161: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   164: if_acmpne -> 192
    //   167: aload #9
    //   169: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   172: if_acmpeq -> 183
    //   175: aload #9
    //   177: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   180: if_acmpne -> 192
    //   183: aload #11
    //   185: iconst_3
    //   186: putfield mMatchConstraintDefaultHeight : I
    //   189: goto -> 236
    //   192: aload #9
    //   194: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   197: if_acmpne -> 236
    //   200: aload #10
    //   202: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   205: if_acmpne -> 236
    //   208: aload #11
    //   210: getfield mMatchConstraintDefaultWidth : I
    //   213: ifne -> 222
    //   216: aload #11
    //   218: iconst_3
    //   219: putfield mMatchConstraintDefaultWidth : I
    //   222: aload #11
    //   224: getfield mMatchConstraintDefaultHeight : I
    //   227: ifne -> 236
    //   230: aload #11
    //   232: iconst_3
    //   233: putfield mMatchConstraintDefaultHeight : I
    //   236: aload #9
    //   238: astore #8
    //   240: aload #9
    //   242: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   245: if_acmpne -> 292
    //   248: aload #9
    //   250: astore #8
    //   252: aload #11
    //   254: getfield mMatchConstraintDefaultWidth : I
    //   257: iconst_1
    //   258: if_icmpne -> 292
    //   261: aload #11
    //   263: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   266: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   269: ifnull -> 287
    //   272: aload #9
    //   274: astore #8
    //   276: aload #11
    //   278: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   281: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   284: ifnonnull -> 292
    //   287: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   290: astore #8
    //   292: aload #8
    //   294: astore #9
    //   296: aload #10
    //   298: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   301: if_acmpne -> 343
    //   304: aload #11
    //   306: getfield mMatchConstraintDefaultHeight : I
    //   309: iconst_1
    //   310: if_icmpne -> 343
    //   313: aload #11
    //   315: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   318: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   321: ifnull -> 335
    //   324: aload #11
    //   326: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   329: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   332: ifnonnull -> 343
    //   335: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   338: astore #8
    //   340: goto -> 347
    //   343: aload #10
    //   345: astore #8
    //   347: aload #11
    //   349: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   352: aload #9
    //   354: putfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   357: aload #11
    //   359: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   362: aload #11
    //   364: getfield mMatchConstraintDefaultWidth : I
    //   367: putfield matchConstraintsType : I
    //   370: aload #11
    //   372: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   375: aload #8
    //   377: putfield dimensionBehavior : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   380: aload #11
    //   382: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   385: aload #11
    //   387: getfield mMatchConstraintDefaultHeight : I
    //   390: putfield matchConstraintsType : I
    //   393: aload #9
    //   395: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   398: if_acmpeq -> 417
    //   401: aload #9
    //   403: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   406: if_acmpeq -> 417
    //   409: aload #9
    //   411: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   414: if_acmpne -> 444
    //   417: aload #8
    //   419: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   422: if_acmpeq -> 1446
    //   425: aload #8
    //   427: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   430: if_acmpeq -> 1446
    //   433: aload #8
    //   435: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   438: if_acmpne -> 444
    //   441: goto -> 1446
    //   444: aload #9
    //   446: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   449: if_acmpne -> 812
    //   452: aload #8
    //   454: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   457: if_acmpeq -> 468
    //   460: aload #8
    //   462: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   465: if_acmpne -> 812
    //   468: aload #11
    //   470: getfield mMatchConstraintDefaultWidth : I
    //   473: iconst_3
    //   474: if_icmpne -> 578
    //   477: aload #8
    //   479: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   482: if_acmpne -> 499
    //   485: aload_0
    //   486: aload #11
    //   488: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   491: iconst_0
    //   492: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   495: iconst_0
    //   496: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   499: aload #11
    //   501: invokevirtual getHeight : ()I
    //   504: istore #4
    //   506: iload #4
    //   508: i2f
    //   509: aload #11
    //   511: getfield mDimensionRatio : F
    //   514: fmul
    //   515: ldc 0.5
    //   517: fadd
    //   518: f2i
    //   519: istore #5
    //   521: aload_0
    //   522: aload #11
    //   524: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   527: iload #5
    //   529: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   532: iload #4
    //   534: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   537: aload #11
    //   539: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   542: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   545: aload #11
    //   547: invokevirtual getWidth : ()I
    //   550: invokevirtual resolve : (I)V
    //   553: aload #11
    //   555: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   558: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   561: aload #11
    //   563: invokevirtual getHeight : ()I
    //   566: invokevirtual resolve : (I)V
    //   569: aload #11
    //   571: iconst_1
    //   572: putfield measured : Z
    //   575: goto -> 9
    //   578: aload #11
    //   580: getfield mMatchConstraintDefaultWidth : I
    //   583: iconst_1
    //   584: if_icmpne -> 619
    //   587: aload_0
    //   588: aload #11
    //   590: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   593: iconst_0
    //   594: aload #8
    //   596: iconst_0
    //   597: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   600: aload #11
    //   602: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   605: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   608: aload #11
    //   610: invokevirtual getWidth : ()I
    //   613: putfield wrapValue : I
    //   616: goto -> 9
    //   619: aload #11
    //   621: getfield mMatchConstraintDefaultWidth : I
    //   624: iconst_2
    //   625: if_icmpne -> 732
    //   628: aload_1
    //   629: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   632: iconst_0
    //   633: aaload
    //   634: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   637: if_acmpeq -> 652
    //   640: aload_1
    //   641: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   644: iconst_0
    //   645: aaload
    //   646: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   649: if_acmpne -> 812
    //   652: aload #11
    //   654: getfield mMatchConstraintPercentWidth : F
    //   657: aload_1
    //   658: invokevirtual getWidth : ()I
    //   661: i2f
    //   662: fmul
    //   663: ldc 0.5
    //   665: fadd
    //   666: f2i
    //   667: istore #4
    //   669: aload #11
    //   671: invokevirtual getHeight : ()I
    //   674: istore #5
    //   676: aload_0
    //   677: aload #11
    //   679: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   682: iload #4
    //   684: aload #8
    //   686: iload #5
    //   688: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   691: aload #11
    //   693: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   696: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   699: aload #11
    //   701: invokevirtual getWidth : ()I
    //   704: invokevirtual resolve : (I)V
    //   707: aload #11
    //   709: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   712: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   715: aload #11
    //   717: invokevirtual getHeight : ()I
    //   720: invokevirtual resolve : (I)V
    //   723: aload #11
    //   725: iconst_1
    //   726: putfield measured : Z
    //   729: goto -> 9
    //   732: aload #11
    //   734: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   737: iconst_0
    //   738: aaload
    //   739: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   742: ifnull -> 758
    //   745: aload #11
    //   747: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   750: iconst_1
    //   751: aaload
    //   752: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   755: ifnonnull -> 812
    //   758: aload_0
    //   759: aload #11
    //   761: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   764: iconst_0
    //   765: aload #8
    //   767: iconst_0
    //   768: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   771: aload #11
    //   773: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   776: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   779: aload #11
    //   781: invokevirtual getWidth : ()I
    //   784: invokevirtual resolve : (I)V
    //   787: aload #11
    //   789: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   792: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   795: aload #11
    //   797: invokevirtual getHeight : ()I
    //   800: invokevirtual resolve : (I)V
    //   803: aload #11
    //   805: iconst_1
    //   806: putfield measured : Z
    //   809: goto -> 9
    //   812: aload #8
    //   814: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   817: if_acmpne -> 1199
    //   820: aload #9
    //   822: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   825: if_acmpeq -> 836
    //   828: aload #9
    //   830: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   833: if_acmpne -> 1199
    //   836: aload #11
    //   838: getfield mMatchConstraintDefaultHeight : I
    //   841: iconst_3
    //   842: if_icmpne -> 963
    //   845: aload #9
    //   847: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   850: if_acmpne -> 867
    //   853: aload_0
    //   854: aload #11
    //   856: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   859: iconst_0
    //   860: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   863: iconst_0
    //   864: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   867: aload #11
    //   869: invokevirtual getWidth : ()I
    //   872: istore #5
    //   874: aload #11
    //   876: getfield mDimensionRatio : F
    //   879: fstore_3
    //   880: fload_3
    //   881: fstore_2
    //   882: aload #11
    //   884: invokevirtual getDimensionRatioSide : ()I
    //   887: iconst_m1
    //   888: if_icmpne -> 895
    //   891: fconst_1
    //   892: fload_3
    //   893: fdiv
    //   894: fstore_2
    //   895: iload #5
    //   897: i2f
    //   898: fload_2
    //   899: fmul
    //   900: ldc 0.5
    //   902: fadd
    //   903: f2i
    //   904: istore #4
    //   906: aload_0
    //   907: aload #11
    //   909: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   912: iload #5
    //   914: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   917: iload #4
    //   919: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   922: aload #11
    //   924: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   927: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   930: aload #11
    //   932: invokevirtual getWidth : ()I
    //   935: invokevirtual resolve : (I)V
    //   938: aload #11
    //   940: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   943: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   946: aload #11
    //   948: invokevirtual getHeight : ()I
    //   951: invokevirtual resolve : (I)V
    //   954: aload #11
    //   956: iconst_1
    //   957: putfield measured : Z
    //   960: goto -> 9
    //   963: aload #11
    //   965: getfield mMatchConstraintDefaultHeight : I
    //   968: iconst_1
    //   969: if_icmpne -> 1004
    //   972: aload_0
    //   973: aload #11
    //   975: aload #9
    //   977: iconst_0
    //   978: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   981: iconst_0
    //   982: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   985: aload #11
    //   987: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   990: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   993: aload #11
    //   995: invokevirtual getHeight : ()I
    //   998: putfield wrapValue : I
    //   1001: goto -> 9
    //   1004: aload #11
    //   1006: getfield mMatchConstraintDefaultHeight : I
    //   1009: iconst_2
    //   1010: if_icmpne -> 1119
    //   1013: aload_1
    //   1014: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1017: iconst_1
    //   1018: aaload
    //   1019: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1022: if_acmpeq -> 1037
    //   1025: aload_1
    //   1026: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1029: iconst_1
    //   1030: aaload
    //   1031: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1034: if_acmpne -> 1199
    //   1037: aload #11
    //   1039: getfield mMatchConstraintPercentHeight : F
    //   1042: fstore_2
    //   1043: aload #11
    //   1045: invokevirtual getWidth : ()I
    //   1048: istore #4
    //   1050: fload_2
    //   1051: aload_1
    //   1052: invokevirtual getHeight : ()I
    //   1055: i2f
    //   1056: fmul
    //   1057: ldc 0.5
    //   1059: fadd
    //   1060: f2i
    //   1061: istore #5
    //   1063: aload_0
    //   1064: aload #11
    //   1066: aload #9
    //   1068: iload #4
    //   1070: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1073: iload #5
    //   1075: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   1078: aload #11
    //   1080: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1083: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1086: aload #11
    //   1088: invokevirtual getWidth : ()I
    //   1091: invokevirtual resolve : (I)V
    //   1094: aload #11
    //   1096: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1099: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1102: aload #11
    //   1104: invokevirtual getHeight : ()I
    //   1107: invokevirtual resolve : (I)V
    //   1110: aload #11
    //   1112: iconst_1
    //   1113: putfield measured : Z
    //   1116: goto -> 9
    //   1119: aload #11
    //   1121: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1124: iconst_2
    //   1125: aaload
    //   1126: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1129: ifnull -> 1145
    //   1132: aload #11
    //   1134: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1137: iconst_3
    //   1138: aaload
    //   1139: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1142: ifnonnull -> 1199
    //   1145: aload_0
    //   1146: aload #11
    //   1148: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1151: iconst_0
    //   1152: aload #8
    //   1154: iconst_0
    //   1155: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   1158: aload #11
    //   1160: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1163: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1166: aload #11
    //   1168: invokevirtual getWidth : ()I
    //   1171: invokevirtual resolve : (I)V
    //   1174: aload #11
    //   1176: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1179: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1182: aload #11
    //   1184: invokevirtual getHeight : ()I
    //   1187: invokevirtual resolve : (I)V
    //   1190: aload #11
    //   1192: iconst_1
    //   1193: putfield measured : Z
    //   1196: goto -> 9
    //   1199: aload #9
    //   1201: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1204: if_acmpne -> 9
    //   1207: aload #8
    //   1209: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1212: if_acmpne -> 9
    //   1215: aload #11
    //   1217: getfield mMatchConstraintDefaultWidth : I
    //   1220: iconst_1
    //   1221: if_icmpeq -> 1397
    //   1224: aload #11
    //   1226: getfield mMatchConstraintDefaultHeight : I
    //   1229: iconst_1
    //   1230: if_icmpne -> 1236
    //   1233: goto -> 1397
    //   1236: aload #11
    //   1238: getfield mMatchConstraintDefaultHeight : I
    //   1241: iconst_2
    //   1242: if_icmpne -> 9
    //   1245: aload #11
    //   1247: getfield mMatchConstraintDefaultWidth : I
    //   1250: iconst_2
    //   1251: if_icmpne -> 9
    //   1254: aload_1
    //   1255: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1258: iconst_0
    //   1259: aaload
    //   1260: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1263: if_acmpeq -> 1278
    //   1266: aload_1
    //   1267: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1270: iconst_0
    //   1271: aaload
    //   1272: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1275: if_acmpne -> 9
    //   1278: aload_1
    //   1279: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1282: iconst_1
    //   1283: aaload
    //   1284: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1287: if_acmpeq -> 1302
    //   1290: aload_1
    //   1291: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1294: iconst_1
    //   1295: aaload
    //   1296: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1299: if_acmpne -> 9
    //   1302: aload #11
    //   1304: getfield mMatchConstraintPercentWidth : F
    //   1307: fstore_2
    //   1308: aload #11
    //   1310: getfield mMatchConstraintPercentHeight : F
    //   1313: fstore_3
    //   1314: fload_2
    //   1315: aload_1
    //   1316: invokevirtual getWidth : ()I
    //   1319: i2f
    //   1320: fmul
    //   1321: ldc 0.5
    //   1323: fadd
    //   1324: f2i
    //   1325: istore #4
    //   1327: fload_3
    //   1328: aload_1
    //   1329: invokevirtual getHeight : ()I
    //   1332: i2f
    //   1333: fmul
    //   1334: ldc 0.5
    //   1336: fadd
    //   1337: f2i
    //   1338: istore #5
    //   1340: aload_0
    //   1341: aload #11
    //   1343: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1346: iload #4
    //   1348: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1351: iload #5
    //   1353: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   1356: aload #11
    //   1358: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1361: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1364: aload #11
    //   1366: invokevirtual getWidth : ()I
    //   1369: invokevirtual resolve : (I)V
    //   1372: aload #11
    //   1374: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1377: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1380: aload #11
    //   1382: invokevirtual getHeight : ()I
    //   1385: invokevirtual resolve : (I)V
    //   1388: aload #11
    //   1390: iconst_1
    //   1391: putfield measured : Z
    //   1394: goto -> 9
    //   1397: aload_0
    //   1398: aload #11
    //   1400: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1403: iconst_0
    //   1404: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1407: iconst_0
    //   1408: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   1411: aload #11
    //   1413: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1416: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1419: aload #11
    //   1421: invokevirtual getWidth : ()I
    //   1424: putfield wrapValue : I
    //   1427: aload #11
    //   1429: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1432: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1435: aload #11
    //   1437: invokevirtual getHeight : ()I
    //   1440: putfield wrapValue : I
    //   1443: goto -> 9
    //   1446: aload #11
    //   1448: invokevirtual getWidth : ()I
    //   1451: istore #4
    //   1453: aload #9
    //   1455: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1458: if_acmpne -> 1505
    //   1461: aload_1
    //   1462: invokevirtual getWidth : ()I
    //   1465: istore #4
    //   1467: aload #11
    //   1469: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1472: getfield mMargin : I
    //   1475: istore #6
    //   1477: aload #11
    //   1479: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1482: getfield mMargin : I
    //   1485: istore #5
    //   1487: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1490: astore #9
    //   1492: iload #4
    //   1494: iload #6
    //   1496: isub
    //   1497: iload #5
    //   1499: isub
    //   1500: istore #4
    //   1502: goto -> 1505
    //   1505: aload #11
    //   1507: invokevirtual getHeight : ()I
    //   1510: istore #5
    //   1512: aload #8
    //   1514: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1517: if_acmpne -> 1564
    //   1520: aload_1
    //   1521: invokevirtual getHeight : ()I
    //   1524: istore #5
    //   1526: aload #11
    //   1528: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1531: getfield mMargin : I
    //   1534: istore #6
    //   1536: aload #11
    //   1538: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1541: getfield mMargin : I
    //   1544: istore #7
    //   1546: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1549: astore #8
    //   1551: iload #5
    //   1553: iload #6
    //   1555: isub
    //   1556: iload #7
    //   1558: isub
    //   1559: istore #5
    //   1561: goto -> 1564
    //   1564: aload_0
    //   1565: aload #11
    //   1567: aload #9
    //   1569: iload #4
    //   1571: aload #8
    //   1573: iload #5
    //   1575: invokespecial measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;ILandroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;I)V
    //   1578: aload #11
    //   1580: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   1583: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1586: aload #11
    //   1588: invokevirtual getWidth : ()I
    //   1591: invokevirtual resolve : (I)V
    //   1594: aload #11
    //   1596: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   1599: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   1602: aload #11
    //   1604: invokevirtual getHeight : ()I
    //   1607: invokevirtual resolve : (I)V
    //   1610: aload #11
    //   1612: iconst_1
    //   1613: putfield measured : Z
    //   1616: goto -> 9
    //   1619: iconst_0
    //   1620: ireturn
  }
  
  private int computeWrap(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt) {
    int i = this.mGroups.size();
    long l = 0L;
    for (byte b = 0; b < i; b++)
      l = Math.max(l, ((RunGroup)this.mGroups.get(b)).computeWrapSize(paramConstraintWidgetContainer, paramInt)); 
    return (int)l;
  }
  
  private void displayGraph() {
    Iterator<WidgetRun> iterator = this.mRuns.iterator();
    String str;
    for (str = "digraph {\n"; iterator.hasNext(); str = generateDisplayGraph(iterator.next(), str));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append("\n}\n");
    str = stringBuilder.toString();
    PrintStream printStream = System.out;
    stringBuilder = new StringBuilder();
    stringBuilder.append("content:<<\n");
    stringBuilder.append(str);
    stringBuilder.append("\n>>");
    printStream.println(stringBuilder.toString());
  }
  
  private void findGroup(WidgetRun paramWidgetRun, int paramInt, ArrayList<RunGroup> paramArrayList) {
    for (Dependency dependency : paramWidgetRun.start.dependencies) {
      if (dependency instanceof DependencyNode) {
        applyGroup((DependencyNode)dependency, paramInt, 0, paramWidgetRun.end, paramArrayList, null);
        continue;
      } 
      if (dependency instanceof WidgetRun)
        applyGroup(((WidgetRun)dependency).start, paramInt, 0, paramWidgetRun.end, paramArrayList, null); 
    } 
    for (Dependency dependency : paramWidgetRun.end.dependencies) {
      if (dependency instanceof DependencyNode) {
        applyGroup((DependencyNode)dependency, paramInt, 1, paramWidgetRun.start, paramArrayList, null);
        continue;
      } 
      if (dependency instanceof WidgetRun)
        applyGroup(((WidgetRun)dependency).end, paramInt, 1, paramWidgetRun.start, paramArrayList, null); 
    } 
    if (paramInt == 1)
      for (Dependency dependency : ((VerticalWidgetRun)paramWidgetRun).baseline.dependencies) {
        if (dependency instanceof DependencyNode)
          applyGroup((DependencyNode)dependency, paramInt, 2, null, paramArrayList, null); 
      }  
  }
  
  private String generateChainDisplayGraph(ChainRun paramChainRun, String paramString) {
    int i = paramChainRun.orientation;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("cluster_");
    stringBuilder1.append(paramChainRun.widget.getDebugName());
    String str2 = stringBuilder1.toString();
    if (i == 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append("_h");
      str2 = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append("_v");
      str2 = stringBuilder.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("subgraph ");
    stringBuilder2.append(str2);
    stringBuilder2.append(" {\n");
    str2 = stringBuilder2.toString();
    Iterator<WidgetRun> iterator = paramChainRun.widgets.iterator();
    String str1;
    for (str1 = ""; iterator.hasNext(); str1 = generateDisplayGraph(widgetRun, str1)) {
      WidgetRun widgetRun = iterator.next();
      String str = widgetRun.widget.getDebugName();
      if (i == 0) {
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append("_HORIZONTAL");
        str = stringBuilder3.toString();
      } else {
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append("_VERTICAL");
        str = stringBuilder3.toString();
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append(str);
      stringBuilder.append(";\n");
      str2 = stringBuilder.toString();
    } 
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str2);
    stringBuilder2.append("}\n");
    str2 = stringBuilder2.toString();
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append(str1);
    stringBuilder2.append(str2);
    return stringBuilder2.toString();
  }
  
  private String generateDisplayGraph(WidgetRun paramWidgetRun, String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   4: astore #6
    //   6: aload_1
    //   7: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   10: astore #7
    //   12: aload_1
    //   13: instanceof androidx/constraintlayout/solver/widgets/analyzer/HelperReferences
    //   16: ifne -> 71
    //   19: aload #6
    //   21: getfield dependencies : Ljava/util/List;
    //   24: invokeinterface isEmpty : ()Z
    //   29: ifeq -> 71
    //   32: aload #7
    //   34: getfield dependencies : Ljava/util/List;
    //   37: invokeinterface isEmpty : ()Z
    //   42: aload #6
    //   44: getfield targets : Ljava/util/List;
    //   47: invokeinterface isEmpty : ()Z
    //   52: iand
    //   53: ifeq -> 71
    //   56: aload #7
    //   58: getfield targets : Ljava/util/List;
    //   61: invokeinterface isEmpty : ()Z
    //   66: ifeq -> 71
    //   69: aload_2
    //   70: areturn
    //   71: new java/lang/StringBuilder
    //   74: dup
    //   75: invokespecial <init> : ()V
    //   78: astore #5
    //   80: aload #5
    //   82: aload_2
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: aload #5
    //   89: aload_0
    //   90: aload_1
    //   91: invokespecial nodeDefinition : (Landroidx/constraintlayout/solver/widgets/analyzer/WidgetRun;)Ljava/lang/String;
    //   94: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: pop
    //   98: aload #5
    //   100: invokevirtual toString : ()Ljava/lang/String;
    //   103: astore_2
    //   104: aload_0
    //   105: aload #6
    //   107: aload #7
    //   109: invokespecial isCenteredConnection : (Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;)Z
    //   112: istore #4
    //   114: aload_0
    //   115: aload #7
    //   117: iload #4
    //   119: aload_0
    //   120: aload #6
    //   122: iload #4
    //   124: aload_2
    //   125: invokespecial generateDisplayNode : (Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;ZLjava/lang/String;)Ljava/lang/String;
    //   128: invokespecial generateDisplayNode : (Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;ZLjava/lang/String;)Ljava/lang/String;
    //   131: astore_2
    //   132: aload_1
    //   133: instanceof androidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun
    //   136: istore_3
    //   137: aload_2
    //   138: astore #5
    //   140: iload_3
    //   141: ifeq -> 160
    //   144: aload_0
    //   145: aload_1
    //   146: checkcast androidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun
    //   149: getfield baseline : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   152: iload #4
    //   154: aload_2
    //   155: invokespecial generateDisplayNode : (Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;ZLjava/lang/String;)Ljava/lang/String;
    //   158: astore #5
    //   160: aload_1
    //   161: instanceof androidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun
    //   164: ifne -> 521
    //   167: aload_1
    //   168: instanceof androidx/constraintlayout/solver/widgets/analyzer/ChainRun
    //   171: istore #4
    //   173: iload #4
    //   175: ifeq -> 191
    //   178: aload_1
    //   179: checkcast androidx/constraintlayout/solver/widgets/analyzer/ChainRun
    //   182: getfield orientation : I
    //   185: ifne -> 191
    //   188: goto -> 521
    //   191: iload_3
    //   192: ifne -> 217
    //   195: aload #5
    //   197: astore_2
    //   198: iload #4
    //   200: ifeq -> 824
    //   203: aload #5
    //   205: astore_2
    //   206: aload_1
    //   207: checkcast androidx/constraintlayout/solver/widgets/analyzer/ChainRun
    //   210: getfield orientation : I
    //   213: iconst_1
    //   214: if_icmpne -> 824
    //   217: aload_1
    //   218: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   221: invokevirtual getVerticalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   224: astore #8
    //   226: aload #8
    //   228: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   231: if_acmpeq -> 285
    //   234: aload #8
    //   236: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   239: if_acmpne -> 245
    //   242: goto -> 285
    //   245: aload #5
    //   247: astore_2
    //   248: aload #8
    //   250: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   253: if_acmpne -> 824
    //   256: aload #5
    //   258: astore_2
    //   259: aload_1
    //   260: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   263: invokevirtual getDimensionRatio : ()F
    //   266: fconst_0
    //   267: fcmpl
    //   268: ifle -> 824
    //   271: aload_1
    //   272: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   275: invokevirtual getDebugName : ()Ljava/lang/String;
    //   278: pop
    //   279: aload #5
    //   281: astore_2
    //   282: goto -> 824
    //   285: aload #6
    //   287: getfield targets : Ljava/util/List;
    //   290: invokeinterface isEmpty : ()Z
    //   295: ifne -> 401
    //   298: aload #7
    //   300: getfield targets : Ljava/util/List;
    //   303: invokeinterface isEmpty : ()Z
    //   308: ifeq -> 401
    //   311: new java/lang/StringBuilder
    //   314: dup
    //   315: invokespecial <init> : ()V
    //   318: astore_2
    //   319: aload_2
    //   320: ldc_w '\\n'
    //   323: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   326: pop
    //   327: aload_2
    //   328: aload #7
    //   330: invokevirtual name : ()Ljava/lang/String;
    //   333: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   336: pop
    //   337: aload_2
    //   338: ldc_w ' -> '
    //   341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   344: pop
    //   345: aload_2
    //   346: aload #6
    //   348: invokevirtual name : ()Ljava/lang/String;
    //   351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   354: pop
    //   355: aload_2
    //   356: ldc_w '\\n'
    //   359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   362: pop
    //   363: aload_2
    //   364: invokevirtual toString : ()Ljava/lang/String;
    //   367: astore_2
    //   368: new java/lang/StringBuilder
    //   371: dup
    //   372: invokespecial <init> : ()V
    //   375: astore #6
    //   377: aload #6
    //   379: aload #5
    //   381: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   384: pop
    //   385: aload #6
    //   387: aload_2
    //   388: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   391: pop
    //   392: aload #6
    //   394: invokevirtual toString : ()Ljava/lang/String;
    //   397: astore_2
    //   398: goto -> 824
    //   401: aload #5
    //   403: astore_2
    //   404: aload #6
    //   406: getfield targets : Ljava/util/List;
    //   409: invokeinterface isEmpty : ()Z
    //   414: ifeq -> 824
    //   417: aload #5
    //   419: astore_2
    //   420: aload #7
    //   422: getfield targets : Ljava/util/List;
    //   425: invokeinterface isEmpty : ()Z
    //   430: ifne -> 824
    //   433: new java/lang/StringBuilder
    //   436: dup
    //   437: invokespecial <init> : ()V
    //   440: astore_2
    //   441: aload_2
    //   442: ldc_w '\\n'
    //   445: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: pop
    //   449: aload_2
    //   450: aload #6
    //   452: invokevirtual name : ()Ljava/lang/String;
    //   455: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   458: pop
    //   459: aload_2
    //   460: ldc_w ' -> '
    //   463: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   466: pop
    //   467: aload_2
    //   468: aload #7
    //   470: invokevirtual name : ()Ljava/lang/String;
    //   473: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   476: pop
    //   477: aload_2
    //   478: ldc_w '\\n'
    //   481: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   484: pop
    //   485: aload_2
    //   486: invokevirtual toString : ()Ljava/lang/String;
    //   489: astore #6
    //   491: new java/lang/StringBuilder
    //   494: dup
    //   495: invokespecial <init> : ()V
    //   498: astore_2
    //   499: aload_2
    //   500: aload #5
    //   502: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   505: pop
    //   506: aload_2
    //   507: aload #6
    //   509: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   512: pop
    //   513: aload_2
    //   514: invokevirtual toString : ()Ljava/lang/String;
    //   517: astore_2
    //   518: goto -> 824
    //   521: aload_1
    //   522: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   525: invokevirtual getHorizontalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   528: astore #8
    //   530: aload #8
    //   532: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   535: if_acmpeq -> 589
    //   538: aload #8
    //   540: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   543: if_acmpne -> 549
    //   546: goto -> 589
    //   549: aload #5
    //   551: astore_2
    //   552: aload #8
    //   554: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   557: if_acmpne -> 824
    //   560: aload #5
    //   562: astore_2
    //   563: aload_1
    //   564: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   567: invokevirtual getDimensionRatio : ()F
    //   570: fconst_0
    //   571: fcmpl
    //   572: ifle -> 824
    //   575: aload_1
    //   576: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   579: invokevirtual getDebugName : ()Ljava/lang/String;
    //   582: pop
    //   583: aload #5
    //   585: astore_2
    //   586: goto -> 824
    //   589: aload #6
    //   591: getfield targets : Ljava/util/List;
    //   594: invokeinterface isEmpty : ()Z
    //   599: ifne -> 705
    //   602: aload #7
    //   604: getfield targets : Ljava/util/List;
    //   607: invokeinterface isEmpty : ()Z
    //   612: ifeq -> 705
    //   615: new java/lang/StringBuilder
    //   618: dup
    //   619: invokespecial <init> : ()V
    //   622: astore_2
    //   623: aload_2
    //   624: ldc_w '\\n'
    //   627: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   630: pop
    //   631: aload_2
    //   632: aload #7
    //   634: invokevirtual name : ()Ljava/lang/String;
    //   637: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   640: pop
    //   641: aload_2
    //   642: ldc_w ' -> '
    //   645: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   648: pop
    //   649: aload_2
    //   650: aload #6
    //   652: invokevirtual name : ()Ljava/lang/String;
    //   655: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   658: pop
    //   659: aload_2
    //   660: ldc_w '\\n'
    //   663: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   666: pop
    //   667: aload_2
    //   668: invokevirtual toString : ()Ljava/lang/String;
    //   671: astore_2
    //   672: new java/lang/StringBuilder
    //   675: dup
    //   676: invokespecial <init> : ()V
    //   679: astore #6
    //   681: aload #6
    //   683: aload #5
    //   685: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   688: pop
    //   689: aload #6
    //   691: aload_2
    //   692: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   695: pop
    //   696: aload #6
    //   698: invokevirtual toString : ()Ljava/lang/String;
    //   701: astore_2
    //   702: goto -> 824
    //   705: aload #5
    //   707: astore_2
    //   708: aload #6
    //   710: getfield targets : Ljava/util/List;
    //   713: invokeinterface isEmpty : ()Z
    //   718: ifeq -> 824
    //   721: aload #5
    //   723: astore_2
    //   724: aload #7
    //   726: getfield targets : Ljava/util/List;
    //   729: invokeinterface isEmpty : ()Z
    //   734: ifne -> 824
    //   737: new java/lang/StringBuilder
    //   740: dup
    //   741: invokespecial <init> : ()V
    //   744: astore_2
    //   745: aload_2
    //   746: ldc_w '\\n'
    //   749: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   752: pop
    //   753: aload_2
    //   754: aload #6
    //   756: invokevirtual name : ()Ljava/lang/String;
    //   759: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   762: pop
    //   763: aload_2
    //   764: ldc_w ' -> '
    //   767: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   770: pop
    //   771: aload_2
    //   772: aload #7
    //   774: invokevirtual name : ()Ljava/lang/String;
    //   777: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   780: pop
    //   781: aload_2
    //   782: ldc_w '\\n'
    //   785: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   788: pop
    //   789: aload_2
    //   790: invokevirtual toString : ()Ljava/lang/String;
    //   793: astore_2
    //   794: new java/lang/StringBuilder
    //   797: dup
    //   798: invokespecial <init> : ()V
    //   801: astore #6
    //   803: aload #6
    //   805: aload #5
    //   807: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   810: pop
    //   811: aload #6
    //   813: aload_2
    //   814: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   817: pop
    //   818: aload #6
    //   820: invokevirtual toString : ()Ljava/lang/String;
    //   823: astore_2
    //   824: aload_1
    //   825: instanceof androidx/constraintlayout/solver/widgets/analyzer/ChainRun
    //   828: ifeq -> 841
    //   831: aload_0
    //   832: aload_1
    //   833: checkcast androidx/constraintlayout/solver/widgets/analyzer/ChainRun
    //   836: aload_2
    //   837: invokespecial generateChainDisplayGraph : (Landroidx/constraintlayout/solver/widgets/analyzer/ChainRun;Ljava/lang/String;)Ljava/lang/String;
    //   840: areturn
    //   841: aload_2
    //   842: areturn
  }
  
  private String generateDisplayNode(DependencyNode paramDependencyNode, boolean paramBoolean, String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: getfield targets : Ljava/util/List;
    //   4: invokeinterface iterator : ()Ljava/util/Iterator;
    //   9: astore #6
    //   11: aload_3
    //   12: astore #5
    //   14: aload #6
    //   16: invokeinterface hasNext : ()Z
    //   21: ifeq -> 431
    //   24: aload #6
    //   26: invokeinterface next : ()Ljava/lang/Object;
    //   31: checkcast androidx/constraintlayout/solver/widgets/analyzer/DependencyNode
    //   34: astore_3
    //   35: new java/lang/StringBuilder
    //   38: dup
    //   39: invokespecial <init> : ()V
    //   42: astore #4
    //   44: aload #4
    //   46: ldc_w '\\n'
    //   49: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload #4
    //   55: aload_1
    //   56: invokevirtual name : ()Ljava/lang/String;
    //   59: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload #4
    //   65: invokevirtual toString : ()Ljava/lang/String;
    //   68: astore #4
    //   70: new java/lang/StringBuilder
    //   73: dup
    //   74: invokespecial <init> : ()V
    //   77: astore #7
    //   79: aload #7
    //   81: aload #4
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: aload #7
    //   89: ldc_w ' -> '
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload #7
    //   98: aload_3
    //   99: invokevirtual name : ()Ljava/lang/String;
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload #7
    //   108: invokevirtual toString : ()Ljava/lang/String;
    //   111: astore #4
    //   113: aload_1
    //   114: getfield margin : I
    //   117: ifgt -> 137
    //   120: iload_2
    //   121: ifne -> 137
    //   124: aload #4
    //   126: astore_3
    //   127: aload_1
    //   128: getfield run : Landroidx/constraintlayout/solver/widgets/analyzer/WidgetRun;
    //   131: instanceof androidx/constraintlayout/solver/widgets/analyzer/HelperReferences
    //   134: ifeq -> 368
    //   137: new java/lang/StringBuilder
    //   140: dup
    //   141: invokespecial <init> : ()V
    //   144: astore_3
    //   145: aload_3
    //   146: aload #4
    //   148: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: pop
    //   152: aload_3
    //   153: ldc_w '['
    //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload_3
    //   161: invokevirtual toString : ()Ljava/lang/String;
    //   164: astore #4
    //   166: aload #4
    //   168: astore_3
    //   169: aload_1
    //   170: getfield margin : I
    //   173: ifle -> 257
    //   176: new java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial <init> : ()V
    //   183: astore_3
    //   184: aload_3
    //   185: aload #4
    //   187: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload_3
    //   192: ldc_w 'label="'
    //   195: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: aload_3
    //   200: aload_1
    //   201: getfield margin : I
    //   204: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: aload_3
    //   209: ldc_w '"'
    //   212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: pop
    //   216: aload_3
    //   217: invokevirtual toString : ()Ljava/lang/String;
    //   220: astore #4
    //   222: aload #4
    //   224: astore_3
    //   225: iload_2
    //   226: ifeq -> 257
    //   229: new java/lang/StringBuilder
    //   232: dup
    //   233: invokespecial <init> : ()V
    //   236: astore_3
    //   237: aload_3
    //   238: aload #4
    //   240: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   243: pop
    //   244: aload_3
    //   245: ldc_w ','
    //   248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: pop
    //   252: aload_3
    //   253: invokevirtual toString : ()Ljava/lang/String;
    //   256: astore_3
    //   257: aload_3
    //   258: astore #4
    //   260: iload_2
    //   261: ifeq -> 296
    //   264: new java/lang/StringBuilder
    //   267: dup
    //   268: invokespecial <init> : ()V
    //   271: astore #4
    //   273: aload #4
    //   275: aload_3
    //   276: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: pop
    //   280: aload #4
    //   282: ldc_w ' style=dashed '
    //   285: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: pop
    //   289: aload #4
    //   291: invokevirtual toString : ()Ljava/lang/String;
    //   294: astore #4
    //   296: aload #4
    //   298: astore_3
    //   299: aload_1
    //   300: getfield run : Landroidx/constraintlayout/solver/widgets/analyzer/WidgetRun;
    //   303: instanceof androidx/constraintlayout/solver/widgets/analyzer/HelperReferences
    //   306: ifeq -> 337
    //   309: new java/lang/StringBuilder
    //   312: dup
    //   313: invokespecial <init> : ()V
    //   316: astore_3
    //   317: aload_3
    //   318: aload #4
    //   320: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   323: pop
    //   324: aload_3
    //   325: ldc_w ' style=bold,color=gray '
    //   328: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: pop
    //   332: aload_3
    //   333: invokevirtual toString : ()Ljava/lang/String;
    //   336: astore_3
    //   337: new java/lang/StringBuilder
    //   340: dup
    //   341: invokespecial <init> : ()V
    //   344: astore #4
    //   346: aload #4
    //   348: aload_3
    //   349: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   352: pop
    //   353: aload #4
    //   355: ldc_w ']'
    //   358: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   361: pop
    //   362: aload #4
    //   364: invokevirtual toString : ()Ljava/lang/String;
    //   367: astore_3
    //   368: new java/lang/StringBuilder
    //   371: dup
    //   372: invokespecial <init> : ()V
    //   375: astore #4
    //   377: aload #4
    //   379: aload_3
    //   380: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   383: pop
    //   384: aload #4
    //   386: ldc_w '\\n'
    //   389: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   392: pop
    //   393: aload #4
    //   395: invokevirtual toString : ()Ljava/lang/String;
    //   398: astore #4
    //   400: new java/lang/StringBuilder
    //   403: dup
    //   404: invokespecial <init> : ()V
    //   407: astore_3
    //   408: aload_3
    //   409: aload #5
    //   411: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: pop
    //   415: aload_3
    //   416: aload #4
    //   418: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   421: pop
    //   422: aload_3
    //   423: invokevirtual toString : ()Ljava/lang/String;
    //   426: astore #5
    //   428: goto -> 14
    //   431: aload #5
    //   433: areturn
  }
  
  private boolean isCenteredConnection(DependencyNode paramDependencyNode1, DependencyNode paramDependencyNode2) {
    Iterator<DependencyNode> iterator2 = paramDependencyNode1.targets.iterator();
    boolean bool2 = false;
    byte b1 = 0;
    while (iterator2.hasNext()) {
      if ((DependencyNode)iterator2.next() != paramDependencyNode2)
        b1++; 
    } 
    Iterator<DependencyNode> iterator1 = paramDependencyNode2.targets.iterator();
    byte b2 = 0;
    while (iterator1.hasNext()) {
      if ((DependencyNode)iterator1.next() != paramDependencyNode1)
        b2++; 
    } 
    boolean bool1 = bool2;
    if (b1 > 0) {
      bool1 = bool2;
      if (b2 > 0)
        bool1 = true; 
    } 
    return bool1;
  }
  
  private void measure(ConstraintWidget paramConstraintWidget, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, int paramInt1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2, int paramInt2) {
    this.mMeasure.horizontalBehavior = paramDimensionBehaviour1;
    this.mMeasure.verticalBehavior = paramDimensionBehaviour2;
    this.mMeasure.horizontalDimension = paramInt1;
    this.mMeasure.verticalDimension = paramInt2;
    this.mMeasurer.measure(paramConstraintWidget, this.mMeasure);
    paramConstraintWidget.setWidth(this.mMeasure.measuredWidth);
    paramConstraintWidget.setHeight(this.mMeasure.measuredHeight);
    paramConstraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
    paramConstraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
  }
  
  private String nodeDefinition(WidgetRun paramWidgetRun) {
    ConstraintWidget.DimensionBehaviour dimensionBehaviour;
    boolean bool = paramWidgetRun instanceof VerticalWidgetRun;
    String str6 = paramWidgetRun.widget.getDebugName();
    ConstraintWidget constraintWidget = paramWidgetRun.widget;
    if (!bool) {
      dimensionBehaviour = constraintWidget.getHorizontalDimensionBehaviour();
    } else {
      dimensionBehaviour = constraintWidget.getVerticalDimensionBehaviour();
    } 
    RunGroup runGroup = paramWidgetRun.runGroup;
    if (!bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str6);
      stringBuilder.append("_HORIZONTAL");
      str3 = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str6);
      stringBuilder.append("_VERTICAL");
      str3 = stringBuilder.toString();
    } 
    StringBuilder stringBuilder5 = new StringBuilder();
    stringBuilder5.append(str3);
    stringBuilder5.append(" [shape=none, label=<");
    String str3 = stringBuilder5.toString();
    stringBuilder5 = new StringBuilder();
    stringBuilder5.append(str3);
    stringBuilder5.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">");
    String str5 = stringBuilder5.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str5);
    stringBuilder2.append("  <TR>");
    String str2 = stringBuilder2.toString();
    if (!bool) {
      StringBuilder stringBuilder7 = new StringBuilder();
      stringBuilder7.append(str2);
      stringBuilder7.append("    <TD ");
      String str = stringBuilder7.toString();
      str2 = str;
      if (paramWidgetRun.start.resolved) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" BGCOLOR=\"green\"");
        str2 = stringBuilder.toString();
      } 
      StringBuilder stringBuilder6 = new StringBuilder();
      stringBuilder6.append(str2);
      stringBuilder6.append(" PORT=\"LEFT\" BORDER=\"1\">L</TD>");
      str2 = stringBuilder6.toString();
    } else {
      StringBuilder stringBuilder7 = new StringBuilder();
      stringBuilder7.append(str2);
      stringBuilder7.append("    <TD ");
      String str = stringBuilder7.toString();
      str2 = str;
      if (paramWidgetRun.start.resolved) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" BGCOLOR=\"green\"");
        str2 = stringBuilder.toString();
      } 
      StringBuilder stringBuilder6 = new StringBuilder();
      stringBuilder6.append(str2);
      stringBuilder6.append(" PORT=\"TOP\" BORDER=\"1\">T</TD>");
      str2 = stringBuilder6.toString();
    } 
    StringBuilder stringBuilder4 = new StringBuilder();
    stringBuilder4.append(str2);
    stringBuilder4.append("    <TD BORDER=\"1\" ");
    String str4 = stringBuilder4.toString();
    if (paramWidgetRun.dimension.resolved && !paramWidgetRun.widget.measured) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str4);
      stringBuilder.append(" BGCOLOR=\"green\" ");
      String str = stringBuilder.toString();
    } else if (paramWidgetRun.dimension.resolved && paramWidgetRun.widget.measured) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str4);
      stringBuilder.append(" BGCOLOR=\"lightgray\" ");
      String str = stringBuilder.toString();
    } else {
      str2 = str4;
      if (!paramWidgetRun.dimension.resolved) {
        str2 = str4;
        if (paramWidgetRun.widget.measured) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str4);
          stringBuilder.append(" BGCOLOR=\"yellow\" ");
          str2 = stringBuilder.toString();
        } 
      } 
    } 
    str4 = str2;
    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append("style=\"dashed\"");
      str4 = stringBuilder.toString();
    } 
    if (runGroup != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(" [");
      stringBuilder.append(runGroup.groupIndex + 1);
      stringBuilder.append("/");
      stringBuilder.append(RunGroup.index);
      stringBuilder.append("]");
      String str = stringBuilder.toString();
    } else {
      str2 = "";
    } 
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append(str4);
    stringBuilder3.append(">");
    stringBuilder3.append(str6);
    stringBuilder3.append(str2);
    stringBuilder3.append(" </TD>");
    str2 = stringBuilder3.toString();
    if (!bool) {
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      stringBuilder3.append("    <TD ");
      String str = stringBuilder3.toString();
      str2 = str;
      if (paramWidgetRun.end.resolved) {
        StringBuilder stringBuilder6 = new StringBuilder();
        stringBuilder6.append(str);
        stringBuilder6.append(" BGCOLOR=\"green\"");
        str2 = stringBuilder6.toString();
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append(" PORT=\"RIGHT\" BORDER=\"1\">R</TD>");
      str1 = stringBuilder.toString();
    } else {
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      stringBuilder3.append("    <TD ");
      String str9 = stringBuilder3.toString();
      str2 = str9;
      if (bool) {
        str2 = str9;
        if (((VerticalWidgetRun)str1).baseline.resolved) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str9);
          stringBuilder.append(" BGCOLOR=\"green\"");
          str7 = stringBuilder.toString();
        } 
      } 
      StringBuilder stringBuilder7 = new StringBuilder();
      stringBuilder7.append(str7);
      stringBuilder7.append(" PORT=\"BASELINE\" BORDER=\"1\">b</TD>");
      String str7 = stringBuilder7.toString();
      stringBuilder7 = new StringBuilder();
      stringBuilder7.append(str7);
      stringBuilder7.append("    <TD ");
      String str8 = stringBuilder7.toString();
      str7 = str8;
      if (((WidgetRun)str1).end.resolved) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str8);
        stringBuilder.append(" BGCOLOR=\"green\"");
        str7 = stringBuilder.toString();
      } 
      StringBuilder stringBuilder6 = new StringBuilder();
      stringBuilder6.append(str7);
      stringBuilder6.append(" PORT=\"BOTTOM\" BORDER=\"1\">B</TD>");
      str1 = stringBuilder6.toString();
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str1);
    stringBuilder1.append("  </TR></TABLE>");
    String str1 = stringBuilder1.toString();
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str1);
    stringBuilder1.append(">];\n");
    return stringBuilder1.toString();
  }
  
  public void buildGraph() {
    buildGraph(this.mRuns);
    this.mGroups.clear();
    RunGroup.index = 0;
    findGroup(this.container.horizontalRun, 0, this.mGroups);
    findGroup(this.container.verticalRun, 1, this.mGroups);
    this.mNeedBuildGraph = false;
  }
  
  public void buildGraph(ArrayList<WidgetRun> paramArrayList) {
    paramArrayList.clear();
    this.mContainer.horizontalRun.clear();
    this.mContainer.verticalRun.clear();
    paramArrayList.add(this.mContainer.horizontalRun);
    paramArrayList.add(this.mContainer.verticalRun);
    Iterator<ConstraintWidget> iterator = this.mContainer.mChildren.iterator();
    HashSet<ChainRun> hashSet = null;
    while (iterator.hasNext()) {
      HashSet<ChainRun> hashSet1;
      ConstraintWidget constraintWidget = iterator.next();
      if (constraintWidget instanceof androidx.constraintlayout.solver.widgets.Guideline) {
        paramArrayList.add(new GuidelineReference(constraintWidget));
        continue;
      } 
      if (constraintWidget.isInHorizontalChain()) {
        if (constraintWidget.horizontalChainRun == null)
          constraintWidget.horizontalChainRun = new ChainRun(constraintWidget, 0); 
        hashSet1 = hashSet;
        if (hashSet == null)
          hashSet1 = new HashSet(); 
        hashSet1.add(constraintWidget.horizontalChainRun);
        hashSet = hashSet1;
      } else {
        paramArrayList.add(constraintWidget.horizontalRun);
      } 
      if (constraintWidget.isInVerticalChain()) {
        if (constraintWidget.verticalChainRun == null)
          constraintWidget.verticalChainRun = new ChainRun(constraintWidget, 1); 
        hashSet1 = hashSet;
        if (hashSet == null)
          hashSet1 = new HashSet<ChainRun>(); 
        hashSet1.add(constraintWidget.verticalChainRun);
      } else {
        paramArrayList.add(constraintWidget.verticalRun);
        hashSet1 = hashSet;
      } 
      hashSet = hashSet1;
      if (constraintWidget instanceof androidx.constraintlayout.solver.widgets.HelperWidget) {
        paramArrayList.add(new HelperReferences(constraintWidget));
        hashSet = hashSet1;
      } 
    } 
    if (hashSet != null)
      paramArrayList.addAll((Collection)hashSet); 
    null = paramArrayList.iterator();
    while (null.hasNext())
      ((WidgetRun)null.next()).clear(); 
    for (WidgetRun widgetRun : paramArrayList) {
      if (widgetRun.widget == this.mContainer)
        continue; 
      widgetRun.apply();
    } 
  }
  
  public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2) {
    if (this.mNeedBuildGraph) {
      buildGraph();
      Iterator<ConstraintWidget> iterator = this.container.mChildren.iterator();
      boolean bool = false;
      while (iterator.hasNext()) {
        ConstraintWidget constraintWidget = iterator.next();
        constraintWidget.isTerminalWidget[0] = true;
        constraintWidget.isTerminalWidget[1] = true;
        if (constraintWidget instanceof androidx.constraintlayout.solver.widgets.Barrier)
          bool = true; 
      } 
      if (!bool)
        for (RunGroup runGroup : this.mGroups) {
          boolean bool1;
          boolean bool2;
          if (paramDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            bool1 = true;
          } else {
            bool1 = false;
          } 
          if (paramDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            bool2 = true;
          } else {
            bool2 = false;
          } 
          runGroup.defineTerminalWidgets(bool1, bool2);
        }  
    } 
  }
  
  public boolean directMeasure(boolean paramBoolean) {
    boolean bool1;
    boolean bool2 = true;
    int i = paramBoolean & true;
    if (this.mNeedBuildGraph || this.mNeedRedoMeasures) {
      for (ConstraintWidget constraintWidget : this.container.mChildren) {
        constraintWidget.ensureWidgetRuns();
        constraintWidget.measured = false;
        constraintWidget.horizontalRun.reset();
        constraintWidget.verticalRun.reset();
      } 
      this.container.ensureWidgetRuns();
      this.container.measured = false;
      this.container.horizontalRun.reset();
      this.container.verticalRun.reset();
      this.mNeedRedoMeasures = false;
    } 
    if (basicMeasureWidgets(this.mContainer))
      return false; 
    this.container.setX(0);
    this.container.setY(0);
    ConstraintWidget.DimensionBehaviour dimensionBehaviour1 = this.container.getDimensionBehaviour(0);
    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.container.getDimensionBehaviour(1);
    if (this.mNeedBuildGraph)
      buildGraph(); 
    int k = this.container.getX();
    int j = this.container.getY();
    this.container.horizontalRun.start.resolve(k);
    this.container.verticalRun.start.resolve(j);
    measureWidgets();
    if (dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      bool1 = i;
      if (i != 0) {
        Iterator<WidgetRun> iterator1 = this.mRuns.iterator();
        while (true) {
          bool1 = i;
          if (iterator1.hasNext()) {
            if (!((WidgetRun)iterator1.next()).supportsWrapComputation()) {
              bool1 = false;
              break;
            } 
            continue;
          } 
          break;
        } 
      } 
      if (bool1 != 0 && dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        ConstraintWidgetContainer constraintWidgetContainer = this.container;
        constraintWidgetContainer.setWidth(computeWrap(constraintWidgetContainer, 0));
        this.container.horizontalRun.dimension.resolve(this.container.getWidth());
      } 
      if (bool1 != 0 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        ConstraintWidgetContainer constraintWidgetContainer = this.container;
        constraintWidgetContainer.setHeight(computeWrap(constraintWidgetContainer, 1));
        this.container.verticalRun.dimension.resolve(this.container.getHeight());
      } 
    } 
    if (this.container.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || this.container.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
      bool1 = this.container.getWidth() + k;
      this.container.horizontalRun.end.resolve(bool1);
      this.container.horizontalRun.dimension.resolve(bool1 - k);
      measureWidgets();
      if (this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
        bool1 = this.container.getHeight() + j;
        this.container.verticalRun.end.resolve(bool1);
        this.container.verticalRun.dimension.resolve(bool1 - j);
      } 
      measureWidgets();
      bool1 = true;
    } else {
      bool1 = false;
    } 
    for (WidgetRun widgetRun : this.mRuns) {
      if (widgetRun.widget == this.container && !widgetRun.resolved)
        continue; 
      widgetRun.applyToWidget();
    } 
    Iterator<WidgetRun> iterator = this.mRuns.iterator();
    while (true) {
      paramBoolean = bool2;
      if (iterator.hasNext()) {
        WidgetRun widgetRun = iterator.next();
        if ((bool1 || widgetRun.widget != this.container) && (!widgetRun.start.resolved || (!widgetRun.end.resolved && !(widgetRun instanceof GuidelineReference)) || (!widgetRun.dimension.resolved && !(widgetRun instanceof ChainRun) && !(widgetRun instanceof GuidelineReference)))) {
          paramBoolean = false;
          break;
        } 
        continue;
      } 
      break;
    } 
    this.container.setHorizontalDimensionBehaviour(dimensionBehaviour1);
    this.container.setVerticalDimensionBehaviour(dimensionBehaviour2);
    return paramBoolean;
  }
  
  public boolean directMeasureSetup(boolean paramBoolean) {
    if (this.mNeedBuildGraph) {
      for (ConstraintWidget constraintWidget : this.container.mChildren) {
        constraintWidget.ensureWidgetRuns();
        constraintWidget.measured = false;
        constraintWidget.horizontalRun.dimension.resolved = false;
        constraintWidget.horizontalRun.resolved = false;
        constraintWidget.horizontalRun.reset();
        constraintWidget.verticalRun.dimension.resolved = false;
        constraintWidget.verticalRun.resolved = false;
        constraintWidget.verticalRun.reset();
      } 
      this.container.ensureWidgetRuns();
      this.container.measured = false;
      this.container.horizontalRun.dimension.resolved = false;
      this.container.horizontalRun.resolved = false;
      this.container.horizontalRun.reset();
      this.container.verticalRun.dimension.resolved = false;
      this.container.verticalRun.resolved = false;
      this.container.verticalRun.reset();
      buildGraph();
    } 
    if (basicMeasureWidgets(this.mContainer))
      return false; 
    this.container.setX(0);
    this.container.setY(0);
    this.container.horizontalRun.start.resolve(0);
    this.container.verticalRun.start.resolve(0);
    return true;
  }
  
  public boolean directMeasureWithOrientation(boolean paramBoolean, int paramInt) {
    // Byte code:
    //   0: iconst_1
    //   1: istore #7
    //   3: iload_1
    //   4: iconst_1
    //   5: iand
    //   6: istore #4
    //   8: aload_0
    //   9: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   12: iconst_0
    //   13: invokevirtual getDimensionBehaviour : (I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   16: astore #8
    //   18: aload_0
    //   19: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   22: iconst_1
    //   23: invokevirtual getDimensionBehaviour : (I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   26: astore #9
    //   28: aload_0
    //   29: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   32: invokevirtual getX : ()I
    //   35: istore #6
    //   37: aload_0
    //   38: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   41: invokevirtual getY : ()I
    //   44: istore #5
    //   46: iload #4
    //   48: ifeq -> 247
    //   51: aload #8
    //   53: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   56: if_acmpeq -> 67
    //   59: aload #9
    //   61: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   64: if_acmpne -> 247
    //   67: aload_0
    //   68: getfield mRuns : Ljava/util/ArrayList;
    //   71: invokevirtual iterator : ()Ljava/util/Iterator;
    //   74: astore #11
    //   76: iload #4
    //   78: istore_3
    //   79: aload #11
    //   81: invokeinterface hasNext : ()Z
    //   86: ifeq -> 120
    //   89: aload #11
    //   91: invokeinterface next : ()Ljava/lang/Object;
    //   96: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   99: astore #10
    //   101: aload #10
    //   103: getfield orientation : I
    //   106: iload_2
    //   107: if_icmpne -> 76
    //   110: aload #10
    //   112: invokevirtual supportsWrapComputation : ()Z
    //   115: ifne -> 76
    //   118: iconst_0
    //   119: istore_3
    //   120: iload_2
    //   121: ifne -> 187
    //   124: iload_3
    //   125: ifeq -> 247
    //   128: aload #8
    //   130: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   133: if_acmpne -> 247
    //   136: aload_0
    //   137: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   140: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   143: invokevirtual setHorizontalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   146: aload_0
    //   147: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   150: astore #10
    //   152: aload #10
    //   154: aload_0
    //   155: aload #10
    //   157: iconst_0
    //   158: invokespecial computeWrap : (Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;I)I
    //   161: invokevirtual setWidth : (I)V
    //   164: aload_0
    //   165: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   168: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   171: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   174: aload_0
    //   175: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   178: invokevirtual getWidth : ()I
    //   181: invokevirtual resolve : (I)V
    //   184: goto -> 247
    //   187: iload_3
    //   188: ifeq -> 247
    //   191: aload #9
    //   193: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   196: if_acmpne -> 247
    //   199: aload_0
    //   200: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   203: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   206: invokevirtual setVerticalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   209: aload_0
    //   210: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   213: astore #10
    //   215: aload #10
    //   217: aload_0
    //   218: aload #10
    //   220: iconst_1
    //   221: invokespecial computeWrap : (Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;I)I
    //   224: invokevirtual setHeight : (I)V
    //   227: aload_0
    //   228: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   231: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   234: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   237: aload_0
    //   238: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   241: invokevirtual getHeight : ()I
    //   244: invokevirtual resolve : (I)V
    //   247: iload_2
    //   248: ifne -> 326
    //   251: aload_0
    //   252: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   255: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   258: iconst_0
    //   259: aaload
    //   260: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   263: if_acmpeq -> 281
    //   266: aload_0
    //   267: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   270: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   273: iconst_0
    //   274: aaload
    //   275: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   278: if_acmpne -> 359
    //   281: aload_0
    //   282: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   285: invokevirtual getWidth : ()I
    //   288: iload #6
    //   290: iadd
    //   291: istore_3
    //   292: aload_0
    //   293: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   296: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   299: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   302: iload_3
    //   303: invokevirtual resolve : (I)V
    //   306: aload_0
    //   307: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   310: getfield horizontalRun : Landroidx/constraintlayout/solver/widgets/analyzer/HorizontalWidgetRun;
    //   313: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   316: iload_3
    //   317: iload #6
    //   319: isub
    //   320: invokevirtual resolve : (I)V
    //   323: goto -> 406
    //   326: aload_0
    //   327: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   330: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   333: iconst_1
    //   334: aaload
    //   335: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   338: if_acmpeq -> 364
    //   341: aload_0
    //   342: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   345: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   348: iconst_1
    //   349: aaload
    //   350: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_PARENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   353: if_acmpne -> 359
    //   356: goto -> 364
    //   359: iconst_0
    //   360: istore_3
    //   361: goto -> 408
    //   364: aload_0
    //   365: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   368: invokevirtual getHeight : ()I
    //   371: iload #5
    //   373: iadd
    //   374: istore_3
    //   375: aload_0
    //   376: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   379: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   382: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   385: iload_3
    //   386: invokevirtual resolve : (I)V
    //   389: aload_0
    //   390: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   393: getfield verticalRun : Landroidx/constraintlayout/solver/widgets/analyzer/VerticalWidgetRun;
    //   396: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   399: iload_3
    //   400: iload #5
    //   402: isub
    //   403: invokevirtual resolve : (I)V
    //   406: iconst_1
    //   407: istore_3
    //   408: aload_0
    //   409: invokevirtual measureWidgets : ()V
    //   412: aload_0
    //   413: getfield mRuns : Ljava/util/ArrayList;
    //   416: invokevirtual iterator : ()Ljava/util/Iterator;
    //   419: astore #11
    //   421: aload #11
    //   423: invokeinterface hasNext : ()Z
    //   428: ifeq -> 486
    //   431: aload #11
    //   433: invokeinterface next : ()Ljava/lang/Object;
    //   438: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   441: astore #10
    //   443: aload #10
    //   445: getfield orientation : I
    //   448: iload_2
    //   449: if_icmpeq -> 455
    //   452: goto -> 421
    //   455: aload #10
    //   457: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   460: aload_0
    //   461: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   464: if_acmpne -> 478
    //   467: aload #10
    //   469: getfield resolved : Z
    //   472: ifne -> 478
    //   475: goto -> 421
    //   478: aload #10
    //   480: invokevirtual applyToWidget : ()V
    //   483: goto -> 421
    //   486: aload_0
    //   487: getfield mRuns : Ljava/util/ArrayList;
    //   490: invokevirtual iterator : ()Ljava/util/Iterator;
    //   493: astore #11
    //   495: iload #7
    //   497: istore_1
    //   498: aload #11
    //   500: invokeinterface hasNext : ()Z
    //   505: ifeq -> 603
    //   508: aload #11
    //   510: invokeinterface next : ()Ljava/lang/Object;
    //   515: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetRun
    //   518: astore #10
    //   520: aload #10
    //   522: getfield orientation : I
    //   525: iload_2
    //   526: if_icmpeq -> 532
    //   529: goto -> 495
    //   532: iload_3
    //   533: ifne -> 551
    //   536: aload #10
    //   538: getfield widget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   541: aload_0
    //   542: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   545: if_acmpne -> 551
    //   548: goto -> 495
    //   551: aload #10
    //   553: getfield start : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   556: getfield resolved : Z
    //   559: ifne -> 567
    //   562: iconst_0
    //   563: istore_1
    //   564: goto -> 603
    //   567: aload #10
    //   569: getfield end : Landroidx/constraintlayout/solver/widgets/analyzer/DependencyNode;
    //   572: getfield resolved : Z
    //   575: ifne -> 581
    //   578: goto -> 562
    //   581: aload #10
    //   583: instanceof androidx/constraintlayout/solver/widgets/analyzer/ChainRun
    //   586: ifne -> 495
    //   589: aload #10
    //   591: getfield dimension : Landroidx/constraintlayout/solver/widgets/analyzer/DimensionDependency;
    //   594: getfield resolved : Z
    //   597: ifne -> 495
    //   600: goto -> 562
    //   603: aload_0
    //   604: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   607: aload #8
    //   609: invokevirtual setHorizontalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   612: aload_0
    //   613: getfield container : Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   616: aload #9
    //   618: invokevirtual setVerticalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   621: iload_1
    //   622: ireturn
  }
  
  public void invalidateGraph() {
    this.mNeedBuildGraph = true;
  }
  
  public void invalidateMeasures() {
    this.mNeedRedoMeasures = true;
  }
  
  public void measureWidgets() {
    Iterator iterator = this.container.mChildren.iterator();
    while (true) {
      while (true)
        break; 
      if (((ConstraintWidget)SYNTHETIC_LOCAL_VARIABLE_7).measured && ((ConstraintWidget)SYNTHETIC_LOCAL_VARIABLE_7).verticalRun.baselineDimension != null)
        ((ConstraintWidget)SYNTHETIC_LOCAL_VARIABLE_7).verticalRun.baselineDimension.resolve(SYNTHETIC_LOCAL_VARIABLE_7.getBaselineDistance()); 
    } 
  }
  
  public void setMeasurer(BasicMeasure.Measurer paramMeasurer) {
    this.mMeasurer = paramMeasurer;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\DependencyGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */