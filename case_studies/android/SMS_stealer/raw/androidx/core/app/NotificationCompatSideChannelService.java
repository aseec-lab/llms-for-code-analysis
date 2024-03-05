package androidx.core.app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;

public abstract class NotificationCompatSideChannelService extends Service {
  public abstract void cancel(String paramString1, int paramInt, String paramString2);
  
  public abstract void cancelAll(String paramString);
  
  void checkPermission(int paramInt, String paramString) {
    String[] arrayOfString = getPackageManager().getPackagesForUid(paramInt);
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++) {
      if (arrayOfString[b].equals(paramString))
        return; 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("NotificationSideChannelService: Uid ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" is not authorized for package ");
    stringBuilder.append(paramString);
    throw new SecurityException(stringBuilder.toString());
  }
  
  public abstract void notify(String paramString1, int paramInt, String paramString2, Notification paramNotification);
  
  public IBinder onBind(Intent paramIntent) {
    return (IBinder)(paramIntent.getAction().equals("android.support.BIND_NOTIFICATION_SIDE_CHANNEL") ? ((Build.VERSION.SDK_INT > 19) ? null : new NotificationSideChannelStub()) : null);
  }
  
  private class NotificationSideChannelStub extends INotificationSideChannel.Stub {
    final NotificationCompatSideChannelService this$0;
    
    public void cancel(String param1String1, int param1Int, String param1String2) throws RemoteException {
      NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), param1String1);
      long l = clearCallingIdentity();
      try {
        NotificationCompatSideChannelService.this.cancel(param1String1, param1Int, param1String2);
        return;
      } finally {
        restoreCallingIdentity(l);
      } 
    }
    
    public void cancelAll(String param1String) {
      NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), param1String);
      long l = clearCallingIdentity();
      try {
        NotificationCompatSideChannelService.this.cancelAll(param1String);
        return;
      } finally {
        restoreCallingIdentity(l);
      } 
    }
    
    public void notify(String param1String1, int param1Int, String param1String2, Notification param1Notification) throws RemoteException {
      NotificationCompatSideChannelService.this.checkPermission(getCallingUid(), param1String1);
      long l = clearCallingIdentity();
      try {
        NotificationCompatSideChannelService.this.notify(param1String1, param1Int, param1String2, param1Notification);
        return;
      } finally {
        restoreCallingIdentity(l);
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\NotificationCompatSideChannelService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */