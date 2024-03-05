package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R;

public class ImageFilterView extends AppCompatImageView {
  private float mCrossfade = 0.0F;
  
  private ImageMatrix mImageMatrix = new ImageMatrix();
  
  LayerDrawable mLayer;
  
  Drawable[] mLayers;
  
  private boolean mOverlay = true;
  
  private Path mPath;
  
  RectF mRect;
  
  private float mRound = Float.NaN;
  
  private float mRoundPercent = 0.0F;
  
  ViewOutlineProvider mViewOutlineProvider;
  
  public ImageFilterView(Context paramContext) {
    super(paramContext);
    init(paramContext, (AttributeSet)null);
  }
  
  public ImageFilterView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public ImageFilterView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
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
    if (Build.VERSION.SDK_INT < 21 && this.mRoundPercent != 0.0F && this.mPath != null) {
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
  
  public float getBrightness() {
    return this.mImageMatrix.mBrightness;
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
              final ImageFilterView this$0;
              
              public void getOutline(View param1View, Outline param1Outline) {
                param1Outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
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
              final ImageFilterView this$0;
              
              public void getOutline(View param1View, Outline param1Outline) {
                int j = ImageFilterView.this.getWidth();
                int i = ImageFilterView.this.getHeight();
                param1Outline.setRoundRect(0, 0, j, i, Math.min(j, i) * ImageFilterView.this.mRoundPercent / 2.0F);
              }
            };
          this.mViewOutlineProvider = viewOutlineProvider;
          setOutlineProvider(viewOutlineProvider);
        } 
        setClipToOutline(true);
      } 
      int i = getWidth();
      int j = getHeight();
      paramFloat = Math.min(i, j) * this.mRoundPercent / 2.0F;
      this.mRect.set(0.0F, 0.0F, i, j);
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
  
  static class ImageMatrix {
    float[] m = new float[20];
    
    float mBrightness = 1.0F;
    
    ColorMatrix mColorMatrix = new ColorMatrix();
    
    float mContrast = 1.0F;
    
    float mSaturation = 1.0F;
    
    ColorMatrix mTmpColorMatrix = new ColorMatrix();
    
    float mWarmth = 1.0F;
    
    private void brightness(float param1Float) {
      float[] arrayOfFloat = this.m;
      arrayOfFloat[0] = param1Float;
      arrayOfFloat[1] = 0.0F;
      arrayOfFloat[2] = 0.0F;
      arrayOfFloat[3] = 0.0F;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = 0.0F;
      arrayOfFloat[6] = param1Float;
      arrayOfFloat[7] = 0.0F;
      arrayOfFloat[8] = 0.0F;
      arrayOfFloat[9] = 0.0F;
      arrayOfFloat[10] = 0.0F;
      arrayOfFloat[11] = 0.0F;
      arrayOfFloat[12] = param1Float;
      arrayOfFloat[13] = 0.0F;
      arrayOfFloat[14] = 0.0F;
      arrayOfFloat[15] = 0.0F;
      arrayOfFloat[16] = 0.0F;
      arrayOfFloat[17] = 0.0F;
      arrayOfFloat[18] = 1.0F;
      arrayOfFloat[19] = 0.0F;
    }
    
    private void saturation(float param1Float) {
      float f3 = 1.0F - param1Float;
      float f2 = 0.2999F * f3;
      float f1 = 0.587F * f3;
      f3 *= 0.114F;
      float[] arrayOfFloat = this.m;
      arrayOfFloat[0] = f2 + param1Float;
      arrayOfFloat[1] = f1;
      arrayOfFloat[2] = f3;
      arrayOfFloat[3] = 0.0F;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = f2;
      arrayOfFloat[6] = f1 + param1Float;
      arrayOfFloat[7] = f3;
      arrayOfFloat[8] = 0.0F;
      arrayOfFloat[9] = 0.0F;
      arrayOfFloat[10] = f2;
      arrayOfFloat[11] = f1;
      arrayOfFloat[12] = f3 + param1Float;
      arrayOfFloat[13] = 0.0F;
      arrayOfFloat[14] = 0.0F;
      arrayOfFloat[15] = 0.0F;
      arrayOfFloat[16] = 0.0F;
      arrayOfFloat[17] = 0.0F;
      arrayOfFloat[18] = 1.0F;
      arrayOfFloat[19] = 0.0F;
    }
    
    private void warmth(float param1Float) {
      float f1 = param1Float;
      if (param1Float <= 0.0F)
        f1 = 0.01F; 
      param1Float = 5000.0F / f1 / 100.0F;
      if (param1Float > 66.0F) {
        double d = (param1Float - 60.0F);
        f2 = (float)Math.pow(d, -0.13320475816726685D) * 329.69873F;
        f1 = (float)Math.pow(d, 0.07551484555006027D) * 288.12216F;
      } else {
        f1 = (float)Math.log(param1Float) * 99.4708F - 161.11957F;
        f2 = 255.0F;
      } 
      if (param1Float < 66.0F) {
        if (param1Float > 19.0F) {
          param1Float = (float)Math.log((param1Float - 10.0F)) * 138.51773F - 305.0448F;
        } else {
          param1Float = 0.0F;
        } 
      } else {
        param1Float = 255.0F;
      } 
      float f2 = Math.min(255.0F, Math.max(f2, 0.0F));
      f1 = Math.min(255.0F, Math.max(f1, 0.0F));
      param1Float = Math.min(255.0F, Math.max(param1Float, 0.0F));
      float f5 = (float)Math.log(50.0F);
      float f4 = (float)Math.log(40.0F);
      float f3 = Math.min(255.0F, Math.max(255.0F, 0.0F));
      f5 = Math.min(255.0F, Math.max(f5 * 99.4708F - 161.11957F, 0.0F));
      f4 = Math.min(255.0F, Math.max(f4 * 138.51773F - 305.0448F, 0.0F));
      f2 /= f3;
      f1 /= f5;
      param1Float /= f4;
      float[] arrayOfFloat = this.m;
      arrayOfFloat[0] = f2;
      arrayOfFloat[1] = 0.0F;
      arrayOfFloat[2] = 0.0F;
      arrayOfFloat[3] = 0.0F;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = 0.0F;
      arrayOfFloat[6] = f1;
      arrayOfFloat[7] = 0.0F;
      arrayOfFloat[8] = 0.0F;
      arrayOfFloat[9] = 0.0F;
      arrayOfFloat[10] = 0.0F;
      arrayOfFloat[11] = 0.0F;
      arrayOfFloat[12] = param1Float;
      arrayOfFloat[13] = 0.0F;
      arrayOfFloat[14] = 0.0F;
      arrayOfFloat[15] = 0.0F;
      arrayOfFloat[16] = 0.0F;
      arrayOfFloat[17] = 0.0F;
      arrayOfFloat[18] = 1.0F;
      arrayOfFloat[19] = 0.0F;
    }
    
    void updateMatrix(ImageView param1ImageView) {
      boolean bool1;
      this.mColorMatrix.reset();
      float f = this.mSaturation;
      boolean bool2 = true;
      if (f != 1.0F) {
        saturation(f);
        this.mColorMatrix.set(this.m);
        bool1 = true;
      } else {
        bool1 = false;
      } 
      f = this.mContrast;
      if (f != 1.0F) {
        this.mTmpColorMatrix.setScale(f, f, f, 1.0F);
        this.mColorMatrix.postConcat(this.mTmpColorMatrix);
        bool1 = true;
      } 
      f = this.mWarmth;
      if (f != 1.0F) {
        warmth(f);
        this.mTmpColorMatrix.set(this.m);
        this.mColorMatrix.postConcat(this.mTmpColorMatrix);
        bool1 = true;
      } 
      f = this.mBrightness;
      if (f != 1.0F) {
        brightness(f);
        this.mTmpColorMatrix.set(this.m);
        this.mColorMatrix.postConcat(this.mTmpColorMatrix);
        bool1 = bool2;
      } 
      if (bool1) {
        param1ImageView.setColorFilter((ColorFilter)new ColorMatrixColorFilter(this.mColorMatrix));
      } else {
        param1ImageView.clearColorFilter();
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayou\\utils\widget\ImageFilterView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */