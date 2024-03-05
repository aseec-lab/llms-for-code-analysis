package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import org.xmlpull.v1.XmlPullParser;

public class TypedArrayUtils {
  private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";
  
  public static int getAttr(Context paramContext, int paramInt1, int paramInt2) {
    TypedValue typedValue = new TypedValue();
    paramContext.getTheme().resolveAttribute(paramInt1, typedValue, true);
    return (typedValue.resourceId != 0) ? paramInt1 : paramInt2;
  }
  
  public static boolean getBoolean(TypedArray paramTypedArray, int paramInt1, int paramInt2, boolean paramBoolean) {
    return paramTypedArray.getBoolean(paramInt1, paramTypedArray.getBoolean(paramInt2, paramBoolean));
  }
  
  public static Drawable getDrawable(TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    Drawable drawable2 = paramTypedArray.getDrawable(paramInt1);
    Drawable drawable1 = drawable2;
    if (drawable2 == null)
      drawable1 = paramTypedArray.getDrawable(paramInt2); 
    return drawable1;
  }
  
  public static int getInt(TypedArray paramTypedArray, int paramInt1, int paramInt2, int paramInt3) {
    return paramTypedArray.getInt(paramInt1, paramTypedArray.getInt(paramInt2, paramInt3));
  }
  
  public static boolean getNamedBoolean(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, String paramString, int paramInt, boolean paramBoolean) {
    return !hasAttribute(paramXmlPullParser, paramString) ? paramBoolean : paramTypedArray.getBoolean(paramInt, paramBoolean);
  }
  
  public static int getNamedColor(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, String paramString, int paramInt1, int paramInt2) {
    return !hasAttribute(paramXmlPullParser, paramString) ? paramInt2 : paramTypedArray.getColor(paramInt1, paramInt2);
  }
  
  public static ColorStateList getNamedColorStateList(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, Resources.Theme paramTheme, String paramString, int paramInt) {
    if (hasAttribute(paramXmlPullParser, paramString)) {
      TypedValue typedValue = new TypedValue();
      paramTypedArray.getValue(paramInt, typedValue);
      if (typedValue.type != 2)
        return (typedValue.type >= 28 && typedValue.type <= 31) ? getNamedColorStateListFromInt(typedValue) : ColorStateListInflaterCompat.inflate(paramTypedArray.getResources(), paramTypedArray.getResourceId(paramInt, 0), paramTheme); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to resolve attribute at index ");
      stringBuilder.append(paramInt);
      stringBuilder.append(": ");
      stringBuilder.append(typedValue);
      throw new UnsupportedOperationException(stringBuilder.toString());
    } 
    return null;
  }
  
  private static ColorStateList getNamedColorStateListFromInt(TypedValue paramTypedValue) {
    return ColorStateList.valueOf(paramTypedValue.data);
  }
  
  public static ComplexColorCompat getNamedComplexColor(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, Resources.Theme paramTheme, String paramString, int paramInt1, int paramInt2) {
    if (hasAttribute(paramXmlPullParser, paramString)) {
      TypedValue typedValue = new TypedValue();
      paramTypedArray.getValue(paramInt1, typedValue);
      if (typedValue.type >= 28 && typedValue.type <= 31)
        return ComplexColorCompat.from(typedValue.data); 
      ComplexColorCompat complexColorCompat = ComplexColorCompat.inflate(paramTypedArray.getResources(), paramTypedArray.getResourceId(paramInt1, 0), paramTheme);
      if (complexColorCompat != null)
        return complexColorCompat; 
    } 
    return ComplexColorCompat.from(paramInt2);
  }
  
  public static float getNamedFloat(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, String paramString, int paramInt, float paramFloat) {
    return !hasAttribute(paramXmlPullParser, paramString) ? paramFloat : paramTypedArray.getFloat(paramInt, paramFloat);
  }
  
  public static int getNamedInt(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, String paramString, int paramInt1, int paramInt2) {
    return !hasAttribute(paramXmlPullParser, paramString) ? paramInt2 : paramTypedArray.getInt(paramInt1, paramInt2);
  }
  
  public static int getNamedResourceId(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, String paramString, int paramInt1, int paramInt2) {
    return !hasAttribute(paramXmlPullParser, paramString) ? paramInt2 : paramTypedArray.getResourceId(paramInt1, paramInt2);
  }
  
  public static String getNamedString(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, String paramString, int paramInt) {
    return !hasAttribute(paramXmlPullParser, paramString) ? null : paramTypedArray.getString(paramInt);
  }
  
  public static int getResourceId(TypedArray paramTypedArray, int paramInt1, int paramInt2, int paramInt3) {
    return paramTypedArray.getResourceId(paramInt1, paramTypedArray.getResourceId(paramInt2, paramInt3));
  }
  
  public static String getString(TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    String str2 = paramTypedArray.getString(paramInt1);
    String str1 = str2;
    if (str2 == null)
      str1 = paramTypedArray.getString(paramInt2); 
    return str1;
  }
  
  public static CharSequence getText(TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    CharSequence charSequence2 = paramTypedArray.getText(paramInt1);
    CharSequence charSequence1 = charSequence2;
    if (charSequence2 == null)
      charSequence1 = paramTypedArray.getText(paramInt2); 
    return charSequence1;
  }
  
  public static CharSequence[] getTextArray(TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    CharSequence[] arrayOfCharSequence2 = paramTypedArray.getTextArray(paramInt1);
    CharSequence[] arrayOfCharSequence1 = arrayOfCharSequence2;
    if (arrayOfCharSequence2 == null)
      arrayOfCharSequence1 = paramTypedArray.getTextArray(paramInt2); 
    return arrayOfCharSequence1;
  }
  
  public static boolean hasAttribute(XmlPullParser paramXmlPullParser, String paramString) {
    boolean bool;
    if (paramXmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", paramString) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static TypedArray obtainAttributes(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int[] paramArrayOfint) {
    return (paramTheme == null) ? paramResources.obtainAttributes(paramAttributeSet, paramArrayOfint) : paramTheme.obtainStyledAttributes(paramAttributeSet, paramArrayOfint, 0, 0);
  }
  
  public static TypedValue peekNamedValue(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser, String paramString, int paramInt) {
    return !hasAttribute(paramXmlPullParser, paramString) ? null : paramTypedArray.peekValue(paramInt);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\res\TypedArrayUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */