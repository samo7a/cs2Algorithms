
import java.util.*;

public class mcm {
    public static int[][] memo;
    public static int[] rows, cols;
    public static int INVALID = -1;
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        rows = new int[n];
        cols = new int[n];
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            rows[i] = sc.nextInt();
            cols[i] = sc.nextInt();
        }
        for (int[] x : memo)
            Arrays.fill(x, INVALID);
        System.out.println(compute(0, n - 1));
    }

    // Finds the min number of operations for multiplying matrices s through e
    public static int compute(int s, int e) {
        if (s == e) return 0; // only 1 matrix no ops requried
        if (memo[s][e] != INVALID) return memo[s][e]; // already computed
        // memo[s][e] = Integer.MAX_VALUE; // assume some large value and fix later
        memo[s][e] = Integer.MAX_VALUE; // assume some large value and fix later
        for (int i = s; i<e; i++) { // i is the last value in the first partition
                                    // (s) (s+1, s+2, ..., e) 
                                    // ...
                                    // (s, s+1, s+2, ..., e-1) (e)
                                    
            int temp = compute(s, i) + compute(i + 1, e) + rows[s] * cols[i] * cols[e];
            if (temp < memo[s][e])
                memo[s][e] = temp;
        }
        return memo[s][e];
    }
}
