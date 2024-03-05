package androidx.core.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import androidx.core.util.Pair;

public class ActivityOptionsCompat {
  public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
  
  public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";
  
  public static ActivityOptionsCompat makeBasic() {
    return (Build.VERSION.SDK_INT >= 23) ? new ActivityOptionsCompatImpl(ActivityOptions.makeBasic()) : new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeClipRevealAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return (Build.VERSION.SDK_INT >= 23) ? new ActivityOptionsCompatImpl(ActivityOptions.makeClipRevealAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4)) : new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeCustomAnimation(Context paramContext, int paramInt1, int paramInt2) {
    return (Build.VERSION.SDK_INT >= 16) ? new ActivityOptionsCompatImpl(ActivityOptions.makeCustomAnimation(paramContext, paramInt1, paramInt2)) : new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeScaleUpAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return (Build.VERSION.SDK_INT >= 16) ? new ActivityOptionsCompatImpl(ActivityOptions.makeScaleUpAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4)) : new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity paramActivity, View paramView, String paramString) {
    return (Build.VERSION.SDK_INT >= 21) ? new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(paramActivity, paramView, paramString)) : new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity paramActivity, Pair<View, String>... paramVarArgs) {
    if (Build.VERSION.SDK_INT >= 21) {
      Pair[] arrayOfPair = null;
      if (paramVarArgs != null) {
        Pair[] arrayOfPair1 = new Pair[paramVarArgs.length];
        byte b = 0;
        while (true) {
          arrayOfPair = arrayOfPair1;
          if (b < paramVarArgs.length) {
            arrayOfPair1[b] = Pair.create((paramVarArgs[b]).first, (paramVarArgs[b]).second);
            b++;
            continue;
          } 
          break;
        } 
      } 
      return new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(paramActivity, arrayOfPair));
    } 
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeTaskLaunchBehind() {
    return (Build.VERSION.SDK_INT >= 21) ? new ActivityOptionsCompatImpl(ActivityOptions.makeTaskLaunchBehind()) : new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View paramView, Bitmap paramBitmap, int paramInt1, int paramInt2) {
    return (Build.VERSION.SDK_INT >= 16) ? new ActivityOptionsCompatImpl(ActivityOptions.makeThumbnailScaleUpAnimation(paramView, paramBitmap, paramInt1, paramInt2)) : new ActivityOptionsCompat();
  }
  
  public Rect getLaunchBounds() {
    return null;
  }
  
  public void requestUsageTimeReport(PendingIntent paramPendingIntent) {}
  
  public ActivityOptionsCompat setLaunchBounds(Rect paramRect) {
    return this;
  }
  
  public Bundle toBundle() {
    return null;
  }
  
  public void update(ActivityOptionsCompat paramActivityOptionsCompat) {}
  
  private static class ActivityOptionsCompatImpl extends ActivityOptionsCompat {
    private final ActivityOptions mActivityOptions;
    
    ActivityOptionsCompatImpl(ActivityOptions param1ActivityOptions) {
      this.mActivityOptions = param1ActivityOptions;
    }
    
    public Rect getLaunchBounds() {
      return (Build.VERSION.SDK_INT < 24) ? null : this.mActivityOptions.getLaunchBounds();
    }
    
    public void requestUsageTimeReport(PendingIntent param1PendingIntent) {
      if (Build.VERSION.SDK_INT >= 23)
        this.mActivityOptions.requestUsageTimeReport(param1PendingIntent); 
    }
    
    public ActivityOptionsCompat setLaunchBounds(Rect param1Rect) {
      return (Build.VERSION.SDK_INT < 24) ? this : new ActivityOptionsCompatImpl(this.mActivityOptions.setLaunchBounds(param1Rect));
    }
    
    public Bundle toBundle() {
      return this.mActivityOptions.toBundle();
    }
    
    public void update(ActivityOptionsCompat param1ActivityOptionsCompat) {
      if (param1ActivityOptionsCompat instanceof ActivityOptionsCompatImpl) {
        param1ActivityOptionsCompat = param1ActivityOptionsCompat;
        this.mActivityOptions.update(((ActivityOptionsCompatImpl)param1ActivityOptionsCompat).mActivityOptions);
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\ActivityOptionsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */