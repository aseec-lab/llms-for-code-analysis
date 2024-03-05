package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T> extends TypeAdapter<T> {
  private final GsonContextImpl context = new GsonContextImpl();
  
  private TypeAdapter<T> delegate;
  
  private final JsonDeserializer<T> deserializer;
  
  final Gson gson;
  
  private final JsonSerializer<T> serializer;
  
  private final TypeAdapterFactory skipPast;
  
  private final TypeToken<T> typeToken;
  
  public TreeTypeAdapter(JsonSerializer<T> paramJsonSerializer, JsonDeserializer<T> paramJsonDeserializer, Gson paramGson, TypeToken<T> paramTypeToken, TypeAdapterFactory paramTypeAdapterFactory) {
    this.serializer = paramJsonSerializer;
    this.deserializer = paramJsonDeserializer;
    this.gson = paramGson;
    this.typeToken = paramTypeToken;
    this.skipPast = paramTypeAdapterFactory;
  }
  
  private TypeAdapter<T> delegate() {
    TypeAdapter<T> typeAdapter = this.delegate;
    if (typeAdapter == null) {
      typeAdapter = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
      this.delegate = typeAdapter;
    } 
    return typeAdapter;
  }
  
  public static TypeAdapterFactory newFactory(TypeToken<?> paramTypeToken, Object paramObject) {
    return new SingleTypeFactory(paramObject, paramTypeToken, false, null);
  }
  
  public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> paramTypeToken, Object paramObject) {
    boolean bool;
    if (paramTypeToken.getType() == paramTypeToken.getRawType()) {
      bool = true;
    } else {
      bool = false;
    } 
    return new SingleTypeFactory(paramObject, paramTypeToken, bool, null);
  }
  
  public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> paramClass, Object paramObject) {
    return new SingleTypeFactory(paramObject, null, false, paramClass);
  }
  
  public T read(JsonReader paramJsonReader) throws IOException {
    if (this.deserializer == null)
      return (T)delegate().read(paramJsonReader); 
    JsonElement jsonElement = Streams.parse(paramJsonReader);
    return (T)(jsonElement.isJsonNull() ? null : this.deserializer.deserialize(jsonElement, this.typeToken.getType(), this.context));
  }
  
  public void write(JsonWriter paramJsonWriter, T paramT) throws IOException {
    JsonSerializer<T> jsonSerializer = this.serializer;
    if (jsonSerializer == null) {
      delegate().write(paramJsonWriter, paramT);
      return;
    } 
    if (paramT == null) {
      paramJsonWriter.nullValue();
      return;
    } 
    Streams.write(jsonSerializer.serialize(paramT, this.typeToken.getType(), this.context), paramJsonWriter);
  }
  
  private final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
    final TreeTypeAdapter this$0;
    
    private GsonContextImpl() {}
    
    public <R> R deserialize(JsonElement param1JsonElement, Type param1Type) throws JsonParseException {
      return (R)TreeTypeAdapter.this.gson.fromJson(param1JsonElement, param1Type);
    }
    
    public JsonElement serialize(Object param1Object) {
      return TreeTypeAdapter.this.gson.toJsonTree(param1Object);
    }
    
    public JsonElement serialize(Object param1Object, Type param1Type) {
      return TreeTypeAdapter.this.gson.toJsonTree(param1Object, param1Type);
    }
  }
  
  private static final class SingleTypeFactory implements TypeAdapterFactory {
    private final JsonDeserializer<?> deserializer;
    
    private final TypeToken<?> exactType;
    
    private final Class<?> hierarchyType;
    
    private final boolean matchRawType;
    
    private final JsonSerializer<?> serializer;
    
    SingleTypeFactory(Object param1Object, TypeToken<?> param1TypeToken, boolean param1Boolean, Class<?> param1Class) {
      JsonDeserializer<?> jsonDeserializer;
      boolean bool = param1Object instanceof JsonSerializer;
      JsonSerializer jsonSerializer2 = null;
      if (bool) {
        jsonSerializer1 = (JsonSerializer)param1Object;
      } else {
        jsonSerializer1 = null;
      } 
      this.serializer = jsonSerializer1;
      JsonSerializer jsonSerializer1 = jsonSerializer2;
      if (param1Object instanceof JsonDeserializer)
        jsonDeserializer = (JsonDeserializer)param1Object; 
      this.deserializer = jsonDeserializer;
      if (this.serializer != null || jsonDeserializer != null) {
        bool = true;
      } else {
        bool = false;
      } 
      .Gson.Preconditions.checkArgument(bool);
      this.exactType = param1TypeToken;
      this.matchRawType = param1Boolean;
      this.hierarchyType = param1Class;
    }
    
    public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
      boolean bool;
      TypeToken<?> typeToken = this.exactType;
      if (typeToken != null) {
        if (typeToken.equals(param1TypeToken) || (this.matchRawType && this.exactType.getType() == param1TypeToken.getRawType())) {
          bool = true;
        } else {
          bool = false;
        } 
      } else {
        bool = this.hierarchyType.isAssignableFrom(param1TypeToken.getRawType());
      } 
      if (bool) {
        TreeTypeAdapter treeTypeAdapter = new TreeTypeAdapter(this.serializer, this.deserializer, param1Gson, param1TypeToken, this);
      } else {
        param1Gson = null;
      } 
      return (TypeAdapter<T>)param1Gson;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\TreeTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */