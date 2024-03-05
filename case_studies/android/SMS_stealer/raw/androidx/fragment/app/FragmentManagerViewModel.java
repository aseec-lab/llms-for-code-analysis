package androidx.fragment.app;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

class FragmentManagerViewModel extends ViewModel {
  private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
      public <T extends ViewModel> T create(Class<T> param1Class) {
        return (T)new FragmentManagerViewModel(true);
      }
    };
  
  private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap<String, FragmentManagerViewModel>();
  
  private boolean mHasBeenCleared = false;
  
  private boolean mHasSavedSnapshot = false;
  
  private final HashSet<Fragment> mRetainedFragments = new HashSet<Fragment>();
  
  private final boolean mStateAutomaticallySaved;
  
  private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap<String, ViewModelStore>();
  
  FragmentManagerViewModel(boolean paramBoolean) {
    this.mStateAutomaticallySaved = paramBoolean;
  }
  
  static FragmentManagerViewModel getInstance(ViewModelStore paramViewModelStore) {
    return (FragmentManagerViewModel)(new ViewModelProvider(paramViewModelStore, FACTORY)).get(FragmentManagerViewModel.class);
  }
  
  boolean addRetainedFragment(Fragment paramFragment) {
    return this.mRetainedFragments.add(paramFragment);
  }
  
  void clearNonConfigState(Fragment paramFragment) {
    if (FragmentManagerImpl.DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Clearing non-config state for ");
      stringBuilder.append(paramFragment);
      Log.d("FragmentManager", stringBuilder.toString());
    } 
    FragmentManagerViewModel fragmentManagerViewModel = this.mChildNonConfigs.get(paramFragment.mWho);
    if (fragmentManagerViewModel != null) {
      fragmentManagerViewModel.onCleared();
      this.mChildNonConfigs.remove(paramFragment.mWho);
    } 
    ViewModelStore viewModelStore = this.mViewModelStores.get(paramFragment.mWho);
    if (viewModelStore != null) {
      viewModelStore.clear();
      this.mViewModelStores.remove(paramFragment.mWho);
    } 
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    if (!this.mRetainedFragments.equals(((FragmentManagerViewModel)paramObject).mRetainedFragments) || !this.mChildNonConfigs.equals(((FragmentManagerViewModel)paramObject).mChildNonConfigs) || !this.mViewModelStores.equals(((FragmentManagerViewModel)paramObject).mViewModelStores))
      bool = false; 
    return bool;
  }
  
  FragmentManagerViewModel getChildNonConfig(Fragment paramFragment) {
    FragmentManagerViewModel fragmentManagerViewModel2 = this.mChildNonConfigs.get(paramFragment.mWho);
    FragmentManagerViewModel fragmentManagerViewModel1 = fragmentManagerViewModel2;
    if (fragmentManagerViewModel2 == null) {
      fragmentManagerViewModel1 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
      this.mChildNonConfigs.put(paramFragment.mWho, fragmentManagerViewModel1);
    } 
    return fragmentManagerViewModel1;
  }
  
  Collection<Fragment> getRetainedFragments() {
    return this.mRetainedFragments;
  }
  
  @Deprecated
  FragmentManagerNonConfig getSnapshot() {
    if (this.mRetainedFragments.isEmpty() && this.mChildNonConfigs.isEmpty() && this.mViewModelStores.isEmpty())
      return null; 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (Map.Entry<String, FragmentManagerViewModel> entry : this.mChildNonConfigs.entrySet()) {
      FragmentManagerNonConfig fragmentManagerNonConfig = ((FragmentManagerViewModel)entry.getValue()).getSnapshot();
      if (fragmentManagerNonConfig != null)
        hashMap.put(entry.getKey(), fragmentManagerNonConfig); 
    } 
    this.mHasSavedSnapshot = true;
    return (this.mRetainedFragments.isEmpty() && hashMap.isEmpty() && this.mViewModelStores.isEmpty()) ? null : new FragmentManagerNonConfig(new ArrayList<Fragment>(this.mRetainedFragments), (Map)hashMap, new HashMap<String, ViewModelStore>(this.mViewModelStores));
  }
  
  ViewModelStore getViewModelStore(Fragment paramFragment) {
    ViewModelStore viewModelStore2 = this.mViewModelStores.get(paramFragment.mWho);
    ViewModelStore viewModelStore1 = viewModelStore2;
    if (viewModelStore2 == null) {
      viewModelStore1 = new ViewModelStore();
      this.mViewModelStores.put(paramFragment.mWho, viewModelStore1);
    } 
    return viewModelStore1;
  }
  
  public int hashCode() {
    return (this.mRetainedFragments.hashCode() * 31 + this.mChildNonConfigs.hashCode()) * 31 + this.mViewModelStores.hashCode();
  }
  
  boolean isCleared() {
    return this.mHasBeenCleared;
  }
  
  protected void onCleared() {
    if (FragmentManagerImpl.DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("onCleared called for ");
      stringBuilder.append(this);
      Log.d("FragmentManager", stringBuilder.toString());
    } 
    this.mHasBeenCleared = true;
  }
  
  boolean removeRetainedFragment(Fragment paramFragment) {
    return this.mRetainedFragments.remove(paramFragment);
  }
  
  @Deprecated
  void restoreFromSnapshot(FragmentManagerNonConfig paramFragmentManagerNonConfig) {
    this.mRetainedFragments.clear();
    this.mChildNonConfigs.clear();
    this.mViewModelStores.clear();
    if (paramFragmentManagerNonConfig != null) {
      Collection<Fragment> collection = paramFragmentManagerNonConfig.getFragments();
      if (collection != null)
        this.mRetainedFragments.addAll(collection); 
      Map<String, FragmentManagerNonConfig> map1 = paramFragmentManagerNonConfig.getChildNonConfigs();
      if (map1 != null)
        for (Map.Entry<String, FragmentManagerNonConfig> entry : map1.entrySet()) {
          FragmentManagerViewModel fragmentManagerViewModel = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
          fragmentManagerViewModel.restoreFromSnapshot((FragmentManagerNonConfig)entry.getValue());
          this.mChildNonConfigs.put((String)entry.getKey(), fragmentManagerViewModel);
        }  
      Map<String, ViewModelStore> map = paramFragmentManagerNonConfig.getViewModelStores();
      if (map != null)
        this.mViewModelStores.putAll(map); 
    } 
    this.mHasSavedSnapshot = false;
  }
  
  boolean shouldDestroy(Fragment paramFragment) {
    return !this.mRetainedFragments.contains(paramFragment) ? true : (this.mStateAutomaticallySaved ? this.mHasBeenCleared : (this.mHasSavedSnapshot ^ true));
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("FragmentManagerViewModel{");
    stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    stringBuilder.append("} Fragments (");
    Iterator<Fragment> iterator = this.mRetainedFragments.iterator();
    while (iterator.hasNext()) {
      stringBuilder.append(iterator.next());
      if (iterator.hasNext())
        stringBuilder.append(", "); 
    } 
    stringBuilder.append(") Child Non Config (");
    iterator = this.mChildNonConfigs.keySet().iterator();
    while (iterator.hasNext()) {
      stringBuilder.append((String)iterator.next());
      if (iterator.hasNext())
        stringBuilder.append(", "); 
    } 
    stringBuilder.append(") ViewModelStores (");
    iterator = this.mViewModelStores.keySet().iterator();
    while (iterator.hasNext()) {
      stringBuilder.append((String)iterator.next());
      if (iterator.hasNext())
        stringBuilder.append(", "); 
    } 
    stringBuilder.append(')');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentManagerViewModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */