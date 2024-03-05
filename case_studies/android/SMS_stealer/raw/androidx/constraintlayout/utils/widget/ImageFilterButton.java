package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.R;

public class ImageFilterButton extends AppCompatImageButton {
  private float mCrossfade = 0.0F;
  
  private ImageFilterView.ImageMatrix mImageMatrix = new ImageFilterView.ImageMatrix();
  
  LayerDrawable mLayer;
  
  Drawable[] mLayers;
  
  private boolean mOverlay = true;
  
  private Path mPath;
  
  RectF mRect;
  
  private float mRound = Float.NaN;
  
  private float mRoundPercent = 0.0F;
  
  ViewOutlineProvider mViewOutlineProvider;
  
  public ImageFilterButton(Context paramContext) {
    super(paramContext);
    init(paramContext, (AttributeSet)null);
  }
  
  public ImageFilterButton(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public ImageFilterButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    setPadding(0, 0, 0, 0);
    if (paramAttributeSet != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ImageFilterView);
      int i = typedArray.getIndexCount();
      Drawable drawable = typedArray.getDrawable(R.styleable.ImageFilterView_altSrc);
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        if (j == R.styleable.ImageFilterView_crossfade) {
          this.mCrossfade = typedArray.getFloat(j, 0.0F);
        } else if (j == R.styleable.ImageFilterView_warmth) {
          setWarmth(typedArray.getFloat(j, 0.0F));
        } else if (j == R.styleable.ImageFilterView_saturation) {
          setSaturation(typedArray.getFloat(j, 0.0F));
        } else if (j == R.styleable.ImageFilterView_contrast) {
          setContrast(typedArray.getFloat(j, 0.0F));
        } else if (j == R.styleable.ImageFilterView_round) {
          if (Build.VERSION.SDK_INT >= 21)
            setRound(typedArray.getDimension(j, 0.0F)); 
        } else if (j == R.styleable.ImageFilterView_roundPercent) {
          if (Build.VERSION.SDK_INT >= 21)
            setRoundPercent(typedArray.getFloat(j, 0.0F)); 
        } else if (j == R.styleable.ImageFilterView_overlay) {
          setOverlay(typedArray.getBoolean(j, this.mOverlay));
        } 
      } 
      typedArray.recycle();
      if (drawable != null) {
        Drawable[] arrayOfDrawable = new Drawable[2];
        this.mLayers = arrayOfDrawable;
        arrayOfDrawable[0] = getDrawable();
        this.mLayers[1] = drawable;
        LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
        this.mLayer = layerDrawable;
        layerDrawable.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0F));
        setImageDrawable((Drawable)this.mLayer);
      } 
    } 
  }
  
  private void setOverlay(boolean paramBoolean) {
    this.mOverlay = paramBoolean;
  }
  
  public void draw(Canvas paramCanvas) {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21 && this.mRound != 0.0F && this.mPath != null) {
      bool = true;
      paramCanvas.save();
      paramCanvas.clipPath(this.mPath);
    } else {
      bool = false;
    } 
    super.draw(paramCanvas);
    if (bool)
      paramCanvas.restore(); 
  }
  
  public float getContrast() {
    return this.mImageMatrix.mContrast;
  }
  
  public float getCrossfade() {
    return this.mCrossfade;
  }
  
  public float getRound() {
    return this.mRound;
  }
  
  public float getRoundPercent() {
    return this.mRoundPercent;
  }
  
  public float getSaturation() {
    return this.mImageMatrix.mSaturation;
  }
  
  public float getWarmth() {
    return this.mImageMatrix.mWarmth;
  }
  
  public void setBrightness(float paramFloat) {
    this.mImageMatrix.mBrightness = paramFloat;
    this.mImageMatrix.updateMatrix((ImageView)this);
  }
  
  public void setContrast(float paramFloat) {
    this.mImageMatrix.mContrast = paramFloat;
    this.mImageMatrix.updateMatrix((ImageView)this);
  }
  
  public void setCrossfade(float paramFloat) {
    this.mCrossfade = paramFloat;
    if (this.mLayers != null) {
      if (!this.mOverlay)
        this.mLayer.getDrawable(0).setAlpha((int)((1.0F - this.mCrossfade) * 255.0F)); 
      this.mLayer.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0F));
      setImageDrawable((Drawable)this.mLayer);
    } 
  }
  
  public void setRound(float paramFloat) {
    boolean bool;
    if (Float.isNaN(paramFloat)) {
      this.mRound = paramFloat;
      paramFloat = this.mRoundPercent;
      this.mRoundPercent = -1.0F;
      setRoundPercent(paramFloat);
      return;
    } 
    if (this.mRound != paramFloat) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mRound = paramFloat;
    if (paramFloat != 0.0F) {
      if (this.mPath == null)
        this.mPath = new Path(); 
      if (this.mRect == null)
        this.mRect = new RectF(); 
      if (Build.VERSION.SDK_INT >= 21) {
        if (this.mViewOutlineProvider == null) {
          ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
              final ImageFilterButton this$0;
              
              public void getOutline(View param1View, Outline param1Outline) {
                param1Outline.setRoundRect(0, 0, ImageFilterButton.this.getWidth(), ImageFilterButton.this.getHeight(), ImageFilterButton.this.mRound);
              }
            };
          this.mViewOutlineProvider = viewOutlineProvider;
          setOutlineProvider(viewOutlineProvider);
        } 
        setClipToOutline(true);
      } 
      int j = getWidth();
      int i = getHeight();
      this.mRect.set(0.0F, 0.0F, j, i);
      this.mPath.reset();
      Path path = this.mPath;
      RectF rectF = this.mRect;
      paramFloat = this.mRound;
      path.addRoundRect(rectF, paramFloat, paramFloat, Path.Direction.CW);
    } else if (Build.VERSION.SDK_INT >= 21) {
      setClipToOutline(false);
    } 
    if (bool && Build.VERSION.SDK_INT >= 21)
      invalidateOutline(); 
  }
  
  public void setRoundPercent(float paramFloat) {
    boolean bool;
    if (this.mRoundPercent != paramFloat) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mRoundPercent = paramFloat;
    if (paramFloat != 0.0F) {
      if (this.mPath == null)
        this.mPath = new Path(); 
      if (this.mRect == null)
        this.mRect = new RectF(); 
      if (Build.VERSION.SDK_INT >= 21) {
        if (this.mViewOutlineProvider == null) {
          ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
              final ImageFilterButton this$0;
              
              public void getOutline(View param1View, Outline param1Outline) {
                int j = ImageFilterButton.this.getWidth();
                int i = ImageFilterButton.this.getHeight();
                param1Outline.setRoundRect(0, 0, j, i, Math.min(j, i) * ImageFilterButton.this.mRoundPercent / 2.0F);
              }
            };
          this.mViewOutlineProvider = viewOutlineProvider;
          setOutlineProvider(viewOutlineProvider);
        } 
        setClipToOutline(true);
      } 
      int j = getWidth();
      int i = getHeight();
      paramFloat = Math.min(j, i) * this.mRoundPercent / 2.0F;
      this.mRect.set(0.0F, 0.0F, j, i);
      this.mPath.reset();
      this.mPath.addRoundRect(this.mRect, paramFloat, paramFloat, Path.Direction.CW);
    } else if (Build.VERSION.SDK_INT >= 21) {
      setClipToOutline(false);
    } 
    if (bool && Build.VERSION.SDK_INT >= 21)
      invalidateOutline(); 
  }
  
  public void setSaturation(float paramFloat) {
    this.mImageMatrix.mSaturation = paramFloat;
    this.mImageMatrix.updateMatrix((ImageView)this);
  }
  
  public void setWarmth(float paramFloat) {
    this.mImageMatrix.mWarmth = paramFloat;
    this.mImageMatrix.updateMatrix((ImageView)this);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayou\\utils\widget\ImageFilterButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */