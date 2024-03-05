package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.collection.ArraySet;
import androidx.core.util.DebugUtils;
import androidx.core.util.LogWriter;
import androidx.core.view.OneShotPreDrawListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory2 {
  static final int ANIM_DUR = 220;
  
  public static final int ANIM_STYLE_CLOSE_ENTER = 3;
  
  public static final int ANIM_STYLE_CLOSE_EXIT = 4;
  
  public static final int ANIM_STYLE_FADE_ENTER = 5;
  
  public static final int ANIM_STYLE_FADE_EXIT = 6;
  
  public static final int ANIM_STYLE_OPEN_ENTER = 1;
  
  public static final int ANIM_STYLE_OPEN_EXIT = 2;
  
  static boolean DEBUG = false;
  
  static final Interpolator DECELERATE_CUBIC;
  
  static final Interpolator DECELERATE_QUINT = (Interpolator)new DecelerateInterpolator(2.5F);
  
  static final String TAG = "FragmentManager";
  
  static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
  
  static final String TARGET_STATE_TAG = "android:target_state";
  
  static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
  
  static final String VIEW_STATE_TAG = "android:view_state";
  
  final HashMap<String, Fragment> mActive = new HashMap<String, Fragment>();
  
  final ArrayList<Fragment> mAdded = new ArrayList<Fragment>();
  
  ArrayList<Integer> mAvailBackStackIndices;
  
  ArrayList<BackStackRecord> mBackStack;
  
  ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
  
  ArrayList<BackStackRecord> mBackStackIndices;
  
  FragmentContainer mContainer;
  
  ArrayList<Fragment> mCreatedMenus;
  
  int mCurState = 0;
  
  boolean mDestroyed;
  
  Runnable mExecCommit = new Runnable() {
      final FragmentManagerImpl this$0;
      
      public void run() {
        FragmentManagerImpl.this.execPendingActions();
      }
    };
  
  boolean mExecutingActions;
  
  boolean mHavePendingDeferredStart;
  
  FragmentHostCallback mHost;
  
  private final CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder> mLifecycleCallbacks = new CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder>();
  
  boolean mNeedMenuInvalidate;
  
  int mNextFragmentIndex = 0;
  
  private FragmentManagerViewModel mNonConfig;
  
  private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) {
      final FragmentManagerImpl this$0;
      
      public void handleOnBackPressed() {
        FragmentManagerImpl.this.handleOnBackPressed();
      }
    };
  
  private OnBackPressedDispatcher mOnBackPressedDispatcher;
  
  Fragment mParent;
  
  ArrayList<OpGenerator> mPendingActions;
  
  ArrayList<StartEnterTransitionListener> mPostponedTransactions;
  
  Fragment mPrimaryNav;
  
  SparseArray<Parcelable> mStateArray = null;
  
  Bundle mStateBundle = null;
  
  boolean mStateSaved;
  
  boolean mStopped;
  
  ArrayList<Fragment> mTmpAddedFragments;
  
  ArrayList<Boolean> mTmpIsPop;
  
  ArrayList<BackStackRecord> mTmpRecords;
  
  static {
    DECELERATE_CUBIC = (Interpolator)new DecelerateInterpolator(1.5F);
  }
  
  private void addAddedFragments(ArraySet<Fragment> paramArraySet) {
    int i = this.mCurState;
    if (i < 1)
      return; 
    int j = Math.min(i, 3);
    int k = this.mAdded.size();
    for (i = 0; i < k; i++) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment.mState < j) {
        moveToState(fragment, j, fragment.getNextAnim(), fragment.getNextTransition(), false);
        if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded)
          paramArraySet.add(fragment); 
      } 
    } 
  }
  
  private void animateRemoveFragment(final Fragment fragment, AnimationOrAnimator paramAnimationOrAnimator, int paramInt) {
    EndViewTransitionAnimation endViewTransitionAnimation;
    final View viewToAnimate = fragment.mView;
    final ViewGroup container = fragment.mContainer;
    viewGroup.startViewTransition(view);
    fragment.setStateAfterAnimating(paramInt);
    if (paramAnimationOrAnimator.animation != null) {
      endViewTransitionAnimation = new EndViewTransitionAnimation(paramAnimationOrAnimator.animation, viewGroup, view);
      fragment.setAnimatingAway(fragment.mView);
      endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() {
            final FragmentManagerImpl this$0;
            
            final ViewGroup val$container;
            
            final Fragment val$fragment;
            
            public void onAnimationEnd(Animation param1Animation) {
              container.post(new Runnable() {
                    final FragmentManagerImpl.null this$1;
                    
                    public void run() {
                      if (fragment.getAnimatingAway() != null) {
                        fragment.setAnimatingAway(null);
                        FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                      } 
                    }
                  });
            }
            
            public void onAnimationRepeat(Animation param1Animation) {}
            
            public void onAnimationStart(Animation param1Animation) {}
          });
      fragment.mView.startAnimation((Animation)endViewTransitionAnimation);
    } else {
      Animator animator = ((AnimationOrAnimator)endViewTransitionAnimation).animator;
      fragment.setAnimator(((AnimationOrAnimator)endViewTransitionAnimation).animator);
      animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            final FragmentManagerImpl this$0;
            
            final ViewGroup val$container;
            
            final Fragment val$fragment;
            
            final View val$viewToAnimate;
            
            public void onAnimationEnd(Animator param1Animator) {
              container.endViewTransition(viewToAnimate);
              param1Animator = fragment.getAnimator();
              fragment.setAnimator(null);
              if (param1Animator != null && container.indexOfChild(viewToAnimate) < 0) {
                FragmentManagerImpl fragmentManagerImpl = FragmentManagerImpl.this;
                Fragment fragment = fragment;
                fragmentManagerImpl.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
              } 
            }
          });
      animator.setTarget(fragment.mView);
      animator.start();
    } 
  }
  
  private void burpActive() {
    this.mActive.values().removeAll(Collections.singleton(null));
  }
  
  private void checkStateLoss() {
    if (!isStateSaved())
      return; 
    throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
  }
  
  private void cleanupExec() {
    this.mExecutingActions = false;
    this.mTmpIsPop.clear();
    this.mTmpRecords.clear();
  }
  
  private void dispatchParentPrimaryNavigationFragmentChanged(Fragment paramFragment) {
    if (paramFragment != null && this.mActive.get(paramFragment.mWho) == paramFragment)
      paramFragment.performPrimaryNavigationFragmentChanged(); 
  }
  
  private void dispatchStateChange(int paramInt) {
    try {
      this.mExecutingActions = true;
      moveToState(paramInt, false);
      this.mExecutingActions = false;
      return;
    } finally {
      this.mExecutingActions = false;
    } 
  }
  
  private void endAnimatingAwayFragments() {
    for (Fragment fragment : this.mActive.values()) {
      if (fragment != null) {
        if (fragment.getAnimatingAway() != null) {
          int i = fragment.getStateAfterAnimating();
          View view = fragment.getAnimatingAway();
          Animation animation = view.getAnimation();
          if (animation != null) {
            animation.cancel();
            view.clearAnimation();
          } 
          fragment.setAnimatingAway(null);
          moveToState(fragment, i, 0, 0, false);
          continue;
        } 
        if (fragment.getAnimator() != null)
          fragment.getAnimator().end(); 
      } 
    } 
  }
  
  private void ensureExecReady(boolean paramBoolean) {
    if (!this.mExecutingActions) {
      if (this.mHost != null) {
        if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
          if (!paramBoolean)
            checkStateLoss(); 
          if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList<BackStackRecord>();
            this.mTmpIsPop = new ArrayList<Boolean>();
          } 
          this.mExecutingActions = true;
          try {
            executePostponedTransaction((ArrayList<BackStackRecord>)null, (ArrayList<Boolean>)null);
            return;
          } finally {
            this.mExecutingActions = false;
          } 
        } 
        throw new IllegalStateException("Must be called from main thread of fragment host");
      } 
      throw new IllegalStateException("Fragment host has been destroyed");
    } 
    throw new IllegalStateException("FragmentManager is already executing transactions");
  }
  
  private static void executeOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2) {
    while (paramInt1 < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(paramInt1);
      boolean bool1 = ((Boolean)paramArrayList1.get(paramInt1)).booleanValue();
      boolean bool = true;
      if (bool1) {
        backStackRecord.bumpBackStackNesting(-1);
        if (paramInt1 != paramInt2 - 1)
          bool = false; 
        backStackRecord.executePopOps(bool);
      } else {
        backStackRecord.bumpBackStackNesting(1);
        backStackRecord.executeOps();
      } 
      paramInt1++;
    } 
  }
  
  private void executeOpsTogether(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2) {
    int i = paramInt1;
    boolean bool1 = ((BackStackRecord)paramArrayList.get(i)).mReorderingAllowed;
    ArrayList<Fragment> arrayList = this.mTmpAddedFragments;
    if (arrayList == null) {
      this.mTmpAddedFragments = new ArrayList<Fragment>();
    } else {
      arrayList.clear();
    } 
    this.mTmpAddedFragments.addAll(this.mAdded);
    Fragment fragment = getPrimaryNavigationFragment();
    int j = i;
    boolean bool = false;
    while (j < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(j);
      if (!((Boolean)paramArrayList1.get(j)).booleanValue()) {
        fragment = backStackRecord.expandOps(this.mTmpAddedFragments, fragment);
      } else {
        fragment = backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, fragment);
      } 
      if (bool || backStackRecord.mAddToBackStack) {
        bool = true;
      } else {
        bool = false;
      } 
      j++;
    } 
    this.mTmpAddedFragments.clear();
    if (!bool1)
      FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, paramInt2, false); 
    executeOps(paramArrayList, paramArrayList1, paramInt1, paramInt2);
    if (bool1) {
      ArraySet<Fragment> arraySet = new ArraySet();
      addAddedFragments(arraySet);
      j = postponePostponableTransactions(paramArrayList, paramArrayList1, paramInt1, paramInt2, arraySet);
      makeRemovedFragmentsInvisible(arraySet);
    } else {
      j = paramInt2;
    } 
    int k = i;
    if (j != i) {
      k = i;
      if (bool1) {
        FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, j, true);
        moveToState(this.mCurState, true);
        k = i;
      } 
    } 
    while (k < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(k);
      if (((Boolean)paramArrayList1.get(k)).booleanValue() && backStackRecord.mIndex >= 0) {
        freeBackStackIndex(backStackRecord.mIndex);
        backStackRecord.mIndex = -1;
      } 
      backStackRecord.runOnCommitRunnables();
      k++;
    } 
    if (bool)
      reportBackStackChanged(); 
  }
  
  private void executePostponedTransaction(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   4: astore #7
    //   6: aload #7
    //   8: ifnonnull -> 16
    //   11: iconst_0
    //   12: istore_3
    //   13: goto -> 22
    //   16: aload #7
    //   18: invokevirtual size : ()I
    //   21: istore_3
    //   22: iconst_0
    //   23: istore #4
    //   25: iload_3
    //   26: istore #6
    //   28: iload #4
    //   30: iload #6
    //   32: if_icmpge -> 252
    //   35: aload_0
    //   36: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   39: iload #4
    //   41: invokevirtual get : (I)Ljava/lang/Object;
    //   44: checkcast androidx/fragment/app/FragmentManagerImpl$StartEnterTransitionListener
    //   47: astore #7
    //   49: aload_1
    //   50: ifnull -> 119
    //   53: aload #7
    //   55: getfield mIsBack : Z
    //   58: ifne -> 119
    //   61: aload_1
    //   62: aload #7
    //   64: getfield mRecord : Landroidx/fragment/app/BackStackRecord;
    //   67: invokevirtual indexOf : (Ljava/lang/Object;)I
    //   70: istore_3
    //   71: iload_3
    //   72: iconst_m1
    //   73: if_icmpeq -> 119
    //   76: aload_2
    //   77: iload_3
    //   78: invokevirtual get : (I)Ljava/lang/Object;
    //   81: checkcast java/lang/Boolean
    //   84: invokevirtual booleanValue : ()Z
    //   87: ifeq -> 119
    //   90: aload_0
    //   91: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   94: iload #4
    //   96: invokevirtual remove : (I)Ljava/lang/Object;
    //   99: pop
    //   100: iload #4
    //   102: iconst_1
    //   103: isub
    //   104: istore #5
    //   106: iload #6
    //   108: iconst_1
    //   109: isub
    //   110: istore_3
    //   111: aload #7
    //   113: invokevirtual cancelTransaction : ()V
    //   116: goto -> 240
    //   119: aload #7
    //   121: invokevirtual isReady : ()Z
    //   124: ifne -> 162
    //   127: iload #6
    //   129: istore_3
    //   130: iload #4
    //   132: istore #5
    //   134: aload_1
    //   135: ifnull -> 240
    //   138: iload #6
    //   140: istore_3
    //   141: iload #4
    //   143: istore #5
    //   145: aload #7
    //   147: getfield mRecord : Landroidx/fragment/app/BackStackRecord;
    //   150: aload_1
    //   151: iconst_0
    //   152: aload_1
    //   153: invokevirtual size : ()I
    //   156: invokevirtual interactsWith : (Ljava/util/ArrayList;II)Z
    //   159: ifeq -> 240
    //   162: aload_0
    //   163: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   166: iload #4
    //   168: invokevirtual remove : (I)Ljava/lang/Object;
    //   171: pop
    //   172: iload #4
    //   174: iconst_1
    //   175: isub
    //   176: istore #5
    //   178: iload #6
    //   180: iconst_1
    //   181: isub
    //   182: istore_3
    //   183: aload_1
    //   184: ifnull -> 235
    //   187: aload #7
    //   189: getfield mIsBack : Z
    //   192: ifne -> 235
    //   195: aload_1
    //   196: aload #7
    //   198: getfield mRecord : Landroidx/fragment/app/BackStackRecord;
    //   201: invokevirtual indexOf : (Ljava/lang/Object;)I
    //   204: istore #4
    //   206: iload #4
    //   208: iconst_m1
    //   209: if_icmpeq -> 235
    //   212: aload_2
    //   213: iload #4
    //   215: invokevirtual get : (I)Ljava/lang/Object;
    //   218: checkcast java/lang/Boolean
    //   221: invokevirtual booleanValue : ()Z
    //   224: ifeq -> 235
    //   227: aload #7
    //   229: invokevirtual cancelTransaction : ()V
    //   232: goto -> 240
    //   235: aload #7
    //   237: invokevirtual completeTransaction : ()V
    //   240: iload #5
    //   242: iconst_1
    //   243: iadd
    //   244: istore #4
    //   246: iload_3
    //   247: istore #6
    //   249: goto -> 28
    //   252: return
  }
  
  private Fragment findFragmentUnder(Fragment paramFragment) {
    ViewGroup viewGroup = paramFragment.mContainer;
    View view = paramFragment.mView;
    if (viewGroup != null && view != null)
      for (int i = this.mAdded.indexOf(paramFragment) - 1; i >= 0; i--) {
        paramFragment = this.mAdded.get(i);
        if (paramFragment.mContainer == viewGroup && paramFragment.mView != null)
          return paramFragment; 
      }  
    return null;
  }
  
  private void forcePostponedTransactions() {
    if (this.mPostponedTransactions != null)
      while (!this.mPostponedTransactions.isEmpty())
        ((StartEnterTransitionListener)this.mPostponedTransactions.remove(0)).completeTransaction();  
  }
  
  private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPendingActions : Ljava/util/ArrayList;
    //   6: astore #6
    //   8: iconst_0
    //   9: istore_3
    //   10: aload #6
    //   12: ifnull -> 101
    //   15: aload_0
    //   16: getfield mPendingActions : Ljava/util/ArrayList;
    //   19: invokevirtual size : ()I
    //   22: ifne -> 28
    //   25: goto -> 101
    //   28: aload_0
    //   29: getfield mPendingActions : Ljava/util/ArrayList;
    //   32: invokevirtual size : ()I
    //   35: istore #4
    //   37: iconst_0
    //   38: istore #5
    //   40: iload_3
    //   41: iload #4
    //   43: if_icmpge -> 75
    //   46: iload #5
    //   48: aload_0
    //   49: getfield mPendingActions : Ljava/util/ArrayList;
    //   52: iload_3
    //   53: invokevirtual get : (I)Ljava/lang/Object;
    //   56: checkcast androidx/fragment/app/FragmentManagerImpl$OpGenerator
    //   59: aload_1
    //   60: aload_2
    //   61: invokeinterface generateOps : (Ljava/util/ArrayList;Ljava/util/ArrayList;)Z
    //   66: ior
    //   67: istore #5
    //   69: iinc #3, 1
    //   72: goto -> 40
    //   75: aload_0
    //   76: getfield mPendingActions : Ljava/util/ArrayList;
    //   79: invokevirtual clear : ()V
    //   82: aload_0
    //   83: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   86: invokevirtual getHandler : ()Landroid/os/Handler;
    //   89: aload_0
    //   90: getfield mExecCommit : Ljava/lang/Runnable;
    //   93: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
    //   96: aload_0
    //   97: monitorexit
    //   98: iload #5
    //   100: ireturn
    //   101: aload_0
    //   102: monitorexit
    //   103: iconst_0
    //   104: ireturn
    //   105: astore_1
    //   106: aload_0
    //   107: monitorexit
    //   108: aload_1
    //   109: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	105	finally
    //   15	25	105	finally
    //   28	37	105	finally
    //   46	69	105	finally
    //   75	98	105	finally
    //   101	103	105	finally
    //   106	108	105	finally
  }
  
  private boolean isMenuAvailable(Fragment paramFragment) {
    boolean bool;
    if ((paramFragment.mHasMenu && paramFragment.mMenuVisible) || paramFragment.mChildFragmentManager.checkForMenus()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static AnimationOrAnimator makeFadeAnimation(float paramFloat1, float paramFloat2) {
    AlphaAnimation alphaAnimation = new AlphaAnimation(paramFloat1, paramFloat2);
    alphaAnimation.setInterpolator(DECELERATE_CUBIC);
    alphaAnimation.setDuration(220L);
    return new AnimationOrAnimator((Animation)alphaAnimation);
  }
  
  static AnimationOrAnimator makeOpenCloseAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    AnimationSet animationSet = new AnimationSet(false);
    ScaleAnimation scaleAnimation = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat1, paramFloat2, 1, 0.5F, 1, 0.5F);
    scaleAnimation.setInterpolator(DECELERATE_QUINT);
    scaleAnimation.setDuration(220L);
    animationSet.addAnimation((Animation)scaleAnimation);
    AlphaAnimation alphaAnimation = new AlphaAnimation(paramFloat3, paramFloat4);
    alphaAnimation.setInterpolator(DECELERATE_CUBIC);
    alphaAnimation.setDuration(220L);
    animationSet.addAnimation((Animation)alphaAnimation);
    return new AnimationOrAnimator((Animation)animationSet);
  }
  
  private void makeRemovedFragmentsInvisible(ArraySet<Fragment> paramArraySet) {
    int i = paramArraySet.size();
    for (byte b = 0; b < i; b++) {
      Fragment fragment = (Fragment)paramArraySet.valueAt(b);
      if (!fragment.mAdded) {
        View view = fragment.requireView();
        fragment.mPostponedAlpha = view.getAlpha();
        view.setAlpha(0.0F);
      } 
    } 
  }
  
  private boolean popBackStackImmediate(String paramString, int paramInt1, int paramInt2) {
    execPendingActions();
    ensureExecReady(true);
    Fragment fragment = this.mPrimaryNav;
    if (fragment != null && paramInt1 < 0 && paramString == null && fragment.getChildFragmentManager().popBackStackImmediate())
      return true; 
    boolean bool = popBackStackState(this.mTmpRecords, this.mTmpIsPop, paramString, paramInt1, paramInt2);
    if (bool) {
      this.mExecutingActions = true;
      try {
        removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      } finally {
        cleanupExec();
      } 
    } 
    updateOnBackPressedCallbackEnabled();
    doPendingDeferredStart();
    burpActive();
    return bool;
  }
  
  private int postponePostponableTransactions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, ArraySet<Fragment> paramArraySet) {
    int i = paramInt2 - 1;
    int j;
    for (j = paramInt2; i >= paramInt1; j = k) {
      boolean bool;
      BackStackRecord backStackRecord = paramArrayList.get(i);
      boolean bool1 = ((Boolean)paramArrayList1.get(i)).booleanValue();
      if (backStackRecord.isPostponed() && !backStackRecord.interactsWith(paramArrayList, i + 1, paramInt2)) {
        bool = true;
      } else {
        bool = false;
      } 
      int k = j;
      if (bool) {
        if (this.mPostponedTransactions == null)
          this.mPostponedTransactions = new ArrayList<StartEnterTransitionListener>(); 
        StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, bool1);
        this.mPostponedTransactions.add(startEnterTransitionListener);
        backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
        if (bool1) {
          backStackRecord.executeOps();
        } else {
          backStackRecord.executePopOps(false);
        } 
        k = j - 1;
        if (i != k) {
          paramArrayList.remove(i);
          paramArrayList.add(k, backStackRecord);
        } 
        addAddedFragments(paramArraySet);
      } 
      i--;
    } 
    return j;
  }
  
  private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    if (paramArrayList == null || paramArrayList.isEmpty())
      return; 
    if (paramArrayList1 != null && paramArrayList.size() == paramArrayList1.size()) {
      executePostponedTransaction(paramArrayList, paramArrayList1);
      int k = paramArrayList.size();
      int i = 0;
      int j;
      for (j = 0; i < k; j = m) {
        int n = i;
        int m = j;
        if (!((BackStackRecord)paramArrayList.get(i)).mReorderingAllowed) {
          if (j != i)
            executeOpsTogether(paramArrayList, paramArrayList1, j, i); 
          j = i + 1;
          m = j;
          if (((Boolean)paramArrayList1.get(i)).booleanValue())
            while (true) {
              m = j;
              if (j < k) {
                m = j;
                if (((Boolean)paramArrayList1.get(j)).booleanValue()) {
                  m = j;
                  if (!((BackStackRecord)paramArrayList.get(j)).mReorderingAllowed) {
                    j++;
                    continue;
                  } 
                } 
              } 
              break;
            }  
          executeOpsTogether(paramArrayList, paramArrayList1, i, m);
          n = m - 1;
        } 
        i = n + 1;
      } 
      if (j != k)
        executeOpsTogether(paramArrayList, paramArrayList1, j, k); 
      return;
    } 
    throw new IllegalStateException("Internal error with the back stack records");
  }
  
  public static int reverseTransit(int paramInt) {
    char c = ' ';
    if (paramInt != 4097)
      if (paramInt != 4099) {
        if (paramInt != 8194) {
          c = Character.MIN_VALUE;
        } else {
          c = 'ခ';
        } 
      } else {
        c = 'ဃ';
      }  
    return c;
  }
  
  private void throwException(RuntimeException paramRuntimeException) {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter printWriter = new PrintWriter((Writer)new LogWriter("FragmentManager"));
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      try {
        fragmentHostCallback.onDump("  ", (FileDescriptor)null, printWriter, new String[0]);
      } catch (Exception exception) {
        Log.e("FragmentManager", "Failed dumping state", exception);
      } 
    } else {
      try {
        dump("  ", (FileDescriptor)null, (PrintWriter)exception, new String[0]);
      } catch (Exception exception1) {
        Log.e("FragmentManager", "Failed dumping state", exception1);
      } 
    } 
    throw paramRuntimeException;
  }
  
  public static int transitToStyleIndex(int paramInt, boolean paramBoolean) {
    if (paramInt != 4097) {
      if (paramInt != 4099) {
        if (paramInt != 8194) {
          paramInt = -1;
        } else if (paramBoolean) {
          paramInt = 3;
        } else {
          paramInt = 4;
        } 
      } else if (paramBoolean) {
        paramInt = 5;
      } else {
        paramInt = 6;
      } 
    } else if (paramBoolean) {
      paramInt = 1;
    } else {
      paramInt = 2;
    } 
    return paramInt;
  }
  
  private void updateOnBackPressedCallbackEnabled() {
    ArrayList<OpGenerator> arrayList = this.mPendingActions;
    boolean bool = true;
    if (arrayList != null && !arrayList.isEmpty()) {
      this.mOnBackPressedCallback.setEnabled(true);
      return;
    } 
    OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
    if (getBackStackEntryCount() <= 0 || !isPrimaryNavigation(this.mParent))
      bool = false; 
    onBackPressedCallback.setEnabled(bool);
  }
  
  void addBackStackState(BackStackRecord paramBackStackRecord) {
    if (this.mBackStack == null)
      this.mBackStack = new ArrayList<BackStackRecord>(); 
    this.mBackStack.add(paramBackStackRecord);
  }
  
  public void addFragment(Fragment paramFragment, boolean paramBoolean) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("add: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    makeActive(paramFragment);
    if (!paramFragment.mDetached)
      if (!this.mAdded.contains(paramFragment)) {
        synchronized (this.mAdded) {
          this.mAdded.add(paramFragment);
          paramFragment.mAdded = true;
          paramFragment.mRemoving = false;
          if (paramFragment.mView == null)
            paramFragment.mHiddenChanged = false; 
          if (isMenuAvailable(paramFragment))
            this.mNeedMenuInvalidate = true; 
          if (paramBoolean)
            moveToState(paramFragment); 
        } 
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment already added: ");
        stringBuilder.append(paramFragment);
        throw new IllegalStateException(stringBuilder.toString());
      }  
  }
  
  public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener) {
    if (this.mBackStackChangeListeners == null)
      this.mBackStackChangeListeners = new ArrayList<FragmentManager.OnBackStackChangedListener>(); 
    this.mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  void addRetainedFragment(Fragment paramFragment) {
    if (isStateSaved()) {
      if (DEBUG)
        Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved"); 
      return;
    } 
    if (this.mNonConfig.addRetainedFragment(paramFragment) && DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Updating retained Fragments: Added ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
  }
  
  public int allocBackStackIndex(BackStackRecord paramBackStackRecord) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   6: ifnull -> 111
    //   9: aload_0
    //   10: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   13: invokevirtual size : ()I
    //   16: ifgt -> 22
    //   19: goto -> 111
    //   22: aload_0
    //   23: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   26: aload_0
    //   27: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   30: invokevirtual size : ()I
    //   33: iconst_1
    //   34: isub
    //   35: invokevirtual remove : (I)Ljava/lang/Object;
    //   38: checkcast java/lang/Integer
    //   41: invokevirtual intValue : ()I
    //   44: istore_2
    //   45: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   48: ifeq -> 97
    //   51: new java/lang/StringBuilder
    //   54: astore_3
    //   55: aload_3
    //   56: invokespecial <init> : ()V
    //   59: aload_3
    //   60: ldc_w 'Adding back stack index '
    //   63: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload_3
    //   68: iload_2
    //   69: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   72: pop
    //   73: aload_3
    //   74: ldc_w ' with '
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload_3
    //   82: aload_1
    //   83: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: ldc 'FragmentManager'
    //   89: aload_3
    //   90: invokevirtual toString : ()Ljava/lang/String;
    //   93: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   96: pop
    //   97: aload_0
    //   98: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   101: iload_2
    //   102: aload_1
    //   103: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: iload_2
    //   110: ireturn
    //   111: aload_0
    //   112: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   115: ifnonnull -> 131
    //   118: new java/util/ArrayList
    //   121: astore_3
    //   122: aload_3
    //   123: invokespecial <init> : ()V
    //   126: aload_0
    //   127: aload_3
    //   128: putfield mBackStackIndices : Ljava/util/ArrayList;
    //   131: aload_0
    //   132: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   135: invokevirtual size : ()I
    //   138: istore_2
    //   139: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   142: ifeq -> 191
    //   145: new java/lang/StringBuilder
    //   148: astore_3
    //   149: aload_3
    //   150: invokespecial <init> : ()V
    //   153: aload_3
    //   154: ldc_w 'Setting back stack index '
    //   157: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: pop
    //   161: aload_3
    //   162: iload_2
    //   163: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   166: pop
    //   167: aload_3
    //   168: ldc_w ' to '
    //   171: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: aload_3
    //   176: aload_1
    //   177: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   180: pop
    //   181: ldc 'FragmentManager'
    //   183: aload_3
    //   184: invokevirtual toString : ()Ljava/lang/String;
    //   187: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   190: pop
    //   191: aload_0
    //   192: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   195: aload_1
    //   196: invokevirtual add : (Ljava/lang/Object;)Z
    //   199: pop
    //   200: aload_0
    //   201: monitorexit
    //   202: iload_2
    //   203: ireturn
    //   204: astore_1
    //   205: aload_0
    //   206: monitorexit
    //   207: aload_1
    //   208: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	204	finally
    //   22	97	204	finally
    //   97	109	204	finally
    //   111	131	204	finally
    //   131	191	204	finally
    //   191	202	204	finally
    //   205	207	204	finally
  }
  
  public void attachController(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment) {
    if (this.mHost == null) {
      this.mHost = paramFragmentHostCallback;
      this.mContainer = paramFragmentContainer;
      this.mParent = paramFragment;
      if (paramFragment != null)
        updateOnBackPressedCallbackEnabled(); 
      if (paramFragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
        Fragment fragment;
        OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner)paramFragmentHostCallback;
        this.mOnBackPressedDispatcher = onBackPressedDispatcherOwner.getOnBackPressedDispatcher();
        if (paramFragment != null)
          fragment = paramFragment; 
        this.mOnBackPressedDispatcher.addCallback(fragment, this.mOnBackPressedCallback);
      } 
      if (paramFragment != null) {
        this.mNonConfig = paramFragment.mFragmentManager.getChildNonConfig(paramFragment);
      } else if (paramFragmentHostCallback instanceof ViewModelStoreOwner) {
        this.mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner)paramFragmentHostCallback).getViewModelStore());
      } else {
        this.mNonConfig = new FragmentManagerViewModel(false);
      } 
      return;
    } 
    throw new IllegalStateException("Already attached");
  }
  
  public void attachFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("attach: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (paramFragment.mDetached) {
      paramFragment.mDetached = false;
      if (!paramFragment.mAdded)
        if (!this.mAdded.contains(paramFragment)) {
          if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("add from attach: ");
            stringBuilder.append(paramFragment);
            Log.v("FragmentManager", stringBuilder.toString());
          } 
          synchronized (this.mAdded) {
            this.mAdded.add(paramFragment);
            paramFragment.mAdded = true;
            if (isMenuAvailable(paramFragment))
              this.mNeedMenuInvalidate = true; 
          } 
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Fragment already added: ");
          stringBuilder.append(paramFragment);
          throw new IllegalStateException(stringBuilder.toString());
        }  
    } 
  }
  
  public FragmentTransaction beginTransaction() {
    return new BackStackRecord(this);
  }
  
  boolean checkForMenus() {
    Iterator<Fragment> iterator = this.mActive.values().iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      Fragment fragment = iterator.next();
      boolean bool1 = bool;
      if (fragment != null)
        bool1 = isMenuAvailable(fragment); 
      bool = bool1;
      if (bool1)
        return true; 
    } 
    return false;
  }
  
  void completeExecute(BackStackRecord paramBackStackRecord, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    if (paramBoolean1) {
      paramBackStackRecord.executePopOps(paramBoolean3);
    } else {
      paramBackStackRecord.executeOps();
    } 
    ArrayList<BackStackRecord> arrayList1 = new ArrayList(1);
    ArrayList<Boolean> arrayList = new ArrayList(1);
    arrayList1.add(paramBackStackRecord);
    arrayList.add(Boolean.valueOf(paramBoolean1));
    if (paramBoolean2)
      FragmentTransition.startTransitions(this, arrayList1, arrayList, 0, 1, true); 
    if (paramBoolean3)
      moveToState(this.mCurState, true); 
    for (Fragment fragment : this.mActive.values()) {
      if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && paramBackStackRecord.interactsWith(fragment.mContainerId)) {
        if (fragment.mPostponedAlpha > 0.0F)
          fragment.mView.setAlpha(fragment.mPostponedAlpha); 
        if (paramBoolean3) {
          fragment.mPostponedAlpha = 0.0F;
          continue;
        } 
        fragment.mPostponedAlpha = -1.0F;
        fragment.mIsNewlyAdded = false;
      } 
    } 
  }
  
  void completeShowHideFragment(final Fragment fragment) {
    if (fragment.mView != null) {
      AnimationOrAnimator animationOrAnimator = loadAnimation(fragment, fragment.getNextTransition(), fragment.mHidden ^ true, fragment.getNextTransitionStyle());
      if (animationOrAnimator != null && animationOrAnimator.animator != null) {
        animationOrAnimator.animator.setTarget(fragment.mView);
        if (fragment.mHidden) {
          if (fragment.isHideReplaced()) {
            fragment.setHideReplaced(false);
          } else {
            final ViewGroup container = fragment.mContainer;
            final View animatingView = fragment.mView;
            viewGroup.startViewTransition(view);
            animationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
                  final FragmentManagerImpl this$0;
                  
                  final View val$animatingView;
                  
                  final ViewGroup val$container;
                  
                  final Fragment val$fragment;
                  
                  public void onAnimationEnd(Animator param1Animator) {
                    container.endViewTransition(animatingView);
                    param1Animator.removeListener((Animator.AnimatorListener)this);
                    if (fragment.mView != null && fragment.mHidden)
                      fragment.mView.setVisibility(8); 
                  }
                });
          } 
        } else {
          fragment.mView.setVisibility(0);
        } 
        animationOrAnimator.animator.start();
      } else {
        boolean bool;
        if (animationOrAnimator != null) {
          fragment.mView.startAnimation(animationOrAnimator.animation);
          animationOrAnimator.animation.start();
        } 
        if (fragment.mHidden && !fragment.isHideReplaced()) {
          bool = true;
        } else {
          bool = false;
        } 
        fragment.mView.setVisibility(bool);
        if (fragment.isHideReplaced())
          fragment.setHideReplaced(false); 
      } 
    } 
    if (fragment.mAdded && isMenuAvailable(fragment))
      this.mNeedMenuInvalidate = true; 
    fragment.mHiddenChanged = false;
    fragment.onHiddenChanged(fragment.mHidden);
  }
  
  public void detachFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("detach: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (!paramFragment.mDetached) {
      paramFragment.mDetached = true;
      if (paramFragment.mAdded) {
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("remove from detach: ");
          stringBuilder.append(paramFragment);
          Log.v("FragmentManager", stringBuilder.toString());
        } 
        synchronized (this.mAdded) {
          this.mAdded.remove(paramFragment);
          if (isMenuAvailable(paramFragment))
            this.mNeedMenuInvalidate = true; 
          paramFragment.mAdded = false;
        } 
      } 
    } 
  }
  
  public void dispatchActivityCreated() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(2);
  }
  
  public void dispatchConfigurationChanged(Configuration paramConfiguration) {
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.performConfigurationChanged(paramConfiguration); 
    } 
  }
  
  public boolean dispatchContextItemSelected(MenuItem paramMenuItem) {
    if (this.mCurState < 1)
      return false; 
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null && fragment.performContextItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  public void dispatchCreate() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(1);
  }
  
  public boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {
    int i = this.mCurState;
    boolean bool1 = false;
    if (i < 1)
      return false; 
    ArrayList<Fragment> arrayList = null;
    i = 0;
    boolean bool2;
    for (bool2 = false; i < this.mAdded.size(); bool2 = bool) {
      Fragment fragment = this.mAdded.get(i);
      ArrayList<Fragment> arrayList1 = arrayList;
      boolean bool = bool2;
      if (fragment != null) {
        arrayList1 = arrayList;
        bool = bool2;
        if (fragment.performCreateOptionsMenu(paramMenu, paramMenuInflater)) {
          arrayList1 = arrayList;
          if (arrayList == null)
            arrayList1 = new ArrayList(); 
          arrayList1.add(fragment);
          bool = true;
        } 
      } 
      i++;
      arrayList = arrayList1;
    } 
    if (this.mCreatedMenus != null)
      for (i = bool1; i < this.mCreatedMenus.size(); i++) {
        Fragment fragment = this.mCreatedMenus.get(i);
        if (arrayList == null || !arrayList.contains(fragment))
          fragment.onDestroyOptionsMenu(); 
      }  
    this.mCreatedMenus = arrayList;
    return bool2;
  }
  
  public void dispatchDestroy() {
    this.mDestroyed = true;
    execPendingActions();
    dispatchStateChange(0);
    this.mHost = null;
    this.mContainer = null;
    this.mParent = null;
    if (this.mOnBackPressedDispatcher != null) {
      this.mOnBackPressedCallback.remove();
      this.mOnBackPressedDispatcher = null;
    } 
  }
  
  public void dispatchDestroyView() {
    dispatchStateChange(1);
  }
  
  public void dispatchLowMemory() {
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.performLowMemory(); 
    } 
  }
  
  public void dispatchMultiWindowModeChanged(boolean paramBoolean) {
    for (int i = this.mAdded.size() - 1; i >= 0; i--) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment != null)
        fragment.performMultiWindowModeChanged(paramBoolean); 
    } 
  }
  
  void dispatchOnFragmentActivityCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentActivityCreated(paramFragment, paramBundle, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentActivityCreated(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentAttached(paramFragment, paramContext, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentAttached(this, paramFragment, paramContext); 
    } 
  }
  
  void dispatchOnFragmentCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentCreated(paramFragment, paramBundle, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentCreated(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentDestroyed(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentDestroyed(paramFragment, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentDestroyed(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentDetached(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentDetached(paramFragment, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentDetached(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentPaused(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPaused(paramFragment, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentPaused(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentPreAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPreAttached(paramFragment, paramContext, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentPreAttached(this, paramFragment, paramContext); 
    } 
  }
  
  void dispatchOnFragmentPreCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPreCreated(paramFragment, paramBundle, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentPreCreated(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentResumed(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentResumed(paramFragment, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentResumed(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentSaveInstanceState(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentSaveInstanceState(paramFragment, paramBundle, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentSaveInstanceState(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentStarted(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentStarted(paramFragment, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentStarted(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentStopped(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentStopped(paramFragment, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentStopped(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentViewCreated(Fragment paramFragment, View paramView, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewCreated(paramFragment, paramView, paramBundle, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentViewCreated(this, paramFragment, paramView, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentViewDestroyed(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewDestroyed(paramFragment, true); 
    } 
    for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
      if (!paramBoolean || fragmentLifecycleCallbacksHolder.mRecursive)
        fragmentLifecycleCallbacksHolder.mCallback.onFragmentViewDestroyed(this, paramFragment); 
    } 
  }
  
  public boolean dispatchOptionsItemSelected(MenuItem paramMenuItem) {
    if (this.mCurState < 1)
      return false; 
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null && fragment.performOptionsItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  public void dispatchOptionsMenuClosed(Menu paramMenu) {
    if (this.mCurState < 1)
      return; 
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.performOptionsMenuClosed(paramMenu); 
    } 
  }
  
  public void dispatchPause() {
    dispatchStateChange(3);
  }
  
  public void dispatchPictureInPictureModeChanged(boolean paramBoolean) {
    for (int i = this.mAdded.size() - 1; i >= 0; i--) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment != null)
        fragment.performPictureInPictureModeChanged(paramBoolean); 
    } 
  }
  
  public boolean dispatchPrepareOptionsMenu(Menu paramMenu) {
    int i = this.mCurState;
    byte b = 0;
    if (i < 1)
      return false; 
    boolean bool;
    for (bool = false; b < this.mAdded.size(); bool = bool1) {
      Fragment fragment = this.mAdded.get(b);
      boolean bool1 = bool;
      if (fragment != null) {
        bool1 = bool;
        if (fragment.performPrepareOptionsMenu(paramMenu))
          bool1 = true; 
      } 
      b++;
    } 
    return bool;
  }
  
  void dispatchPrimaryNavigationFragmentChanged() {
    updateOnBackPressedCallbackEnabled();
    dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
  }
  
  public void dispatchResume() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(4);
  }
  
  public void dispatchStart() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(3);
  }
  
  public void dispatchStop() {
    this.mStopped = true;
    dispatchStateChange(2);
  }
  
  void doPendingDeferredStart() {
    if (this.mHavePendingDeferredStart) {
      this.mHavePendingDeferredStart = false;
      startPendingDeferredFragments();
    } 
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #8
    //   9: aload #8
    //   11: aload_1
    //   12: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: pop
    //   16: aload #8
    //   18: ldc_w '    '
    //   21: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: aload #8
    //   27: invokevirtual toString : ()Ljava/lang/String;
    //   30: astore #8
    //   32: aload_0
    //   33: getfield mActive : Ljava/util/HashMap;
    //   36: invokevirtual isEmpty : ()Z
    //   39: ifne -> 138
    //   42: aload_3
    //   43: aload_1
    //   44: invokevirtual print : (Ljava/lang/String;)V
    //   47: aload_3
    //   48: ldc_w 'Active Fragments in '
    //   51: invokevirtual print : (Ljava/lang/String;)V
    //   54: aload_3
    //   55: aload_0
    //   56: invokestatic identityHashCode : (Ljava/lang/Object;)I
    //   59: invokestatic toHexString : (I)Ljava/lang/String;
    //   62: invokevirtual print : (Ljava/lang/String;)V
    //   65: aload_3
    //   66: ldc_w ':'
    //   69: invokevirtual println : (Ljava/lang/String;)V
    //   72: aload_0
    //   73: getfield mActive : Ljava/util/HashMap;
    //   76: invokevirtual values : ()Ljava/util/Collection;
    //   79: invokeinterface iterator : ()Ljava/util/Iterator;
    //   84: astore #9
    //   86: aload #9
    //   88: invokeinterface hasNext : ()Z
    //   93: ifeq -> 138
    //   96: aload #9
    //   98: invokeinterface next : ()Ljava/lang/Object;
    //   103: checkcast androidx/fragment/app/Fragment
    //   106: astore #10
    //   108: aload_3
    //   109: aload_1
    //   110: invokevirtual print : (Ljava/lang/String;)V
    //   113: aload_3
    //   114: aload #10
    //   116: invokevirtual println : (Ljava/lang/Object;)V
    //   119: aload #10
    //   121: ifnull -> 86
    //   124: aload #10
    //   126: aload #8
    //   128: aload_2
    //   129: aload_3
    //   130: aload #4
    //   132: invokevirtual dump : (Ljava/lang/String;Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    //   135: goto -> 86
    //   138: aload_0
    //   139: getfield mAdded : Ljava/util/ArrayList;
    //   142: invokevirtual size : ()I
    //   145: istore #7
    //   147: iconst_0
    //   148: istore #6
    //   150: iload #7
    //   152: ifle -> 229
    //   155: aload_3
    //   156: aload_1
    //   157: invokevirtual print : (Ljava/lang/String;)V
    //   160: aload_3
    //   161: ldc_w 'Added Fragments:'
    //   164: invokevirtual println : (Ljava/lang/String;)V
    //   167: iconst_0
    //   168: istore #5
    //   170: iload #5
    //   172: iload #7
    //   174: if_icmpge -> 229
    //   177: aload_0
    //   178: getfield mAdded : Ljava/util/ArrayList;
    //   181: iload #5
    //   183: invokevirtual get : (I)Ljava/lang/Object;
    //   186: checkcast androidx/fragment/app/Fragment
    //   189: astore_2
    //   190: aload_3
    //   191: aload_1
    //   192: invokevirtual print : (Ljava/lang/String;)V
    //   195: aload_3
    //   196: ldc_w '  #'
    //   199: invokevirtual print : (Ljava/lang/String;)V
    //   202: aload_3
    //   203: iload #5
    //   205: invokevirtual print : (I)V
    //   208: aload_3
    //   209: ldc_w ': '
    //   212: invokevirtual print : (Ljava/lang/String;)V
    //   215: aload_3
    //   216: aload_2
    //   217: invokevirtual toString : ()Ljava/lang/String;
    //   220: invokevirtual println : (Ljava/lang/String;)V
    //   223: iinc #5, 1
    //   226: goto -> 170
    //   229: aload_0
    //   230: getfield mCreatedMenus : Ljava/util/ArrayList;
    //   233: astore_2
    //   234: aload_2
    //   235: ifnull -> 323
    //   238: aload_2
    //   239: invokevirtual size : ()I
    //   242: istore #7
    //   244: iload #7
    //   246: ifle -> 323
    //   249: aload_3
    //   250: aload_1
    //   251: invokevirtual print : (Ljava/lang/String;)V
    //   254: aload_3
    //   255: ldc_w 'Fragments Created Menus:'
    //   258: invokevirtual println : (Ljava/lang/String;)V
    //   261: iconst_0
    //   262: istore #5
    //   264: iload #5
    //   266: iload #7
    //   268: if_icmpge -> 323
    //   271: aload_0
    //   272: getfield mCreatedMenus : Ljava/util/ArrayList;
    //   275: iload #5
    //   277: invokevirtual get : (I)Ljava/lang/Object;
    //   280: checkcast androidx/fragment/app/Fragment
    //   283: astore_2
    //   284: aload_3
    //   285: aload_1
    //   286: invokevirtual print : (Ljava/lang/String;)V
    //   289: aload_3
    //   290: ldc_w '  #'
    //   293: invokevirtual print : (Ljava/lang/String;)V
    //   296: aload_3
    //   297: iload #5
    //   299: invokevirtual print : (I)V
    //   302: aload_3
    //   303: ldc_w ': '
    //   306: invokevirtual print : (Ljava/lang/String;)V
    //   309: aload_3
    //   310: aload_2
    //   311: invokevirtual toString : ()Ljava/lang/String;
    //   314: invokevirtual println : (Ljava/lang/String;)V
    //   317: iinc #5, 1
    //   320: goto -> 264
    //   323: aload_0
    //   324: getfield mBackStack : Ljava/util/ArrayList;
    //   327: astore_2
    //   328: aload_2
    //   329: ifnull -> 424
    //   332: aload_2
    //   333: invokevirtual size : ()I
    //   336: istore #7
    //   338: iload #7
    //   340: ifle -> 424
    //   343: aload_3
    //   344: aload_1
    //   345: invokevirtual print : (Ljava/lang/String;)V
    //   348: aload_3
    //   349: ldc_w 'Back Stack:'
    //   352: invokevirtual println : (Ljava/lang/String;)V
    //   355: iconst_0
    //   356: istore #5
    //   358: iload #5
    //   360: iload #7
    //   362: if_icmpge -> 424
    //   365: aload_0
    //   366: getfield mBackStack : Ljava/util/ArrayList;
    //   369: iload #5
    //   371: invokevirtual get : (I)Ljava/lang/Object;
    //   374: checkcast androidx/fragment/app/BackStackRecord
    //   377: astore_2
    //   378: aload_3
    //   379: aload_1
    //   380: invokevirtual print : (Ljava/lang/String;)V
    //   383: aload_3
    //   384: ldc_w '  #'
    //   387: invokevirtual print : (Ljava/lang/String;)V
    //   390: aload_3
    //   391: iload #5
    //   393: invokevirtual print : (I)V
    //   396: aload_3
    //   397: ldc_w ': '
    //   400: invokevirtual print : (Ljava/lang/String;)V
    //   403: aload_3
    //   404: aload_2
    //   405: invokevirtual toString : ()Ljava/lang/String;
    //   408: invokevirtual println : (Ljava/lang/String;)V
    //   411: aload_2
    //   412: aload #8
    //   414: aload_3
    //   415: invokevirtual dump : (Ljava/lang/String;Ljava/io/PrintWriter;)V
    //   418: iinc #5, 1
    //   421: goto -> 358
    //   424: aload_0
    //   425: monitorenter
    //   426: aload_0
    //   427: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   430: ifnull -> 518
    //   433: aload_0
    //   434: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   437: invokevirtual size : ()I
    //   440: istore #7
    //   442: iload #7
    //   444: ifle -> 518
    //   447: aload_3
    //   448: aload_1
    //   449: invokevirtual print : (Ljava/lang/String;)V
    //   452: aload_3
    //   453: ldc_w 'Back Stack Indices:'
    //   456: invokevirtual println : (Ljava/lang/String;)V
    //   459: iconst_0
    //   460: istore #5
    //   462: iload #5
    //   464: iload #7
    //   466: if_icmpge -> 518
    //   469: aload_0
    //   470: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   473: iload #5
    //   475: invokevirtual get : (I)Ljava/lang/Object;
    //   478: checkcast androidx/fragment/app/BackStackRecord
    //   481: astore_2
    //   482: aload_3
    //   483: aload_1
    //   484: invokevirtual print : (Ljava/lang/String;)V
    //   487: aload_3
    //   488: ldc_w '  #'
    //   491: invokevirtual print : (Ljava/lang/String;)V
    //   494: aload_3
    //   495: iload #5
    //   497: invokevirtual print : (I)V
    //   500: aload_3
    //   501: ldc_w ': '
    //   504: invokevirtual print : (Ljava/lang/String;)V
    //   507: aload_3
    //   508: aload_2
    //   509: invokevirtual println : (Ljava/lang/Object;)V
    //   512: iinc #5, 1
    //   515: goto -> 462
    //   518: aload_0
    //   519: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   522: ifnull -> 561
    //   525: aload_0
    //   526: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   529: invokevirtual size : ()I
    //   532: ifle -> 561
    //   535: aload_3
    //   536: aload_1
    //   537: invokevirtual print : (Ljava/lang/String;)V
    //   540: aload_3
    //   541: ldc_w 'mAvailBackStackIndices: '
    //   544: invokevirtual print : (Ljava/lang/String;)V
    //   547: aload_3
    //   548: aload_0
    //   549: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   552: invokevirtual toArray : ()[Ljava/lang/Object;
    //   555: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
    //   558: invokevirtual println : (Ljava/lang/String;)V
    //   561: aload_0
    //   562: monitorexit
    //   563: aload_0
    //   564: getfield mPendingActions : Ljava/util/ArrayList;
    //   567: astore_2
    //   568: aload_2
    //   569: ifnull -> 655
    //   572: aload_2
    //   573: invokevirtual size : ()I
    //   576: istore #7
    //   578: iload #7
    //   580: ifle -> 655
    //   583: aload_3
    //   584: aload_1
    //   585: invokevirtual print : (Ljava/lang/String;)V
    //   588: aload_3
    //   589: ldc_w 'Pending Actions:'
    //   592: invokevirtual println : (Ljava/lang/String;)V
    //   595: iload #6
    //   597: istore #5
    //   599: iload #5
    //   601: iload #7
    //   603: if_icmpge -> 655
    //   606: aload_0
    //   607: getfield mPendingActions : Ljava/util/ArrayList;
    //   610: iload #5
    //   612: invokevirtual get : (I)Ljava/lang/Object;
    //   615: checkcast androidx/fragment/app/FragmentManagerImpl$OpGenerator
    //   618: astore_2
    //   619: aload_3
    //   620: aload_1
    //   621: invokevirtual print : (Ljava/lang/String;)V
    //   624: aload_3
    //   625: ldc_w '  #'
    //   628: invokevirtual print : (Ljava/lang/String;)V
    //   631: aload_3
    //   632: iload #5
    //   634: invokevirtual print : (I)V
    //   637: aload_3
    //   638: ldc_w ': '
    //   641: invokevirtual print : (Ljava/lang/String;)V
    //   644: aload_3
    //   645: aload_2
    //   646: invokevirtual println : (Ljava/lang/Object;)V
    //   649: iinc #5, 1
    //   652: goto -> 599
    //   655: aload_3
    //   656: aload_1
    //   657: invokevirtual print : (Ljava/lang/String;)V
    //   660: aload_3
    //   661: ldc_w 'FragmentManager misc state:'
    //   664: invokevirtual println : (Ljava/lang/String;)V
    //   667: aload_3
    //   668: aload_1
    //   669: invokevirtual print : (Ljava/lang/String;)V
    //   672: aload_3
    //   673: ldc_w '  mHost='
    //   676: invokevirtual print : (Ljava/lang/String;)V
    //   679: aload_3
    //   680: aload_0
    //   681: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   684: invokevirtual println : (Ljava/lang/Object;)V
    //   687: aload_3
    //   688: aload_1
    //   689: invokevirtual print : (Ljava/lang/String;)V
    //   692: aload_3
    //   693: ldc_w '  mContainer='
    //   696: invokevirtual print : (Ljava/lang/String;)V
    //   699: aload_3
    //   700: aload_0
    //   701: getfield mContainer : Landroidx/fragment/app/FragmentContainer;
    //   704: invokevirtual println : (Ljava/lang/Object;)V
    //   707: aload_0
    //   708: getfield mParent : Landroidx/fragment/app/Fragment;
    //   711: ifnull -> 734
    //   714: aload_3
    //   715: aload_1
    //   716: invokevirtual print : (Ljava/lang/String;)V
    //   719: aload_3
    //   720: ldc_w '  mParent='
    //   723: invokevirtual print : (Ljava/lang/String;)V
    //   726: aload_3
    //   727: aload_0
    //   728: getfield mParent : Landroidx/fragment/app/Fragment;
    //   731: invokevirtual println : (Ljava/lang/Object;)V
    //   734: aload_3
    //   735: aload_1
    //   736: invokevirtual print : (Ljava/lang/String;)V
    //   739: aload_3
    //   740: ldc_w '  mCurState='
    //   743: invokevirtual print : (Ljava/lang/String;)V
    //   746: aload_3
    //   747: aload_0
    //   748: getfield mCurState : I
    //   751: invokevirtual print : (I)V
    //   754: aload_3
    //   755: ldc_w ' mStateSaved='
    //   758: invokevirtual print : (Ljava/lang/String;)V
    //   761: aload_3
    //   762: aload_0
    //   763: getfield mStateSaved : Z
    //   766: invokevirtual print : (Z)V
    //   769: aload_3
    //   770: ldc_w ' mStopped='
    //   773: invokevirtual print : (Ljava/lang/String;)V
    //   776: aload_3
    //   777: aload_0
    //   778: getfield mStopped : Z
    //   781: invokevirtual print : (Z)V
    //   784: aload_3
    //   785: ldc_w ' mDestroyed='
    //   788: invokevirtual print : (Ljava/lang/String;)V
    //   791: aload_3
    //   792: aload_0
    //   793: getfield mDestroyed : Z
    //   796: invokevirtual println : (Z)V
    //   799: aload_0
    //   800: getfield mNeedMenuInvalidate : Z
    //   803: ifeq -> 826
    //   806: aload_3
    //   807: aload_1
    //   808: invokevirtual print : (Ljava/lang/String;)V
    //   811: aload_3
    //   812: ldc_w '  mNeedMenuInvalidate='
    //   815: invokevirtual print : (Ljava/lang/String;)V
    //   818: aload_3
    //   819: aload_0
    //   820: getfield mNeedMenuInvalidate : Z
    //   823: invokevirtual println : (Z)V
    //   826: return
    //   827: astore_1
    //   828: aload_0
    //   829: monitorexit
    //   830: aload_1
    //   831: athrow
    // Exception table:
    //   from	to	target	type
    //   426	442	827	finally
    //   447	459	827	finally
    //   469	512	827	finally
    //   518	561	827	finally
    //   561	563	827	finally
    //   828	830	827	finally
  }
  
  public void enqueueAction(OpGenerator paramOpGenerator, boolean paramBoolean) {
    // Byte code:
    //   0: iload_2
    //   1: ifne -> 8
    //   4: aload_0
    //   5: invokespecial checkStateLoss : ()V
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield mDestroyed : Z
    //   14: ifne -> 63
    //   17: aload_0
    //   18: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   21: ifnonnull -> 27
    //   24: goto -> 63
    //   27: aload_0
    //   28: getfield mPendingActions : Ljava/util/ArrayList;
    //   31: ifnonnull -> 47
    //   34: new java/util/ArrayList
    //   37: astore_3
    //   38: aload_3
    //   39: invokespecial <init> : ()V
    //   42: aload_0
    //   43: aload_3
    //   44: putfield mPendingActions : Ljava/util/ArrayList;
    //   47: aload_0
    //   48: getfield mPendingActions : Ljava/util/ArrayList;
    //   51: aload_1
    //   52: invokevirtual add : (Ljava/lang/Object;)Z
    //   55: pop
    //   56: aload_0
    //   57: invokevirtual scheduleCommit : ()V
    //   60: aload_0
    //   61: monitorexit
    //   62: return
    //   63: iload_2
    //   64: ifeq -> 70
    //   67: aload_0
    //   68: monitorexit
    //   69: return
    //   70: new java/lang/IllegalStateException
    //   73: astore_1
    //   74: aload_1
    //   75: ldc_w 'Activity has been destroyed'
    //   78: invokespecial <init> : (Ljava/lang/String;)V
    //   81: aload_1
    //   82: athrow
    //   83: astore_1
    //   84: aload_0
    //   85: monitorexit
    //   86: aload_1
    //   87: athrow
    // Exception table:
    //   from	to	target	type
    //   10	24	83	finally
    //   27	47	83	finally
    //   47	62	83	finally
    //   67	69	83	finally
    //   70	83	83	finally
    //   84	86	83	finally
  }
  
  void ensureInflatedFragmentView(Fragment paramFragment) {
    if (paramFragment.mFromLayout && !paramFragment.mPerformedCreateView) {
      paramFragment.performCreateView(paramFragment.performGetLayoutInflater(paramFragment.mSavedFragmentState), null, paramFragment.mSavedFragmentState);
      if (paramFragment.mView != null) {
        paramFragment.mInnerView = paramFragment.mView;
        paramFragment.mView.setSaveFromParentEnabled(false);
        if (paramFragment.mHidden)
          paramFragment.mView.setVisibility(8); 
        paramFragment.onViewCreated(paramFragment.mView, paramFragment.mSavedFragmentState);
        dispatchOnFragmentViewCreated(paramFragment, paramFragment.mView, paramFragment.mSavedFragmentState, false);
      } else {
        paramFragment.mInnerView = null;
      } 
    } 
  }
  
  public boolean execPendingActions() {
    ensureExecReady(true);
    boolean bool = false;
    while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
      try {
        removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
        cleanupExec();
      } finally {
        cleanupExec();
      } 
    } 
    updateOnBackPressedCallbackEnabled();
    doPendingDeferredStart();
    burpActive();
    return bool;
  }
  
  public void execSingleAction(OpGenerator paramOpGenerator, boolean paramBoolean) {
    if (paramBoolean && (this.mHost == null || this.mDestroyed))
      return; 
    ensureExecReady(paramBoolean);
    if (paramOpGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
      try {
        removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      } finally {
        cleanupExec();
      } 
    } 
    updateOnBackPressedCallbackEnabled();
    doPendingDeferredStart();
    burpActive();
  }
  
  public boolean executePendingTransactions() {
    boolean bool = execPendingActions();
    forcePostponedTransactions();
    return bool;
  }
  
  public Fragment findFragmentById(int paramInt) {
    for (int i = this.mAdded.size() - 1; i >= 0; i--) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment != null && fragment.mFragmentId == paramInt)
        return fragment; 
    } 
    for (Fragment fragment : this.mActive.values()) {
      if (fragment != null && fragment.mFragmentId == paramInt)
        return fragment; 
    } 
    return null;
  }
  
  public Fragment findFragmentByTag(String paramString) {
    if (paramString != null)
      for (int i = this.mAdded.size() - 1; i >= 0; i--) {
        Fragment fragment = this.mAdded.get(i);
        if (fragment != null && paramString.equals(fragment.mTag))
          return fragment; 
      }  
    if (paramString != null)
      for (Fragment fragment : this.mActive.values()) {
        if (fragment != null && paramString.equals(fragment.mTag))
          return fragment; 
      }  
    return null;
  }
  
  public Fragment findFragmentByWho(String paramString) {
    for (Fragment fragment : this.mActive.values()) {
      if (fragment != null) {
        fragment = fragment.findFragmentByWho(paramString);
        if (fragment != null)
          return fragment; 
      } 
    } 
    return null;
  }
  
  public void freeBackStackIndex(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   6: iload_1
    //   7: aconst_null
    //   8: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   11: pop
    //   12: aload_0
    //   13: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   16: ifnonnull -> 32
    //   19: new java/util/ArrayList
    //   22: astore_2
    //   23: aload_2
    //   24: invokespecial <init> : ()V
    //   27: aload_0
    //   28: aload_2
    //   29: putfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   32: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   35: ifeq -> 70
    //   38: new java/lang/StringBuilder
    //   41: astore_2
    //   42: aload_2
    //   43: invokespecial <init> : ()V
    //   46: aload_2
    //   47: ldc_w 'Freeing back stack index '
    //   50: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: pop
    //   54: aload_2
    //   55: iload_1
    //   56: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: ldc 'FragmentManager'
    //   62: aload_2
    //   63: invokevirtual toString : ()Ljava/lang/String;
    //   66: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   69: pop
    //   70: aload_0
    //   71: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   74: iload_1
    //   75: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   78: invokevirtual add : (Ljava/lang/Object;)Z
    //   81: pop
    //   82: aload_0
    //   83: monitorexit
    //   84: return
    //   85: astore_2
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_2
    //   89: athrow
    // Exception table:
    //   from	to	target	type
    //   2	32	85	finally
    //   32	70	85	finally
    //   70	84	85	finally
    //   86	88	85	finally
  }
  
  int getActiveFragmentCount() {
    return this.mActive.size();
  }
  
  List<Fragment> getActiveFragments() {
    return new ArrayList<Fragment>(this.mActive.values());
  }
  
  public FragmentManager.BackStackEntry getBackStackEntryAt(int paramInt) {
    return this.mBackStack.get(paramInt);
  }
  
  public int getBackStackEntryCount() {
    boolean bool;
    ArrayList<BackStackRecord> arrayList = this.mBackStack;
    if (arrayList != null) {
      bool = arrayList.size();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  FragmentManagerViewModel getChildNonConfig(Fragment paramFragment) {
    return this.mNonConfig.getChildNonConfig(paramFragment);
  }
  
  public Fragment getFragment(Bundle paramBundle, String paramString) {
    String str = paramBundle.getString(paramString);
    if (str == null)
      return null; 
    Fragment fragment = this.mActive.get(str);
    if (fragment == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Fragment no longer exists for key ");
      stringBuilder.append(paramString);
      stringBuilder.append(": unique id ");
      stringBuilder.append(str);
      throwException(new IllegalStateException(stringBuilder.toString()));
    } 
    return fragment;
  }
  
  public FragmentFactory getFragmentFactory() {
    if (super.getFragmentFactory() == DEFAULT_FACTORY) {
      Fragment fragment = this.mParent;
      if (fragment != null)
        return fragment.mFragmentManager.getFragmentFactory(); 
      setFragmentFactory(new FragmentFactory() {
            final FragmentManagerImpl this$0;
            
            public Fragment instantiate(ClassLoader param1ClassLoader, String param1String) {
              return FragmentManagerImpl.this.mHost.instantiate(FragmentManagerImpl.this.mHost.getContext(), param1String, null);
            }
          });
    } 
    return super.getFragmentFactory();
  }
  
  public List<Fragment> getFragments() {
    if (this.mAdded.isEmpty())
      return Collections.emptyList(); 
    synchronized (this.mAdded) {
      return (List)this.mAdded.clone();
    } 
  }
  
  LayoutInflater.Factory2 getLayoutInflaterFactory() {
    return this;
  }
  
  public Fragment getPrimaryNavigationFragment() {
    return this.mPrimaryNav;
  }
  
  ViewModelStore getViewModelStore(Fragment paramFragment) {
    return this.mNonConfig.getViewModelStore(paramFragment);
  }
  
  void handleOnBackPressed() {
    execPendingActions();
    if (this.mOnBackPressedCallback.isEnabled()) {
      popBackStackImmediate();
    } else {
      this.mOnBackPressedDispatcher.onBackPressed();
    } 
  }
  
  public void hideFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("hide: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (!paramFragment.mHidden) {
      paramFragment.mHidden = true;
      paramFragment.mHiddenChanged = true ^ paramFragment.mHiddenChanged;
    } 
  }
  
  public boolean isDestroyed() {
    return this.mDestroyed;
  }
  
  boolean isPrimaryNavigation(Fragment paramFragment) {
    boolean bool = true;
    if (paramFragment == null)
      return true; 
    FragmentManagerImpl fragmentManagerImpl = paramFragment.mFragmentManager;
    if (paramFragment != fragmentManagerImpl.getPrimaryNavigationFragment() || !isPrimaryNavigation(fragmentManagerImpl.mParent))
      bool = false; 
    return bool;
  }
  
  boolean isStateAtLeast(int paramInt) {
    boolean bool;
    if (this.mCurState >= paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isStateSaved() {
    return (this.mStateSaved || this.mStopped);
  }
  
  AnimationOrAnimator loadAnimation(Fragment paramFragment, int paramInt1, boolean paramBoolean, int paramInt2) {
    int i = paramFragment.getNextAnim();
    boolean bool = false;
    paramFragment.setNextAnim(0);
    if (paramFragment.mContainer != null && paramFragment.mContainer.getLayoutTransition() != null)
      return null; 
    Animation animation = paramFragment.onCreateAnimation(paramInt1, paramBoolean, i);
    if (animation != null)
      return new AnimationOrAnimator(animation); 
    Animator animator = paramFragment.onCreateAnimator(paramInt1, paramBoolean, i);
    if (animator != null)
      return new AnimationOrAnimator(animator); 
    if (i != 0) {
      boolean bool2 = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(i));
      boolean bool1 = bool;
      if (bool2)
        try {
          Animation animation1 = AnimationUtils.loadAnimation(this.mHost.getContext(), i);
          if (animation1 != null)
            return new AnimationOrAnimator(animation1); 
          bool1 = true;
        } catch (android.content.res.Resources.NotFoundException notFoundException) {
          throw notFoundException;
        } catch (RuntimeException runtimeException) {
          bool1 = bool;
        }  
      if (!bool1)
        try {
          animator = AnimatorInflater.loadAnimator(this.mHost.getContext(), i);
          if (animator != null)
            return new AnimationOrAnimator(animator); 
        } catch (RuntimeException runtimeException) {
          Animation animation1;
          if (!bool2) {
            animation1 = AnimationUtils.loadAnimation(this.mHost.getContext(), i);
            if (animation1 != null)
              return new AnimationOrAnimator(animation1); 
          } else {
            throw animation1;
          } 
        }  
    } 
    if (paramInt1 == 0)
      return null; 
    paramInt1 = transitToStyleIndex(paramInt1, paramBoolean);
    if (paramInt1 < 0)
      return null; 
    switch (paramInt1) {
      default:
        paramInt1 = paramInt2;
        if (paramInt2 == 0) {
          paramInt1 = paramInt2;
          if (this.mHost.onHasWindowAnimations())
            paramInt1 = this.mHost.onGetWindowAnimations(); 
        } 
        break;
      case 6:
        return makeFadeAnimation(1.0F, 0.0F);
      case 5:
        return makeFadeAnimation(0.0F, 1.0F);
      case 4:
        return makeOpenCloseAnimation(1.0F, 1.075F, 1.0F, 0.0F);
      case 3:
        return makeOpenCloseAnimation(0.975F, 1.0F, 0.0F, 1.0F);
      case 2:
        return makeOpenCloseAnimation(1.0F, 0.975F, 1.0F, 0.0F);
      case 1:
        return makeOpenCloseAnimation(1.125F, 1.0F, 0.0F, 1.0F);
    } 
    if (paramInt1 == 0);
    return null;
  }
  
  void makeActive(Fragment paramFragment) {
    if (this.mActive.get(paramFragment.mWho) != null)
      return; 
    this.mActive.put(paramFragment.mWho, paramFragment);
    if (paramFragment.mRetainInstanceChangedWhileDetached) {
      if (paramFragment.mRetainInstance) {
        addRetainedFragment(paramFragment);
      } else {
        removeRetainedFragment(paramFragment);
      } 
      paramFragment.mRetainInstanceChangedWhileDetached = false;
    } 
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Added fragment to active set ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
  }
  
  void makeInactive(Fragment paramFragment) {
    if (this.mActive.get(paramFragment.mWho) == null)
      return; 
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Removed fragment from active set ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    for (Fragment fragment : this.mActive.values()) {
      if (fragment != null && paramFragment.mWho.equals(fragment.mTargetWho)) {
        fragment.mTarget = paramFragment;
        fragment.mTargetWho = null;
      } 
    } 
    this.mActive.put(paramFragment.mWho, null);
    removeRetainedFragment(paramFragment);
    if (paramFragment.mTargetWho != null)
      paramFragment.mTarget = this.mActive.get(paramFragment.mTargetWho); 
    paramFragment.initState();
  }
  
  void moveFragmentToExpectedState(Fragment paramFragment) {
    if (paramFragment == null)
      return; 
    if (!this.mActive.containsKey(paramFragment.mWho)) {
      if (DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ignoring moving ");
        stringBuilder.append(paramFragment);
        stringBuilder.append(" to state ");
        stringBuilder.append(this.mCurState);
        stringBuilder.append("since it is not added to ");
        stringBuilder.append(this);
        Log.v("FragmentManager", stringBuilder.toString());
      } 
      return;
    } 
    int j = this.mCurState;
    int i = j;
    if (paramFragment.mRemoving)
      if (paramFragment.isInBackStack()) {
        i = Math.min(j, 1);
      } else {
        i = Math.min(j, 0);
      }  
    moveToState(paramFragment, i, paramFragment.getNextTransition(), paramFragment.getNextTransitionStyle(), false);
    if (paramFragment.mView != null) {
      Fragment fragment = findFragmentUnder(paramFragment);
      if (fragment != null) {
        View view = fragment.mView;
        ViewGroup viewGroup = paramFragment.mContainer;
        i = viewGroup.indexOfChild(view);
        j = viewGroup.indexOfChild(paramFragment.mView);
        if (j < i) {
          viewGroup.removeViewAt(j);
          viewGroup.addView(paramFragment.mView, i);
        } 
      } 
      if (paramFragment.mIsNewlyAdded && paramFragment.mContainer != null) {
        if (paramFragment.mPostponedAlpha > 0.0F)
          paramFragment.mView.setAlpha(paramFragment.mPostponedAlpha); 
        paramFragment.mPostponedAlpha = 0.0F;
        paramFragment.mIsNewlyAdded = false;
        AnimationOrAnimator animationOrAnimator = loadAnimation(paramFragment, paramFragment.getNextTransition(), true, paramFragment.getNextTransitionStyle());
        if (animationOrAnimator != null)
          if (animationOrAnimator.animation != null) {
            paramFragment.mView.startAnimation(animationOrAnimator.animation);
          } else {
            animationOrAnimator.animator.setTarget(paramFragment.mView);
            animationOrAnimator.animator.start();
          }  
      } 
    } 
    if (paramFragment.mHiddenChanged)
      completeShowHideFragment(paramFragment); 
  }
  
  void moveToState(int paramInt, boolean paramBoolean) {
    if (this.mHost != null || paramInt == 0) {
      if (!paramBoolean && paramInt == this.mCurState)
        return; 
      this.mCurState = paramInt;
      int i = this.mAdded.size();
      for (paramInt = 0; paramInt < i; paramInt++)
        moveFragmentToExpectedState(this.mAdded.get(paramInt)); 
      for (Fragment fragment : this.mActive.values()) {
        if (fragment != null && (fragment.mRemoving || fragment.mDetached) && !fragment.mIsNewlyAdded)
          moveFragmentToExpectedState(fragment); 
      } 
      startPendingDeferredFragments();
      if (this.mNeedMenuInvalidate) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null && this.mCurState == 4) {
          fragmentHostCallback.onSupportInvalidateOptionsMenu();
          this.mNeedMenuInvalidate = false;
        } 
      } 
      return;
    } 
    throw new IllegalStateException("No activity");
  }
  
  void moveToState(Fragment paramFragment) {
    moveToState(paramFragment, this.mCurState, 0, 0, false);
  }
  
  void moveToState(Fragment paramFragment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    // Byte code:
    //   0: aload_1
    //   1: getfield mAdded : Z
    //   4: istore #10
    //   6: iconst_1
    //   7: istore #9
    //   9: iconst_1
    //   10: istore #7
    //   12: iconst_1
    //   13: istore #8
    //   15: iload #10
    //   17: ifeq -> 36
    //   20: aload_1
    //   21: getfield mDetached : Z
    //   24: ifeq -> 30
    //   27: goto -> 36
    //   30: iload_2
    //   31: istore #6
    //   33: goto -> 47
    //   36: iload_2
    //   37: istore #6
    //   39: iload_2
    //   40: iconst_1
    //   41: if_icmple -> 47
    //   44: iconst_1
    //   45: istore #6
    //   47: iload #6
    //   49: istore_2
    //   50: aload_1
    //   51: getfield mRemoving : Z
    //   54: ifeq -> 93
    //   57: iload #6
    //   59: istore_2
    //   60: iload #6
    //   62: aload_1
    //   63: getfield mState : I
    //   66: if_icmple -> 93
    //   69: aload_1
    //   70: getfield mState : I
    //   73: ifne -> 88
    //   76: aload_1
    //   77: invokevirtual isInBackStack : ()Z
    //   80: ifeq -> 88
    //   83: iconst_1
    //   84: istore_2
    //   85: goto -> 93
    //   88: aload_1
    //   89: getfield mState : I
    //   92: istore_2
    //   93: iload_2
    //   94: istore #6
    //   96: aload_1
    //   97: getfield mDeferStart : Z
    //   100: ifeq -> 125
    //   103: iload_2
    //   104: istore #6
    //   106: aload_1
    //   107: getfield mState : I
    //   110: iconst_3
    //   111: if_icmpge -> 125
    //   114: iload_2
    //   115: istore #6
    //   117: iload_2
    //   118: iconst_2
    //   119: if_icmple -> 125
    //   122: iconst_2
    //   123: istore #6
    //   125: aload_1
    //   126: getfield mMaxState : Landroidx/lifecycle/Lifecycle$State;
    //   129: getstatic androidx/lifecycle/Lifecycle$State.CREATED : Landroidx/lifecycle/Lifecycle$State;
    //   132: if_acmpne -> 145
    //   135: iload #6
    //   137: iconst_1
    //   138: invokestatic min : (II)I
    //   141: istore_2
    //   142: goto -> 158
    //   145: iload #6
    //   147: aload_1
    //   148: getfield mMaxState : Landroidx/lifecycle/Lifecycle$State;
    //   151: invokevirtual ordinal : ()I
    //   154: invokestatic min : (II)I
    //   157: istore_2
    //   158: aload_1
    //   159: getfield mState : I
    //   162: iload_2
    //   163: if_icmpgt -> 1497
    //   166: aload_1
    //   167: getfield mFromLayout : Z
    //   170: ifeq -> 181
    //   173: aload_1
    //   174: getfield mInLayout : Z
    //   177: ifne -> 181
    //   180: return
    //   181: aload_1
    //   182: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   185: ifnonnull -> 195
    //   188: aload_1
    //   189: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   192: ifnull -> 217
    //   195: aload_1
    //   196: aconst_null
    //   197: invokevirtual setAnimatingAway : (Landroid/view/View;)V
    //   200: aload_1
    //   201: aconst_null
    //   202: invokevirtual setAnimator : (Landroid/animation/Animator;)V
    //   205: aload_0
    //   206: aload_1
    //   207: aload_1
    //   208: invokevirtual getStateAfterAnimating : ()I
    //   211: iconst_0
    //   212: iconst_0
    //   213: iconst_1
    //   214: invokevirtual moveToState : (Landroidx/fragment/app/Fragment;IIIZ)V
    //   217: aload_1
    //   218: getfield mState : I
    //   221: istore #6
    //   223: iload #6
    //   225: ifeq -> 265
    //   228: iload_2
    //   229: istore_3
    //   230: iload #6
    //   232: iconst_1
    //   233: if_icmpeq -> 886
    //   236: iload_2
    //   237: istore #4
    //   239: iload #6
    //   241: iconst_2
    //   242: if_icmpeq -> 262
    //   245: iload_2
    //   246: istore_3
    //   247: iload #6
    //   249: iconst_3
    //   250: if_icmpeq -> 259
    //   253: iload_2
    //   254: istore #6
    //   256: goto -> 2291
    //   259: goto -> 1421
    //   262: goto -> 1354
    //   265: iload_2
    //   266: istore_3
    //   267: iload_2
    //   268: ifle -> 886
    //   271: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   274: ifeq -> 313
    //   277: new java/lang/StringBuilder
    //   280: dup
    //   281: invokespecial <init> : ()V
    //   284: astore #11
    //   286: aload #11
    //   288: ldc_w 'moveto CREATED: '
    //   291: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: pop
    //   295: aload #11
    //   297: aload_1
    //   298: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   301: pop
    //   302: ldc 'FragmentManager'
    //   304: aload #11
    //   306: invokevirtual toString : ()Ljava/lang/String;
    //   309: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   312: pop
    //   313: iload_2
    //   314: istore_3
    //   315: aload_1
    //   316: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   319: ifnull -> 472
    //   322: aload_1
    //   323: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   326: aload_0
    //   327: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   330: invokevirtual getContext : ()Landroid/content/Context;
    //   333: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   336: invokevirtual setClassLoader : (Ljava/lang/ClassLoader;)V
    //   339: aload_1
    //   340: aload_1
    //   341: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   344: ldc 'android:view_state'
    //   346: invokevirtual getSparseParcelableArray : (Ljava/lang/String;)Landroid/util/SparseArray;
    //   349: putfield mSavedViewState : Landroid/util/SparseArray;
    //   352: aload_0
    //   353: aload_1
    //   354: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   357: ldc 'android:target_state'
    //   359: invokevirtual getFragment : (Landroid/os/Bundle;Ljava/lang/String;)Landroidx/fragment/app/Fragment;
    //   362: astore #11
    //   364: aload #11
    //   366: ifnull -> 379
    //   369: aload #11
    //   371: getfield mWho : Ljava/lang/String;
    //   374: astore #11
    //   376: goto -> 382
    //   379: aconst_null
    //   380: astore #11
    //   382: aload_1
    //   383: aload #11
    //   385: putfield mTargetWho : Ljava/lang/String;
    //   388: aload_1
    //   389: getfield mTargetWho : Ljava/lang/String;
    //   392: ifnull -> 409
    //   395: aload_1
    //   396: aload_1
    //   397: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   400: ldc 'android:target_req_state'
    //   402: iconst_0
    //   403: invokevirtual getInt : (Ljava/lang/String;I)I
    //   406: putfield mTargetRequestCode : I
    //   409: aload_1
    //   410: getfield mSavedUserVisibleHint : Ljava/lang/Boolean;
    //   413: ifnull -> 435
    //   416: aload_1
    //   417: aload_1
    //   418: getfield mSavedUserVisibleHint : Ljava/lang/Boolean;
    //   421: invokevirtual booleanValue : ()Z
    //   424: putfield mUserVisibleHint : Z
    //   427: aload_1
    //   428: aconst_null
    //   429: putfield mSavedUserVisibleHint : Ljava/lang/Boolean;
    //   432: goto -> 449
    //   435: aload_1
    //   436: aload_1
    //   437: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   440: ldc 'android:user_visible_hint'
    //   442: iconst_1
    //   443: invokevirtual getBoolean : (Ljava/lang/String;Z)Z
    //   446: putfield mUserVisibleHint : Z
    //   449: iload_2
    //   450: istore_3
    //   451: aload_1
    //   452: getfield mUserVisibleHint : Z
    //   455: ifne -> 472
    //   458: aload_1
    //   459: iconst_1
    //   460: putfield mDeferStart : Z
    //   463: iload_2
    //   464: istore_3
    //   465: iload_2
    //   466: iconst_2
    //   467: if_icmple -> 472
    //   470: iconst_2
    //   471: istore_3
    //   472: aload_1
    //   473: aload_0
    //   474: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   477: putfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   480: aload_1
    //   481: aload_0
    //   482: getfield mParent : Landroidx/fragment/app/Fragment;
    //   485: putfield mParentFragment : Landroidx/fragment/app/Fragment;
    //   488: aload_0
    //   489: getfield mParent : Landroidx/fragment/app/Fragment;
    //   492: astore #11
    //   494: aload #11
    //   496: ifnull -> 509
    //   499: aload #11
    //   501: getfield mChildFragmentManager : Landroidx/fragment/app/FragmentManagerImpl;
    //   504: astore #11
    //   506: goto -> 518
    //   509: aload_0
    //   510: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   513: getfield mFragmentManager : Landroidx/fragment/app/FragmentManagerImpl;
    //   516: astore #11
    //   518: aload_1
    //   519: aload #11
    //   521: putfield mFragmentManager : Landroidx/fragment/app/FragmentManagerImpl;
    //   524: aload_1
    //   525: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   528: ifnull -> 663
    //   531: aload_0
    //   532: getfield mActive : Ljava/util/HashMap;
    //   535: aload_1
    //   536: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   539: getfield mWho : Ljava/lang/String;
    //   542: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   545: aload_1
    //   546: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   549: if_acmpne -> 597
    //   552: aload_1
    //   553: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   556: getfield mState : I
    //   559: iconst_1
    //   560: if_icmpge -> 578
    //   563: aload_0
    //   564: aload_1
    //   565: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   568: iconst_1
    //   569: iconst_0
    //   570: iconst_0
    //   571: iconst_1
    //   572: invokevirtual moveToState : (Landroidx/fragment/app/Fragment;IIIZ)V
    //   575: goto -> 578
    //   578: aload_1
    //   579: aload_1
    //   580: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   583: getfield mWho : Ljava/lang/String;
    //   586: putfield mTargetWho : Ljava/lang/String;
    //   589: aload_1
    //   590: aconst_null
    //   591: putfield mTarget : Landroidx/fragment/app/Fragment;
    //   594: goto -> 663
    //   597: new java/lang/StringBuilder
    //   600: dup
    //   601: invokespecial <init> : ()V
    //   604: astore #11
    //   606: aload #11
    //   608: ldc_w 'Fragment '
    //   611: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   614: pop
    //   615: aload #11
    //   617: aload_1
    //   618: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   621: pop
    //   622: aload #11
    //   624: ldc_w ' declared target fragment '
    //   627: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   630: pop
    //   631: aload #11
    //   633: aload_1
    //   634: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   637: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   640: pop
    //   641: aload #11
    //   643: ldc_w ' that does not belong to this FragmentManager!'
    //   646: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   649: pop
    //   650: new java/lang/IllegalStateException
    //   653: dup
    //   654: aload #11
    //   656: invokevirtual toString : ()Ljava/lang/String;
    //   659: invokespecial <init> : (Ljava/lang/String;)V
    //   662: athrow
    //   663: aload_1
    //   664: getfield mTargetWho : Ljava/lang/String;
    //   667: ifnull -> 779
    //   670: aload_0
    //   671: getfield mActive : Ljava/util/HashMap;
    //   674: aload_1
    //   675: getfield mTargetWho : Ljava/lang/String;
    //   678: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   681: checkcast androidx/fragment/app/Fragment
    //   684: astore #11
    //   686: aload #11
    //   688: ifnull -> 713
    //   691: aload #11
    //   693: getfield mState : I
    //   696: iconst_1
    //   697: if_icmpge -> 779
    //   700: aload_0
    //   701: aload #11
    //   703: iconst_1
    //   704: iconst_0
    //   705: iconst_0
    //   706: iconst_1
    //   707: invokevirtual moveToState : (Landroidx/fragment/app/Fragment;IIIZ)V
    //   710: goto -> 779
    //   713: new java/lang/StringBuilder
    //   716: dup
    //   717: invokespecial <init> : ()V
    //   720: astore #11
    //   722: aload #11
    //   724: ldc_w 'Fragment '
    //   727: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   730: pop
    //   731: aload #11
    //   733: aload_1
    //   734: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   737: pop
    //   738: aload #11
    //   740: ldc_w ' declared target fragment '
    //   743: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   746: pop
    //   747: aload #11
    //   749: aload_1
    //   750: getfield mTargetWho : Ljava/lang/String;
    //   753: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   756: pop
    //   757: aload #11
    //   759: ldc_w ' that does not belong to this FragmentManager!'
    //   762: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   765: pop
    //   766: new java/lang/IllegalStateException
    //   769: dup
    //   770: aload #11
    //   772: invokevirtual toString : ()Ljava/lang/String;
    //   775: invokespecial <init> : (Ljava/lang/String;)V
    //   778: athrow
    //   779: aload_0
    //   780: aload_1
    //   781: aload_0
    //   782: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   785: invokevirtual getContext : ()Landroid/content/Context;
    //   788: iconst_0
    //   789: invokevirtual dispatchOnFragmentPreAttached : (Landroidx/fragment/app/Fragment;Landroid/content/Context;Z)V
    //   792: aload_1
    //   793: invokevirtual performAttach : ()V
    //   796: aload_1
    //   797: getfield mParentFragment : Landroidx/fragment/app/Fragment;
    //   800: ifnonnull -> 814
    //   803: aload_0
    //   804: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   807: aload_1
    //   808: invokevirtual onAttachFragment : (Landroidx/fragment/app/Fragment;)V
    //   811: goto -> 822
    //   814: aload_1
    //   815: getfield mParentFragment : Landroidx/fragment/app/Fragment;
    //   818: aload_1
    //   819: invokevirtual onAttachFragment : (Landroidx/fragment/app/Fragment;)V
    //   822: aload_0
    //   823: aload_1
    //   824: aload_0
    //   825: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   828: invokevirtual getContext : ()Landroid/content/Context;
    //   831: iconst_0
    //   832: invokevirtual dispatchOnFragmentAttached : (Landroidx/fragment/app/Fragment;Landroid/content/Context;Z)V
    //   835: aload_1
    //   836: getfield mIsCreated : Z
    //   839: ifne -> 873
    //   842: aload_0
    //   843: aload_1
    //   844: aload_1
    //   845: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   848: iconst_0
    //   849: invokevirtual dispatchOnFragmentPreCreated : (Landroidx/fragment/app/Fragment;Landroid/os/Bundle;Z)V
    //   852: aload_1
    //   853: aload_1
    //   854: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   857: invokevirtual performCreate : (Landroid/os/Bundle;)V
    //   860: aload_0
    //   861: aload_1
    //   862: aload_1
    //   863: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   866: iconst_0
    //   867: invokevirtual dispatchOnFragmentCreated : (Landroidx/fragment/app/Fragment;Landroid/os/Bundle;Z)V
    //   870: goto -> 886
    //   873: aload_1
    //   874: aload_1
    //   875: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   878: invokevirtual restoreChildFragmentState : (Landroid/os/Bundle;)V
    //   881: aload_1
    //   882: iconst_1
    //   883: putfield mState : I
    //   886: iload_3
    //   887: ifle -> 895
    //   890: aload_0
    //   891: aload_1
    //   892: invokevirtual ensureInflatedFragmentView : (Landroidx/fragment/app/Fragment;)V
    //   895: iload_3
    //   896: istore #4
    //   898: iload_3
    //   899: iconst_1
    //   900: if_icmple -> 262
    //   903: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   906: ifeq -> 945
    //   909: new java/lang/StringBuilder
    //   912: dup
    //   913: invokespecial <init> : ()V
    //   916: astore #11
    //   918: aload #11
    //   920: ldc_w 'moveto ACTIVITY_CREATED: '
    //   923: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   926: pop
    //   927: aload #11
    //   929: aload_1
    //   930: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   933: pop
    //   934: ldc 'FragmentManager'
    //   936: aload #11
    //   938: invokevirtual toString : ()Ljava/lang/String;
    //   941: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   944: pop
    //   945: aload_1
    //   946: getfield mFromLayout : Z
    //   949: ifne -> 1310
    //   952: aload_1
    //   953: getfield mContainerId : I
    //   956: ifeq -> 1163
    //   959: aload_1
    //   960: getfield mContainerId : I
    //   963: iconst_m1
    //   964: if_icmpne -> 1017
    //   967: new java/lang/StringBuilder
    //   970: dup
    //   971: invokespecial <init> : ()V
    //   974: astore #11
    //   976: aload #11
    //   978: ldc_w 'Cannot create fragment '
    //   981: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   984: pop
    //   985: aload #11
    //   987: aload_1
    //   988: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   991: pop
    //   992: aload #11
    //   994: ldc_w ' for a container view with no id'
    //   997: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1000: pop
    //   1001: aload_0
    //   1002: new java/lang/IllegalArgumentException
    //   1005: dup
    //   1006: aload #11
    //   1008: invokevirtual toString : ()Ljava/lang/String;
    //   1011: invokespecial <init> : (Ljava/lang/String;)V
    //   1014: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   1017: aload_0
    //   1018: getfield mContainer : Landroidx/fragment/app/FragmentContainer;
    //   1021: aload_1
    //   1022: getfield mContainerId : I
    //   1025: invokevirtual onFindViewById : (I)Landroid/view/View;
    //   1028: checkcast android/view/ViewGroup
    //   1031: astore #12
    //   1033: aload #12
    //   1035: astore #11
    //   1037: aload #12
    //   1039: ifnonnull -> 1166
    //   1042: aload #12
    //   1044: astore #11
    //   1046: aload_1
    //   1047: getfield mRestored : Z
    //   1050: ifne -> 1166
    //   1053: aload_1
    //   1054: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   1057: aload_1
    //   1058: getfield mContainerId : I
    //   1061: invokevirtual getResourceName : (I)Ljava/lang/String;
    //   1064: astore #11
    //   1066: goto -> 1076
    //   1069: astore #11
    //   1071: ldc_w 'unknown'
    //   1074: astore #11
    //   1076: new java/lang/StringBuilder
    //   1079: dup
    //   1080: invokespecial <init> : ()V
    //   1083: astore #13
    //   1085: aload #13
    //   1087: ldc_w 'No view found for id 0x'
    //   1090: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1093: pop
    //   1094: aload #13
    //   1096: aload_1
    //   1097: getfield mContainerId : I
    //   1100: invokestatic toHexString : (I)Ljava/lang/String;
    //   1103: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1106: pop
    //   1107: aload #13
    //   1109: ldc_w ' ('
    //   1112: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1115: pop
    //   1116: aload #13
    //   1118: aload #11
    //   1120: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1123: pop
    //   1124: aload #13
    //   1126: ldc_w ') for fragment '
    //   1129: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1132: pop
    //   1133: aload #13
    //   1135: aload_1
    //   1136: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1139: pop
    //   1140: aload_0
    //   1141: new java/lang/IllegalArgumentException
    //   1144: dup
    //   1145: aload #13
    //   1147: invokevirtual toString : ()Ljava/lang/String;
    //   1150: invokespecial <init> : (Ljava/lang/String;)V
    //   1153: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   1156: aload #12
    //   1158: astore #11
    //   1160: goto -> 1166
    //   1163: aconst_null
    //   1164: astore #11
    //   1166: aload_1
    //   1167: aload #11
    //   1169: putfield mContainer : Landroid/view/ViewGroup;
    //   1172: aload_1
    //   1173: aload_1
    //   1174: aload_1
    //   1175: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1178: invokevirtual performGetLayoutInflater : (Landroid/os/Bundle;)Landroid/view/LayoutInflater;
    //   1181: aload #11
    //   1183: aload_1
    //   1184: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1187: invokevirtual performCreateView : (Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)V
    //   1190: aload_1
    //   1191: getfield mView : Landroid/view/View;
    //   1194: ifnull -> 1305
    //   1197: aload_1
    //   1198: aload_1
    //   1199: getfield mView : Landroid/view/View;
    //   1202: putfield mInnerView : Landroid/view/View;
    //   1205: aload_1
    //   1206: getfield mView : Landroid/view/View;
    //   1209: iconst_0
    //   1210: invokevirtual setSaveFromParentEnabled : (Z)V
    //   1213: aload #11
    //   1215: ifnull -> 1227
    //   1218: aload #11
    //   1220: aload_1
    //   1221: getfield mView : Landroid/view/View;
    //   1224: invokevirtual addView : (Landroid/view/View;)V
    //   1227: aload_1
    //   1228: getfield mHidden : Z
    //   1231: ifeq -> 1243
    //   1234: aload_1
    //   1235: getfield mView : Landroid/view/View;
    //   1238: bipush #8
    //   1240: invokevirtual setVisibility : (I)V
    //   1243: aload_1
    //   1244: aload_1
    //   1245: getfield mView : Landroid/view/View;
    //   1248: aload_1
    //   1249: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1252: invokevirtual onViewCreated : (Landroid/view/View;Landroid/os/Bundle;)V
    //   1255: aload_0
    //   1256: aload_1
    //   1257: aload_1
    //   1258: getfield mView : Landroid/view/View;
    //   1261: aload_1
    //   1262: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1265: iconst_0
    //   1266: invokevirtual dispatchOnFragmentViewCreated : (Landroidx/fragment/app/Fragment;Landroid/view/View;Landroid/os/Bundle;Z)V
    //   1269: aload_1
    //   1270: getfield mView : Landroid/view/View;
    //   1273: invokevirtual getVisibility : ()I
    //   1276: ifne -> 1293
    //   1279: aload_1
    //   1280: getfield mContainer : Landroid/view/ViewGroup;
    //   1283: ifnull -> 1293
    //   1286: iload #8
    //   1288: istore #5
    //   1290: goto -> 1296
    //   1293: iconst_0
    //   1294: istore #5
    //   1296: aload_1
    //   1297: iload #5
    //   1299: putfield mIsNewlyAdded : Z
    //   1302: goto -> 1310
    //   1305: aload_1
    //   1306: aconst_null
    //   1307: putfield mInnerView : Landroid/view/View;
    //   1310: aload_1
    //   1311: aload_1
    //   1312: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1315: invokevirtual performActivityCreated : (Landroid/os/Bundle;)V
    //   1318: aload_0
    //   1319: aload_1
    //   1320: aload_1
    //   1321: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1324: iconst_0
    //   1325: invokevirtual dispatchOnFragmentActivityCreated : (Landroidx/fragment/app/Fragment;Landroid/os/Bundle;Z)V
    //   1328: aload_1
    //   1329: getfield mView : Landroid/view/View;
    //   1332: ifnull -> 1343
    //   1335: aload_1
    //   1336: aload_1
    //   1337: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1340: invokevirtual restoreViewState : (Landroid/os/Bundle;)V
    //   1343: aload_1
    //   1344: aconst_null
    //   1345: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   1348: iload_3
    //   1349: istore #4
    //   1351: goto -> 262
    //   1354: iload #4
    //   1356: istore_3
    //   1357: iload #4
    //   1359: iconst_2
    //   1360: if_icmple -> 259
    //   1363: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   1366: ifeq -> 1405
    //   1369: new java/lang/StringBuilder
    //   1372: dup
    //   1373: invokespecial <init> : ()V
    //   1376: astore #11
    //   1378: aload #11
    //   1380: ldc_w 'moveto STARTED: '
    //   1383: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1386: pop
    //   1387: aload #11
    //   1389: aload_1
    //   1390: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1393: pop
    //   1394: ldc 'FragmentManager'
    //   1396: aload #11
    //   1398: invokevirtual toString : ()Ljava/lang/String;
    //   1401: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1404: pop
    //   1405: aload_1
    //   1406: invokevirtual performStart : ()V
    //   1409: aload_0
    //   1410: aload_1
    //   1411: iconst_0
    //   1412: invokevirtual dispatchOnFragmentStarted : (Landroidx/fragment/app/Fragment;Z)V
    //   1415: iload #4
    //   1417: istore_3
    //   1418: goto -> 259
    //   1421: iload_3
    //   1422: istore #6
    //   1424: iload_3
    //   1425: iconst_3
    //   1426: if_icmple -> 2291
    //   1429: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   1432: ifeq -> 1471
    //   1435: new java/lang/StringBuilder
    //   1438: dup
    //   1439: invokespecial <init> : ()V
    //   1442: astore #11
    //   1444: aload #11
    //   1446: ldc_w 'moveto RESUMED: '
    //   1449: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1452: pop
    //   1453: aload #11
    //   1455: aload_1
    //   1456: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1459: pop
    //   1460: ldc 'FragmentManager'
    //   1462: aload #11
    //   1464: invokevirtual toString : ()Ljava/lang/String;
    //   1467: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1470: pop
    //   1471: aload_1
    //   1472: invokevirtual performResume : ()V
    //   1475: aload_0
    //   1476: aload_1
    //   1477: iconst_0
    //   1478: invokevirtual dispatchOnFragmentResumed : (Landroidx/fragment/app/Fragment;Z)V
    //   1481: aload_1
    //   1482: aconst_null
    //   1483: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   1486: aload_1
    //   1487: aconst_null
    //   1488: putfield mSavedViewState : Landroid/util/SparseArray;
    //   1491: iload_3
    //   1492: istore #6
    //   1494: goto -> 2291
    //   1497: iload_2
    //   1498: istore #6
    //   1500: aload_1
    //   1501: getfield mState : I
    //   1504: iload_2
    //   1505: if_icmple -> 2291
    //   1508: aload_1
    //   1509: getfield mState : I
    //   1512: istore #6
    //   1514: iload #6
    //   1516: iconst_1
    //   1517: if_icmpeq -> 1906
    //   1520: iload #6
    //   1522: iconst_2
    //   1523: if_icmpeq -> 1658
    //   1526: iload #6
    //   1528: iconst_3
    //   1529: if_icmpeq -> 1601
    //   1532: iload #6
    //   1534: iconst_4
    //   1535: if_icmpeq -> 1544
    //   1538: iload_2
    //   1539: istore #6
    //   1541: goto -> 2291
    //   1544: iload_2
    //   1545: iconst_4
    //   1546: if_icmpge -> 1601
    //   1549: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   1552: ifeq -> 1591
    //   1555: new java/lang/StringBuilder
    //   1558: dup
    //   1559: invokespecial <init> : ()V
    //   1562: astore #11
    //   1564: aload #11
    //   1566: ldc_w 'movefrom RESUMED: '
    //   1569: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1572: pop
    //   1573: aload #11
    //   1575: aload_1
    //   1576: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1579: pop
    //   1580: ldc 'FragmentManager'
    //   1582: aload #11
    //   1584: invokevirtual toString : ()Ljava/lang/String;
    //   1587: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1590: pop
    //   1591: aload_1
    //   1592: invokevirtual performPause : ()V
    //   1595: aload_0
    //   1596: aload_1
    //   1597: iconst_0
    //   1598: invokevirtual dispatchOnFragmentPaused : (Landroidx/fragment/app/Fragment;Z)V
    //   1601: iload_2
    //   1602: iconst_3
    //   1603: if_icmpge -> 1658
    //   1606: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   1609: ifeq -> 1648
    //   1612: new java/lang/StringBuilder
    //   1615: dup
    //   1616: invokespecial <init> : ()V
    //   1619: astore #11
    //   1621: aload #11
    //   1623: ldc_w 'movefrom STARTED: '
    //   1626: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1629: pop
    //   1630: aload #11
    //   1632: aload_1
    //   1633: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1636: pop
    //   1637: ldc 'FragmentManager'
    //   1639: aload #11
    //   1641: invokevirtual toString : ()Ljava/lang/String;
    //   1644: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1647: pop
    //   1648: aload_1
    //   1649: invokevirtual performStop : ()V
    //   1652: aload_0
    //   1653: aload_1
    //   1654: iconst_0
    //   1655: invokevirtual dispatchOnFragmentStopped : (Landroidx/fragment/app/Fragment;Z)V
    //   1658: iload_2
    //   1659: iconst_2
    //   1660: if_icmpge -> 1906
    //   1663: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   1666: ifeq -> 1705
    //   1669: new java/lang/StringBuilder
    //   1672: dup
    //   1673: invokespecial <init> : ()V
    //   1676: astore #11
    //   1678: aload #11
    //   1680: ldc_w 'movefrom ACTIVITY_CREATED: '
    //   1683: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1686: pop
    //   1687: aload #11
    //   1689: aload_1
    //   1690: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1693: pop
    //   1694: ldc 'FragmentManager'
    //   1696: aload #11
    //   1698: invokevirtual toString : ()Ljava/lang/String;
    //   1701: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1704: pop
    //   1705: aload_1
    //   1706: getfield mView : Landroid/view/View;
    //   1709: ifnull -> 1735
    //   1712: aload_0
    //   1713: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   1716: aload_1
    //   1717: invokevirtual onShouldSaveFragmentState : (Landroidx/fragment/app/Fragment;)Z
    //   1720: ifeq -> 1735
    //   1723: aload_1
    //   1724: getfield mSavedViewState : Landroid/util/SparseArray;
    //   1727: ifnonnull -> 1735
    //   1730: aload_0
    //   1731: aload_1
    //   1732: invokevirtual saveFragmentViewState : (Landroidx/fragment/app/Fragment;)V
    //   1735: aload_1
    //   1736: invokevirtual performDestroyView : ()V
    //   1739: aload_0
    //   1740: aload_1
    //   1741: iconst_0
    //   1742: invokevirtual dispatchOnFragmentViewDestroyed : (Landroidx/fragment/app/Fragment;Z)V
    //   1745: aload_1
    //   1746: getfield mView : Landroid/view/View;
    //   1749: ifnull -> 1873
    //   1752: aload_1
    //   1753: getfield mContainer : Landroid/view/ViewGroup;
    //   1756: ifnull -> 1873
    //   1759: aload_1
    //   1760: getfield mContainer : Landroid/view/ViewGroup;
    //   1763: aload_1
    //   1764: getfield mView : Landroid/view/View;
    //   1767: invokevirtual endViewTransition : (Landroid/view/View;)V
    //   1770: aload_1
    //   1771: getfield mView : Landroid/view/View;
    //   1774: invokevirtual clearAnimation : ()V
    //   1777: aload_1
    //   1778: invokevirtual getParentFragment : ()Landroidx/fragment/app/Fragment;
    //   1781: ifnull -> 1794
    //   1784: aload_1
    //   1785: invokevirtual getParentFragment : ()Landroidx/fragment/app/Fragment;
    //   1788: getfield mRemoving : Z
    //   1791: ifne -> 1873
    //   1794: aload_0
    //   1795: getfield mCurState : I
    //   1798: ifle -> 1841
    //   1801: aload_0
    //   1802: getfield mDestroyed : Z
    //   1805: ifne -> 1841
    //   1808: aload_1
    //   1809: getfield mView : Landroid/view/View;
    //   1812: invokevirtual getVisibility : ()I
    //   1815: ifne -> 1841
    //   1818: aload_1
    //   1819: getfield mPostponedAlpha : F
    //   1822: fconst_0
    //   1823: fcmpl
    //   1824: iflt -> 1841
    //   1827: aload_0
    //   1828: aload_1
    //   1829: iload_3
    //   1830: iconst_0
    //   1831: iload #4
    //   1833: invokevirtual loadAnimation : (Landroidx/fragment/app/Fragment;IZI)Landroidx/fragment/app/FragmentManagerImpl$AnimationOrAnimator;
    //   1836: astore #11
    //   1838: goto -> 1844
    //   1841: aconst_null
    //   1842: astore #11
    //   1844: aload_1
    //   1845: fconst_0
    //   1846: putfield mPostponedAlpha : F
    //   1849: aload #11
    //   1851: ifnull -> 1862
    //   1854: aload_0
    //   1855: aload_1
    //   1856: aload #11
    //   1858: iload_2
    //   1859: invokespecial animateRemoveFragment : (Landroidx/fragment/app/Fragment;Landroidx/fragment/app/FragmentManagerImpl$AnimationOrAnimator;I)V
    //   1862: aload_1
    //   1863: getfield mContainer : Landroid/view/ViewGroup;
    //   1866: aload_1
    //   1867: getfield mView : Landroid/view/View;
    //   1870: invokevirtual removeView : (Landroid/view/View;)V
    //   1873: aload_1
    //   1874: aconst_null
    //   1875: putfield mContainer : Landroid/view/ViewGroup;
    //   1878: aload_1
    //   1879: aconst_null
    //   1880: putfield mView : Landroid/view/View;
    //   1883: aload_1
    //   1884: aconst_null
    //   1885: putfield mViewLifecycleOwner : Landroidx/fragment/app/FragmentViewLifecycleOwner;
    //   1888: aload_1
    //   1889: getfield mViewLifecycleOwnerLiveData : Landroidx/lifecycle/MutableLiveData;
    //   1892: aconst_null
    //   1893: invokevirtual setValue : (Ljava/lang/Object;)V
    //   1896: aload_1
    //   1897: aconst_null
    //   1898: putfield mInnerView : Landroid/view/View;
    //   1901: aload_1
    //   1902: iconst_0
    //   1903: putfield mInLayout : Z
    //   1906: iload_2
    //   1907: istore #6
    //   1909: iload_2
    //   1910: iconst_1
    //   1911: if_icmpge -> 2291
    //   1914: aload_0
    //   1915: getfield mDestroyed : Z
    //   1918: ifeq -> 1970
    //   1921: aload_1
    //   1922: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1925: ifnull -> 1947
    //   1928: aload_1
    //   1929: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1932: astore #11
    //   1934: aload_1
    //   1935: aconst_null
    //   1936: invokevirtual setAnimatingAway : (Landroid/view/View;)V
    //   1939: aload #11
    //   1941: invokevirtual clearAnimation : ()V
    //   1944: goto -> 1970
    //   1947: aload_1
    //   1948: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   1951: ifnull -> 1970
    //   1954: aload_1
    //   1955: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   1958: astore #11
    //   1960: aload_1
    //   1961: aconst_null
    //   1962: invokevirtual setAnimator : (Landroid/animation/Animator;)V
    //   1965: aload #11
    //   1967: invokevirtual cancel : ()V
    //   1970: aload_1
    //   1971: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1974: ifnonnull -> 2280
    //   1977: aload_1
    //   1978: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   1981: ifnull -> 1987
    //   1984: goto -> 2280
    //   1987: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   1990: ifeq -> 2029
    //   1993: new java/lang/StringBuilder
    //   1996: dup
    //   1997: invokespecial <init> : ()V
    //   2000: astore #11
    //   2002: aload #11
    //   2004: ldc_w 'movefrom CREATED: '
    //   2007: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2010: pop
    //   2011: aload #11
    //   2013: aload_1
    //   2014: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2017: pop
    //   2018: ldc 'FragmentManager'
    //   2020: aload #11
    //   2022: invokevirtual toString : ()Ljava/lang/String;
    //   2025: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   2028: pop
    //   2029: aload_1
    //   2030: getfield mRemoving : Z
    //   2033: ifeq -> 2048
    //   2036: aload_1
    //   2037: invokevirtual isInBackStack : ()Z
    //   2040: ifne -> 2048
    //   2043: iconst_1
    //   2044: istore_3
    //   2045: goto -> 2050
    //   2048: iconst_0
    //   2049: istore_3
    //   2050: iload_3
    //   2051: ifne -> 2076
    //   2054: aload_0
    //   2055: getfield mNonConfig : Landroidx/fragment/app/FragmentManagerViewModel;
    //   2058: aload_1
    //   2059: invokevirtual shouldDestroy : (Landroidx/fragment/app/Fragment;)Z
    //   2062: ifeq -> 2068
    //   2065: goto -> 2076
    //   2068: aload_1
    //   2069: iconst_0
    //   2070: putfield mState : I
    //   2073: goto -> 2161
    //   2076: aload_0
    //   2077: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   2080: astore #11
    //   2082: aload #11
    //   2084: instanceof androidx/lifecycle/ViewModelStoreOwner
    //   2087: ifeq -> 2102
    //   2090: aload_0
    //   2091: getfield mNonConfig : Landroidx/fragment/app/FragmentManagerViewModel;
    //   2094: invokevirtual isCleared : ()Z
    //   2097: istore #8
    //   2099: goto -> 2134
    //   2102: iload #9
    //   2104: istore #8
    //   2106: aload #11
    //   2108: invokevirtual getContext : ()Landroid/content/Context;
    //   2111: instanceof android/app/Activity
    //   2114: ifeq -> 2134
    //   2117: iconst_1
    //   2118: aload_0
    //   2119: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   2122: invokevirtual getContext : ()Landroid/content/Context;
    //   2125: checkcast android/app/Activity
    //   2128: invokevirtual isChangingConfigurations : ()Z
    //   2131: ixor
    //   2132: istore #8
    //   2134: iload_3
    //   2135: ifne -> 2143
    //   2138: iload #8
    //   2140: ifeq -> 2151
    //   2143: aload_0
    //   2144: getfield mNonConfig : Landroidx/fragment/app/FragmentManagerViewModel;
    //   2147: aload_1
    //   2148: invokevirtual clearNonConfigState : (Landroidx/fragment/app/Fragment;)V
    //   2151: aload_1
    //   2152: invokevirtual performDestroy : ()V
    //   2155: aload_0
    //   2156: aload_1
    //   2157: iconst_0
    //   2158: invokevirtual dispatchOnFragmentDestroyed : (Landroidx/fragment/app/Fragment;Z)V
    //   2161: aload_1
    //   2162: invokevirtual performDetach : ()V
    //   2165: aload_0
    //   2166: aload_1
    //   2167: iconst_0
    //   2168: invokevirtual dispatchOnFragmentDetached : (Landroidx/fragment/app/Fragment;Z)V
    //   2171: iload_2
    //   2172: istore #6
    //   2174: iload #5
    //   2176: ifne -> 2291
    //   2179: iload_3
    //   2180: ifne -> 2269
    //   2183: aload_0
    //   2184: getfield mNonConfig : Landroidx/fragment/app/FragmentManagerViewModel;
    //   2187: aload_1
    //   2188: invokevirtual shouldDestroy : (Landroidx/fragment/app/Fragment;)Z
    //   2191: ifeq -> 2197
    //   2194: goto -> 2269
    //   2197: aload_1
    //   2198: aconst_null
    //   2199: putfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   2202: aload_1
    //   2203: aconst_null
    //   2204: putfield mParentFragment : Landroidx/fragment/app/Fragment;
    //   2207: aload_1
    //   2208: aconst_null
    //   2209: putfield mFragmentManager : Landroidx/fragment/app/FragmentManagerImpl;
    //   2212: iload_2
    //   2213: istore #6
    //   2215: aload_1
    //   2216: getfield mTargetWho : Ljava/lang/String;
    //   2219: ifnull -> 2291
    //   2222: aload_0
    //   2223: getfield mActive : Ljava/util/HashMap;
    //   2226: aload_1
    //   2227: getfield mTargetWho : Ljava/lang/String;
    //   2230: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   2233: checkcast androidx/fragment/app/Fragment
    //   2236: astore #11
    //   2238: iload_2
    //   2239: istore #6
    //   2241: aload #11
    //   2243: ifnull -> 2291
    //   2246: iload_2
    //   2247: istore #6
    //   2249: aload #11
    //   2251: invokevirtual getRetainInstance : ()Z
    //   2254: ifeq -> 2291
    //   2257: aload_1
    //   2258: aload #11
    //   2260: putfield mTarget : Landroidx/fragment/app/Fragment;
    //   2263: iload_2
    //   2264: istore #6
    //   2266: goto -> 2291
    //   2269: aload_0
    //   2270: aload_1
    //   2271: invokevirtual makeInactive : (Landroidx/fragment/app/Fragment;)V
    //   2274: iload_2
    //   2275: istore #6
    //   2277: goto -> 2291
    //   2280: aload_1
    //   2281: iload_2
    //   2282: invokevirtual setStateAfterAnimating : (I)V
    //   2285: iload #7
    //   2287: istore_2
    //   2288: goto -> 2294
    //   2291: iload #6
    //   2293: istore_2
    //   2294: aload_1
    //   2295: getfield mState : I
    //   2298: iload_2
    //   2299: if_icmpeq -> 2378
    //   2302: new java/lang/StringBuilder
    //   2305: dup
    //   2306: invokespecial <init> : ()V
    //   2309: astore #11
    //   2311: aload #11
    //   2313: ldc_w 'moveToState: Fragment state for '
    //   2316: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2319: pop
    //   2320: aload #11
    //   2322: aload_1
    //   2323: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2326: pop
    //   2327: aload #11
    //   2329: ldc_w ' not updated inline; expected state '
    //   2332: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2335: pop
    //   2336: aload #11
    //   2338: iload_2
    //   2339: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2342: pop
    //   2343: aload #11
    //   2345: ldc_w ' found '
    //   2348: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2351: pop
    //   2352: aload #11
    //   2354: aload_1
    //   2355: getfield mState : I
    //   2358: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2361: pop
    //   2362: ldc 'FragmentManager'
    //   2364: aload #11
    //   2366: invokevirtual toString : ()Ljava/lang/String;
    //   2369: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   2372: pop
    //   2373: aload_1
    //   2374: iload_2
    //   2375: putfield mState : I
    //   2378: return
    // Exception table:
    //   from	to	target	type
    //   1053	1066	1069	android/content/res/Resources$NotFoundException
  }
  
  public void noteStateNotSaved() {
    byte b = 0;
    this.mStateSaved = false;
    this.mStopped = false;
    int i = this.mAdded.size();
    while (b < i) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.noteStateNotSaved(); 
      b++;
    } 
  }
  
  public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    boolean bool = "fragment".equals(paramString);
    paramString = null;
    if (!bool)
      return null; 
    String str2 = paramAttributeSet.getAttributeValue(null, "class");
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, FragmentTag.Fragment);
    int i = 0;
    String str1 = str2;
    if (str2 == null)
      str1 = typedArray.getString(0); 
    int j = typedArray.getResourceId(1, -1);
    str2 = typedArray.getString(2);
    typedArray.recycle();
    if (str1 == null || !FragmentFactory.isFragmentClass(paramContext.getClassLoader(), str1))
      return null; 
    if (paramView != null)
      i = paramView.getId(); 
    if (i != -1 || j != -1 || str2 != null) {
      if (j != -1)
        fragment2 = findFragmentById(j); 
      Fragment fragment1 = fragment2;
      if (fragment2 == null) {
        fragment1 = fragment2;
        if (str2 != null)
          fragment1 = findFragmentByTag(str2); 
      } 
      Fragment fragment2 = fragment1;
      if (fragment1 == null) {
        fragment2 = fragment1;
        if (i != -1)
          fragment2 = findFragmentById(i); 
      } 
      if (DEBUG) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("onCreateView: id=0x");
        stringBuilder2.append(Integer.toHexString(j));
        stringBuilder2.append(" fname=");
        stringBuilder2.append(str1);
        stringBuilder2.append(" existing=");
        stringBuilder2.append(fragment2);
        Log.v("FragmentManager", stringBuilder2.toString());
      } 
      if (fragment2 == null) {
        int k;
        fragment2 = getFragmentFactory().instantiate(paramContext.getClassLoader(), str1);
        fragment2.mFromLayout = true;
        if (j != 0) {
          k = j;
        } else {
          k = i;
        } 
        fragment2.mFragmentId = k;
        fragment2.mContainerId = i;
        fragment2.mTag = str2;
        fragment2.mInLayout = true;
        fragment2.mFragmentManager = this;
        fragment2.mHost = this.mHost;
        fragment2.onInflate(this.mHost.getContext(), paramAttributeSet, fragment2.mSavedFragmentState);
        addFragment(fragment2, true);
      } else if (!fragment2.mInLayout) {
        fragment2.mInLayout = true;
        fragment2.mHost = this.mHost;
        fragment2.onInflate(this.mHost.getContext(), paramAttributeSet, fragment2.mSavedFragmentState);
      } else {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(paramAttributeSet.getPositionDescription());
        stringBuilder2.append(": Duplicate id 0x");
        stringBuilder2.append(Integer.toHexString(j));
        stringBuilder2.append(", tag ");
        stringBuilder2.append(str2);
        stringBuilder2.append(", or parent id 0x");
        stringBuilder2.append(Integer.toHexString(i));
        stringBuilder2.append(" with another fragment for ");
        stringBuilder2.append(str1);
        throw new IllegalArgumentException(stringBuilder2.toString());
      } 
      if (this.mCurState < 1 && fragment2.mFromLayout) {
        moveToState(fragment2, 1, 0, 0, false);
      } else {
        moveToState(fragment2);
      } 
      if (fragment2.mView != null) {
        if (j != 0)
          fragment2.mView.setId(j); 
        if (fragment2.mView.getTag() == null)
          fragment2.mView.setTag(str2); 
        return fragment2.mView;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Fragment ");
      stringBuilder1.append(str1);
      stringBuilder1.append(" did not create a view.");
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramAttributeSet.getPositionDescription());
    stringBuilder.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
    stringBuilder.append(str1);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return onCreateView((View)null, paramString, paramContext, paramAttributeSet);
  }
  
  public void performPendingDeferredStart(Fragment paramFragment) {
    if (paramFragment.mDeferStart) {
      if (this.mExecutingActions) {
        this.mHavePendingDeferredStart = true;
        return;
      } 
      paramFragment.mDeferStart = false;
      moveToState(paramFragment, this.mCurState, 0, 0, false);
    } 
  }
  
  public void popBackStack() {
    enqueueAction(new PopBackStackState(null, -1, 0), false);
  }
  
  public void popBackStack(int paramInt1, int paramInt2) {
    if (paramInt1 >= 0) {
      enqueueAction(new PopBackStackState(null, paramInt1, paramInt2), false);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Bad id: ");
    stringBuilder.append(paramInt1);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void popBackStack(String paramString, int paramInt) {
    enqueueAction(new PopBackStackState(paramString, -1, paramInt), false);
  }
  
  public boolean popBackStackImmediate() {
    checkStateLoss();
    return popBackStackImmediate((String)null, -1, 0);
  }
  
  public boolean popBackStackImmediate(int paramInt1, int paramInt2) {
    checkStateLoss();
    execPendingActions();
    if (paramInt1 >= 0)
      return popBackStackImmediate((String)null, paramInt1, paramInt2); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Bad id: ");
    stringBuilder.append(paramInt1);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean popBackStackImmediate(String paramString, int paramInt) {
    checkStateLoss();
    return popBackStackImmediate(paramString, -1, paramInt);
  }
  
  boolean popBackStackState(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, String paramString, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mBackStack : Ljava/util/ArrayList;
    //   4: astore #8
    //   6: aload #8
    //   8: ifnonnull -> 13
    //   11: iconst_0
    //   12: ireturn
    //   13: aload_3
    //   14: ifnonnull -> 71
    //   17: iload #4
    //   19: ifge -> 71
    //   22: iload #5
    //   24: iconst_1
    //   25: iand
    //   26: ifne -> 71
    //   29: aload #8
    //   31: invokevirtual size : ()I
    //   34: iconst_1
    //   35: isub
    //   36: istore #4
    //   38: iload #4
    //   40: ifge -> 45
    //   43: iconst_0
    //   44: ireturn
    //   45: aload_1
    //   46: aload_0
    //   47: getfield mBackStack : Ljava/util/ArrayList;
    //   50: iload #4
    //   52: invokevirtual remove : (I)Ljava/lang/Object;
    //   55: invokevirtual add : (Ljava/lang/Object;)Z
    //   58: pop
    //   59: aload_2
    //   60: iconst_1
    //   61: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   64: invokevirtual add : (Ljava/lang/Object;)Z
    //   67: pop
    //   68: goto -> 326
    //   71: aload_3
    //   72: ifnonnull -> 89
    //   75: iload #4
    //   77: iflt -> 83
    //   80: goto -> 89
    //   83: iconst_m1
    //   84: istore #4
    //   86: goto -> 263
    //   89: aload_0
    //   90: getfield mBackStack : Ljava/util/ArrayList;
    //   93: invokevirtual size : ()I
    //   96: iconst_1
    //   97: isub
    //   98: istore #6
    //   100: iload #6
    //   102: iflt -> 162
    //   105: aload_0
    //   106: getfield mBackStack : Ljava/util/ArrayList;
    //   109: iload #6
    //   111: invokevirtual get : (I)Ljava/lang/Object;
    //   114: checkcast androidx/fragment/app/BackStackRecord
    //   117: astore #8
    //   119: aload_3
    //   120: ifnull -> 138
    //   123: aload_3
    //   124: aload #8
    //   126: invokevirtual getName : ()Ljava/lang/String;
    //   129: invokevirtual equals : (Ljava/lang/Object;)Z
    //   132: ifeq -> 138
    //   135: goto -> 162
    //   138: iload #4
    //   140: iflt -> 156
    //   143: iload #4
    //   145: aload #8
    //   147: getfield mIndex : I
    //   150: if_icmpne -> 156
    //   153: goto -> 162
    //   156: iinc #6, -1
    //   159: goto -> 100
    //   162: iload #6
    //   164: ifge -> 169
    //   167: iconst_0
    //   168: ireturn
    //   169: iload #6
    //   171: istore #7
    //   173: iload #5
    //   175: iconst_1
    //   176: iand
    //   177: ifeq -> 259
    //   180: iload #6
    //   182: iconst_1
    //   183: isub
    //   184: istore #5
    //   186: iload #5
    //   188: istore #7
    //   190: iload #5
    //   192: iflt -> 259
    //   195: aload_0
    //   196: getfield mBackStack : Ljava/util/ArrayList;
    //   199: iload #5
    //   201: invokevirtual get : (I)Ljava/lang/Object;
    //   204: checkcast androidx/fragment/app/BackStackRecord
    //   207: astore #8
    //   209: aload_3
    //   210: ifnull -> 229
    //   213: iload #5
    //   215: istore #6
    //   217: aload_3
    //   218: aload #8
    //   220: invokevirtual getName : ()Ljava/lang/String;
    //   223: invokevirtual equals : (Ljava/lang/Object;)Z
    //   226: ifne -> 180
    //   229: iload #5
    //   231: istore #7
    //   233: iload #4
    //   235: iflt -> 259
    //   238: iload #5
    //   240: istore #7
    //   242: iload #4
    //   244: aload #8
    //   246: getfield mIndex : I
    //   249: if_icmpne -> 259
    //   252: iload #5
    //   254: istore #6
    //   256: goto -> 180
    //   259: iload #7
    //   261: istore #4
    //   263: iload #4
    //   265: aload_0
    //   266: getfield mBackStack : Ljava/util/ArrayList;
    //   269: invokevirtual size : ()I
    //   272: iconst_1
    //   273: isub
    //   274: if_icmpne -> 279
    //   277: iconst_0
    //   278: ireturn
    //   279: aload_0
    //   280: getfield mBackStack : Ljava/util/ArrayList;
    //   283: invokevirtual size : ()I
    //   286: iconst_1
    //   287: isub
    //   288: istore #5
    //   290: iload #5
    //   292: iload #4
    //   294: if_icmple -> 326
    //   297: aload_1
    //   298: aload_0
    //   299: getfield mBackStack : Ljava/util/ArrayList;
    //   302: iload #5
    //   304: invokevirtual remove : (I)Ljava/lang/Object;
    //   307: invokevirtual add : (Ljava/lang/Object;)Z
    //   310: pop
    //   311: aload_2
    //   312: iconst_1
    //   313: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   316: invokevirtual add : (Ljava/lang/Object;)Z
    //   319: pop
    //   320: iinc #5, -1
    //   323: goto -> 290
    //   326: iconst_1
    //   327: ireturn
  }
  
  public void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment) {
    if (paramFragment.mFragmentManager != this) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Fragment ");
      stringBuilder.append(paramFragment);
      stringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(stringBuilder.toString()));
    } 
    paramBundle.putString(paramString, paramFragment.mWho);
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean) {
    this.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(paramFragmentLifecycleCallbacks, paramBoolean));
  }
  
  public void removeFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("remove: ");
      stringBuilder.append(paramFragment);
      stringBuilder.append(" nesting=");
      stringBuilder.append(paramFragment.mBackStackNesting);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    boolean bool = paramFragment.isInBackStack();
    if (!paramFragment.mDetached || (bool ^ true) != 0)
      synchronized (this.mAdded) {
        this.mAdded.remove(paramFragment);
        if (isMenuAvailable(paramFragment))
          this.mNeedMenuInvalidate = true; 
        paramFragment.mAdded = false;
        paramFragment.mRemoving = true;
        return;
      }  
  }
  
  public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener) {
    ArrayList<FragmentManager.OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
    if (arrayList != null)
      arrayList.remove(paramOnBackStackChangedListener); 
  }
  
  void removeRetainedFragment(Fragment paramFragment) {
    if (isStateSaved()) {
      if (DEBUG)
        Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved"); 
      return;
    } 
    if (this.mNonConfig.removeRetainedFragment(paramFragment) && DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Updating retained Fragments: Removed ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
  }
  
  void reportBackStackChanged() {
    if (this.mBackStackChangeListeners != null)
      for (byte b = 0; b < this.mBackStackChangeListeners.size(); b++)
        ((FragmentManager.OnBackStackChangedListener)this.mBackStackChangeListeners.get(b)).onBackStackChanged();  
  }
  
  void restoreAllState(Parcelable paramParcelable, FragmentManagerNonConfig paramFragmentManagerNonConfig) {
    if (this.mHost instanceof ViewModelStoreOwner)
      throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner")); 
    this.mNonConfig.restoreFromSnapshot(paramFragmentManagerNonConfig);
    restoreSaveState(paramParcelable);
  }
  
  void restoreSaveState(Parcelable paramParcelable) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: aload_1
    //   6: checkcast androidx/fragment/app/FragmentManagerState
    //   9: astore #4
    //   11: aload #4
    //   13: getfield mActive : Ljava/util/ArrayList;
    //   16: ifnonnull -> 20
    //   19: return
    //   20: aload_0
    //   21: getfield mNonConfig : Landroidx/fragment/app/FragmentManagerViewModel;
    //   24: invokevirtual getRetainedFragments : ()Ljava/util/Collection;
    //   27: invokeinterface iterator : ()Ljava/util/Iterator;
    //   32: astore #6
    //   34: aload #6
    //   36: invokeinterface hasNext : ()Z
    //   41: ifeq -> 347
    //   44: aload #6
    //   46: invokeinterface next : ()Ljava/lang/Object;
    //   51: checkcast androidx/fragment/app/Fragment
    //   54: astore #5
    //   56: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   59: ifeq -> 95
    //   62: new java/lang/StringBuilder
    //   65: dup
    //   66: invokespecial <init> : ()V
    //   69: astore_1
    //   70: aload_1
    //   71: ldc_w 'restoreSaveState: re-attaching retained '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: aload_1
    //   79: aload #5
    //   81: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: ldc 'FragmentManager'
    //   87: aload_1
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   94: pop
    //   95: aload #4
    //   97: getfield mActive : Ljava/util/ArrayList;
    //   100: invokevirtual iterator : ()Ljava/util/Iterator;
    //   103: astore_3
    //   104: aload_3
    //   105: invokeinterface hasNext : ()Z
    //   110: ifeq -> 141
    //   113: aload_3
    //   114: invokeinterface next : ()Ljava/lang/Object;
    //   119: checkcast androidx/fragment/app/FragmentState
    //   122: astore_1
    //   123: aload_1
    //   124: getfield mWho : Ljava/lang/String;
    //   127: aload #5
    //   129: getfield mWho : Ljava/lang/String;
    //   132: invokevirtual equals : (Ljava/lang/Object;)Z
    //   135: ifeq -> 104
    //   138: goto -> 143
    //   141: aconst_null
    //   142: astore_1
    //   143: aload_1
    //   144: ifnonnull -> 233
    //   147: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   150: ifeq -> 204
    //   153: new java/lang/StringBuilder
    //   156: dup
    //   157: invokespecial <init> : ()V
    //   160: astore_1
    //   161: aload_1
    //   162: ldc_w 'Discarding retained Fragment '
    //   165: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: pop
    //   169: aload_1
    //   170: aload #5
    //   172: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   175: pop
    //   176: aload_1
    //   177: ldc_w ' that was not found in the set of active Fragments '
    //   180: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   183: pop
    //   184: aload_1
    //   185: aload #4
    //   187: getfield mActive : Ljava/util/ArrayList;
    //   190: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   193: pop
    //   194: ldc 'FragmentManager'
    //   196: aload_1
    //   197: invokevirtual toString : ()Ljava/lang/String;
    //   200: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   203: pop
    //   204: aload_0
    //   205: aload #5
    //   207: iconst_1
    //   208: iconst_0
    //   209: iconst_0
    //   210: iconst_0
    //   211: invokevirtual moveToState : (Landroidx/fragment/app/Fragment;IIIZ)V
    //   214: aload #5
    //   216: iconst_1
    //   217: putfield mRemoving : Z
    //   220: aload_0
    //   221: aload #5
    //   223: iconst_0
    //   224: iconst_0
    //   225: iconst_0
    //   226: iconst_0
    //   227: invokevirtual moveToState : (Landroidx/fragment/app/Fragment;IIIZ)V
    //   230: goto -> 34
    //   233: aload_1
    //   234: aload #5
    //   236: putfield mInstance : Landroidx/fragment/app/Fragment;
    //   239: aload #5
    //   241: aconst_null
    //   242: putfield mSavedViewState : Landroid/util/SparseArray;
    //   245: aload #5
    //   247: iconst_0
    //   248: putfield mBackStackNesting : I
    //   251: aload #5
    //   253: iconst_0
    //   254: putfield mInLayout : Z
    //   257: aload #5
    //   259: iconst_0
    //   260: putfield mAdded : Z
    //   263: aload #5
    //   265: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   268: ifnull -> 283
    //   271: aload #5
    //   273: getfield mTarget : Landroidx/fragment/app/Fragment;
    //   276: getfield mWho : Ljava/lang/String;
    //   279: astore_3
    //   280: goto -> 285
    //   283: aconst_null
    //   284: astore_3
    //   285: aload #5
    //   287: aload_3
    //   288: putfield mTargetWho : Ljava/lang/String;
    //   291: aload #5
    //   293: aconst_null
    //   294: putfield mTarget : Landroidx/fragment/app/Fragment;
    //   297: aload_1
    //   298: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   301: ifnull -> 34
    //   304: aload_1
    //   305: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   308: aload_0
    //   309: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   312: invokevirtual getContext : ()Landroid/content/Context;
    //   315: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   318: invokevirtual setClassLoader : (Ljava/lang/ClassLoader;)V
    //   321: aload #5
    //   323: aload_1
    //   324: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   327: ldc 'android:view_state'
    //   329: invokevirtual getSparseParcelableArray : (Ljava/lang/String;)Landroid/util/SparseArray;
    //   332: putfield mSavedViewState : Landroid/util/SparseArray;
    //   335: aload #5
    //   337: aload_1
    //   338: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   341: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   344: goto -> 34
    //   347: aload_0
    //   348: getfield mActive : Ljava/util/HashMap;
    //   351: invokevirtual clear : ()V
    //   354: aload #4
    //   356: getfield mActive : Ljava/util/ArrayList;
    //   359: invokevirtual iterator : ()Ljava/util/Iterator;
    //   362: astore #6
    //   364: aload #6
    //   366: invokeinterface hasNext : ()Z
    //   371: ifeq -> 493
    //   374: aload #6
    //   376: invokeinterface next : ()Ljava/lang/Object;
    //   381: checkcast androidx/fragment/app/FragmentState
    //   384: astore #5
    //   386: aload #5
    //   388: ifnull -> 364
    //   391: aload #5
    //   393: aload_0
    //   394: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   397: invokevirtual getContext : ()Landroid/content/Context;
    //   400: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   403: aload_0
    //   404: invokevirtual getFragmentFactory : ()Landroidx/fragment/app/FragmentFactory;
    //   407: invokevirtual instantiate : (Ljava/lang/ClassLoader;Landroidx/fragment/app/FragmentFactory;)Landroidx/fragment/app/Fragment;
    //   410: astore_3
    //   411: aload_3
    //   412: aload_0
    //   413: putfield mFragmentManager : Landroidx/fragment/app/FragmentManagerImpl;
    //   416: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   419: ifeq -> 471
    //   422: new java/lang/StringBuilder
    //   425: dup
    //   426: invokespecial <init> : ()V
    //   429: astore_1
    //   430: aload_1
    //   431: ldc_w 'restoreSaveState: active ('
    //   434: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   437: pop
    //   438: aload_1
    //   439: aload_3
    //   440: getfield mWho : Ljava/lang/String;
    //   443: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   446: pop
    //   447: aload_1
    //   448: ldc_w '): '
    //   451: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   454: pop
    //   455: aload_1
    //   456: aload_3
    //   457: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   460: pop
    //   461: ldc 'FragmentManager'
    //   463: aload_1
    //   464: invokevirtual toString : ()Ljava/lang/String;
    //   467: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   470: pop
    //   471: aload_0
    //   472: getfield mActive : Ljava/util/HashMap;
    //   475: aload_3
    //   476: getfield mWho : Ljava/lang/String;
    //   479: aload_3
    //   480: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   483: pop
    //   484: aload #5
    //   486: aconst_null
    //   487: putfield mInstance : Landroidx/fragment/app/Fragment;
    //   490: goto -> 364
    //   493: aload_0
    //   494: getfield mAdded : Ljava/util/ArrayList;
    //   497: invokevirtual clear : ()V
    //   500: aload #4
    //   502: getfield mAdded : Ljava/util/ArrayList;
    //   505: ifnull -> 744
    //   508: aload #4
    //   510: getfield mAdded : Ljava/util/ArrayList;
    //   513: invokevirtual iterator : ()Ljava/util/Iterator;
    //   516: astore_3
    //   517: aload_3
    //   518: invokeinterface hasNext : ()Z
    //   523: ifeq -> 744
    //   526: aload_3
    //   527: invokeinterface next : ()Ljava/lang/Object;
    //   532: checkcast java/lang/String
    //   535: astore #5
    //   537: aload_0
    //   538: getfield mActive : Ljava/util/HashMap;
    //   541: aload #5
    //   543: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   546: checkcast androidx/fragment/app/Fragment
    //   549: astore_1
    //   550: aload_1
    //   551: ifnonnull -> 605
    //   554: new java/lang/StringBuilder
    //   557: dup
    //   558: invokespecial <init> : ()V
    //   561: astore #6
    //   563: aload #6
    //   565: ldc_w 'No instantiated fragment for ('
    //   568: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   571: pop
    //   572: aload #6
    //   574: aload #5
    //   576: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   579: pop
    //   580: aload #6
    //   582: ldc_w ')'
    //   585: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   588: pop
    //   589: aload_0
    //   590: new java/lang/IllegalStateException
    //   593: dup
    //   594: aload #6
    //   596: invokevirtual toString : ()Ljava/lang/String;
    //   599: invokespecial <init> : (Ljava/lang/String;)V
    //   602: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   605: aload_1
    //   606: iconst_1
    //   607: putfield mAdded : Z
    //   610: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   613: ifeq -> 669
    //   616: new java/lang/StringBuilder
    //   619: dup
    //   620: invokespecial <init> : ()V
    //   623: astore #6
    //   625: aload #6
    //   627: ldc_w 'restoreSaveState: added ('
    //   630: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   633: pop
    //   634: aload #6
    //   636: aload #5
    //   638: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   641: pop
    //   642: aload #6
    //   644: ldc_w '): '
    //   647: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   650: pop
    //   651: aload #6
    //   653: aload_1
    //   654: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   657: pop
    //   658: ldc 'FragmentManager'
    //   660: aload #6
    //   662: invokevirtual toString : ()Ljava/lang/String;
    //   665: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   668: pop
    //   669: aload_0
    //   670: getfield mAdded : Ljava/util/ArrayList;
    //   673: aload_1
    //   674: invokevirtual contains : (Ljava/lang/Object;)Z
    //   677: ifne -> 710
    //   680: aload_0
    //   681: getfield mAdded : Ljava/util/ArrayList;
    //   684: astore #5
    //   686: aload #5
    //   688: monitorenter
    //   689: aload_0
    //   690: getfield mAdded : Ljava/util/ArrayList;
    //   693: aload_1
    //   694: invokevirtual add : (Ljava/lang/Object;)Z
    //   697: pop
    //   698: aload #5
    //   700: monitorexit
    //   701: goto -> 517
    //   704: astore_1
    //   705: aload #5
    //   707: monitorexit
    //   708: aload_1
    //   709: athrow
    //   710: new java/lang/StringBuilder
    //   713: dup
    //   714: invokespecial <init> : ()V
    //   717: astore_3
    //   718: aload_3
    //   719: ldc_w 'Already added '
    //   722: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   725: pop
    //   726: aload_3
    //   727: aload_1
    //   728: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   731: pop
    //   732: new java/lang/IllegalStateException
    //   735: dup
    //   736: aload_3
    //   737: invokevirtual toString : ()Ljava/lang/String;
    //   740: invokespecial <init> : (Ljava/lang/String;)V
    //   743: athrow
    //   744: aload #4
    //   746: getfield mBackStack : [Landroidx/fragment/app/BackStackState;
    //   749: ifnull -> 923
    //   752: aload_0
    //   753: new java/util/ArrayList
    //   756: dup
    //   757: aload #4
    //   759: getfield mBackStack : [Landroidx/fragment/app/BackStackState;
    //   762: arraylength
    //   763: invokespecial <init> : (I)V
    //   766: putfield mBackStack : Ljava/util/ArrayList;
    //   769: iconst_0
    //   770: istore_2
    //   771: iload_2
    //   772: aload #4
    //   774: getfield mBackStack : [Landroidx/fragment/app/BackStackState;
    //   777: arraylength
    //   778: if_icmpge -> 928
    //   781: aload #4
    //   783: getfield mBackStack : [Landroidx/fragment/app/BackStackState;
    //   786: iload_2
    //   787: aaload
    //   788: aload_0
    //   789: invokevirtual instantiate : (Landroidx/fragment/app/FragmentManagerImpl;)Landroidx/fragment/app/BackStackRecord;
    //   792: astore_1
    //   793: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   796: ifeq -> 892
    //   799: new java/lang/StringBuilder
    //   802: dup
    //   803: invokespecial <init> : ()V
    //   806: astore_3
    //   807: aload_3
    //   808: ldc_w 'restoreAllState: back stack #'
    //   811: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   814: pop
    //   815: aload_3
    //   816: iload_2
    //   817: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   820: pop
    //   821: aload_3
    //   822: ldc_w ' (index '
    //   825: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   828: pop
    //   829: aload_3
    //   830: aload_1
    //   831: getfield mIndex : I
    //   834: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   837: pop
    //   838: aload_3
    //   839: ldc_w '): '
    //   842: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   845: pop
    //   846: aload_3
    //   847: aload_1
    //   848: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   851: pop
    //   852: ldc 'FragmentManager'
    //   854: aload_3
    //   855: invokevirtual toString : ()Ljava/lang/String;
    //   858: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   861: pop
    //   862: new java/io/PrintWriter
    //   865: dup
    //   866: new androidx/core/util/LogWriter
    //   869: dup
    //   870: ldc 'FragmentManager'
    //   872: invokespecial <init> : (Ljava/lang/String;)V
    //   875: invokespecial <init> : (Ljava/io/Writer;)V
    //   878: astore_3
    //   879: aload_1
    //   880: ldc_w '  '
    //   883: aload_3
    //   884: iconst_0
    //   885: invokevirtual dump : (Ljava/lang/String;Ljava/io/PrintWriter;Z)V
    //   888: aload_3
    //   889: invokevirtual close : ()V
    //   892: aload_0
    //   893: getfield mBackStack : Ljava/util/ArrayList;
    //   896: aload_1
    //   897: invokevirtual add : (Ljava/lang/Object;)Z
    //   900: pop
    //   901: aload_1
    //   902: getfield mIndex : I
    //   905: iflt -> 917
    //   908: aload_0
    //   909: aload_1
    //   910: getfield mIndex : I
    //   913: aload_1
    //   914: invokevirtual setBackStackIndex : (ILandroidx/fragment/app/BackStackRecord;)V
    //   917: iinc #2, 1
    //   920: goto -> 771
    //   923: aload_0
    //   924: aconst_null
    //   925: putfield mBackStack : Ljava/util/ArrayList;
    //   928: aload #4
    //   930: getfield mPrimaryNavActiveWho : Ljava/lang/String;
    //   933: ifnull -> 962
    //   936: aload_0
    //   937: getfield mActive : Ljava/util/HashMap;
    //   940: aload #4
    //   942: getfield mPrimaryNavActiveWho : Ljava/lang/String;
    //   945: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   948: checkcast androidx/fragment/app/Fragment
    //   951: astore_1
    //   952: aload_0
    //   953: aload_1
    //   954: putfield mPrimaryNav : Landroidx/fragment/app/Fragment;
    //   957: aload_0
    //   958: aload_1
    //   959: invokespecial dispatchParentPrimaryNavigationFragmentChanged : (Landroidx/fragment/app/Fragment;)V
    //   962: aload_0
    //   963: aload #4
    //   965: getfield mNextFragmentIndex : I
    //   968: putfield mNextFragmentIndex : I
    //   971: return
    // Exception table:
    //   from	to	target	type
    //   689	701	704	finally
    //   705	708	704	finally
  }
  
  @Deprecated
  FragmentManagerNonConfig retainNonConfig() {
    if (this.mHost instanceof ViewModelStoreOwner)
      throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner.")); 
    return this.mNonConfig.getSnapshot();
  }
  
  Parcelable saveAllState() {
    ArrayList arrayList;
    StringBuilder stringBuilder;
    forcePostponedTransactions();
    endAnimatingAwayFragments();
    execPendingActions();
    this.mStateSaved = true;
    boolean bool1 = this.mActive.isEmpty();
    Iterator<Fragment> iterator2 = null;
    if (bool1)
      return null; 
    ArrayList<FragmentState> arrayList1 = new ArrayList(this.mActive.size());
    Iterator<Fragment> iterator1 = this.mActive.values().iterator();
    boolean bool = false;
    int i = 0;
    while (iterator1.hasNext()) {
      arrayList = (ArrayList)iterator1.next();
      if (arrayList != null) {
        if (((Fragment)arrayList).mFragmentManager != this) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Failure saving state: active ");
          stringBuilder1.append(arrayList);
          stringBuilder1.append(" was removed from the FragmentManager");
          throwException(new IllegalStateException(stringBuilder1.toString()));
        } 
        FragmentState fragmentState = new FragmentState((Fragment)arrayList);
        arrayList1.add(fragmentState);
        if (((Fragment)arrayList).mState > 0 && fragmentState.mSavedFragmentState == null) {
          fragmentState.mSavedFragmentState = saveFragmentBasicState((Fragment)arrayList);
          if (((Fragment)arrayList).mTargetWho != null) {
            Fragment fragment1 = this.mActive.get(((Fragment)arrayList).mTargetWho);
            if (fragment1 == null) {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append("Failure saving state: ");
              stringBuilder1.append(arrayList);
              stringBuilder1.append(" has target not in fragment manager: ");
              stringBuilder1.append(((Fragment)arrayList).mTargetWho);
              throwException(new IllegalStateException(stringBuilder1.toString()));
            } 
            if (fragmentState.mSavedFragmentState == null)
              fragmentState.mSavedFragmentState = new Bundle(); 
            putFragment(fragmentState.mSavedFragmentState, "android:target_state", fragment1);
            if (((Fragment)arrayList).mTargetRequestCode != 0)
              fragmentState.mSavedFragmentState.putInt("android:target_req_state", ((Fragment)arrayList).mTargetRequestCode); 
          } 
        } else {
          fragmentState.mSavedFragmentState = ((Fragment)arrayList).mSavedFragmentState;
        } 
        if (DEBUG) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Saved state of ");
          stringBuilder1.append(arrayList);
          stringBuilder1.append(": ");
          stringBuilder1.append(fragmentState.mSavedFragmentState);
          Log.v("FragmentManager", stringBuilder1.toString());
        } 
        i = 1;
      } 
    } 
    if (!i) {
      if (DEBUG)
        Log.v("FragmentManager", "saveAllState: no fragments!"); 
      return null;
    } 
    i = this.mAdded.size();
    if (i > 0) {
      ArrayList<String> arrayList3 = new ArrayList(i);
      Iterator<Fragment> iterator = this.mAdded.iterator();
      while (true) {
        arrayList = arrayList3;
        if (iterator.hasNext()) {
          Fragment fragment1 = iterator.next();
          arrayList3.add(fragment1.mWho);
          if (fragment1.mFragmentManager != this) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("Failure saving state: active ");
            stringBuilder1.append(fragment1);
            stringBuilder1.append(" was removed from the FragmentManager");
            throwException(new IllegalStateException(stringBuilder1.toString()));
          } 
          if (DEBUG) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("saveAllState: adding fragment (");
            stringBuilder1.append(fragment1.mWho);
            stringBuilder1.append("): ");
            stringBuilder1.append(fragment1);
            Log.v("FragmentManager", stringBuilder1.toString());
          } 
          continue;
        } 
        break;
      } 
    } else {
      arrayList = null;
    } 
    ArrayList<BackStackRecord> arrayList2 = this.mBackStack;
    iterator1 = iterator2;
    if (arrayList2 != null) {
      int j = arrayList2.size();
      iterator1 = iterator2;
      if (j > 0) {
        BackStackState[] arrayOfBackStackState = new BackStackState[j];
        i = bool;
        while (true) {
          BackStackState[] arrayOfBackStackState1 = arrayOfBackStackState;
          if (i < j) {
            arrayOfBackStackState[i] = new BackStackState(this.mBackStack.get(i));
            if (DEBUG) {
              stringBuilder = new StringBuilder();
              stringBuilder.append("saveAllState: adding back stack #");
              stringBuilder.append(i);
              stringBuilder.append(": ");
              stringBuilder.append(this.mBackStack.get(i));
              Log.v("FragmentManager", stringBuilder.toString());
            } 
            i++;
            continue;
          } 
          break;
        } 
      } 
    } 
    FragmentManagerState fragmentManagerState = new FragmentManagerState();
    fragmentManagerState.mActive = arrayList1;
    fragmentManagerState.mAdded = arrayList;
    fragmentManagerState.mBackStack = (BackStackState[])stringBuilder;
    Fragment fragment = this.mPrimaryNav;
    if (fragment != null)
      fragmentManagerState.mPrimaryNavActiveWho = fragment.mWho; 
    fragmentManagerState.mNextFragmentIndex = this.mNextFragmentIndex;
    return fragmentManagerState;
  }
  
  Bundle saveFragmentBasicState(Fragment paramFragment) {
    if (this.mStateBundle == null)
      this.mStateBundle = new Bundle(); 
    paramFragment.performSaveInstanceState(this.mStateBundle);
    dispatchOnFragmentSaveInstanceState(paramFragment, this.mStateBundle, false);
    boolean bool = this.mStateBundle.isEmpty();
    Bundle bundle2 = null;
    if (!bool) {
      bundle2 = this.mStateBundle;
      this.mStateBundle = null;
    } 
    if (paramFragment.mView != null)
      saveFragmentViewState(paramFragment); 
    Bundle bundle1 = bundle2;
    if (paramFragment.mSavedViewState != null) {
      bundle1 = bundle2;
      if (bundle2 == null)
        bundle1 = new Bundle(); 
      bundle1.putSparseParcelableArray("android:view_state", paramFragment.mSavedViewState);
    } 
    bundle2 = bundle1;
    if (!paramFragment.mUserVisibleHint) {
      bundle2 = bundle1;
      if (bundle1 == null)
        bundle2 = new Bundle(); 
      bundle2.putBoolean("android:user_visible_hint", paramFragment.mUserVisibleHint);
    } 
    return bundle2;
  }
  
  public Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment) {
    if (paramFragment.mFragmentManager != this) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Fragment ");
      stringBuilder.append(paramFragment);
      stringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(stringBuilder.toString()));
    } 
    int i = paramFragment.mState;
    Fragment.SavedState savedState2 = null;
    Fragment.SavedState savedState1 = savedState2;
    if (i > 0) {
      Bundle bundle = saveFragmentBasicState(paramFragment);
      savedState1 = savedState2;
      if (bundle != null)
        savedState1 = new Fragment.SavedState(bundle); 
    } 
    return savedState1;
  }
  
  void saveFragmentViewState(Fragment paramFragment) {
    if (paramFragment.mInnerView == null)
      return; 
    SparseArray<Parcelable> sparseArray = this.mStateArray;
    if (sparseArray == null) {
      this.mStateArray = new SparseArray();
    } else {
      sparseArray.clear();
    } 
    paramFragment.mInnerView.saveHierarchyState(this.mStateArray);
    if (this.mStateArray.size() > 0) {
      paramFragment.mSavedViewState = this.mStateArray;
      this.mStateArray = null;
    } 
  }
  
  void scheduleCommit() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   6: astore #4
    //   8: iconst_0
    //   9: istore_3
    //   10: aload #4
    //   12: ifnull -> 30
    //   15: aload_0
    //   16: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   19: invokevirtual isEmpty : ()Z
    //   22: ifne -> 30
    //   25: iconst_1
    //   26: istore_1
    //   27: goto -> 32
    //   30: iconst_0
    //   31: istore_1
    //   32: iload_3
    //   33: istore_2
    //   34: aload_0
    //   35: getfield mPendingActions : Ljava/util/ArrayList;
    //   38: ifnull -> 56
    //   41: iload_3
    //   42: istore_2
    //   43: aload_0
    //   44: getfield mPendingActions : Ljava/util/ArrayList;
    //   47: invokevirtual size : ()I
    //   50: iconst_1
    //   51: if_icmpne -> 56
    //   54: iconst_1
    //   55: istore_2
    //   56: iload_1
    //   57: ifne -> 64
    //   60: iload_2
    //   61: ifeq -> 97
    //   64: aload_0
    //   65: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   68: invokevirtual getHandler : ()Landroid/os/Handler;
    //   71: aload_0
    //   72: getfield mExecCommit : Ljava/lang/Runnable;
    //   75: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
    //   78: aload_0
    //   79: getfield mHost : Landroidx/fragment/app/FragmentHostCallback;
    //   82: invokevirtual getHandler : ()Landroid/os/Handler;
    //   85: aload_0
    //   86: getfield mExecCommit : Ljava/lang/Runnable;
    //   89: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   92: pop
    //   93: aload_0
    //   94: invokespecial updateOnBackPressedCallbackEnabled : ()V
    //   97: aload_0
    //   98: monitorexit
    //   99: return
    //   100: astore #4
    //   102: aload_0
    //   103: monitorexit
    //   104: aload #4
    //   106: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	100	finally
    //   15	25	100	finally
    //   34	41	100	finally
    //   43	54	100	finally
    //   64	97	100	finally
    //   97	99	100	finally
    //   102	104	100	finally
  }
  
  public void setBackStackIndex(int paramInt, BackStackRecord paramBackStackRecord) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   6: ifnonnull -> 25
    //   9: new java/util/ArrayList
    //   12: astore #5
    //   14: aload #5
    //   16: invokespecial <init> : ()V
    //   19: aload_0
    //   20: aload #5
    //   22: putfield mBackStackIndices : Ljava/util/ArrayList;
    //   25: aload_0
    //   26: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   29: invokevirtual size : ()I
    //   32: istore #4
    //   34: iload #4
    //   36: istore_3
    //   37: iload_1
    //   38: iload #4
    //   40: if_icmpge -> 115
    //   43: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   46: ifeq -> 102
    //   49: new java/lang/StringBuilder
    //   52: astore #5
    //   54: aload #5
    //   56: invokespecial <init> : ()V
    //   59: aload #5
    //   61: ldc_w 'Setting back stack index '
    //   64: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: pop
    //   68: aload #5
    //   70: iload_1
    //   71: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload #5
    //   77: ldc_w ' to '
    //   80: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload #5
    //   86: aload_2
    //   87: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   90: pop
    //   91: ldc 'FragmentManager'
    //   93: aload #5
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   101: pop
    //   102: aload_0
    //   103: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   106: iload_1
    //   107: aload_2
    //   108: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   111: pop
    //   112: goto -> 281
    //   115: iload_3
    //   116: iload_1
    //   117: if_icmpge -> 213
    //   120: aload_0
    //   121: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   124: aconst_null
    //   125: invokevirtual add : (Ljava/lang/Object;)Z
    //   128: pop
    //   129: aload_0
    //   130: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   133: ifnonnull -> 152
    //   136: new java/util/ArrayList
    //   139: astore #5
    //   141: aload #5
    //   143: invokespecial <init> : ()V
    //   146: aload_0
    //   147: aload #5
    //   149: putfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   152: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   155: ifeq -> 195
    //   158: new java/lang/StringBuilder
    //   161: astore #5
    //   163: aload #5
    //   165: invokespecial <init> : ()V
    //   168: aload #5
    //   170: ldc_w 'Adding available back stack index '
    //   173: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   176: pop
    //   177: aload #5
    //   179: iload_3
    //   180: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   183: pop
    //   184: ldc 'FragmentManager'
    //   186: aload #5
    //   188: invokevirtual toString : ()Ljava/lang/String;
    //   191: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   194: pop
    //   195: aload_0
    //   196: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   199: iload_3
    //   200: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   203: invokevirtual add : (Ljava/lang/Object;)Z
    //   206: pop
    //   207: iinc #3, 1
    //   210: goto -> 115
    //   213: getstatic androidx/fragment/app/FragmentManagerImpl.DEBUG : Z
    //   216: ifeq -> 272
    //   219: new java/lang/StringBuilder
    //   222: astore #5
    //   224: aload #5
    //   226: invokespecial <init> : ()V
    //   229: aload #5
    //   231: ldc_w 'Adding back stack index '
    //   234: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   237: pop
    //   238: aload #5
    //   240: iload_1
    //   241: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   244: pop
    //   245: aload #5
    //   247: ldc_w ' with '
    //   250: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: pop
    //   254: aload #5
    //   256: aload_2
    //   257: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   260: pop
    //   261: ldc 'FragmentManager'
    //   263: aload #5
    //   265: invokevirtual toString : ()Ljava/lang/String;
    //   268: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   271: pop
    //   272: aload_0
    //   273: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   276: aload_2
    //   277: invokevirtual add : (Ljava/lang/Object;)Z
    //   280: pop
    //   281: aload_0
    //   282: monitorexit
    //   283: return
    //   284: astore_2
    //   285: aload_0
    //   286: monitorexit
    //   287: aload_2
    //   288: athrow
    // Exception table:
    //   from	to	target	type
    //   2	25	284	finally
    //   25	34	284	finally
    //   43	102	284	finally
    //   102	112	284	finally
    //   120	152	284	finally
    //   152	195	284	finally
    //   195	207	284	finally
    //   213	272	284	finally
    //   272	281	284	finally
    //   281	283	284	finally
    //   285	287	284	finally
  }
  
  public void setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState) {
    if (this.mActive.get(paramFragment.mWho) == paramFragment && (paramFragment.mHost == null || paramFragment.getFragmentManager() == this)) {
      paramFragment.mMaxState = paramState;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(paramFragment);
    stringBuilder.append(" is not an active fragment of FragmentManager ");
    stringBuilder.append(this);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setPrimaryNavigationFragment(Fragment paramFragment) {
    if (paramFragment == null || (this.mActive.get(paramFragment.mWho) == paramFragment && (paramFragment.mHost == null || paramFragment.getFragmentManager() == this))) {
      Fragment fragment = this.mPrimaryNav;
      this.mPrimaryNav = paramFragment;
      dispatchParentPrimaryNavigationFragmentChanged(fragment);
      dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(paramFragment);
    stringBuilder.append(" is not an active fragment of FragmentManager ");
    stringBuilder.append(this);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void showFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("show: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (paramFragment.mHidden) {
      paramFragment.mHidden = false;
      paramFragment.mHiddenChanged ^= 0x1;
    } 
  }
  
  void startPendingDeferredFragments() {
    for (Fragment fragment : this.mActive.values()) {
      if (fragment != null)
        performPendingDeferredStart(fragment); 
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("FragmentManager{");
    stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    stringBuilder.append(" in ");
    Fragment fragment = this.mParent;
    if (fragment != null) {
      DebugUtils.buildShortClassTag(fragment, stringBuilder);
    } else {
      DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
    } 
    stringBuilder.append("}}");
    return stringBuilder.toString();
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   4: astore #4
    //   6: aload #4
    //   8: monitorenter
    //   9: iconst_0
    //   10: istore_2
    //   11: aload_0
    //   12: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   15: invokevirtual size : ()I
    //   18: istore_3
    //   19: iload_2
    //   20: iload_3
    //   21: if_icmpge -> 60
    //   24: aload_0
    //   25: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   28: iload_2
    //   29: invokevirtual get : (I)Ljava/lang/Object;
    //   32: checkcast androidx/fragment/app/FragmentManagerImpl$FragmentLifecycleCallbacksHolder
    //   35: getfield mCallback : Landroidx/fragment/app/FragmentManager$FragmentLifecycleCallbacks;
    //   38: aload_1
    //   39: if_acmpne -> 54
    //   42: aload_0
    //   43: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   46: iload_2
    //   47: invokevirtual remove : (I)Ljava/lang/Object;
    //   50: pop
    //   51: goto -> 60
    //   54: iinc #2, 1
    //   57: goto -> 19
    //   60: aload #4
    //   62: monitorexit
    //   63: return
    //   64: astore_1
    //   65: aload #4
    //   67: monitorexit
    //   68: aload_1
    //   69: athrow
    // Exception table:
    //   from	to	target	type
    //   11	19	64	finally
    //   24	51	64	finally
    //   60	63	64	finally
    //   65	68	64	finally
  }
  
  private static class AnimationOrAnimator {
    public final Animation animation = null;
    
    public final Animator animator;
    
    AnimationOrAnimator(Animator param1Animator) {
      this.animator = param1Animator;
      if (param1Animator != null)
        return; 
      throw new IllegalStateException("Animator cannot be null");
    }
    
    AnimationOrAnimator(Animation param1Animation) {
      this.animator = null;
      if (param1Animation != null)
        return; 
      throw new IllegalStateException("Animation cannot be null");
    }
  }
  
  private static class EndViewTransitionAnimation extends AnimationSet implements Runnable {
    private boolean mAnimating = true;
    
    private final View mChild;
    
    private boolean mEnded;
    
    private final ViewGroup mParent;
    
    private boolean mTransitionEnded;
    
    EndViewTransitionAnimation(Animation param1Animation, ViewGroup param1ViewGroup, View param1View) {
      super(false);
      this.mParent = param1ViewGroup;
      this.mChild = param1View;
      addAnimation(param1Animation);
      this.mParent.post(this);
    }
    
    public boolean getTransformation(long param1Long, Transformation param1Transformation) {
      this.mAnimating = true;
      if (this.mEnded)
        return this.mTransitionEnded ^ true; 
      if (!super.getTransformation(param1Long, param1Transformation)) {
        this.mEnded = true;
        OneShotPreDrawListener.add((View)this.mParent, this);
      } 
      return true;
    }
    
    public boolean getTransformation(long param1Long, Transformation param1Transformation, float param1Float) {
      this.mAnimating = true;
      if (this.mEnded)
        return this.mTransitionEnded ^ true; 
      if (!super.getTransformation(param1Long, param1Transformation, param1Float)) {
        this.mEnded = true;
        OneShotPreDrawListener.add((View)this.mParent, this);
      } 
      return true;
    }
    
    public void run() {
      if (!this.mEnded && this.mAnimating) {
        this.mAnimating = false;
        this.mParent.post(this);
      } else {
        this.mParent.endViewTransition(this.mChild);
        this.mTransitionEnded = true;
      } 
    }
  }
  
  private static final class FragmentLifecycleCallbacksHolder {
    final FragmentManager.FragmentLifecycleCallbacks mCallback;
    
    final boolean mRecursive;
    
    FragmentLifecycleCallbacksHolder(FragmentManager.FragmentLifecycleCallbacks param1FragmentLifecycleCallbacks, boolean param1Boolean) {
      this.mCallback = param1FragmentLifecycleCallbacks;
      this.mRecursive = param1Boolean;
    }
  }
  
  static class FragmentTag {
    public static final int[] Fragment = new int[] { 16842755, 16842960, 16842961 };
    
    public static final int Fragment_id = 1;
    
    public static final int Fragment_name = 0;
    
    public static final int Fragment_tag = 2;
  }
  
  static interface OpGenerator {
    boolean generateOps(ArrayList<BackStackRecord> param1ArrayList, ArrayList<Boolean> param1ArrayList1);
  }
  
  private class PopBackStackState implements OpGenerator {
    final int mFlags;
    
    final int mId;
    
    final String mName;
    
    final FragmentManagerImpl this$0;
    
    PopBackStackState(String param1String, int param1Int1, int param1Int2) {
      this.mName = param1String;
      this.mId = param1Int1;
      this.mFlags = param1Int2;
    }
    
    public boolean generateOps(ArrayList<BackStackRecord> param1ArrayList, ArrayList<Boolean> param1ArrayList1) {
      return (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && FragmentManagerImpl.this.mPrimaryNav.getChildFragmentManager().popBackStackImmediate()) ? false : FragmentManagerImpl.this.popBackStackState(param1ArrayList, param1ArrayList1, this.mName, this.mId, this.mFlags);
    }
  }
  
  static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
    final boolean mIsBack;
    
    private int mNumPostponed;
    
    final BackStackRecord mRecord;
    
    StartEnterTransitionListener(BackStackRecord param1BackStackRecord, boolean param1Boolean) {
      this.mIsBack = param1Boolean;
      this.mRecord = param1BackStackRecord;
    }
    
    public void cancelTransaction() {
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
    }
    
    public void completeTransaction() {
      int i = this.mNumPostponed;
      byte b = 0;
      if (i > 0) {
        i = 1;
      } else {
        i = 0;
      } 
      FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
      int j = fragmentManagerImpl.mAdded.size();
      while (b < j) {
        Fragment fragment = fragmentManagerImpl.mAdded.get(b);
        fragment.setOnStartEnterTransitionListener(null);
        if (i != 0 && fragment.isPostponed())
          fragment.startPostponedEnterTransition(); 
        b++;
      } 
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, i ^ 0x1, true);
    }
    
    public boolean isReady() {
      boolean bool;
      if (this.mNumPostponed == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onStartEnterTransition() {
      int i = this.mNumPostponed - 1;
      this.mNumPostponed = i;
      if (i != 0)
        return; 
      this.mRecord.mManager.scheduleCommit();
    }
    
    public void startListening() {
      this.mNumPostponed++;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */