package androidx.core.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Xml;
import androidx.core.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

final class GradientColorInflaterCompat {
  private static final int TILE_MODE_CLAMP = 0;
  
  private static final int TILE_MODE_MIRROR = 2;
  
  private static final int TILE_MODE_REPEAT = 1;
  
  private static ColorStops checkColors(ColorStops paramColorStops, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3) {
    return (paramColorStops != null) ? paramColorStops : (paramBoolean ? new ColorStops(paramInt1, paramInt3, paramInt2) : new ColorStops(paramInt1, paramInt2));
  }
  
  static Shader createFromXml(Resources paramResources, XmlPullParser paramXmlPullParser, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    int i;
    AttributeSet attributeSet = Xml.asAttributeSet(paramXmlPullParser);
    while (true) {
      i = paramXmlPullParser.next();
      if (i != 2 && i != 1)
        continue; 
      break;
    } 
    if (i == 2)
      return createFromXmlInner(paramResources, paramXmlPullParser, attributeSet, paramTheme); 
    throw new XmlPullParserException("No start tag found");
  }
  
  static Shader createFromXmlInner(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws IOException, XmlPullParserException {
    TypedArray typedArray;
    String str = paramXmlPullParser.getName();
    if (str.equals("gradient")) {
      typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.GradientColor);
      float f3 = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "startX", R.styleable.GradientColor_android_startX, 0.0F);
      float f5 = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "startY", R.styleable.GradientColor_android_startY, 0.0F);
      float f1 = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "endX", R.styleable.GradientColor_android_endX, 0.0F);
      float f7 = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "endY", R.styleable.GradientColor_android_endY, 0.0F);
      float f4 = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "centerX", R.styleable.GradientColor_android_centerX, 0.0F);
      float f6 = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "centerY", R.styleable.GradientColor_android_centerY, 0.0F);
      int j = TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "type", R.styleable.GradientColor_android_type, 0);
      int n = TypedArrayUtils.getNamedColor(typedArray, paramXmlPullParser, "startColor", R.styleable.GradientColor_android_startColor, 0);
      boolean bool = TypedArrayUtils.hasAttribute(paramXmlPullParser, "centerColor");
      int m = TypedArrayUtils.getNamedColor(typedArray, paramXmlPullParser, "centerColor", R.styleable.GradientColor_android_centerColor, 0);
      int i = TypedArrayUtils.getNamedColor(typedArray, paramXmlPullParser, "endColor", R.styleable.GradientColor_android_endColor, 0);
      int k = TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "tileMode", R.styleable.GradientColor_android_tileMode, 0);
      float f2 = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "gradientRadius", R.styleable.GradientColor_android_gradientRadius, 0.0F);
      typedArray.recycle();
      ColorStops colorStops = checkColors(inflateChildElements(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme), n, i, bool, m);
      if (j != 1)
        return (Shader)((j != 2) ? new LinearGradient(f3, f5, f1, f7, colorStops.mColors, colorStops.mOffsets, parseTileMode(k)) : new SweepGradient(f4, f6, colorStops.mColors, colorStops.mOffsets)); 
      if (f2 > 0.0F)
        return (Shader)new RadialGradient(f4, f6, f2, colorStops.mColors, colorStops.mOffsets, parseTileMode(k)); 
      throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramXmlPullParser.getPositionDescription());
    stringBuilder.append(": invalid gradient color tag ");
    stringBuilder.append((String)typedArray);
    throw new XmlPullParserException(stringBuilder.toString());
  }
  
  private static ColorStops inflateChildElements(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    int i = paramXmlPullParser.getDepth() + 1;
    ArrayList<Float> arrayList = new ArrayList(20);
    ArrayList<Integer> arrayList1 = new ArrayList(20);
    while (true) {
      int j = paramXmlPullParser.next();
      if (j != 1) {
        int k = paramXmlPullParser.getDepth();
        if (k >= i || j != 3) {
          if (j != 2 || k > i || !paramXmlPullParser.getName().equals("item"))
            continue; 
          TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.GradientColorItem);
          boolean bool1 = typedArray.hasValue(R.styleable.GradientColorItem_android_color);
          boolean bool2 = typedArray.hasValue(R.styleable.GradientColorItem_android_offset);
          if (bool1 && bool2) {
            j = typedArray.getColor(R.styleable.GradientColorItem_android_color, 0);
            float f = typedArray.getFloat(R.styleable.GradientColorItem_android_offset, 0.0F);
            typedArray.recycle();
            arrayList1.add(Integer.valueOf(j));
            arrayList.add(Float.valueOf(f));
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramXmlPullParser.getPositionDescription());
          stringBuilder.append(": <item> tag requires a 'color' attribute and a 'offset' attribute!");
          throw new XmlPullParserException(stringBuilder.toString());
        } 
      } 
      break;
    } 
    return (arrayList1.size() > 0) ? new ColorStops(arrayList1, arrayList) : null;
  }
  
  private static Shader.TileMode parseTileMode(int paramInt) {
    return (paramInt != 1) ? ((paramInt != 2) ? Shader.TileMode.CLAMP : Shader.TileMode.MIRROR) : Shader.TileMode.REPEAT;
  }
  
  static final class ColorStops {
    final int[] mColors;
    
    final float[] mOffsets;
    
    ColorStops(int param1Int1, int param1Int2) {
      this.mColors = new int[] { param1Int1, param1Int2 };
      this.mOffsets = new float[] { 0.0F, 1.0F };
    }
    
    ColorStops(int param1Int1, int param1Int2, int param1Int3) {
      this.mColors = new int[] { param1Int1, param1Int2, param1Int3 };
      this.mOffsets = new float[] { 0.0F, 0.5F, 1.0F };
    }
    
    ColorStops(List<Integer> param1List, List<Float> param1List1) {
      int i = param1List.size();
      this.mColors = new int[i];
      this.mOffsets = new float[i];
      for (byte b = 0; b < i; b++) {
        this.mColors[b] = ((Integer)param1List.get(b)).intValue();
        this.mOffsets[b] = ((Float)param1List1.get(b)).floatValue();
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\res\GradientColorInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */