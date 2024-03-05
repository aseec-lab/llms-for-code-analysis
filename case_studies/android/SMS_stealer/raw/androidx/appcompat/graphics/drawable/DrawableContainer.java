package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.SparseArray;
import androidx.core.graphics.drawable.DrawableCompat;

class DrawableContainer extends Drawable implements Drawable.Callback {
  private static final boolean DEBUG = false;
  
  private static final boolean DEFAULT_DITHER = true;
  
  private static final String TAG = "DrawableContainer";
  
  private int mAlpha = 255;
  
  private Runnable mAnimationRunnable;
  
  private BlockInvalidateCallback mBlockInvalidateCallback;
  
  private int mCurIndex = -1;
  
  private Drawable mCurrDrawable;
  
  private DrawableContainerState mDrawableContainerState;
  
  private long mEnterAnimationEnd;
  
  private long mExitAnimationEnd;
  
  private boolean mHasAlpha;
  
  private Rect mHotspotBounds;
  
  private Drawable mLastDrawable;
  
  private boolean mMutated;
  
  private void initializeDrawableForDisplay(Drawable paramDrawable) {
    if (this.mBlockInvalidateCallback == null)
      this.mBlockInvalidateCallback = new BlockInvalidateCallback(); 
    paramDrawable.setCallback(this.mBlockInvalidateCallback.wrap(paramDrawable.getCallback()));
    try {
      if (this.mDrawableContainerState.mEnterFadeDuration <= 0 && this.mHasAlpha)
        paramDrawable.setAlpha(this.mAlpha); 
      if (this.mDrawableContainerState.mHasColorFilter) {
        paramDrawable.setColorFilter(this.mDrawableContainerState.mColorFilter);
      } else {
        if (this.mDrawableContainerState.mHasTintList)
          DrawableCompat.setTintList(paramDrawable, this.mDrawableContainerState.mTintList); 
        if (this.mDrawableContainerState.mHasTintMode)
          DrawableCompat.setTintMode(paramDrawable, this.mDrawableContainerState.mTintMode); 
      } 
      paramDrawable.setVisible(isVisible(), true);
      paramDrawable.setDither(this.mDrawableContainerState.mDither);
      paramDrawable.setState(getState());
      paramDrawable.setLevel(getLevel());
      paramDrawable.setBounds(getBounds());
      if (Build.VERSION.SDK_INT >= 23)
        paramDrawable.setLayoutDirection(getLayoutDirection()); 
      if (Build.VERSION.SDK_INT >= 19)
        paramDrawable.setAutoMirrored(this.mDrawableContainerState.mAutoMirrored); 
      Rect rect = this.mHotspotBounds;
      if (Build.VERSION.SDK_INT >= 21 && rect != null)
        paramDrawable.setHotspotBounds(rect.left, rect.top, rect.right, rect.bottom); 
      return;
    } finally {
      paramDrawable.setCallback(this.mBlockInvalidateCallback.unwrap());
    } 
  }
  
  private boolean needsMirroring() {
    boolean bool1 = isAutoMirrored();
    boolean bool = true;
    if (!bool1 || DrawableCompat.getLayoutDirection(this) != 1)
      bool = false; 
    return bool;
  }
  
  static int resolveDensity(Resources paramResources, int paramInt) {
    if (paramResources != null)
      paramInt = (paramResources.getDisplayMetrics()).densityDpi; 
    int i = paramInt;
    if (paramInt == 0)
      i = 160; 
    return i;
  }
  
  void animate(boolean paramBoolean) {
    boolean bool2 = true;
    this.mHasAlpha = true;
    long l = SystemClock.uptimeMillis();
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null) {
      long l1 = this.mEnterAnimationEnd;
      if (l1 != 0L)
        if (l1 <= l) {
          drawable.setAlpha(this.mAlpha);
          this.mEnterAnimationEnd = 0L;
        } else {
          int i = (int)((l1 - l) * 255L) / this.mDrawableContainerState.mEnterFadeDuration;
          this.mCurrDrawable.setAlpha((255 - i) * this.mAlpha / 255);
          i = 1;
          drawable = this.mLastDrawable;
        }  
    } else {
      this.mEnterAnimationEnd = 0L;
    } 
    boolean bool1 = false;
    drawable = this.mLastDrawable;
  }
  
  public void applyTheme(Resources.Theme paramTheme) {
    this.mDrawableContainerState.applyTheme(paramTheme);
  }
  
  public boolean canApplyTheme() {
    return this.mDrawableContainerState.canApplyTheme();
  }
  
  void clearMutated() {
    this.mDrawableContainerState.clearMutated();
    this.mMutated = false;
  }
  
  DrawableContainerState cloneConstantState() {
    return this.mDrawableContainerState;
  }
  
  public void draw(Canvas paramCanvas) {
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null)
      drawable.draw(paramCanvas); 
    drawable = this.mLastDrawable;
    if (drawable != null)
      drawable.draw(paramCanvas); 
  }
  
  public int getAlpha() {
    return this.mAlpha;
  }
  
  public int getChangingConfigurations() {
    return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations();
  }
  
  public final Drawable.ConstantState getConstantState() {
    if (this.mDrawableContainerState.canConstantState()) {
      this.mDrawableContainerState.mChangingConfigurations = getChangingConfigurations();
      return this.mDrawableContainerState;
    } 
    return null;
  }
  
  public Drawable getCurrent() {
    return this.mCurrDrawable;
  }
  
  int getCurrentIndex() {
    return this.mCurIndex;
  }
  
  public void getHotspotBounds(Rect paramRect) {
    Rect rect = this.mHotspotBounds;
    if (rect != null) {
      paramRect.set(rect);
    } else {
      super.getHotspotBounds(paramRect);
    } 
  }
  
  public int getIntrinsicHeight() {
    byte b;
    if (this.mDrawableContainerState.isConstantSize())
      return this.mDrawableContainerState.getConstantHeight(); 
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null) {
      b = drawable.getIntrinsicHeight();
    } else {
      b = -1;
    } 
    return b;
  }
  
  public int getIntrinsicWidth() {
    byte b;
    if (this.mDrawableContainerState.isConstantSize())
      return this.mDrawableContainerState.getConstantWidth(); 
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null) {
      b = drawable.getIntrinsicWidth();
    } else {
      b = -1;
    } 
    return b;
  }
  
  public int getMinimumHeight() {
    boolean bool;
    if (this.mDrawableContainerState.isConstantSize())
      return this.mDrawableContainerState.getConstantMinimumHeight(); 
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null) {
      bool = drawable.getMinimumHeight();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getMinimumWidth() {
    boolean bool;
    if (this.mDrawableContainerState.isConstantSize())
      return this.mDrawableContainerState.getConstantMinimumWidth(); 
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null) {
      bool = drawable.getMinimumWidth();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getOpacity() {
    Drawable drawable = this.mCurrDrawable;
    return (drawable == null || !drawable.isVisible()) ? -2 : this.mDrawableContainerState.getOpacity();
  }
  
  public void getOutline(Outline paramOutline) {
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null)
      drawable.getOutline(paramOutline); 
  }
  
  public boolean getPadding(Rect paramRect) {
    boolean bool;
    Rect rect = this.mDrawableContainerState.getConstantPadding();
    if (rect != null) {
      paramRect.set(rect);
      int k = rect.left;
      int j = rect.top;
      int i = rect.bottom;
      if ((rect.right | k | j | i) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
    } else {
      Drawable drawable = this.mCurrDrawable;
      if (drawable != null) {
        bool = drawable.getPadding(paramRect);
      } else {
        bool = super.getPadding(paramRect);
      } 
    } 
    if (needsMirroring()) {
      int i = paramRect.left;
      paramRect.left = paramRect.right;
      paramRect.right = i;
    } 
    return bool;
  }
  
  public void invalidateDrawable(Drawable paramDrawable) {
    DrawableContainerState drawableContainerState = this.mDrawableContainerState;
    if (drawableContainerState != null)
      drawableContainerState.invalidateCache(); 
    if (paramDrawable == this.mCurrDrawable && getCallback() != null)
      getCallback().invalidateDrawable(this); 
  }
  
  public boolean isAutoMirrored() {
    return this.mDrawableContainerState.mAutoMirrored;
  }
  
  public boolean isStateful() {
    return this.mDrawableContainerState.isStateful();
  }
  
  public void jumpToCurrentState() {
    boolean bool1;
    Drawable drawable = this.mLastDrawable;
    boolean bool2 = true;
    if (drawable != null) {
      drawable.jumpToCurrentState();
      this.mLastDrawable = null;
      bool1 = true;
    } else {
      bool1 = false;
    } 
    drawable = this.mCurrDrawable;
    if (drawable != null) {
      drawable.jumpToCurrentState();
      if (this.mHasAlpha)
        this.mCurrDrawable.setAlpha(this.mAlpha); 
    } 
    if (this.mExitAnimationEnd != 0L) {
      this.mExitAnimationEnd = 0L;
      bool1 = true;
    } 
    if (this.mEnterAnimationEnd != 0L) {
      this.mEnterAnimationEnd = 0L;
      bool1 = bool2;
    } 
    if (bool1)
      invalidateSelf(); 
  }
  
  public Drawable mutate() {
    if (!this.mMutated && super.mutate() == this) {
      DrawableContainerState drawableContainerState = cloneConstantState();
      drawableContainerState.mutate();
      setConstantState(drawableContainerState);
      this.mMutated = true;
    } 
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect) {
    Drawable drawable = this.mLastDrawable;
    if (drawable != null)
      drawable.setBounds(paramRect); 
    drawable = this.mCurrDrawable;
    if (drawable != null)
      drawable.setBounds(paramRect); 
  }
  
  public boolean onLayoutDirectionChanged(int paramInt) {
    return this.mDrawableContainerState.setLayoutDirection(paramInt, getCurrentIndex());
  }
  
  protected boolean onLevelChange(int paramInt) {
    Drawable drawable = this.mLastDrawable;
    if (drawable != null)
      return drawable.setLevel(paramInt); 
    drawable = this.mCurrDrawable;
    return (drawable != null) ? drawable.setLevel(paramInt) : false;
  }
  
  protected boolean onStateChange(int[] paramArrayOfint) {
    Drawable drawable = this.mLastDrawable;
    if (drawable != null)
      return drawable.setState(paramArrayOfint); 
    drawable = this.mCurrDrawable;
    return (drawable != null) ? drawable.setState(paramArrayOfint) : false;
  }
  
  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong) {
    if (paramDrawable == this.mCurrDrawable && getCallback() != null)
      getCallback().scheduleDrawable(this, paramRunnable, paramLong); 
  }
  
  boolean selectDrawable(int paramInt) {
    if (paramInt == this.mCurIndex)
      return false; 
    long l = SystemClock.uptimeMillis();
    if (this.mDrawableContainerState.mExitFadeDuration > 0) {
      Drawable drawable = this.mLastDrawable;
      if (drawable != null)
        drawable.setVisible(false, false); 
      drawable = this.mCurrDrawable;
      if (drawable != null) {
        this.mLastDrawable = drawable;
        this.mExitAnimationEnd = this.mDrawableContainerState.mExitFadeDuration + l;
      } else {
        this.mLastDrawable = null;
        this.mExitAnimationEnd = 0L;
      } 
    } else {
      Drawable drawable = this.mCurrDrawable;
      if (drawable != null)
        drawable.setVisible(false, false); 
    } 
    if (paramInt >= 0 && paramInt < this.mDrawableContainerState.mNumChildren) {
      Drawable drawable = this.mDrawableContainerState.getChild(paramInt);
      this.mCurrDrawable = drawable;
      this.mCurIndex = paramInt;
      if (drawable != null) {
        if (this.mDrawableContainerState.mEnterFadeDuration > 0)
          this.mEnterAnimationEnd = l + this.mDrawableContainerState.mEnterFadeDuration; 
        initializeDrawableForDisplay(drawable);
      } 
    } else {
      this.mCurrDrawable = null;
      this.mCurIndex = -1;
    } 
    if (this.mEnterAnimationEnd != 0L || this.mExitAnimationEnd != 0L) {
      Runnable runnable = this.mAnimationRunnable;
      if (runnable == null) {
        this.mAnimationRunnable = new Runnable() {
            final DrawableContainer this$0;
            
            public void run() {
              DrawableContainer.this.animate(true);
              DrawableContainer.this.invalidateSelf();
            }
          };
      } else {
        unscheduleSelf(runnable);
      } 
      animate(true);
    } 
    invalidateSelf();
    return true;
  }
  
  public void setAlpha(int paramInt) {
    if (!this.mHasAlpha || this.mAlpha != paramInt) {
      this.mHasAlpha = true;
      this.mAlpha = paramInt;
      Drawable drawable = this.mCurrDrawable;
      if (drawable != null)
        if (this.mEnterAnimationEnd == 0L) {
          drawable.setAlpha(paramInt);
        } else {
          animate(false);
        }  
    } 
  }
  
  public void setAutoMirrored(boolean paramBoolean) {
    if (this.mDrawableContainerState.mAutoMirrored != paramBoolean) {
      this.mDrawableContainerState.mAutoMirrored = paramBoolean;
      Drawable drawable = this.mCurrDrawable;
      if (drawable != null)
        DrawableCompat.setAutoMirrored(drawable, this.mDrawableContainerState.mAutoMirrored); 
    } 
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    this.mDrawableContainerState.mHasColorFilter = true;
    if (this.mDrawableContainerState.mColorFilter != paramColorFilter) {
      this.mDrawableContainerState.mColorFilter = paramColorFilter;
      Drawable drawable = this.mCurrDrawable;
      if (drawable != null)
        drawable.setColorFilter(paramColorFilter); 
    } 
  }
  
  void setConstantState(DrawableContainerState paramDrawableContainerState) {
    this.mDrawableContainerState = paramDrawableContainerState;
    int i = this.mCurIndex;
    if (i >= 0) {
      Drawable drawable = paramDrawableContainerState.getChild(i);
      this.mCurrDrawable = drawable;
      if (drawable != null)
        initializeDrawableForDisplay(drawable); 
    } 
    this.mLastDrawable = null;
  }
  
  void setCurrentIndex(int paramInt) {
    selectDrawable(paramInt);
  }
  
  public void setDither(boolean paramBoolean) {
    if (this.mDrawableContainerState.mDither != paramBoolean) {
      this.mDrawableContainerState.mDither = paramBoolean;
      Drawable drawable = this.mCurrDrawable;
      if (drawable != null)
        drawable.setDither(this.mDrawableContainerState.mDither); 
    } 
  }
  
  public void setEnterFadeDuration(int paramInt) {
    this.mDrawableContainerState.mEnterFadeDuration = paramInt;
  }
  
  public void setExitFadeDuration(int paramInt) {
    this.mDrawableContainerState.mExitFadeDuration = paramInt;
  }
  
  public void setHotspot(float paramFloat1, float paramFloat2) {
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null)
      DrawableCompat.setHotspot(drawable, paramFloat1, paramFloat2); 
  }
  
  public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Rect rect = this.mHotspotBounds;
    if (rect == null) {
      this.mHotspotBounds = new Rect(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      rect.set(paramInt1, paramInt2, paramInt3, paramInt4);
    } 
    Drawable drawable = this.mCurrDrawable;
    if (drawable != null)
      DrawableCompat.setHotspotBounds(drawable, paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  public void setTintList(ColorStateList paramColorStateList) {
    this.mDrawableContainerState.mHasTintList = true;
    if (this.mDrawableContainerState.mTintList != paramColorStateList) {
      this.mDrawableContainerState.mTintList = paramColorStateList;
      DrawableCompat.setTintList(this.mCurrDrawable, paramColorStateList);
    } 
  }
  
  public void setTintMode(PorterDuff.Mode paramMode) {
    this.mDrawableContainerState.mHasTintMode = true;
    if (this.mDrawableContainerState.mTintMode != paramMode) {
      this.mDrawableContainerState.mTintMode = paramMode;
      DrawableCompat.setTintMode(this.mCurrDrawable, paramMode);
    } 
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2) {
    boolean bool = super.setVisible(paramBoolean1, paramBoolean2);
    Drawable drawable = this.mLastDrawable;
    if (drawable != null)
      drawable.setVisible(paramBoolean1, paramBoolean2); 
    drawable = this.mCurrDrawable;
    if (drawable != null)
      drawable.setVisible(paramBoolean1, paramBoolean2); 
    return bool;
  }
  
  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable) {
    if (paramDrawable == this.mCurrDrawable && getCallback() != null)
      getCallback().unscheduleDrawable(this, paramRunnable); 
  }
  
  final void updateDensity(Resources paramResources) {
    this.mDrawableContainerState.updateDensity(paramResources);
  }
  
  static class BlockInvalidateCallback implements Drawable.Callback {
    private Drawable.Callback mCallback;
    
    public void invalidateDrawable(Drawable param1Drawable) {}
    
    public void scheduleDrawable(Drawable param1Drawable, Runnable param1Runnable, long param1Long) {
      Drawable.Callback callback = this.mCallback;
      if (callback != null)
        callback.scheduleDrawable(param1Drawable, param1Runnable, param1Long); 
    }
    
    public void unscheduleDrawable(Drawable param1Drawable, Runnable param1Runnable) {
      Drawable.Callback callback = this.mCallback;
      if (callback != null)
        callback.unscheduleDrawable(param1Drawable, param1Runnable); 
    }
    
    public Drawable.Callback unwrap() {
      Drawable.Callback callback = this.mCallback;
      this.mCallback = null;
      return callback;
    }
    
    public BlockInvalidateCallback wrap(Drawable.Callback param1Callback) {
      this.mCallback = param1Callback;
      return this;
    }
  }
  
  static abstract class DrawableContainerState extends Drawable.ConstantState {
    boolean mAutoMirrored;
    
    boolean mCanConstantState;
    
    int mChangingConfigurations;
    
    boolean mCheckedConstantSize;
    
    boolean mCheckedConstantState;
    
    boolean mCheckedOpacity;
    
    boolean mCheckedPadding;
    
    boolean mCheckedStateful;
    
    int mChildrenChangingConfigurations;
    
    ColorFilter mColorFilter;
    
    int mConstantHeight;
    
    int mConstantMinimumHeight;
    
    int mConstantMinimumWidth;
    
    Rect mConstantPadding;
    
    boolean mConstantSize;
    
    int mConstantWidth;
    
    int mDensity = 160;
    
    boolean mDither;
    
    SparseArray<Drawable.ConstantState> mDrawableFutures;
    
    Drawable[] mDrawables;
    
    int mEnterFadeDuration;
    
    int mExitFadeDuration;
    
    boolean mHasColorFilter;
    
    boolean mHasTintList;
    
    boolean mHasTintMode;
    
    int mLayoutDirection;
    
    boolean mMutated;
    
    int mNumChildren;
    
    int mOpacity;
    
    final DrawableContainer mOwner;
    
    Resources mSourceRes;
    
    boolean mStateful;
    
    ColorStateList mTintList;
    
    PorterDuff.Mode mTintMode;
    
    boolean mVariablePadding;
    
    DrawableContainerState(DrawableContainerState param1DrawableContainerState, DrawableContainer param1DrawableContainer, Resources param1Resources) {
      boolean bool = false;
      this.mVariablePadding = false;
      this.mConstantSize = false;
      this.mDither = true;
      this.mEnterFadeDuration = 0;
      this.mExitFadeDuration = 0;
      this.mOwner = param1DrawableContainer;
      if (param1Resources != null) {
        Resources resources = param1Resources;
      } else if (param1DrawableContainerState != null) {
        Resources resources = param1DrawableContainerState.mSourceRes;
      } else {
        param1DrawableContainer = null;
      } 
      this.mSourceRes = (Resources)param1DrawableContainer;
      if (param1DrawableContainerState != null) {
        i = param1DrawableContainerState.mDensity;
      } else {
        i = 0;
      } 
      int i = DrawableContainer.resolveDensity(param1Resources, i);
      this.mDensity = i;
      if (param1DrawableContainerState != null) {
        this.mChangingConfigurations = param1DrawableContainerState.mChangingConfigurations;
        this.mChildrenChangingConfigurations = param1DrawableContainerState.mChildrenChangingConfigurations;
        this.mCheckedConstantState = true;
        this.mCanConstantState = true;
        this.mVariablePadding = param1DrawableContainerState.mVariablePadding;
        this.mConstantSize = param1DrawableContainerState.mConstantSize;
        this.mDither = param1DrawableContainerState.mDither;
        this.mMutated = param1DrawableContainerState.mMutated;
        this.mLayoutDirection = param1DrawableContainerState.mLayoutDirection;
        this.mEnterFadeDuration = param1DrawableContainerState.mEnterFadeDuration;
        this.mExitFadeDuration = param1DrawableContainerState.mExitFadeDuration;
        this.mAutoMirrored = param1DrawableContainerState.mAutoMirrored;
        this.mColorFilter = param1DrawableContainerState.mColorFilter;
        this.mHasColorFilter = param1DrawableContainerState.mHasColorFilter;
        this.mTintList = param1DrawableContainerState.mTintList;
        this.mTintMode = param1DrawableContainerState.mTintMode;
        this.mHasTintList = param1DrawableContainerState.mHasTintList;
        this.mHasTintMode = param1DrawableContainerState.mHasTintMode;
        if (param1DrawableContainerState.mDensity == i) {
          if (param1DrawableContainerState.mCheckedPadding) {
            this.mConstantPadding = new Rect(param1DrawableContainerState.mConstantPadding);
            this.mCheckedPadding = true;
          } 
          if (param1DrawableContainerState.mCheckedConstantSize) {
            this.mConstantWidth = param1DrawableContainerState.mConstantWidth;
            this.mConstantHeight = param1DrawableContainerState.mConstantHeight;
            this.mConstantMinimumWidth = param1DrawableContainerState.mConstantMinimumWidth;
            this.mConstantMinimumHeight = param1DrawableContainerState.mConstantMinimumHeight;
            this.mCheckedConstantSize = true;
          } 
        } 
        if (param1DrawableContainerState.mCheckedOpacity) {
          this.mOpacity = param1DrawableContainerState.mOpacity;
          this.mCheckedOpacity = true;
        } 
        if (param1DrawableContainerState.mCheckedStateful) {
          this.mStateful = param1DrawableContainerState.mStateful;
          this.mCheckedStateful = true;
        } 
        Drawable[] arrayOfDrawable = param1DrawableContainerState.mDrawables;
        this.mDrawables = new Drawable[arrayOfDrawable.length];
        this.mNumChildren = param1DrawableContainerState.mNumChildren;
        SparseArray<Drawable.ConstantState> sparseArray = param1DrawableContainerState.mDrawableFutures;
        if (sparseArray != null) {
          this.mDrawableFutures = sparseArray.clone();
        } else {
          this.mDrawableFutures = new SparseArray(this.mNumChildren);
        } 
        int j = this.mNumChildren;
        for (i = bool; i < j; i++) {
          if (arrayOfDrawable[i] != null) {
            Drawable.ConstantState constantState = arrayOfDrawable[i].getConstantState();
            if (constantState != null) {
              this.mDrawableFutures.put(i, constantState);
            } else {
              this.mDrawables[i] = arrayOfDrawable[i];
            } 
          } 
        } 
      } else {
        this.mDrawables = new Drawable[10];
        this.mNumChildren = 0;
      } 
    }
    
    private void createAllFutures() {
      SparseArray<Drawable.ConstantState> sparseArray = this.mDrawableFutures;
      if (sparseArray != null) {
        int i = sparseArray.size();
        for (byte b = 0; b < i; b++) {
          int j = this.mDrawableFutures.keyAt(b);
          Drawable.ConstantState constantState = (Drawable.ConstantState)this.mDrawableFutures.valueAt(b);
          this.mDrawables[j] = prepareDrawable(constantState.newDrawable(this.mSourceRes));
        } 
        this.mDrawableFutures = null;
      } 
    }
    
    private Drawable prepareDrawable(Drawable param1Drawable) {
      if (Build.VERSION.SDK_INT >= 23)
        param1Drawable.setLayoutDirection(this.mLayoutDirection); 
      param1Drawable = param1Drawable.mutate();
      param1Drawable.setCallback(this.mOwner);
      return param1Drawable;
    }
    
    public final int addChild(Drawable param1Drawable) {
      int j = this.mNumChildren;
      if (j >= this.mDrawables.length)
        growArray(j, j + 10); 
      param1Drawable.mutate();
      param1Drawable.setVisible(false, true);
      param1Drawable.setCallback(this.mOwner);
      this.mDrawables[j] = param1Drawable;
      this.mNumChildren++;
      int i = this.mChildrenChangingConfigurations;
      this.mChildrenChangingConfigurations = param1Drawable.getChangingConfigurations() | i;
      invalidateCache();
      this.mConstantPadding = null;
      this.mCheckedPadding = false;
      this.mCheckedConstantSize = false;
      this.mCheckedConstantState = false;
      return j;
    }
    
    final void applyTheme(Resources.Theme param1Theme) {
      if (param1Theme != null) {
        createAllFutures();
        int i = this.mNumChildren;
        Drawable[] arrayOfDrawable = this.mDrawables;
        for (byte b = 0; b < i; b++) {
          if (arrayOfDrawable[b] != null && arrayOfDrawable[b].canApplyTheme()) {
            arrayOfDrawable[b].applyTheme(param1Theme);
            this.mChildrenChangingConfigurations |= arrayOfDrawable[b].getChangingConfigurations();
          } 
        } 
        updateDensity(param1Theme.getResources());
      } 
    }
    
    public boolean canApplyTheme() {
      int i = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      for (byte b = 0; b < i; b++) {
        Drawable drawable = arrayOfDrawable[b];
        if (drawable != null) {
          if (drawable.canApplyTheme())
            return true; 
        } else {
          Drawable.ConstantState constantState = (Drawable.ConstantState)this.mDrawableFutures.get(b);
          if (constantState != null && constantState.canApplyTheme())
            return true; 
        } 
      } 
      return false;
    }
    
    public boolean canConstantState() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield mCheckedConstantState : Z
      //   6: ifeq -> 18
      //   9: aload_0
      //   10: getfield mCanConstantState : Z
      //   13: istore_3
      //   14: aload_0
      //   15: monitorexit
      //   16: iload_3
      //   17: ireturn
      //   18: aload_0
      //   19: invokespecial createAllFutures : ()V
      //   22: aload_0
      //   23: iconst_1
      //   24: putfield mCheckedConstantState : Z
      //   27: aload_0
      //   28: getfield mNumChildren : I
      //   31: istore_2
      //   32: aload_0
      //   33: getfield mDrawables : [Landroid/graphics/drawable/Drawable;
      //   36: astore #4
      //   38: iconst_0
      //   39: istore_1
      //   40: iload_1
      //   41: iload_2
      //   42: if_icmpge -> 70
      //   45: aload #4
      //   47: iload_1
      //   48: aaload
      //   49: invokevirtual getConstantState : ()Landroid/graphics/drawable/Drawable$ConstantState;
      //   52: ifnonnull -> 64
      //   55: aload_0
      //   56: iconst_0
      //   57: putfield mCanConstantState : Z
      //   60: aload_0
      //   61: monitorexit
      //   62: iconst_0
      //   63: ireturn
      //   64: iinc #1, 1
      //   67: goto -> 40
      //   70: aload_0
      //   71: iconst_1
      //   72: putfield mCanConstantState : Z
      //   75: aload_0
      //   76: monitorexit
      //   77: iconst_1
      //   78: ireturn
      //   79: astore #4
      //   81: aload_0
      //   82: monitorexit
      //   83: aload #4
      //   85: athrow
      // Exception table:
      //   from	to	target	type
      //   2	14	79	finally
      //   18	38	79	finally
      //   45	60	79	finally
      //   70	75	79	finally
    }
    
    final void clearMutated() {
      this.mMutated = false;
    }
    
    protected void computeConstantSize() {
      this.mCheckedConstantSize = true;
      createAllFutures();
      int i = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      this.mConstantHeight = -1;
      this.mConstantWidth = -1;
      byte b = 0;
      this.mConstantMinimumHeight = 0;
      this.mConstantMinimumWidth = 0;
      while (b < i) {
        Drawable drawable = arrayOfDrawable[b];
        int j = drawable.getIntrinsicWidth();
        if (j > this.mConstantWidth)
          this.mConstantWidth = j; 
        j = drawable.getIntrinsicHeight();
        if (j > this.mConstantHeight)
          this.mConstantHeight = j; 
        j = drawable.getMinimumWidth();
        if (j > this.mConstantMinimumWidth)
          this.mConstantMinimumWidth = j; 
        j = drawable.getMinimumHeight();
        if (j > this.mConstantMinimumHeight)
          this.mConstantMinimumHeight = j; 
        b++;
      } 
    }
    
    final int getCapacity() {
      return this.mDrawables.length;
    }
    
    public int getChangingConfigurations() {
      return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
    }
    
    public final Drawable getChild(int param1Int) {
      Drawable drawable = this.mDrawables[param1Int];
      if (drawable != null)
        return drawable; 
      SparseArray<Drawable.ConstantState> sparseArray = this.mDrawableFutures;
      if (sparseArray != null) {
        int i = sparseArray.indexOfKey(param1Int);
        if (i >= 0) {
          Drawable drawable1 = prepareDrawable(((Drawable.ConstantState)this.mDrawableFutures.valueAt(i)).newDrawable(this.mSourceRes));
          this.mDrawables[param1Int] = drawable1;
          this.mDrawableFutures.removeAt(i);
          if (this.mDrawableFutures.size() == 0)
            this.mDrawableFutures = null; 
          return drawable1;
        } 
      } 
      return null;
    }
    
    public final int getChildCount() {
      return this.mNumChildren;
    }
    
    public final int getConstantHeight() {
      if (!this.mCheckedConstantSize)
        computeConstantSize(); 
      return this.mConstantHeight;
    }
    
    public final int getConstantMinimumHeight() {
      if (!this.mCheckedConstantSize)
        computeConstantSize(); 
      return this.mConstantMinimumHeight;
    }
    
    public final int getConstantMinimumWidth() {
      if (!this.mCheckedConstantSize)
        computeConstantSize(); 
      return this.mConstantMinimumWidth;
    }
    
    public final Rect getConstantPadding() {
      boolean bool = this.mVariablePadding;
      Rect rect1 = null;
      if (bool)
        return null; 
      if (this.mConstantPadding != null || this.mCheckedPadding)
        return this.mConstantPadding; 
      createAllFutures();
      Rect rect2 = new Rect();
      int i = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      byte b = 0;
      while (b < i) {
        Rect rect = rect1;
        if (arrayOfDrawable[b].getPadding(rect2)) {
          Rect rect3 = rect1;
          if (rect1 == null)
            rect3 = new Rect(0, 0, 0, 0); 
          if (rect2.left > rect3.left)
            rect3.left = rect2.left; 
          if (rect2.top > rect3.top)
            rect3.top = rect2.top; 
          if (rect2.right > rect3.right)
            rect3.right = rect2.right; 
          rect = rect3;
          if (rect2.bottom > rect3.bottom) {
            rect3.bottom = rect2.bottom;
            rect = rect3;
          } 
        } 
        b++;
        rect1 = rect;
      } 
      this.mCheckedPadding = true;
      this.mConstantPadding = rect1;
      return rect1;
    }
    
    public final int getConstantWidth() {
      if (!this.mCheckedConstantSize)
        computeConstantSize(); 
      return this.mConstantWidth;
    }
    
    public final int getEnterFadeDuration() {
      return this.mEnterFadeDuration;
    }
    
    public final int getExitFadeDuration() {
      return this.mExitFadeDuration;
    }
    
    public final int getOpacity() {
      int i;
      if (this.mCheckedOpacity)
        return this.mOpacity; 
      createAllFutures();
      int j = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      if (j > 0) {
        i = arrayOfDrawable[0].getOpacity();
      } else {
        i = -2;
      } 
      for (byte b = 1; b < j; b++)
        i = Drawable.resolveOpacity(i, arrayOfDrawable[b].getOpacity()); 
      this.mOpacity = i;
      this.mCheckedOpacity = true;
      return i;
    }
    
    public void growArray(int param1Int1, int param1Int2) {
      Drawable[] arrayOfDrawable = new Drawable[param1Int2];
      System.arraycopy(this.mDrawables, 0, arrayOfDrawable, 0, param1Int1);
      this.mDrawables = arrayOfDrawable;
    }
    
    void invalidateCache() {
      this.mCheckedOpacity = false;
      this.mCheckedStateful = false;
    }
    
    public final boolean isConstantSize() {
      return this.mConstantSize;
    }
    
    public final boolean isStateful() {
      boolean bool1;
      if (this.mCheckedStateful)
        return this.mStateful; 
      createAllFutures();
      int i = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      boolean bool2 = false;
      byte b = 0;
      while (true) {
        bool1 = bool2;
        if (b < i) {
          if (arrayOfDrawable[b].isStateful()) {
            bool1 = true;
            break;
          } 
          b++;
          continue;
        } 
        break;
      } 
      this.mStateful = bool1;
      this.mCheckedStateful = true;
      return bool1;
    }
    
    void mutate() {
      int i = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      for (byte b = 0; b < i; b++) {
        if (arrayOfDrawable[b] != null)
          arrayOfDrawable[b].mutate(); 
      } 
      this.mMutated = true;
    }
    
    public final void setConstantSize(boolean param1Boolean) {
      this.mConstantSize = param1Boolean;
    }
    
    public final void setEnterFadeDuration(int param1Int) {
      this.mEnterFadeDuration = param1Int;
    }
    
    public final void setExitFadeDuration(int param1Int) {
      this.mExitFadeDuration = param1Int;
    }
    
    final boolean setLayoutDirection(int param1Int1, int param1Int2) {
      int i = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      byte b = 0;
      boolean bool;
      for (bool = false; b < i; bool = bool1) {
        boolean bool1 = bool;
        if (arrayOfDrawable[b] != null) {
          boolean bool2;
          if (Build.VERSION.SDK_INT >= 23) {
            bool2 = arrayOfDrawable[b].setLayoutDirection(param1Int1);
          } else {
            bool2 = false;
          } 
          bool1 = bool;
          if (b == param1Int2)
            bool1 = bool2; 
        } 
        b++;
      } 
      this.mLayoutDirection = param1Int1;
      return bool;
    }
    
    public final void setVariablePadding(boolean param1Boolean) {
      this.mVariablePadding = param1Boolean;
    }
    
    final void updateDensity(Resources param1Resources) {
      if (param1Resources != null) {
        this.mSourceRes = param1Resources;
        int j = DrawableContainer.resolveDensity(param1Resources, this.mDensity);
        int i = this.mDensity;
        this.mDensity = j;
        if (i != j) {
          this.mCheckedConstantSize = false;
          this.mCheckedPadding = false;
        } 
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\graphics\drawable\DrawableContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */