package okhttp3;

import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.annotation.Nullable;

public final class Route {
  final Address address;
  
  final InetSocketAddress inetSocketAddress;
  
  final Proxy proxy;
  
  public Route(Address paramAddress, Proxy paramProxy, InetSocketAddress paramInetSocketAddress) {
    if (paramAddress != null) {
      if (paramProxy != null) {
        if (paramInetSocketAddress != null) {
          this.address = paramAddress;
          this.proxy = paramProxy;
          this.inetSocketAddress = paramInetSocketAddress;
          return;
        } 
        throw new NullPointerException("inetSocketAddress == null");
      } 
      throw new NullPointerException("proxy == null");
    } 
    throw new NullPointerException("address == null");
  }
  
  public Address address() {
    return this.address;
  }
  
  public boolean equals(@Nullable Object paramObject) {
    if (paramObject instanceof Route) {
      paramObject = paramObject;
      if (((Route)paramObject).address.equals(this.address) && ((Route)paramObject).proxy.equals(this.proxy) && ((Route)paramObject).inetSocketAddress.equals(this.inetSocketAddress))
        return true; 
    } 
    return false;
  }
  
  public int hashCode() {
    return ((527 + this.address.hashCode()) * 31 + this.proxy.hashCode()) * 31 + this.inetSocketAddress.hashCode();
  }
  
  public Proxy proxy() {
    return this.proxy;
  }
  
  public boolean requiresTunnel() {
    boolean bool;
    if (this.address.sslSocketFactory != null && this.proxy.type() == Proxy.Type.HTTP) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public InetSocketAddress socketAddress() {
    return this.inetSocketAddress;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Route{");
    stringBuilder.append(this.inetSocketAddress);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\Route.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */