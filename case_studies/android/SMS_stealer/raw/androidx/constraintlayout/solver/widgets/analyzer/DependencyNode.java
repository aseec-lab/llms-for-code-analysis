package androidx.constraintlayout.solver.widgets.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DependencyNode implements Dependency {
  public boolean delegateToWidgetRun = false;
  
  List<Dependency> dependencies = new ArrayList<Dependency>();
  
  int margin;
  
  DimensionDependency marginDependency = null;
  
  int marginFactor = 1;
  
  public boolean readyToSolve = false;
  
  public boolean resolved = false;
  
  WidgetRun run;
  
  List<DependencyNode> targets = new ArrayList<DependencyNode>();
  
  Type type = Type.UNKNOWN;
  
  public Dependency updateDelegate = null;
  
  public int value;
  
  public DependencyNode(WidgetRun paramWidgetRun) {
    this.run = paramWidgetRun;
  }
  
  public void addDependency(Dependency paramDependency) {
    this.dependencies.add(paramDependency);
    if (this.resolved)
      paramDependency.update(paramDependency); 
  }
  
  public void clear() {
    this.targets.clear();
    this.dependencies.clear();
    this.resolved = false;
    this.value = 0;
    this.readyToSolve = false;
    this.delegateToWidgetRun = false;
  }
  
  public String name() {
    String str = this.run.widget.getDebugName();
    if (this.type == Type.LEFT || this.type == Type.RIGHT) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append("_HORIZONTAL");
      str = stringBuilder1.toString();
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(":");
      stringBuilder1.append(this.type.name());
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append("_VERTICAL");
    str = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(":");
    stringBuilder.append(this.type.name());
    return stringBuilder.toString();
  }
  
  public void resolve(int paramInt) {
    if (this.resolved)
      return; 
    this.resolved = true;
    this.value = paramInt;
    for (Dependency dependency : this.dependencies)
      dependency.update(dependency); 
  }
  
  public String toString() {
    String str;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.run.widget.getDebugName());
    stringBuilder.append(":");
    stringBuilder.append(this.type);
    stringBuilder.append("(");
    if (this.resolved) {
      Integer integer = Integer.valueOf(this.value);
    } else {
      str = "unresolved";
    } 
    stringBuilder.append(str);
    stringBuilder.append(") <t=");
    stringBuilder.append(this.targets.size());
    stringBuilder.append(":d=");
    stringBuilder.append(this.dependencies.size());
    stringBuilder.append(">");
    return stringBuilder.toString();
  }
  
  public void update(Dependency paramDependency) {
    Iterator<DependencyNode> iterator = this.targets.iterator();
    while (iterator.hasNext()) {
      if (!((DependencyNode)iterator.next()).resolved)
        return; 
    } 
    this.readyToSolve = true;
    Dependency dependency = this.updateDelegate;
    if (dependency != null)
      dependency.update(this); 
    if (this.delegateToWidgetRun) {
      this.run.update(this);
      return;
    } 
    dependency = null;
    byte b = 0;
    for (DependencyNode dependencyNode : this.targets) {
      if (dependencyNode instanceof DimensionDependency)
        continue; 
      b++;
      dependency = dependencyNode;
    } 
    if (dependency != null && b == 1 && ((DependencyNode)dependency).resolved) {
      DimensionDependency dimensionDependency = this.marginDependency;
      if (dimensionDependency != null)
        if (dimensionDependency.resolved) {
          this.margin = this.marginFactor * this.marginDependency.value;
        } else {
          return;
        }  
      resolve(((DependencyNode)dependency).value + this.margin);
    } 
    dependency = this.updateDelegate;
    if (dependency != null)
      dependency.update(this); 
  }
  
  enum Type {
    BASELINE, BOTTOM, HORIZONTAL_DIMENSION, LEFT, RIGHT, TOP, UNKNOWN, VERTICAL_DIMENSION;
    
    private static final Type[] $VALUES;
    
    static {
      LEFT = new Type("LEFT", 3);
      RIGHT = new Type("RIGHT", 4);
      TOP = new Type("TOP", 5);
      BOTTOM = new Type("BOTTOM", 6);
      Type type = new Type("BASELINE", 7);
      BASELINE = type;
      $VALUES = new Type[] { UNKNOWN, HORIZONTAL_DIMENSION, VERTICAL_DIMENSION, LEFT, RIGHT, TOP, BOTTOM, type };
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\DependencyNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */