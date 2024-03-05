package androidx.core.view;

public interface NestedScrollingChild {
  boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2);
  
  boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2);
  
  boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint);
  
  boolean hasNestedScrollingParent();
  
  boolean isNestedScrollingEnabled();
  
  void setNestedScrollingEnabled(boolean paramBoolean);
  
  boolean startNestedScroll(int paramInt);
  
  void stopNestedScroll();
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\core\view\NestedScrollingChild.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */