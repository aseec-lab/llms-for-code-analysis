package okio;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class Buffer implements BufferedSource, BufferedSink, Cloneable, ByteChannel {
  private static final byte[] DIGITS = new byte[] { 
      48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 
      97, 98, 99, 100, 101, 102 };
  
  static final int REPLACEMENT_CHARACTER = 65533;
  
  @Nullable
  Segment head;
  
  long size;
  
  private ByteString digest(String paramString) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(paramString);
      if (this.head != null) {
        messageDigest.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
        Segment segment = this.head;
        while (true) {
          segment = segment.next;
          if (segment != this.head) {
            messageDigest.update(segment.data, segment.pos, segment.limit - segment.pos);
            continue;
          } 
          break;
        } 
      } 
      return ByteString.of(messageDigest.digest());
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new AssertionError();
    } 
  }
  
  private ByteString hmac(String paramString, ByteString paramByteString) {
    try {
      Mac mac = Mac.getInstance(paramString);
      SecretKeySpec secretKeySpec = new SecretKeySpec();
      this(paramByteString.toByteArray(), paramString);
      mac.init(secretKeySpec);
      if (this.head != null) {
        mac.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
        Segment segment = this.head;
        while (true) {
          segment = segment.next;
          if (segment != this.head) {
            mac.update(segment.data, segment.pos, segment.limit - segment.pos);
            continue;
          } 
          break;
        } 
      } 
      return ByteString.of(mac.doFinal());
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new AssertionError();
    } catch (InvalidKeyException invalidKeyException) {
      throw new IllegalArgumentException(invalidKeyException);
    } 
  }
  
  private boolean rangeEquals(Segment paramSegment, int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3) {
    int i = paramSegment.limit;
    byte[] arrayOfByte = paramSegment.data;
    for (Segment segment = paramSegment; paramInt2 < paramInt3; segment = paramSegment) {
      int j = i;
      paramSegment = segment;
      int k = paramInt1;
      if (paramInt1 == i) {
        paramSegment = segment.next;
        arrayOfByte = paramSegment.data;
        k = paramSegment.pos;
        j = paramSegment.limit;
      } 
      if (arrayOfByte[k] != paramByteString.getByte(paramInt2))
        return false; 
      paramInt1 = k + 1;
      paramInt2++;
      i = j;
    } 
    return true;
  }
  
  private void readFrom(InputStream paramInputStream, long paramLong, boolean paramBoolean) throws IOException {
    if (paramInputStream != null)
      while (true) {
        if (paramLong > 0L || paramBoolean) {
          Segment segment = writableSegment(1);
          int i = (int)Math.min(paramLong, (8192 - segment.limit));
          i = paramInputStream.read(segment.data, segment.limit, i);
          if (i == -1) {
            if (paramBoolean)
              return; 
            throw new EOFException();
          } 
          segment.limit += i;
          long l1 = this.size;
          long l2 = i;
          this.size = l1 + l2;
          paramLong -= l2;
          continue;
        } 
        return;
      }  
    throw new IllegalArgumentException("in == null");
  }
  
  public Buffer buffer() {
    return this;
  }
  
  public void clear() {
    try {
      skip(this.size);
      return;
    } catch (EOFException eOFException) {
      throw new AssertionError(eOFException);
    } 
  }
  
  public Buffer clone() {
    Buffer buffer = new Buffer();
    if (this.size == 0L)
      return buffer; 
    Segment segment = this.head.sharedCopy();
    buffer.head = segment;
    segment.prev = segment;
    segment.next = segment;
    segment = this.head;
    while (true) {
      segment = segment.next;
      if (segment != this.head) {
        buffer.head.prev.push(segment.sharedCopy());
        continue;
      } 
      buffer.size = this.size;
      return buffer;
    } 
  }
  
  public void close() {}
  
  public long completeSegmentByteCount() {
    long l2 = this.size;
    if (l2 == 0L)
      return 0L; 
    Segment segment = this.head.prev;
    long l1 = l2;
    if (segment.limit < 8192) {
      l1 = l2;
      if (segment.owner)
        l1 = l2 - (segment.limit - segment.pos); 
    } 
    return l1;
  }
  
  public Buffer copyTo(OutputStream paramOutputStream) throws IOException {
    return copyTo(paramOutputStream, 0L, this.size);
  }
  
  public Buffer copyTo(OutputStream paramOutputStream, long paramLong1, long paramLong2) throws IOException {
    if (paramOutputStream != null) {
      long l1;
      long l2;
      Segment segment2;
      Util.checkOffsetAndCount(this.size, paramLong1, paramLong2);
      if (paramLong2 == 0L)
        return this; 
      Segment segment1 = this.head;
      while (true) {
        segment2 = segment1;
        l1 = paramLong1;
        l2 = paramLong2;
        if (paramLong1 >= (segment1.limit - segment1.pos)) {
          paramLong1 -= (segment1.limit - segment1.pos);
          segment1 = segment1.next;
          continue;
        } 
        break;
      } 
      while (l2 > 0L) {
        int i = (int)(segment2.pos + l1);
        int j = (int)Math.min((segment2.limit - i), l2);
        paramOutputStream.write(segment2.data, i, j);
        l2 -= j;
        segment2 = segment2.next;
        l1 = 0L;
      } 
      return this;
    } 
    throw new IllegalArgumentException("out == null");
  }
  
  public Buffer copyTo(Buffer paramBuffer, long paramLong1, long paramLong2) {
    if (paramBuffer != null) {
      long l1;
      long l2;
      Segment segment2;
      Util.checkOffsetAndCount(this.size, paramLong1, paramLong2);
      if (paramLong2 == 0L)
        return this; 
      paramBuffer.size += paramLong2;
      Segment segment1 = this.head;
      while (true) {
        segment2 = segment1;
        l1 = paramLong1;
        l2 = paramLong2;
        if (paramLong1 >= (segment1.limit - segment1.pos)) {
          paramLong1 -= (segment1.limit - segment1.pos);
          segment1 = segment1.next;
          continue;
        } 
        break;
      } 
      while (l2 > 0L) {
        segment1 = segment2.sharedCopy();
        segment1.pos = (int)(segment1.pos + l1);
        segment1.limit = Math.min(segment1.pos + (int)l2, segment1.limit);
        Segment segment = paramBuffer.head;
        if (segment == null) {
          segment1.prev = segment1;
          segment1.next = segment1;
          paramBuffer.head = segment1;
        } else {
          segment.prev.push(segment1);
        } 
        l2 -= (segment1.limit - segment1.pos);
        segment2 = segment2.next;
        l1 = 0L;
      } 
      return this;
    } 
    throw new IllegalArgumentException("out == null");
  }
  
  public BufferedSink emit() {
    return this;
  }
  
  public Buffer emitCompleteSegments() {
    return this;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof Buffer))
      return false; 
    paramObject = paramObject;
    long l2 = this.size;
    if (l2 != ((Buffer)paramObject).size)
      return false; 
    long l1 = 0L;
    if (l2 == 0L)
      return true; 
    Segment segment = this.head;
    paramObject = ((Buffer)paramObject).head;
    int j = segment.pos;
    int i = ((Segment)paramObject).pos;
    while (l1 < this.size) {
      l2 = Math.min(segment.limit - j, ((Segment)paramObject).limit - i);
      int k = 0;
      while (k < l2) {
        if (segment.data[j] != ((Segment)paramObject).data[i])
          return false; 
        k++;
        j++;
        i++;
      } 
      Segment segment1 = segment;
      k = j;
      if (j == segment.limit) {
        segment1 = segment.next;
        k = segment1.pos;
      } 
      int m = i;
      Object object = paramObject;
      if (i == ((Segment)paramObject).limit) {
        object = ((Segment)paramObject).next;
        m = ((Segment)object).pos;
      } 
      l1 += l2;
      segment = segment1;
      j = k;
      i = m;
      paramObject = object;
    } 
    return true;
  }
  
  public boolean exhausted() {
    boolean bool;
    if (this.size == 0L) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void flush() {}
  
  public byte getByte(long paramLong) {
    Util.checkOffsetAndCount(this.size, paramLong, 1L);
    long l = this.size;
    if (l - paramLong > paramLong)
      for (Segment segment1 = this.head;; segment1 = segment1.next) {
        l = (segment1.limit - segment1.pos);
        if (paramLong < l)
          return segment1.data[segment1.pos + (int)paramLong]; 
        paramLong -= l;
      }  
    paramLong -= l;
    Segment segment = this.head;
    while (true) {
      Segment segment1 = segment.prev;
      l = paramLong + (segment1.limit - segment1.pos);
      segment = segment1;
      paramLong = l;
      if (l >= 0L)
        return segment1.data[segment1.pos + (int)l]; 
    } 
  }
  
  public int hashCode() {
    Segment segment = this.head;
    if (segment == null)
      return 0; 
    int i = 1;
    while (true) {
      int j = segment.pos;
      int m = segment.limit;
      int k = i;
      while (j < m) {
        k = k * 31 + segment.data[j];
        j++;
      } 
      Segment segment1 = segment.next;
      segment = segment1;
      i = k;
      if (segment1 == this.head)
        return k; 
    } 
  }
  
  public ByteString hmacSha1(ByteString paramByteString) {
    return hmac("HmacSHA1", paramByteString);
  }
  
  public ByteString hmacSha256(ByteString paramByteString) {
    return hmac("HmacSHA256", paramByteString);
  }
  
  public ByteString hmacSha512(ByteString paramByteString) {
    return hmac("HmacSHA512", paramByteString);
  }
  
  public long indexOf(byte paramByte) {
    return indexOf(paramByte, 0L, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong) {
    return indexOf(paramByte, paramLong, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong1, long paramLong2) {
    // Byte code:
    //   0: lconst_0
    //   1: lstore #8
    //   3: lload_2
    //   4: lconst_0
    //   5: lcmp
    //   6: iflt -> 300
    //   9: lload #4
    //   11: lload_2
    //   12: lcmp
    //   13: iflt -> 300
    //   16: aload_0
    //   17: getfield size : J
    //   20: lstore #10
    //   22: lload #4
    //   24: lload #10
    //   26: lcmp
    //   27: ifle -> 33
    //   30: goto -> 37
    //   33: lload #4
    //   35: lstore #10
    //   37: lload_2
    //   38: lload #10
    //   40: lcmp
    //   41: ifne -> 48
    //   44: ldc2_w -1
    //   47: lreturn
    //   48: aload_0
    //   49: getfield head : Lokio/Segment;
    //   52: astore #15
    //   54: aload #15
    //   56: ifnonnull -> 63
    //   59: ldc2_w -1
    //   62: lreturn
    //   63: aload_0
    //   64: getfield size : J
    //   67: lstore #12
    //   69: lload #8
    //   71: lstore #4
    //   73: aload #15
    //   75: astore #14
    //   77: lload #12
    //   79: lload_2
    //   80: lsub
    //   81: lload_2
    //   82: lcmp
    //   83: ifge -> 136
    //   86: lload #12
    //   88: lstore #8
    //   90: aload #15
    //   92: astore #14
    //   94: aload #14
    //   96: astore #15
    //   98: lload #8
    //   100: lstore #4
    //   102: lload #8
    //   104: lload_2
    //   105: lcmp
    //   106: ifle -> 178
    //   109: aload #14
    //   111: getfield prev : Lokio/Segment;
    //   114: astore #14
    //   116: lload #8
    //   118: aload #14
    //   120: getfield limit : I
    //   123: aload #14
    //   125: getfield pos : I
    //   128: isub
    //   129: i2l
    //   130: lsub
    //   131: lstore #8
    //   133: goto -> 94
    //   136: aload #14
    //   138: getfield limit : I
    //   141: aload #14
    //   143: getfield pos : I
    //   146: isub
    //   147: i2l
    //   148: lload #4
    //   150: ladd
    //   151: lstore #8
    //   153: lload #8
    //   155: lload_2
    //   156: lcmp
    //   157: ifge -> 174
    //   160: aload #14
    //   162: getfield next : Lokio/Segment;
    //   165: astore #14
    //   167: lload #8
    //   169: lstore #4
    //   171: goto -> 136
    //   174: aload #14
    //   176: astore #15
    //   178: lload #4
    //   180: lload #10
    //   182: lcmp
    //   183: ifge -> 296
    //   186: aload #15
    //   188: getfield data : [B
    //   191: astore #14
    //   193: aload #15
    //   195: getfield limit : I
    //   198: i2l
    //   199: aload #15
    //   201: getfield pos : I
    //   204: i2l
    //   205: lload #10
    //   207: ladd
    //   208: lload #4
    //   210: lsub
    //   211: invokestatic min : (JJ)J
    //   214: l2i
    //   215: istore #7
    //   217: aload #15
    //   219: getfield pos : I
    //   222: i2l
    //   223: lload_2
    //   224: ladd
    //   225: lload #4
    //   227: lsub
    //   228: l2i
    //   229: istore #6
    //   231: iload #6
    //   233: iload #7
    //   235: if_icmpge -> 266
    //   238: aload #14
    //   240: iload #6
    //   242: baload
    //   243: iload_1
    //   244: if_icmpne -> 260
    //   247: iload #6
    //   249: aload #15
    //   251: getfield pos : I
    //   254: isub
    //   255: i2l
    //   256: lload #4
    //   258: ladd
    //   259: lreturn
    //   260: iinc #6, 1
    //   263: goto -> 231
    //   266: lload #4
    //   268: aload #15
    //   270: getfield limit : I
    //   273: aload #15
    //   275: getfield pos : I
    //   278: isub
    //   279: i2l
    //   280: ladd
    //   281: lstore #4
    //   283: aload #15
    //   285: getfield next : Lokio/Segment;
    //   288: astore #15
    //   290: lload #4
    //   292: lstore_2
    //   293: goto -> 178
    //   296: ldc2_w -1
    //   299: lreturn
    //   300: new java/lang/IllegalArgumentException
    //   303: dup
    //   304: ldc_w 'size=%s fromIndex=%s toIndex=%s'
    //   307: iconst_3
    //   308: anewarray java/lang/Object
    //   311: dup
    //   312: iconst_0
    //   313: aload_0
    //   314: getfield size : J
    //   317: invokestatic valueOf : (J)Ljava/lang/Long;
    //   320: aastore
    //   321: dup
    //   322: iconst_1
    //   323: lload_2
    //   324: invokestatic valueOf : (J)Ljava/lang/Long;
    //   327: aastore
    //   328: dup
    //   329: iconst_2
    //   330: lload #4
    //   332: invokestatic valueOf : (J)Ljava/lang/Long;
    //   335: aastore
    //   336: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   339: invokespecial <init> : (Ljava/lang/String;)V
    //   342: athrow
  }
  
  public long indexOf(ByteString paramByteString) throws IOException {
    return indexOf(paramByteString, 0L);
  }
  
  public long indexOf(ByteString paramByteString, long paramLong) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual size : ()I
    //   4: ifeq -> 318
    //   7: lconst_0
    //   8: lstore #8
    //   10: lload_2
    //   11: lconst_0
    //   12: lcmp
    //   13: iflt -> 307
    //   16: aload_0
    //   17: getfield head : Lokio/Segment;
    //   20: astore #13
    //   22: aload #13
    //   24: ifnonnull -> 31
    //   27: ldc2_w -1
    //   30: lreturn
    //   31: aload_0
    //   32: getfield size : J
    //   35: lstore #10
    //   37: aload #13
    //   39: astore #12
    //   41: lload #10
    //   43: lload_2
    //   44: lsub
    //   45: lload_2
    //   46: lcmp
    //   47: ifge -> 96
    //   50: aload #13
    //   52: astore #12
    //   54: aload #12
    //   56: astore #13
    //   58: lload #10
    //   60: lstore #8
    //   62: lload #10
    //   64: lload_2
    //   65: lcmp
    //   66: ifle -> 138
    //   69: aload #12
    //   71: getfield prev : Lokio/Segment;
    //   74: astore #12
    //   76: lload #10
    //   78: aload #12
    //   80: getfield limit : I
    //   83: aload #12
    //   85: getfield pos : I
    //   88: isub
    //   89: i2l
    //   90: lsub
    //   91: lstore #10
    //   93: goto -> 54
    //   96: aload #12
    //   98: getfield limit : I
    //   101: aload #12
    //   103: getfield pos : I
    //   106: isub
    //   107: i2l
    //   108: lload #8
    //   110: ladd
    //   111: lstore #10
    //   113: lload #10
    //   115: lload_2
    //   116: lcmp
    //   117: ifge -> 134
    //   120: aload #12
    //   122: getfield next : Lokio/Segment;
    //   125: astore #12
    //   127: lload #10
    //   129: lstore #8
    //   131: goto -> 96
    //   134: aload #12
    //   136: astore #13
    //   138: aload_1
    //   139: iconst_0
    //   140: invokevirtual getByte : (I)B
    //   143: istore #7
    //   145: aload_1
    //   146: invokevirtual size : ()I
    //   149: istore #6
    //   151: lconst_1
    //   152: aload_0
    //   153: getfield size : J
    //   156: iload #6
    //   158: i2l
    //   159: lsub
    //   160: ladd
    //   161: lstore #10
    //   163: aload #13
    //   165: astore #12
    //   167: lload #8
    //   169: lload #10
    //   171: lcmp
    //   172: ifge -> 303
    //   175: aload #12
    //   177: getfield data : [B
    //   180: astore #13
    //   182: aload #12
    //   184: getfield limit : I
    //   187: i2l
    //   188: aload #12
    //   190: getfield pos : I
    //   193: i2l
    //   194: lload #10
    //   196: ladd
    //   197: lload #8
    //   199: lsub
    //   200: invokestatic min : (JJ)J
    //   203: l2i
    //   204: istore #5
    //   206: aload #12
    //   208: getfield pos : I
    //   211: i2l
    //   212: lload_2
    //   213: ladd
    //   214: lload #8
    //   216: lsub
    //   217: l2i
    //   218: istore #4
    //   220: iload #4
    //   222: iload #5
    //   224: if_icmpge -> 273
    //   227: aload #13
    //   229: iload #4
    //   231: baload
    //   232: iload #7
    //   234: if_icmpne -> 267
    //   237: aload_0
    //   238: aload #12
    //   240: iload #4
    //   242: iconst_1
    //   243: iadd
    //   244: aload_1
    //   245: iconst_1
    //   246: iload #6
    //   248: invokespecial rangeEquals : (Lokio/Segment;ILokio/ByteString;II)Z
    //   251: ifeq -> 267
    //   254: iload #4
    //   256: aload #12
    //   258: getfield pos : I
    //   261: isub
    //   262: i2l
    //   263: lload #8
    //   265: ladd
    //   266: lreturn
    //   267: iinc #4, 1
    //   270: goto -> 220
    //   273: lload #8
    //   275: aload #12
    //   277: getfield limit : I
    //   280: aload #12
    //   282: getfield pos : I
    //   285: isub
    //   286: i2l
    //   287: ladd
    //   288: lstore #8
    //   290: aload #12
    //   292: getfield next : Lokio/Segment;
    //   295: astore #12
    //   297: lload #8
    //   299: lstore_2
    //   300: goto -> 167
    //   303: ldc2_w -1
    //   306: lreturn
    //   307: new java/lang/IllegalArgumentException
    //   310: dup
    //   311: ldc_w 'fromIndex < 0'
    //   314: invokespecial <init> : (Ljava/lang/String;)V
    //   317: athrow
    //   318: new java/lang/IllegalArgumentException
    //   321: dup
    //   322: ldc_w 'bytes is empty'
    //   325: invokespecial <init> : (Ljava/lang/String;)V
    //   328: athrow
  }
  
  public long indexOfElement(ByteString paramByteString) {
    return indexOfElement(paramByteString, 0L);
  }
  
  public long indexOfElement(ByteString paramByteString, long paramLong) {
    long l = 0L;
    if (paramLong >= 0L) {
      byte[] arrayOfByte;
      Segment segment2 = this.head;
      if (segment2 == null)
        return -1L; 
      long l1 = this.size;
      Segment segment1 = segment2;
      if (l1 - paramLong < paramLong) {
        while (true) {
          segment1 = segment2;
          l = l1;
          if (l1 > paramLong) {
            segment2 = segment2.prev;
            l1 -= (segment2.limit - segment2.pos);
            continue;
          } 
          break;
        } 
      } else {
        while (true) {
          l1 = (segment1.limit - segment1.pos) + l;
          if (l1 < paramLong) {
            segment1 = segment1.next;
            l = l1;
            continue;
          } 
          break;
        } 
      } 
      if (paramByteString.size() == 2) {
        byte b2 = paramByteString.getByte(0);
        byte b1 = paramByteString.getByte(1);
        while (l < this.size) {
          arrayOfByte = segment1.data;
          int i = (int)(segment1.pos + paramLong - l);
          int j = segment1.limit;
          while (i < j) {
            byte b = arrayOfByte[i];
            if (b == b2 || b == b1) {
              j = segment1.pos;
              return (i - j) + l;
            } 
            i++;
          } 
          l += (segment1.limit - segment1.pos);
          segment1 = segment1.next;
          paramLong = l;
        } 
      } else {
        arrayOfByte = arrayOfByte.internalArray();
        while (l < this.size) {
          byte[] arrayOfByte1 = segment1.data;
          int i = (int)(segment1.pos + paramLong - l);
          int j = segment1.limit;
          while (i < j) {
            byte b = arrayOfByte1[i];
            int m = arrayOfByte.length;
            for (int k = 0; k < m; k++) {
              if (b == arrayOfByte[k]) {
                k = segment1.pos;
                return (i - k) + l;
              } 
            } 
            i++;
          } 
          l += (segment1.limit - segment1.pos);
          segment1 = segment1.next;
          paramLong = l;
        } 
      } 
      return -1L;
    } 
    throw new IllegalArgumentException("fromIndex < 0");
  }
  
  public InputStream inputStream() {
    return new InputStream() {
        final Buffer this$0;
        
        public int available() {
          return (int)Math.min(Buffer.this.size, 2147483647L);
        }
        
        public void close() {}
        
        public int read() {
          return (Buffer.this.size > 0L) ? (Buffer.this.readByte() & 0xFF) : -1;
        }
        
        public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
          return Buffer.this.read(param1ArrayOfbyte, param1Int1, param1Int2);
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(Buffer.this);
          stringBuilder.append(".inputStream()");
          return stringBuilder.toString();
        }
      };
  }
  
  public boolean isOpen() {
    return true;
  }
  
  public ByteString md5() {
    return digest("MD5");
  }
  
  public OutputStream outputStream() {
    return new OutputStream() {
        final Buffer this$0;
        
        public void close() {}
        
        public void flush() {}
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(Buffer.this);
          stringBuilder.append(".outputStream()");
          return stringBuilder.toString();
        }
        
        public void write(int param1Int) {
          Buffer.this.writeByte((byte)param1Int);
        }
        
        public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
          Buffer.this.write(param1ArrayOfbyte, param1Int1, param1Int2);
        }
      };
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString) {
    return rangeEquals(paramLong, paramByteString, 0, paramByteString.size());
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString, int paramInt1, int paramInt2) {
    if (paramLong < 0L || paramInt1 < 0 || paramInt2 < 0 || this.size - paramLong < paramInt2 || paramByteString.size() - paramInt1 < paramInt2)
      return false; 
    for (byte b = 0; b < paramInt2; b++) {
      if (getByte(b + paramLong) != paramByteString.getByte(paramInt1 + b))
        return false; 
    } 
    return true;
  }
  
  public int read(ByteBuffer paramByteBuffer) throws IOException {
    Segment segment = this.head;
    if (segment == null)
      return -1; 
    int i = Math.min(paramByteBuffer.remaining(), segment.limit - segment.pos);
    paramByteBuffer.put(segment.data, segment.pos, i);
    segment.pos += i;
    this.size -= i;
    if (segment.pos == segment.limit) {
      this.head = segment.pop();
      SegmentPool.recycle(segment);
    } 
    return i;
  }
  
  public int read(byte[] paramArrayOfbyte) {
    return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    Util.checkOffsetAndCount(paramArrayOfbyte.length, paramInt1, paramInt2);
    Segment segment = this.head;
    if (segment == null)
      return -1; 
    paramInt2 = Math.min(paramInt2, segment.limit - segment.pos);
    System.arraycopy(segment.data, segment.pos, paramArrayOfbyte, paramInt1, paramInt2);
    segment.pos += paramInt2;
    this.size -= paramInt2;
    if (segment.pos == segment.limit) {
      this.head = segment.pop();
      SegmentPool.recycle(segment);
    } 
    return paramInt2;
  }
  
  public long read(Buffer paramBuffer, long paramLong) {
    if (paramBuffer != null) {
      if (paramLong >= 0L) {
        long l2 = this.size;
        if (l2 == 0L)
          return -1L; 
        long l1 = paramLong;
        if (paramLong > l2)
          l1 = l2; 
        paramBuffer.write(this, l1);
        return l1;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("byteCount < 0: ");
      stringBuilder.append(paramLong);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new IllegalArgumentException("sink == null");
  }
  
  public long readAll(Sink paramSink) throws IOException {
    long l = this.size;
    if (l > 0L)
      paramSink.write(this, l); 
    return l;
  }
  
  public UnsafeCursor readAndWriteUnsafe() {
    return readAndWriteUnsafe(new UnsafeCursor());
  }
  
  public UnsafeCursor readAndWriteUnsafe(UnsafeCursor paramUnsafeCursor) {
    if (paramUnsafeCursor.buffer == null) {
      paramUnsafeCursor.buffer = this;
      paramUnsafeCursor.readWrite = true;
      return paramUnsafeCursor;
    } 
    throw new IllegalStateException("already attached to a buffer");
  }
  
  public byte readByte() {
    if (this.size != 0L) {
      Segment segment = this.head;
      int i = segment.pos;
      int j = segment.limit;
      byte[] arrayOfByte = segment.data;
      int k = i + 1;
      byte b = arrayOfByte[i];
      this.size--;
      if (k == j) {
        this.head = segment.pop();
        SegmentPool.recycle(segment);
      } else {
        segment.pos = k;
      } 
      return b;
    } 
    throw new IllegalStateException("size == 0");
  }
  
  public byte[] readByteArray() {
    try {
      return readByteArray(this.size);
    } catch (EOFException eOFException) {
      throw new AssertionError(eOFException);
    } 
  }
  
  public byte[] readByteArray(long paramLong) throws EOFException {
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    if (paramLong <= 2147483647L) {
      byte[] arrayOfByte = new byte[(int)paramLong];
      readFully(arrayOfByte);
      return arrayOfByte;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("byteCount > Integer.MAX_VALUE: ");
    stringBuilder.append(paramLong);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public ByteString readByteString() {
    return new ByteString(readByteArray());
  }
  
  public ByteString readByteString(long paramLong) throws EOFException {
    return new ByteString(readByteArray(paramLong));
  }
  
  public long readDecimalLong() {
    long l2 = this.size;
    long l1 = 0L;
    if (l2 != 0L) {
      l2 = -7L;
      byte b1 = 0;
      boolean bool = false;
      for (byte b = 0;; b = b2) {
        byte b2;
        Buffer buffer;
        Segment segment = this.head;
        byte[] arrayOfByte = segment.data;
        int i = segment.pos;
        int j = segment.limit;
        while (true) {
          b2 = b;
          if (i < j) {
            b2 = arrayOfByte[i];
            if (b2 >= 48 && b2 <= 57) {
              int m = 48 - b2;
              int k = l1 cmp -922337203685477580L;
              if (k < 0 || (k == 0 && m < l2)) {
                buffer = (new Buffer()).writeDecimalLong(l1).writeByte(b2);
                if (!bool)
                  buffer.readByte(); 
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Number too large: ");
                stringBuilder.append(buffer.readUtf8());
                throw new NumberFormatException(stringBuilder.toString());
              } 
              l1 = l1 * 10L + m;
            } else if (b2 == 45 && !b1) {
              l2--;
              bool = true;
            } else {
              if (b1) {
                b2 = 1;
                break;
              } 
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Expected leading [0-9] or '-' character but was 0x");
              stringBuilder.append(Integer.toHexString(b2));
              throw new NumberFormatException(stringBuilder.toString());
            } 
            i++;
            b1++;
            continue;
          } 
          break;
        } 
        if (i == j) {
          this.head = buffer.pop();
          SegmentPool.recycle((Segment)buffer);
        } else {
          ((Segment)buffer).pos = i;
        } 
        if (b2 != 0 || this.head == null)
          break; 
      } 
      this.size -= b1;
      if (!bool)
        l1 = -l1; 
      return l1;
    } 
    throw new IllegalStateException("size == 0");
  }
  
  public Buffer readFrom(InputStream paramInputStream) throws IOException {
    readFrom(paramInputStream, Long.MAX_VALUE, true);
    return this;
  }
  
  public Buffer readFrom(InputStream paramInputStream, long paramLong) throws IOException {
    if (paramLong >= 0L) {
      readFrom(paramInputStream, paramLong, false);
      return this;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("byteCount < 0: ");
    stringBuilder.append(paramLong);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void readFully(Buffer paramBuffer, long paramLong) throws EOFException {
    long l = this.size;
    if (l >= paramLong) {
      paramBuffer.write(this, paramLong);
      return;
    } 
    paramBuffer.write(this, l);
    throw new EOFException();
  }
  
  public void readFully(byte[] paramArrayOfbyte) throws EOFException {
    int i = 0;
    while (i < paramArrayOfbyte.length) {
      int j = read(paramArrayOfbyte, i, paramArrayOfbyte.length - i);
      if (j != -1) {
        i += j;
        continue;
      } 
      throw new EOFException();
    } 
  }
  
  public long readHexadecimalUnsignedLong() {
    if (this.size != 0L) {
      int i;
      long l2;
      int j = 0;
      long l1 = 0L;
      byte b = 0;
      while (true) {
        byte b1;
        StringBuilder stringBuilder;
        Segment segment = this.head;
        byte[] arrayOfByte = segment.data;
        int k = segment.pos;
        int m = segment.limit;
        l2 = l1;
        i = j;
        while (true) {
          b1 = b;
          if (k < m) {
            b1 = arrayOfByte[k];
            if (b1 >= 48 && b1 <= 57) {
              j = b1 - 48;
            } else {
              if (b1 >= 97 && b1 <= 102) {
                j = b1 - 97;
              } else if (b1 >= 65 && b1 <= 70) {
                j = b1 - 65;
              } else {
                if (i != 0) {
                  b1 = 1;
                  break;
                } 
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("Expected leading [0-9a-fA-F] character but was 0x");
                stringBuilder1.append(Integer.toHexString(b1));
                throw new NumberFormatException(stringBuilder1.toString());
              } 
              j += 10;
            } 
            if ((0xF000000000000000L & l2) == 0L) {
              l2 = l2 << 4L | j;
              k++;
              i++;
              continue;
            } 
            Buffer buffer = (new Buffer()).writeHexadecimalUnsignedLong(l2).writeByte(b1);
            stringBuilder = new StringBuilder();
            stringBuilder.append("Number too large: ");
            stringBuilder.append(buffer.readUtf8());
            throw new NumberFormatException(stringBuilder.toString());
          } 
          break;
        } 
        if (k == m) {
          this.head = stringBuilder.pop();
          SegmentPool.recycle((Segment)stringBuilder);
        } else {
          ((Segment)stringBuilder).pos = k;
        } 
        if (b1 == 0) {
          j = i;
          b = b1;
          l1 = l2;
          if (this.head == null)
            break; 
          continue;
        } 
        break;
      } 
      this.size -= i;
      return l2;
    } 
    throw new IllegalStateException("size == 0");
  }
  
  public int readInt() {
    if (this.size >= 4L) {
      Segment segment = this.head;
      int j = segment.pos;
      int i = segment.limit;
      if (i - j < 4)
        return (readByte() & 0xFF) << 24 | (readByte() & 0xFF) << 16 | (readByte() & 0xFF) << 8 | readByte() & 0xFF; 
      byte[] arrayOfByte = segment.data;
      int k = j + 1;
      j = arrayOfByte[j];
      int n = k + 1;
      k = arrayOfByte[k];
      int m = n + 1;
      byte b = arrayOfByte[n];
      n = m + 1;
      m = arrayOfByte[m];
      this.size -= 4L;
      if (n == i) {
        this.head = segment.pop();
        SegmentPool.recycle(segment);
      } else {
        segment.pos = n;
      } 
      return (j & 0xFF) << 24 | (k & 0xFF) << 16 | (b & 0xFF) << 8 | m & 0xFF;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("size < 4: ");
    stringBuilder.append(this.size);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public int readIntLe() {
    return Util.reverseBytesInt(readInt());
  }
  
  public long readLong() {
    if (this.size >= 8L) {
      Segment segment = this.head;
      int k = segment.pos;
      int i = segment.limit;
      if (i - k < 8)
        return (readInt() & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL & readInt(); 
      byte[] arrayOfByte = segment.data;
      int j = k + 1;
      long l8 = arrayOfByte[k];
      k = j + 1;
      long l6 = arrayOfByte[j];
      j = k + 1;
      long l3 = arrayOfByte[k];
      k = j + 1;
      long l7 = arrayOfByte[j];
      j = k + 1;
      long l5 = arrayOfByte[k];
      k = j + 1;
      long l1 = arrayOfByte[j];
      j = k + 1;
      long l2 = arrayOfByte[k];
      k = j + 1;
      long l4 = arrayOfByte[j];
      this.size -= 8L;
      if (k == i) {
        this.head = segment.pop();
        SegmentPool.recycle(segment);
      } else {
        segment.pos = k;
      } 
      return l4 & 0xFFL | (l8 & 0xFFL) << 56L | (l6 & 0xFFL) << 48L | (l3 & 0xFFL) << 40L | (l7 & 0xFFL) << 32L | (l5 & 0xFFL) << 24L | (l1 & 0xFFL) << 16L | (l2 & 0xFFL) << 8L;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("size < 8: ");
    stringBuilder.append(this.size);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public long readLongLe() {
    return Util.reverseBytesLong(readLong());
  }
  
  public short readShort() {
    if (this.size >= 2L) {
      Segment segment = this.head;
      int k = segment.pos;
      int i = segment.limit;
      if (i - k < 2)
        return (short)((readByte() & 0xFF) << 8 | readByte() & 0xFF); 
      byte[] arrayOfByte = segment.data;
      int j = k + 1;
      byte b = arrayOfByte[k];
      k = j + 1;
      j = arrayOfByte[j];
      this.size -= 2L;
      if (k == i) {
        this.head = segment.pop();
        SegmentPool.recycle(segment);
      } else {
        segment.pos = k;
      } 
      return (short)((b & 0xFF) << 8 | j & 0xFF);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("size < 2: ");
    stringBuilder.append(this.size);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public short readShortLe() {
    return Util.reverseBytesShort(readShort());
  }
  
  public String readString(long paramLong, Charset paramCharset) throws EOFException {
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    if (paramCharset != null) {
      if (paramLong <= 2147483647L) {
        if (paramLong == 0L)
          return ""; 
        Segment segment = this.head;
        if (segment.pos + paramLong > segment.limit)
          return new String(readByteArray(paramLong), paramCharset); 
        String str = new String(segment.data, segment.pos, (int)paramLong, paramCharset);
        segment.pos = (int)(segment.pos + paramLong);
        this.size -= paramLong;
        if (segment.pos == segment.limit) {
          this.head = segment.pop();
          SegmentPool.recycle(segment);
        } 
        return str;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("byteCount > Integer.MAX_VALUE: ");
      stringBuilder.append(paramLong);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new IllegalArgumentException("charset == null");
  }
  
  public String readString(Charset paramCharset) {
    try {
      return readString(this.size, paramCharset);
    } catch (EOFException eOFException) {
      throw new AssertionError(eOFException);
    } 
  }
  
  public UnsafeCursor readUnsafe() {
    return readUnsafe(new UnsafeCursor());
  }
  
  public UnsafeCursor readUnsafe(UnsafeCursor paramUnsafeCursor) {
    if (paramUnsafeCursor.buffer == null) {
      paramUnsafeCursor.buffer = this;
      paramUnsafeCursor.readWrite = false;
      return paramUnsafeCursor;
    } 
    throw new IllegalStateException("already attached to a buffer");
  }
  
  public String readUtf8() {
    try {
      return readString(this.size, Util.UTF_8);
    } catch (EOFException eOFException) {
      throw new AssertionError(eOFException);
    } 
  }
  
  public String readUtf8(long paramLong) throws EOFException {
    return readString(paramLong, Util.UTF_8);
  }
  
  public int readUtf8CodePoint() throws EOFException {
    if (this.size != 0L) {
      int i;
      byte b1;
      int j;
      byte b = getByte(0L);
      byte b2 = 1;
      if ((b & 0x80) == 0) {
        i = b & Byte.MAX_VALUE;
        b1 = 1;
        j = 0;
      } else if ((b & 0xE0) == 192) {
        i = b & 0x1F;
        b1 = 2;
        j = 128;
      } else if ((b & 0xF0) == 224) {
        i = b & 0xF;
        b1 = 3;
        j = 2048;
      } else if ((b & 0xF8) == 240) {
        i = b & 0x7;
        b1 = 4;
        j = 65536;
      } else {
        skip(1L);
        return 65533;
      } 
      long l2 = this.size;
      long l1 = b1;
      if (l2 >= l1) {
        while (b2 < b1) {
          l2 = b2;
          b = getByte(l2);
          if ((b & 0xC0) == 128) {
            i = i << 6 | b & 0x3F;
            b2++;
            continue;
          } 
          skip(l2);
          return 65533;
        } 
        skip(l1);
        return (i > 1114111) ? 65533 : ((i >= 55296 && i <= 57343) ? 65533 : ((i < j) ? 65533 : i));
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("size < ");
      stringBuilder.append(b1);
      stringBuilder.append(": ");
      stringBuilder.append(this.size);
      stringBuilder.append(" (to read code point prefixed 0x");
      stringBuilder.append(Integer.toHexString(b));
      stringBuilder.append(")");
      throw new EOFException(stringBuilder.toString());
    } 
    throw new EOFException();
  }
  
  @Nullable
  public String readUtf8Line() throws EOFException {
    long l = indexOf((byte)10);
    if (l == -1L) {
      String str;
      l = this.size;
      if (l != 0L) {
        str = readUtf8(l);
      } else {
        str = null;
      } 
      return str;
    } 
    return readUtf8Line(l);
  }
  
  String readUtf8Line(long paramLong) throws EOFException {
    if (paramLong > 0L) {
      long l = paramLong - 1L;
      if (getByte(l) == 13) {
        String str1 = readUtf8(l);
        skip(2L);
        return str1;
      } 
    } 
    String str = readUtf8(paramLong);
    skip(1L);
    return str;
  }
  
  public String readUtf8LineStrict() throws EOFException {
    return readUtf8LineStrict(Long.MAX_VALUE);
  }
  
  public String readUtf8LineStrict(long paramLong) throws EOFException {
    if (paramLong >= 0L) {
      long l1 = Long.MAX_VALUE;
      if (paramLong != Long.MAX_VALUE)
        l1 = paramLong + 1L; 
      long l2 = indexOf((byte)10, 0L, l1);
      if (l2 != -1L)
        return readUtf8Line(l2); 
      if (l1 < size() && getByte(l1 - 1L) == 13 && getByte(l1) == 10)
        return readUtf8Line(l1); 
      Buffer buffer = new Buffer();
      copyTo(buffer, 0L, Math.min(32L, size()));
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("\\n not found: limit=");
      stringBuilder1.append(Math.min(size(), paramLong));
      stringBuilder1.append(" content=");
      stringBuilder1.append(buffer.readByteString().hex());
      stringBuilder1.append('…');
      throw new EOFException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("limit < 0: ");
    stringBuilder.append(paramLong);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean request(long paramLong) {
    boolean bool;
    if (this.size >= paramLong) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void require(long paramLong) throws EOFException {
    if (this.size >= paramLong)
      return; 
    throw new EOFException();
  }
  
  List<Integer> segmentSizes() {
    if (this.head == null)
      return Collections.emptyList(); 
    ArrayList<Integer> arrayList = new ArrayList();
    arrayList.add(Integer.valueOf(this.head.limit - this.head.pos));
    Segment segment = this.head;
    while (true) {
      segment = segment.next;
      if (segment != this.head) {
        arrayList.add(Integer.valueOf(segment.limit - segment.pos));
        continue;
      } 
      return arrayList;
    } 
  }
  
  public int select(Options paramOptions) {
    Segment segment = this.head;
    if (segment == null)
      return paramOptions.indexOf(ByteString.EMPTY); 
    for (ByteString byteString : paramOptions.byteStrings) {
      if (this.size >= byteString.size() && rangeEquals(segment, segment.pos, byteString, 0, byteString.size()))
        try {
          skip(byteString.size());
          return null;
        } catch (EOFException eOFException) {
          throw new AssertionError(eOFException);
        }  
    } 
    return -1;
  }
  
  int selectPrefix(Options paramOptions) {
    Segment segment = this.head;
    for (ByteString byteString : paramOptions.byteStrings) {
      int i = (int)Math.min(this.size, byteString.size());
      if (i == 0 || rangeEquals(segment, segment.pos, byteString, 0, i))
        return null; 
    } 
    return -1;
  }
  
  public ByteString sha1() {
    return digest("SHA-1");
  }
  
  public ByteString sha256() {
    return digest("SHA-256");
  }
  
  public ByteString sha512() {
    return digest("SHA-512");
  }
  
  public long size() {
    return this.size;
  }
  
  public void skip(long paramLong) throws EOFException {
    while (paramLong > 0L) {
      Segment segment = this.head;
      if (segment != null) {
        int i = (int)Math.min(paramLong, (segment.limit - this.head.pos));
        long l2 = this.size;
        long l1 = i;
        this.size = l2 - l1;
        l1 = paramLong - l1;
        segment = this.head;
        segment.pos += i;
        paramLong = l1;
        if (this.head.pos == this.head.limit) {
          segment = this.head;
          this.head = segment.pop();
          SegmentPool.recycle(segment);
          paramLong = l1;
        } 
        continue;
      } 
      throw new EOFException();
    } 
  }
  
  public ByteString snapshot() {
    long l = this.size;
    if (l <= 2147483647L)
      return snapshot((int)l); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("size > Integer.MAX_VALUE: ");
    stringBuilder.append(this.size);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public ByteString snapshot(int paramInt) {
    return (paramInt == 0) ? ByteString.EMPTY : new SegmentedByteString(this, paramInt);
  }
  
  public Timeout timeout() {
    return Timeout.NONE;
  }
  
  public String toString() {
    return snapshot().toString();
  }
  
  Segment writableSegment(int paramInt) {
    Segment segment;
    if (paramInt >= 1 && paramInt <= 8192) {
      Segment segment1 = this.head;
      if (segment1 == null) {
        segment1 = SegmentPool.take();
        this.head = segment1;
        segment1.prev = segment1;
        segment1.next = segment1;
        return segment1;
      } 
      segment = segment1.prev;
      if (segment.limit + paramInt <= 8192) {
        segment1 = segment;
        return !segment.owner ? segment.push(SegmentPool.take()) : segment1;
      } 
    } else {
      throw new IllegalArgumentException();
    } 
    return segment.push(SegmentPool.take());
  }
  
  public int write(ByteBuffer paramByteBuffer) throws IOException {
    if (paramByteBuffer != null) {
      int j = paramByteBuffer.remaining();
      int i = j;
      while (i > 0) {
        Segment segment = writableSegment(1);
        int k = Math.min(i, 8192 - segment.limit);
        paramByteBuffer.get(segment.data, segment.limit, k);
        i -= k;
        segment.limit += k;
      } 
      this.size += j;
      return j;
    } 
    throw new IllegalArgumentException("source == null");
  }
  
  public Buffer write(ByteString paramByteString) {
    if (paramByteString != null) {
      paramByteString.write(this);
      return this;
    } 
    throw new IllegalArgumentException("byteString == null");
  }
  
  public Buffer write(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte != null)
      return write(paramArrayOfbyte, 0, paramArrayOfbyte.length); 
    throw new IllegalArgumentException("source == null");
  }
  
  public Buffer write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte != null) {
      long l3 = paramArrayOfbyte.length;
      long l1 = paramInt1;
      long l2 = paramInt2;
      Util.checkOffsetAndCount(l3, l1, l2);
      paramInt2 += paramInt1;
      while (paramInt1 < paramInt2) {
        Segment segment = writableSegment(1);
        int i = Math.min(paramInt2 - paramInt1, 8192 - segment.limit);
        System.arraycopy(paramArrayOfbyte, paramInt1, segment.data, segment.limit, i);
        paramInt1 += i;
        segment.limit += i;
      } 
      this.size += l2;
      return this;
    } 
    throw new IllegalArgumentException("source == null");
  }
  
  public BufferedSink write(Source paramSource, long paramLong) throws IOException {
    while (paramLong > 0L) {
      long l = paramSource.read(this, paramLong);
      if (l != -1L) {
        paramLong -= l;
        continue;
      } 
      throw new EOFException();
    } 
    return this;
  }
  
  public void write(Buffer paramBuffer, long paramLong) {
    if (paramBuffer != null) {
      if (paramBuffer != this) {
        Util.checkOffsetAndCount(paramBuffer.size, 0L, paramLong);
        while (paramLong > 0L) {
          if (paramLong < (paramBuffer.head.limit - paramBuffer.head.pos)) {
            Segment segment = this.head;
            if (segment != null) {
              segment = segment.prev;
            } else {
              segment = null;
            } 
            if (segment != null && segment.owner) {
              int i;
              long l1 = segment.limit;
              if (segment.shared) {
                i = 0;
              } else {
                i = segment.pos;
              } 
              if (l1 + paramLong - i <= 8192L) {
                paramBuffer.head.writeTo(segment, (int)paramLong);
                paramBuffer.size -= paramLong;
                this.size += paramLong;
                return;
              } 
            } 
            paramBuffer.head = paramBuffer.head.split((int)paramLong);
          } 
          Segment segment2 = paramBuffer.head;
          long l = (segment2.limit - segment2.pos);
          paramBuffer.head = segment2.pop();
          Segment segment1 = this.head;
          if (segment1 == null) {
            this.head = segment2;
            segment2.prev = segment2;
            segment2.next = segment2;
          } else {
            segment1.prev.push(segment2).compact();
          } 
          paramBuffer.size -= l;
          this.size += l;
          paramLong -= l;
        } 
        return;
      } 
      throw new IllegalArgumentException("source == this");
    } 
    throw new IllegalArgumentException("source == null");
  }
  
  public long writeAll(Source paramSource) throws IOException {
    if (paramSource != null) {
      long l = 0L;
      while (true) {
        long l1 = paramSource.read(this, 8192L);
        if (l1 != -1L) {
          l += l1;
          continue;
        } 
        return l;
      } 
    } 
    throw new IllegalArgumentException("source == null");
  }
  
  public Buffer writeByte(int paramInt) {
    Segment segment = writableSegment(1);
    byte[] arrayOfByte = segment.data;
    int i = segment.limit;
    segment.limit = i + 1;
    arrayOfByte[i] = (byte)paramInt;
    this.size++;
    return this;
  }
  
  public Buffer writeDecimalLong(long paramLong) {
    int j = paramLong cmp 0L;
    if (j == 0)
      return writeByte(48); 
    boolean bool = false;
    int i = 1;
    long l = paramLong;
    if (j < 0) {
      l = -paramLong;
      if (l < 0L)
        return writeUtf8("-9223372036854775808"); 
      bool = true;
    } 
    if (l < 100000000L) {
      if (l < 10000L) {
        if (l < 100L) {
          if (l >= 10L)
            i = 2; 
        } else if (l < 1000L) {
          i = 3;
        } else {
          i = 4;
        } 
      } else if (l < 1000000L) {
        if (l < 100000L) {
          i = 5;
        } else {
          i = 6;
        } 
      } else if (l < 10000000L) {
        i = 7;
      } else {
        i = 8;
      } 
    } else if (l < 1000000000000L) {
      if (l < 10000000000L) {
        if (l < 1000000000L) {
          i = 9;
        } else {
          i = 10;
        } 
      } else if (l < 100000000000L) {
        i = 11;
      } else {
        i = 12;
      } 
    } else if (l < 1000000000000000L) {
      if (l < 10000000000000L) {
        i = 13;
      } else if (l < 100000000000000L) {
        i = 14;
      } else {
        i = 15;
      } 
    } else if (l < 100000000000000000L) {
      if (l < 10000000000000000L) {
        i = 16;
      } else {
        i = 17;
      } 
    } else if (l < 1000000000000000000L) {
      i = 18;
    } else {
      i = 19;
    } 
    j = i;
    if (bool)
      j = i + 1; 
    Segment segment = writableSegment(j);
    byte[] arrayOfByte = segment.data;
    i = segment.limit + j;
    while (l != 0L) {
      int k = (int)(l % 10L);
      arrayOfByte[--i] = DIGITS[k];
      l /= 10L;
    } 
    if (bool)
      arrayOfByte[i - 1] = 45; 
    segment.limit += j;
    this.size += j;
    return this;
  }
  
  public Buffer writeHexadecimalUnsignedLong(long paramLong) {
    if (paramLong == 0L)
      return writeByte(48); 
    int k = Long.numberOfTrailingZeros(Long.highestOneBit(paramLong)) / 4 + 1;
    Segment segment = writableSegment(k);
    byte[] arrayOfByte = segment.data;
    int i = segment.limit + k - 1;
    int j = segment.limit;
    while (i >= j) {
      arrayOfByte[i] = DIGITS[(int)(0xFL & paramLong)];
      paramLong >>>= 4L;
      i--;
    } 
    segment.limit += k;
    this.size += k;
    return this;
  }
  
  public Buffer writeInt(int paramInt) {
    Segment segment = writableSegment(4);
    byte[] arrayOfByte = segment.data;
    int j = segment.limit;
    int i = j + 1;
    arrayOfByte[j] = (byte)(paramInt >>> 24 & 0xFF);
    j = i + 1;
    arrayOfByte[i] = (byte)(paramInt >>> 16 & 0xFF);
    i = j + 1;
    arrayOfByte[j] = (byte)(paramInt >>> 8 & 0xFF);
    arrayOfByte[i] = (byte)(paramInt & 0xFF);
    segment.limit = i + 1;
    this.size += 4L;
    return this;
  }
  
  public Buffer writeIntLe(int paramInt) {
    return writeInt(Util.reverseBytesInt(paramInt));
  }
  
  public Buffer writeLong(long paramLong) {
    Segment segment = writableSegment(8);
    byte[] arrayOfByte = segment.data;
    int j = segment.limit;
    int i = j + 1;
    arrayOfByte[j] = (byte)(int)(paramLong >>> 56L & 0xFFL);
    j = i + 1;
    arrayOfByte[i] = (byte)(int)(paramLong >>> 48L & 0xFFL);
    i = j + 1;
    arrayOfByte[j] = (byte)(int)(paramLong >>> 40L & 0xFFL);
    j = i + 1;
    arrayOfByte[i] = (byte)(int)(paramLong >>> 32L & 0xFFL);
    i = j + 1;
    arrayOfByte[j] = (byte)(int)(paramLong >>> 24L & 0xFFL);
    j = i + 1;
    arrayOfByte[i] = (byte)(int)(paramLong >>> 16L & 0xFFL);
    i = j + 1;
    arrayOfByte[j] = (byte)(int)(paramLong >>> 8L & 0xFFL);
    arrayOfByte[i] = (byte)(int)(paramLong & 0xFFL);
    segment.limit = i + 1;
    this.size += 8L;
    return this;
  }
  
  public Buffer writeLongLe(long paramLong) {
    return writeLong(Util.reverseBytesLong(paramLong));
  }
  
  public Buffer writeShort(int paramInt) {
    Segment segment = writableSegment(2);
    byte[] arrayOfByte = segment.data;
    int i = segment.limit;
    int j = i + 1;
    arrayOfByte[i] = (byte)(paramInt >>> 8 & 0xFF);
    arrayOfByte[j] = (byte)(paramInt & 0xFF);
    segment.limit = j + 1;
    this.size += 2L;
    return this;
  }
  
  public Buffer writeShortLe(int paramInt) {
    return writeShort(Util.reverseBytesShort((short)paramInt));
  }
  
  public Buffer writeString(String paramString, int paramInt1, int paramInt2, Charset paramCharset) {
    if (paramString != null) {
      if (paramInt1 >= 0) {
        if (paramInt2 >= paramInt1) {
          byte[] arrayOfByte;
          if (paramInt2 <= paramString.length()) {
            if (paramCharset != null) {
              if (paramCharset.equals(Util.UTF_8))
                return writeUtf8(paramString, paramInt1, paramInt2); 
              arrayOfByte = paramString.substring(paramInt1, paramInt2).getBytes(paramCharset);
              return write(arrayOfByte, 0, arrayOfByte.length);
            } 
            throw new IllegalArgumentException("charset == null");
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("endIndex > string.length: ");
          stringBuilder2.append(paramInt2);
          stringBuilder2.append(" > ");
          stringBuilder2.append(arrayOfByte.length());
          throw new IllegalArgumentException(stringBuilder2.toString());
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("endIndex < beginIndex: ");
        stringBuilder1.append(paramInt2);
        stringBuilder1.append(" < ");
        stringBuilder1.append(paramInt1);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("beginIndex < 0: ");
      stringBuilder.append(paramInt1);
      throw new IllegalAccessError(stringBuilder.toString());
    } 
    throw new IllegalArgumentException("string == null");
  }
  
  public Buffer writeString(String paramString, Charset paramCharset) {
    return writeString(paramString, 0, paramString.length(), paramCharset);
  }
  
  public Buffer writeTo(OutputStream paramOutputStream) throws IOException {
    return writeTo(paramOutputStream, this.size);
  }
  
  public Buffer writeTo(OutputStream paramOutputStream, long paramLong) throws IOException {
    if (paramOutputStream != null) {
      Util.checkOffsetAndCount(this.size, 0L, paramLong);
      Segment segment = this.head;
      while (paramLong > 0L) {
        int i = (int)Math.min(paramLong, (segment.limit - segment.pos));
        paramOutputStream.write(segment.data, segment.pos, i);
        segment.pos += i;
        long l1 = this.size;
        long l2 = i;
        this.size = l1 - l2;
        l1 = paramLong - l2;
        paramLong = l1;
        if (segment.pos == segment.limit) {
          Segment segment1 = segment.pop();
          this.head = segment1;
          SegmentPool.recycle(segment);
          segment = segment1;
          paramLong = l1;
        } 
      } 
      return this;
    } 
    throw new IllegalArgumentException("out == null");
  }
  
  public Buffer writeUtf8(String paramString) {
    return writeUtf8(paramString, 0, paramString.length());
  }
  
  public Buffer writeUtf8(String paramString, int paramInt1, int paramInt2) {
    if (paramString != null) {
      if (paramInt1 >= 0) {
        if (paramInt2 >= paramInt1) {
          if (paramInt2 <= paramString.length()) {
            while (paramInt1 < paramInt2) {
              char c = paramString.charAt(paramInt1);
              if (c < '') {
                Segment segment = writableSegment(1);
                byte[] arrayOfByte = segment.data;
                int j = segment.limit - paramInt1;
                int k = Math.min(paramInt2, 8192 - j);
                int i = paramInt1 + 1;
                arrayOfByte[paramInt1 + j] = (byte)c;
                for (paramInt1 = i; paramInt1 < k; paramInt1++) {
                  i = paramString.charAt(paramInt1);
                  if (i >= 128)
                    break; 
                  arrayOfByte[paramInt1 + j] = (byte)i;
                } 
                i = j + paramInt1 - segment.limit;
                segment.limit += i;
                this.size += i;
                continue;
              } 
              if (c < 'ࠀ') {
                writeByte(c >> 6 | 0xC0);
                writeByte(c & 0x3F | 0x80);
              } else if (c < '?' || c > '?') {
                writeByte(c >> 12 | 0xE0);
                writeByte(c >> 6 & 0x3F | 0x80);
                writeByte(c & 0x3F | 0x80);
              } else {
                int j = paramInt1 + 1;
                if (j < paramInt2) {
                  i = paramString.charAt(j);
                } else {
                  i = 0;
                } 
                if (c > '?' || i < 56320 || i > 57343) {
                  writeByte(63);
                  paramInt1 = j;
                  continue;
                } 
                int i = ((c & 0xFFFF27FF) << 10 | 0xFFFF23FF & i) + 65536;
                writeByte(i >> 18 | 0xF0);
                writeByte(i >> 12 & 0x3F | 0x80);
                writeByte(i >> 6 & 0x3F | 0x80);
                writeByte(i & 0x3F | 0x80);
                paramInt1 += 2;
                continue;
              } 
              paramInt1++;
            } 
            return this;
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("endIndex > string.length: ");
          stringBuilder2.append(paramInt2);
          stringBuilder2.append(" > ");
          stringBuilder2.append(paramString.length());
          throw new IllegalArgumentException(stringBuilder2.toString());
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("endIndex < beginIndex: ");
        stringBuilder1.append(paramInt2);
        stringBuilder1.append(" < ");
        stringBuilder1.append(paramInt1);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("beginIndex < 0: ");
      stringBuilder.append(paramInt1);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new IllegalArgumentException("string == null");
  }
  
  public Buffer writeUtf8CodePoint(int paramInt) {
    if (paramInt < 128) {
      writeByte(paramInt);
    } else if (paramInt < 2048) {
      writeByte(paramInt >> 6 | 0xC0);
      writeByte(paramInt & 0x3F | 0x80);
    } else if (paramInt < 65536) {
      if (paramInt >= 55296 && paramInt <= 57343) {
        writeByte(63);
      } else {
        writeByte(paramInt >> 12 | 0xE0);
        writeByte(paramInt >> 6 & 0x3F | 0x80);
        writeByte(paramInt & 0x3F | 0x80);
      } 
    } else {
      if (paramInt <= 1114111) {
        writeByte(paramInt >> 18 | 0xF0);
        writeByte(paramInt >> 12 & 0x3F | 0x80);
        writeByte(paramInt >> 6 & 0x3F | 0x80);
        writeByte(paramInt & 0x3F | 0x80);
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unexpected code point: ");
      stringBuilder.append(Integer.toHexString(paramInt));
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return this;
  }
  
  public static final class UnsafeCursor implements Closeable {
    public Buffer buffer;
    
    public byte[] data;
    
    public int end = -1;
    
    public long offset = -1L;
    
    public boolean readWrite;
    
    private Segment segment;
    
    public int start = -1;
    
    public void close() {
      if (this.buffer != null) {
        this.buffer = null;
        this.segment = null;
        this.offset = -1L;
        this.data = null;
        this.start = -1;
        this.end = -1;
        return;
      } 
      throw new IllegalStateException("not attached to a buffer");
    }
    
    public long expandBuffer(int param1Int) {
      if (param1Int > 0) {
        if (param1Int <= 8192) {
          Buffer buffer = this.buffer;
          if (buffer != null) {
            if (this.readWrite) {
              long l1 = buffer.size;
              Segment segment = this.buffer.writableSegment(param1Int);
              param1Int = 8192 - segment.limit;
              segment.limit = 8192;
              Buffer buffer1 = this.buffer;
              long l2 = param1Int;
              buffer1.size = l1 + l2;
              this.segment = segment;
              this.offset = l1;
              this.data = segment.data;
              this.start = 8192 - param1Int;
              this.end = 8192;
              return l2;
            } 
            throw new IllegalStateException("expandBuffer() only permitted for read/write buffers");
          } 
          throw new IllegalStateException("not attached to a buffer");
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("minByteCount > Segment.SIZE: ");
        stringBuilder1.append(param1Int);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("minByteCount <= 0: ");
      stringBuilder.append(param1Int);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public int next() {
      if (this.offset != this.buffer.size) {
        long l = this.offset;
        return (l == -1L) ? seek(0L) : seek(l + (this.end - this.start));
      } 
      throw new IllegalStateException();
    }
    
    public long resizeBuffer(long param1Long) {
      Buffer buffer = this.buffer;
      if (buffer != null) {
        if (this.readWrite) {
          long l = buffer.size;
          int i = param1Long cmp l;
          if (i <= 0) {
            if (param1Long >= 0L) {
              long l1 = l - param1Long;
              while (l1 > 0L) {
                Segment segment = this.buffer.head.prev;
                long l2 = (segment.limit - segment.pos);
                if (l2 <= l1) {
                  this.buffer.head = segment.pop();
                  SegmentPool.recycle(segment);
                  l1 -= l2;
                  continue;
                } 
                segment.limit = (int)(segment.limit - l1);
              } 
              this.segment = null;
              this.offset = param1Long;
              this.data = null;
              this.start = -1;
              this.end = -1;
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("newSize < 0: ");
              stringBuilder.append(param1Long);
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
          } else if (i > 0) {
            long l1 = param1Long - l;
            i = 1;
            while (l1 > 0L) {
              Segment segment = this.buffer.writableSegment(1);
              int j = (int)Math.min(l1, (8192 - segment.limit));
              segment.limit += j;
              long l2 = l1 - j;
              l1 = l2;
              if (i != 0) {
                this.segment = segment;
                this.offset = l;
                this.data = segment.data;
                this.start = segment.limit - j;
                this.end = segment.limit;
                i = 0;
                l1 = l2;
              } 
            } 
          } 
          this.buffer.size = param1Long;
          return l;
        } 
        throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers");
      } 
      throw new IllegalStateException("not attached to a buffer");
    }
    
    public int seek(long param1Long) {
      int i = param1Long cmp -1L;
      if (i >= 0 && param1Long <= this.buffer.size) {
        if (i == 0 || param1Long == this.buffer.size) {
          this.segment = null;
          this.offset = param1Long;
          this.data = null;
          this.start = -1;
          this.end = -1;
          return -1;
        } 
        long l3 = 0L;
        long l4 = this.buffer.size;
        Segment segment4 = this.buffer.head;
        Segment segment3 = this.buffer.head;
        Segment segment5 = this.segment;
        long l2 = l3;
        long l1 = l4;
        Segment segment1 = segment4;
        Segment segment2 = segment3;
        if (segment5 != null) {
          l1 = this.offset - (this.start - segment5.pos);
          if (l1 > param1Long) {
            segment2 = this.segment;
            l2 = l3;
            segment1 = segment4;
          } else {
            segment1 = this.segment;
            l2 = l1;
            segment2 = segment3;
            l1 = l4;
          } 
        } 
        l3 = l1;
        if (l1 - param1Long > param1Long - l2) {
          segment2 = segment1;
          while (true) {
            l1 = l2;
            segment1 = segment2;
            if (param1Long >= (segment2.limit - segment2.pos) + l2) {
              l2 += (segment2.limit - segment2.pos);
              segment2 = segment2.next;
              continue;
            } 
            break;
          } 
        } else {
          while (l3 > param1Long) {
            segment2 = segment2.prev;
            l3 -= (segment2.limit - segment2.pos);
          } 
          l1 = l3;
          segment1 = segment2;
        } 
        segment2 = segment1;
        if (this.readWrite) {
          segment2 = segment1;
          if (segment1.shared) {
            segment2 = segment1.unsharedCopy();
            if (this.buffer.head == segment1)
              this.buffer.head = segment2; 
            segment2 = segment1.push(segment2);
            segment2.prev.pop();
          } 
        } 
        this.segment = segment2;
        this.offset = param1Long;
        this.data = segment2.data;
        this.start = segment2.pos + (int)(param1Long - l1);
        i = segment2.limit;
        this.end = i;
        return i - this.start;
      } 
      throw new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", new Object[] { Long.valueOf(param1Long), Long.valueOf(this.buffer.size) }));
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\Buffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */