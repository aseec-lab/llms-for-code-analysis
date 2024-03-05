package androidx.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface VisibleForTesting {
  public static final int NONE = 5;
  
  public static final int PACKAGE_PRIVATE = 3;
  
  public static final int PRIVATE = 2;
  
  public static final int PROTECTED = 4;
  
  int otherwise() default 2;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\annotation\VisibleForTesting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */