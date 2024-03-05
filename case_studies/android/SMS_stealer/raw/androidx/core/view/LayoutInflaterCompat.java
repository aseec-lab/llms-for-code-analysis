package androidx.core.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import java.lang.reflect.Field;

public final class LayoutInflaterCompat {
  private static final String TAG = "LayoutInflaterCompatHC";
  
  private static boolean sCheckedField;
  
  private static Field sLayoutInflaterFactory2Field;
  
  private static void forceSetFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2) {
    if (!sCheckedField) {
      try {
        Field field1 = LayoutInflater.class.getDeclaredField("mFactory2");
        sLayoutInflaterFactory2Field = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("forceSetFactory2 Could not find field 'mFactory2' on class ");
        stringBuilder.append(LayoutInflater.class.getName());
        stringBuilder.append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", stringBuilder.toString(), noSuchFieldException);
      } 
      sCheckedField = true;
    } 
    Field field = sLayoutInflaterFactory2Field;
    if (field != null)
      try {
        field.set(paramLayoutInflater, paramFactory2);
      } catch (IllegalAccessException illegalAccessException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("forceSetFactory2 could not set the Factory2 on LayoutInflater ");
        stringBuilder.append(paramLayoutInflater);
        stringBuilder.append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", stringBuilder.toString(), illegalAccessException);
      }  
  }
  
  @Deprecated
  public static LayoutInflaterFactory getFactory(LayoutInflater paramLayoutInflater) {
    LayoutInflater.Factory factory = paramLayoutInflater.getFactory();
    return (factory instanceof Factory2Wrapper) ? ((Factory2Wrapper)factory).mDelegateFactory : null;
  }
  
  @Deprecated
  public static void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory) {
    int i = Build.VERSION.SDK_INT;
    Factory2Wrapper factory2Wrapper2 = null;
    Factory2Wrapper factory2Wrapper1 = null;
    if (i >= 21) {
      if (paramLayoutInflaterFactory != null)
        factory2Wrapper1 = new Factory2Wrapper(paramLayoutInflaterFactory); 
      paramLayoutInflater.setFactory2(factory2Wrapper1);
    } else {
      factory2Wrapper1 = factory2Wrapper2;
      if (paramLayoutInflaterFactory != null)
        factory2Wrapper1 = new Factory2Wrapper(paramLayoutInflaterFactory); 
      paramLayoutInflater.setFactory2(factory2Wrapper1);
      LayoutInflater.Factory factory = paramLayoutInflater.getFactory();
      if (factory instanceof LayoutInflater.Factory2) {
        forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)factory);
      } else {
        forceSetFactory2(paramLayoutInflater, factory2Wrapper1);
      } 
    } 
  }
  
  public static void setFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2) {
    paramLayoutInflater.setFactory2(paramFactory2);
    if (Build.VERSION.SDK_INT < 21) {
      LayoutInflater.Factory factory = paramLayoutInflater.getFactory();
      if (factory instanceof LayoutInflater.Factory2) {
        forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)factory);
      } else {
        forceSetFactory2(paramLayoutInflater, paramFactory2);
      } 
    } 
  }
  
  static class Factory2Wrapper implements LayoutInflater.Factory2 {
    final LayoutInflaterFactory mDelegateFactory;
    
    Factory2Wrapper(LayoutInflaterFactory param1LayoutInflaterFactory) {
      this.mDelegateFactory = param1LayoutInflaterFactory;
    }
    
    public View onCreateView(View param1View, String param1String, Context param1Context, AttributeSet param1AttributeSet) {
      return this.mDelegateFactory.onCreateView(param1View, param1String, param1Context, param1AttributeSet);
    }
    
    public View onCreateView(String param1String, Context param1Context, AttributeSet param1AttributeSet) {
      return this.mDelegateFactory.onCreateView(null, param1String, param1Context, param1AttributeSet);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getClass().getName());
      stringBuilder.append("{");
      stringBuilder.append(this.mDelegateFactory);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\LayoutInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */