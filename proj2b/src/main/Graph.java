package main;

import java.util.*;

public class Graph {
    ArrayList<LinkedList<Integer>> neighborList;
    public Graph(int n) {
        neighborList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            LinkedList<Integer> addList = new LinkedList<>();
            neighborList.add(addList);
        }
    }

    public void addNode(int id) {
        neighborList.add(id, new LinkedList<>());
    }

    public void addEdge(int from, int to) {
        if (from < neighborList.size()) {
            neighborList.get(from).add(to);
        }
    }

    public LinkedList<Integer> getNeighbors(int id) {
        if (id < neighborList.size()) {
            return neighborList.get(id);
        }
        return new LinkedList<>();
    }

    public Set<Integer> getAllReachable(LinkedList<Integer> list) {
        Queue<Integer> queue = new LinkedList<>(list);
        Set<Integer> visited = new HashSet<>(list);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (Integer neighbor : getNeighbors(node)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return visited;
    }

}

