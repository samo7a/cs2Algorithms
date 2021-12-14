
import java.util.*;

public class kos {
    public static class Graph {
        int n;
        ArrayList<Integer>[] adj;
        ArrayList<Integer>[] jda;
        int[] scc_id;
        Graph(int n) {
            this.n = n;
            adj = new ArrayList[n];
            jda = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<Integer>();
                jda[i] = new ArrayList<Integer>();
            }
        }
        void add(int a, int b) {
            adj[a].add(b);
            jda[b].add(a);
        }
        int count = 0;

        int scc_count = 0;
        void kos() {
            scc_id = new int[n];

            // store the index of each node in the post order
            int[] post = new int[n];
            boolean[] used = new boolean[n];

            // Get the post order
            for (int i = 0; i < n; i++)
                if (!used[i])
                    dfs1(i, post, used);
            
            // DFS on the Reverse of the post on the reverse graph (yikes!)
            Arrays.fill(used, false);
            for (int i = n - 1; i >= 0; i--) {
                int index = post[i];
                if (!used[index]) {
                    // Creating a new SCC
                    dfs2(index, used);
                    scc_count++;
                    System.out.println();
                }    
            }
        }

        void dfs2(int index, boolean[] used) {
            used[index] = true;
            scc_id[index] = scc_count;
            System.out.println(index);
            for (Integer x : jda[index]) {
                if (!used[x]) {
                    dfs2(x, used);
                }
            }
        }

        void dfs1(int index, int[] post, boolean[] used) {
            used[index] = true;
            for (Integer x : adj[index]) {
                if (!used[x]) {
                    dfs1(x, post, used);
                }
            }
            post[count++] = index;
        }
    }
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            g.add(a, b);
        }
        g.kos();
        System.out.println(Arrays.toString(g.scc_id));
    }
}
