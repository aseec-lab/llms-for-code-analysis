package androidx.core.net;

import android.net.TrafficStats;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public final class TrafficStatsCompat {
  @Deprecated
  public static void clearThreadStatsTag() {
    TrafficStats.clearThreadStatsTag();
  }
  
  @Deprecated
  public static int getThreadStatsTag() {
    return TrafficStats.getThreadStatsTag();
  }
  
  @Deprecated
  public static void incrementOperationCount(int paramInt) {
    TrafficStats.incrementOperationCount(paramInt);
  }
  
  @Deprecated
  public static void incrementOperationCount(int paramInt1, int paramInt2) {
    TrafficStats.incrementOperationCount(paramInt1, paramInt2);
  }
  
  @Deprecated
  public static void setThreadStatsTag(int paramInt) {
    TrafficStats.setThreadStatsTag(paramInt);
  }
  
  public static void tagDatagramSocket(DatagramSocket paramDatagramSocket) throws SocketException {
    if (Build.VERSION.SDK_INT >= 24) {
      TrafficStats.tagDatagramSocket(paramDatagramSocket);
    } else {
      ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.fromDatagramSocket(paramDatagramSocket);
      TrafficStats.tagSocket(new DatagramSocketWrapper(paramDatagramSocket, parcelFileDescriptor.getFileDescriptor()));
      parcelFileDescriptor.detachFd();
    } 
  }
  
  @Deprecated
  public static void tagSocket(Socket paramSocket) throws SocketException {
    TrafficStats.tagSocket(paramSocket);
  }
  
  public static void untagDatagramSocket(DatagramSocket paramDatagramSocket) throws SocketException {
    if (Build.VERSION.SDK_INT >= 24) {
      TrafficStats.untagDatagramSocket(paramDatagramSocket);
    } else {
      ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.fromDatagramSocket(paramDatagramSocket);
      TrafficStats.untagSocket(new DatagramSocketWrapper(paramDatagramSocket, parcelFileDescriptor.getFileDescriptor()));
      parcelFileDescriptor.detachFd();
    } 
  }
  
  @Deprecated
  public static void untagSocket(Socket paramSocket) throws SocketException {
    TrafficStats.untagSocket(paramSocket);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\net\TrafficStatsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */