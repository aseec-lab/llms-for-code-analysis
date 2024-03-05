package androidx.core.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public final class DisplayManagerCompat {
  public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
  
  private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap<Context, DisplayManagerCompat>();
  
  private final Context mContext;
  
  private DisplayManagerCompat(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public static DisplayManagerCompat getInstance(Context paramContext) {
    synchronized (sInstances) {
      DisplayManagerCompat displayManagerCompat2 = sInstances.get(paramContext);
      DisplayManagerCompat displayManagerCompat1 = displayManagerCompat2;
      if (displayManagerCompat2 == null) {
        displayManagerCompat1 = new DisplayManagerCompat();
        this(paramContext);
        sInstances.put(paramContext, displayManagerCompat1);
      } 
      return displayManagerCompat1;
    } 
  }
  
  public Display getDisplay(int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      return ((DisplayManager)this.mContext.getSystemService("display")).getDisplay(paramInt); 
    Display display = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
    return (display.getDisplayId() == paramInt) ? display : null;
  }
  
  public Display[] getDisplays() {
    return (Build.VERSION.SDK_INT >= 17) ? ((DisplayManager)this.mContext.getSystemService("display")).getDisplays() : new Display[] { ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay() };
  }
  
  public Display[] getDisplays(String paramString) {
    return (Build.VERSION.SDK_INT >= 17) ? ((DisplayManager)this.mContext.getSystemService("display")).getDisplays(paramString) : ((paramString == null) ? new Display[0] : new Display[] { ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay() });
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\hardware\display\DisplayManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */