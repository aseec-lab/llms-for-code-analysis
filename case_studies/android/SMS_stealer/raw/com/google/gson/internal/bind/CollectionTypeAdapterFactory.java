package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
  private final ConstructorConstructor constructorConstructor;
  
  public CollectionTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor) {
    this.constructorConstructor = paramConstructorConstructor;
  }
  
  public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken) {
    Type type = paramTypeToken.getType();
    Class<?> clazz = paramTypeToken.getRawType();
    if (!Collection.class.isAssignableFrom(clazz))
      return null; 
    type = .Gson.Types.getCollectionElementType(type, clazz);
    return new Adapter(paramGson, type, paramGson.getAdapter(TypeToken.get(type)), this.constructorConstructor.get(paramTypeToken));
  }
  
  private static final class Adapter<E> extends TypeAdapter<Collection<E>> {
    private final ObjectConstructor<? extends Collection<E>> constructor;
    
    private final TypeAdapter<E> elementTypeAdapter;
    
    public Adapter(Gson param1Gson, Type param1Type, TypeAdapter<E> param1TypeAdapter, ObjectConstructor<? extends Collection<E>> param1ObjectConstructor) {
      this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(param1Gson, param1TypeAdapter, param1Type);
      this.constructor = param1ObjectConstructor;
    }
    
    public Collection<E> read(JsonReader param1JsonReader) throws IOException {
      if (param1JsonReader.peek() == JsonToken.NULL) {
        param1JsonReader.nextNull();
        return null;
      } 
      Collection<Object> collection = (Collection)this.constructor.construct();
      param1JsonReader.beginArray();
      while (param1JsonReader.hasNext())
        collection.add(this.elementTypeAdapter.read(param1JsonReader)); 
      param1JsonReader.endArray();
      return (Collection)collection;
    }
    
    public void write(JsonWriter param1JsonWriter, Collection<E> param1Collection) throws IOException {
      // Byte code:
      //   0: aload_2
      //   1: ifnonnull -> 10
      //   4: aload_1
      //   5: invokevirtual nullValue : ()Lcom/google/gson/stream/JsonWriter;
      //   8: pop
      //   9: return
      //   10: aload_1
      //   11: invokevirtual beginArray : ()Lcom/google/gson/stream/JsonWriter;
      //   14: pop
      //   15: aload_2
      //   16: invokeinterface iterator : ()Ljava/util/Iterator;
      //   21: astore_2
      //   22: aload_2
      //   23: invokeinterface hasNext : ()Z
      //   28: ifeq -> 50
      //   31: aload_2
      //   32: invokeinterface next : ()Ljava/lang/Object;
      //   37: astore_3
      //   38: aload_0
      //   39: getfield elementTypeAdapter : Lcom/google/gson/TypeAdapter;
      //   42: aload_1
      //   43: aload_3
      //   44: invokevirtual write : (Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
      //   47: goto -> 22
      //   50: aload_1
      //   51: invokevirtual endArray : ()Lcom/google/gson/stream/JsonWriter;
      //   54: pop
      //   55: return
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\CollectionTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */