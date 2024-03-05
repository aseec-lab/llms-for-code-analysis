package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AccessibilityNodeInfoCompat {
  public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
  
  public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
  
  public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
  
  public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
  
  public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
  
  public static final String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
  
  public static final String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
  
  public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
  
  public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
  
  public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
  
  public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
  
  public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
  
  public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
  
  public static final int ACTION_CLEAR_FOCUS = 2;
  
  public static final int ACTION_CLEAR_SELECTION = 8;
  
  public static final int ACTION_CLICK = 16;
  
  public static final int ACTION_COLLAPSE = 524288;
  
  public static final int ACTION_COPY = 16384;
  
  public static final int ACTION_CUT = 65536;
  
  public static final int ACTION_DISMISS = 1048576;
  
  public static final int ACTION_EXPAND = 262144;
  
  public static final int ACTION_FOCUS = 1;
  
  public static final int ACTION_LONG_CLICK = 32;
  
  public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
  
  public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
  
  public static final int ACTION_PASTE = 32768;
  
  public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
  
  public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
  
  public static final int ACTION_SCROLL_BACKWARD = 8192;
  
  public static final int ACTION_SCROLL_FORWARD = 4096;
  
  public static final int ACTION_SELECT = 4;
  
  public static final int ACTION_SET_SELECTION = 131072;
  
  public static final int ACTION_SET_TEXT = 2097152;
  
  private static final int BOOLEAN_PROPERTY_IS_HEADING = 2;
  
  private static final int BOOLEAN_PROPERTY_IS_SHOWING_HINT = 4;
  
  private static final int BOOLEAN_PROPERTY_IS_TEXT_ENTRY_KEY = 8;
  
  private static final String BOOLEAN_PROPERTY_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY";
  
  private static final int BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 1;
  
  public static final int FOCUS_ACCESSIBILITY = 2;
  
  public static final int FOCUS_INPUT = 1;
  
  private static final String HINT_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY";
  
  public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
  
  public static final int MOVEMENT_GRANULARITY_LINE = 4;
  
  public static final int MOVEMENT_GRANULARITY_PAGE = 16;
  
  public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
  
  public static final int MOVEMENT_GRANULARITY_WORD = 2;
  
  private static final String PANE_TITLE_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY";
  
  private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
  
  private static final String SPANS_ACTION_ID_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY";
  
  private static final String SPANS_END_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY";
  
  private static final String SPANS_FLAGS_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY";
  
  private static final String SPANS_ID_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY";
  
  private static final String SPANS_START_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY";
  
  private static final String TOOLTIP_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY";
  
  private static int sClickableSpanId;
  
  private final AccessibilityNodeInfo mInfo;
  
  public int mParentVirtualDescendantId = -1;
  
  private int mVirtualDescendantId = -1;
  
  private AccessibilityNodeInfoCompat(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    this.mInfo = paramAccessibilityNodeInfo;
  }
  
  @Deprecated
  public AccessibilityNodeInfoCompat(Object paramObject) {
    this.mInfo = (AccessibilityNodeInfo)paramObject;
  }
  
  private void addSpanLocationToExtras(ClickableSpan paramClickableSpan, Spanned paramSpanned, int paramInt) {
    extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").add(Integer.valueOf(paramSpanned.getSpanStart(paramClickableSpan)));
    extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY").add(Integer.valueOf(paramSpanned.getSpanEnd(paramClickableSpan)));
    extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY").add(Integer.valueOf(paramSpanned.getSpanFlags(paramClickableSpan)));
    extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY").add(Integer.valueOf(paramInt));
  }
  
  private void clearExtrasSpans() {
    if (Build.VERSION.SDK_INT >= 19) {
      this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
      this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
      this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
      this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
    } 
  }
  
  private List<Integer> extrasIntList(String paramString) {
    if (Build.VERSION.SDK_INT < 19)
      return new ArrayList<Integer>(); 
    ArrayList<Integer> arrayList2 = this.mInfo.getExtras().getIntegerArrayList(paramString);
    ArrayList<Integer> arrayList1 = arrayList2;
    if (arrayList2 == null) {
      arrayList1 = new ArrayList();
      this.mInfo.getExtras().putIntegerArrayList(paramString, arrayList1);
    } 
    return arrayList1;
  }
  
  private static String getActionSymbolicName(int paramInt) {
    if (paramInt != 1) {
      if (paramInt != 2) {
        switch (paramInt) {
          default:
            switch (paramInt) {
              default:
                switch (paramInt) {
                  default:
                    return "ACTION_UNKNOWN";
                  case 16908361:
                    return "ACTION_PAGE_RIGHT";
                  case 16908360:
                    return "ACTION_PAGE_LEFT";
                  case 16908359:
                    return "ACTION_PAGE_DOWN";
                  case 16908358:
                    return "ACTION_PAGE_UP";
                  case 16908357:
                    return "ACTION_HIDE_TOOLTIP";
                  case 16908356:
                    break;
                } 
                return "ACTION_SHOW_TOOLTIP";
              case 16908349:
                return "ACTION_SET_PROGRESS";
              case 16908348:
                return "ACTION_CONTEXT_CLICK";
              case 16908347:
                return "ACTION_SCROLL_RIGHT";
              case 16908346:
                return "ACTION_SCROLL_DOWN";
              case 16908345:
                return "ACTION_SCROLL_LEFT";
              case 16908344:
                return "ACTION_SCROLL_UP";
              case 16908343:
                return "ACTION_SCROLL_TO_POSITION";
              case 16908342:
                break;
            } 
            return "ACTION_SHOW_ON_SCREEN";
          case 16908354:
            return "ACTION_MOVE_WINDOW";
          case 2097152:
            return "ACTION_SET_TEXT";
          case 524288:
            return "ACTION_COLLAPSE";
          case 262144:
            return "ACTION_EXPAND";
          case 131072:
            return "ACTION_SET_SELECTION";
          case 65536:
            return "ACTION_CUT";
          case 32768:
            return "ACTION_PASTE";
          case 16384:
            return "ACTION_COPY";
          case 8192:
            return "ACTION_SCROLL_BACKWARD";
          case 4096:
            return "ACTION_SCROLL_FORWARD";
          case 2048:
            return "ACTION_PREVIOUS_HTML_ELEMENT";
          case 1024:
            return "ACTION_NEXT_HTML_ELEMENT";
          case 512:
            return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
          case 256:
            return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
          case 128:
            return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
          case 64:
            return "ACTION_ACCESSIBILITY_FOCUS";
          case 32:
            return "ACTION_LONG_CLICK";
          case 16:
            return "ACTION_CLICK";
          case 8:
            return "ACTION_CLEAR_SELECTION";
          case 4:
            break;
        } 
        return "ACTION_SELECT";
      } 
      return "ACTION_CLEAR_FOCUS";
    } 
    return "ACTION_FOCUS";
  }
  
  private boolean getBooleanProperty(int paramInt) {
    Bundle bundle = getExtras();
    boolean bool = false;
    if (bundle == null)
      return false; 
    if ((bundle.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0) & paramInt) == paramInt)
      bool = true; 
    return bool;
  }
  
  public static ClickableSpan[] getClickableSpans(CharSequence paramCharSequence) {
    return (paramCharSequence instanceof Spanned) ? (ClickableSpan[])((Spanned)paramCharSequence).getSpans(0, paramCharSequence.length(), ClickableSpan.class) : null;
  }
  
  private SparseArray<WeakReference<ClickableSpan>> getOrCreateSpansFromViewTags(View paramView) {
    SparseArray<WeakReference<ClickableSpan>> sparseArray2 = getSpansFromViewTags(paramView);
    SparseArray<WeakReference<ClickableSpan>> sparseArray1 = sparseArray2;
    if (sparseArray2 == null) {
      sparseArray1 = new SparseArray();
      paramView.setTag(R.id.tag_accessibility_clickable_spans, sparseArray1);
    } 
    return sparseArray1;
  }
  
  private SparseArray<WeakReference<ClickableSpan>> getSpansFromViewTags(View paramView) {
    return (SparseArray<WeakReference<ClickableSpan>>)paramView.getTag(R.id.tag_accessibility_clickable_spans);
  }
  
  private boolean hasSpans() {
    return extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").isEmpty() ^ true;
  }
  
  private int idForClickableSpan(ClickableSpan paramClickableSpan, SparseArray<WeakReference<ClickableSpan>> paramSparseArray) {
    if (paramSparseArray != null)
      for (byte b = 0; b < paramSparseArray.size(); b++) {
        if (paramClickableSpan.equals(((WeakReference<ClickableSpan>)paramSparseArray.valueAt(b)).get()))
          return paramSparseArray.keyAt(b); 
      }  
    int i = sClickableSpanId;
    sClickableSpanId = i + 1;
    return i;
  }
  
  public static AccessibilityNodeInfoCompat obtain() {
    return wrap(AccessibilityNodeInfo.obtain());
  }
  
  public static AccessibilityNodeInfoCompat obtain(View paramView) {
    return wrap(AccessibilityNodeInfo.obtain(paramView));
  }
  
  public static AccessibilityNodeInfoCompat obtain(View paramView, int paramInt) {
    return (Build.VERSION.SDK_INT >= 16) ? wrapNonNullInstance(AccessibilityNodeInfo.obtain(paramView, paramInt)) : null;
  }
  
  public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    return wrap(AccessibilityNodeInfo.obtain(paramAccessibilityNodeInfoCompat.mInfo));
  }
  
  private void removeCollectedSpans(View paramView) {
    SparseArray<WeakReference<ClickableSpan>> sparseArray = getSpansFromViewTags(paramView);
    if (sparseArray != null) {
      byte b2;
      ArrayList<Integer> arrayList = new ArrayList();
      byte b3 = 0;
      byte b1 = 0;
      while (true) {
        b2 = b3;
        if (b1 < sparseArray.size()) {
          if (((WeakReference)sparseArray.valueAt(b1)).get() == null)
            arrayList.add(Integer.valueOf(b1)); 
          b1++;
          continue;
        } 
        break;
      } 
      while (b2 < arrayList.size()) {
        sparseArray.remove(((Integer)arrayList.get(b2)).intValue());
        b2++;
      } 
    } 
  }
  
  private void setBooleanProperty(int paramInt, boolean paramBoolean) {
    Bundle bundle = getExtras();
    if (bundle != null) {
      boolean bool;
      int i = bundle.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0);
      if (paramBoolean) {
        bool = paramInt;
      } else {
        bool = false;
      } 
      bundle.putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", bool | i & (paramInt ^ 0xFFFFFFFF));
    } 
  }
  
  public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    return new AccessibilityNodeInfoCompat(paramAccessibilityNodeInfo);
  }
  
  static AccessibilityNodeInfoCompat wrapNonNullInstance(Object paramObject) {
    return (paramObject != null) ? new AccessibilityNodeInfoCompat(paramObject) : null;
  }
  
  public void addAction(int paramInt) {
    this.mInfo.addAction(paramInt);
  }
  
  public void addAction(AccessibilityActionCompat paramAccessibilityActionCompat) {
    if (Build.VERSION.SDK_INT >= 21)
      this.mInfo.addAction((AccessibilityNodeInfo.AccessibilityAction)paramAccessibilityActionCompat.mAction); 
  }
  
  public void addChild(View paramView) {
    this.mInfo.addChild(paramView);
  }
  
  public void addChild(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.addChild(paramView, paramInt); 
  }
  
  public void addSpansToExtras(CharSequence paramCharSequence, View paramView) {
    if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 26) {
      clearExtrasSpans();
      removeCollectedSpans(paramView);
      ClickableSpan[] arrayOfClickableSpan = getClickableSpans(paramCharSequence);
      if (arrayOfClickableSpan != null && arrayOfClickableSpan.length > 0) {
        getExtras().putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY", R.id.accessibility_action_clickable_span);
        SparseArray<WeakReference<ClickableSpan>> sparseArray = getOrCreateSpansFromViewTags(paramView);
        for (byte b = 0; arrayOfClickableSpan != null && b < arrayOfClickableSpan.length; b++) {
          int i = idForClickableSpan(arrayOfClickableSpan[b], sparseArray);
          sparseArray.put(i, new WeakReference<ClickableSpan>(arrayOfClickableSpan[b]));
          addSpanLocationToExtras(arrayOfClickableSpan[b], (Spanned)paramCharSequence, i);
        } 
      } 
    } 
  }
  
  public boolean canOpenPopup() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.canOpenPopup() : false;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (!(paramObject instanceof AccessibilityNodeInfoCompat))
      return false; 
    paramObject = paramObject;
    AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
    if (accessibilityNodeInfo == null) {
      if (((AccessibilityNodeInfoCompat)paramObject).mInfo != null)
        return false; 
    } else if (!accessibilityNodeInfo.equals(((AccessibilityNodeInfoCompat)paramObject).mInfo)) {
      return false;
    } 
    return (this.mVirtualDescendantId != ((AccessibilityNodeInfoCompat)paramObject).mVirtualDescendantId) ? false : (!(this.mParentVirtualDescendantId != ((AccessibilityNodeInfoCompat)paramObject).mParentVirtualDescendantId));
  }
  
  public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String paramString) {
    ArrayList<AccessibilityNodeInfoCompat> arrayList = new ArrayList();
    List<AccessibilityNodeInfo> list = this.mInfo.findAccessibilityNodeInfosByText(paramString);
    int i = list.size();
    for (byte b = 0; b < i; b++)
      arrayList.add(wrap(list.get(b))); 
    return arrayList;
  }
  
  public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String paramString) {
    if (Build.VERSION.SDK_INT >= 18) {
      List list = this.mInfo.findAccessibilityNodeInfosByViewId(paramString);
      ArrayList<AccessibilityNodeInfoCompat> arrayList = new ArrayList();
      Iterator<AccessibilityNodeInfo> iterator = list.iterator();
      while (iterator.hasNext())
        arrayList.add(wrap(iterator.next())); 
      return arrayList;
    } 
    return Collections.emptyList();
  }
  
  public AccessibilityNodeInfoCompat findFocus(int paramInt) {
    return (Build.VERSION.SDK_INT >= 16) ? wrapNonNullInstance(this.mInfo.findFocus(paramInt)) : null;
  }
  
  public AccessibilityNodeInfoCompat focusSearch(int paramInt) {
    return (Build.VERSION.SDK_INT >= 16) ? wrapNonNullInstance(this.mInfo.focusSearch(paramInt)) : null;
  }
  
  public List<AccessibilityActionCompat> getActionList() {
    List list;
    if (Build.VERSION.SDK_INT >= 21) {
      list = this.mInfo.getActionList();
    } else {
      list = null;
    } 
    if (list != null) {
      ArrayList<AccessibilityActionCompat> arrayList = new ArrayList();
      int i = list.size();
      for (byte b = 0; b < i; b++)
        arrayList.add(new AccessibilityActionCompat(list.get(b))); 
      return arrayList;
    } 
    return Collections.emptyList();
  }
  
  public int getActions() {
    return this.mInfo.getActions();
  }
  
  @Deprecated
  public void getBoundsInParent(Rect paramRect) {
    this.mInfo.getBoundsInParent(paramRect);
  }
  
  public void getBoundsInScreen(Rect paramRect) {
    this.mInfo.getBoundsInScreen(paramRect);
  }
  
  public AccessibilityNodeInfoCompat getChild(int paramInt) {
    return wrapNonNullInstance(this.mInfo.getChild(paramInt));
  }
  
  public int getChildCount() {
    return this.mInfo.getChildCount();
  }
  
  public CharSequence getClassName() {
    return this.mInfo.getClassName();
  }
  
  public CollectionInfoCompat getCollectionInfo() {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo.CollectionInfo collectionInfo = this.mInfo.getCollectionInfo();
      if (collectionInfo != null)
        return new CollectionInfoCompat(collectionInfo); 
    } 
    return null;
  }
  
  public CollectionItemInfoCompat getCollectionItemInfo() {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo = this.mInfo.getCollectionItemInfo();
      if (collectionItemInfo != null)
        return new CollectionItemInfoCompat(collectionItemInfo); 
    } 
    return null;
  }
  
  public CharSequence getContentDescription() {
    return this.mInfo.getContentDescription();
  }
  
  public int getDrawingOrder() {
    return (Build.VERSION.SDK_INT >= 24) ? this.mInfo.getDrawingOrder() : 0;
  }
  
  public CharSequence getError() {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.getError() : null;
  }
  
  public Bundle getExtras() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getExtras() : new Bundle();
  }
  
  public CharSequence getHintText() {
    return (Build.VERSION.SDK_INT >= 26) ? this.mInfo.getHintText() : ((Build.VERSION.SDK_INT >= 19) ? this.mInfo.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY") : null);
  }
  
  @Deprecated
  public Object getInfo() {
    return this.mInfo;
  }
  
  public int getInputType() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getInputType() : 0;
  }
  
  public AccessibilityNodeInfoCompat getLabelFor() {
    return (Build.VERSION.SDK_INT >= 17) ? wrapNonNullInstance(this.mInfo.getLabelFor()) : null;
  }
  
  public AccessibilityNodeInfoCompat getLabeledBy() {
    return (Build.VERSION.SDK_INT >= 17) ? wrapNonNullInstance(this.mInfo.getLabeledBy()) : null;
  }
  
  public int getLiveRegion() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getLiveRegion() : 0;
  }
  
  public int getMaxTextLength() {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.getMaxTextLength() : -1;
  }
  
  public int getMovementGranularities() {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.getMovementGranularities() : 0;
  }
  
  public CharSequence getPackageName() {
    return this.mInfo.getPackageName();
  }
  
  public CharSequence getPaneTitle() {
    return (Build.VERSION.SDK_INT >= 28) ? this.mInfo.getPaneTitle() : ((Build.VERSION.SDK_INT >= 19) ? this.mInfo.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY") : null);
  }
  
  public AccessibilityNodeInfoCompat getParent() {
    return wrapNonNullInstance(this.mInfo.getParent());
  }
  
  public RangeInfoCompat getRangeInfo() {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo.RangeInfo rangeInfo = this.mInfo.getRangeInfo();
      if (rangeInfo != null)
        return new RangeInfoCompat(rangeInfo); 
    } 
    return null;
  }
  
  public CharSequence getRoleDescription() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getExtras().getCharSequence("AccessibilityNodeInfo.roleDescription") : null;
  }
  
  public CharSequence getText() {
    if (hasSpans()) {
      List<Integer> list4 = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
      List<Integer> list1 = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
      List<Integer> list2 = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
      List<Integer> list3 = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
      CharSequence charSequence = this.mInfo.getText();
      int i = this.mInfo.getText().length();
      byte b = 0;
      SpannableString spannableString = new SpannableString(TextUtils.substring(charSequence, 0, i));
      while (b < list4.size()) {
        spannableString.setSpan(new AccessibilityClickableSpanCompat(((Integer)list3.get(b)).intValue(), this, getExtras().getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY")), ((Integer)list4.get(b)).intValue(), ((Integer)list1.get(b)).intValue(), ((Integer)list2.get(b)).intValue());
        b++;
      } 
      return (CharSequence)spannableString;
    } 
    return this.mInfo.getText();
  }
  
  public int getTextSelectionEnd() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.getTextSelectionEnd() : -1;
  }
  
  public int getTextSelectionStart() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.getTextSelectionStart() : -1;
  }
  
  public CharSequence getTooltipText() {
    return (Build.VERSION.SDK_INT >= 28) ? this.mInfo.getTooltipText() : ((Build.VERSION.SDK_INT >= 19) ? this.mInfo.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY") : null);
  }
  
  public TouchDelegateInfoCompat getTouchDelegateInfo() {
    if (Build.VERSION.SDK_INT >= 29) {
      AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo = this.mInfo.getTouchDelegateInfo();
      if (touchDelegateInfo != null)
        return new TouchDelegateInfoCompat(touchDelegateInfo); 
    } 
    return null;
  }
  
  public AccessibilityNodeInfoCompat getTraversalAfter() {
    return (Build.VERSION.SDK_INT >= 22) ? wrapNonNullInstance(this.mInfo.getTraversalAfter()) : null;
  }
  
  public AccessibilityNodeInfoCompat getTraversalBefore() {
    return (Build.VERSION.SDK_INT >= 22) ? wrapNonNullInstance(this.mInfo.getTraversalBefore()) : null;
  }
  
  public String getViewIdResourceName() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.getViewIdResourceName() : null;
  }
  
  public AccessibilityWindowInfoCompat getWindow() {
    return (Build.VERSION.SDK_INT >= 21) ? AccessibilityWindowInfoCompat.wrapNonNullInstance(this.mInfo.getWindow()) : null;
  }
  
  public int getWindowId() {
    return this.mInfo.getWindowId();
  }
  
  public int hashCode() {
    int i;
    AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
    if (accessibilityNodeInfo == null) {
      i = 0;
    } else {
      i = accessibilityNodeInfo.hashCode();
    } 
    return i;
  }
  
  public boolean isAccessibilityFocused() {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.isAccessibilityFocused() : false;
  }
  
  public boolean isCheckable() {
    return this.mInfo.isCheckable();
  }
  
  public boolean isChecked() {
    return this.mInfo.isChecked();
  }
  
  public boolean isClickable() {
    return this.mInfo.isClickable();
  }
  
  public boolean isContentInvalid() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.isContentInvalid() : false;
  }
  
  public boolean isContextClickable() {
    return (Build.VERSION.SDK_INT >= 23) ? this.mInfo.isContextClickable() : false;
  }
  
  public boolean isDismissable() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.isDismissable() : false;
  }
  
  public boolean isEditable() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.isEditable() : false;
  }
  
  public boolean isEnabled() {
    return this.mInfo.isEnabled();
  }
  
  public boolean isFocusable() {
    return this.mInfo.isFocusable();
  }
  
  public boolean isFocused() {
    return this.mInfo.isFocused();
  }
  
  public boolean isHeading() {
    if (Build.VERSION.SDK_INT >= 28)
      return this.mInfo.isHeading(); 
    boolean bool1 = getBooleanProperty(2);
    boolean bool = true;
    if (bool1)
      return true; 
    CollectionItemInfoCompat collectionItemInfoCompat = getCollectionItemInfo();
    if (collectionItemInfoCompat == null || !collectionItemInfoCompat.isHeading())
      bool = false; 
    return bool;
  }
  
  public boolean isImportantForAccessibility() {
    return (Build.VERSION.SDK_INT >= 24) ? this.mInfo.isImportantForAccessibility() : true;
  }
  
  public boolean isLongClickable() {
    return this.mInfo.isLongClickable();
  }
  
  public boolean isMultiLine() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.isMultiLine() : false;
  }
  
  public boolean isPassword() {
    return this.mInfo.isPassword();
  }
  
  public boolean isScreenReaderFocusable() {
    return (Build.VERSION.SDK_INT >= 28) ? this.mInfo.isScreenReaderFocusable() : getBooleanProperty(1);
  }
  
  public boolean isScrollable() {
    return this.mInfo.isScrollable();
  }
  
  public boolean isSelected() {
    return this.mInfo.isSelected();
  }
  
  public boolean isShowingHintText() {
    return (Build.VERSION.SDK_INT >= 26) ? this.mInfo.isShowingHintText() : getBooleanProperty(4);
  }
  
  public boolean isTextEntryKey() {
    return (Build.VERSION.SDK_INT >= 29) ? this.mInfo.isTextEntryKey() : getBooleanProperty(8);
  }
  
  public boolean isVisibleToUser() {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.isVisibleToUser() : false;
  }
  
  public boolean performAction(int paramInt) {
    return this.mInfo.performAction(paramInt);
  }
  
  public boolean performAction(int paramInt, Bundle paramBundle) {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.performAction(paramInt, paramBundle) : false;
  }
  
  public void recycle() {
    this.mInfo.recycle();
  }
  
  public boolean refresh() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.refresh() : false;
  }
  
  public boolean removeAction(AccessibilityActionCompat paramAccessibilityActionCompat) {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction)paramAccessibilityActionCompat.mAction) : false;
  }
  
  public boolean removeChild(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.removeChild(paramView) : false;
  }
  
  public boolean removeChild(View paramView, int paramInt) {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.removeChild(paramView, paramInt) : false;
  }
  
  public void setAccessibilityFocused(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setAccessibilityFocused(paramBoolean); 
  }
  
  @Deprecated
  public void setBoundsInParent(Rect paramRect) {
    this.mInfo.setBoundsInParent(paramRect);
  }
  
  public void setBoundsInScreen(Rect paramRect) {
    this.mInfo.setBoundsInScreen(paramRect);
  }
  
  public void setCanOpenPopup(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setCanOpenPopup(paramBoolean); 
  }
  
  public void setCheckable(boolean paramBoolean) {
    this.mInfo.setCheckable(paramBoolean);
  }
  
  public void setChecked(boolean paramBoolean) {
    this.mInfo.setChecked(paramBoolean);
  }
  
  public void setClassName(CharSequence paramCharSequence) {
    this.mInfo.setClassName(paramCharSequence);
  }
  
  public void setClickable(boolean paramBoolean) {
    this.mInfo.setClickable(paramBoolean);
  }
  
  public void setCollectionInfo(Object paramObject) {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
      if (paramObject == null) {
        paramObject = null;
      } else {
        paramObject = ((CollectionInfoCompat)paramObject).mInfo;
      } 
      accessibilityNodeInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo)paramObject);
    } 
  }
  
  public void setCollectionItemInfo(Object paramObject) {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
      if (paramObject == null) {
        paramObject = null;
      } else {
        paramObject = ((CollectionItemInfoCompat)paramObject).mInfo;
      } 
      accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo)paramObject);
    } 
  }
  
  public void setContentDescription(CharSequence paramCharSequence) {
    this.mInfo.setContentDescription(paramCharSequence);
  }
  
  public void setContentInvalid(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setContentInvalid(paramBoolean); 
  }
  
  public void setContextClickable(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 23)
      this.mInfo.setContextClickable(paramBoolean); 
  }
  
  public void setDismissable(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setDismissable(paramBoolean); 
  }
  
  public void setDrawingOrder(int paramInt) {
    if (Build.VERSION.SDK_INT >= 24)
      this.mInfo.setDrawingOrder(paramInt); 
  }
  
  public void setEditable(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 18)
      this.mInfo.setEditable(paramBoolean); 
  }
  
  public void setEnabled(boolean paramBoolean) {
    this.mInfo.setEnabled(paramBoolean);
  }
  
  public void setError(CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 21)
      this.mInfo.setError(paramCharSequence); 
  }
  
  public void setFocusable(boolean paramBoolean) {
    this.mInfo.setFocusable(paramBoolean);
  }
  
  public void setFocused(boolean paramBoolean) {
    this.mInfo.setFocused(paramBoolean);
  }
  
  public void setHeading(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 28) {
      this.mInfo.setHeading(paramBoolean);
    } else {
      setBooleanProperty(2, paramBoolean);
    } 
  }
  
  public void setHintText(CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 26) {
      this.mInfo.setHintText(paramCharSequence);
    } else if (Build.VERSION.SDK_INT >= 19) {
      this.mInfo.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY", paramCharSequence);
    } 
  }
  
  public void setImportantForAccessibility(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 24)
      this.mInfo.setImportantForAccessibility(paramBoolean); 
  }
  
  public void setInputType(int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setInputType(paramInt); 
  }
  
  public void setLabelFor(View paramView) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabelFor(paramView); 
  }
  
  public void setLabelFor(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabelFor(paramView, paramInt); 
  }
  
  public void setLabeledBy(View paramView) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabeledBy(paramView); 
  }
  
  public void setLabeledBy(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabeledBy(paramView, paramInt); 
  }
  
  public void setLiveRegion(int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setLiveRegion(paramInt); 
  }
  
  public void setLongClickable(boolean paramBoolean) {
    this.mInfo.setLongClickable(paramBoolean);
  }
  
  public void setMaxTextLength(int paramInt) {
    if (Build.VERSION.SDK_INT >= 21)
      this.mInfo.setMaxTextLength(paramInt); 
  }
  
  public void setMovementGranularities(int paramInt) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setMovementGranularities(paramInt); 
  }
  
  public void setMultiLine(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setMultiLine(paramBoolean); 
  }
  
  public void setPackageName(CharSequence paramCharSequence) {
    this.mInfo.setPackageName(paramCharSequence);
  }
  
  public void setPaneTitle(CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 28) {
      this.mInfo.setPaneTitle(paramCharSequence);
    } else if (Build.VERSION.SDK_INT >= 19) {
      this.mInfo.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY", paramCharSequence);
    } 
  }
  
  public void setParent(View paramView) {
    this.mParentVirtualDescendantId = -1;
    this.mInfo.setParent(paramView);
  }
  
  public void setParent(View paramView, int paramInt) {
    this.mParentVirtualDescendantId = paramInt;
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setParent(paramView, paramInt); 
  }
  
  public void setPassword(boolean paramBoolean) {
    this.mInfo.setPassword(paramBoolean);
  }
  
  public void setRangeInfo(RangeInfoCompat paramRangeInfoCompat) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo)paramRangeInfoCompat.mInfo); 
  }
  
  public void setRoleDescription(CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", paramCharSequence); 
  }
  
  public void setScreenReaderFocusable(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 28) {
      this.mInfo.setScreenReaderFocusable(paramBoolean);
    } else {
      setBooleanProperty(1, paramBoolean);
    } 
  }
  
  public void setScrollable(boolean paramBoolean) {
    this.mInfo.setScrollable(paramBoolean);
  }
  
  public void setSelected(boolean paramBoolean) {
    this.mInfo.setSelected(paramBoolean);
  }
  
  public void setShowingHintText(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 26) {
      this.mInfo.setShowingHintText(paramBoolean);
    } else {
      setBooleanProperty(4, paramBoolean);
    } 
  }
  
  public void setSource(View paramView) {
    this.mVirtualDescendantId = -1;
    this.mInfo.setSource(paramView);
  }
  
  public void setSource(View paramView, int paramInt) {
    this.mVirtualDescendantId = paramInt;
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setSource(paramView, paramInt); 
  }
  
  public void setText(CharSequence paramCharSequence) {
    this.mInfo.setText(paramCharSequence);
  }
  
  public void setTextEntryKey(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 29) {
      this.mInfo.setTextEntryKey(paramBoolean);
    } else {
      setBooleanProperty(8, paramBoolean);
    } 
  }
  
  public void setTextSelection(int paramInt1, int paramInt2) {
    if (Build.VERSION.SDK_INT >= 18)
      this.mInfo.setTextSelection(paramInt1, paramInt2); 
  }
  
  public void setTooltipText(CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 28) {
      this.mInfo.setTooltipText(paramCharSequence);
    } else if (Build.VERSION.SDK_INT >= 19) {
      this.mInfo.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY", paramCharSequence);
    } 
  }
  
  public void setTouchDelegateInfo(TouchDelegateInfoCompat paramTouchDelegateInfoCompat) {
    if (Build.VERSION.SDK_INT >= 29)
      this.mInfo.setTouchDelegateInfo(paramTouchDelegateInfoCompat.mInfo); 
  }
  
  public void setTraversalAfter(View paramView) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalAfter(paramView); 
  }
  
  public void setTraversalAfter(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalAfter(paramView, paramInt); 
  }
  
  public void setTraversalBefore(View paramView) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalBefore(paramView); 
  }
  
  public void setTraversalBefore(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalBefore(paramView, paramInt); 
  }
  
  public void setViewIdResourceName(String paramString) {
    if (Build.VERSION.SDK_INT >= 18)
      this.mInfo.setViewIdResourceName(paramString); 
  }
  
  public void setVisibleToUser(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setVisibleToUser(paramBoolean); 
  }
  
  public String toString() {
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append(super.toString());
    Rect rect = new Rect();
    getBoundsInParent(rect);
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("; boundsInParent: ");
    stringBuilder2.append(rect);
    stringBuilder3.append(stringBuilder2.toString());
    getBoundsInScreen(rect);
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("; boundsInScreen: ");
    stringBuilder2.append(rect);
    stringBuilder3.append(stringBuilder2.toString());
    stringBuilder3.append("; packageName: ");
    stringBuilder3.append(getPackageName());
    stringBuilder3.append("; className: ");
    stringBuilder3.append(getClassName());
    stringBuilder3.append("; text: ");
    stringBuilder3.append(getText());
    stringBuilder3.append("; contentDescription: ");
    stringBuilder3.append(getContentDescription());
    stringBuilder3.append("; viewId: ");
    stringBuilder3.append(getViewIdResourceName());
    stringBuilder3.append("; checkable: ");
    stringBuilder3.append(isCheckable());
    stringBuilder3.append("; checked: ");
    stringBuilder3.append(isChecked());
    stringBuilder3.append("; focusable: ");
    stringBuilder3.append(isFocusable());
    stringBuilder3.append("; focused: ");
    stringBuilder3.append(isFocused());
    stringBuilder3.append("; selected: ");
    stringBuilder3.append(isSelected());
    stringBuilder3.append("; clickable: ");
    stringBuilder3.append(isClickable());
    stringBuilder3.append("; longClickable: ");
    stringBuilder3.append(isLongClickable());
    stringBuilder3.append("; enabled: ");
    stringBuilder3.append(isEnabled());
    stringBuilder3.append("; password: ");
    stringBuilder3.append(isPassword());
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("; scrollable: ");
    stringBuilder1.append(isScrollable());
    stringBuilder3.append(stringBuilder1.toString());
    stringBuilder3.append("; [");
    if (Build.VERSION.SDK_INT >= 21) {
      List<AccessibilityActionCompat> list = getActionList();
      for (byte b = 0; b < list.size(); b++) {
        AccessibilityActionCompat accessibilityActionCompat = list.get(b);
        String str2 = getActionSymbolicName(accessibilityActionCompat.getId());
        String str1 = str2;
        if (str2.equals("ACTION_UNKNOWN")) {
          str1 = str2;
          if (accessibilityActionCompat.getLabel() != null)
            str1 = accessibilityActionCompat.getLabel().toString(); 
        } 
        stringBuilder3.append(str1);
        if (b != list.size() - 1)
          stringBuilder3.append(", "); 
      } 
    } else {
      int i = getActions();
      while (i != 0) {
        int k = 1 << Integer.numberOfTrailingZeros(i);
        int j = i & (k ^ 0xFFFFFFFF);
        stringBuilder3.append(getActionSymbolicName(k));
        i = j;
        if (j != 0) {
          stringBuilder3.append(", ");
          i = j;
        } 
      } 
    } 
    stringBuilder3.append("]");
    return stringBuilder3.toString();
  }
  
  public AccessibilityNodeInfo unwrap() {
    return this.mInfo;
  }
  
  public static class AccessibilityActionCompat {
    public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS;
    
    public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS;
    
    public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
    
    public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION;
    
    public static final AccessibilityActionCompat ACTION_CLICK;
    
    public static final AccessibilityActionCompat ACTION_COLLAPSE;
    
    public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK;
    
    public static final AccessibilityActionCompat ACTION_COPY;
    
    public static final AccessibilityActionCompat ACTION_CUT;
    
    public static final AccessibilityActionCompat ACTION_DISMISS;
    
    public static final AccessibilityActionCompat ACTION_EXPAND;
    
    public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
    
    public static final AccessibilityActionCompat ACTION_HIDE_TOOLTIP;
    
    public static final AccessibilityActionCompat ACTION_LONG_CLICK;
    
    public static final AccessibilityActionCompat ACTION_MOVE_WINDOW;
    
    public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
    
    public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT;
    
    public static final AccessibilityActionCompat ACTION_PAGE_DOWN;
    
    public static final AccessibilityActionCompat ACTION_PAGE_LEFT;
    
    public static final AccessibilityActionCompat ACTION_PAGE_RIGHT;
    
    public static final AccessibilityActionCompat ACTION_PAGE_UP;
    
    public static final AccessibilityActionCompat ACTION_PASTE;
    
    public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
    
    public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_DOWN;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_LEFT;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_UP;
    
    public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, null);
    
    public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
    
    public static final AccessibilityActionCompat ACTION_SET_SELECTION;
    
    public static final AccessibilityActionCompat ACTION_SET_TEXT;
    
    public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
    
    public static final AccessibilityActionCompat ACTION_SHOW_TOOLTIP;
    
    private static final String TAG = "A11yActionCompat";
    
    final Object mAction;
    
    protected final AccessibilityViewCommand mCommand;
    
    private final int mId;
    
    private final Class<? extends AccessibilityViewCommand.CommandArguments> mViewCommandArgumentClass;
    
    static {
      ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
      ACTION_CLICK = new AccessibilityActionCompat(16, null);
      ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
      ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
      ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
      ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null, (Class)AccessibilityViewCommand.MoveAtGranularityArguments.class);
      ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null, (Class)AccessibilityViewCommand.MoveAtGranularityArguments.class);
      ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null, (Class)AccessibilityViewCommand.MoveHtmlArguments.class);
      ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null, (Class)AccessibilityViewCommand.MoveHtmlArguments.class);
      ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
      ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
      ACTION_COPY = new AccessibilityActionCompat(16384, null);
      ACTION_PASTE = new AccessibilityActionCompat(32768, null);
      ACTION_CUT = new AccessibilityActionCompat(65536, null);
      ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null, (Class)AccessibilityViewCommand.SetSelectionArguments.class);
      ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
      ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
      ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
      ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null, (Class)AccessibilityViewCommand.SetTextArguments.class);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(accessibilityAction1, 16908342, null, null, null);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(accessibilityAction1, 16908343, null, null, (Class)AccessibilityViewCommand.ScrollToPositionArguments.class);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SCROLL_UP = new AccessibilityActionCompat(accessibilityAction1, 16908344, null, null, null);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SCROLL_LEFT = new AccessibilityActionCompat(accessibilityAction1, 16908345, null, null, null);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SCROLL_DOWN = new AccessibilityActionCompat(accessibilityAction1, 16908346, null, null, null);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(accessibilityAction1, 16908347, null, null, null);
      if (Build.VERSION.SDK_INT >= 29) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_PAGE_UP = new AccessibilityActionCompat(accessibilityAction1, 16908358, null, null, null);
      if (Build.VERSION.SDK_INT >= 29) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_PAGE_DOWN = new AccessibilityActionCompat(accessibilityAction1, 16908359, null, null, null);
      if (Build.VERSION.SDK_INT >= 29) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_PAGE_LEFT = new AccessibilityActionCompat(accessibilityAction1, 16908360, null, null, null);
      if (Build.VERSION.SDK_INT >= 29) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_PAGE_RIGHT = new AccessibilityActionCompat(accessibilityAction1, 16908361, null, null, null);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(accessibilityAction1, 16908348, null, null, null);
      if (Build.VERSION.SDK_INT >= 24) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SET_PROGRESS = new AccessibilityActionCompat(accessibilityAction1, 16908349, null, null, (Class)AccessibilityViewCommand.SetProgressArguments.class);
      if (Build.VERSION.SDK_INT >= 26) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_MOVE_WINDOW = new AccessibilityActionCompat(accessibilityAction1, 16908354, null, null, (Class)AccessibilityViewCommand.MoveWindowArguments.class);
      if (Build.VERSION.SDK_INT >= 28) {
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP;
      } else {
        accessibilityAction1 = null;
      } 
      ACTION_SHOW_TOOLTIP = new AccessibilityActionCompat(accessibilityAction1, 16908356, null, null, null);
      AccessibilityNodeInfo.AccessibilityAction accessibilityAction1 = accessibilityAction2;
      if (Build.VERSION.SDK_INT >= 28)
        accessibilityAction1 = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP; 
      ACTION_HIDE_TOOLTIP = new AccessibilityActionCompat(accessibilityAction1, 16908357, null, null, null);
    }
    
    public AccessibilityActionCompat(int param1Int, CharSequence param1CharSequence) {
      this(null, param1Int, param1CharSequence, null, null);
    }
    
    public AccessibilityActionCompat(int param1Int, CharSequence param1CharSequence, AccessibilityViewCommand param1AccessibilityViewCommand) {
      this(null, param1Int, param1CharSequence, param1AccessibilityViewCommand, null);
    }
    
    private AccessibilityActionCompat(int param1Int, CharSequence param1CharSequence, Class<? extends AccessibilityViewCommand.CommandArguments> param1Class) {
      this(null, param1Int, param1CharSequence, null, param1Class);
    }
    
    AccessibilityActionCompat(Object param1Object) {
      this(param1Object, 0, null, null, null);
    }
    
    AccessibilityActionCompat(Object param1Object, int param1Int, CharSequence param1CharSequence, AccessibilityViewCommand param1AccessibilityViewCommand, Class<? extends AccessibilityViewCommand.CommandArguments> param1Class) {
      this.mId = param1Int;
      this.mCommand = param1AccessibilityViewCommand;
      if (Build.VERSION.SDK_INT >= 21 && param1Object == null) {
        this.mAction = new AccessibilityNodeInfo.AccessibilityAction(param1Int, param1CharSequence);
      } else {
        this.mAction = param1Object;
      } 
      this.mViewCommandArgumentClass = param1Class;
    }
    
    public AccessibilityActionCompat createReplacementAction(CharSequence param1CharSequence, AccessibilityViewCommand param1AccessibilityViewCommand) {
      return new AccessibilityActionCompat(null, this.mId, param1CharSequence, param1AccessibilityViewCommand, this.mViewCommandArgumentClass);
    }
    
    public boolean equals(Object param1Object) {
      if (param1Object == null)
        return false; 
      if (!(param1Object instanceof AccessibilityActionCompat))
        return false; 
      AccessibilityActionCompat accessibilityActionCompat = (AccessibilityActionCompat)param1Object;
      param1Object = this.mAction;
      if (param1Object == null) {
        if (accessibilityActionCompat.mAction != null)
          return false; 
      } else if (!param1Object.equals(accessibilityActionCompat.mAction)) {
        return false;
      } 
      return true;
    }
    
    public int getId() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getId() : 0;
    }
    
    public CharSequence getLabel() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getLabel() : null;
    }
    
    public int hashCode() {
      boolean bool;
      Object object = this.mAction;
      if (object != null) {
        bool = object.hashCode();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean perform(View param1View, Bundle param1Bundle) {
      if (this.mCommand != null) {
        AccessibilityViewCommand.CommandArguments commandArguments = null;
        Object object = null;
        Class<? extends AccessibilityViewCommand.CommandArguments> clazz = this.mViewCommandArgumentClass;
        if (clazz != null) {
          Exception exception1;
          String str;
          try {
            commandArguments = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            try {
              commandArguments.setBundle(param1Bundle);
            } catch (Exception exception2) {
              AccessibilityViewCommand.CommandArguments commandArguments1 = commandArguments;
              exception = exception2;
            } 
          } catch (Exception exception) {
            exception1 = exception2;
          } 
          Class<? extends AccessibilityViewCommand.CommandArguments> clazz1 = this.mViewCommandArgumentClass;
          if (clazz1 == null) {
            str = "null";
          } else {
            str = str.getName();
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Failed to execute command with argument class ViewCommandArgument: ");
          stringBuilder.append(str);
          Log.e("A11yActionCompat", stringBuilder.toString(), exception);
          exception = exception1;
        } 
        return this.mCommand.perform(param1View, (AccessibilityViewCommand.CommandArguments)exception);
      } 
      return false;
    }
    
    static {
      AccessibilityNodeInfo.AccessibilityAction accessibilityAction2 = null;
    }
  }
  
  public static class CollectionInfoCompat {
    public static final int SELECTION_MODE_MULTIPLE = 2;
    
    public static final int SELECTION_MODE_NONE = 0;
    
    public static final int SELECTION_MODE_SINGLE = 1;
    
    final Object mInfo;
    
    CollectionInfoCompat(Object param1Object) {
      this.mInfo = param1Object;
    }
    
    public static CollectionInfoCompat obtain(int param1Int1, int param1Int2, boolean param1Boolean) {
      return (Build.VERSION.SDK_INT >= 19) ? new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(param1Int1, param1Int2, param1Boolean)) : new CollectionInfoCompat(null);
    }
    
    public static CollectionInfoCompat obtain(int param1Int1, int param1Int2, boolean param1Boolean, int param1Int3) {
      return (Build.VERSION.SDK_INT >= 21) ? new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(param1Int1, param1Int2, param1Boolean, param1Int3)) : ((Build.VERSION.SDK_INT >= 19) ? new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(param1Int1, param1Int2, param1Boolean)) : new CollectionInfoCompat(null));
    }
    
    public int getColumnCount() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getColumnCount() : -1;
    }
    
    public int getRowCount() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getRowCount() : -1;
    }
    
    public int getSelectionMode() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getSelectionMode() : 0;
    }
    
    public boolean isHierarchical() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).isHierarchical() : false;
    }
  }
  
  public static class CollectionItemInfoCompat {
    final Object mInfo;
    
    CollectionItemInfoCompat(Object param1Object) {
      this.mInfo = param1Object;
    }
    
    public static CollectionItemInfoCompat obtain(int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean) {
      return (Build.VERSION.SDK_INT >= 19) ? new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(param1Int1, param1Int2, param1Int3, param1Int4, param1Boolean)) : new CollectionItemInfoCompat(null);
    }
    
    public static CollectionItemInfoCompat obtain(int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean1, boolean param1Boolean2) {
      return (Build.VERSION.SDK_INT >= 21) ? new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(param1Int1, param1Int2, param1Int3, param1Int4, param1Boolean1, param1Boolean2)) : ((Build.VERSION.SDK_INT >= 19) ? new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(param1Int1, param1Int2, param1Int3, param1Int4, param1Boolean1)) : new CollectionItemInfoCompat(null));
    }
    
    public int getColumnIndex() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnIndex() : 0;
    }
    
    public int getColumnSpan() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnSpan() : 0;
    }
    
    public int getRowIndex() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowIndex() : 0;
    }
    
    public int getRowSpan() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowSpan() : 0;
    }
    
    @Deprecated
    public boolean isHeading() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isHeading() : false;
    }
    
    public boolean isSelected() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isSelected() : false;
    }
  }
  
  public static class RangeInfoCompat {
    public static final int RANGE_TYPE_FLOAT = 1;
    
    public static final int RANGE_TYPE_INT = 0;
    
    public static final int RANGE_TYPE_PERCENT = 2;
    
    final Object mInfo;
    
    RangeInfoCompat(Object param1Object) {
      this.mInfo = param1Object;
    }
    
    public static RangeInfoCompat obtain(int param1Int, float param1Float1, float param1Float2, float param1Float3) {
      return (Build.VERSION.SDK_INT >= 19) ? new RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(param1Int, param1Float1, param1Float2, param1Float3)) : new RangeInfoCompat(null);
    }
    
    public float getCurrent() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getCurrent() : 0.0F;
    }
    
    public float getMax() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMax() : 0.0F;
    }
    
    public float getMin() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMin() : 0.0F;
    }
    
    public int getType() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getType() : 0;
    }
  }
  
  public static final class TouchDelegateInfoCompat {
    final AccessibilityNodeInfo.TouchDelegateInfo mInfo;
    
    TouchDelegateInfoCompat(AccessibilityNodeInfo.TouchDelegateInfo param1TouchDelegateInfo) {
      this.mInfo = param1TouchDelegateInfo;
    }
    
    public TouchDelegateInfoCompat(Map<Region, View> param1Map) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mInfo = new AccessibilityNodeInfo.TouchDelegateInfo(param1Map);
      } else {
        this.mInfo = null;
      } 
    }
    
    public Region getRegionAt(int param1Int) {
      return (Build.VERSION.SDK_INT >= 29) ? this.mInfo.getRegionAt(param1Int) : null;
    }
    
    public int getRegionCount() {
      return (Build.VERSION.SDK_INT >= 29) ? this.mInfo.getRegionCount() : 0;
    }
    
    public AccessibilityNodeInfoCompat getTargetForRegion(Region param1Region) {
      if (Build.VERSION.SDK_INT >= 29) {
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo.getTargetForRegion(param1Region);
        if (accessibilityNodeInfo != null)
          return AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo); 
      } 
      return null;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\accessibility\AccessibilityNodeInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */