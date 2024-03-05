package com.google.gson.stream;

import java.io.IOException;

public final class MalformedJsonException extends IOException {
  private static final long serialVersionUID = 1L;
  
  public MalformedJsonException(String paramString) {
    super(paramString);
  }
  
  public MalformedJsonException(String paramString, Throwable paramThrowable) {
    super(paramString);
    initCause(paramThrowable);
  }
  
  public MalformedJsonException(Throwable paramThrowable) {
    initCause(paramThrowable);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\stream\MalformedJsonException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */