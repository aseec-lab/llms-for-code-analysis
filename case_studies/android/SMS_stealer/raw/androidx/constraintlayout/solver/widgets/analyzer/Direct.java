package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ChainHead;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import java.util.ArrayList;
import java.util.Iterator;

public class Direct {
  private static final boolean APPLY_MATCH_PARENT = false;
  
  private static final boolean DEBUG = false;
  
  private static BasicMeasure.Measure measure = new BasicMeasure.Measure();
  
  private static boolean canMeasure(ConstraintWidget paramConstraintWidget) {
    boolean bool1;
    boolean bool2;
    ConstraintWidgetContainer constraintWidgetContainer;
    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = paramConstraintWidget.getHorizontalDimensionBehaviour();
    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = paramConstraintWidget.getVerticalDimensionBehaviour();
    if (paramConstraintWidget.getParent() != null) {
      constraintWidgetContainer = (ConstraintWidgetContainer)paramConstraintWidget.getParent();
    } else {
      constraintWidgetContainer = null;
    } 
    if (constraintWidgetContainer != null) {
      constraintWidgetContainer.getHorizontalDimensionBehaviour();
      ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
    } 
    if (constraintWidgetContainer != null) {
      constraintWidgetContainer.getVerticalDimensionBehaviour();
      ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
    } 
    ConstraintWidget.DimensionBehaviour dimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.FIXED;
    boolean bool4 = false;
    if (dimensionBehaviour2 == dimensionBehaviour1 || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && paramConstraintWidget.mMatchConstraintDefaultWidth == 0 && paramConstraintWidget.mDimensionRatio == 0.0F && paramConstraintWidget.hasDanglingDimension(0)) || paramConstraintWidget.isResolvedHorizontally()) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && paramConstraintWidget.mMatchConstraintDefaultHeight == 0 && paramConstraintWidget.mDimensionRatio == 0.0F && paramConstraintWidget.hasDanglingDimension(1)) || paramConstraintWidget.isResolvedVertically()) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (paramConstraintWidget.mDimensionRatio > 0.0F && (bool1 || bool2))
      return true; 
    boolean bool3 = bool4;
    if (bool1) {
      bool3 = bool4;
      if (bool2)
        bool3 = true; 
    } 
    return bool3;
  }
  
  private static void horizontalSolvingPass(ConstraintWidget paramConstraintWidget, BasicMeasure.Measurer paramMeasurer, boolean paramBoolean) {
    if (!(paramConstraintWidget instanceof ConstraintWidgetContainer) && paramConstraintWidget.isMeasureRequested() && canMeasure(paramConstraintWidget))
      ConstraintWidgetContainer.measure(paramConstraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS); 
    constraintAnchor2 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
    ConstraintAnchor constraintAnchor1 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
    int j = constraintAnchor2.getFinalValue();
    int i = constraintAnchor1.getFinalValue();
    if (constraintAnchor2.getDependents() != null && constraintAnchor2.hasFinalValue())
      for (ConstraintAnchor constraintAnchor : constraintAnchor2.getDependents()) {
        ConstraintWidget constraintWidget = constraintAnchor.mOwner;
        boolean bool = canMeasure(constraintWidget);
        if (constraintWidget.isMeasureRequested() && bool)
          ConstraintWidgetContainer.measure(constraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS); 
        if (constraintWidget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || bool) {
          if (constraintWidget.isMeasureRequested())
            continue; 
          if (constraintAnchor == constraintWidget.mLeft && constraintWidget.mRight.mTarget == null) {
            int k = constraintWidget.mLeft.getMargin() + j;
            constraintWidget.setFinalHorizontal(k, constraintWidget.getWidth() + k);
            horizontalSolvingPass(constraintWidget, paramMeasurer, paramBoolean);
            continue;
          } 
          if (constraintAnchor == constraintWidget.mRight && constraintWidget.mLeft.mTarget == null) {
            int k = j - constraintWidget.mRight.getMargin();
            constraintWidget.setFinalHorizontal(k - constraintWidget.getWidth(), k);
            horizontalSolvingPass(constraintWidget, paramMeasurer, paramBoolean);
            continue;
          } 
          if (constraintAnchor == constraintWidget.mLeft && constraintWidget.mRight.mTarget != null && constraintWidget.mRight.mTarget.hasFinalValue() && !constraintWidget.isInHorizontalChain())
            solveHorizontalCenterConstraints(paramMeasurer, constraintWidget, paramBoolean); 
          continue;
        } 
        if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintMaxWidth >= 0 && constraintWidget.mMatchConstraintMinWidth >= 0 && (constraintWidget.getVisibility() == 8 || (constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.getDimensionRatio() == 0.0F)) && !constraintWidget.isInHorizontalChain() && !constraintWidget.isInVirtualLayout()) {
          boolean bool1;
          if ((constraintAnchor == constraintWidget.mLeft && constraintWidget.mRight.mTarget != null && constraintWidget.mRight.mTarget.hasFinalValue()) || (constraintAnchor == constraintWidget.mRight && constraintWidget.mLeft.mTarget != null && constraintWidget.mLeft.mTarget.hasFinalValue())) {
            bool1 = true;
          } else {
            bool1 = false;
          } 
          if (bool1 && !constraintWidget.isInHorizontalChain())
            solveHorizontalMatchConstraint(paramConstraintWidget, paramMeasurer, constraintWidget, paramBoolean); 
        } 
      }  
    if (paramConstraintWidget instanceof Guideline)
      return; 
    if (constraintAnchor1.getDependents() != null && constraintAnchor1.hasFinalValue())
      for (ConstraintAnchor constraintAnchor2 : constraintAnchor1.getDependents()) {
        int k;
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        boolean bool = canMeasure(constraintWidget);
        if (constraintWidget.isMeasureRequested() && bool)
          ConstraintWidgetContainer.measure(constraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS); 
        if ((constraintAnchor2 == constraintWidget.mLeft && constraintWidget.mRight.mTarget != null && constraintWidget.mRight.mTarget.hasFinalValue()) || (constraintAnchor2 == constraintWidget.mRight && constraintWidget.mLeft.mTarget != null && constraintWidget.mLeft.mTarget.hasFinalValue())) {
          k = 1;
        } else {
          k = 0;
        } 
        if (constraintWidget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || bool) {
          if (constraintWidget.isMeasureRequested())
            continue; 
          if (constraintAnchor2 == constraintWidget.mLeft && constraintWidget.mRight.mTarget == null) {
            k = constraintWidget.mLeft.getMargin() + i;
            constraintWidget.setFinalHorizontal(k, constraintWidget.getWidth() + k);
            horizontalSolvingPass(constraintWidget, paramMeasurer, paramBoolean);
            continue;
          } 
          if (constraintAnchor2 == constraintWidget.mRight && constraintWidget.mLeft.mTarget == null) {
            k = i - constraintWidget.mRight.getMargin();
            constraintWidget.setFinalHorizontal(k - constraintWidget.getWidth(), k);
            horizontalSolvingPass(constraintWidget, paramMeasurer, paramBoolean);
            continue;
          } 
          if (k != 0 && !constraintWidget.isInHorizontalChain())
            solveHorizontalCenterConstraints(paramMeasurer, constraintWidget, paramBoolean); 
          continue;
        } 
        if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintMaxWidth >= 0 && constraintWidget.mMatchConstraintMinWidth >= 0 && (constraintWidget.getVisibility() == 8 || (constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.getDimensionRatio() == 0.0F)) && !constraintWidget.isInHorizontalChain() && !constraintWidget.isInVirtualLayout() && k != 0 && !constraintWidget.isInHorizontalChain())
          solveHorizontalMatchConstraint(paramConstraintWidget, paramMeasurer, constraintWidget, paramBoolean); 
      }  
  }
  
  private static void solveBarrier(Barrier paramBarrier, BasicMeasure.Measurer paramMeasurer, int paramInt, boolean paramBoolean) {
    if (paramBarrier.allSolved())
      if (paramInt == 0) {
        horizontalSolvingPass((ConstraintWidget)paramBarrier, paramMeasurer, paramBoolean);
      } else {
        verticalSolvingPass((ConstraintWidget)paramBarrier, paramMeasurer);
      }  
  }
  
  public static boolean solveChain(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt1, int paramInt2, ChainHead paramChainHead, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    boolean bool = false;
    if (paramBoolean3)
      return false; 
    if (paramInt1 == 0) {
      if (!paramConstraintWidgetContainer.isResolvedHorizontally())
        return false; 
    } else if (!paramConstraintWidgetContainer.isResolvedVertically()) {
      return false;
    } 
    boolean bool1 = paramConstraintWidgetContainer.isRtl();
    ConstraintWidget constraintWidget1 = paramChainHead.getFirst();
    ConstraintWidget constraintWidget2 = paramChainHead.getLast();
    ConstraintWidget constraintWidget4 = paramChainHead.getFirstVisibleWidget();
    ConstraintWidget constraintWidget3 = paramChainHead.getLastVisibleWidget();
    ConstraintWidget constraintWidget5 = paramChainHead.getHead();
    ConstraintAnchor constraintAnchor1 = constraintWidget1.mListAnchors[paramInt2];
    ConstraintAnchor[] arrayOfConstraintAnchor = constraintWidget2.mListAnchors;
    int i = paramInt2 + 1;
    ConstraintAnchor constraintAnchor2 = arrayOfConstraintAnchor[i];
    paramBoolean3 = bool;
    if (constraintAnchor1.mTarget != null)
      if (constraintAnchor2.mTarget == null) {
        paramBoolean3 = bool;
      } else {
        paramBoolean3 = bool;
        if (constraintAnchor1.mTarget.hasFinalValue())
          if (!constraintAnchor2.mTarget.hasFinalValue()) {
            paramBoolean3 = bool;
          } else {
            paramBoolean3 = bool;
            if (constraintWidget4 != null)
              if (constraintWidget3 == null) {
                paramBoolean3 = bool;
              } else {
                Object object;
                int n = constraintAnchor1.mTarget.getFinalValue() + constraintWidget4.mListAnchors[paramInt2].getMargin();
                int m = constraintAnchor2.mTarget.getFinalValue() - constraintWidget3.mListAnchors[i].getMargin();
                int i1 = m - n;
                if (i1 <= 0)
                  return false; 
                BasicMeasure.Measure measure = new BasicMeasure.Measure();
                ConstraintWidget constraintWidget7 = constraintWidget1;
                int j = 0;
                byte b1 = 0;
                byte b2 = 0;
                int k = 0;
                ConstraintWidget constraintWidget6 = constraintWidget1;
                while (true) {
                  ConstraintAnchor constraintAnchor = null;
                  if (!j) {
                    ConstraintWidget constraintWidget;
                    ConstraintAnchor constraintAnchor3 = constraintWidget7.mListAnchors[paramInt2];
                    if (!canMeasure(constraintWidget7))
                      return false; 
                    if (constraintWidget7.mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                      return false; 
                    if (constraintWidget7.isMeasureRequested())
                      ConstraintWidgetContainer.measure(constraintWidget7, paramConstraintWidgetContainer.getMeasurer(), measure, BasicMeasure.Measure.SELF_DIMENSIONS); 
                    int i4 = constraintWidget7.mListAnchors[paramInt2].getMargin();
                    if (paramInt1 == 0) {
                      i3 = constraintWidget7.getWidth();
                    } else {
                      i3 = constraintWidget7.getHeight();
                    } 
                    k = k + i4 + i3 + constraintWidget7.mListAnchors[i].getMargin();
                    int i3 = object + 1;
                    i2 = b1;
                    if (constraintWidget7.getVisibility() != 8)
                      i2 = b1 + 1; 
                    ConstraintAnchor constraintAnchor4 = (constraintWidget7.mListAnchors[i]).mTarget;
                    constraintAnchor3 = constraintAnchor;
                    if (constraintAnchor4 != null) {
                      ConstraintWidget constraintWidget8 = constraintAnchor4.mOwner;
                      constraintAnchor3 = constraintAnchor;
                      if ((constraintWidget8.mListAnchors[paramInt2]).mTarget != null)
                        if ((constraintWidget8.mListAnchors[paramInt2]).mTarget.mOwner != constraintWidget7) {
                          constraintAnchor3 = constraintAnchor;
                        } else {
                          constraintWidget = constraintWidget8;
                        }  
                    } 
                    if (constraintWidget != null) {
                      constraintWidget7 = constraintWidget;
                    } else {
                      j = 1;
                    } 
                    continue;
                  } 
                  if (b1 == 0)
                    return false; 
                  if (b1 != i2)
                    return false; 
                  if (i1 < k)
                    return false; 
                  int i2 = i1 - k;
                  if (paramBoolean1) {
                    j = i2 / (b1 + 1);
                  } else {
                    j = i2;
                    if (paramBoolean2) {
                      j = i2;
                      if (b1 > 2)
                        j = i2 / b1 - 1; 
                    } 
                  } 
                  if (b1 == 1) {
                    float f;
                    if (paramInt1 == 0) {
                      f = constraintWidget5.getHorizontalBiasPercent();
                    } else {
                      f = constraintWidget5.getVerticalBiasPercent();
                    } 
                    paramInt2 = (int)(n + 0.5F + j * f);
                    if (paramInt1 == 0) {
                      constraintWidget4.setFinalHorizontal(paramInt2, constraintWidget4.getWidth() + paramInt2);
                    } else {
                      constraintWidget4.setFinalVertical(paramInt2, constraintWidget4.getHeight() + paramInt2);
                    } 
                    horizontalSolvingPass(constraintWidget4, paramConstraintWidgetContainer.getMeasurer(), bool1);
                    return true;
                  } 
                  if (paramBoolean1) {
                    i2 = n + j;
                    b1 = 0;
                    constraintWidget1 = constraintWidget6;
                    while (true)
                      constraintWidget1 = constraintWidget6; 
                  } else if (paramBoolean2) {
                    if (b1 == 2) {
                      if (paramInt1 == 0) {
                        constraintWidget4.setFinalHorizontal(n, constraintWidget4.getWidth() + n);
                        constraintWidget3.setFinalHorizontal(m - constraintWidget3.getWidth(), m);
                        horizontalSolvingPass(constraintWidget4, paramConstraintWidgetContainer.getMeasurer(), bool1);
                        horizontalSolvingPass(constraintWidget3, paramConstraintWidgetContainer.getMeasurer(), bool1);
                      } else {
                        constraintWidget4.setFinalVertical(n, constraintWidget4.getHeight() + n);
                        constraintWidget3.setFinalVertical(m - constraintWidget3.getHeight(), m);
                        verticalSolvingPass(constraintWidget4, paramConstraintWidgetContainer.getMeasurer());
                        verticalSolvingPass(constraintWidget3, paramConstraintWidgetContainer.getMeasurer());
                      } 
                      return true;
                    } 
                    return false;
                  } 
                  paramBoolean3 = true;
                  break;
                  b1 = b2;
                  object = SYNTHETIC_LOCAL_VARIABLE_13;
                } 
              }  
          }  
      }  
    return paramBoolean3;
  }
  
  private static void solveHorizontalCenterConstraints(BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget, boolean paramBoolean) {
    float f = paramConstraintWidget.getHorizontalBiasPercent();
    int i = paramConstraintWidget.mLeft.mTarget.getFinalValue();
    int j = paramConstraintWidget.mRight.mTarget.getFinalValue();
    int m = paramConstraintWidget.mLeft.getMargin();
    int k = paramConstraintWidget.mRight.getMargin();
    if (i == j) {
      f = 0.5F;
    } else {
      i = m + i;
      j -= k;
    } 
    m = paramConstraintWidget.getWidth();
    k = j - i - m;
    if (i > j)
      k = i - j - m; 
    int n = (int)(f * k + 0.5F) + i;
    k = n + m;
    if (i > j)
      k = n - m; 
    paramConstraintWidget.setFinalHorizontal(n, k);
    horizontalSolvingPass(paramConstraintWidget, paramMeasurer, paramBoolean);
  }
  
  private static void solveHorizontalMatchConstraint(ConstraintWidget paramConstraintWidget1, BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget2, boolean paramBoolean) {
    float f = paramConstraintWidget2.getHorizontalBiasPercent();
    int j = paramConstraintWidget2.mLeft.mTarget.getFinalValue() + paramConstraintWidget2.mLeft.getMargin();
    int i = paramConstraintWidget2.mRight.mTarget.getFinalValue() - paramConstraintWidget2.mRight.getMargin();
    if (i >= j) {
      int m = paramConstraintWidget2.getWidth();
      int k = m;
      if (paramConstraintWidget2.getVisibility() != 8) {
        if (paramConstraintWidget2.mMatchConstraintDefaultWidth == 2) {
          if (paramConstraintWidget1 instanceof ConstraintWidgetContainer) {
            k = paramConstraintWidget1.getWidth();
          } else {
            k = paramConstraintWidget1.getParent().getWidth();
          } 
          k = (int)(paramConstraintWidget2.getHorizontalBiasPercent() * 0.5F * k);
        } else {
          k = m;
          if (paramConstraintWidget2.mMatchConstraintDefaultWidth == 0)
            k = i - j; 
        } 
        m = Math.max(paramConstraintWidget2.mMatchConstraintMinWidth, k);
        k = m;
        if (paramConstraintWidget2.mMatchConstraintMaxWidth > 0)
          k = Math.min(paramConstraintWidget2.mMatchConstraintMaxWidth, m); 
      } 
      m = j + (int)(f * (i - j - k) + 0.5F);
      paramConstraintWidget2.setFinalHorizontal(m, k + m);
      horizontalSolvingPass(paramConstraintWidget2, paramMeasurer, paramBoolean);
    } 
  }
  
  private static void solveVerticalCenterConstraints(BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget) {
    float f = paramConstraintWidget.getVerticalBiasPercent();
    int j = paramConstraintWidget.mTop.mTarget.getFinalValue();
    int i = paramConstraintWidget.mBottom.mTarget.getFinalValue();
    int m = paramConstraintWidget.mTop.getMargin();
    int k = paramConstraintWidget.mBottom.getMargin();
    if (j == i) {
      f = 0.5F;
    } else {
      j = m + j;
      i -= k;
    } 
    int n = paramConstraintWidget.getHeight();
    k = i - j - n;
    if (j > i)
      k = j - i - n; 
    int i1 = (int)(f * k + 0.5F);
    k = j + i1;
    m = k + n;
    if (j > i) {
      k = j - i1;
      m = k - n;
    } 
    paramConstraintWidget.setFinalVertical(k, m);
    verticalSolvingPass(paramConstraintWidget, paramMeasurer);
  }
  
  private static void solveVerticalMatchConstraint(ConstraintWidget paramConstraintWidget1, BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget2) {
    float f = paramConstraintWidget2.getVerticalBiasPercent();
    int j = paramConstraintWidget2.mTop.mTarget.getFinalValue() + paramConstraintWidget2.mTop.getMargin();
    int i = paramConstraintWidget2.mBottom.mTarget.getFinalValue() - paramConstraintWidget2.mBottom.getMargin();
    if (i >= j) {
      int m = paramConstraintWidget2.getHeight();
      int k = m;
      if (paramConstraintWidget2.getVisibility() != 8) {
        if (paramConstraintWidget2.mMatchConstraintDefaultHeight == 2) {
          if (paramConstraintWidget1 instanceof ConstraintWidgetContainer) {
            k = paramConstraintWidget1.getHeight();
          } else {
            k = paramConstraintWidget1.getParent().getHeight();
          } 
          k = (int)(f * 0.5F * k);
        } else {
          k = m;
          if (paramConstraintWidget2.mMatchConstraintDefaultHeight == 0)
            k = i - j; 
        } 
        m = Math.max(paramConstraintWidget2.mMatchConstraintMinHeight, k);
        k = m;
        if (paramConstraintWidget2.mMatchConstraintMaxHeight > 0)
          k = Math.min(paramConstraintWidget2.mMatchConstraintMaxHeight, m); 
      } 
      m = j + (int)(f * (i - j - k) + 0.5F);
      paramConstraintWidget2.setFinalVertical(m, k + m);
      verticalSolvingPass(paramConstraintWidget2, paramMeasurer);
    } 
  }
  
  public static void solvingPass(ConstraintWidgetContainer paramConstraintWidgetContainer, BasicMeasure.Measurer paramMeasurer) {
    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = paramConstraintWidgetContainer.getHorizontalDimensionBehaviour();
    ConstraintWidget.DimensionBehaviour dimensionBehaviour1 = paramConstraintWidgetContainer.getVerticalDimensionBehaviour();
    paramConstraintWidgetContainer.resetFinalResolution();
    ArrayList<ConstraintWidget> arrayList = paramConstraintWidgetContainer.getChildren();
    int i = arrayList.size();
    boolean bool = false;
    byte b1;
    for (b1 = 0; b1 < i; b1++)
      ((ConstraintWidget)arrayList.get(b1)).resetFinalResolution(); 
    boolean bool1 = paramConstraintWidgetContainer.isRtl();
    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED) {
      paramConstraintWidgetContainer.setFinalHorizontal(0, paramConstraintWidgetContainer.getWidth());
    } else {
      paramConstraintWidgetContainer.setFinalLeft(0);
    } 
    byte b2 = 0;
    byte b3 = 0;
    for (b1 = 0; b2 < i; b1 = b4) {
      byte b4;
      byte b5;
      Guideline guideline;
      ConstraintWidget constraintWidget = arrayList.get(b2);
      if (constraintWidget instanceof Guideline) {
        guideline = (Guideline)constraintWidget;
        b5 = b3;
        b4 = b1;
        if (guideline.getOrientation() == 1) {
          if (guideline.getRelativeBegin() != -1) {
            guideline.setFinalValue(guideline.getRelativeBegin());
          } else if (guideline.getRelativeEnd() != -1 && paramConstraintWidgetContainer.isResolvedHorizontally()) {
            guideline.setFinalValue(paramConstraintWidgetContainer.getWidth() - guideline.getRelativeEnd());
          } else if (paramConstraintWidgetContainer.isResolvedHorizontally()) {
            guideline.setFinalValue((int)(guideline.getRelativePercent() * paramConstraintWidgetContainer.getWidth() + 0.5F));
          } 
          b5 = 1;
          b4 = b1;
        } 
      } else {
        b5 = b3;
        b4 = b1;
        if (guideline instanceof Barrier) {
          b5 = b3;
          b4 = b1;
          if (((Barrier)guideline).getOrientation() == 0) {
            b4 = 1;
            b5 = b3;
          } 
        } 
      } 
      b2++;
      b3 = b5;
    } 
    if (b3)
      for (b2 = 0; b2 < i; b2++) {
        ConstraintWidget constraintWidget = arrayList.get(b2);
        if (constraintWidget instanceof Guideline) {
          Guideline guideline = (Guideline)constraintWidget;
          if (guideline.getOrientation() == 1)
            horizontalSolvingPass((ConstraintWidget)guideline, paramMeasurer, bool1); 
        } 
      }  
    horizontalSolvingPass((ConstraintWidget)paramConstraintWidgetContainer, paramMeasurer, bool1);
    if (b1 != 0)
      for (b1 = 0; b1 < i; b1++) {
        ConstraintWidget constraintWidget = arrayList.get(b1);
        if (constraintWidget instanceof Barrier) {
          Barrier barrier = (Barrier)constraintWidget;
          if (barrier.getOrientation() == 0)
            solveBarrier(barrier, paramMeasurer, 0, bool1); 
        } 
      }  
    if (dimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.FIXED) {
      paramConstraintWidgetContainer.setFinalVertical(0, paramConstraintWidgetContainer.getHeight());
    } else {
      paramConstraintWidgetContainer.setFinalTop(0);
    } 
    b3 = 0;
    b2 = 0;
    for (b1 = 0; b3 < i; b1 = b5) {
      byte b4;
      byte b5;
      Guideline guideline;
      ConstraintWidget constraintWidget = arrayList.get(b3);
      if (constraintWidget instanceof Guideline) {
        guideline = (Guideline)constraintWidget;
        b4 = b2;
        b5 = b1;
        if (guideline.getOrientation() == 0) {
          if (guideline.getRelativeBegin() != -1) {
            guideline.setFinalValue(guideline.getRelativeBegin());
          } else if (guideline.getRelativeEnd() != -1 && paramConstraintWidgetContainer.isResolvedVertically()) {
            guideline.setFinalValue(paramConstraintWidgetContainer.getHeight() - guideline.getRelativeEnd());
          } else if (paramConstraintWidgetContainer.isResolvedVertically()) {
            guideline.setFinalValue((int)(guideline.getRelativePercent() * paramConstraintWidgetContainer.getHeight() + 0.5F));
          } 
          b4 = 1;
          b5 = b1;
        } 
      } else {
        b4 = b2;
        b5 = b1;
        if (guideline instanceof Barrier) {
          b4 = b2;
          b5 = b1;
          if (((Barrier)guideline).getOrientation() == 1) {
            b5 = 1;
            b4 = b2;
          } 
        } 
      } 
      b3++;
      b2 = b4;
    } 
    if (b2 != 0)
      for (b2 = 0; b2 < i; b2++) {
        ConstraintWidget constraintWidget = arrayList.get(b2);
        if (constraintWidget instanceof Guideline) {
          Guideline guideline = (Guideline)constraintWidget;
          if (guideline.getOrientation() == 0)
            verticalSolvingPass((ConstraintWidget)guideline, paramMeasurer); 
        } 
      }  
    verticalSolvingPass((ConstraintWidget)paramConstraintWidgetContainer, paramMeasurer);
    b2 = bool;
    if (b1 != 0) {
      b1 = 0;
      while (true) {
        b2 = bool;
        if (b1 < i) {
          ConstraintWidget constraintWidget = arrayList.get(b1);
          if (constraintWidget instanceof Barrier) {
            Barrier barrier = (Barrier)constraintWidget;
            if (barrier.getOrientation() == 1)
              solveBarrier(barrier, paramMeasurer, 1, bool1); 
          } 
          b1++;
          continue;
        } 
        break;
      } 
    } 
    while (b2 < i) {
      ConstraintWidget constraintWidget = arrayList.get(b2);
      if (constraintWidget.isMeasureRequested() && canMeasure(constraintWidget)) {
        ConstraintWidgetContainer.measure(constraintWidget, paramMeasurer, measure, BasicMeasure.Measure.SELF_DIMENSIONS);
        horizontalSolvingPass(constraintWidget, paramMeasurer, bool1);
        verticalSolvingPass(constraintWidget, paramMeasurer);
      } 
      b2++;
    } 
  }
  
  private static void verticalSolvingPass(ConstraintWidget paramConstraintWidget, BasicMeasure.Measurer paramMeasurer) {
    if (!(paramConstraintWidget instanceof ConstraintWidgetContainer) && paramConstraintWidget.isMeasureRequested() && canMeasure(paramConstraintWidget))
      ConstraintWidgetContainer.measure(paramConstraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS); 
    ConstraintAnchor constraintAnchor3 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
    ConstraintAnchor constraintAnchor2 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
    int j = constraintAnchor3.getFinalValue();
    int i = constraintAnchor2.getFinalValue();
    if (constraintAnchor3.getDependents() != null && constraintAnchor3.hasFinalValue())
      for (ConstraintAnchor constraintAnchor : constraintAnchor3.getDependents()) {
        ConstraintWidget constraintWidget = constraintAnchor.mOwner;
        boolean bool = canMeasure(constraintWidget);
        if (constraintWidget.isMeasureRequested() && bool)
          ConstraintWidgetContainer.measure(constraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS); 
        if (constraintWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || bool) {
          if (constraintWidget.isMeasureRequested())
            continue; 
          if (constraintAnchor == constraintWidget.mTop && constraintWidget.mBottom.mTarget == null) {
            int k = constraintWidget.mTop.getMargin() + j;
            constraintWidget.setFinalVertical(k, constraintWidget.getHeight() + k);
            verticalSolvingPass(constraintWidget, paramMeasurer);
            continue;
          } 
          if (constraintAnchor == constraintWidget.mBottom && constraintWidget.mBottom.mTarget == null) {
            int k = j - constraintWidget.mBottom.getMargin();
            constraintWidget.setFinalVertical(k - constraintWidget.getHeight(), k);
            verticalSolvingPass(constraintWidget, paramMeasurer);
            continue;
          } 
          if (constraintAnchor == constraintWidget.mTop && constraintWidget.mBottom.mTarget != null && constraintWidget.mBottom.mTarget.hasFinalValue())
            solveVerticalCenterConstraints(paramMeasurer, constraintWidget); 
          continue;
        } 
        if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintMaxHeight >= 0 && constraintWidget.mMatchConstraintMinHeight >= 0 && (constraintWidget.getVisibility() == 8 || (constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.getDimensionRatio() == 0.0F)) && !constraintWidget.isInVerticalChain() && !constraintWidget.isInVirtualLayout()) {
          boolean bool1;
          if ((constraintAnchor == constraintWidget.mTop && constraintWidget.mBottom.mTarget != null && constraintWidget.mBottom.mTarget.hasFinalValue()) || (constraintAnchor == constraintWidget.mBottom && constraintWidget.mTop.mTarget != null && constraintWidget.mTop.mTarget.hasFinalValue())) {
            bool1 = true;
          } else {
            bool1 = false;
          } 
          if (bool1 && !constraintWidget.isInVerticalChain())
            solveVerticalMatchConstraint(paramConstraintWidget, paramMeasurer, constraintWidget); 
        } 
      }  
    if (paramConstraintWidget instanceof Guideline)
      return; 
    if (constraintAnchor2.getDependents() != null && constraintAnchor2.hasFinalValue())
      for (ConstraintAnchor constraintAnchor : constraintAnchor2.getDependents()) {
        int k;
        ConstraintWidget constraintWidget = constraintAnchor.mOwner;
        boolean bool = canMeasure(constraintWidget);
        if (constraintWidget.isMeasureRequested() && bool)
          ConstraintWidgetContainer.measure(constraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS); 
        if ((constraintAnchor == constraintWidget.mTop && constraintWidget.mBottom.mTarget != null && constraintWidget.mBottom.mTarget.hasFinalValue()) || (constraintAnchor == constraintWidget.mBottom && constraintWidget.mTop.mTarget != null && constraintWidget.mTop.mTarget.hasFinalValue())) {
          k = 1;
        } else {
          k = 0;
        } 
        if (constraintWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || bool) {
          if (constraintWidget.isMeasureRequested())
            continue; 
          if (constraintAnchor == constraintWidget.mTop && constraintWidget.mBottom.mTarget == null) {
            k = constraintWidget.mTop.getMargin() + i;
            constraintWidget.setFinalVertical(k, constraintWidget.getHeight() + k);
            verticalSolvingPass(constraintWidget, paramMeasurer);
            continue;
          } 
          if (constraintAnchor == constraintWidget.mBottom && constraintWidget.mTop.mTarget == null) {
            k = i - constraintWidget.mBottom.getMargin();
            constraintWidget.setFinalVertical(k - constraintWidget.getHeight(), k);
            verticalSolvingPass(constraintWidget, paramMeasurer);
            continue;
          } 
          if (k != 0 && !constraintWidget.isInVerticalChain())
            solveVerticalCenterConstraints(paramMeasurer, constraintWidget); 
          continue;
        } 
        if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintMaxHeight >= 0 && constraintWidget.mMatchConstraintMinHeight >= 0 && (constraintWidget.getVisibility() == 8 || (constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.getDimensionRatio() == 0.0F)) && !constraintWidget.isInVerticalChain() && !constraintWidget.isInVirtualLayout() && k != 0 && !constraintWidget.isInVerticalChain())
          solveVerticalMatchConstraint(paramConstraintWidget, paramMeasurer, constraintWidget); 
      }  
    ConstraintAnchor constraintAnchor1 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE);
    if (constraintAnchor1.getDependents() != null && constraintAnchor1.hasFinalValue()) {
      int k = constraintAnchor1.getFinalValue();
      Iterator<ConstraintAnchor> iterator = constraintAnchor1.getDependents().iterator();
      while (true) {
        if (iterator.hasNext()) {
          constraintAnchor1 = iterator.next();
          ConstraintWidget constraintWidget = constraintAnchor1.mOwner;
          boolean bool = canMeasure(constraintWidget);
          if (constraintWidget.isMeasureRequested() && bool)
            ConstraintWidgetContainer.measure(constraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS); 
          if ((constraintWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || bool) && !constraintWidget.isMeasureRequested() && constraintAnchor1 == constraintWidget.mBaseline) {
            constraintWidget.setFinalBaseline(k);
            try {
              verticalSolvingPass(constraintWidget, paramMeasurer);
            } finally {}
          } 
          continue;
        } 
        return;
      } 
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\solver\widgets\analyzer\Direct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */