package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordnet;
    private final NGramMap ngramMap ;

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
        String word = words.getFirst();
        Set<String> hyponymSet = wordnet.hyponyms(word);
        LinkedList<String> hyponymList = new LinkedList<>(hyponymSet);
        Collections.sort(hyponymList);
        for(String findWord : q.words()) {
            Set<String> hyponymSet2 = wordnet.hyponyms(findWord);
            LinkedList<String> hyponymList2 = new LinkedList<>(hyponymSet2);
            Collections.sort(hyponymList2);
            hyponymList.retainAll(hyponymList2);
        }
        if (i == 0){
            Collections.sort(hyponymList);
            return hyponymList.toString();
        }
        HashMap<String , Double> dataMap = new HashMap<>(hyponymList.size());
        for(String findWord : hyponymList){
            TimeSeries wordHistory = ngramMap.countHistory(findWord, start, end);
            double totalCount = 0;
            for(double count : wordHistory.values()){
                totalCount += count;
            }
            if(totalCount > 0){
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
        List <String> returnList = new LinkedList<>();
        if(sortedList.size() > q.k()){
            returnList = sortedList.subList(0, q.k());
        }else{
            returnList = sortedList;
        }
        Collections.sort(returnList);
        return returnList.toString();
    }
}
