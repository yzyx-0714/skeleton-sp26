package main;

import java.util.*;

public class DirectedGraph {
    private Map<Integer, List<Integer>> hyponymsMap;

    public DirectedGraph() {
        hyponymsMap = new HashMap<>();
    }

    public void addEdge(int start, int hyponyms) {
        if (hyponymsMap.get(start) != null) {
            if (hyponymsMap.get(start).contains(hyponyms)) {
                return;
            }
            hyponymsMap.get(start).add(hyponyms);
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(hyponyms);
            hyponymsMap.put(start, list);
        }
    }

    public Set<Integer> getHyponyms(int start) {
        Set<Integer> visited = new HashSet<>();
        dfs(start, visited);
        return visited;
    }

    private void dfs(int start, Set<Integer> visited) {
        if (visited.contains(start)) return;
        visited.add(start);
        if (hyponymsMap.get(start) == null) return;
        for (int i = 0; i < hyponymsMap.get(start).size(); i++) {
            dfs(hyponymsMap.get(start).get(i), visited);
        }
    }

    public List<Integer> idToList(Integer id) {
        return hyponymsMap.get(id);
    }
}
