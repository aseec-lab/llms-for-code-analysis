package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;

public class FragmentActivity extends ComponentActivity implements ActivityCompat.OnRequestPermissionsResultCallback, ActivityCompat.RequestPermissionsRequestCodeValidator {
  static final String ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies";
  
  static final String FRAGMENTS_TAG = "android:support:fragments";
  
  static final int MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534;
  
  static final String NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index";
  
  static final String REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who";
  
  private static final String TAG = "FragmentActivity";
  
  boolean mCreated;
  
  final LifecycleRegistry mFragmentLifecycleRegistry = new LifecycleRegistry((LifecycleOwner)this);
  
  final FragmentController mFragments = FragmentController.createController(new HostCallbacks());
  
  int mNextCandidateRequestIndex;
  
  SparseArrayCompat<String> mPendingFragmentActivityResults;
  
  boolean mRequestedPermissionsFromFragment;
  
  boolean mResumed;
  
  boolean mStartedActivityFromFragment;
  
  boolean mStartedIntentSenderFromFragment;
  
  boolean mStopped = true;
  
  public FragmentActivity() {}
  
  public FragmentActivity(int paramInt) {
    super(paramInt);
  }
  
  private int allocateRequestIndex(Fragment paramFragment) {
    if (this.mPendingFragmentActivityResults.size() < 65534) {
      while (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) >= 0)
        this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534; 
      int i = this.mNextCandidateRequestIndex;
      this.mPendingFragmentActivityResults.put(i, paramFragment.mWho);
      this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
      return i;
    } 
    throw new IllegalStateException("Too many pending Fragment activity results.");
  }
  
  static void checkForValidRequestCode(int paramInt) {
    if ((paramInt & 0xFFFF0000) == 0)
      return; 
    throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
  }
  
  private void markFragmentsCreated() {
    do {
    
    } while (markState(getSupportFragmentManager(), Lifecycle.State.CREATED));
  }
  
  private static boolean markState(FragmentManager paramFragmentManager, Lifecycle.State paramState) {
    Iterator<Fragment> iterator = paramFragmentManager.getFragments().iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      Fragment fragment = iterator.next();
      if (fragment == null)
        continue; 
      boolean bool1 = bool;
      if (fragment.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
        fragment.mLifecycleRegistry.setCurrentState(paramState);
        bool1 = true;
      } 
      bool = bool1;
      if (fragment.getHost() != null)
        bool = bool1 | markState(fragment.getChildFragmentManager(), paramState); 
    } 
    return bool;
  }
  
  final View dispatchFragmentsOnCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return this.mFragments.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("Local FragmentActivity ");
    paramPrintWriter.print(Integer.toHexString(System.identityHashCode(this)));
    paramPrintWriter.println(" State:");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("  ");
    String str = stringBuilder.toString();
    paramPrintWriter.print(str);
    paramPrintWriter.print("mCreated=");
    paramPrintWriter.print(this.mCreated);
    paramPrintWriter.print(" mResumed=");
    paramPrintWriter.print(this.mResumed);
    paramPrintWriter.print(" mStopped=");
    paramPrintWriter.print(this.mStopped);
    if (getApplication() != null)
      LoaderManager.getInstance((LifecycleOwner)this).dump(str, paramFileDescriptor, paramPrintWriter, paramArrayOfString); 
    this.mFragments.getSupportFragmentManager().dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }
  
  public FragmentManager getSupportFragmentManager() {
    return this.mFragments.getSupportFragmentManager();
  }
  
  @Deprecated
  public LoaderManager getSupportLoaderManager() {
    return LoaderManager.getInstance((LifecycleOwner)this);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    StringBuilder stringBuilder;
    this.mFragments.noteStateNotSaved();
    int i = paramInt1 >> 16;
    if (i != 0) {
      String str = (String)this.mPendingFragmentActivityResults.get(--i);
      this.mPendingFragmentActivityResults.remove(i);
      if (str == null) {
        Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
        return;
      } 
      Fragment fragment = this.mFragments.findFragmentByWho(str);
      if (fragment == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Activity result no fragment exists for who: ");
        stringBuilder.append(str);
        Log.w("FragmentActivity", stringBuilder.toString());
      } else {
        fragment.onActivityResult(paramInt1 & 0xFFFF, paramInt2, (Intent)stringBuilder);
      } 
      return;
    } 
    ActivityCompat.PermissionCompatDelegate permissionCompatDelegate = ActivityCompat.getPermissionCompatDelegate();
    if (permissionCompatDelegate != null && permissionCompatDelegate.onActivityResult((Activity)this, paramInt1, paramInt2, (Intent)stringBuilder))
      return; 
    super.onActivityResult(paramInt1, paramInt2, (Intent)stringBuilder);
  }
  
  public void onAttachFragment(Fragment paramFragment) {}
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    this.mFragments.noteStateNotSaved();
    this.mFragments.dispatchConfigurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle) {
    this.mFragments.attachHost(null);
    if (paramBundle != null) {
      Parcelable parcelable = paramBundle.getParcelable("android:support:fragments");
      this.mFragments.restoreSaveState(parcelable);
      if (paramBundle.containsKey("android:support:next_request_index")) {
        this.mNextCandidateRequestIndex = paramBundle.getInt("android:support:next_request_index");
        int[] arrayOfInt = paramBundle.getIntArray("android:support:request_indicies");
        String[] arrayOfString = paramBundle.getStringArray("android:support:request_fragment_who");
        if (arrayOfInt == null || arrayOfString == null || arrayOfInt.length != arrayOfString.length) {
          Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
        } else {
          this.mPendingFragmentActivityResults = new SparseArrayCompat(arrayOfInt.length);
          for (byte b = 0; b < arrayOfInt.length; b++)
            this.mPendingFragmentActivityResults.put(arrayOfInt[b], arrayOfString[b]); 
        } 
      } 
    } 
    if (this.mPendingFragmentActivityResults == null) {
      this.mPendingFragmentActivityResults = new SparseArrayCompat();
      this.mNextCandidateRequestIndex = 0;
    } 
    super.onCreate(paramBundle);
    this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    this.mFragments.dispatchCreate();
  }
  
  public boolean onCreatePanelMenu(int paramInt, Menu paramMenu) {
    return (paramInt == 0) ? (super.onCreatePanelMenu(paramInt, paramMenu) | this.mFragments.dispatchCreateOptionsMenu(paramMenu, getMenuInflater())) : super.onCreatePanelMenu(paramInt, paramMenu);
  }
  
  public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    View view = dispatchFragmentsOnCreateView(paramView, paramString, paramContext, paramAttributeSet);
    return (view == null) ? super.onCreateView(paramView, paramString, paramContext, paramAttributeSet) : view;
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    View view = dispatchFragmentsOnCreateView((View)null, paramString, paramContext, paramAttributeSet);
    return (view == null) ? super.onCreateView(paramString, paramContext, paramAttributeSet) : view;
  }
  
  protected void onDestroy() {
    super.onDestroy();
    this.mFragments.dispatchDestroy();
    this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
  }
  
  public void onLowMemory() {
    super.onLowMemory();
    this.mFragments.dispatchLowMemory();
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem) {
    return super.onMenuItemSelected(paramInt, paramMenuItem) ? true : ((paramInt != 0) ? ((paramInt != 6) ? false : this.mFragments.dispatchContextItemSelected(paramMenuItem)) : this.mFragments.dispatchOptionsItemSelected(paramMenuItem));
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean) {
    this.mFragments.dispatchMultiWindowModeChanged(paramBoolean);
  }
  
  protected void onNewIntent(Intent paramIntent) {
    super.onNewIntent(paramIntent);
    this.mFragments.noteStateNotSaved();
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu) {
    if (paramInt == 0)
      this.mFragments.dispatchOptionsMenuClosed(paramMenu); 
    super.onPanelClosed(paramInt, paramMenu);
  }
  
  protected void onPause() {
    super.onPause();
    this.mResumed = false;
    this.mFragments.dispatchPause();
    this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean) {
    this.mFragments.dispatchPictureInPictureModeChanged(paramBoolean);
  }
  
  protected void onPostResume() {
    super.onPostResume();
    onResumeFragments();
  }
  
  @Deprecated
  protected boolean onPrepareOptionsPanel(View paramView, Menu paramMenu) {
    return super.onPreparePanel(0, paramView, paramMenu);
  }
  
  public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu) {
    return (paramInt == 0) ? (onPrepareOptionsPanel(paramView, paramMenu) | this.mFragments.dispatchPrepareOptionsMenu(paramMenu)) : super.onPreparePanel(paramInt, paramView, paramMenu);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    this.mFragments.noteStateNotSaved();
    int i = paramInt >> 16 & 0xFFFF;
    if (i != 0) {
      StringBuilder stringBuilder;
      String str = (String)this.mPendingFragmentActivityResults.get(--i);
      this.mPendingFragmentActivityResults.remove(i);
      if (str == null) {
        Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
        return;
      } 
      Fragment fragment = this.mFragments.findFragmentByWho(str);
      if (fragment == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Activity result no fragment exists for who: ");
        stringBuilder.append(str);
        Log.w("FragmentActivity", stringBuilder.toString());
      } else {
        fragment.onRequestPermissionsResult(paramInt & 0xFFFF, (String[])stringBuilder, paramArrayOfint);
      } 
    } 
  }
  
  protected void onResume() {
    super.onResume();
    this.mResumed = true;
    this.mFragments.noteStateNotSaved();
    this.mFragments.execPendingActions();
  }
  
  protected void onResumeFragments() {
    this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    this.mFragments.dispatchResume();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    markFragmentsCreated();
    this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    Parcelable parcelable = this.mFragments.saveAllState();
    if (parcelable != null)
      paramBundle.putParcelable("android:support:fragments", parcelable); 
    if (this.mPendingFragmentActivityResults.size() > 0) {
      paramBundle.putInt("android:support:next_request_index", this.mNextCandidateRequestIndex);
      int[] arrayOfInt = new int[this.mPendingFragmentActivityResults.size()];
      String[] arrayOfString = new String[this.mPendingFragmentActivityResults.size()];
      for (byte b = 0; b < this.mPendingFragmentActivityResults.size(); b++) {
        arrayOfInt[b] = this.mPendingFragmentActivityResults.keyAt(b);
        arrayOfString[b] = (String)this.mPendingFragmentActivityResults.valueAt(b);
      } 
      paramBundle.putIntArray("android:support:request_indicies", arrayOfInt);
      paramBundle.putStringArray("android:support:request_fragment_who", arrayOfString);
    } 
  }
  
  protected void onStart() {
    super.onStart();
    this.mStopped = false;
    if (!this.mCreated) {
      this.mCreated = true;
      this.mFragments.dispatchActivityCreated();
    } 
    this.mFragments.noteStateNotSaved();
    this.mFragments.execPendingActions();
    this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    this.mFragments.dispatchStart();
  }
  
  public void onStateNotSaved() {
    this.mFragments.noteStateNotSaved();
  }
  
  protected void onStop() {
    super.onStop();
    this.mStopped = true;
    markFragmentsCreated();
    this.mFragments.dispatchStop();
    this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
  }
  
  void requestPermissionsFromFragment(Fragment paramFragment, String[] paramArrayOfString, int paramInt) {
    if (paramInt == -1) {
      ActivityCompat.requestPermissions((Activity)this, paramArrayOfString, paramInt);
      return;
    } 
    checkForValidRequestCode(paramInt);
    try {
      this.mRequestedPermissionsFromFragment = true;
      ActivityCompat.requestPermissions((Activity)this, paramArrayOfString, (allocateRequestIndex(paramFragment) + 1 << 16) + (paramInt & 0xFFFF));
      return;
    } finally {
      this.mRequestedPermissionsFromFragment = false;
    } 
  }
  
  public void setEnterSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    ActivityCompat.setEnterSharedElementCallback((Activity)this, paramSharedElementCallback);
  }
  
  public void setExitSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    ActivityCompat.setExitSharedElementCallback((Activity)this, paramSharedElementCallback);
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt) {
    if (!this.mStartedActivityFromFragment && paramInt != -1)
      checkForValidRequestCode(paramInt); 
    super.startActivityForResult(paramIntent, paramInt);
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt, Bundle paramBundle) {
    if (!this.mStartedActivityFromFragment && paramInt != -1)
      checkForValidRequestCode(paramInt); 
    super.startActivityForResult(paramIntent, paramInt, paramBundle);
  }
  
  public void startActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt) {
    startActivityFromFragment(paramFragment, paramIntent, paramInt, (Bundle)null);
  }
  
  public void startActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt, Bundle paramBundle) {
    this.mStartedActivityFromFragment = true;
    if (paramInt == -1)
      try {
        ActivityCompat.startActivityForResult((Activity)this, paramIntent, -1, paramBundle);
        return;
      } finally {
        this.mStartedActivityFromFragment = false;
      }  
    checkForValidRequestCode(paramInt);
    ActivityCompat.startActivityForResult((Activity)this, paramIntent, (allocateRequestIndex(paramFragment) + 1 << 16) + (paramInt & 0xFFFF), paramBundle);
    this.mStartedActivityFromFragment = false;
  }
  
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4) throws IntentSender.SendIntentException {
    if (!this.mStartedIntentSenderFromFragment && paramInt1 != -1)
      checkForValidRequestCode(paramInt1); 
    super.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4);
  }
  
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle) throws IntentSender.SendIntentException {
    if (!this.mStartedIntentSenderFromFragment && paramInt1 != -1)
      checkForValidRequestCode(paramInt1); 
    super.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
  }
  
  public void startIntentSenderFromFragment(Fragment paramFragment, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle) throws IntentSender.SendIntentException {
    this.mStartedIntentSenderFromFragment = true;
    if (paramInt1 == -1)
      try {
        ActivityCompat.startIntentSenderForResult((Activity)this, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
        return;
      } finally {
        this.mStartedIntentSenderFromFragment = false;
      }  
    checkForValidRequestCode(paramInt1);
    ActivityCompat.startIntentSenderForResult((Activity)this, paramIntentSender, (allocateRequestIndex(paramFragment) + 1 << 16) + (paramInt1 & 0xFFFF), paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
    this.mStartedIntentSenderFromFragment = false;
  }
  
  public void supportFinishAfterTransition() {
    ActivityCompat.finishAfterTransition((Activity)this);
  }
  
  @Deprecated
  public void supportInvalidateOptionsMenu() {
    invalidateOptionsMenu();
  }
  
  public void supportPostponeEnterTransition() {
    ActivityCompat.postponeEnterTransition((Activity)this);
  }
  
  public void supportStartPostponedEnterTransition() {
    ActivityCompat.startPostponedEnterTransition((Activity)this);
  }
  
  public final void validateRequestPermissionsRequestCode(int paramInt) {
    if (!this.mRequestedPermissionsFromFragment && paramInt != -1)
      checkForValidRequestCode(paramInt); 
  }
  
  class HostCallbacks extends FragmentHostCallback<FragmentActivity> implements ViewModelStoreOwner, OnBackPressedDispatcherOwner {
    final FragmentActivity this$0;
    
    public HostCallbacks() {
      super(FragmentActivity.this);
    }
    
    public Lifecycle getLifecycle() {
      return (Lifecycle)FragmentActivity.this.mFragmentLifecycleRegistry;
    }
    
    public OnBackPressedDispatcher getOnBackPressedDispatcher() {
      return FragmentActivity.this.getOnBackPressedDispatcher();
    }
    
    public ViewModelStore getViewModelStore() {
      return FragmentActivity.this.getViewModelStore();
    }
    
    public void onAttachFragment(Fragment param1Fragment) {
      FragmentActivity.this.onAttachFragment(param1Fragment);
    }
    
    public void onDump(String param1String, FileDescriptor param1FileDescriptor, PrintWriter param1PrintWriter, String[] param1ArrayOfString) {
      FragmentActivity.this.dump(param1String, param1FileDescriptor, param1PrintWriter, param1ArrayOfString);
    }
    
    public View onFindViewById(int param1Int) {
      return FragmentActivity.this.findViewById(param1Int);
    }
    
    public FragmentActivity onGetHost() {
      return FragmentActivity.this;
    }
    
    public LayoutInflater onGetLayoutInflater() {
      return FragmentActivity.this.getLayoutInflater().cloneInContext((Context)FragmentActivity.this);
    }
    
    public int onGetWindowAnimations() {
      int i;
      Window window = FragmentActivity.this.getWindow();
      if (window == null) {
        i = 0;
      } else {
        i = (window.getAttributes()).windowAnimations;
      } 
      return i;
    }
    
    public boolean onHasView() {
      boolean bool;
      Window window = FragmentActivity.this.getWindow();
      if (window != null && window.peekDecorView() != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean onHasWindowAnimations() {
      boolean bool;
      if (FragmentActivity.this.getWindow() != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onRequestPermissionsFromFragment(Fragment param1Fragment, String[] param1ArrayOfString, int param1Int) {
      FragmentActivity.this.requestPermissionsFromFragment(param1Fragment, param1ArrayOfString, param1Int);
    }
    
    public boolean onShouldSaveFragmentState(Fragment param1Fragment) {
      return FragmentActivity.this.isFinishing() ^ true;
    }
    
    public boolean onShouldShowRequestPermissionRationale(String param1String) {
      return ActivityCompat.shouldShowRequestPermissionRationale((Activity)FragmentActivity.this, param1String);
    }
    
    public void onStartActivityFromFragment(Fragment param1Fragment, Intent param1Intent, int param1Int) {
      FragmentActivity.this.startActivityFromFragment(param1Fragment, param1Intent, param1Int);
    }
    
    public void onStartActivityFromFragment(Fragment param1Fragment, Intent param1Intent, int param1Int, Bundle param1Bundle) {
      FragmentActivity.this.startActivityFromFragment(param1Fragment, param1Intent, param1Int, param1Bundle);
    }
    
    public void onStartIntentSenderFromFragment(Fragment param1Fragment, IntentSender param1IntentSender, int param1Int1, Intent param1Intent, int param1Int2, int param1Int3, int param1Int4, Bundle param1Bundle) throws IntentSender.SendIntentException {
      FragmentActivity.this.startIntentSenderFromFragment(param1Fragment, param1IntentSender, param1Int1, param1Intent, param1Int2, param1Int3, param1Int4, param1Bundle);
    }
    
    public void onSupportInvalidateOptionsMenu() {
      FragmentActivity.this.supportInvalidateOptionsMenu();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */