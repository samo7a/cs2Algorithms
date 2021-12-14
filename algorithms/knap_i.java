
import java.util.*;

public class knap_i {
    public static void main(String[] Args) {
        int n = 5;
        int c = 100000;
        int[] weights = new int[n];
        long[] values = new long[n];
        for (int i = 0; i < n; i++) {
            values[i] = 1;
            weights[i] = 1;
        }
        long[] answer = new long[c + 1];
        // [ best for 0 size knap, best for 1 size knap, best 2 size knap,
        for (int i = 0; i < n; i++) { // current item
        //System.out.println(Arrays.toString(answer));
            for (int j = c; j >= weights[i]; j--) { // current capacity
                long possibleValue = values[i] + answer[j - weights[i]];
                if (possibleValue > answer[j])
                    answer[j] = possibleValue;
            }
        }
        System.out.println(answer[c]);
        //System.out.println(Arrays.toString(answer));
    }
}
