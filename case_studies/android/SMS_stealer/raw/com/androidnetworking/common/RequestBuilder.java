package com.androidnetworking.common;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

public interface RequestBuilder {
  RequestBuilder addHeaders(Object paramObject);
  
  RequestBuilder addHeaders(String paramString1, String paramString2);
  
  RequestBuilder addHeaders(Map<String, String> paramMap);
  
  RequestBuilder addPathParameter(Object paramObject);
  
  RequestBuilder addPathParameter(String paramString1, String paramString2);
  
  RequestBuilder addPathParameter(Map<String, String> paramMap);
  
  RequestBuilder addQueryParameter(Object paramObject);
  
  RequestBuilder addQueryParameter(String paramString1, String paramString2);
  
  RequestBuilder addQueryParameter(Map<String, String> paramMap);
  
  RequestBuilder doNotCacheResponse();
  
  RequestBuilder getResponseOnlyFromNetwork();
  
  RequestBuilder getResponseOnlyIfCached();
  
  RequestBuilder setExecutor(Executor paramExecutor);
  
  RequestBuilder setMaxAgeCacheControl(int paramInt, TimeUnit paramTimeUnit);
  
  RequestBuilder setMaxStaleCacheControl(int paramInt, TimeUnit paramTimeUnit);
  
  RequestBuilder setOkHttpClient(OkHttpClient paramOkHttpClient);
  
  RequestBuilder setPriority(Priority paramPriority);
  
  RequestBuilder setTag(Object paramObject);
  
  RequestBuilder setUserAgent(String paramString);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\common\RequestBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */