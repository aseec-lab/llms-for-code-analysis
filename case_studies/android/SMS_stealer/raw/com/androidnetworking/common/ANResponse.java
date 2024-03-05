package com.androidnetworking.common;

import com.androidnetworking.error.ANError;
import okhttp3.Response;

public class ANResponse<T> {
  private final ANError mANError;
  
  private final T mResult = null;
  
  private Response response;
  
  public ANResponse(ANError paramANError) {
    this.mANError = paramANError;
  }
  
  public ANResponse(T paramT) {
    this.mANError = null;
  }
  
  public static <T> ANResponse<T> failed(ANError paramANError) {
    return new ANResponse<T>(paramANError);
  }
  
  public static <T> ANResponse<T> success(T paramT) {
    return new ANResponse<T>(paramT);
  }
  
  public ANError getError() {
    return this.mANError;
  }
  
  public Response getOkHttpResponse() {
    return this.response;
  }
  
  public T getResult() {
    return this.mResult;
  }
  
  public boolean isSuccess() {
    boolean bool;
    if (this.mANError == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void setOkHttpResponse(Response paramResponse) {
    this.response = paramResponse;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\common\ANResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */