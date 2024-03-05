package androidx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface InspectableProperty {
  int attributeId() default 0;
  
  EnumEntry[] enumMapping() default {};
  
  FlagEntry[] flagMapping() default {};
  
  boolean hasAttributeId() default true;
  
  String name() default "";
  
  ValueType valueType() default ValueType.INFERRED;
  
  @Retention(RetentionPolicy.SOURCE)
  @Target({ElementType.TYPE})
  public static @interface EnumEntry {
    String name();
    
    int value();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @Target({ElementType.TYPE})
  public static @interface FlagEntry {
    int mask() default 0;
    
    String name();
    
    int target();
  }
  
  public enum ValueType {
    COLOR, GRAVITY, INFERRED, INT_ENUM, INT_FLAG, NONE, RESOURCE_ID;
    
    private static final ValueType[] $VALUES;
    
    static {
      COLOR = new ValueType("COLOR", 4);
      GRAVITY = new ValueType("GRAVITY", 5);
      ValueType valueType = new ValueType("RESOURCE_ID", 6);
      RESOURCE_ID = valueType;
      $VALUES = new ValueType[] { NONE, INFERRED, INT_ENUM, INT_FLAG, COLOR, GRAVITY, valueType };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\annotation\InspectableProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */