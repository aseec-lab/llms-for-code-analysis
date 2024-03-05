package androidx.constraintlayout.motion.utils;

import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

public class StopLogic extends MotionInterpolator {
  private boolean mBackwards = false;
  
  private float mLastPosition;
  
  private int mNumberOfStages;
  
  private float mStage1Duration;
  
  private float mStage1EndPosition;
  
  private float mStage1Velocity;
  
  private float mStage2Duration;
  
  private float mStage2EndPosition;
  
  private float mStage2Velocity;
  
  private float mStage3Duration;
  
  private float mStage3EndPosition;
  
  private float mStage3Velocity;
  
  private float mStartPosition;
  
  private String mType;
  
  private float calcY(float paramFloat) {
    float f1 = this.mStage1Duration;
    if (paramFloat <= f1) {
      float f = this.mStage1Velocity;
      return f * paramFloat + (this.mStage2Velocity - f) * paramFloat * paramFloat / f1 * 2.0F;
    } 
    int i = this.mNumberOfStages;
    if (i == 1)
      return this.mStage1EndPosition; 
    paramFloat -= f1;
    f1 = this.mStage2Duration;
    if (paramFloat < f1) {
      float f5 = this.mStage1EndPosition;
      float f4 = this.mStage2Velocity;
      return f5 + f4 * paramFloat + (this.mStage3Velocity - f4) * paramFloat * paramFloat / f1 * 2.0F;
    } 
    if (i == 2)
      return this.mStage2EndPosition; 
    float f2 = paramFloat - f1;
    float f3 = this.mStage3Duration;
    if (f2 < f3) {
      f1 = this.mStage2EndPosition;
      paramFloat = this.mStage3Velocity;
      return f1 + paramFloat * f2 - paramFloat * f2 * f2 / f3 * 2.0F;
    } 
    return this.mStage3EndPosition;
  }
  
  private void setup(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    float f1 = paramFloat1;
    if (paramFloat1 == 0.0F)
      f1 = 1.0E-4F; 
    this.mStage1Velocity = f1;
    paramFloat1 = f1 / paramFloat3;
    float f2 = paramFloat1 * f1 / 2.0F;
    if (f1 < 0.0F) {
      paramFloat1 = (float)Math.sqrt(((paramFloat2 - -f1 / paramFloat3 * f1 / 2.0F) * paramFloat3));
      if (paramFloat1 < paramFloat4) {
        this.mType = "backward accelerate, decelerate";
        this.mNumberOfStages = 2;
        this.mStage1Velocity = f1;
        this.mStage2Velocity = paramFloat1;
        this.mStage3Velocity = 0.0F;
        paramFloat4 = (paramFloat1 - f1) / paramFloat3;
        this.mStage1Duration = paramFloat4;
        this.mStage2Duration = paramFloat1 / paramFloat3;
        this.mStage1EndPosition = (f1 + paramFloat1) * paramFloat4 / 2.0F;
        this.mStage2EndPosition = paramFloat2;
        this.mStage3EndPosition = paramFloat2;
        return;
      } 
      this.mType = "backward accelerate cruse decelerate";
      this.mNumberOfStages = 3;
      this.mStage1Velocity = f1;
      this.mStage2Velocity = paramFloat4;
      this.mStage3Velocity = paramFloat4;
      paramFloat5 = (paramFloat4 - f1) / paramFloat3;
      this.mStage1Duration = paramFloat5;
      paramFloat1 = paramFloat4 / paramFloat3;
      this.mStage3Duration = paramFloat1;
      paramFloat3 = (f1 + paramFloat4) * paramFloat5 / 2.0F;
      paramFloat1 = paramFloat1 * paramFloat4 / 2.0F;
      this.mStage2Duration = (paramFloat2 - paramFloat3 - paramFloat1) / paramFloat4;
      this.mStage1EndPosition = paramFloat3;
      this.mStage2EndPosition = paramFloat2 - paramFloat1;
      this.mStage3EndPosition = paramFloat2;
      return;
    } 
    if (f2 >= paramFloat2) {
      this.mType = "hard stop";
      paramFloat1 = 2.0F * paramFloat2 / f1;
      this.mNumberOfStages = 1;
      this.mStage1Velocity = f1;
      this.mStage2Velocity = 0.0F;
      this.mStage1EndPosition = paramFloat2;
      this.mStage1Duration = paramFloat1;
      return;
    } 
    f2 = paramFloat2 - f2;
    float f3 = f2 / f1;
    if (f3 + paramFloat1 < paramFloat5) {
      this.mType = "cruse decelerate";
      this.mNumberOfStages = 2;
      this.mStage1Velocity = f1;
      this.mStage2Velocity = f1;
      this.mStage3Velocity = 0.0F;
      this.mStage1EndPosition = f2;
      this.mStage2EndPosition = paramFloat2;
      this.mStage1Duration = f3;
      this.mStage2Duration = paramFloat1;
      return;
    } 
    f2 = (float)Math.sqrt((paramFloat3 * paramFloat2 + f1 * f1 / 2.0F));
    paramFloat1 = (f2 - f1) / paramFloat3;
    this.mStage1Duration = paramFloat1;
    paramFloat5 = f2 / paramFloat3;
    this.mStage2Duration = paramFloat5;
    if (f2 < paramFloat4) {
      this.mType = "accelerate decelerate";
      this.mNumberOfStages = 2;
      this.mStage1Velocity = f1;
      this.mStage2Velocity = f2;
      this.mStage3Velocity = 0.0F;
      this.mStage1Duration = paramFloat1;
      this.mStage2Duration = paramFloat5;
      this.mStage1EndPosition = (f1 + f2) * paramFloat1 / 2.0F;
      this.mStage2EndPosition = paramFloat2;
      return;
    } 
    this.mType = "accelerate cruse decelerate";
    this.mNumberOfStages = 3;
    this.mStage1Velocity = f1;
    this.mStage2Velocity = paramFloat4;
    this.mStage3Velocity = paramFloat4;
    paramFloat5 = (paramFloat4 - f1) / paramFloat3;
    this.mStage1Duration = paramFloat5;
    paramFloat1 = paramFloat4 / paramFloat3;
    this.mStage3Duration = paramFloat1;
    paramFloat3 = (f1 + paramFloat4) * paramFloat5 / 2.0F;
    paramFloat1 = paramFloat1 * paramFloat4 / 2.0F;
    this.mStage2Duration = (paramFloat2 - paramFloat3 - paramFloat1) / paramFloat4;
    this.mStage1EndPosition = paramFloat3;
    this.mStage2EndPosition = paramFloat2 - paramFloat1;
    this.mStage3EndPosition = paramFloat2;
  }
  
  public void config(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    boolean bool;
    this.mStartPosition = paramFloat1;
    if (paramFloat1 > paramFloat2) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mBackwards = bool;
    if (bool) {
      setup(-paramFloat3, paramFloat1 - paramFloat2, paramFloat5, paramFloat6, paramFloat4);
    } else {
      setup(paramFloat3, paramFloat2 - paramFloat1, paramFloat5, paramFloat6, paramFloat4);
    } 
  }
  
  public void debug(String paramString1, String paramString2, float paramFloat) {
    String str;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString2);
    stringBuilder2.append(" ===== ");
    stringBuilder2.append(this.mType);
    Log.v(paramString1, stringBuilder2.toString());
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append(paramString2);
    if (this.mBackwards) {
      str = "backwards";
    } else {
      str = "forward ";
    } 
    stringBuilder3.append(str);
    stringBuilder3.append(" time = ");
    stringBuilder3.append(paramFloat);
    stringBuilder3.append("  stages ");
    stringBuilder3.append(this.mNumberOfStages);
    Log.v(paramString1, stringBuilder3.toString());
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(paramString2);
    stringBuilder1.append(" dur ");
    stringBuilder1.append(this.mStage1Duration);
    stringBuilder1.append(" vel ");
    stringBuilder1.append(this.mStage1Velocity);
    stringBuilder1.append(" pos ");
    stringBuilder1.append(this.mStage1EndPosition);
    Log.v(paramString1, stringBuilder1.toString());
    if (this.mNumberOfStages > 1) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append(" dur ");
      stringBuilder1.append(this.mStage2Duration);
      stringBuilder1.append(" vel ");
      stringBuilder1.append(this.mStage2Velocity);
      stringBuilder1.append(" pos ");
      stringBuilder1.append(this.mStage2EndPosition);
      Log.v(paramString1, stringBuilder1.toString());
    } 
    if (this.mNumberOfStages > 2) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append(" dur ");
      stringBuilder1.append(this.mStage3Duration);
      stringBuilder1.append(" vel ");
      stringBuilder1.append(this.mStage3Velocity);
      stringBuilder1.append(" pos ");
      stringBuilder1.append(this.mStage3EndPosition);
      Log.v(paramString1, stringBuilder1.toString());
    } 
    float f = this.mStage1Duration;
    if (paramFloat <= f) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append("stage 0");
      Log.v(paramString1, stringBuilder1.toString());
      return;
    } 
    int i = this.mNumberOfStages;
    if (i == 1) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append("end stage 0");
      Log.v(paramString1, stringBuilder1.toString());
      return;
    } 
    paramFloat -= f;
    f = this.mStage2Duration;
    if (paramFloat < f) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append(" stage 1");
      Log.v(paramString1, stringBuilder1.toString());
      return;
    } 
    if (i == 2) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append("end stage 1");
      Log.v(paramString1, stringBuilder1.toString());
      return;
    } 
    if (paramFloat - f < this.mStage3Duration) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append(" stage 2");
      Log.v(paramString1, stringBuilder1.toString());
      return;
    } 
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append(paramString2);
    stringBuilder1.append(" end stage 2");
    Log.v(paramString1, stringBuilder1.toString());
  }
  
  public float getInterpolation(float paramFloat) {
    float f = calcY(paramFloat);
    this.mLastPosition = paramFloat;
    if (this.mBackwards) {
      paramFloat = this.mStartPosition - f;
    } else {
      paramFloat = this.mStartPosition + f;
    } 
    return paramFloat;
  }
  
  public float getVelocity() {
    float f;
    if (this.mBackwards) {
      f = -getVelocity(this.mLastPosition);
    } else {
      f = getVelocity(this.mLastPosition);
    } 
    return f;
  }
  
  public float getVelocity(float paramFloat) {
    float f1 = this.mStage1Duration;
    if (paramFloat <= f1) {
      float f = this.mStage1Velocity;
      return f + (this.mStage2Velocity - f) * paramFloat / f1;
    } 
    int i = this.mNumberOfStages;
    if (i == 1)
      return 0.0F; 
    float f2 = paramFloat - f1;
    paramFloat = this.mStage2Duration;
    if (f2 < paramFloat) {
      f1 = this.mStage2Velocity;
      return f1 + (this.mStage3Velocity - f1) * f2 / paramFloat;
    } 
    if (i == 2)
      return this.mStage2EndPosition; 
    f1 = f2 - paramFloat;
    paramFloat = this.mStage3Duration;
    if (f1 < paramFloat) {
      f2 = this.mStage3Velocity;
      return f2 - f1 * f2 / paramFloat;
    } 
    return this.mStage3EndPosition;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motio\\utils\StopLogic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */