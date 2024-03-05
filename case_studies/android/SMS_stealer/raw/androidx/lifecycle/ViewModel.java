package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewModel {
  private final Map<String, Object> mBagOfTags = new HashMap<String, Object>();
  
  private volatile boolean mCleared = false;
  
  private static void closeWithRuntimeException(Object paramObject) {
    if (paramObject instanceof Closeable)
      try {
        ((Closeable)paramObject).close();
      } catch (IOException iOException) {
        throw new RuntimeException(iOException);
      }  
  }
  
  final void clear() {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield mCleared : Z
    //   5: aload_0
    //   6: getfield mBagOfTags : Ljava/util/Map;
    //   9: astore_1
    //   10: aload_1
    //   11: ifnull -> 62
    //   14: aload_1
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield mBagOfTags : Ljava/util/Map;
    //   20: invokeinterface values : ()Ljava/util/Collection;
    //   25: invokeinterface iterator : ()Ljava/util/Iterator;
    //   30: astore_2
    //   31: aload_2
    //   32: invokeinterface hasNext : ()Z
    //   37: ifeq -> 52
    //   40: aload_2
    //   41: invokeinterface next : ()Ljava/lang/Object;
    //   46: invokestatic closeWithRuntimeException : (Ljava/lang/Object;)V
    //   49: goto -> 31
    //   52: aload_1
    //   53: monitorexit
    //   54: goto -> 62
    //   57: astore_2
    //   58: aload_1
    //   59: monitorexit
    //   60: aload_2
    //   61: athrow
    //   62: aload_0
    //   63: invokevirtual onCleared : ()V
    //   66: return
    // Exception table:
    //   from	to	target	type
    //   16	31	57	finally
    //   31	49	57	finally
    //   52	54	57	finally
    //   58	60	57	finally
  }
  
  <T> T getTag(String paramString) {
    synchronized (this.mBagOfTags) {
      paramString = (String)this.mBagOfTags.get(paramString);
      return (T)paramString;
    } 
  }
  
  protected void onCleared() {}
  
  <T> T setTagIfAbsent(String paramString, T paramT) {
    synchronized (this.mBagOfTags) {
      Object object = this.mBagOfTags.get(paramString);
      if (object == null)
        this.mBagOfTags.put(paramString, paramT); 
      if (object != null)
        paramT = (T)object; 
      if (this.mCleared)
        closeWithRuntimeException(paramT); 
      return paramT;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\ViewModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */