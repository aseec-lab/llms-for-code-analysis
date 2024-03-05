package androidx.core.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public final class AccessibilityServiceInfoCompat {
  public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
  
  public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
  
  public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
  
  public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
  
  public static final int FEEDBACK_ALL_MASK = -1;
  
  public static final int FEEDBACK_BRAILLE = 32;
  
  public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
  
  public static final int FLAG_REPORT_VIEW_IDS = 16;
  
  public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
  
  public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
  
  public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
  
  public static String capabilityToString(int paramInt) {
    return (paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 4) ? ((paramInt != 8) ? "UNKNOWN" : "CAPABILITY_CAN_FILTER_KEY_EVENTS") : "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY") : "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION") : "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
  }
  
  public static String feedbackTypeToString(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    while (paramInt > 0) {
      int i = 1 << Integer.numberOfTrailingZeros(paramInt);
      paramInt &= i ^ 0xFFFFFFFF;
      if (stringBuilder.length() > 1)
        stringBuilder.append(", "); 
      if (i != 1) {
        if (i != 2) {
          if (i != 4) {
            if (i != 8) {
              if (i != 16)
                continue; 
              stringBuilder.append("FEEDBACK_GENERIC");
              continue;
            } 
            stringBuilder.append("FEEDBACK_VISUAL");
            continue;
          } 
          stringBuilder.append("FEEDBACK_AUDIBLE");
          continue;
        } 
        stringBuilder.append("FEEDBACK_HAPTIC");
        continue;
      } 
      stringBuilder.append("FEEDBACK_SPOKEN");
    } 
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
  
  public static String flagToString(int paramInt) {
    return (paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 4) ? ((paramInt != 8) ? ((paramInt != 16) ? ((paramInt != 32) ? null : "FLAG_REQUEST_FILTER_KEY_EVENTS") : "FLAG_REPORT_VIEW_IDS") : "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY") : "FLAG_REQUEST_TOUCH_EXPLORATION_MODE") : "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS") : "DEFAULT";
  }
  
  public static int getCapabilities(AccessibilityServiceInfo paramAccessibilityServiceInfo) {
    return (Build.VERSION.SDK_INT >= 18) ? paramAccessibilityServiceInfo.getCapabilities() : (paramAccessibilityServiceInfo.getCanRetrieveWindowContent() ? 1 : 0);
  }
  
  public static String loadDescription(AccessibilityServiceInfo paramAccessibilityServiceInfo, PackageManager paramPackageManager) {
    return (Build.VERSION.SDK_INT >= 16) ? paramAccessibilityServiceInfo.loadDescription(paramPackageManager) : paramAccessibilityServiceInfo.getDescription();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\accessibilityservice\AccessibilityServiceInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */