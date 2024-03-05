package okhttp3.internal.http2;

import java.io.IOException;

public final class StreamResetException extends IOException {
  public final ErrorCode errorCode;
  
  public StreamResetException(ErrorCode paramErrorCode) {
    super(stringBuilder.toString());
    this.errorCode = paramErrorCode;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\StreamResetException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */