package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.Parcelable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParcelUtils {
  private static final String INNER_BUNDLE_KEY = "a";
  
  public static <T extends VersionedParcelable> T fromInputStream(InputStream paramInputStream) {
    return (new VersionedParcelStream(paramInputStream, null)).readVersionedParcelable();
  }
  
  public static <T extends VersionedParcelable> T fromParcelable(Parcelable paramParcelable) {
    if (paramParcelable instanceof ParcelImpl)
      return ((ParcelImpl)paramParcelable).getVersionedParcel(); 
    throw new IllegalArgumentException("Invalid parcel");
  }
  
  public static <T extends VersionedParcelable> T getVersionedParcelable(Bundle paramBundle, String paramString) {
    try {
      paramBundle = (Bundle)paramBundle.getParcelable(paramString);
      if (paramBundle == null)
        return null; 
      paramBundle.setClassLoader(ParcelUtils.class.getClassLoader());
      return (T)fromParcelable(paramBundle.getParcelable("a"));
    } catch (RuntimeException runtimeException) {
      return null;
    } 
  }
  
  public static <T extends VersionedParcelable> List<T> getVersionedParcelableList(Bundle paramBundle, String paramString) {
    ArrayList<T> arrayList = new ArrayList();
    try {
      paramBundle = (Bundle)paramBundle.getParcelable(paramString);
      paramBundle.setClassLoader(ParcelUtils.class.getClassLoader());
      Iterator<Parcelable> iterator = paramBundle.getParcelableArrayList("a").iterator();
      while (iterator.hasNext())
        arrayList.add(fromParcelable(iterator.next())); 
      return arrayList;
    } catch (RuntimeException runtimeException) {
      return null;
    } 
  }
  
  public static void putVersionedParcelable(Bundle paramBundle, String paramString, VersionedParcelable paramVersionedParcelable) {
    if (paramVersionedParcelable == null)
      return; 
    Bundle bundle = new Bundle();
    bundle.putParcelable("a", toParcelable(paramVersionedParcelable));
    paramBundle.putParcelable(paramString, (Parcelable)bundle);
  }
  
  public static void putVersionedParcelableList(Bundle paramBundle, String paramString, List<? extends VersionedParcelable> paramList) {
    Bundle bundle = new Bundle();
    ArrayList<Parcelable> arrayList = new ArrayList();
    Iterator<? extends VersionedParcelable> iterator = paramList.iterator();
    while (iterator.hasNext())
      arrayList.add(toParcelable(iterator.next())); 
    bundle.putParcelableArrayList("a", arrayList);
    paramBundle.putParcelable(paramString, (Parcelable)bundle);
  }
  
  public static void toOutputStream(VersionedParcelable paramVersionedParcelable, OutputStream paramOutputStream) {
    VersionedParcelStream versionedParcelStream = new VersionedParcelStream(null, paramOutputStream);
    versionedParcelStream.writeVersionedParcelable(paramVersionedParcelable);
    versionedParcelStream.closeField();
  }
  
  public static Parcelable toParcelable(VersionedParcelable paramVersionedParcelable) {
    return new ParcelImpl(paramVersionedParcelable);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\versionedparcelable\ParcelUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */