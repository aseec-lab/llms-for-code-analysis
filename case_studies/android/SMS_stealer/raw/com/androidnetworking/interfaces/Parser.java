package com.androidnetworking.interfaces;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public interface Parser<F, T> {
  T convert(F paramF) throws IOException;
  
  public static abstract class Factory {
    public Object getObject(String param1String, Type param1Type) {
      return null;
    }
    
    public String getString(Object param1Object) {
      return null;
    }
    
    public HashMap<String, String> getStringMap(Object param1Object) {
      return null;
    }
    
    public Parser<?, RequestBody> requestBodyParser(Type param1Type) {
      return null;
    }
    
    public Parser<ResponseBody, ?> responseBodyParser(Type param1Type) {
      return null;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\interfaces\Parser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */