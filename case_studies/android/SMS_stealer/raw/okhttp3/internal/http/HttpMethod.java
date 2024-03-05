package okhttp3.internal.http;

public final class HttpMethod {
  public static boolean invalidatesCache(String paramString) {
    return (paramString.equals("POST") || paramString.equals("PATCH") || paramString.equals("PUT") || paramString.equals("DELETE") || paramString.equals("MOVE"));
  }
  
  public static boolean permitsRequestBody(String paramString) {
    boolean bool;
    if (!paramString.equals("GET") && !paramString.equals("HEAD")) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean redirectsToGet(String paramString) {
    return paramString.equals("PROPFIND") ^ true;
  }
  
  public static boolean redirectsWithBody(String paramString) {
    return paramString.equals("PROPFIND");
  }
  
  public static boolean requiresRequestBody(String paramString) {
    return (paramString.equals("POST") || paramString.equals("PUT") || paramString.equals("PATCH") || paramString.equals("PROPPATCH") || paramString.equals("REPORT"));
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\okhttp3\internal\http\HttpMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */