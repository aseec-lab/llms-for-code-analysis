package com.androidnetworking.gsonparserfactory;

import com.androidnetworking.interfaces.Parser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public final class GsonParserFactory extends Parser.Factory {
  private final Gson gson = new Gson();
  
  public GsonParserFactory() {}
  
  public GsonParserFactory(Gson paramGson) {}
  
  public Object getObject(String paramString, Type paramType) {
    try {
      return this.gson.fromJson(paramString, paramType);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public String getString(Object paramObject) {
    try {
      return this.gson.toJson(paramObject);
    } catch (Exception exception) {
      exception.printStackTrace();
      return "";
    } 
  }
  
  public HashMap<String, String> getStringMap(Object paramObject) {
    try {
      TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>() {
          final GsonParserFactory this$0;
        };
      super(this);
      Type type = typeToken.getType();
      return (HashMap)this.gson.fromJson(this.gson.toJson(paramObject), type);
    } catch (Exception exception) {
      exception.printStackTrace();
      return new HashMap<String, String>();
    } 
  }
  
  public Parser<?, RequestBody> requestBodyParser(Type paramType) {
    TypeAdapter<?> typeAdapter = this.gson.getAdapter(TypeToken.get(paramType));
    return new GsonRequestBodyParser(this.gson, typeAdapter);
  }
  
  public Parser<ResponseBody, ?> responseBodyParser(Type paramType) {
    TypeAdapter<?> typeAdapter = this.gson.getAdapter(TypeToken.get(paramType));
    return new GsonResponseBodyParser(this.gson, typeAdapter);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\gsonparserfactory\GsonParserFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */