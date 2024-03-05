package com.androidnetworking.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.androidnetworking.core.Core;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndBitmapRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.androidnetworking.internal.ANRequestQueue;
import com.androidnetworking.internal.SynchronousCall;
import com.androidnetworking.model.MultipartFileBody;
import com.androidnetworking.model.MultipartStringBody;
import com.androidnetworking.utils.ParseUtil;
import com.androidnetworking.utils.Utils;
import com.google.gson.internal.;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Okio;
import okio.Source;
import org.json.JSONArray;
import org.json.JSONObject;

public class ANRequest<T extends ANRequest> {
  private static final MediaType JSON_MEDIA_TYPE;
  
  private static final MediaType MEDIA_TYPE_MARKDOWN;
  
  private static final String TAG = ANRequest.class.getSimpleName();
  
  private static final Object sDecodeLock;
  
  private Call call;
  
  private MediaType customMediaType = null;
  
  private Future future;
  
  private boolean isCancelled;
  
  private boolean isDelivered;
  
  private boolean isRunning;
  
  private AnalyticsListener mAnalyticsListener;
  
  private String mApplicationJsonString = null;
  
  private BitmapRequestListener mBitmapRequestListener;
  
  private HashMap<String, String> mBodyParameterMap = new HashMap<String, String>();
  
  private byte[] mByte = null;
  
  private CacheControl mCacheControl = null;
  
  private Bitmap.Config mDecodeConfig;
  
  private String mDirPath;
  
  private DownloadListener mDownloadListener;
  
  private DownloadProgressListener mDownloadProgressListener;
  
  private Executor mExecutor = null;
  
  private File mFile = null;
  
  private String mFileName;
  
  private HashMap<String, List<String>> mHeadersMap = new HashMap<String, List<String>>();
  
  private JSONArrayRequestListener mJSONArrayRequestListener;
  
  private JSONObjectRequestListener mJSONObjectRequestListener;
  
  private int mMaxHeight;
  
  private int mMaxWidth;
  
  private int mMethod = 0;
  
  private HashMap<String, List<MultipartFileBody>> mMultiPartFileMap = new HashMap<String, List<MultipartFileBody>>();
  
  private HashMap<String, MultipartStringBody> mMultiPartParameterMap = new HashMap<String, MultipartStringBody>();
  
  private OkHttpClient mOkHttpClient = null;
  
  private OkHttpResponseAndBitmapRequestListener mOkHttpResponseAndBitmapRequestListener;
  
  private OkHttpResponseAndJSONArrayRequestListener mOkHttpResponseAndJSONArrayRequestListener;
  
  private OkHttpResponseAndJSONObjectRequestListener mOkHttpResponseAndJSONObjectRequestListener;
  
  private OkHttpResponseAndParsedRequestListener mOkHttpResponseAndParsedRequestListener;
  
  private OkHttpResponseAndStringRequestListener mOkHttpResponseAndStringRequestListener;
  
  private OkHttpResponseListener mOkHttpResponseListener;
  
  private ParsedRequestListener mParsedRequestListener;
  
  private HashMap<String, String> mPathParameterMap = new HashMap<String, String>();
  
  private int mPercentageThresholdForCancelling = 0;
  
  private Priority mPriority;
  
  private int mProgress;
  
  private HashMap<String, List<String>> mQueryParameterMap = new HashMap<String, List<String>>();
  
  private int mRequestType = 1;
  
  private ResponseType mResponseType;
  
  private ImageView.ScaleType mScaleType;
  
  private String mStringBody = null;
  
  private StringRequestListener mStringRequestListener;
  
  private Object mTag;
  
  private Type mType = null;
  
  private UploadProgressListener mUploadProgressListener;
  
  private String mUrl;
  
  private HashMap<String, String> mUrlEncodedFormBodyParameterMap = new HashMap<String, String>();
  
  private String mUserAgent = null;
  
  private int sequenceNumber;
  
  static {
    JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    sDecodeLock = new Object();
  }
  
  public ANRequest(DownloadBuilder paramDownloadBuilder) {
    this.mPriority = paramDownloadBuilder.mPriority;
    this.mUrl = paramDownloadBuilder.mUrl;
    this.mTag = paramDownloadBuilder.mTag;
    this.mDirPath = paramDownloadBuilder.mDirPath;
    this.mFileName = paramDownloadBuilder.mFileName;
    this.mHeadersMap = paramDownloadBuilder.mHeadersMap;
    this.mQueryParameterMap = paramDownloadBuilder.mQueryParameterMap;
    this.mPathParameterMap = paramDownloadBuilder.mPathParameterMap;
    this.mCacheControl = paramDownloadBuilder.mCacheControl;
    this.mPercentageThresholdForCancelling = paramDownloadBuilder.mPercentageThresholdForCancelling;
    this.mExecutor = paramDownloadBuilder.mExecutor;
    this.mOkHttpClient = paramDownloadBuilder.mOkHttpClient;
    this.mUserAgent = paramDownloadBuilder.mUserAgent;
  }
  
  public ANRequest(GetRequestBuilder paramGetRequestBuilder) {
    this.mPriority = paramGetRequestBuilder.mPriority;
    this.mUrl = paramGetRequestBuilder.mUrl;
    this.mTag = paramGetRequestBuilder.mTag;
    this.mHeadersMap = paramGetRequestBuilder.mHeadersMap;
    this.mDecodeConfig = paramGetRequestBuilder.mDecodeConfig;
    this.mMaxHeight = paramGetRequestBuilder.mMaxHeight;
    this.mMaxWidth = paramGetRequestBuilder.mMaxWidth;
    this.mScaleType = paramGetRequestBuilder.mScaleType;
    this.mQueryParameterMap = paramGetRequestBuilder.mQueryParameterMap;
    this.mPathParameterMap = paramGetRequestBuilder.mPathParameterMap;
    this.mCacheControl = paramGetRequestBuilder.mCacheControl;
    this.mExecutor = paramGetRequestBuilder.mExecutor;
    this.mOkHttpClient = paramGetRequestBuilder.mOkHttpClient;
    this.mUserAgent = paramGetRequestBuilder.mUserAgent;
  }
  
  public ANRequest(MultiPartBuilder paramMultiPartBuilder) {
    this.mPriority = paramMultiPartBuilder.mPriority;
    this.mUrl = paramMultiPartBuilder.mUrl;
    this.mTag = paramMultiPartBuilder.mTag;
    this.mHeadersMap = paramMultiPartBuilder.mHeadersMap;
    this.mQueryParameterMap = paramMultiPartBuilder.mQueryParameterMap;
    this.mPathParameterMap = paramMultiPartBuilder.mPathParameterMap;
    this.mMultiPartParameterMap = paramMultiPartBuilder.mMultiPartParameterMap;
    this.mMultiPartFileMap = paramMultiPartBuilder.mMultiPartFileMap;
    this.mCacheControl = paramMultiPartBuilder.mCacheControl;
    this.mPercentageThresholdForCancelling = paramMultiPartBuilder.mPercentageThresholdForCancelling;
    this.mExecutor = paramMultiPartBuilder.mExecutor;
    this.mOkHttpClient = paramMultiPartBuilder.mOkHttpClient;
    this.mUserAgent = paramMultiPartBuilder.mUserAgent;
    if (paramMultiPartBuilder.mCustomContentType != null)
      this.customMediaType = MediaType.parse(paramMultiPartBuilder.mCustomContentType); 
  }
  
  public ANRequest(PostRequestBuilder paramPostRequestBuilder) {
    this.mPriority = paramPostRequestBuilder.mPriority;
    this.mUrl = paramPostRequestBuilder.mUrl;
    this.mTag = paramPostRequestBuilder.mTag;
    this.mHeadersMap = paramPostRequestBuilder.mHeadersMap;
    this.mBodyParameterMap = paramPostRequestBuilder.mBodyParameterMap;
    this.mUrlEncodedFormBodyParameterMap = paramPostRequestBuilder.mUrlEncodedFormBodyParameterMap;
    this.mQueryParameterMap = paramPostRequestBuilder.mQueryParameterMap;
    this.mPathParameterMap = paramPostRequestBuilder.mPathParameterMap;
    this.mApplicationJsonString = paramPostRequestBuilder.mApplicationJsonString;
    this.mStringBody = paramPostRequestBuilder.mStringBody;
    this.mFile = paramPostRequestBuilder.mFile;
    this.mByte = paramPostRequestBuilder.mByte;
    this.mCacheControl = paramPostRequestBuilder.mCacheControl;
    this.mExecutor = paramPostRequestBuilder.mExecutor;
    this.mOkHttpClient = paramPostRequestBuilder.mOkHttpClient;
    this.mUserAgent = paramPostRequestBuilder.mUserAgent;
    if (paramPostRequestBuilder.mCustomContentType != null)
      this.customMediaType = MediaType.parse(paramPostRequestBuilder.mCustomContentType); 
  }
  
  private void deliverErrorResponse(ANError paramANError) {
    JSONObjectRequestListener jSONObjectRequestListener = this.mJSONObjectRequestListener;
    if (jSONObjectRequestListener != null) {
      jSONObjectRequestListener.onError(paramANError);
    } else {
      JSONArrayRequestListener jSONArrayRequestListener = this.mJSONArrayRequestListener;
      if (jSONArrayRequestListener != null) {
        jSONArrayRequestListener.onError(paramANError);
      } else {
        StringRequestListener stringRequestListener = this.mStringRequestListener;
        if (stringRequestListener != null) {
          stringRequestListener.onError(paramANError);
        } else {
          BitmapRequestListener bitmapRequestListener = this.mBitmapRequestListener;
          if (bitmapRequestListener != null) {
            bitmapRequestListener.onError(paramANError);
          } else {
            ParsedRequestListener parsedRequestListener = this.mParsedRequestListener;
            if (parsedRequestListener != null) {
              parsedRequestListener.onError(paramANError);
            } else {
              OkHttpResponseListener okHttpResponseListener = this.mOkHttpResponseListener;
              if (okHttpResponseListener != null) {
                okHttpResponseListener.onError(paramANError);
              } else {
                OkHttpResponseAndJSONObjectRequestListener okHttpResponseAndJSONObjectRequestListener = this.mOkHttpResponseAndJSONObjectRequestListener;
                if (okHttpResponseAndJSONObjectRequestListener != null) {
                  okHttpResponseAndJSONObjectRequestListener.onError(paramANError);
                } else {
                  OkHttpResponseAndJSONArrayRequestListener okHttpResponseAndJSONArrayRequestListener = this.mOkHttpResponseAndJSONArrayRequestListener;
                  if (okHttpResponseAndJSONArrayRequestListener != null) {
                    okHttpResponseAndJSONArrayRequestListener.onError(paramANError);
                  } else {
                    OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener = this.mOkHttpResponseAndStringRequestListener;
                    if (okHttpResponseAndStringRequestListener != null) {
                      okHttpResponseAndStringRequestListener.onError(paramANError);
                    } else {
                      OkHttpResponseAndBitmapRequestListener okHttpResponseAndBitmapRequestListener = this.mOkHttpResponseAndBitmapRequestListener;
                      if (okHttpResponseAndBitmapRequestListener != null) {
                        okHttpResponseAndBitmapRequestListener.onError(paramANError);
                      } else {
                        OkHttpResponseAndParsedRequestListener okHttpResponseAndParsedRequestListener = this.mOkHttpResponseAndParsedRequestListener;
                        if (okHttpResponseAndParsedRequestListener != null) {
                          okHttpResponseAndParsedRequestListener.onError(paramANError);
                        } else {
                          DownloadListener downloadListener = this.mDownloadListener;
                          if (downloadListener != null)
                            downloadListener.onError(paramANError); 
                        } 
                      } 
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  private void deliverSuccessResponse(ANResponse<JSONObject> paramANResponse) {
    JSONObjectRequestListener jSONObjectRequestListener = this.mJSONObjectRequestListener;
    if (jSONObjectRequestListener != null) {
      jSONObjectRequestListener.onResponse(paramANResponse.getResult());
    } else {
      JSONArrayRequestListener jSONArrayRequestListener = this.mJSONArrayRequestListener;
      if (jSONArrayRequestListener != null) {
        jSONArrayRequestListener.onResponse((JSONArray)paramANResponse.getResult());
      } else {
        StringRequestListener stringRequestListener = this.mStringRequestListener;
        if (stringRequestListener != null) {
          stringRequestListener.onResponse((String)paramANResponse.getResult());
        } else {
          BitmapRequestListener bitmapRequestListener = this.mBitmapRequestListener;
          if (bitmapRequestListener != null) {
            bitmapRequestListener.onResponse((Bitmap)paramANResponse.getResult());
          } else {
            ParsedRequestListener parsedRequestListener = this.mParsedRequestListener;
            if (parsedRequestListener != null) {
              parsedRequestListener.onResponse(paramANResponse.getResult());
            } else {
              OkHttpResponseAndJSONObjectRequestListener okHttpResponseAndJSONObjectRequestListener = this.mOkHttpResponseAndJSONObjectRequestListener;
              if (okHttpResponseAndJSONObjectRequestListener != null) {
                okHttpResponseAndJSONObjectRequestListener.onResponse(paramANResponse.getOkHttpResponse(), paramANResponse.getResult());
              } else {
                OkHttpResponseAndJSONArrayRequestListener okHttpResponseAndJSONArrayRequestListener = this.mOkHttpResponseAndJSONArrayRequestListener;
                if (okHttpResponseAndJSONArrayRequestListener != null) {
                  okHttpResponseAndJSONArrayRequestListener.onResponse(paramANResponse.getOkHttpResponse(), (JSONArray)paramANResponse.getResult());
                } else {
                  OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener = this.mOkHttpResponseAndStringRequestListener;
                  if (okHttpResponseAndStringRequestListener != null) {
                    okHttpResponseAndStringRequestListener.onResponse(paramANResponse.getOkHttpResponse(), (String)paramANResponse.getResult());
                  } else {
                    OkHttpResponseAndBitmapRequestListener okHttpResponseAndBitmapRequestListener = this.mOkHttpResponseAndBitmapRequestListener;
                    if (okHttpResponseAndBitmapRequestListener != null) {
                      okHttpResponseAndBitmapRequestListener.onResponse(paramANResponse.getOkHttpResponse(), (Bitmap)paramANResponse.getResult());
                    } else {
                      OkHttpResponseAndParsedRequestListener okHttpResponseAndParsedRequestListener = this.mOkHttpResponseAndParsedRequestListener;
                      if (okHttpResponseAndParsedRequestListener != null)
                        okHttpResponseAndParsedRequestListener.onResponse(paramANResponse.getOkHttpResponse(), paramANResponse.getResult()); 
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    finish();
  }
  
  public void cancel(boolean paramBoolean) {
    // Byte code:
    //   0: iload_1
    //   1: ifne -> 22
    //   4: aload_0
    //   5: getfield mPercentageThresholdForCancelling : I
    //   8: ifeq -> 22
    //   11: aload_0
    //   12: getfield mProgress : I
    //   15: aload_0
    //   16: getfield mPercentageThresholdForCancelling : I
    //   19: if_icmpge -> 94
    //   22: aload_0
    //   23: iconst_1
    //   24: putfield isCancelled : Z
    //   27: aload_0
    //   28: iconst_0
    //   29: putfield isRunning : Z
    //   32: aload_0
    //   33: getfield call : Lokhttp3/Call;
    //   36: ifnull -> 48
    //   39: aload_0
    //   40: getfield call : Lokhttp3/Call;
    //   43: invokeinterface cancel : ()V
    //   48: aload_0
    //   49: getfield future : Ljava/util/concurrent/Future;
    //   52: ifnull -> 66
    //   55: aload_0
    //   56: getfield future : Ljava/util/concurrent/Future;
    //   59: iconst_1
    //   60: invokeinterface cancel : (Z)Z
    //   65: pop
    //   66: aload_0
    //   67: getfield isDelivered : Z
    //   70: ifne -> 94
    //   73: new com/androidnetworking/error/ANError
    //   76: astore_2
    //   77: aload_2
    //   78: invokespecial <init> : ()V
    //   81: aload_0
    //   82: aload_2
    //   83: invokevirtual deliverError : (Lcom/androidnetworking/error/ANError;)V
    //   86: goto -> 94
    //   89: astore_2
    //   90: aload_2
    //   91: invokevirtual printStackTrace : ()V
    //   94: return
    // Exception table:
    //   from	to	target	type
    //   4	22	89	java/lang/Exception
    //   22	48	89	java/lang/Exception
    //   48	66	89	java/lang/Exception
    //   66	86	89	java/lang/Exception
  }
  
  public void deliverError(ANError paramANError) {
    /* monitor enter ThisExpression{ObjectType{com/androidnetworking/common/ANRequest}} */
    try {
      if (!this.isDelivered) {
        if (this.isCancelled) {
          paramANError.setCancellationMessageInError();
          paramANError.setErrorCode(0);
        } 
        deliverErrorResponse(paramANError);
      } 
      this.isDelivered = true;
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    /* monitor exit ThisExpression{ObjectType{com/androidnetworking/common/ANRequest}} */
  }
  
  public void deliverOkHttpResponse(Response paramResponse) {
    try {
      this.isDelivered = true;
      if (!this.isCancelled) {
        if (this.mExecutor != null) {
          Executor executor = this.mExecutor;
          Runnable runnable = new Runnable() {
              final ANRequest this$0;
              
              final Response val$response;
              
              public void run() {
                if (ANRequest.this.mOkHttpResponseListener != null)
                  ANRequest.this.mOkHttpResponseListener.onResponse(response); 
                ANRequest.this.finish();
              }
            };
          super(this, paramResponse);
          executor.execute(runnable);
        } else {
          Executor executor = Core.getInstance().getExecutorSupplier().forMainThreadTasks();
          Runnable runnable = new Runnable() {
              final ANRequest this$0;
              
              final Response val$response;
              
              public void run() {
                if (ANRequest.this.mOkHttpResponseListener != null)
                  ANRequest.this.mOkHttpResponseListener.onResponse(response); 
                ANRequest.this.finish();
              }
            };
          super(this, paramResponse);
          executor.execute(runnable);
        } 
      } else {
        ANError aNError = new ANError();
        this();
        aNError.setCancellationMessageInError();
        aNError.setErrorCode(0);
        if (this.mOkHttpResponseListener != null)
          this.mOkHttpResponseListener.onError(aNError); 
        finish();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void deliverResponse(ANResponse paramANResponse) {
    try {
      this.isDelivered = true;
      if (!this.isCancelled) {
        if (this.mExecutor != null) {
          Executor executor = this.mExecutor;
          Runnable runnable = new Runnable() {
              final ANRequest this$0;
              
              final ANResponse val$response;
              
              public void run() {
                ANRequest.this.deliverSuccessResponse(response);
              }
            };
          super(this, paramANResponse);
          executor.execute(runnable);
        } else {
          Executor executor = Core.getInstance().getExecutorSupplier().forMainThreadTasks();
          Runnable runnable = new Runnable() {
              final ANRequest this$0;
              
              final ANResponse val$response;
              
              public void run() {
                ANRequest.this.deliverSuccessResponse(response);
              }
            };
          super(this, paramANResponse);
          executor.execute(runnable);
        } 
      } else {
        ANError aNError = new ANError();
        this();
        aNError.setCancellationMessageInError();
        aNError.setErrorCode(0);
        deliverErrorResponse(aNError);
        finish();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void destroy() {
    this.mJSONArrayRequestListener = null;
    this.mJSONObjectRequestListener = null;
    this.mStringRequestListener = null;
    this.mBitmapRequestListener = null;
    this.mParsedRequestListener = null;
    this.mDownloadProgressListener = null;
    this.mUploadProgressListener = null;
    this.mDownloadListener = null;
    this.mAnalyticsListener = null;
  }
  
  public ANResponse executeForBitmap() {
    this.mResponseType = ResponseType.BITMAP;
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForDownload() {
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForJSONArray() {
    this.mResponseType = ResponseType.JSON_ARRAY;
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForJSONObject() {
    this.mResponseType = ResponseType.JSON_OBJECT;
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForObject(Class paramClass) {
    this.mType = paramClass;
    this.mResponseType = ResponseType.PARSED;
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForObjectList(Class paramClass) {
    this.mType = .Gson.Types.newParameterizedTypeWithOwner(null, List.class, new Type[] { paramClass });
    this.mResponseType = ResponseType.PARSED;
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForOkHttpResponse() {
    this.mResponseType = ResponseType.OK_HTTP_RESPONSE;
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForParsed(TypeToken paramTypeToken) {
    this.mType = paramTypeToken.getType();
    this.mResponseType = ResponseType.PARSED;
    return SynchronousCall.execute(this);
  }
  
  public ANResponse executeForString() {
    this.mResponseType = ResponseType.STRING;
    return SynchronousCall.execute(this);
  }
  
  public void finish() {
    destroy();
    ANRequestQueue.getInstance().finish(this);
  }
  
  public AnalyticsListener getAnalyticsListener() {
    return this.mAnalyticsListener;
  }
  
  public void getAsBitmap(BitmapRequestListener paramBitmapRequestListener) {
    this.mResponseType = ResponseType.BITMAP;
    this.mBitmapRequestListener = paramBitmapRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsJSONArray(JSONArrayRequestListener paramJSONArrayRequestListener) {
    this.mResponseType = ResponseType.JSON_ARRAY;
    this.mJSONArrayRequestListener = paramJSONArrayRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsJSONObject(JSONObjectRequestListener paramJSONObjectRequestListener) {
    this.mResponseType = ResponseType.JSON_OBJECT;
    this.mJSONObjectRequestListener = paramJSONObjectRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsObject(Class paramClass, ParsedRequestListener paramParsedRequestListener) {
    this.mType = paramClass;
    this.mResponseType = ResponseType.PARSED;
    this.mParsedRequestListener = paramParsedRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsObjectList(Class paramClass, ParsedRequestListener paramParsedRequestListener) {
    this.mType = .Gson.Types.newParameterizedTypeWithOwner(null, List.class, new Type[] { paramClass });
    this.mResponseType = ResponseType.PARSED;
    this.mParsedRequestListener = paramParsedRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponse(OkHttpResponseListener paramOkHttpResponseListener) {
    this.mResponseType = ResponseType.OK_HTTP_RESPONSE;
    this.mOkHttpResponseListener = paramOkHttpResponseListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponseAndBitmap(OkHttpResponseAndBitmapRequestListener paramOkHttpResponseAndBitmapRequestListener) {
    this.mResponseType = ResponseType.BITMAP;
    this.mOkHttpResponseAndBitmapRequestListener = paramOkHttpResponseAndBitmapRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponseAndJSONArray(OkHttpResponseAndJSONArrayRequestListener paramOkHttpResponseAndJSONArrayRequestListener) {
    this.mResponseType = ResponseType.JSON_ARRAY;
    this.mOkHttpResponseAndJSONArrayRequestListener = paramOkHttpResponseAndJSONArrayRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponseAndJSONObject(OkHttpResponseAndJSONObjectRequestListener paramOkHttpResponseAndJSONObjectRequestListener) {
    this.mResponseType = ResponseType.JSON_OBJECT;
    this.mOkHttpResponseAndJSONObjectRequestListener = paramOkHttpResponseAndJSONObjectRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponseAndObject(Class paramClass, OkHttpResponseAndParsedRequestListener paramOkHttpResponseAndParsedRequestListener) {
    this.mType = paramClass;
    this.mResponseType = ResponseType.PARSED;
    this.mOkHttpResponseAndParsedRequestListener = paramOkHttpResponseAndParsedRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponseAndObjectList(Class paramClass, OkHttpResponseAndParsedRequestListener paramOkHttpResponseAndParsedRequestListener) {
    this.mType = .Gson.Types.newParameterizedTypeWithOwner(null, List.class, new Type[] { paramClass });
    this.mResponseType = ResponseType.PARSED;
    this.mOkHttpResponseAndParsedRequestListener = paramOkHttpResponseAndParsedRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponseAndParsed(TypeToken paramTypeToken, OkHttpResponseAndParsedRequestListener paramOkHttpResponseAndParsedRequestListener) {
    this.mType = paramTypeToken.getType();
    this.mResponseType = ResponseType.PARSED;
    this.mOkHttpResponseAndParsedRequestListener = paramOkHttpResponseAndParsedRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsOkHttpResponseAndString(OkHttpResponseAndStringRequestListener paramOkHttpResponseAndStringRequestListener) {
    this.mResponseType = ResponseType.STRING;
    this.mOkHttpResponseAndStringRequestListener = paramOkHttpResponseAndStringRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsParsed(TypeToken paramTypeToken, ParsedRequestListener paramParsedRequestListener) {
    this.mType = paramTypeToken.getType();
    this.mResponseType = ResponseType.PARSED;
    this.mParsedRequestListener = paramParsedRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public void getAsString(StringRequestListener paramStringRequestListener) {
    this.mResponseType = ResponseType.STRING;
    this.mStringRequestListener = paramStringRequestListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public CacheControl getCacheControl() {
    return this.mCacheControl;
  }
  
  public Call getCall() {
    return this.call;
  }
  
  public String getDirPath() {
    return this.mDirPath;
  }
  
  public DownloadProgressListener getDownloadProgressListener() {
    return new DownloadProgressListener() {
        final ANRequest this$0;
        
        public void onProgress(long param1Long1, long param1Long2) {
          if (ANRequest.this.mDownloadProgressListener != null && !ANRequest.this.isCancelled)
            ANRequest.this.mDownloadProgressListener.onProgress(param1Long1, param1Long2); 
        }
      };
  }
  
  public String getFileName() {
    return this.mFileName;
  }
  
  public Future getFuture() {
    return this.future;
  }
  
  public Headers getHeaders() {
    Headers.Builder builder = new Headers.Builder();
    try {
      if (this.mHeadersMap != null)
        for (Map.Entry<String, List<String>> entry : this.mHeadersMap.entrySet()) {
          String str = (String)entry.getKey();
          List list = (List)entry.getValue();
          if (list != null) {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext())
              builder.add(str, iterator.next()); 
          } 
        }  
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return builder.build();
  }
  
  public int getMethod() {
    return this.mMethod;
  }
  
  public RequestBody getMultiPartRequestBody() {
    MultipartBody.Builder builder2 = new MultipartBody.Builder();
    MediaType mediaType2 = this.customMediaType;
    MediaType mediaType1 = mediaType2;
    if (mediaType2 == null)
      mediaType1 = MultipartBody.FORM; 
    MultipartBody.Builder builder1 = builder2.setType(mediaType1);
    try {
      Iterator<Map.Entry> iterator = this.mMultiPartParameterMap.entrySet().iterator();
      while (true) {
        boolean bool = iterator.hasNext();
        if (bool) {
          Map.Entry entry = iterator.next();
          MultipartStringBody multipartStringBody = (MultipartStringBody)entry.getValue();
          mediaType1 = null;
          if (multipartStringBody.contentType != null)
            mediaType1 = MediaType.parse(multipartStringBody.contentType); 
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("form-data; name=\"");
          stringBuilder.append((String)entry.getKey());
          stringBuilder.append("\"");
          builder1.addPart(Headers.of(new String[] { "Content-Disposition", stringBuilder.toString() }, ), RequestBody.create(mediaType1, multipartStringBody.value));
          continue;
        } 
        for (Map.Entry<String, List<MultipartFileBody>> entry : this.mMultiPartFileMap.entrySet()) {
          for (MultipartFileBody multipartFileBody : entry.getValue()) {
            String str = multipartFileBody.file.getName();
            if (multipartFileBody.contentType != null) {
              mediaType1 = MediaType.parse(multipartFileBody.contentType);
            } else {
              mediaType1 = MediaType.parse(Utils.getMimeType(str));
            } 
            RequestBody requestBody = RequestBody.create(mediaType1, multipartFileBody.file);
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("form-data; name=\"");
            stringBuilder.append((String)entry.getKey());
            stringBuilder.append("\"; filename=\"");
            stringBuilder.append(str);
            stringBuilder.append("\"");
            builder1.addPart(Headers.of(new String[] { "Content-Disposition", stringBuilder.toString() }, ), requestBody);
          } 
        } 
        return (RequestBody)builder1.build();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return (RequestBody)builder1.build();
  }
  
  public OkHttpClient getOkHttpClient() {
    return this.mOkHttpClient;
  }
  
  public Priority getPriority() {
    return this.mPriority;
  }
  
  public RequestBody getRequestBody() {
    String str1 = this.mApplicationJsonString;
    if (str1 != null) {
      MediaType mediaType = this.customMediaType;
      return (mediaType != null) ? RequestBody.create(mediaType, str1) : RequestBody.create(JSON_MEDIA_TYPE, str1);
    } 
    String str2 = this.mStringBody;
    if (str2 != null) {
      MediaType mediaType = this.customMediaType;
      return (mediaType != null) ? RequestBody.create(mediaType, str2) : RequestBody.create(MEDIA_TYPE_MARKDOWN, str2);
    } 
    File file = this.mFile;
    if (file != null) {
      MediaType mediaType = this.customMediaType;
      return (mediaType != null) ? RequestBody.create(mediaType, file) : RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
    } 
    byte[] arrayOfByte = this.mByte;
    if (arrayOfByte != null) {
      MediaType mediaType = this.customMediaType;
      return (mediaType != null) ? RequestBody.create(mediaType, arrayOfByte) : RequestBody.create(MEDIA_TYPE_MARKDOWN, arrayOfByte);
    } 
    FormBody.Builder builder = new FormBody.Builder();
    try {
      for (Map.Entry<String, String> entry : this.mBodyParameterMap.entrySet())
        builder.add((String)entry.getKey(), (String)entry.getValue()); 
      for (Map.Entry<String, String> entry : this.mUrlEncodedFormBodyParameterMap.entrySet())
        builder.addEncoded((String)entry.getKey(), (String)entry.getValue()); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return (RequestBody)builder.build();
  }
  
  public int getRequestType() {
    return this.mRequestType;
  }
  
  public ResponseType getResponseAs() {
    return this.mResponseType;
  }
  
  public ImageView.ScaleType getScaleType() {
    return this.mScaleType;
  }
  
  public int getSequenceNumber() {
    return this.sequenceNumber;
  }
  
  public Object getTag() {
    return this.mTag;
  }
  
  public Type getType() {
    return this.mType;
  }
  
  public UploadProgressListener getUploadProgressListener() {
    return new UploadProgressListener() {
        final ANRequest this$0;
        
        public void onProgress(long param1Long1, long param1Long2) {
          ANRequest.access$6302(ANRequest.this, (int)(100L * param1Long1 / param1Long2));
          if (ANRequest.this.mUploadProgressListener != null && !ANRequest.this.isCancelled)
            ANRequest.this.mUploadProgressListener.onProgress(param1Long1, param1Long2); 
        }
      };
  }
  
  public String getUrl() {
    String str = this.mUrl;
    for (Map.Entry<String, String> entry : this.mPathParameterMap.entrySet()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("{");
      stringBuilder.append((String)entry.getKey());
      stringBuilder.append("}");
      str = str.replace(stringBuilder.toString(), String.valueOf(entry.getValue()));
    } 
    HttpUrl.Builder builder = HttpUrl.parse(str).newBuilder();
    HashMap<String, List<String>> hashMap = this.mQueryParameterMap;
    if (hashMap != null)
      for (Map.Entry<String, List<String>> entry : hashMap.entrySet()) {
        String str1 = (String)entry.getKey();
        List list = (List)entry.getValue();
        if (list != null) {
          Iterator<String> iterator = list.iterator();
          while (iterator.hasNext())
            builder.addQueryParameter(str1, iterator.next()); 
        } 
      }  
    return builder.build().toString();
  }
  
  public String getUserAgent() {
    return this.mUserAgent;
  }
  
  public boolean isCanceled() {
    return this.isCancelled;
  }
  
  public boolean isRunning() {
    return this.isRunning;
  }
  
  public ANError parseNetworkError(ANError paramANError) {
    try {
      if (paramANError.getResponse() != null && paramANError.getResponse().body() != null && paramANError.getResponse().body().source() != null)
        paramANError.setErrorBody(Okio.buffer((Source)paramANError.getResponse().body().source()).readUtf8()); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return paramANError;
  }
  
  public ANResponse parseResponse(Response paramResponse) {
    Object object;
    switch (this.mResponseType) {
      default:
        return null;
      case null:
        try {
          Okio.buffer((Source)paramResponse.body().source()).skip(Long.MAX_VALUE);
          return ANResponse.success("prefetch");
        } catch (Exception null) {
          return ANResponse.failed(Utils.getErrorForParse(new ANError(exception)));
        } 
      case null:
        try {
          return ANResponse.success(ParseUtil.getParserFactory().responseBodyParser(this.mType).convert(exception.body()));
        } catch (Exception null) {
          return ANResponse.failed(Utils.getErrorForParse(new ANError(exception)));
        } 
      case null:
        object = sDecodeLock;
        /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
        try {
          ANResponse aNResponse = Utils.decodeBitmap((Response)exception, this.mMaxWidth, this.mMaxHeight, this.mDecodeConfig, this.mScaleType);
          /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
          return aNResponse;
        } catch (Exception exception1) {
          ANError aNError = new ANError();
          this(exception1);
          ANResponse<?> aNResponse = ANResponse.failed(Utils.getErrorForParse(aNError));
          /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
          return aNResponse;
        } finally {}
        /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
        throw exception;
      case null:
        try {
          return ANResponse.success(Okio.buffer((Source)exception.body().source()).readUtf8());
        } catch (Exception null) {
          return ANResponse.failed(Utils.getErrorForParse(new ANError(exception)));
        } 
      case null:
        try {
          object = new JSONObject();
          super(Okio.buffer((Source)exception.body().source()).readUtf8());
          return ANResponse.success(object);
        } catch (Exception exception) {
          return ANResponse.failed(Utils.getErrorForParse(new ANError(exception)));
        } 
      case null:
        break;
    } 
    try {
      object = new JSONArray();
      super(Okio.buffer((Source)exception.body().source()).readUtf8());
      return ANResponse.success(object);
    } catch (Exception exception1) {
      return ANResponse.failed(Utils.getErrorForParse(new ANError(exception1)));
    } 
  }
  
  public void prefetch() {
    this.mResponseType = ResponseType.PREFETCH;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public T setAnalyticsListener(AnalyticsListener paramAnalyticsListener) {
    this.mAnalyticsListener = paramAnalyticsListener;
    return (T)this;
  }
  
  public void setCall(Call paramCall) {
    this.call = paramCall;
  }
  
  public T setDownloadProgressListener(DownloadProgressListener paramDownloadProgressListener) {
    this.mDownloadProgressListener = paramDownloadProgressListener;
    return (T)this;
  }
  
  public void setFuture(Future paramFuture) {
    this.future = paramFuture;
  }
  
  public void setProgress(int paramInt) {
    this.mProgress = paramInt;
  }
  
  public void setResponseAs(ResponseType paramResponseType) {
    this.mResponseType = paramResponseType;
  }
  
  public void setRunning(boolean paramBoolean) {
    this.isRunning = paramBoolean;
  }
  
  public void setSequenceNumber(int paramInt) {
    this.sequenceNumber = paramInt;
  }
  
  public void setType(Type paramType) {
    this.mType = paramType;
  }
  
  public T setUploadProgressListener(UploadProgressListener paramUploadProgressListener) {
    this.mUploadProgressListener = paramUploadProgressListener;
    return (T)this;
  }
  
  public void setUserAgent(String paramString) {
    this.mUserAgent = paramString;
  }
  
  public void startDownload(DownloadListener paramDownloadListener) {
    this.mDownloadListener = paramDownloadListener;
    ANRequestQueue.getInstance().addRequest(this);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("ANRequest{sequenceNumber='");
    stringBuilder.append(this.sequenceNumber);
    stringBuilder.append(", mMethod=");
    stringBuilder.append(this.mMethod);
    stringBuilder.append(", mPriority=");
    stringBuilder.append(this.mPriority);
    stringBuilder.append(", mRequestType=");
    stringBuilder.append(this.mRequestType);
    stringBuilder.append(", mUrl=");
    stringBuilder.append(this.mUrl);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public void updateDownloadCompletion() {
    this.isDelivered = true;
    if (this.mDownloadListener != null) {
      if (!this.isCancelled) {
        Executor executor = this.mExecutor;
        if (executor != null) {
          executor.execute(new Runnable() {
                final ANRequest this$0;
                
                public void run() {
                  if (ANRequest.this.mDownloadListener != null)
                    ANRequest.this.mDownloadListener.onDownloadComplete(); 
                  ANRequest.this.finish();
                }
              });
        } else {
          Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
                final ANRequest this$0;
                
                public void run() {
                  if (ANRequest.this.mDownloadListener != null)
                    ANRequest.this.mDownloadListener.onDownloadComplete(); 
                  ANRequest.this.finish();
                }
              });
        } 
      } else {
        deliverError(new ANError());
        finish();
      } 
    } else {
      finish();
    } 
  }
  
  public static class DeleteRequestBuilder extends PostRequestBuilder {
    public DeleteRequestBuilder(String param1String) {
      super(param1String, 3);
    }
  }
  
  public static class DownloadBuilder<T extends DownloadBuilder> implements RequestBuilder {
    private CacheControl mCacheControl;
    
    private String mDirPath;
    
    private Executor mExecutor;
    
    private String mFileName;
    
    private HashMap<String, List<String>> mHeadersMap = new HashMap<String, List<String>>();
    
    private OkHttpClient mOkHttpClient;
    
    private HashMap<String, String> mPathParameterMap = new HashMap<String, String>();
    
    private int mPercentageThresholdForCancelling = 0;
    
    private Priority mPriority = Priority.MEDIUM;
    
    private HashMap<String, List<String>> mQueryParameterMap = new HashMap<String, List<String>>();
    
    private Object mTag;
    
    private String mUrl;
    
    private String mUserAgent;
    
    public DownloadBuilder(String param1String1, String param1String2, String param1String3) {
      this.mUrl = param1String1;
      this.mDirPath = param1String2;
      this.mFileName = param1String3;
    }
    
    public T addHeaders(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addHeaders(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addHeaders(String param1String1, String param1String2) {
      List<String> list2 = this.mHeadersMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mHeadersMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addHeaders(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addHeaders((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public T addPathParameter(Object param1Object) {
      if (param1Object != null)
        this.mPathParameterMap.putAll(ParseUtil.getParserFactory().getStringMap(param1Object)); 
      return (T)this;
    }
    
    public T addPathParameter(String param1String1, String param1String2) {
      this.mPathParameterMap.put(param1String1, param1String2);
      return (T)this;
    }
    
    public T addPathParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        this.mPathParameterMap.putAll(param1Map); 
      return (T)this;
    }
    
    public T addQueryParameter(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addQueryParameter(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addQueryParameter(String param1String1, String param1String2) {
      List<String> list2 = this.mQueryParameterMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mQueryParameterMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addQueryParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addQueryParameter((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public ANRequest build() {
      return new ANRequest<ANRequest>(this);
    }
    
    public T doNotCacheResponse() {
      this.mCacheControl = (new CacheControl.Builder()).noStore().build();
      return (T)this;
    }
    
    public T getResponseOnlyFromNetwork() {
      this.mCacheControl = CacheControl.FORCE_NETWORK;
      return (T)this;
    }
    
    public T getResponseOnlyIfCached() {
      this.mCacheControl = CacheControl.FORCE_CACHE;
      return (T)this;
    }
    
    public T setExecutor(Executor param1Executor) {
      this.mExecutor = param1Executor;
      return (T)this;
    }
    
    public T setMaxAgeCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxAge(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setMaxStaleCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxStale(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setOkHttpClient(OkHttpClient param1OkHttpClient) {
      this.mOkHttpClient = param1OkHttpClient;
      return (T)this;
    }
    
    public T setPercentageThresholdForCancelling(int param1Int) {
      this.mPercentageThresholdForCancelling = param1Int;
      return (T)this;
    }
    
    public T setPriority(Priority param1Priority) {
      this.mPriority = param1Priority;
      return (T)this;
    }
    
    public T setTag(Object param1Object) {
      this.mTag = param1Object;
      return (T)this;
    }
    
    public T setUserAgent(String param1String) {
      this.mUserAgent = param1String;
      return (T)this;
    }
  }
  
  public static class DynamicRequestBuilder extends PostRequestBuilder {
    public DynamicRequestBuilder(String param1String, int param1Int) {
      super(param1String, param1Int);
    }
  }
  
  public static class GetRequestBuilder<T extends GetRequestBuilder> implements RequestBuilder {
    private BitmapFactory.Options mBitmapOptions;
    
    private CacheControl mCacheControl;
    
    private Bitmap.Config mDecodeConfig;
    
    private Executor mExecutor;
    
    private HashMap<String, List<String>> mHeadersMap = new HashMap<String, List<String>>();
    
    private int mMaxHeight;
    
    private int mMaxWidth;
    
    private int mMethod = 0;
    
    private OkHttpClient mOkHttpClient;
    
    private HashMap<String, String> mPathParameterMap = new HashMap<String, String>();
    
    private Priority mPriority = Priority.MEDIUM;
    
    private HashMap<String, List<String>> mQueryParameterMap = new HashMap<String, List<String>>();
    
    private ImageView.ScaleType mScaleType;
    
    private Object mTag;
    
    private String mUrl;
    
    private String mUserAgent;
    
    public GetRequestBuilder(String param1String) {
      this.mUrl = param1String;
      this.mMethod = 0;
    }
    
    public GetRequestBuilder(String param1String, int param1Int) {
      this.mUrl = param1String;
      this.mMethod = param1Int;
    }
    
    public T addHeaders(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addHeaders(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addHeaders(String param1String1, String param1String2) {
      List<String> list2 = this.mHeadersMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mHeadersMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addHeaders(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addHeaders((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public T addPathParameter(Object param1Object) {
      if (param1Object != null)
        this.mPathParameterMap.putAll(ParseUtil.getParserFactory().getStringMap(param1Object)); 
      return (T)this;
    }
    
    public T addPathParameter(String param1String1, String param1String2) {
      this.mPathParameterMap.put(param1String1, param1String2);
      return (T)this;
    }
    
    public T addPathParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        this.mPathParameterMap.putAll(param1Map); 
      return (T)this;
    }
    
    public T addQueryParameter(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addQueryParameter(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addQueryParameter(String param1String1, String param1String2) {
      List<String> list2 = this.mQueryParameterMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mQueryParameterMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addQueryParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addQueryParameter((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public ANRequest build() {
      return new ANRequest<ANRequest>(this);
    }
    
    public T doNotCacheResponse() {
      this.mCacheControl = (new CacheControl.Builder()).noStore().build();
      return (T)this;
    }
    
    public T getResponseOnlyFromNetwork() {
      this.mCacheControl = CacheControl.FORCE_NETWORK;
      return (T)this;
    }
    
    public T getResponseOnlyIfCached() {
      this.mCacheControl = CacheControl.FORCE_CACHE;
      return (T)this;
    }
    
    public T setBitmapConfig(Bitmap.Config param1Config) {
      this.mDecodeConfig = param1Config;
      return (T)this;
    }
    
    public T setBitmapMaxHeight(int param1Int) {
      this.mMaxHeight = param1Int;
      return (T)this;
    }
    
    public T setBitmapMaxWidth(int param1Int) {
      this.mMaxWidth = param1Int;
      return (T)this;
    }
    
    public T setBitmapOptions(BitmapFactory.Options param1Options) {
      this.mBitmapOptions = param1Options;
      return (T)this;
    }
    
    public T setExecutor(Executor param1Executor) {
      this.mExecutor = param1Executor;
      return (T)this;
    }
    
    public T setImageScaleType(ImageView.ScaleType param1ScaleType) {
      this.mScaleType = param1ScaleType;
      return (T)this;
    }
    
    public T setMaxAgeCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxAge(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setMaxStaleCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxStale(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setOkHttpClient(OkHttpClient param1OkHttpClient) {
      this.mOkHttpClient = param1OkHttpClient;
      return (T)this;
    }
    
    public T setPriority(Priority param1Priority) {
      this.mPriority = param1Priority;
      return (T)this;
    }
    
    public T setTag(Object param1Object) {
      this.mTag = param1Object;
      return (T)this;
    }
    
    public T setUserAgent(String param1String) {
      this.mUserAgent = param1String;
      return (T)this;
    }
  }
  
  public static class HeadRequestBuilder extends GetRequestBuilder {
    public HeadRequestBuilder(String param1String) {
      super(param1String, 4);
    }
  }
  
  public static class MultiPartBuilder<T extends MultiPartBuilder> implements RequestBuilder {
    private CacheControl mCacheControl;
    
    private String mCustomContentType;
    
    private Executor mExecutor;
    
    private HashMap<String, List<String>> mHeadersMap = new HashMap<String, List<String>>();
    
    private HashMap<String, List<MultipartFileBody>> mMultiPartFileMap = new HashMap<String, List<MultipartFileBody>>();
    
    private HashMap<String, MultipartStringBody> mMultiPartParameterMap = new HashMap<String, MultipartStringBody>();
    
    private OkHttpClient mOkHttpClient;
    
    private HashMap<String, String> mPathParameterMap = new HashMap<String, String>();
    
    private int mPercentageThresholdForCancelling = 0;
    
    private Priority mPriority = Priority.MEDIUM;
    
    private HashMap<String, List<String>> mQueryParameterMap = new HashMap<String, List<String>>();
    
    private Object mTag;
    
    private String mUrl;
    
    private String mUserAgent;
    
    public MultiPartBuilder(String param1String) {
      this.mUrl = param1String;
    }
    
    private void addMultipartFileWithKey(String param1String, MultipartFileBody param1MultipartFileBody) {
      List<MultipartFileBody> list2 = this.mMultiPartFileMap.get(param1String);
      List<MultipartFileBody> list1 = list2;
      if (list2 == null)
        list1 = new ArrayList(); 
      list1.add(param1MultipartFileBody);
      this.mMultiPartFileMap.put(param1String, list1);
    }
    
    public T addHeaders(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addHeaders(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addHeaders(String param1String1, String param1String2) {
      List<String> list2 = this.mHeadersMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mHeadersMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addHeaders(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addHeaders((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public T addMultipartFile(String param1String, File param1File) {
      return addMultipartFile(param1String, param1File, null);
    }
    
    public T addMultipartFile(String param1String1, File param1File, String param1String2) {
      addMultipartFileWithKey(param1String1, new MultipartFileBody(param1File, param1String2));
      return (T)this;
    }
    
    public T addMultipartFile(Map<String, File> param1Map) {
      return addMultipartFile(param1Map, (String)null);
    }
    
    public T addMultipartFile(Map<String, File> param1Map, String param1String) {
      if (param1Map != null)
        for (Map.Entry<String, File> entry : param1Map.entrySet()) {
          MultipartFileBody multipartFileBody = new MultipartFileBody((File)entry.getValue(), param1String);
          addMultipartFileWithKey((String)entry.getKey(), multipartFileBody);
        }  
      return (T)this;
    }
    
    public T addMultipartFileList(String param1String, List<File> param1List) {
      return addMultipartFileList(param1String, param1List, null);
    }
    
    public T addMultipartFileList(String param1String1, List<File> param1List, String param1String2) {
      if (param1List != null) {
        Iterator<File> iterator = param1List.iterator();
        while (iterator.hasNext())
          addMultipartFileWithKey(param1String1, new MultipartFileBody(iterator.next(), param1String2)); 
      } 
      return (T)this;
    }
    
    public T addMultipartFileList(Map<String, List<File>> param1Map) {
      return addMultipartFileList(param1Map, (String)null);
    }
    
    public T addMultipartFileList(Map<String, List<File>> param1Map, String param1String) {
      if (param1Map != null) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (Map.Entry<String, List<File>> entry : param1Map.entrySet()) {
          List list = (List)entry.getValue();
          ArrayList<MultipartFileBody> arrayList = new ArrayList();
          Iterator<File> iterator = list.iterator();
          while (iterator.hasNext())
            arrayList.add(new MultipartFileBody(iterator.next(), param1String)); 
          hashMap.put(entry.getKey(), arrayList);
        } 
        this.mMultiPartFileMap.putAll(hashMap);
      } 
      return (T)this;
    }
    
    public T addMultipartParameter(Object param1Object) {
      return addMultipartParameter(param1Object, (String)null);
    }
    
    public T addMultipartParameter(Object param1Object, String param1String) {
      if (param1Object != null)
        addMultipartParameter(ParseUtil.getParserFactory().getStringMap(param1Object), param1String); 
      return (T)this;
    }
    
    public T addMultipartParameter(String param1String1, String param1String2) {
      return addMultipartParameter(param1String1, param1String2, null);
    }
    
    public T addMultipartParameter(String param1String1, String param1String2, String param1String3) {
      MultipartStringBody multipartStringBody = new MultipartStringBody(param1String2, param1String3);
      this.mMultiPartParameterMap.put(param1String1, multipartStringBody);
      return (T)this;
    }
    
    public T addMultipartParameter(Map<String, String> param1Map) {
      return addMultipartParameter(param1Map, (String)null);
    }
    
    public T addMultipartParameter(Map<String, String> param1Map, String param1String) {
      if (param1Map != null) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (Map.Entry<String, String> entry : param1Map.entrySet()) {
          MultipartStringBody multipartStringBody = new MultipartStringBody((String)entry.getValue(), param1String);
          hashMap.put(entry.getKey(), multipartStringBody);
        } 
        this.mMultiPartParameterMap.putAll(hashMap);
      } 
      return (T)this;
    }
    
    public T addPathParameter(Object param1Object) {
      if (param1Object != null)
        this.mPathParameterMap.putAll(ParseUtil.getParserFactory().getStringMap(param1Object)); 
      return (T)this;
    }
    
    public T addPathParameter(String param1String1, String param1String2) {
      this.mPathParameterMap.put(param1String1, param1String2);
      return (T)this;
    }
    
    public T addPathParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        this.mPathParameterMap.putAll(param1Map); 
      return (T)this;
    }
    
    public T addQueryParameter(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addQueryParameter(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addQueryParameter(String param1String1, String param1String2) {
      List<String> list2 = this.mQueryParameterMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mQueryParameterMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addQueryParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addQueryParameter((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public ANRequest build() {
      return new ANRequest<ANRequest>(this);
    }
    
    public T doNotCacheResponse() {
      this.mCacheControl = (new CacheControl.Builder()).noStore().build();
      return (T)this;
    }
    
    public T getResponseOnlyFromNetwork() {
      this.mCacheControl = CacheControl.FORCE_NETWORK;
      return (T)this;
    }
    
    public T getResponseOnlyIfCached() {
      this.mCacheControl = CacheControl.FORCE_CACHE;
      return (T)this;
    }
    
    public T setContentType(String param1String) {
      this.mCustomContentType = param1String;
      return (T)this;
    }
    
    public T setExecutor(Executor param1Executor) {
      this.mExecutor = param1Executor;
      return (T)this;
    }
    
    public T setMaxAgeCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxAge(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setMaxStaleCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxStale(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setOkHttpClient(OkHttpClient param1OkHttpClient) {
      this.mOkHttpClient = param1OkHttpClient;
      return (T)this;
    }
    
    public T setPercentageThresholdForCancelling(int param1Int) {
      this.mPercentageThresholdForCancelling = param1Int;
      return (T)this;
    }
    
    public T setPriority(Priority param1Priority) {
      this.mPriority = param1Priority;
      return (T)this;
    }
    
    public T setTag(Object param1Object) {
      this.mTag = param1Object;
      return (T)this;
    }
    
    public T setUserAgent(String param1String) {
      this.mUserAgent = param1String;
      return (T)this;
    }
  }
  
  public static class OptionsRequestBuilder extends GetRequestBuilder {
    public OptionsRequestBuilder(String param1String) {
      super(param1String, 6);
    }
  }
  
  public static class PatchRequestBuilder extends PostRequestBuilder {
    public PatchRequestBuilder(String param1String) {
      super(param1String, 5);
    }
  }
  
  public static class PostRequestBuilder<T extends PostRequestBuilder> implements RequestBuilder {
    private String mApplicationJsonString = null;
    
    private HashMap<String, String> mBodyParameterMap = new HashMap<String, String>();
    
    private byte[] mByte = null;
    
    private CacheControl mCacheControl;
    
    private String mCustomContentType;
    
    private Executor mExecutor;
    
    private File mFile = null;
    
    private HashMap<String, List<String>> mHeadersMap = new HashMap<String, List<String>>();
    
    private int mMethod = 1;
    
    private OkHttpClient mOkHttpClient;
    
    private HashMap<String, String> mPathParameterMap = new HashMap<String, String>();
    
    private Priority mPriority = Priority.MEDIUM;
    
    private HashMap<String, List<String>> mQueryParameterMap = new HashMap<String, List<String>>();
    
    private String mStringBody = null;
    
    private Object mTag;
    
    private String mUrl;
    
    private HashMap<String, String> mUrlEncodedFormBodyParameterMap = new HashMap<String, String>();
    
    private String mUserAgent;
    
    public PostRequestBuilder(String param1String) {
      this.mUrl = param1String;
      this.mMethod = 1;
    }
    
    public PostRequestBuilder(String param1String, int param1Int) {
      this.mUrl = param1String;
      this.mMethod = param1Int;
    }
    
    public T addApplicationJsonBody(Object param1Object) {
      if (param1Object != null)
        this.mApplicationJsonString = ParseUtil.getParserFactory().getString(param1Object); 
      return (T)this;
    }
    
    public T addBodyParameter(Object param1Object) {
      if (param1Object != null)
        this.mBodyParameterMap.putAll(ParseUtil.getParserFactory().getStringMap(param1Object)); 
      return (T)this;
    }
    
    public T addBodyParameter(String param1String1, String param1String2) {
      this.mBodyParameterMap.put(param1String1, param1String2);
      return (T)this;
    }
    
    public T addBodyParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        this.mBodyParameterMap.putAll(param1Map); 
      return (T)this;
    }
    
    public T addByteBody(byte[] param1ArrayOfbyte) {
      this.mByte = param1ArrayOfbyte;
      return (T)this;
    }
    
    public T addFileBody(File param1File) {
      this.mFile = param1File;
      return (T)this;
    }
    
    public T addHeaders(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addHeaders(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addHeaders(String param1String1, String param1String2) {
      List<String> list2 = this.mHeadersMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mHeadersMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addHeaders(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addHeaders((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public T addJSONArrayBody(JSONArray param1JSONArray) {
      if (param1JSONArray != null)
        this.mApplicationJsonString = param1JSONArray.toString(); 
      return (T)this;
    }
    
    public T addJSONObjectBody(JSONObject param1JSONObject) {
      if (param1JSONObject != null)
        this.mApplicationJsonString = param1JSONObject.toString(); 
      return (T)this;
    }
    
    public T addPathParameter(Object param1Object) {
      if (param1Object != null)
        this.mPathParameterMap.putAll(ParseUtil.getParserFactory().getStringMap(param1Object)); 
      return (T)this;
    }
    
    public T addPathParameter(String param1String1, String param1String2) {
      this.mPathParameterMap.put(param1String1, param1String2);
      return (T)this;
    }
    
    public T addPathParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        this.mPathParameterMap.putAll(param1Map); 
      return (T)this;
    }
    
    public T addQueryParameter(Object param1Object) {
      return (T)((param1Object != null) ? (Object)addQueryParameter(ParseUtil.getParserFactory().getStringMap(param1Object)) : this);
    }
    
    public T addQueryParameter(String param1String1, String param1String2) {
      List<String> list2 = this.mQueryParameterMap.get(param1String1);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        this.mQueryParameterMap.put(param1String1, list1);
      } 
      if (!list1.contains(param1String2))
        list1.add(param1String2); 
      return (T)this;
    }
    
    public T addQueryParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        for (Map.Entry<String, String> entry : param1Map.entrySet())
          addQueryParameter((String)entry.getKey(), (String)entry.getValue());  
      return (T)this;
    }
    
    public T addStringBody(String param1String) {
      this.mStringBody = param1String;
      return (T)this;
    }
    
    public T addUrlEncodeFormBodyParameter(Object param1Object) {
      if (param1Object != null)
        this.mUrlEncodedFormBodyParameterMap.putAll(ParseUtil.getParserFactory().getStringMap(param1Object)); 
      return (T)this;
    }
    
    public T addUrlEncodeFormBodyParameter(String param1String1, String param1String2) {
      this.mUrlEncodedFormBodyParameterMap.put(param1String1, param1String2);
      return (T)this;
    }
    
    public T addUrlEncodeFormBodyParameter(Map<String, String> param1Map) {
      if (param1Map != null)
        this.mUrlEncodedFormBodyParameterMap.putAll(param1Map); 
      return (T)this;
    }
    
    public ANRequest build() {
      return new ANRequest<ANRequest>(this);
    }
    
    public T doNotCacheResponse() {
      this.mCacheControl = (new CacheControl.Builder()).noStore().build();
      return (T)this;
    }
    
    public T getResponseOnlyFromNetwork() {
      this.mCacheControl = CacheControl.FORCE_NETWORK;
      return (T)this;
    }
    
    public T getResponseOnlyIfCached() {
      this.mCacheControl = CacheControl.FORCE_CACHE;
      return (T)this;
    }
    
    public T setContentType(String param1String) {
      this.mCustomContentType = param1String;
      return (T)this;
    }
    
    public T setExecutor(Executor param1Executor) {
      this.mExecutor = param1Executor;
      return (T)this;
    }
    
    public T setMaxAgeCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxAge(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setMaxStaleCacheControl(int param1Int, TimeUnit param1TimeUnit) {
      this.mCacheControl = (new CacheControl.Builder()).maxStale(param1Int, param1TimeUnit).build();
      return (T)this;
    }
    
    public T setOkHttpClient(OkHttpClient param1OkHttpClient) {
      this.mOkHttpClient = param1OkHttpClient;
      return (T)this;
    }
    
    public T setPriority(Priority param1Priority) {
      this.mPriority = param1Priority;
      return (T)this;
    }
    
    public T setTag(Object param1Object) {
      this.mTag = param1Object;
      return (T)this;
    }
    
    public T setUserAgent(String param1String) {
      this.mUserAgent = param1String;
      return (T)this;
    }
  }
  
  public static class PutRequestBuilder extends PostRequestBuilder {
    public PutRequestBuilder(String param1String) {
      super(param1String, 2);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\common\ANRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */