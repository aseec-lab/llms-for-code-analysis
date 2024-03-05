package com.androidnetworking.internal;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.ResponseType;
import com.androidnetworking.error.ANError;
import com.androidnetworking.utils.SourceCloseUtil;
import com.androidnetworking.utils.Utils;
import okhttp3.Response;

public final class SynchronousCall {
  public static <T> ANResponse<T> execute(ANRequest paramANRequest) {
    int i = paramANRequest.getRequestType();
    return (i != 0) ? ((i != 1) ? ((i != 2) ? new ANResponse(new ANError()) : executeUploadRequest(paramANRequest)) : executeDownloadRequest(paramANRequest)) : executeSimpleRequest(paramANRequest);
  }
  
  private static <T> ANResponse<T> executeDownloadRequest(ANRequest paramANRequest) {
    try {
      ANError aNError;
      Response response = InternalNetworking.performDownloadRequest(paramANRequest);
      if (response == null) {
        aNError = new ANError();
        this();
        return new ANResponse(Utils.getErrorForConnection(aNError));
      } 
      if (response.code() >= 400) {
        ANResponse<T> aNResponse1 = new ANResponse();
        ANError aNError1 = new ANError();
        this(response);
        this(Utils.getErrorForServerResponse(aNError1, (ANRequest)aNError, response.code()));
        aNResponse1.setOkHttpResponse(response);
        return aNResponse1;
      } 
      ANResponse<T> aNResponse = new ANResponse();
      this("success");
      aNResponse.setOkHttpResponse(response);
      return aNResponse;
    } catch (ANError aNError) {
      return new ANResponse(Utils.getErrorForConnection(new ANError((Throwable)aNError)));
    } catch (Exception exception) {
      return new ANResponse(Utils.getErrorForConnection(new ANError(exception)));
    } 
  }
  
  private static <T> ANResponse<T> executeSimpleRequest(ANRequest paramANRequest) {
    ANResponse<T> aNResponse1;
    ANResponse<T> aNResponse2;
    Response response2 = null;
    Response response3 = null;
    Response response1 = null;
    try {
      Response response = InternalNetworking.performSimpleRequest(paramANRequest);
      if (response == null) {
        response1 = response;
        response2 = response;
        response3 = response;
        ANError aNError = new ANError();
        response1 = response;
        response2 = response;
        response3 = response;
        this();
        response1 = response;
        response2 = response;
        response3 = response;
        ANResponse<T> aNResponse3 = new ANResponse(Utils.getErrorForConnection(aNError));
        SourceCloseUtil.close(response, paramANRequest);
        return aNResponse3;
      } 
      response1 = response;
      response2 = response;
      response3 = response;
      if (paramANRequest.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
        response1 = response;
        response2 = response;
        response3 = response;
        ANResponse<T> aNResponse3 = new ANResponse();
        response1 = response;
        response2 = response;
        response3 = response;
        this(response);
        response1 = response;
        response2 = response;
        response3 = response;
        aNResponse3.setOkHttpResponse(response);
        SourceCloseUtil.close(response, paramANRequest);
        return aNResponse3;
      } 
      response1 = response;
      response2 = response;
      response3 = response;
      if (response.code() >= 400) {
        response1 = response;
        response2 = response;
        response3 = response;
        ANResponse<T> aNResponse3 = new ANResponse();
        response1 = response;
        response2 = response;
        response3 = response;
        ANError aNError = new ANError();
        response1 = response;
        response2 = response;
        response3 = response;
        this(response);
        response1 = response;
        response2 = response;
        response3 = response;
        this(Utils.getErrorForServerResponse(aNError, paramANRequest, response.code()));
        response1 = response;
        response2 = response;
        response3 = response;
        aNResponse3.setOkHttpResponse(response);
        SourceCloseUtil.close(response, paramANRequest);
        return aNResponse3;
      } 
      response1 = response;
      response2 = response;
      response3 = response;
      ANResponse<T> aNResponse = paramANRequest.parseResponse(response);
      response1 = response;
      response2 = response;
      response3 = response;
      aNResponse.setOkHttpResponse(response);
      SourceCloseUtil.close(response, paramANRequest);
      return aNResponse;
    } catch (ANError aNError2) {
      response1 = response3;
      ANError aNError1 = new ANError();
      response1 = response3;
      this((Throwable)aNError2);
      response1 = response3;
      aNResponse2 = new ANResponse(Utils.getErrorForConnection(aNError1));
      SourceCloseUtil.close(response3, paramANRequest);
      return aNResponse2;
    } catch (Exception exception) {
      aNResponse1 = aNResponse2;
      ANError aNError = new ANError();
      aNResponse1 = aNResponse2;
      this(exception);
      aNResponse1 = aNResponse2;
      ANResponse<T> aNResponse = new ANResponse(Utils.getErrorForConnection(aNError));
      SourceCloseUtil.close((Response)aNResponse2, paramANRequest);
      return aNResponse;
    } finally {}
    SourceCloseUtil.close((Response)aNResponse1, paramANRequest);
    throw aNResponse2;
  }
  
  private static <T> ANResponse<T> executeUploadRequest(ANRequest paramANRequest) {
    ANResponse<T> aNResponse1;
    ANResponse<T> aNResponse2;
    Response response2 = null;
    Response response3 = null;
    Response response1 = null;
    try {
      Response response = InternalNetworking.performUploadRequest(paramANRequest);
      if (response == null) {
        response1 = response;
        response2 = response;
        response3 = response;
        ANError aNError = new ANError();
        response1 = response;
        response2 = response;
        response3 = response;
        this();
        response1 = response;
        response2 = response;
        response3 = response;
        ANResponse<T> aNResponse3 = new ANResponse(Utils.getErrorForConnection(aNError));
        SourceCloseUtil.close(response, paramANRequest);
        return aNResponse3;
      } 
      response1 = response;
      response2 = response;
      response3 = response;
      if (paramANRequest.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
        response1 = response;
        response2 = response;
        response3 = response;
        ANResponse<T> aNResponse3 = new ANResponse();
        response1 = response;
        response2 = response;
        response3 = response;
        this(response);
        response1 = response;
        response2 = response;
        response3 = response;
        aNResponse3.setOkHttpResponse(response);
        SourceCloseUtil.close(response, paramANRequest);
        return aNResponse3;
      } 
      response1 = response;
      response2 = response;
      response3 = response;
      if (response.code() >= 400) {
        response1 = response;
        response2 = response;
        response3 = response;
        ANResponse<T> aNResponse3 = new ANResponse();
        response1 = response;
        response2 = response;
        response3 = response;
        ANError aNError = new ANError();
        response1 = response;
        response2 = response;
        response3 = response;
        this(response);
        response1 = response;
        response2 = response;
        response3 = response;
        this(Utils.getErrorForServerResponse(aNError, paramANRequest, response.code()));
        response1 = response;
        response2 = response;
        response3 = response;
        aNResponse3.setOkHttpResponse(response);
        SourceCloseUtil.close(response, paramANRequest);
        return aNResponse3;
      } 
      response1 = response;
      response2 = response;
      response3 = response;
      ANResponse<T> aNResponse = paramANRequest.parseResponse(response);
      response1 = response;
      response2 = response;
      response3 = response;
      aNResponse.setOkHttpResponse(response);
      SourceCloseUtil.close(response, paramANRequest);
      return aNResponse;
    } catch (ANError aNError) {
      response1 = response3;
      aNResponse2 = new ANResponse(Utils.getErrorForConnection(aNError));
      SourceCloseUtil.close(response3, paramANRequest);
      return aNResponse2;
    } catch (Exception exception) {
      aNResponse1 = aNResponse2;
      ANError aNError = new ANError();
      aNResponse1 = aNResponse2;
      this(exception);
      aNResponse1 = aNResponse2;
      ANResponse<T> aNResponse = new ANResponse(Utils.getErrorForConnection(aNError));
      SourceCloseUtil.close((Response)aNResponse2, paramANRequest);
      return aNResponse;
    } finally {}
    SourceCloseUtil.close((Response)aNResponse1, paramANRequest);
    throw aNResponse2;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\SynchronousCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */