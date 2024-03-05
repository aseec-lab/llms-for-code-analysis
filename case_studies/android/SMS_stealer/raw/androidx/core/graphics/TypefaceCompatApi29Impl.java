package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;

public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt) {
    FontResourcesParserCompat.FontFileResourceEntry[] arrayOfFontFileResourceEntry = paramFontFamilyFilesResourceEntry.getEntries();
    int i = arrayOfFontFileResourceEntry.length;
    boolean bool = false;
    paramContext = null;
    char c = Character.MIN_VALUE;
    while (true) {
      FontFamily.Builder builder;
      boolean bool1 = true;
      if (c < i) {
        FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = arrayOfFontFileResourceEntry[c];
        try {
          Font.Builder builder1 = new Font.Builder();
          this(paramResources, fontFileResourceEntry.getResourceId());
          builder1 = builder1.setWeight(fontFileResourceEntry.getWeight());
          if (!fontFileResourceEntry.isItalic())
            bool1 = false; 
          Font font = builder1.setSlant(bool1).setTtcIndex(fontFileResourceEntry.getTtcIndex()).setFontVariationSettings(fontFileResourceEntry.getVariationSettings()).build();
          if (paramContext == null) {
            FontFamily.Builder builder2 = new FontFamily.Builder();
            this(font);
            builder = builder2;
          } else {
            builder.addFont(font);
          } 
        } catch (IOException iOException) {}
        c++;
        continue;
      } 
      if (builder == null)
        return null; 
      if ((paramInt & 0x1) != 0) {
        c = 'ʼ';
      } else {
        c = 'Ɛ';
      } 
      bool1 = bool;
      if ((paramInt & 0x2) != 0)
        bool1 = true; 
      FontStyle fontStyle = new FontStyle(c, bool1);
      return (new Typeface.CustomFallbackBuilder(builder.build())).setStyle(fontStyle).build();
    } 
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   4: astore #10
    //   6: aload_3
    //   7: arraylength
    //   8: istore #8
    //   10: iconst_0
    //   11: istore #7
    //   13: aconst_null
    //   14: astore_1
    //   15: iconst_0
    //   16: istore #5
    //   18: iconst_1
    //   19: istore #6
    //   21: iload #5
    //   23: iload #8
    //   25: if_icmpge -> 218
    //   28: aload_3
    //   29: iload #5
    //   31: aaload
    //   32: astore #12
    //   34: aload_1
    //   35: astore #9
    //   37: aload #10
    //   39: aload #12
    //   41: invokevirtual getUri : ()Landroid/net/Uri;
    //   44: ldc 'r'
    //   46: aload_2
    //   47: invokevirtual openFileDescriptor : (Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   50: astore #11
    //   52: aload #11
    //   54: ifnonnull -> 79
    //   57: aload_1
    //   58: astore #9
    //   60: aload #11
    //   62: ifnull -> 209
    //   65: aload_1
    //   66: astore #9
    //   68: aload #11
    //   70: invokevirtual close : ()V
    //   73: aload_1
    //   74: astore #9
    //   76: goto -> 209
    //   79: new android/graphics/fonts/Font$Builder
    //   82: astore #9
    //   84: aload #9
    //   86: aload #11
    //   88: invokespecial <init> : (Landroid/os/ParcelFileDescriptor;)V
    //   91: aload #9
    //   93: aload #12
    //   95: invokevirtual getWeight : ()I
    //   98: invokevirtual setWeight : (I)Landroid/graphics/fonts/Font$Builder;
    //   101: astore #9
    //   103: aload #12
    //   105: invokevirtual isItalic : ()Z
    //   108: ifeq -> 114
    //   111: goto -> 117
    //   114: iconst_0
    //   115: istore #6
    //   117: aload #9
    //   119: iload #6
    //   121: invokevirtual setSlant : (I)Landroid/graphics/fonts/Font$Builder;
    //   124: aload #12
    //   126: invokevirtual getTtcIndex : ()I
    //   129: invokevirtual setTtcIndex : (I)Landroid/graphics/fonts/Font$Builder;
    //   132: invokevirtual build : ()Landroid/graphics/fonts/Font;
    //   135: astore #9
    //   137: aload_1
    //   138: ifnonnull -> 158
    //   141: new android/graphics/fonts/FontFamily$Builder
    //   144: dup
    //   145: aload #9
    //   147: invokespecial <init> : (Landroid/graphics/fonts/Font;)V
    //   150: astore #9
    //   152: aload #9
    //   154: astore_1
    //   155: goto -> 165
    //   158: aload_1
    //   159: aload #9
    //   161: invokevirtual addFont : (Landroid/graphics/fonts/Font;)Landroid/graphics/fonts/FontFamily$Builder;
    //   164: pop
    //   165: aload_1
    //   166: astore #9
    //   168: aload #11
    //   170: ifnull -> 209
    //   173: goto -> 65
    //   176: astore #12
    //   178: aload #11
    //   180: ifnull -> 203
    //   183: aload #11
    //   185: invokevirtual close : ()V
    //   188: goto -> 203
    //   191: astore #11
    //   193: aload_1
    //   194: astore #9
    //   196: aload #12
    //   198: aload #11
    //   200: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   203: aload_1
    //   204: astore #9
    //   206: aload #12
    //   208: athrow
    //   209: iinc #5, 1
    //   212: aload #9
    //   214: astore_1
    //   215: goto -> 18
    //   218: aload_1
    //   219: ifnonnull -> 224
    //   222: aconst_null
    //   223: areturn
    //   224: iload #4
    //   226: iconst_1
    //   227: iand
    //   228: ifeq -> 239
    //   231: sipush #700
    //   234: istore #5
    //   236: goto -> 244
    //   239: sipush #400
    //   242: istore #5
    //   244: iload #7
    //   246: istore #6
    //   248: iload #4
    //   250: iconst_2
    //   251: iand
    //   252: ifeq -> 258
    //   255: iconst_1
    //   256: istore #6
    //   258: new android/graphics/fonts/FontStyle
    //   261: dup
    //   262: iload #5
    //   264: iload #6
    //   266: invokespecial <init> : (II)V
    //   269: astore_2
    //   270: new android/graphics/Typeface$CustomFallbackBuilder
    //   273: dup
    //   274: aload_1
    //   275: invokevirtual build : ()Landroid/graphics/fonts/FontFamily;
    //   278: invokespecial <init> : (Landroid/graphics/fonts/FontFamily;)V
    //   281: aload_2
    //   282: invokevirtual setStyle : (Landroid/graphics/fonts/FontStyle;)Landroid/graphics/Typeface$CustomFallbackBuilder;
    //   285: invokevirtual build : ()Landroid/graphics/Typeface;
    //   288: areturn
    //   289: astore_1
    //   290: goto -> 209
    // Exception table:
    //   from	to	target	type
    //   37	52	289	java/io/IOException
    //   68	73	289	java/io/IOException
    //   79	111	176	finally
    //   117	137	176	finally
    //   141	152	176	finally
    //   158	165	176	finally
    //   183	188	191	finally
    //   196	203	289	java/io/IOException
    //   206	209	289	java/io/IOException
  }
  
  protected Typeface createFromInputStream(Context paramContext, InputStream paramInputStream) {
    throw new RuntimeException("Do not use this function in API 29 or later.");
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2) {
    try {
      Font.Builder builder = new Font.Builder();
      this(paramResources, paramInt1);
      Font font = builder.build();
      FontFamily.Builder builder1 = new FontFamily.Builder();
      this(font);
      FontFamily fontFamily = builder1.build();
      return (new Typeface.CustomFallbackBuilder(fontFamily)).setStyle(font.getStyle()).build();
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    throw new RuntimeException("Do not use this function in API 29 or later.");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\TypefaceCompatApi29Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */