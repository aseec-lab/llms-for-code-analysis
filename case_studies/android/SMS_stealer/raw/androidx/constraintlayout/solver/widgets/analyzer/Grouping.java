package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.ArrayList;

public class Grouping {
  private static final boolean DEBUG = false;
  
  private static final boolean DEBUG_GROUPING = false;
  
  public static WidgetGroup findDependents(ConstraintWidget paramConstraintWidget, int paramInt, ArrayList<WidgetGroup> paramArrayList, WidgetGroup paramWidgetGroup) {
    int i;
    WidgetGroup widgetGroup;
    if (paramInt == 0) {
      i = paramConstraintWidget.horizontalGroup;
    } else {
      i = paramConstraintWidget.verticalGroup;
    } 
    boolean bool = false;
    if (i != -1 && (paramWidgetGroup == null || i != paramWidgetGroup.id)) {
      byte b = 0;
      while (true) {
        widgetGroup = paramWidgetGroup;
        if (b < paramArrayList.size()) {
          widgetGroup = paramArrayList.get(b);
          if (widgetGroup.getId() == i) {
            if (paramWidgetGroup != null) {
              paramWidgetGroup.moveTo(paramInt, widgetGroup);
              paramArrayList.remove(paramWidgetGroup);
            } 
            break;
          } 
          b++;
          continue;
        } 
        break;
      } 
    } else {
      widgetGroup = paramWidgetGroup;
      if (i != -1)
        return paramWidgetGroup; 
    } 
    paramWidgetGroup = widgetGroup;
    if (widgetGroup == null) {
      paramWidgetGroup = widgetGroup;
      if (paramConstraintWidget instanceof HelperWidget) {
        int j = ((HelperWidget)paramConstraintWidget).findGroupInDependents(paramInt);
        paramWidgetGroup = widgetGroup;
        if (j != -1) {
          i = 0;
          while (true) {
            paramWidgetGroup = widgetGroup;
            if (i < paramArrayList.size()) {
              paramWidgetGroup = paramArrayList.get(i);
              if (paramWidgetGroup.getId() == j)
                break; 
              i++;
              continue;
            } 
            break;
          } 
        } 
      } 
      widgetGroup = paramWidgetGroup;
      if (paramWidgetGroup == null)
        widgetGroup = new WidgetGroup(paramInt); 
      paramArrayList.add(widgetGroup);
      paramWidgetGroup = widgetGroup;
    } 
    if (paramWidgetGroup.add(paramConstraintWidget)) {
      if (paramConstraintWidget instanceof Guideline) {
        Guideline guideline = (Guideline)paramConstraintWidget;
        ConstraintAnchor constraintAnchor = guideline.getAnchor();
        i = bool;
        if (guideline.getOrientation() == 0)
          i = 1; 
        constraintAnchor.findDependents(i, paramArrayList, paramWidgetGroup);
      } 
      if (paramInt == 0) {
        paramConstraintWidget.horizontalGroup = paramWidgetGroup.getId();
        paramConstraintWidget.mLeft.findDependents(paramInt, paramArrayList, paramWidgetGroup);
        paramConstraintWidget.mRight.findDependents(paramInt, paramArrayList, paramWidgetGroup);
      } else {
        paramConstraintWidget.verticalGroup = paramWidgetGroup.getId();
        paramConstraintWidget.mTop.findDependents(paramInt, paramArrayList, paramWidgetGroup);
        paramConstraintWidget.mBaseline.findDependents(paramInt, paramArrayList, paramWidgetGroup);
        paramConstraintWidget.mBottom.findDependents(paramInt, paramArrayList, paramWidgetGroup);
      } 
      paramConstraintWidget.mCenter.findDependents(paramInt, paramArrayList, paramWidgetGroup);
    } 
    return paramWidgetGroup;
  }
  
  private static WidgetGroup findGroup(ArrayList<WidgetGroup> paramArrayList, int paramInt) {
    int i = paramArrayList.size();
    for (byte b = 0; b < i; b++) {
      WidgetGroup widgetGroup = paramArrayList.get(b);
      if (paramInt == widgetGroup.id)
        return widgetGroup; 
    } 
    return null;
  }
  
  public static boolean simpleSolvingPass(ConstraintWidgetContainer paramConstraintWidgetContainer, BasicMeasure.Measurer paramMeasurer) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getChildren : ()Ljava/util/ArrayList;
    //   4: astore #16
    //   6: aload #16
    //   8: invokevirtual size : ()I
    //   11: istore_3
    //   12: iconst_0
    //   13: istore_2
    //   14: iload_2
    //   15: iload_3
    //   16: if_icmpge -> 72
    //   19: aload #16
    //   21: iload_2
    //   22: invokevirtual get : (I)Ljava/lang/Object;
    //   25: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   28: astore #5
    //   30: aload_0
    //   31: invokevirtual getHorizontalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   34: aload_0
    //   35: invokevirtual getVerticalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   38: aload #5
    //   40: invokevirtual getHorizontalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   43: aload #5
    //   45: invokevirtual getVerticalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   48: invokestatic validInGroup : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)Z
    //   51: ifne -> 56
    //   54: iconst_0
    //   55: ireturn
    //   56: aload #5
    //   58: instanceof androidx/constraintlayout/solver/widgets/Flow
    //   61: ifeq -> 66
    //   64: iconst_0
    //   65: ireturn
    //   66: iinc #2, 1
    //   69: goto -> 14
    //   72: aload_0
    //   73: getfield mMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   76: ifnull -> 97
    //   79: aload_0
    //   80: getfield mMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   83: astore #5
    //   85: aload #5
    //   87: aload #5
    //   89: getfield grouping : J
    //   92: lconst_1
    //   93: ladd
    //   94: putfield grouping : J
    //   97: iconst_0
    //   98: istore_2
    //   99: aconst_null
    //   100: astore #11
    //   102: aconst_null
    //   103: astore #5
    //   105: aconst_null
    //   106: astore #7
    //   108: aconst_null
    //   109: astore #6
    //   111: aconst_null
    //   112: astore #9
    //   114: aconst_null
    //   115: astore #8
    //   117: iload_2
    //   118: iload_3
    //   119: if_icmpge -> 675
    //   122: aload #16
    //   124: iload_2
    //   125: invokevirtual get : (I)Ljava/lang/Object;
    //   128: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   131: astore #17
    //   133: aload_0
    //   134: invokevirtual getHorizontalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   137: aload_0
    //   138: invokevirtual getVerticalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   141: aload #17
    //   143: invokevirtual getHorizontalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   146: aload #17
    //   148: invokevirtual getVerticalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   151: invokestatic validInGroup : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)Z
    //   154: ifne -> 174
    //   157: aload #17
    //   159: aload_1
    //   160: aload_0
    //   161: getfield mMeasure : Landroidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure;
    //   164: getstatic androidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure.SELF_DIMENSIONS : I
    //   167: invokestatic measure : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measurer;Landroidx/constraintlayout/solver/widgets/analyzer/BasicMeasure$Measure;I)Z
    //   170: pop
    //   171: goto -> 174
    //   174: aload #17
    //   176: instanceof androidx/constraintlayout/solver/widgets/Guideline
    //   179: istore #4
    //   181: aload #11
    //   183: astore #13
    //   185: aload #7
    //   187: astore #12
    //   189: iload #4
    //   191: ifeq -> 290
    //   194: aload #17
    //   196: checkcast androidx/constraintlayout/solver/widgets/Guideline
    //   199: astore #14
    //   201: aload #7
    //   203: astore #10
    //   205: aload #14
    //   207: invokevirtual getOrientation : ()I
    //   210: ifne -> 239
    //   213: aload #7
    //   215: astore #10
    //   217: aload #7
    //   219: ifnonnull -> 231
    //   222: new java/util/ArrayList
    //   225: dup
    //   226: invokespecial <init> : ()V
    //   229: astore #10
    //   231: aload #10
    //   233: aload #14
    //   235: invokevirtual add : (Ljava/lang/Object;)Z
    //   238: pop
    //   239: aload #11
    //   241: astore #13
    //   243: aload #10
    //   245: astore #12
    //   247: aload #14
    //   249: invokevirtual getOrientation : ()I
    //   252: iconst_1
    //   253: if_icmpne -> 290
    //   256: aload #11
    //   258: astore #7
    //   260: aload #11
    //   262: ifnonnull -> 274
    //   265: new java/util/ArrayList
    //   268: dup
    //   269: invokespecial <init> : ()V
    //   272: astore #7
    //   274: aload #7
    //   276: aload #14
    //   278: invokevirtual add : (Ljava/lang/Object;)Z
    //   281: pop
    //   282: aload #10
    //   284: astore #12
    //   286: aload #7
    //   288: astore #13
    //   290: aload #5
    //   292: astore #7
    //   294: aload #6
    //   296: astore #10
    //   298: aload #17
    //   300: instanceof androidx/constraintlayout/solver/widgets/HelperWidget
    //   303: ifeq -> 468
    //   306: aload #17
    //   308: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   311: ifeq -> 409
    //   314: aload #17
    //   316: checkcast androidx/constraintlayout/solver/widgets/Barrier
    //   319: astore #14
    //   321: aload #5
    //   323: astore #11
    //   325: aload #14
    //   327: invokevirtual getOrientation : ()I
    //   330: ifne -> 359
    //   333: aload #5
    //   335: astore #11
    //   337: aload #5
    //   339: ifnonnull -> 351
    //   342: new java/util/ArrayList
    //   345: dup
    //   346: invokespecial <init> : ()V
    //   349: astore #11
    //   351: aload #11
    //   353: aload #14
    //   355: invokevirtual add : (Ljava/lang/Object;)Z
    //   358: pop
    //   359: aload #11
    //   361: astore #7
    //   363: aload #6
    //   365: astore #10
    //   367: aload #14
    //   369: invokevirtual getOrientation : ()I
    //   372: iconst_1
    //   373: if_icmpne -> 468
    //   376: aload #6
    //   378: astore #10
    //   380: aload #6
    //   382: ifnonnull -> 394
    //   385: new java/util/ArrayList
    //   388: dup
    //   389: invokespecial <init> : ()V
    //   392: astore #10
    //   394: aload #10
    //   396: aload #14
    //   398: invokevirtual add : (Ljava/lang/Object;)Z
    //   401: pop
    //   402: aload #11
    //   404: astore #7
    //   406: goto -> 468
    //   409: aload #17
    //   411: checkcast androidx/constraintlayout/solver/widgets/HelperWidget
    //   414: astore #11
    //   416: aload #5
    //   418: astore #7
    //   420: aload #5
    //   422: ifnonnull -> 434
    //   425: new java/util/ArrayList
    //   428: dup
    //   429: invokespecial <init> : ()V
    //   432: astore #7
    //   434: aload #7
    //   436: aload #11
    //   438: invokevirtual add : (Ljava/lang/Object;)Z
    //   441: pop
    //   442: aload #6
    //   444: astore #10
    //   446: aload #6
    //   448: ifnonnull -> 460
    //   451: new java/util/ArrayList
    //   454: dup
    //   455: invokespecial <init> : ()V
    //   458: astore #10
    //   460: aload #10
    //   462: aload #11
    //   464: invokevirtual add : (Ljava/lang/Object;)Z
    //   467: pop
    //   468: aload #9
    //   470: astore #14
    //   472: aload #17
    //   474: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   477: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   480: ifnonnull -> 549
    //   483: aload #9
    //   485: astore #14
    //   487: aload #17
    //   489: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   492: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   495: ifnonnull -> 549
    //   498: aload #9
    //   500: astore #14
    //   502: iload #4
    //   504: ifne -> 549
    //   507: aload #9
    //   509: astore #14
    //   511: aload #17
    //   513: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   516: ifne -> 549
    //   519: aload #9
    //   521: astore #5
    //   523: aload #9
    //   525: ifnonnull -> 537
    //   528: new java/util/ArrayList
    //   531: dup
    //   532: invokespecial <init> : ()V
    //   535: astore #5
    //   537: aload #5
    //   539: aload #17
    //   541: invokevirtual add : (Ljava/lang/Object;)Z
    //   544: pop
    //   545: aload #5
    //   547: astore #14
    //   549: aload #8
    //   551: astore #15
    //   553: aload #17
    //   555: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   558: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   561: ifnonnull -> 645
    //   564: aload #8
    //   566: astore #15
    //   568: aload #17
    //   570: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   573: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   576: ifnonnull -> 645
    //   579: aload #8
    //   581: astore #15
    //   583: aload #17
    //   585: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   588: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   591: ifnonnull -> 645
    //   594: aload #8
    //   596: astore #15
    //   598: iload #4
    //   600: ifne -> 645
    //   603: aload #8
    //   605: astore #15
    //   607: aload #17
    //   609: instanceof androidx/constraintlayout/solver/widgets/Barrier
    //   612: ifne -> 645
    //   615: aload #8
    //   617: astore #5
    //   619: aload #8
    //   621: ifnonnull -> 633
    //   624: new java/util/ArrayList
    //   627: dup
    //   628: invokespecial <init> : ()V
    //   631: astore #5
    //   633: aload #5
    //   635: aload #17
    //   637: invokevirtual add : (Ljava/lang/Object;)Z
    //   640: pop
    //   641: aload #5
    //   643: astore #15
    //   645: iinc #2, 1
    //   648: aload #13
    //   650: astore #11
    //   652: aload #7
    //   654: astore #5
    //   656: aload #12
    //   658: astore #7
    //   660: aload #10
    //   662: astore #6
    //   664: aload #14
    //   666: astore #9
    //   668: aload #15
    //   670: astore #8
    //   672: goto -> 117
    //   675: new java/util/ArrayList
    //   678: dup
    //   679: invokespecial <init> : ()V
    //   682: astore #10
    //   684: aload #11
    //   686: ifnull -> 724
    //   689: aload #11
    //   691: invokevirtual iterator : ()Ljava/util/Iterator;
    //   694: astore_1
    //   695: aload_1
    //   696: invokeinterface hasNext : ()Z
    //   701: ifeq -> 724
    //   704: aload_1
    //   705: invokeinterface next : ()Ljava/lang/Object;
    //   710: checkcast androidx/constraintlayout/solver/widgets/Guideline
    //   713: iconst_0
    //   714: aload #10
    //   716: aconst_null
    //   717: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   720: pop
    //   721: goto -> 695
    //   724: aload #5
    //   726: ifnull -> 786
    //   729: aload #5
    //   731: invokevirtual iterator : ()Ljava/util/Iterator;
    //   734: astore #5
    //   736: aload #5
    //   738: invokeinterface hasNext : ()Z
    //   743: ifeq -> 786
    //   746: aload #5
    //   748: invokeinterface next : ()Ljava/lang/Object;
    //   753: checkcast androidx/constraintlayout/solver/widgets/HelperWidget
    //   756: astore_1
    //   757: aload_1
    //   758: iconst_0
    //   759: aload #10
    //   761: aconst_null
    //   762: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   765: astore #11
    //   767: aload_1
    //   768: aload #10
    //   770: iconst_0
    //   771: aload #11
    //   773: invokevirtual addDependents : (Ljava/util/ArrayList;ILandroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)V
    //   776: aload #11
    //   778: aload #10
    //   780: invokevirtual cleanup : (Ljava/util/ArrayList;)V
    //   783: goto -> 736
    //   786: aload_0
    //   787: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   790: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   793: astore_1
    //   794: aload_1
    //   795: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   798: ifnull -> 841
    //   801: aload_1
    //   802: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   805: invokevirtual iterator : ()Ljava/util/Iterator;
    //   808: astore_1
    //   809: aload_1
    //   810: invokeinterface hasNext : ()Z
    //   815: ifeq -> 841
    //   818: aload_1
    //   819: invokeinterface next : ()Ljava/lang/Object;
    //   824: checkcast androidx/constraintlayout/solver/widgets/ConstraintAnchor
    //   827: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   830: iconst_0
    //   831: aload #10
    //   833: aconst_null
    //   834: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   837: pop
    //   838: goto -> 809
    //   841: aload_0
    //   842: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   845: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   848: astore_1
    //   849: aload_1
    //   850: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   853: ifnull -> 896
    //   856: aload_1
    //   857: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   860: invokevirtual iterator : ()Ljava/util/Iterator;
    //   863: astore_1
    //   864: aload_1
    //   865: invokeinterface hasNext : ()Z
    //   870: ifeq -> 896
    //   873: aload_1
    //   874: invokeinterface next : ()Ljava/lang/Object;
    //   879: checkcast androidx/constraintlayout/solver/widgets/ConstraintAnchor
    //   882: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   885: iconst_0
    //   886: aload #10
    //   888: aconst_null
    //   889: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   892: pop
    //   893: goto -> 864
    //   896: aload_0
    //   897: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   900: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   903: astore_1
    //   904: aload_1
    //   905: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   908: ifnull -> 951
    //   911: aload_1
    //   912: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   915: invokevirtual iterator : ()Ljava/util/Iterator;
    //   918: astore_1
    //   919: aload_1
    //   920: invokeinterface hasNext : ()Z
    //   925: ifeq -> 951
    //   928: aload_1
    //   929: invokeinterface next : ()Ljava/lang/Object;
    //   934: checkcast androidx/constraintlayout/solver/widgets/ConstraintAnchor
    //   937: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   940: iconst_0
    //   941: aload #10
    //   943: aconst_null
    //   944: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   947: pop
    //   948: goto -> 919
    //   951: aload #9
    //   953: ifnull -> 991
    //   956: aload #9
    //   958: invokevirtual iterator : ()Ljava/util/Iterator;
    //   961: astore_1
    //   962: aload_1
    //   963: invokeinterface hasNext : ()Z
    //   968: ifeq -> 991
    //   971: aload_1
    //   972: invokeinterface next : ()Ljava/lang/Object;
    //   977: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   980: iconst_0
    //   981: aload #10
    //   983: aconst_null
    //   984: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   987: pop
    //   988: goto -> 962
    //   991: aload #7
    //   993: ifnull -> 1031
    //   996: aload #7
    //   998: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1001: astore_1
    //   1002: aload_1
    //   1003: invokeinterface hasNext : ()Z
    //   1008: ifeq -> 1031
    //   1011: aload_1
    //   1012: invokeinterface next : ()Ljava/lang/Object;
    //   1017: checkcast androidx/constraintlayout/solver/widgets/Guideline
    //   1020: iconst_1
    //   1021: aload #10
    //   1023: aconst_null
    //   1024: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1027: pop
    //   1028: goto -> 1002
    //   1031: aload #6
    //   1033: ifnull -> 1093
    //   1036: aload #6
    //   1038: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1041: astore_1
    //   1042: aload_1
    //   1043: invokeinterface hasNext : ()Z
    //   1048: ifeq -> 1093
    //   1051: aload_1
    //   1052: invokeinterface next : ()Ljava/lang/Object;
    //   1057: checkcast androidx/constraintlayout/solver/widgets/HelperWidget
    //   1060: astore #6
    //   1062: aload #6
    //   1064: iconst_1
    //   1065: aload #10
    //   1067: aconst_null
    //   1068: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1071: astore #5
    //   1073: aload #6
    //   1075: aload #10
    //   1077: iconst_1
    //   1078: aload #5
    //   1080: invokevirtual addDependents : (Ljava/util/ArrayList;ILandroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)V
    //   1083: aload #5
    //   1085: aload #10
    //   1087: invokevirtual cleanup : (Ljava/util/ArrayList;)V
    //   1090: goto -> 1042
    //   1093: aload_0
    //   1094: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1097: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1100: astore_1
    //   1101: aload_1
    //   1102: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1105: ifnull -> 1148
    //   1108: aload_1
    //   1109: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1112: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1115: astore_1
    //   1116: aload_1
    //   1117: invokeinterface hasNext : ()Z
    //   1122: ifeq -> 1148
    //   1125: aload_1
    //   1126: invokeinterface next : ()Ljava/lang/Object;
    //   1131: checkcast androidx/constraintlayout/solver/widgets/ConstraintAnchor
    //   1134: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1137: iconst_1
    //   1138: aload #10
    //   1140: aconst_null
    //   1141: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1144: pop
    //   1145: goto -> 1116
    //   1148: aload_0
    //   1149: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BASELINE : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1152: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1155: astore_1
    //   1156: aload_1
    //   1157: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1160: ifnull -> 1203
    //   1163: aload_1
    //   1164: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1167: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1170: astore_1
    //   1171: aload_1
    //   1172: invokeinterface hasNext : ()Z
    //   1177: ifeq -> 1203
    //   1180: aload_1
    //   1181: invokeinterface next : ()Ljava/lang/Object;
    //   1186: checkcast androidx/constraintlayout/solver/widgets/ConstraintAnchor
    //   1189: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1192: iconst_1
    //   1193: aload #10
    //   1195: aconst_null
    //   1196: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1199: pop
    //   1200: goto -> 1171
    //   1203: aload_0
    //   1204: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1207: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1210: astore_1
    //   1211: aload_1
    //   1212: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1215: ifnull -> 1258
    //   1218: aload_1
    //   1219: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1222: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1225: astore_1
    //   1226: aload_1
    //   1227: invokeinterface hasNext : ()Z
    //   1232: ifeq -> 1258
    //   1235: aload_1
    //   1236: invokeinterface next : ()Ljava/lang/Object;
    //   1241: checkcast androidx/constraintlayout/solver/widgets/ConstraintAnchor
    //   1244: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1247: iconst_1
    //   1248: aload #10
    //   1250: aconst_null
    //   1251: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1254: pop
    //   1255: goto -> 1226
    //   1258: aload_0
    //   1259: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1262: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1265: astore_1
    //   1266: aload_1
    //   1267: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1270: ifnull -> 1313
    //   1273: aload_1
    //   1274: invokevirtual getDependents : ()Ljava/util/HashSet;
    //   1277: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1280: astore_1
    //   1281: aload_1
    //   1282: invokeinterface hasNext : ()Z
    //   1287: ifeq -> 1313
    //   1290: aload_1
    //   1291: invokeinterface next : ()Ljava/lang/Object;
    //   1296: checkcast androidx/constraintlayout/solver/widgets/ConstraintAnchor
    //   1299: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1302: iconst_1
    //   1303: aload #10
    //   1305: aconst_null
    //   1306: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1309: pop
    //   1310: goto -> 1281
    //   1313: aload #8
    //   1315: ifnull -> 1353
    //   1318: aload #8
    //   1320: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1323: astore_1
    //   1324: aload_1
    //   1325: invokeinterface hasNext : ()Z
    //   1330: ifeq -> 1353
    //   1333: aload_1
    //   1334: invokeinterface next : ()Ljava/lang/Object;
    //   1339: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   1342: iconst_1
    //   1343: aload #10
    //   1345: aconst_null
    //   1346: invokestatic findDependents : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;ILjava/util/ArrayList;Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1349: pop
    //   1350: goto -> 1324
    //   1353: iconst_0
    //   1354: istore_2
    //   1355: iload_2
    //   1356: iload_3
    //   1357: if_icmpge -> 1437
    //   1360: aload #16
    //   1362: iload_2
    //   1363: invokevirtual get : (I)Ljava/lang/Object;
    //   1366: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   1369: astore #5
    //   1371: aload #5
    //   1373: invokevirtual oppositeDimensionsTied : ()Z
    //   1376: ifeq -> 1431
    //   1379: aload #10
    //   1381: aload #5
    //   1383: getfield horizontalGroup : I
    //   1386: invokestatic findGroup : (Ljava/util/ArrayList;I)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1389: astore_1
    //   1390: aload #10
    //   1392: aload #5
    //   1394: getfield verticalGroup : I
    //   1397: invokestatic findGroup : (Ljava/util/ArrayList;I)Landroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;
    //   1400: astore #5
    //   1402: aload_1
    //   1403: ifnull -> 1431
    //   1406: aload #5
    //   1408: ifnull -> 1431
    //   1411: aload_1
    //   1412: iconst_0
    //   1413: aload #5
    //   1415: invokevirtual moveTo : (ILandroidx/constraintlayout/solver/widgets/analyzer/WidgetGroup;)V
    //   1418: aload #5
    //   1420: iconst_2
    //   1421: invokevirtual setOrientation : (I)V
    //   1424: aload #10
    //   1426: aload_1
    //   1427: invokevirtual remove : (Ljava/lang/Object;)Z
    //   1430: pop
    //   1431: iinc #2, 1
    //   1434: goto -> 1355
    //   1437: aload #10
    //   1439: invokevirtual size : ()I
    //   1442: iconst_1
    //   1443: if_icmpgt -> 1448
    //   1446: iconst_0
    //   1447: ireturn
    //   1448: aload_0
    //   1449: invokevirtual getHorizontalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1452: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1455: if_acmpne -> 1560
    //   1458: aload #10
    //   1460: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1463: astore #6
    //   1465: aconst_null
    //   1466: astore_1
    //   1467: iconst_0
    //   1468: istore_2
    //   1469: aload #6
    //   1471: invokeinterface hasNext : ()Z
    //   1476: ifeq -> 1533
    //   1479: aload #6
    //   1481: invokeinterface next : ()Ljava/lang/Object;
    //   1486: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetGroup
    //   1489: astore #5
    //   1491: aload #5
    //   1493: invokevirtual getOrientation : ()I
    //   1496: iconst_1
    //   1497: if_icmpne -> 1503
    //   1500: goto -> 1469
    //   1503: aload #5
    //   1505: iconst_0
    //   1506: invokevirtual setAuthoritative : (Z)V
    //   1509: aload #5
    //   1511: aload_0
    //   1512: invokevirtual getSystem : ()Landroidx/constraintlayout/solver/LinearSystem;
    //   1515: iconst_0
    //   1516: invokevirtual measureWrap : (Landroidx/constraintlayout/solver/LinearSystem;I)I
    //   1519: istore_3
    //   1520: iload_3
    //   1521: iload_2
    //   1522: if_icmple -> 1469
    //   1525: aload #5
    //   1527: astore_1
    //   1528: iload_3
    //   1529: istore_2
    //   1530: goto -> 1469
    //   1533: aload_1
    //   1534: ifnull -> 1560
    //   1537: aload_0
    //   1538: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1541: invokevirtual setHorizontalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1544: aload_0
    //   1545: iload_2
    //   1546: invokevirtual setWidth : (I)V
    //   1549: aload_1
    //   1550: iconst_1
    //   1551: invokevirtual setAuthoritative : (Z)V
    //   1554: aload_1
    //   1555: astore #5
    //   1557: goto -> 1563
    //   1560: aconst_null
    //   1561: astore #5
    //   1563: aload_0
    //   1564: invokevirtual getVerticalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1567: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1570: if_acmpne -> 1673
    //   1573: aload #10
    //   1575: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1578: astore #7
    //   1580: aconst_null
    //   1581: astore_1
    //   1582: iconst_0
    //   1583: istore_2
    //   1584: aload #7
    //   1586: invokeinterface hasNext : ()Z
    //   1591: ifeq -> 1647
    //   1594: aload #7
    //   1596: invokeinterface next : ()Ljava/lang/Object;
    //   1601: checkcast androidx/constraintlayout/solver/widgets/analyzer/WidgetGroup
    //   1604: astore #6
    //   1606: aload #6
    //   1608: invokevirtual getOrientation : ()I
    //   1611: ifne -> 1617
    //   1614: goto -> 1584
    //   1617: aload #6
    //   1619: iconst_0
    //   1620: invokevirtual setAuthoritative : (Z)V
    //   1623: aload #6
    //   1625: aload_0
    //   1626: invokevirtual getSystem : ()Landroidx/constraintlayout/solver/LinearSystem;
    //   1629: iconst_1
    //   1630: invokevirtual measureWrap : (Landroidx/constraintlayout/solver/LinearSystem;I)I
    //   1633: istore_3
    //   1634: iload_3
    //   1635: iload_2
    //   1636: if_icmple -> 1584
    //   1639: aload #6
    //   1641: astore_1
    //   1642: iload_3
    //   1643: istore_2
    //   1644: goto -> 1584
    //   1647: aload_1
    //   1648: ifnull -> 1673
    //   1651: aload_0
    //   1652: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.FIXED : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1655: invokevirtual setVerticalDimensionBehaviour : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1658: aload_0
    //   1659: iload_2
    //   1660: invokevirtual setHeight : (I)V
    //   1663: aload_1
    //   1664: iconst_1
    //   1665: invokevirtual setAuthoritative : (Z)V
    //   1668: aload_1
    //   1669: astore_0
    //   1670: goto -> 1675
    //   1673: aconst_null
    //   1674: astore_0
    //   1675: aload #5
    //   1677: ifnonnull -> 1693
    //   1680: aload_0
    //   1681: ifnull -> 1687
    //   1684: goto -> 1693
    //   1687: iconst_0
    //   1688: istore #4
    //   1690: goto -> 1696
    //   1693: iconst_1
    //   1694: istore #4
    //   1696: iload #4
    //   1698: ireturn
  }
  
  public static boolean validInGroup(ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour3, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour4) {
    boolean bool1;
    boolean bool2;
    if (paramDimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED || paramDimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (paramDimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && paramDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (paramDimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED || paramDimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (paramDimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && paramDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    return (bool1 || bool2);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\Grouping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */