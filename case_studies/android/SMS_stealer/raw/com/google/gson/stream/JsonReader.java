package com.google.gson.stream;

import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class JsonReader implements Closeable {
  private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
  
  private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
  
  private static final int NUMBER_CHAR_DECIMAL = 3;
  
  private static final int NUMBER_CHAR_DIGIT = 2;
  
  private static final int NUMBER_CHAR_EXP_DIGIT = 7;
  
  private static final int NUMBER_CHAR_EXP_E = 5;
  
  private static final int NUMBER_CHAR_EXP_SIGN = 6;
  
  private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
  
  private static final int NUMBER_CHAR_NONE = 0;
  
  private static final int NUMBER_CHAR_SIGN = 1;
  
  private static final int PEEKED_BEGIN_ARRAY = 3;
  
  private static final int PEEKED_BEGIN_OBJECT = 1;
  
  private static final int PEEKED_BUFFERED = 11;
  
  private static final int PEEKED_DOUBLE_QUOTED = 9;
  
  private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
  
  private static final int PEEKED_END_ARRAY = 4;
  
  private static final int PEEKED_END_OBJECT = 2;
  
  private static final int PEEKED_EOF = 17;
  
  private static final int PEEKED_FALSE = 6;
  
  private static final int PEEKED_LONG = 15;
  
  private static final int PEEKED_NONE = 0;
  
  private static final int PEEKED_NULL = 7;
  
  private static final int PEEKED_NUMBER = 16;
  
  private static final int PEEKED_SINGLE_QUOTED = 8;
  
  private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
  
  private static final int PEEKED_TRUE = 5;
  
  private static final int PEEKED_UNQUOTED = 10;
  
  private static final int PEEKED_UNQUOTED_NAME = 14;
  
  private final char[] buffer = new char[1024];
  
  private final Reader in;
  
  private boolean lenient = false;
  
  private int limit = 0;
  
  private int lineNumber = 0;
  
  private int lineStart = 0;
  
  private int[] pathIndices;
  
  private String[] pathNames;
  
  int peeked = 0;
  
  private long peekedLong;
  
  private int peekedNumberLength;
  
  private String peekedString;
  
  private int pos = 0;
  
  private int[] stack;
  
  private int stackSize;
  
  static {
    JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() {
        public void promoteNameToValue(JsonReader param1JsonReader) throws IOException {
          if (param1JsonReader instanceof JsonTreeReader) {
            ((JsonTreeReader)param1JsonReader).promoteNameToValue();
            return;
          } 
          int j = param1JsonReader.peeked;
          int i = j;
          if (j == 0)
            i = param1JsonReader.doPeek(); 
          if (i == 13) {
            param1JsonReader.peeked = 9;
          } else if (i == 12) {
            param1JsonReader.peeked = 8;
          } else {
            if (i == 14) {
              param1JsonReader.peeked = 10;
              return;
            } 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a name but was ");
            stringBuilder.append(param1JsonReader.peek());
            stringBuilder.append(param1JsonReader.locationString());
            throw new IllegalStateException(stringBuilder.toString());
          } 
        }
      };
  }
  
  public JsonReader(Reader paramReader) {
    int[] arrayOfInt = new int[32];
    this.stack = arrayOfInt;
    this.stackSize = 0;
    this.stackSize = 0 + 1;
    arrayOfInt[0] = 6;
    this.pathNames = new String[32];
    this.pathIndices = new int[32];
    if (paramReader != null) {
      this.in = paramReader;
      return;
    } 
    throw new NullPointerException("in == null");
  }
  
  private void checkLenient() throws IOException {
    if (this.lenient)
      return; 
    throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
  }
  
  private void consumeNonExecutePrefix() throws IOException {
    nextNonWhitespace(true);
    int i = this.pos - 1;
    this.pos = i;
    char[] arrayOfChar = NON_EXECUTE_PREFIX;
    if (i + arrayOfChar.length > this.limit && !fillBuffer(arrayOfChar.length))
      return; 
    i = 0;
    while (true) {
      arrayOfChar = NON_EXECUTE_PREFIX;
      if (i < arrayOfChar.length) {
        if (this.buffer[this.pos + i] != arrayOfChar[i])
          return; 
        i++;
        continue;
      } 
      this.pos += arrayOfChar.length;
      return;
    } 
  }
  
  private boolean fillBuffer(int paramInt) throws IOException {
    char[] arrayOfChar = this.buffer;
    int j = this.lineStart;
    int i = this.pos;
    this.lineStart = j - i;
    j = this.limit;
    if (j != i) {
      j -= i;
      this.limit = j;
      System.arraycopy(arrayOfChar, i, arrayOfChar, 0, j);
    } else {
      this.limit = 0;
    } 
    this.pos = 0;
    while (true) {
      Reader reader = this.in;
      i = this.limit;
      i = reader.read(arrayOfChar, i, arrayOfChar.length - i);
      if (i != -1) {
        int k = this.limit + i;
        this.limit = k;
        i = paramInt;
        if (this.lineNumber == 0) {
          j = this.lineStart;
          i = paramInt;
          if (j == 0) {
            i = paramInt;
            if (k > 0) {
              i = paramInt;
              if (arrayOfChar[0] == '﻿') {
                this.pos++;
                this.lineStart = j + 1;
                i = paramInt + 1;
              } 
            } 
          } 
        } 
        paramInt = i;
        if (this.limit >= i)
          return true; 
        continue;
      } 
      return false;
    } 
  }
  
  private boolean isLiteral(char paramChar) throws IOException {
    if (paramChar != '\t' && paramChar != '\n' && paramChar != '\f' && paramChar != '\r' && paramChar != ' ')
      if (paramChar != '#') {
        if (paramChar != ',')
          if (paramChar != '/' && paramChar != '=') {
            if (paramChar != '{' && paramChar != '}' && paramChar != ':')
              if (paramChar != ';') {
                switch (paramChar) {
                  default:
                    return true;
                  case '\\':
                    checkLenient();
                    break;
                  case '[':
                  case ']':
                    break;
                } 
                return false;
              }  
            return false;
          }  
        return false;
      }  
    return false;
  }
  
  private int nextNonWhitespace(boolean paramBoolean) throws IOException {
    char[] arrayOfChar = this.buffer;
    int i = this.pos;
    int j = this.limit;
    while (true) {
      StringBuilder stringBuilder2;
      StringBuilder stringBuilder3;
      int m = i;
      int k = j;
      if (i == j) {
        this.pos = i;
        if (!fillBuffer(1)) {
          if (!paramBoolean)
            return -1; 
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append("End of input");
          stringBuilder3.append(locationString());
          throw new EOFException(stringBuilder3.toString());
        } 
        m = this.pos;
        k = this.limit;
      } 
      i = m + 1;
      StringBuilder stringBuilder1 = stringBuilder3[m];
      if (stringBuilder1 == 10) {
        this.lineNumber++;
        this.lineStart = i;
      } else if (stringBuilder1 != 32 && stringBuilder1 != 13 && stringBuilder1 != 9) {
        int n;
        if (stringBuilder1 == 47) {
          this.pos = i;
          if (i == k) {
            this.pos = i - 1;
            boolean bool = fillBuffer(2);
            this.pos++;
            if (!bool)
              return stringBuilder1; 
          } 
          checkLenient();
          i = this.pos;
          stringBuilder2 = stringBuilder3[i];
          if (stringBuilder2 != 42) {
            if (stringBuilder2 != 47)
              return stringBuilder1; 
            this.pos = i + 1;
            skipToEndOfLine();
            i = this.pos;
            n = this.limit;
            continue;
          } 
          this.pos = i + 1;
          if (skipTo("*/")) {
            i = this.pos + 2;
            n = this.limit;
            continue;
          } 
          throw syntaxError("Unterminated comment");
        } 
        if (n == 35) {
          this.pos = i;
          checkLenient();
          skipToEndOfLine();
          i = this.pos;
          n = this.limit;
          continue;
        } 
        this.pos = i;
        return n;
      } 
      stringBuilder1 = stringBuilder2;
    } 
  }
  
  private String nextQuotedValue(char paramChar) throws IOException {
    char[] arrayOfChar = this.buffer;
    for (StringBuilder stringBuilder = null;; stringBuilder = stringBuilder1) {
      StringBuilder stringBuilder1;
      int i = this.pos;
      int j = this.limit;
      label31: while (true) {
        int k = i;
        while (true) {
          int m = k;
          if (m < j) {
            k = m + 1;
            m = arrayOfChar[m];
            if (m == paramChar) {
              this.pos = k;
              int n = k - i - 1;
              if (stringBuilder == null)
                return new String(arrayOfChar, i, n); 
              stringBuilder.append(arrayOfChar, i, n);
              return stringBuilder.toString();
            } 
            if (m == 92) {
              this.pos = k;
              k = k - i - 1;
              StringBuilder stringBuilder2 = stringBuilder;
              if (stringBuilder == null)
                stringBuilder2 = new StringBuilder(Math.max((k + 1) * 2, 16)); 
              stringBuilder2.append(arrayOfChar, i, k);
              stringBuilder2.append(readEscapeCharacter());
              i = this.pos;
              j = this.limit;
              stringBuilder = stringBuilder2;
              continue label31;
            } 
            if (m == 10) {
              this.lineNumber++;
              this.lineStart = k;
            } 
            continue;
          } 
          stringBuilder1 = stringBuilder;
          if (stringBuilder == null)
            stringBuilder1 = new StringBuilder(Math.max((m - i) * 2, 16)); 
          stringBuilder1.append(arrayOfChar, i, m - i);
          this.pos = m;
          if (fillBuffer(1))
            break; 
          throw syntaxError("Unterminated string");
        } 
        break;
      } 
    } 
  }
  
  private String nextUnquotedValue() throws IOException {
    byte b;
    StringBuilder stringBuilder1;
    String str;
    boolean bool = false;
    StringBuilder stringBuilder2 = null;
    while (true) {
      b = 0;
      while (true) {
        int i = this.pos;
        if (i + b < this.limit) {
          i = this.buffer[i + b];
          if (i != 9 && i != 10 && i != 12 && i != 13 && i != 32)
            if (i != 35) {
              if (i != 44)
                if (i != 47 && i != 61) {
                  if (i != 123 && i != 125 && i != 58)
                    if (i != 59) {
                      switch (i) {
                        default:
                          b++;
                          continue;
                        case 92:
                          checkLenient();
                          break;
                        case 91:
                        case 93:
                          break;
                      } 
                    } else {
                    
                    }  
                } else {
                
                }  
            } else {
            
            }  
        } else if (b < this.buffer.length) {
          if (fillBuffer(b + 1))
            continue; 
        } else {
          StringBuilder stringBuilder = stringBuilder2;
          if (stringBuilder2 == null)
            stringBuilder = new StringBuilder(Math.max(b, 16)); 
          stringBuilder.append(this.buffer, this.pos, b);
          this.pos += b;
          stringBuilder2 = stringBuilder;
          break;
        } 
        stringBuilder1 = stringBuilder2;
        break;
      } 
      if (!fillBuffer(1)) {
        b = bool;
        break;
      } 
    } 
    if (stringBuilder1 == null) {
      str = new String(this.buffer, this.pos, b);
    } else {
      str.append(this.buffer, this.pos, b);
      str = str.toString();
    } 
    this.pos += b;
    return str;
  }
  
  private int peekKeyword() throws IOException {
    String str1;
    String str2;
    char c = this.buffer[this.pos];
    if (c == 't' || c == 'T') {
      c = '\005';
      str1 = "true";
      str2 = "TRUE";
    } else if (c == 'f' || c == 'F') {
      c = '\006';
      str1 = "false";
      str2 = "FALSE";
    } else if (c == 'n' || c == 'N') {
      c = '\007';
      str1 = "null";
      str2 = "NULL";
    } else {
      return 0;
    } 
    int i = str1.length();
    for (byte b = 1; b < i; b++) {
      if (this.pos + b >= this.limit && !fillBuffer(b + 1))
        return 0; 
      char c1 = this.buffer[this.pos + b];
      if (c1 != str1.charAt(b) && c1 != str2.charAt(b))
        return 0; 
    } 
    if ((this.pos + i < this.limit || fillBuffer(i + 1)) && isLiteral(this.buffer[this.pos + i]))
      return 0; 
    this.pos += i;
    this.peeked = c;
    return c;
  }
  
  private int peekNumber() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield buffer : [C
    //   4: astore #14
    //   6: aload_0
    //   7: getfield pos : I
    //   10: istore #9
    //   12: aload_0
    //   13: getfield limit : I
    //   16: istore #4
    //   18: iconst_0
    //   19: istore #5
    //   21: iconst_0
    //   22: istore_2
    //   23: iconst_1
    //   24: istore_3
    //   25: lconst_0
    //   26: lstore #10
    //   28: iconst_0
    //   29: istore #6
    //   31: iload #9
    //   33: istore #8
    //   35: iload #4
    //   37: istore #7
    //   39: iload #9
    //   41: iload #5
    //   43: iadd
    //   44: iload #4
    //   46: if_icmpne -> 85
    //   49: iload #5
    //   51: aload #14
    //   53: arraylength
    //   54: if_icmpne -> 59
    //   57: iconst_0
    //   58: ireturn
    //   59: aload_0
    //   60: iload #5
    //   62: iconst_1
    //   63: iadd
    //   64: invokespecial fillBuffer : (I)Z
    //   67: ifne -> 73
    //   70: goto -> 294
    //   73: aload_0
    //   74: getfield pos : I
    //   77: istore #8
    //   79: aload_0
    //   80: getfield limit : I
    //   83: istore #7
    //   85: aload #14
    //   87: iload #8
    //   89: iload #5
    //   91: iadd
    //   92: caload
    //   93: istore_1
    //   94: iload_1
    //   95: bipush #43
    //   97: if_icmpeq -> 460
    //   100: iload_1
    //   101: bipush #69
    //   103: if_icmpeq -> 440
    //   106: iload_1
    //   107: bipush #101
    //   109: if_icmpeq -> 440
    //   112: iload_1
    //   113: bipush #45
    //   115: if_icmpeq -> 418
    //   118: iload_1
    //   119: bipush #46
    //   121: if_icmpeq -> 406
    //   124: iload_1
    //   125: bipush #48
    //   127: if_icmplt -> 286
    //   130: iload_1
    //   131: bipush #57
    //   133: if_icmple -> 139
    //   136: goto -> 286
    //   139: iload_2
    //   140: iconst_1
    //   141: if_icmpeq -> 273
    //   144: iload_2
    //   145: ifne -> 151
    //   148: goto -> 273
    //   151: iload_2
    //   152: iconst_2
    //   153: if_icmpne -> 226
    //   156: lload #10
    //   158: lconst_0
    //   159: lcmp
    //   160: ifne -> 165
    //   163: iconst_0
    //   164: ireturn
    //   165: ldc2_w 10
    //   168: lload #10
    //   170: lmul
    //   171: iload_1
    //   172: bipush #48
    //   174: isub
    //   175: i2l
    //   176: lsub
    //   177: lstore #12
    //   179: lload #10
    //   181: ldc2_w -922337203685477580
    //   184: lcmp
    //   185: istore #4
    //   187: iload #4
    //   189: ifgt -> 214
    //   192: iload #4
    //   194: ifne -> 208
    //   197: lload #12
    //   199: lload #10
    //   201: lcmp
    //   202: ifge -> 208
    //   205: goto -> 214
    //   208: iconst_0
    //   209: istore #4
    //   211: goto -> 217
    //   214: iconst_1
    //   215: istore #4
    //   217: iload_3
    //   218: iload #4
    //   220: iand
    //   221: istore #4
    //   223: goto -> 257
    //   226: iload_2
    //   227: iconst_3
    //   228: if_icmpne -> 236
    //   231: iconst_4
    //   232: istore_2
    //   233: goto -> 283
    //   236: iload_2
    //   237: iconst_5
    //   238: if_icmpeq -> 267
    //   241: iload_3
    //   242: istore #4
    //   244: lload #10
    //   246: lstore #12
    //   248: iload_2
    //   249: bipush #6
    //   251: if_icmpne -> 257
    //   254: goto -> 267
    //   257: iload #4
    //   259: istore_3
    //   260: lload #12
    //   262: lstore #10
    //   264: goto -> 283
    //   267: bipush #7
    //   269: istore_2
    //   270: goto -> 283
    //   273: iload_1
    //   274: bipush #48
    //   276: isub
    //   277: ineg
    //   278: i2l
    //   279: lstore #10
    //   281: iconst_2
    //   282: istore_2
    //   283: goto -> 468
    //   286: aload_0
    //   287: iload_1
    //   288: invokespecial isLiteral : (C)Z
    //   291: ifne -> 404
    //   294: iload_2
    //   295: iconst_2
    //   296: if_icmpne -> 368
    //   299: iload_3
    //   300: ifeq -> 368
    //   303: lload #10
    //   305: ldc2_w -9223372036854775808
    //   308: lcmp
    //   309: ifne -> 317
    //   312: iload #6
    //   314: ifeq -> 368
    //   317: lload #10
    //   319: lconst_0
    //   320: lcmp
    //   321: ifne -> 329
    //   324: iload #6
    //   326: ifne -> 368
    //   329: iload #6
    //   331: ifeq -> 337
    //   334: goto -> 342
    //   337: lload #10
    //   339: lneg
    //   340: lstore #10
    //   342: aload_0
    //   343: lload #10
    //   345: putfield peekedLong : J
    //   348: aload_0
    //   349: aload_0
    //   350: getfield pos : I
    //   353: iload #5
    //   355: iadd
    //   356: putfield pos : I
    //   359: aload_0
    //   360: bipush #15
    //   362: putfield peeked : I
    //   365: bipush #15
    //   367: ireturn
    //   368: iload_2
    //   369: iconst_2
    //   370: if_icmpeq -> 389
    //   373: iload_2
    //   374: iconst_4
    //   375: if_icmpeq -> 389
    //   378: iload_2
    //   379: bipush #7
    //   381: if_icmpne -> 387
    //   384: goto -> 389
    //   387: iconst_0
    //   388: ireturn
    //   389: aload_0
    //   390: iload #5
    //   392: putfield peekedNumberLength : I
    //   395: aload_0
    //   396: bipush #16
    //   398: putfield peeked : I
    //   401: bipush #16
    //   403: ireturn
    //   404: iconst_0
    //   405: ireturn
    //   406: iload_2
    //   407: iconst_2
    //   408: if_icmpne -> 416
    //   411: iconst_3
    //   412: istore_2
    //   413: goto -> 468
    //   416: iconst_0
    //   417: ireturn
    //   418: iload_2
    //   419: ifne -> 430
    //   422: iconst_1
    //   423: istore_2
    //   424: iconst_1
    //   425: istore #6
    //   427: goto -> 468
    //   430: iload_2
    //   431: iconst_5
    //   432: if_icmpne -> 438
    //   435: goto -> 465
    //   438: iconst_0
    //   439: ireturn
    //   440: iload_2
    //   441: iconst_2
    //   442: if_icmpeq -> 455
    //   445: iload_2
    //   446: iconst_4
    //   447: if_icmpne -> 453
    //   450: goto -> 455
    //   453: iconst_0
    //   454: ireturn
    //   455: iconst_5
    //   456: istore_2
    //   457: goto -> 468
    //   460: iload_2
    //   461: iconst_5
    //   462: if_icmpne -> 482
    //   465: bipush #6
    //   467: istore_2
    //   468: iinc #5, 1
    //   471: iload #8
    //   473: istore #9
    //   475: iload #7
    //   477: istore #4
    //   479: goto -> 31
    //   482: iconst_0
    //   483: ireturn
  }
  
  private void push(int paramInt) {
    int i = this.stackSize;
    int[] arrayOfInt = this.stack;
    if (i == arrayOfInt.length) {
      int[] arrayOfInt2 = new int[i * 2];
      int[] arrayOfInt1 = new int[i * 2];
      String[] arrayOfString = new String[i * 2];
      System.arraycopy(arrayOfInt, 0, arrayOfInt2, 0, i);
      System.arraycopy(this.pathIndices, 0, arrayOfInt1, 0, this.stackSize);
      System.arraycopy(this.pathNames, 0, arrayOfString, 0, this.stackSize);
      this.stack = arrayOfInt2;
      this.pathIndices = arrayOfInt1;
      this.pathNames = arrayOfString;
    } 
    arrayOfInt = this.stack;
    i = this.stackSize;
    this.stackSize = i + 1;
    arrayOfInt[i] = paramInt;
  }
  
  private char readEscapeCharacter() throws IOException {
    if (this.pos != this.limit || fillBuffer(1)) {
      char[] arrayOfChar = this.buffer;
      int i = this.pos;
      int j = i + 1;
      this.pos = j;
      char c = arrayOfChar[i];
      if (c != '\n') {
        if (c != '"' && c != '\'' && c != '/' && c != '\\') {
          if (c != 'b') {
            if (c != 'f') {
              if (c != 'n') {
                if (c != 'r') {
                  if (c != 't') {
                    if (c == 'u') {
                      if (j + 4 <= this.limit || fillBuffer(4)) {
                        c = Character.MIN_VALUE;
                        j = this.pos;
                        i = j;
                        while (true) {
                          int k = i;
                          if (k < j + 4) {
                            i = this.buffer[k];
                            char c1 = (char)(c << 4);
                            if (i >= 48 && i <= 57) {
                              i -= 48;
                            } else {
                              if (i >= 97 && i <= 102) {
                                i -= 97;
                              } else if (i >= 65) {
                                if (i <= 70) {
                                  i -= 65;
                                } else {
                                  StringBuilder stringBuilder = new StringBuilder();
                                  stringBuilder.append("\\u");
                                  stringBuilder.append(new String(this.buffer, this.pos, 4));
                                  throw new NumberFormatException(stringBuilder.toString());
                                } 
                              } else {
                                continue;
                              } 
                              i += 10;
                            } 
                            c = (char)(c1 + i);
                            i = k + 1;
                            continue;
                          } 
                          this.pos += 4;
                          return c;
                        } 
                      } 
                      throw syntaxError("Unterminated escape sequence");
                    } 
                    throw syntaxError("Invalid escape sequence");
                  } 
                  return '\t';
                } 
                return '\r';
              } 
              return '\n';
            } 
            return '\f';
          } 
          return '\b';
        } 
      } else {
        this.lineNumber++;
        this.lineStart = j;
      } 
      return c;
    } 
    throw syntaxError("Unterminated escape sequence");
  }
  
  private void skipQuotedValue(char paramChar) throws IOException {
    char[] arrayOfChar = this.buffer;
    while (true) {
      int i = this.pos;
      int j = this.limit;
      while (i < j) {
        int k = i + 1;
        i = arrayOfChar[i];
        if (i == paramChar) {
          this.pos = k;
          return;
        } 
        if (i == 92) {
          this.pos = k;
          readEscapeCharacter();
          i = this.pos;
          j = this.limit;
          continue;
        } 
        if (i == 10) {
          this.lineNumber++;
          this.lineStart = k;
        } 
        i = k;
      } 
      this.pos = i;
      if (fillBuffer(1))
        continue; 
      throw syntaxError("Unterminated string");
    } 
  }
  
  private boolean skipTo(String paramString) throws IOException {
    int i = paramString.length();
    label20: while (true) {
      int k = this.pos;
      int j = this.limit;
      byte b = 0;
      if (k + i <= j || fillBuffer(i)) {
        char[] arrayOfChar = this.buffer;
        j = this.pos;
        if (arrayOfChar[j] == '\n') {
          this.lineNumber++;
          this.lineStart = j + 1;
          continue;
        } 
        while (b < i) {
          if (this.buffer[this.pos + b] != paramString.charAt(b)) {
            this.pos++;
            continue label20;
          } 
          b++;
        } 
        return true;
      } 
      return false;
    } 
  }
  
  private void skipToEndOfLine() throws IOException {
    while (this.pos < this.limit || fillBuffer(1)) {
      char[] arrayOfChar = this.buffer;
      int j = this.pos;
      int i = j + 1;
      this.pos = i;
      j = arrayOfChar[j];
      if (j == 10) {
        this.lineNumber++;
        this.lineStart = i;
        break;
      } 
      if (j == 13)
        break; 
    } 
  }
  
  private void skipUnquotedValue() throws IOException {
    do {
      byte b = 0;
      while (true) {
        int i = this.pos;
        if (i + b < this.limit) {
          i = this.buffer[i + b];
          if (i != 9 && i != 10 && i != 12 && i != 13 && i != 32)
            if (i != 35) {
              if (i != 44)
                if (i != 47 && i != 61) {
                  if (i != 123 && i != 125 && i != 58)
                    if (i != 59) {
                      switch (i) {
                        default:
                          b++;
                          continue;
                        case 92:
                          checkLenient();
                          break;
                        case 91:
                        case 93:
                          break;
                      } 
                    } else {
                    
                    }  
                } else {
                
                }  
            } else {
            
            }  
          this.pos += b;
          return;
        } 
        this.pos = i + b;
        break;
      } 
    } while (fillBuffer(1));
  }
  
  private IOException syntaxError(String paramString) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(locationString());
    throw new MalformedJsonException(stringBuilder.toString());
  }
  
  public void beginArray() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 3) {
      push(1);
      this.pathIndices[this.stackSize - 1] = 0;
      this.peeked = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected BEGIN_ARRAY but was ");
    stringBuilder.append(peek());
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void beginObject() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 1) {
      push(3);
      this.peeked = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected BEGIN_OBJECT but was ");
    stringBuilder.append(peek());
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void close() throws IOException {
    this.peeked = 0;
    this.stack[0] = 8;
    this.stackSize = 1;
    this.in.close();
  }
  
  int doPeek() throws IOException {
    int[] arrayOfInt = this.stack;
    int j = this.stackSize;
    int i = arrayOfInt[j - 1];
    if (i == 1) {
      arrayOfInt[j - 1] = 2;
    } else if (i == 2) {
      j = nextNonWhitespace(true);
      if (j != 44) {
        if (j != 59) {
          if (j == 93) {
            this.peeked = 4;
            return 4;
          } 
          throw syntaxError("Unterminated array");
        } 
        checkLenient();
      } 
    } else {
      if (i == 3 || i == 5) {
        this.stack[this.stackSize - 1] = 4;
        if (i == 5) {
          j = nextNonWhitespace(true);
          if (j != 44) {
            if (j != 59) {
              if (j == 125) {
                this.peeked = 2;
                return 2;
              } 
              throw syntaxError("Unterminated object");
            } 
            checkLenient();
          } 
        } 
        j = nextNonWhitespace(true);
        if (j != 34) {
          if (j != 39) {
            if (j != 125) {
              checkLenient();
              this.pos--;
              if (isLiteral((char)j)) {
                this.peeked = 14;
                return 14;
              } 
              throw syntaxError("Expected name");
            } 
            if (i != 5) {
              this.peeked = 2;
              return 2;
            } 
            throw syntaxError("Expected name");
          } 
          checkLenient();
          this.peeked = 12;
          return 12;
        } 
        this.peeked = 13;
        return 13;
      } 
      if (i == 4) {
        arrayOfInt[j - 1] = 5;
        j = nextNonWhitespace(true);
        if (j != 58)
          if (j == 61) {
            checkLenient();
            if (this.pos < this.limit || fillBuffer(1)) {
              char[] arrayOfChar = this.buffer;
              j = this.pos;
              if (arrayOfChar[j] == '>')
                this.pos = j + 1; 
            } 
          } else {
            throw syntaxError("Expected ':'");
          }  
      } else if (i == 6) {
        if (this.lenient)
          consumeNonExecutePrefix(); 
        this.stack[this.stackSize - 1] = 7;
      } else if (i == 7) {
        if (nextNonWhitespace(false) == -1) {
          this.peeked = 17;
          return 17;
        } 
        checkLenient();
        this.pos--;
      } else if (i == 8) {
        throw new IllegalStateException("JsonReader is closed");
      } 
    } 
    j = nextNonWhitespace(true);
    if (j != 34) {
      if (j != 39) {
        if (j != 44 && j != 59)
          if (j != 91) {
            if (j != 93) {
              if (j != 123) {
                this.pos--;
                i = peekKeyword();
                if (i != 0)
                  return i; 
                i = peekNumber();
                if (i != 0)
                  return i; 
                if (isLiteral(this.buffer[this.pos])) {
                  checkLenient();
                  this.peeked = 10;
                  return 10;
                } 
                throw syntaxError("Expected value");
              } 
              this.peeked = 1;
              return 1;
            } 
            if (i == 1) {
              this.peeked = 4;
              return 4;
            } 
          } else {
            this.peeked = 3;
            return 3;
          }  
        if (i == 1 || i == 2) {
          checkLenient();
          this.pos--;
          this.peeked = 7;
          return 7;
        } 
        throw syntaxError("Unexpected value");
      } 
      checkLenient();
      this.peeked = 8;
      return 8;
    } 
    this.peeked = 9;
    return 9;
  }
  
  public void endArray() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 4) {
      i = this.stackSize - 1;
      this.stackSize = i;
      int[] arrayOfInt = this.pathIndices;
      arrayOfInt[--i] = arrayOfInt[i] + 1;
      this.peeked = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected END_ARRAY but was ");
    stringBuilder.append(peek());
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void endObject() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 2) {
      i = this.stackSize - 1;
      this.stackSize = i;
      this.pathNames[i] = null;
      int[] arrayOfInt = this.pathIndices;
      arrayOfInt[--i] = arrayOfInt[i] + 1;
      this.peeked = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected END_OBJECT but was ");
    stringBuilder.append(peek());
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public String getPath() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('$');
    int i = this.stackSize;
    for (byte b = 0; b < i; b++) {
      int j = this.stack[b];
      if (j != 1 && j != 2) {
        if (j == 3 || j == 4 || j == 5) {
          stringBuilder.append('.');
          String[] arrayOfString = this.pathNames;
          if (arrayOfString[b] != null)
            stringBuilder.append(arrayOfString[b]); 
        } 
      } else {
        stringBuilder.append('[');
        stringBuilder.append(this.pathIndices[b]);
        stringBuilder.append(']');
      } 
    } 
    return stringBuilder.toString();
  }
  
  public boolean hasNext() throws IOException {
    boolean bool;
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i != 2 && i != 4) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isLenient() {
    return this.lenient;
  }
  
  String locationString() {
    int k = this.lineNumber;
    int i = this.pos;
    int j = this.lineStart;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(" at line ");
    stringBuilder.append(k + 1);
    stringBuilder.append(" column ");
    stringBuilder.append(i - j + 1);
    stringBuilder.append(" path ");
    stringBuilder.append(getPath());
    return stringBuilder.toString();
  }
  
  public boolean nextBoolean() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 5) {
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return true;
    } 
    if (i == 6) {
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return false;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected a boolean but was ");
    stringBuilder.append(peek());
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public double nextDouble() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 15) {
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return this.peekedLong;
    } 
    if (i == 16) {
      this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
      this.pos += this.peekedNumberLength;
    } else if (i == 8 || i == 9) {
      byte b;
      if (i == 8) {
        b = 39;
      } else {
        b = 34;
      } 
      this.peekedString = nextQuotedValue(b);
    } else if (i == 10) {
      this.peekedString = nextUnquotedValue();
    } else if (i != 11) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected a double but was ");
      stringBuilder1.append(peek());
      stringBuilder1.append(locationString());
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    this.peeked = 11;
    double d = Double.parseDouble(this.peekedString);
    if (this.lenient || (!Double.isNaN(d) && !Double.isInfinite(d))) {
      this.peekedString = null;
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return d;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("JSON forbids NaN and infinities: ");
    stringBuilder.append(d);
    stringBuilder.append(locationString());
    throw new MalformedJsonException(stringBuilder.toString());
  }
  
  public int nextInt() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 15) {
      long l = this.peekedLong;
      i = (int)l;
      if (l == i) {
        this.peeked = 0;
        int[] arrayOfInt = this.pathIndices;
        j = this.stackSize - 1;
        arrayOfInt[j] = arrayOfInt[j] + 1;
        return i;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected an int but was ");
      stringBuilder1.append(this.peekedLong);
      stringBuilder1.append(locationString());
      throw new NumberFormatException(stringBuilder1.toString());
    } 
    if (i == 16) {
      this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
      this.pos += this.peekedNumberLength;
    } else if (i == 8 || i == 9 || i == 10) {
      if (i == 10) {
        this.peekedString = nextUnquotedValue();
      } else {
        byte b;
        if (i == 8) {
          b = 39;
        } else {
          b = 34;
        } 
        this.peekedString = nextQuotedValue(b);
      } 
      try {
        j = Integer.parseInt(this.peekedString);
        this.peeked = 0;
        int[] arrayOfInt = this.pathIndices;
        i = this.stackSize - 1;
        arrayOfInt[i] = arrayOfInt[i] + 1;
        return j;
      } catch (NumberFormatException numberFormatException) {}
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected an int but was ");
      stringBuilder1.append(peek());
      stringBuilder1.append(locationString());
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    this.peeked = 11;
    double d = Double.parseDouble(this.peekedString);
    j = (int)d;
    if (j == d) {
      this.peekedString = null;
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return j;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected an int but was ");
    stringBuilder.append(this.peekedString);
    stringBuilder.append(locationString());
    throw new NumberFormatException(stringBuilder.toString());
  }
  
  public long nextLong() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 15) {
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return this.peekedLong;
    } 
    if (i == 16) {
      this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
      this.pos += this.peekedNumberLength;
    } else if (i == 8 || i == 9 || i == 10) {
      if (i == 10) {
        this.peekedString = nextUnquotedValue();
      } else {
        byte b;
        if (i == 8) {
          b = 39;
        } else {
          b = 34;
        } 
        this.peekedString = nextQuotedValue(b);
      } 
      try {
        long l1 = Long.parseLong(this.peekedString);
        this.peeked = 0;
        int[] arrayOfInt = this.pathIndices;
        i = this.stackSize - 1;
        arrayOfInt[i] = arrayOfInt[i] + 1;
        return l1;
      } catch (NumberFormatException numberFormatException) {}
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected a long but was ");
      stringBuilder1.append(peek());
      stringBuilder1.append(locationString());
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    this.peeked = 11;
    double d = Double.parseDouble(this.peekedString);
    long l = (long)d;
    if (l == d) {
      this.peekedString = null;
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return l;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected a long but was ");
    stringBuilder.append(this.peekedString);
    stringBuilder.append(locationString());
    throw new NumberFormatException(stringBuilder.toString());
  }
  
  public String nextName() throws IOException {
    StringBuilder stringBuilder;
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 14) {
      String str = nextUnquotedValue();
    } else if (i == 12) {
      String str = nextQuotedValue('\'');
    } else {
      if (i == 13) {
        String str = nextQuotedValue('"');
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = str;
        return str;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("Expected a name but was ");
      stringBuilder.append(peek());
      stringBuilder.append(locationString());
      throw new IllegalStateException(stringBuilder.toString());
    } 
    this.peeked = 0;
    this.pathNames[this.stackSize - 1] = (String)stringBuilder;
    return (String)stringBuilder;
  }
  
  public void nextNull() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 7) {
      this.peeked = 0;
      int[] arrayOfInt = this.pathIndices;
      i = this.stackSize - 1;
      arrayOfInt[i] = arrayOfInt[i] + 1;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected null but was ");
    stringBuilder.append(peek());
    stringBuilder.append(locationString());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public String nextString() throws IOException {
    StringBuilder stringBuilder;
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    if (i == 10) {
      String str = nextUnquotedValue();
    } else if (i == 8) {
      String str = nextQuotedValue('\'');
    } else if (i == 9) {
      String str = nextQuotedValue('"');
    } else if (i == 11) {
      String str = this.peekedString;
      this.peekedString = null;
    } else if (i == 15) {
      String str = Long.toString(this.peekedLong);
    } else {
      if (i == 16) {
        String str = new String(this.buffer, this.pos, this.peekedNumberLength);
        this.pos += this.peekedNumberLength;
        this.peeked = 0;
        int[] arrayOfInt1 = this.pathIndices;
        i = this.stackSize - 1;
        arrayOfInt1[i] = arrayOfInt1[i] + 1;
        return str;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("Expected a string but was ");
      stringBuilder.append(peek());
      stringBuilder.append(locationString());
      throw new IllegalStateException(stringBuilder.toString());
    } 
    this.peeked = 0;
    int[] arrayOfInt = this.pathIndices;
    i = this.stackSize - 1;
    arrayOfInt[i] = arrayOfInt[i] + 1;
    return (String)stringBuilder;
  }
  
  public JsonToken peek() throws IOException {
    int j = this.peeked;
    int i = j;
    if (j == 0)
      i = doPeek(); 
    switch (i) {
      default:
        throw new AssertionError();
      case 17:
        return JsonToken.END_DOCUMENT;
      case 15:
      case 16:
        return JsonToken.NUMBER;
      case 12:
      case 13:
      case 14:
        return JsonToken.NAME;
      case 8:
      case 9:
      case 10:
      case 11:
        return JsonToken.STRING;
      case 7:
        return JsonToken.NULL;
      case 5:
      case 6:
        return JsonToken.BOOLEAN;
      case 4:
        return JsonToken.END_ARRAY;
      case 3:
        return JsonToken.BEGIN_ARRAY;
      case 2:
        return JsonToken.END_OBJECT;
      case 1:
        break;
    } 
    return JsonToken.BEGIN_OBJECT;
  }
  
  public final void setLenient(boolean paramBoolean) {
    this.lenient = paramBoolean;
  }
  
  public void skipValue() throws IOException {
    for (int i = 0;; i = j) {
      int j = this.peeked;
      int k = j;
      if (j == 0)
        k = doPeek(); 
      if (k == 3) {
        push(1);
      } else if (k == 1) {
        push(3);
      } else {
        if (k == 4) {
          this.stackSize--;
        } else if (k == 2) {
          this.stackSize--;
        } else {
          if (k == 14 || k == 10) {
            skipUnquotedValue();
            j = i;
          } else if (k == 8 || k == 12) {
            skipQuotedValue('\'');
            j = i;
          } else if (k == 9 || k == 13) {
            skipQuotedValue('"');
            j = i;
          } else {
            j = i;
            if (k == 16) {
              this.pos += this.peekedNumberLength;
              j = i;
            } 
          } 
          this.peeked = 0;
          i = j;
        } 
        j = i - 1;
        this.peeked = 0;
        i = j;
      } 
      j = i + 1;
      this.peeked = 0;
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getClass().getSimpleName());
    stringBuilder.append(locationString());
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\stream\JsonReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */