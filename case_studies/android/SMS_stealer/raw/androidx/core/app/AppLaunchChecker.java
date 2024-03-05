package androidx.core.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AppLaunchChecker {
  private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
  
  private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";
  
  public static boolean hasStartedFromLauncher(Context paramContext) {
    return paramContext.getSharedPreferences("android.support.AppLaunchChecker", 0).getBoolean("startedFromLauncher", false);
  }
  
  public static void onActivityCreate(Activity paramActivity) {
    SharedPreferences sharedPreferences = paramActivity.getSharedPreferences("android.support.AppLaunchChecker", 0);
    if (sharedPreferences.getBoolean("startedFromLauncher", false))
      return; 
    Intent intent = paramActivity.getIntent();
    if (intent == null)
      return; 
    if ("android.intent.action.MAIN".equals(intent.getAction()) && (intent.hasCategory("android.intent.category.LAUNCHER") || intent.hasCategory("android.intent.category.LEANBACK_LAUNCHER")))
      sharedPreferences.edit().putBoolean("startedFromLauncher", true).apply(); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\AppLaunchChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */