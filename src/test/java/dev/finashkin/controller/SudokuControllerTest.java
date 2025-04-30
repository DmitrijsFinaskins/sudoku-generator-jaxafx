package dev.finashkin.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SudokuController class.
 * These tests verify the functionality of the SudokuController class.
 */
class SudokuControllerTest {

    private SudokuController sudokuController;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        sudokuController = new SudokuController();
    }

    @Test
    void testGenerateSudoku() {
        // Generate a Sudoku puzzle
        sudokuController.generateSudoku();

        // Get the generated grid
        int[][] grid = sudokuController.getGrid();

        // Verify the grid is not null
        assertNotNull(grid);

        // Verify the grid has the correct dimensions
        assertEquals(9, grid.length);
        assertEquals(9, grid[0].length);

        // Verify the grid contains valid values (1-9)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(grid[row][col] >= 1 && grid[row][col] <= 9);
            }
        }
    }

    @Test
    void testRemoveNumbers() {
        // Generate a Sudoku puzzle
        sudokuController.generateSudoku();

        // Remove numbers to create a puzzle
        sudokuController.removeNumbers(30);

        // Get the generated grid
        int[][] grid = sudokuController.getGrid();

        // Count the number of non-zero cells
        int nonZeroCells = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] != 0) {
                    nonZeroCells++;
                }
            }
        }

        // Verify the number of non-zero cells is as expected
        assertEquals(30, nonZeroCells);
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
        boolean result = sudokuController.solveSudoku(grid);

        // Verify the grid was solved
        assertTrue(result);

        // Verify the solution is valid
        assertTrue(sudokuController.isSolved(grid));
    }

    @Test
    void testIsSolved() {
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
        assertTrue(sudokuController.isSolved(grid));
    }

    @Test
    void testGettersAndSetters() {
        // Test getDifficultyLevel and setDifficultyLevel
        assertEquals(30, sudokuController.getDifficultyLevel()); // Default value
        sudokuController.setDifficultyLevel(40);
        assertEquals(40, sudokuController.getDifficultyLevel());

        // Test getGrid
        assertNotNull(sudokuController.getGrid());
        assertEquals(9, sudokuController.getGrid().length);
        assertEquals(9, sudokuController.getGrid()[0].length);

        // Test getSolverGrid
        assertNotNull(sudokuController.getSolverGrid());
        assertEquals(9, sudokuController.getSolverGrid().length);
        assertEquals(9, sudokuController.getSolverGrid()[0].length);

        // Test getUserEnteredCells
        assertNotNull(sudokuController.getUserEnteredCells());
        assertEquals(9, sudokuController.getUserEnteredCells().length);
        assertEquals(9, sudokuController.getUserEnteredCells()[0].length);

        // Test getUserEnteredDigitsColor and setUserEnteredDigitsColor
        assertEquals(Color.BLUE, sudokuController.getUserEnteredDigitsColor()); // Default value
        Color newUserColor = Color.RED;
        sudokuController.setUserEnteredDigitsColor(newUserColor);
        assertEquals(newUserColor, sudokuController.getUserEnteredDigitsColor());

        // Test getGeneratedDigitsColor and setGeneratedDigitsColor
        assertEquals(Color.BLACK, sudokuController.getGeneratedDigitsColor()); // Default value
        Color newGenColor = Color.GREEN;
        sudokuController.setGeneratedDigitsColor(newGenColor);
        assertEquals(newGenColor, sudokuController.getGeneratedDigitsColor());
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
        int[][] copy = sudokuController.copyGrid(original);

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
