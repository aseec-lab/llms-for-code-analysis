package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

public class ConstraintAttribute {
  private static final String TAG = "TransitionLayout";
  
  boolean mBooleanValue;
  
  private int mColorValue;
  
  private float mFloatValue;
  
  private int mIntegerValue;
  
  String mName;
  
  private String mStringValue;
  
  private AttributeType mType;
  
  public ConstraintAttribute(ConstraintAttribute paramConstraintAttribute, Object paramObject) {
    this.mName = paramConstraintAttribute.mName;
    this.mType = paramConstraintAttribute.mType;
    setValue(paramObject);
  }
  
  public ConstraintAttribute(String paramString, AttributeType paramAttributeType) {
    this.mName = paramString;
    this.mType = paramAttributeType;
  }
  
  public ConstraintAttribute(String paramString, AttributeType paramAttributeType, Object paramObject) {
    this.mName = paramString;
    this.mType = paramAttributeType;
    setValue(paramObject);
  }
  
  private static int clamp(int paramInt) {
    paramInt = (paramInt & (paramInt >> 31 ^ 0xFFFFFFFF)) - 255;
    return (paramInt & paramInt >> 31) + 255;
  }
  
  public static HashMap<String, ConstraintAttribute> extractAttributes(HashMap<String, ConstraintAttribute> paramHashMap, View paramView) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Class<?> clazz = paramView.getClass();
    for (String str : paramHashMap.keySet()) {
      ConstraintAttribute constraintAttribute = paramHashMap.get(str);
      try {
        if (str.equals("BackgroundColor")) {
          int i = ((ColorDrawable)paramView.getBackground()).getColor();
          ConstraintAttribute constraintAttribute2 = new ConstraintAttribute();
          this(constraintAttribute, Integer.valueOf(i));
          hashMap.put(str, constraintAttribute2);
          continue;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("getMap");
        stringBuilder.append(str);
        Object object = clazz.getMethod(stringBuilder.toString(), new Class[0]).invoke(paramView, new Object[0]);
        ConstraintAttribute constraintAttribute1 = new ConstraintAttribute();
        this(constraintAttribute, object);
        hashMap.put(str, constraintAttribute1);
      } catch (NoSuchMethodException noSuchMethodException) {
        noSuchMethodException.printStackTrace();
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
      } catch (InvocationTargetException invocationTargetException) {
        invocationTargetException.printStackTrace();
      } 
    } 
    return (HashMap)hashMap;
  }
  
  public static void parse(Context paramContext, XmlPullParser paramXmlPullParser, HashMap<String, ConstraintAttribute> paramHashMap) {
    String str;
    TypedArray typedArray = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.CustomAttribute);
    int i = typedArray.getIndexCount();
    XmlPullParser xmlPullParser = null;
    Object object2 = null;
    Object object1 = null;
    byte b = 0;
    while (b < i) {
      AttributeType attributeType1;
      int j = typedArray.getIndex(b);
      if (j == R.styleable.CustomAttribute_attributeName) {
        str = typedArray.getString(j);
        String str2 = str;
        Object object3 = object2;
        Object object4 = object1;
        if (str != null) {
          str2 = str;
          object3 = object2;
          object4 = object1;
          if (str.length() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Character.toUpperCase(str.charAt(0)));
            stringBuilder.append(str.substring(1));
            String str3 = stringBuilder.toString();
            object3 = object2;
            object4 = object1;
          } 
        } 
        continue;
      } 
      if (j == R.styleable.CustomAttribute_customBoolean) {
        Boolean bool = Boolean.valueOf(typedArray.getBoolean(j, false));
        AttributeType attributeType = AttributeType.BOOLEAN_TYPE;
        String str2 = str;
        continue;
      } 
      if (j == R.styleable.CustomAttribute_customColorValue) {
        attributeType1 = AttributeType.COLOR_TYPE;
        Integer integer = Integer.valueOf(typedArray.getColor(j, 0));
      } else if (j == R.styleable.CustomAttribute_customColorDrawableValue) {
        attributeType1 = AttributeType.COLOR_DRAWABLE_TYPE;
        Integer integer = Integer.valueOf(typedArray.getColor(j, 0));
      } else if (j == R.styleable.CustomAttribute_customPixelDimension) {
        attributeType1 = AttributeType.DIMENSION_TYPE;
        Float float_ = Float.valueOf(TypedValue.applyDimension(1, typedArray.getDimension(j, 0.0F), paramContext.getResources().getDisplayMetrics()));
      } else if (j == R.styleable.CustomAttribute_customDimension) {
        attributeType1 = AttributeType.DIMENSION_TYPE;
        Float float_ = Float.valueOf(typedArray.getDimension(j, 0.0F));
      } else if (j == R.styleable.CustomAttribute_customFloatValue) {
        attributeType1 = AttributeType.FLOAT_TYPE;
        Float float_ = Float.valueOf(typedArray.getFloat(j, Float.NaN));
      } else if (j == R.styleable.CustomAttribute_customIntegerValue) {
        attributeType1 = AttributeType.INT_TYPE;
        Integer integer = Integer.valueOf(typedArray.getInteger(j, -1));
      } else {
        String str2 = str;
        Object object3 = object2;
        Object object4 = object1;
        if (j == R.styleable.CustomAttribute_customStringValue) {
          attributeType1 = AttributeType.STRING_TYPE;
          object3 = typedArray.getString(j);
        } else {
          continue;
        } 
      } 
      AttributeType attributeType2 = attributeType1;
      String str1 = str;
      continue;
      b++;
      xmlPullParser = paramXmlPullParser;
      object2 = SYNTHETIC_LOCAL_VARIABLE_6;
      object1 = SYNTHETIC_LOCAL_VARIABLE_7;
    } 
    if (str != null && object2 != null)
      paramHashMap.put(str, new ConstraintAttribute(str, (AttributeType)object1, object2)); 
    typedArray.recycle();
  }
  
  public static void setAttributes(View paramView, HashMap<String, ConstraintAttribute> paramHashMap) {
    Class<?> clazz = paramView.getClass();
    for (String str1 : paramHashMap.keySet()) {
      StringBuilder stringBuilder1;
      ConstraintAttribute constraintAttribute = paramHashMap.get(str1);
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("set");
      stringBuilder2.append(str1);
      String str2 = stringBuilder2.toString();
      try {
        ColorDrawable colorDrawable;
        Method method;
        switch (constraintAttribute.mType) {
          default:
            continue;
          case null:
            clazz.getMethod(str2, new Class[] { float.class }).invoke(paramView, new Object[] { Float.valueOf(constraintAttribute.mFloatValue) });
            continue;
          case null:
            clazz.getMethod(str2, new Class[] { boolean.class }).invoke(paramView, new Object[] { Boolean.valueOf(constraintAttribute.mBooleanValue) });
            continue;
          case null:
            clazz.getMethod(str2, new Class[] { CharSequence.class }).invoke(paramView, new Object[] { constraintAttribute.mStringValue });
            continue;
          case null:
            clazz.getMethod(str2, new Class[] { float.class }).invoke(paramView, new Object[] { Float.valueOf(constraintAttribute.mFloatValue) });
            continue;
          case null:
            clazz.getMethod(str2, new Class[] { int.class }).invoke(paramView, new Object[] { Integer.valueOf(constraintAttribute.mIntegerValue) });
            continue;
          case null:
            method = clazz.getMethod(str2, new Class[] { Drawable.class });
            colorDrawable = new ColorDrawable();
            this();
            colorDrawable.setColor(constraintAttribute.mColorValue);
            method.invoke(paramView, new Object[] { colorDrawable });
            continue;
          case null:
            break;
        } 
        clazz.getMethod(str2, new Class[] { int.class }).invoke(paramView, new Object[] { Integer.valueOf(constraintAttribute.mColorValue) });
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.e("TransitionLayout", noSuchMethodException.getMessage());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Custom Attribute \"");
        stringBuilder.append(str1);
        stringBuilder.append("\" not found on ");
        stringBuilder.append(clazz.getName());
        Log.e("TransitionLayout", stringBuilder.toString());
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append(clazz.getName());
        stringBuilder1.append(" must have a method ");
        stringBuilder1.append(str2);
        Log.e("TransitionLayout", stringBuilder1.toString());
      } catch (IllegalAccessException illegalAccessException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Custom Attribute \"");
        stringBuilder.append((String)stringBuilder1);
        stringBuilder.append("\" not found on ");
        stringBuilder.append(clazz.getName());
        Log.e("TransitionLayout", stringBuilder.toString());
        illegalAccessException.printStackTrace();
      } catch (InvocationTargetException invocationTargetException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Custom Attribute \"");
        stringBuilder.append((String)stringBuilder1);
        stringBuilder.append("\" not found on ");
        stringBuilder.append(clazz.getName());
        Log.e("TransitionLayout", stringBuilder.toString());
        invocationTargetException.printStackTrace();
      } 
    } 
  }
  
  public boolean diff(ConstraintAttribute paramConstraintAttribute) {
    boolean bool5 = false;
    boolean bool2 = false;
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool7 = false;
    boolean bool6 = false;
    boolean bool1 = bool7;
    if (paramConstraintAttribute != null)
      if (this.mType != paramConstraintAttribute.mType) {
        bool1 = bool7;
      } else {
        switch (this.mType) {
          default:
            return false;
          case null:
            bool1 = bool6;
            if (this.mFloatValue == paramConstraintAttribute.mFloatValue)
              bool1 = true; 
            return bool1;
          case null:
            bool1 = bool5;
            if (this.mBooleanValue == paramConstraintAttribute.mBooleanValue)
              bool1 = true; 
            return bool1;
          case null:
            bool1 = bool2;
            if (this.mIntegerValue == paramConstraintAttribute.mIntegerValue)
              bool1 = true; 
            return bool1;
          case null:
            bool1 = bool4;
            if (this.mFloatValue == paramConstraintAttribute.mFloatValue)
              bool1 = true; 
            return bool1;
          case null:
            bool1 = bool3;
            if (this.mIntegerValue == paramConstraintAttribute.mIntegerValue)
              bool1 = true; 
            return bool1;
          case null:
          case null:
            break;
        } 
        bool1 = bool7;
        if (this.mColorValue == paramConstraintAttribute.mColorValue)
          bool1 = true; 
      }  
    return bool1;
  }
  
  public AttributeType getType() {
    return this.mType;
  }
  
  public float getValueToInterpolate() {
    float f;
    switch (this.mType) {
      default:
        return Float.NaN;
      case null:
        return this.mFloatValue;
      case null:
        if (this.mBooleanValue) {
          f = 1.0F;
        } else {
          f = 0.0F;
        } 
        return f;
      case null:
        throw new RuntimeException("Cannot interpolate String");
      case null:
        return this.mFloatValue;
      case null:
        return this.mIntegerValue;
      case null:
      case null:
        break;
    } 
    throw new RuntimeException("Color does not have a single color to interpolate");
  }
  
  public void getValuesToInterpolate(float[] paramArrayOffloat) {
    switch (this.mType) {
      default:
        return;
      case null:
        paramArrayOffloat[0] = this.mFloatValue;
      case null:
        if (this.mBooleanValue) {
          f1 = 1.0F;
        } else {
          f1 = 0.0F;
        } 
        paramArrayOffloat[0] = f1;
      case null:
        throw new RuntimeException("Color does not have a single color to interpolate");
      case null:
        paramArrayOffloat[0] = this.mFloatValue;
      case null:
        paramArrayOffloat[0] = this.mIntegerValue;
      case null:
      case null:
        break;
    } 
    int i = this.mColorValue;
    float f2 = (float)Math.pow(((i >> 16 & 0xFF) / 255.0F), 2.2D);
    float f3 = (float)Math.pow(((i >> 8 & 0xFF) / 255.0F), 2.2D);
    float f1 = (float)Math.pow(((i & 0xFF) / 255.0F), 2.2D);
    paramArrayOffloat[0] = f2;
    paramArrayOffloat[1] = f3;
    paramArrayOffloat[2] = f1;
    paramArrayOffloat[3] = (i >> 24 & 0xFF) / 255.0F;
  }
  
  public int noOfInterpValues() {
    int i = null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
    return (i != 1 && i != 2) ? 1 : 4;
  }
  
  public void setColorValue(int paramInt) {
    this.mColorValue = paramInt;
  }
  
  public void setFloatValue(float paramFloat) {
    this.mFloatValue = paramFloat;
  }
  
  public void setIntValue(int paramInt) {
    this.mIntegerValue = paramInt;
  }
  
  public void setInterpolatedValue(View paramView, float[] paramArrayOffloat) {
    Class<?> clazz = paramView.getClass();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("set");
    stringBuilder.append(this.mName);
    String str = stringBuilder.toString();
    try {
      StringBuilder stringBuilder1;
      ColorDrawable colorDrawable;
      int m;
      Method method2;
      RuntimeException runtimeException;
      int i = null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
      boolean bool = true;
      switch (i) {
        default:
          return;
        case 7:
          clazz.getMethod(str, new Class[] { float.class }).invoke(paramView, new Object[] { Float.valueOf(paramArrayOffloat[0]) });
        case 6:
          method2 = clazz.getMethod(str, new Class[] { boolean.class });
          if (paramArrayOffloat[0] <= 0.5F)
            bool = false; 
          method2.invoke(paramView, new Object[] { Boolean.valueOf(bool) });
        case 5:
          runtimeException = new RuntimeException();
          stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append("unable to interpolate strings ");
          stringBuilder1.append(this.mName);
          this(stringBuilder1.toString());
          throw runtimeException;
        case 4:
          runtimeException.getMethod(str, new Class[] { float.class }).invoke(paramView, new Object[] { Float.valueOf(stringBuilder1[0]) });
        case 3:
          runtimeException.getMethod(str, new Class[] { int.class }).invoke(paramView, new Object[] { Integer.valueOf((int)stringBuilder1[0]) });
        case 2:
          method1 = runtimeException.getMethod(str, new Class[] { Drawable.class });
          i = clamp((int)((float)Math.pow(stringBuilder1[0], 0.45454545454545453D) * 255.0F));
          j = clamp((int)((float)Math.pow(stringBuilder1[1], 0.45454545454545453D) * 255.0F));
          k = clamp((int)((float)Math.pow(stringBuilder1[2], 0.45454545454545453D) * 255.0F));
          m = clamp((int)(stringBuilder1[3] * 255.0F));
          colorDrawable = new ColorDrawable();
          this();
          colorDrawable.setColor(i << 16 | m << 24 | j << 8 | k);
          method1.invoke(paramView, new Object[] { colorDrawable });
        case 1:
          break;
      } 
      Method method1 = method1.getMethod(str, new Class[] { int.class });
      int j = clamp((int)((float)Math.pow(colorDrawable[0], 0.45454545454545453D) * 255.0F));
      i = clamp((int)((float)Math.pow(colorDrawable[1], 0.45454545454545453D) * 255.0F));
      int k = clamp((int)((float)Math.pow(colorDrawable[2], 0.45454545454545453D) * 255.0F));
      method1.invoke(paramView, new Object[] { Integer.valueOf(j << 16 | clamp((int)(colorDrawable[3] * 255.0F)) << 24 | i << 8 | k) });
    } catch (NoSuchMethodException noSuchMethodException) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("no method ");
      stringBuilder1.append(str);
      stringBuilder1.append("on View \"");
      stringBuilder1.append(Debug.getName(paramView));
      stringBuilder1.append("\"");
      Log.e("TransitionLayout", stringBuilder1.toString());
      noSuchMethodException.printStackTrace();
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("cannot access method ");
      stringBuilder1.append(str);
      stringBuilder1.append("on View \"");
      stringBuilder1.append(Debug.getName(paramView));
      stringBuilder1.append("\"");
      Log.e("TransitionLayout", stringBuilder1.toString());
      illegalAccessException.printStackTrace();
    } catch (InvocationTargetException invocationTargetException) {
      invocationTargetException.printStackTrace();
    } 
  }
  
  public void setStringValue(String paramString) {
    this.mStringValue = paramString;
  }
  
  public void setValue(Object paramObject) {
    switch (this.mType) {
      default:
        return;
      case null:
        this.mFloatValue = ((Float)paramObject).floatValue();
      case null:
        this.mBooleanValue = ((Boolean)paramObject).booleanValue();
      case null:
        this.mStringValue = (String)paramObject;
      case null:
        this.mFloatValue = ((Float)paramObject).floatValue();
      case null:
        this.mIntegerValue = ((Integer)paramObject).intValue();
      case null:
      case null:
        break;
    } 
    this.mColorValue = ((Integer)paramObject).intValue();
  }
  
  public void setValue(float[] paramArrayOffloat) {
    int i = null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
    boolean bool = false;
    switch (i) {
      default:
        return;
      case 7:
        this.mFloatValue = paramArrayOffloat[0];
      case 6:
        if (paramArrayOffloat[0] > 0.5D)
          bool = true; 
        this.mBooleanValue = bool;
      case 5:
        throw new RuntimeException("Color does not have a single color to interpolate");
      case 4:
        this.mFloatValue = paramArrayOffloat[0];
      case 3:
        this.mIntegerValue = (int)paramArrayOffloat[0];
      case 1:
      case 2:
        break;
    } 
    i = Color.HSVToColor(paramArrayOffloat);
    this.mColorValue = i;
    this.mColorValue = clamp((int)(paramArrayOffloat[3] * 255.0F)) << 24 | i & 0xFFFFFF;
  }
  
  public enum AttributeType {
    BOOLEAN_TYPE, COLOR_DRAWABLE_TYPE, COLOR_TYPE, DIMENSION_TYPE, FLOAT_TYPE, INT_TYPE, STRING_TYPE;
    
    private static final AttributeType[] $VALUES;
    
    static {
      COLOR_TYPE = new AttributeType("COLOR_TYPE", 2);
      COLOR_DRAWABLE_TYPE = new AttributeType("COLOR_DRAWABLE_TYPE", 3);
      STRING_TYPE = new AttributeType("STRING_TYPE", 4);
      BOOLEAN_TYPE = new AttributeType("BOOLEAN_TYPE", 5);
      AttributeType attributeType = new AttributeType("DIMENSION_TYPE", 6);
      DIMENSION_TYPE = attributeType;
      $VALUES = new AttributeType[] { INT_TYPE, FLOAT_TYPE, COLOR_TYPE, COLOR_DRAWABLE_TYPE, STRING_TYPE, BOOLEAN_TYPE, attributeType };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\widget\ConstraintAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */