package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
  static final int GENERATED_ITEM_PADDING = 4;
  
  static final int MIN_CELL_SIZE = 56;
  
  private static final String TAG = "ActionMenuView";
  
  private MenuPresenter.Callback mActionMenuPresenterCallback;
  
  private boolean mFormatItems;
  
  private int mFormatItemsWidth;
  
  private int mGeneratedItemPadding;
  
  private MenuBuilder mMenu;
  
  MenuBuilder.Callback mMenuBuilderCallback;
  
  private int mMinCellSize;
  
  OnMenuItemClickListener mOnMenuItemClickListener;
  
  private Context mPopupContext;
  
  private int mPopupTheme;
  
  private ActionMenuPresenter mPresenter;
  
  private boolean mReserveOverflow;
  
  public ActionMenuView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ActionMenuView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    setBaselineAligned(false);
    float f = (paramContext.getResources().getDisplayMetrics()).density;
    this.mMinCellSize = (int)(56.0F * f);
    this.mGeneratedItemPadding = (int)(f * 4.0F);
    this.mPopupContext = paramContext;
    this.mPopupTheme = 0;
  }
  
  static int measureChildForCells(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    ActionMenuItemView actionMenuItemView;
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt3) - paramInt4, View.MeasureSpec.getMode(paramInt3));
    if (paramView instanceof ActionMenuItemView) {
      actionMenuItemView = (ActionMenuItemView)paramView;
    } else {
      actionMenuItemView = null;
    } 
    boolean bool = true;
    if (actionMenuItemView != null && actionMenuItemView.hasText()) {
      paramInt3 = 1;
    } else {
      paramInt3 = 0;
    } 
    paramInt4 = 2;
    if (paramInt2 > 0 && (paramInt3 == 0 || paramInt2 >= 2)) {
      paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt2 * paramInt1, -2147483648), i);
      int k = paramView.getMeasuredWidth();
      int j = k / paramInt1;
      paramInt2 = j;
      if (k % paramInt1 != 0)
        paramInt2 = j + 1; 
      if (paramInt3 != 0 && paramInt2 < 2)
        paramInt2 = paramInt4; 
    } else {
      paramInt2 = 0;
    } 
    if (layoutParams.isOverflowButton || paramInt3 == 0)
      bool = false; 
    layoutParams.expandable = bool;
    layoutParams.cellsUsed = paramInt2;
    paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1 * paramInt2, 1073741824), i);
    return paramInt2;
  }
  
  private void onMeasureExactFormat(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_2
    //   1: invokestatic getMode : (I)I
    //   4: istore #12
    //   6: iload_1
    //   7: invokestatic getSize : (I)I
    //   10: istore #5
    //   12: iload_2
    //   13: invokestatic getSize : (I)I
    //   16: istore #6
    //   18: aload_0
    //   19: invokevirtual getPaddingLeft : ()I
    //   22: istore_1
    //   23: aload_0
    //   24: invokevirtual getPaddingRight : ()I
    //   27: istore #7
    //   29: aload_0
    //   30: invokevirtual getPaddingTop : ()I
    //   33: aload_0
    //   34: invokevirtual getPaddingBottom : ()I
    //   37: iadd
    //   38: istore #13
    //   40: iload_2
    //   41: iload #13
    //   43: bipush #-2
    //   45: invokestatic getChildMeasureSpec : (III)I
    //   48: istore #19
    //   50: iload #5
    //   52: iload_1
    //   53: iload #7
    //   55: iadd
    //   56: isub
    //   57: istore #14
    //   59: aload_0
    //   60: getfield mMinCellSize : I
    //   63: istore_2
    //   64: iload #14
    //   66: iload_2
    //   67: idiv
    //   68: istore_1
    //   69: iload_1
    //   70: ifne -> 81
    //   73: aload_0
    //   74: iload #14
    //   76: iconst_0
    //   77: invokevirtual setMeasuredDimension : (II)V
    //   80: return
    //   81: iload_2
    //   82: iload #14
    //   84: iload_2
    //   85: irem
    //   86: iload_1
    //   87: idiv
    //   88: iadd
    //   89: istore #21
    //   91: aload_0
    //   92: invokevirtual getChildCount : ()I
    //   95: istore #20
    //   97: iconst_0
    //   98: istore #5
    //   100: iconst_0
    //   101: istore #9
    //   103: iconst_0
    //   104: istore #7
    //   106: iconst_0
    //   107: istore #11
    //   109: iconst_0
    //   110: istore #10
    //   112: iconst_0
    //   113: istore #8
    //   115: lconst_0
    //   116: lstore #22
    //   118: iload #9
    //   120: iload #20
    //   122: if_icmpge -> 369
    //   125: aload_0
    //   126: iload #9
    //   128: invokevirtual getChildAt : (I)Landroid/view/View;
    //   131: astore #31
    //   133: aload #31
    //   135: invokevirtual getVisibility : ()I
    //   138: bipush #8
    //   140: if_icmpne -> 149
    //   143: iload #8
    //   145: istore_2
    //   146: goto -> 360
    //   149: aload #31
    //   151: instanceof androidx/appcompat/view/menu/ActionMenuItemView
    //   154: istore #30
    //   156: iinc #11, 1
    //   159: iload #30
    //   161: ifeq -> 181
    //   164: aload_0
    //   165: getfield mGeneratedItemPadding : I
    //   168: istore_2
    //   169: aload #31
    //   171: iload_2
    //   172: iconst_0
    //   173: iload_2
    //   174: iconst_0
    //   175: invokevirtual setPadding : (IIII)V
    //   178: goto -> 181
    //   181: aload #31
    //   183: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   186: checkcast androidx/appcompat/widget/ActionMenuView$LayoutParams
    //   189: astore #32
    //   191: aload #32
    //   193: iconst_0
    //   194: putfield expanded : Z
    //   197: aload #32
    //   199: iconst_0
    //   200: putfield extraPixels : I
    //   203: aload #32
    //   205: iconst_0
    //   206: putfield cellsUsed : I
    //   209: aload #32
    //   211: iconst_0
    //   212: putfield expandable : Z
    //   215: aload #32
    //   217: iconst_0
    //   218: putfield leftMargin : I
    //   221: aload #32
    //   223: iconst_0
    //   224: putfield rightMargin : I
    //   227: iload #30
    //   229: ifeq -> 249
    //   232: aload #31
    //   234: checkcast androidx/appcompat/view/menu/ActionMenuItemView
    //   237: invokevirtual hasText : ()Z
    //   240: ifeq -> 249
    //   243: iconst_1
    //   244: istore #30
    //   246: goto -> 252
    //   249: iconst_0
    //   250: istore #30
    //   252: aload #32
    //   254: iload #30
    //   256: putfield preventEdgeOffset : Z
    //   259: aload #32
    //   261: getfield isOverflowButton : Z
    //   264: ifeq -> 272
    //   267: iconst_1
    //   268: istore_2
    //   269: goto -> 274
    //   272: iload_1
    //   273: istore_2
    //   274: aload #31
    //   276: iload #21
    //   278: iload_2
    //   279: iload #19
    //   281: iload #13
    //   283: invokestatic measureChildForCells : (Landroid/view/View;IIII)I
    //   286: istore #15
    //   288: iload #10
    //   290: iload #15
    //   292: invokestatic max : (II)I
    //   295: istore #10
    //   297: iload #8
    //   299: istore_2
    //   300: aload #32
    //   302: getfield expandable : Z
    //   305: ifeq -> 313
    //   308: iload #8
    //   310: iconst_1
    //   311: iadd
    //   312: istore_2
    //   313: aload #32
    //   315: getfield isOverflowButton : Z
    //   318: ifeq -> 324
    //   321: iconst_1
    //   322: istore #7
    //   324: iload_1
    //   325: iload #15
    //   327: isub
    //   328: istore_1
    //   329: iload #5
    //   331: aload #31
    //   333: invokevirtual getMeasuredHeight : ()I
    //   336: invokestatic max : (II)I
    //   339: istore #5
    //   341: iload #15
    //   343: iconst_1
    //   344: if_icmpne -> 360
    //   347: lload #22
    //   349: iconst_1
    //   350: iload #9
    //   352: ishl
    //   353: i2l
    //   354: lor
    //   355: lstore #22
    //   357: goto -> 360
    //   360: iinc #9, 1
    //   363: iload_2
    //   364: istore #8
    //   366: goto -> 118
    //   369: iload #7
    //   371: ifeq -> 386
    //   374: iload #11
    //   376: iconst_2
    //   377: if_icmpne -> 386
    //   380: iconst_1
    //   381: istore #9
    //   383: goto -> 389
    //   386: iconst_0
    //   387: istore #9
    //   389: iconst_0
    //   390: istore_2
    //   391: iload_1
    //   392: istore #13
    //   394: iload #9
    //   396: istore #15
    //   398: iload #14
    //   400: istore #9
    //   402: iload #8
    //   404: ifle -> 722
    //   407: iload #13
    //   409: ifle -> 722
    //   412: iconst_0
    //   413: istore #17
    //   415: iconst_0
    //   416: istore #16
    //   418: ldc 2147483647
    //   420: istore #14
    //   422: lconst_0
    //   423: lstore #26
    //   425: iload #16
    //   427: iload #20
    //   429: if_icmpge -> 552
    //   432: aload_0
    //   433: iload #16
    //   435: invokevirtual getChildAt : (I)Landroid/view/View;
    //   438: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   441: checkcast androidx/appcompat/widget/ActionMenuView$LayoutParams
    //   444: astore #31
    //   446: aload #31
    //   448: getfield expandable : Z
    //   451: ifne -> 468
    //   454: iload #17
    //   456: istore_1
    //   457: iload #14
    //   459: istore #18
    //   461: lload #26
    //   463: lstore #24
    //   465: goto -> 535
    //   468: aload #31
    //   470: getfield cellsUsed : I
    //   473: iload #14
    //   475: if_icmpge -> 496
    //   478: aload #31
    //   480: getfield cellsUsed : I
    //   483: istore #18
    //   485: lconst_1
    //   486: iload #16
    //   488: lshl
    //   489: lstore #24
    //   491: iconst_1
    //   492: istore_1
    //   493: goto -> 535
    //   496: iload #17
    //   498: istore_1
    //   499: iload #14
    //   501: istore #18
    //   503: lload #26
    //   505: lstore #24
    //   507: aload #31
    //   509: getfield cellsUsed : I
    //   512: iload #14
    //   514: if_icmpne -> 535
    //   517: iload #17
    //   519: iconst_1
    //   520: iadd
    //   521: istore_1
    //   522: lload #26
    //   524: lconst_1
    //   525: iload #16
    //   527: lshl
    //   528: lor
    //   529: lstore #24
    //   531: iload #14
    //   533: istore #18
    //   535: iinc #16, 1
    //   538: iload_1
    //   539: istore #17
    //   541: iload #18
    //   543: istore #14
    //   545: lload #24
    //   547: lstore #26
    //   549: goto -> 425
    //   552: iload_2
    //   553: istore_1
    //   554: iload #5
    //   556: istore_2
    //   557: lload #22
    //   559: lload #26
    //   561: lor
    //   562: lstore #22
    //   564: iload #17
    //   566: iload #13
    //   568: if_icmple -> 574
    //   571: goto -> 727
    //   574: iconst_0
    //   575: istore_1
    //   576: iload_1
    //   577: iload #20
    //   579: if_icmpge -> 714
    //   582: aload_0
    //   583: iload_1
    //   584: invokevirtual getChildAt : (I)Landroid/view/View;
    //   587: astore #32
    //   589: aload #32
    //   591: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   594: checkcast androidx/appcompat/widget/ActionMenuView$LayoutParams
    //   597: astore #31
    //   599: iconst_1
    //   600: iload_1
    //   601: ishl
    //   602: i2l
    //   603: lstore #28
    //   605: lload #26
    //   607: lload #28
    //   609: land
    //   610: lconst_0
    //   611: lcmp
    //   612: ifne -> 645
    //   615: lload #22
    //   617: lstore #24
    //   619: aload #31
    //   621: getfield cellsUsed : I
    //   624: iload #14
    //   626: iconst_1
    //   627: iadd
    //   628: if_icmpne -> 638
    //   631: lload #22
    //   633: lload #28
    //   635: lor
    //   636: lstore #24
    //   638: lload #24
    //   640: lstore #22
    //   642: goto -> 708
    //   645: iload #15
    //   647: ifeq -> 687
    //   650: aload #31
    //   652: getfield preventEdgeOffset : Z
    //   655: ifeq -> 687
    //   658: iload #13
    //   660: iconst_1
    //   661: if_icmpne -> 687
    //   664: aload_0
    //   665: getfield mGeneratedItemPadding : I
    //   668: istore #5
    //   670: aload #32
    //   672: iload #5
    //   674: iload #21
    //   676: iadd
    //   677: iconst_0
    //   678: iload #5
    //   680: iconst_0
    //   681: invokevirtual setPadding : (IIII)V
    //   684: goto -> 687
    //   687: aload #31
    //   689: aload #31
    //   691: getfield cellsUsed : I
    //   694: iconst_1
    //   695: iadd
    //   696: putfield cellsUsed : I
    //   699: aload #31
    //   701: iconst_1
    //   702: putfield expanded : Z
    //   705: iinc #13, -1
    //   708: iinc #1, 1
    //   711: goto -> 576
    //   714: iload_2
    //   715: istore #5
    //   717: iconst_1
    //   718: istore_2
    //   719: goto -> 402
    //   722: iload_2
    //   723: istore_1
    //   724: iload #5
    //   726: istore_2
    //   727: iload #7
    //   729: ifne -> 744
    //   732: iload #11
    //   734: iconst_1
    //   735: if_icmpne -> 744
    //   738: iconst_1
    //   739: istore #5
    //   741: goto -> 747
    //   744: iconst_0
    //   745: istore #5
    //   747: iload #13
    //   749: ifle -> 1091
    //   752: lload #22
    //   754: lconst_0
    //   755: lcmp
    //   756: ifeq -> 1091
    //   759: iload #13
    //   761: iload #11
    //   763: iconst_1
    //   764: isub
    //   765: if_icmplt -> 779
    //   768: iload #5
    //   770: ifne -> 779
    //   773: iload #10
    //   775: iconst_1
    //   776: if_icmple -> 1091
    //   779: lload #22
    //   781: invokestatic bitCount : (J)I
    //   784: i2f
    //   785: fstore #4
    //   787: iload #5
    //   789: ifne -> 882
    //   792: fload #4
    //   794: fstore_3
    //   795: lload #22
    //   797: lconst_1
    //   798: land
    //   799: lconst_0
    //   800: lcmp
    //   801: ifeq -> 830
    //   804: fload #4
    //   806: fstore_3
    //   807: aload_0
    //   808: iconst_0
    //   809: invokevirtual getChildAt : (I)Landroid/view/View;
    //   812: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   815: checkcast androidx/appcompat/widget/ActionMenuView$LayoutParams
    //   818: getfield preventEdgeOffset : Z
    //   821: ifne -> 830
    //   824: fload #4
    //   826: ldc 0.5
    //   828: fsub
    //   829: fstore_3
    //   830: iload #20
    //   832: iconst_1
    //   833: isub
    //   834: istore #5
    //   836: fload_3
    //   837: fstore #4
    //   839: lload #22
    //   841: iconst_1
    //   842: iload #5
    //   844: ishl
    //   845: i2l
    //   846: land
    //   847: lconst_0
    //   848: lcmp
    //   849: ifeq -> 882
    //   852: fload_3
    //   853: fstore #4
    //   855: aload_0
    //   856: iload #5
    //   858: invokevirtual getChildAt : (I)Landroid/view/View;
    //   861: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   864: checkcast androidx/appcompat/widget/ActionMenuView$LayoutParams
    //   867: getfield preventEdgeOffset : Z
    //   870: ifne -> 882
    //   873: fload_3
    //   874: ldc 0.5
    //   876: fsub
    //   877: fstore #4
    //   879: goto -> 882
    //   882: fload #4
    //   884: fconst_0
    //   885: fcmpl
    //   886: ifle -> 904
    //   889: iload #13
    //   891: iload #21
    //   893: imul
    //   894: i2f
    //   895: fload #4
    //   897: fdiv
    //   898: f2i
    //   899: istore #5
    //   901: goto -> 907
    //   904: iconst_0
    //   905: istore #5
    //   907: iconst_0
    //   908: istore #7
    //   910: iload_1
    //   911: istore #8
    //   913: iload #7
    //   915: iload #20
    //   917: if_icmpge -> 1094
    //   920: lload #22
    //   922: iconst_1
    //   923: iload #7
    //   925: ishl
    //   926: i2l
    //   927: land
    //   928: lconst_0
    //   929: lcmp
    //   930: ifne -> 939
    //   933: iload_1
    //   934: istore #8
    //   936: goto -> 1082
    //   939: aload_0
    //   940: iload #7
    //   942: invokevirtual getChildAt : (I)Landroid/view/View;
    //   945: astore #31
    //   947: aload #31
    //   949: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   952: checkcast androidx/appcompat/widget/ActionMenuView$LayoutParams
    //   955: astore #32
    //   957: aload #31
    //   959: instanceof androidx/appcompat/view/menu/ActionMenuItemView
    //   962: ifeq -> 1007
    //   965: aload #32
    //   967: iload #5
    //   969: putfield extraPixels : I
    //   972: aload #32
    //   974: iconst_1
    //   975: putfield expanded : Z
    //   978: iload #7
    //   980: ifne -> 1004
    //   983: aload #32
    //   985: getfield preventEdgeOffset : Z
    //   988: ifne -> 1004
    //   991: aload #32
    //   993: iload #5
    //   995: ineg
    //   996: iconst_2
    //   997: idiv
    //   998: putfield leftMargin : I
    //   1001: goto -> 1004
    //   1004: goto -> 1038
    //   1007: aload #32
    //   1009: getfield isOverflowButton : Z
    //   1012: ifeq -> 1044
    //   1015: aload #32
    //   1017: iload #5
    //   1019: putfield extraPixels : I
    //   1022: aload #32
    //   1024: iconst_1
    //   1025: putfield expanded : Z
    //   1028: aload #32
    //   1030: iload #5
    //   1032: ineg
    //   1033: iconst_2
    //   1034: idiv
    //   1035: putfield rightMargin : I
    //   1038: iconst_1
    //   1039: istore #8
    //   1041: goto -> 1082
    //   1044: iload #7
    //   1046: ifeq -> 1058
    //   1049: aload #32
    //   1051: iload #5
    //   1053: iconst_2
    //   1054: idiv
    //   1055: putfield leftMargin : I
    //   1058: iload_1
    //   1059: istore #8
    //   1061: iload #7
    //   1063: iload #20
    //   1065: iconst_1
    //   1066: isub
    //   1067: if_icmpeq -> 1082
    //   1070: aload #32
    //   1072: iload #5
    //   1074: iconst_2
    //   1075: idiv
    //   1076: putfield rightMargin : I
    //   1079: iload_1
    //   1080: istore #8
    //   1082: iinc #7, 1
    //   1085: iload #8
    //   1087: istore_1
    //   1088: goto -> 910
    //   1091: iload_1
    //   1092: istore #8
    //   1094: iload #8
    //   1096: ifeq -> 1167
    //   1099: iconst_0
    //   1100: istore_1
    //   1101: iload_1
    //   1102: iload #20
    //   1104: if_icmpge -> 1167
    //   1107: aload_0
    //   1108: iload_1
    //   1109: invokevirtual getChildAt : (I)Landroid/view/View;
    //   1112: astore #32
    //   1114: aload #32
    //   1116: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1119: checkcast androidx/appcompat/widget/ActionMenuView$LayoutParams
    //   1122: astore #31
    //   1124: aload #31
    //   1126: getfield expanded : Z
    //   1129: ifne -> 1135
    //   1132: goto -> 1161
    //   1135: aload #32
    //   1137: aload #31
    //   1139: getfield cellsUsed : I
    //   1142: iload #21
    //   1144: imul
    //   1145: aload #31
    //   1147: getfield extraPixels : I
    //   1150: iadd
    //   1151: ldc 1073741824
    //   1153: invokestatic makeMeasureSpec : (II)I
    //   1156: iload #19
    //   1158: invokevirtual measure : (II)V
    //   1161: iinc #1, 1
    //   1164: goto -> 1101
    //   1167: iload #12
    //   1169: ldc 1073741824
    //   1171: if_icmpeq -> 1179
    //   1174: iload_2
    //   1175: istore_1
    //   1176: goto -> 1182
    //   1179: iload #6
    //   1181: istore_1
    //   1182: aload_0
    //   1183: iload #9
    //   1185: iload_1
    //   1186: invokevirtual setMeasuredDimension : (II)V
    //   1189: return
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void dismissPopupMenus() {
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null)
      actionMenuPresenter.dismissPopupMenus(); 
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    return false;
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    LayoutParams layoutParams = new LayoutParams(-2, -2);
    layoutParams.gravity = 16;
    return layoutParams;
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    if (paramLayoutParams != null) {
      LayoutParams layoutParams;
      if (paramLayoutParams instanceof LayoutParams) {
        layoutParams = new LayoutParams((LayoutParams)paramLayoutParams);
      } else {
        layoutParams = new LayoutParams((ViewGroup.LayoutParams)layoutParams);
      } 
      if (layoutParams.gravity <= 0)
        layoutParams.gravity = 16; 
      return layoutParams;
    } 
    return generateDefaultLayoutParams();
  }
  
  public LayoutParams generateOverflowButtonLayoutParams() {
    LayoutParams layoutParams = generateDefaultLayoutParams();
    layoutParams.isOverflowButton = true;
    return layoutParams;
  }
  
  public Menu getMenu() {
    if (this.mMenu == null) {
      Context context = getContext();
      MenuBuilder menuBuilder = new MenuBuilder(context);
      this.mMenu = menuBuilder;
      menuBuilder.setCallback(new MenuBuilderCallback());
      ActionMenuPresenter actionMenuPresenter1 = new ActionMenuPresenter(context);
      this.mPresenter = actionMenuPresenter1;
      actionMenuPresenter1.setReserveOverflow(true);
      ActionMenuPresenter actionMenuPresenter2 = this.mPresenter;
      MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
      if (callback == null)
        callback = new ActionMenuPresenterCallback(); 
      actionMenuPresenter2.setCallback(callback);
      this.mMenu.addMenuPresenter((MenuPresenter)this.mPresenter, this.mPopupContext);
      this.mPresenter.setMenuView(this);
    } 
    return (Menu)this.mMenu;
  }
  
  public Drawable getOverflowIcon() {
    getMenu();
    return this.mPresenter.getOverflowIcon();
  }
  
  public int getPopupTheme() {
    return this.mPopupTheme;
  }
  
  public int getWindowAnimations() {
    return 0;
  }
  
  protected boolean hasSupportDividerBeforeChildAt(int paramInt) {
    boolean bool;
    int j = 0;
    if (paramInt == 0)
      return false; 
    View view1 = getChildAt(paramInt - 1);
    View view2 = getChildAt(paramInt);
    int i = j;
    if (paramInt < getChildCount()) {
      i = j;
      if (view1 instanceof ActionMenuChildView)
        i = false | ((ActionMenuChildView)view1).needsDividerAfter(); 
    } 
    j = i;
    if (paramInt > 0) {
      j = i;
      if (view2 instanceof ActionMenuChildView)
        bool = i | ((ActionMenuChildView)view2).needsDividerBefore(); 
    } 
    return bool;
  }
  
  public boolean hideOverflowMenu() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void initialize(MenuBuilder paramMenuBuilder) {
    this.mMenu = paramMenuBuilder;
  }
  
  public boolean invokeItem(MenuItemImpl paramMenuItemImpl) {
    return this.mMenu.performItemAction((MenuItem)paramMenuItemImpl, 0);
  }
  
  public boolean isOverflowMenuShowPending() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowPending()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOverflowMenuShowing() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOverflowReserved() {
    return this.mReserveOverflow;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null) {
      actionMenuPresenter.updateMenuView(false);
      if (this.mPresenter.isOverflowMenuShowing()) {
        this.mPresenter.hideOverflowMenu();
        this.mPresenter.showOverflowMenu();
      } 
    } 
  }
  
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    dismissPopupMenus();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!this.mFormatItems) {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    } 
    int j = getChildCount();
    int i = (paramInt4 - paramInt2) / 2;
    int m = getDividerWidth();
    int k = paramInt3 - paramInt1;
    paramInt1 = k - getPaddingRight() - getPaddingLeft();
    paramBoolean = ViewUtils.isLayoutRtl((View)this);
    paramInt2 = 0;
    paramInt4 = 0;
    paramInt3 = 0;
    while (paramInt2 < j) {
      View view = getChildAt(paramInt2);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.isOverflowButton) {
          int i1;
          int n = view.getMeasuredWidth();
          paramInt4 = n;
          if (hasSupportDividerBeforeChildAt(paramInt2))
            paramInt4 = n + m; 
          int i2 = view.getMeasuredHeight();
          if (paramBoolean) {
            n = getPaddingLeft() + layoutParams.leftMargin;
            i1 = n + paramInt4;
          } else {
            i1 = getWidth() - getPaddingRight() - layoutParams.rightMargin;
            n = i1 - paramInt4;
          } 
          int i3 = i - i2 / 2;
          view.layout(n, i3, i1, i2 + i3);
          paramInt1 -= paramInt4;
          paramInt4 = 1;
        } else {
          paramInt1 -= view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
          hasSupportDividerBeforeChildAt(paramInt2);
          paramInt3++;
        } 
      } 
      paramInt2++;
    } 
    if (j == 1 && paramInt4 == 0) {
      View view = getChildAt(0);
      paramInt2 = view.getMeasuredWidth();
      paramInt1 = view.getMeasuredHeight();
      paramInt3 = k / 2 - paramInt2 / 2;
      paramInt4 = i - paramInt1 / 2;
      view.layout(paramInt3, paramInt4, paramInt2 + paramInt3, paramInt1 + paramInt4);
      return;
    } 
    paramInt2 = paramInt3 - (paramInt4 ^ 0x1);
    if (paramInt2 > 0) {
      paramInt1 /= paramInt2;
    } else {
      paramInt1 = 0;
    } 
    paramInt4 = Math.max(0, paramInt1);
    if (paramBoolean) {
      paramInt2 = getWidth() - getPaddingRight();
      paramInt1 = 0;
      while (paramInt1 < j) {
        View view = getChildAt(paramInt1);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        paramInt3 = paramInt2;
        if (view.getVisibility() != 8)
          if (layoutParams.isOverflowButton) {
            paramInt3 = paramInt2;
          } else {
            int i1 = paramInt2 - layoutParams.rightMargin;
            paramInt3 = view.getMeasuredWidth();
            paramInt2 = view.getMeasuredHeight();
            int n = i - paramInt2 / 2;
            view.layout(i1 - paramInt3, n, i1, paramInt2 + n);
            paramInt3 = i1 - paramInt3 + layoutParams.leftMargin + paramInt4;
          }  
        paramInt1++;
        paramInt2 = paramInt3;
      } 
    } else {
      paramInt3 = getPaddingLeft();
      paramInt1 = 0;
      while (paramInt1 < j) {
        View view = getChildAt(paramInt1);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        paramInt2 = paramInt3;
        if (view.getVisibility() != 8)
          if (layoutParams.isOverflowButton) {
            paramInt2 = paramInt3;
          } else {
            paramInt2 = paramInt3 + layoutParams.leftMargin;
            int i1 = view.getMeasuredWidth();
            paramInt3 = view.getMeasuredHeight();
            int n = i - paramInt3 / 2;
            view.layout(paramInt2, n, paramInt2 + i1, paramInt3 + n);
            paramInt2 += i1 + layoutParams.rightMargin + paramInt4;
          }  
        paramInt1++;
        paramInt3 = paramInt2;
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    boolean bool1;
    boolean bool2 = this.mFormatItems;
    if (View.MeasureSpec.getMode(paramInt1) == 1073741824) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.mFormatItems = bool1;
    if (bool2 != bool1)
      this.mFormatItemsWidth = 0; 
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mFormatItems) {
      MenuBuilder menuBuilder = this.mMenu;
      if (menuBuilder != null && i != this.mFormatItemsWidth) {
        this.mFormatItemsWidth = i;
        menuBuilder.onItemsChanged(true);
      } 
    } 
    int j = getChildCount();
    if (this.mFormatItems && j > 0) {
      onMeasureExactFormat(paramInt1, paramInt2);
    } else {
      for (i = 0; i < j; i++) {
        LayoutParams layoutParams = (LayoutParams)getChildAt(i).getLayoutParams();
        layoutParams.rightMargin = 0;
        layoutParams.leftMargin = 0;
      } 
      super.onMeasure(paramInt1, paramInt2);
    } 
  }
  
  public MenuBuilder peekMenu() {
    return this.mMenu;
  }
  
  public void setExpandedActionViewsExclusive(boolean paramBoolean) {
    this.mPresenter.setExpandedActionViewsExclusive(paramBoolean);
  }
  
  public void setMenuCallbacks(MenuPresenter.Callback paramCallback, MenuBuilder.Callback paramCallback1) {
    this.mActionMenuPresenterCallback = paramCallback;
    this.mMenuBuilderCallback = paramCallback1;
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener) {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable) {
    getMenu();
    this.mPresenter.setOverflowIcon(paramDrawable);
  }
  
  public void setOverflowReserved(boolean paramBoolean) {
    this.mReserveOverflow = paramBoolean;
  }
  
  public void setPopupTheme(int paramInt) {
    if (this.mPopupTheme != paramInt) {
      this.mPopupTheme = paramInt;
      if (paramInt == 0) {
        this.mPopupContext = getContext();
      } else {
        this.mPopupContext = (Context)new ContextThemeWrapper(getContext(), paramInt);
      } 
    } 
  }
  
  public void setPresenter(ActionMenuPresenter paramActionMenuPresenter) {
    this.mPresenter = paramActionMenuPresenter;
    paramActionMenuPresenter.setMenuView(this);
  }
  
  public boolean showOverflowMenu() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static interface ActionMenuChildView {
    boolean needsDividerAfter();
    
    boolean needsDividerBefore();
  }
  
  private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {}
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      return false;
    }
  }
  
  public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
    @ExportedProperty
    public int cellsUsed;
    
    @ExportedProperty
    public boolean expandable;
    
    boolean expanded;
    
    @ExportedProperty
    public int extraPixels;
    
    @ExportedProperty
    public boolean isOverflowButton;
    
    @ExportedProperty
    public boolean preventEdgeOffset;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
      this.isOverflowButton = false;
    }
    
    LayoutParams(int param1Int1, int param1Int2, boolean param1Boolean) {
      super(param1Int1, param1Int2);
      this.isOverflowButton = param1Boolean;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super((ViewGroup.LayoutParams)param1LayoutParams);
      this.isOverflowButton = param1LayoutParams.isOverflowButton;
    }
  }
  
  private class MenuBuilderCallback implements MenuBuilder.Callback {
    final ActionMenuView this$0;
    
    public boolean onMenuItemSelected(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
      boolean bool;
      if (ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(param1MenuItem)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onMenuModeChange(MenuBuilder param1MenuBuilder) {
      if (ActionMenuView.this.mMenuBuilderCallback != null)
        ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(param1MenuBuilder); 
    }
  }
  
  public static interface OnMenuItemClickListener {
    boolean onMenuItemClick(MenuItem param1MenuItem);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\ActionMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */