package androidx.appcompat.app;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.Map;

class ResourcesFlusher {
  private static final String TAG = "ResourcesFlusher";
  
  private static Field sDrawableCacheField;
  
  private static boolean sDrawableCacheFieldFetched;
  
  private static Field sResourcesImplField;
  
  private static boolean sResourcesImplFieldFetched;
  
  private static Class<?> sThemedResourceCacheClazz;
  
  private static boolean sThemedResourceCacheClazzFetched;
  
  private static Field sThemedResourceCache_mUnthemedEntriesField;
  
  private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;
  
  static void flush(Resources paramResources) {
    if (Build.VERSION.SDK_INT >= 28)
      return; 
    if (Build.VERSION.SDK_INT >= 24) {
      flushNougats(paramResources);
    } else if (Build.VERSION.SDK_INT >= 23) {
      flushMarshmallows(paramResources);
    } else if (Build.VERSION.SDK_INT >= 21) {
      flushLollipops(paramResources);
    } 
  }
  
  private static void flushLollipops(Resources paramResources) {
    if (!sDrawableCacheFieldFetched) {
      try {
        Field field1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", noSuchFieldException);
      } 
      sDrawableCacheFieldFetched = true;
    } 
    Field field = sDrawableCacheField;
    if (field != null) {
      IllegalAccessException illegalAccessException2 = null;
      try {
        Map map = (Map)field.get(paramResources);
      } catch (IllegalAccessException illegalAccessException1) {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", illegalAccessException1);
        illegalAccessException1 = illegalAccessException2;
      } 
      if (illegalAccessException1 != null)
        illegalAccessException1.clear(); 
    } 
  }
  
  private static void flushMarshmallows(Resources paramResources) {
    if (!sDrawableCacheFieldFetched) {
      try {
        Field field1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", noSuchFieldException);
      } 
      sDrawableCacheFieldFetched = true;
    } 
    Object object1 = null;
    Field field = sDrawableCacheField;
    Object object = object1;
    if (field != null)
      try {
        object = field.get(paramResources);
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", illegalAccessException);
        object = object1;
      }  
    if (object == null)
      return; 
    flushThemedResourcesCache(object);
  }
  
  private static void flushNougats(Resources paramResources) {
    if (!sResourcesImplFieldFetched) {
      try {
        Field field = Resources.class.getDeclaredField("mResourcesImpl");
        sResourcesImplField = field;
        field.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", noSuchFieldException);
      } 
      sResourcesImplFieldFetched = true;
    } 
    Field field1 = sResourcesImplField;
    if (field1 == null)
      return; 
    Field field2 = null;
    try {
      object = field1.get(paramResources);
    } catch (IllegalAccessException object) {
      Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", (Throwable)object);
      object = null;
    } 
    if (object == null)
      return; 
    if (!sDrawableCacheFieldFetched) {
      try {
        field1 = object.getClass().getDeclaredField("mDrawableCache");
        sDrawableCacheField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", noSuchFieldException);
      } 
      sDrawableCacheFieldFetched = true;
    } 
    Field field3 = sDrawableCacheField;
    field1 = field2;
    if (field3 != null)
      try {
        Object object1 = field3.get(object);
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", illegalAccessException);
        field1 = field2;
      }  
    if (field1 != null)
      flushThemedResourcesCache(field1); 
  }
  
  private static void flushThemedResourcesCache(Object paramObject) {
    Class<?> clazz1;
    if (!sThemedResourceCacheClazzFetched) {
      try {
        sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
      } catch (ClassNotFoundException classNotFoundException) {
        Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", classNotFoundException);
      } 
      sThemedResourceCacheClazzFetched = true;
    } 
    Class<?> clazz2 = sThemedResourceCacheClazz;
    if (clazz2 == null)
      return; 
    if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
      try {
        Field field1 = clazz2.getDeclaredField("mUnthemedEntries");
        sThemedResourceCache_mUnthemedEntriesField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", noSuchFieldException);
      } 
      sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
    } 
    Field field = sThemedResourceCache_mUnthemedEntriesField;
    if (field == null)
      return; 
    clazz2 = null;
    try {
      paramObject = field.get(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", illegalAccessException);
      clazz1 = clazz2;
    } 
    if (clazz1 != null)
      clazz1.clear(); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\app\ResourcesFlusher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */