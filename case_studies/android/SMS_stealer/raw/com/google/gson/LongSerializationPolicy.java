package com.google.gson;

public enum LongSerializationPolicy {
  DEFAULT {
    public JsonElement serialize(Long param1Long) {
      return new JsonPrimitive(param1Long);
    }
  },
  STRING;
  
  private static final LongSerializationPolicy[] $VALUES;
  
  static {
    null  = new null("STRING", 1);
    STRING = ;
    $VALUES = new LongSerializationPolicy[] { DEFAULT,  };
  }
  
  public abstract JsonElement serialize(Long paramLong);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\LongSerializationPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */