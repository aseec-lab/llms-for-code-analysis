package androidx.customview.view;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsSavedState implements Parcelable {
  public static final Parcelable.Creator<AbsSavedState> CREATOR;
  
  public static final AbsSavedState EMPTY_STATE = new AbsSavedState() {
    
    };
  
  private final Parcelable mSuperState;
  
  static {
    CREATOR = (Parcelable.Creator<AbsSavedState>)new Parcelable.ClassLoaderCreator<AbsSavedState>() {
        public AbsSavedState createFromParcel(Parcel param1Parcel) {
          return createFromParcel(param1Parcel, (ClassLoader)null);
        }
        
        public AbsSavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
          if (param1Parcel.readParcelable(param1ClassLoader) == null)
            return AbsSavedState.EMPTY_STATE; 
          throw new IllegalStateException("superState must be null");
        }
        
        public AbsSavedState[] newArray(int param1Int) {
          return new AbsSavedState[param1Int];
        }
      };
  }
  
  private AbsSavedState() {
    this.mSuperState = null;
  }
  
  protected AbsSavedState(Parcel paramParcel) {
    this(paramParcel, null);
  }
  
  protected AbsSavedState(Parcel paramParcel, ClassLoader paramClassLoader) {
    Parcelable parcelable = paramParcel.readParcelable(paramClassLoader);
    if (parcelable == null)
      parcelable = EMPTY_STATE; 
    this.mSuperState = parcelable;
  }
  
  protected AbsSavedState(Parcelable paramParcelable) {
    if (paramParcelable != null) {
      if (paramParcelable == EMPTY_STATE)
        paramParcelable = null; 
      this.mSuperState = paramParcelable;
      return;
    } 
    throw new IllegalArgumentException("superState must not be null");
  }
  
  public int describeContents() {
    return 0;
  }
  
  public final Parcelable getSuperState() {
    return this.mSuperState;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeParcelable(this.mSuperState, paramInt);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\customview\view\AbsSavedState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */