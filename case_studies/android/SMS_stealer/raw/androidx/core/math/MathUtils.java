package androidx.core.math;

public class MathUtils {
  public static double clamp(double paramDouble1, double paramDouble2, double paramDouble3) {
    return (paramDouble1 < paramDouble2) ? paramDouble2 : ((paramDouble1 > paramDouble3) ? paramDouble3 : paramDouble1);
  }
  
  public static float clamp(float paramFloat1, float paramFloat2, float paramFloat3) {
    return (paramFloat1 < paramFloat2) ? paramFloat2 : ((paramFloat1 > paramFloat3) ? paramFloat3 : paramFloat1);
  }
  
  public static int clamp(int paramInt1, int paramInt2, int paramInt3) {
    return (paramInt1 < paramInt2) ? paramInt2 : ((paramInt1 > paramInt3) ? paramInt3 : paramInt1);
  }
  
  public static long clamp(long paramLong1, long paramLong2, long paramLong3) {
    return (paramLong1 < paramLong2) ? paramLong2 : ((paramLong1 > paramLong3) ? paramLong3 : paramLong1);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\math\MathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */