package com.androidnetworking.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.model.Progress;

public class DownloadProgressHandler extends Handler {
  private final DownloadProgressListener mDownloadProgressListener;
  
  public DownloadProgressHandler(DownloadProgressListener paramDownloadProgressListener) {
    super(Looper.getMainLooper());
    this.mDownloadProgressListener = paramDownloadProgressListener;
  }
  
  public void handleMessage(Message paramMessage) {
    if (paramMessage.what != 1) {
      super.handleMessage(paramMessage);
    } else if (this.mDownloadProgressListener != null) {
      Progress progress = (Progress)paramMessage.obj;
      this.mDownloadProgressListener.onProgress(progress.currentBytes, progress.totalBytes);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\DownloadProgressHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */