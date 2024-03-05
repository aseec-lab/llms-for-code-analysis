package com.androidnetworking.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.androidnetworking.model.Progress;

public class UploadProgressHandler extends Handler {
  private final UploadProgressListener mUploadProgressListener;
  
  public UploadProgressHandler(UploadProgressListener paramUploadProgressListener) {
    super(Looper.getMainLooper());
    this.mUploadProgressListener = paramUploadProgressListener;
  }
  
  public void handleMessage(Message paramMessage) {
    if (paramMessage.what != 1) {
      super.handleMessage(paramMessage);
    } else if (this.mUploadProgressListener != null) {
      Progress progress = (Progress)paramMessage.obj;
      this.mUploadProgressListener.onProgress(progress.currentBytes, progress.totalBytes);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\UploadProgressHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */