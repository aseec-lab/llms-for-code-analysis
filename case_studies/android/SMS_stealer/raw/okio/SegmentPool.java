package okio;

import javax.annotation.Nullable;

final class SegmentPool {
  static final long MAX_SIZE = 65536L;
  
  static long byteCount;
  
  @Nullable
  static Segment next;
  
  static void recycle(Segment paramSegment) {
    // Byte code:
    //   0: aload_0
    //   1: getfield next : Lokio/Segment;
    //   4: ifnonnull -> 84
    //   7: aload_0
    //   8: getfield prev : Lokio/Segment;
    //   11: ifnonnull -> 84
    //   14: aload_0
    //   15: getfield shared : Z
    //   18: ifeq -> 22
    //   21: return
    //   22: ldc okio/SegmentPool
    //   24: monitorenter
    //   25: getstatic okio/SegmentPool.byteCount : J
    //   28: ldc2_w 8192
    //   31: ladd
    //   32: ldc2_w 65536
    //   35: lcmp
    //   36: ifle -> 43
    //   39: ldc okio/SegmentPool
    //   41: monitorexit
    //   42: return
    //   43: getstatic okio/SegmentPool.byteCount : J
    //   46: ldc2_w 8192
    //   49: ladd
    //   50: putstatic okio/SegmentPool.byteCount : J
    //   53: aload_0
    //   54: getstatic okio/SegmentPool.next : Lokio/Segment;
    //   57: putfield next : Lokio/Segment;
    //   60: aload_0
    //   61: iconst_0
    //   62: putfield limit : I
    //   65: aload_0
    //   66: iconst_0
    //   67: putfield pos : I
    //   70: aload_0
    //   71: putstatic okio/SegmentPool.next : Lokio/Segment;
    //   74: ldc okio/SegmentPool
    //   76: monitorexit
    //   77: return
    //   78: astore_0
    //   79: ldc okio/SegmentPool
    //   81: monitorexit
    //   82: aload_0
    //   83: athrow
    //   84: new java/lang/IllegalArgumentException
    //   87: dup
    //   88: invokespecial <init> : ()V
    //   91: athrow
    // Exception table:
    //   from	to	target	type
    //   25	42	78	finally
    //   43	77	78	finally
    //   79	82	78	finally
  }
  
  static Segment take() {
    // Byte code:
    //   0: ldc okio/SegmentPool
    //   2: monitorenter
    //   3: getstatic okio/SegmentPool.next : Lokio/Segment;
    //   6: ifnull -> 40
    //   9: getstatic okio/SegmentPool.next : Lokio/Segment;
    //   12: astore_0
    //   13: aload_0
    //   14: getfield next : Lokio/Segment;
    //   17: putstatic okio/SegmentPool.next : Lokio/Segment;
    //   20: aload_0
    //   21: aconst_null
    //   22: putfield next : Lokio/Segment;
    //   25: getstatic okio/SegmentPool.byteCount : J
    //   28: ldc2_w 8192
    //   31: lsub
    //   32: putstatic okio/SegmentPool.byteCount : J
    //   35: ldc okio/SegmentPool
    //   37: monitorexit
    //   38: aload_0
    //   39: areturn
    //   40: ldc okio/SegmentPool
    //   42: monitorexit
    //   43: new okio/Segment
    //   46: dup
    //   47: invokespecial <init> : ()V
    //   50: areturn
    //   51: astore_0
    //   52: ldc okio/SegmentPool
    //   54: monitorexit
    //   55: aload_0
    //   56: athrow
    // Exception table:
    //   from	to	target	type
    //   3	38	51	finally
    //   40	43	51	finally
    //   52	55	51	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\SegmentPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */