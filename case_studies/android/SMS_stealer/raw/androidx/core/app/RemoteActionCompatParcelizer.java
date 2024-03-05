package androidx.core.app;

import android.app.PendingIntent;
import android.os.Parcelable;
import androidx.core.graphics.drawable.IconCompat;
import androidx.versionedparcelable.VersionedParcel;
import androidx.versionedparcelable.VersionedParcelable;

public class RemoteActionCompatParcelizer {
  public static RemoteActionCompat read(VersionedParcel paramVersionedParcel) {
    RemoteActionCompat remoteActionCompat = new RemoteActionCompat();
    remoteActionCompat.mIcon = (IconCompat)paramVersionedParcel.readVersionedParcelable((VersionedParcelable)remoteActionCompat.mIcon, 1);
    remoteActionCompat.mTitle = paramVersionedParcel.readCharSequence(remoteActionCompat.mTitle, 2);
    remoteActionCompat.mContentDescription = paramVersionedParcel.readCharSequence(remoteActionCompat.mContentDescription, 3);
    remoteActionCompat.mActionIntent = (PendingIntent)paramVersionedParcel.readParcelable((Parcelable)remoteActionCompat.mActionIntent, 4);
    remoteActionCompat.mEnabled = paramVersionedParcel.readBoolean(remoteActionCompat.mEnabled, 5);
    remoteActionCompat.mShouldShowIcon = paramVersionedParcel.readBoolean(remoteActionCompat.mShouldShowIcon, 6);
    return remoteActionCompat;
  }
  
  public static void write(RemoteActionCompat paramRemoteActionCompat, VersionedParcel paramVersionedParcel) {
    paramVersionedParcel.setSerializationFlags(false, false);
    paramVersionedParcel.writeVersionedParcelable((VersionedParcelable)paramRemoteActionCompat.mIcon, 1);
    paramVersionedParcel.writeCharSequence(paramRemoteActionCompat.mTitle, 2);
    paramVersionedParcel.writeCharSequence(paramRemoteActionCompat.mContentDescription, 3);
    paramVersionedParcel.writeParcelable((Parcelable)paramRemoteActionCompat.mActionIntent, 4);
    paramVersionedParcel.writeBoolean(paramRemoteActionCompat.mEnabled, 5);
    paramVersionedParcel.writeBoolean(paramRemoteActionCompat.mShouldShowIcon, 6);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\RemoteActionCompatParcelizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */