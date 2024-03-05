package okhttp3;

import java.net.URL;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

public final class Request {
  @Nullable
  final RequestBody body;
  
  private volatile CacheControl cacheControl;
  
  final Headers headers;
  
  final String method;
  
  final Object tag;
  
  final HttpUrl url;
  
  Request(Builder paramBuilder) {
    Object object;
    this.url = paramBuilder.url;
    this.method = paramBuilder.method;
    this.headers = paramBuilder.headers.build();
    this.body = paramBuilder.body;
    if (paramBuilder.tag != null) {
      object = paramBuilder.tag;
    } else {
      object = this;
    } 
    this.tag = object;
  }
  
  @Nullable
  public RequestBody body() {
    return this.body;
  }
  
  public CacheControl cacheControl() {
    CacheControl cacheControl = this.cacheControl;
    if (cacheControl == null) {
      cacheControl = CacheControl.parse(this.headers);
      this.cacheControl = cacheControl;
    } 
    return cacheControl;
  }
  
  @Nullable
  public String header(String paramString) {
    return this.headers.get(paramString);
  }
  
  public List<String> headers(String paramString) {
    return this.headers.values(paramString);
  }
  
  public Headers headers() {
    return this.headers;
  }
  
  public boolean isHttps() {
    return this.url.isHttps();
  }
  
  public String method() {
    return this.method;
  }
  
  public Builder newBuilder() {
    return new Builder(this);
  }
  
  public Object tag() {
    return this.tag;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Request{method=");
    stringBuilder.append(this.method);
    stringBuilder.append(", url=");
    stringBuilder.append(this.url);
    stringBuilder.append(", tag=");
    Object object = this.tag;
    if (object == this)
      object = null; 
    stringBuilder.append(object);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public HttpUrl url() {
    return this.url;
  }
  
  public static class Builder {
    RequestBody body;
    
    Headers.Builder headers;
    
    String method;
    
    Object tag;
    
    HttpUrl url;
    
    public Builder() {
      this.method = "GET";
      this.headers = new Headers.Builder();
    }
    
    Builder(Request param1Request) {
      this.url = param1Request.url;
      this.method = param1Request.method;
      this.body = param1Request.body;
      this.tag = param1Request.tag;
      this.headers = param1Request.headers.newBuilder();
    }
    
    public Builder addHeader(String param1String1, String param1String2) {
      this.headers.add(param1String1, param1String2);
      return this;
    }
    
    public Request build() {
      if (this.url != null)
        return new Request(this); 
      throw new IllegalStateException("url == null");
    }
    
    public Builder cacheControl(CacheControl param1CacheControl) {
      String str = param1CacheControl.toString();
      return str.isEmpty() ? removeHeader("Cache-Control") : header("Cache-Control", str);
    }
    
    public Builder delete() {
      return delete(Util.EMPTY_REQUEST);
    }
    
    public Builder delete(@Nullable RequestBody param1RequestBody) {
      return method("DELETE", param1RequestBody);
    }
    
    public Builder get() {
      return method("GET", null);
    }
    
    public Builder head() {
      return method("HEAD", null);
    }
    
    public Builder header(String param1String1, String param1String2) {
      this.headers.set(param1String1, param1String2);
      return this;
    }
    
    public Builder headers(Headers param1Headers) {
      this.headers = param1Headers.newBuilder();
      return this;
    }
    
    public Builder method(String param1String, @Nullable RequestBody param1RequestBody) {
      if (param1String != null) {
        if (param1String.length() != 0) {
          if (param1RequestBody == null || HttpMethod.permitsRequestBody(param1String)) {
            if (param1RequestBody != null || !HttpMethod.requiresRequestBody(param1String)) {
              this.method = param1String;
              this.body = param1RequestBody;
              return this;
            } 
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("method ");
            stringBuilder1.append(param1String);
            stringBuilder1.append(" must have a request body.");
            throw new IllegalArgumentException(stringBuilder1.toString());
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("method ");
          stringBuilder.append(param1String);
          stringBuilder.append(" must not have a request body.");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        throw new IllegalArgumentException("method.length() == 0");
      } 
      throw new NullPointerException("method == null");
    }
    
    public Builder patch(RequestBody param1RequestBody) {
      return method("PATCH", param1RequestBody);
    }
    
    public Builder post(RequestBody param1RequestBody) {
      return method("POST", param1RequestBody);
    }
    
    public Builder put(RequestBody param1RequestBody) {
      return method("PUT", param1RequestBody);
    }
    
    public Builder removeHeader(String param1String) {
      this.headers.removeAll(param1String);
      return this;
    }
    
    public Builder tag(Object param1Object) {
      this.tag = param1Object;
      return this;
    }
    
    public Builder url(String param1String) {
      if (param1String != null) {
        String str;
        if (param1String.regionMatches(true, 0, "ws:", 0, 3)) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("http:");
          stringBuilder1.append(param1String.substring(3));
          str = stringBuilder1.toString();
        } else {
          str = param1String;
          if (param1String.regionMatches(true, 0, "wss:", 0, 4)) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("https:");
            stringBuilder1.append(param1String.substring(4));
            str = stringBuilder1.toString();
          } 
        } 
        HttpUrl httpUrl = HttpUrl.parse(str);
        if (httpUrl != null)
          return url(httpUrl); 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected url: ");
        stringBuilder.append(str);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new NullPointerException("url == null");
    }
    
    public Builder url(URL param1URL) {
      if (param1URL != null) {
        HttpUrl httpUrl = HttpUrl.get(param1URL);
        if (httpUrl != null)
          return url(httpUrl); 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected url: ");
        stringBuilder.append(param1URL);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new NullPointerException("url == null");
    }
    
    public Builder url(HttpUrl param1HttpUrl) {
      if (param1HttpUrl != null) {
        this.url = param1HttpUrl;
        return this;
      } 
      throw new NullPointerException("url == null");
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Request.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */