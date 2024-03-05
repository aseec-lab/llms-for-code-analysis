package com.google.gson;

import com.google.gson.internal.;
import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class JsonPrimitive extends JsonElement {
  private static final Class<?>[] PRIMITIVE_TYPES = new Class[] { 
      int.class, long.class, short.class, float.class, double.class, byte.class, boolean.class, char.class, Integer.class, Long.class, 
      Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class };
  
  private Object value;
  
  public JsonPrimitive(Boolean paramBoolean) {
    setValue(paramBoolean);
  }
  
  public JsonPrimitive(Character paramCharacter) {
    setValue(paramCharacter);
  }
  
  public JsonPrimitive(Number paramNumber) {
    setValue(paramNumber);
  }
  
  JsonPrimitive(Object paramObject) {
    setValue(paramObject);
  }
  
  public JsonPrimitive(String paramString) {
    setValue(paramString);
  }
  
  private static boolean isIntegral(JsonPrimitive paramJsonPrimitive) {
    Object object = paramJsonPrimitive.value;
    boolean bool1 = object instanceof Number;
    boolean bool = false;
    null = bool;
    if (bool1) {
      object = object;
      if (!(object instanceof BigInteger) && !(object instanceof Long) && !(object instanceof Integer) && !(object instanceof Short)) {
        null = bool;
        return (object instanceof Byte) ? true : null;
      } 
    } else {
      return null;
    } 
    return true;
  }
  
  private static boolean isPrimitiveOrString(Object<?> paramObject) {
    if (paramObject instanceof String)
      return true; 
    paramObject = (Object<?>)paramObject.getClass();
    Class<?>[] arrayOfClass = PRIMITIVE_TYPES;
    int i = arrayOfClass.length;
    for (byte b = 0; b < i; b++) {
      if (arrayOfClass[b].isAssignableFrom((Class<?>)paramObject))
        return true; 
    } 
    return false;
  }
  
  public JsonPrimitive deepCopy() {
    return this;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    if (this.value == null) {
      if (((JsonPrimitive)paramObject).value == null) {
        bool1 = bool3;
      } else {
        bool1 = false;
      } 
      return bool1;
    } 
    if (isIntegral(this) && isIntegral((JsonPrimitive)paramObject)) {
      if (getAsNumber().longValue() != paramObject.getAsNumber().longValue())
        bool1 = false; 
      return bool1;
    } 
    if (this.value instanceof Number && ((JsonPrimitive)paramObject).value instanceof Number) {
      double d2 = getAsNumber().doubleValue();
      double d1 = paramObject.getAsNumber().doubleValue();
      bool1 = bool2;
      if (d2 != d1)
        if (Double.isNaN(d2) && Double.isNaN(d1)) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }  
      return bool1;
    } 
    return this.value.equals(((JsonPrimitive)paramObject).value);
  }
  
  public BigDecimal getAsBigDecimal() {
    Object object = this.value;
    if (object instanceof BigDecimal) {
      object = object;
    } else {
      object = new BigDecimal(this.value.toString());
    } 
    return (BigDecimal)object;
  }
  
  public BigInteger getAsBigInteger() {
    Object object = this.value;
    if (object instanceof BigInteger) {
      object = object;
    } else {
      object = new BigInteger(this.value.toString());
    } 
    return (BigInteger)object;
  }
  
  public boolean getAsBoolean() {
    return isBoolean() ? getAsBooleanWrapper().booleanValue() : Boolean.parseBoolean(getAsString());
  }
  
  Boolean getAsBooleanWrapper() {
    return (Boolean)this.value;
  }
  
  public byte getAsByte() {
    byte b;
    if (isNumber()) {
      b = getAsNumber().byteValue();
    } else {
      b = Byte.parseByte(getAsString());
    } 
    return b;
  }
  
  public char getAsCharacter() {
    return getAsString().charAt(0);
  }
  
  public double getAsDouble() {
    double d;
    if (isNumber()) {
      d = getAsNumber().doubleValue();
    } else {
      d = Double.parseDouble(getAsString());
    } 
    return d;
  }
  
  public float getAsFloat() {
    float f;
    if (isNumber()) {
      f = getAsNumber().floatValue();
    } else {
      f = Float.parseFloat(getAsString());
    } 
    return f;
  }
  
  public int getAsInt() {
    int i;
    if (isNumber()) {
      i = getAsNumber().intValue();
    } else {
      i = Integer.parseInt(getAsString());
    } 
    return i;
  }
  
  public long getAsLong() {
    long l;
    if (isNumber()) {
      l = getAsNumber().longValue();
    } else {
      l = Long.parseLong(getAsString());
    } 
    return l;
  }
  
  public Number getAsNumber() {
    Object object = this.value;
    if (object instanceof String) {
      object = new LazilyParsedNumber((String)this.value);
    } else {
      object = object;
    } 
    return (Number)object;
  }
  
  public short getAsShort() {
    short s;
    if (isNumber()) {
      s = getAsNumber().shortValue();
    } else {
      s = Short.parseShort(getAsString());
    } 
    return s;
  }
  
  public String getAsString() {
    return isNumber() ? getAsNumber().toString() : (isBoolean() ? getAsBooleanWrapper().toString() : (String)this.value);
  }
  
  public int hashCode() {
    if (this.value == null)
      return 31; 
    if (isIntegral(this)) {
      long l = getAsNumber().longValue();
      return (int)(l >>> 32L ^ l);
    } 
    Object object = this.value;
    if (object instanceof Number) {
      long l = Double.doubleToLongBits(getAsNumber().doubleValue());
      return (int)(l >>> 32L ^ l);
    } 
    return object.hashCode();
  }
  
  public boolean isBoolean() {
    return this.value instanceof Boolean;
  }
  
  public boolean isNumber() {
    return this.value instanceof Number;
  }
  
  public boolean isString() {
    return this.value instanceof String;
  }
  
  void setValue(Object paramObject) {
    if (paramObject instanceof Character) {
      this.value = String.valueOf(((Character)paramObject).charValue());
    } else {
      boolean bool;
      if (paramObject instanceof Number || isPrimitiveOrString(paramObject)) {
        bool = true;
      } else {
        bool = false;
      } 
      .Gson.Preconditions.checkArgument(bool);
      this.value = paramObject;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\JsonPrimitive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */