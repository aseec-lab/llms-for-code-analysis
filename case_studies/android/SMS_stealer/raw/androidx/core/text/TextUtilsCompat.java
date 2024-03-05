package androidx.core.text;

import android.os.Build;
import android.text.TextUtils;
import java.util.Locale;

public final class TextUtilsCompat {
  private static final String ARAB_SCRIPT_SUBTAG = "Arab";
  
  private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
  
  private static final Locale ROOT = new Locale("", "");
  
  private static int getLayoutDirectionFromFirstChar(Locale paramLocale) {
    byte b = Character.getDirectionality(paramLocale.getDisplayName(paramLocale).charAt(0));
    return (b != 1 && b != 2) ? 0 : 1;
  }
  
  public static int getLayoutDirectionFromLocale(Locale paramLocale) {
    if (Build.VERSION.SDK_INT >= 17)
      return TextUtils.getLayoutDirectionFromLocale(paramLocale); 
    if (paramLocale != null && !paramLocale.equals(ROOT)) {
      String str = ICUCompat.maximizeAndGetScript(paramLocale);
      if (str == null)
        return getLayoutDirectionFromFirstChar(paramLocale); 
      if (str.equalsIgnoreCase("Arab") || str.equalsIgnoreCase("Hebr"))
        return 1; 
    } 
    return 0;
  }
  
  public static String htmlEncode(String paramString) {
    if (Build.VERSION.SDK_INT >= 17)
      return TextUtils.htmlEncode(paramString); 
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c != '"') {
        if (c != '<') {
          if (c != '>') {
            if (c != '&') {
              if (c != '\'') {
                stringBuilder.append(c);
              } else {
                stringBuilder.append("&#39;");
              } 
            } else {
              stringBuilder.append("&amp;");
            } 
          } else {
            stringBuilder.append("&gt;");
          } 
        } else {
          stringBuilder.append("&lt;");
        } 
      } else {
        stringBuilder.append("&quot;");
      } 
    } 
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\text\TextUtilsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */