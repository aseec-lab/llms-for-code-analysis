package androidx.core.graphics;

import android.graphics.Color;
import java.util.Objects;

public final class ColorUtils {
  private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
  
  private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
  
  private static final ThreadLocal<double[]> TEMP_ARRAY = (ThreadLocal)new ThreadLocal<double>();
  
  private static final double XYZ_EPSILON = 0.008856D;
  
  private static final double XYZ_KAPPA = 903.3D;
  
  private static final double XYZ_WHITE_REFERENCE_X = 95.047D;
  
  private static final double XYZ_WHITE_REFERENCE_Y = 100.0D;
  
  private static final double XYZ_WHITE_REFERENCE_Z = 108.883D;
  
  public static int HSLToColor(float[] paramArrayOffloat) {
    float f1 = paramArrayOffloat[0];
    float f2 = paramArrayOffloat[1];
    float f3 = paramArrayOffloat[2];
    f2 = (1.0F - Math.abs(f3 * 2.0F - 1.0F)) * f2;
    f3 -= 0.5F * f2;
    float f4 = (1.0F - Math.abs(f1 / 60.0F % 2.0F - 1.0F)) * f2;
    switch ((int)f1 / 60) {
      default:
        k = 0;
        i = 0;
        j = 0;
        return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
      case 5:
      case 6:
        i = Math.round((f2 + f3) * 255.0F);
        j = Math.round(f3 * 255.0F);
        k = Math.round((f4 + f3) * 255.0F);
        return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
      case 4:
        i = Math.round((f4 + f3) * 255.0F);
        j = Math.round(f3 * 255.0F);
        k = Math.round((f2 + f3) * 255.0F);
        return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
      case 3:
        i = Math.round(f3 * 255.0F);
        j = Math.round((f4 + f3) * 255.0F);
        k = Math.round((f2 + f3) * 255.0F);
        return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
      case 2:
        i = Math.round(f3 * 255.0F);
        j = Math.round((f2 + f3) * 255.0F);
        k = Math.round((f4 + f3) * 255.0F);
        return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
      case 1:
        i = Math.round((f4 + f3) * 255.0F);
        j = Math.round((f2 + f3) * 255.0F);
        k = Math.round(f3 * 255.0F);
        return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
      case 0:
        break;
    } 
    int i = Math.round((f2 + f3) * 255.0F);
    int j = Math.round((f4 + f3) * 255.0F);
    int k = Math.round(f3 * 255.0F);
    return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
  }
  
  public static int LABToColor(double paramDouble1, double paramDouble2, double paramDouble3) {
    double[] arrayOfDouble = getTempDouble3Array();
    LABToXYZ(paramDouble1, paramDouble2, paramDouble3, arrayOfDouble);
    return XYZToColor(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2]);
  }
  
  public static void LABToXYZ(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfdouble) {
    double d2 = (paramDouble1 + 16.0D) / 116.0D;
    double d3 = paramDouble2 / 500.0D + d2;
    double d1 = d2 - paramDouble3 / 200.0D;
    paramDouble2 = Math.pow(d3, 3.0D);
    if (paramDouble2 <= 0.008856D)
      paramDouble2 = (d3 * 116.0D - 16.0D) / 903.3D; 
    if (paramDouble1 > 7.9996247999999985D) {
      paramDouble1 = Math.pow(d2, 3.0D);
    } else {
      paramDouble1 /= 903.3D;
    } 
    paramDouble3 = Math.pow(d1, 3.0D);
    if (paramDouble3 <= 0.008856D)
      paramDouble3 = (d1 * 116.0D - 16.0D) / 903.3D; 
    paramArrayOfdouble[0] = paramDouble2 * 95.047D;
    paramArrayOfdouble[1] = paramDouble1 * 100.0D;
    paramArrayOfdouble[2] = paramDouble3 * 108.883D;
  }
  
  public static void RGBToHSL(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat) {
    float f6 = paramInt1 / 255.0F;
    float f1 = paramInt2 / 255.0F;
    float f3 = paramInt3 / 255.0F;
    float f5 = Math.max(f6, Math.max(f1, f3));
    float f7 = Math.min(f6, Math.min(f1, f3));
    float f2 = f5 - f7;
    float f4 = (f5 + f7) / 2.0F;
    if (f5 == f7) {
      f2 = 0.0F;
      f1 = 0.0F;
    } else {
      if (f5 == f6) {
        f1 = (f1 - f3) / f2 % 6.0F;
      } else if (f5 == f1) {
        f1 = (f3 - f6) / f2 + 2.0F;
      } else {
        f1 = 4.0F + (f6 - f1) / f2;
      } 
      f3 = f2 / (1.0F - Math.abs(2.0F * f4 - 1.0F));
      f2 = f1;
      f1 = f3;
    } 
    f3 = f2 * 60.0F % 360.0F;
    f2 = f3;
    if (f3 < 0.0F)
      f2 = f3 + 360.0F; 
    paramArrayOffloat[0] = constrain(f2, 0.0F, 360.0F);
    paramArrayOffloat[1] = constrain(f1, 0.0F, 1.0F);
    paramArrayOffloat[2] = constrain(f4, 0.0F, 1.0F);
  }
  
  public static void RGBToLAB(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble) {
    RGBToXYZ(paramInt1, paramInt2, paramInt3, paramArrayOfdouble);
    XYZToLAB(paramArrayOfdouble[0], paramArrayOfdouble[1], paramArrayOfdouble[2], paramArrayOfdouble);
  }
  
  public static void RGBToXYZ(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble) {
    if (paramArrayOfdouble.length == 3) {
      double d1 = paramInt1 / 255.0D;
      if (d1 < 0.04045D) {
        d1 /= 12.92D;
      } else {
        d1 = Math.pow((d1 + 0.055D) / 1.055D, 2.4D);
      } 
      double d2 = paramInt2 / 255.0D;
      if (d2 < 0.04045D) {
        d2 /= 12.92D;
      } else {
        d2 = Math.pow((d2 + 0.055D) / 1.055D, 2.4D);
      } 
      double d3 = paramInt3 / 255.0D;
      if (d3 < 0.04045D) {
        d3 /= 12.92D;
      } else {
        d3 = Math.pow((d3 + 0.055D) / 1.055D, 2.4D);
      } 
      paramArrayOfdouble[0] = (0.4124D * d1 + 0.3576D * d2 + 0.1805D * d3) * 100.0D;
      paramArrayOfdouble[1] = (0.2126D * d1 + 0.7152D * d2 + 0.0722D * d3) * 100.0D;
      paramArrayOfdouble[2] = (d1 * 0.0193D + d2 * 0.1192D + d3 * 0.9505D) * 100.0D;
      return;
    } 
    throw new IllegalArgumentException("outXyz must have a length of 3.");
  }
  
  public static int XYZToColor(double paramDouble1, double paramDouble2, double paramDouble3) {
    double d2 = (3.2406D * paramDouble1 + -1.5372D * paramDouble2 + -0.4986D * paramDouble3) / 100.0D;
    double d1 = (-0.9689D * paramDouble1 + 1.8758D * paramDouble2 + 0.0415D * paramDouble3) / 100.0D;
    paramDouble3 = (0.0557D * paramDouble1 + -0.204D * paramDouble2 + 1.057D * paramDouble3) / 100.0D;
    if (d2 > 0.0031308D) {
      paramDouble1 = Math.pow(d2, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble1 = d2 * 12.92D;
    } 
    if (d1 > 0.0031308D) {
      paramDouble2 = Math.pow(d1, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble2 = d1 * 12.92D;
    } 
    if (paramDouble3 > 0.0031308D) {
      paramDouble3 = Math.pow(paramDouble3, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble3 *= 12.92D;
    } 
    return Color.rgb(constrain((int)Math.round(paramDouble1 * 255.0D), 0, 255), constrain((int)Math.round(paramDouble2 * 255.0D), 0, 255), constrain((int)Math.round(paramDouble3 * 255.0D), 0, 255));
  }
  
  public static void XYZToLAB(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfdouble) {
    if (paramArrayOfdouble.length == 3) {
      paramDouble1 = pivotXyzComponent(paramDouble1 / 95.047D);
      paramDouble2 = pivotXyzComponent(paramDouble2 / 100.0D);
      paramDouble3 = pivotXyzComponent(paramDouble3 / 108.883D);
      paramArrayOfdouble[0] = Math.max(0.0D, 116.0D * paramDouble2 - 16.0D);
      paramArrayOfdouble[1] = (paramDouble1 - paramDouble2) * 500.0D;
      paramArrayOfdouble[2] = (paramDouble2 - paramDouble3) * 200.0D;
      return;
    } 
    throw new IllegalArgumentException("outLab must have a length of 3.");
  }
  
  public static int blendARGB(int paramInt1, int paramInt2, float paramFloat) {
    float f4 = 1.0F - paramFloat;
    float f5 = Color.alpha(paramInt1);
    float f6 = Color.alpha(paramInt2);
    float f2 = Color.red(paramInt1);
    float f9 = Color.red(paramInt2);
    float f3 = Color.green(paramInt1);
    float f8 = Color.green(paramInt2);
    float f7 = Color.blue(paramInt1);
    float f1 = Color.blue(paramInt2);
    return Color.argb((int)(f5 * f4 + f6 * paramFloat), (int)(f2 * f4 + f9 * paramFloat), (int)(f3 * f4 + f8 * paramFloat), (int)(f7 * f4 + f1 * paramFloat));
  }
  
  public static void blendHSL(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat, float[] paramArrayOffloat3) {
    if (paramArrayOffloat3.length == 3) {
      float f = 1.0F - paramFloat;
      paramArrayOffloat3[0] = circularInterpolate(paramArrayOffloat1[0], paramArrayOffloat2[0], paramFloat);
      paramArrayOffloat3[1] = paramArrayOffloat1[1] * f + paramArrayOffloat2[1] * paramFloat;
      paramArrayOffloat3[2] = paramArrayOffloat1[2] * f + paramArrayOffloat2[2] * paramFloat;
      return;
    } 
    throw new IllegalArgumentException("result must have a length of 3.");
  }
  
  public static void blendLAB(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double paramDouble, double[] paramArrayOfdouble3) {
    if (paramArrayOfdouble3.length == 3) {
      double d = 1.0D - paramDouble;
      paramArrayOfdouble3[0] = paramArrayOfdouble1[0] * d + paramArrayOfdouble2[0] * paramDouble;
      paramArrayOfdouble3[1] = paramArrayOfdouble1[1] * d + paramArrayOfdouble2[1] * paramDouble;
      paramArrayOfdouble3[2] = paramArrayOfdouble1[2] * d + paramArrayOfdouble2[2] * paramDouble;
      return;
    } 
    throw new IllegalArgumentException("outResult must have a length of 3.");
  }
  
  public static double calculateContrast(int paramInt1, int paramInt2) {
    if (Color.alpha(paramInt2) == 255) {
      int i = paramInt1;
      if (Color.alpha(paramInt1) < 255)
        i = compositeColors(paramInt1, paramInt2); 
      double d1 = calculateLuminance(i) + 0.05D;
      double d2 = calculateLuminance(paramInt2) + 0.05D;
      return Math.max(d1, d2) / Math.min(d1, d2);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("background can not be translucent: #");
    stringBuilder.append(Integer.toHexString(paramInt2));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static double calculateLuminance(int paramInt) {
    double[] arrayOfDouble = getTempDouble3Array();
    colorToXYZ(paramInt, arrayOfDouble);
    return arrayOfDouble[1] / 100.0D;
  }
  
  public static int calculateMinimumAlpha(int paramInt1, int paramInt2, float paramFloat) {
    int i = Color.alpha(paramInt2);
    int j = 255;
    if (i == 255) {
      double d2 = calculateContrast(setAlphaComponent(paramInt1, 255), paramInt2);
      double d1 = paramFloat;
      if (d2 < d1)
        return -1; 
      i = 0;
      int k = 0;
      while (i <= 10 && j - k > 1) {
        int m = (k + j) / 2;
        if (calculateContrast(setAlphaComponent(paramInt1, m), paramInt2) < d1) {
          k = m;
        } else {
          j = m;
        } 
        i++;
      } 
      return j;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("background can not be translucent: #");
    stringBuilder.append(Integer.toHexString(paramInt2));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  static float circularInterpolate(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f1 = paramFloat1;
    float f2 = paramFloat2;
    if (Math.abs(paramFloat2 - paramFloat1) > 180.0F)
      if (paramFloat2 > paramFloat1) {
        f1 = paramFloat1 + 360.0F;
        f2 = paramFloat2;
      } else {
        f2 = paramFloat2 + 360.0F;
        f1 = paramFloat1;
      }  
    return (f1 + (f2 - f1) * paramFloat3) % 360.0F;
  }
  
  public static void colorToHSL(int paramInt, float[] paramArrayOffloat) {
    RGBToHSL(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOffloat);
  }
  
  public static void colorToLAB(int paramInt, double[] paramArrayOfdouble) {
    RGBToLAB(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfdouble);
  }
  
  public static void colorToXYZ(int paramInt, double[] paramArrayOfdouble) {
    RGBToXYZ(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfdouble);
  }
  
  private static int compositeAlpha(int paramInt1, int paramInt2) {
    return 255 - (255 - paramInt2) * (255 - paramInt1) / 255;
  }
  
  public static int compositeColors(int paramInt1, int paramInt2) {
    int k = Color.alpha(paramInt2);
    int j = Color.alpha(paramInt1);
    int i = compositeAlpha(j, k);
    return Color.argb(i, compositeComponent(Color.red(paramInt1), j, Color.red(paramInt2), k, i), compositeComponent(Color.green(paramInt1), j, Color.green(paramInt2), k, i), compositeComponent(Color.blue(paramInt1), j, Color.blue(paramInt2), k, i));
  }
  
  public static Color compositeColors(Color paramColor1, Color paramColor2) {
    if (Objects.equals(paramColor1.getModel(), paramColor2.getModel())) {
      if (!Objects.equals(paramColor2.getColorSpace(), paramColor1.getColorSpace()))
        paramColor1 = paramColor1.convert(paramColor2.getColorSpace()); 
      float[] arrayOfFloat1 = paramColor1.getComponents();
      float[] arrayOfFloat2 = paramColor2.getComponents();
      float f4 = paramColor1.alpha();
      float f3 = paramColor2.alpha() * (1.0F - f4);
      int i = paramColor2.getComponentCount() - 1;
      arrayOfFloat2[i] = f4 + f3;
      float f2 = f3;
      float f1 = f4;
      if (arrayOfFloat2[i] > 0.0F) {
        f1 = f4 / arrayOfFloat2[i];
        f2 = f3 / arrayOfFloat2[i];
      } 
      for (byte b = 0; b < i; b++)
        arrayOfFloat2[b] = arrayOfFloat1[b] * f1 + arrayOfFloat2[b] * f2; 
      return Color.valueOf(arrayOfFloat2, paramColor2.getColorSpace());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Color models must match (");
    stringBuilder.append(paramColor1.getModel());
    stringBuilder.append(" vs. ");
    stringBuilder.append(paramColor2.getModel());
    stringBuilder.append(")");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private static int compositeComponent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    return (paramInt5 == 0) ? 0 : ((paramInt1 * 255 * paramInt2 + paramInt3 * paramInt4 * (255 - paramInt2)) / paramInt5 * 255);
  }
  
  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramFloat1 >= paramFloat2) {
      paramFloat2 = paramFloat1;
      if (paramFloat1 > paramFloat3)
        paramFloat2 = paramFloat3; 
    } 
    return paramFloat2;
  }
  
  private static int constrain(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 >= paramInt2) {
      paramInt2 = paramInt1;
      if (paramInt1 > paramInt3)
        paramInt2 = paramInt3; 
    } 
    return paramInt2;
  }
  
  public static double distanceEuclidean(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
    return Math.sqrt(Math.pow(paramArrayOfdouble1[0] - paramArrayOfdouble2[0], 2.0D) + Math.pow(paramArrayOfdouble1[1] - paramArrayOfdouble2[1], 2.0D) + Math.pow(paramArrayOfdouble1[2] - paramArrayOfdouble2[2], 2.0D));
  }
  
  private static double[] getTempDouble3Array() {
    double[] arrayOfDouble2 = TEMP_ARRAY.get();
    double[] arrayOfDouble1 = arrayOfDouble2;
    if (arrayOfDouble2 == null) {
      arrayOfDouble1 = new double[3];
      TEMP_ARRAY.set(arrayOfDouble1);
    } 
    return arrayOfDouble1;
  }
  
  private static double pivotXyzComponent(double paramDouble) {
    if (paramDouble > 0.008856D) {
      paramDouble = Math.pow(paramDouble, 0.3333333333333333D);
    } else {
      paramDouble = (paramDouble * 903.3D + 16.0D) / 116.0D;
    } 
    return paramDouble;
  }
  
  public static int setAlphaComponent(int paramInt1, int paramInt2) {
    if (paramInt2 >= 0 && paramInt2 <= 255)
      return paramInt1 & 0xFFFFFF | paramInt2 << 24; 
    throw new IllegalArgumentException("alpha must be between 0 and 255.");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\ColorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */