package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters {
  public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN;
  
  public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
  
  public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER;
  
  public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY;
  
  public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
  
  public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
  
  public static final TypeAdapter<BigDecimal> BIG_DECIMAL;
  
  public static final TypeAdapter<BigInteger> BIG_INTEGER;
  
  public static final TypeAdapter<BitSet> BIT_SET;
  
  public static final TypeAdapterFactory BIT_SET_FACTORY;
  
  public static final TypeAdapter<Boolean> BOOLEAN;
  
  public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING;
  
  public static final TypeAdapterFactory BOOLEAN_FACTORY;
  
  public static final TypeAdapter<Number> BYTE;
  
  public static final TypeAdapterFactory BYTE_FACTORY;
  
  public static final TypeAdapter<Calendar> CALENDAR;
  
  public static final TypeAdapterFactory CALENDAR_FACTORY;
  
  public static final TypeAdapter<Character> CHARACTER;
  
  public static final TypeAdapterFactory CHARACTER_FACTORY;
  
  public static final TypeAdapter<Class> CLASS;
  
  public static final TypeAdapterFactory CLASS_FACTORY;
  
  public static final TypeAdapter<Currency> CURRENCY;
  
  public static final TypeAdapterFactory CURRENCY_FACTORY;
  
  public static final TypeAdapter<Number> DOUBLE;
  
  public static final TypeAdapterFactory ENUM_FACTORY;
  
  public static final TypeAdapter<Number> FLOAT;
  
  public static final TypeAdapter<InetAddress> INET_ADDRESS;
  
  public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
  
  public static final TypeAdapter<Number> INTEGER;
  
  public static final TypeAdapterFactory INTEGER_FACTORY;
  
  public static final TypeAdapter<JsonElement> JSON_ELEMENT;
  
  public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
  
  public static final TypeAdapter<Locale> LOCALE;
  
  public static final TypeAdapterFactory LOCALE_FACTORY;
  
  public static final TypeAdapter<Number> LONG;
  
  public static final TypeAdapter<Number> NUMBER;
  
  public static final TypeAdapterFactory NUMBER_FACTORY;
  
  public static final TypeAdapter<Number> SHORT;
  
  public static final TypeAdapterFactory SHORT_FACTORY;
  
  public static final TypeAdapter<String> STRING;
  
  public static final TypeAdapter<StringBuffer> STRING_BUFFER;
  
  public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
  
  public static final TypeAdapter<StringBuilder> STRING_BUILDER;
  
  public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
  
  public static final TypeAdapterFactory STRING_FACTORY;
  
  public static final TypeAdapterFactory TIMESTAMP_FACTORY;
  
  public static final TypeAdapter<URI> URI;
  
  public static final TypeAdapterFactory URI_FACTORY;
  
  public static final TypeAdapter<URL> URL;
  
  public static final TypeAdapterFactory URL_FACTORY;
  
  public static final TypeAdapter<UUID> UUID;
  
  public static final TypeAdapterFactory UUID_FACTORY;
  
  static {
    TypeAdapter<Class> typeAdapter10 = (new TypeAdapter<Class>() {
        public Class read(JsonReader param1JsonReader) throws IOException {
          throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
        
        public void write(JsonWriter param1JsonWriter, Class param1Class) throws IOException {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Attempted to serialize java.lang.Class: ");
          stringBuilder.append(param1Class.getName());
          stringBuilder.append(". Forgot to register a type adapter?");
          throw new UnsupportedOperationException(stringBuilder.toString());
        }
      }).nullSafe();
    CLASS = typeAdapter10;
    CLASS_FACTORY = newFactory(Class.class, typeAdapter10);
    typeAdapter10 = (new TypeAdapter<BitSet>() {
        public BitSet read(JsonReader param1JsonReader) throws IOException {
          // Byte code:
          //   0: new java/util/BitSet
          //   3: dup
          //   4: invokespecial <init> : ()V
          //   7: astore #6
          //   9: aload_1
          //   10: invokevirtual beginArray : ()V
          //   13: aload_1
          //   14: invokevirtual peek : ()Lcom/google/gson/stream/JsonToken;
          //   17: astore #5
          //   19: iconst_0
          //   20: istore_2
          //   21: aload #5
          //   23: getstatic com/google/gson/stream/JsonToken.END_ARRAY : Lcom/google/gson/stream/JsonToken;
          //   26: if_acmpeq -> 190
          //   29: getstatic com/google/gson/internal/bind/TypeAdapters$36.$SwitchMap$com$google$gson$stream$JsonToken : [I
          //   32: aload #5
          //   34: invokevirtual ordinal : ()I
          //   37: iaload
          //   38: istore_3
          //   39: iconst_1
          //   40: istore #4
          //   42: iload_3
          //   43: iconst_1
          //   44: if_icmpeq -> 160
          //   47: iload_3
          //   48: iconst_2
          //   49: if_icmpeq -> 151
          //   52: iload_3
          //   53: iconst_3
          //   54: if_icmpne -> 117
          //   57: aload_1
          //   58: invokevirtual nextString : ()Ljava/lang/String;
          //   61: astore #5
          //   63: aload #5
          //   65: invokestatic parseInt : (Ljava/lang/String;)I
          //   68: istore_3
          //   69: iload_3
          //   70: ifeq -> 76
          //   73: goto -> 167
          //   76: iconst_0
          //   77: istore #4
          //   79: goto -> 167
          //   82: astore_1
          //   83: new java/lang/StringBuilder
          //   86: dup
          //   87: invokespecial <init> : ()V
          //   90: astore_1
          //   91: aload_1
          //   92: ldc 'Error: Expecting: bitset number value (1, 0), Found: '
          //   94: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   97: pop
          //   98: aload_1
          //   99: aload #5
          //   101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   104: pop
          //   105: new com/google/gson/JsonSyntaxException
          //   108: dup
          //   109: aload_1
          //   110: invokevirtual toString : ()Ljava/lang/String;
          //   113: invokespecial <init> : (Ljava/lang/String;)V
          //   116: athrow
          //   117: new java/lang/StringBuilder
          //   120: dup
          //   121: invokespecial <init> : ()V
          //   124: astore_1
          //   125: aload_1
          //   126: ldc 'Invalid bitset value type: '
          //   128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   131: pop
          //   132: aload_1
          //   133: aload #5
          //   135: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
          //   138: pop
          //   139: new com/google/gson/JsonSyntaxException
          //   142: dup
          //   143: aload_1
          //   144: invokevirtual toString : ()Ljava/lang/String;
          //   147: invokespecial <init> : (Ljava/lang/String;)V
          //   150: athrow
          //   151: aload_1
          //   152: invokevirtual nextBoolean : ()Z
          //   155: istore #4
          //   157: goto -> 167
          //   160: aload_1
          //   161: invokevirtual nextInt : ()I
          //   164: ifeq -> 76
          //   167: iload #4
          //   169: ifeq -> 178
          //   172: aload #6
          //   174: iload_2
          //   175: invokevirtual set : (I)V
          //   178: iinc #2, 1
          //   181: aload_1
          //   182: invokevirtual peek : ()Lcom/google/gson/stream/JsonToken;
          //   185: astore #5
          //   187: goto -> 21
          //   190: aload_1
          //   191: invokevirtual endArray : ()V
          //   194: aload #6
          //   196: areturn
          // Exception table:
          //   from	to	target	type
          //   63	69	82	java/lang/NumberFormatException
        }
        
        public void write(JsonWriter param1JsonWriter, BitSet param1BitSet) throws IOException {
          param1JsonWriter.beginArray();
          int i = param1BitSet.length();
          for (byte b = 0; b < i; b++)
            param1JsonWriter.value(param1BitSet.get(b)); 
          param1JsonWriter.endArray();
        }
      }).nullSafe();
    BIT_SET = (TypeAdapter)typeAdapter10;
    BIT_SET_FACTORY = newFactory(BitSet.class, typeAdapter10);
    BOOLEAN = new TypeAdapter<Boolean>() {
        public Boolean read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return (param1JsonReader.peek() == JsonToken.STRING) ? Boolean.valueOf(Boolean.parseBoolean(param1JsonReader.nextString())) : Boolean.valueOf(param1JsonReader.nextBoolean());
        }
        
        public void write(JsonWriter param1JsonWriter, Boolean param1Boolean) throws IOException {
          param1JsonWriter.value(param1Boolean);
        }
      };
    BOOLEAN_AS_STRING = new TypeAdapter<Boolean>() {
        public Boolean read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return Boolean.valueOf(param1JsonReader.nextString());
        }
        
        public void write(JsonWriter param1JsonWriter, Boolean param1Boolean) throws IOException {
          String str;
          if (param1Boolean == null) {
            str = "null";
          } else {
            str = str.toString();
          } 
          param1JsonWriter.value(str);
        }
      };
    BOOLEAN_FACTORY = newFactory(boolean.class, (Class)Boolean.class, (TypeAdapter)BOOLEAN);
    BYTE = new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          try {
            byte b = (byte)param1JsonReader.nextInt();
            return Byte.valueOf(b);
          } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          param1JsonWriter.value(param1Number);
        }
      };
    BYTE_FACTORY = newFactory(byte.class, (Class)Byte.class, (TypeAdapter)BYTE);
    SHORT = new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          try {
            short s = (short)param1JsonReader.nextInt();
            return Short.valueOf(s);
          } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          param1JsonWriter.value(param1Number);
        }
      };
    SHORT_FACTORY = newFactory(short.class, (Class)Short.class, (TypeAdapter)SHORT);
    INTEGER = new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          try {
            int i = param1JsonReader.nextInt();
            return Integer.valueOf(i);
          } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          param1JsonWriter.value(param1Number);
        }
      };
    INTEGER_FACTORY = newFactory(int.class, (Class)Integer.class, (TypeAdapter)INTEGER);
    typeAdapter10 = (new TypeAdapter<AtomicInteger>() {
        public AtomicInteger read(JsonReader param1JsonReader) throws IOException {
          try {
            return new AtomicInteger(param1JsonReader.nextInt());
          } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, AtomicInteger param1AtomicInteger) throws IOException {
          param1JsonWriter.value(param1AtomicInteger.get());
        }
      }).nullSafe();
    ATOMIC_INTEGER = (TypeAdapter)typeAdapter10;
    ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, typeAdapter10);
    typeAdapter10 = (new TypeAdapter<AtomicBoolean>() {
        public AtomicBoolean read(JsonReader param1JsonReader) throws IOException {
          return new AtomicBoolean(param1JsonReader.nextBoolean());
        }
        
        public void write(JsonWriter param1JsonWriter, AtomicBoolean param1AtomicBoolean) throws IOException {
          param1JsonWriter.value(param1AtomicBoolean.get());
        }
      }).nullSafe();
    ATOMIC_BOOLEAN = (TypeAdapter)typeAdapter10;
    ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, typeAdapter10);
    typeAdapter10 = (new TypeAdapter<AtomicIntegerArray>() {
        public AtomicIntegerArray read(JsonReader param1JsonReader) throws IOException {
          ArrayList<Integer> arrayList = new ArrayList();
          param1JsonReader.beginArray();
          while (param1JsonReader.hasNext()) {
            try {
              arrayList.add(Integer.valueOf(param1JsonReader.nextInt()));
            } catch (NumberFormatException numberFormatException) {
              throw new JsonSyntaxException(numberFormatException);
            } 
          } 
          numberFormatException.endArray();
          int i = arrayList.size();
          AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(i);
          for (byte b = 0; b < i; b++)
            atomicIntegerArray.set(b, ((Integer)arrayList.get(b)).intValue()); 
          return atomicIntegerArray;
        }
        
        public void write(JsonWriter param1JsonWriter, AtomicIntegerArray param1AtomicIntegerArray) throws IOException {
          param1JsonWriter.beginArray();
          int i = param1AtomicIntegerArray.length();
          for (byte b = 0; b < i; b++)
            param1JsonWriter.value(param1AtomicIntegerArray.get(b)); 
          param1JsonWriter.endArray();
        }
      }).nullSafe();
    ATOMIC_INTEGER_ARRAY = (TypeAdapter)typeAdapter10;
    ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, typeAdapter10);
    LONG = new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          try {
            long l = param1JsonReader.nextLong();
            return Long.valueOf(l);
          } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          param1JsonWriter.value(param1Number);
        }
      };
    FLOAT = new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return Float.valueOf((float)param1JsonReader.nextDouble());
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          param1JsonWriter.value(param1Number);
        }
      };
    DOUBLE = new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return Double.valueOf(param1JsonReader.nextDouble());
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          param1JsonWriter.value(param1Number);
        }
      };
    TypeAdapter<Number> typeAdapter9 = new TypeAdapter<Number>() {
        public Number read(JsonReader param1JsonReader) throws IOException {
          StringBuilder stringBuilder;
          JsonToken jsonToken = param1JsonReader.peek();
          int i = TypeAdapters.null.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()];
          if (i != 1 && i != 3) {
            if (i == 4) {
              param1JsonReader.nextNull();
              return null;
            } 
            stringBuilder = new StringBuilder();
            stringBuilder.append("Expecting number, got: ");
            stringBuilder.append(jsonToken);
            throw new JsonSyntaxException(stringBuilder.toString());
          } 
          return (Number)new LazilyParsedNumber(stringBuilder.nextString());
        }
        
        public void write(JsonWriter param1JsonWriter, Number param1Number) throws IOException {
          param1JsonWriter.value(param1Number);
        }
      };
    NUMBER = typeAdapter9;
    NUMBER_FACTORY = newFactory(Number.class, typeAdapter9);
    CHARACTER = new TypeAdapter<Character>() {
        public Character read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          String str = param1JsonReader.nextString();
          if (str.length() == 1)
            return Character.valueOf(str.charAt(0)); 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Expecting character, got: ");
          stringBuilder.append(str);
          throw new JsonSyntaxException(stringBuilder.toString());
        }
        
        public void write(JsonWriter param1JsonWriter, Character param1Character) throws IOException {
          String str;
          if (param1Character == null) {
            param1Character = null;
          } else {
            str = String.valueOf(param1Character);
          } 
          param1JsonWriter.value(str);
        }
      };
    CHARACTER_FACTORY = newFactory(char.class, (Class)Character.class, (TypeAdapter)CHARACTER);
    STRING = new TypeAdapter<String>() {
        public String read(JsonReader param1JsonReader) throws IOException {
          JsonToken jsonToken = param1JsonReader.peek();
          if (jsonToken == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return (jsonToken == JsonToken.BOOLEAN) ? Boolean.toString(param1JsonReader.nextBoolean()) : param1JsonReader.nextString();
        }
        
        public void write(JsonWriter param1JsonWriter, String param1String) throws IOException {
          param1JsonWriter.value(param1String);
        }
      };
    BIG_DECIMAL = new TypeAdapter<BigDecimal>() {
        public BigDecimal read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          try {
            return new BigDecimal(param1JsonReader.nextString());
          } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, BigDecimal param1BigDecimal) throws IOException {
          param1JsonWriter.value(param1BigDecimal);
        }
      };
    BIG_INTEGER = new TypeAdapter<BigInteger>() {
        public BigInteger read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          try {
            return new BigInteger(param1JsonReader.nextString());
          } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, BigInteger param1BigInteger) throws IOException {
          param1JsonWriter.value(param1BigInteger);
        }
      };
    STRING_FACTORY = newFactory(String.class, STRING);
    TypeAdapter<StringBuilder> typeAdapter8 = new TypeAdapter<StringBuilder>() {
        public StringBuilder read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return new StringBuilder(param1JsonReader.nextString());
        }
        
        public void write(JsonWriter param1JsonWriter, StringBuilder param1StringBuilder) throws IOException {
          String str;
          if (param1StringBuilder == null) {
            param1StringBuilder = null;
          } else {
            str = param1StringBuilder.toString();
          } 
          param1JsonWriter.value(str);
        }
      };
    STRING_BUILDER = typeAdapter8;
    STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, typeAdapter8);
    TypeAdapter<StringBuffer> typeAdapter7 = new TypeAdapter<StringBuffer>() {
        public StringBuffer read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return new StringBuffer(param1JsonReader.nextString());
        }
        
        public void write(JsonWriter param1JsonWriter, StringBuffer param1StringBuffer) throws IOException {
          String str;
          if (param1StringBuffer == null) {
            param1StringBuffer = null;
          } else {
            str = param1StringBuffer.toString();
          } 
          param1JsonWriter.value(str);
        }
      };
    STRING_BUFFER = typeAdapter7;
    STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, typeAdapter7);
    TypeAdapter<URL> typeAdapter6 = new TypeAdapter<URL>() {
        public URL read(JsonReader param1JsonReader) throws IOException {
          URL uRL;
          JsonToken jsonToken1 = param1JsonReader.peek();
          JsonToken jsonToken2 = JsonToken.NULL;
          String str2 = null;
          if (jsonToken1 == jsonToken2) {
            param1JsonReader.nextNull();
            return null;
          } 
          String str1 = param1JsonReader.nextString();
          if ("null".equals(str1)) {
            str1 = str2;
          } else {
            uRL = new URL(str1);
          } 
          return uRL;
        }
        
        public void write(JsonWriter param1JsonWriter, URL param1URL) throws IOException {
          String str;
          if (param1URL == null) {
            param1URL = null;
          } else {
            str = param1URL.toExternalForm();
          } 
          param1JsonWriter.value(str);
        }
      };
    URL = typeAdapter6;
    URL_FACTORY = newFactory(URL.class, typeAdapter6);
    TypeAdapter<URI> typeAdapter5 = new TypeAdapter<URI>() {
        public URI read(JsonReader param1JsonReader) throws IOException {
          JsonToken jsonToken1 = param1JsonReader.peek();
          JsonToken jsonToken2 = JsonToken.NULL;
          String str = null;
          if (jsonToken1 == jsonToken2) {
            param1JsonReader.nextNull();
            return null;
          } 
          try {
            URI uRI;
            String str1 = param1JsonReader.nextString();
            if ("null".equals(str1)) {
              str1 = str;
            } else {
              uRI = new URI(str1);
            } 
            return uRI;
          } catch (URISyntaxException uRISyntaxException) {
            throw new JsonIOException(uRISyntaxException);
          } 
        }
        
        public void write(JsonWriter param1JsonWriter, URI param1URI) throws IOException {
          String str;
          if (param1URI == null) {
            param1URI = null;
          } else {
            str = param1URI.toASCIIString();
          } 
          param1JsonWriter.value(str);
        }
      };
    URI = typeAdapter5;
    URI_FACTORY = newFactory(URI.class, typeAdapter5);
    TypeAdapter<InetAddress> typeAdapter4 = new TypeAdapter<InetAddress>() {
        public InetAddress read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return InetAddress.getByName(param1JsonReader.nextString());
        }
        
        public void write(JsonWriter param1JsonWriter, InetAddress param1InetAddress) throws IOException {
          String str;
          if (param1InetAddress == null) {
            param1InetAddress = null;
          } else {
            str = param1InetAddress.getHostAddress();
          } 
          param1JsonWriter.value(str);
        }
      };
    INET_ADDRESS = typeAdapter4;
    INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, typeAdapter4);
    TypeAdapter<UUID> typeAdapter3 = new TypeAdapter<UUID>() {
        public UUID read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          return UUID.fromString(param1JsonReader.nextString());
        }
        
        public void write(JsonWriter param1JsonWriter, UUID param1UUID) throws IOException {
          String str;
          if (param1UUID == null) {
            param1UUID = null;
          } else {
            str = param1UUID.toString();
          } 
          param1JsonWriter.value(str);
        }
      };
    UUID = typeAdapter3;
    UUID_FACTORY = newFactory(UUID.class, typeAdapter3);
    typeAdapter3 = (new TypeAdapter<Currency>() {
        public Currency read(JsonReader param1JsonReader) throws IOException {
          return Currency.getInstance(param1JsonReader.nextString());
        }
        
        public void write(JsonWriter param1JsonWriter, Currency param1Currency) throws IOException {
          param1JsonWriter.value(param1Currency.getCurrencyCode());
        }
      }).nullSafe();
    CURRENCY = (TypeAdapter)typeAdapter3;
    CURRENCY_FACTORY = newFactory(Currency.class, typeAdapter3);
    TIMESTAMP_FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
          return (TypeAdapter)((param1TypeToken.getRawType() != Timestamp.class) ? null : new TypeAdapter<Timestamp>() {
              final TypeAdapters.null this$0;
              
              final TypeAdapter val$dateTypeAdapter;
              
              public Timestamp read(JsonReader param2JsonReader) throws IOException {
                Date date = (Date)dateTypeAdapter.read(param2JsonReader);
                if (date != null) {
                  date = new Timestamp(date.getTime());
                } else {
                  date = null;
                } 
                return (Timestamp)date;
              }
              
              public void write(JsonWriter param2JsonWriter, Timestamp param2Timestamp) throws IOException {
                dateTypeAdapter.write(param2JsonWriter, param2Timestamp);
              }
            });
        }
      };
    TypeAdapter<Calendar> typeAdapter2 = new TypeAdapter<Calendar>() {
        private static final String DAY_OF_MONTH = "dayOfMonth";
        
        private static final String HOUR_OF_DAY = "hourOfDay";
        
        private static final String MINUTE = "minute";
        
        private static final String MONTH = "month";
        
        private static final String SECOND = "second";
        
        private static final String YEAR = "year";
        
        public Calendar read(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader.peek() == JsonToken.NULL) {
            param1JsonReader.nextNull();
            return null;
          } 
          param1JsonReader.beginObject();
          int i1 = 0;
          int n = 0;
          int m = 0;
          int k = 0;
          int j = 0;
          int i = 0;
          while (param1JsonReader.peek() != JsonToken.END_OBJECT) {
            String str = param1JsonReader.nextName();
            int i2 = param1JsonReader.nextInt();
            if ("year".equals(str)) {
              i1 = i2;
              continue;
            } 
            if ("month".equals(str)) {
              n = i2;
              continue;
            } 
            if ("dayOfMonth".equals(str)) {
              m = i2;
              continue;
            } 
            if ("hourOfDay".equals(str)) {
              k = i2;
              continue;
            } 
            if ("minute".equals(str)) {
              j = i2;
              continue;
            } 
            if ("second".equals(str))
              i = i2; 
          } 
          param1JsonReader.endObject();
          return new GregorianCalendar(i1, n, m, k, j, i);
        }
        
        public void write(JsonWriter param1JsonWriter, Calendar param1Calendar) throws IOException {
          if (param1Calendar == null) {
            param1JsonWriter.nullValue();
            return;
          } 
          param1JsonWriter.beginObject();
          param1JsonWriter.name("year");
          param1JsonWriter.value(param1Calendar.get(1));
          param1JsonWriter.name("month");
          param1JsonWriter.value(param1Calendar.get(2));
          param1JsonWriter.name("dayOfMonth");
          param1JsonWriter.value(param1Calendar.get(5));
          param1JsonWriter.name("hourOfDay");
          param1JsonWriter.value(param1Calendar.get(11));
          param1JsonWriter.name("minute");
          param1JsonWriter.value(param1Calendar.get(12));
          param1JsonWriter.name("second");
          param1JsonWriter.value(param1Calendar.get(13));
          param1JsonWriter.endObject();
        }
      };
    CALENDAR = typeAdapter2;
    CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, (Class)GregorianCalendar.class, typeAdapter2);
    TypeAdapter<Locale> typeAdapter1 = new TypeAdapter<Locale>() {
        public Locale read(JsonReader param1JsonReader) throws IOException {
          JsonToken jsonToken2 = param1JsonReader.peek();
          JsonToken jsonToken1 = JsonToken.NULL;
          String str = null;
          if (jsonToken2 == jsonToken1) {
            param1JsonReader.nextNull();
            return null;
          } 
          StringTokenizer stringTokenizer = new StringTokenizer(param1JsonReader.nextString(), "_");
          if (stringTokenizer.hasMoreElements()) {
            String str1 = stringTokenizer.nextToken();
          } else {
            param1JsonReader = null;
          } 
          if (stringTokenizer.hasMoreElements()) {
            String str1 = stringTokenizer.nextToken();
          } else {
            jsonToken1 = null;
          } 
          if (stringTokenizer.hasMoreElements())
            str = stringTokenizer.nextToken(); 
          return (jsonToken1 == null && str == null) ? new Locale((String)param1JsonReader) : ((str == null) ? new Locale((String)param1JsonReader, (String)jsonToken1) : new Locale((String)param1JsonReader, (String)jsonToken1, str));
        }
        
        public void write(JsonWriter param1JsonWriter, Locale param1Locale) throws IOException {
          String str;
          if (param1Locale == null) {
            param1Locale = null;
          } else {
            str = param1Locale.toString();
          } 
          param1JsonWriter.value(str);
        }
      };
    LOCALE = typeAdapter1;
    LOCALE_FACTORY = newFactory(Locale.class, typeAdapter1);
    TypeAdapter<JsonElement> typeAdapter = new TypeAdapter<JsonElement>() {
        public JsonElement read(JsonReader param1JsonReader) throws IOException {
          // Byte code:
          //   0: getstatic com/google/gson/internal/bind/TypeAdapters$36.$SwitchMap$com$google$gson$stream$JsonToken : [I
          //   3: aload_1
          //   4: invokevirtual peek : ()Lcom/google/gson/stream/JsonToken;
          //   7: invokevirtual ordinal : ()I
          //   10: iaload
          //   11: tableswitch default -> 48, 1 -> 169, 2 -> 154, 3 -> 142, 4 -> 134, 5 -> 97, 6 -> 56
          //   48: new java/lang/IllegalArgumentException
          //   51: dup
          //   52: invokespecial <init> : ()V
          //   55: athrow
          //   56: new com/google/gson/JsonObject
          //   59: dup
          //   60: invokespecial <init> : ()V
          //   63: astore_2
          //   64: aload_1
          //   65: invokevirtual beginObject : ()V
          //   68: aload_1
          //   69: invokevirtual hasNext : ()Z
          //   72: ifeq -> 91
          //   75: aload_2
          //   76: aload_1
          //   77: invokevirtual nextName : ()Ljava/lang/String;
          //   80: aload_0
          //   81: aload_1
          //   82: invokevirtual read : (Lcom/google/gson/stream/JsonReader;)Lcom/google/gson/JsonElement;
          //   85: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
          //   88: goto -> 68
          //   91: aload_1
          //   92: invokevirtual endObject : ()V
          //   95: aload_2
          //   96: areturn
          //   97: new com/google/gson/JsonArray
          //   100: dup
          //   101: invokespecial <init> : ()V
          //   104: astore_2
          //   105: aload_1
          //   106: invokevirtual beginArray : ()V
          //   109: aload_1
          //   110: invokevirtual hasNext : ()Z
          //   113: ifeq -> 128
          //   116: aload_2
          //   117: aload_0
          //   118: aload_1
          //   119: invokevirtual read : (Lcom/google/gson/stream/JsonReader;)Lcom/google/gson/JsonElement;
          //   122: invokevirtual add : (Lcom/google/gson/JsonElement;)V
          //   125: goto -> 109
          //   128: aload_1
          //   129: invokevirtual endArray : ()V
          //   132: aload_2
          //   133: areturn
          //   134: aload_1
          //   135: invokevirtual nextNull : ()V
          //   138: getstatic com/google/gson/JsonNull.INSTANCE : Lcom/google/gson/JsonNull;
          //   141: areturn
          //   142: new com/google/gson/JsonPrimitive
          //   145: dup
          //   146: aload_1
          //   147: invokevirtual nextString : ()Ljava/lang/String;
          //   150: invokespecial <init> : (Ljava/lang/String;)V
          //   153: areturn
          //   154: new com/google/gson/JsonPrimitive
          //   157: dup
          //   158: aload_1
          //   159: invokevirtual nextBoolean : ()Z
          //   162: invokestatic valueOf : (Z)Ljava/lang/Boolean;
          //   165: invokespecial <init> : (Ljava/lang/Boolean;)V
          //   168: areturn
          //   169: new com/google/gson/JsonPrimitive
          //   172: dup
          //   173: new com/google/gson/internal/LazilyParsedNumber
          //   176: dup
          //   177: aload_1
          //   178: invokevirtual nextString : ()Ljava/lang/String;
          //   181: invokespecial <init> : (Ljava/lang/String;)V
          //   184: invokespecial <init> : (Ljava/lang/Number;)V
          //   187: areturn
        }
        
        public void write(JsonWriter param1JsonWriter, JsonElement param1JsonElement) throws IOException {
          JsonPrimitive jsonPrimitive;
          if (param1JsonElement == null || param1JsonElement.isJsonNull()) {
            param1JsonWriter.nullValue();
            return;
          } 
          if (param1JsonElement.isJsonPrimitive()) {
            jsonPrimitive = param1JsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber()) {
              param1JsonWriter.value(jsonPrimitive.getAsNumber());
            } else if (jsonPrimitive.isBoolean()) {
              param1JsonWriter.value(jsonPrimitive.getAsBoolean());
            } else {
              param1JsonWriter.value(jsonPrimitive.getAsString());
            } 
          } else {
            Iterator<JsonElement> iterator;
            if (jsonPrimitive.isJsonArray()) {
              param1JsonWriter.beginArray();
              iterator = jsonPrimitive.getAsJsonArray().iterator();
              while (iterator.hasNext())
                write(param1JsonWriter, iterator.next()); 
              param1JsonWriter.endArray();
            } else if (iterator.isJsonObject()) {
              param1JsonWriter.beginObject();
              for (Map.Entry entry : iterator.getAsJsonObject().entrySet()) {
                param1JsonWriter.name((String)entry.getKey());
                write(param1JsonWriter, (JsonElement)entry.getValue());
              } 
              param1JsonWriter.endObject();
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Couldn't write ");
              stringBuilder.append(entry.getClass());
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
          } 
        }
      };
    JSON_ELEMENT = typeAdapter;
    JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, typeAdapter);
    ENUM_FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
          Class<?> clazz2 = param1TypeToken.getRawType();
          if (!Enum.class.isAssignableFrom(clazz2) || clazz2 == Enum.class)
            return null; 
          Class<?> clazz1 = clazz2;
          if (!clazz2.isEnum())
            clazz1 = clazz2.getSuperclass(); 
          return new TypeAdapters.EnumTypeAdapter(clazz1);
        }
      };
  }
  
  private TypeAdapters() {
    throw new UnsupportedOperationException();
  }
  
  public static <TT> TypeAdapterFactory newFactory(final TypeToken<TT> type, final TypeAdapter<TT> typeAdapter) {
    return new TypeAdapterFactory() {
        final TypeToken val$type;
        
        final TypeAdapter val$typeAdapter;
        
        public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
          if (param1TypeToken.equals(type)) {
            TypeAdapter typeAdapter = typeAdapter;
          } else {
            param1Gson = null;
          } 
          return (TypeAdapter<T>)param1Gson;
        }
      };
  }
  
  public static <TT> TypeAdapterFactory newFactory(final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
    return new TypeAdapterFactory() {
        final Class val$type;
        
        final TypeAdapter val$typeAdapter;
        
        public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
          if (param1TypeToken.getRawType() == type) {
            TypeAdapter typeAdapter = typeAdapter;
          } else {
            param1Gson = null;
          } 
          return (TypeAdapter<T>)param1Gson;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[type=");
          stringBuilder.append(type.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(typeAdapter);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  public static <TT> TypeAdapterFactory newFactory(final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
    return new TypeAdapterFactory() {
        final Class val$boxed;
        
        final TypeAdapter val$typeAdapter;
        
        final Class val$unboxed;
        
        public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
          null = param1TypeToken.getRawType();
          return (null == unboxed || null == boxed) ? typeAdapter : null;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[type=");
          stringBuilder.append(boxed.getName());
          stringBuilder.append("+");
          stringBuilder.append(unboxed.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(typeAdapter);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> base, final Class<? extends TT> sub, final TypeAdapter<? super TT> typeAdapter) {
    return new TypeAdapterFactory() {
        final Class val$base;
        
        final Class val$sub;
        
        final TypeAdapter val$typeAdapter;
        
        public <T> TypeAdapter<T> create(Gson param1Gson, TypeToken<T> param1TypeToken) {
          null = param1TypeToken.getRawType();
          return (null == base || null == sub) ? typeAdapter : null;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[type=");
          stringBuilder.append(base.getName());
          stringBuilder.append("+");
          stringBuilder.append(sub.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(typeAdapter);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  public static <T1> TypeAdapterFactory newTypeHierarchyFactory(final Class<T1> clazz, final TypeAdapter<T1> typeAdapter) {
    return new TypeAdapterFactory() {
        final Class val$clazz;
        
        final TypeAdapter val$typeAdapter;
        
        public <T2> TypeAdapter<T2> create(Gson param1Gson, TypeToken<T2> param1TypeToken) {
          final Class<?> requestedType = param1TypeToken.getRawType();
          return !clazz.isAssignableFrom(clazz) ? null : new TypeAdapter<T1>() {
              final TypeAdapters.null this$0;
              
              final Class val$requestedType;
              
              public T1 read(JsonReader param2JsonReader) throws IOException {
                Object object = typeAdapter.read(param2JsonReader);
                if (object == null || requestedType.isInstance(object))
                  return (T1)object; 
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected a ");
                stringBuilder.append(requestedType.getName());
                stringBuilder.append(" but was ");
                stringBuilder.append(object.getClass().getName());
                throw new JsonSyntaxException(stringBuilder.toString());
              }
              
              public void write(JsonWriter param2JsonWriter, T1 param2T1) throws IOException {
                typeAdapter.write(param2JsonWriter, param2T1);
              }
            };
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[typeHierarchy=");
          stringBuilder.append(clazz.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(typeAdapter);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
    private final Map<T, String> constantToName = new HashMap<T, String>();
    
    private final Map<String, T> nameToConstant = new HashMap<String, T>();
    
    public EnumTypeAdapter(Class<T> param1Class) {
      try {
        for (Enum enum_ : (Enum[])param1Class.getEnumConstants()) {
          String str = enum_.name();
          SerializedName serializedName = param1Class.getField(str).<SerializedName>getAnnotation(SerializedName.class);
          if (serializedName != null) {
            String str1 = serializedName.value();
            String[] arrayOfString = serializedName.alternate();
            int i = arrayOfString.length;
            byte b = 0;
            while (true) {
              str = str1;
              if (b < i) {
                str = arrayOfString[b];
                this.nameToConstant.put(str, (T)enum_);
                b++;
                continue;
              } 
              break;
            } 
          } 
          this.nameToConstant.put(str, (T)enum_);
          this.constantToName.put((T)enum_, str);
        } 
        return;
      } catch (NoSuchFieldException noSuchFieldException) {
        throw new AssertionError(noSuchFieldException);
      } 
    }
    
    public T read(JsonReader param1JsonReader) throws IOException {
      if (param1JsonReader.peek() == JsonToken.NULL) {
        param1JsonReader.nextNull();
        return null;
      } 
      return this.nameToConstant.get(param1JsonReader.nextString());
    }
    
    public void write(JsonWriter param1JsonWriter, T param1T) throws IOException {
      String str;
      if (param1T == null) {
        param1T = null;
      } else {
        str = this.constantToName.get(param1T);
      } 
      param1JsonWriter.value(str);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\bind\TypeAdapters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */