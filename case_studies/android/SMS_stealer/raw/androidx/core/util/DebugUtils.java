package androidx.core.util;

public class DebugUtils {
  public static void buildShortClassTag(Object paramObject, StringBuilder paramStringBuilder) {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull -> 14
    //   4: aload_1
    //   5: ldc 'null'
    //   7: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   10: pop
    //   11: goto -> 97
    //   14: aload_0
    //   15: invokevirtual getClass : ()Ljava/lang/Class;
    //   18: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   21: astore #4
    //   23: aload #4
    //   25: ifnull -> 39
    //   28: aload #4
    //   30: astore_3
    //   31: aload #4
    //   33: invokevirtual length : ()I
    //   36: ifgt -> 72
    //   39: aload_0
    //   40: invokevirtual getClass : ()Ljava/lang/Class;
    //   43: invokevirtual getName : ()Ljava/lang/String;
    //   46: astore #4
    //   48: aload #4
    //   50: bipush #46
    //   52: invokevirtual lastIndexOf : (I)I
    //   55: istore_2
    //   56: aload #4
    //   58: astore_3
    //   59: iload_2
    //   60: ifle -> 72
    //   63: aload #4
    //   65: iload_2
    //   66: iconst_1
    //   67: iadd
    //   68: invokevirtual substring : (I)Ljava/lang/String;
    //   71: astore_3
    //   72: aload_1
    //   73: aload_3
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: aload_1
    //   79: bipush #123
    //   81: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: aload_1
    //   86: aload_0
    //   87: invokestatic identityHashCode : (Ljava/lang/Object;)I
    //   90: invokestatic toHexString : (I)Ljava/lang/String;
    //   93: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: return
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\cor\\util\DebugUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */