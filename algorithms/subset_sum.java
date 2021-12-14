
import java.util.*;

public class subset_sum {
    public static int target, INVALID = -1, POSSIBLE = 1, IMPOSSIBLE = 0, n;
    public static int[] values;
    public static int[][] memo; // [last index][sum]
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        target = sc.nextInt();
        values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = sc.nextInt();
        }
        memo = new int[n + 1][target + 1];
        for (int i = 0; i <= n; i++)
            Arrays.fill(memo[i], INVALID);

        System.out.println((rec(0, 0) == POSSIBLE) ? "Possible" : "Impossible");
    }

    public static int rec(int curItem, int sum) {
        if (sum == target) return POSSIBLE; // return 1;
        if (sum > target) return IMPOSSIBLE;
        if (curItem == n) return IMPOSSIBLE; // return 0;
        if (memo[curItem][sum] != INVALID) return memo[curItem][sum];
        
        memo[curItem][sum] = IMPOSSIBLE;
        if (rec(curItem + 1, sum) == POSSIBLE) {
            memo[curItem][sum] = POSSIBLE;
            return POSSIBLE;
        }

        if (rec(curItem + 1, sum + values[curItem]) == POSSIBLE) {
            memo[curItem][sum] = POSSIBLE;
            return POSSIBLE;
        }

        return memo[curItem][sum];
    }

}
