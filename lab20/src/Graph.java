import java.util.*;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }
    /* A Disjoint Set Union (DSU) implementation to assist with Kruskal's algorithm. */
    private class DisjointSetUnion {
        private int[] parent;

        public DisjointSetUnion(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            parent[i] = find(parent[i]); // Path compression
            return parent[i];
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                parent[rootI] = rootJ;
            }
        }

        public boolean isConnected(int i, int j) {
            return find(i) == find(j);
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    public Graph prims(int start) {
        if(getAllVertices().size() > getAllEdges().size()+1) {
            return null;
        }
        HashMap<Integer,Boolean> visited = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparing(Edge::getWeight));
        for(Integer v : getAllVertices()) {
            visited.put(v, false);
        }
        visited.put(start, true);
        Graph returnGraph = new Graph();
        pq.addAll(edges.get(start));
        int totalEdges = 0;
        while (totalEdges <= getAllVertices().size() -1 ) {
            Edge e = pq.poll();
            if (e != null && !visited.get(e.getDest())) {
                int v = e.getDest();
                visited.put(v, true);
                totalEdges++;
                returnGraph.addEdge(e);
                pq.addAll(edges.get(v));
            }
        }
        return returnGraph;
    }

    public Graph kruskals() {
        Graph returnGraph = new Graph();
        for (Integer v : getAllVertices()) {
            returnGraph.addVertex(v);
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparing(Edge::getWeight));
        pq.addAll(allEdges);
        DisjointSetUnion dsu = new DisjointSetUnion(getAllVertices().size());
        int totalEdges = 0;
        while (totalEdges <= getAllVertices().size() -1 ) {
            Edge e = pq.poll();
            int u = e.getSource();
            int v = e.getDest();
            if(!dsu.isConnected(u, v)) {
                dsu.union(u, v);
                totalEdges ++;
                returnGraph.addEdge(e);
            }
        }
        return returnGraph;
    }

    /* A comparator to help you compare vertices in terms of
     * how close they are to the current MST.
     * Feel free to uncomment the below code if you'd like to use it;
     * otherwise, you may implement your own comparator.
     */
//    private class PrimVertexComparator implements Comparator<Integer> {
//        private HashMap<Integer, Edge> distFromTree;
//
//        public PrimVertexComparator(HashMap<Integer, Edge> distFromTree) {
//            this.distFromTree = distFromTree;
//        }
//
//        @Override
//        public int compare(Integer o1, Integer o2) {
//            int edgeCompRes = distFromTree.get(o1).compareTo(distFromTree.get(o2));
//            if (edgeCompRes == 0) {
//                return o1 - o2;
//            }
//            return edgeCompRes;
//        }
//    }
}
