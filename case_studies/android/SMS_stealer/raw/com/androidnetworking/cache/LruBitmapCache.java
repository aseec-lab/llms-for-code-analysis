package com.androidnetworking.cache;

import android.graphics.Bitmap;
import com.androidnetworking.internal.ANImageLoader;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ANImageLoader.ImageCache {
  public LruBitmapCache(int paramInt) {
    super(paramInt);
  }
  
  public void evictAllBitmap() {
    evictAll();
  }
  
  public void evictBitmap(String paramString) {
    remove(paramString);
  }
  
  public Bitmap getBitmap(String paramString) {
    return get(paramString);
  }
  
  public void putBitmap(String paramString, Bitmap paramBitmap) {
    put(paramString, paramBitmap);
  }
  
  protected int sizeOf(String paramString, Bitmap paramBitmap) {
    return paramBitmap.getRowBytes() * paramBitmap.getHeight();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\cache\LruBitmapCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */