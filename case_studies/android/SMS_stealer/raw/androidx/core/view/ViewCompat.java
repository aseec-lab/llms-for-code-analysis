package androidx.core.view;

import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.collection.SimpleArrayMap;
import androidx.core.R;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewCompat {
  private static final int[] ACCESSIBILITY_ACTIONS_RESOURCE_IDS;
  
  public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
  
  public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
  
  public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
  
  public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
  
  public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
  
  public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
  
  public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
  
  @Deprecated
  public static final int LAYER_TYPE_HARDWARE = 2;
  
  @Deprecated
  public static final int LAYER_TYPE_NONE = 0;
  
  @Deprecated
  public static final int LAYER_TYPE_SOFTWARE = 1;
  
  public static final int LAYOUT_DIRECTION_INHERIT = 2;
  
  public static final int LAYOUT_DIRECTION_LOCALE = 3;
  
  public static final int LAYOUT_DIRECTION_LTR = 0;
  
  public static final int LAYOUT_DIRECTION_RTL = 1;
  
  @Deprecated
  public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
  
  @Deprecated
  public static final int MEASURED_SIZE_MASK = 16777215;
  
  @Deprecated
  public static final int MEASURED_STATE_MASK = -16777216;
  
  @Deprecated
  public static final int MEASURED_STATE_TOO_SMALL = 16777216;
  
  @Deprecated
  public static final int OVER_SCROLL_ALWAYS = 0;
  
  @Deprecated
  public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
  
  @Deprecated
  public static final int OVER_SCROLL_NEVER = 2;
  
  public static final int SCROLL_AXIS_HORIZONTAL = 1;
  
  public static final int SCROLL_AXIS_NONE = 0;
  
  public static final int SCROLL_AXIS_VERTICAL = 2;
  
  public static final int SCROLL_INDICATOR_BOTTOM = 2;
  
  public static final int SCROLL_INDICATOR_END = 32;
  
  public static final int SCROLL_INDICATOR_LEFT = 4;
  
  public static final int SCROLL_INDICATOR_RIGHT = 8;
  
  public static final int SCROLL_INDICATOR_START = 16;
  
  public static final int SCROLL_INDICATOR_TOP = 1;
  
  private static final String TAG = "ViewCompat";
  
  public static final int TYPE_NON_TOUCH = 1;
  
  public static final int TYPE_TOUCH = 0;
  
  private static boolean sAccessibilityDelegateCheckFailed;
  
  private static Field sAccessibilityDelegateField;
  
  private static AccessibilityPaneVisibilityManager sAccessibilityPaneVisibilityManager;
  
  private static Method sChildrenDrawingOrderMethod;
  
  private static Method sDispatchFinishTemporaryDetach;
  
  private static Method sDispatchStartTemporaryDetach;
  
  private static Field sMinHeightField;
  
  private static boolean sMinHeightFieldFetched;
  
  private static Field sMinWidthField;
  
  private static boolean sMinWidthFieldFetched;
  
  private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
  
  private static boolean sTempDetachBound;
  
  private static ThreadLocal<Rect> sThreadLocalRect;
  
  private static WeakHashMap<View, String> sTransitionNameMap;
  
  private static WeakHashMap<View, ViewPropertyAnimatorCompat> sViewPropertyAnimatorMap = null;
  
  static {
    sAccessibilityDelegateCheckFailed = false;
    ACCESSIBILITY_ACTIONS_RESOURCE_IDS = new int[] { 
        R.id.accessibility_custom_action_0, R.id.accessibility_custom_action_1, R.id.accessibility_custom_action_2, R.id.accessibility_custom_action_3, R.id.accessibility_custom_action_4, R.id.accessibility_custom_action_5, R.id.accessibility_custom_action_6, R.id.accessibility_custom_action_7, R.id.accessibility_custom_action_8, R.id.accessibility_custom_action_9, 
        R.id.accessibility_custom_action_10, R.id.accessibility_custom_action_11, R.id.accessibility_custom_action_12, R.id.accessibility_custom_action_13, R.id.accessibility_custom_action_14, R.id.accessibility_custom_action_15, R.id.accessibility_custom_action_16, R.id.accessibility_custom_action_17, R.id.accessibility_custom_action_18, R.id.accessibility_custom_action_19, 
        R.id.accessibility_custom_action_20, R.id.accessibility_custom_action_21, R.id.accessibility_custom_action_22, R.id.accessibility_custom_action_23, R.id.accessibility_custom_action_24, R.id.accessibility_custom_action_25, R.id.accessibility_custom_action_26, R.id.accessibility_custom_action_27, R.id.accessibility_custom_action_28, R.id.accessibility_custom_action_29, 
        R.id.accessibility_custom_action_30, R.id.accessibility_custom_action_31 };
    sAccessibilityPaneVisibilityManager = new AccessibilityPaneVisibilityManager();
  }
  
  private static AccessibilityViewProperty<Boolean> accessibilityHeadingProperty() {
    return new AccessibilityViewProperty<Boolean>(R.id.tag_accessibility_heading, Boolean.class, 28) {
        Boolean frameworkGet(View param1View) {
          return Boolean.valueOf(param1View.isAccessibilityHeading());
        }
        
        void frameworkSet(View param1View, Boolean param1Boolean) {
          param1View.setAccessibilityHeading(param1Boolean.booleanValue());
        }
        
        boolean shouldUpdate(Boolean param1Boolean1, Boolean param1Boolean2) {
          return booleanNullToFalseEquals(param1Boolean1, param1Boolean2) ^ true;
        }
      };
  }
  
  public static int addAccessibilityAction(View paramView, CharSequence paramCharSequence, AccessibilityViewCommand paramAccessibilityViewCommand) {
    int i = getAvailableActionIdFromResources(paramView);
    if (i != -1)
      addAccessibilityAction(paramView, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, paramCharSequence, paramAccessibilityViewCommand)); 
    return i;
  }
  
  private static void addAccessibilityAction(View paramView, AccessibilityNodeInfoCompat.AccessibilityActionCompat paramAccessibilityActionCompat) {
    if (Build.VERSION.SDK_INT >= 21) {
      getOrCreateAccessibilityDelegateCompat(paramView);
      removeActionWithId(paramAccessibilityActionCompat.getId(), paramView);
      getActionList(paramView).add(paramAccessibilityActionCompat);
      notifyViewAccessibilityStateChangedIfNeeded(paramView, 0);
    } 
  }
  
  public static void addKeyboardNavigationClusters(View paramView, Collection<View> paramCollection, int paramInt) {
    if (Build.VERSION.SDK_INT >= 26)
      paramView.addKeyboardNavigationClusters(paramCollection, paramInt); 
  }
  
  public static void addOnUnhandledKeyEventListener(View paramView, final OnUnhandledKeyEventListenerCompat listener) {
    if (Build.VERSION.SDK_INT >= 28) {
      SimpleArrayMap simpleArrayMap2 = (SimpleArrayMap)paramView.getTag(R.id.tag_unhandled_key_listeners);
      SimpleArrayMap simpleArrayMap1 = simpleArrayMap2;
      if (simpleArrayMap2 == null) {
        simpleArrayMap1 = new SimpleArrayMap();
        paramView.setTag(R.id.tag_unhandled_key_listeners, simpleArrayMap1);
      } 
      View.OnUnhandledKeyEventListener onUnhandledKeyEventListener = new View.OnUnhandledKeyEventListener() {
          final ViewCompat.OnUnhandledKeyEventListenerCompat val$listener;
          
          public boolean onUnhandledKeyEvent(View param1View, KeyEvent param1KeyEvent) {
            return listener.onUnhandledKeyEvent(param1View, param1KeyEvent);
          }
        };
      simpleArrayMap1.put(listener, onUnhandledKeyEventListener);
      paramView.addOnUnhandledKeyEventListener(onUnhandledKeyEventListener);
      return;
    } 
    ArrayList<OnUnhandledKeyEventListenerCompat> arrayList2 = (ArrayList)paramView.getTag(R.id.tag_unhandled_key_listeners);
    ArrayList<OnUnhandledKeyEventListenerCompat> arrayList1 = arrayList2;
    if (arrayList2 == null) {
      arrayList1 = new ArrayList();
      paramView.setTag(R.id.tag_unhandled_key_listeners, arrayList1);
    } 
    arrayList1.add(listener);
    if (arrayList1.size() == 1)
      UnhandledKeyEventManager.registerListeningView(paramView); 
  }
  
  public static ViewPropertyAnimatorCompat animate(View paramView) {
    if (sViewPropertyAnimatorMap == null)
      sViewPropertyAnimatorMap = new WeakHashMap<View, ViewPropertyAnimatorCompat>(); 
    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = sViewPropertyAnimatorMap.get(paramView);
    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat1 = viewPropertyAnimatorCompat2;
    if (viewPropertyAnimatorCompat2 == null) {
      viewPropertyAnimatorCompat1 = new ViewPropertyAnimatorCompat(paramView);
      sViewPropertyAnimatorMap.put(paramView, viewPropertyAnimatorCompat1);
    } 
    return viewPropertyAnimatorCompat1;
  }
  
  private static void bindTempDetach() {
    try {
      sDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
      sDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.e("ViewCompat", "Couldn't find method", noSuchMethodException);
    } 
    sTempDetachBound = true;
  }
  
  @Deprecated
  public static boolean canScrollHorizontally(View paramView, int paramInt) {
    return paramView.canScrollHorizontally(paramInt);
  }
  
  @Deprecated
  public static boolean canScrollVertically(View paramView, int paramInt) {
    return paramView.canScrollVertically(paramInt);
  }
  
  public static void cancelDragAndDrop(View paramView) {
    if (Build.VERSION.SDK_INT >= 24)
      paramView.cancelDragAndDrop(); 
  }
  
  @Deprecated
  public static int combineMeasuredStates(int paramInt1, int paramInt2) {
    return View.combineMeasuredStates(paramInt1, paramInt2);
  }
  
  private static void compatOffsetLeftAndRight(View paramView, int paramInt) {
    paramView.offsetLeftAndRight(paramInt);
    if (paramView.getVisibility() == 0) {
      tickleInvalidationFlag(paramView);
      ViewParent viewParent = paramView.getParent();
      if (viewParent instanceof View)
        tickleInvalidationFlag((View)viewParent); 
    } 
  }
  
  private static void compatOffsetTopAndBottom(View paramView, int paramInt) {
    paramView.offsetTopAndBottom(paramInt);
    if (paramView.getVisibility() == 0) {
      tickleInvalidationFlag(paramView);
      ViewParent viewParent = paramView.getParent();
      if (viewParent instanceof View)
        tickleInvalidationFlag((View)viewParent); 
    } 
  }
  
  public static WindowInsetsCompat computeSystemWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat, Rect paramRect) {
    return (Build.VERSION.SDK_INT >= 21) ? Api21Impl.computeSystemWindowInsets(paramView, paramWindowInsetsCompat, paramRect) : paramWindowInsetsCompat;
  }
  
  public static WindowInsetsCompat dispatchApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat) {
    if (Build.VERSION.SDK_INT >= 21) {
      WindowInsets windowInsets = paramWindowInsetsCompat.toWindowInsets();
      if (windowInsets != null && !paramView.dispatchApplyWindowInsets(windowInsets).equals(windowInsets))
        return WindowInsetsCompat.toWindowInsetsCompat(windowInsets); 
    } 
    return paramWindowInsetsCompat;
  }
  
  public static void dispatchFinishTemporaryDetach(View paramView) {
    if (Build.VERSION.SDK_INT >= 24) {
      paramView.dispatchFinishTemporaryDetach();
    } else {
      if (!sTempDetachBound)
        bindTempDetach(); 
      Method method = sDispatchFinishTemporaryDetach;
      if (method != null) {
        try {
          method.invoke(paramView, new Object[0]);
        } catch (Exception exception) {
          Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", exception);
        } 
      } else {
        exception.onFinishTemporaryDetach();
      } 
    } 
  }
  
  public static boolean dispatchNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean) : ((paramView instanceof NestedScrollingChild) ? ((NestedScrollingChild)paramView).dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean) : false);
  }
  
  public static boolean dispatchNestedPreFling(View paramView, float paramFloat1, float paramFloat2) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.dispatchNestedPreFling(paramFloat1, paramFloat2) : ((paramView instanceof NestedScrollingChild) ? ((NestedScrollingChild)paramView).dispatchNestedPreFling(paramFloat1, paramFloat2) : false);
  }
  
  public static boolean dispatchNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2) : ((paramView instanceof NestedScrollingChild) ? ((NestedScrollingChild)paramView).dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2) : false);
  }
  
  public static boolean dispatchNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt3) {
    return (paramView instanceof NestedScrollingChild2) ? ((NestedScrollingChild2)paramView).dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2, paramInt3) : ((paramInt3 == 0) ? dispatchNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2) : false);
  }
  
  public static void dispatchNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint1, int paramInt5, int[] paramArrayOfint2) {
    if (paramView instanceof NestedScrollingChild3) {
      ((NestedScrollingChild3)paramView).dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint1, paramInt5, paramArrayOfint2);
    } else {
      dispatchNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint1, paramInt5);
    } 
  }
  
  public static boolean dispatchNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint) : ((paramView instanceof NestedScrollingChild) ? ((NestedScrollingChild)paramView).dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint) : false);
  }
  
  public static boolean dispatchNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5) {
    return (paramView instanceof NestedScrollingChild2) ? ((NestedScrollingChild2)paramView).dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, paramInt5) : ((paramInt5 == 0) ? dispatchNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint) : false);
  }
  
  public static void dispatchStartTemporaryDetach(View paramView) {
    if (Build.VERSION.SDK_INT >= 24) {
      paramView.dispatchStartTemporaryDetach();
    } else {
      if (!sTempDetachBound)
        bindTempDetach(); 
      Method method = sDispatchStartTemporaryDetach;
      if (method != null) {
        try {
          method.invoke(paramView, new Object[0]);
        } catch (Exception exception) {
          Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", exception);
        } 
      } else {
        exception.onStartTemporaryDetach();
      } 
    } 
  }
  
  static boolean dispatchUnhandledKeyEventBeforeCallback(View paramView, KeyEvent paramKeyEvent) {
    return (Build.VERSION.SDK_INT >= 28) ? false : UnhandledKeyEventManager.at(paramView).dispatch(paramView, paramKeyEvent);
  }
  
  static boolean dispatchUnhandledKeyEventBeforeHierarchy(View paramView, KeyEvent paramKeyEvent) {
    return (Build.VERSION.SDK_INT >= 28) ? false : UnhandledKeyEventManager.at(paramView).preDispatch(paramKeyEvent);
  }
  
  public static void enableAccessibleClickableSpanSupport(View paramView) {
    if (Build.VERSION.SDK_INT >= 19)
      getOrCreateAccessibilityDelegateCompat(paramView); 
  }
  
  public static int generateViewId() {
    if (Build.VERSION.SDK_INT >= 17)
      return View.generateViewId(); 
    while (true) {
      int k = sNextGeneratedId.get();
      int j = k + 1;
      int i = j;
      if (j > 16777215)
        i = 1; 
      if (sNextGeneratedId.compareAndSet(k, i))
        return k; 
    } 
  }
  
  public static AccessibilityDelegateCompat getAccessibilityDelegate(View paramView) {
    View.AccessibilityDelegate accessibilityDelegate = getAccessibilityDelegateInternal(paramView);
    return (accessibilityDelegate == null) ? null : ((accessibilityDelegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter) ? ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter)accessibilityDelegate).mCompat : new AccessibilityDelegateCompat(accessibilityDelegate));
  }
  
  private static View.AccessibilityDelegate getAccessibilityDelegateInternal(View paramView) {
    return (Build.VERSION.SDK_INT >= 29) ? paramView.getAccessibilityDelegate() : getAccessibilityDelegateThroughReflection(paramView);
  }
  
  private static View.AccessibilityDelegate getAccessibilityDelegateThroughReflection(View paramView) {
    if (sAccessibilityDelegateCheckFailed)
      return null; 
    if (sAccessibilityDelegateField == null)
      try {
        Field field = View.class.getDeclaredField("mAccessibilityDelegate");
        sAccessibilityDelegateField = field;
        field.setAccessible(true);
      } finally {
        paramView = null;
        sAccessibilityDelegateCheckFailed = true;
      }  
    try {
      Object object;
      return (object instanceof View.AccessibilityDelegate) ? (View.AccessibilityDelegate)object : null;
    } finally {
      paramView = null;
      sAccessibilityDelegateCheckFailed = true;
    } 
  }
  
  public static int getAccessibilityLiveRegion(View paramView) {
    return (Build.VERSION.SDK_INT >= 19) ? paramView.getAccessibilityLiveRegion() : 0;
  }
  
  public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView) {
    if (Build.VERSION.SDK_INT >= 16) {
      AccessibilityNodeProvider accessibilityNodeProvider = paramView.getAccessibilityNodeProvider();
      if (accessibilityNodeProvider != null)
        return new AccessibilityNodeProviderCompat(accessibilityNodeProvider); 
    } 
    return null;
  }
  
  public static CharSequence getAccessibilityPaneTitle(View paramView) {
    return paneTitleProperty().get(paramView);
  }
  
  private static List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> getActionList(View paramView) {
    ArrayList<AccessibilityNodeInfoCompat.AccessibilityActionCompat> arrayList2 = (ArrayList)paramView.getTag(R.id.tag_accessibility_actions);
    ArrayList<AccessibilityNodeInfoCompat.AccessibilityActionCompat> arrayList1 = arrayList2;
    if (arrayList2 == null) {
      arrayList1 = new ArrayList();
      paramView.setTag(R.id.tag_accessibility_actions, arrayList1);
    } 
    return arrayList1;
  }
  
  @Deprecated
  public static float getAlpha(View paramView) {
    return paramView.getAlpha();
  }
  
  private static int getAvailableActionIdFromResources(View paramView) {
    List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> list = getActionList(paramView);
    byte b = 0;
    int i = -1;
    while (true) {
      int[] arrayOfInt = ACCESSIBILITY_ACTIONS_RESOURCE_IDS;
      if (b < arrayOfInt.length && i == -1) {
        int k = arrayOfInt[b];
        byte b1 = 0;
        int j = 1;
        while (b1 < list.size()) {
          byte b2;
          if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat)list.get(b1)).getId() != k) {
            b2 = 1;
          } else {
            b2 = 0;
          } 
          j &= b2;
          b1++;
        } 
        if (j != 0)
          i = k; 
        b++;
        continue;
      } 
      break;
    } 
    return i;
  }
  
  public static ColorStateList getBackgroundTintList(View paramView) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramView.getBackgroundTintList(); 
    if (paramView instanceof TintableBackgroundView) {
      ColorStateList colorStateList = ((TintableBackgroundView)paramView).getSupportBackgroundTintList();
    } else {
      paramView = null;
    } 
    return (ColorStateList)paramView;
  }
  
  public static PorterDuff.Mode getBackgroundTintMode(View paramView) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramView.getBackgroundTintMode(); 
    if (paramView instanceof TintableBackgroundView) {
      PorterDuff.Mode mode = ((TintableBackgroundView)paramView).getSupportBackgroundTintMode();
    } else {
      paramView = null;
    } 
    return (PorterDuff.Mode)paramView;
  }
  
  public static Rect getClipBounds(View paramView) {
    return (Build.VERSION.SDK_INT >= 18) ? paramView.getClipBounds() : null;
  }
  
  public static Display getDisplay(View paramView) {
    return (Build.VERSION.SDK_INT >= 17) ? paramView.getDisplay() : (isAttachedToWindow(paramView) ? ((WindowManager)paramView.getContext().getSystemService("window")).getDefaultDisplay() : null);
  }
  
  public static float getElevation(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.getElevation() : 0.0F;
  }
  
  private static Rect getEmptyTempRect() {
    if (sThreadLocalRect == null)
      sThreadLocalRect = new ThreadLocal<Rect>(); 
    Rect rect2 = sThreadLocalRect.get();
    Rect rect1 = rect2;
    if (rect2 == null) {
      rect1 = new Rect();
      sThreadLocalRect.set(rect1);
    } 
    rect1.setEmpty();
    return rect1;
  }
  
  public static boolean getFitsSystemWindows(View paramView) {
    return (Build.VERSION.SDK_INT >= 16) ? paramView.getFitsSystemWindows() : false;
  }
  
  public static int getImportantForAccessibility(View paramView) {
    return (Build.VERSION.SDK_INT >= 16) ? paramView.getImportantForAccessibility() : 0;
  }
  
  public static int getImportantForAutofill(View paramView) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView.getImportantForAutofill() : 0;
  }
  
  public static int getLabelFor(View paramView) {
    return (Build.VERSION.SDK_INT >= 17) ? paramView.getLabelFor() : 0;
  }
  
  @Deprecated
  public static int getLayerType(View paramView) {
    return paramView.getLayerType();
  }
  
  public static int getLayoutDirection(View paramView) {
    return (Build.VERSION.SDK_INT >= 17) ? paramView.getLayoutDirection() : 0;
  }
  
  @Deprecated
  public static Matrix getMatrix(View paramView) {
    return paramView.getMatrix();
  }
  
  @Deprecated
  public static int getMeasuredHeightAndState(View paramView) {
    return paramView.getMeasuredHeightAndState();
  }
  
  @Deprecated
  public static int getMeasuredState(View paramView) {
    return paramView.getMeasuredState();
  }
  
  @Deprecated
  public static int getMeasuredWidthAndState(View paramView) {
    return paramView.getMeasuredWidthAndState();
  }
  
  public static int getMinimumHeight(View paramView) {
    if (Build.VERSION.SDK_INT >= 16)
      return paramView.getMinimumHeight(); 
    if (!sMinHeightFieldFetched) {
      try {
        Field field1 = View.class.getDeclaredField("mMinHeight");
        sMinHeightField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {}
      sMinHeightFieldFetched = true;
    } 
    Field field = sMinHeightField;
    if (field != null)
      try {
        return ((Integer)field.get(paramView)).intValue();
      } catch (Exception exception) {} 
    return 0;
  }
  
  public static int getMinimumWidth(View paramView) {
    if (Build.VERSION.SDK_INT >= 16)
      return paramView.getMinimumWidth(); 
    if (!sMinWidthFieldFetched) {
      try {
        Field field1 = View.class.getDeclaredField("mMinWidth");
        sMinWidthField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {}
      sMinWidthFieldFetched = true;
    } 
    Field field = sMinWidthField;
    if (field != null)
      try {
        return ((Integer)field.get(paramView)).intValue();
      } catch (Exception exception) {} 
    return 0;
  }
  
  public static int getNextClusterForwardId(View paramView) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView.getNextClusterForwardId() : -1;
  }
  
  static AccessibilityDelegateCompat getOrCreateAccessibilityDelegateCompat(View paramView) {
    AccessibilityDelegateCompat accessibilityDelegateCompat2 = getAccessibilityDelegate(paramView);
    AccessibilityDelegateCompat accessibilityDelegateCompat1 = accessibilityDelegateCompat2;
    if (accessibilityDelegateCompat2 == null)
      accessibilityDelegateCompat1 = new AccessibilityDelegateCompat(); 
    setAccessibilityDelegate(paramView, accessibilityDelegateCompat1);
    return accessibilityDelegateCompat1;
  }
  
  @Deprecated
  public static int getOverScrollMode(View paramView) {
    return paramView.getOverScrollMode();
  }
  
  public static int getPaddingEnd(View paramView) {
    return (Build.VERSION.SDK_INT >= 17) ? paramView.getPaddingEnd() : paramView.getPaddingRight();
  }
  
  public static int getPaddingStart(View paramView) {
    return (Build.VERSION.SDK_INT >= 17) ? paramView.getPaddingStart() : paramView.getPaddingLeft();
  }
  
  public static ViewParent getParentForAccessibility(View paramView) {
    return (Build.VERSION.SDK_INT >= 16) ? paramView.getParentForAccessibility() : paramView.getParent();
  }
  
  @Deprecated
  public static float getPivotX(View paramView) {
    return paramView.getPivotX();
  }
  
  @Deprecated
  public static float getPivotY(View paramView) {
    return paramView.getPivotY();
  }
  
  public static WindowInsetsCompat getRootWindowInsets(View paramView) {
    return (Build.VERSION.SDK_INT >= 23) ? WindowInsetsCompat.toWindowInsetsCompat(Api23Impl.getRootWindowInsets(paramView)) : null;
  }
  
  @Deprecated
  public static float getRotation(View paramView) {
    return paramView.getRotation();
  }
  
  @Deprecated
  public static float getRotationX(View paramView) {
    return paramView.getRotationX();
  }
  
  @Deprecated
  public static float getRotationY(View paramView) {
    return paramView.getRotationY();
  }
  
  @Deprecated
  public static float getScaleX(View paramView) {
    return paramView.getScaleX();
  }
  
  @Deprecated
  public static float getScaleY(View paramView) {
    return paramView.getScaleY();
  }
  
  public static int getScrollIndicators(View paramView) {
    return (Build.VERSION.SDK_INT >= 23) ? paramView.getScrollIndicators() : 0;
  }
  
  public static List<Rect> getSystemGestureExclusionRects(View paramView) {
    return (Build.VERSION.SDK_INT >= 29) ? paramView.getSystemGestureExclusionRects() : Collections.emptyList();
  }
  
  public static String getTransitionName(View paramView) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramView.getTransitionName(); 
    WeakHashMap<View, String> weakHashMap = sTransitionNameMap;
    return (weakHashMap == null) ? null : weakHashMap.get(paramView);
  }
  
  @Deprecated
  public static float getTranslationX(View paramView) {
    return paramView.getTranslationX();
  }
  
  @Deprecated
  public static float getTranslationY(View paramView) {
    return paramView.getTranslationY();
  }
  
  public static float getTranslationZ(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.getTranslationZ() : 0.0F;
  }
  
  public static int getWindowSystemUiVisibility(View paramView) {
    return (Build.VERSION.SDK_INT >= 16) ? paramView.getWindowSystemUiVisibility() : 0;
  }
  
  @Deprecated
  public static float getX(View paramView) {
    return paramView.getX();
  }
  
  @Deprecated
  public static float getY(View paramView) {
    return paramView.getY();
  }
  
  public static float getZ(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.getZ() : 0.0F;
  }
  
  public static boolean hasAccessibilityDelegate(View paramView) {
    boolean bool;
    if (getAccessibilityDelegateInternal(paramView) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean hasExplicitFocusable(View paramView) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView.hasExplicitFocusable() : paramView.hasFocusable();
  }
  
  public static boolean hasNestedScrollingParent(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.hasNestedScrollingParent() : ((paramView instanceof NestedScrollingChild) ? ((NestedScrollingChild)paramView).hasNestedScrollingParent() : false);
  }
  
  public static boolean hasNestedScrollingParent(View paramView, int paramInt) {
    if (paramView instanceof NestedScrollingChild2) {
      ((NestedScrollingChild2)paramView).hasNestedScrollingParent(paramInt);
    } else if (paramInt == 0) {
      return hasNestedScrollingParent(paramView);
    } 
    return false;
  }
  
  public static boolean hasOnClickListeners(View paramView) {
    return (Build.VERSION.SDK_INT >= 15) ? paramView.hasOnClickListeners() : false;
  }
  
  public static boolean hasOverlappingRendering(View paramView) {
    return (Build.VERSION.SDK_INT >= 16) ? paramView.hasOverlappingRendering() : true;
  }
  
  public static boolean hasTransientState(View paramView) {
    return (Build.VERSION.SDK_INT >= 16) ? paramView.hasTransientState() : false;
  }
  
  public static boolean isAccessibilityHeading(View paramView) {
    boolean bool1;
    Boolean bool = accessibilityHeadingProperty().get(paramView);
    if (bool == null) {
      bool1 = false;
    } else {
      bool1 = bool.booleanValue();
    } 
    return bool1;
  }
  
  public static boolean isAttachedToWindow(View paramView) {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 19)
      return paramView.isAttachedToWindow(); 
    if (paramView.getWindowToken() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isFocusedByDefault(View paramView) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView.isFocusedByDefault() : false;
  }
  
  public static boolean isImportantForAccessibility(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.isImportantForAccessibility() : true;
  }
  
  public static boolean isImportantForAutofill(View paramView) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView.isImportantForAutofill() : true;
  }
  
  public static boolean isInLayout(View paramView) {
    return (Build.VERSION.SDK_INT >= 18) ? paramView.isInLayout() : false;
  }
  
  public static boolean isKeyboardNavigationCluster(View paramView) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView.isKeyboardNavigationCluster() : false;
  }
  
  public static boolean isLaidOut(View paramView) {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 19)
      return paramView.isLaidOut(); 
    if (paramView.getWidth() > 0 && paramView.getHeight() > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isLayoutDirectionResolved(View paramView) {
    return (Build.VERSION.SDK_INT >= 19) ? paramView.isLayoutDirectionResolved() : false;
  }
  
  public static boolean isNestedScrollingEnabled(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.isNestedScrollingEnabled() : ((paramView instanceof NestedScrollingChild) ? ((NestedScrollingChild)paramView).isNestedScrollingEnabled() : false);
  }
  
  @Deprecated
  public static boolean isOpaque(View paramView) {
    return paramView.isOpaque();
  }
  
  public static boolean isPaddingRelative(View paramView) {
    return (Build.VERSION.SDK_INT >= 17) ? paramView.isPaddingRelative() : false;
  }
  
  public static boolean isScreenReaderFocusable(View paramView) {
    boolean bool1;
    Boolean bool = screenReaderFocusableProperty().get(paramView);
    if (bool == null) {
      bool1 = false;
    } else {
      bool1 = bool.booleanValue();
    } 
    return bool1;
  }
  
  @Deprecated
  public static void jumpDrawablesToCurrentState(View paramView) {
    paramView.jumpDrawablesToCurrentState();
  }
  
  public static View keyboardNavigationClusterSearch(View paramView1, View paramView2, int paramInt) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView1.keyboardNavigationClusterSearch(paramView2, paramInt) : null;
  }
  
  static void notifyViewAccessibilityStateChangedIfNeeded(View paramView, int paramInt) {
    char c;
    if (!((AccessibilityManager)paramView.getContext().getSystemService("accessibility")).isEnabled())
      return; 
    if (getAccessibilityPaneTitle(paramView) != null) {
      c = '\001';
    } else {
      c = Character.MIN_VALUE;
    } 
    if (getAccessibilityLiveRegion(paramView) != 0 || (c && paramView.getVisibility() == 0)) {
      AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
      if (c) {
        c = ' ';
      } else {
        c = 'ࠀ';
      } 
      accessibilityEvent.setEventType(c);
      accessibilityEvent.setContentChangeTypes(paramInt);
      paramView.sendAccessibilityEventUnchecked(accessibilityEvent);
      return;
    } 
    if (paramView.getParent() != null)
      try {
        paramView.getParent().notifySubtreeAccessibilityStateChanged(paramView, paramView, paramInt);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramView.getParent().getClass().getSimpleName());
        stringBuilder.append(" does not fully implement ViewParent");
        Log.e("ViewCompat", stringBuilder.toString(), abstractMethodError);
      }  
  }
  
  public static void offsetLeftAndRight(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 23) {
      paramView.offsetLeftAndRight(paramInt);
    } else if (Build.VERSION.SDK_INT >= 21) {
      Rect rect = getEmptyTempRect();
      int i = 0;
      ViewParent viewParent = paramView.getParent();
      if (viewParent instanceof View) {
        View view = (View)viewParent;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        i = rect.intersects(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()) ^ true;
      } 
      compatOffsetLeftAndRight(paramView, paramInt);
      if (i != 0 && rect.intersect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()))
        ((View)viewParent).invalidate(rect); 
    } else {
      compatOffsetLeftAndRight(paramView, paramInt);
    } 
  }
  
  public static void offsetTopAndBottom(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 23) {
      paramView.offsetTopAndBottom(paramInt);
    } else if (Build.VERSION.SDK_INT >= 21) {
      Rect rect = getEmptyTempRect();
      int i = 0;
      ViewParent viewParent = paramView.getParent();
      if (viewParent instanceof View) {
        View view = (View)viewParent;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        i = rect.intersects(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()) ^ true;
      } 
      compatOffsetTopAndBottom(paramView, paramInt);
      if (i != 0 && rect.intersect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()))
        ((View)viewParent).invalidate(rect); 
    } else {
      compatOffsetTopAndBottom(paramView, paramInt);
    } 
  }
  
  public static WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat) {
    if (Build.VERSION.SDK_INT >= 21) {
      WindowInsets windowInsets = paramWindowInsetsCompat.toWindowInsets();
      if (windowInsets != null) {
        WindowInsets windowInsets1 = paramView.onApplyWindowInsets(windowInsets);
        if (!windowInsets1.equals(windowInsets))
          return WindowInsetsCompat.toWindowInsetsCompat(windowInsets1); 
      } 
    } 
    return paramWindowInsetsCompat;
  }
  
  @Deprecated
  public static void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    paramView.onInitializeAccessibilityEvent(paramAccessibilityEvent);
  }
  
  public static void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    paramView.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfoCompat.unwrap());
  }
  
  @Deprecated
  public static void onPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    paramView.onPopulateAccessibilityEvent(paramAccessibilityEvent);
  }
  
  private static AccessibilityViewProperty<CharSequence> paneTitleProperty() {
    return new AccessibilityViewProperty<CharSequence>(R.id.tag_accessibility_pane_title, CharSequence.class, 8, 28) {
        CharSequence frameworkGet(View param1View) {
          return param1View.getAccessibilityPaneTitle();
        }
        
        void frameworkSet(View param1View, CharSequence param1CharSequence) {
          param1View.setAccessibilityPaneTitle(param1CharSequence);
        }
        
        boolean shouldUpdate(CharSequence param1CharSequence1, CharSequence param1CharSequence2) {
          return TextUtils.equals(param1CharSequence1, param1CharSequence2) ^ true;
        }
      };
  }
  
  public static boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle) {
    return (Build.VERSION.SDK_INT >= 16) ? paramView.performAccessibilityAction(paramInt, paramBundle) : false;
  }
  
  public static void postInvalidateOnAnimation(View paramView) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramView.postInvalidateOnAnimation();
    } else {
      paramView.postInvalidate();
    } 
  }
  
  public static void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramView.postInvalidateOnAnimation(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      paramView.postInvalidate(paramInt1, paramInt2, paramInt3, paramInt4);
    } 
  }
  
  public static void postOnAnimation(View paramView, Runnable paramRunnable) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramView.postOnAnimation(paramRunnable);
    } else {
      paramView.postDelayed(paramRunnable, ValueAnimator.getFrameDelay());
    } 
  }
  
  public static void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramView.postOnAnimationDelayed(paramRunnable, paramLong);
    } else {
      paramView.postDelayed(paramRunnable, ValueAnimator.getFrameDelay() + paramLong);
    } 
  }
  
  public static void removeAccessibilityAction(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 21) {
      removeActionWithId(paramInt, paramView);
      notifyViewAccessibilityStateChangedIfNeeded(paramView, 0);
    } 
  }
  
  private static void removeActionWithId(int paramInt, View paramView) {
    List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> list = getActionList(paramView);
    for (byte b = 0; b < list.size(); b++) {
      if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat)list.get(b)).getId() == paramInt) {
        list.remove(b);
        break;
      } 
    } 
  }
  
  public static void removeOnUnhandledKeyEventListener(View paramView, OnUnhandledKeyEventListenerCompat paramOnUnhandledKeyEventListenerCompat) {
    View.OnUnhandledKeyEventListener onUnhandledKeyEventListener;
    if (Build.VERSION.SDK_INT >= 28) {
      SimpleArrayMap simpleArrayMap = (SimpleArrayMap)paramView.getTag(R.id.tag_unhandled_key_listeners);
      if (simpleArrayMap == null)
        return; 
      onUnhandledKeyEventListener = (View.OnUnhandledKeyEventListener)simpleArrayMap.get(paramOnUnhandledKeyEventListenerCompat);
      if (onUnhandledKeyEventListener != null)
        paramView.removeOnUnhandledKeyEventListener(onUnhandledKeyEventListener); 
      return;
    } 
    ArrayList arrayList = (ArrayList)paramView.getTag(R.id.tag_unhandled_key_listeners);
    if (arrayList != null) {
      arrayList.remove(onUnhandledKeyEventListener);
      if (arrayList.size() == 0)
        UnhandledKeyEventManager.unregisterListeningView(paramView); 
    } 
  }
  
  public static void replaceAccessibilityAction(View paramView, AccessibilityNodeInfoCompat.AccessibilityActionCompat paramAccessibilityActionCompat, CharSequence paramCharSequence, AccessibilityViewCommand paramAccessibilityViewCommand) {
    if (paramAccessibilityViewCommand == null && paramCharSequence == null) {
      removeAccessibilityAction(paramView, paramAccessibilityActionCompat.getId());
    } else {
      addAccessibilityAction(paramView, paramAccessibilityActionCompat.createReplacementAction(paramCharSequence, paramAccessibilityViewCommand));
    } 
  }
  
  public static void requestApplyInsets(View paramView) {
    if (Build.VERSION.SDK_INT >= 20) {
      paramView.requestApplyInsets();
    } else if (Build.VERSION.SDK_INT >= 16) {
      paramView.requestFitSystemWindows();
    } 
  }
  
  public static <T extends View> T requireViewById(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 28)
      return (T)paramView.requireViewById(paramInt); 
    paramView = paramView.findViewById(paramInt);
    if (paramView != null)
      return (T)paramView; 
    throw new IllegalArgumentException("ID does not reference a View inside this View");
  }
  
  @Deprecated
  public static int resolveSizeAndState(int paramInt1, int paramInt2, int paramInt3) {
    return View.resolveSizeAndState(paramInt1, paramInt2, paramInt3);
  }
  
  public static boolean restoreDefaultFocus(View paramView) {
    return (Build.VERSION.SDK_INT >= 26) ? paramView.restoreDefaultFocus() : paramView.requestFocus();
  }
  
  public static void saveAttributeDataForStyleable(View paramView, Context paramContext, int[] paramArrayOfint, AttributeSet paramAttributeSet, TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    if (Build.VERSION.SDK_INT >= 29)
      Api29Impl.saveAttributeDataForStyleable(paramView, paramContext, paramArrayOfint, paramAttributeSet, paramTypedArray, paramInt1, paramInt2); 
  }
  
  private static AccessibilityViewProperty<Boolean> screenReaderFocusableProperty() {
    return new AccessibilityViewProperty<Boolean>(R.id.tag_screen_reader_focusable, Boolean.class, 28) {
        Boolean frameworkGet(View param1View) {
          return Boolean.valueOf(param1View.isScreenReaderFocusable());
        }
        
        void frameworkSet(View param1View, Boolean param1Boolean) {
          param1View.setScreenReaderFocusable(param1Boolean.booleanValue());
        }
        
        boolean shouldUpdate(Boolean param1Boolean1, Boolean param1Boolean2) {
          return booleanNullToFalseEquals(param1Boolean1, param1Boolean2) ^ true;
        }
      };
  }
  
  public static void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat) {
    View.AccessibilityDelegate accessibilityDelegate;
    AccessibilityDelegateCompat accessibilityDelegateCompat = paramAccessibilityDelegateCompat;
    if (paramAccessibilityDelegateCompat == null) {
      accessibilityDelegateCompat = paramAccessibilityDelegateCompat;
      if (getAccessibilityDelegateInternal(paramView) instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter)
        accessibilityDelegateCompat = new AccessibilityDelegateCompat(); 
    } 
    if (accessibilityDelegateCompat == null) {
      paramAccessibilityDelegateCompat = null;
    } else {
      accessibilityDelegate = accessibilityDelegateCompat.getBridge();
    } 
    paramView.setAccessibilityDelegate(accessibilityDelegate);
  }
  
  public static void setAccessibilityHeading(View paramView, boolean paramBoolean) {
    accessibilityHeadingProperty().set(paramView, Boolean.valueOf(paramBoolean));
  }
  
  public static void setAccessibilityLiveRegion(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      paramView.setAccessibilityLiveRegion(paramInt); 
  }
  
  public static void setAccessibilityPaneTitle(View paramView, CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 19) {
      paneTitleProperty().set(paramView, paramCharSequence);
      if (paramCharSequence != null) {
        sAccessibilityPaneVisibilityManager.addAccessibilityPane(paramView);
      } else {
        sAccessibilityPaneVisibilityManager.removeAccessibilityPane(paramView);
      } 
    } 
  }
  
  @Deprecated
  public static void setActivated(View paramView, boolean paramBoolean) {
    paramView.setActivated(paramBoolean);
  }
  
  @Deprecated
  public static void setAlpha(View paramView, float paramFloat) {
    paramView.setAlpha(paramFloat);
  }
  
  public static void setAutofillHints(View paramView, String... paramVarArgs) {
    if (Build.VERSION.SDK_INT >= 26)
      paramView.setAutofillHints(paramVarArgs); 
  }
  
  public static void setBackground(View paramView, Drawable paramDrawable) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramView.setBackground(paramDrawable);
    } else {
      paramView.setBackgroundDrawable(paramDrawable);
    } 
  }
  
  public static void setBackgroundTintList(View paramView, ColorStateList paramColorStateList) {
    Drawable drawable;
    if (Build.VERSION.SDK_INT >= 21) {
      paramView.setBackgroundTintList(paramColorStateList);
      if (Build.VERSION.SDK_INT == 21) {
        boolean bool;
        drawable = paramView.getBackground();
        if (paramView.getBackgroundTintList() != null || paramView.getBackgroundTintMode() != null) {
          bool = true;
        } else {
          bool = false;
        } 
        if (drawable != null && bool) {
          if (drawable.isStateful())
            drawable.setState(paramView.getDrawableState()); 
          paramView.setBackground(drawable);
        } 
      } 
    } else if (paramView instanceof TintableBackgroundView) {
      ((TintableBackgroundView)paramView).setSupportBackgroundTintList((ColorStateList)drawable);
    } 
  }
  
  public static void setBackgroundTintMode(View paramView, PorterDuff.Mode paramMode) {
    Drawable drawable;
    if (Build.VERSION.SDK_INT >= 21) {
      paramView.setBackgroundTintMode(paramMode);
      if (Build.VERSION.SDK_INT == 21) {
        boolean bool;
        drawable = paramView.getBackground();
        if (paramView.getBackgroundTintList() != null || paramView.getBackgroundTintMode() != null) {
          bool = true;
        } else {
          bool = false;
        } 
        if (drawable != null && bool) {
          if (drawable.isStateful())
            drawable.setState(paramView.getDrawableState()); 
          paramView.setBackground(drawable);
        } 
      } 
    } else if (paramView instanceof TintableBackgroundView) {
      ((TintableBackgroundView)paramView).setSupportBackgroundTintMode((PorterDuff.Mode)drawable);
    } 
  }
  
  @Deprecated
  public static void setChildrenDrawingOrderEnabled(ViewGroup paramViewGroup, boolean paramBoolean) {
    if (sChildrenDrawingOrderMethod == null) {
      try {
        sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[] { boolean.class });
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.e("ViewCompat", "Unable to find childrenDrawingOrderEnabled", noSuchMethodException);
      } 
      sChildrenDrawingOrderMethod.setAccessible(true);
    } 
    try {
      sChildrenDrawingOrderMethod.invoke(paramViewGroup, new Object[] { Boolean.valueOf(paramBoolean) });
    } catch (IllegalAccessException illegalAccessException) {
      Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", illegalAccessException);
    } catch (IllegalArgumentException illegalArgumentException) {
      Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", illegalArgumentException);
    } catch (InvocationTargetException invocationTargetException) {
      Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", invocationTargetException);
    } 
  }
  
  public static void setClipBounds(View paramView, Rect paramRect) {
    if (Build.VERSION.SDK_INT >= 18)
      paramView.setClipBounds(paramRect); 
  }
  
  public static void setElevation(View paramView, float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21)
      paramView.setElevation(paramFloat); 
  }
  
  @Deprecated
  public static void setFitsSystemWindows(View paramView, boolean paramBoolean) {
    paramView.setFitsSystemWindows(paramBoolean);
  }
  
  public static void setFocusedByDefault(View paramView, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 26)
      paramView.setFocusedByDefault(paramBoolean); 
  }
  
  public static void setHasTransientState(View paramView, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 16)
      paramView.setHasTransientState(paramBoolean); 
  }
  
  public static void setImportantForAccessibility(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 19) {
      paramView.setImportantForAccessibility(paramInt);
    } else if (Build.VERSION.SDK_INT >= 16) {
      int i = paramInt;
      if (paramInt == 4)
        i = 2; 
      paramView.setImportantForAccessibility(i);
    } 
  }
  
  public static void setImportantForAutofill(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 26)
      paramView.setImportantForAutofill(paramInt); 
  }
  
  public static void setKeyboardNavigationCluster(View paramView, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 26)
      paramView.setKeyboardNavigationCluster(paramBoolean); 
  }
  
  public static void setLabelFor(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      paramView.setLabelFor(paramInt); 
  }
  
  public static void setLayerPaint(View paramView, Paint paramPaint) {
    if (Build.VERSION.SDK_INT >= 17) {
      paramView.setLayerPaint(paramPaint);
    } else {
      paramView.setLayerType(paramView.getLayerType(), paramPaint);
      paramView.invalidate();
    } 
  }
  
  @Deprecated
  public static void setLayerType(View paramView, int paramInt, Paint paramPaint) {
    paramView.setLayerType(paramInt, paramPaint);
  }
  
  public static void setLayoutDirection(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      paramView.setLayoutDirection(paramInt); 
  }
  
  public static void setNestedScrollingEnabled(View paramView, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramView.setNestedScrollingEnabled(paramBoolean);
    } else if (paramView instanceof NestedScrollingChild) {
      ((NestedScrollingChild)paramView).setNestedScrollingEnabled(paramBoolean);
    } 
  }
  
  public static void setNextClusterForwardId(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 26)
      paramView.setNextClusterForwardId(paramInt); 
  }
  
  public static void setOnApplyWindowInsetsListener(View paramView, final OnApplyWindowInsetsListener listener) {
    if (Build.VERSION.SDK_INT >= 21) {
      if (listener == null) {
        paramView.setOnApplyWindowInsetsListener(null);
        return;
      } 
      paramView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            final OnApplyWindowInsetsListener val$listener;
            
            public WindowInsets onApplyWindowInsets(View param1View, WindowInsets param1WindowInsets) {
              WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(param1WindowInsets);
              return listener.onApplyWindowInsets(param1View, windowInsetsCompat).toWindowInsets();
            }
          });
    } 
  }
  
  @Deprecated
  public static void setOverScrollMode(View paramView, int paramInt) {
    paramView.setOverScrollMode(paramInt);
  }
  
  public static void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (Build.VERSION.SDK_INT >= 17) {
      paramView.setPaddingRelative(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      paramView.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
    } 
  }
  
  @Deprecated
  public static void setPivotX(View paramView, float paramFloat) {
    paramView.setPivotX(paramFloat);
  }
  
  @Deprecated
  public static void setPivotY(View paramView, float paramFloat) {
    paramView.setPivotY(paramFloat);
  }
  
  public static void setPointerIcon(View paramView, PointerIconCompat paramPointerIconCompat) {
    if (Build.VERSION.SDK_INT >= 24) {
      if (paramPointerIconCompat != null) {
        Object object = paramPointerIconCompat.getPointerIcon();
      } else {
        paramPointerIconCompat = null;
      } 
      paramView.setPointerIcon((PointerIcon)paramPointerIconCompat);
    } 
  }
  
  @Deprecated
  public static void setRotation(View paramView, float paramFloat) {
    paramView.setRotation(paramFloat);
  }
  
  @Deprecated
  public static void setRotationX(View paramView, float paramFloat) {
    paramView.setRotationX(paramFloat);
  }
  
  @Deprecated
  public static void setRotationY(View paramView, float paramFloat) {
    paramView.setRotationY(paramFloat);
  }
  
  @Deprecated
  public static void setSaveFromParentEnabled(View paramView, boolean paramBoolean) {
    paramView.setSaveFromParentEnabled(paramBoolean);
  }
  
  @Deprecated
  public static void setScaleX(View paramView, float paramFloat) {
    paramView.setScaleX(paramFloat);
  }
  
  @Deprecated
  public static void setScaleY(View paramView, float paramFloat) {
    paramView.setScaleY(paramFloat);
  }
  
  public static void setScreenReaderFocusable(View paramView, boolean paramBoolean) {
    screenReaderFocusableProperty().set(paramView, Boolean.valueOf(paramBoolean));
  }
  
  public static void setScrollIndicators(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 23)
      paramView.setScrollIndicators(paramInt); 
  }
  
  public static void setScrollIndicators(View paramView, int paramInt1, int paramInt2) {
    if (Build.VERSION.SDK_INT >= 23)
      paramView.setScrollIndicators(paramInt1, paramInt2); 
  }
  
  public static void setSystemGestureExclusionRects(View paramView, List<Rect> paramList) {
    if (Build.VERSION.SDK_INT >= 29)
      paramView.setSystemGestureExclusionRects(paramList); 
  }
  
  public static void setTooltipText(View paramView, CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 26)
      paramView.setTooltipText(paramCharSequence); 
  }
  
  public static void setTransitionName(View paramView, String paramString) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramView.setTransitionName(paramString);
    } else {
      if (sTransitionNameMap == null)
        sTransitionNameMap = new WeakHashMap<View, String>(); 
      sTransitionNameMap.put(paramView, paramString);
    } 
  }
  
  @Deprecated
  public static void setTranslationX(View paramView, float paramFloat) {
    paramView.setTranslationX(paramFloat);
  }
  
  @Deprecated
  public static void setTranslationY(View paramView, float paramFloat) {
    paramView.setTranslationY(paramFloat);
  }
  
  public static void setTranslationZ(View paramView, float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21)
      paramView.setTranslationZ(paramFloat); 
  }
  
  @Deprecated
  public static void setX(View paramView, float paramFloat) {
    paramView.setX(paramFloat);
  }
  
  @Deprecated
  public static void setY(View paramView, float paramFloat) {
    paramView.setY(paramFloat);
  }
  
  public static void setZ(View paramView, float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21)
      paramView.setZ(paramFloat); 
  }
  
  public static boolean startDragAndDrop(View paramView, ClipData paramClipData, View.DragShadowBuilder paramDragShadowBuilder, Object paramObject, int paramInt) {
    return (Build.VERSION.SDK_INT >= 24) ? paramView.startDragAndDrop(paramClipData, paramDragShadowBuilder, paramObject, paramInt) : paramView.startDrag(paramClipData, paramDragShadowBuilder, paramObject, paramInt);
  }
  
  public static boolean startNestedScroll(View paramView, int paramInt) {
    return (Build.VERSION.SDK_INT >= 21) ? paramView.startNestedScroll(paramInt) : ((paramView instanceof NestedScrollingChild) ? ((NestedScrollingChild)paramView).startNestedScroll(paramInt) : false);
  }
  
  public static boolean startNestedScroll(View paramView, int paramInt1, int paramInt2) {
    return (paramView instanceof NestedScrollingChild2) ? ((NestedScrollingChild2)paramView).startNestedScroll(paramInt1, paramInt2) : ((paramInt2 == 0) ? startNestedScroll(paramView, paramInt1) : false);
  }
  
  public static void stopNestedScroll(View paramView) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramView.stopNestedScroll();
    } else if (paramView instanceof NestedScrollingChild) {
      ((NestedScrollingChild)paramView).stopNestedScroll();
    } 
  }
  
  public static void stopNestedScroll(View paramView, int paramInt) {
    if (paramView instanceof NestedScrollingChild2) {
      ((NestedScrollingChild2)paramView).stopNestedScroll(paramInt);
    } else if (paramInt == 0) {
      stopNestedScroll(paramView);
    } 
  }
  
  private static void tickleInvalidationFlag(View paramView) {
    float f = paramView.getTranslationY();
    paramView.setTranslationY(1.0F + f);
    paramView.setTranslationY(f);
  }
  
  public static void updateDragShadow(View paramView, View.DragShadowBuilder paramDragShadowBuilder) {
    if (Build.VERSION.SDK_INT >= 24)
      paramView.updateDragShadow(paramDragShadowBuilder); 
  }
  
  static class AccessibilityPaneVisibilityManager implements ViewTreeObserver.OnGlobalLayoutListener, View.OnAttachStateChangeListener {
    private WeakHashMap<View, Boolean> mPanesToVisible = new WeakHashMap<View, Boolean>();
    
    private void checkPaneVisibility(View param1View, boolean param1Boolean) {
      boolean bool;
      if (param1View.getVisibility() == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      if (param1Boolean != bool) {
        if (bool)
          ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(param1View, 16); 
        this.mPanesToVisible.put(param1View, Boolean.valueOf(bool));
      } 
    }
    
    private void registerForLayoutCallback(View param1View) {
      param1View.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }
    
    private void unregisterForLayoutCallback(View param1View) {
      param1View.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
    
    void addAccessibilityPane(View param1View) {
      boolean bool;
      WeakHashMap<View, Boolean> weakHashMap = this.mPanesToVisible;
      if (param1View.getVisibility() == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      weakHashMap.put(param1View, Boolean.valueOf(bool));
      param1View.addOnAttachStateChangeListener(this);
      if (param1View.isAttachedToWindow())
        registerForLayoutCallback(param1View); 
    }
    
    public void onGlobalLayout() {
      for (Map.Entry<View, Boolean> entry : this.mPanesToVisible.entrySet())
        checkPaneVisibility((View)entry.getKey(), ((Boolean)entry.getValue()).booleanValue()); 
    }
    
    public void onViewAttachedToWindow(View param1View) {
      registerForLayoutCallback(param1View);
    }
    
    public void onViewDetachedFromWindow(View param1View) {}
    
    void removeAccessibilityPane(View param1View) {
      this.mPanesToVisible.remove(param1View);
      param1View.removeOnAttachStateChangeListener(this);
      unregisterForLayoutCallback(param1View);
    }
  }
  
  static abstract class AccessibilityViewProperty<T> {
    private final int mFrameworkMinimumSdk;
    
    private final int mTagKey;
    
    private final Class<T> mType;
    
    AccessibilityViewProperty(int param1Int1, Class<T> param1Class, int param1Int2) {
      this(param1Int1, param1Class, 0, param1Int2);
    }
    
    AccessibilityViewProperty(int param1Int1, Class<T> param1Class, int param1Int2, int param1Int3) {
      this.mTagKey = param1Int1;
      this.mType = param1Class;
      this.mFrameworkMinimumSdk = param1Int3;
    }
    
    private boolean extrasAvailable() {
      boolean bool;
      if (Build.VERSION.SDK_INT >= 19) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private boolean frameworkAvailable() {
      boolean bool;
      if (Build.VERSION.SDK_INT >= this.mFrameworkMinimumSdk) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean booleanNullToFalseEquals(Boolean param1Boolean1, Boolean param1Boolean2) {
      boolean bool1;
      boolean bool2;
      boolean bool = false;
      if (param1Boolean1 == null) {
        bool1 = false;
      } else {
        bool1 = param1Boolean1.booleanValue();
      } 
      if (param1Boolean2 == null) {
        bool2 = false;
      } else {
        bool2 = param1Boolean2.booleanValue();
      } 
      if (bool1 == bool2)
        bool = true; 
      return bool;
    }
    
    abstract T frameworkGet(View param1View);
    
    abstract void frameworkSet(View param1View, T param1T);
    
    T get(View param1View) {
      if (frameworkAvailable())
        return frameworkGet(param1View); 
      if (extrasAvailable()) {
        Object object = param1View.getTag(this.mTagKey);
        if (this.mType.isInstance(object))
          return (T)object; 
      } 
      return null;
    }
    
    void set(View param1View, T param1T) {
      if (frameworkAvailable()) {
        frameworkSet(param1View, param1T);
      } else if (extrasAvailable() && shouldUpdate(get(param1View), param1T)) {
        ViewCompat.getOrCreateAccessibilityDelegateCompat(param1View);
        param1View.setTag(this.mTagKey, param1T);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(param1View, 0);
      } 
    }
    
    boolean shouldUpdate(T param1T1, T param1T2) {
      return param1T2.equals(param1T1) ^ true;
    }
  }
  
  private static class Api21Impl {
    static WindowInsetsCompat computeSystemWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat, Rect param1Rect) {
      WindowInsets windowInsets = param1WindowInsetsCompat.toWindowInsets();
      if (windowInsets != null)
        return WindowInsetsCompat.toWindowInsetsCompat(param1View.computeSystemWindowInsets(windowInsets, param1Rect)); 
      param1Rect.setEmpty();
      return param1WindowInsetsCompat;
    }
  }
  
  private static class Api23Impl {
    public static WindowInsets getRootWindowInsets(View param1View) {
      return param1View.getRootWindowInsets();
    }
  }
  
  private static class Api29Impl {
    static void saveAttributeDataForStyleable(View param1View, Context param1Context, int[] param1ArrayOfint, AttributeSet param1AttributeSet, TypedArray param1TypedArray, int param1Int1, int param1Int2) {
      param1View.saveAttributeDataForStyleable(param1Context, param1ArrayOfint, param1AttributeSet, param1TypedArray, param1Int1, param1Int2);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FocusDirection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FocusRealDirection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FocusRelativeDirection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface NestedScrollType {}
  
  public static interface OnUnhandledKeyEventListenerCompat {
    boolean onUnhandledKeyEvent(View param1View, KeyEvent param1KeyEvent);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ScrollAxis {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ScrollIndicators {}
  
  static class UnhandledKeyEventManager {
    private static final ArrayList<WeakReference<View>> sViewsWithListeners = new ArrayList<WeakReference<View>>();
    
    private SparseArray<WeakReference<View>> mCapturedKeys = null;
    
    private WeakReference<KeyEvent> mLastDispatchedPreViewKeyEvent = null;
    
    private WeakHashMap<View, Boolean> mViewsContainingListeners = null;
    
    static UnhandledKeyEventManager at(View param1View) {
      UnhandledKeyEventManager unhandledKeyEventManager2 = (UnhandledKeyEventManager)param1View.getTag(R.id.tag_unhandled_key_event_manager);
      UnhandledKeyEventManager unhandledKeyEventManager1 = unhandledKeyEventManager2;
      if (unhandledKeyEventManager2 == null) {
        unhandledKeyEventManager1 = new UnhandledKeyEventManager();
        param1View.setTag(R.id.tag_unhandled_key_event_manager, unhandledKeyEventManager1);
      } 
      return unhandledKeyEventManager1;
    }
    
    private View dispatchInOrder(View param1View, KeyEvent param1KeyEvent) {
      WeakHashMap<View, Boolean> weakHashMap = this.mViewsContainingListeners;
      if (weakHashMap != null && weakHashMap.containsKey(param1View)) {
        if (param1View instanceof ViewGroup) {
          ViewGroup viewGroup = (ViewGroup)param1View;
          for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
            View view = dispatchInOrder(viewGroup.getChildAt(i), param1KeyEvent);
            if (view != null)
              return view; 
          } 
        } 
        if (onUnhandledKeyEvent(param1View, param1KeyEvent))
          return param1View; 
      } 
      return null;
    }
    
    private SparseArray<WeakReference<View>> getCapturedKeys() {
      if (this.mCapturedKeys == null)
        this.mCapturedKeys = new SparseArray(); 
      return this.mCapturedKeys;
    }
    
    private boolean onUnhandledKeyEvent(View param1View, KeyEvent param1KeyEvent) {
      ArrayList<ViewCompat.OnUnhandledKeyEventListenerCompat> arrayList = (ArrayList)param1View.getTag(R.id.tag_unhandled_key_listeners);
      if (arrayList != null)
        for (int i = arrayList.size() - 1; i >= 0; i--) {
          if (((ViewCompat.OnUnhandledKeyEventListenerCompat)arrayList.get(i)).onUnhandledKeyEvent(param1View, param1KeyEvent))
            return true; 
        }  
      return false;
    }
    
    private void recalcViewsWithUnhandled() {
      null = this.mViewsContainingListeners;
      if (null != null)
        null.clear(); 
      if (sViewsWithListeners.isEmpty())
        return; 
      synchronized (sViewsWithListeners) {
        if (this.mViewsContainingListeners == null) {
          null = new WeakHashMap<View, Boolean>();
          this();
          this.mViewsContainingListeners = null;
        } 
        for (int i = sViewsWithListeners.size() - 1; i >= 0; i--) {
          View view = ((WeakReference<View>)sViewsWithListeners.get(i)).get();
          if (view == null) {
            sViewsWithListeners.remove(i);
          } else {
            this.mViewsContainingListeners.put(view, Boolean.TRUE);
            for (ViewParent viewParent = view.getParent(); viewParent instanceof View; viewParent = viewParent.getParent())
              this.mViewsContainingListeners.put((View)viewParent, Boolean.TRUE); 
          } 
        } 
        return;
      } 
    }
    
    static void registerListeningView(View param1View) {
      synchronized (sViewsWithListeners) {
        Iterator<WeakReference<View>> iterator = sViewsWithListeners.iterator();
        while (iterator.hasNext()) {
          if (((WeakReference<View>)iterator.next()).get() == param1View)
            return; 
        } 
        ArrayList<WeakReference<View>> arrayList = sViewsWithListeners;
        WeakReference<View> weakReference = new WeakReference();
        this((T)param1View);
        arrayList.add(weakReference);
        return;
      } 
    }
    
    static void unregisterListeningView(View param1View) {
      // Byte code:
      //   0: getstatic androidx/core/view/ViewCompat$UnhandledKeyEventManager.sViewsWithListeners : Ljava/util/ArrayList;
      //   3: astore_2
      //   4: aload_2
      //   5: monitorenter
      //   6: iconst_0
      //   7: istore_1
      //   8: iload_1
      //   9: getstatic androidx/core/view/ViewCompat$UnhandledKeyEventManager.sViewsWithListeners : Ljava/util/ArrayList;
      //   12: invokevirtual size : ()I
      //   15: if_icmpge -> 52
      //   18: getstatic androidx/core/view/ViewCompat$UnhandledKeyEventManager.sViewsWithListeners : Ljava/util/ArrayList;
      //   21: iload_1
      //   22: invokevirtual get : (I)Ljava/lang/Object;
      //   25: checkcast java/lang/ref/WeakReference
      //   28: invokevirtual get : ()Ljava/lang/Object;
      //   31: aload_0
      //   32: if_acmpne -> 46
      //   35: getstatic androidx/core/view/ViewCompat$UnhandledKeyEventManager.sViewsWithListeners : Ljava/util/ArrayList;
      //   38: iload_1
      //   39: invokevirtual remove : (I)Ljava/lang/Object;
      //   42: pop
      //   43: aload_2
      //   44: monitorexit
      //   45: return
      //   46: iinc #1, 1
      //   49: goto -> 8
      //   52: aload_2
      //   53: monitorexit
      //   54: return
      //   55: astore_0
      //   56: aload_2
      //   57: monitorexit
      //   58: aload_0
      //   59: athrow
      // Exception table:
      //   from	to	target	type
      //   8	45	55	finally
      //   52	54	55	finally
      //   56	58	55	finally
    }
    
    boolean dispatch(View param1View, KeyEvent param1KeyEvent) {
      boolean bool;
      if (param1KeyEvent.getAction() == 0)
        recalcViewsWithUnhandled(); 
      param1View = dispatchInOrder(param1View, param1KeyEvent);
      if (param1KeyEvent.getAction() == 0) {
        int i = param1KeyEvent.getKeyCode();
        if (param1View != null && !KeyEvent.isModifierKey(i))
          getCapturedKeys().put(i, new WeakReference<View>(param1View)); 
      } 
      if (param1View != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean preDispatch(KeyEvent param1KeyEvent) {
      WeakReference<KeyEvent> weakReference1 = this.mLastDispatchedPreViewKeyEvent;
      if (weakReference1 != null && weakReference1.get() == param1KeyEvent)
        return false; 
      this.mLastDispatchedPreViewKeyEvent = new WeakReference<KeyEvent>(param1KeyEvent);
      WeakReference<KeyEvent> weakReference2 = null;
      SparseArray<WeakReference<View>> sparseArray = getCapturedKeys();
      weakReference1 = weakReference2;
      if (param1KeyEvent.getAction() == 1) {
        int i = sparseArray.indexOfKey(param1KeyEvent.getKeyCode());
        weakReference1 = weakReference2;
        if (i >= 0) {
          weakReference1 = (WeakReference<KeyEvent>)sparseArray.valueAt(i);
          sparseArray.removeAt(i);
        } 
      } 
      weakReference2 = weakReference1;
      if (weakReference1 == null)
        weakReference2 = (WeakReference<KeyEvent>)sparseArray.get(param1KeyEvent.getKeyCode()); 
      if (weakReference2 != null) {
        View view = (View)weakReference2.get();
        if (view != null && ViewCompat.isAttachedToWindow(view))
          onUnhandledKeyEvent(view, param1KeyEvent); 
        return true;
      } 
      return false;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\ViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */