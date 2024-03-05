package androidx.core.os;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;

public final class HandlerCompat {
  private static final String TAG = "HandlerCompat";
  
  public static Handler createAsync(Looper paramLooper) {
    Throwable throwable;
    if (Build.VERSION.SDK_INT >= 28)
      return Handler.createAsync(paramLooper); 
    if (Build.VERSION.SDK_INT >= 16)
      try {
        return Handler.class.getDeclaredConstructor(new Class[] { Looper.class, Handler.Callback.class, boolean.class }).newInstance(new Object[] { paramLooper, null, Boolean.valueOf(true) });
      } catch (IllegalAccessException|InstantiationException|NoSuchMethodException illegalAccessException) {
        Log.v("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor");
      } catch (InvocationTargetException invocationTargetException) {
        throwable = invocationTargetException.getCause();
        if (!(throwable instanceof RuntimeException)) {
          if (throwable instanceof Error)
            throw (Error)throwable; 
          throw new RuntimeException(throwable);
        } 
        throw (RuntimeException)throwable;
      }  
    return new Handler((Looper)throwable);
  }
  
  public static Handler createAsync(Looper paramLooper, Handler.Callback paramCallback) {
    Throwable throwable;
    if (Build.VERSION.SDK_INT >= 28)
      return Handler.createAsync(paramLooper, paramCallback); 
    if (Build.VERSION.SDK_INT >= 16)
      try {
        return Handler.class.getDeclaredConstructor(new Class[] { Looper.class, Handler.Callback.class, boolean.class }).newInstance(new Object[] { paramLooper, paramCallback, Boolean.valueOf(true) });
      } catch (IllegalAccessException|InstantiationException|NoSuchMethodException illegalAccessException) {
        Log.v("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor");
      } catch (InvocationTargetException invocationTargetException) {
        throwable = invocationTargetException.getCause();
        if (!(throwable instanceof RuntimeException)) {
          if (throwable instanceof Error)
            throw (Error)throwable; 
          throw new RuntimeException(throwable);
        } 
        throw (RuntimeException)throwable;
      }  
    return new Handler((Looper)throwable, paramCallback);
  }
  
  public static boolean postDelayed(Handler paramHandler, Runnable paramRunnable, Object paramObject, long paramLong) {
    if (Build.VERSION.SDK_INT >= 28)
      return paramHandler.postDelayed(paramRunnable, paramObject, paramLong); 
    Message message = Message.obtain(paramHandler, paramRunnable);
    message.obj = paramObject;
    return paramHandler.sendMessageDelayed(message, paramLong);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\os\HandlerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */