package dev.finashkin.model;

import dev.finashkin.SudokuApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SudokuGeneratorTest {

    private SudokuApp sudokuApp;
    private SudokuGenerator sudokuGenerator;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        sudokuApp = new SudokuApp();
        sudokuGenerator = new SudokuGenerator();
    }

    @Test
    void testSudokuGeneration() throws NoSuchAlgorithmException {
        // Create a grid to populate
        int[][] grid = new int[9][9];

        // Call the method that generates the Sudoku
        sudokuGenerator.generateSudoku(grid);

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
    void testPopulateRandomSudoku() {
        // Create a grid to populate
        int[][] grid = new int[9][9];

        // Call the method to populate the grid
        sudokuGenerator.populateRandomSudoku(grid);

        // Verify the grid is filled with valid values (1-9)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(grid[row][col] >= 1 && grid[row][col] <= 9);
            }
        }

        // Verify each row contains unique values
        for (int row = 0; row < 9; row++) {
            Set<Integer> rowValues = new HashSet<>();
            for (int col = 0; col < 9; col++) {
                rowValues.add(grid[row][col]);
            }
            assertEquals(9, rowValues.size(), "Row " + row + " should contain 9 unique values");
        }

        // Verify each column contains unique values
        for (int col = 0; col < 9; col++) {
            Set<Integer> colValues = new HashSet<>();
            for (int row = 0; row < 9; row++) {
                colValues.add(grid[row][col]);
            }
            assertEquals(9, colValues.size(), "Column " + col + " should contain 9 unique values");
        }

        // Verify each 3x3 box contains unique values
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                Set<Integer> boxValues = new HashSet<>();
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        boxValues.add(grid[row][col]);
                    }
                }
                assertEquals(9, boxValues.size(), "Box at (" + boxRow + "," + boxCol + ") should contain 9 unique values");
            }
        }
    }

    @Test
    void testShuffleArray() {
        // Create an array to shuffle
        int[] original = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] shuffled = original.clone();

        // Shuffle the array
        sudokuGenerator.shuffleArray(shuffled);

        // Verify the array still contains all original values
        Set<Integer> originalValues = new HashSet<>();
        Set<Integer> shuffledValues = new HashSet<>();

        for (int i = 0; i < original.length; i++) {
            originalValues.add(original[i]);
            shuffledValues.add(shuffled[i]);
        }

        assertEquals(originalValues, shuffledValues, "Shuffled array should contain the same values as the original");

        // Verify the array has been shuffled (this could occasionally fail if the shuffle doesn't change the order)
        boolean isDifferent = false;
        for (int i = 0; i < original.length; i++) {
            if (original[i] != shuffled[i]) {
                isDifferent = true;
                break;
            }
        }

        assertTrue(isDifferent, "Array should be shuffled (different order than original)");
    }

    @Test
    void testPopulateGrid() {
        // Create an empty grid
        int[][] grid = new int[9][9];

        // Create an array of numbers 1-9
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        // Populate the grid
        sudokuGenerator.populateGrid(grid, numbers);

        // Verify the grid is filled with valid values (1-9)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(grid[row][col] >= 1 && grid[row][col] <= 9);
            }
        }

        // Verify each row contains all numbers from the input array
        for (int row = 0; row < 9; row++) {
            Set<Integer> rowValues = new HashSet<>();
            for (int col = 0; col < 9; col++) {
                rowValues.add(grid[row][col]);
            }
            assertEquals(9, rowValues.size(), "Row " + row + " should contain 9 unique values");
        }

        // Verify the pattern is consistent with the algorithm
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(numbers[(row * 3 + row / 3 + col) % 9], grid[row][col]);
            }
        }
    }

    @Test
    void testPopulateGrid_DifferentNumbers() {
        // Create an empty grid
        int[][] grid = new int[9][9];

        // Create a different array of numbers
        int[] numbers = {9, 8, 7, 6, 5, 4, 3, 2, 1};

        // Populate the grid
        sudokuGenerator.populateGrid(grid, numbers);

        // Verify the grid is filled with valid values (1-9)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(grid[row][col] >= 1 && grid[row][col] <= 9);
            }
        }

        // Verify each row contains all numbers from the input array
        for (int row = 0; row < 9; row++) {
            Set<Integer> rowValues = new HashSet<>();
            for (int col = 0; col < 9; col++) {
                rowValues.add(grid[row][col]);
            }
            assertEquals(9, rowValues.size(), "Row " + row + " should contain 9 unique values");
        }

        // Verify the pattern is consistent with the algorithm
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(numbers[(row * 3 + row / 3 + col) % 9], grid[row][col]);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {20, 30, 40, 50})
    void testRemoveNumbers(int cellsToKeep) {
        // Create a grid to populate
        int[][] grid = new int[9][9];

        // Generate a solved Sudoku
        sudokuGenerator.generateSudoku(grid);

        // Remove numbers
        sudokuGenerator.removeNumbers(grid, cellsToKeep);

        // Count non-zero cells
        int nonZeroCells = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] != 0) {
                    nonZeroCells++;
                }
            }
        }

        // Verify the correct number of cells were kept
        assertEquals(cellsToKeep, nonZeroCells, "Number of non-zero cells should match cellsToKeep");
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0",    // Keep no cells (extreme case)
        "1, 1",    // Keep only 1 cell
        "10, 10",  // Keep 10 cells
        "81, 81"   // Keep all cells (extreme case)
    })
    void testRemoveNumbers_EdgeCases(int cellsToKeep, int expectedCells) {
        // Create a grid to populate
        int[][] grid = new int[9][9];

        // Generate a solved Sudoku
        sudokuGenerator.generateSudoku(grid);

        // Remove numbers
        sudokuGenerator.removeNumbers(grid, cellsToKeep);

        // Count non-zero cells
        int nonZeroCells = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] != 0) {
                    nonZeroCells++;
                }
            }
        }

        // Verify the correct number of cells were kept
        assertEquals(expectedCells, nonZeroCells, "Number of non-zero cells should match cellsToKeep");
    }
}
