package androidx.core.graphics;

public enum BlendModeCompat {
  CLEAR, COLOR, COLOR_BURN, COLOR_DODGE, DARKEN, DIFFERENCE, DST, DST_ATOP, DST_IN, DST_OUT, DST_OVER, EXCLUSION, HARD_LIGHT, HUE, LIGHTEN, LUMINOSITY, MODULATE, MULTIPLY, OVERLAY, PLUS, SATURATION, SCREEN, SOFT_LIGHT, SRC, SRC_ATOP, SRC_IN, SRC_OUT, SRC_OVER, XOR;
  
  private static final BlendModeCompat[] $VALUES;
  
  static {
    DST = new BlendModeCompat("DST", 2);
    SRC_OVER = new BlendModeCompat("SRC_OVER", 3);
    DST_OVER = new BlendModeCompat("DST_OVER", 4);
    SRC_IN = new BlendModeCompat("SRC_IN", 5);
    DST_IN = new BlendModeCompat("DST_IN", 6);
    SRC_OUT = new BlendModeCompat("SRC_OUT", 7);
    DST_OUT = new BlendModeCompat("DST_OUT", 8);
    SRC_ATOP = new BlendModeCompat("SRC_ATOP", 9);
    DST_ATOP = new BlendModeCompat("DST_ATOP", 10);
    XOR = new BlendModeCompat("XOR", 11);
    PLUS = new BlendModeCompat("PLUS", 12);
    MODULATE = new BlendModeCompat("MODULATE", 13);
    SCREEN = new BlendModeCompat("SCREEN", 14);
    OVERLAY = new BlendModeCompat("OVERLAY", 15);
    DARKEN = new BlendModeCompat("DARKEN", 16);
    LIGHTEN = new BlendModeCompat("LIGHTEN", 17);
    COLOR_DODGE = new BlendModeCompat("COLOR_DODGE", 18);
    COLOR_BURN = new BlendModeCompat("COLOR_BURN", 19);
    HARD_LIGHT = new BlendModeCompat("HARD_LIGHT", 20);
    SOFT_LIGHT = new BlendModeCompat("SOFT_LIGHT", 21);
    DIFFERENCE = new BlendModeCompat("DIFFERENCE", 22);
    EXCLUSION = new BlendModeCompat("EXCLUSION", 23);
    MULTIPLY = new BlendModeCompat("MULTIPLY", 24);
    HUE = new BlendModeCompat("HUE", 25);
    SATURATION = new BlendModeCompat("SATURATION", 26);
    COLOR = new BlendModeCompat("COLOR", 27);
    BlendModeCompat blendModeCompat = new BlendModeCompat("LUMINOSITY", 28);
    LUMINOSITY = blendModeCompat;
    $VALUES = new BlendModeCompat[] { 
        CLEAR, SRC, DST, SRC_OVER, DST_OVER, SRC_IN, DST_IN, SRC_OUT, DST_OUT, SRC_ATOP, 
        DST_ATOP, XOR, PLUS, MODULATE, SCREEN, OVERLAY, DARKEN, LIGHTEN, COLOR_DODGE, COLOR_BURN, 
        HARD_LIGHT, SOFT_LIGHT, DIFFERENCE, EXCLUSION, MULTIPLY, HUE, SATURATION, COLOR, blendModeCompat };
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\BlendModeCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */