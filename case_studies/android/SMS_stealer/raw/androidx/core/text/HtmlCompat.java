package androidx.core.text;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

public final class HtmlCompat {
  public static final int FROM_HTML_MODE_COMPACT = 63;
  
  public static final int FROM_HTML_MODE_LEGACY = 0;
  
  public static final int FROM_HTML_OPTION_USE_CSS_COLORS = 256;
  
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE = 32;
  
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_DIV = 16;
  
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_HEADING = 2;
  
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST = 8;
  
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM = 4;
  
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH = 1;
  
  public static final int TO_HTML_PARAGRAPH_LINES_CONSECUTIVE = 0;
  
  public static final int TO_HTML_PARAGRAPH_LINES_INDIVIDUAL = 1;
  
  public static Spanned fromHtml(String paramString, int paramInt) {
    return (Build.VERSION.SDK_INT >= 24) ? Html.fromHtml(paramString, paramInt) : Html.fromHtml(paramString);
  }
  
  public static Spanned fromHtml(String paramString, int paramInt, Html.ImageGetter paramImageGetter, Html.TagHandler paramTagHandler) {
    return (Build.VERSION.SDK_INT >= 24) ? Html.fromHtml(paramString, paramInt, paramImageGetter, paramTagHandler) : Html.fromHtml(paramString, paramImageGetter, paramTagHandler);
  }
  
  public static String toHtml(Spanned paramSpanned, int paramInt) {
    return (Build.VERSION.SDK_INT >= 24) ? Html.toHtml(paramSpanned, paramInt) : Html.toHtml(paramSpanned);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\text\HtmlCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */