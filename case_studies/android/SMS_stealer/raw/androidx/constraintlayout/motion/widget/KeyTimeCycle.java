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

public class KeyTimeCycle extends Key {
  public static final int KEY_TYPE = 3;
  
  static final String NAME = "KeyTimeCycle";
  
  private static final String TAG = "KeyTimeCycle";
  
  private float mAlpha = Float.NaN;
  
  private int mCurveFit = -1;
  
  private float mElevation = Float.NaN;
  
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
  
  private float mWaveOffset = 0.0F;
  
  private float mWavePeriod = Float.NaN;
  
  private int mWaveShape = 0;
  
  public void addTimeValues(HashMap<String, TimeCycleSplineSet> paramHashMap) {
    for (String str : paramHashMap.keySet()) {
      StringBuilder stringBuilder;
      ConstraintAttribute constraintAttribute;
      TimeCycleSplineSet timeCycleSplineSet = paramHashMap.get(str);
      boolean bool = str.startsWith("CUSTOM");
      byte b = 7;
      if (bool) {
        str = str.substring(7);
        constraintAttribute = this.mCustomConstraints.get(str);
        if (constraintAttribute != null)
          ((TimeCycleSplineSet.CustomSet)timeCycleSplineSet).setPoint(this.mFramePosition, constraintAttribute, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
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
          if (constraintAttribute.equals("transitionPathRotate")) {
            b = 5;
            break;
          } 
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
        case -908189617:
          if (constraintAttribute.equals("scaleY"))
            break; 
        case -908189618:
          if (constraintAttribute.equals("scaleX")) {
            b = 6;
            break;
          } 
        case -1001078227:
          if (constraintAttribute.equals("progress")) {
            b = 11;
            break;
          } 
        case -1225497655:
          if (constraintAttribute.equals("translationZ")) {
            b = 10;
            break;
          } 
        case -1225497656:
          if (constraintAttribute.equals("translationY")) {
            b = 9;
            break;
          } 
        case -1225497657:
          if (constraintAttribute.equals("translationX")) {
            b = 8;
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
          Log.e("KeyTimeCycles", stringBuilder.toString());
          continue;
        case 11:
          if (!Float.isNaN(this.mProgress))
            stringBuilder.setPoint(this.mFramePosition, this.mProgress, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 10:
          if (!Float.isNaN(this.mTranslationZ))
            stringBuilder.setPoint(this.mFramePosition, this.mTranslationZ, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 9:
          if (!Float.isNaN(this.mTranslationY))
            stringBuilder.setPoint(this.mFramePosition, this.mTranslationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 8:
          if (!Float.isNaN(this.mTranslationX))
            stringBuilder.setPoint(this.mFramePosition, this.mTranslationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 7:
          if (!Float.isNaN(this.mScaleY))
            stringBuilder.setPoint(this.mFramePosition, this.mScaleY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 6:
          if (!Float.isNaN(this.mScaleX))
            stringBuilder.setPoint(this.mFramePosition, this.mScaleX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 5:
          if (!Float.isNaN(this.mTransitionPathRotate))
            stringBuilder.setPoint(this.mFramePosition, this.mTransitionPathRotate, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 4:
          if (!Float.isNaN(this.mRotationY))
            stringBuilder.setPoint(this.mFramePosition, this.mRotationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 3:
          if (!Float.isNaN(this.mRotationX))
            stringBuilder.setPoint(this.mFramePosition, this.mRotationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 2:
          if (!Float.isNaN(this.mRotation))
            stringBuilder.setPoint(this.mFramePosition, this.mRotation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 1:
          if (!Float.isNaN(this.mElevation))
            stringBuilder.setPoint(this.mFramePosition, this.mElevation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
          continue;
        case 0:
          break;
      } 
      if (!Float.isNaN(this.mAlpha))
        stringBuilder.setPoint(this.mFramePosition, this.mAlpha, this.mWavePeriod, this.mWaveShape, this.mWaveOffset); 
    } 
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {
    throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
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
  
  public void load(Context paramContext, AttributeSet paramAttributeSet) {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyTimeCycle));
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
    if (!Float.isNaN(this.mScaleX))
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
      case 1317633238:
        if (paramString.equals("mTranslationZ")) {
          b = 13;
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
          b = 10;
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
          b = 8;
          break;
        } 
      case -908189618:
        if (paramString.equals("scaleX")) {
          b = 7;
          break;
        } 
      case -1001078227:
        if (paramString.equals("progress")) {
          b = 3;
          break;
        } 
      case -1225497656:
        if (paramString.equals("translationY")) {
          b = 12;
          break;
        } 
      case -1225497657:
        if (paramString.equals("translationX")) {
          b = 11;
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
          b = 9;
          break;
        } 
    } 
    switch (b) {
      default:
        return;
      case 13:
        this.mTranslationZ = toFloat(paramObject);
      case 12:
        this.mTranslationY = toFloat(paramObject);
      case 11:
        this.mTranslationX = toFloat(paramObject);
      case 10:
        this.mTransitionPathRotate = toFloat(paramObject);
      case 9:
        this.mTransitionEasing = paramObject.toString();
      case 8:
        this.mScaleY = toFloat(paramObject);
      case 7:
        this.mScaleX = toFloat(paramObject);
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
    
    private static final int WAVE_OFFSET = 21;
    
    private static final int WAVE_PERIOD = 20;
    
    private static final int WAVE_SHAPE = 19;
    
    private static SparseIntArray mAttrMap;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      mAttrMap = sparseIntArray;
      sparseIntArray.append(R.styleable.KeyTimeCycle_android_alpha, 1);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_elevation, 2);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_rotation, 4);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationX, 5);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationY, 6);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleX, 7);
      mAttrMap.append(R.styleable.KeyTimeCycle_transitionPathRotate, 8);
      mAttrMap.append(R.styleable.KeyTimeCycle_transitionEasing, 9);
      mAttrMap.append(R.styleable.KeyTimeCycle_motionTarget, 10);
      mAttrMap.append(R.styleable.KeyTimeCycle_framePosition, 12);
      mAttrMap.append(R.styleable.KeyTimeCycle_curveFit, 13);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleY, 14);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_translationX, 15);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_translationY, 16);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_translationZ, 17);
      mAttrMap.append(R.styleable.KeyTimeCycle_motionProgress, 18);
      mAttrMap.append(R.styleable.KeyTimeCycle_wavePeriod, 20);
      mAttrMap.append(R.styleable.KeyTimeCycle_waveOffset, 21);
      mAttrMap.append(R.styleable.KeyTimeCycle_waveShape, 19);
    }
    
    public static void read(KeyTimeCycle param1KeyTimeCycle, TypedArray param1TypedArray) {
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
            Log.e("KeyTimeCycle", stringBuilder.toString());
            break;
          case 21:
            if ((param1TypedArray.peekValue(j)).type == 5) {
              KeyTimeCycle.access$602(param1KeyTimeCycle, param1TypedArray.getDimension(j, param1KeyTimeCycle.mWaveOffset));
              break;
            } 
            KeyTimeCycle.access$602(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mWaveOffset));
            break;
          case 20:
            KeyTimeCycle.access$502(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mWavePeriod));
            break;
          case 19:
            KeyTimeCycle.access$402(param1KeyTimeCycle, param1TypedArray.getInt(j, param1KeyTimeCycle.mWaveShape));
            break;
          case 18:
            KeyTimeCycle.access$1602(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mProgress));
            break;
          case 17:
            if (Build.VERSION.SDK_INT >= 21)
              KeyTimeCycle.access$1502(param1KeyTimeCycle, param1TypedArray.getDimension(j, param1KeyTimeCycle.mTranslationZ)); 
            break;
          case 16:
            KeyTimeCycle.access$1402(param1KeyTimeCycle, param1TypedArray.getDimension(j, param1KeyTimeCycle.mTranslationY));
            break;
          case 15:
            KeyTimeCycle.access$1302(param1KeyTimeCycle, param1TypedArray.getDimension(j, param1KeyTimeCycle.mTranslationX));
            break;
          case 14:
            KeyTimeCycle.access$1102(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mScaleY));
            break;
          case 13:
            KeyTimeCycle.access$302(param1KeyTimeCycle, param1TypedArray.getInteger(j, param1KeyTimeCycle.mCurveFit));
            break;
          case 12:
            param1KeyTimeCycle.mFramePosition = param1TypedArray.getInt(j, param1KeyTimeCycle.mFramePosition);
            break;
          case 10:
            if (MotionLayout.IS_IN_EDIT_MODE) {
              param1KeyTimeCycle.mTargetId = param1TypedArray.getResourceId(j, param1KeyTimeCycle.mTargetId);
              if (param1KeyTimeCycle.mTargetId == -1)
                param1KeyTimeCycle.mTargetString = param1TypedArray.getString(j); 
              break;
            } 
            if ((param1TypedArray.peekValue(j)).type == 3) {
              param1KeyTimeCycle.mTargetString = param1TypedArray.getString(j);
              break;
            } 
            param1KeyTimeCycle.mTargetId = param1TypedArray.getResourceId(j, param1KeyTimeCycle.mTargetId);
            break;
          case 9:
            KeyTimeCycle.access$1002(param1KeyTimeCycle, param1TypedArray.getString(j));
            break;
          case 8:
            KeyTimeCycle.access$1202(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mTransitionPathRotate));
            break;
          case 7:
            KeyTimeCycle.access$702(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mScaleX));
            break;
          case 6:
            KeyTimeCycle.access$902(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mRotationY));
            break;
          case 5:
            KeyTimeCycle.access$802(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mRotationX));
            break;
          case 4:
            KeyTimeCycle.access$202(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mRotation));
            break;
          case 2:
            KeyTimeCycle.access$102(param1KeyTimeCycle, param1TypedArray.getDimension(j, param1KeyTimeCycle.mElevation));
            break;
          case 1:
            KeyTimeCycle.access$002(param1KeyTimeCycle, param1TypedArray.getFloat(j, param1KeyTimeCycle.mAlpha));
            break;
        } 
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\KeyTimeCycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */