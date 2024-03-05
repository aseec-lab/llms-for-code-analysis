package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityWindowInfo;

public class AccessibilityWindowInfoCompat {
  public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
  
  public static final int TYPE_APPLICATION = 1;
  
  public static final int TYPE_INPUT_METHOD = 2;
  
  public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
  
  public static final int TYPE_SYSTEM = 3;
  
  private static final int UNDEFINED = -1;
  
  private Object mInfo;
  
  private AccessibilityWindowInfoCompat(Object paramObject) {
    this.mInfo = paramObject;
  }
  
  public static AccessibilityWindowInfoCompat obtain() {
    return (Build.VERSION.SDK_INT >= 21) ? wrapNonNullInstance(AccessibilityWindowInfo.obtain()) : null;
  }
  
  public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat paramAccessibilityWindowInfoCompat) {
    int i = Build.VERSION.SDK_INT;
    AccessibilityWindowInfoCompat accessibilityWindowInfoCompat2 = null;
    AccessibilityWindowInfoCompat accessibilityWindowInfoCompat1 = accessibilityWindowInfoCompat2;
    if (i >= 21)
      if (paramAccessibilityWindowInfoCompat == null) {
        accessibilityWindowInfoCompat1 = accessibilityWindowInfoCompat2;
      } else {
        accessibilityWindowInfoCompat1 = wrapNonNullInstance(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo)paramAccessibilityWindowInfoCompat.mInfo));
      }  
    return accessibilityWindowInfoCompat1;
  }
  
  private static String typeToString(int paramInt) {
    return (paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? ((paramInt != 4) ? "<UNKNOWN>" : "TYPE_ACCESSIBILITY_OVERLAY") : "TYPE_SYSTEM") : "TYPE_INPUT_METHOD") : "TYPE_APPLICATION";
  }
  
  static AccessibilityWindowInfoCompat wrapNonNullInstance(Object paramObject) {
    return (paramObject != null) ? new AccessibilityWindowInfoCompat(paramObject) : null;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (!(paramObject instanceof AccessibilityWindowInfoCompat))
      return false; 
    AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat)paramObject;
    paramObject = this.mInfo;
    if (paramObject == null) {
      if (accessibilityWindowInfoCompat.mInfo != null)
        return false; 
    } else if (!paramObject.equals(accessibilityWindowInfoCompat.mInfo)) {
      return false;
    } 
    return true;
  }
  
  public AccessibilityNodeInfoCompat getAnchor() {
    return (Build.VERSION.SDK_INT >= 24) ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getAnchor()) : null;
  }
  
  public void getBoundsInScreen(Rect paramRect) {
    if (Build.VERSION.SDK_INT >= 21)
      ((AccessibilityWindowInfo)this.mInfo).getBoundsInScreen(paramRect); 
  }
  
  public AccessibilityWindowInfoCompat getChild(int paramInt) {
    return (Build.VERSION.SDK_INT >= 21) ? wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getChild(paramInt)) : null;
  }
  
  public int getChildCount() {
    return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityWindowInfo)this.mInfo).getChildCount() : 0;
  }
  
  public int getId() {
    return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityWindowInfo)this.mInfo).getId() : -1;
  }
  
  public int getLayer() {
    return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityWindowInfo)this.mInfo).getLayer() : -1;
  }
  
  public AccessibilityWindowInfoCompat getParent() {
    return (Build.VERSION.SDK_INT >= 21) ? wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getParent()) : null;
  }
  
  public AccessibilityNodeInfoCompat getRoot() {
    return (Build.VERSION.SDK_INT >= 21) ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getRoot()) : null;
  }
  
  public CharSequence getTitle() {
    return (Build.VERSION.SDK_INT >= 24) ? ((AccessibilityWindowInfo)this.mInfo).getTitle() : null;
  }
  
  public int getType() {
    return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityWindowInfo)this.mInfo).getType() : -1;
  }
  
  public int hashCode() {
    int i;
    Object object = this.mInfo;
    if (object == null) {
      i = 0;
    } else {
      i = object.hashCode();
    } 
    return i;
  }
  
  public boolean isAccessibilityFocused() {
    return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityWindowInfo)this.mInfo).isAccessibilityFocused() : true;
  }
  
  public boolean isActive() {
    return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityWindowInfo)this.mInfo).isActive() : true;
  }
  
  public boolean isFocused() {
    return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityWindowInfo)this.mInfo).isFocused() : true;
  }
  
  public void recycle() {
    if (Build.VERSION.SDK_INT >= 21)
      ((AccessibilityWindowInfo)this.mInfo).recycle(); 
  }
  
  public String toString() {
    boolean bool1;
    StringBuilder stringBuilder = new StringBuilder();
    Rect rect = new Rect();
    getBoundsInScreen(rect);
    stringBuilder.append("AccessibilityWindowInfo[");
    stringBuilder.append("id=");
    stringBuilder.append(getId());
    stringBuilder.append(", type=");
    stringBuilder.append(typeToString(getType()));
    stringBuilder.append(", layer=");
    stringBuilder.append(getLayer());
    stringBuilder.append(", bounds=");
    stringBuilder.append(rect);
    stringBuilder.append(", focused=");
    stringBuilder.append(isFocused());
    stringBuilder.append(", active=");
    stringBuilder.append(isActive());
    stringBuilder.append(", hasParent=");
    AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = getParent();
    boolean bool2 = true;
    if (accessibilityWindowInfoCompat != null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    stringBuilder.append(bool1);
    stringBuilder.append(", hasChildren=");
    if (getChildCount() > 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    stringBuilder.append(bool1);
    stringBuilder.append(']');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\accessibility\AccessibilityWindowInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */