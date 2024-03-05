package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ArrayTypeAdapter<E> extends TypeAdapter<Object> {
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
        Type type = param1TypeToken.getType();
        if (!(type instanceof java.lang.reflect.GenericArrayType) && (!(type instanceof Class) || !((Class)type).isArray()))
          return null; 
        type = .Gson.Types.getArrayComponentType(type);
        return new ArrayTypeAdapter(param1Gson, param1Gson.getAdapter(TypeToken.get(type)), .Gson.Types.getRawType(type));
      }
    };
  
  private final Class<E> componentType;
  
  private final TypeAdapter<E> componentTypeAdapter;
  
  public ArrayTypeAdapter(Gson paramGson, TypeAdapter<E> paramTypeAdapter, Class<E> paramClass) {
    this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(paramGson, paramTypeAdapter, paramClass);
    this.componentType = paramClass;
  }
  
  public Object read(JsonReader paramJsonReader) throws IOException {
    if (paramJsonReader.peek() == JsonToken.NULL) {
      paramJsonReader.nextNull();
      return null;
    } 
    ArrayList<Object> arrayList = new ArrayList();
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
      arrayList.add(this.componentTypeAdapter.read(paramJsonReader)); 
    paramJsonReader.endArray();
    int i = arrayList.size();
    Object object = Array.newInstance(this.componentType, i);
    for (byte b = 0; b < i; b++)
      Array.set(object, b, arrayList.get(b)); 
    return object;
  }
  
  public void write(JsonWriter paramJsonWriter, Object paramObject) throws IOException {
    if (paramObject == null) {
      paramJsonWriter.nullValue();
      return;
    } 
    paramJsonWriter.beginArray();
    byte b = 0;
    int i = Array.getLength(paramObject);
    while (b < i) {
      Object object = Array.get(paramObject, b);
      this.componentTypeAdapter.write(paramJsonWriter, object);
      b++;
    } 
    paramJsonWriter.endArray();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\ArrayTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */