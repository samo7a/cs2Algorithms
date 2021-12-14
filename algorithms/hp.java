

import java.util.*;

public class hp {
    public static int n;
    public static int[] value;
    public static int[][] sum;
    public static long[][] memo;
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        value = new int[n];
        sum = new int[n][n];
        memo = new long[n][n];
        for (int i = 0; i < n; i++) {
            value[i] = sc.nextInt();
            for (int j = 0; j <= i; j++) { // build all sums that use i as the end point
                sum[j][i] = 0;
                memo[j][i] = -1;
                for (int k = j; k <= i; k++) {
                    sum[j][i] += value[k];
                }
                sum[j][i] %= 100;
            }
        }
        System.out.println(dp(0, n - 1));
    }
    public static long dp(int s, int e) {
        if (s == e) return 0; // no potions to combine
        if (memo[s][e] != -1) return memo[s][e]; // previously computed answer

        // s ... e
        // (s) (s + 1, ..., e)
        // (s, s + 1, ..., e - 1) (e)
        long ans = Long.MAX_VALUE;
        for (int i = s; i < e; i++) {
            long tans = dp(s, i) + dp(i + 1, e) + sum[s][i] * sum[i+1][e];
            if (tans < ans) ans = tans;
        }

        memo[s][e] = ans;
        return ans;
    }
}
