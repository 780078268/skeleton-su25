import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class UnionFind {
    ArrayList<Integer> tree = new ArrayList<Integer>();
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        for(int i = 0; i < N; i++) {
            tree.add(-1);
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if(v < 0 || v >= tree.size()) {
            throw new IllegalArgumentException("invalid index");
        }
        return tree.get(this.find(v)) *-1;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if(v < 0 || v >= tree.size()) {
            throw new IllegalArgumentException("invalid index");
        }
        return tree.get(v);
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if(v1 < 0 || v2 < 0 || v1 >= tree.size() || v2 >= tree.size()) {
            throw new IllegalArgumentException("invalid index");
        }
        return this.find(v1) == this.find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if(v < 0 || v >= tree.size()) {
            throw new IllegalArgumentException();
        }
        if(tree.get(v) < 0) {
            return v;
        }else{
            tree.set(v, this.find(tree.get(v)));
            return this.find(this.parent(v));
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. */
    public void union(int v1, int v2) {
        if(v1 <0 || v2 <0){
            throw new IllegalArgumentException();
        }
        if(connected(v1, v2)) {
            return;
        }else{
            if(this.sizeOf(v1) <= this.sizeOf(v2)) {
                int temp1 = tree.get(this.find(v1));
                int temp2 = tree.get(this.find(v2));
                tree.set(this.find(v1), this.find(v2));
                tree.set(this.find(v2), temp1+temp2);
            }else{
                int temp1 = tree.get(this.find(v1));
                int temp2 = tree.get(this.find(v2));
                tree.set(this.find(v2), this.find(v1));
                tree.set(this.find(v1), temp1+temp2);
            }
        }
    }
}
