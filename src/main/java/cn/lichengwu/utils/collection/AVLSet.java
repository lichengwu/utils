package cn.lichengwu.utils.collection;

import java.util.*;

/**
 * a set with avl balance
 *
 * @author 佐井
 * @version 1.0
 * @created 2013-12-07 4:23 PM
 */
public class AVLSet<T> implements Set<T> {

    private static final byte H1 = 1;

    private volatile int size = 0;

    private volatile int modCount = 0;

    private AVLNode<T> root;

    private Comparator<? super T> comparator;

    public AVLSet() {
    }

    public AVLSet(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        AVLNode<T> found = find((T) o, root);
        return found != null;
    }

    @Override
    public Iterator<T> iterator() {
        return new AVLIterator<T>(root);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        AVLNode<T> newRoot = insert(t, root);
        if (newRoot != null) {
            root = newRoot;
        }
        return newRoot != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        AVLNode<T> found = find((T) o, root);
        //只是标记
        if (found != null) {
            found.deleted = true;
            modCount++;
        }
        return found != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsAll(Collection<?> c) {
        for (Object data : c) {
            if (find((T) data, root) == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean changed = false;
        for (T data : c) {
            AVLNode<T> inserted = insert(data, root);
            if (!changed && inserted != null) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (Object o : c) {
            if (remove(o) && !removed) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
        modCount++;
    }

    /**
     * 树左旋转
     *
     * @param k2
     *
     * @return 旋转后新的root
     */
    private AVLNode<T> rotateWithLeftChild(AVLNode<T> k2) {
        AVLNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        //设置父亲
        k1.parent = k2.parent;
        k2.parent = k1;
        if (k1.right != null) {
            k1.right.parent = k2;
        }
        //设置高度
        k2.height = ((byte) (Math.max(nodeHeight(k2.left), nodeHeight(k2.right)) + 1));
        k1.height = (byte) (Math.max(k2.height, nodeHeight(k1.left)) + 1);
        return k1;
    }


    /**
     * 树右旋转
     *
     * @param k1
     *
     * @return 旋转后新的root
     */
    private AVLNode<T> rotateWithRightChild(AVLNode<T> k1) {
        AVLNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k2.parent = k1.parent;
        k1.parent = k2;
        if (k2.left != null) {
            k2.left.parent = k1;
        }
        k1.height = (byte) (Math.max(nodeHeight(k1.left), nodeHeight(k1.right)) + 1);
        k2.height = (byte) (Math.max(k1.height, nodeHeight(k2.right)) + 1);
        return k2;
    }

    /**
     * 树左双旋转
     *
     * @param k3
     *
     * @return 旋转后新的root
     */
    private AVLNode<T> doubleRotateWithLeftChild(AVLNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }


    /**
     * 树右双旋转
     *
     * @param k3
     *
     * @return 旋转后新的root
     */
    private AVLNode<T> doubleRotateWithRightChild(AVLNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    @SuppressWarnings("unchecked")
    private int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super T>) k1).compareTo((T) k2) : comparator.compare((T) k1, (T) k2);
    }


    /**
     * 计算节点高度
     *
     * @param node
     *
     * @return
     */
    private byte nodeHeight(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    /**
     * 插入数据到指定节点
     *
     * @param data
     * @param node
     *
     * @return 如果数据已经存在，返回null;否则返回新的root
     */
    private AVLNode<T> insert(T data, AVLNode<T> node) {
        if (node == null) {
            return new AVLNode<T>(data, null);
        }

        int cp = compare(data, node.data);
        if (cp < 0) {
            node.left = insert(data, node.left);
            if (nodeHeight(node.left) - nodeHeight(node.right) == 2) {
                if (compare(data, node.left.data) < 0) {
                    node = rotateWithLeftChild(node);
                } else {
                    node = doubleRotateWithLeftChild(node);
                }
            }
            size++;
        } else if (cp > 0) {
            node.right = insert(data, node.right);
            if (nodeHeight(node.right) - nodeHeight(node.left) == 2) {
                if (compare(data, node.right.data) > 0) {
                    node = rotateWithRightChild(node);
                } else {
                    node = doubleRotateWithRightChild(node);
                }
            }
            size++;
        }
        modCount++;
        node.height = (byte) (Math.max(nodeHeight(node.left), nodeHeight(node.right)) + 1);
        return node;
    }

    /**
     * 从给定节点找数据
     *
     * @param data
     * @param node
     *
     * @return 数据所在的阶段，如果不存在，返回null
     */
    public AVLNode<T> find(T data, AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        int cp = compare(data, node.data);
        if (cp > 0) {
            return find(data, node.right);
        } else if (cp < 0) {
            return find(data, node.left);
        }
        if (node.deleted) {
            return null;
        } else {
            return node;
        }
    }

    /**
     * 中序遍历
     *
     * @param <T>
     */
    final class AVLIterator<T> implements Iterator<T> {
        private AVLNode next;
        private int currentModCount;

        AVLIterator(AVLNode<T> first) {
            next = first;
            currentModCount = AVLSet.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            AVLNode<T> t = next;
            next = successor(next);
            if (t == null) {
                throw new NoSuchElementException();
            }
            if (currentModCount != AVLSet.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (next != null && !next.deleted) {
                return (T) next.data;
            }
            return null;
        }

        @Override
        public void remove() {
            next.deleted = true;
            size--;
            currentModCount = AVLSet.this.modCount;
        }
    }

    /**
     * Returns the successor of the specified Entry, or null if no such.
     */
    static <T> AVLNode<T> successor(AVLNode<T> t) {
        if (t == null)
            return null;
        else if (t.right != null) {
            AVLNode<T> p = t.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        } else {
            AVLNode<T> p = t.parent;
            AVLNode<T> ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private static class AVLNode<T> {
        //数据
        private T data;
        //数节点高度
        private byte height;
        //左孩子
        private AVLNode<T> left;
        //右孩子
        private AVLNode<T> right;

        private AVLNode<T> parent;
        //删除的时候标记为true，节省开销
        private boolean deleted;

        AVLNode(T data, AVLNode<T> parent) {
            this(data, parent, null, null);
        }

        AVLNode(T data, AVLNode<T> parent, AVLNode<T> left, AVLNode<T> right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
            deleted = false;
        }
    }
}
