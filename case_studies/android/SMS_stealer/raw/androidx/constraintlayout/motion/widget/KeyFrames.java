package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KeyFrames {
  private static final String TAG = "KeyFrames";
  
  public static final int UNSET = -1;
  
  static HashMap<String, Constructor<? extends Key>> sKeyMakers;
  
  private HashMap<Integer, ArrayList<Key>> mFramesMap = new HashMap<Integer, ArrayList<Key>>();
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    sKeyMakers = (HashMap)hashMap;
    try {
      hashMap.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyPosition", KeyPosition.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyCycle", KeyCycle.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyTrigger", KeyTrigger.class.getConstructor(new Class[0]));
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.e("KeyFrames", "unable to load", noSuchMethodException);
    } 
  }
  
  public KeyFrames(Context paramContext, XmlPullParser paramXmlPullParser) {
    Key key = null;
    try {
      int i = paramXmlPullParser.getEventType();
      while (true) {
        Key key1;
        if (i != 1) {
          if (i != 2) {
            if (i != 3) {
              key1 = key;
            } else {
              key1 = key;
              if ("KeyFrameSet".equals(paramXmlPullParser.getName()))
                return; 
            } 
          } else {
            Exception exception;
            String str = paramXmlPullParser.getName();
            boolean bool = sKeyMakers.containsKey(str);
            if (bool) {
              try {
                Key key2 = ((Constructor<Key>)sKeyMakers.get(str)).newInstance(new Object[0]);
                try {
                  key2.load(paramContext, Xml.asAttributeSet(paramXmlPullParser));
                  addKey(key2);
                  key = key2;
                } catch (Exception null) {
                  key = key2;
                } 
              } catch (Exception exception1) {
                exception = exception1;
              } 
              Log.e("KeyFrames", "unable to create ", exception);
            } 
            key1 = key;
            if (exception.equalsIgnoreCase("CustomAttribute")) {
              key1 = key;
              if (key != null) {
                key1 = key;
                if (key.mCustomConstraints != null) {
                  ConstraintAttribute.parse(paramContext, paramXmlPullParser, key.mCustomConstraints);
                  key1 = key;
                } 
              } 
            } 
          } 
        } else {
          break;
        } 
        i = paramXmlPullParser.next();
        key = key1;
      } 
    } catch (XmlPullParserException xmlPullParserException) {
      xmlPullParserException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  private void addKey(Key paramKey) {
    if (!this.mFramesMap.containsKey(Integer.valueOf(paramKey.mTargetId)))
      this.mFramesMap.put(Integer.valueOf(paramKey.mTargetId), new ArrayList<Key>()); 
    ((ArrayList<Key>)this.mFramesMap.get(Integer.valueOf(paramKey.mTargetId))).add(paramKey);
  }
  
  static String name(int paramInt, Context paramContext) {
    return paramContext.getResources().getResourceEntryName(paramInt);
  }
  
  public void addFrames(MotionController paramMotionController) {
    ArrayList<Key> arrayList = this.mFramesMap.get(Integer.valueOf(paramMotionController.mId));
    if (arrayList != null)
      paramMotionController.addKeys(arrayList); 
    arrayList = this.mFramesMap.get(Integer.valueOf(-1));
    if (arrayList != null)
      for (Key key : arrayList) {
        if (key.matches(((ConstraintLayout.LayoutParams)paramMotionController.mView.getLayoutParams()).constraintTag))
          paramMotionController.addKey(key); 
      }  
  }
  
  public ArrayList<Key> getKeyFramesForView(int paramInt) {
    return this.mFramesMap.get(Integer.valueOf(paramInt));
  }
  
  public Set<Integer> getKeys() {
    return this.mFramesMap.keySet();
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\KeyFrames.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */