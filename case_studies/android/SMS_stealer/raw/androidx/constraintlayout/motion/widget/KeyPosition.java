package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;

public class KeyPosition extends KeyPositionBase {
  static final int KEY_TYPE = 2;
  
  static final String NAME = "KeyPosition";
  
  private static final String PERCENT_X = "percentX";
  
  private static final String PERCENT_Y = "percentY";
  
  private static final String TAG = "KeyPosition";
  
  public static final int TYPE_CARTESIAN = 0;
  
  public static final int TYPE_PATH = 1;
  
  public static final int TYPE_SCREEN = 2;
  
  float mAltPercentX = Float.NaN;
  
  float mAltPercentY = Float.NaN;
  
  private float mCalculatedPositionX = Float.NaN;
  
  private float mCalculatedPositionY = Float.NaN;
  
  int mDrawPath = 0;
  
  int mPathMotionArc = UNSET;
  
  float mPercentHeight = Float.NaN;
  
  float mPercentWidth = Float.NaN;
  
  float mPercentX = Float.NaN;
  
  float mPercentY = Float.NaN;
  
  int mPositionType = 0;
  
  String mTransitionEasing = null;
  
  private void calcCartesianPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float f1;
    float f3 = paramFloat3 - paramFloat1;
    float f4 = paramFloat4 - paramFloat2;
    boolean bool = Float.isNaN(this.mPercentX);
    float f2 = 0.0F;
    if (bool) {
      paramFloat3 = 0.0F;
    } else {
      paramFloat3 = this.mPercentX;
    } 
    if (Float.isNaN(this.mAltPercentY)) {
      paramFloat4 = 0.0F;
    } else {
      paramFloat4 = this.mAltPercentY;
    } 
    if (Float.isNaN(this.mPercentY)) {
      f1 = 0.0F;
    } else {
      f1 = this.mPercentY;
    } 
    if (!Float.isNaN(this.mAltPercentX))
      f2 = this.mAltPercentX; 
    this.mCalculatedPositionX = (int)(paramFloat1 + paramFloat3 * f3 + f2 * f4);
    this.mCalculatedPositionY = (int)(paramFloat2 + f3 * paramFloat4 + f4 * f1);
  }
  
  private void calcPathPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    paramFloat3 -= paramFloat1;
    paramFloat4 -= paramFloat2;
    float f3 = -paramFloat4;
    float f1 = this.mPercentX;
    float f2 = this.mPercentY;
    this.mCalculatedPositionX = paramFloat1 + paramFloat3 * f1 + f3 * f2;
    this.mCalculatedPositionY = paramFloat2 + paramFloat4 * f1 + paramFloat3 * f2;
  }
  
  private void calcScreenPosition(int paramInt1, int paramInt2) {
    float f1 = (paramInt1 - 0);
    float f3 = this.mPercentX;
    float f2 = false;
    this.mCalculatedPositionX = f1 * f3 + f2;
    this.mCalculatedPositionY = (paramInt2 - 0) * f3 + f2;
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {}
  
  void calcPosition(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    int i = this.mPositionType;
    if (i != 1) {
      if (i != 2) {
        calcCartesianPosition(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        return;
      } 
      calcScreenPosition(paramInt1, paramInt2);
      return;
    } 
    calcPathPosition(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  float getPositionX() {
    return this.mCalculatedPositionX;
  }
  
  float getPositionY() {
    return this.mCalculatedPositionY;
  }
  
  public boolean intersects(int paramInt1, int paramInt2, RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2) {
    calcPosition(paramInt1, paramInt2, paramRectF1.centerX(), paramRectF1.centerY(), paramRectF2.centerX(), paramRectF2.centerY());
    return (Math.abs(paramFloat1 - this.mCalculatedPositionX) < 20.0F && Math.abs(paramFloat2 - this.mCalculatedPositionY) < 20.0F);
  }
  
  public void load(Context paramContext, AttributeSet paramAttributeSet) {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyPosition));
  }
  
  public void positionAttributes(View paramView, RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOffloat) {
    int i = this.mPositionType;
    if (i != 1) {
      if (i != 2) {
        positionCartAttributes(paramRectF1, paramRectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOffloat);
        return;
      } 
      positionScreenAttributes(paramView, paramRectF1, paramRectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOffloat);
      return;
    } 
    positionPathAttributes(paramRectF1, paramRectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOffloat);
  }
  
  void positionCartAttributes(RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOffloat) {
    float f1 = paramRectF1.centerX();
    float f2 = paramRectF1.centerY();
    float f3 = paramRectF2.centerX();
    float f4 = paramRectF2.centerY();
    f3 -= f1;
    f4 -= f2;
    if (paramArrayOfString[0] != null) {
      if ("percentX".equals(paramArrayOfString[0])) {
        paramArrayOffloat[0] = (paramFloat1 - f1) / f3;
        paramArrayOffloat[1] = (paramFloat2 - f2) / f4;
      } else {
        paramArrayOffloat[1] = (paramFloat1 - f1) / f3;
        paramArrayOffloat[0] = (paramFloat2 - f2) / f4;
      } 
    } else {
      paramArrayOfString[0] = "percentX";
      paramArrayOffloat[0] = (paramFloat1 - f1) / f3;
      paramArrayOfString[1] = "percentY";
      paramArrayOffloat[1] = (paramFloat2 - f2) / f4;
    } 
  }
  
  void positionPathAttributes(RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOffloat) {
    float f2 = paramRectF1.centerX();
    float f3 = paramRectF1.centerY();
    float f4 = paramRectF2.centerX();
    float f1 = paramRectF2.centerY();
    f4 -= f2;
    float f5 = f1 - f3;
    f1 = (float)Math.hypot(f4, f5);
    if (f1 < 1.0E-4D) {
      System.out.println("distance ~ 0");
      paramArrayOffloat[0] = 0.0F;
      paramArrayOffloat[1] = 0.0F;
      return;
    } 
    f4 /= f1;
    f5 /= f1;
    paramFloat2 -= f3;
    f2 = paramFloat1 - f2;
    paramFloat1 = (f4 * paramFloat2 - f2 * f5) / f1;
    paramFloat2 = (f4 * f2 + f5 * paramFloat2) / f1;
    if (paramArrayOfString[0] != null) {
      if ("percentX".equals(paramArrayOfString[0])) {
        paramArrayOffloat[0] = paramFloat2;
        paramArrayOffloat[1] = paramFloat1;
      } 
    } else {
      paramArrayOfString[0] = "percentX";
      paramArrayOfString[1] = "percentY";
      paramArrayOffloat[0] = paramFloat2;
      paramArrayOffloat[1] = paramFloat1;
    } 
  }
  
  void positionScreenAttributes(View paramView, RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOffloat) {
    paramRectF1.centerX();
    paramRectF1.centerY();
    paramRectF2.centerX();
    paramRectF2.centerY();
    ViewGroup viewGroup = (ViewGroup)paramView.getParent();
    int j = viewGroup.getWidth();
    int i = viewGroup.getHeight();
    if (paramArrayOfString[0] != null) {
      if ("percentX".equals(paramArrayOfString[0])) {
        paramArrayOffloat[0] = paramFloat1 / j;
        paramArrayOffloat[1] = paramFloat2 / i;
      } else {
        paramArrayOffloat[1] = paramFloat1 / j;
        paramArrayOffloat[0] = paramFloat2 / i;
      } 
    } else {
      paramArrayOfString[0] = "percentX";
      paramArrayOffloat[0] = paramFloat1 / j;
      paramArrayOfString[1] = "percentY";
      paramArrayOffloat[1] = paramFloat2 / i;
    } 
  }
  
  public void setValue(String paramString, Object paramObject) {
    float f;
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 428090548:
        if (paramString.equals("percentY")) {
          b = 6;
          break;
        } 
      case 428090547:
        if (paramString.equals("percentX")) {
          b = 5;
          break;
        } 
      case -200259324:
        if (paramString.equals("sizePercent")) {
          b = 4;
          break;
        } 
      case -827014263:
        if (paramString.equals("drawPath")) {
          b = 1;
          break;
        } 
      case -1017587252:
        if (paramString.equals("percentHeight")) {
          b = 3;
          break;
        } 
      case -1127236479:
        if (paramString.equals("percentWidth")) {
          b = 2;
          break;
        } 
      case -1812823328:
        if (paramString.equals("transitionEasing")) {
          b = 0;
          break;
        } 
    } 
    switch (b) {
      default:
        return;
      case 6:
        this.mPercentY = toFloat(paramObject);
      case 5:
        this.mPercentX = toFloat(paramObject);
      case 4:
        f = toFloat(paramObject);
        this.mPercentWidth = f;
        this.mPercentHeight = f;
      case 3:
        this.mPercentHeight = toFloat(paramObject);
      case 2:
        this.mPercentWidth = toFloat(paramObject);
      case 1:
        this.mDrawPath = toInt(paramObject);
      case 0:
        break;
    } 
    this.mTransitionEasing = paramObject.toString();
  }
  
  private static class Loader {
    private static final int CURVE_FIT = 4;
    
    private static final int DRAW_PATH = 5;
    
    private static final int FRAME_POSITION = 2;
    
    private static final int PATH_MOTION_ARC = 10;
    
    private static final int PERCENT_HEIGHT = 12;
    
    private static final int PERCENT_WIDTH = 11;
    
    private static final int PERCENT_X = 6;
    
    private static final int PERCENT_Y = 7;
    
    private static final int SIZE_PERCENT = 8;
    
    private static final int TARGET_ID = 1;
    
    private static final int TRANSITION_EASING = 3;
    
    private static final int TYPE = 9;
    
    private static SparseIntArray mAttrMap;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      mAttrMap = sparseIntArray;
      sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
      mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
      mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
      mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
      mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
      mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
      mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
      mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
      mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
      mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
      mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
      mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
    }
    
    private static void read(KeyPosition param1KeyPosition, TypedArray param1TypedArray) {
      int i = param1TypedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        float f;
        StringBuilder stringBuilder;
        int j = param1TypedArray.getIndex(b);
        switch (mAttrMap.get(j)) {
          default:
            stringBuilder = new StringBuilder();
            stringBuilder.append("unused attribute 0x");
            stringBuilder.append(Integer.toHexString(j));
            stringBuilder.append("   ");
            stringBuilder.append(mAttrMap.get(j));
            Log.e("KeyPosition", stringBuilder.toString());
            break;
          case 12:
            param1KeyPosition.mPercentHeight = param1TypedArray.getFloat(j, param1KeyPosition.mPercentHeight);
            break;
          case 11:
            param1KeyPosition.mPercentWidth = param1TypedArray.getFloat(j, param1KeyPosition.mPercentWidth);
            break;
          case 10:
            param1KeyPosition.mPathMotionArc = param1TypedArray.getInt(j, param1KeyPosition.mPathMotionArc);
            break;
          case 9:
            param1KeyPosition.mPositionType = param1TypedArray.getInt(j, param1KeyPosition.mPositionType);
            break;
          case 8:
            f = param1TypedArray.getFloat(j, param1KeyPosition.mPercentHeight);
            param1KeyPosition.mPercentWidth = f;
            param1KeyPosition.mPercentHeight = f;
            break;
          case 7:
            param1KeyPosition.mPercentY = param1TypedArray.getFloat(j, param1KeyPosition.mPercentY);
            break;
          case 6:
            param1KeyPosition.mPercentX = param1TypedArray.getFloat(j, param1KeyPosition.mPercentX);
            break;
          case 5:
            param1KeyPosition.mDrawPath = param1TypedArray.getInt(j, param1KeyPosition.mDrawPath);
            break;
          case 4:
            param1KeyPosition.mCurveFit = param1TypedArray.getInteger(j, param1KeyPosition.mCurveFit);
            break;
          case 3:
            if ((param1TypedArray.peekValue(j)).type == 3) {
              param1KeyPosition.mTransitionEasing = param1TypedArray.getString(j);
              break;
            } 
            param1KeyPosition.mTransitionEasing = Easing.NAMED_EASING[param1TypedArray.getInteger(j, 0)];
            break;
          case 2:
            param1KeyPosition.mFramePosition = param1TypedArray.getInt(j, param1KeyPosition.mFramePosition);
            break;
          case 1:
            if (MotionLayout.IS_IN_EDIT_MODE) {
              param1KeyPosition.mTargetId = param1TypedArray.getResourceId(j, param1KeyPosition.mTargetId);
              if (param1KeyPosition.mTargetId == -1)
                param1KeyPosition.mTargetString = param1TypedArray.getString(j); 
              break;
            } 
            if ((param1TypedArray.peekValue(j)).type == 3) {
              param1KeyPosition.mTargetString = param1TypedArray.getString(j);
              break;
            } 
            param1KeyPosition.mTargetId = param1TypedArray.getResourceId(j, param1KeyPosition.mTargetId);
            break;
        } 
      } 
      if (param1KeyPosition.mFramePosition == -1)
        Log.e("KeyPosition", "no frame position"); 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\KeyPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */