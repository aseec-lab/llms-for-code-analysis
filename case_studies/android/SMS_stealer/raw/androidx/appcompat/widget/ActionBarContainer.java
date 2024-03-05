package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;

public class ActionBarContainer extends FrameLayout {
  private View mActionBarView;
  
  Drawable mBackground;
  
  private View mContextView;
  
  private int mHeight;
  
  boolean mIsSplit;
  
  boolean mIsStacked;
  
  private boolean mIsTransitioning;
  
  Drawable mSplitBackground;
  
  Drawable mStackedBackground;
  
  private View mTabContainer;
  
  public ActionBarContainer(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ActionBarContainer(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    ViewCompat.setBackground((View)this, new ActionBarBackgroundDrawable(this));
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ActionBar);
    this.mBackground = typedArray.getDrawable(R.styleable.ActionBar_background);
    this.mStackedBackground = typedArray.getDrawable(R.styleable.ActionBar_backgroundStacked);
    this.mHeight = typedArray.getDimensionPixelSize(R.styleable.ActionBar_height, -1);
    int i = getId();
    int j = R.id.split_action_bar;
    boolean bool = true;
    if (i == j) {
      this.mIsSplit = true;
      this.mSplitBackground = typedArray.getDrawable(R.styleable.ActionBar_backgroundSplit);
    } 
    typedArray.recycle();
    if (this.mIsSplit ? (this.mSplitBackground == null) : (this.mBackground == null && this.mStackedBackground == null))
      bool = false; 
    setWillNotDraw(bool);
  }
  
  private int getMeasuredHeightWithMargins(View paramView) {
    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)paramView.getLayoutParams();
    return paramView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
  }
  
  private boolean isCollapsed(View paramView) {
    return (paramView == null || paramView.getVisibility() == 8 || paramView.getMeasuredHeight() == 0);
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    Drawable drawable = this.mBackground;
    if (drawable != null && drawable.isStateful())
      this.mBackground.setState(getDrawableState()); 
    drawable = this.mStackedBackground;
    if (drawable != null && drawable.isStateful())
      this.mStackedBackground.setState(getDrawableState()); 
    drawable = this.mSplitBackground;
    if (drawable != null && drawable.isStateful())
      this.mSplitBackground.setState(getDrawableState()); 
  }
  
  public View getTabContainer() {
    return this.mTabContainer;
  }
  
  public void jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState();
    Drawable drawable = this.mBackground;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
    drawable = this.mStackedBackground;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
    drawable = this.mSplitBackground;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
  }
  
  public void onFinishInflate() {
    super.onFinishInflate();
    this.mActionBarView = findViewById(R.id.action_bar);
    this.mContextView = findViewById(R.id.action_context_bar);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent) {
    super.onHoverEvent(paramMotionEvent);
    return true;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    return (this.mIsTransitioning || super.onInterceptTouchEvent(paramMotionEvent));
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Drawable drawable;
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    View view = this.mTabContainer;
    paramInt2 = 1;
    paramInt4 = 0;
    if (view != null && view.getVisibility() != 8) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    if (view != null && view.getVisibility() != 8) {
      int i = getMeasuredHeight();
      FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
      view.layout(paramInt1, i - view.getMeasuredHeight() - layoutParams.bottomMargin, paramInt3, i - layoutParams.bottomMargin);
    } 
    if (this.mIsSplit) {
      drawable = this.mSplitBackground;
      if (drawable != null) {
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        paramInt1 = paramInt2;
      } else {
        paramInt1 = 0;
      } 
    } else {
      paramInt1 = paramInt4;
      if (this.mBackground != null) {
        if (this.mActionBarView.getVisibility() == 0) {
          this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
        } else {
          View view1 = this.mContextView;
          if (view1 != null && view1.getVisibility() == 0) {
            this.mBackground.setBounds(this.mContextView.getLeft(), this.mContextView.getTop(), this.mContextView.getRight(), this.mContextView.getBottom());
          } else {
            this.mBackground.setBounds(0, 0, 0, 0);
          } 
        } 
        paramInt1 = 1;
      } 
      this.mIsStacked = paramBoolean;
      if (paramBoolean) {
        Drawable drawable1 = this.mStackedBackground;
        if (drawable1 != null) {
          drawable1.setBounds(drawable.getLeft(), drawable.getTop(), drawable.getRight(), drawable.getBottom());
          paramInt1 = paramInt2;
        } 
      } 
    } 
    if (paramInt1 != 0)
      invalidate(); 
  }
  
  public void onMeasure(int paramInt1, int paramInt2) {
    int i = paramInt2;
    if (this.mActionBarView == null) {
      i = paramInt2;
      if (View.MeasureSpec.getMode(paramInt2) == Integer.MIN_VALUE) {
        int j = this.mHeight;
        i = paramInt2;
        if (j >= 0)
          i = View.MeasureSpec.makeMeasureSpec(Math.min(j, View.MeasureSpec.getSize(paramInt2)), -2147483648); 
      } 
    } 
    super.onMeasure(paramInt1, i);
    if (this.mActionBarView == null)
      return; 
    paramInt2 = View.MeasureSpec.getMode(i);
    View view = this.mTabContainer;
    if (view != null && view.getVisibility() != 8 && paramInt2 != 1073741824) {
      if (!isCollapsed(this.mActionBarView)) {
        paramInt1 = getMeasuredHeightWithMargins(this.mActionBarView);
      } else if (!isCollapsed(this.mContextView)) {
        paramInt1 = getMeasuredHeightWithMargins(this.mContextView);
      } else {
        paramInt1 = 0;
      } 
      if (paramInt2 == Integer.MIN_VALUE) {
        paramInt2 = View.MeasureSpec.getSize(i);
      } else {
        paramInt2 = Integer.MAX_VALUE;
      } 
      setMeasuredDimension(getMeasuredWidth(), Math.min(paramInt1 + getMeasuredHeightWithMargins(this.mTabContainer), paramInt2));
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    super.onTouchEvent(paramMotionEvent);
    return true;
  }
  
  public void setPrimaryBackground(Drawable paramDrawable) {
    Drawable drawable = this.mBackground;
    if (drawable != null) {
      drawable.setCallback(null);
      unscheduleDrawable(this.mBackground);
    } 
    this.mBackground = paramDrawable;
    if (paramDrawable != null) {
      paramDrawable.setCallback((Drawable.Callback)this);
      View view = this.mActionBarView;
      if (view != null)
        this.mBackground.setBounds(view.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom()); 
    } 
    boolean bool1 = this.mIsSplit;
    boolean bool = true;
    if (bool1 ? (this.mSplitBackground == null) : (this.mBackground == null && this.mStackedBackground == null))
      bool = false; 
    setWillNotDraw(bool);
    invalidate();
    if (Build.VERSION.SDK_INT >= 21)
      invalidateOutline(); 
  }
  
  public void setSplitBackground(Drawable paramDrawable) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mSplitBackground : Landroid/graphics/drawable/Drawable;
    //   4: astore #4
    //   6: aload #4
    //   8: ifnull -> 25
    //   11: aload #4
    //   13: aconst_null
    //   14: invokevirtual setCallback : (Landroid/graphics/drawable/Drawable$Callback;)V
    //   17: aload_0
    //   18: aload_0
    //   19: getfield mSplitBackground : Landroid/graphics/drawable/Drawable;
    //   22: invokevirtual unscheduleDrawable : (Landroid/graphics/drawable/Drawable;)V
    //   25: aload_0
    //   26: aload_1
    //   27: putfield mSplitBackground : Landroid/graphics/drawable/Drawable;
    //   30: iconst_0
    //   31: istore_3
    //   32: aload_1
    //   33: ifnull -> 71
    //   36: aload_1
    //   37: aload_0
    //   38: invokevirtual setCallback : (Landroid/graphics/drawable/Drawable$Callback;)V
    //   41: aload_0
    //   42: getfield mIsSplit : Z
    //   45: ifeq -> 71
    //   48: aload_0
    //   49: getfield mSplitBackground : Landroid/graphics/drawable/Drawable;
    //   52: astore_1
    //   53: aload_1
    //   54: ifnull -> 71
    //   57: aload_1
    //   58: iconst_0
    //   59: iconst_0
    //   60: aload_0
    //   61: invokevirtual getMeasuredWidth : ()I
    //   64: aload_0
    //   65: invokevirtual getMeasuredHeight : ()I
    //   68: invokevirtual setBounds : (IIII)V
    //   71: aload_0
    //   72: getfield mIsSplit : Z
    //   75: ifeq -> 92
    //   78: iload_3
    //   79: istore_2
    //   80: aload_0
    //   81: getfield mSplitBackground : Landroid/graphics/drawable/Drawable;
    //   84: ifnonnull -> 113
    //   87: iconst_1
    //   88: istore_2
    //   89: goto -> 113
    //   92: iload_3
    //   93: istore_2
    //   94: aload_0
    //   95: getfield mBackground : Landroid/graphics/drawable/Drawable;
    //   98: ifnonnull -> 113
    //   101: iload_3
    //   102: istore_2
    //   103: aload_0
    //   104: getfield mStackedBackground : Landroid/graphics/drawable/Drawable;
    //   107: ifnonnull -> 113
    //   110: goto -> 87
    //   113: aload_0
    //   114: iload_2
    //   115: invokevirtual setWillNotDraw : (Z)V
    //   118: aload_0
    //   119: invokevirtual invalidate : ()V
    //   122: getstatic android/os/Build$VERSION.SDK_INT : I
    //   125: bipush #21
    //   127: if_icmplt -> 134
    //   130: aload_0
    //   131: invokevirtual invalidateOutline : ()V
    //   134: return
  }
  
  public void setStackedBackground(Drawable paramDrawable) {
    Drawable drawable = this.mStackedBackground;
    if (drawable != null) {
      drawable.setCallback(null);
      unscheduleDrawable(this.mStackedBackground);
    } 
    this.mStackedBackground = paramDrawable;
    if (paramDrawable != null) {
      paramDrawable.setCallback((Drawable.Callback)this);
      if (this.mIsStacked) {
        paramDrawable = this.mStackedBackground;
        if (paramDrawable != null)
          paramDrawable.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom()); 
      } 
    } 
    boolean bool1 = this.mIsSplit;
    boolean bool = true;
    if (bool1 ? (this.mSplitBackground == null) : (this.mBackground == null && this.mStackedBackground == null))
      bool = false; 
    setWillNotDraw(bool);
    invalidate();
    if (Build.VERSION.SDK_INT >= 21)
      invalidateOutline(); 
  }
  
  public void setTabContainer(ScrollingTabContainerView paramScrollingTabContainerView) {
    View view = this.mTabContainer;
    if (view != null)
      removeView(view); 
    this.mTabContainer = (View)paramScrollingTabContainerView;
    if (paramScrollingTabContainerView != null) {
      addView((View)paramScrollingTabContainerView);
      ViewGroup.LayoutParams layoutParams = paramScrollingTabContainerView.getLayoutParams();
      layoutParams.width = -1;
      layoutParams.height = -2;
      paramScrollingTabContainerView.setAllowCollapse(false);
    } 
  }
  
  public void setTransitioning(boolean paramBoolean) {
    int i;
    this.mIsTransitioning = paramBoolean;
    if (paramBoolean) {
      i = 393216;
    } else {
      i = 262144;
    } 
    setDescendantFocusability(i);
  }
  
  public void setVisibility(int paramInt) {
    boolean bool;
    super.setVisibility(paramInt);
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    Drawable drawable = this.mBackground;
    if (drawable != null)
      drawable.setVisible(bool, false); 
    drawable = this.mStackedBackground;
    if (drawable != null)
      drawable.setVisible(bool, false); 
    drawable = this.mSplitBackground;
    if (drawable != null)
      drawable.setVisible(bool, false); 
  }
  
  public ActionMode startActionModeForChild(View paramView, ActionMode.Callback paramCallback) {
    return null;
  }
  
  public ActionMode startActionModeForChild(View paramView, ActionMode.Callback paramCallback, int paramInt) {
    return (paramInt != 0) ? super.startActionModeForChild(paramView, paramCallback, paramInt) : null;
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    boolean bool;
    if ((paramDrawable == this.mBackground && !this.mIsSplit) || (paramDrawable == this.mStackedBackground && this.mIsStacked) || (paramDrawable == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(paramDrawable)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\ActionBarContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */