package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
  private final Gson context;
  
  private final TypeAdapter<T> delegate;
  
  private final Type type;
  
  TypeAdapterRuntimeTypeWrapper(Gson paramGson, TypeAdapter<T> paramTypeAdapter, Type paramType) {
    this.context = paramGson;
    this.delegate = paramTypeAdapter;
    this.type = paramType;
  }
  
  private Type getRuntimeTypeIfMoreSpecific(Type<?> paramType, Object paramObject) {
    null = paramType;
    if (paramObject != null) {
      if (paramType != Object.class && !(paramType instanceof java.lang.reflect.TypeVariable)) {
        null = paramType;
        return (paramType instanceof Class) ? paramObject.getClass() : null;
      } 
    } else {
      return null;
    } 
    return paramObject.getClass();
  }
  
  public T read(JsonReader paramJsonReader) throws IOException {
    return (T)this.delegate.read(paramJsonReader);
  }
  
  public void write(JsonWriter paramJsonWriter, T paramT) throws IOException {
    TypeAdapter<T> typeAdapter = this.delegate;
    Type type = getRuntimeTypeIfMoreSpecific(this.type, paramT);
    if (type != this.type) {
      typeAdapter = this.context.getAdapter(TypeToken.get(type));
      if (typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter) {
        TypeAdapter<T> typeAdapter1 = this.delegate;
        if (!(typeAdapter1 instanceof ReflectiveTypeAdapterFactory.Adapter))
          typeAdapter = typeAdapter1; 
      } 
    } 
    typeAdapter.write(paramJsonWriter, paramT);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\TypeAdapterRuntimeTypeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */