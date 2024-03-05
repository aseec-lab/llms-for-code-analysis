package androidx.constraintlayout.motion.utils;

public class HyperSpline {
  double[][] mCtl;
  
  Cubic[][] mCurve;
  
  double[] mCurveLength;
  
  int mDimensionality;
  
  int mPoints;
  
  double mTotalLength;
  
  public HyperSpline() {}
  
  public HyperSpline(double[][] paramArrayOfdouble) {
    setup(paramArrayOfdouble);
  }
  
  static Cubic[] calcNaturalCubic(int paramInt, double[] paramArrayOfdouble) {
    double[] arrayOfDouble2 = new double[paramInt];
    double[] arrayOfDouble3 = new double[paramInt];
    double[] arrayOfDouble1 = new double[paramInt];
    int m = paramInt - 1;
    int i = 0;
    arrayOfDouble2[0] = 0.5D;
    int k = 1;
    for (paramInt = 1; paramInt < m; paramInt++)
      arrayOfDouble2[paramInt] = 1.0D / (4.0D - arrayOfDouble2[paramInt - 1]); 
    int j = m - 1;
    arrayOfDouble2[m] = 1.0D / (2.0D - arrayOfDouble2[j]);
    arrayOfDouble3[0] = (paramArrayOfdouble[1] - paramArrayOfdouble[0]) * 3.0D * arrayOfDouble2[0];
    for (paramInt = k; paramInt < m; paramInt = k) {
      k = paramInt + 1;
      double d = paramArrayOfdouble[k];
      int n = paramInt - 1;
      arrayOfDouble3[paramInt] = ((d - paramArrayOfdouble[n]) * 3.0D - arrayOfDouble3[n]) * arrayOfDouble2[paramInt];
    } 
    arrayOfDouble3[m] = ((paramArrayOfdouble[m] - paramArrayOfdouble[j]) * 3.0D - arrayOfDouble3[j]) * arrayOfDouble2[m];
    arrayOfDouble1[m] = arrayOfDouble3[m];
    for (paramInt = j; paramInt >= 0; paramInt--)
      arrayOfDouble1[paramInt] = arrayOfDouble3[paramInt] - arrayOfDouble2[paramInt] * arrayOfDouble1[paramInt + 1]; 
    Cubic[] arrayOfCubic = new Cubic[m];
    for (paramInt = i; paramInt < m; paramInt = i) {
      double d2 = (float)paramArrayOfdouble[paramInt];
      double d1 = arrayOfDouble1[paramInt];
      i = paramInt + 1;
      arrayOfCubic[paramInt] = new Cubic(d2, d1, (paramArrayOfdouble[i] - paramArrayOfdouble[paramInt]) * 3.0D - arrayOfDouble1[paramInt] * 2.0D - arrayOfDouble1[i], (paramArrayOfdouble[paramInt] - paramArrayOfdouble[i]) * 2.0D + arrayOfDouble1[paramInt] + arrayOfDouble1[i]);
    } 
    return arrayOfCubic;
  }
  
  public double approxLength(Cubic[] paramArrayOfCubic) {
    double d3;
    int i = paramArrayOfCubic.length;
    double[] arrayOfDouble = new double[paramArrayOfCubic.length];
    double d4 = 0.0D;
    double d2 = 0.0D;
    double d1 = 0.0D;
    while (true) {
      i = 0;
      boolean bool = false;
      d3 = d4;
      if (d2 < 1.0D) {
        d3 = 0.0D;
        for (i = bool; i < paramArrayOfCubic.length; i++) {
          double d6 = arrayOfDouble[i];
          double d5 = paramArrayOfCubic[i].eval(d2);
          arrayOfDouble[i] = d5;
          d5 = d6 - d5;
          d3 += d5 * d5;
        } 
        double d = d1;
        if (d2 > 0.0D)
          d = d1 + Math.sqrt(d3); 
        d2 += 0.1D;
        d1 = d;
        continue;
      } 
      break;
    } 
    while (i < paramArrayOfCubic.length) {
      d2 = arrayOfDouble[i];
      double d = paramArrayOfCubic[i].eval(1.0D);
      arrayOfDouble[i] = d;
      d2 -= d;
      d3 += d2 * d2;
      i++;
    } 
    return d1 + Math.sqrt(d3);
  }
  
  public double getPos(double paramDouble, int paramInt) {
    paramDouble *= this.mTotalLength;
    byte b = 0;
    while (true) {
      double[] arrayOfDouble = this.mCurveLength;
      if (b < arrayOfDouble.length - 1 && arrayOfDouble[b] < paramDouble) {
        paramDouble -= arrayOfDouble[b];
        b++;
        continue;
      } 
      break;
    } 
    return this.mCurve[paramInt][b].eval(paramDouble / this.mCurveLength[b]);
  }
  
  public void getPos(double paramDouble, double[] paramArrayOfdouble) {
    byte b2;
    paramDouble *= this.mTotalLength;
    byte b3 = 0;
    byte b1 = 0;
    while (true) {
      double[] arrayOfDouble = this.mCurveLength;
      b2 = b3;
      if (b1 < arrayOfDouble.length - 1) {
        b2 = b3;
        if (arrayOfDouble[b1] < paramDouble) {
          paramDouble -= arrayOfDouble[b1];
          b1++;
          continue;
        } 
      } 
      break;
    } 
    while (b2 < paramArrayOfdouble.length) {
      paramArrayOfdouble[b2] = this.mCurve[b2][b1].eval(paramDouble / this.mCurveLength[b1]);
      b2++;
    } 
  }
  
  public void getPos(double paramDouble, float[] paramArrayOffloat) {
    byte b2;
    paramDouble *= this.mTotalLength;
    byte b3 = 0;
    byte b1 = 0;
    while (true) {
      double[] arrayOfDouble = this.mCurveLength;
      b2 = b3;
      if (b1 < arrayOfDouble.length - 1) {
        b2 = b3;
        if (arrayOfDouble[b1] < paramDouble) {
          paramDouble -= arrayOfDouble[b1];
          b1++;
          continue;
        } 
      } 
      break;
    } 
    while (b2 < paramArrayOffloat.length) {
      paramArrayOffloat[b2] = (float)this.mCurve[b2][b1].eval(paramDouble / this.mCurveLength[b1]);
      b2++;
    } 
  }
  
  public void getVelocity(double paramDouble, double[] paramArrayOfdouble) {
    byte b2;
    paramDouble *= this.mTotalLength;
    byte b3 = 0;
    byte b1 = 0;
    while (true) {
      double[] arrayOfDouble = this.mCurveLength;
      b2 = b3;
      if (b1 < arrayOfDouble.length - 1) {
        b2 = b3;
        if (arrayOfDouble[b1] < paramDouble) {
          paramDouble -= arrayOfDouble[b1];
          b1++;
          continue;
        } 
      } 
      break;
    } 
    while (b2 < paramArrayOfdouble.length) {
      paramArrayOfdouble[b2] = this.mCurve[b2][b1].vel(paramDouble / this.mCurveLength[b1]);
      b2++;
    } 
  }
  
  public void setup(double[][] paramArrayOfdouble) {
    int j = (paramArrayOfdouble[0]).length;
    this.mDimensionality = j;
    int i = paramArrayOfdouble.length;
    this.mPoints = i;
    this.mCtl = new double[j][i];
    this.mCurve = new Cubic[this.mDimensionality][];
    for (i = 0; i < this.mDimensionality; i++) {
      for (j = 0; j < this.mPoints; j++)
        this.mCtl[i][j] = paramArrayOfdouble[j][i]; 
    } 
    i = 0;
    while (true) {
      j = this.mDimensionality;
      if (i < j) {
        Cubic[][] arrayOfCubic1 = this.mCurve;
        paramArrayOfdouble = this.mCtl;
        arrayOfCubic1[i] = calcNaturalCubic((paramArrayOfdouble[i]).length, paramArrayOfdouble[i]);
        i++;
        continue;
      } 
      this.mCurveLength = new double[this.mPoints - 1];
      this.mTotalLength = 0.0D;
      Cubic[] arrayOfCubic = new Cubic[j];
      for (i = 0; i < this.mCurveLength.length; i++) {
        for (j = 0; j < this.mDimensionality; j++)
          arrayOfCubic[j] = this.mCurve[j][i]; 
        double d2 = this.mTotalLength;
        double[] arrayOfDouble = this.mCurveLength;
        double d1 = approxLength(arrayOfCubic);
        arrayOfDouble[i] = d1;
        this.mTotalLength = d2 + d1;
      } 
      return;
    } 
  }
  
  public static class Cubic {
    public static final double HALF = 0.5D;
    
    public static final double THIRD = 0.3333333333333333D;
    
    double mA;
    
    double mB;
    
    double mC;
    
    double mD;
    
    public Cubic(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
      this.mA = param1Double1;
      this.mB = param1Double2;
      this.mC = param1Double3;
      this.mD = param1Double4;
    }
    
    public double eval(double param1Double) {
      return ((this.mD * param1Double + this.mC) * param1Double + this.mB) * param1Double + this.mA;
    }
    
    public double vel(double param1Double) {
      return (this.mD * 0.3333333333333333D * param1Double + this.mC * 0.5D) * param1Double + this.mB;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motio\\utils\HyperSpline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */