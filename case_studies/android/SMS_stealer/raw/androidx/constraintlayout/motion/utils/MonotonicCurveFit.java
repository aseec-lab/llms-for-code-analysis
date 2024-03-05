package androidx.constraintlayout.motion.utils;

public class MonotonicCurveFit extends CurveFit {
  private static final String TAG = "MonotonicCurveFit";
  
  private double[] mT;
  
  private double[][] mTangent;
  
  private double[][] mY;
  
  public MonotonicCurveFit(double[] paramArrayOfdouble, double[][] paramArrayOfdouble1) {
    int k = paramArrayOfdouble.length;
    int i = (paramArrayOfdouble1[0]).length;
    int j = k - 1;
    double[][] arrayOfDouble2 = new double[j][i];
    double[][] arrayOfDouble1 = new double[k][i];
    byte b;
    for (b = 0; b < i; b++) {
      int m;
      for (m = 0; m < j; m = n) {
        int n = m + 1;
        double d1 = paramArrayOfdouble[n];
        double d2 = paramArrayOfdouble[m];
        arrayOfDouble2[m][b] = (paramArrayOfdouble1[n][b] - paramArrayOfdouble1[m][b]) / (d1 - d2);
        if (m == 0) {
          arrayOfDouble1[m][b] = arrayOfDouble2[m][b];
        } else {
          arrayOfDouble1[m][b] = (arrayOfDouble2[m - 1][b] + arrayOfDouble2[m][b]) * 0.5D;
        } 
      } 
      arrayOfDouble1[j][b] = arrayOfDouble2[k - 2][b];
    } 
    for (b = 0; b < j; b++) {
      for (byte b1 = 0; b1 < i; b1++) {
        if (arrayOfDouble2[b][b1] == 0.0D) {
          arrayOfDouble1[b][b1] = 0.0D;
          arrayOfDouble1[b + 1][b1] = 0.0D;
        } else {
          double d2 = arrayOfDouble1[b][b1] / arrayOfDouble2[b][b1];
          int m = b + 1;
          double d1 = arrayOfDouble1[m][b1] / arrayOfDouble2[b][b1];
          double d3 = Math.hypot(d2, d1);
          if (d3 > 9.0D) {
            d3 = 3.0D / d3;
            arrayOfDouble1[b][b1] = d2 * d3 * arrayOfDouble2[b][b1];
            arrayOfDouble1[m][b1] = d3 * d1 * arrayOfDouble2[b][b1];
          } 
        } 
      } 
    } 
    this.mT = paramArrayOfdouble;
    this.mY = paramArrayOfdouble1;
    this.mTangent = arrayOfDouble1;
  }
  
  private static double diff(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    double d3 = paramDouble2 * paramDouble2;
    double d2 = paramDouble2 * 6.0D;
    double d1 = 3.0D * paramDouble1;
    return -6.0D * d3 * paramDouble4 + d2 * paramDouble4 + 6.0D * d3 * paramDouble3 - d2 * paramDouble3 + d1 * paramDouble6 * d3 + d1 * paramDouble5 * d3 - 2.0D * paramDouble1 * paramDouble6 * paramDouble2 - 4.0D * paramDouble1 * paramDouble5 * paramDouble2 + paramDouble1 * paramDouble5;
  }
  
  private static double interpolate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    double d3 = paramDouble2 * paramDouble2;
    double d1 = d3 * paramDouble2;
    double d2 = 3.0D * d3;
    paramDouble6 = paramDouble1 * paramDouble6;
    double d4 = paramDouble1 * paramDouble5;
    return -2.0D * d1 * paramDouble4 + d2 * paramDouble4 + d1 * 2.0D * paramDouble3 - d2 * paramDouble3 + paramDouble3 + paramDouble6 * d1 + d1 * d4 - paramDouble6 * d3 - paramDouble1 * 2.0D * paramDouble5 * d3 + d4 * paramDouble2;
  }
  
  public double getPos(double paramDouble, int paramInt) {
    double[] arrayOfDouble = this.mT;
    int j = arrayOfDouble.length;
    int i = 0;
    if (paramDouble <= arrayOfDouble[0])
      return this.mY[0][paramInt]; 
    int k = j - 1;
    if (paramDouble >= arrayOfDouble[k])
      return this.mY[k][paramInt]; 
    while (i < k) {
      arrayOfDouble = this.mT;
      if (paramDouble == arrayOfDouble[i])
        return this.mY[i][paramInt]; 
      j = i + 1;
      if (paramDouble < arrayOfDouble[j]) {
        double d1 = arrayOfDouble[j] - arrayOfDouble[i];
        double d2 = (paramDouble - arrayOfDouble[i]) / d1;
        double[][] arrayOfDouble1 = this.mY;
        double d3 = arrayOfDouble1[i][paramInt];
        paramDouble = arrayOfDouble1[j][paramInt];
        arrayOfDouble1 = this.mTangent;
        return interpolate(d1, d2, d3, paramDouble, arrayOfDouble1[i][paramInt], arrayOfDouble1[j][paramInt]);
      } 
      i = j;
    } 
    return 0.0D;
  }
  
  public void getPos(double paramDouble, double[] paramArrayOfdouble) {
    double[] arrayOfDouble = this.mT;
    int j = arrayOfDouble.length;
    double[][] arrayOfDouble1 = this.mY;
    boolean bool = false;
    int i = 0;
    int k = (arrayOfDouble1[0]).length;
    if (paramDouble <= arrayOfDouble[0]) {
      for (i = 0; i < k; i++)
        paramArrayOfdouble[i] = this.mY[0][i]; 
      return;
    } 
    int m = j - 1;
    if (paramDouble >= arrayOfDouble[m]) {
      while (i < k) {
        paramArrayOfdouble[i] = this.mY[m][i];
        i++;
      } 
      return;
    } 
    for (i = 0; i < m; i = n) {
      if (paramDouble == this.mT[i])
        for (j = 0; j < k; j++)
          paramArrayOfdouble[j] = this.mY[i][j];  
      arrayOfDouble = this.mT;
      int n = i + 1;
      if (paramDouble < arrayOfDouble[n]) {
        double d1 = arrayOfDouble[n] - arrayOfDouble[i];
        double d2 = (paramDouble - arrayOfDouble[i]) / d1;
        for (j = bool; j < k; j++) {
          double[][] arrayOfDouble2 = this.mY;
          double d = arrayOfDouble2[i][j];
          paramDouble = arrayOfDouble2[n][j];
          arrayOfDouble2 = this.mTangent;
          paramArrayOfdouble[j] = interpolate(d1, d2, d, paramDouble, arrayOfDouble2[i][j], arrayOfDouble2[n][j]);
        } 
        return;
      } 
    } 
  }
  
  public void getPos(double paramDouble, float[] paramArrayOffloat) {
    double[] arrayOfDouble1 = this.mT;
    int j = arrayOfDouble1.length;
    double[][] arrayOfDouble = this.mY;
    boolean bool = false;
    int i = 0;
    int k = (arrayOfDouble[0]).length;
    if (paramDouble <= arrayOfDouble1[0]) {
      for (i = 0; i < k; i++)
        paramArrayOffloat[i] = (float)this.mY[0][i]; 
      return;
    } 
    int m = j - 1;
    if (paramDouble >= arrayOfDouble1[m]) {
      while (i < k) {
        paramArrayOffloat[i] = (float)this.mY[m][i];
        i++;
      } 
      return;
    } 
    for (i = 0; i < m; i = n) {
      if (paramDouble == this.mT[i])
        for (j = 0; j < k; j++)
          paramArrayOffloat[j] = (float)this.mY[i][j];  
      double[] arrayOfDouble2 = this.mT;
      int n = i + 1;
      if (paramDouble < arrayOfDouble2[n]) {
        double d1 = arrayOfDouble2[n] - arrayOfDouble2[i];
        double d2 = (paramDouble - arrayOfDouble2[i]) / d1;
        for (j = bool; j < k; j++) {
          double[][] arrayOfDouble3 = this.mY;
          paramDouble = arrayOfDouble3[i][j];
          double d = arrayOfDouble3[n][j];
          arrayOfDouble3 = this.mTangent;
          paramArrayOffloat[j] = (float)interpolate(d1, d2, paramDouble, d, arrayOfDouble3[i][j], arrayOfDouble3[n][j]);
        } 
        return;
      } 
    } 
  }
  
  public double getSlope(double paramDouble, int paramInt) {
    double[] arrayOfDouble = this.mT;
    int j = arrayOfDouble.length;
    int i = 0;
    if (paramDouble < arrayOfDouble[0]) {
      paramDouble = arrayOfDouble[0];
    } else {
      int k = j - 1;
      if (paramDouble >= arrayOfDouble[k])
        paramDouble = arrayOfDouble[k]; 
    } 
    while (i < j - 1) {
      arrayOfDouble = this.mT;
      int k = i + 1;
      if (paramDouble <= arrayOfDouble[k]) {
        double d1 = arrayOfDouble[k] - arrayOfDouble[i];
        double d3 = (paramDouble - arrayOfDouble[i]) / d1;
        double[][] arrayOfDouble1 = this.mY;
        double d2 = arrayOfDouble1[i][paramInt];
        paramDouble = arrayOfDouble1[k][paramInt];
        arrayOfDouble1 = this.mTangent;
        return diff(d1, d3, d2, paramDouble, arrayOfDouble1[i][paramInt], arrayOfDouble1[k][paramInt]) / d1;
      } 
      i = k;
    } 
    return 0.0D;
  }
  
  public void getSlope(double paramDouble, double[] paramArrayOfdouble) {
    double[] arrayOfDouble = this.mT;
    int k = arrayOfDouble.length;
    double[][] arrayOfDouble1 = this.mY;
    byte b = 0;
    int j = (arrayOfDouble1[0]).length;
    if (paramDouble <= arrayOfDouble[0]) {
      paramDouble = arrayOfDouble[0];
    } else {
      int m = k - 1;
      if (paramDouble >= arrayOfDouble[m])
        paramDouble = arrayOfDouble[m]; 
    } 
    int i;
    for (i = 0; i < k - 1; i = m) {
      arrayOfDouble = this.mT;
      int m = i + 1;
      if (paramDouble <= arrayOfDouble[m]) {
        double d = arrayOfDouble[m] - arrayOfDouble[i];
        paramDouble = (paramDouble - arrayOfDouble[i]) / d;
        while (b < j) {
          double[][] arrayOfDouble2 = this.mY;
          double d1 = arrayOfDouble2[i][b];
          double d2 = arrayOfDouble2[m][b];
          arrayOfDouble2 = this.mTangent;
          paramArrayOfdouble[b] = diff(d, paramDouble, d1, d2, arrayOfDouble2[i][b], arrayOfDouble2[m][b]) / d;
          b++;
        } 
        break;
      } 
    } 
  }
  
  public double[] getTimePoints() {
    return this.mT;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motio\\utils\MonotonicCurveFit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */