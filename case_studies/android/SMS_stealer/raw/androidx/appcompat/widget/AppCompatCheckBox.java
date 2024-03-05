package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.TintableBackgroundView;
import androidx.core.widget.TintableCompoundButton;

public class AppCompatCheckBox extends CheckBox implements TintableCompoundButton, TintableBackgroundView {
  private final AppCompatBackgroundHelper mBackgroundTintHelper;
  
  private final AppCompatCompoundButtonHelper mCompoundButtonHelper;
  
  private final AppCompatTextHelper mTextHelper;
  
  public AppCompatCheckBox(Context paramContext) {
    this(paramContext, null);
  }
  
  public AppCompatCheckBox(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.checkboxStyle);
  }
  
  public AppCompatCheckBox(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme((View)this, getContext());
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = new AppCompatCompoundButtonHelper((CompoundButton)this);
    this.mCompoundButtonHelper = appCompatCompoundButtonHelper;
    appCompatCompoundButtonHelper.loadFromAttributes(paramAttributeSet, paramInt);
    AppCompatBackgroundHelper appCompatBackgroundHelper = new AppCompatBackgroundHelper((View)this);
    this.mBackgroundTintHelper = appCompatBackgroundHelper;
    appCompatBackgroundHelper.loadFromAttributes(paramAttributeSet, paramInt);
    AppCompatTextHelper appCompatTextHelper = new AppCompatTextHelper((TextView)this);
    this.mTextHelper = appCompatTextHelper;
    appCompatTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.applySupportBackgroundTint(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.applyCompoundDrawablesTints(); 
  }
  
  public int getCompoundPaddingLeft() {
    int j = super.getCompoundPaddingLeft();
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    int i = j;
    if (appCompatCompoundButtonHelper != null)
      i = appCompatCompoundButtonHelper.getCompoundPaddingLeft(j); 
    return i;
  }
  
  public ColorStateList getSupportBackgroundTintList() {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null) {
      ColorStateList colorStateList = appCompatBackgroundHelper.getSupportBackgroundTintList();
    } else {
      appCompatBackgroundHelper = null;
    } 
    return (ColorStateList)appCompatBackgroundHelper;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode() {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null) {
      PorterDuff.Mode mode = appCompatBackgroundHelper.getSupportBackgroundTintMode();
    } else {
      appCompatBackgroundHelper = null;
    } 
    return (PorterDuff.Mode)appCompatBackgroundHelper;
  }
  
  public ColorStateList getSupportButtonTintList() {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null) {
      ColorStateList colorStateList = appCompatCompoundButtonHelper.getSupportButtonTintList();
    } else {
      appCompatCompoundButtonHelper = null;
    } 
    return (ColorStateList)appCompatCompoundButtonHelper;
  }
  
  public PorterDuff.Mode getSupportButtonTintMode() {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null) {
      PorterDuff.Mode mode = appCompatCompoundButtonHelper.getSupportButtonTintMode();
    } else {
      appCompatCompoundButtonHelper = null;
    } 
    return (PorterDuff.Mode)appCompatCompoundButtonHelper;
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable) {
    super.setBackgroundDrawable(paramDrawable);
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.onSetBackgroundDrawable(paramDrawable); 
  }
  
  public void setBackgroundResource(int paramInt) {
    super.setBackgroundResource(paramInt);
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.onSetBackgroundResource(paramInt); 
  }
  
  public void setButtonDrawable(int paramInt) {
    setButtonDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setButtonDrawable(Drawable paramDrawable) {
    super.setButtonDrawable(paramDrawable);
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null)
      appCompatCompoundButtonHelper.onSetButtonDrawable(); 
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList) {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.setSupportBackgroundTintList(paramColorStateList); 
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode) {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.setSupportBackgroundTintMode(paramMode); 
  }
  
  public void setSupportButtonTintList(ColorStateList paramColorStateList) {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null)
      appCompatCompoundButtonHelper.setSupportButtonTintList(paramColorStateList); 
  }
  
  public void setSupportButtonTintMode(PorterDuff.Mode paramMode) {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null)
      appCompatCompoundButtonHelper.setSupportButtonTintMode(paramMode); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatCheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */