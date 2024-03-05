package androidx.core.view.animation;

import android.graphics.Path;
import android.os.Build;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public final class PathInterpolatorCompat {
  public static Interpolator create(float paramFloat1, float paramFloat2) {
    return (Interpolator)((Build.VERSION.SDK_INT >= 21) ? new PathInterpolator(paramFloat1, paramFloat2) : new PathInterpolatorApi14(paramFloat1, paramFloat2));
  }
  
  public static Interpolator create(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    return (Interpolator)((Build.VERSION.SDK_INT >= 21) ? new PathInterpolator(paramFloat1, paramFloat2, paramFloat3, paramFloat4) : new PathInterpolatorApi14(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
  }
  
  public static Interpolator create(Path paramPath) {
    return (Interpolator)((Build.VERSION.SDK_INT >= 21) ? new PathInterpolator(paramPath) : new PathInterpolatorApi14(paramPath));
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\animation\PathInterpolatorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */