package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ComplexColorCompat {
  private static final String LOG_TAG = "ComplexColorCompat";
  
  private int mColor;
  
  private final ColorStateList mColorStateList;
  
  private final Shader mShader;
  
  private ComplexColorCompat(Shader paramShader, ColorStateList paramColorStateList, int paramInt) {
    this.mShader = paramShader;
    this.mColorStateList = paramColorStateList;
    this.mColor = paramInt;
  }
  
  private static ComplexColorCompat createFromXml(Resources paramResources, int paramInt, Resources.Theme paramTheme) throws IOException, XmlPullParserException {
    XmlResourceParser xmlResourceParser = paramResources.getXml(paramInt);
    AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
    while (true) {
      paramInt = xmlResourceParser.next();
      if (paramInt != 2 && paramInt != 1)
        continue; 
      break;
    } 
    if (paramInt == 2) {
      StringBuilder stringBuilder;
      String str = xmlResourceParser.getName();
      paramInt = -1;
      int i = str.hashCode();
      if (i != 89650992) {
        if (i == 1191572447 && str.equals("selector"))
          paramInt = 0; 
      } else if (str.equals("gradient")) {
        paramInt = 1;
      } 
      if (paramInt != 0) {
        if (paramInt == 1)
          return from(GradientColorInflaterCompat.createFromXmlInner(paramResources, (XmlPullParser)xmlResourceParser, attributeSet, paramTheme)); 
        stringBuilder = new StringBuilder();
        stringBuilder.append(xmlResourceParser.getPositionDescription());
        stringBuilder.append(": unsupported complex color tag ");
        stringBuilder.append(str);
        throw new XmlPullParserException(stringBuilder.toString());
      } 
      return from(ColorStateListInflaterCompat.createFromXmlInner((Resources)stringBuilder, (XmlPullParser)xmlResourceParser, attributeSet, paramTheme));
    } 
    throw new XmlPullParserException("No start tag found");
  }
  
  static ComplexColorCompat from(int paramInt) {
    return new ComplexColorCompat(null, null, paramInt);
  }
  
  static ComplexColorCompat from(ColorStateList paramColorStateList) {
    return new ComplexColorCompat(null, paramColorStateList, paramColorStateList.getDefaultColor());
  }
  
  static ComplexColorCompat from(Shader paramShader) {
    return new ComplexColorCompat(paramShader, null, 0);
  }
  
  public static ComplexColorCompat inflate(Resources paramResources, int paramInt, Resources.Theme paramTheme) {
    try {
      return createFromXml(paramResources, paramInt, paramTheme);
    } catch (Exception exception) {
      Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", exception);
      return null;
    } 
  }
  
  public int getColor() {
    return this.mColor;
  }
  
  public Shader getShader() {
    return this.mShader;
  }
  
  public boolean isGradient() {
    boolean bool;
    if (this.mShader != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isStateful() {
    if (this.mShader == null) {
      ColorStateList colorStateList = this.mColorStateList;
      if (colorStateList != null && colorStateList.isStateful())
        return true; 
    } 
    return false;
  }
  
  public boolean onStateChanged(int[] paramArrayOfint) {
    if (isStateful()) {
      ColorStateList colorStateList = this.mColorStateList;
      int i = colorStateList.getColorForState(paramArrayOfint, colorStateList.getDefaultColor());
      if (i != this.mColor) {
        boolean bool = true;
        this.mColor = i;
        return bool;
      } 
    } 
    return false;
  }
  
  public void setColor(int paramInt) {
    this.mColor = paramInt;
  }
  
  public boolean willDraw() {
    return (isGradient() || this.mColor != 0);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\res\ComplexColorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */