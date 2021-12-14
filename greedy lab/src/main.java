import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// main class
public class main {
	
	// main method
	public static void main (String[] args) {
		Scanner scnr = new Scanner(System.in); // scanner object
		int n = scnr.nextInt(); // read in the number of students
		
		// array list of scrolls
		ArrayList<Interval> intervals = new ArrayList<>();
		
		// read in all the scrolls and add it to the scrolls array list
		for (int i = 0; i < n; i++) {
			int start = scnr.nextInt();
			int duration = scnr.nextInt();
			intervals.add(new Interval(start, duration)); 
		}
		scnr.close();
		//System.out.println(intervals);
		Collections.sort(intervals); // sort the array list according to the guidelines provided in the compareTo method
		//System.out.println(intervals);
		
		int sum = 0; // number of students entered the office hours so far
		int currentTime = 0; // tracks the current time
		for(Interval i : intervals) { // for all the intervals in intervals array list
			if(i.end >= currentTime + 5) { // if the end of the interval is equal to the current time + 5
				sum ++; // increment the sum
				currentTime = currentTime + 5; // increment the current time by 5
			}
		}
		System.out.println(sum); // print the answer
		
	}
	
	
	public static class Interval implements Comparable<Interval> {
		int start;
		int end;
		int duration;
		 
		
		// constructor
		Interval(int start, int duration){
			this.start = start;
			this.end = start + duration; // end = start + duration
			this.duration = duration;
		}
		
		@Override
		public int compareTo(Interval i) { //if two students start together, prioritize the one who ends first
										  // other wise, prioritize the student who came first
			if(this.start == i.start)
				return Integer.compare(this.end, i.end);
			else return Integer.compare(this.start, i.start);
		}
		
		
		// for testing purposes.
		public String toString() {
			return "\n(" + start + "," + end + ")\n";
		}
	}
}
