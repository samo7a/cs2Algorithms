
// Not the most efficient solution to the binairo problem

import java.util.*;

public class binairo {
    public static int[][] grid;
    public static int n, m;
    public static int EMPTY = -1;
    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);

        // Make the grid
        n = sc.nextInt(); // number of rows
        m = sc.nextInt(); // number of columns
        grid = new int[n][m];

        // Read in the contents
        for (int i = 0; i < n; i++) {

            // Read a line (good thing there aren't spaces)
            String line = sc.next();

            // Loop through each column of the current row
            for (int j = 0; j < m; j++) {
                if (line.charAt(j) == '-')
                    grid[i][j] = EMPTY;
                else
                    grid[i][j] = line.charAt(j) -'0';
            }
        }

        // Try to solve
        if (solve(0,0)) {
            printGrid();
        } else {
            System.out.println("Not solvable");
        }
    }

    // Function to print a grid
    public static void printGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1)
                    System.out.print("B");
                else if (grid[i][j] == 0)
                    System.out.print("W");
                else 
                    System.out.print("-");
            }
            System.out.println();
        }
    }

    public static boolean valid(int r, int c) {
        // triples in column
        if (r >= 2)
            if (grid[r][c] == grid[r-1][c] && grid[r-1][c] == grid[r-2][c])
                return false;
        if (r >= 1 && r < n - 1)
            if (grid[r][c] == grid[r-1][c] && grid[r-1][c] == grid[r+1][c])
                return false;
        if (r < n - 2)
            if (grid[r][c] == grid[r+1][c] && grid[r+1][c] == grid[r+2][c])
                return false;

        // triples in row
        if (c >= 2)
            if (grid[r][c] == grid[r][c-1] && grid[r][c-1] == grid[r][c-2])
                return false;
        if (c >= 1 && c < m - 1)
            if (grid[r][c] == grid[r][c-1] && grid[r][c-1] == grid[r][c+1])
                return false;
        if (c < m - 2)
            if (grid[r][c] == grid[r][c+1] && grid[r][c+1] == grid[r][c+2])
                return false;

        int row0 = 0;
        int row1 = 0;
        int col0 = 0;
        int col1 = 0;
        
        // check equal number 0/1 in row
        for (int j = 0; j < m; j++)
            if (grid[r][j] == 1)
                row1++;
            else if (grid[r][j] == 0)
                row0++;
        if (row1 + row1 > m) return false; // more than half 1's
        if (row0 + row0 > m) return false; // more than half 0's
        
        // check equal number 0/1 in column
        for (int i = 0; i < n; i++)
            if (grid[i][c] == 1)
                col1++;
            else if (grid[i][c] == 0)
                col0++;
        if (col1 + col1 > n) return false; // more than half 1's
        if (col0 + col0 > n) return false; // more than half 0's

        // Check for duplicate rows
        if (row1 + row0 == m) {
            for (int i = 0; i < n; i++) { // try all rows
                if (i == r) continue; // don't compare on self
                int j;
                for (j = 0; j < m && grid[i][j] == grid[r][j]; j++)
                    ;// compare until different
                if (j == m) return false;
            }
        }
        
        // Check for duplicate cols
        if (col1 + col0 == m) {
            for (int j = 0; j < m; j++) { // try all columns
                if (j == c) continue; // don't compare on self
                int i;
                for (i = 0; i < n && grid[i][j] == grid[i][c]; i++)
                    ;// compare until different
                if (i == n) return false;
            }
        }

        // Probably good
        return true;
    }

    public static boolean solve(int r, int c) {
        if (r == n) return true;
        if (c == m) return solve(r + 1, 0);
        if (grid[r][c] != EMPTY) return solve(r, c + 1);

        // First try b
        grid[r][c] = 1;
        if (valid(r, c) && solve(r, c + 1))
            return true;

        // second try w
        grid[r][c] = 0;
        if (valid(r, c) && solve(r, c + 1))
            return true;

        // backtrack
        grid[r][c] = EMPTY;

        return false;
    }
}
