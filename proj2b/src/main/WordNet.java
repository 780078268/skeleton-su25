package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private final Graph graph;
    private final Map<Integer, LinkedList<String>> idToWords = new HashMap<>();
    private  Map<String, LinkedList<Integer>> wordsToIds;
    public WordNet(String synsetsFile, String hyponymsFile) {
        wordsToIds = new HashMap<>();
        In in1 = new In(synsetsFile);
        In in2 = new In(hyponymsFile);
        int maxId = -1;
        In inSynsets = new In(synsetsFile);
        while (inSynsets.hasNextLine()) {
            String line = inSynsets.readLine();
            String[] parts = line.split(",", 3);
            int currentId = Integer.parseInt(parts[0]);
            if (currentId > maxId+1) {
                maxId = currentId;
            }
        }
        while (in1.hasNextLine()) {
            String line = in1.readLine();
            String[] parts = line.split(",", 3);
            int id = Integer.parseInt(parts[0]);
            String[] synsetWords = parts[1].split(" ");
            idToWords.put(id, new LinkedList<>(Arrays.asList(synsetWords)));
            for (String singleWord : synsetWords) {
                if (!wordsToIds.containsKey(singleWord)) {
                    wordsToIds.put(singleWord, new LinkedList<>());
                }
                wordsToIds.get(singleWord).add(id);
            }
        }
        this.graph = new Graph(maxId);
        while (in2.hasNextLine()) {
            String line = in2.readLine();
            String[] words = line.split(",");
            int from = Integer.parseInt(words[0]);
            for (int i = 1; i < words.length; i++) {
                int to = Integer.parseInt(words[i]);
                graph.addEdge(from, to);
            }
        }
    }

    public Set<String> hyponyms(String word) {
        if (wordsToIds.containsKey(word)) {

            Set<String> returnSet = new HashSet<>();
            LinkedList<Integer> ids = wordsToIds.get(word);
            Set<Integer> set = graph.getAllReachable(ids);
            Set<String> backSet = new HashSet<>();
            for (Integer i : set) {
                backSet.addAll(idToWords.get(i));
            }
            return backSet;
        }
        return new HashSet<>();
    }
}
