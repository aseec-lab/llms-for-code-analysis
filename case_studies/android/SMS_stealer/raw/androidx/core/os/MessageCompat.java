package androidx.core.os;

import android.os.Build;
import android.os.Message;

public final class MessageCompat {
  private static boolean sTryIsAsynchronous = true;
  
  private static boolean sTrySetAsynchronous = true;
  
  public static boolean isAsynchronous(Message paramMessage) {
    if (Build.VERSION.SDK_INT >= 22)
      return paramMessage.isAsynchronous(); 
    if (sTryIsAsynchronous && Build.VERSION.SDK_INT >= 16)
      try {
        return paramMessage.isAsynchronous();
      } catch (NoSuchMethodError noSuchMethodError) {
        sTryIsAsynchronous = false;
      }  
    return false;
  }
  
  public static void setAsynchronous(Message paramMessage, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 22) {
      paramMessage.setAsynchronous(paramBoolean);
      return;
    } 
    if (sTrySetAsynchronous && Build.VERSION.SDK_INT >= 16)
      try {
        paramMessage.setAsynchronous(paramBoolean);
      } catch (NoSuchMethodError noSuchMethodError) {
        sTrySetAsynchronous = false;
      }  
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\os\MessageCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */