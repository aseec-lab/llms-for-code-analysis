package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;

final class WrappedDrawableState extends Drawable.ConstantState {
  int mChangingConfigurations;
  
  Drawable.ConstantState mDrawableState;
  
  ColorStateList mTint = null;
  
  PorterDuff.Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;
  
  WrappedDrawableState(WrappedDrawableState paramWrappedDrawableState) {
    if (paramWrappedDrawableState != null) {
      this.mChangingConfigurations = paramWrappedDrawableState.mChangingConfigurations;
      this.mDrawableState = paramWrappedDrawableState.mDrawableState;
      this.mTint = paramWrappedDrawableState.mTint;
      this.mTintMode = paramWrappedDrawableState.mTintMode;
    } 
  }
  
  boolean canConstantState() {
    boolean bool;
    if (this.mDrawableState != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getChangingConfigurations() {
    byte b;
    int i = this.mChangingConfigurations;
    Drawable.ConstantState constantState = this.mDrawableState;
    if (constantState != null) {
      b = constantState.getChangingConfigurations();
    } else {
      b = 0;
    } 
    return i | b;
  }
  
  public Drawable newDrawable() {
    return newDrawable(null);
  }
  
  public Drawable newDrawable(Resources paramResources) {
    return (Build.VERSION.SDK_INT >= 21) ? new WrappedDrawableApi21(this, paramResources) : new WrappedDrawableApi14(this, paramResources);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\drawable\WrappedDrawableState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */