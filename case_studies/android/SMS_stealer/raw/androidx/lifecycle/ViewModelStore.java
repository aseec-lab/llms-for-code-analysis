package androidx.lifecycle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ViewModelStore {
  private final HashMap<String, ViewModel> mMap = new HashMap<String, ViewModel>();
  
  public final void clear() {
    Iterator<ViewModel> iterator = this.mMap.values().iterator();
    while (iterator.hasNext())
      ((ViewModel)iterator.next()).clear(); 
    this.mMap.clear();
  }
  
  final ViewModel get(String paramString) {
    return this.mMap.get(paramString);
  }
  
  Set<String> keys() {
    return new HashSet<String>(this.mMap.keySet());
  }
  
  final void put(String paramString, ViewModel paramViewModel) {
    ViewModel viewModel = this.mMap.put(paramString, paramViewModel);
    if (viewModel != null)
      viewModel.onCleared(); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\ViewModelStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */