package androidx.core.text.util;

import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.core.util.PatternsCompat;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat {
  private static final Comparator<LinkSpec> COMPARATOR;
  
  private static final String[] EMPTY_STRING = new String[0];
  
  static {
    COMPARATOR = new Comparator<LinkSpec>() {
        public int compare(LinkifyCompat.LinkSpec param1LinkSpec1, LinkifyCompat.LinkSpec param1LinkSpec2) {
          return (param1LinkSpec1.start < param1LinkSpec2.start) ? -1 : ((param1LinkSpec1.start > param1LinkSpec2.start) ? 1 : ((param1LinkSpec1.end < param1LinkSpec2.end) ? 1 : ((param1LinkSpec1.end > param1LinkSpec2.end) ? -1 : 0)));
        }
      };
  }
  
  private static void addLinkMovementMethod(TextView paramTextView) {
    if (!(paramTextView.getMovementMethod() instanceof LinkMovementMethod) && paramTextView.getLinksClickable())
      paramTextView.setMovementMethod(LinkMovementMethod.getInstance()); 
  }
  
  public static void addLinks(TextView paramTextView, Pattern paramPattern, String paramString) {
    if (shouldAddLinksFallbackToFramework()) {
      Linkify.addLinks(paramTextView, paramPattern, paramString);
      return;
    } 
    addLinks(paramTextView, paramPattern, paramString, (String[])null, (Linkify.MatchFilter)null, (Linkify.TransformFilter)null);
  }
  
  public static void addLinks(TextView paramTextView, Pattern paramPattern, String paramString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter) {
    if (shouldAddLinksFallbackToFramework()) {
      Linkify.addLinks(paramTextView, paramPattern, paramString, paramMatchFilter, paramTransformFilter);
      return;
    } 
    addLinks(paramTextView, paramPattern, paramString, (String[])null, paramMatchFilter, paramTransformFilter);
  }
  
  public static void addLinks(TextView paramTextView, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter) {
    if (shouldAddLinksFallbackToFramework()) {
      Linkify.addLinks(paramTextView, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter);
      return;
    } 
    SpannableString spannableString = SpannableString.valueOf(paramTextView.getText());
    if (addLinks((Spannable)spannableString, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter)) {
      paramTextView.setText((CharSequence)spannableString);
      addLinkMovementMethod(paramTextView);
    } 
  }
  
  public static boolean addLinks(Spannable paramSpannable, int paramInt) {
    if (shouldAddLinksFallbackToFramework())
      return Linkify.addLinks(paramSpannable, paramInt); 
    if (paramInt == 0)
      return false; 
    URLSpan[] arrayOfURLSpan = (URLSpan[])paramSpannable.getSpans(0, paramSpannable.length(), URLSpan.class);
    for (int i = arrayOfURLSpan.length - 1; i >= 0; i--)
      paramSpannable.removeSpan(arrayOfURLSpan[i]); 
    if ((paramInt & 0x4) != 0)
      Linkify.addLinks(paramSpannable, 4); 
    ArrayList<LinkSpec> arrayList = new ArrayList();
    if ((paramInt & 0x1) != 0) {
      Pattern pattern = PatternsCompat.AUTOLINK_WEB_URL;
      Linkify.MatchFilter matchFilter = Linkify.sUrlMatchFilter;
      gatherLinks(arrayList, paramSpannable, pattern, new String[] { "http://", "https://", "rtsp://" }, matchFilter, null);
    } 
    if ((paramInt & 0x2) != 0)
      gatherLinks(arrayList, paramSpannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[] { "mailto:" }, null, null); 
    if ((paramInt & 0x8) != 0)
      gatherMapLinks(arrayList, paramSpannable); 
    pruneOverlaps(arrayList, paramSpannable);
    if (arrayList.size() == 0)
      return false; 
    for (LinkSpec linkSpec : arrayList) {
      if (linkSpec.frameworkAddedSpan == null)
        applyLink(linkSpec.url, linkSpec.start, linkSpec.end, paramSpannable); 
    } 
    return true;
  }
  
  public static boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString) {
    return shouldAddLinksFallbackToFramework() ? Linkify.addLinks(paramSpannable, paramPattern, paramString) : addLinks(paramSpannable, paramPattern, paramString, (String[])null, (Linkify.MatchFilter)null, (Linkify.TransformFilter)null);
  }
  
  public static boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter) {
    return shouldAddLinksFallbackToFramework() ? Linkify.addLinks(paramSpannable, paramPattern, paramString, paramMatchFilter, paramTransformFilter) : addLinks(paramSpannable, paramPattern, paramString, (String[])null, paramMatchFilter, paramTransformFilter);
  }
  
  public static boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter) {
    // Byte code:
    //   0: invokestatic shouldAddLinksFallbackToFramework : ()Z
    //   3: ifeq -> 18
    //   6: aload_0
    //   7: aload_1
    //   8: aload_2
    //   9: aload_3
    //   10: aload #4
    //   12: aload #5
    //   14: invokestatic addLinks : (Landroid/text/Spannable;Ljava/util/regex/Pattern;Ljava/lang/String;[Ljava/lang/String;Landroid/text/util/Linkify$MatchFilter;Landroid/text/util/Linkify$TransformFilter;)Z
    //   17: ireturn
    //   18: aload_2
    //   19: astore #10
    //   21: aload_2
    //   22: ifnonnull -> 29
    //   25: ldc ''
    //   27: astore #10
    //   29: aload_3
    //   30: ifnull -> 41
    //   33: aload_3
    //   34: astore_2
    //   35: aload_3
    //   36: arraylength
    //   37: iconst_1
    //   38: if_icmpge -> 45
    //   41: getstatic androidx/core/text/util/LinkifyCompat.EMPTY_STRING : [Ljava/lang/String;
    //   44: astore_2
    //   45: aload_2
    //   46: arraylength
    //   47: iconst_1
    //   48: iadd
    //   49: anewarray java/lang/String
    //   52: astore #11
    //   54: aload #11
    //   56: iconst_0
    //   57: aload #10
    //   59: getstatic java/util/Locale.ROOT : Ljava/util/Locale;
    //   62: invokevirtual toLowerCase : (Ljava/util/Locale;)Ljava/lang/String;
    //   65: aastore
    //   66: iconst_0
    //   67: istore #6
    //   69: iload #6
    //   71: aload_2
    //   72: arraylength
    //   73: if_icmpge -> 111
    //   76: aload_2
    //   77: iload #6
    //   79: aaload
    //   80: astore_3
    //   81: iinc #6, 1
    //   84: aload_3
    //   85: ifnonnull -> 94
    //   88: ldc ''
    //   90: astore_3
    //   91: goto -> 102
    //   94: aload_3
    //   95: getstatic java/util/Locale.ROOT : Ljava/util/Locale;
    //   98: invokevirtual toLowerCase : (Ljava/util/Locale;)Ljava/lang/String;
    //   101: astore_3
    //   102: aload #11
    //   104: iload #6
    //   106: aload_3
    //   107: aastore
    //   108: goto -> 69
    //   111: aload_1
    //   112: aload_0
    //   113: invokevirtual matcher : (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   116: astore_1
    //   117: iconst_0
    //   118: istore #8
    //   120: aload_1
    //   121: invokevirtual find : ()Z
    //   124: ifeq -> 196
    //   127: aload_1
    //   128: invokevirtual start : ()I
    //   131: istore #6
    //   133: aload_1
    //   134: invokevirtual end : ()I
    //   137: istore #7
    //   139: aload #4
    //   141: ifnull -> 161
    //   144: aload #4
    //   146: aload_0
    //   147: iload #6
    //   149: iload #7
    //   151: invokeinterface acceptMatch : (Ljava/lang/CharSequence;II)Z
    //   156: istore #9
    //   158: goto -> 164
    //   161: iconst_1
    //   162: istore #9
    //   164: iload #9
    //   166: ifeq -> 120
    //   169: aload_1
    //   170: iconst_0
    //   171: invokevirtual group : (I)Ljava/lang/String;
    //   174: aload #11
    //   176: aload_1
    //   177: aload #5
    //   179: invokestatic makeUrl : (Ljava/lang/String;[Ljava/lang/String;Ljava/util/regex/Matcher;Landroid/text/util/Linkify$TransformFilter;)Ljava/lang/String;
    //   182: iload #6
    //   184: iload #7
    //   186: aload_0
    //   187: invokestatic applyLink : (Ljava/lang/String;IILandroid/text/Spannable;)V
    //   190: iconst_1
    //   191: istore #8
    //   193: goto -> 120
    //   196: iload #8
    //   198: ireturn
  }
  
  public static boolean addLinks(TextView paramTextView, int paramInt) {
    if (shouldAddLinksFallbackToFramework())
      return Linkify.addLinks(paramTextView, paramInt); 
    if (paramInt == 0)
      return false; 
    CharSequence charSequence = paramTextView.getText();
    if (charSequence instanceof Spannable) {
      if (addLinks((Spannable)charSequence, paramInt)) {
        addLinkMovementMethod(paramTextView);
        return true;
      } 
      return false;
    } 
    SpannableString spannableString = SpannableString.valueOf(charSequence);
    if (addLinks((Spannable)spannableString, paramInt)) {
      addLinkMovementMethod(paramTextView);
      paramTextView.setText((CharSequence)spannableString);
      return true;
    } 
    return false;
  }
  
  private static void applyLink(String paramString, int paramInt1, int paramInt2, Spannable paramSpannable) {
    paramSpannable.setSpan(new URLSpan(paramString), paramInt1, paramInt2, 33);
  }
  
  private static String findAddress(String paramString) {
    return (Build.VERSION.SDK_INT >= 28) ? WebView.findAddress(paramString) : FindAddress.findAddress(paramString);
  }
  
  private static void gatherLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable, Pattern paramPattern, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter) {
    Matcher matcher = paramPattern.matcher((CharSequence)paramSpannable);
    while (matcher.find()) {
      int j = matcher.start();
      int i = matcher.end();
      if (paramMatchFilter == null || paramMatchFilter.acceptMatch((CharSequence)paramSpannable, j, i)) {
        LinkSpec linkSpec = new LinkSpec();
        linkSpec.url = makeUrl(matcher.group(0), paramArrayOfString, matcher, paramTransformFilter);
        linkSpec.start = j;
        linkSpec.end = i;
        paramArrayList.add(linkSpec);
      } 
    } 
  }
  
  private static void gatherMapLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable) {
    String str = paramSpannable.toString();
    int i = 0;
    while (true) {
      try {
        String str1 = findAddress(str);
        if (str1 != null) {
          int j = str.indexOf(str1);
          if (j >= 0) {
            LinkSpec linkSpec = new LinkSpec();
            this();
            int k = str1.length() + j;
            linkSpec.start = j + i;
            i += k;
            linkSpec.end = i;
            str = str.substring(k);
            try {
              str1 = URLEncoder.encode(str1, "UTF-8");
              StringBuilder stringBuilder = new StringBuilder();
              this();
              stringBuilder.append("geo:0,0?q=");
              stringBuilder.append(str1);
              linkSpec.url = stringBuilder.toString();
              paramArrayList.add(linkSpec);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {}
            continue;
          } 
        } 
      } catch (UnsupportedOperationException unsupportedOperationException) {}
      return;
    } 
  }
  
  private static String makeUrl(String paramString, String[] paramArrayOfString, Matcher paramMatcher, Linkify.TransformFilter paramTransformFilter) {
    int i;
    String str2 = paramString;
    if (paramTransformFilter != null)
      str2 = paramTransformFilter.transformUrl(paramMatcher, paramString); 
    byte b = 0;
    while (true) {
      i = paramArrayOfString.length;
      boolean bool = true;
      if (b < i) {
        if (str2.regionMatches(true, 0, paramArrayOfString[b], 0, paramArrayOfString[b].length())) {
          i = bool;
          paramString = str2;
          if (!str2.regionMatches(false, 0, paramArrayOfString[b], 0, paramArrayOfString[b].length())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(paramArrayOfString[b]);
            stringBuilder.append(str2.substring(paramArrayOfString[b].length()));
            String str = stringBuilder.toString();
            i = bool;
          } 
          break;
        } 
        b++;
        continue;
      } 
      i = 0;
      paramString = str2;
      break;
    } 
    String str1 = paramString;
    if (i == 0) {
      str1 = paramString;
      if (paramArrayOfString.length > 0) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramArrayOfString[0]);
        stringBuilder.append(paramString);
        str1 = stringBuilder.toString();
      } 
    } 
    return str1;
  }
  
  private static void pruneOverlaps(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable) {
    int i = paramSpannable.length();
    int j = 0;
    URLSpan[] arrayOfURLSpan = (URLSpan[])paramSpannable.getSpans(0, i, URLSpan.class);
    for (i = 0; i < arrayOfURLSpan.length; i++) {
      LinkSpec linkSpec = new LinkSpec();
      linkSpec.frameworkAddedSpan = arrayOfURLSpan[i];
      linkSpec.start = paramSpannable.getSpanStart(arrayOfURLSpan[i]);
      linkSpec.end = paramSpannable.getSpanEnd(arrayOfURLSpan[i]);
      paramArrayList.add(linkSpec);
    } 
    Collections.sort(paramArrayList, COMPARATOR);
    int k = paramArrayList.size();
    for (i = j; i < k - 1; i = m) {
      LinkSpec linkSpec1 = paramArrayList.get(i);
      int m = i + 1;
      LinkSpec linkSpec2 = paramArrayList.get(m);
      if (linkSpec1.start <= linkSpec2.start && linkSpec1.end > linkSpec2.start) {
        if (linkSpec2.end <= linkSpec1.end || linkSpec1.end - linkSpec1.start > linkSpec2.end - linkSpec2.start) {
          j = m;
        } else if (linkSpec1.end - linkSpec1.start < linkSpec2.end - linkSpec2.start) {
          j = i;
        } else {
          j = -1;
        } 
        if (j != -1) {
          URLSpan uRLSpan = ((LinkSpec)paramArrayList.get(j)).frameworkAddedSpan;
          if (uRLSpan != null)
            paramSpannable.removeSpan(uRLSpan); 
          paramArrayList.remove(j);
          k--;
          continue;
        } 
      } 
    } 
  }
  
  private static boolean shouldAddLinksFallbackToFramework() {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 28) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static class LinkSpec {
    int end;
    
    URLSpan frameworkAddedSpan;
    
    int start;
    
    String url;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface LinkifyMask {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\tex\\util\LinkifyCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */