package androidx.core.graphics;

import android.graphics.PointF;
import androidx.core.util.Preconditions;

public final class PathSegment {
  private final PointF mEnd;
  
  private final float mEndFraction;
  
  private final PointF mStart;
  
  private final float mStartFraction;
  
  public PathSegment(PointF paramPointF1, float paramFloat1, PointF paramPointF2, float paramFloat2) {
    this.mStart = (PointF)Preconditions.checkNotNull(paramPointF1, "start == null");
    this.mStartFraction = paramFloat1;
    this.mEnd = (PointF)Preconditions.checkNotNull(paramPointF2, "end == null");
    this.mEndFraction = paramFloat2;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof PathSegment))
      return false; 
    paramObject = paramObject;
    if (Float.compare(this.mStartFraction, ((PathSegment)paramObject).mStartFraction) != 0 || Float.compare(this.mEndFraction, ((PathSegment)paramObject).mEndFraction) != 0 || !this.mStart.equals(((PathSegment)paramObject).mStart) || !this.mEnd.equals(((PathSegment)paramObject).mEnd))
      bool = false; 
    return bool;
  }
  
  public PointF getEnd() {
    return this.mEnd;
  }
  
  public float getEndFraction() {
    return this.mEndFraction;
  }
  
  public PointF getStart() {
    return this.mStart;
  }
  
  public float getStartFraction() {
    return this.mStartFraction;
  }
  
  public int hashCode() {
    byte b;
    int j = this.mStart.hashCode();
    float f = this.mStartFraction;
    int i = 0;
    if (f != 0.0F) {
      b = Float.floatToIntBits(f);
    } else {
      b = 0;
    } 
    int k = this.mEnd.hashCode();
    f = this.mEndFraction;
    if (f != 0.0F)
      i = Float.floatToIntBits(f); 
    return ((j * 31 + b) * 31 + k) * 31 + i;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("PathSegment{start=");
    stringBuilder.append(this.mStart);
    stringBuilder.append(", startFraction=");
    stringBuilder.append(this.mStartFraction);
    stringBuilder.append(", end=");
    stringBuilder.append(this.mEnd);
    stringBuilder.append(", endFraction=");
    stringBuilder.append(this.mEndFraction);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\graphics\PathSegment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */