package com.androidnetworking.common;

public enum ResponseType {
  BITMAP, JSON_ARRAY, JSON_OBJECT, OK_HTTP_RESPONSE, PARSED, PREFETCH, STRING;
  
  private static final ResponseType[] $VALUES;
  
  static {
    JSON_OBJECT = new ResponseType("JSON_OBJECT", 1);
    JSON_ARRAY = new ResponseType("JSON_ARRAY", 2);
    OK_HTTP_RESPONSE = new ResponseType("OK_HTTP_RESPONSE", 3);
    BITMAP = new ResponseType("BITMAP", 4);
    PREFETCH = new ResponseType("PREFETCH", 5);
    ResponseType responseType = new ResponseType("PARSED", 6);
    PARSED = responseType;
    $VALUES = new ResponseType[] { STRING, JSON_OBJECT, JSON_ARRAY, OK_HTTP_RESPONSE, BITMAP, PREFETCH, responseType };
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\common\ResponseType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */