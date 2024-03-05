package okio;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public final class GzipSink implements Sink {
  private boolean closed;
  
  private final CRC32 crc = new CRC32();
  
  private final Deflater deflater;
  
  private final DeflaterSink deflaterSink;
  
  private final BufferedSink sink;
  
  public GzipSink(Sink paramSink) {
    if (paramSink != null) {
      this.deflater = new Deflater(-1, true);
      this.sink = Okio.buffer(paramSink);
      this.deflaterSink = new DeflaterSink(this.sink, this.deflater);
      writeHeader();
      return;
    } 
    throw new IllegalArgumentException("sink == null");
  }
  
  private void updateCrc(Buffer paramBuffer, long paramLong) {
    for (Segment segment = paramBuffer.head; paramLong > 0L; segment = segment.next) {
      int i = (int)Math.min(paramLong, (segment.limit - segment.pos));
      this.crc.update(segment.data, segment.pos, i);
      paramLong -= i;
    } 
  }
  
  private void writeFooter() throws IOException {
    this.sink.writeIntLe((int)this.crc.getValue());
    this.sink.writeIntLe((int)this.deflater.getBytesRead());
  }
  
  private void writeHeader() {
    Buffer buffer = this.sink.buffer();
    buffer.writeShort(8075);
    buffer.writeByte(8);
    buffer.writeByte(0);
    buffer.writeInt(0);
    buffer.writeByte(0);
    buffer.writeByte(0);
  }
  
  public void close() throws IOException {
    Exception exception1;
    if (this.closed)
      return; 
    Exception exception2 = null;
    try {
      this.deflaterSink.finishDeflate();
      writeFooter();
    } finally {}
    try {
    
    } finally {
      Exception exception = null;
      exception1 = exception2;
    } 
    try {
    
    } finally {
      Exception exception = null;
      exception2 = exception1;
    } 
    this.closed = true;
    if (exception2 != null)
      Util.sneakyRethrow(exception2); 
  }
  
  public Deflater deflater() {
    return this.deflater;
  }
  
  public void flush() throws IOException {
    this.deflaterSink.flush();
  }
  
  public Timeout timeout() {
    return this.sink.timeout();
  }
  
  public void write(Buffer paramBuffer, long paramLong) throws IOException {
    int i = paramLong cmp 0L;
    if (i >= 0) {
      if (i == 0)
        return; 
      updateCrc(paramBuffer, paramLong);
      this.deflaterSink.write(paramBuffer, paramLong);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("byteCount < 0: ");
    stringBuilder.append(paramLong);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\GzipSink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */