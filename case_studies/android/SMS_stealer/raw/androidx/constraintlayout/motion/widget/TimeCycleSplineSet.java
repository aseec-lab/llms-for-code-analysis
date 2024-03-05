package androidx.constraintlayout.motion.widget;

import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

public abstract class TimeCycleSplineSet {
  private static final int CURVE_OFFSET = 2;
  
  private static final int CURVE_PERIOD = 1;
  
  private static final int CURVE_VALUE = 0;
  
  private static final String TAG = "SplineSet";
  
  private static float VAL_2PI = 6.2831855F;
  
  private int count;
  
  float last_cycle = Float.NaN;
  
  long last_time;
  
  private float[] mCache = new float[3];
  
  protected boolean mContinue = false;
  
  protected CurveFit mCurveFit;
  
  protected int[] mTimePoints = new int[10];
  
  private String mType;
  
  protected float[][] mValues = new float[10][3];
  
  protected int mWaveShape = 0;
  
  static TimeCycleSplineSet makeCustomSpline(String paramString, SparseArray<ConstraintAttribute> paramSparseArray) {
    return new CustomSet(paramString, paramSparseArray);
  }
  
  static TimeCycleSplineSet makeSpline(String paramString, long paramLong) {
    ProgressSet progressSet;
    TranslationZset translationZset;
    TranslationYset translationYset;
    TranslationXset translationXset;
    ScaleYset scaleYset;
    ScaleXset scaleXset;
    PathRotate pathRotate;
    RotationYset rotationYset;
    RotationXset rotationXset;
    RotationSet rotationSet;
    ElevationSet elevationSet;
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 92909918:
        if (paramString.equals("alpha")) {
          b = 0;
          break;
        } 
      case 37232917:
        if (paramString.equals("transitionPathRotate")) {
          b = 5;
          break;
        } 
      case -4379043:
        if (paramString.equals("elevation")) {
          b = 1;
          break;
        } 
      case -40300674:
        if (paramString.equals("rotation")) {
          b = 2;
          break;
        } 
      case -908189617:
        if (paramString.equals("scaleY")) {
          b = 7;
          break;
        } 
      case -908189618:
        if (paramString.equals("scaleX")) {
          b = 6;
          break;
        } 
      case -1001078227:
        if (paramString.equals("progress")) {
          b = 11;
          break;
        } 
      case -1225497655:
        if (paramString.equals("translationZ")) {
          b = 10;
          break;
        } 
      case -1225497656:
        if (paramString.equals("translationY")) {
          b = 9;
          break;
        } 
      case -1225497657:
        if (paramString.equals("translationX")) {
          b = 8;
          break;
        } 
      case -1249320805:
        if (paramString.equals("rotationY")) {
          b = 4;
          break;
        } 
      case -1249320806:
        if (paramString.equals("rotationX")) {
          b = 3;
          break;
        } 
    } 
    switch (b) {
      default:
        return null;
      case 11:
        progressSet = new ProgressSet();
        progressSet.setStartTime(paramLong);
        return progressSet;
      case 10:
        translationZset = new TranslationZset();
        translationZset.setStartTime(paramLong);
        return translationZset;
      case 9:
        translationYset = new TranslationYset();
        translationYset.setStartTime(paramLong);
        return translationYset;
      case 8:
        translationXset = new TranslationXset();
        translationXset.setStartTime(paramLong);
        return translationXset;
      case 7:
        scaleYset = new ScaleYset();
        scaleYset.setStartTime(paramLong);
        return scaleYset;
      case 6:
        scaleXset = new ScaleXset();
        scaleXset.setStartTime(paramLong);
        return scaleXset;
      case 5:
        pathRotate = new PathRotate();
        pathRotate.setStartTime(paramLong);
        return pathRotate;
      case 4:
        rotationYset = new RotationYset();
        rotationYset.setStartTime(paramLong);
        return rotationYset;
      case 3:
        rotationXset = new RotationXset();
        rotationXset.setStartTime(paramLong);
        return rotationXset;
      case 2:
        rotationSet = new RotationSet();
        rotationSet.setStartTime(paramLong);
        return rotationSet;
      case 1:
        elevationSet = new ElevationSet();
        elevationSet.setStartTime(paramLong);
        return elevationSet;
      case 0:
        break;
    } 
    AlphaSet alphaSet = new AlphaSet();
    alphaSet.setStartTime(paramLong);
    return alphaSet;
  }
  
  protected float calcWave(float paramFloat) {
    switch (this.mWaveShape) {
      default:
        return (float)Math.sin((paramFloat * VAL_2PI));
      case 6:
        paramFloat = 1.0F - Math.abs(paramFloat * 4.0F % 4.0F - 2.0F);
        paramFloat *= paramFloat;
        return 1.0F - paramFloat;
      case 5:
        return (float)Math.cos((paramFloat * VAL_2PI));
      case 4:
        paramFloat = (paramFloat * 2.0F + 1.0F) % 2.0F;
        return 1.0F - paramFloat;
      case 3:
        return (paramFloat * 2.0F + 1.0F) % 2.0F - 1.0F;
      case 2:
        paramFloat = Math.abs(paramFloat);
        return 1.0F - paramFloat;
      case 1:
        break;
    } 
    return Math.signum(paramFloat * VAL_2PI);
  }
  
  public float get(float paramFloat, long paramLong, View paramView, KeyCache paramKeyCache) {
    this.mCurveFit.getPos(paramFloat, this.mCache);
    float[] arrayOfFloat = this.mCache;
    paramFloat = arrayOfFloat[1];
    int i = paramFloat cmp 0.0F;
    if (i == 0) {
      this.mContinue = false;
      return arrayOfFloat[2];
    } 
    if (Float.isNaN(this.last_cycle)) {
      float f = paramKeyCache.getFloatValue(paramView, this.mType, 0);
      this.last_cycle = f;
      if (Float.isNaN(f))
        this.last_cycle = 0.0F; 
    } 
    long l = this.last_time;
    paramFloat = (float)((this.last_cycle + (paramLong - l) * 1.0E-9D * paramFloat) % 1.0D);
    this.last_cycle = paramFloat;
    paramKeyCache.setFloatValue(paramView, this.mType, 0, paramFloat);
    this.last_time = paramLong;
    paramFloat = this.mCache[0];
    float f2 = calcWave(this.last_cycle);
    float f1 = this.mCache[2];
    if (paramFloat != 0.0F || i != 0) {
      boolean bool1 = true;
      this.mContinue = bool1;
      return f2 * paramFloat + f1;
    } 
    boolean bool = false;
    this.mContinue = bool;
    return f2 * paramFloat + f1;
  }
  
  public CurveFit getCurveFit() {
    return this.mCurveFit;
  }
  
  public void setPoint(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3) {
    int[] arrayOfInt = this.mTimePoints;
    int i = this.count;
    arrayOfInt[i] = paramInt1;
    float[][] arrayOfFloat = this.mValues;
    arrayOfFloat[i][0] = paramFloat1;
    arrayOfFloat[i][1] = paramFloat2;
    arrayOfFloat[i][2] = paramFloat3;
    this.mWaveShape = Math.max(this.mWaveShape, paramInt2);
    this.count++;
  }
  
  public abstract boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache);
  
  protected void setStartTime(long paramLong) {
    this.last_time = paramLong;
  }
  
  public void setType(String paramString) {
    this.mType = paramString;
  }
  
  public void setup(int paramInt) {
    int i = this.count;
    if (i == 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Error no points added to ");
      stringBuilder.append(this.mType);
      Log.e("SplineSet", stringBuilder.toString());
      return;
    } 
    Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, i - 1);
    int j = 1;
    i = 0;
    while (true) {
      int[] arrayOfInt = this.mTimePoints;
      if (j < arrayOfInt.length) {
        int k = i;
        if (arrayOfInt[j] != arrayOfInt[j - 1])
          k = i + 1; 
        j++;
        i = k;
        continue;
      } 
      j = i;
      if (i == 0)
        j = 1; 
      double[] arrayOfDouble = new double[j];
      double[][] arrayOfDouble1 = new double[j][3];
      i = 0;
      j = 0;
      while (i < this.count) {
        if (i > 0) {
          int[] arrayOfInt1 = this.mTimePoints;
          if (arrayOfInt1[i] == arrayOfInt1[i - 1])
            continue; 
        } 
        arrayOfDouble[j] = this.mTimePoints[i] * 0.01D;
        double[] arrayOfDouble2 = arrayOfDouble1[j];
        float[][] arrayOfFloat = this.mValues;
        arrayOfDouble2[0] = arrayOfFloat[i][0];
        arrayOfDouble1[j][1] = arrayOfFloat[i][1];
        arrayOfDouble1[j][2] = arrayOfFloat[i][2];
        j++;
        continue;
        i++;
      } 
      this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, arrayOfDouble1);
      return;
    } 
  }
  
  public String toString() {
    String str = this.mType;
    DecimalFormat decimalFormat = new DecimalFormat("##.##");
    for (byte b = 0; b < this.count; b++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("[");
      stringBuilder.append(this.mTimePoints[b]);
      stringBuilder.append(" , ");
      stringBuilder.append(decimalFormat.format(this.mValues[b]));
      stringBuilder.append("] ");
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  static class AlphaSet extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setAlpha(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  static class CustomSet extends TimeCycleSplineSet {
    String mAttributeName;
    
    float[] mCache;
    
    SparseArray<ConstraintAttribute> mConstraintAttributeList;
    
    float[] mTempValues;
    
    SparseArray<float[]> mWaveProperties = new SparseArray();
    
    public CustomSet(String param1String, SparseArray<ConstraintAttribute> param1SparseArray) {
      this.mAttributeName = param1String.split(",")[1];
      this.mConstraintAttributeList = param1SparseArray;
    }
    
    public void setPoint(int param1Int1, float param1Float1, float param1Float2, int param1Int2, float param1Float3) {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
    }
    
    public void setPoint(int param1Int1, ConstraintAttribute param1ConstraintAttribute, float param1Float1, int param1Int2, float param1Float2) {
      this.mConstraintAttributeList.append(param1Int1, param1ConstraintAttribute);
      this.mWaveProperties.append(param1Int1, new float[] { param1Float1, param1Float2 });
      this.mWaveShape = Math.max(this.mWaveShape, param1Int2);
    }
    
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      this.mCurveFit.getPos(param1Float, this.mTempValues);
      float[] arrayOfFloat = this.mTempValues;
      param1Float = arrayOfFloat[arrayOfFloat.length - 2];
      float f1 = arrayOfFloat[arrayOfFloat.length - 1];
      long l = this.last_time;
      if (Float.isNaN(this.last_cycle)) {
        this.last_cycle = param1KeyCache.getFloatValue(param1View, this.mAttributeName, 0);
        if (Float.isNaN(this.last_cycle))
          this.last_cycle = 0.0F; 
      } 
      this.last_cycle = (float)((this.last_cycle + (param1Long - l) * 1.0E-9D * param1Float) % 1.0D);
      this.last_time = param1Long;
      float f2 = calcWave(this.last_cycle);
      this.mContinue = false;
      for (byte b = 0; b < this.mCache.length; b++) {
        boolean bool1;
        boolean bool2 = this.mContinue;
        if (this.mTempValues[b] != 0.0D) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        this.mContinue = bool2 | bool1;
        this.mCache[b] = this.mTempValues[b] * f2 + f1;
      } 
      ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).setInterpolatedValue(param1View, this.mCache);
      if (param1Float != 0.0F)
        this.mContinue = true; 
      return this.mContinue;
    }
    
    public void setup(int param1Int) {
      int j = this.mConstraintAttributeList.size();
      int k = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).noOfInterpValues();
      double[] arrayOfDouble1 = new double[j];
      int i = k + 2;
      this.mTempValues = new float[i];
      this.mCache = new float[k];
      double[][] arrayOfDouble = new double[j][i];
      i = 0;
      while (i < j) {
        int m = this.mConstraintAttributeList.keyAt(i);
        ConstraintAttribute constraintAttribute = (ConstraintAttribute)this.mConstraintAttributeList.valueAt(i);
        float[] arrayOfFloat = (float[])this.mWaveProperties.valueAt(i);
        arrayOfDouble1[i] = m * 0.01D;
        constraintAttribute.getValuesToInterpolate(this.mTempValues);
        m = 0;
        while (true) {
          float[] arrayOfFloat1 = this.mTempValues;
          if (m < arrayOfFloat1.length) {
            arrayOfDouble[i][m] = arrayOfFloat1[m];
            m++;
            continue;
          } 
          arrayOfDouble[i][k] = arrayOfFloat[0];
          arrayOfDouble[i][k + 1] = arrayOfFloat[1];
          i++;
        } 
      } 
      this.mCurveFit = CurveFit.get(param1Int, arrayOfDouble1, arrayOfDouble);
    }
  }
  
  static class ElevationSet extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      if (Build.VERSION.SDK_INT >= 21)
        param1View.setElevation(get(param1Float, param1Long, param1View, param1KeyCache)); 
      return this.mContinue;
    }
  }
  
  static class PathRotate extends TimeCycleSplineSet {
    public boolean setPathRotate(View param1View, KeyCache param1KeyCache, float param1Float, long param1Long, double param1Double1, double param1Double2) {
      param1View.setRotation(get(param1Float, param1Long, param1View, param1KeyCache) + (float)Math.toDegrees(Math.atan2(param1Double2, param1Double1)));
      return this.mContinue;
    }
    
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      return this.mContinue;
    }
  }
  
  static class ProgressSet extends TimeCycleSplineSet {
    boolean mNoMethod = false;
    
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      if (param1View instanceof MotionLayout) {
        ((MotionLayout)param1View).setProgress(get(param1Float, param1Long, param1View, param1KeyCache));
      } else {
        if (this.mNoMethod)
          return false; 
        Method method = null;
        try {
          Method method1 = param1View.getClass().getMethod("setProgress", new Class[] { float.class });
          method = method1;
        } catch (NoSuchMethodException noSuchMethodException) {
          this.mNoMethod = true;
        } 
        if (method != null)
          try {
            method.invoke(param1View, new Object[] { Float.valueOf(get(param1Float, param1Long, param1View, param1KeyCache)) });
          } catch (IllegalAccessException illegalAccessException) {
            Log.e("SplineSet", "unable to setProgress", illegalAccessException);
          } catch (InvocationTargetException invocationTargetException) {
            Log.e("SplineSet", "unable to setProgress", invocationTargetException);
          }  
      } 
      return this.mContinue;
    }
  }
  
  static class RotationSet extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setRotation(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  static class RotationXset extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setRotationX(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  static class RotationYset extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setRotationY(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  static class ScaleXset extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setScaleX(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  static class ScaleYset extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setScaleY(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  private static class Sort {
    static void doubleQuickSort(int[] param1ArrayOfint, float[][] param1ArrayOffloat, int param1Int1, int param1Int2) {
      int[] arrayOfInt = new int[param1ArrayOfint.length + 10];
      arrayOfInt[0] = param1Int2;
      arrayOfInt[1] = param1Int1;
      param1Int1 = 2;
      while (param1Int1 > 0) {
        int i = arrayOfInt[--param1Int1];
        param1Int2 = param1Int1 - 1;
        int j = arrayOfInt[param1Int2];
        param1Int1 = param1Int2;
        if (i < j) {
          int k = partition(param1ArrayOfint, param1ArrayOffloat, i, j);
          param1Int1 = param1Int2 + 1;
          arrayOfInt[param1Int2] = k - 1;
          param1Int2 = param1Int1 + 1;
          arrayOfInt[param1Int1] = i;
          i = param1Int2 + 1;
          arrayOfInt[param1Int2] = j;
          param1Int1 = i + 1;
          arrayOfInt[i] = k + 1;
        } 
      } 
    }
    
    private static int partition(int[] param1ArrayOfint, float[][] param1ArrayOffloat, int param1Int1, int param1Int2) {
      int j = param1ArrayOfint[param1Int2];
      int i;
      for (i = param1Int1; param1Int1 < param1Int2; i = k) {
        int k = i;
        if (param1ArrayOfint[param1Int1] <= j) {
          swap(param1ArrayOfint, param1ArrayOffloat, i, param1Int1);
          k = i + 1;
        } 
        param1Int1++;
      } 
      swap(param1ArrayOfint, param1ArrayOffloat, i, param1Int2);
      return i;
    }
    
    private static void swap(int[] param1ArrayOfint, float[][] param1ArrayOffloat, int param1Int1, int param1Int2) {
      int i = param1ArrayOfint[param1Int1];
      param1ArrayOfint[param1Int1] = param1ArrayOfint[param1Int2];
      param1ArrayOfint[param1Int2] = i;
      float[] arrayOfFloat = param1ArrayOffloat[param1Int1];
      param1ArrayOffloat[param1Int1] = param1ArrayOffloat[param1Int2];
      param1ArrayOffloat[param1Int2] = arrayOfFloat;
    }
  }
  
  static class TranslationXset extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setTranslationX(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  static class TranslationYset extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      param1View.setTranslationY(get(param1Float, param1Long, param1View, param1KeyCache));
      return this.mContinue;
    }
  }
  
  static class TranslationZset extends TimeCycleSplineSet {
    public boolean setProperty(View param1View, float param1Float, long param1Long, KeyCache param1KeyCache) {
      if (Build.VERSION.SDK_INT >= 21)
        param1View.setTranslationZ(get(param1Float, param1Long, param1View, param1KeyCache)); 
      return this.mContinue;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\TimeCycleSplineSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */