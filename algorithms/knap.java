
import java.util.*;

public class knap {
    public static long[][] memo1;
    public static long[][] memo2;

    public static long[] values;
    public static int[] weights;
    public static int n;

    public static long INVALID = -1;

    public static void main(String[] Args) {
        
        n = 6;
        int c = 12;
        values = new long[n];
        weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = 1;
            weights[i] = 1;
        }
        values[0] = 3;
        values[1] = 6;
        values[2] = 15;
        values[3] = 11;
        values[4] = 6;
        values[5] = 1;
        weights[0] = 2;
        weights[1] = 3;
        weights[2] = 10;
        weights[3] = 9;
        weights[4] = 5;
        weights[5] = 1;

        memo1 = new long[c + 1][n];
        memo2 = new long[c + 1][n];
        for (long[] x : memo1)
            Arrays.fill(x, INVALID);
        for (long[] x : memo2)
            Arrays.fill(x, INVALID);

        //long start = System.currentTimeMillis();
        //long value1 = dp1(c, 0);
        //long end = System.currentTimeMillis();
        //long start2 = System.currentTimeMillis();
        long value2 = dp2(c, 0);
        int curC = c;
        for (int i = 0; i < n; i++) {
            if ((i != n - 1 && memo2[curC][i] != memo2[curC][i+1]) || ( i == n - 1 && memo2[curC][i] != 0)) {
                System.out.println("Take item " + (i + 1));
                curC -= weights[i];
            }
        }
        //long end2 = System.currentTimeMillis();

        System.out.println(value2);
        //System.out.println(end - start);
        //System.out.println(end2 - start2);
    }

    public static long dp2(int cap, int item) {
        if (item >= n) // no items left to take
            return 0;
        if (memo2[cap][item] != INVALID)
            return memo2[cap][item];

        memo2[cap][item] = 
            dp2(cap, item + 1); // don't take the current item (don't take)
        if (cap - weights[item] >= 0) {
            long possibleValue = 
                dp2(cap - weights[item], item + 1) + 
                values[item]; // (take)

            if (memo2[cap][item] < possibleValue)
                memo2[cap][item] = possibleValue; // update
        }

        return memo2[cap][item];
    }

    // O(I) transition
    // cap starting item
    public static long dp1(int cap, int item) {
        if (item >= n) return 0;
        if (memo1[cap][item] != INVALID)
            return memo1[cap][item];

        memo1[cap][item] = 0; // don't take anything
        for (int i = item; i < n; i++) {
            if (cap - weights[i] < 0) continue; // don't break the bag
            long possibleValue = dp1(cap - weights[i], i + 1) + values[i];
            if (memo1[cap][item] < possibleValue)
                memo1[cap][item] = possibleValue; // update
        }

        return memo1[cap][item];
    }
}
