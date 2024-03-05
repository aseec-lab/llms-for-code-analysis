package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public class TintInfo {
  public boolean mHasTintList;
  
  public boolean mHasTintMode;
  
  public ColorStateList mTintList;
  
  public PorterDuff.Mode mTintMode;
  
  void clear() {
    this.mTintList = null;
    this.mHasTintList = false;
    this.mTintMode = null;
    this.mHasTintMode = false;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\TintInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */