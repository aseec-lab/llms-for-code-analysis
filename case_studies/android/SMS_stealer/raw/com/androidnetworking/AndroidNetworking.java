package com.androidnetworking;

import android.content.Context;
import android.graphics.BitmapFactory;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ConnectionClassManager;
import com.androidnetworking.common.ConnectionQuality;
import com.androidnetworking.core.Core;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.ConnectionQualityChangeListener;
import com.androidnetworking.interfaces.Parser;
import com.androidnetworking.internal.ANImageLoader;
import com.androidnetworking.internal.ANRequestQueue;
import com.androidnetworking.internal.InternalNetworking;
import com.androidnetworking.utils.ParseUtil;
import com.androidnetworking.utils.Utils;
import okhttp3.OkHttpClient;

public class AndroidNetworking {
  public static void cancel(Object paramObject) {
    ANRequestQueue.getInstance().cancelRequestWithGivenTag(paramObject, false);
  }
  
  public static void cancelAll() {
    ANRequestQueue.getInstance().cancelAll(false);
  }
  
  public static ANRequest.DeleteRequestBuilder delete(String paramString) {
    return new ANRequest.DeleteRequestBuilder(paramString);
  }
  
  public static ANRequest.DownloadBuilder download(String paramString1, String paramString2, String paramString3) {
    return new ANRequest.DownloadBuilder(paramString1, paramString2, paramString3);
  }
  
  public static void enableLogging() {
    enableLogging(HttpLoggingInterceptor.Level.BASIC);
  }
  
  public static void enableLogging(HttpLoggingInterceptor.Level paramLevel) {
    InternalNetworking.enableLogging(paramLevel);
  }
  
  public static void evictAllBitmap() {
    ANImageLoader.ImageCache imageCache = ANImageLoader.getInstance().getImageCache();
    if (imageCache != null)
      imageCache.evictAllBitmap(); 
  }
  
  public static void evictBitmap(String paramString) {
    ANImageLoader.ImageCache imageCache = ANImageLoader.getInstance().getImageCache();
    if (imageCache != null && paramString != null)
      imageCache.evictBitmap(paramString); 
  }
  
  public static void forceCancel(Object paramObject) {
    ANRequestQueue.getInstance().cancelRequestWithGivenTag(paramObject, true);
  }
  
  public static void forceCancelAll() {
    ANRequestQueue.getInstance().cancelAll(true);
  }
  
  public static ANRequest.GetRequestBuilder get(String paramString) {
    return new ANRequest.GetRequestBuilder(paramString);
  }
  
  public static int getCurrentBandwidth() {
    return ConnectionClassManager.getInstance().getCurrentBandwidth();
  }
  
  public static ConnectionQuality getCurrentConnectionQuality() {
    return ConnectionClassManager.getInstance().getCurrentConnectionQuality();
  }
  
  public static ANRequest.HeadRequestBuilder head(String paramString) {
    return new ANRequest.HeadRequestBuilder(paramString);
  }
  
  public static void initialize(Context paramContext) {
    InternalNetworking.setClientWithCache(paramContext.getApplicationContext());
    ANRequestQueue.initialize();
    ANImageLoader.initialize();
  }
  
  public static void initialize(Context paramContext, OkHttpClient paramOkHttpClient) {
    OkHttpClient okHttpClient = paramOkHttpClient;
    if (paramOkHttpClient != null) {
      okHttpClient = paramOkHttpClient;
      if (paramOkHttpClient.cache() == null)
        okHttpClient = paramOkHttpClient.newBuilder().cache(Utils.getCache(paramContext.getApplicationContext(), 10485760, "cache_an")).build(); 
    } 
    InternalNetworking.setClient(okHttpClient);
    ANRequestQueue.initialize();
    ANImageLoader.initialize();
  }
  
  public static boolean isRequestRunning(Object paramObject) {
    return ANRequestQueue.getInstance().isRequestRunning(paramObject);
  }
  
  public static ANRequest.OptionsRequestBuilder options(String paramString) {
    return new ANRequest.OptionsRequestBuilder(paramString);
  }
  
  public static ANRequest.PatchRequestBuilder patch(String paramString) {
    return new ANRequest.PatchRequestBuilder(paramString);
  }
  
  public static ANRequest.PostRequestBuilder post(String paramString) {
    return new ANRequest.PostRequestBuilder(paramString);
  }
  
  public static ANRequest.PutRequestBuilder put(String paramString) {
    return new ANRequest.PutRequestBuilder(paramString);
  }
  
  public static void removeConnectionQualityChangeListener() {
    ConnectionClassManager.getInstance().removeListener();
  }
  
  public static ANRequest.DynamicRequestBuilder request(String paramString, int paramInt) {
    return new ANRequest.DynamicRequestBuilder(paramString, paramInt);
  }
  
  public static void setBitmapDecodeOptions(BitmapFactory.Options paramOptions) {
    if (paramOptions != null)
      ANImageLoader.getInstance().setBitmapDecodeOptions(paramOptions); 
  }
  
  public static void setConnectionQualityChangeListener(ConnectionQualityChangeListener paramConnectionQualityChangeListener) {
    ConnectionClassManager.getInstance().setListener(paramConnectionQualityChangeListener);
  }
  
  public static void setParserFactory(Parser.Factory paramFactory) {
    ParseUtil.setParserFactory(paramFactory);
  }
  
  public static void setUserAgent(String paramString) {
    InternalNetworking.setUserAgent(paramString);
  }
  
  public static void shutDown() {
    Core.shutDown();
    evictAllBitmap();
    ConnectionClassManager.getInstance().removeListener();
    ConnectionClassManager.shutDown();
    ParseUtil.shutDown();
  }
  
  public static ANRequest.MultiPartBuilder upload(String paramString) {
    return new ANRequest.MultiPartBuilder(paramString);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\AndroidNetworking.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */