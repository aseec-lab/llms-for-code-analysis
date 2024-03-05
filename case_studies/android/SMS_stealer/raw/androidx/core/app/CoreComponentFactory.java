package androidx.core.app;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;

public class CoreComponentFactory extends AppComponentFactory {
  static <T> T checkCompatWrapper(T paramT) {
    if (paramT instanceof CompatWrapped) {
      Object object = ((CompatWrapped)paramT).getWrapper();
      if (object != null)
        return (T)object; 
    } 
    return paramT;
  }
  
  public Activity instantiateActivity(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return checkCompatWrapper(super.instantiateActivity(paramClassLoader, paramString, paramIntent));
  }
  
  public Application instantiateApplication(ClassLoader paramClassLoader, String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return checkCompatWrapper(super.instantiateApplication(paramClassLoader, paramString));
  }
  
  public ContentProvider instantiateProvider(ClassLoader paramClassLoader, String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return checkCompatWrapper(super.instantiateProvider(paramClassLoader, paramString));
  }
  
  public BroadcastReceiver instantiateReceiver(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return checkCompatWrapper(super.instantiateReceiver(paramClassLoader, paramString, paramIntent));
  }
  
  public Service instantiateService(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return checkCompatWrapper(super.instantiateService(paramClassLoader, paramString, paramIntent));
  }
  
  public static interface CompatWrapped {
    Object getWrapper();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\CoreComponentFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */