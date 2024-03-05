package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ForwardingTimeout extends Timeout {
  private Timeout delegate;
  
  public ForwardingTimeout(Timeout paramTimeout) {
    if (paramTimeout != null) {
      this.delegate = paramTimeout;
      return;
    } 
    throw new IllegalArgumentException("delegate == null");
  }
  
  public Timeout clearDeadline() {
    return this.delegate.clearDeadline();
  }
  
  public Timeout clearTimeout() {
    return this.delegate.clearTimeout();
  }
  
  public long deadlineNanoTime() {
    return this.delegate.deadlineNanoTime();
  }
  
  public Timeout deadlineNanoTime(long paramLong) {
    return this.delegate.deadlineNanoTime(paramLong);
  }
  
  public final Timeout delegate() {
    return this.delegate;
  }
  
  public boolean hasDeadline() {
    return this.delegate.hasDeadline();
  }
  
  public final ForwardingTimeout setDelegate(Timeout paramTimeout) {
    if (paramTimeout != null) {
      this.delegate = paramTimeout;
      return this;
    } 
    throw new IllegalArgumentException("delegate == null");
  }
  
  public void throwIfReached() throws IOException {
    this.delegate.throwIfReached();
  }
  
  public Timeout timeout(long paramLong, TimeUnit paramTimeUnit) {
    return this.delegate.timeout(paramLong, paramTimeUnit);
  }
  
  public long timeoutNanos() {
    return this.delegate.timeoutNanos();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okio\ForwardingTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */