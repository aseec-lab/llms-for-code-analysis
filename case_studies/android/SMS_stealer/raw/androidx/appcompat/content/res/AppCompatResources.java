package androidx.appcompat.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ColorStateListInflaterCompat;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

public final class AppCompatResources {
  private static final String LOG_TAG = "AppCompatResources";
  
  private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<TypedValue>();
  
  private static final Object sColorStateCacheLock;
  
  private static final WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>>(0);
  
  static {
    sColorStateCacheLock = new Object();
  }
  
  private static void addColorStateListToCache(Context paramContext, int paramInt, ColorStateList paramColorStateList) {
    synchronized (sColorStateCacheLock) {
      SparseArray<ColorStateListCacheEntry> sparseArray2 = sColorStateCaches.get(paramContext);
      SparseArray<ColorStateListCacheEntry> sparseArray1 = sparseArray2;
      if (sparseArray2 == null) {
        sparseArray1 = new SparseArray();
        this();
        sColorStateCaches.put(paramContext, sparseArray1);
      } 
      ColorStateListCacheEntry colorStateListCacheEntry = new ColorStateListCacheEntry();
      this(paramColorStateList, paramContext.getResources().getConfiguration());
      sparseArray1.append(paramInt, colorStateListCacheEntry);
      return;
    } 
  }
  
  private static ColorStateList getCachedColorStateList(Context paramContext, int paramInt) {
    synchronized (sColorStateCacheLock) {
      SparseArray sparseArray = sColorStateCaches.get(paramContext);
      if (sparseArray != null && sparseArray.size() > 0) {
        ColorStateListCacheEntry colorStateListCacheEntry = (ColorStateListCacheEntry)sparseArray.get(paramInt);
        if (colorStateListCacheEntry != null) {
          if (colorStateListCacheEntry.configuration.equals(paramContext.getResources().getConfiguration()))
            return colorStateListCacheEntry.value; 
          sparseArray.remove(paramInt);
        } 
      } 
      return null;
    } 
  }
  
  public static ColorStateList getColorStateList(Context paramContext, int paramInt) {
    if (Build.VERSION.SDK_INT >= 23)
      return paramContext.getColorStateList(paramInt); 
    ColorStateList colorStateList = getCachedColorStateList(paramContext, paramInt);
    if (colorStateList != null)
      return colorStateList; 
    colorStateList = inflateColorStateList(paramContext, paramInt);
    if (colorStateList != null) {
      addColorStateListToCache(paramContext, paramInt, colorStateList);
      return colorStateList;
    } 
    return ContextCompat.getColorStateList(paramContext, paramInt);
  }
  
  public static Drawable getDrawable(Context paramContext, int paramInt) {
    return ResourceManagerInternal.get().getDrawable(paramContext, paramInt);
  }
  
  private static TypedValue getTypedValue() {
    TypedValue typedValue2 = TL_TYPED_VALUE.get();
    TypedValue typedValue1 = typedValue2;
    if (typedValue2 == null) {
      typedValue1 = new TypedValue();
      TL_TYPED_VALUE.set(typedValue1);
    } 
    return typedValue1;
  }
  
  private static ColorStateList inflateColorStateList(Context paramContext, int paramInt) {
    if (isColorInt(paramContext, paramInt))
      return null; 
    Resources resources = paramContext.getResources();
    XmlResourceParser xmlResourceParser = resources.getXml(paramInt);
    try {
      return ColorStateListInflaterCompat.createFromXml(resources, (XmlPullParser)xmlResourceParser, paramContext.getTheme());
    } catch (Exception exception) {
      Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", exception);
      return null;
    } 
  }
  
  private static boolean isColorInt(Context paramContext, int paramInt) {
    Resources resources = paramContext.getResources();
    TypedValue typedValue = getTypedValue();
    boolean bool = true;
    resources.getValue(paramInt, typedValue, true);
    if (typedValue.type < 28 || typedValue.type > 31)
      bool = false; 
    return bool;
  }
  
  private static class ColorStateListCacheEntry {
    final Configuration configuration;
    
    final ColorStateList value;
    
    ColorStateListCacheEntry(ColorStateList param1ColorStateList, Configuration param1Configuration) {
      this.value = param1ColorStateList;
      this.configuration = param1Configuration;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\content\res\AppCompatResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */