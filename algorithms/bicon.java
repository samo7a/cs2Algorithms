
import java.util.*;

// Note Art points will be printed multiple times...

public class bicon {

    // Class for the edges
    public static class Edge {
        int dest, id;
        boolean used;
        Edge rev;
        Edge(int x, int i) {
            dest = x;
            id = i;
            used = false;
            rev = null;
        }
    }

    // Class for the graph
    public static class Graph {
        // This will store the name for each node in the correct order
        ArrayList<String> node_names;

        // This will store the adjacency list
        ArrayList<ArrayList<Edge>> adj;

        // This will be a loop up table for the ids (given a node name)
        HashMap<String, Integer> name_to_id;

        // How many edges are in the graph (useful for making edge ids)
        int num_edges = 0;

        // Method to create the graph
        Graph() {
            name_to_id = new HashMap<String, Integer>();
            adj = new ArrayList<ArrayList<Edge>>();
            node_names = new ArrayList<String>();
        }

        // Method to add an edge between two nodes by their name
        void add(String a, String b) {

            // Get the indices for the nodes
            int aa = get(a);
            int bb = get(b);

            // Create the edges
            Edge fwd = new Edge(bb, num_edges);
            Edge rev = new Edge(aa, num_edges);

            // Set the up the reverse pointers
            fwd.rev = rev;
            rev.rev = fwd;

            // Add the edges to their appropriate lists
            adj.get(aa).add(fwd);
            adj.get(bb).add(rev);

            // Increment the number of edges
            num_edges++;
        }
        
        // Method to find the index of a node given its name
        int get(String s) {

            // Check if the node already exists
            if (name_to_id.containsKey(s))
                return name_to_id.get(s);

            // Create the node
            name_to_id.put(s, node_names.size());
            node_names.add(s);
            adj.add(new ArrayList<Edge>());
            return name_to_id.get(s);
        }

        int[] pre_order;
        int[] low_link;
        int[] stack;
        boolean[] on_stack;
        boolean[] vis;
        int cur_pre = 0;


        void run2Edge() {
            int n = node_names.size();
            cur_pre = 0;
            pre_order = new int[n];
            low_link = new int[n];
            stack = new int[n + 1];
            on_stack = new boolean[n];
            vis = new boolean[n];

            for (int i = 0; i < n; i++) {
                if (!vis[i])
                    dfsE(i, -1);
            }
            System.out.println(Arrays.toString(low_link));
            System.out.println(Arrays.toString(pre_order));
        }

        void dfsE(int c, int e_id) {
            pre_order[c] = cur_pre++;
            low_link[c] = pre_order[c];
            stack[++stack[0]] = c;
            on_stack[c] = true;
            vis[c] = true;

            for (Edge e : adj.get(c)) {
                if (!vis[e.dest] && e.id != e_id) {
                    dfsE(e.dest, e.id);
                }
                if (on_stack[e.dest] && e.id != e_id) {
                    if (low_link[c] > low_link[e.dest])
                        low_link[c] = low_link[e.dest];
                }
            }


            if (pre_order[c] == low_link[c] && e_id != -1) {
                System.out.println("Edge " + e_id + " is a bridge");
                create2ECC(c);
            }
            if (e_id == -1) {
                create2ECC(c);
            }
        }

        void create2ECC(int index) {
            while (stack[stack[0]] != index) {
                int cur = stack[stack[0]--];
                on_stack[cur] = false;
                System.out.println(node_names.get(cur));
            }
            int cur = stack[stack[0]--];
            on_stack[cur] = false;
            System.out.println(node_names.get(cur));
            System.out.println();
        }

        boolean[] e_vis;
        void run2Vertex() {
            int n = node_names.size();
            pre_order = new int[n];
            low_link = new int[n];
            stack = new int[num_edges + 1];
            vis = new boolean[n];
            on_stack = new boolean[num_edges];
            cur_pre = 0;

            for (int i = 0; i < n; i++) {
                if (!vis[i])
                    dfsV(i, -1);
            }
            System.out.println(node_names);
            System.out.println(Arrays.toString(low_link));
            System.out.println(Arrays.toString(pre_order));
        }

        void dfsV(int cur, int par) {
            pre_order[cur] = cur_pre++;
            low_link[cur] = pre_order[cur];
            vis[cur] = true;
            boolean found = false;
            
            // Loop throug all edges
            for (Edge e : adj.get(cur)) {

                // Check if the edge has not been used
                if (!on_stack[e.id]) {

                    // Put the edge on the stack.
                    stack[++stack[0]] = e.id;
                    on_stack[e.id] = true;

                    // Check if this is going to be a child
                    if (!vis[e.dest]) {
                        dfsV(e.dest, cur);

                        // Grab the link of your child
                        if (low_link[e.dest] < low_link[cur])
                            low_link[cur] = low_link[e.dest];
                    }

                    // Use the pre-order of your ancestor if possible
                    if (pre_order[e.dest] < low_link[cur])
                        low_link[cur] = pre_order[e.dest];

                    // Check if the child formed a new vcc
                    if (low_link[e.dest] >= pre_order[cur]) {
                        if (found || par != -1)
                            System.out.println("Found art point " + node_names.get(cur));
                        found = true;

                        // Pop to the edge
                        createVCC(e.id);
                    }
                }
            }
        }

        void createVCC(int id) {
            while (id != stack[stack[0]]) {
                int cur = stack[stack[0]--];
                System.out.println(cur);
                // I don't trust removing the edge from the stack :(
            }
            int cur = stack[stack[0]--];
            System.out.println(cur);
            System.out.println();
        }
    }

    // The main method
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        Graph g = new Graph();
        
        // Read in the number of edges
        int e = sc.nextInt();

        // Read in the edges
        for (int i = 0; i < e; i++) {
            String first = sc.next();
            String second = sc.next();
            g.add(first, second);
        }

        // Execute the component finding methods
        g.run2Edge();
        g.run2Vertex();
    }
}
