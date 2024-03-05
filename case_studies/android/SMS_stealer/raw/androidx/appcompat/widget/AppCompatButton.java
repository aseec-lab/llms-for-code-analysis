package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.core.view.TintableBackgroundView;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TextViewCompat;
import androidx.core.widget.TintableCompoundDrawablesView;

public class AppCompatButton extends Button implements TintableBackgroundView, AutoSizeableTextView, TintableCompoundDrawablesView {
  private final AppCompatBackgroundHelper mBackgroundTintHelper;
  
  private final AppCompatTextHelper mTextHelper;
  
  public AppCompatButton(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public AppCompatButton(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.buttonStyle);
  }
  
  public AppCompatButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme((View)this, getContext());
    AppCompatBackgroundHelper appCompatBackgroundHelper = new AppCompatBackgroundHelper((View)this);
    this.mBackgroundTintHelper = appCompatBackgroundHelper;
    appCompatBackgroundHelper.loadFromAttributes(paramAttributeSet, paramInt);
    AppCompatTextHelper appCompatTextHelper = new AppCompatTextHelper((TextView)this);
    this.mTextHelper = appCompatTextHelper;
    appCompatTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
    this.mTextHelper.applyCompoundDrawablesTints();
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
  
  public int getAutoSizeMaxTextSize() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeMaxTextSize(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeMaxTextSize() : -1;
  }
  
  public int getAutoSizeMinTextSize() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeMinTextSize(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeMinTextSize() : -1;
  }
  
  public int getAutoSizeStepGranularity() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeStepGranularity(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeStepGranularity() : -1;
  }
  
  public int[] getAutoSizeTextAvailableSizes() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeTextAvailableSizes(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeTextAvailableSizes() : new int[0];
  }
  
  public int getAutoSizeTextType() {
    boolean bool1 = PLATFORM_SUPPORTS_AUTOSIZE;
    boolean bool = false;
    if (bool1) {
      if (super.getAutoSizeTextType() == 1)
        bool = true; 
      return bool;
    } 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeTextType() : 0;
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
  
  public ColorStateList getSupportCompoundDrawablesTintList() {
    return this.mTextHelper.getCompoundDrawableTintList();
  }
  
  public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
    return this.mTextHelper.getCompoundDrawableTintMode();
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(Button.class.getName());
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName(Button.class.getName());
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
    super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    if (this.mTextHelper != null && !PLATFORM_SUPPORTS_AUTOSIZE && this.mTextHelper.isAutoSizeEnabled())
      this.mTextHelper.autoSizeText(); 
  }
  
  public void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4); 
    } 
  }
  
  public void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfint, int paramInt) throws IllegalArgumentException {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfint, paramInt);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfint, paramInt); 
    } 
  }
  
  public void setAutoSizeTextTypeWithDefaults(int paramInt) {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setAutoSizeTextTypeWithDefaults(paramInt);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setAutoSizeTextTypeWithDefaults(paramInt); 
    } 
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
  
  public void setCustomSelectionActionModeCallback(ActionMode.Callback paramCallback) {
    super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback((TextView)this, paramCallback));
  }
  
  public void setSupportAllCaps(boolean paramBoolean) {
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.setAllCaps(paramBoolean); 
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
  
  public void setSupportCompoundDrawablesTintList(ColorStateList paramColorStateList) {
    this.mTextHelper.setCompoundDrawableTintList(paramColorStateList);
    this.mTextHelper.applyCompoundDrawablesTints();
  }
  
  public void setSupportCompoundDrawablesTintMode(PorterDuff.Mode paramMode) {
    this.mTextHelper.setCompoundDrawableTintMode(paramMode);
    this.mTextHelper.applyCompoundDrawablesTints();
  }
  
  public void setTextAppearance(Context paramContext, int paramInt) {
    super.setTextAppearance(paramContext, paramInt);
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.onSetTextAppearance(paramContext, paramInt); 
  }
  
  public void setTextSize(int paramInt, float paramFloat) {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setTextSize(paramInt, paramFloat);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setTextSize(paramInt, paramFloat); 
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */