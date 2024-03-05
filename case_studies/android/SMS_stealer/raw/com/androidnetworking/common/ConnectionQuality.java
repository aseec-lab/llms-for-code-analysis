package com.androidnetworking.common;

public enum ConnectionQuality {
  EXCELLENT, GOOD, MODERATE, POOR, UNKNOWN;
  
  private static final ConnectionQuality[] $VALUES;
  
  static {
    MODERATE = new ConnectionQuality("MODERATE", 1);
    GOOD = new ConnectionQuality("GOOD", 2);
    EXCELLENT = new ConnectionQuality("EXCELLENT", 3);
    ConnectionQuality connectionQuality = new ConnectionQuality("UNKNOWN", 4);
    UNKNOWN = connectionQuality;
    $VALUES = new ConnectionQuality[] { POOR, MODERATE, GOOD, EXCELLENT, connectionQuality };
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\androidnetworking\common\ConnectionQuality.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */