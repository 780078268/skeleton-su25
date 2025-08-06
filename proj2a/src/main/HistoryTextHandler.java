package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;
import java.util.TreeMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;

    public HistoryTextHandler(NGramMap map){
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {

        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

//        StringBuilder sb = new StringBuilder();
//        for (String word : words) {
//            sb.append(word).append(": ").append(ngm.countHistory(word, startYear, endYear));
//        }
//        return sb.toString();

        String text = "";
        for(String word : words){
            text = text + word + ": ";
            text = text + ngm.weightHistory(word, startYear, endYear) + "\n";
        }
        return text;
    }
}
