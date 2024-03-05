package androidx.lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class ReportFragment extends Fragment {
  private static final String REPORT_FRAGMENT_TAG = "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag";
  
  private ActivityInitializationListener mProcessListener;
  
  private void dispatch(Lifecycle.Event paramEvent) {
    Activity activity = getActivity();
    if (activity instanceof LifecycleRegistryOwner) {
      ((LifecycleRegistryOwner)activity).getLifecycle().handleLifecycleEvent(paramEvent);
      return;
    } 
    if (activity instanceof LifecycleOwner) {
      Lifecycle lifecycle = ((LifecycleOwner)activity).getLifecycle();
      if (lifecycle instanceof LifecycleRegistry)
        ((LifecycleRegistry)lifecycle).handleLifecycleEvent(paramEvent); 
    } 
  }
  
  private void dispatchCreate(ActivityInitializationListener paramActivityInitializationListener) {
    if (paramActivityInitializationListener != null)
      paramActivityInitializationListener.onCreate(); 
  }
  
  private void dispatchResume(ActivityInitializationListener paramActivityInitializationListener) {
    if (paramActivityInitializationListener != null)
      paramActivityInitializationListener.onResume(); 
  }
  
  private void dispatchStart(ActivityInitializationListener paramActivityInitializationListener) {
    if (paramActivityInitializationListener != null)
      paramActivityInitializationListener.onStart(); 
  }
  
  static ReportFragment get(Activity paramActivity) {
    return (ReportFragment)paramActivity.getFragmentManager().findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag");
  }
  
  public static void injectIfNeededIn(Activity paramActivity) {
    FragmentManager fragmentManager = paramActivity.getFragmentManager();
    if (fragmentManager.findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
      fragmentManager.beginTransaction().add(new ReportFragment(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
      fragmentManager.executePendingTransactions();
    } 
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    dispatchCreate(this.mProcessListener);
    dispatch(Lifecycle.Event.ON_CREATE);
  }
  
  public void onDestroy() {
    super.onDestroy();
    dispatch(Lifecycle.Event.ON_DESTROY);
    this.mProcessListener = null;
  }
  
  public void onPause() {
    super.onPause();
    dispatch(Lifecycle.Event.ON_PAUSE);
  }
  
  public void onResume() {
    super.onResume();
    dispatchResume(this.mProcessListener);
    dispatch(Lifecycle.Event.ON_RESUME);
  }
  
  public void onStart() {
    super.onStart();
    dispatchStart(this.mProcessListener);
    dispatch(Lifecycle.Event.ON_START);
  }
  
  public void onStop() {
    super.onStop();
    dispatch(Lifecycle.Event.ON_STOP);
  }
  
  void setProcessListener(ActivityInitializationListener paramActivityInitializationListener) {
    this.mProcessListener = paramActivityInitializationListener;
  }
  
  static interface ActivityInitializationListener {
    void onCreate();
    
    void onResume();
    
    void onStart();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\ReportFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */