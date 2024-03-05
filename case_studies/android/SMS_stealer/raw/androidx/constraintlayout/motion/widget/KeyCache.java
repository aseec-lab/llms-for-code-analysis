package androidx.constraintlayout.motion.widget;

import java.util.Arrays;
import java.util.HashMap;

public class KeyCache {
  HashMap<Object, HashMap<String, float[]>> map = new HashMap<Object, HashMap<String, float[]>>();
  
  float getFloatValue(Object paramObject, String paramString, int paramInt) {
    if (!this.map.containsKey(paramObject))
      return Float.NaN; 
    paramObject = this.map.get(paramObject);
    if (!paramObject.containsKey(paramString))
      return Float.NaN; 
    paramObject = paramObject.get(paramString);
    return (paramObject.length > paramInt) ? paramObject[paramInt] : Float.NaN;
  }
  
  void setFloatValue(Object paramObject, String paramString, int paramInt, float paramFloat) {
    if (!this.map.containsKey(paramObject)) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      float[] arrayOfFloat = new float[paramInt + 1];
      arrayOfFloat[paramInt] = paramFloat;
      hashMap.put(paramString, arrayOfFloat);
      this.map.put(paramObject, hashMap);
    } else {
      HashMap<String, float[]> hashMap = this.map.get(paramObject);
      if (!hashMap.containsKey(paramString)) {
        float[] arrayOfFloat = new float[paramInt + 1];
        arrayOfFloat[paramInt] = paramFloat;
        hashMap.put(paramString, arrayOfFloat);
        this.map.put(paramObject, hashMap);
      } else {
        float[] arrayOfFloat = hashMap.get(paramString);
        paramObject = arrayOfFloat;
        if (arrayOfFloat.length <= paramInt)
          paramObject = Arrays.copyOf(arrayOfFloat, paramInt + 1); 
        paramObject[paramInt] = paramFloat;
        hashMap.put(paramString, paramObject);
      } 
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\KeyCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */