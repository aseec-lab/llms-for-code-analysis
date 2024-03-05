package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.VelocityMatrix;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MotionController {
  private static final boolean DEBUG = false;
  
  public static final int DRAW_PATH_AS_CONFIGURED = 4;
  
  public static final int DRAW_PATH_BASIC = 1;
  
  public static final int DRAW_PATH_CARTESIAN = 3;
  
  public static final int DRAW_PATH_NONE = 0;
  
  public static final int DRAW_PATH_RECTANGLE = 5;
  
  public static final int DRAW_PATH_RELATIVE = 2;
  
  public static final int DRAW_PATH_SCREEN = 6;
  
  private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
  
  public static final int HORIZONTAL_PATH_X = 2;
  
  public static final int HORIZONTAL_PATH_Y = 3;
  
  public static final int PATH_PERCENT = 0;
  
  public static final int PATH_PERPENDICULAR = 1;
  
  private static final String TAG = "MotionController";
  
  public static final int VERTICAL_PATH_X = 4;
  
  public static final int VERTICAL_PATH_Y = 5;
  
  private int MAX_DIMENSION = 4;
  
  String[] attributeTable;
  
  private CurveFit mArcSpline;
  
  private int[] mAttributeInterpCount;
  
  private String[] mAttributeNames;
  
  private HashMap<String, SplineSet> mAttributesMap;
  
  String mConstraintTag;
  
  private int mCurveFitType = -1;
  
  private HashMap<String, KeyCycleOscillator> mCycleMap;
  
  private MotionPaths mEndMotionPath = new MotionPaths();
  
  private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
  
  int mId;
  
  private double[] mInterpolateData;
  
  private int[] mInterpolateVariables;
  
  private double[] mInterpolateVelocity;
  
  private ArrayList<Key> mKeyList = new ArrayList<Key>();
  
  private KeyTrigger[] mKeyTriggers;
  
  private ArrayList<MotionPaths> mMotionPaths = new ArrayList<MotionPaths>();
  
  float mMotionStagger = Float.NaN;
  
  private int mPathMotionArc = Key.UNSET;
  
  private CurveFit[] mSpline;
  
  float mStaggerOffset = 0.0F;
  
  float mStaggerScale = 1.0F;
  
  private MotionPaths mStartMotionPath = new MotionPaths();
  
  private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
  
  private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
  
  private float[] mValuesBuff = new float[4];
  
  private float[] mVelocity = new float[1];
  
  View mView;
  
  MotionController(View paramView) {
    setView(paramView);
  }
  
  private float getAdjustedPosition(float paramFloat, float[] paramArrayOffloat) {
    float f1;
    float f3 = 0.0F;
    float f4 = 1.0F;
    if (paramArrayOffloat != null) {
      paramArrayOffloat[0] = 1.0F;
      f1 = paramFloat;
    } else {
      f1 = paramFloat;
      if (this.mStaggerScale != 1.0D) {
        float f = paramFloat;
        if (paramFloat < this.mStaggerOffset)
          f = 0.0F; 
        paramFloat = this.mStaggerOffset;
        f1 = f;
        if (f > paramFloat) {
          f1 = f;
          if (f < 1.0D)
            f1 = (f - paramFloat) * this.mStaggerScale; 
        } 
      } 
    } 
    Easing easing = this.mStartMotionPath.mKeyFrameEasing;
    paramFloat = Float.NaN;
    Iterator<MotionPaths> iterator = this.mMotionPaths.iterator();
    float f2 = f3;
    while (iterator.hasNext()) {
      MotionPaths motionPaths = iterator.next();
      if (motionPaths.mKeyFrameEasing != null) {
        if (motionPaths.time < f1) {
          easing = motionPaths.mKeyFrameEasing;
          f2 = motionPaths.time;
          continue;
        } 
        if (Float.isNaN(paramFloat))
          paramFloat = motionPaths.time; 
      } 
    } 
    f3 = f1;
    if (easing != null) {
      if (Float.isNaN(paramFloat))
        paramFloat = f4; 
      paramFloat -= f2;
      double d = ((f1 - f2) / paramFloat);
      paramFloat = (float)easing.get(d) * paramFloat + f2;
      f3 = paramFloat;
      if (paramArrayOffloat != null) {
        paramArrayOffloat[0] = (float)easing.getDiff(d);
        f3 = paramFloat;
      } 
    } 
    return f3;
  }
  
  private float getPreCycleDistance() {
    float[] arrayOfFloat = new float[2];
    float f2 = 1.0F / 99;
    double d2 = 0.0D;
    double d1 = 0.0D;
    byte b = 0;
    float f1;
    for (f1 = 0.0F; b < 100; f1 = f3) {
      float f5 = b * f2;
      double d = f5;
      Easing easing = this.mStartMotionPath.mKeyFrameEasing;
      float f3 = Float.NaN;
      Iterator<MotionPaths> iterator = this.mMotionPaths.iterator();
      float f4 = 0.0F;
      while (iterator.hasNext()) {
        MotionPaths motionPaths = iterator.next();
        if (motionPaths.mKeyFrameEasing != null) {
          if (motionPaths.time < f5) {
            easing = motionPaths.mKeyFrameEasing;
            f4 = motionPaths.time;
            continue;
          } 
          if (Float.isNaN(f3))
            f3 = motionPaths.time; 
        } 
      } 
      if (easing != null) {
        float f = f3;
        if (Float.isNaN(f3))
          f = 1.0F; 
        f3 = f - f4;
        d = ((float)easing.get(((f5 - f4) / f3)) * f3 + f4);
      } 
      this.mSpline[0].getPos(d, this.mInterpolateData);
      this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, arrayOfFloat, 0);
      f3 = f1;
      if (b > 0)
        f3 = (float)(f1 + Math.hypot(d1 - arrayOfFloat[1], d2 - arrayOfFloat[0])); 
      d2 = arrayOfFloat[0];
      d1 = arrayOfFloat[1];
      b++;
    } 
    return f1;
  }
  
  private void insertKey(MotionPaths paramMotionPaths) {
    int i = Collections.binarySearch((List)this.mMotionPaths, paramMotionPaths);
    if (i == 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(" KeyPath positon \"");
      stringBuilder.append(paramMotionPaths.position);
      stringBuilder.append("\" outside of range");
      Log.e("MotionController", stringBuilder.toString());
    } 
    this.mMotionPaths.add(-i - 1, paramMotionPaths);
  }
  
  private void readView(MotionPaths paramMotionPaths) {
    paramMotionPaths.setBounds((int)this.mView.getX(), (int)this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
  }
  
  void addKey(Key paramKey) {
    this.mKeyList.add(paramKey);
  }
  
  void addKeys(ArrayList<Key> paramArrayList) {
    this.mKeyList.addAll(paramArrayList);
  }
  
  void buildBounds(float[] paramArrayOffloat, int paramInt) {
    float f = 1.0F / (paramInt - 1);
    HashMap<String, SplineSet> hashMap1 = this.mAttributesMap;
    if (hashMap1 != null)
      SplineSet splineSet = hashMap1.get("translationX"); 
    hashMap1 = this.mAttributesMap;
    if (hashMap1 != null)
      SplineSet splineSet = hashMap1.get("translationY"); 
    HashMap<String, KeyCycleOscillator> hashMap = this.mCycleMap;
    if (hashMap != null)
      KeyCycleOscillator keyCycleOscillator = hashMap.get("translationX"); 
    hashMap = this.mCycleMap;
    if (hashMap != null)
      KeyCycleOscillator keyCycleOscillator = hashMap.get("translationY"); 
    for (byte b = 0; b < paramInt; b++) {
      float f3 = b * f;
      float f2 = this.mStaggerScale;
      float f4 = 0.0F;
      float f1 = f3;
      if (f2 != 1.0F) {
        f2 = f3;
        if (f3 < this.mStaggerOffset)
          f2 = 0.0F; 
        f3 = this.mStaggerOffset;
        f1 = f2;
        if (f2 > f3) {
          f1 = f2;
          if (f2 < 1.0D)
            f1 = (f2 - f3) * this.mStaggerScale; 
        } 
      } 
      double d = f1;
      Easing easing = this.mStartMotionPath.mKeyFrameEasing;
      f2 = Float.NaN;
      Iterator<MotionPaths> iterator = this.mMotionPaths.iterator();
      f3 = f4;
      while (iterator.hasNext()) {
        MotionPaths motionPaths = iterator.next();
        if (motionPaths.mKeyFrameEasing != null) {
          if (motionPaths.time < f1) {
            easing = motionPaths.mKeyFrameEasing;
            f3 = motionPaths.time;
            continue;
          } 
          if (Float.isNaN(f2))
            f2 = motionPaths.time; 
        } 
      } 
      if (easing != null) {
        f4 = f2;
        if (Float.isNaN(f2))
          f4 = 1.0F; 
        f2 = f4 - f3;
        d = ((float)easing.get(((f1 - f3) / f2)) * f2 + f3);
      } 
      this.mSpline[0].getPos(d, this.mInterpolateData);
      CurveFit curveFit = this.mArcSpline;
      if (curveFit != null) {
        double[] arrayOfDouble = this.mInterpolateData;
        if (arrayOfDouble.length > 0)
          curveFit.getPos(d, arrayOfDouble); 
      } 
      this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, paramArrayOffloat, b * 2);
    } 
  }
  
  int buildKeyBounds(float[] paramArrayOffloat, int[] paramArrayOfint) {
    if (paramArrayOffloat != null) {
      double[] arrayOfDouble = this.mSpline[0].getTimePoints();
      if (paramArrayOfint != null) {
        Iterator<MotionPaths> iterator = this.mMotionPaths.iterator();
        for (byte b = 0; iterator.hasNext(); b++)
          paramArrayOfint[b] = ((MotionPaths)iterator.next()).mMode; 
      } 
      byte b1 = 0;
      byte b2 = 0;
      while (b1 < arrayOfDouble.length) {
        this.mSpline[0].getPos(arrayOfDouble[b1], this.mInterpolateData);
        this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, paramArrayOffloat, b2);
        b2 += true;
        b1++;
      } 
      return b2 / 2;
    } 
    return 0;
  }
  
  int buildKeyFrames(float[] paramArrayOffloat, int[] paramArrayOfint) {
    if (paramArrayOffloat != null) {
      double[] arrayOfDouble = this.mSpline[0].getTimePoints();
      if (paramArrayOfint != null) {
        Iterator<MotionPaths> iterator = this.mMotionPaths.iterator();
        for (byte b = 0; iterator.hasNext(); b++)
          paramArrayOfint[b] = ((MotionPaths)iterator.next()).mMode; 
      } 
      byte b1 = 0;
      byte b2 = 0;
      while (b1 < arrayOfDouble.length) {
        this.mSpline[0].getPos(arrayOfDouble[b1], this.mInterpolateData);
        this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, paramArrayOffloat, b2);
        b2 += true;
        b1++;
      } 
      return b2 / 2;
    } 
    return 0;
  }
  
  void buildPath(float[] paramArrayOffloat, int paramInt) {
    SplineSet splineSet1;
    SplineSet splineSet2;
    KeyCycleOscillator keyCycleOscillator1;
    float f = 1.0F / (paramInt - 1);
    HashMap<String, SplineSet> hashMap1 = this.mAttributesMap;
    KeyCycleOscillator keyCycleOscillator2 = null;
    if (hashMap1 == null) {
      hashMap1 = null;
    } else {
      splineSet1 = hashMap1.get("translationX");
    } 
    HashMap<String, SplineSet> hashMap2 = this.mAttributesMap;
    if (hashMap2 == null) {
      hashMap2 = null;
    } else {
      splineSet2 = hashMap2.get("translationY");
    } 
    HashMap<String, KeyCycleOscillator> hashMap3 = this.mCycleMap;
    if (hashMap3 == null) {
      hashMap3 = null;
    } else {
      keyCycleOscillator1 = hashMap3.get("translationX");
    } 
    HashMap<String, KeyCycleOscillator> hashMap4 = this.mCycleMap;
    if (hashMap4 != null)
      keyCycleOscillator2 = hashMap4.get("translationY"); 
    for (byte b = 0; b < paramInt; b++) {
      float f3 = b * f;
      float f2 = this.mStaggerScale;
      float f4 = 0.0F;
      float f1 = f3;
      if (f2 != 1.0F) {
        f2 = f3;
        if (f3 < this.mStaggerOffset)
          f2 = 0.0F; 
        f3 = this.mStaggerOffset;
        f1 = f2;
        if (f2 > f3) {
          f1 = f2;
          if (f2 < 1.0D)
            f1 = (f2 - f3) * this.mStaggerScale; 
        } 
      } 
      double d = f1;
      Easing easing = this.mStartMotionPath.mKeyFrameEasing;
      f2 = Float.NaN;
      Iterator<MotionPaths> iterator = this.mMotionPaths.iterator();
      f3 = f4;
      while (iterator.hasNext()) {
        MotionPaths motionPaths1 = iterator.next();
        Easing easing1 = easing;
        float f5 = f3;
        f4 = f2;
        if (motionPaths1.mKeyFrameEasing != null)
          if (motionPaths1.time < f1) {
            easing1 = motionPaths1.mKeyFrameEasing;
            f5 = motionPaths1.time;
            f4 = f2;
          } else {
            easing1 = easing;
            f5 = f3;
            f4 = f2;
            if (Float.isNaN(f2)) {
              f4 = motionPaths1.time;
              f5 = f3;
              easing1 = easing;
            } 
          }  
        easing = easing1;
        f3 = f5;
        f2 = f4;
      } 
      if (easing != null) {
        f4 = f2;
        if (Float.isNaN(f2))
          f4 = 1.0F; 
        f2 = f4 - f3;
        d = ((float)easing.get(((f1 - f3) / f2)) * f2 + f3);
      } 
      this.mSpline[0].getPos(d, this.mInterpolateData);
      CurveFit curveFit = this.mArcSpline;
      if (curveFit != null) {
        double[] arrayOfDouble1 = this.mInterpolateData;
        if (arrayOfDouble1.length > 0)
          curveFit.getPos(d, arrayOfDouble1); 
      } 
      MotionPaths motionPaths = this.mStartMotionPath;
      int[] arrayOfInt = this.mInterpolateVariables;
      double[] arrayOfDouble = this.mInterpolateData;
      int i = b * 2;
      motionPaths.getCenter(arrayOfInt, arrayOfDouble, paramArrayOffloat, i);
      if (keyCycleOscillator1 != null) {
        paramArrayOffloat[i] = paramArrayOffloat[i] + keyCycleOscillator1.get(f1);
      } else if (splineSet1 != null) {
        paramArrayOffloat[i] = paramArrayOffloat[i] + splineSet1.get(f1);
      } 
      if (keyCycleOscillator2 != null) {
        paramArrayOffloat[++i] = paramArrayOffloat[i] + keyCycleOscillator2.get(f1);
      } else if (splineSet2 != null) {
        paramArrayOffloat[++i] = paramArrayOffloat[i] + splineSet2.get(f1);
      } 
    } 
  }
  
  void buildRect(float paramFloat, float[] paramArrayOffloat, int paramInt) {
    paramFloat = getAdjustedPosition(paramFloat, null);
    this.mSpline[0].getPos(paramFloat, this.mInterpolateData);
    this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, paramArrayOffloat, paramInt);
  }
  
  void buildRectangles(float[] paramArrayOffloat, int paramInt) {
    float f = 1.0F / (paramInt - 1);
    for (byte b = 0; b < paramInt; b++) {
      float f1 = getAdjustedPosition(b * f, null);
      this.mSpline[0].getPos(f1, this.mInterpolateData);
      this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, paramArrayOffloat, b * 8);
    } 
  }
  
  int getAttributeValues(String paramString, float[] paramArrayOffloat, int paramInt) {
    SplineSet splineSet = this.mAttributesMap.get(paramString);
    if (splineSet == null)
      return -1; 
    for (paramInt = 0; paramInt < paramArrayOffloat.length; paramInt++)
      paramArrayOffloat[paramInt] = splineSet.get((paramInt / (paramArrayOffloat.length - 1))); 
    return paramArrayOffloat.length;
  }
  
  void getDpDt(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOffloat) {
    paramFloat1 = getAdjustedPosition(paramFloat1, this.mVelocity);
    CurveFit[] arrayOfCurveFit = this.mSpline;
    byte b = 0;
    if (arrayOfCurveFit != null) {
      CurveFit curveFit = arrayOfCurveFit[0];
      double d = paramFloat1;
      curveFit.getSlope(d, this.mInterpolateVelocity);
      this.mSpline[0].getPos(d, this.mInterpolateData);
      paramFloat1 = this.mVelocity[0];
      while (true) {
        double[] arrayOfDouble = this.mInterpolateVelocity;
        if (b < arrayOfDouble.length) {
          arrayOfDouble[b] = arrayOfDouble[b] * paramFloat1;
          b++;
          continue;
        } 
        curveFit = this.mArcSpline;
        if (curveFit != null) {
          arrayOfDouble = this.mInterpolateData;
          if (arrayOfDouble.length > 0) {
            curveFit.getPos(d, arrayOfDouble);
            this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
            this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOffloat, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
          } 
          return;
        } 
        this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOffloat, this.mInterpolateVariables, arrayOfDouble, this.mInterpolateData);
        return;
      } 
    } 
    paramFloat1 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f5 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f2 = this.mEndMotionPath.width;
    float f3 = this.mStartMotionPath.width;
    float f1 = this.mEndMotionPath.height;
    float f4 = this.mStartMotionPath.height;
    paramArrayOffloat[0] = paramFloat1 * (1.0F - paramFloat2) + (f2 - f3 + paramFloat1) * paramFloat2;
    paramArrayOffloat[1] = f5 * (1.0F - paramFloat3) + (f1 - f4 + f5) * paramFloat3;
  }
  
  public int getDrawPath() {
    int i = this.mStartMotionPath.mDrawPath;
    Iterator<MotionPaths> iterator = this.mMotionPaths.iterator();
    while (iterator.hasNext())
      i = Math.max(i, ((MotionPaths)iterator.next()).mDrawPath); 
    return Math.max(i, this.mEndMotionPath.mDrawPath);
  }
  
  float getFinalX() {
    return this.mEndMotionPath.x;
  }
  
  float getFinalY() {
    return this.mEndMotionPath.y;
  }
  
  MotionPaths getKeyFrame(int paramInt) {
    return this.mMotionPaths.get(paramInt);
  }
  
  public int getKeyFrameInfo(int paramInt, int[] paramArrayOfint) {
    float[] arrayOfFloat = new float[2];
    Iterator<Key> iterator = this.mKeyList.iterator();
    byte b = 0;
    int i;
    for (i = 0; iterator.hasNext(); i = j) {
      Key key = iterator.next();
      if (key.mType != paramInt && paramInt == -1)
        continue; 
      paramArrayOfint[i] = 0;
      int j = i + 1;
      paramArrayOfint[j] = key.mType;
      paramArrayOfint[++j] = key.mFramePosition;
      float f = key.mFramePosition / 100.0F;
      this.mSpline[0].getPos(f, this.mInterpolateData);
      this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, arrayOfFloat, 0);
      paramArrayOfint[++j] = Float.floatToIntBits(arrayOfFloat[0]);
      int k = j + 1;
      paramArrayOfint[k] = Float.floatToIntBits(arrayOfFloat[1]);
      j = k;
      if (key instanceof KeyPosition) {
        key = key;
        j = k + 1;
        paramArrayOfint[j] = ((KeyPosition)key).mPositionType;
        paramArrayOfint[++j] = Float.floatToIntBits(((KeyPosition)key).mPercentX);
        paramArrayOfint[++j] = Float.floatToIntBits(((KeyPosition)key).mPercentY);
      } 
      paramArrayOfint[i] = ++j - i;
      b++;
    } 
    return b;
  }
  
  float getKeyFrameParameter(int paramInt, float paramFloat1, float paramFloat2) {
    float f1 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f2 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f7 = this.mStartMotionPath.x;
    float f6 = this.mStartMotionPath.width / 2.0F;
    float f5 = this.mStartMotionPath.y;
    float f4 = this.mStartMotionPath.height / 2.0F;
    float f3 = (float)Math.hypot(f1, f2);
    if (f3 < 1.0E-7D)
      return Float.NaN; 
    paramFloat1 -= f7 + f6;
    paramFloat2 -= f5 + f4;
    if ((float)Math.hypot(paramFloat1, paramFloat2) == 0.0F)
      return 0.0F; 
    f4 = paramFloat1 * f1 + paramFloat2 * f2;
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? ((paramInt != 4) ? ((paramInt != 5) ? 0.0F : (paramFloat2 / f2)) : (paramFloat1 / f2)) : (paramFloat2 / f1)) : (paramFloat1 / f1)) : (float)Math.sqrt((f3 * f3 - f4 * f4))) : (f4 / f3);
  }
  
  KeyPositionBase getPositionKeyframe(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
    RectF rectF1 = new RectF();
    rectF1.left = this.mStartMotionPath.x;
    rectF1.top = this.mStartMotionPath.y;
    rectF1.right = rectF1.left + this.mStartMotionPath.width;
    rectF1.bottom = rectF1.top + this.mStartMotionPath.height;
    RectF rectF2 = new RectF();
    rectF2.left = this.mEndMotionPath.x;
    rectF2.top = this.mEndMotionPath.y;
    rectF2.right = rectF2.left + this.mEndMotionPath.width;
    rectF2.bottom = rectF2.top + this.mEndMotionPath.height;
    for (Key key : this.mKeyList) {
      if (key instanceof KeyPositionBase) {
        key = key;
        if (key.intersects(paramInt1, paramInt2, rectF1, rectF2, paramFloat1, paramFloat2))
          return (KeyPositionBase)key; 
      } 
    } 
    return null;
  }
  
  void getPostLayoutDvDp(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float paramFloat3, float[] paramArrayOffloat) {
    SplineSet splineSet1;
    double[] arrayOfDouble;
    SplineSet splineSet2;
    SplineSet splineSet3;
    SplineSet splineSet4;
    SplineSet splineSet5;
    KeyCycleOscillator keyCycleOscillator1;
    KeyCycleOscillator keyCycleOscillator2;
    KeyCycleOscillator keyCycleOscillator3;
    KeyCycleOscillator keyCycleOscillator4;
    paramFloat1 = getAdjustedPosition(paramFloat1, this.mVelocity);
    HashMap<String, SplineSet> hashMap1 = this.mAttributesMap;
    KeyCycleOscillator keyCycleOscillator5 = null;
    if (hashMap1 == null) {
      hashMap1 = null;
    } else {
      splineSet1 = hashMap1.get("translationX");
    } 
    HashMap<String, SplineSet> hashMap2 = this.mAttributesMap;
    if (hashMap2 == null) {
      hashMap2 = null;
    } else {
      splineSet2 = hashMap2.get("translationY");
    } 
    HashMap<String, SplineSet> hashMap3 = this.mAttributesMap;
    if (hashMap3 == null) {
      hashMap3 = null;
    } else {
      splineSet3 = hashMap3.get("rotation");
    } 
    HashMap<String, SplineSet> hashMap4 = this.mAttributesMap;
    if (hashMap4 == null) {
      hashMap4 = null;
    } else {
      splineSet4 = hashMap4.get("scaleX");
    } 
    HashMap<String, SplineSet> hashMap5 = this.mAttributesMap;
    if (hashMap5 == null) {
      hashMap5 = null;
    } else {
      splineSet5 = hashMap5.get("scaleY");
    } 
    HashMap<String, KeyCycleOscillator> hashMap6 = this.mCycleMap;
    if (hashMap6 == null) {
      hashMap6 = null;
    } else {
      keyCycleOscillator1 = hashMap6.get("translationX");
    } 
    HashMap<String, KeyCycleOscillator> hashMap7 = this.mCycleMap;
    if (hashMap7 == null) {
      hashMap7 = null;
    } else {
      keyCycleOscillator2 = hashMap7.get("translationY");
    } 
    HashMap<String, KeyCycleOscillator> hashMap8 = this.mCycleMap;
    if (hashMap8 == null) {
      hashMap8 = null;
    } else {
      keyCycleOscillator3 = hashMap8.get("rotation");
    } 
    HashMap<String, KeyCycleOscillator> hashMap9 = this.mCycleMap;
    if (hashMap9 == null) {
      hashMap9 = null;
    } else {
      keyCycleOscillator4 = hashMap9.get("scaleX");
    } 
    HashMap<String, KeyCycleOscillator> hashMap10 = this.mCycleMap;
    if (hashMap10 != null)
      keyCycleOscillator5 = hashMap10.get("scaleY"); 
    VelocityMatrix velocityMatrix = new VelocityMatrix();
    velocityMatrix.clear();
    velocityMatrix.setRotationVelocity(splineSet3, paramFloat1);
    velocityMatrix.setTranslationVelocity(splineSet1, splineSet2, paramFloat1);
    velocityMatrix.setScaleVelocity(splineSet4, splineSet5, paramFloat1);
    velocityMatrix.setRotationVelocity(keyCycleOscillator3, paramFloat1);
    velocityMatrix.setTranslationVelocity(keyCycleOscillator1, keyCycleOscillator2, paramFloat1);
    velocityMatrix.setScaleVelocity(keyCycleOscillator4, keyCycleOscillator5, paramFloat1);
    CurveFit curveFit = this.mArcSpline;
    if (curveFit != null) {
      arrayOfDouble = this.mInterpolateData;
      if (arrayOfDouble.length > 0) {
        double d = paramFloat1;
        curveFit.getPos(d, arrayOfDouble);
        this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
        this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOffloat, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
      } 
      velocityMatrix.applyTransform(paramFloat2, paramFloat3, paramInt1, paramInt2, paramArrayOffloat);
      return;
    } 
    CurveFit[] arrayOfCurveFit = this.mSpline;
    byte b = 0;
    if (arrayOfCurveFit != null) {
      paramFloat1 = getAdjustedPosition(paramFloat1, this.mVelocity);
      CurveFit curveFit1 = this.mSpline[0];
      double d = paramFloat1;
      curveFit1.getSlope(d, this.mInterpolateVelocity);
      this.mSpline[0].getPos(d, this.mInterpolateData);
      paramFloat1 = this.mVelocity[0];
      while (true) {
        arrayOfDouble = this.mInterpolateVelocity;
        if (b < arrayOfDouble.length) {
          arrayOfDouble[b] = arrayOfDouble[b] * paramFloat1;
          b++;
          continue;
        } 
        this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOffloat, this.mInterpolateVariables, arrayOfDouble, this.mInterpolateData);
        velocityMatrix.applyTransform(paramFloat2, paramFloat3, paramInt1, paramInt2, paramArrayOffloat);
        return;
      } 
    } 
    float f5 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f1 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f4 = this.mEndMotionPath.width;
    float f6 = this.mStartMotionPath.width;
    float f3 = this.mEndMotionPath.height;
    float f2 = this.mStartMotionPath.height;
    paramArrayOffloat[0] = f5 * (1.0F - paramFloat2) + (f4 - f6 + f5) * paramFloat2;
    paramArrayOffloat[1] = f1 * (1.0F - paramFloat3) + (f3 - f2 + f1) * paramFloat3;
    velocityMatrix.clear();
    velocityMatrix.setRotationVelocity(splineSet3, paramFloat1);
    velocityMatrix.setTranslationVelocity((SplineSet)arrayOfDouble, splineSet2, paramFloat1);
    velocityMatrix.setScaleVelocity(splineSet4, splineSet5, paramFloat1);
    velocityMatrix.setRotationVelocity(keyCycleOscillator3, paramFloat1);
    velocityMatrix.setTranslationVelocity(keyCycleOscillator1, keyCycleOscillator2, paramFloat1);
    velocityMatrix.setScaleVelocity(keyCycleOscillator4, keyCycleOscillator5, paramFloat1);
    velocityMatrix.applyTransform(paramFloat2, paramFloat3, paramInt1, paramInt2, paramArrayOffloat);
  }
  
  float getStartX() {
    return this.mStartMotionPath.x;
  }
  
  float getStartY() {
    return this.mStartMotionPath.y;
  }
  
  public int getkeyFramePositions(int[] paramArrayOfint, float[] paramArrayOffloat) {
    Iterator<Key> iterator = this.mKeyList.iterator();
    byte b = 0;
    boolean bool = false;
    while (iterator.hasNext()) {
      Key key = iterator.next();
      paramArrayOfint[b] = key.mFramePosition + key.mType * 1000;
      float f = key.mFramePosition / 100.0F;
      this.mSpline[0].getPos(f, this.mInterpolateData);
      this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, paramArrayOffloat, bool);
      bool += true;
      b++;
    } 
    return b;
  }
  
  boolean interpolate(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache) {
    // Byte code:
    //   0: aload_0
    //   1: fload_2
    //   2: aconst_null
    //   3: invokespecial getAdjustedPosition : (F[F)F
    //   6: fstore_2
    //   7: aload_0
    //   8: getfield mAttributesMap : Ljava/util/HashMap;
    //   11: astore #26
    //   13: aload #26
    //   15: ifnull -> 58
    //   18: aload #26
    //   20: invokevirtual values : ()Ljava/util/Collection;
    //   23: invokeinterface iterator : ()Ljava/util/Iterator;
    //   28: astore #26
    //   30: aload #26
    //   32: invokeinterface hasNext : ()Z
    //   37: ifeq -> 58
    //   40: aload #26
    //   42: invokeinterface next : ()Ljava/lang/Object;
    //   47: checkcast androidx/constraintlayout/motion/widget/SplineSet
    //   50: aload_1
    //   51: fload_2
    //   52: invokevirtual setProperty : (Landroid/view/View;F)V
    //   55: goto -> 30
    //   58: aload_0
    //   59: getfield mTimeCycleAttributesMap : Ljava/util/HashMap;
    //   62: astore #26
    //   64: aload #26
    //   66: ifnull -> 148
    //   69: aload #26
    //   71: invokevirtual values : ()Ljava/util/Collection;
    //   74: invokeinterface iterator : ()Ljava/util/Iterator;
    //   79: astore #27
    //   81: aconst_null
    //   82: astore #26
    //   84: iconst_0
    //   85: istore #24
    //   87: aload #27
    //   89: invokeinterface hasNext : ()Z
    //   94: ifeq -> 145
    //   97: aload #27
    //   99: invokeinterface next : ()Ljava/lang/Object;
    //   104: checkcast androidx/constraintlayout/motion/widget/TimeCycleSplineSet
    //   107: astore #28
    //   109: aload #28
    //   111: instanceof androidx/constraintlayout/motion/widget/TimeCycleSplineSet$PathRotate
    //   114: ifeq -> 127
    //   117: aload #28
    //   119: checkcast androidx/constraintlayout/motion/widget/TimeCycleSplineSet$PathRotate
    //   122: astore #26
    //   124: goto -> 87
    //   127: iload #24
    //   129: aload #28
    //   131: aload_1
    //   132: fload_2
    //   133: lload_3
    //   134: aload #5
    //   136: invokevirtual setProperty : (Landroid/view/View;FJLandroidx/constraintlayout/motion/widget/KeyCache;)Z
    //   139: ior
    //   140: istore #24
    //   142: goto -> 87
    //   145: goto -> 154
    //   148: aconst_null
    //   149: astore #26
    //   151: iconst_0
    //   152: istore #24
    //   154: aload_0
    //   155: getfield mSpline : [Landroidx/constraintlayout/motion/utils/CurveFit;
    //   158: astore #27
    //   160: aload #27
    //   162: ifnull -> 575
    //   165: aload #27
    //   167: iconst_0
    //   168: aaload
    //   169: astore #27
    //   171: fload_2
    //   172: f2d
    //   173: dstore #6
    //   175: aload #27
    //   177: dload #6
    //   179: aload_0
    //   180: getfield mInterpolateData : [D
    //   183: invokevirtual getPos : (D[D)V
    //   186: aload_0
    //   187: getfield mSpline : [Landroidx/constraintlayout/motion/utils/CurveFit;
    //   190: iconst_0
    //   191: aaload
    //   192: dload #6
    //   194: aload_0
    //   195: getfield mInterpolateVelocity : [D
    //   198: invokevirtual getSlope : (D[D)V
    //   201: aload_0
    //   202: getfield mArcSpline : Landroidx/constraintlayout/motion/utils/CurveFit;
    //   205: astore #28
    //   207: aload #28
    //   209: ifnull -> 246
    //   212: aload_0
    //   213: getfield mInterpolateData : [D
    //   216: astore #27
    //   218: aload #27
    //   220: arraylength
    //   221: ifle -> 246
    //   224: aload #28
    //   226: dload #6
    //   228: aload #27
    //   230: invokevirtual getPos : (D[D)V
    //   233: aload_0
    //   234: getfield mArcSpline : Landroidx/constraintlayout/motion/utils/CurveFit;
    //   237: dload #6
    //   239: aload_0
    //   240: getfield mInterpolateVelocity : [D
    //   243: invokevirtual getSlope : (D[D)V
    //   246: aload_0
    //   247: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   250: aload_1
    //   251: aload_0
    //   252: getfield mInterpolateVariables : [I
    //   255: aload_0
    //   256: getfield mInterpolateData : [D
    //   259: aload_0
    //   260: getfield mInterpolateVelocity : [D
    //   263: aconst_null
    //   264: invokevirtual setView : (Landroid/view/View;[I[D[D[D)V
    //   267: aload_0
    //   268: getfield mAttributesMap : Ljava/util/HashMap;
    //   271: astore #27
    //   273: aload #27
    //   275: ifnull -> 351
    //   278: aload #27
    //   280: invokevirtual values : ()Ljava/util/Collection;
    //   283: invokeinterface iterator : ()Ljava/util/Iterator;
    //   288: astore #27
    //   290: aload #27
    //   292: invokeinterface hasNext : ()Z
    //   297: ifeq -> 351
    //   300: aload #27
    //   302: invokeinterface next : ()Ljava/lang/Object;
    //   307: checkcast androidx/constraintlayout/motion/widget/SplineSet
    //   310: astore #28
    //   312: aload #28
    //   314: instanceof androidx/constraintlayout/motion/widget/SplineSet$PathRotate
    //   317: ifeq -> 290
    //   320: aload #28
    //   322: checkcast androidx/constraintlayout/motion/widget/SplineSet$PathRotate
    //   325: astore #28
    //   327: aload_0
    //   328: getfield mInterpolateVelocity : [D
    //   331: astore #29
    //   333: aload #28
    //   335: aload_1
    //   336: fload_2
    //   337: aload #29
    //   339: iconst_0
    //   340: daload
    //   341: aload #29
    //   343: iconst_1
    //   344: daload
    //   345: invokevirtual setPathRotate : (Landroid/view/View;FDD)V
    //   348: goto -> 290
    //   351: aload #26
    //   353: ifnull -> 388
    //   356: aload_0
    //   357: getfield mInterpolateVelocity : [D
    //   360: astore #27
    //   362: aload #26
    //   364: aload_1
    //   365: aload #5
    //   367: fload_2
    //   368: lload_3
    //   369: aload #27
    //   371: iconst_0
    //   372: daload
    //   373: aload #27
    //   375: iconst_1
    //   376: daload
    //   377: invokevirtual setPathRotate : (Landroid/view/View;Landroidx/constraintlayout/motion/widget/KeyCache;FJDD)Z
    //   380: iload #24
    //   382: ior
    //   383: istore #24
    //   385: goto -> 388
    //   388: iconst_1
    //   389: istore #20
    //   391: aload_0
    //   392: getfield mSpline : [Landroidx/constraintlayout/motion/utils/CurveFit;
    //   395: astore #5
    //   397: iload #20
    //   399: aload #5
    //   401: arraylength
    //   402: if_icmpge -> 455
    //   405: aload #5
    //   407: iload #20
    //   409: aaload
    //   410: dload #6
    //   412: aload_0
    //   413: getfield mValuesBuff : [F
    //   416: invokevirtual getPos : (D[F)V
    //   419: aload_0
    //   420: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   423: getfield attributes : Ljava/util/LinkedHashMap;
    //   426: aload_0
    //   427: getfield mAttributeNames : [Ljava/lang/String;
    //   430: iload #20
    //   432: iconst_1
    //   433: isub
    //   434: aaload
    //   435: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   438: checkcast androidx/constraintlayout/widget/ConstraintAttribute
    //   441: aload_1
    //   442: aload_0
    //   443: getfield mValuesBuff : [F
    //   446: invokevirtual setInterpolatedValue : (Landroid/view/View;[F)V
    //   449: iinc #20, 1
    //   452: goto -> 391
    //   455: aload_0
    //   456: getfield mStartPoint : Landroidx/constraintlayout/motion/widget/MotionConstrainedPoint;
    //   459: getfield mVisibilityMode : I
    //   462: ifne -> 527
    //   465: fload_2
    //   466: fconst_0
    //   467: fcmpg
    //   468: ifgt -> 485
    //   471: aload_1
    //   472: aload_0
    //   473: getfield mStartPoint : Landroidx/constraintlayout/motion/widget/MotionConstrainedPoint;
    //   476: getfield visibility : I
    //   479: invokevirtual setVisibility : (I)V
    //   482: goto -> 527
    //   485: fload_2
    //   486: fconst_1
    //   487: fcmpl
    //   488: iflt -> 505
    //   491: aload_1
    //   492: aload_0
    //   493: getfield mEndPoint : Landroidx/constraintlayout/motion/widget/MotionConstrainedPoint;
    //   496: getfield visibility : I
    //   499: invokevirtual setVisibility : (I)V
    //   502: goto -> 527
    //   505: aload_0
    //   506: getfield mEndPoint : Landroidx/constraintlayout/motion/widget/MotionConstrainedPoint;
    //   509: getfield visibility : I
    //   512: aload_0
    //   513: getfield mStartPoint : Landroidx/constraintlayout/motion/widget/MotionConstrainedPoint;
    //   516: getfield visibility : I
    //   519: if_icmpeq -> 527
    //   522: aload_1
    //   523: iconst_0
    //   524: invokevirtual setVisibility : (I)V
    //   527: iload #24
    //   529: istore #25
    //   531: aload_0
    //   532: getfield mKeyTriggers : [Landroidx/constraintlayout/motion/widget/KeyTrigger;
    //   535: ifnull -> 835
    //   538: iconst_0
    //   539: istore #20
    //   541: aload_0
    //   542: getfield mKeyTriggers : [Landroidx/constraintlayout/motion/widget/KeyTrigger;
    //   545: astore #5
    //   547: iload #24
    //   549: istore #25
    //   551: iload #20
    //   553: aload #5
    //   555: arraylength
    //   556: if_icmpge -> 835
    //   559: aload #5
    //   561: iload #20
    //   563: aaload
    //   564: fload_2
    //   565: aload_1
    //   566: invokevirtual conditionallyFire : (FLandroid/view/View;)V
    //   569: iinc #20, 1
    //   572: goto -> 541
    //   575: aload_0
    //   576: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   579: getfield x : F
    //   582: fstore #18
    //   584: aload_0
    //   585: getfield mEndMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   588: getfield x : F
    //   591: fstore #17
    //   593: aload_0
    //   594: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   597: getfield x : F
    //   600: fstore #19
    //   602: aload_0
    //   603: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   606: getfield y : F
    //   609: fstore #14
    //   611: aload_0
    //   612: getfield mEndMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   615: getfield y : F
    //   618: fstore #15
    //   620: aload_0
    //   621: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   624: getfield y : F
    //   627: fstore #16
    //   629: aload_0
    //   630: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   633: getfield width : F
    //   636: fstore #8
    //   638: aload_0
    //   639: getfield mEndMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   642: getfield width : F
    //   645: fstore #13
    //   647: aload_0
    //   648: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   651: getfield width : F
    //   654: fstore #12
    //   656: aload_0
    //   657: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   660: getfield height : F
    //   663: fstore #9
    //   665: aload_0
    //   666: getfield mEndMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   669: getfield height : F
    //   672: fstore #11
    //   674: aload_0
    //   675: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   678: getfield height : F
    //   681: fstore #10
    //   683: fload #18
    //   685: fload #17
    //   687: fload #19
    //   689: fsub
    //   690: fload_2
    //   691: fmul
    //   692: fadd
    //   693: ldc_w 0.5
    //   696: fadd
    //   697: fstore #17
    //   699: fload #17
    //   701: f2i
    //   702: istore #23
    //   704: fload #14
    //   706: fload #15
    //   708: fload #16
    //   710: fsub
    //   711: fload_2
    //   712: fmul
    //   713: fadd
    //   714: ldc_w 0.5
    //   717: fadd
    //   718: fstore #14
    //   720: fload #14
    //   722: f2i
    //   723: istore #20
    //   725: fload #17
    //   727: fload #8
    //   729: fload #13
    //   731: fload #12
    //   733: fsub
    //   734: fload_2
    //   735: fmul
    //   736: fadd
    //   737: fadd
    //   738: f2i
    //   739: istore #22
    //   741: fload #14
    //   743: fload #9
    //   745: fload #11
    //   747: fload #10
    //   749: fsub
    //   750: fload_2
    //   751: fmul
    //   752: fadd
    //   753: fadd
    //   754: f2i
    //   755: istore #21
    //   757: aload_0
    //   758: getfield mEndMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   761: getfield width : F
    //   764: aload_0
    //   765: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   768: getfield width : F
    //   771: fcmpl
    //   772: ifne -> 793
    //   775: aload_0
    //   776: getfield mEndMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   779: getfield height : F
    //   782: aload_0
    //   783: getfield mStartMotionPath : Landroidx/constraintlayout/motion/widget/MotionPaths;
    //   786: getfield height : F
    //   789: fcmpl
    //   790: ifeq -> 819
    //   793: aload_1
    //   794: iload #22
    //   796: iload #23
    //   798: isub
    //   799: ldc_w 1073741824
    //   802: invokestatic makeMeasureSpec : (II)I
    //   805: iload #21
    //   807: iload #20
    //   809: isub
    //   810: ldc_w 1073741824
    //   813: invokestatic makeMeasureSpec : (II)I
    //   816: invokevirtual measure : (II)V
    //   819: aload_1
    //   820: iload #23
    //   822: iload #20
    //   824: iload #22
    //   826: iload #21
    //   828: invokevirtual layout : (IIII)V
    //   831: iload #24
    //   833: istore #25
    //   835: aload_0
    //   836: getfield mCycleMap : Ljava/util/HashMap;
    //   839: astore #5
    //   841: aload #5
    //   843: ifnull -> 929
    //   846: aload #5
    //   848: invokevirtual values : ()Ljava/util/Collection;
    //   851: invokeinterface iterator : ()Ljava/util/Iterator;
    //   856: astore #5
    //   858: aload #5
    //   860: invokeinterface hasNext : ()Z
    //   865: ifeq -> 929
    //   868: aload #5
    //   870: invokeinterface next : ()Ljava/lang/Object;
    //   875: checkcast androidx/constraintlayout/motion/widget/KeyCycleOscillator
    //   878: astore #26
    //   880: aload #26
    //   882: instanceof androidx/constraintlayout/motion/widget/KeyCycleOscillator$PathRotateSet
    //   885: ifeq -> 919
    //   888: aload #26
    //   890: checkcast androidx/constraintlayout/motion/widget/KeyCycleOscillator$PathRotateSet
    //   893: astore #27
    //   895: aload_0
    //   896: getfield mInterpolateVelocity : [D
    //   899: astore #26
    //   901: aload #27
    //   903: aload_1
    //   904: fload_2
    //   905: aload #26
    //   907: iconst_0
    //   908: daload
    //   909: aload #26
    //   911: iconst_1
    //   912: daload
    //   913: invokevirtual setPathRotate : (Landroid/view/View;FDD)V
    //   916: goto -> 858
    //   919: aload #26
    //   921: aload_1
    //   922: fload_2
    //   923: invokevirtual setProperty : (Landroid/view/View;F)V
    //   926: goto -> 858
    //   929: iload #25
    //   931: ireturn
  }
  
  String name() {
    return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
  }
  
  void positionKeyframe(View paramView, KeyPositionBase paramKeyPositionBase, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOffloat) {
    RectF rectF1 = new RectF();
    rectF1.left = this.mStartMotionPath.x;
    rectF1.top = this.mStartMotionPath.y;
    rectF1.right = rectF1.left + this.mStartMotionPath.width;
    rectF1.bottom = rectF1.top + this.mStartMotionPath.height;
    RectF rectF2 = new RectF();
    rectF2.left = this.mEndMotionPath.x;
    rectF2.top = this.mEndMotionPath.y;
    rectF2.right = rectF2.left + this.mEndMotionPath.width;
    rectF2.bottom = rectF2.top + this.mEndMotionPath.height;
    paramKeyPositionBase.positionAttributes(paramView, rectF1, rectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOffloat);
  }
  
  public void setDrawPath(int paramInt) {
    this.mStartMotionPath.mDrawPath = paramInt;
  }
  
  void setEndState(ConstraintWidget paramConstraintWidget, ConstraintSet paramConstraintSet) {
    this.mEndMotionPath.time = 1.0F;
    this.mEndMotionPath.position = 1.0F;
    readView(this.mEndMotionPath);
    this.mEndMotionPath.setBounds(paramConstraintWidget.getX(), paramConstraintWidget.getY(), paramConstraintWidget.getWidth(), paramConstraintWidget.getHeight());
    this.mEndMotionPath.applyParameters(paramConstraintSet.getParameters(this.mId));
    this.mEndPoint.setState(paramConstraintWidget, paramConstraintSet, this.mId);
  }
  
  public void setPathMotionArc(int paramInt) {
    this.mPathMotionArc = paramInt;
  }
  
  void setStartCurrentState(View paramView) {
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    this.mStartMotionPath.setBounds(paramView.getX(), paramView.getY(), paramView.getWidth(), paramView.getHeight());
    this.mStartPoint.setState(paramView);
  }
  
  void setStartState(ConstraintWidget paramConstraintWidget, ConstraintSet paramConstraintSet) {
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    readView(this.mStartMotionPath);
    this.mStartMotionPath.setBounds(paramConstraintWidget.getX(), paramConstraintWidget.getY(), paramConstraintWidget.getWidth(), paramConstraintWidget.getHeight());
    ConstraintSet.Constraint constraint = paramConstraintSet.getParameters(this.mId);
    this.mStartMotionPath.applyParameters(constraint);
    this.mMotionStagger = constraint.motion.mMotionStagger;
    this.mStartPoint.setState(paramConstraintWidget, paramConstraintSet, this.mId);
  }
  
  public void setView(View paramView) {
    this.mView = paramView;
    this.mId = paramView.getId();
    ViewGroup.LayoutParams layoutParams = paramView.getLayoutParams();
    if (layoutParams instanceof ConstraintLayout.LayoutParams)
      this.mConstraintTag = ((ConstraintLayout.LayoutParams)layoutParams).getConstraintTag(); 
  }
  
  public void setup(int paramInt1, int paramInt2, float paramFloat, long paramLong) {
    ArrayList arrayList1;
    new HashSet();
    HashSet<String> hashSet4 = new HashSet();
    HashSet<String> hashSet1 = new HashSet();
    HashSet<String> hashSet3 = new HashSet();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (this.mPathMotionArc != Key.UNSET)
      this.mStartMotionPath.mPathMotionArc = this.mPathMotionArc; 
    this.mStartPoint.different(this.mEndPoint, hashSet1);
    ArrayList<Key> arrayList = this.mKeyList;
    if (arrayList != null) {
      Iterator<Key> iterator = arrayList.iterator();
      arrayList = null;
      while (true) {
        arrayList1 = arrayList;
        if (iterator.hasNext()) {
          Key key = iterator.next();
          if (key instanceof KeyPosition) {
            KeyPosition keyPosition = (KeyPosition)key;
            insertKey(new MotionPaths(paramInt1, paramInt2, keyPosition, this.mStartMotionPath, this.mEndMotionPath));
            if (keyPosition.mCurveFit != Key.UNSET)
              this.mCurveFitType = keyPosition.mCurveFit; 
            continue;
          } 
          if (key instanceof KeyCycle) {
            key.getAttributeNames(hashSet3);
            continue;
          } 
          if (key instanceof KeyTimeCycle) {
            key.getAttributeNames(hashSet4);
            continue;
          } 
          if (key instanceof KeyTrigger) {
            arrayList1 = arrayList;
            if (arrayList == null)
              arrayList1 = new ArrayList<Key>(); 
            arrayList1.add(key);
            arrayList = arrayList1;
            continue;
          } 
          key.setInterpolation((HashMap)hashMap);
          key.getAttributeNames(hashSet1);
          continue;
        } 
        break;
      } 
    } else {
      arrayList1 = null;
    } 
    if (arrayList1 != null)
      this.mKeyTriggers = (KeyTrigger[])arrayList1.toArray((Object[])new KeyTrigger[0]); 
    if (!hashSet1.isEmpty()) {
      this.mAttributesMap = new HashMap<String, SplineSet>();
      for (String str : hashSet1) {
        SplineSet splineSet;
        if (str.startsWith("CUSTOM,")) {
          SparseArray<ConstraintAttribute> sparseArray = new SparseArray();
          String str1 = str.split(",")[1];
          for (Key key : this.mKeyList) {
            if (key.mCustomConstraints == null)
              continue; 
            ConstraintAttribute constraintAttribute = key.mCustomConstraints.get(str1);
            if (constraintAttribute != null)
              sparseArray.append(key.mFramePosition, constraintAttribute); 
          } 
          splineSet = SplineSet.makeCustomSpline(str, sparseArray);
        } else {
          splineSet = SplineSet.makeSpline(str);
        } 
        if (splineSet == null)
          continue; 
        splineSet.setType(str);
        this.mAttributesMap.put(str, splineSet);
      } 
      arrayList = this.mKeyList;
      if (arrayList != null)
        for (Key key : arrayList) {
          if (key instanceof KeyAttributes)
            key.addValues(this.mAttributesMap); 
        }  
      this.mStartPoint.addValues(this.mAttributesMap, 0);
      this.mEndPoint.addValues(this.mAttributesMap, 100);
      for (String str : this.mAttributesMap.keySet()) {
        if (hashMap.containsKey(str)) {
          paramInt1 = ((Integer)hashMap.get(str)).intValue();
        } else {
          paramInt1 = 0;
        } 
        ((SplineSet)this.mAttributesMap.get(str)).setup(paramInt1);
      } 
    } 
    if (!hashSet4.isEmpty()) {
      if (this.mTimeCycleAttributesMap == null)
        this.mTimeCycleAttributesMap = new HashMap<String, TimeCycleSplineSet>(); 
      for (String str : hashSet4) {
        TimeCycleSplineSet timeCycleSplineSet;
        if (this.mTimeCycleAttributesMap.containsKey(str))
          continue; 
        if (str.startsWith("CUSTOM,")) {
          SparseArray<ConstraintAttribute> sparseArray = new SparseArray();
          String str1 = str.split(",")[1];
          for (Key key : this.mKeyList) {
            if (key.mCustomConstraints == null)
              continue; 
            ConstraintAttribute constraintAttribute = key.mCustomConstraints.get(str1);
            if (constraintAttribute != null)
              sparseArray.append(key.mFramePosition, constraintAttribute); 
          } 
          timeCycleSplineSet = TimeCycleSplineSet.makeCustomSpline(str, sparseArray);
        } else {
          timeCycleSplineSet = TimeCycleSplineSet.makeSpline(str, paramLong);
        } 
        if (timeCycleSplineSet == null)
          continue; 
        timeCycleSplineSet.setType(str);
        this.mTimeCycleAttributesMap.put(str, timeCycleSplineSet);
      } 
      arrayList = this.mKeyList;
      if (arrayList != null)
        for (Key key : arrayList) {
          if (key instanceof KeyTimeCycle)
            ((KeyTimeCycle)key).addTimeValues(this.mTimeCycleAttributesMap); 
        }  
      for (String str : this.mTimeCycleAttributesMap.keySet()) {
        if (hashMap.containsKey(str)) {
          paramInt1 = ((Integer)hashMap.get(str)).intValue();
        } else {
          paramInt1 = 0;
        } 
        ((TimeCycleSplineSet)this.mTimeCycleAttributesMap.get(str)).setup(paramInt1);
      } 
    } 
    int i = this.mMotionPaths.size() + 2;
    MotionPaths[] arrayOfMotionPaths = new MotionPaths[i];
    arrayOfMotionPaths[0] = this.mStartMotionPath;
    arrayOfMotionPaths[i - 1] = this.mEndMotionPath;
    if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1)
      this.mCurveFitType = 0; 
    null = this.mMotionPaths.iterator();
    for (paramInt1 = 1; null.hasNext(); paramInt1++)
      arrayOfMotionPaths[paramInt1] = null.next(); 
    HashSet<String> hashSet2 = new HashSet();
    for (String str : this.mEndMotionPath.attributes.keySet()) {
      if (this.mStartMotionPath.attributes.containsKey(str)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CUSTOM,");
        stringBuilder.append(str);
        if (!hashSet1.contains(stringBuilder.toString()))
          hashSet2.add(str); 
      } 
    } 
    String[] arrayOfString = (String[])hashSet2.toArray((Object[])new String[0]);
    this.mAttributeNames = arrayOfString;
    this.mAttributeInterpCount = new int[arrayOfString.length];
    paramInt1 = 0;
    while (true) {
      boolean bool;
      arrayOfString = this.mAttributeNames;
      if (paramInt1 < arrayOfString.length) {
        String str = arrayOfString[paramInt1];
        this.mAttributeInterpCount[paramInt1] = 0;
        for (paramInt2 = 0; paramInt2 < i; paramInt2++) {
          if ((arrayOfMotionPaths[paramInt2]).attributes.containsKey(str)) {
            int[] arrayOfInt1 = this.mAttributeInterpCount;
            arrayOfInt1[paramInt1] = arrayOfInt1[paramInt1] + ((ConstraintAttribute)(arrayOfMotionPaths[paramInt2]).attributes.get(str)).noOfInterpValues();
            break;
          } 
        } 
        paramInt1++;
        continue;
      } 
      if ((arrayOfMotionPaths[0]).mPathMotionArc != Key.UNSET) {
        bool = true;
      } else {
        bool = false;
      } 
      int k = 18 + this.mAttributeNames.length;
      boolean[] arrayOfBoolean = new boolean[k];
      for (paramInt1 = 1; paramInt1 < i; paramInt1++)
        arrayOfMotionPaths[paramInt1].different(arrayOfMotionPaths[paramInt1 - 1], arrayOfBoolean, this.mAttributeNames, bool); 
      paramInt1 = 1;
      for (paramInt2 = 0; paramInt1 < k; paramInt2 = m) {
        int m = paramInt2;
        if (arrayOfBoolean[paramInt1])
          m = paramInt2 + 1; 
        paramInt1++;
      } 
      int[] arrayOfInt = new int[paramInt2];
      this.mInterpolateVariables = arrayOfInt;
      this.mInterpolateData = new double[arrayOfInt.length];
      this.mInterpolateVelocity = new double[arrayOfInt.length];
      paramInt1 = 1;
      int j;
      for (j = 0; paramInt1 < k; j = paramInt2) {
        paramInt2 = j;
        if (arrayOfBoolean[paramInt1]) {
          this.mInterpolateVariables[j] = paramInt1;
          paramInt2 = j + 1;
        } 
        paramInt1++;
      } 
      double[][] arrayOfDouble = new double[i][this.mInterpolateVariables.length];
      double[] arrayOfDouble1 = new double[i];
      for (paramInt1 = 0; paramInt1 < i; paramInt1++) {
        arrayOfMotionPaths[paramInt1].fillStandard(arrayOfDouble[paramInt1], this.mInterpolateVariables);
        arrayOfDouble1[paramInt1] = (arrayOfMotionPaths[paramInt1]).time;
      } 
      paramInt1 = 0;
      while (true) {
        int[] arrayOfInt1 = this.mInterpolateVariables;
        if (paramInt1 < arrayOfInt1.length) {
          if (arrayOfInt1[paramInt1] < MotionPaths.names.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(MotionPaths.names[this.mInterpolateVariables[paramInt1]]);
            stringBuilder.append(" [");
            String str = stringBuilder.toString();
            for (paramInt2 = 0; paramInt2 < i; paramInt2++) {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append(str);
              stringBuilder1.append(arrayOfDouble[paramInt2][paramInt1]);
              str = stringBuilder1.toString();
            } 
          } 
          paramInt1++;
          continue;
        } 
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        paramInt1 = 0;
        while (true) {
          String[] arrayOfString1 = this.mAttributeNames;
          if (paramInt1 < arrayOfString1.length) {
            double[][] arrayOfDouble2 = (double[][])null;
            String str = arrayOfString1[paramInt1];
            arrayOfString1 = null;
            j = 0;
            for (k = 0; j < i; k = paramInt2) {
              double[] arrayOfDouble5;
              double[][] arrayOfDouble4 = arrayOfDouble2;
              String[] arrayOfString2 = arrayOfString1;
              paramInt2 = k;
              if (arrayOfMotionPaths[j].hasCustomData(str)) {
                double[] arrayOfDouble6;
                arrayOfDouble4 = arrayOfDouble2;
                if (arrayOfDouble2 == null) {
                  arrayOfDouble6 = new double[i];
                  arrayOfDouble4 = new double[i][arrayOfMotionPaths[j].getCustomDataCount(str)];
                } 
                arrayOfDouble6[k] = (arrayOfMotionPaths[j]).time;
                arrayOfMotionPaths[j].getCustomData(str, arrayOfDouble4[k], 0);
                paramInt2 = k + 1;
                arrayOfDouble5 = arrayOfDouble6;
              } 
              j++;
              arrayOfDouble2 = arrayOfDouble4;
              arrayOfDouble3 = arrayOfDouble5;
            } 
            double[] arrayOfDouble3 = Arrays.copyOf(arrayOfDouble3, k);
            arrayOfDouble2 = Arrays.<double[]>copyOf(arrayOfDouble2, k);
            CurveFit[] arrayOfCurveFit = this.mSpline;
            arrayOfCurveFit[++paramInt1] = CurveFit.get(this.mCurveFitType, arrayOfDouble3, arrayOfDouble2);
            continue;
          } 
          this.mSpline[0] = CurveFit.get(this.mCurveFitType, arrayOfDouble1, arrayOfDouble);
          if ((arrayOfMotionPaths[0]).mPathMotionArc != Key.UNSET) {
            int[] arrayOfInt2 = new int[i];
            double[] arrayOfDouble2 = new double[i];
            double[][] arrayOfDouble3 = new double[i][2];
            for (paramInt1 = 0; paramInt1 < i; paramInt1++) {
              arrayOfInt2[paramInt1] = (arrayOfMotionPaths[paramInt1]).mPathMotionArc;
              arrayOfDouble2[paramInt1] = (arrayOfMotionPaths[paramInt1]).time;
              arrayOfDouble3[paramInt1][0] = (arrayOfMotionPaths[paramInt1]).x;
              arrayOfDouble3[paramInt1][1] = (arrayOfMotionPaths[paramInt1]).y;
            } 
            this.mArcSpline = CurveFit.getArc(arrayOfInt2, arrayOfDouble2, arrayOfDouble3);
          } 
          float f = Float.NaN;
          this.mCycleMap = new HashMap<String, KeyCycleOscillator>();
          if (this.mKeyList != null) {
            for (String str : hashSet3) {
              KeyCycleOscillator keyCycleOscillator = KeyCycleOscillator.makeSpline(str);
              if (keyCycleOscillator == null)
                continue; 
              paramFloat = f;
              if (keyCycleOscillator.variesByPath()) {
                paramFloat = f;
                if (Float.isNaN(f))
                  paramFloat = getPreCycleDistance(); 
              } 
              keyCycleOscillator.setType(str);
              this.mCycleMap.put(str, keyCycleOscillator);
              f = paramFloat;
            } 
            for (Key key : this.mKeyList) {
              if (key instanceof KeyCycle)
                ((KeyCycle)key).addCycleValues(this.mCycleMap); 
            } 
            Iterator<KeyCycleOscillator> iterator = this.mCycleMap.values().iterator();
            while (iterator.hasNext())
              ((KeyCycleOscillator)iterator.next()).setup(f); 
          } 
          return;
        } 
        break;
      } 
      break;
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(" start: x: ");
    stringBuilder.append(this.mStartMotionPath.x);
    stringBuilder.append(" y: ");
    stringBuilder.append(this.mStartMotionPath.y);
    stringBuilder.append(" end: x: ");
    stringBuilder.append(this.mEndMotionPath.x);
    stringBuilder.append(" y: ");
    stringBuilder.append(this.mEndMotionPath.y);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\MotionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */