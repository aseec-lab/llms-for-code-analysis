package androidx.core.app;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import java.lang.reflect.InvocationTargetException;

public class AppComponentFactory extends AppComponentFactory {
  public final Activity instantiateActivity(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return CoreComponentFactory.<Activity>checkCompatWrapper(instantiateActivityCompat(paramClassLoader, paramString, paramIntent));
  }
  
  public Activity instantiateActivityCompat(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      return Class.forName(paramString, false, paramClassLoader).<Activity>asSubclass(Activity.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (NoSuchMethodException noSuchMethodException) {}
    throw new RuntimeException("Couldn't call constructor", noSuchMethodException);
  }
  
  public final Application instantiateApplication(ClassLoader paramClassLoader, String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return CoreComponentFactory.<Application>checkCompatWrapper(instantiateApplicationCompat(paramClassLoader, paramString));
  }
  
  public Application instantiateApplicationCompat(ClassLoader paramClassLoader, String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      return Class.forName(paramString, false, paramClassLoader).<Application>asSubclass(Application.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (NoSuchMethodException noSuchMethodException) {}
    throw new RuntimeException("Couldn't call constructor", noSuchMethodException);
  }
  
  public final ContentProvider instantiateProvider(ClassLoader paramClassLoader, String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return CoreComponentFactory.<ContentProvider>checkCompatWrapper(instantiateProviderCompat(paramClassLoader, paramString));
  }
  
  public ContentProvider instantiateProviderCompat(ClassLoader paramClassLoader, String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      return Class.forName(paramString, false, paramClassLoader).<ContentProvider>asSubclass(ContentProvider.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (NoSuchMethodException noSuchMethodException) {}
    throw new RuntimeException("Couldn't call constructor", noSuchMethodException);
  }
  
  public final BroadcastReceiver instantiateReceiver(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return CoreComponentFactory.<BroadcastReceiver>checkCompatWrapper(instantiateReceiverCompat(paramClassLoader, paramString, paramIntent));
  }
  
  public BroadcastReceiver instantiateReceiverCompat(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      return Class.forName(paramString, false, paramClassLoader).<BroadcastReceiver>asSubclass(BroadcastReceiver.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (NoSuchMethodException noSuchMethodException) {}
    throw new RuntimeException("Couldn't call constructor", noSuchMethodException);
  }
  
  public final Service instantiateService(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return CoreComponentFactory.<Service>checkCompatWrapper(instantiateServiceCompat(paramClassLoader, paramString, paramIntent));
  }
  
  public Service instantiateServiceCompat(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      return Class.forName(paramString, false, paramClassLoader).<Service>asSubclass(Service.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (NoSuchMethodException noSuchMethodException) {}
    throw new RuntimeException("Couldn't call constructor", noSuchMethodException);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\AppComponentFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */