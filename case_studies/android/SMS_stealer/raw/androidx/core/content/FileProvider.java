package androidx.core.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider extends ContentProvider {
  private static final String ATTR_NAME = "name";
  
  private static final String ATTR_PATH = "path";
  
  private static final String[] COLUMNS = new String[] { "_display_name", "_size" };
  
  private static final File DEVICE_ROOT = new File("/");
  
  private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
  
  private static final String TAG_CACHE_PATH = "cache-path";
  
  private static final String TAG_EXTERNAL = "external-path";
  
  private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
  
  private static final String TAG_EXTERNAL_FILES = "external-files-path";
  
  private static final String TAG_EXTERNAL_MEDIA = "external-media-path";
  
  private static final String TAG_FILES_PATH = "files-path";
  
  private static final String TAG_ROOT_PATH = "root-path";
  
  private static HashMap<String, PathStrategy> sCache = new HashMap<String, PathStrategy>();
  
  private PathStrategy mStrategy;
  
  private static File buildPath(File paramFile, String... paramVarArgs) {
    int i = paramVarArgs.length;
    byte b = 0;
    File file;
    for (file = paramFile; b < i; file = paramFile) {
      String str = paramVarArgs[b];
      paramFile = file;
      if (str != null)
        paramFile = new File(file, str); 
      b++;
    } 
    return file;
  }
  
  private static Object[] copyOf(Object[] paramArrayOfObject, int paramInt) {
    Object[] arrayOfObject = new Object[paramInt];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
    return arrayOfObject;
  }
  
  private static String[] copyOf(String[] paramArrayOfString, int paramInt) {
    String[] arrayOfString = new String[paramInt];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramInt);
    return arrayOfString;
  }
  
  private static PathStrategy getPathStrategy(Context paramContext, String paramString) {
    synchronized (sCache) {
      PathStrategy pathStrategy2 = sCache.get(paramString);
      PathStrategy pathStrategy1 = pathStrategy2;
      if (pathStrategy2 == null)
        try {
          pathStrategy1 = parsePathStrategy(paramContext, paramString);
          sCache.put(paramString, pathStrategy1);
        } catch (IOException iOException) {
          IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
          this("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", iOException);
          throw illegalArgumentException;
        } catch (XmlPullParserException xmlPullParserException) {
          IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
          this("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", (Throwable)xmlPullParserException);
          throw illegalArgumentException;
        }  
      return pathStrategy1;
    } 
  }
  
  public static Uri getUriForFile(Context paramContext, String paramString, File paramFile) {
    return getPathStrategy(paramContext, paramString).getUriForFile(paramFile);
  }
  
  private static int modeToMode(String paramString) {
    int i;
    if ("r".equals(paramString)) {
      i = 268435456;
    } else {
      if ("w".equals(paramString) || "wt".equals(paramString))
        return 738197504; 
      if ("wa".equals(paramString)) {
        i = 704643072;
      } else if ("rw".equals(paramString)) {
        i = 939524096;
      } else if ("rwt".equals(paramString)) {
        i = 1006632960;
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid mode: ");
        stringBuilder.append(paramString);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    } 
    return i;
  }
  
  private static PathStrategy parsePathStrategy(Context paramContext, String paramString) throws IOException, XmlPullParserException {
    File file;
    SimplePathStrategy simplePathStrategy = new SimplePathStrategy(paramString);
    ProviderInfo providerInfo = paramContext.getPackageManager().resolveContentProvider(paramString, 128);
    if (providerInfo != null) {
      XmlResourceParser xmlResourceParser = providerInfo.loadXmlMetaData(paramContext.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
      if (xmlResourceParser != null)
        while (true) {
          int i = xmlResourceParser.next();
          if (i != 1) {
            if (i == 2) {
              String str3 = xmlResourceParser.getName();
              providerInfo = null;
              String str2 = xmlResourceParser.getAttributeValue(null, "name");
              String str1 = xmlResourceParser.getAttributeValue(null, "path");
              if ("root-path".equals(str3)) {
                file = DEVICE_ROOT;
              } else if ("files-path".equals(str3)) {
                file = paramContext.getFilesDir();
              } else if ("cache-path".equals(str3)) {
                file = paramContext.getCacheDir();
              } else if ("external-path".equals(str3)) {
                file = Environment.getExternalStorageDirectory();
              } else {
                File[] arrayOfFile;
                if ("external-files-path".equals(str3)) {
                  arrayOfFile = ContextCompat.getExternalFilesDirs(paramContext, null);
                  ProviderInfo providerInfo1 = providerInfo;
                  if (arrayOfFile.length > 0)
                    file = arrayOfFile[0]; 
                } else if ("external-cache-path".equals(arrayOfFile)) {
                  arrayOfFile = ContextCompat.getExternalCacheDirs(paramContext);
                  ProviderInfo providerInfo1 = providerInfo;
                  if (arrayOfFile.length > 0)
                    file = arrayOfFile[0]; 
                } else {
                  ProviderInfo providerInfo1 = providerInfo;
                  if (Build.VERSION.SDK_INT >= 21) {
                    providerInfo1 = providerInfo;
                    if ("external-media-path".equals(arrayOfFile)) {
                      arrayOfFile = paramContext.getExternalMediaDirs();
                      providerInfo1 = providerInfo;
                      if (arrayOfFile.length > 0)
                        file = arrayOfFile[0]; 
                    } 
                  } 
                } 
              } 
              if (file != null)
                simplePathStrategy.addRoot(str2, buildPath(file, new String[] { str1 })); 
            } 
            continue;
          } 
          return simplePathStrategy;
        }  
      throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Couldn't find meta-data for provider with authority ");
    stringBuilder.append((String)file);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void attachInfo(Context paramContext, ProviderInfo paramProviderInfo) {
    super.attachInfo(paramContext, paramProviderInfo);
    if (!paramProviderInfo.exported) {
      if (paramProviderInfo.grantUriPermissions) {
        this.mStrategy = getPathStrategy(paramContext, paramProviderInfo.authority);
        return;
      } 
      throw new SecurityException("Provider must grant uri permissions");
    } 
    throw new SecurityException("Provider must not be exported");
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    return this.mStrategy.getFileForUri(paramUri).delete();
  }
  
  public String getType(Uri paramUri) {
    File file = this.mStrategy.getFileForUri(paramUri);
    int i = file.getName().lastIndexOf('.');
    if (i >= 0) {
      String str = file.getName().substring(i + 1);
      str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str);
      if (str != null)
        return str; 
    } 
    return "application/octet-stream";
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues) {
    throw new UnsupportedOperationException("No external inserts");
  }
  
  public boolean onCreate() {
    return true;
  }
  
  public ParcelFileDescriptor openFile(Uri paramUri, String paramString) throws FileNotFoundException {
    return ParcelFileDescriptor.open(this.mStrategy.getFileForUri(paramUri), modeToMode(paramString));
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    Object object;
    int i;
    File file = this.mStrategy.getFileForUri(paramUri);
    String[] arrayOfString1 = paramArrayOfString1;
    if (paramArrayOfString1 == null)
      arrayOfString1 = COLUMNS; 
    paramArrayOfString2 = new String[arrayOfString1.length];
    Object[] arrayOfObject2 = new Object[arrayOfString1.length];
    int j = arrayOfString1.length;
    byte b = 0;
    boolean bool = false;
    while (b < j) {
      paramString2 = arrayOfString1[b];
      if ("_display_name".equals(paramString2)) {
        paramArrayOfString2[object] = "_display_name";
        int m = object + 1;
        arrayOfObject2[object] = file.getName();
        i = m;
      } else {
        int m = i;
        if ("_size".equals(paramString2)) {
          paramArrayOfString2[i] = "_size";
          m = i + 1;
          arrayOfObject2[i] = Long.valueOf(file.length());
          i = m;
        } else {
          continue;
        } 
      } 
      int k = i;
      continue;
      b++;
      object = SYNTHETIC_LOCAL_VARIABLE_8;
    } 
    String[] arrayOfString2 = copyOf(paramArrayOfString2, i);
    Object[] arrayOfObject1 = copyOf(arrayOfObject2, i);
    MatrixCursor matrixCursor = new MatrixCursor(arrayOfString2, 1);
    matrixCursor.addRow(arrayOfObject1);
    return (Cursor)matrixCursor;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    throw new UnsupportedOperationException("No external updates");
  }
  
  static interface PathStrategy {
    File getFileForUri(Uri param1Uri);
    
    Uri getUriForFile(File param1File);
  }
  
  static class SimplePathStrategy implements PathStrategy {
    private final String mAuthority;
    
    private final HashMap<String, File> mRoots = new HashMap<String, File>();
    
    SimplePathStrategy(String param1String) {
      this.mAuthority = param1String;
    }
    
    void addRoot(String param1String, File param1File) {
      if (!TextUtils.isEmpty(param1String))
        try {
          File file = param1File.getCanonicalFile();
          this.mRoots.put(param1String, file);
          return;
        } catch (IOException iOException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Failed to resolve canonical path for ");
          stringBuilder.append(param1File);
          throw new IllegalArgumentException(stringBuilder.toString(), iOException);
        }  
      throw new IllegalArgumentException("Name must not be empty");
    }
    
    public File getFileForUri(Uri param1Uri) {
      File file1;
      String str2 = param1Uri.getEncodedPath();
      int i = str2.indexOf('/', 1);
      String str1 = Uri.decode(str2.substring(1, i));
      str2 = Uri.decode(str2.substring(i + 1));
      File file2 = this.mRoots.get(str1);
      if (file2 != null) {
        file1 = new File(file2, str2);
        try {
          File file = file1.getCanonicalFile();
          if (file.getPath().startsWith(file2.getPath()))
            return file; 
          throw new SecurityException("Resolved path jumped beyond configured root");
        } catch (IOException iOException) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Failed to resolve canonical path for ");
          stringBuilder1.append(file1);
          throw new IllegalArgumentException(stringBuilder1.toString());
        } 
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to find configured root for ");
      stringBuilder.append(file1);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Uri getUriForFile(File param1File) {
      StringBuilder stringBuilder;
      try {
        Map.Entry<String, File> entry;
        StringBuilder stringBuilder1;
        String str = param1File.getCanonicalPath();
        param1File = null;
        for (Map.Entry<String, File> entry1 : this.mRoots.entrySet()) {
          String str1 = ((File)entry1.getValue()).getPath();
          if (str.startsWith(str1) && (param1File == null || str1.length() > ((File)param1File.getValue()).getPath().length()))
            entry = entry1; 
        } 
        if (entry != null) {
          String str2 = ((File)entry.getValue()).getPath();
          if (str2.endsWith("/")) {
            str2 = str.substring(str2.length());
          } else {
            str2 = str.substring(str2.length() + 1);
          } 
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append(Uri.encode(entry.getKey()));
          stringBuilder1.append('/');
          stringBuilder1.append(Uri.encode(str2, "/"));
          String str1 = stringBuilder1.toString();
          return (new Uri.Builder()).scheme("content").authority(this.mAuthority).encodedPath(str1).build();
        } 
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to find configured root that contains ");
        stringBuilder.append((String)stringBuilder1);
        throw new IllegalArgumentException(stringBuilder.toString());
      } catch (IOException iOException) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Failed to resolve canonical path for ");
        stringBuilder1.append(stringBuilder);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\FileProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */