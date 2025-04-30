package dev.finashkin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SudokuSolver class.
 * These tests verify the functionality of the SudokuSolver class.
 */
class SudokuSolverTest {

    private SudokuSolver sudokuSolver;

    @BeforeEach
    void setUp() {
        sudokuSolver = new SudokuSolver();
    }

    @Test
    void testSolveSudoku() {
        // Create a solvable Sudoku grid
        int[][] grid = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        // Solve the grid
        boolean result = sudokuSolver.solveSudoku(grid);

        // Verify the grid was solved
        assertTrue(result);

        // Verify the solution is valid
        // We don't have direct access to SudokuValidator here, so we'll check manually
        // Check rows
        for (int row = 0; row < 9; row++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int col = 0; col < 9; col++) {
                int num = grid[row][col];
                assertFalse(used[num], "Duplicate number " + num + " in row " + row);
                used[num] = true;
            }
        }

        // Check columns
        for (int col = 0; col < 9; col++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int row = 0; row < 9; row++) {
                int num = grid[row][col];
                assertFalse(used[num], "Duplicate number " + num + " in column " + col);
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
                        assertFalse(used[num], "Duplicate number " + num + " in box at " + boxRow + "," + boxCol);
                        used[num] = true;
                    }
                }
            }
        }
    }

    @Test
    void testCopyGrid() {
        // Create a grid
        int[][] original = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        // Copy the grid
        int[][] copy = sudokuSolver.copyGrid(original);

        // Verify the copy is not the same object as the original
        assertNotSame(original, copy);

        // Verify the copy has the same values as the original
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(original[row][col], copy[row][col]);
            }
        }

        // Modify the copy and verify the original is unchanged
        copy[0][0] = 0;
        assertEquals(5, original[0][0]);
    }
}