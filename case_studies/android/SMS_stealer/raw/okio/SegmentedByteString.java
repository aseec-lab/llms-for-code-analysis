package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

final class SegmentedByteString extends ByteString {
  final transient int[] directory;
  
  final transient byte[][] segments;
  
  SegmentedByteString(Buffer paramBuffer, int paramInt) {
    super(null);
    Util.checkOffsetAndCount(paramBuffer.size, 0L, paramInt);
    Segment segment2 = paramBuffer.head;
    int k = 0;
    int j = 0;
    int i = 0;
    while (j < paramInt) {
      if (segment2.limit != segment2.pos) {
        j += segment2.limit - segment2.pos;
        i++;
        segment2 = segment2.next;
        continue;
      } 
      throw new AssertionError("s.limit == s.pos");
    } 
    this.segments = new byte[i][];
    this.directory = new int[i * 2];
    Segment segment1 = paramBuffer.head;
    j = 0;
    i = k;
    while (i < paramInt) {
      this.segments[j] = segment1.data;
      k = i + segment1.limit - segment1.pos;
      i = k;
      if (k > paramInt)
        i = paramInt; 
      int[] arrayOfInt = this.directory;
      arrayOfInt[j] = i;
      arrayOfInt[this.segments.length + j] = segment1.pos;
      segment1.shared = true;
      j++;
      segment1 = segment1.next;
    } 
  }
  
  private int segment(int paramInt) {
    paramInt = Arrays.binarySearch(this.directory, 0, this.segments.length, paramInt + 1);
    if (paramInt < 0)
      paramInt ^= 0xFFFFFFFF; 
    return paramInt;
  }
  
  private ByteString toByteString() {
    return new ByteString(toByteArray());
  }
  
  private Object writeReplace() {
    return toByteString();
  }
  
  public ByteBuffer asByteBuffer() {
    return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
  }
  
  public String base64() {
    return toByteString().base64();
  }
  
  public String base64Url() {
    return toByteString().base64Url();
  }
  
  public boolean equals(Object paramObject) {
    null = true;
    if (paramObject == this)
      return true; 
    if (paramObject instanceof ByteString) {
      paramObject = paramObject;
      if (paramObject.size() == size() && rangeEquals(0, (ByteString)paramObject, 0, size()))
        return null; 
    } 
    return false;
  }
  
  public byte getByte(int paramInt) {
    int i;
    Util.checkOffsetAndCount(this.directory[this.segments.length - 1], paramInt, 1L);
    int j = segment(paramInt);
    if (j == 0) {
      i = 0;
    } else {
      i = this.directory[j - 1];
    } 
    int[] arrayOfInt = this.directory;
    byte[][] arrayOfByte = this.segments;
    int k = arrayOfInt[arrayOfByte.length + j];
    return arrayOfByte[j][paramInt - i + k];
  }
  
  public int hashCode() {
    int i = this.hashCode;
    if (i != 0)
      return i; 
    int m = this.segments.length;
    byte b = 0;
    int j = 0;
    int k = 1;
    while (b < m) {
      byte[] arrayOfByte = this.segments[b];
      int[] arrayOfInt = this.directory;
      int i1 = arrayOfInt[m + b];
      int n = arrayOfInt[b];
      for (i = i1; i < n - j + i1; i++)
        k = k * 31 + arrayOfByte[i]; 
      b++;
      j = n;
    } 
    this.hashCode = k;
    return k;
  }
  
  public String hex() {
    return toByteString().hex();
  }
  
  public ByteString hmacSha1(ByteString paramByteString) {
    return toByteString().hmacSha1(paramByteString);
  }
  
  public ByteString hmacSha256(ByteString paramByteString) {
    return toByteString().hmacSha256(paramByteString);
  }
  
  public int indexOf(byte[] paramArrayOfbyte, int paramInt) {
    return toByteString().indexOf(paramArrayOfbyte, paramInt);
  }
  
  byte[] internalArray() {
    return toByteArray();
  }
  
  public int lastIndexOf(byte[] paramArrayOfbyte, int paramInt) {
    return toByteString().lastIndexOf(paramArrayOfbyte, paramInt);
  }
  
  public ByteString md5() {
    return toByteString().md5();
  }
  
  public boolean rangeEquals(int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3) {
    if (paramInt1 < 0 || paramInt1 > size() - paramInt3)
      return false; 
    int j = segment(paramInt1);
    int i = paramInt1;
    for (paramInt1 = j; paramInt3 > 0; paramInt1++) {
      if (paramInt1 == 0) {
        j = 0;
      } else {
        j = this.directory[paramInt1 - 1];
      } 
      int k = Math.min(paramInt3, this.directory[paramInt1] - j + j - i);
      int[] arrayOfInt = this.directory;
      byte[][] arrayOfByte = this.segments;
      int m = arrayOfInt[arrayOfByte.length + paramInt1];
      if (!paramByteString.rangeEquals(paramInt2, arrayOfByte[paramInt1], i - j + m, k))
        return false; 
      i += k;
      paramInt2 += k;
      paramInt3 -= k;
    } 
    return true;
  }
  
  public boolean rangeEquals(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
    if (paramInt1 < 0 || paramInt1 > size() - paramInt3 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt3)
      return false; 
    for (int i = segment(paramInt1); paramInt3 > 0; i++) {
      int j;
      if (i == 0) {
        j = 0;
      } else {
        j = this.directory[i - 1];
      } 
      int k = Math.min(paramInt3, this.directory[i] - j + j - paramInt1);
      int[] arrayOfInt = this.directory;
      byte[][] arrayOfByte = this.segments;
      int m = arrayOfInt[arrayOfByte.length + i];
      if (!Util.arrayRangeEquals(arrayOfByte[i], paramInt1 - j + m, paramArrayOfbyte, paramInt2, k))
        return false; 
      paramInt1 += k;
      paramInt2 += k;
      paramInt3 -= k;
    } 
    return true;
  }
  
  public ByteString sha1() {
    return toByteString().sha1();
  }
  
  public ByteString sha256() {
    return toByteString().sha256();
  }
  
  public int size() {
    return this.directory[this.segments.length - 1];
  }
  
  public String string(Charset paramCharset) {
    return toByteString().string(paramCharset);
  }
  
  public ByteString substring(int paramInt) {
    return toByteString().substring(paramInt);
  }
  
  public ByteString substring(int paramInt1, int paramInt2) {
    return toByteString().substring(paramInt1, paramInt2);
  }
  
  public ByteString toAsciiLowercase() {
    return toByteString().toAsciiLowercase();
  }
  
  public ByteString toAsciiUppercase() {
    return toByteString().toAsciiUppercase();
  }
  
  public byte[] toByteArray() {
    int[] arrayOfInt = this.directory;
    byte[][] arrayOfByte = this.segments;
    byte[] arrayOfByte1 = new byte[arrayOfInt[arrayOfByte.length - 1]];
    int j = arrayOfByte.length;
    byte b = 0;
    for (int i = 0; b < j; i = k) {
      int[] arrayOfInt1 = this.directory;
      int m = arrayOfInt1[j + b];
      int k = arrayOfInt1[b];
      System.arraycopy(this.segments[b], m, arrayOfByte1, i, k - i);
      b++;
    } 
    return arrayOfByte1;
  }
  
  public String toString() {
    return toByteString().toString();
  }
  
  public String utf8() {
    return toByteString().utf8();
  }
  
  public void write(OutputStream paramOutputStream) throws IOException {
    if (paramOutputStream != null) {
      int j = this.segments.length;
      byte b = 0;
      for (int i = 0; b < j; i = k) {
        int[] arrayOfInt = this.directory;
        int m = arrayOfInt[j + b];
        int k = arrayOfInt[b];
        paramOutputStream.write(this.segments[b], m, k - i);
        b++;
      } 
      return;
    } 
    throw new IllegalArgumentException("out == null");
  }
  
  void write(Buffer paramBuffer) {
    int j = this.segments.length;
    byte b = 0;
    int i;
    for (i = 0; b < j; i = k) {
      int[] arrayOfInt = this.directory;
      int m = arrayOfInt[j + b];
      int k = arrayOfInt[b];
      Segment segment = new Segment(this.segments[b], m, m + k - i, true, false);
      if (paramBuffer.head == null) {
        segment.prev = segment;
        segment.next = segment;
        paramBuffer.head = segment;
      } else {
        paramBuffer.head.prev.push(segment);
      } 
      b++;
    } 
    paramBuffer.size += i;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\SegmentedByteString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */