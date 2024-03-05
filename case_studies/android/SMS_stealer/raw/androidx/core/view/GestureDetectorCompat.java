package androidx.core.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
  private final GestureDetectorCompatImpl mImpl;
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener) {
    this(paramContext, paramOnGestureListener, null);
  }
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler) {
    if (Build.VERSION.SDK_INT > 17) {
      this.mImpl = new GestureDetectorCompatImplJellybeanMr2(paramContext, paramOnGestureListener, paramHandler);
    } else {
      this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, paramHandler);
    } 
  }
  
  public boolean isLongpressEnabled() {
    return this.mImpl.isLongpressEnabled();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }
  
  public void setIsLongpressEnabled(boolean paramBoolean) {
    this.mImpl.setIsLongpressEnabled(paramBoolean);
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener) {
    this.mImpl.setOnDoubleTapListener(paramOnDoubleTapListener);
  }
  
  static interface GestureDetectorCompatImpl {
    boolean isLongpressEnabled();
    
    boolean onTouchEvent(MotionEvent param1MotionEvent);
    
    void setIsLongpressEnabled(boolean param1Boolean);
    
    void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener);
  }
  
  static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    
    private static final int LONG_PRESS = 2;
    
    private static final int SHOW_PRESS = 1;
    
    private static final int TAP = 3;
    
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    
    private boolean mAlwaysInBiggerTapRegion;
    
    private boolean mAlwaysInTapRegion;
    
    MotionEvent mCurrentDownEvent;
    
    boolean mDeferConfirmSingleTap;
    
    GestureDetector.OnDoubleTapListener mDoubleTapListener;
    
    private int mDoubleTapSlopSquare;
    
    private float mDownFocusX;
    
    private float mDownFocusY;
    
    private final Handler mHandler;
    
    private boolean mInLongPress;
    
    private boolean mIsDoubleTapping;
    
    private boolean mIsLongpressEnabled;
    
    private float mLastFocusX;
    
    private float mLastFocusY;
    
    final GestureDetector.OnGestureListener mListener;
    
    private int mMaximumFlingVelocity;
    
    private int mMinimumFlingVelocity;
    
    private MotionEvent mPreviousUpEvent;
    
    boolean mStillDown;
    
    private int mTouchSlopSquare;
    
    private VelocityTracker mVelocityTracker;
    
    static {
    
    }
    
    GestureDetectorCompatImplBase(Context param1Context, GestureDetector.OnGestureListener param1OnGestureListener, Handler param1Handler) {
      if (param1Handler != null) {
        this.mHandler = new GestureHandler(param1Handler);
      } else {
        this.mHandler = new GestureHandler();
      } 
      this.mListener = param1OnGestureListener;
      if (param1OnGestureListener instanceof GestureDetector.OnDoubleTapListener)
        setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)param1OnGestureListener); 
      init(param1Context);
    }
    
    private void cancel() {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      this.mIsDoubleTapping = false;
      this.mStillDown = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress)
        this.mInLongPress = false; 
    }
    
    private void cancelTaps() {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mIsDoubleTapping = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress)
        this.mInLongPress = false; 
    }
    
    private void init(Context param1Context) {
      if (param1Context != null) {
        if (this.mListener != null) {
          this.mIsLongpressEnabled = true;
          ViewConfiguration viewConfiguration = ViewConfiguration.get(param1Context);
          int i = viewConfiguration.getScaledTouchSlop();
          int j = viewConfiguration.getScaledDoubleTapSlop();
          this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
          this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
          this.mTouchSlopSquare = i * i;
          this.mDoubleTapSlopSquare = j * j;
          return;
        } 
        throw new IllegalArgumentException("OnGestureListener must not be null");
      } 
      throw new IllegalArgumentException("Context must not be null");
    }
    
    private boolean isConsideredDoubleTap(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, MotionEvent param1MotionEvent3) {
      boolean bool1 = this.mAlwaysInBiggerTapRegion;
      boolean bool = false;
      if (!bool1)
        return false; 
      if (param1MotionEvent3.getEventTime() - param1MotionEvent2.getEventTime() > DOUBLE_TAP_TIMEOUT)
        return false; 
      int j = (int)param1MotionEvent1.getX() - (int)param1MotionEvent3.getX();
      int i = (int)param1MotionEvent1.getY() - (int)param1MotionEvent3.getY();
      if (j * j + i * i < this.mDoubleTapSlopSquare)
        bool = true; 
      return bool;
    }
    
    void dispatchLongPress() {
      this.mHandler.removeMessages(3);
      this.mDeferConfirmSingleTap = false;
      this.mInLongPress = true;
      this.mListener.onLongPress(this.mCurrentDownEvent);
    }
    
    public boolean isLongpressEnabled() {
      return this.mIsLongpressEnabled;
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual getAction : ()I
      //   4: istore #6
      //   6: aload_0
      //   7: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   10: ifnonnull -> 20
      //   13: aload_0
      //   14: invokestatic obtain : ()Landroid/view/VelocityTracker;
      //   17: putfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   20: aload_0
      //   21: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   24: aload_1
      //   25: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
      //   28: iload #6
      //   30: sipush #255
      //   33: iand
      //   34: istore #10
      //   36: iconst_0
      //   37: istore #12
      //   39: iload #10
      //   41: bipush #6
      //   43: if_icmpne -> 52
      //   46: iconst_1
      //   47: istore #6
      //   49: goto -> 55
      //   52: iconst_0
      //   53: istore #6
      //   55: iload #6
      //   57: ifeq -> 69
      //   60: aload_1
      //   61: invokevirtual getActionIndex : ()I
      //   64: istore #7
      //   66: goto -> 72
      //   69: iconst_m1
      //   70: istore #7
      //   72: aload_1
      //   73: invokevirtual getPointerCount : ()I
      //   76: istore #9
      //   78: iconst_0
      //   79: istore #8
      //   81: fconst_0
      //   82: fstore_3
      //   83: fconst_0
      //   84: fstore_2
      //   85: iload #8
      //   87: iload #9
      //   89: if_icmpge -> 126
      //   92: iload #7
      //   94: iload #8
      //   96: if_icmpne -> 102
      //   99: goto -> 120
      //   102: fload_3
      //   103: aload_1
      //   104: iload #8
      //   106: invokevirtual getX : (I)F
      //   109: fadd
      //   110: fstore_3
      //   111: fload_2
      //   112: aload_1
      //   113: iload #8
      //   115: invokevirtual getY : (I)F
      //   118: fadd
      //   119: fstore_2
      //   120: iinc #8, 1
      //   123: goto -> 85
      //   126: iload #6
      //   128: ifeq -> 140
      //   131: iload #9
      //   133: iconst_1
      //   134: isub
      //   135: istore #6
      //   137: goto -> 144
      //   140: iload #9
      //   142: istore #6
      //   144: iload #6
      //   146: i2f
      //   147: fstore #4
      //   149: fload_3
      //   150: fload #4
      //   152: fdiv
      //   153: fstore_3
      //   154: fload_2
      //   155: fload #4
      //   157: fdiv
      //   158: fstore #5
      //   160: iload #10
      //   162: ifeq -> 914
      //   165: iload #10
      //   167: iconst_1
      //   168: if_icmpeq -> 646
      //   171: iload #10
      //   173: iconst_2
      //   174: if_icmpeq -> 398
      //   177: iload #10
      //   179: iconst_3
      //   180: if_icmpeq -> 387
      //   183: iload #10
      //   185: iconst_5
      //   186: if_icmpeq -> 354
      //   189: iload #10
      //   191: bipush #6
      //   193: if_icmpeq -> 203
      //   196: iload #12
      //   198: istore #11
      //   200: goto -> 1183
      //   203: aload_0
      //   204: fload_3
      //   205: putfield mLastFocusX : F
      //   208: aload_0
      //   209: fload_3
      //   210: putfield mDownFocusX : F
      //   213: aload_0
      //   214: fload #5
      //   216: putfield mLastFocusY : F
      //   219: aload_0
      //   220: fload #5
      //   222: putfield mDownFocusY : F
      //   225: aload_0
      //   226: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   229: sipush #1000
      //   232: aload_0
      //   233: getfield mMaximumFlingVelocity : I
      //   236: i2f
      //   237: invokevirtual computeCurrentVelocity : (IF)V
      //   240: aload_1
      //   241: invokevirtual getActionIndex : ()I
      //   244: istore #7
      //   246: aload_1
      //   247: iload #7
      //   249: invokevirtual getPointerId : (I)I
      //   252: istore #6
      //   254: aload_0
      //   255: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   258: iload #6
      //   260: invokevirtual getXVelocity : (I)F
      //   263: fstore_2
      //   264: aload_0
      //   265: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   268: iload #6
      //   270: invokevirtual getYVelocity : (I)F
      //   273: fstore_3
      //   274: iconst_0
      //   275: istore #6
      //   277: iload #12
      //   279: istore #11
      //   281: iload #6
      //   283: iload #9
      //   285: if_icmpge -> 1183
      //   288: iload #6
      //   290: iload #7
      //   292: if_icmpne -> 298
      //   295: goto -> 348
      //   298: aload_1
      //   299: iload #6
      //   301: invokevirtual getPointerId : (I)I
      //   304: istore #8
      //   306: aload_0
      //   307: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   310: iload #8
      //   312: invokevirtual getXVelocity : (I)F
      //   315: fload_2
      //   316: fmul
      //   317: aload_0
      //   318: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   321: iload #8
      //   323: invokevirtual getYVelocity : (I)F
      //   326: fload_3
      //   327: fmul
      //   328: fadd
      //   329: fconst_0
      //   330: fcmpg
      //   331: ifge -> 348
      //   334: aload_0
      //   335: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   338: invokevirtual clear : ()V
      //   341: iload #12
      //   343: istore #11
      //   345: goto -> 1183
      //   348: iinc #6, 1
      //   351: goto -> 277
      //   354: aload_0
      //   355: fload_3
      //   356: putfield mLastFocusX : F
      //   359: aload_0
      //   360: fload_3
      //   361: putfield mDownFocusX : F
      //   364: aload_0
      //   365: fload #5
      //   367: putfield mLastFocusY : F
      //   370: aload_0
      //   371: fload #5
      //   373: putfield mDownFocusY : F
      //   376: aload_0
      //   377: invokespecial cancelTaps : ()V
      //   380: iload #12
      //   382: istore #11
      //   384: goto -> 1183
      //   387: aload_0
      //   388: invokespecial cancel : ()V
      //   391: iload #12
      //   393: istore #11
      //   395: goto -> 1183
      //   398: aload_0
      //   399: getfield mInLongPress : Z
      //   402: ifeq -> 412
      //   405: iload #12
      //   407: istore #11
      //   409: goto -> 1183
      //   412: aload_0
      //   413: getfield mLastFocusX : F
      //   416: fload_3
      //   417: fsub
      //   418: fstore #4
      //   420: aload_0
      //   421: getfield mLastFocusY : F
      //   424: fload #5
      //   426: fsub
      //   427: fstore_2
      //   428: aload_0
      //   429: getfield mIsDoubleTapping : Z
      //   432: ifeq -> 452
      //   435: iconst_0
      //   436: aload_0
      //   437: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   440: aload_1
      //   441: invokeinterface onDoubleTapEvent : (Landroid/view/MotionEvent;)Z
      //   446: ior
      //   447: istore #11
      //   449: goto -> 1183
      //   452: aload_0
      //   453: getfield mAlwaysInTapRegion : Z
      //   456: ifeq -> 590
      //   459: fload_3
      //   460: aload_0
      //   461: getfield mDownFocusX : F
      //   464: fsub
      //   465: f2i
      //   466: istore #7
      //   468: fload #5
      //   470: aload_0
      //   471: getfield mDownFocusY : F
      //   474: fsub
      //   475: f2i
      //   476: istore #6
      //   478: iload #7
      //   480: iload #7
      //   482: imul
      //   483: iload #6
      //   485: iload #6
      //   487: imul
      //   488: iadd
      //   489: istore #6
      //   491: iload #6
      //   493: aload_0
      //   494: getfield mTouchSlopSquare : I
      //   497: if_icmple -> 562
      //   500: aload_0
      //   501: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   504: aload_0
      //   505: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   508: aload_1
      //   509: fload #4
      //   511: fload_2
      //   512: invokeinterface onScroll : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;FF)Z
      //   517: istore #12
      //   519: aload_0
      //   520: fload_3
      //   521: putfield mLastFocusX : F
      //   524: aload_0
      //   525: fload #5
      //   527: putfield mLastFocusY : F
      //   530: aload_0
      //   531: iconst_0
      //   532: putfield mAlwaysInTapRegion : Z
      //   535: aload_0
      //   536: getfield mHandler : Landroid/os/Handler;
      //   539: iconst_3
      //   540: invokevirtual removeMessages : (I)V
      //   543: aload_0
      //   544: getfield mHandler : Landroid/os/Handler;
      //   547: iconst_1
      //   548: invokevirtual removeMessages : (I)V
      //   551: aload_0
      //   552: getfield mHandler : Landroid/os/Handler;
      //   555: iconst_2
      //   556: invokevirtual removeMessages : (I)V
      //   559: goto -> 565
      //   562: iconst_0
      //   563: istore #12
      //   565: iload #12
      //   567: istore #11
      //   569: iload #6
      //   571: aload_0
      //   572: getfield mTouchSlopSquare : I
      //   575: if_icmple -> 911
      //   578: aload_0
      //   579: iconst_0
      //   580: putfield mAlwaysInBiggerTapRegion : Z
      //   583: iload #12
      //   585: istore #11
      //   587: goto -> 911
      //   590: fload #4
      //   592: invokestatic abs : (F)F
      //   595: fconst_1
      //   596: fcmpl
      //   597: ifge -> 613
      //   600: iload #12
      //   602: istore #11
      //   604: fload_2
      //   605: invokestatic abs : (F)F
      //   608: fconst_1
      //   609: fcmpl
      //   610: iflt -> 1183
      //   613: aload_0
      //   614: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   617: aload_0
      //   618: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   621: aload_1
      //   622: fload #4
      //   624: fload_2
      //   625: invokeinterface onScroll : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;FF)Z
      //   630: istore #11
      //   632: aload_0
      //   633: fload_3
      //   634: putfield mLastFocusX : F
      //   637: aload_0
      //   638: fload #5
      //   640: putfield mLastFocusY : F
      //   643: goto -> 1183
      //   646: aload_0
      //   647: iconst_0
      //   648: putfield mStillDown : Z
      //   651: aload_1
      //   652: invokestatic obtain : (Landroid/view/MotionEvent;)Landroid/view/MotionEvent;
      //   655: astore #13
      //   657: aload_0
      //   658: getfield mIsDoubleTapping : Z
      //   661: ifeq -> 681
      //   664: aload_0
      //   665: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   668: aload_1
      //   669: invokeinterface onDoubleTapEvent : (Landroid/view/MotionEvent;)Z
      //   674: iconst_0
      //   675: ior
      //   676: istore #11
      //   678: goto -> 848
      //   681: aload_0
      //   682: getfield mInLongPress : Z
      //   685: ifeq -> 704
      //   688: aload_0
      //   689: getfield mHandler : Landroid/os/Handler;
      //   692: iconst_3
      //   693: invokevirtual removeMessages : (I)V
      //   696: aload_0
      //   697: iconst_0
      //   698: putfield mInLongPress : Z
      //   701: goto -> 824
      //   704: aload_0
      //   705: getfield mAlwaysInTapRegion : Z
      //   708: ifeq -> 753
      //   711: aload_0
      //   712: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   715: aload_1
      //   716: invokeinterface onSingleTapUp : (Landroid/view/MotionEvent;)Z
      //   721: istore #11
      //   723: aload_0
      //   724: getfield mDeferConfirmSingleTap : Z
      //   727: ifeq -> 750
      //   730: aload_0
      //   731: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   734: astore #14
      //   736: aload #14
      //   738: ifnull -> 750
      //   741: aload #14
      //   743: aload_1
      //   744: invokeinterface onSingleTapConfirmed : (Landroid/view/MotionEvent;)Z
      //   749: pop
      //   750: goto -> 848
      //   753: aload_0
      //   754: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   757: astore #14
      //   759: aload_1
      //   760: iconst_0
      //   761: invokevirtual getPointerId : (I)I
      //   764: istore #6
      //   766: aload #14
      //   768: sipush #1000
      //   771: aload_0
      //   772: getfield mMaximumFlingVelocity : I
      //   775: i2f
      //   776: invokevirtual computeCurrentVelocity : (IF)V
      //   779: aload #14
      //   781: iload #6
      //   783: invokevirtual getYVelocity : (I)F
      //   786: fstore_2
      //   787: aload #14
      //   789: iload #6
      //   791: invokevirtual getXVelocity : (I)F
      //   794: fstore_3
      //   795: fload_2
      //   796: invokestatic abs : (F)F
      //   799: aload_0
      //   800: getfield mMinimumFlingVelocity : I
      //   803: i2f
      //   804: fcmpl
      //   805: ifgt -> 830
      //   808: fload_3
      //   809: invokestatic abs : (F)F
      //   812: aload_0
      //   813: getfield mMinimumFlingVelocity : I
      //   816: i2f
      //   817: fcmpl
      //   818: ifle -> 824
      //   821: goto -> 830
      //   824: iconst_0
      //   825: istore #11
      //   827: goto -> 848
      //   830: aload_0
      //   831: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   834: aload_0
      //   835: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   838: aload_1
      //   839: fload_3
      //   840: fload_2
      //   841: invokeinterface onFling : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;FF)Z
      //   846: istore #11
      //   848: aload_0
      //   849: getfield mPreviousUpEvent : Landroid/view/MotionEvent;
      //   852: astore_1
      //   853: aload_1
      //   854: ifnull -> 861
      //   857: aload_1
      //   858: invokevirtual recycle : ()V
      //   861: aload_0
      //   862: aload #13
      //   864: putfield mPreviousUpEvent : Landroid/view/MotionEvent;
      //   867: aload_0
      //   868: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   871: astore_1
      //   872: aload_1
      //   873: ifnull -> 885
      //   876: aload_1
      //   877: invokevirtual recycle : ()V
      //   880: aload_0
      //   881: aconst_null
      //   882: putfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   885: aload_0
      //   886: iconst_0
      //   887: putfield mIsDoubleTapping : Z
      //   890: aload_0
      //   891: iconst_0
      //   892: putfield mDeferConfirmSingleTap : Z
      //   895: aload_0
      //   896: getfield mHandler : Landroid/os/Handler;
      //   899: iconst_1
      //   900: invokevirtual removeMessages : (I)V
      //   903: aload_0
      //   904: getfield mHandler : Landroid/os/Handler;
      //   907: iconst_2
      //   908: invokevirtual removeMessages : (I)V
      //   911: goto -> 1183
      //   914: aload_0
      //   915: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   918: ifnull -> 1032
      //   921: aload_0
      //   922: getfield mHandler : Landroid/os/Handler;
      //   925: iconst_3
      //   926: invokevirtual hasMessages : (I)Z
      //   929: istore #11
      //   931: iload #11
      //   933: ifeq -> 944
      //   936: aload_0
      //   937: getfield mHandler : Landroid/os/Handler;
      //   940: iconst_3
      //   941: invokevirtual removeMessages : (I)V
      //   944: aload_0
      //   945: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   948: astore #14
      //   950: aload #14
      //   952: ifnull -> 1019
      //   955: aload_0
      //   956: getfield mPreviousUpEvent : Landroid/view/MotionEvent;
      //   959: astore #13
      //   961: aload #13
      //   963: ifnull -> 1019
      //   966: iload #11
      //   968: ifeq -> 1019
      //   971: aload_0
      //   972: aload #14
      //   974: aload #13
      //   976: aload_1
      //   977: invokespecial isConsideredDoubleTap : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;Landroid/view/MotionEvent;)Z
      //   980: ifeq -> 1019
      //   983: aload_0
      //   984: iconst_1
      //   985: putfield mIsDoubleTapping : Z
      //   988: aload_0
      //   989: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   992: aload_0
      //   993: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   996: invokeinterface onDoubleTap : (Landroid/view/MotionEvent;)Z
      //   1001: iconst_0
      //   1002: ior
      //   1003: aload_0
      //   1004: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   1007: aload_1
      //   1008: invokeinterface onDoubleTapEvent : (Landroid/view/MotionEvent;)Z
      //   1013: ior
      //   1014: istore #6
      //   1016: goto -> 1035
      //   1019: aload_0
      //   1020: getfield mHandler : Landroid/os/Handler;
      //   1023: iconst_3
      //   1024: getstatic androidx/core/view/GestureDetectorCompat$GestureDetectorCompatImplBase.DOUBLE_TAP_TIMEOUT : I
      //   1027: i2l
      //   1028: invokevirtual sendEmptyMessageDelayed : (IJ)Z
      //   1031: pop
      //   1032: iconst_0
      //   1033: istore #6
      //   1035: aload_0
      //   1036: fload_3
      //   1037: putfield mLastFocusX : F
      //   1040: aload_0
      //   1041: fload_3
      //   1042: putfield mDownFocusX : F
      //   1045: aload_0
      //   1046: fload #5
      //   1048: putfield mLastFocusY : F
      //   1051: aload_0
      //   1052: fload #5
      //   1054: putfield mDownFocusY : F
      //   1057: aload_0
      //   1058: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1061: astore #13
      //   1063: aload #13
      //   1065: ifnull -> 1073
      //   1068: aload #13
      //   1070: invokevirtual recycle : ()V
      //   1073: aload_0
      //   1074: aload_1
      //   1075: invokestatic obtain : (Landroid/view/MotionEvent;)Landroid/view/MotionEvent;
      //   1078: putfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1081: aload_0
      //   1082: iconst_1
      //   1083: putfield mAlwaysInTapRegion : Z
      //   1086: aload_0
      //   1087: iconst_1
      //   1088: putfield mAlwaysInBiggerTapRegion : Z
      //   1091: aload_0
      //   1092: iconst_1
      //   1093: putfield mStillDown : Z
      //   1096: aload_0
      //   1097: iconst_0
      //   1098: putfield mInLongPress : Z
      //   1101: aload_0
      //   1102: iconst_0
      //   1103: putfield mDeferConfirmSingleTap : Z
      //   1106: aload_0
      //   1107: getfield mIsLongpressEnabled : Z
      //   1110: ifeq -> 1147
      //   1113: aload_0
      //   1114: getfield mHandler : Landroid/os/Handler;
      //   1117: iconst_2
      //   1118: invokevirtual removeMessages : (I)V
      //   1121: aload_0
      //   1122: getfield mHandler : Landroid/os/Handler;
      //   1125: iconst_2
      //   1126: aload_0
      //   1127: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1130: invokevirtual getDownTime : ()J
      //   1133: getstatic androidx/core/view/GestureDetectorCompat$GestureDetectorCompatImplBase.TAP_TIMEOUT : I
      //   1136: i2l
      //   1137: ladd
      //   1138: getstatic androidx/core/view/GestureDetectorCompat$GestureDetectorCompatImplBase.LONGPRESS_TIMEOUT : I
      //   1141: i2l
      //   1142: ladd
      //   1143: invokevirtual sendEmptyMessageAtTime : (IJ)Z
      //   1146: pop
      //   1147: aload_0
      //   1148: getfield mHandler : Landroid/os/Handler;
      //   1151: iconst_1
      //   1152: aload_0
      //   1153: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1156: invokevirtual getDownTime : ()J
      //   1159: getstatic androidx/core/view/GestureDetectorCompat$GestureDetectorCompatImplBase.TAP_TIMEOUT : I
      //   1162: i2l
      //   1163: ladd
      //   1164: invokevirtual sendEmptyMessageAtTime : (IJ)Z
      //   1167: pop
      //   1168: iload #6
      //   1170: aload_0
      //   1171: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   1174: aload_1
      //   1175: invokeinterface onDown : (Landroid/view/MotionEvent;)Z
      //   1180: ior
      //   1181: istore #11
      //   1183: iload #11
      //   1185: ireturn
    }
    
    public void setIsLongpressEnabled(boolean param1Boolean) {
      this.mIsLongpressEnabled = param1Boolean;
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener) {
      this.mDoubleTapListener = param1OnDoubleTapListener;
    }
    
    private class GestureHandler extends Handler {
      final GestureDetectorCompat.GestureDetectorCompatImplBase this$0;
      
      GestureHandler() {}
      
      GestureHandler(Handler param2Handler) {
        super(param2Handler.getLooper());
      }
      
      public void handleMessage(Message param2Message) {
        int i = param2Message.what;
        if (i != 1) {
          if (i != 2) {
            if (i == 3) {
              if (GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener != null)
                if (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown) {
                  GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                } else {
                  GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                }  
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Unknown message ");
              stringBuilder.append(param2Message);
              throw new RuntimeException(stringBuilder.toString());
            } 
          } else {
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.dispatchLongPress();
          } 
        } else {
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
        } 
      }
    }
  }
  
  private class GestureHandler extends Handler {
    final GestureDetectorCompat.GestureDetectorCompatImplBase this$0;
    
    GestureHandler() {}
    
    GestureHandler(Handler param1Handler) {
      super(param1Handler.getLooper());
    }
    
    public void handleMessage(Message param1Message) {
      int i = param1Message.what;
      if (i != 1) {
        if (i != 2) {
          if (i == 3) {
            if (this.this$0.mDoubleTapListener != null)
              if (!this.this$0.mStillDown) {
                this.this$0.mDoubleTapListener.onSingleTapConfirmed(this.this$0.mCurrentDownEvent);
              } else {
                this.this$0.mDeferConfirmSingleTap = true;
              }  
          } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown message ");
            stringBuilder.append(param1Message);
            throw new RuntimeException(stringBuilder.toString());
          } 
        } else {
          this.this$0.dispatchLongPress();
        } 
      } else {
        this.this$0.mListener.onShowPress(this.this$0.mCurrentDownEvent);
      } 
    }
  }
  
  static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
    private final GestureDetector mDetector;
    
    GestureDetectorCompatImplJellybeanMr2(Context param1Context, GestureDetector.OnGestureListener param1OnGestureListener, Handler param1Handler) {
      this.mDetector = new GestureDetector(param1Context, param1OnGestureListener, param1Handler);
    }
    
    public boolean isLongpressEnabled() {
      return this.mDetector.isLongpressEnabled();
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      return this.mDetector.onTouchEvent(param1MotionEvent);
    }
    
    public void setIsLongpressEnabled(boolean param1Boolean) {
      this.mDetector.setIsLongpressEnabled(param1Boolean);
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener) {
      this.mDetector.setOnDoubleTapListener(param1OnDoubleTapListener);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\GestureDetectorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */