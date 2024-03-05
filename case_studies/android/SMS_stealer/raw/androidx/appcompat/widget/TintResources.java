package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

class TintResources extends ResourcesWrapper {
  private final WeakReference<Context> mContextRef;
  
  public TintResources(Context paramContext, Resources paramResources) {
    super(paramResources);
    this.mContextRef = new WeakReference<Context>(paramContext);
  }
  
  public Drawable getDrawable(int paramInt) throws Resources.NotFoundException {
    Drawable drawable = super.getDrawable(paramInt);
    Context context = this.mContextRef.get();
    if (drawable != null && context != null)
      ResourceManagerInternal.get().tintDrawableUsingColorFilter(context, paramInt, drawable); 
    return drawable;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\TintResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */