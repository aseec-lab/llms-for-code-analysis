package androidx.loader.app;

import android.os.Bundle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {
  public static void enableDebugLogging(boolean paramBoolean) {
    LoaderManagerImpl.DEBUG = paramBoolean;
  }
  
  public static <T extends LifecycleOwner & ViewModelStoreOwner> LoaderManager getInstance(T paramT) {
    return new LoaderManagerImpl((LifecycleOwner)paramT, ((ViewModelStoreOwner)paramT).getViewModelStore());
  }
  
  public abstract void destroyLoader(int paramInt);
  
  @Deprecated
  public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);
  
  public abstract <D> Loader<D> getLoader(int paramInt);
  
  public boolean hasRunningLoaders() {
    return false;
  }
  
  public abstract <D> Loader<D> initLoader(int paramInt, Bundle paramBundle, LoaderCallbacks<D> paramLoaderCallbacks);
  
  public abstract void markForRedelivery();
  
  public abstract <D> Loader<D> restartLoader(int paramInt, Bundle paramBundle, LoaderCallbacks<D> paramLoaderCallbacks);
  
  public static interface LoaderCallbacks<D> {
    Loader<D> onCreateLoader(int param1Int, Bundle param1Bundle);
    
    void onLoadFinished(Loader<D> param1Loader, D param1D);
    
    void onLoaderReset(Loader<D> param1Loader);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\loader\app\LoaderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */