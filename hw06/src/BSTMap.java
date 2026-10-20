import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    //字节点
    private class BST {
        K key;
        V value;
        BST left;
        BST right;
        //构造函数
        public BST(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }
    private BST root;
    private int size;

    private BST insert(BST T, K ik, V iv) {
        if (T == null) {
            size++;
            return new BST(ik, iv);
        }
        if (T.key.compareTo(ik) > 0) {
            T.left = insert(T.left, ik, iv);
        } else if (T.key.compareTo(ik) < 0) {
            T.right = insert(T.right, ik, iv);
        } else if (T.key.compareTo(ik) == 0) {
            T.value = iv;
        }
        return T;
    }

    private BST find(BST T, K ik) {
        if (T == null) {
            return null;
        }
        if (T.key.compareTo(ik) == 0) {
            return T;
        } else if (T.key.compareTo(ik) > 0) {
            return find(T.left, ik);
        } else {
            return find(T.right, ik);
        }
    }

    private void inorder(BST T, List<K> list) {
        if (T == null) {
            return;
        }
        inorder(T.left, list);
        list.add(T.key);
        inorder(T.right, list);
    }

    private BST delect(BST T, K ik) {
        if (T == null) {
            return null;
        }
        //判断往哪里删
        if (T.key.compareTo(ik) > 0) {
            T.left = delect(T.left, ik);
        } else if (T.key.compareTo(ik) < 0) {
            T.right = delect(T.right, ik);
        } else {
            if (T.left == null && T.right == null) {
                return null;
            }
            if (T.left == null) {
                return T.right;
            }
            if (T.right == null) {
                return T.left;
            }
            BST min = findMin(T.right);
            T.key = min.key;
            T.value = min.value;
            T.right = delect(T.right, min.key);
        }
        return T;
    }

    private BST findMin(BST T) {
        while (T.left != null) {
            T = T.left;
        }
        return T;
    }

    /**
     * 将指定的值与该映射中的指定键关联起来。
     * 如果映射已经包含指定的键，则用指定的值替换该键的映射。
     *
     * @param key 键
     * @param value 值
     */
    @Override
    public void put(K key, V value) {
        this.root = insert(root, key, value);
    }

    /**
     * 返回指定键所映射的值；如果此映射不包含该键的映射，则返回 null。
     *
     * @param key 键
     */
    @Override
    public V get(K key) {
        BST result = find(root, key);
        if (result == null) {
            return null;
        }
        return result.value;
    }

    /**
     * 返回此映射是否包含指定键的映射。
     *
     * @param key 键
     */
    @Override
    public boolean containsKey(K key) {
        BST result = find(root, key);
        if (result == null) {
            return false;
        }
        return true;
    }

    /**
     * 返回此映射中的键值映射关系数。
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 移除此映射中的所有映射关系。
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 返回此映射中包含的键的 Set 视图。Lab 7 不要求实现。
     * 如果不实现此方法，请抛出 UnsupportedOperationException 异常。
     */
    @Override
    public Set<K> keySet() {
        List<K> keySet = new ArrayList<>();
        inorder(root, keySet);
        return new HashSet<>(keySet);
    }

    /**
     * 如果存在，则从该映射中移除指定键的映射关系；
     * 如果不存在这样的映射，则返回 null。
     * Lab 7 不要求实现。如果不实现此方法，请抛出
     * UnsupportedOperationException 异常。
     *
     * @param key 键
     */
    @Override
    public V remove(K key) {
        BST target = find(root, key);
        if (target == null) {
            return null;
        }
        V oldValue = target.value;
        delect(root, key);
        size--;
        return oldValue;
    }

    /**
     * 返回 {@code T} 类型元素的迭代器。
     *
     * @return 一个迭代器。
     */
    @Override
    public Iterator<K> iterator() {
        List<K> list = new ArrayList<>();
        inorder(root, list);
        return list.iterator();
    }
}