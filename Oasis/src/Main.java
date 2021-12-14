import java.lang.Math;                          //
import java.io.IOException;                   //
import java.util.Scanner;                   // <----  essential
import java.util.TreeSet;                   // <----   imports

// main class
public class Main {
	// two global variables for the coordinations of the oasis
	static int x0;  
	static int y0;
	// main method with lower case args 
	public static void main(String[] args) throws IOException{
		// declaring objects for the data structure and the I/O
		TreeSet<Friend> friendList = new TreeSet<Friend>();
		Scanner scnr = new Scanner(System.in);
		
		
		// scanning the the first three values in the file .... no. of friends/lines and the coordinates of the oasis
		int n = scnr.nextInt();
		x0 = scnr.nextInt();
		y0 = scnr.nextInt();
		
		// I liked this syntax of the while loop :D
		// looping through each line to get the coordinates of each friend and their names and add it to the treeSet
		while(n-->0){
			int x1 = scnr.nextInt();
			int y1 = scnr.nextInt();
			String token = scnr.next();
			friendList.add(new Friend(x1,y1,token));
		}
		
	
		// printing the solution to the console and to the file
		for (Friend s : friendList) {
			System.out.println(s);
		}
		// closing the output file

	}
	
	// public class that implements Comparable interface sort the Friend objects according to their distance from the oasis
	public static class Friend implements Comparable<Friend>{
		// class fields, name of each friend and their coordinates
		int x;
		int y;
		String name;
		// simple constructor
		Friend(int x, int y, String name){
			this.x = x;
			this.y = y;
			this.name = name;
		}
		// as required I will not use sqrt() method
		// so I will sort the objects according to their distance squared. It is actually the same
		double distanceSquared() {
			return Math.pow(x - x0,2) + Math.pow(y - y0, 2);
		}

		@Override  // overrided compareTo() method
		public int compareTo(Friend f) {
			return Double.compare(distanceSquared(), f.distanceSquared()); 
		}

		public String toString() {  
			return name;      // simply returning the string value
		}


	}

}
