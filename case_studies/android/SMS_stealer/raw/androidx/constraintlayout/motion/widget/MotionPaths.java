package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.LinkedHashMap;

class MotionPaths implements Comparable<MotionPaths> {
  static final int CARTESIAN = 2;
  
  public static final boolean DEBUG = false;
  
  static final int OFF_HEIGHT = 4;
  
  static final int OFF_PATH_ROTATE = 5;
  
  static final int OFF_POSITION = 0;
  
  static final int OFF_WIDTH = 3;
  
  static final int OFF_X = 1;
  
  static final int OFF_Y = 2;
  
  public static final boolean OLD_WAY = false;
  
  static final int PERPENDICULAR = 1;
  
  static final int SCREEN = 3;
  
  public static final String TAG = "MotionPaths";
  
  static String[] names = new String[] { "position", "x", "y", "width", "height", "pathRotate" };
  
  LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap<String, ConstraintAttribute>();
  
  float height;
  
  int mDrawPath = 0;
  
  Easing mKeyFrameEasing;
  
  int mMode = 0;
  
  int mPathMotionArc = Key.UNSET;
  
  float mPathRotate = Float.NaN;
  
  float mProgress = Float.NaN;
  
  double[] mTempDelta = new double[18];
  
  double[] mTempValue = new double[18];
  
  float position;
  
  float time;
  
  float width;
  
  float x;
  
  float y;
  
  public MotionPaths() {}
  
  public MotionPaths(int paramInt1, int paramInt2, KeyPosition paramKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2) {
    int i = paramKeyPosition.mPositionType;
    if (i != 1) {
      if (i != 2) {
        initCartesian(paramKeyPosition, paramMotionPaths1, paramMotionPaths2);
        return;
      } 
      initScreen(paramInt1, paramInt2, paramKeyPosition, paramMotionPaths1, paramMotionPaths2);
      return;
    } 
    initPath(paramKeyPosition, paramMotionPaths1, paramMotionPaths2);
  }
  
  private boolean diff(float paramFloat1, float paramFloat2) {
    boolean bool = Float.isNaN(paramFloat1);
    boolean bool1 = true;
    boolean bool2 = true;
    if (bool || Float.isNaN(paramFloat2)) {
      if (Float.isNaN(paramFloat1) == Float.isNaN(paramFloat2))
        bool1 = false; 
      return bool1;
    } 
    if (Math.abs(paramFloat1 - paramFloat2) > 1.0E-6F) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    return bool1;
  }
  
  private static final float xRotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    return (paramFloat5 - paramFloat3) * paramFloat2 - (paramFloat6 - paramFloat4) * paramFloat1 + paramFloat3;
  }
  
  private static final float yRotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    return (paramFloat5 - paramFloat3) * paramFloat1 + (paramFloat6 - paramFloat4) * paramFloat2 + paramFloat4;
  }
  
  public void applyParameters(ConstraintSet.Constraint paramConstraint) {
    this.mKeyFrameEasing = Easing.getInterpolator(paramConstraint.motion.mTransitionEasing);
    this.mPathMotionArc = paramConstraint.motion.mPathMotionArc;
    this.mPathRotate = paramConstraint.motion.mPathRotate;
    this.mDrawPath = paramConstraint.motion.mDrawPath;
    this.mProgress = paramConstraint.propertySet.mProgress;
    for (String str : paramConstraint.mCustomConstraints.keySet()) {
      ConstraintAttribute constraintAttribute = (ConstraintAttribute)paramConstraint.mCustomConstraints.get(str);
      if (constraintAttribute.getType() != ConstraintAttribute.AttributeType.STRING_TYPE)
        this.attributes.put(str, constraintAttribute); 
    } 
  }
  
  public int compareTo(MotionPaths paramMotionPaths) {
    return Float.compare(this.position, paramMotionPaths.position);
  }
  
  void different(MotionPaths paramMotionPaths, boolean[] paramArrayOfboolean, String[] paramArrayOfString, boolean paramBoolean) {
    paramArrayOfboolean[0] = paramArrayOfboolean[0] | diff(this.position, paramMotionPaths.position);
    paramArrayOfboolean[1] = paramArrayOfboolean[1] | diff(this.x, paramMotionPaths.x) | paramBoolean;
    boolean bool = paramArrayOfboolean[2];
    paramArrayOfboolean[2] = paramBoolean | diff(this.y, paramMotionPaths.y) | bool;
    paramArrayOfboolean[3] = paramArrayOfboolean[3] | diff(this.width, paramMotionPaths.width);
    paramBoolean = paramArrayOfboolean[4];
    paramArrayOfboolean[4] = diff(this.height, paramMotionPaths.height) | paramBoolean;
  }
  
  void fillStandard(double[] paramArrayOfdouble, int[] paramArrayOfint) {
    float f1 = this.position;
    byte b = 0;
    float f6 = this.x;
    float f2 = this.y;
    float f4 = this.width;
    float f5 = this.height;
    float f3 = this.mPathRotate;
    int i;
    for (i = 0; b < paramArrayOfint.length; i = j) {
      int j = i;
      if (paramArrayOfint[b] < 6) {
        j = paramArrayOfint[b];
        (new float[6])[0] = f1;
        (new float[6])[1] = f6;
        (new float[6])[2] = f2;
        (new float[6])[3] = f4;
        (new float[6])[4] = f5;
        (new float[6])[5] = f3;
        paramArrayOfdouble[i] = (new float[6])[j];
        j = i + 1;
      } 
      b++;
    } 
  }
  
  void getBounds(int[] paramArrayOfint, double[] paramArrayOfdouble, float[] paramArrayOffloat, int paramInt) {
    float f2 = this.width;
    float f1 = this.height;
    for (byte b = 0; b < paramArrayOfint.length; b++) {
      float f = (float)paramArrayOfdouble[b];
      int i = paramArrayOfint[b];
      if (i != 3) {
        if (i == 4)
          f1 = f; 
      } else {
        f2 = f;
      } 
    } 
    paramArrayOffloat[paramInt] = f2;
    paramArrayOffloat[paramInt + 1] = f1;
  }
  
  void getCenter(int[] paramArrayOfint, double[] paramArrayOfdouble, float[] paramArrayOffloat, int paramInt) {
    float f4 = this.x;
    float f3 = this.y;
    float f2 = this.width;
    float f1 = this.height;
    for (byte b = 0; b < paramArrayOfint.length; b++) {
      float f = (float)paramArrayOfdouble[b];
      int i = paramArrayOfint[b];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i == 4)
              f1 = f; 
          } else {
            f2 = f;
          } 
        } else {
          f3 = f;
        } 
      } else {
        f4 = f;
      } 
    } 
    paramArrayOffloat[paramInt] = f4 + f2 / 2.0F + 0.0F;
    paramArrayOffloat[paramInt + 1] = f3 + f1 / 2.0F + 0.0F;
  }
  
  int getCustomData(String paramString, double[] paramArrayOfdouble, int paramInt) {
    ConstraintAttribute constraintAttribute = this.attributes.get(paramString);
    if (constraintAttribute.noOfInterpValues() == 1) {
      paramArrayOfdouble[paramInt] = constraintAttribute.getValueToInterpolate();
      return 1;
    } 
    int i = constraintAttribute.noOfInterpValues();
    float[] arrayOfFloat = new float[i];
    constraintAttribute.getValuesToInterpolate(arrayOfFloat);
    byte b = 0;
    while (b < i) {
      paramArrayOfdouble[paramInt] = arrayOfFloat[b];
      b++;
      paramInt++;
    } 
    return i;
  }
  
  int getCustomDataCount(String paramString) {
    return ((ConstraintAttribute)this.attributes.get(paramString)).noOfInterpValues();
  }
  
  void getRect(int[] paramArrayOfint, double[] paramArrayOfdouble, float[] paramArrayOffloat, int paramInt) {
    float f4 = this.x;
    float f3 = this.y;
    float f5 = this.width;
    float f2 = this.height;
    int i;
    for (i = 0; i < paramArrayOfint.length; i++) {
      float f = (float)paramArrayOfdouble[i];
      int k = paramArrayOfint[i];
      if (k != 1) {
        if (k != 2) {
          if (k != 3) {
            if (k == 4)
              f2 = f; 
          } else {
            f5 = f;
          } 
        } else {
          f3 = f;
        } 
      } else {
        f4 = f;
      } 
    } 
    float f1 = f5 + f4;
    f2 += f3;
    Float.isNaN(Float.NaN);
    Float.isNaN(Float.NaN);
    int j = paramInt + 1;
    paramArrayOffloat[paramInt] = f4 + 0.0F;
    i = j + 1;
    paramArrayOffloat[j] = f3 + 0.0F;
    paramInt = i + 1;
    paramArrayOffloat[i] = f1 + 0.0F;
    i = paramInt + 1;
    paramArrayOffloat[paramInt] = f3 + 0.0F;
    paramInt = i + 1;
    paramArrayOffloat[i] = f1 + 0.0F;
    i = paramInt + 1;
    paramArrayOffloat[paramInt] = f2 + 0.0F;
    paramArrayOffloat[i] = f4 + 0.0F;
    paramArrayOffloat[i + 1] = f2 + 0.0F;
  }
  
  boolean hasCustomData(String paramString) {
    return this.attributes.containsKey(paramString);
  }
  
  void initCartesian(KeyPosition paramKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2) {
    float f1 = paramKeyPosition.mFramePosition / 100.0F;
    this.time = f1;
    this.mDrawPath = paramKeyPosition.mDrawPath;
    if (Float.isNaN(paramKeyPosition.mPercentWidth)) {
      f3 = f1;
    } else {
      f3 = paramKeyPosition.mPercentWidth;
    } 
    if (Float.isNaN(paramKeyPosition.mPercentHeight)) {
      f2 = f1;
    } else {
      f2 = paramKeyPosition.mPercentHeight;
    } 
    float f7 = paramMotionPaths2.width;
    float f9 = paramMotionPaths1.width;
    float f11 = paramMotionPaths2.height;
    float f4 = paramMotionPaths1.height;
    this.position = this.time;
    float f10 = paramMotionPaths1.x;
    float f14 = f9 / 2.0F;
    float f8 = paramMotionPaths1.y;
    float f12 = f4 / 2.0F;
    float f5 = paramMotionPaths2.x;
    float f15 = f7 / 2.0F;
    float f6 = paramMotionPaths2.y;
    float f13 = f11 / 2.0F;
    f5 = f5 + f15 - f14 + f10;
    f6 = f6 + f13 - f8 + f12;
    float f3 = (f7 - f9) * f3;
    f7 = f3 / 2.0F;
    this.x = (int)(f10 + f5 * f1 - f7);
    float f2 = (f11 - f4) * f2;
    f10 = f2 / 2.0F;
    this.y = (int)(f8 + f6 * f1 - f10);
    this.width = (int)(f9 + f3);
    this.height = (int)(f4 + f2);
    if (Float.isNaN(paramKeyPosition.mPercentX)) {
      f2 = f1;
    } else {
      f2 = paramKeyPosition.mPercentX;
    } 
    boolean bool = Float.isNaN(paramKeyPosition.mAltPercentY);
    f4 = 0.0F;
    if (bool) {
      f3 = 0.0F;
    } else {
      f3 = paramKeyPosition.mAltPercentY;
    } 
    if (!Float.isNaN(paramKeyPosition.mPercentY))
      f1 = paramKeyPosition.mPercentY; 
    if (!Float.isNaN(paramKeyPosition.mAltPercentX))
      f4 = paramKeyPosition.mAltPercentX; 
    this.mMode = 2;
    this.x = (int)(paramMotionPaths1.x + f2 * f5 + f4 * f6 - f7);
    this.y = (int)(paramMotionPaths1.y + f5 * f3 + f6 * f1 - f10);
    this.mKeyFrameEasing = Easing.getInterpolator(paramKeyPosition.mTransitionEasing);
    this.mPathMotionArc = paramKeyPosition.mPathMotionArc;
  }
  
  void initPath(KeyPosition paramKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2) {
    float f1 = paramKeyPosition.mFramePosition / 100.0F;
    this.time = f1;
    this.mDrawPath = paramKeyPosition.mDrawPath;
    if (Float.isNaN(paramKeyPosition.mPercentWidth)) {
      f3 = f1;
    } else {
      f3 = paramKeyPosition.mPercentWidth;
    } 
    if (Float.isNaN(paramKeyPosition.mPercentHeight)) {
      f2 = f1;
    } else {
      f2 = paramKeyPosition.mPercentHeight;
    } 
    float f12 = paramMotionPaths2.width;
    float f13 = paramMotionPaths1.width;
    float f9 = paramMotionPaths2.height;
    float f5 = paramMotionPaths1.height;
    this.position = this.time;
    if (!Float.isNaN(paramKeyPosition.mPercentX))
      f1 = paramKeyPosition.mPercentX; 
    float f11 = paramMotionPaths1.x;
    float f6 = paramMotionPaths1.width;
    float f17 = f6 / 2.0F;
    float f7 = paramMotionPaths1.y;
    float f8 = paramMotionPaths1.height;
    float f14 = f8 / 2.0F;
    float f4 = paramMotionPaths2.x;
    float f16 = paramMotionPaths2.width / 2.0F;
    float f15 = paramMotionPaths2.y;
    float f10 = paramMotionPaths2.height / 2.0F;
    f4 = f4 + f16 - f17 + f11;
    f14 = f15 + f10 - f14 + f7;
    f10 = f4 * f1;
    f12 = (f12 - f13) * f3;
    float f3 = f12 / 2.0F;
    this.x = (int)(f11 + f10 - f3);
    f11 = f1 * f14;
    f1 = (f9 - f5) * f2;
    float f2 = f1 / 2.0F;
    this.y = (int)(f7 + f11 - f2);
    this.width = (int)(f6 + f12);
    this.height = (int)(f8 + f1);
    if (Float.isNaN(paramKeyPosition.mPercentY)) {
      f1 = 0.0F;
    } else {
      f1 = paramKeyPosition.mPercentY;
    } 
    f5 = -f14;
    this.mMode = 1;
    f3 = (int)(paramMotionPaths1.x + f10 - f3);
    this.x = f3;
    f2 = (int)(paramMotionPaths1.y + f11 - f2);
    this.y = f2;
    this.x = f3 + f5 * f1;
    this.y = f2 + f4 * f1;
    this.mKeyFrameEasing = Easing.getInterpolator(paramKeyPosition.mTransitionEasing);
    this.mPathMotionArc = paramKeyPosition.mPathMotionArc;
  }
  
  void initScreen(int paramInt1, int paramInt2, KeyPosition paramKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2) {
    float f1 = paramKeyPosition.mFramePosition / 100.0F;
    this.time = f1;
    this.mDrawPath = paramKeyPosition.mDrawPath;
    if (Float.isNaN(paramKeyPosition.mPercentWidth)) {
      f2 = f1;
    } else {
      f2 = paramKeyPosition.mPercentWidth;
    } 
    if (Float.isNaN(paramKeyPosition.mPercentHeight)) {
      f3 = f1;
    } else {
      f3 = paramKeyPosition.mPercentHeight;
    } 
    float f15 = paramMotionPaths2.width;
    float f7 = paramMotionPaths1.width;
    float f10 = paramMotionPaths2.height;
    float f9 = paramMotionPaths1.height;
    this.position = this.time;
    float f14 = paramMotionPaths1.x;
    float f13 = f7 / 2.0F;
    float f4 = paramMotionPaths1.y;
    float f6 = f9 / 2.0F;
    float f11 = paramMotionPaths2.x;
    float f12 = f15 / 2.0F;
    float f5 = paramMotionPaths2.y;
    float f8 = f10 / 2.0F;
    float f2 = (f15 - f7) * f2;
    this.x = (int)(f14 + (f11 + f12 - f13 + f14) * f1 - f2 / 2.0F);
    float f3 = (f10 - f9) * f3;
    this.y = (int)(f4 + (f5 + f8 - f4 + f6) * f1 - f3 / 2.0F);
    this.width = (int)(f7 + f2);
    this.height = (int)(f9 + f3);
    this.mMode = 3;
    if (!Float.isNaN(paramKeyPosition.mPercentX)) {
      paramInt1 = (int)(paramInt1 - this.width);
      this.x = (int)(paramKeyPosition.mPercentX * paramInt1);
    } 
    if (!Float.isNaN(paramKeyPosition.mPercentY)) {
      paramInt1 = (int)(paramInt2 - this.height);
      this.y = (int)(paramKeyPosition.mPercentY * paramInt1);
    } 
    this.mKeyFrameEasing = Easing.getInterpolator(paramKeyPosition.mTransitionEasing);
    this.mPathMotionArc = paramKeyPosition.mPathMotionArc;
  }
  
  void setBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.width = paramFloat3;
    this.height = paramFloat4;
  }
  
  void setDpDt(float paramFloat1, float paramFloat2, float[] paramArrayOffloat, int[] paramArrayOfint, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
    byte b = 0;
    float f5 = 0.0F;
    float f3 = 0.0F;
    float f4 = 0.0F;
    float f2 = 0.0F;
    while (b < paramArrayOfint.length) {
      float f = (float)paramArrayOfdouble1[b];
      double d = paramArrayOfdouble2[b];
      int i = paramArrayOfint[b];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i == 4)
              f2 = f; 
          } else {
            f3 = f;
          } 
        } else {
          f4 = f;
        } 
      } else {
        f5 = f;
      } 
      b++;
    } 
    float f1 = f5 - 0.0F * f3 / 2.0F;
    f4 -= 0.0F * f2 / 2.0F;
    paramArrayOffloat[0] = f1 * (1.0F - paramFloat1) + (f3 * 1.0F + f1) * paramFloat1 + 0.0F;
    paramArrayOffloat[1] = f4 * (1.0F - paramFloat2) + (f2 * 1.0F + f4) * paramFloat2 + 0.0F;
  }
  
  void setView(View paramView, int[] paramArrayOfint, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3) {
    float f1 = this.x;
    float f7 = this.y;
    float f6 = this.width;
    float f4 = this.height;
    if (paramArrayOfint.length != 0 && this.mTempValue.length <= paramArrayOfint[paramArrayOfint.length - 1]) {
      int i = paramArrayOfint[paramArrayOfint.length - 1] + 1;
      this.mTempValue = new double[i];
      this.mTempDelta = new double[i];
    } 
    Arrays.fill(this.mTempValue, Double.NaN);
    byte b;
    for (b = 0; b < paramArrayOfint.length; b++) {
      this.mTempValue[paramArrayOfint[b]] = paramArrayOfdouble1[b];
      this.mTempDelta[paramArrayOfint[b]] = paramArrayOfdouble2[b];
    } 
    b = 0;
    float f9 = Float.NaN;
    float f8 = 0.0F;
    float f5 = 0.0F;
    float f3 = 0.0F;
    float f2 = 0.0F;
    while (true) {
      double[] arrayOfDouble = this.mTempValue;
      if (b < arrayOfDouble.length) {
        boolean bool = Double.isNaN(arrayOfDouble[b]);
        double d = 0.0D;
        if (!bool || (paramArrayOfdouble3 != null && paramArrayOfdouble3[b] != 0.0D)) {
          if (paramArrayOfdouble3 != null)
            d = paramArrayOfdouble3[b]; 
          if (!Double.isNaN(this.mTempValue[b]))
            d = this.mTempValue[b] + d; 
          float f10 = (float)d;
          float f11 = (float)this.mTempDelta[b];
          if (b != 1) {
            if (b != 2) {
              if (b != 3) {
                if (b != 4) {
                  if (b == 5)
                    f9 = f10; 
                } else {
                  f4 = f10;
                  f2 = f11;
                } 
              } else {
                f6 = f10;
                f5 = f11;
              } 
            } else {
              f7 = f10;
              f3 = f11;
            } 
          } else {
            f8 = f11;
            f1 = f10;
          } 
        } 
        b++;
        continue;
      } 
      if (Float.isNaN(f9)) {
        if (!Float.isNaN(Float.NaN))
          paramView.setRotation(Float.NaN); 
      } else {
        float f10 = Float.NaN;
        if (Float.isNaN(Float.NaN))
          f10 = 0.0F; 
        float f11 = f5 / 2.0F;
        f2 /= 2.0F;
        paramView.setRotation((float)(f10 + f9 + Math.toDegrees(Math.atan2((f3 + f2), (f8 + f11)))));
      } 
      float f = f1 + 0.5F;
      int i = (int)f;
      f1 = f7 + 0.5F;
      int j = (int)f1;
      int n = (int)(f + f6);
      int k = (int)(f1 + f4);
      int i1 = n - i;
      int m = k - j;
      if (i1 != paramView.getMeasuredWidth() || m != paramView.getMeasuredHeight()) {
        b = 1;
      } else {
        b = 0;
      } 
      if (b != 0)
        paramView.measure(View.MeasureSpec.makeMeasureSpec(i1, 1073741824), View.MeasureSpec.makeMeasureSpec(m, 1073741824)); 
      paramView.layout(i, j, n, k);
      return;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\MotionPaths.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */