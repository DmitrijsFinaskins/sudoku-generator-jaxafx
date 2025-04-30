package dev.finashkin.model;

import dev.finashkin.utils.SolveSudokuHelper;

/**
 * Class responsible for solving Sudoku puzzles.
 * This class provides methods to solve Sudoku puzzles using a backtracking algorithm.
 */
public class SudokuSolver {

    private final SolveSudokuHelper solveSudokuHelper = new SolveSudokuHelper();

    /**
     * Solves the given Sudoku grid using a backtracking algorithm.
     * 
     * @param grid the 9x9 grid to solve
     * @return true if the grid was successfully solved, false otherwise
     */
    public boolean solveSudoku(int[][] grid) {
        // First validate the initial grid
        if (!solveSudokuHelper.isValidGrid(grid)) {
            return false;
        }

        // Start solving from the top-left cell (0,0)
        return solveSudokuHelper.solveSudokuHelper(grid, 0, 0);
    }

    /**
     * Creates a copy of the grid to avoid modifying the original.
     * 
     * @param grid the original grid to copy
     * @return a new copy of the grid
     */
    public int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, 9);
        }
        return copy;
    }
}
