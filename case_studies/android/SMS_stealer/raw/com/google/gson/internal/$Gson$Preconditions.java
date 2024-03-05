package com.google.gson.internal;

public final class $Gson$Preconditions {
  private $Gson$Preconditions() {
    throw new UnsupportedOperationException();
  }
  
  public static void checkArgument(boolean paramBoolean) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException();
  }
  
  public static <T> T checkNotNull(T paramT) {
    if (paramT != null)
      return paramT; 
    throw null;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\$Gson$Preconditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */