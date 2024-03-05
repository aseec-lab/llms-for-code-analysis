package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.R;

public class AppCompatSeekBar extends SeekBar {
  private final AppCompatSeekBarHelper mAppCompatSeekBarHelper;
  
  public AppCompatSeekBar(Context paramContext) {
    this(paramContext, null);
  }
  
  public AppCompatSeekBar(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.seekBarStyle);
  }
  
  public AppCompatSeekBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme((View)this, getContext());
    AppCompatSeekBarHelper appCompatSeekBarHelper = new AppCompatSeekBarHelper(this);
    this.mAppCompatSeekBarHelper = appCompatSeekBarHelper;
    appCompatSeekBarHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    this.mAppCompatSeekBarHelper.drawableStateChanged();
  }
  
  public void jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState();
    this.mAppCompatSeekBarHelper.jumpDrawablesToCurrentState();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial onDraw : (Landroid/graphics/Canvas;)V
    //   7: aload_0
    //   8: getfield mAppCompatSeekBarHelper : Landroidx/appcompat/widget/AppCompatSeekBarHelper;
    //   11: aload_1
    //   12: invokevirtual drawTickMarks : (Landroid/graphics/Canvas;)V
    //   15: aload_0
    //   16: monitorexit
    //   17: return
    //   18: astore_1
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_1
    //   22: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	18	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatSeekBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */