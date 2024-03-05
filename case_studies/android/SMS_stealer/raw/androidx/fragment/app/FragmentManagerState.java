package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

final class FragmentManagerState implements Parcelable {
  public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator<FragmentManagerState>() {
      public FragmentManagerState createFromParcel(Parcel param1Parcel) {
        return new FragmentManagerState(param1Parcel);
      }
      
      public FragmentManagerState[] newArray(int param1Int) {
        return new FragmentManagerState[param1Int];
      }
    };
  
  ArrayList<FragmentState> mActive;
  
  ArrayList<String> mAdded;
  
  BackStackState[] mBackStack;
  
  int mNextFragmentIndex;
  
  String mPrimaryNavActiveWho = null;
  
  public FragmentManagerState() {}
  
  public FragmentManagerState(Parcel paramParcel) {
    this.mActive = paramParcel.createTypedArrayList(FragmentState.CREATOR);
    this.mAdded = paramParcel.createStringArrayList();
    this.mBackStack = (BackStackState[])paramParcel.createTypedArray(BackStackState.CREATOR);
    this.mPrimaryNavActiveWho = paramParcel.readString();
    this.mNextFragmentIndex = paramParcel.readInt();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeTypedList(this.mActive);
    paramParcel.writeStringList(this.mAdded);
    paramParcel.writeTypedArray((Parcelable[])this.mBackStack, paramInt);
    paramParcel.writeString(this.mPrimaryNavActiveWho);
    paramParcel.writeInt(this.mNextFragmentIndex);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\fragment\app\FragmentManagerState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */