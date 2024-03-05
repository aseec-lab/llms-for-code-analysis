package androidx.core.util;

public class Pair<F, S> {
  public final F first;
  
  public final S second;
  
  public Pair(F paramF, S paramS) {
    this.first = paramF;
    this.second = paramS;
  }
  
  public static <A, B> Pair<A, B> create(A paramA, B paramB) {
    return new Pair<A, B>(paramA, paramB);
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof Pair;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    bool = bool1;
    if (ObjectsCompat.equals(((Pair)paramObject).first, this.first)) {
      bool = bool1;
      if (ObjectsCompat.equals(((Pair)paramObject).second, this.second))
        bool = true; 
    } 
    return bool;
  }
  
  public int hashCode() {
    int i;
    F f = this.first;
    int j = 0;
    if (f == null) {
      i = 0;
    } else {
      i = f.hashCode();
    } 
    S s = this.second;
    if (s != null)
      j = s.hashCode(); 
    return i ^ j;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Pair{");
    stringBuilder.append(String.valueOf(this.first));
    stringBuilder.append(" ");
    stringBuilder.append(String.valueOf(this.second));
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\cor\\util\Pair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */