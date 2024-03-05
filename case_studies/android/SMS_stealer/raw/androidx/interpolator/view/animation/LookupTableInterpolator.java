package androidx.interpolator.view.animation;

import android.view.animation.Interpolator;

abstract class LookupTableInterpolator implements Interpolator {
  private final float mStepSize;
  
  private final float[] mValues;
  
  protected LookupTableInterpolator(float[] paramArrayOffloat) {
    this.mValues = paramArrayOffloat;
    this.mStepSize = 1.0F / (paramArrayOffloat.length - 1);
  }
  
  public float getInterpolation(float paramFloat) {
    if (paramFloat >= 1.0F)
      return 1.0F; 
    if (paramFloat <= 0.0F)
      return 0.0F; 
    float[] arrayOfFloat = this.mValues;
    int i = Math.min((int)((arrayOfFloat.length - 1) * paramFloat), arrayOfFloat.length - 2);
    float f2 = i;
    float f1 = this.mStepSize;
    paramFloat = (paramFloat - f2 * f1) / f1;
    arrayOfFloat = this.mValues;
    return arrayOfFloat[i] + paramFloat * (arrayOfFloat[i + 1] - arrayOfFloat[i]);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\interpolator\view\animation\LookupTableInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */