package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
  private final ConstructorConstructor constructorConstructor;
  
  private final Excluder excluder;
  
  private final FieldNamingStrategy fieldNamingPolicy;
  
  private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
  
  public ReflectiveTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor, FieldNamingStrategy paramFieldNamingStrategy, Excluder paramExcluder, JsonAdapterAnnotationTypeAdapterFactory paramJsonAdapterAnnotationTypeAdapterFactory) {
    this.constructorConstructor = paramConstructorConstructor;
    this.fieldNamingPolicy = paramFieldNamingStrategy;
    this.excluder = paramExcluder;
    this.jsonAdapterFactory = paramJsonAdapterAnnotationTypeAdapterFactory;
  }
  
  private BoundField createBoundField(final Gson context, final Field field, String paramString, final TypeToken<?> fieldType, boolean paramBoolean1, boolean paramBoolean2) {
    final boolean jsonAdapterPresent;
    final TypeAdapter typeAdapter;
    final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());
    JsonAdapter jsonAdapter1 = field.<JsonAdapter>getAnnotation(JsonAdapter.class);
    if (jsonAdapter1 != null) {
      TypeAdapter<?> typeAdapter1 = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, context, fieldType, jsonAdapter1);
    } else {
      jsonAdapter1 = null;
    } 
    if (jsonAdapter1 != null) {
      bool = true;
    } else {
      bool = false;
    } 
    JsonAdapter jsonAdapter2 = jsonAdapter1;
    if (jsonAdapter1 == null)
      typeAdapter = context.getAdapter(fieldType); 
    return new BoundField(paramString, paramBoolean1, paramBoolean2) {
        final ReflectiveTypeAdapterFactory this$0;
        
        final Gson val$context;
        
        final Field val$field;
        
        final TypeToken val$fieldType;
        
        final boolean val$isPrimitive;
        
        final boolean val$jsonAdapterPresent;
        
        final TypeAdapter val$typeAdapter;
        
        void read(JsonReader param1JsonReader, Object param1Object) throws IOException, IllegalAccessException {
          Object object = typeAdapter.read(param1JsonReader);
          if (object != null || !isPrimitive)
            field.set(param1Object, object); 
        }
        
        void write(JsonWriter param1JsonWriter, Object param1Object) throws IOException, IllegalAccessException {
          Object object = field.get(param1Object);
          if (jsonAdapterPresent) {
            param1Object = typeAdapter;
          } else {
            param1Object = new TypeAdapterRuntimeTypeWrapper(context, typeAdapter, fieldType.getType());
          } 
          param1Object.write(param1JsonWriter, object);
        }
        
        public boolean writeField(Object param1Object) throws IOException, IllegalAccessException {
          boolean bool1 = this.serialized;
          boolean bool = false;
          if (!bool1)
            return false; 
          if (field.get(param1Object) != param1Object)
            bool = true; 
          return bool;
        }
      };
  }
  
  static boolean excludeField(Field paramField, boolean paramBoolean, Excluder paramExcluder) {
    if (!paramExcluder.excludeClass(paramField.getType(), paramBoolean) && !paramExcluder.excludeField(paramField, paramBoolean)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    return paramBoolean;
  }
  
  private Map<String, BoundField> getBoundFields(Gson paramGson, TypeToken<?> paramTypeToken, Class<?> paramClass) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    if (paramClass.isInterface())
      return (Map)linkedHashMap; 
    Type type = paramTypeToken.getType();
    Class<?> clazz = paramClass;
    TypeToken<?> typeToken = paramTypeToken;
    while (clazz != Object.class) {
      for (Field field : clazz.getDeclaredFields()) {
        boolean bool1 = excludeField(field, true);
        boolean bool2 = excludeField(field, false);
        if (bool1 || bool2) {
          BoundField boundField;
          field.setAccessible(true);
          Type type1 = .Gson.Types.resolve(typeToken.getType(), clazz, field.getGenericType());
          List<String> list = getFieldNames(field);
          int i = list.size();
          paramTypeToken = null;
          for (byte b = 0; b < i; b++) {
            String str = list.get(b);
            if (b != 0)
              bool1 = false; 
            BoundField boundField1 = (BoundField)linkedHashMap.put(str, createBoundField(paramGson, field, str, TypeToken.get(type1), bool1, bool2));
            if (paramTypeToken == null)
              boundField = boundField1; 
          } 
          if (boundField != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(type);
            stringBuilder.append(" declares multiple JSON fields named ");
            stringBuilder.append(boundField.name);
            throw new IllegalArgumentException(stringBuilder.toString());
          } 
        } 
      } 
      typeToken = TypeToken.get(.Gson.Types.resolve(typeToken.getType(), clazz, clazz.getGenericSuperclass()));
      clazz = typeToken.getRawType();
    } 
    return (Map)linkedHashMap;
  }
  
  private List<String> getFieldNames(Field paramField) {
    SerializedName serializedName = paramField.<SerializedName>getAnnotation(SerializedName.class);
    if (serializedName == null)
      return Collections.singletonList(this.fieldNamingPolicy.translateName(paramField)); 
    String str = serializedName.value();
    String[] arrayOfString = serializedName.alternate();
    if (arrayOfString.length == 0)
      return Collections.singletonList(str); 
    ArrayList<String> arrayList = new ArrayList(arrayOfString.length + 1);
    arrayList.add(str);
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++)
      arrayList.add(arrayOfString[b]); 
    return arrayList;
  }
  
  public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken) {
    Class<?> clazz = paramTypeToken.getRawType();
    return !Object.class.isAssignableFrom(clazz) ? null : new Adapter<T>(this.constructorConstructor.get(paramTypeToken), getBoundFields(paramGson, paramTypeToken, clazz));
  }
  
  public boolean excludeField(Field paramField, boolean paramBoolean) {
    return excludeField(paramField, paramBoolean, this.excluder);
  }
  
  public static final class Adapter<T> extends TypeAdapter<T> {
    private final Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields;
    
    private final ObjectConstructor<T> constructor;
    
    Adapter(ObjectConstructor<T> param1ObjectConstructor, Map<String, ReflectiveTypeAdapterFactory.BoundField> param1Map) {
      this.constructor = param1ObjectConstructor;
      this.boundFields = param1Map;
    }
    
    public T read(JsonReader param1JsonReader) throws IOException {
      if (param1JsonReader.peek() == JsonToken.NULL) {
        param1JsonReader.nextNull();
        return null;
      } 
      Object object = this.constructor.construct();
      try {
        param1JsonReader.beginObject();
        while (param1JsonReader.hasNext()) {
          String str = param1JsonReader.nextName();
          ReflectiveTypeAdapterFactory.BoundField boundField = this.boundFields.get(str);
          if (boundField == null || !boundField.deserialized) {
            param1JsonReader.skipValue();
            continue;
          } 
          boundField.read(param1JsonReader, object);
        } 
        param1JsonReader.endObject();
        return (T)object;
      } catch (IllegalStateException illegalStateException) {
        throw new JsonSyntaxException(illegalStateException);
      } catch (IllegalAccessException illegalAccessException) {
        throw new AssertionError(illegalAccessException);
      } 
    }
    
    public void write(JsonWriter param1JsonWriter, T param1T) throws IOException {
      if (param1T == null) {
        param1JsonWriter.nullValue();
        return;
      } 
      param1JsonWriter.beginObject();
      try {
        for (ReflectiveTypeAdapterFactory.BoundField boundField : this.boundFields.values()) {
          if (boundField.writeField(param1T)) {
            param1JsonWriter.name(boundField.name);
            boundField.write(param1JsonWriter, param1T);
          } 
        } 
        param1JsonWriter.endObject();
        return;
      } catch (IllegalAccessException illegalAccessException) {
        throw new AssertionError(illegalAccessException);
      } 
    }
  }
  
  static abstract class BoundField {
    final boolean deserialized;
    
    final String name;
    
    final boolean serialized;
    
    protected BoundField(String param1String, boolean param1Boolean1, boolean param1Boolean2) {
      this.name = param1String;
      this.serialized = param1Boolean1;
      this.deserialized = param1Boolean2;
    }
    
    abstract void read(JsonReader param1JsonReader, Object param1Object) throws IOException, IllegalAccessException;
    
    abstract void write(JsonWriter param1JsonWriter, Object param1Object) throws IOException, IllegalAccessException;
    
    abstract boolean writeField(Object param1Object) throws IOException, IllegalAccessException;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\ReflectiveTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */