

import java.util.*;

public class huffman {
    public static void main(String[] Args) {
//        HashMap<String, Integer> freq = new HashMap<String, Integer>();
        
        PriorityQueue<Token> pq = new PriorityQueue<Token>();
        pq.add(new Token("A", 384));
        pq.add(new Token("B", 67));
        pq.add(new Token("C", 109));
        pq.add(new Token("D", 212));
        pq.add(new Token("E", 581));
        pq.add(new Token("F", 125));
        pq.add(new Token("G", 94));
        String[] code = new String[pq.size()];
        Arrays.fill(code , "");

        while (pq.size() > 1) {
            Token least = pq.poll();
            Token secondLeast = pq.poll();
            Token merged = new Token(least.value + secondLeast.value,
                                     least.freq + secondLeast.freq);
            System.out.println("Merging " + least + " " + secondLeast);
            pq.add(merged);

            // Code extraction
            for (int i = 0; i < least.value.length(); i++)
                code[least.value.charAt(i)-'A'] = "0" + code[least.value.charAt(i)-'A'];
            for (int i = 0; i < secondLeast.value.length(); i++)
                code[secondLeast.value.charAt(i)-'A'] = "1" + code[secondLeast.value.charAt(i)-'A'];
        }
        System.out.println(Arrays.toString(code));
    }

    public static class Token implements Comparable<Token> {
        String value;
        int freq;

        Token(String s, int f) {
            value = s;
            freq = f;
        }

        public int compareTo(Token o) {
            return Integer.compare(freq, o.freq);
        }

        public String toString() {
            return "(" + value + " " + freq + ")";
        }
    }
}
