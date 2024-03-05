package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

public final class CompoundButtonCompat {
  private static final String TAG = "CompoundButtonCompat";
  
  private static Field sButtonDrawableField;
  
  private static boolean sButtonDrawableFieldFetched;
  
  public static Drawable getButtonDrawable(CompoundButton paramCompoundButton) {
    if (Build.VERSION.SDK_INT >= 23)
      return paramCompoundButton.getButtonDrawable(); 
    if (!sButtonDrawableFieldFetched) {
      try {
        Field field1 = CompoundButton.class.getDeclaredField("mButtonDrawable");
        sButtonDrawableField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.i("CompoundButtonCompat", "Failed to retrieve mButtonDrawable field", noSuchFieldException);
      } 
      sButtonDrawableFieldFetched = true;
    } 
    Field field = sButtonDrawableField;
    if (field != null)
      try {
        return (Drawable)field.get(paramCompoundButton);
      } catch (IllegalAccessException illegalAccessException) {
        Log.i("CompoundButtonCompat", "Failed to get button drawable via reflection", illegalAccessException);
        sButtonDrawableField = null;
      }  
    return null;
  }
  
  public static ColorStateList getButtonTintList(CompoundButton paramCompoundButton) {
    return (Build.VERSION.SDK_INT >= 21) ? paramCompoundButton.getButtonTintList() : ((paramCompoundButton instanceof TintableCompoundButton) ? ((TintableCompoundButton)paramCompoundButton).getSupportButtonTintList() : null);
  }
  
  public static PorterDuff.Mode getButtonTintMode(CompoundButton paramCompoundButton) {
    return (Build.VERSION.SDK_INT >= 21) ? paramCompoundButton.getButtonTintMode() : ((paramCompoundButton instanceof TintableCompoundButton) ? ((TintableCompoundButton)paramCompoundButton).getSupportButtonTintMode() : null);
  }
  
  public static void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramCompoundButton.setButtonTintList(paramColorStateList);
    } else if (paramCompoundButton instanceof TintableCompoundButton) {
      ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintList(paramColorStateList);
    } 
  }
  
  public static void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramCompoundButton.setButtonTintMode(paramMode);
    } else if (paramCompoundButton instanceof TintableCompoundButton) {
      ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintMode(paramMode);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\widget\CompoundButtonCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */