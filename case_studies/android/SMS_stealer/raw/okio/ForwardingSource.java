package okio;

import java.io.IOException;

public abstract class ForwardingSource implements Source {
  private final Source delegate;
  
  public ForwardingSource(Source paramSource) {
    if (paramSource != null) {
      this.delegate = paramSource;
      return;
    } 
    throw new IllegalArgumentException("delegate == null");
  }
  
  public void close() throws IOException {
    this.delegate.close();
  }
  
  public final Source delegate() {
    return this.delegate;
  }
  
  public long read(Buffer paramBuffer, long paramLong) throws IOException {
    return this.delegate.read(paramBuffer, paramLong);
  }
  
  public Timeout timeout() {
    return this.delegate.timeout();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getClass().getSimpleName());
    stringBuilder.append("(");
    stringBuilder.append(this.delegate.toString());
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\ForwardingSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */