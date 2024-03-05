package androidx.fragment.app;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;

public abstract class FragmentPagerAdapter extends PagerAdapter {
  public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
  
  @Deprecated
  public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
  
  private static final boolean DEBUG = false;
  
  private static final String TAG = "FragmentPagerAdapter";
  
  private final int mBehavior;
  
  private FragmentTransaction mCurTransaction = null;
  
  private Fragment mCurrentPrimaryItem = null;
  
  private final FragmentManager mFragmentManager;
  
  @Deprecated
  public FragmentPagerAdapter(FragmentManager paramFragmentManager) {
    this(paramFragmentManager, 0);
  }
  
  public FragmentPagerAdapter(FragmentManager paramFragmentManager, int paramInt) {
    this.mFragmentManager = paramFragmentManager;
    this.mBehavior = paramInt;
  }
  
  private static String makeFragmentName(int paramInt, long paramLong) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("android:switcher:");
    stringBuilder.append(paramInt);
    stringBuilder.append(":");
    stringBuilder.append(paramLong);
    return stringBuilder.toString();
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
    Fragment fragment = (Fragment)paramObject;
    if (this.mCurTransaction == null)
      this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
    this.mCurTransaction.detach(fragment);
    if (fragment == this.mCurrentPrimaryItem)
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
  
  public long getItemId(int paramInt) {
    return paramInt;
  }
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
    Fragment fragment1;
    if (this.mCurTransaction == null)
      this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
    long l = getItemId(paramInt);
    String str = makeFragmentName(paramViewGroup.getId(), l);
    Fragment fragment2 = this.mFragmentManager.findFragmentByTag(str);
    if (fragment2 != null) {
      this.mCurTransaction.attach(fragment2);
      fragment1 = fragment2;
    } else {
      fragment2 = getItem(paramInt);
      this.mCurTransaction.add(fragment1.getId(), fragment2, makeFragmentName(fragment1.getId(), l));
      fragment1 = fragment2;
    } 
    if (fragment1 != this.mCurrentPrimaryItem) {
      fragment1.setMenuVisibility(false);
      if (this.mBehavior == 1) {
        this.mCurTransaction.setMaxLifecycle(fragment1, Lifecycle.State.STARTED);
      } else {
        fragment1.setUserVisibleHint(false);
      } 
    } 
    return fragment1;
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
  
  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader) {}
  
  public Parcelable saveState() {
    return null;
  }
  
  public void setPrimaryItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
    Fragment fragment = (Fragment)paramObject;
    paramObject = this.mCurrentPrimaryItem;
    if (fragment != paramObject) {
      if (paramObject != null) {
        paramObject.setMenuVisibility(false);
        if (this.mBehavior == 1) {
          if (this.mCurTransaction == null)
            this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
          this.mCurTransaction.setMaxLifecycle(this.mCurrentPrimaryItem, Lifecycle.State.STARTED);
        } else {
          this.mCurrentPrimaryItem.setUserVisibleHint(false);
        } 
      } 
      fragment.setMenuVisibility(true);
      if (this.mBehavior == 1) {
        if (this.mCurTransaction == null)
          this.mCurTransaction = this.mFragmentManager.beginTransaction(); 
        this.mCurTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
      } else {
        fragment.setUserVisibleHint(true);
      } 
      this.mCurrentPrimaryItem = fragment;
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


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */