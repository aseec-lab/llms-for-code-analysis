package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat {
  private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
  
  public static final String PARCEL_FONT_RESULTS = "font_results";
  
  static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
  
  static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
  
  private static final SelfDestructiveThread sBackgroundThread;
  
  private static final Comparator<byte[]> sByteArrayComparator;
  
  static final Object sLock;
  
  static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>> sPendingReplies;
  
  static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
  
  static {
    sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    sLock = new Object();
    sPendingReplies = new SimpleArrayMap();
    sByteArrayComparator = new Comparator<byte[]>() {
        public int compare(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
          if (param1ArrayOfbyte1.length != param1ArrayOfbyte2.length) {
            int i = param1ArrayOfbyte1.length;
            int j = param1ArrayOfbyte2.length;
            return i - j;
          } 
          for (byte b = 0; b < param1ArrayOfbyte1.length; b++) {
            if (param1ArrayOfbyte1[b] != param1ArrayOfbyte2[b]) {
              byte b2 = param1ArrayOfbyte1[b];
              byte b1 = param1ArrayOfbyte2[b];
              b = b2;
              return b - b1;
            } 
          } 
          return 0;
        }
      };
  }
  
  public static Typeface buildTypeface(Context paramContext, CancellationSignal paramCancellationSignal, FontInfo[] paramArrayOfFontInfo) {
    return TypefaceCompat.createFromFontInfo(paramContext, paramCancellationSignal, paramArrayOfFontInfo, 0);
  }
  
  private static List<byte[]> convertToByteArrayList(Signature[] paramArrayOfSignature) {
    ArrayList<byte[]> arrayList = new ArrayList();
    for (byte b = 0; b < paramArrayOfSignature.length; b++)
      arrayList.add(paramArrayOfSignature[b].toByteArray()); 
    return (List<byte[]>)arrayList;
  }
  
  private static boolean equalsByteArrayList(List<byte[]> paramList1, List<byte[]> paramList2) {
    if (paramList1.size() != paramList2.size())
      return false; 
    for (byte b = 0; b < paramList1.size(); b++) {
      if (!Arrays.equals(paramList1.get(b), paramList2.get(b)))
        return false; 
    } 
    return true;
  }
  
  public static FontFamilyResult fetchFonts(Context paramContext, CancellationSignal paramCancellationSignal, FontRequest paramFontRequest) throws PackageManager.NameNotFoundException {
    ProviderInfo providerInfo = getProvider(paramContext.getPackageManager(), paramFontRequest, paramContext.getResources());
    return (providerInfo == null) ? new FontFamilyResult(1, null) : new FontFamilyResult(0, getFontFromProvider(paramContext, paramFontRequest, providerInfo.authority, paramCancellationSignal));
  }
  
  private static List<List<byte[]>> getCertificates(FontRequest paramFontRequest, Resources paramResources) {
    return (paramFontRequest.getCertificates() != null) ? paramFontRequest.getCertificates() : FontResourcesParserCompat.readCerts(paramResources, paramFontRequest.getCertificatesArrayResId());
  }
  
  static FontInfo[] getFontFromProvider(Context paramContext, FontRequest paramFontRequest, String paramString, CancellationSignal paramCancellationSignal) {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #14
    //   9: new android/net/Uri$Builder
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: ldc 'content'
    //   18: invokevirtual scheme : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   21: aload_2
    //   22: invokevirtual authority : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   25: invokevirtual build : ()Landroid/net/Uri;
    //   28: astore #16
    //   30: new android/net/Uri$Builder
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: ldc 'content'
    //   39: invokevirtual scheme : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   42: aload_2
    //   43: invokevirtual authority : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   46: ldc 'file'
    //   48: invokevirtual appendPath : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   51: invokevirtual build : ()Landroid/net/Uri;
    //   54: astore #17
    //   56: aconst_null
    //   57: astore #15
    //   59: aload #15
    //   61: astore_2
    //   62: getstatic android/os/Build$VERSION.SDK_INT : I
    //   65: bipush #16
    //   67: if_icmple -> 152
    //   70: aload #15
    //   72: astore_2
    //   73: aload_0
    //   74: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   77: astore_0
    //   78: aload #15
    //   80: astore_2
    //   81: aload_1
    //   82: invokevirtual getQuery : ()Ljava/lang/String;
    //   85: astore_1
    //   86: aload #15
    //   88: astore_2
    //   89: aload_0
    //   90: aload #16
    //   92: bipush #7
    //   94: anewarray java/lang/String
    //   97: dup
    //   98: iconst_0
    //   99: ldc '_id'
    //   101: aastore
    //   102: dup
    //   103: iconst_1
    //   104: ldc 'file_id'
    //   106: aastore
    //   107: dup
    //   108: iconst_2
    //   109: ldc 'font_ttc_index'
    //   111: aastore
    //   112: dup
    //   113: iconst_3
    //   114: ldc 'font_variation_settings'
    //   116: aastore
    //   117: dup
    //   118: iconst_4
    //   119: ldc 'font_weight'
    //   121: aastore
    //   122: dup
    //   123: iconst_5
    //   124: ldc 'font_italic'
    //   126: aastore
    //   127: dup
    //   128: bipush #6
    //   130: ldc 'result_code'
    //   132: aastore
    //   133: ldc 'query = ?'
    //   135: iconst_1
    //   136: anewarray java/lang/String
    //   139: dup
    //   140: iconst_0
    //   141: aload_1
    //   142: aastore
    //   143: aconst_null
    //   144: aload_3
    //   145: invokevirtual query : (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   148: astore_0
    //   149: goto -> 230
    //   152: aload #15
    //   154: astore_2
    //   155: aload_0
    //   156: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   159: astore_0
    //   160: aload #15
    //   162: astore_2
    //   163: aload_1
    //   164: invokevirtual getQuery : ()Ljava/lang/String;
    //   167: astore_1
    //   168: aload #15
    //   170: astore_2
    //   171: aload_0
    //   172: aload #16
    //   174: bipush #7
    //   176: anewarray java/lang/String
    //   179: dup
    //   180: iconst_0
    //   181: ldc '_id'
    //   183: aastore
    //   184: dup
    //   185: iconst_1
    //   186: ldc 'file_id'
    //   188: aastore
    //   189: dup
    //   190: iconst_2
    //   191: ldc 'font_ttc_index'
    //   193: aastore
    //   194: dup
    //   195: iconst_3
    //   196: ldc 'font_variation_settings'
    //   198: aastore
    //   199: dup
    //   200: iconst_4
    //   201: ldc 'font_weight'
    //   203: aastore
    //   204: dup
    //   205: iconst_5
    //   206: ldc 'font_italic'
    //   208: aastore
    //   209: dup
    //   210: bipush #6
    //   212: ldc 'result_code'
    //   214: aastore
    //   215: ldc 'query = ?'
    //   217: iconst_1
    //   218: anewarray java/lang/String
    //   221: dup
    //   222: iconst_0
    //   223: aload_1
    //   224: aastore
    //   225: aconst_null
    //   226: invokevirtual query : (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   229: astore_0
    //   230: aload #14
    //   232: astore_1
    //   233: aload_0
    //   234: ifnull -> 527
    //   237: aload #14
    //   239: astore_1
    //   240: aload_0
    //   241: astore_2
    //   242: aload_0
    //   243: invokeinterface getCount : ()I
    //   248: ifle -> 527
    //   251: aload_0
    //   252: astore_2
    //   253: aload_0
    //   254: ldc 'result_code'
    //   256: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   261: istore #11
    //   263: aload_0
    //   264: astore_2
    //   265: new java/util/ArrayList
    //   268: astore_3
    //   269: aload_0
    //   270: astore_2
    //   271: aload_3
    //   272: invokespecial <init> : ()V
    //   275: aload_0
    //   276: astore_2
    //   277: aload_0
    //   278: ldc '_id'
    //   280: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   285: istore #8
    //   287: aload_0
    //   288: astore_2
    //   289: aload_0
    //   290: ldc 'file_id'
    //   292: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   297: istore #9
    //   299: aload_0
    //   300: astore_2
    //   301: aload_0
    //   302: ldc 'font_ttc_index'
    //   304: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   309: istore #12
    //   311: aload_0
    //   312: astore_2
    //   313: aload_0
    //   314: ldc 'font_weight'
    //   316: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   321: istore #7
    //   323: aload_0
    //   324: astore_2
    //   325: aload_0
    //   326: ldc 'font_italic'
    //   328: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   333: istore #10
    //   335: aload_0
    //   336: astore_2
    //   337: aload_0
    //   338: invokeinterface moveToNext : ()Z
    //   343: ifeq -> 525
    //   346: iload #11
    //   348: iconst_m1
    //   349: if_icmpeq -> 367
    //   352: aload_0
    //   353: astore_2
    //   354: aload_0
    //   355: iload #11
    //   357: invokeinterface getInt : (I)I
    //   362: istore #4
    //   364: goto -> 370
    //   367: iconst_0
    //   368: istore #4
    //   370: iload #12
    //   372: iconst_m1
    //   373: if_icmpeq -> 391
    //   376: aload_0
    //   377: astore_2
    //   378: aload_0
    //   379: iload #12
    //   381: invokeinterface getInt : (I)I
    //   386: istore #5
    //   388: goto -> 394
    //   391: iconst_0
    //   392: istore #5
    //   394: iload #9
    //   396: iconst_m1
    //   397: if_icmpne -> 419
    //   400: aload_0
    //   401: astore_2
    //   402: aload #16
    //   404: aload_0
    //   405: iload #8
    //   407: invokeinterface getLong : (I)J
    //   412: invokestatic withAppendedId : (Landroid/net/Uri;J)Landroid/net/Uri;
    //   415: astore_1
    //   416: goto -> 435
    //   419: aload_0
    //   420: astore_2
    //   421: aload #17
    //   423: aload_0
    //   424: iload #9
    //   426: invokeinterface getLong : (I)J
    //   431: invokestatic withAppendedId : (Landroid/net/Uri;J)Landroid/net/Uri;
    //   434: astore_1
    //   435: iload #7
    //   437: iconst_m1
    //   438: if_icmpeq -> 456
    //   441: aload_0
    //   442: astore_2
    //   443: aload_0
    //   444: iload #7
    //   446: invokeinterface getInt : (I)I
    //   451: istore #6
    //   453: goto -> 461
    //   456: sipush #400
    //   459: istore #6
    //   461: iload #10
    //   463: iconst_m1
    //   464: if_icmpeq -> 487
    //   467: aload_0
    //   468: astore_2
    //   469: aload_0
    //   470: iload #10
    //   472: invokeinterface getInt : (I)I
    //   477: iconst_1
    //   478: if_icmpne -> 487
    //   481: iconst_1
    //   482: istore #13
    //   484: goto -> 490
    //   487: iconst_0
    //   488: istore #13
    //   490: aload_0
    //   491: astore_2
    //   492: new androidx/core/provider/FontsContractCompat$FontInfo
    //   495: astore #14
    //   497: aload_0
    //   498: astore_2
    //   499: aload #14
    //   501: aload_1
    //   502: iload #5
    //   504: iload #6
    //   506: iload #13
    //   508: iload #4
    //   510: invokespecial <init> : (Landroid/net/Uri;IIZI)V
    //   513: aload_0
    //   514: astore_2
    //   515: aload_3
    //   516: aload #14
    //   518: invokevirtual add : (Ljava/lang/Object;)Z
    //   521: pop
    //   522: goto -> 335
    //   525: aload_3
    //   526: astore_1
    //   527: aload_0
    //   528: ifnull -> 537
    //   531: aload_0
    //   532: invokeinterface close : ()V
    //   537: aload_1
    //   538: iconst_0
    //   539: anewarray androidx/core/provider/FontsContractCompat$FontInfo
    //   542: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   545: checkcast [Landroidx/core/provider/FontsContractCompat$FontInfo;
    //   548: areturn
    //   549: astore_0
    //   550: aload_2
    //   551: ifnull -> 560
    //   554: aload_2
    //   555: invokeinterface close : ()V
    //   560: aload_0
    //   561: athrow
    // Exception table:
    //   from	to	target	type
    //   62	70	549	finally
    //   73	78	549	finally
    //   81	86	549	finally
    //   89	149	549	finally
    //   155	160	549	finally
    //   163	168	549	finally
    //   171	230	549	finally
    //   242	251	549	finally
    //   253	263	549	finally
    //   265	269	549	finally
    //   271	275	549	finally
    //   277	287	549	finally
    //   289	299	549	finally
    //   301	311	549	finally
    //   313	323	549	finally
    //   325	335	549	finally
    //   337	346	549	finally
    //   354	364	549	finally
    //   378	388	549	finally
    //   402	416	549	finally
    //   421	435	549	finally
    //   443	453	549	finally
    //   469	481	549	finally
    //   492	497	549	finally
    //   499	513	549	finally
    //   515	522	549	finally
  }
  
  static TypefaceResult getFontInternal(Context paramContext, FontRequest paramFontRequest, int paramInt) {
    try {
      FontFamilyResult fontFamilyResult = fetchFonts(paramContext, null, paramFontRequest);
      int i = fontFamilyResult.getStatusCode();
      byte b = -3;
      if (i == 0) {
        Typeface typeface = TypefaceCompat.createFromFontInfo(paramContext, null, fontFamilyResult.getFonts(), paramInt);
        if (typeface != null)
          b = 0; 
        return new TypefaceResult(typeface, b);
      } 
      if (fontFamilyResult.getStatusCode() == 1)
        b = -2; 
      return new TypefaceResult(null, b);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      return new TypefaceResult(null, -1);
    } 
  }
  
  public static Typeface getFontSync(Context paramContext, final FontRequest request, final ResourcesCompat.FontCallback fontCallback, final Handler handler, boolean paramBoolean, int paramInt1, final int style) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(request.getIdentifier());
    stringBuilder.append("-");
    stringBuilder.append(style);
    final String id = stringBuilder.toString();
    Typeface typeface = (Typeface)sTypefaceCache.get(str);
    if (typeface != null) {
      if (fontCallback != null)
        fontCallback.onFontRetrieved(typeface); 
      return typeface;
    } 
    if (paramBoolean && paramInt1 == -1) {
      typefaceResult = getFontInternal(paramContext, request, style);
      if (fontCallback != null)
        if (typefaceResult.mResult == 0) {
          fontCallback.callbackSuccessAsync(typefaceResult.mTypeface, handler);
        } else {
          fontCallback.callbackFailAsync(typefaceResult.mResult, handler);
        }  
      return typefaceResult.mTypeface;
    } 
    Callable<TypefaceResult> callable = new Callable<TypefaceResult>() {
        final Context val$context;
        
        final String val$id;
        
        final FontRequest val$request;
        
        final int val$style;
        
        public FontsContractCompat.TypefaceResult call() throws Exception {
          FontsContractCompat.TypefaceResult typefaceResult = FontsContractCompat.getFontInternal(context, request, style);
          if (typefaceResult.mTypeface != null)
            FontsContractCompat.sTypefaceCache.put(id, typefaceResult.mTypeface); 
          return typefaceResult;
        }
      };
    final TypefaceResult context = null;
    if (paramBoolean) {
      Typeface typeface1;
      try {
        Typeface typeface2 = ((TypefaceResult)sBackgroundThread.postAndWait((Callable)callable, paramInt1)).mTypeface;
        typeface1 = typeface2;
      } catch (InterruptedException interruptedException) {}
      return typeface1;
    } 
    if (fontCallback == null) {
      typefaceResult = null;
    } else {
      null = new SelfDestructiveThread.ReplyCallback<TypefaceResult>() {
          final ResourcesCompat.FontCallback val$fontCallback;
          
          final Handler val$handler;
          
          public void onReply(FontsContractCompat.TypefaceResult param1TypefaceResult) {
            if (param1TypefaceResult == null) {
              fontCallback.callbackFailAsync(1, handler);
            } else if (param1TypefaceResult.mResult == 0) {
              fontCallback.callbackSuccessAsync(param1TypefaceResult.mTypeface, handler);
            } else {
              fontCallback.callbackFailAsync(param1TypefaceResult.mResult, handler);
            } 
          }
        };
    } 
    synchronized (sLock) {
      ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>> arrayList = (ArrayList)sPendingReplies.get(str);
      if (arrayList != null) {
        if (null != null)
          arrayList.add(null); 
        return null;
      } 
      if (null != null) {
        arrayList = new ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>();
        this();
        arrayList.add(null);
        sPendingReplies.put(str, arrayList);
      } 
      sBackgroundThread.postAndReply((Callable<?>)interruptedException, new SelfDestructiveThread.ReplyCallback<TypefaceResult>() {
            final String val$id;
            
            public void onReply(FontsContractCompat.TypefaceResult param1TypefaceResult) {
              synchronized (FontsContractCompat.sLock) {
                ArrayList<SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult>> arrayList = (ArrayList)FontsContractCompat.sPendingReplies.get(id);
                if (arrayList == null)
                  return; 
                FontsContractCompat.sPendingReplies.remove(id);
                for (byte b = 0; b < arrayList.size(); b++)
                  ((SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult>)arrayList.get(b)).onReply(param1TypefaceResult); 
                return;
              } 
            }
          });
      return null;
    } 
  }
  
  public static ProviderInfo getProvider(PackageManager paramPackageManager, FontRequest paramFontRequest, Resources paramResources) throws PackageManager.NameNotFoundException {
    String str = paramFontRequest.getProviderAuthority();
    byte b = 0;
    ProviderInfo providerInfo = paramPackageManager.resolveContentProvider(str, 0);
    if (providerInfo != null) {
      ArrayList<byte> arrayList;
      if (providerInfo.packageName.equals(paramFontRequest.getProviderPackage())) {
        List<byte[]> list = convertToByteArrayList((paramPackageManager.getPackageInfo(providerInfo.packageName, 64)).signatures);
        Collections.sort((List)list, (Comparator)sByteArrayComparator);
        List<List<byte[]>> list1 = getCertificates(paramFontRequest, paramResources);
        while (b < list1.size()) {
          arrayList = new ArrayList(list1.get(b));
          Collections.sort(arrayList, (Comparator)sByteArrayComparator);
          if (equalsByteArrayList(list, (List)arrayList))
            return providerInfo; 
          b++;
        } 
        return null;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Found content provider ");
      stringBuilder1.append(str);
      stringBuilder1.append(", but package was not ");
      stringBuilder1.append(arrayList.getProviderPackage());
      throw new PackageManager.NameNotFoundException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No package found for authority: ");
    stringBuilder.append(str);
    throw new PackageManager.NameNotFoundException(stringBuilder.toString());
  }
  
  public static Map<Uri, ByteBuffer> prepareFontData(Context paramContext, FontInfo[] paramArrayOfFontInfo, CancellationSignal paramCancellationSignal) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    int i = paramArrayOfFontInfo.length;
    for (byte b = 0; b < i; b++) {
      FontInfo fontInfo = paramArrayOfFontInfo[b];
      if (fontInfo.getResultCode() == 0) {
        Uri uri = fontInfo.getUri();
        if (!hashMap.containsKey(uri))
          hashMap.put(uri, TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, uri)); 
      } 
    } 
    return (Map)Collections.unmodifiableMap(hashMap);
  }
  
  public static void requestFont(Context paramContext, FontRequest paramFontRequest, FontRequestCallback paramFontRequestCallback, Handler paramHandler) {
    requestFontInternal(paramContext.getApplicationContext(), paramFontRequest, paramFontRequestCallback, paramHandler);
  }
  
  private static void requestFontInternal(final Context appContext, final FontRequest request, final FontRequestCallback callback, Handler paramHandler) {
    paramHandler.post(new Runnable() {
          final Context val$appContext;
          
          final FontsContractCompat.FontRequestCallback val$callback;
          
          final Handler val$callerThreadHandler;
          
          final FontRequest val$request;
          
          public void run() {
            try {
              FontsContractCompat.FontFamilyResult fontFamilyResult = FontsContractCompat.fetchFonts(appContext, null, request);
              if (fontFamilyResult.getStatusCode() != 0) {
                int k = fontFamilyResult.getStatusCode();
                if (k != 1) {
                  if (k != 2) {
                    callerThreadHandler.post(new Runnable() {
                          final FontsContractCompat.null this$0;
                          
                          public void run() {
                            callback.onTypefaceRequestFailed(-3);
                          }
                        });
                    return;
                  } 
                  callerThreadHandler.post(new Runnable() {
                        final FontsContractCompat.null this$0;
                        
                        public void run() {
                          callback.onTypefaceRequestFailed(-3);
                        }
                      });
                  return;
                } 
                callerThreadHandler.post(new Runnable() {
                      final FontsContractCompat.null this$0;
                      
                      public void run() {
                        callback.onTypefaceRequestFailed(-2);
                      }
                    });
                return;
              } 
              FontsContractCompat.FontInfo[] arrayOfFontInfo = fontFamilyResult.getFonts();
              if (arrayOfFontInfo == null || arrayOfFontInfo.length == 0) {
                callerThreadHandler.post(new Runnable() {
                      final FontsContractCompat.null this$0;
                      
                      public void run() {
                        callback.onTypefaceRequestFailed(1);
                      }
                    });
                return;
              } 
              int j = arrayOfFontInfo.length;
              for (final int resultCode = 0; i < j; i++) {
                FontsContractCompat.FontInfo fontInfo = arrayOfFontInfo[i];
                if (fontInfo.getResultCode() != 0) {
                  i = fontInfo.getResultCode();
                  if (i < 0) {
                    callerThreadHandler.post(new Runnable() {
                          final FontsContractCompat.null this$0;
                          
                          public void run() {
                            callback.onTypefaceRequestFailed(-3);
                          }
                        });
                  } else {
                    callerThreadHandler.post(new Runnable() {
                          final FontsContractCompat.null this$0;
                          
                          final int val$resultCode;
                          
                          public void run() {
                            callback.onTypefaceRequestFailed(resultCode);
                          }
                        });
                  } 
                  return;
                } 
              } 
              final Typeface typeface = FontsContractCompat.buildTypeface(appContext, null, arrayOfFontInfo);
              if (typeface == null) {
                callerThreadHandler.post(new Runnable() {
                      final FontsContractCompat.null this$0;
                      
                      public void run() {
                        callback.onTypefaceRequestFailed(-3);
                      }
                    });
                return;
              } 
              callerThreadHandler.post(new Runnable() {
                    final FontsContractCompat.null this$0;
                    
                    final Typeface val$typeface;
                    
                    public void run() {
                      callback.onTypefaceRetrieved(typeface);
                    }
                  });
              return;
            } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
              callerThreadHandler.post(new Runnable() {
                    final FontsContractCompat.null this$0;
                    
                    public void run() {
                      callback.onTypefaceRequestFailed(-1);
                    }
                  });
              return;
            } 
          }
        });
  }
  
  public static void resetCache() {
    sTypefaceCache.evictAll();
  }
  
  public static final class Columns implements BaseColumns {
    public static final String FILE_ID = "file_id";
    
    public static final String ITALIC = "font_italic";
    
    public static final String RESULT_CODE = "result_code";
    
    public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
    
    public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
    
    public static final int RESULT_CODE_MALFORMED_QUERY = 3;
    
    public static final int RESULT_CODE_OK = 0;
    
    public static final String TTC_INDEX = "font_ttc_index";
    
    public static final String VARIATION_SETTINGS = "font_variation_settings";
    
    public static final String WEIGHT = "font_weight";
  }
  
  public static class FontFamilyResult {
    public static final int STATUS_OK = 0;
    
    public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
    
    public static final int STATUS_WRONG_CERTIFICATES = 1;
    
    private final FontsContractCompat.FontInfo[] mFonts;
    
    private final int mStatusCode;
    
    public FontFamilyResult(int param1Int, FontsContractCompat.FontInfo[] param1ArrayOfFontInfo) {
      this.mStatusCode = param1Int;
      this.mFonts = param1ArrayOfFontInfo;
    }
    
    public FontsContractCompat.FontInfo[] getFonts() {
      return this.mFonts;
    }
    
    public int getStatusCode() {
      return this.mStatusCode;
    }
  }
  
  public static class FontInfo {
    private final boolean mItalic;
    
    private final int mResultCode;
    
    private final int mTtcIndex;
    
    private final Uri mUri;
    
    private final int mWeight;
    
    public FontInfo(Uri param1Uri, int param1Int1, int param1Int2, boolean param1Boolean, int param1Int3) {
      this.mUri = (Uri)Preconditions.checkNotNull(param1Uri);
      this.mTtcIndex = param1Int1;
      this.mWeight = param1Int2;
      this.mItalic = param1Boolean;
      this.mResultCode = param1Int3;
    }
    
    public int getResultCode() {
      return this.mResultCode;
    }
    
    public int getTtcIndex() {
      return this.mTtcIndex;
    }
    
    public Uri getUri() {
      return this.mUri;
    }
    
    public int getWeight() {
      return this.mWeight;
    }
    
    public boolean isItalic() {
      return this.mItalic;
    }
  }
  
  public static class FontRequestCallback {
    public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
    
    public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
    
    public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
    
    public static final int FAIL_REASON_MALFORMED_QUERY = 3;
    
    public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
    
    public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
    
    public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
    
    public static final int RESULT_OK = 0;
    
    public void onTypefaceRequestFailed(int param1Int) {}
    
    public void onTypefaceRetrieved(Typeface param1Typeface) {}
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface FontRequestFailReason {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FontRequestFailReason {}
  
  private static final class TypefaceResult {
    final int mResult;
    
    final Typeface mTypeface;
    
    TypefaceResult(Typeface param1Typeface, int param1Int) {
      this.mTypeface = param1Typeface;
      this.mResult = param1Int;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\provider\FontsContractCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */