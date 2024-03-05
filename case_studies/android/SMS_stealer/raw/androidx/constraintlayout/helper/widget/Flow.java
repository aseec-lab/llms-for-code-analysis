package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.VirtualLayout;

public class Flow extends VirtualLayout {
  public static final int CHAIN_PACKED = 2;
  
  public static final int CHAIN_SPREAD = 0;
  
  public static final int CHAIN_SPREAD_INSIDE = 1;
  
  public static final int HORIZONTAL = 0;
  
  public static final int HORIZONTAL_ALIGN_CENTER = 2;
  
  public static final int HORIZONTAL_ALIGN_END = 1;
  
  public static final int HORIZONTAL_ALIGN_START = 0;
  
  private static final String TAG = "Flow";
  
  public static final int VERTICAL = 1;
  
  public static final int VERTICAL_ALIGN_BASELINE = 3;
  
  public static final int VERTICAL_ALIGN_BOTTOM = 1;
  
  public static final int VERTICAL_ALIGN_CENTER = 2;
  
  public static final int VERTICAL_ALIGN_TOP = 0;
  
  public static final int WRAP_ALIGNED = 2;
  
  public static final int WRAP_CHAIN = 1;
  
  public static final int WRAP_NONE = 0;
  
  private androidx.constraintlayout.solver.widgets.Flow mFlow;
  
  public Flow(Context paramContext) {
    super(paramContext);
  }
  
  public Flow(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public Flow(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void init(AttributeSet paramAttributeSet) {
    super.init(paramAttributeSet);
    this.mFlow = new androidx.constraintlayout.solver.widgets.Flow();
    if (paramAttributeSet != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int i = typedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        if (j == R.styleable.ConstraintLayout_Layout_android_orientation) {
          this.mFlow.setOrientation(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_android_padding) {
          this.mFlow.setPadding(typedArray.getDimensionPixelSize(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_android_paddingStart) {
          if (Build.VERSION.SDK_INT >= 17)
            this.mFlow.setPaddingStart(typedArray.getDimensionPixelSize(j, 0)); 
        } else if (j == R.styleable.ConstraintLayout_Layout_android_paddingEnd) {
          if (Build.VERSION.SDK_INT >= 17)
            this.mFlow.setPaddingEnd(typedArray.getDimensionPixelSize(j, 0)); 
        } else if (j == R.styleable.ConstraintLayout_Layout_android_paddingLeft) {
          this.mFlow.setPaddingLeft(typedArray.getDimensionPixelSize(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_android_paddingTop) {
          this.mFlow.setPaddingTop(typedArray.getDimensionPixelSize(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_android_paddingRight) {
          this.mFlow.setPaddingRight(typedArray.getDimensionPixelSize(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_android_paddingBottom) {
          this.mFlow.setPaddingBottom(typedArray.getDimensionPixelSize(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_wrapMode) {
          this.mFlow.setWrapMode(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_horizontalStyle) {
          this.mFlow.setHorizontalStyle(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_verticalStyle) {
          this.mFlow.setVerticalStyle(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_firstHorizontalStyle) {
          this.mFlow.setFirstHorizontalStyle(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_lastHorizontalStyle) {
          this.mFlow.setLastHorizontalStyle(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_firstVerticalStyle) {
          this.mFlow.setFirstVerticalStyle(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_lastVerticalStyle) {
          this.mFlow.setLastVerticalStyle(typedArray.getInt(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_horizontalBias) {
          this.mFlow.setHorizontalBias(typedArray.getFloat(j, 0.5F));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_firstHorizontalBias) {
          this.mFlow.setFirstHorizontalBias(typedArray.getFloat(j, 0.5F));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_lastHorizontalBias) {
          this.mFlow.setLastHorizontalBias(typedArray.getFloat(j, 0.5F));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_firstVerticalBias) {
          this.mFlow.setFirstVerticalBias(typedArray.getFloat(j, 0.5F));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_lastVerticalBias) {
          this.mFlow.setLastVerticalBias(typedArray.getFloat(j, 0.5F));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_verticalBias) {
          this.mFlow.setVerticalBias(typedArray.getFloat(j, 0.5F));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_horizontalAlign) {
          this.mFlow.setHorizontalAlign(typedArray.getInt(j, 2));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_verticalAlign) {
          this.mFlow.setVerticalAlign(typedArray.getInt(j, 2));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_horizontalGap) {
          this.mFlow.setHorizontalGap(typedArray.getDimensionPixelSize(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_verticalGap) {
          this.mFlow.setVerticalGap(typedArray.getDimensionPixelSize(j, 0));
        } else if (j == R.styleable.ConstraintLayout_Layout_flow_maxElementsWrap) {
          this.mFlow.setMaxElementsWrap(typedArray.getInt(j, -1));
        } 
      } 
      typedArray.recycle();
    } 
    this.mHelperWidget = (Helper)this.mFlow;
    validateParams();
  }
  
  public void loadParameters(ConstraintSet.Constraint paramConstraint, HelperWidget paramHelperWidget, ConstraintLayout.LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray) {
    super.loadParameters(paramConstraint, paramHelperWidget, paramLayoutParams, paramSparseArray);
    if (paramHelperWidget instanceof androidx.constraintlayout.solver.widgets.Flow) {
      androidx.constraintlayout.solver.widgets.Flow flow = (androidx.constraintlayout.solver.widgets.Flow)paramHelperWidget;
      if (paramLayoutParams.orientation != -1)
        flow.setOrientation(paramLayoutParams.orientation); 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    onMeasure((VirtualLayout)this.mFlow, paramInt1, paramInt2);
  }
  
  public void onMeasure(VirtualLayout paramVirtualLayout, int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.getMode(paramInt1);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    if (paramVirtualLayout != null) {
      paramVirtualLayout.measure(i, paramInt1, j, paramInt2);
      setMeasuredDimension(paramVirtualLayout.getMeasuredWidth(), paramVirtualLayout.getMeasuredHeight());
    } else {
      setMeasuredDimension(0, 0);
    } 
  }
  
  public void resolveRtl(ConstraintWidget paramConstraintWidget, boolean paramBoolean) {
    this.mFlow.applyRtl(paramBoolean);
  }
  
  public void setFirstHorizontalBias(float paramFloat) {
    this.mFlow.setFirstHorizontalBias(paramFloat);
    requestLayout();
  }
  
  public void setFirstHorizontalStyle(int paramInt) {
    this.mFlow.setFirstHorizontalStyle(paramInt);
    requestLayout();
  }
  
  public void setFirstVerticalBias(float paramFloat) {
    this.mFlow.setFirstVerticalBias(paramFloat);
    requestLayout();
  }
  
  public void setFirstVerticalStyle(int paramInt) {
    this.mFlow.setFirstVerticalStyle(paramInt);
    requestLayout();
  }
  
  public void setHorizontalAlign(int paramInt) {
    this.mFlow.setHorizontalAlign(paramInt);
    requestLayout();
  }
  
  public void setHorizontalBias(float paramFloat) {
    this.mFlow.setHorizontalBias(paramFloat);
    requestLayout();
  }
  
  public void setHorizontalGap(int paramInt) {
    this.mFlow.setHorizontalGap(paramInt);
    requestLayout();
  }
  
  public void setHorizontalStyle(int paramInt) {
    this.mFlow.setHorizontalStyle(paramInt);
    requestLayout();
  }
  
  public void setMaxElementsWrap(int paramInt) {
    this.mFlow.setMaxElementsWrap(paramInt);
    requestLayout();
  }
  
  public void setOrientation(int paramInt) {
    this.mFlow.setOrientation(paramInt);
    requestLayout();
  }
  
  public void setPadding(int paramInt) {
    this.mFlow.setPadding(paramInt);
    requestLayout();
  }
  
  public void setPaddingBottom(int paramInt) {
    this.mFlow.setPaddingBottom(paramInt);
    requestLayout();
  }
  
  public void setPaddingLeft(int paramInt) {
    this.mFlow.setPaddingLeft(paramInt);
    requestLayout();
  }
  
  public void setPaddingRight(int paramInt) {
    this.mFlow.setPaddingRight(paramInt);
    requestLayout();
  }
  
  public void setPaddingTop(int paramInt) {
    this.mFlow.setPaddingTop(paramInt);
    requestLayout();
  }
  
  public void setVerticalAlign(int paramInt) {
    this.mFlow.setVerticalAlign(paramInt);
    requestLayout();
  }
  
  public void setVerticalBias(float paramFloat) {
    this.mFlow.setVerticalBias(paramFloat);
    requestLayout();
  }
  
  public void setVerticalGap(int paramInt) {
    this.mFlow.setVerticalGap(paramInt);
    requestLayout();
  }
  
  public void setVerticalStyle(int paramInt) {
    this.mFlow.setVerticalStyle(paramInt);
    requestLayout();
  }
  
  public void setWrapMode(int paramInt) {
    this.mFlow.setWrapMode(paramInt);
    requestLayout();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\helper\widget\Flow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */