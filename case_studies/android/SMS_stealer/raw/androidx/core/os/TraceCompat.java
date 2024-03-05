package androidx.core.os;

import android.os.Build;
import android.os.Trace;
import android.util.Log;
import java.lang.reflect.Method;

public final class TraceCompat {
  private static final String TAG = "TraceCompat";
  
  private static Method sAsyncTraceBeginMethod;
  
  private static Method sAsyncTraceEndMethod;
  
  private static Method sIsTagEnabledMethod;
  
  private static Method sTraceCounterMethod;
  
  private static long sTraceTagApp;
  
  static {
    if (Build.VERSION.SDK_INT >= 18 && Build.VERSION.SDK_INT < 29)
      try {
        sTraceTagApp = Trace.class.getField("TRACE_TAG_APP").getLong(null);
        sIsTagEnabledMethod = Trace.class.getMethod("isTagEnabled", new Class[] { long.class });
        sAsyncTraceBeginMethod = Trace.class.getMethod("asyncTraceBegin", new Class[] { long.class, String.class, int.class });
        sAsyncTraceEndMethod = Trace.class.getMethod("asyncTraceEnd", new Class[] { long.class, String.class, int.class });
        sTraceCounterMethod = Trace.class.getMethod("traceCounter", new Class[] { long.class, String.class, int.class });
      } catch (Exception exception) {
        Log.i("TraceCompat", "Unable to initialize via reflection.", exception);
      }  
  }
  
  public static void beginAsyncSection(String paramString, int paramInt) {
    if (Build.VERSION.SDK_INT >= 29) {
      Trace.beginAsyncSection(paramString, paramInt);
    } else if (Build.VERSION.SDK_INT >= 18) {
      try {
        sAsyncTraceBeginMethod.invoke(null, new Object[] { Long.valueOf(sTraceTagApp), paramString, Integer.valueOf(paramInt) });
      } catch (Exception exception) {
        Log.v("TraceCompat", "Unable to invoke asyncTraceBegin() via reflection.");
      } 
    } 
  }
  
  public static void beginSection(String paramString) {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.beginSection(paramString); 
  }
  
  public static void endAsyncSection(String paramString, int paramInt) {
    if (Build.VERSION.SDK_INT >= 29) {
      Trace.endAsyncSection(paramString, paramInt);
    } else if (Build.VERSION.SDK_INT >= 18) {
      try {
        sAsyncTraceEndMethod.invoke(null, new Object[] { Long.valueOf(sTraceTagApp), paramString, Integer.valueOf(paramInt) });
      } catch (Exception exception) {
        Log.v("TraceCompat", "Unable to invoke endAsyncSection() via reflection.");
      } 
    } 
  }
  
  public static void endSection() {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.endSection(); 
  }
  
  public static boolean isEnabled() {
    if (Build.VERSION.SDK_INT >= 29)
      return Trace.isEnabled(); 
    if (Build.VERSION.SDK_INT >= 18)
      try {
        return ((Boolean)sIsTagEnabledMethod.invoke(null, new Object[] { Long.valueOf(sTraceTagApp) })).booleanValue();
      } catch (Exception exception) {
        Log.v("TraceCompat", "Unable to invoke isTagEnabled() via reflection.");
      }  
    return false;
  }
  
  public static void setCounter(String paramString, int paramInt) {
    if (Build.VERSION.SDK_INT >= 29) {
      Trace.setCounter(paramString, paramInt);
    } else if (Build.VERSION.SDK_INT >= 18) {
      try {
        sTraceCounterMethod.invoke(null, new Object[] { Long.valueOf(sTraceTagApp), paramString, Integer.valueOf(paramInt) });
      } catch (Exception exception) {
        Log.v("TraceCompat", "Unable to invoke traceCounter() via reflection.");
      } 
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\os\TraceCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */