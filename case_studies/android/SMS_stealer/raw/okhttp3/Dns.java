package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public interface Dns {
  public static final Dns SYSTEM = new Dns() {
      public List<InetAddress> lookup(String param1String) throws UnknownHostException {
        if (param1String != null)
          try {
            return Arrays.asList(InetAddress.getAllByName(param1String));
          } catch (NullPointerException nullPointerException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Broken system behaviour for dns lookup of ");
            stringBuilder.append(param1String);
            UnknownHostException unknownHostException = new UnknownHostException(stringBuilder.toString());
            unknownHostException.initCause(nullPointerException);
            throw unknownHostException;
          }  
        throw new UnknownHostException("hostname == null");
      }
    };
  
  List<InetAddress> lookup(String paramString) throws UnknownHostException;
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Dns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */