package okhttp3.internal.connection;

import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.Route;

public final class RouteDatabase {
  private final Set<Route> failedRoutes = new LinkedHashSet<Route>();
  
  public void connected(Route paramRoute) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failedRoutes : Ljava/util/Set;
    //   6: aload_1
    //   7: invokeinterface remove : (Ljava/lang/Object;)Z
    //   12: pop
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  public void failed(Route paramRoute) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failedRoutes : Ljava/util/Set;
    //   6: aload_1
    //   7: invokeinterface add : (Ljava/lang/Object;)Z
    //   12: pop
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  public boolean shouldPostpone(Route paramRoute) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield failedRoutes : Ljava/util/Set;
    //   6: aload_1
    //   7: invokeinterface contains : (Ljava/lang/Object;)Z
    //   12: istore_2
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_2
    //   16: ireturn
    //   17: astore_1
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_1
    //   21: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	17	finally
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\connection\RouteDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */