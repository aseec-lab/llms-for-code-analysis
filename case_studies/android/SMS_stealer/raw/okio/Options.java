package okio;

import java.util.AbstractList;
import java.util.RandomAccess;

public final class Options extends AbstractList<ByteString> implements RandomAccess {
  final ByteString[] byteStrings;
  
  private Options(ByteString[] paramArrayOfByteString) {
    this.byteStrings = paramArrayOfByteString;
  }
  
  public static Options of(ByteString... paramVarArgs) {
    return new Options((ByteString[])paramVarArgs.clone());
  }
  
  public ByteString get(int paramInt) {
    return this.byteStrings[paramInt];
  }
  
  public int size() {
    return this.byteStrings.length;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\Options.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */