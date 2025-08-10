package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordnet;
    private final NGramMap ngramMap;

    public HyponymsHandler(WordNet wn, NGramMap ngramMap) {
        this.wordnet = wn;
        this.ngramMap = ngramMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        int i = q.k();
        int start = q.startYear();
        int end = q.endYear();
        List<String> words =  q.words();
        if (words.isEmpty()) {
            return "[]";
        }
        Set<String> commonHyponyms = new HashSet<>(wordnet.hyponyms(words.get(0)));
        for (int j = 1; j < words.size(); j++) {
            commonHyponyms.retainAll(wordnet.hyponyms(words.get(j)));
        }
        if (i == 0) {
            LinkedList<String> sortedList = new LinkedList<>(commonHyponyms);
            Collections.sort(sortedList);
            return sortedList.toString();
        }
        HashMap<String, Double> dataMap = new HashMap<>(commonHyponyms.size());
        for (String findWord : commonHyponyms) {
            TimeSeries wordHistory = ngramMap.countHistory(findWord, start, end);
            double totalCount = 0;
            for (double count : wordHistory.values()) {
                totalCount += count;
            }
            if (totalCount > 0) {
                dataMap.put(findWord, totalCount);
            }
        }
        LinkedList<String> sortedList = new LinkedList<>(dataMap.keySet());
        sortedList.sort((word1, word2) -> {
            Double count2 = dataMap.get(word2);
            Double count1 = dataMap.get(word1);
            int countComparison = count2.compareTo(count1);

            if (countComparison != 0) {
                return countComparison;
            } else {
                return word1.compareTo(word2);
            }
        });
        List<String> returnList = new LinkedList<>();
        if (sortedList.size() > q.k()) {
            returnList = sortedList.subList(0, q.k());
        } else {
            returnList = sortedList;
        }
        List<String> finalReturnList = returnList;
        Collections.sort(returnList);
        return new ArrayList<>(returnList).toString();
    }
}
