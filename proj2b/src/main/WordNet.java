package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private final Graph graph;
    private final Map<Integer, LinkedList<String>> idToWords = new HashMap<>();
    private  Map<String, LinkedList<Integer>> wordsToIds;
    // 文件: WordNet.java (最终正确版本)

    public WordNet(String synsetsFile, String hyponymsFile) {
        wordsToIds = new HashMap<>();
        int maxId = -1;
        In synsetsReader = new In(synsetsFile);
        while (synsetsReader.hasNextLine()) {
            String[] parts = synsetsReader.readLine().split(",", 2);
            int currentId = Integer.parseInt(parts[0]);
            if (currentId > maxId) {
                maxId = currentId;
            }
        }
        In hyponymsReader = new In(hyponymsFile);
        while (hyponymsReader.hasNextLine()) {
            String[] parts = hyponymsReader.readLine().split(",");
            for (String part : parts) {
                int currentId = Integer.parseInt(part);
                if (currentId > maxId) {
                    maxId = currentId;
                }
            }
        }
        this.graph = new Graph(maxId + 1);
        In synsetsReader2 = new In(synsetsFile);
        while (synsetsReader2.hasNextLine()) {
            String line = synsetsReader2.readLine();
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

        // --- 步骤 4: 添加图的边 ---
        In hyponymsReader2 = new In(hyponymsFile);
        while (hyponymsReader2.hasNextLine()) {
            String line = hyponymsReader2.readLine();
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
