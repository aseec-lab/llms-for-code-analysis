package okhttp3.internal.platform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import okhttp3.internal.Util;

class JdkWithJettyBootPlatform extends Platform {
  private final Class<?> clientProviderClass;
  
  private final Method getMethod;
  
  private final Method putMethod;
  
  private final Method removeMethod;
  
  private final Class<?> serverProviderClass;
  
  JdkWithJettyBootPlatform(Method paramMethod1, Method paramMethod2, Method paramMethod3, Class<?> paramClass1, Class<?> paramClass2) {
    this.putMethod = paramMethod1;
    this.getMethod = paramMethod2;
    this.removeMethod = paramMethod3;
    this.clientProviderClass = paramClass1;
    this.serverProviderClass = paramClass2;
  }
  
  public static Platform buildIfSupported() {
    try {
      Class<?> clazz1 = Class.forName("org.eclipse.jetty.alpn.ALPN");
      StringBuilder stringBuilder1 = new StringBuilder();
      this();
      stringBuilder1.append("org.eclipse.jetty.alpn.ALPN");
      stringBuilder1.append("$Provider");
      Class<?> clazz2 = Class.forName(stringBuilder1.toString());
      StringBuilder stringBuilder2 = new StringBuilder();
      this();
      stringBuilder2.append("org.eclipse.jetty.alpn.ALPN");
      stringBuilder2.append("$ClientProvider");
      Class<?> clazz3 = Class.forName(stringBuilder2.toString());
      StringBuilder stringBuilder3 = new StringBuilder();
      this();
      stringBuilder3.append("org.eclipse.jetty.alpn.ALPN");
      stringBuilder3.append("$ServerProvider");
      Class<?> clazz4 = Class.forName(stringBuilder3.toString());
      return new JdkWithJettyBootPlatform(clazz1.getMethod("put", new Class[] { SSLSocket.class, clazz2 }), clazz1.getMethod("get", new Class[] { SSLSocket.class }), clazz1.getMethod("remove", new Class[] { SSLSocket.class }), clazz3, clazz4);
    } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
      return null;
    } 
  }
  
  public void afterHandshake(SSLSocket paramSSLSocket) {
    try {
      this.removeMethod.invoke(null, new Object[] { paramSSLSocket });
      return;
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw Util.assertionError("unable to remove alpn", invocationTargetException);
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList) {
    List<String> list = alpnProtocolNames(paramList);
    try {
      ClassLoader classLoader = Platform.class.getClassLoader();
      Class<?> clazz2 = this.clientProviderClass;
      Class<?> clazz1 = this.serverProviderClass;
      JettyNegoProvider jettyNegoProvider = new JettyNegoProvider();
      this(list);
      Object object = Proxy.newProxyInstance(classLoader, new Class[] { clazz2, clazz1 }, jettyNegoProvider);
      this.putMethod.invoke(null, new Object[] { paramSSLSocket, object });
      return;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (IllegalAccessException illegalAccessException) {}
    throw Util.assertionError("unable to set alpn", illegalAccessException);
  }
  
  @Nullable
  public String getSelectedProtocol(SSLSocket paramSSLSocket) {
    try {
      String str;
      Method method = this.getMethod;
      JettyNegoProvider jettyNegoProvider2 = null;
      JettyNegoProvider jettyNegoProvider1 = (JettyNegoProvider)Proxy.getInvocationHandler(method.invoke(null, new Object[] { paramSSLSocket }));
      if (!jettyNegoProvider1.unsupported && jettyNegoProvider1.selected == null) {
        Platform.get().log(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
        return null;
      } 
      if (jettyNegoProvider1.unsupported) {
        jettyNegoProvider1 = jettyNegoProvider2;
      } else {
        str = jettyNegoProvider1.selected;
      } 
      return str;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (IllegalAccessException illegalAccessException) {}
    throw Util.assertionError("unable to get selected protocol", illegalAccessException);
  }
  
  private static class JettyNegoProvider implements InvocationHandler {
    private final List<String> protocols;
    
    String selected;
    
    boolean unsupported;
    
    JettyNegoProvider(List<String> param1List) {
      this.protocols = param1List;
    }
    
    public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable {
      String str = param1Method.getName();
      Class<?> clazz = param1Method.getReturnType();
      param1Object = param1ArrayOfObject;
      if (param1ArrayOfObject == null)
        param1Object = Util.EMPTY_STRING_ARRAY; 
      if (str.equals("supports") && boolean.class == clazz)
        return Boolean.valueOf(true); 
      if (str.equals("unsupported") && void.class == clazz) {
        this.unsupported = true;
        return null;
      } 
      if (str.equals("protocols") && param1Object.length == 0)
        return this.protocols; 
      if ((str.equals("selectProtocol") || str.equals("select")) && String.class == clazz && param1Object.length == 1 && param1Object[0] instanceof List) {
        param1Object = param1Object[0];
        int i = param1Object.size();
        for (byte b = 0; b < i; b++) {
          if (this.protocols.contains(param1Object.get(b))) {
            param1Object = param1Object.get(b);
            this.selected = (String)param1Object;
            return param1Object;
          } 
        } 
        param1Object = this.protocols.get(0);
        this.selected = (String)param1Object;
        return param1Object;
      } 
      if ((str.equals("protocolSelected") || str.equals("selected")) && param1Object.length == 1) {
        this.selected = (String)param1Object[0];
        return null;
      } 
      return param1Method.invoke(this, (Object[])param1Object);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\platform\JdkWithJettyBootPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */