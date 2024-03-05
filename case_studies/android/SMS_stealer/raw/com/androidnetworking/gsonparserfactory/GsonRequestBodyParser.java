package com.androidnetworking.gsonparserfactory;

import com.androidnetworking.interfaces.Parser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;

final class GsonRequestBodyParser<T> implements Parser<T, RequestBody> {
  private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
  
  private static final Charset UTF_8 = Charset.forName("UTF-8");
  
  private final TypeAdapter<T> adapter;
  
  private final Gson gson;
  
  GsonRequestBodyParser(Gson paramGson, TypeAdapter<T> paramTypeAdapter) {
    this.gson = paramGson;
    this.adapter = paramTypeAdapter;
  }
  
  public RequestBody convert(T paramT) throws IOException {
    Buffer buffer = new Buffer();
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(buffer.outputStream(), UTF_8);
    JsonWriter jsonWriter = this.gson.newJsonWriter(outputStreamWriter);
    this.adapter.write(jsonWriter, paramT);
    jsonWriter.close();
    return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\gsonparserfactory\GsonRequestBodyParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */