package dev.finashkin.utils;

import dev.finashkin.model.SudokuGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SolutionCounter.
 * This class tests whether generated Sudoku puzzles have unique solutions.
 */
class SolutionCounterTest {

    private SudokuGenerator sudokuGenerator;
    private SolutionCounter solutionCounter;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        sudokuGenerator = new SudokuGenerator();
        solutionCounter = new SolutionCounter();
    }

    @Test
    void testCompleteSudokuHasOneSolution() {
        // Generate a complete Sudoku puzzle
        int[][] grid = new int[9][9];
        sudokuGenerator.generateSudoku(grid);

        // Count the number of solutions
        int solutionCount = solutionCounter.countSolutions(grid);

        // A complete valid Sudoku should have exactly one solution
        assertEquals(1, solutionCount, "A complete Sudoku should have exactly one solution");
    }

    @ParameterizedTest
    @ValueSource(ints = {20, 30, 40, 50})
    void testSudokuWithRemovedNumbersSolutionCount(int cellsToKeep) {
        // Generate a complete Sudoku puzzle
        int[][] grid = new int[9][9];
        sudokuGenerator.generateSudoku(grid);

        // Remove numbers to create a puzzle
        sudokuGenerator.removeNumbers(grid, cellsToKeep);

        // Count the number of solutions
        int solutionCount = solutionCounter.countSolutions(grid);

        // Print the solution count for analysis
        System.out.println("[DEBUG_LOG] Cells kept: " + cellsToKeep + ", Solution count: " + solutionCount);

        // Check if the puzzle has a unique solution
        // Note: This test might fail if the random removal of numbers creates a puzzle with multiple solutions
        assertEquals(1, solutionCount, "A Sudoku puzzle should have exactly one solution");
    }

    @Test
    void testGridWithMultipleSolutions() {
        // Create a simple grid with multiple solutions
        // This is a very sparse grid with only a few numbers filled in
        int[][] grid = new int[9][9];

        // Fill in a few numbers to constrain the grid
        grid[0][0] = 1;
        grid[1][1] = 2;
        grid[2][2] = 3;
        grid[3][3] = 4;
        grid[4][4] = 5;
        grid[5][5] = 6;
        grid[6][6] = 7;
        grid[7][7] = 8;
        grid[8][8] = 9;

        // Count the number of solutions
        int solutionCount = solutionCounter.countSolutions(grid);

        // This grid should have multiple solutions
        assertTrue(solutionCount > 1, "The sparse grid should have multiple solutions");
        System.out.println("[DEBUG_LOG] Sparse grid has " + solutionCount + " solutions");
    }
}
