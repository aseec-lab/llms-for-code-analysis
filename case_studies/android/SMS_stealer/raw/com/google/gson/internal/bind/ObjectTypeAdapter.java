package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class ObjectTypeAdapter extends TypeAdapter<Object> {
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
        return (param1TypeToken.getRawType() == Object.class) ? new ObjectTypeAdapter(param1Gson) : null;
      }
    };
  
  private final Gson gson;
  
  ObjectTypeAdapter(Gson paramGson) {
    this.gson = paramGson;
  }
  
  public Object read(JsonReader paramJsonReader) throws IOException {
    LinkedTreeMap<String, Object> linkedTreeMap;
    JsonToken jsonToken = paramJsonReader.peek();
    switch (jsonToken) {
      default:
        throw new IllegalStateException();
      case null:
        paramJsonReader.nextNull();
        return null;
      case null:
        return Boolean.valueOf(paramJsonReader.nextBoolean());
      case null:
        return Double.valueOf(paramJsonReader.nextDouble());
      case null:
        return paramJsonReader.nextString();
      case null:
        linkedTreeMap = new LinkedTreeMap();
        paramJsonReader.beginObject();
        while (paramJsonReader.hasNext())
          linkedTreeMap.put(paramJsonReader.nextName(), read(paramJsonReader)); 
        paramJsonReader.endObject();
        return linkedTreeMap;
      case null:
        break;
    } 
    ArrayList<Object> arrayList = new ArrayList();
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
      arrayList.add(read(paramJsonReader)); 
    paramJsonReader.endArray();
    return arrayList;
  }
  
  public void write(JsonWriter paramJsonWriter, Object paramObject) throws IOException {
    if (paramObject == null) {
      paramJsonWriter.nullValue();
      return;
    } 
    TypeAdapter typeAdapter = this.gson.getAdapter(paramObject.getClass());
    if (typeAdapter instanceof ObjectTypeAdapter) {
      paramJsonWriter.beginObject();
      paramJsonWriter.endObject();
      return;
    } 
    typeAdapter.write(paramJsonWriter, paramObject);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\ObjectTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */