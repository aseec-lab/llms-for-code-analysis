package androidx.core.content;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public class ContextCompat {
  private static final String TAG = "ContextCompat";
  
  private static final Object sLock = new Object();
  
  private static TypedValue sTempValue;
  
  public static int checkSelfPermission(Context paramContext, String paramString) {
    if (paramString != null)
      return paramContext.checkPermission(paramString, Process.myPid(), Process.myUid()); 
    throw new IllegalArgumentException("permission is null");
  }
  
  public static Context createDeviceProtectedStorageContext(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 24) ? paramContext.createDeviceProtectedStorageContext() : null;
  }
  
  private static File createFilesDir(File paramFile) {
    // Byte code:
    //   0: ldc androidx/core/content/ContextCompat
    //   2: monitorenter
    //   3: aload_0
    //   4: invokevirtual exists : ()Z
    //   7: ifne -> 70
    //   10: aload_0
    //   11: invokevirtual mkdirs : ()Z
    //   14: ifne -> 70
    //   17: aload_0
    //   18: invokevirtual exists : ()Z
    //   21: istore_1
    //   22: iload_1
    //   23: ifeq -> 31
    //   26: ldc androidx/core/content/ContextCompat
    //   28: monitorexit
    //   29: aload_0
    //   30: areturn
    //   31: new java/lang/StringBuilder
    //   34: astore_2
    //   35: aload_2
    //   36: invokespecial <init> : ()V
    //   39: aload_2
    //   40: ldc 'Unable to create files subdir '
    //   42: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_2
    //   47: aload_0
    //   48: invokevirtual getPath : ()Ljava/lang/String;
    //   51: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: ldc 'ContextCompat'
    //   57: aload_2
    //   58: invokevirtual toString : ()Ljava/lang/String;
    //   61: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   64: pop
    //   65: ldc androidx/core/content/ContextCompat
    //   67: monitorexit
    //   68: aconst_null
    //   69: areturn
    //   70: ldc androidx/core/content/ContextCompat
    //   72: monitorexit
    //   73: aload_0
    //   74: areturn
    //   75: astore_0
    //   76: ldc androidx/core/content/ContextCompat
    //   78: monitorexit
    //   79: aload_0
    //   80: athrow
    // Exception table:
    //   from	to	target	type
    //   3	22	75	finally
    //   31	65	75	finally
  }
  
  public static File getCodeCacheDir(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 21) ? paramContext.getCodeCacheDir() : createFilesDir(new File((paramContext.getApplicationInfo()).dataDir, "code_cache"));
  }
  
  public static int getColor(Context paramContext, int paramInt) {
    return (Build.VERSION.SDK_INT >= 23) ? paramContext.getColor(paramInt) : paramContext.getResources().getColor(paramInt);
  }
  
  public static ColorStateList getColorStateList(Context paramContext, int paramInt) {
    return (Build.VERSION.SDK_INT >= 23) ? paramContext.getColorStateList(paramInt) : paramContext.getResources().getColorStateList(paramInt);
  }
  
  public static File getDataDir(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 24)
      return paramContext.getDataDir(); 
    String str = (paramContext.getApplicationInfo()).dataDir;
    if (str != null) {
      File file = new File(str);
    } else {
      str = null;
    } 
    return (File)str;
  }
  
  public static Drawable getDrawable(Context paramContext, int paramInt) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramContext.getDrawable(paramInt); 
    if (Build.VERSION.SDK_INT >= 16)
      return paramContext.getResources().getDrawable(paramInt); 
    synchronized (sLock) {
      if (sTempValue == null) {
        TypedValue typedValue = new TypedValue();
        this();
        sTempValue = typedValue;
      } 
      paramContext.getResources().getValue(paramInt, sTempValue, true);
      paramInt = sTempValue.resourceId;
      return paramContext.getResources().getDrawable(paramInt);
    } 
  }
  
  public static File[] getExternalCacheDirs(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getExternalCacheDirs() : new File[] { paramContext.getExternalCacheDir() };
  }
  
  public static File[] getExternalFilesDirs(Context paramContext, String paramString) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getExternalFilesDirs(paramString) : new File[] { paramContext.getExternalFilesDir(paramString) };
  }
  
  public static Executor getMainExecutor(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 28) ? paramContext.getMainExecutor() : new MainHandlerExecutor(new Handler(paramContext.getMainLooper()));
  }
  
  public static File getNoBackupFilesDir(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 21) ? paramContext.getNoBackupFilesDir() : createFilesDir(new File((paramContext.getApplicationInfo()).dataDir, "no_backup"));
  }
  
  public static File[] getObbDirs(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getObbDirs() : new File[] { paramContext.getObbDir() };
  }
  
  public static <T> T getSystemService(Context paramContext, Class<T> paramClass) {
    if (Build.VERSION.SDK_INT >= 23)
      return (T)paramContext.getSystemService(paramClass); 
    String str = getSystemServiceName(paramContext, paramClass);
    if (str != null) {
      Object object = paramContext.getSystemService(str);
    } else {
      paramContext = null;
    } 
    return (T)paramContext;
  }
  
  public static String getSystemServiceName(Context paramContext, Class<?> paramClass) {
    return (Build.VERSION.SDK_INT >= 23) ? paramContext.getSystemServiceName(paramClass) : LegacyServiceMapHolder.SERVICES.get(paramClass);
  }
  
  public static boolean isDeviceProtectedStorage(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 24) ? paramContext.isDeviceProtectedStorage() : false;
  }
  
  public static boolean startActivities(Context paramContext, Intent[] paramArrayOfIntent) {
    return startActivities(paramContext, paramArrayOfIntent, null);
  }
  
  public static boolean startActivities(Context paramContext, Intent[] paramArrayOfIntent, Bundle paramBundle) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramContext.startActivities(paramArrayOfIntent, paramBundle);
    } else {
      paramContext.startActivities(paramArrayOfIntent);
    } 
    return true;
  }
  
  public static void startActivity(Context paramContext, Intent paramIntent, Bundle paramBundle) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramContext.startActivity(paramIntent, paramBundle);
    } else {
      paramContext.startActivity(paramIntent);
    } 
  }
  
  public static void startForegroundService(Context paramContext, Intent paramIntent) {
    if (Build.VERSION.SDK_INT >= 26) {
      paramContext.startForegroundService(paramIntent);
    } else {
      paramContext.startService(paramIntent);
    } 
  }
  
  private static final class LegacyServiceMapHolder {
    static final HashMap<Class<?>, String> SERVICES = new HashMap<Class<?>, String>();
    
    static {
      if (Build.VERSION.SDK_INT >= 22) {
        SERVICES.put(SubscriptionManager.class, "telephony_subscription_service");
        SERVICES.put(UsageStatsManager.class, "usagestats");
      } 
      if (Build.VERSION.SDK_INT >= 21) {
        SERVICES.put(AppWidgetManager.class, "appwidget");
        SERVICES.put(BatteryManager.class, "batterymanager");
        SERVICES.put(CameraManager.class, "camera");
        SERVICES.put(JobScheduler.class, "jobscheduler");
        SERVICES.put(LauncherApps.class, "launcherapps");
        SERVICES.put(MediaProjectionManager.class, "media_projection");
        SERVICES.put(MediaSessionManager.class, "media_session");
        SERVICES.put(RestrictionsManager.class, "restrictions");
        SERVICES.put(TelecomManager.class, "telecom");
        SERVICES.put(TvInputManager.class, "tv_input");
      } 
      if (Build.VERSION.SDK_INT >= 19) {
        SERVICES.put(AppOpsManager.class, "appops");
        SERVICES.put(CaptioningManager.class, "captioning");
        SERVICES.put(ConsumerIrManager.class, "consumer_ir");
        SERVICES.put(PrintManager.class, "print");
      } 
      if (Build.VERSION.SDK_INT >= 18)
        SERVICES.put(BluetoothManager.class, "bluetooth"); 
      if (Build.VERSION.SDK_INT >= 17) {
        SERVICES.put(DisplayManager.class, "display");
        SERVICES.put(UserManager.class, "user");
      } 
      if (Build.VERSION.SDK_INT >= 16) {
        SERVICES.put(InputManager.class, "input");
        SERVICES.put(MediaRouter.class, "media_router");
        SERVICES.put(NsdManager.class, "servicediscovery");
      } 
      SERVICES.put(AccessibilityManager.class, "accessibility");
      SERVICES.put(AccountManager.class, "account");
      SERVICES.put(ActivityManager.class, "activity");
      SERVICES.put(AlarmManager.class, "alarm");
      SERVICES.put(AudioManager.class, "audio");
      SERVICES.put(ClipboardManager.class, "clipboard");
      SERVICES.put(ConnectivityManager.class, "connectivity");
      SERVICES.put(DevicePolicyManager.class, "device_policy");
      SERVICES.put(DownloadManager.class, "download");
      SERVICES.put(DropBoxManager.class, "dropbox");
      SERVICES.put(InputMethodManager.class, "input_method");
      SERVICES.put(KeyguardManager.class, "keyguard");
      SERVICES.put(LayoutInflater.class, "layout_inflater");
      SERVICES.put(LocationManager.class, "location");
      SERVICES.put(NfcManager.class, "nfc");
      SERVICES.put(NotificationManager.class, "notification");
      SERVICES.put(PowerManager.class, "power");
      SERVICES.put(SearchManager.class, "search");
      SERVICES.put(SensorManager.class, "sensor");
      SERVICES.put(StorageManager.class, "storage");
      SERVICES.put(TelephonyManager.class, "phone");
      SERVICES.put(TextServicesManager.class, "textservices");
      SERVICES.put(UiModeManager.class, "uimode");
      SERVICES.put(UsbManager.class, "usb");
      SERVICES.put(Vibrator.class, "vibrator");
      SERVICES.put(WallpaperManager.class, "wallpaper");
      SERVICES.put(WifiP2pManager.class, "wifip2p");
      SERVICES.put(WifiManager.class, "wifi");
      SERVICES.put(WindowManager.class, "window");
    }
  }
  
  private static class MainHandlerExecutor implements Executor {
    private final Handler mHandler;
    
    MainHandlerExecutor(Handler param1Handler) {
      this.mHandler = param1Handler;
    }
    
    public void execute(Runnable param1Runnable) {
      if (this.mHandler.post(param1Runnable))
        return; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mHandler);
      stringBuilder.append(" is shutting down");
      throw new RejectedExecutionException(stringBuilder.toString());
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\ContextCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */