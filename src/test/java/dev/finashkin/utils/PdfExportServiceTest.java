package dev.finashkin.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the PdfExportService.
 * This class tests the functionality of exporting Sudoku puzzles to PDF format.
 */
class PdfExportServiceTest {

    private PdfExportService pdfExportService;

    @TempDir
    Path tempDir;

    private Color originalColor = Color.BLACK;
    private Color userEnteredColor = Color.BLUE;

    @BeforeEach
    void setUp() {
        pdfExportService = new PdfExportService();
    }

    /**
     * Test exporting a valid Sudoku puzzle without a solution.
     */
    @Test
    void testExportToPdf_ValidPuzzleNoSolution() {
        // Create a valid Sudoku puzzle
        int[][] grid = createValidSudokuGrid();

        // Create a file path in the temporary directory
        String filePath = tempDir.resolve("sudoku_no_solution.pdf").toString();

        // Export the puzzle without a solution
        boolean result = pdfExportService.exportToPdf(grid, null, filePath, false, "Sudoku Puzzle Test", originalColor, userEnteredColor);

        // Verify the export was successful
        assertTrue(result);

        // Verify the file was created
        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    /**
     * Test exporting a valid Sudoku puzzle with a solution.
     */
    @Test
    void testExportToPdf_ValidPuzzleWithSolution() {
        // Create a valid Sudoku puzzle
        int[][] grid = createValidSudokuGrid();

        // Create a solution grid (for testing, we'll use the same grid)
        int[][] solutionGrid = createValidSudokuGrid();

        // Create a file path in the temporary directory
        String filePath = tempDir.resolve("sudoku_with_solution.pdf").toString();

        // Export the puzzle with a solution
        boolean result = pdfExportService.exportToPdf(grid, solutionGrid, filePath, true, "Sudoku Puzzle with Solution", originalColor, userEnteredColor);

        // Verify the export was successful
        assertTrue(result);

        // Verify the file was created
        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    /**
     * Test exporting an empty Sudoku grid.
     */
    @Test
    void testExportToPdf_EmptyGrid() {
        // Create an empty grid
        int[][] emptyGrid = new int[9][9];

        // Create a file path in the temporary directory
        String filePath = tempDir.resolve("empty_sudoku.pdf").toString();

        // Export the empty puzzle
        boolean result = pdfExportService.exportToPdf(emptyGrid, null, filePath, false, "Empty Sudoku Puzzle", originalColor, userEnteredColor);

        // Verify the export was successful
        assertTrue(result);

        // Verify the file was created
        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    /**
     * Test exporting with a null grid.
     */
    @Test
    void testExportToPdf_NullGrid() {
        // Create a file path in the temporary directory
        String filePath = tempDir.resolve("null_grid.pdf").toString();

        // Export with a null grid
        boolean result = pdfExportService.exportToPdf(null, null, filePath, false, "Null Grid Test", originalColor, userEnteredColor);

        // Verify the export failed
        assertFalse(result);

        // Verify no file was created
        File pdfFile = new File(filePath);
        assertFalse(pdfFile.exists());
    }

    /**
     * Test exporting with a null file path.
     */
    @Test
    void testExportToPdf_NullFilePath() {
        // Create a valid Sudoku puzzle
        int[][] grid = createValidSudokuGrid();

        // Export with a null file path
        boolean result = pdfExportService.exportToPdf(grid, null, null, false, "Null File Path Test", originalColor, userEnteredColor);

        // Verify the export failed
        assertFalse(result);
    }

    /**
     * Test exporting with an invalid file path.
     */
    @Test
    void testExportToPdf_InvalidFilePath() {
        // Create a valid Sudoku puzzle
        int[][] grid = createValidSudokuGrid();

        // Export with an invalid file path
        boolean result = pdfExportService.exportToPdf(grid, null, "/invalid/path/that/does/not/exist/file.pdf", false, "Invalid Path Test", originalColor, userEnteredColor);

        // Verify the export failed
        assertFalse(result);
    }

    /**
     * Test exporting with a null title.
     */
    @Test
    void testExportToPdf_NullTitle() {
        // Create a valid Sudoku puzzle
        int[][] grid = createValidSudokuGrid();

        // Create a file path in the temporary directory
        String filePath = tempDir.resolve("null_title.pdf").toString();

        // Export with a null title
        boolean result = pdfExportService.exportToPdf(grid, null, filePath, false, null, originalColor, userEnteredColor);

        // Verify the export failed
        assertFalse(result);

        // Verify no file was created
        File pdfFile = new File(filePath);
        assertFalse(pdfFile.exists());
    }

    /**
     * Test exporting with solution requested but null solution grid.
     */
    @Test
    void testExportToPdf_NullSolutionGrid() {
        // Create a valid Sudoku puzzle
        int[][] grid = createValidSudokuGrid();

        // Create a file path in the temporary directory
        String filePath = tempDir.resolve("null_solution.pdf").toString();

        // Export with solution requested but null solution grid
        boolean result = pdfExportService.exportToPdf(grid, null, filePath, true, "Null Solution Test", originalColor, userEnteredColor);

        // Verify the export was successful (should still create a PDF without the solution)
        assertTrue(result);

        // Verify the file was created
        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    /**
     * Helper method to create a valid Sudoku grid for testing.
     * 
     * @return a 9x9 Sudoku grid with valid values
     */
    private int[][] createValidSudokuGrid() {
        return new int[][] {
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
    }

    /**
     * Helper method to create a partially filled Sudoku grid for testing.
     * 
     * @return a 9x9 Sudoku grid with some empty cells (0 values)
     */
    private int[][] createPartialSudokuGrid() {
        return new int[][] {
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
    }
}
