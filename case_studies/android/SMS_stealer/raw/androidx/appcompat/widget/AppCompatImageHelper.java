package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;

public class AppCompatImageHelper {
  private TintInfo mImageTint;
  
  private TintInfo mInternalImageTint;
  
  private TintInfo mTmpInfo;
  
  private final ImageView mView;
  
  public AppCompatImageHelper(ImageView paramImageView) {
    this.mView = paramImageView;
  }
  
  private boolean applyFrameworkTintUsingColorFilter(Drawable paramDrawable) {
    if (this.mTmpInfo == null)
      this.mTmpInfo = new TintInfo(); 
    TintInfo tintInfo = this.mTmpInfo;
    tintInfo.clear();
    ColorStateList colorStateList = ImageViewCompat.getImageTintList(this.mView);
    if (colorStateList != null) {
      tintInfo.mHasTintList = true;
      tintInfo.mTintList = colorStateList;
    } 
    PorterDuff.Mode mode = ImageViewCompat.getImageTintMode(this.mView);
    if (mode != null) {
      tintInfo.mHasTintMode = true;
      tintInfo.mTintMode = mode;
    } 
    if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
      AppCompatDrawableManager.tintDrawable(paramDrawable, tintInfo, this.mView.getDrawableState());
      return true;
    } 
    return false;
  }
  
  private boolean shouldApplyFrameworkTintUsingColorFilter() {
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    if (i > 21) {
      if (this.mInternalImageTint == null)
        bool = false; 
      return bool;
    } 
    return (i == 21);
  }
  
  void applySupportImageTint() {
    Drawable drawable = this.mView.getDrawable();
    if (drawable != null)
      DrawableUtils.fixDrawable(drawable); 
    if (drawable != null) {
      if (shouldApplyFrameworkTintUsingColorFilter() && applyFrameworkTintUsingColorFilter(drawable))
        return; 
      TintInfo tintInfo = this.mImageTint;
      if (tintInfo != null) {
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
      } else {
        tintInfo = this.mInternalImageTint;
        if (tintInfo != null)
          AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState()); 
      } 
    } 
  }
  
  ColorStateList getSupportImageTintList() {
    TintInfo tintInfo = this.mImageTint;
    if (tintInfo != null) {
      ColorStateList colorStateList = tintInfo.mTintList;
    } else {
      tintInfo = null;
    } 
    return (ColorStateList)tintInfo;
  }
  
  PorterDuff.Mode getSupportImageTintMode() {
    TintInfo tintInfo = this.mImageTint;
    if (tintInfo != null) {
      PorterDuff.Mode mode = tintInfo.mTintMode;
    } else {
      tintInfo = null;
    } 
    return (PorterDuff.Mode)tintInfo;
  }
  
  boolean hasOverlappingRendering() {
    Drawable drawable = this.mView.getBackground();
    return !(Build.VERSION.SDK_INT >= 21 && drawable instanceof android.graphics.drawable.RippleDrawable);
  }
  
  public void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, R.styleable.AppCompatImageView, paramInt, 0);
    ImageView imageView = this.mView;
    ViewCompat.saveAttributeDataForStyleable((View)imageView, imageView.getContext(), R.styleable.AppCompatImageView, paramAttributeSet, tintTypedArray.getWrappedTypeArray(), paramInt, 0);
    try {
      Drawable drawable2 = this.mView.getDrawable();
      Drawable drawable1 = drawable2;
      if (drawable2 == null) {
        paramInt = tintTypedArray.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1);
        drawable1 = drawable2;
        if (paramInt != -1) {
          drawable2 = AppCompatResources.getDrawable(this.mView.getContext(), paramInt);
          drawable1 = drawable2;
          if (drawable2 != null) {
            this.mView.setImageDrawable(drawable2);
            drawable1 = drawable2;
          } 
        } 
      } 
      if (drawable1 != null)
        DrawableUtils.fixDrawable(drawable1); 
      if (tintTypedArray.hasValue(R.styleable.AppCompatImageView_tint))
        ImageViewCompat.setImageTintList(this.mView, tintTypedArray.getColorStateList(R.styleable.AppCompatImageView_tint)); 
      if (tintTypedArray.hasValue(R.styleable.AppCompatImageView_tintMode))
        ImageViewCompat.setImageTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArray.getInt(R.styleable.AppCompatImageView_tintMode, -1), null)); 
      return;
    } finally {
      tintTypedArray.recycle();
    } 
  }
  
  public void setImageResource(int paramInt) {
    if (paramInt != 0) {
      Drawable drawable = AppCompatResources.getDrawable(this.mView.getContext(), paramInt);
      if (drawable != null)
        DrawableUtils.fixDrawable(drawable); 
      this.mView.setImageDrawable(drawable);
    } else {
      this.mView.setImageDrawable(null);
    } 
    applySupportImageTint();
  }
  
  void setInternalImageTint(ColorStateList paramColorStateList) {
    if (paramColorStateList != null) {
      if (this.mInternalImageTint == null)
        this.mInternalImageTint = new TintInfo(); 
      this.mInternalImageTint.mTintList = paramColorStateList;
      this.mInternalImageTint.mHasTintList = true;
    } else {
      this.mInternalImageTint = null;
    } 
    applySupportImageTint();
  }
  
  void setSupportImageTintList(ColorStateList paramColorStateList) {
    if (this.mImageTint == null)
      this.mImageTint = new TintInfo(); 
    this.mImageTint.mTintList = paramColorStateList;
    this.mImageTint.mHasTintList = true;
    applySupportImageTint();
  }
  
  void setSupportImageTintMode(PorterDuff.Mode paramMode) {
    if (this.mImageTint == null)
      this.mImageTint = new TintInfo(); 
    this.mImageTint.mTintMode = paramMode;
    this.mImageTint.mHasTintMode = true;
    applySupportImageTint();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatImageHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */