package androidx.constraintlayout.motion.widget;

import android.view.animation.Interpolator;

public abstract class MotionInterpolator implements Interpolator {
  public abstract float getInterpolation(float paramFloat);
  
  public abstract float getVelocity();
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\MotionInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */