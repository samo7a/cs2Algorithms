
import java.util.*;
import java.math.*;

public class BI {
    public static void main(String[] Args) {
        // BigInteger x = BigInteger.valueOf(1);
        // BigInteger x = new BigInteger("1");
        BigInteger x = BigInteger.ONE;
        BigInteger ZERO = BigInteger.ZERO;
        // multiply the value x by 2 100
        for (int i = 0; i < 100; i++)
            x = x.multiply(ZERO);
        System.out.println(x);
    }
}
