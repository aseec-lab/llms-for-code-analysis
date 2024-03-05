package okhttp3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

public final class FormBody extends RequestBody {
  private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
  
  private final List<String> encodedNames;
  
  private final List<String> encodedValues;
  
  FormBody(List<String> paramList1, List<String> paramList2) {
    this.encodedNames = Util.immutableList(paramList1);
    this.encodedValues = Util.immutableList(paramList2);
  }
  
  private long writeOrCountBytes(@Nullable BufferedSink paramBufferedSink, boolean paramBoolean) {
    Buffer buffer;
    long l;
    if (paramBoolean) {
      buffer = new Buffer();
    } else {
      buffer = buffer.buffer();
    } 
    byte b = 0;
    int i = this.encodedNames.size();
    while (b < i) {
      if (b > 0)
        buffer.writeByte(38); 
      buffer.writeUtf8(this.encodedNames.get(b));
      buffer.writeByte(61);
      buffer.writeUtf8(this.encodedValues.get(b));
      b++;
    } 
    if (paramBoolean) {
      l = buffer.size();
      buffer.clear();
    } else {
      l = 0L;
    } 
    return l;
  }
  
  public long contentLength() {
    return writeOrCountBytes(null, true);
  }
  
  public MediaType contentType() {
    return CONTENT_TYPE;
  }
  
  public String encodedName(int paramInt) {
    return this.encodedNames.get(paramInt);
  }
  
  public String encodedValue(int paramInt) {
    return this.encodedValues.get(paramInt);
  }
  
  public String name(int paramInt) {
    return HttpUrl.percentDecode(encodedName(paramInt), true);
  }
  
  public int size() {
    return this.encodedNames.size();
  }
  
  public String value(int paramInt) {
    return HttpUrl.percentDecode(encodedValue(paramInt), true);
  }
  
  public void writeTo(BufferedSink paramBufferedSink) throws IOException {
    writeOrCountBytes(paramBufferedSink, false);
  }
  
  public static final class Builder {
    private final Charset charset;
    
    private final List<String> names = new ArrayList<String>();
    
    private final List<String> values = new ArrayList<String>();
    
    public Builder() {
      this(null);
    }
    
    public Builder(Charset param1Charset) {
      this.charset = param1Charset;
    }
    
    public Builder add(String param1String1, String param1String2) {
      if (param1String1 != null) {
        if (param1String2 != null) {
          this.names.add(HttpUrl.canonicalize(param1String1, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true, this.charset));
          this.values.add(HttpUrl.canonicalize(param1String2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true, this.charset));
          return this;
        } 
        throw new NullPointerException("value == null");
      } 
      throw new NullPointerException("name == null");
    }
    
    public Builder addEncoded(String param1String1, String param1String2) {
      if (param1String1 != null) {
        if (param1String2 != null) {
          this.names.add(HttpUrl.canonicalize(param1String1, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true, this.charset));
          this.values.add(HttpUrl.canonicalize(param1String2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true, this.charset));
          return this;
        } 
        throw new NullPointerException("value == null");
      } 
      throw new NullPointerException("name == null");
    }
    
    public FormBody build() {
      return new FormBody(this.names, this.values);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\FormBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */