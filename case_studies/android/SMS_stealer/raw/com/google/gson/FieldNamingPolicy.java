package com.google.gson;

import java.lang.reflect.Field;
import java.util.Locale;

public enum FieldNamingPolicy implements FieldNamingStrategy {
  IDENTITY {
    public String translateName(Field param1Field) {
      return param1Field.getName();
    }
  },
  LOWER_CASE_WITH_DASHES,
  LOWER_CASE_WITH_UNDERSCORES,
  UPPER_CAMEL_CASE {
    public String translateName(Field param1Field) {
      return null.upperCaseFirstLetter(param1Field.getName());
    }
  },
  UPPER_CAMEL_CASE_WITH_SPACES {
    public String translateName(Field param1Field) {
      return null.upperCaseFirstLetter(null.separateCamelCase(param1Field.getName(), " "));
    }
  };
  
  private static final FieldNamingPolicy[] $VALUES;
  
  static {
    LOWER_CASE_WITH_UNDERSCORES = new null("LOWER_CASE_WITH_UNDERSCORES", 3);
    null  = new null("LOWER_CASE_WITH_DASHES", 4);
    LOWER_CASE_WITH_DASHES = ;
    $VALUES = new FieldNamingPolicy[] { IDENTITY, UPPER_CAMEL_CASE, UPPER_CAMEL_CASE_WITH_SPACES, LOWER_CASE_WITH_UNDERSCORES,  };
  }
  
  private static String modifyString(char paramChar, String paramString, int paramInt) {
    if (paramInt < paramString.length()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramChar);
      stringBuilder.append(paramString.substring(paramInt));
      paramString = stringBuilder.toString();
    } else {
      paramString = String.valueOf(paramChar);
    } 
    return paramString;
  }
  
  static String separateCamelCase(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    int i = paramString1.length();
    for (byte b = 0; b < i; b++) {
      char c = paramString1.charAt(b);
      if (Character.isUpperCase(c) && stringBuilder.length() != 0)
        stringBuilder.append(paramString2); 
      stringBuilder.append(c);
    } 
    return stringBuilder.toString();
  }
  
  static String upperCaseFirstLetter(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    byte b = 0;
    char c = paramString.charAt(0);
    int i = paramString.length();
    while (b < i - 1 && !Character.isLetter(c)) {
      stringBuilder.append(c);
      c = paramString.charAt(++b);
    } 
    String str = paramString;
    if (!Character.isUpperCase(c)) {
      stringBuilder.append(modifyString(Character.toUpperCase(c), paramString, b + 1));
      str = stringBuilder.toString();
    } 
    return str;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\FieldNamingPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */