package androidx.collection;

class ContainerHelpers {
  static final int[] EMPTY_INTS = new int[0];
  
  static final long[] EMPTY_LONGS = new long[0];
  
  static final Object[] EMPTY_OBJECTS = new Object[0];
  
  static int binarySearch(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    paramInt1--;
    int i = 0;
    while (i <= paramInt1) {
      int j = i + paramInt1 >>> 1;
      int k = paramArrayOfint[j];
      if (k < paramInt2) {
        i = j + 1;
        continue;
      } 
      if (k > paramInt2) {
        paramInt1 = j - 1;
        continue;
      } 
      return j;
    } 
    return i ^ 0xFFFFFFFF;
  }
  
  static int binarySearch(long[] paramArrayOflong, int paramInt, long paramLong) {
    paramInt--;
    int i = 0;
    while (i <= paramInt) {
      int j = i + paramInt >>> 1;
      int k = paramArrayOflong[j] cmp paramLong;
      if (k < 0) {
        i = j + 1;
        continue;
      } 
      if (k > 0) {
        paramInt = j - 1;
        continue;
      } 
      return j;
    } 
    return i ^ 0xFFFFFFFF;
  }
  
  public static boolean equal(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2)));
  }
  
  public static int idealByteArraySize(int paramInt) {
    for (byte b = 4; b < 32; b++) {
      int i = (1 << b) - 12;
      if (paramInt <= i)
        return i; 
    } 
    return paramInt;
  }
  
  public static int idealIntArraySize(int paramInt) {
    return idealByteArraySize(paramInt * 4) / 4;
  }
  
  public static int idealLongArraySize(int paramInt) {
    return idealByteArraySize(paramInt * 8) / 8;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\collection\ContainerHelpers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */