package androidx.vectordrawable.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflaterCompat {
  private static final boolean DBG_ANIMATOR_INFLATER = false;
  
  private static final int MAX_NUM_POINTS = 100;
  
  private static final String TAG = "AnimatorInflater";
  
  private static final int TOGETHER = 0;
  
  private static final int VALUE_TYPE_COLOR = 3;
  
  private static final int VALUE_TYPE_FLOAT = 0;
  
  private static final int VALUE_TYPE_INT = 1;
  
  private static final int VALUE_TYPE_PATH = 2;
  
  private static final int VALUE_TYPE_UNDEFINED = 4;
  
  private static Animator createAnimatorFromXml(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, float paramFloat) throws XmlPullParserException, IOException {
    return createAnimatorFromXml(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser), null, 0, paramFloat);
  }
  
  private static Animator createAnimatorFromXml(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, AnimatorSet paramAnimatorSet, int paramInt, float paramFloat) throws XmlPullParserException, IOException {
    int i;
    int j = paramXmlPullParser.getDepth();
    TypedArray typedArray = null;
    ArrayList<TypedArray> arrayList = null;
    while (true) {
      int k = paramXmlPullParser.next();
      i = 0;
      boolean bool = false;
      if ((k != 3 || paramXmlPullParser.getDepth() > j) && k != 1) {
        ObjectAnimator objectAnimator;
        TypedArray typedArray1;
        if (k != 2)
          continue; 
        String str = paramXmlPullParser.getName();
        if (str.equals("objectAnimator")) {
          objectAnimator = loadObjectAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, paramFloat, paramXmlPullParser);
        } else {
          ValueAnimator valueAnimator;
          if (objectAnimator.equals("animator")) {
            valueAnimator = loadAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, null, paramFloat, paramXmlPullParser);
          } else {
            AnimatorSet animatorSet;
            if (valueAnimator.equals("set")) {
              animatorSet = new AnimatorSet();
              typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_ANIMATOR_SET);
              i = TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "ordering", 0, 0);
              createAnimatorFromXml(paramContext, paramResources, paramTheme, paramXmlPullParser, paramAttributeSet, animatorSet, i, paramFloat);
              typedArray.recycle();
            } else if (animatorSet.equals("propertyValuesHolder")) {
              PropertyValuesHolder[] arrayOfPropertyValuesHolder = loadValues(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser));
              if (arrayOfPropertyValuesHolder != null && typedArray instanceof ValueAnimator)
                ((ValueAnimator)typedArray).setValues(arrayOfPropertyValuesHolder); 
              bool = true;
              typedArray1 = typedArray;
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Unknown animator name: ");
              stringBuilder.append(paramXmlPullParser.getName());
              throw new RuntimeException(stringBuilder.toString());
            } 
          } 
        } 
        typedArray = typedArray1;
        if (paramAnimatorSet != null) {
          typedArray = typedArray1;
          if (!bool) {
            ArrayList<TypedArray> arrayList1 = arrayList;
            if (arrayList == null)
              arrayList1 = new ArrayList(); 
            arrayList1.add(typedArray1);
            typedArray = typedArray1;
            arrayList = arrayList1;
          } 
        } 
        continue;
      } 
      break;
    } 
    if (paramAnimatorSet != null && arrayList != null) {
      Animator[] arrayOfAnimator = new Animator[arrayList.size()];
      Iterator<TypedArray> iterator = arrayList.iterator();
      for (int k = i; iterator.hasNext(); k++)
        arrayOfAnimator[k] = (Animator)iterator.next(); 
      if (paramInt == 0) {
        paramAnimatorSet.playTogether(arrayOfAnimator);
      } else {
        paramAnimatorSet.playSequentially(arrayOfAnimator);
      } 
    } 
    return (Animator)typedArray;
  }
  
  private static Keyframe createNewKeyframe(Keyframe paramKeyframe, float paramFloat) {
    if (paramKeyframe.getType() == float.class) {
      paramKeyframe = Keyframe.ofFloat(paramFloat);
    } else if (paramKeyframe.getType() == int.class) {
      paramKeyframe = Keyframe.ofInt(paramFloat);
    } else {
      paramKeyframe = Keyframe.ofObject(paramFloat);
    } 
    return paramKeyframe;
  }
  
  private static void distributeKeyframes(Keyframe[] paramArrayOfKeyframe, float paramFloat, int paramInt1, int paramInt2) {
    paramFloat /= (paramInt2 - paramInt1 + 2);
    while (paramInt1 <= paramInt2) {
      paramArrayOfKeyframe[paramInt1].setFraction(paramArrayOfKeyframe[paramInt1 - 1].getFraction() + paramFloat);
      paramInt1++;
    } 
  }
  
  private static void dumpKeyframes(Object[] paramArrayOfObject, String paramString) {
    if (paramArrayOfObject != null && paramArrayOfObject.length != 0) {
      Log.d("AnimatorInflater", paramString);
      int i = paramArrayOfObject.length;
      for (byte b = 0; b < i; b++) {
        Float float_;
        Keyframe keyframe = (Keyframe)paramArrayOfObject[b];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Keyframe ");
        stringBuilder.append(b);
        stringBuilder.append(": fraction ");
        float f = keyframe.getFraction();
        String str = "null";
        if (f < 0.0F) {
          paramString = "null";
        } else {
          float_ = Float.valueOf(keyframe.getFraction());
        } 
        stringBuilder.append(float_);
        stringBuilder.append(", , value : ");
        Object object = str;
        if (keyframe.hasValue())
          object = keyframe.getValue(); 
        stringBuilder.append(object);
        Log.d("AnimatorInflater", stringBuilder.toString());
      } 
    } 
  }
  
  private static PropertyValuesHolder getPVH(TypedArray paramTypedArray, int paramInt1, int paramInt2, int paramInt3, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: iload_2
    //   2: invokevirtual peekValue : (I)Landroid/util/TypedValue;
    //   5: astore #12
    //   7: aload #12
    //   9: ifnull -> 18
    //   12: iconst_1
    //   13: istore #9
    //   15: goto -> 21
    //   18: iconst_0
    //   19: istore #9
    //   21: iload #9
    //   23: ifeq -> 36
    //   26: aload #12
    //   28: getfield type : I
    //   31: istore #10
    //   33: goto -> 39
    //   36: iconst_0
    //   37: istore #10
    //   39: aload_0
    //   40: iload_3
    //   41: invokevirtual peekValue : (I)Landroid/util/TypedValue;
    //   44: astore #12
    //   46: aload #12
    //   48: ifnull -> 57
    //   51: iconst_1
    //   52: istore #8
    //   54: goto -> 60
    //   57: iconst_0
    //   58: istore #8
    //   60: iload #8
    //   62: ifeq -> 75
    //   65: aload #12
    //   67: getfield type : I
    //   70: istore #11
    //   72: goto -> 78
    //   75: iconst_0
    //   76: istore #11
    //   78: iload_1
    //   79: istore #7
    //   81: iload_1
    //   82: iconst_4
    //   83: if_icmpne -> 121
    //   86: iload #9
    //   88: ifeq -> 99
    //   91: iload #10
    //   93: invokestatic isColorType : (I)Z
    //   96: ifne -> 112
    //   99: iload #8
    //   101: ifeq -> 118
    //   104: iload #11
    //   106: invokestatic isColorType : (I)Z
    //   109: ifeq -> 118
    //   112: iconst_3
    //   113: istore #7
    //   115: goto -> 121
    //   118: iconst_0
    //   119: istore #7
    //   121: iload #7
    //   123: ifne -> 131
    //   126: iconst_1
    //   127: istore_1
    //   128: goto -> 133
    //   131: iconst_0
    //   132: istore_1
    //   133: aconst_null
    //   134: astore #12
    //   136: aconst_null
    //   137: astore #14
    //   139: iload #7
    //   141: iconst_2
    //   142: if_icmpne -> 340
    //   145: aload_0
    //   146: iload_2
    //   147: invokevirtual getString : (I)Ljava/lang/String;
    //   150: astore #13
    //   152: aload_0
    //   153: iload_3
    //   154: invokevirtual getString : (I)Ljava/lang/String;
    //   157: astore #14
    //   159: aload #13
    //   161: invokestatic createNodesFromPathData : (Ljava/lang/String;)[Landroidx/core/graphics/PathParser$PathDataNode;
    //   164: astore #16
    //   166: aload #14
    //   168: invokestatic createNodesFromPathData : (Ljava/lang/String;)[Landroidx/core/graphics/PathParser$PathDataNode;
    //   171: astore #15
    //   173: aload #16
    //   175: ifnonnull -> 186
    //   178: aload #12
    //   180: astore_0
    //   181: aload #15
    //   183: ifnull -> 728
    //   186: aload #16
    //   188: ifnull -> 307
    //   191: new androidx/vectordrawable/graphics/drawable/AnimatorInflaterCompat$PathDataEvaluator
    //   194: dup
    //   195: invokespecial <init> : ()V
    //   198: astore_0
    //   199: aload #15
    //   201: ifnull -> 288
    //   204: aload #16
    //   206: aload #15
    //   208: invokestatic canMorph : ([Landroidx/core/graphics/PathParser$PathDataNode;[Landroidx/core/graphics/PathParser$PathDataNode;)Z
    //   211: ifeq -> 238
    //   214: aload #4
    //   216: aload_0
    //   217: iconst_2
    //   218: anewarray java/lang/Object
    //   221: dup
    //   222: iconst_0
    //   223: aload #16
    //   225: aastore
    //   226: dup
    //   227: iconst_1
    //   228: aload #15
    //   230: aastore
    //   231: invokestatic ofObject : (Ljava/lang/String;Landroid/animation/TypeEvaluator;[Ljava/lang/Object;)Landroid/animation/PropertyValuesHolder;
    //   234: astore_0
    //   235: goto -> 304
    //   238: new java/lang/StringBuilder
    //   241: dup
    //   242: invokespecial <init> : ()V
    //   245: astore_0
    //   246: aload_0
    //   247: ldc_w ' Can't morph from '
    //   250: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: pop
    //   254: aload_0
    //   255: aload #13
    //   257: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   260: pop
    //   261: aload_0
    //   262: ldc_w ' to '
    //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload_0
    //   270: aload #14
    //   272: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: pop
    //   276: new android/view/InflateException
    //   279: dup
    //   280: aload_0
    //   281: invokevirtual toString : ()Ljava/lang/String;
    //   284: invokespecial <init> : (Ljava/lang/String;)V
    //   287: athrow
    //   288: aload #4
    //   290: aload_0
    //   291: iconst_1
    //   292: anewarray java/lang/Object
    //   295: dup
    //   296: iconst_0
    //   297: aload #16
    //   299: aastore
    //   300: invokestatic ofObject : (Ljava/lang/String;Landroid/animation/TypeEvaluator;[Ljava/lang/Object;)Landroid/animation/PropertyValuesHolder;
    //   303: astore_0
    //   304: goto -> 728
    //   307: aload #12
    //   309: astore_0
    //   310: aload #15
    //   312: ifnull -> 728
    //   315: aload #4
    //   317: new androidx/vectordrawable/graphics/drawable/AnimatorInflaterCompat$PathDataEvaluator
    //   320: dup
    //   321: invokespecial <init> : ()V
    //   324: iconst_1
    //   325: anewarray java/lang/Object
    //   328: dup
    //   329: iconst_0
    //   330: aload #15
    //   332: aastore
    //   333: invokestatic ofObject : (Ljava/lang/String;Landroid/animation/TypeEvaluator;[Ljava/lang/Object;)Landroid/animation/PropertyValuesHolder;
    //   336: astore_0
    //   337: goto -> 728
    //   340: iload #7
    //   342: iconst_3
    //   343: if_icmpne -> 354
    //   346: invokestatic getInstance : ()Landroidx/vectordrawable/graphics/drawable/ArgbEvaluator;
    //   349: astore #13
    //   351: goto -> 357
    //   354: aconst_null
    //   355: astore #13
    //   357: iload_1
    //   358: ifeq -> 505
    //   361: iload #9
    //   363: ifeq -> 460
    //   366: iload #10
    //   368: iconst_5
    //   369: if_icmpne -> 383
    //   372: aload_0
    //   373: iload_2
    //   374: fconst_0
    //   375: invokevirtual getDimension : (IF)F
    //   378: fstore #5
    //   380: goto -> 391
    //   383: aload_0
    //   384: iload_2
    //   385: fconst_0
    //   386: invokevirtual getFloat : (IF)F
    //   389: fstore #5
    //   391: iload #8
    //   393: ifeq -> 443
    //   396: iload #11
    //   398: iconst_5
    //   399: if_icmpne -> 413
    //   402: aload_0
    //   403: iload_3
    //   404: fconst_0
    //   405: invokevirtual getDimension : (IF)F
    //   408: fstore #6
    //   410: goto -> 421
    //   413: aload_0
    //   414: iload_3
    //   415: fconst_0
    //   416: invokevirtual getFloat : (IF)F
    //   419: fstore #6
    //   421: aload #4
    //   423: iconst_2
    //   424: newarray float
    //   426: dup
    //   427: iconst_0
    //   428: fload #5
    //   430: fastore
    //   431: dup
    //   432: iconst_1
    //   433: fload #6
    //   435: fastore
    //   436: invokestatic ofFloat : (Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;
    //   439: astore_0
    //   440: goto -> 499
    //   443: aload #4
    //   445: iconst_1
    //   446: newarray float
    //   448: dup
    //   449: iconst_0
    //   450: fload #5
    //   452: fastore
    //   453: invokestatic ofFloat : (Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;
    //   456: astore_0
    //   457: goto -> 499
    //   460: iload #11
    //   462: iconst_5
    //   463: if_icmpne -> 477
    //   466: aload_0
    //   467: iload_3
    //   468: fconst_0
    //   469: invokevirtual getDimension : (IF)F
    //   472: fstore #5
    //   474: goto -> 485
    //   477: aload_0
    //   478: iload_3
    //   479: fconst_0
    //   480: invokevirtual getFloat : (IF)F
    //   483: fstore #5
    //   485: aload #4
    //   487: iconst_1
    //   488: newarray float
    //   490: dup
    //   491: iconst_0
    //   492: fload #5
    //   494: fastore
    //   495: invokestatic ofFloat : (Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;
    //   498: astore_0
    //   499: aload_0
    //   500: astore #12
    //   502: goto -> 702
    //   505: iload #9
    //   507: ifeq -> 637
    //   510: iload #10
    //   512: iconst_5
    //   513: if_icmpne -> 527
    //   516: aload_0
    //   517: iload_2
    //   518: fconst_0
    //   519: invokevirtual getDimension : (IF)F
    //   522: f2i
    //   523: istore_1
    //   524: goto -> 552
    //   527: iload #10
    //   529: invokestatic isColorType : (I)Z
    //   532: ifeq -> 545
    //   535: aload_0
    //   536: iload_2
    //   537: iconst_0
    //   538: invokevirtual getColor : (II)I
    //   541: istore_1
    //   542: goto -> 552
    //   545: aload_0
    //   546: iload_2
    //   547: iconst_0
    //   548: invokevirtual getInt : (II)I
    //   551: istore_1
    //   552: iload #8
    //   554: ifeq -> 620
    //   557: iload #11
    //   559: iconst_5
    //   560: if_icmpne -> 574
    //   563: aload_0
    //   564: iload_3
    //   565: fconst_0
    //   566: invokevirtual getDimension : (IF)F
    //   569: f2i
    //   570: istore_2
    //   571: goto -> 599
    //   574: iload #11
    //   576: invokestatic isColorType : (I)Z
    //   579: ifeq -> 592
    //   582: aload_0
    //   583: iload_3
    //   584: iconst_0
    //   585: invokevirtual getColor : (II)I
    //   588: istore_2
    //   589: goto -> 599
    //   592: aload_0
    //   593: iload_3
    //   594: iconst_0
    //   595: invokevirtual getInt : (II)I
    //   598: istore_2
    //   599: aload #4
    //   601: iconst_2
    //   602: newarray int
    //   604: dup
    //   605: iconst_0
    //   606: iload_1
    //   607: iastore
    //   608: dup
    //   609: iconst_1
    //   610: iload_2
    //   611: iastore
    //   612: invokestatic ofInt : (Ljava/lang/String;[I)Landroid/animation/PropertyValuesHolder;
    //   615: astore #12
    //   617: goto -> 702
    //   620: aload #4
    //   622: iconst_1
    //   623: newarray int
    //   625: dup
    //   626: iconst_0
    //   627: iload_1
    //   628: iastore
    //   629: invokestatic ofInt : (Ljava/lang/String;[I)Landroid/animation/PropertyValuesHolder;
    //   632: astore #12
    //   634: goto -> 702
    //   637: aload #14
    //   639: astore #12
    //   641: iload #8
    //   643: ifeq -> 702
    //   646: iload #11
    //   648: iconst_5
    //   649: if_icmpne -> 663
    //   652: aload_0
    //   653: iload_3
    //   654: fconst_0
    //   655: invokevirtual getDimension : (IF)F
    //   658: f2i
    //   659: istore_1
    //   660: goto -> 688
    //   663: iload #11
    //   665: invokestatic isColorType : (I)Z
    //   668: ifeq -> 681
    //   671: aload_0
    //   672: iload_3
    //   673: iconst_0
    //   674: invokevirtual getColor : (II)I
    //   677: istore_1
    //   678: goto -> 688
    //   681: aload_0
    //   682: iload_3
    //   683: iconst_0
    //   684: invokevirtual getInt : (II)I
    //   687: istore_1
    //   688: aload #4
    //   690: iconst_1
    //   691: newarray int
    //   693: dup
    //   694: iconst_0
    //   695: iload_1
    //   696: iastore
    //   697: invokestatic ofInt : (Ljava/lang/String;[I)Landroid/animation/PropertyValuesHolder;
    //   700: astore #12
    //   702: aload #12
    //   704: astore_0
    //   705: aload #12
    //   707: ifnull -> 728
    //   710: aload #12
    //   712: astore_0
    //   713: aload #13
    //   715: ifnull -> 728
    //   718: aload #12
    //   720: aload #13
    //   722: invokevirtual setEvaluator : (Landroid/animation/TypeEvaluator;)V
    //   725: aload #12
    //   727: astore_0
    //   728: aload_0
    //   729: areturn
  }
  
  private static int inferValueTypeFromValues(TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    boolean bool1;
    TypedValue typedValue2 = paramTypedArray.peekValue(paramInt1);
    int i = 1;
    boolean bool2 = false;
    if (typedValue2 != null) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    if (paramInt1 != 0) {
      bool1 = typedValue2.type;
    } else {
      bool1 = false;
    } 
    TypedValue typedValue1 = paramTypedArray.peekValue(paramInt2);
    if (typedValue1 != null) {
      paramInt2 = i;
    } else {
      paramInt2 = 0;
    } 
    if (paramInt2 != 0) {
      i = typedValue1.type;
    } else {
      i = 0;
    } 
    if (paramInt1 == 0 || !isColorType(bool1)) {
      paramInt1 = bool2;
      if (paramInt2 != 0) {
        paramInt1 = bool2;
        if (isColorType(i))
          return 3; 
      } 
      return paramInt1;
    } 
    return 3;
  }
  
  private static int inferValueTypeOfKeyframe(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser) {
    boolean bool;
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_KEYFRAME);
    byte b2 = 0;
    TypedValue typedValue = TypedArrayUtils.peekNamedValue(typedArray, paramXmlPullParser, "value", 0);
    if (typedValue != null) {
      bool = true;
    } else {
      bool = false;
    } 
    byte b1 = b2;
    if (bool) {
      b1 = b2;
      if (isColorType(typedValue.type))
        b1 = 3; 
    } 
    typedArray.recycle();
    return b1;
  }
  
  private static boolean isColorType(int paramInt) {
    boolean bool;
    if (paramInt >= 28 && paramInt <= 31) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static Animator loadAnimator(Context paramContext, int paramInt) throws Resources.NotFoundException {
    Animator animator;
    if (Build.VERSION.SDK_INT >= 24) {
      animator = AnimatorInflater.loadAnimator(paramContext, paramInt);
    } else {
      animator = loadAnimator((Context)animator, animator.getResources(), animator.getTheme(), paramInt);
    } 
    return animator;
  }
  
  public static Animator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt) throws Resources.NotFoundException {
    return loadAnimator(paramContext, paramResources, paramTheme, paramInt, 1.0F);
  }
  
  public static Animator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt, float paramFloat) throws Resources.NotFoundException {
    XmlResourceParser xmlResourceParser2 = null;
    XmlResourceParser xmlResourceParser3 = null;
    XmlResourceParser xmlResourceParser1 = null;
    try {
      XmlResourceParser xmlResourceParser = paramResources.getAnimation(paramInt);
      xmlResourceParser1 = xmlResourceParser;
      xmlResourceParser2 = xmlResourceParser;
      xmlResourceParser3 = xmlResourceParser;
      Animator animator = createAnimatorFromXml(paramContext, paramResources, paramTheme, (XmlPullParser)xmlResourceParser, paramFloat);
      if (xmlResourceParser != null)
        xmlResourceParser.close(); 
      return animator;
    } catch (XmlPullParserException xmlPullParserException) {
      xmlResourceParser1 = xmlResourceParser3;
      Resources.NotFoundException notFoundException = new Resources.NotFoundException();
      xmlResourceParser1 = xmlResourceParser3;
      StringBuilder stringBuilder = new StringBuilder();
      xmlResourceParser1 = xmlResourceParser3;
      this();
      xmlResourceParser1 = xmlResourceParser3;
      stringBuilder.append("Can't load animation resource ID #0x");
      xmlResourceParser1 = xmlResourceParser3;
      stringBuilder.append(Integer.toHexString(paramInt));
      xmlResourceParser1 = xmlResourceParser3;
      this(stringBuilder.toString());
      xmlResourceParser1 = xmlResourceParser3;
      notFoundException.initCause((Throwable)xmlPullParserException);
      xmlResourceParser1 = xmlResourceParser3;
      throw notFoundException;
    } catch (IOException iOException) {
      xmlResourceParser1 = xmlResourceParser2;
      Resources.NotFoundException notFoundException = new Resources.NotFoundException();
      xmlResourceParser1 = xmlResourceParser2;
      StringBuilder stringBuilder = new StringBuilder();
      xmlResourceParser1 = xmlResourceParser2;
      this();
      xmlResourceParser1 = xmlResourceParser2;
      stringBuilder.append("Can't load animation resource ID #0x");
      xmlResourceParser1 = xmlResourceParser2;
      stringBuilder.append(Integer.toHexString(paramInt));
      xmlResourceParser1 = xmlResourceParser2;
      this(stringBuilder.toString());
      xmlResourceParser1 = xmlResourceParser2;
      notFoundException.initCause(iOException);
      xmlResourceParser1 = xmlResourceParser2;
      throw notFoundException;
    } finally {}
    if (xmlResourceParser1 != null)
      xmlResourceParser1.close(); 
    throw paramContext;
  }
  
  private static ValueAnimator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, ValueAnimator paramValueAnimator, float paramFloat, XmlPullParser paramXmlPullParser) throws Resources.NotFoundException {
    TypedArray typedArray2 = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_ANIMATOR);
    TypedArray typedArray1 = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
    ValueAnimator valueAnimator = paramValueAnimator;
    if (paramValueAnimator == null)
      valueAnimator = new ValueAnimator(); 
    parseAnimatorFromTypeArray(valueAnimator, typedArray2, typedArray1, paramFloat, paramXmlPullParser);
    int i = TypedArrayUtils.getNamedResourceId(typedArray2, paramXmlPullParser, "interpolator", 0, 0);
    if (i > 0)
      valueAnimator.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(paramContext, i)); 
    typedArray2.recycle();
    if (typedArray1 != null)
      typedArray1.recycle(); 
    return valueAnimator;
  }
  
  private static Keyframe loadKeyframe(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int paramInt, XmlPullParser paramXmlPullParser) throws XmlPullParserException, IOException {
    Keyframe keyframe;
    boolean bool;
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_KEYFRAME);
    float f = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "fraction", 3, -1.0F);
    TypedValue typedValue = TypedArrayUtils.peekNamedValue(typedArray, paramXmlPullParser, "value", 0);
    if (typedValue != null) {
      bool = true;
    } else {
      bool = false;
    } 
    int i = paramInt;
    if (paramInt == 4)
      if (bool && isColorType(typedValue.type)) {
        i = 3;
      } else {
        i = 0;
      }  
    if (bool) {
      if (i != 0) {
        if (i != 1 && i != 3) {
          typedValue = null;
        } else {
          keyframe = Keyframe.ofInt(f, TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "value", 0, 0));
        } 
      } else {
        keyframe = Keyframe.ofFloat(f, TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "value", 0, 0.0F));
      } 
    } else if (i == 0) {
      keyframe = Keyframe.ofFloat(f);
    } else {
      keyframe = Keyframe.ofInt(f);
    } 
    paramInt = TypedArrayUtils.getNamedResourceId(typedArray, paramXmlPullParser, "interpolator", 1, 0);
    if (paramInt > 0)
      keyframe.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(paramContext, paramInt)); 
    typedArray.recycle();
    return keyframe;
  }
  
  private static ObjectAnimator loadObjectAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, float paramFloat, XmlPullParser paramXmlPullParser) throws Resources.NotFoundException {
    ObjectAnimator objectAnimator = new ObjectAnimator();
    loadAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, (ValueAnimator)objectAnimator, paramFloat, paramXmlPullParser);
    return objectAnimator;
  }
  
  private static PropertyValuesHolder loadPvh(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, String paramString, int paramInt) throws XmlPullParserException, IOException {
    PropertyValuesHolder propertyValuesHolder;
    Context context = null;
    ArrayList<Keyframe> arrayList = null;
    int i = paramInt;
    while (true) {
      paramInt = paramXmlPullParser.next();
      if (paramInt != 3 && paramInt != 1) {
        if (paramXmlPullParser.getName().equals("keyframe")) {
          paramInt = i;
          if (i == 4)
            paramInt = inferValueTypeOfKeyframe(paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), paramXmlPullParser); 
          Keyframe keyframe = loadKeyframe(paramContext, paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), paramInt, paramXmlPullParser);
          ArrayList<Keyframe> arrayList1 = arrayList;
          if (keyframe != null) {
            arrayList1 = arrayList;
            if (arrayList == null)
              arrayList1 = new ArrayList(); 
            arrayList1.add(keyframe);
          } 
          paramXmlPullParser.next();
          arrayList = arrayList1;
          i = paramInt;
        } 
        continue;
      } 
      break;
    } 
    paramContext = context;
    if (arrayList != null) {
      int j = arrayList.size();
      paramContext = context;
      if (j > 0) {
        int k = 0;
        Keyframe keyframe1 = arrayList.get(0);
        Keyframe keyframe2 = arrayList.get(j - 1);
        float f = keyframe2.getFraction();
        paramInt = j;
        if (f < 1.0F)
          if (f < 0.0F) {
            keyframe2.setFraction(1.0F);
            paramInt = j;
          } else {
            arrayList.add(arrayList.size(), createNewKeyframe(keyframe2, 1.0F));
            paramInt = j + 1;
          }  
        f = keyframe1.getFraction();
        j = paramInt;
        if (f != 0.0F)
          if (f < 0.0F) {
            keyframe1.setFraction(0.0F);
            j = paramInt;
          } else {
            arrayList.add(0, createNewKeyframe(keyframe1, 0.0F));
            j = paramInt + 1;
          }  
        Keyframe[] arrayOfKeyframe = new Keyframe[j];
        arrayList.toArray(arrayOfKeyframe);
        for (paramInt = k; paramInt < j; paramInt++) {
          keyframe2 = arrayOfKeyframe[paramInt];
          if (keyframe2.getFraction() < 0.0F)
            if (paramInt == 0) {
              keyframe2.setFraction(0.0F);
            } else {
              int m = j - 1;
              if (paramInt == m) {
                keyframe2.setFraction(1.0F);
              } else {
                k = paramInt + 1;
                int n = paramInt;
                while (k < m && arrayOfKeyframe[k].getFraction() < 0.0F) {
                  n = k;
                  k++;
                } 
                distributeKeyframes(arrayOfKeyframe, arrayOfKeyframe[n + 1].getFraction() - arrayOfKeyframe[paramInt - 1].getFraction(), paramInt, n);
              } 
            }  
        } 
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofKeyframe(paramString, arrayOfKeyframe);
        propertyValuesHolder = propertyValuesHolder1;
        if (i == 3) {
          propertyValuesHolder1.setEvaluator(ArgbEvaluator.getInstance());
          propertyValuesHolder = propertyValuesHolder1;
        } 
      } 
    } 
    return propertyValuesHolder;
  }
  
  private static PropertyValuesHolder[] loadValues(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet) throws XmlPullParserException, IOException {
    PropertyValuesHolder[] arrayOfPropertyValuesHolder;
    int i;
    ArrayList<PropertyValuesHolder> arrayList;
    Context context = null;
    PropertyValuesHolder propertyValuesHolder = null;
    while (true) {
      int j = paramXmlPullParser.getEventType();
      i = 0;
      if (j != 3 && j != 1) {
        if (j != 2) {
          paramXmlPullParser.next();
          continue;
        } 
        if (paramXmlPullParser.getName().equals("propertyValuesHolder")) {
          ArrayList<PropertyValuesHolder> arrayList1;
          TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
          String str = TypedArrayUtils.getNamedString(typedArray, paramXmlPullParser, "propertyName", 3);
          i = TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "valueType", 2, 4);
          PropertyValuesHolder propertyValuesHolder1 = loadPvh(paramContext, paramResources, paramTheme, paramXmlPullParser, str, i);
          PropertyValuesHolder propertyValuesHolder2 = propertyValuesHolder1;
          if (propertyValuesHolder1 == null)
            propertyValuesHolder2 = getPVH(typedArray, i, 0, 1, str); 
          propertyValuesHolder1 = propertyValuesHolder;
          if (propertyValuesHolder2 != null) {
            propertyValuesHolder1 = propertyValuesHolder;
            if (propertyValuesHolder == null)
              arrayList1 = new ArrayList(); 
            arrayList1.add(propertyValuesHolder2);
          } 
          typedArray.recycle();
          arrayList = arrayList1;
        } 
        paramXmlPullParser.next();
        continue;
      } 
      break;
    } 
    paramContext = context;
    if (arrayList != null) {
      int j = arrayList.size();
      PropertyValuesHolder[] arrayOfPropertyValuesHolder1 = new PropertyValuesHolder[j];
      while (true) {
        arrayOfPropertyValuesHolder = arrayOfPropertyValuesHolder1;
        if (i < j) {
          arrayOfPropertyValuesHolder1[i] = arrayList.get(i);
          i++;
          continue;
        } 
        break;
      } 
    } 
    return arrayOfPropertyValuesHolder;
  }
  
  private static void parseAnimatorFromTypeArray(ValueAnimator paramValueAnimator, TypedArray paramTypedArray1, TypedArray paramTypedArray2, float paramFloat, XmlPullParser paramXmlPullParser) {
    long l1 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "duration", 1, 300);
    long l2 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "startOffset", 2, 0);
    int i = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "valueType", 7, 4);
    int j = i;
    if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueFrom")) {
      j = i;
      if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueTo")) {
        int k = i;
        if (i == 4)
          k = inferValueTypeFromValues(paramTypedArray1, 5, 6); 
        PropertyValuesHolder propertyValuesHolder = getPVH(paramTypedArray1, k, 5, 6, "");
        j = k;
        if (propertyValuesHolder != null) {
          paramValueAnimator.setValues(new PropertyValuesHolder[] { propertyValuesHolder });
          j = k;
        } 
      } 
    } 
    paramValueAnimator.setDuration(l1);
    paramValueAnimator.setStartDelay(l2);
    paramValueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatCount", 3, 0));
    paramValueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatMode", 4, 1));
    if (paramTypedArray2 != null)
      setupObjectAnimator(paramValueAnimator, paramTypedArray2, j, paramFloat, paramXmlPullParser); 
  }
  
  private static void setupObjectAnimator(ValueAnimator paramValueAnimator, TypedArray paramTypedArray, int paramInt, float paramFloat, XmlPullParser paramXmlPullParser) {
    // Byte code:
    //   0: aload_0
    //   1: checkcast android/animation/ObjectAnimator
    //   4: astore_0
    //   5: aload_1
    //   6: aload #4
    //   8: ldc_w 'pathData'
    //   11: iconst_1
    //   12: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   15: astore #6
    //   17: aload #6
    //   19: ifnull -> 122
    //   22: aload_1
    //   23: aload #4
    //   25: ldc_w 'propertyXName'
    //   28: iconst_2
    //   29: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   32: astore #5
    //   34: aload_1
    //   35: aload #4
    //   37: ldc_w 'propertyYName'
    //   40: iconst_3
    //   41: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   44: astore #4
    //   46: iload_2
    //   47: iconst_2
    //   48: if_icmpeq -> 51
    //   51: aload #5
    //   53: ifnonnull -> 101
    //   56: aload #4
    //   58: ifnull -> 64
    //   61: goto -> 101
    //   64: new java/lang/StringBuilder
    //   67: dup
    //   68: invokespecial <init> : ()V
    //   71: astore_0
    //   72: aload_0
    //   73: aload_1
    //   74: invokevirtual getPositionDescription : ()Ljava/lang/String;
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload_0
    //   82: ldc_w ' propertyXName or propertyYName is needed for PathData'
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: new android/view/InflateException
    //   92: dup
    //   93: aload_0
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: invokespecial <init> : (Ljava/lang/String;)V
    //   100: athrow
    //   101: aload #6
    //   103: invokestatic createPathFromPathData : (Ljava/lang/String;)Landroid/graphics/Path;
    //   106: aload_0
    //   107: fload_3
    //   108: ldc_w 0.5
    //   111: fmul
    //   112: aload #5
    //   114: aload #4
    //   116: invokestatic setupPathMotion : (Landroid/graphics/Path;Landroid/animation/ObjectAnimator;FLjava/lang/String;Ljava/lang/String;)V
    //   119: goto -> 136
    //   122: aload_0
    //   123: aload_1
    //   124: aload #4
    //   126: ldc_w 'propertyName'
    //   129: iconst_0
    //   130: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   133: invokevirtual setPropertyName : (Ljava/lang/String;)V
    //   136: return
  }
  
  private static void setupPathMotion(Path paramPath, ObjectAnimator paramObjectAnimator, float paramFloat, String paramString1, String paramString2) {
    PathMeasure pathMeasure = new PathMeasure(paramPath, false);
    ArrayList<Float> arrayList = new ArrayList();
    float f2 = 0.0F;
    arrayList.add(Float.valueOf(0.0F));
    float f1 = 0.0F;
    while (true) {
      float f = f1 + pathMeasure.getLength();
      arrayList.add(Float.valueOf(f));
      f1 = f;
      if (!pathMeasure.nextContour()) {
        PathMeasure pathMeasure1 = new PathMeasure(paramPath, false);
        int j = Math.min(100, (int)(f / paramFloat) + 1);
        float[] arrayOfFloat1 = new float[j];
        float[] arrayOfFloat2 = new float[j];
        float[] arrayOfFloat3 = new float[2];
        f1 = f / (j - 1);
        byte b = 0;
        int i = 0;
        paramFloat = f2;
        while (true) {
          PropertyValuesHolder propertyValuesHolder;
          pathMeasure = null;
          if (b < j) {
            pathMeasure1.getPosTan(paramFloat - ((Float)arrayList.get(i)).floatValue(), arrayOfFloat3, null);
            arrayOfFloat1[b] = arrayOfFloat3[0];
            arrayOfFloat2[b] = arrayOfFloat3[1];
            paramFloat += f1;
            int m = i + 1;
            int k = i;
            if (m < arrayList.size()) {
              k = i;
              if (paramFloat > ((Float)arrayList.get(m)).floatValue()) {
                pathMeasure1.nextContour();
                k = m;
              } 
            } 
            b++;
            i = k;
            continue;
          } 
          if (paramString1 != null) {
            PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat(paramString1, arrayOfFloat1);
          } else {
            arrayOfFloat1 = null;
          } 
          PathMeasure pathMeasure2 = pathMeasure;
          if (paramString2 != null)
            propertyValuesHolder = PropertyValuesHolder.ofFloat(paramString2, arrayOfFloat2); 
          if (arrayOfFloat1 == null) {
            paramObjectAnimator.setValues(new PropertyValuesHolder[] { propertyValuesHolder });
          } else if (propertyValuesHolder == null) {
            paramObjectAnimator.setValues(new PropertyValuesHolder[] { (PropertyValuesHolder)arrayOfFloat1 });
          } else {
            paramObjectAnimator.setValues(new PropertyValuesHolder[] { (PropertyValuesHolder)arrayOfFloat1, propertyValuesHolder });
          } 
          return;
        } 
        break;
      } 
    } 
  }
  
  private static class PathDataEvaluator implements TypeEvaluator<PathParser.PathDataNode[]> {
    private PathParser.PathDataNode[] mNodeArray;
    
    PathDataEvaluator() {}
    
    PathDataEvaluator(PathParser.PathDataNode[] param1ArrayOfPathDataNode) {
      this.mNodeArray = param1ArrayOfPathDataNode;
    }
    
    public PathParser.PathDataNode[] evaluate(float param1Float, PathParser.PathDataNode[] param1ArrayOfPathDataNode1, PathParser.PathDataNode[] param1ArrayOfPathDataNode2) {
      if (PathParser.canMorph(param1ArrayOfPathDataNode1, param1ArrayOfPathDataNode2)) {
        if (!PathParser.canMorph(this.mNodeArray, param1ArrayOfPathDataNode1))
          this.mNodeArray = PathParser.deepCopyNodes(param1ArrayOfPathDataNode1); 
        for (byte b = 0; b < param1ArrayOfPathDataNode1.length; b++)
          this.mNodeArray[b].interpolatePathDataNode(param1ArrayOfPathDataNode1[b], param1ArrayOfPathDataNode2[b], param1Float); 
        return this.mNodeArray;
      } 
      throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\vectordrawable\graphics\drawable\AnimatorInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */