package androidx.core.view;

import android.view.View;
import android.view.ViewParent;

public class NestedScrollingChildHelper {
  private boolean mIsNestedScrollingEnabled;
  
  private ViewParent mNestedScrollingParentNonTouch;
  
  private ViewParent mNestedScrollingParentTouch;
  
  private int[] mTempNestedScrollConsumed;
  
  private final View mView;
  
  public NestedScrollingChildHelper(View paramView) {
    this.mView = paramView;
  }
  
  private boolean dispatchNestedScrollInternal(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint1, int paramInt5, int[] paramArrayOfint2) {
    if (isNestedScrollingEnabled()) {
      ViewParent viewParent = getNestedScrollingParentForType(paramInt5);
      if (viewParent == null)
        return false; 
      if (paramInt1 != 0 || paramInt2 != 0 || paramInt3 != 0 || paramInt4 != 0) {
        byte b1;
        byte b2;
        if (paramArrayOfint1 != null) {
          this.mView.getLocationInWindow(paramArrayOfint1);
          b1 = paramArrayOfint1[0];
          b2 = paramArrayOfint1[1];
        } else {
          b1 = 0;
          b2 = 0;
        } 
        if (paramArrayOfint2 == null) {
          paramArrayOfint2 = getTempNestedScrollConsumed();
          paramArrayOfint2[0] = 0;
          paramArrayOfint2[1] = 0;
        } 
        ViewParentCompat.onNestedScroll(viewParent, this.mView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfint2);
        if (paramArrayOfint1 != null) {
          this.mView.getLocationInWindow(paramArrayOfint1);
          paramArrayOfint1[0] = paramArrayOfint1[0] - b1;
          paramArrayOfint1[1] = paramArrayOfint1[1] - b2;
        } 
        return true;
      } 
      if (paramArrayOfint1 != null) {
        paramArrayOfint1[0] = 0;
        paramArrayOfint1[1] = 0;
      } 
    } 
    return false;
  }
  
  private ViewParent getNestedScrollingParentForType(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? null : this.mNestedScrollingParentNonTouch) : this.mNestedScrollingParentTouch;
  }
  
  private int[] getTempNestedScrollConsumed() {
    if (this.mTempNestedScrollConsumed == null)
      this.mTempNestedScrollConsumed = new int[2]; 
    return this.mTempNestedScrollConsumed;
  }
  
  private void setNestedScrollingParentForType(int paramInt, ViewParent paramViewParent) {
    if (paramInt != 0) {
      if (paramInt == 1)
        this.mNestedScrollingParentNonTouch = paramViewParent; 
    } else {
      this.mNestedScrollingParentTouch = paramViewParent;
    } 
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    if (isNestedScrollingEnabled()) {
      ViewParent viewParent = getNestedScrollingParentForType(0);
      if (viewParent != null)
        return ViewParentCompat.onNestedFling(viewParent, this.mView, paramFloat1, paramFloat2, paramBoolean); 
    } 
    return false;
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2) {
    if (isNestedScrollingEnabled()) {
      ViewParent viewParent = getNestedScrollingParentForType(0);
      if (viewParent != null)
        return ViewParentCompat.onNestedPreFling(viewParent, this.mView, paramFloat1, paramFloat2); 
    } 
    return false;
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    return dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2, 0);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt3) {
    boolean bool1 = isNestedScrollingEnabled();
    boolean bool = false;
    null = bool;
    if (bool1) {
      ViewParent viewParent = getNestedScrollingParentForType(paramInt3);
      if (viewParent == null)
        return false; 
      if (paramInt1 != 0 || paramInt2 != 0) {
        byte b1;
        byte b2;
        if (paramArrayOfint2 != null) {
          this.mView.getLocationInWindow(paramArrayOfint2);
          b1 = paramArrayOfint2[0];
          b2 = paramArrayOfint2[1];
        } else {
          b1 = 0;
          b2 = 0;
        } 
        int[] arrayOfInt = paramArrayOfint1;
        if (paramArrayOfint1 == null)
          arrayOfInt = getTempNestedScrollConsumed(); 
        arrayOfInt[0] = 0;
        arrayOfInt[1] = 0;
        ViewParentCompat.onNestedPreScroll(viewParent, this.mView, paramInt1, paramInt2, arrayOfInt, paramInt3);
        if (paramArrayOfint2 != null) {
          this.mView.getLocationInWindow(paramArrayOfint2);
          paramArrayOfint2[0] = paramArrayOfint2[0] - b1;
          paramArrayOfint2[1] = paramArrayOfint2[1] - b2;
        } 
        if (arrayOfInt[0] == 0) {
          null = bool;
          return (arrayOfInt[1] != 0) ? true : null;
        } 
      } else {
        null = bool;
        if (paramArrayOfint2 != null) {
          paramArrayOfint2[0] = 0;
          paramArrayOfint2[1] = 0;
          null = bool;
        } 
        return null;
      } 
    } else {
      return null;
    } 
    return true;
  }
  
  public void dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint1, int paramInt5, int[] paramArrayOfint2) {
    dispatchNestedScrollInternal(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint1, paramInt5, paramArrayOfint2);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    return dispatchNestedScrollInternal(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, 0, null);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5) {
    return dispatchNestedScrollInternal(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, paramInt5, null);
  }
  
  public boolean hasNestedScrollingParent() {
    return hasNestedScrollingParent(0);
  }
  
  public boolean hasNestedScrollingParent(int paramInt) {
    boolean bool;
    if (getNestedScrollingParentForType(paramInt) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isNestedScrollingEnabled() {
    return this.mIsNestedScrollingEnabled;
  }
  
  public void onDetachedFromWindow() {
    ViewCompat.stopNestedScroll(this.mView);
  }
  
  public void onStopNestedScroll(View paramView) {
    ViewCompat.stopNestedScroll(this.mView);
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean) {
    if (this.mIsNestedScrollingEnabled)
      ViewCompat.stopNestedScroll(this.mView); 
    this.mIsNestedScrollingEnabled = paramBoolean;
  }
  
  public boolean startNestedScroll(int paramInt) {
    return startNestedScroll(paramInt, 0);
  }
  
  public boolean startNestedScroll(int paramInt1, int paramInt2) {
    if (hasNestedScrollingParent(paramInt2))
      return true; 
    if (isNestedScrollingEnabled()) {
      ViewParent viewParent = this.mView.getParent();
      View view = this.mView;
      while (viewParent != null) {
        if (ViewParentCompat.onStartNestedScroll(viewParent, view, this.mView, paramInt1, paramInt2)) {
          setNestedScrollingParentForType(paramInt2, viewParent);
          ViewParentCompat.onNestedScrollAccepted(viewParent, view, this.mView, paramInt1, paramInt2);
          return true;
        } 
        if (viewParent instanceof View)
          view = (View)viewParent; 
        viewParent = viewParent.getParent();
      } 
    } 
    return false;
  }
  
  public void stopNestedScroll() {
    stopNestedScroll(0);
  }
  
  public void stopNestedScroll(int paramInt) {
    ViewParent viewParent = getNestedScrollingParentForType(paramInt);
    if (viewParent != null) {
      ViewParentCompat.onStopNestedScroll(viewParent, this.mView, paramInt);
      setNestedScrollingParentForType(paramInt, null);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\NestedScrollingChildHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */