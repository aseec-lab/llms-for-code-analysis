package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JsonStreamParser implements Iterator<JsonElement> {
  private final Object lock;
  
  private final JsonReader parser;
  
  public JsonStreamParser(Reader paramReader) {
    JsonReader jsonReader = new JsonReader(paramReader);
    this.parser = jsonReader;
    jsonReader.setLenient(true);
    this.lock = new Object();
  }
  
  public JsonStreamParser(String paramString) {
    this(new StringReader(paramString));
  }
  
  public boolean hasNext() {
    Exception exception;
    Object object = this.lock;
    /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
    try {
      boolean bool;
      JsonToken jsonToken2 = this.parser.peek();
      JsonToken jsonToken1 = JsonToken.END_DOCUMENT;
      if (jsonToken2 != jsonToken1) {
        bool = true;
      } else {
        bool = false;
      } 
      /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
      return bool;
    } catch (MalformedJsonException malformedJsonException) {
      exception = new JsonSyntaxException();
      this((Throwable)malformedJsonException);
      throw exception;
    } catch (IOException iOException) {
      exception = new JsonIOException();
      this(iOException);
      throw exception;
    } finally {}
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
    throw exception;
  }
  
  public JsonElement next() throws JsonParseException {
    if (hasNext())
      try {
        return Streams.parse(this.parser);
      } catch (StackOverflowError stackOverflowError) {
        throw new JsonParseException("Failed parsing JSON source to Json", stackOverflowError);
      } catch (OutOfMemoryError outOfMemoryError) {
        throw new JsonParseException("Failed parsing JSON source to Json", outOfMemoryError);
      } catch (JsonParseException jsonParseException2) {
        NoSuchElementException noSuchElementException;
        JsonParseException jsonParseException1 = jsonParseException2;
        if (jsonParseException2.getCause() instanceof java.io.EOFException)
          noSuchElementException = new NoSuchElementException(); 
        throw noSuchElementException;
      }  
    throw new NoSuchElementException();
  }
  
  public void remove() {
    throw new UnsupportedOperationException();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\JsonStreamParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */