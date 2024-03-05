package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R;
import androidx.core.widget.NestedScrollView;
import org.xmlpull.v1.XmlPullParser;

class TouchResponse {
  private static final boolean DEBUG = false;
  
  static final int FLAG_DISABLE_POST_SCROLL = 1;
  
  static final int FLAG_DISABLE_SCROLL = 2;
  
  private static final int SIDE_BOTTOM = 3;
  
  private static final int SIDE_END = 6;
  
  private static final int SIDE_LEFT = 1;
  
  private static final int SIDE_MIDDLE = 4;
  
  private static final int SIDE_RIGHT = 2;
  
  private static final int SIDE_START = 5;
  
  private static final int SIDE_TOP = 0;
  
  private static final String TAG = "TouchResponse";
  
  private static final float[][] TOUCH_DIRECTION;
  
  private static final int TOUCH_DOWN = 1;
  
  private static final int TOUCH_END = 5;
  
  private static final int TOUCH_LEFT = 2;
  
  private static final int TOUCH_RIGHT = 3;
  
  private static final float[][] TOUCH_SIDES = new float[][] { { 0.5F, 0.0F }, { 0.0F, 0.5F }, { 1.0F, 0.5F }, { 0.5F, 1.0F }, { 0.5F, 0.5F }, { 0.0F, 0.5F }, { 1.0F, 0.5F } };
  
  private static final int TOUCH_START = 4;
  
  private static final int TOUCH_UP = 0;
  
  private float[] mAnchorDpDt = new float[2];
  
  private float mDragScale = 1.0F;
  
  private boolean mDragStarted = false;
  
  private float mDragThreshold = 10.0F;
  
  private int mFlags = 0;
  
  private float mLastTouchX;
  
  private float mLastTouchY;
  
  private int mLimitBoundsTo = -1;
  
  private float mMaxAcceleration = 1.2F;
  
  private float mMaxVelocity = 4.0F;
  
  private final MotionLayout mMotionLayout;
  
  private boolean mMoveWhenScrollAtTop = true;
  
  private int mOnTouchUp = 0;
  
  private int mTouchAnchorId = -1;
  
  private int mTouchAnchorSide = 0;
  
  private float mTouchAnchorX = 0.5F;
  
  private float mTouchAnchorY = 0.5F;
  
  private float mTouchDirectionX = 0.0F;
  
  private float mTouchDirectionY = 1.0F;
  
  private int mTouchRegionId = -1;
  
  private int mTouchSide = 0;
  
  static {
    float[] arrayOfFloat = { 1.0F, 0.0F };
    TOUCH_DIRECTION = new float[][] { { 0.0F, -1.0F }, { 0.0F, 1.0F }, { -1.0F, 0.0F }, { 1.0F, 0.0F }, { -1.0F, 0.0F }, arrayOfFloat };
  }
  
  TouchResponse(Context paramContext, MotionLayout paramMotionLayout, XmlPullParser paramXmlPullParser) {
    this.mMotionLayout = paramMotionLayout;
    fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser));
  }
  
  private void fill(TypedArray paramTypedArray) {
    int i = paramTypedArray.getIndexCount();
    for (byte b = 0; b < i; b++) {
      int j = paramTypedArray.getIndex(b);
      if (j == R.styleable.OnSwipe_touchAnchorId) {
        this.mTouchAnchorId = paramTypedArray.getResourceId(j, this.mTouchAnchorId);
      } else if (j == R.styleable.OnSwipe_touchAnchorSide) {
        j = paramTypedArray.getInt(j, this.mTouchAnchorSide);
        this.mTouchAnchorSide = j;
        float[][] arrayOfFloat = TOUCH_SIDES;
        this.mTouchAnchorX = arrayOfFloat[j][0];
        this.mTouchAnchorY = arrayOfFloat[j][1];
      } else if (j == R.styleable.OnSwipe_dragDirection) {
        j = paramTypedArray.getInt(j, this.mTouchSide);
        this.mTouchSide = j;
        float[][] arrayOfFloat = TOUCH_DIRECTION;
        this.mTouchDirectionX = arrayOfFloat[j][0];
        this.mTouchDirectionY = arrayOfFloat[j][1];
      } else if (j == R.styleable.OnSwipe_maxVelocity) {
        this.mMaxVelocity = paramTypedArray.getFloat(j, this.mMaxVelocity);
      } else if (j == R.styleable.OnSwipe_maxAcceleration) {
        this.mMaxAcceleration = paramTypedArray.getFloat(j, this.mMaxAcceleration);
      } else if (j == R.styleable.OnSwipe_moveWhenScrollAtTop) {
        this.mMoveWhenScrollAtTop = paramTypedArray.getBoolean(j, this.mMoveWhenScrollAtTop);
      } else if (j == R.styleable.OnSwipe_dragScale) {
        this.mDragScale = paramTypedArray.getFloat(j, this.mDragScale);
      } else if (j == R.styleable.OnSwipe_dragThreshold) {
        this.mDragThreshold = paramTypedArray.getFloat(j, this.mDragThreshold);
      } else if (j == R.styleable.OnSwipe_touchRegionId) {
        this.mTouchRegionId = paramTypedArray.getResourceId(j, this.mTouchRegionId);
      } else if (j == R.styleable.OnSwipe_onTouchUp) {
        this.mOnTouchUp = paramTypedArray.getInt(j, this.mOnTouchUp);
      } else if (j == R.styleable.OnSwipe_nestedScrollFlags) {
        this.mFlags = paramTypedArray.getInteger(j, 0);
      } else if (j == R.styleable.OnSwipe_limitBoundsTo) {
        this.mLimitBoundsTo = paramTypedArray.getResourceId(j, 0);
      } 
    } 
  }
  
  private void fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet) {
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.OnSwipe);
    fill(typedArray);
    typedArray.recycle();
  }
  
  float dot(float paramFloat1, float paramFloat2) {
    return paramFloat1 * this.mTouchDirectionX + paramFloat2 * this.mTouchDirectionY;
  }
  
  public int getAnchorId() {
    return this.mTouchAnchorId;
  }
  
  public int getFlags() {
    return this.mFlags;
  }
  
  RectF getLimitBoundsTo(ViewGroup paramViewGroup, RectF paramRectF) {
    int i = this.mLimitBoundsTo;
    if (i == -1)
      return null; 
    View view = paramViewGroup.findViewById(i);
    if (view == null)
      return null; 
    paramRectF.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    return paramRectF;
  }
  
  int getLimitBoundsToId() {
    return this.mLimitBoundsTo;
  }
  
  float getMaxAcceleration() {
    return this.mMaxAcceleration;
  }
  
  public float getMaxVelocity() {
    return this.mMaxVelocity;
  }
  
  boolean getMoveWhenScrollAtTop() {
    return this.mMoveWhenScrollAtTop;
  }
  
  float getProgressDirection(float paramFloat1, float paramFloat2) {
    float f = this.mMotionLayout.getProgress();
    this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
    if (this.mTouchDirectionX != 0.0F) {
      float[] arrayOfFloat = this.mAnchorDpDt;
      if (arrayOfFloat[0] == 0.0F)
        arrayOfFloat[0] = 1.0E-7F; 
      paramFloat1 = paramFloat1 * this.mTouchDirectionX / this.mAnchorDpDt[0];
    } else {
      float[] arrayOfFloat = this.mAnchorDpDt;
      if (arrayOfFloat[1] == 0.0F)
        arrayOfFloat[1] = 1.0E-7F; 
      paramFloat1 = paramFloat2 * this.mTouchDirectionY / this.mAnchorDpDt[1];
    } 
    return paramFloat1;
  }
  
  RectF getTouchRegion(ViewGroup paramViewGroup, RectF paramRectF) {
    int i = this.mTouchRegionId;
    if (i == -1)
      return null; 
    View view = paramViewGroup.findViewById(i);
    if (view == null)
      return null; 
    paramRectF.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    return paramRectF;
  }
  
  int getTouchRegionId() {
    return this.mTouchRegionId;
  }
  
  void processTouchEvent(MotionEvent paramMotionEvent, MotionLayout.MotionTracker paramMotionTracker, int paramInt, MotionScene paramMotionScene) {
    MotionLayout motionLayout;
    paramMotionTracker.addMovement(paramMotionEvent);
    paramInt = paramMotionEvent.getAction();
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt == 2) {
          float f2 = paramMotionEvent.getRawY() - this.mLastTouchY;
          float f1 = paramMotionEvent.getRawX() - this.mLastTouchX;
          if (Math.abs(this.mTouchDirectionX * f1 + this.mTouchDirectionY * f2) > this.mDragThreshold || this.mDragStarted) {
            float f3 = this.mMotionLayout.getProgress();
            if (!this.mDragStarted) {
              this.mDragStarted = true;
              this.mMotionLayout.setProgress(f3);
            } 
            paramInt = this.mTouchAnchorId;
            if (paramInt != -1) {
              this.mMotionLayout.getAnchorDpDt(paramInt, f3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
            } else {
              float f = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
              float[] arrayOfFloat1 = this.mAnchorDpDt;
              arrayOfFloat1[1] = this.mTouchDirectionY * f;
              arrayOfFloat1[0] = f * this.mTouchDirectionX;
            } 
            float f4 = this.mTouchDirectionX;
            float[] arrayOfFloat = this.mAnchorDpDt;
            if (Math.abs((f4 * arrayOfFloat[0] + this.mTouchDirectionY * arrayOfFloat[1]) * this.mDragScale) < 0.01D) {
              arrayOfFloat = this.mAnchorDpDt;
              arrayOfFloat[0] = 0.01F;
              arrayOfFloat[1] = 0.01F;
            } 
            if (this.mTouchDirectionX != 0.0F) {
              f1 /= this.mAnchorDpDt[0];
            } else {
              f1 = f2 / this.mAnchorDpDt[1];
            } 
            f1 = Math.max(Math.min(f3 + f1, 1.0F), 0.0F);
            if (f1 != this.mMotionLayout.getProgress()) {
              this.mMotionLayout.setProgress(f1);
              paramMotionTracker.computeCurrentVelocity(1000);
              f3 = paramMotionTracker.getXVelocity();
              f1 = paramMotionTracker.getYVelocity();
              if (this.mTouchDirectionX != 0.0F) {
                f1 = f3 / this.mAnchorDpDt[0];
              } else {
                f1 /= this.mAnchorDpDt[1];
              } 
              this.mMotionLayout.mLastVelocity = f1;
            } else {
              this.mMotionLayout.mLastVelocity = 0.0F;
            } 
            this.mLastTouchX = paramMotionEvent.getRawX();
            this.mLastTouchY = paramMotionEvent.getRawY();
          } 
        } 
      } else {
        this.mDragStarted = false;
        paramMotionTracker.computeCurrentVelocity(1000);
        float f2 = paramMotionTracker.getXVelocity();
        float f1 = paramMotionTracker.getYVelocity();
        float f3 = this.mMotionLayout.getProgress();
        paramInt = this.mTouchAnchorId;
        if (paramInt != -1) {
          this.mMotionLayout.getAnchorDpDt(paramInt, f3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        } else {
          float f = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
          float[] arrayOfFloat1 = this.mAnchorDpDt;
          arrayOfFloat1[1] = this.mTouchDirectionY * f;
          arrayOfFloat1[0] = f * this.mTouchDirectionX;
        } 
        float f4 = this.mTouchDirectionX;
        float[] arrayOfFloat = this.mAnchorDpDt;
        float f5 = arrayOfFloat[0];
        f5 = arrayOfFloat[1];
        if (f4 != 0.0F) {
          f1 = f2 / arrayOfFloat[0];
        } else {
          f1 /= arrayOfFloat[1];
        } 
        if (!Float.isNaN(f1)) {
          f2 = f1 / 3.0F + f3;
        } else {
          f2 = f3;
        } 
        if (f2 != 0.0F && f2 != 1.0F) {
          paramInt = this.mOnTouchUp;
          if (paramInt != 3) {
            motionLayout = this.mMotionLayout;
            if (f2 < 0.5D) {
              f2 = 0.0F;
            } else {
              f2 = 1.0F;
            } 
            motionLayout.touchAnimateTo(paramInt, f2, f1);
            if (0.0F >= f3 || 1.0F <= f3)
              this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED); 
            return;
          } 
        } 
        if (0.0F >= f2 || 1.0F <= f2)
          this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED); 
      } 
    } else {
      this.mLastTouchX = motionLayout.getRawX();
      this.mLastTouchY = motionLayout.getRawY();
      this.mDragStarted = false;
    } 
  }
  
  void scrollMove(float paramFloat1, float paramFloat2) {
    float f1 = this.mMotionLayout.getProgress();
    if (!this.mDragStarted) {
      this.mDragStarted = true;
      this.mMotionLayout.setProgress(f1);
    } 
    this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f1, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
    float f2 = this.mTouchDirectionX;
    float[] arrayOfFloat = this.mAnchorDpDt;
    if (Math.abs(f2 * arrayOfFloat[0] + this.mTouchDirectionY * arrayOfFloat[1]) < 0.01D) {
      arrayOfFloat = this.mAnchorDpDt;
      arrayOfFloat[0] = 0.01F;
      arrayOfFloat[1] = 0.01F;
    } 
    f2 = this.mTouchDirectionX;
    if (f2 != 0.0F) {
      paramFloat1 = paramFloat1 * f2 / this.mAnchorDpDt[0];
    } else {
      paramFloat1 = paramFloat2 * this.mTouchDirectionY / this.mAnchorDpDt[1];
    } 
    paramFloat1 = Math.max(Math.min(f1 + paramFloat1, 1.0F), 0.0F);
    if (paramFloat1 != this.mMotionLayout.getProgress())
      this.mMotionLayout.setProgress(paramFloat1); 
  }
  
  void scrollUp(float paramFloat1, float paramFloat2) {
    int i = 0;
    this.mDragStarted = false;
    float f1 = this.mMotionLayout.getProgress();
    this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f1, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
    float f3 = this.mTouchDirectionX;
    float[] arrayOfFloat = this.mAnchorDpDt;
    float f2 = arrayOfFloat[0];
    float f4 = this.mTouchDirectionY;
    f2 = arrayOfFloat[1];
    f2 = 0.0F;
    if (f3 != 0.0F) {
      paramFloat1 = paramFloat1 * f3 / arrayOfFloat[0];
    } else {
      paramFloat1 = paramFloat2 * f4 / arrayOfFloat[1];
    } 
    paramFloat2 = f1;
    if (!Float.isNaN(paramFloat1))
      paramFloat2 = f1 + paramFloat1 / 3.0F; 
    if (paramFloat2 != 0.0F) {
      int j;
      if (paramFloat2 != 1.0F) {
        j = 1;
      } else {
        j = 0;
      } 
      if (this.mOnTouchUp != 3)
        i = 1; 
      if ((i & j) != 0) {
        MotionLayout motionLayout = this.mMotionLayout;
        j = this.mOnTouchUp;
        if (paramFloat2 < 0.5D) {
          paramFloat2 = f2;
        } else {
          paramFloat2 = 1.0F;
        } 
        motionLayout.touchAnimateTo(j, paramFloat2, paramFloat1);
      } 
    } 
  }
  
  public void setAnchorId(int paramInt) {
    this.mTouchAnchorId = paramInt;
  }
  
  void setDown(float paramFloat1, float paramFloat2) {
    this.mLastTouchX = paramFloat1;
    this.mLastTouchY = paramFloat2;
  }
  
  public void setMaxAcceleration(float paramFloat) {
    this.mMaxAcceleration = paramFloat;
  }
  
  public void setMaxVelocity(float paramFloat) {
    this.mMaxVelocity = paramFloat;
  }
  
  public void setRTL(boolean paramBoolean) {
    if (paramBoolean) {
      float[][] arrayOfFloat1 = TOUCH_DIRECTION;
      arrayOfFloat1[4] = arrayOfFloat1[3];
      arrayOfFloat1[5] = arrayOfFloat1[2];
      arrayOfFloat1 = TOUCH_SIDES;
      arrayOfFloat1[5] = arrayOfFloat1[2];
      arrayOfFloat1[6] = arrayOfFloat1[1];
    } else {
      float[][] arrayOfFloat1 = TOUCH_DIRECTION;
      arrayOfFloat1[4] = arrayOfFloat1[2];
      arrayOfFloat1[5] = arrayOfFloat1[3];
      arrayOfFloat1 = TOUCH_SIDES;
      arrayOfFloat1[5] = arrayOfFloat1[1];
      arrayOfFloat1[6] = arrayOfFloat1[2];
    } 
    float[][] arrayOfFloat = TOUCH_SIDES;
    int i = this.mTouchAnchorSide;
    this.mTouchAnchorX = arrayOfFloat[i][0];
    this.mTouchAnchorY = arrayOfFloat[i][1];
    arrayOfFloat = TOUCH_DIRECTION;
    i = this.mTouchSide;
    this.mTouchDirectionX = arrayOfFloat[i][0];
    this.mTouchDirectionY = arrayOfFloat[i][1];
  }
  
  public void setTouchAnchorLocation(float paramFloat1, float paramFloat2) {
    this.mTouchAnchorX = paramFloat1;
    this.mTouchAnchorY = paramFloat2;
  }
  
  void setUpTouchEvent(float paramFloat1, float paramFloat2) {
    this.mLastTouchX = paramFloat1;
    this.mLastTouchY = paramFloat2;
    this.mDragStarted = false;
  }
  
  void setupTouch() {
    NestedScrollView nestedScrollView;
    int i = this.mTouchAnchorId;
    if (i != -1) {
      View view = this.mMotionLayout.findViewById(i);
      nestedScrollView = (NestedScrollView)view;
      if (view == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cannot find TouchAnchorId @id/");
        stringBuilder.append(Debug.getName(this.mMotionLayout.getContext(), this.mTouchAnchorId));
        Log.e("TouchResponse", stringBuilder.toString());
        View view1 = view;
      } 
    } else {
      nestedScrollView = null;
    } 
    if (nestedScrollView instanceof NestedScrollView) {
      nestedScrollView = nestedScrollView;
      nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            final TouchResponse this$0;
            
            public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
              return false;
            }
          });
      nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            final TouchResponse this$0;
            
            public void onScrollChange(NestedScrollView param1NestedScrollView, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
          });
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mTouchDirectionX);
    stringBuilder.append(" , ");
    stringBuilder.append(this.mTouchDirectionY);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\TouchResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */