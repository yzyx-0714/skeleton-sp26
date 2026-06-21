import edu.princeton.cs.algs4.In;

public class IntList {
    int first;
    IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrRecursiveDestructive(IntList L, int x) {
        // TODO: Fill in this code
        if (L == null){
            return L;
        }
        L.first = L.first + x;
        incrRecursiveDestructive(L.rest, x);
        return L;
    }

    /*
     * =================================================================
     * OPTIONAL METHODS
     * =================================================================
     */

    /**
     * Returns the sum of all elements in the IntList.
     */
    public int sum() {
        // Optional: Fill in this code
        int total = 0;
        IntList ex = this;
        while (ex != null) {
            total += ex.first;
            ex = ex.rest;
        }
        return total;
    }

    /**
     * Destructively adds x to the end of the list.
     */
    public void addLast(int x) {
        // Optional: Fill in this code
        IntList ex = this;
        while (ex.rest != null) {
            ex = ex.rest;
        }
        ex.rest = new IntList(x, null);
    }

    /**
     * Destructively adds x to the front of this IntList.
     * This is a bit tricky to implement. The standard way to do this would be
     * to return a new IntList, but for practice, this implementation should
     * be destructive.
     */
    public void addFirst(int x) {
        // Optional: Fill in this code
        IntList end = this.rest;
        int first_ed = this.first;
        this.first = x;
        this.rest = new IntList(first_ed, end);
    }

    public static void main() {
        IntList L = new IntList(5,null);
        L.rest = new IntList(10,null);
        L.rest.rest = new IntList(15,null);
        incrRecursiveDestructive(L, 3);
    }
}
