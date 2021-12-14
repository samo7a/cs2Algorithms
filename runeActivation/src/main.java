import java.util.Scanner; // import scanner to take input from user
// main class
public class main {
	// main method
	public static void main(String[] args) {
		// scanning the r and c variables
		Scanner scnr = new Scanner(System.in);
		int r = scnr.nextInt();  // no. of rows
		int c = scnr.nextInt();  // no. of columns
		// three 2d arrays, one for the current status of the grid
		// one for the goal grid 
		// and one for the solution 
		char current [][] = new char[r][c];
		char goal [][] = new char[r][c];
		char solution [][] = new char [r][c];
		
		// reading the contents of the current array
		for (int i = 0; i < r; i++) {
			String token = scnr.next();
			for (int j = 0; j < c; j++) {
				current[i][j] = token.charAt(j);
			}
		}
		// reading the contents of the goal array
		for (int i = 0; i < r; i++) {
			String token = scnr.next();
			for (int j = 0; j < c; j++) {
				goal[i][j] = token.charAt(j);
			}
		}
		// filling up the solution array with '0' in each slot
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				solution [i][j] = '0';
			}
		}
// ************ just for testing purposes ********************
//				System.out.println("The current grid is : ");
//				printGrid(current, r, c);
//				System.out.println();
//				System.out.println("The goal grid is : ");
//				printGrid(goal, r, c);
//				System.out.println();
// *************************************************************
				
		// if there is a solution, print the solution array
		// otherwise print "-1"
		if(solve(current, goal, solution, r, c, 0, 0)) {
			// some times the solve function find the solution before it goes
			// through all tiles 
			// so this simple for loop is for replacing the zeroes with dashes
			// to match the assignment requirement.
			for (int i = 0; i < r; i++) 
				for (int j = 0; j < c; j++) 
					if(solution[i][j] == '0') solution[i][j] = '-';
				
			printGrid(solution, r, c); 
		}
		else System.out.println("-1");

	}

	// prints the array
	public static void printGrid(char[][] array, int r, int c) {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				System.out.printf("%c", array[i][j]);
			}
			System.out.println();
		}	
	}
	public static boolean solve(char[][] current, char[][] goal, char solution [][],  int r, int c, int currR, int currC ) {
		// the base cases
		
		// if the current and goal arrays are identical return true
		if(isIdentical(current, goal, r, c)) return true;
		// if we reached the end of the grid, check if it is identical and return true if it is so.
		if(currR >= r) if(isIdentical(current, goal, r, c)) return true;  else return false;
		// if we reached the end of the column, go the next row and the first column
		if(currC >= c) return solve(current, goal, solution, r, c, currR + 1, 0);
		// if the current tile is not zero, thats mean we already covered it, so go the next tile
		if(solution[currR][currC] != '0') return solve(current, goal, solution, r, c, currR, currC + 1);
		
		// a for loop to try out our options (C,-)
		for (int i = 0; i < 2; i++) {
			// to transform the options from 0,1 to C,-
			char solutionChar;
			if (i == 0) solutionChar = 'C';
			else solutionChar = '-';
			
			// casting the spell to flip the tiles and update the solution array
			solution[currR][currC] = solutionChar;
			castSpell(current, r, c, currR, currC);
			
			//System.out.println("The solution grid is : ");
			//printGrid(solution, r, c);
			//System.out.println("The current grid is : ");
			//printGrid(current, r, c);

			// if the current and goal arrays are identical return true
			if(isIdentical(current, goal, r, c)) return true;
			// if not try the next tile
			if (solve(current, goal, solution, r, c, currR, currC + 1)) return true;

			// if not backtrack
			solution[currR][currC] = '0';
		
		}
		// return false if there is no solution
		return false;
	}
	
	
	// Compares the current and the goal arrays. 
	// Returns true if they are identical; returns false otherwise.
	public static boolean isIdentical(char[][] current, char[][] goal,int r, int c) {
		for (int i = 0; i < r; i++) 
			for (int j = 0; j < c; j++) 
				if (current[i][j] != goal[i][j]) return false;

		return true;
	}
	
	//  changes the value of the tile and its adjacent tiles (that shares a side) 
	public static void castSpell(char[][] current, int r, int c, int currR, int currC) { 
		if (currR >= 0 && currC >= 0 && currR < r && currC < c) {   // check if the row and column are out of bounds
																    // it may be unnecessary, but I preferred to leave it
			if(currR > 0 ) flip(current, currR - 1 , currC);        // flip the upper tile 
			if(currR < r - 1 ) flip(current, currR + 1 , currC);	// flip the below tile 
			if(currC > 0 ) flip(current, currR , currC - 1);		// flip the tile to the left
			if(currC < c - 1) flip(current, currR , currC + 1);		// flip the tile to the right
			flip(current, currR , currC);							// flip the current tile
		}

	}

	// it flips the value from 'A' -> 'D' and vice versa
	public static void flip(char[][] current,int currR, int currC ) {
		if (current[currR][currC] == 'A') {
			current[currR][currC] = 'D';
		}
		else current[currR][currC] = 'A';
	}
}
