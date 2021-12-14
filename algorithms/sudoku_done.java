
// This program solves a given sudoku puzzle

import java.util.*;

// The class for the sudoku solver
public class sudoku_done {

    // Our sudoku grid that we will modify
    public static int[][] grid;

    // The length and height of the sudoku grid
    public static int dim;

    // The main method
    public static void main(String[] Args) {

        // We will read in the puzzle through standard input
        Scanner sc = new Scanner(System.in);

        // Read in the dimensions of the board
        dim = sc.nextInt();

        // Create the board
        grid = new int[dim][dim];

        // Read in the full board
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // Try to solve the puzzle!
        if (solve(0, 0)) {

            // We found a solution! Now print it.
            printGrid();
        } else {

            // Puzzle could not be solved T_T
            System.out.println("The initial puzzle was not solvable!");
            printGrid();
        }
    }

    // The method to print our grid
    public static void printGrid() {
        int subSize = 1;
        while (subSize * subSize != dim)
            subSize++;
        for (int i = 0; i <= dim; i++) {
            for (int j = 0; j < dim; j++) { 
                System.out.print(((i%subSize)*(j%subSize)) == 0 ? "#" : "+");
                System.out.print((i%subSize==0) ? "==" : "--");
            }
            System.out.println("#");
            if (i==dim) break;
            for (int j = 0; j <= dim; j++) {
                System.out.print((j%subSize)==0?"|":":");
                if (j==dim) {
                    System.out.println();
                    break;
                }
                System.out.printf("%2d",grid[i][j]);
            }
        }
    }

    public static boolean valid(int r, int c) {
        for (int i = 0; i < dim; i++) {
            if (grid[i][c] == grid[r][c] && r != i) return false;
            if (grid[r][i] == grid[r][c] && c != i) return false;
        }
        int subSize = 1;
        while (subSize * subSize != dim)
            subSize++;
        int i = (r/subSize)*subSize;
        int j = (c/subSize)*subSize;

        for (int ii = 0; ii < subSize; ii++)
            for (int jj = 0; jj < subSize; jj++) {
                if (grid[i+ii][j+jj] == grid[r][c] && (i+ii != r || j+jj != c)) return false;
            }
        return true;
    }

    // The method to solve the sudoku puzzle
    public static boolean solve(int r, int c) {
        if (r == dim) return true;
        if (c == dim ) return solve(r + 1, 0);
        if (grid[r][c] != 0) return solve(r, c + 1);
        for (int i = 1; i <= dim; i++) {
            grid[r][c] = i;
            if (valid(r, c) && solve(r, c + 1)) return true;
            grid[r][c] = 0;
        }
        return false;
    }
}
