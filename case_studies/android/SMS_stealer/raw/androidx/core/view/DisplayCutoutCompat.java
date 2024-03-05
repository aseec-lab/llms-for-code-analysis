package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import java.util.List;

public final class DisplayCutoutCompat {
  private final Object mDisplayCutout;
  
  public DisplayCutoutCompat(Rect paramRect, List<Rect> paramList) {
    this(paramRect);
  }
  
  private DisplayCutoutCompat(Object paramObject) {
    this.mDisplayCutout = paramObject;
  }
  
  static DisplayCutoutCompat wrap(Object paramObject) {
    if (paramObject == null) {
      paramObject = null;
    } else {
      paramObject = new DisplayCutoutCompat(paramObject);
    } 
    return (DisplayCutoutCompat)paramObject;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    Object object = this.mDisplayCutout;
    if (object == null) {
      if (((DisplayCutoutCompat)paramObject).mDisplayCutout != null)
        bool = false; 
    } else {
      bool = object.equals(((DisplayCutoutCompat)paramObject).mDisplayCutout);
    } 
    return bool;
  }
  
  public List<Rect> getBoundingRects() {
    return (Build.VERSION.SDK_INT >= 28) ? ((DisplayCutout)this.mDisplayCutout).getBoundingRects() : null;
  }
  
  public int getSafeInsetBottom() {
    return (Build.VERSION.SDK_INT >= 28) ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetBottom() : 0;
  }
  
  public int getSafeInsetLeft() {
    return (Build.VERSION.SDK_INT >= 28) ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetLeft() : 0;
  }
  
  public int getSafeInsetRight() {
    return (Build.VERSION.SDK_INT >= 28) ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetRight() : 0;
  }
  
  public int getSafeInsetTop() {
    return (Build.VERSION.SDK_INT >= 28) ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetTop() : 0;
  }
  
  public int hashCode() {
    int i;
    Object object = this.mDisplayCutout;
    if (object == null) {
      i = 0;
    } else {
      i = object.hashCode();
    } 
    return i;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("DisplayCutoutCompat{");
    stringBuilder.append(this.mDisplayCutout);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  DisplayCutout unwrap() {
    return (DisplayCutout)this.mDisplayCutout;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\DisplayCutoutCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */