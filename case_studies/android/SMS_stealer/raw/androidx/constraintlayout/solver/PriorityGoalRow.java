package androidx.constraintlayout.solver;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityGoalRow extends ArrayRow {
  private static final boolean DEBUG = false;
  
  static final int NOT_FOUND = -1;
  
  private static final float epsilon = 1.0E-4F;
  
  private int TABLE_SIZE = 128;
  
  GoalVariableAccessor accessor = new GoalVariableAccessor(this);
  
  private SolverVariable[] arrayGoals = new SolverVariable[128];
  
  Cache mCache;
  
  private int numGoals = 0;
  
  private SolverVariable[] sortArray = new SolverVariable[128];
  
  public PriorityGoalRow(Cache paramCache) {
    super(paramCache);
    this.mCache = paramCache;
  }
  
  private final void addToGoal(SolverVariable paramSolverVariable) {
    int i = this.numGoals;
    SolverVariable[] arrayOfSolverVariable = this.arrayGoals;
    if (i + 1 > arrayOfSolverVariable.length) {
      arrayOfSolverVariable = Arrays.<SolverVariable>copyOf(arrayOfSolverVariable, arrayOfSolverVariable.length * 2);
      this.arrayGoals = arrayOfSolverVariable;
      this.sortArray = Arrays.<SolverVariable>copyOf(arrayOfSolverVariable, arrayOfSolverVariable.length * 2);
    } 
    arrayOfSolverVariable = this.arrayGoals;
    i = this.numGoals;
    arrayOfSolverVariable[i] = paramSolverVariable;
    this.numGoals = ++i;
    if (i > 1 && (arrayOfSolverVariable[i - 1]).id > paramSolverVariable.id) {
      boolean bool = false;
      i = 0;
      while (true) {
        int j = this.numGoals;
        if (i < j) {
          this.sortArray[i] = this.arrayGoals[i];
          i++;
          continue;
        } 
        Arrays.sort(this.sortArray, 0, j, new Comparator<SolverVariable>() {
              final PriorityGoalRow this$0;
              
              public int compare(SolverVariable param1SolverVariable1, SolverVariable param1SolverVariable2) {
                return param1SolverVariable1.id - param1SolverVariable2.id;
              }
            });
        for (i = bool; i < this.numGoals; i++)
          this.arrayGoals[i] = this.sortArray[i]; 
        break;
      } 
    } 
    paramSolverVariable.inGoal = true;
    paramSolverVariable.addToRow(this);
  }
  
  private final void removeGoal(SolverVariable paramSolverVariable) {
    for (int i = 0; i < this.numGoals; i++) {
      if (this.arrayGoals[i] == paramSolverVariable)
        while (true) {
          int j = this.numGoals;
          if (i < j - 1) {
            SolverVariable[] arrayOfSolverVariable = this.arrayGoals;
            j = i + 1;
            arrayOfSolverVariable[i] = arrayOfSolverVariable[j];
            i = j;
            continue;
          } 
          this.numGoals = j - 1;
          paramSolverVariable.inGoal = false;
          return;
        }  
    } 
  }
  
  public void addError(SolverVariable paramSolverVariable) {
    this.accessor.init(paramSolverVariable);
    this.accessor.reset();
    paramSolverVariable.goalStrengthVector[paramSolverVariable.strength] = 1.0F;
    addToGoal(paramSolverVariable);
  }
  
  public void clear() {
    this.numGoals = 0;
    this.constantValue = 0.0F;
  }
  
  public SolverVariable getPivotCandidate(LinearSystem paramLinearSystem, boolean[] paramArrayOfboolean) {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: iconst_m1
    //   3: istore #4
    //   5: iload_3
    //   6: aload_0
    //   7: getfield numGoals : I
    //   10: if_icmpge -> 101
    //   13: aload_0
    //   14: getfield arrayGoals : [Landroidx/constraintlayout/solver/SolverVariable;
    //   17: iload_3
    //   18: aaload
    //   19: astore_1
    //   20: aload_2
    //   21: aload_1
    //   22: getfield id : I
    //   25: baload
    //   26: ifeq -> 36
    //   29: iload #4
    //   31: istore #5
    //   33: goto -> 91
    //   36: aload_0
    //   37: getfield accessor : Landroidx/constraintlayout/solver/PriorityGoalRow$GoalVariableAccessor;
    //   40: aload_1
    //   41: invokevirtual init : (Landroidx/constraintlayout/solver/SolverVariable;)V
    //   44: iload #4
    //   46: iconst_m1
    //   47: if_icmpne -> 67
    //   50: iload #4
    //   52: istore #5
    //   54: aload_0
    //   55: getfield accessor : Landroidx/constraintlayout/solver/PriorityGoalRow$GoalVariableAccessor;
    //   58: invokevirtual isNegative : ()Z
    //   61: ifeq -> 91
    //   64: goto -> 88
    //   67: iload #4
    //   69: istore #5
    //   71: aload_0
    //   72: getfield accessor : Landroidx/constraintlayout/solver/PriorityGoalRow$GoalVariableAccessor;
    //   75: aload_0
    //   76: getfield arrayGoals : [Landroidx/constraintlayout/solver/SolverVariable;
    //   79: iload #4
    //   81: aaload
    //   82: invokevirtual isSmallerThan : (Landroidx/constraintlayout/solver/SolverVariable;)Z
    //   85: ifeq -> 91
    //   88: iload_3
    //   89: istore #5
    //   91: iinc #3, 1
    //   94: iload #5
    //   96: istore #4
    //   98: goto -> 5
    //   101: iload #4
    //   103: iconst_m1
    //   104: if_icmpne -> 109
    //   107: aconst_null
    //   108: areturn
    //   109: aload_0
    //   110: getfield arrayGoals : [Landroidx/constraintlayout/solver/SolverVariable;
    //   113: iload #4
    //   115: aaload
    //   116: areturn
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.numGoals == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(" goal -> (");
    stringBuilder.append(this.constantValue);
    stringBuilder.append(") : ");
    String str = stringBuilder.toString();
    for (byte b = 0; b < this.numGoals; b++) {
      SolverVariable solverVariable = this.arrayGoals[b];
      this.accessor.init(solverVariable);
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(this.accessor);
      stringBuilder1.append(" ");
      str = stringBuilder1.toString();
    } 
    return str;
  }
  
  public void updateFromRow(LinearSystem paramLinearSystem, ArrayRow paramArrayRow, boolean paramBoolean) {
    SolverVariable solverVariable = paramArrayRow.variable;
    if (solverVariable == null)
      return; 
    ArrayRow.ArrayRowVariables arrayRowVariables = paramArrayRow.variables;
    int i = arrayRowVariables.getCurrentSize();
    for (byte b = 0; b < i; b++) {
      SolverVariable solverVariable1 = arrayRowVariables.getVariable(b);
      float f = arrayRowVariables.getVariableValue(b);
      this.accessor.init(solverVariable1);
      if (this.accessor.addToGoal(solverVariable, f))
        addToGoal(solverVariable1); 
      this.constantValue += paramArrayRow.constantValue * f;
    } 
    removeGoal(solverVariable);
  }
  
  class GoalVariableAccessor implements Comparable {
    PriorityGoalRow row;
    
    final PriorityGoalRow this$0;
    
    SolverVariable variable;
    
    public GoalVariableAccessor(PriorityGoalRow param1PriorityGoalRow1) {
      this.row = param1PriorityGoalRow1;
    }
    
    public void add(SolverVariable param1SolverVariable) {
      for (byte b = 0; b < 9; b++) {
        float[] arrayOfFloat = this.variable.goalStrengthVector;
        arrayOfFloat[b] = arrayOfFloat[b] + param1SolverVariable.goalStrengthVector[b];
        if (Math.abs(this.variable.goalStrengthVector[b]) < 1.0E-4F)
          this.variable.goalStrengthVector[b] = 0.0F; 
      } 
    }
    
    public boolean addToGoal(SolverVariable param1SolverVariable, float param1Float) {
      boolean bool1 = this.variable.inGoal;
      boolean bool = true;
      byte b = 0;
      if (bool1) {
        for (b = 0; b < 9; b++) {
          float[] arrayOfFloat = this.variable.goalStrengthVector;
          arrayOfFloat[b] = arrayOfFloat[b] + param1SolverVariable.goalStrengthVector[b] * param1Float;
          if (Math.abs(this.variable.goalStrengthVector[b]) < 1.0E-4F) {
            this.variable.goalStrengthVector[b] = 0.0F;
          } else {
            bool = false;
          } 
        } 
        if (bool)
          PriorityGoalRow.this.removeGoal(this.variable); 
        return false;
      } 
      while (b < 9) {
        float f = param1SolverVariable.goalStrengthVector[b];
        if (f != 0.0F) {
          float f1 = f * param1Float;
          f = f1;
          if (Math.abs(f1) < 1.0E-4F)
            f = 0.0F; 
          this.variable.goalStrengthVector[b] = f;
        } else {
          this.variable.goalStrengthVector[b] = 0.0F;
        } 
        b++;
      } 
      return true;
    }
    
    public int compareTo(Object param1Object) {
      param1Object = param1Object;
      return this.variable.id - ((SolverVariable)param1Object).id;
    }
    
    public void init(SolverVariable param1SolverVariable) {
      this.variable = param1SolverVariable;
    }
    
    public final boolean isNegative() {
      for (byte b = 8; b >= 0; b--) {
        float f = this.variable.goalStrengthVector[b];
        if (f > 0.0F)
          return false; 
        if (f < 0.0F)
          return true; 
      } 
      return false;
    }
    
    public final boolean isNull() {
      for (byte b = 0; b < 9; b++) {
        if (this.variable.goalStrengthVector[b] != 0.0F)
          return false; 
      } 
      return true;
    }
    
    public final boolean isSmallerThan(SolverVariable param1SolverVariable) {
      byte b = 8;
      while (b >= 0) {
        float f1 = param1SolverVariable.goalStrengthVector[b];
        float f2 = this.variable.goalStrengthVector[b];
        if (f2 == f1) {
          b--;
          continue;
        } 
        if (f2 < f1)
          return true; 
      } 
      return false;
    }
    
    public void reset() {
      Arrays.fill(this.variable.goalStrengthVector, 0.0F);
    }
    
    public String toString() {
      StringBuilder stringBuilder2;
      SolverVariable solverVariable = this.variable;
      String str1 = "[ ";
      String str2 = str1;
      if (solverVariable != null) {
        byte b = 0;
        while (true) {
          str2 = str1;
          if (b < 9) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str1);
            stringBuilder2.append(this.variable.goalStrengthVector[b]);
            stringBuilder2.append(" ");
            str1 = stringBuilder2.toString();
            b++;
            continue;
          } 
          break;
        } 
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append((String)stringBuilder2);
      stringBuilder1.append("] ");
      stringBuilder1.append(this.variable);
      return stringBuilder1.toString();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\PriorityGoalRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */