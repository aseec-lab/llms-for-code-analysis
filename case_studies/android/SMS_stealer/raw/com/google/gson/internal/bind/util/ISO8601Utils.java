package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
  private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
  
  private static final String UTC_ID = "UTC";
  
  private static boolean checkOffset(String paramString, int paramInt, char paramChar) {
    boolean bool;
    if (paramInt < paramString.length() && paramString.charAt(paramInt) == paramChar) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static String format(Date paramDate) {
    return format(paramDate, false, TIMEZONE_UTC);
  }
  
  public static String format(Date paramDate, boolean paramBoolean) {
    return format(paramDate, paramBoolean, TIMEZONE_UTC);
  }
  
  public static String format(Date paramDate, boolean paramBoolean, TimeZone paramTimeZone) {
    int i;
    GregorianCalendar gregorianCalendar = new GregorianCalendar(paramTimeZone, Locale.US);
    gregorianCalendar.setTime(paramDate);
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    } 
    if (paramTimeZone.getRawOffset() == 0) {
      j = 1;
    } else {
      j = 6;
    } 
    StringBuilder stringBuilder = new StringBuilder(19 + i + j);
    padInt(stringBuilder, gregorianCalendar.get(1), 4);
    byte b = 45;
    stringBuilder.append('-');
    padInt(stringBuilder, gregorianCalendar.get(2) + 1, 2);
    stringBuilder.append('-');
    padInt(stringBuilder, gregorianCalendar.get(5), 2);
    stringBuilder.append('T');
    padInt(stringBuilder, gregorianCalendar.get(11), 2);
    stringBuilder.append(':');
    padInt(stringBuilder, gregorianCalendar.get(12), 2);
    stringBuilder.append(':');
    padInt(stringBuilder, gregorianCalendar.get(13), 2);
    if (paramBoolean) {
      stringBuilder.append('.');
      padInt(stringBuilder, gregorianCalendar.get(14), 3);
    } 
    int j = paramTimeZone.getOffset(gregorianCalendar.getTimeInMillis());
    if (j != 0) {
      int k = j / 60000;
      i = Math.abs(k / 60);
      k = Math.abs(k % 60);
      if (j >= 0)
        b = 43; 
      stringBuilder.append(b);
      padInt(stringBuilder, i, 2);
      stringBuilder.append(':');
      padInt(stringBuilder, k, 2);
    } else {
      stringBuilder.append('Z');
    } 
    return stringBuilder.toString();
  }
  
  private static int indexOfNonDigit(String paramString, int paramInt) {
    while (paramInt < paramString.length()) {
      char c = paramString.charAt(paramInt);
      if (c < '0' || c > '9')
        return paramInt; 
      paramInt++;
    } 
    return paramString.length();
  }
  
  private static void padInt(StringBuilder paramStringBuilder, int paramInt1, int paramInt2) {
    String str = Integer.toString(paramInt1);
    for (paramInt1 = paramInt2 - str.length(); paramInt1 > 0; paramInt1--)
      paramStringBuilder.append('0'); 
    paramStringBuilder.append(str);
  }
  
  public static Date parse(String paramString, ParsePosition paramParsePosition) throws ParseException {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getIndex : ()I
    //   4: istore_3
    //   5: iload_3
    //   6: iconst_4
    //   7: iadd
    //   8: istore #4
    //   10: aload_0
    //   11: iload_3
    //   12: iload #4
    //   14: invokestatic parseInt : (Ljava/lang/String;II)I
    //   17: istore #9
    //   19: iload #4
    //   21: istore_3
    //   22: aload_0
    //   23: iload #4
    //   25: bipush #45
    //   27: invokestatic checkOffset : (Ljava/lang/String;IC)Z
    //   30: ifeq -> 38
    //   33: iload #4
    //   35: iconst_1
    //   36: iadd
    //   37: istore_3
    //   38: iload_3
    //   39: iconst_2
    //   40: iadd
    //   41: istore #4
    //   43: aload_0
    //   44: iload_3
    //   45: iload #4
    //   47: invokestatic parseInt : (Ljava/lang/String;II)I
    //   50: istore #10
    //   52: iload #4
    //   54: istore_3
    //   55: aload_0
    //   56: iload #4
    //   58: bipush #45
    //   60: invokestatic checkOffset : (Ljava/lang/String;IC)Z
    //   63: ifeq -> 71
    //   66: iload #4
    //   68: iconst_1
    //   69: iadd
    //   70: istore_3
    //   71: iload_3
    //   72: iconst_2
    //   73: iadd
    //   74: istore #6
    //   76: aload_0
    //   77: iload_3
    //   78: iload #6
    //   80: invokestatic parseInt : (Ljava/lang/String;II)I
    //   83: istore #11
    //   85: aload_0
    //   86: iload #6
    //   88: bipush #84
    //   90: invokestatic checkOffset : (Ljava/lang/String;IC)Z
    //   93: istore #13
    //   95: iload #13
    //   97: ifne -> 139
    //   100: aload_0
    //   101: invokevirtual length : ()I
    //   104: iload #6
    //   106: if_icmpgt -> 139
    //   109: new java/util/GregorianCalendar
    //   112: astore #14
    //   114: aload #14
    //   116: iload #9
    //   118: iload #10
    //   120: iconst_1
    //   121: isub
    //   122: iload #11
    //   124: invokespecial <init> : (III)V
    //   127: aload_1
    //   128: iload #6
    //   130: invokevirtual setIndex : (I)V
    //   133: aload #14
    //   135: invokevirtual getTime : ()Ljava/util/Date;
    //   138: areturn
    //   139: iload #13
    //   141: ifeq -> 412
    //   144: iload #6
    //   146: iconst_1
    //   147: iadd
    //   148: istore_3
    //   149: iload_3
    //   150: iconst_2
    //   151: iadd
    //   152: istore #4
    //   154: aload_0
    //   155: iload_3
    //   156: iload #4
    //   158: invokestatic parseInt : (Ljava/lang/String;II)I
    //   161: istore #5
    //   163: iload #4
    //   165: istore_3
    //   166: aload_0
    //   167: iload #4
    //   169: bipush #58
    //   171: invokestatic checkOffset : (Ljava/lang/String;IC)Z
    //   174: ifeq -> 182
    //   177: iload #4
    //   179: iconst_1
    //   180: iadd
    //   181: istore_3
    //   182: iload_3
    //   183: iconst_2
    //   184: iadd
    //   185: istore #4
    //   187: aload_0
    //   188: iload_3
    //   189: iload #4
    //   191: invokestatic parseInt : (Ljava/lang/String;II)I
    //   194: istore #7
    //   196: iload #4
    //   198: istore_3
    //   199: aload_0
    //   200: iload #4
    //   202: bipush #58
    //   204: invokestatic checkOffset : (Ljava/lang/String;IC)Z
    //   207: ifeq -> 215
    //   210: iload #4
    //   212: iconst_1
    //   213: iadd
    //   214: istore_3
    //   215: aload_0
    //   216: invokevirtual length : ()I
    //   219: iload_3
    //   220: if_icmple -> 399
    //   223: aload_0
    //   224: iload_3
    //   225: invokevirtual charAt : (I)C
    //   228: istore #4
    //   230: iload #4
    //   232: bipush #90
    //   234: if_icmpeq -> 399
    //   237: iload #4
    //   239: bipush #43
    //   241: if_icmpeq -> 399
    //   244: iload #4
    //   246: bipush #45
    //   248: if_icmpeq -> 399
    //   251: iload_3
    //   252: iconst_2
    //   253: iadd
    //   254: istore #6
    //   256: aload_0
    //   257: iload_3
    //   258: iload #6
    //   260: invokestatic parseInt : (Ljava/lang/String;II)I
    //   263: istore_3
    //   264: iload_3
    //   265: istore #4
    //   267: iload_3
    //   268: bipush #59
    //   270: if_icmple -> 286
    //   273: iload_3
    //   274: istore #4
    //   276: iload_3
    //   277: bipush #63
    //   279: if_icmpge -> 286
    //   282: bipush #59
    //   284: istore #4
    //   286: aload_0
    //   287: iload #6
    //   289: bipush #46
    //   291: invokestatic checkOffset : (Ljava/lang/String;IC)Z
    //   294: ifeq -> 386
    //   297: iinc #6, 1
    //   300: aload_0
    //   301: iload #6
    //   303: iconst_1
    //   304: iadd
    //   305: invokestatic indexOfNonDigit : (Ljava/lang/String;I)I
    //   308: istore #8
    //   310: iload #8
    //   312: iload #6
    //   314: iconst_3
    //   315: iadd
    //   316: invokestatic min : (II)I
    //   319: istore #12
    //   321: aload_0
    //   322: iload #6
    //   324: iload #12
    //   326: invokestatic parseInt : (Ljava/lang/String;II)I
    //   329: istore_3
    //   330: iload #12
    //   332: iload #6
    //   334: isub
    //   335: istore #6
    //   337: iload #6
    //   339: iconst_1
    //   340: if_icmpeq -> 360
    //   343: iload #6
    //   345: iconst_2
    //   346: if_icmpeq -> 352
    //   349: goto -> 365
    //   352: iload_3
    //   353: bipush #10
    //   355: imul
    //   356: istore_3
    //   357: goto -> 365
    //   360: iload_3
    //   361: bipush #100
    //   363: imul
    //   364: istore_3
    //   365: iload #5
    //   367: istore #6
    //   369: iload_3
    //   370: istore #5
    //   372: iload #6
    //   374: istore_3
    //   375: iload #8
    //   377: istore #6
    //   379: iload #4
    //   381: istore #8
    //   383: goto -> 427
    //   386: iload #5
    //   388: istore_3
    //   389: iconst_0
    //   390: istore #5
    //   392: iload #4
    //   394: istore #8
    //   396: goto -> 427
    //   399: iload_3
    //   400: istore #6
    //   402: iload #5
    //   404: istore_3
    //   405: iload #7
    //   407: istore #4
    //   409: goto -> 417
    //   412: iconst_0
    //   413: istore_3
    //   414: iconst_0
    //   415: istore #4
    //   417: iconst_0
    //   418: istore #5
    //   420: iconst_0
    //   421: istore #8
    //   423: iload #4
    //   425: istore #7
    //   427: aload_0
    //   428: invokevirtual length : ()I
    //   431: iload #6
    //   433: if_icmple -> 854
    //   436: aload_0
    //   437: iload #6
    //   439: invokevirtual charAt : (I)C
    //   442: istore_2
    //   443: iload_2
    //   444: bipush #90
    //   446: if_icmpne -> 463
    //   449: getstatic com/google/gson/internal/bind/util/ISO8601Utils.TIMEZONE_UTC : Ljava/util/TimeZone;
    //   452: astore #14
    //   454: iload #6
    //   456: iconst_1
    //   457: iadd
    //   458: istore #4
    //   460: goto -> 763
    //   463: iload_2
    //   464: bipush #43
    //   466: if_icmpeq -> 529
    //   469: iload_2
    //   470: bipush #45
    //   472: if_icmpne -> 478
    //   475: goto -> 529
    //   478: new java/lang/IndexOutOfBoundsException
    //   481: astore #15
    //   483: new java/lang/StringBuilder
    //   486: astore #14
    //   488: aload #14
    //   490: invokespecial <init> : ()V
    //   493: aload #14
    //   495: ldc 'Invalid time zone indicator ''
    //   497: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   500: pop
    //   501: aload #14
    //   503: iload_2
    //   504: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   507: pop
    //   508: aload #14
    //   510: ldc '''
    //   512: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   515: pop
    //   516: aload #15
    //   518: aload #14
    //   520: invokevirtual toString : ()Ljava/lang/String;
    //   523: invokespecial <init> : (Ljava/lang/String;)V
    //   526: aload #15
    //   528: athrow
    //   529: aload_0
    //   530: iload #6
    //   532: invokevirtual substring : (I)Ljava/lang/String;
    //   535: astore #14
    //   537: aload #14
    //   539: invokevirtual length : ()I
    //   542: iconst_5
    //   543: if_icmplt -> 549
    //   546: goto -> 582
    //   549: new java/lang/StringBuilder
    //   552: astore #15
    //   554: aload #15
    //   556: invokespecial <init> : ()V
    //   559: aload #15
    //   561: aload #14
    //   563: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   566: pop
    //   567: aload #15
    //   569: ldc '00'
    //   571: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   574: pop
    //   575: aload #15
    //   577: invokevirtual toString : ()Ljava/lang/String;
    //   580: astore #14
    //   582: iload #6
    //   584: aload #14
    //   586: invokevirtual length : ()I
    //   589: iadd
    //   590: istore #4
    //   592: ldc '+0000'
    //   594: aload #14
    //   596: invokevirtual equals : (Ljava/lang/Object;)Z
    //   599: ifne -> 758
    //   602: ldc '+00:00'
    //   604: aload #14
    //   606: invokevirtual equals : (Ljava/lang/Object;)Z
    //   609: ifeq -> 615
    //   612: goto -> 758
    //   615: new java/lang/StringBuilder
    //   618: astore #15
    //   620: aload #15
    //   622: invokespecial <init> : ()V
    //   625: aload #15
    //   627: ldc 'GMT'
    //   629: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   632: pop
    //   633: aload #15
    //   635: aload #14
    //   637: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   640: pop
    //   641: aload #15
    //   643: invokevirtual toString : ()Ljava/lang/String;
    //   646: astore #15
    //   648: aload #15
    //   650: invokestatic getTimeZone : (Ljava/lang/String;)Ljava/util/TimeZone;
    //   653: astore #14
    //   655: aload #14
    //   657: invokevirtual getID : ()Ljava/lang/String;
    //   660: astore #16
    //   662: aload #16
    //   664: aload #15
    //   666: invokevirtual equals : (Ljava/lang/Object;)Z
    //   669: ifne -> 755
    //   672: aload #16
    //   674: ldc ':'
    //   676: ldc ''
    //   678: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   681: aload #15
    //   683: invokevirtual equals : (Ljava/lang/Object;)Z
    //   686: ifeq -> 692
    //   689: goto -> 755
    //   692: new java/lang/IndexOutOfBoundsException
    //   695: astore #17
    //   697: new java/lang/StringBuilder
    //   700: astore #16
    //   702: aload #16
    //   704: invokespecial <init> : ()V
    //   707: aload #16
    //   709: ldc 'Mismatching time zone indicator: '
    //   711: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   714: pop
    //   715: aload #16
    //   717: aload #15
    //   719: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   722: pop
    //   723: aload #16
    //   725: ldc ' given, resolves to '
    //   727: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   730: pop
    //   731: aload #16
    //   733: aload #14
    //   735: invokevirtual getID : ()Ljava/lang/String;
    //   738: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   741: pop
    //   742: aload #17
    //   744: aload #16
    //   746: invokevirtual toString : ()Ljava/lang/String;
    //   749: invokespecial <init> : (Ljava/lang/String;)V
    //   752: aload #17
    //   754: athrow
    //   755: goto -> 763
    //   758: getstatic com/google/gson/internal/bind/util/ISO8601Utils.TIMEZONE_UTC : Ljava/util/TimeZone;
    //   761: astore #14
    //   763: new java/util/GregorianCalendar
    //   766: astore #15
    //   768: aload #15
    //   770: aload #14
    //   772: invokespecial <init> : (Ljava/util/TimeZone;)V
    //   775: aload #15
    //   777: iconst_0
    //   778: invokevirtual setLenient : (Z)V
    //   781: aload #15
    //   783: iconst_1
    //   784: iload #9
    //   786: invokevirtual set : (II)V
    //   789: aload #15
    //   791: iconst_2
    //   792: iload #10
    //   794: iconst_1
    //   795: isub
    //   796: invokevirtual set : (II)V
    //   799: aload #15
    //   801: iconst_5
    //   802: iload #11
    //   804: invokevirtual set : (II)V
    //   807: aload #15
    //   809: bipush #11
    //   811: iload_3
    //   812: invokevirtual set : (II)V
    //   815: aload #15
    //   817: bipush #12
    //   819: iload #7
    //   821: invokevirtual set : (II)V
    //   824: aload #15
    //   826: bipush #13
    //   828: iload #8
    //   830: invokevirtual set : (II)V
    //   833: aload #15
    //   835: bipush #14
    //   837: iload #5
    //   839: invokevirtual set : (II)V
    //   842: aload_1
    //   843: iload #4
    //   845: invokevirtual setIndex : (I)V
    //   848: aload #15
    //   850: invokevirtual getTime : ()Ljava/util/Date;
    //   853: areturn
    //   854: new java/lang/IllegalArgumentException
    //   857: astore #14
    //   859: aload #14
    //   861: ldc 'No time zone indicator'
    //   863: invokespecial <init> : (Ljava/lang/String;)V
    //   866: aload #14
    //   868: athrow
    //   869: astore #14
    //   871: goto -> 881
    //   874: astore #14
    //   876: goto -> 881
    //   879: astore #14
    //   881: aload_0
    //   882: ifnonnull -> 890
    //   885: aconst_null
    //   886: astore_0
    //   887: goto -> 928
    //   890: new java/lang/StringBuilder
    //   893: dup
    //   894: invokespecial <init> : ()V
    //   897: astore #15
    //   899: aload #15
    //   901: bipush #34
    //   903: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   906: pop
    //   907: aload #15
    //   909: aload_0
    //   910: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   913: pop
    //   914: aload #15
    //   916: ldc '''
    //   918: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   921: pop
    //   922: aload #15
    //   924: invokevirtual toString : ()Ljava/lang/String;
    //   927: astore_0
    //   928: aload #14
    //   930: invokevirtual getMessage : ()Ljava/lang/String;
    //   933: astore #16
    //   935: aload #16
    //   937: ifnull -> 952
    //   940: aload #16
    //   942: astore #15
    //   944: aload #16
    //   946: invokevirtual isEmpty : ()Z
    //   949: ifeq -> 998
    //   952: new java/lang/StringBuilder
    //   955: dup
    //   956: invokespecial <init> : ()V
    //   959: astore #15
    //   961: aload #15
    //   963: ldc '('
    //   965: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   968: pop
    //   969: aload #15
    //   971: aload #14
    //   973: invokevirtual getClass : ()Ljava/lang/Class;
    //   976: invokevirtual getName : ()Ljava/lang/String;
    //   979: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   982: pop
    //   983: aload #15
    //   985: ldc ')'
    //   987: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   990: pop
    //   991: aload #15
    //   993: invokevirtual toString : ()Ljava/lang/String;
    //   996: astore #15
    //   998: new java/lang/StringBuilder
    //   1001: dup
    //   1002: invokespecial <init> : ()V
    //   1005: astore #16
    //   1007: aload #16
    //   1009: ldc 'Failed to parse date ['
    //   1011: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1014: pop
    //   1015: aload #16
    //   1017: aload_0
    //   1018: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1021: pop
    //   1022: aload #16
    //   1024: ldc ']: '
    //   1026: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1029: pop
    //   1030: aload #16
    //   1032: aload #15
    //   1034: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1037: pop
    //   1038: new java/text/ParseException
    //   1041: dup
    //   1042: aload #16
    //   1044: invokevirtual toString : ()Ljava/lang/String;
    //   1047: aload_1
    //   1048: invokevirtual getIndex : ()I
    //   1051: invokespecial <init> : (Ljava/lang/String;I)V
    //   1054: astore_0
    //   1055: aload_0
    //   1056: aload #14
    //   1058: invokevirtual initCause : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   1061: pop
    //   1062: aload_0
    //   1063: athrow
    // Exception table:
    //   from	to	target	type
    //   0	5	879	java/lang/IndexOutOfBoundsException
    //   0	5	874	java/lang/NumberFormatException
    //   0	5	869	java/lang/IllegalArgumentException
    //   10	19	879	java/lang/IndexOutOfBoundsException
    //   10	19	874	java/lang/NumberFormatException
    //   10	19	869	java/lang/IllegalArgumentException
    //   22	33	879	java/lang/IndexOutOfBoundsException
    //   22	33	874	java/lang/NumberFormatException
    //   22	33	869	java/lang/IllegalArgumentException
    //   43	52	879	java/lang/IndexOutOfBoundsException
    //   43	52	874	java/lang/NumberFormatException
    //   43	52	869	java/lang/IllegalArgumentException
    //   55	66	879	java/lang/IndexOutOfBoundsException
    //   55	66	874	java/lang/NumberFormatException
    //   55	66	869	java/lang/IllegalArgumentException
    //   76	95	879	java/lang/IndexOutOfBoundsException
    //   76	95	874	java/lang/NumberFormatException
    //   76	95	869	java/lang/IllegalArgumentException
    //   100	139	879	java/lang/IndexOutOfBoundsException
    //   100	139	874	java/lang/NumberFormatException
    //   100	139	869	java/lang/IllegalArgumentException
    //   154	163	879	java/lang/IndexOutOfBoundsException
    //   154	163	874	java/lang/NumberFormatException
    //   154	163	869	java/lang/IllegalArgumentException
    //   166	177	879	java/lang/IndexOutOfBoundsException
    //   166	177	874	java/lang/NumberFormatException
    //   166	177	869	java/lang/IllegalArgumentException
    //   187	196	879	java/lang/IndexOutOfBoundsException
    //   187	196	874	java/lang/NumberFormatException
    //   187	196	869	java/lang/IllegalArgumentException
    //   199	210	879	java/lang/IndexOutOfBoundsException
    //   199	210	874	java/lang/NumberFormatException
    //   199	210	869	java/lang/IllegalArgumentException
    //   215	230	879	java/lang/IndexOutOfBoundsException
    //   215	230	874	java/lang/NumberFormatException
    //   215	230	869	java/lang/IllegalArgumentException
    //   256	264	879	java/lang/IndexOutOfBoundsException
    //   256	264	874	java/lang/NumberFormatException
    //   256	264	869	java/lang/IllegalArgumentException
    //   286	297	879	java/lang/IndexOutOfBoundsException
    //   286	297	874	java/lang/NumberFormatException
    //   286	297	869	java/lang/IllegalArgumentException
    //   300	330	879	java/lang/IndexOutOfBoundsException
    //   300	330	874	java/lang/NumberFormatException
    //   300	330	869	java/lang/IllegalArgumentException
    //   427	443	879	java/lang/IndexOutOfBoundsException
    //   427	443	874	java/lang/NumberFormatException
    //   427	443	869	java/lang/IllegalArgumentException
    //   449	454	879	java/lang/IndexOutOfBoundsException
    //   449	454	874	java/lang/NumberFormatException
    //   449	454	869	java/lang/IllegalArgumentException
    //   478	529	879	java/lang/IndexOutOfBoundsException
    //   478	529	874	java/lang/NumberFormatException
    //   478	529	869	java/lang/IllegalArgumentException
    //   529	546	879	java/lang/IndexOutOfBoundsException
    //   529	546	874	java/lang/NumberFormatException
    //   529	546	869	java/lang/IllegalArgumentException
    //   549	582	879	java/lang/IndexOutOfBoundsException
    //   549	582	874	java/lang/NumberFormatException
    //   549	582	869	java/lang/IllegalArgumentException
    //   582	612	879	java/lang/IndexOutOfBoundsException
    //   582	612	874	java/lang/NumberFormatException
    //   582	612	869	java/lang/IllegalArgumentException
    //   615	689	879	java/lang/IndexOutOfBoundsException
    //   615	689	874	java/lang/NumberFormatException
    //   615	689	869	java/lang/IllegalArgumentException
    //   692	755	879	java/lang/IndexOutOfBoundsException
    //   692	755	874	java/lang/NumberFormatException
    //   692	755	869	java/lang/IllegalArgumentException
    //   758	763	879	java/lang/IndexOutOfBoundsException
    //   758	763	874	java/lang/NumberFormatException
    //   758	763	869	java/lang/IllegalArgumentException
    //   763	854	879	java/lang/IndexOutOfBoundsException
    //   763	854	874	java/lang/NumberFormatException
    //   763	854	869	java/lang/IllegalArgumentException
    //   854	869	879	java/lang/IndexOutOfBoundsException
    //   854	869	874	java/lang/NumberFormatException
    //   854	869	869	java/lang/IllegalArgumentException
  }
  
  private static int parseInt(String paramString, int paramInt1, int paramInt2) throws NumberFormatException {
    if (paramInt1 >= 0 && paramInt2 <= paramString.length() && paramInt1 <= paramInt2) {
      int i;
      int j;
      if (paramInt1 < paramInt2) {
        i = paramInt1 + 1;
        j = Character.digit(paramString.charAt(paramInt1), 10);
        if (j >= 0) {
          j = -j;
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Invalid number: ");
          stringBuilder.append(paramString.substring(paramInt1, paramInt2));
          throw new NumberFormatException(stringBuilder.toString());
        } 
      } else {
        i = paramInt1;
        j = 0;
      } 
      while (i < paramInt2) {
        int k = Character.digit(paramString.charAt(i), 10);
        if (k >= 0) {
          j = j * 10 - k;
          i++;
          continue;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid number: ");
        stringBuilder.append(paramString.substring(paramInt1, paramInt2));
        throw new NumberFormatException(stringBuilder.toString());
      } 
      return -j;
    } 
    throw new NumberFormatException(paramString);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bin\\util\ISO8601Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */