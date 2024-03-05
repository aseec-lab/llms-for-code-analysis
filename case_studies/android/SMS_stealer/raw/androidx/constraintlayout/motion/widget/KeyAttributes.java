package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;

public class KeyAttributes extends Key {
  public static final int KEY_TYPE = 1;
  
  static final String NAME = "KeyAttribute";
  
  private static final String TAG = "KeyAttribute";
  
  private float mAlpha = Float.NaN;
  
  private int mCurveFit = -1;
  
  private float mElevation = Float.NaN;
  
  private float mPivotX = Float.NaN;
  
  private float mPivotY = Float.NaN;
  
  private float mProgress = Float.NaN;
  
  private float mRotation = Float.NaN;
  
  private float mRotationX = Float.NaN;
  
  private float mRotationY = Float.NaN;
  
  private float mScaleX = Float.NaN;
  
  private float mScaleY = Float.NaN;
  
  private String mTransitionEasing;
  
  private float mTransitionPathRotate = Float.NaN;
  
  private float mTranslationX = Float.NaN;
  
  private float mTranslationY = Float.NaN;
  
  private float mTranslationZ = Float.NaN;
  
  private boolean mVisibility = false;
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {
    for (String str : paramHashMap.keySet()) {
      StringBuilder stringBuilder;
      ConstraintAttribute constraintAttribute;
      SplineSet splineSet = paramHashMap.get(str);
      boolean bool = str.startsWith("CUSTOM");
      byte b = 7;
      if (bool) {
        str = str.substring(7);
        constraintAttribute = this.mCustomConstraints.get(str);
        if (constraintAttribute != null)
          ((SplineSet.CustomSet)splineSet).setPoint(this.mFramePosition, constraintAttribute); 
        continue;
      } 
      switch (constraintAttribute.hashCode()) {
        default:
          b = -1;
          break;
        case 92909918:
          if (constraintAttribute.equals("alpha")) {
            b = 0;
            break;
          } 
        case 37232917:
          if (constraintAttribute.equals("transitionPathRotate"))
            break; 
        case -4379043:
          if (constraintAttribute.equals("elevation")) {
            b = 1;
            break;
          } 
        case -40300674:
          if (constraintAttribute.equals("rotation")) {
            b = 2;
            break;
          } 
        case -760884509:
          if (constraintAttribute.equals("transformPivotY")) {
            b = 6;
            break;
          } 
        case -760884510:
          if (constraintAttribute.equals("transformPivotX")) {
            b = 5;
            break;
          } 
        case -908189617:
          if (constraintAttribute.equals("scaleY")) {
            b = 9;
            break;
          } 
        case -908189618:
          if (constraintAttribute.equals("scaleX")) {
            b = 8;
            break;
          } 
        case -1001078227:
          if (constraintAttribute.equals("progress")) {
            b = 13;
            break;
          } 
        case -1225497655:
          if (constraintAttribute.equals("translationZ")) {
            b = 12;
            break;
          } 
        case -1225497656:
          if (constraintAttribute.equals("translationY")) {
            b = 11;
            break;
          } 
        case -1225497657:
          if (constraintAttribute.equals("translationX")) {
            b = 10;
            break;
          } 
        case -1249320805:
          if (constraintAttribute.equals("rotationY")) {
            b = 4;
            break;
          } 
        case -1249320806:
          if (constraintAttribute.equals("rotationX")) {
            b = 3;
            break;
          } 
      } 
      switch (b) {
        default:
          stringBuilder = new StringBuilder();
          stringBuilder.append("UNKNOWN addValues \"");
          stringBuilder.append((String)constraintAttribute);
          stringBuilder.append("\"");
          Log.v("KeyAttributes", stringBuilder.toString());
          continue;
        case 13:
          if (!Float.isNaN(this.mProgress))
            stringBuilder.setPoint(this.mFramePosition, this.mProgress); 
          continue;
        case 12:
          if (!Float.isNaN(this.mTranslationZ))
            stringBuilder.setPoint(this.mFramePosition, this.mTranslationZ); 
          continue;
        case 11:
          if (!Float.isNaN(this.mTranslationY))
            stringBuilder.setPoint(this.mFramePosition, this.mTranslationY); 
          continue;
        case 10:
          if (!Float.isNaN(this.mTranslationX))
            stringBuilder.setPoint(this.mFramePosition, this.mTranslationX); 
          continue;
        case 9:
          if (!Float.isNaN(this.mScaleY))
            stringBuilder.setPoint(this.mFramePosition, this.mScaleY); 
          continue;
        case 8:
          if (!Float.isNaN(this.mScaleX))
            stringBuilder.setPoint(this.mFramePosition, this.mScaleX); 
          continue;
        case 7:
          if (!Float.isNaN(this.mTransitionPathRotate))
            stringBuilder.setPoint(this.mFramePosition, this.mTransitionPathRotate); 
          continue;
        case 6:
          if (!Float.isNaN(this.mRotationY))
            stringBuilder.setPoint(this.mFramePosition, this.mPivotY); 
          continue;
        case 5:
          if (!Float.isNaN(this.mRotationX))
            stringBuilder.setPoint(this.mFramePosition, this.mPivotX); 
          continue;
        case 4:
          if (!Float.isNaN(this.mRotationY))
            stringBuilder.setPoint(this.mFramePosition, this.mRotationY); 
          continue;
        case 3:
          if (!Float.isNaN(this.mRotationX))
            stringBuilder.setPoint(this.mFramePosition, this.mRotationX); 
          continue;
        case 2:
          if (!Float.isNaN(this.mRotation))
            stringBuilder.setPoint(this.mFramePosition, this.mRotation); 
          continue;
        case 1:
          if (!Float.isNaN(this.mElevation))
            stringBuilder.setPoint(this.mFramePosition, this.mElevation); 
          continue;
        case 0:
          break;
      } 
      if (!Float.isNaN(this.mAlpha))
        stringBuilder.setPoint(this.mFramePosition, this.mAlpha); 
    } 
  }
  
  public void getAttributeNames(HashSet<String> paramHashSet) {
    if (!Float.isNaN(this.mAlpha))
      paramHashSet.add("alpha"); 
    if (!Float.isNaN(this.mElevation))
      paramHashSet.add("elevation"); 
    if (!Float.isNaN(this.mRotation))
      paramHashSet.add("rotation"); 
    if (!Float.isNaN(this.mRotationX))
      paramHashSet.add("rotationX"); 
    if (!Float.isNaN(this.mRotationY))
      paramHashSet.add("rotationY"); 
    if (!Float.isNaN(this.mPivotX))
      paramHashSet.add("transformPivotX"); 
    if (!Float.isNaN(this.mPivotY))
      paramHashSet.add("transformPivotY"); 
    if (!Float.isNaN(this.mTranslationX))
      paramHashSet.add("translationX"); 
    if (!Float.isNaN(this.mTranslationY))
      paramHashSet.add("translationY"); 
    if (!Float.isNaN(this.mTranslationZ))
      paramHashSet.add("translationZ"); 
    if (!Float.isNaN(this.mTransitionPathRotate))
      paramHashSet.add("transitionPathRotate"); 
    if (!Float.isNaN(this.mScaleX))
      paramHashSet.add("scaleX"); 
    if (!Float.isNaN(this.mScaleY))
      paramHashSet.add("scaleY"); 
    if (!Float.isNaN(this.mProgress))
      paramHashSet.add("progress"); 
    if (this.mCustomConstraints.size() > 0)
      for (String str : this.mCustomConstraints.keySet()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CUSTOM,");
        stringBuilder.append(str);
        paramHashSet.add(stringBuilder.toString());
      }  
  }
  
  int getCurveFit() {
    return this.mCurveFit;
  }
  
  public void load(Context paramContext, AttributeSet paramAttributeSet) {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyAttribute));
  }
  
  public void setInterpolation(HashMap<String, Integer> paramHashMap) {
    if (this.mCurveFit == -1)
      return; 
    if (!Float.isNaN(this.mAlpha))
      paramHashMap.put("alpha", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mElevation))
      paramHashMap.put("elevation", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mRotation))
      paramHashMap.put("rotation", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mRotationX))
      paramHashMap.put("rotationX", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mRotationY))
      paramHashMap.put("rotationY", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mPivotX))
      paramHashMap.put("transformPivotX", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mPivotY))
      paramHashMap.put("transformPivotY", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mTranslationX))
      paramHashMap.put("translationX", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mTranslationY))
      paramHashMap.put("translationY", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mTranslationZ))
      paramHashMap.put("translationZ", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mTransitionPathRotate))
      paramHashMap.put("transitionPathRotate", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mScaleX))
      paramHashMap.put("scaleX", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mScaleY))
      paramHashMap.put("scaleY", Integer.valueOf(this.mCurveFit)); 
    if (!Float.isNaN(this.mProgress))
      paramHashMap.put("progress", Integer.valueOf(this.mCurveFit)); 
    if (this.mCustomConstraints.size() > 0)
      for (String str : this.mCustomConstraints.keySet()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CUSTOM,");
        stringBuilder.append(str);
        paramHashMap.put(stringBuilder.toString(), Integer.valueOf(this.mCurveFit));
      }  
  }
  
  public void setValue(String paramString, Object paramObject) {
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 1941332754:
        if (paramString.equals("visibility")) {
          b = 12;
          break;
        } 
      case 1317633238:
        if (paramString.equals("mTranslationZ")) {
          b = 16;
          break;
        } 
      case 579057826:
        if (paramString.equals("curveFit")) {
          b = 1;
          break;
        } 
      case 92909918:
        if (paramString.equals("alpha")) {
          b = 0;
          break;
        } 
      case 37232917:
        if (paramString.equals("transitionPathRotate")) {
          b = 13;
          break;
        } 
      case -4379043:
        if (paramString.equals("elevation")) {
          b = 2;
          break;
        } 
      case -40300674:
        if (paramString.equals("rotation")) {
          b = 4;
          break;
        } 
      case -908189617:
        if (paramString.equals("scaleY")) {
          b = 10;
          break;
        } 
      case -908189618:
        if (paramString.equals("scaleX")) {
          b = 9;
          break;
        } 
      case -987906985:
        if (paramString.equals("pivotY")) {
          b = 8;
          break;
        } 
      case -987906986:
        if (paramString.equals("pivotX")) {
          b = 7;
          break;
        } 
      case -1225497656:
        if (paramString.equals("translationY")) {
          b = 15;
          break;
        } 
      case -1225497657:
        if (paramString.equals("translationX")) {
          b = 14;
          break;
        } 
      case -1249320805:
        if (paramString.equals("rotationY")) {
          b = 6;
          break;
        } 
      case -1249320806:
        if (paramString.equals("rotationX")) {
          b = 5;
          break;
        } 
      case -1812823328:
        if (paramString.equals("transitionEasing")) {
          b = 11;
          break;
        } 
      case -1913008125:
        if (paramString.equals("motionProgress")) {
          b = 3;
          break;
        } 
    } 
    switch (b) {
      default:
        return;
      case 16:
        this.mTranslationZ = toFloat(paramObject);
      case 15:
        this.mTranslationY = toFloat(paramObject);
      case 14:
        this.mTranslationX = toFloat(paramObject);
      case 13:
        this.mTransitionPathRotate = toFloat(paramObject);
      case 12:
        this.mVisibility = toBoolean(paramObject);
      case 11:
        this.mTransitionEasing = paramObject.toString();
      case 10:
        this.mScaleY = toFloat(paramObject);
      case 9:
        this.mScaleX = toFloat(paramObject);
      case 8:
        this.mPivotY = toFloat(paramObject);
      case 7:
        this.mPivotX = toFloat(paramObject);
      case 6:
        this.mRotationY = toFloat(paramObject);
      case 5:
        this.mRotationX = toFloat(paramObject);
      case 4:
        this.mRotation = toFloat(paramObject);
      case 3:
        this.mProgress = toFloat(paramObject);
      case 2:
        this.mElevation = toFloat(paramObject);
      case 1:
        this.mCurveFit = toInt(paramObject);
      case 0:
        break;
    } 
    this.mAlpha = toFloat(paramObject);
  }
  
  private static class Loader {
    private static final int ANDROID_ALPHA = 1;
    
    private static final int ANDROID_ELEVATION = 2;
    
    private static final int ANDROID_PIVOT_X = 19;
    
    private static final int ANDROID_PIVOT_Y = 20;
    
    private static final int ANDROID_ROTATION = 4;
    
    private static final int ANDROID_ROTATION_X = 5;
    
    private static final int ANDROID_ROTATION_Y = 6;
    
    private static final int ANDROID_SCALE_X = 7;
    
    private static final int ANDROID_SCALE_Y = 14;
    
    private static final int ANDROID_TRANSLATION_X = 15;
    
    private static final int ANDROID_TRANSLATION_Y = 16;
    
    private static final int ANDROID_TRANSLATION_Z = 17;
    
    private static final int CURVE_FIT = 13;
    
    private static final int FRAME_POSITION = 12;
    
    private static final int PROGRESS = 18;
    
    private static final int TARGET_ID = 10;
    
    private static final int TRANSITION_EASING = 9;
    
    private static final int TRANSITION_PATH_ROTATE = 8;
    
    private static SparseIntArray mAttrMap;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      mAttrMap = sparseIntArray;
      sparseIntArray.append(R.styleable.KeyAttribute_android_alpha, 1);
      mAttrMap.append(R.styleable.KeyAttribute_android_elevation, 2);
      mAttrMap.append(R.styleable.KeyAttribute_android_rotation, 4);
      mAttrMap.append(R.styleable.KeyAttribute_android_rotationX, 5);
      mAttrMap.append(R.styleable.KeyAttribute_android_rotationY, 6);
      mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotX, 19);
      mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotY, 20);
      mAttrMap.append(R.styleable.KeyAttribute_android_scaleX, 7);
      mAttrMap.append(R.styleable.KeyAttribute_transitionPathRotate, 8);
      mAttrMap.append(R.styleable.KeyAttribute_transitionEasing, 9);
      mAttrMap.append(R.styleable.KeyAttribute_motionTarget, 10);
      mAttrMap.append(R.styleable.KeyAttribute_framePosition, 12);
      mAttrMap.append(R.styleable.KeyAttribute_curveFit, 13);
      mAttrMap.append(R.styleable.KeyAttribute_android_scaleY, 14);
      mAttrMap.append(R.styleable.KeyAttribute_android_translationX, 15);
      mAttrMap.append(R.styleable.KeyAttribute_android_translationY, 16);
      mAttrMap.append(R.styleable.KeyAttribute_android_translationZ, 17);
      mAttrMap.append(R.styleable.KeyAttribute_motionProgress, 18);
    }
    
    public static void read(KeyAttributes param1KeyAttributes, TypedArray param1TypedArray) {
      int i = param1TypedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        StringBuilder stringBuilder;
        int j = param1TypedArray.getIndex(b);
        switch (mAttrMap.get(j)) {
          default:
            stringBuilder = new StringBuilder();
            stringBuilder.append("unused attribute 0x");
            stringBuilder.append(Integer.toHexString(j));
            stringBuilder.append("   ");
            stringBuilder.append(mAttrMap.get(j));
            Log.e("KeyAttribute", stringBuilder.toString());
            break;
          case 20:
            KeyAttributes.access$802(param1KeyAttributes, param1TypedArray.getDimension(j, param1KeyAttributes.mPivotY));
            break;
          case 19:
            KeyAttributes.access$702(param1KeyAttributes, param1TypedArray.getDimension(j, param1KeyAttributes.mPivotX));
            break;
          case 18:
            KeyAttributes.access$1502(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mProgress));
            break;
          case 17:
            if (Build.VERSION.SDK_INT >= 21)
              KeyAttributes.access$1402(param1KeyAttributes, param1TypedArray.getDimension(j, param1KeyAttributes.mTranslationZ)); 
            break;
          case 16:
            KeyAttributes.access$1302(param1KeyAttributes, param1TypedArray.getDimension(j, param1KeyAttributes.mTranslationY));
            break;
          case 15:
            KeyAttributes.access$1202(param1KeyAttributes, param1TypedArray.getDimension(j, param1KeyAttributes.mTranslationX));
            break;
          case 14:
            KeyAttributes.access$1002(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mScaleY));
            break;
          case 13:
            KeyAttributes.access$302(param1KeyAttributes, param1TypedArray.getInteger(j, param1KeyAttributes.mCurveFit));
            break;
          case 12:
            param1KeyAttributes.mFramePosition = param1TypedArray.getInt(j, param1KeyAttributes.mFramePosition);
            break;
          case 10:
            if (MotionLayout.IS_IN_EDIT_MODE) {
              param1KeyAttributes.mTargetId = param1TypedArray.getResourceId(j, param1KeyAttributes.mTargetId);
              if (param1KeyAttributes.mTargetId == -1)
                param1KeyAttributes.mTargetString = param1TypedArray.getString(j); 
              break;
            } 
            if ((param1TypedArray.peekValue(j)).type == 3) {
              param1KeyAttributes.mTargetString = param1TypedArray.getString(j);
              break;
            } 
            param1KeyAttributes.mTargetId = param1TypedArray.getResourceId(j, param1KeyAttributes.mTargetId);
            break;
          case 9:
            KeyAttributes.access$902(param1KeyAttributes, param1TypedArray.getString(j));
            break;
          case 8:
            KeyAttributes.access$1102(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mTransitionPathRotate));
            break;
          case 7:
            KeyAttributes.access$402(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mScaleX));
            break;
          case 6:
            KeyAttributes.access$602(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mRotationY));
            break;
          case 5:
            KeyAttributes.access$502(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mRotationX));
            break;
          case 4:
            KeyAttributes.access$202(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mRotation));
            break;
          case 2:
            KeyAttributes.access$102(param1KeyAttributes, param1TypedArray.getDimension(j, param1KeyAttributes.mElevation));
            break;
          case 1:
            KeyAttributes.access$002(param1KeyAttributes, param1TypedArray.getFloat(j, param1KeyAttributes.mAlpha));
            break;
        } 
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\KeyAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */