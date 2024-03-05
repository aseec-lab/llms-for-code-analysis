package androidx.core.app;

import android.app.ActivityManager;
import android.os.Build;

public final class ActivityManagerCompat {
  public static boolean isLowRamDevice(ActivityManager paramActivityManager) {
    return (Build.VERSION.SDK_INT >= 19) ? paramActivityManager.isLowRamDevice() : false;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\ActivityManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */