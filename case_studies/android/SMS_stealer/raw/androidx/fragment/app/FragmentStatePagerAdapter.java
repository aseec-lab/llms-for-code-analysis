package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

public abstract class FragmentStatePagerAdapter extends PagerAdapter {
  public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
  
  @Deprecated
  public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
  
  private static final boolean DEBUG = false;
  
  private static final String TAG = "FragmentStatePagerAdapt";
  
  private final int mBehavior;
  
  private FragmentTransaction mCurTransaction = null;
  
  private Fragment mCurrentPrimaryItem = null;
  
  private final FragmentManager mFragmentManager;
  
  private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
  
  private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<Fragment.SavedState>();
  
  @Deprecated
  public FragmentStatePagerAdapter(FragmentManager paramFragmentManager) {
    this(paramFragmentManager, 0);
  }
  
  public FragmentStatePagerAdapter(FragmentManager paramFragmentManager, int paramInt) {
    this.mFragmentManager = paramFragmentManager;
    this.mBehavior = paramInt;
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
    paramObject = paramObject;
    if (this.mCurTransaction == null)
      this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
    while (this.mSavedState.size() <= paramInt)
      this.mSavedState.add(null); 
    ArrayList<Fragment.SavedState> arrayList = this.mSavedState;
    if (paramObject.isAdded()) {
      Fragment.SavedState savedState = this.mFragmentManager.saveFragmentInstanceState((Fragment)paramObject);
    } else {
      paramViewGroup = null;
    } 
    arrayList.set(paramInt, paramViewGroup);
    this.mFragments.set(paramInt, null);
    this.mCurTransaction.remove((Fragment)paramObject);
    if (paramObject == this.mCurrentPrimaryItem)
      this.mCurrentPrimaryItem = null; 
  }
  
  public void finishUpdate(ViewGroup paramViewGroup) {
    FragmentTransaction fragmentTransaction = this.mCurTransaction;
    if (fragmentTransaction != null) {
      fragmentTransaction.commitNowAllowingStateLoss();
      this.mCurTransaction = null;
    } 
  }
  
  public abstract Fragment getItem(int paramInt);
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
    if (this.mFragments.size() > paramInt) {
      Fragment fragment1 = this.mFragments.get(paramInt);
      if (fragment1 != null)
        return fragment1; 
    } 
    if (this.mCurTransaction == null)
      this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
    Fragment fragment = getItem(paramInt);
    if (this.mSavedState.size() > paramInt) {
      Fragment.SavedState savedState = this.mSavedState.get(paramInt);
      if (savedState != null)
        fragment.setInitialSavedState(savedState); 
    } 
    while (this.mFragments.size() <= paramInt)
      this.mFragments.add(null); 
    fragment.setMenuVisibility(false);
    if (this.mBehavior == 0)
      fragment.setUserVisibleHint(false); 
    this.mFragments.set(paramInt, fragment);
    this.mCurTransaction.add(paramViewGroup.getId(), fragment);
    if (this.mBehavior == 1)
      this.mCurTransaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED); 
    return fragment;
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject) {
    boolean bool;
    if (((Fragment)paramObject).getView() == paramView) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader) {
    if (paramParcelable != null) {
      Bundle bundle = (Bundle)paramParcelable;
      bundle.setClassLoader(paramClassLoader);
      Parcelable[] arrayOfParcelable = bundle.getParcelableArray("states");
      this.mSavedState.clear();
      this.mFragments.clear();
      if (arrayOfParcelable != null)
        for (byte b = 0; b < arrayOfParcelable.length; b++)
          this.mSavedState.add((Fragment.SavedState)arrayOfParcelable[b]);  
      for (String str : bundle.keySet()) {
        if (str.startsWith("f")) {
          int i = Integer.parseInt(str.substring(1));
          Fragment fragment = this.mFragmentManager.getFragment(bundle, str);
          if (fragment != null) {
            while (this.mFragments.size() <= i)
              this.mFragments.add(null); 
            fragment.setMenuVisibility(false);
            this.mFragments.set(i, fragment);
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Bad fragment at key ");
          stringBuilder.append(str);
          Log.w("FragmentStatePagerAdapt", stringBuilder.toString());
        } 
      } 
    } 
  }
  
  public Parcelable saveState() {
    Bundle bundle;
    if (this.mSavedState.size() > 0) {
      bundle = new Bundle();
      Fragment.SavedState[] arrayOfSavedState = new Fragment.SavedState[this.mSavedState.size()];
      this.mSavedState.toArray(arrayOfSavedState);
      bundle.putParcelableArray("states", (Parcelable[])arrayOfSavedState);
    } else {
      bundle = null;
    } 
    byte b = 0;
    while (b < this.mFragments.size()) {
      Fragment fragment = this.mFragments.get(b);
      Bundle bundle1 = bundle;
      if (fragment != null) {
        bundle1 = bundle;
        if (fragment.isAdded()) {
          bundle1 = bundle;
          if (bundle == null)
            bundle1 = new Bundle(); 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("f");
          stringBuilder.append(b);
          String str = stringBuilder.toString();
          this.mFragmentManager.putFragment(bundle1, str, fragment);
        } 
      } 
      b++;
      bundle = bundle1;
    } 
    return (Parcelable)bundle;
  }
  
  public void setPrimaryItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
    paramObject = paramObject;
    Fragment fragment = this.mCurrentPrimaryItem;
    if (paramObject != fragment) {
      if (fragment != null) {
        fragment.setMenuVisibility(false);
        if (this.mBehavior == 1) {
          if (this.mCurTransaction == null)
            this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
          this.mCurTransaction.setMaxLifecycle(this.mCurrentPrimaryItem, Lifecycle.State.STARTED);
        } else {
          this.mCurrentPrimaryItem.setUserVisibleHint(false);
        } 
      } 
      paramObject.setMenuVisibility(true);
      if (this.mBehavior == 1) {
        if (this.mCurTransaction == null)
          this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
        this.mCurTransaction.setMaxLifecycle((Fragment)paramObject, Lifecycle.State.RESUMED);
      } else {
        paramObject.setUserVisibleHint(true);
      } 
      this.mCurrentPrimaryItem = (Fragment)paramObject;
    } 
  }
  
  public void startUpdate(ViewGroup paramViewGroup) {
    if (paramViewGroup.getId() != -1)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("ViewPager with adapter ");
    stringBuilder.append(this);
    stringBuilder.append(" requires a view id");
    throw new IllegalStateException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentStatePagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */