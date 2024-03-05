package androidx.fragment.app;

import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.Map;

@Deprecated
public class FragmentManagerNonConfig {
  private final Map<String, FragmentManagerNonConfig> mChildNonConfigs;
  
  private final Collection<Fragment> mFragments;
  
  private final Map<String, ViewModelStore> mViewModelStores;
  
  FragmentManagerNonConfig(Collection<Fragment> paramCollection, Map<String, FragmentManagerNonConfig> paramMap, Map<String, ViewModelStore> paramMap1) {
    this.mFragments = paramCollection;
    this.mChildNonConfigs = paramMap;
    this.mViewModelStores = paramMap1;
  }
  
  Map<String, FragmentManagerNonConfig> getChildNonConfigs() {
    return this.mChildNonConfigs;
  }
  
  Collection<Fragment> getFragments() {
    return this.mFragments;
  }
  
  Map<String, ViewModelStore> getViewModelStores() {
    return this.mViewModelStores;
  }
  
  boolean isRetaining(Fragment paramFragment) {
    Collection<Fragment> collection = this.mFragments;
    return (collection == null) ? false : collection.contains(paramFragment);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentManagerNonConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */