package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.SplineSet;

public class VelocityMatrix {
  private static String TAG = "VelocityMatrix";
  
  float mDRotate;
  
  float mDScaleX;
  
  float mDScaleY;
  
  float mDTranslateX;
  
  float mDTranslateY;
  
  float mRotate;
  
  public void applyTransform(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float[] paramArrayOffloat) {
    float f1 = paramArrayOffloat[0];
    float f2 = paramArrayOffloat[1];
    paramFloat1 = (paramFloat1 - 0.5F) * 2.0F;
    float f7 = (paramFloat2 - 0.5F) * 2.0F;
    float f5 = this.mDTranslateX;
    float f3 = this.mDTranslateY;
    paramFloat2 = this.mDScaleX;
    float f4 = this.mDScaleY;
    float f8 = (float)Math.toRadians(this.mRotate);
    float f6 = (float)Math.toRadians(this.mDRotate);
    double d4 = (-paramInt1 * paramFloat1);
    double d2 = f8;
    double d1 = Math.sin(d2);
    double d3 = (paramInt2 * f7);
    float f9 = (float)(d4 * d1 - Math.cos(d2) * d3);
    f8 = (float)((paramInt1 * paramFloat1) * Math.cos(d2) - d3 * Math.sin(d2));
    paramArrayOffloat[0] = f1 + f5 + paramFloat2 * paramFloat1 + f9 * f6;
    paramArrayOffloat[1] = f2 + f3 + f4 * f7 + f6 * f8;
  }
  
  public void clear() {
    this.mDRotate = 0.0F;
    this.mDTranslateY = 0.0F;
    this.mDTranslateX = 0.0F;
    this.mDScaleY = 0.0F;
    this.mDScaleX = 0.0F;
  }
  
  public void setRotationVelocity(KeyCycleOscillator paramKeyCycleOscillator, float paramFloat) {
    if (paramKeyCycleOscillator != null)
      this.mDRotate = paramKeyCycleOscillator.getSlope(paramFloat); 
  }
  
  public void setRotationVelocity(SplineSet paramSplineSet, float paramFloat) {
    if (paramSplineSet != null) {
      this.mDRotate = paramSplineSet.getSlope(paramFloat);
      this.mRotate = paramSplineSet.get(paramFloat);
    } 
  }
  
  public void setScaleVelocity(KeyCycleOscillator paramKeyCycleOscillator1, KeyCycleOscillator paramKeyCycleOscillator2, float paramFloat) {
    if (paramKeyCycleOscillator1 == null && paramKeyCycleOscillator2 == null)
      return; 
    if (paramKeyCycleOscillator1 == null)
      this.mDScaleX = paramKeyCycleOscillator1.getSlope(paramFloat); 
    if (paramKeyCycleOscillator2 == null)
      this.mDScaleY = paramKeyCycleOscillator2.getSlope(paramFloat); 
  }
  
  public void setScaleVelocity(SplineSet paramSplineSet1, SplineSet paramSplineSet2, float paramFloat) {
    if (paramSplineSet1 != null)
      this.mDScaleX = paramSplineSet1.getSlope(paramFloat); 
    if (paramSplineSet2 != null)
      this.mDScaleY = paramSplineSet2.getSlope(paramFloat); 
  }
  
  public void setTranslationVelocity(KeyCycleOscillator paramKeyCycleOscillator1, KeyCycleOscillator paramKeyCycleOscillator2, float paramFloat) {
    if (paramKeyCycleOscillator1 != null)
      this.mDTranslateX = paramKeyCycleOscillator1.getSlope(paramFloat); 
    if (paramKeyCycleOscillator2 != null)
      this.mDTranslateY = paramKeyCycleOscillator2.getSlope(paramFloat); 
  }
  
  public void setTranslationVelocity(SplineSet paramSplineSet1, SplineSet paramSplineSet2, float paramFloat) {
    if (paramSplineSet1 != null)
      this.mDTranslateX = paramSplineSet1.getSlope(paramFloat); 
    if (paramSplineSet2 != null)
      this.mDTranslateY = paramSplineSet2.getSlope(paramFloat); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motio\\utils\VelocityMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */