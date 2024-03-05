package androidx.constraintlayout.motion.utils;

public class LinearCurveFit extends CurveFit {
  private static final String TAG = "LinearCurveFit";
  
  private double[] mT;
  
  private double mTotalLength = Double.NaN;
  
  private double[][] mY;
  
  public LinearCurveFit(double[] paramArrayOfdouble, double[][] paramArrayOfdouble1) {
    int i = paramArrayOfdouble.length;
    i = (paramArrayOfdouble1[0]).length;
    this.mT = paramArrayOfdouble;
    this.mY = paramArrayOfdouble1;
    if (i > 2) {
      double d1 = 0.0D;
      double d2 = 0.0D;
      i = 0;
      while (i < paramArrayOfdouble.length) {
        double d4 = paramArrayOfdouble1[i][0];
        double d3 = paramArrayOfdouble1[i][0];
        if (i > 0)
          Math.hypot(d4 - d1, d3 - d2); 
        i++;
        d1 = d4;
        d2 = d3;
      } 
      this.mTotalLength = 0.0D;
    } 
  }
  
  private double getLength2D(double paramDouble) {
    if (Double.isNaN(this.mTotalLength))
      return 0.0D; 
    double[] arrayOfDouble = this.mT;
    int i = arrayOfDouble.length;
    if (paramDouble <= arrayOfDouble[0])
      return 0.0D; 
    int j = i - 1;
    if (paramDouble >= arrayOfDouble[j])
      return this.mTotalLength; 
    double d2 = 0.0D;
    double d1 = 0.0D;
    double d3 = d1;
    i = 0;
    while (i < j) {
      double[][] arrayOfDouble2 = this.mY;
      double d6 = arrayOfDouble2[i][0];
      double d5 = arrayOfDouble2[i][1];
      double d4 = d2;
      if (i > 0)
        d4 = d2 + Math.hypot(d6 - d1, d5 - d3); 
      double[] arrayOfDouble1 = this.mT;
      if (paramDouble == arrayOfDouble1[i])
        return d4; 
      int k = i + 1;
      if (paramDouble < arrayOfDouble1[k]) {
        d2 = arrayOfDouble1[k];
        d1 = arrayOfDouble1[i];
        d3 = (paramDouble - arrayOfDouble1[i]) / (d2 - d1);
        double[][] arrayOfDouble3 = this.mY;
        double d8 = arrayOfDouble3[i][0];
        paramDouble = arrayOfDouble3[k][0];
        d1 = arrayOfDouble3[i][1];
        d2 = arrayOfDouble3[k][1];
        double d7 = 1.0D - d3;
        return d4 + Math.hypot(d5 - d1 * d7 + d2 * d3, d6 - d8 * d7 + paramDouble * d3);
      } 
      i = k;
      d1 = d6;
      d3 = d5;
      d2 = d4;
    } 
    return 0.0D;
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
        double d1 = arrayOfDouble[j];
        double d2 = arrayOfDouble[i];
        paramDouble = (paramDouble - arrayOfDouble[i]) / (d1 - d2);
        double[][] arrayOfDouble1 = this.mY;
        return arrayOfDouble1[i][paramInt] * (1.0D - paramDouble) + arrayOfDouble1[j][paramInt] * paramDouble;
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
        double d1 = arrayOfDouble[n];
        double d2 = arrayOfDouble[i];
        paramDouble = (paramDouble - arrayOfDouble[i]) / (d1 - d2);
        for (j = bool; j < k; j++) {
          double[][] arrayOfDouble2 = this.mY;
          paramArrayOfdouble[j] = arrayOfDouble2[i][j] * (1.0D - paramDouble) + arrayOfDouble2[n][j] * paramDouble;
        } 
        return;
      } 
    } 
  }
  
  public void getPos(double paramDouble, float[] paramArrayOffloat) {
    double[] arrayOfDouble = this.mT;
    int j = arrayOfDouble.length;
    double[][] arrayOfDouble1 = this.mY;
    boolean bool = false;
    int i = 0;
    int k = (arrayOfDouble1[0]).length;
    if (paramDouble <= arrayOfDouble[0]) {
      for (i = 0; i < k; i++)
        paramArrayOffloat[i] = (float)this.mY[0][i]; 
      return;
    } 
    int m = j - 1;
    if (paramDouble >= arrayOfDouble[m]) {
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
      arrayOfDouble = this.mT;
      int n = i + 1;
      if (paramDouble < arrayOfDouble[n]) {
        double d1 = arrayOfDouble[n];
        double d2 = arrayOfDouble[i];
        paramDouble = (paramDouble - arrayOfDouble[i]) / (d1 - d2);
        for (j = bool; j < k; j++) {
          double[][] arrayOfDouble2 = this.mY;
          paramArrayOffloat[j] = (float)(arrayOfDouble2[i][j] * (1.0D - paramDouble) + arrayOfDouble2[n][j] * paramDouble);
        } 
        return;
      } 
    } 
  }
  
  public double getSlope(double paramDouble, int paramInt) {
    double d;
    int i;
    double[] arrayOfDouble = this.mT;
    int k = arrayOfDouble.length;
    int j = 0;
    if (paramDouble < arrayOfDouble[0]) {
      d = arrayOfDouble[0];
      i = j;
    } else {
      int m = k - 1;
      i = j;
      d = paramDouble;
      if (paramDouble >= arrayOfDouble[m]) {
        d = arrayOfDouble[m];
        i = j;
      } 
    } 
    while (i < k - 1) {
      arrayOfDouble = this.mT;
      j = i + 1;
      if (d <= arrayOfDouble[j]) {
        d = arrayOfDouble[j];
        paramDouble = arrayOfDouble[i];
        double d1 = arrayOfDouble[i];
        double[][] arrayOfDouble1 = this.mY;
        d1 = arrayOfDouble1[i][paramInt];
        return (arrayOfDouble1[j][paramInt] - d1) / (d - paramDouble);
      } 
      i = j;
    } 
    return 0.0D;
  }
  
  public void getSlope(double paramDouble, double[] paramArrayOfdouble) {
    double d;
    double[] arrayOfDouble1 = this.mT;
    int k = arrayOfDouble1.length;
    double[][] arrayOfDouble = this.mY;
    byte b = 0;
    int j = (arrayOfDouble[0]).length;
    if (paramDouble <= arrayOfDouble1[0]) {
      d = arrayOfDouble1[0];
    } else {
      int m = k - 1;
      d = paramDouble;
      if (paramDouble >= arrayOfDouble1[m])
        d = arrayOfDouble1[m]; 
    } 
    int i;
    for (i = 0; i < k - 1; i = m) {
      double[] arrayOfDouble2 = this.mT;
      int m = i + 1;
      if (d <= arrayOfDouble2[m]) {
        d = arrayOfDouble2[m];
        paramDouble = arrayOfDouble2[i];
        double d1 = arrayOfDouble2[i];
        while (b < j) {
          double[][] arrayOfDouble3 = this.mY;
          d1 = arrayOfDouble3[i][b];
          paramArrayOfdouble[b] = (arrayOfDouble3[m][b] - d1) / (d - paramDouble);
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


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motio\\utils\LinearCurveFit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */