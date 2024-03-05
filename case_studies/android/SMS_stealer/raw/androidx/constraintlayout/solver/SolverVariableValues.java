package androidx.constraintlayout.solver;

import java.io.PrintStream;
import java.util.Arrays;

public class SolverVariableValues implements ArrayRow.ArrayRowVariables {
  private static final boolean DEBUG = false;
  
  private static final boolean HASH = true;
  
  private static float epsilon = 0.001F;
  
  private int HASH_SIZE = 16;
  
  private final int NONE = -1;
  
  private int SIZE = 16;
  
  int head = -1;
  
  int[] keys = new int[16];
  
  protected final Cache mCache;
  
  int mCount = 0;
  
  private final ArrayRow mRow;
  
  int[] next = new int[16];
  
  int[] nextKeys = new int[16];
  
  int[] previous = new int[16];
  
  float[] values = new float[16];
  
  int[] variables = new int[16];
  
  SolverVariableValues(ArrayRow paramArrayRow, Cache paramCache) {
    this.mRow = paramArrayRow;
    this.mCache = paramCache;
    clear();
  }
  
  private void addToHashMap(SolverVariable paramSolverVariable, int paramInt) {
    int k = paramSolverVariable.id % this.HASH_SIZE;
    int[] arrayOfInt = this.keys;
    int j = arrayOfInt[k];
    int i = j;
    if (j == -1) {
      arrayOfInt[k] = paramInt;
    } else {
      while (true) {
        arrayOfInt = this.nextKeys;
        if (arrayOfInt[i] != -1) {
          i = arrayOfInt[i];
          continue;
        } 
        arrayOfInt[i] = paramInt;
        this.nextKeys[paramInt] = -1;
        return;
      } 
    } 
    this.nextKeys[paramInt] = -1;
  }
  
  private void addVariable(int paramInt, SolverVariable paramSolverVariable, float paramFloat) {
    this.variables[paramInt] = paramSolverVariable.id;
    this.values[paramInt] = paramFloat;
    this.previous[paramInt] = -1;
    this.next[paramInt] = -1;
    paramSolverVariable.addToRow(this.mRow);
    paramSolverVariable.usageInRowCount++;
    this.mCount++;
  }
  
  private void displayHash() {
    for (byte b = 0; b < this.HASH_SIZE; b++) {
      if (this.keys[b] != -1) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(hashCode());
        stringBuilder.append(" hash [");
        stringBuilder.append(b);
        stringBuilder.append("] => ");
        String str = stringBuilder.toString();
        int i = this.keys[b];
        for (boolean bool = false; !bool; bool = true) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str);
          stringBuilder1.append(" ");
          stringBuilder1.append(this.variables[i]);
          str = stringBuilder1.toString();
          int[] arrayOfInt = this.nextKeys;
          if (arrayOfInt[i] != -1) {
            i = arrayOfInt[i];
            continue;
          } 
        } 
        System.out.println(str);
      } 
    } 
  }
  
  private int findEmptySlot() {
    for (byte b = 0; b < this.SIZE; b++) {
      if (this.variables[b] == -1)
        return b; 
    } 
    return -1;
  }
  
  private void increaseSize() {
    int j = this.SIZE * 2;
    this.variables = Arrays.copyOf(this.variables, j);
    this.values = Arrays.copyOf(this.values, j);
    this.previous = Arrays.copyOf(this.previous, j);
    this.next = Arrays.copyOf(this.next, j);
    this.nextKeys = Arrays.copyOf(this.nextKeys, j);
    for (int i = this.SIZE; i < j; i++) {
      this.variables[i] = -1;
      this.nextKeys[i] = -1;
    } 
    this.SIZE = j;
  }
  
  private void insertVariable(int paramInt, SolverVariable paramSolverVariable, float paramFloat) {
    int i = findEmptySlot();
    addVariable(i, paramSolverVariable, paramFloat);
    if (paramInt != -1) {
      this.previous[i] = paramInt;
      int[] arrayOfInt1 = this.next;
      arrayOfInt1[i] = arrayOfInt1[paramInt];
      arrayOfInt1[paramInt] = i;
    } else {
      this.previous[i] = -1;
      if (this.mCount > 0) {
        this.next[i] = this.head;
        this.head = i;
      } else {
        this.next[i] = -1;
      } 
    } 
    int[] arrayOfInt = this.next;
    if (arrayOfInt[i] != -1)
      this.previous[arrayOfInt[i]] = i; 
    addToHashMap(paramSolverVariable, i);
  }
  
  private void removeFromHashMap(SolverVariable paramSolverVariable) {
    int m = paramSolverVariable.id % this.HASH_SIZE;
    int j = this.keys[m];
    if (j == -1)
      return; 
    int k = paramSolverVariable.id;
    int i = j;
    if (this.variables[j] == k) {
      int[] arrayOfInt2 = this.keys;
      int[] arrayOfInt1 = this.nextKeys;
      arrayOfInt2[m] = arrayOfInt1[j];
      arrayOfInt1[j] = -1;
    } else {
      while (true) {
        int[] arrayOfInt1 = this.nextKeys;
        if (arrayOfInt1[i] != -1 && this.variables[arrayOfInt1[i]] != k) {
          i = arrayOfInt1[i];
          continue;
        } 
        break;
      } 
      int[] arrayOfInt = this.nextKeys;
      j = arrayOfInt[i];
      if (j != -1 && this.variables[j] == k) {
        arrayOfInt[i] = arrayOfInt[j];
        arrayOfInt[j] = -1;
      } 
    } 
  }
  
  public void add(SolverVariable paramSolverVariable, float paramFloat, boolean paramBoolean) {
    float f = epsilon;
    if (paramFloat > -f && paramFloat < f)
      return; 
    int i = indexOf(paramSolverVariable);
    if (i == -1) {
      put(paramSolverVariable, paramFloat);
    } else {
      float[] arrayOfFloat = this.values;
      arrayOfFloat[i] = arrayOfFloat[i] + paramFloat;
      f = arrayOfFloat[i];
      paramFloat = epsilon;
      if (f > -paramFloat && arrayOfFloat[i] < paramFloat) {
        arrayOfFloat[i] = 0.0F;
        remove(paramSolverVariable, paramBoolean);
      } 
    } 
  }
  
  public void clear() {
    int i = this.mCount;
    byte b;
    for (b = 0; b < i; b++) {
      SolverVariable solverVariable = getVariable(b);
      if (solverVariable != null)
        solverVariable.removeFromRow(this.mRow); 
    } 
    for (b = 0; b < this.SIZE; b++) {
      this.variables[b] = -1;
      this.nextKeys[b] = -1;
    } 
    for (b = 0; b < this.HASH_SIZE; b++)
      this.keys[b] = -1; 
    this.mCount = 0;
    this.head = -1;
  }
  
  public boolean contains(SolverVariable paramSolverVariable) {
    boolean bool;
    if (indexOf(paramSolverVariable) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void display() {
    int i = this.mCount;
    System.out.print("{ ");
    for (byte b = 0; b < i; b++) {
      SolverVariable solverVariable = getVariable(b);
      if (solverVariable != null) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(solverVariable);
        stringBuilder.append(" = ");
        stringBuilder.append(getVariableValue(b));
        stringBuilder.append(" ");
        printStream.print(stringBuilder.toString());
      } 
    } 
    System.out.println(" }");
  }
  
  public void divideByAmount(float paramFloat) {
    int j = this.mCount;
    int i = this.head;
    for (byte b = 0; b < j; b++) {
      float[] arrayOfFloat = this.values;
      arrayOfFloat[i] = arrayOfFloat[i] / paramFloat;
      i = this.next[i];
      if (i == -1)
        break; 
    } 
  }
  
  public float get(SolverVariable paramSolverVariable) {
    int i = indexOf(paramSolverVariable);
    return (i != -1) ? this.values[i] : 0.0F;
  }
  
  public int getCurrentSize() {
    return this.mCount;
  }
  
  public SolverVariable getVariable(int paramInt) {
    int j = this.mCount;
    if (j == 0)
      return null; 
    int i = this.head;
    for (byte b = 0; b < j; b++) {
      if (b == paramInt && i != -1)
        return this.mCache.mIndexedVariables[this.variables[i]]; 
      i = this.next[i];
      if (i == -1)
        break; 
    } 
    return null;
  }
  
  public float getVariableValue(int paramInt) {
    int j = this.mCount;
    int i = this.head;
    for (byte b = 0; b < j; b++) {
      if (b == paramInt)
        return this.values[i]; 
      i = this.next[i];
      if (i == -1)
        break; 
    } 
    return 0.0F;
  }
  
  public int indexOf(SolverVariable paramSolverVariable) {
    if (this.mCount != 0 && paramSolverVariable != null) {
      int k = paramSolverVariable.id;
      int i = this.HASH_SIZE;
      int j = this.keys[k % i];
      if (j == -1)
        return -1; 
      i = j;
      if (this.variables[j] == k)
        return j; 
      while (true) {
        int[] arrayOfInt1 = this.nextKeys;
        if (arrayOfInt1[i] != -1 && this.variables[arrayOfInt1[i]] != k) {
          i = arrayOfInt1[i];
          continue;
        } 
        break;
      } 
      int[] arrayOfInt = this.nextKeys;
      if (arrayOfInt[i] == -1)
        return -1; 
      if (this.variables[arrayOfInt[i]] == k)
        return arrayOfInt[i]; 
    } 
    return -1;
  }
  
  public void invert() {
    int j = this.mCount;
    int i = this.head;
    for (byte b = 0; b < j; b++) {
      float[] arrayOfFloat = this.values;
      arrayOfFloat[i] = arrayOfFloat[i] * -1.0F;
      i = this.next[i];
      if (i == -1)
        break; 
    } 
  }
  
  public void put(SolverVariable paramSolverVariable, float paramFloat) {
    float f = epsilon;
    if (paramFloat > -f && paramFloat < f) {
      remove(paramSolverVariable, true);
      return;
    } 
    int i = this.mCount;
    byte b = 0;
    if (i == 0) {
      addVariable(0, paramSolverVariable, paramFloat);
      addToHashMap(paramSolverVariable, 0);
      this.head = 0;
    } else {
      i = indexOf(paramSolverVariable);
      if (i != -1) {
        this.values[i] = paramFloat;
      } else {
        int k;
        if (this.mCount + 1 >= this.SIZE)
          increaseSize(); 
        int m = this.mCount;
        i = this.head;
        int j = -1;
        while (true) {
          k = j;
          if (b < m) {
            if (this.variables[i] == paramSolverVariable.id) {
              this.values[i] = paramFloat;
              return;
            } 
            if (this.variables[i] < paramSolverVariable.id)
              j = i; 
            i = this.next[i];
            if (i == -1) {
              k = j;
              break;
            } 
            b++;
            continue;
          } 
          break;
        } 
        insertVariable(k, paramSolverVariable, paramFloat);
      } 
    } 
  }
  
  public float remove(SolverVariable paramSolverVariable, boolean paramBoolean) {
    int i = indexOf(paramSolverVariable);
    if (i == -1)
      return 0.0F; 
    removeFromHashMap(paramSolverVariable);
    float f = this.values[i];
    if (this.head == i)
      this.head = this.next[i]; 
    this.variables[i] = -1;
    int[] arrayOfInt1 = this.previous;
    if (arrayOfInt1[i] != -1) {
      int[] arrayOfInt = this.next;
      arrayOfInt[arrayOfInt1[i]] = arrayOfInt[i];
    } 
    int[] arrayOfInt2 = this.next;
    if (arrayOfInt2[i] != -1) {
      arrayOfInt1 = this.previous;
      arrayOfInt1[arrayOfInt2[i]] = arrayOfInt1[i];
    } 
    this.mCount--;
    paramSolverVariable.usageInRowCount--;
    if (paramBoolean)
      paramSolverVariable.removeFromRow(this.mRow); 
    return f;
  }
  
  public int sizeInBytes() {
    return 0;
  }
  
  public String toString() {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(hashCode());
    stringBuilder1.append(" { ");
    String str = stringBuilder1.toString();
    int i = this.mCount;
    for (byte b = 0; b < i; b++) {
      SolverVariable solverVariable = getVariable(b);
      if (solverVariable != null) {
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append(str);
        stringBuilder4.append(solverVariable);
        stringBuilder4.append(" = ");
        stringBuilder4.append(getVariableValue(b));
        stringBuilder4.append(" ");
        str = stringBuilder4.toString();
        int j = indexOf(solverVariable);
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append("[p: ");
        str = stringBuilder3.toString();
        if (this.previous[j] != -1) {
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str);
          stringBuilder3.append(this.mCache.mIndexedVariables[this.variables[this.previous[j]]]);
          str = stringBuilder3.toString();
        } else {
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str);
          stringBuilder3.append("none");
          str = stringBuilder3.toString();
        } 
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append(", n: ");
        str = stringBuilder3.toString();
        if (this.next[j] != -1) {
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str);
          stringBuilder3.append(this.mCache.mIndexedVariables[this.variables[this.next[j]]]);
          str = stringBuilder3.toString();
        } else {
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str);
          stringBuilder3.append("none");
          str = stringBuilder3.toString();
        } 
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append("]");
        str = stringBuilder3.toString();
      } 
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str);
    stringBuilder2.append(" }");
    return stringBuilder2.toString();
  }
  
  public float use(ArrayRow paramArrayRow, boolean paramBoolean) {
    float f = get(paramArrayRow.variable);
    remove(paramArrayRow.variable, paramBoolean);
    SolverVariableValues solverVariableValues = (SolverVariableValues)paramArrayRow.variables;
    int k = solverVariableValues.getCurrentSize();
    int i = solverVariableValues.head;
    int j = 0;
    i = 0;
    while (j < k) {
      int m = j;
      if (solverVariableValues.variables[i] != -1) {
        float f1 = solverVariableValues.values[i];
        add(this.mCache.mIndexedVariables[solverVariableValues.variables[i]], f1 * f, paramBoolean);
        m = j + 1;
      } 
      i++;
      j = m;
    } 
    return f;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\SolverVariableValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */