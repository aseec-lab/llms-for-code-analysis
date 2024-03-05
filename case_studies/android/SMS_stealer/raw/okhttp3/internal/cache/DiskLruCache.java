package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class DiskLruCache implements Closeable, Flushable {
  static final boolean $assertionsDisabled = false;
  
  static final long ANY_SEQUENCE_NUMBER = -1L;
  
  private static final String CLEAN = "CLEAN";
  
  private static final String DIRTY = "DIRTY";
  
  static final String JOURNAL_FILE = "journal";
  
  static final String JOURNAL_FILE_BACKUP = "journal.bkp";
  
  static final String JOURNAL_FILE_TEMP = "journal.tmp";
  
  static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
  
  static final String MAGIC = "libcore.io.DiskLruCache";
  
  private static final String READ = "READ";
  
  private static final String REMOVE = "REMOVE";
  
  static final String VERSION_1 = "1";
  
  private final int appVersion;
  
  private final Runnable cleanupRunnable = new Runnable() {
      final DiskLruCache this$0;
      
      public void run() {
        synchronized (DiskLruCache.this) {
          boolean bool;
          if (!DiskLruCache.this.initialized) {
            bool = true;
          } else {
            bool = false;
          } 
          if (bool | DiskLruCache.this.closed)
            return; 
          try {
            DiskLruCache.this.trimToSize();
          } catch (IOException iOException) {
            DiskLruCache.this.mostRecentTrimFailed = true;
          } 
          try {
            if (DiskLruCache.this.journalRebuildRequired()) {
              DiskLruCache.this.rebuildJournal();
              DiskLruCache.this.redundantOpCount = 0;
            } 
          } catch (IOException iOException) {
            DiskLruCache.this.mostRecentRebuildFailed = true;
            DiskLruCache.this.journalWriter = Okio.buffer(Okio.blackhole());
          } 
          return;
        } 
      }
    };
  
  boolean closed;
  
  final File directory;
  
  private final Executor executor;
  
  final FileSystem fileSystem;
  
  boolean hasJournalErrors;
  
  boolean initialized;
  
  private final File journalFile;
  
  private final File journalFileBackup;
  
  private final File journalFileTmp;
  
  BufferedSink journalWriter;
  
  final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<String, Entry>(0, 0.75F, true);
  
  private long maxSize;
  
  boolean mostRecentRebuildFailed;
  
  boolean mostRecentTrimFailed;
  
  private long nextSequenceNumber = 0L;
  
  int redundantOpCount;
  
  private long size = 0L;
  
  final int valueCount;
  
  DiskLruCache(FileSystem paramFileSystem, File paramFile, int paramInt1, int paramInt2, long paramLong, Executor paramExecutor) {
    this.fileSystem = paramFileSystem;
    this.directory = paramFile;
    this.appVersion = paramInt1;
    this.journalFile = new File(paramFile, "journal");
    this.journalFileTmp = new File(paramFile, "journal.tmp");
    this.journalFileBackup = new File(paramFile, "journal.bkp");
    this.valueCount = paramInt2;
    this.maxSize = paramLong;
    this.executor = paramExecutor;
  }
  
  private void checkNotClosed() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isClosed : ()Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifne -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: new java/lang/IllegalStateException
    //   17: astore_2
    //   18: aload_2
    //   19: ldc 'cache is closed'
    //   21: invokespecial <init> : (Ljava/lang/String;)V
    //   24: aload_2
    //   25: athrow
    //   26: astore_2
    //   27: aload_0
    //   28: monitorexit
    //   29: aload_2
    //   30: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	26	finally
    //   14	26	26	finally
  }
  
  public static DiskLruCache create(FileSystem paramFileSystem, File paramFile, int paramInt1, int paramInt2, long paramLong) {
    if (paramLong > 0L) {
      if (paramInt2 > 0)
        return new DiskLruCache(paramFileSystem, paramFile, paramInt1, paramInt2, paramLong, new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory("OkHttp DiskLruCache", true))); 
      throw new IllegalArgumentException("valueCount <= 0");
    } 
    throw new IllegalArgumentException("maxSize <= 0");
  }
  
  private BufferedSink newJournalWriter() throws FileNotFoundException {
    return Okio.buffer((Sink)new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile)) {
          static final boolean $assertionsDisabled = false;
          
          final DiskLruCache this$0;
          
          protected void onException(IOException param1IOException) {
            DiskLruCache.this.hasJournalErrors = true;
          }
        });
  }
  
  private void processJournal() throws IOException {
    this.fileSystem.delete(this.journalFileTmp);
    Iterator<Entry> iterator = this.lruEntries.values().iterator();
    while (iterator.hasNext()) {
      Entry entry = iterator.next();
      Editor editor = entry.currentEditor;
      boolean bool = false;
      byte b = 0;
      if (editor == null) {
        while (b < this.valueCount) {
          this.size += entry.lengths[b];
          b++;
        } 
        continue;
      } 
      entry.currentEditor = null;
      for (b = bool; b < this.valueCount; b++) {
        this.fileSystem.delete(entry.cleanFiles[b]);
        this.fileSystem.delete(entry.dirtyFiles[b]);
      } 
      iterator.remove();
    } 
  }
  
  private void readJournal() throws IOException {
    BufferedSource bufferedSource = Okio.buffer(this.fileSystem.source(this.journalFile));
    try {
      String str4 = bufferedSource.readUtf8LineStrict();
      String str2 = bufferedSource.readUtf8LineStrict();
      String str5 = bufferedSource.readUtf8LineStrict();
      String str3 = bufferedSource.readUtf8LineStrict();
      String str1 = bufferedSource.readUtf8LineStrict();
      if ("libcore.io.DiskLruCache".equals(str4) && "1".equals(str2) && Integer.toString(this.appVersion).equals(str5) && Integer.toString(this.valueCount).equals(str3)) {
        boolean bool = "".equals(str1);
        if (bool) {
          byte b = 0;
          try {
            while (true) {
              readJournalLine(bufferedSource.readUtf8LineStrict());
              b++;
            } 
          } catch (EOFException eOFException) {
            this.redundantOpCount = b - this.lruEntries.size();
            if (!bufferedSource.exhausted()) {
              rebuildJournal();
            } else {
              this.journalWriter = newJournalWriter();
            } 
            return;
          } 
        } 
      } 
      IOException iOException = new IOException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("unexpected journal header: [");
      stringBuilder.append(str4);
      stringBuilder.append(", ");
      stringBuilder.append(str2);
      stringBuilder.append(", ");
      stringBuilder.append(str3);
      stringBuilder.append(", ");
      stringBuilder.append((String)eOFException);
      stringBuilder.append("]");
      this(stringBuilder.toString());
      throw iOException;
    } finally {
      Util.closeQuietly((Closeable)bufferedSource);
    } 
  }
  
  private void readJournalLine(String paramString) throws IOException {
    String[] arrayOfString;
    int i = paramString.indexOf(' ');
    if (i != -1) {
      String str;
      int k = i + 1;
      int j = paramString.indexOf(' ', k);
      if (j == -1) {
        String str1 = paramString.substring(k);
        str = str1;
        if (i == 6) {
          str = str1;
          if (paramString.startsWith("REMOVE")) {
            this.lruEntries.remove(str1);
            return;
          } 
        } 
      } else {
        str = paramString.substring(k, j);
      } 
      Entry entry2 = this.lruEntries.get(str);
      Entry entry1 = entry2;
      if (entry2 == null) {
        entry1 = new Entry(str);
        this.lruEntries.put(str, entry1);
      } 
      if (j != -1 && i == 5 && paramString.startsWith("CLEAN")) {
        arrayOfString = paramString.substring(j + 1).split(" ");
        entry1.readable = true;
        entry1.currentEditor = null;
        entry1.setLengths(arrayOfString);
      } else if (j == -1 && i == 5 && arrayOfString.startsWith("DIRTY")) {
        entry1.currentEditor = new Editor(entry1);
      } else if (j != -1 || i != 4 || !arrayOfString.startsWith("READ")) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("unexpected journal line: ");
        stringBuilder1.append((String)arrayOfString);
        throw new IOException(stringBuilder1.toString());
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("unexpected journal line: ");
    stringBuilder.append((String)arrayOfString);
    throw new IOException(stringBuilder.toString());
  }
  
  private void validateKey(String paramString) {
    if (LEGAL_KEY_PATTERN.matcher(paramString).matches())
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("keys must match regex [a-z0-9_-]{1,120}: \"");
    stringBuilder.append(paramString);
    stringBuilder.append("\"");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void close() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield initialized : Z
    //   6: ifeq -> 108
    //   9: aload_0
    //   10: getfield closed : Z
    //   13: ifeq -> 19
    //   16: goto -> 108
    //   19: aload_0
    //   20: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   23: invokevirtual values : ()Ljava/util/Collection;
    //   26: aload_0
    //   27: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   30: invokevirtual size : ()I
    //   33: anewarray okhttp3/internal/cache/DiskLruCache$Entry
    //   36: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   41: checkcast [Lokhttp3/internal/cache/DiskLruCache$Entry;
    //   44: astore #4
    //   46: aload #4
    //   48: arraylength
    //   49: istore_2
    //   50: iconst_0
    //   51: istore_1
    //   52: iload_1
    //   53: iload_2
    //   54: if_icmpge -> 82
    //   57: aload #4
    //   59: iload_1
    //   60: aaload
    //   61: astore_3
    //   62: aload_3
    //   63: getfield currentEditor : Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   66: ifnull -> 76
    //   69: aload_3
    //   70: getfield currentEditor : Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   73: invokevirtual abort : ()V
    //   76: iinc #1, 1
    //   79: goto -> 52
    //   82: aload_0
    //   83: invokevirtual trimToSize : ()V
    //   86: aload_0
    //   87: getfield journalWriter : Lokio/BufferedSink;
    //   90: invokeinterface close : ()V
    //   95: aload_0
    //   96: aconst_null
    //   97: putfield journalWriter : Lokio/BufferedSink;
    //   100: aload_0
    //   101: iconst_1
    //   102: putfield closed : Z
    //   105: aload_0
    //   106: monitorexit
    //   107: return
    //   108: aload_0
    //   109: iconst_1
    //   110: putfield closed : Z
    //   113: aload_0
    //   114: monitorexit
    //   115: return
    //   116: astore_3
    //   117: aload_0
    //   118: monitorexit
    //   119: aload_3
    //   120: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	116	finally
    //   19	50	116	finally
    //   62	76	116	finally
    //   82	105	116	finally
    //   108	113	116	finally
  }
  
  void completeEdit(Editor paramEditor, boolean paramBoolean) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: getfield entry : Lokhttp3/internal/cache/DiskLruCache$Entry;
    //   6: astore #10
    //   8: aload #10
    //   10: getfield currentEditor : Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   13: aload_1
    //   14: if_acmpne -> 478
    //   17: iconst_0
    //   18: istore #5
    //   20: iload #5
    //   22: istore_3
    //   23: iload_2
    //   24: ifeq -> 142
    //   27: iload #5
    //   29: istore_3
    //   30: aload #10
    //   32: getfield readable : Z
    //   35: ifne -> 142
    //   38: iconst_0
    //   39: istore #4
    //   41: iload #5
    //   43: istore_3
    //   44: iload #4
    //   46: aload_0
    //   47: getfield valueCount : I
    //   50: if_icmpge -> 142
    //   53: aload_1
    //   54: getfield written : [Z
    //   57: iload #4
    //   59: baload
    //   60: ifeq -> 96
    //   63: aload_0
    //   64: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   67: aload #10
    //   69: getfield dirtyFiles : [Ljava/io/File;
    //   72: iload #4
    //   74: aaload
    //   75: invokeinterface exists : (Ljava/io/File;)Z
    //   80: ifne -> 90
    //   83: aload_1
    //   84: invokevirtual abort : ()V
    //   87: aload_0
    //   88: monitorexit
    //   89: return
    //   90: iinc #4, 1
    //   93: goto -> 41
    //   96: aload_1
    //   97: invokevirtual abort : ()V
    //   100: new java/lang/IllegalStateException
    //   103: astore_1
    //   104: new java/lang/StringBuilder
    //   107: astore #10
    //   109: aload #10
    //   111: invokespecial <init> : ()V
    //   114: aload #10
    //   116: ldc_w 'Newly created entry didn't create value for index '
    //   119: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload #10
    //   125: iload #4
    //   127: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: aload_1
    //   132: aload #10
    //   134: invokevirtual toString : ()Ljava/lang/String;
    //   137: invokespecial <init> : (Ljava/lang/String;)V
    //   140: aload_1
    //   141: athrow
    //   142: iload_3
    //   143: aload_0
    //   144: getfield valueCount : I
    //   147: if_icmpge -> 260
    //   150: aload #10
    //   152: getfield dirtyFiles : [Ljava/io/File;
    //   155: iload_3
    //   156: aaload
    //   157: astore_1
    //   158: iload_2
    //   159: ifeq -> 244
    //   162: aload_0
    //   163: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   166: aload_1
    //   167: invokeinterface exists : (Ljava/io/File;)Z
    //   172: ifeq -> 254
    //   175: aload #10
    //   177: getfield cleanFiles : [Ljava/io/File;
    //   180: iload_3
    //   181: aaload
    //   182: astore #11
    //   184: aload_0
    //   185: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   188: aload_1
    //   189: aload #11
    //   191: invokeinterface rename : (Ljava/io/File;Ljava/io/File;)V
    //   196: aload #10
    //   198: getfield lengths : [J
    //   201: iload_3
    //   202: laload
    //   203: lstore #6
    //   205: aload_0
    //   206: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   209: aload #11
    //   211: invokeinterface size : (Ljava/io/File;)J
    //   216: lstore #8
    //   218: aload #10
    //   220: getfield lengths : [J
    //   223: iload_3
    //   224: lload #8
    //   226: lastore
    //   227: aload_0
    //   228: aload_0
    //   229: getfield size : J
    //   232: lload #6
    //   234: lsub
    //   235: lload #8
    //   237: ladd
    //   238: putfield size : J
    //   241: goto -> 254
    //   244: aload_0
    //   245: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   248: aload_1
    //   249: invokeinterface delete : (Ljava/io/File;)V
    //   254: iinc #3, 1
    //   257: goto -> 142
    //   260: aload_0
    //   261: aload_0
    //   262: getfield redundantOpCount : I
    //   265: iconst_1
    //   266: iadd
    //   267: putfield redundantOpCount : I
    //   270: aload #10
    //   272: aconst_null
    //   273: putfield currentEditor : Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   276: aload #10
    //   278: getfield readable : Z
    //   281: iload_2
    //   282: ior
    //   283: ifeq -> 375
    //   286: aload #10
    //   288: iconst_1
    //   289: putfield readable : Z
    //   292: aload_0
    //   293: getfield journalWriter : Lokio/BufferedSink;
    //   296: ldc 'CLEAN'
    //   298: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   303: bipush #32
    //   305: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   310: pop
    //   311: aload_0
    //   312: getfield journalWriter : Lokio/BufferedSink;
    //   315: aload #10
    //   317: getfield key : Ljava/lang/String;
    //   320: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   325: pop
    //   326: aload #10
    //   328: aload_0
    //   329: getfield journalWriter : Lokio/BufferedSink;
    //   332: invokevirtual writeLengths : (Lokio/BufferedSink;)V
    //   335: aload_0
    //   336: getfield journalWriter : Lokio/BufferedSink;
    //   339: bipush #10
    //   341: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   346: pop
    //   347: iload_2
    //   348: ifeq -> 434
    //   351: aload_0
    //   352: getfield nextSequenceNumber : J
    //   355: lstore #6
    //   357: aload_0
    //   358: lconst_1
    //   359: lload #6
    //   361: ladd
    //   362: putfield nextSequenceNumber : J
    //   365: aload #10
    //   367: lload #6
    //   369: putfield sequenceNumber : J
    //   372: goto -> 434
    //   375: aload_0
    //   376: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   379: aload #10
    //   381: getfield key : Ljava/lang/String;
    //   384: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   387: pop
    //   388: aload_0
    //   389: getfield journalWriter : Lokio/BufferedSink;
    //   392: ldc 'REMOVE'
    //   394: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   399: bipush #32
    //   401: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   406: pop
    //   407: aload_0
    //   408: getfield journalWriter : Lokio/BufferedSink;
    //   411: aload #10
    //   413: getfield key : Ljava/lang/String;
    //   416: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   421: pop
    //   422: aload_0
    //   423: getfield journalWriter : Lokio/BufferedSink;
    //   426: bipush #10
    //   428: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   433: pop
    //   434: aload_0
    //   435: getfield journalWriter : Lokio/BufferedSink;
    //   438: invokeinterface flush : ()V
    //   443: aload_0
    //   444: getfield size : J
    //   447: aload_0
    //   448: getfield maxSize : J
    //   451: lcmp
    //   452: ifgt -> 462
    //   455: aload_0
    //   456: invokevirtual journalRebuildRequired : ()Z
    //   459: ifeq -> 475
    //   462: aload_0
    //   463: getfield executor : Ljava/util/concurrent/Executor;
    //   466: aload_0
    //   467: getfield cleanupRunnable : Ljava/lang/Runnable;
    //   470: invokeinterface execute : (Ljava/lang/Runnable;)V
    //   475: aload_0
    //   476: monitorexit
    //   477: return
    //   478: new java/lang/IllegalStateException
    //   481: astore_1
    //   482: aload_1
    //   483: invokespecial <init> : ()V
    //   486: aload_1
    //   487: athrow
    //   488: astore_1
    //   489: aload_0
    //   490: monitorexit
    //   491: aload_1
    //   492: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	488	finally
    //   30	38	488	finally
    //   44	87	488	finally
    //   96	142	488	finally
    //   142	158	488	finally
    //   162	241	488	finally
    //   244	254	488	finally
    //   260	347	488	finally
    //   351	372	488	finally
    //   375	434	488	finally
    //   434	462	488	finally
    //   462	475	488	finally
    //   478	488	488	finally
  }
  
  public void delete() throws IOException {
    close();
    this.fileSystem.deleteContents(this.directory);
  }
  
  @Nullable
  public Editor edit(String paramString) throws IOException {
    return edit(paramString, -1L);
  }
  
  Editor edit(String paramString, long paramLong) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual initialize : ()V
    //   6: aload_0
    //   7: invokespecial checkNotClosed : ()V
    //   10: aload_0
    //   11: aload_1
    //   12: invokespecial validateKey : (Ljava/lang/String;)V
    //   15: aload_0
    //   16: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   19: aload_1
    //   20: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast okhttp3/internal/cache/DiskLruCache$Entry
    //   26: astore #8
    //   28: lload_2
    //   29: ldc2_w -1
    //   32: lcmp
    //   33: ifeq -> 59
    //   36: aload #8
    //   38: ifnull -> 55
    //   41: aload #8
    //   43: getfield sequenceNumber : J
    //   46: lstore #4
    //   48: lload #4
    //   50: lload_2
    //   51: lcmp
    //   52: ifeq -> 59
    //   55: aload_0
    //   56: monitorexit
    //   57: aconst_null
    //   58: areturn
    //   59: aload #8
    //   61: ifnull -> 80
    //   64: aload #8
    //   66: getfield currentEditor : Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   69: astore #7
    //   71: aload #7
    //   73: ifnull -> 80
    //   76: aload_0
    //   77: monitorexit
    //   78: aconst_null
    //   79: areturn
    //   80: aload_0
    //   81: getfield mostRecentTrimFailed : Z
    //   84: ifne -> 206
    //   87: aload_0
    //   88: getfield mostRecentRebuildFailed : Z
    //   91: ifeq -> 97
    //   94: goto -> 206
    //   97: aload_0
    //   98: getfield journalWriter : Lokio/BufferedSink;
    //   101: ldc 'DIRTY'
    //   103: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   108: bipush #32
    //   110: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   115: aload_1
    //   116: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   121: bipush #10
    //   123: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   128: pop
    //   129: aload_0
    //   130: getfield journalWriter : Lokio/BufferedSink;
    //   133: invokeinterface flush : ()V
    //   138: aload_0
    //   139: getfield hasJournalErrors : Z
    //   142: istore #6
    //   144: iload #6
    //   146: ifeq -> 153
    //   149: aload_0
    //   150: monitorexit
    //   151: aconst_null
    //   152: areturn
    //   153: aload #8
    //   155: astore #7
    //   157: aload #8
    //   159: ifnonnull -> 185
    //   162: new okhttp3/internal/cache/DiskLruCache$Entry
    //   165: astore #7
    //   167: aload #7
    //   169: aload_0
    //   170: aload_1
    //   171: invokespecial <init> : (Lokhttp3/internal/cache/DiskLruCache;Ljava/lang/String;)V
    //   174: aload_0
    //   175: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   178: aload_1
    //   179: aload #7
    //   181: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   184: pop
    //   185: new okhttp3/internal/cache/DiskLruCache$Editor
    //   188: astore_1
    //   189: aload_1
    //   190: aload_0
    //   191: aload #7
    //   193: invokespecial <init> : (Lokhttp3/internal/cache/DiskLruCache;Lokhttp3/internal/cache/DiskLruCache$Entry;)V
    //   196: aload #7
    //   198: aload_1
    //   199: putfield currentEditor : Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   202: aload_0
    //   203: monitorexit
    //   204: aload_1
    //   205: areturn
    //   206: aload_0
    //   207: getfield executor : Ljava/util/concurrent/Executor;
    //   210: aload_0
    //   211: getfield cleanupRunnable : Ljava/lang/Runnable;
    //   214: invokeinterface execute : (Ljava/lang/Runnable;)V
    //   219: aload_0
    //   220: monitorexit
    //   221: aconst_null
    //   222: areturn
    //   223: astore_1
    //   224: aload_0
    //   225: monitorexit
    //   226: aload_1
    //   227: athrow
    // Exception table:
    //   from	to	target	type
    //   2	28	223	finally
    //   41	48	223	finally
    //   64	71	223	finally
    //   80	94	223	finally
    //   97	144	223	finally
    //   162	185	223	finally
    //   185	202	223	finally
    //   206	219	223	finally
  }
  
  public void evictAll() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual initialize : ()V
    //   6: aload_0
    //   7: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   10: invokevirtual values : ()Ljava/util/Collection;
    //   13: aload_0
    //   14: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   17: invokevirtual size : ()I
    //   20: anewarray okhttp3/internal/cache/DiskLruCache$Entry
    //   23: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   28: checkcast [Lokhttp3/internal/cache/DiskLruCache$Entry;
    //   31: astore_3
    //   32: aload_3
    //   33: arraylength
    //   34: istore_2
    //   35: iconst_0
    //   36: istore_1
    //   37: iload_1
    //   38: iload_2
    //   39: if_icmpge -> 56
    //   42: aload_0
    //   43: aload_3
    //   44: iload_1
    //   45: aaload
    //   46: invokevirtual removeEntry : (Lokhttp3/internal/cache/DiskLruCache$Entry;)Z
    //   49: pop
    //   50: iinc #1, 1
    //   53: goto -> 37
    //   56: aload_0
    //   57: iconst_0
    //   58: putfield mostRecentTrimFailed : Z
    //   61: aload_0
    //   62: monitorexit
    //   63: return
    //   64: astore_3
    //   65: aload_0
    //   66: monitorexit
    //   67: aload_3
    //   68: athrow
    // Exception table:
    //   from	to	target	type
    //   2	35	64	finally
    //   42	50	64	finally
    //   56	61	64	finally
  }
  
  public void flush() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield initialized : Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifne -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: invokespecial checkNotClosed : ()V
    //   18: aload_0
    //   19: invokevirtual trimToSize : ()V
    //   22: aload_0
    //   23: getfield journalWriter : Lokio/BufferedSink;
    //   26: invokeinterface flush : ()V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: astore_2
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_2
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	34	finally
    //   14	31	34	finally
  }
  
  public Snapshot get(String paramString) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual initialize : ()V
    //   6: aload_0
    //   7: invokespecial checkNotClosed : ()V
    //   10: aload_0
    //   11: aload_1
    //   12: invokespecial validateKey : (Ljava/lang/String;)V
    //   15: aload_0
    //   16: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   19: aload_1
    //   20: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast okhttp3/internal/cache/DiskLruCache$Entry
    //   26: astore_2
    //   27: aload_2
    //   28: ifnull -> 120
    //   31: aload_2
    //   32: getfield readable : Z
    //   35: ifne -> 41
    //   38: goto -> 120
    //   41: aload_2
    //   42: invokevirtual snapshot : ()Lokhttp3/internal/cache/DiskLruCache$Snapshot;
    //   45: astore_2
    //   46: aload_2
    //   47: ifnonnull -> 54
    //   50: aload_0
    //   51: monitorexit
    //   52: aconst_null
    //   53: areturn
    //   54: aload_0
    //   55: aload_0
    //   56: getfield redundantOpCount : I
    //   59: iconst_1
    //   60: iadd
    //   61: putfield redundantOpCount : I
    //   64: aload_0
    //   65: getfield journalWriter : Lokio/BufferedSink;
    //   68: ldc 'READ'
    //   70: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   75: bipush #32
    //   77: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   82: aload_1
    //   83: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   88: bipush #10
    //   90: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   95: pop
    //   96: aload_0
    //   97: invokevirtual journalRebuildRequired : ()Z
    //   100: ifeq -> 116
    //   103: aload_0
    //   104: getfield executor : Ljava/util/concurrent/Executor;
    //   107: aload_0
    //   108: getfield cleanupRunnable : Ljava/lang/Runnable;
    //   111: invokeinterface execute : (Ljava/lang/Runnable;)V
    //   116: aload_0
    //   117: monitorexit
    //   118: aload_2
    //   119: areturn
    //   120: aload_0
    //   121: monitorexit
    //   122: aconst_null
    //   123: areturn
    //   124: astore_1
    //   125: aload_0
    //   126: monitorexit
    //   127: aload_1
    //   128: athrow
    // Exception table:
    //   from	to	target	type
    //   2	27	124	finally
    //   31	38	124	finally
    //   41	46	124	finally
    //   54	116	124	finally
  }
  
  public File getDirectory() {
    return this.directory;
  }
  
  public long getMaxSize() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield maxSize : J
    //   6: lstore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: lload_1
    //   10: lreturn
    //   11: astore_3
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_3
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public void initialize() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield initialized : Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifeq -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   18: aload_0
    //   19: getfield journalFileBackup : Ljava/io/File;
    //   22: invokeinterface exists : (Ljava/io/File;)Z
    //   27: ifeq -> 79
    //   30: aload_0
    //   31: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   34: aload_0
    //   35: getfield journalFile : Ljava/io/File;
    //   38: invokeinterface exists : (Ljava/io/File;)Z
    //   43: ifeq -> 62
    //   46: aload_0
    //   47: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   50: aload_0
    //   51: getfield journalFileBackup : Ljava/io/File;
    //   54: invokeinterface delete : (Ljava/io/File;)V
    //   59: goto -> 79
    //   62: aload_0
    //   63: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   66: aload_0
    //   67: getfield journalFileBackup : Ljava/io/File;
    //   70: aload_0
    //   71: getfield journalFile : Ljava/io/File;
    //   74: invokeinterface rename : (Ljava/io/File;Ljava/io/File;)V
    //   79: aload_0
    //   80: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   83: aload_0
    //   84: getfield journalFile : Ljava/io/File;
    //   87: invokeinterface exists : (Ljava/io/File;)Z
    //   92: istore_1
    //   93: iload_1
    //   94: ifeq -> 201
    //   97: aload_0
    //   98: invokespecial readJournal : ()V
    //   101: aload_0
    //   102: invokespecial processJournal : ()V
    //   105: aload_0
    //   106: iconst_1
    //   107: putfield initialized : Z
    //   110: aload_0
    //   111: monitorexit
    //   112: return
    //   113: astore #4
    //   115: invokestatic get : ()Lokhttp3/internal/platform/Platform;
    //   118: astore_2
    //   119: new java/lang/StringBuilder
    //   122: astore_3
    //   123: aload_3
    //   124: invokespecial <init> : ()V
    //   127: aload_3
    //   128: ldc_w 'DiskLruCache '
    //   131: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: pop
    //   135: aload_3
    //   136: aload_0
    //   137: getfield directory : Ljava/io/File;
    //   140: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   143: pop
    //   144: aload_3
    //   145: ldc_w ' is corrupt: '
    //   148: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: pop
    //   152: aload_3
    //   153: aload #4
    //   155: invokevirtual getMessage : ()Ljava/lang/String;
    //   158: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: pop
    //   162: aload_3
    //   163: ldc_w ', removing'
    //   166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: aload_2
    //   171: iconst_5
    //   172: aload_3
    //   173: invokevirtual toString : ()Ljava/lang/String;
    //   176: aload #4
    //   178: invokevirtual log : (ILjava/lang/String;Ljava/lang/Throwable;)V
    //   181: aload_0
    //   182: invokevirtual delete : ()V
    //   185: aload_0
    //   186: iconst_0
    //   187: putfield closed : Z
    //   190: goto -> 201
    //   193: astore_2
    //   194: aload_0
    //   195: iconst_0
    //   196: putfield closed : Z
    //   199: aload_2
    //   200: athrow
    //   201: aload_0
    //   202: invokevirtual rebuildJournal : ()V
    //   205: aload_0
    //   206: iconst_1
    //   207: putfield initialized : Z
    //   210: aload_0
    //   211: monitorexit
    //   212: return
    //   213: astore_2
    //   214: aload_0
    //   215: monitorexit
    //   216: aload_2
    //   217: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	213	finally
    //   14	59	213	finally
    //   62	79	213	finally
    //   79	93	213	finally
    //   97	110	113	java/io/IOException
    //   97	110	213	finally
    //   115	181	213	finally
    //   181	185	193	finally
    //   185	190	213	finally
    //   194	201	213	finally
    //   201	210	213	finally
  }
  
  public boolean isClosed() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield closed : Z
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  boolean journalRebuildRequired() {
    boolean bool;
    int i = this.redundantOpCount;
    if (i >= 2000 && i >= this.lruEntries.size()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void rebuildJournal() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield journalWriter : Lokio/BufferedSink;
    //   6: ifnull -> 18
    //   9: aload_0
    //   10: getfield journalWriter : Lokio/BufferedSink;
    //   13: invokeinterface close : ()V
    //   18: aload_0
    //   19: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   22: aload_0
    //   23: getfield journalFileTmp : Ljava/io/File;
    //   26: invokeinterface sink : (Ljava/io/File;)Lokio/Sink;
    //   31: invokestatic buffer : (Lokio/Sink;)Lokio/BufferedSink;
    //   34: astore_1
    //   35: aload_1
    //   36: ldc 'libcore.io.DiskLruCache'
    //   38: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   43: bipush #10
    //   45: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   50: pop
    //   51: aload_1
    //   52: ldc '1'
    //   54: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   59: bipush #10
    //   61: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   66: pop
    //   67: aload_1
    //   68: aload_0
    //   69: getfield appVersion : I
    //   72: i2l
    //   73: invokeinterface writeDecimalLong : (J)Lokio/BufferedSink;
    //   78: bipush #10
    //   80: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   85: pop
    //   86: aload_1
    //   87: aload_0
    //   88: getfield valueCount : I
    //   91: i2l
    //   92: invokeinterface writeDecimalLong : (J)Lokio/BufferedSink;
    //   97: bipush #10
    //   99: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   104: pop
    //   105: aload_1
    //   106: bipush #10
    //   108: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   113: pop
    //   114: aload_0
    //   115: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   118: invokevirtual values : ()Ljava/util/Collection;
    //   121: invokeinterface iterator : ()Ljava/util/Iterator;
    //   126: astore_2
    //   127: aload_2
    //   128: invokeinterface hasNext : ()Z
    //   133: ifeq -> 236
    //   136: aload_2
    //   137: invokeinterface next : ()Ljava/lang/Object;
    //   142: checkcast okhttp3/internal/cache/DiskLruCache$Entry
    //   145: astore_3
    //   146: aload_3
    //   147: getfield currentEditor : Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   150: ifnull -> 192
    //   153: aload_1
    //   154: ldc 'DIRTY'
    //   156: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   161: bipush #32
    //   163: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   168: pop
    //   169: aload_1
    //   170: aload_3
    //   171: getfield key : Ljava/lang/String;
    //   174: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   179: pop
    //   180: aload_1
    //   181: bipush #10
    //   183: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   188: pop
    //   189: goto -> 127
    //   192: aload_1
    //   193: ldc 'CLEAN'
    //   195: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   200: bipush #32
    //   202: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   207: pop
    //   208: aload_1
    //   209: aload_3
    //   210: getfield key : Ljava/lang/String;
    //   213: invokeinterface writeUtf8 : (Ljava/lang/String;)Lokio/BufferedSink;
    //   218: pop
    //   219: aload_3
    //   220: aload_1
    //   221: invokevirtual writeLengths : (Lokio/BufferedSink;)V
    //   224: aload_1
    //   225: bipush #10
    //   227: invokeinterface writeByte : (I)Lokio/BufferedSink;
    //   232: pop
    //   233: goto -> 127
    //   236: aload_1
    //   237: invokeinterface close : ()V
    //   242: aload_0
    //   243: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   246: aload_0
    //   247: getfield journalFile : Ljava/io/File;
    //   250: invokeinterface exists : (Ljava/io/File;)Z
    //   255: ifeq -> 275
    //   258: aload_0
    //   259: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   262: aload_0
    //   263: getfield journalFile : Ljava/io/File;
    //   266: aload_0
    //   267: getfield journalFileBackup : Ljava/io/File;
    //   270: invokeinterface rename : (Ljava/io/File;Ljava/io/File;)V
    //   275: aload_0
    //   276: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   279: aload_0
    //   280: getfield journalFileTmp : Ljava/io/File;
    //   283: aload_0
    //   284: getfield journalFile : Ljava/io/File;
    //   287: invokeinterface rename : (Ljava/io/File;Ljava/io/File;)V
    //   292: aload_0
    //   293: getfield fileSystem : Lokhttp3/internal/io/FileSystem;
    //   296: aload_0
    //   297: getfield journalFileBackup : Ljava/io/File;
    //   300: invokeinterface delete : (Ljava/io/File;)V
    //   305: aload_0
    //   306: aload_0
    //   307: invokespecial newJournalWriter : ()Lokio/BufferedSink;
    //   310: putfield journalWriter : Lokio/BufferedSink;
    //   313: aload_0
    //   314: iconst_0
    //   315: putfield hasJournalErrors : Z
    //   318: aload_0
    //   319: iconst_0
    //   320: putfield mostRecentRebuildFailed : Z
    //   323: aload_0
    //   324: monitorexit
    //   325: return
    //   326: astore_2
    //   327: aload_1
    //   328: invokeinterface close : ()V
    //   333: aload_2
    //   334: athrow
    //   335: astore_1
    //   336: aload_0
    //   337: monitorexit
    //   338: aload_1
    //   339: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	335	finally
    //   18	35	335	finally
    //   35	127	326	finally
    //   127	189	326	finally
    //   192	233	326	finally
    //   236	275	335	finally
    //   275	323	335	finally
    //   327	335	335	finally
  }
  
  public boolean remove(String paramString) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual initialize : ()V
    //   6: aload_0
    //   7: invokespecial checkNotClosed : ()V
    //   10: aload_0
    //   11: aload_1
    //   12: invokespecial validateKey : (Ljava/lang/String;)V
    //   15: aload_0
    //   16: getfield lruEntries : Ljava/util/LinkedHashMap;
    //   19: aload_1
    //   20: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast okhttp3/internal/cache/DiskLruCache$Entry
    //   26: astore_1
    //   27: aload_1
    //   28: ifnonnull -> 35
    //   31: aload_0
    //   32: monitorexit
    //   33: iconst_0
    //   34: ireturn
    //   35: aload_0
    //   36: aload_1
    //   37: invokevirtual removeEntry : (Lokhttp3/internal/cache/DiskLruCache$Entry;)Z
    //   40: istore_2
    //   41: iload_2
    //   42: ifeq -> 62
    //   45: aload_0
    //   46: getfield size : J
    //   49: aload_0
    //   50: getfield maxSize : J
    //   53: lcmp
    //   54: ifgt -> 62
    //   57: aload_0
    //   58: iconst_0
    //   59: putfield mostRecentTrimFailed : Z
    //   62: aload_0
    //   63: monitorexit
    //   64: iload_2
    //   65: ireturn
    //   66: astore_1
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_1
    //   70: athrow
    // Exception table:
    //   from	to	target	type
    //   2	27	66	finally
    //   35	41	66	finally
    //   45	62	66	finally
  }
  
  boolean removeEntry(Entry paramEntry) throws IOException {
    if (paramEntry.currentEditor != null)
      paramEntry.currentEditor.detach(); 
    for (byte b = 0; b < this.valueCount; b++) {
      this.fileSystem.delete(paramEntry.cleanFiles[b]);
      this.size -= paramEntry.lengths[b];
      paramEntry.lengths[b] = 0L;
    } 
    this.redundantOpCount++;
    this.journalWriter.writeUtf8("REMOVE").writeByte(32).writeUtf8(paramEntry.key).writeByte(10);
    this.lruEntries.remove(paramEntry.key);
    if (journalRebuildRequired())
      this.executor.execute(this.cleanupRunnable); 
    return true;
  }
  
  public void setMaxSize(long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: lload_1
    //   4: putfield maxSize : J
    //   7: aload_0
    //   8: getfield initialized : Z
    //   11: ifeq -> 27
    //   14: aload_0
    //   15: getfield executor : Ljava/util/concurrent/Executor;
    //   18: aload_0
    //   19: getfield cleanupRunnable : Ljava/lang/Runnable;
    //   22: invokeinterface execute : (Ljava/lang/Runnable;)V
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: astore_3
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_3
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   2	27	30	finally
  }
  
  public long size() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual initialize : ()V
    //   6: aload_0
    //   7: getfield size : J
    //   10: lstore_1
    //   11: aload_0
    //   12: monitorexit
    //   13: lload_1
    //   14: lreturn
    //   15: astore_3
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_3
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	15	finally
  }
  
  public Iterator<Snapshot> snapshots() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual initialize : ()V
    //   6: new okhttp3/internal/cache/DiskLruCache$3
    //   9: dup
    //   10: aload_0
    //   11: invokespecial <init> : (Lokhttp3/internal/cache/DiskLruCache;)V
    //   14: astore_1
    //   15: aload_0
    //   16: monitorexit
    //   17: aload_1
    //   18: areturn
    //   19: astore_1
    //   20: aload_0
    //   21: monitorexit
    //   22: aload_1
    //   23: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	19	finally
  }
  
  void trimToSize() throws IOException {
    while (this.size > this.maxSize)
      removeEntry(this.lruEntries.values().iterator().next()); 
    this.mostRecentTrimFailed = false;
  }
  
  public final class Editor {
    private boolean done;
    
    final DiskLruCache.Entry entry;
    
    final DiskLruCache this$0;
    
    final boolean[] written;
    
    Editor(DiskLruCache.Entry param1Entry) {
      boolean[] arrayOfBoolean;
      this.entry = param1Entry;
      if (param1Entry.readable) {
        DiskLruCache.this = null;
      } else {
        arrayOfBoolean = new boolean[DiskLruCache.this.valueCount];
      } 
      this.written = arrayOfBoolean;
    }
    
    public void abort() throws IOException {
      synchronized (DiskLruCache.this) {
        if (!this.done) {
          if (this.entry.currentEditor == this)
            DiskLruCache.this.completeEdit(this, false); 
          this.done = true;
          return;
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this();
        throw illegalStateException;
      } 
    }
    
    public void abortUnlessCommitted() {
      synchronized (DiskLruCache.this) {
        if (!this.done) {
          Editor editor = this.entry.currentEditor;
          if (editor == this)
            try {
              DiskLruCache.this.completeEdit(this, false);
            } catch (IOException iOException) {} 
        } 
        return;
      } 
    }
    
    public void commit() throws IOException {
      synchronized (DiskLruCache.this) {
        if (!this.done) {
          if (this.entry.currentEditor == this)
            DiskLruCache.this.completeEdit(this, true); 
          this.done = true;
          return;
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this();
        throw illegalStateException;
      } 
    }
    
    void detach() {
      if (this.entry.currentEditor == this) {
        byte b = 0;
        while (true) {
          if (b < DiskLruCache.this.valueCount) {
            try {
              DiskLruCache.this.fileSystem.delete(this.entry.dirtyFiles[b]);
            } catch (IOException iOException) {}
            b++;
            continue;
          } 
          this.entry.currentEditor = null;
          return;
        } 
      } 
    }
    
    public Sink newSink(int param1Int) {
      synchronized (DiskLruCache.this) {
        if (!this.done) {
          if (this.entry.currentEditor != this)
            return Okio.blackhole(); 
          if (!this.entry.readable)
            this.written[param1Int] = true; 
          File file = this.entry.dirtyFiles[param1Int];
          try {
            Sink sink = DiskLruCache.this.fileSystem.sink(file);
            FaultHidingSink faultHidingSink = new FaultHidingSink() {
                final DiskLruCache.Editor this$1;
                
                protected void onException(IOException param2IOException) {
                  synchronized (DiskLruCache.this) {
                    DiskLruCache.Editor.this.detach();
                    return;
                  } 
                }
              };
            super(this, sink);
            return (Sink)faultHidingSink;
          } catch (FileNotFoundException fileNotFoundException) {
            return Okio.blackhole();
          } 
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this();
        throw illegalStateException;
      } 
    }
    
    public Source newSource(int param1Int) {
      synchronized (DiskLruCache.this) {
        if (!this.done) {
          if (this.entry.readable) {
            Editor editor = this.entry.currentEditor;
            if (editor == this)
              try {
                return DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[param1Int]);
              } catch (FileNotFoundException fileNotFoundException) {
                return null;
              }  
          } 
          return null;
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this();
        throw illegalStateException;
      } 
    }
  }
  
  class null extends FaultHidingSink {
    final DiskLruCache.Editor this$1;
    
    null(Sink param1Sink) {
      super(param1Sink);
    }
    
    protected void onException(IOException param1IOException) {
      synchronized (DiskLruCache.this) {
        this.this$1.detach();
        return;
      } 
    }
  }
  
  private final class Entry {
    final File[] cleanFiles;
    
    DiskLruCache.Editor currentEditor;
    
    final File[] dirtyFiles;
    
    final String key;
    
    final long[] lengths;
    
    boolean readable;
    
    long sequenceNumber;
    
    final DiskLruCache this$0;
    
    Entry(String param1String) {
      this.key = param1String;
      this.lengths = new long[DiskLruCache.this.valueCount];
      this.cleanFiles = new File[DiskLruCache.this.valueCount];
      this.dirtyFiles = new File[DiskLruCache.this.valueCount];
      StringBuilder stringBuilder = new StringBuilder(param1String);
      stringBuilder.append('.');
      int i = stringBuilder.length();
      for (byte b = 0; b < DiskLruCache.this.valueCount; b++) {
        stringBuilder.append(b);
        this.cleanFiles[b] = new File(DiskLruCache.this.directory, stringBuilder.toString());
        stringBuilder.append(".tmp");
        this.dirtyFiles[b] = new File(DiskLruCache.this.directory, stringBuilder.toString());
        stringBuilder.setLength(i);
      } 
    }
    
    private IOException invalidLengths(String[] param1ArrayOfString) throws IOException {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unexpected journal line: ");
      stringBuilder.append(Arrays.toString((Object[])param1ArrayOfString));
      throw new IOException(stringBuilder.toString());
    }
    
    void setLengths(String[] param1ArrayOfString) throws IOException {
      if (param1ArrayOfString.length == DiskLruCache.this.valueCount) {
        byte b = 0;
        try {
          while (b < param1ArrayOfString.length) {
            this.lengths[b] = Long.parseLong(param1ArrayOfString[b]);
            b++;
          } 
          return;
        } catch (NumberFormatException numberFormatException) {
          throw invalidLengths(param1ArrayOfString);
        } 
      } 
      throw invalidLengths(param1ArrayOfString);
    }
    
    DiskLruCache.Snapshot snapshot() {
      if (Thread.holdsLock(DiskLruCache.this)) {
        Source[] arrayOfSource = new Source[DiskLruCache.this.valueCount];
        long[] arrayOfLong = (long[])this.lengths.clone();
        boolean bool = false;
        byte b = 0;
        try {
          while (b < DiskLruCache.this.valueCount) {
            arrayOfSource[b] = DiskLruCache.this.fileSystem.source(this.cleanFiles[b]);
            b++;
          } 
          return new DiskLruCache.Snapshot(this.key, this.sequenceNumber, arrayOfSource, arrayOfLong);
        } catch (FileNotFoundException fileNotFoundException) {
          for (b = bool; b < DiskLruCache.this.valueCount && arrayOfSource[b] != null; b++)
            Util.closeQuietly((Closeable)arrayOfSource[b]); 
          try {
            DiskLruCache.this.removeEntry(this);
          } catch (IOException iOException) {}
          return null;
        } 
      } 
      throw new AssertionError();
    }
    
    void writeLengths(BufferedSink param1BufferedSink) throws IOException {
      for (long l : this.lengths)
        param1BufferedSink.writeByte(32).writeDecimalLong(l); 
    }
  }
  
  public final class Snapshot implements Closeable {
    private final String key;
    
    private final long[] lengths;
    
    private final long sequenceNumber;
    
    private final Source[] sources;
    
    final DiskLruCache this$0;
    
    Snapshot(String param1String, long param1Long, Source[] param1ArrayOfSource, long[] param1ArrayOflong) {
      this.key = param1String;
      this.sequenceNumber = param1Long;
      this.sources = param1ArrayOfSource;
      this.lengths = param1ArrayOflong;
    }
    
    public void close() {
      Source[] arrayOfSource = this.sources;
      int i = arrayOfSource.length;
      for (byte b = 0; b < i; b++)
        Util.closeQuietly((Closeable)arrayOfSource[b]); 
    }
    
    @Nullable
    public DiskLruCache.Editor edit() throws IOException {
      return DiskLruCache.this.edit(this.key, this.sequenceNumber);
    }
    
    public long getLength(int param1Int) {
      return this.lengths[param1Int];
    }
    
    public Source getSource(int param1Int) {
      return this.sources[param1Int];
    }
    
    public String key() {
      return this.key;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\cache\DiskLruCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */