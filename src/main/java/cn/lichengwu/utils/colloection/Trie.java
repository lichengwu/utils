package cn.lichengwu.utils.colloection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.lichengwu.utils.lang.Assert;

/**
 * Trie Data Struct
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-10-23 下午6:25
 */
public class Trie {

    TrieNode root = new TrieNode(null, (char) 0, false);

    private int size = 0;

    public Trie() {
    }

    /**
     * add a string to collection
     * <p>
     * <b>Note:</b> string in the collection can be duplicated. you can invoke
     * add("string1") twice, and the collection will contains two string
     * 'string1'.
     * </p>
     * 
     * @param value
     *            the value to be added into collction which must not be null
     * @return whether the value already exists
     */
    public boolean add(String value) {
        int iCount = 0;
        TrieNode node = root;
        while (iCount < value.length() - 1) {
            char currentValue = value.charAt(iCount);
            TrieNode child = node.getChild(currentValue);
            if (child == null) {
                child = new TrieNode(node, currentValue, false);
            }
            node = child;
            iCount++;
        }
        TrieNode lastNote = node.getChild(value.charAt(iCount));
        boolean exists = lastNote == null ? true : !lastNote.isEdge;
        if (lastNote == null) {
            lastNote = new TrieNode(node, value.charAt(iCount), true);
        }
        lastNote.isEdge = true;
        lastNote.itemCount++;
        size++;
        return exists;
    }

    /**
     * remove a string from collection
     * 
     * @param value
     * @return if the string removed from collection return true, otherwise
     *         return false.
     */
    public boolean remove(String value) {
        Assert.notNull(value, "removed string can not be null");

        // tack to the lase node
        int iCount = 0;
        TrieNode node = root;
        while (iCount < value.length()) {
            char currentValue = value.charAt(iCount);
            TrieNode child = node.getChild(currentValue);
            if (child == null) {
                return false;
            }
            node = child;
            iCount++;
        }
        if (!node.isEdge) {
            return false;
        }

        // remove the node one by one.
        TrieNode lastNode4Delete = node;
        lastNode4Delete.itemCount--;
        while (lastNode4Delete.parent != null) {
            if (lastNode4Delete.itemCount == 0 && lastNode4Delete.isEdge) {
                lastNode4Delete.isEdge = false;
            }
            if (lastNode4Delete.getChilden().isEmpty() && !lastNode4Delete.isEdge) {
                lastNode4Delete.childenMap.clear();
                lastNode4Delete.parent.childenMap.remove(lastNode4Delete.value);
            }
            lastNode4Delete = lastNode4Delete.parent;
        }
        size--;
        return true;
    }

    /**
     * whether the value exists in collection
     * 
     * @param value
     *            the value for test
     * @return whether the value exists in collection
     */
    public boolean contains(String value) {

        int iCount = 0;
        TrieNode node = root;
        while (iCount < value.length()) {
            char currentValue = value.charAt(iCount);
            TrieNode child = node.getChild(currentValue);
            if (child == null) {
                return false;
            }
            node = child;
            iCount++;
        }
        return node.isEdge;
    }

    /**
     * TODO Returns an iterator over the elements in this collection
     * 
     * @return n iterator over the elements in this collection
     */
    public Iterator<String> iterator() {
        return new Iterator() {


            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object next() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * test the collection is empty
     * 
     * @return return true while the collection is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * get the string count in the collection
     * 
     * @return string count in the collection
     */
    public int size() {
        return size;
    }

    /**
     * TrieNode for Trie
     */
    static class TrieNode {

        TrieNode parent;
        char value;
        volatile boolean isEdge;
        int length;
        Map<Character, TrieNode> childenMap = new HashMap<Character, TrieNode>();
        int itemCount = 0;

        /**
         * create new TrieNode
         * 
         * @param parent
         *            parent node
         * @param value
         *            node vale
         * @param isEdge
         *            wether the node is a string's edge
         */
        TrieNode(TrieNode parent, char value, boolean isEdge) {
            this.parent = parent;
            this.value = value;
            this.isEdge = this.isEdge || isEdge;
            // for root node
            if (parent == null) {
                this.length = 0;
            } else {
                // normal node
                this.length = this.parent.length + 1;
                this.parent.childenMap.put(value, this);
            }

        }

        /**
         * whether the child nodes contains the given char
         * 
         * @param c
         * @return return true if the child nodes contains the given value,
         *         otherwise return false.
         */
        boolean containsChildValue(char c) {
            return childenMap.containsKey(c);
        }

        /**
         * get child node with the give c value.
         * 
         * @param c
         * @return a child node witch the value is c, or null.
         */
        TrieNode getChild(char c) {
            return childenMap.get(c);
        }

        /**
         * get all child nodes.
         * 
         * @return all child nodes
         */
        Collection<TrieNode> getChilden() {

            return childenMap.values();
        }

        /**
         * get the string value whitch the node represent.
         * <p>
         * <b>Note:</b> this method will track back to root node, and piece all
         * node value together even this node is not a string's edge.
         * </p>
         * 
         * @return
         */
        String getValue() {
            char[] arr = new char[length];
            TrieNode node = this;
            while (node.parent != null) {
                arr[node.length - 1] = node.value;
                node = node.parent;
            }
            return String.valueOf(arr);
        }
    }
}
