package androidx.core.content.res;

import android.content.res.Resources;
import android.os.Build;

public final class ConfigurationHelper {
  public static int getDensityDpi(Resources paramResources) {
    return (Build.VERSION.SDK_INT >= 17) ? (paramResources.getConfiguration()).densityDpi : (paramResources.getDisplayMetrics()).densityDpi;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\res\ConfigurationHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */