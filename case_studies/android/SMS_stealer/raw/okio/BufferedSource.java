package okio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

public interface BufferedSource extends Source, ReadableByteChannel {
  Buffer buffer();
  
  boolean exhausted() throws IOException;
  
  long indexOf(byte paramByte) throws IOException;
  
  long indexOf(byte paramByte, long paramLong) throws IOException;
  
  long indexOf(byte paramByte, long paramLong1, long paramLong2) throws IOException;
  
  long indexOf(ByteString paramByteString) throws IOException;
  
  long indexOf(ByteString paramByteString, long paramLong) throws IOException;
  
  long indexOfElement(ByteString paramByteString) throws IOException;
  
  long indexOfElement(ByteString paramByteString, long paramLong) throws IOException;
  
  InputStream inputStream();
  
  boolean rangeEquals(long paramLong, ByteString paramByteString) throws IOException;
  
  boolean rangeEquals(long paramLong, ByteString paramByteString, int paramInt1, int paramInt2) throws IOException;
  
  int read(byte[] paramArrayOfbyte) throws IOException;
  
  int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  long readAll(Sink paramSink) throws IOException;
  
  byte readByte() throws IOException;
  
  byte[] readByteArray() throws IOException;
  
  byte[] readByteArray(long paramLong) throws IOException;
  
  ByteString readByteString() throws IOException;
  
  ByteString readByteString(long paramLong) throws IOException;
  
  long readDecimalLong() throws IOException;
  
  void readFully(Buffer paramBuffer, long paramLong) throws IOException;
  
  void readFully(byte[] paramArrayOfbyte) throws IOException;
  
  long readHexadecimalUnsignedLong() throws IOException;
  
  int readInt() throws IOException;
  
  int readIntLe() throws IOException;
  
  long readLong() throws IOException;
  
  long readLongLe() throws IOException;
  
  short readShort() throws IOException;
  
  short readShortLe() throws IOException;
  
  String readString(long paramLong, Charset paramCharset) throws IOException;
  
  String readString(Charset paramCharset) throws IOException;
  
  String readUtf8() throws IOException;
  
  String readUtf8(long paramLong) throws IOException;
  
  int readUtf8CodePoint() throws IOException;
  
  @Nullable
  String readUtf8Line() throws IOException;
  
  String readUtf8LineStrict() throws IOException;
  
  String readUtf8LineStrict(long paramLong) throws IOException;
  
  boolean request(long paramLong) throws IOException;
  
  void require(long paramLong) throws IOException;
  
  int select(Options paramOptions) throws IOException;
  
  void skip(long paramLong) throws IOException;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\BufferedSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */