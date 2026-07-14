import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void addFirstTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void AddLastTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

    }

    @Test
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);  // -2, -1, 0, 1, 2

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();

    }

    @Test
    public void getFirstTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);   // -2, -1, 0, 1, 2

        assertThat(lld1.getFirst()).isEqualTo(-2);

    }

    @Test
    public void getLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);  // -2, -1, 0, 1, 2

        assertThat(lld1.getLast()).isEqualTo(2);

    }

    @Test
    public void getTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        assertThat(lld1.get(0)).isEqualTo(null);
        assertThat(lld1.get(3)).isEqualTo(null);

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);  // -2, -1, 0, 1, 2

        assertThat(lld1.get(-8)).isEqualTo(null);
        assertThat(lld1.get(8)).isEqualTo(null);
        assertThat(lld1.get(10)).isEqualTo(null);
        assertThat(lld1.get(0)).isEqualTo(-2);
        assertThat(lld1.get(2)).isEqualTo(0);
        assertThat(lld1.get(4)).isEqualTo(2);
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
    }
    @Test
    public void testSizeZero() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);
    }
    @Test
    public void testSizeOne() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(1);
        assertThat(lld1.size()).isEqualTo(1);
    }
    @Test
    public void testSizeAndIsEmpty() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addFirst(1);
        lld1.addLast(2);
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);  // -2, -1, 0, 1, 2

        assertThat(lld1.removeFirst()).isEqualTo(-2);
        assertThat(lld1.toList()).containsExactly(-1, 0, 1, 2).inOrder();

    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);  // -2, -1, 0, 1, 2

        assertThat(lld1.removeLast()).isEqualTo(2);
        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1).inOrder();

    }

    @Test
    public void resizeUpTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(2);
        lld1.addFirst(4);
        lld1.addLast(-8);
        lld1.addFirst(-2);
        lld1.addLast(1);
        lld1.addLast(6);
        lld1.addFirst(-6);

        assertThat(lld1.toList()).containsExactly(-6, -2, 4, 0, 2, -8, 1, 6).inOrder();

        lld1.addLast(99);
        lld1.addFirst(88);
        lld1.addLast(14);
        lld1.addFirst(65);
        lld1.addLast(91);
        lld1.addFirst(24);

        assertThat(lld1.toList()).containsExactly(24, 65, 88, -6, -2, 4, 0, 2, -8, 1, 6, 99, 14, 91).inOrder();

        lld1.addLast(100);
        lld1.addFirst(33);
        lld1.addLast(77);

        assertThat(lld1.toList()).containsExactly(33, 24, 65, 88, -6, -2, 4, 0, 2, -8, 1, 6, 99, 14, 91, 100, 77).inOrder();

        lld1.removeFirst();
        lld1.removeLast();
        lld1.addFirst(999);
        lld1.addLast(888);

        assertThat(lld1.toList()).containsExactly(999, 24, 65, 88, -6, -2, 4, 0, 2, -8, 1, 6, 99, 14, 91, 100, 888).inOrder();

    }

    @Test
    public void resizeDownTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(2);
        lld1.addFirst(4);
        lld1.addLast(-8);
        lld1.addFirst(-2);
        lld1.addLast(1);
        lld1.addLast(6);
        lld1.addFirst(-6);
        lld1.addLast(99);
        lld1.addFirst(88);
        lld1.addLast(14);
        lld1.addFirst(65);
        lld1.addLast(91);
        lld1.addFirst(24);
        lld1.addLast(100);
        lld1.addFirst(33);  // 33, 24, 65, 88, -6, -2, 4, 0, 2, -8, 1, 6, 99, 14, 91, 100

        assertThat(lld1.toList()).containsExactly(33, 24, 65, 88, -6, -2, 4, 0, 2, -8, 1, 6, 99, 14, 91, 100).inOrder();

        for (int i = 0; i < 8; i++) {
            lld1.removeFirst();
        }
        assertThat(lld1.toList()).containsExactly(2, -8, 1, 6, 99, 14, 91, 100).inOrder();

        for (int i = 0; i < 4; i++) {
            lld1.removeFirst();
        }
        assertThat(lld1.toList()).containsExactly(99, 14, 91, 100).inOrder();

        for (int i = 0; i < 2; i++) {
            lld1.removeFirst();
        }
        assertThat(lld1.toList()).containsExactly(91, 100).inOrder();

        lld1.addFirst(999);
        lld1.addLast(888);
        assertThat(lld1.toList()).containsExactly(999, 91, 100, 888).inOrder();
    }

    @Test
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front"); // after this call we expect: ["front"]
        ad.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad).containsExactly("front", "middle", "back");
    }

    //加测试

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        assertThat(ad).isEqualTo(ad2);
    }
}


