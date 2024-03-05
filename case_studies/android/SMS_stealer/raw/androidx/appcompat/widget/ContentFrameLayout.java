package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;

public class ContentFrameLayout extends FrameLayout {
  private OnAttachListener mAttachListener;
  
  private final Rect mDecorPadding = new Rect();
  
  private TypedValue mFixedHeightMajor;
  
  private TypedValue mFixedHeightMinor;
  
  private TypedValue mFixedWidthMajor;
  
  private TypedValue mFixedWidthMinor;
  
  private TypedValue mMinWidthMajor;
  
  private TypedValue mMinWidthMinor;
  
  public ContentFrameLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void dispatchFitSystemWindows(Rect paramRect) {
    fitSystemWindows(paramRect);
  }
  
  public TypedValue getFixedHeightMajor() {
    if (this.mFixedHeightMajor == null)
      this.mFixedHeightMajor = new TypedValue(); 
    return this.mFixedHeightMajor;
  }
  
  public TypedValue getFixedHeightMinor() {
    if (this.mFixedHeightMinor == null)
      this.mFixedHeightMinor = new TypedValue(); 
    return this.mFixedHeightMinor;
  }
  
  public TypedValue getFixedWidthMajor() {
    if (this.mFixedWidthMajor == null)
      this.mFixedWidthMajor = new TypedValue(); 
    return this.mFixedWidthMajor;
  }
  
  public TypedValue getFixedWidthMinor() {
    if (this.mFixedWidthMinor == null)
      this.mFixedWidthMinor = new TypedValue(); 
    return this.mFixedWidthMinor;
  }
  
  public TypedValue getMinWidthMajor() {
    if (this.mMinWidthMajor == null)
      this.mMinWidthMajor = new TypedValue(); 
    return this.mMinWidthMajor;
  }
  
  public TypedValue getMinWidthMinor() {
    if (this.mMinWidthMinor == null)
      this.mMinWidthMinor = new TypedValue(); 
    return this.mMinWidthMinor;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    OnAttachListener onAttachListener = this.mAttachListener;
    if (onAttachListener != null)
      onAttachListener.onAttachedFromWindow(); 
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    OnAttachListener onAttachListener = this.mAttachListener;
    if (onAttachListener != null)
      onAttachListener.onDetachedFromWindow(); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getContext : ()Landroid/content/Context;
    //   4: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   7: invokevirtual getDisplayMetrics : ()Landroid/util/DisplayMetrics;
    //   10: astore #11
    //   12: aload #11
    //   14: getfield widthPixels : I
    //   17: istore #5
    //   19: aload #11
    //   21: getfield heightPixels : I
    //   24: istore #4
    //   26: iconst_1
    //   27: istore #8
    //   29: iload #5
    //   31: iload #4
    //   33: if_icmpge -> 42
    //   36: iconst_1
    //   37: istore #4
    //   39: goto -> 45
    //   42: iconst_0
    //   43: istore #4
    //   45: iload_1
    //   46: invokestatic getMode : (I)I
    //   49: istore #9
    //   51: iload_2
    //   52: invokestatic getMode : (I)I
    //   55: istore #7
    //   57: iload #9
    //   59: ldc -2147483648
    //   61: if_icmpne -> 197
    //   64: iload #4
    //   66: ifeq -> 78
    //   69: aload_0
    //   70: getfield mFixedWidthMinor : Landroid/util/TypedValue;
    //   73: astore #10
    //   75: goto -> 84
    //   78: aload_0
    //   79: getfield mFixedWidthMajor : Landroid/util/TypedValue;
    //   82: astore #10
    //   84: aload #10
    //   86: ifnull -> 197
    //   89: aload #10
    //   91: getfield type : I
    //   94: ifeq -> 197
    //   97: aload #10
    //   99: getfield type : I
    //   102: iconst_5
    //   103: if_icmpne -> 121
    //   106: aload #10
    //   108: aload #11
    //   110: invokevirtual getDimension : (Landroid/util/DisplayMetrics;)F
    //   113: fstore_3
    //   114: fload_3
    //   115: f2i
    //   116: istore #5
    //   118: goto -> 155
    //   121: aload #10
    //   123: getfield type : I
    //   126: bipush #6
    //   128: if_icmpne -> 152
    //   131: aload #10
    //   133: aload #11
    //   135: getfield widthPixels : I
    //   138: i2f
    //   139: aload #11
    //   141: getfield widthPixels : I
    //   144: i2f
    //   145: invokevirtual getFraction : (FF)F
    //   148: fstore_3
    //   149: goto -> 114
    //   152: iconst_0
    //   153: istore #5
    //   155: iload #5
    //   157: ifle -> 197
    //   160: iload #5
    //   162: aload_0
    //   163: getfield mDecorPadding : Landroid/graphics/Rect;
    //   166: getfield left : I
    //   169: aload_0
    //   170: getfield mDecorPadding : Landroid/graphics/Rect;
    //   173: getfield right : I
    //   176: iadd
    //   177: isub
    //   178: iload_1
    //   179: invokestatic getSize : (I)I
    //   182: invokestatic min : (II)I
    //   185: ldc 1073741824
    //   187: invokestatic makeMeasureSpec : (II)I
    //   190: istore #6
    //   192: iconst_1
    //   193: istore_1
    //   194: goto -> 206
    //   197: iconst_0
    //   198: istore #5
    //   200: iload_1
    //   201: istore #6
    //   203: iload #5
    //   205: istore_1
    //   206: iload_2
    //   207: istore #5
    //   209: iload #7
    //   211: ldc -2147483648
    //   213: if_icmpne -> 353
    //   216: iload #4
    //   218: ifeq -> 230
    //   221: aload_0
    //   222: getfield mFixedHeightMajor : Landroid/util/TypedValue;
    //   225: astore #10
    //   227: goto -> 236
    //   230: aload_0
    //   231: getfield mFixedHeightMinor : Landroid/util/TypedValue;
    //   234: astore #10
    //   236: iload_2
    //   237: istore #5
    //   239: aload #10
    //   241: ifnull -> 353
    //   244: iload_2
    //   245: istore #5
    //   247: aload #10
    //   249: getfield type : I
    //   252: ifeq -> 353
    //   255: aload #10
    //   257: getfield type : I
    //   260: iconst_5
    //   261: if_icmpne -> 279
    //   264: aload #10
    //   266: aload #11
    //   268: invokevirtual getDimension : (Landroid/util/DisplayMetrics;)F
    //   271: fstore_3
    //   272: fload_3
    //   273: f2i
    //   274: istore #7
    //   276: goto -> 313
    //   279: aload #10
    //   281: getfield type : I
    //   284: bipush #6
    //   286: if_icmpne -> 310
    //   289: aload #10
    //   291: aload #11
    //   293: getfield heightPixels : I
    //   296: i2f
    //   297: aload #11
    //   299: getfield heightPixels : I
    //   302: i2f
    //   303: invokevirtual getFraction : (FF)F
    //   306: fstore_3
    //   307: goto -> 272
    //   310: iconst_0
    //   311: istore #7
    //   313: iload_2
    //   314: istore #5
    //   316: iload #7
    //   318: ifle -> 353
    //   321: iload #7
    //   323: aload_0
    //   324: getfield mDecorPadding : Landroid/graphics/Rect;
    //   327: getfield top : I
    //   330: aload_0
    //   331: getfield mDecorPadding : Landroid/graphics/Rect;
    //   334: getfield bottom : I
    //   337: iadd
    //   338: isub
    //   339: iload_2
    //   340: invokestatic getSize : (I)I
    //   343: invokestatic min : (II)I
    //   346: ldc 1073741824
    //   348: invokestatic makeMeasureSpec : (II)I
    //   351: istore #5
    //   353: aload_0
    //   354: iload #6
    //   356: iload #5
    //   358: invokespecial onMeasure : (II)V
    //   361: aload_0
    //   362: invokevirtual getMeasuredWidth : ()I
    //   365: istore #7
    //   367: iload #7
    //   369: ldc 1073741824
    //   371: invokestatic makeMeasureSpec : (II)I
    //   374: istore #6
    //   376: iload_1
    //   377: ifne -> 519
    //   380: iload #9
    //   382: ldc -2147483648
    //   384: if_icmpne -> 519
    //   387: iload #4
    //   389: ifeq -> 401
    //   392: aload_0
    //   393: getfield mMinWidthMinor : Landroid/util/TypedValue;
    //   396: astore #10
    //   398: goto -> 407
    //   401: aload_0
    //   402: getfield mMinWidthMajor : Landroid/util/TypedValue;
    //   405: astore #10
    //   407: aload #10
    //   409: ifnull -> 519
    //   412: aload #10
    //   414: getfield type : I
    //   417: ifeq -> 519
    //   420: aload #10
    //   422: getfield type : I
    //   425: iconst_5
    //   426: if_icmpne -> 443
    //   429: aload #10
    //   431: aload #11
    //   433: invokevirtual getDimension : (Landroid/util/DisplayMetrics;)F
    //   436: fstore_3
    //   437: fload_3
    //   438: f2i
    //   439: istore_1
    //   440: goto -> 476
    //   443: aload #10
    //   445: getfield type : I
    //   448: bipush #6
    //   450: if_icmpne -> 474
    //   453: aload #10
    //   455: aload #11
    //   457: getfield widthPixels : I
    //   460: i2f
    //   461: aload #11
    //   463: getfield widthPixels : I
    //   466: i2f
    //   467: invokevirtual getFraction : (FF)F
    //   470: fstore_3
    //   471: goto -> 437
    //   474: iconst_0
    //   475: istore_1
    //   476: iload_1
    //   477: istore_2
    //   478: iload_1
    //   479: ifle -> 500
    //   482: iload_1
    //   483: aload_0
    //   484: getfield mDecorPadding : Landroid/graphics/Rect;
    //   487: getfield left : I
    //   490: aload_0
    //   491: getfield mDecorPadding : Landroid/graphics/Rect;
    //   494: getfield right : I
    //   497: iadd
    //   498: isub
    //   499: istore_2
    //   500: iload #7
    //   502: iload_2
    //   503: if_icmpge -> 519
    //   506: iload_2
    //   507: ldc 1073741824
    //   509: invokestatic makeMeasureSpec : (II)I
    //   512: istore_1
    //   513: iload #8
    //   515: istore_2
    //   516: goto -> 524
    //   519: iconst_0
    //   520: istore_2
    //   521: iload #6
    //   523: istore_1
    //   524: iload_2
    //   525: ifeq -> 535
    //   528: aload_0
    //   529: iload_1
    //   530: iload #5
    //   532: invokespecial onMeasure : (II)V
    //   535: return
  }
  
  public void setAttachListener(OnAttachListener paramOnAttachListener) {
    this.mAttachListener = paramOnAttachListener;
  }
  
  public void setDecorPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mDecorPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    if (ViewCompat.isLaidOut((View)this))
      requestLayout(); 
  }
  
  public static interface OnAttachListener {
    void onAttachedFromWindow();
    
    void onDetachedFromWindow();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\ContentFrameLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */