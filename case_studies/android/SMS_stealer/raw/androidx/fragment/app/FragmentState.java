package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.lifecycle.Lifecycle;

final class FragmentState implements Parcelable {
  public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator<FragmentState>() {
      public FragmentState createFromParcel(Parcel param1Parcel) {
        return new FragmentState(param1Parcel);
      }
      
      public FragmentState[] newArray(int param1Int) {
        return new FragmentState[param1Int];
      }
    };
  
  final Bundle mArguments;
  
  final String mClassName;
  
  final int mContainerId;
  
  final boolean mDetached;
  
  final int mFragmentId;
  
  final boolean mFromLayout;
  
  final boolean mHidden;
  
  Fragment mInstance;
  
  final int mMaxLifecycleState;
  
  final boolean mRemoving;
  
  final boolean mRetainInstance;
  
  Bundle mSavedFragmentState;
  
  final String mTag;
  
  final String mWho;
  
  FragmentState(Parcel paramParcel) {
    boolean bool1;
    this.mClassName = paramParcel.readString();
    this.mWho = paramParcel.readString();
    int i = paramParcel.readInt();
    boolean bool2 = true;
    if (i != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.mFromLayout = bool1;
    this.mFragmentId = paramParcel.readInt();
    this.mContainerId = paramParcel.readInt();
    this.mTag = paramParcel.readString();
    if (paramParcel.readInt() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.mRetainInstance = bool1;
    if (paramParcel.readInt() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.mRemoving = bool1;
    if (paramParcel.readInt() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.mDetached = bool1;
    this.mArguments = paramParcel.readBundle();
    if (paramParcel.readInt() != 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    this.mHidden = bool1;
    this.mSavedFragmentState = paramParcel.readBundle();
    this.mMaxLifecycleState = paramParcel.readInt();
  }
  
  FragmentState(Fragment paramFragment) {
    this.mClassName = paramFragment.getClass().getName();
    this.mWho = paramFragment.mWho;
    this.mFromLayout = paramFragment.mFromLayout;
    this.mFragmentId = paramFragment.mFragmentId;
    this.mContainerId = paramFragment.mContainerId;
    this.mTag = paramFragment.mTag;
    this.mRetainInstance = paramFragment.mRetainInstance;
    this.mRemoving = paramFragment.mRemoving;
    this.mDetached = paramFragment.mDetached;
    this.mArguments = paramFragment.mArguments;
    this.mHidden = paramFragment.mHidden;
    this.mMaxLifecycleState = paramFragment.mMaxState.ordinal();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public Fragment instantiate(ClassLoader paramClassLoader, FragmentFactory paramFragmentFactory) {
    if (this.mInstance == null) {
      Bundle bundle2 = this.mArguments;
      if (bundle2 != null)
        bundle2.setClassLoader(paramClassLoader); 
      Fragment fragment = paramFragmentFactory.instantiate(paramClassLoader, this.mClassName);
      this.mInstance = fragment;
      fragment.setArguments(this.mArguments);
      Bundle bundle1 = this.mSavedFragmentState;
      if (bundle1 != null) {
        bundle1.setClassLoader(paramClassLoader);
        this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
      } else {
        this.mInstance.mSavedFragmentState = new Bundle();
      } 
      this.mInstance.mWho = this.mWho;
      this.mInstance.mFromLayout = this.mFromLayout;
      this.mInstance.mRestored = true;
      this.mInstance.mFragmentId = this.mFragmentId;
      this.mInstance.mContainerId = this.mContainerId;
      this.mInstance.mTag = this.mTag;
      this.mInstance.mRetainInstance = this.mRetainInstance;
      this.mInstance.mRemoving = this.mRemoving;
      this.mInstance.mDetached = this.mDetached;
      this.mInstance.mHidden = this.mHidden;
      this.mInstance.mMaxState = Lifecycle.State.values()[this.mMaxLifecycleState];
      if (FragmentManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Instantiated fragment ");
        stringBuilder.append(this.mInstance);
        Log.v("FragmentManager", stringBuilder.toString());
      } 
    } 
    return this.mInstance;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("FragmentState{");
    stringBuilder.append(this.mClassName);
    stringBuilder.append(" (");
    stringBuilder.append(this.mWho);
    stringBuilder.append(")}:");
    if (this.mFromLayout)
      stringBuilder.append(" fromLayout"); 
    if (this.mContainerId != 0) {
      stringBuilder.append(" id=0x");
      stringBuilder.append(Integer.toHexString(this.mContainerId));
    } 
    String str = this.mTag;
    if (str != null && !str.isEmpty()) {
      stringBuilder.append(" tag=");
      stringBuilder.append(this.mTag);
    } 
    if (this.mRetainInstance)
      stringBuilder.append(" retainInstance"); 
    if (this.mRemoving)
      stringBuilder.append(" removing"); 
    if (this.mDetached)
      stringBuilder.append(" detached"); 
    if (this.mHidden)
      stringBuilder.append(" hidden"); 
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.mClassName);
    paramParcel.writeString(this.mWho);
    paramParcel.writeInt(this.mFromLayout);
    paramParcel.writeInt(this.mFragmentId);
    paramParcel.writeInt(this.mContainerId);
    paramParcel.writeString(this.mTag);
    paramParcel.writeInt(this.mRetainInstance);
    paramParcel.writeInt(this.mRemoving);
    paramParcel.writeInt(this.mDetached);
    paramParcel.writeBundle(this.mArguments);
    paramParcel.writeInt(this.mHidden);
    paramParcel.writeBundle(this.mSavedFragmentState);
    paramParcel.writeInt(this.mMaxLifecycleState);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */