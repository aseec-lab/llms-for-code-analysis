package androidx.core.database;

import android.text.TextUtils;

@Deprecated
public final class DatabaseUtilsCompat {
  @Deprecated
  public static String[] appendSelectionArgs(String[] paramArrayOfString1, String[] paramArrayOfString2) {
    if (paramArrayOfString1 == null || paramArrayOfString1.length == 0)
      return paramArrayOfString2; 
    String[] arrayOfString = new String[paramArrayOfString1.length + paramArrayOfString2.length];
    System.arraycopy(paramArrayOfString1, 0, arrayOfString, 0, paramArrayOfString1.length);
    System.arraycopy(paramArrayOfString2, 0, arrayOfString, paramArrayOfString1.length, paramArrayOfString2.length);
    return arrayOfString;
  }
  
  @Deprecated
  public static String concatenateWhere(String paramString1, String paramString2) {
    if (TextUtils.isEmpty(paramString1))
      return paramString2; 
    if (TextUtils.isEmpty(paramString2))
      return paramString1; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    stringBuilder.append(paramString1);
    stringBuilder.append(") AND (");
    stringBuilder.append(paramString2);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\database\DatabaseUtilsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */