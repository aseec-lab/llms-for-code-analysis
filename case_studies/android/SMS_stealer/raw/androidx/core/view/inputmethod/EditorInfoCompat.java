package androidx.core.view.inputmethod;

import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

public final class EditorInfoCompat {
  private static final String CONTENT_MIME_TYPES_INTEROP_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
  
  private static final String CONTENT_MIME_TYPES_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
  
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  
  public static final int IME_FLAG_FORCE_ASCII = -2147483648;
  
  public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
  
  public static String[] getContentMimeTypes(EditorInfo paramEditorInfo) {
    String[] arrayOfString1;
    if (Build.VERSION.SDK_INT >= 25) {
      arrayOfString1 = paramEditorInfo.contentMimeTypes;
      if (arrayOfString1 == null)
        arrayOfString1 = EMPTY_STRING_ARRAY; 
      return arrayOfString1;
    } 
    if (((EditorInfo)arrayOfString1).extras == null)
      return EMPTY_STRING_ARRAY; 
    String[] arrayOfString3 = ((EditorInfo)arrayOfString1).extras.getStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    String[] arrayOfString2 = arrayOfString3;
    if (arrayOfString3 == null)
      arrayOfString2 = ((EditorInfo)arrayOfString1).extras.getStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES"); 
    if (arrayOfString2 == null)
      arrayOfString2 = EMPTY_STRING_ARRAY; 
    return arrayOfString2;
  }
  
  static int getProtocol(EditorInfo paramEditorInfo) {
    if (Build.VERSION.SDK_INT >= 25)
      return 1; 
    if (paramEditorInfo.extras == null)
      return 0; 
    boolean bool2 = paramEditorInfo.extras.containsKey("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    boolean bool1 = paramEditorInfo.extras.containsKey("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    return (bool2 && bool1) ? 4 : (bool2 ? 3 : (bool1 ? 2 : 0));
  }
  
  public static void setContentMimeTypes(EditorInfo paramEditorInfo, String[] paramArrayOfString) {
    if (Build.VERSION.SDK_INT >= 25) {
      paramEditorInfo.contentMimeTypes = paramArrayOfString;
    } else {
      if (paramEditorInfo.extras == null)
        paramEditorInfo.extras = new Bundle(); 
      paramEditorInfo.extras.putStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", paramArrayOfString);
      paramEditorInfo.extras.putStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", paramArrayOfString);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\inputmethod\EditorInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */