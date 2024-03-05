package com.google.gson;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class DefaultDateTypeAdapter extends TypeAdapter<Date> {
  private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
  
  private final Class<? extends Date> dateType;
  
  private final DateFormat enUsFormat;
  
  private final DateFormat localFormat;
  
  public DefaultDateTypeAdapter(int paramInt1, int paramInt2) {
    this(Date.class, DateFormat.getDateTimeInstance(paramInt1, paramInt2, Locale.US), DateFormat.getDateTimeInstance(paramInt1, paramInt2));
  }
  
  DefaultDateTypeAdapter(Class<? extends Date> paramClass) {
    this(paramClass, DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
  }
  
  DefaultDateTypeAdapter(Class<? extends Date> paramClass, int paramInt) {
    this(paramClass, DateFormat.getDateInstance(paramInt, Locale.US), DateFormat.getDateInstance(paramInt));
  }
  
  public DefaultDateTypeAdapter(Class<? extends Date> paramClass, int paramInt1, int paramInt2) {
    this(paramClass, DateFormat.getDateTimeInstance(paramInt1, paramInt2, Locale.US), DateFormat.getDateTimeInstance(paramInt1, paramInt2));
  }
  
  DefaultDateTypeAdapter(Class<? extends Date> paramClass, String paramString) {
    this(paramClass, new SimpleDateFormat(paramString, Locale.US), new SimpleDateFormat(paramString));
  }
  
  DefaultDateTypeAdapter(Class<? extends Date> paramClass, DateFormat paramDateFormat1, DateFormat paramDateFormat2) {
    if (paramClass == Date.class || paramClass == Date.class || paramClass == Timestamp.class) {
      this.dateType = paramClass;
      this.enUsFormat = paramDateFormat1;
      this.localFormat = paramDateFormat2;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Date type must be one of ");
    stringBuilder.append(Date.class);
    stringBuilder.append(", ");
    stringBuilder.append(Timestamp.class);
    stringBuilder.append(", or ");
    stringBuilder.append(Date.class);
    stringBuilder.append(" but was ");
    stringBuilder.append(paramClass);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private Date deserializeToDate(String paramString) {
    DateFormat dateFormat = this.localFormat;
    /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
    try {
      Date date = this.localFormat.parse(paramString);
      /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
      return date;
    } catch (ParseException parseException) {
      try {
        Date date = this.enUsFormat.parse(paramString);
        /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
        return date;
      } catch (ParseException parseException1) {
        try {
          ParsePosition parsePosition = new ParsePosition();
          this(0);
          Date date = ISO8601Utils.parse(paramString, parsePosition);
          /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
          return date;
        } catch (ParseException parseException2) {
          JsonSyntaxException jsonSyntaxException = new JsonSyntaxException();
          this(paramString, parseException2);
          throw jsonSyntaxException;
        } 
      } 
    } finally {}
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
    throw paramString;
  }
  
  public Date read(JsonReader paramJsonReader) throws IOException {
    if (paramJsonReader.peek() == JsonToken.NULL) {
      paramJsonReader.nextNull();
      return null;
    } 
    Date date = deserializeToDate(paramJsonReader.nextString());
    Class<? extends Date> clazz = this.dateType;
    if (clazz == Date.class)
      return date; 
    if (clazz == Timestamp.class)
      return new Timestamp(date.getTime()); 
    if (clazz == Date.class)
      return new Date(date.getTime()); 
    throw new AssertionError();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("DefaultDateTypeAdapter");
    stringBuilder.append('(');
    stringBuilder.append(this.localFormat.getClass().getSimpleName());
    stringBuilder.append(')');
    return stringBuilder.toString();
  }
  
  public void write(JsonWriter paramJsonWriter, Date paramDate) throws IOException {
    if (paramDate == null) {
      paramJsonWriter.nullValue();
      return;
    } 
    synchronized (this.localFormat) {
      paramJsonWriter.value(this.enUsFormat.format(paramDate));
      return;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\DefaultDateTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */