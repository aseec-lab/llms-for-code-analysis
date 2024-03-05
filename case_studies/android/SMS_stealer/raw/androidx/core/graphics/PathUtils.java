package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Collection;

public final class PathUtils {
  public static Collection<PathSegment> flatten(Path paramPath) {
    return flatten(paramPath, 0.5F);
  }
  
  public static Collection<PathSegment> flatten(Path paramPath, float paramFloat) {
    float[] arrayOfFloat = paramPath.approximate(paramFloat);
    int i = arrayOfFloat.length / 3;
    ArrayList<PathSegment> arrayList = new ArrayList(i);
    for (byte b = 1; b < i; b++) {
      int k = b * 3;
      int j = (b - 1) * 3;
      float f3 = arrayOfFloat[k];
      float f1 = arrayOfFloat[k + 1];
      paramFloat = arrayOfFloat[k + 2];
      float f2 = arrayOfFloat[j];
      float f4 = arrayOfFloat[j + 1];
      float f5 = arrayOfFloat[j + 2];
      if (f3 != f2 && (f1 != f4 || paramFloat != f5))
        arrayList.add(new PathSegment(new PointF(f4, f5), f2, new PointF(f1, paramFloat), f3)); 
    } 
    return arrayList;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\PathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */