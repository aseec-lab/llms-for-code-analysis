package androidx.core.content.pm;

import java.util.ArrayList;
import java.util.List;

public abstract class ShortcutInfoCompatSaver<T> {
  public abstract T addShortcuts(List<ShortcutInfoCompat> paramList);
  
  public List<ShortcutInfoCompat> getShortcuts() throws Exception {
    return new ArrayList<ShortcutInfoCompat>();
  }
  
  public abstract T removeAllShortcuts();
  
  public abstract T removeShortcuts(List<String> paramList);
  
  public static class NoopImpl extends ShortcutInfoCompatSaver<Void> {
    public Void addShortcuts(List<ShortcutInfoCompat> param1List) {
      return null;
    }
    
    public Void removeAllShortcuts() {
      return null;
    }
    
    public Void removeShortcuts(List<String> param1List) {
      return null;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\content\pm\ShortcutInfoCompatSaver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */