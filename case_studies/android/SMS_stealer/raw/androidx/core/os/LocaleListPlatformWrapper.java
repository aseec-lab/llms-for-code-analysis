package androidx.core.os;

import android.os.LocaleList;
import java.util.Locale;

final class LocaleListPlatformWrapper implements LocaleListInterface {
  private final LocaleList mLocaleList;
  
  LocaleListPlatformWrapper(LocaleList paramLocaleList) {
    this.mLocaleList = paramLocaleList;
  }
  
  public boolean equals(Object paramObject) {
    return this.mLocaleList.equals(((LocaleListInterface)paramObject).getLocaleList());
  }
  
  public Locale get(int paramInt) {
    return this.mLocaleList.get(paramInt);
  }
  
  public Locale getFirstMatch(String[] paramArrayOfString) {
    return this.mLocaleList.getFirstMatch(paramArrayOfString);
  }
  
  public Object getLocaleList() {
    return this.mLocaleList;
  }
  
  public int hashCode() {
    return this.mLocaleList.hashCode();
  }
  
  public int indexOf(Locale paramLocale) {
    return this.mLocaleList.indexOf(paramLocale);
  }
  
  public boolean isEmpty() {
    return this.mLocaleList.isEmpty();
  }
  
  public int size() {
    return this.mLocaleList.size();
  }
  
  public String toLanguageTags() {
    return this.mLocaleList.toLanguageTags();
  }
  
  public String toString() {
    return this.mLocaleList.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\os\LocaleListPlatformWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */