package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;

final class RealCall implements Call {
  final OkHttpClient client;
  
  private EventListener eventListener;
  
  private boolean executed;
  
  final boolean forWebSocket;
  
  final Request originalRequest;
  
  final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;
  
  private RealCall(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean) {
    this.client = paramOkHttpClient;
    this.originalRequest = paramRequest;
    this.forWebSocket = paramBoolean;
    this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(paramOkHttpClient, paramBoolean);
  }
  
  private void captureCallStackTrace() {
    Object object = Platform.get().getStackTraceForCloseable("response.body().close()");
    this.retryAndFollowUpInterceptor.setCallStackTrace(object);
  }
  
  static RealCall newRealCall(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean) {
    RealCall realCall = new RealCall(paramOkHttpClient, paramRequest, paramBoolean);
    realCall.eventListener = paramOkHttpClient.eventListenerFactory().create(realCall);
    return realCall;
  }
  
  public void cancel() {
    this.retryAndFollowUpInterceptor.cancel();
  }
  
  public RealCall clone() {
    return newRealCall(this.client, this.originalRequest, this.forWebSocket);
  }
  
  public void enqueue(Callback paramCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield executed : Z
    //   6: ifne -> 48
    //   9: aload_0
    //   10: iconst_1
    //   11: putfield executed : Z
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_0
    //   17: invokespecial captureCallStackTrace : ()V
    //   20: aload_0
    //   21: getfield eventListener : Lokhttp3/EventListener;
    //   24: aload_0
    //   25: invokevirtual callStart : (Lokhttp3/Call;)V
    //   28: aload_0
    //   29: getfield client : Lokhttp3/OkHttpClient;
    //   32: invokevirtual dispatcher : ()Lokhttp3/Dispatcher;
    //   35: new okhttp3/RealCall$AsyncCall
    //   38: dup
    //   39: aload_0
    //   40: aload_1
    //   41: invokespecial <init> : (Lokhttp3/RealCall;Lokhttp3/Callback;)V
    //   44: invokevirtual enqueue : (Lokhttp3/RealCall$AsyncCall;)V
    //   47: return
    //   48: new java/lang/IllegalStateException
    //   51: astore_1
    //   52: aload_1
    //   53: ldc 'Already Executed'
    //   55: invokespecial <init> : (Ljava/lang/String;)V
    //   58: aload_1
    //   59: athrow
    //   60: astore_1
    //   61: aload_0
    //   62: monitorexit
    //   63: aload_1
    //   64: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	60	finally
    //   48	60	60	finally
    //   61	63	60	finally
  }
  
  public Response execute() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield executed : Z
    //   6: ifne -> 102
    //   9: aload_0
    //   10: iconst_1
    //   11: putfield executed : Z
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_0
    //   17: invokespecial captureCallStackTrace : ()V
    //   20: aload_0
    //   21: getfield eventListener : Lokhttp3/EventListener;
    //   24: aload_0
    //   25: invokevirtual callStart : (Lokhttp3/Call;)V
    //   28: aload_0
    //   29: getfield client : Lokhttp3/OkHttpClient;
    //   32: invokevirtual dispatcher : ()Lokhttp3/Dispatcher;
    //   35: aload_0
    //   36: invokevirtual executed : (Lokhttp3/RealCall;)V
    //   39: aload_0
    //   40: invokevirtual getResponseWithInterceptorChain : ()Lokhttp3/Response;
    //   43: astore_1
    //   44: aload_1
    //   45: ifnull -> 61
    //   48: aload_0
    //   49: getfield client : Lokhttp3/OkHttpClient;
    //   52: invokevirtual dispatcher : ()Lokhttp3/Dispatcher;
    //   55: aload_0
    //   56: invokevirtual finished : (Lokhttp3/RealCall;)V
    //   59: aload_1
    //   60: areturn
    //   61: new java/io/IOException
    //   64: astore_1
    //   65: aload_1
    //   66: ldc 'Canceled'
    //   68: invokespecial <init> : (Ljava/lang/String;)V
    //   71: aload_1
    //   72: athrow
    //   73: astore_1
    //   74: goto -> 89
    //   77: astore_1
    //   78: aload_0
    //   79: getfield eventListener : Lokhttp3/EventListener;
    //   82: aload_0
    //   83: aload_1
    //   84: invokevirtual callFailed : (Lokhttp3/Call;Ljava/io/IOException;)V
    //   87: aload_1
    //   88: athrow
    //   89: aload_0
    //   90: getfield client : Lokhttp3/OkHttpClient;
    //   93: invokevirtual dispatcher : ()Lokhttp3/Dispatcher;
    //   96: aload_0
    //   97: invokevirtual finished : (Lokhttp3/RealCall;)V
    //   100: aload_1
    //   101: athrow
    //   102: new java/lang/IllegalStateException
    //   105: astore_1
    //   106: aload_1
    //   107: ldc 'Already Executed'
    //   109: invokespecial <init> : (Ljava/lang/String;)V
    //   112: aload_1
    //   113: athrow
    //   114: astore_1
    //   115: aload_0
    //   116: monitorexit
    //   117: aload_1
    //   118: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	114	finally
    //   28	44	77	java/io/IOException
    //   28	44	73	finally
    //   61	73	77	java/io/IOException
    //   61	73	73	finally
    //   78	89	73	finally
    //   102	114	114	finally
    //   115	117	114	finally
  }
  
  Response getResponseWithInterceptorChain() throws IOException {
    ArrayList<Interceptor> arrayList = new ArrayList();
    arrayList.addAll(this.client.interceptors());
    arrayList.add(this.retryAndFollowUpInterceptor);
    arrayList.add(new BridgeInterceptor(this.client.cookieJar()));
    arrayList.add(new CacheInterceptor(this.client.internalCache()));
    arrayList.add(new ConnectInterceptor(this.client));
    if (!this.forWebSocket)
      arrayList.addAll(this.client.networkInterceptors()); 
    arrayList.add(new CallServerInterceptor(this.forWebSocket));
    return (new RealInterceptorChain(arrayList, null, null, null, 0, this.originalRequest, this, this.eventListener, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis())).proceed(this.originalRequest);
  }
  
  public boolean isCanceled() {
    return this.retryAndFollowUpInterceptor.isCanceled();
  }
  
  public boolean isExecuted() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield executed : Z
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  String redactedUrl() {
    return this.originalRequest.url().redact();
  }
  
  public Request request() {
    return this.originalRequest;
  }
  
  StreamAllocation streamAllocation() {
    return this.retryAndFollowUpInterceptor.streamAllocation();
  }
  
  String toLoggableString() {
    String str;
    StringBuilder stringBuilder = new StringBuilder();
    if (isCanceled()) {
      str = "canceled ";
    } else {
      str = "";
    } 
    stringBuilder.append(str);
    if (this.forWebSocket) {
      str = "web socket";
    } else {
      str = "call";
    } 
    stringBuilder.append(str);
    stringBuilder.append(" to ");
    stringBuilder.append(redactedUrl());
    return stringBuilder.toString();
  }
  
  final class AsyncCall extends NamedRunnable {
    private final Callback responseCallback;
    
    final RealCall this$0;
    
    AsyncCall(Callback param1Callback) {
      super("OkHttp %s", new Object[] { this$0.redactedUrl() });
      this.responseCallback = param1Callback;
    }
    
    protected void execute() {
      // Byte code:
      //   0: iconst_1
      //   1: istore_1
      //   2: aload_0
      //   3: getfield this$0 : Lokhttp3/RealCall;
      //   6: invokevirtual getResponseWithInterceptorChain : ()Lokhttp3/Response;
      //   9: astore_3
      //   10: aload_0
      //   11: getfield this$0 : Lokhttp3/RealCall;
      //   14: getfield retryAndFollowUpInterceptor : Lokhttp3/internal/http/RetryAndFollowUpInterceptor;
      //   17: invokevirtual isCanceled : ()Z
      //   20: istore_2
      //   21: iload_2
      //   22: ifeq -> 61
      //   25: aload_0
      //   26: getfield responseCallback : Lokhttp3/Callback;
      //   29: astore_3
      //   30: aload_0
      //   31: getfield this$0 : Lokhttp3/RealCall;
      //   34: astore #5
      //   36: new java/io/IOException
      //   39: astore #4
      //   41: aload #4
      //   43: ldc 'Canceled'
      //   45: invokespecial <init> : (Ljava/lang/String;)V
      //   48: aload_3
      //   49: aload #5
      //   51: aload #4
      //   53: invokeinterface onFailure : (Lokhttp3/Call;Ljava/io/IOException;)V
      //   58: goto -> 75
      //   61: aload_0
      //   62: getfield responseCallback : Lokhttp3/Callback;
      //   65: aload_0
      //   66: getfield this$0 : Lokhttp3/RealCall;
      //   69: aload_3
      //   70: invokeinterface onResponse : (Lokhttp3/Call;Lokhttp3/Response;)V
      //   75: aload_0
      //   76: getfield this$0 : Lokhttp3/RealCall;
      //   79: getfield client : Lokhttp3/OkHttpClient;
      //   82: invokevirtual dispatcher : ()Lokhttp3/Dispatcher;
      //   85: aload_0
      //   86: invokevirtual finished : (Lokhttp3/RealCall$AsyncCall;)V
      //   89: goto -> 190
      //   92: astore_3
      //   93: goto -> 103
      //   96: astore_3
      //   97: goto -> 191
      //   100: astore_3
      //   101: iconst_0
      //   102: istore_1
      //   103: iload_1
      //   104: ifeq -> 158
      //   107: invokestatic get : ()Lokhttp3/internal/platform/Platform;
      //   110: astore #4
      //   112: new java/lang/StringBuilder
      //   115: astore #5
      //   117: aload #5
      //   119: invokespecial <init> : ()V
      //   122: aload #5
      //   124: ldc 'Callback failure for '
      //   126: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   129: pop
      //   130: aload #5
      //   132: aload_0
      //   133: getfield this$0 : Lokhttp3/RealCall;
      //   136: invokevirtual toLoggableString : ()Ljava/lang/String;
      //   139: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   142: pop
      //   143: aload #4
      //   145: iconst_4
      //   146: aload #5
      //   148: invokevirtual toString : ()Ljava/lang/String;
      //   151: aload_3
      //   152: invokevirtual log : (ILjava/lang/String;Ljava/lang/Throwable;)V
      //   155: goto -> 75
      //   158: aload_0
      //   159: getfield this$0 : Lokhttp3/RealCall;
      //   162: invokestatic access$000 : (Lokhttp3/RealCall;)Lokhttp3/EventListener;
      //   165: aload_0
      //   166: getfield this$0 : Lokhttp3/RealCall;
      //   169: aload_3
      //   170: invokevirtual callFailed : (Lokhttp3/Call;Ljava/io/IOException;)V
      //   173: aload_0
      //   174: getfield responseCallback : Lokhttp3/Callback;
      //   177: aload_0
      //   178: getfield this$0 : Lokhttp3/RealCall;
      //   181: aload_3
      //   182: invokeinterface onFailure : (Lokhttp3/Call;Ljava/io/IOException;)V
      //   187: goto -> 75
      //   190: return
      //   191: aload_0
      //   192: getfield this$0 : Lokhttp3/RealCall;
      //   195: getfield client : Lokhttp3/OkHttpClient;
      //   198: invokevirtual dispatcher : ()Lokhttp3/Dispatcher;
      //   201: aload_0
      //   202: invokevirtual finished : (Lokhttp3/RealCall$AsyncCall;)V
      //   205: aload_3
      //   206: athrow
      // Exception table:
      //   from	to	target	type
      //   2	21	100	java/io/IOException
      //   2	21	96	finally
      //   25	58	92	java/io/IOException
      //   25	58	96	finally
      //   61	75	92	java/io/IOException
      //   61	75	96	finally
      //   107	155	96	finally
      //   158	187	96	finally
    }
    
    RealCall get() {
      return RealCall.this;
    }
    
    String host() {
      return RealCall.this.originalRequest.url().host();
    }
    
    Request request() {
      return RealCall.this.originalRequest;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\RealCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */