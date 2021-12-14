
import java.util.*;

public class floyds {
    public static int oo = 987654321;
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] mat = new int[n][n]; // adjacency matrix
        int[][] itermediate = new int[n][n]; // adjacency matrix

        for (int[] x : mat)
            Arrays.fill(x, oo);
        for (int[] x : itermediate)
            Arrays.fill(x, -1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = sc.nextInt();
            }
        }
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    if (mat[j][k] > mat[j][i] + mat[i][k] && 
                            mat[j][i] != oo && 
                            mat[i][k] != oo) {
                        mat[j][k] = mat[j][i] + mat[i][k];
                        itermediate[j][k] = i;
                    }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(i + " --> " + j);
                System.out.println(mat[i][j]);
                printPath(i, j, itermediate);
                System.out.println(j);
                System.out.println();
            }
        }
    }
    public static void printPath(int start, int end, int[][] itermediate) {
        if (start == end) {
            //System.out.println(start);
            return;
        }
        if (itermediate[start][end] == -1) {
            System.out.print(start + " --> ");
            return;
        }


        printPath(start, itermediate[start][end], itermediate); // first part

        printPath(itermediate[start][end], end, itermediate); // second part
    }
}
