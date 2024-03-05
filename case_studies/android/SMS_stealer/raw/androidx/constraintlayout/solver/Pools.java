package androidx.constraintlayout.solver;

final class Pools {
  private static final boolean DEBUG = false;
  
  static interface Pool<T> {
    T acquire();
    
    boolean release(T param1T);
    
    void releaseAll(T[] param1ArrayOfT, int param1Int);
  }
  
  static class SimplePool<T> implements Pool<T> {
    private final Object[] mPool;
    
    private int mPoolSize;
    
    SimplePool(int param1Int) {
      if (param1Int > 0) {
        this.mPool = new Object[param1Int];
        return;
      } 
      throw new IllegalArgumentException("The max pool size must be > 0");
    }
    
    private boolean isInPool(T param1T) {
      for (byte b = 0; b < this.mPoolSize; b++) {
        if (this.mPool[b] == param1T)
          return true; 
      } 
      return false;
    }
    
    public T acquire() {
      int i = this.mPoolSize;
      if (i > 0) {
        int j = i - 1;
        Object[] arrayOfObject = this.mPool;
        Object object = arrayOfObject[j];
        arrayOfObject[j] = null;
        this.mPoolSize = i - 1;
        return (T)object;
      } 
      return null;
    }
    
    public boolean release(T param1T) {
      int i = this.mPoolSize;
      Object[] arrayOfObject = this.mPool;
      if (i < arrayOfObject.length) {
        arrayOfObject[i] = param1T;
        this.mPoolSize = i + 1;
        return true;
      } 
      return false;
    }
    
    public void releaseAll(T[] param1ArrayOfT, int param1Int) {
      int i = param1Int;
      if (param1Int > param1ArrayOfT.length)
        i = param1ArrayOfT.length; 
      for (param1Int = 0; param1Int < i; param1Int++) {
        T t = param1ArrayOfT[param1Int];
        int j = this.mPoolSize;
        Object[] arrayOfObject = this.mPool;
        if (j < arrayOfObject.length) {
          arrayOfObject[j] = t;
          this.mPoolSize = j + 1;
        } 
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\Pools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */