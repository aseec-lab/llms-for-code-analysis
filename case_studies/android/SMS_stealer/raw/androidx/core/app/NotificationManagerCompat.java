package androidx.core.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class NotificationManagerCompat {
  public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
  
  private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
  
  public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
  
  public static final int IMPORTANCE_DEFAULT = 3;
  
  public static final int IMPORTANCE_HIGH = 4;
  
  public static final int IMPORTANCE_LOW = 2;
  
  public static final int IMPORTANCE_MAX = 5;
  
  public static final int IMPORTANCE_MIN = 1;
  
  public static final int IMPORTANCE_NONE = 0;
  
  public static final int IMPORTANCE_UNSPECIFIED = -1000;
  
  static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
  
  private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
  
  private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
  
  private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
  
  private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
  
  private static final String TAG = "NotifManCompat";
  
  private static Set<String> sEnabledNotificationListenerPackages;
  
  private static String sEnabledNotificationListeners;
  
  private static final Object sEnabledNotificationListenersLock = new Object();
  
  private static final Object sLock;
  
  private static SideChannelManager sSideChannelManager;
  
  private final Context mContext;
  
  private final NotificationManager mNotificationManager;
  
  static {
    sEnabledNotificationListenerPackages = new HashSet<String>();
    sLock = new Object();
  }
  
  private NotificationManagerCompat(Context paramContext) {
    this.mContext = paramContext;
    this.mNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
  }
  
  public static NotificationManagerCompat from(Context paramContext) {
    return new NotificationManagerCompat(paramContext);
  }
  
  public static Set<String> getEnabledListenerPackages(Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   4: ldc 'enabled_notification_listeners'
    //   6: invokestatic getString : (Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;
    //   9: astore #4
    //   11: getstatic androidx/core/app/NotificationManagerCompat.sEnabledNotificationListenersLock : Ljava/lang/Object;
    //   14: astore_0
    //   15: aload_0
    //   16: monitorenter
    //   17: aload #4
    //   19: ifnull -> 106
    //   22: aload #4
    //   24: getstatic androidx/core/app/NotificationManagerCompat.sEnabledNotificationListeners : Ljava/lang/String;
    //   27: invokevirtual equals : (Ljava/lang/Object;)Z
    //   30: ifne -> 106
    //   33: aload #4
    //   35: ldc ':'
    //   37: iconst_m1
    //   38: invokevirtual split : (Ljava/lang/String;I)[Ljava/lang/String;
    //   41: astore_3
    //   42: new java/util/HashSet
    //   45: astore #5
    //   47: aload #5
    //   49: aload_3
    //   50: arraylength
    //   51: invokespecial <init> : (I)V
    //   54: aload_3
    //   55: arraylength
    //   56: istore_2
    //   57: iconst_0
    //   58: istore_1
    //   59: iload_1
    //   60: iload_2
    //   61: if_icmpge -> 96
    //   64: aload_3
    //   65: iload_1
    //   66: aaload
    //   67: invokestatic unflattenFromString : (Ljava/lang/String;)Landroid/content/ComponentName;
    //   70: astore #6
    //   72: aload #6
    //   74: ifnull -> 90
    //   77: aload #5
    //   79: aload #6
    //   81: invokevirtual getPackageName : ()Ljava/lang/String;
    //   84: invokeinterface add : (Ljava/lang/Object;)Z
    //   89: pop
    //   90: iinc #1, 1
    //   93: goto -> 59
    //   96: aload #5
    //   98: putstatic androidx/core/app/NotificationManagerCompat.sEnabledNotificationListenerPackages : Ljava/util/Set;
    //   101: aload #4
    //   103: putstatic androidx/core/app/NotificationManagerCompat.sEnabledNotificationListeners : Ljava/lang/String;
    //   106: getstatic androidx/core/app/NotificationManagerCompat.sEnabledNotificationListenerPackages : Ljava/util/Set;
    //   109: astore_3
    //   110: aload_0
    //   111: monitorexit
    //   112: aload_3
    //   113: areturn
    //   114: astore_3
    //   115: aload_0
    //   116: monitorexit
    //   117: aload_3
    //   118: athrow
    // Exception table:
    //   from	to	target	type
    //   22	57	114	finally
    //   64	72	114	finally
    //   77	90	114	finally
    //   96	106	114	finally
    //   106	112	114	finally
    //   115	117	114	finally
  }
  
  private void pushSideChannelQueue(Task paramTask) {
    synchronized (sLock) {
      if (sSideChannelManager == null) {
        SideChannelManager sideChannelManager = new SideChannelManager();
        this(this.mContext.getApplicationContext());
        sSideChannelManager = sideChannelManager;
      } 
      sSideChannelManager.queueTask(paramTask);
      return;
    } 
  }
  
  private static boolean useSideChannelForNotification(Notification paramNotification) {
    boolean bool;
    Bundle bundle = NotificationCompat.getExtras(paramNotification);
    if (bundle != null && bundle.getBoolean("android.support.useSideChannel")) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean areNotificationsEnabled() {
    if (Build.VERSION.SDK_INT >= 24)
      return this.mNotificationManager.areNotificationsEnabled(); 
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (i >= 19) {
      AppOpsManager appOpsManager = (AppOpsManager)this.mContext.getSystemService("appops");
      ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
      String str = this.mContext.getApplicationContext().getPackageName();
      i = applicationInfo.uid;
      try {
        Class<?> clazz = Class.forName(AppOpsManager.class.getName());
        i = ((Integer)clazz.getMethod("checkOpNoThrow", new Class[] { int.class, int.class, String.class }).invoke(appOpsManager, new Object[] { Integer.valueOf(((Integer)clazz.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), str })).intValue();
        if (i == 0) {
          bool1 = bool2;
        } else {
          bool1 = false;
        } 
      } catch (ClassNotFoundException|NoSuchMethodException|NoSuchFieldException|java.lang.reflect.InvocationTargetException|IllegalAccessException|RuntimeException classNotFoundException) {
        bool1 = bool2;
      } 
    } 
    return bool1;
  }
  
  public void cancel(int paramInt) {
    cancel(null, paramInt);
  }
  
  public void cancel(String paramString, int paramInt) {
    this.mNotificationManager.cancel(paramString, paramInt);
    if (Build.VERSION.SDK_INT <= 19)
      pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), paramInt, paramString)); 
  }
  
  public void cancelAll() {
    this.mNotificationManager.cancelAll();
    if (Build.VERSION.SDK_INT <= 19)
      pushSideChannelQueue(new CancelTask(this.mContext.getPackageName())); 
  }
  
  public void createNotificationChannel(NotificationChannel paramNotificationChannel) {
    if (Build.VERSION.SDK_INT >= 26)
      this.mNotificationManager.createNotificationChannel(paramNotificationChannel); 
  }
  
  public void createNotificationChannelGroup(NotificationChannelGroup paramNotificationChannelGroup) {
    if (Build.VERSION.SDK_INT >= 26)
      this.mNotificationManager.createNotificationChannelGroup(paramNotificationChannelGroup); 
  }
  
  public void createNotificationChannelGroups(List<NotificationChannelGroup> paramList) {
    if (Build.VERSION.SDK_INT >= 26)
      this.mNotificationManager.createNotificationChannelGroups(paramList); 
  }
  
  public void createNotificationChannels(List<NotificationChannel> paramList) {
    if (Build.VERSION.SDK_INT >= 26)
      this.mNotificationManager.createNotificationChannels(paramList); 
  }
  
  public void deleteNotificationChannel(String paramString) {
    if (Build.VERSION.SDK_INT >= 26)
      this.mNotificationManager.deleteNotificationChannel(paramString); 
  }
  
  public void deleteNotificationChannelGroup(String paramString) {
    if (Build.VERSION.SDK_INT >= 26)
      this.mNotificationManager.deleteNotificationChannelGroup(paramString); 
  }
  
  public int getImportance() {
    return (Build.VERSION.SDK_INT >= 24) ? this.mNotificationManager.getImportance() : -1000;
  }
  
  public NotificationChannel getNotificationChannel(String paramString) {
    return (Build.VERSION.SDK_INT >= 26) ? this.mNotificationManager.getNotificationChannel(paramString) : null;
  }
  
  public NotificationChannelGroup getNotificationChannelGroup(String paramString) {
    if (Build.VERSION.SDK_INT >= 28)
      return this.mNotificationManager.getNotificationChannelGroup(paramString); 
    if (Build.VERSION.SDK_INT >= 26)
      for (NotificationChannelGroup notificationChannelGroup : getNotificationChannelGroups()) {
        if (notificationChannelGroup.getId().equals(paramString))
          return notificationChannelGroup; 
      }  
    return null;
  }
  
  public List<NotificationChannelGroup> getNotificationChannelGroups() {
    return (Build.VERSION.SDK_INT >= 26) ? this.mNotificationManager.getNotificationChannelGroups() : Collections.emptyList();
  }
  
  public List<NotificationChannel> getNotificationChannels() {
    return (Build.VERSION.SDK_INT >= 26) ? this.mNotificationManager.getNotificationChannels() : Collections.emptyList();
  }
  
  public void notify(int paramInt, Notification paramNotification) {
    notify(null, paramInt, paramNotification);
  }
  
  public void notify(String paramString, int paramInt, Notification paramNotification) {
    if (useSideChannelForNotification(paramNotification)) {
      pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), paramInt, paramString, paramNotification));
      this.mNotificationManager.cancel(paramString, paramInt);
    } else {
      this.mNotificationManager.notify(paramString, paramInt, paramNotification);
    } 
  }
  
  private static class CancelTask implements Task {
    final boolean all;
    
    final int id;
    
    final String packageName;
    
    final String tag;
    
    CancelTask(String param1String) {
      this.packageName = param1String;
      this.id = 0;
      this.tag = null;
      this.all = true;
    }
    
    CancelTask(String param1String1, int param1Int, String param1String2) {
      this.packageName = param1String1;
      this.id = param1Int;
      this.tag = param1String2;
      this.all = false;
    }
    
    public void send(INotificationSideChannel param1INotificationSideChannel) throws RemoteException {
      if (this.all) {
        param1INotificationSideChannel.cancelAll(this.packageName);
      } else {
        param1INotificationSideChannel.cancel(this.packageName, this.id, this.tag);
      } 
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("CancelTask[");
      stringBuilder.append("packageName:");
      stringBuilder.append(this.packageName);
      stringBuilder.append(", id:");
      stringBuilder.append(this.id);
      stringBuilder.append(", tag:");
      stringBuilder.append(this.tag);
      stringBuilder.append(", all:");
      stringBuilder.append(this.all);
      stringBuilder.append("]");
      return stringBuilder.toString();
    }
  }
  
  private static class NotifyTask implements Task {
    final int id;
    
    final Notification notif;
    
    final String packageName;
    
    final String tag;
    
    NotifyTask(String param1String1, int param1Int, String param1String2, Notification param1Notification) {
      this.packageName = param1String1;
      this.id = param1Int;
      this.tag = param1String2;
      this.notif = param1Notification;
    }
    
    public void send(INotificationSideChannel param1INotificationSideChannel) throws RemoteException {
      param1INotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("NotifyTask[");
      stringBuilder.append("packageName:");
      stringBuilder.append(this.packageName);
      stringBuilder.append(", id:");
      stringBuilder.append(this.id);
      stringBuilder.append(", tag:");
      stringBuilder.append(this.tag);
      stringBuilder.append("]");
      return stringBuilder.toString();
    }
  }
  
  private static class ServiceConnectedEvent {
    final ComponentName componentName;
    
    final IBinder iBinder;
    
    ServiceConnectedEvent(ComponentName param1ComponentName, IBinder param1IBinder) {
      this.componentName = param1ComponentName;
      this.iBinder = param1IBinder;
    }
  }
  
  private static class SideChannelManager implements Handler.Callback, ServiceConnection {
    private static final int MSG_QUEUE_TASK = 0;
    
    private static final int MSG_RETRY_LISTENER_QUEUE = 3;
    
    private static final int MSG_SERVICE_CONNECTED = 1;
    
    private static final int MSG_SERVICE_DISCONNECTED = 2;
    
    private Set<String> mCachedEnabledPackages = new HashSet<String>();
    
    private final Context mContext;
    
    private final Handler mHandler;
    
    private final HandlerThread mHandlerThread;
    
    private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap<ComponentName, ListenerRecord>();
    
    SideChannelManager(Context param1Context) {
      this.mContext = param1Context;
      HandlerThread handlerThread = new HandlerThread("NotificationManagerCompat");
      this.mHandlerThread = handlerThread;
      handlerThread.start();
      this.mHandler = new Handler(this.mHandlerThread.getLooper(), this);
    }
    
    private boolean ensureServiceBound(ListenerRecord param1ListenerRecord) {
      if (param1ListenerRecord.bound)
        return true; 
      Intent intent = (new Intent("android.support.BIND_NOTIFICATION_SIDE_CHANNEL")).setComponent(param1ListenerRecord.componentName);
      param1ListenerRecord.bound = this.mContext.bindService(intent, this, 33);
      if (param1ListenerRecord.bound) {
        param1ListenerRecord.retryCount = 0;
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to bind to listener ");
        stringBuilder.append(param1ListenerRecord.componentName);
        Log.w("NotifManCompat", stringBuilder.toString());
        this.mContext.unbindService(this);
      } 
      return param1ListenerRecord.bound;
    }
    
    private void ensureServiceUnbound(ListenerRecord param1ListenerRecord) {
      if (param1ListenerRecord.bound) {
        this.mContext.unbindService(this);
        param1ListenerRecord.bound = false;
      } 
      param1ListenerRecord.service = null;
    }
    
    private void handleQueueTask(NotificationManagerCompat.Task param1Task) {
      updateListenerMap();
      for (ListenerRecord listenerRecord : this.mRecordMap.values()) {
        listenerRecord.taskQueue.add(param1Task);
        processListenerQueue(listenerRecord);
      } 
    }
    
    private void handleRetryListenerQueue(ComponentName param1ComponentName) {
      ListenerRecord listenerRecord = this.mRecordMap.get(param1ComponentName);
      if (listenerRecord != null)
        processListenerQueue(listenerRecord); 
    }
    
    private void handleServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
      ListenerRecord listenerRecord = this.mRecordMap.get(param1ComponentName);
      if (listenerRecord != null) {
        listenerRecord.service = INotificationSideChannel.Stub.asInterface(param1IBinder);
        listenerRecord.retryCount = 0;
        processListenerQueue(listenerRecord);
      } 
    }
    
    private void handleServiceDisconnected(ComponentName param1ComponentName) {
      ListenerRecord listenerRecord = this.mRecordMap.get(param1ComponentName);
      if (listenerRecord != null)
        ensureServiceUnbound(listenerRecord); 
    }
    
    private void processListenerQueue(ListenerRecord param1ListenerRecord) {
      if (Log.isLoggable("NotifManCompat", 3)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Processing component ");
        stringBuilder.append(param1ListenerRecord.componentName);
        stringBuilder.append(", ");
        stringBuilder.append(param1ListenerRecord.taskQueue.size());
        stringBuilder.append(" queued tasks");
        Log.d("NotifManCompat", stringBuilder.toString());
      } 
      if (param1ListenerRecord.taskQueue.isEmpty())
        return; 
      if (!ensureServiceBound(param1ListenerRecord) || param1ListenerRecord.service == null) {
        scheduleListenerRetry(param1ListenerRecord);
        return;
      } 
      while (true) {
        NotificationManagerCompat.Task task = param1ListenerRecord.taskQueue.peek();
        if (task == null)
          break; 
        try {
          if (Log.isLoggable("NotifManCompat", 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("Sending task ");
            stringBuilder.append(task);
            Log.d("NotifManCompat", stringBuilder.toString());
          } 
          task.send(param1ListenerRecord.service);
          param1ListenerRecord.taskQueue.remove();
          continue;
        } catch (DeadObjectException deadObjectException) {
          if (Log.isLoggable("NotifManCompat", 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Remote service has died: ");
            stringBuilder.append(param1ListenerRecord.componentName);
            Log.d("NotifManCompat", stringBuilder.toString());
          } 
        } catch (RemoteException remoteException) {}
        if (!param1ListenerRecord.taskQueue.isEmpty())
          scheduleListenerRetry(param1ListenerRecord); 
        return;
      } 
      if (!param1ListenerRecord.taskQueue.isEmpty())
        scheduleListenerRetry(param1ListenerRecord); 
    }
    
    private void scheduleListenerRetry(ListenerRecord param1ListenerRecord) {
      if (this.mHandler.hasMessages(3, param1ListenerRecord.componentName))
        return; 
      param1ListenerRecord.retryCount++;
      if (param1ListenerRecord.retryCount > 6) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Giving up on delivering ");
        stringBuilder.append(param1ListenerRecord.taskQueue.size());
        stringBuilder.append(" tasks to ");
        stringBuilder.append(param1ListenerRecord.componentName);
        stringBuilder.append(" after ");
        stringBuilder.append(param1ListenerRecord.retryCount);
        stringBuilder.append(" retries");
        Log.w("NotifManCompat", stringBuilder.toString());
        param1ListenerRecord.taskQueue.clear();
        return;
      } 
      int i = (1 << param1ListenerRecord.retryCount - 1) * 1000;
      if (Log.isLoggable("NotifManCompat", 3)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Scheduling retry for ");
        stringBuilder.append(i);
        stringBuilder.append(" ms");
        Log.d("NotifManCompat", stringBuilder.toString());
      } 
      Message message = this.mHandler.obtainMessage(3, param1ListenerRecord.componentName);
      this.mHandler.sendMessageDelayed(message, i);
    }
    
    private void updateListenerMap() {
      Set<String> set = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
      if (set.equals(this.mCachedEnabledPackages))
        return; 
      this.mCachedEnabledPackages = set;
      List list = this.mContext.getPackageManager().queryIntentServices((new Intent()).setAction("android.support.BIND_NOTIFICATION_SIDE_CHANNEL"), 0);
      HashSet<ComponentName> hashSet = new HashSet();
      for (ResolveInfo resolveInfo : list) {
        if (!set.contains(resolveInfo.serviceInfo.packageName))
          continue; 
        ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
        if (resolveInfo.serviceInfo.permission != null) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Permission present on component ");
          stringBuilder.append(componentName);
          stringBuilder.append(", not adding listener record.");
          Log.w("NotifManCompat", stringBuilder.toString());
          continue;
        } 
        hashSet.add(componentName);
      } 
      for (ComponentName componentName : hashSet) {
        if (!this.mRecordMap.containsKey(componentName)) {
          if (Log.isLoggable("NotifManCompat", 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Adding listener record for ");
            stringBuilder.append(componentName);
            Log.d("NotifManCompat", stringBuilder.toString());
          } 
          this.mRecordMap.put(componentName, new ListenerRecord(componentName));
        } 
      } 
      Iterator<Map.Entry> iterator = this.mRecordMap.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry entry = iterator.next();
        if (!hashSet.contains(entry.getKey())) {
          if (Log.isLoggable("NotifManCompat", 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Removing listener record for ");
            stringBuilder.append(entry.getKey());
            Log.d("NotifManCompat", stringBuilder.toString());
          } 
          ensureServiceUnbound((ListenerRecord)entry.getValue());
          iterator.remove();
        } 
      } 
    }
    
    public boolean handleMessage(Message param1Message) {
      NotificationManagerCompat.ServiceConnectedEvent serviceConnectedEvent;
      int i = param1Message.what;
      if (i != 0) {
        if (i != 1) {
          if (i != 2) {
            if (i != 3)
              return false; 
            handleRetryListenerQueue((ComponentName)param1Message.obj);
            return true;
          } 
          handleServiceDisconnected((ComponentName)param1Message.obj);
          return true;
        } 
        serviceConnectedEvent = (NotificationManagerCompat.ServiceConnectedEvent)param1Message.obj;
        handleServiceConnected(serviceConnectedEvent.componentName, serviceConnectedEvent.iBinder);
        return true;
      } 
      handleQueueTask((NotificationManagerCompat.Task)((Message)serviceConnectedEvent).obj);
      return true;
    }
    
    public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
      if (Log.isLoggable("NotifManCompat", 3)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connected to service ");
        stringBuilder.append(param1ComponentName);
        Log.d("NotifManCompat", stringBuilder.toString());
      } 
      this.mHandler.obtainMessage(1, new NotificationManagerCompat.ServiceConnectedEvent(param1ComponentName, param1IBinder)).sendToTarget();
    }
    
    public void onServiceDisconnected(ComponentName param1ComponentName) {
      if (Log.isLoggable("NotifManCompat", 3)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Disconnected from service ");
        stringBuilder.append(param1ComponentName);
        Log.d("NotifManCompat", stringBuilder.toString());
      } 
      this.mHandler.obtainMessage(2, param1ComponentName).sendToTarget();
    }
    
    public void queueTask(NotificationManagerCompat.Task param1Task) {
      this.mHandler.obtainMessage(0, param1Task).sendToTarget();
    }
    
    private static class ListenerRecord {
      boolean bound = false;
      
      final ComponentName componentName;
      
      int retryCount = 0;
      
      INotificationSideChannel service;
      
      ArrayDeque<NotificationManagerCompat.Task> taskQueue = new ArrayDeque<NotificationManagerCompat.Task>();
      
      ListenerRecord(ComponentName param2ComponentName) {
        this.componentName = param2ComponentName;
      }
    }
  }
  
  private static class ListenerRecord {
    boolean bound = false;
    
    final ComponentName componentName;
    
    int retryCount = 0;
    
    INotificationSideChannel service;
    
    ArrayDeque<NotificationManagerCompat.Task> taskQueue = new ArrayDeque<NotificationManagerCompat.Task>();
    
    ListenerRecord(ComponentName param1ComponentName) {
      this.componentName = param1ComponentName;
    }
  }
  
  private static interface Task {
    void send(INotificationSideChannel param1INotificationSideChannel) throws RemoteException;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\app\NotificationManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */