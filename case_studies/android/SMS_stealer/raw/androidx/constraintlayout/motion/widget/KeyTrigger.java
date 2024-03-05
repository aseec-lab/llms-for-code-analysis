package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public class KeyTrigger extends Key {
  public static final int KEY_TYPE = 5;
  
  static final String NAME = "KeyTrigger";
  
  private static final String TAG = "KeyTrigger";
  
  RectF mCollisionRect = new RectF();
  
  private String mCross = null;
  
  private int mCurveFit = -1;
  
  private Method mFireCross;
  
  private boolean mFireCrossReset = true;
  
  private float mFireLastPos;
  
  private Method mFireNegativeCross;
  
  private boolean mFireNegativeReset = true;
  
  private Method mFirePositiveCross;
  
  private boolean mFirePositiveReset = true;
  
  private float mFireThreshold = Float.NaN;
  
  private String mNegativeCross = null;
  
  private String mPositiveCross = null;
  
  private boolean mPostLayout = false;
  
  RectF mTargetRect = new RectF();
  
  private int mTriggerCollisionId = UNSET;
  
  private View mTriggerCollisionView = null;
  
  private int mTriggerID = UNSET;
  
  private int mTriggerReceiver = UNSET;
  
  float mTriggerSlack = 0.1F;
  
  private void setUpRect(RectF paramRectF, View paramView, boolean paramBoolean) {
    paramRectF.top = paramView.getTop();
    paramRectF.bottom = paramView.getBottom();
    paramRectF.left = paramView.getLeft();
    paramRectF.right = paramView.getRight();
    if (paramBoolean)
      paramView.getMatrix().mapRect(paramRectF); 
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {}
  
  public void conditionallyFire(float paramFloat, View paramView) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTriggerCollisionId : I
    //   4: istore #6
    //   6: getstatic androidx/constraintlayout/motion/widget/KeyTrigger.UNSET : I
    //   9: istore #5
    //   11: iconst_1
    //   12: istore #7
    //   14: iload #6
    //   16: iload #5
    //   18: if_icmpeq -> 192
    //   21: aload_0
    //   22: getfield mTriggerCollisionView : Landroid/view/View;
    //   25: ifnonnull -> 46
    //   28: aload_0
    //   29: aload_2
    //   30: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   33: checkcast android/view/ViewGroup
    //   36: aload_0
    //   37: getfield mTriggerCollisionId : I
    //   40: invokevirtual findViewById : (I)Landroid/view/View;
    //   43: putfield mTriggerCollisionView : Landroid/view/View;
    //   46: aload_0
    //   47: aload_0
    //   48: getfield mCollisionRect : Landroid/graphics/RectF;
    //   51: aload_0
    //   52: getfield mTriggerCollisionView : Landroid/view/View;
    //   55: aload_0
    //   56: getfield mPostLayout : Z
    //   59: invokespecial setUpRect : (Landroid/graphics/RectF;Landroid/view/View;Z)V
    //   62: aload_0
    //   63: aload_0
    //   64: getfield mTargetRect : Landroid/graphics/RectF;
    //   67: aload_2
    //   68: aload_0
    //   69: getfield mPostLayout : Z
    //   72: invokespecial setUpRect : (Landroid/graphics/RectF;Landroid/view/View;Z)V
    //   75: aload_0
    //   76: getfield mCollisionRect : Landroid/graphics/RectF;
    //   79: aload_0
    //   80: getfield mTargetRect : Landroid/graphics/RectF;
    //   83: invokevirtual intersect : (Landroid/graphics/RectF;)Z
    //   86: ifeq -> 142
    //   89: aload_0
    //   90: getfield mFireCrossReset : Z
    //   93: ifeq -> 107
    //   96: aload_0
    //   97: iconst_0
    //   98: putfield mFireCrossReset : Z
    //   101: iconst_1
    //   102: istore #5
    //   104: goto -> 110
    //   107: iconst_0
    //   108: istore #5
    //   110: aload_0
    //   111: getfield mFirePositiveReset : Z
    //   114: ifeq -> 128
    //   117: aload_0
    //   118: iconst_0
    //   119: putfield mFirePositiveReset : Z
    //   122: iconst_1
    //   123: istore #7
    //   125: goto -> 131
    //   128: iconst_0
    //   129: istore #7
    //   131: aload_0
    //   132: iconst_1
    //   133: putfield mFireNegativeReset : Z
    //   136: iconst_0
    //   137: istore #6
    //   139: goto -> 406
    //   142: aload_0
    //   143: getfield mFireCrossReset : Z
    //   146: ifne -> 160
    //   149: aload_0
    //   150: iconst_1
    //   151: putfield mFireCrossReset : Z
    //   154: iconst_1
    //   155: istore #5
    //   157: goto -> 163
    //   160: iconst_0
    //   161: istore #5
    //   163: aload_0
    //   164: getfield mFireNegativeReset : Z
    //   167: ifeq -> 181
    //   170: aload_0
    //   171: iconst_0
    //   172: putfield mFireNegativeReset : Z
    //   175: iconst_1
    //   176: istore #6
    //   178: goto -> 184
    //   181: iconst_0
    //   182: istore #6
    //   184: aload_0
    //   185: iconst_1
    //   186: putfield mFirePositiveReset : Z
    //   189: goto -> 403
    //   192: aload_0
    //   193: getfield mFireCrossReset : Z
    //   196: ifeq -> 230
    //   199: aload_0
    //   200: getfield mFireThreshold : F
    //   203: fstore_3
    //   204: fload_1
    //   205: fload_3
    //   206: fsub
    //   207: aload_0
    //   208: getfield mFireLastPos : F
    //   211: fload_3
    //   212: fsub
    //   213: fmul
    //   214: fconst_0
    //   215: fcmpg
    //   216: ifge -> 252
    //   219: aload_0
    //   220: iconst_0
    //   221: putfield mFireCrossReset : Z
    //   224: iconst_1
    //   225: istore #5
    //   227: goto -> 255
    //   230: fload_1
    //   231: aload_0
    //   232: getfield mFireThreshold : F
    //   235: fsub
    //   236: invokestatic abs : (F)F
    //   239: aload_0
    //   240: getfield mTriggerSlack : F
    //   243: fcmpl
    //   244: ifle -> 252
    //   247: aload_0
    //   248: iconst_1
    //   249: putfield mFireCrossReset : Z
    //   252: iconst_0
    //   253: istore #5
    //   255: aload_0
    //   256: getfield mFireNegativeReset : Z
    //   259: ifeq -> 304
    //   262: aload_0
    //   263: getfield mFireThreshold : F
    //   266: fstore #4
    //   268: fload_1
    //   269: fload #4
    //   271: fsub
    //   272: fstore_3
    //   273: aload_0
    //   274: getfield mFireLastPos : F
    //   277: fload #4
    //   279: fsub
    //   280: fload_3
    //   281: fmul
    //   282: fconst_0
    //   283: fcmpg
    //   284: ifge -> 326
    //   287: fload_3
    //   288: fconst_0
    //   289: fcmpg
    //   290: ifge -> 326
    //   293: aload_0
    //   294: iconst_0
    //   295: putfield mFireNegativeReset : Z
    //   298: iconst_1
    //   299: istore #6
    //   301: goto -> 329
    //   304: fload_1
    //   305: aload_0
    //   306: getfield mFireThreshold : F
    //   309: fsub
    //   310: invokestatic abs : (F)F
    //   313: aload_0
    //   314: getfield mTriggerSlack : F
    //   317: fcmpl
    //   318: ifle -> 326
    //   321: aload_0
    //   322: iconst_1
    //   323: putfield mFireNegativeReset : Z
    //   326: iconst_0
    //   327: istore #6
    //   329: aload_0
    //   330: getfield mFirePositiveReset : Z
    //   333: ifeq -> 381
    //   336: aload_0
    //   337: getfield mFireThreshold : F
    //   340: fstore_3
    //   341: fload_1
    //   342: fload_3
    //   343: fsub
    //   344: fstore #4
    //   346: aload_0
    //   347: getfield mFireLastPos : F
    //   350: fload_3
    //   351: fsub
    //   352: fload #4
    //   354: fmul
    //   355: fconst_0
    //   356: fcmpg
    //   357: ifge -> 375
    //   360: fload #4
    //   362: fconst_0
    //   363: fcmpl
    //   364: ifle -> 375
    //   367: aload_0
    //   368: iconst_0
    //   369: putfield mFirePositiveReset : Z
    //   372: goto -> 378
    //   375: iconst_0
    //   376: istore #7
    //   378: goto -> 406
    //   381: fload_1
    //   382: aload_0
    //   383: getfield mFireThreshold : F
    //   386: fsub
    //   387: invokestatic abs : (F)F
    //   390: aload_0
    //   391: getfield mTriggerSlack : F
    //   394: fcmpl
    //   395: ifle -> 403
    //   398: aload_0
    //   399: iconst_1
    //   400: putfield mFirePositiveReset : Z
    //   403: iconst_0
    //   404: istore #7
    //   406: aload_0
    //   407: fload_1
    //   408: putfield mFireLastPos : F
    //   411: iload #6
    //   413: ifne -> 426
    //   416: iload #5
    //   418: ifne -> 426
    //   421: iload #7
    //   423: ifeq -> 443
    //   426: aload_2
    //   427: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   430: checkcast androidx/constraintlayout/motion/widget/MotionLayout
    //   433: aload_0
    //   434: getfield mTriggerID : I
    //   437: iload #7
    //   439: fload_1
    //   440: invokevirtual fireTrigger : (IZF)V
    //   443: aload_0
    //   444: getfield mTriggerReceiver : I
    //   447: getstatic androidx/constraintlayout/motion/widget/KeyTrigger.UNSET : I
    //   450: if_icmpne -> 456
    //   453: goto -> 471
    //   456: aload_2
    //   457: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   460: checkcast androidx/constraintlayout/motion/widget/MotionLayout
    //   463: aload_0
    //   464: getfield mTriggerReceiver : I
    //   467: invokevirtual findViewById : (I)Landroid/view/View;
    //   470: astore_2
    //   471: iload #6
    //   473: ifeq -> 686
    //   476: aload_0
    //   477: getfield mNegativeCross : Ljava/lang/String;
    //   480: ifnull -> 686
    //   483: aload_0
    //   484: getfield mFireNegativeCross : Ljava/lang/reflect/Method;
    //   487: ifnonnull -> 591
    //   490: aload_0
    //   491: aload_2
    //   492: invokevirtual getClass : ()Ljava/lang/Class;
    //   495: aload_0
    //   496: getfield mNegativeCross : Ljava/lang/String;
    //   499: iconst_0
    //   500: anewarray java/lang/Class
    //   503: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   506: putfield mFireNegativeCross : Ljava/lang/reflect/Method;
    //   509: goto -> 591
    //   512: astore #8
    //   514: new java/lang/StringBuilder
    //   517: dup
    //   518: invokespecial <init> : ()V
    //   521: astore #8
    //   523: aload #8
    //   525: ldc 'Could not find method "'
    //   527: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   530: pop
    //   531: aload #8
    //   533: aload_0
    //   534: getfield mNegativeCross : Ljava/lang/String;
    //   537: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   540: pop
    //   541: aload #8
    //   543: ldc '"on class '
    //   545: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   548: pop
    //   549: aload #8
    //   551: aload_2
    //   552: invokevirtual getClass : ()Ljava/lang/Class;
    //   555: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   558: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   561: pop
    //   562: aload #8
    //   564: ldc ' '
    //   566: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   569: pop
    //   570: aload #8
    //   572: aload_2
    //   573: invokestatic getName : (Landroid/view/View;)Ljava/lang/String;
    //   576: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   579: pop
    //   580: ldc 'KeyTrigger'
    //   582: aload #8
    //   584: invokevirtual toString : ()Ljava/lang/String;
    //   587: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   590: pop
    //   591: aload_0
    //   592: getfield mFireNegativeCross : Ljava/lang/reflect/Method;
    //   595: aload_2
    //   596: iconst_0
    //   597: anewarray java/lang/Object
    //   600: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   603: pop
    //   604: goto -> 686
    //   607: astore #8
    //   609: new java/lang/StringBuilder
    //   612: dup
    //   613: invokespecial <init> : ()V
    //   616: astore #8
    //   618: aload #8
    //   620: ldc 'Exception in call "'
    //   622: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   625: pop
    //   626: aload #8
    //   628: aload_0
    //   629: getfield mNegativeCross : Ljava/lang/String;
    //   632: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   635: pop
    //   636: aload #8
    //   638: ldc '"on class '
    //   640: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   643: pop
    //   644: aload #8
    //   646: aload_2
    //   647: invokevirtual getClass : ()Ljava/lang/Class;
    //   650: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   653: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   656: pop
    //   657: aload #8
    //   659: ldc ' '
    //   661: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   664: pop
    //   665: aload #8
    //   667: aload_2
    //   668: invokestatic getName : (Landroid/view/View;)Ljava/lang/String;
    //   671: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   674: pop
    //   675: ldc 'KeyTrigger'
    //   677: aload #8
    //   679: invokevirtual toString : ()Ljava/lang/String;
    //   682: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   685: pop
    //   686: iload #7
    //   688: ifeq -> 901
    //   691: aload_0
    //   692: getfield mPositiveCross : Ljava/lang/String;
    //   695: ifnull -> 901
    //   698: aload_0
    //   699: getfield mFirePositiveCross : Ljava/lang/reflect/Method;
    //   702: ifnonnull -> 806
    //   705: aload_0
    //   706: aload_2
    //   707: invokevirtual getClass : ()Ljava/lang/Class;
    //   710: aload_0
    //   711: getfield mPositiveCross : Ljava/lang/String;
    //   714: iconst_0
    //   715: anewarray java/lang/Class
    //   718: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   721: putfield mFirePositiveCross : Ljava/lang/reflect/Method;
    //   724: goto -> 806
    //   727: astore #8
    //   729: new java/lang/StringBuilder
    //   732: dup
    //   733: invokespecial <init> : ()V
    //   736: astore #8
    //   738: aload #8
    //   740: ldc 'Could not find method "'
    //   742: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   745: pop
    //   746: aload #8
    //   748: aload_0
    //   749: getfield mPositiveCross : Ljava/lang/String;
    //   752: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   755: pop
    //   756: aload #8
    //   758: ldc '"on class '
    //   760: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   763: pop
    //   764: aload #8
    //   766: aload_2
    //   767: invokevirtual getClass : ()Ljava/lang/Class;
    //   770: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   773: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   776: pop
    //   777: aload #8
    //   779: ldc ' '
    //   781: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   784: pop
    //   785: aload #8
    //   787: aload_2
    //   788: invokestatic getName : (Landroid/view/View;)Ljava/lang/String;
    //   791: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   794: pop
    //   795: ldc 'KeyTrigger'
    //   797: aload #8
    //   799: invokevirtual toString : ()Ljava/lang/String;
    //   802: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   805: pop
    //   806: aload_0
    //   807: getfield mFirePositiveCross : Ljava/lang/reflect/Method;
    //   810: aload_2
    //   811: iconst_0
    //   812: anewarray java/lang/Object
    //   815: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   818: pop
    //   819: goto -> 901
    //   822: astore #8
    //   824: new java/lang/StringBuilder
    //   827: dup
    //   828: invokespecial <init> : ()V
    //   831: astore #8
    //   833: aload #8
    //   835: ldc 'Exception in call "'
    //   837: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   840: pop
    //   841: aload #8
    //   843: aload_0
    //   844: getfield mPositiveCross : Ljava/lang/String;
    //   847: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   850: pop
    //   851: aload #8
    //   853: ldc '"on class '
    //   855: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   858: pop
    //   859: aload #8
    //   861: aload_2
    //   862: invokevirtual getClass : ()Ljava/lang/Class;
    //   865: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   868: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   871: pop
    //   872: aload #8
    //   874: ldc ' '
    //   876: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   879: pop
    //   880: aload #8
    //   882: aload_2
    //   883: invokestatic getName : (Landroid/view/View;)Ljava/lang/String;
    //   886: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   889: pop
    //   890: ldc 'KeyTrigger'
    //   892: aload #8
    //   894: invokevirtual toString : ()Ljava/lang/String;
    //   897: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   900: pop
    //   901: iload #5
    //   903: ifeq -> 1116
    //   906: aload_0
    //   907: getfield mCross : Ljava/lang/String;
    //   910: ifnull -> 1116
    //   913: aload_0
    //   914: getfield mFireCross : Ljava/lang/reflect/Method;
    //   917: ifnonnull -> 1021
    //   920: aload_0
    //   921: aload_2
    //   922: invokevirtual getClass : ()Ljava/lang/Class;
    //   925: aload_0
    //   926: getfield mCross : Ljava/lang/String;
    //   929: iconst_0
    //   930: anewarray java/lang/Class
    //   933: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   936: putfield mFireCross : Ljava/lang/reflect/Method;
    //   939: goto -> 1021
    //   942: astore #8
    //   944: new java/lang/StringBuilder
    //   947: dup
    //   948: invokespecial <init> : ()V
    //   951: astore #8
    //   953: aload #8
    //   955: ldc 'Could not find method "'
    //   957: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   960: pop
    //   961: aload #8
    //   963: aload_0
    //   964: getfield mCross : Ljava/lang/String;
    //   967: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   970: pop
    //   971: aload #8
    //   973: ldc '"on class '
    //   975: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   978: pop
    //   979: aload #8
    //   981: aload_2
    //   982: invokevirtual getClass : ()Ljava/lang/Class;
    //   985: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   988: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   991: pop
    //   992: aload #8
    //   994: ldc ' '
    //   996: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   999: pop
    //   1000: aload #8
    //   1002: aload_2
    //   1003: invokestatic getName : (Landroid/view/View;)Ljava/lang/String;
    //   1006: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1009: pop
    //   1010: ldc 'KeyTrigger'
    //   1012: aload #8
    //   1014: invokevirtual toString : ()Ljava/lang/String;
    //   1017: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   1020: pop
    //   1021: aload_0
    //   1022: getfield mFireCross : Ljava/lang/reflect/Method;
    //   1025: aload_2
    //   1026: iconst_0
    //   1027: anewarray java/lang/Object
    //   1030: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   1033: pop
    //   1034: goto -> 1116
    //   1037: astore #8
    //   1039: new java/lang/StringBuilder
    //   1042: dup
    //   1043: invokespecial <init> : ()V
    //   1046: astore #8
    //   1048: aload #8
    //   1050: ldc 'Exception in call "'
    //   1052: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1055: pop
    //   1056: aload #8
    //   1058: aload_0
    //   1059: getfield mCross : Ljava/lang/String;
    //   1062: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1065: pop
    //   1066: aload #8
    //   1068: ldc '"on class '
    //   1070: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1073: pop
    //   1074: aload #8
    //   1076: aload_2
    //   1077: invokevirtual getClass : ()Ljava/lang/Class;
    //   1080: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   1083: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1086: pop
    //   1087: aload #8
    //   1089: ldc ' '
    //   1091: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1094: pop
    //   1095: aload #8
    //   1097: aload_2
    //   1098: invokestatic getName : (Landroid/view/View;)Ljava/lang/String;
    //   1101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1104: pop
    //   1105: ldc 'KeyTrigger'
    //   1107: aload #8
    //   1109: invokevirtual toString : ()Ljava/lang/String;
    //   1112: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   1115: pop
    //   1116: return
    // Exception table:
    //   from	to	target	type
    //   490	509	512	java/lang/NoSuchMethodException
    //   591	604	607	java/lang/Exception
    //   705	724	727	java/lang/NoSuchMethodException
    //   806	819	822	java/lang/Exception
    //   920	939	942	java/lang/NoSuchMethodException
    //   1021	1034	1037	java/lang/Exception
  }
  
  public void getAttributeNames(HashSet<String> paramHashSet) {}
  
  int getCurveFit() {
    return this.mCurveFit;
  }
  
  public void load(Context paramContext, AttributeSet paramAttributeSet) {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyTrigger), paramContext);
  }
  
  public void setValue(String paramString, Object paramObject) {}
  
  private static class Loader {
    private static final int COLLISION = 9;
    
    private static final int CROSS = 4;
    
    private static final int FRAME_POS = 8;
    
    private static final int NEGATIVE_CROSS = 1;
    
    private static final int POSITIVE_CROSS = 2;
    
    private static final int POST_LAYOUT = 10;
    
    private static final int TARGET_ID = 7;
    
    private static final int TRIGGER_ID = 6;
    
    private static final int TRIGGER_RECEIVER = 11;
    
    private static final int TRIGGER_SLACK = 5;
    
    private static SparseIntArray mAttrMap;
    
    static {
      SparseIntArray sparseIntArray = new SparseIntArray();
      mAttrMap = sparseIntArray;
      sparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
      mAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
      mAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
      mAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
      mAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
      mAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
      mAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
      mAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
      mAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
      mAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
    }
    
    public static void read(KeyTrigger param1KeyTrigger, TypedArray param1TypedArray, Context param1Context) {
      int i = param1TypedArray.getIndexCount();
      for (byte b = 0;; b++) {
        if (b < i) {
          StringBuilder stringBuilder;
          int j = param1TypedArray.getIndex(b);
          switch (mAttrMap.get(j)) {
            default:
              stringBuilder = new StringBuilder();
              stringBuilder.append("unused attribute 0x");
              stringBuilder.append(Integer.toHexString(j));
              stringBuilder.append("   ");
              stringBuilder.append(mAttrMap.get(j));
              Log.e("KeyTrigger", stringBuilder.toString());
              b++;
              continue;
            case 11:
              KeyTrigger.access$702(param1KeyTrigger, param1TypedArray.getResourceId(j, param1KeyTrigger.mTriggerReceiver));
            case 10:
              KeyTrigger.access$602(param1KeyTrigger, param1TypedArray.getBoolean(j, param1KeyTrigger.mPostLayout));
              break;
            case 9:
              KeyTrigger.access$502(param1KeyTrigger, param1TypedArray.getResourceId(j, param1KeyTrigger.mTriggerCollisionId));
              break;
            case 8:
              param1KeyTrigger.mFramePosition = param1TypedArray.getInteger(j, param1KeyTrigger.mFramePosition);
              KeyTrigger.access$002(param1KeyTrigger, (param1KeyTrigger.mFramePosition + 0.5F) / 100.0F);
              break;
            case 7:
              if (MotionLayout.IS_IN_EDIT_MODE) {
                param1KeyTrigger.mTargetId = param1TypedArray.getResourceId(j, param1KeyTrigger.mTargetId);
                if (param1KeyTrigger.mTargetId == -1)
                  param1KeyTrigger.mTargetString = param1TypedArray.getString(j); 
                break;
              } 
              if ((param1TypedArray.peekValue(j)).type == 3) {
                param1KeyTrigger.mTargetString = param1TypedArray.getString(j);
                break;
              } 
              param1KeyTrigger.mTargetId = param1TypedArray.getResourceId(j, param1KeyTrigger.mTargetId);
              break;
            case 6:
              KeyTrigger.access$402(param1KeyTrigger, param1TypedArray.getResourceId(j, param1KeyTrigger.mTriggerID));
              break;
            case 5:
              param1KeyTrigger.mTriggerSlack = param1TypedArray.getFloat(j, param1KeyTrigger.mTriggerSlack);
              break;
            case 4:
              KeyTrigger.access$302(param1KeyTrigger, param1TypedArray.getString(j));
              break;
            case 2:
              KeyTrigger.access$202(param1KeyTrigger, param1TypedArray.getString(j));
              break;
            case 1:
              KeyTrigger.access$102(param1KeyTrigger, param1TypedArray.getString(j));
              break;
          } 
        } else {
          break;
        } 
      } 
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\KeyTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */