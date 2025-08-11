import java.util.*;

public class Graph {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }


    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        for (Edge e : adjLists[v1]) {
            if (e.to == v2) {
                e.weight = weight;
                return;
            }
        }
        Edge e = new Edge(v1, v2, weight);
        adjLists[v1].add(e);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for (Edge e : adjLists[from]) {
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        LinkedList<Integer> returnList = new LinkedList<>();
        for (Edge e : adjLists[v]) {
            returnList.add(e.to);
        }
        return returnList;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int returnValue = 0;
        for (LinkedList<Edge> adjList : adjLists) {
            for (Edge e : adjList) {
                if (e.to == v) {
                    returnValue++;
                }
            }
        }
        return returnValue;
    }

    /* Returns a list of the vertices that lie on the shortest path from start to stop. 
    If no such path exists, you should return an empty list. If START == STOP, returns a List with START. */
    public ArrayList<Integer> shortestPath(int start, int stop) {
        ArrayList<Integer> returnList = new ArrayList<>();
        HashMap<Integer, Integer> distance = new HashMap<>();
        HashMap<Integer, Integer> parents = new HashMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        for (int i = 0; i < adjLists.length; i++) {
            distance.put(i, Integer.MAX_VALUE);
        }
        distance.put(start, 0);
        pq.add(start);
        while (!pq.isEmpty()) {
            int current = pq.poll();
            if (current == stop) {
                break;
            }
            for (Edge e : adjLists[current]) {
                int to = e.to;
                int weight = e.weight;
                if (distance.get(current) == Integer.MAX_VALUE) {
                    continue;
                }
                int temp = distance.get(current) + weight;
                if (temp < distance.get(to)) {
                    distance.put(to, temp);
                    parents.put(to, current);
                    pq.add(to);
                }
            }
        }
        if (distance.get(stop) == Integer.MAX_VALUE) {
            return returnList;
        }

        int current = stop;
        while (current != start) {
            returnList.add(current);
            current = parents.get(current);
        }
        returnList.add(start);
        Collections.reverse(returnList);
        return returnList;
    }

    private Edge getEdge(int v1, int v2) {
        for (Edge e : adjLists[v1]) {
            if (e.to == v2) {
                return e;
            }
        }
        return null;
    }

    private class Edge {
        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

        public int to() {
            return this.to;
        }

    }



    
}
