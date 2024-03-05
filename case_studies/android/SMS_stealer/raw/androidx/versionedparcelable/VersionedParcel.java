package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class VersionedParcel {
  private static final int EX_BAD_PARCELABLE = -2;
  
  private static final int EX_ILLEGAL_ARGUMENT = -3;
  
  private static final int EX_ILLEGAL_STATE = -5;
  
  private static final int EX_NETWORK_MAIN_THREAD = -6;
  
  private static final int EX_NULL_POINTER = -4;
  
  private static final int EX_PARCELABLE = -9;
  
  private static final int EX_SECURITY = -1;
  
  private static final int EX_UNSUPPORTED_OPERATION = -7;
  
  private static final String TAG = "VersionedParcel";
  
  private static final int TYPE_BINDER = 5;
  
  private static final int TYPE_FLOAT = 8;
  
  private static final int TYPE_INTEGER = 7;
  
  private static final int TYPE_PARCELABLE = 2;
  
  private static final int TYPE_SERIALIZABLE = 3;
  
  private static final int TYPE_STRING = 4;
  
  private static final int TYPE_VERSIONED_PARCELABLE = 1;
  
  protected final ArrayMap<String, Class> mParcelizerCache;
  
  protected final ArrayMap<String, Method> mReadCache;
  
  protected final ArrayMap<String, Method> mWriteCache;
  
  public VersionedParcel(ArrayMap<String, Method> paramArrayMap1, ArrayMap<String, Method> paramArrayMap2, ArrayMap<String, Class> paramArrayMap) {
    this.mReadCache = paramArrayMap1;
    this.mWriteCache = paramArrayMap2;
    this.mParcelizerCache = paramArrayMap;
  }
  
  private Exception createException(int paramInt, String paramString) {
    StringBuilder stringBuilder;
    switch (paramInt) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown exception code: ");
        stringBuilder.append(paramInt);
        stringBuilder.append(" msg ");
        stringBuilder.append(paramString);
        return new RuntimeException(stringBuilder.toString());
      case -1:
        return new SecurityException(paramString);
      case -2:
        return (Exception)new BadParcelableException(paramString);
      case -3:
        return new IllegalArgumentException(paramString);
      case -4:
        return new NullPointerException(paramString);
      case -5:
        return new IllegalStateException(paramString);
      case -6:
        return (Exception)new NetworkOnMainThreadException();
      case -7:
        return new UnsupportedOperationException(paramString);
      case -9:
        break;
    } 
    return readParcelable();
  }
  
  private Class findParcelClass(Class<? extends VersionedParcelable> paramClass) throws ClassNotFoundException {
    Class<?> clazz2 = (Class)this.mParcelizerCache.get(paramClass.getName());
    Class<?> clazz1 = clazz2;
    if (clazz2 == null) {
      clazz1 = Class.forName(String.format("%s.%sParcelizer", new Object[] { paramClass.getPackage().getName(), paramClass.getSimpleName() }), false, paramClass.getClassLoader());
      this.mParcelizerCache.put(paramClass.getName(), clazz1);
    } 
    return clazz1;
  }
  
  private Method getReadMethod(String paramString) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
    Method method2 = (Method)this.mReadCache.get(paramString);
    Method method1 = method2;
    if (method2 == null) {
      System.currentTimeMillis();
      method1 = Class.forName(paramString, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", new Class[] { VersionedParcel.class });
      this.mReadCache.put(paramString, method1);
    } 
    return method1;
  }
  
  protected static Throwable getRootCause(Throwable paramThrowable) {
    while (paramThrowable.getCause() != null)
      paramThrowable = paramThrowable.getCause(); 
    return paramThrowable;
  }
  
  private <T> int getType(T paramT) {
    if (paramT instanceof String)
      return 4; 
    if (paramT instanceof Parcelable)
      return 2; 
    if (paramT instanceof VersionedParcelable)
      return 1; 
    if (paramT instanceof Serializable)
      return 3; 
    if (paramT instanceof IBinder)
      return 5; 
    if (paramT instanceof Integer)
      return 7; 
    if (paramT instanceof Float)
      return 8; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramT.getClass().getName());
    stringBuilder.append(" cannot be VersionedParcelled");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private Method getWriteMethod(Class<? extends VersionedParcelable> paramClass) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
    Method method2 = (Method)this.mWriteCache.get(paramClass.getName());
    Method method1 = method2;
    if (method2 == null) {
      Class clazz = findParcelClass(paramClass);
      System.currentTimeMillis();
      method1 = clazz.getDeclaredMethod("write", new Class[] { paramClass, VersionedParcel.class });
      this.mWriteCache.put(paramClass.getName(), method1);
    } 
    return method1;
  }
  
  private <T, S extends Collection<T>> S readCollection(S paramS) {
    int i = readInt();
    if (i < 0)
      return null; 
    if (i != 0) {
      int k = readInt();
      if (i < 0)
        return null; 
      int j = i;
      if (k != 1) {
        j = i;
        if (k != 2) {
          j = i;
          if (k != 3) {
            j = i;
            if (k != 4) {
              if (k == 5)
                while (i > 0) {
                  paramS.add(readStrongBinder());
                  i--;
                }  
            } else {
              while (j > 0) {
                paramS.add(readString());
                j--;
              } 
            } 
          } else {
            while (j > 0) {
              paramS.add(readSerializable());
              j--;
            } 
          } 
        } else {
          while (j > 0) {
            paramS.add(readParcelable());
            j--;
          } 
        } 
      } else {
        while (j > 0) {
          paramS.add(readVersionedParcelable());
          j--;
        } 
      } 
    } 
    return paramS;
  }
  
  private Exception readException(int paramInt, String paramString) {
    return createException(paramInt, paramString);
  }
  
  private int readExceptionCode() {
    return readInt();
  }
  
  private <T> void writeCollection(Collection<T> paramCollection) {
    if (paramCollection == null) {
      writeInt(-1);
      return;
    } 
    int i = paramCollection.size();
    writeInt(i);
    if (i > 0) {
      i = getType(paramCollection.iterator().next());
      writeInt(i);
      switch (i) {
        default:
          return;
        case 8:
          iterator = paramCollection.iterator();
          while (iterator.hasNext())
            writeFloat(((Float)iterator.next()).floatValue()); 
        case 7:
          iterator = iterator.iterator();
          while (iterator.hasNext())
            writeInt(((Integer)iterator.next()).intValue()); 
        case 5:
          iterator = iterator.iterator();
          while (iterator.hasNext())
            writeStrongBinder((IBinder)iterator.next()); 
        case 4:
          iterator = iterator.iterator();
          while (iterator.hasNext())
            writeString((String)iterator.next()); 
        case 3:
          iterator = iterator.iterator();
          while (iterator.hasNext())
            writeSerializable((Serializable)iterator.next()); 
        case 2:
          iterator = iterator.iterator();
          while (iterator.hasNext())
            writeParcelable((Parcelable)iterator.next()); 
        case 1:
          break;
      } 
      Iterator<T> iterator = iterator.iterator();
      while (iterator.hasNext())
        writeVersionedParcelable((VersionedParcelable)iterator.next()); 
    } 
  }
  
  private <T> void writeCollection(Collection<T> paramCollection, int paramInt) {
    setOutputField(paramInt);
    writeCollection(paramCollection);
  }
  
  private void writeSerializable(Serializable paramSerializable) {
    if (paramSerializable == null) {
      writeString(null);
      return;
    } 
    String str = paramSerializable.getClass().getName();
    writeString(str);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream();
      this(byteArrayOutputStream);
      objectOutputStream.writeObject(paramSerializable);
      objectOutputStream.close();
      writeByteArray(byteArrayOutputStream.toByteArray());
      return;
    } catch (IOException iOException) {
      paramSerializable = new StringBuilder();
      paramSerializable.append("VersionedParcelable encountered IOException writing serializable object (name = ");
      paramSerializable.append(str);
      paramSerializable.append(")");
      throw new RuntimeException(paramSerializable.toString(), iOException);
    } 
  }
  
  private void writeVersionedParcelableCreator(VersionedParcelable paramVersionedParcelable) {
    try {
      Class clazz = findParcelClass((Class)paramVersionedParcelable.getClass());
      writeString(clazz.getName());
      return;
    } catch (ClassNotFoundException classNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramVersionedParcelable.getClass().getSimpleName());
      stringBuilder.append(" does not have a Parcelizer");
      throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
    } 
  }
  
  protected abstract void closeField();
  
  protected abstract VersionedParcel createSubParcel();
  
  public boolean isStream() {
    return false;
  }
  
  protected <T> T[] readArray(T[] paramArrayOfT) {
    int i = readInt();
    if (i < 0)
      return null; 
    ArrayList<IBinder> arrayList = new ArrayList(i);
    if (i != 0) {
      int k = readInt();
      if (i < 0)
        return null; 
      int j = i;
      if (k != 1) {
        j = i;
        if (k != 2) {
          j = i;
          if (k != 3) {
            j = i;
            if (k != 4) {
              if (k == 5)
                while (i > 0) {
                  arrayList.add(readStrongBinder());
                  i--;
                }  
            } else {
              while (j > 0) {
                arrayList.add(readString());
                j--;
              } 
            } 
          } else {
            while (j > 0) {
              arrayList.add(readSerializable());
              j--;
            } 
          } 
        } else {
          while (j > 0) {
            arrayList.add(readParcelable());
            j--;
          } 
        } 
      } else {
        while (j > 0) {
          arrayList.add(readVersionedParcelable());
          j--;
        } 
      } 
    } 
    return arrayList.toArray(paramArrayOfT);
  }
  
  public <T> T[] readArray(T[] paramArrayOfT, int paramInt) {
    return !readField(paramInt) ? paramArrayOfT : readArray(paramArrayOfT);
  }
  
  protected abstract boolean readBoolean();
  
  public boolean readBoolean(boolean paramBoolean, int paramInt) {
    return !readField(paramInt) ? paramBoolean : readBoolean();
  }
  
  protected boolean[] readBooleanArray() {
    int i = readInt();
    if (i < 0)
      return null; 
    boolean[] arrayOfBoolean = new boolean[i];
    for (byte b = 0; b < i; b++) {
      boolean bool;
      if (readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      arrayOfBoolean[b] = bool;
    } 
    return arrayOfBoolean;
  }
  
  public boolean[] readBooleanArray(boolean[] paramArrayOfboolean, int paramInt) {
    return !readField(paramInt) ? paramArrayOfboolean : readBooleanArray();
  }
  
  protected abstract Bundle readBundle();
  
  public Bundle readBundle(Bundle paramBundle, int paramInt) {
    return !readField(paramInt) ? paramBundle : readBundle();
  }
  
  public byte readByte(byte paramByte, int paramInt) {
    return !readField(paramInt) ? paramByte : (byte)(readInt() & 0xFF);
  }
  
  protected abstract byte[] readByteArray();
  
  public byte[] readByteArray(byte[] paramArrayOfbyte, int paramInt) {
    return !readField(paramInt) ? paramArrayOfbyte : readByteArray();
  }
  
  public char[] readCharArray(char[] paramArrayOfchar, int paramInt) {
    if (!readField(paramInt))
      return paramArrayOfchar; 
    int i = readInt();
    if (i < 0)
      return null; 
    paramArrayOfchar = new char[i];
    for (paramInt = 0; paramInt < i; paramInt++)
      paramArrayOfchar[paramInt] = (char)readInt(); 
    return paramArrayOfchar;
  }
  
  protected abstract CharSequence readCharSequence();
  
  public CharSequence readCharSequence(CharSequence paramCharSequence, int paramInt) {
    return !readField(paramInt) ? paramCharSequence : readCharSequence();
  }
  
  protected abstract double readDouble();
  
  public double readDouble(double paramDouble, int paramInt) {
    return !readField(paramInt) ? paramDouble : readDouble();
  }
  
  protected double[] readDoubleArray() {
    int i = readInt();
    if (i < 0)
      return null; 
    double[] arrayOfDouble = new double[i];
    for (byte b = 0; b < i; b++)
      arrayOfDouble[b] = readDouble(); 
    return arrayOfDouble;
  }
  
  public double[] readDoubleArray(double[] paramArrayOfdouble, int paramInt) {
    return !readField(paramInt) ? paramArrayOfdouble : readDoubleArray();
  }
  
  public Exception readException(Exception paramException, int paramInt) {
    if (!readField(paramInt))
      return paramException; 
    paramInt = readExceptionCode();
    if (paramInt != 0)
      paramException = readException(paramInt, readString()); 
    return paramException;
  }
  
  protected abstract boolean readField(int paramInt);
  
  protected abstract float readFloat();
  
  public float readFloat(float paramFloat, int paramInt) {
    return !readField(paramInt) ? paramFloat : readFloat();
  }
  
  protected float[] readFloatArray() {
    int i = readInt();
    if (i < 0)
      return null; 
    float[] arrayOfFloat = new float[i];
    for (byte b = 0; b < i; b++)
      arrayOfFloat[b] = readFloat(); 
    return arrayOfFloat;
  }
  
  public float[] readFloatArray(float[] paramArrayOffloat, int paramInt) {
    return !readField(paramInt) ? paramArrayOffloat : readFloatArray();
  }
  
  protected <T extends VersionedParcelable> T readFromParcel(String paramString, VersionedParcel paramVersionedParcel) {
    try {
      return (T)getReadMethod(paramString).invoke(null, new Object[] { paramVersionedParcel });
    } catch (IllegalAccessException illegalAccessException) {
      throw new RuntimeException("VersionedParcel encountered IllegalAccessException", illegalAccessException);
    } catch (InvocationTargetException invocationTargetException) {
      if (invocationTargetException.getCause() instanceof RuntimeException)
        throw (RuntimeException)invocationTargetException.getCause(); 
      throw new RuntimeException("VersionedParcel encountered InvocationTargetException", invocationTargetException);
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", noSuchMethodException);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", classNotFoundException);
    } 
  }
  
  protected abstract int readInt();
  
  public int readInt(int paramInt1, int paramInt2) {
    return !readField(paramInt2) ? paramInt1 : readInt();
  }
  
  protected int[] readIntArray() {
    int i = readInt();
    if (i < 0)
      return null; 
    int[] arrayOfInt = new int[i];
    for (byte b = 0; b < i; b++)
      arrayOfInt[b] = readInt(); 
    return arrayOfInt;
  }
  
  public int[] readIntArray(int[] paramArrayOfint, int paramInt) {
    return !readField(paramInt) ? paramArrayOfint : readIntArray();
  }
  
  public <T> List<T> readList(List<T> paramList, int paramInt) {
    return !readField(paramInt) ? paramList : readCollection(new ArrayList<T>());
  }
  
  protected abstract long readLong();
  
  public long readLong(long paramLong, int paramInt) {
    return !readField(paramInt) ? paramLong : readLong();
  }
  
  protected long[] readLongArray() {
    int i = readInt();
    if (i < 0)
      return null; 
    long[] arrayOfLong = new long[i];
    for (byte b = 0; b < i; b++)
      arrayOfLong[b] = readLong(); 
    return arrayOfLong;
  }
  
  public long[] readLongArray(long[] paramArrayOflong, int paramInt) {
    return !readField(paramInt) ? paramArrayOflong : readLongArray();
  }
  
  public <K, V> Map<K, V> readMap(Map<K, V> paramMap, int paramInt) {
    if (!readField(paramInt))
      return paramMap; 
    int i = readInt();
    if (i < 0)
      return null; 
    ArrayMap arrayMap = new ArrayMap();
    if (i == 0)
      return (Map<K, V>)arrayMap; 
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList1 = new ArrayList();
    readCollection(arrayList2);
    readCollection(arrayList1);
    for (paramInt = 0; paramInt < i; paramInt++)
      arrayMap.put(arrayList2.get(paramInt), arrayList1.get(paramInt)); 
    return (Map<K, V>)arrayMap;
  }
  
  protected abstract <T extends Parcelable> T readParcelable();
  
  public <T extends Parcelable> T readParcelable(T paramT, int paramInt) {
    return !readField(paramInt) ? paramT : readParcelable();
  }
  
  protected Serializable readSerializable() {
    String str = readString();
    if (str == null)
      return null; 
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readByteArray());
    try {
      ObjectInputStream objectInputStream = new ObjectInputStream() {
          final VersionedParcel this$0;
          
          protected Class<?> resolveClass(ObjectStreamClass param1ObjectStreamClass) throws IOException, ClassNotFoundException {
            Class<?> clazz = Class.forName(param1ObjectStreamClass.getName(), false, getClass().getClassLoader());
            return (clazz != null) ? clazz : super.resolveClass(param1ObjectStreamClass);
          }
        };
      super(this, byteArrayInputStream);
      return (Serializable)objectInputStream.readObject();
    } catch (IOException iOException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("VersionedParcelable encountered IOException reading a Serializable object (name = ");
      stringBuilder.append(str);
      stringBuilder.append(")");
      throw new RuntimeException(stringBuilder.toString(), iOException);
    } catch (ClassNotFoundException classNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = ");
      stringBuilder.append(str);
      stringBuilder.append(")");
      throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
    } 
  }
  
  public <T> Set<T> readSet(Set<T> paramSet, int paramInt) {
    return !readField(paramInt) ? paramSet : (Set<T>)readCollection(new ArraySet());
  }
  
  public Size readSize(Size paramSize, int paramInt) {
    return !readField(paramInt) ? paramSize : (readBoolean() ? new Size(readInt(), readInt()) : null);
  }
  
  public SizeF readSizeF(SizeF paramSizeF, int paramInt) {
    return !readField(paramInt) ? paramSizeF : (readBoolean() ? new SizeF(readFloat(), readFloat()) : null);
  }
  
  public SparseBooleanArray readSparseBooleanArray(SparseBooleanArray paramSparseBooleanArray, int paramInt) {
    if (!readField(paramInt))
      return paramSparseBooleanArray; 
    int i = readInt();
    if (i < 0)
      return null; 
    paramSparseBooleanArray = new SparseBooleanArray(i);
    for (paramInt = 0; paramInt < i; paramInt++)
      paramSparseBooleanArray.put(readInt(), readBoolean()); 
    return paramSparseBooleanArray;
  }
  
  protected abstract String readString();
  
  public String readString(String paramString, int paramInt) {
    return !readField(paramInt) ? paramString : readString();
  }
  
  protected abstract IBinder readStrongBinder();
  
  public IBinder readStrongBinder(IBinder paramIBinder, int paramInt) {
    return !readField(paramInt) ? paramIBinder : readStrongBinder();
  }
  
  protected <T extends VersionedParcelable> T readVersionedParcelable() {
    String str = readString();
    return (str == null) ? null : readFromParcel(str, createSubParcel());
  }
  
  public <T extends VersionedParcelable> T readVersionedParcelable(T paramT, int paramInt) {
    return !readField(paramInt) ? paramT : readVersionedParcelable();
  }
  
  protected abstract void setOutputField(int paramInt);
  
  public void setSerializationFlags(boolean paramBoolean1, boolean paramBoolean2) {}
  
  protected <T> void writeArray(T[] paramArrayOfT) {
    if (paramArrayOfT == null) {
      writeInt(-1);
      return;
    } 
    int i = paramArrayOfT.length;
    writeInt(i);
    if (i > 0) {
      byte b3 = 0;
      byte b4 = 0;
      byte b5 = 0;
      byte b1 = 0;
      byte b2 = 0;
      int j = getType(paramArrayOfT[0]);
      writeInt(j);
      if (j != 1) {
        b1 = b5;
        if (j != 2) {
          b1 = b4;
          if (j != 3) {
            b1 = b3;
            if (j != 4) {
              b1 = b2;
              if (j == 5)
                while (b1 < i) {
                  writeStrongBinder((IBinder)paramArrayOfT[b1]);
                  b1++;
                }  
            } else {
              while (b1 < i) {
                writeString((String)paramArrayOfT[b1]);
                b1++;
              } 
            } 
          } else {
            while (b1 < i) {
              writeSerializable((Serializable)paramArrayOfT[b1]);
              b1++;
            } 
          } 
        } else {
          while (b1 < i) {
            writeParcelable((Parcelable)paramArrayOfT[b1]);
            b1++;
          } 
        } 
      } else {
        while (b1 < i) {
          writeVersionedParcelable((VersionedParcelable)paramArrayOfT[b1]);
          b1++;
        } 
      } 
    } 
  }
  
  public <T> void writeArray(T[] paramArrayOfT, int paramInt) {
    setOutputField(paramInt);
    writeArray(paramArrayOfT);
  }
  
  protected abstract void writeBoolean(boolean paramBoolean);
  
  public void writeBoolean(boolean paramBoolean, int paramInt) {
    setOutputField(paramInt);
    writeBoolean(paramBoolean);
  }
  
  protected void writeBooleanArray(boolean[] paramArrayOfboolean) {
    if (paramArrayOfboolean != null) {
      int i = paramArrayOfboolean.length;
      writeInt(i);
      for (byte b = 0; b < i; b++)
        writeInt(paramArrayOfboolean[b]); 
    } else {
      writeInt(-1);
    } 
  }
  
  public void writeBooleanArray(boolean[] paramArrayOfboolean, int paramInt) {
    setOutputField(paramInt);
    writeBooleanArray(paramArrayOfboolean);
  }
  
  protected abstract void writeBundle(Bundle paramBundle);
  
  public void writeBundle(Bundle paramBundle, int paramInt) {
    setOutputField(paramInt);
    writeBundle(paramBundle);
  }
  
  public void writeByte(byte paramByte, int paramInt) {
    setOutputField(paramInt);
    writeInt(paramByte);
  }
  
  protected abstract void writeByteArray(byte[] paramArrayOfbyte);
  
  public void writeByteArray(byte[] paramArrayOfbyte, int paramInt) {
    setOutputField(paramInt);
    writeByteArray(paramArrayOfbyte);
  }
  
  protected abstract void writeByteArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  public void writeByteArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
    setOutputField(paramInt3);
    writeByteArray(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void writeCharArray(char[] paramArrayOfchar, int paramInt) {
    setOutputField(paramInt);
    if (paramArrayOfchar != null) {
      int i = paramArrayOfchar.length;
      writeInt(i);
      for (paramInt = 0; paramInt < i; paramInt++)
        writeInt(paramArrayOfchar[paramInt]); 
    } else {
      writeInt(-1);
    } 
  }
  
  protected abstract void writeCharSequence(CharSequence paramCharSequence);
  
  public void writeCharSequence(CharSequence paramCharSequence, int paramInt) {
    setOutputField(paramInt);
    writeCharSequence(paramCharSequence);
  }
  
  protected abstract void writeDouble(double paramDouble);
  
  public void writeDouble(double paramDouble, int paramInt) {
    setOutputField(paramInt);
    writeDouble(paramDouble);
  }
  
  protected void writeDoubleArray(double[] paramArrayOfdouble) {
    if (paramArrayOfdouble != null) {
      int i = paramArrayOfdouble.length;
      writeInt(i);
      for (byte b = 0; b < i; b++)
        writeDouble(paramArrayOfdouble[b]); 
    } else {
      writeInt(-1);
    } 
  }
  
  public void writeDoubleArray(double[] paramArrayOfdouble, int paramInt) {
    setOutputField(paramInt);
    writeDoubleArray(paramArrayOfdouble);
  }
  
  public void writeException(Exception paramException, int paramInt) {
    setOutputField(paramInt);
    if (paramException == null) {
      writeNoException();
      return;
    } 
    paramInt = 0;
    if (paramException instanceof Parcelable && paramException.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
      paramInt = -9;
    } else if (paramException instanceof SecurityException) {
      paramInt = -1;
    } else if (paramException instanceof BadParcelableException) {
      paramInt = -2;
    } else if (paramException instanceof IllegalArgumentException) {
      paramInt = -3;
    } else if (paramException instanceof NullPointerException) {
      paramInt = -4;
    } else if (paramException instanceof IllegalStateException) {
      paramInt = -5;
    } else if (paramException instanceof NetworkOnMainThreadException) {
      paramInt = -6;
    } else if (paramException instanceof UnsupportedOperationException) {
      paramInt = -7;
    } 
    writeInt(paramInt);
    if (paramInt == 0) {
      if (paramException instanceof RuntimeException)
        throw (RuntimeException)paramException; 
      throw new RuntimeException(paramException);
    } 
    writeString(paramException.getMessage());
    if (paramInt == -9)
      writeParcelable((Parcelable)paramException); 
  }
  
  protected abstract void writeFloat(float paramFloat);
  
  public void writeFloat(float paramFloat, int paramInt) {
    setOutputField(paramInt);
    writeFloat(paramFloat);
  }
  
  protected void writeFloatArray(float[] paramArrayOffloat) {
    if (paramArrayOffloat != null) {
      int i = paramArrayOffloat.length;
      writeInt(i);
      for (byte b = 0; b < i; b++)
        writeFloat(paramArrayOffloat[b]); 
    } else {
      writeInt(-1);
    } 
  }
  
  public void writeFloatArray(float[] paramArrayOffloat, int paramInt) {
    setOutputField(paramInt);
    writeFloatArray(paramArrayOffloat);
  }
  
  protected abstract void writeInt(int paramInt);
  
  public void writeInt(int paramInt1, int paramInt2) {
    setOutputField(paramInt2);
    writeInt(paramInt1);
  }
  
  protected void writeIntArray(int[] paramArrayOfint) {
    if (paramArrayOfint != null) {
      int i = paramArrayOfint.length;
      writeInt(i);
      for (byte b = 0; b < i; b++)
        writeInt(paramArrayOfint[b]); 
    } else {
      writeInt(-1);
    } 
  }
  
  public void writeIntArray(int[] paramArrayOfint, int paramInt) {
    setOutputField(paramInt);
    writeIntArray(paramArrayOfint);
  }
  
  public <T> void writeList(List<T> paramList, int paramInt) {
    writeCollection(paramList, paramInt);
  }
  
  protected abstract void writeLong(long paramLong);
  
  public void writeLong(long paramLong, int paramInt) {
    setOutputField(paramInt);
    writeLong(paramLong);
  }
  
  protected void writeLongArray(long[] paramArrayOflong) {
    if (paramArrayOflong != null) {
      int i = paramArrayOflong.length;
      writeInt(i);
      for (byte b = 0; b < i; b++)
        writeLong(paramArrayOflong[b]); 
    } else {
      writeInt(-1);
    } 
  }
  
  public void writeLongArray(long[] paramArrayOflong, int paramInt) {
    setOutputField(paramInt);
    writeLongArray(paramArrayOflong);
  }
  
  public <K, V> void writeMap(Map<K, V> paramMap, int paramInt) {
    setOutputField(paramInt);
    if (paramMap == null) {
      writeInt(-1);
      return;
    } 
    paramInt = paramMap.size();
    writeInt(paramInt);
    if (paramInt == 0)
      return; 
    ArrayList<?> arrayList2 = new ArrayList();
    ArrayList<?> arrayList1 = new ArrayList();
    for (Map.Entry<K, V> entry : paramMap.entrySet()) {
      arrayList2.add(entry.getKey());
      arrayList1.add(entry.getValue());
    } 
    writeCollection(arrayList2);
    writeCollection(arrayList1);
  }
  
  protected void writeNoException() {
    writeInt(0);
  }
  
  protected abstract void writeParcelable(Parcelable paramParcelable);
  
  public void writeParcelable(Parcelable paramParcelable, int paramInt) {
    setOutputField(paramInt);
    writeParcelable(paramParcelable);
  }
  
  public void writeSerializable(Serializable paramSerializable, int paramInt) {
    setOutputField(paramInt);
    writeSerializable(paramSerializable);
  }
  
  public <T> void writeSet(Set<T> paramSet, int paramInt) {
    writeCollection(paramSet, paramInt);
  }
  
  public void writeSize(Size paramSize, int paramInt) {
    boolean bool;
    setOutputField(paramInt);
    if (paramSize != null) {
      bool = true;
    } else {
      bool = false;
    } 
    writeBoolean(bool);
    if (paramSize != null) {
      writeInt(paramSize.getWidth());
      writeInt(paramSize.getHeight());
    } 
  }
  
  public void writeSizeF(SizeF paramSizeF, int paramInt) {
    boolean bool;
    setOutputField(paramInt);
    if (paramSizeF != null) {
      bool = true;
    } else {
      bool = false;
    } 
    writeBoolean(bool);
    if (paramSizeF != null) {
      writeFloat(paramSizeF.getWidth());
      writeFloat(paramSizeF.getHeight());
    } 
  }
  
  public void writeSparseBooleanArray(SparseBooleanArray paramSparseBooleanArray, int paramInt) {
    setOutputField(paramInt);
    if (paramSparseBooleanArray == null) {
      writeInt(-1);
      return;
    } 
    int i = paramSparseBooleanArray.size();
    writeInt(i);
    for (paramInt = 0; paramInt < i; paramInt++) {
      writeInt(paramSparseBooleanArray.keyAt(paramInt));
      writeBoolean(paramSparseBooleanArray.valueAt(paramInt));
    } 
  }
  
  protected abstract void writeString(String paramString);
  
  public void writeString(String paramString, int paramInt) {
    setOutputField(paramInt);
    writeString(paramString);
  }
  
  protected abstract void writeStrongBinder(IBinder paramIBinder);
  
  public void writeStrongBinder(IBinder paramIBinder, int paramInt) {
    setOutputField(paramInt);
    writeStrongBinder(paramIBinder);
  }
  
  protected abstract void writeStrongInterface(IInterface paramIInterface);
  
  public void writeStrongInterface(IInterface paramIInterface, int paramInt) {
    setOutputField(paramInt);
    writeStrongInterface(paramIInterface);
  }
  
  protected <T extends VersionedParcelable> void writeToParcel(T paramT, VersionedParcel paramVersionedParcel) {
    try {
      getWriteMethod(paramT.getClass()).invoke(null, new Object[] { paramT, paramVersionedParcel });
      return;
    } catch (IllegalAccessException illegalAccessException) {
      throw new RuntimeException("VersionedParcel encountered IllegalAccessException", illegalAccessException);
    } catch (InvocationTargetException invocationTargetException) {
      if (invocationTargetException.getCause() instanceof RuntimeException)
        throw (RuntimeException)invocationTargetException.getCause(); 
      throw new RuntimeException("VersionedParcel encountered InvocationTargetException", invocationTargetException);
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", noSuchMethodException);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", classNotFoundException);
    } 
  }
  
  protected void writeVersionedParcelable(VersionedParcelable paramVersionedParcelable) {
    if (paramVersionedParcelable == null) {
      writeString(null);
      return;
    } 
    writeVersionedParcelableCreator(paramVersionedParcelable);
    VersionedParcel versionedParcel = createSubParcel();
    writeToParcel(paramVersionedParcelable, versionedParcel);
    versionedParcel.closeField();
  }
  
  public void writeVersionedParcelable(VersionedParcelable paramVersionedParcelable, int paramInt) {
    setOutputField(paramInt);
    writeVersionedParcelable(paramVersionedParcelable);
  }
  
  public static class ParcelException extends RuntimeException {
    public ParcelException(Throwable param1Throwable) {
      super(param1Throwable);
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\versionedparcelable\VersionedParcel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */