package dev.finashkin.model;

/**
 * Class responsible for validating Sudoku puzzles.
 * This class provides methods to check if a Sudoku grid is solved correctly
 * according to Sudoku rules (no repeating numbers in rows, columns, or 3x3 boxes).
 */
public class SudokuValidator {

    /**
     * Checks if the Sudoku grid is solved correctly.
     * This method verifies that all cells in the grid contain valid numbers
     * according to Sudoku rules (no repeating numbers in rows, columns, or 3x3 boxes).
     *
     * @param grid the 9x9 grid to check
     * @return true if the grid is solved correctly, false otherwise
     */
    public boolean isSolved(int[][] grid) {
        // Check if the grid is complete (no empty cells)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    return false; // Grid is not complete
                }
            }
        }

        // Check rows
        for (int row = 0; row < 9; row++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int col = 0; col < 9; col++) {
                int num = grid[row][col];
                if (used[num]) {
                    return false; // Duplicate number in row
                }
                used[num] = true;
            }
        }

        // Check columns
        for (int col = 0; col < 9; col++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int row = 0; row < 9; row++) {
                int num = grid[row][col];
                if (used[num]) {
                    return false; // Duplicate number in column
                }
                used[num] = true;
            }
        }

        // Check 3x3 boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] used = new boolean[10]; // Index 0 is not used
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        int num = grid[row][col];
                        if (used[num]) {
                            return false; // Duplicate number in box
                        }
                        used[num] = true;
                    }
                }
            }
        }

        return true; // Grid is complete and valid
    }
}