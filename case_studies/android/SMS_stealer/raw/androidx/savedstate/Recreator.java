package androidx.savedstate;

import android.os.Bundle;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class Recreator implements GenericLifecycleObserver {
  static final String CLASSES_KEY = "classes_to_restore";
  
  static final String COMPONENT_KEY = "androidx.savedstate.Restarter";
  
  private final SavedStateRegistryOwner mOwner;
  
  Recreator(SavedStateRegistryOwner paramSavedStateRegistryOwner) {
    this.mOwner = paramSavedStateRegistryOwner;
  }
  
  private void reflectiveNew(String paramString) {
    StringBuilder stringBuilder;
    try {
      Class<? extends SavedStateRegistry.AutoRecreated> clazz = Class.forName(paramString, false, Recreator.class.getClassLoader()).asSubclass(SavedStateRegistry.AutoRecreated.class);
      try {
        Constructor<? extends SavedStateRegistry.AutoRecreated> constructor = clazz.getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);
        try {
          SavedStateRegistry.AutoRecreated autoRecreated = constructor.newInstance(new Object[0]);
          autoRecreated.onRecreated(this.mOwner);
          return;
        } catch (Exception exception) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Failed to instantiate ");
          stringBuilder1.append(paramString);
          throw new RuntimeException(stringBuilder1.toString(), exception);
        } 
      } catch (NoSuchMethodException noSuchMethodException) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Class");
        stringBuilder.append(exception.getSimpleName());
        stringBuilder.append(" must have default constructor in order to be automatically recreated");
        throw new IllegalStateException(stringBuilder.toString(), noSuchMethodException);
      } 
    } catch (ClassNotFoundException classNotFoundException) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Class ");
      stringBuilder1.append((String)stringBuilder);
      stringBuilder1.append(" wasn't found");
      throw new RuntimeException(stringBuilder1.toString(), classNotFoundException);
    } 
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent) {
    if (paramEvent == Lifecycle.Event.ON_CREATE) {
      paramLifecycleOwner.getLifecycle().removeObserver((LifecycleObserver)this);
      Bundle bundle = this.mOwner.getSavedStateRegistry().consumeRestoredStateForKey("androidx.savedstate.Restarter");
      if (bundle == null)
        return; 
      ArrayList arrayList = bundle.getStringArrayList("classes_to_restore");
      if (arrayList != null) {
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext())
          reflectiveNew(iterator.next()); 
        return;
      } 
      throw new IllegalStateException("Bundle with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
    } 
    throw new AssertionError("Next event must be ON_CREATE");
  }
  
  static final class SavedStateProvider implements SavedStateRegistry.SavedStateProvider {
    final Set<String> mClasses = new HashSet<String>();
    
    SavedStateProvider(SavedStateRegistry param1SavedStateRegistry) {
      param1SavedStateRegistry.registerSavedStateProvider("androidx.savedstate.Restarter", this);
    }
    
    void add(String param1String) {
      this.mClasses.add(param1String);
    }
    
    public Bundle saveState() {
      Bundle bundle = new Bundle();
      bundle.putStringArrayList("classes_to_restore", new ArrayList<String>(this.mClasses));
      return bundle;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\savedstate\Recreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */