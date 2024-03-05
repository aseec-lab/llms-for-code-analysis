package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawerArrowDrawable extends Drawable {
  public static final int ARROW_DIRECTION_END = 3;
  
  public static final int ARROW_DIRECTION_LEFT = 0;
  
  public static final int ARROW_DIRECTION_RIGHT = 1;
  
  public static final int ARROW_DIRECTION_START = 2;
  
  private static final float ARROW_HEAD_ANGLE = (float)Math.toRadians(45.0D);
  
  private float mArrowHeadLength;
  
  private float mArrowShaftLength;
  
  private float mBarGap;
  
  private float mBarLength;
  
  private int mDirection = 2;
  
  private float mMaxCutForBarSize;
  
  private final Paint mPaint = new Paint();
  
  private final Path mPath = new Path();
  
  private float mProgress;
  
  private final int mSize;
  
  private boolean mSpin;
  
  private boolean mVerticalMirror = false;
  
  public DrawerArrowDrawable(Context paramContext) {
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeJoin(Paint.Join.MITER);
    this.mPaint.setStrokeCap(Paint.Cap.BUTT);
    this.mPaint.setAntiAlias(true);
    TypedArray typedArray = paramContext.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
    setColor(typedArray.getColor(R.styleable.DrawerArrowToggle_color, 0));
    setBarThickness(typedArray.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0F));
    setSpinEnabled(typedArray.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true));
    setGapSize(Math.round(typedArray.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0F)));
    this.mSize = typedArray.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
    this.mBarLength = Math.round(typedArray.getDimension(R.styleable.DrawerArrowToggle_barLength, 0.0F));
    this.mArrowHeadLength = Math.round(typedArray.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0F));
    this.mArrowShaftLength = typedArray.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0F);
    typedArray.recycle();
  }
  
  private static float lerp(float paramFloat1, float paramFloat2, float paramFloat3) {
    return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
  }
  
  public void draw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getBounds : ()Landroid/graphics/Rect;
    //   4: astore #16
    //   6: aload_0
    //   7: getfield mDirection : I
    //   10: istore #15
    //   12: iconst_0
    //   13: istore #14
    //   15: iload #14
    //   17: istore #13
    //   19: iload #15
    //   21: ifeq -> 65
    //   24: iload #15
    //   26: iconst_1
    //   27: if_icmpeq -> 62
    //   30: iload #15
    //   32: iconst_3
    //   33: if_icmpeq -> 51
    //   36: iload #14
    //   38: istore #13
    //   40: aload_0
    //   41: invokestatic getLayoutDirection : (Landroid/graphics/drawable/Drawable;)I
    //   44: iconst_1
    //   45: if_icmpne -> 65
    //   48: goto -> 62
    //   51: iload #14
    //   53: istore #13
    //   55: aload_0
    //   56: invokestatic getLayoutDirection : (Landroid/graphics/drawable/Drawable;)I
    //   59: ifne -> 65
    //   62: iconst_1
    //   63: istore #13
    //   65: aload_0
    //   66: getfield mArrowHeadLength : F
    //   69: fstore #6
    //   71: fload #6
    //   73: fload #6
    //   75: fmul
    //   76: fconst_2
    //   77: fmul
    //   78: f2d
    //   79: invokestatic sqrt : (D)D
    //   82: d2f
    //   83: fstore #6
    //   85: aload_0
    //   86: getfield mBarLength : F
    //   89: fload #6
    //   91: aload_0
    //   92: getfield mProgress : F
    //   95: invokestatic lerp : (FFF)F
    //   98: fstore #11
    //   100: aload_0
    //   101: getfield mBarLength : F
    //   104: aload_0
    //   105: getfield mArrowShaftLength : F
    //   108: aload_0
    //   109: getfield mProgress : F
    //   112: invokestatic lerp : (FFF)F
    //   115: fstore #8
    //   117: fconst_0
    //   118: aload_0
    //   119: getfield mMaxCutForBarSize : F
    //   122: aload_0
    //   123: getfield mProgress : F
    //   126: invokestatic lerp : (FFF)F
    //   129: invokestatic round : (F)I
    //   132: i2f
    //   133: fstore #9
    //   135: fconst_0
    //   136: getstatic androidx/appcompat/graphics/drawable/DrawerArrowDrawable.ARROW_HEAD_ANGLE : F
    //   139: aload_0
    //   140: getfield mProgress : F
    //   143: invokestatic lerp : (FFF)F
    //   146: fstore #10
    //   148: iload #13
    //   150: ifeq -> 159
    //   153: fconst_0
    //   154: fstore #6
    //   156: goto -> 163
    //   159: ldc -180.0
    //   161: fstore #6
    //   163: iload #13
    //   165: ifeq -> 175
    //   168: ldc 180.0
    //   170: fstore #7
    //   172: goto -> 178
    //   175: fconst_0
    //   176: fstore #7
    //   178: fload #6
    //   180: fload #7
    //   182: aload_0
    //   183: getfield mProgress : F
    //   186: invokestatic lerp : (FFF)F
    //   189: fstore #6
    //   191: fload #11
    //   193: f2d
    //   194: dstore_2
    //   195: fload #10
    //   197: f2d
    //   198: dstore #4
    //   200: dload #4
    //   202: invokestatic cos : (D)D
    //   205: dload_2
    //   206: dmul
    //   207: invokestatic round : (D)J
    //   210: l2f
    //   211: fstore #10
    //   213: dload_2
    //   214: dload #4
    //   216: invokestatic sin : (D)D
    //   219: dmul
    //   220: invokestatic round : (D)J
    //   223: l2f
    //   224: fstore #7
    //   226: aload_0
    //   227: getfield mPath : Landroid/graphics/Path;
    //   230: invokevirtual rewind : ()V
    //   233: aload_0
    //   234: getfield mBarGap : F
    //   237: aload_0
    //   238: getfield mPaint : Landroid/graphics/Paint;
    //   241: invokevirtual getStrokeWidth : ()F
    //   244: fadd
    //   245: aload_0
    //   246: getfield mMaxCutForBarSize : F
    //   249: fneg
    //   250: aload_0
    //   251: getfield mProgress : F
    //   254: invokestatic lerp : (FFF)F
    //   257: fstore #12
    //   259: fload #8
    //   261: fneg
    //   262: fconst_2
    //   263: fdiv
    //   264: fstore #11
    //   266: aload_0
    //   267: getfield mPath : Landroid/graphics/Path;
    //   270: fload #11
    //   272: fload #9
    //   274: fadd
    //   275: fconst_0
    //   276: invokevirtual moveTo : (FF)V
    //   279: aload_0
    //   280: getfield mPath : Landroid/graphics/Path;
    //   283: fload #8
    //   285: fload #9
    //   287: fconst_2
    //   288: fmul
    //   289: fsub
    //   290: fconst_0
    //   291: invokevirtual rLineTo : (FF)V
    //   294: aload_0
    //   295: getfield mPath : Landroid/graphics/Path;
    //   298: fload #11
    //   300: fload #12
    //   302: invokevirtual moveTo : (FF)V
    //   305: aload_0
    //   306: getfield mPath : Landroid/graphics/Path;
    //   309: fload #10
    //   311: fload #7
    //   313: invokevirtual rLineTo : (FF)V
    //   316: aload_0
    //   317: getfield mPath : Landroid/graphics/Path;
    //   320: fload #11
    //   322: fload #12
    //   324: fneg
    //   325: invokevirtual moveTo : (FF)V
    //   328: aload_0
    //   329: getfield mPath : Landroid/graphics/Path;
    //   332: fload #10
    //   334: fload #7
    //   336: fneg
    //   337: invokevirtual rLineTo : (FF)V
    //   340: aload_0
    //   341: getfield mPath : Landroid/graphics/Path;
    //   344: invokevirtual close : ()V
    //   347: aload_1
    //   348: invokevirtual save : ()I
    //   351: pop
    //   352: aload_0
    //   353: getfield mPaint : Landroid/graphics/Paint;
    //   356: invokevirtual getStrokeWidth : ()F
    //   359: fstore #8
    //   361: aload #16
    //   363: invokevirtual height : ()I
    //   366: i2f
    //   367: fstore #9
    //   369: aload_0
    //   370: getfield mBarGap : F
    //   373: fstore #7
    //   375: fload #9
    //   377: ldc_w 3.0
    //   380: fload #8
    //   382: fmul
    //   383: fsub
    //   384: fconst_2
    //   385: fload #7
    //   387: fmul
    //   388: fsub
    //   389: f2i
    //   390: iconst_4
    //   391: idiv
    //   392: iconst_2
    //   393: imul
    //   394: i2f
    //   395: fstore #9
    //   397: aload_1
    //   398: aload #16
    //   400: invokevirtual centerX : ()I
    //   403: i2f
    //   404: fload #9
    //   406: fload #8
    //   408: ldc_w 1.5
    //   411: fmul
    //   412: fload #7
    //   414: fadd
    //   415: fadd
    //   416: invokevirtual translate : (FF)V
    //   419: aload_0
    //   420: getfield mSpin : Z
    //   423: ifeq -> 458
    //   426: aload_0
    //   427: getfield mVerticalMirror : Z
    //   430: iload #13
    //   432: ixor
    //   433: ifeq -> 442
    //   436: iconst_m1
    //   437: istore #13
    //   439: goto -> 445
    //   442: iconst_1
    //   443: istore #13
    //   445: aload_1
    //   446: fload #6
    //   448: iload #13
    //   450: i2f
    //   451: fmul
    //   452: invokevirtual rotate : (F)V
    //   455: goto -> 469
    //   458: iload #13
    //   460: ifeq -> 469
    //   463: aload_1
    //   464: ldc 180.0
    //   466: invokevirtual rotate : (F)V
    //   469: aload_1
    //   470: aload_0
    //   471: getfield mPath : Landroid/graphics/Path;
    //   474: aload_0
    //   475: getfield mPaint : Landroid/graphics/Paint;
    //   478: invokevirtual drawPath : (Landroid/graphics/Path;Landroid/graphics/Paint;)V
    //   481: aload_1
    //   482: invokevirtual restore : ()V
    //   485: return
  }
  
  public float getArrowHeadLength() {
    return this.mArrowHeadLength;
  }
  
  public float getArrowShaftLength() {
    return this.mArrowShaftLength;
  }
  
  public float getBarLength() {
    return this.mBarLength;
  }
  
  public float getBarThickness() {
    return this.mPaint.getStrokeWidth();
  }
  
  public int getColor() {
    return this.mPaint.getColor();
  }
  
  public int getDirection() {
    return this.mDirection;
  }
  
  public float getGapSize() {
    return this.mBarGap;
  }
  
  public int getIntrinsicHeight() {
    return this.mSize;
  }
  
  public int getIntrinsicWidth() {
    return this.mSize;
  }
  
  public int getOpacity() {
    return -3;
  }
  
  public final Paint getPaint() {
    return this.mPaint;
  }
  
  public float getProgress() {
    return this.mProgress;
  }
  
  public boolean isSpinEnabled() {
    return this.mSpin;
  }
  
  public void setAlpha(int paramInt) {
    if (paramInt != this.mPaint.getAlpha()) {
      this.mPaint.setAlpha(paramInt);
      invalidateSelf();
    } 
  }
  
  public void setArrowHeadLength(float paramFloat) {
    if (this.mArrowHeadLength != paramFloat) {
      this.mArrowHeadLength = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setArrowShaftLength(float paramFloat) {
    if (this.mArrowShaftLength != paramFloat) {
      this.mArrowShaftLength = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setBarLength(float paramFloat) {
    if (this.mBarLength != paramFloat) {
      this.mBarLength = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setBarThickness(float paramFloat) {
    if (this.mPaint.getStrokeWidth() != paramFloat) {
      this.mPaint.setStrokeWidth(paramFloat);
      this.mMaxCutForBarSize = (float)((paramFloat / 2.0F) * Math.cos(ARROW_HEAD_ANGLE));
      invalidateSelf();
    } 
  }
  
  public void setColor(int paramInt) {
    if (paramInt != this.mPaint.getColor()) {
      this.mPaint.setColor(paramInt);
      invalidateSelf();
    } 
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    this.mPaint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  public void setDirection(int paramInt) {
    if (paramInt != this.mDirection) {
      this.mDirection = paramInt;
      invalidateSelf();
    } 
  }
  
  public void setGapSize(float paramFloat) {
    if (paramFloat != this.mBarGap) {
      this.mBarGap = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setProgress(float paramFloat) {
    if (this.mProgress != paramFloat) {
      this.mProgress = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setSpinEnabled(boolean paramBoolean) {
    if (this.mSpin != paramBoolean) {
      this.mSpin = paramBoolean;
      invalidateSelf();
    } 
  }
  
  public void setVerticalMirror(boolean paramBoolean) {
    if (this.mVerticalMirror != paramBoolean) {
      this.mVerticalMirror = paramBoolean;
      invalidateSelf();
    } 
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ArrowDirection {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\graphics\drawable\DrawerArrowDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */