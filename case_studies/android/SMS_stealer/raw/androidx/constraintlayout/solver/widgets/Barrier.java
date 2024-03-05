package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import java.util.HashMap;

public class Barrier extends HelperWidget {
  public static final int BOTTOM = 3;
  
  public static final int LEFT = 0;
  
  public static final int RIGHT = 1;
  
  public static final int TOP = 2;
  
  private static final boolean USE_RELAX_GONE = false;
  
  private static final boolean USE_RESOLUTION = true;
  
  private boolean mAllowsGoneWidget = true;
  
  private int mBarrierType = 0;
  
  private int mMargin = 0;
  
  boolean resolved = false;
  
  public Barrier() {}
  
  public Barrier(String paramString) {
    setDebugName(paramString);
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   4: iconst_0
    //   5: aload_0
    //   6: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   9: aastore
    //   10: aload_0
    //   11: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   14: iconst_2
    //   15: aload_0
    //   16: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   19: aastore
    //   20: aload_0
    //   21: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   24: iconst_1
    //   25: aload_0
    //   26: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   29: aastore
    //   30: aload_0
    //   31: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   34: iconst_3
    //   35: aload_0
    //   36: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   39: aastore
    //   40: iconst_0
    //   41: istore_3
    //   42: iload_3
    //   43: aload_0
    //   44: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   47: arraylength
    //   48: if_icmpge -> 76
    //   51: aload_0
    //   52: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   55: iload_3
    //   56: aaload
    //   57: aload_1
    //   58: aload_0
    //   59: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   62: iload_3
    //   63: aaload
    //   64: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   67: putfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   70: iinc #3, 1
    //   73: goto -> 42
    //   76: aload_0
    //   77: getfield mBarrierType : I
    //   80: istore_3
    //   81: iload_3
    //   82: iflt -> 1031
    //   85: iload_3
    //   86: iconst_4
    //   87: if_icmpge -> 1031
    //   90: aload_0
    //   91: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   94: aload_0
    //   95: getfield mBarrierType : I
    //   98: aaload
    //   99: astore #7
    //   101: aload_0
    //   102: getfield resolved : Z
    //   105: ifne -> 113
    //   108: aload_0
    //   109: invokevirtual allSolved : ()Z
    //   112: pop
    //   113: aload_0
    //   114: getfield resolved : Z
    //   117: ifeq -> 216
    //   120: aload_0
    //   121: iconst_0
    //   122: putfield resolved : Z
    //   125: aload_0
    //   126: getfield mBarrierType : I
    //   129: istore_3
    //   130: iload_3
    //   131: ifeq -> 185
    //   134: iload_3
    //   135: iconst_1
    //   136: if_icmpne -> 142
    //   139: goto -> 185
    //   142: iload_3
    //   143: iconst_2
    //   144: if_icmpeq -> 152
    //   147: iload_3
    //   148: iconst_3
    //   149: if_icmpne -> 215
    //   152: aload_1
    //   153: aload_0
    //   154: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   157: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   160: aload_0
    //   161: getfield mY : I
    //   164: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   167: aload_1
    //   168: aload_0
    //   169: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   172: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   175: aload_0
    //   176: getfield mY : I
    //   179: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   182: goto -> 215
    //   185: aload_1
    //   186: aload_0
    //   187: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   190: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   193: aload_0
    //   194: getfield mX : I
    //   197: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   200: aload_1
    //   201: aload_0
    //   202: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   205: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   208: aload_0
    //   209: getfield mX : I
    //   212: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;I)V
    //   215: return
    //   216: iconst_0
    //   217: istore_3
    //   218: iload_3
    //   219: aload_0
    //   220: getfield mWidgetsCount : I
    //   223: if_icmpge -> 367
    //   226: aload_0
    //   227: getfield mWidgets : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   230: iload_3
    //   231: aaload
    //   232: astore #8
    //   234: aload_0
    //   235: getfield mAllowsGoneWidget : Z
    //   238: ifne -> 252
    //   241: aload #8
    //   243: invokevirtual allowedInBarrier : ()Z
    //   246: ifne -> 252
    //   249: goto -> 361
    //   252: aload_0
    //   253: getfield mBarrierType : I
    //   256: istore #4
    //   258: iload #4
    //   260: ifeq -> 269
    //   263: iload #4
    //   265: iconst_1
    //   266: if_icmpne -> 307
    //   269: aload #8
    //   271: invokevirtual getHorizontalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   274: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   277: if_acmpne -> 307
    //   280: aload #8
    //   282: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   285: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   288: ifnull -> 307
    //   291: aload #8
    //   293: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   296: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   299: ifnull -> 307
    //   302: iconst_1
    //   303: istore_2
    //   304: goto -> 369
    //   307: aload_0
    //   308: getfield mBarrierType : I
    //   311: istore #4
    //   313: iload #4
    //   315: iconst_2
    //   316: if_icmpeq -> 325
    //   319: iload #4
    //   321: iconst_3
    //   322: if_icmpne -> 361
    //   325: aload #8
    //   327: invokevirtual getVerticalDimensionBehaviour : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   330: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   333: if_acmpne -> 361
    //   336: aload #8
    //   338: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   341: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   344: ifnull -> 361
    //   347: aload #8
    //   349: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   352: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   355: ifnull -> 361
    //   358: goto -> 302
    //   361: iinc #3, 1
    //   364: goto -> 218
    //   367: iconst_0
    //   368: istore_2
    //   369: aload_0
    //   370: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   373: invokevirtual hasCenteredDependents : ()Z
    //   376: ifne -> 397
    //   379: aload_0
    //   380: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   383: invokevirtual hasCenteredDependents : ()Z
    //   386: ifeq -> 392
    //   389: goto -> 397
    //   392: iconst_0
    //   393: istore_3
    //   394: goto -> 399
    //   397: iconst_1
    //   398: istore_3
    //   399: aload_0
    //   400: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   403: invokevirtual hasCenteredDependents : ()Z
    //   406: ifne -> 428
    //   409: aload_0
    //   410: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   413: invokevirtual hasCenteredDependents : ()Z
    //   416: ifeq -> 422
    //   419: goto -> 428
    //   422: iconst_0
    //   423: istore #4
    //   425: goto -> 431
    //   428: iconst_1
    //   429: istore #4
    //   431: iload_2
    //   432: ifne -> 490
    //   435: aload_0
    //   436: getfield mBarrierType : I
    //   439: ifne -> 446
    //   442: iload_3
    //   443: ifne -> 484
    //   446: aload_0
    //   447: getfield mBarrierType : I
    //   450: iconst_2
    //   451: if_icmpne -> 459
    //   454: iload #4
    //   456: ifne -> 484
    //   459: aload_0
    //   460: getfield mBarrierType : I
    //   463: iconst_1
    //   464: if_icmpne -> 471
    //   467: iload_3
    //   468: ifne -> 484
    //   471: aload_0
    //   472: getfield mBarrierType : I
    //   475: iconst_3
    //   476: if_icmpne -> 490
    //   479: iload #4
    //   481: ifeq -> 490
    //   484: iconst_1
    //   485: istore #4
    //   487: goto -> 493
    //   490: iconst_0
    //   491: istore #4
    //   493: iconst_5
    //   494: istore_3
    //   495: iload #4
    //   497: ifne -> 502
    //   500: iconst_4
    //   501: istore_3
    //   502: iconst_0
    //   503: istore #4
    //   505: iload #4
    //   507: aload_0
    //   508: getfield mWidgetsCount : I
    //   511: if_icmpge -> 718
    //   514: aload_0
    //   515: getfield mWidgets : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   518: iload #4
    //   520: aaload
    //   521: astore #8
    //   523: aload_0
    //   524: getfield mAllowsGoneWidget : Z
    //   527: ifne -> 541
    //   530: aload #8
    //   532: invokevirtual allowedInBarrier : ()Z
    //   535: ifne -> 541
    //   538: goto -> 712
    //   541: aload_1
    //   542: aload #8
    //   544: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   547: aload_0
    //   548: getfield mBarrierType : I
    //   551: aaload
    //   552: invokevirtual createObjectVariable : (Ljava/lang/Object;)Landroidx/constraintlayout/solver/SolverVariable;
    //   555: astore #9
    //   557: aload #8
    //   559: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   562: aload_0
    //   563: getfield mBarrierType : I
    //   566: aaload
    //   567: aload #9
    //   569: putfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   572: aload #8
    //   574: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   577: aload_0
    //   578: getfield mBarrierType : I
    //   581: aaload
    //   582: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   585: ifnull -> 628
    //   588: aload #8
    //   590: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   593: aload_0
    //   594: getfield mBarrierType : I
    //   597: aaload
    //   598: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   601: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   604: aload_0
    //   605: if_acmpne -> 628
    //   608: aload #8
    //   610: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   613: aload_0
    //   614: getfield mBarrierType : I
    //   617: aaload
    //   618: getfield mMargin : I
    //   621: iconst_0
    //   622: iadd
    //   623: istore #5
    //   625: goto -> 631
    //   628: iconst_0
    //   629: istore #5
    //   631: aload_0
    //   632: getfield mBarrierType : I
    //   635: istore #6
    //   637: iload #6
    //   639: ifeq -> 673
    //   642: iload #6
    //   644: iconst_2
    //   645: if_icmpne -> 651
    //   648: goto -> 673
    //   651: aload_1
    //   652: aload #7
    //   654: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   657: aload #9
    //   659: aload_0
    //   660: getfield mMargin : I
    //   663: iload #5
    //   665: iadd
    //   666: iload_2
    //   667: invokevirtual addGreaterBarrier : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IZ)V
    //   670: goto -> 692
    //   673: aload_1
    //   674: aload #7
    //   676: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   679: aload #9
    //   681: aload_0
    //   682: getfield mMargin : I
    //   685: iload #5
    //   687: isub
    //   688: iload_2
    //   689: invokevirtual addLowerBarrier : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IZ)V
    //   692: aload_1
    //   693: aload #7
    //   695: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   698: aload #9
    //   700: aload_0
    //   701: getfield mMargin : I
    //   704: iload #5
    //   706: iadd
    //   707: iload_3
    //   708: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   711: pop
    //   712: iinc #4, 1
    //   715: goto -> 505
    //   718: aload_0
    //   719: getfield mBarrierType : I
    //   722: istore_3
    //   723: iload_3
    //   724: ifne -> 800
    //   727: aload_1
    //   728: aload_0
    //   729: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   732: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   735: aload_0
    //   736: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   739: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   742: iconst_0
    //   743: bipush #8
    //   745: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   748: pop
    //   749: aload_1
    //   750: aload_0
    //   751: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   754: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   757: aload_0
    //   758: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   761: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   764: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   767: iconst_0
    //   768: iconst_4
    //   769: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   772: pop
    //   773: aload_1
    //   774: aload_0
    //   775: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   778: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   781: aload_0
    //   782: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   785: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   788: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   791: iconst_0
    //   792: iconst_0
    //   793: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   796: pop
    //   797: goto -> 1031
    //   800: iload_3
    //   801: iconst_1
    //   802: if_icmpne -> 878
    //   805: aload_1
    //   806: aload_0
    //   807: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   810: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   813: aload_0
    //   814: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   817: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   820: iconst_0
    //   821: bipush #8
    //   823: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   826: pop
    //   827: aload_1
    //   828: aload_0
    //   829: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   832: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   835: aload_0
    //   836: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   839: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   842: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   845: iconst_0
    //   846: iconst_4
    //   847: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   850: pop
    //   851: aload_1
    //   852: aload_0
    //   853: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   856: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   859: aload_0
    //   860: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   863: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   866: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   869: iconst_0
    //   870: iconst_0
    //   871: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   874: pop
    //   875: goto -> 1031
    //   878: iload_3
    //   879: iconst_2
    //   880: if_icmpne -> 956
    //   883: aload_1
    //   884: aload_0
    //   885: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   888: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   891: aload_0
    //   892: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   895: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   898: iconst_0
    //   899: bipush #8
    //   901: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   904: pop
    //   905: aload_1
    //   906: aload_0
    //   907: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   910: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   913: aload_0
    //   914: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   917: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   920: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   923: iconst_0
    //   924: iconst_4
    //   925: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   928: pop
    //   929: aload_1
    //   930: aload_0
    //   931: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   934: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   937: aload_0
    //   938: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   941: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   944: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   947: iconst_0
    //   948: iconst_0
    //   949: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   952: pop
    //   953: goto -> 1031
    //   956: iload_3
    //   957: iconst_3
    //   958: if_icmpne -> 1031
    //   961: aload_1
    //   962: aload_0
    //   963: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   966: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   969: aload_0
    //   970: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   973: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   976: iconst_0
    //   977: bipush #8
    //   979: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   982: pop
    //   983: aload_1
    //   984: aload_0
    //   985: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   988: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   991: aload_0
    //   992: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   995: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   998: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1001: iconst_0
    //   1002: iconst_4
    //   1003: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1006: pop
    //   1007: aload_1
    //   1008: aload_0
    //   1009: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1012: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1015: aload_0
    //   1016: getfield mParent : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1019: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1022: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1025: iconst_0
    //   1026: iconst_0
    //   1027: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1030: pop
    //   1031: return
  }
  
  public boolean allSolved() {
    // Byte code:
    //   0: iconst_0
    //   1: istore #5
    //   3: iconst_0
    //   4: istore_1
    //   5: iconst_1
    //   6: istore_2
    //   7: iload_1
    //   8: aload_0
    //   9: getfield mWidgetsCount : I
    //   12: if_icmpge -> 111
    //   15: aload_0
    //   16: getfield mWidgets : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   19: iload_1
    //   20: aaload
    //   21: astore #7
    //   23: aload_0
    //   24: getfield mAllowsGoneWidget : Z
    //   27: ifne -> 43
    //   30: aload #7
    //   32: invokevirtual allowedInBarrier : ()Z
    //   35: ifne -> 43
    //   38: iload_2
    //   39: istore_3
    //   40: goto -> 103
    //   43: aload_0
    //   44: getfield mBarrierType : I
    //   47: istore_3
    //   48: iload_3
    //   49: ifeq -> 57
    //   52: iload_3
    //   53: iconst_1
    //   54: if_icmpne -> 70
    //   57: aload #7
    //   59: invokevirtual isResolvedHorizontally : ()Z
    //   62: ifne -> 70
    //   65: iconst_0
    //   66: istore_3
    //   67: goto -> 103
    //   70: aload_0
    //   71: getfield mBarrierType : I
    //   74: istore #4
    //   76: iload #4
    //   78: iconst_2
    //   79: if_icmpeq -> 90
    //   82: iload_2
    //   83: istore_3
    //   84: iload #4
    //   86: iconst_3
    //   87: if_icmpne -> 103
    //   90: iload_2
    //   91: istore_3
    //   92: aload #7
    //   94: invokevirtual isResolvedVertically : ()Z
    //   97: ifne -> 103
    //   100: goto -> 65
    //   103: iinc #1, 1
    //   106: iload_3
    //   107: istore_2
    //   108: goto -> 7
    //   111: iload_2
    //   112: ifeq -> 429
    //   115: aload_0
    //   116: getfield mWidgetsCount : I
    //   119: ifle -> 429
    //   122: iconst_0
    //   123: istore_1
    //   124: iconst_0
    //   125: istore #4
    //   127: iload #5
    //   129: aload_0
    //   130: getfield mWidgetsCount : I
    //   133: if_icmpge -> 383
    //   136: aload_0
    //   137: getfield mWidgets : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   140: iload #5
    //   142: aaload
    //   143: astore #7
    //   145: aload_0
    //   146: getfield mAllowsGoneWidget : Z
    //   149: ifne -> 163
    //   152: aload #7
    //   154: invokevirtual allowedInBarrier : ()Z
    //   157: ifne -> 163
    //   160: goto -> 377
    //   163: iload_1
    //   164: istore_3
    //   165: iload #4
    //   167: istore_2
    //   168: iload #4
    //   170: ifne -> 258
    //   173: aload_0
    //   174: getfield mBarrierType : I
    //   177: istore_2
    //   178: iload_2
    //   179: ifne -> 197
    //   182: aload #7
    //   184: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   187: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   190: invokevirtual getFinalValue : ()I
    //   193: istore_1
    //   194: goto -> 254
    //   197: iload_2
    //   198: iconst_1
    //   199: if_icmpne -> 217
    //   202: aload #7
    //   204: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   207: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   210: invokevirtual getFinalValue : ()I
    //   213: istore_1
    //   214: goto -> 254
    //   217: iload_2
    //   218: iconst_2
    //   219: if_icmpne -> 237
    //   222: aload #7
    //   224: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   227: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   230: invokevirtual getFinalValue : ()I
    //   233: istore_1
    //   234: goto -> 254
    //   237: iload_2
    //   238: iconst_3
    //   239: if_icmpne -> 254
    //   242: aload #7
    //   244: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   247: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   250: invokevirtual getFinalValue : ()I
    //   253: istore_1
    //   254: iconst_1
    //   255: istore_2
    //   256: iload_1
    //   257: istore_3
    //   258: aload_0
    //   259: getfield mBarrierType : I
    //   262: istore #6
    //   264: iload #6
    //   266: ifne -> 291
    //   269: iload_3
    //   270: aload #7
    //   272: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   275: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   278: invokevirtual getFinalValue : ()I
    //   281: invokestatic min : (II)I
    //   284: istore_1
    //   285: iload_2
    //   286: istore #4
    //   288: goto -> 377
    //   291: iload #6
    //   293: iconst_1
    //   294: if_icmpne -> 319
    //   297: iload_3
    //   298: aload #7
    //   300: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   303: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   306: invokevirtual getFinalValue : ()I
    //   309: invokestatic max : (II)I
    //   312: istore_1
    //   313: iload_2
    //   314: istore #4
    //   316: goto -> 377
    //   319: iload #6
    //   321: iconst_2
    //   322: if_icmpne -> 347
    //   325: iload_3
    //   326: aload #7
    //   328: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   331: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   334: invokevirtual getFinalValue : ()I
    //   337: invokestatic min : (II)I
    //   340: istore_1
    //   341: iload_2
    //   342: istore #4
    //   344: goto -> 377
    //   347: iload_3
    //   348: istore_1
    //   349: iload_2
    //   350: istore #4
    //   352: iload #6
    //   354: iconst_3
    //   355: if_icmpne -> 377
    //   358: iload_3
    //   359: aload #7
    //   361: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   364: invokevirtual getAnchor : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   367: invokevirtual getFinalValue : ()I
    //   370: invokestatic max : (II)I
    //   373: istore_1
    //   374: iload_2
    //   375: istore #4
    //   377: iinc #5, 1
    //   380: goto -> 127
    //   383: iload_1
    //   384: aload_0
    //   385: getfield mMargin : I
    //   388: iadd
    //   389: istore_1
    //   390: aload_0
    //   391: getfield mBarrierType : I
    //   394: istore_2
    //   395: iload_2
    //   396: ifeq -> 416
    //   399: iload_2
    //   400: iconst_1
    //   401: if_icmpne -> 407
    //   404: goto -> 416
    //   407: aload_0
    //   408: iload_1
    //   409: iload_1
    //   410: invokevirtual setFinalVertical : (II)V
    //   413: goto -> 422
    //   416: aload_0
    //   417: iload_1
    //   418: iload_1
    //   419: invokevirtual setFinalHorizontal : (II)V
    //   422: aload_0
    //   423: iconst_1
    //   424: putfield resolved : Z
    //   427: iconst_1
    //   428: ireturn
    //   429: iconst_0
    //   430: ireturn
  }
  
  public boolean allowedInBarrier() {
    return true;
  }
  
  public boolean allowsGoneWidget() {
    return this.mAllowsGoneWidget;
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap) {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = paramConstraintWidget;
    this.mBarrierType = ((Barrier)paramConstraintWidget).mBarrierType;
    this.mAllowsGoneWidget = ((Barrier)paramConstraintWidget).mAllowsGoneWidget;
    this.mMargin = ((Barrier)paramConstraintWidget).mMargin;
  }
  
  public int getBarrierType() {
    return this.mBarrierType;
  }
  
  public int getMargin() {
    return this.mMargin;
  }
  
  public int getOrientation() {
    int i = this.mBarrierType;
    return (i != 0 && i != 1) ? ((i != 2 && i != 3) ? -1 : 1) : 0;
  }
  
  public boolean isResolvedHorizontally() {
    return this.resolved;
  }
  
  public boolean isResolvedVertically() {
    return this.resolved;
  }
  
  protected void markWidgets() {
    for (byte b = 0; b < this.mWidgetsCount; b++) {
      ConstraintWidget constraintWidget = this.mWidgets[b];
      int i = this.mBarrierType;
      if (i == 0 || i == 1) {
        constraintWidget.setInBarrier(0, true);
      } else if (i == 2 || i == 3) {
        constraintWidget.setInBarrier(1, true);
      } 
    } 
  }
  
  public void setAllowsGoneWidget(boolean paramBoolean) {
    this.mAllowsGoneWidget = paramBoolean;
  }
  
  public void setBarrierType(int paramInt) {
    this.mBarrierType = paramInt;
  }
  
  public void setMargin(int paramInt) {
    this.mMargin = paramInt;
  }
  
  public String toString() {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[Barrier] ");
    stringBuilder1.append(getDebugName());
    stringBuilder1.append(" {");
    String str = stringBuilder1.toString();
    for (byte b = 0; b < this.mWidgetsCount; b++) {
      ConstraintWidget constraintWidget = this.mWidgets[b];
      String str1 = str;
      if (b > 0) {
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append(", ");
        str1 = stringBuilder3.toString();
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append(constraintWidget.getDebugName());
      str = stringBuilder.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str);
    stringBuilder2.append("}");
    return stringBuilder2.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\Barrier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */