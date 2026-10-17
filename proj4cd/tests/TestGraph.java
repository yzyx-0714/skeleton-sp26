import main.DirectedGraph;
import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class TestGraph {

    @Test
    public void addEdgeText() {
        DirectedGraph digraph = new DirectedGraph();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == j) continue;
                digraph.addEdge(i,j);
            }
        }
        digraph.addEdge(0,5);
        digraph.addEdge(4,6);
        /**  0: 1, 2, 3, 4, 5
         *   1: 0, 2, 3, 4
         *   2: 0, 1, 3, 4
         *   3: 0, 1, 2, 4
         *   4: 0, 1, 2, 3, 6
         * */
        Set<Integer> example1 = digraph.getHyponyms(0);
        Set<Integer> example2 = digraph.getHyponyms(3);
        Set<Integer> example3 = digraph.getHyponyms(6);

        Set<Integer> expect1 = new HashSet<>();
        for (int i = 0; i <= 6; i++) {
            expect1.add(i);
        }

        Set<Integer> expect3 = new HashSet<>();
        expect3.add(6);

        assertThat(example1).isEqualTo(expect1);
        assertThat(example2).isEqualTo(expect1);
        assertThat(example3).isEqualTo(expect3);


    }
}
