package okhttp3.internal.cache2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

final class Relay {
  private static final long FILE_HEADER_SIZE = 32L;
  
  static final ByteString PREFIX_CLEAN = ByteString.encodeUtf8("OkHttp cache v1\n");
  
  static final ByteString PREFIX_DIRTY = ByteString.encodeUtf8("OkHttp DIRTY :(\n");
  
  private static final int SOURCE_FILE = 2;
  
  private static final int SOURCE_UPSTREAM = 1;
  
  final Buffer buffer;
  
  final long bufferMaxSize;
  
  boolean complete;
  
  RandomAccessFile file;
  
  private final ByteString metadata;
  
  int sourceCount;
  
  Source upstream;
  
  final Buffer upstreamBuffer;
  
  long upstreamPos;
  
  Thread upstreamReader;
  
  private Relay(RandomAccessFile paramRandomAccessFile, Source paramSource, long paramLong1, ByteString paramByteString, long paramLong2) {
    boolean bool;
    this.upstreamBuffer = new Buffer();
    this.buffer = new Buffer();
    this.file = paramRandomAccessFile;
    this.upstream = paramSource;
    if (paramSource == null) {
      bool = true;
    } else {
      bool = false;
    } 
    this.complete = bool;
    this.upstreamPos = paramLong1;
    this.metadata = paramByteString;
    this.bufferMaxSize = paramLong2;
  }
  
  public static Relay edit(File paramFile, Source paramSource, ByteString paramByteString, long paramLong) throws IOException {
    RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile, "rw");
    Relay relay = new Relay(randomAccessFile, paramSource, 0L, paramByteString, paramLong);
    randomAccessFile.setLength(0L);
    relay.writeHeader(PREFIX_DIRTY, -1L, -1L);
    return relay;
  }
  
  public static Relay read(File paramFile) throws IOException {
    RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile, "rw");
    FileOperator fileOperator = new FileOperator(randomAccessFile.getChannel());
    Buffer buffer = new Buffer();
    fileOperator.read(0L, buffer, 32L);
    if (buffer.readByteString(PREFIX_CLEAN.size()).equals(PREFIX_CLEAN)) {
      long l2 = buffer.readLong();
      long l1 = buffer.readLong();
      buffer = new Buffer();
      fileOperator.read(l2 + 32L, buffer, l1);
      return new Relay(randomAccessFile, null, l2, buffer.readByteString(), 0L);
    } 
    throw new IOException("unreadable cache file");
  }
  
  private void writeHeader(ByteString paramByteString, long paramLong1, long paramLong2) throws IOException {
    Buffer buffer = new Buffer();
    buffer.write(paramByteString);
    buffer.writeLong(paramLong1);
    buffer.writeLong(paramLong2);
    if (buffer.size() == 32L) {
      (new FileOperator(this.file.getChannel())).write(0L, buffer, 32L);
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  private void writeMetadata(long paramLong) throws IOException {
    Buffer buffer = new Buffer();
    buffer.write(this.metadata);
    (new FileOperator(this.file.getChannel())).write(32L + paramLong, buffer, this.metadata.size());
  }
  
  void commit(long paramLong) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: lload_1
    //   2: invokespecial writeMetadata : (J)V
    //   5: aload_0
    //   6: getfield file : Ljava/io/RandomAccessFile;
    //   9: invokevirtual getChannel : ()Ljava/nio/channels/FileChannel;
    //   12: iconst_0
    //   13: invokevirtual force : (Z)V
    //   16: aload_0
    //   17: getstatic okhttp3/internal/cache2/Relay.PREFIX_CLEAN : Lokio/ByteString;
    //   20: lload_1
    //   21: aload_0
    //   22: getfield metadata : Lokio/ByteString;
    //   25: invokevirtual size : ()I
    //   28: i2l
    //   29: invokespecial writeHeader : (Lokio/ByteString;JJ)V
    //   32: aload_0
    //   33: getfield file : Ljava/io/RandomAccessFile;
    //   36: invokevirtual getChannel : ()Ljava/nio/channels/FileChannel;
    //   39: iconst_0
    //   40: invokevirtual force : (Z)V
    //   43: aload_0
    //   44: monitorenter
    //   45: aload_0
    //   46: iconst_1
    //   47: putfield complete : Z
    //   50: aload_0
    //   51: monitorexit
    //   52: aload_0
    //   53: getfield upstream : Lokio/Source;
    //   56: invokestatic closeQuietly : (Ljava/io/Closeable;)V
    //   59: aload_0
    //   60: aconst_null
    //   61: putfield upstream : Lokio/Source;
    //   64: return
    //   65: astore_3
    //   66: aload_0
    //   67: monitorexit
    //   68: aload_3
    //   69: athrow
    // Exception table:
    //   from	to	target	type
    //   45	52	65	finally
    //   66	68	65	finally
  }
  
  boolean isClosed() {
    boolean bool;
    if (this.file == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public ByteString metadata() {
    return this.metadata;
  }
  
  public Source newSource() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield file : Ljava/io/RandomAccessFile;
    //   6: ifnonnull -> 13
    //   9: aload_0
    //   10: monitorexit
    //   11: aconst_null
    //   12: areturn
    //   13: aload_0
    //   14: aload_0
    //   15: getfield sourceCount : I
    //   18: iconst_1
    //   19: iadd
    //   20: putfield sourceCount : I
    //   23: aload_0
    //   24: monitorexit
    //   25: new okhttp3/internal/cache2/Relay$RelaySource
    //   28: dup
    //   29: aload_0
    //   30: invokespecial <init> : (Lokhttp3/internal/cache2/Relay;)V
    //   33: areturn
    //   34: astore_1
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_1
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	34	finally
    //   13	25	34	finally
    //   35	37	34	finally
  }
  
  class RelaySource implements Source {
    private FileOperator fileOperator = new FileOperator(Relay.this.file.getChannel());
    
    private long sourcePos;
    
    final Relay this$0;
    
    private final Timeout timeout = new Timeout();
    
    public void close() throws IOException {
      if (this.fileOperator == null)
        return; 
      null = null;
      this.fileOperator = null;
      synchronized (Relay.this) {
        Relay relay = Relay.this;
        relay.sourceCount--;
        if (Relay.this.sourceCount == 0) {
          null = Relay.this.file;
          Relay.this.file = null;
        } 
        if (null != null)
          Util.closeQuietly(null); 
        return;
      } 
    }
    
    public long read(Buffer param1Buffer, long param1Long) throws IOException {
      // Byte code:
      //   0: aload_0
      //   1: getfield fileOperator : Lokhttp3/internal/cache2/FileOperator;
      //   4: ifnull -> 541
      //   7: aload_0
      //   8: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   11: astore #9
      //   13: aload #9
      //   15: monitorenter
      //   16: aload_0
      //   17: getfield sourcePos : J
      //   20: lstore #7
      //   22: aload_0
      //   23: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   26: getfield upstreamPos : J
      //   29: lstore #5
      //   31: lload #7
      //   33: lload #5
      //   35: lcmp
      //   36: ifne -> 99
      //   39: aload_0
      //   40: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   43: getfield complete : Z
      //   46: ifeq -> 56
      //   49: aload #9
      //   51: monitorexit
      //   52: ldc2_w -1
      //   55: lreturn
      //   56: aload_0
      //   57: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   60: getfield upstreamReader : Ljava/lang/Thread;
      //   63: ifnull -> 80
      //   66: aload_0
      //   67: getfield timeout : Lokio/Timeout;
      //   70: aload_0
      //   71: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   74: invokevirtual waitUntilNotified : (Ljava/lang/Object;)V
      //   77: goto -> 16
      //   80: aload_0
      //   81: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   84: invokestatic currentThread : ()Ljava/lang/Thread;
      //   87: putfield upstreamReader : Ljava/lang/Thread;
      //   90: iconst_1
      //   91: istore #4
      //   93: aload #9
      //   95: monitorexit
      //   96: goto -> 130
      //   99: lload #5
      //   101: aload_0
      //   102: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   105: getfield buffer : Lokio/Buffer;
      //   108: invokevirtual size : ()J
      //   111: lsub
      //   112: lstore #7
      //   114: aload_0
      //   115: getfield sourcePos : J
      //   118: lload #7
      //   120: lcmp
      //   121: ifge -> 488
      //   124: aload #9
      //   126: monitorexit
      //   127: iconst_2
      //   128: istore #4
      //   130: iload #4
      //   132: iconst_2
      //   133: if_icmpne -> 177
      //   136: lload_2
      //   137: lload #5
      //   139: aload_0
      //   140: getfield sourcePos : J
      //   143: lsub
      //   144: invokestatic min : (JJ)J
      //   147: lstore_2
      //   148: aload_0
      //   149: getfield fileOperator : Lokhttp3/internal/cache2/FileOperator;
      //   152: aload_0
      //   153: getfield sourcePos : J
      //   156: ldc2_w 32
      //   159: ladd
      //   160: aload_1
      //   161: lload_2
      //   162: invokevirtual read : (JLokio/Buffer;J)V
      //   165: aload_0
      //   166: aload_0
      //   167: getfield sourcePos : J
      //   170: lload_2
      //   171: ladd
      //   172: putfield sourcePos : J
      //   175: lload_2
      //   176: lreturn
      //   177: aload_0
      //   178: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   181: getfield upstream : Lokio/Source;
      //   184: aload_0
      //   185: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   188: getfield upstreamBuffer : Lokio/Buffer;
      //   191: aload_0
      //   192: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   195: getfield bufferMaxSize : J
      //   198: invokeinterface read : (Lokio/Buffer;J)J
      //   203: lstore #7
      //   205: lload #7
      //   207: ldc2_w -1
      //   210: lcmp
      //   211: ifne -> 260
      //   214: aload_0
      //   215: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   218: lload #5
      //   220: invokevirtual commit : (J)V
      //   223: aload_0
      //   224: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   227: astore #9
      //   229: aload #9
      //   231: monitorenter
      //   232: aload_0
      //   233: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   236: aconst_null
      //   237: putfield upstreamReader : Ljava/lang/Thread;
      //   240: aload_0
      //   241: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   244: invokevirtual notifyAll : ()V
      //   247: aload #9
      //   249: monitorexit
      //   250: ldc2_w -1
      //   253: lreturn
      //   254: astore_1
      //   255: aload #9
      //   257: monitorexit
      //   258: aload_1
      //   259: athrow
      //   260: lload #7
      //   262: lload_2
      //   263: invokestatic min : (JJ)J
      //   266: lstore_2
      //   267: aload_0
      //   268: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   271: getfield upstreamBuffer : Lokio/Buffer;
      //   274: aload_1
      //   275: lconst_0
      //   276: lload_2
      //   277: invokevirtual copyTo : (Lokio/Buffer;JJ)Lokio/Buffer;
      //   280: pop
      //   281: aload_0
      //   282: aload_0
      //   283: getfield sourcePos : J
      //   286: lload_2
      //   287: ladd
      //   288: putfield sourcePos : J
      //   291: aload_0
      //   292: getfield fileOperator : Lokhttp3/internal/cache2/FileOperator;
      //   295: lload #5
      //   297: ldc2_w 32
      //   300: ladd
      //   301: aload_0
      //   302: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   305: getfield upstreamBuffer : Lokio/Buffer;
      //   308: invokevirtual clone : ()Lokio/Buffer;
      //   311: lload #7
      //   313: invokevirtual write : (JLokio/Buffer;J)V
      //   316: aload_0
      //   317: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   320: astore_1
      //   321: aload_1
      //   322: monitorenter
      //   323: aload_0
      //   324: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   327: getfield buffer : Lokio/Buffer;
      //   330: aload_0
      //   331: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   334: getfield upstreamBuffer : Lokio/Buffer;
      //   337: lload #7
      //   339: invokevirtual write : (Lokio/Buffer;J)V
      //   342: aload_0
      //   343: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   346: getfield buffer : Lokio/Buffer;
      //   349: invokevirtual size : ()J
      //   352: aload_0
      //   353: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   356: getfield bufferMaxSize : J
      //   359: lcmp
      //   360: ifle -> 391
      //   363: aload_0
      //   364: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   367: getfield buffer : Lokio/Buffer;
      //   370: aload_0
      //   371: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   374: getfield buffer : Lokio/Buffer;
      //   377: invokevirtual size : ()J
      //   380: aload_0
      //   381: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   384: getfield bufferMaxSize : J
      //   387: lsub
      //   388: invokevirtual skip : (J)V
      //   391: aload_0
      //   392: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   395: astore #9
      //   397: aload #9
      //   399: aload #9
      //   401: getfield upstreamPos : J
      //   404: lload #7
      //   406: ladd
      //   407: putfield upstreamPos : J
      //   410: aload_1
      //   411: monitorexit
      //   412: aload_0
      //   413: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   416: astore_1
      //   417: aload_1
      //   418: monitorenter
      //   419: aload_0
      //   420: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   423: aconst_null
      //   424: putfield upstreamReader : Ljava/lang/Thread;
      //   427: aload_0
      //   428: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   431: invokevirtual notifyAll : ()V
      //   434: aload_1
      //   435: monitorexit
      //   436: lload_2
      //   437: lreturn
      //   438: astore #9
      //   440: aload_1
      //   441: monitorexit
      //   442: aload #9
      //   444: athrow
      //   445: astore #9
      //   447: aload_1
      //   448: monitorexit
      //   449: aload #9
      //   451: athrow
      //   452: astore #9
      //   454: aload_0
      //   455: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   458: astore_1
      //   459: aload_1
      //   460: monitorenter
      //   461: aload_0
      //   462: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   465: aconst_null
      //   466: putfield upstreamReader : Ljava/lang/Thread;
      //   469: aload_0
      //   470: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   473: invokevirtual notifyAll : ()V
      //   476: aload_1
      //   477: monitorexit
      //   478: aload #9
      //   480: athrow
      //   481: astore #9
      //   483: aload_1
      //   484: monitorexit
      //   485: aload #9
      //   487: athrow
      //   488: lload_2
      //   489: lload #5
      //   491: aload_0
      //   492: getfield sourcePos : J
      //   495: lsub
      //   496: invokestatic min : (JJ)J
      //   499: lstore_2
      //   500: aload_0
      //   501: getfield this$0 : Lokhttp3/internal/cache2/Relay;
      //   504: getfield buffer : Lokio/Buffer;
      //   507: aload_1
      //   508: aload_0
      //   509: getfield sourcePos : J
      //   512: lload #7
      //   514: lsub
      //   515: lload_2
      //   516: invokevirtual copyTo : (Lokio/Buffer;JJ)Lokio/Buffer;
      //   519: pop
      //   520: aload_0
      //   521: aload_0
      //   522: getfield sourcePos : J
      //   525: lload_2
      //   526: ladd
      //   527: putfield sourcePos : J
      //   530: aload #9
      //   532: monitorexit
      //   533: lload_2
      //   534: lreturn
      //   535: astore_1
      //   536: aload #9
      //   538: monitorexit
      //   539: aload_1
      //   540: athrow
      //   541: new java/lang/IllegalStateException
      //   544: dup
      //   545: ldc 'closed'
      //   547: invokespecial <init> : (Ljava/lang/String;)V
      //   550: athrow
      // Exception table:
      //   from	to	target	type
      //   16	31	535	finally
      //   39	52	535	finally
      //   56	77	535	finally
      //   80	90	535	finally
      //   93	96	535	finally
      //   99	127	535	finally
      //   177	205	452	finally
      //   214	223	452	finally
      //   232	250	254	finally
      //   255	258	254	finally
      //   260	323	452	finally
      //   323	391	445	finally
      //   391	412	445	finally
      //   419	436	438	finally
      //   440	442	438	finally
      //   447	449	445	finally
      //   449	452	452	finally
      //   461	478	481	finally
      //   483	485	481	finally
      //   488	533	535	finally
      //   536	539	535	finally
    }
    
    public Timeout timeout() {
      return this.timeout;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\cache2\Relay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */