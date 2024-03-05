package androidx.core.content.pm;

import android.content.pm.PackageInfo;
import android.os.Build;

public final class PackageInfoCompat {
  public static long getLongVersionCode(PackageInfo paramPackageInfo) {
    return (Build.VERSION.SDK_INT >= 28) ? paramPackageInfo.getLongVersionCode() : paramPackageInfo.versionCode;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\pm\PackageInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */