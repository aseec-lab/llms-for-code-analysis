package com.androidnetworking.internal;

import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.model.Progress;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ResponseProgressBody extends ResponseBody {
  private BufferedSource bufferedSource;
  
  private DownloadProgressHandler downloadProgressHandler;
  
  private final ResponseBody mResponseBody;
  
  public ResponseProgressBody(ResponseBody paramResponseBody, DownloadProgressListener paramDownloadProgressListener) {
    this.mResponseBody = paramResponseBody;
    if (paramDownloadProgressListener != null)
      this.downloadProgressHandler = new DownloadProgressHandler(paramDownloadProgressListener); 
  }
  
  private Source source(Source paramSource) {
    return (Source)new ForwardingSource(paramSource) {
        final ResponseProgressBody this$0;
        
        long totalBytesRead;
        
        public long read(Buffer param1Buffer, long param1Long) throws IOException {
          long l1 = super.read(param1Buffer, param1Long);
          long l2 = this.totalBytesRead;
          if (l1 != -1L) {
            param1Long = l1;
          } else {
            param1Long = 0L;
          } 
          this.totalBytesRead = l2 + param1Long;
          if (ResponseProgressBody.this.downloadProgressHandler != null)
            ResponseProgressBody.this.downloadProgressHandler.obtainMessage(1, new Progress(this.totalBytesRead, ResponseProgressBody.this.mResponseBody.contentLength())).sendToTarget(); 
          return l1;
        }
      };
  }
  
  public long contentLength() {
    return this.mResponseBody.contentLength();
  }
  
  public MediaType contentType() {
    return this.mResponseBody.contentType();
  }
  
  public BufferedSource source() {
    if (this.bufferedSource == null)
      this.bufferedSource = Okio.buffer(source((Source)this.mResponseBody.source())); 
    return this.bufferedSource;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\ResponseProgressBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */