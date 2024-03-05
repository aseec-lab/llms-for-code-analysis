package okhttp3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;

public final class Cookie {
  private static final Pattern DAY_OF_MONTH_PATTERN;
  
  private static final Pattern MONTH_PATTERN;
  
  private static final Pattern TIME_PATTERN;
  
  private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
  
  private final String domain;
  
  private final long expiresAt;
  
  private final boolean hostOnly;
  
  private final boolean httpOnly;
  
  private final String name;
  
  private final String path;
  
  private final boolean persistent;
  
  private final boolean secure;
  
  private final String value;
  
  static {
    MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
    DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
    TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
  }
  
  private Cookie(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    this.name = paramString1;
    this.value = paramString2;
    this.expiresAt = paramLong;
    this.domain = paramString3;
    this.path = paramString4;
    this.secure = paramBoolean1;
    this.httpOnly = paramBoolean2;
    this.hostOnly = paramBoolean3;
    this.persistent = paramBoolean4;
  }
  
  Cookie(Builder paramBuilder) {
    if (paramBuilder.name != null) {
      if (paramBuilder.value != null) {
        if (paramBuilder.domain != null) {
          this.name = paramBuilder.name;
          this.value = paramBuilder.value;
          this.expiresAt = paramBuilder.expiresAt;
          this.domain = paramBuilder.domain;
          this.path = paramBuilder.path;
          this.secure = paramBuilder.secure;
          this.httpOnly = paramBuilder.httpOnly;
          this.persistent = paramBuilder.persistent;
          this.hostOnly = paramBuilder.hostOnly;
          return;
        } 
        throw new NullPointerException("builder.domain == null");
      } 
      throw new NullPointerException("builder.value == null");
    } 
    throw new NullPointerException("builder.name == null");
  }
  
  private static int dateCharacterOffset(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
    while (paramInt1 < paramInt2) {
      char c = paramString.charAt(paramInt1);
      if ((c < ' ' && c != '\t') || c >= '' || (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ':') {
        c = '\001';
      } else {
        c = Character.MIN_VALUE;
      } 
      if (c == (paramBoolean ^ true))
        return paramInt1; 
      paramInt1++;
    } 
    return paramInt2;
  }
  
  private static boolean domainMatch(String paramString1, String paramString2) {
    return paramString1.equals(paramString2) ? true : ((paramString1.endsWith(paramString2) && paramString1.charAt(paramString1.length() - paramString2.length() - 1) == '.' && !Util.verifyAsIpAddress(paramString1)));
  }
  
  @Nullable
  static Cookie parse(long paramLong, HttpUrl paramHttpUrl, String paramString) {
    // Byte code:
    //   0: aload_3
    //   1: invokevirtual length : ()I
    //   4: istore #5
    //   6: aload_3
    //   7: iconst_0
    //   8: iload #5
    //   10: bipush #59
    //   12: invokestatic delimiterOffset : (Ljava/lang/String;IIC)I
    //   15: istore #4
    //   17: aload_3
    //   18: iconst_0
    //   19: iload #4
    //   21: bipush #61
    //   23: invokestatic delimiterOffset : (Ljava/lang/String;IIC)I
    //   26: istore #6
    //   28: iload #6
    //   30: iload #4
    //   32: if_icmpne -> 37
    //   35: aconst_null
    //   36: areturn
    //   37: aload_3
    //   38: iconst_0
    //   39: iload #6
    //   41: invokestatic trimSubstring : (Ljava/lang/String;II)Ljava/lang/String;
    //   44: astore #28
    //   46: aload #28
    //   48: invokevirtual isEmpty : ()Z
    //   51: ifne -> 722
    //   54: aload #28
    //   56: invokestatic indexOfControlOrNonAscii : (Ljava/lang/String;)I
    //   59: iconst_m1
    //   60: if_icmpeq -> 66
    //   63: goto -> 722
    //   66: aload_3
    //   67: iload #6
    //   69: iconst_1
    //   70: iadd
    //   71: iload #4
    //   73: invokestatic trimSubstring : (Ljava/lang/String;II)Ljava/lang/String;
    //   76: astore #27
    //   78: aload #27
    //   80: invokestatic indexOfControlOrNonAscii : (Ljava/lang/String;)I
    //   83: iconst_m1
    //   84: if_icmpeq -> 89
    //   87: aconst_null
    //   88: areturn
    //   89: iinc #4, 1
    //   92: aconst_null
    //   93: astore #23
    //   95: aconst_null
    //   96: astore #24
    //   98: ldc2_w -1
    //   101: lstore #17
    //   103: ldc2_w 253402300799999
    //   106: lstore #15
    //   108: iconst_0
    //   109: istore #11
    //   111: iconst_0
    //   112: istore #10
    //   114: iconst_1
    //   115: istore #9
    //   117: iconst_0
    //   118: istore #8
    //   120: iload #4
    //   122: iload #5
    //   124: if_icmpge -> 494
    //   127: aload_3
    //   128: iload #4
    //   130: iload #5
    //   132: bipush #59
    //   134: invokestatic delimiterOffset : (Ljava/lang/String;IIC)I
    //   137: istore #6
    //   139: aload_3
    //   140: iload #4
    //   142: iload #6
    //   144: bipush #61
    //   146: invokestatic delimiterOffset : (Ljava/lang/String;IIC)I
    //   149: istore #7
    //   151: aload_3
    //   152: iload #4
    //   154: iload #7
    //   156: invokestatic trimSubstring : (Ljava/lang/String;II)Ljava/lang/String;
    //   159: astore #29
    //   161: iload #7
    //   163: iload #6
    //   165: if_icmpge -> 183
    //   168: aload_3
    //   169: iload #7
    //   171: iconst_1
    //   172: iadd
    //   173: iload #6
    //   175: invokestatic trimSubstring : (Ljava/lang/String;II)Ljava/lang/String;
    //   178: astore #25
    //   180: goto -> 187
    //   183: ldc ''
    //   185: astore #25
    //   187: aload #29
    //   189: ldc 'expires'
    //   191: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   194: ifeq -> 217
    //   197: aload #25
    //   199: iconst_0
    //   200: aload #25
    //   202: invokevirtual length : ()I
    //   205: invokestatic parseExpires : (Ljava/lang/String;II)J
    //   208: lstore #19
    //   210: lload #19
    //   212: lstore #15
    //   214: goto -> 238
    //   217: aload #29
    //   219: ldc 'max-age'
    //   221: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   224: ifeq -> 268
    //   227: aload #25
    //   229: invokestatic parseMaxAge : (Ljava/lang/String;)J
    //   232: lstore #19
    //   234: lload #19
    //   236: lstore #17
    //   238: iconst_1
    //   239: istore #14
    //   241: aload #23
    //   243: astore #25
    //   245: aload #24
    //   247: astore #26
    //   249: iload #11
    //   251: istore #12
    //   253: iload #9
    //   255: istore #13
    //   257: lload #17
    //   259: lstore #19
    //   261: lload #15
    //   263: lstore #21
    //   265: goto -> 457
    //   268: aload #29
    //   270: ldc 'domain'
    //   272: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   275: ifeq -> 311
    //   278: aload #25
    //   280: invokestatic parseDomain : (Ljava/lang/String;)Ljava/lang/String;
    //   283: astore #26
    //   285: iconst_0
    //   286: istore #13
    //   288: aload #23
    //   290: astore #25
    //   292: iload #11
    //   294: istore #12
    //   296: iload #8
    //   298: istore #14
    //   300: lload #17
    //   302: lstore #19
    //   304: lload #15
    //   306: lstore #21
    //   308: goto -> 457
    //   311: aload #29
    //   313: ldc 'path'
    //   315: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   318: ifeq -> 348
    //   321: aload #24
    //   323: astore #26
    //   325: iload #11
    //   327: istore #12
    //   329: iload #9
    //   331: istore #13
    //   333: iload #8
    //   335: istore #14
    //   337: lload #17
    //   339: lstore #19
    //   341: lload #15
    //   343: lstore #21
    //   345: goto -> 457
    //   348: aload #29
    //   350: ldc 'secure'
    //   352: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   355: ifeq -> 388
    //   358: iconst_1
    //   359: istore #12
    //   361: aload #23
    //   363: astore #25
    //   365: aload #24
    //   367: astore #26
    //   369: iload #9
    //   371: istore #13
    //   373: iload #8
    //   375: istore #14
    //   377: lload #17
    //   379: lstore #19
    //   381: lload #15
    //   383: lstore #21
    //   385: goto -> 457
    //   388: aload #23
    //   390: astore #25
    //   392: aload #24
    //   394: astore #26
    //   396: iload #11
    //   398: istore #12
    //   400: iload #9
    //   402: istore #13
    //   404: iload #8
    //   406: istore #14
    //   408: lload #17
    //   410: lstore #19
    //   412: lload #15
    //   414: lstore #21
    //   416: aload #29
    //   418: ldc 'httponly'
    //   420: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   423: ifeq -> 457
    //   426: iconst_1
    //   427: istore #10
    //   429: lload #15
    //   431: lstore #21
    //   433: lload #17
    //   435: lstore #19
    //   437: iload #8
    //   439: istore #14
    //   441: iload #9
    //   443: istore #13
    //   445: iload #11
    //   447: istore #12
    //   449: aload #24
    //   451: astore #26
    //   453: aload #23
    //   455: astore #25
    //   457: iload #6
    //   459: iconst_1
    //   460: iadd
    //   461: istore #4
    //   463: aload #25
    //   465: astore #23
    //   467: aload #26
    //   469: astore #24
    //   471: iload #12
    //   473: istore #11
    //   475: iload #13
    //   477: istore #9
    //   479: iload #14
    //   481: istore #8
    //   483: lload #19
    //   485: lstore #17
    //   487: lload #21
    //   489: lstore #15
    //   491: goto -> 120
    //   494: ldc2_w -9223372036854775808
    //   497: lstore #19
    //   499: lload #17
    //   501: ldc2_w -9223372036854775808
    //   504: lcmp
    //   505: ifne -> 514
    //   508: lload #19
    //   510: lstore_0
    //   511: goto -> 583
    //   514: lload #17
    //   516: ldc2_w -1
    //   519: lcmp
    //   520: ifeq -> 580
    //   523: lload #17
    //   525: ldc2_w 9223372036854775
    //   528: lcmp
    //   529: ifgt -> 543
    //   532: lload #17
    //   534: ldc2_w 1000
    //   537: lmul
    //   538: lstore #15
    //   540: goto -> 548
    //   543: ldc2_w 9223372036854775807
    //   546: lstore #15
    //   548: lload_0
    //   549: lload #15
    //   551: ladd
    //   552: lstore #15
    //   554: lload #15
    //   556: lload_0
    //   557: lcmp
    //   558: iflt -> 573
    //   561: lload #15
    //   563: lstore_0
    //   564: lload #15
    //   566: ldc2_w 253402300799999
    //   569: lcmp
    //   570: ifle -> 511
    //   573: ldc2_w 253402300799999
    //   576: lstore_0
    //   577: goto -> 583
    //   580: lload #15
    //   582: lstore_0
    //   583: aload_2
    //   584: invokevirtual host : ()Ljava/lang/String;
    //   587: astore #25
    //   589: aload #24
    //   591: ifnonnull -> 600
    //   594: aload #25
    //   596: astore_3
    //   597: goto -> 615
    //   600: aload #25
    //   602: aload #24
    //   604: invokestatic domainMatch : (Ljava/lang/String;Ljava/lang/String;)Z
    //   607: ifne -> 612
    //   610: aconst_null
    //   611: areturn
    //   612: aload #24
    //   614: astore_3
    //   615: aload #25
    //   617: invokevirtual length : ()I
    //   620: aload_3
    //   621: invokevirtual length : ()I
    //   624: if_icmpeq -> 639
    //   627: invokestatic get : ()Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;
    //   630: aload_3
    //   631: invokevirtual getEffectiveTldPlusOne : (Ljava/lang/String;)Ljava/lang/String;
    //   634: ifnonnull -> 639
    //   637: aconst_null
    //   638: areturn
    //   639: ldc '/'
    //   641: astore #24
    //   643: aload #23
    //   645: ifnull -> 667
    //   648: aload #23
    //   650: ldc '/'
    //   652: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   655: ifne -> 661
    //   658: goto -> 667
    //   661: aload #23
    //   663: astore_2
    //   664: goto -> 699
    //   667: aload_2
    //   668: invokevirtual encodedPath : ()Ljava/lang/String;
    //   671: astore #23
    //   673: aload #23
    //   675: bipush #47
    //   677: invokevirtual lastIndexOf : (I)I
    //   680: istore #4
    //   682: aload #24
    //   684: astore_2
    //   685: iload #4
    //   687: ifeq -> 699
    //   690: aload #23
    //   692: iconst_0
    //   693: iload #4
    //   695: invokevirtual substring : (II)Ljava/lang/String;
    //   698: astore_2
    //   699: new okhttp3/Cookie
    //   702: dup
    //   703: aload #28
    //   705: aload #27
    //   707: lload_0
    //   708: aload_3
    //   709: aload_2
    //   710: iload #11
    //   712: iload #10
    //   714: iload #9
    //   716: iload #8
    //   718: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;ZZZZ)V
    //   721: areturn
    //   722: aconst_null
    //   723: areturn
    //   724: astore #25
    //   726: aload #23
    //   728: astore #25
    //   730: aload #24
    //   732: astore #26
    //   734: iload #11
    //   736: istore #12
    //   738: iload #9
    //   740: istore #13
    //   742: iload #8
    //   744: istore #14
    //   746: lload #17
    //   748: lstore #19
    //   750: lload #15
    //   752: lstore #21
    //   754: goto -> 457
    // Exception table:
    //   from	to	target	type
    //   197	210	724	java/lang/IllegalArgumentException
    //   227	234	724	java/lang/NumberFormatException
    //   278	285	724	java/lang/IllegalArgumentException
  }
  
  @Nullable
  public static Cookie parse(HttpUrl paramHttpUrl, String paramString) {
    return parse(System.currentTimeMillis(), paramHttpUrl, paramString);
  }
  
  public static List<Cookie> parseAll(HttpUrl paramHttpUrl, Headers paramHeaders) {
    List<?> list;
    ArrayList<Cookie> arrayList;
    List<String> list1 = paramHeaders.values("Set-Cookie");
    int i = list1.size();
    Headers headers = null;
    byte b = 0;
    while (b < i) {
      ArrayList<Cookie> arrayList1;
      Cookie cookie = parse(paramHttpUrl, list1.get(b));
      if (cookie == null) {
        paramHeaders = headers;
      } else {
        paramHeaders = headers;
        if (headers == null)
          arrayList1 = new ArrayList(); 
        arrayList1.add(cookie);
      } 
      b++;
      arrayList = arrayList1;
    } 
    if (arrayList != null) {
      list = Collections.unmodifiableList(arrayList);
    } else {
      list = Collections.emptyList();
    } 
    return (List)list;
  }
  
  private static String parseDomain(String paramString) {
    if (!paramString.endsWith(".")) {
      String str = paramString;
      if (paramString.startsWith("."))
        str = paramString.substring(1); 
      paramString = Util.canonicalizeHost(str);
      if (paramString != null)
        return paramString; 
      throw new IllegalArgumentException();
    } 
    throw new IllegalArgumentException();
  }
  
  private static long parseExpires(String paramString, int paramInt1, int paramInt2) {
    int j = dateCharacterOffset(paramString, paramInt1, paramInt2, false);
    Matcher matcher = TIME_PATTERN.matcher(paramString);
    paramInt1 = -1;
    int i = -1;
    byte b4 = -1;
    byte b1 = -1;
    byte b3 = -1;
    byte b2 = -1;
    while (j < paramInt2) {
      int k;
      byte b5;
      byte b6;
      byte b7;
      byte b8;
      int m = dateCharacterOffset(paramString, j + 1, paramInt2, true);
      matcher.region(j, m);
      if (i == -1 && matcher.usePattern(TIME_PATTERN).matches()) {
        j = Integer.parseInt(matcher.group(1));
        b7 = Integer.parseInt(matcher.group(2));
        b8 = Integer.parseInt(matcher.group(3));
        k = paramInt1;
        b5 = b4;
        b6 = b1;
      } else if (b4 == -1 && matcher.usePattern(DAY_OF_MONTH_PATTERN).matches()) {
        b5 = Integer.parseInt(matcher.group(1));
        k = paramInt1;
        j = i;
        b6 = b1;
        b7 = b3;
        b8 = b2;
      } else if (b1 == -1 && matcher.usePattern(MONTH_PATTERN).matches()) {
        String str = matcher.group(1).toLowerCase(Locale.US);
        b6 = MONTH_PATTERN.pattern().indexOf(str) / 4;
        k = paramInt1;
        j = i;
        b5 = b4;
        b7 = b3;
        b8 = b2;
      } else {
        k = paramInt1;
        j = i;
        b5 = b4;
        b6 = b1;
        b7 = b3;
        b8 = b2;
        if (paramInt1 == -1) {
          k = paramInt1;
          j = i;
          b5 = b4;
          b6 = b1;
          b7 = b3;
          b8 = b2;
          if (matcher.usePattern(YEAR_PATTERN).matches()) {
            k = Integer.parseInt(matcher.group(1));
            b8 = b2;
            b7 = b3;
            b6 = b1;
            b5 = b4;
            j = i;
          } 
        } 
      } 
      m = dateCharacterOffset(paramString, m + 1, paramInt2, false);
      paramInt1 = k;
      i = j;
      b4 = b5;
      b1 = b6;
      b3 = b7;
      b2 = b8;
      j = m;
    } 
    paramInt2 = paramInt1;
    if (paramInt1 >= 70) {
      paramInt2 = paramInt1;
      if (paramInt1 <= 99)
        paramInt2 = paramInt1 + 1900; 
    } 
    paramInt1 = paramInt2;
    if (paramInt2 >= 0) {
      paramInt1 = paramInt2;
      if (paramInt2 <= 69)
        paramInt1 = paramInt2 + 2000; 
    } 
    if (paramInt1 >= 1601) {
      if (b1 != -1) {
        if (b4 >= 1 && b4 <= 31) {
          if (i >= 0 && i <= 23) {
            if (b3 >= 0 && b3 <= 59) {
              if (b2 >= 0 && b2 <= 59) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(Util.UTC);
                gregorianCalendar.setLenient(false);
                gregorianCalendar.set(1, paramInt1);
                gregorianCalendar.set(2, b1 - 1);
                gregorianCalendar.set(5, b4);
                gregorianCalendar.set(11, i);
                gregorianCalendar.set(12, b3);
                gregorianCalendar.set(13, b2);
                gregorianCalendar.set(14, 0);
                return gregorianCalendar.getTimeInMillis();
              } 
              throw new IllegalArgumentException();
            } 
            throw new IllegalArgumentException();
          } 
          throw new IllegalArgumentException();
        } 
        throw new IllegalArgumentException();
      } 
      throw new IllegalArgumentException();
    } 
    throw new IllegalArgumentException();
  }
  
  private static long parseMaxAge(String paramString) {
    long l = Long.MIN_VALUE;
    try {
      long l1 = Long.parseLong(paramString);
      if (l1 > 0L)
        l = l1; 
      return l;
    } catch (NumberFormatException numberFormatException) {
      if (paramString.matches("-?\\d+")) {
        if (!paramString.startsWith("-"))
          l = Long.MAX_VALUE; 
        return l;
      } 
      throw numberFormatException;
    } 
  }
  
  private static boolean pathMatch(HttpUrl paramHttpUrl, String paramString) {
    String str = paramHttpUrl.encodedPath();
    if (str.equals(paramString))
      return true; 
    if (str.startsWith(paramString)) {
      if (paramString.endsWith("/"))
        return true; 
      if (str.charAt(paramString.length()) == '/')
        return true; 
    } 
    return false;
  }
  
  public String domain() {
    return this.domain;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    boolean bool = paramObject instanceof Cookie;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    bool = bool1;
    if (((Cookie)paramObject).name.equals(this.name)) {
      bool = bool1;
      if (((Cookie)paramObject).value.equals(this.value)) {
        bool = bool1;
        if (((Cookie)paramObject).domain.equals(this.domain)) {
          bool = bool1;
          if (((Cookie)paramObject).path.equals(this.path)) {
            bool = bool1;
            if (((Cookie)paramObject).expiresAt == this.expiresAt) {
              bool = bool1;
              if (((Cookie)paramObject).secure == this.secure) {
                bool = bool1;
                if (((Cookie)paramObject).httpOnly == this.httpOnly) {
                  bool = bool1;
                  if (((Cookie)paramObject).persistent == this.persistent) {
                    bool = bool1;
                    if (((Cookie)paramObject).hostOnly == this.hostOnly)
                      bool = true; 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    return bool;
  }
  
  public long expiresAt() {
    return this.expiresAt;
  }
  
  public int hashCode() {
    int j = this.name.hashCode();
    int k = this.value.hashCode();
    int m = this.domain.hashCode();
    int i = this.path.hashCode();
    long l = this.expiresAt;
    return ((((((((527 + j) * 31 + k) * 31 + m) * 31 + i) * 31 + (int)(l ^ l >>> 32L)) * 31 + (this.secure ^ true)) * 31 + (this.httpOnly ^ true)) * 31 + (this.persistent ^ true)) * 31 + (this.hostOnly ^ true);
  }
  
  public boolean hostOnly() {
    return this.hostOnly;
  }
  
  public boolean httpOnly() {
    return this.httpOnly;
  }
  
  public boolean matches(HttpUrl paramHttpUrl) {
    boolean bool;
    if (this.hostOnly) {
      bool = paramHttpUrl.host().equals(this.domain);
    } else {
      bool = domainMatch(paramHttpUrl.host(), this.domain);
    } 
    return !bool ? false : (!pathMatch(paramHttpUrl, this.path) ? false : (!(this.secure && !paramHttpUrl.isHttps())));
  }
  
  public String name() {
    return this.name;
  }
  
  public String path() {
    return this.path;
  }
  
  public boolean persistent() {
    return this.persistent;
  }
  
  public boolean secure() {
    return this.secure;
  }
  
  public String toString() {
    return toString(false);
  }
  
  String toString(boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.name);
    stringBuilder.append('=');
    stringBuilder.append(this.value);
    if (this.persistent)
      if (this.expiresAt == Long.MIN_VALUE) {
        stringBuilder.append("; max-age=0");
      } else {
        stringBuilder.append("; expires=");
        stringBuilder.append(HttpDate.format(new Date(this.expiresAt)));
      }  
    if (!this.hostOnly) {
      stringBuilder.append("; domain=");
      if (paramBoolean)
        stringBuilder.append("."); 
      stringBuilder.append(this.domain);
    } 
    stringBuilder.append("; path=");
    stringBuilder.append(this.path);
    if (this.secure)
      stringBuilder.append("; secure"); 
    if (this.httpOnly)
      stringBuilder.append("; httponly"); 
    return stringBuilder.toString();
  }
  
  public String value() {
    return this.value;
  }
  
  public static final class Builder {
    String domain;
    
    long expiresAt = 253402300799999L;
    
    boolean hostOnly;
    
    boolean httpOnly;
    
    String name;
    
    String path = "/";
    
    boolean persistent;
    
    boolean secure;
    
    String value;
    
    private Builder domain(String param1String, boolean param1Boolean) {
      if (param1String != null) {
        String str = Util.canonicalizeHost(param1String);
        if (str != null) {
          this.domain = str;
          this.hostOnly = param1Boolean;
          return this;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected domain: ");
        stringBuilder.append(param1String);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new NullPointerException("domain == null");
    }
    
    public Cookie build() {
      return new Cookie(this);
    }
    
    public Builder domain(String param1String) {
      return domain(param1String, false);
    }
    
    public Builder expiresAt(long param1Long) {
      long l = param1Long;
      if (param1Long <= 0L)
        l = Long.MIN_VALUE; 
      param1Long = l;
      if (l > 253402300799999L)
        param1Long = 253402300799999L; 
      this.expiresAt = param1Long;
      this.persistent = true;
      return this;
    }
    
    public Builder hostOnlyDomain(String param1String) {
      return domain(param1String, true);
    }
    
    public Builder httpOnly() {
      this.httpOnly = true;
      return this;
    }
    
    public Builder name(String param1String) {
      if (param1String != null) {
        if (param1String.trim().equals(param1String)) {
          this.name = param1String;
          return this;
        } 
        throw new IllegalArgumentException("name is not trimmed");
      } 
      throw new NullPointerException("name == null");
    }
    
    public Builder path(String param1String) {
      if (param1String.startsWith("/")) {
        this.path = param1String;
        return this;
      } 
      throw new IllegalArgumentException("path must start with '/'");
    }
    
    public Builder secure() {
      this.secure = true;
      return this;
    }
    
    public Builder value(String param1String) {
      if (param1String != null) {
        if (param1String.trim().equals(param1String)) {
          this.value = param1String;
          return this;
        } 
        throw new IllegalArgumentException("value is not trimmed");
      } 
      throw new NullPointerException("value == null");
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Cookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */