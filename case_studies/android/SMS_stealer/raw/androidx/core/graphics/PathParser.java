package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

public class PathParser {
  private static final String LOGTAG = "PathParser";
  
  private static void addNode(ArrayList<PathDataNode> paramArrayList, char paramChar, float[] paramArrayOffloat) {
    paramArrayList.add(new PathDataNode(paramChar, paramArrayOffloat));
  }
  
  public static boolean canMorph(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2) {
    if (paramArrayOfPathDataNode1 == null || paramArrayOfPathDataNode2 == null)
      return false; 
    if (paramArrayOfPathDataNode1.length != paramArrayOfPathDataNode2.length)
      return false; 
    for (byte b = 0; b < paramArrayOfPathDataNode1.length; b++) {
      if ((paramArrayOfPathDataNode1[b]).mType != (paramArrayOfPathDataNode2[b]).mType || (paramArrayOfPathDataNode1[b]).mParams.length != (paramArrayOfPathDataNode2[b]).mParams.length)
        return false; 
    } 
    return true;
  }
  
  static float[] copyOfRange(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
    if (paramInt1 <= paramInt2) {
      int i = paramArrayOffloat.length;
      if (paramInt1 >= 0 && paramInt1 <= i) {
        paramInt2 -= paramInt1;
        i = Math.min(paramInt2, i - paramInt1);
        float[] arrayOfFloat = new float[paramInt2];
        System.arraycopy(paramArrayOffloat, paramInt1, arrayOfFloat, 0, i);
        return arrayOfFloat;
      } 
      throw new ArrayIndexOutOfBoundsException();
    } 
    throw new IllegalArgumentException();
  }
  
  public static PathDataNode[] createNodesFromPathData(String paramString) {
    if (paramString == null)
      return null; 
    ArrayList<PathDataNode> arrayList = new ArrayList();
    int j = 1;
    int i = 0;
    while (j < paramString.length()) {
      j = nextStart(paramString, j);
      String str = paramString.substring(i, j).trim();
      if (str.length() > 0) {
        float[] arrayOfFloat = getFloats(str);
        addNode(arrayList, str.charAt(0), arrayOfFloat);
      } 
      i = j;
      j++;
    } 
    if (j - i == 1 && i < paramString.length())
      addNode(arrayList, paramString.charAt(i), new float[0]); 
    return arrayList.<PathDataNode>toArray(new PathDataNode[arrayList.size()]);
  }
  
  public static Path createPathFromPathData(String paramString) {
    Path path = new Path();
    PathDataNode[] arrayOfPathDataNode = createNodesFromPathData(paramString);
    if (arrayOfPathDataNode != null)
      try {
        PathDataNode.nodesToPath(arrayOfPathDataNode, path);
        return path;
      } catch (RuntimeException runtimeException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error in parsing ");
        stringBuilder.append(paramString);
        throw new RuntimeException(stringBuilder.toString(), runtimeException);
      }  
    return null;
  }
  
  public static PathDataNode[] deepCopyNodes(PathDataNode[] paramArrayOfPathDataNode) {
    if (paramArrayOfPathDataNode == null)
      return null; 
    PathDataNode[] arrayOfPathDataNode = new PathDataNode[paramArrayOfPathDataNode.length];
    for (byte b = 0; b < paramArrayOfPathDataNode.length; b++)
      arrayOfPathDataNode[b] = new PathDataNode(paramArrayOfPathDataNode[b]); 
    return arrayOfPathDataNode;
  }
  
  private static void extract(String paramString, int paramInt, ExtractFloatResult paramExtractFloatResult) {
    paramExtractFloatResult.mEndWithNegOrDot = false;
    int i = paramInt;
    boolean bool1 = false;
    boolean bool3 = false;
    boolean bool2 = false;
    while (i < paramString.length()) {
      char c = paramString.charAt(i);
      if (c != ' ') {
        if (c != 'E' && c != 'e') {
          switch (c) {
            default:
              bool1 = false;
              break;
            case '.':
              if (!bool3) {
                bool1 = false;
                bool3 = true;
                break;
              } 
              paramExtractFloatResult.mEndWithNegOrDot = true;
            case '-':
            
            case ',':
              bool1 = false;
              bool2 = true;
              break;
          } 
        } else {
          bool1 = true;
        } 
        if (bool2)
          break; 
        continue;
      } 
      i++;
    } 
    paramExtractFloatResult.mEndPosition = i;
  }
  
  private static float[] getFloats(String paramString) {
    if (paramString.charAt(0) == 'z' || paramString.charAt(0) == 'Z')
      return new float[0]; 
    try {
      null = new float[paramString.length()];
      ExtractFloatResult extractFloatResult = new ExtractFloatResult();
      this();
      int k = paramString.length();
      int i = 1;
      int j;
      for (j = 0; i < k; j = m) {
        extract(paramString, i, extractFloatResult);
        int n = extractFloatResult.mEndPosition;
        int m = j;
        if (i < n) {
          null[j] = Float.parseFloat(paramString.substring(i, n));
          m = j + 1;
        } 
        if (extractFloatResult.mEndWithNegOrDot) {
          i = n;
          j = m;
          continue;
        } 
        i = n + 1;
      } 
      return copyOfRange(null, 0, j);
    } catch (NumberFormatException numberFormatException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("error in parsing \"");
      stringBuilder.append(paramString);
      stringBuilder.append("\"");
      throw new RuntimeException(stringBuilder.toString(), numberFormatException);
    } 
  }
  
  public static boolean interpolatePathDataNodes(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2, PathDataNode[] paramArrayOfPathDataNode3, float paramFloat) {
    if (paramArrayOfPathDataNode1 != null && paramArrayOfPathDataNode2 != null && paramArrayOfPathDataNode3 != null) {
      if (paramArrayOfPathDataNode1.length == paramArrayOfPathDataNode2.length && paramArrayOfPathDataNode2.length == paramArrayOfPathDataNode3.length) {
        boolean bool = canMorph(paramArrayOfPathDataNode2, paramArrayOfPathDataNode3);
        byte b = 0;
        if (!bool)
          return false; 
        while (b < paramArrayOfPathDataNode1.length) {
          paramArrayOfPathDataNode1[b].interpolatePathDataNode(paramArrayOfPathDataNode2[b], paramArrayOfPathDataNode3[b], paramFloat);
          b++;
        } 
        return true;
      } 
      throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
    } 
    throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
  }
  
  private static int nextStart(String paramString, int paramInt) {
    while (paramInt < paramString.length()) {
      char c = paramString.charAt(paramInt);
      if (((c - 65) * (c - 90) <= 0 || (c - 97) * (c - 122) <= 0) && c != 'e' && c != 'E')
        return paramInt; 
      paramInt++;
    } 
    return paramInt;
  }
  
  public static void updateNodes(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2) {
    for (byte b = 0; b < paramArrayOfPathDataNode2.length; b++) {
      (paramArrayOfPathDataNode1[b]).mType = (paramArrayOfPathDataNode2[b]).mType;
      for (byte b1 = 0; b1 < (paramArrayOfPathDataNode2[b]).mParams.length; b1++)
        (paramArrayOfPathDataNode1[b]).mParams[b1] = (paramArrayOfPathDataNode2[b]).mParams[b1]; 
    } 
  }
  
  private static class ExtractFloatResult {
    int mEndPosition;
    
    boolean mEndWithNegOrDot;
  }
  
  public static class PathDataNode {
    public float[] mParams;
    
    public char mType;
    
    PathDataNode(char param1Char, float[] param1ArrayOffloat) {
      this.mType = param1Char;
      this.mParams = param1ArrayOffloat;
    }
    
    PathDataNode(PathDataNode param1PathDataNode) {
      this.mType = param1PathDataNode.mType;
      float[] arrayOfFloat = param1PathDataNode.mParams;
      this.mParams = PathParser.copyOfRange(arrayOfFloat, 0, arrayOfFloat.length);
    }
    
    private static void addCommand(Path param1Path, float[] param1ArrayOffloat1, char param1Char1, char param1Char2, float[] param1ArrayOffloat2) {
      // Byte code:
      //   0: aload_1
      //   1: iconst_0
      //   2: faload
      //   3: fstore #13
      //   5: aload_1
      //   6: iconst_1
      //   7: faload
      //   8: fstore #14
      //   10: aload_1
      //   11: iconst_2
      //   12: faload
      //   13: fstore #12
      //   15: aload_1
      //   16: iconst_3
      //   17: faload
      //   18: fstore #11
      //   20: aload_1
      //   21: iconst_4
      //   22: faload
      //   23: fstore #10
      //   25: aload_1
      //   26: iconst_5
      //   27: faload
      //   28: fstore #9
      //   30: fload #13
      //   32: fstore #6
      //   34: fload #14
      //   36: fstore #5
      //   38: fload #12
      //   40: fstore #7
      //   42: fload #11
      //   44: fstore #8
      //   46: iload_3
      //   47: lookupswitch default -> 216, 65 -> 336, 67 -> 313, 72 -> 291, 76 -> 232, 77 -> 232, 81 -> 269, 83 -> 269, 84 -> 232, 86 -> 291, 90 -> 238, 97 -> 336, 99 -> 313, 104 -> 291, 108 -> 232, 109 -> 232, 113 -> 269, 115 -> 269, 116 -> 232, 118 -> 291, 122 -> 238
      //   216: fload #11
      //   218: fstore #8
      //   220: fload #12
      //   222: fstore #7
      //   224: fload #14
      //   226: fstore #5
      //   228: fload #13
      //   230: fstore #6
      //   232: iconst_2
      //   233: istore #15
      //   235: goto -> 356
      //   238: aload_0
      //   239: invokevirtual close : ()V
      //   242: aload_0
      //   243: fload #10
      //   245: fload #9
      //   247: invokevirtual moveTo : (FF)V
      //   250: fload #10
      //   252: fstore #6
      //   254: fload #6
      //   256: fstore #7
      //   258: fload #9
      //   260: fstore #5
      //   262: fload #5
      //   264: fstore #8
      //   266: goto -> 232
      //   269: iconst_4
      //   270: istore #15
      //   272: fload #13
      //   274: fstore #6
      //   276: fload #14
      //   278: fstore #5
      //   280: fload #12
      //   282: fstore #7
      //   284: fload #11
      //   286: fstore #8
      //   288: goto -> 356
      //   291: iconst_1
      //   292: istore #15
      //   294: fload #13
      //   296: fstore #6
      //   298: fload #14
      //   300: fstore #5
      //   302: fload #12
      //   304: fstore #7
      //   306: fload #11
      //   308: fstore #8
      //   310: goto -> 356
      //   313: bipush #6
      //   315: istore #15
      //   317: fload #13
      //   319: fstore #6
      //   321: fload #14
      //   323: fstore #5
      //   325: fload #12
      //   327: fstore #7
      //   329: fload #11
      //   331: fstore #8
      //   333: goto -> 356
      //   336: bipush #7
      //   338: istore #15
      //   340: fload #11
      //   342: fstore #8
      //   344: fload #12
      //   346: fstore #7
      //   348: fload #14
      //   350: fstore #5
      //   352: fload #13
      //   354: fstore #6
      //   356: fload #5
      //   358: fstore #13
      //   360: iconst_0
      //   361: istore #17
      //   363: iload_2
      //   364: istore #16
      //   366: fload #9
      //   368: fstore #11
      //   370: fload #10
      //   372: fstore #12
      //   374: fload #6
      //   376: fstore #5
      //   378: fload #13
      //   380: fstore #6
      //   382: iload #17
      //   384: istore_2
      //   385: iload_2
      //   386: aload #4
      //   388: arraylength
      //   389: if_icmpge -> 2112
      //   392: iload_3
      //   393: bipush #65
      //   395: if_icmpeq -> 1968
      //   398: iload_3
      //   399: bipush #67
      //   401: if_icmpeq -> 1861
      //   404: iload_3
      //   405: bipush #72
      //   407: if_icmpeq -> 1835
      //   410: iload_3
      //   411: bipush #81
      //   413: if_icmpeq -> 1748
      //   416: iload_3
      //   417: bipush #86
      //   419: if_icmpeq -> 1722
      //   422: iload_3
      //   423: bipush #97
      //   425: if_icmpeq -> 1582
      //   428: iload_3
      //   429: bipush #99
      //   431: if_icmpeq -> 1439
      //   434: iload_3
      //   435: bipush #104
      //   437: if_icmpeq -> 1411
      //   440: iload_3
      //   441: bipush #113
      //   443: if_icmpeq -> 1311
      //   446: iload_3
      //   447: bipush #118
      //   449: if_icmpeq -> 1286
      //   452: iload_3
      //   453: bipush #76
      //   455: if_icmpeq -> 1241
      //   458: iload_3
      //   459: bipush #77
      //   461: if_icmpeq -> 1171
      //   464: iload_3
      //   465: bipush #83
      //   467: if_icmpeq -> 1026
      //   470: iload_3
      //   471: bipush #84
      //   473: if_icmpeq -> 915
      //   476: iload_3
      //   477: bipush #108
      //   479: if_icmpeq -> 860
      //   482: iload_3
      //   483: bipush #109
      //   485: if_icmpeq -> 792
      //   488: iload_3
      //   489: bipush #115
      //   491: if_icmpeq -> 634
      //   494: iload_3
      //   495: bipush #116
      //   497: if_icmpeq -> 503
      //   500: goto -> 2101
      //   503: iload #16
      //   505: bipush #113
      //   507: if_icmpeq -> 543
      //   510: iload #16
      //   512: bipush #116
      //   514: if_icmpeq -> 543
      //   517: iload #16
      //   519: bipush #81
      //   521: if_icmpeq -> 543
      //   524: iload #16
      //   526: bipush #84
      //   528: if_icmpne -> 534
      //   531: goto -> 543
      //   534: fconst_0
      //   535: fstore #8
      //   537: fconst_0
      //   538: fstore #7
      //   540: goto -> 557
      //   543: fload #5
      //   545: fload #7
      //   547: fsub
      //   548: fstore #7
      //   550: fload #6
      //   552: fload #8
      //   554: fsub
      //   555: fstore #8
      //   557: iload_2
      //   558: iconst_0
      //   559: iadd
      //   560: istore #16
      //   562: aload #4
      //   564: iload #16
      //   566: faload
      //   567: fstore #9
      //   569: iload_2
      //   570: iconst_1
      //   571: iadd
      //   572: istore #17
      //   574: aload_0
      //   575: fload #7
      //   577: fload #8
      //   579: fload #9
      //   581: aload #4
      //   583: iload #17
      //   585: faload
      //   586: invokevirtual rQuadTo : (FFFF)V
      //   589: fload #5
      //   591: aload #4
      //   593: iload #16
      //   595: faload
      //   596: fadd
      //   597: fstore #9
      //   599: fload #6
      //   601: aload #4
      //   603: iload #17
      //   605: faload
      //   606: fadd
      //   607: fstore #10
      //   609: fload #8
      //   611: fload #6
      //   613: fadd
      //   614: fstore #8
      //   616: fload #7
      //   618: fload #5
      //   620: fadd
      //   621: fstore #7
      //   623: fload #10
      //   625: fstore #6
      //   627: fload #9
      //   629: fstore #5
      //   631: goto -> 500
      //   634: iload #16
      //   636: bipush #99
      //   638: if_icmpeq -> 674
      //   641: iload #16
      //   643: bipush #115
      //   645: if_icmpeq -> 674
      //   648: iload #16
      //   650: bipush #67
      //   652: if_icmpeq -> 674
      //   655: iload #16
      //   657: bipush #83
      //   659: if_icmpne -> 665
      //   662: goto -> 674
      //   665: fconst_0
      //   666: fstore #7
      //   668: fconst_0
      //   669: fstore #8
      //   671: goto -> 688
      //   674: fload #6
      //   676: fload #8
      //   678: fsub
      //   679: fstore #8
      //   681: fload #5
      //   683: fload #7
      //   685: fsub
      //   686: fstore #7
      //   688: iload_2
      //   689: iconst_0
      //   690: iadd
      //   691: istore #16
      //   693: aload #4
      //   695: iload #16
      //   697: faload
      //   698: fstore #13
      //   700: iload_2
      //   701: iconst_1
      //   702: iadd
      //   703: istore #19
      //   705: aload #4
      //   707: iload #19
      //   709: faload
      //   710: fstore #10
      //   712: iload_2
      //   713: iconst_2
      //   714: iadd
      //   715: istore #18
      //   717: aload #4
      //   719: iload #18
      //   721: faload
      //   722: fstore #9
      //   724: iload_2
      //   725: iconst_3
      //   726: iadd
      //   727: istore #17
      //   729: aload_0
      //   730: fload #7
      //   732: fload #8
      //   734: fload #13
      //   736: fload #10
      //   738: fload #9
      //   740: aload #4
      //   742: iload #17
      //   744: faload
      //   745: invokevirtual rCubicTo : (FFFFFF)V
      //   748: aload #4
      //   750: iload #16
      //   752: faload
      //   753: fload #5
      //   755: fadd
      //   756: fstore #10
      //   758: aload #4
      //   760: iload #19
      //   762: faload
      //   763: fload #6
      //   765: fadd
      //   766: fstore #7
      //   768: fload #5
      //   770: aload #4
      //   772: iload #18
      //   774: faload
      //   775: fadd
      //   776: fstore #8
      //   778: aload #4
      //   780: iload #17
      //   782: faload
      //   783: fstore #9
      //   785: fload #10
      //   787: fstore #5
      //   789: goto -> 1556
      //   792: iload_2
      //   793: iconst_0
      //   794: iadd
      //   795: istore #17
      //   797: fload #5
      //   799: aload #4
      //   801: iload #17
      //   803: faload
      //   804: fadd
      //   805: fstore #5
      //   807: iload_2
      //   808: iconst_1
      //   809: iadd
      //   810: istore #16
      //   812: fload #6
      //   814: aload #4
      //   816: iload #16
      //   818: faload
      //   819: fadd
      //   820: fstore #6
      //   822: iload_2
      //   823: ifle -> 843
      //   826: aload_0
      //   827: aload #4
      //   829: iload #17
      //   831: faload
      //   832: aload #4
      //   834: iload #16
      //   836: faload
      //   837: invokevirtual rLineTo : (FF)V
      //   840: goto -> 500
      //   843: aload_0
      //   844: aload #4
      //   846: iload #17
      //   848: faload
      //   849: aload #4
      //   851: iload #16
      //   853: faload
      //   854: invokevirtual rMoveTo : (FF)V
      //   857: goto -> 1230
      //   860: iload_2
      //   861: iconst_0
      //   862: iadd
      //   863: istore #17
      //   865: aload #4
      //   867: iload #17
      //   869: faload
      //   870: fstore #9
      //   872: iload_2
      //   873: iconst_1
      //   874: iadd
      //   875: istore #16
      //   877: aload_0
      //   878: fload #9
      //   880: aload #4
      //   882: iload #16
      //   884: faload
      //   885: invokevirtual rLineTo : (FF)V
      //   888: fload #5
      //   890: aload #4
      //   892: iload #17
      //   894: faload
      //   895: fadd
      //   896: fstore #5
      //   898: aload #4
      //   900: iload #16
      //   902: faload
      //   903: fstore #9
      //   905: fload #6
      //   907: fload #9
      //   909: fadd
      //   910: fstore #6
      //   912: goto -> 500
      //   915: iload #16
      //   917: bipush #113
      //   919: if_icmpeq -> 951
      //   922: iload #16
      //   924: bipush #116
      //   926: if_icmpeq -> 951
      //   929: iload #16
      //   931: bipush #81
      //   933: if_icmpeq -> 951
      //   936: fload #6
      //   938: fstore #10
      //   940: fload #5
      //   942: fstore #9
      //   944: iload #16
      //   946: bipush #84
      //   948: if_icmpne -> 969
      //   951: fload #5
      //   953: fconst_2
      //   954: fmul
      //   955: fload #7
      //   957: fsub
      //   958: fstore #9
      //   960: fload #6
      //   962: fconst_2
      //   963: fmul
      //   964: fload #8
      //   966: fsub
      //   967: fstore #10
      //   969: iload_2
      //   970: iconst_0
      //   971: iadd
      //   972: istore #17
      //   974: aload #4
      //   976: iload #17
      //   978: faload
      //   979: fstore #5
      //   981: iload_2
      //   982: iconst_1
      //   983: iadd
      //   984: istore #16
      //   986: aload_0
      //   987: fload #9
      //   989: fload #10
      //   991: fload #5
      //   993: aload #4
      //   995: iload #16
      //   997: faload
      //   998: invokevirtual quadTo : (FFFF)V
      //   1001: aload #4
      //   1003: iload #17
      //   1005: faload
      //   1006: fstore #5
      //   1008: aload #4
      //   1010: iload #16
      //   1012: faload
      //   1013: fstore #6
      //   1015: fload #10
      //   1017: fstore #8
      //   1019: fload #9
      //   1021: fstore #7
      //   1023: goto -> 2101
      //   1026: iload #16
      //   1028: bipush #99
      //   1030: if_icmpeq -> 1062
      //   1033: iload #16
      //   1035: bipush #115
      //   1037: if_icmpeq -> 1062
      //   1040: iload #16
      //   1042: bipush #67
      //   1044: if_icmpeq -> 1062
      //   1047: fload #6
      //   1049: fstore #10
      //   1051: fload #5
      //   1053: fstore #9
      //   1055: iload #16
      //   1057: bipush #83
      //   1059: if_icmpne -> 1080
      //   1062: fload #5
      //   1064: fconst_2
      //   1065: fmul
      //   1066: fload #7
      //   1068: fsub
      //   1069: fstore #9
      //   1071: fload #6
      //   1073: fconst_2
      //   1074: fmul
      //   1075: fload #8
      //   1077: fsub
      //   1078: fstore #10
      //   1080: iload_2
      //   1081: iconst_0
      //   1082: iadd
      //   1083: istore #19
      //   1085: aload #4
      //   1087: iload #19
      //   1089: faload
      //   1090: fstore #6
      //   1092: iload_2
      //   1093: iconst_1
      //   1094: iadd
      //   1095: istore #18
      //   1097: aload #4
      //   1099: iload #18
      //   1101: faload
      //   1102: fstore #7
      //   1104: iload_2
      //   1105: iconst_2
      //   1106: iadd
      //   1107: istore #17
      //   1109: aload #4
      //   1111: iload #17
      //   1113: faload
      //   1114: fstore #5
      //   1116: iload_2
      //   1117: iconst_3
      //   1118: iadd
      //   1119: istore #16
      //   1121: aload_0
      //   1122: fload #9
      //   1124: fload #10
      //   1126: fload #6
      //   1128: fload #7
      //   1130: fload #5
      //   1132: aload #4
      //   1134: iload #16
      //   1136: faload
      //   1137: invokevirtual cubicTo : (FFFFFF)V
      //   1140: aload #4
      //   1142: iload #19
      //   1144: faload
      //   1145: fstore #5
      //   1147: aload #4
      //   1149: iload #18
      //   1151: faload
      //   1152: fstore #7
      //   1154: aload #4
      //   1156: iload #17
      //   1158: faload
      //   1159: fstore #9
      //   1161: aload #4
      //   1163: iload #16
      //   1165: faload
      //   1166: fstore #6
      //   1168: goto -> 1567
      //   1171: iload_2
      //   1172: iconst_0
      //   1173: iadd
      //   1174: istore #16
      //   1176: aload #4
      //   1178: iload #16
      //   1180: faload
      //   1181: fstore #5
      //   1183: iload_2
      //   1184: iconst_1
      //   1185: iadd
      //   1186: istore #17
      //   1188: aload #4
      //   1190: iload #17
      //   1192: faload
      //   1193: fstore #6
      //   1195: iload_2
      //   1196: ifle -> 1216
      //   1199: aload_0
      //   1200: aload #4
      //   1202: iload #16
      //   1204: faload
      //   1205: aload #4
      //   1207: iload #17
      //   1209: faload
      //   1210: invokevirtual lineTo : (FF)V
      //   1213: goto -> 500
      //   1216: aload_0
      //   1217: aload #4
      //   1219: iload #16
      //   1221: faload
      //   1222: aload #4
      //   1224: iload #17
      //   1226: faload
      //   1227: invokevirtual moveTo : (FF)V
      //   1230: fload #6
      //   1232: fstore #11
      //   1234: fload #5
      //   1236: fstore #12
      //   1238: goto -> 2101
      //   1241: iload_2
      //   1242: iconst_0
      //   1243: iadd
      //   1244: istore #17
      //   1246: aload #4
      //   1248: iload #17
      //   1250: faload
      //   1251: fstore #5
      //   1253: iload_2
      //   1254: iconst_1
      //   1255: iadd
      //   1256: istore #16
      //   1258: aload_0
      //   1259: fload #5
      //   1261: aload #4
      //   1263: iload #16
      //   1265: faload
      //   1266: invokevirtual lineTo : (FF)V
      //   1269: aload #4
      //   1271: iload #17
      //   1273: faload
      //   1274: fstore #5
      //   1276: aload #4
      //   1278: iload #16
      //   1280: faload
      //   1281: fstore #6
      //   1283: goto -> 500
      //   1286: iload_2
      //   1287: iconst_0
      //   1288: iadd
      //   1289: istore #16
      //   1291: aload_0
      //   1292: fconst_0
      //   1293: aload #4
      //   1295: iload #16
      //   1297: faload
      //   1298: invokevirtual rLineTo : (FF)V
      //   1301: aload #4
      //   1303: iload #16
      //   1305: faload
      //   1306: fstore #9
      //   1308: goto -> 905
      //   1311: iload_2
      //   1312: iconst_0
      //   1313: iadd
      //   1314: istore #18
      //   1316: aload #4
      //   1318: iload #18
      //   1320: faload
      //   1321: fstore #7
      //   1323: iload_2
      //   1324: iconst_1
      //   1325: iadd
      //   1326: istore #19
      //   1328: aload #4
      //   1330: iload #19
      //   1332: faload
      //   1333: fstore #9
      //   1335: iload_2
      //   1336: iconst_2
      //   1337: iadd
      //   1338: istore #16
      //   1340: aload #4
      //   1342: iload #16
      //   1344: faload
      //   1345: fstore #8
      //   1347: iload_2
      //   1348: iconst_3
      //   1349: iadd
      //   1350: istore #17
      //   1352: aload_0
      //   1353: fload #7
      //   1355: fload #9
      //   1357: fload #8
      //   1359: aload #4
      //   1361: iload #17
      //   1363: faload
      //   1364: invokevirtual rQuadTo : (FFFF)V
      //   1367: aload #4
      //   1369: iload #18
      //   1371: faload
      //   1372: fload #5
      //   1374: fadd
      //   1375: fstore #10
      //   1377: aload #4
      //   1379: iload #19
      //   1381: faload
      //   1382: fload #6
      //   1384: fadd
      //   1385: fstore #7
      //   1387: fload #5
      //   1389: aload #4
      //   1391: iload #16
      //   1393: faload
      //   1394: fadd
      //   1395: fstore #8
      //   1397: aload #4
      //   1399: iload #17
      //   1401: faload
      //   1402: fstore #9
      //   1404: fload #10
      //   1406: fstore #5
      //   1408: goto -> 1556
      //   1411: iload_2
      //   1412: iconst_0
      //   1413: iadd
      //   1414: istore #16
      //   1416: aload_0
      //   1417: aload #4
      //   1419: iload #16
      //   1421: faload
      //   1422: fconst_0
      //   1423: invokevirtual rLineTo : (FF)V
      //   1426: fload #5
      //   1428: aload #4
      //   1430: iload #16
      //   1432: faload
      //   1433: fadd
      //   1434: fstore #5
      //   1436: goto -> 500
      //   1439: aload #4
      //   1441: iload_2
      //   1442: iconst_0
      //   1443: iadd
      //   1444: faload
      //   1445: fstore #9
      //   1447: aload #4
      //   1449: iload_2
      //   1450: iconst_1
      //   1451: iadd
      //   1452: faload
      //   1453: fstore #7
      //   1455: iload_2
      //   1456: iconst_2
      //   1457: iadd
      //   1458: istore #18
      //   1460: aload #4
      //   1462: iload #18
      //   1464: faload
      //   1465: fstore #8
      //   1467: iload_2
      //   1468: iconst_3
      //   1469: iadd
      //   1470: istore #16
      //   1472: aload #4
      //   1474: iload #16
      //   1476: faload
      //   1477: fstore #13
      //   1479: iload_2
      //   1480: iconst_4
      //   1481: iadd
      //   1482: istore #19
      //   1484: aload #4
      //   1486: iload #19
      //   1488: faload
      //   1489: fstore #10
      //   1491: iload_2
      //   1492: iconst_5
      //   1493: iadd
      //   1494: istore #17
      //   1496: aload_0
      //   1497: fload #9
      //   1499: fload #7
      //   1501: fload #8
      //   1503: fload #13
      //   1505: fload #10
      //   1507: aload #4
      //   1509: iload #17
      //   1511: faload
      //   1512: invokevirtual rCubicTo : (FFFFFF)V
      //   1515: aload #4
      //   1517: iload #18
      //   1519: faload
      //   1520: fload #5
      //   1522: fadd
      //   1523: fstore #10
      //   1525: aload #4
      //   1527: iload #16
      //   1529: faload
      //   1530: fload #6
      //   1532: fadd
      //   1533: fstore #7
      //   1535: fload #5
      //   1537: aload #4
      //   1539: iload #19
      //   1541: faload
      //   1542: fadd
      //   1543: fstore #8
      //   1545: aload #4
      //   1547: iload #17
      //   1549: faload
      //   1550: fstore #9
      //   1552: fload #10
      //   1554: fstore #5
      //   1556: fload #6
      //   1558: fload #9
      //   1560: fadd
      //   1561: fstore #6
      //   1563: fload #8
      //   1565: fstore #9
      //   1567: fload #7
      //   1569: fstore #8
      //   1571: fload #5
      //   1573: fstore #7
      //   1575: fload #9
      //   1577: fstore #5
      //   1579: goto -> 500
      //   1582: iload_2
      //   1583: iconst_5
      //   1584: iadd
      //   1585: istore #17
      //   1587: aload #4
      //   1589: iload #17
      //   1591: faload
      //   1592: fstore #10
      //   1594: iload_2
      //   1595: bipush #6
      //   1597: iadd
      //   1598: istore #16
      //   1600: aload #4
      //   1602: iload #16
      //   1604: faload
      //   1605: fstore #13
      //   1607: aload #4
      //   1609: iload_2
      //   1610: iconst_0
      //   1611: iadd
      //   1612: faload
      //   1613: fstore #9
      //   1615: aload #4
      //   1617: iload_2
      //   1618: iconst_1
      //   1619: iadd
      //   1620: faload
      //   1621: fstore #8
      //   1623: aload #4
      //   1625: iload_2
      //   1626: iconst_2
      //   1627: iadd
      //   1628: faload
      //   1629: fstore #7
      //   1631: aload #4
      //   1633: iload_2
      //   1634: iconst_3
      //   1635: iadd
      //   1636: faload
      //   1637: fconst_0
      //   1638: fcmpl
      //   1639: ifeq -> 1648
      //   1642: iconst_1
      //   1643: istore #20
      //   1645: goto -> 1651
      //   1648: iconst_0
      //   1649: istore #20
      //   1651: aload #4
      //   1653: iload_2
      //   1654: iconst_4
      //   1655: iadd
      //   1656: faload
      //   1657: fconst_0
      //   1658: fcmpl
      //   1659: ifeq -> 1668
      //   1662: iconst_1
      //   1663: istore #21
      //   1665: goto -> 1671
      //   1668: iconst_0
      //   1669: istore #21
      //   1671: aload_0
      //   1672: fload #5
      //   1674: fload #6
      //   1676: fload #10
      //   1678: fload #5
      //   1680: fadd
      //   1681: fload #13
      //   1683: fload #6
      //   1685: fadd
      //   1686: fload #9
      //   1688: fload #8
      //   1690: fload #7
      //   1692: iload #20
      //   1694: iload #21
      //   1696: invokestatic drawArc : (Landroid/graphics/Path;FFFFFFFZZ)V
      //   1699: fload #5
      //   1701: aload #4
      //   1703: iload #17
      //   1705: faload
      //   1706: fadd
      //   1707: fstore #5
      //   1709: fload #6
      //   1711: aload #4
      //   1713: iload #16
      //   1715: faload
      //   1716: fadd
      //   1717: fstore #6
      //   1719: goto -> 2093
      //   1722: iload_2
      //   1723: iconst_0
      //   1724: iadd
      //   1725: istore #16
      //   1727: aload_0
      //   1728: fload #5
      //   1730: aload #4
      //   1732: iload #16
      //   1734: faload
      //   1735: invokevirtual lineTo : (FF)V
      //   1738: aload #4
      //   1740: iload #16
      //   1742: faload
      //   1743: fstore #6
      //   1745: goto -> 2101
      //   1748: iload_2
      //   1749: iconst_0
      //   1750: iadd
      //   1751: istore #16
      //   1753: aload #4
      //   1755: iload #16
      //   1757: faload
      //   1758: fstore #7
      //   1760: iload_2
      //   1761: iconst_1
      //   1762: iadd
      //   1763: istore #18
      //   1765: aload #4
      //   1767: iload #18
      //   1769: faload
      //   1770: fstore #6
      //   1772: iload_2
      //   1773: iconst_2
      //   1774: iadd
      //   1775: istore #17
      //   1777: aload #4
      //   1779: iload #17
      //   1781: faload
      //   1782: fstore #5
      //   1784: iload_2
      //   1785: iconst_3
      //   1786: iadd
      //   1787: istore #19
      //   1789: aload_0
      //   1790: fload #7
      //   1792: fload #6
      //   1794: fload #5
      //   1796: aload #4
      //   1798: iload #19
      //   1800: faload
      //   1801: invokevirtual quadTo : (FFFF)V
      //   1804: aload #4
      //   1806: iload #16
      //   1808: faload
      //   1809: fstore #7
      //   1811: aload #4
      //   1813: iload #18
      //   1815: faload
      //   1816: fstore #8
      //   1818: aload #4
      //   1820: iload #17
      //   1822: faload
      //   1823: fstore #5
      //   1825: aload #4
      //   1827: iload #19
      //   1829: faload
      //   1830: fstore #6
      //   1832: goto -> 2101
      //   1835: iload_2
      //   1836: iconst_0
      //   1837: iadd
      //   1838: istore #16
      //   1840: aload_0
      //   1841: aload #4
      //   1843: iload #16
      //   1845: faload
      //   1846: fload #6
      //   1848: invokevirtual lineTo : (FF)V
      //   1851: aload #4
      //   1853: iload #16
      //   1855: faload
      //   1856: fstore #5
      //   1858: goto -> 2101
      //   1861: aload #4
      //   1863: iload_2
      //   1864: iconst_0
      //   1865: iadd
      //   1866: faload
      //   1867: fstore #8
      //   1869: aload #4
      //   1871: iload_2
      //   1872: iconst_1
      //   1873: iadd
      //   1874: faload
      //   1875: fstore #7
      //   1877: iload_2
      //   1878: iconst_2
      //   1879: iadd
      //   1880: istore #18
      //   1882: aload #4
      //   1884: iload #18
      //   1886: faload
      //   1887: fstore #5
      //   1889: iload_2
      //   1890: iconst_3
      //   1891: iadd
      //   1892: istore #17
      //   1894: aload #4
      //   1896: iload #17
      //   1898: faload
      //   1899: fstore #6
      //   1901: iload_2
      //   1902: iconst_4
      //   1903: iadd
      //   1904: istore #16
      //   1906: aload #4
      //   1908: iload #16
      //   1910: faload
      //   1911: fstore #9
      //   1913: iload_2
      //   1914: iconst_5
      //   1915: iadd
      //   1916: istore #19
      //   1918: aload_0
      //   1919: fload #8
      //   1921: fload #7
      //   1923: fload #5
      //   1925: fload #6
      //   1927: fload #9
      //   1929: aload #4
      //   1931: iload #19
      //   1933: faload
      //   1934: invokevirtual cubicTo : (FFFFFF)V
      //   1937: aload #4
      //   1939: iload #16
      //   1941: faload
      //   1942: fstore #5
      //   1944: aload #4
      //   1946: iload #19
      //   1948: faload
      //   1949: fstore #6
      //   1951: aload #4
      //   1953: iload #18
      //   1955: faload
      //   1956: fstore #7
      //   1958: aload #4
      //   1960: iload #17
      //   1962: faload
      //   1963: fstore #8
      //   1965: goto -> 2101
      //   1968: iload_2
      //   1969: iconst_5
      //   1970: iadd
      //   1971: istore #17
      //   1973: aload #4
      //   1975: iload #17
      //   1977: faload
      //   1978: fstore #13
      //   1980: iload_2
      //   1981: bipush #6
      //   1983: iadd
      //   1984: istore #16
      //   1986: aload #4
      //   1988: iload #16
      //   1990: faload
      //   1991: fstore #7
      //   1993: aload #4
      //   1995: iload_2
      //   1996: iconst_0
      //   1997: iadd
      //   1998: faload
      //   1999: fstore #10
      //   2001: aload #4
      //   2003: iload_2
      //   2004: iconst_1
      //   2005: iadd
      //   2006: faload
      //   2007: fstore #9
      //   2009: aload #4
      //   2011: iload_2
      //   2012: iconst_2
      //   2013: iadd
      //   2014: faload
      //   2015: fstore #8
      //   2017: aload #4
      //   2019: iload_2
      //   2020: iconst_3
      //   2021: iadd
      //   2022: faload
      //   2023: fconst_0
      //   2024: fcmpl
      //   2025: ifeq -> 2034
      //   2028: iconst_1
      //   2029: istore #20
      //   2031: goto -> 2037
      //   2034: iconst_0
      //   2035: istore #20
      //   2037: aload #4
      //   2039: iload_2
      //   2040: iconst_4
      //   2041: iadd
      //   2042: faload
      //   2043: fconst_0
      //   2044: fcmpl
      //   2045: ifeq -> 2054
      //   2048: iconst_1
      //   2049: istore #21
      //   2051: goto -> 2057
      //   2054: iconst_0
      //   2055: istore #21
      //   2057: aload_0
      //   2058: fload #5
      //   2060: fload #6
      //   2062: fload #13
      //   2064: fload #7
      //   2066: fload #10
      //   2068: fload #9
      //   2070: fload #8
      //   2072: iload #20
      //   2074: iload #21
      //   2076: invokestatic drawArc : (Landroid/graphics/Path;FFFFFFFZZ)V
      //   2079: aload #4
      //   2081: iload #17
      //   2083: faload
      //   2084: fstore #5
      //   2086: aload #4
      //   2088: iload #16
      //   2090: faload
      //   2091: fstore #6
      //   2093: fload #6
      //   2095: fstore #8
      //   2097: fload #5
      //   2099: fstore #7
      //   2101: iload_2
      //   2102: iload #15
      //   2104: iadd
      //   2105: istore_2
      //   2106: iload_3
      //   2107: istore #16
      //   2109: goto -> 385
      //   2112: aload_1
      //   2113: iconst_0
      //   2114: fload #5
      //   2116: fastore
      //   2117: aload_1
      //   2118: iconst_1
      //   2119: fload #6
      //   2121: fastore
      //   2122: aload_1
      //   2123: iconst_2
      //   2124: fload #7
      //   2126: fastore
      //   2127: aload_1
      //   2128: iconst_3
      //   2129: fload #8
      //   2131: fastore
      //   2132: aload_1
      //   2133: iconst_4
      //   2134: fload #12
      //   2136: fastore
      //   2137: aload_1
      //   2138: iconst_5
      //   2139: fload #11
      //   2141: fastore
      //   2142: return
    }
    
    private static void arcToBezier(Path param1Path, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9) {
      int i = (int)Math.ceil(Math.abs(param1Double9 * 4.0D / Math.PI));
      double d2 = Math.cos(param1Double7);
      double d4 = Math.sin(param1Double7);
      param1Double7 = Math.cos(param1Double8);
      double d3 = Math.sin(param1Double8);
      double d1 = -param1Double3;
      double d7 = d1 * d2;
      double d8 = param1Double4 * d4;
      double d6 = d1 * d4;
      double d9 = param1Double4 * d2;
      double d5 = param1Double9 / i;
      d1 = d3 * d6 + param1Double7 * d9;
      param1Double4 = d7 * d3 - d8 * param1Double7;
      byte b = 0;
      param1Double7 = param1Double5;
      param1Double5 = param1Double4;
      d3 = param1Double8;
      param1Double9 = param1Double6;
      param1Double4 = d6;
      param1Double8 = param1Double7;
      param1Double6 = d5;
      param1Double7 = d4;
      while (b < i) {
        d6 = d3 + param1Double6;
        double d10 = Math.sin(d6);
        double d12 = Math.cos(d6);
        double d11 = param1Double1 + param1Double3 * d2 * d12 - d8 * d10;
        d4 = param1Double2 + param1Double3 * param1Double7 * d12 + d9 * d10;
        d5 = d7 * d10 - d8 * d12;
        d10 = d10 * param1Double4 + d12 * d9;
        d3 = d6 - d3;
        d12 = Math.tan(d3 / 2.0D);
        d3 = Math.sin(d3) * (Math.sqrt(d12 * 3.0D * d12 + 4.0D) - 1.0D) / 3.0D;
        param1Path.rLineTo(0.0F, 0.0F);
        param1Path.cubicTo((float)(param1Double8 + param1Double5 * d3), (float)(param1Double9 + d1 * d3), (float)(d11 - d3 * d5), (float)(d4 - d3 * d10), (float)d11, (float)d4);
        b++;
        param1Double8 = d11;
        d3 = d6;
        d1 = d10;
        param1Double5 = d5;
        param1Double9 = d4;
      } 
    }
    
    private static void drawArc(Path param1Path, float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6, float param1Float7, boolean param1Boolean1, boolean param1Boolean2) {
      double d10 = Math.toRadians(param1Float7);
      double d11 = Math.cos(d10);
      double d5 = Math.sin(d10);
      double d6 = param1Float1;
      double d8 = param1Float2;
      double d7 = param1Float5;
      double d1 = (d6 * d11 + d8 * d5) / d7;
      double d2 = -param1Float1;
      double d9 = param1Float6;
      double d12 = (d2 * d5 + d8 * d11) / d9;
      double d3 = param1Float3;
      d2 = param1Float4;
      double d4 = (d3 * d11 + d2 * d5) / d7;
      double d13 = (-param1Float3 * d5 + d2 * d11) / d9;
      double d14 = d1 - d4;
      double d15 = d12 - d13;
      d3 = (d1 + d4) / 2.0D;
      d2 = (d12 + d13) / 2.0D;
      double d17 = d14 * d14 + d15 * d15;
      if (d17 == 0.0D) {
        Log.w("PathParser", " Points are coincident");
        return;
      } 
      double d16 = 1.0D / d17 - 0.25D;
      if (d16 < 0.0D) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Points are too far apart ");
        stringBuilder.append(d17);
        Log.w("PathParser", stringBuilder.toString());
        float f = (float)(Math.sqrt(d17) / 1.99999D);
        drawArc(param1Path, param1Float1, param1Float2, param1Float3, param1Float4, param1Float5 * f, param1Float6 * f, param1Float7, param1Boolean1, param1Boolean2);
        return;
      } 
      d16 = Math.sqrt(d16);
      d14 *= d16;
      d15 = d16 * d15;
      if (param1Boolean1 == param1Boolean2) {
        d3 -= d15;
        d2 += d14;
      } else {
        d3 += d15;
        d2 -= d14;
      } 
      d12 = Math.atan2(d12 - d2, d1 - d3);
      d4 = Math.atan2(d13 - d2, d4 - d3) - d12;
      int i = d4 cmp 0.0D;
      if (i >= 0) {
        param1Boolean1 = true;
      } else {
        param1Boolean1 = false;
      } 
      d1 = d4;
      if (param1Boolean2 != param1Boolean1)
        if (i > 0) {
          d1 = d4 - 6.283185307179586D;
        } else {
          d1 = d4 + 6.283185307179586D;
        }  
      d3 *= d7;
      d2 *= d9;
      arcToBezier(param1Path, d3 * d11 - d2 * d5, d3 * d5 + d2 * d11, d7, d9, d6, d8, d10, d12, d1);
    }
    
    public static void nodesToPath(PathDataNode[] param1ArrayOfPathDataNode, Path param1Path) {
      float[] arrayOfFloat = new float[6];
      char c = 'm';
      for (byte b = 0; b < param1ArrayOfPathDataNode.length; b++) {
        addCommand(param1Path, arrayOfFloat, c, (param1ArrayOfPathDataNode[b]).mType, (param1ArrayOfPathDataNode[b]).mParams);
        c = (param1ArrayOfPathDataNode[b]).mType;
      } 
    }
    
    public void interpolatePathDataNode(PathDataNode param1PathDataNode1, PathDataNode param1PathDataNode2, float param1Float) {
      this.mType = param1PathDataNode1.mType;
      byte b = 0;
      while (true) {
        float[] arrayOfFloat = param1PathDataNode1.mParams;
        if (b < arrayOfFloat.length) {
          this.mParams[b] = arrayOfFloat[b] * (1.0F - param1Float) + param1PathDataNode2.mParams[b] * param1Float;
          b++;
          continue;
        } 
        break;
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\PathParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */