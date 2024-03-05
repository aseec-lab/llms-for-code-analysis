package androidx.arch.core.internal;

import java.util.HashMap;
import java.util.Map;

public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
  private HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap<K, SafeIterableMap.Entry<K, V>>();
  
  public Map.Entry<K, V> ceil(K paramK) {
    return contains(paramK) ? ((SafeIterableMap.Entry)this.mHashMap.get(paramK)).mPrevious : null;
  }
  
  public boolean contains(K paramK) {
    return this.mHashMap.containsKey(paramK);
  }
  
  protected SafeIterableMap.Entry<K, V> get(K paramK) {
    return this.mHashMap.get(paramK);
  }
  
  public V putIfAbsent(K paramK, V paramV) {
    SafeIterableMap.Entry<K, V> entry = get(paramK);
    if (entry != null)
      return entry.mValue; 
    this.mHashMap.put(paramK, put(paramK, paramV));
    return null;
  }
  
  public V remove(K paramK) {
    V v = super.remove(paramK);
    this.mHashMap.remove(paramK);
    return v;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\arch\core\internal\FastSafeIterableMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */