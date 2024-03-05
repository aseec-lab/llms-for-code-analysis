package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;

public final class JsonTreeReader extends JsonReader {
  private static final Object SENTINEL_CLOSED;
  
  private static final Reader UNREADABLE_READER = new Reader() {
      public void close() throws IOException {
        throw new AssertionError();
      }
      
      public int read(char[] param1ArrayOfchar, int param1Int1, int param1Int2) throws IOException {
        throw new AssertionError();
      }
    };
  
  private int[] pathIndices = new int[32];
  
  private String[] pathNames = new String[32];
  
  private Object[] stack = new Object[32];
  
  private int stackSize = 0;
  
  static {
    SENTINEL_CLOSED = new Object();
  }
  
  public JsonTreeReader(JsonElement paramJsonElement) {
    super(UNREADABLE_READER);
    push(paramJsonElement);
  }
  
  private void expect(JsonToken paramJsonToken) throws IOException {
    if (peek() == paramJsonToken)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(paramJsonToken);
    stringBuilder.append(" but was ");
    stringBuilder.append(peek());
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  private String locationString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(" at path ");
    stringBuilder.append(getPath());
    return stringBuilder.toString();
  }
  
  private Object peekStack() {
    return this.stack[this.stackSize - 1];
  }
  
  private Object popStack() {
    Object[] arrayOfObject = this.stack;
    int i = this.stackSize - 1;
    this.stackSize = i;
    Object object = arrayOfObject[i];
    arrayOfObject[i] = null;
    return object;
  }
  
  private void push(Object paramObject) {
    int i = this.stackSize;
    Object[] arrayOfObject2 = this.stack;
    if (i == arrayOfObject2.length) {
      Object[] arrayOfObject = new Object[i * 2];
      int[] arrayOfInt = new int[i * 2];
      String[] arrayOfString = new String[i * 2];
      System.arraycopy(arrayOfObject2, 0, arrayOfObject, 0, i);
      System.arraycopy(this.pathIndices, 0, arrayOfInt, 0, this.stackSize);
      System.arraycopy(this.pathNames, 0, arrayOfString, 0, this.stackSize);
      this.stack = arrayOfObject;
      this.pathIndices = arrayOfInt;
      this.pathNames = arrayOfString;
    } 
    Object[] arrayOfObject1 = this.stack;
    i = this.stackSize;
    this.stackSize = i + 1;
    arrayOfObject1[i] = paramObject;
  }
  
  public void beginArray() throws IOException {
    expect(JsonToken.BEGIN_ARRAY);
    push(((JsonArray)peekStack()).iterator());
    this.pathIndices[this.stackSize - 1] = 0;
  }
  
  public void beginObject() throws IOException {
    expect(JsonToken.BEGIN_OBJECT);
    push(((JsonObject)peekStack()).entrySet().iterator());
  }
  
  public void close() throws IOException {
    this.stack = new Object[] { SENTINEL_CLOSED };
    this.stackSize = 1;
  }
  
  public void endArray() throws IOException {
    expect(JsonToken.END_ARRAY);
    popStack();
    popStack();
    int i = this.stackSize;
    if (i > 0) {
      int[] arrayOfInt = this.pathIndices;
      arrayOfInt[--i] = arrayOfInt[i] + 1;
    } 
  }
  
  public void endObject() throws IOException {
    expect(JsonToken.END_OBJECT);
    popStack();
    popStack();
    int i = this.stackSize;
    if (i > 0) {
      int[] arrayOfInt = this.pathIndices;
      arrayOfInt[--i] = arrayOfInt[i] + 1;
    } 
  }
  
  public String getPath() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('$');
    for (int i = 0; i < this.stackSize; i = j + 1) {
      int j;
      Object[] arrayOfObject = this.stack;
      if (arrayOfObject[i] instanceof JsonArray) {
        j = ++i;
        if (arrayOfObject[i] instanceof Iterator) {
          stringBuilder.append('[');
          stringBuilder.append(this.pathIndices[i]);
          stringBuilder.append(']');
          j = i;
        } 
      } else {
        j = i;
        if (arrayOfObject[i] instanceof JsonObject) {
          j = ++i;
          if (arrayOfObject[i] instanceof Iterator) {
            stringBuilder.append('.');
            String[] arrayOfString = this.pathNames;
            j = i;
            if (arrayOfString[i] != null) {
              stringBuilder.append(arrayOfString[i]);
              j = i;
            } 
          } 
        } 
      } 
    } 
    return stringBuilder.toString();
  }
  
  public boolean hasNext() throws IOException {
    boolean bool;
    JsonToken jsonToken = peek();
    if (jsonToken != JsonToken.END_OBJECT && jsonToken != JsonToken.END_ARRAY) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean nextBoolean() throws IOException {
    expect(JsonToken.BOOLEAN);
    boolean bool = ((JsonPrimitive)popStack()).getAsBoolean();
    int i = this.stackSize;
    if (i > 0) {
      int[] arrayOfInt = this.pathIndices;
      arrayOfInt[--i] = arrayOfInt[i] + 1;
    } 
    return bool;
  }
  
  public double nextDouble() throws IOException {
    JsonToken jsonToken = peek();
    if (jsonToken == JsonToken.NUMBER || jsonToken == JsonToken.STRING) {
      double d = ((JsonPrimitive)peekStack()).getAsDouble();
      if (isLenient() || (!Double.isNaN(d) && !Double.isInfinite(d))) {
        popStack();
        int i = this.stackSize;
        if (i > 0) {
          int[] arrayOfInt = this.pathIndices;
          arrayOfInt[--i] = arrayOfInt[i] + 1;
        } 
        return d;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("JSON forbids NaN and infinities: ");
      stringBuilder1.append(d);
      throw new NumberFormatException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(JsonToken.NUMBER);
    stringBuilder.append(" but was ");
    stringBuilder.append(jsonToken);
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public int nextInt() throws IOException {
    JsonToken jsonToken = peek();
    if (jsonToken == JsonToken.NUMBER || jsonToken == JsonToken.STRING) {
      int i = ((JsonPrimitive)peekStack()).getAsInt();
      popStack();
      int j = this.stackSize;
      if (j > 0) {
        int[] arrayOfInt = this.pathIndices;
        arrayOfInt[--j] = arrayOfInt[j] + 1;
      } 
      return i;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(JsonToken.NUMBER);
    stringBuilder.append(" but was ");
    stringBuilder.append(jsonToken);
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public long nextLong() throws IOException {
    JsonToken jsonToken = peek();
    if (jsonToken == JsonToken.NUMBER || jsonToken == JsonToken.STRING) {
      long l = ((JsonPrimitive)peekStack()).getAsLong();
      popStack();
      int i = this.stackSize;
      if (i > 0) {
        int[] arrayOfInt = this.pathIndices;
        arrayOfInt[--i] = arrayOfInt[i] + 1;
      } 
      return l;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(JsonToken.NUMBER);
    stringBuilder.append(" but was ");
    stringBuilder.append(jsonToken);
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public String nextName() throws IOException {
    expect(JsonToken.NAME);
    Map.Entry entry = ((Iterator<Map.Entry>)peekStack()).next();
    String str = (String)entry.getKey();
    this.pathNames[this.stackSize - 1] = str;
    push(entry.getValue());
    return str;
  }
  
  public void nextNull() throws IOException {
    expect(JsonToken.NULL);
    popStack();
    int i = this.stackSize;
    if (i > 0) {
      int[] arrayOfInt = this.pathIndices;
      arrayOfInt[--i] = arrayOfInt[i] + 1;
    } 
  }
  
  public String nextString() throws IOException {
    String str;
    JsonToken jsonToken = peek();
    if (jsonToken == JsonToken.STRING || jsonToken == JsonToken.NUMBER) {
      str = ((JsonPrimitive)popStack()).getAsString();
      int i = this.stackSize;
      if (i > 0) {
        int[] arrayOfInt = this.pathIndices;
        arrayOfInt[--i] = arrayOfInt[i] + 1;
      } 
      return str;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(JsonToken.STRING);
    stringBuilder.append(" but was ");
    stringBuilder.append(str);
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public JsonToken peek() throws IOException {
    if (this.stackSize == 0)
      return JsonToken.END_DOCUMENT; 
    Object object = peekStack();
    if (object instanceof Iterator) {
      boolean bool = this.stack[this.stackSize - 2] instanceof JsonObject;
      object = object;
      if (object.hasNext()) {
        if (bool)
          return JsonToken.NAME; 
        push(object.next());
        return peek();
      } 
      if (bool) {
        object = JsonToken.END_OBJECT;
      } else {
        object = JsonToken.END_ARRAY;
      } 
      return (JsonToken)object;
    } 
    if (object instanceof JsonObject)
      return JsonToken.BEGIN_OBJECT; 
    if (object instanceof JsonArray)
      return JsonToken.BEGIN_ARRAY; 
    if (object instanceof JsonPrimitive) {
      object = object;
      if (object.isString())
        return JsonToken.STRING; 
      if (object.isBoolean())
        return JsonToken.BOOLEAN; 
      if (object.isNumber())
        return JsonToken.NUMBER; 
      throw new AssertionError();
    } 
    if (object instanceof com.google.gson.JsonNull)
      return JsonToken.NULL; 
    if (object == SENTINEL_CLOSED)
      throw new IllegalStateException("JsonReader is closed"); 
    throw new AssertionError();
  }
  
  public void promoteNameToValue() throws IOException {
    expect(JsonToken.NAME);
    Map.Entry entry = ((Iterator<Map.Entry>)peekStack()).next();
    push(entry.getValue());
    push(new JsonPrimitive((String)entry.getKey()));
  }
  
  public void skipValue() throws IOException {
    if (peek() == JsonToken.NAME) {
      nextName();
      this.pathNames[this.stackSize - 2] = "null";
    } else {
      popStack();
      int j = this.stackSize;
      if (j > 0)
        this.pathNames[j - 1] = "null"; 
    } 
    int i = this.stackSize;
    if (i > 0) {
      int[] arrayOfInt = this.pathIndices;
      arrayOfInt[--i] = arrayOfInt[i] + 1;
    } 
  }
  
  public String toString() {
    return getClass().getSimpleName();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\JsonTreeReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */