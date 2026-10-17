package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {
        WordNet wordNet = new WordNet(hyponymFile, synsetFile);
        NGramMap ngram = new NGramMap(wordHistoryFile, yearHistoryFile);
        return new HyponymsHandler(wordNet, ngram);
//        throw new RuntimeException("Please fill out AutograderBuddy.java!");
    }
}
