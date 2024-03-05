package androidx.core.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class KeyEventDispatcher {
  private static boolean sActionBarFieldsFetched;
  
  private static Method sActionBarOnMenuKeyMethod;
  
  private static boolean sDialogFieldsFetched;
  
  private static Field sDialogKeyListenerField;
  
  private static boolean actionBarOnMenuKeyEventPre28(ActionBar paramActionBar, KeyEvent paramKeyEvent) {
    if (!sActionBarFieldsFetched) {
      try {
        sActionBarOnMenuKeyMethod = paramActionBar.getClass().getMethod("onMenuKeyEvent", new Class[] { KeyEvent.class });
      } catch (NoSuchMethodException noSuchMethodException) {}
      sActionBarFieldsFetched = true;
    } 
    Method method = sActionBarOnMenuKeyMethod;
    if (method != null)
      try {
        return ((Boolean)method.invoke(paramActionBar, new Object[] { paramKeyEvent })).booleanValue();
      } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {} 
    return false;
  }
  
  private static boolean activitySuperDispatchKeyEventPre28(Activity paramActivity, KeyEvent paramKeyEvent) {
    paramActivity.onUserInteraction();
    Window window = paramActivity.getWindow();
    if (window.hasFeature(8)) {
      ActionBar actionBar = paramActivity.getActionBar();
      if (paramKeyEvent.getKeyCode() == 82 && actionBar != null && actionBarOnMenuKeyEventPre28(actionBar, paramKeyEvent))
        return true; 
    } 
    if (window.superDispatchKeyEvent(paramKeyEvent))
      return true; 
    View view = window.getDecorView();
    if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(view, paramKeyEvent))
      return true; 
    if (view != null) {
      KeyEvent.DispatcherState dispatcherState = view.getKeyDispatcherState();
    } else {
      view = null;
    } 
    return paramKeyEvent.dispatch((KeyEvent.Callback)paramActivity, (KeyEvent.DispatcherState)view, paramActivity);
  }
  
  private static boolean dialogSuperDispatchKeyEventPre28(Dialog paramDialog, KeyEvent paramKeyEvent) {
    DialogInterface.OnKeyListener onKeyListener = getDialogKeyListenerPre28(paramDialog);
    if (onKeyListener != null && onKeyListener.onKey((DialogInterface)paramDialog, paramKeyEvent.getKeyCode(), paramKeyEvent))
      return true; 
    Window window = paramDialog.getWindow();
    if (window.superDispatchKeyEvent(paramKeyEvent))
      return true; 
    View view = window.getDecorView();
    if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(view, paramKeyEvent))
      return true; 
    if (view != null) {
      KeyEvent.DispatcherState dispatcherState = view.getKeyDispatcherState();
    } else {
      view = null;
    } 
    return paramKeyEvent.dispatch((KeyEvent.Callback)paramDialog, (KeyEvent.DispatcherState)view, paramDialog);
  }
  
  public static boolean dispatchBeforeHierarchy(View paramView, KeyEvent paramKeyEvent) {
    return ViewCompat.dispatchUnhandledKeyEventBeforeHierarchy(paramView, paramKeyEvent);
  }
  
  public static boolean dispatchKeyEvent(Component paramComponent, View paramView, Window.Callback paramCallback, KeyEvent paramKeyEvent) {
    boolean bool = false;
    if (paramComponent == null)
      return false; 
    if (Build.VERSION.SDK_INT >= 28)
      return paramComponent.superDispatchKeyEvent(paramKeyEvent); 
    if (paramCallback instanceof Activity)
      return activitySuperDispatchKeyEventPre28((Activity)paramCallback, paramKeyEvent); 
    if (paramCallback instanceof Dialog)
      return dialogSuperDispatchKeyEventPre28((Dialog)paramCallback, paramKeyEvent); 
    if ((paramView != null && ViewCompat.dispatchUnhandledKeyEventBeforeCallback(paramView, paramKeyEvent)) || paramComponent.superDispatchKeyEvent(paramKeyEvent))
      bool = true; 
    return bool;
  }
  
  private static DialogInterface.OnKeyListener getDialogKeyListenerPre28(Dialog paramDialog) {
    if (!sDialogFieldsFetched) {
      try {
        Field field1 = Dialog.class.getDeclaredField("mOnKeyListener");
        sDialogKeyListenerField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {}
      sDialogFieldsFetched = true;
    } 
    Field field = sDialogKeyListenerField;
    if (field != null)
      try {
        return (DialogInterface.OnKeyListener)field.get(paramDialog);
      } catch (IllegalAccessException illegalAccessException) {} 
    return null;
  }
  
  public static interface Component {
    boolean superDispatchKeyEvent(KeyEvent param1KeyEvent);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\KeyEventDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */