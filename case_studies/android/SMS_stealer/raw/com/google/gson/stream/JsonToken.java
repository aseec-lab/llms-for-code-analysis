package com.google.gson.stream;

public enum JsonToken {
  BEGIN_ARRAY, BEGIN_OBJECT, BOOLEAN, END_ARRAY, END_DOCUMENT, END_OBJECT, NAME, NULL, NUMBER, STRING;
  
  private static final JsonToken[] $VALUES;
  
  static {
    BEGIN_OBJECT = new JsonToken("BEGIN_OBJECT", 2);
    END_OBJECT = new JsonToken("END_OBJECT", 3);
    NAME = new JsonToken("NAME", 4);
    STRING = new JsonToken("STRING", 5);
    NUMBER = new JsonToken("NUMBER", 6);
    BOOLEAN = new JsonToken("BOOLEAN", 7);
    NULL = new JsonToken("NULL", 8);
    JsonToken jsonToken = new JsonToken("END_DOCUMENT", 9);
    END_DOCUMENT = jsonToken;
    $VALUES = new JsonToken[] { BEGIN_ARRAY, END_ARRAY, BEGIN_OBJECT, END_OBJECT, NAME, STRING, NUMBER, BOOLEAN, NULL, jsonToken };
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\stream\JsonToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */