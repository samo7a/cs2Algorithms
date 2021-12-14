
import java.util.*;

public class fib {
    public static long[] memo;
    public static long INVALID = -1;
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long start1 = System.currentTimeMillis();
        long val1 = F(n);
        long end1 = System.currentTimeMillis();
        System.out.println(val1);
        System.out.println("Time taken was  " + (end1 - start1));
        long start2 = System.currentTimeMillis();
        memo = new long[n + 1];
        Arrays.fill(memo, INVALID);
        long val2 = FDP(n);
        long end2 = System.currentTimeMillis();
        System.out.println(val2);
        System.out.println("Time taken was  " + (end2 - start2));
    }
    public static long FDP(int n) {
        if (n <= 3) return n;
        if (memo[n] != INVALID) return memo[n];
        memo[n] = FDP(n - 1) + FDP(n - 2);
        return memo[n];
    }
    public static long F(int n) {
        if (n <= 3) return n;
        return F(n - 1) + F(n - 2);
    }
}
