package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Key {
  static final String ALPHA = "alpha";
  
  static final String CUSTOM = "CUSTOM";
  
  static final String ELEVATION = "elevation";
  
  static final String PIVOT_X = "transformPivotX";
  
  static final String PIVOT_Y = "transformPivotY";
  
  static final String PROGRESS = "progress";
  
  static final String ROTATION = "rotation";
  
  static final String ROTATION_X = "rotationX";
  
  static final String ROTATION_Y = "rotationY";
  
  static final String SCALE_X = "scaleX";
  
  static final String SCALE_Y = "scaleY";
  
  static final String TRANSITION_PATH_ROTATE = "transitionPathRotate";
  
  static final String TRANSLATION_X = "translationX";
  
  static final String TRANSLATION_Y = "translationY";
  
  static final String TRANSLATION_Z = "translationZ";
  
  public static int UNSET = -1;
  
  static final String WAVE_OFFSET = "waveOffset";
  
  static final String WAVE_PERIOD = "wavePeriod";
  
  static final String WAVE_VARIES_BY = "waveVariesBy";
  
  HashMap<String, ConstraintAttribute> mCustomConstraints;
  
  int mFramePosition;
  
  int mTargetId;
  
  String mTargetString;
  
  protected int mType;
  
  public Key() {
    int i = UNSET;
    this.mFramePosition = i;
    this.mTargetId = i;
    this.mTargetString = null;
  }
  
  public abstract void addValues(HashMap<String, SplineSet> paramHashMap);
  
  abstract void getAttributeNames(HashSet<String> paramHashSet);
  
  abstract void load(Context paramContext, AttributeSet paramAttributeSet);
  
  boolean matches(String paramString) {
    String str = this.mTargetString;
    return (str == null || paramString == null) ? false : paramString.matches(str);
  }
  
  public void setInterpolation(HashMap<String, Integer> paramHashMap) {}
  
  public abstract void setValue(String paramString, Object paramObject);
  
  boolean toBoolean(Object paramObject) {
    boolean bool;
    if (paramObject instanceof Boolean) {
      bool = ((Boolean)paramObject).booleanValue();
    } else {
      bool = Boolean.parseBoolean(paramObject.toString());
    } 
    return bool;
  }
  
  float toFloat(Object paramObject) {
    float f;
    if (paramObject instanceof Float) {
      f = ((Float)paramObject).floatValue();
    } else {
      f = Float.parseFloat(paramObject.toString());
    } 
    return f;
  }
  
  int toInt(Object paramObject) {
    int i;
    if (paramObject instanceof Integer) {
      i = ((Integer)paramObject).intValue();
    } else {
      i = Integer.parseInt(paramObject.toString());
    } 
    return i;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\Key.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */