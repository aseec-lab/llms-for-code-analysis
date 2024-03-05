package androidx.constraintlayout.motion.widget;

import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public abstract class KeyCycleOscillator {
  private static final String TAG = "KeyCycleOscillator";
  
  private CurveFit mCurveFit;
  
  protected ConstraintAttribute mCustom;
  
  private CycleOscillator mCycleOscillator;
  
  private String mType;
  
  public int mVariesBy = 0;
  
  ArrayList<WavePoint> mWavePoints = new ArrayList<WavePoint>();
  
  private int mWaveShape = 0;
  
  static KeyCycleOscillator makeSpline(String paramString) {
    if (paramString.startsWith("CUSTOM"))
      return new CustomSet(); 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 156108012:
        if (paramString.equals("waveOffset"))
          b = 8; 
        break;
      case 92909918:
        if (paramString.equals("alpha"))
          b = 0; 
        break;
      case 37232917:
        if (paramString.equals("transitionPathRotate"))
          b = 5; 
        break;
      case -4379043:
        if (paramString.equals("elevation"))
          b = 1; 
        break;
      case -40300674:
        if (paramString.equals("rotation"))
          b = 2; 
        break;
      case -797520672:
        if (paramString.equals("waveVariesBy"))
          b = 9; 
        break;
      case -908189617:
        if (paramString.equals("scaleY"))
          b = 7; 
        break;
      case -908189618:
        if (paramString.equals("scaleX"))
          b = 6; 
        break;
      case -1001078227:
        if (paramString.equals("progress"))
          b = 13; 
        break;
      case -1225497655:
        if (paramString.equals("translationZ"))
          b = 12; 
        break;
      case -1225497656:
        if (paramString.equals("translationY"))
          b = 11; 
        break;
      case -1225497657:
        if (paramString.equals("translationX"))
          b = 10; 
        break;
      case -1249320805:
        if (paramString.equals("rotationY"))
          b = 4; 
        break;
      case -1249320806:
        if (paramString.equals("rotationX"))
          b = 3; 
        break;
    } 
    switch (b) {
      default:
        return null;
      case 13:
        return new ProgressSet();
      case 12:
        return new TranslationZset();
      case 11:
        return new TranslationYset();
      case 10:
        return new TranslationXset();
      case 9:
        return new AlphaSet();
      case 8:
        return new AlphaSet();
      case 7:
        return new ScaleYset();
      case 6:
        return new ScaleXset();
      case 5:
        return new PathRotateSet();
      case 4:
        return new RotationYset();
      case 3:
        return new RotationXset();
      case 2:
        return new RotationSet();
      case 1:
        return new ElevationSet();
      case 0:
        break;
    } 
    return new AlphaSet();
  }
  
  public float get(float paramFloat) {
    return (float)this.mCycleOscillator.getValues(paramFloat);
  }
  
  public CurveFit getCurveFit() {
    return this.mCurveFit;
  }
  
  public float getSlope(float paramFloat) {
    return (float)this.mCycleOscillator.getSlope(paramFloat);
  }
  
  public void setPoint(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3) {
    this.mWavePoints.add(new WavePoint(paramInt1, paramFloat1, paramFloat2, paramFloat3));
    if (paramInt3 != -1)
      this.mVariesBy = paramInt3; 
    this.mWaveShape = paramInt2;
  }
  
  public void setPoint(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, ConstraintAttribute paramConstraintAttribute) {
    this.mWavePoints.add(new WavePoint(paramInt1, paramFloat1, paramFloat2, paramFloat3));
    if (paramInt3 != -1)
      this.mVariesBy = paramInt3; 
    this.mWaveShape = paramInt2;
    this.mCustom = paramConstraintAttribute;
  }
  
  public abstract void setProperty(View paramView, float paramFloat);
  
  public void setType(String paramString) {
    this.mType = paramString;
  }
  
  public void setup(float paramFloat) {
    int i = this.mWavePoints.size();
    if (i == 0)
      return; 
    Collections.sort(this.mWavePoints, new Comparator<WavePoint>() {
          final KeyCycleOscillator this$0;
          
          public int compare(KeyCycleOscillator.WavePoint param1WavePoint1, KeyCycleOscillator.WavePoint param1WavePoint2) {
            return Integer.compare(param1WavePoint1.mPosition, param1WavePoint2.mPosition);
          }
        });
    double[] arrayOfDouble1 = new double[i];
    double[][] arrayOfDouble = new double[i][2];
    this.mCycleOscillator = new CycleOscillator(this.mWaveShape, this.mVariesBy, i);
    Iterator<WavePoint> iterator = this.mWavePoints.iterator();
    for (i = 0; iterator.hasNext(); i++) {
      WavePoint wavePoint = iterator.next();
      arrayOfDouble1[i] = wavePoint.mPeriod * 0.01D;
      arrayOfDouble[i][0] = wavePoint.mValue;
      arrayOfDouble[i][1] = wavePoint.mOffset;
      this.mCycleOscillator.setPoint(i, wavePoint.mPosition, wavePoint.mPeriod, wavePoint.mOffset, wavePoint.mValue);
    } 
    this.mCycleOscillator.setup(paramFloat);
    this.mCurveFit = CurveFit.get(0, arrayOfDouble1, arrayOfDouble);
  }
  
  public String toString() {
    String str = this.mType;
    DecimalFormat decimalFormat = new DecimalFormat("##.##");
    for (WavePoint wavePoint : this.mWavePoints) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("[");
      stringBuilder.append(wavePoint.mPosition);
      stringBuilder.append(" , ");
      stringBuilder.append(decimalFormat.format(wavePoint.mValue));
      stringBuilder.append("] ");
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  public boolean variesByPath() {
    int i = this.mVariesBy;
    boolean bool = true;
    if (i != 1)
      bool = false; 
    return bool;
  }
  
  static class AlphaSet extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setAlpha(get(param1Float));
    }
  }
  
  static class CustomSet extends KeyCycleOscillator {
    float[] value = new float[1];
    
    public void setProperty(View param1View, float param1Float) {
      this.value[0] = get(param1Float);
      this.mCustom.setInterpolatedValue(param1View, this.value);
    }
  }
  
  static class CycleOscillator {
    private static final String TAG = "CycleOscillator";
    
    static final int UNSET = -1;
    
    CurveFit mCurveFit;
    
    public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap<String, ConstraintAttribute>();
    
    float[] mOffset;
    
    Oscillator mOscillator = new Oscillator();
    
    float mPathLength;
    
    float[] mPeriod;
    
    double[] mPosition;
    
    float[] mScale;
    
    double[] mSplineSlopeCache;
    
    double[] mSplineValueCache;
    
    float[] mValues;
    
    private final int mVariesBy;
    
    int mWaveShape;
    
    CycleOscillator(int param1Int1, int param1Int2, int param1Int3) {
      this.mWaveShape = param1Int1;
      this.mVariesBy = param1Int2;
      this.mOscillator.setType(param1Int1);
      this.mValues = new float[param1Int3];
      this.mPosition = new double[param1Int3];
      this.mPeriod = new float[param1Int3];
      this.mOffset = new float[param1Int3];
      this.mScale = new float[param1Int3];
    }
    
    private ConstraintAttribute get(String param1String, ConstraintAttribute.AttributeType param1AttributeType) {
      ConstraintAttribute constraintAttribute;
      StringBuilder stringBuilder;
      if (this.mCustomConstraints.containsKey(param1String)) {
        constraintAttribute = this.mCustomConstraints.get(param1String);
        if (constraintAttribute.getType() != param1AttributeType) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("ConstraintAttribute is already a ");
          stringBuilder.append(constraintAttribute.getType().name());
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        ConstraintAttribute constraintAttribute1 = new ConstraintAttribute((String)constraintAttribute, (ConstraintAttribute.AttributeType)stringBuilder);
        this.mCustomConstraints.put(constraintAttribute, constraintAttribute1);
        constraintAttribute = constraintAttribute1;
      } 
      return constraintAttribute;
    }
    
    public double getSlope(float param1Float) {
      CurveFit curveFit = this.mCurveFit;
      if (curveFit != null) {
        double d = param1Float;
        curveFit.getSlope(d, this.mSplineSlopeCache);
        this.mCurveFit.getPos(d, this.mSplineValueCache);
      } else {
        double[] arrayOfDouble1 = this.mSplineSlopeCache;
        arrayOfDouble1[0] = 0.0D;
        arrayOfDouble1[1] = 0.0D;
      } 
      Oscillator oscillator = this.mOscillator;
      double d2 = param1Float;
      double d1 = oscillator.getValue(d2);
      d2 = this.mOscillator.getSlope(d2);
      double[] arrayOfDouble = this.mSplineSlopeCache;
      return arrayOfDouble[0] + d1 * arrayOfDouble[1] + d2 * this.mSplineValueCache[1];
    }
    
    public double getValues(float param1Float) {
      CurveFit curveFit = this.mCurveFit;
      if (curveFit != null) {
        curveFit.getPos(param1Float, this.mSplineValueCache);
      } else {
        double[] arrayOfDouble = this.mSplineValueCache;
        arrayOfDouble[0] = this.mOffset[0];
        arrayOfDouble[1] = this.mValues[0];
      } 
      return this.mSplineValueCache[0] + this.mOscillator.getValue(param1Float) * this.mSplineValueCache[1];
    }
    
    public void setPoint(int param1Int1, int param1Int2, float param1Float1, float param1Float2, float param1Float3) {
      this.mPosition[param1Int1] = param1Int2 / 100.0D;
      this.mPeriod[param1Int1] = param1Float1;
      this.mOffset[param1Int1] = param1Float2;
      this.mValues[param1Int1] = param1Float3;
    }
    
    public void setup(float param1Float) {
      this.mPathLength = param1Float;
      double[][] arrayOfDouble = new double[this.mPosition.length][2];
      float[] arrayOfFloat = this.mValues;
      this.mSplineValueCache = new double[arrayOfFloat.length + 1];
      this.mSplineSlopeCache = new double[arrayOfFloat.length + 1];
      if (this.mPosition[0] > 0.0D)
        this.mOscillator.addPoint(0.0D, this.mPeriod[0]); 
      double[] arrayOfDouble1 = this.mPosition;
      int i = arrayOfDouble1.length - 1;
      if (arrayOfDouble1[i] < 1.0D)
        this.mOscillator.addPoint(1.0D, this.mPeriod[i]); 
      i = 0;
      while (i < arrayOfDouble.length) {
        arrayOfDouble[i][0] = this.mOffset[i];
        byte b = 0;
        while (true) {
          float[] arrayOfFloat1 = this.mValues;
          if (b < arrayOfFloat1.length) {
            arrayOfDouble[b][1] = arrayOfFloat1[b];
            b++;
            continue;
          } 
          this.mOscillator.addPoint(this.mPosition[i], this.mPeriod[i]);
          i++;
        } 
      } 
      this.mOscillator.normalize();
      arrayOfDouble1 = this.mPosition;
      if (arrayOfDouble1.length > 1) {
        this.mCurveFit = CurveFit.get(0, arrayOfDouble1, arrayOfDouble);
      } else {
        this.mCurveFit = null;
      } 
    }
  }
  
  static class ElevationSet extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      if (Build.VERSION.SDK_INT >= 21)
        param1View.setElevation(get(param1Float)); 
    }
  }
  
  private static class IntDoubleSort {
    private static int partition(int[] param1ArrayOfint, float[] param1ArrayOffloat, int param1Int1, int param1Int2) {
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
    
    static void sort(int[] param1ArrayOfint, float[] param1ArrayOffloat, int param1Int1, int param1Int2) {
      int[] arrayOfInt = new int[param1ArrayOfint.length + 10];
      arrayOfInt[0] = param1Int2;
      arrayOfInt[1] = param1Int1;
      param1Int1 = 2;
      while (param1Int1 > 0) {
        int j = arrayOfInt[--param1Int1];
        param1Int2 = param1Int1 - 1;
        int i = arrayOfInt[param1Int2];
        param1Int1 = param1Int2;
        if (j < i) {
          int k = partition(param1ArrayOfint, param1ArrayOffloat, j, i);
          param1Int1 = param1Int2 + 1;
          arrayOfInt[param1Int2] = k - 1;
          param1Int2 = param1Int1 + 1;
          arrayOfInt[param1Int1] = j;
          j = param1Int2 + 1;
          arrayOfInt[param1Int2] = i;
          param1Int1 = j + 1;
          arrayOfInt[j] = k + 1;
        } 
      } 
    }
    
    private static void swap(int[] param1ArrayOfint, float[] param1ArrayOffloat, int param1Int1, int param1Int2) {
      int i = param1ArrayOfint[param1Int1];
      param1ArrayOfint[param1Int1] = param1ArrayOfint[param1Int2];
      param1ArrayOfint[param1Int2] = i;
      float f = param1ArrayOffloat[param1Int1];
      param1ArrayOffloat[param1Int1] = param1ArrayOffloat[param1Int2];
      param1ArrayOffloat[param1Int2] = f;
    }
  }
  
  private static class IntFloatFloatSort {
    private static int partition(int[] param1ArrayOfint, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2, int param1Int1, int param1Int2) {
      int j = param1ArrayOfint[param1Int2];
      int i;
      for (i = param1Int1; param1Int1 < param1Int2; i = k) {
        int k = i;
        if (param1ArrayOfint[param1Int1] <= j) {
          swap(param1ArrayOfint, param1ArrayOffloat1, param1ArrayOffloat2, i, param1Int1);
          k = i + 1;
        } 
        param1Int1++;
      } 
      swap(param1ArrayOfint, param1ArrayOffloat1, param1ArrayOffloat2, i, param1Int2);
      return i;
    }
    
    static void sort(int[] param1ArrayOfint, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2, int param1Int1, int param1Int2) {
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
          int k = partition(param1ArrayOfint, param1ArrayOffloat1, param1ArrayOffloat2, i, j);
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
    
    private static void swap(int[] param1ArrayOfint, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2, int param1Int1, int param1Int2) {
      int i = param1ArrayOfint[param1Int1];
      param1ArrayOfint[param1Int1] = param1ArrayOfint[param1Int2];
      param1ArrayOfint[param1Int2] = i;
      float f = param1ArrayOffloat1[param1Int1];
      param1ArrayOffloat1[param1Int1] = param1ArrayOffloat1[param1Int2];
      param1ArrayOffloat1[param1Int2] = f;
      f = param1ArrayOffloat2[param1Int1];
      param1ArrayOffloat2[param1Int1] = param1ArrayOffloat2[param1Int2];
      param1ArrayOffloat2[param1Int2] = f;
    }
  }
  
  static class PathRotateSet extends KeyCycleOscillator {
    public void setPathRotate(View param1View, float param1Float, double param1Double1, double param1Double2) {
      param1View.setRotation(get(param1Float) + (float)Math.toDegrees(Math.atan2(param1Double2, param1Double1)));
    }
    
    public void setProperty(View param1View, float param1Float) {}
  }
  
  static class ProgressSet extends KeyCycleOscillator {
    boolean mNoMethod = false;
    
    public void setProperty(View param1View, float param1Float) {
      if (param1View instanceof MotionLayout) {
        ((MotionLayout)param1View).setProgress(get(param1Float));
      } else {
        if (this.mNoMethod)
          return; 
        Method method = null;
        try {
          Method method1 = param1View.getClass().getMethod("setProgress", new Class[] { float.class });
          method = method1;
        } catch (NoSuchMethodException noSuchMethodException) {
          this.mNoMethod = true;
        } 
        if (method != null)
          try {
            method.invoke(param1View, new Object[] { Float.valueOf(get(param1Float)) });
          } catch (IllegalAccessException illegalAccessException) {
            Log.e("KeyCycleOscillator", "unable to setProgress", illegalAccessException);
          } catch (InvocationTargetException invocationTargetException) {
            Log.e("KeyCycleOscillator", "unable to setProgress", invocationTargetException);
          }  
      } 
    }
  }
  
  static class RotationSet extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setRotation(get(param1Float));
    }
  }
  
  static class RotationXset extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setRotationX(get(param1Float));
    }
  }
  
  static class RotationYset extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setRotationY(get(param1Float));
    }
  }
  
  static class ScaleXset extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setScaleX(get(param1Float));
    }
  }
  
  static class ScaleYset extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setScaleY(get(param1Float));
    }
  }
  
  static class TranslationXset extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setTranslationX(get(param1Float));
    }
  }
  
  static class TranslationYset extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      param1View.setTranslationY(get(param1Float));
    }
  }
  
  static class TranslationZset extends KeyCycleOscillator {
    public void setProperty(View param1View, float param1Float) {
      if (Build.VERSION.SDK_INT >= 21)
        param1View.setTranslationZ(get(param1Float)); 
    }
  }
  
  static class WavePoint {
    float mOffset;
    
    float mPeriod;
    
    int mPosition;
    
    float mValue;
    
    public WavePoint(int param1Int, float param1Float1, float param1Float2, float param1Float3) {
      this.mPosition = param1Int;
      this.mValue = param1Float3;
      this.mOffset = param1Float2;
      this.mPeriod = param1Float1;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\KeyCycleOscillator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */