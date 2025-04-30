package dev.finashkin.utils;

/**
 * Helper class for counting the number of solutions for a Sudoku puzzle.
 * This class extends SolveSudokuHelper and adds functionality to count
 * solutions up to 2. If more than one solution is found, the counting stops
 * as we already know the solution is not unique.
 */
public class SolutionCounter extends SolveSudokuHelper {

    private int solutionCount;

    /**
     * Counts the number of solutions for a given Sudoku puzzle.
     * The method stops counting after finding more than one solution,
     * as we already know the solution is not unique at that point.
     * 
     * @param grid the 9x9 Sudoku grid to solve
     * @return the number of valid solutions (1 for a unique solution, 2 for multiple solutions)
     */
    public int countSolutions(int[][] grid) {
        // Create a copy of the grid to avoid modifying the original
        int[][] gridCopy = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, gridCopy[row], 0, 9);
        }

        solutionCount = 0;
        countSolutionsHelper(gridCopy, 0, 0);
        return solutionCount;
    }

    /**
     * Recursively counts solutions for a Sudoku puzzle using backtracking.
     * This method tries to place numbers 1-9 in empty cells and continues searching
     * until it finds more than one solution. If more than one solution is found,
     * the method stops counting as we already know the solution is not unique.
     * 
     * @param grid the 9x9 Sudoku grid to solve
     * @param row the current row being processed
     * @param col the current column being processed
     * @return true if a solution was found, false otherwise
     */
    private boolean countSolutionsHelper(int[][] grid, int row, int col) {
        // If we've filled the entire grid, we've found a solution
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                // Found a valid solution
                solutionCount++;
                System.out.println("[DEBUG_LOG] Found solution #" + solutionCount);

                // If we've found more than one solution, we can stop counting
                if (solutionCount > 1) {
                    return true;
                }

                // Return true to indicate we found a solution, but we'll continue searching
                // only if we haven't found more than one solution yet
                return true;
            }
        }

        // If this cell is already filled, move to the next cell
        if (grid[row][col] != 0) {
            return countSolutionsHelper(grid, row + 1, col);
        }

        // Try each number in this cell
        boolean foundSolution = false;
        for (int num = 1; num <= 9; num++) {
            // If we've already found more than one solution, we can stop
            if (solutionCount > 1) {
                return true;
            }

            if (isValidPlacement(grid, row, col, num)) {
                // Place the number
                grid[row][col] = num;

                // Continue searching for more solutions
                boolean result = countSolutionsHelper(grid, row + 1, col);

                // If we found a solution, mark it but continue searching
                if (result) {
                    foundSolution = true;

                    // If we've found more than one solution, we can stop
                    if (solutionCount > 1) {
                        return true;
                    }
                }

                // Backtrack to find more solutions
                grid[row][col] = 0;
            }
        }

        return foundSolution;
    }
}
