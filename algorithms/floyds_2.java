
import java.util.*;

public class floyds_2 {
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        boolean[][] adj_mat = new boolean[n][n];
        for (int i = 0; i < n; i++)
            adj_mat[i][i] = true;

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            adj_mat[a][b] = true;
        }

        for (int i = 0; i < n; i++)
            for (int s = 0; s < n; s++)
                for (int e = 0; e < n; e++) {
                    if (adj_mat[s][i] && adj_mat[i][e])
                        adj_mat[s][e] = true;
                }
        for (int s = 0; s < n; s++) {
            for (int e = 0; e < n; e++) {
                if (adj_mat[s][e] == true && adj_mat[e][s] == true)
                    System.out.println((s + 1) + " " + (e + 1) + " is in the same SCC" );
            }
        }
    }
}
