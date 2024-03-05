package androidx.appcompat.text;

import android.content.Context;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

public class AllCapsTransformationMethod implements TransformationMethod {
  private Locale mLocale;
  
  public AllCapsTransformationMethod(Context paramContext) {
    this.mLocale = (paramContext.getResources().getConfiguration()).locale;
  }
  
  public CharSequence getTransformation(CharSequence paramCharSequence, View paramView) {
    if (paramCharSequence != null) {
      paramCharSequence = paramCharSequence.toString().toUpperCase(this.mLocale);
    } else {
      paramCharSequence = null;
    } 
    return paramCharSequence;
  }
  
  public void onFocusChanged(View paramView, CharSequence paramCharSequence, boolean paramBoolean, int paramInt, Rect paramRect) {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\text\AllCapsTransformationMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */