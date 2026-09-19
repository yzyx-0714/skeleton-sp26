package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryTextHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
            TimeSeries ts = ngm.weightHistory(word, startYear, endYear);
            response += word + ": " + ts.toString() + "\n";
        }
        return response;
    }
}
