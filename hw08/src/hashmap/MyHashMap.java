package hashmap;

import java.util.*;

/**
 *  基于哈希表实现的 Map。
 *
 *  假定不会插入 null 键，并且在 remove() 后不会缩容。
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {



    /**
     * 将指定值与指定键关联到这个 map 中。
     * 如果 map 已经包含指定键，则用指定值替换该键原来的映射。
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        int keyHash = key.hashCode();
        int index = Math.floorMod(keyHash, buckets.length);
        for (Node item : buckets[index]) {
            if (item.key.equals(key)) {
                item.value = value;
                return;
            }
        }
        size++;
        buckets[index].add(new Node(key, value));

        if ((double) size / buckets.length > loadFactor) {
            resize();
        }

    }

    /**
     * 返回指定键所映射的值；如果这个 map 中没有该键的映射，则返回 null。
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * 返回这个 map 是否包含指定键的映射。
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回这个 map 中键值对映射的数量。
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 移除这个 map 中的所有映射。
     */
    @Override
    public void clear() {
        size = 0;
        buckets = new Collection[buckets.length];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * 返回包含在此 map 中所有键的 Set 视图。本次实验不要求实现。
     * 如果不实现这个方法，请抛出 UnsupportedOperationException。
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> nodeArray : buckets) {
            for (Node node : nodeArray) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }

    /**
     * 如果指定键存在，则从这个 map 中移除该键的映射；
     * 如果不存在这样的映射，则返回 null。
     * 本次实验不要求实现。如果不实现这个方法，请抛出
     * UnsupportedOperationException。
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        for (Collection<Node> nodeArray : buckets) {
            for (Node node : nodeArray) {
                if (node.key.equals(key)) {
                    V value = node.value;
                    nodeArray.remove(node);
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 返回一个遍历 {@code T} 类型元素的迭代器。
     *
     * @return 一个 Iterator
     */
    @Override
    public Iterator<K> iterator() {
        List<K> list = new ArrayList<>();
        for (Collection<Node> nodeArray : buckets) {
            for (Node node : nodeArray) {
                list.add(node.key);
            }
        }
        return list.iterator();
    }

    /**
     * 用于存储键值对的 protected 辅助类
     * protected 限定符允许子类访问
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* 实例变量 */
    private Collection<Node>[] buckets;
    // 你可能还需要再定义一些！
    private int size;
    private double loadFactor;

    /** 构造器 */
    public MyHashMap() {
        this.loadFactor = 0.75;
        this.size = 0;
        buckets = new Collection[16];
        for (int i = 0; i < 16; i++) {
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialCapacity) {
        this.loadFactor = 0.75;
        this.size = 0;
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap 构造器，创建长度为 initialCapacity 的底层数组。
     * 负载因子（元素数量 / 桶数量）应始终 <= loadFactor
     *
     * @param initialCapacity 底层数组的初始大小
     * @param loadFactor 最大负载因子
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        this.size = 0;
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * 返回一个作为哈希表桶的数据结构
     *
     * 对哈希表桶的唯一要求是：
     *  1. 插入元素（`add` 方法）
     *  2. 移除元素（`remove` 方法）
     *  3. 遍历元素（`iterator` 方法）
     *  注意这里指的是哈希表桶本身，
     *  而不是哈希表本身。
     *
     * 这些方法都由 java.util.Collection 支持，
     * Java 中的大多数数据结构都继承自 Collection，
     * 因此我们几乎可以把任意数据结构用作桶。
     *
     * 重写这个方法，即可使用不同的数据结构作为底层桶类型。
     *
     * 务必调用这个工厂方法，而不是使用 new 运算符
     * 自行创建桶数据结构！
     */
    protected Collection<Node> createBucket() {
        // TODO：补全这个方法。
        return new LinkedList<>();
    }

    // TODO：实现下面 Map61B 接口中的方法
    // 在完成之前，你的代码无法编译！
    private void resize() {
        Collection<Node>[] newBucket = new Collection[buckets.length * 2];
        for (int i = 0; i < newBucket.length; i++) {
            newBucket[i] = createBucket();
        }
        for (Collection<Node> nodeArray : buckets) {
            for (Node node : nodeArray) {
                int index = Math.floorMod(node.key.hashCode(), newBucket.length);
                newBucket[index].add(node);
            }
        }
        this.buckets = newBucket;
    }
}
