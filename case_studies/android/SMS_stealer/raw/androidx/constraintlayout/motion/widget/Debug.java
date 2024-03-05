package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Debug {
  public static void dumpLayoutParams(ViewGroup.LayoutParams paramLayoutParams, String paramString) {
    StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(".(");
    stringBuilder.append(stackTraceElement.getFileName());
    stringBuilder.append(":");
    stringBuilder.append(stackTraceElement.getLineNumber());
    stringBuilder.append(") ");
    stringBuilder.append(paramString);
    stringBuilder.append("  ");
    paramString = stringBuilder.toString();
    PrintStream printStream = System.out;
    stringBuilder = new StringBuilder();
    stringBuilder.append(" >>>>>>>>>>>>>>>>>>. dump ");
    stringBuilder.append(paramString);
    stringBuilder.append("  ");
    stringBuilder.append(paramLayoutParams.getClass().getName());
    printStream.println(stringBuilder.toString());
    Field[] arrayOfField = paramLayoutParams.getClass().getFields();
    byte b = 0;
    while (true) {
      if (b < arrayOfField.length) {
        Field field = arrayOfField[b];
        try {
          Object object = field.get(paramLayoutParams);
          String str = field.getName();
          if (str.contains("To") && !object.toString().equals("-1")) {
            PrintStream printStream2 = System.out;
            StringBuilder stringBuilder2 = new StringBuilder();
            this();
            stringBuilder2.append(paramString);
            stringBuilder2.append("       ");
            stringBuilder2.append(str);
            stringBuilder2.append(" ");
            stringBuilder2.append(object);
            printStream2.println(stringBuilder2.toString());
          } 
        } catch (IllegalAccessException illegalAccessException) {}
        b++;
        continue;
      } 
      PrintStream printStream1 = System.out;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(" <<<<<<<<<<<<<<<<< dump ");
      stringBuilder1.append(paramString);
      printStream1.println(stringBuilder1.toString());
      return;
    } 
  }
  
  public static void dumpLayoutParams(ViewGroup paramViewGroup, String paramString) {
    StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(".(");
    stringBuilder1.append(stackTraceElement.getFileName());
    stringBuilder1.append(":");
    stringBuilder1.append(stackTraceElement.getLineNumber());
    stringBuilder1.append(") ");
    stringBuilder1.append(paramString);
    stringBuilder1.append("  ");
    String str = stringBuilder1.toString();
    int i = paramViewGroup.getChildCount();
    PrintStream printStream = System.out;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append(" children ");
    stringBuilder2.append(i);
    printStream.println(stringBuilder2.toString());
    byte b = 0;
    label22: while (true) {
      if (b < i) {
        View view = paramViewGroup.getChildAt(b);
        printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("     ");
        stringBuilder.append(getName(view));
        printStream.println(stringBuilder.toString());
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Field[] arrayOfField = layoutParams.getClass().getFields();
        byte b1 = 0;
        while (true) {
          if (b1 < arrayOfField.length) {
            Field field = arrayOfField[b1];
            try {
              Object object = field.get(layoutParams);
              if (field.getName().contains("To") && !object.toString().equals("-1")) {
                PrintStream printStream1 = System.out;
                StringBuilder stringBuilder3 = new StringBuilder();
                this();
                stringBuilder3.append(str);
                stringBuilder3.append("       ");
                stringBuilder3.append(field.getName());
                stringBuilder3.append(" ");
                stringBuilder3.append(object);
                printStream1.println(stringBuilder3.toString());
              } 
            } catch (IllegalAccessException illegalAccessException) {}
            b1++;
            continue;
          } 
          b++;
          continue label22;
        } 
        break;
      } 
      return;
    } 
  }
  
  public static void dumpPoc(Object paramObject) {
    StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(".(");
    stringBuilder1.append(stackTraceElement.getFileName());
    stringBuilder1.append(":");
    stringBuilder1.append(stackTraceElement.getLineNumber());
    stringBuilder1.append(")");
    String str = stringBuilder1.toString();
    Class<?> clazz = paramObject.getClass();
    PrintStream printStream = System.out;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str);
    stringBuilder2.append("------------- ");
    stringBuilder2.append(clazz.getName());
    stringBuilder2.append(" --------------------");
    printStream.println(stringBuilder2.toString());
    Field[] arrayOfField = clazz.getFields();
    byte b = 0;
    while (true) {
      if (b < arrayOfField.length) {
        Field field = arrayOfField[b];
        try {
          Object object = field.get(paramObject);
          if (field.getName().startsWith("layout_constraint") && (!(object instanceof Integer) || !object.toString().equals("-1")) && (!(object instanceof Integer) || !object.toString().equals("0")) && (!(object instanceof Float) || !object.toString().equals("1.0")) && (!(object instanceof Float) || !object.toString().equals("0.5"))) {
            PrintStream printStream1 = System.out;
            StringBuilder stringBuilder3 = new StringBuilder();
            this();
            stringBuilder3.append(str);
            stringBuilder3.append("    ");
            stringBuilder3.append(field.getName());
            stringBuilder3.append(" ");
            stringBuilder3.append(object);
            printStream1.println(stringBuilder3.toString());
          } 
        } catch (IllegalAccessException illegalAccessException) {}
        b++;
        continue;
      } 
      paramObject = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("------------- ");
      stringBuilder.append(clazz.getSimpleName());
      stringBuilder.append(" --------------------");
      paramObject.println(stringBuilder.toString());
      return;
    } 
  }
  
  public static String getActionType(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    Field[] arrayOfField = MotionEvent.class.getFields();
    byte b = 0;
    while (true) {
      if (b < arrayOfField.length) {
        Field field = arrayOfField[b];
        try {
          if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(int.class) && field.getInt(null) == i)
            return field.getName(); 
        } catch (IllegalAccessException illegalAccessException) {}
        b++;
        continue;
      } 
      return "---";
    } 
  }
  
  public static String getCallFrom(int paramInt) {
    StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[paramInt + 2];
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(".(");
    stringBuilder.append(stackTraceElement.getFileName());
    stringBuilder.append(":");
    stringBuilder.append(stackTraceElement.getLineNumber());
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public static String getLoc() {
    StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(".(");
    stringBuilder.append(stackTraceElement.getFileName());
    stringBuilder.append(":");
    stringBuilder.append(stackTraceElement.getLineNumber());
    stringBuilder.append(") ");
    stringBuilder.append(stackTraceElement.getMethodName());
    stringBuilder.append("()");
    return stringBuilder.toString();
  }
  
  public static String getLocation() {
    StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(".(");
    stringBuilder.append(stackTraceElement.getFileName());
    stringBuilder.append(":");
    stringBuilder.append(stackTraceElement.getLineNumber());
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public static String getLocation2() {
    StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[2];
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(".(");
    stringBuilder.append(stackTraceElement.getFileName());
    stringBuilder.append(":");
    stringBuilder.append(stackTraceElement.getLineNumber());
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public static String getName(Context paramContext, int paramInt) {
    if (paramInt != -1)
      try {
        return paramContext.getResources().getResourceEntryName(paramInt);
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        stringBuilder.append(paramInt);
        return stringBuilder.toString();
      }  
    return "UNKNOWN";
  }
  
  public static String getName(Context paramContext, int[] paramArrayOfint) {
    try {
      StringBuilder stringBuilder2 = new StringBuilder();
      this();
      stringBuilder2.append(paramArrayOfint.length);
      stringBuilder2.append("[");
      String str = stringBuilder2.toString();
      for (byte b = 0; b < paramArrayOfint.length; b++) {
        StringBuilder stringBuilder3 = new StringBuilder();
        this();
        stringBuilder3.append(str);
        if (b == 0) {
          str = "";
        } else {
          str = " ";
        } 
        stringBuilder3.append(str);
        String str1 = stringBuilder3.toString();
        try {
          str = paramContext.getResources().getResourceEntryName(paramArrayOfint[b]);
        } catch (android.content.res.Resources.NotFoundException notFoundException) {
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("? ");
          stringBuilder.append(paramArrayOfint[b]);
          stringBuilder.append(" ");
          str = stringBuilder.toString();
        } 
        StringBuilder stringBuilder4 = new StringBuilder();
        this();
        stringBuilder4.append(str1);
        stringBuilder4.append(str);
        str = stringBuilder4.toString();
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      this();
      stringBuilder1.append(str);
      stringBuilder1.append("]");
      return stringBuilder1.toString();
    } catch (Exception exception) {
      Log.v("DEBUG", exception.toString());
      return "UNKNOWN";
    } 
  }
  
  public static String getName(View paramView) {
    try {
      return paramView.getContext().getResources().getResourceEntryName(paramView.getId());
    } catch (Exception exception) {
      return "UNKNOWN";
    } 
  }
  
  public static String getState(MotionLayout paramMotionLayout, int paramInt) {
    return (paramInt == -1) ? "UNDEFINED" : paramMotionLayout.getContext().getResources().getResourceEntryName(paramInt);
  }
  
  public static void logStack(String paramString1, String paramString2, int paramInt) {
    StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
    int i = arrayOfStackTraceElement.length;
    boolean bool = true;
    i = Math.min(paramInt, i - 1);
    String str = " ";
    for (paramInt = bool; paramInt <= i; paramInt++) {
      StackTraceElement stackTraceElement = arrayOfStackTraceElement[paramInt];
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(".(");
      stringBuilder1.append(arrayOfStackTraceElement[paramInt].getFileName());
      stringBuilder1.append(":");
      stringBuilder1.append(arrayOfStackTraceElement[paramInt].getLineNumber());
      stringBuilder1.append(") ");
      stringBuilder1.append(arrayOfStackTraceElement[paramInt].getMethodName());
      String str1 = stringBuilder1.toString();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(" ");
      str = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString2);
      stringBuilder2.append(str);
      stringBuilder2.append(str1);
      stringBuilder2.append(str);
      Log.v(paramString1, stringBuilder2.toString());
    } 
  }
  
  public static void printStack(String paramString, int paramInt) {
    StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
    int i = arrayOfStackTraceElement.length;
    boolean bool = true;
    i = Math.min(paramInt, i - 1);
    String str = " ";
    for (paramInt = bool; paramInt <= i; paramInt++) {
      StackTraceElement stackTraceElement = arrayOfStackTraceElement[paramInt];
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(".(");
      stringBuilder1.append(arrayOfStackTraceElement[paramInt].getFileName());
      stringBuilder1.append(":");
      stringBuilder1.append(arrayOfStackTraceElement[paramInt].getLineNumber());
      stringBuilder1.append(") ");
      String str1 = stringBuilder1.toString();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(" ");
      str = stringBuilder2.toString();
      PrintStream printStream = System.out;
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString);
      stringBuilder2.append(str);
      stringBuilder2.append(str1);
      stringBuilder2.append(str);
      printStream.println(stringBuilder2.toString());
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\Debug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */