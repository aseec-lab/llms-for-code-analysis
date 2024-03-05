package androidx.core.view.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;

class PathInterpolatorApi14 implements Interpolator {
  private static final float PRECISION = 0.002F;
  
  private final float[] mX;
  
  private final float[] mY;
  
  PathInterpolatorApi14(float paramFloat1, float paramFloat2) {
    this(createQuad(paramFloat1, paramFloat2));
  }
  
  PathInterpolatorApi14(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this(createCubic(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
  }
  
  PathInterpolatorApi14(Path paramPath) {
    PathMeasure pathMeasure = new PathMeasure(paramPath, false);
    float f = pathMeasure.getLength();
    int i = (int)(f / 0.002F) + 1;
    this.mX = new float[i];
    this.mY = new float[i];
    float[] arrayOfFloat = new float[2];
    for (byte b = 0; b < i; b++) {
      pathMeasure.getPosTan(b * f / (i - 1), arrayOfFloat, null);
      this.mX[b] = arrayOfFloat[0];
      this.mY[b] = arrayOfFloat[1];
    } 
  }
  
  private static Path createCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 1.0F, 1.0F);
    return path;
  }
  
  private static Path createQuad(float paramFloat1, float paramFloat2) {
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.quadTo(paramFloat1, paramFloat2, 1.0F, 1.0F);
    return path;
  }
  
  public float getInterpolation(float paramFloat) {
    if (paramFloat <= 0.0F)
      return 0.0F; 
    if (paramFloat >= 1.0F)
      return 1.0F; 
    int j = 0;
    int i = this.mX.length - 1;
    while (i - j > 1) {
      int k = (j + i) / 2;
      if (paramFloat < this.mX[k]) {
        i = k;
        continue;
      } 
      j = k;
    } 
    float[] arrayOfFloat = this.mX;
    float f = arrayOfFloat[i] - arrayOfFloat[j];
    if (f == 0.0F)
      return this.mY[j]; 
    paramFloat = (paramFloat - arrayOfFloat[j]) / f;
    arrayOfFloat = this.mY;
    f = arrayOfFloat[j];
    return f + paramFloat * (arrayOfFloat[i] - f);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\animation\PathInterpolatorApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */