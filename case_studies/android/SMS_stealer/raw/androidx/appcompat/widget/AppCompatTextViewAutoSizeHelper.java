package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

class AppCompatTextViewAutoSizeHelper {
  private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
  
  private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
  
  private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
  
  private static final String TAG = "ACTVAutoSizeHelper";
  
  private static final RectF TEMP_RECTF = new RectF();
  
  static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0F;
  
  private static final int VERY_WIDE = 1048576;
  
  private static ConcurrentHashMap<String, Field> sTextViewFieldByNameCache;
  
  private static ConcurrentHashMap<String, Method> sTextViewMethodByNameCache = new ConcurrentHashMap<String, Method>();
  
  private float mAutoSizeMaxTextSizeInPx = -1.0F;
  
  private float mAutoSizeMinTextSizeInPx = -1.0F;
  
  private float mAutoSizeStepGranularityInPx = -1.0F;
  
  private int[] mAutoSizeTextSizesInPx = new int[0];
  
  private int mAutoSizeTextType = 0;
  
  private final Context mContext;
  
  private boolean mHasPresetAutoSizeValues = false;
  
  private final Impl mImpl;
  
  private boolean mNeedsAutoSizeText = false;
  
  private TextPaint mTempTextPaint;
  
  private final TextView mTextView;
  
  static {
    sTextViewFieldByNameCache = new ConcurrentHashMap<String, Field>();
  }
  
  AppCompatTextViewAutoSizeHelper(TextView paramTextView) {
    this.mTextView = paramTextView;
    this.mContext = paramTextView.getContext();
    if (Build.VERSION.SDK_INT >= 29) {
      this.mImpl = new Impl29();
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new Impl23();
    } else {
      this.mImpl = new Impl();
    } 
  }
  
  private static <T> T accessAndReturnWithDefault(Object paramObject, String paramString, T paramT) {
    try {
      Field field = getTextViewField(paramString);
      return (T)((field == null) ? (Object)paramT : field.get(paramObject));
    } catch (IllegalAccessException illegalAccessException) {
      paramObject = new StringBuilder();
      paramObject.append("Failed to access TextView#");
      paramObject.append(paramString);
      paramObject.append(" member");
      Log.w("ACTVAutoSizeHelper", paramObject.toString(), illegalAccessException);
      return paramT;
    } 
  }
  
  private int[] cleanupAutoSizePresetSizes(int[] paramArrayOfint) {
    int i = paramArrayOfint.length;
    if (i == 0)
      return paramArrayOfint; 
    Arrays.sort(paramArrayOfint);
    ArrayList<? extends Comparable<? super Integer>> arrayList = new ArrayList();
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      int j = paramArrayOfint[b];
      if (j > 0 && Collections.binarySearch(arrayList, Integer.valueOf(j)) < 0)
        arrayList.add(Integer.valueOf(j)); 
    } 
    if (i == arrayList.size())
      return paramArrayOfint; 
    i = arrayList.size();
    paramArrayOfint = new int[i];
    for (b = bool; b < i; b++)
      paramArrayOfint[b] = ((Integer)arrayList.get(b)).intValue(); 
    return paramArrayOfint;
  }
  
  private void clearAutoSizeConfiguration() {
    this.mAutoSizeTextType = 0;
    this.mAutoSizeMinTextSizeInPx = -1.0F;
    this.mAutoSizeMaxTextSizeInPx = -1.0F;
    this.mAutoSizeStepGranularityInPx = -1.0F;
    this.mAutoSizeTextSizesInPx = new int[0];
    this.mNeedsAutoSizeText = false;
  }
  
  private StaticLayout createStaticLayoutForMeasuring(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt1, int paramInt2) {
    StaticLayout.Builder builder1 = StaticLayout.Builder.obtain(paramCharSequence, 0, paramCharSequence.length(), this.mTempTextPaint, paramInt1);
    StaticLayout.Builder builder2 = builder1.setAlignment(paramAlignment).setLineSpacing(this.mTextView.getLineSpacingExtra(), this.mTextView.getLineSpacingMultiplier()).setIncludePad(this.mTextView.getIncludeFontPadding()).setBreakStrategy(this.mTextView.getBreakStrategy()).setHyphenationFrequency(this.mTextView.getHyphenationFrequency());
    paramInt1 = paramInt2;
    if (paramInt2 == -1)
      paramInt1 = Integer.MAX_VALUE; 
    builder2.setMaxLines(paramInt1);
    try {
      this.mImpl.computeAndSetTextDirection(builder1, this.mTextView);
    } catch (ClassCastException classCastException) {
      Log.w("ACTVAutoSizeHelper", "Failed to obtain TextDirectionHeuristic, auto size may be incorrect");
    } 
    return builder1.build();
  }
  
  private StaticLayout createStaticLayoutForMeasuringPre16(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt) {
    float f1 = ((Float)accessAndReturnWithDefault(this.mTextView, "mSpacingMult", Float.valueOf(1.0F))).floatValue();
    float f2 = ((Float)accessAndReturnWithDefault(this.mTextView, "mSpacingAdd", Float.valueOf(0.0F))).floatValue();
    boolean bool = ((Boolean)accessAndReturnWithDefault(this.mTextView, "mIncludePad", Boolean.valueOf(true))).booleanValue();
    return new StaticLayout(paramCharSequence, this.mTempTextPaint, paramInt, paramAlignment, f1, f2, bool);
  }
  
  private StaticLayout createStaticLayoutForMeasuringPre23(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt) {
    float f2 = this.mTextView.getLineSpacingMultiplier();
    float f1 = this.mTextView.getLineSpacingExtra();
    boolean bool = this.mTextView.getIncludeFontPadding();
    return new StaticLayout(paramCharSequence, this.mTempTextPaint, paramInt, paramAlignment, f2, f1, bool);
  }
  
  private int findLargestTextSizeWhichFits(RectF paramRectF) {
    int i = this.mAutoSizeTextSizesInPx.length;
    if (i != 0) {
      int k = i - 1;
      i = 1;
      int j = 0;
      while (i <= k) {
        int m = (i + k) / 2;
        if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[m], paramRectF)) {
          j = i;
          i = m + 1;
          continue;
        } 
        j = m - 1;
        k = j;
      } 
      return this.mAutoSizeTextSizesInPx[j];
    } 
    throw new IllegalStateException("No available text sizes to choose from.");
  }
  
  private static Field getTextViewField(String paramString) {
    try {
      Field field2 = sTextViewFieldByNameCache.get(paramString);
      Field field1 = field2;
      if (field2 == null) {
        field2 = TextView.class.getDeclaredField(paramString);
        field1 = field2;
        if (field2 != null) {
          field2.setAccessible(true);
          sTextViewFieldByNameCache.put(paramString, field2);
          field1 = field2;
        } 
      } 
      return field1;
    } catch (NoSuchFieldException noSuchFieldException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to access TextView#");
      stringBuilder.append(paramString);
      stringBuilder.append(" member");
      Log.w("ACTVAutoSizeHelper", stringBuilder.toString(), noSuchFieldException);
      return null;
    } 
  }
  
  private static Method getTextViewMethod(String paramString) {
    try {
      Method method2 = sTextViewMethodByNameCache.get(paramString);
      Method method1 = method2;
      if (method2 == null) {
        method2 = TextView.class.getDeclaredMethod(paramString, new Class[0]);
        method1 = method2;
        if (method2 != null) {
          method2.setAccessible(true);
          sTextViewMethodByNameCache.put(paramString, method2);
          method1 = method2;
        } 
      } 
      return method1;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to retrieve TextView#");
      stringBuilder.append(paramString);
      stringBuilder.append("() method");
      Log.w("ACTVAutoSizeHelper", stringBuilder.toString(), exception);
      return null;
    } 
  }
  
  static <T> T invokeAndReturnWithDefault(Object paramObject, String paramString, T paramT) {
    try {
      paramObject = getTextViewMethod(paramString).invoke(paramObject, new Object[0]);
      paramT = (T)paramObject;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Failed to invoke TextView#");
      stringBuilder.append(paramString);
      stringBuilder.append("() method");
      Log.w("ACTVAutoSizeHelper", stringBuilder.toString(), exception);
    } finally {}
    return paramT;
  }
  
  private void setRawTextSize(float paramFloat) {
    if (paramFloat != this.mTextView.getPaint().getTextSize()) {
      boolean bool;
      this.mTextView.getPaint().setTextSize(paramFloat);
      if (Build.VERSION.SDK_INT >= 18) {
        bool = this.mTextView.isInLayout();
      } else {
        bool = false;
      } 
      if (this.mTextView.getLayout() != null) {
        this.mNeedsAutoSizeText = false;
        try {
          Method method = getTextViewMethod("nullLayouts");
          if (method != null)
            method.invoke(this.mTextView, new Object[0]); 
        } catch (Exception exception) {
          Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#nullLayouts() method", exception);
        } 
        if (!bool) {
          this.mTextView.requestLayout();
        } else {
          this.mTextView.forceLayout();
        } 
        this.mTextView.invalidate();
      } 
    } 
  }
  
  private boolean setupAutoSizeText() {
    boolean bool = supportsAutoSizeText();
    byte b = 0;
    if (bool && this.mAutoSizeTextType == 1) {
      if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
        int i = (int)Math.floor(((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx)) + 1;
        int[] arrayOfInt = new int[i];
        while (b < i) {
          arrayOfInt[b] = Math.round(this.mAutoSizeMinTextSizeInPx + b * this.mAutoSizeStepGranularityInPx);
          b++;
        } 
        this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt);
      } 
      this.mNeedsAutoSizeText = true;
    } else {
      this.mNeedsAutoSizeText = false;
    } 
    return this.mNeedsAutoSizeText;
  }
  
  private void setupAutoSizeUniformPresetSizes(TypedArray paramTypedArray) {
    int i = paramTypedArray.length();
    int[] arrayOfInt = new int[i];
    if (i > 0) {
      for (byte b = 0; b < i; b++)
        arrayOfInt[b] = paramTypedArray.getDimensionPixelSize(b, -1); 
      this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt);
      setupAutoSizeUniformPresetSizesConfiguration();
    } 
  }
  
  private boolean setupAutoSizeUniformPresetSizesConfiguration() {
    boolean bool;
    int i = this.mAutoSizeTextSizesInPx.length;
    if (i > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mHasPresetAutoSizeValues = bool;
    if (bool) {
      this.mAutoSizeTextType = 1;
      int[] arrayOfInt = this.mAutoSizeTextSizesInPx;
      this.mAutoSizeMinTextSizeInPx = arrayOfInt[0];
      this.mAutoSizeMaxTextSizeInPx = arrayOfInt[i - 1];
      this.mAutoSizeStepGranularityInPx = -1.0F;
    } 
    return this.mHasPresetAutoSizeValues;
  }
  
  private boolean suggestedSizeFitsInSpace(int paramInt, RectF paramRectF) {
    byte b;
    CharSequence charSequence2 = this.mTextView.getText();
    TransformationMethod transformationMethod = this.mTextView.getTransformationMethod();
    CharSequence charSequence1 = charSequence2;
    if (transformationMethod != null) {
      CharSequence charSequence = transformationMethod.getTransformation(charSequence2, (View)this.mTextView);
      charSequence1 = charSequence2;
      if (charSequence != null)
        charSequence1 = charSequence; 
    } 
    if (Build.VERSION.SDK_INT >= 16) {
      b = this.mTextView.getMaxLines();
    } else {
      b = -1;
    } 
    initTempTextPaint(paramInt);
    StaticLayout staticLayout = createLayout(charSequence1, invokeAndReturnWithDefault(this.mTextView, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL), Math.round(paramRectF.right), b);
    return (b != -1 && (staticLayout.getLineCount() > b || staticLayout.getLineEnd(staticLayout.getLineCount() - 1) != charSequence1.length())) ? false : (!(staticLayout.getHeight() > paramRectF.bottom));
  }
  
  private boolean supportsAutoSizeText() {
    return this.mTextView instanceof AppCompatEditText ^ true;
  }
  
  private void validateAndSetAutoSizeTextTypeUniformConfiguration(float paramFloat1, float paramFloat2, float paramFloat3) throws IllegalArgumentException {
    if (paramFloat1 > 0.0F) {
      if (paramFloat2 > paramFloat1) {
        if (paramFloat3 > 0.0F) {
          this.mAutoSizeTextType = 1;
          this.mAutoSizeMinTextSizeInPx = paramFloat1;
          this.mAutoSizeMaxTextSizeInPx = paramFloat2;
          this.mAutoSizeStepGranularityInPx = paramFloat3;
          this.mHasPresetAutoSizeValues = false;
          return;
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("The auto-size step granularity (");
        stringBuilder2.append(paramFloat3);
        stringBuilder2.append("px) is less or equal to (0px)");
        throw new IllegalArgumentException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Maximum auto-size text size (");
      stringBuilder1.append(paramFloat2);
      stringBuilder1.append("px) is less or equal to minimum auto-size text size (");
      stringBuilder1.append(paramFloat1);
      stringBuilder1.append("px)");
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Minimum auto-size text size (");
    stringBuilder.append(paramFloat1);
    stringBuilder.append("px) is less or equal to (0px)");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  void autoSizeText() {
    if (!isAutoSizeEnabled())
      return; 
    if (this.mNeedsAutoSizeText) {
      int i;
      if (this.mTextView.getMeasuredHeight() <= 0 || this.mTextView.getMeasuredWidth() <= 0)
        return; 
      if (this.mImpl.isHorizontallyScrollable(this.mTextView)) {
        i = 1048576;
      } else {
        i = this.mTextView.getMeasuredWidth() - this.mTextView.getTotalPaddingLeft() - this.mTextView.getTotalPaddingRight();
      } 
      int j = this.mTextView.getHeight() - this.mTextView.getCompoundPaddingBottom() - this.mTextView.getCompoundPaddingTop();
      if (i <= 0 || j <= 0)
        return; 
      synchronized (TEMP_RECTF) {
        TEMP_RECTF.setEmpty();
        TEMP_RECTF.right = i;
        TEMP_RECTF.bottom = j;
        float f = findLargestTextSizeWhichFits(TEMP_RECTF);
        if (f != this.mTextView.getTextSize())
          setTextSizeInternal(0, f); 
      } 
    } 
    this.mNeedsAutoSizeText = true;
  }
  
  StaticLayout createLayout(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt1, int paramInt2) {
    return (Build.VERSION.SDK_INT >= 23) ? createStaticLayoutForMeasuring(paramCharSequence, paramAlignment, paramInt1, paramInt2) : ((Build.VERSION.SDK_INT >= 16) ? createStaticLayoutForMeasuringPre23(paramCharSequence, paramAlignment, paramInt1) : createStaticLayoutForMeasuringPre16(paramCharSequence, paramAlignment, paramInt1));
  }
  
  int getAutoSizeMaxTextSize() {
    return Math.round(this.mAutoSizeMaxTextSizeInPx);
  }
  
  int getAutoSizeMinTextSize() {
    return Math.round(this.mAutoSizeMinTextSizeInPx);
  }
  
  int getAutoSizeStepGranularity() {
    return Math.round(this.mAutoSizeStepGranularityInPx);
  }
  
  int[] getAutoSizeTextAvailableSizes() {
    return this.mAutoSizeTextSizesInPx;
  }
  
  int getAutoSizeTextType() {
    return this.mAutoSizeTextType;
  }
  
  void initTempTextPaint(int paramInt) {
    TextPaint textPaint = this.mTempTextPaint;
    if (textPaint == null) {
      this.mTempTextPaint = new TextPaint();
    } else {
      textPaint.reset();
    } 
    this.mTempTextPaint.set(this.mTextView.getPaint());
    this.mTempTextPaint.setTextSize(paramInt);
  }
  
  boolean isAutoSizeEnabled() {
    boolean bool;
    if (supportsAutoSizeText() && this.mAutoSizeTextType != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    float f1;
    float f2;
    float f3;
    TypedArray typedArray = this.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppCompatTextView, paramInt, 0);
    TextView textView = this.mTextView;
    ViewCompat.saveAttributeDataForStyleable((View)textView, textView.getContext(), R.styleable.AppCompatTextView, paramAttributeSet, typedArray, paramInt, 0);
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeTextType))
      this.mAutoSizeTextType = typedArray.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0); 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity)) {
      f1 = typedArray.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0F);
    } else {
      f1 = -1.0F;
    } 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize)) {
      f3 = typedArray.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0F);
    } else {
      f3 = -1.0F;
    } 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize)) {
      f2 = typedArray.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0F);
    } else {
      f2 = -1.0F;
    } 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes)) {
      paramInt = typedArray.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0);
      if (paramInt > 0) {
        TypedArray typedArray1 = typedArray.getResources().obtainTypedArray(paramInt);
        setupAutoSizeUniformPresetSizes(typedArray1);
        typedArray1.recycle();
      } 
    } 
    typedArray.recycle();
    if (supportsAutoSizeText()) {
      if (this.mAutoSizeTextType == 1) {
        if (!this.mHasPresetAutoSizeValues) {
          DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
          float f = f3;
          if (f3 == -1.0F)
            f = TypedValue.applyDimension(2, 12.0F, displayMetrics); 
          f3 = f2;
          if (f2 == -1.0F)
            f3 = TypedValue.applyDimension(2, 112.0F, displayMetrics); 
          f2 = f1;
          if (f1 == -1.0F)
            f2 = 1.0F; 
          validateAndSetAutoSizeTextTypeUniformConfiguration(f, f3, f2);
        } 
        setupAutoSizeText();
      } 
    } else {
      this.mAutoSizeTextType = 0;
    } 
  }
  
  void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
    if (supportsAutoSizeText()) {
      DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
      validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(paramInt4, paramInt1, displayMetrics), TypedValue.applyDimension(paramInt4, paramInt2, displayMetrics), TypedValue.applyDimension(paramInt4, paramInt3, displayMetrics));
      if (setupAutoSizeText())
        autoSizeText(); 
    } 
  }
  
  void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfint, int paramInt) throws IllegalArgumentException {
    if (supportsAutoSizeText()) {
      int i = paramArrayOfint.length;
      byte b = 0;
      if (i > 0) {
        int[] arrayOfInt1;
        int[] arrayOfInt2 = new int[i];
        if (paramInt == 0) {
          arrayOfInt1 = Arrays.copyOf(paramArrayOfint, i);
        } else {
          DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
          while (true) {
            arrayOfInt1 = arrayOfInt2;
            if (b < i) {
              arrayOfInt2[b] = Math.round(TypedValue.applyDimension(paramInt, paramArrayOfint[b], displayMetrics));
              b++;
              continue;
            } 
            break;
          } 
        } 
        this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt1);
        if (!setupAutoSizeUniformPresetSizesConfiguration()) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("None of the preset sizes is valid: ");
          stringBuilder.append(Arrays.toString(paramArrayOfint));
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        this.mHasPresetAutoSizeValues = false;
      } 
      if (setupAutoSizeText())
        autoSizeText(); 
    } 
  }
  
  void setAutoSizeTextTypeWithDefaults(int paramInt) {
    if (supportsAutoSizeText())
      if (paramInt != 0) {
        if (paramInt == 1) {
          DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
          validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0F, displayMetrics), TypedValue.applyDimension(2, 112.0F, displayMetrics), 1.0F);
          if (setupAutoSizeText())
            autoSizeText(); 
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown auto-size text type: ");
          stringBuilder.append(paramInt);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        clearAutoSizeConfiguration();
      }  
  }
  
  void setTextSizeInternal(int paramInt, float paramFloat) {
    Resources resources;
    Context context = this.mContext;
    if (context == null) {
      resources = Resources.getSystem();
    } else {
      resources = resources.getResources();
    } 
    setRawTextSize(TypedValue.applyDimension(paramInt, paramFloat, resources.getDisplayMetrics()));
  }
  
  private static class Impl {
    void computeAndSetTextDirection(StaticLayout.Builder param1Builder, TextView param1TextView) {}
    
    boolean isHorizontallyScrollable(TextView param1TextView) {
      return ((Boolean)AppCompatTextViewAutoSizeHelper.<Boolean>invokeAndReturnWithDefault(param1TextView, "getHorizontallyScrolling", Boolean.valueOf(false))).booleanValue();
    }
  }
  
  private static class Impl23 extends Impl {
    void computeAndSetTextDirection(StaticLayout.Builder param1Builder, TextView param1TextView) {
      param1Builder.setTextDirection(AppCompatTextViewAutoSizeHelper.<TextDirectionHeuristic>invokeAndReturnWithDefault(param1TextView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR));
    }
  }
  
  private static class Impl29 extends Impl23 {
    void computeAndSetTextDirection(StaticLayout.Builder param1Builder, TextView param1TextView) {
      param1Builder.setTextDirection(param1TextView.getTextDirectionHeuristic());
    }
    
    boolean isHorizontallyScrollable(TextView param1TextView) {
      return param1TextView.isHorizontallyScrollable();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\AppCompatTextViewAutoSizeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */