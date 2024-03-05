package androidx.fragment.app;

import android.util.Log;
import androidx.core.util.LogWriter;
import androidx.lifecycle.Lifecycle;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, FragmentManagerImpl.OpGenerator {
  static final String TAG = "FragmentManager";
  
  boolean mCommitted;
  
  int mIndex = -1;
  
  final FragmentManagerImpl mManager;
  
  public BackStackRecord(FragmentManagerImpl paramFragmentManagerImpl) {
    this.mManager = paramFragmentManagerImpl;
  }
  
  private static boolean isFragmentPostponed(FragmentTransaction.Op paramOp) {
    boolean bool;
    Fragment fragment = paramOp.mFragment;
    if (fragment != null && fragment.mAdded && fragment.mView != null && !fragment.mDetached && !fragment.mHidden && fragment.isPostponed()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void bumpBackStackNesting(int paramInt) {
    if (!this.mAddToBackStack)
      return; 
    if (FragmentManagerImpl.DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Bump nesting in ");
      stringBuilder.append(this);
      stringBuilder.append(" by ");
      stringBuilder.append(paramInt);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    int i = this.mOps.size();
    for (byte b = 0; b < i; b++) {
      FragmentTransaction.Op op = this.mOps.get(b);
      if (op.mFragment != null) {
        Fragment fragment = op.mFragment;
        fragment.mBackStackNesting += paramInt;
        if (FragmentManagerImpl.DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Bump nesting of ");
          stringBuilder.append(op.mFragment);
          stringBuilder.append(" to ");
          stringBuilder.append(op.mFragment.mBackStackNesting);
          Log.v("FragmentManager", stringBuilder.toString());
        } 
      } 
    } 
  }
  
  public int commit() {
    return commitInternal(false);
  }
  
  public int commitAllowingStateLoss() {
    return commitInternal(true);
  }
  
  int commitInternal(boolean paramBoolean) {
    if (!this.mCommitted) {
      if (FragmentManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Commit: ");
        stringBuilder.append(this);
        Log.v("FragmentManager", stringBuilder.toString());
        PrintWriter printWriter = new PrintWriter((Writer)new LogWriter("FragmentManager"));
        dump("  ", printWriter);
        printWriter.close();
      } 
      this.mCommitted = true;
      if (this.mAddToBackStack) {
        this.mIndex = this.mManager.allocBackStackIndex(this);
      } else {
        this.mIndex = -1;
      } 
      this.mManager.enqueueAction(this, paramBoolean);
      return this.mIndex;
    } 
    throw new IllegalStateException("commit already called");
  }
  
  public void commitNow() {
    disallowAddToBackStack();
    this.mManager.execSingleAction(this, false);
  }
  
  public void commitNowAllowingStateLoss() {
    disallowAddToBackStack();
    this.mManager.execSingleAction(this, true);
  }
  
  public FragmentTransaction detach(Fragment paramFragment) {
    if (paramFragment.mFragmentManager == null || paramFragment.mFragmentManager == this.mManager)
      return super.detach(paramFragment); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot detach Fragment attached to a different FragmentManager. Fragment ");
    stringBuilder.append(paramFragment.toString());
    stringBuilder.append(" is already attached to a FragmentManager.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2) {
    super.doAddOp(paramInt1, paramFragment, paramString, paramInt2);
    paramFragment.mFragmentManager = this.mManager;
  }
  
  public void dump(String paramString, PrintWriter paramPrintWriter) {
    dump(paramString, paramPrintWriter, true);
  }
  
  public void dump(String paramString, PrintWriter paramPrintWriter, boolean paramBoolean) {
    if (paramBoolean) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mName=");
      paramPrintWriter.print(this.mName);
      paramPrintWriter.print(" mIndex=");
      paramPrintWriter.print(this.mIndex);
      paramPrintWriter.print(" mCommitted=");
      paramPrintWriter.println(this.mCommitted);
      if (this.mTransition != 0) {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mTransition=#");
        paramPrintWriter.print(Integer.toHexString(this.mTransition));
        paramPrintWriter.print(" mTransitionStyle=#");
        paramPrintWriter.println(Integer.toHexString(this.mTransitionStyle));
      } 
      if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mEnterAnim));
        paramPrintWriter.print(" mExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mExitAnim));
      } 
      if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mPopEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mPopEnterAnim));
        paramPrintWriter.print(" mPopExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mPopExitAnim));
      } 
      if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
        paramPrintWriter.print(" mBreadCrumbTitleText=");
        paramPrintWriter.println(this.mBreadCrumbTitleText);
      } 
      if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbShortTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
        paramPrintWriter.print(" mBreadCrumbShortTitleText=");
        paramPrintWriter.println(this.mBreadCrumbShortTitleText);
      } 
    } 
    if (!this.mOps.isEmpty()) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Operations:");
      int i = this.mOps.size();
      for (byte b = 0; b < i; b++) {
        StringBuilder stringBuilder;
        String str;
        FragmentTransaction.Op op = this.mOps.get(b);
        switch (op.mCmd) {
          default:
            stringBuilder = new StringBuilder();
            stringBuilder.append("cmd=");
            stringBuilder.append(op.mCmd);
            str = stringBuilder.toString();
            break;
          case 10:
            str = "OP_SET_MAX_LIFECYCLE";
            break;
          case 9:
            str = "UNSET_PRIMARY_NAV";
            break;
          case 8:
            str = "SET_PRIMARY_NAV";
            break;
          case 7:
            str = "ATTACH";
            break;
          case 6:
            str = "DETACH";
            break;
          case 5:
            str = "SHOW";
            break;
          case 4:
            str = "HIDE";
            break;
          case 3:
            str = "REMOVE";
            break;
          case 2:
            str = "REPLACE";
            break;
          case 1:
            str = "ADD";
            break;
          case 0:
            str = "NULL";
            break;
        } 
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  Op #");
        paramPrintWriter.print(b);
        paramPrintWriter.print(": ");
        paramPrintWriter.print(str);
        paramPrintWriter.print(" ");
        paramPrintWriter.println(op.mFragment);
        if (paramBoolean) {
          if (op.mEnterAnim != 0 || op.mExitAnim != 0) {
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("enterAnim=#");
            paramPrintWriter.print(Integer.toHexString(op.mEnterAnim));
            paramPrintWriter.print(" exitAnim=#");
            paramPrintWriter.println(Integer.toHexString(op.mExitAnim));
          } 
          if (op.mPopEnterAnim != 0 || op.mPopExitAnim != 0) {
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("popEnterAnim=#");
            paramPrintWriter.print(Integer.toHexString(op.mPopEnterAnim));
            paramPrintWriter.print(" popExitAnim=#");
            paramPrintWriter.println(Integer.toHexString(op.mPopExitAnim));
          } 
        } 
      } 
    } 
  }
  
  void executeOps() {
    int i = this.mOps.size();
    for (byte b = 0; b < i; b++) {
      StringBuilder stringBuilder;
      FragmentTransaction.Op op = this.mOps.get(b);
      Fragment fragment = op.mFragment;
      if (fragment != null)
        fragment.setNextTransition(this.mTransition, this.mTransitionStyle); 
      switch (op.mCmd) {
        default:
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown cmd: ");
          stringBuilder.append(op.mCmd);
          throw new IllegalArgumentException(stringBuilder.toString());
        case 10:
          this.mManager.setMaxLifecycle((Fragment)stringBuilder, op.mCurrentMaxState);
          break;
        case 9:
          this.mManager.setPrimaryNavigationFragment(null);
          break;
        case 8:
          this.mManager.setPrimaryNavigationFragment((Fragment)stringBuilder);
          break;
        case 7:
          stringBuilder.setNextAnim(op.mEnterAnim);
          this.mManager.attachFragment((Fragment)stringBuilder);
          break;
        case 6:
          stringBuilder.setNextAnim(op.mExitAnim);
          this.mManager.detachFragment((Fragment)stringBuilder);
          break;
        case 5:
          stringBuilder.setNextAnim(op.mEnterAnim);
          this.mManager.showFragment((Fragment)stringBuilder);
          break;
        case 4:
          stringBuilder.setNextAnim(op.mExitAnim);
          this.mManager.hideFragment((Fragment)stringBuilder);
          break;
        case 3:
          stringBuilder.setNextAnim(op.mExitAnim);
          this.mManager.removeFragment((Fragment)stringBuilder);
          break;
        case 1:
          stringBuilder.setNextAnim(op.mEnterAnim);
          this.mManager.addFragment((Fragment)stringBuilder, false);
          break;
      } 
      if (!this.mReorderingAllowed && op.mCmd != 1 && stringBuilder != null)
        this.mManager.moveFragmentToExpectedState((Fragment)stringBuilder); 
    } 
    if (!this.mReorderingAllowed) {
      FragmentManagerImpl fragmentManagerImpl = this.mManager;
      fragmentManagerImpl.moveToState(fragmentManagerImpl.mCurState, true);
    } 
  }
  
  void executePopOps(boolean paramBoolean) {
    for (int i = this.mOps.size() - 1; i >= 0; i--) {
      StringBuilder stringBuilder;
      FragmentTransaction.Op op = this.mOps.get(i);
      Fragment fragment = op.mFragment;
      if (fragment != null)
        fragment.setNextTransition(FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle); 
      switch (op.mCmd) {
        default:
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown cmd: ");
          stringBuilder.append(op.mCmd);
          throw new IllegalArgumentException(stringBuilder.toString());
        case 10:
          this.mManager.setMaxLifecycle((Fragment)stringBuilder, op.mOldMaxState);
          break;
        case 9:
          this.mManager.setPrimaryNavigationFragment((Fragment)stringBuilder);
          break;
        case 8:
          this.mManager.setPrimaryNavigationFragment(null);
          break;
        case 7:
          stringBuilder.setNextAnim(op.mPopExitAnim);
          this.mManager.detachFragment((Fragment)stringBuilder);
          break;
        case 6:
          stringBuilder.setNextAnim(op.mPopEnterAnim);
          this.mManager.attachFragment((Fragment)stringBuilder);
          break;
        case 5:
          stringBuilder.setNextAnim(op.mPopExitAnim);
          this.mManager.hideFragment((Fragment)stringBuilder);
          break;
        case 4:
          stringBuilder.setNextAnim(op.mPopEnterAnim);
          this.mManager.showFragment((Fragment)stringBuilder);
          break;
        case 3:
          stringBuilder.setNextAnim(op.mPopEnterAnim);
          this.mManager.addFragment((Fragment)stringBuilder, false);
          break;
        case 1:
          stringBuilder.setNextAnim(op.mPopExitAnim);
          this.mManager.removeFragment((Fragment)stringBuilder);
          break;
      } 
      if (!this.mReorderingAllowed && op.mCmd != 3 && stringBuilder != null)
        this.mManager.moveFragmentToExpectedState((Fragment)stringBuilder); 
    } 
    if (!this.mReorderingAllowed && paramBoolean) {
      FragmentManagerImpl fragmentManagerImpl = this.mManager;
      fragmentManagerImpl.moveToState(fragmentManagerImpl.mCurState, true);
    } 
  }
  
  Fragment expandOps(ArrayList<Fragment> paramArrayList, Fragment paramFragment) {
    int i = 0;
    Fragment fragment;
    for (fragment = paramFragment; i < this.mOps.size(); fragment = paramFragment) {
      FragmentTransaction.Op op = this.mOps.get(i);
      int j = op.mCmd;
      if (j != 1)
        if (j != 2) {
          if (j != 3 && j != 6) {
            if (j != 7) {
              if (j != 8) {
                paramFragment = fragment;
                j = i;
              } else {
                this.mOps.add(i, new FragmentTransaction.Op(9, fragment));
                j = i + 1;
                paramFragment = op.mFragment;
              } 
              continue;
            } 
          } else {
            paramArrayList.remove(op.mFragment);
            paramFragment = fragment;
            j = i;
            if (op.mFragment == fragment) {
              this.mOps.add(i, new FragmentTransaction.Op(9, op.mFragment));
              j = i + 1;
              paramFragment = null;
            } 
            continue;
          } 
        } else {
          Fragment fragment1 = op.mFragment;
          int m = fragment1.mContainerId;
          int k = paramArrayList.size() - 1;
          boolean bool = false;
          j = i;
          paramFragment = fragment;
          while (k >= 0) {
            Fragment fragment2 = paramArrayList.get(k);
            fragment = paramFragment;
            i = j;
            boolean bool1 = bool;
            if (fragment2.mContainerId == m)
              if (fragment2 == fragment1) {
                bool1 = true;
                fragment = paramFragment;
                i = j;
              } else {
                fragment = paramFragment;
                i = j;
                if (fragment2 == paramFragment) {
                  this.mOps.add(j, new FragmentTransaction.Op(9, fragment2));
                  i = j + 1;
                  fragment = null;
                } 
                FragmentTransaction.Op op1 = new FragmentTransaction.Op(3, fragment2);
                op1.mEnterAnim = op.mEnterAnim;
                op1.mPopEnterAnim = op.mPopEnterAnim;
                op1.mExitAnim = op.mExitAnim;
                op1.mPopExitAnim = op.mPopExitAnim;
                this.mOps.add(i, op1);
                paramArrayList.remove(fragment2);
                i++;
                bool1 = bool;
              }  
            k--;
            paramFragment = fragment;
            j = i;
            bool = bool1;
          } 
          if (bool) {
            this.mOps.remove(j);
            j--;
          } else {
            op.mCmd = 1;
            paramArrayList.add(fragment1);
          } 
          continue;
        }  
      paramArrayList.add(op.mFragment);
      j = i;
      paramFragment = fragment;
      continue;
      i = SYNTHETIC_LOCAL_VARIABLE_3 + 1;
    } 
    return fragment;
  }
  
  public boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    if (FragmentManagerImpl.DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Run: ");
      stringBuilder.append(this);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    paramArrayList.add(this);
    paramArrayList1.add(Boolean.valueOf(false));
    if (this.mAddToBackStack)
      this.mManager.addBackStackState(this); 
    return true;
  }
  
  public CharSequence getBreadCrumbShortTitle() {
    return (this.mBreadCrumbShortTitleRes != 0) ? this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes) : this.mBreadCrumbShortTitleText;
  }
  
  public int getBreadCrumbShortTitleRes() {
    return this.mBreadCrumbShortTitleRes;
  }
  
  public CharSequence getBreadCrumbTitle() {
    return (this.mBreadCrumbTitleRes != 0) ? this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes) : this.mBreadCrumbTitleText;
  }
  
  public int getBreadCrumbTitleRes() {
    return this.mBreadCrumbTitleRes;
  }
  
  public int getId() {
    return this.mIndex;
  }
  
  public String getName() {
    return this.mName;
  }
  
  public FragmentTransaction hide(Fragment paramFragment) {
    if (paramFragment.mFragmentManager == null || paramFragment.mFragmentManager == this.mManager)
      return super.hide(paramFragment); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot hide Fragment attached to a different FragmentManager. Fragment ");
    stringBuilder.append(paramFragment.toString());
    stringBuilder.append(" is already attached to a FragmentManager.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  boolean interactsWith(int paramInt) {
    int i = this.mOps.size();
    for (byte b = 0; b < i; b++) {
      int j;
      FragmentTransaction.Op op = this.mOps.get(b);
      if (op.mFragment != null) {
        j = op.mFragment.mContainerId;
      } else {
        j = 0;
      } 
      if (j && j == paramInt)
        return true; 
    } 
    return false;
  }
  
  boolean interactsWith(ArrayList<BackStackRecord> paramArrayList, int paramInt1, int paramInt2) {
    if (paramInt2 == paramInt1)
      return false; 
    int j = this.mOps.size();
    int i = -1;
    byte b = 0;
    while (b < j) {
      int k;
      FragmentTransaction.Op op = this.mOps.get(b);
      if (op.mFragment != null) {
        k = op.mFragment.mContainerId;
      } else {
        k = 0;
      } 
      int m = i;
      if (k) {
        m = i;
        if (k != i) {
          for (i = paramInt1; i < paramInt2; i++) {
            BackStackRecord backStackRecord = paramArrayList.get(i);
            int n = backStackRecord.mOps.size();
            for (m = 0; m < n; m++) {
              int i1;
              FragmentTransaction.Op op1 = backStackRecord.mOps.get(m);
              if (op1.mFragment != null) {
                i1 = op1.mFragment.mContainerId;
              } else {
                i1 = 0;
              } 
              if (i1 == k)
                return true; 
            } 
          } 
          m = k;
        } 
      } 
      b++;
      i = m;
    } 
    return false;
  }
  
  public boolean isEmpty() {
    return this.mOps.isEmpty();
  }
  
  boolean isPostponed() {
    for (byte b = 0; b < this.mOps.size(); b++) {
      if (isFragmentPostponed(this.mOps.get(b)))
        return true; 
    } 
    return false;
  }
  
  public FragmentTransaction remove(Fragment paramFragment) {
    if (paramFragment.mFragmentManager == null || paramFragment.mFragmentManager == this.mManager)
      return super.remove(paramFragment); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot remove Fragment attached to a different FragmentManager. Fragment ");
    stringBuilder.append(paramFragment.toString());
    stringBuilder.append(" is already attached to a FragmentManager.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void runOnCommitRunnables() {
    if (this.mCommitRunnables != null) {
      for (byte b = 0; b < this.mCommitRunnables.size(); b++)
        ((Runnable)this.mCommitRunnables.get(b)).run(); 
      this.mCommitRunnables = null;
    } 
  }
  
  public FragmentTransaction setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState) {
    if (paramFragment.mFragmentManager == this.mManager) {
      if (paramState.isAtLeast(Lifecycle.State.CREATED))
        return super.setMaxLifecycle(paramFragment, paramState); 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Cannot set maximum Lifecycle below ");
      stringBuilder1.append(Lifecycle.State.CREATED);
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot setMaxLifecycle for Fragment not attached to FragmentManager ");
    stringBuilder.append(this.mManager);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener paramOnStartEnterTransitionListener) {
    for (byte b = 0; b < this.mOps.size(); b++) {
      FragmentTransaction.Op op = this.mOps.get(b);
      if (isFragmentPostponed(op))
        op.mFragment.setOnStartEnterTransitionListener(paramOnStartEnterTransitionListener); 
    } 
  }
  
  public FragmentTransaction setPrimaryNavigationFragment(Fragment paramFragment) {
    if (paramFragment == null || paramFragment.mFragmentManager == null || paramFragment.mFragmentManager == this.mManager)
      return super.setPrimaryNavigationFragment(paramFragment); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot setPrimaryNavigation for Fragment attached to a different FragmentManager. Fragment ");
    stringBuilder.append(paramFragment.toString());
    stringBuilder.append(" is already attached to a FragmentManager.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public FragmentTransaction show(Fragment paramFragment) {
    if (paramFragment.mFragmentManager == null || paramFragment.mFragmentManager == this.mManager)
      return super.show(paramFragment); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot show Fragment attached to a different FragmentManager. Fragment ");
    stringBuilder.append(paramFragment.toString());
    stringBuilder.append(" is already attached to a FragmentManager.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("BackStackEntry{");
    stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    if (this.mIndex >= 0) {
      stringBuilder.append(" #");
      stringBuilder.append(this.mIndex);
    } 
    if (this.mName != null) {
      stringBuilder.append(" ");
      stringBuilder.append(this.mName);
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  Fragment trackAddedFragmentsInPop(ArrayList<Fragment> paramArrayList, Fragment paramFragment) {
    int i = this.mOps.size() - 1;
    while (i >= 0) {
      FragmentTransaction.Op op = this.mOps.get(i);
      int j = op.mCmd;
      if (j != 1)
        if (j != 3) {
          switch (j) {
            case 10:
              op.mCurrentMaxState = op.mOldMaxState;
              break;
            case 9:
              paramFragment = op.mFragment;
              break;
            case 8:
              paramFragment = null;
              break;
            case 6:
              paramArrayList.add(op.mFragment);
              break;
            case 7:
              paramArrayList.remove(op.mFragment);
              break;
          } 
          i--;
        }  
    } 
    return paramFragment;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\BackStackRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */