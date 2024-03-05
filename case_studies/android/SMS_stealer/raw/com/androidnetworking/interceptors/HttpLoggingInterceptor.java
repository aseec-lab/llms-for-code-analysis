package com.androidnetworking.interceptors;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;

public class HttpLoggingInterceptor implements Interceptor {
  private static final Charset UTF8 = Charset.forName("UTF-8");
  
  private volatile Level level = Level.NONE;
  
  private final Logger logger;
  
  public HttpLoggingInterceptor() {
    this(Logger.DEFAULT);
  }
  
  public HttpLoggingInterceptor(Logger paramLogger) {
    this.logger = paramLogger;
  }
  
  private boolean bodyEncoded(Headers paramHeaders) {
    boolean bool;
    String str = paramHeaders.get("Content-Encoding");
    if (str != null && !str.equalsIgnoreCase("identity")) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static boolean isPlaintext(Buffer paramBuffer) {
    try {
      long l;
      Buffer buffer = new Buffer();
      this();
      if (paramBuffer.size() < 64L) {
        l = paramBuffer.size();
      } else {
        l = 64L;
      } 
      paramBuffer.copyTo(buffer, 0L, l);
      for (byte b = 0; b < 16 && !buffer.exhausted(); b++) {
        int i = buffer.readUtf8CodePoint();
        if (Character.isISOControl(i)) {
          boolean bool = Character.isWhitespace(i);
          if (!bool)
            return false; 
        } 
      } 
      return true;
    } catch (EOFException eOFException) {
      return false;
    } 
  }
  
  public Level getLevel() {
    return this.level;
  }
  
  public Response intercept(Interceptor.Chain paramChain) throws IOException {
    byte b;
    boolean bool;
    Protocol protocol;
    Level level2 = this.level;
    Request request = paramChain.request();
    if (level2 == Level.NONE)
      return paramChain.proceed(request); 
    Level level1 = Level.BODY;
    int i = 1;
    if (level2 == level1) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool || level2 == Level.HEADERS) {
      b = 1;
    } else {
      b = 0;
    } 
    RequestBody requestBody = request.body();
    if (requestBody == null)
      i = 0; 
    Connection connection = paramChain.connection();
    if (connection != null) {
      protocol = connection.protocol();
    } else {
      protocol = Protocol.HTTP_1_1;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("--> ");
    stringBuilder.append(request.method());
    stringBuilder.append(' ');
    stringBuilder.append(request.url());
    stringBuilder.append(' ');
    stringBuilder.append(protocol);
    String str2 = stringBuilder.toString();
    String str1 = str2;
    if (!b) {
      str1 = str2;
      if (i) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str2);
        stringBuilder1.append(" (");
        stringBuilder1.append(requestBody.contentLength());
        stringBuilder1.append("-byte body)");
        str1 = stringBuilder1.toString();
      } 
    } 
    this.logger.log(str1);
    if (b) {
      if (i) {
        if (requestBody.contentType() != null) {
          Logger logger = this.logger;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Content-Type: ");
          stringBuilder1.append(requestBody.contentType());
          logger.log(stringBuilder1.toString());
        } 
        if (requestBody.contentLength() != -1L) {
          Logger logger = this.logger;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Content-Length: ");
          stringBuilder1.append(requestBody.contentLength());
          logger.log(stringBuilder1.toString());
        } 
      } 
      Headers headers = request.headers();
      int j = headers.size();
      for (byte b1 = 0; b1 < j; b1++) {
        String str = headers.name(b1);
        if (!"Content-Type".equalsIgnoreCase(str) && !"Content-Length".equalsIgnoreCase(str)) {
          Logger logger = this.logger;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str);
          stringBuilder1.append(": ");
          stringBuilder1.append(headers.value(b1));
          logger.log(stringBuilder1.toString());
        } 
      } 
      if (!bool || !i) {
        Logger logger = this.logger;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("--> END ");
        stringBuilder1.append(request.method());
        logger.log(stringBuilder1.toString());
      } else if (bodyEncoded(request.headers())) {
        Logger logger = this.logger;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("--> END ");
        stringBuilder1.append(request.method());
        stringBuilder1.append(" (encoded body omitted)");
        logger.log(stringBuilder1.toString());
      } else {
        Buffer buffer = new Buffer();
        requestBody.writeTo((BufferedSink)buffer);
        Charset charset = UTF8;
        MediaType mediaType = requestBody.contentType();
        if (mediaType != null)
          charset = mediaType.charset(UTF8); 
        this.logger.log("");
        if (isPlaintext(buffer)) {
          this.logger.log(buffer.readString(charset));
          Logger logger = this.logger;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("--> END ");
          stringBuilder1.append(request.method());
          stringBuilder1.append(" (");
          stringBuilder1.append(requestBody.contentLength());
          stringBuilder1.append("-byte body)");
          logger.log(stringBuilder1.toString());
        } else {
          Logger logger = this.logger;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("--> END ");
          stringBuilder1.append(request.method());
          stringBuilder1.append(" (binary ");
          stringBuilder1.append(requestBody.contentLength());
          stringBuilder1.append("-byte body omitted)");
          logger.log(stringBuilder1.toString());
        } 
      } 
    } 
    long l = System.nanoTime();
    try {
      String str;
      Response response = paramChain.proceed(request);
      long l1 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l);
      ResponseBody responseBody = response.body();
      l = responseBody.contentLength();
      if (l != -1L) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(l);
        stringBuilder2.append("-byte");
        str = stringBuilder2.toString();
      } else {
        str = "unknown-length";
      } 
      Logger logger = this.logger;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("<-- ");
      stringBuilder1.append(response.code());
      stringBuilder1.append(' ');
      stringBuilder1.append(response.message());
      stringBuilder1.append(' ');
      stringBuilder1.append(response.request().url());
      stringBuilder1.append(" (");
      stringBuilder1.append(l1);
      stringBuilder1.append("ms");
      if (!b) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", ");
        stringBuilder2.append(str);
        stringBuilder2.append(" body");
        str = stringBuilder2.toString();
      } else {
        str = "";
      } 
      stringBuilder1.append(str);
      stringBuilder1.append(')');
      logger.log(stringBuilder1.toString());
      if (b) {
        Headers headers = response.headers();
        i = headers.size();
        for (b = 0; b < i; b++) {
          logger = this.logger;
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append(headers.name(b));
          stringBuilder1.append(": ");
          stringBuilder1.append(headers.value(b));
          logger.log(stringBuilder1.toString());
        } 
        if (!bool || !HttpHeaders.hasBody(response)) {
          this.logger.log("<-- END HTTP");
          return response;
        } 
        if (bodyEncoded(response.headers())) {
          this.logger.log("<-- END HTTP (encoded body omitted)");
        } else {
          BufferedSource bufferedSource = responseBody.source();
          bufferedSource.request(Long.MAX_VALUE);
          Buffer buffer = bufferedSource.buffer();
          Charset charset = UTF8;
          MediaType mediaType = responseBody.contentType();
          if (mediaType != null)
            charset = mediaType.charset(UTF8); 
          if (!isPlaintext(buffer)) {
            this.logger.log("");
            logger1 = this.logger;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("<-- END HTTP (binary ");
            stringBuilder3.append(buffer.size());
            stringBuilder3.append("-byte body omitted)");
            logger1.log(stringBuilder3.toString());
            return response;
          } 
          if (l != 0L) {
            this.logger.log("");
            this.logger.log(buffer.clone().readString((Charset)logger1));
          } 
          Logger logger1 = this.logger;
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("<-- END HTTP (");
          stringBuilder2.append(buffer.size());
          stringBuilder2.append("-byte body)");
          logger1.log(stringBuilder2.toString());
        } 
      } 
      return response;
    } catch (Exception exception) {
      Logger logger = this.logger;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("<-- HTTP FAILED: ");
      stringBuilder1.append(exception);
      logger.log(stringBuilder1.toString());
      throw exception;
    } 
  }
  
  public HttpLoggingInterceptor setLevel(Level paramLevel) {
    if (paramLevel != null) {
      this.level = paramLevel;
      return this;
    } 
    throw new NullPointerException("level == null. Use Level.NONE instead.");
  }
  
  public enum Level {
    BASIC, BODY, HEADERS, NONE;
    
    private static final Level[] $VALUES;
    
    static {
      Level level = new Level("BODY", 3);
      BODY = level;
      $VALUES = new Level[] { NONE, BASIC, HEADERS, level };
    }
  }
  
  public static interface Logger {
    public static final Logger DEFAULT = new Logger() {
        public void log(String param2String) {
          Platform.get().log(4, param2String, null);
        }
      };
    
    void log(String param1String);
  }
  
  static final class null implements Logger {
    public void log(String param1String) {
      Platform.get().log(4, param1String, null);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\interceptors\HttpLoggingInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */