package com.androidnetworking.common;

import com.androidnetworking.interfaces.ConnectionQualityChangeListener;

public class ConnectionClassManager {
  private static final long BANDWIDTH_LOWER_BOUND = 10L;
  
  private static final int BYTES_TO_BITS = 8;
  
  private static final int DEFAULT_GOOD_BANDWIDTH = 2000;
  
  private static final int DEFAULT_MODERATE_BANDWIDTH = 550;
  
  private static final int DEFAULT_POOR_BANDWIDTH = 150;
  
  private static final int DEFAULT_SAMPLES_TO_QUALITY_CHANGE = 5;
  
  private static final int MINIMUM_SAMPLES_TO_DECIDE_QUALITY = 2;
  
  private static ConnectionClassManager sInstance;
  
  private ConnectionQualityChangeListener mConnectionQualityChangeListener;
  
  private int mCurrentBandwidth = 0;
  
  private int mCurrentBandwidthForSampling = 0;
  
  private ConnectionQuality mCurrentConnectionQuality = ConnectionQuality.UNKNOWN;
  
  private int mCurrentNumberOfSample = 0;
  
  public static ConnectionClassManager getInstance() {
    // Byte code:
    //   0: getstatic com/androidnetworking/common/ConnectionClassManager.sInstance : Lcom/androidnetworking/common/ConnectionClassManager;
    //   3: ifnonnull -> 39
    //   6: ldc com/androidnetworking/common/ConnectionClassManager
    //   8: monitorenter
    //   9: getstatic com/androidnetworking/common/ConnectionClassManager.sInstance : Lcom/androidnetworking/common/ConnectionClassManager;
    //   12: ifnonnull -> 27
    //   15: new com/androidnetworking/common/ConnectionClassManager
    //   18: astore_0
    //   19: aload_0
    //   20: invokespecial <init> : ()V
    //   23: aload_0
    //   24: putstatic com/androidnetworking/common/ConnectionClassManager.sInstance : Lcom/androidnetworking/common/ConnectionClassManager;
    //   27: ldc com/androidnetworking/common/ConnectionClassManager
    //   29: monitorexit
    //   30: goto -> 39
    //   33: astore_0
    //   34: ldc com/androidnetworking/common/ConnectionClassManager
    //   36: monitorexit
    //   37: aload_0
    //   38: athrow
    //   39: getstatic com/androidnetworking/common/ConnectionClassManager.sInstance : Lcom/androidnetworking/common/ConnectionClassManager;
    //   42: areturn
    // Exception table:
    //   from	to	target	type
    //   9	27	33	finally
    //   27	30	33	finally
    //   34	37	33	finally
  }
  
  public static void shutDown() {
    if (sInstance != null)
      sInstance = null; 
  }
  
  public int getCurrentBandwidth() {
    return this.mCurrentBandwidth;
  }
  
  public ConnectionQuality getCurrentConnectionQuality() {
    return this.mCurrentConnectionQuality;
  }
  
  public void removeListener() {
    this.mConnectionQualityChangeListener = null;
  }
  
  public void setListener(ConnectionQualityChangeListener paramConnectionQualityChangeListener) {
    this.mConnectionQualityChangeListener = paramConnectionQualityChangeListener;
  }
  
  public void updateBandwidth(long paramLong1, long paramLong2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: lload_3
    //   3: lconst_0
    //   4: lcmp
    //   5: ifeq -> 290
    //   8: lload_1
    //   9: ldc2_w 20000
    //   12: lcmp
    //   13: iflt -> 290
    //   16: lload_1
    //   17: l2d
    //   18: dconst_1
    //   19: dmul
    //   20: lload_3
    //   21: l2d
    //   22: ddiv
    //   23: ldc2_w 8.0
    //   26: dmul
    //   27: dstore #5
    //   29: dload #5
    //   31: ldc2_w 10.0
    //   34: dcmpg
    //   35: ifge -> 41
    //   38: goto -> 290
    //   41: aload_0
    //   42: aload_0
    //   43: getfield mCurrentBandwidthForSampling : I
    //   46: aload_0
    //   47: getfield mCurrentNumberOfSample : I
    //   50: imul
    //   51: i2d
    //   52: dload #5
    //   54: dadd
    //   55: aload_0
    //   56: getfield mCurrentNumberOfSample : I
    //   59: iconst_1
    //   60: iadd
    //   61: i2d
    //   62: ddiv
    //   63: d2i
    //   64: putfield mCurrentBandwidthForSampling : I
    //   67: aload_0
    //   68: getfield mCurrentNumberOfSample : I
    //   71: iconst_1
    //   72: iadd
    //   73: istore #7
    //   75: aload_0
    //   76: iload #7
    //   78: putfield mCurrentNumberOfSample : I
    //   81: iload #7
    //   83: iconst_5
    //   84: if_icmpeq -> 105
    //   87: aload_0
    //   88: getfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   91: getstatic com/androidnetworking/common/ConnectionQuality.UNKNOWN : Lcom/androidnetworking/common/ConnectionQuality;
    //   94: if_acmpne -> 280
    //   97: aload_0
    //   98: getfield mCurrentNumberOfSample : I
    //   101: iconst_2
    //   102: if_icmpne -> 280
    //   105: aload_0
    //   106: getfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   109: astore #8
    //   111: aload_0
    //   112: aload_0
    //   113: getfield mCurrentBandwidthForSampling : I
    //   116: putfield mCurrentBandwidth : I
    //   119: aload_0
    //   120: getfield mCurrentBandwidthForSampling : I
    //   123: ifgt -> 136
    //   126: aload_0
    //   127: getstatic com/androidnetworking/common/ConnectionQuality.UNKNOWN : Lcom/androidnetworking/common/ConnectionQuality;
    //   130: putfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   133: goto -> 213
    //   136: aload_0
    //   137: getfield mCurrentBandwidthForSampling : I
    //   140: sipush #150
    //   143: if_icmpge -> 156
    //   146: aload_0
    //   147: getstatic com/androidnetworking/common/ConnectionQuality.POOR : Lcom/androidnetworking/common/ConnectionQuality;
    //   150: putfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   153: goto -> 213
    //   156: aload_0
    //   157: getfield mCurrentBandwidthForSampling : I
    //   160: sipush #550
    //   163: if_icmpge -> 176
    //   166: aload_0
    //   167: getstatic com/androidnetworking/common/ConnectionQuality.MODERATE : Lcom/androidnetworking/common/ConnectionQuality;
    //   170: putfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   173: goto -> 213
    //   176: aload_0
    //   177: getfield mCurrentBandwidthForSampling : I
    //   180: sipush #2000
    //   183: if_icmpge -> 196
    //   186: aload_0
    //   187: getstatic com/androidnetworking/common/ConnectionQuality.GOOD : Lcom/androidnetworking/common/ConnectionQuality;
    //   190: putfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   193: goto -> 213
    //   196: aload_0
    //   197: getfield mCurrentBandwidthForSampling : I
    //   200: sipush #2000
    //   203: if_icmple -> 213
    //   206: aload_0
    //   207: getstatic com/androidnetworking/common/ConnectionQuality.EXCELLENT : Lcom/androidnetworking/common/ConnectionQuality;
    //   210: putfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   213: aload_0
    //   214: getfield mCurrentNumberOfSample : I
    //   217: iconst_5
    //   218: if_icmpne -> 231
    //   221: aload_0
    //   222: iconst_0
    //   223: putfield mCurrentBandwidthForSampling : I
    //   226: aload_0
    //   227: iconst_0
    //   228: putfield mCurrentNumberOfSample : I
    //   231: aload_0
    //   232: getfield mCurrentConnectionQuality : Lcom/androidnetworking/common/ConnectionQuality;
    //   235: aload #8
    //   237: if_acmpeq -> 280
    //   240: aload_0
    //   241: getfield mConnectionQualityChangeListener : Lcom/androidnetworking/interfaces/ConnectionQualityChangeListener;
    //   244: ifnull -> 280
    //   247: invokestatic getInstance : ()Lcom/androidnetworking/core/Core;
    //   250: invokevirtual getExecutorSupplier : ()Lcom/androidnetworking/core/ExecutorSupplier;
    //   253: invokeinterface forMainThreadTasks : ()Ljava/util/concurrent/Executor;
    //   258: astore #9
    //   260: new com/androidnetworking/common/ConnectionClassManager$1
    //   263: astore #8
    //   265: aload #8
    //   267: aload_0
    //   268: invokespecial <init> : (Lcom/androidnetworking/common/ConnectionClassManager;)V
    //   271: aload #9
    //   273: aload #8
    //   275: invokeinterface execute : (Ljava/lang/Runnable;)V
    //   280: aload_0
    //   281: monitorexit
    //   282: return
    //   283: astore #8
    //   285: aload_0
    //   286: monitorexit
    //   287: aload #8
    //   289: athrow
    //   290: aload_0
    //   291: monitorexit
    //   292: return
    // Exception table:
    //   from	to	target	type
    //   41	81	283	finally
    //   87	105	283	finally
    //   105	133	283	finally
    //   136	153	283	finally
    //   156	173	283	finally
    //   176	193	283	finally
    //   196	213	283	finally
    //   213	231	283	finally
    //   231	280	283	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\common\ConnectionClassManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */