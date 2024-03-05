package androidx.core.graphics;

import android.graphics.Rect;

public final class Insets {
  public static final Insets NONE = new Insets(0, 0, 0, 0);
  
  public final int bottom;
  
  public final int left;
  
  public final int right;
  
  public final int top;
  
  private Insets(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.left = paramInt1;
    this.top = paramInt2;
    this.right = paramInt3;
    this.bottom = paramInt4;
  }
  
  public static Insets of(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return (paramInt1 == 0 && paramInt2 == 0 && paramInt3 == 0 && paramInt4 == 0) ? NONE : new Insets(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static Insets of(Rect paramRect) {
    return of(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
  }
  
  public static Insets toCompatInsets(android.graphics.Insets paramInsets) {
    return of(paramInsets.left, paramInsets.top, paramInsets.right, paramInsets.bottom);
  }
  
  @Deprecated
  public static Insets wrap(android.graphics.Insets paramInsets) {
    return toCompatInsets(paramInsets);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    return (this.bottom != ((Insets)paramObject).bottom) ? false : ((this.left != ((Insets)paramObject).left) ? false : ((this.right != ((Insets)paramObject).right) ? false : (!(this.top != ((Insets)paramObject).top))));
  }
  
  public int hashCode() {
    return ((this.left * 31 + this.top) * 31 + this.right) * 31 + this.bottom;
  }
  
  public android.graphics.Insets toPlatformInsets() {
    return android.graphics.Insets.of(this.left, this.top, this.right, this.bottom);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Insets{left=");
    stringBuilder.append(this.left);
    stringBuilder.append(", top=");
    stringBuilder.append(this.top);
    stringBuilder.append(", right=");
    stringBuilder.append(this.right);
    stringBuilder.append(", bottom=");
    stringBuilder.append(this.bottom);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\Insets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */