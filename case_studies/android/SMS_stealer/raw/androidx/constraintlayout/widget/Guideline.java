package androidx.constraintlayout.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class Guideline extends View {
  public Guideline(Context paramContext) {
    super(paramContext);
    super.setVisibility(8);
  }
  
  public Guideline(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    super.setVisibility(8);
  }
  
  public Guideline(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    super.setVisibility(8);
  }
  
  public Guideline(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1);
    super.setVisibility(8);
  }
  
  public void draw(Canvas paramCanvas) {}
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    setMeasuredDimension(0, 0);
  }
  
  public void setGuidelineBegin(int paramInt) {
    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
    layoutParams.guideBegin = paramInt;
    setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  public void setGuidelineEnd(int paramInt) {
    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
    layoutParams.guideEnd = paramInt;
    setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  public void setGuidelinePercent(float paramFloat) {
    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
    layoutParams.guidePercent = paramFloat;
    setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  public void setVisibility(int paramInt) {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\widget\Guideline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */