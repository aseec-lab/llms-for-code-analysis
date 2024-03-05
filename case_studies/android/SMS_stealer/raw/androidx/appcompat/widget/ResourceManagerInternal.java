package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat;
import androidx.appcompat.resources.R;
import androidx.collection.LongSparseArray;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourceManagerInternal {
  private static final ColorFilterLruCache COLOR_FILTER_CACHE;
  
  private static final boolean DEBUG = false;
  
  private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
  
  private static ResourceManagerInternal INSTANCE;
  
  private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
  
  private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
  
  private static final String TAG = "ResourceManagerInternal";
  
  private SimpleArrayMap<String, InflateDelegate> mDelegates;
  
  private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>>(0);
  
  private boolean mHasCheckedVectorDrawableSetup;
  
  private ResourceManagerHooks mHooks;
  
  private SparseArrayCompat<String> mKnownDrawableIdTags;
  
  private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
  
  private TypedValue mTypedValue;
  
  static {
    COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
  }
  
  private void addDelegate(String paramString, InflateDelegate paramInflateDelegate) {
    if (this.mDelegates == null)
      this.mDelegates = new SimpleArrayMap(); 
    this.mDelegates.put(paramString, paramInflateDelegate);
  }
  
  private boolean addDrawableToCache(Context paramContext, long paramLong, Drawable paramDrawable) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload #4
    //   4: invokevirtual getConstantState : ()Landroid/graphics/drawable/Drawable$ConstantState;
    //   7: astore #6
    //   9: aload #6
    //   11: ifnull -> 78
    //   14: aload_0
    //   15: getfield mDrawableCaches : Ljava/util/WeakHashMap;
    //   18: aload_1
    //   19: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   22: checkcast androidx/collection/LongSparseArray
    //   25: astore #5
    //   27: aload #5
    //   29: astore #4
    //   31: aload #5
    //   33: ifnonnull -> 57
    //   36: new androidx/collection/LongSparseArray
    //   39: astore #4
    //   41: aload #4
    //   43: invokespecial <init> : ()V
    //   46: aload_0
    //   47: getfield mDrawableCaches : Ljava/util/WeakHashMap;
    //   50: aload_1
    //   51: aload #4
    //   53: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   56: pop
    //   57: new java/lang/ref/WeakReference
    //   60: astore_1
    //   61: aload_1
    //   62: aload #6
    //   64: invokespecial <init> : (Ljava/lang/Object;)V
    //   67: aload #4
    //   69: lload_2
    //   70: aload_1
    //   71: invokevirtual put : (JLjava/lang/Object;)V
    //   74: aload_0
    //   75: monitorexit
    //   76: iconst_1
    //   77: ireturn
    //   78: aload_0
    //   79: monitorexit
    //   80: iconst_0
    //   81: ireturn
    //   82: astore_1
    //   83: aload_0
    //   84: monitorexit
    //   85: aload_1
    //   86: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	82	finally
    //   14	27	82	finally
    //   36	57	82	finally
    //   57	74	82	finally
  }
  
  private void addTintListToCache(Context paramContext, int paramInt, ColorStateList paramColorStateList) {
    if (this.mTintLists == null)
      this.mTintLists = new WeakHashMap<Context, SparseArrayCompat<ColorStateList>>(); 
    SparseArrayCompat<ColorStateList> sparseArrayCompat2 = this.mTintLists.get(paramContext);
    SparseArrayCompat<ColorStateList> sparseArrayCompat1 = sparseArrayCompat2;
    if (sparseArrayCompat2 == null) {
      sparseArrayCompat1 = new SparseArrayCompat();
      this.mTintLists.put(paramContext, sparseArrayCompat1);
    } 
    sparseArrayCompat1.append(paramInt, paramColorStateList);
  }
  
  private void checkVectorDrawableSetup(Context paramContext) {
    if (this.mHasCheckedVectorDrawableSetup)
      return; 
    this.mHasCheckedVectorDrawableSetup = true;
    Drawable drawable = getDrawable(paramContext, R.drawable.abc_vector_test);
    if (drawable != null && isVectorDrawable(drawable))
      return; 
    this.mHasCheckedVectorDrawableSetup = false;
    throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
  }
  
  private static long createCacheKey(TypedValue paramTypedValue) {
    return paramTypedValue.assetCookie << 32L | paramTypedValue.data;
  }
  
  private Drawable createDrawableIfNeeded(Context paramContext, int paramInt) {
    Drawable drawable1;
    if (this.mTypedValue == null)
      this.mTypedValue = new TypedValue(); 
    TypedValue typedValue = this.mTypedValue;
    paramContext.getResources().getValue(paramInt, typedValue, true);
    long l = createCacheKey(typedValue);
    Drawable drawable2 = getCachedDrawable(paramContext, l);
    if (drawable2 != null)
      return drawable2; 
    ResourceManagerHooks resourceManagerHooks = this.mHooks;
    if (resourceManagerHooks == null) {
      resourceManagerHooks = null;
    } else {
      drawable1 = resourceManagerHooks.createDrawableFor(this, paramContext, paramInt);
    } 
    if (drawable1 != null) {
      drawable1.setChangingConfigurations(typedValue.changingConfigurations);
      addDrawableToCache(paramContext, l, drawable1);
    } 
    return drawable1;
  }
  
  private static PorterDuffColorFilter createTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, int[] paramArrayOfint) {
    return (paramColorStateList == null || paramMode == null) ? null : getPorterDuffColorFilter(paramColorStateList.getColorForState(paramArrayOfint, 0), paramMode);
  }
  
  public static ResourceManagerInternal get() {
    // Byte code:
    //   0: ldc androidx/appcompat/widget/ResourceManagerInternal
    //   2: monitorenter
    //   3: getstatic androidx/appcompat/widget/ResourceManagerInternal.INSTANCE : Landroidx/appcompat/widget/ResourceManagerInternal;
    //   6: ifnonnull -> 25
    //   9: new androidx/appcompat/widget/ResourceManagerInternal
    //   12: astore_0
    //   13: aload_0
    //   14: invokespecial <init> : ()V
    //   17: aload_0
    //   18: putstatic androidx/appcompat/widget/ResourceManagerInternal.INSTANCE : Landroidx/appcompat/widget/ResourceManagerInternal;
    //   21: aload_0
    //   22: invokestatic installDefaultInflateDelegates : (Landroidx/appcompat/widget/ResourceManagerInternal;)V
    //   25: getstatic androidx/appcompat/widget/ResourceManagerInternal.INSTANCE : Landroidx/appcompat/widget/ResourceManagerInternal;
    //   28: astore_0
    //   29: ldc androidx/appcompat/widget/ResourceManagerInternal
    //   31: monitorexit
    //   32: aload_0
    //   33: areturn
    //   34: astore_0
    //   35: ldc androidx/appcompat/widget/ResourceManagerInternal
    //   37: monitorexit
    //   38: aload_0
    //   39: athrow
    // Exception table:
    //   from	to	target	type
    //   3	25	34	finally
    //   25	29	34	finally
  }
  
  private Drawable getCachedDrawable(Context paramContext, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mDrawableCaches : Ljava/util/WeakHashMap;
    //   6: aload_1
    //   7: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: checkcast androidx/collection/LongSparseArray
    //   13: astore #4
    //   15: aload #4
    //   17: ifnonnull -> 24
    //   20: aload_0
    //   21: monitorexit
    //   22: aconst_null
    //   23: areturn
    //   24: aload #4
    //   26: lload_2
    //   27: invokevirtual get : (J)Ljava/lang/Object;
    //   30: checkcast java/lang/ref/WeakReference
    //   33: astore #5
    //   35: aload #5
    //   37: ifnull -> 75
    //   40: aload #5
    //   42: invokevirtual get : ()Ljava/lang/Object;
    //   45: checkcast android/graphics/drawable/Drawable$ConstantState
    //   48: astore #5
    //   50: aload #5
    //   52: ifnull -> 69
    //   55: aload #5
    //   57: aload_1
    //   58: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   61: invokevirtual newDrawable : (Landroid/content/res/Resources;)Landroid/graphics/drawable/Drawable;
    //   64: astore_1
    //   65: aload_0
    //   66: monitorexit
    //   67: aload_1
    //   68: areturn
    //   69: aload #4
    //   71: lload_2
    //   72: invokevirtual remove : (J)V
    //   75: aload_0
    //   76: monitorexit
    //   77: aconst_null
    //   78: areturn
    //   79: astore_1
    //   80: aload_0
    //   81: monitorexit
    //   82: aload_1
    //   83: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	79	finally
    //   24	35	79	finally
    //   40	50	79	finally
    //   55	65	79	finally
    //   69	75	79	finally
  }
  
  public static PorterDuffColorFilter getPorterDuffColorFilter(int paramInt, PorterDuff.Mode paramMode) {
    // Byte code:
    //   0: ldc androidx/appcompat/widget/ResourceManagerInternal
    //   2: monitorenter
    //   3: getstatic androidx/appcompat/widget/ResourceManagerInternal.COLOR_FILTER_CACHE : Landroidx/appcompat/widget/ResourceManagerInternal$ColorFilterLruCache;
    //   6: iload_0
    //   7: aload_1
    //   8: invokevirtual get : (ILandroid/graphics/PorterDuff$Mode;)Landroid/graphics/PorterDuffColorFilter;
    //   11: astore_3
    //   12: aload_3
    //   13: astore_2
    //   14: aload_3
    //   15: ifnonnull -> 38
    //   18: new android/graphics/PorterDuffColorFilter
    //   21: astore_2
    //   22: aload_2
    //   23: iload_0
    //   24: aload_1
    //   25: invokespecial <init> : (ILandroid/graphics/PorterDuff$Mode;)V
    //   28: getstatic androidx/appcompat/widget/ResourceManagerInternal.COLOR_FILTER_CACHE : Landroidx/appcompat/widget/ResourceManagerInternal$ColorFilterLruCache;
    //   31: iload_0
    //   32: aload_1
    //   33: aload_2
    //   34: invokevirtual put : (ILandroid/graphics/PorterDuff$Mode;Landroid/graphics/PorterDuffColorFilter;)Landroid/graphics/PorterDuffColorFilter;
    //   37: pop
    //   38: ldc androidx/appcompat/widget/ResourceManagerInternal
    //   40: monitorexit
    //   41: aload_2
    //   42: areturn
    //   43: astore_1
    //   44: ldc androidx/appcompat/widget/ResourceManagerInternal
    //   46: monitorexit
    //   47: aload_1
    //   48: athrow
    // Exception table:
    //   from	to	target	type
    //   3	12	43	finally
    //   18	38	43	finally
  }
  
  private ColorStateList getTintListFromCache(Context paramContext, int paramInt) {
    WeakHashMap<Context, SparseArrayCompat<ColorStateList>> weakHashMap = this.mTintLists;
    ColorStateList colorStateList2 = null;
    ColorStateList colorStateList1 = colorStateList2;
    if (weakHashMap != null) {
      SparseArrayCompat sparseArrayCompat = weakHashMap.get(paramContext);
      colorStateList1 = colorStateList2;
      if (sparseArrayCompat != null)
        colorStateList1 = (ColorStateList)sparseArrayCompat.get(paramInt); 
    } 
    return colorStateList1;
  }
  
  private static void installDefaultInflateDelegates(ResourceManagerInternal paramResourceManagerInternal) {
    if (Build.VERSION.SDK_INT < 24) {
      paramResourceManagerInternal.addDelegate("vector", new VdcInflateDelegate());
      paramResourceManagerInternal.addDelegate("animated-vector", new AvdcInflateDelegate());
      paramResourceManagerInternal.addDelegate("animated-selector", new AsldcInflateDelegate());
    } 
  }
  
  private static boolean isVectorDrawable(Drawable paramDrawable) {
    return (paramDrawable instanceof VectorDrawableCompat || "android.graphics.drawable.VectorDrawable".equals(paramDrawable.getClass().getName()));
  }
  
  private Drawable loadDrawableFromDelegates(Context paramContext, int paramInt) {
    SimpleArrayMap<String, InflateDelegate> simpleArrayMap = this.mDelegates;
    if (simpleArrayMap != null && !simpleArrayMap.isEmpty()) {
      SparseArrayCompat<String> sparseArrayCompat = this.mKnownDrawableIdTags;
      if (sparseArrayCompat != null) {
        String str = (String)sparseArrayCompat.get(paramInt);
        if ("appcompat_skip_skip".equals(str) || (str != null && this.mDelegates.get(str) == null))
          return null; 
      } else {
        this.mKnownDrawableIdTags = new SparseArrayCompat();
      } 
      if (this.mTypedValue == null)
        this.mTypedValue = new TypedValue(); 
      TypedValue typedValue = this.mTypedValue;
      Resources resources = paramContext.getResources();
      resources.getValue(paramInt, typedValue, true);
      long l = createCacheKey(typedValue);
      Drawable drawable2 = getCachedDrawable(paramContext, l);
      if (drawable2 != null)
        return drawable2; 
      Drawable drawable1 = drawable2;
      if (typedValue.string != null) {
        drawable1 = drawable2;
        if (typedValue.string.toString().endsWith(".xml")) {
          drawable1 = drawable2;
          try {
            int i;
            XmlResourceParser xmlResourceParser = resources.getXml(paramInt);
            drawable1 = drawable2;
            AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
            while (true) {
              drawable1 = drawable2;
              i = xmlResourceParser.next();
              if (i != 2 && i != 1)
                continue; 
              break;
            } 
            if (i == 2) {
              drawable1 = drawable2;
              String str = xmlResourceParser.getName();
              drawable1 = drawable2;
              this.mKnownDrawableIdTags.append(paramInt, str);
              drawable1 = drawable2;
              InflateDelegate inflateDelegate = (InflateDelegate)this.mDelegates.get(str);
              Drawable drawable = drawable2;
              if (inflateDelegate != null) {
                drawable1 = drawable2;
                drawable = inflateDelegate.createFromXmlInner(paramContext, (XmlPullParser)xmlResourceParser, attributeSet, paramContext.getTheme());
              } 
              drawable1 = drawable;
              if (drawable != null) {
                drawable1 = drawable;
                drawable.setChangingConfigurations(typedValue.changingConfigurations);
                drawable1 = drawable;
                addDrawableToCache(paramContext, l, drawable);
                drawable1 = drawable;
              } 
            } else {
              drawable1 = drawable2;
              XmlPullParserException xmlPullParserException = new XmlPullParserException();
              drawable1 = drawable2;
              this("No start tag found");
              drawable1 = drawable2;
              throw xmlPullParserException;
            } 
          } catch (Exception exception) {
            Log.e("ResourceManagerInternal", "Exception while inflating drawable", exception);
          } 
        } 
      } 
      if (drawable1 == null)
        this.mKnownDrawableIdTags.append(paramInt, "appcompat_skip_skip"); 
      return drawable1;
    } 
    return null;
  }
  
  private Drawable tintDrawable(Context paramContext, int paramInt, boolean paramBoolean, Drawable paramDrawable) {
    Drawable drawable;
    PorterDuff.Mode mode1;
    PorterDuff.Mode mode2;
    ColorStateList colorStateList = getTintList(paramContext, paramInt);
    if (colorStateList != null) {
      drawable = paramDrawable;
      if (DrawableUtils.canSafelyMutateDrawable(paramDrawable))
        drawable = paramDrawable.mutate(); 
      drawable = DrawableCompat.wrap(drawable);
      DrawableCompat.setTintList(drawable, colorStateList);
      mode1 = getTintMode(paramInt);
      Drawable drawable1 = drawable;
      if (mode1 != null) {
        DrawableCompat.setTintMode(drawable, mode1);
        drawable1 = drawable;
      } 
    } else {
      ResourceManagerHooks resourceManagerHooks = this.mHooks;
      if (resourceManagerHooks != null && resourceManagerHooks.tintDrawable((Context)drawable, paramInt, (Drawable)mode1)) {
        mode2 = mode1;
      } else {
        mode2 = mode1;
        if (!tintDrawableUsingColorFilter((Context)drawable, paramInt, (Drawable)mode1)) {
          mode2 = mode1;
          if (paramBoolean)
            mode2 = null; 
        } 
      } 
    } 
    return (Drawable)mode2;
  }
  
  static void tintDrawable(Drawable paramDrawable, TintInfo paramTintInfo, int[] paramArrayOfint) {
    if (DrawableUtils.canSafelyMutateDrawable(paramDrawable) && paramDrawable.mutate() != paramDrawable) {
      Log.d("ResourceManagerInternal", "Mutated drawable is not the same instance as the input.");
      return;
    } 
    if (paramTintInfo.mHasTintList || paramTintInfo.mHasTintMode) {
      PorterDuff.Mode mode;
      ColorStateList colorStateList;
      if (paramTintInfo.mHasTintList) {
        colorStateList = paramTintInfo.mTintList;
      } else {
        colorStateList = null;
      } 
      if (paramTintInfo.mHasTintMode) {
        mode = paramTintInfo.mTintMode;
      } else {
        mode = DEFAULT_MODE;
      } 
      paramDrawable.setColorFilter((ColorFilter)createTintFilter(colorStateList, mode, paramArrayOfint));
    } else {
      paramDrawable.clearColorFilter();
    } 
    if (Build.VERSION.SDK_INT <= 23)
      paramDrawable.invalidateSelf(); 
  }
  
  public Drawable getDrawable(Context paramContext, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: iload_2
    //   5: iconst_0
    //   6: invokevirtual getDrawable : (Landroid/content/Context;IZ)Landroid/graphics/drawable/Drawable;
    //   9: astore_1
    //   10: aload_0
    //   11: monitorexit
    //   12: aload_1
    //   13: areturn
    //   14: astore_1
    //   15: aload_0
    //   16: monitorexit
    //   17: aload_1
    //   18: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	14	finally
  }
  
  Drawable getDrawable(Context paramContext, int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial checkVectorDrawableSetup : (Landroid/content/Context;)V
    //   7: aload_0
    //   8: aload_1
    //   9: iload_2
    //   10: invokespecial loadDrawableFromDelegates : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   13: astore #4
    //   15: aload #4
    //   17: astore #5
    //   19: aload #4
    //   21: ifnonnull -> 32
    //   24: aload_0
    //   25: aload_1
    //   26: iload_2
    //   27: invokespecial createDrawableIfNeeded : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   30: astore #5
    //   32: aload #5
    //   34: astore #4
    //   36: aload #5
    //   38: ifnonnull -> 48
    //   41: aload_1
    //   42: iload_2
    //   43: invokestatic getDrawable : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   46: astore #4
    //   48: aload #4
    //   50: astore #5
    //   52: aload #4
    //   54: ifnull -> 68
    //   57: aload_0
    //   58: aload_1
    //   59: iload_2
    //   60: iload_3
    //   61: aload #4
    //   63: invokespecial tintDrawable : (Landroid/content/Context;IZLandroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/Drawable;
    //   66: astore #5
    //   68: aload #5
    //   70: ifnull -> 78
    //   73: aload #5
    //   75: invokestatic fixDrawable : (Landroid/graphics/drawable/Drawable;)V
    //   78: aload_0
    //   79: monitorexit
    //   80: aload #5
    //   82: areturn
    //   83: astore_1
    //   84: aload_0
    //   85: monitorexit
    //   86: aload_1
    //   87: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	83	finally
    //   24	32	83	finally
    //   41	48	83	finally
    //   57	68	83	finally
    //   73	78	83	finally
  }
  
  ColorStateList getTintList(Context paramContext, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: iload_2
    //   5: invokespecial getTintListFromCache : (Landroid/content/Context;I)Landroid/content/res/ColorStateList;
    //   8: astore_3
    //   9: aload_3
    //   10: astore #4
    //   12: aload_3
    //   13: ifnonnull -> 57
    //   16: aload_0
    //   17: getfield mHooks : Landroidx/appcompat/widget/ResourceManagerInternal$ResourceManagerHooks;
    //   20: ifnonnull -> 28
    //   23: aconst_null
    //   24: astore_3
    //   25: goto -> 40
    //   28: aload_0
    //   29: getfield mHooks : Landroidx/appcompat/widget/ResourceManagerInternal$ResourceManagerHooks;
    //   32: aload_1
    //   33: iload_2
    //   34: invokeinterface getTintListForDrawableRes : (Landroid/content/Context;I)Landroid/content/res/ColorStateList;
    //   39: astore_3
    //   40: aload_3
    //   41: astore #4
    //   43: aload_3
    //   44: ifnull -> 57
    //   47: aload_0
    //   48: aload_1
    //   49: iload_2
    //   50: aload_3
    //   51: invokespecial addTintListToCache : (Landroid/content/Context;ILandroid/content/res/ColorStateList;)V
    //   54: aload_3
    //   55: astore #4
    //   57: aload_0
    //   58: monitorexit
    //   59: aload #4
    //   61: areturn
    //   62: astore_1
    //   63: aload_0
    //   64: monitorexit
    //   65: aload_1
    //   66: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	62	finally
    //   16	23	62	finally
    //   28	40	62	finally
    //   47	54	62	finally
  }
  
  PorterDuff.Mode getTintMode(int paramInt) {
    PorterDuff.Mode mode;
    ResourceManagerHooks resourceManagerHooks = this.mHooks;
    if (resourceManagerHooks == null) {
      resourceManagerHooks = null;
    } else {
      mode = resourceManagerHooks.getTintModeForDrawableRes(paramInt);
    } 
    return mode;
  }
  
  public void onConfigurationChanged(Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mDrawableCaches : Ljava/util/WeakHashMap;
    //   6: aload_1
    //   7: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: checkcast androidx/collection/LongSparseArray
    //   13: astore_1
    //   14: aload_1
    //   15: ifnull -> 22
    //   18: aload_1
    //   19: invokevirtual clear : ()V
    //   22: aload_0
    //   23: monitorexit
    //   24: return
    //   25: astore_1
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_1
    //   29: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	25	finally
    //   18	22	25	finally
  }
  
  Drawable onDrawableLoadedFromResources(Context paramContext, VectorEnabledTintResources paramVectorEnabledTintResources, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: iload_3
    //   5: invokespecial loadDrawableFromDelegates : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   8: astore #5
    //   10: aload #5
    //   12: astore #4
    //   14: aload #5
    //   16: ifnonnull -> 26
    //   19: aload_2
    //   20: iload_3
    //   21: invokevirtual superGetDrawable : (I)Landroid/graphics/drawable/Drawable;
    //   24: astore #4
    //   26: aload #4
    //   28: ifnull -> 45
    //   31: aload_0
    //   32: aload_1
    //   33: iload_3
    //   34: iconst_0
    //   35: aload #4
    //   37: invokespecial tintDrawable : (Landroid/content/Context;IZLandroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/Drawable;
    //   40: astore_1
    //   41: aload_0
    //   42: monitorexit
    //   43: aload_1
    //   44: areturn
    //   45: aload_0
    //   46: monitorexit
    //   47: aconst_null
    //   48: areturn
    //   49: astore_1
    //   50: aload_0
    //   51: monitorexit
    //   52: aload_1
    //   53: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	49	finally
    //   19	26	49	finally
    //   31	41	49	finally
  }
  
  public void setHooks(ResourceManagerHooks paramResourceManagerHooks) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield mHooks : Landroidx/appcompat/widget/ResourceManagerInternal$ResourceManagerHooks;
    //   7: aload_0
    //   8: monitorexit
    //   9: return
    //   10: astore_1
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_1
    //   14: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	10	finally
  }
  
  boolean tintDrawableUsingColorFilter(Context paramContext, int paramInt, Drawable paramDrawable) {
    boolean bool;
    ResourceManagerHooks resourceManagerHooks = this.mHooks;
    if (resourceManagerHooks != null && resourceManagerHooks.tintDrawableUsingColorFilter(paramContext, paramInt, paramDrawable)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static class AsldcInflateDelegate implements InflateDelegate {
    public Drawable createFromXmlInner(Context param1Context, XmlPullParser param1XmlPullParser, AttributeSet param1AttributeSet, Resources.Theme param1Theme) {
      try {
        return (Drawable)AnimatedStateListDrawableCompat.createFromXmlInner(param1Context, param1Context.getResources(), param1XmlPullParser, param1AttributeSet, param1Theme);
      } catch (Exception exception) {
        Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", exception);
        return null;
      } 
    }
  }
  
  private static class AvdcInflateDelegate implements InflateDelegate {
    public Drawable createFromXmlInner(Context param1Context, XmlPullParser param1XmlPullParser, AttributeSet param1AttributeSet, Resources.Theme param1Theme) {
      try {
        return (Drawable)AnimatedVectorDrawableCompat.createFromXmlInner(param1Context, param1Context.getResources(), param1XmlPullParser, param1AttributeSet, param1Theme);
      } catch (Exception exception) {
        Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", exception);
        return null;
      } 
    }
  }
  
  private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
    public ColorFilterLruCache(int param1Int) {
      super(param1Int);
    }
    
    private static int generateCacheKey(int param1Int, PorterDuff.Mode param1Mode) {
      return (param1Int + 31) * 31 + param1Mode.hashCode();
    }
    
    PorterDuffColorFilter get(int param1Int, PorterDuff.Mode param1Mode) {
      return (PorterDuffColorFilter)get(Integer.valueOf(generateCacheKey(param1Int, param1Mode)));
    }
    
    PorterDuffColorFilter put(int param1Int, PorterDuff.Mode param1Mode, PorterDuffColorFilter param1PorterDuffColorFilter) {
      return (PorterDuffColorFilter)put(Integer.valueOf(generateCacheKey(param1Int, param1Mode)), param1PorterDuffColorFilter);
    }
  }
  
  private static interface InflateDelegate {
    Drawable createFromXmlInner(Context param1Context, XmlPullParser param1XmlPullParser, AttributeSet param1AttributeSet, Resources.Theme param1Theme);
  }
  
  static interface ResourceManagerHooks {
    Drawable createDrawableFor(ResourceManagerInternal param1ResourceManagerInternal, Context param1Context, int param1Int);
    
    ColorStateList getTintListForDrawableRes(Context param1Context, int param1Int);
    
    PorterDuff.Mode getTintModeForDrawableRes(int param1Int);
    
    boolean tintDrawable(Context param1Context, int param1Int, Drawable param1Drawable);
    
    boolean tintDrawableUsingColorFilter(Context param1Context, int param1Int, Drawable param1Drawable);
  }
  
  private static class VdcInflateDelegate implements InflateDelegate {
    public Drawable createFromXmlInner(Context param1Context, XmlPullParser param1XmlPullParser, AttributeSet param1AttributeSet, Resources.Theme param1Theme) {
      try {
        return (Drawable)VectorDrawableCompat.createFromXmlInner(param1Context.getResources(), param1XmlPullParser, param1AttributeSet, param1Theme);
      } catch (Exception exception) {
        Log.e("VdcInflateDelegate", "Exception while inflating <vector>", exception);
        return null;
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\ResourceManagerInternal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */