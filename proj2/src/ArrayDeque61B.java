import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;


    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        resizeUp();
        items[nextFirst] = x;
        size += 1;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        resizeUp();
        items[nextLast] = x;
        size += 1;
        nextLast = (nextLast + 1) % items.length;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int start = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            returnList.add(items[(start + i) % items.length]);
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size <= 0;
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
        if (this.isEmpty() || size == 0) {
            return null;
        }
        return items[(nextFirst + 1) % items.length];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (this.isEmpty() || size == 0) {
            return null;
        }
        return items[(nextLast - 1 + items.length) % items.length];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        nextFirst = (nextFirst + 1) % items.length;
        T rmItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        resizeDown();
        return rmItem;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        nextLast = (nextLast - 1 + items.length) % items.length;
        T rmItem = items[nextLast];
        items[nextLast] = null;
        size--;
        resizeDown();
        return rmItem;
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
        return items[(nextFirst + 1 + index) % items.length];
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
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    public void resizeUp() {
        if (size == items.length) {
            T[] newitems = (T[]) new Object[items.length * 2];
            for (int i = 0; i < size; i++) {
                newitems[i] = this.get(i);
            }
            nextFirst = newitems.length - 1;
            nextLast = size;
            items = newitems;
        }

    }

    public void resizeDown() {
        if (items.length >= 16 && size <= items.length * 0.25) {
            T[] newitems = (T[]) new Object[items.length / 2];
            for (int i = 0; i < size; i++) {
                newitems[i] = this.get(i);
            }
            nextFirst = newitems.length - 1;
            nextLast = size;
            items = newitems;
        }
    }

    private class ArrayDeque61BIterator implements Iterator<T> {

        private int pointer;

        ArrayDeque61BIterator() {
            pointer = (nextFirst + 1) % items.length;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if (pointer < nextLast) {
                return true;
            }
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            T toReturn = items[pointer];
            pointer += 1;
            return toReturn;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {return true;}
        if (!(other instanceof ArrayDeque61B<?> otherArray)) {
            return false;
        }
        if (this.size != otherArray.size) {return false;}

        Iterator<T> thisIter =  this.iterator();
        Iterator<?> otherIter = otherArray.iterator();

        while (thisIter.hasNext()) {
            T item1 = thisIter.next();
            Object item2 = otherIter.next();
            if (!Objects.equals(item1, item2)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String toReturnString = "[";
        int index = 0;
        for (T x : this) {
            toReturnString += x;
            index++;
            if (index < size) {
                toReturnString += ", ";
            }

        }
        toReturnString += "]";
        return toReturnString;
    }

    public static void main() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        System.out.println(ad);
        }
    }


