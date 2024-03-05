package androidx.lifecycle;

import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class LifecycleRegistry extends Lifecycle {
  private int mAddingObserverCounter = 0;
  
  private boolean mHandlingEvent = false;
  
  private final WeakReference<LifecycleOwner> mLifecycleOwner;
  
  private boolean mNewEventOccurred = false;
  
  private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap = new FastSafeIterableMap();
  
  private ArrayList<Lifecycle.State> mParentStates = new ArrayList<Lifecycle.State>();
  
  private Lifecycle.State mState;
  
  public LifecycleRegistry(LifecycleOwner paramLifecycleOwner) {
    this.mLifecycleOwner = new WeakReference<LifecycleOwner>(paramLifecycleOwner);
    this.mState = Lifecycle.State.INITIALIZED;
  }
  
  private void backwardPass(LifecycleOwner paramLifecycleOwner) {
    Iterator<Map.Entry> iterator = this.mObserverMap.descendingIterator();
    while (iterator.hasNext() && !this.mNewEventOccurred) {
      Map.Entry entry = iterator.next();
      ObserverWithState observerWithState = (ObserverWithState)entry.getValue();
      while (observerWithState.mState.compareTo(this.mState) > 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
        Lifecycle.Event event = downEvent(observerWithState.mState);
        pushParentState(getStateAfter(event));
        observerWithState.dispatchEvent(paramLifecycleOwner, event);
        popParentState();
      } 
    } 
  }
  
  private Lifecycle.State calculateTargetState(LifecycleObserver paramLifecycleObserver) {
    Lifecycle.State state;
    Map.Entry entry = this.mObserverMap.ceil(paramLifecycleObserver);
    ArrayList<Lifecycle.State> arrayList = null;
    if (entry != null) {
      Lifecycle.State state1 = ((ObserverWithState)entry.getValue()).mState;
    } else {
      entry = null;
    } 
    if (!this.mParentStates.isEmpty()) {
      arrayList = this.mParentStates;
      state = arrayList.get(arrayList.size() - 1);
    } 
    return min(min(this.mState, (Lifecycle.State)entry), state);
  }
  
  private static Lifecycle.Event downEvent(Lifecycle.State paramState) {
    int i = null.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i != 5) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Unexpected state value ");
              stringBuilder.append(paramState);
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
            throw new IllegalArgumentException();
          } 
          return Lifecycle.Event.ON_PAUSE;
        } 
        return Lifecycle.Event.ON_STOP;
      } 
      return Lifecycle.Event.ON_DESTROY;
    } 
    throw new IllegalArgumentException();
  }
  
  private void forwardPass(LifecycleOwner paramLifecycleOwner) {
    SafeIterableMap.IteratorWithAdditions<Map.Entry> iteratorWithAdditions = this.mObserverMap.iteratorWithAdditions();
    while (iteratorWithAdditions.hasNext() && !this.mNewEventOccurred) {
      Map.Entry entry = iteratorWithAdditions.next();
      ObserverWithState observerWithState = (ObserverWithState)entry.getValue();
      while (observerWithState.mState.compareTo(this.mState) < 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
        pushParentState(observerWithState.mState);
        observerWithState.dispatchEvent(paramLifecycleOwner, upEvent(observerWithState.mState));
        popParentState();
      } 
    } 
  }
  
  static Lifecycle.State getStateAfter(Lifecycle.Event paramEvent) {
    StringBuilder stringBuilder;
    switch (paramEvent) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected event value ");
        stringBuilder.append(paramEvent);
        throw new IllegalArgumentException(stringBuilder.toString());
      case null:
        return Lifecycle.State.DESTROYED;
      case null:
        return Lifecycle.State.RESUMED;
      case null:
      case null:
        return Lifecycle.State.STARTED;
      case null:
      case null:
        break;
    } 
    return Lifecycle.State.CREATED;
  }
  
  private boolean isSynced() {
    int i = this.mObserverMap.size();
    boolean bool = true;
    if (i == 0)
      return true; 
    Lifecycle.State state2 = ((ObserverWithState)this.mObserverMap.eldest().getValue()).mState;
    Lifecycle.State state1 = ((ObserverWithState)this.mObserverMap.newest().getValue()).mState;
    if (state2 != state1 || this.mState != state1)
      bool = false; 
    return bool;
  }
  
  static Lifecycle.State min(Lifecycle.State paramState1, Lifecycle.State paramState2) {
    Lifecycle.State state = paramState1;
    if (paramState2 != null) {
      state = paramState1;
      if (paramState2.compareTo(paramState1) < 0)
        state = paramState2; 
    } 
    return state;
  }
  
  private void moveToState(Lifecycle.State paramState) {
    if (this.mState == paramState)
      return; 
    this.mState = paramState;
    if (this.mHandlingEvent || this.mAddingObserverCounter != 0) {
      this.mNewEventOccurred = true;
      return;
    } 
    this.mHandlingEvent = true;
    sync();
    this.mHandlingEvent = false;
  }
  
  private void popParentState() {
    ArrayList<Lifecycle.State> arrayList = this.mParentStates;
    arrayList.remove(arrayList.size() - 1);
  }
  
  private void pushParentState(Lifecycle.State paramState) {
    this.mParentStates.add(paramState);
  }
  
  private void sync() {
    LifecycleOwner lifecycleOwner = this.mLifecycleOwner.get();
    if (lifecycleOwner != null) {
      while (!isSynced()) {
        this.mNewEventOccurred = false;
        if (this.mState.compareTo(((ObserverWithState)this.mObserverMap.eldest().getValue()).mState) < 0)
          backwardPass(lifecycleOwner); 
        Map.Entry entry = this.mObserverMap.newest();
        if (!this.mNewEventOccurred && entry != null && this.mState.compareTo(((ObserverWithState)entry.getValue()).mState) > 0)
          forwardPass(lifecycleOwner); 
      } 
      this.mNewEventOccurred = false;
      return;
    } 
    throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
  }
  
  private static Lifecycle.Event upEvent(Lifecycle.State paramState) {
    int i = null.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()];
    if (i != 1)
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i != 5) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Unexpected state value ");
              stringBuilder.append(paramState);
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
          } else {
            throw new IllegalArgumentException();
          } 
        } else {
          return Lifecycle.Event.ON_RESUME;
        } 
      } else {
        return Lifecycle.Event.ON_START;
      }  
    return Lifecycle.Event.ON_CREATE;
  }
  
  public void addObserver(LifecycleObserver paramLifecycleObserver) {
    boolean bool;
    if (this.mState == Lifecycle.State.DESTROYED) {
      state = Lifecycle.State.DESTROYED;
    } else {
      state = Lifecycle.State.INITIALIZED;
    } 
    ObserverWithState observerWithState = new ObserverWithState(paramLifecycleObserver, state);
    if ((ObserverWithState)this.mObserverMap.putIfAbsent(paramLifecycleObserver, observerWithState) != null)
      return; 
    LifecycleOwner lifecycleOwner = this.mLifecycleOwner.get();
    if (lifecycleOwner == null)
      return; 
    if (this.mAddingObserverCounter != 0 || this.mHandlingEvent) {
      bool = true;
    } else {
      bool = false;
    } 
    Lifecycle.State state = calculateTargetState(paramLifecycleObserver);
    this.mAddingObserverCounter++;
    while (observerWithState.mState.compareTo(state) < 0 && this.mObserverMap.contains(paramLifecycleObserver)) {
      pushParentState(observerWithState.mState);
      observerWithState.dispatchEvent(lifecycleOwner, upEvent(observerWithState.mState));
      popParentState();
      state = calculateTargetState(paramLifecycleObserver);
    } 
    if (!bool)
      sync(); 
    this.mAddingObserverCounter--;
  }
  
  public Lifecycle.State getCurrentState() {
    return this.mState;
  }
  
  public int getObserverCount() {
    return this.mObserverMap.size();
  }
  
  public void handleLifecycleEvent(Lifecycle.Event paramEvent) {
    moveToState(getStateAfter(paramEvent));
  }
  
  @Deprecated
  public void markState(Lifecycle.State paramState) {
    setCurrentState(paramState);
  }
  
  public void removeObserver(LifecycleObserver paramLifecycleObserver) {
    this.mObserverMap.remove(paramLifecycleObserver);
  }
  
  public void setCurrentState(Lifecycle.State paramState) {
    moveToState(paramState);
  }
  
  static class ObserverWithState {
    LifecycleEventObserver mLifecycleObserver;
    
    Lifecycle.State mState;
    
    ObserverWithState(LifecycleObserver param1LifecycleObserver, Lifecycle.State param1State) {
      this.mLifecycleObserver = Lifecycling.lifecycleEventObserver(param1LifecycleObserver);
      this.mState = param1State;
    }
    
    void dispatchEvent(LifecycleOwner param1LifecycleOwner, Lifecycle.Event param1Event) {
      Lifecycle.State state = LifecycleRegistry.getStateAfter(param1Event);
      this.mState = LifecycleRegistry.min(this.mState, state);
      this.mLifecycleObserver.onStateChanged(param1LifecycleOwner, param1Event);
      this.mState = state;
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\lifecycle\LifecycleRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */