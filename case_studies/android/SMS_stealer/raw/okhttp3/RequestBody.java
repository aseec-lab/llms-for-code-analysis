package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;

public abstract class RequestBody {
  public static RequestBody create(@Nullable final MediaType contentType, final File file) {
    if (file != null)
      return new RequestBody() {
          final MediaType val$contentType;
          
          final File val$file;
          
          public long contentLength() {
            return file.length();
          }
          
          @Nullable
          public MediaType contentType() {
            return contentType;
          }
          
          public void writeTo(BufferedSink param1BufferedSink) throws IOException {
            Source source = null;
            try {
              Source source1 = Okio.source(file);
              source = source1;
              param1BufferedSink.writeAll(source1);
              return;
            } finally {
              Util.closeQuietly((Closeable)source);
            } 
          }
        }; 
    throw new NullPointerException("content == null");
  }
  
  public static RequestBody create(@Nullable MediaType paramMediaType, String paramString) {
    Charset charset = Util.UTF_8;
    MediaType mediaType = paramMediaType;
    if (paramMediaType != null) {
      Charset charset1 = paramMediaType.charset();
      charset = charset1;
      mediaType = paramMediaType;
      if (charset1 == null) {
        charset = Util.UTF_8;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramMediaType);
        stringBuilder.append("; charset=utf-8");
        mediaType = MediaType.parse(stringBuilder.toString());
      } 
    } 
    return create(mediaType, paramString.getBytes(charset));
  }
  
  public static RequestBody create(@Nullable final MediaType contentType, final ByteString content) {
    return new RequestBody() {
        final ByteString val$content;
        
        final MediaType val$contentType;
        
        public long contentLength() throws IOException {
          return content.size();
        }
        
        @Nullable
        public MediaType contentType() {
          return contentType;
        }
        
        public void writeTo(BufferedSink param1BufferedSink) throws IOException {
          param1BufferedSink.write(content);
        }
      };
  }
  
  public static RequestBody create(@Nullable MediaType paramMediaType, byte[] paramArrayOfbyte) {
    return create(paramMediaType, paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public static RequestBody create(@Nullable final MediaType contentType, final byte[] content, final int offset, final int byteCount) {
    if (content != null) {
      Util.checkOffsetAndCount(content.length, offset, byteCount);
      return new RequestBody() {
          final int val$byteCount;
          
          final byte[] val$content;
          
          final MediaType val$contentType;
          
          final int val$offset;
          
          public long contentLength() {
            return byteCount;
          }
          
          @Nullable
          public MediaType contentType() {
            return contentType;
          }
          
          public void writeTo(BufferedSink param1BufferedSink) throws IOException {
            param1BufferedSink.write(content, offset, byteCount);
          }
        };
    } 
    throw new NullPointerException("content == null");
  }
  
  public long contentLength() throws IOException {
    return -1L;
  }
  
  @Nullable
  public abstract MediaType contentType();
  
  public abstract void writeTo(BufferedSink paramBufferedSink) throws IOException;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\RequestBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */