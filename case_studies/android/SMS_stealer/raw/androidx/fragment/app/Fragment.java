package androidx.fragment.app;

import android.animation.Animator;
import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.core.app.SharedElementCallback;
import androidx.core.util.DebugUtils;
import androidx.core.view.LayoutInflaterCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {
  static final int ACTIVITY_CREATED = 2;
  
  static final int CREATED = 1;
  
  static final int INITIALIZING = 0;
  
  static final int RESUMED = 4;
  
  static final int STARTED = 3;
  
  static final Object USE_DEFAULT_TRANSITION = new Object();
  
  boolean mAdded;
  
  AnimationInfo mAnimationInfo;
  
  Bundle mArguments;
  
  int mBackStackNesting;
  
  private boolean mCalled;
  
  FragmentManagerImpl mChildFragmentManager = new FragmentManagerImpl();
  
  ViewGroup mContainer;
  
  int mContainerId;
  
  private int mContentLayoutId;
  
  boolean mDeferStart;
  
  boolean mDetached;
  
  int mFragmentId;
  
  FragmentManagerImpl mFragmentManager;
  
  boolean mFromLayout;
  
  boolean mHasMenu;
  
  boolean mHidden;
  
  boolean mHiddenChanged;
  
  FragmentHostCallback mHost;
  
  boolean mInLayout;
  
  View mInnerView;
  
  boolean mIsCreated;
  
  boolean mIsNewlyAdded;
  
  private Boolean mIsPrimaryNavigationFragment = null;
  
  LayoutInflater mLayoutInflater;
  
  LifecycleRegistry mLifecycleRegistry;
  
  Lifecycle.State mMaxState = Lifecycle.State.RESUMED;
  
  boolean mMenuVisible = true;
  
  Fragment mParentFragment;
  
  boolean mPerformedCreateView;
  
  float mPostponedAlpha;
  
  Runnable mPostponedDurationRunnable = new Runnable() {
      final Fragment this$0;
      
      public void run() {
        Fragment.this.startPostponedEnterTransition();
      }
    };
  
  boolean mRemoving;
  
  boolean mRestored;
  
  boolean mRetainInstance;
  
  boolean mRetainInstanceChangedWhileDetached;
  
  Bundle mSavedFragmentState;
  
  SavedStateRegistryController mSavedStateRegistryController;
  
  Boolean mSavedUserVisibleHint;
  
  SparseArray<Parcelable> mSavedViewState;
  
  int mState = 0;
  
  String mTag;
  
  Fragment mTarget;
  
  int mTargetRequestCode;
  
  String mTargetWho = null;
  
  boolean mUserVisibleHint = true;
  
  View mView;
  
  FragmentViewLifecycleOwner mViewLifecycleOwner;
  
  MutableLiveData<LifecycleOwner> mViewLifecycleOwnerLiveData = new MutableLiveData();
  
  String mWho = UUID.randomUUID().toString();
  
  public Fragment() {
    initLifecycle();
  }
  
  public Fragment(int paramInt) {
    this();
    this.mContentLayoutId = paramInt;
  }
  
  private AnimationInfo ensureAnimationInfo() {
    if (this.mAnimationInfo == null)
      this.mAnimationInfo = new AnimationInfo(); 
    return this.mAnimationInfo;
  }
  
  private void initLifecycle() {
    this.mLifecycleRegistry = new LifecycleRegistry(this);
    this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
    if (Build.VERSION.SDK_INT >= 19)
      this.mLifecycleRegistry.addObserver((LifecycleObserver)new LifecycleEventObserver() {
            final Fragment this$0;
            
            public void onStateChanged(LifecycleOwner param1LifecycleOwner, Lifecycle.Event param1Event) {
              if (param1Event == Lifecycle.Event.ON_STOP && Fragment.this.mView != null)
                Fragment.this.mView.cancelPendingInputEvents(); 
            }
          }); 
  }
  
  @Deprecated
  public static Fragment instantiate(Context paramContext, String paramString) {
    return instantiate(paramContext, paramString, null);
  }
  
  @Deprecated
  public static Fragment instantiate(Context paramContext, String paramString, Bundle paramBundle) {
    try {
      Fragment fragment = FragmentFactory.loadFragmentClass(paramContext.getClassLoader(), paramString).getConstructor(new Class[0]).newInstance(new Object[0]);
      if (paramBundle != null) {
        paramBundle.setClassLoader(fragment.getClass().getClassLoader());
        fragment.setArguments(paramBundle);
      } 
      return fragment;
    } catch (InstantiationException instantiationException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": make sure class name exists, is public, and has an empty constructor that is public");
      throw new InstantiationException(stringBuilder.toString(), instantiationException);
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": make sure class name exists, is public, and has an empty constructor that is public");
      throw new InstantiationException(stringBuilder.toString(), illegalAccessException);
    } catch (NoSuchMethodException noSuchMethodException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": could not find Fragment constructor");
      throw new InstantiationException(stringBuilder.toString(), noSuchMethodException);
    } catch (InvocationTargetException invocationTargetException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": calling Fragment constructor caused an exception");
      throw new InstantiationException(stringBuilder.toString(), invocationTargetException);
    } 
  }
  
  void callStartTransitionListener() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    OnStartEnterTransitionListener onStartEnterTransitionListener = null;
    if (animationInfo != null) {
      animationInfo.mEnterTransitionPostponed = false;
      onStartEnterTransitionListener = this.mAnimationInfo.mStartEnterTransitionListener;
      this.mAnimationInfo.mStartEnterTransitionListener = null;
    } 
    if (onStartEnterTransitionListener != null)
      onStartEnterTransitionListener.onStartEnterTransition(); 
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mFragmentId=#");
    paramPrintWriter.print(Integer.toHexString(this.mFragmentId));
    paramPrintWriter.print(" mContainerId=#");
    paramPrintWriter.print(Integer.toHexString(this.mContainerId));
    paramPrintWriter.print(" mTag=");
    paramPrintWriter.println(this.mTag);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mState=");
    paramPrintWriter.print(this.mState);
    paramPrintWriter.print(" mWho=");
    paramPrintWriter.print(this.mWho);
    paramPrintWriter.print(" mBackStackNesting=");
    paramPrintWriter.println(this.mBackStackNesting);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mAdded=");
    paramPrintWriter.print(this.mAdded);
    paramPrintWriter.print(" mRemoving=");
    paramPrintWriter.print(this.mRemoving);
    paramPrintWriter.print(" mFromLayout=");
    paramPrintWriter.print(this.mFromLayout);
    paramPrintWriter.print(" mInLayout=");
    paramPrintWriter.println(this.mInLayout);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mHidden=");
    paramPrintWriter.print(this.mHidden);
    paramPrintWriter.print(" mDetached=");
    paramPrintWriter.print(this.mDetached);
    paramPrintWriter.print(" mMenuVisible=");
    paramPrintWriter.print(this.mMenuVisible);
    paramPrintWriter.print(" mHasMenu=");
    paramPrintWriter.println(this.mHasMenu);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mRetainInstance=");
    paramPrintWriter.print(this.mRetainInstance);
    paramPrintWriter.print(" mUserVisibleHint=");
    paramPrintWriter.println(this.mUserVisibleHint);
    if (this.mFragmentManager != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mFragmentManager=");
      paramPrintWriter.println(this.mFragmentManager);
    } 
    if (this.mHost != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mHost=");
      paramPrintWriter.println(this.mHost);
    } 
    if (this.mParentFragment != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mParentFragment=");
      paramPrintWriter.println(this.mParentFragment);
    } 
    if (this.mArguments != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mArguments=");
      paramPrintWriter.println(this.mArguments);
    } 
    if (this.mSavedFragmentState != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedFragmentState=");
      paramPrintWriter.println(this.mSavedFragmentState);
    } 
    if (this.mSavedViewState != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedViewState=");
      paramPrintWriter.println(this.mSavedViewState);
    } 
    Fragment fragment = getTargetFragment();
    if (fragment != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mTarget=");
      paramPrintWriter.print(fragment);
      paramPrintWriter.print(" mTargetRequestCode=");
      paramPrintWriter.println(this.mTargetRequestCode);
    } 
    if (getNextAnim() != 0) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mNextAnim=");
      paramPrintWriter.println(getNextAnim());
    } 
    if (this.mContainer != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mContainer=");
      paramPrintWriter.println(this.mContainer);
    } 
    if (this.mView != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mView=");
      paramPrintWriter.println(this.mView);
    } 
    if (this.mInnerView != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mInnerView=");
      paramPrintWriter.println(this.mView);
    } 
    if (getAnimatingAway() != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mAnimatingAway=");
      paramPrintWriter.println(getAnimatingAway());
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mStateAfterAnimating=");
      paramPrintWriter.println(getStateAfterAnimating());
    } 
    if (getContext() != null)
      LoaderManager.getInstance(this).dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString); 
    paramPrintWriter.print(paramString);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Child ");
    stringBuilder.append(this.mChildFragmentManager);
    stringBuilder.append(":");
    paramPrintWriter.println(stringBuilder.toString());
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("  ");
    fragmentManagerImpl.dump(stringBuilder.toString(), paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }
  
  public final boolean equals(Object paramObject) {
    return super.equals(paramObject);
  }
  
  Fragment findFragmentByWho(String paramString) {
    return paramString.equals(this.mWho) ? this : this.mChildFragmentManager.findFragmentByWho(paramString);
  }
  
  public final FragmentActivity getActivity() {
    FragmentActivity fragmentActivity;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      fragmentActivity = (FragmentActivity)fragmentHostCallback.getActivity();
    } 
    return fragmentActivity;
  }
  
  public boolean getAllowEnterTransitionOverlap() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null || animationInfo.mAllowEnterTransitionOverlap == null) ? true : this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue();
  }
  
  public boolean getAllowReturnTransitionOverlap() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null || animationInfo.mAllowReturnTransitionOverlap == null) ? true : this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue();
  }
  
  View getAnimatingAway() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mAnimatingAway;
  }
  
  Animator getAnimator() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mAnimator;
  }
  
  public final Bundle getArguments() {
    return this.mArguments;
  }
  
  public final FragmentManager getChildFragmentManager() {
    if (this.mHost != null)
      return this.mChildFragmentManager; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" has not been attached yet.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public Context getContext() {
    Context context;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      context = fragmentHostCallback.getContext();
    } 
    return context;
  }
  
  public Object getEnterTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mEnterTransition;
  }
  
  SharedElementCallback getEnterTransitionCallback() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mEnterTransitionCallback;
  }
  
  public Object getExitTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mExitTransition;
  }
  
  SharedElementCallback getExitTransitionCallback() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mExitTransitionCallback;
  }
  
  public final FragmentManager getFragmentManager() {
    return this.mFragmentManager;
  }
  
  public final Object getHost() {
    FragmentHostCallback<FragmentHostCallback> fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      fragmentHostCallback = fragmentHostCallback.onGetHost();
    } 
    return fragmentHostCallback;
  }
  
  public final int getId() {
    return this.mFragmentId;
  }
  
  public final LayoutInflater getLayoutInflater() {
    LayoutInflater layoutInflater2 = this.mLayoutInflater;
    LayoutInflater layoutInflater1 = layoutInflater2;
    if (layoutInflater2 == null)
      layoutInflater1 = performGetLayoutInflater(null); 
    return layoutInflater1;
  }
  
  @Deprecated
  public LayoutInflater getLayoutInflater(Bundle paramBundle) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      LayoutInflater layoutInflater = fragmentHostCallback.onGetLayoutInflater();
      LayoutInflaterCompat.setFactory2(layoutInflater, this.mChildFragmentManager.getLayoutInflaterFactory());
      return layoutInflater;
    } 
    throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
  }
  
  public Lifecycle getLifecycle() {
    return (Lifecycle)this.mLifecycleRegistry;
  }
  
  @Deprecated
  public LoaderManager getLoaderManager() {
    return LoaderManager.getInstance(this);
  }
  
  int getNextAnim() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mNextAnim;
  }
  
  int getNextTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mNextTransition;
  }
  
  int getNextTransitionStyle() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mNextTransitionStyle;
  }
  
  public final Fragment getParentFragment() {
    return this.mParentFragment;
  }
  
  public Object getReenterTransition() {
    Object object = this.mAnimationInfo;
    if (object == null)
      return null; 
    if (((AnimationInfo)object).mReenterTransition == USE_DEFAULT_TRANSITION) {
      Object object1 = getExitTransition();
    } else {
      object = this.mAnimationInfo.mReenterTransition;
    } 
    return object;
  }
  
  public final Resources getResources() {
    return requireContext().getResources();
  }
  
  public final boolean getRetainInstance() {
    return this.mRetainInstance;
  }
  
  public Object getReturnTransition() {
    Object object = this.mAnimationInfo;
    if (object == null)
      return null; 
    if (((AnimationInfo)object).mReturnTransition == USE_DEFAULT_TRANSITION) {
      Object object1 = getEnterTransition();
    } else {
      object = this.mAnimationInfo.mReturnTransition;
    } 
    return object;
  }
  
  public final SavedStateRegistry getSavedStateRegistry() {
    return this.mSavedStateRegistryController.getSavedStateRegistry();
  }
  
  public Object getSharedElementEnterTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mSharedElementEnterTransition;
  }
  
  public Object getSharedElementReturnTransition() {
    Object object = this.mAnimationInfo;
    if (object == null)
      return null; 
    if (((AnimationInfo)object).mSharedElementReturnTransition == USE_DEFAULT_TRANSITION) {
      Object object1 = getSharedElementEnterTransition();
    } else {
      object = this.mAnimationInfo.mSharedElementReturnTransition;
    } 
    return object;
  }
  
  int getStateAfterAnimating() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mStateAfterAnimating;
  }
  
  public final String getString(int paramInt) {
    return getResources().getString(paramInt);
  }
  
  public final String getString(int paramInt, Object... paramVarArgs) {
    return getResources().getString(paramInt, paramVarArgs);
  }
  
  public final String getTag() {
    return this.mTag;
  }
  
  public final Fragment getTargetFragment() {
    Fragment fragment = this.mTarget;
    if (fragment != null)
      return fragment; 
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    return (fragmentManagerImpl != null && this.mTargetWho != null) ? fragmentManagerImpl.mActive.get(this.mTargetWho) : null;
  }
  
  public final int getTargetRequestCode() {
    return this.mTargetRequestCode;
  }
  
  public final CharSequence getText(int paramInt) {
    return getResources().getText(paramInt);
  }
  
  @Deprecated
  public boolean getUserVisibleHint() {
    return this.mUserVisibleHint;
  }
  
  public View getView() {
    return this.mView;
  }
  
  public LifecycleOwner getViewLifecycleOwner() {
    FragmentViewLifecycleOwner fragmentViewLifecycleOwner = this.mViewLifecycleOwner;
    if (fragmentViewLifecycleOwner != null)
      return fragmentViewLifecycleOwner; 
    throw new IllegalStateException("Can't access the Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or after onDestroyView()");
  }
  
  public LiveData<LifecycleOwner> getViewLifecycleOwnerLiveData() {
    return (LiveData<LifecycleOwner>)this.mViewLifecycleOwnerLiveData;
  }
  
  public ViewModelStore getViewModelStore() {
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    if (fragmentManagerImpl != null)
      return fragmentManagerImpl.getViewModelStore(this); 
    throw new IllegalStateException("Can't access ViewModels from detached fragment");
  }
  
  public final boolean hasOptionsMenu() {
    return this.mHasMenu;
  }
  
  public final int hashCode() {
    return super.hashCode();
  }
  
  void initState() {
    initLifecycle();
    this.mWho = UUID.randomUUID().toString();
    this.mAdded = false;
    this.mRemoving = false;
    this.mFromLayout = false;
    this.mInLayout = false;
    this.mRestored = false;
    this.mBackStackNesting = 0;
    this.mFragmentManager = null;
    this.mChildFragmentManager = new FragmentManagerImpl();
    this.mHost = null;
    this.mFragmentId = 0;
    this.mContainerId = 0;
    this.mTag = null;
    this.mHidden = false;
    this.mDetached = false;
  }
  
  public final boolean isAdded() {
    boolean bool;
    if (this.mHost != null && this.mAdded) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isDetached() {
    return this.mDetached;
  }
  
  public final boolean isHidden() {
    return this.mHidden;
  }
  
  boolean isHideReplaced() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? false : animationInfo.mIsHideReplaced;
  }
  
  final boolean isInBackStack() {
    boolean bool;
    if (this.mBackStackNesting > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isInLayout() {
    return this.mInLayout;
  }
  
  public final boolean isMenuVisible() {
    return this.mMenuVisible;
  }
  
  boolean isPostponed() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? false : animationInfo.mEnterTransitionPostponed;
  }
  
  public final boolean isRemoving() {
    return this.mRemoving;
  }
  
  public final boolean isResumed() {
    boolean bool;
    if (this.mState >= 4) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isStateSaved() {
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    return (fragmentManagerImpl == null) ? false : fragmentManagerImpl.isStateSaved();
  }
  
  public final boolean isVisible() {
    if (isAdded() && !isHidden()) {
      View view = this.mView;
      if (view != null && view.getWindowToken() != null && this.mView.getVisibility() == 0)
        return true; 
    } 
    return false;
  }
  
  void noteStateNotSaved() {
    this.mChildFragmentManager.noteStateNotSaved();
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    this.mCalled = true;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {}
  
  @Deprecated
  public void onAttach(Activity paramActivity) {
    this.mCalled = true;
  }
  
  public void onAttach(Context paramContext) {
    Activity activity;
    this.mCalled = true;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      activity = fragmentHostCallback.getActivity();
    } 
    if (activity != null) {
      this.mCalled = false;
      onAttach(activity);
    } 
  }
  
  public void onAttachFragment(Fragment paramFragment) {}
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    this.mCalled = true;
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem) {
    return false;
  }
  
  public void onCreate(Bundle paramBundle) {
    this.mCalled = true;
    restoreChildFragmentState(paramBundle);
    if (!this.mChildFragmentManager.isStateAtLeast(1))
      this.mChildFragmentManager.dispatchCreate(); 
  }
  
  public Animation onCreateAnimation(int paramInt1, boolean paramBoolean, int paramInt2) {
    return null;
  }
  
  public Animator onCreateAnimator(int paramInt1, boolean paramBoolean, int paramInt2) {
    return null;
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo) {
    requireActivity().onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {}
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    int i = this.mContentLayoutId;
    return (i != 0) ? paramLayoutInflater.inflate(i, paramViewGroup, false) : null;
  }
  
  public void onDestroy() {
    this.mCalled = true;
  }
  
  public void onDestroyOptionsMenu() {}
  
  public void onDestroyView() {
    this.mCalled = true;
  }
  
  public void onDetach() {
    this.mCalled = true;
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle) {
    return getLayoutInflater(paramBundle);
  }
  
  public void onHiddenChanged(boolean paramBoolean) {}
  
  @Deprecated
  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle) {
    this.mCalled = true;
  }
  
  public void onInflate(Context paramContext, AttributeSet paramAttributeSet, Bundle paramBundle) {
    Activity activity;
    this.mCalled = true;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      activity = fragmentHostCallback.getActivity();
    } 
    if (activity != null) {
      this.mCalled = false;
      onInflate(activity, paramAttributeSet, paramBundle);
    } 
  }
  
  public void onLowMemory() {
    this.mCalled = true;
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean) {}
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    return false;
  }
  
  public void onOptionsMenuClosed(Menu paramMenu) {}
  
  public void onPause() {
    this.mCalled = true;
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean) {}
  
  public void onPrepareOptionsMenu(Menu paramMenu) {}
  
  public void onPrimaryNavigationFragmentChanged(boolean paramBoolean) {}
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {}
  
  public void onResume() {
    this.mCalled = true;
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {}
  
  public void onStart() {
    this.mCalled = true;
  }
  
  public void onStop() {
    this.mCalled = true;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {}
  
  public void onViewStateRestored(Bundle paramBundle) {
    this.mCalled = true;
  }
  
  void performActivityCreated(Bundle paramBundle) {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mState = 2;
    this.mCalled = false;
    onActivityCreated(paramBundle);
    if (this.mCalled) {
      this.mChildFragmentManager.dispatchActivityCreated();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onActivityCreated()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performAttach() {
    this.mChildFragmentManager.attachController(this.mHost, new FragmentContainer() {
          final Fragment this$0;
          
          public View onFindViewById(int param1Int) {
            if (Fragment.this.mView != null)
              return Fragment.this.mView.findViewById(param1Int); 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" does not have a view");
            throw new IllegalStateException(stringBuilder.toString());
          }
          
          public boolean onHasView() {
            boolean bool;
            if (Fragment.this.mView != null) {
              bool = true;
            } else {
              bool = false;
            } 
            return bool;
          }
        },  this);
    this.mCalled = false;
    onAttach(this.mHost.getContext());
    if (this.mCalled)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onAttach()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performConfigurationChanged(Configuration paramConfiguration) {
    onConfigurationChanged(paramConfiguration);
    this.mChildFragmentManager.dispatchConfigurationChanged(paramConfiguration);
  }
  
  boolean performContextItemSelected(MenuItem paramMenuItem) {
    if (!this.mHidden) {
      if (onContextItemSelected(paramMenuItem))
        return true; 
      if (this.mChildFragmentManager.dispatchContextItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  void performCreate(Bundle paramBundle) {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mState = 1;
    this.mCalled = false;
    this.mSavedStateRegistryController.performRestore(paramBundle);
    onCreate(paramBundle);
    this.mIsCreated = true;
    if (this.mCalled) {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onCreate()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  boolean performCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {
    boolean bool3 = this.mHidden;
    boolean bool2 = false;
    boolean bool1 = false;
    if (!bool3) {
      boolean bool = bool1;
      if (this.mHasMenu) {
        bool = bool1;
        if (this.mMenuVisible) {
          bool = true;
          onCreateOptionsMenu(paramMenu, paramMenuInflater);
        } 
      } 
      bool2 = bool | this.mChildFragmentManager.dispatchCreateOptionsMenu(paramMenu, paramMenuInflater);
    } 
    return bool2;
  }
  
  void performCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mPerformedCreateView = true;
    this.mViewLifecycleOwner = new FragmentViewLifecycleOwner();
    View view = onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mView = view;
    if (view != null) {
      this.mViewLifecycleOwner.initialize();
      this.mViewLifecycleOwnerLiveData.setValue(this.mViewLifecycleOwner);
    } else {
      if (!this.mViewLifecycleOwner.isInitialized()) {
        this.mViewLifecycleOwner = null;
        return;
      } 
      throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
    } 
  }
  
  void performDestroy() {
    this.mChildFragmentManager.dispatchDestroy();
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    this.mState = 0;
    this.mCalled = false;
    this.mIsCreated = false;
    onDestroy();
    if (this.mCalled)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onDestroy()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performDestroyView() {
    this.mChildFragmentManager.dispatchDestroyView();
    if (this.mView != null)
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY); 
    this.mState = 1;
    this.mCalled = false;
    onDestroyView();
    if (this.mCalled) {
      LoaderManager.getInstance(this).markForRedelivery();
      this.mPerformedCreateView = false;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onDestroyView()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performDetach() {
    this.mCalled = false;
    onDetach();
    this.mLayoutInflater = null;
    if (this.mCalled) {
      if (!this.mChildFragmentManager.isDestroyed()) {
        this.mChildFragmentManager.dispatchDestroy();
        this.mChildFragmentManager = new FragmentManagerImpl();
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onDetach()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  LayoutInflater performGetLayoutInflater(Bundle paramBundle) {
    LayoutInflater layoutInflater = onGetLayoutInflater(paramBundle);
    this.mLayoutInflater = layoutInflater;
    return layoutInflater;
  }
  
  void performLowMemory() {
    onLowMemory();
    this.mChildFragmentManager.dispatchLowMemory();
  }
  
  void performMultiWindowModeChanged(boolean paramBoolean) {
    onMultiWindowModeChanged(paramBoolean);
    this.mChildFragmentManager.dispatchMultiWindowModeChanged(paramBoolean);
  }
  
  boolean performOptionsItemSelected(MenuItem paramMenuItem) {
    if (!this.mHidden) {
      if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(paramMenuItem))
        return true; 
      if (this.mChildFragmentManager.dispatchOptionsItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  void performOptionsMenuClosed(Menu paramMenu) {
    if (!this.mHidden) {
      if (this.mHasMenu && this.mMenuVisible)
        onOptionsMenuClosed(paramMenu); 
      this.mChildFragmentManager.dispatchOptionsMenuClosed(paramMenu);
    } 
  }
  
  void performPause() {
    this.mChildFragmentManager.dispatchPause();
    if (this.mView != null)
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE); 
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    this.mState = 3;
    this.mCalled = false;
    onPause();
    if (this.mCalled)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onPause()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performPictureInPictureModeChanged(boolean paramBoolean) {
    onPictureInPictureModeChanged(paramBoolean);
    this.mChildFragmentManager.dispatchPictureInPictureModeChanged(paramBoolean);
  }
  
  boolean performPrepareOptionsMenu(Menu paramMenu) {
    boolean bool3 = this.mHidden;
    boolean bool2 = false;
    boolean bool1 = false;
    if (!bool3) {
      boolean bool = bool1;
      if (this.mHasMenu) {
        bool = bool1;
        if (this.mMenuVisible) {
          bool = true;
          onPrepareOptionsMenu(paramMenu);
        } 
      } 
      bool2 = bool | this.mChildFragmentManager.dispatchPrepareOptionsMenu(paramMenu);
    } 
    return bool2;
  }
  
  void performPrimaryNavigationFragmentChanged() {
    boolean bool = this.mFragmentManager.isPrimaryNavigation(this);
    Boolean bool1 = this.mIsPrimaryNavigationFragment;
    if (bool1 == null || bool1.booleanValue() != bool) {
      this.mIsPrimaryNavigationFragment = Boolean.valueOf(bool);
      onPrimaryNavigationFragmentChanged(bool);
      this.mChildFragmentManager.dispatchPrimaryNavigationFragmentChanged();
    } 
  }
  
  void performResume() {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mChildFragmentManager.execPendingActions();
    this.mState = 4;
    this.mCalled = false;
    onResume();
    if (this.mCalled) {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
      if (this.mView != null)
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME); 
      this.mChildFragmentManager.dispatchResume();
      this.mChildFragmentManager.execPendingActions();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onResume()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performSaveInstanceState(Bundle paramBundle) {
    onSaveInstanceState(paramBundle);
    this.mSavedStateRegistryController.performSave(paramBundle);
    Parcelable parcelable = this.mChildFragmentManager.saveAllState();
    if (parcelable != null)
      paramBundle.putParcelable("android:support:fragments", parcelable); 
  }
  
  void performStart() {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mChildFragmentManager.execPendingActions();
    this.mState = 3;
    this.mCalled = false;
    onStart();
    if (this.mCalled) {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
      if (this.mView != null)
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START); 
      this.mChildFragmentManager.dispatchStart();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onStart()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performStop() {
    this.mChildFragmentManager.dispatchStop();
    if (this.mView != null)
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP); 
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    this.mState = 2;
    this.mCalled = false;
    onStop();
    if (this.mCalled)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onStop()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  public void postponeEnterTransition() {
    (ensureAnimationInfo()).mEnterTransitionPostponed = true;
  }
  
  public final void postponeEnterTransition(long paramLong, TimeUnit paramTimeUnit) {
    Handler handler;
    (ensureAnimationInfo()).mEnterTransitionPostponed = true;
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    if (fragmentManagerImpl != null) {
      handler = fragmentManagerImpl.mHost.getHandler();
    } else {
      handler = new Handler(Looper.getMainLooper());
    } 
    handler.removeCallbacks(this.mPostponedDurationRunnable);
    handler.postDelayed(this.mPostponedDurationRunnable, paramTimeUnit.toMillis(paramLong));
  }
  
  public void registerForContextMenu(View paramView) {
    paramView.setOnCreateContextMenuListener(this);
  }
  
  public final void requestPermissions(String[] paramArrayOfString, int paramInt) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onRequestPermissionsFromFragment(this, paramArrayOfString, paramInt);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final FragmentActivity requireActivity() {
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null)
      return fragmentActivity; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to an activity.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final Bundle requireArguments() {
    Bundle bundle = getArguments();
    if (bundle != null)
      return bundle; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" does not have any arguments.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final Context requireContext() {
    Context context = getContext();
    if (context != null)
      return context; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to a context.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final FragmentManager requireFragmentManager() {
    FragmentManager fragmentManager = getFragmentManager();
    if (fragmentManager != null)
      return fragmentManager; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not associated with a fragment manager.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final Object requireHost() {
    Object object = getHost();
    if (object != null)
      return object; 
    object = new StringBuilder();
    object.append("Fragment ");
    object.append(this);
    object.append(" not attached to a host.");
    throw new IllegalStateException(object.toString());
  }
  
  public final Fragment requireParentFragment() {
    StringBuilder stringBuilder;
    Fragment fragment = getParentFragment();
    if (fragment == null) {
      if (getContext() == null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Fragment ");
        stringBuilder1.append(this);
        stringBuilder1.append(" is not attached to any Fragment or host");
        throw new IllegalStateException(stringBuilder1.toString());
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("Fragment ");
      stringBuilder.append(this);
      stringBuilder.append(" is not a child Fragment, it is directly attached to ");
      stringBuilder.append(getContext());
      throw new IllegalStateException(stringBuilder.toString());
    } 
    return (Fragment)stringBuilder;
  }
  
  public final View requireView() {
    View view = getView();
    if (view != null)
      return view; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not return a View from onCreateView() or this was called before onCreateView().");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  void restoreChildFragmentState(Bundle paramBundle) {
    if (paramBundle != null) {
      Parcelable parcelable = paramBundle.getParcelable("android:support:fragments");
      if (parcelable != null) {
        this.mChildFragmentManager.restoreSaveState(parcelable);
        this.mChildFragmentManager.dispatchCreate();
      } 
    } 
  }
  
  final void restoreViewState(Bundle paramBundle) {
    SparseArray<Parcelable> sparseArray = this.mSavedViewState;
    if (sparseArray != null) {
      this.mInnerView.restoreHierarchyState(sparseArray);
      this.mSavedViewState = null;
    } 
    this.mCalled = false;
    onViewStateRestored(paramBundle);
    if (this.mCalled) {
      if (this.mView != null)
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE); 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onViewStateRestored()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  public void setAllowEnterTransitionOverlap(boolean paramBoolean) {
    (ensureAnimationInfo()).mAllowEnterTransitionOverlap = Boolean.valueOf(paramBoolean);
  }
  
  public void setAllowReturnTransitionOverlap(boolean paramBoolean) {
    (ensureAnimationInfo()).mAllowReturnTransitionOverlap = Boolean.valueOf(paramBoolean);
  }
  
  void setAnimatingAway(View paramView) {
    (ensureAnimationInfo()).mAnimatingAway = paramView;
  }
  
  void setAnimator(Animator paramAnimator) {
    (ensureAnimationInfo()).mAnimator = paramAnimator;
  }
  
  public void setArguments(Bundle paramBundle) {
    if (this.mFragmentManager == null || !isStateSaved()) {
      this.mArguments = paramBundle;
      return;
    } 
    throw new IllegalStateException("Fragment already added and state has been saved");
  }
  
  public void setEnterSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    (ensureAnimationInfo()).mEnterTransitionCallback = paramSharedElementCallback;
  }
  
  public void setEnterTransition(Object paramObject) {
    (ensureAnimationInfo()).mEnterTransition = paramObject;
  }
  
  public void setExitSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    (ensureAnimationInfo()).mExitTransitionCallback = paramSharedElementCallback;
  }
  
  public void setExitTransition(Object paramObject) {
    (ensureAnimationInfo()).mExitTransition = paramObject;
  }
  
  public void setHasOptionsMenu(boolean paramBoolean) {
    if (this.mHasMenu != paramBoolean) {
      this.mHasMenu = paramBoolean;
      if (isAdded() && !isHidden())
        this.mHost.onSupportInvalidateOptionsMenu(); 
    } 
  }
  
  void setHideReplaced(boolean paramBoolean) {
    (ensureAnimationInfo()).mIsHideReplaced = paramBoolean;
  }
  
  public void setInitialSavedState(SavedState paramSavedState) {
    if (this.mFragmentManager == null) {
      if (paramSavedState != null && paramSavedState.mState != null) {
        Bundle bundle = paramSavedState.mState;
      } else {
        paramSavedState = null;
      } 
      this.mSavedFragmentState = (Bundle)paramSavedState;
      return;
    } 
    throw new IllegalStateException("Fragment already added");
  }
  
  public void setMenuVisibility(boolean paramBoolean) {
    if (this.mMenuVisible != paramBoolean) {
      this.mMenuVisible = paramBoolean;
      if (this.mHasMenu && isAdded() && !isHidden())
        this.mHost.onSupportInvalidateOptionsMenu(); 
    } 
  }
  
  void setNextAnim(int paramInt) {
    if (this.mAnimationInfo == null && paramInt == 0)
      return; 
    (ensureAnimationInfo()).mNextAnim = paramInt;
  }
  
  void setNextTransition(int paramInt1, int paramInt2) {
    if (this.mAnimationInfo == null && paramInt1 == 0 && paramInt2 == 0)
      return; 
    ensureAnimationInfo();
    this.mAnimationInfo.mNextTransition = paramInt1;
    this.mAnimationInfo.mNextTransitionStyle = paramInt2;
  }
  
  void setOnStartEnterTransitionListener(OnStartEnterTransitionListener paramOnStartEnterTransitionListener) {
    ensureAnimationInfo();
    if (paramOnStartEnterTransitionListener == this.mAnimationInfo.mStartEnterTransitionListener)
      return; 
    if (paramOnStartEnterTransitionListener == null || this.mAnimationInfo.mStartEnterTransitionListener == null) {
      if (this.mAnimationInfo.mEnterTransitionPostponed)
        this.mAnimationInfo.mStartEnterTransitionListener = paramOnStartEnterTransitionListener; 
      if (paramOnStartEnterTransitionListener != null)
        paramOnStartEnterTransitionListener.startListening(); 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Trying to set a replacement startPostponedEnterTransition on ");
    stringBuilder.append(this);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void setReenterTransition(Object paramObject) {
    (ensureAnimationInfo()).mReenterTransition = paramObject;
  }
  
  public void setRetainInstance(boolean paramBoolean) {
    this.mRetainInstance = paramBoolean;
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    if (fragmentManagerImpl != null) {
      if (paramBoolean) {
        fragmentManagerImpl.addRetainedFragment(this);
      } else {
        fragmentManagerImpl.removeRetainedFragment(this);
      } 
    } else {
      this.mRetainInstanceChangedWhileDetached = true;
    } 
  }
  
  public void setReturnTransition(Object paramObject) {
    (ensureAnimationInfo()).mReturnTransition = paramObject;
  }
  
  public void setSharedElementEnterTransition(Object paramObject) {
    (ensureAnimationInfo()).mSharedElementEnterTransition = paramObject;
  }
  
  public void setSharedElementReturnTransition(Object paramObject) {
    (ensureAnimationInfo()).mSharedElementReturnTransition = paramObject;
  }
  
  void setStateAfterAnimating(int paramInt) {
    (ensureAnimationInfo()).mStateAfterAnimating = paramInt;
  }
  
  public void setTargetFragment(Fragment paramFragment, int paramInt) {
    FragmentManager fragmentManager1;
    FragmentManager fragmentManager2 = getFragmentManager();
    if (paramFragment != null) {
      fragmentManager1 = paramFragment.getFragmentManager();
    } else {
      fragmentManager1 = null;
    } 
    if (fragmentManager2 == null || fragmentManager1 == null || fragmentManager2 == fragmentManager1) {
      Fragment fragment = paramFragment;
      while (fragment != null) {
        if (fragment != this) {
          fragment = fragment.getTargetFragment();
          continue;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Setting ");
        stringBuilder1.append(paramFragment);
        stringBuilder1.append(" as the target of ");
        stringBuilder1.append(this);
        stringBuilder1.append(" would create a target cycle");
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      if (paramFragment == null) {
        this.mTargetWho = null;
        this.mTarget = null;
      } else if (this.mFragmentManager != null && paramFragment.mFragmentManager != null) {
        this.mTargetWho = paramFragment.mWho;
        this.mTarget = null;
      } else {
        this.mTargetWho = null;
        this.mTarget = paramFragment;
      } 
      this.mTargetRequestCode = paramInt;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(paramFragment);
    stringBuilder.append(" must share the same FragmentManager to be set as a target fragment");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  @Deprecated
  public void setUserVisibleHint(boolean paramBoolean) {
    boolean bool;
    if (!this.mUserVisibleHint && paramBoolean && this.mState < 3 && this.mFragmentManager != null && isAdded() && this.mIsCreated)
      this.mFragmentManager.performPendingDeferredStart(this); 
    this.mUserVisibleHint = paramBoolean;
    if (this.mState < 3 && !paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mDeferStart = bool;
    if (this.mSavedFragmentState != null)
      this.mSavedUserVisibleHint = Boolean.valueOf(paramBoolean); 
  }
  
  public boolean shouldShowRequestPermissionRationale(String paramString) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    return (fragmentHostCallback != null) ? fragmentHostCallback.onShouldShowRequestPermissionRationale(paramString) : false;
  }
  
  public void startActivity(Intent paramIntent) {
    startActivity(paramIntent, null);
  }
  
  public void startActivity(Intent paramIntent, Bundle paramBundle) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onStartActivityFromFragment(this, paramIntent, -1, paramBundle);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt) {
    startActivityForResult(paramIntent, paramInt, null);
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt, Bundle paramBundle) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onStartActivityFromFragment(this, paramIntent, paramInt, paramBundle);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle) throws IntentSender.SendIntentException {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onStartIntentSenderFromFragment(this, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void startPostponedEnterTransition() {
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    if (fragmentManagerImpl == null || fragmentManagerImpl.mHost == null) {
      (ensureAnimationInfo()).mEnterTransitionPostponed = false;
      return;
    } 
    if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
      this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable() {
            final Fragment this$0;
            
            public void run() {
              Fragment.this.callStartTransitionListener();
            }
          });
    } else {
      callStartTransitionListener();
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    DebugUtils.buildShortClassTag(this, stringBuilder);
    stringBuilder.append(" (");
    stringBuilder.append(this.mWho);
    stringBuilder.append(")");
    if (this.mFragmentId != 0) {
      stringBuilder.append(" id=0x");
      stringBuilder.append(Integer.toHexString(this.mFragmentId));
    } 
    if (this.mTag != null) {
      stringBuilder.append(" ");
      stringBuilder.append(this.mTag);
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public void unregisterForContextMenu(View paramView) {
    paramView.setOnCreateContextMenuListener(null);
  }
  
  static class AnimationInfo {
    Boolean mAllowEnterTransitionOverlap;
    
    Boolean mAllowReturnTransitionOverlap;
    
    View mAnimatingAway;
    
    Animator mAnimator;
    
    Object mEnterTransition = null;
    
    SharedElementCallback mEnterTransitionCallback = null;
    
    boolean mEnterTransitionPostponed;
    
    Object mExitTransition = null;
    
    SharedElementCallback mExitTransitionCallback = null;
    
    boolean mIsHideReplaced;
    
    int mNextAnim;
    
    int mNextTransition;
    
    int mNextTransitionStyle;
    
    Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
    
    Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    
    Object mSharedElementEnterTransition = null;
    
    Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    
    Fragment.OnStartEnterTransitionListener mStartEnterTransitionListener;
    
    int mStateAfterAnimating;
  }
  
  public static class InstantiationException extends RuntimeException {
    public InstantiationException(String param1String, Exception param1Exception) {
      super(param1String, param1Exception);
    }
  }
  
  static interface OnStartEnterTransitionListener {
    void onStartEnterTransition();
    
    void startListening();
  }
  
  public static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public Fragment.SavedState createFromParcel(Parcel param2Parcel) {
          return new Fragment.SavedState(param2Parcel, null);
        }
        
        public Fragment.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new Fragment.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public Fragment.SavedState[] newArray(int param2Int) {
          return new Fragment.SavedState[param2Int];
        }
      };
    
    final Bundle mState;
    
    SavedState(Bundle param1Bundle) {
      this.mState = param1Bundle;
    }
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      Bundle bundle = param1Parcel.readBundle();
      this.mState = bundle;
      if (param1ClassLoader != null && bundle != null)
        bundle.setClassLoader(param1ClassLoader); 
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeBundle(this.mState);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public Fragment.SavedState createFromParcel(Parcel param1Parcel) {
      return new Fragment.SavedState(param1Parcel, null);
    }
    
    public Fragment.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new Fragment.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public Fragment.SavedState[] newArray(int param1Int) {
      return new Fragment.SavedState[param1Int];
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\Fragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */