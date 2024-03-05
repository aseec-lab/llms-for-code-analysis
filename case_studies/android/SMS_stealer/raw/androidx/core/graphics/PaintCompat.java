package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.os.Build;
import androidx.core.util.Pair;

public final class PaintCompat {
  private static final String EM_STRING = "m";
  
  private static final String TOFU_STRING = "󟿽";
  
  private static final ThreadLocal<Pair<Rect, Rect>> sRectThreadLocal = new ThreadLocal<Pair<Rect, Rect>>();
  
  public static boolean hasGlyph(Paint paramPaint, String paramString) {
    if (Build.VERSION.SDK_INT >= 23)
      return paramPaint.hasGlyph(paramString); 
    int i = paramString.length();
    if (i == 1 && Character.isWhitespace(paramString.charAt(0)))
      return true; 
    float f2 = paramPaint.measureText("󟿽");
    float f4 = paramPaint.measureText("m");
    float f3 = paramPaint.measureText(paramString);
    float f1 = 0.0F;
    if (f3 == 0.0F)
      return false; 
    if (paramString.codePointCount(0, paramString.length()) > 1) {
      if (f3 > f4 * 2.0F)
        return false; 
      int j;
      for (j = 0; j < i; j = k) {
        int k = Character.charCount(paramString.codePointAt(j)) + j;
        f1 += paramPaint.measureText(paramString, j, k);
      } 
      if (f3 >= f1)
        return false; 
    } 
    if (f3 != f2)
      return true; 
    Pair<Rect, Rect> pair = obtainEmptyRects();
    paramPaint.getTextBounds("󟿽", 0, 2, (Rect)pair.first);
    paramPaint.getTextBounds(paramString, 0, i, (Rect)pair.second);
    return ((Rect)pair.first).equals(pair.second) ^ true;
  }
  
  private static Pair<Rect, Rect> obtainEmptyRects() {
    Pair<Rect, Rect> pair = sRectThreadLocal.get();
    if (pair == null) {
      pair = new Pair(new Rect(), new Rect());
      sRectThreadLocal.set(pair);
    } else {
      ((Rect)pair.first).setEmpty();
      ((Rect)pair.second).setEmpty();
    } 
    return pair;
  }
  
  public static boolean setBlendMode(Paint paramPaint, BlendModeCompat paramBlendModeCompat) {
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    BlendMode blendMode1 = null;
    BlendMode blendMode2 = null;
    if (i >= 29) {
      blendMode1 = blendMode2;
      if (paramBlendModeCompat != null)
        blendMode1 = BlendModeUtils.obtainBlendModeFromCompat(paramBlendModeCompat); 
      paramPaint.setBlendMode(blendMode1);
      return true;
    } 
    if (paramBlendModeCompat != null) {
      PorterDuffXfermode porterDuffXfermode;
      PorterDuff.Mode mode = BlendModeUtils.obtainPorterDuffFromCompat(paramBlendModeCompat);
      BlendMode blendMode = blendMode1;
      if (mode != null)
        porterDuffXfermode = new PorterDuffXfermode(mode); 
      paramPaint.setXfermode((Xfermode)porterDuffXfermode);
      if (mode == null)
        bool = false; 
      return bool;
    } 
    paramPaint.setXfermode(null);
    return true;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\PaintCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */