package okhttp3.internal.http2;

import java.io.IOException;
import okhttp3.internal.Util;
import okio.ByteString;

public final class Http2 {
  static final String[] BINARY;
  
  static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
  
  static final String[] FLAGS;
  
  static final byte FLAG_ACK = 1;
  
  static final byte FLAG_COMPRESSED = 32;
  
  static final byte FLAG_END_HEADERS = 4;
  
  static final byte FLAG_END_PUSH_PROMISE = 4;
  
  static final byte FLAG_END_STREAM = 1;
  
  static final byte FLAG_NONE = 0;
  
  static final byte FLAG_PADDED = 8;
  
  static final byte FLAG_PRIORITY = 32;
  
  private static final String[] FRAME_NAMES = new String[] { "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION" };
  
  static final int INITIAL_MAX_FRAME_SIZE = 16384;
  
  static final byte TYPE_CONTINUATION = 9;
  
  static final byte TYPE_DATA = 0;
  
  static final byte TYPE_GOAWAY = 7;
  
  static final byte TYPE_HEADERS = 1;
  
  static final byte TYPE_PING = 6;
  
  static final byte TYPE_PRIORITY = 2;
  
  static final byte TYPE_PUSH_PROMISE = 5;
  
  static final byte TYPE_RST_STREAM = 3;
  
  static final byte TYPE_SETTINGS = 4;
  
  static final byte TYPE_WINDOW_UPDATE = 8;
  
  static {
    FLAGS = new String[64];
    BINARY = new String[256];
    byte b2 = 0;
    byte b1 = 0;
    while (true) {
      byte b;
      String[] arrayOfString1 = BINARY;
      if (b1 < arrayOfString1.length) {
        arrayOfString1[b1] = Util.format("%8s", new Object[] { Integer.toBinaryString(b1) }).replace(' ', '0');
        b1++;
        continue;
      } 
      String[] arrayOfString2 = FLAGS;
      arrayOfString2[0] = "";
      arrayOfString2[1] = "END_STREAM";
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = 1;
      arrayOfString2[8] = "PADDED";
      for (b1 = 0; b1 < 1; b1++) {
        b = arrayOfInt[b1];
        arrayOfString2 = FLAGS;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FLAGS[b]);
        stringBuilder.append("|PADDED");
        arrayOfString2[b | 0x8] = stringBuilder.toString();
      } 
      arrayOfString2 = FLAGS;
      arrayOfString2[4] = "END_HEADERS";
      arrayOfString2[32] = "PRIORITY";
      arrayOfString2[36] = "END_HEADERS|PRIORITY";
      b1 = 0;
      while (true) {
        b = b2;
        if (b1 < 3) {
          (new int[3])[0] = 4;
          (new int[3])[1] = 32;
          (new int[3])[2] = 36;
          int i = (new int[3])[b1];
          for (b = 0; b < 1; b++) {
            int k = arrayOfInt[b];
            arrayOfString2 = FLAGS;
            int j = k | i;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(FLAGS[k]);
            stringBuilder2.append('|');
            stringBuilder2.append(FLAGS[i]);
            arrayOfString2[j] = stringBuilder2.toString();
            String[] arrayOfString = FLAGS;
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(FLAGS[k]);
            stringBuilder1.append('|');
            stringBuilder1.append(FLAGS[i]);
            stringBuilder1.append("|PADDED");
            arrayOfString[j | 0x8] = stringBuilder1.toString();
          } 
          b1++;
          continue;
        } 
        break;
      } 
      while (true) {
        String[] arrayOfString = FLAGS;
        if (b < arrayOfString.length) {
          if (arrayOfString[b] == null)
            arrayOfString[b] = BINARY[b]; 
          b++;
          continue;
        } 
        break;
      } 
      return;
    } 
  }
  
  static String formatFlags(byte paramByte1, byte paramByte2) {
    if (paramByte2 == 0)
      return ""; 
    if (paramByte1 != 2 && paramByte1 != 3)
      if (paramByte1 != 4 && paramByte1 != 6) {
        if (paramByte1 != 7 && paramByte1 != 8) {
          String str;
          String[] arrayOfString = FLAGS;
          if (paramByte2 < arrayOfString.length) {
            str = arrayOfString[paramByte2];
          } else {
            str = BINARY[paramByte2];
          } 
          return (paramByte1 == 5 && (paramByte2 & 0x4) != 0) ? str.replace("HEADERS", "PUSH_PROMISE") : ((paramByte1 == 0 && (paramByte2 & 0x20) != 0) ? str.replace("PRIORITY", "COMPRESSED") : str);
        } 
      } else {
        String str;
        if (paramByte2 == 1) {
          str = "ACK";
        } else {
          str = BINARY[paramByte2];
        } 
        return str;
      }  
    return BINARY[paramByte2];
  }
  
  static String frameLog(boolean paramBoolean, int paramInt1, int paramInt2, byte paramByte1, byte paramByte2) {
    String str1;
    String str2;
    String[] arrayOfString = FRAME_NAMES;
    if (paramByte1 < arrayOfString.length) {
      str1 = arrayOfString[paramByte1];
    } else {
      str1 = Util.format("0x%02x", new Object[] { Byte.valueOf(paramByte1) });
    } 
    String str3 = formatFlags(paramByte1, paramByte2);
    if (paramBoolean) {
      str2 = "<<";
    } else {
      str2 = ">>";
    } 
    return Util.format("%s 0x%08x %5d %-13s %s", new Object[] { str2, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), str1, str3 });
  }
  
  static IllegalArgumentException illegalArgument(String paramString, Object... paramVarArgs) {
    throw new IllegalArgumentException(Util.format(paramString, paramVarArgs));
  }
  
  static IOException ioException(String paramString, Object... paramVarArgs) throws IOException {
    throw new IOException(Util.format(paramString, paramVarArgs));
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\Http2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */