package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.collection.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Iterator;

public abstract class AppCompatDelegate {
  static final boolean DEBUG = false;
  
  public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
  
  public static final int FEATURE_SUPPORT_ACTION_BAR = 108;
  
  public static final int FEATURE_SUPPORT_ACTION_BAR_OVERLAY = 109;
  
  @Deprecated
  public static final int MODE_NIGHT_AUTO = 0;
  
  public static final int MODE_NIGHT_AUTO_BATTERY = 3;
  
  @Deprecated
  public static final int MODE_NIGHT_AUTO_TIME = 0;
  
  public static final int MODE_NIGHT_FOLLOW_SYSTEM = -1;
  
  public static final int MODE_NIGHT_NO = 1;
  
  public static final int MODE_NIGHT_UNSPECIFIED = -100;
  
  public static final int MODE_NIGHT_YES = 2;
  
  static final String TAG = "AppCompatDelegate";
  
  private static final ArraySet<WeakReference<AppCompatDelegate>> sActivityDelegates = new ArraySet();
  
  private static final Object sActivityDelegatesLock = new Object();
  
  private static int sDefaultNightMode = -100;
  
  static void addActiveDelegate(AppCompatDelegate paramAppCompatDelegate) {
    synchronized (sActivityDelegatesLock) {
      removeDelegateFromActives(paramAppCompatDelegate);
      ArraySet<WeakReference<AppCompatDelegate>> arraySet = sActivityDelegates;
      WeakReference weakReference = new WeakReference();
      this((T)paramAppCompatDelegate);
      arraySet.add(weakReference);
      return;
    } 
  }
  
  private static void applyDayNightToActiveDelegates() {
    synchronized (sActivityDelegatesLock) {
      Iterator<WeakReference<AppCompatDelegate>> iterator = sActivityDelegates.iterator();
      while (iterator.hasNext()) {
        AppCompatDelegate appCompatDelegate = ((WeakReference<AppCompatDelegate>)iterator.next()).get();
        if (appCompatDelegate != null)
          appCompatDelegate.applyDayNight(); 
      } 
      return;
    } 
  }
  
  public static AppCompatDelegate create(Activity paramActivity, AppCompatCallback paramAppCompatCallback) {
    return new AppCompatDelegateImpl(paramActivity, paramAppCompatCallback);
  }
  
  public static AppCompatDelegate create(Dialog paramDialog, AppCompatCallback paramAppCompatCallback) {
    return new AppCompatDelegateImpl(paramDialog, paramAppCompatCallback);
  }
  
  public static AppCompatDelegate create(Context paramContext, Activity paramActivity, AppCompatCallback paramAppCompatCallback) {
    return new AppCompatDelegateImpl(paramContext, paramActivity, paramAppCompatCallback);
  }
  
  public static AppCompatDelegate create(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback) {
    return new AppCompatDelegateImpl(paramContext, paramWindow, paramAppCompatCallback);
  }
  
  public static int getDefaultNightMode() {
    return sDefaultNightMode;
  }
  
  public static boolean isCompatVectorFromResourcesEnabled() {
    return VectorEnabledTintResources.isCompatVectorFromResourcesEnabled();
  }
  
  static void removeActivityDelegate(AppCompatDelegate paramAppCompatDelegate) {
    synchronized (sActivityDelegatesLock) {
      removeDelegateFromActives(paramAppCompatDelegate);
      return;
    } 
  }
  
  private static void removeDelegateFromActives(AppCompatDelegate paramAppCompatDelegate) {
    synchronized (sActivityDelegatesLock) {
      Iterator<WeakReference<AppCompatDelegate>> iterator = sActivityDelegates.iterator();
      while (iterator.hasNext()) {
        AppCompatDelegate appCompatDelegate = ((WeakReference<AppCompatDelegate>)iterator.next()).get();
        if (appCompatDelegate == paramAppCompatDelegate || appCompatDelegate == null)
          iterator.remove(); 
      } 
      return;
    } 
  }
  
  public static void setCompatVectorFromResourcesEnabled(boolean paramBoolean) {
    VectorEnabledTintResources.setCompatVectorFromResourcesEnabled(paramBoolean);
  }
  
  public static void setDefaultNightMode(int paramInt) {
    if (paramInt != -1 && paramInt != 0 && paramInt != 1 && paramInt != 2 && paramInt != 3) {
      Log.d("AppCompatDelegate", "setDefaultNightMode() called with an unknown mode");
    } else if (sDefaultNightMode != paramInt) {
      sDefaultNightMode = paramInt;
      applyDayNightToActiveDelegates();
    } 
  }
  
  public abstract void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams);
  
  public abstract boolean applyDayNight();
  
  @Deprecated
  public void attachBaseContext(Context paramContext) {}
  
  public Context attachBaseContext2(Context paramContext) {
    attachBaseContext(paramContext);
    return paramContext;
  }
  
  public abstract View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet);
  
  public abstract <T extends View> T findViewById(int paramInt);
  
  public abstract ActionBarDrawerToggle.Delegate getDrawerToggleDelegate();
  
  public int getLocalNightMode() {
    return -100;
  }
  
  public abstract MenuInflater getMenuInflater();
  
  public abstract ActionBar getSupportActionBar();
  
  public abstract boolean hasWindowFeature(int paramInt);
  
  public abstract void installViewFactory();
  
  public abstract void invalidateOptionsMenu();
  
  public abstract boolean isHandleNativeActionModesEnabled();
  
  public abstract void onConfigurationChanged(Configuration paramConfiguration);
  
  public abstract void onCreate(Bundle paramBundle);
  
  public abstract void onDestroy();
  
  public abstract void onPostCreate(Bundle paramBundle);
  
  public abstract void onPostResume();
  
  public abstract void onSaveInstanceState(Bundle paramBundle);
  
  public abstract void onStart();
  
  public abstract void onStop();
  
  public abstract boolean requestWindowFeature(int paramInt);
  
  public abstract void setContentView(int paramInt);
  
  public abstract void setContentView(View paramView);
  
  public abstract void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams);
  
  public abstract void setHandleNativeActionModesEnabled(boolean paramBoolean);
  
  public abstract void setLocalNightMode(int paramInt);
  
  public abstract void setSupportActionBar(Toolbar paramToolbar);
  
  public void setTheme(int paramInt) {}
  
  public abstract void setTitle(CharSequence paramCharSequence);
  
  public abstract ActionMode startSupportActionMode(ActionMode.Callback paramCallback);
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface NightMode {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\app\AppCompatDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */