package com.google.gson;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
  static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
  
  static final boolean DEFAULT_ESCAPE_HTML = true;
  
  static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
  
  static final boolean DEFAULT_LENIENT = false;
  
  static final boolean DEFAULT_PRETTY_PRINT = false;
  
  static final boolean DEFAULT_SERIALIZE_NULLS = false;
  
  static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
  
  private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
  
  private static final TypeToken<?> NULL_KEY_SURROGATE = TypeToken.get(Object.class);
  
  private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>>();
  
  private final ConstructorConstructor constructorConstructor;
  
  private final Excluder excluder;
  
  private final List<TypeAdapterFactory> factories;
  
  private final FieldNamingStrategy fieldNamingStrategy;
  
  private final boolean generateNonExecutableJson;
  
  private final boolean htmlSafe;
  
  private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
  
  private final boolean lenient;
  
  private final boolean prettyPrinting;
  
  private final boolean serializeNulls;
  
  private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<TypeToken<?>, TypeAdapter<?>>();
  
  public Gson() {
    this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
  }
  
  Gson(Excluder paramExcluder, FieldNamingStrategy paramFieldNamingStrategy, Map<Type, InstanceCreator<?>> paramMap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, LongSerializationPolicy paramLongSerializationPolicy, List<TypeAdapterFactory> paramList) {
    this.constructorConstructor = new ConstructorConstructor(paramMap);
    this.excluder = paramExcluder;
    this.fieldNamingStrategy = paramFieldNamingStrategy;
    this.serializeNulls = paramBoolean1;
    this.generateNonExecutableJson = paramBoolean3;
    this.htmlSafe = paramBoolean4;
    this.prettyPrinting = paramBoolean5;
    this.lenient = paramBoolean6;
    ArrayList<TypeAdapterFactory> arrayList = new ArrayList();
    arrayList.add(TypeAdapters.JSON_ELEMENT_FACTORY);
    arrayList.add(ObjectTypeAdapter.FACTORY);
    arrayList.add(paramExcluder);
    arrayList.addAll(paramList);
    arrayList.add(TypeAdapters.STRING_FACTORY);
    arrayList.add(TypeAdapters.INTEGER_FACTORY);
    arrayList.add(TypeAdapters.BOOLEAN_FACTORY);
    arrayList.add(TypeAdapters.BYTE_FACTORY);
    arrayList.add(TypeAdapters.SHORT_FACTORY);
    TypeAdapter<Number> typeAdapter = longAdapter(paramLongSerializationPolicy);
    arrayList.add(TypeAdapters.newFactory(long.class, Long.class, typeAdapter));
    arrayList.add(TypeAdapters.newFactory(double.class, Double.class, doubleAdapter(paramBoolean7)));
    arrayList.add(TypeAdapters.newFactory(float.class, Float.class, floatAdapter(paramBoolean7)));
    arrayList.add(TypeAdapters.NUMBER_FACTORY);
    arrayList.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
    arrayList.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
    arrayList.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(typeAdapter)));
    arrayList.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(typeAdapter)));
    arrayList.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
    arrayList.add(TypeAdapters.CHARACTER_FACTORY);
    arrayList.add(TypeAdapters.STRING_BUILDER_FACTORY);
    arrayList.add(TypeAdapters.STRING_BUFFER_FACTORY);
    arrayList.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
    arrayList.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
    arrayList.add(TypeAdapters.URL_FACTORY);
    arrayList.add(TypeAdapters.URI_FACTORY);
    arrayList.add(TypeAdapters.UUID_FACTORY);
    arrayList.add(TypeAdapters.CURRENCY_FACTORY);
    arrayList.add(TypeAdapters.LOCALE_FACTORY);
    arrayList.add(TypeAdapters.INET_ADDRESS_FACTORY);
    arrayList.add(TypeAdapters.BIT_SET_FACTORY);
    arrayList.add(DateTypeAdapter.FACTORY);
    arrayList.add(TypeAdapters.CALENDAR_FACTORY);
    arrayList.add(TimeTypeAdapter.FACTORY);
    arrayList.add(SqlDateTypeAdapter.FACTORY);
    arrayList.add(TypeAdapters.TIMESTAMP_FACTORY);
    arrayList.add(ArrayTypeAdapter.FACTORY);
    arrayList.add(TypeAdapters.CLASS_FACTORY);
    arrayList.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
    arrayList.add(new MapTypeAdapterFactory(this.constructorConstructor, paramBoolean2));
    JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
    this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
    arrayList.add(jsonAdapterAnnotationTypeAdapterFactory);
    arrayList.add(TypeAdapters.ENUM_FACTORY);
    arrayList.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, paramFieldNamingStrategy, paramExcluder, this.jsonAdapterFactory));
    this.factories = Collections.unmodifiableList(arrayList);
  }
  
  private static void assertFullConsumption(Object paramObject, JsonReader paramJsonReader) {
    if (paramObject != null)
      try {
        if (paramJsonReader.peek() != JsonToken.END_DOCUMENT) {
          paramObject = new JsonIOException();
          super("JSON document was not fully consumed.");
          throw paramObject;
        } 
      } catch (MalformedJsonException malformedJsonException) {
        throw new JsonSyntaxException(malformedJsonException);
      } catch (IOException iOException) {
        throw new JsonIOException(iOException);
      }  
  }
  
  private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
    return (new TypeAdapter<AtomicLong>() {
        final TypeAdapter val$longAdapter;
        
        public AtomicLong read(JsonReader param1JsonReader) throws IOException {
          return new AtomicLong(((Number)longAdapter.read(param1JsonReader)).longValue());
        }
        
        public void write(JsonWriter param1JsonWriter, AtomicLong param1AtomicLong) throws IOException {
          longAdapter.write(param1JsonWriter, Long.valueOf(param1AtomicLong.get()));
        }
      }).nullSafe();
  }
  
  private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
    return (new TypeAdapter<AtomicLongArray>() {
        final TypeAdapter val$longAdapter;
        
        public AtomicLongArray read(JsonReader param1JsonReader) throws IOException {
          ArrayList<Long> arrayList = new ArrayList();
          param1JsonReader.beginArray();
          while (param1JsonReader.hasNext())
            arrayList.add(Long.valueOf(((Number)longAdapter.read(param1JsonReader)).longValue())); 
          param1JsonReader.endArray();
          int i = arrayList.size();
          AtomicLongArray atomicLongArray = new AtomicLongArray(i);
          for (byte b = 0; b < i; b++)
            atomicLongArray.set(b, ((Long)arrayList.get(b)).longValue()); 
          return atomicLongArray;
        }
        
        public void write(JsonWriter param1JsonWriter, AtomicLongArray param1AtomicLongArray) throws IOException {
          param1JsonWriter.beginArray();
          int i = param1AtomicLongArray.length();
          for (byte b = 0; b < i; b++)
            longAdapter.write(param1JsonWriter, Long.valueOf(param1AtomicLongArray.get(b))); 
          param1JsonWriter.endArray();
        }
      }).nullSafe();
  }
  
  static void checkValidFloatingPoint(double paramDouble) {
    if (!Double.isNaN(paramDouble) && !Double.isInfinite(paramDouble))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramDouble);
    stringBuilder.append(" is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private TypeAdapter<Number> doubleAdapter(boolean paramBoolean) {
    return paramBoolean ? TypeAdapters.DOUBLE : new TypeAdapter<Number>() {
        final Gson this$0;
        
        public Double read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return Double.valueOf(param1JsonReader.nextDouble());
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          if (param1Number == null) {
            param1JsonWriter.nullValue();
            return;
          } 
          Gson.checkValidFloatingPoint(param1Number.doubleValue());
          param1JsonWriter.value(param1Number);
        }
      };
  }
  
  private TypeAdapter<Number> floatAdapter(boolean paramBoolean) {
    return paramBoolean ? TypeAdapters.FLOAT : new TypeAdapter<Number>() {
        final Gson this$0;
        
        public Float read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return Float.valueOf((float)param1JsonReader.nextDouble());
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          if (param1Number == null) {
            param1JsonWriter.nullValue();
            return;
          } 
          Gson.checkValidFloatingPoint(param1Number.floatValue());
          param1JsonWriter.value(param1Number);
        }
      };
  }
  
  private static TypeAdapter<Number> longAdapter(LongSerializationPolicy paramLongSerializationPolicy) {
    return (paramLongSerializationPolicy == LongSerializationPolicy.DEFAULT) ? TypeAdapters.LONG : new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return Long.valueOf(param1JsonReader.nextLong());
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          if (param1Number == null) {
            param1JsonWriter.nullValue();
            return;
          } 
          param1JsonWriter.value(param1Number.toString());
        }
      };
  }
  
  public Excluder excluder() {
    return this.excluder;
  }
  
  public FieldNamingStrategy fieldNamingStrategy() {
    return this.fieldNamingStrategy;
  }
  
  public <T> T fromJson(JsonElement paramJsonElement, Class<T> paramClass) throws JsonSyntaxException {
    paramJsonElement = fromJson(paramJsonElement, paramClass);
    return Primitives.wrap(paramClass).cast(paramJsonElement);
  }
  
  public <T> T fromJson(JsonElement paramJsonElement, Type paramType) throws JsonSyntaxException {
    return (paramJsonElement == null) ? null : fromJson((JsonReader)new JsonTreeReader(paramJsonElement), paramType);
  }
  
  public <T> T fromJson(JsonReader paramJsonReader, Type paramType) throws JsonIOException, JsonSyntaxException {
    boolean bool1 = paramJsonReader.isLenient();
    boolean bool = true;
    paramJsonReader.setLenient(true);
    try {
      paramJsonReader.peek();
      bool = false;
      paramType = getAdapter(TypeToken.get(paramType)).read(paramJsonReader);
      paramJsonReader.setLenient(bool1);
      return (T)paramType;
    } catch (EOFException eOFException) {
      if (bool) {
        paramJsonReader.setLenient(bool1);
        return null;
      } 
      JsonSyntaxException jsonSyntaxException = new JsonSyntaxException();
      this(eOFException);
      throw jsonSyntaxException;
    } catch (IllegalStateException illegalStateException) {
      JsonSyntaxException jsonSyntaxException = new JsonSyntaxException();
      this(illegalStateException);
      throw jsonSyntaxException;
    } catch (IOException iOException) {
      JsonSyntaxException jsonSyntaxException = new JsonSyntaxException();
      this(iOException);
      throw jsonSyntaxException;
    } finally {}
    paramJsonReader.setLenient(bool1);
    throw paramType;
  }
  
  public <T> T fromJson(Reader paramReader, Class<T> paramClass) throws JsonSyntaxException, JsonIOException {
    JsonReader jsonReader = newJsonReader(paramReader);
    paramReader = fromJson(jsonReader, paramClass);
    assertFullConsumption(paramReader, jsonReader);
    return Primitives.wrap(paramClass).cast(paramReader);
  }
  
  public <T> T fromJson(Reader paramReader, Type paramType) throws JsonIOException, JsonSyntaxException {
    JsonReader jsonReader = newJsonReader(paramReader);
    paramType = fromJson(jsonReader, paramType);
    assertFullConsumption(paramType, jsonReader);
    return (T)paramType;
  }
  
  public <T> T fromJson(String paramString, Class<T> paramClass) throws JsonSyntaxException {
    paramString = fromJson(paramString, paramClass);
    return Primitives.wrap(paramClass).cast(paramString);
  }
  
  public <T> T fromJson(String paramString, Type paramType) throws JsonSyntaxException {
    return (paramString == null) ? null : fromJson(new StringReader(paramString), paramType);
  }
  
  public <T> TypeAdapter<T> getAdapter(TypeToken<T> paramTypeToken) {
    TypeToken<T> typeToken;
    Map<TypeToken<?>, TypeAdapter<?>> map2 = this.typeTokenCache;
    if (paramTypeToken == null) {
      typeToken = (TypeToken)NULL_KEY_SURROGATE;
    } else {
      typeToken = paramTypeToken;
    } 
    TypeAdapter<T> typeAdapter = (TypeAdapter)map2.get(typeToken);
    if (typeAdapter != null)
      return typeAdapter; 
    map2 = (Map<TypeToken<?>, TypeAdapter<?>>)this.calls.get();
    boolean bool = false;
    Map<TypeToken<?>, TypeAdapter<?>> map1 = map2;
    if (map2 == null) {
      map1 = new HashMap<TypeToken<?>, TypeAdapter<?>>();
      this.calls.set(map1);
      bool = true;
    } 
    null = (FutureTypeAdapter)map1.get(paramTypeToken);
    if (null != null)
      return null; 
    try {
      FutureTypeAdapter<?> futureTypeAdapter = new FutureTypeAdapter();
      this();
      map1.put(paramTypeToken, futureTypeAdapter);
      Iterator<TypeAdapterFactory> iterator = this.factories.iterator();
      while (iterator.hasNext()) {
        TypeAdapter<T> typeAdapter1 = ((TypeAdapterFactory)iterator.next()).create(this, paramTypeToken);
        if (typeAdapter1 != null) {
          futureTypeAdapter.setDelegate(typeAdapter1);
          this.typeTokenCache.put(paramTypeToken, typeAdapter1);
          return typeAdapter1;
        } 
      } 
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("GSON cannot handle ");
      stringBuilder.append(paramTypeToken);
      this(stringBuilder.toString());
      throw illegalArgumentException;
    } finally {
      map1.remove(paramTypeToken);
      if (bool)
        this.calls.remove(); 
    } 
  }
  
  public <T> TypeAdapter<T> getAdapter(Class<T> paramClass) {
    return getAdapter(TypeToken.get(paramClass));
  }
  
  public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory paramTypeAdapterFactory, TypeToken<T> paramTypeToken) {
    JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory;
    TypeAdapterFactory typeAdapterFactory = paramTypeAdapterFactory;
    if (!this.factories.contains(paramTypeAdapterFactory))
      jsonAdapterAnnotationTypeAdapterFactory = this.jsonAdapterFactory; 
    boolean bool = false;
    for (TypeAdapterFactory typeAdapterFactory1 : this.factories) {
      if (!bool) {
        if (typeAdapterFactory1 == jsonAdapterAnnotationTypeAdapterFactory)
          bool = true; 
        continue;
      } 
      TypeAdapter<T> typeAdapter = typeAdapterFactory1.create(this, paramTypeToken);
      if (typeAdapter != null)
        return typeAdapter; 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("GSON cannot serialize ");
    stringBuilder.append(paramTypeToken);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean htmlSafe() {
    return this.htmlSafe;
  }
  
  public JsonReader newJsonReader(Reader paramReader) {
    JsonReader jsonReader = new JsonReader(paramReader);
    jsonReader.setLenient(this.lenient);
    return jsonReader;
  }
  
  public JsonWriter newJsonWriter(Writer paramWriter) throws IOException {
    if (this.generateNonExecutableJson)
      paramWriter.write(")]}'\n"); 
    JsonWriter jsonWriter = new JsonWriter(paramWriter);
    if (this.prettyPrinting)
      jsonWriter.setIndent("  "); 
    jsonWriter.setSerializeNulls(this.serializeNulls);
    return jsonWriter;
  }
  
  public boolean serializeNulls() {
    return this.serializeNulls;
  }
  
  public String toJson(JsonElement paramJsonElement) {
    StringWriter stringWriter = new StringWriter();
    toJson(paramJsonElement, stringWriter);
    return stringWriter.toString();
  }
  
  public String toJson(Object paramObject) {
    return (paramObject == null) ? toJson(JsonNull.INSTANCE) : toJson(paramObject, paramObject.getClass());
  }
  
  public String toJson(Object paramObject, Type paramType) {
    StringWriter stringWriter = new StringWriter();
    toJson(paramObject, paramType, stringWriter);
    return stringWriter.toString();
  }
  
  public void toJson(JsonElement paramJsonElement, JsonWriter paramJsonWriter) throws JsonIOException {
    boolean bool1 = paramJsonWriter.isLenient();
    paramJsonWriter.setLenient(true);
    boolean bool3 = paramJsonWriter.isHtmlSafe();
    paramJsonWriter.setHtmlSafe(this.htmlSafe);
    boolean bool2 = paramJsonWriter.getSerializeNulls();
    paramJsonWriter.setSerializeNulls(this.serializeNulls);
    try {
      Streams.write(paramJsonElement, paramJsonWriter);
      paramJsonWriter.setLenient(bool1);
      paramJsonWriter.setHtmlSafe(bool3);
      paramJsonWriter.setSerializeNulls(bool2);
      return;
    } catch (IOException iOException) {
      JsonIOException jsonIOException = new JsonIOException();
      this(iOException);
      throw jsonIOException;
    } finally {}
    paramJsonWriter.setLenient(bool1);
    paramJsonWriter.setHtmlSafe(bool3);
    paramJsonWriter.setSerializeNulls(bool2);
    throw paramJsonElement;
  }
  
  public void toJson(JsonElement paramJsonElement, Appendable paramAppendable) throws JsonIOException {
    try {
      toJson(paramJsonElement, newJsonWriter(Streams.writerForAppendable(paramAppendable)));
      return;
    } catch (IOException iOException) {
      throw new JsonIOException(iOException);
    } 
  }
  
  public void toJson(Object paramObject, Appendable paramAppendable) throws JsonIOException {
    if (paramObject != null) {
      toJson(paramObject, paramObject.getClass(), paramAppendable);
    } else {
      toJson(JsonNull.INSTANCE, paramAppendable);
    } 
  }
  
  public void toJson(Object paramObject, Type paramType, JsonWriter paramJsonWriter) throws JsonIOException {
    TypeAdapter<?> typeAdapter = getAdapter(TypeToken.get(paramType));
    boolean bool3 = paramJsonWriter.isLenient();
    paramJsonWriter.setLenient(true);
    boolean bool2 = paramJsonWriter.isHtmlSafe();
    paramJsonWriter.setHtmlSafe(this.htmlSafe);
    boolean bool1 = paramJsonWriter.getSerializeNulls();
    paramJsonWriter.setSerializeNulls(this.serializeNulls);
    try {
      typeAdapter.write(paramJsonWriter, paramObject);
      paramJsonWriter.setLenient(bool3);
      paramJsonWriter.setHtmlSafe(bool2);
      paramJsonWriter.setSerializeNulls(bool1);
      return;
    } catch (IOException iOException) {
      paramObject = new JsonIOException();
      super(iOException);
      throw paramObject;
    } finally {}
    paramJsonWriter.setLenient(bool3);
    paramJsonWriter.setHtmlSafe(bool2);
    paramJsonWriter.setSerializeNulls(bool1);
    throw paramObject;
  }
  
  public void toJson(Object paramObject, Type paramType, Appendable paramAppendable) throws JsonIOException {
    try {
      toJson(paramObject, paramType, newJsonWriter(Streams.writerForAppendable(paramAppendable)));
      return;
    } catch (IOException iOException) {
      throw new JsonIOException(iOException);
    } 
  }
  
  public JsonElement toJsonTree(Object paramObject) {
    return (paramObject == null) ? JsonNull.INSTANCE : toJsonTree(paramObject, paramObject.getClass());
  }
  
  public JsonElement toJsonTree(Object paramObject, Type paramType) {
    JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
    toJson(paramObject, paramType, (JsonWriter)jsonTreeWriter);
    return jsonTreeWriter.get();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{serializeNulls:");
    stringBuilder.append(this.serializeNulls);
    stringBuilder.append(",factories:");
    stringBuilder.append(this.factories);
    stringBuilder.append(",instanceCreators:");
    stringBuilder.append(this.constructorConstructor);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  static class FutureTypeAdapter<T> extends TypeAdapter<T> {
    private TypeAdapter<T> delegate;
    
    public T read(JsonReader param1JsonReader) throws IOException {
      TypeAdapter<T> typeAdapter = this.delegate;
      if (typeAdapter != null)
        return typeAdapter.read(param1JsonReader); 
      throw new IllegalStateException();
    }
    
    public void setDelegate(TypeAdapter<T> param1TypeAdapter) {
      if (this.delegate == null) {
        this.delegate = param1TypeAdapter;
        return;
      } 
      throw new AssertionError();
    }
    
    public void write(JsonWriter param1JsonWriter, T param1T) throws IOException {
      TypeAdapter<T> typeAdapter = this.delegate;
      if (typeAdapter != null) {
        typeAdapter.write(param1JsonWriter, param1T);
        return;
      } 
      throw new IllegalStateException();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\Gson.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */