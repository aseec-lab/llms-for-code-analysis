/*    */ package okhttp3.internal.http2;
/*    */ class Huffman { private static final int[] CODES = $d2j$hex$f7f6d28c$decode_I("f81f0000d8ff7f00e2ffff0fe3ffff0fe4ffff0fe5ffff0fe6ffff0fe7ffff0fe8ffff0feaffff00fcffff3fe9ffff0feaffff0ffdffff3febffff0fecffff0fedffff0feeffff0fefffff0ff0ffff0ff1ffff0ff2ffff0ffeffff3ff3ffff0ff4ffff0ff5ffff0ff6ffff0ff7ffff0ff8ffff0ff9ffff0ffaffff0ffbffff0f14000000f8030000f9030000fa0f0000f91f000015000000f8000000fa070000fa030000fb030000f9000000fb070000fa000000160000001700000018000000000000000100000002000000190000001a0000001b0000001c0000001d0000001e0000001f0000005c000000fb000000fc7f000020000000fb0f0000fc030000fa1f0000210000005d0000005e0000005f000000600000006100000062000000630000006400000065000000660000006700000068000000690000006a0000006b0000006c0000006d0000006e0000006f000000700000007100000072000000fc00000073000000fd000000fb1f0000f0ff0700fc1f0000fc3f000022000000fd7f0000030000002300000004000000240000000500000025000000260000002700000006000000740000007500000028000000290000002a000000070000002b000000760000002c00000008000000090000002d0000007700000078000000790000007a0000007b000000fe7f0000fc070000fd3f0000fd1f0000fcffff0fe6ff0f00d2ff3f00e7ff0f00e8ff0f00d3ff3f00d4ff3f00d5ff3f00d9ff7f00d6ff3f00daff7f00dbff7f00dcff7f00ddff7f00deff7f00ebffff00dfff7f00ecffff00edffff00d7ff3f00e0ff7f00eeffff00e1ff7f00e2ff7f00e3ff7f00e4ff7f00dcff1f00d8ff3f00e5ff7f00d9ff3f00e6ff7f00e7ff7f00efffff00daff3f00ddff1f00e9ff0f00dbff3f00dcff3f00e8ff7f00e9ff7f00deff1f00eaff7f00ddff3f00deff3f00f0ffff00dfff1f00dfff3f00ebff7f00ecff7f00e0ff1f00e1ff1f00e0ff3f00e2ff1f00edff7f00e1ff3f00eeff7f00efff7f00eaff0f00e2ff3f00e3ff3f00e4ff3f00f0ff7f00e5ff3f00e6ff3f00f1ff7f00e0ffff03e1ffff03ebff0f00f1ff0700e7ff3f00f2ff7f00e8ff3f00ecffff01e2ffff03e3ffff03e4ffff03deffff07dfffff07e5ffff03f1ffff00edffff01f2ff0700e3ff1f00e6ffff03e0ffff07e1ffff07e7ffff03e2ffff07f2ffff00e4ff1f00e5ff1f00e8ffff03e9ffff03fdffff0fe3ffff07e4ffff07e5ffff07ecff0f00f3ffff00edff0f00e6ff1f00e9ff3f00e7ff1f00e8ff1f00f3ff7f00eaff3f00ebff3f00eeffff01efffff01f4ffff00f5ffff00eaffff03f4ff7f00ebffff03e6ffff07ecffff03edffff03e7ffff07e8ffff07e9ffff07eaffff07ebffff07feffff0fecffff07edffff07eeffff07efffff07f0ffff07eeffff03"); private static final byte[] CODE_LENGTHS = new byte[] { 13, 23, 28, 28, 28, 28, 28, 28, 28, 24, 30, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 28, 6, 10, 10, 12, 13, 6, 8, 11, 10, 10, 8, 11, 8, 6, 6, 6, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 15, 6, 12, 10, 13, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 7, 8, 13, 19, 13, 14, 6, 15, 5, 6, 5, 6, 5, 6, 6, 6, 5, 7, 7, 6, 6, 6, 5, 6, 7, 6, 5, 5, 6, 7, 7, 7, 7, 7, 15, 11, 14, 13, 28, 20, 22, 20, 20, 22, 22, 22, 23, 22, 23, 23, 23, 23, 23, 24, 23, 24, 24, 22, 23, 24, 23, 23, 23, 23, 21, 22, 23, 22, 23, 23, 24, 
/*    */       22, 21, 20, 22, 22, 23, 23, 21, 23, 22, 
/*    */       22, 24, 21, 22, 23, 23, 21, 21, 22, 21, 
/*    */       23, 22, 23, 23, 20, 22, 22, 22, 23, 22, 
/*    */       22, 23, 26, 26, 20, 19, 22, 23, 22, 25, 
/*    */       26, 26, 26, 27, 27, 26, 24, 25, 19, 21, 
/*    */       26, 27, 27, 26, 27, 24, 21, 21, 26, 26, 
/*    */       28, 27, 27, 27, 20, 24, 20, 21, 22, 21, 
/*    */       21, 23, 22, 22, 25, 25, 24, 24, 26, 23, 
/*    */       26, 27, 26, 26, 27, 27, 27, 27, 27, 28, 
/* 12 */       27, 27, 27, 27, 27, 26 }; private static final Huffman INSTANCE = new Huffman(); private final Node root = new Node(); private Huffman() { buildTree(); } private static long[] $d2j$hex$f7f6d28c$decode_J(String src) { byte[] d = $d2j$hex$f7f6d28c$decode_B(src);
/* 13 */     ByteBuffer b = ByteBuffer.wrap(d);
/* 14 */     b.order(ByteOrder.LITTLE_ENDIAN);
/* 15 */     LongBuffer s = b.asLongBuffer();
/* 16 */     long[] data = new long[d.length / 8];
/* 17 */     s.get(data);
/* 18 */     return data; }
/*    */   private void addCode(int paramInt1, int paramInt2, byte paramByte) { Node node2 = new Node(paramInt1, paramByte); Node node1 = this.root; while (paramByte > 8) { paramByte = (byte)(paramByte - 8); paramInt1 = paramInt2 >>> paramByte & 0xFF; if (node1.children != null) { if (node1.children[paramInt1] == null) node1.children[paramInt1] = new Node();  node1 = node1.children[paramInt1]; continue; }  throw new IllegalStateException("invalid dictionary: prefix not unique"); }  int i = 8 - paramByte; paramInt2 = paramInt2 << i & 0xFF; for (paramInt1 = paramInt2; paramInt1 < paramInt2 + (1 << i); paramInt1++) node1.children[paramInt1] = node2;  }
/*    */   private void buildTree() { byte b = 0; while (true) { byte[] arrayOfByte = CODE_LENGTHS; if (b < arrayOfByte.length) { addCode(b, CODES[b], arrayOfByte[b]); b++; continue; }  break; }  }
/*    */   public static Huffman get() { return INSTANCE; }
/* 22 */   byte[] decode(byte[] paramArrayOfbyte) { int k; Node node2; ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); Node node1 = this.root; byte b = 0; int j = 0; int i = 0; while (true) { node2 = node1; k = i; if (b < paramArrayOfbyte.length) { j = j << 8 | paramArrayOfbyte[b] & 0xFF; for (i += true; i >= 8; i -= 8) { node1 = node1.children[j >>> i - 8 & 0xFF]; if (node1.children == null) { byteArrayOutputStream.write(node1.symbol); i -= node1.terminalBits; node1 = this.root; continue; }  }  b++; continue; }  break; }  while (k > 0) { Node node = node2.children[j << 8 - k & 0xFF]; if (node.children != null || node.terminalBits > k) break;  byteArrayOutputStream.write(node.symbol); k -= node.terminalBits; node2 = this.root; }  return byteArrayOutputStream.toByteArray(); } void encode(ByteString paramByteString, BufferedSink paramBufferedSink) throws IOException { byte b = 0; long l = 0L; int i = 0; while (b < paramByteString.size()) { int k = paramByteString.getByte(b) & 0xFF; int j = CODES[k]; k = CODE_LENGTHS[k]; l = l << k | j; i += k; while (i >= 8) { i -= 8; paramBufferedSink.writeByte((int)(l >> i)); }  b++; }  if (i > 0) paramBufferedSink.writeByte((int)((255 >>> i) | l << 8 - i));  } int encodedLength(ByteString paramByteString) { long l = 0L; for (byte b = 0; b < paramByteString.size(); b++) { byte b1 = paramByteString.getByte(b); l += CODE_LENGTHS[b1 & 0xFF]; }  return (int)(l + 7L >> 3L); } private static int[] $d2j$hex$f7f6d28c$decode_I(String src) { byte[] d = $d2j$hex$f7f6d28c$decode_B(src);
/* 23 */     ByteBuffer b = ByteBuffer.wrap(d);
/* 24 */     b.order(ByteOrder.LITTLE_ENDIAN);
/* 25 */     IntBuffer s = b.asIntBuffer();
/* 26 */     int[] data = new int[d.length / 4];
/* 27 */     s.get(data);
/* 28 */     return data; }
/*    */ 
/*    */   
/*    */   private static short[] $d2j$hex$f7f6d28c$decode_S(String src) {
/* 32 */     byte[] d = $d2j$hex$f7f6d28c$decode_B(src);
/* 33 */     ByteBuffer b = ByteBuffer.wrap(d);
/* 34 */     b.order(ByteOrder.LITTLE_ENDIAN);
/* 35 */     ShortBuffer s = b.asShortBuffer();
/* 36 */     short[] data = new short[d.length / 2];
/* 37 */     s.get(data);
/* 38 */     return data;
/*    */   }
/*    */   
/*    */   private static byte[] $d2j$hex$f7f6d28c$decode_B(String src) {
/* 42 */     char[] d = src.toCharArray();
/* 43 */     byte[] ret = new byte[src.length() / 2];
/* 44 */     for (int i = 0; i < ret.length; i++) {
/* 45 */       int hh, ll; char h = d[2 * i];
/* 46 */       char l = d[2 * i + 1];
/*    */       
/* 48 */       if (h >= '0' && h <= '9') {
/* 49 */         hh = h - 48;
/* 50 */       } else if (h >= 'a' && h <= 'f') {
/* 51 */         hh = h - 97 + 10;
/* 52 */       } else if (h >= 'A' && h <= 'F') {
/* 53 */         hh = h - 65 + 10;
/*    */       } else {
/* 55 */         throw new RuntimeException();
/*    */       } 
/*    */       
/* 58 */       if (l >= '0' && l <= '9') {
/* 59 */         ll = l - 48;
/* 60 */       } else if (l >= 'a' && l <= 'f') {
/* 61 */         ll = l - 97 + 10;
/* 62 */       } else if (l >= 'A' && l <= 'F') {
/* 63 */         ll = l - 65 + 10;
/*    */       } else {
/* 65 */         throw new RuntimeException();
/*    */       } 
/* 67 */       ret[i] = (byte)(hh << 4 | ll);
/*    */     } 
/* 69 */     return ret;
/*    */   }
/*    */   
/*    */   private static final class Node {
/*    */     final Node[] children = new Node[256];
/*    */     final int symbol = 0;
/*    */     final int terminalBits;
/*    */     
/*    */     Node() {
/*    */       this.terminalBits = 0;
/*    */     }
/*    */     
/*    */     Node(int param1Int1, int param1Int2) {
/*    */       param1Int2 &= 0x7;
/*    */       param1Int1 = param1Int2;
/*    */       if (param1Int2 == 0)
/*    */         param1Int1 = 8; 
/*    */       this.terminalBits = param1Int1;
/*    */     }
/*    */   } }


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http2\Huffman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */