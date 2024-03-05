package okhttp3.internal.http2;

public enum ErrorCode {
  CANCEL,
  COMPRESSION_ERROR,
  CONNECT_ERROR,
  ENHANCE_YOUR_CALM,
  FLOW_CONTROL_ERROR,
  HTTP_1_1_REQUIRED,
  INADEQUATE_SECURITY,
  INTERNAL_ERROR,
  NO_ERROR(0),
  PROTOCOL_ERROR(1),
  REFUSED_STREAM(1);
  
  private static final ErrorCode[] $VALUES;
  
  public final int httpCode;
  
  static {
    INTERNAL_ERROR = new ErrorCode("INTERNAL_ERROR", 2, 2);
    FLOW_CONTROL_ERROR = new ErrorCode("FLOW_CONTROL_ERROR", 3, 3);
    REFUSED_STREAM = new ErrorCode("REFUSED_STREAM", 4, 7);
    CANCEL = new ErrorCode("CANCEL", 5, 8);
    COMPRESSION_ERROR = new ErrorCode("COMPRESSION_ERROR", 6, 9);
    CONNECT_ERROR = new ErrorCode("CONNECT_ERROR", 7, 10);
    ENHANCE_YOUR_CALM = new ErrorCode("ENHANCE_YOUR_CALM", 8, 11);
    INADEQUATE_SECURITY = new ErrorCode("INADEQUATE_SECURITY", 9, 12);
    ErrorCode errorCode = new ErrorCode("HTTP_1_1_REQUIRED", 10, 13);
    HTTP_1_1_REQUIRED = errorCode;
    $VALUES = new ErrorCode[] { 
        NO_ERROR, PROTOCOL_ERROR, INTERNAL_ERROR, FLOW_CONTROL_ERROR, REFUSED_STREAM, CANCEL, COMPRESSION_ERROR, CONNECT_ERROR, ENHANCE_YOUR_CALM, INADEQUATE_SECURITY, 
        errorCode };
  }
  
  ErrorCode(int paramInt1) {
    this.httpCode = paramInt1;
  }
  
  public static ErrorCode fromHttp2(int paramInt) {
    for (ErrorCode errorCode : values()) {
      if (errorCode.httpCode == paramInt)
        return errorCode; 
    } 
    return null;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\ErrorCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */