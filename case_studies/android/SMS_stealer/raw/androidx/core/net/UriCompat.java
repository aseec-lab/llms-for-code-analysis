package androidx.core.net;

import android.net.Uri;

public final class UriCompat {
  public static String toSafeString(Uri paramUri) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getScheme : ()Ljava/lang/String;
    //   4: astore #5
    //   6: aload_0
    //   7: invokevirtual getSchemeSpecificPart : ()Ljava/lang/String;
    //   10: astore #4
    //   12: aload #4
    //   14: astore_3
    //   15: aload #5
    //   17: ifnull -> 333
    //   20: aload #5
    //   22: ldc 'tel'
    //   24: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   27: ifne -> 238
    //   30: aload #5
    //   32: ldc 'sip'
    //   34: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   37: ifne -> 238
    //   40: aload #5
    //   42: ldc 'sms'
    //   44: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   47: ifne -> 238
    //   50: aload #5
    //   52: ldc 'smsto'
    //   54: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   57: ifne -> 238
    //   60: aload #5
    //   62: ldc 'mailto'
    //   64: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   67: ifne -> 238
    //   70: aload #5
    //   72: ldc 'nfc'
    //   74: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   77: ifeq -> 83
    //   80: goto -> 238
    //   83: aload #5
    //   85: ldc 'http'
    //   87: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   90: ifne -> 126
    //   93: aload #5
    //   95: ldc 'https'
    //   97: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   100: ifne -> 126
    //   103: aload #5
    //   105: ldc 'ftp'
    //   107: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   110: ifne -> 126
    //   113: aload #4
    //   115: astore_3
    //   116: aload #5
    //   118: ldc 'rtsp'
    //   120: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   123: ifeq -> 333
    //   126: new java/lang/StringBuilder
    //   129: dup
    //   130: invokespecial <init> : ()V
    //   133: astore #6
    //   135: aload #6
    //   137: ldc '//'
    //   139: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: pop
    //   143: aload_0
    //   144: invokevirtual getHost : ()Ljava/lang/String;
    //   147: astore_3
    //   148: ldc ''
    //   150: astore #4
    //   152: aload_3
    //   153: ifnull -> 164
    //   156: aload_0
    //   157: invokevirtual getHost : ()Ljava/lang/String;
    //   160: astore_3
    //   161: goto -> 167
    //   164: ldc ''
    //   166: astore_3
    //   167: aload #6
    //   169: aload_3
    //   170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: pop
    //   174: aload #4
    //   176: astore_3
    //   177: aload_0
    //   178: invokevirtual getPort : ()I
    //   181: iconst_m1
    //   182: if_icmpeq -> 214
    //   185: new java/lang/StringBuilder
    //   188: dup
    //   189: invokespecial <init> : ()V
    //   192: astore_3
    //   193: aload_3
    //   194: ldc ':'
    //   196: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: aload_3
    //   201: aload_0
    //   202: invokevirtual getPort : ()I
    //   205: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   208: pop
    //   209: aload_3
    //   210: invokevirtual toString : ()Ljava/lang/String;
    //   213: astore_3
    //   214: aload #6
    //   216: aload_3
    //   217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: pop
    //   221: aload #6
    //   223: ldc '/...'
    //   225: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: pop
    //   229: aload #6
    //   231: invokevirtual toString : ()Ljava/lang/String;
    //   234: astore_3
    //   235: goto -> 333
    //   238: new java/lang/StringBuilder
    //   241: dup
    //   242: bipush #64
    //   244: invokespecial <init> : (I)V
    //   247: astore_0
    //   248: aload_0
    //   249: aload #5
    //   251: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   254: pop
    //   255: aload_0
    //   256: bipush #58
    //   258: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: aload #4
    //   264: ifnull -> 328
    //   267: iconst_0
    //   268: istore_2
    //   269: iload_2
    //   270: aload #4
    //   272: invokevirtual length : ()I
    //   275: if_icmpge -> 328
    //   278: aload #4
    //   280: iload_2
    //   281: invokevirtual charAt : (I)C
    //   284: istore_1
    //   285: iload_1
    //   286: bipush #45
    //   288: if_icmpeq -> 316
    //   291: iload_1
    //   292: bipush #64
    //   294: if_icmpeq -> 316
    //   297: iload_1
    //   298: bipush #46
    //   300: if_icmpne -> 306
    //   303: goto -> 316
    //   306: aload_0
    //   307: bipush #120
    //   309: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   312: pop
    //   313: goto -> 322
    //   316: aload_0
    //   317: iload_1
    //   318: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   321: pop
    //   322: iinc #2, 1
    //   325: goto -> 269
    //   328: aload_0
    //   329: invokevirtual toString : ()Ljava/lang/String;
    //   332: areturn
    //   333: new java/lang/StringBuilder
    //   336: dup
    //   337: bipush #64
    //   339: invokespecial <init> : (I)V
    //   342: astore_0
    //   343: aload #5
    //   345: ifnull -> 362
    //   348: aload_0
    //   349: aload #5
    //   351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   354: pop
    //   355: aload_0
    //   356: bipush #58
    //   358: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   361: pop
    //   362: aload_3
    //   363: ifnull -> 372
    //   366: aload_0
    //   367: aload_3
    //   368: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: pop
    //   372: aload_0
    //   373: invokevirtual toString : ()Ljava/lang/String;
    //   376: areturn
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\net\UriCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */