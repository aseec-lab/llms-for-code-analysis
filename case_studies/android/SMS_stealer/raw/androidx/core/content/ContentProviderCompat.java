package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;

public final class ContentProviderCompat {
  public static Context requireContext(ContentProvider paramContentProvider) {
    Context context = paramContentProvider.getContext();
    if (context != null)
      return context; 
    throw new IllegalStateException("Cannot find context from the provider.");
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\ContentProviderCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */