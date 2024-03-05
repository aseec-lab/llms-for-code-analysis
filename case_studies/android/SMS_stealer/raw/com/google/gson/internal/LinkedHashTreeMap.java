package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedHashTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
  static final boolean $assertionsDisabled = false;
  
  private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
      public int compare(Comparable<Comparable> param1Comparable1, Comparable param1Comparable2) {
        return param1Comparable1.compareTo(param1Comparable2);
      }
    };
  
  Comparator<? super K> comparator;
  
  private EntrySet entrySet;
  
  final Node<K, V> header;
  
  private KeySet keySet;
  
  int modCount;
  
  int size;
  
  Node<K, V>[] table;
  
  int threshold;
  
  public LinkedHashTreeMap() {
    this((Comparator)NATURAL_ORDER);
  }
  
  public LinkedHashTreeMap(Comparator<? super K> paramComparator) {
    Comparator<Comparable> comparator;
    this.size = 0;
    this.modCount = 0;
    if (paramComparator == null)
      comparator = NATURAL_ORDER; 
    this.comparator = (Comparator)comparator;
    this.header = new Node<K, V>();
    Node[] arrayOfNode = new Node[16];
    this.table = (Node<K, V>[])arrayOfNode;
    this.threshold = arrayOfNode.length / 2 + arrayOfNode.length / 4;
  }
  
  private void doubleCapacity() {
    Node[] arrayOfNode = (Node[])doubleCapacity(this.table);
    this.table = (Node<K, V>[])arrayOfNode;
    this.threshold = arrayOfNode.length / 2 + arrayOfNode.length / 4;
  }
  
  static <K, V> Node<K, V>[] doubleCapacity(Node<K, V>[] paramArrayOfNode) {
    int i = paramArrayOfNode.length;
    Node[] arrayOfNode = new Node[i * 2];
    AvlIterator<Object, Object> avlIterator = new AvlIterator<Object, Object>();
    AvlBuilder<Object, Object> avlBuilder1 = new AvlBuilder<Object, Object>();
    AvlBuilder<Object, Object> avlBuilder2 = new AvlBuilder<Object, Object>();
    for (byte b = 0; b < i; b++) {
      Node<K, V> node = paramArrayOfNode[b];
      if (node != null) {
        avlIterator.reset((Node)node);
        byte b2 = 0;
        byte b1 = 0;
        while (true) {
          Node<Object, Object> node1 = avlIterator.next();
          if (node1 != null) {
            if ((node1.hash & i) == 0) {
              b2++;
              continue;
            } 
            b1++;
            continue;
          } 
          avlBuilder1.reset(b2);
          avlBuilder2.reset(b1);
          avlIterator.reset((Node)node);
          while (true) {
            node = (Node)avlIterator.next();
            if (node != null) {
              if ((node.hash & i) == 0) {
                avlBuilder1.add((Node)node);
                continue;
              } 
              avlBuilder2.add((Node)node);
              continue;
            } 
            node1 = null;
            if (b2 > 0) {
              node = (Node)avlBuilder1.root();
            } else {
              node = null;
            } 
            arrayOfNode[b] = node;
            Node<Object, Object> node2 = node1;
            if (b1 > 0)
              node2 = avlBuilder2.root(); 
            arrayOfNode[b + i] = node2;
            break;
          } 
          break;
        } 
      } 
    } 
    return (Node<K, V>[])arrayOfNode;
  }
  
  private boolean equal(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2)));
  }
  
  private void rebalance(Node<K, V> paramNode, boolean paramBoolean) {
    while (paramNode != null) {
      int i;
      int j;
      Node<K, V> node1 = paramNode.left;
      Node<K, V> node2 = paramNode.right;
      boolean bool = false;
      byte b = 0;
      if (node1 != null) {
        i = node1.height;
      } else {
        i = 0;
      } 
      if (node2 != null) {
        j = node2.height;
      } else {
        j = 0;
      } 
      int k = i - j;
      if (k == -2) {
        node1 = node2.left;
        Node<K, V> node = node2.right;
        if (node != null) {
          i = node.height;
        } else {
          i = 0;
        } 
        j = b;
        if (node1 != null)
          j = node1.height; 
        i = j - i;
        if (i == -1 || (i == 0 && !paramBoolean)) {
          rotateLeft(paramNode);
        } else {
          rotateRight(node2);
          rotateLeft(paramNode);
        } 
        if (paramBoolean)
          break; 
      } else if (k == 2) {
        Node<K, V> node = node1.left;
        node2 = node1.right;
        if (node2 != null) {
          i = node2.height;
        } else {
          i = 0;
        } 
        j = bool;
        if (node != null)
          j = node.height; 
        i = j - i;
        if (i == 1 || (i == 0 && !paramBoolean)) {
          rotateRight(paramNode);
        } else {
          rotateLeft(node1);
          rotateRight(paramNode);
        } 
        if (paramBoolean)
          break; 
      } else if (k == 0) {
        paramNode.height = i + 1;
        if (paramBoolean)
          break; 
      } else {
        paramNode.height = Math.max(i, j) + 1;
        if (!paramBoolean)
          break; 
      } 
      paramNode = paramNode.parent;
    } 
  }
  
  private void replaceInParent(Node<K, V> paramNode1, Node<K, V> paramNode2) {
    Node<K, V> node = paramNode1.parent;
    paramNode1.parent = null;
    if (paramNode2 != null)
      paramNode2.parent = node; 
    if (node != null) {
      if (node.left == paramNode1) {
        node.left = paramNode2;
      } else {
        node.right = paramNode2;
      } 
    } else {
      int i = paramNode1.hash;
      Node<K, V>[] arrayOfNode = this.table;
      arrayOfNode[i & arrayOfNode.length - 1] = paramNode2;
    } 
  }
  
  private void rotateLeft(Node<K, V> paramNode) {
    Node<K, V> node4 = paramNode.left;
    Node<K, V> node2 = paramNode.right;
    Node<K, V> node1 = node2.left;
    Node<K, V> node3 = node2.right;
    paramNode.right = node1;
    if (node1 != null)
      node1.parent = paramNode; 
    replaceInParent(paramNode, node2);
    node2.left = paramNode;
    paramNode.parent = node2;
    byte b = 0;
    if (node4 != null) {
      i = node4.height;
    } else {
      i = 0;
    } 
    if (node1 != null) {
      j = node1.height;
    } else {
      j = 0;
    } 
    paramNode.height = Math.max(i, j) + 1;
    int j = paramNode.height;
    int i = b;
    if (node3 != null)
      i = node3.height; 
    node2.height = Math.max(j, i) + 1;
  }
  
  private void rotateRight(Node<K, V> paramNode) {
    Node<K, V> node1 = paramNode.left;
    Node<K, V> node2 = paramNode.right;
    Node<K, V> node4 = node1.left;
    Node<K, V> node3 = node1.right;
    paramNode.left = node3;
    if (node3 != null)
      node3.parent = paramNode; 
    replaceInParent(paramNode, node1);
    node1.right = paramNode;
    paramNode.parent = node1;
    byte b = 0;
    if (node2 != null) {
      i = node2.height;
    } else {
      i = 0;
    } 
    if (node3 != null) {
      j = node3.height;
    } else {
      j = 0;
    } 
    paramNode.height = Math.max(i, j) + 1;
    int j = paramNode.height;
    int i = b;
    if (node4 != null)
      i = node4.height; 
    node1.height = Math.max(j, i) + 1;
  }
  
  private static int secondaryHash(int paramInt) {
    paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
    return paramInt >>> 4 ^ paramInt >>> 7 ^ paramInt;
  }
  
  private Object writeReplace() throws ObjectStreamException {
    return new LinkedHashMap<Object, Object>(this);
  }
  
  public void clear() {
    Arrays.fill((Object[])this.table, (Object)null);
    this.size = 0;
    this.modCount++;
    Node<K, V> node2 = this.header;
    for (Node<K, V> node1 = node2.next; node1 != node2; node1 = node) {
      Node<K, V> node = node1.next;
      node1.prev = null;
      node1.next = null;
    } 
    node2.prev = node2;
    node2.next = node2;
  }
  
  public boolean containsKey(Object paramObject) {
    boolean bool;
    if (findByObject(paramObject) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Set<Map.Entry<K, V>> entrySet() {
    EntrySet entrySet = this.entrySet;
    if (entrySet == null) {
      entrySet = new EntrySet();
      this.entrySet = entrySet;
    } 
    return entrySet;
  }
  
  Node<K, V> find(K paramK, boolean paramBoolean) {
    Node<K, V> node1;
    StringBuilder stringBuilder;
    Comparator<? super K> comparator = this.comparator;
    Node<K, V>[] arrayOfNode = this.table;
    int j = secondaryHash(paramK.hashCode());
    int k = arrayOfNode.length - 1 & j;
    Node<K, V> node2 = arrayOfNode[k];
    if (node2 != null) {
      Comparable<K> comparable;
      if (comparator == NATURAL_ORDER) {
        comparable = (Comparable)paramK;
      } else {
        comparable = null;
      } 
      while (true) {
        Node<K, V> node;
        if (comparable != null) {
          i = comparable.compareTo(node2.key);
        } else {
          i = comparator.compare(paramK, node2.key);
        } 
        if (i == 0)
          return node2; 
        if (i < 0) {
          node = node2.left;
        } else {
          node = node2.right;
        } 
        if (node == null)
          break; 
        node2 = node;
      } 
    } else {
      i = 0;
    } 
    if (!paramBoolean)
      return null; 
    Node<K, V> node3 = this.header;
    if (node2 == null) {
      if (comparator != NATURAL_ORDER || paramK instanceof Comparable) {
        node1 = new Node<K, V>(node2, paramK, j, node3, node3.prev);
        arrayOfNode[k] = node1;
      } else {
        stringBuilder = new StringBuilder();
        stringBuilder.append(node1.getClass().getName());
        stringBuilder.append(" is not Comparable");
        throw new ClassCastException(stringBuilder.toString());
      } 
    } else {
      node1 = new Node<K, V>((Node<K, V>)stringBuilder, (K)node1, j, node3, node3.prev);
      if (i) {
        ((Node)stringBuilder).left = node1;
      } else {
        ((Node)stringBuilder).right = node1;
      } 
      rebalance((Node<K, V>)stringBuilder, true);
    } 
    int i = this.size;
    this.size = i + 1;
    if (i > this.threshold)
      doubleCapacity(); 
    this.modCount++;
    return node1;
  }
  
  Node<K, V> findByEntry(Map.Entry<?, ?> paramEntry) {
    boolean bool;
    Node<K, V> node = findByObject(paramEntry.getKey());
    if (node != null && equal(node.value, paramEntry.getValue())) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      paramEntry = node;
    } else {
      paramEntry = null;
    } 
    return (Node)paramEntry;
  }
  
  Node<K, V> findByObject(Object paramObject) {
    Node<K, V> node2 = null;
    Node<K, V> node1 = node2;
    if (paramObject != null)
      try {
        node1 = find((K)paramObject, false);
      } catch (ClassCastException classCastException) {
        node1 = node2;
      }  
    return node1;
  }
  
  public V get(Object<K, V> paramObject) {
    paramObject = (Object<K, V>)findByObject(paramObject);
    if (paramObject != null) {
      V v = ((Node)paramObject).value;
    } else {
      paramObject = null;
    } 
    return (V)paramObject;
  }
  
  public Set<K> keySet() {
    KeySet keySet = this.keySet;
    if (keySet == null) {
      keySet = new KeySet();
      this.keySet = keySet;
    } 
    return keySet;
  }
  
  public V put(K paramK, V paramV) {
    if (paramK != null) {
      Node<K, V> node = find(paramK, true);
      V v = node.value;
      node.value = paramV;
      return v;
    } 
    throw new NullPointerException("key == null");
  }
  
  public V remove(Object<K, V> paramObject) {
    paramObject = (Object<K, V>)removeInternalByKey(paramObject);
    if (paramObject != null) {
      V v = ((Node)paramObject).value;
    } else {
      paramObject = null;
    } 
    return (V)paramObject;
  }
  
  void removeInternal(Node<K, V> paramNode, boolean paramBoolean) {
    if (paramBoolean) {
      paramNode.prev.next = paramNode.next;
      paramNode.next.prev = paramNode.prev;
      paramNode.prev = null;
      paramNode.next = null;
    } 
    Node<K, V> node3 = paramNode.left;
    Node<K, V> node2 = paramNode.right;
    Node<K, V> node1 = paramNode.parent;
    int i = 0;
    if (node3 != null && node2 != null) {
      boolean bool;
      if (node3.height > node2.height) {
        node1 = node3.last();
      } else {
        node1 = node2.first();
      } 
      removeInternal(node1, false);
      node2 = paramNode.left;
      if (node2 != null) {
        bool = node2.height;
        node1.left = node2;
        node2.parent = node1;
        paramNode.left = null;
      } else {
        bool = false;
      } 
      node2 = paramNode.right;
      if (node2 != null) {
        i = node2.height;
        node1.right = node2;
        node2.parent = node1;
        paramNode.right = null;
      } 
      node1.height = Math.max(bool, i) + 1;
      replaceInParent(paramNode, node1);
      return;
    } 
    if (node3 != null) {
      replaceInParent(paramNode, node3);
      paramNode.left = null;
    } else if (node2 != null) {
      replaceInParent(paramNode, node2);
      paramNode.right = null;
    } else {
      replaceInParent(paramNode, null);
    } 
    rebalance(node1, false);
    this.size--;
    this.modCount++;
  }
  
  Node<K, V> removeInternalByKey(Object<K, V> paramObject) {
    paramObject = (Object<K, V>)findByObject(paramObject);
    if (paramObject != null)
      removeInternal((Node<K, V>)paramObject, true); 
    return (Node<K, V>)paramObject;
  }
  
  public int size() {
    return this.size;
  }
  
  static final class AvlBuilder<K, V> {
    private int leavesSkipped;
    
    private int leavesToSkip;
    
    private int size;
    
    private LinkedHashTreeMap.Node<K, V> stack;
    
    void add(LinkedHashTreeMap.Node<K, V> param1Node) {
      param1Node.right = null;
      param1Node.parent = null;
      param1Node.left = null;
      param1Node.height = 1;
      int i = this.leavesToSkip;
      if (i > 0) {
        int k = this.size;
        if ((k & 0x1) == 0) {
          this.size = k + 1;
          this.leavesToSkip = i - 1;
          this.leavesSkipped++;
        } 
      } 
      param1Node.parent = this.stack;
      this.stack = param1Node;
      i = this.size + 1;
      this.size = i;
      int j = this.leavesToSkip;
      if (j > 0 && (i & 0x1) == 0) {
        this.size = i + 1;
        this.leavesToSkip = j - 1;
        this.leavesSkipped++;
      } 
      i = 4;
      while (true) {
        j = this.size;
        int k = i - 1;
        if ((j & k) == k) {
          j = this.leavesSkipped;
          if (j == 0) {
            LinkedHashTreeMap.Node<K, V> node1 = this.stack;
            param1Node = node1.parent;
            LinkedHashTreeMap.Node<K, V> node2 = param1Node.parent;
            param1Node.parent = node2.parent;
            this.stack = param1Node;
            param1Node.left = node2;
            param1Node.right = node1;
            node1.height++;
            node2.parent = param1Node;
            node1.parent = param1Node;
          } else if (j == 1) {
            LinkedHashTreeMap.Node<K, V> node = this.stack;
            param1Node = node.parent;
            this.stack = param1Node;
            param1Node.right = node;
            node.height++;
            node.parent = param1Node;
            this.leavesSkipped = 0;
          } else if (j == 2) {
            this.leavesSkipped = 0;
          } 
          i *= 2;
          continue;
        } 
        break;
      } 
    }
    
    void reset(int param1Int) {
      this.leavesToSkip = Integer.highestOneBit(param1Int) * 2 - 1 - param1Int;
      this.size = 0;
      this.leavesSkipped = 0;
      this.stack = null;
    }
    
    LinkedHashTreeMap.Node<K, V> root() {
      LinkedHashTreeMap.Node<K, V> node = this.stack;
      if (node.parent == null)
        return node; 
      throw new IllegalStateException();
    }
  }
  
  static class AvlIterator<K, V> {
    private LinkedHashTreeMap.Node<K, V> stackTop;
    
    public LinkedHashTreeMap.Node<K, V> next() {
      LinkedHashTreeMap.Node<K, V> node3 = this.stackTop;
      if (node3 == null)
        return null; 
      LinkedHashTreeMap.Node<K, V> node2 = node3.parent;
      node3.parent = null;
      LinkedHashTreeMap.Node<K, V> node1 = node3.right;
      while (true) {
        LinkedHashTreeMap.Node<K, V> node = node2;
        node2 = node1;
        if (node2 != null) {
          node2.parent = node;
          node1 = node2.left;
          continue;
        } 
        this.stackTop = node;
        return node3;
      } 
    }
    
    void reset(LinkedHashTreeMap.Node<K, V> param1Node) {
      LinkedHashTreeMap.Node<K, V> node = null;
      while (param1Node != null) {
        param1Node.parent = node;
        LinkedHashTreeMap.Node<K, V> node1 = param1Node.left;
        node = param1Node;
        param1Node = node1;
      } 
      this.stackTop = node;
    }
  }
  
  final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    final LinkedHashTreeMap this$0;
    
    public void clear() {
      LinkedHashTreeMap.this.clear();
    }
    
    public boolean contains(Object param1Object) {
      boolean bool;
      if (param1Object instanceof Map.Entry && LinkedHashTreeMap.this.findByEntry((Map.Entry<?, ?>)param1Object) != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Iterator<Map.Entry<K, V>> iterator() {
      return new LinkedHashTreeMap<K, V>.LinkedTreeMapIterator<Map.Entry<K, V>>() {
          final LinkedHashTreeMap.EntrySet this$1;
          
          public Map.Entry<K, V> next() {
            return nextNode();
          }
        };
    }
    
    public boolean remove(Object param1Object) {
      if (!(param1Object instanceof Map.Entry))
        return false; 
      param1Object = LinkedHashTreeMap.this.findByEntry((Map.Entry<?, ?>)param1Object);
      if (param1Object == null)
        return false; 
      LinkedHashTreeMap.this.removeInternal((LinkedHashTreeMap.Node)param1Object, true);
      return true;
    }
    
    public int size() {
      return LinkedHashTreeMap.this.size;
    }
  }
  
  class null extends LinkedTreeMapIterator<Map.Entry<K, V>> {
    final LinkedHashTreeMap.EntrySet this$1;
    
    public Map.Entry<K, V> next() {
      return nextNode();
    }
  }
  
  final class KeySet extends AbstractSet<K> {
    final LinkedHashTreeMap this$0;
    
    public void clear() {
      LinkedHashTreeMap.this.clear();
    }
    
    public boolean contains(Object param1Object) {
      return LinkedHashTreeMap.this.containsKey(param1Object);
    }
    
    public Iterator<K> iterator() {
      return new LinkedHashTreeMap<K, V>.LinkedTreeMapIterator<K>() {
          final LinkedHashTreeMap.KeySet this$1;
          
          public K next() {
            return (nextNode()).key;
          }
        };
    }
    
    public boolean remove(Object param1Object) {
      boolean bool;
      if (LinkedHashTreeMap.this.removeInternalByKey(param1Object) != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int size() {
      return LinkedHashTreeMap.this.size;
    }
  }
  
  class null extends LinkedTreeMapIterator<K> {
    final LinkedHashTreeMap.KeySet this$1;
    
    public K next() {
      return (nextNode()).key;
    }
  }
  
  private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
    int expectedModCount = LinkedHashTreeMap.this.modCount;
    
    LinkedHashTreeMap.Node<K, V> lastReturned = null;
    
    LinkedHashTreeMap.Node<K, V> next = LinkedHashTreeMap.this.header.next;
    
    final LinkedHashTreeMap this$0;
    
    public final boolean hasNext() {
      boolean bool;
      if (this.next != LinkedHashTreeMap.this.header) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    final LinkedHashTreeMap.Node<K, V> nextNode() {
      LinkedHashTreeMap.Node<K, V> node = this.next;
      if (node != LinkedHashTreeMap.this.header) {
        if (LinkedHashTreeMap.this.modCount == this.expectedModCount) {
          this.next = node.next;
          this.lastReturned = node;
          return node;
        } 
        throw new ConcurrentModificationException();
      } 
      throw new NoSuchElementException();
    }
    
    public final void remove() {
      LinkedHashTreeMap.Node<K, V> node = this.lastReturned;
      if (node != null) {
        LinkedHashTreeMap.this.removeInternal(node, true);
        this.lastReturned = null;
        this.expectedModCount = LinkedHashTreeMap.this.modCount;
        return;
      } 
      throw new IllegalStateException();
    }
  }
  
  static final class Node<K, V> implements Map.Entry<K, V> {
    final int hash;
    
    int height;
    
    final K key;
    
    Node<K, V> left;
    
    Node<K, V> next;
    
    Node<K, V> parent;
    
    Node<K, V> prev;
    
    Node<K, V> right;
    
    V value;
    
    Node() {
      this.key = null;
      this.hash = -1;
      this.prev = this;
      this.next = this;
    }
    
    Node(Node<K, V> param1Node1, K param1K, int param1Int, Node<K, V> param1Node2, Node<K, V> param1Node3) {
      this.parent = param1Node1;
      this.key = param1K;
      this.hash = param1Int;
      this.height = 1;
      this.next = param1Node2;
      this.prev = param1Node3;
      param1Node3.next = this;
      param1Node2.prev = this;
    }
    
    public boolean equals(Object param1Object) {
      // Byte code:
      //   0: aload_1
      //   1: instanceof java/util/Map$Entry
      //   4: istore #4
      //   6: iconst_0
      //   7: istore_3
      //   8: iload_3
      //   9: istore_2
      //   10: iload #4
      //   12: ifeq -> 104
      //   15: aload_1
      //   16: checkcast java/util/Map$Entry
      //   19: astore_1
      //   20: aload_0
      //   21: getfield key : Ljava/lang/Object;
      //   24: astore #5
      //   26: aload #5
      //   28: ifnonnull -> 45
      //   31: iload_3
      //   32: istore_2
      //   33: aload_1
      //   34: invokeinterface getKey : ()Ljava/lang/Object;
      //   39: ifnonnull -> 104
      //   42: goto -> 61
      //   45: iload_3
      //   46: istore_2
      //   47: aload #5
      //   49: aload_1
      //   50: invokeinterface getKey : ()Ljava/lang/Object;
      //   55: invokevirtual equals : (Ljava/lang/Object;)Z
      //   58: ifeq -> 104
      //   61: aload_0
      //   62: getfield value : Ljava/lang/Object;
      //   65: astore #5
      //   67: aload #5
      //   69: ifnonnull -> 86
      //   72: iload_3
      //   73: istore_2
      //   74: aload_1
      //   75: invokeinterface getValue : ()Ljava/lang/Object;
      //   80: ifnonnull -> 104
      //   83: goto -> 102
      //   86: iload_3
      //   87: istore_2
      //   88: aload #5
      //   90: aload_1
      //   91: invokeinterface getValue : ()Ljava/lang/Object;
      //   96: invokevirtual equals : (Ljava/lang/Object;)Z
      //   99: ifeq -> 104
      //   102: iconst_1
      //   103: istore_2
      //   104: iload_2
      //   105: ireturn
    }
    
    public Node<K, V> first() {
      Node<K, V> node1 = this.left;
      Node<K, V> node2 = this;
      while (node1 != null) {
        Node<K, V> node = node1.left;
        node2 = node1;
        node1 = node;
      } 
      return node2;
    }
    
    public K getKey() {
      return this.key;
    }
    
    public V getValue() {
      return this.value;
    }
    
    public int hashCode() {
      int i;
      K k = this.key;
      int j = 0;
      if (k == null) {
        i = 0;
      } else {
        i = k.hashCode();
      } 
      V v = this.value;
      if (v != null)
        j = v.hashCode(); 
      return i ^ j;
    }
    
    public Node<K, V> last() {
      Node<K, V> node1 = this.right;
      Node<K, V> node2 = this;
      while (node1 != null) {
        Node<K, V> node = node1.right;
        node2 = node1;
        node1 = node;
      } 
      return node2;
    }
    
    public V setValue(V param1V) {
      V v = this.value;
      this.value = param1V;
      return v;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.key);
      stringBuilder.append("=");
      stringBuilder.append(this.value);
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\com\google\gson\internal\LinkedHashTreeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */