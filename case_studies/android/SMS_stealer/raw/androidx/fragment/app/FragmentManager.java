package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public abstract class FragmentManager {
  static final FragmentFactory DEFAULT_FACTORY = new FragmentFactory();
  
  public static final int POP_BACK_STACK_INCLUSIVE = 1;
  
  private FragmentFactory mFragmentFactory = null;
  
  public static void enableDebugLogging(boolean paramBoolean) {
    FragmentManagerImpl.DEBUG = paramBoolean;
  }
  
  public abstract void addOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener);
  
  public abstract FragmentTransaction beginTransaction();
  
  public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);
  
  public abstract boolean executePendingTransactions();
  
  public abstract Fragment findFragmentById(int paramInt);
  
  public abstract Fragment findFragmentByTag(String paramString);
  
  public abstract BackStackEntry getBackStackEntryAt(int paramInt);
  
  public abstract int getBackStackEntryCount();
  
  public abstract Fragment getFragment(Bundle paramBundle, String paramString);
  
  public FragmentFactory getFragmentFactory() {
    if (this.mFragmentFactory == null)
      this.mFragmentFactory = DEFAULT_FACTORY; 
    return this.mFragmentFactory;
  }
  
  public abstract List<Fragment> getFragments();
  
  public abstract Fragment getPrimaryNavigationFragment();
  
  public abstract boolean isDestroyed();
  
  public abstract boolean isStateSaved();
  
  @Deprecated
  public FragmentTransaction openTransaction() {
    return beginTransaction();
  }
  
  public abstract void popBackStack();
  
  public abstract void popBackStack(int paramInt1, int paramInt2);
  
  public abstract void popBackStack(String paramString, int paramInt);
  
  public abstract boolean popBackStackImmediate();
  
  public abstract boolean popBackStackImmediate(int paramInt1, int paramInt2);
  
  public abstract boolean popBackStackImmediate(String paramString, int paramInt);
  
  public abstract void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment);
  
  public abstract void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean);
  
  public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener);
  
  public abstract Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment);
  
  public void setFragmentFactory(FragmentFactory paramFragmentFactory) {
    this.mFragmentFactory = paramFragmentFactory;
  }
  
  public abstract void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks);
  
  public static interface BackStackEntry {
    CharSequence getBreadCrumbShortTitle();
    
    int getBreadCrumbShortTitleRes();
    
    CharSequence getBreadCrumbTitle();
    
    int getBreadCrumbTitleRes();
    
    int getId();
    
    String getName();
  }
  
  public static abstract class FragmentLifecycleCallbacks {
    public void onFragmentActivityCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentAttached(FragmentManager param1FragmentManager, Fragment param1Fragment, Context param1Context) {}
    
    public void onFragmentCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentDestroyed(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentDetached(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentPaused(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentPreAttached(FragmentManager param1FragmentManager, Fragment param1Fragment, Context param1Context) {}
    
    public void onFragmentPreCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentResumed(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentSaveInstanceState(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentStarted(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentStopped(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentViewCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, View param1View, Bundle param1Bundle) {}
    
    public void onFragmentViewDestroyed(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
  }
  
  public static interface OnBackStackChangedListener {
    void onBackStackChanged();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */