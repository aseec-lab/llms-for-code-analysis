package androidx.core.util;

import java.util.Locale;

public final class Preconditions {
  public static void checkArgument(boolean paramBoolean) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException();
  }
  
  public static void checkArgument(boolean paramBoolean, Object paramObject) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException(String.valueOf(paramObject));
  }
  
  public static int checkArgumentInRange(int paramInt1, int paramInt2, int paramInt3, String paramString) {
    if (paramInt1 >= paramInt2) {
      if (paramInt1 <= paramInt3)
        return paramInt1; 
      throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", new Object[] { paramString, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) }));
    } 
    throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", new Object[] { paramString, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) }));
  }
  
  public static int checkArgumentNonnegative(int paramInt) {
    if (paramInt >= 0)
      return paramInt; 
    throw new IllegalArgumentException();
  }
  
  public static int checkArgumentNonnegative(int paramInt, String paramString) {
    if (paramInt >= 0)
      return paramInt; 
    throw new IllegalArgumentException(paramString);
  }
  
  public static <T> T checkNotNull(T paramT) {
    if (paramT != null)
      return paramT; 
    throw null;
  }
  
  public static <T> T checkNotNull(T paramT, Object paramObject) {
    if (paramT != null)
      return paramT; 
    throw new NullPointerException(String.valueOf(paramObject));
  }
  
  public static void checkState(boolean paramBoolean) {
    checkState(paramBoolean, null);
  }
  
  public static void checkState(boolean paramBoolean, String paramString) {
    if (paramBoolean)
      return; 
    throw new IllegalStateException(paramString);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\cor\\util\Preconditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */