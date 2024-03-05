package androidx.collection;

public class SparseArrayCompat<E> implements Cloneable {
  private static final Object DELETED = new Object();
  
  private boolean mGarbage = false;
  
  private int[] mKeys;
  
  private int mSize;
  
  private Object[] mValues;
  
  public SparseArrayCompat() {
    this(10);
  }
  
  public SparseArrayCompat(int paramInt) {
    if (paramInt == 0) {
      this.mKeys = ContainerHelpers.EMPTY_INTS;
      this.mValues = ContainerHelpers.EMPTY_OBJECTS;
    } else {
      paramInt = ContainerHelpers.idealIntArraySize(paramInt);
      this.mKeys = new int[paramInt];
      this.mValues = new Object[paramInt];
    } 
  }
  
  private void gc() {
    int k = this.mSize;
    int[] arrayOfInt = this.mKeys;
    Object[] arrayOfObject = this.mValues;
    int j = 0;
    int i;
    for (i = 0; j < k; i = m) {
      Object object = arrayOfObject[j];
      int m = i;
      if (object != DELETED) {
        if (j != i) {
          arrayOfInt[i] = arrayOfInt[j];
          arrayOfObject[i] = object;
          arrayOfObject[j] = null;
        } 
        m = i + 1;
      } 
      j++;
    } 
    this.mGarbage = false;
    this.mSize = i;
  }
  
  public void append(int paramInt, E paramE) {
    int i = this.mSize;
    if (i != 0 && paramInt <= this.mKeys[i - 1]) {
      put(paramInt, paramE);
      return;
    } 
    if (this.mGarbage && this.mSize >= this.mKeys.length)
      gc(); 
    i = this.mSize;
    if (i >= this.mKeys.length) {
      int j = ContainerHelpers.idealIntArraySize(i + 1);
      int[] arrayOfInt1 = new int[j];
      Object[] arrayOfObject1 = new Object[j];
      int[] arrayOfInt2 = this.mKeys;
      System.arraycopy(arrayOfInt2, 0, arrayOfInt1, 0, arrayOfInt2.length);
      Object[] arrayOfObject2 = this.mValues;
      System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, arrayOfObject2.length);
      this.mKeys = arrayOfInt1;
      this.mValues = arrayOfObject1;
    } 
    this.mKeys[i] = paramInt;
    this.mValues[i] = paramE;
    this.mSize = i + 1;
  }
  
  public void clear() {
    int i = this.mSize;
    Object[] arrayOfObject = this.mValues;
    for (byte b = 0; b < i; b++)
      arrayOfObject[b] = null; 
    this.mSize = 0;
    this.mGarbage = false;
  }
  
  public SparseArrayCompat<E> clone() {
    try {
      SparseArrayCompat<E> sparseArrayCompat = (SparseArrayCompat)super.clone();
      sparseArrayCompat.mKeys = (int[])this.mKeys.clone();
      sparseArrayCompat.mValues = (Object[])this.mValues.clone();
      return sparseArrayCompat;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new AssertionError(cloneNotSupportedException);
    } 
  }
  
  public boolean containsKey(int paramInt) {
    boolean bool;
    if (indexOfKey(paramInt) >= 0) {
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
  public void delete(int paramInt) {
    remove(paramInt);
  }
  
  public E get(int paramInt) {
    return get(paramInt, null);
  }
  
  public E get(int paramInt, E paramE) {
    paramInt = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
    if (paramInt >= 0) {
      Object[] arrayOfObject = this.mValues;
      if (arrayOfObject[paramInt] != DELETED)
        return (E)arrayOfObject[paramInt]; 
    } 
    return paramE;
  }
  
  public int indexOfKey(int paramInt) {
    if (this.mGarbage)
      gc(); 
    return ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
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
  
  public int keyAt(int paramInt) {
    if (this.mGarbage)
      gc(); 
    return this.mKeys[paramInt];
  }
  
  public void put(int paramInt, E paramE) {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
    if (i >= 0) {
      this.mValues[i] = paramE;
    } else {
      int j = i ^ 0xFFFFFFFF;
      if (j < this.mSize) {
        Object[] arrayOfObject = this.mValues;
        if (arrayOfObject[j] == DELETED) {
          this.mKeys[j] = paramInt;
          arrayOfObject[j] = paramE;
          return;
        } 
      } 
      i = j;
      if (this.mGarbage) {
        i = j;
        if (this.mSize >= this.mKeys.length) {
          gc();
          i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt) ^ 0xFFFFFFFF;
        } 
      } 
      j = this.mSize;
      if (j >= this.mKeys.length) {
        j = ContainerHelpers.idealIntArraySize(j + 1);
        int[] arrayOfInt1 = new int[j];
        Object[] arrayOfObject1 = new Object[j];
        int[] arrayOfInt2 = this.mKeys;
        System.arraycopy(arrayOfInt2, 0, arrayOfInt1, 0, arrayOfInt2.length);
        Object[] arrayOfObject2 = this.mValues;
        System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, arrayOfObject2.length);
        this.mKeys = arrayOfInt1;
        this.mValues = arrayOfObject1;
      } 
      j = this.mSize;
      if (j - i != 0) {
        int[] arrayOfInt = this.mKeys;
        int k = i + 1;
        System.arraycopy(arrayOfInt, i, arrayOfInt, k, j - i);
        Object[] arrayOfObject = this.mValues;
        System.arraycopy(arrayOfObject, i, arrayOfObject, k, this.mSize - i);
      } 
      this.mKeys[i] = paramInt;
      this.mValues[i] = paramE;
      this.mSize++;
    } 
  }
  
  public void putAll(SparseArrayCompat<? extends E> paramSparseArrayCompat) {
    int i = paramSparseArrayCompat.size();
    for (byte b = 0; b < i; b++)
      put(paramSparseArrayCompat.keyAt(b), paramSparseArrayCompat.valueAt(b)); 
  }
  
  public E putIfAbsent(int paramInt, E paramE) {
    E e = get(paramInt);
    if (e == null)
      put(paramInt, paramE); 
    return e;
  }
  
  public void remove(int paramInt) {
    paramInt = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
    if (paramInt >= 0) {
      Object[] arrayOfObject = this.mValues;
      Object object2 = arrayOfObject[paramInt];
      Object object1 = DELETED;
      if (object2 != object1) {
        arrayOfObject[paramInt] = object1;
        this.mGarbage = true;
      } 
    } 
  }
  
  public boolean remove(int paramInt, Object paramObject) {
    paramInt = indexOfKey(paramInt);
    if (paramInt >= 0) {
      E e = valueAt(paramInt);
      if (paramObject == e || (paramObject != null && paramObject.equals(e))) {
        removeAt(paramInt);
        return true;
      } 
    } 
    return false;
  }
  
  public void removeAt(int paramInt) {
    Object[] arrayOfObject = this.mValues;
    Object object2 = arrayOfObject[paramInt];
    Object object1 = DELETED;
    if (object2 != object1) {
      arrayOfObject[paramInt] = object1;
      this.mGarbage = true;
    } 
  }
  
  public void removeAtRange(int paramInt1, int paramInt2) {
    paramInt2 = Math.min(this.mSize, paramInt2 + paramInt1);
    while (paramInt1 < paramInt2) {
      removeAt(paramInt1);
      paramInt1++;
    } 
  }
  
  public E replace(int paramInt, E paramE) {
    paramInt = indexOfKey(paramInt);
    if (paramInt >= 0) {
      Object[] arrayOfObject = this.mValues;
      Object object = arrayOfObject[paramInt];
      arrayOfObject[paramInt] = paramE;
      return (E)object;
    } 
    return null;
  }
  
  public boolean replace(int paramInt, E paramE1, E paramE2) {
    paramInt = indexOfKey(paramInt);
    if (paramInt >= 0) {
      Object object = this.mValues[paramInt];
      if (object == paramE1 || (paramE1 != null && paramE1.equals(object))) {
        this.mValues[paramInt] = paramE2;
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


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\collection\SparseArrayCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */