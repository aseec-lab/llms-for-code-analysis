package androidx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
public @interface RestrictTo {
  Scope[] value();
  
  public enum Scope {
    GROUP_ID, LIBRARY, LIBRARY_GROUP, LIBRARY_GROUP_PREFIX, SUBCLASSES, TESTS;
    
    private static final Scope[] $VALUES;
    
    static {
      Scope scope = new Scope("SUBCLASSES", 5);
      SUBCLASSES = scope;
      $VALUES = new Scope[] { LIBRARY, LIBRARY_GROUP, LIBRARY_GROUP_PREFIX, GROUP_ID, TESTS, scope };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\annotation\RestrictTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */