package dev.finashkin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Integration tests for the SudokuApp class.
 * These tests verify the high-level functionality of the application.
 * Detailed tests for specific components are in their respective test classes:
 * - SudokuGeneratorTest
 * - SudokuValidatorTest
 * - SudokuSolverTest
 * - SudokuUITest
 */
@ExtendWith(MockitoExtension.class)
class SudokuAppTest {

    private SudokuApp sudokuApp;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        sudokuApp = new SudokuApp();
    }

    @Test
    void testSudokuGeneration() throws NoSuchAlgorithmException {
        // Call the method that generates the Sudoku
        sudokuApp.generateSudoku();

        // Get the generated grid
        int[][] grid = sudokuApp.getGrid();

        // Verify the grid is not null
        assertNotNull(grid);

        // Verify the grid has the correct dimensions
        assertEquals(9, grid.length);
        assertEquals(9, grid[0].length);

        // Verify the grid contains valid values (0-9)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(grid[row][col] >= 0 && grid[row][col] <= 9);
            }
        }
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
        boolean result = sudokuApp.solveSudoku(grid);

        // Verify the grid was solved
        assertTrue(result);

        // Verify the solution is valid
        assertTrue(sudokuApp.isSolved(grid));
    }

    @Test
    void testIsSolved_ValidSolution() {
        // Create a valid Sudoku solution
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

        // Verify the solution is valid
        assertTrue(sudokuApp.isSolved(grid));
    }
}
