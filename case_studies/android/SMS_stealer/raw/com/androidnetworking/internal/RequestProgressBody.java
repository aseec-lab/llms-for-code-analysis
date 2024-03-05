package com.androidnetworking.internal;

import com.androidnetworking.interfaces.UploadProgressListener;
import com.androidnetworking.model.Progress;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class RequestProgressBody extends RequestBody {
  private BufferedSink bufferedSink;
  
  private final RequestBody requestBody;
  
  private UploadProgressHandler uploadProgressHandler;
  
  public RequestProgressBody(RequestBody paramRequestBody, UploadProgressListener paramUploadProgressListener) {
    this.requestBody = paramRequestBody;
    if (paramUploadProgressListener != null)
      this.uploadProgressHandler = new UploadProgressHandler(paramUploadProgressListener); 
  }
  
  private Sink sink(Sink paramSink) {
    return (Sink)new ForwardingSink(paramSink) {
        long bytesWritten = 0L;
        
        long contentLength = 0L;
        
        final RequestProgressBody this$0;
        
        public void write(Buffer param1Buffer, long param1Long) throws IOException {
          super.write(param1Buffer, param1Long);
          if (this.contentLength == 0L)
            this.contentLength = RequestProgressBody.this.contentLength(); 
          this.bytesWritten += param1Long;
          if (RequestProgressBody.this.uploadProgressHandler != null)
            RequestProgressBody.this.uploadProgressHandler.obtainMessage(1, new Progress(this.bytesWritten, this.contentLength)).sendToTarget(); 
        }
      };
  }
  
  public long contentLength() throws IOException {
    return this.requestBody.contentLength();
  }
  
  public MediaType contentType() {
    return this.requestBody.contentType();
  }
  
  public void writeTo(BufferedSink paramBufferedSink) throws IOException {
    if (this.bufferedSink == null)
      this.bufferedSink = Okio.buffer(sink((Sink)paramBufferedSink)); 
    this.requestBody.writeTo(this.bufferedSink);
    this.bufferedSink.flush();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\RequestProgressBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */