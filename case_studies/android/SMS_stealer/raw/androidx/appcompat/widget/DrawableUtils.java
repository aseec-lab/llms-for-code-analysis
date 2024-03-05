package androidx.appcompat.widget;

import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.util.Log;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.WrappedDrawable;
import java.lang.reflect.Field;

public class DrawableUtils {
  private static final int[] CHECKED_STATE_SET = new int[] { 16842912 };
  
  private static final int[] EMPTY_STATE_SET = new int[0];
  
  public static final Rect INSETS_NONE = new Rect();
  
  private static final String TAG = "DrawableUtils";
  
  private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
  
  private static Class<?> sInsetsClazz;
  
  static {
    if (Build.VERSION.SDK_INT >= 18)
      try {
        sInsetsClazz = Class.forName("android.graphics.Insets");
      } catch (ClassNotFoundException classNotFoundException) {} 
  }
  
  public static boolean canSafelyMutateDrawable(Drawable paramDrawable) {
    Drawable[] arrayOfDrawable;
    if (Build.VERSION.SDK_INT < 15 && paramDrawable instanceof android.graphics.drawable.InsetDrawable)
      return false; 
    if (Build.VERSION.SDK_INT < 15 && paramDrawable instanceof android.graphics.drawable.GradientDrawable)
      return false; 
    if (Build.VERSION.SDK_INT < 17 && paramDrawable instanceof android.graphics.drawable.LayerDrawable)
      return false; 
    if (paramDrawable instanceof DrawableContainer) {
      Drawable.ConstantState constantState = paramDrawable.getConstantState();
      if (constantState instanceof DrawableContainer.DrawableContainerState) {
        arrayOfDrawable = ((DrawableContainer.DrawableContainerState)constantState).getChildren();
        int i = arrayOfDrawable.length;
        for (byte b = 0; b < i; b++) {
          if (!canSafelyMutateDrawable(arrayOfDrawable[b]))
            return false; 
        } 
      } 
    } else {
      if (arrayOfDrawable instanceof WrappedDrawable)
        return canSafelyMutateDrawable(((WrappedDrawable)arrayOfDrawable).getWrappedDrawable()); 
      if (arrayOfDrawable instanceof DrawableWrapper)
        return canSafelyMutateDrawable(((DrawableWrapper)arrayOfDrawable).getWrappedDrawable()); 
      if (arrayOfDrawable instanceof ScaleDrawable)
        return canSafelyMutateDrawable(((ScaleDrawable)arrayOfDrawable).getDrawable()); 
    } 
    return true;
  }
  
  static void fixDrawable(Drawable paramDrawable) {
    if (Build.VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(paramDrawable.getClass().getName()))
      fixVectorDrawableTinting(paramDrawable); 
  }
  
  private static void fixVectorDrawableTinting(Drawable paramDrawable) {
    int[] arrayOfInt = paramDrawable.getState();
    if (arrayOfInt == null || arrayOfInt.length == 0) {
      paramDrawable.setState(CHECKED_STATE_SET);
    } else {
      paramDrawable.setState(EMPTY_STATE_SET);
    } 
    paramDrawable.setState(arrayOfInt);
  }
  
  public static Rect getOpticalBounds(Drawable paramDrawable) {
    Rect rect;
    if (Build.VERSION.SDK_INT >= 29) {
      Insets insets = paramDrawable.getOpticalInsets();
      rect = new Rect();
      rect.left = insets.left;
      rect.right = insets.right;
      rect.top = insets.top;
      rect.bottom = insets.bottom;
      return rect;
    } 
    if (sInsetsClazz != null)
      try {
        Drawable drawable = DrawableCompat.unwrap((Drawable)rect);
        Object object = drawable.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(drawable, new Object[0]);
        if (object != null) {
          Rect rect1 = new Rect();
          this();
          for (Field field : sInsetsClazz.getFields()) {
            String str = field.getName();
            byte b = -1;
            switch (str.hashCode()) {
              case 108511772:
                if (str.equals("right"))
                  b = 2; 
                break;
              case 3317767:
                if (str.equals("left"))
                  b = 0; 
                break;
              case 115029:
                if (str.equals("top"))
                  b = 1; 
                break;
              case -1383228885:
                if (str.equals("bottom"))
                  b = 3; 
                break;
            } 
            if (b != 0) {
              if (b != 1) {
                if (b != 2) {
                  if (b == 3)
                    rect1.bottom = field.getInt(object); 
                } else {
                  rect1.right = field.getInt(object);
                } 
              } else {
                rect1.top = field.getInt(object);
              } 
            } else {
              rect1.left = field.getInt(object);
            } 
          } 
          return rect1;
        } 
      } catch (Exception exception) {
        Log.e("DrawableUtils", "Couldn't obtain the optical insets. Ignoring.");
      }  
    return INSETS_NONE;
  }
  
  public static PorterDuff.Mode parseTintMode(int paramInt, PorterDuff.Mode paramMode) {
    if (paramInt != 3) {
      if (paramInt != 5) {
        if (paramInt != 9) {
          switch (paramInt) {
            default:
              return paramMode;
            case 16:
              return PorterDuff.Mode.ADD;
            case 15:
              return PorterDuff.Mode.SCREEN;
            case 14:
              break;
          } 
          return PorterDuff.Mode.MULTIPLY;
        } 
        return PorterDuff.Mode.SRC_ATOP;
      } 
      return PorterDuff.Mode.SRC_IN;
    } 
    return PorterDuff.Mode.SRC_OVER;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\DrawableUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */