package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;
    private final NGramMap ngram;

    public HyponymsHandler(WordNet wordNet, NGramMap ngram) {
        this.wordNet = wordNet;
        this.ngram = ngram;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> result = wordNet.hyponyms(q.words());
        if (q.k() == 0) { return result.toString(); }


        return getMostK(result, q.startYear(), q.endYear(), q.k()).toString();
    }

    private List<String> getMostK(List<String> hyponymsList,  int startYear, int endYear, int k) {
        List<Map.Entry<String, Double>> wordToCountList = new ArrayList<>();
        for (String word : hyponymsList) {
            Map<Integer, Double> yearToNumber = ngram.countHistory(word, startYear, endYear);
            Double total = 0.0;
            for (Integer year : yearToNumber.keySet()) {
                total += yearToNumber.get(year);
            }
            if (total > 0) {
                wordToCountList.add(Map.entry(word, total));
            }
        }

        wordToCountList.sort((a,b) -> Double.compare(b.getValue(), a.getValue()));
        int time = k;
        if (wordToCountList.size() < k) {
            time = wordToCountList.size();
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i < time; i++) {
            result.add(wordToCountList.get(i).getKey());
        }

        result.sort(Comparator.naturalOrder());
        return result;
    }
}
