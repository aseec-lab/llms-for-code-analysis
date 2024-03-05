package com.google.gson.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class $Gson$Types {
  static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
  
  private $Gson$Types() {
    throw new UnsupportedOperationException();
  }
  
  public static GenericArrayType arrayOf(Type paramType) {
    return new GenericArrayTypeImpl(paramType);
  }
  
  public static Type canonicalize(Type paramType) {
    if (paramType instanceof Class) {
      Class clazz = (Class)paramType;
      paramType = clazz;
      if (clazz.isArray())
        paramType = new GenericArrayTypeImpl(canonicalize(clazz.getComponentType())); 
      return paramType;
    } 
    if (paramType instanceof ParameterizedType) {
      paramType = paramType;
      return new ParameterizedTypeImpl(paramType.getOwnerType(), paramType.getRawType(), paramType.getActualTypeArguments());
    } 
    if (paramType instanceof GenericArrayType)
      return new GenericArrayTypeImpl(((GenericArrayType)paramType).getGenericComponentType()); 
    if (paramType instanceof WildcardType) {
      paramType = paramType;
      return new WildcardTypeImpl(paramType.getUpperBounds(), paramType.getLowerBounds());
    } 
    return paramType;
  }
  
  static void checkNotPrimitive(Type paramType) {
    boolean bool;
    if (!(paramType instanceof Class) || !((Class)paramType).isPrimitive()) {
      bool = true;
    } else {
      bool = false;
    } 
    $Gson$Preconditions.checkArgument(bool);
  }
  
  private static Class<?> declaringClassOf(TypeVariable<?> paramTypeVariable) {
    paramTypeVariable = (TypeVariable<?>)paramTypeVariable.getGenericDeclaration();
    if (paramTypeVariable instanceof Class) {
      Class clazz = (Class)paramTypeVariable;
    } else {
      paramTypeVariable = null;
    } 
    return (Class<?>)paramTypeVariable;
  }
  
  static boolean equal(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2)));
  }
  
  public static boolean equals(Type paramType1, Type paramType2) {
    boolean bool3 = true;
    boolean bool2 = true;
    boolean bool1 = true;
    if (paramType1 == paramType2)
      return true; 
    if (paramType1 instanceof Class)
      return paramType1.equals(paramType2); 
    if (paramType1 instanceof ParameterizedType) {
      if (!(paramType2 instanceof ParameterizedType))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      if (!equal(paramType1.getOwnerType(), paramType2.getOwnerType()) || !paramType1.getRawType().equals(paramType2.getRawType()) || !Arrays.equals((Object[])paramType1.getActualTypeArguments(), (Object[])paramType2.getActualTypeArguments()))
        bool1 = false; 
      return bool1;
    } 
    if (paramType1 instanceof GenericArrayType) {
      if (!(paramType2 instanceof GenericArrayType))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      return equals(paramType1.getGenericComponentType(), paramType2.getGenericComponentType());
    } 
    if (paramType1 instanceof WildcardType) {
      if (!(paramType2 instanceof WildcardType))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      if (Arrays.equals((Object[])paramType1.getUpperBounds(), (Object[])paramType2.getUpperBounds()) && Arrays.equals((Object[])paramType1.getLowerBounds(), (Object[])paramType2.getLowerBounds())) {
        bool1 = bool3;
      } else {
        bool1 = false;
      } 
      return bool1;
    } 
    if (paramType1 instanceof TypeVariable) {
      if (!(paramType2 instanceof TypeVariable))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      if (paramType1.getGenericDeclaration() == paramType2.getGenericDeclaration() && paramType1.getName().equals(paramType2.getName())) {
        bool1 = bool2;
      } else {
        bool1 = false;
      } 
      return bool1;
    } 
    return false;
  }
  
  public static Type getArrayComponentType(Type<?> paramType) {
    if (paramType instanceof GenericArrayType) {
      paramType = ((GenericArrayType)paramType).getGenericComponentType();
    } else {
      paramType = ((Class)paramType).getComponentType();
    } 
    return paramType;
  }
  
  public static Type getCollectionElementType(Type paramType, Class<?> paramClass) {
    Type type = getSupertype(paramType, paramClass, Collection.class);
    paramType = type;
    if (type instanceof WildcardType)
      paramType = ((WildcardType)type).getUpperBounds()[0]; 
    return (paramType instanceof ParameterizedType) ? ((ParameterizedType)paramType).getActualTypeArguments()[0] : Object.class;
  }
  
  static Type getGenericSupertype(Type<?> paramType, Class<?> paramClass1, Class<?> paramClass2) {
    if (paramClass2 == paramClass1)
      return paramType; 
    if (paramClass2.isInterface()) {
      Class[] arrayOfClass = paramClass1.getInterfaces();
      byte b = 0;
      int i = arrayOfClass.length;
      while (b < i) {
        if (arrayOfClass[b] == paramClass2)
          return paramClass1.getGenericInterfaces()[b]; 
        if (paramClass2.isAssignableFrom(arrayOfClass[b]))
          return getGenericSupertype(paramClass1.getGenericInterfaces()[b], arrayOfClass[b], paramClass2); 
        b++;
      } 
    } 
    if (!paramClass1.isInterface())
      while (paramClass1 != Object.class) {
        paramType = paramClass1.getSuperclass();
        if (paramType == paramClass2)
          return paramClass1.getGenericSuperclass(); 
        if (paramClass2.isAssignableFrom((Class<?>)paramType))
          return getGenericSupertype(paramClass1.getGenericSuperclass(), (Class<?>)paramType, paramClass2); 
        Type<?> type = paramType;
      }  
    return paramClass2;
  }
  
  public static Type[] getMapKeyAndValueTypes(Type paramType, Class<?> paramClass) {
    if (paramType == Properties.class)
      return new Type[] { String.class, String.class }; 
    paramType = getSupertype(paramType, paramClass, Map.class);
    return (paramType instanceof ParameterizedType) ? ((ParameterizedType)paramType).getActualTypeArguments() : new Type[] { Object.class, Object.class };
  }
  
  public static Class<?> getRawType(Type paramType) {
    String str;
    if (paramType instanceof Class)
      return (Class)paramType; 
    if (paramType instanceof ParameterizedType) {
      paramType = ((ParameterizedType)paramType).getRawType();
      $Gson$Preconditions.checkArgument(paramType instanceof Class);
      return (Class)paramType;
    } 
    if (paramType instanceof GenericArrayType)
      return Array.newInstance(getRawType(((GenericArrayType)paramType).getGenericComponentType()), 0).getClass(); 
    if (paramType instanceof TypeVariable)
      return Object.class; 
    if (paramType instanceof WildcardType)
      return getRawType(((WildcardType)paramType).getUpperBounds()[0]); 
    if (paramType == null) {
      str = "null";
    } else {
      str = paramType.getClass().getName();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected a Class, ParameterizedType, or GenericArrayType, but <");
    stringBuilder.append(paramType);
    stringBuilder.append("> is of type ");
    stringBuilder.append(str);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  static Type getSupertype(Type paramType, Class<?> paramClass1, Class<?> paramClass2) {
    $Gson$Preconditions.checkArgument(paramClass2.isAssignableFrom(paramClass1));
    return resolve(paramType, paramClass1, getGenericSupertype(paramType, paramClass1, paramClass2));
  }
  
  static int hashCodeOrZero(Object paramObject) {
    boolean bool;
    if (paramObject != null) {
      bool = paramObject.hashCode();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static int indexOf(Object[] paramArrayOfObject, Object paramObject) {
    int i = paramArrayOfObject.length;
    for (byte b = 0; b < i; b++) {
      if (paramObject.equals(paramArrayOfObject[b]))
        return b; 
    } 
    throw new NoSuchElementException();
  }
  
  public static ParameterizedType newParameterizedTypeWithOwner(Type paramType1, Type paramType2, Type... paramVarArgs) {
    return new ParameterizedTypeImpl(paramType1, paramType2, paramVarArgs);
  }
  
  public static Type resolve(Type paramType1, Class<?> paramClass, Type paramType2) {
    return resolve(paramType1, paramClass, paramType2, new HashSet<TypeVariable>());
  }
  
  private static Type resolve(Type<?> paramType1, Class<?> paramClass, Type<?> paramType2, Collection<TypeVariable> paramCollection) {
    Type[] arrayOfType1;
    Type type;
    while (paramType2 instanceof TypeVariable) {
      TypeVariable<?> typeVariable = (TypeVariable)paramType2;
      if (paramCollection.contains(typeVariable))
        return paramType2; 
      paramCollection.add(typeVariable);
      type = resolveTypeVariable(paramType1, paramClass, typeVariable);
      paramType2 = type;
      if (type == typeVariable)
        return type; 
    } 
    if (paramType2 instanceof Class) {
      type = paramType2;
      if (type.isArray()) {
        paramType2 = type.getComponentType();
        paramType1 = resolve(paramType1, paramClass, paramType2, paramCollection);
        if (paramType2 == paramType1) {
          paramType1 = type;
        } else {
          paramType1 = arrayOf(paramType1);
        } 
        return paramType1;
      } 
    } 
    if (paramType2 instanceof GenericArrayType) {
      paramType2 = paramType2;
      type = paramType2.getGenericComponentType();
      paramType1 = resolve(paramType1, paramClass, type, paramCollection);
      if (type == paramType1) {
        paramType1 = paramType2;
      } else {
        paramType1 = arrayOf(paramType1);
      } 
      return paramType1;
    } 
    boolean bool = paramType2 instanceof ParameterizedType;
    byte b = 0;
    if (bool) {
      boolean bool1;
      ParameterizedType parameterizedType = (ParameterizedType)paramType2;
      paramType2 = parameterizedType.getOwnerType();
      Type type1 = resolve(paramType1, paramClass, paramType2, paramCollection);
      if (type1 != paramType2) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      arrayOfType1 = parameterizedType.getActualTypeArguments();
      int i = arrayOfType1.length;
      while (b < i) {
        Type type2 = resolve(paramType1, paramClass, arrayOfType1[b], paramCollection);
        boolean bool2 = bool1;
        Type[] arrayOfType = arrayOfType1;
        if (type2 != arrayOfType1[b]) {
          bool2 = bool1;
          arrayOfType = arrayOfType1;
          if (!bool1) {
            arrayOfType = (Type[])arrayOfType1.clone();
            bool2 = true;
          } 
          arrayOfType[b] = type2;
        } 
        b++;
        bool1 = bool2;
        arrayOfType1 = arrayOfType;
      } 
      paramType1 = parameterizedType;
      if (bool1)
        paramType1 = newParameterizedTypeWithOwner(type1, parameterizedType.getRawType(), arrayOfType1); 
      return paramType1;
    } 
    Type[] arrayOfType2 = arrayOfType1;
    if (arrayOfType1 instanceof WildcardType) {
      WildcardType wildcardType = (WildcardType)arrayOfType1;
      Type[] arrayOfType4 = wildcardType.getLowerBounds();
      Type[] arrayOfType3 = wildcardType.getUpperBounds();
      if (arrayOfType4.length == 1) {
        paramType1 = resolve(paramType1, paramClass, arrayOfType4[0], paramCollection);
        type = wildcardType;
        if (paramType1 != arrayOfType4[0])
          return supertypeOf(paramType1); 
      } else {
        type = wildcardType;
        if (arrayOfType3.length == 1) {
          type = arrayOfType3[0];
          try {
            paramType1 = resolve(paramType1, paramClass, type, paramCollection);
            type = wildcardType;
            return (paramType1 != arrayOfType3[0]) ? subtypeOf(paramType1) : type;
          } finally {}
        } 
      } 
    } 
    return type;
  }
  
  static Type resolveTypeVariable(Type paramType, Class<?> paramClass, TypeVariable<?> paramTypeVariable) {
    Class<?> clazz = declaringClassOf(paramTypeVariable);
    if (clazz == null)
      return paramTypeVariable; 
    paramType = getGenericSupertype(paramType, paramClass, clazz);
    if (paramType instanceof ParameterizedType) {
      int i = indexOf((Object[])clazz.getTypeParameters(), paramTypeVariable);
      return ((ParameterizedType)paramType).getActualTypeArguments()[i];
    } 
    return paramTypeVariable;
  }
  
  public static WildcardType subtypeOf(Type paramType) {
    Type[] arrayOfType;
    if (paramType instanceof WildcardType) {
      arrayOfType = ((WildcardType)paramType).getUpperBounds();
    } else {
      arrayOfType = new Type[] { (Type)arrayOfType };
    } 
    return new WildcardTypeImpl(arrayOfType, EMPTY_TYPE_ARRAY);
  }
  
  public static WildcardType supertypeOf(Type paramType) {
    Type[] arrayOfType;
    if (paramType instanceof WildcardType) {
      arrayOfType = ((WildcardType)paramType).getLowerBounds();
    } else {
      arrayOfType = new Type[] { (Type)arrayOfType };
    } 
    return new WildcardTypeImpl(new Type[] { Object.class }, arrayOfType);
  }
  
  public static String typeToString(Type paramType) {
    String str;
    if (paramType instanceof Class) {
      str = ((Class)paramType).getName();
    } else {
      str = str.toString();
    } 
    return str;
  }
  
  private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
    private static final long serialVersionUID = 0L;
    
    private final Type componentType;
    
    public GenericArrayTypeImpl(Type param1Type) {
      this.componentType = $Gson$Types.canonicalize(param1Type);
    }
    
    public boolean equals(Object param1Object) {
      boolean bool;
      if (param1Object instanceof GenericArrayType && $Gson$Types.equals(this, (GenericArrayType)param1Object)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Type getGenericComponentType() {
      return this.componentType;
    }
    
    public int hashCode() {
      return this.componentType.hashCode();
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append($Gson$Types.typeToString(this.componentType));
      stringBuilder.append("[]");
      return stringBuilder.toString();
    }
  }
  
  private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
    private static final long serialVersionUID = 0L;
    
    private final Type ownerType;
    
    private final Type rawType;
    
    private final Type[] typeArguments;
    
    public ParameterizedTypeImpl(Type param1Type1, Type param1Type2, Type... param1VarArgs) {
      boolean bool = param1Type2 instanceof Class;
      byte b2 = 0;
      if (bool) {
        boolean bool1;
        Class clazz = (Class)param1Type2;
        bool = Modifier.isStatic(clazz.getModifiers());
        boolean bool2 = true;
        if (bool || clazz.getEnclosingClass() == null) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        bool = bool2;
        if (param1Type1 == null)
          if (bool1) {
            bool = bool2;
          } else {
            bool = false;
          }  
        $Gson$Preconditions.checkArgument(bool);
      } 
      if (param1Type1 == null) {
        param1Type1 = null;
      } else {
        param1Type1 = $Gson$Types.canonicalize(param1Type1);
      } 
      this.ownerType = param1Type1;
      this.rawType = $Gson$Types.canonicalize(param1Type2);
      Type[] arrayOfType = (Type[])param1VarArgs.clone();
      this.typeArguments = arrayOfType;
      int i = arrayOfType.length;
      for (byte b1 = b2; b1 < i; b1++) {
        $Gson$Preconditions.checkNotNull(this.typeArguments[b1]);
        $Gson$Types.checkNotPrimitive(this.typeArguments[b1]);
        arrayOfType = this.typeArguments;
        arrayOfType[b1] = $Gson$Types.canonicalize(arrayOfType[b1]);
      } 
    }
    
    public boolean equals(Object param1Object) {
      boolean bool;
      if (param1Object instanceof ParameterizedType && $Gson$Types.equals(this, (ParameterizedType)param1Object)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Type[] getActualTypeArguments() {
      return (Type[])this.typeArguments.clone();
    }
    
    public Type getOwnerType() {
      return this.ownerType;
    }
    
    public Type getRawType() {
      return this.rawType;
    }
    
    public int hashCode() {
      return Arrays.hashCode((Object[])this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.hashCodeOrZero(this.ownerType);
    }
    
    public String toString() {
      int i = this.typeArguments.length;
      if (i == 0)
        return $Gson$Types.typeToString(this.rawType); 
      StringBuilder stringBuilder = new StringBuilder((i + 1) * 30);
      stringBuilder.append($Gson$Types.typeToString(this.rawType));
      stringBuilder.append("<");
      stringBuilder.append($Gson$Types.typeToString(this.typeArguments[0]));
      for (byte b = 1; b < i; b++) {
        stringBuilder.append(", ");
        stringBuilder.append($Gson$Types.typeToString(this.typeArguments[b]));
      } 
      stringBuilder.append(">");
      return stringBuilder.toString();
    }
  }
  
  private static final class WildcardTypeImpl implements WildcardType, Serializable {
    private static final long serialVersionUID = 0L;
    
    private final Type lowerBound;
    
    private final Type upperBound;
    
    public WildcardTypeImpl(Type[] param1ArrayOfType1, Type[] param1ArrayOfType2) {
      boolean bool1;
      int i = param1ArrayOfType2.length;
      boolean bool2 = true;
      if (i <= 1) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      $Gson$Preconditions.checkArgument(bool1);
      if (param1ArrayOfType1.length == 1) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      $Gson$Preconditions.checkArgument(bool1);
      if (param1ArrayOfType2.length == 1) {
        $Gson$Preconditions.checkNotNull(param1ArrayOfType2[0]);
        $Gson$Types.checkNotPrimitive(param1ArrayOfType2[0]);
        if (param1ArrayOfType1[0] == Object.class) {
          bool1 = bool2;
        } else {
          bool1 = false;
        } 
        $Gson$Preconditions.checkArgument(bool1);
        this.lowerBound = $Gson$Types.canonicalize(param1ArrayOfType2[0]);
        this.upperBound = Object.class;
      } else {
        $Gson$Preconditions.checkNotNull(param1ArrayOfType1[0]);
        $Gson$Types.checkNotPrimitive(param1ArrayOfType1[0]);
        this.lowerBound = null;
        this.upperBound = $Gson$Types.canonicalize(param1ArrayOfType1[0]);
      } 
    }
    
    public boolean equals(Object param1Object) {
      boolean bool;
      if (param1Object instanceof WildcardType && $Gson$Types.equals(this, (WildcardType)param1Object)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Type[] getLowerBounds() {
      Type[] arrayOfType;
      Type type = this.lowerBound;
      if (type != null) {
        arrayOfType = new Type[1];
        arrayOfType[0] = type;
      } else {
        arrayOfType = $Gson$Types.EMPTY_TYPE_ARRAY;
      } 
      return arrayOfType;
    }
    
    public Type[] getUpperBounds() {
      return new Type[] { this.upperBound };
    }
    
    public int hashCode() {
      boolean bool;
      Type type = this.lowerBound;
      if (type != null) {
        bool = type.hashCode() + 31;
      } else {
        bool = true;
      } 
      return bool ^ this.upperBound.hashCode() + 31;
    }
    
    public String toString() {
      if (this.lowerBound != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("? super ");
        stringBuilder1.append($Gson$Types.typeToString(this.lowerBound));
        return stringBuilder1.toString();
      } 
      if (this.upperBound == Object.class)
        return "?"; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("? extends ");
      stringBuilder.append($Gson$Types.typeToString(this.upperBound));
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\$Gson$Types.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */