package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem {
  public static long ARRAY_ROW_CREATION = 0L;
  
  public static final boolean DEBUG = false;
  
  private static final boolean DEBUG_CONSTRAINTS = false;
  
  public static final boolean FULL_DEBUG = false;
  
  public static final boolean MEASURE = false;
  
  public static long OPTIMIZED_ARRAY_ROW_CREATION = 0L;
  
  public static boolean OPTIMIZED_ENGINE = false;
  
  private static int POOL_SIZE = 1000;
  
  public static boolean SIMPLIFY_SYNONYMS = true;
  
  public static boolean SKIP_COLUMNS = true;
  
  public static boolean USE_BASIC_SYNONYMS = true;
  
  public static boolean USE_DEPENDENCY_ORDERING = false;
  
  public static boolean USE_SYNONYMS = true;
  
  public static Metrics sMetrics;
  
  private int TABLE_SIZE = 32;
  
  public boolean graphOptimizer = false;
  
  public boolean hasSimpleDefinition = false;
  
  private boolean[] mAlreadyTestedCandidates = new boolean[32];
  
  final Cache mCache;
  
  private Row mGoal;
  
  private int mMaxColumns = 32;
  
  private int mMaxRows = 32;
  
  int mNumColumns = 1;
  
  int mNumRows = 0;
  
  private SolverVariable[] mPoolVariables = new SolverVariable[POOL_SIZE];
  
  private int mPoolVariablesCount = 0;
  
  ArrayRow[] mRows = null;
  
  private Row mTempGoal;
  
  private HashMap<String, SolverVariable> mVariables = null;
  
  int mVariablesID = 0;
  
  public boolean newgraphOptimizer = false;
  
  public LinearSystem() {
    this.mRows = new ArrayRow[32];
    releaseRows();
    this.mCache = new Cache();
    this.mGoal = new PriorityGoalRow(this.mCache);
    if (OPTIMIZED_ENGINE) {
      this.mTempGoal = new ValuesRow(this.mCache);
    } else {
      this.mTempGoal = new ArrayRow(this.mCache);
    } 
  }
  
  private SolverVariable acquireSolverVariable(SolverVariable.Type paramType, String paramString) {
    SolverVariable solverVariable1;
    SolverVariable solverVariable2 = this.mCache.solverVariablePool.acquire();
    if (solverVariable2 == null) {
      solverVariable2 = new SolverVariable(paramType, paramString);
      solverVariable2.setType(paramType, paramString);
      solverVariable1 = solverVariable2;
    } else {
      solverVariable2.reset();
      solverVariable2.setType((SolverVariable.Type)solverVariable1, paramString);
      solverVariable1 = solverVariable2;
    } 
    int i = this.mPoolVariablesCount;
    int j = POOL_SIZE;
    if (i >= j) {
      i = j * 2;
      POOL_SIZE = i;
      this.mPoolVariables = Arrays.<SolverVariable>copyOf(this.mPoolVariables, i);
    } 
    SolverVariable[] arrayOfSolverVariable = this.mPoolVariables;
    i = this.mPoolVariablesCount;
    this.mPoolVariablesCount = i + 1;
    arrayOfSolverVariable[i] = solverVariable1;
    return solverVariable1;
  }
  
  private void addError(ArrayRow paramArrayRow) {
    paramArrayRow.addError(this, 0);
  }
  
  private final void addRow(ArrayRow paramArrayRow) {
    if (SIMPLIFY_SYNONYMS && paramArrayRow.isSimpleDefinition) {
      paramArrayRow.variable.setFinalValue(this, paramArrayRow.constantValue);
    } else {
      this.mRows[this.mNumRows] = paramArrayRow;
      paramArrayRow.variable.definitionId = this.mNumRows;
      this.mNumRows++;
      paramArrayRow.variable.updateReferencesWithNewDefinition(this, paramArrayRow);
    } 
    if (SIMPLIFY_SYNONYMS && this.hasSimpleDefinition) {
      for (int i = 0; i < this.mNumRows; i = j + 1) {
        if (this.mRows[i] == null)
          System.out.println("WTF"); 
        ArrayRow[] arrayOfArrayRow = this.mRows;
        int j = i;
        if (arrayOfArrayRow[i] != null) {
          j = i;
          if ((arrayOfArrayRow[i]).isSimpleDefinition) {
            ArrayRow arrayRow = this.mRows[i];
            arrayRow.variable.setFinalValue(this, arrayRow.constantValue);
            if (OPTIMIZED_ENGINE) {
              this.mCache.optimizedArrayRowPool.release(arrayRow);
            } else {
              this.mCache.arrayRowPool.release(arrayRow);
            } 
            this.mRows[i] = null;
            j = i + 1;
            int k = j;
            while (true) {
              int m = this.mNumRows;
              if (j < m) {
                ArrayRow[] arrayOfArrayRow1 = this.mRows;
                k = j - 1;
                arrayOfArrayRow1[k] = arrayOfArrayRow1[j];
                if ((arrayOfArrayRow1[k]).variable.definitionId == j)
                  (this.mRows[k]).variable.definitionId = k; 
                k = j;
                j++;
                continue;
              } 
              if (k < m)
                this.mRows[k] = null; 
              this.mNumRows--;
              j = i - 1;
              break;
            } 
          } 
        } 
      } 
      this.hasSimpleDefinition = false;
    } 
  }
  
  private void addSingleError(ArrayRow paramArrayRow, int paramInt) {
    addSingleError(paramArrayRow, paramInt, 0);
  }
  
  private void computeValues() {
    for (byte b = 0; b < this.mNumRows; b++) {
      ArrayRow arrayRow = this.mRows[b];
      arrayRow.variable.computedValue = arrayRow.constantValue;
    } 
  }
  
  public static ArrayRow createRowDimensionPercent(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, float paramFloat) {
    return paramLinearSystem.createRow().createRowDimensionPercent(paramSolverVariable1, paramSolverVariable2, paramFloat);
  }
  
  private SolverVariable createVariable(String paramString, SolverVariable.Type paramType) {
    Metrics metrics = sMetrics;
    if (metrics != null)
      metrics.variables++; 
    if (this.mNumColumns + 1 >= this.mMaxColumns)
      increaseTableSize(); 
    SolverVariable solverVariable = acquireSolverVariable(paramType, null);
    solverVariable.setName(paramString);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns++;
    solverVariable.id = i;
    if (this.mVariables == null)
      this.mVariables = new HashMap<String, SolverVariable>(); 
    this.mVariables.put(paramString, solverVariable);
    this.mCache.mIndexedVariables[this.mVariablesID] = solverVariable;
    return solverVariable;
  }
  
  private void displayRows() {
    displaySolverVariables();
    String str = "";
    for (byte b = 0; b < this.mNumRows; b++) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(this.mRows[b]);
      str = stringBuilder1.toString();
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append("\n");
      str = stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(this.mGoal);
    stringBuilder.append("\n");
    str = stringBuilder.toString();
    System.out.println(str);
  }
  
  private void displaySolverVariables() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Display Rows (");
    stringBuilder.append(this.mNumRows);
    stringBuilder.append("x");
    stringBuilder.append(this.mNumColumns);
    stringBuilder.append(")\n");
    String str = stringBuilder.toString();
    System.out.println(str);
  }
  
  private int enforceBFS(Row paramRow) throws Exception {
    int i = 0;
    while (true) {
      if (i < this.mNumRows) {
        if ((this.mRows[i]).variable.mType != SolverVariable.Type.UNRESTRICTED && (this.mRows[i]).constantValue < 0.0F) {
          i = 1;
          break;
        } 
        i++;
        continue;
      } 
      i = 0;
      break;
    } 
    if (i != 0) {
      boolean bool = false;
      i = 0;
      while (!bool) {
        Metrics metrics = sMetrics;
        if (metrics != null)
          metrics.bfs++; 
        int j = i + 1;
        float f = Float.MAX_VALUE;
        i = 0;
        byte b1 = -1;
        byte b3 = -1;
        byte b2;
        for (b2 = 0; i < this.mNumRows; b2 = b6) {
          float f1;
          byte b4;
          byte b5;
          byte b6;
          ArrayRow arrayRow = this.mRows[i];
          if (arrayRow.variable.mType == SolverVariable.Type.UNRESTRICTED) {
            f1 = f;
            b4 = b1;
            b5 = b3;
            b6 = b2;
          } else if (arrayRow.isSimpleDefinition) {
            f1 = f;
            b4 = b1;
            b5 = b3;
            b6 = b2;
          } else {
            f1 = f;
            b4 = b1;
            b5 = b3;
            b6 = b2;
            if (arrayRow.constantValue < 0.0F) {
              byte b;
              if (SKIP_COLUMNS) {
                int k = arrayRow.variables.getCurrentSize();
                b = 0;
                while (true) {
                  f1 = f;
                  b4 = b1;
                  b5 = b3;
                  b6 = b2;
                  if (b < k) {
                    SolverVariable solverVariable = arrayRow.variables.getVariable(b);
                    float f2 = arrayRow.variables.get(solverVariable);
                    if (f2 <= 0.0F) {
                      f1 = f;
                      b6 = b1;
                      b5 = b3;
                      b4 = b2;
                      continue;
                    } 
                    b4 = 0;
                    byte b7 = b1;
                    b1 = b4;
                    while (true) {
                      f1 = f;
                      b6 = b7;
                      b5 = b3;
                      b4 = b2;
                      b1++;
                      b2 = b4;
                    } 
                    continue;
                  } 
                  break;
                  b++;
                  f = f1;
                  b1 = b6;
                  b3 = b5;
                  b2 = b4;
                } 
              } else {
                byte b7 = 1;
                while (true) {
                  f1 = f;
                  b4 = b1;
                  b5 = b3;
                  b6 = b2;
                  if (b7 < this.mNumColumns) {
                    SolverVariable solverVariable = this.mCache.mIndexedVariables[b7];
                    float f2 = arrayRow.variables.get(solverVariable);
                    if (f2 <= 0.0F) {
                      f1 = f;
                      b5 = b1;
                      b = b3;
                      b6 = b2;
                      continue;
                    } 
                    b5 = 0;
                    b4 = b1;
                    b1 = b5;
                    while (true) {
                      f1 = f;
                      b5 = b4;
                      b = b3;
                      b6 = b2;
                      b1++;
                      b2 = b5;
                    } 
                    continue;
                  } 
                  break;
                  b7++;
                  f = f1;
                  b1 = b5;
                  b3 = b;
                  b2 = b6;
                } 
              } 
            } 
          } 
          i++;
          f = f1;
          b1 = b4;
          b3 = b5;
        } 
        if (b1 != -1) {
          ArrayRow arrayRow = this.mRows[b1];
          arrayRow.variable.definitionId = -1;
          metrics = sMetrics;
          if (metrics != null)
            metrics.pivots++; 
          arrayRow.pivot(this.mCache.mIndexedVariables[b3]);
          arrayRow.variable.definitionId = b1;
          arrayRow.variable.updateReferencesWithNewDefinition(this, arrayRow);
        } else {
          bool = true;
        } 
        i = j;
        if (j > this.mNumColumns / 2) {
          bool = true;
          i = j;
        } 
      } 
    } else {
      i = 0;
    } 
    return i;
  }
  
  private String getDisplaySize(int paramInt) {
    int i = paramInt * 4;
    int j = i / 1024;
    paramInt = j / 1024;
    if (paramInt > 0) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("");
      stringBuilder1.append(paramInt);
      stringBuilder1.append(" Mb");
      return stringBuilder1.toString();
    } 
    if (j > 0) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("");
      stringBuilder1.append(j);
      stringBuilder1.append(" Kb");
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(i);
    stringBuilder.append(" bytes");
    return stringBuilder.toString();
  }
  
  private String getDisplayStrength(int paramInt) {
    return (paramInt == 1) ? "LOW" : ((paramInt == 2) ? "MEDIUM" : ((paramInt == 3) ? "HIGH" : ((paramInt == 4) ? "HIGHEST" : ((paramInt == 5) ? "EQUALITY" : ((paramInt == 8) ? "FIXED" : ((paramInt == 6) ? "BARRIER" : "NONE"))))));
  }
  
  public static Metrics getMetrics() {
    return sMetrics;
  }
  
  private void increaseTableSize() {
    int i = this.TABLE_SIZE * 2;
    this.TABLE_SIZE = i;
    this.mRows = Arrays.<ArrayRow>copyOf(this.mRows, i);
    Cache cache = this.mCache;
    cache.mIndexedVariables = Arrays.<SolverVariable>copyOf(cache.mIndexedVariables, this.TABLE_SIZE);
    i = this.TABLE_SIZE;
    this.mAlreadyTestedCandidates = new boolean[i];
    this.mMaxColumns = i;
    this.mMaxRows = i;
    Metrics metrics = sMetrics;
    if (metrics != null) {
      metrics.tableSizeIncrease++;
      metrics = sMetrics;
      metrics.maxTableSize = Math.max(metrics.maxTableSize, this.TABLE_SIZE);
      metrics = sMetrics;
      metrics.lastTableSize = metrics.maxTableSize;
    } 
  }
  
  private final int optimize(Row paramRow, boolean paramBoolean) {
    Metrics metrics = sMetrics;
    if (metrics != null)
      metrics.optimize++; 
    int i;
    for (i = 0; i < this.mNumColumns; i++)
      this.mAlreadyTestedCandidates[i] = false; 
    boolean bool = false;
    for (i = 0; !bool; i = j) {
      metrics = sMetrics;
      if (metrics != null)
        metrics.iterations++; 
      int j = i + 1;
      if (j >= this.mNumColumns * 2)
        return j; 
      if (paramRow.getKey() != null)
        this.mAlreadyTestedCandidates[(paramRow.getKey()).id] = true; 
      SolverVariable solverVariable = paramRow.getPivotCandidate(this, this.mAlreadyTestedCandidates);
      if (solverVariable != null) {
        if (this.mAlreadyTestedCandidates[solverVariable.id])
          return j; 
        this.mAlreadyTestedCandidates[solverVariable.id] = true;
      } 
      if (solverVariable != null) {
        float f = Float.MAX_VALUE;
        i = 0;
        int k;
        for (k = -1; i < this.mNumRows; k = m) {
          float f1;
          int m;
          ArrayRow arrayRow = this.mRows[i];
          if (arrayRow.variable.mType == SolverVariable.Type.UNRESTRICTED) {
            f1 = f;
            m = k;
          } else if (arrayRow.isSimpleDefinition) {
            f1 = f;
            m = k;
          } else {
            f1 = f;
            m = k;
            if (arrayRow.hasVariable(solverVariable)) {
              float f2 = arrayRow.variables.get(solverVariable);
              f1 = f;
              m = k;
              if (f2 < 0.0F) {
                f2 = -arrayRow.constantValue / f2;
                f1 = f;
                m = k;
                if (f2 < f) {
                  m = i;
                  f1 = f2;
                } 
              } 
            } 
          } 
          i++;
          f = f1;
        } 
        i = j;
        if (k > -1) {
          ArrayRow arrayRow = this.mRows[k];
          arrayRow.variable.definitionId = -1;
          Metrics metrics1 = sMetrics;
          if (metrics1 != null)
            metrics1.pivots++; 
          arrayRow.pivot(solverVariable);
          arrayRow.variable.definitionId = k;
          arrayRow.variable.updateReferencesWithNewDefinition(this, arrayRow);
          i = j;
        } 
        continue;
      } 
      bool = true;
    } 
    return i;
  }
  
  private void releaseRows() {
    boolean bool = OPTIMIZED_ENGINE;
    byte b1 = 0;
    byte b2 = 0;
    if (bool) {
      for (b1 = b2; b1 < this.mNumRows; b1++) {
        ArrayRow arrayRow = this.mRows[b1];
        if (arrayRow != null)
          this.mCache.optimizedArrayRowPool.release(arrayRow); 
        this.mRows[b1] = null;
      } 
    } else {
      while (b1 < this.mNumRows) {
        ArrayRow arrayRow = this.mRows[b1];
        if (arrayRow != null)
          this.mCache.arrayRowPool.release(arrayRow); 
        this.mRows[b1] = null;
        b1++;
      } 
    } 
  }
  
  public void addCenterPoint(ConstraintWidget paramConstraintWidget1, ConstraintWidget paramConstraintWidget2, float paramFloat, int paramInt) {
    SolverVariable solverVariable4 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.LEFT));
    SolverVariable solverVariable5 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.TOP));
    SolverVariable solverVariable3 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT));
    SolverVariable solverVariable7 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.BOTTOM));
    SolverVariable solverVariable6 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT));
    SolverVariable solverVariable8 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.TOP));
    SolverVariable solverVariable1 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT));
    SolverVariable solverVariable2 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM));
    ArrayRow arrayRow2 = createRow();
    double d1 = paramFloat;
    double d2 = Math.sin(d1);
    double d3 = paramInt;
    arrayRow2.createRowWithAngle(solverVariable5, solverVariable7, solverVariable8, solverVariable2, (float)(d2 * d3));
    addConstraint(arrayRow2);
    ArrayRow arrayRow1 = createRow();
    arrayRow1.createRowWithAngle(solverVariable4, solverVariable3, solverVariable6, solverVariable1, (float)(Math.cos(d1) * d3));
    addConstraint(arrayRow1);
  }
  
  public void addCentering(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2, int paramInt3) {
    ArrayRow arrayRow = createRow();
    arrayRow.createRowCentering(paramSolverVariable1, paramSolverVariable2, paramInt1, paramFloat, paramSolverVariable3, paramSolverVariable4, paramInt2);
    if (paramInt3 != 8)
      arrayRow.addError(this, paramInt3); 
    addConstraint(arrayRow);
  }
  
  public void addConstraint(ArrayRow paramArrayRow) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   8: astore #4
    //   10: aload #4
    //   12: ifnull -> 51
    //   15: aload #4
    //   17: aload #4
    //   19: getfield constraints : J
    //   22: lconst_1
    //   23: ladd
    //   24: putfield constraints : J
    //   27: aload_1
    //   28: getfield isSimpleDefinition : Z
    //   31: ifeq -> 51
    //   34: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   37: astore #4
    //   39: aload #4
    //   41: aload #4
    //   43: getfield simpleconstraints : J
    //   46: lconst_1
    //   47: ladd
    //   48: putfield simpleconstraints : J
    //   51: aload_0
    //   52: getfield mNumRows : I
    //   55: istore_2
    //   56: iconst_1
    //   57: istore_3
    //   58: iload_2
    //   59: iconst_1
    //   60: iadd
    //   61: aload_0
    //   62: getfield mMaxRows : I
    //   65: if_icmpge -> 81
    //   68: aload_0
    //   69: getfield mNumColumns : I
    //   72: iconst_1
    //   73: iadd
    //   74: aload_0
    //   75: getfield mMaxColumns : I
    //   78: if_icmplt -> 85
    //   81: aload_0
    //   82: invokespecial increaseTableSize : ()V
    //   85: iconst_0
    //   86: istore_2
    //   87: aload_1
    //   88: getfield isSimpleDefinition : Z
    //   91: ifne -> 310
    //   94: aload_1
    //   95: aload_0
    //   96: invokevirtual updateFromSystem : (Landroidx/constraintlayout/solver/LinearSystem;)V
    //   99: aload_1
    //   100: invokevirtual isEmpty : ()Z
    //   103: ifeq -> 107
    //   106: return
    //   107: aload_1
    //   108: invokevirtual ensurePositiveConstant : ()V
    //   111: aload_1
    //   112: aload_0
    //   113: invokevirtual chooseSubject : (Landroidx/constraintlayout/solver/LinearSystem;)Z
    //   116: ifeq -> 300
    //   119: aload_0
    //   120: invokevirtual createExtraVariable : ()Landroidx/constraintlayout/solver/SolverVariable;
    //   123: astore #4
    //   125: aload_1
    //   126: aload #4
    //   128: putfield variable : Landroidx/constraintlayout/solver/SolverVariable;
    //   131: aload_0
    //   132: getfield mNumRows : I
    //   135: istore_2
    //   136: aload_0
    //   137: aload_1
    //   138: invokespecial addRow : (Landroidx/constraintlayout/solver/ArrayRow;)V
    //   141: aload_0
    //   142: getfield mNumRows : I
    //   145: iload_2
    //   146: iconst_1
    //   147: iadd
    //   148: if_icmpne -> 300
    //   151: aload_0
    //   152: getfield mTempGoal : Landroidx/constraintlayout/solver/LinearSystem$Row;
    //   155: aload_1
    //   156: invokeinterface initFromRow : (Landroidx/constraintlayout/solver/LinearSystem$Row;)V
    //   161: aload_0
    //   162: aload_0
    //   163: getfield mTempGoal : Landroidx/constraintlayout/solver/LinearSystem$Row;
    //   166: iconst_1
    //   167: invokespecial optimize : (Landroidx/constraintlayout/solver/LinearSystem$Row;Z)I
    //   170: pop
    //   171: iload_3
    //   172: istore_2
    //   173: aload #4
    //   175: getfield definitionId : I
    //   178: iconst_m1
    //   179: if_icmpne -> 302
    //   182: aload_1
    //   183: getfield variable : Landroidx/constraintlayout/solver/SolverVariable;
    //   186: aload #4
    //   188: if_acmpne -> 232
    //   191: aload_1
    //   192: aload #4
    //   194: invokevirtual pickPivot : (Landroidx/constraintlayout/solver/SolverVariable;)Landroidx/constraintlayout/solver/SolverVariable;
    //   197: astore #5
    //   199: aload #5
    //   201: ifnull -> 232
    //   204: getstatic androidx/constraintlayout/solver/LinearSystem.sMetrics : Landroidx/constraintlayout/solver/Metrics;
    //   207: astore #4
    //   209: aload #4
    //   211: ifnull -> 226
    //   214: aload #4
    //   216: aload #4
    //   218: getfield pivots : J
    //   221: lconst_1
    //   222: ladd
    //   223: putfield pivots : J
    //   226: aload_1
    //   227: aload #5
    //   229: invokevirtual pivot : (Landroidx/constraintlayout/solver/SolverVariable;)V
    //   232: aload_1
    //   233: getfield isSimpleDefinition : Z
    //   236: ifne -> 248
    //   239: aload_1
    //   240: getfield variable : Landroidx/constraintlayout/solver/SolverVariable;
    //   243: aload_0
    //   244: aload_1
    //   245: invokevirtual updateReferencesWithNewDefinition : (Landroidx/constraintlayout/solver/LinearSystem;Landroidx/constraintlayout/solver/ArrayRow;)V
    //   248: getstatic androidx/constraintlayout/solver/LinearSystem.OPTIMIZED_ENGINE : Z
    //   251: ifeq -> 271
    //   254: aload_0
    //   255: getfield mCache : Landroidx/constraintlayout/solver/Cache;
    //   258: getfield optimizedArrayRowPool : Landroidx/constraintlayout/solver/Pools$Pool;
    //   261: aload_1
    //   262: invokeinterface release : (Ljava/lang/Object;)Z
    //   267: pop
    //   268: goto -> 285
    //   271: aload_0
    //   272: getfield mCache : Landroidx/constraintlayout/solver/Cache;
    //   275: getfield arrayRowPool : Landroidx/constraintlayout/solver/Pools$Pool;
    //   278: aload_1
    //   279: invokeinterface release : (Ljava/lang/Object;)Z
    //   284: pop
    //   285: aload_0
    //   286: aload_0
    //   287: getfield mNumRows : I
    //   290: iconst_1
    //   291: isub
    //   292: putfield mNumRows : I
    //   295: iload_3
    //   296: istore_2
    //   297: goto -> 302
    //   300: iconst_0
    //   301: istore_2
    //   302: aload_1
    //   303: invokevirtual hasKeyVariable : ()Z
    //   306: ifne -> 310
    //   309: return
    //   310: iload_2
    //   311: ifne -> 319
    //   314: aload_0
    //   315: aload_1
    //   316: invokespecial addRow : (Landroidx/constraintlayout/solver/ArrayRow;)V
    //   319: return
  }
  
  public ArrayRow addEquality(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2) {
    if (USE_BASIC_SYNONYMS && paramInt2 == 8 && paramSolverVariable2.isFinalValue && paramSolverVariable1.definitionId == -1) {
      paramSolverVariable1.setFinalValue(this, paramSolverVariable2.computedValue + paramInt1);
      return null;
    } 
    ArrayRow arrayRow = createRow();
    arrayRow.createRowEquals(paramSolverVariable1, paramSolverVariable2, paramInt1);
    if (paramInt2 != 8)
      arrayRow.addError(this, paramInt2); 
    addConstraint(arrayRow);
    return arrayRow;
  }
  
  public void addEquality(SolverVariable paramSolverVariable, int paramInt) {
    if (USE_BASIC_SYNONYMS && paramSolverVariable.definitionId == -1) {
      float f = paramInt;
      paramSolverVariable.setFinalValue(this, f);
      for (paramInt = 0; paramInt < this.mVariablesID + 1; paramInt++) {
        SolverVariable solverVariable = this.mCache.mIndexedVariables[paramInt];
        if (solverVariable != null && solverVariable.isSynonym && solverVariable.synonym == paramSolverVariable.id)
          solverVariable.setFinalValue(this, solverVariable.synonymDelta + f); 
      } 
      return;
    } 
    int i = paramSolverVariable.definitionId;
    if (paramSolverVariable.definitionId != -1) {
      ArrayRow arrayRow = this.mRows[i];
      if (arrayRow.isSimpleDefinition) {
        arrayRow.constantValue = paramInt;
      } else if (arrayRow.variables.getCurrentSize() == 0) {
        arrayRow.isSimpleDefinition = true;
        arrayRow.constantValue = paramInt;
      } else {
        arrayRow = createRow();
        arrayRow.createRowEquals(paramSolverVariable, paramInt);
        addConstraint(arrayRow);
      } 
    } else {
      ArrayRow arrayRow = createRow();
      arrayRow.createRowDefinition(paramSolverVariable, paramInt);
      addConstraint(arrayRow);
    } 
  }
  
  public void addGreaterBarrier(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean) {
    ArrayRow arrayRow = createRow();
    SolverVariable solverVariable = createSlackVariable();
    solverVariable.strength = 0;
    arrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, solverVariable, paramInt);
    addConstraint(arrayRow);
  }
  
  public void addGreaterThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2) {
    ArrayRow arrayRow = createRow();
    SolverVariable solverVariable = createSlackVariable();
    solverVariable.strength = 0;
    arrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, solverVariable, paramInt1);
    if (paramInt2 != 8)
      addSingleError(arrayRow, (int)(arrayRow.variables.get(solverVariable) * -1.0F), paramInt2); 
    addConstraint(arrayRow);
  }
  
  public void addLowerBarrier(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean) {
    ArrayRow arrayRow = createRow();
    SolverVariable solverVariable = createSlackVariable();
    solverVariable.strength = 0;
    arrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, solverVariable, paramInt);
    addConstraint(arrayRow);
  }
  
  public void addLowerThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2) {
    ArrayRow arrayRow = createRow();
    SolverVariable solverVariable = createSlackVariable();
    solverVariable.strength = 0;
    arrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, solverVariable, paramInt1);
    if (paramInt2 != 8)
      addSingleError(arrayRow, (int)(arrayRow.variables.get(solverVariable) * -1.0F), paramInt2); 
    addConstraint(arrayRow);
  }
  
  public void addRatio(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat, int paramInt) {
    ArrayRow arrayRow = createRow();
    arrayRow.createRowDimensionRatio(paramSolverVariable1, paramSolverVariable2, paramSolverVariable3, paramSolverVariable4, paramFloat);
    if (paramInt != 8)
      arrayRow.addError(this, paramInt); 
    addConstraint(arrayRow);
  }
  
  void addSingleError(ArrayRow paramArrayRow, int paramInt1, int paramInt2) {
    paramArrayRow.addSingleError(createErrorVariable(paramInt2, null), paramInt1);
  }
  
  public void addSynonym(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt) {
    if (paramSolverVariable1.definitionId == -1 && paramInt == 0) {
      SolverVariable solverVariable = paramSolverVariable2;
      if (paramSolverVariable2.isSynonym) {
        float f = paramSolverVariable2.synonymDelta;
        solverVariable = this.mCache.mIndexedVariables[paramSolverVariable2.synonym];
      } 
      if (paramSolverVariable1.isSynonym) {
        float f = paramSolverVariable1.synonymDelta;
        paramSolverVariable1 = this.mCache.mIndexedVariables[paramSolverVariable1.synonym];
      } else {
        paramSolverVariable1.setSynonym(this, solverVariable, 0.0F);
      } 
    } else {
      addEquality(paramSolverVariable1, paramSolverVariable2, paramInt, 8);
    } 
  }
  
  final void cleanupRows() {
    for (int i = 0;; i = j + 1) {
      int j;
      if (i < this.mNumRows) {
        ArrayRow arrayRow = this.mRows[i];
        if (arrayRow.variables.getCurrentSize() == 0)
          arrayRow.isSimpleDefinition = true; 
        j = i;
        if (arrayRow.isSimpleDefinition) {
          arrayRow.variable.computedValue = arrayRow.constantValue;
          arrayRow.variable.removeFromRow(arrayRow);
          j = i;
          while (true) {
            int k = this.mNumRows;
            if (j < k - 1) {
              ArrayRow[] arrayOfArrayRow = this.mRows;
              k = j + 1;
              arrayOfArrayRow[j] = arrayOfArrayRow[k];
              j = k;
              continue;
            } 
            this.mRows[k - 1] = null;
            this.mNumRows = k - 1;
            j = i - 1;
            if (OPTIMIZED_ENGINE) {
              this.mCache.optimizedArrayRowPool.release(arrayRow);
              break;
            } 
            this.mCache.arrayRowPool.release(arrayRow);
            i = j + 1;
          } 
        } 
      } else {
        break;
      } 
    } 
  }
  
  public SolverVariable createErrorVariable(int paramInt, String paramString) {
    Metrics metrics = sMetrics;
    if (metrics != null)
      metrics.errors++; 
    if (this.mNumColumns + 1 >= this.mMaxColumns)
      increaseTableSize(); 
    SolverVariable solverVariable = acquireSolverVariable(SolverVariable.Type.ERROR, paramString);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns++;
    solverVariable.id = i;
    solverVariable.strength = paramInt;
    this.mCache.mIndexedVariables[this.mVariablesID] = solverVariable;
    this.mGoal.addError(solverVariable);
    return solverVariable;
  }
  
  public SolverVariable createExtraVariable() {
    Metrics metrics = sMetrics;
    if (metrics != null)
      metrics.extravariables++; 
    if (this.mNumColumns + 1 >= this.mMaxColumns)
      increaseTableSize(); 
    SolverVariable solverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns++;
    solverVariable.id = i;
    this.mCache.mIndexedVariables[this.mVariablesID] = solverVariable;
    return solverVariable;
  }
  
  public SolverVariable createObjectVariable(Object paramObject) {
    SolverVariable solverVariable = null;
    if (paramObject == null)
      return null; 
    if (this.mNumColumns + 1 >= this.mMaxColumns)
      increaseTableSize(); 
    if (paramObject instanceof ConstraintAnchor) {
      ConstraintAnchor constraintAnchor = (ConstraintAnchor)paramObject;
      solverVariable = constraintAnchor.getSolverVariable();
      paramObject = solverVariable;
      if (solverVariable == null) {
        constraintAnchor.resetSolverVariable(this.mCache);
        paramObject = constraintAnchor.getSolverVariable();
      } 
      if (((SolverVariable)paramObject).id != -1 && ((SolverVariable)paramObject).id <= this.mVariablesID) {
        Object object = paramObject;
        if (this.mCache.mIndexedVariables[((SolverVariable)paramObject).id] == null) {
          if (((SolverVariable)paramObject).id != -1)
            paramObject.reset(); 
          int j = this.mVariablesID + 1;
          this.mVariablesID = j;
          this.mNumColumns++;
          ((SolverVariable)paramObject).id = j;
          ((SolverVariable)paramObject).mType = SolverVariable.Type.UNRESTRICTED;
          this.mCache.mIndexedVariables[this.mVariablesID] = (SolverVariable)paramObject;
          return (SolverVariable)paramObject;
        } 
        return (SolverVariable)object;
      } 
    } else {
      return solverVariable;
    } 
    if (((SolverVariable)paramObject).id != -1)
      paramObject.reset(); 
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns++;
    ((SolverVariable)paramObject).id = i;
    ((SolverVariable)paramObject).mType = SolverVariable.Type.UNRESTRICTED;
    this.mCache.mIndexedVariables[this.mVariablesID] = (SolverVariable)paramObject;
    return (SolverVariable)paramObject;
  }
  
  public ArrayRow createRow() {
    ArrayRow arrayRow;
    if (OPTIMIZED_ENGINE) {
      arrayRow = this.mCache.optimizedArrayRowPool.acquire();
      if (arrayRow == null) {
        arrayRow = new ValuesRow(this.mCache);
        OPTIMIZED_ARRAY_ROW_CREATION++;
      } else {
        arrayRow.reset();
      } 
    } else {
      arrayRow = this.mCache.arrayRowPool.acquire();
      if (arrayRow == null) {
        arrayRow = new ArrayRow(this.mCache);
        ARRAY_ROW_CREATION++;
      } else {
        arrayRow.reset();
      } 
    } 
    SolverVariable.increaseErrorId();
    return arrayRow;
  }
  
  public SolverVariable createSlackVariable() {
    Metrics metrics = sMetrics;
    if (metrics != null)
      metrics.slackvariables++; 
    if (this.mNumColumns + 1 >= this.mMaxColumns)
      increaseTableSize(); 
    SolverVariable solverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
    int i = this.mVariablesID + 1;
    this.mVariablesID = i;
    this.mNumColumns++;
    solverVariable.id = i;
    this.mCache.mIndexedVariables[this.mVariablesID] = solverVariable;
    return solverVariable;
  }
  
  public void displayReadableRows() {
    displaySolverVariables();
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(" num vars ");
    stringBuilder1.append(this.mVariablesID);
    stringBuilder1.append("\n");
    String str1 = stringBuilder1.toString();
    boolean bool = false;
    byte b = 0;
    while (b < this.mVariablesID + 1) {
      SolverVariable solverVariable = this.mCache.mIndexedVariables[b];
      String str = str1;
      if (solverVariable != null) {
        str = str1;
        if (solverVariable.isFinalValue) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str1);
          stringBuilder.append(" $[");
          stringBuilder.append(b);
          stringBuilder.append("] => ");
          stringBuilder.append(solverVariable);
          stringBuilder.append(" = ");
          stringBuilder.append(solverVariable.computedValue);
          stringBuilder.append("\n");
          str = stringBuilder.toString();
        } 
      } 
      b++;
      str1 = str;
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str1);
    stringBuilder2.append("\n");
    str1 = stringBuilder2.toString();
    b = 0;
    while (b < this.mVariablesID + 1) {
      SolverVariable solverVariable = this.mCache.mIndexedVariables[b];
      String str = str1;
      if (solverVariable != null) {
        str = str1;
        if (solverVariable.isSynonym) {
          SolverVariable solverVariable1 = this.mCache.mIndexedVariables[solverVariable.synonym];
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str1);
          stringBuilder.append(" ~[");
          stringBuilder.append(b);
          stringBuilder.append("] => ");
          stringBuilder.append(solverVariable);
          stringBuilder.append(" = ");
          stringBuilder.append(solverVariable1);
          stringBuilder.append(" + ");
          stringBuilder.append(solverVariable.synonymDelta);
          stringBuilder.append("\n");
          str = stringBuilder.toString();
        } 
      } 
      b++;
      str1 = str;
    } 
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str1);
    stringBuilder2.append("\n\n #  ");
    str1 = stringBuilder2.toString();
    for (b = bool; b < this.mNumRows; b++) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str1);
      stringBuilder2.append(this.mRows[b].toReadableString());
      str1 = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str1);
      stringBuilder2.append("\n #  ");
      str1 = stringBuilder2.toString();
    } 
    String str2 = str1;
    if (this.mGoal != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append("Goal: ");
      stringBuilder.append(this.mGoal);
      stringBuilder.append("\n");
      str2 = stringBuilder.toString();
    } 
    System.out.println(str2);
  }
  
  void displaySystemInformations() {
    int k = 0;
    int i;
    for (i = 0; k < this.TABLE_SIZE; i = m) {
      ArrayRow[] arrayOfArrayRow = this.mRows;
      int m = i;
      if (arrayOfArrayRow[k] != null)
        m = i + arrayOfArrayRow[k].sizeInBytes(); 
      k++;
    } 
    k = 0;
    int j;
    for (j = 0; k < this.mNumRows; j = m) {
      ArrayRow[] arrayOfArrayRow = this.mRows;
      int m = j;
      if (arrayOfArrayRow[k] != null)
        m = j + arrayOfArrayRow[k].sizeInBytes(); 
      k++;
    } 
    PrintStream printStream = System.out;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Linear System -> Table size: ");
    stringBuilder.append(this.TABLE_SIZE);
    stringBuilder.append(" (");
    k = this.TABLE_SIZE;
    stringBuilder.append(getDisplaySize(k * k));
    stringBuilder.append(") -- row sizes: ");
    stringBuilder.append(getDisplaySize(i));
    stringBuilder.append(", actual size: ");
    stringBuilder.append(getDisplaySize(j));
    stringBuilder.append(" rows: ");
    stringBuilder.append(this.mNumRows);
    stringBuilder.append("/");
    stringBuilder.append(this.mMaxRows);
    stringBuilder.append(" cols: ");
    stringBuilder.append(this.mNumColumns);
    stringBuilder.append("/");
    stringBuilder.append(this.mMaxColumns);
    stringBuilder.append(" ");
    stringBuilder.append(0);
    stringBuilder.append(" occupied cells, ");
    stringBuilder.append(getDisplaySize(0));
    printStream.println(stringBuilder.toString());
  }
  
  public void displayVariablesReadableRows() {
    displaySolverVariables();
    String str = "";
    byte b = 0;
    while (b < this.mNumRows) {
      String str1 = str;
      if ((this.mRows[b]).variable.mType == SolverVariable.Type.UNRESTRICTED) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append(this.mRows[b].toReadableString());
        str = stringBuilder1.toString();
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append("\n");
        str1 = stringBuilder1.toString();
      } 
      b++;
      str = str1;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(this.mGoal);
    stringBuilder.append("\n");
    str = stringBuilder.toString();
    System.out.println(str);
  }
  
  public void fillMetrics(Metrics paramMetrics) {
    sMetrics = paramMetrics;
  }
  
  public Cache getCache() {
    return this.mCache;
  }
  
  Row getGoal() {
    return this.mGoal;
  }
  
  public int getMemoryUsed() {
    byte b = 0;
    int i;
    for (i = 0; b < this.mNumRows; i = j) {
      ArrayRow[] arrayOfArrayRow = this.mRows;
      int j = i;
      if (arrayOfArrayRow[b] != null)
        j = i + arrayOfArrayRow[b].sizeInBytes(); 
      b++;
    } 
    return i;
  }
  
  public int getNumEquations() {
    return this.mNumRows;
  }
  
  public int getNumVariables() {
    return this.mVariablesID;
  }
  
  public int getObjectVariableValue(Object paramObject) {
    paramObject = ((ConstraintAnchor)paramObject).getSolverVariable();
    return (paramObject != null) ? (int)(((SolverVariable)paramObject).computedValue + 0.5F) : 0;
  }
  
  ArrayRow getRow(int paramInt) {
    return this.mRows[paramInt];
  }
  
  float getValueFor(String paramString) {
    SolverVariable solverVariable = getVariable(paramString, SolverVariable.Type.UNRESTRICTED);
    return (solverVariable == null) ? 0.0F : solverVariable.computedValue;
  }
  
  SolverVariable getVariable(String paramString, SolverVariable.Type paramType) {
    if (this.mVariables == null)
      this.mVariables = new HashMap<String, SolverVariable>(); 
    SolverVariable solverVariable2 = this.mVariables.get(paramString);
    SolverVariable solverVariable1 = solverVariable2;
    if (solverVariable2 == null)
      solverVariable1 = createVariable(paramString, paramType); 
    return solverVariable1;
  }
  
  public void minimize() throws Exception {
    Metrics metrics = sMetrics;
    if (metrics != null)
      metrics.minimize++; 
    if (this.mGoal.isEmpty()) {
      computeValues();
      return;
    } 
    if (this.graphOptimizer || this.newgraphOptimizer) {
      metrics = sMetrics;
      if (metrics != null)
        metrics.graphOptimizer++; 
      boolean bool = false;
      byte b = 0;
      while (true) {
        if (b < this.mNumRows) {
          if (!(this.mRows[b]).isSimpleDefinition) {
            b = bool;
            break;
          } 
          b++;
          continue;
        } 
        b = 1;
        break;
      } 
      if (b == 0) {
        minimizeGoal(this.mGoal);
      } else {
        metrics = sMetrics;
        if (metrics != null)
          metrics.fullySolved++; 
        computeValues();
      } 
      return;
    } 
    minimizeGoal(this.mGoal);
  }
  
  void minimizeGoal(Row paramRow) throws Exception {
    Metrics metrics = sMetrics;
    if (metrics != null) {
      metrics.minimizeGoal++;
      metrics = sMetrics;
      metrics.maxVariables = Math.max(metrics.maxVariables, this.mNumColumns);
      metrics = sMetrics;
      metrics.maxRows = Math.max(metrics.maxRows, this.mNumRows);
    } 
    enforceBFS(paramRow);
    optimize(paramRow, false);
    computeValues();
  }
  
  public void removeRow(ArrayRow paramArrayRow) {
    if (paramArrayRow.isSimpleDefinition && paramArrayRow.variable != null) {
      if (paramArrayRow.variable.definitionId != -1) {
        int i = paramArrayRow.variable.definitionId;
        while (true) {
          int j = this.mNumRows;
          if (i < j - 1) {
            ArrayRow[] arrayOfArrayRow2 = this.mRows;
            j = i + 1;
            SolverVariable solverVariable = (arrayOfArrayRow2[j]).variable;
            if (solverVariable.definitionId == j)
              solverVariable.definitionId = i; 
            ArrayRow[] arrayOfArrayRow1 = this.mRows;
            arrayOfArrayRow1[i] = arrayOfArrayRow1[j];
            i = j;
            continue;
          } 
          this.mNumRows = j - 1;
          break;
        } 
      } 
      if (!paramArrayRow.variable.isFinalValue)
        paramArrayRow.variable.setFinalValue(this, paramArrayRow.constantValue); 
      if (OPTIMIZED_ENGINE) {
        this.mCache.optimizedArrayRowPool.release(paramArrayRow);
      } else {
        this.mCache.arrayRowPool.release(paramArrayRow);
      } 
    } 
  }
  
  public void reset() {
    byte b;
    for (b = 0; b < this.mCache.mIndexedVariables.length; b++) {
      SolverVariable solverVariable = this.mCache.mIndexedVariables[b];
      if (solverVariable != null)
        solverVariable.reset(); 
    } 
    this.mCache.solverVariablePool.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
    this.mPoolVariablesCount = 0;
    Arrays.fill((Object[])this.mCache.mIndexedVariables, (Object)null);
    HashMap<String, SolverVariable> hashMap = this.mVariables;
    if (hashMap != null)
      hashMap.clear(); 
    this.mVariablesID = 0;
    this.mGoal.clear();
    this.mNumColumns = 1;
    for (b = 0; b < this.mNumRows; b++) {
      ArrayRow[] arrayOfArrayRow = this.mRows;
      if (arrayOfArrayRow[b] != null)
        (arrayOfArrayRow[b]).used = false; 
    } 
    releaseRows();
    this.mNumRows = 0;
    if (OPTIMIZED_ENGINE) {
      this.mTempGoal = new ValuesRow(this.mCache);
    } else {
      this.mTempGoal = new ArrayRow(this.mCache);
    } 
  }
  
  static interface Row {
    void addError(SolverVariable param1SolverVariable);
    
    void clear();
    
    SolverVariable getKey();
    
    SolverVariable getPivotCandidate(LinearSystem param1LinearSystem, boolean[] param1ArrayOfboolean);
    
    void initFromRow(Row param1Row);
    
    boolean isEmpty();
    
    void updateFromFinalVariable(LinearSystem param1LinearSystem, SolverVariable param1SolverVariable, boolean param1Boolean);
    
    void updateFromRow(LinearSystem param1LinearSystem, ArrayRow param1ArrayRow, boolean param1Boolean);
    
    void updateFromSystem(LinearSystem param1LinearSystem);
  }
  
  class ValuesRow extends ArrayRow {
    final LinearSystem this$0;
    
    public ValuesRow(Cache param1Cache) {
      this.variables = new SolverVariableValues(this, param1Cache);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\LinearSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */