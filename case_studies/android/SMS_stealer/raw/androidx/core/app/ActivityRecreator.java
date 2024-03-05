package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

final class ActivityRecreator {
  private static final String LOG_TAG = "ActivityRecreator";
  
  protected static final Class<?> activityThreadClass;
  
  private static final Handler mainHandler = new Handler(Looper.getMainLooper());
  
  protected static final Field mainThreadField;
  
  protected static final Method performStopActivity2ParamsMethod;
  
  protected static final Method performStopActivity3ParamsMethod;
  
  protected static final Method requestRelaunchActivityMethod;
  
  protected static final Field tokenField;
  
  static {
    activityThreadClass = getActivityThreadClass();
    mainThreadField = getMainThreadField();
    tokenField = getTokenField();
    performStopActivity3ParamsMethod = getPerformStopActivity3Params(activityThreadClass);
    performStopActivity2ParamsMethod = getPerformStopActivity2Params(activityThreadClass);
    requestRelaunchActivityMethod = getRequestRelaunchActivityMethod(activityThreadClass);
  }
  
  private static Class<?> getActivityThreadClass() {
    try {
      return Class.forName("android.app.ActivityThread");
    } finally {
      Exception exception = null;
    } 
  }
  
  private static Field getMainThreadField() {
    try {
      Field field = Activity.class.getDeclaredField("mMainThread");
      return field;
    } finally {
      Exception exception = null;
    } 
  }
  
  private static Method getPerformStopActivity2Params(Class<?> paramClass) {
    if (paramClass == null)
      return null; 
    try {
      Method method = paramClass.getDeclaredMethod("performStopActivity", new Class[] { IBinder.class, boolean.class });
      return method;
    } finally {
      paramClass = null;
    } 
  }
  
  private static Method getPerformStopActivity3Params(Class<?> paramClass) {
    if (paramClass == null)
      return null; 
    try {
      Method method = paramClass.getDeclaredMethod("performStopActivity", new Class[] { IBinder.class, boolean.class, String.class });
      return method;
    } finally {
      paramClass = null;
    } 
  }
  
  private static Method getRequestRelaunchActivityMethod(Class<?> paramClass) {
    if (needsRelaunchCall() && paramClass != null)
      try {
        Method method = paramClass.getDeclaredMethod("requestRelaunchActivity", new Class[] { IBinder.class, List.class, List.class, int.class, boolean.class, Configuration.class, Configuration.class, boolean.class, boolean.class });
        method.setAccessible(true);
        return method;
      } finally {} 
    return null;
  }
  
  private static Field getTokenField() {
    try {
      Field field = Activity.class.getDeclaredField("mToken");
      return field;
    } finally {
      Exception exception = null;
    } 
  }
  
  private static boolean needsRelaunchCall() {
    return (Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27);
  }
  
  protected static boolean queueOnStopIfNecessary(Object paramObject, Activity paramActivity) {
    try {
      Object object = tokenField.get(paramActivity);
      if (object != paramObject)
        return false; 
      paramObject = mainThreadField.get(paramActivity);
      Handler handler = mainHandler;
      Runnable runnable = new Runnable() {
          final Object val$activityThread;
          
          final Object val$token;
          
          public void run() {
            try {
            
            } catch (RuntimeException runtimeException) {
            
            } finally {
              Exception exception = null;
            } 
          }
        };
      return true;
    } finally {
      paramObject = null;
      Log.e("ActivityRecreator", "Exception while fetching field values", (Throwable)paramObject);
    } 
  }
  
  static boolean recreate(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 28) {
      paramActivity.recreate();
      return true;
    } 
    if (needsRelaunchCall() && requestRelaunchActivityMethod == null)
      return false; 
    if (performStopActivity2ParamsMethod == null && performStopActivity3ParamsMethod == null)
      return false; 
    try {
      Object object1 = tokenField.get(paramActivity);
      if (object1 == null)
        return false; 
      Object object2 = mainThreadField.get(paramActivity);
      if (object2 == null)
        return false; 
      Application application = paramActivity.getApplication();
      LifecycleCheckCallbacks lifecycleCheckCallbacks = new LifecycleCheckCallbacks();
      this(paramActivity);
      application.registerActivityLifecycleCallbacks(lifecycleCheckCallbacks);
      Handler handler = mainHandler;
      Runnable runnable = new Runnable() {
          final ActivityRecreator.LifecycleCheckCallbacks val$callbacks;
          
          final Object val$token;
          
          public void run() {
            callbacks.currentlyRecreatingToken = token;
          }
        };
      super(lifecycleCheckCallbacks, object1);
      handler.post(runnable);
    } finally {
      paramActivity = null;
    } 
  }
  
  private static final class LifecycleCheckCallbacks implements Application.ActivityLifecycleCallbacks {
    Object currentlyRecreatingToken;
    
    private Activity mActivity;
    
    private boolean mDestroyed = false;
    
    private boolean mStarted = false;
    
    private boolean mStopQueued = false;
    
    LifecycleCheckCallbacks(Activity param1Activity) {
      this.mActivity = param1Activity;
    }
    
    public void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {}
    
    public void onActivityDestroyed(Activity param1Activity) {
      if (this.mActivity == param1Activity) {
        this.mActivity = null;
        this.mDestroyed = true;
      } 
    }
    
    public void onActivityPaused(Activity param1Activity) {
      if (this.mDestroyed && !this.mStopQueued && !this.mStarted && ActivityRecreator.queueOnStopIfNecessary(this.currentlyRecreatingToken, param1Activity)) {
        this.mStopQueued = true;
        this.currentlyRecreatingToken = null;
      } 
    }
    
    public void onActivityResumed(Activity param1Activity) {}
    
    public void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {}
    
    public void onActivityStarted(Activity param1Activity) {
      if (this.mActivity == param1Activity)
        this.mStarted = true; 
    }
    
    public void onActivityStopped(Activity param1Activity) {}
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\ActivityRecreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */