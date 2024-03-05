package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

class ArcCurveFit extends CurveFit {
  public static final int ARC_START_FLIP = 3;
  
  public static final int ARC_START_HORIZONTAL = 2;
  
  public static final int ARC_START_LINEAR = 0;
  
  public static final int ARC_START_VERTICAL = 1;
  
  private static final int START_HORIZONTAL = 2;
  
  private static final int START_LINEAR = 3;
  
  private static final int START_VERTICAL = 1;
  
  Arc[] mArcs;
  
  private final double[] mTime;
  
  public ArcCurveFit(int[] paramArrayOfint, double[] paramArrayOfdouble, double[][] paramArrayOfdouble1) {
    this.mTime = paramArrayOfdouble;
    this.mArcs = new Arc[paramArrayOfdouble.length - 1];
    int i = 0;
    byte b2 = 1;
    byte b1 = 1;
    while (i < this.mArcs.length) {
      int j = paramArrayOfint[i];
      if (j != 0) {
        if (j != 1) {
          if (j != 2) {
            if (j == 3) {
              if (b2 == 1) {
                b2 = 2;
              } else {
                b2 = 1;
              } 
              b1 = b2;
            } 
          } else {
            b2 = 2;
            b1 = 2;
          } 
        } else {
          b2 = 1;
          b1 = 1;
        } 
      } else {
        b1 = 3;
      } 
      Arc[] arrayOfArc = this.mArcs;
      double d = paramArrayOfdouble[i];
      j = i + 1;
      arrayOfArc[i] = new Arc(b1, d, paramArrayOfdouble[j], paramArrayOfdouble1[i][0], paramArrayOfdouble1[i][1], paramArrayOfdouble1[j][0], paramArrayOfdouble1[j][1]);
      i = j;
    } 
  }
  
  public double getPos(double paramDouble, int paramInt) {
    double d;
    byte b1;
    Arc[] arrayOfArc = this.mArcs;
    byte b2 = 0;
    if (paramDouble < (arrayOfArc[0]).mTime1) {
      d = (this.mArcs[0]).mTime1;
      b1 = b2;
    } else {
      arrayOfArc = this.mArcs;
      b1 = b2;
      d = paramDouble;
      if (paramDouble > (arrayOfArc[arrayOfArc.length - 1]).mTime2) {
        arrayOfArc = this.mArcs;
        d = (arrayOfArc[arrayOfArc.length - 1]).mTime2;
        b1 = b2;
      } 
    } 
    while (true) {
      arrayOfArc = this.mArcs;
      if (b1 < arrayOfArc.length) {
        if (d <= (arrayOfArc[b1]).mTime2) {
          if ((this.mArcs[b1]).linear)
            return (paramInt == 0) ? this.mArcs[b1].getLinearX(d) : this.mArcs[b1].getLinearY(d); 
          this.mArcs[b1].setPoint(d);
          return (paramInt == 0) ? this.mArcs[b1].getX() : this.mArcs[b1].getY();
        } 
        b1++;
        continue;
      } 
      return Double.NaN;
    } 
  }
  
  public void getPos(double paramDouble, double[] paramArrayOfdouble) {
    double d = paramDouble;
    if (paramDouble < (this.mArcs[0]).mTime1)
      d = (this.mArcs[0]).mTime1; 
    Arc[] arrayOfArc = this.mArcs;
    paramDouble = d;
    if (d > (arrayOfArc[arrayOfArc.length - 1]).mTime2) {
      arrayOfArc = this.mArcs;
      paramDouble = (arrayOfArc[arrayOfArc.length - 1]).mTime2;
    } 
    byte b = 0;
    while (true) {
      arrayOfArc = this.mArcs;
      if (b < arrayOfArc.length) {
        if (paramDouble <= (arrayOfArc[b]).mTime2) {
          if ((this.mArcs[b]).linear) {
            paramArrayOfdouble[0] = this.mArcs[b].getLinearX(paramDouble);
            paramArrayOfdouble[1] = this.mArcs[b].getLinearY(paramDouble);
            return;
          } 
          this.mArcs[b].setPoint(paramDouble);
          paramArrayOfdouble[0] = this.mArcs[b].getX();
          paramArrayOfdouble[1] = this.mArcs[b].getY();
          return;
        } 
        b++;
        continue;
      } 
      break;
    } 
  }
  
  public void getPos(double paramDouble, float[] paramArrayOffloat) {
    double d;
    if (paramDouble < (this.mArcs[0]).mTime1) {
      d = (this.mArcs[0]).mTime1;
    } else {
      Arc[] arrayOfArc = this.mArcs;
      d = paramDouble;
      if (paramDouble > (arrayOfArc[arrayOfArc.length - 1]).mTime2) {
        arrayOfArc = this.mArcs;
        d = (arrayOfArc[arrayOfArc.length - 1]).mTime2;
      } 
    } 
    byte b = 0;
    while (true) {
      Arc[] arrayOfArc = this.mArcs;
      if (b < arrayOfArc.length) {
        if (d <= (arrayOfArc[b]).mTime2) {
          if ((this.mArcs[b]).linear) {
            paramArrayOffloat[0] = (float)this.mArcs[b].getLinearX(d);
            paramArrayOffloat[1] = (float)this.mArcs[b].getLinearY(d);
            return;
          } 
          this.mArcs[b].setPoint(d);
          paramArrayOffloat[0] = (float)this.mArcs[b].getX();
          paramArrayOffloat[1] = (float)this.mArcs[b].getY();
          return;
        } 
        b++;
        continue;
      } 
      break;
    } 
  }
  
  public double getSlope(double paramDouble, int paramInt) {
    Arc[] arrayOfArc = this.mArcs;
    byte b2 = 0;
    double d = paramDouble;
    if (paramDouble < (arrayOfArc[0]).mTime1)
      d = (this.mArcs[0]).mTime1; 
    arrayOfArc = this.mArcs;
    byte b1 = b2;
    paramDouble = d;
    if (d > (arrayOfArc[arrayOfArc.length - 1]).mTime2) {
      arrayOfArc = this.mArcs;
      paramDouble = (arrayOfArc[arrayOfArc.length - 1]).mTime2;
      b1 = b2;
    } 
    while (true) {
      arrayOfArc = this.mArcs;
      if (b1 < arrayOfArc.length) {
        if (paramDouble <= (arrayOfArc[b1]).mTime2) {
          if ((this.mArcs[b1]).linear)
            return (paramInt == 0) ? this.mArcs[b1].getLinearDX(paramDouble) : this.mArcs[b1].getLinearDY(paramDouble); 
          this.mArcs[b1].setPoint(paramDouble);
          return (paramInt == 0) ? this.mArcs[b1].getDX() : this.mArcs[b1].getDY();
        } 
        b1++;
        continue;
      } 
      return Double.NaN;
    } 
  }
  
  public void getSlope(double paramDouble, double[] paramArrayOfdouble) {
    double d;
    if (paramDouble < (this.mArcs[0]).mTime1) {
      d = (this.mArcs[0]).mTime1;
    } else {
      Arc[] arrayOfArc = this.mArcs;
      d = paramDouble;
      if (paramDouble > (arrayOfArc[arrayOfArc.length - 1]).mTime2) {
        arrayOfArc = this.mArcs;
        d = (arrayOfArc[arrayOfArc.length - 1]).mTime2;
      } 
    } 
    byte b = 0;
    while (true) {
      Arc[] arrayOfArc = this.mArcs;
      if (b < arrayOfArc.length) {
        if (d <= (arrayOfArc[b]).mTime2) {
          if ((this.mArcs[b]).linear) {
            paramArrayOfdouble[0] = this.mArcs[b].getLinearDX(d);
            paramArrayOfdouble[1] = this.mArcs[b].getLinearDY(d);
            return;
          } 
          this.mArcs[b].setPoint(d);
          paramArrayOfdouble[0] = this.mArcs[b].getDX();
          paramArrayOfdouble[1] = this.mArcs[b].getDY();
          return;
        } 
        b++;
        continue;
      } 
      break;
    } 
  }
  
  public double[] getTimePoints() {
    return this.mTime;
  }
  
  private static class Arc {
    private static final double EPSILON = 0.001D;
    
    private static final String TAG = "Arc";
    
    private static double[] ourPercent = new double[91];
    
    boolean linear;
    
    double mArcDistance;
    
    double mArcVelocity;
    
    double mEllipseA;
    
    double mEllipseB;
    
    double mEllipseCenterX;
    
    double mEllipseCenterY;
    
    double[] mLut;
    
    double mOneOverDeltaTime;
    
    double mTime1;
    
    double mTime2;
    
    double mTmpCosAngle;
    
    double mTmpSinAngle;
    
    boolean mVertical;
    
    double mX1;
    
    double mX2;
    
    double mY1;
    
    double mY2;
    
    Arc(int param1Int, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
      boolean bool = false;
      this.linear = false;
      if (param1Int == 1)
        bool = true; 
      this.mVertical = bool;
      this.mTime1 = param1Double1;
      this.mTime2 = param1Double2;
      this.mOneOverDeltaTime = 1.0D / (param1Double2 - param1Double1);
      if (3 == param1Int)
        this.linear = true; 
      param1Double1 = param1Double5 - param1Double3;
      param1Double2 = param1Double6 - param1Double4;
      if (this.linear || Math.abs(param1Double1) < 0.001D || Math.abs(param1Double2) < 0.001D) {
        this.linear = true;
        this.mX1 = param1Double3;
        this.mX2 = param1Double5;
        this.mY1 = param1Double4;
        this.mY2 = param1Double6;
        param1Double3 = Math.hypot(param1Double2, param1Double1);
        this.mArcDistance = param1Double3;
        this.mArcVelocity = param1Double3 * this.mOneOverDeltaTime;
        param1Double3 = this.mTime2;
        param1Double4 = this.mTime1;
        this.mEllipseCenterX = param1Double1 / (param1Double3 - param1Double4);
        this.mEllipseCenterY = param1Double2 / (param1Double3 - param1Double4);
        return;
      } 
      this.mLut = new double[101];
      if (this.mVertical) {
        param1Int = -1;
      } else {
        param1Int = 1;
      } 
      this.mEllipseA = param1Double1 * param1Int;
      if (this.mVertical) {
        param1Int = 1;
      } else {
        param1Int = -1;
      } 
      this.mEllipseB = param1Double2 * param1Int;
      if (this.mVertical) {
        param1Double1 = param1Double5;
      } else {
        param1Double1 = param1Double3;
      } 
      this.mEllipseCenterX = param1Double1;
      if (this.mVertical) {
        param1Double1 = param1Double4;
      } else {
        param1Double1 = param1Double6;
      } 
      this.mEllipseCenterY = param1Double1;
      buildTable(param1Double3, param1Double4, param1Double5, param1Double6);
      this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
    }
    
    private void buildTable(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
      byte b = 0;
      double d1 = 0.0D;
      double d3 = 0.0D;
      double d2 = 0.0D;
      while (true) {
        double[] arrayOfDouble = ourPercent;
        if (b < arrayOfDouble.length) {
          double d5 = Math.toRadians(b * 90.0D / (arrayOfDouble.length - 1));
          double d4 = Math.sin(d5);
          d5 = Math.cos(d5);
          d4 *= param1Double3 - param1Double1;
          d5 *= param1Double2 - param1Double4;
          if (b > 0) {
            d1 += Math.hypot(d4 - d3, d5 - d2);
            ourPercent[b] = d1;
          } 
          b++;
          d2 = d5;
          d3 = d4;
          continue;
        } 
        this.mArcDistance = d1;
        b = 0;
        while (true) {
          arrayOfDouble = ourPercent;
          if (b < arrayOfDouble.length) {
            arrayOfDouble[b] = arrayOfDouble[b] / d1;
            b++;
            continue;
          } 
          b = 0;
          while (true) {
            arrayOfDouble = this.mLut;
            if (b < arrayOfDouble.length) {
              param1Double1 = b / (arrayOfDouble.length - 1);
              int i = Arrays.binarySearch(ourPercent, param1Double1);
              if (i >= 0) {
                this.mLut[b] = (i / (ourPercent.length - 1));
              } else if (i == -1) {
                this.mLut[b] = 0.0D;
              } else {
                i = -i;
                int j = i - 2;
                param1Double2 = j;
                arrayOfDouble = ourPercent;
                param1Double1 = (param1Double2 + (param1Double1 - arrayOfDouble[j]) / (arrayOfDouble[i - 1] - arrayOfDouble[j])) / (arrayOfDouble.length - 1);
                this.mLut[b] = param1Double1;
              } 
              b++;
              continue;
            } 
            break;
          } 
          return;
        } 
        break;
      } 
    }
    
    double getDX() {
      double d2 = this.mEllipseA * this.mTmpCosAngle;
      double d1 = -this.mEllipseB;
      double d3 = this.mTmpSinAngle;
      d3 = this.mArcVelocity / Math.hypot(d2, d1 * d3);
      d1 = d2;
      if (this.mVertical)
        d1 = -d2; 
      return d1 * d3;
    }
    
    double getDY() {
      double d2 = this.mEllipseA;
      double d3 = this.mTmpCosAngle;
      double d1 = -this.mEllipseB * this.mTmpSinAngle;
      d2 = this.mArcVelocity / Math.hypot(d2 * d3, d1);
      if (this.mVertical) {
        d1 = -d1 * d2;
      } else {
        d1 *= d2;
      } 
      return d1;
    }
    
    public double getLinearDX(double param1Double) {
      return this.mEllipseCenterX;
    }
    
    public double getLinearDY(double param1Double) {
      return this.mEllipseCenterY;
    }
    
    public double getLinearX(double param1Double) {
      double d1 = this.mTime1;
      double d2 = this.mOneOverDeltaTime;
      double d3 = this.mX1;
      return d3 + (param1Double - d1) * d2 * (this.mX2 - d3);
    }
    
    public double getLinearY(double param1Double) {
      double d1 = this.mTime1;
      double d3 = this.mOneOverDeltaTime;
      double d2 = this.mY1;
      return d2 + (param1Double - d1) * d3 * (this.mY2 - d2);
    }
    
    double getX() {
      return this.mEllipseCenterX + this.mEllipseA * this.mTmpSinAngle;
    }
    
    double getY() {
      return this.mEllipseCenterY + this.mEllipseB * this.mTmpCosAngle;
    }
    
    double lookup(double param1Double) {
      if (param1Double <= 0.0D)
        return 0.0D; 
      if (param1Double >= 1.0D)
        return 1.0D; 
      double[] arrayOfDouble = this.mLut;
      param1Double *= (arrayOfDouble.length - 1);
      int i = (int)param1Double;
      double d = i;
      return arrayOfDouble[i] + (param1Double - d) * (arrayOfDouble[i + 1] - arrayOfDouble[i]);
    }
    
    void setPoint(double param1Double) {
      if (this.mVertical) {
        param1Double = this.mTime2 - param1Double;
      } else {
        param1Double -= this.mTime1;
      } 
      param1Double = lookup(param1Double * this.mOneOverDeltaTime) * 1.5707963267948966D;
      this.mTmpSinAngle = Math.sin(param1Double);
      this.mTmpCosAngle = Math.cos(param1Double);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motio\\utils\ArcCurveFit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */