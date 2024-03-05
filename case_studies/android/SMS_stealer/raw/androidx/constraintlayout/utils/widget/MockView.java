package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.R;

public class MockView extends View {
  private int mDiagonalsColor = Color.argb(255, 0, 0, 0);
  
  private boolean mDrawDiagonals = true;
  
  private boolean mDrawLabel = true;
  
  private int mMargin = 4;
  
  private Paint mPaintDiagonals = new Paint();
  
  private Paint mPaintText = new Paint();
  
  private Paint mPaintTextBackground = new Paint();
  
  protected String mText = null;
  
  private int mTextBackgroundColor = Color.argb(255, 50, 50, 50);
  
  private Rect mTextBounds = new Rect();
  
  private int mTextColor = Color.argb(255, 200, 200, 200);
  
  public MockView(Context paramContext) {
    super(paramContext);
    init(paramContext, (AttributeSet)null);
  }
  
  public MockView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public MockView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    if (paramAttributeSet != null) {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MockView);
      int i = typedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        if (j == R.styleable.MockView_mock_label) {
          this.mText = typedArray.getString(j);
        } else if (j == R.styleable.MockView_mock_showDiagonals) {
          this.mDrawDiagonals = typedArray.getBoolean(j, this.mDrawDiagonals);
        } else if (j == R.styleable.MockView_mock_diagonalsColor) {
          this.mDiagonalsColor = typedArray.getColor(j, this.mDiagonalsColor);
        } else if (j == R.styleable.MockView_mock_labelBackgroundColor) {
          this.mTextBackgroundColor = typedArray.getColor(j, this.mTextBackgroundColor);
        } else if (j == R.styleable.MockView_mock_labelColor) {
          this.mTextColor = typedArray.getColor(j, this.mTextColor);
        } else if (j == R.styleable.MockView_mock_showLabel) {
          this.mDrawLabel = typedArray.getBoolean(j, this.mDrawLabel);
        } 
      } 
      typedArray.recycle();
    } 
    if (this.mText == null)
      try {
        this.mText = paramContext.getResources().getResourceEntryName(getId());
      } catch (Exception exception) {} 
    this.mPaintDiagonals.setColor(this.mDiagonalsColor);
    this.mPaintDiagonals.setAntiAlias(true);
    this.mPaintText.setColor(this.mTextColor);
    this.mPaintText.setAntiAlias(true);
    this.mPaintTextBackground.setColor(this.mTextBackgroundColor);
    this.mMargin = Math.round(this.mMargin * (getResources().getDisplayMetrics()).xdpi / 160.0F);
  }
  
  public void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    int m = getWidth();
    int k = getHeight();
    int j = m;
    int i = k;
    if (this.mDrawDiagonals) {
      j = m - 1;
      i = k - 1;
      float f2 = j;
      float f1 = i;
      paramCanvas.drawLine(0.0F, 0.0F, f2, f1, this.mPaintDiagonals);
      paramCanvas.drawLine(0.0F, f1, f2, 0.0F, this.mPaintDiagonals);
      paramCanvas.drawLine(0.0F, 0.0F, f2, 0.0F, this.mPaintDiagonals);
      paramCanvas.drawLine(f2, 0.0F, f2, f1, this.mPaintDiagonals);
      paramCanvas.drawLine(f2, f1, 0.0F, f1, this.mPaintDiagonals);
      paramCanvas.drawLine(0.0F, f1, 0.0F, 0.0F, this.mPaintDiagonals);
    } 
    String str = this.mText;
    if (str != null && this.mDrawLabel) {
      this.mPaintText.getTextBounds(str, 0, str.length(), this.mTextBounds);
      float f2 = (j - this.mTextBounds.width()) / 2.0F;
      float f1 = (i - this.mTextBounds.height()) / 2.0F + this.mTextBounds.height();
      this.mTextBounds.offset((int)f2, (int)f1);
      Rect rect = this.mTextBounds;
      rect.set(rect.left - this.mMargin, this.mTextBounds.top - this.mMargin, this.mTextBounds.right + this.mMargin, this.mTextBounds.bottom + this.mMargin);
      paramCanvas.drawRect(this.mTextBounds, this.mPaintTextBackground);
      paramCanvas.drawText(this.mText, f2, f1, this.mPaintText);
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayou\\utils\widget\MockView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */