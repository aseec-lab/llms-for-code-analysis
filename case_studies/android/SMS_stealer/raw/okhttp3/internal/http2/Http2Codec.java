package okhttp3.internal.http2;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.ByteString;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class Http2Codec implements HttpCodec {
  private static final ByteString CONNECTION = ByteString.encodeUtf8("connection");
  
  private static final ByteString ENCODING;
  
  private static final ByteString HOST = ByteString.encodeUtf8("host");
  
  private static final List<ByteString> HTTP_2_SKIPPED_REQUEST_HEADERS;
  
  private static final List<ByteString> HTTP_2_SKIPPED_RESPONSE_HEADERS;
  
  private static final ByteString KEEP_ALIVE = ByteString.encodeUtf8("keep-alive");
  
  private static final ByteString PROXY_CONNECTION = ByteString.encodeUtf8("proxy-connection");
  
  private static final ByteString TE;
  
  private static final ByteString TRANSFER_ENCODING = ByteString.encodeUtf8("transfer-encoding");
  
  private static final ByteString UPGRADE;
  
  private final Interceptor.Chain chain;
  
  private final OkHttpClient client;
  
  private final Http2Connection connection;
  
  private Http2Stream stream;
  
  final StreamAllocation streamAllocation;
  
  static {
    TE = ByteString.encodeUtf8("te");
    ENCODING = ByteString.encodeUtf8("encoding");
    ByteString byteString = ByteString.encodeUtf8("upgrade");
    UPGRADE = byteString;
    HTTP_2_SKIPPED_REQUEST_HEADERS = Util.immutableList((Object[])new ByteString[] { 
          CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, byteString, Header.TARGET_METHOD, Header.TARGET_PATH, 
          Header.TARGET_SCHEME, Header.TARGET_AUTHORITY });
    HTTP_2_SKIPPED_RESPONSE_HEADERS = Util.immutableList((Object[])new ByteString[] { CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, UPGRADE });
  }
  
  public Http2Codec(OkHttpClient paramOkHttpClient, Interceptor.Chain paramChain, StreamAllocation paramStreamAllocation, Http2Connection paramHttp2Connection) {
    this.client = paramOkHttpClient;
    this.chain = paramChain;
    this.streamAllocation = paramStreamAllocation;
    this.connection = paramHttp2Connection;
  }
  
  public static List<Header> http2HeadersList(Request paramRequest) {
    Headers headers = paramRequest.headers();
    ArrayList<Header> arrayList = new ArrayList(headers.size() + 4);
    arrayList.add(new Header(Header.TARGET_METHOD, paramRequest.method()));
    arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(paramRequest.url())));
    String str = paramRequest.header("Host");
    if (str != null)
      arrayList.add(new Header(Header.TARGET_AUTHORITY, str)); 
    arrayList.add(new Header(Header.TARGET_SCHEME, paramRequest.url().scheme()));
    byte b = 0;
    int i = headers.size();
    while (b < i) {
      ByteString byteString = ByteString.encodeUtf8(headers.name(b).toLowerCase(Locale.US));
      if (!HTTP_2_SKIPPED_REQUEST_HEADERS.contains(byteString))
        arrayList.add(new Header(byteString, headers.value(b))); 
      b++;
    } 
    return arrayList;
  }
  
  public static Response.Builder readHttp2HeadersList(List<Header> paramList) throws IOException {
    Headers.Builder builder = new Headers.Builder();
    int i = paramList.size();
    byte b = 0;
    Header header;
    for (header = null; b < i; header = header1) {
      Headers.Builder builder1;
      Header header1 = paramList.get(b);
      if (header1 == null) {
        builder1 = builder;
        header1 = header;
        if (header != null) {
          builder1 = builder;
          header1 = header;
          if (((StatusLine)header).code == 100) {
            builder1 = new Headers.Builder();
            header1 = null;
          } 
        } 
      } else {
        ByteString byteString = header1.name;
        String str = header1.value.utf8();
        if (byteString.equals(Header.RESPONSE_STATUS)) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("HTTP/1.1 ");
          stringBuilder.append(str);
          StatusLine statusLine = StatusLine.parse(stringBuilder.toString());
          builder1 = builder;
        } else {
          builder1 = builder;
          header1 = header;
          if (!HTTP_2_SKIPPED_RESPONSE_HEADERS.contains(byteString)) {
            Internal.instance.addLenient(builder, byteString.utf8(), str);
            header1 = header;
            builder1 = builder;
          } 
        } 
      } 
      b++;
      builder = builder1;
    } 
    if (header != null)
      return (new Response.Builder()).protocol(Protocol.HTTP_2).code(((StatusLine)header).code).message(((StatusLine)header).message).headers(builder.build()); 
    throw new ProtocolException("Expected ':status' header not present");
  }
  
  public void cancel() {
    Http2Stream http2Stream = this.stream;
    if (http2Stream != null)
      http2Stream.closeLater(ErrorCode.CANCEL); 
  }
  
  public Sink createRequestBody(Request paramRequest, long paramLong) {
    return this.stream.getSink();
  }
  
  public void finishRequest() throws IOException {
    this.stream.getSink().close();
  }
  
  public void flushRequest() throws IOException {
    this.connection.flush();
  }
  
  public ResponseBody openResponseBody(Response paramResponse) throws IOException {
    this.streamAllocation.eventListener.responseBodyStart(this.streamAllocation.call);
    return (ResponseBody)new RealResponseBody(paramResponse.header("Content-Type"), HttpHeaders.contentLength(paramResponse), Okio.buffer((Source)new StreamFinishingSource(this.stream.getSource())));
  }
  
  public Response.Builder readResponseHeaders(boolean paramBoolean) throws IOException {
    Response.Builder builder = readHttp2HeadersList(this.stream.takeResponseHeaders());
    return (paramBoolean && Internal.instance.code(builder) == 100) ? null : builder;
  }
  
  public void writeRequestHeaders(Request paramRequest) throws IOException {
    boolean bool;
    if (this.stream != null)
      return; 
    if (paramRequest.body() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    List<Header> list = http2HeadersList(paramRequest);
    Http2Stream http2Stream = this.connection.newStream(list, bool);
    this.stream = http2Stream;
    http2Stream.readTimeout().timeout(this.chain.readTimeoutMillis(), TimeUnit.MILLISECONDS);
    this.stream.writeTimeout().timeout(this.chain.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
  }
  
  class StreamFinishingSource extends ForwardingSource {
    long bytesRead = 0L;
    
    boolean completed = false;
    
    final Http2Codec this$0;
    
    StreamFinishingSource(Source param1Source) {
      super(param1Source);
    }
    
    private void endOfInput(IOException param1IOException) {
      if (this.completed)
        return; 
      this.completed = true;
      Http2Codec.this.streamAllocation.streamFinished(false, Http2Codec.this, this.bytesRead, param1IOException);
    }
    
    public void close() throws IOException {
      super.close();
      endOfInput(null);
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      try {
        param1Long = delegate().read(param1Buffer, param1Long);
        if (param1Long > 0L)
          this.bytesRead += param1Long; 
        return param1Long;
      } catch (IOException iOException) {
        endOfInput(iOException);
        throw iOException;
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\Http2Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */