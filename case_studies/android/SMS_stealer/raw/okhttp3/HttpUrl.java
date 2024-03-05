package okhttp3;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okio.Buffer;

public final class HttpUrl {
  static final String FORM_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#&!$(),~";
  
  static final String FRAGMENT_ENCODE_SET = "";
  
  static final String FRAGMENT_ENCODE_SET_URI = " \"#<>\\^`{|}";
  
  private static final char[] HEX_DIGITS = new char[] { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'A', 'B', 'C', 'D', 'E', 'F' };
  
  static final String PASSWORD_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
  
  static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";
  
  static final String PATH_SEGMENT_ENCODE_SET_URI = "[]";
  
  static final String QUERY_COMPONENT_ENCODE_SET = " !\"#$&'(),/:;<=>?@[]\\^`{|}~";
  
  static final String QUERY_COMPONENT_ENCODE_SET_URI = "\\^`{|}";
  
  static final String QUERY_COMPONENT_REENCODE_SET = " \"'<>#&=";
  
  static final String QUERY_ENCODE_SET = " \"'<>#";
  
  static final String USERNAME_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
  
  @Nullable
  private final String fragment;
  
  final String host;
  
  private final String password;
  
  private final List<String> pathSegments;
  
  final int port;
  
  @Nullable
  private final List<String> queryNamesAndValues;
  
  final String scheme;
  
  private final String url;
  
  private final String username;
  
  HttpUrl(Builder paramBuilder) {
    String str;
    this.scheme = paramBuilder.scheme;
    this.username = percentDecode(paramBuilder.encodedUsername, false);
    this.password = percentDecode(paramBuilder.encodedPassword, false);
    this.host = paramBuilder.host;
    this.port = paramBuilder.effectivePort();
    this.pathSegments = percentDecode(paramBuilder.encodedPathSegments, false);
    List<String> list = paramBuilder.encodedQueryNamesAndValues;
    List list1 = null;
    if (list != null) {
      list = percentDecode(paramBuilder.encodedQueryNamesAndValues, true);
    } else {
      list = null;
    } 
    this.queryNamesAndValues = list;
    list = list1;
    if (paramBuilder.encodedFragment != null)
      str = percentDecode(paramBuilder.encodedFragment, false); 
    this.fragment = str;
    this.url = paramBuilder.toString();
  }
  
  static String canonicalize(String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset) {
    for (int i = paramInt1; i < paramInt2; i += Character.charCount(j)) {
      int j = paramString1.codePointAt(i);
      if (j < 32 || j == 127 || (j >= 128 && paramBoolean4) || paramString2.indexOf(j) != -1 || (j == 37 && (!paramBoolean1 || (paramBoolean2 && !percentEncoded(paramString1, i, paramInt2)))) || (j == 43 && paramBoolean3)) {
        Buffer buffer = new Buffer();
        buffer.writeUtf8(paramString1, paramInt1, i);
        canonicalize(buffer, paramString1, i, paramInt2, paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramCharset);
        return buffer.readUtf8();
      } 
    } 
    return paramString1.substring(paramInt1, paramInt2);
  }
  
  static String canonicalize(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    return canonicalize(paramString1, 0, paramString1.length(), paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, null);
  }
  
  static String canonicalize(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset) {
    return canonicalize(paramString1, 0, paramString1.length(), paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramCharset);
  }
  
  static void canonicalize(Buffer paramBuffer, String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset) {
    for (Object object = null; paramInt1 < paramInt2; object = SYNTHETIC_LOCAL_VARIABLE_14) {
      int i = paramString1.codePointAt(paramInt1);
      if (paramBoolean1) {
        Object object1 = object;
        if (i != 9) {
          object1 = object;
          if (i != 10) {
            object1 = object;
            if (i != 12) {
              if (i == 13) {
                object1 = object;
                continue;
              } 
            } else {
              continue;
            } 
          } else {
            continue;
          } 
        } else {
          continue;
        } 
      } 
      if (i == 43 && paramBoolean3) {
        String str;
        if (paramBoolean1) {
          str = "+";
        } else {
          str = "%2B";
        } 
        paramBuffer.writeUtf8(str);
        Object object1 = object;
      } else if (i < 32 || i == 127 || (i >= 128 && paramBoolean4) || paramString2.indexOf(i) != -1 || (i == 37 && (!paramBoolean1 || (paramBoolean2 && !percentEncoded(paramString1, paramInt1, paramInt2))))) {
        Object object1 = object;
        if (object == null)
          object1 = new Buffer(); 
        if (paramCharset == null || paramCharset.equals(Util.UTF_8)) {
          object1.writeUtf8CodePoint(i);
        } else {
          object1.writeString(paramString1, paramInt1, Character.charCount(i) + paramInt1, paramCharset);
        } 
        while (true) {
          Object object2 = object1;
          if (!object1.exhausted()) {
            int j = object1.readByte() & 0xFF;
            paramBuffer.writeByte(37);
            paramBuffer.writeByte(HEX_DIGITS[j >> 4 & 0xF]);
            paramBuffer.writeByte(HEX_DIGITS[j & 0xF]);
            continue;
          } 
          break;
        } 
      } else {
        paramBuffer.writeUtf8CodePoint(i);
        Object object1 = object;
      } 
      continue;
      paramInt1 += Character.charCount(SYNTHETIC_LOCAL_VARIABLE_11);
    } 
  }
  
  public static int defaultPort(String paramString) {
    return paramString.equals("http") ? 80 : (paramString.equals("https") ? 443 : -1);
  }
  
  @Nullable
  public static HttpUrl get(URI paramURI) {
    return parse(paramURI.toString());
  }
  
  @Nullable
  public static HttpUrl get(URL paramURL) {
    return parse(paramURL.toString());
  }
  
  static HttpUrl getChecked(String paramString) throws MalformedURLException, UnknownHostException {
    StringBuilder stringBuilder;
    Builder builder = new Builder();
    Builder.ParseResult parseResult = builder.parse(null, paramString);
    int i = null.$SwitchMap$okhttp3$HttpUrl$Builder$ParseResult[parseResult.ordinal()];
    if (i != 1) {
      if (i != 2) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid URL: ");
        stringBuilder.append(parseResult);
        stringBuilder.append(" for ");
        stringBuilder.append(paramString);
        throw new MalformedURLException(stringBuilder.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Invalid host: ");
      stringBuilder1.append(paramString);
      throw new UnknownHostException(stringBuilder1.toString());
    } 
    return stringBuilder.build();
  }
  
  static void namesAndValuesToQueryString(StringBuilder paramStringBuilder, List<String> paramList) {
    int i = paramList.size();
    for (byte b = 0; b < i; b += 2) {
      String str1 = paramList.get(b);
      String str2 = paramList.get(b + 1);
      if (b > 0)
        paramStringBuilder.append('&'); 
      paramStringBuilder.append(str1);
      if (str2 != null) {
        paramStringBuilder.append('=');
        paramStringBuilder.append(str2);
      } 
    } 
  }
  
  @Nullable
  public static HttpUrl parse(String paramString) {
    Builder builder = new Builder();
    HttpUrl httpUrl = null;
    if (builder.parse(null, paramString) == Builder.ParseResult.SUCCESS)
      httpUrl = builder.build(); 
    return httpUrl;
  }
  
  static void pathSegmentsToString(StringBuilder paramStringBuilder, List<String> paramList) {
    int i = paramList.size();
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append('/');
      paramStringBuilder.append(paramList.get(b));
    } 
  }
  
  static String percentDecode(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
    for (int i = paramInt1; i < paramInt2; i++) {
      char c = paramString.charAt(i);
      if (c == '%' || (c == '+' && paramBoolean)) {
        Buffer buffer = new Buffer();
        buffer.writeUtf8(paramString, paramInt1, i);
        percentDecode(buffer, paramString, i, paramInt2, paramBoolean);
        return buffer.readUtf8();
      } 
    } 
    return paramString.substring(paramInt1, paramInt2);
  }
  
  static String percentDecode(String paramString, boolean paramBoolean) {
    return percentDecode(paramString, 0, paramString.length(), paramBoolean);
  }
  
  private List<String> percentDecode(List<String> paramList, boolean paramBoolean) {
    int i = paramList.size();
    ArrayList<String> arrayList = new ArrayList(i);
    for (byte b = 0; b < i; b++) {
      String str = paramList.get(b);
      if (str != null) {
        str = percentDecode(str, paramBoolean);
      } else {
        str = null;
      } 
      arrayList.add(str);
    } 
    return Collections.unmodifiableList(arrayList);
  }
  
  static void percentDecode(Buffer paramBuffer, String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
    // Byte code:
    //   0: iload_2
    //   1: iload_3
    //   2: if_icmpge -> 123
    //   5: aload_1
    //   6: iload_2
    //   7: invokevirtual codePointAt : (I)I
    //   10: istore #6
    //   12: iload #6
    //   14: bipush #37
    //   16: if_icmpne -> 83
    //   19: iload_2
    //   20: iconst_2
    //   21: iadd
    //   22: istore #5
    //   24: iload #5
    //   26: iload_3
    //   27: if_icmpge -> 83
    //   30: aload_1
    //   31: iload_2
    //   32: iconst_1
    //   33: iadd
    //   34: invokevirtual charAt : (I)C
    //   37: invokestatic decodeHexDigit : (C)I
    //   40: istore #8
    //   42: aload_1
    //   43: iload #5
    //   45: invokevirtual charAt : (I)C
    //   48: invokestatic decodeHexDigit : (C)I
    //   51: istore #7
    //   53: iload #8
    //   55: iconst_m1
    //   56: if_icmpeq -> 105
    //   59: iload #7
    //   61: iconst_m1
    //   62: if_icmpeq -> 105
    //   65: aload_0
    //   66: iload #8
    //   68: iconst_4
    //   69: ishl
    //   70: iload #7
    //   72: iadd
    //   73: invokevirtual writeByte : (I)Lokio/Buffer;
    //   76: pop
    //   77: iload #5
    //   79: istore_2
    //   80: goto -> 112
    //   83: iload #6
    //   85: bipush #43
    //   87: if_icmpne -> 105
    //   90: iload #4
    //   92: ifeq -> 105
    //   95: aload_0
    //   96: bipush #32
    //   98: invokevirtual writeByte : (I)Lokio/Buffer;
    //   101: pop
    //   102: goto -> 112
    //   105: aload_0
    //   106: iload #6
    //   108: invokevirtual writeUtf8CodePoint : (I)Lokio/Buffer;
    //   111: pop
    //   112: iload_2
    //   113: iload #6
    //   115: invokestatic charCount : (I)I
    //   118: iadd
    //   119: istore_2
    //   120: goto -> 0
    //   123: return
  }
  
  static boolean percentEncoded(String paramString, int paramInt1, int paramInt2) {
    int i = paramInt1 + 2;
    boolean bool = true;
    if (i >= paramInt2 || paramString.charAt(paramInt1) != '%' || Util.decodeHexDigit(paramString.charAt(paramInt1 + 1)) == -1 || Util.decodeHexDigit(paramString.charAt(i)) == -1)
      bool = false; 
    return bool;
  }
  
  static List<String> queryStringToNamesAndValues(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    for (int i = 0; i <= paramString.length(); i = j + 1) {
      int k = paramString.indexOf('&', i);
      int j = k;
      if (k == -1)
        j = paramString.length(); 
      k = paramString.indexOf('=', i);
      if (k == -1 || k > j) {
        arrayList.add(paramString.substring(i, j));
        arrayList.add(null);
      } else {
        arrayList.add(paramString.substring(i, k));
        arrayList.add(paramString.substring(k + 1, j));
      } 
    } 
    return arrayList;
  }
  
  @Nullable
  public String encodedFragment() {
    if (this.fragment == null)
      return null; 
    int i = this.url.indexOf('#');
    return this.url.substring(i + 1);
  }
  
  public String encodedPassword() {
    if (this.password.isEmpty())
      return ""; 
    int j = this.url.indexOf(':', this.scheme.length() + 3);
    int i = this.url.indexOf('@');
    return this.url.substring(j + 1, i);
  }
  
  public String encodedPath() {
    int j = this.url.indexOf('/', this.scheme.length() + 3);
    String str = this.url;
    int i = Util.delimiterOffset(str, j, str.length(), "?#");
    return this.url.substring(j, i);
  }
  
  public List<String> encodedPathSegments() {
    int i = this.url.indexOf('/', this.scheme.length() + 3);
    String str = this.url;
    int j = Util.delimiterOffset(str, i, str.length(), "?#");
    ArrayList<String> arrayList = new ArrayList();
    while (i < j) {
      int k = i + 1;
      i = Util.delimiterOffset(this.url, k, j, '/');
      arrayList.add(this.url.substring(k, i));
    } 
    return arrayList;
  }
  
  @Nullable
  public String encodedQuery() {
    if (this.queryNamesAndValues == null)
      return null; 
    int j = this.url.indexOf('?') + 1;
    String str = this.url;
    int i = Util.delimiterOffset(str, j, str.length(), '#');
    return this.url.substring(j, i);
  }
  
  public String encodedUsername() {
    if (this.username.isEmpty())
      return ""; 
    int i = this.scheme.length() + 3;
    String str = this.url;
    int j = Util.delimiterOffset(str, i, str.length(), ":@");
    return this.url.substring(i, j);
  }
  
  public boolean equals(@Nullable Object paramObject) {
    boolean bool;
    if (paramObject instanceof HttpUrl && ((HttpUrl)paramObject).url.equals(this.url)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  @Nullable
  public String fragment() {
    return this.fragment;
  }
  
  public int hashCode() {
    return this.url.hashCode();
  }
  
  public String host() {
    return this.host;
  }
  
  public boolean isHttps() {
    return this.scheme.equals("https");
  }
  
  public Builder newBuilder() {
    byte b;
    Builder builder = new Builder();
    builder.scheme = this.scheme;
    builder.encodedUsername = encodedUsername();
    builder.encodedPassword = encodedPassword();
    builder.host = this.host;
    if (this.port != defaultPort(this.scheme)) {
      b = this.port;
    } else {
      b = -1;
    } 
    builder.port = b;
    builder.encodedPathSegments.clear();
    builder.encodedPathSegments.addAll(encodedPathSegments());
    builder.encodedQuery(encodedQuery());
    builder.encodedFragment = encodedFragment();
    return builder;
  }
  
  @Nullable
  public Builder newBuilder(String paramString) {
    Builder builder = new Builder();
    if (builder.parse(this, paramString) == Builder.ParseResult.SUCCESS) {
      Builder builder1 = builder;
    } else {
      paramString = null;
    } 
    return (Builder)paramString;
  }
  
  public String password() {
    return this.password;
  }
  
  public List<String> pathSegments() {
    return this.pathSegments;
  }
  
  public int pathSize() {
    return this.pathSegments.size();
  }
  
  public int port() {
    return this.port;
  }
  
  @Nullable
  public String query() {
    if (this.queryNamesAndValues == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    namesAndValuesToQueryString(stringBuilder, this.queryNamesAndValues);
    return stringBuilder.toString();
  }
  
  @Nullable
  public String queryParameter(String paramString) {
    List<String> list = this.queryNamesAndValues;
    if (list == null)
      return null; 
    byte b = 0;
    int i = list.size();
    while (b < i) {
      if (paramString.equals(this.queryNamesAndValues.get(b)))
        return this.queryNamesAndValues.get(b + 1); 
      b += 2;
    } 
    return null;
  }
  
  public String queryParameterName(int paramInt) {
    List<String> list = this.queryNamesAndValues;
    if (list != null)
      return list.get(paramInt * 2); 
    throw new IndexOutOfBoundsException();
  }
  
  public Set<String> queryParameterNames() {
    if (this.queryNamesAndValues == null)
      return Collections.emptySet(); 
    LinkedHashSet<? extends String> linkedHashSet = new LinkedHashSet();
    byte b = 0;
    int i = this.queryNamesAndValues.size();
    while (b < i) {
      linkedHashSet.add(this.queryNamesAndValues.get(b));
      b += 2;
    } 
    return Collections.unmodifiableSet(linkedHashSet);
  }
  
  public String queryParameterValue(int paramInt) {
    List<String> list = this.queryNamesAndValues;
    if (list != null)
      return list.get(paramInt * 2 + 1); 
    throw new IndexOutOfBoundsException();
  }
  
  public List<String> queryParameterValues(String paramString) {
    if (this.queryNamesAndValues == null)
      return Collections.emptyList(); 
    ArrayList<? extends String> arrayList = new ArrayList();
    byte b = 0;
    int i = this.queryNamesAndValues.size();
    while (b < i) {
      if (paramString.equals(this.queryNamesAndValues.get(b)))
        arrayList.add(this.queryNamesAndValues.get(b + 1)); 
      b += 2;
    } 
    return Collections.unmodifiableList(arrayList);
  }
  
  public int querySize() {
    boolean bool;
    List<String> list = this.queryNamesAndValues;
    if (list != null) {
      bool = list.size() / 2;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String redact() {
    return newBuilder("/...").username("").password("").build().toString();
  }
  
  @Nullable
  public HttpUrl resolve(String paramString) {
    Builder builder = newBuilder(paramString);
    if (builder != null) {
      HttpUrl httpUrl = builder.build();
    } else {
      builder = null;
    } 
    return (HttpUrl)builder;
  }
  
  public String scheme() {
    return this.scheme;
  }
  
  public String toString() {
    return this.url;
  }
  
  @Nullable
  public String topPrivateDomain() {
    return Util.verifyAsIpAddress(this.host) ? null : PublicSuffixDatabase.get().getEffectiveTldPlusOne(this.host);
  }
  
  public URI uri() {
    String str = newBuilder().reencodeForUri().toString();
    try {
      return new URI(str);
    } catch (URISyntaxException uRISyntaxException) {
      try {
        return URI.create(str.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", ""));
      } catch (Exception exception) {
        throw new RuntimeException(uRISyntaxException);
      } 
    } 
  }
  
  public URL url() {
    try {
      return new URL(this.url);
    } catch (MalformedURLException malformedURLException) {
      throw new RuntimeException(malformedURLException);
    } 
  }
  
  public String username() {
    return this.username;
  }
  
  public static final class Builder {
    @Nullable
    String encodedFragment;
    
    String encodedPassword = "";
    
    final List<String> encodedPathSegments;
    
    @Nullable
    List<String> encodedQueryNamesAndValues;
    
    String encodedUsername = "";
    
    @Nullable
    String host;
    
    int port = -1;
    
    @Nullable
    String scheme;
    
    public Builder() {
      ArrayList<String> arrayList = new ArrayList();
      this.encodedPathSegments = arrayList;
      arrayList.add("");
    }
    
    private Builder addPathSegments(String param1String, boolean param1Boolean) {
      int i = 0;
      while (true) {
        boolean bool;
        int j = Util.delimiterOffset(param1String, i, param1String.length(), "/\\");
        if (j < param1String.length()) {
          bool = true;
        } else {
          bool = false;
        } 
        push(param1String, i, j, bool, param1Boolean);
        i = ++j;
        if (j > param1String.length())
          return this; 
      } 
    }
    
    private static String canonicalizeHost(String param1String, int param1Int1, int param1Int2) {
      return Util.canonicalizeHost(HttpUrl.percentDecode(param1String, param1Int1, param1Int2, false));
    }
    
    private boolean isDot(String param1String) {
      return (param1String.equals(".") || param1String.equalsIgnoreCase("%2e"));
    }
    
    private boolean isDotDot(String param1String) {
      return (param1String.equals("..") || param1String.equalsIgnoreCase("%2e.") || param1String.equalsIgnoreCase(".%2e") || param1String.equalsIgnoreCase("%2e%2e"));
    }
    
    private static int parsePort(String param1String, int param1Int1, int param1Int2) {
      try {
        param1Int1 = Integer.parseInt(HttpUrl.canonicalize(param1String, param1Int1, param1Int2, "", false, false, false, true, null));
        if (param1Int1 > 0 && param1Int1 <= 65535)
          return param1Int1; 
      } catch (NumberFormatException numberFormatException) {}
      return -1;
    }
    
    private void pop() {
      List<String> list = this.encodedPathSegments;
      if (((String)list.remove(list.size() - 1)).isEmpty() && !this.encodedPathSegments.isEmpty()) {
        list = this.encodedPathSegments;
        list.set(list.size() - 1, "");
      } else {
        this.encodedPathSegments.add("");
      } 
    }
    
    private static int portColonOffset(String param1String, int param1Int1, int param1Int2) {
      while (param1Int1 < param1Int2) {
        char c = param1String.charAt(param1Int1);
        if (c != ':') {
          int i = param1Int1;
          if (c != '[') {
            i = param1Int1;
          } else {
            while (true) {
              param1Int1 = i + 1;
              i = param1Int1;
              if (param1Int1 < param1Int2) {
                i = param1Int1;
                if (param1String.charAt(param1Int1) == ']') {
                  i = param1Int1;
                  break;
                } 
                continue;
              } 
              break;
            } 
          } 
          param1Int1 = i + 1;
          continue;
        } 
        return param1Int1;
      } 
      return param1Int2;
    }
    
    private void push(String param1String, int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2) {
      param1String = HttpUrl.canonicalize(param1String, param1Int1, param1Int2, " \"<>^`{}|/\\?#", param1Boolean2, false, false, true, null);
      if (isDot(param1String))
        return; 
      if (isDotDot(param1String)) {
        pop();
        return;
      } 
      List<String> list = this.encodedPathSegments;
      if (((String)list.get(list.size() - 1)).isEmpty()) {
        list = this.encodedPathSegments;
        list.set(list.size() - 1, param1String);
      } else {
        this.encodedPathSegments.add(param1String);
      } 
      if (param1Boolean1)
        this.encodedPathSegments.add(""); 
    }
    
    private void removeAllCanonicalQueryParameters(String param1String) {
      for (int i = this.encodedQueryNamesAndValues.size() - 2; i >= 0; i -= 2) {
        if (param1String.equals(this.encodedQueryNamesAndValues.get(i))) {
          this.encodedQueryNamesAndValues.remove(i + 1);
          this.encodedQueryNamesAndValues.remove(i);
          if (this.encodedQueryNamesAndValues.isEmpty()) {
            this.encodedQueryNamesAndValues = null;
            return;
          } 
        } 
      } 
    }
    
    private void resolvePath(String param1String, int param1Int1, int param1Int2) {
      if (param1Int1 == param1Int2)
        return; 
      char c = param1String.charAt(param1Int1);
      if (c == '/' || c == '\\') {
        this.encodedPathSegments.clear();
        this.encodedPathSegments.add("");
      } else {
        List<String> list = this.encodedPathSegments;
        list.set(list.size() - 1, "");
        while (true) {
          if (param1Int1 < param1Int2) {
            boolean bool;
            int i = Util.delimiterOffset(param1String, param1Int1, param1Int2, "/\\");
            if (i < param1Int2) {
              bool = true;
            } else {
              bool = false;
            } 
            push(param1String, param1Int1, i, bool, true);
            param1Int1 = i;
            if (bool) {
              param1Int1 = i;
            } else {
              continue;
            } 
          } else {
            break;
          } 
          param1Int1++;
        } 
        return;
      } 
      param1Int1++;
      continue;
    }
    
    private static int schemeDelimiterOffset(String param1String, int param1Int1, int param1Int2) {
      // Byte code:
      //   0: iload_2
      //   1: iload_1
      //   2: isub
      //   3: iconst_2
      //   4: if_icmpge -> 9
      //   7: iconst_m1
      //   8: ireturn
      //   9: aload_0
      //   10: iload_1
      //   11: invokevirtual charAt : (I)C
      //   14: istore #4
      //   16: iload #4
      //   18: bipush #97
      //   20: if_icmplt -> 32
      //   23: iload_1
      //   24: istore_3
      //   25: iload #4
      //   27: bipush #122
      //   29: if_icmple -> 51
      //   32: iload #4
      //   34: bipush #65
      //   36: if_icmplt -> 154
      //   39: iload_1
      //   40: istore_3
      //   41: iload #4
      //   43: bipush #90
      //   45: if_icmple -> 51
      //   48: goto -> 154
      //   51: iload_3
      //   52: iconst_1
      //   53: iadd
      //   54: istore_1
      //   55: iload_1
      //   56: iload_2
      //   57: if_icmpge -> 154
      //   60: aload_0
      //   61: iload_1
      //   62: invokevirtual charAt : (I)C
      //   65: istore #4
      //   67: iload #4
      //   69: bipush #97
      //   71: if_icmplt -> 83
      //   74: iload_1
      //   75: istore_3
      //   76: iload #4
      //   78: bipush #122
      //   80: if_icmple -> 51
      //   83: iload #4
      //   85: bipush #65
      //   87: if_icmplt -> 99
      //   90: iload_1
      //   91: istore_3
      //   92: iload #4
      //   94: bipush #90
      //   96: if_icmple -> 51
      //   99: iload #4
      //   101: bipush #48
      //   103: if_icmplt -> 115
      //   106: iload_1
      //   107: istore_3
      //   108: iload #4
      //   110: bipush #57
      //   112: if_icmple -> 51
      //   115: iload_1
      //   116: istore_3
      //   117: iload #4
      //   119: bipush #43
      //   121: if_icmpeq -> 51
      //   124: iload_1
      //   125: istore_3
      //   126: iload #4
      //   128: bipush #45
      //   130: if_icmpeq -> 51
      //   133: iload #4
      //   135: bipush #46
      //   137: if_icmpne -> 145
      //   140: iload_1
      //   141: istore_3
      //   142: goto -> 51
      //   145: iload #4
      //   147: bipush #58
      //   149: if_icmpne -> 154
      //   152: iload_1
      //   153: ireturn
      //   154: iconst_m1
      //   155: ireturn
    }
    
    private static int slashCount(String param1String, int param1Int1, int param1Int2) {
      byte b = 0;
      while (param1Int1 < param1Int2) {
        char c = param1String.charAt(param1Int1);
        if (c == '\\' || c == '/') {
          b++;
          param1Int1++;
        } 
      } 
      return b;
    }
    
    public Builder addEncodedPathSegment(String param1String) {
      if (param1String != null) {
        push(param1String, 0, param1String.length(), false, true);
        return this;
      } 
      throw new NullPointerException("encodedPathSegment == null");
    }
    
    public Builder addEncodedPathSegments(String param1String) {
      if (param1String != null)
        return addPathSegments(param1String, true); 
      throw new NullPointerException("encodedPathSegments == null");
    }
    
    public Builder addEncodedQueryParameter(String param1String1, @Nullable String param1String2) {
      if (param1String1 != null) {
        if (this.encodedQueryNamesAndValues == null)
          this.encodedQueryNamesAndValues = new ArrayList<String>(); 
        this.encodedQueryNamesAndValues.add(HttpUrl.canonicalize(param1String1, " \"'<>#&=", true, false, true, true));
        List<String> list = this.encodedQueryNamesAndValues;
        if (param1String2 != null) {
          param1String1 = HttpUrl.canonicalize(param1String2, " \"'<>#&=", true, false, true, true);
        } else {
          param1String1 = null;
        } 
        list.add(param1String1);
        return this;
      } 
      throw new NullPointerException("encodedName == null");
    }
    
    public Builder addPathSegment(String param1String) {
      if (param1String != null) {
        push(param1String, 0, param1String.length(), false, false);
        return this;
      } 
      throw new NullPointerException("pathSegment == null");
    }
    
    public Builder addPathSegments(String param1String) {
      if (param1String != null)
        return addPathSegments(param1String, false); 
      throw new NullPointerException("pathSegments == null");
    }
    
    public Builder addQueryParameter(String param1String1, @Nullable String param1String2) {
      if (param1String1 != null) {
        if (this.encodedQueryNamesAndValues == null)
          this.encodedQueryNamesAndValues = new ArrayList<String>(); 
        this.encodedQueryNamesAndValues.add(HttpUrl.canonicalize(param1String1, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
        List<String> list = this.encodedQueryNamesAndValues;
        if (param1String2 != null) {
          param1String1 = HttpUrl.canonicalize(param1String2, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true);
        } else {
          param1String1 = null;
        } 
        list.add(param1String1);
        return this;
      } 
      throw new NullPointerException("name == null");
    }
    
    public HttpUrl build() {
      if (this.scheme != null) {
        if (this.host != null)
          return new HttpUrl(this); 
        throw new IllegalStateException("host == null");
      } 
      throw new IllegalStateException("scheme == null");
    }
    
    int effectivePort() {
      int i = this.port;
      if (i == -1)
        i = HttpUrl.defaultPort(this.scheme); 
      return i;
    }
    
    public Builder encodedFragment(@Nullable String param1String) {
      if (param1String != null) {
        param1String = HttpUrl.canonicalize(param1String, "", true, false, false, false);
      } else {
        param1String = null;
      } 
      this.encodedFragment = param1String;
      return this;
    }
    
    public Builder encodedPassword(String param1String) {
      if (param1String != null) {
        this.encodedPassword = HttpUrl.canonicalize(param1String, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
        return this;
      } 
      throw new NullPointerException("encodedPassword == null");
    }
    
    public Builder encodedPath(String param1String) {
      if (param1String != null) {
        if (param1String.startsWith("/")) {
          resolvePath(param1String, 0, param1String.length());
          return this;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected encodedPath: ");
        stringBuilder.append(param1String);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new NullPointerException("encodedPath == null");
    }
    
    public Builder encodedQuery(@Nullable String param1String) {
      if (param1String != null) {
        List<String> list = HttpUrl.queryStringToNamesAndValues(HttpUrl.canonicalize(param1String, " \"'<>#", true, false, true, true));
      } else {
        param1String = null;
      } 
      this.encodedQueryNamesAndValues = (List<String>)param1String;
      return this;
    }
    
    public Builder encodedUsername(String param1String) {
      if (param1String != null) {
        this.encodedUsername = HttpUrl.canonicalize(param1String, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
        return this;
      } 
      throw new NullPointerException("encodedUsername == null");
    }
    
    public Builder fragment(@Nullable String param1String) {
      if (param1String != null) {
        param1String = HttpUrl.canonicalize(param1String, "", false, false, false, false);
      } else {
        param1String = null;
      } 
      this.encodedFragment = param1String;
      return this;
    }
    
    public Builder host(String param1String) {
      if (param1String != null) {
        String str = canonicalizeHost(param1String, 0, param1String.length());
        if (str != null) {
          this.host = str;
          return this;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected host: ");
        stringBuilder.append(param1String);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new NullPointerException("host == null");
    }
    
    ParseResult parse(@Nullable HttpUrl param1HttpUrl, String param1String) {
      // Byte code:
      //   0: aload_2
      //   1: iconst_0
      //   2: aload_2
      //   3: invokevirtual length : ()I
      //   6: invokestatic skipLeadingAsciiWhitespace : (Ljava/lang/String;II)I
      //   9: istore_3
      //   10: aload_2
      //   11: iload_3
      //   12: aload_2
      //   13: invokevirtual length : ()I
      //   16: invokestatic skipTrailingAsciiWhitespace : (Ljava/lang/String;II)I
      //   19: istore #8
      //   21: aload_2
      //   22: iload_3
      //   23: iload #8
      //   25: invokestatic schemeDelimiterOffset : (Ljava/lang/String;II)I
      //   28: iconst_m1
      //   29: if_icmpeq -> 91
      //   32: aload_2
      //   33: iconst_1
      //   34: iload_3
      //   35: ldc_w 'https:'
      //   38: iconst_0
      //   39: bipush #6
      //   41: invokevirtual regionMatches : (ZILjava/lang/String;II)Z
      //   44: ifeq -> 60
      //   47: aload_0
      //   48: ldc_w 'https'
      //   51: putfield scheme : Ljava/lang/String;
      //   54: iinc #3, 6
      //   57: goto -> 103
      //   60: aload_2
      //   61: iconst_1
      //   62: iload_3
      //   63: ldc_w 'http:'
      //   66: iconst_0
      //   67: iconst_5
      //   68: invokevirtual regionMatches : (ZILjava/lang/String;II)Z
      //   71: ifeq -> 87
      //   74: aload_0
      //   75: ldc_w 'http'
      //   78: putfield scheme : Ljava/lang/String;
      //   81: iinc #3, 5
      //   84: goto -> 103
      //   87: getstatic okhttp3/HttpUrl$Builder$ParseResult.UNSUPPORTED_SCHEME : Lokhttp3/HttpUrl$Builder$ParseResult;
      //   90: areturn
      //   91: aload_1
      //   92: ifnull -> 711
      //   95: aload_0
      //   96: aload_1
      //   97: getfield scheme : Ljava/lang/String;
      //   100: putfield scheme : Ljava/lang/String;
      //   103: aload_2
      //   104: iload_3
      //   105: iload #8
      //   107: invokestatic slashCount : (Ljava/lang/String;II)I
      //   110: istore #4
      //   112: iload #4
      //   114: iconst_2
      //   115: if_icmpge -> 228
      //   118: aload_1
      //   119: ifnull -> 228
      //   122: aload_1
      //   123: getfield scheme : Ljava/lang/String;
      //   126: aload_0
      //   127: getfield scheme : Ljava/lang/String;
      //   130: invokevirtual equals : (Ljava/lang/Object;)Z
      //   133: ifne -> 139
      //   136: goto -> 228
      //   139: aload_0
      //   140: aload_1
      //   141: invokevirtual encodedUsername : ()Ljava/lang/String;
      //   144: putfield encodedUsername : Ljava/lang/String;
      //   147: aload_0
      //   148: aload_1
      //   149: invokevirtual encodedPassword : ()Ljava/lang/String;
      //   152: putfield encodedPassword : Ljava/lang/String;
      //   155: aload_0
      //   156: aload_1
      //   157: getfield host : Ljava/lang/String;
      //   160: putfield host : Ljava/lang/String;
      //   163: aload_0
      //   164: aload_1
      //   165: getfield port : I
      //   168: putfield port : I
      //   171: aload_0
      //   172: getfield encodedPathSegments : Ljava/util/List;
      //   175: invokeinterface clear : ()V
      //   180: aload_0
      //   181: getfield encodedPathSegments : Ljava/util/List;
      //   184: aload_1
      //   185: invokevirtual encodedPathSegments : ()Ljava/util/List;
      //   188: invokeinterface addAll : (Ljava/util/Collection;)Z
      //   193: pop
      //   194: iload_3
      //   195: iload #8
      //   197: if_icmpeq -> 213
      //   200: iload_3
      //   201: istore #4
      //   203: aload_2
      //   204: iload_3
      //   205: invokevirtual charAt : (I)C
      //   208: bipush #35
      //   210: if_icmpne -> 591
      //   213: aload_0
      //   214: aload_1
      //   215: invokevirtual encodedQuery : ()Ljava/lang/String;
      //   218: invokevirtual encodedQuery : (Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;
      //   221: pop
      //   222: iload_3
      //   223: istore #4
      //   225: goto -> 591
      //   228: iload_3
      //   229: iload #4
      //   231: iadd
      //   232: istore #5
      //   234: iconst_0
      //   235: istore_3
      //   236: iconst_0
      //   237: istore #4
      //   239: aload_2
      //   240: iload #5
      //   242: iload #8
      //   244: ldc_w '@/\?#'
      //   247: invokestatic delimiterOffset : (Ljava/lang/String;IILjava/lang/String;)I
      //   250: istore #7
      //   252: iload #7
      //   254: iload #8
      //   256: if_icmpeq -> 270
      //   259: aload_2
      //   260: iload #7
      //   262: invokevirtual charAt : (I)C
      //   265: istore #6
      //   267: goto -> 273
      //   270: iconst_m1
      //   271: istore #6
      //   273: iload #6
      //   275: iconst_m1
      //   276: if_icmpeq -> 498
      //   279: iload #6
      //   281: bipush #35
      //   283: if_icmpeq -> 498
      //   286: iload #6
      //   288: bipush #47
      //   290: if_icmpeq -> 498
      //   293: iload #6
      //   295: bipush #92
      //   297: if_icmpeq -> 498
      //   300: iload #6
      //   302: bipush #63
      //   304: if_icmpeq -> 498
      //   307: iload #6
      //   309: bipush #64
      //   311: if_icmpeq -> 317
      //   314: goto -> 495
      //   317: iload_3
      //   318: ifne -> 436
      //   321: aload_2
      //   322: iload #5
      //   324: iload #7
      //   326: bipush #58
      //   328: invokestatic delimiterOffset : (Ljava/lang/String;IIC)I
      //   331: istore #6
      //   333: aload_2
      //   334: iload #5
      //   336: iload #6
      //   338: ldc ' "':;<=>@[]^`{}|/\?#'
      //   340: iconst_1
      //   341: iconst_0
      //   342: iconst_0
      //   343: iconst_1
      //   344: aconst_null
      //   345: invokestatic canonicalize : (Ljava/lang/String;IILjava/lang/String;ZZZZLjava/nio/charset/Charset;)Ljava/lang/String;
      //   348: astore #9
      //   350: aload #9
      //   352: astore_1
      //   353: iload #4
      //   355: ifeq -> 395
      //   358: new java/lang/StringBuilder
      //   361: dup
      //   362: invokespecial <init> : ()V
      //   365: astore_1
      //   366: aload_1
      //   367: aload_0
      //   368: getfield encodedUsername : Ljava/lang/String;
      //   371: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   374: pop
      //   375: aload_1
      //   376: ldc_w '%40'
      //   379: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   382: pop
      //   383: aload_1
      //   384: aload #9
      //   386: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   389: pop
      //   390: aload_1
      //   391: invokevirtual toString : ()Ljava/lang/String;
      //   394: astore_1
      //   395: aload_0
      //   396: aload_1
      //   397: putfield encodedUsername : Ljava/lang/String;
      //   400: iload #6
      //   402: iload #7
      //   404: if_icmpeq -> 430
      //   407: aload_0
      //   408: aload_2
      //   409: iload #6
      //   411: iconst_1
      //   412: iadd
      //   413: iload #7
      //   415: ldc ' "':;<=>@[]^`{}|/\?#'
      //   417: iconst_1
      //   418: iconst_0
      //   419: iconst_0
      //   420: iconst_1
      //   421: aconst_null
      //   422: invokestatic canonicalize : (Ljava/lang/String;IILjava/lang/String;ZZZZLjava/nio/charset/Charset;)Ljava/lang/String;
      //   425: putfield encodedPassword : Ljava/lang/String;
      //   428: iconst_1
      //   429: istore_3
      //   430: iconst_1
      //   431: istore #4
      //   433: goto -> 489
      //   436: new java/lang/StringBuilder
      //   439: dup
      //   440: invokespecial <init> : ()V
      //   443: astore_1
      //   444: aload_1
      //   445: aload_0
      //   446: getfield encodedPassword : Ljava/lang/String;
      //   449: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   452: pop
      //   453: aload_1
      //   454: ldc_w '%40'
      //   457: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   460: pop
      //   461: aload_1
      //   462: aload_2
      //   463: iload #5
      //   465: iload #7
      //   467: ldc ' "':;<=>@[]^`{}|/\?#'
      //   469: iconst_1
      //   470: iconst_0
      //   471: iconst_0
      //   472: iconst_1
      //   473: aconst_null
      //   474: invokestatic canonicalize : (Ljava/lang/String;IILjava/lang/String;ZZZZLjava/nio/charset/Charset;)Ljava/lang/String;
      //   477: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   480: pop
      //   481: aload_0
      //   482: aload_1
      //   483: invokevirtual toString : ()Ljava/lang/String;
      //   486: putfield encodedPassword : Ljava/lang/String;
      //   489: iload #7
      //   491: iconst_1
      //   492: iadd
      //   493: istore #5
      //   495: goto -> 239
      //   498: aload_2
      //   499: iload #5
      //   501: iload #7
      //   503: invokestatic portColonOffset : (Ljava/lang/String;II)I
      //   506: istore #4
      //   508: iload #4
      //   510: iconst_1
      //   511: iadd
      //   512: istore_3
      //   513: iload_3
      //   514: iload #7
      //   516: if_icmpge -> 553
      //   519: aload_0
      //   520: aload_2
      //   521: iload #5
      //   523: iload #4
      //   525: invokestatic canonicalizeHost : (Ljava/lang/String;II)Ljava/lang/String;
      //   528: putfield host : Ljava/lang/String;
      //   531: aload_2
      //   532: iload_3
      //   533: iload #7
      //   535: invokestatic parsePort : (Ljava/lang/String;II)I
      //   538: istore_3
      //   539: aload_0
      //   540: iload_3
      //   541: putfield port : I
      //   544: iload_3
      //   545: iconst_m1
      //   546: if_icmpne -> 576
      //   549: getstatic okhttp3/HttpUrl$Builder$ParseResult.INVALID_PORT : Lokhttp3/HttpUrl$Builder$ParseResult;
      //   552: areturn
      //   553: aload_0
      //   554: aload_2
      //   555: iload #5
      //   557: iload #4
      //   559: invokestatic canonicalizeHost : (Ljava/lang/String;II)Ljava/lang/String;
      //   562: putfield host : Ljava/lang/String;
      //   565: aload_0
      //   566: aload_0
      //   567: getfield scheme : Ljava/lang/String;
      //   570: invokestatic defaultPort : (Ljava/lang/String;)I
      //   573: putfield port : I
      //   576: aload_0
      //   577: getfield host : Ljava/lang/String;
      //   580: ifnonnull -> 587
      //   583: getstatic okhttp3/HttpUrl$Builder$ParseResult.INVALID_HOST : Lokhttp3/HttpUrl$Builder$ParseResult;
      //   586: areturn
      //   587: iload #7
      //   589: istore #4
      //   591: aload_2
      //   592: iload #4
      //   594: iload #8
      //   596: ldc_w '?#'
      //   599: invokestatic delimiterOffset : (Ljava/lang/String;IILjava/lang/String;)I
      //   602: istore #5
      //   604: aload_0
      //   605: aload_2
      //   606: iload #4
      //   608: iload #5
      //   610: invokespecial resolvePath : (Ljava/lang/String;II)V
      //   613: iload #5
      //   615: istore_3
      //   616: iload #5
      //   618: iload #8
      //   620: if_icmpge -> 671
      //   623: iload #5
      //   625: istore_3
      //   626: aload_2
      //   627: iload #5
      //   629: invokevirtual charAt : (I)C
      //   632: bipush #63
      //   634: if_icmpne -> 671
      //   637: aload_2
      //   638: iload #5
      //   640: iload #8
      //   642: bipush #35
      //   644: invokestatic delimiterOffset : (Ljava/lang/String;IIC)I
      //   647: istore_3
      //   648: aload_0
      //   649: aload_2
      //   650: iload #5
      //   652: iconst_1
      //   653: iadd
      //   654: iload_3
      //   655: ldc ' "'<>#'
      //   657: iconst_1
      //   658: iconst_0
      //   659: iconst_1
      //   660: iconst_1
      //   661: aconst_null
      //   662: invokestatic canonicalize : (Ljava/lang/String;IILjava/lang/String;ZZZZLjava/nio/charset/Charset;)Ljava/lang/String;
      //   665: invokestatic queryStringToNamesAndValues : (Ljava/lang/String;)Ljava/util/List;
      //   668: putfield encodedQueryNamesAndValues : Ljava/util/List;
      //   671: iload_3
      //   672: iload #8
      //   674: if_icmpge -> 707
      //   677: aload_2
      //   678: iload_3
      //   679: invokevirtual charAt : (I)C
      //   682: bipush #35
      //   684: if_icmpne -> 707
      //   687: aload_0
      //   688: aload_2
      //   689: iconst_1
      //   690: iload_3
      //   691: iadd
      //   692: iload #8
      //   694: ldc ''
      //   696: iconst_1
      //   697: iconst_0
      //   698: iconst_0
      //   699: iconst_0
      //   700: aconst_null
      //   701: invokestatic canonicalize : (Ljava/lang/String;IILjava/lang/String;ZZZZLjava/nio/charset/Charset;)Ljava/lang/String;
      //   704: putfield encodedFragment : Ljava/lang/String;
      //   707: getstatic okhttp3/HttpUrl$Builder$ParseResult.SUCCESS : Lokhttp3/HttpUrl$Builder$ParseResult;
      //   710: areturn
      //   711: getstatic okhttp3/HttpUrl$Builder$ParseResult.MISSING_SCHEME : Lokhttp3/HttpUrl$Builder$ParseResult;
      //   714: areturn
    }
    
    public Builder password(String param1String) {
      if (param1String != null) {
        this.encodedPassword = HttpUrl.canonicalize(param1String, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
        return this;
      } 
      throw new NullPointerException("password == null");
    }
    
    public Builder port(int param1Int) {
      if (param1Int > 0 && param1Int <= 65535) {
        this.port = param1Int;
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unexpected port: ");
      stringBuilder.append(param1Int);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder query(@Nullable String param1String) {
      if (param1String != null) {
        List<String> list = HttpUrl.queryStringToNamesAndValues(HttpUrl.canonicalize(param1String, " \"'<>#", false, false, true, true));
      } else {
        param1String = null;
      } 
      this.encodedQueryNamesAndValues = (List<String>)param1String;
      return this;
    }
    
    Builder reencodeForUri() {
      int i = this.encodedPathSegments.size();
      boolean bool = false;
      byte b;
      for (b = 0; b < i; b++) {
        String str1 = this.encodedPathSegments.get(b);
        this.encodedPathSegments.set(b, HttpUrl.canonicalize(str1, "[]", true, true, false, true));
      } 
      List<String> list = this.encodedQueryNamesAndValues;
      if (list != null) {
        i = list.size();
        for (b = bool; b < i; b++) {
          String str1 = this.encodedQueryNamesAndValues.get(b);
          if (str1 != null)
            this.encodedQueryNamesAndValues.set(b, HttpUrl.canonicalize(str1, "\\^`{|}", true, true, true, true)); 
        } 
      } 
      String str = this.encodedFragment;
      if (str != null)
        this.encodedFragment = HttpUrl.canonicalize(str, " \"#<>\\^`{|}", true, true, false, false); 
      return this;
    }
    
    public Builder removeAllEncodedQueryParameters(String param1String) {
      if (param1String != null) {
        if (this.encodedQueryNamesAndValues == null)
          return this; 
        removeAllCanonicalQueryParameters(HttpUrl.canonicalize(param1String, " \"'<>#&=", true, false, true, true));
        return this;
      } 
      throw new NullPointerException("encodedName == null");
    }
    
    public Builder removeAllQueryParameters(String param1String) {
      if (param1String != null) {
        if (this.encodedQueryNamesAndValues == null)
          return this; 
        removeAllCanonicalQueryParameters(HttpUrl.canonicalize(param1String, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
        return this;
      } 
      throw new NullPointerException("name == null");
    }
    
    public Builder removePathSegment(int param1Int) {
      this.encodedPathSegments.remove(param1Int);
      if (this.encodedPathSegments.isEmpty())
        this.encodedPathSegments.add(""); 
      return this;
    }
    
    public Builder scheme(String param1String) {
      if (param1String != null) {
        if (param1String.equalsIgnoreCase("http")) {
          this.scheme = "http";
        } else {
          if (param1String.equalsIgnoreCase("https")) {
            this.scheme = "https";
            return this;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("unexpected scheme: ");
          stringBuilder.append(param1String);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return this;
      } 
      throw new NullPointerException("scheme == null");
    }
    
    public Builder setEncodedPathSegment(int param1Int, String param1String) {
      if (param1String != null) {
        String str = HttpUrl.canonicalize(param1String, 0, param1String.length(), " \"<>^`{}|/\\?#", true, false, false, true, null);
        this.encodedPathSegments.set(param1Int, str);
        if (!isDot(str) && !isDotDot(str))
          return this; 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected path segment: ");
        stringBuilder.append(param1String);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new NullPointerException("encodedPathSegment == null");
    }
    
    public Builder setEncodedQueryParameter(String param1String1, @Nullable String param1String2) {
      removeAllEncodedQueryParameters(param1String1);
      addEncodedQueryParameter(param1String1, param1String2);
      return this;
    }
    
    public Builder setPathSegment(int param1Int, String param1String) {
      if (param1String != null) {
        String str = HttpUrl.canonicalize(param1String, 0, param1String.length(), " \"<>^`{}|/\\?#", false, false, false, true, null);
        if (!isDot(str) && !isDotDot(str)) {
          this.encodedPathSegments.set(param1Int, str);
          return this;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected path segment: ");
        stringBuilder.append(param1String);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new NullPointerException("pathSegment == null");
    }
    
    public Builder setQueryParameter(String param1String1, @Nullable String param1String2) {
      removeAllQueryParameters(param1String1);
      addQueryParameter(param1String1, param1String2);
      return this;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.scheme);
      stringBuilder.append("://");
      if (!this.encodedUsername.isEmpty() || !this.encodedPassword.isEmpty()) {
        stringBuilder.append(this.encodedUsername);
        if (!this.encodedPassword.isEmpty()) {
          stringBuilder.append(':');
          stringBuilder.append(this.encodedPassword);
        } 
        stringBuilder.append('@');
      } 
      if (this.host.indexOf(':') != -1) {
        stringBuilder.append('[');
        stringBuilder.append(this.host);
        stringBuilder.append(']');
      } else {
        stringBuilder.append(this.host);
      } 
      int i = effectivePort();
      if (i != HttpUrl.defaultPort(this.scheme)) {
        stringBuilder.append(':');
        stringBuilder.append(i);
      } 
      HttpUrl.pathSegmentsToString(stringBuilder, this.encodedPathSegments);
      if (this.encodedQueryNamesAndValues != null) {
        stringBuilder.append('?');
        HttpUrl.namesAndValuesToQueryString(stringBuilder, this.encodedQueryNamesAndValues);
      } 
      if (this.encodedFragment != null) {
        stringBuilder.append('#');
        stringBuilder.append(this.encodedFragment);
      } 
      return stringBuilder.toString();
    }
    
    public Builder username(String param1String) {
      if (param1String != null) {
        this.encodedUsername = HttpUrl.canonicalize(param1String, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
        return this;
      } 
      throw new NullPointerException("username == null");
    }
    
    enum ParseResult {
      INVALID_HOST, INVALID_PORT, MISSING_SCHEME, SUCCESS, UNSUPPORTED_SCHEME;
      
      private static final ParseResult[] $VALUES;
      
      static {
        ParseResult parseResult = new ParseResult("INVALID_HOST", 4);
        INVALID_HOST = parseResult;
        $VALUES = new ParseResult[] { SUCCESS, MISSING_SCHEME, UNSUPPORTED_SCHEME, INVALID_PORT, parseResult };
      }
    }
  }
  
  enum ParseResult {
    INVALID_HOST, INVALID_PORT, MISSING_SCHEME, SUCCESS, UNSUPPORTED_SCHEME;
    
    private static final ParseResult[] $VALUES;
    
    static {
      INVALID_PORT = new ParseResult("INVALID_PORT", 3);
      ParseResult parseResult = new ParseResult("INVALID_HOST", 4);
      INVALID_HOST = parseResult;
      $VALUES = new ParseResult[] { SUCCESS, MISSING_SCHEME, UNSUPPORTED_SCHEME, INVALID_PORT, parseResult };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\HttpUrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */