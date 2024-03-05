package okhttp3.internal.http2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

final class Hpack {
  static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX;
  
  private static final int PREFIX_4_BITS = 15;
  
  private static final int PREFIX_5_BITS = 31;
  
  private static final int PREFIX_6_BITS = 63;
  
  private static final int PREFIX_7_BITS = 127;
  
  static final Header[] STATIC_HEADER_TABLE = new Header[] { 
      new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), 
      new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), 
      new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), 
      new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), 
      new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), 
      new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), 
      new Header("www-authenticate", "") };
  
  static {
    NAME_TO_FIRST_INDEX = nameToFirstIndex();
  }
  
  static ByteString checkLowercase(ByteString paramByteString) throws IOException {
    int i = paramByteString.size();
    byte b = 0;
    while (b < i) {
      byte b1 = paramByteString.getByte(b);
      if (b1 < 65 || b1 > 90) {
        b++;
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("PROTOCOL_ERROR response malformed: mixed case name: ");
      stringBuilder.append(paramByteString.utf8());
      throw new IOException(stringBuilder.toString());
    } 
    return paramByteString;
  }
  
  private static Map<ByteString, Integer> nameToFirstIndex() {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>(STATIC_HEADER_TABLE.length);
    byte b = 0;
    while (true) {
      Header[] arrayOfHeader = STATIC_HEADER_TABLE;
      if (b < arrayOfHeader.length) {
        if (!linkedHashMap.containsKey((arrayOfHeader[b]).name))
          linkedHashMap.put((STATIC_HEADER_TABLE[b]).name, Integer.valueOf(b)); 
        b++;
        continue;
      } 
      return (Map)Collections.unmodifiableMap(linkedHashMap);
    } 
  }
  
  static final class Reader {
    Header[] dynamicTable;
    
    int dynamicTableByteCount;
    
    int headerCount;
    
    private final List<Header> headerList = new ArrayList<Header>();
    
    private final int headerTableSizeSetting;
    
    private int maxDynamicTableByteCount;
    
    int nextHeaderIndex;
    
    private final BufferedSource source;
    
    Reader(int param1Int1, int param1Int2, Source param1Source) {
      Header[] arrayOfHeader = new Header[8];
      this.dynamicTable = arrayOfHeader;
      this.nextHeaderIndex = arrayOfHeader.length - 1;
      this.headerCount = 0;
      this.dynamicTableByteCount = 0;
      this.headerTableSizeSetting = param1Int1;
      this.maxDynamicTableByteCount = param1Int2;
      this.source = Okio.buffer(param1Source);
    }
    
    Reader(int param1Int, Source param1Source) {
      this(param1Int, param1Int, param1Source);
    }
    
    private void adjustDynamicTableByteCount() {
      int i = this.maxDynamicTableByteCount;
      int j = this.dynamicTableByteCount;
      if (i < j)
        if (i == 0) {
          clearDynamicTable();
        } else {
          evictToRecoverBytes(j - i);
        }  
    }
    
    private void clearDynamicTable() {
      Arrays.fill((Object[])this.dynamicTable, (Object)null);
      this.nextHeaderIndex = this.dynamicTable.length - 1;
      this.headerCount = 0;
      this.dynamicTableByteCount = 0;
    }
    
    private int dynamicTableIndex(int param1Int) {
      return this.nextHeaderIndex + 1 + param1Int;
    }
    
    private int evictToRecoverBytes(int param1Int) {
      int i = 0;
      boolean bool = false;
      if (param1Int > 0) {
        i = this.dynamicTable.length - 1;
        int j = param1Int;
        param1Int = bool;
        while (i >= this.nextHeaderIndex && j > 0) {
          j -= (this.dynamicTable[i]).hpackSize;
          this.dynamicTableByteCount -= (this.dynamicTable[i]).hpackSize;
          this.headerCount--;
          param1Int++;
          i--;
        } 
        Header[] arrayOfHeader = this.dynamicTable;
        i = this.nextHeaderIndex;
        System.arraycopy(arrayOfHeader, i + 1, arrayOfHeader, i + 1 + param1Int, this.headerCount);
        this.nextHeaderIndex += param1Int;
        i = param1Int;
      } 
      return i;
    }
    
    private ByteString getName(int param1Int) throws IOException {
      if (isStaticHeader(param1Int))
        return (Hpack.STATIC_HEADER_TABLE[param1Int]).name; 
      int i = dynamicTableIndex(param1Int - Hpack.STATIC_HEADER_TABLE.length);
      if (i >= 0) {
        Header[] arrayOfHeader = this.dynamicTable;
        if (i < arrayOfHeader.length)
          return (arrayOfHeader[i]).name; 
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Header index too large ");
      stringBuilder.append(param1Int + 1);
      throw new IOException(stringBuilder.toString());
    }
    
    private void insertIntoDynamicTable(int param1Int, Header param1Header) {
      this.headerList.add(param1Header);
      int j = param1Header.hpackSize;
      int i = j;
      if (param1Int != -1)
        i = j - (this.dynamicTable[dynamicTableIndex(param1Int)]).hpackSize; 
      j = this.maxDynamicTableByteCount;
      if (i > j) {
        clearDynamicTable();
        return;
      } 
      j = evictToRecoverBytes(this.dynamicTableByteCount + i - j);
      if (param1Int == -1) {
        param1Int = this.headerCount;
        Header[] arrayOfHeader = this.dynamicTable;
        if (param1Int + 1 > arrayOfHeader.length) {
          Header[] arrayOfHeader1 = new Header[arrayOfHeader.length * 2];
          System.arraycopy(arrayOfHeader, 0, arrayOfHeader1, arrayOfHeader.length, arrayOfHeader.length);
          this.nextHeaderIndex = this.dynamicTable.length - 1;
          this.dynamicTable = arrayOfHeader1;
        } 
        param1Int = this.nextHeaderIndex;
        this.nextHeaderIndex = param1Int - 1;
        this.dynamicTable[param1Int] = param1Header;
        this.headerCount++;
      } else {
        int k = dynamicTableIndex(param1Int);
        this.dynamicTable[param1Int + k + j] = param1Header;
      } 
      this.dynamicTableByteCount += i;
    }
    
    private boolean isStaticHeader(int param1Int) {
      boolean bool = true;
      if (param1Int < 0 || param1Int > Hpack.STATIC_HEADER_TABLE.length - 1)
        bool = false; 
      return bool;
    }
    
    private int readByte() throws IOException {
      return this.source.readByte() & 0xFF;
    }
    
    private void readIndexedHeader(int param1Int) throws IOException {
      if (isStaticHeader(param1Int)) {
        Header header = Hpack.STATIC_HEADER_TABLE[param1Int];
        this.headerList.add(header);
      } else {
        int i = dynamicTableIndex(param1Int - Hpack.STATIC_HEADER_TABLE.length);
        if (i >= 0) {
          Header[] arrayOfHeader = this.dynamicTable;
          if (i < arrayOfHeader.length) {
            this.headerList.add(arrayOfHeader[i]);
            return;
          } 
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Header index too large ");
        stringBuilder.append(param1Int + 1);
        throw new IOException(stringBuilder.toString());
      } 
    }
    
    private void readLiteralHeaderWithIncrementalIndexingIndexedName(int param1Int) throws IOException {
      insertIntoDynamicTable(-1, new Header(getName(param1Int), readByteString()));
    }
    
    private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
      insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(readByteString()), readByteString()));
    }
    
    private void readLiteralHeaderWithoutIndexingIndexedName(int param1Int) throws IOException {
      ByteString byteString1 = getName(param1Int);
      ByteString byteString2 = readByteString();
      this.headerList.add(new Header(byteString1, byteString2));
    }
    
    private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
      ByteString byteString1 = Hpack.checkLowercase(readByteString());
      ByteString byteString2 = readByteString();
      this.headerList.add(new Header(byteString1, byteString2));
    }
    
    public List<Header> getAndResetHeaderList() {
      ArrayList<Header> arrayList = new ArrayList<Header>(this.headerList);
      this.headerList.clear();
      return arrayList;
    }
    
    int maxDynamicTableByteCount() {
      return this.maxDynamicTableByteCount;
    }
    
    ByteString readByteString() throws IOException {
      boolean bool;
      int i = readByte();
      if ((i & 0x80) == 128) {
        bool = true;
      } else {
        bool = false;
      } 
      i = readInt(i, 127);
      return bool ? ByteString.of(Huffman.get().decode(this.source.readByteArray(i))) : this.source.readByteString(i);
    }
    
    void readHeaders() throws IOException {
      while (!this.source.exhausted()) {
        int i = this.source.readByte() & 0xFF;
        if (i != 128) {
          if ((i & 0x80) == 128) {
            readIndexedHeader(readInt(i, 127) - 1);
            continue;
          } 
          if (i == 64) {
            readLiteralHeaderWithIncrementalIndexingNewName();
            continue;
          } 
          if ((i & 0x40) == 64) {
            readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(i, 63) - 1);
            continue;
          } 
          if ((i & 0x20) == 32) {
            i = readInt(i, 31);
            this.maxDynamicTableByteCount = i;
            if (i >= 0 && i <= this.headerTableSizeSetting) {
              adjustDynamicTableByteCount();
              continue;
            } 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid dynamic table size update ");
            stringBuilder.append(this.maxDynamicTableByteCount);
            throw new IOException(stringBuilder.toString());
          } 
          if (i == 16 || i == 0) {
            readLiteralHeaderWithoutIndexingNewName();
            continue;
          } 
          readLiteralHeaderWithoutIndexingIndexedName(readInt(i, 15) - 1);
          continue;
        } 
        throw new IOException("index == 0");
      } 
    }
    
    int readInt(int param1Int1, int param1Int2) throws IOException {
      param1Int1 &= param1Int2;
      if (param1Int1 < param1Int2)
        return param1Int1; 
      param1Int1 = 0;
      while (true) {
        int i = readByte();
        if ((i & 0x80) != 0) {
          param1Int2 += (i & 0x7F) << param1Int1;
          param1Int1 += 7;
          continue;
        } 
        return param1Int2 + (i << param1Int1);
      } 
    }
  }
  
  static final class Writer {
    private static final int SETTINGS_HEADER_TABLE_SIZE = 4096;
    
    private static final int SETTINGS_HEADER_TABLE_SIZE_LIMIT = 16384;
    
    Header[] dynamicTable;
    
    int dynamicTableByteCount;
    
    private boolean emitDynamicTableSizeUpdate;
    
    int headerCount;
    
    int headerTableSizeSetting;
    
    int maxDynamicTableByteCount;
    
    int nextHeaderIndex;
    
    private final Buffer out;
    
    private int smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
    
    private final boolean useCompression;
    
    Writer(int param1Int, boolean param1Boolean, Buffer param1Buffer) {
      Header[] arrayOfHeader = new Header[8];
      this.dynamicTable = arrayOfHeader;
      this.nextHeaderIndex = arrayOfHeader.length - 1;
      this.headerCount = 0;
      this.dynamicTableByteCount = 0;
      this.headerTableSizeSetting = param1Int;
      this.maxDynamicTableByteCount = param1Int;
      this.useCompression = param1Boolean;
      this.out = param1Buffer;
    }
    
    Writer(Buffer param1Buffer) {
      this(4096, true, param1Buffer);
    }
    
    private void adjustDynamicTableByteCount() {
      int i = this.maxDynamicTableByteCount;
      int j = this.dynamicTableByteCount;
      if (i < j)
        if (i == 0) {
          clearDynamicTable();
        } else {
          evictToRecoverBytes(j - i);
        }  
    }
    
    private void clearDynamicTable() {
      Arrays.fill((Object[])this.dynamicTable, (Object)null);
      this.nextHeaderIndex = this.dynamicTable.length - 1;
      this.headerCount = 0;
      this.dynamicTableByteCount = 0;
    }
    
    private int evictToRecoverBytes(int param1Int) {
      int i = 0;
      boolean bool = false;
      if (param1Int > 0) {
        i = this.dynamicTable.length - 1;
        int j = param1Int;
        param1Int = bool;
        while (i >= this.nextHeaderIndex && j > 0) {
          j -= (this.dynamicTable[i]).hpackSize;
          this.dynamicTableByteCount -= (this.dynamicTable[i]).hpackSize;
          this.headerCount--;
          param1Int++;
          i--;
        } 
        Header[] arrayOfHeader = this.dynamicTable;
        i = this.nextHeaderIndex;
        System.arraycopy(arrayOfHeader, i + 1, arrayOfHeader, i + 1 + param1Int, this.headerCount);
        arrayOfHeader = this.dynamicTable;
        i = this.nextHeaderIndex;
        Arrays.fill((Object[])arrayOfHeader, i + 1, i + 1 + param1Int, (Object)null);
        this.nextHeaderIndex += param1Int;
        i = param1Int;
      } 
      return i;
    }
    
    private void insertIntoDynamicTable(Header param1Header) {
      int i = param1Header.hpackSize;
      int j = this.maxDynamicTableByteCount;
      if (i > j) {
        clearDynamicTable();
        return;
      } 
      evictToRecoverBytes(this.dynamicTableByteCount + i - j);
      j = this.headerCount;
      Header[] arrayOfHeader = this.dynamicTable;
      if (j + 1 > arrayOfHeader.length) {
        Header[] arrayOfHeader1 = new Header[arrayOfHeader.length * 2];
        System.arraycopy(arrayOfHeader, 0, arrayOfHeader1, arrayOfHeader.length, arrayOfHeader.length);
        this.nextHeaderIndex = this.dynamicTable.length - 1;
        this.dynamicTable = arrayOfHeader1;
      } 
      j = this.nextHeaderIndex;
      this.nextHeaderIndex = j - 1;
      this.dynamicTable[j] = param1Header;
      this.headerCount++;
      this.dynamicTableByteCount += i;
    }
    
    void setHeaderTableSizeSetting(int param1Int) {
      this.headerTableSizeSetting = param1Int;
      int i = Math.min(param1Int, 16384);
      param1Int = this.maxDynamicTableByteCount;
      if (param1Int == i)
        return; 
      if (i < param1Int)
        this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, i); 
      this.emitDynamicTableSizeUpdate = true;
      this.maxDynamicTableByteCount = i;
      adjustDynamicTableByteCount();
    }
    
    void writeByteString(ByteString param1ByteString) throws IOException {
      if (this.useCompression && Huffman.get().encodedLength(param1ByteString) < param1ByteString.size()) {
        Buffer buffer = new Buffer();
        Huffman.get().encode(param1ByteString, (BufferedSink)buffer);
        param1ByteString = buffer.readByteString();
        writeInt(param1ByteString.size(), 127, 128);
        this.out.write(param1ByteString);
      } else {
        writeInt(param1ByteString.size(), 127, 0);
        this.out.write(param1ByteString);
      } 
    }
    
    void writeHeaders(List<Header> param1List) throws IOException {
      // Byte code:
      //   0: aload_0
      //   1: getfield emitDynamicTableSizeUpdate : Z
      //   4: ifeq -> 52
      //   7: aload_0
      //   8: getfield smallestHeaderTableSizeSetting : I
      //   11: istore_2
      //   12: iload_2
      //   13: aload_0
      //   14: getfield maxDynamicTableByteCount : I
      //   17: if_icmpge -> 29
      //   20: aload_0
      //   21: iload_2
      //   22: bipush #31
      //   24: bipush #32
      //   26: invokevirtual writeInt : (III)V
      //   29: aload_0
      //   30: iconst_0
      //   31: putfield emitDynamicTableSizeUpdate : Z
      //   34: aload_0
      //   35: ldc 2147483647
      //   37: putfield smallestHeaderTableSizeSetting : I
      //   40: aload_0
      //   41: aload_0
      //   42: getfield maxDynamicTableByteCount : I
      //   45: bipush #31
      //   47: bipush #32
      //   49: invokevirtual writeInt : (III)V
      //   52: aload_1
      //   53: invokeinterface size : ()I
      //   58: istore #8
      //   60: iconst_0
      //   61: istore #4
      //   63: iload #4
      //   65: iload #8
      //   67: if_icmpge -> 453
      //   70: aload_1
      //   71: iload #4
      //   73: invokeinterface get : (I)Ljava/lang/Object;
      //   78: checkcast okhttp3/internal/http2/Header
      //   81: astore #10
      //   83: aload #10
      //   85: getfield name : Lokio/ByteString;
      //   88: invokevirtual toAsciiLowercase : ()Lokio/ByteString;
      //   91: astore #12
      //   93: aload #10
      //   95: getfield value : Lokio/ByteString;
      //   98: astore #13
      //   100: getstatic okhttp3/internal/http2/Hpack.NAME_TO_FIRST_INDEX : Ljava/util/Map;
      //   103: aload #12
      //   105: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   110: checkcast java/lang/Integer
      //   113: astore #11
      //   115: aload #11
      //   117: ifnull -> 193
      //   120: aload #11
      //   122: invokevirtual intValue : ()I
      //   125: iconst_1
      //   126: iadd
      //   127: istore_3
      //   128: iload_3
      //   129: iconst_1
      //   130: if_icmple -> 186
      //   133: iload_3
      //   134: bipush #8
      //   136: if_icmpge -> 186
      //   139: getstatic okhttp3/internal/http2/Hpack.STATIC_HEADER_TABLE : [Lokhttp3/internal/http2/Header;
      //   142: iload_3
      //   143: iconst_1
      //   144: isub
      //   145: aaload
      //   146: getfield value : Lokio/ByteString;
      //   149: aload #13
      //   151: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   154: ifeq -> 162
      //   157: iload_3
      //   158: istore_2
      //   159: goto -> 197
      //   162: getstatic okhttp3/internal/http2/Hpack.STATIC_HEADER_TABLE : [Lokhttp3/internal/http2/Header;
      //   165: iload_3
      //   166: aaload
      //   167: getfield value : Lokio/ByteString;
      //   170: aload #13
      //   172: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   175: ifeq -> 186
      //   178: iload_3
      //   179: istore_2
      //   180: iinc #3, 1
      //   183: goto -> 197
      //   186: iload_3
      //   187: istore_2
      //   188: iconst_m1
      //   189: istore_3
      //   190: goto -> 197
      //   193: iconst_m1
      //   194: istore_3
      //   195: iconst_m1
      //   196: istore_2
      //   197: iload_3
      //   198: istore #7
      //   200: iload_2
      //   201: istore #6
      //   203: iload_3
      //   204: iconst_m1
      //   205: if_icmpne -> 328
      //   208: aload_0
      //   209: getfield nextHeaderIndex : I
      //   212: iconst_1
      //   213: iadd
      //   214: istore #5
      //   216: aload_0
      //   217: getfield dynamicTable : [Lokhttp3/internal/http2/Header;
      //   220: arraylength
      //   221: istore #9
      //   223: iload_3
      //   224: istore #7
      //   226: iload_2
      //   227: istore #6
      //   229: iload #5
      //   231: iload #9
      //   233: if_icmpge -> 328
      //   236: iload_2
      //   237: istore #6
      //   239: aload_0
      //   240: getfield dynamicTable : [Lokhttp3/internal/http2/Header;
      //   243: iload #5
      //   245: aaload
      //   246: getfield name : Lokio/ByteString;
      //   249: aload #12
      //   251: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   254: ifeq -> 319
      //   257: aload_0
      //   258: getfield dynamicTable : [Lokhttp3/internal/http2/Header;
      //   261: iload #5
      //   263: aaload
      //   264: getfield value : Lokio/ByteString;
      //   267: aload #13
      //   269: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   272: ifeq -> 297
      //   275: aload_0
      //   276: getfield nextHeaderIndex : I
      //   279: istore_3
      //   280: getstatic okhttp3/internal/http2/Hpack.STATIC_HEADER_TABLE : [Lokhttp3/internal/http2/Header;
      //   283: arraylength
      //   284: iload #5
      //   286: iload_3
      //   287: isub
      //   288: iadd
      //   289: istore #7
      //   291: iload_2
      //   292: istore #6
      //   294: goto -> 328
      //   297: iload_2
      //   298: istore #6
      //   300: iload_2
      //   301: iconst_m1
      //   302: if_icmpne -> 319
      //   305: iload #5
      //   307: aload_0
      //   308: getfield nextHeaderIndex : I
      //   311: isub
      //   312: getstatic okhttp3/internal/http2/Hpack.STATIC_HEADER_TABLE : [Lokhttp3/internal/http2/Header;
      //   315: arraylength
      //   316: iadd
      //   317: istore #6
      //   319: iinc #5, 1
      //   322: iload #6
      //   324: istore_2
      //   325: goto -> 223
      //   328: iload #7
      //   330: iconst_m1
      //   331: if_icmpeq -> 348
      //   334: aload_0
      //   335: iload #7
      //   337: bipush #127
      //   339: sipush #128
      //   342: invokevirtual writeInt : (III)V
      //   345: goto -> 447
      //   348: iload #6
      //   350: iconst_m1
      //   351: if_icmpne -> 385
      //   354: aload_0
      //   355: getfield out : Lokio/Buffer;
      //   358: bipush #64
      //   360: invokevirtual writeByte : (I)Lokio/Buffer;
      //   363: pop
      //   364: aload_0
      //   365: aload #12
      //   367: invokevirtual writeByteString : (Lokio/ByteString;)V
      //   370: aload_0
      //   371: aload #13
      //   373: invokevirtual writeByteString : (Lokio/ByteString;)V
      //   376: aload_0
      //   377: aload #10
      //   379: invokespecial insertIntoDynamicTable : (Lokhttp3/internal/http2/Header;)V
      //   382: goto -> 447
      //   385: aload #12
      //   387: getstatic okhttp3/internal/http2/Header.PSEUDO_PREFIX : Lokio/ByteString;
      //   390: invokevirtual startsWith : (Lokio/ByteString;)Z
      //   393: ifeq -> 425
      //   396: getstatic okhttp3/internal/http2/Header.TARGET_AUTHORITY : Lokio/ByteString;
      //   399: aload #12
      //   401: invokevirtual equals : (Ljava/lang/Object;)Z
      //   404: ifne -> 425
      //   407: aload_0
      //   408: iload #6
      //   410: bipush #15
      //   412: iconst_0
      //   413: invokevirtual writeInt : (III)V
      //   416: aload_0
      //   417: aload #13
      //   419: invokevirtual writeByteString : (Lokio/ByteString;)V
      //   422: goto -> 447
      //   425: aload_0
      //   426: iload #6
      //   428: bipush #63
      //   430: bipush #64
      //   432: invokevirtual writeInt : (III)V
      //   435: aload_0
      //   436: aload #13
      //   438: invokevirtual writeByteString : (Lokio/ByteString;)V
      //   441: aload_0
      //   442: aload #10
      //   444: invokespecial insertIntoDynamicTable : (Lokhttp3/internal/http2/Header;)V
      //   447: iinc #4, 1
      //   450: goto -> 63
      //   453: return
    }
    
    void writeInt(int param1Int1, int param1Int2, int param1Int3) {
      if (param1Int1 < param1Int2) {
        this.out.writeByte(param1Int1 | param1Int3);
        return;
      } 
      this.out.writeByte(param1Int3 | param1Int2);
      for (param1Int1 -= param1Int2; param1Int1 >= 128; param1Int1 >>>= 7)
        this.out.writeByte(0x80 | param1Int1 & 0x7F); 
      this.out.writeByte(param1Int1);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\Hpack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */