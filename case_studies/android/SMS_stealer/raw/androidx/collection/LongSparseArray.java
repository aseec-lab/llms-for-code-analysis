package androidx.collection;

public class LongSparseArray<E> implements Cloneable {
  private static final Object DELETED = new Object();
  
  private boolean mGarbage = false;
  
  private long[] mKeys;
  
  private int mSize;
  
  private Object[] mValues;
  
  public LongSparseArray() {
    this(10);
  }
  
  public LongSparseArray(int paramInt) {
    if (paramInt == 0) {
      this.mKeys = ContainerHelpers.EMPTY_LONGS;
      this.mValues = ContainerHelpers.EMPTY_OBJECTS;
    } else {
      paramInt = ContainerHelpers.idealLongArraySize(paramInt);
      this.mKeys = new long[paramInt];
      this.mValues = new Object[paramInt];
    } 
  }
  
  private void gc() {
    int k = this.mSize;
    long[] arrayOfLong = this.mKeys;
    Object[] arrayOfObject = this.mValues;
    int i = 0;
    int j;
    for (j = 0; i < k; j = m) {
      Object object = arrayOfObject[i];
      int m = j;
      if (object != DELETED) {
        if (i != j) {
          arrayOfLong[j] = arrayOfLong[i];
          arrayOfObject[j] = object;
          arrayOfObject[i] = null;
        } 
        m = j + 1;
      } 
      i++;
    } 
    this.mGarbage = false;
    this.mSize = j;
  }
  
  public void append(long paramLong, E paramE) {
    int i = this.mSize;
    if (i != 0 && paramLong <= this.mKeys[i - 1]) {
      put(paramLong, paramE);
      return;
    } 
    if (this.mGarbage && this.mSize >= this.mKeys.length)
      gc(); 
    int j = this.mSize;
    if (j >= this.mKeys.length) {
      i = ContainerHelpers.idealLongArraySize(j + 1);
      long[] arrayOfLong1 = new long[i];
      Object[] arrayOfObject1 = new Object[i];
      long[] arrayOfLong2 = this.mKeys;
      System.arraycopy(arrayOfLong2, 0, arrayOfLong1, 0, arrayOfLong2.length);
      Object[] arrayOfObject2 = this.mValues;
      System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, arrayOfObject2.length);
      this.mKeys = arrayOfLong1;
      this.mValues = arrayOfObject1;
    } 
    this.mKeys[j] = paramLong;
    this.mValues[j] = paramE;
    this.mSize = j + 1;
  }
  
  public void clear() {
    int i = this.mSize;
    Object[] arrayOfObject = this.mValues;
    for (byte b = 0; b < i; b++)
      arrayOfObject[b] = null; 
    this.mSize = 0;
    this.mGarbage = false;
  }
  
  public LongSparseArray<E> clone() {
    try {
      LongSparseArray<E> longSparseArray = (LongSparseArray)super.clone();
      longSparseArray.mKeys = (long[])this.mKeys.clone();
      longSparseArray.mValues = (Object[])this.mValues.clone();
      return longSparseArray;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new AssertionError(cloneNotSupportedException);
    } 
  }
  
  public boolean containsKey(long paramLong) {
    boolean bool;
    if (indexOfKey(paramLong) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean containsValue(E paramE) {
    boolean bool;
    if (indexOfValue(paramE) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  @Deprecated
  public void delete(long paramLong) {
    remove(paramLong);
  }
  
  public E get(long paramLong) {
    return get(paramLong, null);
  }
  
  public E get(long paramLong, E paramE) {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0) {
      Object[] arrayOfObject = this.mValues;
      if (arrayOfObject[i] != DELETED)
        return (E)arrayOfObject[i]; 
    } 
    return paramE;
  }
  
  public int indexOfKey(long paramLong) {
    if (this.mGarbage)
      gc(); 
    return ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
  }
  
  public int indexOfValue(E paramE) {
    if (this.mGarbage)
      gc(); 
    for (byte b = 0; b < this.mSize; b++) {
      if (this.mValues[b] == paramE)
        return b; 
    } 
    return -1;
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (size() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public long keyAt(int paramInt) {
    if (this.mGarbage)
      gc(); 
    return this.mKeys[paramInt];
  }
  
  public void put(long paramLong, E paramE) {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0) {
      this.mValues[i] = paramE;
    } else {
      int j = i ^ 0xFFFFFFFF;
      if (j < this.mSize) {
        Object[] arrayOfObject = this.mValues;
        if (arrayOfObject[j] == DELETED) {
          this.mKeys[j] = paramLong;
          arrayOfObject[j] = paramE;
          return;
        } 
      } 
      i = j;
      if (this.mGarbage) {
        i = j;
        if (this.mSize >= this.mKeys.length) {
          gc();
          i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong) ^ 0xFFFFFFFF;
        } 
      } 
      j = this.mSize;
      if (j >= this.mKeys.length) {
        j = ContainerHelpers.idealLongArraySize(j + 1);
        long[] arrayOfLong1 = new long[j];
        Object[] arrayOfObject1 = new Object[j];
        long[] arrayOfLong2 = this.mKeys;
        System.arraycopy(arrayOfLong2, 0, arrayOfLong1, 0, arrayOfLong2.length);
        Object[] arrayOfObject2 = this.mValues;
        System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, arrayOfObject2.length);
        this.mKeys = arrayOfLong1;
        this.mValues = arrayOfObject1;
      } 
      j = this.mSize;
      if (j - i != 0) {
        long[] arrayOfLong = this.mKeys;
        int k = i + 1;
        System.arraycopy(arrayOfLong, i, arrayOfLong, k, j - i);
        Object[] arrayOfObject = this.mValues;
        System.arraycopy(arrayOfObject, i, arrayOfObject, k, this.mSize - i);
      } 
      this.mKeys[i] = paramLong;
      this.mValues[i] = paramE;
      this.mSize++;
    } 
  }
  
  public void putAll(LongSparseArray<? extends E> paramLongSparseArray) {
    int i = paramLongSparseArray.size();
    for (byte b = 0; b < i; b++)
      put(paramLongSparseArray.keyAt(b), paramLongSparseArray.valueAt(b)); 
  }
  
  public E putIfAbsent(long paramLong, E paramE) {
    E e = get(paramLong);
    if (e == null)
      put(paramLong, paramE); 
    return e;
  }
  
  public void remove(long paramLong) {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0) {
      Object[] arrayOfObject = this.mValues;
      Object object2 = arrayOfObject[i];
      Object object1 = DELETED;
      if (object2 != object1) {
        arrayOfObject[i] = object1;
        this.mGarbage = true;
      } 
    } 
  }
  
  public boolean remove(long paramLong, Object paramObject) {
    int i = indexOfKey(paramLong);
    if (i >= 0) {
      E e = valueAt(i);
      if (paramObject == e || (paramObject != null && paramObject.equals(e))) {
        removeAt(i);
        return true;
      } 
    } 
    return false;
  }
  
  public void removeAt(int paramInt) {
    Object[] arrayOfObject = this.mValues;
    Object object1 = arrayOfObject[paramInt];
    Object object2 = DELETED;
    if (object1 != object2) {
      arrayOfObject[paramInt] = object2;
      this.mGarbage = true;
    } 
  }
  
  public E replace(long paramLong, E paramE) {
    int i = indexOfKey(paramLong);
    if (i >= 0) {
      Object[] arrayOfObject = this.mValues;
      Object object = arrayOfObject[i];
      arrayOfObject[i] = paramE;
      return (E)object;
    } 
    return null;
  }
  
  public boolean replace(long paramLong, E paramE1, E paramE2) {
    int i = indexOfKey(paramLong);
    if (i >= 0) {
      Object object = this.mValues[i];
      if (object == paramE1 || (paramE1 != null && paramE1.equals(object))) {
        this.mValues[i] = paramE2;
        return true;
      } 
    } 
    return false;
  }
  
  public void setValueAt(int paramInt, E paramE) {
    if (this.mGarbage)
      gc(); 
    this.mValues[paramInt] = paramE;
  }
  
  public int size() {
    if (this.mGarbage)
      gc(); 
    return this.mSize;
  }
  
  public String toString() {
    if (size() <= 0)
      return "{}"; 
    StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
    stringBuilder.append('{');
    for (byte b = 0; b < this.mSize; b++) {
      if (b > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(keyAt(b));
      stringBuilder.append('=');
      E e = valueAt(b);
      if (e != this) {
        stringBuilder.append(e);
      } else {
        stringBuilder.append("(this Map)");
      } 
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public E valueAt(int paramInt) {
    if (this.mGarbage)
      gc(); 
    return (E)this.mValues[paramInt];
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\collection\LongSparseArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */