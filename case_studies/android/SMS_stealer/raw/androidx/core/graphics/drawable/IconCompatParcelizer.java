package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.os.Parcelable;
import androidx.versionedparcelable.VersionedParcel;

public class IconCompatParcelizer {
  public static IconCompat read(VersionedParcel paramVersionedParcel) {
    IconCompat iconCompat = new IconCompat();
    iconCompat.mType = paramVersionedParcel.readInt(iconCompat.mType, 1);
    iconCompat.mData = paramVersionedParcel.readByteArray(iconCompat.mData, 2);
    iconCompat.mParcelable = paramVersionedParcel.readParcelable(iconCompat.mParcelable, 3);
    iconCompat.mInt1 = paramVersionedParcel.readInt(iconCompat.mInt1, 4);
    iconCompat.mInt2 = paramVersionedParcel.readInt(iconCompat.mInt2, 5);
    iconCompat.mTintList = (ColorStateList)paramVersionedParcel.readParcelable((Parcelable)iconCompat.mTintList, 6);
    iconCompat.mTintModeStr = paramVersionedParcel.readString(iconCompat.mTintModeStr, 7);
    iconCompat.onPostParceling();
    return iconCompat;
  }
  
  public static void write(IconCompat paramIconCompat, VersionedParcel paramVersionedParcel) {
    paramVersionedParcel.setSerializationFlags(true, true);
    paramIconCompat.onPreParceling(paramVersionedParcel.isStream());
    if (-1 != paramIconCompat.mType)
      paramVersionedParcel.writeInt(paramIconCompat.mType, 1); 
    if (paramIconCompat.mData != null)
      paramVersionedParcel.writeByteArray(paramIconCompat.mData, 2); 
    if (paramIconCompat.mParcelable != null)
      paramVersionedParcel.writeParcelable(paramIconCompat.mParcelable, 3); 
    if (paramIconCompat.mInt1 != 0)
      paramVersionedParcel.writeInt(paramIconCompat.mInt1, 4); 
    if (paramIconCompat.mInt2 != 0)
      paramVersionedParcel.writeInt(paramIconCompat.mInt2, 5); 
    if (paramIconCompat.mTintList != null)
      paramVersionedParcel.writeParcelable((Parcelable)paramIconCompat.mTintList, 6); 
    if (paramIconCompat.mTintModeStr != null)
      paramVersionedParcel.writeString(paramIconCompat.mTintModeStr, 7); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\drawable\IconCompatParcelizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */