package okio;

import javax.annotation.Nullable;

final class Segment {
  static final int SHARE_MINIMUM = 1024;
  
  static final int SIZE = 8192;
  
  final byte[] data = new byte[8192];
  
  int limit;
  
  Segment next;
  
  boolean owner;
  
  int pos;
  
  Segment prev;
  
  boolean shared;
  
  Segment() {
    this.owner = true;
    this.shared = false;
  }
  
  Segment(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    this.pos = paramInt1;
    this.limit = paramInt2;
    this.shared = paramBoolean1;
    this.owner = paramBoolean2;
  }
  
  public void compact() {
    Segment segment = this.prev;
    if (segment != this) {
      int i;
      if (!segment.owner)
        return; 
      int j = this.limit - this.pos;
      int k = segment.limit;
      if (segment.shared) {
        i = 0;
      } else {
        i = segment.pos;
      } 
      if (j > 8192 - k + i)
        return; 
      writeTo(this.prev, j);
      pop();
      SegmentPool.recycle(this);
      return;
    } 
    throw new IllegalStateException();
  }
  
  @Nullable
  public Segment pop() {
    Segment segment1 = this.next;
    if (segment1 == this)
      segment1 = null; 
    Segment segment2 = this.prev;
    segment2.next = this.next;
    this.next.prev = segment2;
    this.next = null;
    this.prev = null;
    return segment1;
  }
  
  public Segment push(Segment paramSegment) {
    paramSegment.prev = this;
    paramSegment.next = this.next;
    this.next.prev = paramSegment;
    this.next = paramSegment;
    return paramSegment;
  }
  
  Segment sharedCopy() {
    this.shared = true;
    return new Segment(this.data, this.pos, this.limit, true, false);
  }
  
  public Segment split(int paramInt) {
    if (paramInt > 0 && paramInt <= this.limit - this.pos) {
      Segment segment;
      if (paramInt >= 1024) {
        segment = sharedCopy();
      } else {
        segment = SegmentPool.take();
        System.arraycopy(this.data, this.pos, segment.data, 0, paramInt);
      } 
      segment.limit = segment.pos + paramInt;
      this.pos += paramInt;
      this.prev.push(segment);
      return segment;
    } 
    throw new IllegalArgumentException();
  }
  
  Segment unsharedCopy() {
    return new Segment((byte[])this.data.clone(), this.pos, this.limit, false, true);
  }
  
  public void writeTo(Segment paramSegment, int paramInt) {
    if (paramSegment.owner) {
      int i = paramSegment.limit;
      if (i + paramInt > 8192)
        if (!paramSegment.shared) {
          int j = paramSegment.pos;
          if (i + paramInt - j <= 8192) {
            byte[] arrayOfByte = paramSegment.data;
            System.arraycopy(arrayOfByte, j, arrayOfByte, 0, i - j);
            paramSegment.limit -= paramSegment.pos;
            paramSegment.pos = 0;
          } else {
            throw new IllegalArgumentException();
          } 
        } else {
          throw new IllegalArgumentException();
        }  
      System.arraycopy(this.data, this.pos, paramSegment.data, paramSegment.limit, paramInt);
      paramSegment.limit += paramInt;
      this.pos += paramInt;
      return;
    } 
    throw new IllegalArgumentException();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\Segment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */