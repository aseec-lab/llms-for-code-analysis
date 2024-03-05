package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateSet {
  private static final boolean DEBUG = false;
  
  public static final String TAG = "ConstraintLayoutStates";
  
  private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
  
  private ConstraintsChangedListener mConstraintsChangedListener = null;
  
  int mCurrentConstraintNumber = -1;
  
  int mCurrentStateId = -1;
  
  ConstraintSet mDefaultConstraintSet;
  
  int mDefaultState = -1;
  
  private SparseArray<State> mStateList = new SparseArray();
  
  public StateSet(Context paramContext, XmlPullParser paramXmlPullParser) {
    load(paramContext, paramXmlPullParser);
  }
  
  private void load(Context paramContext, XmlPullParser paramXmlPullParser) {
    TypedArray typedArray = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.StateSet);
    int j = typedArray.getIndexCount();
    int i;
    for (i = 0; i < j; i++) {
      int k = typedArray.getIndex(i);
      if (k == R.styleable.StateSet_defaultState)
        this.mDefaultState = typedArray.getResourceId(k, this.mDefaultState); 
    } 
    typedArray.recycle();
    typedArray = null;
    try {
      i = paramXmlPullParser.getEventType();
      while (i != 1) {
        TypedArray typedArray1;
        if (i != 0) {
          if (i != 2) {
            if (i != 3) {
              typedArray1 = typedArray;
            } else {
              typedArray1 = typedArray;
              if ("StateSet".equals(paramXmlPullParser.getName()))
                return; 
            } 
          } else {
            String str = paramXmlPullParser.getName();
            i = -1;
            switch (str.hashCode()) {
              case 1901439077:
                if (str.equals("Variant"))
                  i = 3; 
                break;
              case 1382829617:
                if (str.equals("StateSet"))
                  i = 1; 
                break;
              case 1301459538:
                if (str.equals("LayoutDescription"))
                  i = 0; 
                break;
              case 80204913:
                if (str.equals("State"))
                  i = 2; 
                break;
            } 
            typedArray1 = typedArray;
            if (i != 0) {
              typedArray1 = typedArray;
              if (i != 1)
                if (i != 2) {
                  if (i != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    this();
                    stringBuilder.append("unknown tag ");
                    stringBuilder.append(str);
                    Log.v("ConstraintLayoutStates", stringBuilder.toString());
                    TypedArray typedArray2 = typedArray;
                  } else {
                    Variant variant = new Variant();
                    this(paramContext, paramXmlPullParser);
                    typedArray1 = typedArray;
                    if (typedArray != null) {
                      typedArray.add(variant);
                      typedArray1 = typedArray;
                    } 
                  } 
                } else {
                  State state = new State();
                  this(paramContext, paramXmlPullParser);
                  this.mStateList.put(state.mId, state);
                }  
            } 
          } 
        } else {
          paramXmlPullParser.getName();
          typedArray1 = typedArray;
        } 
        i = paramXmlPullParser.next();
        typedArray = typedArray1;
      } 
    } catch (XmlPullParserException xmlPullParserException) {
      xmlPullParserException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public int convertToConstraintSet(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
    State state = (State)this.mStateList.get(paramInt2);
    if (state == null)
      return paramInt2; 
    if (paramFloat1 == -1.0F || paramFloat2 == -1.0F) {
      if (state.mConstraintID == paramInt1)
        return paramInt1; 
      Iterator<Variant> iterator = state.mVariants.iterator();
      while (iterator.hasNext()) {
        if (paramInt1 == ((Variant)iterator.next()).mConstraintID)
          return paramInt1; 
      } 
      return state.mConstraintID;
    } 
    Variant variant = null;
    for (Variant variant1 : state.mVariants) {
      if (variant1.match(paramFloat1, paramFloat2)) {
        if (paramInt1 == variant1.mConstraintID)
          return paramInt1; 
        variant = variant1;
      } 
    } 
    return (variant != null) ? variant.mConstraintID : state.mConstraintID;
  }
  
  public boolean needsToChange(int paramInt, float paramFloat1, float paramFloat2) {
    int i = this.mCurrentStateId;
    if (i != paramInt)
      return true; 
    if (paramInt == -1) {
      object = this.mStateList.valueAt(0);
    } else {
      object = this.mStateList.get(i);
    } 
    Object object = object;
    return (this.mCurrentConstraintNumber != -1 && ((Variant)((State)object).mVariants.get(this.mCurrentConstraintNumber)).match(paramFloat1, paramFloat2)) ? false : (!(this.mCurrentConstraintNumber == object.findMatch(paramFloat1, paramFloat2)));
  }
  
  public void setOnConstraintsChanged(ConstraintsChangedListener paramConstraintsChangedListener) {
    this.mConstraintsChangedListener = paramConstraintsChangedListener;
  }
  
  public int stateGetConstraintID(int paramInt1, int paramInt2, int paramInt3) {
    return updateConstraints(-1, paramInt1, paramInt2, paramInt3);
  }
  
  public int updateConstraints(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
    if (paramInt1 == paramInt2) {
      State state1;
      if (paramInt2 == -1) {
        state1 = (State)this.mStateList.valueAt(0);
      } else {
        state1 = (State)this.mStateList.get(this.mCurrentStateId);
      } 
      if (state1 == null)
        return -1; 
      if (this.mCurrentConstraintNumber != -1 && ((Variant)state1.mVariants.get(paramInt1)).match(paramFloat1, paramFloat2))
        return paramInt1; 
      paramInt2 = state1.findMatch(paramFloat1, paramFloat2);
      if (paramInt1 == paramInt2)
        return paramInt1; 
      if (paramInt2 == -1) {
        paramInt1 = state1.mConstraintID;
      } else {
        paramInt1 = ((Variant)state1.mVariants.get(paramInt2)).mConstraintID;
      } 
      return paramInt1;
    } 
    State state = (State)this.mStateList.get(paramInt2);
    if (state == null)
      return -1; 
    paramInt1 = state.findMatch(paramFloat1, paramFloat2);
    if (paramInt1 == -1) {
      paramInt1 = state.mConstraintID;
    } else {
      paramInt1 = ((Variant)state.mVariants.get(paramInt1)).mConstraintID;
    } 
    return paramInt1;
  }
  
  static class State {
    int mConstraintID = -1;
    
    int mId;
    
    boolean mIsLayout;
    
    ArrayList<StateSet.Variant> mVariants = new ArrayList<StateSet.Variant>();
    
    public State(Context param1Context, XmlPullParser param1XmlPullParser) {
      byte b = 0;
      this.mIsLayout = false;
      TypedArray typedArray = param1Context.obtainStyledAttributes(Xml.asAttributeSet(param1XmlPullParser), R.styleable.State);
      int i = typedArray.getIndexCount();
      while (b < i) {
        int j = typedArray.getIndex(b);
        if (j == R.styleable.State_android_id) {
          this.mId = typedArray.getResourceId(j, this.mId);
        } else if (j == R.styleable.State_constraints) {
          this.mConstraintID = typedArray.getResourceId(j, this.mConstraintID);
          String str = param1Context.getResources().getResourceTypeName(this.mConstraintID);
          param1Context.getResources().getResourceName(this.mConstraintID);
          if ("layout".equals(str))
            this.mIsLayout = true; 
        } 
        b++;
      } 
      typedArray.recycle();
    }
    
    void add(StateSet.Variant param1Variant) {
      this.mVariants.add(param1Variant);
    }
    
    public int findMatch(float param1Float1, float param1Float2) {
      for (byte b = 0; b < this.mVariants.size(); b++) {
        if (((StateSet.Variant)this.mVariants.get(b)).match(param1Float1, param1Float2))
          return b; 
      } 
      return -1;
    }
  }
  
  static class Variant {
    int mConstraintID = -1;
    
    int mId;
    
    boolean mIsLayout;
    
    float mMaxHeight = Float.NaN;
    
    float mMaxWidth = Float.NaN;
    
    float mMinHeight = Float.NaN;
    
    float mMinWidth = Float.NaN;
    
    public Variant(Context param1Context, XmlPullParser param1XmlPullParser) {
      byte b = 0;
      this.mIsLayout = false;
      TypedArray typedArray = param1Context.obtainStyledAttributes(Xml.asAttributeSet(param1XmlPullParser), R.styleable.Variant);
      int i = typedArray.getIndexCount();
      while (b < i) {
        int j = typedArray.getIndex(b);
        if (j == R.styleable.Variant_constraints) {
          this.mConstraintID = typedArray.getResourceId(j, this.mConstraintID);
          String str = param1Context.getResources().getResourceTypeName(this.mConstraintID);
          param1Context.getResources().getResourceName(this.mConstraintID);
          if ("layout".equals(str))
            this.mIsLayout = true; 
        } else if (j == R.styleable.Variant_region_heightLessThan) {
          this.mMaxHeight = typedArray.getDimension(j, this.mMaxHeight);
        } else if (j == R.styleable.Variant_region_heightMoreThan) {
          this.mMinHeight = typedArray.getDimension(j, this.mMinHeight);
        } else if (j == R.styleable.Variant_region_widthLessThan) {
          this.mMaxWidth = typedArray.getDimension(j, this.mMaxWidth);
        } else if (j == R.styleable.Variant_region_widthMoreThan) {
          this.mMinWidth = typedArray.getDimension(j, this.mMinWidth);
        } else {
          Log.v("ConstraintLayoutStates", "Unknown tag");
        } 
        b++;
      } 
      typedArray.recycle();
    }
    
    boolean match(float param1Float1, float param1Float2) {
      return (!Float.isNaN(this.mMinWidth) && param1Float1 < this.mMinWidth) ? false : ((!Float.isNaN(this.mMinHeight) && param1Float2 < this.mMinHeight) ? false : ((!Float.isNaN(this.mMaxWidth) && param1Float1 > this.mMaxWidth) ? false : (!(!Float.isNaN(this.mMaxHeight) && param1Float2 > this.mMaxHeight))));
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\widget\StateSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */