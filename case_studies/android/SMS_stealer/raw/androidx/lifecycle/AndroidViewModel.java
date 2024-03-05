package androidx.lifecycle;

import android.app.Application;

public class AndroidViewModel extends ViewModel {
  private Application mApplication;
  
  public AndroidViewModel(Application paramApplication) {
    this.mApplication = paramApplication;
  }
  
  public <T extends Application> T getApplication() {
    return (T)this.mApplication;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\AndroidViewModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */