package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;

public final class DateTypeAdapter extends TypeAdapter<Date> {
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
        if (param1TypeToken.getRawType() == Date.class) {
          DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
        } else {
          param1Gson = null;
        } 
        return (TypeAdapter<T>)param1Gson;
      }
    };
  
  private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);
  
  private final DateFormat localFormat = DateFormat.getDateTimeInstance(2, 2);
  
  private Date deserializeToDate(String paramString) {
    /* monitor enter ThisExpression{ObjectType{com/google/gson/internal/bind/DateTypeAdapter}} */
    try {
      Date date = this.localFormat.parse(paramString);
      /* monitor exit ThisExpression{ObjectType{com/google/gson/internal/bind/DateTypeAdapter}} */
      return date;
    } catch (ParseException parseException) {
      try {
        Date date = this.enUsFormat.parse(paramString);
        /* monitor exit ThisExpression{ObjectType{com/google/gson/internal/bind/DateTypeAdapter}} */
        return date;
      } catch (ParseException parseException1) {
        try {
          ParsePosition parsePosition = new ParsePosition();
          this(0);
          Date date = ISO8601Utils.parse(paramString, parsePosition);
          /* monitor exit ThisExpression{ObjectType{com/google/gson/internal/bind/DateTypeAdapter}} */
          return date;
        } catch (ParseException parseException2) {
          JsonSyntaxException jsonSyntaxException = new JsonSyntaxException();
          this(paramString, parseException2);
          throw jsonSyntaxException;
        } 
      } 
    } finally {}
    /* monitor exit ThisExpression{ObjectType{com/google/gson/internal/bind/DateTypeAdapter}} */
    throw paramString;
  }
  
  public Date read(JsonReader paramJsonReader) throws IOException {
    if (paramJsonReader.peek() == JsonToken.NULL) {
      paramJsonReader.nextNull();
      return null;
    } 
    return deserializeToDate(paramJsonReader.nextString());
  }
  
  public void write(JsonWriter paramJsonWriter, Date paramDate) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: ifnonnull -> 14
    //   6: aload_1
    //   7: invokevirtual nullValue : ()Lcom/google/gson/stream/JsonWriter;
    //   10: pop
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_1
    //   15: aload_0
    //   16: getfield enUsFormat : Ljava/text/DateFormat;
    //   19: aload_2
    //   20: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
    //   23: invokevirtual value : (Ljava/lang/String;)Lcom/google/gson/stream/JsonWriter;
    //   26: pop
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   6	11	30	finally
    //   14	27	30	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\DateTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */