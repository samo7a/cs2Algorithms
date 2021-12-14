
import java.util.*;

public class dijkstra {
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            long w = sc.nextLong();
            g.add(a, b, w);
        }
        g.dijkstra(0);
    }
    
    public static class Edge {
        int dest;
        long w;
        Edge(int dest, long w) {
            this.dest = dest;
            this.w = w;
        }
    }

    public static class Pair implements Comparable<Pair> {
        int location;
        long dist;
        Pair(int loc, long d) {
            location = loc;
            dist = d;
        }

        public int compareTo(Pair o) {
            // sort by smallest distance
            return Long.compare(dist, o.dist);
        }
    }

    public static class Graph {
        ArrayList<Edge>[] adj;
        int n;
        Graph(int n) {
            this.n = n;
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<Edge>();
            }
        }

        void add(int s, int e, long w) {
            adj[s].add(new Edge(e, w));
        }

        void dijkstra(int index) {
            long[] dist = new long[n];
            Arrays.fill(dist, 987654321);
            dist[index] = 0;
            boolean[] visited = new boolean[n];
            PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
            pq.add(new Pair(index, 0));

            while (!pq.isEmpty()) {
                Pair cur = pq.poll();
                if (visited[cur.location])
                    continue;
                visited[cur.location] = true;
                for (Edge e : adj[cur.location]) {
                    int newLocation = e.dest;
                    long newDist = e.w + dist[cur.location];
                    // long newDist = e.w + cur.dist;
                    if (dist[newLocation] > newDist) {
                        dist[newLocation] = newDist;
                        pq.add(new Pair(newLocation, newDist));
                    }
                }
            }

            System.out.println(Arrays.toString(dist));
        }
    }
}
