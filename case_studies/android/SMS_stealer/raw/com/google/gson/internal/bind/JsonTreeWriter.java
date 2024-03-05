package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class JsonTreeWriter extends JsonWriter {
  private static final JsonPrimitive SENTINEL_CLOSED;
  
  private static final Writer UNWRITABLE_WRITER = new Writer() {
      public void close() throws IOException {
        throw new AssertionError();
      }
      
      public void flush() throws IOException {
        throw new AssertionError();
      }
      
      public void write(char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
        throw new AssertionError();
      }
    };
  
  private String pendingName;
  
  private JsonElement product = (JsonElement)JsonNull.INSTANCE;
  
  private final List<JsonElement> stack = new ArrayList<JsonElement>();
  
  static {
    SENTINEL_CLOSED = new JsonPrimitive("closed");
  }
  
  public JsonTreeWriter() {
    super(UNWRITABLE_WRITER);
  }
  
  private JsonElement peek() {
    List<JsonElement> list = this.stack;
    return list.get(list.size() - 1);
  }
  
  private void put(JsonElement paramJsonElement) {
    if (this.pendingName != null) {
      if (!paramJsonElement.isJsonNull() || getSerializeNulls())
        ((JsonObject)peek()).add(this.pendingName, paramJsonElement); 
      this.pendingName = null;
    } else if (this.stack.isEmpty()) {
      this.product = paramJsonElement;
    } else {
      JsonElement jsonElement = peek();
      if (jsonElement instanceof JsonArray) {
        ((JsonArray)jsonElement).add(paramJsonElement);
        return;
      } 
      throw new IllegalStateException();
    } 
  }
  
  public JsonWriter beginArray() throws IOException {
    JsonArray jsonArray = new JsonArray();
    put((JsonElement)jsonArray);
    this.stack.add(jsonArray);
    return this;
  }
  
  public JsonWriter beginObject() throws IOException {
    JsonObject jsonObject = new JsonObject();
    put((JsonElement)jsonObject);
    this.stack.add(jsonObject);
    return this;
  }
  
  public void close() throws IOException {
    if (this.stack.isEmpty()) {
      this.stack.add(SENTINEL_CLOSED);
      return;
    } 
    throw new IOException("Incomplete document");
  }
  
  public JsonWriter endArray() throws IOException {
    if (!this.stack.isEmpty() && this.pendingName == null) {
      if (peek() instanceof JsonArray) {
        List<JsonElement> list = this.stack;
        list.remove(list.size() - 1);
        return this;
      } 
      throw new IllegalStateException();
    } 
    throw new IllegalStateException();
  }
  
  public JsonWriter endObject() throws IOException {
    if (!this.stack.isEmpty() && this.pendingName == null) {
      if (peek() instanceof JsonObject) {
        List<JsonElement> list = this.stack;
        list.remove(list.size() - 1);
        return this;
      } 
      throw new IllegalStateException();
    } 
    throw new IllegalStateException();
  }
  
  public void flush() throws IOException {}
  
  public JsonElement get() {
    if (this.stack.isEmpty())
      return this.product; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected one JSON element but was ");
    stringBuilder.append(this.stack);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public JsonWriter name(String paramString) throws IOException {
    if (!this.stack.isEmpty() && this.pendingName == null) {
      if (peek() instanceof JsonObject) {
        this.pendingName = paramString;
        return this;
      } 
      throw new IllegalStateException();
    } 
    throw new IllegalStateException();
  }
  
  public JsonWriter nullValue() throws IOException {
    put((JsonElement)JsonNull.INSTANCE);
    return this;
  }
  
  public JsonWriter value(double paramDouble) throws IOException {
    if (isLenient() || (!Double.isNaN(paramDouble) && !Double.isInfinite(paramDouble))) {
      put((JsonElement)new JsonPrimitive(Double.valueOf(paramDouble)));
      return this;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("JSON forbids NaN and infinities: ");
    stringBuilder.append(paramDouble);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public JsonWriter value(long paramLong) throws IOException {
    put((JsonElement)new JsonPrimitive(Long.valueOf(paramLong)));
    return this;
  }
  
  public JsonWriter value(Boolean paramBoolean) throws IOException {
    if (paramBoolean == null)
      return nullValue(); 
    put((JsonElement)new JsonPrimitive(paramBoolean));
    return this;
  }
  
  public JsonWriter value(Number paramNumber) throws IOException {
    if (paramNumber == null)
      return nullValue(); 
    if (!isLenient()) {
      double d = paramNumber.doubleValue();
      if (Double.isNaN(d) || Double.isInfinite(d)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JSON forbids NaN and infinities: ");
        stringBuilder.append(paramNumber);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    } 
    put((JsonElement)new JsonPrimitive(paramNumber));
    return this;
  }
  
  public JsonWriter value(String paramString) throws IOException {
    if (paramString == null)
      return nullValue(); 
    put((JsonElement)new JsonPrimitive(paramString));
    return this;
  }
  
  public JsonWriter value(boolean paramBoolean) throws IOException {
    put((JsonElement)new JsonPrimitive(Boolean.valueOf(paramBoolean)));
    return this;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\JsonTreeWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */