package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

public abstract class FragmentContainer {
  @Deprecated
  public Fragment instantiate(Context paramContext, String paramString, Bundle paramBundle) {
    return Fragment.instantiate(paramContext, paramString, paramBundle);
  }
  
  public abstract View onFindViewById(int paramInt);
  
  public abstract boolean onHasView();
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */