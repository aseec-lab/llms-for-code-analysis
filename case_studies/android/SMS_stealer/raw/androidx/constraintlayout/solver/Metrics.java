package androidx.constraintlayout.solver;

import java.util.ArrayList;

public class Metrics {
  public long additionalMeasures;
  
  public long barrierConnectionResolved;
  
  public long bfs;
  
  public long centerConnectionResolved;
  
  public long chainConnectionResolved;
  
  public long constraints;
  
  public long determineGroups;
  
  public long errors;
  
  public long extravariables;
  
  public long fullySolved;
  
  public long graphOptimizer;
  
  public long graphSolved;
  
  public long grouping;
  
  public long infeasibleDetermineGroups;
  
  public long iterations;
  
  public long lastTableSize;
  
  public long layouts;
  
  public long linearSolved;
  
  public long matchConnectionResolved;
  
  public long maxRows;
  
  public long maxTableSize;
  
  public long maxVariables;
  
  public long measuredMatchWidgets;
  
  public long measuredWidgets;
  
  public long measures;
  
  public long measuresLayoutDuration;
  
  public long measuresWidgetsDuration;
  
  public long measuresWrap;
  
  public long measuresWrapInfeasible;
  
  public long minimize;
  
  public long minimizeGoal;
  
  public long nonresolvedWidgets;
  
  public long oldresolvedWidgets;
  
  public long optimize;
  
  public long pivots;
  
  public ArrayList<String> problematicLayouts = new ArrayList<String>();
  
  public long resolutions;
  
  public long resolvedWidgets;
  
  public long simpleconstraints;
  
  public long slackvariables;
  
  public long tableSizeIncrease;
  
  public long variables;
  
  public long widgets;
  
  public void reset() {
    this.measures = 0L;
    this.widgets = 0L;
    this.additionalMeasures = 0L;
    this.resolutions = 0L;
    this.tableSizeIncrease = 0L;
    this.maxTableSize = 0L;
    this.lastTableSize = 0L;
    this.maxVariables = 0L;
    this.maxRows = 0L;
    this.minimize = 0L;
    this.minimizeGoal = 0L;
    this.constraints = 0L;
    this.simpleconstraints = 0L;
    this.optimize = 0L;
    this.iterations = 0L;
    this.pivots = 0L;
    this.bfs = 0L;
    this.variables = 0L;
    this.errors = 0L;
    this.slackvariables = 0L;
    this.extravariables = 0L;
    this.fullySolved = 0L;
    this.graphOptimizer = 0L;
    this.graphSolved = 0L;
    this.resolvedWidgets = 0L;
    this.oldresolvedWidgets = 0L;
    this.nonresolvedWidgets = 0L;
    this.centerConnectionResolved = 0L;
    this.matchConnectionResolved = 0L;
    this.chainConnectionResolved = 0L;
    this.barrierConnectionResolved = 0L;
    this.problematicLayouts.clear();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n*** Metrics ***\nmeasures: ");
    stringBuilder.append(this.measures);
    stringBuilder.append("\nmeasuresWrap: ");
    stringBuilder.append(this.measuresWrap);
    stringBuilder.append("\nmeasuresWrapInfeasible: ");
    stringBuilder.append(this.measuresWrapInfeasible);
    stringBuilder.append("\ndetermineGroups: ");
    stringBuilder.append(this.determineGroups);
    stringBuilder.append("\ninfeasibleDetermineGroups: ");
    stringBuilder.append(this.infeasibleDetermineGroups);
    stringBuilder.append("\ngraphOptimizer: ");
    stringBuilder.append(this.graphOptimizer);
    stringBuilder.append("\nwidgets: ");
    stringBuilder.append(this.widgets);
    stringBuilder.append("\ngraphSolved: ");
    stringBuilder.append(this.graphSolved);
    stringBuilder.append("\nlinearSolved: ");
    stringBuilder.append(this.linearSolved);
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\Metrics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */