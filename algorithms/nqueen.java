import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class main {
	// set of global variables
	public static int count = 0;  // count var to count how many solutions are there
	public static int n;       // size of the board n*n
	// the main method
	public static void main(String[] args) {
		
		n = 0; // initialize the board size to 0 for now
		Scanner scnr = new Scanner(System.in);  // Scanner object to read in the board size
		System.out.println("Enter the board dimension. Note: It has to be between 4 and 30 \nn = "); // prompt to enter the board size
		while(n < 4 || n > 30) {  // just to ensure that the board size is within the range
			n = scnr.nextInt(); // reading the board size
			if (n < 4 || n > 30)	System.out.println("n has to be between 4 and 30, please enter a valid value: ");
		}
		
		// create the board and initialize each element with '-'
		char board [][] = new char [n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = '-';
			}
		}
		// this array is to check if the next the move is valid or not
		// 1 if the move is valid, 0 if the move is not valid
		char isValid [][] = new char [n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				isValid[i][j] = '1';
			}
		}

		// if there is a solution print it
		// else print that the solution cannot be found
		if(solve(board, isValid, 0)) {
			System.out.println("The Solution is --> ");
			print(board);	
		}
		else System.out.println("Solution cannot be found");


		// reset the two arrays back to its initial state
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = '-';
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				isValid[i][j] = '1';
			}
		}
		
		// finding how many solutions are there and print it
		// note: this may take too much time to run to get all the number of all the solutions
		noOfSolutions(board, isValid, 0);
		System.out.printf("No. of solutions of the n queen problem of size %d is %d %n", n, count);
		
		
		// the following commented section is for testing how much time it is going to take
		// the program to run for n input from 4 to 30
		/*
		for (int i = 4; i <= 30; i++) {
			n = i;
			char board2 [][] = new char [n][n];
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					board2[j][k] = '-';
				}
			}
			char isValid2 [][] = new char [n][n];
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					isValid2[j][k] = '1';
				}
			}
			long startTime;
			long endTime;
			long runTime = 0;
			startTime = System.currentTimeMillis();
			if(solve(board2, isValid2, 0)) {
				endTime = System.currentTimeMillis();
				runTime = endTime - startTime;
			}
			if ( runTime < 5555) {
			System.out.println("It took " + runTime + " milliseconds");
			String hms = String.format("%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(runTime),
					TimeUnit.MILLISECONDS.toMinutes(runTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(runTime)),
					TimeUnit.MILLISECONDS.toSeconds(runTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runTime)),
					TimeUnit.MILLISECONDS.toMillis(runTime) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(runTime)));
			System.out.println("                                                 hh:mm:ss: ms");
			System.out.println("The run time of the n queen problem of size " + n + " is " + hms);
			}

		}
*/
		scnr.close();
	}
	
	public static boolean solve(char[][] board, char [][] isValid, int r) {

		if(r == n) return true;
		for(int i = 0; i < n; i++) {
			if (board[r][i] == '-') {
				if(isValid(isValid, r, i)) {
					board[r][i] = 'X';
					updateValidArray(board, isValid);
					if(solve(board, isValid, r + 1)) return true;
					board[r][i] = '-';
					updateValidArray(board, isValid);
				}
			}
		}
		return false;
	}
	
	// pretty simple. if 1 return true, 0 return false.
	public static boolean isValid(char[][] isValid ,int r, int c) {
		if (isValid[r][c] == '1') return true;
		return false;
	}
	
	// this method will update the isValid array.
	public static void updateValidArray(char[][] board, char[][] isValid) {
		// reset the array to the initial state
		for (int i = 0; i < n; i++) 
			for (int j = 0; j < n; j++) 
				isValid[i][j] = '1';
		
		// for every element in the board, if X is detected, mark the attacking positions with zero
		for (int m = 0; m < n; m++) {
			for (int k = 0; k < n; k++) {
				if(board[m][k] == 'X') {
					int rr = m;
					int cc = k;
					for (int i = 0; i < n; i ++) isValid[i][k] = '0'; // mark the row with zero
					for (int i = 0; i < n; i ++) isValid[m][i] = '0'; // mark the column with zero
					// the while loops conditions are to ensure that the row and column are within the bounds of n 
					while (rr < n  && rr >= 0 && cc < n  && cc >= 0 ) isValid[rr++][cc++] = '0'; // mark the upper right diagonal with zero
					rr = m;
					cc = k;
					while (rr < n  && rr >= 0 && cc < n  && cc >= 0 ) isValid[rr--][cc--] = '0'; // mark the lower left diagonal with zero
					rr = m;
					cc = k;
					while (rr < n  && rr >= 0 && cc < n  && cc >= 0 ) isValid[rr--][cc++] = '0'; // mark the lower right diagonal with zero
					rr = m;
					cc = k;
					while (rr < n  && rr >= 0 && cc < n  && cc >= 0 ) isValid[rr++][cc--] = '0'; // mark the upper left diagonal with zero
					rr = m;
					cc = k;
				}
			}
		}
	}
	
	// simple nested loop to print a two dimensional array
	public static void print(char [][] array) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%c ", array[i][j]);
			}
			System.out.println();

		}
		System.out.println();
	}
	
	// it is almost the same as the solve method, but it includes the count increment.
	// note: this may take too much time to run to get all the number of all the solutions
	public static boolean noOfSolutions(char[][] board, char [][] isValid, int r) {
		if( r == n) {
			count ++;
			return true;
		}

		for (int i = 0; i < n; i ++) {
			if(isValid(isValid, r, i)) {
				board[r][i] = 'X';
				updateValidArray(board, isValid);
				noOfSolutions(board, isValid, r + 1);
				board[r][i] = '-';
				updateValidArray(board, isValid);
			}
		}
		return false;
	}
}
