import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static com.google.common.truth.Truth.assertThat;

public class MSTTest {

    private final static String SEPARATOR = System.getProperty("file.separator");
    private final static String INPUT_FOLDER = System.getProperty("user.dir") + SEPARATOR + "test" + SEPARATOR + "inputs";
    private final static String NORMAL = INPUT_FOLDER + SEPARATOR + "graphTestNormal.in";
    private final static String ALL_DISJOINT = INPUT_FOLDER + SEPARATOR + "graphTestAllDisjoint.in";
    private final static String SOME_DISJOINT = INPUT_FOLDER + SEPARATOR + "graphTestSomeDisjoint.in";
    private final static String MULTI_EDGE = INPUT_FOLDER + SEPARATOR + "graphTestMultiEdge.in";

    @Test
    public void testExample() {
        Graph input = loadFromText(NORMAL);
        Graph output = new Graph();
        output.addEdge(0, 2, 1);
        output.addEdge(1, 2, 2);
        output.addEdge(2, 3, 6);

        Graph mst1 = input.prims(0);
        Graph mst2 = input.kruskals();
        assertThat(mst1).isEqualTo(output);
        assertThat(mst2).isEqualTo(output);
    }

    @Test
    public void testEdgeCases() { // 将 test1 重命名，使其含义更清晰
        // --- 测试1：处理非连通图（包含多个连通分量） ---
        System.out.println("测试：非连通图 (SOME_DISJOINT)");
        Graph someDisjoint = loadFromText(SOME_DISJOINT);

        // 对于 Kruskal，它应该找到一个最小生成森林（MSF）
        Graph kruskalExpected = new Graph();
        kruskalExpected.addEdge(0, 1, 1); // 第一个分量的 MST
        kruskalExpected.addEdge(2, 3, 2); // 第二个分量的 MST

        // 对于 Prim，它只会找到包含起点的那个连通分量的 MST
        Graph primsExpected = new Graph();
        primsExpected.addEdge(0, 1, 1);

        Graph kruskalActual = someDisjoint.kruskals();
        Graph primsActual = someDisjoint.prims(0);

        assertThat(kruskalActual).isEqualTo(kruskalExpected);
        assertThat(primsActual).isEqualTo(primsExpected);

        // --- 测试2：处理完全不连通的图（只有顶点，没有边） ---
        System.out.println("测试：无边图 (ALL_DISJOINT)");
        Graph allDisjoint = loadFromText(ALL_DISJOINT);
        Graph emptyGraph = new Graph(); // 预期结果是一个空图（但包含顶点）
        for (int v : allDisjoint.getAllVertices()) {
            emptyGraph.addVertex(v);
        }

        assertThat(allDisjoint.kruskals()).isEqualTo(emptyGraph);
        assertThat(allDisjoint.prims(0)).isEqualTo(emptyGraph);

        // --- 测试3：处理两点间有多条边的情况 ---
        System.out.println("测试：多重边图 (MULTI_EDGE)");
        Graph multiEdge = loadFromText(MULTI_EDGE);
        Graph multiEdgeExpected = new Graph();
        multiEdgeExpected.addEdge(0, 1, 3); // 算法应该选择权重最小的边
        multiEdgeExpected.addEdge(1, 2, 2);

        assertThat(multiEdge.kruskals()).isEqualTo(multiEdgeExpected);
        assertThat(multiEdge.prims(0)).isEqualTo(multiEdgeExpected);

        // --- 测试4：Prim算法从不同起点开始（针对连通图） ---
        System.out.println("测试：Prim算法的不同起点");
        Graph normalGraph = loadFromText(NORMAL);
        Graph mstFrom0 = normalGraph.prims(0);
        Graph mstFrom1 = normalGraph.prims(1);

        // 对于连通图，不同起点产生的MST总权重必须相同
        // 注意：具体的边可能会不同（如果存在相同权重的边），但总权重一定一样
        assertThat(calculateTotalWeight(mstFrom0)).isEqualTo(calculateTotalWeight(mstFrom1));
    }

    /**
     * 一个辅助方法，用于计算图的总权重。
     * @param g 一个图
     * @return 图中所有边的权重之和
     */
    private int calculateTotalWeight(Graph g) {
        int totalWeight = 0;
        for (Edge e : g.getAllEdges()) {
            totalWeight += e.getWeight();
        }
        return totalWeight;
    }

    /* Returns a randomly generated graph with VERTICES number of vertices and
       EDGES number of edges with max weight WEIGHT. */
    public static Graph randomGraph(int vertices, int edges, int weight) {
        Graph g = new Graph();
        Random rng = new Random();
        for (int i = 0; i < vertices; i += 1) {
            g.addVertex(i);
        }
        for (int i = 0; i < edges; i += 1) {
            Edge e = new Edge(rng.nextInt(vertices), rng.nextInt(vertices), rng.nextInt(weight));
            g.addEdge(e);
        }
        return g;
    }

    /* Returns a Graph object with integer edge weights as parsed from
       FILENAME. Talk about the setup of this file. */
    public static Graph loadFromText(String filename) {
        Charset cs = Charset.forName("US-ASCII");
        try (BufferedReader r = Files.newBufferedReader(Paths.get(filename), cs)) {
            Graph g = new Graph();
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields.length == 3) {
                    int from = Integer.parseInt(fields[0]);
                    int to = Integer.parseInt(fields[1]);
                    int weight = Integer.parseInt(fields[2]);
                    g.addEdge(from, to, weight);
                } else if (fields.length == 1) {
                    g.addVertex(Integer.parseInt(fields[0]));
                } else {
                    throw new IllegalArgumentException("Bad input file!");
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}

