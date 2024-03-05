package okhttp3;

import java.io.IOException;
import javax.annotation.Nullable;

public interface Authenticator {
  public static final Authenticator NONE = new Authenticator() {
      public Request authenticate(Route param1Route, Response param1Response) {
        return null;
      }
    };
  
  @Nullable
  Request authenticate(Route paramRoute, Response paramResponse) throws IOException;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Authenticator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */