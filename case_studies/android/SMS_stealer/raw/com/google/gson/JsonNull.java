package com.google.gson;

public final class JsonNull extends JsonElement {
  public static final JsonNull INSTANCE = new JsonNull();
  
  public JsonNull deepCopy() {
    return INSTANCE;
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject || paramObject instanceof JsonNull);
  }
  
  public int hashCode() {
    return JsonNull.class.hashCode();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\JsonNull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */