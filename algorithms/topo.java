
// Java program to demonstrate a Topological sort

import java.util.*;

public class topo {

    // Class for our graph (uses an adjacency list)
    public static class Graph {
        ArrayList<Integer>[] adj;
        int location;
        int[] order;
        boolean[] on_stack, visited;

        // Constructor for the graph
        Graph(int n) {
            adj = new ArrayList[n];
            location = n - 1;
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<Integer>();
            on_stack = new boolean[n];
            visited = new boolean[n];
            order = new int[n];
        }

        void add(int i, int j) {
            adj[i].add(j);
            // adj[j].add(i); // DON'T DO THIS IN DIRECTED GRAPHS!!!
        }
        
        // The code for the toposort
        void topoSort() {

            // DFS from every node 
            // if some node has been visited, it will be handled by dfs
            for (int i = 0; i < adj.length; i++)
                dfs(i);

            // Print the result
            for (int i = 0; i < adj.length; i++) {
                System.out.println(order[i] + 1);
            }
        }

        // The DFS method to toposort the graph
        void dfs(int index) {

            // Check if a cycle exists
            if (on_stack[index]) {
                System.out.println("BADNESS");
                return;
            }

            // Check if the node has been visited
            if (visited[index]) {
                return;
            }

            // Put the node on the stack and visit it
            visited[index] = on_stack[index] = true;

            // try to DFS on all the children
            for (Integer next : adj[index]) {
                dfs(next);
            }

            // now that we are leaving the node is not on the stack
            on_stack[index] = false;

            // add the element to the proper location (back)
            order[location] = index;
            location--; // move the location back 1
        }
    }

    // Main method
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);

        // Read in the number of nodes and edges
        int n = sc.nextInt();
        int m = sc.nextInt();

        // Create the graph data structure
        Graph g = new Graph(n);

        // Read in the edges
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1; // account for 1 based index by subtracting 1
            int b = sc.nextInt() - 1;
            g.add(a, b);
        }

        // Topo sort the graph now that the graph has been built
        g.topoSort();
    }
}
