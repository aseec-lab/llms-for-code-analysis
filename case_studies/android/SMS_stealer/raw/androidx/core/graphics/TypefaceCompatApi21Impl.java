package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
  private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
  
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  
  private static final String TAG = "TypefaceCompatApi21Impl";
  
  private static Method sAddFontWeightStyle;
  
  private static Method sCreateFromFamiliesWithDefault;
  
  private static Class<?> sFontFamily;
  
  private static Constructor<?> sFontFamilyCtor;
  
  private static boolean sHasInitBeenCalled;
  
  private static boolean addFontWeightStyle(Object paramObject, String paramString, int paramInt, boolean paramBoolean) {
    init();
    try {
      return ((Boolean)sAddFontWeightStyle.invoke(paramObject, new Object[] { paramString, Integer.valueOf(paramInt), Boolean.valueOf(paramBoolean) })).booleanValue();
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject) {
    init();
    try {
      Object object = Array.newInstance(sFontFamily, 1);
      Array.set(object, 0, paramObject);
      return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, new Object[] { object });
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private File getFile(ParcelFileDescriptor paramParcelFileDescriptor) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("/proc/self/fd/");
      stringBuilder.append(paramParcelFileDescriptor.getFd());
      String str = Os.readlink(stringBuilder.toString());
      if (OsConstants.S_ISREG((Os.stat(str)).st_mode))
        return new File(str); 
    } catch (ErrnoException errnoException) {}
    return null;
  }
  
  private static void init() {
    Class clazz1;
    Class clazz2;
    if (sHasInitBeenCalled)
      return; 
    sHasInitBeenCalled = true;
    Constructor<?> constructor = null;
    try {
      clazz1 = Class.forName("android.graphics.FontFamily");
      Constructor<?> constructor1 = clazz1.getConstructor(new Class[0]);
      Method method2 = clazz1.getMethod("addFontWeightStyle", new Class[] { String.class, int.class, boolean.class });
      Method method1 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(clazz1, 1).getClass() });
      constructor = constructor1;
    } catch (ClassNotFoundException classNotFoundException) {
      Log.e("TypefaceCompatApi21Impl", classNotFoundException.getClass().getName(), classNotFoundException);
      classNotFoundException = null;
      clazz1 = null;
      clazz2 = clazz1;
    } catch (NoSuchMethodException noSuchMethodException) {}
    sFontFamilyCtor = constructor;
    sFontFamily = clazz1;
    sAddFontWeightStyle = (Method)clazz2;
    sCreateFromFamiliesWithDefault = (Method)noSuchMethodException;
  }
  
  private static Object newFamily() {
    init();
    try {
      return sFontFamilyCtor.newInstance(new Object[0]);
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InstantiationException instantiationException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt) {
    Object object = newFamily();
    FontResourcesParserCompat.FontFileResourceEntry[] arrayOfFontFileResourceEntry = paramFontFamilyFilesResourceEntry.getEntries();
    int i = arrayOfFontFileResourceEntry.length;
    paramInt = 0;
    while (paramInt < i) {
      FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = arrayOfFontFileResourceEntry[paramInt];
      File file = TypefaceCompatUtil.getTempFile(paramContext);
      if (file == null)
        return null; 
      try {
        boolean bool = TypefaceCompatUtil.copyToFile(file, paramResources, fontFileResourceEntry.getResourceId());
        if (!bool)
          return null; 
        bool = addFontWeightStyle(object, file.getPath(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic());
        if (!bool)
          return null; 
        file.delete();
      } catch (RuntimeException runtimeException) {
        return null;
      } finally {
        file.delete();
      } 
    } 
    return createFromFamiliesWithDefault(object);
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    if (paramArrayOfFontInfo.length < 1)
      return null; 
    FontsContractCompat.FontInfo fontInfo = findBestInfo(paramArrayOfFontInfo, paramInt);
    ContentResolver contentResolver = paramContext.getContentResolver();
    try {
      ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(fontInfo.getUri(), "r", paramCancellationSignal);
      if (parcelFileDescriptor == null) {
        if (parcelFileDescriptor != null)
          parcelFileDescriptor.close(); 
        return null;
      } 
      try {
        FileInputStream fileInputStream;
        File file = getFile(parcelFileDescriptor);
        if (file == null || !file.canRead()) {
          fileInputStream = new FileInputStream();
          this(parcelFileDescriptor.getFileDescriptor());
          try {
            return createFromInputStream(paramContext, fileInputStream);
          } finally {
            try {
              fileInputStream.close();
            } finally {
              fileInputStream = null;
            } 
          } 
        } 
        return Typeface.createFromFile((File)fileInputStream);
      } finally {
        if (parcelFileDescriptor != null)
          try {
            parcelFileDescriptor.close();
          } finally {
            parcelFileDescriptor = null;
          }  
      } 
    } catch (IOException iOException) {
      return null;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\TypefaceCompatApi21Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */