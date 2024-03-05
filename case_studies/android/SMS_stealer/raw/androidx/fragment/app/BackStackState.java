package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;

final class BackStackState implements Parcelable {
  public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>() {
      public BackStackState createFromParcel(Parcel param1Parcel) {
        return new BackStackState(param1Parcel);
      }
      
      public BackStackState[] newArray(int param1Int) {
        return new BackStackState[param1Int];
      }
    };
  
  final int mBreadCrumbShortTitleRes;
  
  final CharSequence mBreadCrumbShortTitleText;
  
  final int mBreadCrumbTitleRes;
  
  final CharSequence mBreadCrumbTitleText;
  
  final int[] mCurrentMaxLifecycleStates;
  
  final ArrayList<String> mFragmentWhos;
  
  final int mIndex;
  
  final String mName;
  
  final int[] mOldMaxLifecycleStates;
  
  final int[] mOps;
  
  final boolean mReorderingAllowed;
  
  final ArrayList<String> mSharedElementSourceNames;
  
  final ArrayList<String> mSharedElementTargetNames;
  
  final int mTransition;
  
  final int mTransitionStyle;
  
  public BackStackState(Parcel paramParcel) {
    boolean bool;
    this.mOps = paramParcel.createIntArray();
    this.mFragmentWhos = paramParcel.createStringArrayList();
    this.mOldMaxLifecycleStates = paramParcel.createIntArray();
    this.mCurrentMaxLifecycleStates = paramParcel.createIntArray();
    this.mTransition = paramParcel.readInt();
    this.mTransitionStyle = paramParcel.readInt();
    this.mName = paramParcel.readString();
    this.mIndex = paramParcel.readInt();
    this.mBreadCrumbTitleRes = paramParcel.readInt();
    this.mBreadCrumbTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel);
    this.mBreadCrumbShortTitleRes = paramParcel.readInt();
    this.mBreadCrumbShortTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel);
    this.mSharedElementSourceNames = paramParcel.createStringArrayList();
    this.mSharedElementTargetNames = paramParcel.createStringArrayList();
    if (paramParcel.readInt() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mReorderingAllowed = bool;
  }
  
  public BackStackState(BackStackRecord paramBackStackRecord) {
    int i = paramBackStackRecord.mOps.size();
    this.mOps = new int[i * 5];
    if (paramBackStackRecord.mAddToBackStack) {
      this.mFragmentWhos = new ArrayList<String>(i);
      this.mOldMaxLifecycleStates = new int[i];
      this.mCurrentMaxLifecycleStates = new int[i];
      byte b = 0;
      for (int j = 0; b < i; j = k + 1) {
        FragmentTransaction.Op op = paramBackStackRecord.mOps.get(b);
        int[] arrayOfInt = this.mOps;
        int k = j + 1;
        arrayOfInt[j] = op.mCmd;
        ArrayList<String> arrayList = this.mFragmentWhos;
        if (op.mFragment != null) {
          String str = op.mFragment.mWho;
        } else {
          arrayOfInt = null;
        } 
        arrayList.add(arrayOfInt);
        arrayOfInt = this.mOps;
        int m = k + 1;
        arrayOfInt[k] = op.mEnterAnim;
        arrayOfInt = this.mOps;
        j = m + 1;
        arrayOfInt[m] = op.mExitAnim;
        arrayOfInt = this.mOps;
        k = j + 1;
        arrayOfInt[j] = op.mPopEnterAnim;
        this.mOps[k] = op.mPopExitAnim;
        this.mOldMaxLifecycleStates[b] = op.mOldMaxState.ordinal();
        this.mCurrentMaxLifecycleStates[b] = op.mCurrentMaxState.ordinal();
        b++;
      } 
      this.mTransition = paramBackStackRecord.mTransition;
      this.mTransitionStyle = paramBackStackRecord.mTransitionStyle;
      this.mName = paramBackStackRecord.mName;
      this.mIndex = paramBackStackRecord.mIndex;
      this.mBreadCrumbTitleRes = paramBackStackRecord.mBreadCrumbTitleRes;
      this.mBreadCrumbTitleText = paramBackStackRecord.mBreadCrumbTitleText;
      this.mBreadCrumbShortTitleRes = paramBackStackRecord.mBreadCrumbShortTitleRes;
      this.mBreadCrumbShortTitleText = paramBackStackRecord.mBreadCrumbShortTitleText;
      this.mSharedElementSourceNames = paramBackStackRecord.mSharedElementSourceNames;
      this.mSharedElementTargetNames = paramBackStackRecord.mSharedElementTargetNames;
      this.mReorderingAllowed = paramBackStackRecord.mReorderingAllowed;
      return;
    } 
    throw new IllegalStateException("Not on back stack");
  }
  
  public int describeContents() {
    return 0;
  }
  
  public BackStackRecord instantiate(FragmentManagerImpl paramFragmentManagerImpl) {
    BackStackRecord backStackRecord = new BackStackRecord(paramFragmentManagerImpl);
    int i = 0;
    byte b = 0;
    while (i < this.mOps.length) {
      FragmentTransaction.Op op = new FragmentTransaction.Op();
      int[] arrayOfInt2 = this.mOps;
      int k = i + 1;
      op.mCmd = arrayOfInt2[i];
      if (FragmentManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Instantiate ");
        stringBuilder.append(backStackRecord);
        stringBuilder.append(" op #");
        stringBuilder.append(b);
        stringBuilder.append(" base fragment #");
        stringBuilder.append(this.mOps[k]);
        Log.v("FragmentManager", stringBuilder.toString());
      } 
      String str = this.mFragmentWhos.get(b);
      if (str != null) {
        op.mFragment = paramFragmentManagerImpl.mActive.get(str);
      } else {
        op.mFragment = null;
      } 
      op.mOldMaxState = Lifecycle.State.values()[this.mOldMaxLifecycleStates[b]];
      op.mCurrentMaxState = Lifecycle.State.values()[this.mCurrentMaxLifecycleStates[b]];
      int[] arrayOfInt1 = this.mOps;
      int j = k + 1;
      op.mEnterAnim = arrayOfInt1[k];
      arrayOfInt1 = this.mOps;
      i = j + 1;
      op.mExitAnim = arrayOfInt1[j];
      arrayOfInt1 = this.mOps;
      j = i + 1;
      op.mPopEnterAnim = arrayOfInt1[i];
      op.mPopExitAnim = this.mOps[j];
      backStackRecord.mEnterAnim = op.mEnterAnim;
      backStackRecord.mExitAnim = op.mExitAnim;
      backStackRecord.mPopEnterAnim = op.mPopEnterAnim;
      backStackRecord.mPopExitAnim = op.mPopExitAnim;
      backStackRecord.addOp(op);
      b++;
      i = j + 1;
    } 
    backStackRecord.mTransition = this.mTransition;
    backStackRecord.mTransitionStyle = this.mTransitionStyle;
    backStackRecord.mName = this.mName;
    backStackRecord.mIndex = this.mIndex;
    backStackRecord.mAddToBackStack = true;
    backStackRecord.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
    backStackRecord.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
    backStackRecord.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
    backStackRecord.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
    backStackRecord.mSharedElementSourceNames = this.mSharedElementSourceNames;
    backStackRecord.mSharedElementTargetNames = this.mSharedElementTargetNames;
    backStackRecord.mReorderingAllowed = this.mReorderingAllowed;
    backStackRecord.bumpBackStackNesting(1);
    return backStackRecord;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeIntArray(this.mOps);
    paramParcel.writeStringList(this.mFragmentWhos);
    paramParcel.writeIntArray(this.mOldMaxLifecycleStates);
    paramParcel.writeIntArray(this.mCurrentMaxLifecycleStates);
    paramParcel.writeInt(this.mTransition);
    paramParcel.writeInt(this.mTransitionStyle);
    paramParcel.writeString(this.mName);
    paramParcel.writeInt(this.mIndex);
    paramParcel.writeInt(this.mBreadCrumbTitleRes);
    TextUtils.writeToParcel(this.mBreadCrumbTitleText, paramParcel, 0);
    paramParcel.writeInt(this.mBreadCrumbShortTitleRes);
    TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, paramParcel, 0);
    paramParcel.writeStringList(this.mSharedElementSourceNames);
    paramParcel.writeStringList(this.mSharedElementTargetNames);
    paramParcel.writeInt(this.mReorderingAllowed);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\BackStackState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */