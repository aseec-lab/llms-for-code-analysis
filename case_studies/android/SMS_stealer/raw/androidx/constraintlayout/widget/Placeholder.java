package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public class Placeholder extends View {
  private View mContent = null;
  
  private int mContentId = -1;
  
  private int mEmptyVisibility = 4;
  
  public Placeholder(Context paramContext) {
    super(paramContext);
    init((AttributeSet)null);
  }
  
  public Placeholder(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }
  
  public Placeholder(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }
  
  public Placeholder(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1);
    init(paramAttributeSet);
  }
  
  private void init(AttributeSet paramAttributeSet) {
    setVisibility(this.mEmptyVisibility);
    this.mContentId = -1;
    if (paramAttributeSet != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_placeholder);
      int i = typedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        if (j == R.styleable.ConstraintLayout_placeholder_content) {
          this.mContentId = typedArray.getResourceId(j, this.mContentId);
        } else if (j == R.styleable.ConstraintLayout_placeholder_placeholder_emptyVisibility) {
          this.mEmptyVisibility = typedArray.getInt(j, this.mEmptyVisibility);
        } 
      } 
      typedArray.recycle();
    } 
  }
  
  public View getContent() {
    return this.mContent;
  }
  
  public int getEmptyVisibility() {
    return this.mEmptyVisibility;
  }
  
  public void onDraw(Canvas paramCanvas) {
    if (isInEditMode()) {
      paramCanvas.drawRGB(223, 223, 223);
      Paint paint = new Paint();
      paint.setARGB(255, 210, 210, 210);
      paint.setTextAlign(Paint.Align.CENTER);
      paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
      Rect rect = new Rect();
      paramCanvas.getClipBounds(rect);
      paint.setTextSize(rect.height());
      int i = rect.height();
      int j = rect.width();
      paint.setTextAlign(Paint.Align.LEFT);
      paint.getTextBounds("?", 0, 1, rect);
      paramCanvas.drawText("?", j / 2.0F - rect.width() / 2.0F - rect.left, i / 2.0F + rect.height() / 2.0F - rect.bottom, paint);
    } 
  }
  
  public void setContentId(int paramInt) {
    if (this.mContentId == paramInt)
      return; 
    View view = this.mContent;
    if (view != null) {
      view.setVisibility(0);
      ((ConstraintLayout.LayoutParams)this.mContent.getLayoutParams()).isInPlaceholder = false;
      this.mContent = null;
    } 
    this.mContentId = paramInt;
    if (paramInt != -1) {
      view = ((View)getParent()).findViewById(paramInt);
      if (view != null)
        view.setVisibility(8); 
    } 
  }
  
  public void setEmptyVisibility(int paramInt) {
    this.mEmptyVisibility = paramInt;
  }
  
  public void updatePostMeasure(ConstraintLayout paramConstraintLayout) {
    if (this.mContent == null)
      return; 
    ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams)getLayoutParams();
    ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams)this.mContent.getLayoutParams();
    layoutParams1.widget.setVisibility(0);
    if (layoutParams2.widget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED)
      layoutParams2.widget.setWidth(layoutParams1.widget.getWidth()); 
    if (layoutParams2.widget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED)
      layoutParams2.widget.setHeight(layoutParams1.widget.getHeight()); 
    layoutParams1.widget.setVisibility(8);
  }
  
  public void updatePreLayout(ConstraintLayout paramConstraintLayout) {
    if (this.mContentId == -1 && !isInEditMode())
      setVisibility(this.mEmptyVisibility); 
    View view = paramConstraintLayout.findViewById(this.mContentId);
    this.mContent = view;
    if (view != null) {
      ((ConstraintLayout.LayoutParams)view.getLayoutParams()).isInPlaceholder = true;
      this.mContent.setVisibility(0);
      setVisibility(0);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\widget\Placeholder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */