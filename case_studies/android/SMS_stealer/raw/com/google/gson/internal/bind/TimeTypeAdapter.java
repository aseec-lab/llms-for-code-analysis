package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class TimeTypeAdapter extends TypeAdapter<Time> {
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
        if (param1TypeToken.getRawType() == Time.class) {
          TimeTypeAdapter timeTypeAdapter = new TimeTypeAdapter();
        } else {
          param1Gson = null;
        } 
        return (TypeAdapter<T>)param1Gson;
      }
    };
  
  private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");
  
  public Time read(JsonReader paramJsonReader) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual peek : ()Lcom/google/gson/stream/JsonToken;
    //   6: getstatic com/google/gson/stream/JsonToken.NULL : Lcom/google/gson/stream/JsonToken;
    //   9: if_acmpne -> 20
    //   12: aload_1
    //   13: invokevirtual nextNull : ()V
    //   16: aload_0
    //   17: monitorexit
    //   18: aconst_null
    //   19: areturn
    //   20: new java/sql/Time
    //   23: dup
    //   24: aload_0
    //   25: getfield format : Ljava/text/DateFormat;
    //   28: aload_1
    //   29: invokevirtual nextString : ()Ljava/lang/String;
    //   32: invokevirtual parse : (Ljava/lang/String;)Ljava/util/Date;
    //   35: invokevirtual getTime : ()J
    //   38: invokespecial <init> : (J)V
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: areturn
    //   46: astore_1
    //   47: new com/google/gson/JsonSyntaxException
    //   50: astore_2
    //   51: aload_2
    //   52: aload_1
    //   53: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   56: aload_2
    //   57: athrow
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	58	finally
    //   20	42	46	java/text/ParseException
    //   20	42	58	finally
    //   47	58	58	finally
  }
  
  public void write(JsonWriter paramJsonWriter, Time paramTime) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: ifnonnull -> 11
    //   6: aconst_null
    //   7: astore_2
    //   8: goto -> 20
    //   11: aload_0
    //   12: getfield format : Ljava/text/DateFormat;
    //   15: aload_2
    //   16: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
    //   19: astore_2
    //   20: aload_1
    //   21: aload_2
    //   22: invokevirtual value : (Ljava/lang/String;)Lcom/google/gson/stream/JsonWriter;
    //   25: pop
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   11	20	29	finally
    //   20	26	29	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\TimeTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */