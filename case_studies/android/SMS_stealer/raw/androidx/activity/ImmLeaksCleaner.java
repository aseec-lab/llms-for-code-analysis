package androidx.activity;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Field;

final class ImmLeaksCleaner implements LifecycleEventObserver {
  private static final int INIT_FAILED = 2;
  
  private static final int INIT_SUCCESS = 1;
  
  private static final int NOT_INITIALIAZED = 0;
  
  private static Field sHField;
  
  private static Field sNextServedViewField;
  
  private static int sReflectedFieldsInitialized;
  
  private static Field sServedViewField;
  
  private Activity mActivity;
  
  ImmLeaksCleaner(Activity paramActivity) {
    this.mActivity = paramActivity;
  }
  
  private static void initializeReflectiveFields() {
    try {
      sReflectedFieldsInitialized = 2;
      Field field = InputMethodManager.class.getDeclaredField("mServedView");
      sServedViewField = field;
      field.setAccessible(true);
      field = InputMethodManager.class.getDeclaredField("mNextServedView");
      sNextServedViewField = field;
      field.setAccessible(true);
      field = InputMethodManager.class.getDeclaredField("mH");
      sHField = field;
      field.setAccessible(true);
      sReflectedFieldsInitialized = 1;
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent) {
    if (paramEvent != Lifecycle.Event.ON_DESTROY)
      return; 
    if (sReflectedFieldsInitialized == 0)
      initializeReflectiveFields(); 
    if (sReflectedFieldsInitialized == 1) {
      InputMethodManager inputMethodManager = (InputMethodManager)this.mActivity.getSystemService("input_method");
      try {
        Object object = sHField.get(inputMethodManager);
        if (object == null)
          return; 
        /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
        try {
          View view = (View)sServedViewField.get(inputMethodManager);
          if (view == null) {
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
            return;
          } 
          if (view.isAttachedToWindow()) {
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
            return;
          } 
          try {
            sNextServedViewField.set(inputMethodManager, (Object)null);
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
            inputMethodManager.isActive();
          } catch (IllegalAccessException illegalAccessException) {
            /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
            return;
          } 
        } catch (IllegalAccessException illegalAccessException) {
          /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
          return;
        } catch (ClassCastException classCastException) {
          /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
          return;
        } finally {}
      } catch (IllegalAccessException illegalAccessException) {}
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\activity\ImmLeaksCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */