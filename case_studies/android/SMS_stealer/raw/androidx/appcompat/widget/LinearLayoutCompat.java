package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
  private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.widget.LinearLayoutCompat";
  
  public static final int HORIZONTAL = 0;
  
  private static final int INDEX_BOTTOM = 2;
  
  private static final int INDEX_CENTER_VERTICAL = 0;
  
  private static final int INDEX_FILL = 3;
  
  private static final int INDEX_TOP = 1;
  
  public static final int SHOW_DIVIDER_BEGINNING = 1;
  
  public static final int SHOW_DIVIDER_END = 4;
  
  public static final int SHOW_DIVIDER_MIDDLE = 2;
  
  public static final int SHOW_DIVIDER_NONE = 0;
  
  public static final int VERTICAL = 1;
  
  private static final int VERTICAL_GRAVITY_COUNT = 4;
  
  private boolean mBaselineAligned = true;
  
  private int mBaselineAlignedChildIndex = -1;
  
  private int mBaselineChildTop = 0;
  
  private Drawable mDivider;
  
  private int mDividerHeight;
  
  private int mDividerPadding;
  
  private int mDividerWidth;
  
  private int mGravity = 8388659;
  
  private int[] mMaxAscent;
  
  private int[] mMaxDescent;
  
  private int mOrientation;
  
  private int mShowDividers;
  
  private int mTotalLength;
  
  private boolean mUseLargestChild;
  
  private float mWeightSum;
  
  public LinearLayoutCompat(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.LinearLayoutCompat, paramInt, 0);
    ViewCompat.saveAttributeDataForStyleable((View)this, paramContext, R.styleable.LinearLayoutCompat, paramAttributeSet, tintTypedArray.getWrappedTypeArray(), paramInt, 0);
    paramInt = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
    if (paramInt >= 0)
      setOrientation(paramInt); 
    paramInt = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
    if (paramInt >= 0)
      setGravity(paramInt); 
    boolean bool = tintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
    if (!bool)
      setBaselineAligned(bool); 
    this.mWeightSum = tintTypedArray.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0F);
    this.mBaselineAlignedChildIndex = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
    this.mUseLargestChild = tintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
    setDividerDrawable(tintTypedArray.getDrawable(R.styleable.LinearLayoutCompat_divider));
    this.mShowDividers = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
    this.mDividerPadding = tintTypedArray.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
    tintTypedArray.recycle();
  }
  
  private void forceUniformHeight(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
    for (byte b = 0; b < paramInt1; b++) {
      View view = getVirtualChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.height == -1) {
          int j = layoutParams.width;
          layoutParams.width = view.getMeasuredWidth();
          measureChildWithMargins(view, paramInt2, 0, i, 0);
          layoutParams.width = j;
        } 
      } 
    } 
  }
  
  private void forceUniformWidth(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    for (byte b = 0; b < paramInt1; b++) {
      View view = getVirtualChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.width == -1) {
          int j = layoutParams.height;
          layoutParams.height = view.getMeasuredHeight();
          measureChildWithMargins(view, i, 0, paramInt2, 0);
          layoutParams.height = j;
        } 
      } 
    } 
  }
  
  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramView.layout(paramInt1, paramInt2, paramInt3 + paramInt1, paramInt4 + paramInt2);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void drawDividersHorizontal(Canvas paramCanvas) {
    int j = getVirtualChildCount();
    boolean bool = ViewUtils.isLayoutRtl((View)this);
    int i;
    for (i = 0; i < j; i++) {
      View view = getVirtualChildAt(i);
      if (view != null && view.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
        int k;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (bool) {
          k = view.getRight() + layoutParams.rightMargin;
        } else {
          k = view.getLeft() - layoutParams.leftMargin - this.mDividerWidth;
        } 
        drawVerticalDivider(paramCanvas, k);
      } 
    } 
    if (hasDividerBeforeChildAt(j)) {
      View view = getVirtualChildAt(j - 1);
      if (view == null) {
        if (bool) {
          i = getPaddingLeft();
        } else {
          int k = getWidth() - getPaddingRight();
          i = this.mDividerWidth;
          i = k - i;
        } 
      } else {
        int k;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (bool) {
          k = view.getLeft() - layoutParams.leftMargin;
          i = this.mDividerWidth;
        } else {
          i = view.getRight() + layoutParams.rightMargin;
          drawVerticalDivider(paramCanvas, i);
        } 
        i = k - i;
      } 
    } else {
      return;
    } 
    drawVerticalDivider(paramCanvas, i);
  }
  
  void drawDividersVertical(Canvas paramCanvas) {
    int j = getVirtualChildCount();
    int i;
    for (i = 0; i < j; i++) {
      View view = getVirtualChildAt(i);
      if (view != null && view.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        drawHorizontalDivider(paramCanvas, view.getTop() - layoutParams.topMargin - this.mDividerHeight);
      } 
    } 
    if (hasDividerBeforeChildAt(j)) {
      View view = getVirtualChildAt(j - 1);
      if (view == null) {
        i = getHeight() - getPaddingBottom() - this.mDividerHeight;
      } else {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        i = view.getBottom() + layoutParams.bottomMargin;
      } 
      drawHorizontalDivider(paramCanvas, i);
    } 
  }
  
  void drawHorizontalDivider(Canvas paramCanvas, int paramInt) {
    this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, paramInt, getWidth() - getPaddingRight() - this.mDividerPadding, this.mDividerHeight + paramInt);
    this.mDivider.draw(paramCanvas);
  }
  
  void drawVerticalDivider(Canvas paramCanvas, int paramInt) {
    this.mDivider.setBounds(paramInt, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + paramInt, getHeight() - getPaddingBottom() - this.mDividerPadding);
    this.mDivider.draw(paramCanvas);
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    int i = this.mOrientation;
    return (i == 0) ? new LayoutParams(-2, -2) : ((i == 1) ? new LayoutParams(-1, -2) : null);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getBaseline() {
    if (this.mBaselineAlignedChildIndex < 0)
      return super.getBaseline(); 
    int j = getChildCount();
    int i = this.mBaselineAlignedChildIndex;
    if (j > i) {
      View view = getChildAt(i);
      int k = view.getBaseline();
      if (k == -1) {
        if (this.mBaselineAlignedChildIndex == 0)
          return -1; 
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
      } 
      j = this.mBaselineChildTop;
      i = j;
      if (this.mOrientation == 1) {
        int m = this.mGravity & 0x70;
        i = j;
        if (m != 48)
          if (m != 16) {
            if (m != 80) {
              i = j;
            } else {
              i = getBottom() - getTop() - getPaddingBottom() - this.mTotalLength;
            } 
          } else {
            i = j + (getBottom() - getTop() - getPaddingTop() - getPaddingBottom() - this.mTotalLength) / 2;
          }  
      } 
      return i + ((LayoutParams)view.getLayoutParams()).topMargin + k;
    } 
    throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
  }
  
  public int getBaselineAlignedChildIndex() {
    return this.mBaselineAlignedChildIndex;
  }
  
  int getChildrenSkipCount(View paramView, int paramInt) {
    return 0;
  }
  
  public Drawable getDividerDrawable() {
    return this.mDivider;
  }
  
  public int getDividerPadding() {
    return this.mDividerPadding;
  }
  
  public int getDividerWidth() {
    return this.mDividerWidth;
  }
  
  public int getGravity() {
    return this.mGravity;
  }
  
  int getLocationOffset(View paramView) {
    return 0;
  }
  
  int getNextLocationOffset(View paramView) {
    return 0;
  }
  
  public int getOrientation() {
    return this.mOrientation;
  }
  
  public int getShowDividers() {
    return this.mShowDividers;
  }
  
  View getVirtualChildAt(int paramInt) {
    return getChildAt(paramInt);
  }
  
  int getVirtualChildCount() {
    return getChildCount();
  }
  
  public float getWeightSum() {
    return this.mWeightSum;
  }
  
  protected boolean hasDividerBeforeChildAt(int paramInt) {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    if (paramInt == 0) {
      bool1 = bool3;
      if ((this.mShowDividers & 0x1) != 0)
        bool1 = true; 
      return bool1;
    } 
    if (paramInt == getChildCount()) {
      if ((this.mShowDividers & 0x4) != 0)
        bool1 = true; 
      return bool1;
    } 
    bool1 = bool2;
    if ((this.mShowDividers & 0x2) != 0) {
      paramInt--;
      while (true) {
        bool1 = bool2;
        if (paramInt >= 0) {
          if (getChildAt(paramInt).getVisibility() != 8) {
            bool1 = true;
            break;
          } 
          paramInt--;
          continue;
        } 
        break;
      } 
    } 
    return bool1;
  }
  
  public boolean isBaselineAligned() {
    return this.mBaselineAligned;
  }
  
  public boolean isMeasureWithLargestChildEnabled() {
    return this.mUseLargestChild;
  }
  
  void layoutHorizontal(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    byte b1;
    byte b2;
    boolean bool2 = ViewUtils.isLayoutRtl((View)this);
    int k = getPaddingTop();
    int n = paramInt4 - paramInt2;
    int m = getPaddingBottom();
    int i1 = getPaddingBottom();
    int j = getVirtualChildCount();
    paramInt2 = this.mGravity;
    paramInt4 = paramInt2 & 0x70;
    boolean bool1 = this.mBaselineAligned;
    int[] arrayOfInt1 = this.mMaxAscent;
    int[] arrayOfInt2 = this.mMaxDescent;
    paramInt2 = GravityCompat.getAbsoluteGravity(0x800007 & paramInt2, ViewCompat.getLayoutDirection((View)this));
    if (paramInt2 != 1) {
      if (paramInt2 != 5) {
        paramInt2 = getPaddingLeft();
      } else {
        paramInt2 = getPaddingLeft() + paramInt3 - paramInt1 - this.mTotalLength;
      } 
    } else {
      paramInt2 = getPaddingLeft() + (paramInt3 - paramInt1 - this.mTotalLength) / 2;
    } 
    if (bool2) {
      b2 = j - 1;
      b1 = -1;
    } else {
      b2 = 0;
      b1 = 1;
    } 
    int i = 0;
    paramInt3 = paramInt4;
    paramInt4 = k;
    while (i < j) {
      int i2 = b2 + b1 * i;
      View view = getVirtualChildAt(i2);
      if (view == null) {
        paramInt2 += measureNullChild(i2);
      } else if (view.getVisibility() != 8) {
        int i5 = view.getMeasuredWidth();
        int i6 = view.getMeasuredHeight();
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (bool1 && layoutParams.height != -1) {
          i3 = view.getBaseline();
        } else {
          i3 = -1;
        } 
        int i4 = layoutParams.gravity;
        paramInt1 = i4;
        if (i4 < 0)
          paramInt1 = paramInt3; 
        paramInt1 &= 0x70;
        if (paramInt1 != 16) {
          if (paramInt1 != 48) {
            if (paramInt1 != 80) {
              paramInt1 = paramInt4;
            } else {
              i4 = n - m - i6 - layoutParams.bottomMargin;
              paramInt1 = i4;
              if (i3 != -1) {
                paramInt1 = view.getMeasuredHeight();
                paramInt1 = i4 - arrayOfInt2[2] - paramInt1 - i3;
              } 
            } 
          } else {
            i4 = layoutParams.topMargin + paramInt4;
            paramInt1 = i4;
            if (i3 != -1)
              paramInt1 = i4 + arrayOfInt1[1] - i3; 
          } 
        } else {
          paramInt1 = (n - k - i1 - i6) / 2 + paramInt4 + layoutParams.topMargin - layoutParams.bottomMargin;
        } 
        int i3 = paramInt2;
        if (hasDividerBeforeChildAt(i2))
          i3 = paramInt2 + this.mDividerWidth; 
        paramInt2 = layoutParams.leftMargin + i3;
        setChildFrame(view, paramInt2 + getLocationOffset(view), paramInt1, i5, i6);
        i3 = layoutParams.rightMargin;
        paramInt1 = getNextLocationOffset(view);
        i += getChildrenSkipCount(view, i2);
        paramInt2 += i5 + i3 + paramInt1;
      } 
      i++;
    } 
  }
  
  void layoutVertical(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = getPaddingLeft();
    int j = paramInt3 - paramInt1;
    int n = getPaddingRight();
    int k = getPaddingRight();
    int m = getVirtualChildCount();
    int i1 = this.mGravity;
    paramInt1 = i1 & 0x70;
    if (paramInt1 != 16) {
      if (paramInt1 != 80) {
        paramInt1 = getPaddingTop();
      } else {
        paramInt1 = getPaddingTop() + paramInt4 - paramInt2 - this.mTotalLength;
      } 
    } else {
      paramInt1 = getPaddingTop() + (paramInt4 - paramInt2 - this.mTotalLength) / 2;
    } 
    paramInt2 = 0;
    while (paramInt2 < m) {
      View view = getVirtualChildAt(paramInt2);
      if (view == null) {
        paramInt3 = paramInt1 + measureNullChild(paramInt2);
        paramInt4 = paramInt2;
      } else {
        paramInt3 = paramInt1;
        paramInt4 = paramInt2;
        if (view.getVisibility() != 8) {
          int i3 = view.getMeasuredWidth();
          int i2 = view.getMeasuredHeight();
          LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
          paramInt4 = layoutParams.gravity;
          paramInt3 = paramInt4;
          if (paramInt4 < 0)
            paramInt3 = i1 & 0x800007; 
          paramInt3 = GravityCompat.getAbsoluteGravity(paramInt3, ViewCompat.getLayoutDirection((View)this)) & 0x7;
          if (paramInt3 != 1) {
            if (paramInt3 != 5) {
              paramInt3 = layoutParams.leftMargin + i;
            } else {
              paramInt3 = j - n - i3;
              paramInt4 = layoutParams.rightMargin;
              paramInt3 -= paramInt4;
            } 
          } else {
            paramInt3 = (j - i - k - i3) / 2 + i + layoutParams.leftMargin;
            paramInt4 = layoutParams.rightMargin;
            paramInt3 -= paramInt4;
          } 
          paramInt4 = paramInt1;
          if (hasDividerBeforeChildAt(paramInt2))
            paramInt4 = paramInt1 + this.mDividerHeight; 
          paramInt1 = paramInt4 + layoutParams.topMargin;
          setChildFrame(view, paramInt3, paramInt1 + getLocationOffset(view), i3, i2);
          i3 = layoutParams.bottomMargin;
          paramInt3 = getNextLocationOffset(view);
          paramInt4 = paramInt2 + getChildrenSkipCount(view, paramInt2);
          paramInt3 = paramInt1 + i2 + i3 + paramInt3;
        } 
      } 
      paramInt2 = paramInt4 + 1;
      paramInt1 = paramInt3;
    } 
  }
  
  void measureChildBeforeLayout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    measureChildWithMargins(paramView, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  void measureHorizontal(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: putfield mTotalLength : I
    //   5: aload_0
    //   6: invokevirtual getVirtualChildCount : ()I
    //   9: istore #16
    //   11: iload_1
    //   12: invokestatic getMode : (I)I
    //   15: istore #21
    //   17: iload_2
    //   18: invokestatic getMode : (I)I
    //   21: istore #20
    //   23: aload_0
    //   24: getfield mMaxAscent : [I
    //   27: ifnull -> 37
    //   30: aload_0
    //   31: getfield mMaxDescent : [I
    //   34: ifnonnull -> 51
    //   37: aload_0
    //   38: iconst_4
    //   39: newarray int
    //   41: putfield mMaxAscent : [I
    //   44: aload_0
    //   45: iconst_4
    //   46: newarray int
    //   48: putfield mMaxDescent : [I
    //   51: aload_0
    //   52: getfield mMaxAscent : [I
    //   55: astore #26
    //   57: aload_0
    //   58: getfield mMaxDescent : [I
    //   61: astore #25
    //   63: aload #26
    //   65: iconst_3
    //   66: iconst_m1
    //   67: iastore
    //   68: aload #26
    //   70: iconst_2
    //   71: iconst_m1
    //   72: iastore
    //   73: aload #26
    //   75: iconst_1
    //   76: iconst_m1
    //   77: iastore
    //   78: aload #26
    //   80: iconst_0
    //   81: iconst_m1
    //   82: iastore
    //   83: aload #25
    //   85: iconst_3
    //   86: iconst_m1
    //   87: iastore
    //   88: aload #25
    //   90: iconst_2
    //   91: iconst_m1
    //   92: iastore
    //   93: aload #25
    //   95: iconst_1
    //   96: iconst_m1
    //   97: iastore
    //   98: aload #25
    //   100: iconst_0
    //   101: iconst_m1
    //   102: iastore
    //   103: aload_0
    //   104: getfield mBaselineAligned : Z
    //   107: istore #23
    //   109: aload_0
    //   110: getfield mUseLargestChild : Z
    //   113: istore #24
    //   115: iload #21
    //   117: ldc 1073741824
    //   119: if_icmpne -> 128
    //   122: iconst_1
    //   123: istore #15
    //   125: goto -> 131
    //   128: iconst_0
    //   129: istore #15
    //   131: fconst_0
    //   132: fstore_3
    //   133: iconst_0
    //   134: istore #8
    //   136: iconst_0
    //   137: istore #7
    //   139: iconst_0
    //   140: istore #13
    //   142: iconst_0
    //   143: istore #6
    //   145: iconst_0
    //   146: istore #11
    //   148: iconst_0
    //   149: istore #12
    //   151: iconst_0
    //   152: istore #9
    //   154: iconst_1
    //   155: istore #5
    //   157: iconst_0
    //   158: istore #10
    //   160: iload #8
    //   162: iload #16
    //   164: if_icmpge -> 846
    //   167: aload_0
    //   168: iload #8
    //   170: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   173: astore #27
    //   175: aload #27
    //   177: ifnonnull -> 202
    //   180: aload_0
    //   181: aload_0
    //   182: getfield mTotalLength : I
    //   185: aload_0
    //   186: iload #8
    //   188: invokevirtual measureNullChild : (I)I
    //   191: iadd
    //   192: putfield mTotalLength : I
    //   195: iload #9
    //   197: istore #14
    //   199: goto -> 836
    //   202: aload #27
    //   204: invokevirtual getVisibility : ()I
    //   207: bipush #8
    //   209: if_icmpne -> 228
    //   212: iload #8
    //   214: aload_0
    //   215: aload #27
    //   217: iload #8
    //   219: invokevirtual getChildrenSkipCount : (Landroid/view/View;I)I
    //   222: iadd
    //   223: istore #8
    //   225: goto -> 195
    //   228: aload_0
    //   229: iload #8
    //   231: invokevirtual hasDividerBeforeChildAt : (I)Z
    //   234: ifeq -> 250
    //   237: aload_0
    //   238: aload_0
    //   239: getfield mTotalLength : I
    //   242: aload_0
    //   243: getfield mDividerWidth : I
    //   246: iadd
    //   247: putfield mTotalLength : I
    //   250: aload #27
    //   252: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   255: checkcast androidx/appcompat/widget/LinearLayoutCompat$LayoutParams
    //   258: astore #28
    //   260: fload_3
    //   261: aload #28
    //   263: getfield weight : F
    //   266: fadd
    //   267: fstore_3
    //   268: iload #21
    //   270: ldc 1073741824
    //   272: if_icmpne -> 384
    //   275: aload #28
    //   277: getfield width : I
    //   280: ifne -> 384
    //   283: aload #28
    //   285: getfield weight : F
    //   288: fconst_0
    //   289: fcmpl
    //   290: ifle -> 384
    //   293: iload #15
    //   295: ifeq -> 321
    //   298: aload_0
    //   299: aload_0
    //   300: getfield mTotalLength : I
    //   303: aload #28
    //   305: getfield leftMargin : I
    //   308: aload #28
    //   310: getfield rightMargin : I
    //   313: iadd
    //   314: iadd
    //   315: putfield mTotalLength : I
    //   318: goto -> 350
    //   321: aload_0
    //   322: getfield mTotalLength : I
    //   325: istore #14
    //   327: aload_0
    //   328: iload #14
    //   330: aload #28
    //   332: getfield leftMargin : I
    //   335: iload #14
    //   337: iadd
    //   338: aload #28
    //   340: getfield rightMargin : I
    //   343: iadd
    //   344: invokestatic max : (II)I
    //   347: putfield mTotalLength : I
    //   350: iload #23
    //   352: ifeq -> 378
    //   355: iconst_0
    //   356: iconst_0
    //   357: invokestatic makeMeasureSpec : (II)I
    //   360: istore #14
    //   362: aload #27
    //   364: iload #14
    //   366: iload #14
    //   368: invokevirtual measure : (II)V
    //   371: iload #7
    //   373: istore #14
    //   375: goto -> 568
    //   378: iconst_1
    //   379: istore #12
    //   381: goto -> 572
    //   384: aload #28
    //   386: getfield width : I
    //   389: ifne -> 415
    //   392: aload #28
    //   394: getfield weight : F
    //   397: fconst_0
    //   398: fcmpl
    //   399: ifle -> 415
    //   402: aload #28
    //   404: bipush #-2
    //   406: putfield width : I
    //   409: iconst_0
    //   410: istore #14
    //   412: goto -> 420
    //   415: ldc_w -2147483648
    //   418: istore #14
    //   420: fload_3
    //   421: fconst_0
    //   422: fcmpl
    //   423: ifne -> 435
    //   426: aload_0
    //   427: getfield mTotalLength : I
    //   430: istore #17
    //   432: goto -> 438
    //   435: iconst_0
    //   436: istore #17
    //   438: aload_0
    //   439: aload #27
    //   441: iload #8
    //   443: iload_1
    //   444: iload #17
    //   446: iload_2
    //   447: iconst_0
    //   448: invokevirtual measureChildBeforeLayout : (Landroid/view/View;IIIII)V
    //   451: iload #14
    //   453: ldc_w -2147483648
    //   456: if_icmpeq -> 466
    //   459: aload #28
    //   461: iload #14
    //   463: putfield width : I
    //   466: aload #27
    //   468: invokevirtual getMeasuredWidth : ()I
    //   471: istore #17
    //   473: iload #15
    //   475: ifeq -> 511
    //   478: aload_0
    //   479: aload_0
    //   480: getfield mTotalLength : I
    //   483: aload #28
    //   485: getfield leftMargin : I
    //   488: iload #17
    //   490: iadd
    //   491: aload #28
    //   493: getfield rightMargin : I
    //   496: iadd
    //   497: aload_0
    //   498: aload #27
    //   500: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   503: iadd
    //   504: iadd
    //   505: putfield mTotalLength : I
    //   508: goto -> 550
    //   511: aload_0
    //   512: getfield mTotalLength : I
    //   515: istore #14
    //   517: aload_0
    //   518: iload #14
    //   520: iload #14
    //   522: iload #17
    //   524: iadd
    //   525: aload #28
    //   527: getfield leftMargin : I
    //   530: iadd
    //   531: aload #28
    //   533: getfield rightMargin : I
    //   536: iadd
    //   537: aload_0
    //   538: aload #27
    //   540: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   543: iadd
    //   544: invokestatic max : (II)I
    //   547: putfield mTotalLength : I
    //   550: iload #7
    //   552: istore #14
    //   554: iload #24
    //   556: ifeq -> 568
    //   559: iload #17
    //   561: iload #7
    //   563: invokestatic max : (II)I
    //   566: istore #14
    //   568: iload #14
    //   570: istore #7
    //   572: iload #20
    //   574: ldc 1073741824
    //   576: if_icmpeq -> 597
    //   579: aload #28
    //   581: getfield height : I
    //   584: iconst_m1
    //   585: if_icmpne -> 597
    //   588: iconst_1
    //   589: istore #14
    //   591: iconst_1
    //   592: istore #10
    //   594: goto -> 600
    //   597: iconst_0
    //   598: istore #14
    //   600: aload #28
    //   602: getfield topMargin : I
    //   605: aload #28
    //   607: getfield bottomMargin : I
    //   610: iadd
    //   611: istore #18
    //   613: aload #27
    //   615: invokevirtual getMeasuredHeight : ()I
    //   618: iload #18
    //   620: iadd
    //   621: istore #17
    //   623: iload #9
    //   625: aload #27
    //   627: invokevirtual getMeasuredState : ()I
    //   630: invokestatic combineMeasuredStates : (II)I
    //   633: istore #19
    //   635: iload #23
    //   637: ifeq -> 724
    //   640: aload #27
    //   642: invokevirtual getBaseline : ()I
    //   645: istore #22
    //   647: iload #22
    //   649: iconst_m1
    //   650: if_icmpeq -> 724
    //   653: aload #28
    //   655: getfield gravity : I
    //   658: ifge -> 670
    //   661: aload_0
    //   662: getfield mGravity : I
    //   665: istore #9
    //   667: goto -> 677
    //   670: aload #28
    //   672: getfield gravity : I
    //   675: istore #9
    //   677: iload #9
    //   679: bipush #112
    //   681: iand
    //   682: iconst_4
    //   683: ishr
    //   684: bipush #-2
    //   686: iand
    //   687: iconst_1
    //   688: ishr
    //   689: istore #9
    //   691: aload #26
    //   693: iload #9
    //   695: aload #26
    //   697: iload #9
    //   699: iaload
    //   700: iload #22
    //   702: invokestatic max : (II)I
    //   705: iastore
    //   706: aload #25
    //   708: iload #9
    //   710: aload #25
    //   712: iload #9
    //   714: iaload
    //   715: iload #17
    //   717: iload #22
    //   719: isub
    //   720: invokestatic max : (II)I
    //   723: iastore
    //   724: iload #13
    //   726: iload #17
    //   728: invokestatic max : (II)I
    //   731: istore #13
    //   733: iload #5
    //   735: ifeq -> 753
    //   738: aload #28
    //   740: getfield height : I
    //   743: iconst_m1
    //   744: if_icmpne -> 753
    //   747: iconst_1
    //   748: istore #5
    //   750: goto -> 756
    //   753: iconst_0
    //   754: istore #5
    //   756: aload #28
    //   758: getfield weight : F
    //   761: fconst_0
    //   762: fcmpl
    //   763: ifle -> 790
    //   766: iload #14
    //   768: ifeq -> 778
    //   771: iload #18
    //   773: istore #17
    //   775: goto -> 778
    //   778: iload #11
    //   780: iload #17
    //   782: invokestatic max : (II)I
    //   785: istore #9
    //   787: goto -> 815
    //   790: iload #14
    //   792: ifeq -> 798
    //   795: goto -> 802
    //   798: iload #17
    //   800: istore #18
    //   802: iload #6
    //   804: iload #18
    //   806: invokestatic max : (II)I
    //   809: istore #6
    //   811: iload #11
    //   813: istore #9
    //   815: aload_0
    //   816: aload #27
    //   818: iload #8
    //   820: invokevirtual getChildrenSkipCount : (Landroid/view/View;I)I
    //   823: iload #8
    //   825: iadd
    //   826: istore #8
    //   828: iload #19
    //   830: istore #14
    //   832: iload #9
    //   834: istore #11
    //   836: iinc #8, 1
    //   839: iload #14
    //   841: istore #9
    //   843: goto -> 160
    //   846: aload_0
    //   847: getfield mTotalLength : I
    //   850: ifle -> 875
    //   853: aload_0
    //   854: iload #16
    //   856: invokevirtual hasDividerBeforeChildAt : (I)Z
    //   859: ifeq -> 875
    //   862: aload_0
    //   863: aload_0
    //   864: getfield mTotalLength : I
    //   867: aload_0
    //   868: getfield mDividerWidth : I
    //   871: iadd
    //   872: putfield mTotalLength : I
    //   875: aload #26
    //   877: iconst_1
    //   878: iaload
    //   879: iconst_m1
    //   880: if_icmpne -> 917
    //   883: aload #26
    //   885: iconst_0
    //   886: iaload
    //   887: iconst_m1
    //   888: if_icmpne -> 917
    //   891: aload #26
    //   893: iconst_2
    //   894: iaload
    //   895: iconst_m1
    //   896: if_icmpne -> 917
    //   899: aload #26
    //   901: iconst_3
    //   902: iaload
    //   903: iconst_m1
    //   904: if_icmpeq -> 910
    //   907: goto -> 917
    //   910: iload #13
    //   912: istore #8
    //   914: goto -> 975
    //   917: iload #13
    //   919: aload #26
    //   921: iconst_3
    //   922: iaload
    //   923: aload #26
    //   925: iconst_0
    //   926: iaload
    //   927: aload #26
    //   929: iconst_1
    //   930: iaload
    //   931: aload #26
    //   933: iconst_2
    //   934: iaload
    //   935: invokestatic max : (II)I
    //   938: invokestatic max : (II)I
    //   941: invokestatic max : (II)I
    //   944: aload #25
    //   946: iconst_3
    //   947: iaload
    //   948: aload #25
    //   950: iconst_0
    //   951: iaload
    //   952: aload #25
    //   954: iconst_1
    //   955: iaload
    //   956: aload #25
    //   958: iconst_2
    //   959: iaload
    //   960: invokestatic max : (II)I
    //   963: invokestatic max : (II)I
    //   966: invokestatic max : (II)I
    //   969: iadd
    //   970: invokestatic max : (II)I
    //   973: istore #8
    //   975: iload #9
    //   977: istore #13
    //   979: iload #8
    //   981: istore #14
    //   983: iload #24
    //   985: ifeq -> 1174
    //   988: iload #21
    //   990: ldc_w -2147483648
    //   993: if_icmpeq -> 1005
    //   996: iload #8
    //   998: istore #14
    //   1000: iload #21
    //   1002: ifne -> 1174
    //   1005: aload_0
    //   1006: iconst_0
    //   1007: putfield mTotalLength : I
    //   1010: iconst_0
    //   1011: istore #9
    //   1013: iload #8
    //   1015: istore #14
    //   1017: iload #9
    //   1019: iload #16
    //   1021: if_icmpge -> 1174
    //   1024: aload_0
    //   1025: iload #9
    //   1027: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   1030: astore #28
    //   1032: aload #28
    //   1034: ifnonnull -> 1055
    //   1037: aload_0
    //   1038: aload_0
    //   1039: getfield mTotalLength : I
    //   1042: aload_0
    //   1043: iload #9
    //   1045: invokevirtual measureNullChild : (I)I
    //   1048: iadd
    //   1049: putfield mTotalLength : I
    //   1052: goto -> 1078
    //   1055: aload #28
    //   1057: invokevirtual getVisibility : ()I
    //   1060: bipush #8
    //   1062: if_icmpne -> 1081
    //   1065: iload #9
    //   1067: aload_0
    //   1068: aload #28
    //   1070: iload #9
    //   1072: invokevirtual getChildrenSkipCount : (Landroid/view/View;I)I
    //   1075: iadd
    //   1076: istore #9
    //   1078: goto -> 1168
    //   1081: aload #28
    //   1083: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1086: checkcast androidx/appcompat/widget/LinearLayoutCompat$LayoutParams
    //   1089: astore #27
    //   1091: iload #15
    //   1093: ifeq -> 1129
    //   1096: aload_0
    //   1097: aload_0
    //   1098: getfield mTotalLength : I
    //   1101: aload #27
    //   1103: getfield leftMargin : I
    //   1106: iload #7
    //   1108: iadd
    //   1109: aload #27
    //   1111: getfield rightMargin : I
    //   1114: iadd
    //   1115: aload_0
    //   1116: aload #28
    //   1118: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1121: iadd
    //   1122: iadd
    //   1123: putfield mTotalLength : I
    //   1126: goto -> 1078
    //   1129: aload_0
    //   1130: getfield mTotalLength : I
    //   1133: istore #14
    //   1135: aload_0
    //   1136: iload #14
    //   1138: iload #14
    //   1140: iload #7
    //   1142: iadd
    //   1143: aload #27
    //   1145: getfield leftMargin : I
    //   1148: iadd
    //   1149: aload #27
    //   1151: getfield rightMargin : I
    //   1154: iadd
    //   1155: aload_0
    //   1156: aload #28
    //   1158: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1161: iadd
    //   1162: invokestatic max : (II)I
    //   1165: putfield mTotalLength : I
    //   1168: iinc #9, 1
    //   1171: goto -> 1013
    //   1174: aload_0
    //   1175: getfield mTotalLength : I
    //   1178: aload_0
    //   1179: invokevirtual getPaddingLeft : ()I
    //   1182: aload_0
    //   1183: invokevirtual getPaddingRight : ()I
    //   1186: iadd
    //   1187: iadd
    //   1188: istore #8
    //   1190: aload_0
    //   1191: iload #8
    //   1193: putfield mTotalLength : I
    //   1196: iload #8
    //   1198: aload_0
    //   1199: invokevirtual getSuggestedMinimumWidth : ()I
    //   1202: invokestatic max : (II)I
    //   1205: iload_1
    //   1206: iconst_0
    //   1207: invokestatic resolveSizeAndState : (III)I
    //   1210: istore #18
    //   1212: ldc_w 16777215
    //   1215: iload #18
    //   1217: iand
    //   1218: aload_0
    //   1219: getfield mTotalLength : I
    //   1222: isub
    //   1223: istore #17
    //   1225: iload #12
    //   1227: ifne -> 1360
    //   1230: iload #17
    //   1232: ifeq -> 1244
    //   1235: fload_3
    //   1236: fconst_0
    //   1237: fcmpl
    //   1238: ifle -> 1244
    //   1241: goto -> 1360
    //   1244: iload #6
    //   1246: iload #11
    //   1248: invokestatic max : (II)I
    //   1251: istore #9
    //   1253: iload #24
    //   1255: ifeq -> 1345
    //   1258: iload #21
    //   1260: ldc 1073741824
    //   1262: if_icmpeq -> 1345
    //   1265: iconst_0
    //   1266: istore #6
    //   1268: iload #6
    //   1270: iload #16
    //   1272: if_icmpge -> 1345
    //   1275: aload_0
    //   1276: iload #6
    //   1278: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   1281: astore #25
    //   1283: aload #25
    //   1285: ifnull -> 1339
    //   1288: aload #25
    //   1290: invokevirtual getVisibility : ()I
    //   1293: bipush #8
    //   1295: if_icmpne -> 1301
    //   1298: goto -> 1339
    //   1301: aload #25
    //   1303: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1306: checkcast androidx/appcompat/widget/LinearLayoutCompat$LayoutParams
    //   1309: getfield weight : F
    //   1312: fconst_0
    //   1313: fcmpl
    //   1314: ifle -> 1339
    //   1317: aload #25
    //   1319: iload #7
    //   1321: ldc 1073741824
    //   1323: invokestatic makeMeasureSpec : (II)I
    //   1326: aload #25
    //   1328: invokevirtual getMeasuredHeight : ()I
    //   1331: ldc 1073741824
    //   1333: invokestatic makeMeasureSpec : (II)I
    //   1336: invokevirtual measure : (II)V
    //   1339: iinc #6, 1
    //   1342: goto -> 1268
    //   1345: iload #16
    //   1347: istore #8
    //   1349: iload #14
    //   1351: istore #6
    //   1353: iload #9
    //   1355: istore #7
    //   1357: goto -> 2096
    //   1360: aload_0
    //   1361: getfield mWeightSum : F
    //   1364: fstore #4
    //   1366: fload #4
    //   1368: fconst_0
    //   1369: fcmpl
    //   1370: ifle -> 1376
    //   1373: fload #4
    //   1375: fstore_3
    //   1376: aload #26
    //   1378: iconst_3
    //   1379: iconst_m1
    //   1380: iastore
    //   1381: aload #26
    //   1383: iconst_2
    //   1384: iconst_m1
    //   1385: iastore
    //   1386: aload #26
    //   1388: iconst_1
    //   1389: iconst_m1
    //   1390: iastore
    //   1391: aload #26
    //   1393: iconst_0
    //   1394: iconst_m1
    //   1395: iastore
    //   1396: aload #25
    //   1398: iconst_3
    //   1399: iconst_m1
    //   1400: iastore
    //   1401: aload #25
    //   1403: iconst_2
    //   1404: iconst_m1
    //   1405: iastore
    //   1406: aload #25
    //   1408: iconst_1
    //   1409: iconst_m1
    //   1410: iastore
    //   1411: aload #25
    //   1413: iconst_0
    //   1414: iconst_m1
    //   1415: iastore
    //   1416: aload_0
    //   1417: iconst_0
    //   1418: putfield mTotalLength : I
    //   1421: iload #13
    //   1423: istore #9
    //   1425: iconst_m1
    //   1426: istore #11
    //   1428: iconst_0
    //   1429: istore #13
    //   1431: iload #5
    //   1433: istore #8
    //   1435: iload #16
    //   1437: istore #7
    //   1439: iload #9
    //   1441: istore #5
    //   1443: iload #6
    //   1445: istore #9
    //   1447: iload #17
    //   1449: istore #6
    //   1451: iload #13
    //   1453: iload #7
    //   1455: if_icmpge -> 1962
    //   1458: aload_0
    //   1459: iload #13
    //   1461: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   1464: astore #28
    //   1466: aload #28
    //   1468: ifnull -> 1956
    //   1471: aload #28
    //   1473: invokevirtual getVisibility : ()I
    //   1476: bipush #8
    //   1478: if_icmpne -> 1484
    //   1481: goto -> 1956
    //   1484: aload #28
    //   1486: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1489: checkcast androidx/appcompat/widget/LinearLayoutCompat$LayoutParams
    //   1492: astore #27
    //   1494: aload #27
    //   1496: getfield weight : F
    //   1499: fstore #4
    //   1501: fload #4
    //   1503: fconst_0
    //   1504: fcmpl
    //   1505: ifle -> 1668
    //   1508: iload #6
    //   1510: i2f
    //   1511: fload #4
    //   1513: fmul
    //   1514: fload_3
    //   1515: fdiv
    //   1516: f2i
    //   1517: istore #14
    //   1519: iload_2
    //   1520: aload_0
    //   1521: invokevirtual getPaddingTop : ()I
    //   1524: aload_0
    //   1525: invokevirtual getPaddingBottom : ()I
    //   1528: iadd
    //   1529: aload #27
    //   1531: getfield topMargin : I
    //   1534: iadd
    //   1535: aload #27
    //   1537: getfield bottomMargin : I
    //   1540: iadd
    //   1541: aload #27
    //   1543: getfield height : I
    //   1546: invokestatic getChildMeasureSpec : (III)I
    //   1549: istore #17
    //   1551: aload #27
    //   1553: getfield width : I
    //   1556: ifne -> 1601
    //   1559: iload #21
    //   1561: ldc 1073741824
    //   1563: if_icmpeq -> 1569
    //   1566: goto -> 1601
    //   1569: iload #14
    //   1571: ifle -> 1581
    //   1574: iload #14
    //   1576: istore #12
    //   1578: goto -> 1584
    //   1581: iconst_0
    //   1582: istore #12
    //   1584: aload #28
    //   1586: iload #12
    //   1588: ldc 1073741824
    //   1590: invokestatic makeMeasureSpec : (II)I
    //   1593: iload #17
    //   1595: invokevirtual measure : (II)V
    //   1598: goto -> 1637
    //   1601: aload #28
    //   1603: invokevirtual getMeasuredWidth : ()I
    //   1606: iload #14
    //   1608: iadd
    //   1609: istore #16
    //   1611: iload #16
    //   1613: istore #12
    //   1615: iload #16
    //   1617: ifge -> 1623
    //   1620: iconst_0
    //   1621: istore #12
    //   1623: aload #28
    //   1625: iload #12
    //   1627: ldc 1073741824
    //   1629: invokestatic makeMeasureSpec : (II)I
    //   1632: iload #17
    //   1634: invokevirtual measure : (II)V
    //   1637: iload #5
    //   1639: aload #28
    //   1641: invokevirtual getMeasuredState : ()I
    //   1644: ldc_w -16777216
    //   1647: iand
    //   1648: invokestatic combineMeasuredStates : (II)I
    //   1651: istore #5
    //   1653: fload_3
    //   1654: fload #4
    //   1656: fsub
    //   1657: fstore_3
    //   1658: iload #6
    //   1660: iload #14
    //   1662: isub
    //   1663: istore #6
    //   1665: goto -> 1668
    //   1668: iload #15
    //   1670: ifeq -> 1709
    //   1673: aload_0
    //   1674: aload_0
    //   1675: getfield mTotalLength : I
    //   1678: aload #28
    //   1680: invokevirtual getMeasuredWidth : ()I
    //   1683: aload #27
    //   1685: getfield leftMargin : I
    //   1688: iadd
    //   1689: aload #27
    //   1691: getfield rightMargin : I
    //   1694: iadd
    //   1695: aload_0
    //   1696: aload #28
    //   1698: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1701: iadd
    //   1702: iadd
    //   1703: putfield mTotalLength : I
    //   1706: goto -> 1751
    //   1709: aload_0
    //   1710: getfield mTotalLength : I
    //   1713: istore #12
    //   1715: aload_0
    //   1716: iload #12
    //   1718: aload #28
    //   1720: invokevirtual getMeasuredWidth : ()I
    //   1723: iload #12
    //   1725: iadd
    //   1726: aload #27
    //   1728: getfield leftMargin : I
    //   1731: iadd
    //   1732: aload #27
    //   1734: getfield rightMargin : I
    //   1737: iadd
    //   1738: aload_0
    //   1739: aload #28
    //   1741: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1744: iadd
    //   1745: invokestatic max : (II)I
    //   1748: putfield mTotalLength : I
    //   1751: iload #20
    //   1753: ldc 1073741824
    //   1755: if_icmpeq -> 1773
    //   1758: aload #27
    //   1760: getfield height : I
    //   1763: iconst_m1
    //   1764: if_icmpne -> 1773
    //   1767: iconst_1
    //   1768: istore #12
    //   1770: goto -> 1776
    //   1773: iconst_0
    //   1774: istore #12
    //   1776: aload #27
    //   1778: getfield topMargin : I
    //   1781: aload #27
    //   1783: getfield bottomMargin : I
    //   1786: iadd
    //   1787: istore #17
    //   1789: aload #28
    //   1791: invokevirtual getMeasuredHeight : ()I
    //   1794: iload #17
    //   1796: iadd
    //   1797: istore #16
    //   1799: iload #11
    //   1801: iload #16
    //   1803: invokestatic max : (II)I
    //   1806: istore #14
    //   1808: iload #12
    //   1810: ifeq -> 1820
    //   1813: iload #17
    //   1815: istore #11
    //   1817: goto -> 1824
    //   1820: iload #16
    //   1822: istore #11
    //   1824: iload #9
    //   1826: iload #11
    //   1828: invokestatic max : (II)I
    //   1831: istore #11
    //   1833: iload #8
    //   1835: ifeq -> 1853
    //   1838: aload #27
    //   1840: getfield height : I
    //   1843: iconst_m1
    //   1844: if_icmpne -> 1853
    //   1847: iconst_1
    //   1848: istore #8
    //   1850: goto -> 1856
    //   1853: iconst_0
    //   1854: istore #8
    //   1856: iload #23
    //   1858: ifeq -> 1945
    //   1861: aload #28
    //   1863: invokevirtual getBaseline : ()I
    //   1866: istore #12
    //   1868: iload #12
    //   1870: iconst_m1
    //   1871: if_icmpeq -> 1945
    //   1874: aload #27
    //   1876: getfield gravity : I
    //   1879: ifge -> 1891
    //   1882: aload_0
    //   1883: getfield mGravity : I
    //   1886: istore #9
    //   1888: goto -> 1898
    //   1891: aload #27
    //   1893: getfield gravity : I
    //   1896: istore #9
    //   1898: iload #9
    //   1900: bipush #112
    //   1902: iand
    //   1903: iconst_4
    //   1904: ishr
    //   1905: bipush #-2
    //   1907: iand
    //   1908: iconst_1
    //   1909: ishr
    //   1910: istore #9
    //   1912: aload #26
    //   1914: iload #9
    //   1916: aload #26
    //   1918: iload #9
    //   1920: iaload
    //   1921: iload #12
    //   1923: invokestatic max : (II)I
    //   1926: iastore
    //   1927: aload #25
    //   1929: iload #9
    //   1931: aload #25
    //   1933: iload #9
    //   1935: iaload
    //   1936: iload #16
    //   1938: iload #12
    //   1940: isub
    //   1941: invokestatic max : (II)I
    //   1944: iastore
    //   1945: iload #11
    //   1947: istore #9
    //   1949: iload #14
    //   1951: istore #11
    //   1953: goto -> 1956
    //   1956: iinc #13, 1
    //   1959: goto -> 1451
    //   1962: aload_0
    //   1963: aload_0
    //   1964: getfield mTotalLength : I
    //   1967: aload_0
    //   1968: invokevirtual getPaddingLeft : ()I
    //   1971: aload_0
    //   1972: invokevirtual getPaddingRight : ()I
    //   1975: iadd
    //   1976: iadd
    //   1977: putfield mTotalLength : I
    //   1980: aload #26
    //   1982: iconst_1
    //   1983: iaload
    //   1984: iconst_m1
    //   1985: if_icmpne -> 2022
    //   1988: aload #26
    //   1990: iconst_0
    //   1991: iaload
    //   1992: iconst_m1
    //   1993: if_icmpne -> 2022
    //   1996: aload #26
    //   1998: iconst_2
    //   1999: iaload
    //   2000: iconst_m1
    //   2001: if_icmpne -> 2022
    //   2004: aload #26
    //   2006: iconst_3
    //   2007: iaload
    //   2008: iconst_m1
    //   2009: if_icmpeq -> 2015
    //   2012: goto -> 2022
    //   2015: iload #11
    //   2017: istore #6
    //   2019: goto -> 2080
    //   2022: iload #11
    //   2024: aload #26
    //   2026: iconst_3
    //   2027: iaload
    //   2028: aload #26
    //   2030: iconst_0
    //   2031: iaload
    //   2032: aload #26
    //   2034: iconst_1
    //   2035: iaload
    //   2036: aload #26
    //   2038: iconst_2
    //   2039: iaload
    //   2040: invokestatic max : (II)I
    //   2043: invokestatic max : (II)I
    //   2046: invokestatic max : (II)I
    //   2049: aload #25
    //   2051: iconst_3
    //   2052: iaload
    //   2053: aload #25
    //   2055: iconst_0
    //   2056: iaload
    //   2057: aload #25
    //   2059: iconst_1
    //   2060: iaload
    //   2061: aload #25
    //   2063: iconst_2
    //   2064: iaload
    //   2065: invokestatic max : (II)I
    //   2068: invokestatic max : (II)I
    //   2071: invokestatic max : (II)I
    //   2074: iadd
    //   2075: invokestatic max : (II)I
    //   2078: istore #6
    //   2080: iload #5
    //   2082: istore #13
    //   2084: iload #8
    //   2086: istore #5
    //   2088: iload #7
    //   2090: istore #8
    //   2092: iload #9
    //   2094: istore #7
    //   2096: iload #5
    //   2098: ifne -> 2111
    //   2101: iload #20
    //   2103: ldc 1073741824
    //   2105: if_icmpeq -> 2111
    //   2108: goto -> 2115
    //   2111: iload #6
    //   2113: istore #7
    //   2115: aload_0
    //   2116: iload #18
    //   2118: iload #13
    //   2120: ldc_w -16777216
    //   2123: iand
    //   2124: ior
    //   2125: iload #7
    //   2127: aload_0
    //   2128: invokevirtual getPaddingTop : ()I
    //   2131: aload_0
    //   2132: invokevirtual getPaddingBottom : ()I
    //   2135: iadd
    //   2136: iadd
    //   2137: aload_0
    //   2138: invokevirtual getSuggestedMinimumHeight : ()I
    //   2141: invokestatic max : (II)I
    //   2144: iload_2
    //   2145: iload #13
    //   2147: bipush #16
    //   2149: ishl
    //   2150: invokestatic resolveSizeAndState : (III)I
    //   2153: invokevirtual setMeasuredDimension : (II)V
    //   2156: iload #10
    //   2158: ifeq -> 2168
    //   2161: aload_0
    //   2162: iload #8
    //   2164: iload_1
    //   2165: invokespecial forceUniformHeight : (II)V
    //   2168: return
  }
  
  int measureNullChild(int paramInt) {
    return 0;
  }
  
  void measureVertical(int paramInt1, int paramInt2) {
    this.mTotalLength = 0;
    int i4 = getVirtualChildCount();
    int i7 = View.MeasureSpec.getMode(paramInt1);
    int i5 = View.MeasureSpec.getMode(paramInt2);
    int i8 = this.mBaselineAlignedChildIndex;
    boolean bool1 = this.mUseLargestChild;
    float f = 0.0F;
    int j = 0;
    int i3 = 0;
    int n = 0;
    int i = 0;
    int m = 0;
    int i1 = 0;
    int i2 = 0;
    int k = 1;
    boolean bool = false;
    while (i1 < i4) {
      View view = getVirtualChildAt(i1);
      if (view == null) {
        this.mTotalLength += measureNullChild(i1);
      } else if (view.getVisibility() == 8) {
        i1 += getChildrenSkipCount(view, i1);
      } else {
        if (hasDividerBeforeChildAt(i1))
          this.mTotalLength += this.mDividerHeight; 
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        f += layoutParams.weight;
        if (i5 == 1073741824 && layoutParams.height == 0 && layoutParams.weight > 0.0F) {
          i2 = this.mTotalLength;
          this.mTotalLength = Math.max(i2, layoutParams.topMargin + i2 + layoutParams.bottomMargin);
          i2 = 1;
        } else {
          if (layoutParams.height == 0 && layoutParams.weight > 0.0F) {
            layoutParams.height = -2;
            i9 = 0;
          } else {
            i9 = Integer.MIN_VALUE;
          } 
          if (f == 0.0F) {
            i10 = this.mTotalLength;
          } else {
            i10 = 0;
          } 
          measureChildBeforeLayout(view, i1, paramInt1, 0, paramInt2, i10);
          if (i9 != Integer.MIN_VALUE)
            layoutParams.height = i9; 
          int i10 = view.getMeasuredHeight();
          int i9 = this.mTotalLength;
          this.mTotalLength = Math.max(i9, i9 + i10 + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
          if (bool1)
            n = Math.max(i10, n); 
        } 
        if (i8 >= 0 && i8 == i1 + 1)
          this.mBaselineChildTop = this.mTotalLength; 
        if (i1 >= i8 || layoutParams.weight <= 0.0F) {
          if (i7 != 1073741824 && layoutParams.width == -1) {
            i9 = 1;
            bool = true;
          } else {
            i9 = 0;
          } 
          int i10 = layoutParams.leftMargin + layoutParams.rightMargin;
          int i11 = view.getMeasuredWidth() + i10;
          i3 = Math.max(i3, i11);
          int i12 = View.combineMeasuredStates(j, view.getMeasuredState());
          if (k && layoutParams.width == -1) {
            j = 1;
          } else {
            j = 0;
          } 
          if (layoutParams.weight > 0.0F) {
            if (!i9)
              i10 = i11; 
            i = Math.max(i, i10);
            k = m;
          } else {
            if (!i9)
              i10 = i11; 
            k = Math.max(m, i10);
          } 
          i10 = getChildrenSkipCount(view, i1);
          m = k;
          int i9 = i12;
          i1 = i10 + i1;
          k = j;
          j = i9;
        } else {
          throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
        } 
      } 
      i1++;
    } 
    if (this.mTotalLength > 0 && hasDividerBeforeChildAt(i4))
      this.mTotalLength += this.mDividerHeight; 
    if (bool1 && (i5 == Integer.MIN_VALUE || i5 == 0)) {
      this.mTotalLength = 0;
      for (i1 = 0; i1 < i4; i1++) {
        View view = getVirtualChildAt(i1);
        if (view == null) {
          this.mTotalLength += measureNullChild(i1);
        } else if (view.getVisibility() == 8) {
          i1 += getChildrenSkipCount(view, i1);
        } else {
          LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
          int i9 = this.mTotalLength;
          this.mTotalLength = Math.max(i9, i9 + n + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
        } 
      } 
    } 
    i1 = this.mTotalLength + getPaddingTop() + getPaddingBottom();
    this.mTotalLength = i1;
    int i6 = View.resolveSizeAndState(Math.max(i1, getSuggestedMinimumHeight()), paramInt2, 0);
    i1 = (0xFFFFFF & i6) - this.mTotalLength;
    if (i2 != 0 || (i1 != 0 && f > 0.0F)) {
      float f1 = this.mWeightSum;
      if (f1 > 0.0F)
        f = f1; 
      this.mTotalLength = 0;
      n = i1;
      i = j;
      i1 = 0;
      j = n;
      n = i3;
      while (i1 < i4) {
        View view = getVirtualChildAt(i1);
        if (view.getVisibility() != 8) {
          LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
          f1 = layoutParams.weight;
          if (f1 > 0.0F) {
            i3 = (int)(j * f1 / f);
            int i10 = getPaddingLeft();
            int i11 = getPaddingRight();
            i8 = layoutParams.leftMargin;
            int i13 = layoutParams.rightMargin;
            int i12 = layoutParams.width;
            i2 = j - i3;
            i10 = getChildMeasureSpec(paramInt1, i10 + i11 + i8 + i13, i12);
            if (layoutParams.height != 0 || i5 != 1073741824) {
              i3 = view.getMeasuredHeight() + i3;
              j = i3;
              if (i3 < 0)
                j = 0; 
              view.measure(i10, View.MeasureSpec.makeMeasureSpec(j, 1073741824));
            } else {
              if (i3 > 0) {
                j = i3;
              } else {
                j = 0;
              } 
              view.measure(i10, View.MeasureSpec.makeMeasureSpec(j, 1073741824));
            } 
            i = View.combineMeasuredStates(i, view.getMeasuredState() & 0xFFFFFF00);
            f -= f1;
            j = i2;
          } 
          i3 = layoutParams.leftMargin + layoutParams.rightMargin;
          int i9 = view.getMeasuredWidth() + i3;
          i2 = Math.max(n, i9);
          if (i7 != 1073741824 && layoutParams.width == -1) {
            n = 1;
          } else {
            n = 0;
          } 
          if (n != 0) {
            n = i3;
          } else {
            n = i9;
          } 
          m = Math.max(m, n);
          if (k != 0 && layoutParams.width == -1) {
            k = 1;
          } else {
            k = 0;
          } 
          n = this.mTotalLength;
          this.mTotalLength = Math.max(n, view.getMeasuredHeight() + n + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
          n = i2;
        } 
        i1++;
      } 
      this.mTotalLength += getPaddingTop() + getPaddingBottom();
      j = i;
      i = m;
    } else {
      m = Math.max(m, i);
      if (bool1 && i5 != 1073741824)
        for (i = 0; i < i4; i++) {
          View view = getVirtualChildAt(i);
          if (view != null && view.getVisibility() != 8 && ((LayoutParams)view.getLayoutParams()).weight > 0.0F)
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(n, 1073741824)); 
        }  
      i = m;
      n = i3;
    } 
    if (k != 0 || i7 == 1073741824)
      i = n; 
    setMeasuredDimension(View.resolveSizeAndState(Math.max(i + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), paramInt1, j), i6);
    if (bool)
      forceUniformWidth(i4, paramInt2); 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    if (this.mDivider == null)
      return; 
    if (this.mOrientation == 1) {
      drawDividersVertical(paramCanvas);
    } else {
      drawDividersHorizontal(paramCanvas);
    } 
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mOrientation == 1) {
      layoutVertical(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      layoutHorizontal(paramInt1, paramInt2, paramInt3, paramInt4);
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    if (this.mOrientation == 1) {
      measureVertical(paramInt1, paramInt2);
    } else {
      measureHorizontal(paramInt1, paramInt2);
    } 
  }
  
  public void setBaselineAligned(boolean paramBoolean) {
    this.mBaselineAligned = paramBoolean;
  }
  
  public void setBaselineAlignedChildIndex(int paramInt) {
    if (paramInt >= 0 && paramInt < getChildCount()) {
      this.mBaselineAlignedChildIndex = paramInt;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("base aligned child index out of range (0, ");
    stringBuilder.append(getChildCount());
    stringBuilder.append(")");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setDividerDrawable(Drawable paramDrawable) {
    if (paramDrawable == this.mDivider)
      return; 
    this.mDivider = paramDrawable;
    boolean bool = false;
    if (paramDrawable != null) {
      this.mDividerWidth = paramDrawable.getIntrinsicWidth();
      this.mDividerHeight = paramDrawable.getIntrinsicHeight();
    } else {
      this.mDividerWidth = 0;
      this.mDividerHeight = 0;
    } 
    if (paramDrawable == null)
      bool = true; 
    setWillNotDraw(bool);
    requestLayout();
  }
  
  public void setDividerPadding(int paramInt) {
    this.mDividerPadding = paramInt;
  }
  
  public void setGravity(int paramInt) {
    if (this.mGravity != paramInt) {
      int i = paramInt;
      if ((0x800007 & paramInt) == 0)
        i = paramInt | 0x800003; 
      paramInt = i;
      if ((i & 0x70) == 0)
        paramInt = i | 0x30; 
      this.mGravity = paramInt;
      requestLayout();
    } 
  }
  
  public void setHorizontalGravity(int paramInt) {
    paramInt &= 0x800007;
    int i = this.mGravity;
    if ((0x800007 & i) != paramInt) {
      this.mGravity = paramInt | 0xFF7FFFF8 & i;
      requestLayout();
    } 
  }
  
  public void setMeasureWithLargestChildEnabled(boolean paramBoolean) {
    this.mUseLargestChild = paramBoolean;
  }
  
  public void setOrientation(int paramInt) {
    if (this.mOrientation != paramInt) {
      this.mOrientation = paramInt;
      requestLayout();
    } 
  }
  
  public void setShowDividers(int paramInt) {
    if (paramInt != this.mShowDividers)
      requestLayout(); 
    this.mShowDividers = paramInt;
  }
  
  public void setVerticalGravity(int paramInt) {
    int i = paramInt & 0x70;
    paramInt = this.mGravity;
    if ((paramInt & 0x70) != i) {
      this.mGravity = i | paramInt & 0xFFFFFF8F;
      requestLayout();
    } 
  }
  
  public void setWeightSum(float paramFloat) {
    this.mWeightSum = Math.max(0.0F, paramFloat);
  }
  
  public boolean shouldDelayChildPressedState() {
    return false;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DividerMode {}
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    public int gravity = -1;
    
    public float weight;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
      this.weight = 0.0F;
    }
    
    public LayoutParams(int param1Int1, int param1Int2, float param1Float) {
      super(param1Int1, param1Int2);
      this.weight = param1Float;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.LinearLayoutCompat_Layout);
      this.weight = typedArray.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0F);
      this.gravity = typedArray.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
      typedArray.recycle();
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.weight = param1LayoutParams.weight;
      this.gravity = param1LayoutParams.gravity;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface OrientationMode {}
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\LinearLayoutCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */