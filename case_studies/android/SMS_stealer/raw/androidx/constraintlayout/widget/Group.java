package androidx.constraintlayout.widget;

import android.content.Context;
import android.util.AttributeSet;

public class Group extends ConstraintHelper {
  public Group(Context paramContext) {
    super(paramContext);
  }
  
  public Group(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public Group(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void init(AttributeSet paramAttributeSet) {
    super.init(paramAttributeSet);
    this.mUseViewMeasure = false;
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    applyLayoutFeatures();
  }
  
  public void setElevation(float paramFloat) {
    super.setElevation(paramFloat);
    applyLayoutFeatures();
  }
  
  public void setVisibility(int paramInt) {
    super.setVisibility(paramInt);
    applyLayoutFeatures();
  }
  
  public void updatePostLayout(ConstraintLayout paramConstraintLayout) {
    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
    layoutParams.widget.setWidth(0);
    layoutParams.widget.setHeight(0);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\widget\Group.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */