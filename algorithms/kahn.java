
import java.util.*;

public class kahn {
    // The graph class
    public static class Graph {
        int n;
        ArrayList<Integer>[] adj;

        // Constructor method
        Graph(int n) {
            this.n = n; // added this to fix the program.

            // Create the adjacency list
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<Integer>();
            }
        }

        // Add edge method
        void add(int a, int b) {
            adj[a].add(b); // directed graph
        }

        // Kahn's algorithm for finding the topological ordering
        void topo() {

            // Create the in degree array
            int[] inDegree = new int[n]; // auto filled with 0's
            for (int i = 0; i < n; i++) {
                for (Integer end : adj[i]) {
                    inDegree[end]++;
                }
            }

            // Fill the queue with all values that have no prereqs
            Queue<Integer> q = new LinkedList<Integer>();
            boolean[] vis = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (inDegree[i] == 0) {
                    q.add(i);
                    vis[i] = true;
                }
            }

            // Loop until no class is left to process
            while (!q.isEmpty()) {
                int cur = q.poll();
                System.out.println(cur + 1);

                // Update the nodes that are directly reached by the current
                for (Integer end : adj[cur]) {
                    inDegree[end]--;
                    if (inDegree[end] == 0) {
                        q.add(end);
                        vis[end] = true;
                    }
                }
            }

            // If there was some node not visited, a cycle must exist.
            for (int i = 0; i < n; i++) {
                if (!vis[i]) {
                    System.out.println("Cycle detected");
                    return;
                }   
            }
        }
    }

    // The main method of our program
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);

        // Read in the number of nodes and edges
        int n = sc.nextInt();
        int m = sc.nextInt();

        // Create the graph structure
        Graph g = new Graph(n);

        // Read in the edges
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            g.add(a, b);
        }

        // Run the topo sort.
        g.topo();
    }
}
