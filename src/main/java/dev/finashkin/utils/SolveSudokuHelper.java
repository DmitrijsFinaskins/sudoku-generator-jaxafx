package dev.finashkin.utils;

/**
 * Helper class for solving Sudoku puzzles.
 * This class provides methods to solve Sudoku puzzles using a backtracking algorithm
 * and to validate number placements according to Sudoku rules.
 */
public class SolveSudokuHelper {

    /**
     * Validates the initial state of a Sudoku grid.
     * This method checks if the grid contains any violations of Sudoku rules
     * (e.g., duplicate numbers in a row, column, or box).
     *
     * @param grid the 9x9 Sudoku grid to validate
     * @return true if the grid is valid, false otherwise
     */
    public boolean isValidGrid(int[][] grid) {
        // Check rows
        for (int row = 0; row < 9; row++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int col = 0; col < 9; col++) {
                int num = grid[row][col];
                if (num != 0) { // Skip empty cells
                    if (used[num]) {
                        return false; // Duplicate number in row
                    }
                    used[num] = true;
                }
            }
        }

        // Check columns
        for (int col = 0; col < 9; col++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int row = 0; row < 9; row++) {
                int num = grid[row][col];
                if (num != 0) { // Skip empty cells
                    if (used[num]) {
                        return false; // Duplicate number in column
                    }
                    used[num] = true;
                }
            }
        }

        // Check 3x3 boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] used = new boolean[10]; // Index 0 is not used
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        int num = grid[row][col];
                        if (num != 0) { // Skip empty cells
                            if (used[num]) {
                                return false; // Duplicate number in box
                            }
                            used[num] = true;
                        }
                    }
                }
            }
        }

        return true; // Grid is valid
    }

    /**
     * Recursively solves a Sudoku puzzle using backtracking.
     * This method tries to place numbers 1-9 in empty cells and backtracks when
     * a placement leads to an invalid solution.
     *
     * @param grid the 9x9 Sudoku grid to solve
     * @param row the current row being processed
     * @param col the current column being processed
     * @return true if the puzzle was successfully solved, false otherwise
     */
    public boolean solveSudokuHelper(int[][] grid, int row, int col) {
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                return true; // Solved the entire grid
            }
        }

        if (grid[row][col] != 0) {
            return solveSudokuHelper(grid, row + 1, col);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num;
                if (solveSudokuHelper(grid, row + 1, col)) {
                    return true; // Found a valid solution
                }
                grid[row][col] = 0; // Backtrack if the placement is not valid
            }
        }

        return false; // No valid number can be placed
    }

    /**
     * Checks if a number can be placed in a specific position according to Sudoku rules.
     * This method verifies that the number doesn't already exist in the same row,
     * column, or 3x3 box.
     *
     * @param grid the 9x9 Sudoku grid
     * @param row the row index where the number would be placed
     * @param col the column index where the number would be placed
     * @param num the number to check (1-9)
     * @return true if the number can be placed at the specified position, false otherwise
     */
    public boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        // Check if 'num' is not already in the current row or column
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }

        // Check if 'num' is not already in the 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[boxRow + i][boxCol + j] == num) {
                    return false;
                }
            }
        }

        // 'num' can be placed in this position
        return true;
    }
}
