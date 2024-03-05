package androidx.vectordrawable.graphics.drawable;

import android.animation.TypeEvaluator;

public class ArgbEvaluator implements TypeEvaluator {
  private static final ArgbEvaluator sInstance = new ArgbEvaluator();
  
  public static ArgbEvaluator getInstance() {
    return sInstance;
  }
  
  public Object evaluate(float paramFloat, Object paramObject1, Object paramObject2) {
    int i = ((Integer)paramObject1).intValue();
    float f2 = (i >> 24 & 0xFF) / 255.0F;
    float f5 = (i >> 16 & 0xFF) / 255.0F;
    float f4 = (i >> 8 & 0xFF) / 255.0F;
    float f6 = (i & 0xFF) / 255.0F;
    i = ((Integer)paramObject2).intValue();
    float f1 = (i >> 24 & 0xFF) / 255.0F;
    float f8 = (i >> 16 & 0xFF) / 255.0F;
    float f7 = (i >> 8 & 0xFF) / 255.0F;
    float f3 = (i & 0xFF) / 255.0F;
    f5 = (float)Math.pow(f5, 2.2D);
    f4 = (float)Math.pow(f4, 2.2D);
    f6 = (float)Math.pow(f6, 2.2D);
    f8 = (float)Math.pow(f8, 2.2D);
    f7 = (float)Math.pow(f7, 2.2D);
    f3 = (float)Math.pow(f3, 2.2D);
    f5 = (float)Math.pow((f5 + (f8 - f5) * paramFloat), 0.45454545454545453D);
    f4 = (float)Math.pow((f4 + (f7 - f4) * paramFloat), 0.45454545454545453D);
    f3 = (float)Math.pow((f6 + paramFloat * (f3 - f6)), 0.45454545454545453D);
    i = Math.round((f2 + (f1 - f2) * paramFloat) * 255.0F);
    return Integer.valueOf(Math.round(f5 * 255.0F) << 16 | i << 24 | Math.round(f4 * 255.0F) << 8 | Math.round(f3 * 255.0F));
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\vectordrawable\graphics\drawable\ArgbEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */