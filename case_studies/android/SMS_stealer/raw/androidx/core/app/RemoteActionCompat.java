package androidx.core.app;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.os.Build;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.VersionedParcelable;

public final class RemoteActionCompat implements VersionedParcelable {
  public PendingIntent mActionIntent;
  
  public CharSequence mContentDescription;
  
  public boolean mEnabled;
  
  public IconCompat mIcon;
  
  public boolean mShouldShowIcon;
  
  public CharSequence mTitle;
  
  public RemoteActionCompat() {}
  
  public RemoteActionCompat(RemoteActionCompat paramRemoteActionCompat) {
    Preconditions.checkNotNull(paramRemoteActionCompat);
    this.mIcon = paramRemoteActionCompat.mIcon;
    this.mTitle = paramRemoteActionCompat.mTitle;
    this.mContentDescription = paramRemoteActionCompat.mContentDescription;
    this.mActionIntent = paramRemoteActionCompat.mActionIntent;
    this.mEnabled = paramRemoteActionCompat.mEnabled;
    this.mShouldShowIcon = paramRemoteActionCompat.mShouldShowIcon;
  }
  
  public RemoteActionCompat(IconCompat paramIconCompat, CharSequence paramCharSequence1, CharSequence paramCharSequence2, PendingIntent paramPendingIntent) {
    this.mIcon = (IconCompat)Preconditions.checkNotNull(paramIconCompat);
    this.mTitle = (CharSequence)Preconditions.checkNotNull(paramCharSequence1);
    this.mContentDescription = (CharSequence)Preconditions.checkNotNull(paramCharSequence2);
    this.mActionIntent = (PendingIntent)Preconditions.checkNotNull(paramPendingIntent);
    this.mEnabled = true;
    this.mShouldShowIcon = true;
  }
  
  public static RemoteActionCompat createFromRemoteAction(RemoteAction paramRemoteAction) {
    Preconditions.checkNotNull(paramRemoteAction);
    RemoteActionCompat remoteActionCompat = new RemoteActionCompat(IconCompat.createFromIcon(paramRemoteAction.getIcon()), paramRemoteAction.getTitle(), paramRemoteAction.getContentDescription(), paramRemoteAction.getActionIntent());
    remoteActionCompat.setEnabled(paramRemoteAction.isEnabled());
    if (Build.VERSION.SDK_INT >= 28)
      remoteActionCompat.setShouldShowIcon(paramRemoteAction.shouldShowIcon()); 
    return remoteActionCompat;
  }
  
  public PendingIntent getActionIntent() {
    return this.mActionIntent;
  }
  
  public CharSequence getContentDescription() {
    return this.mContentDescription;
  }
  
  public IconCompat getIcon() {
    return this.mIcon;
  }
  
  public CharSequence getTitle() {
    return this.mTitle;
  }
  
  public boolean isEnabled() {
    return this.mEnabled;
  }
  
  public void setEnabled(boolean paramBoolean) {
    this.mEnabled = paramBoolean;
  }
  
  public void setShouldShowIcon(boolean paramBoolean) {
    this.mShouldShowIcon = paramBoolean;
  }
  
  public boolean shouldShowIcon() {
    return this.mShouldShowIcon;
  }
  
  public RemoteAction toRemoteAction() {
    RemoteAction remoteAction = new RemoteAction(this.mIcon.toIcon(), this.mTitle, this.mContentDescription, this.mActionIntent);
    remoteAction.setEnabled(isEnabled());
    if (Build.VERSION.SDK_INT >= 28)
      remoteAction.setShouldShowIcon(shouldShowIcon()); 
    return remoteAction;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\RemoteActionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */