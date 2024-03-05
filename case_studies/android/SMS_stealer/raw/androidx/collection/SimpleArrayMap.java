package androidx.collection;

import java.util.ConcurrentModificationException;
import java.util.Map;

public class SimpleArrayMap<K, V> {
  private static final int BASE_SIZE = 4;
  
  private static final int CACHE_SIZE = 10;
  
  private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
  
  private static final boolean DEBUG = false;
  
  private static final String TAG = "ArrayMap";
  
  static Object[] mBaseCache;
  
  static int mBaseCacheSize;
  
  static Object[] mTwiceBaseCache;
  
  static int mTwiceBaseCacheSize;
  
  Object[] mArray;
  
  int[] mHashes;
  
  int mSize;
  
  public SimpleArrayMap() {
    this.mHashes = ContainerHelpers.EMPTY_INTS;
    this.mArray = ContainerHelpers.EMPTY_OBJECTS;
    this.mSize = 0;
  }
  
  public SimpleArrayMap(int paramInt) {
    if (paramInt == 0) {
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
    } else {
      allocArrays(paramInt);
    } 
    this.mSize = 0;
  }
  
  public SimpleArrayMap(SimpleArrayMap<K, V> paramSimpleArrayMap) {
    this();
    if (paramSimpleArrayMap != null)
      putAll(paramSimpleArrayMap); 
  }
  
  private void allocArrays(int paramInt) {
    // Byte code:
    //   0: iload_1
    //   1: bipush #8
    //   3: if_icmpne -> 75
    //   6: ldc androidx/collection/SimpleArrayMap
    //   8: monitorenter
    //   9: getstatic androidx/collection/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   12: ifnull -> 63
    //   15: getstatic androidx/collection/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   18: astore_2
    //   19: aload_0
    //   20: aload_2
    //   21: putfield mArray : [Ljava/lang/Object;
    //   24: aload_2
    //   25: iconst_0
    //   26: aaload
    //   27: checkcast [Ljava/lang/Object;
    //   30: putstatic androidx/collection/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   33: aload_0
    //   34: aload_2
    //   35: iconst_1
    //   36: aaload
    //   37: checkcast [I
    //   40: putfield mHashes : [I
    //   43: aload_2
    //   44: iconst_1
    //   45: aconst_null
    //   46: aastore
    //   47: aload_2
    //   48: iconst_0
    //   49: aconst_null
    //   50: aastore
    //   51: getstatic androidx/collection/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   54: iconst_1
    //   55: isub
    //   56: putstatic androidx/collection/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   59: ldc androidx/collection/SimpleArrayMap
    //   61: monitorexit
    //   62: return
    //   63: ldc androidx/collection/SimpleArrayMap
    //   65: monitorexit
    //   66: goto -> 149
    //   69: astore_2
    //   70: ldc androidx/collection/SimpleArrayMap
    //   72: monitorexit
    //   73: aload_2
    //   74: athrow
    //   75: iload_1
    //   76: iconst_4
    //   77: if_icmpne -> 149
    //   80: ldc androidx/collection/SimpleArrayMap
    //   82: monitorenter
    //   83: getstatic androidx/collection/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   86: ifnull -> 137
    //   89: getstatic androidx/collection/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   92: astore_2
    //   93: aload_0
    //   94: aload_2
    //   95: putfield mArray : [Ljava/lang/Object;
    //   98: aload_2
    //   99: iconst_0
    //   100: aaload
    //   101: checkcast [Ljava/lang/Object;
    //   104: putstatic androidx/collection/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   107: aload_0
    //   108: aload_2
    //   109: iconst_1
    //   110: aaload
    //   111: checkcast [I
    //   114: putfield mHashes : [I
    //   117: aload_2
    //   118: iconst_1
    //   119: aconst_null
    //   120: aastore
    //   121: aload_2
    //   122: iconst_0
    //   123: aconst_null
    //   124: aastore
    //   125: getstatic androidx/collection/SimpleArrayMap.mBaseCacheSize : I
    //   128: iconst_1
    //   129: isub
    //   130: putstatic androidx/collection/SimpleArrayMap.mBaseCacheSize : I
    //   133: ldc androidx/collection/SimpleArrayMap
    //   135: monitorexit
    //   136: return
    //   137: ldc androidx/collection/SimpleArrayMap
    //   139: monitorexit
    //   140: goto -> 149
    //   143: astore_2
    //   144: ldc androidx/collection/SimpleArrayMap
    //   146: monitorexit
    //   147: aload_2
    //   148: athrow
    //   149: aload_0
    //   150: iload_1
    //   151: newarray int
    //   153: putfield mHashes : [I
    //   156: aload_0
    //   157: iload_1
    //   158: iconst_1
    //   159: ishl
    //   160: anewarray java/lang/Object
    //   163: putfield mArray : [Ljava/lang/Object;
    //   166: return
    // Exception table:
    //   from	to	target	type
    //   9	43	69	finally
    //   51	62	69	finally
    //   63	66	69	finally
    //   70	73	69	finally
    //   83	117	143	finally
    //   125	136	143	finally
    //   137	140	143	finally
    //   144	147	143	finally
  }
  
  private static int binarySearchHashes(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    try {
      return ContainerHelpers.binarySearch(paramArrayOfint, paramInt1, paramInt2);
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new ConcurrentModificationException();
    } 
  }
  
  private static void freeArrays(int[] paramArrayOfint, Object[] paramArrayOfObject, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: arraylength
    //   2: bipush #8
    //   4: if_icmpne -> 73
    //   7: ldc androidx/collection/SimpleArrayMap
    //   9: monitorenter
    //   10: getstatic androidx/collection/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   13: bipush #10
    //   15: if_icmpge -> 61
    //   18: aload_1
    //   19: iconst_0
    //   20: getstatic androidx/collection/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   23: aastore
    //   24: aload_1
    //   25: iconst_1
    //   26: aload_0
    //   27: aastore
    //   28: iload_2
    //   29: iconst_1
    //   30: ishl
    //   31: iconst_1
    //   32: isub
    //   33: istore_2
    //   34: iload_2
    //   35: iconst_2
    //   36: if_icmplt -> 49
    //   39: aload_1
    //   40: iload_2
    //   41: aconst_null
    //   42: aastore
    //   43: iinc #2, -1
    //   46: goto -> 34
    //   49: aload_1
    //   50: putstatic androidx/collection/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   53: getstatic androidx/collection/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   56: iconst_1
    //   57: iadd
    //   58: putstatic androidx/collection/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   61: ldc androidx/collection/SimpleArrayMap
    //   63: monitorexit
    //   64: goto -> 145
    //   67: astore_0
    //   68: ldc androidx/collection/SimpleArrayMap
    //   70: monitorexit
    //   71: aload_0
    //   72: athrow
    //   73: aload_0
    //   74: arraylength
    //   75: iconst_4
    //   76: if_icmpne -> 145
    //   79: ldc androidx/collection/SimpleArrayMap
    //   81: monitorenter
    //   82: getstatic androidx/collection/SimpleArrayMap.mBaseCacheSize : I
    //   85: bipush #10
    //   87: if_icmpge -> 133
    //   90: aload_1
    //   91: iconst_0
    //   92: getstatic androidx/collection/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   95: aastore
    //   96: aload_1
    //   97: iconst_1
    //   98: aload_0
    //   99: aastore
    //   100: iload_2
    //   101: iconst_1
    //   102: ishl
    //   103: iconst_1
    //   104: isub
    //   105: istore_2
    //   106: iload_2
    //   107: iconst_2
    //   108: if_icmplt -> 121
    //   111: aload_1
    //   112: iload_2
    //   113: aconst_null
    //   114: aastore
    //   115: iinc #2, -1
    //   118: goto -> 106
    //   121: aload_1
    //   122: putstatic androidx/collection/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   125: getstatic androidx/collection/SimpleArrayMap.mBaseCacheSize : I
    //   128: iconst_1
    //   129: iadd
    //   130: putstatic androidx/collection/SimpleArrayMap.mBaseCacheSize : I
    //   133: ldc androidx/collection/SimpleArrayMap
    //   135: monitorexit
    //   136: goto -> 145
    //   139: astore_0
    //   140: ldc androidx/collection/SimpleArrayMap
    //   142: monitorexit
    //   143: aload_0
    //   144: athrow
    //   145: return
    // Exception table:
    //   from	to	target	type
    //   10	24	67	finally
    //   49	61	67	finally
    //   61	64	67	finally
    //   68	71	67	finally
    //   82	96	139	finally
    //   121	133	139	finally
    //   133	136	139	finally
    //   140	143	139	finally
  }
  
  public void clear() {
    int i = this.mSize;
    if (i > 0) {
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      this.mSize = 0;
      freeArrays(arrayOfInt, arrayOfObject, i);
    } 
    if (this.mSize <= 0)
      return; 
    throw new ConcurrentModificationException();
  }
  
  public boolean containsKey(Object paramObject) {
    boolean bool;
    if (indexOfKey(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean containsValue(Object paramObject) {
    boolean bool;
    if (indexOfValue(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void ensureCapacity(int paramInt) {
    int i = this.mSize;
    int[] arrayOfInt = this.mHashes;
    if (arrayOfInt.length < paramInt) {
      Object[] arrayOfObject = this.mArray;
      allocArrays(paramInt);
      if (this.mSize > 0) {
        System.arraycopy(arrayOfInt, 0, this.mHashes, 0, i);
        System.arraycopy(arrayOfObject, 0, this.mArray, 0, i << 1);
      } 
      freeArrays(arrayOfInt, arrayOfObject, i);
    } 
    if (this.mSize == i)
      return; 
    throw new ConcurrentModificationException();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject instanceof SimpleArrayMap) {
      SimpleArrayMap simpleArrayMap = (SimpleArrayMap)paramObject;
      if (size() != simpleArrayMap.size())
        return false; 
      byte b = 0;
      try {
        while (b < this.mSize) {
          K k = keyAt(b);
          paramObject = valueAt(b);
          Object object = simpleArrayMap.get(k);
          if (paramObject == null) {
            if (object != null || !simpleArrayMap.containsKey(k))
              return false; 
          } else {
            boolean bool = paramObject.equals(object);
            if (!bool)
              return false; 
          } 
          b++;
        } 
        return true;
      } catch (NullPointerException|ClassCastException nullPointerException) {
        return false;
      } 
    } 
    if (nullPointerException instanceof Map) {
      Map map = (Map)nullPointerException;
      if (size() != map.size())
        return false; 
      byte b = 0;
      try {
        while (b < this.mSize) {
          nullPointerException = (NullPointerException)keyAt(b);
          V v = valueAt(b);
          Object object = map.get(nullPointerException);
          if (v == null) {
            if (object != null || !map.containsKey(nullPointerException))
              return false; 
          } else {
            boolean bool = v.equals(object);
            if (!bool)
              return false; 
          } 
          b++;
        } 
        return true;
      } catch (NullPointerException|ClassCastException nullPointerException1) {}
    } 
    return false;
  }
  
  public V get(Object paramObject) {
    return getOrDefault(paramObject, null);
  }
  
  public V getOrDefault(Object paramObject, V paramV) {
    int i = indexOfKey(paramObject);
    if (i >= 0)
      paramV = (V)this.mArray[(i << 1) + 1]; 
    return paramV;
  }
  
  public int hashCode() {
    int[] arrayOfInt = this.mHashes;
    Object[] arrayOfObject = this.mArray;
    int j = this.mSize;
    boolean bool = true;
    byte b = 0;
    int i = 0;
    while (b < j) {
      int k;
      Object object = arrayOfObject[bool];
      int m = arrayOfInt[b];
      if (object == null) {
        k = 0;
      } else {
        k = object.hashCode();
      } 
      i += k ^ m;
      b++;
      bool += true;
    } 
    return i;
  }
  
  int indexOf(Object paramObject, int paramInt) {
    int j = this.mSize;
    if (j == 0)
      return -1; 
    int k = binarySearchHashes(this.mHashes, j, paramInt);
    if (k < 0)
      return k; 
    if (paramObject.equals(this.mArray[k << 1]))
      return k; 
    int i;
    for (i = k + 1; i < j && this.mHashes[i] == paramInt; i++) {
      if (paramObject.equals(this.mArray[i << 1]))
        return i; 
    } 
    for (j = k - 1; j >= 0 && this.mHashes[j] == paramInt; j--) {
      if (paramObject.equals(this.mArray[j << 1]))
        return j; 
    } 
    return i ^ 0xFFFFFFFF;
  }
  
  public int indexOfKey(Object paramObject) {
    int i;
    if (paramObject == null) {
      i = indexOfNull();
    } else {
      i = indexOf(paramObject, paramObject.hashCode());
    } 
    return i;
  }
  
  int indexOfNull() {
    int k = this.mSize;
    if (k == 0)
      return -1; 
    int j = binarySearchHashes(this.mHashes, k, 0);
    if (j < 0)
      return j; 
    if (this.mArray[j << 1] == null)
      return j; 
    int i;
    for (i = j + 1; i < k && this.mHashes[i] == 0; i++) {
      if (this.mArray[i << 1] == null)
        return i; 
    } 
    while (--j >= 0 && this.mHashes[j] == 0) {
      if (this.mArray[j << 1] == null)
        return j; 
      j--;
    } 
    return i ^ 0xFFFFFFFF;
  }
  
  int indexOfValue(Object paramObject) {
    int i = this.mSize * 2;
    Object[] arrayOfObject = this.mArray;
    if (paramObject == null) {
      for (byte b = 1; b < i; b += 2) {
        if (arrayOfObject[b] == null)
          return b >> 1; 
      } 
    } else {
      for (byte b = 1; b < i; b += 2) {
        if (paramObject.equals(arrayOfObject[b]))
          return b >> 1; 
      } 
    } 
    return -1;
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.mSize <= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public K keyAt(int paramInt) {
    return (K)this.mArray[paramInt << 1];
  }
  
  public V put(K paramK, V paramV) {
    Object[] arrayOfObject;
    int j;
    int k = this.mSize;
    if (paramK == null) {
      i = indexOfNull();
      j = 0;
    } else {
      j = paramK.hashCode();
      i = indexOf(paramK, j);
    } 
    if (i >= 0) {
      i = (i << 1) + 1;
      arrayOfObject = this.mArray;
      Object object = arrayOfObject[i];
      arrayOfObject[i] = paramV;
      return (V)object;
    } 
    int m = i ^ 0xFFFFFFFF;
    if (k >= this.mHashes.length) {
      i = 4;
      if (k >= 8) {
        i = (k >> 1) + k;
      } else if (k >= 4) {
        i = 8;
      } 
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject1 = this.mArray;
      allocArrays(i);
      if (k == this.mSize) {
        int[] arrayOfInt1 = this.mHashes;
        if (arrayOfInt1.length > 0) {
          System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, arrayOfInt.length);
          System.arraycopy(arrayOfObject1, 0, this.mArray, 0, arrayOfObject1.length);
        } 
        freeArrays(arrayOfInt, arrayOfObject1, k);
      } else {
        throw new ConcurrentModificationException();
      } 
    } 
    if (m < k) {
      int[] arrayOfInt = this.mHashes;
      i = m + 1;
      System.arraycopy(arrayOfInt, m, arrayOfInt, i, k - m);
      Object[] arrayOfObject1 = this.mArray;
      System.arraycopy(arrayOfObject1, m << 1, arrayOfObject1, i << 1, this.mSize - m << 1);
    } 
    int i = this.mSize;
    if (k == i) {
      int[] arrayOfInt = this.mHashes;
      if (m < arrayOfInt.length) {
        arrayOfInt[m] = j;
        Object[] arrayOfObject1 = this.mArray;
        j = m << 1;
        arrayOfObject1[j] = arrayOfObject;
        arrayOfObject1[j + 1] = paramV;
        this.mSize = i + 1;
        return null;
      } 
    } 
    throw new ConcurrentModificationException();
  }
  
  public void putAll(SimpleArrayMap<? extends K, ? extends V> paramSimpleArrayMap) {
    int i = paramSimpleArrayMap.mSize;
    ensureCapacity(this.mSize + i);
    int j = this.mSize;
    byte b = 0;
    if (j == 0) {
      if (i > 0) {
        System.arraycopy(paramSimpleArrayMap.mHashes, 0, this.mHashes, 0, i);
        System.arraycopy(paramSimpleArrayMap.mArray, 0, this.mArray, 0, i << 1);
        this.mSize = i;
      } 
    } else {
      while (b < i) {
        put(paramSimpleArrayMap.keyAt(b), paramSimpleArrayMap.valueAt(b));
        b++;
      } 
    } 
  }
  
  public V putIfAbsent(K paramK, V paramV) {
    V v2 = get(paramK);
    V v1 = v2;
    if (v2 == null)
      v1 = put(paramK, paramV); 
    return v1;
  }
  
  public V remove(Object paramObject) {
    int i = indexOfKey(paramObject);
    return (i >= 0) ? removeAt(i) : null;
  }
  
  public boolean remove(Object paramObject1, Object paramObject2) {
    int i = indexOfKey(paramObject1);
    if (i >= 0) {
      paramObject1 = valueAt(i);
      if (paramObject2 == paramObject1 || (paramObject2 != null && paramObject2.equals(paramObject1))) {
        removeAt(i);
        return true;
      } 
    } 
    return false;
  }
  
  public V removeAt(int paramInt) {
    Object[] arrayOfObject = this.mArray;
    int k = paramInt << 1;
    Object object = arrayOfObject[k + 1];
    int j = this.mSize;
    int i = 0;
    if (j <= 1) {
      freeArrays(this.mHashes, arrayOfObject, j);
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      paramInt = i;
    } else {
      int m = j - 1;
      int[] arrayOfInt = this.mHashes;
      int n = arrayOfInt.length;
      i = 8;
      if (n > 8 && j < arrayOfInt.length / 3) {
        if (j > 8)
          i = j + (j >> 1); 
        int[] arrayOfInt1 = this.mHashes;
        Object[] arrayOfObject1 = this.mArray;
        allocArrays(i);
        if (j == this.mSize) {
          if (paramInt > 0) {
            System.arraycopy(arrayOfInt1, 0, this.mHashes, 0, paramInt);
            System.arraycopy(arrayOfObject1, 0, this.mArray, 0, k);
          } 
          if (paramInt < m) {
            i = paramInt + 1;
            arrayOfInt = this.mHashes;
            n = m - paramInt;
            System.arraycopy(arrayOfInt1, i, arrayOfInt, paramInt, n);
            System.arraycopy(arrayOfObject1, i << 1, this.mArray, k, n << 1);
          } 
        } else {
          throw new ConcurrentModificationException();
        } 
      } else {
        if (paramInt < m) {
          arrayOfInt = this.mHashes;
          i = paramInt + 1;
          n = m - paramInt;
          System.arraycopy(arrayOfInt, i, arrayOfInt, paramInt, n);
          Object[] arrayOfObject2 = this.mArray;
          System.arraycopy(arrayOfObject2, i << 1, arrayOfObject2, k, n << 1);
        } 
        Object[] arrayOfObject1 = this.mArray;
        paramInt = m << 1;
        arrayOfObject1[paramInt] = null;
        arrayOfObject1[paramInt + 1] = null;
      } 
      paramInt = m;
    } 
    if (j == this.mSize) {
      this.mSize = paramInt;
      return (V)object;
    } 
    throw new ConcurrentModificationException();
  }
  
  public V replace(K paramK, V paramV) {
    int i = indexOfKey(paramK);
    return (i >= 0) ? setValueAt(i, paramV) : null;
  }
  
  public boolean replace(K paramK, V paramV1, V paramV2) {
    int i = indexOfKey(paramK);
    if (i >= 0) {
      paramK = (K)valueAt(i);
      if (paramK == paramV1 || (paramV1 != null && paramV1.equals(paramK))) {
        setValueAt(i, paramV2);
        return true;
      } 
    } 
    return false;
  }
  
  public V setValueAt(int paramInt, V paramV) {
    paramInt = (paramInt << 1) + 1;
    Object[] arrayOfObject = this.mArray;
    Object object = arrayOfObject[paramInt];
    arrayOfObject[paramInt] = paramV;
    return (V)object;
  }
  
  public int size() {
    return this.mSize;
  }
  
  public String toString() {
    if (isEmpty())
      return "{}"; 
    StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
    stringBuilder.append('{');
    for (byte b = 0; b < this.mSize; b++) {
      if (b > 0)
        stringBuilder.append(", "); 
      V v = (V)keyAt(b);
      if (v != this) {
        stringBuilder.append(v);
      } else {
        stringBuilder.append("(this Map)");
      } 
      stringBuilder.append('=');
      v = valueAt(b);
      if (v != this) {
        stringBuilder.append(v);
      } else {
        stringBuilder.append("(this Map)");
      } 
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public V valueAt(int paramInt) {
    return (V)this.mArray[(paramInt << 1) + 1];
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\collection\SimpleArrayMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */