package ir.siqe.holo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
  public void onBackPressed() {
    Toast.makeText((Context)this, "back to exit", 1).show();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131361832);
    WebView webView = (WebView)findViewById(2131165433);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new mWebViewClient());
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setLoadWithOverviewMode(true);
    webView.getSettings().setUseWideViewPort(true);
    webView.loadUrl("https://eblaqie.org/pishgiri");
  }
  
  private class mWebViewClient extends WebViewClient {
    final MainActivity2 this$0;
    
    private mWebViewClient() {}
    
    public void onPageFinished(WebView param1WebView, String param1String) {
      super.onPageFinished(param1WebView, param1String);
    }
    
    public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
      super.onPageStarted(param1WebView, param1String, param1Bitmap);
    }
    
    public void onReceivedError(WebView param1WebView, WebResourceRequest param1WebResourceRequest, WebResourceError param1WebResourceError) {
      super.onReceivedError(param1WebView, param1WebResourceRequest, param1WebResourceError);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1WebResourceError);
      stringBuilder.append("");
      Log.i("============>", stringBuilder.toString());
    }
    
    public void onReceivedSslError(WebView param1WebView, SslErrorHandler param1SslErrorHandler, SslError param1SslError) {
      super.onReceivedSslError(param1WebView, param1SslErrorHandler, param1SslError);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1SslError);
      stringBuilder.append("");
      Log.i("============s>", stringBuilder.toString());
      param1SslErrorHandler.proceed();
    }
    
    public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
      param1WebView.loadUrl(param1String);
      return true;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\ir\siqe\holo\MainActivity2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */