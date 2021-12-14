import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// main class
public class main {
	
	// main method
	public static void main (String[] args) {
		Scanner scnr = new Scanner(System.in); // scanner object
		int n = scnr.nextInt(); // read in the number of scrolls
		
		// array list of scrolls
		ArrayList<Scroll> scrolls = new ArrayList<>();
		
		// read in all the scrolls and add it to the scrolls array list
		for (int i = 0; i < n; i++) {
			int first = scnr.nextInt();
			int second = scnr.nextInt();
			scrolls.add(new Scroll(first, second));
		}
		scnr.close();
		System.out.println(scrolls);
		Collections.sort(scrolls); // sort the array list according to the guidelines provided in the compareTo method
		System.out.println(scrolls);
		
		// computing the time.
		// it is hard to explain in words. I have to draw it to better explain this.
		// but the idea is to add the time of the first translator of the first scroll
		// plus the summation of all the second translators times 
		// plus any lag time between the first and the second translators
		 int firstTimes = 0;
		 int totalTime = scrolls.get(0).first;
		 for (Scroll s : scrolls) {      
			 if(s.first <= s.second) {
				 firstTimes = firstTimes + s.first;
				 totalTime = totalTime + s.second;
			 }
			 else {
				 firstTimes = firstTimes + s.first;
				 totalTime = firstTimes + s.second;
			 }
		 }
		System.out.println(totalTime);  // print the answer
	}
	
	// Scroll class that stores the time for both translators to translate each scroll
	public static class Scroll implements Comparable<Scroll>{
		int first; // time for the first translator
		int second; // time for the second translator
		 
		
		// constructor
		Scroll(int first, int second){
			this.first = first;
			this.second = second;
		}
		
		@Override
		public int compareTo(Scroll s) {
			// to sort the scrolls the optimum way
			// we have two cases
			// if the the time for the second translator is greater than the time for the first translator
			// here we need to order according to the first translator's time (from smaller to bigger)
			if (this.first <= this.second) return Integer.compare(this.first, s.first);
			// the second case is when the time for the first translator is bigger than that in the second
			// here we need to order according to the second translator's time ( from bigger to smaller)
			else return Integer.compare(s.second, this.second);
		}
		
		
		// for testing purposes.
		public String toString() {
			return "\n(" + first + "," + second + ")\n";
		}
	}
}
