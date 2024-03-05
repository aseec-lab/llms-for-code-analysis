package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class StateListDrawable extends DrawableContainer {
  private static final boolean DEBUG = false;
  
  private static final String TAG = "StateListDrawable";
  
  private boolean mMutated;
  
  private StateListState mStateListState;
  
  StateListDrawable() {
    this((StateListState)null, (Resources)null);
  }
  
  StateListDrawable(StateListState paramStateListState) {
    if (paramStateListState != null)
      setConstantState(paramStateListState); 
  }
  
  StateListDrawable(StateListState paramStateListState, Resources paramResources) {
    setConstantState(new StateListState(paramStateListState, this, paramResources));
    onStateChange(getState());
  }
  
  private void inflateChildElements(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    StateListState stateListState = this.mStateListState;
    int i = paramXmlPullParser.getDepth() + 1;
    while (true) {
      Drawable drawable;
      int[] arrayOfInt;
      int j = paramXmlPullParser.next();
      if (j != 1) {
        int k = paramXmlPullParser.getDepth();
        if (k >= i || j != 3) {
          if (j != 2 || k > i || !paramXmlPullParser.getName().equals("item"))
            continue; 
          TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.StateListDrawableItem);
          Drawable drawable1 = null;
          k = typedArray.getResourceId(R.styleable.StateListDrawableItem_android_drawable, -1);
          if (k > 0)
            drawable1 = ResourceManagerInternal.get().getDrawable(paramContext, k); 
          typedArray.recycle();
          arrayOfInt = extractStateSet(paramAttributeSet);
          drawable = drawable1;
          if (drawable1 == null)
            while (true) {
              k = paramXmlPullParser.next();
              if (k == 4)
                continue; 
              if (k == 2) {
                if (Build.VERSION.SDK_INT >= 21) {
                  drawable = Drawable.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
                  break;
                } 
                drawable = Drawable.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet);
              } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(paramXmlPullParser.getPositionDescription());
                stringBuilder.append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                throw new XmlPullParserException(stringBuilder.toString());
              } 
              stateListState.addStateSet(arrayOfInt, drawable);
            }  
        } else {
          break;
        } 
      } else {
        break;
      } 
      stateListState.addStateSet(arrayOfInt, drawable);
    } 
  }
  
  private void updateStateFromTypedArray(TypedArray paramTypedArray) {
    StateListState stateListState = this.mStateListState;
    if (Build.VERSION.SDK_INT >= 21)
      stateListState.mChangingConfigurations |= paramTypedArray.getChangingConfigurations(); 
    stateListState.mVariablePadding = paramTypedArray.getBoolean(R.styleable.StateListDrawable_android_variablePadding, stateListState.mVariablePadding);
    stateListState.mConstantSize = paramTypedArray.getBoolean(R.styleable.StateListDrawable_android_constantSize, stateListState.mConstantSize);
    stateListState.mEnterFadeDuration = paramTypedArray.getInt(R.styleable.StateListDrawable_android_enterFadeDuration, stateListState.mEnterFadeDuration);
    stateListState.mExitFadeDuration = paramTypedArray.getInt(R.styleable.StateListDrawable_android_exitFadeDuration, stateListState.mExitFadeDuration);
    stateListState.mDither = paramTypedArray.getBoolean(R.styleable.StateListDrawable_android_dither, stateListState.mDither);
  }
  
  public void addState(int[] paramArrayOfint, Drawable paramDrawable) {
    if (paramDrawable != null) {
      this.mStateListState.addStateSet(paramArrayOfint, paramDrawable);
      onStateChange(getState());
    } 
  }
  
  public void applyTheme(Resources.Theme paramTheme) {
    super.applyTheme(paramTheme);
    onStateChange(getState());
  }
  
  void clearMutated() {
    super.clearMutated();
    this.mMutated = false;
  }
  
  StateListState cloneConstantState() {
    return new StateListState(this.mStateListState, this, null);
  }
  
  int[] extractStateSet(AttributeSet paramAttributeSet) {
    int j = paramAttributeSet.getAttributeCount();
    int[] arrayOfInt = new int[j];
    byte b = 0;
    int i;
    for (i = 0; b < j; i = k) {
      int m = paramAttributeSet.getAttributeNameResource(b);
      int k = i;
      if (m != 0) {
        k = i;
        if (m != 16842960) {
          k = i;
          if (m != 16843161) {
            if (paramAttributeSet.getAttributeBooleanValue(b, false)) {
              k = m;
            } else {
              k = -m;
            } 
            arrayOfInt[i] = k;
            k = i + 1;
          } 
        } 
      } 
      b++;
    } 
    return StateSet.trimStateSet(arrayOfInt, i);
  }
  
  int getStateCount() {
    return this.mStateListState.getChildCount();
  }
  
  Drawable getStateDrawable(int paramInt) {
    return this.mStateListState.getChild(paramInt);
  }
  
  int getStateDrawableIndex(int[] paramArrayOfint) {
    return this.mStateListState.indexOfStateSet(paramArrayOfint);
  }
  
  StateListState getStateListState() {
    return this.mStateListState;
  }
  
  int[] getStateSet(int paramInt) {
    return this.mStateListState.mStateSets[paramInt];
  }
  
  public void inflate(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.StateListDrawable);
    setVisible(typedArray.getBoolean(R.styleable.StateListDrawable_android_visible, true), true);
    updateStateFromTypedArray(typedArray);
    updateDensity(paramResources);
    typedArray.recycle();
    inflateChildElements(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    onStateChange(getState());
  }
  
  public boolean isStateful() {
    return true;
  }
  
  public Drawable mutate() {
    if (!this.mMutated && super.mutate() == this) {
      this.mStateListState.mutate();
      this.mMutated = true;
    } 
    return this;
  }
  
  protected boolean onStateChange(int[] paramArrayOfint) {
    null = super.onStateChange(paramArrayOfint);
    int j = this.mStateListState.indexOfStateSet(paramArrayOfint);
    int i = j;
    if (j < 0)
      i = this.mStateListState.indexOfStateSet(StateSet.WILD_CARD); 
    return (selectDrawable(i) || null);
  }
  
  void setConstantState(DrawableContainer.DrawableContainerState paramDrawableContainerState) {
    super.setConstantState(paramDrawableContainerState);
    if (paramDrawableContainerState instanceof StateListState)
      this.mStateListState = (StateListState)paramDrawableContainerState; 
  }
  
  static class StateListState extends DrawableContainer.DrawableContainerState {
    int[][] mStateSets;
    
    StateListState(StateListState param1StateListState, StateListDrawable param1StateListDrawable, Resources param1Resources) {
      super(param1StateListState, param1StateListDrawable, param1Resources);
      if (param1StateListState != null) {
        this.mStateSets = param1StateListState.mStateSets;
      } else {
        this.mStateSets = new int[getCapacity()][];
      } 
    }
    
    int addStateSet(int[] param1ArrayOfint, Drawable param1Drawable) {
      int i = addChild(param1Drawable);
      this.mStateSets[i] = param1ArrayOfint;
      return i;
    }
    
    public void growArray(int param1Int1, int param1Int2) {
      super.growArray(param1Int1, param1Int2);
      int[][] arrayOfInt = new int[param1Int2][];
      System.arraycopy(this.mStateSets, 0, arrayOfInt, 0, param1Int1);
      this.mStateSets = arrayOfInt;
    }
    
    int indexOfStateSet(int[] param1ArrayOfint) {
      int[][] arrayOfInt = this.mStateSets;
      int i = getChildCount();
      for (byte b = 0; b < i; b++) {
        if (StateSet.stateSetMatches(arrayOfInt[b], param1ArrayOfint))
          return b; 
      } 
      return -1;
    }
    
    void mutate() {
      int[][] arrayOfInt1 = this.mStateSets;
      int[][] arrayOfInt2 = new int[arrayOfInt1.length][];
      for (int i = arrayOfInt1.length - 1; i >= 0; i--) {
        arrayOfInt1 = this.mStateSets;
        if (arrayOfInt1[i] != null) {
          int[] arrayOfInt = (int[])arrayOfInt1[i].clone();
        } else {
          arrayOfInt1 = null;
        } 
        arrayOfInt2[i] = (int[])arrayOfInt1;
      } 
      this.mStateSets = arrayOfInt2;
    }
    
    public Drawable newDrawable() {
      return new StateListDrawable(this, null);
    }
    
    public Drawable newDrawable(Resources param1Resources) {
      return new StateListDrawable(this, param1Resources);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\graphics\drawable\StateListDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */