package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.math.BigDecimal;

public final class LazilyParsedNumber extends Number {
  private final String value;
  
  public LazilyParsedNumber(String paramString) {
    this.value = paramString;
  }
  
  private Object writeReplace() throws ObjectStreamException {
    return new BigDecimal(this.value);
  }
  
  public double doubleValue() {
    return Double.parseDouble(this.value);
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (paramObject instanceof LazilyParsedNumber) {
      LazilyParsedNumber lazilyParsedNumber = (LazilyParsedNumber)paramObject;
      paramObject = this.value;
      String str = lazilyParsedNumber.value;
      boolean bool1 = bool;
      if (paramObject != str)
        if (paramObject.equals(str)) {
          bool1 = bool;
        } else {
          bool1 = false;
        }  
      return bool1;
    } 
    return false;
  }
  
  public float floatValue() {
    return Float.parseFloat(this.value);
  }
  
  public int hashCode() {
    return this.value.hashCode();
  }
  
  public int intValue() {
    try {
      return Integer.parseInt(this.value);
    } catch (NumberFormatException numberFormatException) {
      try {
        long l = Long.parseLong(this.value);
        return (int)l;
      } catch (NumberFormatException numberFormatException1) {
        return (new BigDecimal(this.value)).intValue();
      } 
    } 
  }
  
  public long longValue() {
    try {
      return Long.parseLong(this.value);
    } catch (NumberFormatException numberFormatException) {
      return (new BigDecimal(this.value)).longValue();
    } 
  }
  
  public String toString() {
    return this.value;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\LazilyParsedNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */