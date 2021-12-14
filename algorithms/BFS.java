
// Code to demonstrate a BFS

import java.util.*;

public class search {

    // A graph using an adjacency list
    public static class Graph {
        TreeSet<Integer>[] adj;
        int n;
        boolean[] visited;

        // Constructor for the graph
        Graph(int n) {
            this.n = n;
            adj = new TreeSet[n];
            for (int i = 0; i < n; i++)
                adj[i] = new TreeSet<Integer>();
        }

        // Code to add an edge
        void add(int i, int j) {
            adj[i].add(j);
            // let's assume directed!
            // adj[j].add(i);
        }

        // Code to bfs on the graph
        void bfs(int index) {

            // Our q that is initialized with the start
            Queue<Integer> q = new LinkedList<Integer>();
            q.add(index);

            // Our visited array that is initialized with the start as visited
            visited = new boolean[n];
            visited[index] = true;

            // Loop until the queue is empty
            while (!q.isEmpty()) {

                // Process the next node
                int cur = q.poll();
                System.out.println("Beginning " + (cur + 1) + ".");
                // visited[cur] = true; // WRONG

                // Loop through all the children
                for (Integer next : adj[cur]) {
                    if (!visited[next]) {
                        q.add(next);
                        visited[next] = true; // CORRECT
                    }
                }

                // End the processing of the node
                System.out.println((cur + 1) + " is done!");
            }
        }
    }

    // The main method
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);

        // Read in the number of nodes and edges
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        // Create the graph
        Graph g = new Graph(n);

        // Read in the edges
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            g.add(a, b);
        }

        // BFS from node 0
        g.bfs(0);
    }
}
