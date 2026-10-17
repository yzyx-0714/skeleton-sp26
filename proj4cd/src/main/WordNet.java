package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Map<Integer, List<String>> synsets;     // 此 节点id 所拥有的词
    private Map<String, List<Integer>> wordToIds;   // 单词 和 此单词所在的节点id
    private DirectedGraph graph;

    public WordNet(String hyponymsEECSFilename, String synsetsFilename) {
        synsets = new HashMap<>();
        wordToIds = new HashMap<>();
        graph = new DirectedGraph();

        // Map<Integer, List<Integer>>
        In in = new In(hyponymsEECSFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] totalID = nextLine.split(",");
            for (int i = 1; i < totalID.length; i++) {
                graph.addEdge(Integer.parseInt(totalID[0]), Integer.parseInt(totalID[i]));
            }
        }

        // Map<Integer, String>
        in = new In(synsetsFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] line = nextLine.split(",");
            Integer id = Integer.parseInt(line[0]);
            String[] word = line[1].split(" ");
            List<String> wordList = new ArrayList<>();
            for (int i = 0; i < word.length; i++) {
                wordList.add(word[i]);
                if (wordToIds.get(word[i]) == null) {
                    List<Integer> idList = new ArrayList<>();
                    idList.add(id);
                    wordToIds.put(word[i], idList);
                } else {
                    wordToIds.get(word[i]).add(id);
                }
            }
            synsets.put(id, wordList);
        }
    }

    public List<Integer> getSynsetIds(String word) {
        return wordToIds.get(word);
    }

    public List<Integer> getDirectChildren(Integer id) {
        return graph.idToList(id);
    }

    public List<String> getWords(Integer id) {
        return synsets.get(id);
    }

    public List<String> hyponyms(String word) {
        if (wordToIds.get(word) == null || wordToIds.get(word).isEmpty()) {
            return new ArrayList<>();
        }
        List<String> resultList = listIdToWord(word);
        resultList.sort(Comparator.naturalOrder());
        return resultList;
    }

    private List<String> listIdToWord(String word) {//扫的是所有下位词列表
        Set<String> wordSet = new HashSet<>();
        for (Integer id : wordToIds.get(word)) {  //遍历word所在的id节点
            for (Integer resultId : graph.getHyponyms(id)) {
                wordSet.addAll(synsets.get(resultId));
            }
        }
        return new ArrayList<>(wordSet);
    }

    public List<String> hyponyms(List<String> words) {
        if (words == null || words.isEmpty()) {
            return new ArrayList<>();
        }
        Set<String> wordSet = new HashSet<>(hyponyms(words.get(0)));
        for (int i = 1; i < words.size(); i++) {
            wordSet.retainAll(listIdToWord(words.get(i)));
            if (wordSet.isEmpty()) {
                return new ArrayList<>();
            }
        }

        List<String> resultList = new ArrayList<>(wordSet);
        resultList.sort(Comparator.naturalOrder());
        return resultList;
    }
}
