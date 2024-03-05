package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {
  private final Map<Type, InstanceCreator<?>> instanceCreators;
  
  public ConstructorConstructor(Map<Type, InstanceCreator<?>> paramMap) {
    this.instanceCreators = paramMap;
  }
  
  private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> paramClass) {
    try {
      final Constructor<? super T> constructor = paramClass.getDeclaredConstructor(new Class[0]);
      if (!constructor.isAccessible())
        constructor.setAccessible(true); 
      return new ObjectConstructor<T>() {
          final ConstructorConstructor this$0;
          
          final Constructor val$constructor;
          
          public T construct() {
            try {
              return (T)constructor.newInstance((Object[])null);
            } catch (InstantiationException instantiationException) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Failed to invoke ");
              stringBuilder.append(constructor);
              stringBuilder.append(" with no args");
              throw new RuntimeException(stringBuilder.toString(), instantiationException);
            } catch (InvocationTargetException invocationTargetException) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Failed to invoke ");
              stringBuilder.append(constructor);
              stringBuilder.append(" with no args");
              throw new RuntimeException(stringBuilder.toString(), invocationTargetException.getTargetException());
            } catch (IllegalAccessException illegalAccessException) {
              throw new AssertionError(illegalAccessException);
            } 
          }
        };
    } catch (NoSuchMethodException noSuchMethodException) {
      return null;
    } 
  }
  
  private <T> ObjectConstructor<T> newDefaultImplementationConstructor(final Type type, Class<? super T> paramClass) {
    return Collection.class.isAssignableFrom(paramClass) ? (SortedSet.class.isAssignableFrom(paramClass) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new TreeSet();
        }
      } : (EnumSet.class.isAssignableFrom(paramClass) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        final Type val$type;
        
        public T construct() {
          Type type = type;
          if (type instanceof ParameterizedType) {
            type = ((ParameterizedType)type).getActualTypeArguments()[0];
            if (type instanceof Class)
              return (T)EnumSet.noneOf((Class<Enum>)type); 
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("Invalid EnumSet type: ");
            stringBuilder1.append(type.toString());
            throw new JsonIOException(stringBuilder1.toString());
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Invalid EnumSet type: ");
          stringBuilder.append(type.toString());
          throw new JsonIOException(stringBuilder.toString());
        }
      } : (Set.class.isAssignableFrom(paramClass) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new LinkedHashSet();
        }
      } : (Queue.class.isAssignableFrom(paramClass) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new ArrayDeque();
        }
      } : new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new ArrayList();
        }
      })))) : (Map.class.isAssignableFrom(paramClass) ? (ConcurrentNavigableMap.class.isAssignableFrom(paramClass) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new ConcurrentSkipListMap<Object, Object>();
        }
      } : (ConcurrentMap.class.isAssignableFrom(paramClass) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new ConcurrentHashMap<Object, Object>();
        }
      } : (SortedMap.class.isAssignableFrom(paramClass) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new TreeMap<Object, Object>();
        }
      } : ((type instanceof ParameterizedType && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType())) ? new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new LinkedHashMap<Object, Object>();
        }
      } : new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        public T construct() {
          return (T)new LinkedTreeMap<Object, Object>();
        }
      })))) : null);
  }
  
  private <T> ObjectConstructor<T> newUnsafeAllocator(final Type type, final Class<? super T> rawType) {
    return new ObjectConstructor<T>() {
        final ConstructorConstructor this$0;
        
        private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();
        
        final Class val$rawType;
        
        final Type val$type;
        
        public T construct() {
          try {
            return (T)this.unsafeAllocator.newInstance(rawType);
          } catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to invoke no-args constructor for ");
            stringBuilder.append(type);
            stringBuilder.append(". Registering an InstanceCreator with Gson for this type may fix this problem.");
            throw new RuntimeException(stringBuilder.toString(), exception);
          } 
        }
      };
  }
  
  public <T> ObjectConstructor<T> get(TypeToken<T> paramTypeToken) {
    final Type type = paramTypeToken.getType();
    Class<?> clazz = paramTypeToken.getRawType();
    final InstanceCreator rawTypeCreator = this.instanceCreators.get(type);
    if (instanceCreator != null)
      return new ObjectConstructor<T>() {
          final ConstructorConstructor this$0;
          
          final Type val$type;
          
          final InstanceCreator val$typeCreator;
          
          public T construct() {
            return (T)typeCreator.createInstance(type);
          }
        }; 
    instanceCreator = this.instanceCreators.get(clazz);
    if (instanceCreator != null)
      return new ObjectConstructor<T>() {
          final ConstructorConstructor this$0;
          
          final InstanceCreator val$rawTypeCreator;
          
          final Type val$type;
          
          public T construct() {
            return (T)rawTypeCreator.createInstance(type);
          }
        }; 
    ObjectConstructor<?> objectConstructor = newDefaultConstructor(clazz);
    if (objectConstructor != null)
      return (ObjectConstructor)objectConstructor; 
    objectConstructor = newDefaultImplementationConstructor(type, clazz);
    return (ObjectConstructor)((objectConstructor != null) ? objectConstructor : newUnsafeAllocator(type, (Class)clazz));
  }
  
  public String toString() {
    return this.instanceCreators.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\ConstructorConstructor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */