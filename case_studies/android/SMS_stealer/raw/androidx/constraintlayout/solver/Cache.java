package androidx.constraintlayout.solver;

public class Cache {
  Pools.Pool<ArrayRow> arrayRowPool = new Pools.SimplePool<ArrayRow>(256);
  
  SolverVariable[] mIndexedVariables = new SolverVariable[32];
  
  Pools.Pool<ArrayRow> optimizedArrayRowPool = new Pools.SimplePool<ArrayRow>(256);
  
  Pools.Pool<SolverVariable> solverVariablePool = new Pools.SimplePool<SolverVariable>(256);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */