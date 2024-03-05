package androidx.core.os;

import java.util.Locale;

interface LocaleListInterface {
  Locale get(int paramInt);
  
  Locale getFirstMatch(String[] paramArrayOfString);
  
  Object getLocaleList();
  
  int indexOf(Locale paramLocale);
  
  boolean isEmpty();
  
  int size();
  
  String toLanguageTags();
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\os\LocaleListInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */