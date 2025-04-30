package dev.finashkin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for edge cases in the SudokuSolver class.
 * These tests verify the behavior of the SudokuSolver class when dealing with
 * edge cases like invalid inputs, empty grid, already solved grid, etc.
 */
class SudokuSolverEdgeCasesTest {

    private SudokuSolver sudokuSolver;

    @BeforeEach
    void setUp() {
        sudokuSolver = new SudokuSolver();
    }

    @Test
    void testSolveSudoku_InvalidGrid() {
        // Create an invalid grid with duplicate values in a row
        int[][] grid = {
            {5, 3, 5, 0, 7, 0, 0, 0, 0}, // Duplicate 5 in first row
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        // Try to solve the grid
        boolean result = sudokuSolver.solveSudoku(grid);

        // Verify the grid could not be solved
        assertFalse(result);
    }

    @Test
    void testSolveSudoku_EmptyGrid() {
        // Create an empty grid
        int[][] grid = new int[9][9];

        // Solve the grid
        boolean result = sudokuSolver.solveSudoku(grid);

        // Verify the grid was solved
        assertTrue(result);

        // Verify the grid contains valid values (1-9)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(grid[row][col] >= 1 && grid[row][col] <= 9);
            }
        }

        // Verify each row contains unique values
        for (int row = 0; row < 9; row++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int col = 0; col < 9; col++) {
                int num = grid[row][col];
                assertFalse(used[num], "Duplicate number " + num + " in row " + row);
                used[num] = true;
            }
        }

        // Verify each column contains unique values
        for (int col = 0; col < 9; col++) {
            boolean[] used = new boolean[10]; // Index 0 is not used
            for (int row = 0; row < 9; row++) {
                int num = grid[row][col];
                assertFalse(used[num], "Duplicate number " + num + " in column " + col);
                used[num] = true;
            }
        }

        // Verify each 3x3 box contains unique values
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
    void testSolveSudoku_AlreadySolvedGrid() {
        // Create an already solved grid
        int[][] grid = {
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

        // Make a copy of the original grid
        int[][] originalGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, originalGrid[row], 0, 9);
        }

        // Solve the grid
        boolean result = sudokuSolver.solveSudoku(grid);

        // Verify the grid was "solved" (should return true)
        assertTrue(result);

        // Verify the grid was not changed
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(originalGrid[row][col], grid[row][col],
                    "Grid should not be changed at position (" + row + "," + col + ")");
            }
        }
    }

    @Test
    void testSolveSudoku_OneEmptyCell() {
        // Create a grid with only one empty cell
        int[][] grid = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 0, 3, 5}, // One empty cell at (7,6)
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        // Solve the grid
        boolean result = sudokuSolver.solveSudoku(grid);

        // Verify the grid was solved
        assertTrue(result);

        // Verify the empty cell was filled correctly
        assertEquals(6, grid[7][6]);
    }

    @Test
    void testSolveSudoku_UnsolvableGrid() {
        // Create an unsolvable grid (with conflicting values)
        int[][] grid = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 5}  // Conflict with last row, last column
        };

        // Try to solve the grid
        boolean result = sudokuSolver.solveSudoku(grid);

        // Verify the grid could not be solved
        assertFalse(result);
    }

    @Test
    void testCopyGrid_NullGrid() {
        // Try to copy a null grid
        try {
            int[][] copy = sudokuSolver.copyGrid(null);
            fail("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    void testCopyGrid_EmptyGrid() {
        // Create an empty grid
        int[][] grid = new int[9][9];

        // Copy the grid
        int[][] copy = sudokuSolver.copyGrid(grid);

        // Verify the copy is not the same object as the original
        assertNotSame(grid, copy);

        // Verify the copy has the same values as the original
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(grid[row][col], copy[row][col]);
            }
        }
    }
}