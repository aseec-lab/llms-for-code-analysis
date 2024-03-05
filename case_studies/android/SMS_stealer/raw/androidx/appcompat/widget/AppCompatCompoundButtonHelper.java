package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;

class AppCompatCompoundButtonHelper {
  private ColorStateList mButtonTintList = null;
  
  private PorterDuff.Mode mButtonTintMode = null;
  
  private boolean mHasButtonTint = false;
  
  private boolean mHasButtonTintMode = false;
  
  private boolean mSkipNextApply;
  
  private final CompoundButton mView;
  
  AppCompatCompoundButtonHelper(CompoundButton paramCompoundButton) {
    this.mView = paramCompoundButton;
  }
  
  void applyButtonTint() {
    Drawable drawable = CompoundButtonCompat.getButtonDrawable(this.mView);
    if (drawable != null && (this.mHasButtonTint || this.mHasButtonTintMode)) {
      drawable = DrawableCompat.wrap(drawable).mutate();
      if (this.mHasButtonTint)
        DrawableCompat.setTintList(drawable, this.mButtonTintList); 
      if (this.mHasButtonTintMode)
        DrawableCompat.setTintMode(drawable, this.mButtonTintMode); 
      if (drawable.isStateful())
        drawable.setState(this.mView.getDrawableState()); 
      this.mView.setButtonDrawable(drawable);
    } 
  }
  
  int getCompoundPaddingLeft(int paramInt) {
    int i = paramInt;
    if (Build.VERSION.SDK_INT < 17) {
      Drawable drawable = CompoundButtonCompat.getButtonDrawable(this.mView);
      i = paramInt;
      if (drawable != null)
        i = paramInt + drawable.getIntrinsicWidth(); 
    } 
    return i;
  }
  
  ColorStateList getSupportButtonTintList() {
    return this.mButtonTintList;
  }
  
  PorterDuff.Mode getSupportButtonTintMode() {
    return this.mButtonTintMode;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mView : Landroid/widget/CompoundButton;
    //   4: invokevirtual getContext : ()Landroid/content/Context;
    //   7: aload_1
    //   8: getstatic androidx/appcompat/R$styleable.CompoundButton : [I
    //   11: iload_2
    //   12: iconst_0
    //   13: invokestatic obtainStyledAttributes : (Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroidx/appcompat/widget/TintTypedArray;
    //   16: astore_3
    //   17: aload_0
    //   18: getfield mView : Landroid/widget/CompoundButton;
    //   21: astore #4
    //   23: aload #4
    //   25: aload #4
    //   27: invokevirtual getContext : ()Landroid/content/Context;
    //   30: getstatic androidx/appcompat/R$styleable.CompoundButton : [I
    //   33: aload_1
    //   34: aload_3
    //   35: invokevirtual getWrappedTypeArray : ()Landroid/content/res/TypedArray;
    //   38: iload_2
    //   39: iconst_0
    //   40: invokestatic saveAttributeDataForStyleable : (Landroid/view/View;Landroid/content/Context;[ILandroid/util/AttributeSet;Landroid/content/res/TypedArray;II)V
    //   43: aload_3
    //   44: getstatic androidx/appcompat/R$styleable.CompoundButton_buttonCompat : I
    //   47: invokevirtual hasValue : (I)Z
    //   50: ifeq -> 89
    //   53: aload_3
    //   54: getstatic androidx/appcompat/R$styleable.CompoundButton_buttonCompat : I
    //   57: iconst_0
    //   58: invokevirtual getResourceId : (II)I
    //   61: istore_2
    //   62: iload_2
    //   63: ifeq -> 89
    //   66: aload_0
    //   67: getfield mView : Landroid/widget/CompoundButton;
    //   70: aload_0
    //   71: getfield mView : Landroid/widget/CompoundButton;
    //   74: invokevirtual getContext : ()Landroid/content/Context;
    //   77: iload_2
    //   78: invokestatic getDrawable : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   81: invokevirtual setButtonDrawable : (Landroid/graphics/drawable/Drawable;)V
    //   84: iconst_1
    //   85: istore_2
    //   86: goto -> 91
    //   89: iconst_0
    //   90: istore_2
    //   91: iload_2
    //   92: ifne -> 136
    //   95: aload_3
    //   96: getstatic androidx/appcompat/R$styleable.CompoundButton_android_button : I
    //   99: invokevirtual hasValue : (I)Z
    //   102: ifeq -> 136
    //   105: aload_3
    //   106: getstatic androidx/appcompat/R$styleable.CompoundButton_android_button : I
    //   109: iconst_0
    //   110: invokevirtual getResourceId : (II)I
    //   113: istore_2
    //   114: iload_2
    //   115: ifeq -> 136
    //   118: aload_0
    //   119: getfield mView : Landroid/widget/CompoundButton;
    //   122: aload_0
    //   123: getfield mView : Landroid/widget/CompoundButton;
    //   126: invokevirtual getContext : ()Landroid/content/Context;
    //   129: iload_2
    //   130: invokestatic getDrawable : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   133: invokevirtual setButtonDrawable : (Landroid/graphics/drawable/Drawable;)V
    //   136: aload_3
    //   137: getstatic androidx/appcompat/R$styleable.CompoundButton_buttonTint : I
    //   140: invokevirtual hasValue : (I)Z
    //   143: ifeq -> 160
    //   146: aload_0
    //   147: getfield mView : Landroid/widget/CompoundButton;
    //   150: aload_3
    //   151: getstatic androidx/appcompat/R$styleable.CompoundButton_buttonTint : I
    //   154: invokevirtual getColorStateList : (I)Landroid/content/res/ColorStateList;
    //   157: invokestatic setButtonTintList : (Landroid/widget/CompoundButton;Landroid/content/res/ColorStateList;)V
    //   160: aload_3
    //   161: getstatic androidx/appcompat/R$styleable.CompoundButton_buttonTintMode : I
    //   164: invokevirtual hasValue : (I)Z
    //   167: ifeq -> 189
    //   170: aload_0
    //   171: getfield mView : Landroid/widget/CompoundButton;
    //   174: aload_3
    //   175: getstatic androidx/appcompat/R$styleable.CompoundButton_buttonTintMode : I
    //   178: iconst_m1
    //   179: invokevirtual getInt : (II)I
    //   182: aconst_null
    //   183: invokestatic parseTintMode : (ILandroid/graphics/PorterDuff$Mode;)Landroid/graphics/PorterDuff$Mode;
    //   186: invokestatic setButtonTintMode : (Landroid/widget/CompoundButton;Landroid/graphics/PorterDuff$Mode;)V
    //   189: aload_3
    //   190: invokevirtual recycle : ()V
    //   193: return
    //   194: astore_1
    //   195: aload_3
    //   196: invokevirtual recycle : ()V
    //   199: aload_1
    //   200: athrow
    //   201: astore_1
    //   202: goto -> 89
    // Exception table:
    //   from	to	target	type
    //   43	62	194	finally
    //   66	84	201	android/content/res/Resources$NotFoundException
    //   66	84	194	finally
    //   95	114	194	finally
    //   118	136	194	finally
    //   136	160	194	finally
    //   160	189	194	finally
  }
  
  void onSetButtonDrawable() {
    if (this.mSkipNextApply) {
      this.mSkipNextApply = false;
      return;
    } 
    this.mSkipNextApply = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintList(ColorStateList paramColorStateList) {
    this.mButtonTintList = paramColorStateList;
    this.mHasButtonTint = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintMode(PorterDuff.Mode paramMode) {
    this.mButtonTintMode = paramMode;
    this.mHasButtonTintMode = true;
    applyButtonTint();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatCompoundButtonHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */