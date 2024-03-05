package com.google.gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
  private final List<JsonElement> elements = new ArrayList<JsonElement>();
  
  public JsonArray() {}
  
  public JsonArray(int paramInt) {}
  
  public void add(JsonElement paramJsonElement) {
    JsonElement jsonElement = paramJsonElement;
    if (paramJsonElement == null)
      jsonElement = JsonNull.INSTANCE; 
    this.elements.add(jsonElement);
  }
  
  public void add(Boolean paramBoolean) {
    JsonNull jsonNull;
    JsonPrimitive jsonPrimitive;
    List<JsonElement> list = this.elements;
    if (paramBoolean == null) {
      jsonNull = JsonNull.INSTANCE;
    } else {
      jsonPrimitive = new JsonPrimitive((Boolean)jsonNull);
    } 
    list.add(jsonPrimitive);
  }
  
  public void add(Character paramCharacter) {
    JsonNull jsonNull;
    JsonPrimitive jsonPrimitive;
    List<JsonElement> list = this.elements;
    if (paramCharacter == null) {
      jsonNull = JsonNull.INSTANCE;
    } else {
      jsonPrimitive = new JsonPrimitive((Character)jsonNull);
    } 
    list.add(jsonPrimitive);
  }
  
  public void add(Number paramNumber) {
    JsonNull jsonNull;
    JsonPrimitive jsonPrimitive;
    List<JsonElement> list = this.elements;
    if (paramNumber == null) {
      jsonNull = JsonNull.INSTANCE;
    } else {
      jsonPrimitive = new JsonPrimitive((Number)jsonNull);
    } 
    list.add(jsonPrimitive);
  }
  
  public void add(String paramString) {
    JsonNull jsonNull;
    JsonPrimitive jsonPrimitive;
    List<JsonElement> list = this.elements;
    if (paramString == null) {
      jsonNull = JsonNull.INSTANCE;
    } else {
      jsonPrimitive = new JsonPrimitive((String)jsonNull);
    } 
    list.add(jsonPrimitive);
  }
  
  public void addAll(JsonArray paramJsonArray) {
    this.elements.addAll(paramJsonArray.elements);
  }
  
  public boolean contains(JsonElement paramJsonElement) {
    return this.elements.contains(paramJsonElement);
  }
  
  public JsonArray deepCopy() {
    if (!this.elements.isEmpty()) {
      JsonArray jsonArray = new JsonArray(this.elements.size());
      Iterator<JsonElement> iterator = this.elements.iterator();
      while (iterator.hasNext())
        jsonArray.add(((JsonElement)iterator.next()).deepCopy()); 
      return jsonArray;
    } 
    return new JsonArray();
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject == this || (paramObject instanceof JsonArray && ((JsonArray)paramObject).elements.equals(this.elements)));
  }
  
  public JsonElement get(int paramInt) {
    return this.elements.get(paramInt);
  }
  
  public BigDecimal getAsBigDecimal() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsBigDecimal(); 
    throw new IllegalStateException();
  }
  
  public BigInteger getAsBigInteger() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsBigInteger(); 
    throw new IllegalStateException();
  }
  
  public boolean getAsBoolean() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsBoolean(); 
    throw new IllegalStateException();
  }
  
  public byte getAsByte() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsByte(); 
    throw new IllegalStateException();
  }
  
  public char getAsCharacter() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsCharacter(); 
    throw new IllegalStateException();
  }
  
  public double getAsDouble() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsDouble(); 
    throw new IllegalStateException();
  }
  
  public float getAsFloat() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsFloat(); 
    throw new IllegalStateException();
  }
  
  public int getAsInt() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsInt(); 
    throw new IllegalStateException();
  }
  
  public long getAsLong() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsLong(); 
    throw new IllegalStateException();
  }
  
  public Number getAsNumber() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsNumber(); 
    throw new IllegalStateException();
  }
  
  public short getAsShort() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsShort(); 
    throw new IllegalStateException();
  }
  
  public String getAsString() {
    if (this.elements.size() == 1)
      return ((JsonElement)this.elements.get(0)).getAsString(); 
    throw new IllegalStateException();
  }
  
  public int hashCode() {
    return this.elements.hashCode();
  }
  
  public Iterator<JsonElement> iterator() {
    return this.elements.iterator();
  }
  
  public JsonElement remove(int paramInt) {
    return this.elements.remove(paramInt);
  }
  
  public boolean remove(JsonElement paramJsonElement) {
    return this.elements.remove(paramJsonElement);
  }
  
  public JsonElement set(int paramInt, JsonElement paramJsonElement) {
    return this.elements.set(paramInt, paramJsonElement);
  }
  
  public int size() {
    return this.elements.size();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\JsonArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */