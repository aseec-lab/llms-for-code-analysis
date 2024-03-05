package com.google.gson;

import com.google.gson.internal.;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GsonBuilder {
  private boolean complexMapKeySerialization = false;
  
  private String datePattern;
  
  private int dateStyle = 2;
  
  private boolean escapeHtmlChars = true;
  
  private Excluder excluder = Excluder.DEFAULT;
  
  private final List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
  
  private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
  
  private boolean generateNonExecutableJson = false;
  
  private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<TypeAdapterFactory>();
  
  private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<Type, InstanceCreator<?>>();
  
  private boolean lenient = false;
  
  private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
  
  private boolean prettyPrinting = false;
  
  private boolean serializeNulls = false;
  
  private boolean serializeSpecialFloatingPointValues = false;
  
  private int timeStyle = 2;
  
  private void addTypeAdaptersForDate(String paramString, int paramInt1, int paramInt2, List<TypeAdapterFactory> paramList) {
    DefaultDateTypeAdapter defaultDateTypeAdapter1;
    DefaultDateTypeAdapter defaultDateTypeAdapter2;
    DefaultDateTypeAdapter defaultDateTypeAdapter3;
    if (paramString != null && !"".equals(paramString.trim())) {
      defaultDateTypeAdapter3 = new DefaultDateTypeAdapter(Date.class, paramString);
      defaultDateTypeAdapter2 = new DefaultDateTypeAdapter((Class)Timestamp.class, paramString);
      defaultDateTypeAdapter1 = new DefaultDateTypeAdapter((Class)Date.class, paramString);
    } else if (paramInt1 != 2 && paramInt2 != 2) {
      defaultDateTypeAdapter1 = new DefaultDateTypeAdapter(Date.class, paramInt1, paramInt2);
      defaultDateTypeAdapter2 = new DefaultDateTypeAdapter((Class)Timestamp.class, paramInt1, paramInt2);
      DefaultDateTypeAdapter defaultDateTypeAdapter = new DefaultDateTypeAdapter((Class)Date.class, paramInt1, paramInt2);
      defaultDateTypeAdapter3 = defaultDateTypeAdapter1;
      defaultDateTypeAdapter1 = defaultDateTypeAdapter;
    } else {
      return;
    } 
    paramList.add(TypeAdapters.newFactory(Date.class, defaultDateTypeAdapter3));
    paramList.add(TypeAdapters.newFactory(Timestamp.class, defaultDateTypeAdapter2));
    paramList.add(TypeAdapters.newFactory(Date.class, defaultDateTypeAdapter1));
  }
  
  public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy paramExclusionStrategy) {
    this.excluder = this.excluder.withExclusionStrategy(paramExclusionStrategy, false, true);
    return this;
  }
  
  public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy paramExclusionStrategy) {
    this.excluder = this.excluder.withExclusionStrategy(paramExclusionStrategy, true, false);
    return this;
  }
  
  public Gson create() {
    ArrayList<TypeAdapterFactory> arrayList1 = new ArrayList(this.factories.size() + this.hierarchyFactories.size() + 3);
    arrayList1.addAll(this.factories);
    Collections.reverse(arrayList1);
    ArrayList<TypeAdapterFactory> arrayList2 = new ArrayList<TypeAdapterFactory>(this.hierarchyFactories);
    Collections.reverse(arrayList2);
    arrayList1.addAll(arrayList2);
    addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, arrayList1);
    return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, arrayList1);
  }
  
  public GsonBuilder disableHtmlEscaping() {
    this.escapeHtmlChars = false;
    return this;
  }
  
  public GsonBuilder disableInnerClassSerialization() {
    this.excluder = this.excluder.disableInnerClassSerialization();
    return this;
  }
  
  public GsonBuilder enableComplexMapKeySerialization() {
    this.complexMapKeySerialization = true;
    return this;
  }
  
  public GsonBuilder excludeFieldsWithModifiers(int... paramVarArgs) {
    this.excluder = this.excluder.withModifiers(paramVarArgs);
    return this;
  }
  
  public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
    this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
    return this;
  }
  
  public GsonBuilder generateNonExecutableJson() {
    this.generateNonExecutableJson = true;
    return this;
  }
  
  public GsonBuilder registerTypeAdapter(Type paramType, Object paramObject) {
    boolean bool;
    boolean bool1 = paramObject instanceof JsonSerializer;
    if (bool1 || paramObject instanceof JsonDeserializer || paramObject instanceof InstanceCreator || paramObject instanceof TypeAdapter) {
      bool = true;
    } else {
      bool = false;
    } 
    .Gson.Preconditions.checkArgument(bool);
    if (paramObject instanceof InstanceCreator)
      this.instanceCreators.put(paramType, (InstanceCreator)paramObject); 
    if (bool1 || paramObject instanceof JsonDeserializer) {
      TypeToken typeToken = TypeToken.get(paramType);
      this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, paramObject));
    } 
    if (paramObject instanceof TypeAdapter)
      this.factories.add(TypeAdapters.newFactory(TypeToken.get(paramType), (TypeAdapter)paramObject)); 
    return this;
  }
  
  public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory paramTypeAdapterFactory) {
    this.factories.add(paramTypeAdapterFactory);
    return this;
  }
  
  public GsonBuilder registerTypeHierarchyAdapter(Class<?> paramClass, Object paramObject) {
    boolean bool;
    boolean bool1 = paramObject instanceof JsonSerializer;
    if (bool1 || paramObject instanceof JsonDeserializer || paramObject instanceof TypeAdapter) {
      bool = true;
    } else {
      bool = false;
    } 
    .Gson.Preconditions.checkArgument(bool);
    if (paramObject instanceof JsonDeserializer || bool1)
      this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(paramClass, paramObject)); 
    if (paramObject instanceof TypeAdapter)
      this.factories.add(TypeAdapters.newTypeHierarchyFactory(paramClass, (TypeAdapter)paramObject)); 
    return this;
  }
  
  public GsonBuilder serializeNulls() {
    this.serializeNulls = true;
    return this;
  }
  
  public GsonBuilder serializeSpecialFloatingPointValues() {
    this.serializeSpecialFloatingPointValues = true;
    return this;
  }
  
  public GsonBuilder setDateFormat(int paramInt) {
    this.dateStyle = paramInt;
    this.datePattern = null;
    return this;
  }
  
  public GsonBuilder setDateFormat(int paramInt1, int paramInt2) {
    this.dateStyle = paramInt1;
    this.timeStyle = paramInt2;
    this.datePattern = null;
    return this;
  }
  
  public GsonBuilder setDateFormat(String paramString) {
    this.datePattern = paramString;
    return this;
  }
  
  public GsonBuilder setExclusionStrategies(ExclusionStrategy... paramVarArgs) {
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++) {
      ExclusionStrategy exclusionStrategy = paramVarArgs[b];
      this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, true, true);
    } 
    return this;
  }
  
  public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy paramFieldNamingPolicy) {
    this.fieldNamingPolicy = paramFieldNamingPolicy;
    return this;
  }
  
  public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy paramFieldNamingStrategy) {
    this.fieldNamingPolicy = paramFieldNamingStrategy;
    return this;
  }
  
  public GsonBuilder setLenient() {
    this.lenient = true;
    return this;
  }
  
  public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy paramLongSerializationPolicy) {
    this.longSerializationPolicy = paramLongSerializationPolicy;
    return this;
  }
  
  public GsonBuilder setPrettyPrinting() {
    this.prettyPrinting = true;
    return this;
  }
  
  public GsonBuilder setVersion(double paramDouble) {
    this.excluder = this.excluder.withVersion(paramDouble);
    return this;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\GsonBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */