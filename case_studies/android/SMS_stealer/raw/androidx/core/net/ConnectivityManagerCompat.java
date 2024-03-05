package androidx.core.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ConnectivityManagerCompat {
  public static final int RESTRICT_BACKGROUND_STATUS_DISABLED = 1;
  
  public static final int RESTRICT_BACKGROUND_STATUS_ENABLED = 3;
  
  public static final int RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2;
  
  public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager paramConnectivityManager, Intent paramIntent) {
    NetworkInfo networkInfo = (NetworkInfo)paramIntent.getParcelableExtra("networkInfo");
    return (networkInfo != null) ? paramConnectivityManager.getNetworkInfo(networkInfo.getType()) : null;
  }
  
  public static int getRestrictBackgroundStatus(ConnectivityManager paramConnectivityManager) {
    return (Build.VERSION.SDK_INT >= 24) ? paramConnectivityManager.getRestrictBackgroundStatus() : 3;
  }
  
  public static boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager) {
    if (Build.VERSION.SDK_INT >= 16)
      return paramConnectivityManager.isActiveNetworkMetered(); 
    NetworkInfo networkInfo = paramConnectivityManager.getActiveNetworkInfo();
    if (networkInfo == null)
      return true; 
    int i = networkInfo.getType();
    return (i != 1 && i != 7 && i != 9);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface RestrictBackgroundStatus {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\net\ConnectivityManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */