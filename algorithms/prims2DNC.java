
import java.util.*;


public class prims {

    // The main method
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);

        // Read in the number of nodes and edges
        int nodes = sc.nextInt();
        int edges = sc.nextInt();


        // Create the placeholder memory
        ArrayList<Edge>[] adj_list = new ArrayList[nodes];
        for (int i = 0; i < nodes; i++)
            adj_list[i] = new ArrayList<Edge>();

        // rod has x, y, and index
        // rods can share x's
        // rods can share y's
        // rods cannot share an index

        // Read in all the edges
        for (int i = 0; i < edges; i++) {

            // Adjust for the 0 indexing
            int st = sc.nextInt() - 1; // from some list of red
            int en = sc.nextInt() - 1;
            int weight = sc.nextInt();

            // Add the undirected edge
            adj_list[st].add(new Edge(en, weight));
            adj_list[en].add(new Edge(st, weight));
        }

    
        // Create the set of connected nodes containing the node 0
        TreeSet<Integer> connected = new TreeSet<Integer>();
        connected.add(0);

        // Add all the initial edges to the edge heap
        PriorityQueue<Edge> edge_heap = new PriorityQueue<Edge>();
        for (Edge e : adj_list[0]) {
            edge_heap.add(e);
        }

        // Initialize the answer to 0
        int ans = 0;

        // create a list of perminant red edge

        // Loop until everything is connnected
        while (connected.size() != nodes) {

            // Get the potential edge
            Edge po_edge = edge_heap.poll();

            // Skip if edge is redundant
            if (connected.contains(po_edge.dest)) continue;

            // Update the answer
            ans += po_edge.weight; 
            // add the edge to the perminant red edges
            // THIS po_edge IS PART OF THE RED MST

            // Add the edge to our set and update the edge heap
            connected.add(po_edge.dest);
            for (Edge e : adj_list[po_edge.dest]) {
                if (!connected.contains(e.dest))
                    edge_heap.add(e);
            }
        }

        System.out.println(ans);
    }

    // TreeSet<Rod> connected implementation
    public static class Rod implements Comparable<Rod> {
        int x, y;
        public int compareTo(Rod o) {
            if (x == o.x)
                return Integer.compare(y, o.y);
            return Integer.compare(x, o.x);
        }
    }

    // The edge class
    public static class Edge implements Comparable<Edge> {
        int dest, weight;

        Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }

        // The edge comparison method
        public int compareTo(Edge o) {
            return Integer.compare(weight, o.weight);
        }
    }
}
