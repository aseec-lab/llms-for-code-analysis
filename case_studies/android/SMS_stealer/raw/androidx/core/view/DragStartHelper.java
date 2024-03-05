package androidx.core.view;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class DragStartHelper {
  private boolean mDragging;
  
  private int mLastTouchX;
  
  private int mLastTouchY;
  
  private final OnDragStartListener mListener;
  
  private final View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {
      final DragStartHelper this$0;
      
      public boolean onLongClick(View param1View) {
        return DragStartHelper.this.onLongClick(param1View);
      }
    };
  
  private final View.OnTouchListener mTouchListener = new View.OnTouchListener() {
      final DragStartHelper this$0;
      
      public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
        return DragStartHelper.this.onTouch(param1View, param1MotionEvent);
      }
    };
  
  private final View mView;
  
  public DragStartHelper(View paramView, OnDragStartListener paramOnDragStartListener) {
    this.mView = paramView;
    this.mListener = paramOnDragStartListener;
  }
  
  public void attach() {
    this.mView.setOnLongClickListener(this.mLongClickListener);
    this.mView.setOnTouchListener(this.mTouchListener);
  }
  
  public void detach() {
    this.mView.setOnLongClickListener(null);
    this.mView.setOnTouchListener(null);
  }
  
  public void getTouchPosition(Point paramPoint) {
    paramPoint.set(this.mLastTouchX, this.mLastTouchY);
  }
  
  public boolean onLongClick(View paramView) {
    return this.mListener.onDragStart(paramView, this);
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    int k = (int)paramMotionEvent.getX();
    int i = (int)paramMotionEvent.getY();
    int j = paramMotionEvent.getAction();
    if (j != 0) {
      if (j != 1)
        if (j != 2) {
          if (j != 3)
            return false; 
        } else {
          if (MotionEventCompat.isFromSource(paramMotionEvent, 8194) && (paramMotionEvent.getButtonState() & 0x1) != 0 && !this.mDragging && (this.mLastTouchX != k || this.mLastTouchY != i)) {
            this.mLastTouchX = k;
            this.mLastTouchY = i;
            boolean bool = this.mListener.onDragStart(paramView, this);
            this.mDragging = bool;
            return bool;
          } 
          return false;
        }  
      this.mDragging = false;
    } else {
      this.mLastTouchX = k;
      this.mLastTouchY = i;
    } 
    return false;
  }
  
  public static interface OnDragStartListener {
    boolean onDragStart(View param1View, DragStartHelper param1DragStartHelper);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\DragStartHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */