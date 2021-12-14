

import java.util.*;

public class ffa {
    public static class Edge {
        int i, j, cap, flow;
        Edge rev;
        Edge(int i, int j, int cap) {
            this.i=i;
            this.j=j;
            this.cap=cap;
            flow = 0;
        }
    }

    public static int oo = 987654321;

    public static class Graph {
        int n, s, t;
        ArrayList<Edge>[] adj;
        boolean[] vis;
        Graph(int n) {
            this.n = n;
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<Edge>();
            s = 0;
            t = n - 1;
        }

        int getFlow() {
            vis = new boolean[n];
            int curFlow = dfs(s, oo);
            int ans = 0;
            while (curFlow != 0) {
                ans += curFlow;
                System.out.println("We sent " + curFlow + " flow.");
                Arrays.fill(vis, false); // reset the dfs
                curFlow = dfs(s, oo);
            }
            return ans;
        }

        int dfs(int node, int limit) {
            if (vis[node]) return 0;
            vis[node] = true;
            if (node == t) {
                return limit;
            }

            for (Edge e : adj[node]) {
                int tlimit = limit;
                if (tlimit > e.cap - e.flow)
                    tlimit = e.cap - e.flow;
                if (tlimit == 0) continue;
                int flow = dfs(e.j, tlimit);

                if (flow != 0) {
                    // update the edge e and return the flow
                    e.flow += flow;
                    e.rev.flow -= flow;
                    System.out.println((e.i + 1) + " " + (e.j + 1));

                    return flow;
                }
            }

            return 0;
        }

        void add(int a, int b, int cap) {
            Edge fwd = new Edge(a, b, cap);
            Edge rev = new Edge(b, a, 0);
            fwd.rev = rev;
            rev.rev = fwd;
            adj[a].add(fwd);
            adj[b].add(rev);
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
            int cap = sc.nextInt();
            g.add(a, b, cap);
        }

        System.out.println(g.getFlow());
    }
}
