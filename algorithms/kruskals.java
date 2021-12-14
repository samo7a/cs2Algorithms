

import java.util.*;

public class kruskals {
    public static void main(String[] Args) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
    }

    // we only need an edge list to be efficient
    // {
    // (1, 2, 5),
    // (2, 1, 10),
    // (2, 3, 4)
    // }
    public static ArrayList<Edge> runKruskalsEdge(ArrayList<Edge> graph, int n) {
        
        ArrayList<Edge> retEdgeList = new ArrayList<Edge>();
        DisjointSet disjointSet = new DisjointSet(n);

        // sort edge list
        Collections.sort(graph);
//        Collections.sort(graph, (Edge a, Edge b)->Integer.compare(a.st, b.st)); // sort by st (but why?)

        // Loop until everthing is merged
        int numComponents = n; // n is the number of nodes
        int pos = 0;
        while (numComponents > 1) {

            // Get the current edge (smallest unused edge)
            Edge curEdge = graph.get(pos);
            pos++;

            // Check if this forms a cycle
            // Check if the root of the start and the root of the end are different
            if (disjointSet.find(curEdge.start) !=
                    disjointSet.find(curEdge.end)) {
                System.out.println("Merging " + curEdge.start + " and " +curEdge.end);
                int sRoot = disjointSet.find(curEdge.start);
                int eRoot = disjointSet.find(curEdge.end);
                System.out.println("Size of " + curEdge.start + " is " + disjointSet.size[sRoot]);
                System.out.println("Size of " + curEdge.end + " is " + disjointSet.size[eRoot]);

                // The edge does not form a cycle
                // use the edge
                retEdgeList.add(curEdge);

                // Link the components
                disjointSet.union(curEdge.start, curEdge.end);
                numComponents--;
            }
        }

        return retEdgeList;
    }

    public static class Edge implements Comparable<Edge> {
        int start, end, weight;

        Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static class DisjointSet {
        int[] parent; // store who is my parent (if root we are our parent)
        int[] rank; // longest path from leaf to root
        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            Arrays.fill(rank, 1);
        }

        int find(int cur) {
            if (cur == parent[cur]) return cur; // we are root!
            return find(parent[cur]); // Does not path compress!
        }

        boolean union(int a, int b) {
            int aRoot = find(a); // get the root of the first node
            int bRoot = find(b); // get the root of the second node

            if (aRoot == bRoot) return false; // no updates are necessary

            // parent[aRoot] = bRoot; // We should check ranks first.
            if (rank[aRoot] < rank[bRoot]) {
                // b's path is longer
                // a is a smaller tree
                // Don't make b's path be longer
                parent[aRoot] = bRoot;
                // Do not update the rank of B
                return true;
            }

            // Avoid making an even worse path in the larger tree a
            parent[bRoot] = aRoot;

            // Check ranks were equal (create new worst case path)
            if (rank[aRoot] == rank[bRoot]) {
                rank[aRoot]++; // rank of the root of the final tree increase
            }

            return true;
        }
    }
}
