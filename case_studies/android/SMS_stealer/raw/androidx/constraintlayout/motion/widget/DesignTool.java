package androidx.constraintlayout.motion.widget;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.io.PrintStream;
import java.util.HashMap;

public class DesignTool implements ProxyInterface {
  private static final boolean DEBUG = false;
  
  private static final String TAG = "DesignTool";
  
  static final HashMap<Pair<Integer, Integer>, String> allAttributes = new HashMap<Pair<Integer, Integer>, String>();
  
  static final HashMap<String, String> allMargins = new HashMap<String, String>();
  
  private String mLastEndState = null;
  
  private int mLastEndStateId = -1;
  
  private String mLastStartState = null;
  
  private int mLastStartStateId = -1;
  
  private final MotionLayout mMotionLayout;
  
  private MotionScene mSceneCache;
  
  static {
    HashMap<Pair<Integer, Integer>, String> hashMap = allAttributes;
    Integer integer1 = Integer.valueOf(4);
    hashMap.put(Pair.create(integer1, integer1), "layout_constraintBottom_toBottomOf");
    hashMap = allAttributes;
    Integer integer2 = Integer.valueOf(3);
    hashMap.put(Pair.create(integer1, integer2), "layout_constraintBottom_toTopOf");
    allAttributes.put(Pair.create(integer2, integer1), "layout_constraintTop_toBottomOf");
    allAttributes.put(Pair.create(integer2, integer2), "layout_constraintTop_toTopOf");
    hashMap = allAttributes;
    integer1 = Integer.valueOf(6);
    hashMap.put(Pair.create(integer1, integer1), "layout_constraintStart_toStartOf");
    hashMap = allAttributes;
    integer2 = Integer.valueOf(7);
    hashMap.put(Pair.create(integer1, integer2), "layout_constraintStart_toEndOf");
    allAttributes.put(Pair.create(integer2, integer1), "layout_constraintEnd_toStartOf");
    allAttributes.put(Pair.create(integer2, integer2), "layout_constraintEnd_toEndOf");
    hashMap = allAttributes;
    integer1 = Integer.valueOf(1);
    hashMap.put(Pair.create(integer1, integer1), "layout_constraintLeft_toLeftOf");
    hashMap = allAttributes;
    integer2 = Integer.valueOf(2);
    hashMap.put(Pair.create(integer1, integer2), "layout_constraintLeft_toRightOf");
    allAttributes.put(Pair.create(integer2, integer2), "layout_constraintRight_toRightOf");
    allAttributes.put(Pair.create(integer2, integer1), "layout_constraintRight_toLeftOf");
    hashMap = allAttributes;
    integer1 = Integer.valueOf(5);
    hashMap.put(Pair.create(integer1, integer1), "layout_constraintBaseline_toBaselineOf");
    allMargins.put("layout_constraintBottom_toBottomOf", "layout_marginBottom");
    allMargins.put("layout_constraintBottom_toTopOf", "layout_marginBottom");
    allMargins.put("layout_constraintTop_toBottomOf", "layout_marginTop");
    allMargins.put("layout_constraintTop_toTopOf", "layout_marginTop");
    allMargins.put("layout_constraintStart_toStartOf", "layout_marginStart");
    allMargins.put("layout_constraintStart_toEndOf", "layout_marginStart");
    allMargins.put("layout_constraintEnd_toStartOf", "layout_marginEnd");
    allMargins.put("layout_constraintEnd_toEndOf", "layout_marginEnd");
    allMargins.put("layout_constraintLeft_toLeftOf", "layout_marginLeft");
    allMargins.put("layout_constraintLeft_toRightOf", "layout_marginLeft");
    allMargins.put("layout_constraintRight_toRightOf", "layout_marginRight");
    allMargins.put("layout_constraintRight_toLeftOf", "layout_marginRight");
  }
  
  public DesignTool(MotionLayout paramMotionLayout) {
    this.mMotionLayout = paramMotionLayout;
  }
  
  private static void Connect(int paramInt1, ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap, int paramInt2, int paramInt3) {
    String str2 = allAttributes.get(Pair.create(Integer.valueOf(paramInt2), Integer.valueOf(paramInt3)));
    String str1 = paramHashMap.get(str2);
    if (str1 != null) {
      str2 = allMargins.get(str2);
      if (str2 != null) {
        paramInt1 = GetPxFromDp(paramInt1, paramHashMap.get(str2));
      } else {
        paramInt1 = 0;
      } 
      int i = Integer.parseInt(str1);
      paramConstraintSet.connect(paramView.getId(), paramInt2, i, paramInt3, paramInt1);
    } 
  }
  
  private static int GetPxFromDp(int paramInt, String paramString) {
    if (paramString == null)
      return 0; 
    int i = paramString.indexOf('d');
    return (i == -1) ? 0 : (int)((Integer.valueOf(paramString.substring(0, i)).intValue() * paramInt) / 160.0F);
  }
  
  private static void SetAbsolutePositions(int paramInt, ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap) {
    String str2 = paramHashMap.get("layout_editor_absoluteX");
    if (str2 != null)
      paramConstraintSet.setEditorAbsoluteX(paramView.getId(), GetPxFromDp(paramInt, str2)); 
    String str1 = paramHashMap.get("layout_editor_absoluteY");
    if (str1 != null)
      paramConstraintSet.setEditorAbsoluteY(paramView.getId(), GetPxFromDp(paramInt, str1)); 
  }
  
  private static void SetBias(ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap, int paramInt) {
    String str2;
    if (paramInt == 1) {
      str2 = "layout_constraintVertical_bias";
    } else {
      str2 = "layout_constraintHorizontal_bias";
    } 
    String str1 = paramHashMap.get(str2);
    if (str1 != null)
      if (paramInt == 0) {
        paramConstraintSet.setHorizontalBias(paramView.getId(), Float.parseFloat(str1));
      } else if (paramInt == 1) {
        paramConstraintSet.setVerticalBias(paramView.getId(), Float.parseFloat(str1));
      }  
  }
  
  private static void SetDimensions(int paramInt1, ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap, int paramInt2) {
    String str2;
    if (paramInt2 == 1) {
      str2 = "layout_height";
    } else {
      str2 = "layout_width";
    } 
    String str1 = paramHashMap.get(str2);
    if (str1 != null) {
      int i = -2;
      if (!str1.equalsIgnoreCase("wrap_content"))
        i = GetPxFromDp(paramInt1, str1); 
      if (paramInt2 == 0) {
        paramConstraintSet.constrainWidth(paramView.getId(), i);
      } else {
        paramConstraintSet.constrainHeight(paramView.getId(), i);
      } 
    } 
  }
  
  public int designAccess(int paramInt1, String paramString, Object paramObject, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3) {
    paramObject = paramObject;
    if (paramInt1 != 0) {
      if (this.mMotionLayout.mScene == null)
        return -1; 
      if (paramObject != null) {
        MotionController motionController = this.mMotionLayout.mFrameArrayList.get(paramObject);
        paramObject = motionController;
        if (motionController == null)
          return -1; 
      } else {
        return -1;
      } 
    } else {
      paramObject = null;
    } 
    if (paramInt1 != 0) {
      if (paramInt1 != 1) {
        if (paramInt1 != 2) {
          if (paramInt1 != 3)
            return -1; 
          paramInt1 = this.mMotionLayout.mScene.getDuration() / 16;
          return paramObject.getAttributeValues(paramString, paramArrayOffloat2, paramInt3);
        } 
        paramInt1 = this.mMotionLayout.mScene.getDuration() / 16;
        paramObject.buildKeyFrames(paramArrayOffloat2, null);
        return paramInt1;
      } 
      paramInt1 = this.mMotionLayout.mScene.getDuration() / 16;
      paramObject.buildPath(paramArrayOffloat2, paramInt1);
      return paramInt1;
    } 
    return 1;
  }
  
  public void disableAutoTransition(boolean paramBoolean) {
    this.mMotionLayout.disableAutoTransition(paramBoolean);
  }
  
  public void dumpConstraintSet(String paramString) {
    if (this.mMotionLayout.mScene == null)
      this.mMotionLayout.mScene = this.mSceneCache; 
    int i = this.mMotionLayout.lookUpConstraintId(paramString);
    PrintStream printStream = System.out;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(" dumping  ");
    stringBuilder.append(paramString);
    stringBuilder.append(" (");
    stringBuilder.append(i);
    stringBuilder.append(")");
    printStream.println(stringBuilder.toString());
    try {
      this.mMotionLayout.mScene.getConstraintSet(i).dump(this.mMotionLayout.mScene, new int[0]);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public int getAnimationKeyFrames(Object paramObject, float[] paramArrayOffloat) {
    if (this.mMotionLayout.mScene == null)
      return -1; 
    int i = this.mMotionLayout.mScene.getDuration() / 16;
    paramObject = this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject == null)
      return 0; 
    paramObject.buildKeyFrames(paramArrayOffloat, null);
    return i;
  }
  
  public int getAnimationPath(Object paramObject, float[] paramArrayOffloat, int paramInt) {
    if (this.mMotionLayout.mScene == null)
      return -1; 
    paramObject = this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject == null)
      return 0; 
    paramObject.buildPath(paramArrayOffloat, paramInt);
    return paramInt;
  }
  
  public void getAnimationRectangles(Object paramObject, float[] paramArrayOffloat) {
    if (this.mMotionLayout.mScene == null)
      return; 
    int i = this.mMotionLayout.mScene.getDuration() / 16;
    paramObject = this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject == null)
      return; 
    paramObject.buildRectangles(paramArrayOffloat, i);
  }
  
  public String getEndState() {
    int i = this.mMotionLayout.getEndState();
    if (this.mLastEndStateId == i)
      return this.mLastEndState; 
    String str = this.mMotionLayout.getConstraintSetNames(i);
    if (str != null) {
      this.mLastEndState = str;
      this.mLastEndStateId = i;
    } 
    return str;
  }
  
  public int getKeyFrameInfo(Object paramObject, int paramInt, int[] paramArrayOfint) {
    paramObject = this.mMotionLayout.mFrameArrayList.get(paramObject);
    return (paramObject == null) ? 0 : paramObject.getKeyFrameInfo(paramInt, paramArrayOfint);
  }
  
  public float getKeyFramePosition(Object paramObject, int paramInt, float paramFloat1, float paramFloat2) {
    return ((MotionController)this.mMotionLayout.mFrameArrayList.get(paramObject)).getKeyFrameParameter(paramInt, paramFloat1, paramFloat2);
  }
  
  public int getKeyFramePositions(Object paramObject, int[] paramArrayOfint, float[] paramArrayOffloat) {
    paramObject = this.mMotionLayout.mFrameArrayList.get(paramObject);
    return (paramObject == null) ? 0 : paramObject.getkeyFramePositions(paramArrayOfint, paramArrayOffloat);
  }
  
  public Object getKeyframe(int paramInt1, int paramInt2, int paramInt3) {
    return (this.mMotionLayout.mScene == null) ? null : this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), paramInt1, paramInt2, paramInt3);
  }
  
  public Object getKeyframe(Object paramObject, int paramInt1, int paramInt2) {
    if (this.mMotionLayout.mScene == null)
      return null; 
    int i = ((View)paramObject).getId();
    return this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), paramInt1, i, paramInt2);
  }
  
  public Object getKeyframeAtLocation(Object paramObject, float paramFloat1, float paramFloat2) {
    View view = (View)paramObject;
    if (this.mMotionLayout.mScene == null)
      return Integer.valueOf(-1); 
    if (view != null) {
      paramObject = this.mMotionLayout.mFrameArrayList.get(view);
      if (paramObject == null)
        return null; 
      ViewGroup viewGroup = (ViewGroup)view.getParent();
      return paramObject.getPositionKeyframe(viewGroup.getWidth(), viewGroup.getHeight(), paramFloat1, paramFloat2);
    } 
    return null;
  }
  
  public Boolean getPositionKeyframe(Object paramObject1, Object paramObject2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOffloat) {
    if (paramObject1 instanceof KeyPositionBase) {
      paramObject1 = paramObject1;
      HashMap<View, MotionController> hashMap = this.mMotionLayout.mFrameArrayList;
      paramObject2 = paramObject2;
      ((MotionController)hashMap.get(paramObject2)).positionKeyframe((View)paramObject2, (KeyPositionBase)paramObject1, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOffloat);
      this.mMotionLayout.rebuildScene();
      this.mMotionLayout.mInTransition = true;
      return Boolean.valueOf(true);
    } 
    return Boolean.valueOf(false);
  }
  
  public float getProgress() {
    return this.mMotionLayout.getProgress();
  }
  
  public String getStartState() {
    int i = this.mMotionLayout.getStartState();
    if (this.mLastStartStateId == i)
      return this.mLastStartState; 
    String str = this.mMotionLayout.getConstraintSetNames(i);
    if (str != null) {
      this.mLastStartState = str;
      this.mLastStartStateId = i;
    } 
    return this.mMotionLayout.getConstraintSetNames(i);
  }
  
  public String getState() {
    if (this.mLastStartState != null && this.mLastEndState != null) {
      float f = getProgress();
      if (f <= 0.01F)
        return this.mLastStartState; 
      if (f >= 0.99F)
        return this.mLastEndState; 
    } 
    return this.mLastStartState;
  }
  
  public long getTransitionTimeMs() {
    return this.mMotionLayout.getTransitionTimeMs();
  }
  
  public boolean isInTransition() {
    boolean bool;
    if (this.mLastStartState != null && this.mLastEndState != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void setAttributes(int paramInt, String paramString, Object paramObject1, Object paramObject2) {
    paramObject1 = paramObject1;
    paramObject2 = paramObject2;
    int i = this.mMotionLayout.lookUpConstraintId(paramString);
    ConstraintSet constraintSet = this.mMotionLayout.mScene.getConstraintSet(i);
    if (constraintSet == null)
      return; 
    constraintSet.clear(paramObject1.getId());
    SetDimensions(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 0);
    SetDimensions(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 1);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 6, 6);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 6, 7);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 7, 7);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 7, 6);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 1, 1);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 1, 2);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 2, 2);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 2, 1);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 3, 3);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 3, 4);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 4, 3);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 4, 4);
    Connect(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 5, 5);
    SetBias(constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 0);
    SetBias(constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2, 1);
    SetAbsolutePositions(paramInt, constraintSet, (View)paramObject1, (HashMap<String, String>)paramObject2);
    this.mMotionLayout.updateState(i, constraintSet);
    this.mMotionLayout.requestLayout();
  }
  
  public void setKeyFrame(Object paramObject1, int paramInt, String paramString, Object paramObject2) {
    if (this.mMotionLayout.mScene != null) {
      this.mMotionLayout.mScene.setKeyframe((View)paramObject1, paramInt, paramString, paramObject2);
      this.mMotionLayout.mTransitionGoalPosition = paramInt / 100.0F;
      this.mMotionLayout.mTransitionLastPosition = 0.0F;
      this.mMotionLayout.rebuildScene();
      this.mMotionLayout.evaluate(true);
    } 
  }
  
  public boolean setKeyFramePosition(Object paramObject, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
    if (this.mMotionLayout.mScene != null) {
      MotionController motionController = this.mMotionLayout.mFrameArrayList.get(paramObject);
      paramInt1 = (int)(this.mMotionLayout.mTransitionPosition * 100.0F);
      if (motionController != null) {
        MotionScene motionScene = this.mMotionLayout.mScene;
        paramObject = paramObject;
        if (motionScene.hasKeyFramePosition((View)paramObject, paramInt1)) {
          float f = motionController.getKeyFrameParameter(2, paramFloat1, paramFloat2);
          paramFloat1 = motionController.getKeyFrameParameter(5, paramFloat1, paramFloat2);
          this.mMotionLayout.mScene.setKeyframe((View)paramObject, paramInt1, "motion:percentX", Float.valueOf(f));
          this.mMotionLayout.mScene.setKeyframe((View)paramObject, paramInt1, "motion:percentY", Float.valueOf(paramFloat1));
          this.mMotionLayout.rebuildScene();
          this.mMotionLayout.evaluate(true);
          this.mMotionLayout.invalidate();
          return true;
        } 
      } 
    } 
    return false;
  }
  
  public void setKeyframe(Object paramObject1, String paramString, Object paramObject2) {
    if (paramObject1 instanceof Key) {
      ((Key)paramObject1).setValue(paramString, paramObject2);
      this.mMotionLayout.rebuildScene();
      this.mMotionLayout.mInTransition = true;
    } 
  }
  
  public void setState(String paramString) {
    int i;
    String str = paramString;
    if (paramString == null)
      str = "motion_base"; 
    if (this.mLastStartState == str)
      return; 
    this.mLastStartState = str;
    this.mLastEndState = null;
    if (this.mMotionLayout.mScene == null)
      this.mMotionLayout.mScene = this.mSceneCache; 
    if (str != null) {
      i = this.mMotionLayout.lookUpConstraintId(str);
    } else {
      i = R.id.motion_base;
    } 
    this.mLastStartStateId = i;
    if (i != 0)
      if (i == this.mMotionLayout.getStartState()) {
        this.mMotionLayout.setProgress(0.0F);
      } else if (i == this.mMotionLayout.getEndState()) {
        this.mMotionLayout.setProgress(1.0F);
      } else {
        this.mMotionLayout.transitionToState(i);
        this.mMotionLayout.setProgress(1.0F);
      }  
    this.mMotionLayout.requestLayout();
  }
  
  public void setToolPosition(float paramFloat) {
    if (this.mMotionLayout.mScene == null)
      this.mMotionLayout.mScene = this.mSceneCache; 
    this.mMotionLayout.setProgress(paramFloat);
    this.mMotionLayout.evaluate(true);
    this.mMotionLayout.requestLayout();
    this.mMotionLayout.invalidate();
  }
  
  public void setTransition(String paramString1, String paramString2) {
    if (this.mMotionLayout.mScene == null)
      this.mMotionLayout.mScene = this.mSceneCache; 
    int i = this.mMotionLayout.lookUpConstraintId(paramString1);
    int j = this.mMotionLayout.lookUpConstraintId(paramString2);
    this.mMotionLayout.setTransition(i, j);
    this.mLastStartStateId = i;
    this.mLastEndStateId = j;
    this.mLastStartState = paramString1;
    this.mLastEndState = paramString2;
  }
  
  public void setViewDebug(Object paramObject, int paramInt) {
    paramObject = this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject != null) {
      paramObject.setDrawPath(paramInt);
      this.mMotionLayout.invalidate();
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\DesignTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */