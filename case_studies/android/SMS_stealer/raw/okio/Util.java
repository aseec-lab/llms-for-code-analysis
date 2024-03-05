package okio;

import java.nio.charset.Charset;

final class Util {
  public static final Charset UTF_8 = Charset.forName("UTF-8");
  
  public static boolean arrayRangeEquals(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) {
    for (byte b = 0; b < paramInt3; b++) {
      if (paramArrayOfbyte1[b + paramInt1] != paramArrayOfbyte2[b + paramInt2])
        return false; 
    } 
    return true;
  }
  
  public static void checkOffsetAndCount(long paramLong1, long paramLong2, long paramLong3) {
    if ((paramLong2 | paramLong3) >= 0L && paramLong2 <= paramLong1 && paramLong1 - paramLong2 >= paramLong3)
      return; 
    throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", new Object[] { Long.valueOf(paramLong1), Long.valueOf(paramLong2), Long.valueOf(paramLong3) }));
  }
  
  public static int reverseBytesInt(int paramInt) {
    return (paramInt & 0xFF) << 24 | (0xFF000000 & paramInt) >>> 24 | (0xFF0000 & paramInt) >>> 8 | (0xFF00 & paramInt) << 8;
  }
  
  public static long reverseBytesLong(long paramLong) {
    return (paramLong & 0xFFL) << 56L | (0xFF00000000000000L & paramLong) >>> 56L | (0xFF000000000000L & paramLong) >>> 40L | (0xFF0000000000L & paramLong) >>> 24L | (0xFF00000000L & paramLong) >>> 8L | (0xFF000000L & paramLong) << 8L | (0xFF0000L & paramLong) << 24L | (0xFF00L & paramLong) << 40L;
  }
  
  public static short reverseBytesShort(short paramShort) {
    int i = paramShort & 0xFFFF;
    return (short)((i & 0xFF) << 8 | (0xFF00 & i) >>> 8);
  }
  
  public static void sneakyRethrow(Throwable paramThrowable) {
    sneakyThrow2(paramThrowable);
  }
  
  private static <T extends Throwable> void sneakyThrow2(Throwable paramThrowable) throws T {
    throw (T)paramThrowable;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */