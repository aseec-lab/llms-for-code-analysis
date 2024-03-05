package androidx.core.database;

import android.database.CursorWindow;
import android.os.Build;

public final class CursorWindowCompat {
  public static CursorWindow create(String paramString, long paramLong) {
    return (Build.VERSION.SDK_INT >= 28) ? new CursorWindow(paramString, paramLong) : ((Build.VERSION.SDK_INT >= 15) ? new CursorWindow(paramString) : new CursorWindow(false));
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\database\CursorWindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */