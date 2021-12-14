
import java.util.*;

public class tarj {
    public static class Graph {
        int n;
        ArrayList<Integer>[] adj;
        int[] scc_id;

        // Constructor
        Graph(int n) {
            this.n = n;
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<Integer>();
        }

        // Method to add an edge
        void add(int a, int b) {
            adj[a].add(b);
        }

        // store the current pre order value to assign
        int cur_pre_order = 0;

        // Store the number of SCCs
        int scc_count = 0;

        // Store the low link (smallest pre-order value reachable on the stack) for the nodes
        int[] low_link;

        // Pre order information for the nodes
        int[] pre_order;

        // Stores whether a value is on the stack or not
        boolean[] on_stack;

        // The actual stack
        int[] stack;

        // store whether a node has been visited or not
        boolean[] vis;

        // The lowlink method for finding the SCCs
        void lowlink() {
            scc_id = new int[n];
            low_link = new int[n];
            pre_order = new int[n];
            on_stack = new boolean[n];
            vis = new boolean[n];
            stack = new int[n + 1]; // stack[0] = size
                                    // stack[1] = first value on stack (bottom)
            for (int i = 0; i < n; i++) {
                if (!vis[i]) {
                    dfs(i);
                }
            }
        }

        // The DFS for the low link method
        void dfs(int i) {
            vis[i] = true;
            stack[++stack[0]] = i;
            pre_order[i] = cur_pre_order++;
            low_link[i] = pre_order[i];
            on_stack[i] = true;


            // The actual DFS
            for (Integer x : adj[i]) {
                if (!vis[x]) {
                    dfs(x);
                }
                if (on_stack[x]) {
                    if (low_link[x] < low_link[i])
                        low_link[i] = low_link[x];
//                    low_link[i] = Math.min(low_link[i], low_link[x]);
                }
            }


            if (low_link[i] == pre_order[i]) {
                // This is a new SCC
                createSCC(i);
            }
        }

        // The method to make a new SCC stopping at some given index
        void createSCC(int index) {
            
            // Mark the non index values that are on the scc_stack and remove them
            while (stack[stack[0]] != index) {
                int cur = stack[stack[0]--];
                scc_id[cur] = scc_count;
                System.out.println(cur);
                on_stack[cur] = false; // on program stack
            }

            // Mark the index value and remove it too
            scc_id[index] = scc_count;
            stack[0]--;
            on_stack[index] = false;
            System.out.println(index);
            System.out.println();

            // Update the number of SCCs at the end
            scc_count++;
        }
    }
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        
        // Read in the number of nodes and edges
        int n = sc.nextInt();
        int m = sc.nextInt();
        Graph g = new Graph(n);

        // Read in all the edges
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            g.add(a, b);
        }

        // Use Tarjans to find the SCCs
        g.lowlink();
        System.out.println(Arrays.toString(g.scc_id));
    }
}
