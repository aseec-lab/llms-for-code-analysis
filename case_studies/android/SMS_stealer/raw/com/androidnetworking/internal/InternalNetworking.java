package com.androidnetworking.internal;

import android.content.Context;
import android.net.TrafficStats;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ConnectionClassManager;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class InternalNetworking {
  public static OkHttpClient sHttpClient = getClient();
  
  public static String sUserAgent = null;
  
  public static void addHeadersToRequestBuilder(Request.Builder paramBuilder, ANRequest paramANRequest) {
    if (paramANRequest.getUserAgent() != null) {
      paramBuilder.addHeader("User-Agent", paramANRequest.getUserAgent());
    } else {
      String str = sUserAgent;
      if (str != null) {
        paramANRequest.setUserAgent(str);
        paramBuilder.addHeader("User-Agent", sUserAgent);
      } 
    } 
    Headers headers = paramANRequest.getHeaders();
    if (headers != null) {
      paramBuilder.headers(headers);
      if (paramANRequest.getUserAgent() != null && !headers.names().contains("User-Agent"))
        paramBuilder.addHeader("User-Agent", paramANRequest.getUserAgent()); 
    } 
  }
  
  public static void enableLogging(HttpLoggingInterceptor.Level paramLevel) {
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(paramLevel);
    sHttpClient = getClient().newBuilder().addInterceptor((Interceptor)httpLoggingInterceptor).build();
  }
  
  public static OkHttpClient getClient() {
    OkHttpClient okHttpClient2 = sHttpClient;
    OkHttpClient okHttpClient1 = okHttpClient2;
    if (okHttpClient2 == null)
      okHttpClient1 = getDefaultClient(); 
    return okHttpClient1;
  }
  
  public static OkHttpClient getDefaultClient() {
    return (new OkHttpClient()).newBuilder().connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).build();
  }
  
  public static Response performDownloadRequest(ANRequest paramANRequest) throws ANError {
    try {
      OkHttpClient okHttpClient;
      Request.Builder builder = new Request.Builder();
      this();
      builder = builder.url(paramANRequest.getUrl());
      addHeadersToRequestBuilder(builder, paramANRequest);
      builder = builder.get();
      if (paramANRequest.getCacheControl() != null)
        builder.cacheControl(paramANRequest.getCacheControl()); 
      Request request = builder.build();
      if (paramANRequest.getOkHttpClient() != null) {
        OkHttpClient.Builder builder1 = paramANRequest.getOkHttpClient().newBuilder().cache(sHttpClient.cache());
        Interceptor interceptor = new Interceptor() {
            final ANRequest val$request;
            
            public Response intercept(Interceptor.Chain param1Chain) throws IOException {
              Response response = param1Chain.proceed(param1Chain.request());
              return response.newBuilder().body(new ResponseProgressBody(response.body(), request.getDownloadProgressListener())).build();
            }
          };
        super(paramANRequest);
        okHttpClient = builder1.addNetworkInterceptor(interceptor).build();
      } else {
        OkHttpClient.Builder builder1 = sHttpClient.newBuilder();
        Interceptor interceptor = new Interceptor() {
            final ANRequest val$request;
            
            public Response intercept(Interceptor.Chain param1Chain) throws IOException {
              Response response = param1Chain.proceed(param1Chain.request());
              return response.newBuilder().body(new ResponseProgressBody(response.body(), request.getDownloadProgressListener())).build();
            }
          };
        super(paramANRequest);
        okHttpClient = builder1.addNetworkInterceptor(interceptor).build();
      } 
      paramANRequest.setCall(okHttpClient.newCall(request));
      long l2 = System.currentTimeMillis();
      long l1 = TrafficStats.getTotalRxBytes();
      Response response = paramANRequest.getCall().execute();
      Utils.saveFile(response, paramANRequest.getDirPath(), paramANRequest.getFileName());
      l2 = System.currentTimeMillis() - l2;
      if (response.cacheResponse() == null) {
        long l = TrafficStats.getTotalRxBytes();
        if (l1 == -1L || l == -1L) {
          l1 = response.body().contentLength();
        } else {
          l1 = l - l1;
        } 
        ConnectionClassManager.getInstance().updateBandwidth(l1, l2);
        Utils.sendAnalytics(paramANRequest.getAnalyticsListener(), l2, -1L, response.body().contentLength(), false);
      } else if (paramANRequest.getAnalyticsListener() != null) {
        Utils.sendAnalytics(paramANRequest.getAnalyticsListener(), l2, -1L, 0L, true);
      } 
      return response;
    } catch (IOException iOException) {
      try {
        File file = new File();
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append(paramANRequest.getDirPath());
        stringBuilder.append(File.separator);
        stringBuilder.append(paramANRequest.getFileName());
        this(stringBuilder.toString());
        if (file.exists())
          file.delete(); 
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
      throw new ANError(iOException);
    } 
  }
  
  public static Response performSimpleRequest(ANRequest paramANRequest) throws ANError {
    try {
      AnalyticsListener analyticsListener;
      Request.Builder builder = new Request.Builder();
      this();
      builder = builder.url(paramANRequest.getUrl());
      addHeadersToRequestBuilder(builder, paramANRequest);
      int i = paramANRequest.getMethod();
      RequestBody requestBody = null;
      switch (i) {
        case 6:
          builder = builder.method("OPTIONS", null);
          break;
        case 5:
          requestBody = paramANRequest.getRequestBody();
          builder = builder.patch(requestBody);
          break;
        case 4:
          builder = builder.head();
          break;
        case 3:
          requestBody = paramANRequest.getRequestBody();
          builder = builder.delete(requestBody);
          break;
        case 2:
          requestBody = paramANRequest.getRequestBody();
          builder = builder.put(requestBody);
          break;
        case 1:
          requestBody = paramANRequest.getRequestBody();
          builder = builder.post(requestBody);
          break;
        case 0:
          builder = builder.get();
          break;
      } 
      if (paramANRequest.getCacheControl() != null)
        builder.cacheControl(paramANRequest.getCacheControl()); 
      Request request = builder.build();
      if (paramANRequest.getOkHttpClient() != null) {
        paramANRequest.setCall(paramANRequest.getOkHttpClient().newBuilder().cache(sHttpClient.cache()).build().newCall(request));
      } else {
        paramANRequest.setCall(sHttpClient.newCall(request));
      } 
      long l1 = System.currentTimeMillis();
      long l2 = TrafficStats.getTotalRxBytes();
      Response response1 = paramANRequest.getCall().execute();
      long l3 = System.currentTimeMillis() - l1;
      Response response2 = response1.cacheResponse();
      l1 = -1L;
      if (response2 == null) {
        long l = TrafficStats.getTotalRxBytes();
        if (l2 == -1L || l == -1L) {
          l2 = response1.body().contentLength();
        } else {
          l2 = l - l2;
        } 
        ConnectionClassManager.getInstance().updateBandwidth(l2, l3);
        analyticsListener = paramANRequest.getAnalyticsListener();
        l2 = l1;
        if (requestBody != null) {
          l2 = l1;
          if (requestBody.contentLength() != 0L)
            l2 = requestBody.contentLength(); 
        } 
        Utils.sendAnalytics(analyticsListener, l3, l2, response1.body().contentLength(), false);
      } else if (analyticsListener.getAnalyticsListener() != null) {
        if (response1.networkResponse() == null) {
          Utils.sendAnalytics(analyticsListener.getAnalyticsListener(), l3, 0L, 0L, true);
        } else {
          analyticsListener = analyticsListener.getAnalyticsListener();
          l2 = l1;
          if (requestBody != null) {
            l2 = l1;
            if (requestBody.contentLength() != 0L)
              l2 = requestBody.contentLength(); 
          } 
          Utils.sendAnalytics(analyticsListener, l3, l2, 0L, true);
        } 
      } 
      return response1;
    } catch (IOException iOException) {
      throw new ANError(iOException);
    } 
  }
  
  public static Response performUploadRequest(ANRequest paramANRequest) throws ANError {
    try {
      Request.Builder builder = new Request.Builder();
      this();
      builder = builder.url(paramANRequest.getUrl());
      addHeadersToRequestBuilder(builder, paramANRequest);
      RequestBody requestBody = paramANRequest.getMultiPartRequestBody();
      long l1 = requestBody.contentLength();
      RequestProgressBody requestProgressBody = new RequestProgressBody();
      this(requestBody, paramANRequest.getUploadProgressListener());
      builder = builder.post(requestProgressBody);
      if (paramANRequest.getCacheControl() != null)
        builder.cacheControl(paramANRequest.getCacheControl()); 
      Request request = builder.build();
      if (paramANRequest.getOkHttpClient() != null) {
        paramANRequest.setCall(paramANRequest.getOkHttpClient().newBuilder().cache(sHttpClient.cache()).build().newCall(request));
      } else {
        paramANRequest.setCall(sHttpClient.newCall(request));
      } 
      long l2 = System.currentTimeMillis();
      Response response = paramANRequest.getCall().execute();
      l2 = System.currentTimeMillis() - l2;
      if (paramANRequest.getAnalyticsListener() != null)
        if (response.cacheResponse() == null) {
          Utils.sendAnalytics(paramANRequest.getAnalyticsListener(), l2, l1, response.body().contentLength(), false);
        } else if (response.networkResponse() == null) {
          Utils.sendAnalytics(paramANRequest.getAnalyticsListener(), l2, 0L, 0L, true);
        } else {
          AnalyticsListener analyticsListener = paramANRequest.getAnalyticsListener();
          if (l1 == 0L)
            l1 = -1L; 
          Utils.sendAnalytics(analyticsListener, l2, l1, 0L, true);
        }  
      return response;
    } catch (IOException iOException) {
      throw new ANError(iOException);
    } 
  }
  
  public static void setClient(OkHttpClient paramOkHttpClient) {
    sHttpClient = paramOkHttpClient;
  }
  
  public static void setClientWithCache(Context paramContext) {
    sHttpClient = (new OkHttpClient()).newBuilder().cache(Utils.getCache(paramContext, 10485760, "cache_an")).connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).build();
  }
  
  public static void setUserAgent(String paramString) {
    sUserAgent = paramString;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\InternalNetworking.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */