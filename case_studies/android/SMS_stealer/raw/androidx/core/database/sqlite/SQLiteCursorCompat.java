package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteCursor;
import android.os.Build;

public final class SQLiteCursorCompat {
  public static void setFillWindowForwardOnly(SQLiteCursor paramSQLiteCursor, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 28)
      paramSQLiteCursor.setFillWindowForwardOnly(paramBoolean); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\database\sqlite\SQLiteCursorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */