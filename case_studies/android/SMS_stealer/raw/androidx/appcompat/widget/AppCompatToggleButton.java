package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AppCompatToggleButton extends ToggleButton {
  private final AppCompatTextHelper mTextHelper;
  
  public AppCompatToggleButton(Context paramContext) {
    this(paramContext, null);
  }
  
  public AppCompatToggleButton(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 16842827);
  }
  
  public AppCompatToggleButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme((View)this, getContext());
    AppCompatTextHelper appCompatTextHelper = new AppCompatTextHelper((TextView)this);
    this.mTextHelper = appCompatTextHelper;
    appCompatTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */