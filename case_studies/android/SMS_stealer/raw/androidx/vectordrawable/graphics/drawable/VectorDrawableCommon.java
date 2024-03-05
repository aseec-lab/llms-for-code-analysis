package androidx.vectordrawable.graphics.drawable;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;

abstract class VectorDrawableCommon extends Drawable implements TintAwareDrawable {
  Drawable mDelegateDrawable;
  
  public void applyTheme(Resources.Theme paramTheme) {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null)
      DrawableCompat.applyTheme(drawable, paramTheme); 
  }
  
  public void clearColorFilter() {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null) {
      drawable.clearColorFilter();
      return;
    } 
    super.clearColorFilter();
  }
  
  public Drawable getCurrent() {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.getCurrent() : super.getCurrent();
  }
  
  public int getMinimumHeight() {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.getMinimumHeight() : super.getMinimumHeight();
  }
  
  public int getMinimumWidth() {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.getMinimumWidth() : super.getMinimumWidth();
  }
  
  public boolean getPadding(Rect paramRect) {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.getPadding(paramRect) : super.getPadding(paramRect);
  }
  
  public int[] getState() {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.getState() : super.getState();
  }
  
  public Region getTransparentRegion() {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.getTransparentRegion() : super.getTransparentRegion();
  }
  
  public void jumpToCurrentState() {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null)
      DrawableCompat.jumpToCurrentState(drawable); 
  }
  
  protected void onBoundsChange(Rect paramRect) {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null) {
      drawable.setBounds(paramRect);
      return;
    } 
    super.onBoundsChange(paramRect);
  }
  
  protected boolean onLevelChange(int paramInt) {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.setLevel(paramInt) : super.onLevelChange(paramInt);
  }
  
  public void setChangingConfigurations(int paramInt) {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null) {
      drawable.setChangingConfigurations(paramInt);
      return;
    } 
    super.setChangingConfigurations(paramInt);
  }
  
  public void setColorFilter(int paramInt, PorterDuff.Mode paramMode) {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null) {
      drawable.setColorFilter(paramInt, paramMode);
      return;
    } 
    super.setColorFilter(paramInt, paramMode);
  }
  
  public void setFilterBitmap(boolean paramBoolean) {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null)
      drawable.setFilterBitmap(paramBoolean); 
  }
  
  public void setHotspot(float paramFloat1, float paramFloat2) {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null)
      DrawableCompat.setHotspot(drawable, paramFloat1, paramFloat2); 
  }
  
  public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Drawable drawable = this.mDelegateDrawable;
    if (drawable != null)
      DrawableCompat.setHotspotBounds(drawable, paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  public boolean setState(int[] paramArrayOfint) {
    Drawable drawable = this.mDelegateDrawable;
    return (drawable != null) ? drawable.setState(paramArrayOfint) : super.setState(paramArrayOfint);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\vectordrawable\graphics\drawable\VectorDrawableCommon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */