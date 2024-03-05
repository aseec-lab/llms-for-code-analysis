package androidx.core.os;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public final class ParcelableCompat {
  @Deprecated
  public static <T> Parcelable.Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> paramParcelableCompatCreatorCallbacks) {
    return (Parcelable.Creator<T>)new ParcelableCompatCreatorHoneycombMR2<T>(paramParcelableCompatCreatorCallbacks);
  }
  
  static class ParcelableCompatCreatorHoneycombMR2<T> implements Parcelable.ClassLoaderCreator<T> {
    private final ParcelableCompatCreatorCallbacks<T> mCallbacks;
    
    ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> param1ParcelableCompatCreatorCallbacks) {
      this.mCallbacks = param1ParcelableCompatCreatorCallbacks;
    }
    
    public T createFromParcel(Parcel param1Parcel) {
      return this.mCallbacks.createFromParcel(param1Parcel, null);
    }
    
    public T createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return this.mCallbacks.createFromParcel(param1Parcel, param1ClassLoader);
    }
    
    public T[] newArray(int param1Int) {
      return this.mCallbacks.newArray(param1Int);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\os\ParcelableCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */