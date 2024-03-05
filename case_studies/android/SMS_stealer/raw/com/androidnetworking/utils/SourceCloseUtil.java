package com.androidnetworking.utils;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ResponseType;
import okhttp3.Response;

public final class SourceCloseUtil {
  public static void close(Response paramResponse, ANRequest paramANRequest) {
    if (paramANRequest.getResponseAs() != ResponseType.OK_HTTP_RESPONSE && paramResponse != null && paramResponse.body() != null && paramResponse.body().source() != null)
      try {
        paramResponse.body().source().close();
      } catch (Exception exception) {} 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworkin\\utils\SourceCloseUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */