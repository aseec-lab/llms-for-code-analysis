package androidx.lifecycle;

import java.util.concurrent.atomic.AtomicReference;

public abstract class Lifecycle {
  AtomicReference<Object> mInternalScopeRef = new AtomicReference();
  
  public abstract void addObserver(LifecycleObserver paramLifecycleObserver);
  
  public abstract State getCurrentState();
  
  public abstract void removeObserver(LifecycleObserver paramLifecycleObserver);
  
  public enum Event {
    ON_ANY, ON_CREATE, ON_DESTROY, ON_PAUSE, ON_RESUME, ON_START, ON_STOP;
    
    private static final Event[] $VALUES;
    
    static {
      ON_PAUSE = new Event("ON_PAUSE", 3);
      ON_STOP = new Event("ON_STOP", 4);
      ON_DESTROY = new Event("ON_DESTROY", 5);
      Event event = new Event("ON_ANY", 6);
      ON_ANY = event;
      $VALUES = new Event[] { ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY, event };
    }
  }
  
  public enum State {
    DESTROYED, CREATED, INITIALIZED, RESUMED, STARTED;
    
    private static final State[] $VALUES;
    
    static {
      State state = new State("RESUMED", 4);
      RESUMED = state;
      $VALUES = new State[] { DESTROYED, INITIALIZED, CREATED, STARTED, state };
    }
    
    public boolean isAtLeast(State param1State) {
      boolean bool;
      if (compareTo(param1State) >= 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\Lifecycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */