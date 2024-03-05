package androidx.core.text;

import android.os.Build;
import android.text.Layout;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.core.os.TraceCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class PrecomputedTextCompat implements Spannable {
  private static final char LINE_FEED = '\n';
  
  private static Executor sExecutor;
  
  private static final Object sLock = new Object();
  
  private final int[] mParagraphEnds;
  
  private final Params mParams;
  
  private final Spannable mText;
  
  private final PrecomputedText mWrapped;
  
  static {
    sExecutor = null;
  }
  
  private PrecomputedTextCompat(PrecomputedText paramPrecomputedText, Params paramParams) {
    this.mText = (Spannable)paramPrecomputedText;
    this.mParams = paramParams;
    this.mParagraphEnds = null;
    if (Build.VERSION.SDK_INT < 29)
      paramPrecomputedText = null; 
    this.mWrapped = paramPrecomputedText;
  }
  
  private PrecomputedTextCompat(CharSequence paramCharSequence, Params paramParams, int[] paramArrayOfint) {
    this.mText = (Spannable)new SpannableString(paramCharSequence);
    this.mParams = paramParams;
    this.mParagraphEnds = paramArrayOfint;
    this.mWrapped = null;
  }
  
  public static PrecomputedTextCompat create(CharSequence paramCharSequence, Params paramParams) {
    Preconditions.checkNotNull(paramCharSequence);
    Preconditions.checkNotNull(paramParams);
    try {
      TraceCompat.beginSection("PrecomputedText");
      if (Build.VERSION.SDK_INT >= 29 && paramParams.mWrapped != null) {
        precomputedTextCompat = new PrecomputedTextCompat(PrecomputedText.create(paramCharSequence, paramParams.mWrapped), paramParams);
        return precomputedTextCompat;
      } 
      ArrayList<Integer> arrayList = new ArrayList();
      this();
      int j = precomputedTextCompat.length();
      int i = 0;
      while (i < j) {
        i = TextUtils.indexOf((CharSequence)precomputedTextCompat, '\n', i, j);
        if (i < 0) {
          i = j;
        } else {
          i++;
        } 
        arrayList.add(Integer.valueOf(i));
      } 
      int[] arrayOfInt = new int[arrayList.size()];
      for (i = 0; i < arrayList.size(); i++)
        arrayOfInt[i] = ((Integer)arrayList.get(i)).intValue(); 
      if (Build.VERSION.SDK_INT >= 23) {
        StaticLayout.Builder.obtain((CharSequence)precomputedTextCompat, 0, precomputedTextCompat.length(), paramParams.getTextPaint(), 2147483647).setBreakStrategy(paramParams.getBreakStrategy()).setHyphenationFrequency(paramParams.getHyphenationFrequency()).setTextDirection(paramParams.getTextDirection()).build();
      } else if (Build.VERSION.SDK_INT >= 21) {
        new StaticLayout((CharSequence)precomputedTextCompat, paramParams.getTextPaint(), 2147483647, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      } 
      PrecomputedTextCompat precomputedTextCompat = new PrecomputedTextCompat((CharSequence)precomputedTextCompat, paramParams, arrayOfInt);
      return precomputedTextCompat;
    } finally {
      TraceCompat.endSection();
    } 
  }
  
  public static Future<PrecomputedTextCompat> getTextFuture(CharSequence paramCharSequence, Params paramParams, Executor paramExecutor) {
    PrecomputedTextFutureTask precomputedTextFutureTask = new PrecomputedTextFutureTask(paramParams, paramCharSequence);
    Executor executor = paramExecutor;
    if (paramExecutor == null)
      synchronized (sLock) {
        if (sExecutor == null)
          sExecutor = Executors.newFixedThreadPool(1); 
        executor = sExecutor;
      }  
    executor.execute(precomputedTextFutureTask);
    return precomputedTextFutureTask;
  }
  
  public char charAt(int paramInt) {
    return this.mText.charAt(paramInt);
  }
  
  public int getParagraphCount() {
    return (Build.VERSION.SDK_INT >= 29) ? this.mWrapped.getParagraphCount() : this.mParagraphEnds.length;
  }
  
  public int getParagraphEnd(int paramInt) {
    Preconditions.checkArgumentInRange(paramInt, 0, getParagraphCount(), "paraIndex");
    return (Build.VERSION.SDK_INT >= 29) ? this.mWrapped.getParagraphEnd(paramInt) : this.mParagraphEnds[paramInt];
  }
  
  public int getParagraphStart(int paramInt) {
    int i = getParagraphCount();
    boolean bool = false;
    Preconditions.checkArgumentInRange(paramInt, 0, i, "paraIndex");
    if (Build.VERSION.SDK_INT >= 29)
      return this.mWrapped.getParagraphStart(paramInt); 
    if (paramInt == 0) {
      paramInt = bool;
    } else {
      paramInt = this.mParagraphEnds[paramInt - 1];
    } 
    return paramInt;
  }
  
  public Params getParams() {
    return this.mParams;
  }
  
  public PrecomputedText getPrecomputedText() {
    Spannable spannable = this.mText;
    return (spannable instanceof PrecomputedText) ? (PrecomputedText)spannable : null;
  }
  
  public int getSpanEnd(Object paramObject) {
    return this.mText.getSpanEnd(paramObject);
  }
  
  public int getSpanFlags(Object paramObject) {
    return this.mText.getSpanFlags(paramObject);
  }
  
  public int getSpanStart(Object paramObject) {
    return this.mText.getSpanStart(paramObject);
  }
  
  public <T> T[] getSpans(int paramInt1, int paramInt2, Class<T> paramClass) {
    return (T[])((Build.VERSION.SDK_INT >= 29) ? this.mWrapped.getSpans(paramInt1, paramInt2, paramClass) : this.mText.getSpans(paramInt1, paramInt2, paramClass));
  }
  
  public int length() {
    return this.mText.length();
  }
  
  public int nextSpanTransition(int paramInt1, int paramInt2, Class paramClass) {
    return this.mText.nextSpanTransition(paramInt1, paramInt2, paramClass);
  }
  
  public void removeSpan(Object paramObject) {
    if (!(paramObject instanceof android.text.style.MetricAffectingSpan)) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mWrapped.removeSpan(paramObject);
      } else {
        this.mText.removeSpan(paramObject);
      } 
      return;
    } 
    throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
  }
  
  public void setSpan(Object paramObject, int paramInt1, int paramInt2, int paramInt3) {
    if (!(paramObject instanceof android.text.style.MetricAffectingSpan)) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mWrapped.setSpan(paramObject, paramInt1, paramInt2, paramInt3);
      } else {
        this.mText.setSpan(paramObject, paramInt1, paramInt2, paramInt3);
      } 
      return;
    } 
    throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
  }
  
  public CharSequence subSequence(int paramInt1, int paramInt2) {
    return this.mText.subSequence(paramInt1, paramInt2);
  }
  
  public String toString() {
    return this.mText.toString();
  }
  
  public static final class Params {
    private final int mBreakStrategy;
    
    private final int mHyphenationFrequency;
    
    private final TextPaint mPaint;
    
    private final TextDirectionHeuristic mTextDir;
    
    final PrecomputedText.Params mWrapped;
    
    public Params(PrecomputedText.Params param1Params) {
      this.mPaint = param1Params.getTextPaint();
      this.mTextDir = param1Params.getTextDirection();
      this.mBreakStrategy = param1Params.getBreakStrategy();
      this.mHyphenationFrequency = param1Params.getHyphenationFrequency();
      if (Build.VERSION.SDK_INT < 29)
        param1Params = null; 
      this.mWrapped = param1Params;
    }
    
    Params(TextPaint param1TextPaint, TextDirectionHeuristic param1TextDirectionHeuristic, int param1Int1, int param1Int2) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mWrapped = (new PrecomputedText.Params.Builder(param1TextPaint)).setBreakStrategy(param1Int1).setHyphenationFrequency(param1Int2).setTextDirection(param1TextDirectionHeuristic).build();
      } else {
        this.mWrapped = null;
      } 
      this.mPaint = param1TextPaint;
      this.mTextDir = param1TextDirectionHeuristic;
      this.mBreakStrategy = param1Int1;
      this.mHyphenationFrequency = param1Int2;
    }
    
    public boolean equals(Object param1Object) {
      if (param1Object == this)
        return true; 
      if (!(param1Object instanceof Params))
        return false; 
      param1Object = param1Object;
      return !equalsWithoutTextDirection((Params)param1Object) ? false : (!(Build.VERSION.SDK_INT >= 18 && this.mTextDir != param1Object.getTextDirection()));
    }
    
    public boolean equalsWithoutTextDirection(Params param1Params) {
      if (Build.VERSION.SDK_INT >= 23) {
        if (this.mBreakStrategy != param1Params.getBreakStrategy())
          return false; 
        if (this.mHyphenationFrequency != param1Params.getHyphenationFrequency())
          return false; 
      } 
      if (this.mPaint.getTextSize() != param1Params.getTextPaint().getTextSize())
        return false; 
      if (this.mPaint.getTextScaleX() != param1Params.getTextPaint().getTextScaleX())
        return false; 
      if (this.mPaint.getTextSkewX() != param1Params.getTextPaint().getTextSkewX())
        return false; 
      if (Build.VERSION.SDK_INT >= 21) {
        if (this.mPaint.getLetterSpacing() != param1Params.getTextPaint().getLetterSpacing())
          return false; 
        if (!TextUtils.equals(this.mPaint.getFontFeatureSettings(), param1Params.getTextPaint().getFontFeatureSettings()))
          return false; 
      } 
      if (this.mPaint.getFlags() != param1Params.getTextPaint().getFlags())
        return false; 
      if (Build.VERSION.SDK_INT >= 24) {
        if (!this.mPaint.getTextLocales().equals(param1Params.getTextPaint().getTextLocales()))
          return false; 
      } else if (Build.VERSION.SDK_INT >= 17 && !this.mPaint.getTextLocale().equals(param1Params.getTextPaint().getTextLocale())) {
        return false;
      } 
      if (this.mPaint.getTypeface() == null) {
        if (param1Params.getTextPaint().getTypeface() != null)
          return false; 
      } else if (!this.mPaint.getTypeface().equals(param1Params.getTextPaint().getTypeface())) {
        return false;
      } 
      return true;
    }
    
    public int getBreakStrategy() {
      return this.mBreakStrategy;
    }
    
    public int getHyphenationFrequency() {
      return this.mHyphenationFrequency;
    }
    
    public TextDirectionHeuristic getTextDirection() {
      return this.mTextDir;
    }
    
    public TextPaint getTextPaint() {
      return this.mPaint;
    }
    
    public int hashCode() {
      return (Build.VERSION.SDK_INT >= 24) ? ObjectsCompat.hash(new Object[] { 
            Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocales(), this.mPaint.getTypeface(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), 
            Integer.valueOf(this.mHyphenationFrequency) }) : ((Build.VERSION.SDK_INT >= 21) ? ObjectsCompat.hash(new Object[] { 
            Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocale(), this.mPaint.getTypeface(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), 
            Integer.valueOf(this.mHyphenationFrequency) }) : ((Build.VERSION.SDK_INT >= 18) ? ObjectsCompat.hash(new Object[] { Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocale(), this.mPaint.getTypeface(), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency) }) : ((Build.VERSION.SDK_INT >= 17) ? ObjectsCompat.hash(new Object[] { Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocale(), this.mPaint.getTypeface(), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency) }) : ObjectsCompat.hash(new Object[] { Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTypeface(), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency) }))));
    }
    
    public String toString() {
      StringBuilder stringBuilder1 = new StringBuilder("{");
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("textSize=");
      stringBuilder2.append(this.mPaint.getTextSize());
      stringBuilder1.append(stringBuilder2.toString());
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(", textScaleX=");
      stringBuilder2.append(this.mPaint.getTextScaleX());
      stringBuilder1.append(stringBuilder2.toString());
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(", textSkewX=");
      stringBuilder2.append(this.mPaint.getTextSkewX());
      stringBuilder1.append(stringBuilder2.toString());
      if (Build.VERSION.SDK_INT >= 21) {
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", letterSpacing=");
        stringBuilder2.append(this.mPaint.getLetterSpacing());
        stringBuilder1.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", elegantTextHeight=");
        stringBuilder2.append(this.mPaint.isElegantTextHeight());
        stringBuilder1.append(stringBuilder2.toString());
      } 
      if (Build.VERSION.SDK_INT >= 24) {
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", textLocale=");
        stringBuilder2.append(this.mPaint.getTextLocales());
        stringBuilder1.append(stringBuilder2.toString());
      } else if (Build.VERSION.SDK_INT >= 17) {
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", textLocale=");
        stringBuilder2.append(this.mPaint.getTextLocale());
        stringBuilder1.append(stringBuilder2.toString());
      } 
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(", typeface=");
      stringBuilder2.append(this.mPaint.getTypeface());
      stringBuilder1.append(stringBuilder2.toString());
      if (Build.VERSION.SDK_INT >= 26) {
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", variationSettings=");
        stringBuilder2.append(this.mPaint.getFontVariationSettings());
        stringBuilder1.append(stringBuilder2.toString());
      } 
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(", textDir=");
      stringBuilder2.append(this.mTextDir);
      stringBuilder1.append(stringBuilder2.toString());
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(", breakStrategy=");
      stringBuilder2.append(this.mBreakStrategy);
      stringBuilder1.append(stringBuilder2.toString());
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(", hyphenationFrequency=");
      stringBuilder2.append(this.mHyphenationFrequency);
      stringBuilder1.append(stringBuilder2.toString());
      stringBuilder1.append("}");
      return stringBuilder1.toString();
    }
    
    public static class Builder {
      private int mBreakStrategy;
      
      private int mHyphenationFrequency;
      
      private final TextPaint mPaint;
      
      private TextDirectionHeuristic mTextDir;
      
      public Builder(TextPaint param2TextPaint) {
        this.mPaint = param2TextPaint;
        if (Build.VERSION.SDK_INT >= 23) {
          this.mBreakStrategy = 1;
          this.mHyphenationFrequency = 1;
        } else {
          this.mHyphenationFrequency = 0;
          this.mBreakStrategy = 0;
        } 
        if (Build.VERSION.SDK_INT >= 18) {
          this.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
        } else {
          this.mTextDir = null;
        } 
      }
      
      public PrecomputedTextCompat.Params build() {
        return new PrecomputedTextCompat.Params(this.mPaint, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
      }
      
      public Builder setBreakStrategy(int param2Int) {
        this.mBreakStrategy = param2Int;
        return this;
      }
      
      public Builder setHyphenationFrequency(int param2Int) {
        this.mHyphenationFrequency = param2Int;
        return this;
      }
      
      public Builder setTextDirection(TextDirectionHeuristic param2TextDirectionHeuristic) {
        this.mTextDir = param2TextDirectionHeuristic;
        return this;
      }
    }
  }
  
  public static class Builder {
    private int mBreakStrategy;
    
    private int mHyphenationFrequency;
    
    private final TextPaint mPaint;
    
    private TextDirectionHeuristic mTextDir;
    
    public Builder(TextPaint param1TextPaint) {
      this.mPaint = param1TextPaint;
      if (Build.VERSION.SDK_INT >= 23) {
        this.mBreakStrategy = 1;
        this.mHyphenationFrequency = 1;
      } else {
        this.mHyphenationFrequency = 0;
        this.mBreakStrategy = 0;
      } 
      if (Build.VERSION.SDK_INT >= 18) {
        this.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
      } else {
        this.mTextDir = null;
      } 
    }
    
    public PrecomputedTextCompat.Params build() {
      return new PrecomputedTextCompat.Params(this.mPaint, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
    }
    
    public Builder setBreakStrategy(int param1Int) {
      this.mBreakStrategy = param1Int;
      return this;
    }
    
    public Builder setHyphenationFrequency(int param1Int) {
      this.mHyphenationFrequency = param1Int;
      return this;
    }
    
    public Builder setTextDirection(TextDirectionHeuristic param1TextDirectionHeuristic) {
      this.mTextDir = param1TextDirectionHeuristic;
      return this;
    }
  }
  
  private static class PrecomputedTextFutureTask extends FutureTask<PrecomputedTextCompat> {
    PrecomputedTextFutureTask(PrecomputedTextCompat.Params param1Params, CharSequence param1CharSequence) {
      super(new PrecomputedTextCallback(param1Params, param1CharSequence));
    }
    
    private static class PrecomputedTextCallback implements Callable<PrecomputedTextCompat> {
      private PrecomputedTextCompat.Params mParams;
      
      private CharSequence mText;
      
      PrecomputedTextCallback(PrecomputedTextCompat.Params param2Params, CharSequence param2CharSequence) {
        this.mParams = param2Params;
        this.mText = param2CharSequence;
      }
      
      public PrecomputedTextCompat call() throws Exception {
        return PrecomputedTextCompat.create(this.mText, this.mParams);
      }
    }
  }
  
  private static class PrecomputedTextCallback implements Callable<PrecomputedTextCompat> {
    private PrecomputedTextCompat.Params mParams;
    
    private CharSequence mText;
    
    PrecomputedTextCallback(PrecomputedTextCompat.Params param1Params, CharSequence param1CharSequence) {
      this.mParams = param1Params;
      this.mText = param1CharSequence;
    }
    
    public PrecomputedTextCompat call() throws Exception {
      return PrecomputedTextCompat.create(this.mText, this.mParams);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\text\PrecomputedTextCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */