package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;

public final class Headers {
  private final String[] namesAndValues;
  
  Headers(Builder paramBuilder) {
    this.namesAndValues = paramBuilder.namesAndValues.<String>toArray(new String[paramBuilder.namesAndValues.size()]);
  }
  
  private Headers(String[] paramArrayOfString) {
    this.namesAndValues = paramArrayOfString;
  }
  
  private static String get(String[] paramArrayOfString, String paramString) {
    for (int i = paramArrayOfString.length - 2; i >= 0; i -= 2) {
      if (paramString.equalsIgnoreCase(paramArrayOfString[i]))
        return paramArrayOfString[i + 1]; 
    } 
    return null;
  }
  
  public static Headers of(Map<String, String> paramMap) {
    if (paramMap != null) {
      StringBuilder stringBuilder;
      String[] arrayOfString = new String[paramMap.size() * 2];
      Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
      byte b = 0;
      while (iterator.hasNext()) {
        Map.Entry entry = iterator.next();
        if (entry.getKey() != null && entry.getValue() != null) {
          String str1 = ((String)entry.getKey()).trim();
          String str2 = ((String)entry.getValue()).trim();
          if (str1.length() != 0 && str1.indexOf(false) == -1 && str2.indexOf(false) == -1) {
            arrayOfString[b] = str1;
            arrayOfString[b + 1] = str2;
            b += 2;
            continue;
          } 
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unexpected header: ");
          stringBuilder.append(str1);
          stringBuilder.append(": ");
          stringBuilder.append(str2);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        throw new IllegalArgumentException("Headers cannot be null");
      } 
      return new Headers((String[])stringBuilder);
    } 
    throw new NullPointerException("headers == null");
  }
  
  public static Headers of(String... paramVarArgs) {
    if (paramVarArgs != null) {
      if (paramVarArgs.length % 2 == 0) {
        StringBuilder stringBuilder;
        String[] arrayOfString = (String[])paramVarArgs.clone();
        byte b = 0;
        while (b < arrayOfString.length) {
          if (arrayOfString[b] != null) {
            arrayOfString[b] = arrayOfString[b].trim();
            b++;
            continue;
          } 
          throw new IllegalArgumentException("Headers cannot be null");
        } 
        b = 0;
        while (b < arrayOfString.length) {
          String str1 = arrayOfString[b];
          String str2 = arrayOfString[b + 1];
          if (str1.length() != 0 && str1.indexOf(false) == -1 && str2.indexOf(false) == -1) {
            b += 2;
            continue;
          } 
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unexpected header: ");
          stringBuilder.append(str1);
          stringBuilder.append(": ");
          stringBuilder.append(str2);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return new Headers((String[])stringBuilder);
      } 
      throw new IllegalArgumentException("Expected alternating header names and values");
    } 
    throw new NullPointerException("namesAndValues == null");
  }
  
  public long byteCount() {
    String[] arrayOfString = this.namesAndValues;
    long l = (arrayOfString.length * 2);
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++)
      l += this.namesAndValues[b].length(); 
    return l;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    boolean bool;
    if (paramObject instanceof Headers && Arrays.equals((Object[])((Headers)paramObject).namesAndValues, (Object[])this.namesAndValues)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  @Nullable
  public String get(String paramString) {
    return get(this.namesAndValues, paramString);
  }
  
  @Nullable
  public Date getDate(String paramString) {
    paramString = get(paramString);
    if (paramString != null) {
      Date date = HttpDate.parse(paramString);
    } else {
      paramString = null;
    } 
    return (Date)paramString;
  }
  
  public int hashCode() {
    return Arrays.hashCode((Object[])this.namesAndValues);
  }
  
  public String name(int paramInt) {
    return this.namesAndValues[paramInt * 2];
  }
  
  public Set<String> names() {
    TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    int i = size();
    for (byte b = 0; b < i; b++)
      treeSet.add(name(b)); 
    return Collections.unmodifiableSet(treeSet);
  }
  
  public Builder newBuilder() {
    Builder builder = new Builder();
    Collections.addAll(builder.namesAndValues, this.namesAndValues);
    return builder;
  }
  
  public int size() {
    return this.namesAndValues.length / 2;
  }
  
  public Map<String, List<String>> toMultimap() {
    TreeMap<String, Object> treeMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
    int i = size();
    for (byte b = 0; b < i; b++) {
      String str = name(b).toLowerCase(Locale.US);
      List<String> list2 = (List)treeMap.get(str);
      List<String> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList(2);
        treeMap.put(str, list1);
      } 
      list1.add(value(b));
    } 
    return (Map)treeMap;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    int i = size();
    for (byte b = 0; b < i; b++) {
      stringBuilder.append(name(b));
      stringBuilder.append(": ");
      stringBuilder.append(value(b));
      stringBuilder.append("\n");
    } 
    return stringBuilder.toString();
  }
  
  public String value(int paramInt) {
    return this.namesAndValues[paramInt * 2 + 1];
  }
  
  public List<String> values(String paramString) {
    List<?> list;
    int i = size();
    ArrayList<String> arrayList = null;
    byte b = 0;
    while (b < i) {
      ArrayList<String> arrayList1 = arrayList;
      if (paramString.equalsIgnoreCase(name(b))) {
        arrayList1 = arrayList;
        if (arrayList == null)
          arrayList1 = new ArrayList(2); 
        arrayList1.add(value(b));
      } 
      b++;
      arrayList = arrayList1;
    } 
    if (arrayList != null) {
      list = Collections.unmodifiableList(arrayList);
    } else {
      list = Collections.emptyList();
    } 
    return (List)list;
  }
  
  public static final class Builder {
    final List<String> namesAndValues = new ArrayList<String>(20);
    
    private void checkNameAndValue(String param1String1, String param1String2) {
      if (param1String1 != null) {
        if (!param1String1.isEmpty()) {
          int i = param1String1.length();
          byte b = 0;
          while (b < i) {
            char c = param1String1.charAt(b);
            if (c > ' ' && c < '') {
              b++;
              continue;
            } 
            throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", new Object[] { Integer.valueOf(c), Integer.valueOf(b), param1String1 }));
          } 
          if (param1String2 != null) {
            i = param1String2.length();
            b = 0;
            while (b < i) {
              char c = param1String2.charAt(b);
              if ((c > '\037' || c == '\t') && c < '') {
                b++;
                continue;
              } 
              throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", new Object[] { Integer.valueOf(c), Integer.valueOf(b), param1String1, param1String2 }));
            } 
            return;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("value for name ");
          stringBuilder.append(param1String1);
          stringBuilder.append(" == null");
          throw new NullPointerException(stringBuilder.toString());
        } 
        throw new IllegalArgumentException("name is empty");
      } 
      throw new NullPointerException("name == null");
    }
    
    public Builder add(String param1String) {
      int i = param1String.indexOf(":");
      if (i != -1)
        return add(param1String.substring(0, i).trim(), param1String.substring(i + 1)); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unexpected header: ");
      stringBuilder.append(param1String);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder add(String param1String1, String param1String2) {
      checkNameAndValue(param1String1, param1String2);
      return addLenient(param1String1, param1String2);
    }
    
    Builder addLenient(String param1String) {
      int i = param1String.indexOf(":", 1);
      return (i != -1) ? addLenient(param1String.substring(0, i), param1String.substring(i + 1)) : (param1String.startsWith(":") ? addLenient("", param1String.substring(1)) : addLenient("", param1String));
    }
    
    Builder addLenient(String param1String1, String param1String2) {
      this.namesAndValues.add(param1String1);
      this.namesAndValues.add(param1String2.trim());
      return this;
    }
    
    public Headers build() {
      return new Headers(this);
    }
    
    public String get(String param1String) {
      for (int i = this.namesAndValues.size() - 2; i >= 0; i -= 2) {
        if (param1String.equalsIgnoreCase(this.namesAndValues.get(i)))
          return this.namesAndValues.get(i + 1); 
      } 
      return null;
    }
    
    public Builder removeAll(String param1String) {
      for (int i = 0; i < this.namesAndValues.size(); i = j + 2) {
        int j = i;
        if (param1String.equalsIgnoreCase(this.namesAndValues.get(i))) {
          this.namesAndValues.remove(i);
          this.namesAndValues.remove(i);
          j = i - 2;
        } 
      } 
      return this;
    }
    
    public Builder set(String param1String1, String param1String2) {
      checkNameAndValue(param1String1, param1String2);
      removeAll(param1String1);
      addLenient(param1String1, param1String2);
      return this;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Headers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */