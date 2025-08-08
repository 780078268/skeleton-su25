import org.junit.Test;
import java.util.List;
import static com.google.common.truth.Truth.*;

public class TestGraph {

    /**
     * Helper method to create a simple directed graph for testing.
     * 0 -> 1 -> 2
     * |
     * v
     * 3 -> 4
     */
    private Graph buildSimpleGraph() {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 3);
        g.addEdge(3, 4);
        return g;
    }

    /**
     * Helper method to create a directed graph with a cycle.
     * 0 -> 1 -> 2
     * ^    |
     * |    v
     * +--- 3
     */
    private Graph buildCyclicGraph() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 0); // Cycle back to 0
        return g;
    }

    // ========================================================================
    // Path and PathExists Tests
    // ========================================================================

    @Test
    public void testPathExistsOnEmptyGraph() {
        Graph g = new Graph(3);
        assertWithMessage("Path should not exist in a graph with no edges")
                .that(g.pathExists(0, 1)).isFalse();
    }

    @Test
    public void testPathExistsToSelf() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        assertWithMessage("Path should always exist from a vertex to itself")
                .that(g.pathExists(0, 0)).isTrue();
    }

    @Test
    public void testSimplePath() {
        Graph g = buildSimpleGraph();

        assertWithMessage("Path from 0 to 2 should exist")
                .that(g.pathExists(0, 2)).isTrue();
        assertWithMessage("Path from 0 to 4 should exist")
                .that(g.pathExists(0, 4)).isTrue();

        List<Integer> pathTo2 = g.path(0, 2);
        List<Integer> pathTo4 = g.path(0, 4);

        assertThat(pathTo2).containsExactly(0, 1, 2).inOrder();
        assertThat(pathTo4).containsExactly(0, 3, 4).inOrder();
    }

    @Test
    public void testNoPathExists() {
        Graph g = buildSimpleGraph();

        assertWithMessage("Path from 2 to 4 should not exist")
                .that(g.pathExists(2, 4)).isFalse();
        assertWithMessage("Path from 4 to 0 should not exist (directed graph)")
                .that(g.pathExists(4, 0)).isFalse();

        List<Integer> path = g.path(2, 4);
        assertThat(path).isEmpty();
    }

    @Test
    public void testPathInCyclicGraph() {
        Graph g = buildCyclicGraph();

        assertWithMessage("Path from 0 to 3 should exist in cyclic graph")
                .that(g.pathExists(0, 3)).isTrue();
        assertWithMessage("Path from 3 to 1 should exist in cyclic graph")
                .that(g.pathExists(3, 1)).isTrue();

        List<Integer> pathTo3 = g.path(0, 3);
        assertThat(pathTo3).containsExactly(0, 1, 3).inOrder();
    }

    // ========================================================================
    // DFS Tests
    // ========================================================================

    @Test
    public void testDfsTraversalOrder() {
        Graph g = new Graph(5);
        // Add edges such that smaller vertices are preferred first in DFS
        // 0 -> 4, 0 -> 2, 0 -> 1
        g.addEdge(0, 4);
        g.addEdge(0, 2);
        g.addEdge(0, 1);
        g.addEdge(2, 3);

        List<Integer> dfsResult = g.dfs(0);
        // Your DFSIterator sorts neighbors to visit smaller ones first.
        // So from 0, it should visit 1, then 2 (and its neighbor 3), then 4.
        assertThat(dfsResult).containsExactly(0, 1, 2, 3, 4).inOrder();
    }

    // ========================================================================
    // Topological Sort Tests
    // ========================================================================

    @Test
    public void testTopologicalSortOnDAG() {
        // This graph is a Directed Acyclic Graph (DAG)
        Graph g = buildSimpleGraph();

        List<Integer> sorted = g.topologicalSort();
        // A valid topological sort could be [0, 1, 3, 2, 4]
        // Another could be [0, 3, 1, 4, 2]
        // We will just check if the ordering is valid.
        assertWithMessage("Topological sort should have all vertices")
                .that(sorted.size()).isEqualTo(5);

        // Check that for every edge u -> v, u appears before v in the list.
        for (int u = 0; u < 5; u++) {
            for (int v : g.neighbors(u)) {
                assertWithMessage("For edge %s->%s, %s should appear before %s", u, v, u, v)
                        .that(sorted.indexOf(u)).isLessThan(sorted.indexOf(v));
            }
        }
    }

    @Test
    public void testTopologicalSortOnCyclicGraph() {
        Graph g = buildCyclicGraph();
        List<Integer> sorted = g.topologicalSort();

        // A graph with a cycle cannot have a full topological sort.
        // Depending on implementation, it might be empty or incomplete.
        // A robust implementation should return a sort with fewer than all vertices.
        assertWithMessage("Topological sort of a cyclic graph should be incomplete")
                .that(sorted.size()).isLessThan(4);
    }

    @Test
    public void testPathOnCompleteUndirectedGraph() {
        int vertexCount = 20;
        Graph g = new Graph(vertexCount);
        for (int i = 0; i < vertexCount; ++i)
            for (int j = i + 1; j < vertexCount; ++j)
                g.addUndirectedEdge(i, j);

        List<Integer> path = g.path(0, vertexCount / 2);
        assertWithMessage(path.toString()).that(path.size()).isGreaterThan(1);
    }
}