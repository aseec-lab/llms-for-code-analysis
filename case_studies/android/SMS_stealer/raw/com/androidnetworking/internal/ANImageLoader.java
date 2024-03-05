package com.androidnetworking.internal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import java.util.HashMap;
import java.util.LinkedList;

public class ANImageLoader {
  private static final int cacheSize;
  
  private static final int maxMemory;
  
  private static ANImageLoader sInstance;
  
  private int mBatchResponseDelayMs = 100;
  
  private final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap<String, BatchedImageRequest>();
  
  private BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();
  
  private final ImageCache mCache;
  
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  
  private final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap<String, BatchedImageRequest>();
  
  private Runnable mRunnable;
  
  static {
    int i = (int)(Runtime.getRuntime().maxMemory() / 1024L);
    maxMemory = i;
    cacheSize = i / 8;
  }
  
  public ANImageLoader(ImageCache paramImageCache) {
    this.mCache = paramImageCache;
  }
  
  private void batchResponse(String paramString, BatchedImageRequest paramBatchedImageRequest) {
    this.mBatchedResponses.put(paramString, paramBatchedImageRequest);
    if (this.mRunnable == null) {
      Runnable runnable = new Runnable() {
          final ANImageLoader this$0;
          
          public void run() {
            for (ANImageLoader.BatchedImageRequest batchedImageRequest : ANImageLoader.this.mBatchedResponses.values()) {
              for (ANImageLoader.ImageContainer imageContainer : batchedImageRequest.mContainers) {
                if (imageContainer.mListener == null)
                  continue; 
                if (batchedImageRequest.getError() == null) {
                  ANImageLoader.ImageContainer.access$502(imageContainer, batchedImageRequest.mResponseBitmap);
                  imageContainer.mListener.onResponse(imageContainer, false);
                  continue;
                } 
                imageContainer.mListener.onError(batchedImageRequest.getError());
              } 
            } 
            ANImageLoader.this.mBatchedResponses.clear();
            ANImageLoader.access$602(ANImageLoader.this, null);
          }
        };
      this.mRunnable = runnable;
      this.mHandler.postDelayed(runnable, this.mBatchResponseDelayMs);
    } 
  }
  
  private static String getCacheKey(String paramString, int paramInt1, int paramInt2, ImageView.ScaleType paramScaleType) {
    StringBuilder stringBuilder = new StringBuilder(paramString.length() + 12);
    stringBuilder.append("#W");
    stringBuilder.append(paramInt1);
    stringBuilder.append("#H");
    stringBuilder.append(paramInt2);
    stringBuilder.append("#S");
    stringBuilder.append(paramScaleType.ordinal());
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static ImageListener getImageListener(final ImageView view, final int defaultImageResId, final int errorImageResId) {
    return new ImageListener() {
        final int val$defaultImageResId;
        
        final int val$errorImageResId;
        
        final ImageView val$view;
        
        public void onError(ANError param1ANError) {
          int i = errorImageResId;
          if (i != 0)
            view.setImageResource(i); 
        }
        
        public void onResponse(ANImageLoader.ImageContainer param1ImageContainer, boolean param1Boolean) {
          if (param1ImageContainer.getBitmap() != null) {
            view.setImageBitmap(param1ImageContainer.getBitmap());
          } else {
            int i = defaultImageResId;
            if (i != 0)
              view.setImageResource(i); 
          } 
        }
      };
  }
  
  public static ANImageLoader getInstance() {
    // Byte code:
    //   0: getstatic com/androidnetworking/internal/ANImageLoader.sInstance : Lcom/androidnetworking/internal/ANImageLoader;
    //   3: ifnonnull -> 51
    //   6: ldc com/androidnetworking/internal/ANImageLoader
    //   8: monitorenter
    //   9: getstatic com/androidnetworking/internal/ANImageLoader.sInstance : Lcom/androidnetworking/internal/ANImageLoader;
    //   12: ifnonnull -> 39
    //   15: new com/androidnetworking/internal/ANImageLoader
    //   18: astore_0
    //   19: new com/androidnetworking/cache/LruBitmapCache
    //   22: astore_1
    //   23: aload_1
    //   24: getstatic com/androidnetworking/internal/ANImageLoader.cacheSize : I
    //   27: invokespecial <init> : (I)V
    //   30: aload_0
    //   31: aload_1
    //   32: invokespecial <init> : (Lcom/androidnetworking/internal/ANImageLoader$ImageCache;)V
    //   35: aload_0
    //   36: putstatic com/androidnetworking/internal/ANImageLoader.sInstance : Lcom/androidnetworking/internal/ANImageLoader;
    //   39: ldc com/androidnetworking/internal/ANImageLoader
    //   41: monitorexit
    //   42: goto -> 51
    //   45: astore_0
    //   46: ldc com/androidnetworking/internal/ANImageLoader
    //   48: monitorexit
    //   49: aload_0
    //   50: athrow
    //   51: getstatic com/androidnetworking/internal/ANImageLoader.sInstance : Lcom/androidnetworking/internal/ANImageLoader;
    //   54: areturn
    // Exception table:
    //   from	to	target	type
    //   9	39	45	finally
    //   39	42	45	finally
    //   46	49	45	finally
  }
  
  public static void initialize() {
    getInstance();
  }
  
  private void throwIfNotOnMainThread() {
    if (Looper.myLooper() == Looper.getMainLooper())
      return; 
    throw new IllegalStateException("ImageLoader must be invoked from the main thread.");
  }
  
  public ImageContainer get(String paramString, ImageListener paramImageListener) {
    return get(paramString, paramImageListener, 0, 0);
  }
  
  public ImageContainer get(String paramString, ImageListener paramImageListener, int paramInt1, int paramInt2) {
    return get(paramString, paramImageListener, paramInt1, paramInt2, ImageView.ScaleType.CENTER_INSIDE);
  }
  
  public ImageContainer get(String paramString, ImageListener paramImageListener, int paramInt1, int paramInt2, ImageView.ScaleType paramScaleType) {
    ImageContainer imageContainer1;
    throwIfNotOnMainThread();
    String str = getCacheKey(paramString, paramInt1, paramInt2, paramScaleType);
    Bitmap bitmap = this.mCache.getBitmap(str);
    if (bitmap != null) {
      imageContainer1 = new ImageContainer(bitmap, paramString, null, null);
      paramImageListener.onResponse(imageContainer1, true);
      return imageContainer1;
    } 
    ImageContainer imageContainer2 = new ImageContainer(null, (String)imageContainer1, str, paramImageListener);
    paramImageListener.onResponse(imageContainer2, true);
    BatchedImageRequest batchedImageRequest = this.mInFlightRequests.get(str);
    if (batchedImageRequest != null) {
      batchedImageRequest.addContainer(imageContainer2);
      return imageContainer2;
    } 
    ANRequest aNRequest = makeImageRequest((String)imageContainer1, paramInt1, paramInt2, paramScaleType, str);
    this.mInFlightRequests.put(str, new BatchedImageRequest(aNRequest, imageContainer2));
    return imageContainer2;
  }
  
  public ImageCache getImageCache() {
    return this.mCache;
  }
  
  public boolean isCached(String paramString, int paramInt1, int paramInt2) {
    return isCached(paramString, paramInt1, paramInt2, ImageView.ScaleType.CENTER_INSIDE);
  }
  
  public boolean isCached(String paramString, int paramInt1, int paramInt2, ImageView.ScaleType paramScaleType) {
    boolean bool;
    throwIfNotOnMainThread();
    paramString = getCacheKey(paramString, paramInt1, paramInt2, paramScaleType);
    if (this.mCache.getBitmap(paramString) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected ANRequest makeImageRequest(String paramString1, int paramInt1, int paramInt2, ImageView.ScaleType paramScaleType, final String cacheKey) {
    ANRequest aNRequest = AndroidNetworking.get(paramString1).setTag("ImageRequestTag").setBitmapMaxHeight(paramInt2).setBitmapMaxWidth(paramInt1).setImageScaleType(paramScaleType).setBitmapConfig(Bitmap.Config.RGB_565).setBitmapOptions(this.mBitmapOptions).build();
    aNRequest.getAsBitmap(new BitmapRequestListener() {
          final ANImageLoader this$0;
          
          final String val$cacheKey;
          
          public void onError(ANError param1ANError) {
            ANImageLoader.this.onGetImageError(cacheKey, param1ANError);
          }
          
          public void onResponse(Bitmap param1Bitmap) {
            ANImageLoader.this.onGetImageSuccess(cacheKey, param1Bitmap);
          }
        });
    return aNRequest;
  }
  
  protected void onGetImageError(String paramString, ANError paramANError) {
    BatchedImageRequest batchedImageRequest = this.mInFlightRequests.remove(paramString);
    if (batchedImageRequest != null) {
      batchedImageRequest.setError(paramANError);
      batchResponse(paramString, batchedImageRequest);
    } 
  }
  
  protected void onGetImageSuccess(String paramString, Bitmap paramBitmap) {
    this.mCache.putBitmap(paramString, paramBitmap);
    BatchedImageRequest batchedImageRequest = this.mInFlightRequests.remove(paramString);
    if (batchedImageRequest != null) {
      BatchedImageRequest.access$002(batchedImageRequest, paramBitmap);
      batchResponse(paramString, batchedImageRequest);
    } 
  }
  
  public void setBatchedResponseDelay(int paramInt) {
    this.mBatchResponseDelayMs = paramInt;
  }
  
  public void setBitmapDecodeOptions(BitmapFactory.Options paramOptions) {
    this.mBitmapOptions = paramOptions;
  }
  
  private class BatchedImageRequest {
    private ANError mANError;
    
    private final LinkedList<ANImageLoader.ImageContainer> mContainers;
    
    private final ANRequest mRequest;
    
    private Bitmap mResponseBitmap;
    
    final ANImageLoader this$0;
    
    public BatchedImageRequest(ANRequest param1ANRequest, ANImageLoader.ImageContainer param1ImageContainer) {
      LinkedList<ANImageLoader.ImageContainer> linkedList = new LinkedList();
      this.mContainers = linkedList;
      this.mRequest = param1ANRequest;
      linkedList.add(param1ImageContainer);
    }
    
    public void addContainer(ANImageLoader.ImageContainer param1ImageContainer) {
      this.mContainers.add(param1ImageContainer);
    }
    
    public ANError getError() {
      return this.mANError;
    }
    
    public boolean removeContainerAndCancelIfNecessary(ANImageLoader.ImageContainer param1ImageContainer) {
      this.mContainers.remove(param1ImageContainer);
      if (this.mContainers.size() == 0) {
        this.mRequest.cancel(true);
        if (this.mRequest.isCanceled()) {
          this.mRequest.destroy();
          ANRequestQueue.getInstance().finish(this.mRequest);
        } 
        return true;
      } 
      return false;
    }
    
    public void setError(ANError param1ANError) {
      this.mANError = param1ANError;
    }
  }
  
  public static interface ImageCache {
    void evictAllBitmap();
    
    void evictBitmap(String param1String);
    
    Bitmap getBitmap(String param1String);
    
    void putBitmap(String param1String, Bitmap param1Bitmap);
  }
  
  public class ImageContainer {
    private Bitmap mBitmap;
    
    private final String mCacheKey;
    
    private final ANImageLoader.ImageListener mListener;
    
    private final String mRequestUrl;
    
    final ANImageLoader this$0;
    
    public ImageContainer(Bitmap param1Bitmap, String param1String1, String param1String2, ANImageLoader.ImageListener param1ImageListener) {
      this.mBitmap = param1Bitmap;
      this.mRequestUrl = param1String1;
      this.mCacheKey = param1String2;
      this.mListener = param1ImageListener;
    }
    
    public void cancelRequest() {
      if (this.mListener == null)
        return; 
      ANImageLoader.BatchedImageRequest batchedImageRequest = (ANImageLoader.BatchedImageRequest)ANImageLoader.this.mInFlightRequests.get(this.mCacheKey);
      if (batchedImageRequest != null) {
        if (batchedImageRequest.removeContainerAndCancelIfNecessary(this))
          ANImageLoader.this.mInFlightRequests.remove(this.mCacheKey); 
      } else {
        batchedImageRequest = (ANImageLoader.BatchedImageRequest)ANImageLoader.this.mBatchedResponses.get(this.mCacheKey);
        if (batchedImageRequest != null) {
          batchedImageRequest.removeContainerAndCancelIfNecessary(this);
          if (batchedImageRequest.mContainers.size() == 0)
            ANImageLoader.this.mBatchedResponses.remove(this.mCacheKey); 
        } 
      } 
    }
    
    public Bitmap getBitmap() {
      return this.mBitmap;
    }
    
    public String getRequestUrl() {
      return this.mRequestUrl;
    }
  }
  
  public static interface ImageListener {
    void onError(ANError param1ANError);
    
    void onResponse(ANImageLoader.ImageContainer param1ImageContainer, boolean param1Boolean);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\internal\ANImageLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */