package okhttp3.internal.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public final class HttpHeaders {
  private static final Pattern PARAMETER = Pattern.compile(" +([^ \"=]*)=(:?\"([^\"]*)\"|([^ \"=]*)) *(:?,|$)");
  
  private static final String QUOTED_STRING = "\"([^\"]*)\"";
  
  private static final String TOKEN = "([^ \"=]*)";
  
  public static long contentLength(Headers paramHeaders) {
    return stringToLong(paramHeaders.get("Content-Length"));
  }
  
  public static long contentLength(Response paramResponse) {
    return contentLength(paramResponse.headers());
  }
  
  public static boolean hasBody(Response paramResponse) {
    if (paramResponse.request().method().equals("HEAD"))
      return false; 
    int i = paramResponse.code();
    return ((i < 100 || i >= 200) && i != 204 && i != 304) ? true : ((contentLength(paramResponse) != -1L || "chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding"))));
  }
  
  public static boolean hasVaryAll(Headers paramHeaders) {
    return varyFields(paramHeaders).contains("*");
  }
  
  public static boolean hasVaryAll(Response paramResponse) {
    return hasVaryAll(paramResponse.headers());
  }
  
  public static List<Challenge> parseChallenges(Headers paramHeaders, String paramString) {
    ArrayList<Challenge> arrayList = new ArrayList();
    for (String str3 : paramHeaders.values(paramString)) {
      String str1;
      String str2;
      int i = str3.indexOf(' ');
      if (i == -1)
        continue; 
      String str4 = str3.substring(0, i);
      Matcher matcher = PARAMETER.matcher(str3);
      paramString = null;
      paramHeaders = null;
      while (true) {
        str2 = paramString;
        Headers headers = paramHeaders;
        if (matcher.find(i)) {
          String str;
          if (str3.regionMatches(true, matcher.start(1), "realm", 0, 5)) {
            str1 = matcher.group(3);
          } else {
            str1 = paramString;
            if (str3.regionMatches(true, matcher.start(1), "charset", 0, 7)) {
              str = matcher.group(3);
              str1 = paramString;
            } 
          } 
          if (str1 != null && str != null) {
            str2 = str1;
            str1 = str;
            break;
          } 
          i = matcher.end();
          paramString = str1;
          continue;
        } 
        break;
      } 
      if (str2 == null)
        continue; 
      Challenge challenge2 = new Challenge(str4, str2);
      Challenge challenge1 = challenge2;
      if (str1 != null)
        if (str1.equalsIgnoreCase("UTF-8")) {
          challenge1 = challenge2.withCharset(Util.UTF_8);
        } else {
          continue;
        }  
      arrayList.add(challenge1);
    } 
    return arrayList;
  }
  
  public static int parseSeconds(String paramString, int paramInt) {
    try {
      long l = Long.parseLong(paramString);
      if (l > 2147483647L)
        return Integer.MAX_VALUE; 
      if (l < 0L)
        return 0; 
      paramInt = (int)l;
    } catch (NumberFormatException numberFormatException) {}
    return paramInt;
  }
  
  public static void receiveHeaders(CookieJar paramCookieJar, HttpUrl paramHttpUrl, Headers paramHeaders) {
    if (paramCookieJar == CookieJar.NO_COOKIES)
      return; 
    List list = Cookie.parseAll(paramHttpUrl, paramHeaders);
    if (list.isEmpty())
      return; 
    paramCookieJar.saveFromResponse(paramHttpUrl, list);
  }
  
  public static int skipUntil(String paramString1, int paramInt, String paramString2) {
    while (paramInt < paramString1.length() && paramString2.indexOf(paramString1.charAt(paramInt)) == -1)
      paramInt++; 
    return paramInt;
  }
  
  public static int skipWhitespace(String paramString, int paramInt) {
    while (paramInt < paramString.length()) {
      char c = paramString.charAt(paramInt);
      if (c != ' ' && c != '\t')
        break; 
      paramInt++;
    } 
    return paramInt;
  }
  
  private static long stringToLong(String paramString) {
    long l1;
    long l2 = -1L;
    if (paramString == null)
      return -1L; 
    try {
      l1 = Long.parseLong(paramString);
    } catch (NumberFormatException numberFormatException) {
      l1 = l2;
    } 
    return l1;
  }
  
  public static Set<String> varyFields(Headers paramHeaders) {
    Set<?> set = Collections.emptySet();
    int i = paramHeaders.size();
    for (byte b = 0; b < i; b++) {
      if ("Vary".equalsIgnoreCase(paramHeaders.name(b))) {
        String str = paramHeaders.value(b);
        Set<?> set1 = set;
        if (set.isEmpty())
          set1 = new TreeSet(String.CASE_INSENSITIVE_ORDER); 
        String[] arrayOfString = str.split(",");
        int j = arrayOfString.length;
        byte b1 = 0;
        while (true) {
          set = set1;
          if (b1 < j) {
            set1.add(arrayOfString[b1].trim());
            b1++;
            continue;
          } 
          break;
        } 
      } 
    } 
    return (Set)set;
  }
  
  private static Set<String> varyFields(Response paramResponse) {
    return varyFields(paramResponse.headers());
  }
  
  public static Headers varyHeaders(Headers paramHeaders1, Headers paramHeaders2) {
    Set<String> set = varyFields(paramHeaders2);
    if (set.isEmpty())
      return (new Headers.Builder()).build(); 
    Headers.Builder builder = new Headers.Builder();
    byte b = 0;
    int i = paramHeaders1.size();
    while (b < i) {
      String str = paramHeaders1.name(b);
      if (set.contains(str))
        builder.add(str, paramHeaders1.value(b)); 
      b++;
    } 
    return builder.build();
  }
  
  public static Headers varyHeaders(Response paramResponse) {
    return varyHeaders(paramResponse.networkResponse().request().headers(), paramResponse.headers());
  }
  
  public static boolean varyMatches(Response paramResponse, Headers paramHeaders, Request paramRequest) {
    for (String str : varyFields(paramResponse)) {
      if (!Util.equal(paramHeaders.values(str), paramRequest.headers(str)))
        return false; 
    } 
    return true;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\HttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */