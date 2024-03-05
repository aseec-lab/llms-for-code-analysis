package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
  private final ConstructorConstructor constructorConstructor;
  
  public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor) {
    this.constructorConstructor = paramConstructorConstructor;
  }
  
  public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken) {
    JsonAdapter jsonAdapter = (JsonAdapter)paramTypeToken.getRawType().getAnnotation(JsonAdapter.class);
    return (TypeAdapter)((jsonAdapter == null) ? null : getTypeAdapter(this.constructorConstructor, paramGson, paramTypeToken, jsonAdapter));
  }
  
  TypeAdapter<?> getTypeAdapter(ConstructorConstructor paramConstructorConstructor, Gson paramGson, TypeToken<?> paramTypeToken, JsonAdapter paramJsonAdapter) {
    StringBuilder stringBuilder1;
    TypeAdapter<?> typeAdapter;
    Object object = paramConstructorConstructor.get(TypeToken.get(paramJsonAdapter.value())).construct();
    if (object instanceof TypeAdapter) {
      TypeAdapter typeAdapter1 = (TypeAdapter)object;
    } else if (object instanceof TypeAdapterFactory) {
      TypeAdapter typeAdapter1 = ((TypeAdapterFactory)object).create(paramGson, paramTypeToken);
    } else {
      boolean bool = object instanceof JsonSerializer;
      if (bool || object instanceof JsonDeserializer) {
        JsonDeserializer<?> jsonDeserializer = null;
        if (bool) {
          JsonSerializer jsonSerializer = (JsonSerializer)object;
        } else {
          paramConstructorConstructor = null;
        } 
        if (object instanceof JsonDeserializer)
          jsonDeserializer = (JsonDeserializer)object; 
        TreeTypeAdapter treeTypeAdapter = new TreeTypeAdapter((JsonSerializer<?>)paramConstructorConstructor, jsonDeserializer, paramGson, paramTypeToken, null);
      } else {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Invalid attempt to bind an instance of ");
        stringBuilder1.append(object.getClass().getName());
        stringBuilder1.append(" as a @JsonAdapter for ");
        stringBuilder1.append(paramTypeToken.toString());
        stringBuilder1.append(". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
    } 
    StringBuilder stringBuilder2 = stringBuilder1;
    if (stringBuilder1 != null) {
      stringBuilder2 = stringBuilder1;
      if (paramJsonAdapter.nullSafe())
        typeAdapter = stringBuilder1.nullSafe(); 
    } 
    return typeAdapter;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\JsonAdapterAnnotationTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */