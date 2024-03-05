package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;

public abstract class ResponseBody implements Closeable {
  private Reader reader;
  
  private Charset charset() {
    Charset charset;
    MediaType mediaType = contentType();
    if (mediaType != null) {
      charset = mediaType.charset(Util.UTF_8);
    } else {
      charset = Util.UTF_8;
    } 
    return charset;
  }
  
  public static ResponseBody create(@Nullable final MediaType contentType, final long contentLength, final BufferedSource content) {
    if (content != null)
      return new ResponseBody() {
          final BufferedSource val$content;
          
          final long val$contentLength;
          
          final MediaType val$contentType;
          
          public long contentLength() {
            return contentLength;
          }
          
          @Nullable
          public MediaType contentType() {
            return contentType;
          }
          
          public BufferedSource source() {
            return content;
          }
        }; 
    throw new NullPointerException("source == null");
  }
  
  public static ResponseBody create(@Nullable MediaType paramMediaType, String paramString) {
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
    Buffer buffer = (new Buffer()).writeString(paramString, charset);
    return create(mediaType, buffer.size(), (BufferedSource)buffer);
  }
  
  public static ResponseBody create(@Nullable MediaType paramMediaType, byte[] paramArrayOfbyte) {
    Buffer buffer = (new Buffer()).write(paramArrayOfbyte);
    return create(paramMediaType, paramArrayOfbyte.length, (BufferedSource)buffer);
  }
  
  public final InputStream byteStream() {
    return source().inputStream();
  }
  
  public final byte[] bytes() throws IOException {
    long l = contentLength();
    if (l <= 2147483647L) {
      StringBuilder stringBuilder1;
      BufferedSource bufferedSource = source();
      try {
        byte[] arrayOfByte = bufferedSource.readByteArray();
        Util.closeQuietly((Closeable)bufferedSource);
        if (l == -1L || l == arrayOfByte.length)
          return arrayOfByte; 
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Content-Length (");
        stringBuilder1.append(l);
        stringBuilder1.append(") and stream length (");
        stringBuilder1.append(arrayOfByte.length);
        stringBuilder1.append(") disagree");
        throw new IOException(stringBuilder1.toString());
      } finally {
        Util.closeQuietly((Closeable)stringBuilder1);
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot buffer entire body for content length: ");
    stringBuilder.append(l);
    throw new IOException(stringBuilder.toString());
  }
  
  public final Reader charStream() {
    Reader reader = this.reader;
    if (reader == null) {
      reader = new BomAwareReader(source(), charset());
      this.reader = reader;
    } 
    return reader;
  }
  
  public void close() {
    Util.closeQuietly((Closeable)source());
  }
  
  public abstract long contentLength();
  
  @Nullable
  public abstract MediaType contentType();
  
  public abstract BufferedSource source();
  
  public final String string() throws IOException {
    BufferedSource bufferedSource = source();
    try {
      return bufferedSource.readString(Util.bomAwareCharset(bufferedSource, charset()));
    } finally {
      Util.closeQuietly((Closeable)bufferedSource);
    } 
  }
  
  static final class BomAwareReader extends Reader {
    private final Charset charset;
    
    private boolean closed;
    
    private Reader delegate;
    
    private final BufferedSource source;
    
    BomAwareReader(BufferedSource param1BufferedSource, Charset param1Charset) {
      this.source = param1BufferedSource;
      this.charset = param1Charset;
    }
    
    public void close() throws IOException {
      this.closed = true;
      Reader reader = this.delegate;
      if (reader != null) {
        reader.close();
      } else {
        this.source.close();
      } 
    }
    
    public int read(char[] param1ArrayOfchar, int param1Int1, int param1Int2) throws IOException {
      if (!this.closed) {
        Reader reader2 = this.delegate;
        Reader reader1 = reader2;
        if (reader2 == null) {
          Charset charset = Util.bomAwareCharset(this.source, this.charset);
          reader1 = new InputStreamReader(this.source.inputStream(), charset);
          this.delegate = reader1;
        } 
        return reader1.read(param1ArrayOfchar, param1Int1, param1Int2);
      } 
      throw new IOException("Stream closed");
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\ResponseBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */