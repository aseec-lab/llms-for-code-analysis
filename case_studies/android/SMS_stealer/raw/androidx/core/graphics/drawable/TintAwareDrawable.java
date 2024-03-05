package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public interface TintAwareDrawable {
  void setTint(int paramInt);
  
  void setTintList(ColorStateList paramColorStateList);
  
  void setTintMode(PorterDuff.Mode paramMode);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\drawable\TintAwareDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */