package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

public class IconCompat extends CustomVersionedParcelable {
  private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25F;
  
  private static final int AMBIENT_SHADOW_ALPHA = 30;
  
  private static final float BLUR_FACTOR = 0.010416667F;
  
  static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
  
  private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667F;
  
  private static final String EXTRA_INT1 = "int1";
  
  private static final String EXTRA_INT2 = "int2";
  
  private static final String EXTRA_OBJ = "obj";
  
  private static final String EXTRA_TINT_LIST = "tint_list";
  
  private static final String EXTRA_TINT_MODE = "tint_mode";
  
  private static final String EXTRA_TYPE = "type";
  
  private static final float ICON_DIAMETER_FACTOR = 0.9166667F;
  
  private static final int KEY_SHADOW_ALPHA = 61;
  
  private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334F;
  
  private static final String TAG = "IconCompat";
  
  public static final int TYPE_ADAPTIVE_BITMAP = 5;
  
  public static final int TYPE_BITMAP = 1;
  
  public static final int TYPE_DATA = 3;
  
  public static final int TYPE_RESOURCE = 2;
  
  public static final int TYPE_UNKNOWN = -1;
  
  public static final int TYPE_URI = 4;
  
  public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
  
  public byte[] mData = null;
  
  public int mInt1 = 0;
  
  public int mInt2 = 0;
  
  Object mObj1;
  
  public Parcelable mParcelable = null;
  
  public ColorStateList mTintList = null;
  
  PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;
  
  public String mTintModeStr = null;
  
  public int mType = -1;
  
  public IconCompat() {}
  
  private IconCompat(int paramInt) {
    this.mType = paramInt;
  }
  
  public static IconCompat createFromBundle(Bundle paramBundle) {
    StringBuilder stringBuilder;
    int i = paramBundle.getInt("type");
    IconCompat iconCompat = new IconCompat(i);
    iconCompat.mInt1 = paramBundle.getInt("int1");
    iconCompat.mInt2 = paramBundle.getInt("int2");
    if (paramBundle.containsKey("tint_list"))
      iconCompat.mTintList = (ColorStateList)paramBundle.getParcelable("tint_list"); 
    if (paramBundle.containsKey("tint_mode"))
      iconCompat.mTintMode = PorterDuff.Mode.valueOf(paramBundle.getString("tint_mode")); 
    switch (i) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown type ");
        stringBuilder.append(i);
        Log.w("IconCompat", stringBuilder.toString());
        return null;
      case 3:
        iconCompat.mObj1 = stringBuilder.getByteArray("obj");
        return iconCompat;
      case 2:
      case 4:
      case 6:
        iconCompat.mObj1 = stringBuilder.getString("obj");
        return iconCompat;
      case -1:
      case 1:
      case 5:
        break;
    } 
    iconCompat.mObj1 = stringBuilder.getParcelable("obj");
    return iconCompat;
  }
  
  public static IconCompat createFromIcon(Context paramContext, Icon paramIcon) {
    IconCompat iconCompat;
    Preconditions.checkNotNull(paramIcon);
    int i = getType(paramIcon);
    if (i != 2) {
      if (i != 4) {
        if (i != 6) {
          iconCompat = new IconCompat(-1);
          iconCompat.mObj1 = paramIcon;
          return iconCompat;
        } 
        return createWithAdaptiveBitmapContentUri(getUri(paramIcon));
      } 
      return createWithContentUri(getUri(paramIcon));
    } 
    String str = getResPackage(paramIcon);
    try {
      return createWithResource(getResources((Context)iconCompat, str), str, getResId(paramIcon));
    } catch (android.content.res.Resources.NotFoundException notFoundException) {
      throw new IllegalArgumentException("Icon resource cannot be found");
    } 
  }
  
  public static IconCompat createFromIcon(Icon paramIcon) {
    Preconditions.checkNotNull(paramIcon);
    int i = getType(paramIcon);
    if (i != 2) {
      if (i != 4) {
        if (i != 6) {
          IconCompat iconCompat = new IconCompat(-1);
          iconCompat.mObj1 = paramIcon;
          return iconCompat;
        } 
        return createWithAdaptiveBitmapContentUri(getUri(paramIcon));
      } 
      return createWithContentUri(getUri(paramIcon));
    } 
    return createWithResource(null, getResPackage(paramIcon), getResId(paramIcon));
  }
  
  public static IconCompat createFromIconOrNullIfZeroResId(Icon paramIcon) {
    return (getType(paramIcon) == 2 && getResId(paramIcon) == 0) ? null : createFromIcon(paramIcon);
  }
  
  static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap paramBitmap, boolean paramBoolean) {
    int i = (int)(Math.min(paramBitmap.getWidth(), paramBitmap.getHeight()) * 0.6666667F);
    Bitmap bitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(3);
    float f3 = i;
    float f1 = 0.5F * f3;
    float f2 = 0.9166667F * f1;
    if (paramBoolean) {
      float f = 0.010416667F * f3;
      paint.setColor(0);
      paint.setShadowLayer(f, 0.0F, f3 * 0.020833334F, 1023410176);
      canvas.drawCircle(f1, f1, f2, paint);
      paint.setShadowLayer(f, 0.0F, 0.0F, 503316480);
      canvas.drawCircle(f1, f1, f2, paint);
      paint.clearShadowLayer();
    } 
    paint.setColor(-16777216);
    BitmapShader bitmapShader = new BitmapShader(paramBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    Matrix matrix = new Matrix();
    matrix.setTranslate((-(paramBitmap.getWidth() - i) / 2), (-(paramBitmap.getHeight() - i) / 2));
    bitmapShader.setLocalMatrix(matrix);
    paint.setShader((Shader)bitmapShader);
    canvas.drawCircle(f1, f1, f2, paint);
    canvas.setBitmap(null);
    return bitmap;
  }
  
  public static IconCompat createWithAdaptiveBitmap(Bitmap paramBitmap) {
    if (paramBitmap != null) {
      IconCompat iconCompat = new IconCompat(5);
      iconCompat.mObj1 = paramBitmap;
      return iconCompat;
    } 
    throw new IllegalArgumentException("Bitmap must not be null.");
  }
  
  public static IconCompat createWithAdaptiveBitmapContentUri(Uri paramUri) {
    if (paramUri != null)
      return createWithAdaptiveBitmapContentUri(paramUri.toString()); 
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static IconCompat createWithAdaptiveBitmapContentUri(String paramString) {
    if (paramString != null) {
      IconCompat iconCompat = new IconCompat(6);
      iconCompat.mObj1 = paramString;
      return iconCompat;
    } 
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static IconCompat createWithBitmap(Bitmap paramBitmap) {
    if (paramBitmap != null) {
      IconCompat iconCompat = new IconCompat(1);
      iconCompat.mObj1 = paramBitmap;
      return iconCompat;
    } 
    throw new IllegalArgumentException("Bitmap must not be null.");
  }
  
  public static IconCompat createWithContentUri(Uri paramUri) {
    if (paramUri != null)
      return createWithContentUri(paramUri.toString()); 
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static IconCompat createWithContentUri(String paramString) {
    if (paramString != null) {
      IconCompat iconCompat = new IconCompat(4);
      iconCompat.mObj1 = paramString;
      return iconCompat;
    } 
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static IconCompat createWithData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte != null) {
      IconCompat iconCompat = new IconCompat(3);
      iconCompat.mObj1 = paramArrayOfbyte;
      iconCompat.mInt1 = paramInt1;
      iconCompat.mInt2 = paramInt2;
      return iconCompat;
    } 
    throw new IllegalArgumentException("Data must not be null.");
  }
  
  public static IconCompat createWithResource(Context paramContext, int paramInt) {
    if (paramContext != null)
      return createWithResource(paramContext.getResources(), paramContext.getPackageName(), paramInt); 
    throw new IllegalArgumentException("Context must not be null.");
  }
  
  public static IconCompat createWithResource(Resources paramResources, String paramString, int paramInt) {
    if (paramString != null) {
      if (paramInt != 0) {
        IconCompat iconCompat = new IconCompat(2);
        iconCompat.mInt1 = paramInt;
        if (paramResources != null) {
          try {
            iconCompat.mObj1 = paramResources.getResourceName(paramInt);
          } catch (android.content.res.Resources.NotFoundException notFoundException) {
            throw new IllegalArgumentException("Icon resource cannot be found");
          } 
        } else {
          iconCompat.mObj1 = paramString;
        } 
        return iconCompat;
      } 
      throw new IllegalArgumentException("Drawable resource ID must not be 0");
    } 
    throw new IllegalArgumentException("Package must not be null.");
  }
  
  private static int getResId(Icon paramIcon) {
    if (Build.VERSION.SDK_INT >= 28)
      return paramIcon.getResId(); 
    try {
      return ((Integer)paramIcon.getClass().getMethod("getResId", new Class[0]).invoke(paramIcon, new Object[0])).intValue();
    } catch (IllegalAccessException illegalAccessException) {
      Log.e("IconCompat", "Unable to get icon resource", illegalAccessException);
      return 0;
    } catch (InvocationTargetException invocationTargetException) {
      Log.e("IconCompat", "Unable to get icon resource", invocationTargetException);
      return 0;
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.e("IconCompat", "Unable to get icon resource", noSuchMethodException);
      return 0;
    } 
  }
  
  private static String getResPackage(Icon paramIcon) {
    if (Build.VERSION.SDK_INT >= 28)
      return paramIcon.getResPackage(); 
    try {
      return (String)paramIcon.getClass().getMethod("getResPackage", new Class[0]).invoke(paramIcon, new Object[0]);
    } catch (IllegalAccessException illegalAccessException) {
      Log.e("IconCompat", "Unable to get icon package", illegalAccessException);
      return null;
    } catch (InvocationTargetException invocationTargetException) {
      Log.e("IconCompat", "Unable to get icon package", invocationTargetException);
      return null;
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.e("IconCompat", "Unable to get icon package", noSuchMethodException);
      return null;
    } 
  }
  
  private static Resources getResources(Context paramContext, String paramString) {
    if ("android".equals(paramString))
      return Resources.getSystem(); 
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      ApplicationInfo applicationInfo = packageManager.getApplicationInfo(paramString, 8192);
      return (applicationInfo != null) ? packageManager.getResourcesForApplication(applicationInfo) : null;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      Log.e("IconCompat", String.format("Unable to find pkg=%s for icon", new Object[] { paramString }), (Throwable)nameNotFoundException);
      return null;
    } 
  }
  
  private static int getType(Icon paramIcon) {
    if (Build.VERSION.SDK_INT >= 28)
      return paramIcon.getType(); 
    try {
      return ((Integer)paramIcon.getClass().getMethod("getType", new Class[0]).invoke(paramIcon, new Object[0])).intValue();
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to get icon type ");
      stringBuilder.append(paramIcon);
      Log.e("IconCompat", stringBuilder.toString(), illegalAccessException);
      return -1;
    } catch (InvocationTargetException invocationTargetException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to get icon type ");
      stringBuilder.append(paramIcon);
      Log.e("IconCompat", stringBuilder.toString(), invocationTargetException);
      return -1;
    } catch (NoSuchMethodException noSuchMethodException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to get icon type ");
      stringBuilder.append(paramIcon);
      Log.e("IconCompat", stringBuilder.toString(), noSuchMethodException);
      return -1;
    } 
  }
  
  private static Uri getUri(Icon paramIcon) {
    if (Build.VERSION.SDK_INT >= 28)
      return paramIcon.getUri(); 
    try {
      return (Uri)paramIcon.getClass().getMethod("getUri", new Class[0]).invoke(paramIcon, new Object[0]);
    } catch (IllegalAccessException illegalAccessException) {
      Log.e("IconCompat", "Unable to get icon uri", illegalAccessException);
      return null;
    } catch (InvocationTargetException invocationTargetException) {
      Log.e("IconCompat", "Unable to get icon uri", invocationTargetException);
      return null;
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.e("IconCompat", "Unable to get icon uri", noSuchMethodException);
      return null;
    } 
  }
  
  private InputStream getUriInputStream(Context paramContext) {
    Uri uri = getUri();
    String str = uri.getScheme();
    if ("content".equals(str) || "file".equals(str)) {
      try {
        return paramContext.getContentResolver().openInputStream(uri);
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to load image from URI: ");
        stringBuilder.append(uri);
        Log.w("IconCompat", stringBuilder.toString(), exception);
      } 
      return null;
    } 
    try {
      File file = new File();
      this((String)this.mObj1);
      return new FileInputStream(file);
    } catch (FileNotFoundException fileNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to load image from path: ");
      stringBuilder.append(uri);
      Log.w("IconCompat", stringBuilder.toString(), fileNotFoundException);
    } 
    return null;
  }
  
  private Drawable loadDrawableInner(Context paramContext) {
    InputStream inputStream;
    String str1;
    Resources resources;
    String str2;
    switch (this.mType) {
      default:
        return null;
      case 6:
        inputStream = getUriInputStream(paramContext);
        if (inputStream != null)
          return (Drawable)((Build.VERSION.SDK_INT >= 26) ? new AdaptiveIconDrawable(null, (Drawable)new BitmapDrawable(paramContext.getResources(), BitmapFactory.decodeStream(inputStream))) : new BitmapDrawable(paramContext.getResources(), createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(inputStream), false))); 
      case 5:
        return (Drawable)new BitmapDrawable(paramContext.getResources(), createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, false));
      case 4:
        inputStream = getUriInputStream(paramContext);
        if (inputStream != null)
          return (Drawable)new BitmapDrawable(paramContext.getResources(), BitmapFactory.decodeStream(inputStream)); 
      case 3:
        return (Drawable)new BitmapDrawable(paramContext.getResources(), BitmapFactory.decodeByteArray((byte[])this.mObj1, this.mInt1, this.mInt2));
      case 2:
        str2 = getResPackage();
        str1 = str2;
        if (TextUtils.isEmpty(str2))
          str1 = paramContext.getPackageName(); 
        resources = getResources(paramContext, str1);
        try {
          return ResourcesCompat.getDrawable(resources, this.mInt1, paramContext.getTheme());
        } catch (RuntimeException runtimeException) {
          Log.e("IconCompat", String.format("Unable to load resource 0x%08x from pkg=%s", new Object[] { Integer.valueOf(this.mInt1), this.mObj1 }), runtimeException);
        } 
      case 1:
        break;
    } 
    return (Drawable)new BitmapDrawable(runtimeException.getResources(), (Bitmap)this.mObj1);
  }
  
  private static String typeToString(int paramInt) {
    switch (paramInt) {
      default:
        return "UNKNOWN";
      case 6:
        return "URI_MASKABLE";
      case 5:
        return "BITMAP_MASKABLE";
      case 4:
        return "URI";
      case 3:
        return "DATA";
      case 2:
        return "RESOURCE";
      case 1:
        break;
    } 
    return "BITMAP";
  }
  
  public void addToShortcutIntent(Intent paramIntent, Drawable paramDrawable, Context paramContext) {
    StringBuilder stringBuilder;
    Bitmap bitmap;
    checkResource(paramContext);
    int i = this.mType;
    if (i != 1) {
      if (i != 2) {
        if (i == 5) {
          bitmap = createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
        } else {
          throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
        } 
      } else {
        try {
          Bitmap bitmap1;
          Context context = bitmap.createPackageContext(getResPackage(), 0);
          if (paramDrawable == null) {
            paramIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", (Parcelable)Intent.ShortcutIconResource.fromContext(context, this.mInt1));
            return;
          } 
          Drawable drawable = ContextCompat.getDrawable(context, this.mInt1);
          if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            i = ((ActivityManager)context.getSystemService("activity")).getLauncherLargeIconSize();
            bitmap1 = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
          } else {
            bitmap1 = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
          } 
          drawable.setBounds(0, 0, bitmap1.getWidth(), bitmap1.getHeight());
          Canvas canvas = new Canvas();
          this(bitmap1);
          drawable.draw(canvas);
        } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("Can't find package ");
          stringBuilder.append(this.mObj1);
          throw new IllegalArgumentException(stringBuilder.toString(), nameNotFoundException);
        } 
      } 
    } else {
      Bitmap bitmap1 = (Bitmap)this.mObj1;
      bitmap = bitmap1;
      if (stringBuilder != null)
        bitmap = bitmap1.copy(bitmap1.getConfig(), true); 
    } 
    if (stringBuilder != null) {
      i = bitmap.getWidth();
      int j = bitmap.getHeight();
      stringBuilder.setBounds(i / 2, j / 2, i, j);
      stringBuilder.draw(new Canvas(bitmap));
    } 
    nameNotFoundException.putExtra("android.intent.extra.shortcut.ICON", (Parcelable)bitmap);
  }
  
  public void checkResource(Context paramContext) {
    if (this.mType == 2) {
      String str3 = (String)this.mObj1;
      if (!str3.contains(":"))
        return; 
      String str1 = str3.split(":", -1)[1];
      String str2 = str1.split("/", -1)[0];
      str1 = str1.split("/", -1)[1];
      str3 = str3.split(":", -1)[0];
      int i = getResources(paramContext, str3).getIdentifier(str1, str2, str3);
      if (this.mInt1 != i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id has changed for ");
        stringBuilder.append(str3);
        stringBuilder.append("/");
        stringBuilder.append(str1);
        Log.i("IconCompat", stringBuilder.toString());
        this.mInt1 = i;
      } 
    } 
  }
  
  public Bitmap getBitmap() {
    if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
      Object object = this.mObj1;
      return (object instanceof Bitmap) ? (Bitmap)object : null;
    } 
    int i = this.mType;
    if (i == 1)
      return (Bitmap)this.mObj1; 
    if (i == 5)
      return createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("called getBitmap() on ");
    stringBuilder.append(this);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public int getResId() {
    if (this.mType == -1 && Build.VERSION.SDK_INT >= 23)
      return getResId((Icon)this.mObj1); 
    if (this.mType == 2)
      return this.mInt1; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("called getResId() on ");
    stringBuilder.append(this);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public String getResPackage() {
    if (this.mType == -1 && Build.VERSION.SDK_INT >= 23)
      return getResPackage((Icon)this.mObj1); 
    if (this.mType == 2)
      return ((String)this.mObj1).split(":", -1)[0]; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("called getResPackage() on ");
    stringBuilder.append(this);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public int getType() {
    return (this.mType == -1 && Build.VERSION.SDK_INT >= 23) ? getType((Icon)this.mObj1) : this.mType;
  }
  
  public Uri getUri() {
    if (this.mType == -1 && Build.VERSION.SDK_INT >= 23)
      return getUri((Icon)this.mObj1); 
    int i = this.mType;
    if (i == 4 || i == 6)
      return Uri.parse((String)this.mObj1); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("called getUri() on ");
    stringBuilder.append(this);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public Drawable loadDrawable(Context paramContext) {
    checkResource(paramContext);
    if (Build.VERSION.SDK_INT >= 23)
      return toIcon(paramContext).loadDrawable(paramContext); 
    Drawable drawable = loadDrawableInner(paramContext);
    if (drawable != null && (this.mTintList != null || this.mTintMode != DEFAULT_TINT_MODE)) {
      drawable.mutate();
      DrawableCompat.setTintList(drawable, this.mTintList);
      DrawableCompat.setTintMode(drawable, this.mTintMode);
    } 
    return drawable;
  }
  
  public void onPostParceling() {
    this.mTintMode = PorterDuff.Mode.valueOf(this.mTintModeStr);
    switch (this.mType) {
      default:
        return;
      case 3:
        this.mObj1 = this.mData;
      case 2:
      case 4:
      case 6:
        this.mObj1 = new String(this.mData, Charset.forName("UTF-16"));
      case 1:
      case 5:
        parcelable = this.mParcelable;
        if (parcelable != null) {
          this.mObj1 = parcelable;
        } else {
          byte[] arrayOfByte = this.mData;
          this.mObj1 = arrayOfByte;
          this.mType = 3;
          this.mInt1 = 0;
          this.mInt2 = arrayOfByte.length;
        } 
      case -1:
        break;
    } 
    Parcelable parcelable = this.mParcelable;
    if (parcelable != null)
      this.mObj1 = parcelable; 
    throw new IllegalArgumentException("Invalid icon");
  }
  
  public void onPreParceling(boolean paramBoolean) {
    this.mTintModeStr = this.mTintMode.name();
    switch (this.mType) {
      default:
        return;
      case 4:
      case 6:
        this.mData = this.mObj1.toString().getBytes(Charset.forName("UTF-16"));
      case 3:
        this.mData = (byte[])this.mObj1;
      case 2:
        this.mData = ((String)this.mObj1).getBytes(Charset.forName("UTF-16"));
      case 1:
      case 5:
        if (paramBoolean) {
          Bitmap bitmap = (Bitmap)this.mObj1;
          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
          bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
          this.mData = byteArrayOutputStream.toByteArray();
        } else {
          this.mParcelable = (Parcelable)this.mObj1;
        } 
      case -1:
        break;
    } 
    if (!paramBoolean)
      this.mParcelable = (Parcelable)this.mObj1; 
    throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
  }
  
  public IconCompat setTint(int paramInt) {
    return setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public IconCompat setTintList(ColorStateList paramColorStateList) {
    this.mTintList = paramColorStateList;
    return this;
  }
  
  public IconCompat setTintMode(PorterDuff.Mode paramMode) {
    this.mTintMode = paramMode;
    return this;
  }
  
  public Bundle toBundle() {
    Bundle bundle = new Bundle();
    switch (this.mType) {
      default:
        throw new IllegalArgumentException("Invalid icon");
      case 3:
        bundle.putByteArray("obj", (byte[])this.mObj1);
        break;
      case 2:
      case 4:
      case 6:
        bundle.putString("obj", (String)this.mObj1);
        break;
      case 1:
      case 5:
        bundle.putParcelable("obj", (Parcelable)this.mObj1);
        break;
      case -1:
        bundle.putParcelable("obj", (Parcelable)this.mObj1);
        break;
    } 
    bundle.putInt("type", this.mType);
    bundle.putInt("int1", this.mInt1);
    bundle.putInt("int2", this.mInt2);
    ColorStateList colorStateList = this.mTintList;
    if (colorStateList != null)
      bundle.putParcelable("tint_list", (Parcelable)colorStateList); 
    PorterDuff.Mode mode = this.mTintMode;
    if (mode != DEFAULT_TINT_MODE)
      bundle.putString("tint_mode", mode.name()); 
    return bundle;
  }
  
  @Deprecated
  public Icon toIcon() {
    return toIcon(null);
  }
  
  public Icon toIcon(Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mType : I
    //   4: tableswitch default -> 52, -1 -> 322, 0 -> 52, 1 -> 276, 2 -> 261, 3 -> 239, 4 -> 225, 5 -> 185, 6 -> 63
    //   52: new java/lang/IllegalArgumentException
    //   55: dup
    //   56: ldc_w 'Unknown type'
    //   59: invokespecial <init> : (Ljava/lang/String;)V
    //   62: athrow
    //   63: aload_1
    //   64: ifnull -> 148
    //   67: aload_0
    //   68: aload_1
    //   69: invokespecial getUriInputStream : (Landroid/content/Context;)Ljava/io/InputStream;
    //   72: astore_1
    //   73: aload_1
    //   74: ifnull -> 111
    //   77: getstatic android/os/Build$VERSION.SDK_INT : I
    //   80: bipush #26
    //   82: if_icmplt -> 96
    //   85: aload_1
    //   86: invokestatic decodeStream : (Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   89: invokestatic createWithAdaptiveBitmap : (Landroid/graphics/Bitmap;)Landroid/graphics/drawable/Icon;
    //   92: astore_1
    //   93: goto -> 287
    //   96: aload_1
    //   97: invokestatic decodeStream : (Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   100: iconst_0
    //   101: invokestatic createLegacyIconFromAdaptiveIcon : (Landroid/graphics/Bitmap;Z)Landroid/graphics/Bitmap;
    //   104: invokestatic createWithBitmap : (Landroid/graphics/Bitmap;)Landroid/graphics/drawable/Icon;
    //   107: astore_1
    //   108: goto -> 287
    //   111: new java/lang/StringBuilder
    //   114: dup
    //   115: invokespecial <init> : ()V
    //   118: astore_1
    //   119: aload_1
    //   120: ldc_w 'Cannot load adaptive icon from uri: '
    //   123: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: aload_1
    //   128: aload_0
    //   129: invokevirtual getUri : ()Landroid/net/Uri;
    //   132: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: new java/lang/IllegalStateException
    //   139: dup
    //   140: aload_1
    //   141: invokevirtual toString : ()Ljava/lang/String;
    //   144: invokespecial <init> : (Ljava/lang/String;)V
    //   147: athrow
    //   148: new java/lang/StringBuilder
    //   151: dup
    //   152: invokespecial <init> : ()V
    //   155: astore_1
    //   156: aload_1
    //   157: ldc_w 'Context is required to resolve the file uri of the icon: '
    //   160: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: aload_1
    //   165: aload_0
    //   166: invokevirtual getUri : ()Landroid/net/Uri;
    //   169: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   172: pop
    //   173: new java/lang/IllegalArgumentException
    //   176: dup
    //   177: aload_1
    //   178: invokevirtual toString : ()Ljava/lang/String;
    //   181: invokespecial <init> : (Ljava/lang/String;)V
    //   184: athrow
    //   185: getstatic android/os/Build$VERSION.SDK_INT : I
    //   188: bipush #26
    //   190: if_icmplt -> 207
    //   193: aload_0
    //   194: getfield mObj1 : Ljava/lang/Object;
    //   197: checkcast android/graphics/Bitmap
    //   200: invokestatic createWithAdaptiveBitmap : (Landroid/graphics/Bitmap;)Landroid/graphics/drawable/Icon;
    //   203: astore_1
    //   204: goto -> 287
    //   207: aload_0
    //   208: getfield mObj1 : Ljava/lang/Object;
    //   211: checkcast android/graphics/Bitmap
    //   214: iconst_0
    //   215: invokestatic createLegacyIconFromAdaptiveIcon : (Landroid/graphics/Bitmap;Z)Landroid/graphics/Bitmap;
    //   218: invokestatic createWithBitmap : (Landroid/graphics/Bitmap;)Landroid/graphics/drawable/Icon;
    //   221: astore_1
    //   222: goto -> 287
    //   225: aload_0
    //   226: getfield mObj1 : Ljava/lang/Object;
    //   229: checkcast java/lang/String
    //   232: invokestatic createWithContentUri : (Ljava/lang/String;)Landroid/graphics/drawable/Icon;
    //   235: astore_1
    //   236: goto -> 287
    //   239: aload_0
    //   240: getfield mObj1 : Ljava/lang/Object;
    //   243: checkcast [B
    //   246: aload_0
    //   247: getfield mInt1 : I
    //   250: aload_0
    //   251: getfield mInt2 : I
    //   254: invokestatic createWithData : ([BII)Landroid/graphics/drawable/Icon;
    //   257: astore_1
    //   258: goto -> 287
    //   261: aload_0
    //   262: invokevirtual getResPackage : ()Ljava/lang/String;
    //   265: aload_0
    //   266: getfield mInt1 : I
    //   269: invokestatic createWithResource : (Ljava/lang/String;I)Landroid/graphics/drawable/Icon;
    //   272: astore_1
    //   273: goto -> 287
    //   276: aload_0
    //   277: getfield mObj1 : Ljava/lang/Object;
    //   280: checkcast android/graphics/Bitmap
    //   283: invokestatic createWithBitmap : (Landroid/graphics/Bitmap;)Landroid/graphics/drawable/Icon;
    //   286: astore_1
    //   287: aload_0
    //   288: getfield mTintList : Landroid/content/res/ColorStateList;
    //   291: astore_2
    //   292: aload_2
    //   293: ifnull -> 302
    //   296: aload_1
    //   297: aload_2
    //   298: invokevirtual setTintList : (Landroid/content/res/ColorStateList;)Landroid/graphics/drawable/Icon;
    //   301: pop
    //   302: aload_0
    //   303: getfield mTintMode : Landroid/graphics/PorterDuff$Mode;
    //   306: astore_2
    //   307: aload_2
    //   308: getstatic androidx/core/graphics/drawable/IconCompat.DEFAULT_TINT_MODE : Landroid/graphics/PorterDuff$Mode;
    //   311: if_acmpeq -> 320
    //   314: aload_1
    //   315: aload_2
    //   316: invokevirtual setTintMode : (Landroid/graphics/PorterDuff$Mode;)Landroid/graphics/drawable/Icon;
    //   319: pop
    //   320: aload_1
    //   321: areturn
    //   322: aload_0
    //   323: getfield mObj1 : Ljava/lang/Object;
    //   326: checkcast android/graphics/drawable/Icon
    //   329: areturn
  }
  
  public String toString() {
    if (this.mType == -1)
      return String.valueOf(this.mObj1); 
    StringBuilder stringBuilder = new StringBuilder("Icon(typ=");
    stringBuilder.append(typeToString(this.mType));
    switch (this.mType) {
      case 4:
      case 6:
        stringBuilder.append(" uri=");
        stringBuilder.append(this.mObj1);
        break;
      case 3:
        stringBuilder.append(" len=");
        stringBuilder.append(this.mInt1);
        if (this.mInt2 != 0) {
          stringBuilder.append(" off=");
          stringBuilder.append(this.mInt2);
        } 
        break;
      case 2:
        stringBuilder.append(" pkg=");
        stringBuilder.append(getResPackage());
        stringBuilder.append(" id=");
        stringBuilder.append(String.format("0x%08x", new Object[] { Integer.valueOf(getResId()) }));
        break;
      case 1:
      case 5:
        stringBuilder.append(" size=");
        stringBuilder.append(((Bitmap)this.mObj1).getWidth());
        stringBuilder.append("x");
        stringBuilder.append(((Bitmap)this.mObj1).getHeight());
        break;
    } 
    if (this.mTintList != null) {
      stringBuilder.append(" tint=");
      stringBuilder.append(this.mTintList);
    } 
    if (this.mTintMode != DEFAULT_TINT_MODE) {
      stringBuilder.append(" mode=");
      stringBuilder.append(this.mTintMode);
    } 
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface IconType {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\drawable\IconCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */