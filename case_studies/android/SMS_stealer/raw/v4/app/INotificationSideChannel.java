package android.support.v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INotificationSideChannel extends IInterface {
  void cancel(String paramString1, int paramInt, String paramString2) throws RemoteException;
  
  void cancelAll(String paramString) throws RemoteException;
  
  void notify(String paramString1, int paramInt, String paramString2, Notification paramNotification) throws RemoteException;
  
  public static class Default implements INotificationSideChannel {
    public IBinder asBinder() {
      return null;
    }
    
    public void cancel(String param1String1, int param1Int, String param1String2) throws RemoteException {}
    
    public void cancelAll(String param1String) throws RemoteException {}
    
    public void notify(String param1String1, int param1Int, String param1String2, Notification param1Notification) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements INotificationSideChannel {
    private static final String DESCRIPTOR = "android.support.v4.app.INotificationSideChannel";
    
    static final int TRANSACTION_cancel = 2;
    
    static final int TRANSACTION_cancelAll = 3;
    
    static final int TRANSACTION_notify = 1;
    
    public Stub() {
      attachInterface(this, "android.support.v4.app.INotificationSideChannel");
    }
    
    public static INotificationSideChannel asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.support.v4.app.INotificationSideChannel");
      return (iInterface != null && iInterface instanceof INotificationSideChannel) ? (INotificationSideChannel)iInterface : new Proxy(param1IBinder);
    }
    
    public static INotificationSideChannel getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(INotificationSideChannel param1INotificationSideChannel) {
      if (Proxy.sDefaultImpl == null && param1INotificationSideChannel != null) {
        Proxy.sDefaultImpl = param1INotificationSideChannel;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 1598968902)
              return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
            param1Parcel2.writeString("android.support.v4.app.INotificationSideChannel");
            return true;
          } 
          param1Parcel1.enforceInterface("android.support.v4.app.INotificationSideChannel");
          cancelAll(param1Parcel1.readString());
          return true;
        } 
        param1Parcel1.enforceInterface("android.support.v4.app.INotificationSideChannel");
        cancel(param1Parcel1.readString(), param1Parcel1.readInt(), param1Parcel1.readString());
        return true;
      } 
      param1Parcel1.enforceInterface("android.support.v4.app.INotificationSideChannel");
      String str1 = param1Parcel1.readString();
      param1Int1 = param1Parcel1.readInt();
      String str2 = param1Parcel1.readString();
      if (param1Parcel1.readInt() != 0) {
        Notification notification = (Notification)Notification.CREATOR.createFromParcel(param1Parcel1);
      } else {
        param1Parcel1 = null;
      } 
      notify(str1, param1Int1, str2, (Notification)param1Parcel1);
      return true;
    }
    
    private static class Proxy implements INotificationSideChannel {
      public static INotificationSideChannel sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void cancel(String param2String1, int param2Int, String param2String2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
          parcel.writeString(param2String1);
          parcel.writeInt(param2Int);
          parcel.writeString(param2String2);
          if (!this.mRemote.transact(2, parcel, null, 1) && INotificationSideChannel.Stub.getDefaultImpl() != null) {
            INotificationSideChannel.Stub.getDefaultImpl().cancel(param2String1, param2Int, param2String2);
            return;
          } 
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void cancelAll(String param2String) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
          parcel.writeString(param2String);
          if (!this.mRemote.transact(3, parcel, null, 1) && INotificationSideChannel.Stub.getDefaultImpl() != null) {
            INotificationSideChannel.Stub.getDefaultImpl().cancelAll(param2String);
            return;
          } 
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.support.v4.app.INotificationSideChannel";
      }
      
      public void notify(String param2String1, int param2Int, String param2String2, Notification param2Notification) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
          parcel.writeString(param2String1);
          parcel.writeInt(param2Int);
          parcel.writeString(param2String2);
          if (param2Notification != null) {
            parcel.writeInt(1);
            param2Notification.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel, null, 1) && INotificationSideChannel.Stub.getDefaultImpl() != null) {
            INotificationSideChannel.Stub.getDefaultImpl().notify(param2String1, param2Int, param2String2, param2Notification);
            return;
          } 
          return;
        } finally {
          parcel.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements INotificationSideChannel {
    public static INotificationSideChannel sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void cancel(String param1String1, int param1Int, String param1String2) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
        parcel.writeString(param1String1);
        parcel.writeInt(param1Int);
        parcel.writeString(param1String2);
        if (!this.mRemote.transact(2, parcel, null, 1) && INotificationSideChannel.Stub.getDefaultImpl() != null) {
          INotificationSideChannel.Stub.getDefaultImpl().cancel(param1String1, param1Int, param1String2);
          return;
        } 
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void cancelAll(String param1String) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
        parcel.writeString(param1String);
        if (!this.mRemote.transact(3, parcel, null, 1) && INotificationSideChannel.Stub.getDefaultImpl() != null) {
          INotificationSideChannel.Stub.getDefaultImpl().cancelAll(param1String);
          return;
        } 
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.support.v4.app.INotificationSideChannel";
    }
    
    public void notify(String param1String1, int param1Int, String param1String2, Notification param1Notification) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
        parcel.writeString(param1String1);
        parcel.writeInt(param1Int);
        parcel.writeString(param1String2);
        if (param1Notification != null) {
          parcel.writeInt(1);
          param1Notification.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel, null, 1) && INotificationSideChannel.Stub.getDefaultImpl() != null) {
          INotificationSideChannel.Stub.getDefaultImpl().notify(param1String1, param1Int, param1String2, param1Notification);
          return;
        } 
        return;
      } finally {
        parcel.recycle();
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\android\support\v4\app\INotificationSideChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */