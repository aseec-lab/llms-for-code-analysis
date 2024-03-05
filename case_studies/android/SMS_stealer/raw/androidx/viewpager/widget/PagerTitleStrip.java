package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

@DecorView
public class PagerTitleStrip extends ViewGroup {
  private static final int[] ATTRS = new int[] { 16842804, 16842901, 16842904, 16842927 };
  
  private static final float SIDE_ALPHA = 0.6F;
  
  private static final int[] TEXT_ATTRS = new int[] { 16843660 };
  
  private static final int TEXT_SPACING = 16;
  
  TextView mCurrText;
  
  private int mGravity;
  
  private int mLastKnownCurrentPage = -1;
  
  float mLastKnownPositionOffset = -1.0F;
  
  TextView mNextText;
  
  private int mNonPrimaryAlpha;
  
  private final PageListener mPageListener = new PageListener();
  
  ViewPager mPager;
  
  TextView mPrevText;
  
  private int mScaledTextSpacing;
  
  int mTextColor;
  
  private boolean mUpdatingPositions;
  
  private boolean mUpdatingText;
  
  private WeakReference<PagerAdapter> mWatchingAdapter;
  
  public PagerTitleStrip(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public PagerTitleStrip(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    TextView textView = new TextView(paramContext);
    this.mPrevText = textView;
    addView((View)textView);
    textView = new TextView(paramContext);
    this.mCurrText = textView;
    addView((View)textView);
    textView = new TextView(paramContext);
    this.mNextText = textView;
    addView((View)textView);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, ATTRS);
    boolean bool = false;
    int i = typedArray.getResourceId(0, 0);
    if (i != 0) {
      TextViewCompat.setTextAppearance(this.mPrevText, i);
      TextViewCompat.setTextAppearance(this.mCurrText, i);
      TextViewCompat.setTextAppearance(this.mNextText, i);
    } 
    int j = typedArray.getDimensionPixelSize(1, 0);
    if (j != 0)
      setTextSize(0, j); 
    if (typedArray.hasValue(2)) {
      j = typedArray.getColor(2, 0);
      this.mPrevText.setTextColor(j);
      this.mCurrText.setTextColor(j);
      this.mNextText.setTextColor(j);
    } 
    this.mGravity = typedArray.getInteger(3, 80);
    typedArray.recycle();
    this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
    setNonPrimaryAlpha(0.6F);
    this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
    this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
    this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
    if (i != 0) {
      typedArray = paramContext.obtainStyledAttributes(i, TEXT_ATTRS);
      bool = typedArray.getBoolean(0, false);
      typedArray.recycle();
    } 
    if (bool) {
      setSingleLineAllCaps(this.mPrevText);
      setSingleLineAllCaps(this.mCurrText);
      setSingleLineAllCaps(this.mNextText);
    } else {
      this.mPrevText.setSingleLine();
      this.mCurrText.setSingleLine();
      this.mNextText.setSingleLine();
    } 
    this.mScaledTextSpacing = (int)((paramContext.getResources().getDisplayMetrics()).density * 16.0F);
  }
  
  private static void setSingleLineAllCaps(TextView paramTextView) {
    paramTextView.setTransformationMethod((TransformationMethod)new SingleLineAllCapsTransform(paramTextView.getContext()));
  }
  
  int getMinHeight() {
    boolean bool;
    Drawable drawable = getBackground();
    if (drawable != null) {
      bool = drawable.getIntrinsicHeight();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getTextSpacing() {
    return this.mScaledTextSpacing;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewParent viewParent = getParent();
    if (viewParent instanceof ViewPager) {
      ViewPager viewPager = (ViewPager)viewParent;
      PagerAdapter pagerAdapter = viewPager.getAdapter();
      viewPager.setInternalPageChangeListener(this.mPageListener);
      viewPager.addOnAdapterChangeListener(this.mPageListener);
      this.mPager = viewPager;
      WeakReference<PagerAdapter> weakReference = this.mWatchingAdapter;
      if (weakReference != null) {
        PagerAdapter pagerAdapter1 = weakReference.get();
      } else {
        weakReference = null;
      } 
      updateAdapter((PagerAdapter)weakReference, pagerAdapter);
      return;
    } 
    throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewPager viewPager = this.mPager;
    if (viewPager != null) {
      updateAdapter(viewPager.getAdapter(), (PagerAdapter)null);
      this.mPager.setInternalPageChangeListener(null);
      this.mPager.removeOnAdapterChangeListener(this.mPageListener);
      this.mPager = null;
    } 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mPager != null) {
      float f = this.mLastKnownPositionOffset;
      if (f < 0.0F)
        f = 0.0F; 
      updateTextPositions(this.mLastKnownCurrentPage, f, true);
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    if (View.MeasureSpec.getMode(paramInt1) == 1073741824) {
      int j = getPaddingTop() + getPaddingBottom();
      int k = getChildMeasureSpec(paramInt2, j, -2);
      int i = View.MeasureSpec.getSize(paramInt1);
      paramInt1 = getChildMeasureSpec(paramInt1, (int)(i * 0.2F), -2);
      this.mPrevText.measure(paramInt1, k);
      this.mCurrText.measure(paramInt1, k);
      this.mNextText.measure(paramInt1, k);
      if (View.MeasureSpec.getMode(paramInt2) == 1073741824) {
        paramInt1 = View.MeasureSpec.getSize(paramInt2);
      } else {
        paramInt1 = this.mCurrText.getMeasuredHeight();
        paramInt1 = Math.max(getMinHeight(), paramInt1 + j);
      } 
      setMeasuredDimension(i, View.resolveSizeAndState(paramInt1, paramInt2, this.mCurrText.getMeasuredState() << 16));
      return;
    } 
    throw new IllegalStateException("Must measure with an exact width");
  }
  
  public void requestLayout() {
    if (!this.mUpdatingText)
      super.requestLayout(); 
  }
  
  public void setGravity(int paramInt) {
    this.mGravity = paramInt;
    requestLayout();
  }
  
  public void setNonPrimaryAlpha(float paramFloat) {
    int i = (int)(paramFloat * 255.0F) & 0xFF;
    this.mNonPrimaryAlpha = i;
    i = i << 24 | this.mTextColor & 0xFFFFFF;
    this.mPrevText.setTextColor(i);
    this.mNextText.setTextColor(i);
  }
  
  public void setTextColor(int paramInt) {
    this.mTextColor = paramInt;
    this.mCurrText.setTextColor(paramInt);
    paramInt = this.mNonPrimaryAlpha << 24 | this.mTextColor & 0xFFFFFF;
    this.mPrevText.setTextColor(paramInt);
    this.mNextText.setTextColor(paramInt);
  }
  
  public void setTextSize(int paramInt, float paramFloat) {
    this.mPrevText.setTextSize(paramInt, paramFloat);
    this.mCurrText.setTextSize(paramInt, paramFloat);
    this.mNextText.setTextSize(paramInt, paramFloat);
  }
  
  public void setTextSpacing(int paramInt) {
    this.mScaledTextSpacing = paramInt;
    requestLayout();
  }
  
  void updateAdapter(PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2) {
    if (paramPagerAdapter1 != null) {
      paramPagerAdapter1.unregisterDataSetObserver(this.mPageListener);
      this.mWatchingAdapter = null;
    } 
    if (paramPagerAdapter2 != null) {
      paramPagerAdapter2.registerDataSetObserver(this.mPageListener);
      this.mWatchingAdapter = new WeakReference<PagerAdapter>(paramPagerAdapter2);
    } 
    ViewPager viewPager = this.mPager;
    if (viewPager != null) {
      this.mLastKnownCurrentPage = -1;
      this.mLastKnownPositionOffset = -1.0F;
      updateText(viewPager.getCurrentItem(), paramPagerAdapter2);
      requestLayout();
    } 
  }
  
  void updateText(int paramInt, PagerAdapter paramPagerAdapter) {
    if (paramPagerAdapter != null) {
      i = paramPagerAdapter.getCount();
    } else {
      i = 0;
    } 
    this.mUpdatingText = true;
    CharSequence charSequence2 = null;
    if (paramInt >= 1 && paramPagerAdapter != null) {
      charSequence1 = paramPagerAdapter.getPageTitle(paramInt - 1);
    } else {
      charSequence1 = null;
    } 
    this.mPrevText.setText(charSequence1);
    TextView textView = this.mCurrText;
    if (paramPagerAdapter != null && paramInt < i) {
      charSequence1 = paramPagerAdapter.getPageTitle(paramInt);
    } else {
      charSequence1 = null;
    } 
    textView.setText(charSequence1);
    int j = paramInt + 1;
    CharSequence charSequence1 = charSequence2;
    if (j < i) {
      charSequence1 = charSequence2;
      if (paramPagerAdapter != null)
        charSequence1 = paramPagerAdapter.getPageTitle(j); 
    } 
    this.mNextText.setText(charSequence1);
    int i = View.MeasureSpec.makeMeasureSpec(Math.max(0, (int)((getWidth() - getPaddingLeft() - getPaddingRight()) * 0.8F)), -2147483648);
    j = View.MeasureSpec.makeMeasureSpec(Math.max(0, getHeight() - getPaddingTop() - getPaddingBottom()), -2147483648);
    this.mPrevText.measure(i, j);
    this.mCurrText.measure(i, j);
    this.mNextText.measure(i, j);
    this.mLastKnownCurrentPage = paramInt;
    if (!this.mUpdatingPositions)
      updateTextPositions(paramInt, this.mLastKnownPositionOffset, false); 
    this.mUpdatingText = false;
  }
  
  void updateTextPositions(int paramInt, float paramFloat, boolean paramBoolean) {
    if (paramInt != this.mLastKnownCurrentPage) {
      updateText(paramInt, this.mPager.getAdapter());
    } else if (!paramBoolean && paramFloat == this.mLastKnownPositionOffset) {
      return;
    } 
    this.mUpdatingPositions = true;
    int m = this.mPrevText.getMeasuredWidth();
    int j = this.mCurrText.getMeasuredWidth();
    int k = this.mNextText.getMeasuredWidth();
    int i3 = j / 2;
    int i1 = getWidth();
    int i5 = getHeight();
    int i2 = getPaddingLeft();
    int n = getPaddingRight();
    paramInt = getPaddingTop();
    int i = getPaddingBottom();
    int i4 = n + i3;
    float f2 = 0.5F + paramFloat;
    float f1 = f2;
    if (f2 > 1.0F)
      f1 = f2 - 1.0F; 
    i4 = i1 - i4 - (int)((i1 - i2 + i3 - i4) * f1) - i3;
    i3 = j + i4;
    int i7 = this.mPrevText.getBaseline();
    j = this.mCurrText.getBaseline();
    int i6 = this.mNextText.getBaseline();
    int i8 = Math.max(Math.max(i7, j), i6);
    i7 = i8 - i7;
    j = i8 - j;
    i6 = i8 - i6;
    int i9 = this.mPrevText.getMeasuredHeight();
    int i10 = this.mCurrText.getMeasuredHeight();
    i8 = this.mNextText.getMeasuredHeight();
    i8 = Math.max(Math.max(i9 + i7, i10 + j), i8 + i6);
    i9 = this.mGravity & 0x70;
    if (i9 != 16) {
      if (i9 != 80) {
        i = i7 + paramInt;
        j += paramInt;
        paramInt += i6;
      } else {
        paramInt = i5 - i - i8;
        i = i7 + paramInt;
        j += paramInt;
        paramInt += i6;
      } 
    } else {
      paramInt = (i5 - paramInt - i - i8) / 2;
      i = i7 + paramInt;
      j += paramInt;
      paramInt += i6;
    } 
    TextView textView = this.mCurrText;
    textView.layout(i4, j, i3, textView.getMeasuredHeight() + j);
    j = Math.min(i2, i4 - this.mScaledTextSpacing - m);
    textView = this.mPrevText;
    textView.layout(j, i, m + j, textView.getMeasuredHeight() + i);
    i = Math.max(i1 - n - k, i3 + this.mScaledTextSpacing);
    textView = this.mNextText;
    textView.layout(i, paramInt, i + k, textView.getMeasuredHeight() + paramInt);
    this.mLastKnownPositionOffset = paramFloat;
    this.mUpdatingPositions = false;
  }
  
  private class PageListener extends DataSetObserver implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
    private int mScrollState;
    
    final PagerTitleStrip this$0;
    
    public void onAdapterChanged(ViewPager param1ViewPager, PagerAdapter param1PagerAdapter1, PagerAdapter param1PagerAdapter2) {
      PagerTitleStrip.this.updateAdapter(param1PagerAdapter1, param1PagerAdapter2);
    }
    
    public void onChanged() {
      PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
      pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
      float f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
      float f1 = 0.0F;
      if (f2 >= 0.0F)
        f1 = PagerTitleStrip.this.mLastKnownPositionOffset; 
      pagerTitleStrip = PagerTitleStrip.this;
      pagerTitleStrip.updateTextPositions(pagerTitleStrip.mPager.getCurrentItem(), f1, true);
    }
    
    public void onPageScrollStateChanged(int param1Int) {
      this.mScrollState = param1Int;
    }
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {
      param1Int2 = param1Int1;
      if (param1Float > 0.5F)
        param1Int2 = param1Int1 + 1; 
      PagerTitleStrip.this.updateTextPositions(param1Int2, param1Float, false);
    }
    
    public void onPageSelected(int param1Int) {
      if (this.mScrollState == 0) {
        PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
        pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
        float f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
        float f1 = 0.0F;
        if (f2 >= 0.0F)
          f1 = PagerTitleStrip.this.mLastKnownPositionOffset; 
        pagerTitleStrip = PagerTitleStrip.this;
        pagerTitleStrip.updateTextPositions(pagerTitleStrip.mPager.getCurrentItem(), f1, true);
      } 
    }
  }
  
  private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
    private Locale mLocale;
    
    SingleLineAllCapsTransform(Context param1Context) {
      this.mLocale = (param1Context.getResources().getConfiguration()).locale;
    }
    
    public CharSequence getTransformation(CharSequence param1CharSequence, View param1View) {
      param1CharSequence = super.getTransformation(param1CharSequence, param1View);
      if (param1CharSequence != null) {
        param1CharSequence = param1CharSequence.toString().toUpperCase(this.mLocale);
      } else {
        param1CharSequence = null;
      } 
      return param1CharSequence;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\viewpager\widget\PagerTitleStrip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */