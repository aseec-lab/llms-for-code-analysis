package okhttp3.internal.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;

public final class RouteSelector {
  private final Address address;
  
  private final Call call;
  
  private final EventListener eventListener;
  
  private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
  
  private int nextProxyIndex;
  
  private final List<Route> postponedRoutes = new ArrayList<Route>();
  
  private List<Proxy> proxies = Collections.emptyList();
  
  private final RouteDatabase routeDatabase;
  
  public RouteSelector(Address paramAddress, RouteDatabase paramRouteDatabase, Call paramCall, EventListener paramEventListener) {
    this.address = paramAddress;
    this.routeDatabase = paramRouteDatabase;
    this.call = paramCall;
    this.eventListener = paramEventListener;
    resetNextProxy(paramAddress.url(), paramAddress.proxy());
  }
  
  static String getHostString(InetSocketAddress paramInetSocketAddress) {
    InetAddress inetAddress = paramInetSocketAddress.getAddress();
    return (inetAddress == null) ? paramInetSocketAddress.getHostName() : inetAddress.getHostAddress();
  }
  
  private boolean hasNextProxy() {
    boolean bool;
    if (this.nextProxyIndex < this.proxies.size()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private Proxy nextProxy() throws IOException {
    if (hasNextProxy()) {
      List<Proxy> list = this.proxies;
      int i = this.nextProxyIndex;
      this.nextProxyIndex = i + 1;
      Proxy proxy = list.get(i);
      resetNextInetSocketAddress(proxy);
      return proxy;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No route to ");
    stringBuilder.append(this.address.url().host());
    stringBuilder.append("; exhausted proxy configurations: ");
    stringBuilder.append(this.proxies);
    throw new SocketException(stringBuilder.toString());
  }
  
  private void resetNextInetSocketAddress(Proxy paramProxy) throws IOException {
    int i;
    String str;
    InetAddress inetAddress;
    this.inetSocketAddresses = new ArrayList<InetSocketAddress>();
    if (paramProxy.type() == Proxy.Type.DIRECT || paramProxy.type() == Proxy.Type.SOCKS) {
      str = this.address.url().host();
      i = this.address.url().port();
    } else {
      SocketAddress socketAddress = paramProxy.address();
      if (socketAddress instanceof InetSocketAddress) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        str = getHostString(inetSocketAddress);
        i = inetSocketAddress.getPort();
      } else {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Proxy.address() is not an InetSocketAddress: ");
        stringBuilder.append(str.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    } 
    if (i >= 1 && i <= 65535) {
      if (stringBuilder.type() == Proxy.Type.SOCKS) {
        this.inetSocketAddresses.add(InetSocketAddress.createUnresolved(str, i));
      } else {
        this.eventListener.dnsStart(this.call, str);
        List<InetAddress> list = this.address.dns().lookup(str);
        if (!list.isEmpty()) {
          this.eventListener.dnsEnd(this.call, str, list);
          byte b = 0;
          int j = list.size();
          while (b < j) {
            inetAddress = list.get(b);
            this.inetSocketAddresses.add(new InetSocketAddress(inetAddress, i));
            b++;
          } 
          return;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(this.address.dns());
        stringBuilder1.append(" returned no addresses for ");
        stringBuilder1.append((String)inetAddress);
        throw new UnknownHostException(stringBuilder1.toString());
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No route to ");
    stringBuilder.append((String)inetAddress);
    stringBuilder.append(":");
    stringBuilder.append(i);
    stringBuilder.append("; port is out of range");
    throw new SocketException(stringBuilder.toString());
  }
  
  private void resetNextProxy(HttpUrl paramHttpUrl, Proxy paramProxy) {
    if (paramProxy != null) {
      this.proxies = Collections.singletonList(paramProxy);
    } else {
      List<Proxy> list = this.address.proxySelector().select(paramHttpUrl.uri());
      if (list != null && !list.isEmpty()) {
        list = Util.immutableList(list);
      } else {
        list = Util.immutableList((Object[])new Proxy[] { Proxy.NO_PROXY });
      } 
      this.proxies = list;
    } 
    this.nextProxyIndex = 0;
  }
  
  public void connectFailed(Route paramRoute, IOException paramIOException) {
    if (paramRoute.proxy().type() != Proxy.Type.DIRECT && this.address.proxySelector() != null)
      this.address.proxySelector().connectFailed(this.address.url().uri(), paramRoute.proxy().address(), paramIOException); 
    this.routeDatabase.failed(paramRoute);
  }
  
  public boolean hasNext() {
    return (hasNextProxy() || !this.postponedRoutes.isEmpty());
  }
  
  public Selection next() throws IOException {
    if (hasNext()) {
      ArrayList<Route> arrayList = new ArrayList();
      while (hasNextProxy()) {
        Proxy proxy = nextProxy();
        byte b = 0;
        int i = this.inetSocketAddresses.size();
        while (b < i) {
          Route route = new Route(this.address, proxy, this.inetSocketAddresses.get(b));
          if (this.routeDatabase.shouldPostpone(route)) {
            this.postponedRoutes.add(route);
          } else {
            arrayList.add(route);
          } 
          b++;
        } 
        if (!arrayList.isEmpty())
          break; 
      } 
      if (arrayList.isEmpty()) {
        arrayList.addAll(this.postponedRoutes);
        this.postponedRoutes.clear();
      } 
      return new Selection(arrayList);
    } 
    throw new NoSuchElementException();
  }
  
  public static final class Selection {
    private int nextRouteIndex = 0;
    
    private final List<Route> routes;
    
    Selection(List<Route> param1List) {
      this.routes = param1List;
    }
    
    public List<Route> getAll() {
      return new ArrayList<Route>(this.routes);
    }
    
    public boolean hasNext() {
      boolean bool;
      if (this.nextRouteIndex < this.routes.size()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Route next() {
      if (hasNext()) {
        List<Route> list = this.routes;
        int i = this.nextRouteIndex;
        this.nextRouteIndex = i + 1;
        return list.get(i);
      } 
      throw new NoSuchElementException();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\connection\RouteSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */