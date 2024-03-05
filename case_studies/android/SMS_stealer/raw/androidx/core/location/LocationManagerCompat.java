package androidx.core.location;

import android.location.LocationManager;
import android.os.Build;

public final class LocationManagerCompat {
  public static boolean isLocationEnabled(LocationManager paramLocationManager) {
    return (Build.VERSION.SDK_INT >= 28) ? paramLocationManager.isLocationEnabled() : ((paramLocationManager.isProviderEnabled("network") || paramLocationManager.isProviderEnabled("gps")));
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\location\LocationManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */