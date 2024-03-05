package okhttp3;

import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public final class CacheControl {
  public static final CacheControl FORCE_CACHE;
  
  public static final CacheControl FORCE_NETWORK = (new Builder()).noCache().build();
  
  @Nullable
  String headerValue;
  
  private final boolean immutable;
  
  private final boolean isPrivate;
  
  private final boolean isPublic;
  
  private final int maxAgeSeconds;
  
  private final int maxStaleSeconds;
  
  private final int minFreshSeconds;
  
  private final boolean mustRevalidate;
  
  private final boolean noCache;
  
  private final boolean noStore;
  
  private final boolean noTransform;
  
  private final boolean onlyIfCached;
  
  private final int sMaxAgeSeconds;
  
  static {
    FORCE_CACHE = (new Builder()).onlyIfCached().maxStale(2147483647, TimeUnit.SECONDS).build();
  }
  
  CacheControl(Builder paramBuilder) {
    this.noCache = paramBuilder.noCache;
    this.noStore = paramBuilder.noStore;
    this.maxAgeSeconds = paramBuilder.maxAgeSeconds;
    this.sMaxAgeSeconds = -1;
    this.isPrivate = false;
    this.isPublic = false;
    this.mustRevalidate = false;
    this.maxStaleSeconds = paramBuilder.maxStaleSeconds;
    this.minFreshSeconds = paramBuilder.minFreshSeconds;
    this.onlyIfCached = paramBuilder.onlyIfCached;
    this.noTransform = paramBuilder.noTransform;
    this.immutable = paramBuilder.immutable;
  }
  
  private CacheControl(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt3, int paramInt4, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, @Nullable String paramString) {
    this.noCache = paramBoolean1;
    this.noStore = paramBoolean2;
    this.maxAgeSeconds = paramInt1;
    this.sMaxAgeSeconds = paramInt2;
    this.isPrivate = paramBoolean3;
    this.isPublic = paramBoolean4;
    this.mustRevalidate = paramBoolean5;
    this.maxStaleSeconds = paramInt3;
    this.minFreshSeconds = paramInt4;
    this.onlyIfCached = paramBoolean6;
    this.noTransform = paramBoolean7;
    this.immutable = paramBoolean8;
    this.headerValue = paramString;
  }
  
  private String headerValue() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.noCache)
      stringBuilder.append("no-cache, "); 
    if (this.noStore)
      stringBuilder.append("no-store, "); 
    if (this.maxAgeSeconds != -1) {
      stringBuilder.append("max-age=");
      stringBuilder.append(this.maxAgeSeconds);
      stringBuilder.append(", ");
    } 
    if (this.sMaxAgeSeconds != -1) {
      stringBuilder.append("s-maxage=");
      stringBuilder.append(this.sMaxAgeSeconds);
      stringBuilder.append(", ");
    } 
    if (this.isPrivate)
      stringBuilder.append("private, "); 
    if (this.isPublic)
      stringBuilder.append("public, "); 
    if (this.mustRevalidate)
      stringBuilder.append("must-revalidate, "); 
    if (this.maxStaleSeconds != -1) {
      stringBuilder.append("max-stale=");
      stringBuilder.append(this.maxStaleSeconds);
      stringBuilder.append(", ");
    } 
    if (this.minFreshSeconds != -1) {
      stringBuilder.append("min-fresh=");
      stringBuilder.append(this.minFreshSeconds);
      stringBuilder.append(", ");
    } 
    if (this.onlyIfCached)
      stringBuilder.append("only-if-cached, "); 
    if (this.noTransform)
      stringBuilder.append("no-transform, "); 
    if (this.immutable)
      stringBuilder.append("immutable, "); 
    if (stringBuilder.length() == 0)
      return ""; 
    stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    return stringBuilder.toString();
  }
  
  public static CacheControl parse(Headers paramHeaders) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual size : ()I
    //   4: istore #13
    //   6: iconst_0
    //   7: istore #7
    //   9: iconst_1
    //   10: istore_2
    //   11: aconst_null
    //   12: astore #30
    //   14: iconst_0
    //   15: istore #21
    //   17: iconst_0
    //   18: istore #20
    //   20: iconst_m1
    //   21: istore #6
    //   23: iconst_m1
    //   24: istore #5
    //   26: iconst_0
    //   27: istore #19
    //   29: iconst_0
    //   30: istore #18
    //   32: iconst_0
    //   33: istore #17
    //   35: iconst_m1
    //   36: istore #4
    //   38: iconst_m1
    //   39: istore_3
    //   40: iconst_0
    //   41: istore #16
    //   43: iconst_0
    //   44: istore #14
    //   46: iconst_0
    //   47: istore #15
    //   49: iload #7
    //   51: iload #13
    //   53: if_icmpge -> 1216
    //   56: aload_0
    //   57: iload #7
    //   59: invokevirtual name : (I)Ljava/lang/String;
    //   62: astore #33
    //   64: aload_0
    //   65: iload #7
    //   67: invokevirtual value : (I)Ljava/lang/String;
    //   70: astore #32
    //   72: aload #33
    //   74: ldc 'Cache-Control'
    //   76: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   79: ifeq -> 97
    //   82: aload #30
    //   84: ifnull -> 90
    //   87: goto -> 160
    //   90: aload #32
    //   92: astore #30
    //   94: goto -> 162
    //   97: iload_2
    //   98: istore #11
    //   100: aload #30
    //   102: astore #31
    //   104: iload #21
    //   106: istore #23
    //   108: iload #20
    //   110: istore #24
    //   112: iload #6
    //   114: istore #9
    //   116: iload #5
    //   118: istore #10
    //   120: iload #19
    //   122: istore #28
    //   124: iload #18
    //   126: istore #25
    //   128: iload #17
    //   130: istore #27
    //   132: iload #4
    //   134: istore_1
    //   135: iload_3
    //   136: istore #8
    //   138: iload #16
    //   140: istore #29
    //   142: iload #14
    //   144: istore #26
    //   146: iload #15
    //   148: istore #22
    //   150: aload #33
    //   152: ldc 'Pragma'
    //   154: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   157: ifeq -> 1157
    //   160: iconst_0
    //   161: istore_2
    //   162: iconst_0
    //   163: istore #12
    //   165: iload_2
    //   166: istore #11
    //   168: aload #30
    //   170: astore #31
    //   172: iload #21
    //   174: istore #23
    //   176: iload #20
    //   178: istore #24
    //   180: iload #6
    //   182: istore #9
    //   184: iload #5
    //   186: istore #10
    //   188: iload #19
    //   190: istore #28
    //   192: iload #18
    //   194: istore #25
    //   196: iload #17
    //   198: istore #27
    //   200: iload #4
    //   202: istore_1
    //   203: iload_3
    //   204: istore #8
    //   206: iload #16
    //   208: istore #29
    //   210: iload #14
    //   212: istore #26
    //   214: iload #15
    //   216: istore #22
    //   218: iload #12
    //   220: aload #32
    //   222: invokevirtual length : ()I
    //   225: if_icmpge -> 1157
    //   228: aload #32
    //   230: iload #12
    //   232: ldc '=,;'
    //   234: invokestatic skipUntil : (Ljava/lang/String;ILjava/lang/String;)I
    //   237: istore_1
    //   238: aload #32
    //   240: iload #12
    //   242: iload_1
    //   243: invokevirtual substring : (II)Ljava/lang/String;
    //   246: invokevirtual trim : ()Ljava/lang/String;
    //   249: astore #33
    //   251: iload_1
    //   252: aload #32
    //   254: invokevirtual length : ()I
    //   257: if_icmpeq -> 376
    //   260: aload #32
    //   262: iload_1
    //   263: invokevirtual charAt : (I)C
    //   266: bipush #44
    //   268: if_icmpeq -> 376
    //   271: aload #32
    //   273: iload_1
    //   274: invokevirtual charAt : (I)C
    //   277: bipush #59
    //   279: if_icmpne -> 285
    //   282: goto -> 376
    //   285: aload #32
    //   287: iload_1
    //   288: iconst_1
    //   289: iadd
    //   290: invokestatic skipWhitespace : (Ljava/lang/String;I)I
    //   293: istore #8
    //   295: iload #8
    //   297: aload #32
    //   299: invokevirtual length : ()I
    //   302: if_icmpge -> 350
    //   305: aload #32
    //   307: iload #8
    //   309: invokevirtual charAt : (I)C
    //   312: bipush #34
    //   314: if_icmpne -> 350
    //   317: iload #8
    //   319: iconst_1
    //   320: iadd
    //   321: istore_1
    //   322: aload #32
    //   324: iload_1
    //   325: ldc '"'
    //   327: invokestatic skipUntil : (Ljava/lang/String;ILjava/lang/String;)I
    //   330: istore #8
    //   332: aload #32
    //   334: iload_1
    //   335: iload #8
    //   337: invokevirtual substring : (II)Ljava/lang/String;
    //   340: astore #31
    //   342: iload #8
    //   344: iconst_1
    //   345: iadd
    //   346: istore_1
    //   347: goto -> 382
    //   350: aload #32
    //   352: iload #8
    //   354: ldc ',;'
    //   356: invokestatic skipUntil : (Ljava/lang/String;ILjava/lang/String;)I
    //   359: istore_1
    //   360: aload #32
    //   362: iload #8
    //   364: iload_1
    //   365: invokevirtual substring : (II)Ljava/lang/String;
    //   368: invokevirtual trim : ()Ljava/lang/String;
    //   371: astore #31
    //   373: goto -> 382
    //   376: iinc #1, 1
    //   379: aconst_null
    //   380: astore #31
    //   382: ldc 'no-cache'
    //   384: aload #33
    //   386: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   389: ifeq -> 437
    //   392: iconst_1
    //   393: istore #22
    //   395: iload #20
    //   397: istore #23
    //   399: iload #6
    //   401: istore #8
    //   403: iload #5
    //   405: istore #9
    //   407: iload #19
    //   409: istore #24
    //   411: iload #18
    //   413: istore #25
    //   415: iload #17
    //   417: istore #26
    //   419: iload #4
    //   421: istore #10
    //   423: iload_3
    //   424: istore #11
    //   426: iload #16
    //   428: istore #27
    //   430: iload #14
    //   432: istore #28
    //   434: goto -> 1108
    //   437: ldc 'no-store'
    //   439: aload #33
    //   441: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   444: ifeq -> 492
    //   447: iconst_1
    //   448: istore #23
    //   450: iload #21
    //   452: istore #22
    //   454: iload #6
    //   456: istore #8
    //   458: iload #5
    //   460: istore #9
    //   462: iload #19
    //   464: istore #24
    //   466: iload #18
    //   468: istore #25
    //   470: iload #17
    //   472: istore #26
    //   474: iload #4
    //   476: istore #10
    //   478: iload_3
    //   479: istore #11
    //   481: iload #16
    //   483: istore #27
    //   485: iload #14
    //   487: istore #28
    //   489: goto -> 1108
    //   492: ldc 'max-age'
    //   494: aload #33
    //   496: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   499: ifeq -> 552
    //   502: aload #31
    //   504: iconst_m1
    //   505: invokestatic parseSeconds : (Ljava/lang/String;I)I
    //   508: istore #8
    //   510: iload #21
    //   512: istore #22
    //   514: iload #20
    //   516: istore #23
    //   518: iload #5
    //   520: istore #9
    //   522: iload #19
    //   524: istore #24
    //   526: iload #18
    //   528: istore #25
    //   530: iload #17
    //   532: istore #26
    //   534: iload #4
    //   536: istore #10
    //   538: iload_3
    //   539: istore #11
    //   541: iload #16
    //   543: istore #27
    //   545: iload #14
    //   547: istore #28
    //   549: goto -> 1108
    //   552: ldc 's-maxage'
    //   554: aload #33
    //   556: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   559: ifeq -> 612
    //   562: aload #31
    //   564: iconst_m1
    //   565: invokestatic parseSeconds : (Ljava/lang/String;I)I
    //   568: istore #9
    //   570: iload #21
    //   572: istore #22
    //   574: iload #20
    //   576: istore #23
    //   578: iload #6
    //   580: istore #8
    //   582: iload #19
    //   584: istore #24
    //   586: iload #18
    //   588: istore #25
    //   590: iload #17
    //   592: istore #26
    //   594: iload #4
    //   596: istore #10
    //   598: iload_3
    //   599: istore #11
    //   601: iload #16
    //   603: istore #27
    //   605: iload #14
    //   607: istore #28
    //   609: goto -> 1108
    //   612: ldc 'private'
    //   614: aload #33
    //   616: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   619: ifeq -> 667
    //   622: iconst_1
    //   623: istore #24
    //   625: iload #21
    //   627: istore #22
    //   629: iload #20
    //   631: istore #23
    //   633: iload #6
    //   635: istore #8
    //   637: iload #5
    //   639: istore #9
    //   641: iload #18
    //   643: istore #25
    //   645: iload #17
    //   647: istore #26
    //   649: iload #4
    //   651: istore #10
    //   653: iload_3
    //   654: istore #11
    //   656: iload #16
    //   658: istore #27
    //   660: iload #14
    //   662: istore #28
    //   664: goto -> 1108
    //   667: ldc 'public'
    //   669: aload #33
    //   671: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   674: ifeq -> 722
    //   677: iconst_1
    //   678: istore #25
    //   680: iload #21
    //   682: istore #22
    //   684: iload #20
    //   686: istore #23
    //   688: iload #6
    //   690: istore #8
    //   692: iload #5
    //   694: istore #9
    //   696: iload #19
    //   698: istore #24
    //   700: iload #17
    //   702: istore #26
    //   704: iload #4
    //   706: istore #10
    //   708: iload_3
    //   709: istore #11
    //   711: iload #16
    //   713: istore #27
    //   715: iload #14
    //   717: istore #28
    //   719: goto -> 1108
    //   722: ldc 'must-revalidate'
    //   724: aload #33
    //   726: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   729: ifeq -> 777
    //   732: iconst_1
    //   733: istore #26
    //   735: iload #21
    //   737: istore #22
    //   739: iload #20
    //   741: istore #23
    //   743: iload #6
    //   745: istore #8
    //   747: iload #5
    //   749: istore #9
    //   751: iload #19
    //   753: istore #24
    //   755: iload #18
    //   757: istore #25
    //   759: iload #4
    //   761: istore #10
    //   763: iload_3
    //   764: istore #11
    //   766: iload #16
    //   768: istore #27
    //   770: iload #14
    //   772: istore #28
    //   774: goto -> 1108
    //   777: ldc 'max-stale'
    //   779: aload #33
    //   781: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   784: ifeq -> 838
    //   787: aload #31
    //   789: ldc 2147483647
    //   791: invokestatic parseSeconds : (Ljava/lang/String;I)I
    //   794: istore #10
    //   796: iload #21
    //   798: istore #22
    //   800: iload #20
    //   802: istore #23
    //   804: iload #6
    //   806: istore #8
    //   808: iload #5
    //   810: istore #9
    //   812: iload #19
    //   814: istore #24
    //   816: iload #18
    //   818: istore #25
    //   820: iload #17
    //   822: istore #26
    //   824: iload_3
    //   825: istore #11
    //   827: iload #16
    //   829: istore #27
    //   831: iload #14
    //   833: istore #28
    //   835: goto -> 1108
    //   838: ldc 'min-fresh'
    //   840: aload #33
    //   842: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   845: ifeq -> 899
    //   848: aload #31
    //   850: iconst_m1
    //   851: invokestatic parseSeconds : (Ljava/lang/String;I)I
    //   854: istore #11
    //   856: iload #21
    //   858: istore #22
    //   860: iload #20
    //   862: istore #23
    //   864: iload #6
    //   866: istore #8
    //   868: iload #5
    //   870: istore #9
    //   872: iload #19
    //   874: istore #24
    //   876: iload #18
    //   878: istore #25
    //   880: iload #17
    //   882: istore #26
    //   884: iload #4
    //   886: istore #10
    //   888: iload #16
    //   890: istore #27
    //   892: iload #14
    //   894: istore #28
    //   896: goto -> 1108
    //   899: ldc 'only-if-cached'
    //   901: aload #33
    //   903: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   906: ifeq -> 954
    //   909: iconst_1
    //   910: istore #27
    //   912: iload #21
    //   914: istore #22
    //   916: iload #20
    //   918: istore #23
    //   920: iload #6
    //   922: istore #8
    //   924: iload #5
    //   926: istore #9
    //   928: iload #19
    //   930: istore #24
    //   932: iload #18
    //   934: istore #25
    //   936: iload #17
    //   938: istore #26
    //   940: iload #4
    //   942: istore #10
    //   944: iload_3
    //   945: istore #11
    //   947: iload #14
    //   949: istore #28
    //   951: goto -> 1108
    //   954: ldc 'no-transform'
    //   956: aload #33
    //   958: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   961: ifeq -> 1009
    //   964: iconst_1
    //   965: istore #28
    //   967: iload #21
    //   969: istore #22
    //   971: iload #20
    //   973: istore #23
    //   975: iload #6
    //   977: istore #8
    //   979: iload #5
    //   981: istore #9
    //   983: iload #19
    //   985: istore #24
    //   987: iload #18
    //   989: istore #25
    //   991: iload #17
    //   993: istore #26
    //   995: iload #4
    //   997: istore #10
    //   999: iload_3
    //   1000: istore #11
    //   1002: iload #16
    //   1004: istore #27
    //   1006: goto -> 1108
    //   1009: iload #21
    //   1011: istore #22
    //   1013: iload #20
    //   1015: istore #23
    //   1017: iload #6
    //   1019: istore #8
    //   1021: iload #5
    //   1023: istore #9
    //   1025: iload #19
    //   1027: istore #24
    //   1029: iload #18
    //   1031: istore #25
    //   1033: iload #17
    //   1035: istore #26
    //   1037: iload #4
    //   1039: istore #10
    //   1041: iload_3
    //   1042: istore #11
    //   1044: iload #16
    //   1046: istore #27
    //   1048: iload #14
    //   1050: istore #28
    //   1052: ldc 'immutable'
    //   1054: aload #33
    //   1056: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   1059: ifeq -> 1108
    //   1062: iconst_1
    //   1063: istore #15
    //   1065: iload #14
    //   1067: istore #28
    //   1069: iload #16
    //   1071: istore #27
    //   1073: iload_3
    //   1074: istore #11
    //   1076: iload #4
    //   1078: istore #10
    //   1080: iload #17
    //   1082: istore #26
    //   1084: iload #18
    //   1086: istore #25
    //   1088: iload #19
    //   1090: istore #24
    //   1092: iload #5
    //   1094: istore #9
    //   1096: iload #6
    //   1098: istore #8
    //   1100: iload #20
    //   1102: istore #23
    //   1104: iload #21
    //   1106: istore #22
    //   1108: iload_1
    //   1109: istore #12
    //   1111: iload #22
    //   1113: istore #21
    //   1115: iload #23
    //   1117: istore #20
    //   1119: iload #8
    //   1121: istore #6
    //   1123: iload #9
    //   1125: istore #5
    //   1127: iload #24
    //   1129: istore #19
    //   1131: iload #25
    //   1133: istore #18
    //   1135: iload #26
    //   1137: istore #17
    //   1139: iload #10
    //   1141: istore #4
    //   1143: iload #11
    //   1145: istore_3
    //   1146: iload #27
    //   1148: istore #16
    //   1150: iload #28
    //   1152: istore #14
    //   1154: goto -> 165
    //   1157: iinc #7, 1
    //   1160: iload #11
    //   1162: istore_2
    //   1163: aload #31
    //   1165: astore #30
    //   1167: iload #23
    //   1169: istore #21
    //   1171: iload #24
    //   1173: istore #20
    //   1175: iload #9
    //   1177: istore #6
    //   1179: iload #10
    //   1181: istore #5
    //   1183: iload #28
    //   1185: istore #19
    //   1187: iload #25
    //   1189: istore #18
    //   1191: iload #27
    //   1193: istore #17
    //   1195: iload_1
    //   1196: istore #4
    //   1198: iload #8
    //   1200: istore_3
    //   1201: iload #29
    //   1203: istore #16
    //   1205: iload #26
    //   1207: istore #14
    //   1209: iload #22
    //   1211: istore #15
    //   1213: goto -> 49
    //   1216: iload_2
    //   1217: ifne -> 1225
    //   1220: aconst_null
    //   1221: astore_0
    //   1222: goto -> 1228
    //   1225: aload #30
    //   1227: astore_0
    //   1228: new okhttp3/CacheControl
    //   1231: dup
    //   1232: iload #21
    //   1234: iload #20
    //   1236: iload #6
    //   1238: iload #5
    //   1240: iload #19
    //   1242: iload #18
    //   1244: iload #17
    //   1246: iload #4
    //   1248: iload_3
    //   1249: iload #16
    //   1251: iload #14
    //   1253: iload #15
    //   1255: aload_0
    //   1256: invokespecial <init> : (ZZIIZZZIIZZZLjava/lang/String;)V
    //   1259: areturn
  }
  
  public boolean immutable() {
    return this.immutable;
  }
  
  public boolean isPrivate() {
    return this.isPrivate;
  }
  
  public boolean isPublic() {
    return this.isPublic;
  }
  
  public int maxAgeSeconds() {
    return this.maxAgeSeconds;
  }
  
  public int maxStaleSeconds() {
    return this.maxStaleSeconds;
  }
  
  public int minFreshSeconds() {
    return this.minFreshSeconds;
  }
  
  public boolean mustRevalidate() {
    return this.mustRevalidate;
  }
  
  public boolean noCache() {
    return this.noCache;
  }
  
  public boolean noStore() {
    return this.noStore;
  }
  
  public boolean noTransform() {
    return this.noTransform;
  }
  
  public boolean onlyIfCached() {
    return this.onlyIfCached;
  }
  
  public int sMaxAgeSeconds() {
    return this.sMaxAgeSeconds;
  }
  
  public String toString() {
    String str = this.headerValue;
    if (str == null) {
      str = headerValue();
      this.headerValue = str;
    } 
    return str;
  }
  
  public static final class Builder {
    boolean immutable;
    
    int maxAgeSeconds = -1;
    
    int maxStaleSeconds = -1;
    
    int minFreshSeconds = -1;
    
    boolean noCache;
    
    boolean noStore;
    
    boolean noTransform;
    
    boolean onlyIfCached;
    
    public CacheControl build() {
      return new CacheControl(this);
    }
    
    public Builder immutable() {
      this.immutable = true;
      return this;
    }
    
    public Builder maxAge(int param1Int, TimeUnit param1TimeUnit) {
      if (param1Int >= 0) {
        long l = param1TimeUnit.toSeconds(param1Int);
        if (l > 2147483647L) {
          param1Int = Integer.MAX_VALUE;
        } else {
          param1Int = (int)l;
        } 
        this.maxAgeSeconds = param1Int;
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("maxAge < 0: ");
      stringBuilder.append(param1Int);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder maxStale(int param1Int, TimeUnit param1TimeUnit) {
      if (param1Int >= 0) {
        long l = param1TimeUnit.toSeconds(param1Int);
        if (l > 2147483647L) {
          param1Int = Integer.MAX_VALUE;
        } else {
          param1Int = (int)l;
        } 
        this.maxStaleSeconds = param1Int;
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("maxStale < 0: ");
      stringBuilder.append(param1Int);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder minFresh(int param1Int, TimeUnit param1TimeUnit) {
      if (param1Int >= 0) {
        long l = param1TimeUnit.toSeconds(param1Int);
        if (l > 2147483647L) {
          param1Int = Integer.MAX_VALUE;
        } else {
          param1Int = (int)l;
        } 
        this.minFreshSeconds = param1Int;
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("minFresh < 0: ");
      stringBuilder.append(param1Int);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder noCache() {
      this.noCache = true;
      return this;
    }
    
    public Builder noStore() {
      this.noStore = true;
      return this;
    }
    
    public Builder noTransform() {
      this.noTransform = true;
      return this;
    }
    
    public Builder onlyIfCached() {
      this.onlyIfCached = true;
      return this;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\CacheControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */