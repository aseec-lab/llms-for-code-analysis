package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
  static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
  
  private static final boolean IS_PRE_LOLLIPOP;
  
  private static final boolean sCanApplyOverrideConfiguration;
  
  private static final boolean sCanReturnDifferentContext;
  
  private static boolean sInstalledExceptionHandler;
  
  private static final SimpleArrayMap<String, Integer> sLocalNightModes = new SimpleArrayMap();
  
  private static final int[] sWindowBackgroundStyleable;
  
  ActionBar mActionBar;
  
  private ActionMenuPresenterCallback mActionMenuPresenterCallback;
  
  ActionMode mActionMode;
  
  PopupWindow mActionModePopup;
  
  ActionBarContextView mActionModeView;
  
  private boolean mActivityHandlesUiMode;
  
  private boolean mActivityHandlesUiModeChecked;
  
  final AppCompatCallback mAppCompatCallback;
  
  private AppCompatViewInflater mAppCompatViewInflater;
  
  private AppCompatWindowCallback mAppCompatWindowCallback;
  
  private AutoNightModeManager mAutoBatteryNightModeManager;
  
  private AutoNightModeManager mAutoTimeNightModeManager;
  
  private boolean mBaseContextAttached;
  
  private boolean mClosingActionMenu;
  
  final Context mContext;
  
  private boolean mCreated;
  
  private DecorContentParent mDecorContentParent;
  
  private boolean mEnableDefaultActionBarUp;
  
  ViewPropertyAnimatorCompat mFadeAnim = null;
  
  private boolean mFeatureIndeterminateProgress;
  
  private boolean mFeatureProgress;
  
  private boolean mHandleNativeActionModes = true;
  
  boolean mHasActionBar;
  
  final Object mHost;
  
  int mInvalidatePanelMenuFeatures;
  
  boolean mInvalidatePanelMenuPosted;
  
  private final Runnable mInvalidatePanelMenuRunnable = new Runnable() {
      final AppCompatDelegateImpl this$0;
      
      public void run() {
        if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 0x1) != 0)
          AppCompatDelegateImpl.this.doInvalidatePanelMenu(0); 
        if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 0x1000) != 0)
          AppCompatDelegateImpl.this.doInvalidatePanelMenu(108); 
        AppCompatDelegateImpl.this.mInvalidatePanelMenuPosted = false;
        AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures = 0;
      }
    };
  
  boolean mIsDestroyed;
  
  boolean mIsFloating;
  
  private int mLocalNightMode = -100;
  
  private boolean mLongPressBackDown;
  
  MenuInflater mMenuInflater;
  
  boolean mOverlayActionBar;
  
  boolean mOverlayActionMode;
  
  private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
  
  private PanelFeatureState[] mPanels;
  
  private PanelFeatureState mPreparedPanel;
  
  Runnable mShowActionModePopup;
  
  private boolean mStarted;
  
  private View mStatusGuard;
  
  ViewGroup mSubDecor;
  
  private boolean mSubDecorInstalled;
  
  private Rect mTempRect1;
  
  private Rect mTempRect2;
  
  private int mThemeResId;
  
  private CharSequence mTitle;
  
  private TextView mTitleView;
  
  Window mWindow;
  
  boolean mWindowNoTitle;
  
  static {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i < 21) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    IS_PRE_LOLLIPOP = bool1;
    sWindowBackgroundStyleable = new int[] { 16842836 };
    sCanReturnDifferentContext = "robolectric".equals(Build.FINGERPRINT) ^ true;
    boolean bool1 = bool2;
    if (Build.VERSION.SDK_INT >= 17)
      bool1 = true; 
    sCanApplyOverrideConfiguration = bool1;
    if (IS_PRE_LOLLIPOP && !sInstalledExceptionHandler) {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()) {
            final Thread.UncaughtExceptionHandler val$defHandler;
            
            private boolean shouldWrapException(Throwable param1Throwable) {
              boolean bool1 = param1Throwable instanceof Resources.NotFoundException;
              boolean bool = false;
              null = bool;
              if (bool1) {
                String str = param1Throwable.getMessage();
                null = bool;
                if (str != null) {
                  if (!str.contains("drawable")) {
                    null = bool;
                    return str.contains("Drawable") ? true : null;
                  } 
                } else {
                  return null;
                } 
              } else {
                return null;
              } 
              return true;
            }
            
            public void uncaughtException(Thread param1Thread, Throwable param1Throwable) {
              if (shouldWrapException(param1Throwable)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(param1Throwable.getMessage());
                stringBuilder.append(". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
                Resources.NotFoundException notFoundException = new Resources.NotFoundException(stringBuilder.toString());
                notFoundException.initCause(param1Throwable.getCause());
                notFoundException.setStackTrace(param1Throwable.getStackTrace());
                defHandler.uncaughtException(param1Thread, (Throwable)notFoundException);
              } else {
                defHandler.uncaughtException(param1Thread, param1Throwable);
              } 
            }
          });
      sInstalledExceptionHandler = true;
    } 
  }
  
  AppCompatDelegateImpl(Activity paramActivity, AppCompatCallback paramAppCompatCallback) {
    this((Context)paramActivity, (Window)null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Dialog paramDialog, AppCompatCallback paramAppCompatCallback) {
    this(paramDialog.getContext(), paramDialog.getWindow(), paramAppCompatCallback, paramDialog);
  }
  
  AppCompatDelegateImpl(Context paramContext, Activity paramActivity, AppCompatCallback paramAppCompatCallback) {
    this(paramContext, (Window)null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback) {
    this(paramContext, paramWindow, paramAppCompatCallback, paramContext);
  }
  
  private AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback, Object paramObject) {
    this.mContext = paramContext;
    this.mAppCompatCallback = paramAppCompatCallback;
    this.mHost = paramObject;
    if (this.mLocalNightMode == -100 && paramObject instanceof Dialog) {
      AppCompatActivity appCompatActivity = tryUnwrapContext();
      if (appCompatActivity != null)
        this.mLocalNightMode = appCompatActivity.getDelegate().getLocalNightMode(); 
    } 
    if (this.mLocalNightMode == -100) {
      Integer integer = (Integer)sLocalNightModes.get(this.mHost.getClass().getName());
      if (integer != null) {
        this.mLocalNightMode = integer.intValue();
        sLocalNightModes.remove(this.mHost.getClass().getName());
      } 
    } 
    if (paramWindow != null)
      attachToWindow(paramWindow); 
    AppCompatDrawableManager.preload();
  }
  
  private boolean applyDayNight(boolean paramBoolean) {
    if (this.mIsDestroyed)
      return false; 
    int i = calculateNightMode();
    paramBoolean = updateForNightMode(mapNightMode(this.mContext, i), paramBoolean);
    if (i == 0) {
      getAutoTimeNightModeManager(this.mContext).setup();
    } else {
      AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
      if (autoNightModeManager != null)
        autoNightModeManager.cleanup(); 
    } 
    if (i == 3) {
      getAutoBatteryNightModeManager(this.mContext).setup();
    } else {
      AutoNightModeManager autoNightModeManager = this.mAutoBatteryNightModeManager;
      if (autoNightModeManager != null)
        autoNightModeManager.cleanup(); 
    } 
    return paramBoolean;
  }
  
  private void applyFixedSizeWindow() {
    ContentFrameLayout contentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(16908290);
    View view = this.mWindow.getDecorView();
    contentFrameLayout.setDecorPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    TypedArray typedArray = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    typedArray.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
    typedArray.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor()); 
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor()); 
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor()); 
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor()); 
    typedArray.recycle();
    contentFrameLayout.requestLayout();
  }
  
  private void attachToWindow(Window paramWindow) {
    if (this.mWindow == null) {
      Window.Callback callback = paramWindow.getCallback();
      if (!(callback instanceof AppCompatWindowCallback)) {
        AppCompatWindowCallback appCompatWindowCallback = new AppCompatWindowCallback(callback);
        this.mAppCompatWindowCallback = appCompatWindowCallback;
        paramWindow.setCallback((Window.Callback)appCompatWindowCallback);
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mContext, null, sWindowBackgroundStyleable);
        Drawable drawable = tintTypedArray.getDrawableIfKnown(0);
        if (drawable != null)
          paramWindow.setBackgroundDrawable(drawable); 
        tintTypedArray.recycle();
        this.mWindow = paramWindow;
        return;
      } 
      throw new IllegalStateException("AppCompat has already installed itself into the Window");
    } 
    throw new IllegalStateException("AppCompat has already installed itself into the Window");
  }
  
  private int calculateNightMode() {
    int i = this.mLocalNightMode;
    if (i == -100)
      i = getDefaultNightMode(); 
    return i;
  }
  
  private void cleanupAutoManagers() {
    AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
    if (autoNightModeManager != null)
      autoNightModeManager.cleanup(); 
    autoNightModeManager = this.mAutoBatteryNightModeManager;
    if (autoNightModeManager != null)
      autoNightModeManager.cleanup(); 
  }
  
  private Configuration createOverrideConfigurationForDayNight(Context paramContext, int paramInt, Configuration paramConfiguration) {
    if (paramInt != 1) {
      if (paramInt != 2) {
        paramInt = (paramContext.getApplicationContext().getResources().getConfiguration()).uiMode & 0x30;
      } else {
        paramInt = 32;
      } 
    } else {
      paramInt = 16;
    } 
    Configuration configuration = new Configuration();
    configuration.fontScale = 0.0F;
    if (paramConfiguration != null)
      configuration.setTo(paramConfiguration); 
    configuration.uiMode = paramInt | configuration.uiMode & 0xFFFFFFCF;
    return configuration;
  }
  
  private ViewGroup createSubDecor() {
    StringBuilder stringBuilder;
    TypedArray typedArray = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
      ViewGroup viewGroup;
      if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
        requestWindowFeature(1);
      } else if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
        requestWindowFeature(108);
      } 
      if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false))
        requestWindowFeature(109); 
      if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false))
        requestWindowFeature(10); 
      this.mIsFloating = typedArray.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
      typedArray.recycle();
      ensureWindow();
      this.mWindow.getDecorView();
      LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
      if (!this.mWindowNoTitle) {
        if (this.mIsFloating) {
          viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.abc_dialog_title_material, null);
          this.mOverlayActionBar = false;
          this.mHasActionBar = false;
        } else if (this.mHasActionBar) {
          Context context;
          TypedValue typedValue = new TypedValue();
          this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, typedValue, true);
          if (typedValue.resourceId != 0) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, typedValue.resourceId);
          } else {
            context = this.mContext;
          } 
          ViewGroup viewGroup1 = (ViewGroup)LayoutInflater.from(context).inflate(R.layout.abc_screen_toolbar, null);
          DecorContentParent decorContentParent = (DecorContentParent)viewGroup1.findViewById(R.id.decor_content_parent);
          this.mDecorContentParent = decorContentParent;
          decorContentParent.setWindowCallback(getWindowCallback());
          if (this.mOverlayActionBar)
            this.mDecorContentParent.initFeature(109); 
          if (this.mFeatureProgress)
            this.mDecorContentParent.initFeature(2); 
          viewGroup = viewGroup1;
          if (this.mFeatureIndeterminateProgress) {
            this.mDecorContentParent.initFeature(5);
            viewGroup = viewGroup1;
          } 
        } else {
          layoutInflater = null;
        } 
      } else if (this.mOverlayActionMode) {
        viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.abc_screen_simple_overlay_action_mode, null);
      } else {
        viewGroup = (ViewGroup)viewGroup.inflate(R.layout.abc_screen_simple, null);
      } 
      if (viewGroup != null) {
        if (Build.VERSION.SDK_INT >= 21) {
          ViewCompat.setOnApplyWindowInsetsListener((View)viewGroup, new OnApplyWindowInsetsListener() {
                final AppCompatDelegateImpl this$0;
                
                public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
                  int j = param1WindowInsetsCompat.getSystemWindowInsetTop();
                  int i = AppCompatDelegateImpl.this.updateStatusGuard(param1WindowInsetsCompat, (Rect)null);
                  WindowInsetsCompat windowInsetsCompat = param1WindowInsetsCompat;
                  if (j != i)
                    windowInsetsCompat = param1WindowInsetsCompat.replaceSystemWindowInsets(param1WindowInsetsCompat.getSystemWindowInsetLeft(), i, param1WindowInsetsCompat.getSystemWindowInsetRight(), param1WindowInsetsCompat.getSystemWindowInsetBottom()); 
                  return ViewCompat.onApplyWindowInsets(param1View, windowInsetsCompat);
                }
              });
        } else if (viewGroup instanceof FitWindowsViewGroup) {
          ((FitWindowsViewGroup)viewGroup).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener() {
                final AppCompatDelegateImpl this$0;
                
                public void onFitSystemWindows(Rect param1Rect) {
                  param1Rect.top = AppCompatDelegateImpl.this.updateStatusGuard((WindowInsetsCompat)null, param1Rect);
                }
              });
        } 
        if (this.mDecorContentParent == null)
          this.mTitleView = (TextView)viewGroup.findViewById(R.id.title); 
        ViewUtils.makeOptionalFitsSystemWindows((View)viewGroup);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)viewGroup.findViewById(R.id.action_bar_activity_content);
        ViewGroup viewGroup1 = (ViewGroup)this.mWindow.findViewById(16908290);
        if (viewGroup1 != null) {
          while (viewGroup1.getChildCount() > 0) {
            View view = viewGroup1.getChildAt(0);
            viewGroup1.removeViewAt(0);
            contentFrameLayout.addView(view);
          } 
          viewGroup1.setId(-1);
          contentFrameLayout.setId(16908290);
          if (viewGroup1 instanceof FrameLayout)
            ((FrameLayout)viewGroup1).setForeground(null); 
        } 
        this.mWindow.setContentView((View)viewGroup);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() {
              final AppCompatDelegateImpl this$0;
              
              public void onAttachedFromWindow() {}
              
              public void onDetachedFromWindow() {
                AppCompatDelegateImpl.this.dismissPopups();
              }
            });
        return viewGroup;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("AppCompat does not support the current theme features: { windowActionBar: ");
      stringBuilder.append(this.mHasActionBar);
      stringBuilder.append(", windowActionBarOverlay: ");
      stringBuilder.append(this.mOverlayActionBar);
      stringBuilder.append(", android:windowIsFloating: ");
      stringBuilder.append(this.mIsFloating);
      stringBuilder.append(", windowActionModeOverlay: ");
      stringBuilder.append(this.mOverlayActionMode);
      stringBuilder.append(", windowNoTitle: ");
      stringBuilder.append(this.mWindowNoTitle);
      stringBuilder.append(" }");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    stringBuilder.recycle();
    throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
  }
  
  private void ensureSubDecor() {
    if (!this.mSubDecorInstalled) {
      this.mSubDecor = createSubDecor();
      CharSequence charSequence = getTitle();
      if (!TextUtils.isEmpty(charSequence)) {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
          decorContentParent.setWindowTitle(charSequence);
        } else if (peekSupportActionBar() != null) {
          peekSupportActionBar().setWindowTitle(charSequence);
        } else {
          TextView textView = this.mTitleView;
          if (textView != null)
            textView.setText(charSequence); 
        } 
      } 
      applyFixedSizeWindow();
      onSubDecorInstalled(this.mSubDecor);
      this.mSubDecorInstalled = true;
      PanelFeatureState panelFeatureState = getPanelState(0, false);
      if (!this.mIsDestroyed && (panelFeatureState == null || panelFeatureState.menu == null))
        invalidatePanelMenu(108); 
    } 
  }
  
  private void ensureWindow() {
    if (this.mWindow == null) {
      Object object = this.mHost;
      if (object instanceof Activity)
        attachToWindow(((Activity)object).getWindow()); 
    } 
    if (this.mWindow != null)
      return; 
    throw new IllegalStateException("We have not been given a Window");
  }
  
  private static Configuration generateConfigDelta(Configuration paramConfiguration1, Configuration paramConfiguration2) {
    Configuration configuration = new Configuration();
    configuration.fontScale = 0.0F;
    if (paramConfiguration2 != null && paramConfiguration1.diff(paramConfiguration2) != 0) {
      if (paramConfiguration1.fontScale != paramConfiguration2.fontScale)
        configuration.fontScale = paramConfiguration2.fontScale; 
      if (paramConfiguration1.mcc != paramConfiguration2.mcc)
        configuration.mcc = paramConfiguration2.mcc; 
      if (paramConfiguration1.mnc != paramConfiguration2.mnc)
        configuration.mnc = paramConfiguration2.mnc; 
      if (Build.VERSION.SDK_INT >= 24) {
        ConfigurationImplApi24.generateConfigDelta_locale(paramConfiguration1, paramConfiguration2, configuration);
      } else if (!ObjectsCompat.equals(paramConfiguration1.locale, paramConfiguration2.locale)) {
        configuration.locale = paramConfiguration2.locale;
      } 
      if (paramConfiguration1.touchscreen != paramConfiguration2.touchscreen)
        configuration.touchscreen = paramConfiguration2.touchscreen; 
      if (paramConfiguration1.keyboard != paramConfiguration2.keyboard)
        configuration.keyboard = paramConfiguration2.keyboard; 
      if (paramConfiguration1.keyboardHidden != paramConfiguration2.keyboardHidden)
        configuration.keyboardHidden = paramConfiguration2.keyboardHidden; 
      if (paramConfiguration1.navigation != paramConfiguration2.navigation)
        configuration.navigation = paramConfiguration2.navigation; 
      if (paramConfiguration1.navigationHidden != paramConfiguration2.navigationHidden)
        configuration.navigationHidden = paramConfiguration2.navigationHidden; 
      if (paramConfiguration1.orientation != paramConfiguration2.orientation)
        configuration.orientation = paramConfiguration2.orientation; 
      if ((paramConfiguration1.screenLayout & 0xF) != (paramConfiguration2.screenLayout & 0xF))
        configuration.screenLayout |= paramConfiguration2.screenLayout & 0xF; 
      if ((paramConfiguration1.screenLayout & 0xC0) != (paramConfiguration2.screenLayout & 0xC0))
        configuration.screenLayout |= paramConfiguration2.screenLayout & 0xC0; 
      if ((paramConfiguration1.screenLayout & 0x30) != (paramConfiguration2.screenLayout & 0x30))
        configuration.screenLayout |= paramConfiguration2.screenLayout & 0x30; 
      if ((paramConfiguration1.screenLayout & 0x300) != (paramConfiguration2.screenLayout & 0x300))
        configuration.screenLayout |= paramConfiguration2.screenLayout & 0x300; 
      if (Build.VERSION.SDK_INT >= 26)
        ConfigurationImplApi26.generateConfigDelta_colorMode(paramConfiguration1, paramConfiguration2, configuration); 
      if ((paramConfiguration1.uiMode & 0xF) != (paramConfiguration2.uiMode & 0xF))
        configuration.uiMode |= paramConfiguration2.uiMode & 0xF; 
      if ((paramConfiguration1.uiMode & 0x30) != (paramConfiguration2.uiMode & 0x30))
        configuration.uiMode |= paramConfiguration2.uiMode & 0x30; 
      if (paramConfiguration1.screenWidthDp != paramConfiguration2.screenWidthDp)
        configuration.screenWidthDp = paramConfiguration2.screenWidthDp; 
      if (paramConfiguration1.screenHeightDp != paramConfiguration2.screenHeightDp)
        configuration.screenHeightDp = paramConfiguration2.screenHeightDp; 
      if (paramConfiguration1.smallestScreenWidthDp != paramConfiguration2.smallestScreenWidthDp)
        configuration.smallestScreenWidthDp = paramConfiguration2.smallestScreenWidthDp; 
      if (Build.VERSION.SDK_INT >= 17)
        ConfigurationImplApi17.generateConfigDelta_densityDpi(paramConfiguration1, paramConfiguration2, configuration); 
    } 
    return configuration;
  }
  
  private AutoNightModeManager getAutoBatteryNightModeManager(Context paramContext) {
    if (this.mAutoBatteryNightModeManager == null)
      this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(paramContext); 
    return this.mAutoBatteryNightModeManager;
  }
  
  private AutoNightModeManager getAutoTimeNightModeManager(Context paramContext) {
    if (this.mAutoTimeNightModeManager == null)
      this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(paramContext)); 
    return this.mAutoTimeNightModeManager;
  }
  
  private void initWindowDecorActionBar() {
    ensureSubDecor();
    if (this.mHasActionBar && this.mActionBar == null) {
      Object object = this.mHost;
      if (object instanceof Activity) {
        this.mActionBar = new WindowDecorActionBar((Activity)this.mHost, this.mOverlayActionBar);
      } else if (object instanceof Dialog) {
        this.mActionBar = new WindowDecorActionBar((Dialog)this.mHost);
      } 
      object = this.mActionBar;
      if (object != null)
        object.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp); 
    } 
  }
  
  private boolean initializePanelContent(PanelFeatureState paramPanelFeatureState) {
    View view = paramPanelFeatureState.createdPanelView;
    boolean bool = true;
    if (view != null) {
      paramPanelFeatureState.shownPanelView = paramPanelFeatureState.createdPanelView;
      return true;
    } 
    if (paramPanelFeatureState.menu == null)
      return false; 
    if (this.mPanelMenuPresenterCallback == null)
      this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback(); 
    paramPanelFeatureState.shownPanelView = (View)paramPanelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback);
    if (paramPanelFeatureState.shownPanelView == null)
      bool = false; 
    return bool;
  }
  
  private boolean initializePanelDecor(PanelFeatureState paramPanelFeatureState) {
    paramPanelFeatureState.setStyle(getActionBarThemedContext());
    paramPanelFeatureState.decorView = (ViewGroup)new ListMenuDecorView(paramPanelFeatureState.listPresenterContext);
    paramPanelFeatureState.gravity = 81;
    return true;
  }
  
  private boolean initializePanelMenu(PanelFeatureState paramPanelFeatureState) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mContext : Landroid/content/Context;
    //   4: astore #4
    //   6: aload_1
    //   7: getfield featureId : I
    //   10: ifeq -> 25
    //   13: aload #4
    //   15: astore_2
    //   16: aload_1
    //   17: getfield featureId : I
    //   20: bipush #108
    //   22: if_icmpne -> 191
    //   25: aload #4
    //   27: astore_2
    //   28: aload_0
    //   29: getfield mDecorContentParent : Landroidx/appcompat/widget/DecorContentParent;
    //   32: ifnull -> 191
    //   35: new android/util/TypedValue
    //   38: dup
    //   39: invokespecial <init> : ()V
    //   42: astore #5
    //   44: aload #4
    //   46: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   49: astore #6
    //   51: aload #6
    //   53: getstatic androidx/appcompat/R$attr.actionBarTheme : I
    //   56: aload #5
    //   58: iconst_1
    //   59: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   62: pop
    //   63: aconst_null
    //   64: astore_2
    //   65: aload #5
    //   67: getfield resourceId : I
    //   70: ifeq -> 112
    //   73: aload #4
    //   75: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   78: invokevirtual newTheme : ()Landroid/content/res/Resources$Theme;
    //   81: astore_2
    //   82: aload_2
    //   83: aload #6
    //   85: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   88: aload_2
    //   89: aload #5
    //   91: getfield resourceId : I
    //   94: iconst_1
    //   95: invokevirtual applyStyle : (IZ)V
    //   98: aload_2
    //   99: getstatic androidx/appcompat/R$attr.actionBarWidgetTheme : I
    //   102: aload #5
    //   104: iconst_1
    //   105: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   108: pop
    //   109: goto -> 124
    //   112: aload #6
    //   114: getstatic androidx/appcompat/R$attr.actionBarWidgetTheme : I
    //   117: aload #5
    //   119: iconst_1
    //   120: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   123: pop
    //   124: aload_2
    //   125: astore_3
    //   126: aload #5
    //   128: getfield resourceId : I
    //   131: ifeq -> 165
    //   134: aload_2
    //   135: astore_3
    //   136: aload_2
    //   137: ifnonnull -> 155
    //   140: aload #4
    //   142: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   145: invokevirtual newTheme : ()Landroid/content/res/Resources$Theme;
    //   148: astore_3
    //   149: aload_3
    //   150: aload #6
    //   152: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   155: aload_3
    //   156: aload #5
    //   158: getfield resourceId : I
    //   161: iconst_1
    //   162: invokevirtual applyStyle : (IZ)V
    //   165: aload #4
    //   167: astore_2
    //   168: aload_3
    //   169: ifnull -> 191
    //   172: new androidx/appcompat/view/ContextThemeWrapper
    //   175: dup
    //   176: aload #4
    //   178: iconst_0
    //   179: invokespecial <init> : (Landroid/content/Context;I)V
    //   182: astore_2
    //   183: aload_2
    //   184: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   187: aload_3
    //   188: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   191: new androidx/appcompat/view/menu/MenuBuilder
    //   194: dup
    //   195: aload_2
    //   196: invokespecial <init> : (Landroid/content/Context;)V
    //   199: astore_2
    //   200: aload_2
    //   201: aload_0
    //   202: invokevirtual setCallback : (Landroidx/appcompat/view/menu/MenuBuilder$Callback;)V
    //   205: aload_1
    //   206: aload_2
    //   207: invokevirtual setMenu : (Landroidx/appcompat/view/menu/MenuBuilder;)V
    //   210: iconst_1
    //   211: ireturn
  }
  
  private void invalidatePanelMenu(int paramInt) {
    this.mInvalidatePanelMenuFeatures = 1 << paramInt | this.mInvalidatePanelMenuFeatures;
    if (!this.mInvalidatePanelMenuPosted) {
      ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
      this.mInvalidatePanelMenuPosted = true;
    } 
  }
  
  private boolean isActivityManifestHandlingUiMode() {
    if (!this.mActivityHandlesUiModeChecked && this.mHost instanceof Activity) {
      PackageManager packageManager = this.mContext.getPackageManager();
      if (packageManager == null)
        return false; 
      try {
        boolean bool1;
        boolean bool2;
        if (Build.VERSION.SDK_INT >= 29) {
          bool1 = true;
        } else if (Build.VERSION.SDK_INT >= 24) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        ComponentName componentName = new ComponentName();
        this(this.mContext, this.mHost.getClass());
        ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, bool1);
        if (activityInfo != null && (activityInfo.configChanges & 0x200) != 0) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        this.mActivityHandlesUiMode = bool2;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", (Throwable)nameNotFoundException);
        this.mActivityHandlesUiMode = false;
      } 
    } 
    this.mActivityHandlesUiModeChecked = true;
    return this.mActivityHandlesUiMode;
  }
  
  private boolean onKeyDownPanel(int paramInt, KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getRepeatCount() == 0) {
      PanelFeatureState panelFeatureState = getPanelState(paramInt, true);
      if (!panelFeatureState.isOpen)
        return preparePanel(panelFeatureState, paramKeyEvent); 
    } 
    return false;
  }
  
  private boolean onKeyUpPanel(int paramInt, KeyEvent paramKeyEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mActionMode : Landroidx/appcompat/view/ActionMode;
    //   4: ifnull -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: iconst_1
    //   10: istore #4
    //   12: aload_0
    //   13: iload_1
    //   14: iconst_1
    //   15: invokevirtual getPanelState : (IZ)Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;
    //   18: astore #5
    //   20: iload_1
    //   21: ifne -> 113
    //   24: aload_0
    //   25: getfield mDecorContentParent : Landroidx/appcompat/widget/DecorContentParent;
    //   28: astore #6
    //   30: aload #6
    //   32: ifnull -> 113
    //   35: aload #6
    //   37: invokeinterface canShowOverflowMenu : ()Z
    //   42: ifeq -> 113
    //   45: aload_0
    //   46: getfield mContext : Landroid/content/Context;
    //   49: invokestatic get : (Landroid/content/Context;)Landroid/view/ViewConfiguration;
    //   52: invokevirtual hasPermanentMenuKey : ()Z
    //   55: ifne -> 113
    //   58: aload_0
    //   59: getfield mDecorContentParent : Landroidx/appcompat/widget/DecorContentParent;
    //   62: invokeinterface isOverflowMenuShowing : ()Z
    //   67: ifne -> 100
    //   70: aload_0
    //   71: getfield mIsDestroyed : Z
    //   74: ifne -> 184
    //   77: aload_0
    //   78: aload #5
    //   80: aload_2
    //   81: invokespecial preparePanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   84: ifeq -> 184
    //   87: aload_0
    //   88: getfield mDecorContentParent : Landroidx/appcompat/widget/DecorContentParent;
    //   91: invokeinterface showOverflowMenu : ()Z
    //   96: istore_3
    //   97: goto -> 202
    //   100: aload_0
    //   101: getfield mDecorContentParent : Landroidx/appcompat/widget/DecorContentParent;
    //   104: invokeinterface hideOverflowMenu : ()Z
    //   109: istore_3
    //   110: goto -> 202
    //   113: aload #5
    //   115: getfield isOpen : Z
    //   118: ifne -> 189
    //   121: aload #5
    //   123: getfield isHandled : Z
    //   126: ifeq -> 132
    //   129: goto -> 189
    //   132: aload #5
    //   134: getfield isPrepared : Z
    //   137: ifeq -> 184
    //   140: aload #5
    //   142: getfield refreshMenuContent : Z
    //   145: ifeq -> 165
    //   148: aload #5
    //   150: iconst_0
    //   151: putfield isPrepared : Z
    //   154: aload_0
    //   155: aload #5
    //   157: aload_2
    //   158: invokespecial preparePanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   161: istore_3
    //   162: goto -> 167
    //   165: iconst_1
    //   166: istore_3
    //   167: iload_3
    //   168: ifeq -> 184
    //   171: aload_0
    //   172: aload #5
    //   174: aload_2
    //   175: invokespecial openPanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Landroid/view/KeyEvent;)V
    //   178: iload #4
    //   180: istore_3
    //   181: goto -> 202
    //   184: iconst_0
    //   185: istore_3
    //   186: goto -> 202
    //   189: aload #5
    //   191: getfield isOpen : Z
    //   194: istore_3
    //   195: aload_0
    //   196: aload #5
    //   198: iconst_1
    //   199: invokevirtual closePanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Z)V
    //   202: iload_3
    //   203: ifeq -> 245
    //   206: aload_0
    //   207: getfield mContext : Landroid/content/Context;
    //   210: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   213: ldc_w 'audio'
    //   216: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   219: checkcast android/media/AudioManager
    //   222: astore_2
    //   223: aload_2
    //   224: ifnull -> 235
    //   227: aload_2
    //   228: iconst_0
    //   229: invokevirtual playSoundEffect : (I)V
    //   232: goto -> 245
    //   235: ldc_w 'AppCompatDelegate'
    //   238: ldc_w 'Couldn't get audio manager'
    //   241: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   244: pop
    //   245: iload_3
    //   246: ireturn
  }
  
  private void openPanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent) {
    // Byte code:
    //   0: aload_1
    //   1: getfield isOpen : Z
    //   4: ifne -> 412
    //   7: aload_0
    //   8: getfield mIsDestroyed : Z
    //   11: ifeq -> 17
    //   14: goto -> 412
    //   17: aload_1
    //   18: getfield featureId : I
    //   21: ifne -> 56
    //   24: aload_0
    //   25: getfield mContext : Landroid/content/Context;
    //   28: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   31: invokevirtual getConfiguration : ()Landroid/content/res/Configuration;
    //   34: getfield screenLayout : I
    //   37: bipush #15
    //   39: iand
    //   40: iconst_4
    //   41: if_icmpne -> 49
    //   44: iconst_1
    //   45: istore_3
    //   46: goto -> 51
    //   49: iconst_0
    //   50: istore_3
    //   51: iload_3
    //   52: ifeq -> 56
    //   55: return
    //   56: aload_0
    //   57: invokevirtual getWindowCallback : ()Landroid/view/Window$Callback;
    //   60: astore #4
    //   62: aload #4
    //   64: ifnull -> 92
    //   67: aload #4
    //   69: aload_1
    //   70: getfield featureId : I
    //   73: aload_1
    //   74: getfield menu : Landroidx/appcompat/view/menu/MenuBuilder;
    //   77: invokeinterface onMenuOpened : (ILandroid/view/Menu;)Z
    //   82: ifne -> 92
    //   85: aload_0
    //   86: aload_1
    //   87: iconst_1
    //   88: invokevirtual closePanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Z)V
    //   91: return
    //   92: aload_0
    //   93: getfield mContext : Landroid/content/Context;
    //   96: ldc_w 'window'
    //   99: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   102: checkcast android/view/WindowManager
    //   105: astore #5
    //   107: aload #5
    //   109: ifnonnull -> 113
    //   112: return
    //   113: aload_0
    //   114: aload_1
    //   115: aload_2
    //   116: invokespecial preparePanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   119: ifne -> 123
    //   122: return
    //   123: aload_1
    //   124: getfield decorView : Landroid/view/ViewGroup;
    //   127: ifnull -> 172
    //   130: aload_1
    //   131: getfield refreshDecorView : Z
    //   134: ifeq -> 140
    //   137: goto -> 172
    //   140: aload_1
    //   141: getfield createdPanelView : Landroid/view/View;
    //   144: ifnull -> 338
    //   147: aload_1
    //   148: getfield createdPanelView : Landroid/view/View;
    //   151: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   154: astore_2
    //   155: aload_2
    //   156: ifnull -> 338
    //   159: aload_2
    //   160: getfield width : I
    //   163: iconst_m1
    //   164: if_icmpne -> 338
    //   167: iconst_m1
    //   168: istore_3
    //   169: goto -> 341
    //   172: aload_1
    //   173: getfield decorView : Landroid/view/ViewGroup;
    //   176: ifnonnull -> 195
    //   179: aload_0
    //   180: aload_1
    //   181: invokespecial initializePanelDecor : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;)Z
    //   184: ifeq -> 194
    //   187: aload_1
    //   188: getfield decorView : Landroid/view/ViewGroup;
    //   191: ifnonnull -> 219
    //   194: return
    //   195: aload_1
    //   196: getfield refreshDecorView : Z
    //   199: ifeq -> 219
    //   202: aload_1
    //   203: getfield decorView : Landroid/view/ViewGroup;
    //   206: invokevirtual getChildCount : ()I
    //   209: ifle -> 219
    //   212: aload_1
    //   213: getfield decorView : Landroid/view/ViewGroup;
    //   216: invokevirtual removeAllViews : ()V
    //   219: aload_0
    //   220: aload_1
    //   221: invokespecial initializePanelContent : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;)Z
    //   224: ifeq -> 407
    //   227: aload_1
    //   228: invokevirtual hasPanelItems : ()Z
    //   231: ifne -> 237
    //   234: goto -> 407
    //   237: aload_1
    //   238: getfield shownPanelView : Landroid/view/View;
    //   241: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   244: astore #4
    //   246: aload #4
    //   248: astore_2
    //   249: aload #4
    //   251: ifnonnull -> 266
    //   254: new android/view/ViewGroup$LayoutParams
    //   257: dup
    //   258: bipush #-2
    //   260: bipush #-2
    //   262: invokespecial <init> : (II)V
    //   265: astore_2
    //   266: aload_1
    //   267: getfield background : I
    //   270: istore_3
    //   271: aload_1
    //   272: getfield decorView : Landroid/view/ViewGroup;
    //   275: iload_3
    //   276: invokevirtual setBackgroundResource : (I)V
    //   279: aload_1
    //   280: getfield shownPanelView : Landroid/view/View;
    //   283: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   286: astore #4
    //   288: aload #4
    //   290: instanceof android/view/ViewGroup
    //   293: ifeq -> 308
    //   296: aload #4
    //   298: checkcast android/view/ViewGroup
    //   301: aload_1
    //   302: getfield shownPanelView : Landroid/view/View;
    //   305: invokevirtual removeView : (Landroid/view/View;)V
    //   308: aload_1
    //   309: getfield decorView : Landroid/view/ViewGroup;
    //   312: aload_1
    //   313: getfield shownPanelView : Landroid/view/View;
    //   316: aload_2
    //   317: invokevirtual addView : (Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    //   320: aload_1
    //   321: getfield shownPanelView : Landroid/view/View;
    //   324: invokevirtual hasFocus : ()Z
    //   327: ifne -> 338
    //   330: aload_1
    //   331: getfield shownPanelView : Landroid/view/View;
    //   334: invokevirtual requestFocus : ()Z
    //   337: pop
    //   338: bipush #-2
    //   340: istore_3
    //   341: aload_1
    //   342: iconst_0
    //   343: putfield isHandled : Z
    //   346: new android/view/WindowManager$LayoutParams
    //   349: dup
    //   350: iload_3
    //   351: bipush #-2
    //   353: aload_1
    //   354: getfield x : I
    //   357: aload_1
    //   358: getfield y : I
    //   361: sipush #1002
    //   364: ldc_w 8519680
    //   367: bipush #-3
    //   369: invokespecial <init> : (IIIIIII)V
    //   372: astore_2
    //   373: aload_2
    //   374: aload_1
    //   375: getfield gravity : I
    //   378: putfield gravity : I
    //   381: aload_2
    //   382: aload_1
    //   383: getfield windowAnimations : I
    //   386: putfield windowAnimations : I
    //   389: aload #5
    //   391: aload_1
    //   392: getfield decorView : Landroid/view/ViewGroup;
    //   395: aload_2
    //   396: invokeinterface addView : (Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    //   401: aload_1
    //   402: iconst_1
    //   403: putfield isOpen : Z
    //   406: return
    //   407: aload_1
    //   408: iconst_1
    //   409: putfield refreshDecorView : Z
    //   412: return
  }
  
  private boolean performPanelShortcut(PanelFeatureState paramPanelFeatureState, int paramInt1, KeyEvent paramKeyEvent, int paramInt2) {
    // Byte code:
    //   0: aload_3
    //   1: invokevirtual isSystem : ()Z
    //   4: istore #5
    //   6: iconst_0
    //   7: istore #6
    //   9: iload #5
    //   11: ifeq -> 16
    //   14: iconst_0
    //   15: ireturn
    //   16: aload_1
    //   17: getfield isPrepared : Z
    //   20: ifne -> 36
    //   23: iload #6
    //   25: istore #5
    //   27: aload_0
    //   28: aload_1
    //   29: aload_3
    //   30: invokespecial preparePanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   33: ifeq -> 60
    //   36: iload #6
    //   38: istore #5
    //   40: aload_1
    //   41: getfield menu : Landroidx/appcompat/view/menu/MenuBuilder;
    //   44: ifnull -> 60
    //   47: aload_1
    //   48: getfield menu : Landroidx/appcompat/view/menu/MenuBuilder;
    //   51: iload_2
    //   52: aload_3
    //   53: iload #4
    //   55: invokevirtual performShortcut : (ILandroid/view/KeyEvent;I)Z
    //   58: istore #5
    //   60: iload #5
    //   62: ifeq -> 85
    //   65: iload #4
    //   67: iconst_1
    //   68: iand
    //   69: ifne -> 85
    //   72: aload_0
    //   73: getfield mDecorContentParent : Landroidx/appcompat/widget/DecorContentParent;
    //   76: ifnonnull -> 85
    //   79: aload_0
    //   80: aload_1
    //   81: iconst_1
    //   82: invokevirtual closePanel : (Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;Z)V
    //   85: iload #5
    //   87: ireturn
  }
  
  private boolean preparePanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent) {
    DecorContentParent decorContentParent;
    int i;
    if (this.mIsDestroyed)
      return false; 
    if (paramPanelFeatureState.isPrepared)
      return true; 
    PanelFeatureState panelFeatureState = this.mPreparedPanel;
    if (panelFeatureState != null && panelFeatureState != paramPanelFeatureState)
      closePanel(panelFeatureState, false); 
    Window.Callback callback = getWindowCallback();
    if (callback != null)
      paramPanelFeatureState.createdPanelView = callback.onCreatePanelView(paramPanelFeatureState.featureId); 
    if (paramPanelFeatureState.featureId == 0 || paramPanelFeatureState.featureId == 108) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      DecorContentParent decorContentParent1 = this.mDecorContentParent;
      if (decorContentParent1 != null)
        decorContentParent1.setMenuPrepared(); 
    } 
    if (paramPanelFeatureState.createdPanelView == null && (!i || !(peekSupportActionBar() instanceof ToolbarActionBar))) {
      DecorContentParent decorContentParent1;
      boolean bool;
      if (paramPanelFeatureState.menu == null || paramPanelFeatureState.refreshMenuContent) {
        if (paramPanelFeatureState.menu == null && (!initializePanelMenu(paramPanelFeatureState) || paramPanelFeatureState.menu == null))
          return false; 
        if (i && this.mDecorContentParent != null) {
          if (this.mActionMenuPresenterCallback == null)
            this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback(); 
          this.mDecorContentParent.setMenu((Menu)paramPanelFeatureState.menu, this.mActionMenuPresenterCallback);
        } 
        paramPanelFeatureState.menu.stopDispatchingItemsChanged();
        if (!callback.onCreatePanelMenu(paramPanelFeatureState.featureId, (Menu)paramPanelFeatureState.menu)) {
          paramPanelFeatureState.setMenu(null);
          if (i) {
            decorContentParent = this.mDecorContentParent;
            if (decorContentParent != null)
              decorContentParent.setMenu(null, this.mActionMenuPresenterCallback); 
          } 
          return false;
        } 
        ((PanelFeatureState)decorContentParent).refreshMenuContent = false;
      } 
      ((PanelFeatureState)decorContentParent).menu.stopDispatchingItemsChanged();
      if (((PanelFeatureState)decorContentParent).frozenActionViewState != null) {
        ((PanelFeatureState)decorContentParent).menu.restoreActionViewStates(((PanelFeatureState)decorContentParent).frozenActionViewState);
        ((PanelFeatureState)decorContentParent).frozenActionViewState = null;
      } 
      if (!callback.onPreparePanel(0, ((PanelFeatureState)decorContentParent).createdPanelView, (Menu)((PanelFeatureState)decorContentParent).menu)) {
        if (i) {
          decorContentParent1 = this.mDecorContentParent;
          if (decorContentParent1 != null)
            decorContentParent1.setMenu(null, this.mActionMenuPresenterCallback); 
        } 
        ((PanelFeatureState)decorContentParent).menu.startDispatchingItemsChanged();
        return false;
      } 
      if (decorContentParent1 != null) {
        i = decorContentParent1.getDeviceId();
      } else {
        i = -1;
      } 
      if (KeyCharacterMap.load(i).getKeyboardType() != 1) {
        bool = true;
      } else {
        bool = false;
      } 
      ((PanelFeatureState)decorContentParent).qwertyMode = bool;
      ((PanelFeatureState)decorContentParent).menu.setQwertyMode(((PanelFeatureState)decorContentParent).qwertyMode);
      ((PanelFeatureState)decorContentParent).menu.startDispatchingItemsChanged();
    } 
    ((PanelFeatureState)decorContentParent).isPrepared = true;
    ((PanelFeatureState)decorContentParent).isHandled = false;
    this.mPreparedPanel = (PanelFeatureState)decorContentParent;
    return true;
  }
  
  private void reopenMenu(boolean paramBoolean) {
    DecorContentParent decorContentParent = this.mDecorContentParent;
    if (decorContentParent != null && decorContentParent.canShowOverflowMenu() && (!ViewConfiguration.get(this.mContext).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
      Window.Callback callback = getWindowCallback();
      if (!this.mDecorContentParent.isOverflowMenuShowing() || !paramBoolean) {
        if (callback != null && !this.mIsDestroyed) {
          if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 0x1) != 0) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
          } 
          PanelFeatureState panelFeatureState1 = getPanelState(0, true);
          if (panelFeatureState1.menu != null && !panelFeatureState1.refreshMenuContent && callback.onPreparePanel(0, panelFeatureState1.createdPanelView, (Menu)panelFeatureState1.menu)) {
            callback.onMenuOpened(108, (Menu)panelFeatureState1.menu);
            this.mDecorContentParent.showOverflowMenu();
          } 
        } 
        return;
      } 
      this.mDecorContentParent.hideOverflowMenu();
      if (!this.mIsDestroyed)
        callback.onPanelClosed(108, (Menu)(getPanelState(0, true)).menu); 
      return;
    } 
    PanelFeatureState panelFeatureState = getPanelState(0, true);
    panelFeatureState.refreshDecorView = true;
    closePanel(panelFeatureState, false);
    openPanel(panelFeatureState, (KeyEvent)null);
  }
  
  private int sanitizeWindowFeatureId(int paramInt) {
    if (paramInt == 8) {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
      return 108;
    } 
    int i = paramInt;
    if (paramInt == 9) {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
      i = 109;
    } 
    return i;
  }
  
  private boolean shouldInheritContext(ViewParent paramViewParent) {
    if (paramViewParent == null)
      return false; 
    View view = this.mWindow.getDecorView();
    while (true) {
      if (paramViewParent == null)
        return true; 
      if (paramViewParent == view || !(paramViewParent instanceof View) || ViewCompat.isAttachedToWindow((View)paramViewParent))
        break; 
      paramViewParent = paramViewParent.getParent();
    } 
    return false;
  }
  
  private void throwFeatureRequestIfSubDecorInstalled() {
    if (!this.mSubDecorInstalled)
      return; 
    throw new AndroidRuntimeException("Window feature must be requested before adding content");
  }
  
  private AppCompatActivity tryUnwrapContext() {
    Context context = this.mContext;
    while (context != null) {
      if (context instanceof AppCompatActivity)
        return (AppCompatActivity)context; 
      if (context instanceof ContextWrapper)
        context = ((ContextWrapper)context).getBaseContext(); 
    } 
    return null;
  }
  
  private boolean updateForNightMode(int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: aload_0
    //   2: getfield mContext : Landroid/content/Context;
    //   5: iload_1
    //   6: aconst_null
    //   7: invokespecial createOverrideConfigurationForDayNight : (Landroid/content/Context;ILandroid/content/res/Configuration;)Landroid/content/res/Configuration;
    //   10: astore #7
    //   12: aload_0
    //   13: invokespecial isActivityManifestHandlingUiMode : ()Z
    //   16: istore #6
    //   18: aload_0
    //   19: getfield mContext : Landroid/content/Context;
    //   22: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   25: invokevirtual getConfiguration : ()Landroid/content/res/Configuration;
    //   28: getfield uiMode : I
    //   31: bipush #48
    //   33: iand
    //   34: istore_3
    //   35: aload #7
    //   37: getfield uiMode : I
    //   40: bipush #48
    //   42: iand
    //   43: istore #4
    //   45: iconst_1
    //   46: istore #5
    //   48: iload_3
    //   49: iload #4
    //   51: if_icmpeq -> 123
    //   54: iload_2
    //   55: ifeq -> 123
    //   58: iload #6
    //   60: ifne -> 123
    //   63: aload_0
    //   64: getfield mBaseContextAttached : Z
    //   67: ifeq -> 123
    //   70: getstatic androidx/appcompat/app/AppCompatDelegateImpl.sCanReturnDifferentContext : Z
    //   73: ifne -> 83
    //   76: aload_0
    //   77: getfield mCreated : Z
    //   80: ifeq -> 123
    //   83: aload_0
    //   84: getfield mHost : Ljava/lang/Object;
    //   87: astore #7
    //   89: aload #7
    //   91: instanceof android/app/Activity
    //   94: ifeq -> 123
    //   97: aload #7
    //   99: checkcast android/app/Activity
    //   102: invokevirtual isChild : ()Z
    //   105: ifne -> 123
    //   108: aload_0
    //   109: getfield mHost : Ljava/lang/Object;
    //   112: checkcast android/app/Activity
    //   115: invokestatic recreate : (Landroid/app/Activity;)V
    //   118: iconst_1
    //   119: istore_2
    //   120: goto -> 125
    //   123: iconst_0
    //   124: istore_2
    //   125: iload_2
    //   126: ifne -> 150
    //   129: iload_3
    //   130: iload #4
    //   132: if_icmpeq -> 150
    //   135: aload_0
    //   136: iload #4
    //   138: iload #6
    //   140: aconst_null
    //   141: invokespecial updateResourcesConfigurationForNightMode : (IZLandroid/content/res/Configuration;)V
    //   144: iload #5
    //   146: istore_2
    //   147: goto -> 150
    //   150: iload_2
    //   151: ifeq -> 177
    //   154: aload_0
    //   155: getfield mHost : Ljava/lang/Object;
    //   158: astore #7
    //   160: aload #7
    //   162: instanceof androidx/appcompat/app/AppCompatActivity
    //   165: ifeq -> 177
    //   168: aload #7
    //   170: checkcast androidx/appcompat/app/AppCompatActivity
    //   173: iload_1
    //   174: invokevirtual onNightModeChanged : (I)V
    //   177: iload_2
    //   178: ireturn
  }
  
  private void updateResourcesConfigurationForNightMode(int paramInt, boolean paramBoolean, Configuration paramConfiguration) {
    Resources resources = this.mContext.getResources();
    Configuration configuration = new Configuration(resources.getConfiguration());
    if (paramConfiguration != null)
      configuration.updateFrom(paramConfiguration); 
    configuration.uiMode = paramInt | (resources.getConfiguration()).uiMode & 0xFFFFFFCF;
    resources.updateConfiguration(configuration, null);
    if (Build.VERSION.SDK_INT < 26)
      ResourcesFlusher.flush(resources); 
    paramInt = this.mThemeResId;
    if (paramInt != 0) {
      this.mContext.setTheme(paramInt);
      if (Build.VERSION.SDK_INT >= 23)
        this.mContext.getTheme().applyStyle(this.mThemeResId, true); 
    } 
    if (paramBoolean) {
      Object object = this.mHost;
      if (object instanceof Activity) {
        object = object;
        if (object instanceof LifecycleOwner) {
          if (((LifecycleOwner)object).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED))
            object.onConfigurationChanged(configuration); 
        } else if (this.mStarted) {
          object.onConfigurationChanged(configuration);
        } 
      } 
    } 
  }
  
  private void updateStatusGuardColor(View paramView) {
    int i;
    if ((ViewCompat.getWindowSystemUiVisibility(paramView) & 0x2000) != 0) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      i = ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard_light);
    } else {
      i = ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard);
    } 
    paramView.setBackgroundColor(i);
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    ensureSubDecor();
    ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(paramView, paramLayoutParams);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public boolean applyDayNight() {
    return applyDayNight(true);
  }
  
  public Context attachBaseContext2(Context paramContext) {
    int i = 1;
    this.mBaseContextAttached = true;
    int j = mapNightMode(paramContext, calculateNightMode());
    boolean bool = sCanApplyOverrideConfiguration;
    Configuration configuration = null;
    if (bool && paramContext instanceof ContextThemeWrapper) {
      Configuration configuration1 = createOverrideConfigurationForDayNight(paramContext, j, (Configuration)null);
      try {
        ContextThemeWrapperCompatApi17Impl.applyOverrideConfiguration((ContextThemeWrapper)paramContext, configuration1);
        return paramContext;
      } catch (IllegalStateException illegalStateException) {}
    } 
    if (paramContext instanceof ContextThemeWrapper) {
      Configuration configuration1 = createOverrideConfigurationForDayNight(paramContext, j, (Configuration)null);
      try {
        ((ContextThemeWrapper)paramContext).applyOverrideConfiguration(configuration1);
        return paramContext;
      } catch (IllegalStateException illegalStateException) {}
    } 
    if (!sCanReturnDifferentContext)
      return super.attachBaseContext2(paramContext); 
    try {
      Configuration configuration2 = paramContext.getPackageManager().getResourcesForApplication(paramContext.getApplicationInfo()).getConfiguration();
      Configuration configuration1 = paramContext.getResources().getConfiguration();
      if (!configuration2.equals(configuration1))
        configuration = generateConfigDelta(configuration2, configuration1); 
      configuration1 = createOverrideConfigurationForDayNight(paramContext, j, configuration);
      ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(paramContext, R.style.Theme_AppCompat_Empty);
      contextThemeWrapper.applyOverrideConfiguration(configuration1);
      j = 0;
      try {
        Resources.Theme theme = paramContext.getTheme();
        if (theme == null)
          i = 0; 
      } catch (NullPointerException nullPointerException) {
        i = j;
      } 
      if (i != 0)
        ResourcesCompat.ThemeCompat.rebase(contextThemeWrapper.getTheme()); 
      return super.attachBaseContext2((Context)contextThemeWrapper);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      throw new RuntimeException("Application failed to obtain resources from itself", nameNotFoundException);
    } 
  }
  
  void callOnPanelClosed(int paramInt, PanelFeatureState paramPanelFeatureState, Menu paramMenu) {
    MenuBuilder menuBuilder;
    PanelFeatureState panelFeatureState = paramPanelFeatureState;
    Menu menu = paramMenu;
    if (paramMenu == null) {
      PanelFeatureState panelFeatureState1 = paramPanelFeatureState;
      if (paramPanelFeatureState == null) {
        panelFeatureState1 = paramPanelFeatureState;
        if (paramInt >= 0) {
          PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
          panelFeatureState1 = paramPanelFeatureState;
          if (paramInt < arrayOfPanelFeatureState.length)
            panelFeatureState1 = arrayOfPanelFeatureState[paramInt]; 
        } 
      } 
      panelFeatureState = panelFeatureState1;
      menu = paramMenu;
      if (panelFeatureState1 != null) {
        menuBuilder = panelFeatureState1.menu;
        panelFeatureState = panelFeatureState1;
      } 
    } 
    if (panelFeatureState != null && !panelFeatureState.isOpen)
      return; 
    if (!this.mIsDestroyed)
      this.mAppCompatWindowCallback.getWrapped().onPanelClosed(paramInt, (Menu)menuBuilder); 
  }
  
  void checkCloseActionMenu(MenuBuilder paramMenuBuilder) {
    if (this.mClosingActionMenu)
      return; 
    this.mClosingActionMenu = true;
    this.mDecorContentParent.dismissPopups();
    Window.Callback callback = getWindowCallback();
    if (callback != null && !this.mIsDestroyed)
      callback.onPanelClosed(108, (Menu)paramMenuBuilder); 
    this.mClosingActionMenu = false;
  }
  
  void closePanel(int paramInt) {
    closePanel(getPanelState(paramInt, true), true);
  }
  
  void closePanel(PanelFeatureState paramPanelFeatureState, boolean paramBoolean) {
    if (paramBoolean && paramPanelFeatureState.featureId == 0) {
      DecorContentParent decorContentParent = this.mDecorContentParent;
      if (decorContentParent != null && decorContentParent.isOverflowMenuShowing()) {
        checkCloseActionMenu(paramPanelFeatureState.menu);
        return;
      } 
    } 
    WindowManager windowManager = (WindowManager)this.mContext.getSystemService("window");
    if (windowManager != null && paramPanelFeatureState.isOpen && paramPanelFeatureState.decorView != null) {
      windowManager.removeView((View)paramPanelFeatureState.decorView);
      if (paramBoolean)
        callOnPanelClosed(paramPanelFeatureState.featureId, paramPanelFeatureState, (Menu)null); 
    } 
    paramPanelFeatureState.isPrepared = false;
    paramPanelFeatureState.isHandled = false;
    paramPanelFeatureState.isOpen = false;
    paramPanelFeatureState.shownPanelView = null;
    paramPanelFeatureState.refreshDecorView = true;
    if (this.mPreparedPanel == paramPanelFeatureState)
      this.mPreparedPanel = null; 
  }
  
  public View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    AppCompatViewInflater appCompatViewInflater = this.mAppCompatViewInflater;
    boolean bool = false;
    if (appCompatViewInflater == null) {
      String str = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
      if (str == null) {
        this.mAppCompatViewInflater = new AppCompatViewInflater();
      } else {
        try {
          this.mAppCompatViewInflater = Class.forName(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } finally {
          appCompatViewInflater = null;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Failed to instantiate custom view inflater ");
          stringBuilder.append(str);
          stringBuilder.append(". Falling back to default.");
          Log.i("AppCompatDelegate", stringBuilder.toString(), (Throwable)appCompatViewInflater);
        } 
      } 
    } 
    if (IS_PRE_LOLLIPOP) {
      if (paramAttributeSet instanceof XmlPullParser) {
        if (((XmlPullParser)paramAttributeSet).getDepth() > 1)
          bool = true; 
      } else {
        bool = shouldInheritContext((ViewParent)paramView);
      } 
    } else {
      bool = false;
    } 
    return this.mAppCompatViewInflater.createView(paramView, paramString, paramContext, paramAttributeSet, bool, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
  }
  
  void dismissPopups() {
    DecorContentParent decorContentParent = this.mDecorContentParent;
    if (decorContentParent != null)
      decorContentParent.dismissPopups(); 
    if (this.mActionModePopup != null) {
      this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
      if (this.mActionModePopup.isShowing())
        try {
          this.mActionModePopup.dismiss();
        } catch (IllegalArgumentException illegalArgumentException) {} 
      this.mActionModePopup = null;
    } 
    endOnGoingFadeAnimation();
    PanelFeatureState panelFeatureState = getPanelState(0, false);
    if (panelFeatureState != null && panelFeatureState.menu != null)
      panelFeatureState.menu.close(); 
  }
  
  boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    Object object = this.mHost;
    boolean bool1 = object instanceof KeyEventDispatcher.Component;
    boolean bool = true;
    if (bool1 || object instanceof AppCompatDialog) {
      object = this.mWindow.getDecorView();
      if (object != null && KeyEventDispatcher.dispatchBeforeHierarchy((View)object, paramKeyEvent))
        return true; 
    } 
    if (paramKeyEvent.getKeyCode() == 82 && this.mAppCompatWindowCallback.getWrapped().dispatchKeyEvent(paramKeyEvent))
      return true; 
    int i = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() != 0)
      bool = false; 
    if (bool) {
      bool1 = onKeyDown(i, paramKeyEvent);
    } else {
      bool1 = onKeyUp(i, paramKeyEvent);
    } 
    return bool1;
  }
  
  void doInvalidatePanelMenu(int paramInt) {
    PanelFeatureState panelFeatureState = getPanelState(paramInt, true);
    if (panelFeatureState.menu != null) {
      Bundle bundle = new Bundle();
      panelFeatureState.menu.saveActionViewStates(bundle);
      if (bundle.size() > 0)
        panelFeatureState.frozenActionViewState = bundle; 
      panelFeatureState.menu.stopDispatchingItemsChanged();
      panelFeatureState.menu.clear();
    } 
    panelFeatureState.refreshMenuContent = true;
    panelFeatureState.refreshDecorView = true;
    if ((paramInt == 108 || paramInt == 0) && this.mDecorContentParent != null) {
      panelFeatureState = getPanelState(0, false);
      if (panelFeatureState != null) {
        panelFeatureState.isPrepared = false;
        preparePanel(panelFeatureState, (KeyEvent)null);
      } 
    } 
  }
  
  void endOnGoingFadeAnimation() {
    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
    if (viewPropertyAnimatorCompat != null)
      viewPropertyAnimatorCompat.cancel(); 
  }
  
  PanelFeatureState findMenuPanel(Menu paramMenu) {
    byte b1;
    PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
    byte b2 = 0;
    if (arrayOfPanelFeatureState != null) {
      b1 = arrayOfPanelFeatureState.length;
    } else {
      b1 = 0;
    } 
    while (b2 < b1) {
      PanelFeatureState panelFeatureState = arrayOfPanelFeatureState[b2];
      if (panelFeatureState != null && panelFeatureState.menu == paramMenu)
        return panelFeatureState; 
      b2++;
    } 
    return null;
  }
  
  public <T extends View> T findViewById(int paramInt) {
    ensureSubDecor();
    return (T)this.mWindow.findViewById(paramInt);
  }
  
  final Context getActionBarThemedContext() {
    Context context;
    ActionBar actionBar1 = getSupportActionBar();
    if (actionBar1 != null) {
      Context context1 = actionBar1.getThemedContext();
    } else {
      actionBar1 = null;
    } 
    ActionBar actionBar2 = actionBar1;
    if (actionBar1 == null)
      context = this.mContext; 
    return context;
  }
  
  final AutoNightModeManager getAutoTimeNightModeManager() {
    return getAutoTimeNightModeManager(this.mContext);
  }
  
  public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
    return new ActionBarDrawableToggleImpl();
  }
  
  public int getLocalNightMode() {
    return this.mLocalNightMode;
  }
  
  public MenuInflater getMenuInflater() {
    if (this.mMenuInflater == null) {
      Context context;
      initWindowDecorActionBar();
      ActionBar actionBar = this.mActionBar;
      if (actionBar != null) {
        context = actionBar.getThemedContext();
      } else {
        context = this.mContext;
      } 
      this.mMenuInflater = (MenuInflater)new SupportMenuInflater(context);
    } 
    return this.mMenuInflater;
  }
  
  protected PanelFeatureState getPanelState(int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPanels : [Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;
    //   4: astore #4
    //   6: aload #4
    //   8: ifnull -> 21
    //   11: aload #4
    //   13: astore_3
    //   14: aload #4
    //   16: arraylength
    //   17: iload_1
    //   18: if_icmpgt -> 49
    //   21: iload_1
    //   22: iconst_1
    //   23: iadd
    //   24: anewarray androidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState
    //   27: astore_3
    //   28: aload #4
    //   30: ifnull -> 44
    //   33: aload #4
    //   35: iconst_0
    //   36: aload_3
    //   37: iconst_0
    //   38: aload #4
    //   40: arraylength
    //   41: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   44: aload_0
    //   45: aload_3
    //   46: putfield mPanels : [Landroidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState;
    //   49: aload_3
    //   50: iload_1
    //   51: aaload
    //   52: astore #5
    //   54: aload #5
    //   56: astore #4
    //   58: aload #5
    //   60: ifnonnull -> 78
    //   63: new androidx/appcompat/app/AppCompatDelegateImpl$PanelFeatureState
    //   66: dup
    //   67: iload_1
    //   68: invokespecial <init> : (I)V
    //   71: astore #4
    //   73: aload_3
    //   74: iload_1
    //   75: aload #4
    //   77: aastore
    //   78: aload #4
    //   80: areturn
  }
  
  ViewGroup getSubDecor() {
    return this.mSubDecor;
  }
  
  public ActionBar getSupportActionBar() {
    initWindowDecorActionBar();
    return this.mActionBar;
  }
  
  final CharSequence getTitle() {
    Object object = this.mHost;
    return (object instanceof Activity) ? ((Activity)object).getTitle() : this.mTitle;
  }
  
  final Window.Callback getWindowCallback() {
    return this.mWindow.getCallback();
  }
  
  public boolean hasWindowFeature(int paramInt) {
    boolean bool;
    int i = sanitizeWindowFeatureId(paramInt);
    boolean bool2 = true;
    if (i != 1) {
      if (i != 2) {
        if (i != 5) {
          if (i != 10) {
            if (i != 108) {
              if (i != 109) {
                bool = false;
              } else {
                bool = this.mOverlayActionBar;
              } 
            } else {
              bool = this.mHasActionBar;
            } 
          } else {
            bool = this.mOverlayActionMode;
          } 
        } else {
          bool = this.mFeatureIndeterminateProgress;
        } 
      } else {
        bool = this.mFeatureProgress;
      } 
    } else {
      bool = this.mWindowNoTitle;
    } 
    boolean bool1 = bool2;
    if (!bool)
      if (this.mWindow.hasFeature(paramInt)) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }  
    return bool1;
  }
  
  public void installViewFactory() {
    LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
    if (layoutInflater.getFactory() == null) {
      LayoutInflaterCompat.setFactory2(layoutInflater, this);
    } else if (!(layoutInflater.getFactory2() instanceof AppCompatDelegateImpl)) {
      Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
    } 
  }
  
  public void invalidateOptionsMenu() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null && actionBar.invalidateOptionsMenu())
      return; 
    invalidatePanelMenu(0);
  }
  
  public boolean isHandleNativeActionModesEnabled() {
    return this.mHandleNativeActionModes;
  }
  
  int mapNightMode(Context paramContext, int paramInt) {
    if (paramInt != -100) {
      if (paramInt != -1)
        if (paramInt != 0) {
          if (paramInt != 1 && paramInt != 2) {
            if (paramInt == 3)
              return getAutoBatteryNightModeManager(paramContext).getApplyableNightMode(); 
            throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
          } 
        } else {
          return (Build.VERSION.SDK_INT >= 23 && ((UiModeManager)paramContext.getApplicationContext().getSystemService(UiModeManager.class)).getNightMode() == 0) ? -1 : getAutoTimeNightModeManager(paramContext).getApplyableNightMode();
        }  
      return paramInt;
    } 
    return -1;
  }
  
  boolean onBackPressed() {
    ActionMode actionMode = this.mActionMode;
    if (actionMode != null) {
      actionMode.finish();
      return true;
    } 
    ActionBar actionBar = getSupportActionBar();
    return (actionBar != null && actionBar.collapseActionView());
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    if (this.mHasActionBar && this.mSubDecorInstalled) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null)
        actionBar.onConfigurationChanged(paramConfiguration); 
    } 
    AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
    applyDayNight(false);
  }
  
  public void onCreate(Bundle paramBundle) {
    this.mBaseContextAttached = true;
    applyDayNight(false);
    ensureWindow();
    Object object = this.mHost;
    if (object instanceof Activity) {
      Object object1;
      paramBundle = null;
      try {
        object = NavUtils.getParentActivityName((Activity)object);
        object1 = object;
      } catch (IllegalArgumentException illegalArgumentException) {}
      if (object1 != null) {
        object1 = peekSupportActionBar();
        if (object1 == null) {
          this.mEnableDefaultActionBarUp = true;
        } else {
          object1.setDefaultDisplayHomeAsUpEnabled(true);
        } 
      } 
      addActiveDelegate(this);
    } 
    this.mCreated = true;
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return createView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return onCreateView((View)null, paramString, paramContext, paramAttributeSet);
  }
  
  public void onDestroy() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mHost : Ljava/lang/Object;
    //   4: instanceof android/app/Activity
    //   7: ifeq -> 14
    //   10: aload_0
    //   11: invokestatic removeActivityDelegate : (Landroidx/appcompat/app/AppCompatDelegate;)V
    //   14: aload_0
    //   15: getfield mInvalidatePanelMenuPosted : Z
    //   18: ifeq -> 36
    //   21: aload_0
    //   22: getfield mWindow : Landroid/view/Window;
    //   25: invokevirtual getDecorView : ()Landroid/view/View;
    //   28: aload_0
    //   29: getfield mInvalidatePanelMenuRunnable : Ljava/lang/Runnable;
    //   32: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)Z
    //   35: pop
    //   36: aload_0
    //   37: iconst_0
    //   38: putfield mStarted : Z
    //   41: aload_0
    //   42: iconst_1
    //   43: putfield mIsDestroyed : Z
    //   46: aload_0
    //   47: getfield mLocalNightMode : I
    //   50: bipush #-100
    //   52: if_icmpeq -> 104
    //   55: aload_0
    //   56: getfield mHost : Ljava/lang/Object;
    //   59: astore_1
    //   60: aload_1
    //   61: instanceof android/app/Activity
    //   64: ifeq -> 104
    //   67: aload_1
    //   68: checkcast android/app/Activity
    //   71: invokevirtual isChangingConfigurations : ()Z
    //   74: ifeq -> 104
    //   77: getstatic androidx/appcompat/app/AppCompatDelegateImpl.sLocalNightModes : Landroidx/collection/SimpleArrayMap;
    //   80: aload_0
    //   81: getfield mHost : Ljava/lang/Object;
    //   84: invokevirtual getClass : ()Ljava/lang/Class;
    //   87: invokevirtual getName : ()Ljava/lang/String;
    //   90: aload_0
    //   91: getfield mLocalNightMode : I
    //   94: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   97: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   100: pop
    //   101: goto -> 121
    //   104: getstatic androidx/appcompat/app/AppCompatDelegateImpl.sLocalNightModes : Landroidx/collection/SimpleArrayMap;
    //   107: aload_0
    //   108: getfield mHost : Ljava/lang/Object;
    //   111: invokevirtual getClass : ()Ljava/lang/Class;
    //   114: invokevirtual getName : ()Ljava/lang/String;
    //   117: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   120: pop
    //   121: aload_0
    //   122: getfield mActionBar : Landroidx/appcompat/app/ActionBar;
    //   125: astore_1
    //   126: aload_1
    //   127: ifnull -> 134
    //   130: aload_1
    //   131: invokevirtual onDestroy : ()V
    //   134: aload_0
    //   135: invokespecial cleanupAutoManagers : ()V
    //   138: return
  }
  
  boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    boolean bool = true;
    if (paramInt != 4) {
      if (paramInt == 82) {
        onKeyDownPanel(0, paramKeyEvent);
        return true;
      } 
    } else {
      if ((paramKeyEvent.getFlags() & 0x80) == 0)
        bool = false; 
      this.mLongPressBackDown = bool;
    } 
    return false;
  }
  
  boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent) {
    PanelFeatureState panelFeatureState1;
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null && actionBar.onKeyShortcut(paramInt, paramKeyEvent))
      return true; 
    PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
    if (panelFeatureState2 != null && performPanelShortcut(panelFeatureState2, paramKeyEvent.getKeyCode(), paramKeyEvent, 1)) {
      panelFeatureState1 = this.mPreparedPanel;
      if (panelFeatureState1 != null)
        panelFeatureState1.isHandled = true; 
      return true;
    } 
    if (this.mPreparedPanel == null) {
      panelFeatureState2 = getPanelState(0, true);
      preparePanel(panelFeatureState2, (KeyEvent)panelFeatureState1);
      boolean bool = performPanelShortcut(panelFeatureState2, panelFeatureState1.getKeyCode(), (KeyEvent)panelFeatureState1, 1);
      panelFeatureState2.isPrepared = false;
      if (bool)
        return true; 
    } 
    return false;
  }
  
  boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt != 4) {
      if (paramInt == 82) {
        onKeyUpPanel(0, paramKeyEvent);
        return true;
      } 
    } else {
      boolean bool = this.mLongPressBackDown;
      this.mLongPressBackDown = false;
      PanelFeatureState panelFeatureState = getPanelState(0, false);
      if (panelFeatureState != null && panelFeatureState.isOpen) {
        if (!bool)
          closePanel(panelFeatureState, true); 
        return true;
      } 
      if (onBackPressed())
        return true; 
    } 
    return false;
  }
  
  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem) {
    Window.Callback callback = getWindowCallback();
    if (callback != null && !this.mIsDestroyed) {
      PanelFeatureState panelFeatureState = findMenuPanel((Menu)paramMenuBuilder.getRootMenu());
      if (panelFeatureState != null)
        return callback.onMenuItemSelected(panelFeatureState.featureId, paramMenuItem); 
    } 
    return false;
  }
  
  public void onMenuModeChange(MenuBuilder paramMenuBuilder) {
    reopenMenu(true);
  }
  
  void onMenuOpened(int paramInt) {
    if (paramInt == 108) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null)
        actionBar.dispatchMenuVisibilityChanged(true); 
    } 
  }
  
  void onPanelClosed(int paramInt) {
    if (paramInt == 108) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null)
        actionBar.dispatchMenuVisibilityChanged(false); 
    } else if (paramInt == 0) {
      PanelFeatureState panelFeatureState = getPanelState(paramInt, true);
      if (panelFeatureState.isOpen)
        closePanel(panelFeatureState, false); 
    } 
  }
  
  public void onPostCreate(Bundle paramBundle) {
    ensureSubDecor();
  }
  
  public void onPostResume() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setShowHideAnimationEnabled(true); 
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {}
  
  public void onStart() {
    this.mStarted = true;
    applyDayNight();
  }
  
  public void onStop() {
    this.mStarted = false;
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setShowHideAnimationEnabled(false); 
  }
  
  void onSubDecorInstalled(ViewGroup paramViewGroup) {}
  
  final ActionBar peekSupportActionBar() {
    return this.mActionBar;
  }
  
  public boolean requestWindowFeature(int paramInt) {
    paramInt = sanitizeWindowFeatureId(paramInt);
    if (this.mWindowNoTitle && paramInt == 108)
      return false; 
    if (this.mHasActionBar && paramInt == 1)
      this.mHasActionBar = false; 
    if (paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt != 5) {
          if (paramInt != 10) {
            if (paramInt != 108) {
              if (paramInt != 109)
                return this.mWindow.requestFeature(paramInt); 
              throwFeatureRequestIfSubDecorInstalled();
              this.mOverlayActionBar = true;
              return true;
            } 
            throwFeatureRequestIfSubDecorInstalled();
            this.mHasActionBar = true;
            return true;
          } 
          throwFeatureRequestIfSubDecorInstalled();
          this.mOverlayActionMode = true;
          return true;
        } 
        throwFeatureRequestIfSubDecorInstalled();
        this.mFeatureIndeterminateProgress = true;
        return true;
      } 
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureProgress = true;
      return true;
    } 
    throwFeatureRequestIfSubDecorInstalled();
    this.mWindowNoTitle = true;
    return true;
  }
  
  public void setContentView(int paramInt) {
    ensureSubDecor();
    ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    viewGroup.removeAllViews();
    LayoutInflater.from(this.mContext).inflate(paramInt, viewGroup);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setContentView(View paramView) {
    ensureSubDecor();
    ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    viewGroup.removeAllViews();
    viewGroup.addView(paramView);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    ensureSubDecor();
    ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    viewGroup.removeAllViews();
    viewGroup.addView(paramView, paramLayoutParams);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setHandleNativeActionModesEnabled(boolean paramBoolean) {
    this.mHandleNativeActionModes = paramBoolean;
  }
  
  public void setLocalNightMode(int paramInt) {
    if (this.mLocalNightMode != paramInt) {
      this.mLocalNightMode = paramInt;
      if (this.mBaseContextAttached)
        applyDayNight(); 
    } 
  }
  
  public void setSupportActionBar(Toolbar paramToolbar) {
    if (!(this.mHost instanceof Activity))
      return; 
    ActionBar actionBar = getSupportActionBar();
    if (!(actionBar instanceof WindowDecorActionBar)) {
      this.mMenuInflater = null;
      if (actionBar != null)
        actionBar.onDestroy(); 
      if (paramToolbar != null) {
        ToolbarActionBar toolbarActionBar = new ToolbarActionBar(paramToolbar, getTitle(), (Window.Callback)this.mAppCompatWindowCallback);
        this.mActionBar = toolbarActionBar;
        this.mWindow.setCallback(toolbarActionBar.getWrappedWindowCallback());
      } else {
        this.mActionBar = null;
        this.mWindow.setCallback((Window.Callback)this.mAppCompatWindowCallback);
      } 
      invalidateOptionsMenu();
      return;
    } 
    throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
  }
  
  public void setTheme(int paramInt) {
    this.mThemeResId = paramInt;
  }
  
  public final void setTitle(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    DecorContentParent decorContentParent = this.mDecorContentParent;
    if (decorContentParent != null) {
      decorContentParent.setWindowTitle(paramCharSequence);
    } else if (peekSupportActionBar() != null) {
      peekSupportActionBar().setWindowTitle(paramCharSequence);
    } else {
      TextView textView = this.mTitleView;
      if (textView != null)
        textView.setText(paramCharSequence); 
    } 
  }
  
  final boolean shouldAnimateActionModeView() {
    if (this.mSubDecorInstalled) {
      ViewGroup viewGroup = this.mSubDecor;
      if (viewGroup != null && ViewCompat.isLaidOut((View)viewGroup))
        return true; 
    } 
    return false;
  }
  
  public ActionMode startSupportActionMode(ActionMode.Callback paramCallback) {
    if (paramCallback != null) {
      ActionMode actionMode = this.mActionMode;
      if (actionMode != null)
        actionMode.finish(); 
      paramCallback = new ActionModeCallbackWrapperV9(paramCallback);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
        ActionMode actionMode1 = actionBar.startActionMode(paramCallback);
        this.mActionMode = actionMode1;
        if (actionMode1 != null) {
          AppCompatCallback appCompatCallback = this.mAppCompatCallback;
          if (appCompatCallback != null)
            appCompatCallback.onSupportActionModeStarted(actionMode1); 
        } 
      } 
      if (this.mActionMode == null)
        this.mActionMode = startSupportActionModeFromWindow(paramCallback); 
      return this.mActionMode;
    } 
    throw new IllegalArgumentException("ActionMode callback can not be null.");
  }
  
  ActionMode startSupportActionModeFromWindow(ActionMode.Callback paramCallback) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual endOnGoingFadeAnimation : ()V
    //   4: aload_0
    //   5: getfield mActionMode : Landroidx/appcompat/view/ActionMode;
    //   8: astore #4
    //   10: aload #4
    //   12: ifnull -> 20
    //   15: aload #4
    //   17: invokevirtual finish : ()V
    //   20: aload_1
    //   21: astore #4
    //   23: aload_1
    //   24: instanceof androidx/appcompat/app/AppCompatDelegateImpl$ActionModeCallbackWrapperV9
    //   27: ifne -> 41
    //   30: new androidx/appcompat/app/AppCompatDelegateImpl$ActionModeCallbackWrapperV9
    //   33: dup
    //   34: aload_0
    //   35: aload_1
    //   36: invokespecial <init> : (Landroidx/appcompat/app/AppCompatDelegateImpl;Landroidx/appcompat/view/ActionMode$Callback;)V
    //   39: astore #4
    //   41: aload_0
    //   42: getfield mAppCompatCallback : Landroidx/appcompat/app/AppCompatCallback;
    //   45: astore_1
    //   46: aload_1
    //   47: ifnull -> 69
    //   50: aload_0
    //   51: getfield mIsDestroyed : Z
    //   54: ifne -> 69
    //   57: aload_1
    //   58: aload #4
    //   60: invokeinterface onWindowStartingSupportActionMode : (Landroidx/appcompat/view/ActionMode$Callback;)Landroidx/appcompat/view/ActionMode;
    //   65: astore_1
    //   66: goto -> 71
    //   69: aconst_null
    //   70: astore_1
    //   71: aload_1
    //   72: ifnull -> 83
    //   75: aload_0
    //   76: aload_1
    //   77: putfield mActionMode : Landroidx/appcompat/view/ActionMode;
    //   80: goto -> 574
    //   83: aload_0
    //   84: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   87: astore_1
    //   88: iconst_1
    //   89: istore_3
    //   90: aload_1
    //   91: ifnonnull -> 355
    //   94: aload_0
    //   95: getfield mIsFloating : Z
    //   98: ifeq -> 315
    //   101: new android/util/TypedValue
    //   104: dup
    //   105: invokespecial <init> : ()V
    //   108: astore #5
    //   110: aload_0
    //   111: getfield mContext : Landroid/content/Context;
    //   114: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   117: astore_1
    //   118: aload_1
    //   119: getstatic androidx/appcompat/R$attr.actionBarTheme : I
    //   122: aload #5
    //   124: iconst_1
    //   125: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   128: pop
    //   129: aload #5
    //   131: getfield resourceId : I
    //   134: ifeq -> 191
    //   137: aload_0
    //   138: getfield mContext : Landroid/content/Context;
    //   141: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   144: invokevirtual newTheme : ()Landroid/content/res/Resources$Theme;
    //   147: astore #6
    //   149: aload #6
    //   151: aload_1
    //   152: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   155: aload #6
    //   157: aload #5
    //   159: getfield resourceId : I
    //   162: iconst_1
    //   163: invokevirtual applyStyle : (IZ)V
    //   166: new androidx/appcompat/view/ContextThemeWrapper
    //   169: dup
    //   170: aload_0
    //   171: getfield mContext : Landroid/content/Context;
    //   174: iconst_0
    //   175: invokespecial <init> : (Landroid/content/Context;I)V
    //   178: astore_1
    //   179: aload_1
    //   180: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   183: aload #6
    //   185: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   188: goto -> 196
    //   191: aload_0
    //   192: getfield mContext : Landroid/content/Context;
    //   195: astore_1
    //   196: aload_0
    //   197: new androidx/appcompat/widget/ActionBarContextView
    //   200: dup
    //   201: aload_1
    //   202: invokespecial <init> : (Landroid/content/Context;)V
    //   205: putfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   208: new android/widget/PopupWindow
    //   211: dup
    //   212: aload_1
    //   213: aconst_null
    //   214: getstatic androidx/appcompat/R$attr.actionModePopupWindowStyle : I
    //   217: invokespecial <init> : (Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   220: astore #6
    //   222: aload_0
    //   223: aload #6
    //   225: putfield mActionModePopup : Landroid/widget/PopupWindow;
    //   228: aload #6
    //   230: iconst_2
    //   231: invokestatic setWindowLayoutType : (Landroid/widget/PopupWindow;I)V
    //   234: aload_0
    //   235: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   238: aload_0
    //   239: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   242: invokevirtual setContentView : (Landroid/view/View;)V
    //   245: aload_0
    //   246: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   249: iconst_m1
    //   250: invokevirtual setWidth : (I)V
    //   253: aload_1
    //   254: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   257: getstatic androidx/appcompat/R$attr.actionBarSize : I
    //   260: aload #5
    //   262: iconst_1
    //   263: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   266: pop
    //   267: aload #5
    //   269: getfield data : I
    //   272: aload_1
    //   273: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   276: invokevirtual getDisplayMetrics : ()Landroid/util/DisplayMetrics;
    //   279: invokestatic complexToDimensionPixelSize : (ILandroid/util/DisplayMetrics;)I
    //   282: istore_2
    //   283: aload_0
    //   284: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   287: iload_2
    //   288: invokevirtual setContentHeight : (I)V
    //   291: aload_0
    //   292: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   295: bipush #-2
    //   297: invokevirtual setHeight : (I)V
    //   300: aload_0
    //   301: new androidx/appcompat/app/AppCompatDelegateImpl$6
    //   304: dup
    //   305: aload_0
    //   306: invokespecial <init> : (Landroidx/appcompat/app/AppCompatDelegateImpl;)V
    //   309: putfield mShowActionModePopup : Ljava/lang/Runnable;
    //   312: goto -> 355
    //   315: aload_0
    //   316: getfield mSubDecor : Landroid/view/ViewGroup;
    //   319: getstatic androidx/appcompat/R$id.action_mode_bar_stub : I
    //   322: invokevirtual findViewById : (I)Landroid/view/View;
    //   325: checkcast androidx/appcompat/widget/ViewStubCompat
    //   328: astore_1
    //   329: aload_1
    //   330: ifnull -> 355
    //   333: aload_1
    //   334: aload_0
    //   335: invokevirtual getActionBarThemedContext : ()Landroid/content/Context;
    //   338: invokestatic from : (Landroid/content/Context;)Landroid/view/LayoutInflater;
    //   341: invokevirtual setLayoutInflater : (Landroid/view/LayoutInflater;)V
    //   344: aload_0
    //   345: aload_1
    //   346: invokevirtual inflate : ()Landroid/view/View;
    //   349: checkcast androidx/appcompat/widget/ActionBarContextView
    //   352: putfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   355: aload_0
    //   356: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   359: ifnull -> 574
    //   362: aload_0
    //   363: invokevirtual endOnGoingFadeAnimation : ()V
    //   366: aload_0
    //   367: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   370: invokevirtual killMode : ()V
    //   373: aload_0
    //   374: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   377: invokevirtual getContext : ()Landroid/content/Context;
    //   380: astore_1
    //   381: aload_0
    //   382: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   385: astore #5
    //   387: aload_0
    //   388: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   391: ifnonnull -> 397
    //   394: goto -> 399
    //   397: iconst_0
    //   398: istore_3
    //   399: new androidx/appcompat/view/StandaloneActionMode
    //   402: dup
    //   403: aload_1
    //   404: aload #5
    //   406: aload #4
    //   408: iload_3
    //   409: invokespecial <init> : (Landroid/content/Context;Landroidx/appcompat/widget/ActionBarContextView;Landroidx/appcompat/view/ActionMode$Callback;Z)V
    //   412: astore_1
    //   413: aload #4
    //   415: aload_1
    //   416: aload_1
    //   417: invokevirtual getMenu : ()Landroid/view/Menu;
    //   420: invokeinterface onCreateActionMode : (Landroidx/appcompat/view/ActionMode;Landroid/view/Menu;)Z
    //   425: ifeq -> 569
    //   428: aload_1
    //   429: invokevirtual invalidate : ()V
    //   432: aload_0
    //   433: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   436: aload_1
    //   437: invokevirtual initForMode : (Landroidx/appcompat/view/ActionMode;)V
    //   440: aload_0
    //   441: aload_1
    //   442: putfield mActionMode : Landroidx/appcompat/view/ActionMode;
    //   445: aload_0
    //   446: invokevirtual shouldAnimateActionModeView : ()Z
    //   449: ifeq -> 493
    //   452: aload_0
    //   453: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   456: fconst_0
    //   457: invokevirtual setAlpha : (F)V
    //   460: aload_0
    //   461: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   464: invokestatic animate : (Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;
    //   467: fconst_1
    //   468: invokevirtual alpha : (F)Landroidx/core/view/ViewPropertyAnimatorCompat;
    //   471: astore_1
    //   472: aload_0
    //   473: aload_1
    //   474: putfield mFadeAnim : Landroidx/core/view/ViewPropertyAnimatorCompat;
    //   477: aload_1
    //   478: new androidx/appcompat/app/AppCompatDelegateImpl$7
    //   481: dup
    //   482: aload_0
    //   483: invokespecial <init> : (Landroidx/appcompat/app/AppCompatDelegateImpl;)V
    //   486: invokevirtual setListener : (Landroidx/core/view/ViewPropertyAnimatorListener;)Landroidx/core/view/ViewPropertyAnimatorCompat;
    //   489: pop
    //   490: goto -> 544
    //   493: aload_0
    //   494: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   497: fconst_1
    //   498: invokevirtual setAlpha : (F)V
    //   501: aload_0
    //   502: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   505: iconst_0
    //   506: invokevirtual setVisibility : (I)V
    //   509: aload_0
    //   510: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   513: bipush #32
    //   515: invokevirtual sendAccessibilityEvent : (I)V
    //   518: aload_0
    //   519: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   522: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   525: instanceof android/view/View
    //   528: ifeq -> 544
    //   531: aload_0
    //   532: getfield mActionModeView : Landroidx/appcompat/widget/ActionBarContextView;
    //   535: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   538: checkcast android/view/View
    //   541: invokestatic requestApplyInsets : (Landroid/view/View;)V
    //   544: aload_0
    //   545: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   548: ifnull -> 574
    //   551: aload_0
    //   552: getfield mWindow : Landroid/view/Window;
    //   555: invokevirtual getDecorView : ()Landroid/view/View;
    //   558: aload_0
    //   559: getfield mShowActionModePopup : Ljava/lang/Runnable;
    //   562: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   565: pop
    //   566: goto -> 574
    //   569: aload_0
    //   570: aconst_null
    //   571: putfield mActionMode : Landroidx/appcompat/view/ActionMode;
    //   574: aload_0
    //   575: getfield mActionMode : Landroidx/appcompat/view/ActionMode;
    //   578: astore #4
    //   580: aload #4
    //   582: ifnull -> 602
    //   585: aload_0
    //   586: getfield mAppCompatCallback : Landroidx/appcompat/app/AppCompatCallback;
    //   589: astore_1
    //   590: aload_1
    //   591: ifnull -> 602
    //   594: aload_1
    //   595: aload #4
    //   597: invokeinterface onSupportActionModeStarted : (Landroidx/appcompat/view/ActionMode;)V
    //   602: aload_0
    //   603: getfield mActionMode : Landroidx/appcompat/view/ActionMode;
    //   606: areturn
    //   607: astore_1
    //   608: goto -> 69
    // Exception table:
    //   from	to	target	type
    //   57	66	607	java/lang/AbstractMethodError
  }
  
  final int updateStatusGuard(WindowInsetsCompat paramWindowInsetsCompat, Rect paramRect) {
    int i;
    int j;
    boolean bool1;
    boolean bool2 = false;
    if (paramWindowInsetsCompat != null) {
      i = paramWindowInsetsCompat.getSystemWindowInsetTop();
    } else if (paramRect != null) {
      i = paramRect.top;
    } else {
      i = 0;
    } 
    ActionBarContextView actionBarContextView = this.mActionModeView;
    if (actionBarContextView != null && actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
      boolean bool;
      ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
      boolean bool3 = this.mActionModeView.isShown();
      int k = 1;
      bool1 = true;
      if (bool3) {
        int m;
        if (this.mTempRect1 == null) {
          this.mTempRect1 = new Rect();
          this.mTempRect2 = new Rect();
        } 
        Rect rect1 = this.mTempRect1;
        Rect rect2 = this.mTempRect2;
        if (paramWindowInsetsCompat == null) {
          rect1.set(paramRect);
        } else {
          rect1.set(paramWindowInsetsCompat.getSystemWindowInsetLeft(), paramWindowInsetsCompat.getSystemWindowInsetTop(), paramWindowInsetsCompat.getSystemWindowInsetRight(), paramWindowInsetsCompat.getSystemWindowInsetBottom());
        } 
        ViewUtils.computeFitSystemWindows((View)this.mSubDecor, rect1, rect2);
        int n = rect1.top;
        int i1 = rect1.left;
        bool = rect1.right;
        paramWindowInsetsCompat = ViewCompat.getRootWindowInsets((View)this.mSubDecor);
        if (paramWindowInsetsCompat == null) {
          k = 0;
        } else {
          k = paramWindowInsetsCompat.getSystemWindowInsetLeft();
        } 
        if (paramWindowInsetsCompat == null) {
          m = 0;
        } else {
          m = paramWindowInsetsCompat.getSystemWindowInsetRight();
        } 
        if (marginLayoutParams.topMargin != n || marginLayoutParams.leftMargin != i1 || marginLayoutParams.rightMargin != bool) {
          marginLayoutParams.topMargin = n;
          marginLayoutParams.leftMargin = i1;
          marginLayoutParams.rightMargin = bool;
          bool = true;
        } else {
          bool = false;
        } 
        if (n > 0 && this.mStatusGuard == null) {
          View view1 = new View(this.mContext);
          this.mStatusGuard = view1;
          view1.setVisibility(8);
          FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
          layoutParams.leftMargin = k;
          layoutParams.rightMargin = m;
          this.mSubDecor.addView(this.mStatusGuard, -1, (ViewGroup.LayoutParams)layoutParams);
        } else {
          View view1 = this.mStatusGuard;
          if (view1 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams1 = (ViewGroup.MarginLayoutParams)view1.getLayoutParams();
            if (marginLayoutParams1.height != marginLayoutParams.topMargin || marginLayoutParams1.leftMargin != k || marginLayoutParams1.rightMargin != m) {
              marginLayoutParams1.height = marginLayoutParams.topMargin;
              marginLayoutParams1.leftMargin = k;
              marginLayoutParams1.rightMargin = m;
              this.mStatusGuard.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams1);
            } 
          } 
        } 
        if (this.mStatusGuard != null) {
          m = bool1;
        } else {
          m = 0;
        } 
        if (m != 0 && this.mStatusGuard.getVisibility() != 0)
          updateStatusGuardColor(this.mStatusGuard); 
        k = i;
        if (!this.mOverlayActionMode) {
          k = i;
          if (m != 0)
            k = 0; 
        } 
        i = k;
        k = bool;
        bool = m;
      } else if (marginLayoutParams.topMargin != 0) {
        marginLayoutParams.topMargin = 0;
        bool = false;
      } else {
        bool = false;
        k = 0;
      } 
      j = i;
      bool1 = bool;
      if (k != 0) {
        this.mActionModeView.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams);
        j = i;
        bool1 = bool;
      } 
    } else {
      bool1 = false;
      j = i;
    } 
    View view = this.mStatusGuard;
    if (view != null) {
      if (bool1) {
        i = bool2;
      } else {
        i = 8;
      } 
      view.setVisibility(i);
    } 
    return j;
  }
  
  private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
    final AppCompatDelegateImpl this$0;
    
    public Context getActionBarThemedContext() {
      return AppCompatDelegateImpl.this.getActionBarThemedContext();
    }
    
    public Drawable getThemeUpIndicator() {
      TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[] { R.attr.homeAsUpIndicator });
      Drawable drawable = tintTypedArray.getDrawable(0);
      tintTypedArray.recycle();
      return drawable;
    }
    
    public boolean isNavigationVisible() {
      boolean bool;
      ActionBar actionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      if (actionBar != null && (actionBar.getDisplayOptions() & 0x4) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void setActionBarDescription(int param1Int) {
      ActionBar actionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      if (actionBar != null)
        actionBar.setHomeActionContentDescription(param1Int); 
    }
    
    public void setActionBarUpIndicator(Drawable param1Drawable, int param1Int) {
      ActionBar actionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      if (actionBar != null) {
        actionBar.setHomeAsUpIndicator(param1Drawable);
        actionBar.setHomeActionContentDescription(param1Int);
      } 
    }
  }
  
  private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
    final AppCompatDelegateImpl this$0;
    
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {
      AppCompatDelegateImpl.this.checkCloseActionMenu(param1MenuBuilder);
    }
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      Window.Callback callback = AppCompatDelegateImpl.this.getWindowCallback();
      if (callback != null)
        callback.onMenuOpened(108, (Menu)param1MenuBuilder); 
      return true;
    }
  }
  
  class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
    private ActionMode.Callback mWrapped;
    
    final AppCompatDelegateImpl this$0;
    
    public ActionModeCallbackWrapperV9(ActionMode.Callback param1Callback) {
      this.mWrapped = param1Callback;
    }
    
    public boolean onActionItemClicked(ActionMode param1ActionMode, MenuItem param1MenuItem) {
      return this.mWrapped.onActionItemClicked(param1ActionMode, param1MenuItem);
    }
    
    public boolean onCreateActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      return this.mWrapped.onCreateActionMode(param1ActionMode, param1Menu);
    }
    
    public void onDestroyActionMode(ActionMode param1ActionMode) {
      this.mWrapped.onDestroyActionMode(param1ActionMode);
      if (AppCompatDelegateImpl.this.mActionModePopup != null)
        AppCompatDelegateImpl.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup); 
      if (AppCompatDelegateImpl.this.mActionModeView != null) {
        AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
        AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
        appCompatDelegateImpl.mFadeAnim = ViewCompat.animate((View)appCompatDelegateImpl.mActionModeView).alpha(0.0F);
        AppCompatDelegateImpl.this.mFadeAnim.setListener((ViewPropertyAnimatorListener)new ViewPropertyAnimatorListenerAdapter() {
              final AppCompatDelegateImpl.ActionModeCallbackWrapperV9 this$1;
              
              public void onAnimationEnd(View param2View) {
                AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                if (AppCompatDelegateImpl.this.mActionModePopup != null) {
                  AppCompatDelegateImpl.this.mActionModePopup.dismiss();
                } else if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                  ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
                } 
                AppCompatDelegateImpl.this.mActionModeView.removeAllViews();
                AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                AppCompatDelegateImpl.this.mFadeAnim = null;
                ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mSubDecor);
              }
            });
      } 
      if (AppCompatDelegateImpl.this.mAppCompatCallback != null)
        AppCompatDelegateImpl.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImpl.this.mActionMode); 
      AppCompatDelegateImpl.this.mActionMode = null;
      ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mSubDecor);
    }
    
    public boolean onPrepareActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mSubDecor);
      return this.mWrapped.onPrepareActionMode(param1ActionMode, param1Menu);
    }
  }
  
  class null extends ViewPropertyAnimatorListenerAdapter {
    final AppCompatDelegateImpl.ActionModeCallbackWrapperV9 this$1;
    
    public void onAnimationEnd(View param1View) {
      AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
      if (AppCompatDelegateImpl.this.mActionModePopup != null) {
        AppCompatDelegateImpl.this.mActionModePopup.dismiss();
      } else if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
        ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
      } 
      AppCompatDelegateImpl.this.mActionModeView.removeAllViews();
      AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
      AppCompatDelegateImpl.this.mFadeAnim = null;
      ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mSubDecor);
    }
  }
  
  class AppCompatWindowCallback extends WindowCallbackWrapper {
    final AppCompatDelegateImpl this$0;
    
    AppCompatWindowCallback(Window.Callback param1Callback) {
      super(param1Callback);
    }
    
    public boolean dispatchKeyEvent(KeyEvent param1KeyEvent) {
      return (AppCompatDelegateImpl.this.dispatchKeyEvent(param1KeyEvent) || super.dispatchKeyEvent(param1KeyEvent));
    }
    
    public boolean dispatchKeyShortcutEvent(KeyEvent param1KeyEvent) {
      return (super.dispatchKeyShortcutEvent(param1KeyEvent) || AppCompatDelegateImpl.this.onKeyShortcut(param1KeyEvent.getKeyCode(), param1KeyEvent));
    }
    
    public void onContentChanged() {}
    
    public boolean onCreatePanelMenu(int param1Int, Menu param1Menu) {
      return (param1Int == 0 && !(param1Menu instanceof MenuBuilder)) ? false : super.onCreatePanelMenu(param1Int, param1Menu);
    }
    
    public boolean onMenuOpened(int param1Int, Menu param1Menu) {
      super.onMenuOpened(param1Int, param1Menu);
      AppCompatDelegateImpl.this.onMenuOpened(param1Int);
      return true;
    }
    
    public void onPanelClosed(int param1Int, Menu param1Menu) {
      super.onPanelClosed(param1Int, param1Menu);
      AppCompatDelegateImpl.this.onPanelClosed(param1Int);
    }
    
    public boolean onPreparePanel(int param1Int, View param1View, Menu param1Menu) {
      MenuBuilder menuBuilder;
      if (param1Menu instanceof MenuBuilder) {
        menuBuilder = (MenuBuilder)param1Menu;
      } else {
        menuBuilder = null;
      } 
      if (param1Int == 0 && menuBuilder == null)
        return false; 
      if (menuBuilder != null)
        menuBuilder.setOverrideVisibleItems(true); 
      boolean bool = super.onPreparePanel(param1Int, param1View, param1Menu);
      if (menuBuilder != null)
        menuBuilder.setOverrideVisibleItems(false); 
      return bool;
    }
    
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> param1List, Menu param1Menu, int param1Int) {
      AppCompatDelegateImpl.PanelFeatureState panelFeatureState = AppCompatDelegateImpl.this.getPanelState(0, true);
      if (panelFeatureState != null && panelFeatureState.menu != null) {
        super.onProvideKeyboardShortcuts(param1List, (Menu)panelFeatureState.menu, param1Int);
      } else {
        super.onProvideKeyboardShortcuts(param1List, param1Menu, param1Int);
      } 
    }
    
    public ActionMode onWindowStartingActionMode(ActionMode.Callback param1Callback) {
      return (Build.VERSION.SDK_INT >= 23) ? null : (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() ? startAsSupportActionMode(param1Callback) : super.onWindowStartingActionMode(param1Callback));
    }
    
    public ActionMode onWindowStartingActionMode(ActionMode.Callback param1Callback, int param1Int) {
      return (!AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() || param1Int != 0) ? super.onWindowStartingActionMode(param1Callback, param1Int) : startAsSupportActionMode(param1Callback);
    }
    
    final ActionMode startAsSupportActionMode(ActionMode.Callback param1Callback) {
      SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, param1Callback);
      ActionMode actionMode = AppCompatDelegateImpl.this.startSupportActionMode((ActionMode.Callback)callbackWrapper);
      return (actionMode != null) ? callbackWrapper.getActionModeWrapper(actionMode) : null;
    }
  }
  
  private class AutoBatteryNightModeManager extends AutoNightModeManager {
    private final PowerManager mPowerManager;
    
    final AppCompatDelegateImpl this$0;
    
    AutoBatteryNightModeManager(Context param1Context) {
      this.mPowerManager = (PowerManager)param1Context.getApplicationContext().getSystemService("power");
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver() {
      if (Build.VERSION.SDK_INT >= 21) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        return intentFilter;
      } 
      return null;
    }
    
    public int getApplyableNightMode() {
      int i = Build.VERSION.SDK_INT;
      byte b2 = 1;
      byte b1 = b2;
      if (i >= 21) {
        b1 = b2;
        if (this.mPowerManager.isPowerSaveMode())
          b1 = 2; 
      } 
      return b1;
    }
    
    public void onChange() {
      AppCompatDelegateImpl.this.applyDayNight();
    }
  }
  
  abstract class AutoNightModeManager {
    private BroadcastReceiver mReceiver;
    
    final AppCompatDelegateImpl this$0;
    
    void cleanup() {
      if (this.mReceiver != null) {
        try {
          AppCompatDelegateImpl.this.mContext.unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException illegalArgumentException) {}
        this.mReceiver = null;
      } 
    }
    
    abstract IntentFilter createIntentFilterForBroadcastReceiver();
    
    abstract int getApplyableNightMode();
    
    boolean isListening() {
      boolean bool;
      if (this.mReceiver != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    abstract void onChange();
    
    void setup() {
      cleanup();
      IntentFilter intentFilter = createIntentFilterForBroadcastReceiver();
      if (intentFilter != null && intentFilter.countActions() != 0) {
        if (this.mReceiver == null)
          this.mReceiver = new BroadcastReceiver() {
              final AppCompatDelegateImpl.AutoNightModeManager this$1;
              
              public void onReceive(Context param2Context, Intent param2Intent) {
                AppCompatDelegateImpl.AutoNightModeManager.this.onChange();
              }
            }; 
        AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, intentFilter);
      } 
    }
  }
  
  class null extends BroadcastReceiver {
    final AppCompatDelegateImpl.AutoNightModeManager this$1;
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      this.this$1.onChange();
    }
  }
  
  private class AutoTimeNightModeManager extends AutoNightModeManager {
    private final TwilightManager mTwilightManager;
    
    final AppCompatDelegateImpl this$0;
    
    AutoTimeNightModeManager(TwilightManager param1TwilightManager) {
      this.mTwilightManager = param1TwilightManager;
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.TIME_SET");
      intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
      intentFilter.addAction("android.intent.action.TIME_TICK");
      return intentFilter;
    }
    
    public int getApplyableNightMode() {
      boolean bool;
      if (this.mTwilightManager.isNight()) {
        bool = true;
      } else {
        bool = true;
      } 
      return bool;
    }
    
    public void onChange() {
      AppCompatDelegateImpl.this.applyDayNight();
    }
  }
  
  static class ConfigurationImplApi17 {
    static void generateConfigDelta_densityDpi(Configuration param1Configuration1, Configuration param1Configuration2, Configuration param1Configuration3) {
      if (param1Configuration1.densityDpi != param1Configuration2.densityDpi)
        param1Configuration3.densityDpi = param1Configuration2.densityDpi; 
    }
  }
  
  static class ConfigurationImplApi24 {
    static void generateConfigDelta_locale(Configuration param1Configuration1, Configuration param1Configuration2, Configuration param1Configuration3) {
      LocaleList localeList2 = param1Configuration1.getLocales();
      LocaleList localeList1 = param1Configuration2.getLocales();
      if (!localeList2.equals(localeList1)) {
        param1Configuration3.setLocales(localeList1);
        param1Configuration3.locale = param1Configuration2.locale;
      } 
    }
  }
  
  static class ConfigurationImplApi26 {
    static void generateConfigDelta_colorMode(Configuration param1Configuration1, Configuration param1Configuration2, Configuration param1Configuration3) {
      if ((param1Configuration1.colorMode & 0x3) != (param1Configuration2.colorMode & 0x3))
        param1Configuration3.colorMode |= param1Configuration2.colorMode & 0x3; 
      if ((param1Configuration1.colorMode & 0xC) != (param1Configuration2.colorMode & 0xC))
        param1Configuration3.colorMode |= param1Configuration2.colorMode & 0xC; 
    }
  }
  
  private static class ContextThemeWrapperCompatApi17Impl {
    static void applyOverrideConfiguration(ContextThemeWrapper param1ContextThemeWrapper, Configuration param1Configuration) {
      param1ContextThemeWrapper.applyOverrideConfiguration(param1Configuration);
    }
  }
  
  private class ListMenuDecorView extends ContentFrameLayout {
    final AppCompatDelegateImpl this$0;
    
    public ListMenuDecorView(Context param1Context) {
      super(param1Context);
    }
    
    private boolean isOutOfBounds(int param1Int1, int param1Int2) {
      return (param1Int1 < -5 || param1Int2 < -5 || param1Int1 > getWidth() + 5 || param1Int2 > getHeight() + 5);
    }
    
    public boolean dispatchKeyEvent(KeyEvent param1KeyEvent) {
      return (AppCompatDelegateImpl.this.dispatchKeyEvent(param1KeyEvent) || super.dispatchKeyEvent(param1KeyEvent));
    }
    
    public boolean onInterceptTouchEvent(MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0 && isOutOfBounds((int)param1MotionEvent.getX(), (int)param1MotionEvent.getY())) {
        AppCompatDelegateImpl.this.closePanel(0);
        return true;
      } 
      return super.onInterceptTouchEvent(param1MotionEvent);
    }
    
    public void setBackgroundResource(int param1Int) {
      setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), param1Int));
    }
  }
  
  protected static final class PanelFeatureState {
    int background;
    
    View createdPanelView;
    
    ViewGroup decorView;
    
    int featureId;
    
    Bundle frozenActionViewState;
    
    Bundle frozenMenuState;
    
    int gravity;
    
    boolean isHandled;
    
    boolean isOpen;
    
    boolean isPrepared;
    
    ListMenuPresenter listMenuPresenter;
    
    Context listPresenterContext;
    
    MenuBuilder menu;
    
    public boolean qwertyMode;
    
    boolean refreshDecorView;
    
    boolean refreshMenuContent;
    
    View shownPanelView;
    
    boolean wasLastOpen;
    
    int windowAnimations;
    
    int x;
    
    int y;
    
    PanelFeatureState(int param1Int) {
      this.featureId = param1Int;
      this.refreshDecorView = false;
    }
    
    void applyFrozenState() {
      MenuBuilder menuBuilder = this.menu;
      if (menuBuilder != null) {
        Bundle bundle = this.frozenMenuState;
        if (bundle != null) {
          menuBuilder.restorePresenterStates(bundle);
          this.frozenMenuState = null;
        } 
      } 
    }
    
    public void clearMenuPresenters() {
      MenuBuilder menuBuilder = this.menu;
      if (menuBuilder != null)
        menuBuilder.removeMenuPresenter((MenuPresenter)this.listMenuPresenter); 
      this.listMenuPresenter = null;
    }
    
    MenuView getListMenuView(MenuPresenter.Callback param1Callback) {
      if (this.menu == null)
        return null; 
      if (this.listMenuPresenter == null) {
        ListMenuPresenter listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
        this.listMenuPresenter = listMenuPresenter;
        listMenuPresenter.setCallback(param1Callback);
        this.menu.addMenuPresenter((MenuPresenter)this.listMenuPresenter);
      } 
      return this.listMenuPresenter.getMenuView(this.decorView);
    }
    
    public boolean hasPanelItems() {
      View view = this.shownPanelView;
      boolean bool = false;
      if (view == null)
        return false; 
      if (this.createdPanelView != null)
        return true; 
      if (this.listMenuPresenter.getAdapter().getCount() > 0)
        bool = true; 
      return bool;
    }
    
    void onRestoreInstanceState(Parcelable param1Parcelable) {
      param1Parcelable = param1Parcelable;
      this.featureId = ((SavedState)param1Parcelable).featureId;
      this.wasLastOpen = ((SavedState)param1Parcelable).isOpen;
      this.frozenMenuState = ((SavedState)param1Parcelable).menuState;
      this.shownPanelView = null;
      this.decorView = null;
    }
    
    Parcelable onSaveInstanceState() {
      SavedState savedState = new SavedState();
      savedState.featureId = this.featureId;
      savedState.isOpen = this.isOpen;
      if (this.menu != null) {
        savedState.menuState = new Bundle();
        this.menu.savePresenterStates(savedState.menuState);
      } 
      return savedState;
    }
    
    void setMenu(MenuBuilder param1MenuBuilder) {
      MenuBuilder menuBuilder = this.menu;
      if (param1MenuBuilder == menuBuilder)
        return; 
      if (menuBuilder != null)
        menuBuilder.removeMenuPresenter((MenuPresenter)this.listMenuPresenter); 
      this.menu = param1MenuBuilder;
      if (param1MenuBuilder != null) {
        ListMenuPresenter listMenuPresenter = this.listMenuPresenter;
        if (listMenuPresenter != null)
          param1MenuBuilder.addMenuPresenter((MenuPresenter)listMenuPresenter); 
      } 
    }
    
    void setStyle(Context param1Context) {
      TypedValue typedValue = new TypedValue();
      Resources.Theme theme = param1Context.getResources().newTheme();
      theme.setTo(param1Context.getTheme());
      theme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
      if (typedValue.resourceId != 0)
        theme.applyStyle(typedValue.resourceId, true); 
      theme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
      if (typedValue.resourceId != 0) {
        theme.applyStyle(typedValue.resourceId, true);
      } else {
        theme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      } 
      ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(param1Context, 0);
      contextThemeWrapper.getTheme().setTo(theme);
      this.listPresenterContext = (Context)contextThemeWrapper;
      TypedArray typedArray = contextThemeWrapper.obtainStyledAttributes(R.styleable.AppCompatTheme);
      this.background = typedArray.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
      this.windowAnimations = typedArray.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
      typedArray.recycle();
    }
    
    private static class SavedState implements Parcelable {
      public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
          public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel) {
            return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param3Parcel, null);
          }
          
          public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel, ClassLoader param3ClassLoader) {
            return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param3Parcel, param3ClassLoader);
          }
          
          public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int param3Int) {
            return new AppCompatDelegateImpl.PanelFeatureState.SavedState[param3Int];
          }
        };
      
      int featureId;
      
      boolean isOpen;
      
      Bundle menuState;
      
      static SavedState readFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
        SavedState savedState = new SavedState();
        savedState.featureId = param2Parcel.readInt();
        int i = param2Parcel.readInt();
        boolean bool = true;
        if (i != 1)
          bool = false; 
        savedState.isOpen = bool;
        if (bool)
          savedState.menuState = param2Parcel.readBundle(param2ClassLoader); 
        return savedState;
      }
      
      public int describeContents() {
        return 0;
      }
      
      public void writeToParcel(Parcel param2Parcel, int param2Int) {
        param2Parcel.writeInt(this.featureId);
        param2Parcel.writeInt(this.isOpen);
        if (this.isOpen)
          param2Parcel.writeBundle(this.menuState); 
      }
    }
    
    class null implements Parcelable.ClassLoaderCreator<SavedState> {
      public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param2Parcel) {
        return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param2Parcel, null);
      }
      
      public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
        return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param2Parcel, param2ClassLoader);
      }
      
      public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int param2Int) {
        return new AppCompatDelegateImpl.PanelFeatureState.SavedState[param2Int];
      }
    }
  }
  
  private static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel) {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param3Parcel, null);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel, ClassLoader param3ClassLoader) {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param3Parcel, param3ClassLoader);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int param3Int) {
          return new AppCompatDelegateImpl.PanelFeatureState.SavedState[param3Int];
        }
      };
    
    int featureId;
    
    boolean isOpen;
    
    Bundle menuState;
    
    static SavedState readFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      SavedState savedState = new SavedState();
      savedState.featureId = param1Parcel.readInt();
      int i = param1Parcel.readInt();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      savedState.isOpen = bool;
      if (bool)
        savedState.menuState = param1Parcel.readBundle(param1ClassLoader); 
      return savedState;
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.featureId);
      param1Parcel.writeInt(this.isOpen);
      if (this.isOpen)
        param1Parcel.writeBundle(this.menuState); 
    }
  }
  
  class null implements Parcelable.ClassLoaderCreator<PanelFeatureState.SavedState> {
    public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param1Parcel) {
      return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param1Parcel, null);
    }
    
    public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(param1Parcel, param1ClassLoader);
    }
    
    public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int param1Int) {
      return new AppCompatDelegateImpl.PanelFeatureState.SavedState[param1Int];
    }
  }
  
  private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
    final AppCompatDelegateImpl this$0;
    
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {
      boolean bool;
      MenuBuilder menuBuilder = param1MenuBuilder.getRootMenu();
      if (menuBuilder != param1MenuBuilder) {
        bool = true;
      } else {
        bool = false;
      } 
      AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
      if (bool)
        param1MenuBuilder = menuBuilder; 
      AppCompatDelegateImpl.PanelFeatureState panelFeatureState = appCompatDelegateImpl.findMenuPanel((Menu)param1MenuBuilder);
      if (panelFeatureState != null)
        if (bool) {
          AppCompatDelegateImpl.this.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, (Menu)menuBuilder);
          AppCompatDelegateImpl.this.closePanel(panelFeatureState, true);
        } else {
          AppCompatDelegateImpl.this.closePanel(panelFeatureState, param1Boolean);
        }  
    }
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      if (param1MenuBuilder == param1MenuBuilder.getRootMenu() && AppCompatDelegateImpl.this.mHasActionBar) {
        Window.Callback callback = AppCompatDelegateImpl.this.getWindowCallback();
        if (callback != null && !AppCompatDelegateImpl.this.mIsDestroyed)
          callback.onMenuOpened(108, (Menu)param1MenuBuilder); 
      } 
      return true;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\app\AppCompatDelegateImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */