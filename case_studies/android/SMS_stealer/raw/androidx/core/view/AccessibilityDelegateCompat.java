package androidx.core.view;

import android.os.Build;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.core.R;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class AccessibilityDelegateCompat {
  private static final View.AccessibilityDelegate DEFAULT_DELEGATE = new View.AccessibilityDelegate();
  
  private final View.AccessibilityDelegate mBridge;
  
  private final View.AccessibilityDelegate mOriginalDelegate;
  
  public AccessibilityDelegateCompat() {
    this(DEFAULT_DELEGATE);
  }
  
  public AccessibilityDelegateCompat(View.AccessibilityDelegate paramAccessibilityDelegate) {
    this.mOriginalDelegate = paramAccessibilityDelegate;
    this.mBridge = new AccessibilityDelegateAdapter(this);
  }
  
  static List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> getActionList(View paramView) {
    List<?> list2 = (List)paramView.getTag(R.id.tag_accessibility_actions);
    List<?> list1 = list2;
    if (list2 == null)
      list1 = Collections.emptyList(); 
    return (List)list1;
  }
  
  private boolean isSpanStillValid(ClickableSpan paramClickableSpan, View paramView) {
    if (paramClickableSpan != null) {
      ClickableSpan[] arrayOfClickableSpan = AccessibilityNodeInfoCompat.getClickableSpans(paramView.createAccessibilityNodeInfo().getText());
      for (byte b = 0; arrayOfClickableSpan != null && b < arrayOfClickableSpan.length; b++) {
        if (paramClickableSpan.equals(arrayOfClickableSpan[b]))
          return true; 
      } 
    } 
    return false;
  }
  
  private boolean performClickableSpanAction(int paramInt, View paramView) {
    SparseArray sparseArray = (SparseArray)paramView.getTag(R.id.tag_accessibility_clickable_spans);
    if (sparseArray != null) {
      WeakReference<ClickableSpan> weakReference = (WeakReference)sparseArray.get(paramInt);
      if (weakReference != null) {
        ClickableSpan clickableSpan = weakReference.get();
        if (isSpanStillValid(clickableSpan, paramView)) {
          clickableSpan.onClick(paramView);
          return true;
        } 
      } 
    } 
    return false;
  }
  
  public boolean dispatchPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    return this.mOriginalDelegate.dispatchPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView) {
    if (Build.VERSION.SDK_INT >= 16) {
      AccessibilityNodeProvider accessibilityNodeProvider = this.mOriginalDelegate.getAccessibilityNodeProvider(paramView);
      if (accessibilityNodeProvider != null)
        return new AccessibilityNodeProviderCompat(accessibilityNodeProvider); 
    } 
    return null;
  }
  
  View.AccessibilityDelegate getBridge() {
    return this.mBridge;
  }
  
  public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    this.mOriginalDelegate.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat.unwrap());
  }
  
  public void onPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    this.mOriginalDelegate.onPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent) {
    return this.mOriginalDelegate.onRequestSendAccessibilityEvent(paramViewGroup, paramView, paramAccessibilityEvent);
  }
  
  public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle) {
    List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> list = getActionList(paramView);
    boolean bool1 = false;
    byte b = 0;
    while (true) {
      bool2 = bool1;
      if (b < list.size()) {
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = list.get(b);
        if (accessibilityActionCompat.getId() == paramInt) {
          bool2 = accessibilityActionCompat.perform(paramView, paramBundle);
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    bool1 = bool2;
    if (!bool2) {
      bool1 = bool2;
      if (Build.VERSION.SDK_INT >= 16)
        bool1 = this.mOriginalDelegate.performAccessibilityAction(paramView, paramInt, paramBundle); 
    } 
    boolean bool2 = bool1;
    if (!bool1) {
      bool2 = bool1;
      if (paramInt == R.id.accessibility_action_clickable_span)
        bool2 = performClickableSpanAction(paramBundle.getInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", -1), paramView); 
    } 
    return bool2;
  }
  
  public void sendAccessibilityEvent(View paramView, int paramInt) {
    this.mOriginalDelegate.sendAccessibilityEvent(paramView, paramInt);
  }
  
  public void sendAccessibilityEventUnchecked(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    this.mOriginalDelegate.sendAccessibilityEventUnchecked(paramView, paramAccessibilityEvent);
  }
  
  static final class AccessibilityDelegateAdapter extends View.AccessibilityDelegate {
    final AccessibilityDelegateCompat mCompat;
    
    AccessibilityDelegateAdapter(AccessibilityDelegateCompat param1AccessibilityDelegateCompat) {
      this.mCompat = param1AccessibilityDelegateCompat;
    }
    
    public boolean dispatchPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return this.mCompat.dispatchPopulateAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View param1View) {
      AccessibilityNodeProviderCompat accessibilityNodeProviderCompat = this.mCompat.getAccessibilityNodeProvider(param1View);
      if (accessibilityNodeProviderCompat != null) {
        AccessibilityNodeProvider accessibilityNodeProvider = (AccessibilityNodeProvider)accessibilityNodeProviderCompat.getProvider();
      } else {
        accessibilityNodeProviderCompat = null;
      } 
      return (AccessibilityNodeProvider)accessibilityNodeProviderCompat;
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      this.mCompat.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfo param1AccessibilityNodeInfo) {
      AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.wrap(param1AccessibilityNodeInfo);
      accessibilityNodeInfoCompat.setScreenReaderFocusable(ViewCompat.isScreenReaderFocusable(param1View));
      accessibilityNodeInfoCompat.setHeading(ViewCompat.isAccessibilityHeading(param1View));
      accessibilityNodeInfoCompat.setPaneTitle(ViewCompat.getAccessibilityPaneTitle(param1View));
      this.mCompat.onInitializeAccessibilityNodeInfo(param1View, accessibilityNodeInfoCompat);
      accessibilityNodeInfoCompat.addSpansToExtras(param1AccessibilityNodeInfo.getText(), param1View);
      List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> list = AccessibilityDelegateCompat.getActionList(param1View);
      for (byte b = 0; b < list.size(); b++)
        accessibilityNodeInfoCompat.addAction(list.get(b)); 
    }
    
    public void onPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      this.mCompat.onPopulateAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup param1ViewGroup, View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return this.mCompat.onRequestSendAccessibilityEvent(param1ViewGroup, param1View, param1AccessibilityEvent);
    }
    
    public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
      return this.mCompat.performAccessibilityAction(param1View, param1Int, param1Bundle);
    }
    
    public void sendAccessibilityEvent(View param1View, int param1Int) {
      this.mCompat.sendAccessibilityEvent(param1View, param1Int);
    }
    
    public void sendAccessibilityEventUnchecked(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      this.mCompat.sendAccessibilityEventUnchecked(param1View, param1AccessibilityEvent);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\AccessibilityDelegateCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */