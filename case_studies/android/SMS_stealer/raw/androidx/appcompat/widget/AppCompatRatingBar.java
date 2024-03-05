package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import androidx.appcompat.R;

public class AppCompatRatingBar extends RatingBar {
  private final AppCompatProgressBarHelper mAppCompatProgressBarHelper;
  
  public AppCompatRatingBar(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public AppCompatRatingBar(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.ratingBarStyle);
  }
  
  public AppCompatRatingBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme((View)this, getContext());
    AppCompatProgressBarHelper appCompatProgressBarHelper = new AppCompatProgressBarHelper((ProgressBar)this);
    this.mAppCompatProgressBarHelper = appCompatProgressBarHelper;
    appCompatProgressBarHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iload_1
    //   4: iload_2
    //   5: invokespecial onMeasure : (II)V
    //   8: aload_0
    //   9: getfield mAppCompatProgressBarHelper : Landroidx/appcompat/widget/AppCompatProgressBarHelper;
    //   12: invokevirtual getSampleTile : ()Landroid/graphics/Bitmap;
    //   15: astore_3
    //   16: aload_3
    //   17: ifnull -> 42
    //   20: aload_0
    //   21: aload_3
    //   22: invokevirtual getWidth : ()I
    //   25: aload_0
    //   26: invokevirtual getNumStars : ()I
    //   29: imul
    //   30: iload_1
    //   31: iconst_0
    //   32: invokestatic resolveSizeAndState : (III)I
    //   35: aload_0
    //   36: invokevirtual getMeasuredHeight : ()I
    //   39: invokevirtual setMeasuredDimension : (II)V
    //   42: aload_0
    //   43: monitorexit
    //   44: return
    //   45: astore_3
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_3
    //   49: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	45	finally
    //   20	42	45	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatRatingBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */