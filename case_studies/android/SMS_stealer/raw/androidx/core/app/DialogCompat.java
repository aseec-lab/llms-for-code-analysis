package androidx.core.app;

import android.app.Dialog;
import android.os.Build;
import android.view.View;

public class DialogCompat {
  public static View requireViewById(Dialog paramDialog, int paramInt) {
    if (Build.VERSION.SDK_INT >= 28)
      return paramDialog.requireViewById(paramInt); 
    View view = paramDialog.findViewById(paramInt);
    if (view != null)
      return view; 
    throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\DialogCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */