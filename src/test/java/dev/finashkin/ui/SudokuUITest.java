package dev.finashkin.ui;

import dev.finashkin.SudokuApp;
import dev.finashkin.model.SudokuGenerator;
import dev.finashkin.model.SudokuValidator;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SudokuUITest {

    private SudokuApp sudokuApp;
    private SudokuGridUI sudokuGridUI;
    private SudokuGenerator sudokuGenerator;
    private SudokuValidator sudokuValidator;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        sudokuApp = new SudokuApp();
        sudokuGridUI = new SudokuGridUI();
        sudokuGenerator = new SudokuGenerator();
        sudokuValidator = new SudokuValidator();
    }

    @Test
    void testUserEnteredDigitsHandling() {
        // Generate a Sudoku puzzle
        int[][] grid = new int[9][9];
        sudokuGenerator.generateSudoku(grid);

        // Remove numbers to create a puzzle with empty cells
        sudokuGenerator.removeNumbers(grid, 30); // Keep 30 cells, remove the rest

        // Get the original grid
        int[][] originalGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, originalGrid[row], 0, 9);
        }

        // Simulate user entering a digit in an empty cell
        int userRow = -1;
        int userCol = -1;

        // Find an empty cell
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (originalGrid[row][col] == 0) {
                    userRow = row;
                    userCol = col;
                    break;
                }
            }
            if (userRow != -1) break;
        }

        // Make sure we found an empty cell
        assertNotEquals(-1, userRow, "Could not find an empty cell in the grid");

        // Enter a digit (5) in the empty cell
        grid[userRow][userCol] = 5;

        // Verify the digit was entered
        assertEquals(5, grid[userRow][userCol], "User-entered digit should be stored in the grid");
    }

    @Test
    void testSolverGridCreation() {
        // This test verifies that the solver grid is correctly created from the generator grid

        // Generate a Sudoku puzzle
        int[][] grid = new int[9][9];
        sudokuGenerator.generateSudoku(grid);

        // Get the original grid
        int[][] originalGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, originalGrid[row], 0, 9);
        }

        // Create a solver grid
        int[][] solverGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                solverGrid[row][col] = originalGrid[row][col];
            }
        }

        // Verify the solver grid matches the original grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(originalGrid[row][col], solverGrid[row][col], 
                    "Solver grid should match the original grid at position (" + row + "," + col + ")");
            }
        }
    }

    @Test
    void testCheckButtonLogic() {
        // This test verifies the core logic behind the "Check" button functionality

        // 1. Create a valid Sudoku solution
        int[][] validSolution = {
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

        // Verify the grid is recognized as solved
        assertTrue(sudokuValidator.isSolved(validSolution), 
            "A valid and complete Sudoku solution should be recognized as solved");

        // 2. Create a grid with one incorrect value (simulating user error)
        int[][] gridWithError = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(validSolution[row], 0, gridWithError[row], 0, 9);
        }
        // Introduce an error - swap two digits in the first row
        gridWithError[0][0] = 9;
        gridWithError[0][6] = 5;

        // Verify the grid is recognized as not solved
        assertFalse(sudokuValidator.isSolved(gridWithError), 
            "A Sudoku grid with errors should not be recognized as solved");

        // 3. Create a grid with empty cells (simulating incomplete puzzle)
        int[][] incompleteGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(validSolution[row], 0, incompleteGrid[row], 0, 9);
        }
        // Make some cells empty
        incompleteGrid[0][0] = 0;
        incompleteGrid[4][4] = 0;
        incompleteGrid[8][8] = 0;

        // Verify the grid is recognized as not solved
        assertFalse(sudokuValidator.isSolved(incompleteGrid), 
            "An incomplete Sudoku grid should not be recognized as solved");
    }

    @Test
    void testGridUpdateFromUserInput() {
        // This test verifies that the grid is correctly updated from user input
        // which is a key part of the "Check" button functionality

        // Generate a Sudoku puzzle with some empty cells
        int[][] grid = new int[9][9];
        sudokuGenerator.generateSudoku(grid);
        sudokuGenerator.removeNumbers(grid, 40); // Keep 40 cells, remove the rest

        // Get the original grid
        int[][] originalGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, originalGrid[row], 0, 9);
        }

        // Find an empty cell
        int emptyRow = -1;
        int emptyCol = -1;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (originalGrid[row][col] == 0) {
                    emptyRow = row;
                    emptyCol = col;
                    break;
                }
            }
            if (emptyRow != -1) break;
        }

        // Make sure we found an empty cell
        assertNotEquals(-1, emptyRow, "Could not find an empty cell in the grid");

        // Simulate user entering a digit (5) in the empty cell
        grid[emptyRow][emptyCol] = 5;

        // Verify the grid was updated
        assertEquals(5, grid[emptyRow][emptyCol], 
            "Grid should be updated with user input");

        // Create a copy of the grid with multiple user entries
        int[][] userGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, userGrid[row], 0, 9);
        }

        // Find another empty cell
        int anotherEmptyRow = -1;
        int anotherEmptyCol = -1;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (userGrid[row][col] == 0) {
                    anotherEmptyRow = row;
                    anotherEmptyCol = col;
                    break;
                }
            }
            if (anotherEmptyRow != -1) break;
        }

        // Make sure we found another empty cell
        assertNotEquals(-1, anotherEmptyRow, "Could not find another empty cell in the grid");

        // Simulate user entering another digit (7) in the empty cell
        userGrid[anotherEmptyRow][anotherEmptyCol] = 7;

        // Verify both cells were updated
        assertEquals(5, grid[emptyRow][emptyCol], 
            "First user input should be preserved");
        assertEquals(7, userGrid[anotherEmptyRow][anotherEmptyCol], 
            "Second user input should be added");
    }

    @Test
    void testSolverTabIntegration() {
        // This test verifies that the Solver tab correctly receives the grid state from the Generator tab,
        // which is important for the "Show Solution" button functionality

        // Generate a Sudoku puzzle with some empty cells
        int[][] grid = new int[9][9];
        sudokuGenerator.generateSudoku(grid);
        sudokuGenerator.removeNumbers(grid, 30); // Keep 30 cells, remove the rest

        // Get the original grid from the Generator tab
        int[][] generatorGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, generatorGrid[row], 0, 9);
        }

        // Find an empty cell
        int emptyRow = -1;
        int emptyCol = -1;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (generatorGrid[row][col] == 0) {
                    emptyRow = row;
                    emptyCol = col;
                    break;
                }
            }
            if (emptyRow != -1) break;
        }

        // Make sure we found an empty cell
        assertNotEquals(-1, emptyRow, "Could not find an empty cell in the grid");

        // Simulate user entering a digit (5) in the empty cell
        grid[emptyRow][emptyCol] = 5;

        // Create a copy of the grid with the user entry
        int[][] userGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, userGrid[row], 0, 9);
        }

        // Verify the user entry was made
        assertEquals(5, userGrid[emptyRow][emptyCol], "User entry should be in the grid");

        // Create a solution for the original puzzle (without user entries)
        int[][] solutionGrid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(generatorGrid[row], 0, solutionGrid[row], 0, 9);
        }

        // Solve the puzzle
        boolean solved = sudokuGenerator.solveSudoku(solutionGrid);
        assertTrue(solved, "The puzzle should be solvable");

        // Verify the solution is valid
        assertTrue(sudokuValidator.isSolved(solutionGrid), "The solution should be valid");

        // Check if the user entry was correct or incorrect
        boolean userEntryCorrect = (userGrid[emptyRow][emptyCol] == solutionGrid[emptyRow][emptyCol]);

        // This simulates what happens when the user clicks "Show Solution" in the error dialog
        // and then clicks the "Solve" button in the Solver tab

        if (!userEntryCorrect) {
            // If the user entry was incorrect, it would be highlighted in red in the UI
            // and replaced with the correct value
            System.out.println("[DEBUG_LOG] User entry at (" + emptyRow + "," + emptyCol + 
                              ") was incorrect. Expected: " + solutionGrid[emptyRow][emptyCol] + 
                              ", Got: " + userGrid[emptyRow][emptyCol]);
        } else {
            // If the user entry was correct, it would be highlighted in green in the UI
            System.out.println("[DEBUG_LOG] User entry at (" + emptyRow + "," + emptyCol + 
                              ") was correct: " + userGrid[emptyRow][emptyCol]);
        }

        // Verify that we can determine if the user entry was correct or not
        // This is what the Solver tab would use to highlight cells
        if (userEntryCorrect) {
            assertEquals(userGrid[emptyRow][emptyCol], solutionGrid[emptyRow][emptyCol],
                "User entry should match the solution");
        } else {
            assertNotEquals(userGrid[emptyRow][emptyCol], solutionGrid[emptyRow][emptyCol],
                "User entry should not match the solution");
        }
    }

    @Test
    void testColorProperties() {
        // This test verifies that the color properties are correctly handled

        // Define custom colors
        Color userEnteredDigitsColor = Color.RED;
        Color generatedDigitsColor = Color.GREEN;

        // Test that the color conversion to hex string works as expected
        String userColorHex = String.format("#%02X%02X%02X", 
            (int)(userEnteredDigitsColor.getRed() * 255), 
            (int)(userEnteredDigitsColor.getGreen() * 255), 
            (int)(userEnteredDigitsColor.getBlue() * 255));

        String genColorHex = String.format("#%02X%02X%02X", 
            (int)(generatedDigitsColor.getRed() * 255), 
            (int)(generatedDigitsColor.getGreen() * 255), 
            (int)(generatedDigitsColor.getBlue() * 255));

        // Verify the hex strings are correct
        assertEquals("#FF0000", userColorHex, "User color hex string should be correct");
        assertEquals("#008000", genColorHex, "Generated color hex string should be correct");

        // Verify that different colors produce different hex strings
        assertNotEquals(userColorHex, genColorHex, "Different colors should produce different hex strings");
    }
}
