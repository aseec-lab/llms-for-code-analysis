package com.androidnetworking.utils;

import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.androidnetworking.interfaces.Parser;
import com.google.gson.Gson;

public class ParseUtil {
  private static Parser.Factory mParserFactory;
  
  public static Parser.Factory getParserFactory() {
    if (mParserFactory == null)
      mParserFactory = (Parser.Factory)new GsonParserFactory(new Gson()); 
    return mParserFactory;
  }
  
  public static void setParserFactory(Parser.Factory paramFactory) {
    mParserFactory = paramFactory;
  }
  
  public static void shutDown() {
    mParserFactory = null;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworkin\\utils\ParseUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */