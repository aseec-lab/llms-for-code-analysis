package androidx.appcompat.widget;

import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

class AppCompatHintHelper {
  static InputConnection onCreateInputConnection(InputConnection paramInputConnection, EditorInfo paramEditorInfo, View paramView) {
    if (paramInputConnection != null && paramEditorInfo.hintText == null)
      for (ViewParent viewParent = paramView.getParent(); viewParent instanceof View; viewParent = viewParent.getParent()) {
        if (viewParent instanceof WithHint) {
          paramEditorInfo.hintText = ((WithHint)viewParent).getHint();
          break;
        } 
      }  
    return paramInputConnection;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatHintHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */