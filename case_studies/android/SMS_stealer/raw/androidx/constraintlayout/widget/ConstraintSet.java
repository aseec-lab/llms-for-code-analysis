package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintSet {
  private static final int ALPHA = 43;
  
  private static final int ANIMATE_RELATIVE_TO = 64;
  
  private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
  
  private static final int BARRIER_DIRECTION = 72;
  
  private static final int BARRIER_MARGIN = 73;
  
  private static final int BARRIER_TYPE = 1;
  
  public static final int BASELINE = 5;
  
  private static final int BASELINE_TO_BASELINE = 1;
  
  public static final int BOTTOM = 4;
  
  private static final int BOTTOM_MARGIN = 2;
  
  private static final int BOTTOM_TO_BOTTOM = 3;
  
  private static final int BOTTOM_TO_TOP = 4;
  
  public static final int CHAIN_PACKED = 2;
  
  public static final int CHAIN_SPREAD = 0;
  
  public static final int CHAIN_SPREAD_INSIDE = 1;
  
  private static final int CHAIN_USE_RTL = 71;
  
  private static final int CIRCLE = 61;
  
  private static final int CIRCLE_ANGLE = 63;
  
  private static final int CIRCLE_RADIUS = 62;
  
  private static final int CONSTRAINED_HEIGHT = 81;
  
  private static final int CONSTRAINED_WIDTH = 80;
  
  private static final int CONSTRAINT_REFERENCED_IDS = 74;
  
  private static final int CONSTRAINT_TAG = 77;
  
  private static final boolean DEBUG = false;
  
  private static final int DIMENSION_RATIO = 5;
  
  private static final int DRAW_PATH = 66;
  
  private static final int EDITOR_ABSOLUTE_X = 6;
  
  private static final int EDITOR_ABSOLUTE_Y = 7;
  
  private static final int ELEVATION = 44;
  
  public static final int END = 7;
  
  private static final int END_MARGIN = 8;
  
  private static final int END_TO_END = 9;
  
  private static final int END_TO_START = 10;
  
  private static final String ERROR_MESSAGE = "XML parser error must be within a Constraint ";
  
  public static final int GONE = 8;
  
  private static final int GONE_BOTTOM_MARGIN = 11;
  
  private static final int GONE_END_MARGIN = 12;
  
  private static final int GONE_LEFT_MARGIN = 13;
  
  private static final int GONE_RIGHT_MARGIN = 14;
  
  private static final int GONE_START_MARGIN = 15;
  
  private static final int GONE_TOP_MARGIN = 16;
  
  private static final int GUIDE_BEGIN = 17;
  
  private static final int GUIDE_END = 18;
  
  private static final int GUIDE_PERCENT = 19;
  
  private static final int HEIGHT_DEFAULT = 55;
  
  private static final int HEIGHT_MAX = 57;
  
  private static final int HEIGHT_MIN = 59;
  
  private static final int HEIGHT_PERCENT = 70;
  
  public static final int HORIZONTAL = 0;
  
  private static final int HORIZONTAL_BIAS = 20;
  
  public static final int HORIZONTAL_GUIDELINE = 0;
  
  private static final int HORIZONTAL_STYLE = 41;
  
  private static final int HORIZONTAL_WEIGHT = 39;
  
  public static final int INVISIBLE = 4;
  
  private static final int LAYOUT_HEIGHT = 21;
  
  private static final int LAYOUT_VISIBILITY = 22;
  
  private static final int LAYOUT_WIDTH = 23;
  
  public static final int LEFT = 1;
  
  private static final int LEFT_MARGIN = 24;
  
  private static final int LEFT_TO_LEFT = 25;
  
  private static final int LEFT_TO_RIGHT = 26;
  
  public static final int MATCH_CONSTRAINT = 0;
  
  public static final int MATCH_CONSTRAINT_SPREAD = 0;
  
  public static final int MATCH_CONSTRAINT_WRAP = 1;
  
  private static final int MOTION_STAGGER = 79;
  
  private static final int ORIENTATION = 27;
  
  public static final int PARENT_ID = 0;
  
  private static final int PATH_MOTION_ARC = 76;
  
  private static final int PROGRESS = 68;
  
  public static final int RIGHT = 2;
  
  private static final int RIGHT_MARGIN = 28;
  
  private static final int RIGHT_TO_LEFT = 29;
  
  private static final int RIGHT_TO_RIGHT = 30;
  
  private static final int ROTATION = 60;
  
  private static final int ROTATION_X = 45;
  
  private static final int ROTATION_Y = 46;
  
  private static final int SCALE_X = 47;
  
  private static final int SCALE_Y = 48;
  
  public static final int START = 6;
  
  private static final int START_MARGIN = 31;
  
  private static final int START_TO_END = 32;
  
  private static final int START_TO_START = 33;
  
  private static final String TAG = "ConstraintSet";
  
  public static final int TOP = 3;
  
  private static final int TOP_MARGIN = 34;
  
  private static final int TOP_TO_BOTTOM = 35;
  
  private static final int TOP_TO_TOP = 36;
  
  private static final int TRANSFORM_PIVOT_X = 49;
  
  private static final int TRANSFORM_PIVOT_Y = 50;
  
  private static final int TRANSITION_EASING = 65;
  
  private static final int TRANSITION_PATH_ROTATE = 67;
  
  private static final int TRANSLATION_X = 51;
  
  private static final int TRANSLATION_Y = 52;
  
  private static final int TRANSLATION_Z = 53;
  
  public static final int UNSET = -1;
  
  private static final int UNUSED = 82;
  
  public static final int VERTICAL = 1;
  
  private static final int VERTICAL_BIAS = 37;
  
  public static final int VERTICAL_GUIDELINE = 1;
  
  private static final int VERTICAL_STYLE = 42;
  
  private static final int VERTICAL_WEIGHT = 40;
  
  private static final int VIEW_ID = 38;
  
  private static final int[] VISIBILITY_FLAGS = new int[] { 0, 4, 8 };
  
  private static final int VISIBILITY_MODE = 78;
  
  public static final int VISIBILITY_MODE_IGNORE = 1;
  
  public static final int VISIBILITY_MODE_NORMAL = 0;
  
  public static final int VISIBLE = 0;
  
  private static final int WIDTH_DEFAULT = 54;
  
  private static final int WIDTH_MAX = 56;
  
  private static final int WIDTH_MIN = 58;
  
  private static final int WIDTH_PERCENT = 69;
  
  public static final int WRAP_CONTENT = -2;
  
  private static SparseIntArray mapToConstant;
  
  private HashMap<Integer, Constraint> mConstraints = new HashMap<Integer, Constraint>();
  
  private boolean mForceId = true;
  
  private HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap<String, ConstraintAttribute>();
  
  private boolean mValidate;
  
  static {
    SparseIntArray sparseIntArray = new SparseIntArray();
    mapToConstant = sparseIntArray;
    sparseIntArray.append(R.styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
    mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_toRightOf, 26);
    mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toLeftOf, 29);
    mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toRightOf, 30);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toTopOf, 36);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toBottomOf, 35);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toTopOf, 4);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toBottomOf, 3);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toBaselineOf, 1);
    mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteX, 6);
    mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteY, 7);
    mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_begin, 17);
    mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_end, 18);
    mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_percent, 19);
    mapToConstant.append(R.styleable.Constraint_android_orientation, 27);
    mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toEndOf, 32);
    mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toStartOf, 33);
    mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toStartOf, 10);
    mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toEndOf, 9);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginLeft, 13);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginTop, 16);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginRight, 14);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginBottom, 11);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginStart, 15);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginEnd, 12);
    mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_weight, 40);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_weight, 39);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_chainStyle, 41);
    mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_chainStyle, 42);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_bias, 20);
    mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_bias, 37);
    mapToConstant.append(R.styleable.Constraint_layout_constraintDimensionRatio, 5);
    mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_creator, 82);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTop_creator, 82);
    mapToConstant.append(R.styleable.Constraint_layout_constraintRight_creator, 82);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_creator, 82);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_creator, 82);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginLeft, 24);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginRight, 28);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginStart, 31);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginEnd, 8);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginTop, 34);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginBottom, 2);
    mapToConstant.append(R.styleable.Constraint_android_layout_width, 23);
    mapToConstant.append(R.styleable.Constraint_android_layout_height, 21);
    mapToConstant.append(R.styleable.Constraint_android_visibility, 22);
    mapToConstant.append(R.styleable.Constraint_android_alpha, 43);
    mapToConstant.append(R.styleable.Constraint_android_elevation, 44);
    mapToConstant.append(R.styleable.Constraint_android_rotationX, 45);
    mapToConstant.append(R.styleable.Constraint_android_rotationY, 46);
    mapToConstant.append(R.styleable.Constraint_android_rotation, 60);
    mapToConstant.append(R.styleable.Constraint_android_scaleX, 47);
    mapToConstant.append(R.styleable.Constraint_android_scaleY, 48);
    mapToConstant.append(R.styleable.Constraint_android_transformPivotX, 49);
    mapToConstant.append(R.styleable.Constraint_android_transformPivotY, 50);
    mapToConstant.append(R.styleable.Constraint_android_translationX, 51);
    mapToConstant.append(R.styleable.Constraint_android_translationY, 52);
    mapToConstant.append(R.styleable.Constraint_android_translationZ, 53);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_default, 54);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_default, 55);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_max, 56);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_max, 57);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_min, 58);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_min, 59);
    mapToConstant.append(R.styleable.Constraint_layout_constraintCircle, 61);
    mapToConstant.append(R.styleable.Constraint_layout_constraintCircleRadius, 62);
    mapToConstant.append(R.styleable.Constraint_layout_constraintCircleAngle, 63);
    mapToConstant.append(R.styleable.Constraint_animate_relativeTo, 64);
    mapToConstant.append(R.styleable.Constraint_transitionEasing, 65);
    mapToConstant.append(R.styleable.Constraint_drawPath, 66);
    mapToConstant.append(R.styleable.Constraint_transitionPathRotate, 67);
    mapToConstant.append(R.styleable.Constraint_motionStagger, 79);
    mapToConstant.append(R.styleable.Constraint_android_id, 38);
    mapToConstant.append(R.styleable.Constraint_motionProgress, 68);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_percent, 69);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_percent, 70);
    mapToConstant.append(R.styleable.Constraint_chainUseRtl, 71);
    mapToConstant.append(R.styleable.Constraint_barrierDirection, 72);
    mapToConstant.append(R.styleable.Constraint_barrierMargin, 73);
    mapToConstant.append(R.styleable.Constraint_constraint_referenced_ids, 74);
    mapToConstant.append(R.styleable.Constraint_barrierAllowsGoneWidgets, 75);
    mapToConstant.append(R.styleable.Constraint_pathMotionArc, 76);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTag, 77);
    mapToConstant.append(R.styleable.Constraint_visibilityMode, 78);
    mapToConstant.append(R.styleable.Constraint_layout_constrainedWidth, 80);
    mapToConstant.append(R.styleable.Constraint_layout_constrainedHeight, 81);
  }
  
  private void addAttributes(ConstraintAttribute.AttributeType paramAttributeType, String... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length; b++) {
      StringBuilder stringBuilder;
      if (this.mSavedAttributes.containsKey(paramVarArgs[b])) {
        ConstraintAttribute constraintAttribute = this.mSavedAttributes.get(paramVarArgs[b]);
        if (constraintAttribute.getType() != paramAttributeType) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("ConstraintAttribute is already a ");
          stringBuilder.append(constraintAttribute.getType().name());
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        ConstraintAttribute constraintAttribute = new ConstraintAttribute(paramVarArgs[b], (ConstraintAttribute.AttributeType)stringBuilder);
        this.mSavedAttributes.put(paramVarArgs[b], constraintAttribute);
      } 
    } 
  }
  
  private int[] convertReferenceString(View paramView, String paramString) {
    String[] arrayOfString = paramString.split(",");
    Context context = paramView.getContext();
    int[] arrayOfInt2 = new int[arrayOfString.length];
    byte b2 = 0;
    byte b1;
    for (b1 = 0; b2 < arrayOfString.length; b1++) {
      String str = arrayOfString[b2].trim();
      try {
        j = R.id.class.getField(str).getInt(null);
      } catch (Exception exception) {
        j = 0;
      } 
      int i = j;
      if (!j)
        i = context.getResources().getIdentifier(str, "id", context.getPackageName()); 
      int j = i;
      if (i == 0) {
        j = i;
        if (paramView.isInEditMode()) {
          j = i;
          if (paramView.getParent() instanceof ConstraintLayout) {
            Object object = ((ConstraintLayout)paramView.getParent()).getDesignInformation(0, str);
            j = i;
            if (object != null) {
              j = i;
              if (object instanceof Integer)
                j = ((Integer)object).intValue(); 
            } 
          } 
        } 
      } 
      arrayOfInt2[b1] = j;
      b2++;
    } 
    int[] arrayOfInt1 = arrayOfInt2;
    if (b1 != arrayOfString.length)
      arrayOfInt1 = Arrays.copyOf(arrayOfInt2, b1); 
    return arrayOfInt1;
  }
  
  private void createHorizontalChain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, float[] paramArrayOffloat, int paramInt5, int paramInt6, int paramInt7) {
    if (paramArrayOfint.length >= 2) {
      if (paramArrayOffloat == null || paramArrayOffloat.length == paramArrayOfint.length) {
        if (paramArrayOffloat != null)
          (get(paramArrayOfint[0])).layout.horizontalWeight = paramArrayOffloat[0]; 
        (get(paramArrayOfint[0])).layout.horizontalChainStyle = paramInt5;
        connect(paramArrayOfint[0], paramInt6, paramInt1, paramInt2, -1);
        for (paramInt1 = 1; paramInt1 < paramArrayOfint.length; paramInt1++) {
          paramInt2 = paramArrayOfint[paramInt1];
          paramInt5 = paramArrayOfint[paramInt1];
          paramInt2 = paramInt1 - 1;
          connect(paramInt5, paramInt6, paramArrayOfint[paramInt2], paramInt7, -1);
          connect(paramArrayOfint[paramInt2], paramInt7, paramArrayOfint[paramInt1], paramInt6, -1);
          if (paramArrayOffloat != null)
            (get(paramArrayOfint[paramInt1])).layout.horizontalWeight = paramArrayOffloat[paramInt1]; 
        } 
        connect(paramArrayOfint[paramArrayOfint.length - 1], paramInt7, paramInt3, paramInt4, -1);
        return;
      } 
      throw new IllegalArgumentException("must have 2 or more widgets in a chain");
    } 
    throw new IllegalArgumentException("must have 2 or more widgets in a chain");
  }
  
  private Constraint fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet) {
    Constraint constraint = new Constraint();
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Constraint);
    populateConstraint(paramContext, constraint, typedArray);
    typedArray.recycle();
    return constraint;
  }
  
  private Constraint get(int paramInt) {
    if (!this.mConstraints.containsKey(Integer.valueOf(paramInt)))
      this.mConstraints.put(Integer.valueOf(paramInt), new Constraint()); 
    return this.mConstraints.get(Integer.valueOf(paramInt));
  }
  
  private static int lookupID(TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    int i = paramTypedArray.getResourceId(paramInt1, paramInt2);
    paramInt2 = i;
    if (i == -1)
      paramInt2 = paramTypedArray.getInt(paramInt1, -1); 
    return paramInt2;
  }
  
  private void populateConstraint(Context paramContext, Constraint paramConstraint, TypedArray paramTypedArray) {
    int i = paramTypedArray.getIndexCount();
    for (byte b = 0; b < i; b++) {
      StringBuilder stringBuilder;
      int j = paramTypedArray.getIndex(b);
      if (j != R.styleable.Constraint_android_id && R.styleable.Constraint_android_layout_marginStart != j && R.styleable.Constraint_android_layout_marginEnd != j) {
        paramConstraint.motion.mApply = true;
        paramConstraint.layout.mApply = true;
        paramConstraint.propertySet.mApply = true;
        paramConstraint.transform.mApply = true;
      } 
      switch (mapToConstant.get(j)) {
        default:
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown attribute 0x");
          stringBuilder.append(Integer.toHexString(j));
          stringBuilder.append("   ");
          stringBuilder.append(mapToConstant.get(j));
          Log.w("ConstraintSet", stringBuilder.toString());
          break;
        case 82:
          stringBuilder = new StringBuilder();
          stringBuilder.append("unused attribute 0x");
          stringBuilder.append(Integer.toHexString(j));
          stringBuilder.append("   ");
          stringBuilder.append(mapToConstant.get(j));
          Log.w("ConstraintSet", stringBuilder.toString());
          break;
        case 81:
          paramConstraint.layout.constrainedHeight = paramTypedArray.getBoolean(j, paramConstraint.layout.constrainedHeight);
          break;
        case 80:
          paramConstraint.layout.constrainedWidth = paramTypedArray.getBoolean(j, paramConstraint.layout.constrainedWidth);
          break;
        case 79:
          paramConstraint.motion.mMotionStagger = paramTypedArray.getFloat(j, paramConstraint.motion.mMotionStagger);
          break;
        case 78:
          paramConstraint.propertySet.mVisibilityMode = paramTypedArray.getInt(j, paramConstraint.propertySet.mVisibilityMode);
          break;
        case 77:
          paramConstraint.layout.mConstraintTag = paramTypedArray.getString(j);
          break;
        case 76:
          paramConstraint.motion.mPathMotionArc = paramTypedArray.getInt(j, paramConstraint.motion.mPathMotionArc);
          break;
        case 75:
          paramConstraint.layout.mBarrierAllowsGoneWidgets = paramTypedArray.getBoolean(j, paramConstraint.layout.mBarrierAllowsGoneWidgets);
          break;
        case 74:
          paramConstraint.layout.mReferenceIdString = paramTypedArray.getString(j);
          break;
        case 73:
          paramConstraint.layout.mBarrierMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.mBarrierMargin);
          break;
        case 72:
          paramConstraint.layout.mBarrierDirection = paramTypedArray.getInt(j, paramConstraint.layout.mBarrierDirection);
          break;
        case 71:
          Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
          break;
        case 70:
          paramConstraint.layout.heightPercent = paramTypedArray.getFloat(j, 1.0F);
          break;
        case 69:
          paramConstraint.layout.widthPercent = paramTypedArray.getFloat(j, 1.0F);
          break;
        case 68:
          paramConstraint.propertySet.mProgress = paramTypedArray.getFloat(j, paramConstraint.propertySet.mProgress);
          break;
        case 67:
          paramConstraint.motion.mPathRotate = paramTypedArray.getFloat(j, paramConstraint.motion.mPathRotate);
          break;
        case 66:
          paramConstraint.motion.mDrawPath = paramTypedArray.getInt(j, 0);
          break;
        case 65:
          if ((paramTypedArray.peekValue(j)).type == 3) {
            paramConstraint.motion.mTransitionEasing = paramTypedArray.getString(j);
            break;
          } 
          paramConstraint.motion.mTransitionEasing = Easing.NAMED_EASING[paramTypedArray.getInteger(j, 0)];
          break;
        case 64:
          paramConstraint.motion.mAnimateRelativeTo = lookupID(paramTypedArray, j, paramConstraint.motion.mAnimateRelativeTo);
          break;
        case 63:
          paramConstraint.layout.circleAngle = paramTypedArray.getFloat(j, paramConstraint.layout.circleAngle);
          break;
        case 62:
          paramConstraint.layout.circleRadius = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.circleRadius);
          break;
        case 61:
          paramConstraint.layout.circleConstraint = lookupID(paramTypedArray, j, paramConstraint.layout.circleConstraint);
          break;
        case 60:
          paramConstraint.transform.rotation = paramTypedArray.getFloat(j, paramConstraint.transform.rotation);
          break;
        case 59:
          paramConstraint.layout.heightMin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.heightMin);
          break;
        case 58:
          paramConstraint.layout.widthMin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.widthMin);
          break;
        case 57:
          paramConstraint.layout.heightMax = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.heightMax);
          break;
        case 56:
          paramConstraint.layout.widthMax = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.widthMax);
          break;
        case 55:
          paramConstraint.layout.heightDefault = paramTypedArray.getInt(j, paramConstraint.layout.heightDefault);
          break;
        case 54:
          paramConstraint.layout.widthDefault = paramTypedArray.getInt(j, paramConstraint.layout.widthDefault);
          break;
        case 53:
          if (Build.VERSION.SDK_INT >= 21)
            paramConstraint.transform.translationZ = paramTypedArray.getDimension(j, paramConstraint.transform.translationZ); 
          break;
        case 52:
          paramConstraint.transform.translationY = paramTypedArray.getDimension(j, paramConstraint.transform.translationY);
          break;
        case 51:
          paramConstraint.transform.translationX = paramTypedArray.getDimension(j, paramConstraint.transform.translationX);
          break;
        case 50:
          paramConstraint.transform.transformPivotY = paramTypedArray.getDimension(j, paramConstraint.transform.transformPivotY);
          break;
        case 49:
          paramConstraint.transform.transformPivotX = paramTypedArray.getDimension(j, paramConstraint.transform.transformPivotX);
          break;
        case 48:
          paramConstraint.transform.scaleY = paramTypedArray.getFloat(j, paramConstraint.transform.scaleY);
          break;
        case 47:
          paramConstraint.transform.scaleX = paramTypedArray.getFloat(j, paramConstraint.transform.scaleX);
          break;
        case 46:
          paramConstraint.transform.rotationY = paramTypedArray.getFloat(j, paramConstraint.transform.rotationY);
          break;
        case 45:
          paramConstraint.transform.rotationX = paramTypedArray.getFloat(j, paramConstraint.transform.rotationX);
          break;
        case 44:
          if (Build.VERSION.SDK_INT >= 21) {
            paramConstraint.transform.applyElevation = true;
            paramConstraint.transform.elevation = paramTypedArray.getDimension(j, paramConstraint.transform.elevation);
          } 
          break;
        case 43:
          paramConstraint.propertySet.alpha = paramTypedArray.getFloat(j, paramConstraint.propertySet.alpha);
          break;
        case 42:
          paramConstraint.layout.verticalChainStyle = paramTypedArray.getInt(j, paramConstraint.layout.verticalChainStyle);
          break;
        case 41:
          paramConstraint.layout.horizontalChainStyle = paramTypedArray.getInt(j, paramConstraint.layout.horizontalChainStyle);
          break;
        case 40:
          paramConstraint.layout.verticalWeight = paramTypedArray.getFloat(j, paramConstraint.layout.verticalWeight);
          break;
        case 39:
          paramConstraint.layout.horizontalWeight = paramTypedArray.getFloat(j, paramConstraint.layout.horizontalWeight);
          break;
        case 38:
          paramConstraint.mViewId = paramTypedArray.getResourceId(j, paramConstraint.mViewId);
          break;
        case 37:
          paramConstraint.layout.verticalBias = paramTypedArray.getFloat(j, paramConstraint.layout.verticalBias);
          break;
        case 36:
          paramConstraint.layout.topToTop = lookupID(paramTypedArray, j, paramConstraint.layout.topToTop);
          break;
        case 35:
          paramConstraint.layout.topToBottom = lookupID(paramTypedArray, j, paramConstraint.layout.topToBottom);
          break;
        case 34:
          paramConstraint.layout.topMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.topMargin);
          break;
        case 33:
          paramConstraint.layout.startToStart = lookupID(paramTypedArray, j, paramConstraint.layout.startToStart);
          break;
        case 32:
          paramConstraint.layout.startToEnd = lookupID(paramTypedArray, j, paramConstraint.layout.startToEnd);
          break;
        case 31:
          if (Build.VERSION.SDK_INT >= 17)
            paramConstraint.layout.startMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.startMargin); 
          break;
        case 30:
          paramConstraint.layout.rightToRight = lookupID(paramTypedArray, j, paramConstraint.layout.rightToRight);
          break;
        case 29:
          paramConstraint.layout.rightToLeft = lookupID(paramTypedArray, j, paramConstraint.layout.rightToLeft);
          break;
        case 28:
          paramConstraint.layout.rightMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.rightMargin);
          break;
        case 27:
          paramConstraint.layout.orientation = paramTypedArray.getInt(j, paramConstraint.layout.orientation);
          break;
        case 26:
          paramConstraint.layout.leftToRight = lookupID(paramTypedArray, j, paramConstraint.layout.leftToRight);
          break;
        case 25:
          paramConstraint.layout.leftToLeft = lookupID(paramTypedArray, j, paramConstraint.layout.leftToLeft);
          break;
        case 24:
          paramConstraint.layout.leftMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.leftMargin);
          break;
        case 23:
          paramConstraint.layout.mWidth = paramTypedArray.getLayoutDimension(j, paramConstraint.layout.mWidth);
          break;
        case 22:
          paramConstraint.propertySet.visibility = paramTypedArray.getInt(j, paramConstraint.propertySet.visibility);
          paramConstraint.propertySet.visibility = VISIBILITY_FLAGS[paramConstraint.propertySet.visibility];
          break;
        case 21:
          paramConstraint.layout.mHeight = paramTypedArray.getLayoutDimension(j, paramConstraint.layout.mHeight);
          break;
        case 20:
          paramConstraint.layout.horizontalBias = paramTypedArray.getFloat(j, paramConstraint.layout.horizontalBias);
          break;
        case 19:
          paramConstraint.layout.guidePercent = paramTypedArray.getFloat(j, paramConstraint.layout.guidePercent);
          break;
        case 18:
          paramConstraint.layout.guideEnd = paramTypedArray.getDimensionPixelOffset(j, paramConstraint.layout.guideEnd);
          break;
        case 17:
          paramConstraint.layout.guideBegin = paramTypedArray.getDimensionPixelOffset(j, paramConstraint.layout.guideBegin);
          break;
        case 16:
          paramConstraint.layout.goneTopMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.goneTopMargin);
          break;
        case 15:
          paramConstraint.layout.goneStartMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.goneStartMargin);
          break;
        case 14:
          paramConstraint.layout.goneRightMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.goneRightMargin);
          break;
        case 13:
          paramConstraint.layout.goneLeftMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.goneLeftMargin);
          break;
        case 12:
          paramConstraint.layout.goneEndMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.goneEndMargin);
          break;
        case 11:
          paramConstraint.layout.goneBottomMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.goneBottomMargin);
          break;
        case 10:
          paramConstraint.layout.endToStart = lookupID(paramTypedArray, j, paramConstraint.layout.endToStart);
          break;
        case 9:
          paramConstraint.layout.endToEnd = lookupID(paramTypedArray, j, paramConstraint.layout.endToEnd);
          break;
        case 8:
          if (Build.VERSION.SDK_INT >= 17)
            paramConstraint.layout.endMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.endMargin); 
          break;
        case 7:
          paramConstraint.layout.editorAbsoluteY = paramTypedArray.getDimensionPixelOffset(j, paramConstraint.layout.editorAbsoluteY);
          break;
        case 6:
          paramConstraint.layout.editorAbsoluteX = paramTypedArray.getDimensionPixelOffset(j, paramConstraint.layout.editorAbsoluteX);
          break;
        case 5:
          paramConstraint.layout.dimensionRatio = paramTypedArray.getString(j);
          break;
        case 4:
          paramConstraint.layout.bottomToTop = lookupID(paramTypedArray, j, paramConstraint.layout.bottomToTop);
          break;
        case 3:
          paramConstraint.layout.bottomToBottom = lookupID(paramTypedArray, j, paramConstraint.layout.bottomToBottom);
          break;
        case 2:
          paramConstraint.layout.bottomMargin = paramTypedArray.getDimensionPixelSize(j, paramConstraint.layout.bottomMargin);
          break;
        case 1:
          paramConstraint.layout.baselineToBaseline = lookupID(paramTypedArray, j, paramConstraint.layout.baselineToBaseline);
          break;
      } 
    } 
  }
  
  private String sideToString(int paramInt) {
    switch (paramInt) {
      default:
        return "undefined";
      case 7:
        return "end";
      case 6:
        return "start";
      case 5:
        return "baseline";
      case 4:
        return "bottom";
      case 3:
        return "top";
      case 2:
        return "right";
      case 1:
        break;
    } 
    return "left";
  }
  
  private static String[] splitString(String paramString) {
    char[] arrayOfChar = paramString.toCharArray();
    ArrayList<String> arrayList = new ArrayList();
    byte b1 = 0;
    byte b2 = 0;
    for (int i = 0; b1 < arrayOfChar.length; i = j) {
      byte b;
      int j;
      if (arrayOfChar[b1] == ',' && !i) {
        arrayList.add(new String(arrayOfChar, b2, b1 - b2));
        b = b1 + 1;
        j = i;
      } else {
        b = b2;
        j = i;
        if (arrayOfChar[b1] == '"') {
          j = i ^ 0x1;
          b = b2;
        } 
      } 
      b1++;
      b2 = b;
    } 
    arrayList.add(new String(arrayOfChar, b2, arrayOfChar.length - b2));
    return arrayList.<String>toArray(new String[arrayList.size()]);
  }
  
  public void addColorAttributes(String... paramVarArgs) {
    addAttributes(ConstraintAttribute.AttributeType.COLOR_TYPE, paramVarArgs);
  }
  
  public void addFloatAttributes(String... paramVarArgs) {
    addAttributes(ConstraintAttribute.AttributeType.FLOAT_TYPE, paramVarArgs);
  }
  
  public void addIntAttributes(String... paramVarArgs) {
    addAttributes(ConstraintAttribute.AttributeType.INT_TYPE, paramVarArgs);
  }
  
  public void addStringAttributes(String... paramVarArgs) {
    addAttributes(ConstraintAttribute.AttributeType.STRING_TYPE, paramVarArgs);
  }
  
  public void addToHorizontalChain(int paramInt1, int paramInt2, int paramInt3) {
    byte b;
    if (paramInt2 == 0) {
      b = 1;
    } else {
      b = 2;
    } 
    connect(paramInt1, 1, paramInt2, b, 0);
    if (paramInt3 == 0) {
      b = 2;
    } else {
      b = 1;
    } 
    connect(paramInt1, 2, paramInt3, b, 0);
    if (paramInt2 != 0)
      connect(paramInt2, 2, paramInt1, 1, 0); 
    if (paramInt3 != 0)
      connect(paramInt3, 1, paramInt1, 2, 0); 
  }
  
  public void addToHorizontalChainRTL(int paramInt1, int paramInt2, int paramInt3) {
    byte b;
    if (paramInt2 == 0) {
      b = 6;
    } else {
      b = 7;
    } 
    connect(paramInt1, 6, paramInt2, b, 0);
    if (paramInt3 == 0) {
      b = 7;
    } else {
      b = 6;
    } 
    connect(paramInt1, 7, paramInt3, b, 0);
    if (paramInt2 != 0)
      connect(paramInt2, 7, paramInt1, 6, 0); 
    if (paramInt3 != 0)
      connect(paramInt3, 6, paramInt1, 7, 0); 
  }
  
  public void addToVerticalChain(int paramInt1, int paramInt2, int paramInt3) {
    byte b;
    if (paramInt2 == 0) {
      b = 3;
    } else {
      b = 4;
    } 
    connect(paramInt1, 3, paramInt2, b, 0);
    if (paramInt3 == 0) {
      b = 4;
    } else {
      b = 3;
    } 
    connect(paramInt1, 4, paramInt3, b, 0);
    if (paramInt2 != 0)
      connect(paramInt2, 4, paramInt1, 3, 0); 
    if (paramInt3 != 0)
      connect(paramInt3, 3, paramInt1, 4, 0); 
  }
  
  public void applyCustomAttributes(ConstraintLayout paramConstraintLayout) {
    int i = paramConstraintLayout.getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = paramConstraintLayout.getChildAt(b);
      int j = view.getId();
      if (!this.mConstraints.containsKey(Integer.valueOf(j))) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id unknown ");
        stringBuilder.append(Debug.getName(view));
        Log.v("ConstraintSet", stringBuilder.toString());
      } else if (!this.mForceId || j != -1) {
        if (this.mConstraints.containsKey(Integer.valueOf(j)))
          ConstraintAttribute.setAttributes(view, ((Constraint)this.mConstraints.get(Integer.valueOf(j))).mCustomConstraints); 
      } else {
        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
      } 
    } 
  }
  
  public void applyTo(ConstraintLayout paramConstraintLayout) {
    applyToInternal(paramConstraintLayout, true);
    paramConstraintLayout.setConstraintSet((ConstraintSet)null);
    paramConstraintLayout.requestLayout();
  }
  
  public void applyToHelper(ConstraintHelper paramConstraintHelper, ConstraintWidget paramConstraintWidget, ConstraintLayout.LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray) {
    int i = paramConstraintHelper.getId();
    if (this.mConstraints.containsKey(Integer.valueOf(i))) {
      Constraint constraint = this.mConstraints.get(Integer.valueOf(i));
      if (paramConstraintWidget instanceof HelperWidget)
        paramConstraintHelper.loadParameters(constraint, (HelperWidget)paramConstraintWidget, paramLayoutParams, paramSparseArray); 
    } 
  }
  
  void applyToInternal(ConstraintLayout paramConstraintLayout, boolean paramBoolean) {
    int i = paramConstraintLayout.getChildCount();
    HashSet hashSet = new HashSet(this.mConstraints.keySet());
    for (byte b = 0; b < i; b++) {
      View view = paramConstraintLayout.getChildAt(b);
      int j = view.getId();
      if (!this.mConstraints.containsKey(Integer.valueOf(j))) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id unknown ");
        stringBuilder.append(Debug.getName(view));
        Log.w("ConstraintSet", stringBuilder.toString());
      } else if (!this.mForceId || j != -1) {
        if (j != -1)
          if (this.mConstraints.containsKey(Integer.valueOf(j))) {
            hashSet.remove(Integer.valueOf(j));
            Constraint constraint = this.mConstraints.get(Integer.valueOf(j));
            if (view instanceof Barrier)
              constraint.layout.mHelperType = 1; 
            if (constraint.layout.mHelperType != -1 && constraint.layout.mHelperType == 1) {
              Barrier barrier = (Barrier)view;
              barrier.setId(j);
              barrier.setType(constraint.layout.mBarrierDirection);
              barrier.setMargin(constraint.layout.mBarrierMargin);
              barrier.setAllowsGoneWidget(constraint.layout.mBarrierAllowsGoneWidgets);
              if (constraint.layout.mReferenceIds != null) {
                barrier.setReferencedIds(constraint.layout.mReferenceIds);
              } else if (constraint.layout.mReferenceIdString != null) {
                constraint.layout.mReferenceIds = convertReferenceString(barrier, constraint.layout.mReferenceIdString);
                barrier.setReferencedIds(constraint.layout.mReferenceIds);
              } 
            } 
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)view.getLayoutParams();
            layoutParams.validate();
            constraint.applyTo(layoutParams);
            if (paramBoolean)
              ConstraintAttribute.setAttributes(view, constraint.mCustomConstraints); 
            view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            if (constraint.propertySet.mVisibilityMode == 0)
              view.setVisibility(constraint.propertySet.visibility); 
            if (Build.VERSION.SDK_INT >= 17) {
              view.setAlpha(constraint.propertySet.alpha);
              view.setRotation(constraint.transform.rotation);
              view.setRotationX(constraint.transform.rotationX);
              view.setRotationY(constraint.transform.rotationY);
              view.setScaleX(constraint.transform.scaleX);
              view.setScaleY(constraint.transform.scaleY);
              if (!Float.isNaN(constraint.transform.transformPivotX))
                view.setPivotX(constraint.transform.transformPivotX); 
              if (!Float.isNaN(constraint.transform.transformPivotY))
                view.setPivotY(constraint.transform.transformPivotY); 
              view.setTranslationX(constraint.transform.translationX);
              view.setTranslationY(constraint.transform.translationY);
              if (Build.VERSION.SDK_INT >= 21) {
                view.setTranslationZ(constraint.transform.translationZ);
                if (constraint.transform.applyElevation)
                  view.setElevation(constraint.transform.elevation); 
              } 
            } 
          } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("WARNING NO CONSTRAINTS for view ");
            stringBuilder.append(j);
            Log.v("ConstraintSet", stringBuilder.toString());
          }  
      } else {
        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
      } 
    } 
    for (Integer integer : hashSet) {
      Constraint constraint = this.mConstraints.get(integer);
      if (constraint.layout.mHelperType != -1 && constraint.layout.mHelperType == 1) {
        Barrier barrier = new Barrier(paramConstraintLayout.getContext());
        barrier.setId(integer.intValue());
        if (constraint.layout.mReferenceIds != null) {
          barrier.setReferencedIds(constraint.layout.mReferenceIds);
        } else if (constraint.layout.mReferenceIdString != null) {
          constraint.layout.mReferenceIds = convertReferenceString(barrier, constraint.layout.mReferenceIdString);
          barrier.setReferencedIds(constraint.layout.mReferenceIds);
        } 
        barrier.setType(constraint.layout.mBarrierDirection);
        barrier.setMargin(constraint.layout.mBarrierMargin);
        ConstraintLayout.LayoutParams layoutParams = paramConstraintLayout.generateDefaultLayoutParams();
        barrier.validateParams();
        constraint.applyTo(layoutParams);
        paramConstraintLayout.addView(barrier, (ViewGroup.LayoutParams)layoutParams);
      } 
      if (constraint.layout.mIsGuideline) {
        Guideline guideline = new Guideline(paramConstraintLayout.getContext());
        guideline.setId(integer.intValue());
        ConstraintLayout.LayoutParams layoutParams = paramConstraintLayout.generateDefaultLayoutParams();
        constraint.applyTo(layoutParams);
        paramConstraintLayout.addView(guideline, (ViewGroup.LayoutParams)layoutParams);
      } 
    } 
  }
  
  public void applyToLayoutParams(int paramInt, ConstraintLayout.LayoutParams paramLayoutParams) {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt)))
      ((Constraint)this.mConstraints.get(Integer.valueOf(paramInt))).applyTo(paramLayoutParams); 
  }
  
  public void applyToWithoutCustom(ConstraintLayout paramConstraintLayout) {
    applyToInternal(paramConstraintLayout, false);
    paramConstraintLayout.setConstraintSet((ConstraintSet)null);
  }
  
  public void center(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat) {
    if (paramInt4 >= 0) {
      if (paramInt7 >= 0) {
        if (paramFloat > 0.0F && paramFloat <= 1.0F) {
          if (paramInt3 == 1 || paramInt3 == 2) {
            connect(paramInt1, 1, paramInt2, paramInt3, paramInt4);
            connect(paramInt1, 2, paramInt5, paramInt6, paramInt7);
            ((Constraint)this.mConstraints.get(Integer.valueOf(paramInt1))).layout.horizontalBias = paramFloat;
            return;
          } 
          if (paramInt3 == 6 || paramInt3 == 7) {
            connect(paramInt1, 6, paramInt2, paramInt3, paramInt4);
            connect(paramInt1, 7, paramInt5, paramInt6, paramInt7);
            ((Constraint)this.mConstraints.get(Integer.valueOf(paramInt1))).layout.horizontalBias = paramFloat;
            return;
          } 
          connect(paramInt1, 3, paramInt2, paramInt3, paramInt4);
          connect(paramInt1, 4, paramInt5, paramInt6, paramInt7);
          ((Constraint)this.mConstraints.get(Integer.valueOf(paramInt1))).layout.verticalBias = paramFloat;
          return;
        } 
        throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
      } 
      throw new IllegalArgumentException("margin must be > 0");
    } 
    throw new IllegalArgumentException("margin must be > 0");
  }
  
  public void centerHorizontally(int paramInt1, int paramInt2) {
    if (paramInt2 == 0) {
      center(paramInt1, 0, 1, 0, 0, 2, 0, 0.5F);
    } else {
      center(paramInt1, paramInt2, 2, 0, paramInt2, 1, 0, 0.5F);
    } 
  }
  
  public void centerHorizontally(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat) {
    connect(paramInt1, 1, paramInt2, paramInt3, paramInt4);
    connect(paramInt1, 2, paramInt5, paramInt6, paramInt7);
    ((Constraint)this.mConstraints.get(Integer.valueOf(paramInt1))).layout.horizontalBias = paramFloat;
  }
  
  public void centerHorizontallyRtl(int paramInt1, int paramInt2) {
    if (paramInt2 == 0) {
      center(paramInt1, 0, 6, 0, 0, 7, 0, 0.5F);
    } else {
      center(paramInt1, paramInt2, 7, 0, paramInt2, 6, 0, 0.5F);
    } 
  }
  
  public void centerHorizontallyRtl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat) {
    connect(paramInt1, 6, paramInt2, paramInt3, paramInt4);
    connect(paramInt1, 7, paramInt5, paramInt6, paramInt7);
    ((Constraint)this.mConstraints.get(Integer.valueOf(paramInt1))).layout.horizontalBias = paramFloat;
  }
  
  public void centerVertically(int paramInt1, int paramInt2) {
    if (paramInt2 == 0) {
      center(paramInt1, 0, 3, 0, 0, 4, 0, 0.5F);
    } else {
      center(paramInt1, paramInt2, 4, 0, paramInt2, 3, 0, 0.5F);
    } 
  }
  
  public void centerVertically(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat) {
    connect(paramInt1, 3, paramInt2, paramInt3, paramInt4);
    connect(paramInt1, 4, paramInt5, paramInt6, paramInt7);
    ((Constraint)this.mConstraints.get(Integer.valueOf(paramInt1))).layout.verticalBias = paramFloat;
  }
  
  public void clear(int paramInt) {
    this.mConstraints.remove(Integer.valueOf(paramInt));
  }
  
  public void clear(int paramInt1, int paramInt2) {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt1))) {
      Constraint constraint = this.mConstraints.get(Integer.valueOf(paramInt1));
      switch (paramInt2) {
        default:
          throw new IllegalArgumentException("unknown constraint");
        case 7:
          constraint.layout.endToStart = -1;
          constraint.layout.endToEnd = -1;
          constraint.layout.endMargin = -1;
          constraint.layout.goneEndMargin = -1;
          return;
        case 6:
          constraint.layout.startToEnd = -1;
          constraint.layout.startToStart = -1;
          constraint.layout.startMargin = -1;
          constraint.layout.goneStartMargin = -1;
          return;
        case 5:
          constraint.layout.baselineToBaseline = -1;
          return;
        case 4:
          constraint.layout.bottomToTop = -1;
          constraint.layout.bottomToBottom = -1;
          constraint.layout.bottomMargin = -1;
          constraint.layout.goneBottomMargin = -1;
          return;
        case 3:
          constraint.layout.topToBottom = -1;
          constraint.layout.topToTop = -1;
          constraint.layout.topMargin = -1;
          constraint.layout.goneTopMargin = -1;
          return;
        case 2:
          constraint.layout.rightToRight = -1;
          constraint.layout.rightToLeft = -1;
          constraint.layout.rightMargin = -1;
          constraint.layout.goneRightMargin = -1;
          return;
        case 1:
          break;
      } 
      constraint.layout.leftToRight = -1;
      constraint.layout.leftToLeft = -1;
      constraint.layout.leftMargin = -1;
      constraint.layout.goneLeftMargin = -1;
    } 
  }
  
  public void clone(Context paramContext, int paramInt) {
    clone((ConstraintLayout)LayoutInflater.from(paramContext).inflate(paramInt, null));
  }
  
  public void clone(ConstraintLayout paramConstraintLayout) {
    int i = paramConstraintLayout.getChildCount();
    this.mConstraints.clear();
    byte b = 0;
    while (b < i) {
      View view = paramConstraintLayout.getChildAt(b);
      ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)view.getLayoutParams();
      int j = view.getId();
      if (!this.mForceId || j != -1) {
        if (!this.mConstraints.containsKey(Integer.valueOf(j)))
          this.mConstraints.put(Integer.valueOf(j), new Constraint()); 
        Constraint constraint = this.mConstraints.get(Integer.valueOf(j));
        constraint.mCustomConstraints = ConstraintAttribute.extractAttributes(this.mSavedAttributes, view);
        constraint.fillFrom(j, layoutParams);
        constraint.propertySet.visibility = view.getVisibility();
        if (Build.VERSION.SDK_INT >= 17) {
          constraint.propertySet.alpha = view.getAlpha();
          constraint.transform.rotation = view.getRotation();
          constraint.transform.rotationX = view.getRotationX();
          constraint.transform.rotationY = view.getRotationY();
          constraint.transform.scaleX = view.getScaleX();
          constraint.transform.scaleY = view.getScaleY();
          float f1 = view.getPivotX();
          float f2 = view.getPivotY();
          if (f1 != 0.0D || f2 != 0.0D) {
            constraint.transform.transformPivotX = f1;
            constraint.transform.transformPivotY = f2;
          } 
          constraint.transform.translationX = view.getTranslationX();
          constraint.transform.translationY = view.getTranslationY();
          if (Build.VERSION.SDK_INT >= 21) {
            constraint.transform.translationZ = view.getTranslationZ();
            if (constraint.transform.applyElevation)
              constraint.transform.elevation = view.getElevation(); 
          } 
        } 
        if (view instanceof Barrier) {
          Barrier barrier = (Barrier)view;
          constraint.layout.mBarrierAllowsGoneWidgets = barrier.allowsGoneWidget();
          constraint.layout.mReferenceIds = barrier.getReferencedIds();
          constraint.layout.mBarrierDirection = barrier.getType();
          constraint.layout.mBarrierMargin = barrier.getMargin();
        } 
        b++;
        continue;
      } 
      throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
    } 
  }
  
  public void clone(ConstraintSet paramConstraintSet) {
    this.mConstraints.clear();
    for (Integer integer : paramConstraintSet.mConstraints.keySet())
      this.mConstraints.put(integer, ((Constraint)paramConstraintSet.mConstraints.get(integer)).clone()); 
  }
  
  public void clone(Constraints paramConstraints) {
    int i = paramConstraints.getChildCount();
    this.mConstraints.clear();
    byte b = 0;
    while (b < i) {
      View view = paramConstraints.getChildAt(b);
      Constraints.LayoutParams layoutParams = (Constraints.LayoutParams)view.getLayoutParams();
      int j = view.getId();
      if (!this.mForceId || j != -1) {
        if (!this.mConstraints.containsKey(Integer.valueOf(j)))
          this.mConstraints.put(Integer.valueOf(j), new Constraint()); 
        Constraint constraint = this.mConstraints.get(Integer.valueOf(j));
        if (view instanceof ConstraintHelper)
          constraint.fillFromConstraints((ConstraintHelper)view, j, layoutParams); 
        constraint.fillFromConstraints(j, layoutParams);
        b++;
        continue;
      } 
      throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
    } 
  }
  
  public void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    StringBuilder stringBuilder;
    if (!this.mConstraints.containsKey(Integer.valueOf(paramInt1)))
      this.mConstraints.put(Integer.valueOf(paramInt1), new Constraint()); 
    Constraint constraint = this.mConstraints.get(Integer.valueOf(paramInt1));
    switch (paramInt2) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append(sideToString(paramInt2));
        stringBuilder.append(" to ");
        stringBuilder.append(sideToString(paramInt4));
        stringBuilder.append(" unknown");
        throw new IllegalArgumentException(stringBuilder.toString());
      case 7:
        if (paramInt4 == 7) {
          ((Constraint)stringBuilder).layout.endToEnd = paramInt3;
          ((Constraint)stringBuilder).layout.endToStart = -1;
        } else if (paramInt4 == 6) {
          ((Constraint)stringBuilder).layout.endToStart = paramInt3;
          ((Constraint)stringBuilder).layout.endToEnd = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return;
      case 6:
        if (paramInt4 == 6) {
          ((Constraint)stringBuilder).layout.startToStart = paramInt3;
          ((Constraint)stringBuilder).layout.startToEnd = -1;
        } else if (paramInt4 == 7) {
          ((Constraint)stringBuilder).layout.startToEnd = paramInt3;
          ((Constraint)stringBuilder).layout.startToStart = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return;
      case 5:
        if (paramInt4 == 5) {
          ((Constraint)stringBuilder).layout.baselineToBaseline = paramInt3;
          ((Constraint)stringBuilder).layout.bottomToBottom = -1;
          ((Constraint)stringBuilder).layout.bottomToTop = -1;
          ((Constraint)stringBuilder).layout.topToTop = -1;
          ((Constraint)stringBuilder).layout.topToBottom = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return;
      case 4:
        if (paramInt4 == 4) {
          ((Constraint)stringBuilder).layout.bottomToBottom = paramInt3;
          ((Constraint)stringBuilder).layout.bottomToTop = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else if (paramInt4 == 3) {
          ((Constraint)stringBuilder).layout.bottomToTop = paramInt3;
          ((Constraint)stringBuilder).layout.bottomToBottom = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return;
      case 3:
        if (paramInt4 == 3) {
          ((Constraint)stringBuilder).layout.topToTop = paramInt3;
          ((Constraint)stringBuilder).layout.topToBottom = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else if (paramInt4 == 4) {
          ((Constraint)stringBuilder).layout.topToBottom = paramInt3;
          ((Constraint)stringBuilder).layout.topToTop = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return;
      case 2:
        if (paramInt4 == 1) {
          ((Constraint)stringBuilder).layout.rightToLeft = paramInt3;
          ((Constraint)stringBuilder).layout.rightToRight = -1;
        } else if (paramInt4 == 2) {
          ((Constraint)stringBuilder).layout.rightToRight = paramInt3;
          ((Constraint)stringBuilder).layout.rightToLeft = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return;
      case 1:
        break;
    } 
    if (paramInt4 == 1) {
      ((Constraint)stringBuilder).layout.leftToLeft = paramInt3;
      ((Constraint)stringBuilder).layout.leftToRight = -1;
    } else {
      if (paramInt4 == 2) {
        ((Constraint)stringBuilder).layout.leftToRight = paramInt3;
        ((Constraint)stringBuilder).layout.leftToLeft = -1;
        return;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("left to ");
      stringBuilder.append(sideToString(paramInt4));
      stringBuilder.append(" undefined");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
  }
  
  public void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    StringBuilder stringBuilder;
    if (!this.mConstraints.containsKey(Integer.valueOf(paramInt1)))
      this.mConstraints.put(Integer.valueOf(paramInt1), new Constraint()); 
    Constraint constraint = this.mConstraints.get(Integer.valueOf(paramInt1));
    switch (paramInt2) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append(sideToString(paramInt2));
        stringBuilder.append(" to ");
        stringBuilder.append(sideToString(paramInt4));
        stringBuilder.append(" unknown");
        throw new IllegalArgumentException(stringBuilder.toString());
      case 7:
        if (paramInt4 == 7) {
          ((Constraint)stringBuilder).layout.endToEnd = paramInt3;
          ((Constraint)stringBuilder).layout.endToStart = -1;
        } else if (paramInt4 == 6) {
          ((Constraint)stringBuilder).layout.endToStart = paramInt3;
          ((Constraint)stringBuilder).layout.endToEnd = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        ((Constraint)stringBuilder).layout.endMargin = paramInt5;
        return;
      case 6:
        if (paramInt4 == 6) {
          ((Constraint)stringBuilder).layout.startToStart = paramInt3;
          ((Constraint)stringBuilder).layout.startToEnd = -1;
        } else if (paramInt4 == 7) {
          ((Constraint)stringBuilder).layout.startToEnd = paramInt3;
          ((Constraint)stringBuilder).layout.startToStart = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        ((Constraint)stringBuilder).layout.startMargin = paramInt5;
        return;
      case 5:
        if (paramInt4 == 5) {
          ((Constraint)stringBuilder).layout.baselineToBaseline = paramInt3;
          ((Constraint)stringBuilder).layout.bottomToBottom = -1;
          ((Constraint)stringBuilder).layout.bottomToTop = -1;
          ((Constraint)stringBuilder).layout.topToTop = -1;
          ((Constraint)stringBuilder).layout.topToBottom = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return;
      case 4:
        if (paramInt4 == 4) {
          ((Constraint)stringBuilder).layout.bottomToBottom = paramInt3;
          ((Constraint)stringBuilder).layout.bottomToTop = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else if (paramInt4 == 3) {
          ((Constraint)stringBuilder).layout.bottomToTop = paramInt3;
          ((Constraint)stringBuilder).layout.bottomToBottom = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        ((Constraint)stringBuilder).layout.bottomMargin = paramInt5;
        return;
      case 3:
        if (paramInt4 == 3) {
          ((Constraint)stringBuilder).layout.topToTop = paramInt3;
          ((Constraint)stringBuilder).layout.topToBottom = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else if (paramInt4 == 4) {
          ((Constraint)stringBuilder).layout.topToBottom = paramInt3;
          ((Constraint)stringBuilder).layout.topToTop = -1;
          ((Constraint)stringBuilder).layout.baselineToBaseline = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        ((Constraint)stringBuilder).layout.topMargin = paramInt5;
        return;
      case 2:
        if (paramInt4 == 1) {
          ((Constraint)stringBuilder).layout.rightToLeft = paramInt3;
          ((Constraint)stringBuilder).layout.rightToRight = -1;
        } else if (paramInt4 == 2) {
          ((Constraint)stringBuilder).layout.rightToRight = paramInt3;
          ((Constraint)stringBuilder).layout.rightToLeft = -1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("right to ");
          stringBuilder.append(sideToString(paramInt4));
          stringBuilder.append(" undefined");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        ((Constraint)stringBuilder).layout.rightMargin = paramInt5;
        return;
      case 1:
        break;
    } 
    if (paramInt4 == 1) {
      ((Constraint)stringBuilder).layout.leftToLeft = paramInt3;
      ((Constraint)stringBuilder).layout.leftToRight = -1;
    } else if (paramInt4 == 2) {
      ((Constraint)stringBuilder).layout.leftToRight = paramInt3;
      ((Constraint)stringBuilder).layout.leftToLeft = -1;
    } else {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Left to ");
      stringBuilder.append(sideToString(paramInt4));
      stringBuilder.append(" undefined");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    ((Constraint)stringBuilder).layout.leftMargin = paramInt5;
  }
  
  public void constrainCircle(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
    Constraint constraint = get(paramInt1);
    constraint.layout.circleConstraint = paramInt2;
    constraint.layout.circleRadius = paramInt3;
    constraint.layout.circleAngle = paramFloat;
  }
  
  public void constrainDefaultHeight(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.heightDefault = paramInt2;
  }
  
  public void constrainDefaultWidth(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.widthDefault = paramInt2;
  }
  
  public void constrainHeight(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.mHeight = paramInt2;
  }
  
  public void constrainMaxHeight(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.heightMax = paramInt2;
  }
  
  public void constrainMaxWidth(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.widthMax = paramInt2;
  }
  
  public void constrainMinHeight(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.heightMin = paramInt2;
  }
  
  public void constrainMinWidth(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.widthMin = paramInt2;
  }
  
  public void constrainPercentHeight(int paramInt, float paramFloat) {
    (get(paramInt)).layout.heightPercent = paramFloat;
  }
  
  public void constrainPercentWidth(int paramInt, float paramFloat) {
    (get(paramInt)).layout.widthPercent = paramFloat;
  }
  
  public void constrainWidth(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.mWidth = paramInt2;
  }
  
  public void constrainedHeight(int paramInt, boolean paramBoolean) {
    (get(paramInt)).layout.constrainedHeight = paramBoolean;
  }
  
  public void constrainedWidth(int paramInt, boolean paramBoolean) {
    (get(paramInt)).layout.constrainedWidth = paramBoolean;
  }
  
  public void create(int paramInt1, int paramInt2) {
    Constraint constraint = get(paramInt1);
    constraint.layout.mIsGuideline = true;
    constraint.layout.orientation = paramInt2;
  }
  
  public void createBarrier(int paramInt1, int paramInt2, int paramInt3, int... paramVarArgs) {
    Constraint constraint = get(paramInt1);
    constraint.layout.mHelperType = 1;
    constraint.layout.mBarrierDirection = paramInt2;
    constraint.layout.mBarrierMargin = paramInt3;
    constraint.layout.mIsGuideline = false;
    constraint.layout.mReferenceIds = paramVarArgs;
  }
  
  public void createHorizontalChain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, float[] paramArrayOffloat, int paramInt5) {
    createHorizontalChain(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, paramArrayOffloat, paramInt5, 1, 2);
  }
  
  public void createHorizontalChainRtl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, float[] paramArrayOffloat, int paramInt5) {
    createHorizontalChain(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, paramArrayOffloat, paramInt5, 6, 7);
  }
  
  public void createVerticalChain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, float[] paramArrayOffloat, int paramInt5) {
    if (paramArrayOfint.length >= 2) {
      if (paramArrayOffloat == null || paramArrayOffloat.length == paramArrayOfint.length) {
        if (paramArrayOffloat != null)
          (get(paramArrayOfint[0])).layout.verticalWeight = paramArrayOffloat[0]; 
        (get(paramArrayOfint[0])).layout.verticalChainStyle = paramInt5;
        connect(paramArrayOfint[0], 3, paramInt1, paramInt2, 0);
        for (paramInt1 = 1; paramInt1 < paramArrayOfint.length; paramInt1++) {
          paramInt2 = paramArrayOfint[paramInt1];
          paramInt5 = paramArrayOfint[paramInt1];
          paramInt2 = paramInt1 - 1;
          connect(paramInt5, 3, paramArrayOfint[paramInt2], 4, 0);
          connect(paramArrayOfint[paramInt2], 4, paramArrayOfint[paramInt1], 3, 0);
          if (paramArrayOffloat != null)
            (get(paramArrayOfint[paramInt1])).layout.verticalWeight = paramArrayOffloat[paramInt1]; 
        } 
        connect(paramArrayOfint[paramArrayOfint.length - 1], 4, paramInt3, paramInt4, 0);
        return;
      } 
      throw new IllegalArgumentException("must have 2 or more widgets in a chain");
    } 
    throw new IllegalArgumentException("must have 2 or more widgets in a chain");
  }
  
  public void dump(MotionScene paramMotionScene, int... paramVarArgs) {
    Set<Integer> set = this.mConstraints.keySet();
    int i = paramVarArgs.length;
    boolean bool = false;
    if (i != 0) {
      HashSet<Integer> hashSet = new HashSet();
      int k = paramVarArgs.length;
      i = 0;
      while (true) {
        set = hashSet;
        if (i < k) {
          hashSet.add(Integer.valueOf(paramVarArgs[i]));
          i++;
          continue;
        } 
        break;
      } 
    } else {
      set = new HashSet<Integer>(set);
    } 
    PrintStream printStream = System.out;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(set.size());
    stringBuilder2.append(" constraints");
    printStream.println(stringBuilder2.toString());
    StringBuilder stringBuilder1 = new StringBuilder();
    Integer[] arrayOfInteger = (Integer[])set.toArray((Object[])new Integer[0]);
    int j = arrayOfInteger.length;
    for (i = bool; i < j; i++) {
      Integer integer = arrayOfInteger[i];
      Constraint constraint = this.mConstraints.get(integer);
      stringBuilder1.append("<Constraint id=");
      stringBuilder1.append(integer);
      stringBuilder1.append(" \n");
      constraint.layout.dump(paramMotionScene, stringBuilder1);
      stringBuilder1.append("/>\n");
    } 
    System.out.println(stringBuilder1.toString());
  }
  
  public boolean getApplyElevation(int paramInt) {
    return (get(paramInt)).transform.applyElevation;
  }
  
  public Constraint getConstraint(int paramInt) {
    return this.mConstraints.containsKey(Integer.valueOf(paramInt)) ? this.mConstraints.get(Integer.valueOf(paramInt)) : null;
  }
  
  public HashMap<String, ConstraintAttribute> getCustomAttributeSet() {
    return this.mSavedAttributes;
  }
  
  public int getHeight(int paramInt) {
    return (get(paramInt)).layout.mHeight;
  }
  
  public int[] getKnownIds() {
    Set<Integer> set = this.mConstraints.keySet();
    byte b = 0;
    Integer[] arrayOfInteger = set.<Integer>toArray(new Integer[0]);
    int i = arrayOfInteger.length;
    int[] arrayOfInt = new int[i];
    while (b < i) {
      arrayOfInt[b] = arrayOfInteger[b].intValue();
      b++;
    } 
    return arrayOfInt;
  }
  
  public Constraint getParameters(int paramInt) {
    return get(paramInt);
  }
  
  public int[] getReferencedIds(int paramInt) {
    Constraint constraint = get(paramInt);
    return (constraint.layout.mReferenceIds == null) ? new int[0] : Arrays.copyOf(constraint.layout.mReferenceIds, constraint.layout.mReferenceIds.length);
  }
  
  public int getVisibility(int paramInt) {
    return (get(paramInt)).propertySet.visibility;
  }
  
  public int getVisibilityMode(int paramInt) {
    return (get(paramInt)).propertySet.mVisibilityMode;
  }
  
  public int getWidth(int paramInt) {
    return (get(paramInt)).layout.mWidth;
  }
  
  public boolean isForceId() {
    return this.mForceId;
  }
  
  public void load(Context paramContext, int paramInt) {
    XmlResourceParser xmlResourceParser = paramContext.getResources().getXml(paramInt);
    try {
      for (paramInt = xmlResourceParser.getEventType(); paramInt != 1; paramInt = xmlResourceParser.next()) {
        if (paramInt != 0) {
          if (paramInt == 2) {
            String str = xmlResourceParser.getName();
            Constraint constraint = fillFromAttributeList(paramContext, Xml.asAttributeSet((XmlPullParser)xmlResourceParser));
            if (str.equalsIgnoreCase("Guideline"))
              constraint.layout.mIsGuideline = true; 
            this.mConstraints.put(Integer.valueOf(constraint.mViewId), constraint);
          } 
        } else {
          xmlResourceParser.getName();
        } 
      } 
    } catch (XmlPullParserException xmlPullParserException) {
      xmlPullParserException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public void load(Context paramContext, XmlPullParser paramXmlPullParser) {
    try {
      int i = paramXmlPullParser.getEventType();
      for (Constraint constraint = null; i != 1; constraint = constraint1) {
        Constraint constraint1;
        if (i != 0) {
          byte b = 3;
          if (i != 2) {
            if (i != 3) {
              constraint1 = constraint;
            } else {
              String str = paramXmlPullParser.getName();
              if ("ConstraintSet".equals(str))
                return; 
              constraint1 = constraint;
              if (str.equalsIgnoreCase("Constraint")) {
                this.mConstraints.put(Integer.valueOf(constraint.mViewId), constraint);
                constraint1 = null;
              } 
            } 
          } else {
            RuntimeException runtimeException2;
            StringBuilder stringBuilder1;
            RuntimeException runtimeException1;
            boolean bool;
            Constraint constraint2;
            StringBuilder stringBuilder3;
            RuntimeException runtimeException3;
            StringBuilder stringBuilder2;
            String str = paramXmlPullParser.getName();
            switch (str.hashCode()) {
              default:
                i = -1;
                break;
              case 1803088381:
                if (str.equals("Constraint")) {
                  i = 0;
                  break;
                } 
              case 1791837707:
                if (str.equals("CustomAttribute")) {
                  i = 7;
                  break;
                } 
              case 1331510167:
                if (str.equals("Barrier")) {
                  i = 2;
                  break;
                } 
              case -71750448:
                if (str.equals("Guideline")) {
                  i = 1;
                  break;
                } 
              case -1238332596:
                if (str.equals("Transform")) {
                  i = 4;
                  break;
                } 
              case -1269513683:
                if (str.equals("PropertySet")) {
                  i = b;
                  break;
                } 
              case -1984451626:
                if (str.equals("Motion")) {
                  i = 6;
                  break;
                } 
              case -2025855158:
                bool = str.equals("Layout");
                if (bool) {
                  i = 5;
                  break;
                } 
            } 
            switch (i) {
              default:
                constraint2 = constraint;
                break;
              case 7:
                if (constraint != null) {
                  ConstraintAttribute.parse(paramContext, paramXmlPullParser, constraint.mCustomConstraints);
                  constraint2 = constraint;
                  break;
                } 
                runtimeException2 = new RuntimeException();
                stringBuilder3 = new StringBuilder();
                this();
                stringBuilder3.append("XML parser error must be within a Constraint ");
                stringBuilder3.append(paramXmlPullParser.getLineNumber());
                this(stringBuilder3.toString());
                throw runtimeException2;
              case 6:
                if (constraint != null) {
                  constraint.motion.fillFromAttributeList((Context)runtimeException2, Xml.asAttributeSet(paramXmlPullParser));
                  Constraint constraint3 = constraint;
                  break;
                } 
                runtimeException2 = new RuntimeException();
                stringBuilder3 = new StringBuilder();
                this();
                stringBuilder3.append("XML parser error must be within a Constraint ");
                stringBuilder3.append(paramXmlPullParser.getLineNumber());
                this(stringBuilder3.toString());
                throw runtimeException2;
              case 5:
                if (constraint != null) {
                  constraint.layout.fillFromAttributeList((Context)runtimeException2, Xml.asAttributeSet(paramXmlPullParser));
                  Constraint constraint3 = constraint;
                  break;
                } 
                runtimeException2 = new RuntimeException();
                stringBuilder3 = new StringBuilder();
                this();
                stringBuilder3.append("XML parser error must be within a Constraint ");
                stringBuilder3.append(paramXmlPullParser.getLineNumber());
                this(stringBuilder3.toString());
                throw runtimeException2;
              case 4:
                if (constraint != null) {
                  constraint.transform.fillFromAttributeList((Context)runtimeException2, Xml.asAttributeSet(paramXmlPullParser));
                  Constraint constraint3 = constraint;
                  break;
                } 
                runtimeException3 = new RuntimeException();
                stringBuilder1 = new StringBuilder();
                this();
                stringBuilder1.append("XML parser error must be within a Constraint ");
                stringBuilder1.append(paramXmlPullParser.getLineNumber());
                this(stringBuilder1.toString());
                throw runtimeException3;
              case 3:
                if (constraint != null) {
                  constraint.propertySet.fillFromAttributeList((Context)stringBuilder1, Xml.asAttributeSet(paramXmlPullParser));
                  Constraint constraint3 = constraint;
                  break;
                } 
                runtimeException1 = new RuntimeException();
                stringBuilder2 = new StringBuilder();
                this();
                stringBuilder2.append("XML parser error must be within a Constraint ");
                stringBuilder2.append(paramXmlPullParser.getLineNumber());
                this(stringBuilder2.toString());
                throw runtimeException1;
              case 2:
                constraint1 = fillFromAttributeList((Context)runtimeException1, Xml.asAttributeSet(paramXmlPullParser));
                constraint1.layout.mHelperType = 1;
                break;
              case 1:
                constraint1 = fillFromAttributeList((Context)runtimeException1, Xml.asAttributeSet(paramXmlPullParser));
                constraint1.layout.mIsGuideline = true;
                constraint1.layout.mApply = true;
                break;
              case 0:
                constraint1 = fillFromAttributeList((Context)runtimeException1, Xml.asAttributeSet(paramXmlPullParser));
                break;
            } 
          } 
        } else {
          paramXmlPullParser.getName();
          constraint1 = constraint;
        } 
        i = paramXmlPullParser.next();
      } 
    } catch (XmlPullParserException xmlPullParserException) {
      xmlPullParserException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public void parseColorAttributes(Constraint paramConstraint, String paramString) {
    String[] arrayOfString = paramString.split(",");
    for (byte b = 0; b < arrayOfString.length; b++) {
      StringBuilder stringBuilder;
      String[] arrayOfString1 = arrayOfString[b].split("=");
      if (arrayOfString1.length != 2) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(" Unable to parse ");
        stringBuilder.append(arrayOfString[b]);
        Log.w("ConstraintSet", stringBuilder.toString());
      } else {
        paramConstraint.setColorValue((String)stringBuilder[0], Color.parseColor((String)stringBuilder[1]));
      } 
    } 
  }
  
  public void parseFloatAttributes(Constraint paramConstraint, String paramString) {
    String[] arrayOfString = paramString.split(",");
    for (byte b = 0; b < arrayOfString.length; b++) {
      StringBuilder stringBuilder;
      String[] arrayOfString1 = arrayOfString[b].split("=");
      if (arrayOfString1.length != 2) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(" Unable to parse ");
        stringBuilder.append(arrayOfString[b]);
        Log.w("ConstraintSet", stringBuilder.toString());
      } else {
        paramConstraint.setFloatValue((String)stringBuilder[0], Float.parseFloat((String)stringBuilder[1]));
      } 
    } 
  }
  
  public void parseIntAttributes(Constraint paramConstraint, String paramString) {
    String[] arrayOfString = paramString.split(",");
    for (byte b = 0; b < arrayOfString.length; b++) {
      StringBuilder stringBuilder;
      String[] arrayOfString1 = arrayOfString[b].split("=");
      if (arrayOfString1.length != 2) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(" Unable to parse ");
        stringBuilder.append(arrayOfString[b]);
        Log.w("ConstraintSet", stringBuilder.toString());
      } else {
        paramConstraint.setFloatValue((String)stringBuilder[0], Integer.decode((String)stringBuilder[1]).intValue());
      } 
    } 
  }
  
  public void parseStringAttributes(Constraint paramConstraint, String paramString) {
    String[] arrayOfString = splitString(paramString);
    for (byte b = 0; b < arrayOfString.length; b++) {
      String[] arrayOfString1 = arrayOfString[b].split("=");
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(" Unable to parse ");
      stringBuilder.append(arrayOfString[b]);
      Log.w("ConstraintSet", stringBuilder.toString());
      paramConstraint.setStringValue(arrayOfString1[0], arrayOfString1[1]);
    } 
  }
  
  public void readFallback(ConstraintLayout paramConstraintLayout) {
    int i = paramConstraintLayout.getChildCount();
    byte b = 0;
    while (b < i) {
      View view = paramConstraintLayout.getChildAt(b);
      ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)view.getLayoutParams();
      int j = view.getId();
      if (!this.mForceId || j != -1) {
        if (!this.mConstraints.containsKey(Integer.valueOf(j)))
          this.mConstraints.put(Integer.valueOf(j), new Constraint()); 
        Constraint constraint = this.mConstraints.get(Integer.valueOf(j));
        if (!constraint.layout.mApply) {
          constraint.fillFrom(j, layoutParams);
          if (view instanceof ConstraintHelper) {
            constraint.layout.mReferenceIds = ((ConstraintHelper)view).getReferencedIds();
            if (view instanceof Barrier) {
              Barrier barrier = (Barrier)view;
              constraint.layout.mBarrierAllowsGoneWidgets = barrier.allowsGoneWidget();
              constraint.layout.mBarrierDirection = barrier.getType();
              constraint.layout.mBarrierMargin = barrier.getMargin();
            } 
          } 
          constraint.layout.mApply = true;
        } 
        if (!constraint.propertySet.mApply) {
          constraint.propertySet.visibility = view.getVisibility();
          constraint.propertySet.alpha = view.getAlpha();
          constraint.propertySet.mApply = true;
        } 
        if (Build.VERSION.SDK_INT >= 17 && !constraint.transform.mApply) {
          constraint.transform.mApply = true;
          constraint.transform.rotation = view.getRotation();
          constraint.transform.rotationX = view.getRotationX();
          constraint.transform.rotationY = view.getRotationY();
          constraint.transform.scaleX = view.getScaleX();
          constraint.transform.scaleY = view.getScaleY();
          float f1 = view.getPivotX();
          float f2 = view.getPivotY();
          if (f1 != 0.0D || f2 != 0.0D) {
            constraint.transform.transformPivotX = f1;
            constraint.transform.transformPivotY = f2;
          } 
          constraint.transform.translationX = view.getTranslationX();
          constraint.transform.translationY = view.getTranslationY();
          if (Build.VERSION.SDK_INT >= 21) {
            constraint.transform.translationZ = view.getTranslationZ();
            if (constraint.transform.applyElevation)
              constraint.transform.elevation = view.getElevation(); 
          } 
        } 
        b++;
        continue;
      } 
      throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
    } 
  }
  
  public void readFallback(ConstraintSet paramConstraintSet) {
    for (Integer integer : paramConstraintSet.mConstraints.keySet()) {
      int i = integer.intValue();
      Constraint constraint1 = paramConstraintSet.mConstraints.get(integer);
      if (!this.mConstraints.containsKey(Integer.valueOf(i)))
        this.mConstraints.put(Integer.valueOf(i), new Constraint()); 
      Constraint constraint2 = this.mConstraints.get(Integer.valueOf(i));
      if (!constraint2.layout.mApply)
        constraint2.layout.copyFrom(constraint1.layout); 
      if (!constraint2.propertySet.mApply)
        constraint2.propertySet.copyFrom(constraint1.propertySet); 
      if (!constraint2.transform.mApply)
        constraint2.transform.copyFrom(constraint1.transform); 
      if (!constraint2.motion.mApply)
        constraint2.motion.copyFrom(constraint1.motion); 
      for (String str : constraint1.mCustomConstraints.keySet()) {
        if (!constraint2.mCustomConstraints.containsKey(str))
          constraint2.mCustomConstraints.put(str, constraint1.mCustomConstraints.get(str)); 
      } 
    } 
  }
  
  public void removeAttribute(String paramString) {
    this.mSavedAttributes.remove(paramString);
  }
  
  public void removeFromHorizontalChain(int paramInt) {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt))) {
      Constraint constraint = this.mConstraints.get(Integer.valueOf(paramInt));
      int i = constraint.layout.leftToRight;
      int j = constraint.layout.rightToLeft;
      if (i != -1 || j != -1) {
        if (i != -1 && j != -1) {
          connect(i, 2, j, 1, 0);
          connect(j, 1, i, 2, 0);
        } else if (i != -1 || j != -1) {
          if (constraint.layout.rightToRight != -1) {
            connect(i, 2, constraint.layout.rightToRight, 2, 0);
          } else if (constraint.layout.leftToLeft != -1) {
            connect(j, 1, constraint.layout.leftToLeft, 1, 0);
          } 
        } 
        clear(paramInt, 1);
        clear(paramInt, 2);
        return;
      } 
      int k = constraint.layout.startToEnd;
      j = constraint.layout.endToStart;
      if (k != -1 || j != -1)
        if (k != -1 && j != -1) {
          connect(k, 7, j, 6, 0);
          connect(j, 6, i, 7, 0);
        } else if (i != -1 || j != -1) {
          if (constraint.layout.rightToRight != -1) {
            connect(i, 7, constraint.layout.rightToRight, 7, 0);
          } else if (constraint.layout.leftToLeft != -1) {
            connect(j, 6, constraint.layout.leftToLeft, 6, 0);
          } 
        }  
      clear(paramInt, 6);
      clear(paramInt, 7);
    } 
  }
  
  public void removeFromVerticalChain(int paramInt) {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt))) {
      Constraint constraint = this.mConstraints.get(Integer.valueOf(paramInt));
      int j = constraint.layout.topToBottom;
      int i = constraint.layout.bottomToTop;
      if (j != -1 || i != -1)
        if (j != -1 && i != -1) {
          connect(j, 4, i, 3, 0);
          connect(i, 3, j, 4, 0);
        } else if (j != -1 || i != -1) {
          if (constraint.layout.bottomToBottom != -1) {
            connect(j, 4, constraint.layout.bottomToBottom, 4, 0);
          } else if (constraint.layout.topToTop != -1) {
            connect(i, 3, constraint.layout.topToTop, 3, 0);
          } 
        }  
    } 
    clear(paramInt, 3);
    clear(paramInt, 4);
  }
  
  public void setAlpha(int paramInt, float paramFloat) {
    (get(paramInt)).propertySet.alpha = paramFloat;
  }
  
  public void setApplyElevation(int paramInt, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 21)
      (get(paramInt)).transform.applyElevation = paramBoolean; 
  }
  
  public void setBarrierType(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.mHelperType = paramInt2;
  }
  
  public void setColorValue(int paramInt1, String paramString, int paramInt2) {
    get(paramInt1).setColorValue(paramString, paramInt2);
  }
  
  public void setDimensionRatio(int paramInt, String paramString) {
    (get(paramInt)).layout.dimensionRatio = paramString;
  }
  
  public void setEditorAbsoluteX(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.editorAbsoluteX = paramInt2;
  }
  
  public void setEditorAbsoluteY(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.editorAbsoluteY = paramInt2;
  }
  
  public void setElevation(int paramInt, float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21) {
      (get(paramInt)).transform.elevation = paramFloat;
      (get(paramInt)).transform.applyElevation = true;
    } 
  }
  
  public void setFloatValue(int paramInt, String paramString, float paramFloat) {
    get(paramInt).setFloatValue(paramString, paramFloat);
  }
  
  public void setForceId(boolean paramBoolean) {
    this.mForceId = paramBoolean;
  }
  
  public void setGoneMargin(int paramInt1, int paramInt2, int paramInt3) {
    Constraint constraint = get(paramInt1);
    switch (paramInt2) {
      default:
        throw new IllegalArgumentException("unknown constraint");
      case 7:
        constraint.layout.goneEndMargin = paramInt3;
        return;
      case 6:
        constraint.layout.goneStartMargin = paramInt3;
        return;
      case 5:
        throw new IllegalArgumentException("baseline does not support margins");
      case 4:
        constraint.layout.goneBottomMargin = paramInt3;
        return;
      case 3:
        constraint.layout.goneTopMargin = paramInt3;
        return;
      case 2:
        constraint.layout.goneRightMargin = paramInt3;
        return;
      case 1:
        break;
    } 
    constraint.layout.goneLeftMargin = paramInt3;
  }
  
  public void setGuidelineBegin(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.guideBegin = paramInt2;
    (get(paramInt1)).layout.guideEnd = -1;
    (get(paramInt1)).layout.guidePercent = -1.0F;
  }
  
  public void setGuidelineEnd(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.guideEnd = paramInt2;
    (get(paramInt1)).layout.guideBegin = -1;
    (get(paramInt1)).layout.guidePercent = -1.0F;
  }
  
  public void setGuidelinePercent(int paramInt, float paramFloat) {
    (get(paramInt)).layout.guidePercent = paramFloat;
    (get(paramInt)).layout.guideEnd = -1;
    (get(paramInt)).layout.guideBegin = -1;
  }
  
  public void setHorizontalBias(int paramInt, float paramFloat) {
    (get(paramInt)).layout.horizontalBias = paramFloat;
  }
  
  public void setHorizontalChainStyle(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.horizontalChainStyle = paramInt2;
  }
  
  public void setHorizontalWeight(int paramInt, float paramFloat) {
    (get(paramInt)).layout.horizontalWeight = paramFloat;
  }
  
  public void setIntValue(int paramInt1, String paramString, int paramInt2) {
    get(paramInt1).setIntValue(paramString, paramInt2);
  }
  
  public void setMargin(int paramInt1, int paramInt2, int paramInt3) {
    Constraint constraint = get(paramInt1);
    switch (paramInt2) {
      default:
        throw new IllegalArgumentException("unknown constraint");
      case 7:
        constraint.layout.endMargin = paramInt3;
        return;
      case 6:
        constraint.layout.startMargin = paramInt3;
        return;
      case 5:
        throw new IllegalArgumentException("baseline does not support margins");
      case 4:
        constraint.layout.bottomMargin = paramInt3;
        return;
      case 3:
        constraint.layout.topMargin = paramInt3;
        return;
      case 2:
        constraint.layout.rightMargin = paramInt3;
        return;
      case 1:
        break;
    } 
    constraint.layout.leftMargin = paramInt3;
  }
  
  public void setReferencedIds(int paramInt, int... paramVarArgs) {
    (get(paramInt)).layout.mReferenceIds = paramVarArgs;
  }
  
  public void setRotation(int paramInt, float paramFloat) {
    (get(paramInt)).transform.rotation = paramFloat;
  }
  
  public void setRotationX(int paramInt, float paramFloat) {
    (get(paramInt)).transform.rotationX = paramFloat;
  }
  
  public void setRotationY(int paramInt, float paramFloat) {
    (get(paramInt)).transform.rotationY = paramFloat;
  }
  
  public void setScaleX(int paramInt, float paramFloat) {
    (get(paramInt)).transform.scaleX = paramFloat;
  }
  
  public void setScaleY(int paramInt, float paramFloat) {
    (get(paramInt)).transform.scaleY = paramFloat;
  }
  
  public void setStringValue(int paramInt, String paramString1, String paramString2) {
    get(paramInt).setStringValue(paramString1, paramString2);
  }
  
  public void setTransformPivot(int paramInt, float paramFloat1, float paramFloat2) {
    Constraint constraint = get(paramInt);
    constraint.transform.transformPivotY = paramFloat2;
    constraint.transform.transformPivotX = paramFloat1;
  }
  
  public void setTransformPivotX(int paramInt, float paramFloat) {
    (get(paramInt)).transform.transformPivotX = paramFloat;
  }
  
  public void setTransformPivotY(int paramInt, float paramFloat) {
    (get(paramInt)).transform.transformPivotY = paramFloat;
  }
  
  public void setTranslation(int paramInt, float paramFloat1, float paramFloat2) {
    Constraint constraint = get(paramInt);
    constraint.transform.translationX = paramFloat1;
    constraint.transform.translationY = paramFloat2;
  }
  
  public void setTranslationX(int paramInt, float paramFloat) {
    (get(paramInt)).transform.translationX = paramFloat;
  }
  
  public void setTranslationY(int paramInt, float paramFloat) {
    (get(paramInt)).transform.translationY = paramFloat;
  }
  
  public void setTranslationZ(int paramInt, float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21)
      (get(paramInt)).transform.translationZ = paramFloat; 
  }
  
  public void setValidateOnParse(boolean paramBoolean) {
    this.mValidate = paramBoolean;
  }
  
  public void setVerticalBias(int paramInt, float paramFloat) {
    (get(paramInt)).layout.verticalBias = paramFloat;
  }
  
  public void setVerticalChainStyle(int paramInt1, int paramInt2) {
    (get(paramInt1)).layout.verticalChainStyle = paramInt2;
  }
  
  public void setVerticalWeight(int paramInt, float paramFloat) {
    (get(paramInt)).layout.verticalWeight = paramFloat;
  }
  
  public void setVisibility(int paramInt1, int paramInt2) {
    (get(paramInt1)).propertySet.visibility = paramInt2;
  }
  
  public void setVisibilityMode(int paramInt1, int paramInt2) {
    (get(paramInt1)).propertySet.mVisibilityMode = paramInt2;
  }
  
  public static class Constraint {
    public final ConstraintSet.Layout layout = new ConstraintSet.Layout();
    
    public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap<String, ConstraintAttribute>();
    
    int mViewId;
    
    public final ConstraintSet.Motion motion = new ConstraintSet.Motion();
    
    public final ConstraintSet.PropertySet propertySet = new ConstraintSet.PropertySet();
    
    public final ConstraintSet.Transform transform = new ConstraintSet.Transform();
    
    private void fillFrom(int param1Int, ConstraintLayout.LayoutParams param1LayoutParams) {
      this.mViewId = param1Int;
      this.layout.leftToLeft = param1LayoutParams.leftToLeft;
      this.layout.leftToRight = param1LayoutParams.leftToRight;
      this.layout.rightToLeft = param1LayoutParams.rightToLeft;
      this.layout.rightToRight = param1LayoutParams.rightToRight;
      this.layout.topToTop = param1LayoutParams.topToTop;
      this.layout.topToBottom = param1LayoutParams.topToBottom;
      this.layout.bottomToTop = param1LayoutParams.bottomToTop;
      this.layout.bottomToBottom = param1LayoutParams.bottomToBottom;
      this.layout.baselineToBaseline = param1LayoutParams.baselineToBaseline;
      this.layout.startToEnd = param1LayoutParams.startToEnd;
      this.layout.startToStart = param1LayoutParams.startToStart;
      this.layout.endToStart = param1LayoutParams.endToStart;
      this.layout.endToEnd = param1LayoutParams.endToEnd;
      this.layout.horizontalBias = param1LayoutParams.horizontalBias;
      this.layout.verticalBias = param1LayoutParams.verticalBias;
      this.layout.dimensionRatio = param1LayoutParams.dimensionRatio;
      this.layout.circleConstraint = param1LayoutParams.circleConstraint;
      this.layout.circleRadius = param1LayoutParams.circleRadius;
      this.layout.circleAngle = param1LayoutParams.circleAngle;
      this.layout.editorAbsoluteX = param1LayoutParams.editorAbsoluteX;
      this.layout.editorAbsoluteY = param1LayoutParams.editorAbsoluteY;
      this.layout.orientation = param1LayoutParams.orientation;
      this.layout.guidePercent = param1LayoutParams.guidePercent;
      this.layout.guideBegin = param1LayoutParams.guideBegin;
      this.layout.guideEnd = param1LayoutParams.guideEnd;
      this.layout.mWidth = param1LayoutParams.width;
      this.layout.mHeight = param1LayoutParams.height;
      this.layout.leftMargin = param1LayoutParams.leftMargin;
      this.layout.rightMargin = param1LayoutParams.rightMargin;
      this.layout.topMargin = param1LayoutParams.topMargin;
      this.layout.bottomMargin = param1LayoutParams.bottomMargin;
      this.layout.verticalWeight = param1LayoutParams.verticalWeight;
      this.layout.horizontalWeight = param1LayoutParams.horizontalWeight;
      this.layout.verticalChainStyle = param1LayoutParams.verticalChainStyle;
      this.layout.horizontalChainStyle = param1LayoutParams.horizontalChainStyle;
      this.layout.constrainedWidth = param1LayoutParams.constrainedWidth;
      this.layout.constrainedHeight = param1LayoutParams.constrainedHeight;
      this.layout.widthDefault = param1LayoutParams.matchConstraintDefaultWidth;
      this.layout.heightDefault = param1LayoutParams.matchConstraintDefaultHeight;
      this.layout.widthMax = param1LayoutParams.matchConstraintMaxWidth;
      this.layout.heightMax = param1LayoutParams.matchConstraintMaxHeight;
      this.layout.widthMin = param1LayoutParams.matchConstraintMinWidth;
      this.layout.heightMin = param1LayoutParams.matchConstraintMinHeight;
      this.layout.widthPercent = param1LayoutParams.matchConstraintPercentWidth;
      this.layout.heightPercent = param1LayoutParams.matchConstraintPercentHeight;
      this.layout.mConstraintTag = param1LayoutParams.constraintTag;
      this.layout.goneTopMargin = param1LayoutParams.goneTopMargin;
      this.layout.goneBottomMargin = param1LayoutParams.goneBottomMargin;
      this.layout.goneLeftMargin = param1LayoutParams.goneLeftMargin;
      this.layout.goneRightMargin = param1LayoutParams.goneRightMargin;
      this.layout.goneStartMargin = param1LayoutParams.goneStartMargin;
      this.layout.goneEndMargin = param1LayoutParams.goneEndMargin;
      if (Build.VERSION.SDK_INT >= 17) {
        this.layout.endMargin = param1LayoutParams.getMarginEnd();
        this.layout.startMargin = param1LayoutParams.getMarginStart();
      } 
    }
    
    private void fillFromConstraints(int param1Int, Constraints.LayoutParams param1LayoutParams) {
      fillFrom(param1Int, param1LayoutParams);
      this.propertySet.alpha = param1LayoutParams.alpha;
      this.transform.rotation = param1LayoutParams.rotation;
      this.transform.rotationX = param1LayoutParams.rotationX;
      this.transform.rotationY = param1LayoutParams.rotationY;
      this.transform.scaleX = param1LayoutParams.scaleX;
      this.transform.scaleY = param1LayoutParams.scaleY;
      this.transform.transformPivotX = param1LayoutParams.transformPivotX;
      this.transform.transformPivotY = param1LayoutParams.transformPivotY;
      this.transform.translationX = param1LayoutParams.translationX;
      this.transform.translationY = param1LayoutParams.translationY;
      this.transform.translationZ = param1LayoutParams.translationZ;
      this.transform.elevation = param1LayoutParams.elevation;
      this.transform.applyElevation = param1LayoutParams.applyElevation;
    }
    
    private void fillFromConstraints(ConstraintHelper param1ConstraintHelper, int param1Int, Constraints.LayoutParams param1LayoutParams) {
      fillFromConstraints(param1Int, param1LayoutParams);
      if (param1ConstraintHelper instanceof Barrier) {
        this.layout.mHelperType = 1;
        param1ConstraintHelper = param1ConstraintHelper;
        this.layout.mBarrierDirection = param1ConstraintHelper.getType();
        this.layout.mReferenceIds = param1ConstraintHelper.getReferencedIds();
        this.layout.mBarrierMargin = param1ConstraintHelper.getMargin();
      } 
    }
    
    private ConstraintAttribute get(String param1String, ConstraintAttribute.AttributeType param1AttributeType) {
      ConstraintAttribute constraintAttribute;
      StringBuilder stringBuilder;
      if (this.mCustomConstraints.containsKey(param1String)) {
        constraintAttribute = this.mCustomConstraints.get(param1String);
        if (constraintAttribute.getType() != param1AttributeType) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("ConstraintAttribute is already a ");
          stringBuilder.append(constraintAttribute.getType().name());
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        ConstraintAttribute constraintAttribute1 = new ConstraintAttribute((String)constraintAttribute, (ConstraintAttribute.AttributeType)stringBuilder);
        this.mCustomConstraints.put(constraintAttribute, constraintAttribute1);
        constraintAttribute = constraintAttribute1;
      } 
      return constraintAttribute;
    }
    
    private void setColorValue(String param1String, int param1Int) {
      get(param1String, ConstraintAttribute.AttributeType.COLOR_TYPE).setColorValue(param1Int);
    }
    
    private void setFloatValue(String param1String, float param1Float) {
      get(param1String, ConstraintAttribute.AttributeType.FLOAT_TYPE).setFloatValue(param1Float);
    }
    
    private void setIntValue(String param1String, int param1Int) {
      get(param1String, ConstraintAttribute.AttributeType.INT_TYPE).setIntValue(param1Int);
    }
    
    private void setStringValue(String param1String1, String param1String2) {
      get(param1String1, ConstraintAttribute.AttributeType.STRING_TYPE).setStringValue(param1String2);
    }
    
    public void applyTo(ConstraintLayout.LayoutParams param1LayoutParams) {
      param1LayoutParams.leftToLeft = this.layout.leftToLeft;
      param1LayoutParams.leftToRight = this.layout.leftToRight;
      param1LayoutParams.rightToLeft = this.layout.rightToLeft;
      param1LayoutParams.rightToRight = this.layout.rightToRight;
      param1LayoutParams.topToTop = this.layout.topToTop;
      param1LayoutParams.topToBottom = this.layout.topToBottom;
      param1LayoutParams.bottomToTop = this.layout.bottomToTop;
      param1LayoutParams.bottomToBottom = this.layout.bottomToBottom;
      param1LayoutParams.baselineToBaseline = this.layout.baselineToBaseline;
      param1LayoutParams.startToEnd = this.layout.startToEnd;
      param1LayoutParams.startToStart = this.layout.startToStart;
      param1LayoutParams.endToStart = this.layout.endToStart;
      param1LayoutParams.endToEnd = this.layout.endToEnd;
      param1LayoutParams.leftMargin = this.layout.leftMargin;
      param1LayoutParams.rightMargin = this.layout.rightMargin;
      param1LayoutParams.topMargin = this.layout.topMargin;
      param1LayoutParams.bottomMargin = this.layout.bottomMargin;
      param1LayoutParams.goneStartMargin = this.layout.goneStartMargin;
      param1LayoutParams.goneEndMargin = this.layout.goneEndMargin;
      param1LayoutParams.goneTopMargin = this.layout.goneTopMargin;
      param1LayoutParams.goneBottomMargin = this.layout.goneBottomMargin;
      param1LayoutParams.horizontalBias = this.layout.horizontalBias;
      param1LayoutParams.verticalBias = this.layout.verticalBias;
      param1LayoutParams.circleConstraint = this.layout.circleConstraint;
      param1LayoutParams.circleRadius = this.layout.circleRadius;
      param1LayoutParams.circleAngle = this.layout.circleAngle;
      param1LayoutParams.dimensionRatio = this.layout.dimensionRatio;
      param1LayoutParams.editorAbsoluteX = this.layout.editorAbsoluteX;
      param1LayoutParams.editorAbsoluteY = this.layout.editorAbsoluteY;
      param1LayoutParams.verticalWeight = this.layout.verticalWeight;
      param1LayoutParams.horizontalWeight = this.layout.horizontalWeight;
      param1LayoutParams.verticalChainStyle = this.layout.verticalChainStyle;
      param1LayoutParams.horizontalChainStyle = this.layout.horizontalChainStyle;
      param1LayoutParams.constrainedWidth = this.layout.constrainedWidth;
      param1LayoutParams.constrainedHeight = this.layout.constrainedHeight;
      param1LayoutParams.matchConstraintDefaultWidth = this.layout.widthDefault;
      param1LayoutParams.matchConstraintDefaultHeight = this.layout.heightDefault;
      param1LayoutParams.matchConstraintMaxWidth = this.layout.widthMax;
      param1LayoutParams.matchConstraintMaxHeight = this.layout.heightMax;
      param1LayoutParams.matchConstraintMinWidth = this.layout.widthMin;
      param1LayoutParams.matchConstraintMinHeight = this.layout.heightMin;
      param1LayoutParams.matchConstraintPercentWidth = this.layout.widthPercent;
      param1LayoutParams.matchConstraintPercentHeight = this.layout.heightPercent;
      param1LayoutParams.orientation = this.layout.orientation;
      param1LayoutParams.guidePercent = this.layout.guidePercent;
      param1LayoutParams.guideBegin = this.layout.guideBegin;
      param1LayoutParams.guideEnd = this.layout.guideEnd;
      param1LayoutParams.width = this.layout.mWidth;
      param1LayoutParams.height = this.layout.mHeight;
      if (this.layout.mConstraintTag != null)
        param1LayoutParams.constraintTag = this.layout.mConstraintTag; 
      if (Build.VERSION.SDK_INT >= 17) {
        param1LayoutParams.setMarginStart(this.layout.startMargin);
        param1LayoutParams.setMarginEnd(this.layout.endMargin);
      } 
      param1LayoutParams.validate();
    }
    
    public Constraint clone() {
      Constraint constraint = new Constraint();
      constraint.layout.copyFrom(this.layout);
      constraint.motion.copyFrom(this.motion);
      constraint.propertySet.copyFrom(this.propertySet);
      constraint.transform.copyFrom(this.transform);
      constraint.mViewId = this.mViewId;
      return constraint;
    }
  }
  
  public static class Layout {
    private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
    
    private static final int BARRIER_DIRECTION = 72;
    
    private static final int BARRIER_MARGIN = 73;
    
    private static final int BASELINE_TO_BASELINE = 1;
    
    private static final int BOTTOM_MARGIN = 2;
    
    private static final int BOTTOM_TO_BOTTOM = 3;
    
    private static final int BOTTOM_TO_TOP = 4;
    
    private static final int CHAIN_USE_RTL = 71;
    
    private static final int CIRCLE = 61;
    
    private static final int CIRCLE_ANGLE = 63;
    
    private static final int CIRCLE_RADIUS = 62;
    
    private static final int CONSTRAINT_REFERENCED_IDS = 74;
    
    private static final int DIMENSION_RATIO = 5;
    
    private static final int EDITOR_ABSOLUTE_X = 6;
    
    private static final int EDITOR_ABSOLUTE_Y = 7;
    
    private static final int END_MARGIN = 8;
    
    private static final int END_TO_END = 9;
    
    private static final int END_TO_START = 10;
    
    private static final int GONE_BOTTOM_MARGIN = 11;
    
    private static final int GONE_END_MARGIN = 12;
    
    private static final int GONE_LEFT_MARGIN = 13;
    
    private static final int GONE_RIGHT_MARGIN = 14;
    
    private static final int GONE_START_MARGIN = 15;
    
    private static final int GONE_TOP_MARGIN = 16;
    
    private static final int GUIDE_BEGIN = 17;
    
    private static final int GUIDE_END = 18;
    
    private static final int GUIDE_PERCENT = 19;
    
    private static final int HEIGHT_PERCENT = 70;
    
    private static final int HORIZONTAL_BIAS = 20;
    
    private static final int HORIZONTAL_STYLE = 39;
    
    private static final int HORIZONTAL_WEIGHT = 37;
    
    private static final int LAYOUT_HEIGHT = 21;
    
    private static final int LAYOUT_WIDTH = 22;
    
    private static final int LEFT_MARGIN = 23;
    
    private static final int LEFT_TO_LEFT = 24;
    
    private static final int LEFT_TO_RIGHT = 25;
    
    private static final int ORIENTATION = 26;
    
    private static final int RIGHT_MARGIN = 27;
    
    private static final int RIGHT_TO_LEFT = 28;
    
    private static final int RIGHT_TO_RIGHT = 29;
    
    private static final int START_MARGIN = 30;
    
    private static final int START_TO_END = 31;
    
    private static final int START_TO_START = 32;
    
    private static final int TOP_MARGIN = 33;
    
    private static final int TOP_TO_BOTTOM = 34;
    
    private static final int TOP_TO_TOP = 35;
    
    public static final int UNSET = -1;
    
    private static final int UNUSED = 76;
    
    private static final int VERTICAL_BIAS = 36;
    
    private static final int VERTICAL_STYLE = 40;
    
    private static final int VERTICAL_WEIGHT = 38;
    
    private static final int WIDTH_PERCENT = 69;
    
    private static SparseIntArray mapToConstant;
    
    public int baselineToBaseline = -1;
    
    public int bottomMargin = -1;
    
    public int bottomToBottom = -1;
    
    public int bottomToTop = -1;
    
    public float circleAngle = 0.0F;
    
    public int circleConstraint = -1;
    
    public int circleRadius = 0;
    
    public boolean constrainedHeight = false;
    
    public boolean constrainedWidth = false;
    
    public String dimensionRatio = null;
    
    public int editorAbsoluteX = -1;
    
    public int editorAbsoluteY = -1;
    
    public int endMargin = -1;
    
    public int endToEnd = -1;
    
    public int endToStart = -1;
    
    public int goneBottomMargin = -1;
    
    public int goneEndMargin = -1;
    
    public int goneLeftMargin = -1;
    
    public int goneRightMargin = -1;
    
    public int goneStartMargin = -1;
    
    public int goneTopMargin = -1;
    
    public int guideBegin = -1;
    
    public int guideEnd = -1;
    
    public float guidePercent = -1.0F;
    
    public int heightDefault = 0;
    
    public int heightMax = -1;
    
    public int heightMin = -1;
    
    public float heightPercent = 1.0F;
    
    public float horizontalBias = 0.5F;
    
    public int horizontalChainStyle = 0;
    
    public float horizontalWeight = -1.0F;
    
    public int leftMargin = -1;
    
    public int leftToLeft = -1;
    
    public int leftToRight = -1;
    
    public boolean mApply = false;
    
    public boolean mBarrierAllowsGoneWidgets = true;
    
    public int mBarrierDirection = -1;
    
    public int mBarrierMargin = 0;
    
    public String mConstraintTag;
    
    public int mHeight;
    
    public int mHelperType = -1;
    
    public boolean mIsGuideline = false;
    
    public String mReferenceIdString;
    
    public int[] mReferenceIds;
    
    public int mWidth;
    
    public int orientation = -1;
    
    public int rightMargin = -1;
    
    public int rightToLeft = -1;
    
    public int rightToRight = -1;
    
    public int startMargin = -1;
    
    public int startToEnd = -1;
    
    public int startToStart = -1;
    
    public int topMargin = -1;
    
    public int topToBottom = -1;
    
    public int topToTop = -1;
    
    public float verticalBias = 0.5F;
    
    public int verticalChainStyle = 0;
    
    public float verticalWeight = -1.0F;
    
    public int widthDefault = 0;
    
    public int widthMax = -1;
    
    public int widthMin = -1;
    
    public float widthPercent = 1.0F;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      mapToConstant = sparseIntArray;
      sparseIntArray.append(R.styleable.Layout_layout_constraintLeft_toLeftOf, 24);
      mapToConstant.append(R.styleable.Layout_layout_constraintLeft_toRightOf, 25);
      mapToConstant.append(R.styleable.Layout_layout_constraintRight_toLeftOf, 28);
      mapToConstant.append(R.styleable.Layout_layout_constraintRight_toRightOf, 29);
      mapToConstant.append(R.styleable.Layout_layout_constraintTop_toTopOf, 35);
      mapToConstant.append(R.styleable.Layout_layout_constraintTop_toBottomOf, 34);
      mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toTopOf, 4);
      mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toBottomOf, 3);
      mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_toBaselineOf, 1);
      mapToConstant.append(R.styleable.Layout_layout_editor_absoluteX, 6);
      mapToConstant.append(R.styleable.Layout_layout_editor_absoluteY, 7);
      mapToConstant.append(R.styleable.Layout_layout_constraintGuide_begin, 17);
      mapToConstant.append(R.styleable.Layout_layout_constraintGuide_end, 18);
      mapToConstant.append(R.styleable.Layout_layout_constraintGuide_percent, 19);
      mapToConstant.append(R.styleable.Layout_android_orientation, 26);
      mapToConstant.append(R.styleable.Layout_layout_constraintStart_toEndOf, 31);
      mapToConstant.append(R.styleable.Layout_layout_constraintStart_toStartOf, 32);
      mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toStartOf, 10);
      mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toEndOf, 9);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginLeft, 13);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginTop, 16);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginRight, 14);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginBottom, 11);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginStart, 15);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginEnd, 12);
      mapToConstant.append(R.styleable.Layout_layout_constraintVertical_weight, 38);
      mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_weight, 37);
      mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_chainStyle, 39);
      mapToConstant.append(R.styleable.Layout_layout_constraintVertical_chainStyle, 40);
      mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_bias, 20);
      mapToConstant.append(R.styleable.Layout_layout_constraintVertical_bias, 36);
      mapToConstant.append(R.styleable.Layout_layout_constraintDimensionRatio, 5);
      mapToConstant.append(R.styleable.Layout_layout_constraintLeft_creator, 76);
      mapToConstant.append(R.styleable.Layout_layout_constraintTop_creator, 76);
      mapToConstant.append(R.styleable.Layout_layout_constraintRight_creator, 76);
      mapToConstant.append(R.styleable.Layout_layout_constraintBottom_creator, 76);
      mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_creator, 76);
      mapToConstant.append(R.styleable.Layout_android_layout_marginLeft, 23);
      mapToConstant.append(R.styleable.Layout_android_layout_marginRight, 27);
      mapToConstant.append(R.styleable.Layout_android_layout_marginStart, 30);
      mapToConstant.append(R.styleable.Layout_android_layout_marginEnd, 8);
      mapToConstant.append(R.styleable.Layout_android_layout_marginTop, 33);
      mapToConstant.append(R.styleable.Layout_android_layout_marginBottom, 2);
      mapToConstant.append(R.styleable.Layout_android_layout_width, 22);
      mapToConstant.append(R.styleable.Layout_android_layout_height, 21);
      mapToConstant.append(R.styleable.Layout_layout_constraintCircle, 61);
      mapToConstant.append(R.styleable.Layout_layout_constraintCircleRadius, 62);
      mapToConstant.append(R.styleable.Layout_layout_constraintCircleAngle, 63);
      mapToConstant.append(R.styleable.Layout_layout_constraintWidth_percent, 69);
      mapToConstant.append(R.styleable.Layout_layout_constraintHeight_percent, 70);
      mapToConstant.append(R.styleable.Layout_chainUseRtl, 71);
      mapToConstant.append(R.styleable.Layout_barrierDirection, 72);
      mapToConstant.append(R.styleable.Layout_barrierMargin, 73);
      mapToConstant.append(R.styleable.Layout_constraint_referenced_ids, 74);
      mapToConstant.append(R.styleable.Layout_barrierAllowsGoneWidgets, 75);
    }
    
    public void copyFrom(Layout param1Layout) {
      this.mIsGuideline = param1Layout.mIsGuideline;
      this.mWidth = param1Layout.mWidth;
      this.mApply = param1Layout.mApply;
      this.mHeight = param1Layout.mHeight;
      this.guideBegin = param1Layout.guideBegin;
      this.guideEnd = param1Layout.guideEnd;
      this.guidePercent = param1Layout.guidePercent;
      this.leftToLeft = param1Layout.leftToLeft;
      this.leftToRight = param1Layout.leftToRight;
      this.rightToLeft = param1Layout.rightToLeft;
      this.rightToRight = param1Layout.rightToRight;
      this.topToTop = param1Layout.topToTop;
      this.topToBottom = param1Layout.topToBottom;
      this.bottomToTop = param1Layout.bottomToTop;
      this.bottomToBottom = param1Layout.bottomToBottom;
      this.baselineToBaseline = param1Layout.baselineToBaseline;
      this.startToEnd = param1Layout.startToEnd;
      this.startToStart = param1Layout.startToStart;
      this.endToStart = param1Layout.endToStart;
      this.endToEnd = param1Layout.endToEnd;
      this.horizontalBias = param1Layout.horizontalBias;
      this.verticalBias = param1Layout.verticalBias;
      this.dimensionRatio = param1Layout.dimensionRatio;
      this.circleConstraint = param1Layout.circleConstraint;
      this.circleRadius = param1Layout.circleRadius;
      this.circleAngle = param1Layout.circleAngle;
      this.editorAbsoluteX = param1Layout.editorAbsoluteX;
      this.editorAbsoluteY = param1Layout.editorAbsoluteY;
      this.orientation = param1Layout.orientation;
      this.leftMargin = param1Layout.leftMargin;
      this.rightMargin = param1Layout.rightMargin;
      this.topMargin = param1Layout.topMargin;
      this.bottomMargin = param1Layout.bottomMargin;
      this.endMargin = param1Layout.endMargin;
      this.startMargin = param1Layout.startMargin;
      this.goneLeftMargin = param1Layout.goneLeftMargin;
      this.goneTopMargin = param1Layout.goneTopMargin;
      this.goneRightMargin = param1Layout.goneRightMargin;
      this.goneBottomMargin = param1Layout.goneBottomMargin;
      this.goneEndMargin = param1Layout.goneEndMargin;
      this.goneStartMargin = param1Layout.goneStartMargin;
      this.verticalWeight = param1Layout.verticalWeight;
      this.horizontalWeight = param1Layout.horizontalWeight;
      this.horizontalChainStyle = param1Layout.horizontalChainStyle;
      this.verticalChainStyle = param1Layout.verticalChainStyle;
      this.widthDefault = param1Layout.widthDefault;
      this.heightDefault = param1Layout.heightDefault;
      this.widthMax = param1Layout.widthMax;
      this.heightMax = param1Layout.heightMax;
      this.widthMin = param1Layout.widthMin;
      this.heightMin = param1Layout.heightMin;
      this.widthPercent = param1Layout.widthPercent;
      this.heightPercent = param1Layout.heightPercent;
      this.mBarrierDirection = param1Layout.mBarrierDirection;
      this.mBarrierMargin = param1Layout.mBarrierMargin;
      this.mHelperType = param1Layout.mHelperType;
      this.mConstraintTag = param1Layout.mConstraintTag;
      int[] arrayOfInt = param1Layout.mReferenceIds;
      if (arrayOfInt != null) {
        this.mReferenceIds = Arrays.copyOf(arrayOfInt, arrayOfInt.length);
      } else {
        this.mReferenceIds = null;
      } 
      this.mReferenceIdString = param1Layout.mReferenceIdString;
      this.constrainedWidth = param1Layout.constrainedWidth;
      this.constrainedHeight = param1Layout.constrainedHeight;
      this.mBarrierAllowsGoneWidgets = param1Layout.mBarrierAllowsGoneWidgets;
    }
    
    public void dump(MotionScene param1MotionScene, StringBuilder param1StringBuilder) {
      Field[] arrayOfField = getClass().getDeclaredFields();
      param1StringBuilder.append("\n");
      for (byte b = 0; b < arrayOfField.length; b++) {
        Field field = arrayOfField[b];
        String str = field.getName();
        if (!Modifier.isStatic(field.getModifiers()))
          try {
            String str1;
            Object object = field.get(this);
            Class<?> clazz = field.getType();
            Class<int> clazz1 = int.class;
            if (clazz == clazz1) {
              object = object;
              if (object.intValue() != -1) {
                str1 = param1MotionScene.lookUpConstraintName(object.intValue());
                param1StringBuilder.append("    ");
                param1StringBuilder.append(str);
                param1StringBuilder.append(" = \"");
                if (str1 != null)
                  object = str1; 
                param1StringBuilder.append(object);
                param1StringBuilder.append("\"\n");
              } 
            } else if (str1 == float.class) {
              object = object;
              if (object.floatValue() != -1.0F) {
                param1StringBuilder.append("    ");
                param1StringBuilder.append(str);
                param1StringBuilder.append(" = \"");
                param1StringBuilder.append(object);
                param1StringBuilder.append("\"\n");
              } 
            } 
          } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
          }  
      } 
    }
    
    void fillFromAttributeList(Context param1Context, AttributeSet param1AttributeSet) {
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.Layout);
      this.mApply = true;
      int i = typedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        int k = mapToConstant.get(j);
        if (k != 80) {
          if (k != 81) {
            StringBuilder stringBuilder;
            switch (k) {
              default:
                switch (k) {
                  default:
                    switch (k) {
                      default:
                        switch (k) {
                          default:
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown attribute 0x");
                            stringBuilder.append(Integer.toHexString(j));
                            stringBuilder.append("   ");
                            stringBuilder.append(mapToConstant.get(j));
                            Log.w("ConstraintSet", stringBuilder.toString());
                            break;
                          case 77:
                            this.mConstraintTag = typedArray.getString(j);
                            break;
                          case 76:
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("unused attribute 0x");
                            stringBuilder.append(Integer.toHexString(j));
                            stringBuilder.append("   ");
                            stringBuilder.append(mapToConstant.get(j));
                            Log.w("ConstraintSet", stringBuilder.toString());
                            break;
                          case 75:
                            this.mBarrierAllowsGoneWidgets = typedArray.getBoolean(j, this.mBarrierAllowsGoneWidgets);
                            break;
                          case 74:
                            this.mReferenceIdString = typedArray.getString(j);
                            break;
                          case 73:
                            this.mBarrierMargin = typedArray.getDimensionPixelSize(j, this.mBarrierMargin);
                            break;
                          case 72:
                            this.mBarrierDirection = typedArray.getInt(j, this.mBarrierDirection);
                            break;
                          case 71:
                            Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                            break;
                          case 70:
                            this.heightPercent = typedArray.getFloat(j, 1.0F);
                            break;
                          case 69:
                            break;
                        } 
                        this.widthPercent = typedArray.getFloat(j, 1.0F);
                        break;
                      case 63:
                        this.circleAngle = typedArray.getFloat(j, this.circleAngle);
                        break;
                      case 62:
                        this.circleRadius = typedArray.getDimensionPixelSize(j, this.circleRadius);
                        break;
                      case 61:
                        break;
                    } 
                    this.circleConstraint = ConstraintSet.lookupID(typedArray, j, this.circleConstraint);
                    break;
                  case 59:
                    this.heightMin = typedArray.getDimensionPixelSize(j, this.heightMin);
                    break;
                  case 58:
                    this.widthMin = typedArray.getDimensionPixelSize(j, this.widthMin);
                    break;
                  case 57:
                    this.heightMax = typedArray.getDimensionPixelSize(j, this.heightMax);
                    break;
                  case 56:
                    this.widthMax = typedArray.getDimensionPixelSize(j, this.widthMax);
                    break;
                  case 55:
                    this.heightDefault = typedArray.getInt(j, this.heightDefault);
                    break;
                  case 54:
                    break;
                } 
                this.widthDefault = typedArray.getInt(j, this.widthDefault);
                break;
              case 40:
                this.verticalChainStyle = typedArray.getInt(j, this.verticalChainStyle);
                break;
              case 39:
                this.horizontalChainStyle = typedArray.getInt(j, this.horizontalChainStyle);
                break;
              case 38:
                this.verticalWeight = typedArray.getFloat(j, this.verticalWeight);
                break;
              case 37:
                this.horizontalWeight = typedArray.getFloat(j, this.horizontalWeight);
                break;
              case 36:
                this.verticalBias = typedArray.getFloat(j, this.verticalBias);
                break;
              case 35:
                this.topToTop = ConstraintSet.lookupID(typedArray, j, this.topToTop);
                break;
              case 34:
                this.topToBottom = ConstraintSet.lookupID(typedArray, j, this.topToBottom);
                break;
              case 33:
                this.topMargin = typedArray.getDimensionPixelSize(j, this.topMargin);
                break;
              case 32:
                this.startToStart = ConstraintSet.lookupID(typedArray, j, this.startToStart);
                break;
              case 31:
                this.startToEnd = ConstraintSet.lookupID(typedArray, j, this.startToEnd);
                break;
              case 30:
                if (Build.VERSION.SDK_INT >= 17)
                  this.startMargin = typedArray.getDimensionPixelSize(j, this.startMargin); 
                break;
              case 29:
                this.rightToRight = ConstraintSet.lookupID(typedArray, j, this.rightToRight);
                break;
              case 28:
                this.rightToLeft = ConstraintSet.lookupID(typedArray, j, this.rightToLeft);
                break;
              case 27:
                this.rightMargin = typedArray.getDimensionPixelSize(j, this.rightMargin);
                break;
              case 26:
                this.orientation = typedArray.getInt(j, this.orientation);
                break;
              case 25:
                this.leftToRight = ConstraintSet.lookupID(typedArray, j, this.leftToRight);
                break;
              case 24:
                this.leftToLeft = ConstraintSet.lookupID(typedArray, j, this.leftToLeft);
                break;
              case 23:
                this.leftMargin = typedArray.getDimensionPixelSize(j, this.leftMargin);
                break;
              case 22:
                this.mWidth = typedArray.getLayoutDimension(j, this.mWidth);
                break;
              case 21:
                this.mHeight = typedArray.getLayoutDimension(j, this.mHeight);
                break;
              case 20:
                this.horizontalBias = typedArray.getFloat(j, this.horizontalBias);
                break;
              case 19:
                this.guidePercent = typedArray.getFloat(j, this.guidePercent);
                break;
              case 18:
                this.guideEnd = typedArray.getDimensionPixelOffset(j, this.guideEnd);
                break;
              case 17:
                this.guideBegin = typedArray.getDimensionPixelOffset(j, this.guideBegin);
                break;
              case 16:
                this.goneTopMargin = typedArray.getDimensionPixelSize(j, this.goneTopMargin);
                break;
              case 15:
                this.goneStartMargin = typedArray.getDimensionPixelSize(j, this.goneStartMargin);
                break;
              case 14:
                this.goneRightMargin = typedArray.getDimensionPixelSize(j, this.goneRightMargin);
                break;
              case 13:
                this.goneLeftMargin = typedArray.getDimensionPixelSize(j, this.goneLeftMargin);
                break;
              case 12:
                this.goneEndMargin = typedArray.getDimensionPixelSize(j, this.goneEndMargin);
                break;
              case 11:
                this.goneBottomMargin = typedArray.getDimensionPixelSize(j, this.goneBottomMargin);
                break;
              case 10:
                this.endToStart = ConstraintSet.lookupID(typedArray, j, this.endToStart);
                break;
              case 9:
                this.endToEnd = ConstraintSet.lookupID(typedArray, j, this.endToEnd);
                break;
              case 8:
                if (Build.VERSION.SDK_INT >= 17)
                  this.endMargin = typedArray.getDimensionPixelSize(j, this.endMargin); 
                break;
              case 7:
                this.editorAbsoluteY = typedArray.getDimensionPixelOffset(j, this.editorAbsoluteY);
                break;
              case 6:
                this.editorAbsoluteX = typedArray.getDimensionPixelOffset(j, this.editorAbsoluteX);
                break;
              case 5:
                this.dimensionRatio = typedArray.getString(j);
                break;
              case 4:
                this.bottomToTop = ConstraintSet.lookupID(typedArray, j, this.bottomToTop);
                break;
              case 3:
                this.bottomToBottom = ConstraintSet.lookupID(typedArray, j, this.bottomToBottom);
                break;
              case 2:
                this.bottomMargin = typedArray.getDimensionPixelSize(j, this.bottomMargin);
                break;
              case 1:
                this.baselineToBaseline = ConstraintSet.lookupID(typedArray, j, this.baselineToBaseline);
                break;
            } 
          } else {
            this.constrainedHeight = typedArray.getBoolean(j, this.constrainedHeight);
          } 
        } else {
          this.constrainedWidth = typedArray.getBoolean(j, this.constrainedWidth);
        } 
      } 
      typedArray.recycle();
    }
  }
  
  public static class Motion {
    private static final int ANIMATE_RELATIVE_TO = 5;
    
    private static final int MOTION_DRAW_PATH = 4;
    
    private static final int MOTION_STAGGER = 6;
    
    private static final int PATH_MOTION_ARC = 2;
    
    private static final int TRANSITION_EASING = 3;
    
    private static final int TRANSITION_PATH_ROTATE = 1;
    
    private static SparseIntArray mapToConstant;
    
    public int mAnimateRelativeTo = -1;
    
    public boolean mApply = false;
    
    public int mDrawPath = 0;
    
    public float mMotionStagger = Float.NaN;
    
    public int mPathMotionArc = -1;
    
    public float mPathRotate = Float.NaN;
    
    public String mTransitionEasing = null;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      mapToConstant = sparseIntArray;
      sparseIntArray.append(R.styleable.Motion_motionPathRotate, 1);
      mapToConstant.append(R.styleable.Motion_pathMotionArc, 2);
      mapToConstant.append(R.styleable.Motion_transitionEasing, 3);
      mapToConstant.append(R.styleable.Motion_drawPath, 4);
      mapToConstant.append(R.styleable.Motion_animate_relativeTo, 5);
      mapToConstant.append(R.styleable.Motion_motionStagger, 6);
    }
    
    public void copyFrom(Motion param1Motion) {
      this.mApply = param1Motion.mApply;
      this.mAnimateRelativeTo = param1Motion.mAnimateRelativeTo;
      this.mTransitionEasing = param1Motion.mTransitionEasing;
      this.mPathMotionArc = param1Motion.mPathMotionArc;
      this.mDrawPath = param1Motion.mDrawPath;
      this.mPathRotate = param1Motion.mPathRotate;
      this.mMotionStagger = param1Motion.mMotionStagger;
    }
    
    void fillFromAttributeList(Context param1Context, AttributeSet param1AttributeSet) {
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.Motion);
      this.mApply = true;
      int i = typedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        switch (mapToConstant.get(j)) {
          case 6:
            this.mMotionStagger = typedArray.getFloat(j, this.mMotionStagger);
            break;
          case 5:
            this.mAnimateRelativeTo = ConstraintSet.lookupID(typedArray, j, this.mAnimateRelativeTo);
            break;
          case 4:
            this.mDrawPath = typedArray.getInt(j, 0);
            break;
          case 3:
            if ((typedArray.peekValue(j)).type == 3) {
              this.mTransitionEasing = typedArray.getString(j);
              break;
            } 
            this.mTransitionEasing = Easing.NAMED_EASING[typedArray.getInteger(j, 0)];
            break;
          case 2:
            this.mPathMotionArc = typedArray.getInt(j, this.mPathMotionArc);
            break;
          case 1:
            this.mPathRotate = typedArray.getFloat(j, this.mPathRotate);
            break;
        } 
      } 
      typedArray.recycle();
    }
  }
  
  public static class PropertySet {
    public float alpha = 1.0F;
    
    public boolean mApply = false;
    
    public float mProgress = Float.NaN;
    
    public int mVisibilityMode = 0;
    
    public int visibility = 0;
    
    public void copyFrom(PropertySet param1PropertySet) {
      this.mApply = param1PropertySet.mApply;
      this.visibility = param1PropertySet.visibility;
      this.alpha = param1PropertySet.alpha;
      this.mProgress = param1PropertySet.mProgress;
      this.mVisibilityMode = param1PropertySet.mVisibilityMode;
    }
    
    void fillFromAttributeList(Context param1Context, AttributeSet param1AttributeSet) {
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.PropertySet);
      this.mApply = true;
      int i = typedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        if (j == R.styleable.PropertySet_android_alpha) {
          this.alpha = typedArray.getFloat(j, this.alpha);
        } else if (j == R.styleable.PropertySet_android_visibility) {
          this.visibility = typedArray.getInt(j, this.visibility);
          this.visibility = ConstraintSet.VISIBILITY_FLAGS[this.visibility];
        } else if (j == R.styleable.PropertySet_visibilityMode) {
          this.mVisibilityMode = typedArray.getInt(j, this.mVisibilityMode);
        } else if (j == R.styleable.PropertySet_motionProgress) {
          this.mProgress = typedArray.getFloat(j, this.mProgress);
        } 
      } 
      typedArray.recycle();
    }
  }
  
  public static class Transform {
    private static final int ELEVATION = 11;
    
    private static final int ROTATION = 1;
    
    private static final int ROTATION_X = 2;
    
    private static final int ROTATION_Y = 3;
    
    private static final int SCALE_X = 4;
    
    private static final int SCALE_Y = 5;
    
    private static final int TRANSFORM_PIVOT_X = 6;
    
    private static final int TRANSFORM_PIVOT_Y = 7;
    
    private static final int TRANSLATION_X = 8;
    
    private static final int TRANSLATION_Y = 9;
    
    private static final int TRANSLATION_Z = 10;
    
    private static SparseIntArray mapToConstant;
    
    public boolean applyElevation = false;
    
    public float elevation = 0.0F;
    
    public boolean mApply = false;
    
    public float rotation = 0.0F;
    
    public float rotationX = 0.0F;
    
    public float rotationY = 0.0F;
    
    public float scaleX = 1.0F;
    
    public float scaleY = 1.0F;
    
    public float transformPivotX = Float.NaN;
    
    public float transformPivotY = Float.NaN;
    
    public float translationX = 0.0F;
    
    public float translationY = 0.0F;
    
    public float translationZ = 0.0F;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      mapToConstant = sparseIntArray;
      sparseIntArray.append(R.styleable.Transform_android_rotation, 1);
      mapToConstant.append(R.styleable.Transform_android_rotationX, 2);
      mapToConstant.append(R.styleable.Transform_android_rotationY, 3);
      mapToConstant.append(R.styleable.Transform_android_scaleX, 4);
      mapToConstant.append(R.styleable.Transform_android_scaleY, 5);
      mapToConstant.append(R.styleable.Transform_android_transformPivotX, 6);
      mapToConstant.append(R.styleable.Transform_android_transformPivotY, 7);
      mapToConstant.append(R.styleable.Transform_android_translationX, 8);
      mapToConstant.append(R.styleable.Transform_android_translationY, 9);
      mapToConstant.append(R.styleable.Transform_android_translationZ, 10);
      mapToConstant.append(R.styleable.Transform_android_elevation, 11);
    }
    
    public void copyFrom(Transform param1Transform) {
      this.mApply = param1Transform.mApply;
      this.rotation = param1Transform.rotation;
      this.rotationX = param1Transform.rotationX;
      this.rotationY = param1Transform.rotationY;
      this.scaleX = param1Transform.scaleX;
      this.scaleY = param1Transform.scaleY;
      this.transformPivotX = param1Transform.transformPivotX;
      this.transformPivotY = param1Transform.transformPivotY;
      this.translationX = param1Transform.translationX;
      this.translationY = param1Transform.translationY;
      this.translationZ = param1Transform.translationZ;
      this.applyElevation = param1Transform.applyElevation;
      this.elevation = param1Transform.elevation;
    }
    
    void fillFromAttributeList(Context param1Context, AttributeSet param1AttributeSet) {
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.Transform);
      this.mApply = true;
      int i = typedArray.getIndexCount();
      for (byte b = 0; b < i; b++) {
        int j = typedArray.getIndex(b);
        switch (mapToConstant.get(j)) {
          case 11:
            if (Build.VERSION.SDK_INT >= 21) {
              this.applyElevation = true;
              this.elevation = typedArray.getDimension(j, this.elevation);
            } 
            break;
          case 10:
            if (Build.VERSION.SDK_INT >= 21)
              this.translationZ = typedArray.getDimension(j, this.translationZ); 
            break;
          case 9:
            this.translationY = typedArray.getDimension(j, this.translationY);
            break;
          case 8:
            this.translationX = typedArray.getDimension(j, this.translationX);
            break;
          case 7:
            this.transformPivotY = typedArray.getDimension(j, this.transformPivotY);
            break;
          case 6:
            this.transformPivotX = typedArray.getDimension(j, this.transformPivotX);
            break;
          case 5:
            this.scaleY = typedArray.getFloat(j, this.scaleY);
            break;
          case 4:
            this.scaleX = typedArray.getFloat(j, this.scaleX);
            break;
          case 3:
            this.rotationY = typedArray.getFloat(j, this.rotationY);
            break;
          case 2:
            this.rotationX = typedArray.getFloat(j, this.rotationX);
            break;
          case 1:
            this.rotation = typedArray.getFloat(j, this.rotation);
            break;
        } 
      } 
      typedArray.recycle();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\widget\ConstraintSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */