package com.androidnetworking.gsonparserfactory;

import com.androidnetworking.interfaces.Parser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import okhttp3.ResponseBody;

final class GsonResponseBodyParser<T> implements Parser<ResponseBody, T> {
  private final TypeAdapter<T> adapter;
  
  private final Gson gson;
  
  GsonResponseBodyParser(Gson paramGson, TypeAdapter<T> paramTypeAdapter) {
    this.gson = paramGson;
    this.adapter = paramTypeAdapter;
  }
  
  public T convert(ResponseBody paramResponseBody) throws IOException {
    null = this.gson.newJsonReader(paramResponseBody.charStream());
    try {
      return (T)this.adapter.read(null);
    } finally {
      paramResponseBody.close();
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\gsonparserfactory\GsonResponseBodyParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */