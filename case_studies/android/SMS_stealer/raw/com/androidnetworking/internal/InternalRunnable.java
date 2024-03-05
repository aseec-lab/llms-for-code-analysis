package com.androidnetworking.internal;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.common.ResponseType;
import com.androidnetworking.core.Core;
import com.androidnetworking.error.ANError;
import com.androidnetworking.utils.SourceCloseUtil;
import com.androidnetworking.utils.Utils;
import okhttp3.Response;

public class InternalRunnable implements Runnable {
  private final Priority priority;
  
  public final ANRequest request;
  
  public final int sequence;
  
  public InternalRunnable(ANRequest paramANRequest) {
    this.request = paramANRequest;
    this.sequence = paramANRequest.getSequenceNumber();
    this.priority = paramANRequest.getPriority();
  }
  
  private void deliverError(final ANRequest request, final ANError anError) {
    Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
          final InternalRunnable this$0;
          
          final ANError val$anError;
          
          final ANRequest val$request;
          
          public void run() {
            request.deliverError(anError);
            request.finish();
          }
        });
  }
  
  private void executeDownloadRequest() {
    try {
      Response response = InternalNetworking.performDownloadRequest(this.request);
      if (response == null) {
        ANRequest aNRequest = this.request;
        ANError aNError = new ANError();
        this();
        deliverError(aNRequest, Utils.getErrorForConnection(aNError));
        return;
      } 
      if (response.code() >= 400) {
        ANRequest aNRequest = this.request;
        ANError aNError = new ANError();
        this(response);
        deliverError(aNRequest, Utils.getErrorForServerResponse(aNError, this.request, response.code()));
        return;
      } 
      this.request.updateDownloadCompletion();
    } catch (Exception exception) {
      deliverError(this.request, Utils.getErrorForConnection(new ANError(exception)));
    } 
  }
  
  private void executeSimpleRequest() {
    Response response1 = null;
    Response response2 = null;
    try {
      Response response = InternalNetworking.performSimpleRequest(this.request);
      if (response == null) {
        response2 = response;
        response1 = response;
        ANRequest aNRequest = this.request;
        response2 = response;
        response1 = response;
        ANError aNError = new ANError();
        response2 = response;
        response1 = response;
        this();
        response2 = response;
        response1 = response;
        deliverError(aNRequest, Utils.getErrorForConnection(aNError));
      } else {
        response2 = response;
        response1 = response;
        if (this.request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
          response2 = response;
          response1 = response;
          this.request.deliverOkHttpResponse(response);
        } else {
          response2 = response;
          response1 = response;
          if (response.code() >= 400) {
            response2 = response;
            response1 = response;
            ANRequest aNRequest = this.request;
            response2 = response;
            response1 = response;
            ANError aNError = new ANError();
            response2 = response;
            response1 = response;
            this(response);
            response2 = response;
            response1 = response;
            deliverError(aNRequest, Utils.getErrorForServerResponse(aNError, this.request, response.code()));
          } else {
            response2 = response;
            response1 = response;
            ANResponse aNResponse = this.request.parseResponse(response);
            response2 = response;
            response1 = response;
            if (!aNResponse.isSuccess()) {
              response2 = response;
              response1 = response;
              deliverError(this.request, aNResponse.getError());
            } else {
              response2 = response;
              response1 = response;
              aNResponse.setOkHttpResponse(response);
              response2 = response;
              response1 = response;
              this.request.deliverResponse(aNResponse);
              response1 = response;
              SourceCloseUtil.close(response1, this.request);
            } 
          } 
        } 
      } 
      SourceCloseUtil.close(response, this.request);
      return;
    } catch (Exception exception) {
      response2 = response1;
      ANRequest aNRequest = this.request;
      response2 = response1;
      ANError aNError = new ANError();
      response2 = response1;
      this(exception);
      response2 = response1;
      deliverError(aNRequest, Utils.getErrorForConnection(aNError));
    } finally {}
    SourceCloseUtil.close(response1, this.request);
  }
  
  private void executeUploadRequest() {
    Response response1 = null;
    Response response2 = null;
    try {
      Response response = InternalNetworking.performUploadRequest(this.request);
      if (response == null) {
        response2 = response;
        response1 = response;
        ANRequest aNRequest = this.request;
        response2 = response;
        response1 = response;
        ANError aNError = new ANError();
        response2 = response;
        response1 = response;
        this();
        response2 = response;
        response1 = response;
        deliverError(aNRequest, Utils.getErrorForConnection(aNError));
      } else {
        response2 = response;
        response1 = response;
        if (this.request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
          response2 = response;
          response1 = response;
          this.request.deliverOkHttpResponse(response);
        } else {
          response2 = response;
          response1 = response;
          if (response.code() >= 400) {
            response2 = response;
            response1 = response;
            ANRequest aNRequest = this.request;
            response2 = response;
            response1 = response;
            ANError aNError = new ANError();
            response2 = response;
            response1 = response;
            this(response);
            response2 = response;
            response1 = response;
            deliverError(aNRequest, Utils.getErrorForServerResponse(aNError, this.request, response.code()));
          } else {
            response2 = response;
            response1 = response;
            ANResponse aNResponse = this.request.parseResponse(response);
            response2 = response;
            response1 = response;
            if (!aNResponse.isSuccess()) {
              response2 = response;
              response1 = response;
              deliverError(this.request, aNResponse.getError());
            } else {
              response2 = response;
              response1 = response;
              aNResponse.setOkHttpResponse(response);
              response2 = response;
              response1 = response;
              this.request.deliverResponse(aNResponse);
              response1 = response;
              SourceCloseUtil.close(response1, this.request);
            } 
          } 
        } 
      } 
      SourceCloseUtil.close(response, this.request);
      return;
    } catch (Exception exception) {
      response2 = response1;
      ANRequest aNRequest = this.request;
      response2 = response1;
      ANError aNError = new ANError();
      response2 = response1;
      this(exception);
      response2 = response1;
      deliverError(aNRequest, Utils.getErrorForConnection(aNError));
    } finally {}
    SourceCloseUtil.close(response1, this.request);
  }
  
  public Priority getPriority() {
    return this.priority;
  }
  
  public void run() {
    this.request.setRunning(true);
    int i = this.request.getRequestType();
    if (i != 0) {
      if (i != 1) {
        if (i == 2)
          executeUploadRequest(); 
      } else {
        executeDownloadRequest();
      } 
    } else {
      executeSimpleRequest();
    } 
    this.request.setRunning(false);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\InternalRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */