package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;

class ActionBarBackgroundDrawable extends Drawable {
  final ActionBarContainer mContainer;
  
  public ActionBarBackgroundDrawable(ActionBarContainer paramActionBarContainer) {
    this.mContainer = paramActionBarContainer;
  }
  
  public void draw(Canvas paramCanvas) {
    if (this.mContainer.mIsSplit) {
      if (this.mContainer.mSplitBackground != null)
        this.mContainer.mSplitBackground.draw(paramCanvas); 
    } else {
      if (this.mContainer.mBackground != null)
        this.mContainer.mBackground.draw(paramCanvas); 
      if (this.mContainer.mStackedBackground != null && this.mContainer.mIsStacked)
        this.mContainer.mStackedBackground.draw(paramCanvas); 
    } 
  }
  
  public int getOpacity() {
    return 0;
  }
  
  public void getOutline(Outline paramOutline) {
    if (this.mContainer.mIsSplit) {
      if (this.mContainer.mSplitBackground != null)
        this.mContainer.mSplitBackground.getOutline(paramOutline); 
    } else if (this.mContainer.mBackground != null) {
      this.mContainer.mBackground.getOutline(paramOutline);
    } 
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\ActionBarBackgroundDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */