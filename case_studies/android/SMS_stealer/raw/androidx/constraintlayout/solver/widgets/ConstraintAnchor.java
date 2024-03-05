package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.analyzer.Grouping;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintAnchor {
  private static final boolean ALLOW_BINARY = false;
  
  private static final int UNSET_GONE_MARGIN = -1;
  
  private HashSet<ConstraintAnchor> mDependents = null;
  
  private int mFinalValue;
  
  int mGoneMargin = -1;
  
  private boolean mHasFinalValue;
  
  public int mMargin = 0;
  
  public final ConstraintWidget mOwner;
  
  SolverVariable mSolverVariable;
  
  public ConstraintAnchor mTarget;
  
  public final Type mType;
  
  public ConstraintAnchor(ConstraintWidget paramConstraintWidget, Type paramType) {
    this.mOwner = paramConstraintWidget;
    this.mType = paramType;
  }
  
  private boolean isConnectionToMe(ConstraintWidget paramConstraintWidget, HashSet<ConstraintWidget> paramHashSet) {
    if (paramHashSet.contains(paramConstraintWidget))
      return false; 
    paramHashSet.add(paramConstraintWidget);
    if (paramConstraintWidget == getOwner())
      return true; 
    ArrayList<ConstraintAnchor> arrayList = paramConstraintWidget.getAnchors();
    int i = arrayList.size();
    for (byte b = 0; b < i; b++) {
      ConstraintAnchor constraintAnchor = arrayList.get(b);
      if (constraintAnchor.isSimilarDimensionConnection(this) && constraintAnchor.isConnected() && isConnectionToMe(constraintAnchor.getTarget().getOwner(), paramHashSet))
        return true; 
    } 
    return false;
  }
  
  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt) {
    return connect(paramConstraintAnchor, paramInt, -1, false);
  }
  
  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt1, int paramInt2, boolean paramBoolean) {
    if (paramConstraintAnchor == null) {
      reset();
      return true;
    } 
    if (!paramBoolean && !isValidConnection(paramConstraintAnchor))
      return false; 
    this.mTarget = paramConstraintAnchor;
    if (paramConstraintAnchor.mDependents == null)
      paramConstraintAnchor.mDependents = new HashSet<ConstraintAnchor>(); 
    HashSet<ConstraintAnchor> hashSet = this.mTarget.mDependents;
    if (hashSet != null)
      hashSet.add(this); 
    if (paramInt1 > 0) {
      this.mMargin = paramInt1;
    } else {
      this.mMargin = 0;
    } 
    this.mGoneMargin = paramInt2;
    return true;
  }
  
  public void copyFrom(ConstraintAnchor paramConstraintAnchor, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap) {
    ConstraintAnchor constraintAnchor2 = this.mTarget;
    if (constraintAnchor2 != null) {
      HashSet<ConstraintAnchor> hashSet = constraintAnchor2.mDependents;
      if (hashSet != null)
        hashSet.remove(this); 
    } 
    constraintAnchor2 = paramConstraintAnchor.mTarget;
    if (constraintAnchor2 != null) {
      Type type = constraintAnchor2.getType();
      this.mTarget = ((ConstraintWidget)paramHashMap.get(paramConstraintAnchor.mTarget.mOwner)).getAnchor(type);
    } else {
      this.mTarget = null;
    } 
    ConstraintAnchor constraintAnchor1 = this.mTarget;
    if (constraintAnchor1 != null) {
      if (constraintAnchor1.mDependents == null)
        constraintAnchor1.mDependents = new HashSet<ConstraintAnchor>(); 
      this.mTarget.mDependents.add(this);
    } 
    this.mMargin = paramConstraintAnchor.mMargin;
    this.mGoneMargin = paramConstraintAnchor.mGoneMargin;
  }
  
  public void findDependents(int paramInt, ArrayList<WidgetGroup> paramArrayList, WidgetGroup paramWidgetGroup) {
    HashSet<ConstraintAnchor> hashSet = this.mDependents;
    if (hashSet != null) {
      Iterator<ConstraintAnchor> iterator = hashSet.iterator();
      while (iterator.hasNext())
        Grouping.findDependents(((ConstraintAnchor)iterator.next()).mOwner, paramInt, paramArrayList, paramWidgetGroup); 
    } 
  }
  
  public HashSet<ConstraintAnchor> getDependents() {
    return this.mDependents;
  }
  
  public int getFinalValue() {
    return !this.mHasFinalValue ? 0 : this.mFinalValue;
  }
  
  public int getMargin() {
    if (this.mOwner.getVisibility() == 8)
      return 0; 
    if (this.mGoneMargin > -1) {
      ConstraintAnchor constraintAnchor = this.mTarget;
      if (constraintAnchor != null && constraintAnchor.mOwner.getVisibility() == 8)
        return this.mGoneMargin; 
    } 
    return this.mMargin;
  }
  
  public final ConstraintAnchor getOpposite() {
    switch (this.mType) {
      default:
        throw new AssertionError(this.mType.name());
      case null:
        return this.mOwner.mTop;
      case null:
        return this.mOwner.mBottom;
      case null:
        return this.mOwner.mLeft;
      case null:
        return this.mOwner.mRight;
      case null:
      case null:
      case null:
      case null:
      case null:
        break;
    } 
    return null;
  }
  
  public ConstraintWidget getOwner() {
    return this.mOwner;
  }
  
  public SolverVariable getSolverVariable() {
    return this.mSolverVariable;
  }
  
  public ConstraintAnchor getTarget() {
    return this.mTarget;
  }
  
  public Type getType() {
    return this.mType;
  }
  
  public boolean hasCenteredDependents() {
    HashSet<ConstraintAnchor> hashSet = this.mDependents;
    if (hashSet == null)
      return false; 
    Iterator<ConstraintAnchor> iterator = hashSet.iterator();
    while (iterator.hasNext()) {
      if (((ConstraintAnchor)iterator.next()).getOpposite().isConnected())
        return true; 
    } 
    return false;
  }
  
  public boolean hasDependents() {
    HashSet<ConstraintAnchor> hashSet = this.mDependents;
    boolean bool = false;
    if (hashSet == null)
      return false; 
    if (hashSet.size() > 0)
      bool = true; 
    return bool;
  }
  
  public boolean hasFinalValue() {
    return this.mHasFinalValue;
  }
  
  public boolean isConnected() {
    boolean bool;
    if (this.mTarget != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isConnectionAllowed(ConstraintWidget paramConstraintWidget) {
    if (isConnectionToMe(paramConstraintWidget, new HashSet<ConstraintWidget>()))
      return false; 
    ConstraintWidget constraintWidget = getOwner().getParent();
    return (constraintWidget == paramConstraintWidget) ? true : ((paramConstraintWidget.getParent() == constraintWidget));
  }
  
  public boolean isConnectionAllowed(ConstraintWidget paramConstraintWidget, ConstraintAnchor paramConstraintAnchor) {
    return isConnectionAllowed(paramConstraintWidget);
  }
  
  public boolean isSideAnchor() {
    switch (this.mType) {
      default:
        throw new AssertionError(this.mType.name());
      case null:
      case null:
      case null:
      case null:
        return true;
      case null:
      case null:
      case null:
      case null:
      case null:
        break;
    } 
    return false;
  }
  
  public boolean isSimilarDimensionConnection(ConstraintAnchor paramConstraintAnchor) {
    Type type1 = paramConstraintAnchor.getType();
    Type type2 = this.mType;
    boolean bool2 = true;
    boolean bool1 = true;
    boolean bool3 = true;
    if (type1 == type2)
      return true; 
    switch (this.mType) {
      default:
        throw new AssertionError(this.mType.name());
      case null:
        return false;
      case null:
      case null:
      case null:
      case null:
        bool1 = bool3;
        if (type1 != Type.TOP) {
          bool1 = bool3;
          if (type1 != Type.BOTTOM) {
            bool1 = bool3;
            if (type1 != Type.CENTER_Y)
              if (type1 == Type.BASELINE) {
                bool1 = bool3;
              } else {
                bool1 = false;
              }  
          } 
        } 
        return bool1;
      case null:
      case null:
      case null:
        bool1 = bool2;
        if (type1 != Type.LEFT) {
          bool1 = bool2;
          if (type1 != Type.RIGHT)
            if (type1 == Type.CENTER_X) {
              bool1 = bool2;
            } else {
              bool1 = false;
            }  
        } 
        return bool1;
      case null:
        break;
    } 
    if (type1 == Type.BASELINE)
      bool1 = false; 
    return bool1;
  }
  
  public boolean isValidConnection(ConstraintAnchor paramConstraintAnchor) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #5
    //   3: iconst_0
    //   4: istore_3
    //   5: iconst_0
    //   6: istore #4
    //   8: aload_1
    //   9: ifnonnull -> 14
    //   12: iconst_0
    //   13: ireturn
    //   14: aload_1
    //   15: invokevirtual getType : ()Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   18: astore #7
    //   20: aload_0
    //   21: getfield mType : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   24: astore #6
    //   26: aload #7
    //   28: aload #6
    //   30: if_acmpne -> 65
    //   33: aload #6
    //   35: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BASELINE : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   38: if_acmpne -> 63
    //   41: aload_1
    //   42: invokevirtual getOwner : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   45: invokevirtual hasBaseline : ()Z
    //   48: ifeq -> 61
    //   51: aload_0
    //   52: invokevirtual getOwner : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   55: invokevirtual hasBaseline : ()Z
    //   58: ifne -> 63
    //   61: iconst_0
    //   62: ireturn
    //   63: iconst_1
    //   64: ireturn
    //   65: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type : [I
    //   68: aload_0
    //   69: getfield mType : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   72: invokevirtual ordinal : ()I
    //   75: iaload
    //   76: tableswitch default -> 128, 1 -> 263, 2 -> 204, 3 -> 204, 4 -> 145, 5 -> 145, 6 -> 143, 7 -> 143, 8 -> 143, 9 -> 143
    //   128: new java/lang/AssertionError
    //   131: dup
    //   132: aload_0
    //   133: getfield mType : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   136: invokevirtual name : ()Ljava/lang/String;
    //   139: invokespecial <init> : (Ljava/lang/Object;)V
    //   142: athrow
    //   143: iconst_0
    //   144: ireturn
    //   145: aload #7
    //   147: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.TOP : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   150: if_acmpeq -> 169
    //   153: aload #7
    //   155: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BOTTOM : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   158: if_acmpne -> 164
    //   161: goto -> 169
    //   164: iconst_0
    //   165: istore_2
    //   166: goto -> 171
    //   169: iconst_1
    //   170: istore_2
    //   171: iload_2
    //   172: istore_3
    //   173: aload_1
    //   174: invokevirtual getOwner : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   177: instanceof androidx/constraintlayout/solver/widgets/Guideline
    //   180: ifeq -> 202
    //   183: iload_2
    //   184: ifne -> 198
    //   187: iload #4
    //   189: istore_2
    //   190: aload #7
    //   192: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   195: if_acmpne -> 200
    //   198: iconst_1
    //   199: istore_2
    //   200: iload_2
    //   201: istore_3
    //   202: iload_3
    //   203: ireturn
    //   204: aload #7
    //   206: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.LEFT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   209: if_acmpeq -> 228
    //   212: aload #7
    //   214: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.RIGHT : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   217: if_acmpne -> 223
    //   220: goto -> 228
    //   223: iconst_0
    //   224: istore_2
    //   225: goto -> 230
    //   228: iconst_1
    //   229: istore_2
    //   230: iload_2
    //   231: istore_3
    //   232: aload_1
    //   233: invokevirtual getOwner : ()Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   236: instanceof androidx/constraintlayout/solver/widgets/Guideline
    //   239: ifeq -> 261
    //   242: iload_2
    //   243: ifne -> 257
    //   246: iload #5
    //   248: istore_2
    //   249: aload #7
    //   251: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   254: if_acmpne -> 259
    //   257: iconst_1
    //   258: istore_2
    //   259: iload_2
    //   260: istore_3
    //   261: iload_3
    //   262: ireturn
    //   263: iload_3
    //   264: istore_2
    //   265: aload #7
    //   267: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.BASELINE : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   270: if_acmpeq -> 295
    //   273: iload_3
    //   274: istore_2
    //   275: aload #7
    //   277: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_X : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   280: if_acmpeq -> 295
    //   283: iload_3
    //   284: istore_2
    //   285: aload #7
    //   287: getstatic androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type.CENTER_Y : Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   290: if_acmpeq -> 295
    //   293: iconst_1
    //   294: istore_2
    //   295: iload_2
    //   296: ireturn
  }
  
  public boolean isVerticalAnchor() {
    switch (this.mType) {
      default:
        throw new AssertionError(this.mType.name());
      case null:
      case null:
      case null:
      case null:
      case null:
        return true;
      case null:
      case null:
      case null:
      case null:
        break;
    } 
    return false;
  }
  
  public void reset() {
    ConstraintAnchor constraintAnchor = this.mTarget;
    if (constraintAnchor != null) {
      HashSet<ConstraintAnchor> hashSet = constraintAnchor.mDependents;
      if (hashSet != null) {
        hashSet.remove(this);
        if (this.mTarget.mDependents.size() == 0)
          this.mTarget.mDependents = null; 
      } 
    } 
    this.mDependents = null;
    this.mTarget = null;
    this.mMargin = 0;
    this.mGoneMargin = -1;
    this.mHasFinalValue = false;
    this.mFinalValue = 0;
  }
  
  public void resetFinalResolution() {
    this.mHasFinalValue = false;
    this.mFinalValue = 0;
  }
  
  public void resetSolverVariable(Cache paramCache) {
    SolverVariable solverVariable = this.mSolverVariable;
    if (solverVariable == null) {
      this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED, null);
    } else {
      solverVariable.reset();
    } 
  }
  
  public void setFinalValue(int paramInt) {
    this.mFinalValue = paramInt;
    this.mHasFinalValue = true;
  }
  
  public void setGoneMargin(int paramInt) {
    if (isConnected())
      this.mGoneMargin = paramInt; 
  }
  
  public void setMargin(int paramInt) {
    if (isConnected())
      this.mMargin = paramInt; 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mOwner.getDebugName());
    stringBuilder.append(":");
    stringBuilder.append(this.mType.toString());
    return stringBuilder.toString();
  }
  
  public enum Type {
    BASELINE, BOTTOM, CENTER, CENTER_X, CENTER_Y, LEFT, NONE, RIGHT, TOP;
    
    private static final Type[] $VALUES;
    
    static {
      RIGHT = new Type("RIGHT", 3);
      BOTTOM = new Type("BOTTOM", 4);
      BASELINE = new Type("BASELINE", 5);
      CENTER = new Type("CENTER", 6);
      CENTER_X = new Type("CENTER_X", 7);
      Type type = new Type("CENTER_Y", 8);
      CENTER_Y = type;
      $VALUES = new Type[] { NONE, LEFT, TOP, RIGHT, BOTTOM, BASELINE, CENTER, CENTER_X, type };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\ConstraintAnchor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */