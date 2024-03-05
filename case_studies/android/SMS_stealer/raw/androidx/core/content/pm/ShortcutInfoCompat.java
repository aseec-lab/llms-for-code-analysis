package androidx.core.content.pm;

import android.app.Person;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ShortcutInfoCompat {
  private static final String EXTRA_LONG_LIVED = "extraLongLived";
  
  private static final String EXTRA_PERSON_ = "extraPerson_";
  
  private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
  
  ComponentName mActivity;
  
  Set<String> mCategories;
  
  Context mContext;
  
  CharSequence mDisabledMessage;
  
  IconCompat mIcon;
  
  String mId;
  
  Intent[] mIntents;
  
  boolean mIsAlwaysBadged;
  
  boolean mIsLongLived;
  
  CharSequence mLabel;
  
  CharSequence mLongLabel;
  
  Person[] mPersons;
  
  int mRank;
  
  private PersistableBundle buildLegacyExtrasBundle() {
    PersistableBundle persistableBundle = new PersistableBundle();
    Person[] arrayOfPerson = this.mPersons;
    if (arrayOfPerson != null && arrayOfPerson.length > 0) {
      persistableBundle.putInt("extraPersonCount", arrayOfPerson.length);
      for (int i = 0; i < this.mPersons.length; i = j) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("extraPerson_");
        int j = i + 1;
        stringBuilder.append(j);
        persistableBundle.putPersistableBundle(stringBuilder.toString(), this.mPersons[i].toPersistableBundle());
      } 
    } 
    persistableBundle.putBoolean("extraLongLived", this.mIsLongLived);
    return persistableBundle;
  }
  
  static boolean getLongLivedFromExtra(PersistableBundle paramPersistableBundle) {
    return (paramPersistableBundle == null || !paramPersistableBundle.containsKey("extraLongLived")) ? false : paramPersistableBundle.getBoolean("extraLongLived");
  }
  
  static Person[] getPersonsFromExtra(PersistableBundle paramPersistableBundle) {
    if (paramPersistableBundle == null || !paramPersistableBundle.containsKey("extraPersonCount"))
      return null; 
    int j = paramPersistableBundle.getInt("extraPersonCount");
    Person[] arrayOfPerson = new Person[j];
    for (int i = 0; i < j; i = k) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("extraPerson_");
      int k = i + 1;
      stringBuilder.append(k);
      arrayOfPerson[i] = Person.fromPersistableBundle(paramPersistableBundle.getPersistableBundle(stringBuilder.toString()));
    } 
    return arrayOfPerson;
  }
  
  Intent addToIntent(Intent paramIntent) {
    Intent[] arrayOfIntent = this.mIntents;
    paramIntent.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)arrayOfIntent[arrayOfIntent.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
    if (this.mIcon != null) {
      Drawable drawable;
      ComponentName componentName = null;
      Intent[] arrayOfIntent1 = null;
      if (this.mIsAlwaysBadged) {
        Intent[] arrayOfIntent2;
        PackageManager packageManager = this.mContext.getPackageManager();
        componentName = this.mActivity;
        arrayOfIntent = arrayOfIntent1;
        if (componentName != null)
          try {
            Drawable drawable1 = packageManager.getActivityIcon(componentName);
          } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
            arrayOfIntent2 = arrayOfIntent1;
          }  
        Intent[] arrayOfIntent3 = arrayOfIntent2;
        if (arrayOfIntent2 == null)
          drawable = this.mContext.getApplicationInfo().loadIcon(packageManager); 
      } 
      this.mIcon.addToShortcutIntent(paramIntent, drawable, this.mContext);
    } 
    return paramIntent;
  }
  
  public ComponentName getActivity() {
    return this.mActivity;
  }
  
  public Set<String> getCategories() {
    return this.mCategories;
  }
  
  public CharSequence getDisabledMessage() {
    return this.mDisabledMessage;
  }
  
  public IconCompat getIcon() {
    return this.mIcon;
  }
  
  public String getId() {
    return this.mId;
  }
  
  public Intent getIntent() {
    Intent[] arrayOfIntent = this.mIntents;
    return arrayOfIntent[arrayOfIntent.length - 1];
  }
  
  public Intent[] getIntents() {
    Intent[] arrayOfIntent = this.mIntents;
    return Arrays.<Intent>copyOf(arrayOfIntent, arrayOfIntent.length);
  }
  
  public CharSequence getLongLabel() {
    return this.mLongLabel;
  }
  
  public int getRank() {
    return this.mRank;
  }
  
  public CharSequence getShortLabel() {
    return this.mLabel;
  }
  
  public ShortcutInfo toShortcutInfo() {
    ShortcutInfo.Builder builder = (new ShortcutInfo.Builder(this.mContext, this.mId)).setShortLabel(this.mLabel).setIntents(this.mIntents);
    IconCompat iconCompat = this.mIcon;
    if (iconCompat != null)
      builder.setIcon(iconCompat.toIcon(this.mContext)); 
    if (!TextUtils.isEmpty(this.mLongLabel))
      builder.setLongLabel(this.mLongLabel); 
    if (!TextUtils.isEmpty(this.mDisabledMessage))
      builder.setDisabledMessage(this.mDisabledMessage); 
    ComponentName componentName = this.mActivity;
    if (componentName != null)
      builder.setActivity(componentName); 
    Set<String> set = this.mCategories;
    if (set != null)
      builder.setCategories(set); 
    builder.setRank(this.mRank);
    if (Build.VERSION.SDK_INT >= 29) {
      Person[] arrayOfPerson = this.mPersons;
      if (arrayOfPerson != null && arrayOfPerson.length > 0) {
        int i = arrayOfPerson.length;
        Person[] arrayOfPerson1 = new Person[i];
        for (byte b = 0; b < i; b++)
          arrayOfPerson1[b] = this.mPersons[b].toAndroidPerson(); 
        builder.setPersons(arrayOfPerson1);
      } 
      builder.setLongLived(this.mIsLongLived);
    } else {
      builder.setExtras(buildLegacyExtrasBundle());
    } 
    return builder.build();
  }
  
  public static class Builder {
    private final ShortcutInfoCompat mInfo;
    
    public Builder(Context param1Context, ShortcutInfo param1ShortcutInfo) {
      ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
      this.mInfo = shortcutInfoCompat;
      shortcutInfoCompat.mContext = param1Context;
      this.mInfo.mId = param1ShortcutInfo.getId();
      Intent[] arrayOfIntent = param1ShortcutInfo.getIntents();
      this.mInfo.mIntents = Arrays.<Intent>copyOf(arrayOfIntent, arrayOfIntent.length);
      this.mInfo.mActivity = param1ShortcutInfo.getActivity();
      this.mInfo.mLabel = param1ShortcutInfo.getShortLabel();
      this.mInfo.mLongLabel = param1ShortcutInfo.getLongLabel();
      this.mInfo.mDisabledMessage = param1ShortcutInfo.getDisabledMessage();
      this.mInfo.mCategories = param1ShortcutInfo.getCategories();
      this.mInfo.mPersons = ShortcutInfoCompat.getPersonsFromExtra(param1ShortcutInfo.getExtras());
      this.mInfo.mRank = param1ShortcutInfo.getRank();
    }
    
    public Builder(Context param1Context, String param1String) {
      ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
      this.mInfo = shortcutInfoCompat;
      shortcutInfoCompat.mContext = param1Context;
      this.mInfo.mId = param1String;
    }
    
    public Builder(ShortcutInfoCompat param1ShortcutInfoCompat) {
      ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
      this.mInfo = shortcutInfoCompat;
      shortcutInfoCompat.mContext = param1ShortcutInfoCompat.mContext;
      this.mInfo.mId = param1ShortcutInfoCompat.mId;
      this.mInfo.mIntents = Arrays.<Intent>copyOf(param1ShortcutInfoCompat.mIntents, param1ShortcutInfoCompat.mIntents.length);
      this.mInfo.mActivity = param1ShortcutInfoCompat.mActivity;
      this.mInfo.mLabel = param1ShortcutInfoCompat.mLabel;
      this.mInfo.mLongLabel = param1ShortcutInfoCompat.mLongLabel;
      this.mInfo.mDisabledMessage = param1ShortcutInfoCompat.mDisabledMessage;
      this.mInfo.mIcon = param1ShortcutInfoCompat.mIcon;
      this.mInfo.mIsAlwaysBadged = param1ShortcutInfoCompat.mIsAlwaysBadged;
      this.mInfo.mIsLongLived = param1ShortcutInfoCompat.mIsLongLived;
      this.mInfo.mRank = param1ShortcutInfoCompat.mRank;
      if (param1ShortcutInfoCompat.mPersons != null)
        this.mInfo.mPersons = Arrays.<Person>copyOf(param1ShortcutInfoCompat.mPersons, param1ShortcutInfoCompat.mPersons.length); 
      if (param1ShortcutInfoCompat.mCategories != null)
        this.mInfo.mCategories = new HashSet<String>(param1ShortcutInfoCompat.mCategories); 
    }
    
    public ShortcutInfoCompat build() {
      if (!TextUtils.isEmpty(this.mInfo.mLabel)) {
        if (this.mInfo.mIntents != null && this.mInfo.mIntents.length != 0)
          return this.mInfo; 
        throw new IllegalArgumentException("Shortcut must have an intent");
      } 
      throw new IllegalArgumentException("Shortcut must have a non-empty label");
    }
    
    public Builder setActivity(ComponentName param1ComponentName) {
      this.mInfo.mActivity = param1ComponentName;
      return this;
    }
    
    public Builder setAlwaysBadged() {
      this.mInfo.mIsAlwaysBadged = true;
      return this;
    }
    
    public Builder setCategories(Set<String> param1Set) {
      this.mInfo.mCategories = param1Set;
      return this;
    }
    
    public Builder setDisabledMessage(CharSequence param1CharSequence) {
      this.mInfo.mDisabledMessage = param1CharSequence;
      return this;
    }
    
    public Builder setIcon(IconCompat param1IconCompat) {
      this.mInfo.mIcon = param1IconCompat;
      return this;
    }
    
    public Builder setIntent(Intent param1Intent) {
      return setIntents(new Intent[] { param1Intent });
    }
    
    public Builder setIntents(Intent[] param1ArrayOfIntent) {
      this.mInfo.mIntents = param1ArrayOfIntent;
      return this;
    }
    
    public Builder setLongLabel(CharSequence param1CharSequence) {
      this.mInfo.mLongLabel = param1CharSequence;
      return this;
    }
    
    @Deprecated
    public Builder setLongLived() {
      this.mInfo.mIsLongLived = true;
      return this;
    }
    
    public Builder setLongLived(boolean param1Boolean) {
      this.mInfo.mIsLongLived = param1Boolean;
      return this;
    }
    
    public Builder setPerson(Person param1Person) {
      return setPersons(new Person[] { param1Person });
    }
    
    public Builder setPersons(Person[] param1ArrayOfPerson) {
      this.mInfo.mPersons = param1ArrayOfPerson;
      return this;
    }
    
    public Builder setRank(int param1Int) {
      this.mInfo.mRank = param1Int;
      return this;
    }
    
    public Builder setShortLabel(CharSequence param1CharSequence) {
      this.mInfo.mLabel = param1CharSequence;
      return this;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\pm\ShortcutInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */