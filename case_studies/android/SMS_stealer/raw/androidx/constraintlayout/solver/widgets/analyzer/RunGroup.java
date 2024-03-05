package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

class RunGroup {
  public static final int BASELINE = 2;
  
  public static final int END = 1;
  
  public static final int START = 0;
  
  public static int index;
  
  int direction;
  
  public boolean dual = false;
  
  WidgetRun firstRun = null;
  
  int groupIndex = 0;
  
  WidgetRun lastRun = null;
  
  public int position = 0;
  
  ArrayList<WidgetRun> runs = new ArrayList<WidgetRun>();
  
  public RunGroup(WidgetRun paramWidgetRun, int paramInt) {
    int i = index;
    this.groupIndex = i;
    index = i + 1;
    this.firstRun = paramWidgetRun;
    this.lastRun = paramWidgetRun;
    this.direction = paramInt;
  }
  
  private boolean defineTerminalWidget(WidgetRun paramWidgetRun, int paramInt) {
    if (!paramWidgetRun.widget.isTerminalWidget[paramInt])
      return false; 
    for (Dependency dependency : paramWidgetRun.start.dependencies) {
      if (dependency instanceof DependencyNode) {
        dependency = dependency;
        if (((DependencyNode)dependency).run != paramWidgetRun && dependency == ((DependencyNode)dependency).run.start) {
          if (paramWidgetRun instanceof ChainRun) {
            Iterator<WidgetRun> iterator = ((ChainRun)paramWidgetRun).widgets.iterator();
            while (iterator.hasNext())
              defineTerminalWidget(iterator.next(), paramInt); 
          } else if (!(paramWidgetRun instanceof HelperReferences)) {
            paramWidgetRun.widget.isTerminalWidget[paramInt] = false;
          } 
          defineTerminalWidget(((DependencyNode)dependency).run, paramInt);
        } 
      } 
    } 
    for (Dependency dependency : paramWidgetRun.end.dependencies) {
      if (dependency instanceof DependencyNode) {
        dependency = dependency;
        if (((DependencyNode)dependency).run != paramWidgetRun && dependency == ((DependencyNode)dependency).run.start) {
          if (paramWidgetRun instanceof ChainRun) {
            Iterator<WidgetRun> iterator = ((ChainRun)paramWidgetRun).widgets.iterator();
            while (iterator.hasNext())
              defineTerminalWidget(iterator.next(), paramInt); 
          } else if (!(paramWidgetRun instanceof HelperReferences)) {
            paramWidgetRun.widget.isTerminalWidget[paramInt] = false;
          } 
          defineTerminalWidget(((DependencyNode)dependency).run, paramInt);
        } 
      } 
    } 
    return false;
  }
  
  private long traverseEnd(DependencyNode paramDependencyNode, long paramLong) {
    WidgetRun widgetRun = paramDependencyNode.run;
    if (widgetRun instanceof HelperReferences)
      return paramLong; 
    int i = paramDependencyNode.dependencies.size();
    byte b = 0;
    long l1;
    for (l1 = paramLong; b < i; l1 = l) {
      Dependency dependency = paramDependencyNode.dependencies.get(b);
      long l = l1;
      if (dependency instanceof DependencyNode) {
        dependency = dependency;
        if (((DependencyNode)dependency).run == widgetRun) {
          l = l1;
        } else {
          l = Math.min(l1, traverseEnd((DependencyNode)dependency, ((DependencyNode)dependency).margin + paramLong));
        } 
      } 
      b++;
    } 
    long l2 = l1;
    if (paramDependencyNode == widgetRun.end) {
      l2 = widgetRun.getWrapDimension();
      paramDependencyNode = widgetRun.start;
      paramLong -= l2;
      l2 = Math.min(Math.min(l1, traverseEnd(paramDependencyNode, paramLong)), paramLong - widgetRun.start.margin);
    } 
    return l2;
  }
  
  private long traverseStart(DependencyNode paramDependencyNode, long paramLong) {
    WidgetRun widgetRun = paramDependencyNode.run;
    if (widgetRun instanceof HelperReferences)
      return paramLong; 
    int i = paramDependencyNode.dependencies.size();
    byte b = 0;
    long l1;
    for (l1 = paramLong; b < i; l1 = l) {
      Dependency dependency = paramDependencyNode.dependencies.get(b);
      long l = l1;
      if (dependency instanceof DependencyNode) {
        dependency = dependency;
        if (((DependencyNode)dependency).run == widgetRun) {
          l = l1;
        } else {
          l = Math.max(l1, traverseStart((DependencyNode)dependency, ((DependencyNode)dependency).margin + paramLong));
        } 
      } 
      b++;
    } 
    long l2 = l1;
    if (paramDependencyNode == widgetRun.start) {
      l2 = widgetRun.getWrapDimension();
      paramDependencyNode = widgetRun.end;
      paramLong += l2;
      l2 = Math.max(Math.max(l1, traverseStart(paramDependencyNode, paramLong)), paramLong - widgetRun.end.margin);
    } 
    return l2;
  }
  
  public void add(WidgetRun paramWidgetRun) {
    this.runs.add(paramWidgetRun);
    this.lastRun = paramWidgetRun;
  }
  
  public long computeWrapSize(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt) {
    DependencyNode dependencyNode1;
    long l1;
    DependencyNode dependencyNode2;
    WidgetRun widgetRun = this.firstRun;
    boolean bool1 = widgetRun instanceof ChainRun;
    long l2 = 0L;
    if (bool1) {
      if (((ChainRun)widgetRun).orientation != paramInt)
        return 0L; 
    } else if (paramInt == 0) {
      if (!(widgetRun instanceof HorizontalWidgetRun))
        return 0L; 
    } else if (!(widgetRun instanceof VerticalWidgetRun)) {
      return 0L;
    } 
    if (paramInt == 0) {
      dependencyNode2 = paramConstraintWidgetContainer.horizontalRun.start;
    } else {
      dependencyNode2 = paramConstraintWidgetContainer.verticalRun.start;
    } 
    if (paramInt == 0) {
      dependencyNode1 = paramConstraintWidgetContainer.horizontalRun.end;
    } else {
      dependencyNode1 = ((ConstraintWidgetContainer)dependencyNode1).verticalRun.end;
    } 
    boolean bool2 = this.firstRun.start.targets.contains(dependencyNode2);
    bool1 = this.firstRun.end.targets.contains(dependencyNode1);
    long l3 = this.firstRun.getWrapDimension();
    if (bool2 && bool1) {
      l1 = traverseStart(this.firstRun.start, 0L);
      long l5 = traverseEnd(this.firstRun.end, 0L);
      long l4 = l1 - l3;
      l1 = l4;
      if (l4 >= -this.firstRun.end.margin)
        l1 = l4 + this.firstRun.end.margin; 
      l5 = -l5 - l3 - this.firstRun.start.margin;
      l4 = l5;
      if (l5 >= this.firstRun.start.margin)
        l4 = l5 - this.firstRun.start.margin; 
      float f2 = this.firstRun.widget.getBiasPercent(paramInt);
      if (f2 > 0.0F)
        l2 = (long)((float)l4 / f2 + (float)l1 / (1.0F - f2)); 
      float f1 = (float)l2;
      l1 = (long)(f1 * f2 + 0.5F);
      l4 = (long)(f1 * (1.0F - f2) + 0.5F);
      l1 = this.firstRun.start.margin + l1 + l3 + l4;
      paramInt = this.firstRun.end.margin;
    } else {
      if (bool2) {
        l1 = Math.max(traverseStart(this.firstRun.start, this.firstRun.start.margin), this.firstRun.start.margin + l3);
      } else if (bool1) {
        long l = traverseEnd(this.firstRun.end, this.firstRun.end.margin);
        l1 = -this.firstRun.end.margin;
        l1 = Math.max(-l, l1 + l3);
      } else {
        l1 = this.firstRun.start.margin + this.firstRun.getWrapDimension();
        paramInt = this.firstRun.end.margin;
        l1 -= paramInt;
      } 
      return l1;
    } 
    l1 -= paramInt;
  }
  
  public void defineTerminalWidgets(boolean paramBoolean1, boolean paramBoolean2) {
    if (paramBoolean1) {
      WidgetRun widgetRun = this.firstRun;
      if (widgetRun instanceof HorizontalWidgetRun)
        defineTerminalWidget(widgetRun, 0); 
    } 
    if (paramBoolean2) {
      WidgetRun widgetRun = this.firstRun;
      if (widgetRun instanceof VerticalWidgetRun)
        defineTerminalWidget(widgetRun, 1); 
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\RunGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */