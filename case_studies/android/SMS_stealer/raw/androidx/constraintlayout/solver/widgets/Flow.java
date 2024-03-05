package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import java.util.ArrayList;
import java.util.HashMap;

public class Flow extends VirtualLayout {
  public static final int HORIZONTAL_ALIGN_CENTER = 2;
  
  public static final int HORIZONTAL_ALIGN_END = 1;
  
  public static final int HORIZONTAL_ALIGN_START = 0;
  
  public static final int VERTICAL_ALIGN_BASELINE = 3;
  
  public static final int VERTICAL_ALIGN_BOTTOM = 1;
  
  public static final int VERTICAL_ALIGN_CENTER = 2;
  
  public static final int VERTICAL_ALIGN_TOP = 0;
  
  public static final int WRAP_ALIGNED = 2;
  
  public static final int WRAP_CHAIN = 1;
  
  public static final int WRAP_NONE = 0;
  
  private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
  
  private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
  
  private int[] mAlignedDimensions = null;
  
  private ArrayList<WidgetsList> mChainList = new ArrayList<WidgetsList>();
  
  private ConstraintWidget[] mDisplayedWidgets;
  
  private int mDisplayedWidgetsCount = 0;
  
  private float mFirstHorizontalBias = 0.5F;
  
  private int mFirstHorizontalStyle = -1;
  
  private float mFirstVerticalBias = 0.5F;
  
  private int mFirstVerticalStyle = -1;
  
  private int mHorizontalAlign = 2;
  
  private float mHorizontalBias = 0.5F;
  
  private int mHorizontalGap = 0;
  
  private int mHorizontalStyle = -1;
  
  private float mLastHorizontalBias = 0.5F;
  
  private int mLastHorizontalStyle = -1;
  
  private float mLastVerticalBias = 0.5F;
  
  private int mLastVerticalStyle = -1;
  
  private int mMaxElementsWrap = -1;
  
  private int mOrientation = 0;
  
  private int mVerticalAlign = 2;
  
  private float mVerticalBias = 0.5F;
  
  private int mVerticalGap = 0;
  
  private int mVerticalStyle = -1;
  
  private int mWrapMode = 0;
  
  private void createAlignedConstraints(boolean paramBoolean) {
    if (this.mAlignedDimensions != null && this.mAlignedBiggestElementsInCols != null && this.mAlignedBiggestElementsInRows != null) {
      ConstraintWidget constraintWidget;
      byte b;
      for (b = 0; b < this.mDisplayedWidgetsCount; b++)
        this.mDisplayedWidgets[b].resetAnchors(); 
      int[] arrayOfInt = this.mAlignedDimensions;
      int i = arrayOfInt[0];
      int j = arrayOfInt[1];
      arrayOfInt = null;
      b = 0;
      while (b < i) {
        byte b1;
        ConstraintWidget constraintWidget1;
        if (paramBoolean) {
          b1 = i - b - 1;
        } else {
          b1 = b;
        } 
        ConstraintWidget constraintWidget2 = this.mAlignedBiggestElementsInCols[b1];
        int[] arrayOfInt1 = arrayOfInt;
        if (constraintWidget2 != null)
          if (constraintWidget2.getVisibility() == 8) {
            arrayOfInt1 = arrayOfInt;
          } else {
            if (b == 0) {
              constraintWidget2.connect(constraintWidget2.mLeft, this.mLeft, getPaddingLeft());
              constraintWidget2.setHorizontalChainStyle(this.mHorizontalStyle);
              constraintWidget2.setHorizontalBiasPercent(this.mHorizontalBias);
            } 
            if (b == i - 1)
              constraintWidget2.connect(constraintWidget2.mRight, this.mRight, getPaddingRight()); 
            if (b > 0) {
              constraintWidget2.connect(constraintWidget2.mLeft, ((ConstraintWidget)arrayOfInt).mRight, this.mHorizontalGap);
              arrayOfInt.connect(((ConstraintWidget)arrayOfInt).mRight, constraintWidget2.mLeft, 0);
            } 
            constraintWidget1 = constraintWidget2;
          }  
        b++;
        constraintWidget = constraintWidget1;
      } 
      b = 0;
      while (b < j) {
        ConstraintWidget constraintWidget2 = this.mAlignedBiggestElementsInRows[b];
        ConstraintWidget constraintWidget1 = constraintWidget;
        if (constraintWidget2 != null)
          if (constraintWidget2.getVisibility() == 8) {
            constraintWidget1 = constraintWidget;
          } else {
            if (b == 0) {
              constraintWidget2.connect(constraintWidget2.mTop, this.mTop, getPaddingTop());
              constraintWidget2.setVerticalChainStyle(this.mVerticalStyle);
              constraintWidget2.setVerticalBiasPercent(this.mVerticalBias);
            } 
            if (b == j - 1)
              constraintWidget2.connect(constraintWidget2.mBottom, this.mBottom, getPaddingBottom()); 
            if (b > 0) {
              constraintWidget2.connect(constraintWidget2.mTop, constraintWidget.mBottom, this.mVerticalGap);
              constraintWidget.connect(constraintWidget.mBottom, constraintWidget2.mTop, 0);
            } 
            constraintWidget1 = constraintWidget2;
          }  
        b++;
        constraintWidget = constraintWidget1;
      } 
      for (b = 0; b < i; b++) {
        for (byte b1 = 0; b1 < j; b1++) {
          int k = b1 * i + b;
          if (this.mOrientation == 1)
            k = b * j + b1; 
          ConstraintWidget[] arrayOfConstraintWidget = this.mDisplayedWidgets;
          if (k < arrayOfConstraintWidget.length) {
            ConstraintWidget constraintWidget1 = arrayOfConstraintWidget[k];
            if (constraintWidget1 != null && constraintWidget1.getVisibility() != 8) {
              ConstraintWidget constraintWidget3 = this.mAlignedBiggestElementsInCols[b];
              ConstraintWidget constraintWidget2 = this.mAlignedBiggestElementsInRows[b1];
              if (constraintWidget1 != constraintWidget3) {
                constraintWidget1.connect(constraintWidget1.mLeft, constraintWidget3.mLeft, 0);
                constraintWidget1.connect(constraintWidget1.mRight, constraintWidget3.mRight, 0);
              } 
              if (constraintWidget1 != constraintWidget2) {
                constraintWidget1.connect(constraintWidget1.mTop, constraintWidget2.mTop, 0);
                constraintWidget1.connect(constraintWidget1.mBottom, constraintWidget2.mBottom, 0);
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  private final int getWidgetHeight(ConstraintWidget paramConstraintWidget, int paramInt) {
    if (paramConstraintWidget == null)
      return 0; 
    if (paramConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 0)
        return 0; 
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 2) {
        paramInt = (int)(paramConstraintWidget.mMatchConstraintPercentHeight * paramInt);
        if (paramInt != paramConstraintWidget.getHeight()) {
          paramConstraintWidget.setMeasureRequested(true);
          measure(paramConstraintWidget, paramConstraintWidget.getHorizontalDimensionBehaviour(), paramConstraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, paramInt);
        } 
        return paramInt;
      } 
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 1)
        return paramConstraintWidget.getHeight(); 
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 3)
        return (int)(paramConstraintWidget.getWidth() * paramConstraintWidget.mDimensionRatio + 0.5F); 
    } 
    return paramConstraintWidget.getHeight();
  }
  
  private final int getWidgetWidth(ConstraintWidget paramConstraintWidget, int paramInt) {
    if (paramConstraintWidget == null)
      return 0; 
    if (paramConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 0)
        return 0; 
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 2) {
        paramInt = (int)(paramConstraintWidget.mMatchConstraintPercentWidth * paramInt);
        if (paramInt != paramConstraintWidget.getWidth()) {
          paramConstraintWidget.setMeasureRequested(true);
          measure(paramConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, paramInt, paramConstraintWidget.getVerticalDimensionBehaviour(), paramConstraintWidget.getHeight());
        } 
        return paramInt;
      } 
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 1)
        return paramConstraintWidget.getWidth(); 
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 3)
        return (int)(paramConstraintWidget.getHeight() * paramConstraintWidget.mDimensionRatio + 0.5F); 
    } 
    return paramConstraintWidget.getWidth();
  }
  
  private void measureAligned(ConstraintWidget[] paramArrayOfConstraintWidget, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint) {
    // Byte code:
    //   0: iload_3
    //   1: ifne -> 120
    //   4: aload_0
    //   5: getfield mMaxElementsWrap : I
    //   8: istore #6
    //   10: iload #6
    //   12: istore #8
    //   14: iload #6
    //   16: ifgt -> 110
    //   19: iconst_0
    //   20: istore #6
    //   22: iconst_0
    //   23: istore #9
    //   25: iconst_0
    //   26: istore #7
    //   28: iload #6
    //   30: istore #8
    //   32: iload #9
    //   34: iload_2
    //   35: if_icmpge -> 110
    //   38: iload #7
    //   40: istore #8
    //   42: iload #9
    //   44: ifle -> 56
    //   47: iload #7
    //   49: aload_0
    //   50: getfield mHorizontalGap : I
    //   53: iadd
    //   54: istore #8
    //   56: aload_1
    //   57: iload #9
    //   59: aaload
    //   60: astore #13
    //   62: aload #13
    //   64: ifnonnull -> 74
    //   67: iload #8
    //   69: istore #7
    //   71: goto -> 104
    //   74: iload #8
    //   76: aload_0
    //   77: aload #13
    //   79: iload #4
    //   81: invokespecial getWidgetWidth : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)I
    //   84: iadd
    //   85: istore #7
    //   87: iload #7
    //   89: iload #4
    //   91: if_icmple -> 101
    //   94: iload #6
    //   96: istore #8
    //   98: goto -> 110
    //   101: iinc #6, 1
    //   104: iinc #9, 1
    //   107: goto -> 28
    //   110: iload #8
    //   112: istore #7
    //   114: iconst_0
    //   115: istore #6
    //   117: goto -> 233
    //   120: aload_0
    //   121: getfield mMaxElementsWrap : I
    //   124: istore #6
    //   126: iload #6
    //   128: istore #8
    //   130: iload #6
    //   132: ifgt -> 226
    //   135: iconst_0
    //   136: istore #6
    //   138: iconst_0
    //   139: istore #9
    //   141: iconst_0
    //   142: istore #7
    //   144: iload #6
    //   146: istore #8
    //   148: iload #9
    //   150: iload_2
    //   151: if_icmpge -> 226
    //   154: iload #7
    //   156: istore #8
    //   158: iload #9
    //   160: ifle -> 172
    //   163: iload #7
    //   165: aload_0
    //   166: getfield mVerticalGap : I
    //   169: iadd
    //   170: istore #8
    //   172: aload_1
    //   173: iload #9
    //   175: aaload
    //   176: astore #13
    //   178: aload #13
    //   180: ifnonnull -> 190
    //   183: iload #8
    //   185: istore #7
    //   187: goto -> 220
    //   190: iload #8
    //   192: aload_0
    //   193: aload #13
    //   195: iload #4
    //   197: invokespecial getWidgetHeight : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)I
    //   200: iadd
    //   201: istore #7
    //   203: iload #7
    //   205: iload #4
    //   207: if_icmple -> 217
    //   210: iload #6
    //   212: istore #8
    //   214: goto -> 226
    //   217: iinc #6, 1
    //   220: iinc #9, 1
    //   223: goto -> 144
    //   226: iconst_0
    //   227: istore #7
    //   229: iload #8
    //   231: istore #6
    //   233: aload_0
    //   234: getfield mAlignedDimensions : [I
    //   237: ifnonnull -> 247
    //   240: aload_0
    //   241: iconst_2
    //   242: newarray int
    //   244: putfield mAlignedDimensions : [I
    //   247: iload #6
    //   249: ifne -> 265
    //   252: iload #6
    //   254: istore #11
    //   256: iload #7
    //   258: istore #9
    //   260: iload_3
    //   261: iconst_1
    //   262: if_icmpeq -> 282
    //   265: iload #7
    //   267: ifne -> 296
    //   270: iload_3
    //   271: ifne -> 296
    //   274: iload #7
    //   276: istore #9
    //   278: iload #6
    //   280: istore #11
    //   282: iconst_1
    //   283: istore #12
    //   285: iload #11
    //   287: istore #6
    //   289: iload #9
    //   291: istore #7
    //   293: goto -> 299
    //   296: iconst_0
    //   297: istore #12
    //   299: iload #12
    //   301: ifne -> 820
    //   304: iload_3
    //   305: ifne -> 324
    //   308: iload_2
    //   309: i2f
    //   310: iload #7
    //   312: i2f
    //   313: fdiv
    //   314: f2d
    //   315: invokestatic ceil : (D)D
    //   318: d2i
    //   319: istore #6
    //   321: goto -> 337
    //   324: iload_2
    //   325: i2f
    //   326: iload #6
    //   328: i2f
    //   329: fdiv
    //   330: f2d
    //   331: invokestatic ceil : (D)D
    //   334: d2i
    //   335: istore #7
    //   337: aload_0
    //   338: getfield mAlignedBiggestElementsInCols : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   341: astore #13
    //   343: aload #13
    //   345: ifnull -> 368
    //   348: aload #13
    //   350: arraylength
    //   351: iload #7
    //   353: if_icmpge -> 359
    //   356: goto -> 368
    //   359: aload #13
    //   361: aconst_null
    //   362: invokestatic fill : ([Ljava/lang/Object;Ljava/lang/Object;)V
    //   365: goto -> 377
    //   368: aload_0
    //   369: iload #7
    //   371: anewarray androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   374: putfield mAlignedBiggestElementsInCols : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   377: aload_0
    //   378: getfield mAlignedBiggestElementsInRows : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   381: astore #13
    //   383: aload #13
    //   385: ifnull -> 408
    //   388: aload #13
    //   390: arraylength
    //   391: iload #6
    //   393: if_icmpge -> 399
    //   396: goto -> 408
    //   399: aload #13
    //   401: aconst_null
    //   402: invokestatic fill : ([Ljava/lang/Object;Ljava/lang/Object;)V
    //   405: goto -> 417
    //   408: aload_0
    //   409: iload #6
    //   411: anewarray androidx/constraintlayout/solver/widgets/ConstraintWidget
    //   414: putfield mAlignedBiggestElementsInRows : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   417: iconst_0
    //   418: istore #8
    //   420: iload #8
    //   422: iload #7
    //   424: if_icmpge -> 590
    //   427: iconst_0
    //   428: istore #9
    //   430: iload #9
    //   432: iload #6
    //   434: if_icmpge -> 584
    //   437: iload #9
    //   439: iload #7
    //   441: imul
    //   442: iload #8
    //   444: iadd
    //   445: istore #10
    //   447: iload_3
    //   448: iconst_1
    //   449: if_icmpne -> 462
    //   452: iload #8
    //   454: iload #6
    //   456: imul
    //   457: iload #9
    //   459: iadd
    //   460: istore #10
    //   462: iload #10
    //   464: aload_1
    //   465: arraylength
    //   466: if_icmplt -> 472
    //   469: goto -> 578
    //   472: aload_1
    //   473: iload #10
    //   475: aaload
    //   476: astore #13
    //   478: aload #13
    //   480: ifnonnull -> 486
    //   483: goto -> 578
    //   486: aload_0
    //   487: aload #13
    //   489: iload #4
    //   491: invokespecial getWidgetWidth : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)I
    //   494: istore #10
    //   496: aload_0
    //   497: getfield mAlignedBiggestElementsInCols : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   500: astore #14
    //   502: aload #14
    //   504: iload #8
    //   506: aaload
    //   507: ifnull -> 523
    //   510: aload #14
    //   512: iload #8
    //   514: aaload
    //   515: invokevirtual getWidth : ()I
    //   518: iload #10
    //   520: if_icmpge -> 532
    //   523: aload_0
    //   524: getfield mAlignedBiggestElementsInCols : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   527: iload #8
    //   529: aload #13
    //   531: aastore
    //   532: aload_0
    //   533: aload #13
    //   535: iload #4
    //   537: invokespecial getWidgetHeight : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)I
    //   540: istore #10
    //   542: aload_0
    //   543: getfield mAlignedBiggestElementsInRows : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   546: astore #14
    //   548: aload #14
    //   550: iload #9
    //   552: aaload
    //   553: ifnull -> 569
    //   556: aload #14
    //   558: iload #9
    //   560: aaload
    //   561: invokevirtual getHeight : ()I
    //   564: iload #10
    //   566: if_icmpge -> 578
    //   569: aload_0
    //   570: getfield mAlignedBiggestElementsInRows : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   573: iload #9
    //   575: aload #13
    //   577: aastore
    //   578: iinc #9, 1
    //   581: goto -> 430
    //   584: iinc #8, 1
    //   587: goto -> 420
    //   590: iconst_0
    //   591: istore #9
    //   593: iconst_0
    //   594: istore #8
    //   596: iload #9
    //   598: iload #7
    //   600: if_icmpge -> 662
    //   603: aload_0
    //   604: getfield mAlignedBiggestElementsInCols : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   607: iload #9
    //   609: aaload
    //   610: astore #13
    //   612: iload #8
    //   614: istore #10
    //   616: aload #13
    //   618: ifnull -> 652
    //   621: iload #8
    //   623: istore #10
    //   625: iload #9
    //   627: ifle -> 639
    //   630: iload #8
    //   632: aload_0
    //   633: getfield mHorizontalGap : I
    //   636: iadd
    //   637: istore #10
    //   639: iload #10
    //   641: aload_0
    //   642: aload #13
    //   644: iload #4
    //   646: invokespecial getWidgetWidth : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)I
    //   649: iadd
    //   650: istore #10
    //   652: iinc #9, 1
    //   655: iload #10
    //   657: istore #8
    //   659: goto -> 596
    //   662: iconst_0
    //   663: istore #9
    //   665: iconst_0
    //   666: istore #10
    //   668: iload #9
    //   670: iload #6
    //   672: if_icmpge -> 734
    //   675: aload_0
    //   676: getfield mAlignedBiggestElementsInRows : [Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   679: iload #9
    //   681: aaload
    //   682: astore #13
    //   684: iload #10
    //   686: istore #11
    //   688: aload #13
    //   690: ifnull -> 724
    //   693: iload #10
    //   695: istore #11
    //   697: iload #9
    //   699: ifle -> 711
    //   702: iload #10
    //   704: aload_0
    //   705: getfield mVerticalGap : I
    //   708: iadd
    //   709: istore #11
    //   711: iload #11
    //   713: aload_0
    //   714: aload #13
    //   716: iload #4
    //   718: invokespecial getWidgetHeight : (Landroidx/constraintlayout/solver/widgets/ConstraintWidget;I)I
    //   721: iadd
    //   722: istore #11
    //   724: iinc #9, 1
    //   727: iload #11
    //   729: istore #10
    //   731: goto -> 668
    //   734: aload #5
    //   736: iconst_0
    //   737: iload #8
    //   739: iastore
    //   740: aload #5
    //   742: iconst_1
    //   743: iload #10
    //   745: iastore
    //   746: iload_3
    //   747: ifne -> 785
    //   750: iload #6
    //   752: istore #11
    //   754: iload #7
    //   756: istore #9
    //   758: iload #8
    //   760: iload #4
    //   762: if_icmple -> 282
    //   765: iload #6
    //   767: istore #11
    //   769: iload #7
    //   771: istore #9
    //   773: iload #7
    //   775: iconst_1
    //   776: if_icmple -> 282
    //   779: iinc #7, -1
    //   782: goto -> 299
    //   785: iload #6
    //   787: istore #11
    //   789: iload #7
    //   791: istore #9
    //   793: iload #10
    //   795: iload #4
    //   797: if_icmple -> 282
    //   800: iload #6
    //   802: istore #11
    //   804: iload #7
    //   806: istore #9
    //   808: iload #6
    //   810: iconst_1
    //   811: if_icmple -> 282
    //   814: iinc #6, -1
    //   817: goto -> 299
    //   820: aload_0
    //   821: getfield mAlignedDimensions : [I
    //   824: astore_1
    //   825: aload_1
    //   826: iconst_0
    //   827: iload #7
    //   829: iastore
    //   830: aload_1
    //   831: iconst_1
    //   832: iload #6
    //   834: iastore
    //   835: return
  }
  
  private void measureChainWrap(ConstraintWidget[] paramArrayOfConstraintWidget, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint) {
    ConstraintWidget constraintWidget;
    if (paramInt1 == 0)
      return; 
    this.mChainList.clear();
    WidgetsList widgetsList = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
    this.mChainList.add(widgetsList);
    if (paramInt2 == 0) {
      Object object;
      boolean bool = false;
      int i4 = 0;
      byte b = 0;
      while (true) {
        Object object1 = object;
        if (b < paramInt1) {
          boolean bool1;
          WidgetsList widgetsList1;
          constraintWidget = paramArrayOfConstraintWidget[b];
          int i5 = getWidgetWidth(constraintWidget, paramInt3);
          object1 = object;
          if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            j = object + 1; 
          if ((i4 == paramInt3 || this.mHorizontalGap + i4 + i5 > paramInt3) && widgetsList.biggest != null) {
            bool1 = true;
          } else {
            bool1 = false;
          } 
          boolean bool2 = bool1;
          if (!bool1) {
            bool2 = bool1;
            if (b > 0) {
              int i6 = this.mMaxElementsWrap;
              bool2 = bool1;
              if (i6 > 0) {
                bool2 = bool1;
                if (b % i6 == 0)
                  bool2 = true; 
              } 
            } 
          } 
          if (bool2) {
            widgetsList1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
            widgetsList1.setStartIndex(b);
            this.mChainList.add(widgetsList1);
          } else {
            widgetsList1 = widgetsList;
            if (b > 0) {
              i4 += this.mHorizontalGap + i5;
              continue;
            } 
          } 
          i4 = i5;
          widgetsList = widgetsList1;
          continue;
        } 
        break;
        widgetsList.add((ConstraintWidget)SYNTHETIC_LOCAL_VARIABLE_16);
        b++;
        object = SYNTHETIC_LOCAL_VARIABLE_7;
      } 
    } else {
      int i4 = 0;
      int i5 = 0;
      byte b = 0;
      while (true) {
        j = i4;
        if (b < paramInt1) {
          WidgetsList widgetsList1;
          constraintWidget = paramArrayOfConstraintWidget[b];
          int i7 = getWidgetHeight(constraintWidget, paramInt3);
          j = i4;
          if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            j = i4 + 1; 
          if ((i5 == paramInt3 || this.mVerticalGap + i5 + i7 > paramInt3) && widgetsList.biggest != null) {
            i4 = 1;
          } else {
            i4 = 0;
          } 
          int i6 = i4;
          if (i4 == 0) {
            i6 = i4;
            if (b > 0) {
              int i8 = this.mMaxElementsWrap;
              i6 = i4;
              if (i8 > 0) {
                i6 = i4;
                if (b % i8 == 0)
                  i6 = 1; 
              } 
            } 
          } 
          if (i6 != 0) {
            widgetsList1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
            widgetsList1.setStartIndex(b);
            this.mChainList.add(widgetsList1);
          } else {
            widgetsList1 = widgetsList;
            if (b > 0) {
              i5 += this.mVerticalGap + i7;
              continue;
            } 
          } 
          i5 = i7;
          widgetsList = widgetsList1;
          continue;
        } 
        break;
        widgetsList.add(constraintWidget);
        b++;
        i4 = j;
      } 
    } 
    int i3 = this.mChainList.size();
    ConstraintAnchor constraintAnchor1 = this.mLeft;
    ConstraintAnchor constraintAnchor4 = this.mTop;
    ConstraintAnchor constraintAnchor2 = this.mRight;
    ConstraintAnchor constraintAnchor3 = this.mBottom;
    int k = getPaddingLeft();
    int m = getPaddingTop();
    int i1 = getPaddingRight();
    int n = getPaddingBottom();
    if (getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    if (j > 0 && paramInt1 != 0)
      for (paramInt1 = 0; paramInt1 < i3; paramInt1++) {
        WidgetsList widgetsList1 = this.mChainList.get(paramInt1);
        if (paramInt2 == 0) {
          widgetsList1.measureMatchConstraints(paramInt3 - widgetsList1.getWidth());
        } else {
          widgetsList1.measureMatchConstraints(paramInt3 - widgetsList1.getHeight());
        } 
      }  
    int i = 0;
    int i2 = 0;
    int j = 0;
    while (j < i3) {
      WidgetsList widgetsList1 = this.mChainList.get(j);
      if (paramInt2 == 0) {
        if (j < i3 - 1) {
          constraintAnchor3 = ((WidgetsList)this.mChainList.get(j + 1)).biggest.mTop;
          paramInt1 = 0;
        } else {
          constraintAnchor3 = this.mBottom;
          paramInt1 = getPaddingBottom();
        } 
        ConstraintAnchor constraintAnchor = widgetsList1.biggest.mBottom;
        widgetsList1.setup(paramInt2, constraintAnchor1, constraintAnchor4, constraintAnchor2, constraintAnchor3, k, m, i1, paramInt1, paramInt3);
        m = Math.max(i2, widgetsList1.getWidth());
        n = i + widgetsList1.getHeight();
        i = n;
        if (j > 0)
          i = n + this.mVerticalGap; 
        constraintAnchor4 = constraintAnchor;
        i2 = 0;
        n = paramInt1;
        paramInt1 = m;
        m = i2;
      } else {
        if (j < i3 - 1) {
          constraintAnchor2 = ((WidgetsList)this.mChainList.get(j + 1)).biggest.mLeft;
          paramInt1 = 0;
        } else {
          constraintAnchor2 = this.mRight;
          paramInt1 = getPaddingRight();
        } 
        ConstraintAnchor constraintAnchor = widgetsList1.biggest.mRight;
        widgetsList1.setup(paramInt2, constraintAnchor1, constraintAnchor4, constraintAnchor2, constraintAnchor3, k, m, paramInt1, n, paramInt3);
        k = i2 + widgetsList1.getWidth();
        i1 = Math.max(i, widgetsList1.getHeight());
        i = k;
        if (j > 0)
          i = k + this.mHorizontalGap; 
        k = i1;
        i1 = paramInt1;
        constraintAnchor1 = constraintAnchor;
        i2 = 0;
        paramInt1 = i;
        i = k;
        k = i2;
      } 
      j++;
      i2 = paramInt1;
    } 
    paramArrayOfint[0] = i2;
    paramArrayOfint[1] = i;
  }
  
  private void measureNoWrap(ConstraintWidget[] paramArrayOfConstraintWidget, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint) {
    WidgetsList widgetsList;
    if (paramInt1 == 0)
      return; 
    if (this.mChainList.size() == 0) {
      widgetsList = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
      this.mChainList.add(widgetsList);
    } else {
      widgetsList = this.mChainList.get(0);
      widgetsList.clear();
      ConstraintAnchor constraintAnchor1 = this.mLeft;
      ConstraintAnchor constraintAnchor3 = this.mTop;
      ConstraintAnchor constraintAnchor2 = this.mRight;
      ConstraintAnchor constraintAnchor4 = this.mBottom;
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int m = getPaddingRight();
      int k = getPaddingBottom();
      widgetsList.setup(paramInt2, constraintAnchor1, constraintAnchor3, constraintAnchor2, constraintAnchor4, i, j, m, k, paramInt3);
    } 
    for (paramInt2 = 0; paramInt2 < paramInt1; paramInt2++)
      widgetsList.add(paramArrayOfConstraintWidget[paramInt2]); 
    paramArrayOfint[0] = widgetsList.getWidth();
    paramArrayOfint[1] = widgetsList.getHeight();
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean) {
    super.addToSolver(paramLinearSystem, paramBoolean);
    if (getParent() != null) {
      paramBoolean = ((ConstraintWidgetContainer)getParent()).isRtl();
    } else {
      paramBoolean = false;
    } 
    int i = this.mWrapMode;
    if (i != 0) {
      if (i != 1) {
        if (i == 2)
          createAlignedConstraints(paramBoolean); 
      } else {
        int j = this.mChainList.size();
        for (i = 0; i < j; i++) {
          boolean bool;
          WidgetsList widgetsList = this.mChainList.get(i);
          if (i == j - 1) {
            bool = true;
          } else {
            bool = false;
          } 
          widgetsList.createConstraints(paramBoolean, i, bool);
        } 
      } 
    } else if (this.mChainList.size() > 0) {
      ((WidgetsList)this.mChainList.get(0)).createConstraints(paramBoolean, 0, true);
    } 
    needsCallbackFromSolver(false);
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap) {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = paramConstraintWidget;
    this.mHorizontalStyle = ((Flow)paramConstraintWidget).mHorizontalStyle;
    this.mVerticalStyle = ((Flow)paramConstraintWidget).mVerticalStyle;
    this.mFirstHorizontalStyle = ((Flow)paramConstraintWidget).mFirstHorizontalStyle;
    this.mFirstVerticalStyle = ((Flow)paramConstraintWidget).mFirstVerticalStyle;
    this.mLastHorizontalStyle = ((Flow)paramConstraintWidget).mLastHorizontalStyle;
    this.mLastVerticalStyle = ((Flow)paramConstraintWidget).mLastVerticalStyle;
    this.mHorizontalBias = ((Flow)paramConstraintWidget).mHorizontalBias;
    this.mVerticalBias = ((Flow)paramConstraintWidget).mVerticalBias;
    this.mFirstHorizontalBias = ((Flow)paramConstraintWidget).mFirstHorizontalBias;
    this.mFirstVerticalBias = ((Flow)paramConstraintWidget).mFirstVerticalBias;
    this.mLastHorizontalBias = ((Flow)paramConstraintWidget).mLastHorizontalBias;
    this.mLastVerticalBias = ((Flow)paramConstraintWidget).mLastVerticalBias;
    this.mHorizontalGap = ((Flow)paramConstraintWidget).mHorizontalGap;
    this.mVerticalGap = ((Flow)paramConstraintWidget).mVerticalGap;
    this.mHorizontalAlign = ((Flow)paramConstraintWidget).mHorizontalAlign;
    this.mVerticalAlign = ((Flow)paramConstraintWidget).mVerticalAlign;
    this.mWrapMode = ((Flow)paramConstraintWidget).mWrapMode;
    this.mMaxElementsWrap = ((Flow)paramConstraintWidget).mMaxElementsWrap;
    this.mOrientation = ((Flow)paramConstraintWidget).mOrientation;
  }
  
  public void measure(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mWidgetsCount > 0 && !measureChildren()) {
      setMeasure(0, 0);
      needsCallbackFromSolver(false);
      return;
    } 
    int i1 = getPaddingLeft();
    int i2 = getPaddingRight();
    int n = getPaddingTop();
    int m = getPaddingBottom();
    int[] arrayOfInt = new int[2];
    int j = paramInt2 - i1 - i2;
    if (this.mOrientation == 1)
      j = paramInt4 - n - m; 
    if (this.mOrientation == 0) {
      if (this.mHorizontalStyle == -1)
        this.mHorizontalStyle = 0; 
      if (this.mVerticalStyle == -1)
        this.mVerticalStyle = 0; 
    } else {
      if (this.mHorizontalStyle == -1)
        this.mHorizontalStyle = 0; 
      if (this.mVerticalStyle == -1)
        this.mVerticalStyle = 0; 
    } 
    ConstraintWidget[] arrayOfConstraintWidget = this.mWidgets;
    int k = 0;
    int i;
    for (i = 0; k < this.mWidgetsCount; i = i3) {
      int i3 = i;
      if (this.mWidgets[k].getVisibility() == 8)
        i3 = i + 1; 
      k++;
    } 
    k = this.mWidgetsCount;
    if (i > 0) {
      arrayOfConstraintWidget = new ConstraintWidget[this.mWidgetsCount - i];
      k = 0;
      for (i = 0; k < this.mWidgetsCount; i = i3) {
        ConstraintWidget constraintWidget = this.mWidgets[k];
        int i3 = i;
        if (constraintWidget.getVisibility() != 8) {
          arrayOfConstraintWidget[i] = constraintWidget;
          i3 = i + 1;
        } 
        k++;
      } 
      k = i;
    } 
    this.mDisplayedWidgets = arrayOfConstraintWidget;
    this.mDisplayedWidgetsCount = k;
    i = this.mWrapMode;
    if (i != 0) {
      if (i != 1) {
        if (i == 2)
          measureAligned(arrayOfConstraintWidget, k, this.mOrientation, j, arrayOfInt); 
      } else {
        measureChainWrap(arrayOfConstraintWidget, k, this.mOrientation, j, arrayOfInt);
      } 
    } else {
      measureNoWrap(arrayOfConstraintWidget, k, this.mOrientation, j, arrayOfInt);
    } 
    boolean bool = true;
    j = arrayOfInt[0] + i1 + i2;
    i = arrayOfInt[1] + n + m;
    if (paramInt1 == 1073741824) {
      paramInt1 = paramInt2;
    } else if (paramInt1 == Integer.MIN_VALUE) {
      paramInt1 = Math.min(j, paramInt2);
    } else if (paramInt1 == 0) {
      paramInt1 = j;
    } else {
      paramInt1 = 0;
    } 
    if (paramInt3 == 1073741824) {
      paramInt2 = paramInt4;
    } else if (paramInt3 == Integer.MIN_VALUE) {
      paramInt2 = Math.min(i, paramInt4);
    } else if (paramInt3 == 0) {
      paramInt2 = i;
    } else {
      paramInt2 = 0;
    } 
    setMeasure(paramInt1, paramInt2);
    setWidth(paramInt1);
    setHeight(paramInt2);
    if (this.mWidgetsCount <= 0)
      bool = false; 
    needsCallbackFromSolver(bool);
  }
  
  public void setFirstHorizontalBias(float paramFloat) {
    this.mFirstHorizontalBias = paramFloat;
  }
  
  public void setFirstHorizontalStyle(int paramInt) {
    this.mFirstHorizontalStyle = paramInt;
  }
  
  public void setFirstVerticalBias(float paramFloat) {
    this.mFirstVerticalBias = paramFloat;
  }
  
  public void setFirstVerticalStyle(int paramInt) {
    this.mFirstVerticalStyle = paramInt;
  }
  
  public void setHorizontalAlign(int paramInt) {
    this.mHorizontalAlign = paramInt;
  }
  
  public void setHorizontalBias(float paramFloat) {
    this.mHorizontalBias = paramFloat;
  }
  
  public void setHorizontalGap(int paramInt) {
    this.mHorizontalGap = paramInt;
  }
  
  public void setHorizontalStyle(int paramInt) {
    this.mHorizontalStyle = paramInt;
  }
  
  public void setLastHorizontalBias(float paramFloat) {
    this.mLastHorizontalBias = paramFloat;
  }
  
  public void setLastHorizontalStyle(int paramInt) {
    this.mLastHorizontalStyle = paramInt;
  }
  
  public void setLastVerticalBias(float paramFloat) {
    this.mLastVerticalBias = paramFloat;
  }
  
  public void setLastVerticalStyle(int paramInt) {
    this.mLastVerticalStyle = paramInt;
  }
  
  public void setMaxElementsWrap(int paramInt) {
    this.mMaxElementsWrap = paramInt;
  }
  
  public void setOrientation(int paramInt) {
    this.mOrientation = paramInt;
  }
  
  public void setVerticalAlign(int paramInt) {
    this.mVerticalAlign = paramInt;
  }
  
  public void setVerticalBias(float paramFloat) {
    this.mVerticalBias = paramFloat;
  }
  
  public void setVerticalGap(int paramInt) {
    this.mVerticalGap = paramInt;
  }
  
  public void setVerticalStyle(int paramInt) {
    this.mVerticalStyle = paramInt;
  }
  
  public void setWrapMode(int paramInt) {
    this.mWrapMode = paramInt;
  }
  
  private class WidgetsList {
    private ConstraintWidget biggest = null;
    
    int biggestDimension = 0;
    
    private ConstraintAnchor mBottom;
    
    private int mCount = 0;
    
    private int mHeight = 0;
    
    private ConstraintAnchor mLeft;
    
    private int mMax = 0;
    
    private int mNbMatchConstraintsWidgets = 0;
    
    private int mOrientation = 0;
    
    private int mPaddingBottom = 0;
    
    private int mPaddingLeft = 0;
    
    private int mPaddingRight = 0;
    
    private int mPaddingTop = 0;
    
    private ConstraintAnchor mRight;
    
    private int mStartIndex = 0;
    
    private ConstraintAnchor mTop;
    
    private int mWidth = 0;
    
    final Flow this$0;
    
    public WidgetsList(int param1Int1, ConstraintAnchor param1ConstraintAnchor1, ConstraintAnchor param1ConstraintAnchor2, ConstraintAnchor param1ConstraintAnchor3, ConstraintAnchor param1ConstraintAnchor4, int param1Int2) {
      this.mOrientation = param1Int1;
      this.mLeft = param1ConstraintAnchor1;
      this.mTop = param1ConstraintAnchor2;
      this.mRight = param1ConstraintAnchor3;
      this.mBottom = param1ConstraintAnchor4;
      this.mPaddingLeft = Flow.this.getPaddingLeft();
      this.mPaddingTop = Flow.this.getPaddingTop();
      this.mPaddingRight = Flow.this.getPaddingRight();
      this.mPaddingBottom = Flow.this.getPaddingBottom();
      this.mMax = param1Int2;
    }
    
    private void recomputeDimensions() {
      this.mWidth = 0;
      this.mHeight = 0;
      this.biggest = null;
      this.biggestDimension = 0;
      int i = this.mCount;
      for (byte b = 0; b < i && this.mStartIndex + b < Flow.this.mDisplayedWidgetsCount; b++) {
        ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + b];
        if (this.mOrientation == 0) {
          int k = constraintWidget.getWidth();
          int j = Flow.this.mHorizontalGap;
          if (constraintWidget.getVisibility() == 8)
            j = 0; 
          this.mWidth += k + j;
          j = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
          if (this.biggest == null || this.biggestDimension < j) {
            this.biggest = constraintWidget;
            this.biggestDimension = j;
            this.mHeight = j;
          } 
        } else {
          int k = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
          int m = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
          int j = Flow.this.mVerticalGap;
          if (constraintWidget.getVisibility() == 8)
            j = 0; 
          this.mHeight += m + j;
          if (this.biggest == null || this.biggestDimension < k) {
            this.biggest = constraintWidget;
            this.biggestDimension = k;
            this.mWidth = k;
          } 
        } 
      } 
    }
    
    public void add(ConstraintWidget param1ConstraintWidget) {
      int i = this.mOrientation;
      int j = 0;
      int k = 0;
      if (i == 0) {
        i = Flow.this.getWidgetWidth(param1ConstraintWidget, this.mMax);
        if (param1ConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          this.mNbMatchConstraintsWidgets++;
          i = 0;
        } 
        j = Flow.this.mHorizontalGap;
        if (param1ConstraintWidget.getVisibility() == 8)
          j = k; 
        this.mWidth += i + j;
        i = Flow.this.getWidgetHeight(param1ConstraintWidget, this.mMax);
        if (this.biggest == null || this.biggestDimension < i) {
          this.biggest = param1ConstraintWidget;
          this.biggestDimension = i;
          this.mHeight = i;
        } 
      } else {
        int m = Flow.this.getWidgetWidth(param1ConstraintWidget, this.mMax);
        i = Flow.this.getWidgetHeight(param1ConstraintWidget, this.mMax);
        if (param1ConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          this.mNbMatchConstraintsWidgets++;
          i = 0;
        } 
        k = Flow.this.mVerticalGap;
        if (param1ConstraintWidget.getVisibility() != 8)
          j = k; 
        this.mHeight += i + j;
        if (this.biggest == null || this.biggestDimension < m) {
          this.biggest = param1ConstraintWidget;
          this.biggestDimension = m;
          this.mWidth = m;
        } 
      } 
      this.mCount++;
    }
    
    public void clear() {
      this.biggestDimension = 0;
      this.biggest = null;
      this.mWidth = 0;
      this.mHeight = 0;
      this.mStartIndex = 0;
      this.mCount = 0;
      this.mNbMatchConstraintsWidgets = 0;
    }
    
    public void createConstraints(boolean param1Boolean1, int param1Int, boolean param1Boolean2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield mCount : I
      //   4: istore #13
      //   6: iconst_0
      //   7: istore #6
      //   9: iload #6
      //   11: iload #13
      //   13: if_icmpge -> 69
      //   16: aload_0
      //   17: getfield mStartIndex : I
      //   20: iload #6
      //   22: iadd
      //   23: aload_0
      //   24: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   27: invokestatic access$400 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   30: if_icmplt -> 36
      //   33: goto -> 69
      //   36: aload_0
      //   37: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   40: invokestatic access$500 : (Landroidx/constraintlayout/solver/widgets/Flow;)[Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   43: aload_0
      //   44: getfield mStartIndex : I
      //   47: iload #6
      //   49: iadd
      //   50: aaload
      //   51: astore #14
      //   53: aload #14
      //   55: ifnull -> 63
      //   58: aload #14
      //   60: invokevirtual resetAnchors : ()V
      //   63: iinc #6, 1
      //   66: goto -> 9
      //   69: iload #13
      //   71: ifeq -> 1672
      //   74: aload_0
      //   75: getfield biggest : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   78: ifnonnull -> 84
      //   81: goto -> 1672
      //   84: iload_3
      //   85: ifeq -> 98
      //   88: iload_2
      //   89: ifne -> 98
      //   92: iconst_1
      //   93: istore #9
      //   95: goto -> 101
      //   98: iconst_0
      //   99: istore #9
      //   101: iconst_0
      //   102: istore #6
      //   104: iconst_m1
      //   105: istore #7
      //   107: iconst_m1
      //   108: istore #8
      //   110: iload #6
      //   112: iload #13
      //   114: if_icmpge -> 222
      //   117: iload_1
      //   118: ifeq -> 133
      //   121: iload #13
      //   123: iconst_1
      //   124: isub
      //   125: iload #6
      //   127: isub
      //   128: istore #12
      //   130: goto -> 137
      //   133: iload #6
      //   135: istore #12
      //   137: aload_0
      //   138: getfield mStartIndex : I
      //   141: iload #12
      //   143: iadd
      //   144: aload_0
      //   145: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   148: invokestatic access$400 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   151: if_icmplt -> 157
      //   154: goto -> 222
      //   157: iload #7
      //   159: istore #11
      //   161: iload #8
      //   163: istore #10
      //   165: aload_0
      //   166: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   169: invokestatic access$500 : (Landroidx/constraintlayout/solver/widgets/Flow;)[Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   172: aload_0
      //   173: getfield mStartIndex : I
      //   176: iload #12
      //   178: iadd
      //   179: aaload
      //   180: invokevirtual getVisibility : ()I
      //   183: ifne -> 208
      //   186: iload #7
      //   188: istore #8
      //   190: iload #7
      //   192: iconst_m1
      //   193: if_icmpne -> 200
      //   196: iload #6
      //   198: istore #8
      //   200: iload #6
      //   202: istore #10
      //   204: iload #8
      //   206: istore #11
      //   208: iinc #6, 1
      //   211: iload #11
      //   213: istore #7
      //   215: iload #10
      //   217: istore #8
      //   219: goto -> 110
      //   222: aconst_null
      //   223: astore #14
      //   225: aconst_null
      //   226: astore #15
      //   228: aload_0
      //   229: getfield mOrientation : I
      //   232: ifne -> 957
      //   235: aload_0
      //   236: getfield biggest : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   239: astore #16
      //   241: aload #16
      //   243: aload_0
      //   244: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   247: invokestatic access$600 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   250: invokevirtual setVerticalChainStyle : (I)V
      //   253: aload_0
      //   254: getfield mPaddingTop : I
      //   257: istore #10
      //   259: iload #10
      //   261: istore #6
      //   263: iload_2
      //   264: ifle -> 279
      //   267: iload #10
      //   269: aload_0
      //   270: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   273: invokestatic access$100 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   276: iadd
      //   277: istore #6
      //   279: aload #16
      //   281: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   284: aload_0
      //   285: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   288: iload #6
      //   290: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   293: pop
      //   294: iload_3
      //   295: ifeq -> 315
      //   298: aload #16
      //   300: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   303: aload_0
      //   304: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   307: aload_0
      //   308: getfield mPaddingBottom : I
      //   311: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   314: pop
      //   315: iload_2
      //   316: ifle -> 339
      //   319: aload_0
      //   320: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   323: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   326: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   329: aload #16
      //   331: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   334: iconst_0
      //   335: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   338: pop
      //   339: aload_0
      //   340: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   343: invokestatic access$700 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   346: iconst_3
      //   347: if_icmpne -> 438
      //   350: aload #16
      //   352: invokevirtual hasBaseline : ()Z
      //   355: ifne -> 438
      //   358: iconst_0
      //   359: istore_2
      //   360: iload_2
      //   361: iload #13
      //   363: if_icmpge -> 438
      //   366: iload_1
      //   367: ifeq -> 381
      //   370: iload #13
      //   372: iconst_1
      //   373: isub
      //   374: iload_2
      //   375: isub
      //   376: istore #6
      //   378: goto -> 384
      //   381: iload_2
      //   382: istore #6
      //   384: aload_0
      //   385: getfield mStartIndex : I
      //   388: iload #6
      //   390: iadd
      //   391: aload_0
      //   392: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   395: invokestatic access$400 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   398: if_icmplt -> 404
      //   401: goto -> 438
      //   404: aload_0
      //   405: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   408: invokestatic access$500 : (Landroidx/constraintlayout/solver/widgets/Flow;)[Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   411: aload_0
      //   412: getfield mStartIndex : I
      //   415: iload #6
      //   417: iadd
      //   418: aaload
      //   419: astore #14
      //   421: aload #14
      //   423: invokevirtual hasBaseline : ()Z
      //   426: ifeq -> 432
      //   429: goto -> 442
      //   432: iinc #2, 1
      //   435: goto -> 360
      //   438: aload #16
      //   440: astore #14
      //   442: iconst_0
      //   443: istore_2
      //   444: iload_2
      //   445: iload #13
      //   447: if_icmpge -> 1672
      //   450: iload_1
      //   451: ifeq -> 465
      //   454: iload #13
      //   456: iconst_1
      //   457: isub
      //   458: iload_2
      //   459: isub
      //   460: istore #6
      //   462: goto -> 468
      //   465: iload_2
      //   466: istore #6
      //   468: aload_0
      //   469: getfield mStartIndex : I
      //   472: iload #6
      //   474: iadd
      //   475: aload_0
      //   476: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   479: invokestatic access$400 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   482: if_icmplt -> 488
      //   485: goto -> 1672
      //   488: aload_0
      //   489: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   492: invokestatic access$500 : (Landroidx/constraintlayout/solver/widgets/Flow;)[Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   495: aload_0
      //   496: getfield mStartIndex : I
      //   499: iload #6
      //   501: iadd
      //   502: aaload
      //   503: astore #17
      //   505: iload_2
      //   506: ifne -> 527
      //   509: aload #17
      //   511: aload #17
      //   513: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   516: aload_0
      //   517: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   520: aload_0
      //   521: getfield mPaddingLeft : I
      //   524: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)V
      //   527: iload #6
      //   529: ifne -> 652
      //   532: aload_0
      //   533: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   536: invokestatic access$800 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   539: istore #10
      //   541: aload_0
      //   542: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   545: invokestatic access$900 : (Landroidx/constraintlayout/solver/widgets/Flow;)F
      //   548: fstore #5
      //   550: aload_0
      //   551: getfield mStartIndex : I
      //   554: ifne -> 589
      //   557: aload_0
      //   558: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   561: invokestatic access$1000 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   564: iconst_m1
      //   565: if_icmpeq -> 589
      //   568: aload_0
      //   569: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   572: invokestatic access$1000 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   575: istore #6
      //   577: aload_0
      //   578: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   581: invokestatic access$1100 : (Landroidx/constraintlayout/solver/widgets/Flow;)F
      //   584: fstore #4
      //   586: goto -> 638
      //   589: iload #10
      //   591: istore #6
      //   593: fload #5
      //   595: fstore #4
      //   597: iload_3
      //   598: ifeq -> 638
      //   601: iload #10
      //   603: istore #6
      //   605: fload #5
      //   607: fstore #4
      //   609: aload_0
      //   610: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   613: invokestatic access$1200 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   616: iconst_m1
      //   617: if_icmpeq -> 638
      //   620: aload_0
      //   621: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   624: invokestatic access$1200 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   627: istore #6
      //   629: aload_0
      //   630: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   633: invokestatic access$1300 : (Landroidx/constraintlayout/solver/widgets/Flow;)F
      //   636: fstore #4
      //   638: aload #17
      //   640: iload #6
      //   642: invokevirtual setHorizontalChainStyle : (I)V
      //   645: aload #17
      //   647: fload #4
      //   649: invokevirtual setHorizontalBiasPercent : (F)V
      //   652: iload_2
      //   653: iload #13
      //   655: iconst_1
      //   656: isub
      //   657: if_icmpne -> 678
      //   660: aload #17
      //   662: aload #17
      //   664: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   667: aload_0
      //   668: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   671: aload_0
      //   672: getfield mPaddingRight : I
      //   675: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)V
      //   678: aload #15
      //   680: ifnull -> 757
      //   683: aload #17
      //   685: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   688: aload #15
      //   690: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   693: aload_0
      //   694: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   697: invokestatic access$000 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   700: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   703: pop
      //   704: iload_2
      //   705: iload #7
      //   707: if_icmpne -> 722
      //   710: aload #17
      //   712: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   715: aload_0
      //   716: getfield mPaddingLeft : I
      //   719: invokevirtual setGoneMargin : (I)V
      //   722: aload #15
      //   724: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   727: aload #17
      //   729: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   732: iconst_0
      //   733: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   736: pop
      //   737: iload_2
      //   738: iload #8
      //   740: iconst_1
      //   741: iadd
      //   742: if_icmpne -> 757
      //   745: aload #15
      //   747: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   750: aload_0
      //   751: getfield mPaddingRight : I
      //   754: invokevirtual setGoneMargin : (I)V
      //   757: aload #17
      //   759: aload #16
      //   761: if_acmpeq -> 947
      //   764: aload_0
      //   765: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   768: invokestatic access$700 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   771: iconst_3
      //   772: if_icmpne -> 816
      //   775: aload #14
      //   777: invokevirtual hasBaseline : ()Z
      //   780: ifeq -> 816
      //   783: aload #17
      //   785: aload #14
      //   787: if_acmpeq -> 816
      //   790: aload #17
      //   792: invokevirtual hasBaseline : ()Z
      //   795: ifeq -> 816
      //   798: aload #17
      //   800: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   803: aload #14
      //   805: getfield mBaseline : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   808: iconst_0
      //   809: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   812: pop
      //   813: goto -> 947
      //   816: aload_0
      //   817: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   820: invokestatic access$700 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   823: istore #6
      //   825: iload #6
      //   827: ifeq -> 929
      //   830: iload #6
      //   832: iconst_1
      //   833: if_icmpeq -> 911
      //   836: iload #9
      //   838: ifeq -> 878
      //   841: aload #17
      //   843: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   846: aload_0
      //   847: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   850: aload_0
      //   851: getfield mPaddingTop : I
      //   854: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   857: pop
      //   858: aload #17
      //   860: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   863: aload_0
      //   864: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   867: aload_0
      //   868: getfield mPaddingBottom : I
      //   871: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   874: pop
      //   875: goto -> 947
      //   878: aload #17
      //   880: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   883: aload #16
      //   885: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   888: iconst_0
      //   889: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   892: pop
      //   893: aload #17
      //   895: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   898: aload #16
      //   900: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   903: iconst_0
      //   904: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   907: pop
      //   908: goto -> 947
      //   911: aload #17
      //   913: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   916: aload #16
      //   918: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   921: iconst_0
      //   922: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   925: pop
      //   926: goto -> 947
      //   929: aload #17
      //   931: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   934: aload #16
      //   936: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   939: iconst_0
      //   940: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   943: pop
      //   944: goto -> 947
      //   947: iinc #2, 1
      //   950: aload #17
      //   952: astore #15
      //   954: goto -> 444
      //   957: aload_0
      //   958: getfield biggest : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   961: astore #16
      //   963: aload #16
      //   965: aload_0
      //   966: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   969: invokestatic access$800 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   972: invokevirtual setHorizontalChainStyle : (I)V
      //   975: aload_0
      //   976: getfield mPaddingLeft : I
      //   979: istore #10
      //   981: iload #10
      //   983: istore #6
      //   985: iload_2
      //   986: ifle -> 1001
      //   989: iload #10
      //   991: aload_0
      //   992: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   995: invokestatic access$000 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   998: iadd
      //   999: istore #6
      //   1001: iload_1
      //   1002: ifeq -> 1068
      //   1005: aload #16
      //   1007: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1010: aload_0
      //   1011: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1014: iload #6
      //   1016: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1019: pop
      //   1020: iload_3
      //   1021: ifeq -> 1041
      //   1024: aload #16
      //   1026: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1029: aload_0
      //   1030: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1033: aload_0
      //   1034: getfield mPaddingRight : I
      //   1037: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1040: pop
      //   1041: iload_2
      //   1042: ifle -> 1128
      //   1045: aload_0
      //   1046: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1049: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   1052: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1055: aload #16
      //   1057: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1060: iconst_0
      //   1061: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1064: pop
      //   1065: goto -> 1128
      //   1068: aload #16
      //   1070: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1073: aload_0
      //   1074: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1077: iload #6
      //   1079: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1082: pop
      //   1083: iload_3
      //   1084: ifeq -> 1104
      //   1087: aload #16
      //   1089: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1092: aload_0
      //   1093: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1096: aload_0
      //   1097: getfield mPaddingRight : I
      //   1100: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1103: pop
      //   1104: iload_2
      //   1105: ifle -> 1128
      //   1108: aload_0
      //   1109: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1112: getfield mOwner : Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   1115: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1118: aload #16
      //   1120: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1123: iconst_0
      //   1124: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1127: pop
      //   1128: iconst_0
      //   1129: istore #6
      //   1131: iload #6
      //   1133: iload #13
      //   1135: if_icmpge -> 1672
      //   1138: aload_0
      //   1139: getfield mStartIndex : I
      //   1142: iload #6
      //   1144: iadd
      //   1145: aload_0
      //   1146: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1149: invokestatic access$400 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1152: if_icmplt -> 1158
      //   1155: goto -> 1672
      //   1158: aload_0
      //   1159: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1162: invokestatic access$500 : (Landroidx/constraintlayout/solver/widgets/Flow;)[Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
      //   1165: aload_0
      //   1166: getfield mStartIndex : I
      //   1169: iload #6
      //   1171: iadd
      //   1172: aaload
      //   1173: astore #15
      //   1175: iload #6
      //   1177: ifne -> 1313
      //   1180: aload #15
      //   1182: aload #15
      //   1184: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1187: aload_0
      //   1188: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1191: aload_0
      //   1192: getfield mPaddingTop : I
      //   1195: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)V
      //   1198: aload_0
      //   1199: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1202: invokestatic access$600 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1205: istore #10
      //   1207: aload_0
      //   1208: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1211: invokestatic access$1400 : (Landroidx/constraintlayout/solver/widgets/Flow;)F
      //   1214: fstore #5
      //   1216: aload_0
      //   1217: getfield mStartIndex : I
      //   1220: ifne -> 1254
      //   1223: aload_0
      //   1224: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1227: invokestatic access$1500 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1230: iconst_m1
      //   1231: if_icmpeq -> 1254
      //   1234: aload_0
      //   1235: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1238: invokestatic access$1500 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1241: istore_2
      //   1242: aload_0
      //   1243: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1246: invokestatic access$1600 : (Landroidx/constraintlayout/solver/widgets/Flow;)F
      //   1249: fstore #4
      //   1251: goto -> 1300
      //   1254: iload #10
      //   1256: istore_2
      //   1257: fload #5
      //   1259: fstore #4
      //   1261: iload_3
      //   1262: ifeq -> 1300
      //   1265: iload #10
      //   1267: istore_2
      //   1268: fload #5
      //   1270: fstore #4
      //   1272: aload_0
      //   1273: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1276: invokestatic access$1700 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1279: iconst_m1
      //   1280: if_icmpeq -> 1300
      //   1283: aload_0
      //   1284: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1287: invokestatic access$1700 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1290: istore_2
      //   1291: aload_0
      //   1292: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1295: invokestatic access$1800 : (Landroidx/constraintlayout/solver/widgets/Flow;)F
      //   1298: fstore #4
      //   1300: aload #15
      //   1302: iload_2
      //   1303: invokevirtual setVerticalChainStyle : (I)V
      //   1306: aload #15
      //   1308: fload #4
      //   1310: invokevirtual setVerticalBiasPercent : (F)V
      //   1313: iload #6
      //   1315: iload #13
      //   1317: iconst_1
      //   1318: isub
      //   1319: if_icmpne -> 1340
      //   1322: aload #15
      //   1324: aload #15
      //   1326: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1329: aload_0
      //   1330: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1333: aload_0
      //   1334: getfield mPaddingBottom : I
      //   1337: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)V
      //   1340: aload #14
      //   1342: ifnull -> 1421
      //   1345: aload #15
      //   1347: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1350: aload #14
      //   1352: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1355: aload_0
      //   1356: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1359: invokestatic access$100 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1362: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1365: pop
      //   1366: iload #6
      //   1368: iload #7
      //   1370: if_icmpne -> 1385
      //   1373: aload #15
      //   1375: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1378: aload_0
      //   1379: getfield mPaddingTop : I
      //   1382: invokevirtual setGoneMargin : (I)V
      //   1385: aload #14
      //   1387: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1390: aload #15
      //   1392: getfield mTop : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1395: iconst_0
      //   1396: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1399: pop
      //   1400: iload #6
      //   1402: iload #8
      //   1404: iconst_1
      //   1405: iadd
      //   1406: if_icmpne -> 1421
      //   1409: aload #14
      //   1411: getfield mBottom : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1414: aload_0
      //   1415: getfield mPaddingBottom : I
      //   1418: invokevirtual setGoneMargin : (I)V
      //   1421: aload #15
      //   1423: aload #16
      //   1425: if_acmpeq -> 1662
      //   1428: iload_1
      //   1429: ifeq -> 1526
      //   1432: aload_0
      //   1433: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1436: invokestatic access$1900 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1439: istore_2
      //   1440: iload_2
      //   1441: ifeq -> 1508
      //   1444: iload_2
      //   1445: iconst_1
      //   1446: if_icmpeq -> 1490
      //   1449: iload_2
      //   1450: iconst_2
      //   1451: if_icmpeq -> 1457
      //   1454: goto -> 1662
      //   1457: aload #15
      //   1459: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1462: aload #16
      //   1464: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1467: iconst_0
      //   1468: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1471: pop
      //   1472: aload #15
      //   1474: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1477: aload #16
      //   1479: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1482: iconst_0
      //   1483: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1486: pop
      //   1487: goto -> 1662
      //   1490: aload #15
      //   1492: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1495: aload #16
      //   1497: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1500: iconst_0
      //   1501: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1504: pop
      //   1505: goto -> 1662
      //   1508: aload #15
      //   1510: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1513: aload #16
      //   1515: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1518: iconst_0
      //   1519: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1522: pop
      //   1523: goto -> 1662
      //   1526: aload_0
      //   1527: getfield this$0 : Landroidx/constraintlayout/solver/widgets/Flow;
      //   1530: invokestatic access$1900 : (Landroidx/constraintlayout/solver/widgets/Flow;)I
      //   1533: istore_2
      //   1534: iload_2
      //   1535: ifeq -> 1644
      //   1538: iload_2
      //   1539: iconst_1
      //   1540: if_icmpeq -> 1626
      //   1543: iload_2
      //   1544: iconst_2
      //   1545: if_icmpeq -> 1551
      //   1548: goto -> 1662
      //   1551: iload #9
      //   1553: ifeq -> 1593
      //   1556: aload #15
      //   1558: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1561: aload_0
      //   1562: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1565: aload_0
      //   1566: getfield mPaddingLeft : I
      //   1569: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1572: pop
      //   1573: aload #15
      //   1575: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1578: aload_0
      //   1579: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1582: aload_0
      //   1583: getfield mPaddingRight : I
      //   1586: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1589: pop
      //   1590: goto -> 1662
      //   1593: aload #15
      //   1595: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1598: aload #16
      //   1600: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1603: iconst_0
      //   1604: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1607: pop
      //   1608: aload #15
      //   1610: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1613: aload #16
      //   1615: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1618: iconst_0
      //   1619: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1622: pop
      //   1623: goto -> 1662
      //   1626: aload #15
      //   1628: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1631: aload #16
      //   1633: getfield mRight : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1636: iconst_0
      //   1637: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1640: pop
      //   1641: goto -> 1662
      //   1644: aload #15
      //   1646: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1649: aload #16
      //   1651: getfield mLeft : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
      //   1654: iconst_0
      //   1655: invokevirtual connect : (Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;I)Z
      //   1658: pop
      //   1659: goto -> 1662
      //   1662: iinc #6, 1
      //   1665: aload #15
      //   1667: astore #14
      //   1669: goto -> 1131
      //   1672: return
    }
    
    public int getHeight() {
      return (this.mOrientation == 1) ? (this.mHeight - Flow.this.mVerticalGap) : this.mHeight;
    }
    
    public int getWidth() {
      return (this.mOrientation == 0) ? (this.mWidth - Flow.this.mHorizontalGap) : this.mWidth;
    }
    
    public void measureMatchConstraints(int param1Int) {
      int j = this.mNbMatchConstraintsWidgets;
      if (j == 0)
        return; 
      int i = this.mCount;
      j = param1Int / j;
      for (param1Int = 0; param1Int < i && this.mStartIndex + param1Int < Flow.this.mDisplayedWidgetsCount; param1Int++) {
        ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + param1Int];
        if (this.mOrientation == 0) {
          if (constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0)
            Flow.this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, j, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight()); 
        } else if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
          Flow.this.measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, j);
        } 
      } 
      recomputeDimensions();
    }
    
    public void setStartIndex(int param1Int) {
      this.mStartIndex = param1Int;
    }
    
    public void setup(int param1Int1, ConstraintAnchor param1ConstraintAnchor1, ConstraintAnchor param1ConstraintAnchor2, ConstraintAnchor param1ConstraintAnchor3, ConstraintAnchor param1ConstraintAnchor4, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
      this.mOrientation = param1Int1;
      this.mLeft = param1ConstraintAnchor1;
      this.mTop = param1ConstraintAnchor2;
      this.mRight = param1ConstraintAnchor3;
      this.mBottom = param1ConstraintAnchor4;
      this.mPaddingLeft = param1Int2;
      this.mPaddingTop = param1Int3;
      this.mPaddingRight = param1Int4;
      this.mPaddingBottom = param1Int5;
      this.mMax = param1Int6;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\Flow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */