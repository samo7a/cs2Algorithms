
import java.util.Scanner;

public class main {
	
	static int grid[][];
	static int dim;
	
	public static void main (String[] args) {
		Scanner scnr = new Scanner(System.in);
		dim = scnr.nextInt();
		
		grid = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				grid[i][j] = scnr.nextInt();
			}
		}
		
		if (solve(0,0)) {
			printSolution();
		} else {
			System.out.println("Unsolvable!");

		}
		
	}
	
	public static boolean solve(int r, int c) {
				
				if (r == dim) return true;
				if (c == dim) return solve (r+1, 0);
				if (grid[r][c] != 0) return solve (r, c+1);
				
				for (int i = 1; i <= dim; i++) {
					grid[r][c] = i;
					if (valid(r,c)) if (solve(r, c+1)) return true;
					else grid[r][c] = 0;
				}
		
		return false;
		
	}
	
	public static void printSolution () {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				System.out.printf("%2d ", grid[i][j]);
			}
			System.out.println();
		}
		
	}
	
	public static boolean valid(int r, int c) {
		for (int i = 0; i < dim; i++) {
			if (grid[r][c] == grid[i][c] && i != r) return false;
			if (grid[r][c] == grid[r][i] && i != c) return false;
		}
		int subsize = 1;
		while (subsize * subsize != dim) {
			subsize ++;
		}
		
		int rr = (r/subsize) * subsize;
		int cc = (c/subsize) * subsize;
		
		for (int i = 0; i < subsize; i++) {
			for (int j = 0; j < subsize; j++) {
				int realR = i + rr;
				int realC = j + cc;
				if (grid[realR][realC] == grid[r][c] && (r!= realR || c!= realC)) return false;
			}
		}
		return true;
	}
	
	
}
