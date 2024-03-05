package okhttp3.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import okhttp3.internal.Util;

public final class HttpDate {
  private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
  
  private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
  
  public static final long MAX_DATE = 253402300799999L;
  
  private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>() {
      protected DateFormat initialValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        simpleDateFormat.setLenient(false);
        simpleDateFormat.setTimeZone(Util.UTC);
        return simpleDateFormat;
      }
    };
  
  static {
    String[] arrayOfString = new String[15];
    arrayOfString[0] = "EEE, dd MMM yyyy HH:mm:ss zzz";
    arrayOfString[1] = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    arrayOfString[2] = "EEE MMM d HH:mm:ss yyyy";
    arrayOfString[3] = "EEE, dd-MMM-yyyy HH:mm:ss z";
    arrayOfString[4] = "EEE, dd-MMM-yyyy HH-mm-ss z";
    arrayOfString[5] = "EEE, dd MMM yy HH:mm:ss z";
    arrayOfString[6] = "EEE dd-MMM-yyyy HH:mm:ss z";
    arrayOfString[7] = "EEE dd MMM yyyy HH:mm:ss z";
    arrayOfString[8] = "EEE dd-MMM-yyyy HH-mm-ss z";
    arrayOfString[9] = "EEE dd-MMM-yy HH:mm:ss z";
    arrayOfString[10] = "EEE dd MMM yy HH:mm:ss z";
    arrayOfString[11] = "EEE,dd-MMM-yy HH:mm:ss z";
    arrayOfString[12] = "EEE,dd-MMM-yyyy HH:mm:ss z";
    arrayOfString[13] = "EEE, dd-MM-yyyy HH:mm:ss z";
    arrayOfString[14] = "EEE MMM d yyyy HH:mm:ss z";
    BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = arrayOfString;
    BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[arrayOfString.length];
  }
  
  public static String format(Date paramDate) {
    return ((DateFormat)STANDARD_DATE_FORMAT.get()).format(paramDate);
  }
  
  public static Date parse(String paramString) {
    if (paramString.length() == 0)
      return null; 
    ParsePosition parsePosition = new ParsePosition(0);
    Date date = ((DateFormat)STANDARD_DATE_FORMAT.get()).parse(paramString, parsePosition);
    if (parsePosition.getIndex() == paramString.length())
      return date; 
    synchronized (BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS) {
      int i = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
      for (byte b = 0; b < i; b++) {
        DateFormat dateFormat2 = BROWSER_COMPATIBLE_DATE_FORMATS[b];
        DateFormat dateFormat1 = dateFormat2;
        if (dateFormat2 == null) {
          dateFormat1 = new SimpleDateFormat();
          super(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[b], Locale.US);
          dateFormat1.setTimeZone(Util.UTC);
          BROWSER_COMPATIBLE_DATE_FORMATS[b] = dateFormat1;
        } 
        parsePosition.setIndex(0);
        Date date1 = dateFormat1.parse(paramString, parsePosition);
        if (parsePosition.getIndex() != 0)
          return date1; 
      } 
      return null;
    } 
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\HttpDate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */