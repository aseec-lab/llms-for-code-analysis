package androidx.constraintlayout.motion.widget;

interface ProxyInterface {
  int designAccess(int paramInt1, String paramString, Object paramObject, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3);
  
  float getKeyFramePosition(Object paramObject, int paramInt, float paramFloat1, float paramFloat2);
  
  Object getKeyframeAtLocation(Object paramObject, float paramFloat1, float paramFloat2);
  
  Boolean getPositionKeyframe(Object paramObject1, Object paramObject2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOffloat);
  
  long getTransitionTimeMs();
  
  void setAttributes(int paramInt, String paramString, Object paramObject1, Object paramObject2);
  
  void setKeyFrame(Object paramObject1, int paramInt, String paramString, Object paramObject2);
  
  boolean setKeyFramePosition(Object paramObject, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2);
  
  void setToolPosition(float paramFloat);
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\constraintlayout\motion\widget\ProxyInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */