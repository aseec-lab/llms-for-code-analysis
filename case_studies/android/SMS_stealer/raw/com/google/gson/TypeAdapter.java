package com.google.gson;

import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T> {
  public final T fromJson(Reader paramReader) throws IOException {
    return read(new JsonReader(paramReader));
  }
  
  public final T fromJson(String paramString) throws IOException {
    return fromJson(new StringReader(paramString));
  }
  
  public final T fromJsonTree(JsonElement paramJsonElement) {
    try {
      JsonTreeReader jsonTreeReader = new JsonTreeReader();
      this(paramJsonElement);
      return read((JsonReader)jsonTreeReader);
    } catch (IOException iOException) {
      throw new JsonIOException(iOException);
    } 
  }
  
  public final TypeAdapter<T> nullSafe() {
    return new TypeAdapter<T>() {
        final TypeAdapter this$0;
        
        public T read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return TypeAdapter.this.read(param1JsonReader);
        }
        
        public void write(JsonWriter param1JsonWriter, T param1T) throws IOException {
          if (param1T == null) {
            param1JsonWriter.nullValue();
          } else {
            TypeAdapter.this.write(param1JsonWriter, param1T);
          } 
        }
      };
  }
  
  public abstract T read(JsonReader paramJsonReader) throws IOException;
  
  public final String toJson(T paramT) {
    StringWriter stringWriter = new StringWriter();
    try {
      toJson(stringWriter, paramT);
      return stringWriter.toString();
    } catch (IOException iOException) {
      throw new AssertionError(iOException);
    } 
  }
  
  public final void toJson(Writer paramWriter, T paramT) throws IOException {
    write(new JsonWriter(paramWriter), paramT);
  }
  
  public final JsonElement toJsonTree(T paramT) {
    try {
      JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
      this();
      write((JsonWriter)jsonTreeWriter, paramT);
      return jsonTreeWriter.get();
    } catch (IOException iOException) {
      throw new JsonIOException(iOException);
    } 
  }
  
  public abstract void write(JsonWriter paramJsonWriter, T paramT) throws IOException;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\TypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */