package androidx.fragment.app;

import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public abstract class FragmentTransaction {
  static final int OP_ADD = 1;
  
  static final int OP_ATTACH = 7;
  
  static final int OP_DETACH = 6;
  
  static final int OP_HIDE = 4;
  
  static final int OP_NULL = 0;
  
  static final int OP_REMOVE = 3;
  
  static final int OP_REPLACE = 2;
  
  static final int OP_SET_MAX_LIFECYCLE = 10;
  
  static final int OP_SET_PRIMARY_NAV = 8;
  
  static final int OP_SHOW = 5;
  
  static final int OP_UNSET_PRIMARY_NAV = 9;
  
  public static final int TRANSIT_ENTER_MASK = 4096;
  
  public static final int TRANSIT_EXIT_MASK = 8192;
  
  public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
  
  public static final int TRANSIT_FRAGMENT_FADE = 4099;
  
  public static final int TRANSIT_FRAGMENT_OPEN = 4097;
  
  public static final int TRANSIT_NONE = 0;
  
  public static final int TRANSIT_UNSET = -1;
  
  boolean mAddToBackStack;
  
  boolean mAllowAddToBackStack = true;
  
  int mBreadCrumbShortTitleRes;
  
  CharSequence mBreadCrumbShortTitleText;
  
  int mBreadCrumbTitleRes;
  
  CharSequence mBreadCrumbTitleText;
  
  ArrayList<Runnable> mCommitRunnables;
  
  int mEnterAnim;
  
  int mExitAnim;
  
  String mName;
  
  ArrayList<Op> mOps = new ArrayList<Op>();
  
  int mPopEnterAnim;
  
  int mPopExitAnim;
  
  boolean mReorderingAllowed = false;
  
  ArrayList<String> mSharedElementSourceNames;
  
  ArrayList<String> mSharedElementTargetNames;
  
  int mTransition;
  
  int mTransitionStyle;
  
  public FragmentTransaction add(int paramInt, Fragment paramFragment) {
    doAddOp(paramInt, paramFragment, null, 1);
    return this;
  }
  
  public FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString) {
    doAddOp(paramInt, paramFragment, paramString, 1);
    return this;
  }
  
  public FragmentTransaction add(Fragment paramFragment, String paramString) {
    doAddOp(0, paramFragment, paramString, 1);
    return this;
  }
  
  void addOp(Op paramOp) {
    this.mOps.add(paramOp);
    paramOp.mEnterAnim = this.mEnterAnim;
    paramOp.mExitAnim = this.mExitAnim;
    paramOp.mPopEnterAnim = this.mPopEnterAnim;
    paramOp.mPopExitAnim = this.mPopExitAnim;
  }
  
  public FragmentTransaction addSharedElement(View paramView, String paramString) {
    if (FragmentTransition.supportsTransition()) {
      String str = ViewCompat.getTransitionName(paramView);
      if (str != null) {
        StringBuilder stringBuilder1;
        StringBuilder stringBuilder2;
        if (this.mSharedElementSourceNames == null) {
          this.mSharedElementSourceNames = new ArrayList<String>();
          this.mSharedElementTargetNames = new ArrayList<String>();
        } else if (!this.mSharedElementTargetNames.contains(paramString)) {
          if (this.mSharedElementSourceNames.contains(str)) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("A shared element with the source name '");
            stringBuilder2.append(str);
            stringBuilder2.append("' has already been added to the transaction.");
            throw new IllegalArgumentException(stringBuilder2.toString());
          } 
        } else {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("A shared element with the target name '");
          stringBuilder1.append((String)stringBuilder2);
          stringBuilder1.append("' has already been added to the transaction.");
          throw new IllegalArgumentException(stringBuilder1.toString());
        } 
        this.mSharedElementSourceNames.add(stringBuilder1);
        this.mSharedElementTargetNames.add(stringBuilder2);
      } else {
        throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
      } 
    } 
    return this;
  }
  
  public FragmentTransaction addToBackStack(String paramString) {
    if (this.mAllowAddToBackStack) {
      this.mAddToBackStack = true;
      this.mName = paramString;
      return this;
    } 
    throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
  }
  
  public FragmentTransaction attach(Fragment paramFragment) {
    addOp(new Op(7, paramFragment));
    return this;
  }
  
  public abstract int commit();
  
  public abstract int commitAllowingStateLoss();
  
  public abstract void commitNow();
  
  public abstract void commitNowAllowingStateLoss();
  
  public FragmentTransaction detach(Fragment paramFragment) {
    addOp(new Op(6, paramFragment));
    return this;
  }
  
  public FragmentTransaction disallowAddToBackStack() {
    if (!this.mAddToBackStack) {
      this.mAllowAddToBackStack = false;
      return this;
    } 
    throw new IllegalStateException("This transaction is already being added to the back stack");
  }
  
  void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2) {
    StringBuilder stringBuilder2;
    Class<?> clazz = paramFragment.getClass();
    int i = clazz.getModifiers();
    if (!clazz.isAnonymousClass() && Modifier.isPublic(i) && (!clazz.isMemberClass() || Modifier.isStatic(i))) {
      if (paramString != null)
        if (paramFragment.mTag == null || paramString.equals(paramFragment.mTag)) {
          paramFragment.mTag = paramString;
        } else {
          stringBuilder2 = new StringBuilder();
          stringBuilder2.append("Can't change tag of fragment ");
          stringBuilder2.append(paramFragment);
          stringBuilder2.append(": was ");
          stringBuilder2.append(paramFragment.mTag);
          stringBuilder2.append(" now ");
          stringBuilder2.append(paramString);
          throw new IllegalStateException(stringBuilder2.toString());
        }  
      if (paramInt1 != 0) {
        StringBuilder stringBuilder;
        if (paramInt1 != -1) {
          if (paramFragment.mFragmentId == 0 || paramFragment.mFragmentId == paramInt1) {
            paramFragment.mFragmentId = paramInt1;
            paramFragment.mContainerId = paramInt1;
          } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Can't change container ID of fragment ");
            stringBuilder.append(paramFragment);
            stringBuilder.append(": was ");
            stringBuilder.append(paramFragment.mFragmentId);
            stringBuilder.append(" now ");
            stringBuilder.append(paramInt1);
            throw new IllegalStateException(stringBuilder.toString());
          } 
        } else {
          stringBuilder2 = new StringBuilder();
          stringBuilder2.append("Can't add fragment ");
          stringBuilder2.append(paramFragment);
          stringBuilder2.append(" with tag ");
          stringBuilder2.append((String)stringBuilder);
          stringBuilder2.append(" to container view with no id");
          throw new IllegalArgumentException(stringBuilder2.toString());
        } 
      } 
      addOp(new Op(paramInt2, paramFragment));
      return;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Fragment ");
    stringBuilder1.append(stringBuilder2.getCanonicalName());
    stringBuilder1.append(" must be a public static class to be  properly recreated from instance state.");
    throw new IllegalStateException(stringBuilder1.toString());
  }
  
  public FragmentTransaction hide(Fragment paramFragment) {
    addOp(new Op(4, paramFragment));
    return this;
  }
  
  public boolean isAddToBackStackAllowed() {
    return this.mAllowAddToBackStack;
  }
  
  public boolean isEmpty() {
    return this.mOps.isEmpty();
  }
  
  public FragmentTransaction remove(Fragment paramFragment) {
    addOp(new Op(3, paramFragment));
    return this;
  }
  
  public FragmentTransaction replace(int paramInt, Fragment paramFragment) {
    return replace(paramInt, paramFragment, null);
  }
  
  public FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString) {
    if (paramInt != 0) {
      doAddOp(paramInt, paramFragment, paramString, 2);
      return this;
    } 
    throw new IllegalArgumentException("Must use non-zero containerViewId");
  }
  
  public FragmentTransaction runOnCommit(Runnable paramRunnable) {
    disallowAddToBackStack();
    if (this.mCommitRunnables == null)
      this.mCommitRunnables = new ArrayList<Runnable>(); 
    this.mCommitRunnables.add(paramRunnable);
    return this;
  }
  
  @Deprecated
  public FragmentTransaction setAllowOptimization(boolean paramBoolean) {
    return setReorderingAllowed(paramBoolean);
  }
  
  public FragmentTransaction setBreadCrumbShortTitle(int paramInt) {
    this.mBreadCrumbShortTitleRes = paramInt;
    this.mBreadCrumbShortTitleText = null;
    return this;
  }
  
  public FragmentTransaction setBreadCrumbShortTitle(CharSequence paramCharSequence) {
    this.mBreadCrumbShortTitleRes = 0;
    this.mBreadCrumbShortTitleText = paramCharSequence;
    return this;
  }
  
  public FragmentTransaction setBreadCrumbTitle(int paramInt) {
    this.mBreadCrumbTitleRes = paramInt;
    this.mBreadCrumbTitleText = null;
    return this;
  }
  
  public FragmentTransaction setBreadCrumbTitle(CharSequence paramCharSequence) {
    this.mBreadCrumbTitleRes = 0;
    this.mBreadCrumbTitleText = paramCharSequence;
    return this;
  }
  
  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2) {
    return setCustomAnimations(paramInt1, paramInt2, 0, 0);
  }
  
  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mEnterAnim = paramInt1;
    this.mExitAnim = paramInt2;
    this.mPopEnterAnim = paramInt3;
    this.mPopExitAnim = paramInt4;
    return this;
  }
  
  public FragmentTransaction setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState) {
    addOp(new Op(10, paramFragment, paramState));
    return this;
  }
  
  public FragmentTransaction setPrimaryNavigationFragment(Fragment paramFragment) {
    addOp(new Op(8, paramFragment));
    return this;
  }
  
  public FragmentTransaction setReorderingAllowed(boolean paramBoolean) {
    this.mReorderingAllowed = paramBoolean;
    return this;
  }
  
  public FragmentTransaction setTransition(int paramInt) {
    this.mTransition = paramInt;
    return this;
  }
  
  public FragmentTransaction setTransitionStyle(int paramInt) {
    this.mTransitionStyle = paramInt;
    return this;
  }
  
  public FragmentTransaction show(Fragment paramFragment) {
    addOp(new Op(5, paramFragment));
    return this;
  }
  
  static final class Op {
    int mCmd;
    
    Lifecycle.State mCurrentMaxState;
    
    int mEnterAnim;
    
    int mExitAnim;
    
    Fragment mFragment;
    
    Lifecycle.State mOldMaxState;
    
    int mPopEnterAnim;
    
    int mPopExitAnim;
    
    Op() {}
    
    Op(int param1Int, Fragment param1Fragment) {
      this.mCmd = param1Int;
      this.mFragment = param1Fragment;
      this.mOldMaxState = Lifecycle.State.RESUMED;
      this.mCurrentMaxState = Lifecycle.State.RESUMED;
    }
    
    Op(int param1Int, Fragment param1Fragment, Lifecycle.State param1State) {
      this.mCmd = param1Int;
      this.mFragment = param1Fragment;
      this.mOldMaxState = param1Fragment.mMaxState;
      this.mCurrentMaxState = param1State;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */