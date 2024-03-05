package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

public class ImageViewCompat {
  public static ColorStateList getImageTintList(ImageView paramImageView) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramImageView.getImageTintList(); 
    if (paramImageView instanceof TintableImageSourceView) {
      ColorStateList colorStateList = ((TintableImageSourceView)paramImageView).getSupportImageTintList();
    } else {
      paramImageView = null;
    } 
    return (ColorStateList)paramImageView;
  }
  
  public static PorterDuff.Mode getImageTintMode(ImageView paramImageView) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramImageView.getImageTintMode(); 
    if (paramImageView instanceof TintableImageSourceView) {
      PorterDuff.Mode mode = ((TintableImageSourceView)paramImageView).getSupportImageTintMode();
    } else {
      paramImageView = null;
    } 
    return (PorterDuff.Mode)paramImageView;
  }
  
  public static void setImageTintList(ImageView paramImageView, ColorStateList paramColorStateList) {
    Drawable drawable;
    if (Build.VERSION.SDK_INT >= 21) {
      paramImageView.setImageTintList(paramColorStateList);
      if (Build.VERSION.SDK_INT == 21) {
        drawable = paramImageView.getDrawable();
        if (drawable != null && paramImageView.getImageTintList() != null) {
          if (drawable.isStateful())
            drawable.setState(paramImageView.getDrawableState()); 
          paramImageView.setImageDrawable(drawable);
        } 
      } 
    } else if (paramImageView instanceof TintableImageSourceView) {
      ((TintableImageSourceView)paramImageView).setSupportImageTintList((ColorStateList)drawable);
    } 
  }
  
  public static void setImageTintMode(ImageView paramImageView, PorterDuff.Mode paramMode) {
    Drawable drawable;
    if (Build.VERSION.SDK_INT >= 21) {
      paramImageView.setImageTintMode(paramMode);
      if (Build.VERSION.SDK_INT == 21) {
        drawable = paramImageView.getDrawable();
        if (drawable != null && paramImageView.getImageTintList() != null) {
          if (drawable.isStateful())
            drawable.setState(paramImageView.getDrawableState()); 
          paramImageView.setImageDrawable(drawable);
        } 
      } 
    } else if (paramImageView instanceof TintableImageSourceView) {
      ((TintableImageSourceView)paramImageView).setSupportImageTintMode((PorterDuff.Mode)drawable);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\widget\ImageViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */