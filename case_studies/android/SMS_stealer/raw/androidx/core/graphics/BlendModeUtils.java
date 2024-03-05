package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.PorterDuff;

class BlendModeUtils {
  static BlendMode obtainBlendModeFromCompat(BlendModeCompat paramBlendModeCompat) {
    switch (paramBlendModeCompat) {
      default:
        return null;
      case null:
        return BlendMode.LUMINOSITY;
      case null:
        return BlendMode.COLOR;
      case null:
        return BlendMode.SATURATION;
      case null:
        return BlendMode.HUE;
      case null:
        return BlendMode.MULTIPLY;
      case null:
        return BlendMode.EXCLUSION;
      case null:
        return BlendMode.DIFFERENCE;
      case null:
        return BlendMode.SOFT_LIGHT;
      case null:
        return BlendMode.HARD_LIGHT;
      case null:
        return BlendMode.COLOR_BURN;
      case null:
        return BlendMode.COLOR_DODGE;
      case null:
        return BlendMode.LIGHTEN;
      case null:
        return BlendMode.DARKEN;
      case null:
        return BlendMode.OVERLAY;
      case null:
        return BlendMode.SCREEN;
      case null:
        return BlendMode.MODULATE;
      case null:
        return BlendMode.PLUS;
      case null:
        return BlendMode.XOR;
      case null:
        return BlendMode.DST_ATOP;
      case null:
        return BlendMode.SRC_ATOP;
      case null:
        return BlendMode.DST_OUT;
      case null:
        return BlendMode.SRC_OUT;
      case null:
        return BlendMode.DST_IN;
      case null:
        return BlendMode.SRC_IN;
      case null:
        return BlendMode.DST_OVER;
      case null:
        return BlendMode.SRC_OVER;
      case null:
        return BlendMode.DST;
      case null:
        return BlendMode.SRC;
      case null:
        break;
    } 
    return BlendMode.CLEAR;
  }
  
  static PorterDuff.Mode obtainPorterDuffFromCompat(BlendModeCompat paramBlendModeCompat) {
    if (paramBlendModeCompat != null) {
      switch (paramBlendModeCompat) {
        default:
          return null;
        case null:
          return PorterDuff.Mode.LIGHTEN;
        case null:
          return PorterDuff.Mode.DARKEN;
        case null:
          return PorterDuff.Mode.OVERLAY;
        case null:
          return PorterDuff.Mode.SCREEN;
        case null:
          return PorterDuff.Mode.MULTIPLY;
        case null:
          return PorterDuff.Mode.ADD;
        case null:
          return PorterDuff.Mode.XOR;
        case null:
          return PorterDuff.Mode.DST_ATOP;
        case null:
          return PorterDuff.Mode.SRC_ATOP;
        case null:
          return PorterDuff.Mode.DST_OUT;
        case null:
          return PorterDuff.Mode.SRC_OUT;
        case null:
          return PorterDuff.Mode.DST_IN;
        case null:
          return PorterDuff.Mode.SRC_IN;
        case null:
          return PorterDuff.Mode.DST_OVER;
        case null:
          return PorterDuff.Mode.SRC_OVER;
        case null:
          return PorterDuff.Mode.DST;
        case null:
          return PorterDuff.Mode.SRC;
        case null:
          break;
      } 
      return PorterDuff.Mode.CLEAR;
    } 
    return null;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\BlendModeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */