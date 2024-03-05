package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

public class AlertDialogLayout extends LinearLayoutCompat {
  public AlertDialogLayout(Context paramContext) {
    super(paramContext);
  }
  
  public AlertDialogLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  private void forceUniformWidth(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    for (byte b = 0; b < paramInt1; b++) {
      View view = getChildAt(b);
      if (view.getVisibility() != 8) {
        LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)view.getLayoutParams();
        if (layoutParams.width == -1) {
          int j = layoutParams.height;
          layoutParams.height = view.getMeasuredHeight();
          measureChildWithMargins(view, i, 0, paramInt2, 0);
          layoutParams.height = j;
        } 
      } 
    } 
  }
  
  private static int resolveMinimumHeight(View paramView) {
    int i = ViewCompat.getMinimumHeight(paramView);
    if (i > 0)
      return i; 
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      if (viewGroup.getChildCount() == 1)
        return resolveMinimumHeight(viewGroup.getChildAt(0)); 
    } 
    return 0;
  }
  
  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramView.layout(paramInt1, paramInt2, paramInt3 + paramInt1, paramInt4 + paramInt2);
  }
  
  private boolean tryOnMeasure(int paramInt1, int paramInt2) {
    int i1;
    byte b;
    int i4 = getChildCount();
    View view3 = null;
    View view2 = null;
    View view1 = null;
    int i;
    for (i = 0; i < i4; i++) {
      View view = getChildAt(i);
      if (view.getVisibility() != 8) {
        j = view.getId();
        if (j == R.id.topPanel) {
          view3 = view;
        } else if (j == R.id.buttonPanel) {
          view2 = view;
        } else if (j == R.id.contentPanel || j == R.id.customPanel) {
          if (view1 != null)
            return false; 
          view1 = view;
        } else {
          return false;
        } 
      } 
    } 
    int i6 = View.MeasureSpec.getMode(paramInt2);
    int n = View.MeasureSpec.getSize(paramInt2);
    int i5 = View.MeasureSpec.getMode(paramInt1);
    int k = getPaddingTop() + getPaddingBottom();
    if (view3 != null) {
      view3.measure(paramInt1, 0);
      k += view3.getMeasuredHeight();
      j = View.combineMeasuredStates(0, view3.getMeasuredState());
    } else {
      j = 0;
    } 
    if (view2 != null) {
      view2.measure(paramInt1, 0);
      i = resolveMinimumHeight(view2);
      i1 = view2.getMeasuredHeight() - i;
      k += i;
      j = View.combineMeasuredStates(j, view2.getMeasuredState());
    } else {
      i = 0;
      i1 = 0;
    } 
    if (view1 != null) {
      int i7;
      if (i6 == 0) {
        i7 = 0;
      } else {
        i7 = View.MeasureSpec.makeMeasureSpec(Math.max(0, n - k), i6);
      } 
      view1.measure(paramInt1, i7);
      b = view1.getMeasuredHeight();
      k += b;
      j = View.combineMeasuredStates(j, view1.getMeasuredState());
    } else {
      b = 0;
    } 
    int i2 = n - k;
    n = j;
    int i3 = i2;
    int m = k;
    if (view2 != null) {
      i1 = Math.min(i2, i1);
      n = i2;
      m = i;
      if (i1 > 0) {
        n = i2 - i1;
        m = i + i1;
      } 
      view2.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(m, 1073741824));
      m = k - i + view2.getMeasuredHeight();
      i = View.combineMeasuredStates(j, view2.getMeasuredState());
      i3 = n;
      n = i;
    } 
    int j = n;
    i = m;
    if (view1 != null) {
      j = n;
      i = m;
      if (i3 > 0) {
        view1.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(b + i3, i6));
        i = m - b + view1.getMeasuredHeight();
        j = View.combineMeasuredStates(n, view1.getMeasuredState());
      } 
    } 
    n = 0;
    for (m = 0; n < i4; m = k) {
      View view = getChildAt(n);
      k = m;
      if (view.getVisibility() != 8)
        k = Math.max(m, view.getMeasuredWidth()); 
      n++;
    } 
    setMeasuredDimension(View.resolveSizeAndState(m + getPaddingLeft() + getPaddingRight(), paramInt1, j), View.resolveSizeAndState(i, paramInt2, 0));
    if (i5 != 1073741824)
      forceUniformWidth(i4, paramInt2); 
    return true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = getPaddingLeft();
    int m = paramInt3 - paramInt1;
    int j = getPaddingRight();
    int i1 = getPaddingRight();
    paramInt3 = getMeasuredHeight();
    int n = getChildCount();
    int k = getGravity();
    paramInt1 = k & 0x70;
    if (paramInt1 != 16) {
      if (paramInt1 != 80) {
        paramInt1 = getPaddingTop();
      } else {
        paramInt1 = getPaddingTop() + paramInt4 - paramInt2 - paramInt3;
      } 
    } else {
      paramInt1 = getPaddingTop() + (paramInt4 - paramInt2 - paramInt3) / 2;
    } 
    Drawable drawable = getDividerDrawable();
    if (drawable == null) {
      paramInt2 = 0;
    } else {
      paramInt2 = drawable.getIntrinsicHeight();
    } 
    paramInt3 = 0;
    while (paramInt3 < n) {
      View view = getChildAt(paramInt3);
      paramInt4 = paramInt1;
      if (view != null) {
        paramInt4 = paramInt1;
        if (view.getVisibility() != 8) {
          int i3 = view.getMeasuredWidth();
          int i4 = view.getMeasuredHeight();
          LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)view.getLayoutParams();
          int i2 = layoutParams.gravity;
          paramInt4 = i2;
          if (i2 < 0)
            paramInt4 = k & 0x800007; 
          paramInt4 = GravityCompat.getAbsoluteGravity(paramInt4, ViewCompat.getLayoutDirection((View)this)) & 0x7;
          if (paramInt4 != 1) {
            if (paramInt4 != 5) {
              paramInt4 = layoutParams.leftMargin + i;
            } else {
              i2 = m - j - i3;
              paramInt4 = layoutParams.rightMargin;
              paramInt4 = i2 - paramInt4;
            } 
          } else {
            i2 = (m - i - i1 - i3) / 2 + i + layoutParams.leftMargin;
            paramInt4 = layoutParams.rightMargin;
            paramInt4 = i2 - paramInt4;
          } 
          i2 = paramInt1;
          if (hasDividerBeforeChildAt(paramInt3))
            i2 = paramInt1 + paramInt2; 
          paramInt1 = i2 + layoutParams.topMargin;
          setChildFrame(view, paramInt4, paramInt1, i3, i4);
          paramInt4 = paramInt1 + i4 + layoutParams.bottomMargin;
        } 
      } 
      paramInt3++;
      paramInt1 = paramInt4;
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    if (!tryOnMeasure(paramInt1, paramInt2))
      super.onMeasure(paramInt1, paramInt2); 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AlertDialogLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */