package com.google.gson.internal;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Primitives {
  private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
  
  private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;
  
  static {
    HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>(16);
    HashMap<Object, Object> hashMap1 = new HashMap<Object, Object>(16);
    add((Map)hashMap2, (Map)hashMap1, boolean.class, Boolean.class);
    add((Map)hashMap2, (Map)hashMap1, byte.class, Byte.class);
    add((Map)hashMap2, (Map)hashMap1, char.class, Character.class);
    add((Map)hashMap2, (Map)hashMap1, double.class, Double.class);
    add((Map)hashMap2, (Map)hashMap1, float.class, Float.class);
    add((Map)hashMap2, (Map)hashMap1, int.class, Integer.class);
    add((Map)hashMap2, (Map)hashMap1, long.class, Long.class);
    add((Map)hashMap2, (Map)hashMap1, short.class, Short.class);
    add((Map)hashMap2, (Map)hashMap1, void.class, Void.class);
    PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(hashMap2);
    WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(hashMap1);
  }
  
  private Primitives() {
    throw new UnsupportedOperationException();
  }
  
  private static void add(Map<Class<?>, Class<?>> paramMap1, Map<Class<?>, Class<?>> paramMap2, Class<?> paramClass1, Class<?> paramClass2) {
    paramMap1.put(paramClass1, paramClass2);
    paramMap2.put(paramClass2, paramClass1);
  }
  
  public static boolean isPrimitive(Type paramType) {
    return PRIMITIVE_TO_WRAPPER_TYPE.containsKey(paramType);
  }
  
  public static boolean isWrapperType(Type paramType) {
    return WRAPPER_TO_PRIMITIVE_TYPE.containsKey($Gson$Preconditions.checkNotNull(paramType));
  }
  
  public static <T> Class<T> unwrap(Class<T> paramClass) {
    Class<T> clazz = (Class)WRAPPER_TO_PRIMITIVE_TYPE.get($Gson$Preconditions.checkNotNull(paramClass));
    if (clazz != null)
      paramClass = clazz; 
    return paramClass;
  }
  
  public static <T> Class<T> wrap(Class<T> paramClass) {
    Class<T> clazz = (Class)PRIMITIVE_TO_WRAPPER_TYPE.get($Gson$Preconditions.checkNotNull(paramClass));
    if (clazz != null)
      paramClass = clazz; 
    return paramClass;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\Primitives.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */