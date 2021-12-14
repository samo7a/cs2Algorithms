
import java.util.*;

public class BIT {
    public static void main(String[] Args) {
        int max = 100; // use values in the range of [0,100]
        Random r = new Random();
        Fenwick bit = new Fenwick(100); 
        for (int i = 0; i < 10; i++) {
            int position = r.nextInt(max+1);
            System.out.println("The number of values less than or equal " + position + " is " + bit.sum(position));
            System.out.println("Incrementing " + position + " by 1");
            bit.inc(position, 1);
        }
    }

    public static class Fenwick {
        int max;
        int[] arr;
        Fenwick(int n) {
            max = n + 1 + 1; // 1 for inclusive bound 1 for 1-index
            arr = new int[max];
        }
        int sum(int index) {
            index++; // 1-indexed;
            int ret = 0;
            while (index != 0) {
                ret += arr[index]; // add in the current bucket
                index -= (index&-index); // reduce by the lowest 1 bit
            }
            return ret;
        }
        void inc(int index, int value) {
            index++; // 1-indexed
            while (index < max) {
                arr[index] += value;
                index += (index&-index);
            }
        }
    }
}
