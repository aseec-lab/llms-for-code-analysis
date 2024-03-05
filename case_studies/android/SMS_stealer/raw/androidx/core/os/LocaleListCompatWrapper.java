package androidx.core.os;

import android.os.Build;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

final class LocaleListCompatWrapper implements LocaleListInterface {
  private static final Locale EN_LATN;
  
  private static final Locale LOCALE_AR_XB;
  
  private static final Locale LOCALE_EN_XA;
  
  private static final Locale[] sEmptyList = new Locale[0];
  
  private final Locale[] mList;
  
  private final String mStringRepresentation;
  
  static {
    LOCALE_EN_XA = new Locale("en", "XA");
    LOCALE_AR_XB = new Locale("ar", "XB");
    EN_LATN = LocaleListCompat.forLanguageTagCompat("en-Latn");
  }
  
  LocaleListCompatWrapper(Locale... paramVarArgs) {
    if (paramVarArgs.length == 0) {
      this.mList = sEmptyList;
      this.mStringRepresentation = "";
    } else {
      Locale[] arrayOfLocale = new Locale[paramVarArgs.length];
      HashSet<Locale> hashSet = new HashSet();
      StringBuilder stringBuilder = new StringBuilder();
      byte b = 0;
      while (b < paramVarArgs.length) {
        Locale locale = paramVarArgs[b];
        if (locale != null) {
          if (!hashSet.contains(locale)) {
            locale = (Locale)locale.clone();
            arrayOfLocale[b] = locale;
            toLanguageTag(stringBuilder, locale);
            if (b < paramVarArgs.length - 1)
              stringBuilder.append(','); 
            hashSet.add(locale);
            b++;
            continue;
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("list[");
          stringBuilder2.append(b);
          stringBuilder2.append("] is a repetition");
          throw new IllegalArgumentException(stringBuilder2.toString());
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("list[");
        stringBuilder1.append(b);
        stringBuilder1.append("] is null");
        throw new NullPointerException(stringBuilder1.toString());
      } 
      this.mList = arrayOfLocale;
      this.mStringRepresentation = stringBuilder.toString();
    } 
  }
  
  private Locale computeFirstMatch(Collection<String> paramCollection, boolean paramBoolean) {
    Locale locale;
    int i = computeFirstMatchIndex(paramCollection, paramBoolean);
    if (i == -1) {
      paramCollection = null;
    } else {
      locale = this.mList[i];
    } 
    return locale;
  }
  
  private int computeFirstMatchIndex(Collection<String> paramCollection, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mList : [Ljava/util/Locale;
    //   4: astore #5
    //   6: aload #5
    //   8: arraylength
    //   9: iconst_1
    //   10: if_icmpne -> 15
    //   13: iconst_0
    //   14: ireturn
    //   15: aload #5
    //   17: arraylength
    //   18: ifne -> 23
    //   21: iconst_m1
    //   22: ireturn
    //   23: iload_2
    //   24: ifeq -> 50
    //   27: aload_0
    //   28: getstatic androidx/core/os/LocaleListCompatWrapper.EN_LATN : Ljava/util/Locale;
    //   31: invokespecial findFirstMatchIndex : (Ljava/util/Locale;)I
    //   34: istore_3
    //   35: iload_3
    //   36: ifne -> 41
    //   39: iconst_0
    //   40: ireturn
    //   41: iload_3
    //   42: ldc 2147483647
    //   44: if_icmpge -> 50
    //   47: goto -> 53
    //   50: ldc 2147483647
    //   52: istore_3
    //   53: aload_1
    //   54: invokeinterface iterator : ()Ljava/util/Iterator;
    //   59: astore_1
    //   60: aload_1
    //   61: invokeinterface hasNext : ()Z
    //   66: ifeq -> 106
    //   69: aload_0
    //   70: aload_1
    //   71: invokeinterface next : ()Ljava/lang/Object;
    //   76: checkcast java/lang/String
    //   79: invokestatic forLanguageTagCompat : (Ljava/lang/String;)Ljava/util/Locale;
    //   82: invokespecial findFirstMatchIndex : (Ljava/util/Locale;)I
    //   85: istore #4
    //   87: iload #4
    //   89: ifne -> 94
    //   92: iconst_0
    //   93: ireturn
    //   94: iload #4
    //   96: iload_3
    //   97: if_icmpge -> 60
    //   100: iload #4
    //   102: istore_3
    //   103: goto -> 60
    //   106: iload_3
    //   107: ldc 2147483647
    //   109: if_icmpne -> 114
    //   112: iconst_0
    //   113: ireturn
    //   114: iload_3
    //   115: ireturn
  }
  
  private int findFirstMatchIndex(Locale paramLocale) {
    byte b = 0;
    while (true) {
      Locale[] arrayOfLocale = this.mList;
      if (b < arrayOfLocale.length) {
        if (matchScore(paramLocale, arrayOfLocale[b]) > 0)
          return b; 
        b++;
        continue;
      } 
      return Integer.MAX_VALUE;
    } 
  }
  
  private static String getLikelyScript(Locale paramLocale) {
    if (Build.VERSION.SDK_INT >= 21) {
      String str = paramLocale.getScript();
      if (!str.isEmpty())
        return str; 
    } 
    return "";
  }
  
  private static boolean isPseudoLocale(Locale paramLocale) {
    return (LOCALE_EN_XA.equals(paramLocale) || LOCALE_AR_XB.equals(paramLocale));
  }
  
  private static int matchScore(Locale paramLocale1, Locale paramLocale2) {
    boolean bool1 = paramLocale1.equals(paramLocale2);
    boolean bool = true;
    if (bool1)
      return 1; 
    if (!paramLocale1.getLanguage().equals(paramLocale2.getLanguage()))
      return 0; 
    if (isPseudoLocale(paramLocale1) || isPseudoLocale(paramLocale2))
      return 0; 
    String str = getLikelyScript(paramLocale1);
    if (str.isEmpty()) {
      String str1 = paramLocale1.getCountry();
      boolean bool2 = bool;
      if (!str1.isEmpty())
        if (str1.equals(paramLocale2.getCountry())) {
          bool2 = bool;
        } else {
          bool2 = false;
        }  
      return bool2;
    } 
    return str.equals(getLikelyScript(paramLocale2));
  }
  
  static void toLanguageTag(StringBuilder paramStringBuilder, Locale paramLocale) {
    paramStringBuilder.append(paramLocale.getLanguage());
    String str = paramLocale.getCountry();
    if (str != null && !str.isEmpty()) {
      paramStringBuilder.append('-');
      paramStringBuilder.append(paramLocale.getCountry());
    } 
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (!(paramObject instanceof LocaleListCompatWrapper))
      return false; 
    paramObject = ((LocaleListCompatWrapper)paramObject).mList;
    if (this.mList.length != paramObject.length)
      return false; 
    byte b = 0;
    while (true) {
      Locale[] arrayOfLocale = this.mList;
      if (b < arrayOfLocale.length) {
        if (!arrayOfLocale[b].equals(paramObject[b]))
          return false; 
        b++;
        continue;
      } 
      return true;
    } 
  }
  
  public Locale get(int paramInt) {
    if (paramInt >= 0) {
      Locale[] arrayOfLocale = this.mList;
      if (paramInt < arrayOfLocale.length)
        return arrayOfLocale[paramInt]; 
    } 
    return null;
  }
  
  public Locale getFirstMatch(String[] paramArrayOfString) {
    return computeFirstMatch(Arrays.asList(paramArrayOfString), false);
  }
  
  public Object getLocaleList() {
    return null;
  }
  
  public int hashCode() {
    int i = 1;
    byte b = 0;
    while (true) {
      Locale[] arrayOfLocale = this.mList;
      if (b < arrayOfLocale.length) {
        i = i * 31 + arrayOfLocale[b].hashCode();
        b++;
        continue;
      } 
      return i;
    } 
  }
  
  public int indexOf(Locale paramLocale) {
    byte b = 0;
    while (true) {
      Locale[] arrayOfLocale = this.mList;
      if (b < arrayOfLocale.length) {
        if (arrayOfLocale[b].equals(paramLocale))
          return b; 
        b++;
        continue;
      } 
      return -1;
    } 
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.mList.length == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int size() {
    return this.mList.length;
  }
  
  public String toLanguageTags() {
    return this.mStringRepresentation;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    byte b = 0;
    while (true) {
      Locale[] arrayOfLocale = this.mList;
      if (b < arrayOfLocale.length) {
        stringBuilder.append(arrayOfLocale[b]);
        if (b < this.mList.length - 1)
          stringBuilder.append(','); 
        b++;
        continue;
      } 
      stringBuilder.append("]");
      return stringBuilder.toString();
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\os\LocaleListCompatWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */