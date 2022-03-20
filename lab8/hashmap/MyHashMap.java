package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author risa
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadfactor;
    private int N; // bucket size
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.buckets = this.createTable(16);
        this.N = 16;
        this.loadfactor = 0.75;
        for (int i = 0; i < N; i += 1) {
            this.buckets[i] = this.createBucket();
        }
    }

    public MyHashMap(int initialSize) {
        this.buckets = this.createTable(initialSize);
        this.N = initialSize;
        this.loadfactor = 0.75;
        for (int i = 0; i < N; i += 1) {
            this.buckets[i] = this.createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.buckets = this.createTable(initialSize);
        this.N = initialSize;
        this.loadfactor = maxLoad;
        for (int i = 0; i < N; i += 1) {
            this.buckets[i] = this.createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
       return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        this.buckets = this.createTable(16);
        this.N = 16;
        this.loadfactor = 0.75;
        for (int i = 0; i < N; i += 1) {
            this.buckets[i] = this.createBucket();
        }
    }

    @Override
    public boolean containsKey(K key) {
        // Find the hashcode of the key
        int code = key.hashCode();
        int index = code % this.N;
        if (index < 0) {
            index = index + N;
        }

        // Get the right bucket of the buckets array, based on hashcode
        // Go through each node in the bucket and check if its key matches
        Iterator bucketIterator = (this.buckets[index]).iterator();
        while (bucketIterator.hasNext()) {
            Node node = (Node) bucketIterator.next();
            if (node.key.equals(key)) {
                return true;
            }
        }
        // If no match, return false
        return false;
    }

    @Override
    public V get(K key) {

        int code = key.hashCode();
        int transformedCode = code % this.N;
        if (transformedCode < 0) {
            transformedCode = transformedCode + N;
        }
        Iterator bucketIterator = (this.buckets[transformedCode]).iterator();
        while (bucketIterator.hasNext()) {
            Node node = (Node) bucketIterator.next();
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < N; i+= 1) {
            Collection<Node> bucket = buckets[i];
            size += bucket.size();
        }
        return size;
    }

    @Override
    public void put(K key, V value) {
        int hashCode = key.hashCode();
        int index = hashCode % N;
        if (index < 0) {
            index = index + N;
        }
        // Check if item exists -- if yes, update
        if (this.containsKey(key)) {
            Iterator bucketIterator = buckets[index].iterator();
            while (bucketIterator.hasNext()) {
                Node node = (Node) bucketIterator.next();
                if (node.key.equals(key)) {
                    node.value = value;
                }
            }
        } else {
            // If no, add a new node
            buckets[index].add(new Node(key, value));
        }
    }

    @Override
    public Set<K> keySet() {
        Set keySet = new HashSet();
        for (int i = 0; i < this.N; i += 1) {
            Collection bucket = buckets[i];
            for (int j = 0; j < bucket.size(); j += 1) {
                Iterator bucketIterator = bucket.iterator();
                while(bucketIterator.hasNext()) {
                    Node node = (Node) bucketIterator.next();
                    keySet.add(node.key);
                }
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Error");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Error");
    }

    @Override
    public Iterator<K> iterator() {
        // Create an iterator of the keyset
        Set keySet = this.keySet();
        Iterator keySetIterator = keySet.iterator();

        return keySetIterator;
    }

}
