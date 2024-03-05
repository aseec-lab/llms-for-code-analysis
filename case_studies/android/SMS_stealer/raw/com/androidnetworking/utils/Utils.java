package com.androidnetworking.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.core.Core;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import okhttp3.Cache;
import okhttp3.Response;
import okio.Okio;
import okio.Source;

public class Utils {
  public static ANResponse<Bitmap> decodeBitmap(Response paramResponse, int paramInt1, int paramInt2, Bitmap.Config paramConfig, BitmapFactory.Options paramOptions, ImageView.ScaleType paramScaleType) {
    Bitmap bitmap;
    byte[] arrayOfByte = new byte[0];
    try {
      byte[] arrayOfByte1 = Okio.buffer((Source)paramResponse.body().source()).readByteArray();
      arrayOfByte = arrayOfByte1;
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    if (paramInt1 == 0 && paramInt2 == 0) {
      paramOptions.inPreferredConfig = paramConfig;
      bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, paramOptions);
    } else {
      paramOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, paramOptions);
      int j = paramOptions.outWidth;
      int k = paramOptions.outHeight;
      int i = getResizedDimension(paramInt1, paramInt2, j, k, paramScaleType);
      paramInt1 = getResizedDimension(paramInt2, paramInt1, k, j, paramScaleType);
      paramOptions.inJustDecodeBounds = false;
      paramOptions.inSampleSize = findBestSampleSize(j, k, i, paramInt1);
      bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, paramOptions);
      if (bitmap != null && (bitmap.getWidth() > i || bitmap.getHeight() > paramInt1)) {
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, i, paramInt1, true);
        bitmap.recycle();
        bitmap = bitmap1;
      } 
    } 
    return (bitmap == null) ? ANResponse.failed(getErrorForParse(new ANError(paramResponse))) : ANResponse.success(bitmap);
  }
  
  public static ANResponse<Bitmap> decodeBitmap(Response paramResponse, int paramInt1, int paramInt2, Bitmap.Config paramConfig, ImageView.ScaleType paramScaleType) {
    return decodeBitmap(paramResponse, paramInt1, paramInt2, paramConfig, new BitmapFactory.Options(), paramScaleType);
  }
  
  public static int findBestSampleSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    double d = Math.min(paramInt1 / paramInt3, paramInt2 / paramInt4);
    float f = 1.0F;
    while (true) {
      float f1 = 2.0F * f;
      if (f1 <= d) {
        f = f1;
        continue;
      } 
      return (int)f;
    } 
  }
  
  public static Cache getCache(Context paramContext, int paramInt, String paramString) {
    return new Cache(getDiskCacheDir(paramContext, paramString), paramInt);
  }
  
  public static File getDiskCacheDir(Context paramContext, String paramString) {
    return new File(paramContext.getCacheDir(), paramString);
  }
  
  public static ANError getErrorForConnection(ANError paramANError) {
    paramANError.setErrorDetail("connectionError");
    paramANError.setErrorCode(0);
    return paramANError;
  }
  
  public static ANError getErrorForParse(ANError paramANError) {
    paramANError.setErrorCode(0);
    paramANError.setErrorDetail("parseError");
    return paramANError;
  }
  
  public static ANError getErrorForServerResponse(ANError paramANError, ANRequest paramANRequest, int paramInt) {
    paramANError = paramANRequest.parseNetworkError(paramANError);
    paramANError.setErrorCode(paramInt);
    paramANError.setErrorDetail("responseFromServerError");
    return paramANError;
  }
  
  public static String getMimeType(String paramString) {
    String str = URLConnection.getFileNameMap().getContentTypeFor(paramString);
    paramString = str;
    if (str == null)
      paramString = "application/octet-stream"; 
    return paramString;
  }
  
  private static int getResizedDimension(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ImageView.ScaleType paramScaleType) {
    if (paramInt1 == 0 && paramInt2 == 0)
      return paramInt3; 
    if (paramScaleType == ImageView.ScaleType.FIT_XY)
      return (paramInt1 == 0) ? paramInt3 : paramInt1; 
    if (paramInt1 == 0) {
      double d = paramInt2 / paramInt4;
      return (int)(paramInt3 * d);
    } 
    if (paramInt2 == 0)
      return paramInt1; 
    double d1 = paramInt4 / paramInt3;
    if (paramScaleType == ImageView.ScaleType.CENTER_CROP) {
      double d5 = paramInt1;
      double d4 = paramInt2;
      if (d5 * d1 < d4)
        paramInt1 = (int)(d4 / d1); 
      return paramInt1;
    } 
    double d3 = paramInt1;
    double d2 = paramInt2;
    if (d3 * d1 > d2)
      paramInt1 = (int)(d2 / d1); 
    return paramInt1;
  }
  
  public static void saveFile(Response paramResponse, String paramString1, String paramString2) throws IOException {
    // Byte code:
    //   0: sipush #2048
    //   3: newarray byte
    //   5: astore #6
    //   7: aconst_null
    //   8: astore #5
    //   10: aload_0
    //   11: invokevirtual body : ()Lokhttp3/ResponseBody;
    //   14: invokevirtual byteStream : ()Ljava/io/InputStream;
    //   17: astore #4
    //   19: new java/io/File
    //   22: astore_0
    //   23: aload_0
    //   24: aload_1
    //   25: invokespecial <init> : (Ljava/lang/String;)V
    //   28: aload_0
    //   29: invokevirtual exists : ()Z
    //   32: ifne -> 40
    //   35: aload_0
    //   36: invokevirtual mkdirs : ()Z
    //   39: pop
    //   40: new java/io/File
    //   43: astore_1
    //   44: aload_1
    //   45: aload_0
    //   46: aload_2
    //   47: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   50: new java/io/FileOutputStream
    //   53: astore_0
    //   54: aload_0
    //   55: aload_1
    //   56: invokespecial <init> : (Ljava/io/File;)V
    //   59: aload #4
    //   61: aload #6
    //   63: invokevirtual read : ([B)I
    //   66: istore_3
    //   67: iload_3
    //   68: iconst_m1
    //   69: if_icmpeq -> 83
    //   72: aload_0
    //   73: aload #6
    //   75: iconst_0
    //   76: iload_3
    //   77: invokevirtual write : ([BII)V
    //   80: goto -> 59
    //   83: aload_0
    //   84: invokevirtual flush : ()V
    //   87: aload #4
    //   89: ifnull -> 105
    //   92: aload #4
    //   94: invokevirtual close : ()V
    //   97: goto -> 105
    //   100: astore_1
    //   101: aload_1
    //   102: invokevirtual printStackTrace : ()V
    //   105: aload_0
    //   106: invokevirtual close : ()V
    //   109: goto -> 117
    //   112: astore_0
    //   113: aload_0
    //   114: invokevirtual printStackTrace : ()V
    //   117: return
    //   118: astore_1
    //   119: goto -> 125
    //   122: astore_1
    //   123: aconst_null
    //   124: astore_0
    //   125: aload #4
    //   127: astore_2
    //   128: goto -> 137
    //   131: astore_1
    //   132: aconst_null
    //   133: astore_0
    //   134: aload #5
    //   136: astore_2
    //   137: aload_2
    //   138: ifnull -> 153
    //   141: aload_2
    //   142: invokevirtual close : ()V
    //   145: goto -> 153
    //   148: astore_2
    //   149: aload_2
    //   150: invokevirtual printStackTrace : ()V
    //   153: aload_0
    //   154: ifnull -> 169
    //   157: aload_0
    //   158: invokevirtual close : ()V
    //   161: goto -> 169
    //   164: astore_0
    //   165: aload_0
    //   166: invokevirtual printStackTrace : ()V
    //   169: aload_1
    //   170: athrow
    // Exception table:
    //   from	to	target	type
    //   10	19	131	finally
    //   19	40	122	finally
    //   40	59	122	finally
    //   59	67	118	finally
    //   72	80	118	finally
    //   83	87	118	finally
    //   92	97	100	java/io/IOException
    //   105	109	112	java/io/IOException
    //   141	145	148	java/io/IOException
    //   157	161	164	java/io/IOException
  }
  
  public static void sendAnalytics(final AnalyticsListener analyticsListener, final long timeTakenInMillis, final long bytesSent, final long bytesReceived, final boolean isFromCache) {
    Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
          final AnalyticsListener val$analyticsListener;
          
          final long val$bytesReceived;
          
          final long val$bytesSent;
          
          final boolean val$isFromCache;
          
          final long val$timeTakenInMillis;
          
          public void run() {
            AnalyticsListener analyticsListener = analyticsListener;
            if (analyticsListener != null)
              analyticsListener.onReceived(timeTakenInMillis, bytesSent, bytesReceived, isFromCache); 
          }
        });
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworkin\\utils\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */