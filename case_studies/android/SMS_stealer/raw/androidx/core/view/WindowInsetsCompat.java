package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;

public class WindowInsetsCompat {
  public static final WindowInsetsCompat CONSUMED = (new Builder()).build().consumeDisplayCutout().consumeStableInsets().consumeSystemWindowInsets();
  
  private static final String TAG = "WindowInsetsCompat";
  
  private final Impl mImpl;
  
  private WindowInsetsCompat(WindowInsets paramWindowInsets) {
    if (Build.VERSION.SDK_INT >= 29) {
      this.mImpl = new Impl29(this, paramWindowInsets);
    } else if (Build.VERSION.SDK_INT >= 28) {
      this.mImpl = new Impl28(this, paramWindowInsets);
    } else if (Build.VERSION.SDK_INT >= 21) {
      this.mImpl = new Impl21(this, paramWindowInsets);
    } else if (Build.VERSION.SDK_INT >= 20) {
      this.mImpl = new Impl20(this, paramWindowInsets);
    } else {
      this.mImpl = new Impl(this);
    } 
  }
  
  public WindowInsetsCompat(WindowInsetsCompat paramWindowInsetsCompat) {
    if (paramWindowInsetsCompat != null) {
      Impl impl = paramWindowInsetsCompat.mImpl;
      if (Build.VERSION.SDK_INT >= 29 && impl instanceof Impl29) {
        this.mImpl = new Impl29(this, (Impl29)impl);
      } else if (Build.VERSION.SDK_INT >= 28 && impl instanceof Impl28) {
        this.mImpl = new Impl28(this, (Impl28)impl);
      } else if (Build.VERSION.SDK_INT >= 21 && impl instanceof Impl21) {
        this.mImpl = new Impl21(this, (Impl21)impl);
      } else if (Build.VERSION.SDK_INT >= 20 && impl instanceof Impl20) {
        this.mImpl = new Impl20(this, (Impl20)impl);
      } else {
        this.mImpl = new Impl(this);
      } 
    } else {
      this.mImpl = new Impl(this);
    } 
  }
  
  static Insets insetInsets(Insets paramInsets, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = Math.max(0, paramInsets.left - paramInt1);
    int j = Math.max(0, paramInsets.top - paramInt2);
    int k = Math.max(0, paramInsets.right - paramInt3);
    int m = Math.max(0, paramInsets.bottom - paramInt4);
    return (i == paramInt1 && j == paramInt2 && k == paramInt3 && m == paramInt4) ? paramInsets : Insets.of(i, j, k, m);
  }
  
  public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets paramWindowInsets) {
    return new WindowInsetsCompat((WindowInsets)Preconditions.checkNotNull(paramWindowInsets));
  }
  
  public WindowInsetsCompat consumeDisplayCutout() {
    return this.mImpl.consumeDisplayCutout();
  }
  
  public WindowInsetsCompat consumeStableInsets() {
    return this.mImpl.consumeStableInsets();
  }
  
  public WindowInsetsCompat consumeSystemWindowInsets() {
    return this.mImpl.consumeSystemWindowInsets();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof WindowInsetsCompat))
      return false; 
    paramObject = paramObject;
    return ObjectsCompat.equals(this.mImpl, ((WindowInsetsCompat)paramObject).mImpl);
  }
  
  public DisplayCutoutCompat getDisplayCutout() {
    return this.mImpl.getDisplayCutout();
  }
  
  public Insets getMandatorySystemGestureInsets() {
    return this.mImpl.getMandatorySystemGestureInsets();
  }
  
  public int getStableInsetBottom() {
    return (getStableInsets()).bottom;
  }
  
  public int getStableInsetLeft() {
    return (getStableInsets()).left;
  }
  
  public int getStableInsetRight() {
    return (getStableInsets()).right;
  }
  
  public int getStableInsetTop() {
    return (getStableInsets()).top;
  }
  
  public Insets getStableInsets() {
    return this.mImpl.getStableInsets();
  }
  
  public Insets getSystemGestureInsets() {
    return this.mImpl.getSystemGestureInsets();
  }
  
  public int getSystemWindowInsetBottom() {
    return (getSystemWindowInsets()).bottom;
  }
  
  public int getSystemWindowInsetLeft() {
    return (getSystemWindowInsets()).left;
  }
  
  public int getSystemWindowInsetRight() {
    return (getSystemWindowInsets()).right;
  }
  
  public int getSystemWindowInsetTop() {
    return (getSystemWindowInsets()).top;
  }
  
  public Insets getSystemWindowInsets() {
    return this.mImpl.getSystemWindowInsets();
  }
  
  public Insets getTappableElementInsets() {
    return this.mImpl.getTappableElementInsets();
  }
  
  public boolean hasInsets() {
    return (hasSystemWindowInsets() || hasStableInsets() || getDisplayCutout() != null || !getSystemGestureInsets().equals(Insets.NONE) || !getMandatorySystemGestureInsets().equals(Insets.NONE) || !getTappableElementInsets().equals(Insets.NONE));
  }
  
  public boolean hasStableInsets() {
    return getStableInsets().equals(Insets.NONE) ^ true;
  }
  
  public boolean hasSystemWindowInsets() {
    return getSystemWindowInsets().equals(Insets.NONE) ^ true;
  }
  
  public int hashCode() {
    int i;
    Impl impl = this.mImpl;
    if (impl == null) {
      i = 0;
    } else {
      i = impl.hashCode();
    } 
    return i;
  }
  
  public WindowInsetsCompat inset(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return this.mImpl.inset(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public WindowInsetsCompat inset(Insets paramInsets) {
    return inset(paramInsets.left, paramInsets.top, paramInsets.right, paramInsets.bottom);
  }
  
  public boolean isConsumed() {
    return this.mImpl.isConsumed();
  }
  
  public boolean isRound() {
    return this.mImpl.isRound();
  }
  
  @Deprecated
  public WindowInsetsCompat replaceSystemWindowInsets(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return (new Builder(this)).setSystemWindowInsets(Insets.of(paramInt1, paramInt2, paramInt3, paramInt4)).build();
  }
  
  @Deprecated
  public WindowInsetsCompat replaceSystemWindowInsets(Rect paramRect) {
    return (new Builder(this)).setSystemWindowInsets(Insets.of(paramRect)).build();
  }
  
  public WindowInsets toWindowInsets() {
    Impl impl = this.mImpl;
    if (impl instanceof Impl20) {
      WindowInsets windowInsets = ((Impl20)impl).mPlatformInsets;
    } else {
      impl = null;
    } 
    return (WindowInsets)impl;
  }
  
  public static final class Builder {
    private final WindowInsetsCompat.BuilderImpl mImpl;
    
    public Builder() {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl29();
      } else if (Build.VERSION.SDK_INT >= 20) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl20();
      } else {
        this.mImpl = new WindowInsetsCompat.BuilderImpl();
      } 
    }
    
    public Builder(WindowInsetsCompat param1WindowInsetsCompat) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl29(param1WindowInsetsCompat);
      } else if (Build.VERSION.SDK_INT >= 20) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl20(param1WindowInsetsCompat);
      } else {
        this.mImpl = new WindowInsetsCompat.BuilderImpl(param1WindowInsetsCompat);
      } 
    }
    
    public WindowInsetsCompat build() {
      return this.mImpl.build();
    }
    
    public Builder setDisplayCutout(DisplayCutoutCompat param1DisplayCutoutCompat) {
      this.mImpl.setDisplayCutout(param1DisplayCutoutCompat);
      return this;
    }
    
    public Builder setMandatorySystemGestureInsets(Insets param1Insets) {
      this.mImpl.setMandatorySystemGestureInsets(param1Insets);
      return this;
    }
    
    public Builder setStableInsets(Insets param1Insets) {
      this.mImpl.setStableInsets(param1Insets);
      return this;
    }
    
    public Builder setSystemGestureInsets(Insets param1Insets) {
      this.mImpl.setSystemGestureInsets(param1Insets);
      return this;
    }
    
    public Builder setSystemWindowInsets(Insets param1Insets) {
      this.mImpl.setSystemWindowInsets(param1Insets);
      return this;
    }
    
    public Builder setTappableElementInsets(Insets param1Insets) {
      this.mImpl.setTappableElementInsets(param1Insets);
      return this;
    }
  }
  
  private static class BuilderImpl {
    private final WindowInsetsCompat mInsets;
    
    BuilderImpl() {
      this(new WindowInsetsCompat((WindowInsetsCompat)null));
    }
    
    BuilderImpl(WindowInsetsCompat param1WindowInsetsCompat) {
      this.mInsets = param1WindowInsetsCompat;
    }
    
    WindowInsetsCompat build() {
      return this.mInsets;
    }
    
    void setDisplayCutout(DisplayCutoutCompat param1DisplayCutoutCompat) {}
    
    void setMandatorySystemGestureInsets(Insets param1Insets) {}
    
    void setStableInsets(Insets param1Insets) {}
    
    void setSystemGestureInsets(Insets param1Insets) {}
    
    void setSystemWindowInsets(Insets param1Insets) {}
    
    void setTappableElementInsets(Insets param1Insets) {}
  }
  
  private static class BuilderImpl20 extends BuilderImpl {
    private static Constructor<WindowInsets> sConstructor;
    
    private static boolean sConstructorFetched;
    
    private static Field sConsumedField;
    
    private static boolean sConsumedFieldFetched;
    
    private WindowInsets mInsets = createWindowInsetsInstance();
    
    BuilderImpl20() {}
    
    BuilderImpl20(WindowInsetsCompat param1WindowInsetsCompat) {}
    
    private static WindowInsets createWindowInsetsInstance() {
      if (!sConsumedFieldFetched) {
        try {
          sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
        } catch (ReflectiveOperationException reflectiveOperationException) {
          Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets.CONSUMED field", reflectiveOperationException);
        } 
        sConsumedFieldFetched = true;
      } 
      Field field = sConsumedField;
      if (field != null)
        try {
          WindowInsets windowInsets = (WindowInsets)field.get(null);
          if (windowInsets != null)
            return new WindowInsets(windowInsets); 
        } catch (ReflectiveOperationException reflectiveOperationException) {
          Log.i("WindowInsetsCompat", "Could not get value from WindowInsets.CONSUMED field", reflectiveOperationException);
        }  
      if (!sConstructorFetched) {
        try {
          sConstructor = WindowInsets.class.getConstructor(new Class[] { Rect.class });
        } catch (ReflectiveOperationException reflectiveOperationException) {
          Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets(Rect) constructor", reflectiveOperationException);
        } 
        sConstructorFetched = true;
      } 
      Constructor<WindowInsets> constructor = sConstructor;
      if (constructor != null)
        try {
          Rect rect = new Rect();
          this();
          return constructor.newInstance(new Object[] { rect });
        } catch (ReflectiveOperationException reflectiveOperationException) {
          Log.i("WindowInsetsCompat", "Could not invoke WindowInsets(Rect) constructor", reflectiveOperationException);
        }  
      return null;
    }
    
    WindowInsetsCompat build() {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mInsets);
    }
    
    void setSystemWindowInsets(Insets param1Insets) {
      WindowInsets windowInsets = this.mInsets;
      if (windowInsets != null)
        this.mInsets = windowInsets.replaceSystemWindowInsets(param1Insets.left, param1Insets.top, param1Insets.right, param1Insets.bottom); 
    }
  }
  
  private static class BuilderImpl29 extends BuilderImpl {
    final WindowInsets.Builder mPlatBuilder;
    
    BuilderImpl29() {
      this.mPlatBuilder = new WindowInsets.Builder();
    }
    
    BuilderImpl29(WindowInsetsCompat param1WindowInsetsCompat) {
      WindowInsets.Builder builder;
      WindowInsets windowInsets = param1WindowInsetsCompat.toWindowInsets();
      if (windowInsets != null) {
        builder = new WindowInsets.Builder(windowInsets);
      } else {
        builder = new WindowInsets.Builder();
      } 
      this.mPlatBuilder = builder;
    }
    
    WindowInsetsCompat build() {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build());
    }
    
    void setDisplayCutout(DisplayCutoutCompat param1DisplayCutoutCompat) {
      WindowInsets.Builder builder = this.mPlatBuilder;
      if (param1DisplayCutoutCompat != null) {
        DisplayCutout displayCutout = param1DisplayCutoutCompat.unwrap();
      } else {
        param1DisplayCutoutCompat = null;
      } 
      builder.setDisplayCutout((DisplayCutout)param1DisplayCutoutCompat);
    }
    
    void setMandatorySystemGestureInsets(Insets param1Insets) {
      this.mPlatBuilder.setMandatorySystemGestureInsets(param1Insets.toPlatformInsets());
    }
    
    void setStableInsets(Insets param1Insets) {
      this.mPlatBuilder.setStableInsets(param1Insets.toPlatformInsets());
    }
    
    void setSystemGestureInsets(Insets param1Insets) {
      this.mPlatBuilder.setSystemGestureInsets(param1Insets.toPlatformInsets());
    }
    
    void setSystemWindowInsets(Insets param1Insets) {
      this.mPlatBuilder.setSystemWindowInsets(param1Insets.toPlatformInsets());
    }
    
    void setTappableElementInsets(Insets param1Insets) {
      this.mPlatBuilder.setTappableElementInsets(param1Insets.toPlatformInsets());
    }
  }
  
  private static class Impl {
    final WindowInsetsCompat mHost;
    
    Impl(WindowInsetsCompat param1WindowInsetsCompat) {
      this.mHost = param1WindowInsetsCompat;
    }
    
    WindowInsetsCompat consumeDisplayCutout() {
      return this.mHost;
    }
    
    WindowInsetsCompat consumeStableInsets() {
      return this.mHost;
    }
    
    WindowInsetsCompat consumeSystemWindowInsets() {
      return this.mHost;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (!(param1Object instanceof Impl))
        return false; 
      param1Object = param1Object;
      if (isRound() != param1Object.isRound() || isConsumed() != param1Object.isConsumed() || !ObjectsCompat.equals(getSystemWindowInsets(), param1Object.getSystemWindowInsets()) || !ObjectsCompat.equals(getStableInsets(), param1Object.getStableInsets()) || !ObjectsCompat.equals(getDisplayCutout(), param1Object.getDisplayCutout()))
        bool = false; 
      return bool;
    }
    
    DisplayCutoutCompat getDisplayCutout() {
      return null;
    }
    
    Insets getMandatorySystemGestureInsets() {
      return getSystemWindowInsets();
    }
    
    Insets getStableInsets() {
      return Insets.NONE;
    }
    
    Insets getSystemGestureInsets() {
      return getSystemWindowInsets();
    }
    
    Insets getSystemWindowInsets() {
      return Insets.NONE;
    }
    
    Insets getTappableElementInsets() {
      return getSystemWindowInsets();
    }
    
    public int hashCode() {
      return ObjectsCompat.hash(new Object[] { Boolean.valueOf(isRound()), Boolean.valueOf(isConsumed()), getSystemWindowInsets(), getStableInsets(), getDisplayCutout() });
    }
    
    WindowInsetsCompat inset(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      return WindowInsetsCompat.CONSUMED;
    }
    
    boolean isConsumed() {
      return false;
    }
    
    boolean isRound() {
      return false;
    }
  }
  
  private static class Impl20 extends Impl {
    final WindowInsets mPlatformInsets;
    
    private Insets mSystemWindowInsets = null;
    
    Impl20(WindowInsetsCompat param1WindowInsetsCompat, WindowInsets param1WindowInsets) {
      super(param1WindowInsetsCompat);
      this.mPlatformInsets = param1WindowInsets;
    }
    
    Impl20(WindowInsetsCompat param1WindowInsetsCompat, Impl20 param1Impl20) {
      this(param1WindowInsetsCompat, new WindowInsets(param1Impl20.mPlatformInsets));
    }
    
    final Insets getSystemWindowInsets() {
      if (this.mSystemWindowInsets == null)
        this.mSystemWindowInsets = Insets.of(this.mPlatformInsets.getSystemWindowInsetLeft(), this.mPlatformInsets.getSystemWindowInsetTop(), this.mPlatformInsets.getSystemWindowInsetRight(), this.mPlatformInsets.getSystemWindowInsetBottom()); 
      return this.mSystemWindowInsets;
    }
    
    WindowInsetsCompat inset(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets));
      builder.setSystemWindowInsets(WindowInsetsCompat.insetInsets(getSystemWindowInsets(), param1Int1, param1Int2, param1Int3, param1Int4));
      builder.setStableInsets(WindowInsetsCompat.insetInsets(getStableInsets(), param1Int1, param1Int2, param1Int3, param1Int4));
      return builder.build();
    }
    
    boolean isRound() {
      return this.mPlatformInsets.isRound();
    }
  }
  
  private static class Impl21 extends Impl20 {
    private Insets mStableInsets = null;
    
    Impl21(WindowInsetsCompat param1WindowInsetsCompat, WindowInsets param1WindowInsets) {
      super(param1WindowInsetsCompat, param1WindowInsets);
    }
    
    Impl21(WindowInsetsCompat param1WindowInsetsCompat, Impl21 param1Impl21) {
      super(param1WindowInsetsCompat, param1Impl21);
    }
    
    WindowInsetsCompat consumeStableInsets() {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeStableInsets());
    }
    
    WindowInsetsCompat consumeSystemWindowInsets() {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeSystemWindowInsets());
    }
    
    final Insets getStableInsets() {
      if (this.mStableInsets == null)
        this.mStableInsets = Insets.of(this.mPlatformInsets.getStableInsetLeft(), this.mPlatformInsets.getStableInsetTop(), this.mPlatformInsets.getStableInsetRight(), this.mPlatformInsets.getStableInsetBottom()); 
      return this.mStableInsets;
    }
    
    boolean isConsumed() {
      return this.mPlatformInsets.isConsumed();
    }
  }
  
  private static class Impl28 extends Impl21 {
    Impl28(WindowInsetsCompat param1WindowInsetsCompat, WindowInsets param1WindowInsets) {
      super(param1WindowInsetsCompat, param1WindowInsets);
    }
    
    Impl28(WindowInsetsCompat param1WindowInsetsCompat, Impl28 param1Impl28) {
      super(param1WindowInsetsCompat, param1Impl28);
    }
    
    WindowInsetsCompat consumeDisplayCutout() {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeDisplayCutout());
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (!(param1Object instanceof Impl28))
        return false; 
      param1Object = param1Object;
      return Objects.equals(this.mPlatformInsets, ((Impl28)param1Object).mPlatformInsets);
    }
    
    DisplayCutoutCompat getDisplayCutout() {
      return DisplayCutoutCompat.wrap(this.mPlatformInsets.getDisplayCutout());
    }
    
    public int hashCode() {
      return this.mPlatformInsets.hashCode();
    }
  }
  
  private static class Impl29 extends Impl28 {
    private Insets mMandatorySystemGestureInsets = null;
    
    private Insets mSystemGestureInsets = null;
    
    private Insets mTappableElementInsets = null;
    
    Impl29(WindowInsetsCompat param1WindowInsetsCompat, WindowInsets param1WindowInsets) {
      super(param1WindowInsetsCompat, param1WindowInsets);
    }
    
    Impl29(WindowInsetsCompat param1WindowInsetsCompat, Impl29 param1Impl29) {
      super(param1WindowInsetsCompat, param1Impl29);
    }
    
    Insets getMandatorySystemGestureInsets() {
      if (this.mMandatorySystemGestureInsets == null)
        this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getMandatorySystemGestureInsets()); 
      return this.mMandatorySystemGestureInsets;
    }
    
    Insets getSystemGestureInsets() {
      if (this.mSystemGestureInsets == null)
        this.mSystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getSystemGestureInsets()); 
      return this.mSystemGestureInsets;
    }
    
    Insets getTappableElementInsets() {
      if (this.mTappableElementInsets == null)
        this.mTappableElementInsets = Insets.toCompatInsets(this.mPlatformInsets.getTappableElementInsets()); 
      return this.mTappableElementInsets;
    }
    
    WindowInsetsCompat inset(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.inset(param1Int1, param1Int2, param1Int3, param1Int4));
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\WindowInsetsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */