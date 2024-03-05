package okhttp3.internal.publicsuffix;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.IDN;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

public final class PublicSuffixDatabase {
  private static final String[] EMPTY_RULE;
  
  private static final byte EXCEPTION_MARKER = 33;
  
  private static final String[] PREVAILING_RULE;
  
  public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
  
  private static final byte[] WILDCARD_LABEL = new byte[] { 42 };
  
  private static final PublicSuffixDatabase instance;
  
  private final AtomicBoolean listRead = new AtomicBoolean(false);
  
  private byte[] publicSuffixExceptionListBytes;
  
  private byte[] publicSuffixListBytes;
  
  private final CountDownLatch readCompleteLatch = new CountDownLatch(1);
  
  static {
    EMPTY_RULE = new String[0];
    PREVAILING_RULE = new String[] { "*" };
    instance = new PublicSuffixDatabase();
  }
  
  private static String binarySearchBytes(byte[] paramArrayOfbyte, byte[][] paramArrayOfbyte1, int paramInt) {
    int j = paramArrayOfbyte.length;
    int i = 0;
    label55: while (i < j) {
      int m;
      int i1;
      int i2;
      int k;
      for (k = (i + j) / 2; k > -1 && paramArrayOfbyte[k] != 10; k--);
      int n = k + 1;
      k = 1;
      while (true) {
        i1 = n + k;
        if (paramArrayOfbyte[i1] != 10) {
          k++;
          continue;
        } 
        i2 = i1 - n;
        int i3 = paramInt;
        m = 0;
        k = 0;
        byte b = 0;
        while (true) {
          int i4;
          if (m) {
            i4 = 46;
            m = 0;
          } else {
            byte b1 = paramArrayOfbyte1[i3][k];
            i4 = b1 & 0xFF;
          } 
          i4 -= paramArrayOfbyte[n + b] & 0xFF;
          if (i4 == 0) {
            b++;
            k++;
            if (b != i2)
              if ((paramArrayOfbyte1[i3]).length == k) {
                if (i3 != paramArrayOfbyte1.length - 1) {
                  i3++;
                  m = 1;
                  k = -1;
                  continue;
                } 
              } else {
                continue;
              }  
          } 
          if (i4 < 0)
            continue; 
          if (i4 > 0)
            continue; 
          m = i2 - b;
          for (k = (paramArrayOfbyte1[i3]).length - k; ++i3 < paramArrayOfbyte1.length; k += (paramArrayOfbyte1[i3]).length);
          break;
        } 
        return (String)paramArrayOfbyte;
      } 
      if (k < m) {
        j = n - 1;
        continue label55;
      } 
      if (k > m) {
        i = i1 + 1;
        continue label55;
      } 
      String str = new String(paramArrayOfbyte, n, i2, Util.UTF_8);
    } 
    return null;
  }
  
  private String[] findMatchingRule(String[] paramArrayOfString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield listRead : Ljava/util/concurrent/atomic/AtomicBoolean;
    //   4: invokevirtual get : ()Z
    //   7: istore #5
    //   9: iconst_0
    //   10: istore_3
    //   11: iload #5
    //   13: ifne -> 35
    //   16: aload_0
    //   17: getfield listRead : Ljava/util/concurrent/atomic/AtomicBoolean;
    //   20: iconst_0
    //   21: iconst_1
    //   22: invokevirtual compareAndSet : (ZZ)Z
    //   25: ifeq -> 35
    //   28: aload_0
    //   29: invokespecial readTheListUninterruptibly : ()V
    //   32: goto -> 42
    //   35: aload_0
    //   36: getfield readCompleteLatch : Ljava/util/concurrent/CountDownLatch;
    //   39: invokevirtual await : ()V
    //   42: aload_0
    //   43: monitorenter
    //   44: aload_0
    //   45: getfield publicSuffixListBytes : [B
    //   48: ifnull -> 346
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_1
    //   54: arraylength
    //   55: istore #4
    //   57: iload #4
    //   59: anewarray [B
    //   62: astore #9
    //   64: iconst_0
    //   65: istore_2
    //   66: iload_2
    //   67: aload_1
    //   68: arraylength
    //   69: if_icmpge -> 91
    //   72: aload #9
    //   74: iload_2
    //   75: aload_1
    //   76: iload_2
    //   77: aaload
    //   78: getstatic okhttp3/internal/Util.UTF_8 : Ljava/nio/charset/Charset;
    //   81: invokevirtual getBytes : (Ljava/nio/charset/Charset;)[B
    //   84: aastore
    //   85: iinc #2, 1
    //   88: goto -> 66
    //   91: iconst_0
    //   92: istore_2
    //   93: aconst_null
    //   94: astore #8
    //   96: iload_2
    //   97: iload #4
    //   99: if_icmpge -> 126
    //   102: aload_0
    //   103: getfield publicSuffixListBytes : [B
    //   106: aload #9
    //   108: iload_2
    //   109: invokestatic binarySearchBytes : ([B[[BI)Ljava/lang/String;
    //   112: astore_1
    //   113: aload_1
    //   114: ifnull -> 120
    //   117: goto -> 128
    //   120: iinc #2, 1
    //   123: goto -> 93
    //   126: aconst_null
    //   127: astore_1
    //   128: iload #4
    //   130: iconst_1
    //   131: if_icmple -> 188
    //   134: aload #9
    //   136: invokevirtual clone : ()Ljava/lang/Object;
    //   139: checkcast [[B
    //   142: astore #7
    //   144: iconst_0
    //   145: istore_2
    //   146: iload_2
    //   147: aload #7
    //   149: arraylength
    //   150: iconst_1
    //   151: isub
    //   152: if_icmpge -> 188
    //   155: aload #7
    //   157: iload_2
    //   158: getstatic okhttp3/internal/publicsuffix/PublicSuffixDatabase.WILDCARD_LABEL : [B
    //   161: aastore
    //   162: aload_0
    //   163: getfield publicSuffixListBytes : [B
    //   166: aload #7
    //   168: iload_2
    //   169: invokestatic binarySearchBytes : ([B[[BI)Ljava/lang/String;
    //   172: astore #6
    //   174: aload #6
    //   176: ifnull -> 182
    //   179: goto -> 191
    //   182: iinc #2, 1
    //   185: goto -> 146
    //   188: aconst_null
    //   189: astore #6
    //   191: aload #8
    //   193: astore #7
    //   195: aload #6
    //   197: ifnull -> 240
    //   200: iload_3
    //   201: istore_2
    //   202: aload #8
    //   204: astore #7
    //   206: iload_2
    //   207: iload #4
    //   209: iconst_1
    //   210: isub
    //   211: if_icmpge -> 240
    //   214: aload_0
    //   215: getfield publicSuffixExceptionListBytes : [B
    //   218: aload #9
    //   220: iload_2
    //   221: invokestatic binarySearchBytes : ([B[[BI)Ljava/lang/String;
    //   224: astore #7
    //   226: aload #7
    //   228: ifnull -> 234
    //   231: goto -> 240
    //   234: iinc #2, 1
    //   237: goto -> 202
    //   240: aload #7
    //   242: ifnull -> 277
    //   245: new java/lang/StringBuilder
    //   248: dup
    //   249: invokespecial <init> : ()V
    //   252: astore_1
    //   253: aload_1
    //   254: ldc '!'
    //   256: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   259: pop
    //   260: aload_1
    //   261: aload #7
    //   263: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   266: pop
    //   267: aload_1
    //   268: invokevirtual toString : ()Ljava/lang/String;
    //   271: ldc '\.'
    //   273: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   276: areturn
    //   277: aload_1
    //   278: ifnonnull -> 290
    //   281: aload #6
    //   283: ifnonnull -> 290
    //   286: getstatic okhttp3/internal/publicsuffix/PublicSuffixDatabase.PREVAILING_RULE : [Ljava/lang/String;
    //   289: areturn
    //   290: aload_1
    //   291: ifnull -> 304
    //   294: aload_1
    //   295: ldc '\.'
    //   297: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   300: astore_1
    //   301: goto -> 308
    //   304: getstatic okhttp3/internal/publicsuffix/PublicSuffixDatabase.EMPTY_RULE : [Ljava/lang/String;
    //   307: astore_1
    //   308: aload #6
    //   310: ifnull -> 325
    //   313: aload #6
    //   315: ldc '\.'
    //   317: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   320: astore #6
    //   322: goto -> 330
    //   325: getstatic okhttp3/internal/publicsuffix/PublicSuffixDatabase.EMPTY_RULE : [Ljava/lang/String;
    //   328: astore #6
    //   330: aload_1
    //   331: arraylength
    //   332: aload #6
    //   334: arraylength
    //   335: if_icmple -> 341
    //   338: goto -> 344
    //   341: aload #6
    //   343: astore_1
    //   344: aload_1
    //   345: areturn
    //   346: new java/lang/IllegalStateException
    //   349: astore_1
    //   350: aload_1
    //   351: ldc 'Unable to load publicsuffixes.gz resource from the classpath.'
    //   353: invokespecial <init> : (Ljava/lang/String;)V
    //   356: aload_1
    //   357: athrow
    //   358: astore_1
    //   359: aload_0
    //   360: monitorexit
    //   361: aload_1
    //   362: athrow
    //   363: astore #6
    //   365: goto -> 42
    // Exception table:
    //   from	to	target	type
    //   35	42	363	java/lang/InterruptedException
    //   44	53	358	finally
    //   346	358	358	finally
    //   359	361	358	finally
  }
  
  public static PublicSuffixDatabase get() {
    return instance;
  }
  
  private void readTheList() throws IOException {
    // Byte code:
    //   0: ldc okhttp3/internal/publicsuffix/PublicSuffixDatabase
    //   2: ldc 'publicsuffixes.gz'
    //   4: invokevirtual getResourceAsStream : (Ljava/lang/String;)Ljava/io/InputStream;
    //   7: astore_1
    //   8: aload_1
    //   9: ifnonnull -> 13
    //   12: return
    //   13: new okio/GzipSource
    //   16: dup
    //   17: aload_1
    //   18: invokestatic source : (Ljava/io/InputStream;)Lokio/Source;
    //   21: invokespecial <init> : (Lokio/Source;)V
    //   24: invokestatic buffer : (Lokio/Source;)Lokio/BufferedSource;
    //   27: astore_1
    //   28: aload_1
    //   29: invokeinterface readInt : ()I
    //   34: newarray byte
    //   36: astore_2
    //   37: aload_1
    //   38: aload_2
    //   39: invokeinterface readFully : ([B)V
    //   44: aload_1
    //   45: invokeinterface readInt : ()I
    //   50: newarray byte
    //   52: astore_3
    //   53: aload_1
    //   54: aload_3
    //   55: invokeinterface readFully : ([B)V
    //   60: aload_1
    //   61: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   64: aload_0
    //   65: monitorenter
    //   66: aload_0
    //   67: aload_2
    //   68: putfield publicSuffixListBytes : [B
    //   71: aload_0
    //   72: aload_3
    //   73: putfield publicSuffixExceptionListBytes : [B
    //   76: aload_0
    //   77: monitorexit
    //   78: aload_0
    //   79: getfield readCompleteLatch : Ljava/util/concurrent/CountDownLatch;
    //   82: invokevirtual countDown : ()V
    //   85: return
    //   86: astore_1
    //   87: aload_0
    //   88: monitorexit
    //   89: aload_1
    //   90: athrow
    //   91: astore_2
    //   92: aload_1
    //   93: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   96: aload_2
    //   97: athrow
    // Exception table:
    //   from	to	target	type
    //   28	60	91	finally
    //   66	78	86	finally
    //   87	89	86	finally
  }
  
  private void readTheListUninterruptibly() {
    boolean bool = false;
    while (true) {
      try {
        readTheList();
        return;
      } catch (InterruptedIOException interruptedIOException) {
      
      } catch (IOException iOException) {
        Platform.get().log(5, "Failed to read public suffix list", iOException);
        return;
      } finally {
        if (bool)
          Thread.currentThread().interrupt(); 
      } 
    } 
  }
  
  public String getEffectiveTldPlusOne(String paramString) {
    if (paramString != null) {
      int i;
      int j;
      String[] arrayOfString2 = IDN.toUnicode(paramString).split("\\.");
      String[] arrayOfString3 = findMatchingRule(arrayOfString2);
      if (arrayOfString2.length == arrayOfString3.length && arrayOfString3[0].charAt(0) != '!')
        return null; 
      if (arrayOfString3[0].charAt(0) == '!') {
        i = arrayOfString2.length;
        j = arrayOfString3.length;
      } else {
        i = arrayOfString2.length;
        j = arrayOfString3.length + 1;
      } 
      i -= j;
      StringBuilder stringBuilder = new StringBuilder();
      String[] arrayOfString1 = paramString.split("\\.");
      while (i < arrayOfString1.length) {
        stringBuilder.append(arrayOfString1[i]);
        stringBuilder.append('.');
        i++;
      } 
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
      return stringBuilder.toString();
    } 
    throw new NullPointerException("domain == null");
  }
  
  void setListBytes(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.publicSuffixListBytes = paramArrayOfbyte1;
    this.publicSuffixExceptionListBytes = paramArrayOfbyte2;
    this.listRead.set(true);
    this.readCompleteLatch.countDown();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\publicsuffix\PublicSuffixDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */