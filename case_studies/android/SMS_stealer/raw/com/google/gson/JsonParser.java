package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
  public JsonElement parse(JsonReader paramJsonReader) throws JsonIOException, JsonSyntaxException {
    Exception exception;
    boolean bool = paramJsonReader.isLenient();
    paramJsonReader.setLenient(true);
    try {
      JsonElement jsonElement = Streams.parse(paramJsonReader);
      paramJsonReader.setLenient(bool);
      return jsonElement;
    } catch (StackOverflowError stackOverflowError) {
      JsonParseException jsonParseException = new JsonParseException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Failed parsing JSON source: ");
      stringBuilder.append(paramJsonReader);
      stringBuilder.append(" to Json");
      this(stringBuilder.toString(), stackOverflowError);
      throw jsonParseException;
    } catch (OutOfMemoryError outOfMemoryError) {
      JsonParseException jsonParseException = new JsonParseException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Failed parsing JSON source: ");
      stringBuilder.append(paramJsonReader);
      stringBuilder.append(" to Json");
      this(stringBuilder.toString(), outOfMemoryError);
      throw jsonParseException;
    } finally {}
    paramJsonReader.setLenient(bool);
    throw exception;
  }
  
  public JsonElement parse(Reader paramReader) throws JsonIOException, JsonSyntaxException {
    try {
      JsonReader jsonReader = new JsonReader();
      this(paramReader);
      JsonElement jsonElement = parse(jsonReader);
      if (jsonElement.isJsonNull() || jsonReader.peek() == JsonToken.END_DOCUMENT)
        return jsonElement; 
      JsonSyntaxException jsonSyntaxException = new JsonSyntaxException();
      this("Did not consume the entire document.");
      throw jsonSyntaxException;
    } catch (MalformedJsonException malformedJsonException) {
      throw new JsonSyntaxException(malformedJsonException);
    } catch (IOException iOException) {
      throw new JsonIOException(iOException);
    } catch (NumberFormatException numberFormatException) {
      throw new JsonSyntaxException(numberFormatException);
    } 
  }
  
  public JsonElement parse(String paramString) throws JsonSyntaxException {
    return parse(new StringReader(paramString));
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\JsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */