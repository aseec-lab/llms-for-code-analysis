package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

class TypefaceCompatApi24Impl extends TypefaceCompatBaseImpl {
  private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
  
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  
  private static final String TAG = "TypefaceCompatApi24Impl";
  
  private static final Method sAddFontWeightStyle;
  
  private static final Method sCreateFromFamiliesWithDefault;
  
  private static final Class<?> sFontFamily;
  
  private static final Constructor<?> sFontFamilyCtor;
  
  static {
    ClassNotFoundException classNotFoundException;
    Class clazz;
    Constructor<?> constructor = null;
    try {
      clazz = Class.forName("android.graphics.FontFamily");
      Constructor<?> constructor1 = clazz.getConstructor(new Class[0]);
      Method method2 = clazz.getMethod("addFontWeightStyle", new Class[] { ByteBuffer.class, int.class, List.class, int.class, boolean.class });
      Method method1 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(clazz, 1).getClass() });
      constructor = constructor1;
    } catch (ClassNotFoundException classNotFoundException1) {
      Log.e("TypefaceCompatApi24Impl", classNotFoundException1.getClass().getName(), classNotFoundException1);
      clazz = null;
      classNotFoundException1 = null;
      classNotFoundException = classNotFoundException1;
    } catch (NoSuchMethodException noSuchMethodException) {}
    sFontFamilyCtor = constructor;
    sFontFamily = clazz;
    sAddFontWeightStyle = (Method)classNotFoundException;
    sCreateFromFamiliesWithDefault = (Method)noSuchMethodException;
  }
  
  private static boolean addFontWeightStyle(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, boolean paramBoolean) {
    try {
      return ((Boolean)sAddFontWeightStyle.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Boolean.valueOf(paramBoolean) })).booleanValue();
    } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {
      return false;
    } 
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject) {
    try {
      Object object = Array.newInstance(sFontFamily, 1);
      Array.set(object, 0, paramObject);
      return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, new Object[] { object });
    } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {
      return null;
    } 
  }
  
  public static boolean isUsable() {
    boolean bool;
    if (sAddFontWeightStyle == null)
      Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation."); 
    if (sAddFontWeightStyle != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static Object newFamily() {
    try {
      return sFontFamilyCtor.newInstance(new Object[0]);
    } catch (IllegalAccessException|InstantiationException|java.lang.reflect.InvocationTargetException illegalAccessException) {
      return null;
    } 
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt) {
    Object object = newFamily();
    if (object == null)
      return null; 
    FontResourcesParserCompat.FontFileResourceEntry[] arrayOfFontFileResourceEntry = paramFontFamilyFilesResourceEntry.getEntries();
    int i = arrayOfFontFileResourceEntry.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = arrayOfFontFileResourceEntry[paramInt];
      ByteBuffer byteBuffer = TypefaceCompatUtil.copyToDirectBuffer(paramContext, paramResources, fontFileResourceEntry.getResourceId());
      if (byteBuffer == null)
        return null; 
      if (!addFontWeightStyle(object, byteBuffer, fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic()))
        return null; 
    } 
    return createFromFamiliesWithDefault(object);
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    Object object = newFamily();
    if (object == null)
      return null; 
    SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
    int i = paramArrayOfFontInfo.length;
    for (byte b = 0; b < i; b++) {
      FontsContractCompat.FontInfo fontInfo = paramArrayOfFontInfo[b];
      Uri uri = fontInfo.getUri();
      ByteBuffer byteBuffer2 = (ByteBuffer)simpleArrayMap.get(uri);
      ByteBuffer byteBuffer1 = byteBuffer2;
      if (byteBuffer2 == null) {
        byteBuffer1 = TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, uri);
        simpleArrayMap.put(uri, byteBuffer1);
      } 
      if (byteBuffer1 == null)
        return null; 
      if (!addFontWeightStyle(object, byteBuffer1, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic()))
        return null; 
    } 
    Typeface typeface = createFromFamiliesWithDefault(object);
    return (typeface == null) ? null : Typeface.create(typeface, paramInt);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\TypefaceCompatApi24Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */