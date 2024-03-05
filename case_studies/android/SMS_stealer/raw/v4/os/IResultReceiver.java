package android.support.v4.os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IResultReceiver extends IInterface {
  void send(int paramInt, Bundle paramBundle) throws RemoteException;
  
  public static class Default implements IResultReceiver {
    public IBinder asBinder() {
      return null;
    }
    
    public void send(int param1Int, Bundle param1Bundle) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IResultReceiver {
    private static final String DESCRIPTOR = "android.support.v4.os.IResultReceiver";
    
    static final int TRANSACTION_send = 1;
    
    public Stub() {
      attachInterface(this, "android.support.v4.os.IResultReceiver");
    }
    
    public static IResultReceiver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.support.v4.os.IResultReceiver");
      return (iInterface != null && iInterface instanceof IResultReceiver) ? (IResultReceiver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IResultReceiver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IResultReceiver param1IResultReceiver) {
      if (Proxy.sDefaultImpl == null && param1IResultReceiver != null) {
        Proxy.sDefaultImpl = param1IResultReceiver;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("android.support.v4.os.IResultReceiver");
        return true;
      } 
      param1Parcel1.enforceInterface("android.support.v4.os.IResultReceiver");
      param1Int1 = param1Parcel1.readInt();
      if (param1Parcel1.readInt() != 0) {
        Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1);
      } else {
        param1Parcel1 = null;
      } 
      send(param1Int1, (Bundle)param1Parcel1);
      return true;
    }
    
    private static class Proxy implements IResultReceiver {
      public static IResultReceiver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.support.v4.os.IResultReceiver";
      }
      
      public void send(int param2Int, Bundle param2Bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.os.IResultReceiver");
          parcel.writeInt(param2Int);
          if (param2Bundle != null) {
            parcel.writeInt(1);
            param2Bundle.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel, null, 1) && IResultReceiver.Stub.getDefaultImpl() != null) {
            IResultReceiver.Stub.getDefaultImpl().send(param2Int, param2Bundle);
            return;
          } 
          return;
        } finally {
          parcel.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IResultReceiver {
    public static IResultReceiver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.support.v4.os.IResultReceiver";
    }
    
    public void send(int param1Int, Bundle param1Bundle) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.os.IResultReceiver");
        parcel.writeInt(param1Int);
        if (param1Bundle != null) {
          parcel.writeInt(1);
          param1Bundle.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel, null, 1) && IResultReceiver.Stub.getDefaultImpl() != null) {
          IResultReceiver.Stub.getDefaultImpl().send(param1Int, param1Bundle);
          return;
        } 
        return;
      } finally {
        parcel.recycle();
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\android\support\v4\os\IResultReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */