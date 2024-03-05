package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class RouteException extends RuntimeException {
  private static final Method addSuppressedExceptionMethod;
  
  private IOException lastException;
  
  static {
    try {
      Method method = Throwable.class.getDeclaredMethod("addSuppressed", new Class[] { Throwable.class });
    } catch (Exception exception) {
      exception = null;
    } 
    addSuppressedExceptionMethod = (Method)exception;
  }
  
  public RouteException(IOException paramIOException) {
    super(paramIOException);
    this.lastException = paramIOException;
  }
  
  private void addSuppressedIfPossible(IOException paramIOException1, IOException paramIOException2) {
    Method method = addSuppressedExceptionMethod;
    if (method != null)
      try {
        method.invoke(paramIOException1, new Object[] { paramIOException2 });
      } catch (InvocationTargetException|IllegalAccessException invocationTargetException) {} 
  }
  
  public void addConnectException(IOException paramIOException) {
    addSuppressedIfPossible(paramIOException, this.lastException);
    this.lastException = paramIOException;
  }
  
  public IOException getLastConnectException() {
    return this.lastException;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\connection\RouteException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */