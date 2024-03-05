package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.TintableBackgroundView;

public class AppCompatMultiAutoCompleteTextView extends MultiAutoCompleteTextView implements TintableBackgroundView {
  private static final int[] TINT_ATTRS = new int[] { 16843126 };
  
  private final AppCompatBackgroundHelper mBackgroundTintHelper;
  
  private final AppCompatTextHelper mTextHelper;
  
  public AppCompatMultiAutoCompleteTextView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public AppCompatMultiAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.autoCompleteTextViewStyle);
  }
  
  public AppCompatMultiAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme((View)this, getContext());
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
    if (tintTypedArray.hasValue(0))
      setDropDownBackgroundDrawable(tintTypedArray.getDrawable(0)); 
    tintTypedArray.recycle();
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
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo) {
    return AppCompatHintHelper.onCreateInputConnection(super.onCreateInputConnection(paramEditorInfo), paramEditorInfo, (View)this);
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
  
  public void setDropDownBackgroundResource(int paramInt) {
    setDropDownBackgroundDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
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
  
  public void setTextAppearance(Context paramContext, int paramInt) {
    super.setTextAppearance(paramContext, paramInt);
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.onSetTextAppearance(paramContext, paramInt); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatMultiAutoCompleteTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */