package androidx.core.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import java.lang.reflect.Method;

public final class ViewConfigurationCompat {
  private static final String TAG = "ViewConfigCompat";
  
  private static Method sGetScaledScrollFactorMethod;
  
  static {
    if (Build.VERSION.SDK_INT == 25)
      try {
        sGetScaledScrollFactorMethod = ViewConfiguration.class.getDeclaredMethod("getScaledScrollFactor", new Class[0]);
      } catch (Exception exception) {
        Log.i("ViewConfigCompat", "Could not find method getScaledScrollFactor() on ViewConfiguration");
      }  
  }
  
  private static float getLegacyScrollFactor(ViewConfiguration paramViewConfiguration, Context paramContext) {
    if (Build.VERSION.SDK_INT >= 25) {
      Method method = sGetScaledScrollFactorMethod;
      if (method != null)
        try {
          int i = ((Integer)method.invoke(paramViewConfiguration, new Object[0])).intValue();
          return i;
        } catch (Exception exception) {
          Log.i("ViewConfigCompat", "Could not find method getScaledScrollFactor() on ViewConfiguration");
        }  
    } 
    TypedValue typedValue = new TypedValue();
    return paramContext.getTheme().resolveAttribute(16842829, typedValue, true) ? typedValue.getDimension(paramContext.getResources().getDisplayMetrics()) : 0.0F;
  }
  
  public static float getScaledHorizontalScrollFactor(ViewConfiguration paramViewConfiguration, Context paramContext) {
    return (Build.VERSION.SDK_INT >= 26) ? paramViewConfiguration.getScaledHorizontalScrollFactor() : getLegacyScrollFactor(paramViewConfiguration, paramContext);
  }
  
  public static int getScaledHoverSlop(ViewConfiguration paramViewConfiguration) {
    return (Build.VERSION.SDK_INT >= 28) ? paramViewConfiguration.getScaledHoverSlop() : (paramViewConfiguration.getScaledTouchSlop() / 2);
  }
  
  @Deprecated
  public static int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration) {
    return paramViewConfiguration.getScaledPagingTouchSlop();
  }
  
  public static float getScaledVerticalScrollFactor(ViewConfiguration paramViewConfiguration, Context paramContext) {
    return (Build.VERSION.SDK_INT >= 26) ? paramViewConfiguration.getScaledVerticalScrollFactor() : getLegacyScrollFactor(paramViewConfiguration, paramContext);
  }
  
  @Deprecated
  public static boolean hasPermanentMenuKey(ViewConfiguration paramViewConfiguration) {
    return paramViewConfiguration.hasPermanentMenuKey();
  }
  
  public static boolean shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration paramViewConfiguration, Context paramContext) {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 28)
      return paramViewConfiguration.shouldShowMenuShortcutsWhenKeyboardPresent(); 
    Resources resources = paramContext.getResources();
    int i = resources.getIdentifier("config_showMenuShortcutsWhenKeyboardPresent", "bool", "android");
    if (i != 0 && resources.getBoolean(i)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\ViewConfigurationCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */