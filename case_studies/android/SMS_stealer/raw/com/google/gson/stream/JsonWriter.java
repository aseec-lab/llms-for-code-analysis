package com.google.gson.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JsonWriter implements Closeable, Flushable {
  private static final String[] HTML_SAFE_REPLACEMENT_CHARS;
  
  private static final String[] REPLACEMENT_CHARS = new String[128];
  
  private String deferredName;
  
  private boolean htmlSafe;
  
  private String indent;
  
  private boolean lenient;
  
  private final Writer out;
  
  private String separator;
  
  private boolean serializeNulls;
  
  private int[] stack = new int[32];
  
  private int stackSize = 0;
  
  static {
    for (byte b = 0; b <= 31; b++) {
      REPLACEMENT_CHARS[b] = String.format("\\u%04x", new Object[] { Integer.valueOf(b) });
    } 
    String[] arrayOfString = REPLACEMENT_CHARS;
    arrayOfString[34] = "\\\"";
    arrayOfString[92] = "\\\\";
    arrayOfString[9] = "\\t";
    arrayOfString[8] = "\\b";
    arrayOfString[10] = "\\n";
    arrayOfString[13] = "\\r";
    arrayOfString[12] = "\\f";
    arrayOfString = (String[])arrayOfString.clone();
    HTML_SAFE_REPLACEMENT_CHARS = arrayOfString;
    arrayOfString[60] = "\\u003c";
    arrayOfString[62] = "\\u003e";
    arrayOfString[38] = "\\u0026";
    arrayOfString[61] = "\\u003d";
    arrayOfString[39] = "\\u0027";
  }
  
  public JsonWriter(Writer paramWriter) {
    push(6);
    this.separator = ":";
    this.serializeNulls = true;
    if (paramWriter != null) {
      this.out = paramWriter;
      return;
    } 
    throw new NullPointerException("out == null");
  }
  
  private void beforeName() throws IOException {
    int i = peek();
    if (i == 5) {
      this.out.write(44);
    } else if (i != 3) {
      throw new IllegalStateException("Nesting problem.");
    } 
    newline();
    replaceTop(4);
  }
  
  private void beforeValue() throws IOException {
    int i = peek();
    if (i != 1) {
      if (i != 2) {
        if (i != 4) {
          if (i != 6)
            if (i == 7) {
              if (!this.lenient)
                throw new IllegalStateException("JSON must have only one top-level value."); 
            } else {
              throw new IllegalStateException("Nesting problem.");
            }  
          replaceTop(7);
        } else {
          this.out.append(this.separator);
          replaceTop(5);
        } 
      } else {
        this.out.append(',');
        newline();
      } 
    } else {
      replaceTop(2);
      newline();
    } 
  }
  
  private JsonWriter close(int paramInt1, int paramInt2, String paramString) throws IOException {
    int i = peek();
    if (i == paramInt2 || i == paramInt1) {
      if (this.deferredName == null) {
        this.stackSize--;
        if (i == paramInt2)
          newline(); 
        this.out.write(paramString);
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Dangling name: ");
      stringBuilder.append(this.deferredName);
      throw new IllegalStateException(stringBuilder.toString());
    } 
    throw new IllegalStateException("Nesting problem.");
  }
  
  private void newline() throws IOException {
    if (this.indent == null)
      return; 
    this.out.write("\n");
    int i = this.stackSize;
    for (byte b = 1; b < i; b++)
      this.out.write(this.indent); 
  }
  
  private JsonWriter open(int paramInt, String paramString) throws IOException {
    beforeValue();
    push(paramInt);
    this.out.write(paramString);
    return this;
  }
  
  private int peek() {
    int i = this.stackSize;
    if (i != 0)
      return this.stack[i - 1]; 
    throw new IllegalStateException("JsonWriter is closed.");
  }
  
  private void push(int paramInt) {
    int i = this.stackSize;
    int[] arrayOfInt2 = this.stack;
    if (i == arrayOfInt2.length) {
      int[] arrayOfInt = new int[i * 2];
      System.arraycopy(arrayOfInt2, 0, arrayOfInt, 0, i);
      this.stack = arrayOfInt;
    } 
    int[] arrayOfInt1 = this.stack;
    i = this.stackSize;
    this.stackSize = i + 1;
    arrayOfInt1[i] = paramInt;
  }
  
  private void replaceTop(int paramInt) {
    this.stack[this.stackSize - 1] = paramInt;
  }
  
  private void string(String paramString) throws IOException {
    Object object;
    String[] arrayOfString;
    if (this.htmlSafe) {
      arrayOfString = HTML_SAFE_REPLACEMENT_CHARS;
    } else {
      arrayOfString = REPLACEMENT_CHARS;
    } 
    this.out.write("\"");
    int i = paramString.length();
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      String str;
      char c = paramString.charAt(b);
      if (c < '') {
        String str1 = arrayOfString[c];
        str = str1;
        if (str1 == null) {
          Object object1 = object;
          continue;
        } 
      } else if (c == ' ') {
        str = "\\u2028";
      } else {
        Object object1 = object;
        if (c == ' ') {
          str = "\\u2029";
        } else {
          continue;
        } 
      } 
      if (object < b)
        this.out.write(paramString, object, b - object); 
      this.out.write(str);
      int j = b + 1;
      continue;
      b++;
      object = SYNTHETIC_LOCAL_VARIABLE_3;
    } 
    if (object < i)
      this.out.write(paramString, object, i - object); 
    this.out.write("\"");
  }
  
  private void writeDeferredName() throws IOException {
    if (this.deferredName != null) {
      beforeName();
      string(this.deferredName);
      this.deferredName = null;
    } 
  }
  
  public JsonWriter beginArray() throws IOException {
    writeDeferredName();
    return open(1, "[");
  }
  
  public JsonWriter beginObject() throws IOException {
    writeDeferredName();
    return open(3, "{");
  }
  
  public void close() throws IOException {
    this.out.close();
    int i = this.stackSize;
    if (i <= 1 && (i != 1 || this.stack[i - 1] == 7)) {
      this.stackSize = 0;
      return;
    } 
    throw new IOException("Incomplete document");
  }
  
  public JsonWriter endArray() throws IOException {
    return close(1, 2, "]");
  }
  
  public JsonWriter endObject() throws IOException {
    return close(3, 5, "}");
  }
  
  public void flush() throws IOException {
    if (this.stackSize != 0) {
      this.out.flush();
      return;
    } 
    throw new IllegalStateException("JsonWriter is closed.");
  }
  
  public final boolean getSerializeNulls() {
    return this.serializeNulls;
  }
  
  public final boolean isHtmlSafe() {
    return this.htmlSafe;
  }
  
  public boolean isLenient() {
    return this.lenient;
  }
  
  public JsonWriter jsonValue(String paramString) throws IOException {
    if (paramString == null)
      return nullValue(); 
    writeDeferredName();
    beforeValue();
    this.out.append(paramString);
    return this;
  }
  
  public JsonWriter name(String paramString) throws IOException {
    if (paramString != null) {
      if (this.deferredName == null) {
        if (this.stackSize != 0) {
          this.deferredName = paramString;
          return this;
        } 
        throw new IllegalStateException("JsonWriter is closed.");
      } 
      throw new IllegalStateException();
    } 
    throw new NullPointerException("name == null");
  }
  
  public JsonWriter nullValue() throws IOException {
    if (this.deferredName != null)
      if (this.serializeNulls) {
        writeDeferredName();
      } else {
        this.deferredName = null;
        return this;
      }  
    beforeValue();
    this.out.write("null");
    return this;
  }
  
  public final void setHtmlSafe(boolean paramBoolean) {
    this.htmlSafe = paramBoolean;
  }
  
  public final void setIndent(String paramString) {
    if (paramString.length() == 0) {
      this.indent = null;
      this.separator = ":";
    } else {
      this.indent = paramString;
      this.separator = ": ";
    } 
  }
  
  public final void setLenient(boolean paramBoolean) {
    this.lenient = paramBoolean;
  }
  
  public final void setSerializeNulls(boolean paramBoolean) {
    this.serializeNulls = paramBoolean;
  }
  
  public JsonWriter value(double paramDouble) throws IOException {
    writeDeferredName();
    if (this.lenient || (!Double.isNaN(paramDouble) && !Double.isInfinite(paramDouble))) {
      beforeValue();
      this.out.append(Double.toString(paramDouble));
      return this;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Numeric values must be finite, but was ");
    stringBuilder.append(paramDouble);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public JsonWriter value(long paramLong) throws IOException {
    writeDeferredName();
    beforeValue();
    this.out.write(Long.toString(paramLong));
    return this;
  }
  
  public JsonWriter value(Boolean paramBoolean) throws IOException {
    String str;
    if (paramBoolean == null)
      return nullValue(); 
    writeDeferredName();
    beforeValue();
    Writer writer = this.out;
    if (paramBoolean.booleanValue()) {
      str = "true";
    } else {
      str = "false";
    } 
    writer.write(str);
    return this;
  }
  
  public JsonWriter value(Number paramNumber) throws IOException {
    if (paramNumber == null)
      return nullValue(); 
    writeDeferredName();
    String str = paramNumber.toString();
    if (this.lenient || (!str.equals("-Infinity") && !str.equals("Infinity") && !str.equals("NaN"))) {
      beforeValue();
      this.out.append(str);
      return this;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Numeric values must be finite, but was ");
    stringBuilder.append(paramNumber);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public JsonWriter value(String paramString) throws IOException {
    if (paramString == null)
      return nullValue(); 
    writeDeferredName();
    beforeValue();
    string(paramString);
    return this;
  }
  
  public JsonWriter value(boolean paramBoolean) throws IOException {
    String str;
    writeDeferredName();
    beforeValue();
    Writer writer = this.out;
    if (paramBoolean) {
      str = "true";
    } else {
      str = "false";
    } 
    writer.write(str);
    return this;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\stream\JsonWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */