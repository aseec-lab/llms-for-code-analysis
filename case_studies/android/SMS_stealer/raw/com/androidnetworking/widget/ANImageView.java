package com.androidnetworking.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.androidnetworking.error.ANError;
import com.androidnetworking.internal.ANImageLoader;

public class ANImageView extends AppCompatImageView {
  private int mDefaultImageId;
  
  private int mErrorImageId;
  
  private ANImageLoader.ImageContainer mImageContainer;
  
  private String mUrl;
  
  public ANImageView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ANImageView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ANImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void setDefaultImageOrNull() {
    int i = this.mDefaultImageId;
    if (i != 0) {
      setImageResource(i);
    } else {
      setImageBitmap(null);
    } 
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    invalidate();
  }
  
  void loadImageIfNecessary(boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getWidth : ()I
    //   4: istore #6
    //   6: aload_0
    //   7: invokevirtual getHeight : ()I
    //   10: istore #5
    //   12: aload_0
    //   13: invokevirtual getScaleType : ()Landroid/widget/ImageView$ScaleType;
    //   16: astore #8
    //   18: aload_0
    //   19: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   22: astore #9
    //   24: iconst_1
    //   25: istore #4
    //   27: aload #9
    //   29: ifnull -> 76
    //   32: aload_0
    //   33: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   36: getfield width : I
    //   39: bipush #-2
    //   41: if_icmpne -> 49
    //   44: iconst_1
    //   45: istore_2
    //   46: goto -> 51
    //   49: iconst_0
    //   50: istore_2
    //   51: iload_2
    //   52: istore_3
    //   53: aload_0
    //   54: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   57: getfield height : I
    //   60: bipush #-2
    //   62: if_icmpne -> 78
    //   65: iconst_1
    //   66: istore #7
    //   68: iload_2
    //   69: istore_3
    //   70: iload #7
    //   72: istore_2
    //   73: goto -> 80
    //   76: iconst_0
    //   77: istore_3
    //   78: iconst_0
    //   79: istore_2
    //   80: iload_3
    //   81: ifeq -> 91
    //   84: iload_2
    //   85: ifeq -> 91
    //   88: goto -> 94
    //   91: iconst_0
    //   92: istore #4
    //   94: iload #6
    //   96: ifne -> 110
    //   99: iload #5
    //   101: ifne -> 110
    //   104: iload #4
    //   106: ifne -> 110
    //   109: return
    //   110: aload_0
    //   111: getfield mUrl : Ljava/lang/String;
    //   114: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   117: ifeq -> 146
    //   120: aload_0
    //   121: getfield mImageContainer : Lcom/androidnetworking/internal/ANImageLoader$ImageContainer;
    //   124: astore #8
    //   126: aload #8
    //   128: ifnull -> 141
    //   131: aload #8
    //   133: invokevirtual cancelRequest : ()V
    //   136: aload_0
    //   137: aconst_null
    //   138: putfield mImageContainer : Lcom/androidnetworking/internal/ANImageLoader$ImageContainer;
    //   141: aload_0
    //   142: invokespecial setDefaultImageOrNull : ()V
    //   145: return
    //   146: aload_0
    //   147: getfield mImageContainer : Lcom/androidnetworking/internal/ANImageLoader$ImageContainer;
    //   150: astore #9
    //   152: aload #9
    //   154: ifnull -> 194
    //   157: aload #9
    //   159: invokevirtual getRequestUrl : ()Ljava/lang/String;
    //   162: ifnull -> 194
    //   165: aload_0
    //   166: getfield mImageContainer : Lcom/androidnetworking/internal/ANImageLoader$ImageContainer;
    //   169: invokevirtual getRequestUrl : ()Ljava/lang/String;
    //   172: aload_0
    //   173: getfield mUrl : Ljava/lang/String;
    //   176: invokevirtual equals : (Ljava/lang/Object;)Z
    //   179: ifeq -> 183
    //   182: return
    //   183: aload_0
    //   184: getfield mImageContainer : Lcom/androidnetworking/internal/ANImageLoader$ImageContainer;
    //   187: invokevirtual cancelRequest : ()V
    //   190: aload_0
    //   191: invokespecial setDefaultImageOrNull : ()V
    //   194: iload #6
    //   196: istore #4
    //   198: iload_3
    //   199: ifeq -> 205
    //   202: iconst_0
    //   203: istore #4
    //   205: iload_2
    //   206: ifeq -> 214
    //   209: iconst_0
    //   210: istore_2
    //   211: goto -> 217
    //   214: iload #5
    //   216: istore_2
    //   217: aload_0
    //   218: invokestatic getInstance : ()Lcom/androidnetworking/internal/ANImageLoader;
    //   221: aload_0
    //   222: getfield mUrl : Ljava/lang/String;
    //   225: new com/androidnetworking/widget/ANImageView$1
    //   228: dup
    //   229: aload_0
    //   230: iload_1
    //   231: invokespecial <init> : (Lcom/androidnetworking/widget/ANImageView;Z)V
    //   234: iload #4
    //   236: iload_2
    //   237: aload #8
    //   239: invokevirtual get : (Ljava/lang/String;Lcom/androidnetworking/internal/ANImageLoader$ImageListener;IILandroid/widget/ImageView$ScaleType;)Lcom/androidnetworking/internal/ANImageLoader$ImageContainer;
    //   242: putfield mImageContainer : Lcom/androidnetworking/internal/ANImageLoader$ImageContainer;
    //   245: return
  }
  
  protected void onDetachedFromWindow() {
    ANImageLoader.ImageContainer imageContainer = this.mImageContainer;
    if (imageContainer != null) {
      imageContainer.cancelRequest();
      setImageBitmap(null);
      this.mImageContainer = null;
    } 
    super.onDetachedFromWindow();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    loadImageIfNecessary(true);
  }
  
  public void setDefaultImageResId(int paramInt) {
    this.mDefaultImageId = paramInt;
  }
  
  public void setErrorImageResId(int paramInt) {
    this.mErrorImageId = paramInt;
  }
  
  public void setImageUrl(String paramString) {
    this.mUrl = paramString;
    loadImageIfNecessary(false);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\widget\ANImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */