package androidx.appcompat.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.Xml;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedStateListDrawableCompat extends StateListDrawable implements TintAwareDrawable {
  private static final String ELEMENT_ITEM = "item";
  
  private static final String ELEMENT_TRANSITION = "transition";
  
  private static final String ITEM_MISSING_DRAWABLE_ERROR = ": <item> tag requires a 'drawable' attribute or child tag defining a drawable";
  
  private static final String LOGTAG = AnimatedStateListDrawableCompat.class.getSimpleName();
  
  private static final String TRANSITION_MISSING_DRAWABLE_ERROR = ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable";
  
  private static final String TRANSITION_MISSING_FROM_TO_ID = ": <transition> tag requires 'fromId' & 'toId' attributes";
  
  private boolean mMutated;
  
  private AnimatedStateListState mState;
  
  private Transition mTransition;
  
  private int mTransitionFromIndex = -1;
  
  private int mTransitionToIndex = -1;
  
  public AnimatedStateListDrawableCompat() {
    this((AnimatedStateListState)null, (Resources)null);
  }
  
  AnimatedStateListDrawableCompat(AnimatedStateListState paramAnimatedStateListState, Resources paramResources) {
    super((StateListDrawable.StateListState)null);
    setConstantState(new AnimatedStateListState(paramAnimatedStateListState, this, paramResources));
    onStateChange(getState());
    jumpToCurrentState();
  }
  
  public static AnimatedStateListDrawableCompat create(Context paramContext, int paramInt, Resources.Theme paramTheme) {
    try {
      Resources resources = paramContext.getResources();
      XmlResourceParser xmlResourceParser = resources.getXml(paramInt);
      AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
      while (true) {
        paramInt = xmlResourceParser.next();
        if (paramInt != 2 && paramInt != 1)
          continue; 
        break;
      } 
      if (paramInt == 2)
        return createFromXmlInner(paramContext, resources, (XmlPullParser)xmlResourceParser, attributeSet, paramTheme); 
      XmlPullParserException xmlPullParserException = new XmlPullParserException();
      this("No start tag found");
      throw xmlPullParserException;
    } catch (XmlPullParserException xmlPullParserException) {
      Log.e(LOGTAG, "parser error", (Throwable)xmlPullParserException);
    } catch (IOException iOException) {
      Log.e(LOGTAG, "parser error", iOException);
    } 
    return null;
  }
  
  public static AnimatedStateListDrawableCompat createFromXmlInner(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws IOException, XmlPullParserException {
    AnimatedStateListDrawableCompat animatedStateListDrawableCompat;
    String str = paramXmlPullParser.getName();
    if (str.equals("animated-selector")) {
      animatedStateListDrawableCompat = new AnimatedStateListDrawableCompat();
      animatedStateListDrawableCompat.inflate(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
      return animatedStateListDrawableCompat;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramXmlPullParser.getPositionDescription());
    stringBuilder.append(": invalid animated-selector tag ");
    stringBuilder.append((String)animatedStateListDrawableCompat);
    throw new XmlPullParserException(stringBuilder.toString());
  }
  
  private void inflateChildElements(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    int i = paramXmlPullParser.getDepth() + 1;
    while (true) {
      int j = paramXmlPullParser.next();
      if (j != 1) {
        int k = paramXmlPullParser.getDepth();
        if (k >= i || j != 3) {
          if (j != 2 || k > i)
            continue; 
          if (paramXmlPullParser.getName().equals("item")) {
            parseItem(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
            continue;
          } 
          if (paramXmlPullParser.getName().equals("transition"))
            parseTransition(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme); 
          continue;
        } 
      } 
      break;
    } 
  }
  
  private void init() {
    onStateChange(getState());
  }
  
  private int parseItem(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    Context context;
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.AnimatedStateListDrawableItem);
    int i = typedArray.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_id, 0);
    int j = typedArray.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_drawable, -1);
    if (j > 0) {
      context = (Context)ResourceManagerInternal.get().getDrawable(paramContext, j);
    } else {
      context = null;
    } 
    typedArray.recycle();
    int[] arrayOfInt = extractStateSet(paramAttributeSet);
    paramContext = context;
    if (context == null)
      while (true) {
        j = paramXmlPullParser.next();
        if (j == 4)
          continue; 
        if (j == 2) {
          if (paramXmlPullParser.getName().equals("vector")) {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
            break;
          } 
          if (Build.VERSION.SDK_INT >= 21) {
            Drawable drawable = Drawable.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
          } else {
            Drawable drawable = Drawable.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet);
          } 
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append(paramXmlPullParser.getPositionDescription());
          stringBuilder.append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
          throw new XmlPullParserException(stringBuilder.toString());
        } 
        if (stringBuilder != null)
          return this.mState.addStateSet(arrayOfInt, (Drawable)stringBuilder, i); 
        stringBuilder = new StringBuilder();
        stringBuilder.append(paramXmlPullParser.getPositionDescription());
        stringBuilder.append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
        throw new XmlPullParserException(stringBuilder.toString());
      }  
    if (stringBuilder != null)
      return this.mState.addStateSet(arrayOfInt, (Drawable)stringBuilder, i); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramXmlPullParser.getPositionDescription());
    stringBuilder.append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
    throw new XmlPullParserException(stringBuilder.toString());
  }
  
  private int parseTransition(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    // Byte code:
    //   0: aload_2
    //   1: aload #5
    //   3: aload #4
    //   5: getstatic androidx/appcompat/resources/R$styleable.AnimatedStateListDrawableTransition : [I
    //   8: invokestatic obtainAttributes : (Landroid/content/res/Resources;Landroid/content/res/Resources$Theme;Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;
    //   11: astore #10
    //   13: aload #10
    //   15: getstatic androidx/appcompat/resources/R$styleable.AnimatedStateListDrawableTransition_android_fromId : I
    //   18: iconst_m1
    //   19: invokevirtual getResourceId : (II)I
    //   22: istore #7
    //   24: aload #10
    //   26: getstatic androidx/appcompat/resources/R$styleable.AnimatedStateListDrawableTransition_android_toId : I
    //   29: iconst_m1
    //   30: invokevirtual getResourceId : (II)I
    //   33: istore #6
    //   35: aload #10
    //   37: getstatic androidx/appcompat/resources/R$styleable.AnimatedStateListDrawableTransition_android_drawable : I
    //   40: iconst_m1
    //   41: invokevirtual getResourceId : (II)I
    //   44: istore #8
    //   46: iload #8
    //   48: ifle -> 65
    //   51: invokestatic get : ()Landroidx/appcompat/widget/ResourceManagerInternal;
    //   54: aload_1
    //   55: iload #8
    //   57: invokevirtual getDrawable : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   60: astore #11
    //   62: goto -> 68
    //   65: aconst_null
    //   66: astore #11
    //   68: aload #10
    //   70: getstatic androidx/appcompat/resources/R$styleable.AnimatedStateListDrawableTransition_android_reversible : I
    //   73: iconst_0
    //   74: invokevirtual getBoolean : (IZ)Z
    //   77: istore #9
    //   79: aload #10
    //   81: invokevirtual recycle : ()V
    //   84: aload #11
    //   86: astore #10
    //   88: aload #11
    //   90: ifnonnull -> 218
    //   93: aload_3
    //   94: invokeinterface next : ()I
    //   99: istore #8
    //   101: iload #8
    //   103: iconst_4
    //   104: if_icmpne -> 110
    //   107: goto -> 93
    //   110: iload #8
    //   112: iconst_2
    //   113: if_icmpne -> 180
    //   116: aload_3
    //   117: invokeinterface getName : ()Ljava/lang/String;
    //   122: ldc_w 'animated-vector'
    //   125: invokevirtual equals : (Ljava/lang/Object;)Z
    //   128: ifeq -> 146
    //   131: aload_1
    //   132: aload_2
    //   133: aload_3
    //   134: aload #4
    //   136: aload #5
    //   138: invokestatic createFromXmlInner : (Landroid/content/Context;Landroid/content/res/Resources;Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/content/res/Resources$Theme;)Landroidx/vectordrawable/graphics/drawable/AnimatedVectorDrawableCompat;
    //   141: astore #10
    //   143: goto -> 218
    //   146: getstatic android/os/Build$VERSION.SDK_INT : I
    //   149: bipush #21
    //   151: if_icmplt -> 168
    //   154: aload_2
    //   155: aload_3
    //   156: aload #4
    //   158: aload #5
    //   160: invokestatic createFromXmlInner : (Landroid/content/res/Resources;Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;
    //   163: astore #10
    //   165: goto -> 218
    //   168: aload_2
    //   169: aload_3
    //   170: aload #4
    //   172: invokestatic createFromXmlInner : (Landroid/content/res/Resources;Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;)Landroid/graphics/drawable/Drawable;
    //   175: astore #10
    //   177: goto -> 218
    //   180: new java/lang/StringBuilder
    //   183: dup
    //   184: invokespecial <init> : ()V
    //   187: astore_1
    //   188: aload_1
    //   189: aload_3
    //   190: invokeinterface getPositionDescription : ()Ljava/lang/String;
    //   195: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: aload_1
    //   200: ldc ': <transition> tag requires a 'drawable' attribute or child tag defining a drawable'
    //   202: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: pop
    //   206: new org/xmlpull/v1/XmlPullParserException
    //   209: dup
    //   210: aload_1
    //   211: invokevirtual toString : ()Ljava/lang/String;
    //   214: invokespecial <init> : (Ljava/lang/String;)V
    //   217: athrow
    //   218: aload #10
    //   220: ifnull -> 289
    //   223: iload #7
    //   225: iconst_m1
    //   226: if_icmpeq -> 251
    //   229: iload #6
    //   231: iconst_m1
    //   232: if_icmpeq -> 251
    //   235: aload_0
    //   236: getfield mState : Landroidx/appcompat/graphics/drawable/AnimatedStateListDrawableCompat$AnimatedStateListState;
    //   239: iload #7
    //   241: iload #6
    //   243: aload #10
    //   245: iload #9
    //   247: invokevirtual addTransition : (IILandroid/graphics/drawable/Drawable;Z)I
    //   250: ireturn
    //   251: new java/lang/StringBuilder
    //   254: dup
    //   255: invokespecial <init> : ()V
    //   258: astore_1
    //   259: aload_1
    //   260: aload_3
    //   261: invokeinterface getPositionDescription : ()Ljava/lang/String;
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload_1
    //   271: ldc ': <transition> tag requires 'fromId' & 'toId' attributes'
    //   273: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: new org/xmlpull/v1/XmlPullParserException
    //   280: dup
    //   281: aload_1
    //   282: invokevirtual toString : ()Ljava/lang/String;
    //   285: invokespecial <init> : (Ljava/lang/String;)V
    //   288: athrow
    //   289: new java/lang/StringBuilder
    //   292: dup
    //   293: invokespecial <init> : ()V
    //   296: astore_1
    //   297: aload_1
    //   298: aload_3
    //   299: invokeinterface getPositionDescription : ()Ljava/lang/String;
    //   304: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   307: pop
    //   308: aload_1
    //   309: ldc ': <transition> tag requires a 'drawable' attribute or child tag defining a drawable'
    //   311: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: new org/xmlpull/v1/XmlPullParserException
    //   318: dup
    //   319: aload_1
    //   320: invokevirtual toString : ()Ljava/lang/String;
    //   323: invokespecial <init> : (Ljava/lang/String;)V
    //   326: athrow
  }
  
  private boolean selectTransition(int paramInt) {
    int i;
    AnimatableTransition animatableTransition;
    Transition transition = this.mTransition;
    if (transition != null) {
      if (paramInt == this.mTransitionToIndex)
        return true; 
      if (paramInt == this.mTransitionFromIndex && transition.canReverse()) {
        transition.reverse();
        this.mTransitionToIndex = this.mTransitionFromIndex;
        this.mTransitionFromIndex = paramInt;
        return true;
      } 
      i = this.mTransitionToIndex;
      transition.stop();
    } else {
      i = getCurrentIndex();
    } 
    this.mTransition = null;
    this.mTransitionFromIndex = -1;
    this.mTransitionToIndex = -1;
    AnimatedStateListState animatedStateListState = this.mState;
    int j = animatedStateListState.getKeyframeIdAt(i);
    int k = animatedStateListState.getKeyframeIdAt(paramInt);
    if (k == 0 || j == 0)
      return false; 
    int m = animatedStateListState.indexOfTransition(j, k);
    if (m < 0)
      return false; 
    boolean bool = animatedStateListState.transitionHasReversibleFlag(j, k);
    selectDrawable(m);
    Drawable drawable = getCurrent();
    if (drawable instanceof AnimationDrawable) {
      boolean bool1 = animatedStateListState.isTransitionReversed(j, k);
      AnimationDrawableTransition animationDrawableTransition = new AnimationDrawableTransition((AnimationDrawable)drawable, bool1, bool);
    } else if (drawable instanceof AnimatedVectorDrawableCompat) {
      AnimatedVectorDrawableTransition animatedVectorDrawableTransition = new AnimatedVectorDrawableTransition((AnimatedVectorDrawableCompat)drawable);
    } else {
      if (drawable instanceof Animatable) {
        animatableTransition = new AnimatableTransition((Animatable)drawable);
        animatableTransition.start();
        this.mTransition = animatableTransition;
        this.mTransitionFromIndex = i;
        this.mTransitionToIndex = paramInt;
        return true;
      } 
      return false;
    } 
    animatableTransition.start();
    this.mTransition = animatableTransition;
    this.mTransitionFromIndex = i;
    this.mTransitionToIndex = paramInt;
    return true;
  }
  
  private void updateStateFromTypedArray(TypedArray paramTypedArray) {
    AnimatedStateListState animatedStateListState = this.mState;
    if (Build.VERSION.SDK_INT >= 21)
      animatedStateListState.mChangingConfigurations |= paramTypedArray.getChangingConfigurations(); 
    animatedStateListState.setVariablePadding(paramTypedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_variablePadding, animatedStateListState.mVariablePadding));
    animatedStateListState.setConstantSize(paramTypedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_constantSize, animatedStateListState.mConstantSize));
    animatedStateListState.setEnterFadeDuration(paramTypedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_enterFadeDuration, animatedStateListState.mEnterFadeDuration));
    animatedStateListState.setExitFadeDuration(paramTypedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_exitFadeDuration, animatedStateListState.mExitFadeDuration));
    setDither(paramTypedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_dither, animatedStateListState.mDither));
  }
  
  public void addState(int[] paramArrayOfint, Drawable paramDrawable, int paramInt) {
    if (paramDrawable != null) {
      this.mState.addStateSet(paramArrayOfint, paramDrawable, paramInt);
      onStateChange(getState());
      return;
    } 
    throw new IllegalArgumentException("Drawable must not be null");
  }
  
  public <T extends Drawable & Animatable> void addTransition(int paramInt1, int paramInt2, T paramT, boolean paramBoolean) {
    if (paramT != null) {
      this.mState.addTransition(paramInt1, paramInt2, (Drawable)paramT, paramBoolean);
      return;
    } 
    throw new IllegalArgumentException("Transition drawable must not be null");
  }
  
  void clearMutated() {
    super.clearMutated();
    this.mMutated = false;
  }
  
  AnimatedStateListState cloneConstantState() {
    return new AnimatedStateListState(this.mState, this, null);
  }
  
  public void inflate(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.AnimatedStateListDrawableCompat);
    setVisible(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_visible, true), true);
    updateStateFromTypedArray(typedArray);
    updateDensity(paramResources);
    typedArray.recycle();
    inflateChildElements(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    init();
  }
  
  public boolean isStateful() {
    return true;
  }
  
  public void jumpToCurrentState() {
    super.jumpToCurrentState();
    Transition transition = this.mTransition;
    if (transition != null) {
      transition.stop();
      this.mTransition = null;
      selectDrawable(this.mTransitionToIndex);
      this.mTransitionToIndex = -1;
      this.mTransitionFromIndex = -1;
    } 
  }
  
  public Drawable mutate() {
    if (!this.mMutated && super.mutate() == this) {
      this.mState.mutate();
      this.mMutated = true;
    } 
    return this;
  }
  
  protected boolean onStateChange(int[] paramArrayOfint) {
    boolean bool1;
    int i = this.mState.indexOfKeyframe(paramArrayOfint);
    if (i != getCurrentIndex() && (selectTransition(i) || selectDrawable(i))) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    Drawable drawable = getCurrent();
    boolean bool2 = bool1;
    if (drawable != null)
      bool2 = bool1 | drawable.setState(paramArrayOfint); 
    return bool2;
  }
  
  void setConstantState(DrawableContainer.DrawableContainerState paramDrawableContainerState) {
    super.setConstantState(paramDrawableContainerState);
    if (paramDrawableContainerState instanceof AnimatedStateListState)
      this.mState = (AnimatedStateListState)paramDrawableContainerState; 
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2) {
    boolean bool = super.setVisible(paramBoolean1, paramBoolean2);
    if (this.mTransition != null && (bool || paramBoolean2))
      if (paramBoolean1) {
        this.mTransition.start();
      } else {
        jumpToCurrentState();
      }  
    return bool;
  }
  
  private static class AnimatableTransition extends Transition {
    private final Animatable mA;
    
    AnimatableTransition(Animatable param1Animatable) {
      this.mA = param1Animatable;
    }
    
    public void start() {
      this.mA.start();
    }
    
    public void stop() {
      this.mA.stop();
    }
  }
  
  static class AnimatedStateListState extends StateListDrawable.StateListState {
    private static final long REVERSED_BIT = 4294967296L;
    
    private static final long REVERSIBLE_FLAG_BIT = 8589934592L;
    
    SparseArrayCompat<Integer> mStateIds;
    
    LongSparseArray<Long> mTransitions;
    
    AnimatedStateListState(AnimatedStateListState param1AnimatedStateListState, AnimatedStateListDrawableCompat param1AnimatedStateListDrawableCompat, Resources param1Resources) {
      super(param1AnimatedStateListState, param1AnimatedStateListDrawableCompat, param1Resources);
      if (param1AnimatedStateListState != null) {
        this.mTransitions = param1AnimatedStateListState.mTransitions;
        this.mStateIds = param1AnimatedStateListState.mStateIds;
      } else {
        this.mTransitions = new LongSparseArray();
        this.mStateIds = new SparseArrayCompat();
      } 
    }
    
    private static long generateTransitionKey(int param1Int1, int param1Int2) {
      long l = param1Int1;
      return param1Int2 | l << 32L;
    }
    
    int addStateSet(int[] param1ArrayOfint, Drawable param1Drawable, int param1Int) {
      int i = addStateSet(param1ArrayOfint, param1Drawable);
      this.mStateIds.put(i, Integer.valueOf(param1Int));
      return i;
    }
    
    int addTransition(int param1Int1, int param1Int2, Drawable param1Drawable, boolean param1Boolean) {
      long l1;
      int i = addChild(param1Drawable);
      long l2 = generateTransitionKey(param1Int1, param1Int2);
      if (param1Boolean) {
        l1 = 8589934592L;
      } else {
        l1 = 0L;
      } 
      LongSparseArray<Long> longSparseArray = this.mTransitions;
      long l3 = i;
      longSparseArray.append(l2, Long.valueOf(l3 | l1));
      if (param1Boolean) {
        l2 = generateTransitionKey(param1Int2, param1Int1);
        this.mTransitions.append(l2, Long.valueOf(0x100000000L | l3 | l1));
      } 
      return i;
    }
    
    int getKeyframeIdAt(int param1Int) {
      boolean bool = false;
      if (param1Int < 0) {
        param1Int = bool;
      } else {
        param1Int = ((Integer)this.mStateIds.get(param1Int, Integer.valueOf(0))).intValue();
      } 
      return param1Int;
    }
    
    int indexOfKeyframe(int[] param1ArrayOfint) {
      int i = indexOfStateSet(param1ArrayOfint);
      return (i >= 0) ? i : indexOfStateSet(StateSet.WILD_CARD);
    }
    
    int indexOfTransition(int param1Int1, int param1Int2) {
      long l = generateTransitionKey(param1Int1, param1Int2);
      return (int)((Long)this.mTransitions.get(l, Long.valueOf(-1L))).longValue();
    }
    
    boolean isTransitionReversed(int param1Int1, int param1Int2) {
      boolean bool;
      long l = generateTransitionKey(param1Int1, param1Int2);
      if ((((Long)this.mTransitions.get(l, Long.valueOf(-1L))).longValue() & 0x100000000L) != 0L) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    void mutate() {
      this.mTransitions = this.mTransitions.clone();
      this.mStateIds = this.mStateIds.clone();
    }
    
    public Drawable newDrawable() {
      return new AnimatedStateListDrawableCompat(this, null);
    }
    
    public Drawable newDrawable(Resources param1Resources) {
      return new AnimatedStateListDrawableCompat(this, param1Resources);
    }
    
    boolean transitionHasReversibleFlag(int param1Int1, int param1Int2) {
      boolean bool;
      long l = generateTransitionKey(param1Int1, param1Int2);
      if ((((Long)this.mTransitions.get(l, Long.valueOf(-1L))).longValue() & 0x200000000L) != 0L) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
  
  private static class AnimatedVectorDrawableTransition extends Transition {
    private final AnimatedVectorDrawableCompat mAvd;
    
    AnimatedVectorDrawableTransition(AnimatedVectorDrawableCompat param1AnimatedVectorDrawableCompat) {
      this.mAvd = param1AnimatedVectorDrawableCompat;
    }
    
    public void start() {
      this.mAvd.start();
    }
    
    public void stop() {
      this.mAvd.stop();
    }
  }
  
  private static class AnimationDrawableTransition extends Transition {
    private final ObjectAnimator mAnim;
    
    private final boolean mHasReversibleFlag;
    
    AnimationDrawableTransition(AnimationDrawable param1AnimationDrawable, boolean param1Boolean1, boolean param1Boolean2) {
      boolean bool;
      int i = param1AnimationDrawable.getNumberOfFrames();
      if (param1Boolean1) {
        bool = i - 1;
      } else {
        bool = false;
      } 
      if (param1Boolean1) {
        i = 0;
      } else {
        i--;
      } 
      AnimatedStateListDrawableCompat.FrameInterpolator frameInterpolator = new AnimatedStateListDrawableCompat.FrameInterpolator(param1AnimationDrawable, param1Boolean1);
      ObjectAnimator objectAnimator = ObjectAnimator.ofInt(param1AnimationDrawable, "currentIndex", new int[] { bool, i });
      if (Build.VERSION.SDK_INT >= 18)
        objectAnimator.setAutoCancel(true); 
      objectAnimator.setDuration(frameInterpolator.getTotalDuration());
      objectAnimator.setInterpolator(frameInterpolator);
      this.mHasReversibleFlag = param1Boolean2;
      this.mAnim = objectAnimator;
    }
    
    public boolean canReverse() {
      return this.mHasReversibleFlag;
    }
    
    public void reverse() {
      this.mAnim.reverse();
    }
    
    public void start() {
      this.mAnim.start();
    }
    
    public void stop() {
      this.mAnim.cancel();
    }
  }
  
  private static class FrameInterpolator implements TimeInterpolator {
    private int[] mFrameTimes;
    
    private int mFrames;
    
    private int mTotalDuration;
    
    FrameInterpolator(AnimationDrawable param1AnimationDrawable, boolean param1Boolean) {
      updateFrames(param1AnimationDrawable, param1Boolean);
    }
    
    public float getInterpolation(float param1Float) {
      int i = (int)(param1Float * this.mTotalDuration + 0.5F);
      int j = this.mFrames;
      int[] arrayOfInt = this.mFrameTimes;
      byte b;
      for (b = 0; b < j && i >= arrayOfInt[b]; b++)
        i -= arrayOfInt[b]; 
      if (b < j) {
        param1Float = i / this.mTotalDuration;
      } else {
        param1Float = 0.0F;
      } 
      return b / j + param1Float;
    }
    
    int getTotalDuration() {
      return this.mTotalDuration;
    }
    
    int updateFrames(AnimationDrawable param1AnimationDrawable, boolean param1Boolean) {
      int j = param1AnimationDrawable.getNumberOfFrames();
      this.mFrames = j;
      int[] arrayOfInt = this.mFrameTimes;
      if (arrayOfInt == null || arrayOfInt.length < j)
        this.mFrameTimes = new int[j]; 
      arrayOfInt = this.mFrameTimes;
      byte b = 0;
      int i = 0;
      while (b < j) {
        if (param1Boolean) {
          k = j - b - 1;
        } else {
          k = b;
        } 
        int k = param1AnimationDrawable.getDuration(k);
        arrayOfInt[b] = k;
        i += k;
        b++;
      } 
      this.mTotalDuration = i;
      return i;
    }
  }
  
  private static abstract class Transition {
    private Transition() {}
    
    public boolean canReverse() {
      return false;
    }
    
    public void reverse() {}
    
    public abstract void start();
    
    public abstract void stop();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\graphics\drawable\AnimatedStateListDrawableCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */