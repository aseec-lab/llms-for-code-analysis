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
import java.util.Arrays;

public abstract class SplineSet {
  private static final String TAG = "SplineSet";
  
  private int count;
  
  protected CurveFit mCurveFit;
  
  protected int[] mTimePoints = new int[10];
  
  private String mType;
  
  protected float[] mValues = new float[10];
  
  static SplineSet makeCustomSpline(String paramString, SparseArray<ConstraintAttribute> paramSparseArray) {
    return new CustomSet(paramString, paramSparseArray);
  }
  
  static SplineSet makeSpline(String paramString) {
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 156108012:
        if (paramString.equals("waveOffset")) {
          b = 10;
          break;
        } 
      case 92909918:
        if (paramString.equals("alpha")) {
          b = 0;
          break;
        } 
      case 37232917:
        if (paramString.equals("transitionPathRotate")) {
          b = 7;
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
      case -760884509:
        if (paramString.equals("transformPivotY")) {
          b = 6;
          break;
        } 
      case -760884510:
        if (paramString.equals("transformPivotX")) {
          b = 5;
          break;
        } 
      case -797520672:
        if (paramString.equals("waveVariesBy")) {
          b = 11;
          break;
        } 
      case -908189617:
        if (paramString.equals("scaleY")) {
          b = 9;
          break;
        } 
      case -908189618:
        if (paramString.equals("scaleX")) {
          b = 8;
          break;
        } 
      case -1001078227:
        if (paramString.equals("progress")) {
          b = 15;
          break;
        } 
      case -1225497655:
        if (paramString.equals("translationZ")) {
          b = 14;
          break;
        } 
      case -1225497656:
        if (paramString.equals("translationY")) {
          b = 13;
          break;
        } 
      case -1225497657:
        if (paramString.equals("translationX")) {
          b = 12;
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
      case 15:
        return new ProgressSet();
      case 14:
        return new TranslationZset();
      case 13:
        return new TranslationYset();
      case 12:
        return new TranslationXset();
      case 11:
        return new AlphaSet();
      case 10:
        return new AlphaSet();
      case 9:
        return new ScaleYset();
      case 8:
        return new ScaleXset();
      case 7:
        return new PathRotate();
      case 6:
        return new PivotYset();
      case 5:
        return new PivotXset();
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
    return (float)this.mCurveFit.getPos(paramFloat, 0);
  }
  
  public CurveFit getCurveFit() {
    return this.mCurveFit;
  }
  
  public float getSlope(float paramFloat) {
    return (float)this.mCurveFit.getSlope(paramFloat, 0);
  }
  
  public void setPoint(int paramInt, float paramFloat) {
    int[] arrayOfInt = this.mTimePoints;
    if (arrayOfInt.length < this.count + 1) {
      this.mTimePoints = Arrays.copyOf(arrayOfInt, arrayOfInt.length * 2);
      float[] arrayOfFloat = this.mValues;
      this.mValues = Arrays.copyOf(arrayOfFloat, arrayOfFloat.length * 2);
    } 
    arrayOfInt = this.mTimePoints;
    int i = this.count;
    arrayOfInt[i] = paramInt;
    this.mValues[i] = paramFloat;
    this.count = i + 1;
  }
  
  public abstract void setProperty(View paramView, float paramFloat);
  
  public void setType(String paramString) {
    this.mType = paramString;
  }
  
  public void setup(int paramInt) {
    int i = this.count;
    if (i == 0)
      return; 
    Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, i - 1);
    i = 1;
    int j;
    for (j = 1; i < this.count; j = k) {
      int[] arrayOfInt = this.mTimePoints;
      int k = j;
      if (arrayOfInt[i - 1] != arrayOfInt[i])
        k = j + 1; 
      i++;
    } 
    double[] arrayOfDouble = new double[j];
    double[][] arrayOfDouble1 = new double[j][1];
    i = 0;
    byte b = 0;
    while (i < this.count) {
      if (i > 0) {
        int[] arrayOfInt = this.mTimePoints;
        if (arrayOfInt[i] == arrayOfInt[i - 1])
          continue; 
      } 
      arrayOfDouble[b] = this.mTimePoints[i] * 0.01D;
      arrayOfDouble1[b][0] = this.mValues[i];
      b++;
      continue;
      i++;
    } 
    this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, arrayOfDouble1);
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
  
  static class AlphaSet extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setAlpha(get(param1Float));
    }
  }
  
  static class CustomSet extends SplineSet {
    String mAttributeName;
    
    SparseArray<ConstraintAttribute> mConstraintAttributeList;
    
    float[] mTempValues;
    
    public CustomSet(String param1String, SparseArray<ConstraintAttribute> param1SparseArray) {
      this.mAttributeName = param1String.split(",")[1];
      this.mConstraintAttributeList = param1SparseArray;
    }
    
    public void setPoint(int param1Int, float param1Float) {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
    }
    
    public void setPoint(int param1Int, ConstraintAttribute param1ConstraintAttribute) {
      this.mConstraintAttributeList.append(param1Int, param1ConstraintAttribute);
    }
    
    public void setProperty(View param1View, float param1Float) {
      this.mCurveFit.getPos(param1Float, this.mTempValues);
      ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).setInterpolatedValue(param1View, this.mTempValues);
    }
    
    public void setup(int param1Int) {
      int j = this.mConstraintAttributeList.size();
      int i = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).noOfInterpValues();
      double[] arrayOfDouble = new double[j];
      this.mTempValues = new float[i];
      double[][] arrayOfDouble1 = new double[j][i];
      i = 0;
      while (i < j) {
        int k = this.mConstraintAttributeList.keyAt(i);
        ConstraintAttribute constraintAttribute = (ConstraintAttribute)this.mConstraintAttributeList.valueAt(i);
        arrayOfDouble[i] = k * 0.01D;
        constraintAttribute.getValuesToInterpolate(this.mTempValues);
        k = 0;
        while (true) {
          float[] arrayOfFloat = this.mTempValues;
          if (k < arrayOfFloat.length) {
            arrayOfDouble1[i][k] = arrayOfFloat[k];
            k++;
            continue;
          } 
          i++;
        } 
      } 
      this.mCurveFit = CurveFit.get(param1Int, arrayOfDouble, arrayOfDouble1);
    }
  }
  
  static class ElevationSet extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      if (Build.VERSION.SDK_INT >= 21)
        param1View.setElevation(get(param1Float)); 
    }
  }
  
  static class PathRotate extends SplineSet {
    public void setPathRotate(View param1View, float param1Float, double param1Double1, double param1Double2) {
      param1View.setRotation(get(param1Float) + (float)Math.toDegrees(Math.atan2(param1Double2, param1Double1)));
    }
    
    public void setProperty(View param1View, float param1Float) {}
  }
  
  static class PivotXset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setPivotX(get(param1Float));
    }
  }
  
  static class PivotYset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setPivotY(get(param1Float));
    }
  }
  
  static class ProgressSet extends SplineSet {
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
            Log.e("SplineSet", "unable to setProgress", illegalAccessException);
          } catch (InvocationTargetException invocationTargetException) {
            Log.e("SplineSet", "unable to setProgress", invocationTargetException);
          }  
      } 
    }
  }
  
  static class RotationSet extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setRotation(get(param1Float));
    }
  }
  
  static class RotationXset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setRotationX(get(param1Float));
    }
  }
  
  static class RotationYset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setRotationY(get(param1Float));
    }
  }
  
  static class ScaleXset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setScaleX(get(param1Float));
    }
  }
  
  static class ScaleYset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setScaleY(get(param1Float));
    }
  }
  
  private static class Sort {
    static void doubleQuickSort(int[] param1ArrayOfint, float[] param1ArrayOffloat, int param1Int1, int param1Int2) {
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
    
    private static void swap(int[] param1ArrayOfint, float[] param1ArrayOffloat, int param1Int1, int param1Int2) {
      int i = param1ArrayOfint[param1Int1];
      param1ArrayOfint[param1Int1] = param1ArrayOfint[param1Int2];
      param1ArrayOfint[param1Int2] = i;
      float f = param1ArrayOffloat[param1Int1];
      param1ArrayOffloat[param1Int1] = param1ArrayOffloat[param1Int2];
      param1ArrayOffloat[param1Int2] = f;
    }
  }
  
  static class TranslationXset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setTranslationX(get(param1Float));
    }
  }
  
  static class TranslationYset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      param1View.setTranslationY(get(param1Float));
    }
  }
  
  static class TranslationZset extends SplineSet {
    public void setProperty(View param1View, float param1Float) {
      if (Build.VERSION.SDK_INT >= 21)
        param1View.setTranslationZ(get(param1Float)); 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\SplineSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */