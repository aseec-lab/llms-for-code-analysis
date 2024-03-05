package androidx.appcompat.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import java.util.Calendar;

class TwilightManager {
  private static final int SUNRISE = 6;
  
  private static final int SUNSET = 22;
  
  private static final String TAG = "TwilightManager";
  
  private static TwilightManager sInstance;
  
  private final Context mContext;
  
  private final LocationManager mLocationManager;
  
  private final TwilightState mTwilightState = new TwilightState();
  
  TwilightManager(Context paramContext, LocationManager paramLocationManager) {
    this.mContext = paramContext;
    this.mLocationManager = paramLocationManager;
  }
  
  static TwilightManager getInstance(Context paramContext) {
    if (sInstance == null) {
      paramContext = paramContext.getApplicationContext();
      sInstance = new TwilightManager(paramContext, (LocationManager)paramContext.getSystemService("location"));
    } 
    return sInstance;
  }
  
  private Location getLastKnownLocation() {
    Location location1;
    int i = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION");
    Location location2 = null;
    if (i == 0) {
      location1 = getLastKnownLocationForProvider("network");
    } else {
      location1 = null;
    } 
    if (PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0)
      location2 = getLastKnownLocationForProvider("gps"); 
    if (location2 != null && location1 != null) {
      Location location = location1;
      if (location2.getTime() > location1.getTime())
        location = location2; 
      return location;
    } 
    if (location2 != null)
      location1 = location2; 
    return location1;
  }
  
  private Location getLastKnownLocationForProvider(String paramString) {
    try {
      if (this.mLocationManager.isProviderEnabled(paramString))
        return this.mLocationManager.getLastKnownLocation(paramString); 
    } catch (Exception exception) {
      Log.d("TwilightManager", "Failed to get last known location", exception);
    } 
    return null;
  }
  
  private boolean isStateValid() {
    boolean bool;
    if (this.mTwilightState.nextUpdate > System.currentTimeMillis()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static void setInstance(TwilightManager paramTwilightManager) {
    sInstance = paramTwilightManager;
  }
  
  private void updateState(Location paramLocation) {
    boolean bool;
    TwilightState twilightState = this.mTwilightState;
    long l1 = System.currentTimeMillis();
    TwilightCalculator twilightCalculator = TwilightCalculator.getInstance();
    twilightCalculator.calculateTwilight(l1 - 86400000L, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l5 = twilightCalculator.sunset;
    twilightCalculator.calculateTwilight(l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    if (twilightCalculator.state == 1) {
      bool = true;
    } else {
      bool = false;
    } 
    long l3 = twilightCalculator.sunrise;
    long l2 = twilightCalculator.sunset;
    twilightCalculator.calculateTwilight(86400000L + l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l4 = twilightCalculator.sunrise;
    if (l3 == -1L || l2 == -1L) {
      l1 = 43200000L + l1;
    } else {
      if (l1 > l2) {
        l1 = 0L + l4;
      } else if (l1 > l3) {
        l1 = 0L + l2;
      } else {
        l1 = 0L + l3;
      } 
      l1 += 60000L;
    } 
    twilightState.isNight = bool;
    twilightState.yesterdaySunset = l5;
    twilightState.todaySunrise = l3;
    twilightState.todaySunset = l2;
    twilightState.tomorrowSunrise = l4;
    twilightState.nextUpdate = l1;
  }
  
  boolean isNight() {
    TwilightState twilightState = this.mTwilightState;
    if (isStateValid())
      return twilightState.isNight; 
    Location location = getLastKnownLocation();
    if (location != null) {
      updateState(location);
      return twilightState.isNight;
    } 
    Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
    int i = Calendar.getInstance().get(11);
    return (i < 6 || i >= 22);
  }
  
  private static class TwilightState {
    boolean isNight;
    
    long nextUpdate;
    
    long todaySunrise;
    
    long todaySunset;
    
    long tomorrowSunrise;
    
    long yesterdaySunset;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\app\TwilightManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */