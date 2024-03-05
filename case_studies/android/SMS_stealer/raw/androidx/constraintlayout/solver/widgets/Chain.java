package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import java.util.ArrayList;

public class Chain {
  private static final boolean DEBUG = false;
  
  public static final boolean USE_CHAIN_OPTIMIZATION = false;
  
  static void applyChainConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt1, int paramInt2, ChainHead paramChainHead) {
    // Byte code:
    //   0: aload #4
    //   2: getfield mFirst : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   5: astore #26
    //   7: aload #4
    //   9: getfield mLast : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   12: astore #23
    //   14: aload #4
    //   16: getfield mFirstVisibleWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   19: astore #20
    //   21: aload #4
    //   23: getfield mLastVisibleWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   26: astore #24
    //   28: aload #4
    //   30: getfield mHead : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   33: astore #18
    //   35: aload #4
    //   37: getfield mTotalWeight : F
    //   40: fstore #5
    //   42: aload #4
    //   44: getfield mFirstMatchConstraintWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   47: astore #17
    //   49: aload #4
    //   51: getfield mLastMatchConstraintWidget : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   54: astore #17
    //   56: aload_0
    //   57: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   60: iload_2
    //   61: aaload
    //   62: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.WRAP_CONTENT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   65: if_acmpne -> 74
    //   68: iconst_1
    //   69: istore #12
    //   71: goto -> 77
    //   74: iconst_0
    //   75: istore #12
    //   77: iload_2
    //   78: ifne -> 136
    //   81: aload #18
    //   83: getfield mHorizontalChainStyle : I
    //   86: ifne -> 95
    //   89: iconst_1
    //   90: istore #8
    //   92: goto -> 98
    //   95: iconst_0
    //   96: istore #8
    //   98: aload #18
    //   100: getfield mHorizontalChainStyle : I
    //   103: iconst_1
    //   104: if_icmpne -> 113
    //   107: iconst_1
    //   108: istore #9
    //   110: goto -> 116
    //   113: iconst_0
    //   114: istore #9
    //   116: iload #8
    //   118: istore #10
    //   120: iload #9
    //   122: istore #11
    //   124: aload #18
    //   126: getfield mHorizontalChainStyle : I
    //   129: iconst_2
    //   130: if_icmpne -> 198
    //   133: goto -> 188
    //   136: aload #18
    //   138: getfield mVerticalChainStyle : I
    //   141: ifne -> 150
    //   144: iconst_1
    //   145: istore #8
    //   147: goto -> 153
    //   150: iconst_0
    //   151: istore #8
    //   153: aload #18
    //   155: getfield mVerticalChainStyle : I
    //   158: iconst_1
    //   159: if_icmpne -> 168
    //   162: iconst_1
    //   163: istore #9
    //   165: goto -> 171
    //   168: iconst_0
    //   169: istore #9
    //   171: iload #8
    //   173: istore #10
    //   175: iload #9
    //   177: istore #11
    //   179: aload #18
    //   181: getfield mVerticalChainStyle : I
    //   184: iconst_2
    //   185: if_icmpne -> 198
    //   188: iconst_1
    //   189: istore #13
    //   191: iload #8
    //   193: istore #10
    //   195: goto -> 205
    //   198: iconst_0
    //   199: istore #13
    //   201: iload #11
    //   203: istore #9
    //   205: aload #26
    //   207: astore #19
    //   209: iload #10
    //   211: istore #11
    //   213: iconst_0
    //   214: istore #8
    //   216: iload #9
    //   218: istore #10
    //   220: aconst_null
    //   221: astore #25
    //   223: aconst_null
    //   224: astore #21
    //   226: iload #8
    //   228: ifne -> 635
    //   231: aload #19
    //   233: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   236: iload_3
    //   237: aaload
    //   238: astore #17
    //   240: iload #13
    //   242: ifeq -> 251
    //   245: iconst_1
    //   246: istore #9
    //   248: goto -> 254
    //   251: iconst_4
    //   252: istore #9
    //   254: aload #17
    //   256: invokevirtual getMargin : ()I
    //   259: istore #16
    //   261: aload #19
    //   263: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   266: iload_2
    //   267: aaload
    //   268: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   271: if_acmpne -> 290
    //   274: aload #19
    //   276: getfield mResolvedMatchConstraintDefault : [I
    //   279: iload_2
    //   280: iaload
    //   281: ifne -> 290
    //   284: iconst_1
    //   285: istore #15
    //   287: goto -> 293
    //   290: iconst_0
    //   291: istore #15
    //   293: iload #16
    //   295: istore #14
    //   297: aload #17
    //   299: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   302: ifnull -> 329
    //   305: iload #16
    //   307: istore #14
    //   309: aload #19
    //   311: aload #26
    //   313: if_acmpeq -> 329
    //   316: iload #16
    //   318: aload #17
    //   320: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   323: invokevirtual getMargin : ()I
    //   326: iadd
    //   327: istore #14
    //   329: iload #13
    //   331: ifeq -> 355
    //   334: aload #19
    //   336: aload #26
    //   338: if_acmpeq -> 355
    //   341: aload #19
    //   343: aload #20
    //   345: if_acmpeq -> 355
    //   348: bipush #8
    //   350: istore #9
    //   352: goto -> 355
    //   355: aload #17
    //   357: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   360: ifnull -> 456
    //   363: aload #19
    //   365: aload #20
    //   367: if_acmpne -> 394
    //   370: aload_1
    //   371: aload #17
    //   373: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   376: aload #17
    //   378: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   381: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   384: iload #14
    //   386: bipush #6
    //   388: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   391: goto -> 415
    //   394: aload_1
    //   395: aload #17
    //   397: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   400: aload #17
    //   402: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   405: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   408: iload #14
    //   410: bipush #8
    //   412: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   415: iload #15
    //   417: ifeq -> 431
    //   420: iload #13
    //   422: ifne -> 431
    //   425: iconst_5
    //   426: istore #9
    //   428: goto -> 431
    //   431: aload_1
    //   432: aload #17
    //   434: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   437: aload #17
    //   439: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   442: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   445: iload #14
    //   447: iload #9
    //   449: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   452: pop
    //   453: goto -> 456
    //   456: iload #12
    //   458: ifeq -> 541
    //   461: aload #19
    //   463: invokevirtual getVisibility : ()I
    //   466: bipush #8
    //   468: if_icmpeq -> 515
    //   471: aload #19
    //   473: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   476: iload_2
    //   477: aaload
    //   478: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   481: if_acmpne -> 515
    //   484: aload_1
    //   485: aload #19
    //   487: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   490: iload_3
    //   491: iconst_1
    //   492: iadd
    //   493: aaload
    //   494: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   497: aload #19
    //   499: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   502: iload_3
    //   503: aaload
    //   504: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   507: iconst_0
    //   508: iconst_5
    //   509: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   512: goto -> 515
    //   515: aload_1
    //   516: aload #19
    //   518: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   521: iload_3
    //   522: aaload
    //   523: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   526: aload_0
    //   527: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   530: iload_3
    //   531: aaload
    //   532: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   535: iconst_0
    //   536: bipush #8
    //   538: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   541: aload #19
    //   543: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   546: iload_3
    //   547: iconst_1
    //   548: iadd
    //   549: aaload
    //   550: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   553: astore #22
    //   555: aload #21
    //   557: astore #17
    //   559: aload #22
    //   561: ifnull -> 617
    //   564: aload #22
    //   566: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   569: astore #22
    //   571: aload #21
    //   573: astore #17
    //   575: aload #22
    //   577: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   580: iload_3
    //   581: aaload
    //   582: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   585: ifnull -> 617
    //   588: aload #22
    //   590: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   593: iload_3
    //   594: aaload
    //   595: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   598: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   601: aload #19
    //   603: if_acmpeq -> 613
    //   606: aload #21
    //   608: astore #17
    //   610: goto -> 617
    //   613: aload #22
    //   615: astore #17
    //   617: aload #17
    //   619: ifnull -> 629
    //   622: aload #17
    //   624: astore #19
    //   626: goto -> 632
    //   629: iconst_1
    //   630: istore #8
    //   632: goto -> 220
    //   635: aload #24
    //   637: ifnull -> 831
    //   640: aload #23
    //   642: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   645: astore #17
    //   647: iload_3
    //   648: iconst_1
    //   649: iadd
    //   650: istore #9
    //   652: aload #17
    //   654: iload #9
    //   656: aaload
    //   657: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   660: ifnull -> 831
    //   663: aload #24
    //   665: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   668: iload #9
    //   670: aaload
    //   671: astore #17
    //   673: aload #24
    //   675: getfield mListDimensionBehaviors : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   678: iload_2
    //   679: aaload
    //   680: getstatic androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour.MATCH_CONSTRAINT : Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   683: if_acmpne -> 702
    //   686: aload #24
    //   688: getfield mResolvedMatchConstraintDefault : [I
    //   691: iload_2
    //   692: iaload
    //   693: ifne -> 702
    //   696: iconst_1
    //   697: istore #8
    //   699: goto -> 705
    //   702: iconst_0
    //   703: istore #8
    //   705: iload #8
    //   707: ifeq -> 755
    //   710: iload #13
    //   712: ifne -> 755
    //   715: aload #17
    //   717: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   720: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   723: aload_0
    //   724: if_acmpne -> 755
    //   727: aload_1
    //   728: aload #17
    //   730: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   733: aload #17
    //   735: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   738: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   741: aload #17
    //   743: invokevirtual getMargin : ()I
    //   746: ineg
    //   747: iconst_5
    //   748: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   751: pop
    //   752: goto -> 797
    //   755: iload #13
    //   757: ifeq -> 797
    //   760: aload #17
    //   762: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   765: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   768: aload_0
    //   769: if_acmpne -> 797
    //   772: aload_1
    //   773: aload #17
    //   775: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   778: aload #17
    //   780: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   783: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   786: aload #17
    //   788: invokevirtual getMargin : ()I
    //   791: ineg
    //   792: iconst_4
    //   793: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   796: pop
    //   797: aload_1
    //   798: aload #17
    //   800: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   803: aload #23
    //   805: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   808: iload #9
    //   810: aaload
    //   811: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   814: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   817: aload #17
    //   819: invokevirtual getMargin : ()I
    //   822: ineg
    //   823: bipush #6
    //   825: invokevirtual addLowerThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   828: goto -> 831
    //   831: iload #12
    //   833: ifeq -> 881
    //   836: aload_0
    //   837: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   840: astore_0
    //   841: iload_3
    //   842: iconst_1
    //   843: iadd
    //   844: istore #8
    //   846: aload_1
    //   847: aload_0
    //   848: iload #8
    //   850: aaload
    //   851: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   854: aload #23
    //   856: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   859: iload #8
    //   861: aaload
    //   862: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   865: aload #23
    //   867: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   870: iload #8
    //   872: aaload
    //   873: invokevirtual getMargin : ()I
    //   876: bipush #8
    //   878: invokevirtual addGreaterThan : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   881: aload #4
    //   883: getfield mWeightedMatchConstraintsWidgets : Ljava/util/ArrayList;
    //   886: astore_0
    //   887: aload_0
    //   888: ifnull -> 1180
    //   891: aload_0
    //   892: invokevirtual size : ()I
    //   895: istore #9
    //   897: iload #9
    //   899: iconst_1
    //   900: if_icmple -> 1180
    //   903: aload #4
    //   905: getfield mHasUndefinedWeights : Z
    //   908: ifeq -> 930
    //   911: aload #4
    //   913: getfield mHasComplexMatchWeights : Z
    //   916: ifne -> 930
    //   919: aload #4
    //   921: getfield mWidgetsMatchCount : I
    //   924: i2f
    //   925: fstore #6
    //   927: goto -> 934
    //   930: fload #5
    //   932: fstore #6
    //   934: aconst_null
    //   935: astore #17
    //   937: iconst_0
    //   938: istore #8
    //   940: fconst_0
    //   941: fstore #7
    //   943: iload #8
    //   945: iload #9
    //   947: if_icmpge -> 1180
    //   950: aload_0
    //   951: iload #8
    //   953: invokevirtual get : (I)Ljava/lang/Object;
    //   956: checkcast androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   959: astore #19
    //   961: aload #19
    //   963: getfield mWeight : [F
    //   966: iload_2
    //   967: faload
    //   968: fstore #5
    //   970: fload #5
    //   972: fconst_0
    //   973: fcmpg
    //   974: ifge -> 1023
    //   977: aload #4
    //   979: getfield mHasComplexMatchWeights : Z
    //   982: ifeq -> 1017
    //   985: aload_1
    //   986: aload #19
    //   988: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   991: iload_3
    //   992: iconst_1
    //   993: iadd
    //   994: aaload
    //   995: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   998: aload #19
    //   1000: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1003: iload_3
    //   1004: aaload
    //   1005: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1008: iconst_0
    //   1009: iconst_4
    //   1010: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1013: pop
    //   1014: goto -> 1060
    //   1017: fconst_1
    //   1018: fstore #5
    //   1020: goto -> 1023
    //   1023: fload #5
    //   1025: fconst_0
    //   1026: fcmpl
    //   1027: ifne -> 1067
    //   1030: aload_1
    //   1031: aload #19
    //   1033: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1036: iload_3
    //   1037: iconst_1
    //   1038: iadd
    //   1039: aaload
    //   1040: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1043: aload #19
    //   1045: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1048: iload_3
    //   1049: aaload
    //   1050: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1053: iconst_0
    //   1054: bipush #8
    //   1056: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   1059: pop
    //   1060: fload #7
    //   1062: fstore #5
    //   1064: goto -> 1170
    //   1067: aload #17
    //   1069: ifnull -> 1166
    //   1072: aload #17
    //   1074: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1077: iload_3
    //   1078: aaload
    //   1079: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1082: astore #21
    //   1084: aload #17
    //   1086: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1089: astore #17
    //   1091: iload_3
    //   1092: iconst_1
    //   1093: iadd
    //   1094: istore #12
    //   1096: aload #17
    //   1098: iload #12
    //   1100: aaload
    //   1101: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1104: astore #17
    //   1106: aload #19
    //   1108: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1111: iload_3
    //   1112: aaload
    //   1113: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1116: astore #22
    //   1118: aload #19
    //   1120: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1123: iload #12
    //   1125: aaload
    //   1126: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1129: astore #27
    //   1131: aload_1
    //   1132: invokevirtual createRow : ()Landroidx/constraintlayout/solver/ArrayRow;
    //   1135: astore #28
    //   1137: aload #28
    //   1139: fload #7
    //   1141: fload #6
    //   1143: fload #5
    //   1145: aload #21
    //   1147: aload #17
    //   1149: aload #22
    //   1151: aload #27
    //   1153: invokevirtual createRowEqualMatchDimensions : (FFFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;)Landroidx/constraintlayout/solver/ArrayRow;
    //   1156: pop
    //   1157: aload_1
    //   1158: aload #28
    //   1160: invokevirtual addConstraint : (Landroidx/constraintlayout/solver/ArrayRow;)V
    //   1163: goto -> 1166
    //   1166: aload #19
    //   1168: astore #17
    //   1170: iinc #8, 1
    //   1173: fload #5
    //   1175: fstore #7
    //   1177: goto -> 943
    //   1180: aload #20
    //   1182: ifnull -> 1357
    //   1185: aload #20
    //   1187: aload #24
    //   1189: if_acmpeq -> 1197
    //   1192: iload #13
    //   1194: ifeq -> 1357
    //   1197: aload #26
    //   1199: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1202: iload_3
    //   1203: aaload
    //   1204: astore_0
    //   1205: aload #23
    //   1207: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1210: astore #4
    //   1212: iload_3
    //   1213: iconst_1
    //   1214: iadd
    //   1215: istore #8
    //   1217: aload #4
    //   1219: iload #8
    //   1221: aaload
    //   1222: astore #4
    //   1224: aload_0
    //   1225: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1228: ifnull -> 1242
    //   1231: aload_0
    //   1232: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1235: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1238: astore_0
    //   1239: goto -> 1244
    //   1242: aconst_null
    //   1243: astore_0
    //   1244: aload #4
    //   1246: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1249: ifnull -> 1265
    //   1252: aload #4
    //   1254: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1257: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1260: astore #4
    //   1262: goto -> 1268
    //   1265: aconst_null
    //   1266: astore #4
    //   1268: aload #20
    //   1270: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1273: iload_3
    //   1274: aaload
    //   1275: astore #19
    //   1277: aload #24
    //   1279: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1282: iload #8
    //   1284: aaload
    //   1285: astore #17
    //   1287: aload_0
    //   1288: ifnull -> 2424
    //   1291: aload #4
    //   1293: ifnull -> 2424
    //   1296: iload_2
    //   1297: ifne -> 1310
    //   1300: aload #18
    //   1302: getfield mHorizontalBiasPercent : F
    //   1305: fstore #5
    //   1307: goto -> 1317
    //   1310: aload #18
    //   1312: getfield mVerticalBiasPercent : F
    //   1315: fstore #5
    //   1317: aload #19
    //   1319: invokevirtual getMargin : ()I
    //   1322: istore_2
    //   1323: aload #17
    //   1325: invokevirtual getMargin : ()I
    //   1328: istore #8
    //   1330: aload_1
    //   1331: aload #19
    //   1333: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1336: aload_0
    //   1337: iload_2
    //   1338: fload #5
    //   1340: aload #4
    //   1342: aload #17
    //   1344: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1347: iload #8
    //   1349: bipush #7
    //   1351: invokevirtual addCentering : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1354: goto -> 2424
    //   1357: iload #11
    //   1359: ifeq -> 1849
    //   1362: aload #20
    //   1364: ifnull -> 1849
    //   1367: aload #4
    //   1369: getfield mWidgetsMatchCount : I
    //   1372: ifle -> 1394
    //   1375: aload #4
    //   1377: getfield mWidgetsCount : I
    //   1380: aload #4
    //   1382: getfield mWidgetsMatchCount : I
    //   1385: if_icmpne -> 1394
    //   1388: iconst_1
    //   1389: istore #12
    //   1391: goto -> 1397
    //   1394: iconst_0
    //   1395: istore #12
    //   1397: aload #20
    //   1399: astore #4
    //   1401: aload #4
    //   1403: astore #19
    //   1405: aload #4
    //   1407: ifnull -> 2424
    //   1410: aload #4
    //   1412: getfield mNextChainWidget : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1415: iload_2
    //   1416: aaload
    //   1417: astore #17
    //   1419: aload #17
    //   1421: ifnull -> 1446
    //   1424: aload #17
    //   1426: invokevirtual getVisibility : ()I
    //   1429: bipush #8
    //   1431: if_icmpne -> 1446
    //   1434: aload #17
    //   1436: getfield mNextChainWidget : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1439: iload_2
    //   1440: aaload
    //   1441: astore #17
    //   1443: goto -> 1419
    //   1446: aload #17
    //   1448: ifnonnull -> 1464
    //   1451: aload #4
    //   1453: aload #24
    //   1455: if_acmpne -> 1461
    //   1458: goto -> 1464
    //   1461: goto -> 1828
    //   1464: aload #4
    //   1466: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1469: iload_3
    //   1470: aaload
    //   1471: astore #21
    //   1473: aload #21
    //   1475: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1478: astore #27
    //   1480: aload #21
    //   1482: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1485: ifnull -> 1501
    //   1488: aload #21
    //   1490: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1493: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1496: astore #18
    //   1498: goto -> 1504
    //   1501: aconst_null
    //   1502: astore #18
    //   1504: aload #19
    //   1506: aload #4
    //   1508: if_acmpeq -> 1527
    //   1511: aload #19
    //   1513: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1516: iload_3
    //   1517: iconst_1
    //   1518: iadd
    //   1519: aaload
    //   1520: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1523: astore_0
    //   1524: goto -> 1579
    //   1527: aload #18
    //   1529: astore_0
    //   1530: aload #4
    //   1532: aload #20
    //   1534: if_acmpne -> 1579
    //   1537: aload #18
    //   1539: astore_0
    //   1540: aload #19
    //   1542: aload #4
    //   1544: if_acmpne -> 1579
    //   1547: aload #26
    //   1549: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1552: iload_3
    //   1553: aaload
    //   1554: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1557: ifnull -> 1577
    //   1560: aload #26
    //   1562: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1565: iload_3
    //   1566: aaload
    //   1567: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1570: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1573: astore_0
    //   1574: goto -> 1579
    //   1577: aconst_null
    //   1578: astore_0
    //   1579: aload #21
    //   1581: invokevirtual getMargin : ()I
    //   1584: istore #13
    //   1586: aload #4
    //   1588: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1591: astore #18
    //   1593: iload_3
    //   1594: iconst_1
    //   1595: iadd
    //   1596: istore #14
    //   1598: aload #18
    //   1600: iload #14
    //   1602: aaload
    //   1603: invokevirtual getMargin : ()I
    //   1606: istore #9
    //   1608: aload #17
    //   1610: ifnull -> 1645
    //   1613: aload #17
    //   1615: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1618: iload_3
    //   1619: aaload
    //   1620: astore #21
    //   1622: aload #21
    //   1624: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1627: astore #18
    //   1629: aload #4
    //   1631: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1634: iload #14
    //   1636: aaload
    //   1637: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1640: astore #22
    //   1642: goto -> 1689
    //   1645: aload #23
    //   1647: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1650: iload #14
    //   1652: aaload
    //   1653: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1656: astore #21
    //   1658: aload #21
    //   1660: ifnull -> 1673
    //   1663: aload #21
    //   1665: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1668: astore #18
    //   1670: goto -> 1676
    //   1673: aconst_null
    //   1674: astore #18
    //   1676: aload #4
    //   1678: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1681: iload #14
    //   1683: aaload
    //   1684: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1687: astore #22
    //   1689: iload #9
    //   1691: istore #8
    //   1693: aload #21
    //   1695: ifnull -> 1708
    //   1698: iload #9
    //   1700: aload #21
    //   1702: invokevirtual getMargin : ()I
    //   1705: iadd
    //   1706: istore #8
    //   1708: iload #13
    //   1710: istore #9
    //   1712: aload #19
    //   1714: ifnull -> 1733
    //   1717: iload #13
    //   1719: aload #19
    //   1721: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1724: iload #14
    //   1726: aaload
    //   1727: invokevirtual getMargin : ()I
    //   1730: iadd
    //   1731: istore #9
    //   1733: aload #27
    //   1735: ifnull -> 1461
    //   1738: aload_0
    //   1739: ifnull -> 1461
    //   1742: aload #18
    //   1744: ifnull -> 1461
    //   1747: aload #22
    //   1749: ifnull -> 1461
    //   1752: aload #4
    //   1754: aload #20
    //   1756: if_acmpne -> 1771
    //   1759: aload #20
    //   1761: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1764: iload_3
    //   1765: aaload
    //   1766: invokevirtual getMargin : ()I
    //   1769: istore #9
    //   1771: aload #4
    //   1773: aload #24
    //   1775: if_acmpne -> 1794
    //   1778: aload #24
    //   1780: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1783: iload #14
    //   1785: aaload
    //   1786: invokevirtual getMargin : ()I
    //   1789: istore #8
    //   1791: goto -> 1794
    //   1794: iload #12
    //   1796: ifeq -> 1806
    //   1799: bipush #8
    //   1801: istore #13
    //   1803: goto -> 1809
    //   1806: iconst_5
    //   1807: istore #13
    //   1809: aload_1
    //   1810: aload #27
    //   1812: aload_0
    //   1813: iload #9
    //   1815: ldc 0.5
    //   1817: aload #18
    //   1819: aload #22
    //   1821: iload #8
    //   1823: iload #13
    //   1825: invokevirtual addCentering : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   1828: aload #4
    //   1830: invokevirtual getVisibility : ()I
    //   1833: bipush #8
    //   1835: if_icmpeq -> 1842
    //   1838: aload #4
    //   1840: astore #19
    //   1842: aload #17
    //   1844: astore #4
    //   1846: goto -> 1405
    //   1849: iload #10
    //   1851: ifeq -> 2424
    //   1854: aload #20
    //   1856: ifnull -> 2424
    //   1859: aload #4
    //   1861: getfield mWidgetsMatchCount : I
    //   1864: ifle -> 1886
    //   1867: aload #4
    //   1869: getfield mWidgetsCount : I
    //   1872: aload #4
    //   1874: getfield mWidgetsMatchCount : I
    //   1877: if_icmpne -> 1886
    //   1880: iconst_1
    //   1881: istore #8
    //   1883: goto -> 1889
    //   1886: iconst_0
    //   1887: istore #8
    //   1889: aload #20
    //   1891: astore #17
    //   1893: aload #17
    //   1895: astore #4
    //   1897: aload #17
    //   1899: ifnull -> 2264
    //   1902: aload #17
    //   1904: getfield mNextChainWidget : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1907: iload_2
    //   1908: aaload
    //   1909: astore_0
    //   1910: aload_0
    //   1911: ifnull -> 1933
    //   1914: aload_0
    //   1915: invokevirtual getVisibility : ()I
    //   1918: bipush #8
    //   1920: if_icmpne -> 1933
    //   1923: aload_0
    //   1924: getfield mNextChainWidget : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1927: iload_2
    //   1928: aaload
    //   1929: astore_0
    //   1930: goto -> 1910
    //   1933: aload #17
    //   1935: aload #20
    //   1937: if_acmpeq -> 2237
    //   1940: aload #17
    //   1942: aload #24
    //   1944: if_acmpeq -> 2237
    //   1947: aload_0
    //   1948: ifnull -> 2237
    //   1951: aload_0
    //   1952: aload #24
    //   1954: if_acmpne -> 1962
    //   1957: aconst_null
    //   1958: astore_0
    //   1959: goto -> 1962
    //   1962: aload #17
    //   1964: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1967: iload_3
    //   1968: aaload
    //   1969: astore #18
    //   1971: aload #18
    //   1973: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1976: astore #27
    //   1978: aload #18
    //   1980: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1983: ifnull -> 1996
    //   1986: aload #18
    //   1988: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1991: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   1994: astore #19
    //   1996: aload #4
    //   1998: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2001: astore #19
    //   2003: iload_3
    //   2004: iconst_1
    //   2005: iadd
    //   2006: istore #14
    //   2008: aload #19
    //   2010: iload #14
    //   2012: aaload
    //   2013: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2016: astore #28
    //   2018: aload #18
    //   2020: invokevirtual getMargin : ()I
    //   2023: istore #13
    //   2025: aload #17
    //   2027: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2030: iload #14
    //   2032: aaload
    //   2033: invokevirtual getMargin : ()I
    //   2036: istore #12
    //   2038: aload_0
    //   2039: ifnull -> 2084
    //   2042: aload_0
    //   2043: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2046: iload_3
    //   2047: aaload
    //   2048: astore #19
    //   2050: aload #19
    //   2052: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2055: astore #21
    //   2057: aload #19
    //   2059: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2062: ifnull -> 2078
    //   2065: aload #19
    //   2067: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2070: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2073: astore #18
    //   2075: goto -> 2132
    //   2078: aconst_null
    //   2079: astore #18
    //   2081: goto -> 2132
    //   2084: aload #24
    //   2086: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2089: iload_3
    //   2090: aaload
    //   2091: astore #22
    //   2093: aload #22
    //   2095: ifnull -> 2108
    //   2098: aload #22
    //   2100: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2103: astore #19
    //   2105: goto -> 2111
    //   2108: aconst_null
    //   2109: astore #19
    //   2111: aload #17
    //   2113: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2116: iload #14
    //   2118: aaload
    //   2119: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2122: astore #18
    //   2124: aload #19
    //   2126: astore #21
    //   2128: aload #22
    //   2130: astore #19
    //   2132: iload #12
    //   2134: istore #9
    //   2136: aload #19
    //   2138: ifnull -> 2151
    //   2141: iload #12
    //   2143: aload #19
    //   2145: invokevirtual getMargin : ()I
    //   2148: iadd
    //   2149: istore #9
    //   2151: iload #13
    //   2153: istore #12
    //   2155: aload #4
    //   2157: ifnull -> 2176
    //   2160: iload #13
    //   2162: aload #4
    //   2164: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2167: iload #14
    //   2169: aaload
    //   2170: invokevirtual getMargin : ()I
    //   2173: iadd
    //   2174: istore #12
    //   2176: iload #8
    //   2178: ifeq -> 2188
    //   2181: bipush #8
    //   2183: istore #13
    //   2185: goto -> 2191
    //   2188: iconst_4
    //   2189: istore #13
    //   2191: aload #27
    //   2193: ifnull -> 2234
    //   2196: aload #28
    //   2198: ifnull -> 2234
    //   2201: aload #21
    //   2203: ifnull -> 2234
    //   2206: aload #18
    //   2208: ifnull -> 2234
    //   2211: aload_1
    //   2212: aload #27
    //   2214: aload #28
    //   2216: iload #12
    //   2218: ldc 0.5
    //   2220: aload #21
    //   2222: aload #18
    //   2224: iload #9
    //   2226: iload #13
    //   2228: invokevirtual addCentering : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2231: goto -> 2234
    //   2234: goto -> 2237
    //   2237: aload #17
    //   2239: invokevirtual getVisibility : ()I
    //   2242: bipush #8
    //   2244: if_icmpeq -> 2250
    //   2247: goto -> 2254
    //   2250: aload #4
    //   2252: astore #17
    //   2254: aload #17
    //   2256: astore #4
    //   2258: aload_0
    //   2259: astore #17
    //   2261: goto -> 1897
    //   2264: aload #20
    //   2266: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2269: iload_3
    //   2270: aaload
    //   2271: astore_0
    //   2272: aload #26
    //   2274: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2277: iload_3
    //   2278: aaload
    //   2279: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2282: astore #4
    //   2284: aload #24
    //   2286: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2289: astore #17
    //   2291: iload_3
    //   2292: iconst_1
    //   2293: iadd
    //   2294: istore_2
    //   2295: aload #17
    //   2297: iload_2
    //   2298: aaload
    //   2299: astore #17
    //   2301: aload #23
    //   2303: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2306: iload_2
    //   2307: aaload
    //   2308: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2311: astore #18
    //   2313: aload #4
    //   2315: ifnull -> 2390
    //   2318: aload #20
    //   2320: aload #24
    //   2322: if_acmpeq -> 2347
    //   2325: aload_1
    //   2326: aload_0
    //   2327: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2330: aload #4
    //   2332: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2335: aload_0
    //   2336: invokevirtual getMargin : ()I
    //   2339: iconst_5
    //   2340: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   2343: pop
    //   2344: goto -> 2390
    //   2347: aload #18
    //   2349: ifnull -> 2390
    //   2352: aload_1
    //   2353: aload_0
    //   2354: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2357: aload #4
    //   2359: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2362: aload_0
    //   2363: invokevirtual getMargin : ()I
    //   2366: ldc 0.5
    //   2368: aload #17
    //   2370: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2373: aload #18
    //   2375: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2378: aload #17
    //   2380: invokevirtual getMargin : ()I
    //   2383: iconst_5
    //   2384: invokevirtual addCentering : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2387: goto -> 2390
    //   2390: aload #18
    //   2392: ifnull -> 2424
    //   2395: aload #20
    //   2397: aload #24
    //   2399: if_acmpeq -> 2424
    //   2402: aload_1
    //   2403: aload #17
    //   2405: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2408: aload #18
    //   2410: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2413: aload #17
    //   2415: invokevirtual getMargin : ()I
    //   2418: ineg
    //   2419: iconst_5
    //   2420: invokevirtual addEquality : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)Landroidx/constraintlayout/solver/ArrayRow;
    //   2423: pop
    //   2424: iload #11
    //   2426: ifne -> 2434
    //   2429: iload #10
    //   2431: ifeq -> 2644
    //   2434: aload #20
    //   2436: ifnull -> 2644
    //   2439: aload #20
    //   2441: aload #24
    //   2443: if_acmpeq -> 2644
    //   2446: aload #20
    //   2448: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2451: iload_3
    //   2452: aaload
    //   2453: astore #18
    //   2455: aload #24
    //   2457: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2460: astore_0
    //   2461: iload_3
    //   2462: iconst_1
    //   2463: iadd
    //   2464: istore_2
    //   2465: aload_0
    //   2466: iload_2
    //   2467: aaload
    //   2468: astore #17
    //   2470: aload #18
    //   2472: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2475: ifnull -> 2491
    //   2478: aload #18
    //   2480: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2483: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2486: astore #4
    //   2488: goto -> 2494
    //   2491: aconst_null
    //   2492: astore #4
    //   2494: aload #17
    //   2496: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2499: ifnull -> 2514
    //   2502: aload #17
    //   2504: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2507: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2510: astore_0
    //   2511: goto -> 2516
    //   2514: aconst_null
    //   2515: astore_0
    //   2516: aload #23
    //   2518: aload #24
    //   2520: if_acmpeq -> 2555
    //   2523: aload #23
    //   2525: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2528: iload_2
    //   2529: aaload
    //   2530: astore #19
    //   2532: aload #25
    //   2534: astore_0
    //   2535: aload #19
    //   2537: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2540: ifnull -> 2552
    //   2543: aload #19
    //   2545: getfield mTarget : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2548: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2551: astore_0
    //   2552: goto -> 2555
    //   2555: aload #20
    //   2557: aload #24
    //   2559: if_acmpne -> 2580
    //   2562: aload #20
    //   2564: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2567: iload_3
    //   2568: aaload
    //   2569: astore #18
    //   2571: aload #20
    //   2573: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2576: iload_2
    //   2577: aaload
    //   2578: astore #17
    //   2580: aload #4
    //   2582: ifnull -> 2644
    //   2585: aload_0
    //   2586: ifnull -> 2644
    //   2589: aload #18
    //   2591: invokevirtual getMargin : ()I
    //   2594: istore_3
    //   2595: aload #24
    //   2597: ifnonnull -> 2607
    //   2600: aload #23
    //   2602: astore #19
    //   2604: goto -> 2611
    //   2607: aload #24
    //   2609: astore #19
    //   2611: aload #19
    //   2613: getfield mListAnchors : [Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   2616: iload_2
    //   2617: aaload
    //   2618: invokevirtual getMargin : ()I
    //   2621: istore_2
    //   2622: aload_1
    //   2623: aload #18
    //   2625: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2628: aload #4
    //   2630: iload_3
    //   2631: ldc 0.5
    //   2633: aload_0
    //   2634: aload #17
    //   2636: getfield mSolverVariable : Landroidx/constraintlayout/solver/SolverVariable;
    //   2639: iload_2
    //   2640: iconst_5
    //   2641: invokevirtual addCentering : (Landroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;IFLandroidx/constraintlayout/solver/SolverVariable;Landroidx/constraintlayout/solver/SolverVariable;II)V
    //   2644: return
  }
  
  public static void applyChainConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, ArrayList<ConstraintWidget> paramArrayList, int paramInt) {
    byte b1;
    int i;
    ChainHead[] arrayOfChainHead;
    byte b2 = 0;
    if (paramInt == 0) {
      i = paramConstraintWidgetContainer.mHorizontalChainsSize;
      arrayOfChainHead = paramConstraintWidgetContainer.mHorizontalChainsArray;
      b1 = 0;
    } else {
      i = paramConstraintWidgetContainer.mVerticalChainsSize;
      arrayOfChainHead = paramConstraintWidgetContainer.mVerticalChainsArray;
      b1 = 2;
    } 
    while (b2 < i) {
      ChainHead chainHead = arrayOfChainHead[b2];
      chainHead.define();
      if (paramArrayList == null || (paramArrayList != null && paramArrayList.contains(chainHead.mFirst)))
        applyChainConstraints(paramConstraintWidgetContainer, paramLinearSystem, paramInt, b1, chainHead); 
      b2++;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\Chain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */