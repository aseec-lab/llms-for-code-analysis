package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class MapTypeAdapterFactory implements TypeAdapterFactory {
  final boolean complexMapKeySerialization;
  
  private final ConstructorConstructor constructorConstructor;
  
  public MapTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor, boolean paramBoolean) {
    this.constructorConstructor = paramConstructorConstructor;
    this.complexMapKeySerialization = paramBoolean;
  }
  
  private TypeAdapter<?> getKeyAdapter(Gson paramGson, Type paramType) {
    return (paramType == boolean.class || paramType == Boolean.class) ? TypeAdapters.BOOLEAN_AS_STRING : paramGson.getAdapter(TypeToken.get(paramType));
  }
  
  public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken) {
    Type type = paramTypeToken.getType();
    if (!Map.class.isAssignableFrom(paramTypeToken.getRawType()))
      return null; 
    Type[] arrayOfType = .Gson.Types.getMapKeyAndValueTypes(type, .Gson.Types.getRawType(type));
    TypeAdapter<?> typeAdapter2 = getKeyAdapter(paramGson, arrayOfType[0]);
    TypeAdapter<?> typeAdapter1 = paramGson.getAdapter(TypeToken.get(arrayOfType[1]));
    ObjectConstructor<? extends Map<?, ?>> objectConstructor = this.constructorConstructor.get(paramTypeToken);
    return (TypeAdapter)new Adapter<Object, Object>(paramGson, arrayOfType[0], typeAdapter2, arrayOfType[1], typeAdapter1, objectConstructor);
  }
  
  private final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {
    private final ObjectConstructor<? extends Map<K, V>> constructor;
    
    private final TypeAdapter<K> keyTypeAdapter;
    
    final MapTypeAdapterFactory this$0;
    
    private final TypeAdapter<V> valueTypeAdapter;
    
    public Adapter(Gson param1Gson, Type param1Type1, TypeAdapter<K> param1TypeAdapter, Type param1Type2, TypeAdapter<V> param1TypeAdapter1, ObjectConstructor<? extends Map<K, V>> param1ObjectConstructor) {
      this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper<K>(param1Gson, param1TypeAdapter, param1Type1);
      this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper<V>(param1Gson, param1TypeAdapter1, param1Type2);
      this.constructor = param1ObjectConstructor;
    }
    
    private String keyToString(JsonElement param1JsonElement) {
      JsonPrimitive jsonPrimitive;
      if (param1JsonElement.isJsonPrimitive()) {
        jsonPrimitive = param1JsonElement.getAsJsonPrimitive();
        if (jsonPrimitive.isNumber())
          return String.valueOf(jsonPrimitive.getAsNumber()); 
        if (jsonPrimitive.isBoolean())
          return Boolean.toString(jsonPrimitive.getAsBoolean()); 
        if (jsonPrimitive.isString())
          return jsonPrimitive.getAsString(); 
        throw new AssertionError();
      } 
      if (jsonPrimitive.isJsonNull())
        return "null"; 
      throw new AssertionError();
    }
    
    public Map<K, V> read(JsonReader param1JsonReader) throws IOException {
      StringBuilder stringBuilder;
      JsonToken jsonToken = param1JsonReader.peek();
      if (jsonToken == JsonToken.NULL) {
        param1JsonReader.nextNull();
        return null;
      } 
      Map<Object, Object> map = (Map)this.constructor.construct();
      if (jsonToken == JsonToken.BEGIN_ARRAY) {
        param1JsonReader.beginArray();
        while (param1JsonReader.hasNext()) {
          param1JsonReader.beginArray();
          Object object = this.keyTypeAdapter.read(param1JsonReader);
          if (map.put(object, this.valueTypeAdapter.read(param1JsonReader)) == null) {
            param1JsonReader.endArray();
            continue;
          } 
          stringBuilder = new StringBuilder();
          stringBuilder.append("duplicate key: ");
          stringBuilder.append(object);
          throw new JsonSyntaxException(stringBuilder.toString());
        } 
        stringBuilder.endArray();
      } else {
        stringBuilder.beginObject();
        while (stringBuilder.hasNext()) {
          JsonReaderInternalAccess.INSTANCE.promoteNameToValue((JsonReader)stringBuilder);
          Object object = this.keyTypeAdapter.read((JsonReader)stringBuilder);
          if (map.put(object, this.valueTypeAdapter.read((JsonReader)stringBuilder)) == null)
            continue; 
          stringBuilder = new StringBuilder();
          stringBuilder.append("duplicate key: ");
          stringBuilder.append(object);
          throw new JsonSyntaxException(stringBuilder.toString());
        } 
        stringBuilder.endObject();
      } 
      return (Map)map;
    }
    
    public void write(JsonWriter param1JsonWriter, Map<K, V> param1Map) throws IOException {
      if (param1Map == null) {
        param1JsonWriter.nullValue();
        return;
      } 
      if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
        param1JsonWriter.beginObject();
        for (Map.Entry<K, V> entry : param1Map.entrySet()) {
          param1JsonWriter.name(String.valueOf(entry.getKey()));
          this.valueTypeAdapter.write(param1JsonWriter, entry.getValue());
        } 
        param1JsonWriter.endObject();
        return;
      } 
      ArrayList<JsonElement> arrayList1 = new ArrayList(param1Map.size());
      ArrayList arrayList = new ArrayList(param1Map.size());
      Iterator<Map.Entry> iterator = param1Map.entrySet().iterator();
      boolean bool2 = false;
      boolean bool1 = false;
      int i;
      for (i = 0; iterator.hasNext(); i |= b) {
        byte b;
        Map.Entry entry = iterator.next();
        JsonElement jsonElement = this.keyTypeAdapter.toJsonTree(entry.getKey());
        arrayList1.add(jsonElement);
        arrayList.add(entry.getValue());
        if (jsonElement.isJsonArray() || jsonElement.isJsonObject()) {
          b = 1;
        } else {
          b = 0;
        } 
      } 
      if (i != 0) {
        param1JsonWriter.beginArray();
        int j = arrayList1.size();
        for (i = bool1; i < j; i++) {
          param1JsonWriter.beginArray();
          Streams.write(arrayList1.get(i), param1JsonWriter);
          this.valueTypeAdapter.write(param1JsonWriter, arrayList.get(i));
          param1JsonWriter.endArray();
        } 
        param1JsonWriter.endArray();
      } else {
        param1JsonWriter.beginObject();
        int j = arrayList1.size();
        for (i = bool2; i < j; i++) {
          param1JsonWriter.name(keyToString(arrayList1.get(i)));
          this.valueTypeAdapter.write(param1JsonWriter, arrayList.get(i));
        } 
        param1JsonWriter.endObject();
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\MapTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */