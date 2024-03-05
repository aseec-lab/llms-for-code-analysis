package androidx.constraintlayout.solver;

import java.util.ArrayList;

public class ArrayRow implements LinearSystem.Row {
  private static final boolean DEBUG = false;
  
  private static final boolean FULL_NEW_CHECK = false;
  
  float constantValue = 0.0F;
  
  boolean isSimpleDefinition = false;
  
  boolean used = false;
  
  SolverVariable variable = null;
  
  public ArrayRowVariables variables;
  
  ArrayList<SolverVariable> variablesToUpdate = new ArrayList<SolverVariable>();
  
  public ArrayRow() {}
  
  public ArrayRow(Cache paramCache) {
    this.variables = new ArrayLinkedVariables(this, paramCache);
  }
  
  private boolean isNew(SolverVariable paramSolverVariable, LinearSystem paramLinearSystem) {
    int i = paramSolverVariable.usageInRowCount;
    boolean bool = true;
    if (i > 1)
      bool = false; 
    return bool;
  }
  
  private SolverVariable pickPivotInVariables(boolean[] paramArrayOfboolean, SolverVariable paramSolverVariable) {
    // Byte code:
    //   0: aload_0
    //   1: getfield variables : Landroidx/constraintlayout/solver/ArrayRow$ArrayRowVariables;
    //   4: invokeinterface getCurrentSize : ()I
    //   9: istore #7
    //   11: aconst_null
    //   12: astore #9
    //   14: iconst_0
    //   15: istore #6
    //   17: fconst_0
    //   18: fstore #4
    //   20: iload #6
    //   22: iload #7
    //   24: if_icmpge -> 165
    //   27: aload_0
    //   28: getfield variables : Landroidx/constraintlayout/solver/ArrayRow$ArrayRowVariables;
    //   31: iload #6
    //   33: invokeinterface getVariableValue : (I)F
    //   38: fstore #5
    //   40: aload #9
    //   42: astore #8
    //   44: fload #4
    //   46: fstore_3
    //   47: fload #5
    //   49: fconst_0
    //   50: fcmpg
    //   51: ifge -> 152
    //   54: aload_0
    //   55: getfield variables : Landroidx/constraintlayout/solver/ArrayRow$ArrayRowVariables;
    //   58: iload #6
    //   60: invokeinterface getVariable : (I)Landroidx/constraintlayout/solver/SolverVariable;
    //   65: astore #10
    //   67: aload_1
    //   68: ifnull -> 88
    //   71: aload #9
    //   73: astore #8
    //   75: fload #4
    //   77: fstore_3
    //   78: aload_1
    //   79: aload #10
    //   81: getfield id : I
    //   84: baload
    //   85: ifne -> 152
    //   88: aload #9
    //   90: astore #8
    //   92: fload #4
    //   94: fstore_3
    //   95: aload #10
    //   97: aload_2
    //   98: if_acmpeq -> 152
    //   101: aload #10
    //   103: getfield mType : Landroidx/constraintlayout/solver/SolverVariable$Type;
    //   106: getstatic androidx/constraintlayout/solver/SolverVariable$Type.SLACK : Landroidx/constraintlayout/solver/SolverVariable$Type;
    //   109: if_acmpeq -> 130
    //   112: aload #9
    //   114: astore #8
    //   116: fload #4
    //   118: fstore_3
    //   119: aload #10
    //   121: getfield mType : Landroidx/constraintlayout/solver/SolverVariable$Type;
    //   124: getstatic androidx/constraintlayout/solver/SolverVariable$Type.ERROR : Landroidx/constraintlayout/solver/SolverVariable$Type;
    //   127: if_acmpne -> 152
    //   130: aload #9
    //   132: astore #8
    //   134: fload #4
    //   136: fstore_3
    //   137: fload #5
    //   139: fload #4
    //   141: fcmpg
    //   142: ifge -> 152
    //   145: fload #5
    //   147: fstore_3
    //   148: aload #10
    //   150: astore #8
    //   152: iinc #6, 1
    //   155: aload #8
    //   157: astore #9
    //   159: fload_3
    //   160: fstore #4
    //   162: goto -> 20
    //   165: aload #9
    //   167: areturn
  }
  
  public ArrayRow addError(LinearSystem paramLinearSystem, int paramInt) {
    this.variables.put(paramLinearSystem.createErrorVariable(paramInt, "ep"), 1.0F);
    this.variables.put(paramLinearSystem.createErrorVariable(paramInt, "em"), -1.0F);
    return this;
  }
  
  public void addError(SolverVariable paramSolverVariable) {
    int i = paramSolverVariable.strength;
    float f = 1.0F;
    if (i != 1)
      if (paramSolverVariable.strength == 2) {
        f = 1000.0F;
      } else if (paramSolverVariable.strength == 3) {
        f = 1000000.0F;
      } else if (paramSolverVariable.strength == 4) {
        f = 1.0E9F;
      } else if (paramSolverVariable.strength == 5) {
        f = 1.0E12F;
      }  
    this.variables.put(paramSolverVariable, f);
  }
  
  ArrayRow addSingleError(SolverVariable paramSolverVariable, int paramInt) {
    this.variables.put(paramSolverVariable, paramInt);
    return this;
  }
  
  boolean chooseSubject(LinearSystem paramLinearSystem) {
    boolean bool;
    SolverVariable solverVariable = chooseSubjectInVariables(paramLinearSystem);
    if (solverVariable == null) {
      bool = true;
    } else {
      pivot(solverVariable);
      bool = false;
    } 
    if (this.variables.getCurrentSize() == 0)
      this.isSimpleDefinition = true; 
    return bool;
  }
  
  SolverVariable chooseSubjectInVariables(LinearSystem paramLinearSystem) {
    int i = this.variables.getCurrentSize();
    SolverVariable solverVariable2 = null;
    SolverVariable solverVariable1 = null;
    byte b = 0;
    boolean bool2 = false;
    boolean bool1 = false;
    float f2 = 0.0F;
    float f1;
    for (f1 = 0.0F;; f1 = f4) {
      float f3;
      float f4;
      boolean bool3;
      boolean bool4;
      SolverVariable solverVariable3;
      SolverVariable solverVariable4;
      if (b < i) {
        float f = this.variables.getVariableValue(b);
        SolverVariable solverVariable = this.variables.getVariable(b);
        if (solverVariable.mType == SolverVariable.Type.UNRESTRICTED) {
          if (solverVariable2 == null) {
            bool3 = isNew(solverVariable, paramLinearSystem);
          } else if (f2 > f) {
            bool3 = isNew(solverVariable, paramLinearSystem);
          } else {
            SolverVariable solverVariable5 = solverVariable2;
            SolverVariable solverVariable6 = solverVariable1;
            bool3 = bool2;
            boolean bool = bool1;
            float f5 = f2;
            float f6 = f1;
            if (!bool2) {
              solverVariable5 = solverVariable2;
              solverVariable6 = solverVariable1;
              bool3 = bool2;
              bool = bool1;
              f5 = f2;
              f6 = f1;
              if (isNew(solverVariable, paramLinearSystem)) {
                bool3 = true;
                solverVariable5 = solverVariable;
                solverVariable6 = solverVariable1;
                bool = bool1;
                f5 = f;
                f6 = f1;
              } 
            } 
            b++;
            solverVariable2 = solverVariable5;
            solverVariable1 = solverVariable6;
            bool2 = bool3;
            bool1 = bool;
            f2 = f5;
            f1 = f6;
          } 
          solverVariable3 = solverVariable;
          solverVariable4 = solverVariable1;
          bool4 = bool1;
          f3 = f;
          f4 = f1;
        } else {
          solverVariable3 = solverVariable2;
          solverVariable4 = solverVariable1;
          bool3 = bool2;
          bool4 = bool1;
          f3 = f2;
          f4 = f1;
          if (solverVariable2 == null) {
            solverVariable3 = solverVariable2;
            solverVariable4 = solverVariable1;
            bool3 = bool2;
            bool4 = bool1;
            f3 = f2;
            f4 = f1;
            if (f < 0.0F) {
              if (solverVariable1 == null) {
                bool3 = isNew(solverVariable, paramLinearSystem);
              } else if (f1 > f) {
                bool3 = isNew(solverVariable, paramLinearSystem);
              } else {
                solverVariable3 = solverVariable2;
                solverVariable4 = solverVariable1;
                bool3 = bool2;
                bool4 = bool1;
                f3 = f2;
                f4 = f1;
                if (!bool1) {
                  solverVariable3 = solverVariable2;
                  solverVariable4 = solverVariable1;
                  bool3 = bool2;
                  bool4 = bool1;
                  f3 = f2;
                  f4 = f1;
                  if (isNew(solverVariable, paramLinearSystem)) {
                    bool4 = true;
                    f4 = f;
                    f3 = f2;
                    bool3 = bool2;
                    solverVariable4 = solverVariable;
                    solverVariable3 = solverVariable2;
                  } 
                } 
                b++;
                solverVariable2 = solverVariable3;
                solverVariable1 = solverVariable4;
                bool2 = bool3;
                bool1 = bool4;
                f2 = f3;
                f1 = f4;
              } 
              bool4 = bool3;
              solverVariable3 = solverVariable2;
              solverVariable4 = solverVariable;
              bool3 = bool2;
              f3 = f2;
              f4 = f;
            } 
          } 
        } 
      } else {
        break;
      } 
      b++;
      solverVariable2 = solverVariable3;
      solverVariable1 = solverVariable4;
      bool2 = bool3;
      bool1 = bool4;
      f2 = f3;
    } 
    return (solverVariable2 != null) ? solverVariable2 : solverVariable1;
  }
  
  public void clear() {
    this.variables.clear();
    this.variable = null;
    this.constantValue = 0.0F;
  }
  
  ArrayRow createRowCentering(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2) {
    if (paramSolverVariable2 == paramSolverVariable3) {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable2, -2.0F);
      return this;
    } 
    if (paramFloat == 0.5F) {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      if (paramInt1 > 0 || paramInt2 > 0)
        this.constantValue = (-paramInt1 + paramInt2); 
    } else if (paramFloat <= 0.0F) {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.constantValue = paramInt1;
    } else if (paramFloat >= 1.0F) {
      this.variables.put(paramSolverVariable4, -1.0F);
      this.variables.put(paramSolverVariable3, 1.0F);
      this.constantValue = -paramInt2;
    } else {
      ArrayRowVariables arrayRowVariables = this.variables;
      float f = 1.0F - paramFloat;
      arrayRowVariables.put(paramSolverVariable1, f * 1.0F);
      this.variables.put(paramSolverVariable2, f * -1.0F);
      this.variables.put(paramSolverVariable3, -1.0F * paramFloat);
      this.variables.put(paramSolverVariable4, 1.0F * paramFloat);
      if (paramInt1 > 0 || paramInt2 > 0)
        this.constantValue = -paramInt1 * f + paramInt2 * paramFloat; 
    } 
    return this;
  }
  
  ArrayRow createRowDefinition(SolverVariable paramSolverVariable, int paramInt) {
    this.variable = paramSolverVariable;
    float f = paramInt;
    paramSolverVariable.computedValue = f;
    this.constantValue = f;
    this.isSimpleDefinition = true;
    return this;
  }
  
  ArrayRow createRowDimensionPercent(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, float paramFloat) {
    this.variables.put(paramSolverVariable1, -1.0F);
    this.variables.put(paramSolverVariable2, paramFloat);
    return this;
  }
  
  public ArrayRow createRowDimensionRatio(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat) {
    this.variables.put(paramSolverVariable1, -1.0F);
    this.variables.put(paramSolverVariable2, 1.0F);
    this.variables.put(paramSolverVariable3, paramFloat);
    this.variables.put(paramSolverVariable4, -paramFloat);
    return this;
  }
  
  public ArrayRow createRowEqualDimension(float paramFloat1, float paramFloat2, float paramFloat3, SolverVariable paramSolverVariable1, int paramInt1, SolverVariable paramSolverVariable2, int paramInt2, SolverVariable paramSolverVariable3, int paramInt3, SolverVariable paramSolverVariable4, int paramInt4) {
    if (paramFloat2 == 0.0F || paramFloat1 == paramFloat3) {
      this.constantValue = (-paramInt1 - paramInt2 + paramInt3 + paramInt4);
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
      return this;
    } 
    paramFloat1 = paramFloat1 / paramFloat2 / paramFloat3 / paramFloat2;
    this.constantValue = (-paramInt1 - paramInt2) + paramInt3 * paramFloat1 + paramInt4 * paramFloat1;
    this.variables.put(paramSolverVariable1, 1.0F);
    this.variables.put(paramSolverVariable2, -1.0F);
    this.variables.put(paramSolverVariable4, paramFloat1);
    this.variables.put(paramSolverVariable3, -paramFloat1);
    return this;
  }
  
  public ArrayRow createRowEqualMatchDimensions(float paramFloat1, float paramFloat2, float paramFloat3, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4) {
    this.constantValue = 0.0F;
    if (paramFloat2 == 0.0F || paramFloat1 == paramFloat3) {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
      return this;
    } 
    if (paramFloat1 == 0.0F) {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
    } else if (paramFloat3 == 0.0F) {
      this.variables.put(paramSolverVariable3, 1.0F);
      this.variables.put(paramSolverVariable4, -1.0F);
    } else {
      paramFloat1 = paramFloat1 / paramFloat2 / paramFloat3 / paramFloat2;
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable4, paramFloat1);
      this.variables.put(paramSolverVariable3, -paramFloat1);
    } 
    return this;
  }
  
  public ArrayRow createRowEquals(SolverVariable paramSolverVariable, int paramInt) {
    if (paramInt < 0) {
      this.constantValue = (paramInt * -1);
      this.variables.put(paramSolverVariable, 1.0F);
    } else {
      this.constantValue = paramInt;
      this.variables.put(paramSolverVariable, -1.0F);
    } 
    return this;
  }
  
  public ArrayRow createRowEquals(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt) {
    int i = 0;
    int j = 0;
    if (paramInt != 0) {
      i = j;
      j = paramInt;
      if (paramInt < 0) {
        j = paramInt * -1;
        i = 1;
      } 
      this.constantValue = j;
    } 
    if (i == 0) {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
    } else {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
    } 
    return this;
  }
  
  public ArrayRow createRowGreaterThan(SolverVariable paramSolverVariable1, int paramInt, SolverVariable paramSolverVariable2) {
    this.constantValue = paramInt;
    this.variables.put(paramSolverVariable1, -1.0F);
    return this;
  }
  
  public ArrayRow createRowGreaterThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, int paramInt) {
    int i = 0;
    int j = 0;
    if (paramInt != 0) {
      i = j;
      j = paramInt;
      if (paramInt < 0) {
        j = paramInt * -1;
        i = 1;
      } 
      this.constantValue = j;
    } 
    if (i == 0) {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.variables.put(paramSolverVariable3, 1.0F);
    } else {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
    } 
    return this;
  }
  
  public ArrayRow createRowLowerThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, int paramInt) {
    int i = 0;
    int j = 0;
    if (paramInt != 0) {
      i = j;
      j = paramInt;
      if (paramInt < 0) {
        j = paramInt * -1;
        i = 1;
      } 
      this.constantValue = j;
    } 
    if (i == 0) {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
    } else {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable3, 1.0F);
    } 
    return this;
  }
  
  public ArrayRow createRowWithAngle(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat) {
    this.variables.put(paramSolverVariable3, 0.5F);
    this.variables.put(paramSolverVariable4, 0.5F);
    this.variables.put(paramSolverVariable1, -0.5F);
    this.variables.put(paramSolverVariable2, -0.5F);
    this.constantValue = -paramFloat;
    return this;
  }
  
  void ensurePositiveConstant() {
    float f = this.constantValue;
    if (f < 0.0F) {
      this.constantValue = f * -1.0F;
      this.variables.invert();
    } 
  }
  
  public SolverVariable getKey() {
    return this.variable;
  }
  
  public SolverVariable getPivotCandidate(LinearSystem paramLinearSystem, boolean[] paramArrayOfboolean) {
    return pickPivotInVariables(paramArrayOfboolean, null);
  }
  
  boolean hasKeyVariable() {
    boolean bool;
    SolverVariable solverVariable = this.variable;
    if (solverVariable != null && (solverVariable.mType == SolverVariable.Type.UNRESTRICTED || this.constantValue >= 0.0F)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  boolean hasVariable(SolverVariable paramSolverVariable) {
    return this.variables.contains(paramSolverVariable);
  }
  
  public void initFromRow(LinearSystem.Row paramRow) {
    if (paramRow instanceof ArrayRow) {
      paramRow = paramRow;
      this.variable = null;
      this.variables.clear();
      for (byte b = 0; b < ((ArrayRow)paramRow).variables.getCurrentSize(); b++) {
        SolverVariable solverVariable = ((ArrayRow)paramRow).variables.getVariable(b);
        float f = ((ArrayRow)paramRow).variables.getVariableValue(b);
        this.variables.add(solverVariable, f, true);
      } 
    } 
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.variable == null && this.constantValue == 0.0F && this.variables.getCurrentSize() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public SolverVariable pickPivot(SolverVariable paramSolverVariable) {
    return pickPivotInVariables(null, paramSolverVariable);
  }
  
  void pivot(SolverVariable paramSolverVariable) {
    SolverVariable solverVariable = this.variable;
    if (solverVariable != null) {
      this.variables.put(solverVariable, -1.0F);
      this.variable.definitionId = -1;
      this.variable = null;
    } 
    float f = this.variables.remove(paramSolverVariable, true) * -1.0F;
    this.variable = paramSolverVariable;
    if (f == 1.0F)
      return; 
    this.constantValue /= f;
    this.variables.divideByAmount(f);
  }
  
  public void reset() {
    this.variable = null;
    this.variables.clear();
    this.constantValue = 0.0F;
    this.isSimpleDefinition = false;
  }
  
  int sizeInBytes() {
    byte b;
    if (this.variable != null) {
      b = 4;
    } else {
      b = 0;
    } 
    return b + 4 + 4 + this.variables.sizeInBytes();
  }
  
  String toReadableString() {
    // Byte code:
    //   0: aload_0
    //   1: getfield variable : Landroidx/constraintlayout/solver/SolverVariable;
    //   4: ifnonnull -> 14
    //   7: ldc '0'
    //   9: astore #7
    //   11: goto -> 48
    //   14: new java/lang/StringBuilder
    //   17: dup
    //   18: invokespecial <init> : ()V
    //   21: astore #7
    //   23: aload #7
    //   25: ldc ''
    //   27: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: aload #7
    //   33: aload_0
    //   34: getfield variable : Landroidx/constraintlayout/solver/SolverVariable;
    //   37: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload #7
    //   43: invokevirtual toString : ()Ljava/lang/String;
    //   46: astore #7
    //   48: new java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial <init> : ()V
    //   55: astore #8
    //   57: aload #8
    //   59: aload #7
    //   61: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: aload #8
    //   67: ldc ' = '
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: pop
    //   73: aload #8
    //   75: invokevirtual toString : ()Ljava/lang/String;
    //   78: astore #7
    //   80: aload_0
    //   81: getfield constantValue : F
    //   84: fstore_1
    //   85: iconst_0
    //   86: istore #4
    //   88: fload_1
    //   89: fconst_0
    //   90: fcmpl
    //   91: ifeq -> 133
    //   94: new java/lang/StringBuilder
    //   97: dup
    //   98: invokespecial <init> : ()V
    //   101: astore #8
    //   103: aload #8
    //   105: aload #7
    //   107: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload #8
    //   113: aload_0
    //   114: getfield constantValue : F
    //   117: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload #8
    //   123: invokevirtual toString : ()Ljava/lang/String;
    //   126: astore #7
    //   128: iconst_1
    //   129: istore_3
    //   130: goto -> 135
    //   133: iconst_0
    //   134: istore_3
    //   135: aload_0
    //   136: getfield variables : Landroidx/constraintlayout/solver/ArrayRow$ArrayRowVariables;
    //   139: invokeinterface getCurrentSize : ()I
    //   144: istore #5
    //   146: iload #4
    //   148: iload #5
    //   150: if_icmpge -> 432
    //   153: aload_0
    //   154: getfield variables : Landroidx/constraintlayout/solver/ArrayRow$ArrayRowVariables;
    //   157: iload #4
    //   159: invokeinterface getVariable : (I)Landroidx/constraintlayout/solver/SolverVariable;
    //   164: astore #8
    //   166: aload #8
    //   168: ifnonnull -> 174
    //   171: goto -> 426
    //   174: aload_0
    //   175: getfield variables : Landroidx/constraintlayout/solver/ArrayRow$ArrayRowVariables;
    //   178: iload #4
    //   180: invokeinterface getVariableValue : (I)F
    //   185: fstore_2
    //   186: fload_2
    //   187: fconst_0
    //   188: fcmpl
    //   189: istore #6
    //   191: iload #6
    //   193: ifne -> 199
    //   196: goto -> 426
    //   199: aload #8
    //   201: invokevirtual toString : ()Ljava/lang/String;
    //   204: astore #9
    //   206: iload_3
    //   207: ifne -> 257
    //   210: aload #7
    //   212: astore #8
    //   214: fload_2
    //   215: fstore_1
    //   216: fload_2
    //   217: fconst_0
    //   218: fcmpg
    //   219: ifge -> 336
    //   222: new java/lang/StringBuilder
    //   225: dup
    //   226: invokespecial <init> : ()V
    //   229: astore #8
    //   231: aload #8
    //   233: aload #7
    //   235: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload #8
    //   241: ldc '- '
    //   243: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   246: pop
    //   247: aload #8
    //   249: invokevirtual toString : ()Ljava/lang/String;
    //   252: astore #8
    //   254: goto -> 331
    //   257: iload #6
    //   259: ifle -> 299
    //   262: new java/lang/StringBuilder
    //   265: dup
    //   266: invokespecial <init> : ()V
    //   269: astore #8
    //   271: aload #8
    //   273: aload #7
    //   275: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: pop
    //   279: aload #8
    //   281: ldc ' + '
    //   283: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: pop
    //   287: aload #8
    //   289: invokevirtual toString : ()Ljava/lang/String;
    //   292: astore #8
    //   294: fload_2
    //   295: fstore_1
    //   296: goto -> 336
    //   299: new java/lang/StringBuilder
    //   302: dup
    //   303: invokespecial <init> : ()V
    //   306: astore #8
    //   308: aload #8
    //   310: aload #7
    //   312: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   315: pop
    //   316: aload #8
    //   318: ldc ' - '
    //   320: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   323: pop
    //   324: aload #8
    //   326: invokevirtual toString : ()Ljava/lang/String;
    //   329: astore #8
    //   331: fload_2
    //   332: ldc -1.0
    //   334: fmul
    //   335: fstore_1
    //   336: fload_1
    //   337: fconst_1
    //   338: fcmpl
    //   339: ifne -> 377
    //   342: new java/lang/StringBuilder
    //   345: dup
    //   346: invokespecial <init> : ()V
    //   349: astore #7
    //   351: aload #7
    //   353: aload #8
    //   355: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: pop
    //   359: aload #7
    //   361: aload #9
    //   363: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: pop
    //   367: aload #7
    //   369: invokevirtual toString : ()Ljava/lang/String;
    //   372: astore #7
    //   374: goto -> 424
    //   377: new java/lang/StringBuilder
    //   380: dup
    //   381: invokespecial <init> : ()V
    //   384: astore #7
    //   386: aload #7
    //   388: aload #8
    //   390: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload #7
    //   396: fload_1
    //   397: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   400: pop
    //   401: aload #7
    //   403: ldc ' '
    //   405: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   408: pop
    //   409: aload #7
    //   411: aload #9
    //   413: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: pop
    //   417: aload #7
    //   419: invokevirtual toString : ()Ljava/lang/String;
    //   422: astore #7
    //   424: iconst_1
    //   425: istore_3
    //   426: iinc #4, 1
    //   429: goto -> 146
    //   432: aload #7
    //   434: astore #8
    //   436: iload_3
    //   437: ifne -> 472
    //   440: new java/lang/StringBuilder
    //   443: dup
    //   444: invokespecial <init> : ()V
    //   447: astore #8
    //   449: aload #8
    //   451: aload #7
    //   453: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   456: pop
    //   457: aload #8
    //   459: ldc '0.0'
    //   461: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   464: pop
    //   465: aload #8
    //   467: invokevirtual toString : ()Ljava/lang/String;
    //   470: astore #8
    //   472: aload #8
    //   474: areturn
  }
  
  public String toString() {
    return toReadableString();
  }
  
  public void updateFromFinalVariable(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable, boolean paramBoolean) {
    if (!paramSolverVariable.isFinalValue)
      return; 
    float f = this.variables.get(paramSolverVariable);
    this.constantValue += paramSolverVariable.computedValue * f;
    this.variables.remove(paramSolverVariable, paramBoolean);
    if (paramBoolean)
      paramSolverVariable.removeFromRow(this); 
    if (LinearSystem.SIMPLIFY_SYNONYMS && paramSolverVariable != null && this.variables.getCurrentSize() == 0) {
      this.isSimpleDefinition = true;
      paramLinearSystem.hasSimpleDefinition = true;
    } 
  }
  
  public void updateFromRow(LinearSystem paramLinearSystem, ArrayRow paramArrayRow, boolean paramBoolean) {
    float f = this.variables.use(paramArrayRow, paramBoolean);
    this.constantValue += paramArrayRow.constantValue * f;
    if (paramBoolean)
      paramArrayRow.variable.removeFromRow(this); 
    if (LinearSystem.SIMPLIFY_SYNONYMS && this.variable != null && this.variables.getCurrentSize() == 0) {
      this.isSimpleDefinition = true;
      paramLinearSystem.hasSimpleDefinition = true;
    } 
  }
  
  public void updateFromSynonymVariable(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable, boolean paramBoolean) {
    if (!paramSolverVariable.isSynonym)
      return; 
    float f = this.variables.get(paramSolverVariable);
    this.constantValue += paramSolverVariable.synonymDelta * f;
    this.variables.remove(paramSolverVariable, paramBoolean);
    if (paramBoolean)
      paramSolverVariable.removeFromRow(this); 
    this.variables.add(paramLinearSystem.mCache.mIndexedVariables[paramSolverVariable.synonym], f, paramBoolean);
    if (LinearSystem.SIMPLIFY_SYNONYMS && paramSolverVariable != null && this.variables.getCurrentSize() == 0) {
      this.isSimpleDefinition = true;
      paramLinearSystem.hasSimpleDefinition = true;
    } 
  }
  
  public void updateFromSystem(LinearSystem paramLinearSystem) {
    if (paramLinearSystem.mRows.length == 0)
      return; 
    for (boolean bool = false; !bool; bool = true) {
      int i = this.variables.getCurrentSize();
      byte b;
      for (b = 0; b < i; b++) {
        SolverVariable solverVariable = this.variables.getVariable(b);
        if (solverVariable.definitionId != -1 || solverVariable.isFinalValue || solverVariable.isSynonym)
          this.variablesToUpdate.add(solverVariable); 
      } 
      i = this.variablesToUpdate.size();
      if (i > 0) {
        for (b = 0; b < i; b++) {
          SolverVariable solverVariable = this.variablesToUpdate.get(b);
          if (solverVariable.isFinalValue) {
            updateFromFinalVariable(paramLinearSystem, solverVariable, true);
          } else if (solverVariable.isSynonym) {
            updateFromSynonymVariable(paramLinearSystem, solverVariable, true);
          } else {
            updateFromRow(paramLinearSystem, paramLinearSystem.mRows[solverVariable.definitionId], true);
          } 
        } 
        this.variablesToUpdate.clear();
        continue;
      } 
    } 
    if (LinearSystem.SIMPLIFY_SYNONYMS && this.variable != null && this.variables.getCurrentSize() == 0) {
      this.isSimpleDefinition = true;
      paramLinearSystem.hasSimpleDefinition = true;
    } 
  }
  
  public static interface ArrayRowVariables {
    void add(SolverVariable param1SolverVariable, float param1Float, boolean param1Boolean);
    
    void clear();
    
    boolean contains(SolverVariable param1SolverVariable);
    
    void display();
    
    void divideByAmount(float param1Float);
    
    float get(SolverVariable param1SolverVariable);
    
    int getCurrentSize();
    
    SolverVariable getVariable(int param1Int);
    
    float getVariableValue(int param1Int);
    
    int indexOf(SolverVariable param1SolverVariable);
    
    void invert();
    
    void put(SolverVariable param1SolverVariable, float param1Float);
    
    float remove(SolverVariable param1SolverVariable, boolean param1Boolean);
    
    int sizeInBytes();
    
    float use(ArrayRow param1ArrayRow, boolean param1Boolean);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\ArrayRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */