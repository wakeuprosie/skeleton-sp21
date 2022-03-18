package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    /** Invariants:
     * Each node can only have up to 2 children
     * Every key in the left subtree of a parent is less than the parent key
     * Every key in the right subtree of a parent is greater than the parent key
     * No duplicate keys allowed
     */

    private BSTNode root;

    /** Constructor */
    public BSTMap() {
    }

    private class BSTNode<K> {
        private BSTNode left, right; // Child nodes
        private K key; // Key at this node
        private V value; // Value at this node
        private int size; // Size of the subtree below this node

        public BSTNode(K key, V value, int size) {
            this.size = size;
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        this.root = put(root, key, value);
        return;
    }

    // Traverses the tree until you get to the leaf node where you can add this new node
    private BSTNode put(BSTNode node, K key, V value) {

        // Constructs a root node
        if (node == null) {
            return new BSTNode(key, value, 1);
        }

        // Compares the new node key to the node you passed in to see if the new node goes left or right
        int cmp = key.compareTo(node.key);

        // Recurse to get to the root
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value; // Special case if you already have this key, just update its value
        }

        // If you didn't reach a leaf node, increase the size of this node by 1 bc you will be adding the new node below it somewhere
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    /** Removes all of the mappings from this map. */
    // This means clear the value of the keys
    @Override
    public void clear() {
        this.root = null;
    }

    /* private void clear(BSTNode root) {
        if (root == null) {
            return;
        }
        if (root.right != null) {
            BSTNode temp = root.right;
            root.right = null;
            return clear(temp);
        }
        if (root.left != null) {
            BSTNode temp = root.left;
            root.left = null;
            return clear(temp);
        }
    }*/

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        if (key.compareTo(node.key) < 0) {
            containsKey(node.left, key);
        }
        if (key.compareTo(node.key) > 0) {
            containsKey(node.right, key);
        }
        return true;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    private int size(BSTNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node, K key) {
        if (!containsKey(key)) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return (V) get(node.left, key);
        }
        if (cmp > 0) {
            return (V) get(node.right, key);
        }
        return (V) node.value;
    }

    /** SKIP THESE */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Error");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Error");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Error");
    }


    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Error");
    }
}
