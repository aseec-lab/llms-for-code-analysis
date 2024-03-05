package androidx.core.text.util;

import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FindAddress {
  private static final String HOUSE_COMPONENT = "(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)";
  
  private static final String HOUSE_END = "(?=[,\"'\t                　\n\013\f\r  ]|$)";
  
  private static final String HOUSE_POST_DELIM = ",\"'\t                　\n\013\f\r  ";
  
  private static final String HOUSE_PRE_DELIM = ":,\"'\t                　\n\013\f\r  ";
  
  private static final int MAX_ADDRESS_LINES = 5;
  
  private static final int MAX_ADDRESS_WORDS = 14;
  
  private static final int MAX_LOCATION_NAME_DISTANCE = 5;
  
  private static final int MIN_ADDRESS_WORDS = 4;
  
  private static final String NL = "\n\013\f\r  ";
  
  private static final String SP = "\t                　";
  
  private static final String WORD_DELIM = ",*•\t                　\n\013\f\r  ";
  
  private static final String WORD_END = "(?=[,*•\t                　\n\013\f\r  ]|$)";
  
  private static final String WS = "\t                　\n\013\f\r  ";
  
  private static final int kMaxAddressNameWordLength = 25;
  
  private static final Pattern sHouseNumberRe;
  
  private static final Pattern sLocationNameRe;
  
  private static final Pattern sStateRe;
  
  private static final ZipRange[] sStateZipCodeRanges = new ZipRange[] { 
      new ZipRange(99, 99, -1, -1), new ZipRange(35, 36, -1, -1), new ZipRange(71, 72, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(85, 86, -1, -1), new ZipRange(90, 96, -1, -1), new ZipRange(80, 81, -1, -1), new ZipRange(6, 6, -1, -1), new ZipRange(20, 20, -1, -1), new ZipRange(19, 19, -1, -1), 
      new ZipRange(32, 34, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(30, 31, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(50, 52, -1, -1), new ZipRange(83, 83, -1, -1), new ZipRange(60, 62, -1, -1), new ZipRange(46, 47, -1, -1), new ZipRange(66, 67, 73, -1), 
      new ZipRange(40, 42, -1, -1), new ZipRange(70, 71, -1, -1), new ZipRange(1, 2, -1, -1), new ZipRange(20, 21, -1, -1), new ZipRange(3, 4, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(48, 49, -1, -1), new ZipRange(55, 56, -1, -1), new ZipRange(63, 65, -1, -1), new ZipRange(96, 96, -1, -1), 
      new ZipRange(38, 39, -1, -1), new ZipRange(55, 56, -1, -1), new ZipRange(27, 28, -1, -1), new ZipRange(58, 58, -1, -1), new ZipRange(68, 69, -1, -1), new ZipRange(3, 4, -1, -1), new ZipRange(7, 8, -1, -1), new ZipRange(87, 88, 86, -1), new ZipRange(88, 89, 96, -1), new ZipRange(10, 14, 0, 6), 
      new ZipRange(43, 45, -1, -1), new ZipRange(73, 74, -1, -1), new ZipRange(97, 97, -1, -1), new ZipRange(15, 19, -1, -1), new ZipRange(6, 6, 0, 9), new ZipRange(96, 96, -1, -1), new ZipRange(2, 2, -1, -1), new ZipRange(29, 29, -1, -1), new ZipRange(57, 57, -1, -1), new ZipRange(37, 38, -1, -1), 
      new ZipRange(75, 79, 87, 88), new ZipRange(84, 84, -1, -1), new ZipRange(22, 24, 20, -1), new ZipRange(6, 9, -1, -1), new ZipRange(5, 5, -1, -1), new ZipRange(98, 99, -1, -1), new ZipRange(53, 54, -1, -1), new ZipRange(24, 26, -1, -1), new ZipRange(82, 83, -1, -1) };
  
  private static final Pattern sSuffixedNumberRe;
  
  private static final Pattern sWordRe = Pattern.compile("[^,*•\t                　\n\013\f\r  ]+(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
  
  private static final Pattern sZipCodeRe;
  
  static {
    sHouseNumberRe = Pattern.compile("(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)(?:-(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?))*(?=[,\"'\t                　\n\013\f\r  ]|$)", 2);
    sStateRe = Pattern.compile("(?:(ak|alaska)|(al|alabama)|(ar|arkansas)|(as|american[\t                　]+samoa)|(az|arizona)|(ca|california)|(co|colorado)|(ct|connecticut)|(dc|district[\t                　]+of[\t                　]+columbia)|(de|delaware)|(fl|florida)|(fm|federated[\t                　]+states[\t                　]+of[\t                　]+micronesia)|(ga|georgia)|(gu|guam)|(hi|hawaii)|(ia|iowa)|(id|idaho)|(il|illinois)|(in|indiana)|(ks|kansas)|(ky|kentucky)|(la|louisiana)|(ma|massachusetts)|(md|maryland)|(me|maine)|(mh|marshall[\t                　]+islands)|(mi|michigan)|(mn|minnesota)|(mo|missouri)|(mp|northern[\t                　]+mariana[\t                　]+islands)|(ms|mississippi)|(mt|montana)|(nc|north[\t                　]+carolina)|(nd|north[\t                　]+dakota)|(ne|nebraska)|(nh|new[\t                　]+hampshire)|(nj|new[\t                　]+jersey)|(nm|new[\t                　]+mexico)|(nv|nevada)|(ny|new[\t                　]+york)|(oh|ohio)|(ok|oklahoma)|(or|oregon)|(pa|pennsylvania)|(pr|puerto[\t                　]+rico)|(pw|palau)|(ri|rhode[\t                　]+island)|(sc|south[\t                　]+carolina)|(sd|south[\t                　]+dakota)|(tn|tennessee)|(tx|texas)|(ut|utah)|(va|virginia)|(vi|virgin[\t                　]+islands)|(vt|vermont)|(wa|washington)|(wi|wisconsin)|(wv|west[\t                　]+virginia)|(wy|wyoming))(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
    sLocationNameRe = Pattern.compile("(?:alley|annex|arcade|ave[.]?|avenue|alameda|bayou|beach|bend|bluffs?|bottom|boulevard|branch|bridge|brooks?|burgs?|bypass|broadway|camino|camp|canyon|cape|causeway|centers?|circles?|cliffs?|club|common|corners?|course|courts?|coves?|creek|crescent|crest|crossing|crossroad|curve|circulo|dale|dam|divide|drives?|estates?|expressway|extensions?|falls?|ferry|fields?|flats?|fords?|forest|forges?|forks?|fort|freeway|gardens?|gateway|glens?|greens?|groves?|harbors?|haven|heights|highway|hills?|hollow|inlet|islands?|isle|junctions?|keys?|knolls?|lakes?|land|landing|lane|lights?|loaf|locks?|lodge|loop|mall|manors?|meadows?|mews|mills?|mission|motorway|mount|mountains?|neck|orchard|oval|overpass|parks?|parkways?|pass|passage|path|pike|pines?|plains?|plaza|points?|ports?|prairie|privada|radial|ramp|ranch|rapids?|rd[.]?|rest|ridges?|river|roads?|route|row|rue|run|shoals?|shores?|skyway|springs?|spurs?|squares?|station|stravenue|stream|st[.]?|streets?|summit|speedway|terrace|throughway|trace|track|trafficway|trail|tunnel|turnpike|underpass|unions?|valleys?|viaduct|views?|villages?|ville|vista|walks?|wall|ways?|wells?|xing|xrd)(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
    sSuffixedNumberRe = Pattern.compile("([0-9]+)(st|nd|rd|th)", 2);
    sZipCodeRe = Pattern.compile("(?:[0-9]{5}(?:-[0-9]{4})?)(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
  }
  
  private static int attemptMatch(String paramString, MatchResult paramMatchResult) {
    int i;
    int j = paramMatchResult.end();
    Matcher matcher = sWordRe.matcher(paramString);
    String str = "";
    int n = 1;
    byte b = 1;
    int i1 = 1;
    boolean bool = false;
    int k = -1;
    int m = -1;
    while (true) {
      i = j;
      if (j < paramString.length()) {
        boolean bool1;
        int i2;
        int i3;
        if (!matcher.find(j)) {
          i = paramString.length();
          return -i;
        } 
        i = j;
        if (matcher.end() - matcher.start() > 25) {
          i = matcher.end();
          return -i;
        } 
        while (i < matcher.start()) {
          j = n;
          if ("\n\013\f\r  ".indexOf(paramString.charAt(i)) != -1)
            j = n + 1; 
          i++;
          n = j;
        } 
        if (n > 5 || ++b > 14)
          break; 
        if (matchHouseNumber(paramString, i) != null) {
          if (i1 && n > 1)
            return -i; 
          j = i1;
          bool1 = bool;
          i3 = k;
          i2 = m;
          if (k == -1) {
            j = i1;
            bool1 = bool;
            i3 = i;
            i2 = m;
          } 
        } else if (isValidLocationName(matcher.group(0))) {
          j = 0;
          bool1 = true;
          i3 = k;
          i2 = m;
        } else {
          if (b == 5 && !bool) {
            i = matcher.end();
            break;
          } 
          j = m;
          if (bool) {
            j = m;
            if (b > 4) {
              MatchResult matchResult = matchState(paramString, i);
              j = m;
              if (matchResult != null) {
                if (str.equals("et") && matchResult.group(0).equals("al")) {
                  i = matchResult.end();
                  break;
                } 
                Matcher matcher1 = sWordRe.matcher(paramString);
                if (matcher1.find(matchResult.end())) {
                  j = m;
                  if (isValidZipCode(matcher1.group(0), matchResult))
                    return matcher1.end(); 
                } else {
                  j = matchResult.end();
                } 
              } 
            } 
          } 
          i = 0;
          i2 = j;
          i3 = k;
          bool1 = bool;
          j = i;
        } 
        str = matcher.group(0);
        i = matcher.end();
        i1 = j;
        bool = bool1;
        k = i3;
        m = i2;
        j = i;
        continue;
      } 
      break;
    } 
    if (m > 0)
      return m; 
    if (k <= 0)
      k = i; 
    return -k;
  }
  
  private static boolean checkHouseNumber(String paramString) {
    int i = 0;
    int j;
    for (j = 0; i < paramString.length(); j = k) {
      int k = j;
      if (Character.isDigit(paramString.charAt(i)))
        k = j + 1; 
      i++;
    } 
    if (j > 5)
      return false; 
    Matcher matcher = sSuffixedNumberRe.matcher(paramString);
    if (matcher.find()) {
      i = Integer.parseInt(matcher.group(1));
      if (i == 0)
        return false; 
      String str2 = matcher.group(2).toLowerCase(Locale.getDefault());
      j = i % 10;
      String str1 = "th";
      if (j != 1) {
        if (j != 2) {
          if (j != 3)
            return str2.equals("th"); 
          if (i % 100 != 13)
            str1 = "rd"; 
          return str2.equals(str1);
        } 
        if (i % 100 != 12)
          str1 = "nd"; 
        return str2.equals(str1);
      } 
      if (i % 100 != 11)
        str1 = "st"; 
      return str2.equals(str1);
    } 
    return true;
  }
  
  static String findAddress(String paramString) {
    Matcher matcher = sHouseNumberRe.matcher(paramString);
    for (int i = 0; matcher.find(i); i = matcher.end()) {
      if (checkHouseNumber(matcher.group(0))) {
        i = matcher.start();
        int j = attemptMatch(paramString, matcher);
        if (j > 0)
          return paramString.substring(i, j); 
        i = -j;
        continue;
      } 
    } 
    return null;
  }
  
  public static boolean isValidLocationName(String paramString) {
    return sLocationNameRe.matcher(paramString).matches();
  }
  
  public static boolean isValidZipCode(String paramString) {
    return sZipCodeRe.matcher(paramString).matches();
  }
  
  public static boolean isValidZipCode(String paramString1, String paramString2) {
    return isValidZipCode(paramString1, matchState(paramString2, 0));
  }
  
  private static boolean isValidZipCode(String paramString, MatchResult paramMatchResult) {
    int j;
    boolean bool2 = false;
    if (paramMatchResult == null)
      return false; 
    int i = paramMatchResult.groupCount();
    while (true) {
      j = i;
      if (i > 0) {
        j = i - 1;
        if (paramMatchResult.group(i) != null)
          break; 
        i = j;
        continue;
      } 
      break;
    } 
    boolean bool1 = bool2;
    if (sZipCodeRe.matcher(paramString).matches()) {
      bool1 = bool2;
      if (sStateZipCodeRanges[j].matches(paramString))
        bool1 = true; 
    } 
    return bool1;
  }
  
  public static MatchResult matchHouseNumber(String paramString, int paramInt) {
    if (paramInt > 0 && ":,\"'\t                　\n\013\f\r  ".indexOf(paramString.charAt(paramInt - 1)) == -1)
      return null; 
    Matcher matcher = sHouseNumberRe.matcher(paramString).region(paramInt, paramString.length());
    if (matcher.lookingAt()) {
      MatchResult matchResult = matcher.toMatchResult();
      if (checkHouseNumber(matchResult.group(0)))
        return matchResult; 
    } 
    return null;
  }
  
  public static MatchResult matchState(String paramString, int paramInt) {
    MatchResult matchResult;
    String str = null;
    if (paramInt > 0 && ",*•\t                　\n\013\f\r  ".indexOf(paramString.charAt(paramInt - 1)) == -1)
      return null; 
    Matcher matcher = sStateRe.matcher(paramString).region(paramInt, paramString.length());
    paramString = str;
    if (matcher.lookingAt())
      matchResult = matcher.toMatchResult(); 
    return matchResult;
  }
  
  private static class ZipRange {
    int mException1;
    
    int mException2;
    
    int mHigh;
    
    int mLow;
    
    ZipRange(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      this.mLow = param1Int1;
      this.mHigh = param1Int2;
      this.mException1 = param1Int3;
      this.mException2 = param1Int4;
    }
    
    boolean matches(String param1String) {
      boolean bool = false;
      int i = Integer.parseInt(param1String.substring(0, 2));
      if ((this.mLow <= i && i <= this.mHigh) || i == this.mException1 || i == this.mException2)
        bool = true; 
      return bool;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\tex\\util\FindAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */