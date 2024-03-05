package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.widget.ConstraintSet;

public class TransitionBuilder {
  private static final String TAG = "TransitionBuilder";
  
  public static MotionScene.Transition buildTransition(MotionScene paramMotionScene, int paramInt1, int paramInt2, ConstraintSet paramConstraintSet1, int paramInt3, ConstraintSet paramConstraintSet2) {
    MotionScene.Transition transition = new MotionScene.Transition(paramInt1, paramMotionScene, paramInt2, paramInt3);
    updateConstraintSetInMotionScene(paramMotionScene, transition, paramConstraintSet1, paramConstraintSet2);
    return transition;
  }
  
  private static void updateConstraintSetInMotionScene(MotionScene paramMotionScene, MotionScene.Transition paramTransition, ConstraintSet paramConstraintSet1, ConstraintSet paramConstraintSet2) {
    int j = paramTransition.getStartConstraintSetId();
    int i = paramTransition.getEndConstraintSetId();
    paramMotionScene.setConstraintSet(j, paramConstraintSet1);
    paramMotionScene.setConstraintSet(i, paramConstraintSet2);
  }
  
  public static void validate(MotionLayout paramMotionLayout) {
    if (paramMotionLayout.mScene != null) {
      MotionScene motionScene = paramMotionLayout.mScene;
      if (motionScene.validateLayout(paramMotionLayout)) {
        if (motionScene.mCurrentTransition != null && !motionScene.getDefinedTransitions().isEmpty())
          return; 
        throw new RuntimeException("Invalid motion layout. Motion Scene doesn't have any transition.");
      } 
      throw new RuntimeException("MotionLayout doesn't have the right motion scene.");
    } 
    throw new RuntimeException("Invalid motion layout. Layout missing Motion Scene.");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\TransitionBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */