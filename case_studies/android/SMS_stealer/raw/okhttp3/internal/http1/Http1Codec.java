package okhttp3.internal.http1;

import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http1Codec implements HttpCodec {
  private static final int HEADER_LIMIT = 262144;
  
  private static final int STATE_CLOSED = 6;
  
  private static final int STATE_IDLE = 0;
  
  private static final int STATE_OPEN_REQUEST_BODY = 1;
  
  private static final int STATE_OPEN_RESPONSE_BODY = 4;
  
  private static final int STATE_READING_RESPONSE_BODY = 5;
  
  private static final int STATE_READ_RESPONSE_HEADERS = 3;
  
  private static final int STATE_WRITING_REQUEST_BODY = 2;
  
  final OkHttpClient client;
  
  private long headerLimit = 262144L;
  
  final BufferedSink sink;
  
  final BufferedSource source;
  
  int state = 0;
  
  final StreamAllocation streamAllocation;
  
  public Http1Codec(OkHttpClient paramOkHttpClient, StreamAllocation paramStreamAllocation, BufferedSource paramBufferedSource, BufferedSink paramBufferedSink) {
    this.client = paramOkHttpClient;
    this.streamAllocation = paramStreamAllocation;
    this.source = paramBufferedSource;
    this.sink = paramBufferedSink;
  }
  
  private String readHeaderLine() throws IOException {
    String str = this.source.readUtf8LineStrict(this.headerLimit);
    this.headerLimit -= str.length();
    return str;
  }
  
  public void cancel() {
    RealConnection realConnection = this.streamAllocation.connection();
    if (realConnection != null)
      realConnection.cancel(); 
  }
  
  public Sink createRequestBody(Request paramRequest, long paramLong) {
    if ("chunked".equalsIgnoreCase(paramRequest.header("Transfer-Encoding")))
      return newChunkedSink(); 
    if (paramLong != -1L)
      return newFixedLengthSink(paramLong); 
    throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
  }
  
  void detachTimeout(ForwardingTimeout paramForwardingTimeout) {
    Timeout timeout = paramForwardingTimeout.delegate();
    paramForwardingTimeout.setDelegate(Timeout.NONE);
    timeout.clearDeadline();
    timeout.clearTimeout();
  }
  
  public void finishRequest() throws IOException {
    this.sink.flush();
  }
  
  public void flushRequest() throws IOException {
    this.sink.flush();
  }
  
  public boolean isClosed() {
    boolean bool;
    if (this.state == 6) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Sink newChunkedSink() {
    if (this.state == 1) {
      this.state = 2;
      return new ChunkedSink();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state: ");
    stringBuilder.append(this.state);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public Source newChunkedSource(HttpUrl paramHttpUrl) throws IOException {
    if (this.state == 4) {
      this.state = 5;
      return new ChunkedSource(paramHttpUrl);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state: ");
    stringBuilder.append(this.state);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public Sink newFixedLengthSink(long paramLong) {
    if (this.state == 1) {
      this.state = 2;
      return new FixedLengthSink(paramLong);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state: ");
    stringBuilder.append(this.state);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public Source newFixedLengthSource(long paramLong) throws IOException {
    if (this.state == 4) {
      this.state = 5;
      return new FixedLengthSource(paramLong);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state: ");
    stringBuilder.append(this.state);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public Source newUnknownLengthSource() throws IOException {
    if (this.state == 4) {
      StreamAllocation streamAllocation = this.streamAllocation;
      if (streamAllocation != null) {
        this.state = 5;
        streamAllocation.noNewStreams();
        return new UnknownLengthSource();
      } 
      throw new IllegalStateException("streamAllocation == null");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state: ");
    stringBuilder.append(this.state);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public ResponseBody openResponseBody(Response paramResponse) throws IOException {
    this.streamAllocation.eventListener.responseBodyStart(this.streamAllocation.call);
    String str = paramResponse.header("Content-Type");
    if (!HttpHeaders.hasBody(paramResponse))
      return (ResponseBody)new RealResponseBody(str, 0L, Okio.buffer(newFixedLengthSource(0L))); 
    if ("chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding")))
      return (ResponseBody)new RealResponseBody(str, -1L, Okio.buffer(newChunkedSource(paramResponse.request().url()))); 
    long l = HttpHeaders.contentLength(paramResponse);
    return (ResponseBody)((l != -1L) ? new RealResponseBody(str, l, Okio.buffer(newFixedLengthSource(l))) : new RealResponseBody(str, -1L, Okio.buffer(newUnknownLengthSource())));
  }
  
  public Headers readHeaders() throws IOException {
    Headers.Builder builder = new Headers.Builder();
    while (true) {
      String str = readHeaderLine();
      if (str.length() != 0) {
        Internal.instance.addLenient(builder, str);
        continue;
      } 
      return builder.build();
    } 
  }
  
  public Response.Builder readResponseHeaders(boolean paramBoolean) throws IOException {
    int i = this.state;
    if (i == 1 || i == 3)
      try {
        StatusLine statusLine = StatusLine.parse(readHeaderLine());
        Response.Builder builder = new Response.Builder();
        this();
        builder = builder.protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message).headers(readHeaders());
        if (paramBoolean && statusLine.code == 100)
          return null; 
        if (statusLine.code == 100) {
          this.state = 3;
          return builder;
        } 
        this.state = 4;
        return builder;
      } catch (EOFException eOFException) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("unexpected end of stream on ");
        stringBuilder1.append(this.streamAllocation);
        IOException iOException = new IOException(stringBuilder1.toString());
        iOException.initCause(eOFException);
        throw iOException;
      }  
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state: ");
    stringBuilder.append(this.state);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void writeRequest(Headers paramHeaders, String paramString) throws IOException {
    if (this.state == 0) {
      this.sink.writeUtf8(paramString).writeUtf8("\r\n");
      byte b = 0;
      int i = paramHeaders.size();
      while (b < i) {
        this.sink.writeUtf8(paramHeaders.name(b)).writeUtf8(": ").writeUtf8(paramHeaders.value(b)).writeUtf8("\r\n");
        b++;
      } 
      this.sink.writeUtf8("\r\n");
      this.state = 1;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state: ");
    stringBuilder.append(this.state);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void writeRequestHeaders(Request paramRequest) throws IOException {
    String str = RequestLine.get(paramRequest, this.streamAllocation.connection().route().proxy().type());
    writeRequest(paramRequest.headers(), str);
  }
  
  private abstract class AbstractSource implements Source {
    protected long bytesRead = 0L;
    
    protected boolean closed;
    
    final Http1Codec this$0;
    
    protected final ForwardingTimeout timeout = new ForwardingTimeout(Http1Codec.this.source.timeout());
    
    private AbstractSource() {}
    
    protected final void endOfInput(boolean param1Boolean, IOException param1IOException) throws IOException {
      if (Http1Codec.this.state == 6)
        return; 
      if (Http1Codec.this.state == 5) {
        Http1Codec.this.detachTimeout(this.timeout);
        Http1Codec.this.state = 6;
        if (Http1Codec.this.streamAllocation != null)
          Http1Codec.this.streamAllocation.streamFinished(param1Boolean ^ true, Http1Codec.this, this.bytesRead, param1IOException); 
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("state: ");
      stringBuilder.append(Http1Codec.this.state);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      try {
        param1Long = Http1Codec.this.source.read(param1Buffer, param1Long);
        if (param1Long > 0L)
          this.bytesRead += param1Long; 
        return param1Long;
      } catch (IOException iOException) {
        endOfInput(false, iOException);
        throw iOException;
      } 
    }
    
    public Timeout timeout() {
      return (Timeout)this.timeout;
    }
  }
  
  private final class ChunkedSink implements Sink {
    private boolean closed;
    
    final Http1Codec this$0;
    
    private final ForwardingTimeout timeout = new ForwardingTimeout(Http1Codec.this.sink.timeout());
    
    public void close() throws IOException {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield closed : Z
      //   6: istore_1
      //   7: iload_1
      //   8: ifeq -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_0
      //   15: iconst_1
      //   16: putfield closed : Z
      //   19: aload_0
      //   20: getfield this$0 : Lokhttp3/internal/http1/Http1Codec;
      //   23: getfield sink : Lokio/BufferedSink;
      //   26: ldc '0\\r\\n\\r\\n'
      //   28: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
      //   33: pop
      //   34: aload_0
      //   35: getfield this$0 : Lokhttp3/internal/http1/Http1Codec;
      //   38: aload_0
      //   39: getfield timeout : Lokio/ForwardingTimeout;
      //   42: invokevirtual detachTimeout : (Lokio/ForwardingTimeout;)V
      //   45: aload_0
      //   46: getfield this$0 : Lokhttp3/internal/http1/Http1Codec;
      //   49: iconst_3
      //   50: putfield state : I
      //   53: aload_0
      //   54: monitorexit
      //   55: return
      //   56: astore_2
      //   57: aload_0
      //   58: monitorexit
      //   59: aload_2
      //   60: athrow
      // Exception table:
      //   from	to	target	type
      //   2	7	56	finally
      //   14	53	56	finally
    }
    
    public void flush() throws IOException {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield closed : Z
      //   6: istore_1
      //   7: iload_1
      //   8: ifeq -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_0
      //   15: getfield this$0 : Lokhttp3/internal/http1/Http1Codec;
      //   18: getfield sink : Lokio/BufferedSink;
      //   21: invokeinterface flush : ()V
      //   26: aload_0
      //   27: monitorexit
      //   28: return
      //   29: astore_2
      //   30: aload_0
      //   31: monitorexit
      //   32: aload_2
      //   33: athrow
      // Exception table:
      //   from	to	target	type
      //   2	7	29	finally
      //   14	26	29	finally
    }
    
    public Timeout timeout() {
      return (Timeout)this.timeout;
    }
    
    public void write(Buffer param1Buffer, long param1Long) throws IOException {
      if (!this.closed) {
        if (param1Long == 0L)
          return; 
        Http1Codec.this.sink.writeHexadecimalUnsignedLong(param1Long);
        Http1Codec.this.sink.writeUtf8("\r\n");
        Http1Codec.this.sink.write(param1Buffer, param1Long);
        Http1Codec.this.sink.writeUtf8("\r\n");
        return;
      } 
      throw new IllegalStateException("closed");
    }
  }
  
  private class ChunkedSource extends AbstractSource {
    private static final long NO_CHUNK_YET = -1L;
    
    private long bytesRemainingInChunk = -1L;
    
    private boolean hasMoreChunks = true;
    
    final Http1Codec this$0;
    
    private final HttpUrl url;
    
    ChunkedSource(HttpUrl param1HttpUrl) {
      this.url = param1HttpUrl;
    }
    
    private void readChunkSize() throws IOException {
      if (this.bytesRemainingInChunk != -1L)
        Http1Codec.this.source.readUtf8LineStrict(); 
      try {
        this.bytesRemainingInChunk = Http1Codec.this.source.readHexadecimalUnsignedLong();
        String str = Http1Codec.this.source.readUtf8LineStrict().trim();
        if (this.bytesRemainingInChunk >= 0L) {
          if (!str.isEmpty()) {
            boolean bool = str.startsWith(";");
            if (bool) {
              if (this.bytesRemainingInChunk == 0L) {
                this.hasMoreChunks = false;
                HttpHeaders.receiveHeaders(Http1Codec.this.client.cookieJar(), this.url, Http1Codec.this.readHeaders());
                endOfInput(true, null);
              } 
              return;
            } 
            ProtocolException protocolException = new ProtocolException();
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("expected chunk size and optional extensions but was \"");
            stringBuilder.append(this.bytesRemainingInChunk);
            stringBuilder.append(str);
            stringBuilder.append("\"");
            this(stringBuilder.toString());
            throw protocolException;
          } 
        } else {
          ProtocolException protocolException = new ProtocolException();
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("expected chunk size and optional extensions but was \"");
          stringBuilder.append(this.bytesRemainingInChunk);
          stringBuilder.append(str);
          stringBuilder.append("\"");
          this(stringBuilder.toString());
          throw protocolException;
        } 
        if (this.bytesRemainingInChunk == 0L) {
          this.hasMoreChunks = false;
          HttpHeaders.receiveHeaders(Http1Codec.this.client.cookieJar(), this.url, Http1Codec.this.readHeaders());
          endOfInput(true, null);
        } 
        return;
      } catch (NumberFormatException numberFormatException) {
        throw new ProtocolException(numberFormatException.getMessage());
      } 
    }
    
    public void close() throws IOException {
      if (this.closed)
        return; 
      if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS))
        endOfInput(false, null); 
      this.closed = true;
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      if (param1Long >= 0L) {
        if (!this.closed) {
          if (!this.hasMoreChunks)
            return -1L; 
          long l = this.bytesRemainingInChunk;
          if (l == 0L || l == -1L) {
            readChunkSize();
            if (!this.hasMoreChunks)
              return -1L; 
          } 
          param1Long = super.read(param1Buffer, Math.min(param1Long, this.bytesRemainingInChunk));
          if (param1Long != -1L) {
            this.bytesRemainingInChunk -= param1Long;
            return param1Long;
          } 
          ProtocolException protocolException = new ProtocolException("unexpected end of stream");
          endOfInput(false, protocolException);
          throw protocolException;
        } 
        throw new IllegalStateException("closed");
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("byteCount < 0: ");
      stringBuilder.append(param1Long);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
  }
  
  private final class FixedLengthSink implements Sink {
    private long bytesRemaining;
    
    private boolean closed;
    
    final Http1Codec this$0;
    
    private final ForwardingTimeout timeout = new ForwardingTimeout(Http1Codec.this.sink.timeout());
    
    FixedLengthSink(long param1Long) {
      this.bytesRemaining = param1Long;
    }
    
    public void close() throws IOException {
      if (this.closed)
        return; 
      this.closed = true;
      if (this.bytesRemaining <= 0L) {
        Http1Codec.this.detachTimeout(this.timeout);
        Http1Codec.this.state = 3;
        return;
      } 
      throw new ProtocolException("unexpected end of stream");
    }
    
    public void flush() throws IOException {
      if (this.closed)
        return; 
      Http1Codec.this.sink.flush();
    }
    
    public Timeout timeout() {
      return (Timeout)this.timeout;
    }
    
    public void write(Buffer param1Buffer, long param1Long) throws IOException {
      if (!this.closed) {
        Util.checkOffsetAndCount(param1Buffer.size(), 0L, param1Long);
        if (param1Long <= this.bytesRemaining) {
          Http1Codec.this.sink.write(param1Buffer, param1Long);
          this.bytesRemaining -= param1Long;
          return;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("expected ");
        stringBuilder.append(this.bytesRemaining);
        stringBuilder.append(" bytes but received ");
        stringBuilder.append(param1Long);
        throw new ProtocolException(stringBuilder.toString());
      } 
      throw new IllegalStateException("closed");
    }
  }
  
  private class FixedLengthSource extends AbstractSource {
    private long bytesRemaining;
    
    final Http1Codec this$0;
    
    FixedLengthSource(long param1Long) throws IOException {
      this.bytesRemaining = param1Long;
      if (param1Long == 0L)
        endOfInput(true, null); 
    }
    
    public void close() throws IOException {
      if (this.closed)
        return; 
      if (this.bytesRemaining != 0L && !Util.discard(this, 100, TimeUnit.MILLISECONDS))
        endOfInput(false, null); 
      this.closed = true;
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      if (param1Long >= 0L) {
        if (!this.closed) {
          long l = this.bytesRemaining;
          if (l == 0L)
            return -1L; 
          param1Long = super.read(param1Buffer, Math.min(l, param1Long));
          if (param1Long != -1L) {
            l = this.bytesRemaining - param1Long;
            this.bytesRemaining = l;
            if (l == 0L)
              endOfInput(true, null); 
            return param1Long;
          } 
          ProtocolException protocolException = new ProtocolException("unexpected end of stream");
          endOfInput(false, protocolException);
          throw protocolException;
        } 
        throw new IllegalStateException("closed");
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("byteCount < 0: ");
      stringBuilder.append(param1Long);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
  }
  
  private class UnknownLengthSource extends AbstractSource {
    private boolean inputExhausted;
    
    final Http1Codec this$0;
    
    public void close() throws IOException {
      if (this.closed)
        return; 
      if (!this.inputExhausted)
        endOfInput(false, null); 
      this.closed = true;
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      if (param1Long >= 0L) {
        if (!this.closed) {
          if (this.inputExhausted)
            return -1L; 
          param1Long = super.read(param1Buffer, param1Long);
          if (param1Long == -1L) {
            this.inputExhausted = true;
            endOfInput(true, null);
            return -1L;
          } 
          return param1Long;
        } 
        throw new IllegalStateException("closed");
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("byteCount < 0: ");
      stringBuilder.append(param1Long);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http1\Http1Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */