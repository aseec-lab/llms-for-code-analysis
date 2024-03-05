package com.androidnetworking.error;

import com.androidnetworking.utils.ParseUtil;
import okhttp3.Response;

public class ANError extends Exception {
  private String errorBody;
  
  private int errorCode = 0;
  
  private String errorDetail;
  
  private Response response;
  
  public ANError() {}
  
  public ANError(String paramString) {
    super(paramString);
  }
  
  public ANError(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
  }
  
  public ANError(String paramString, Response paramResponse) {
    super(paramString);
    this.response = paramResponse;
  }
  
  public ANError(String paramString, Response paramResponse, Throwable paramThrowable) {
    super(paramString, paramThrowable);
    this.response = paramResponse;
  }
  
  public ANError(Throwable paramThrowable) {
    super(paramThrowable);
  }
  
  public ANError(Response paramResponse) {
    this.response = paramResponse;
  }
  
  public ANError(Response paramResponse, Throwable paramThrowable) {
    super(paramThrowable);
    this.response = paramResponse;
  }
  
  public <T> T getErrorAsObject(Class<T> paramClass) {
    try {
      return (T)ParseUtil.getParserFactory().getObject(this.errorBody, paramClass);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public String getErrorBody() {
    return this.errorBody;
  }
  
  public int getErrorCode() {
    return this.errorCode;
  }
  
  public String getErrorDetail() {
    return this.errorDetail;
  }
  
  public Response getResponse() {
    return this.response;
  }
  
  public void setCancellationMessageInError() {
    this.errorDetail = "requestCancelledError";
  }
  
  public void setErrorBody(String paramString) {
    this.errorBody = paramString;
  }
  
  public void setErrorCode(int paramInt) {
    this.errorCode = paramInt;
  }
  
  public void setErrorDetail(String paramString) {
    this.errorDetail = paramString;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\error\ANError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */