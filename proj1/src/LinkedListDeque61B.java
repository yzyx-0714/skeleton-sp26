import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;

    public  class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public LinkedListDeque61B() {
        sentinel =  new Node(null, null, null);
        size = 0;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node start = new Node(null, x, null);
//        if (size == 0) {
//            sentinel.prev = start;
//            start.next = sentinel;
//            sentinel.next = start;
//            start.prev = sentinel;
//            size += 1;
//            return;
//        }
        sentinel.next.prev = start;
        start.next = sentinel.next;
        sentinel.next = start;
        start.prev = sentinel;
        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node end = new Node(null, x, null);
        sentinel.prev.next = end;
        end.prev = sentinel.prev;
        sentinel.prev = end;
        end.next = sentinel;
        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (this.size == 0) {
            return returnList;
        }
        Node ex = sentinel.next;
        while (ex != sentinel) {
            returnList.add(ex.item);
            ex = ex.next;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     * 判断双端队列是否为空。不会修改双端队列本身
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     *         若双端队列中不包含任何元素，返回 {@code true}；否则返回 {@code false}
     */

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        return sentinel.next.item;
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        return sentinel.prev.item;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        return first;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T first = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return first;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node result = sentinel.next;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursive2(index, sentinel.next);
    }

    private T getRecursive2(int index, Node ex) {
        if (index == 0) {
            return ex.item;
        }
        return getRecursive2(index - 1, ex.next);
    }

    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);   // [0]
         lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}
