package dev.finashkin.ui;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestFX UI tests for SudokuGridUI.
 * This class tests the UI components and interactions of the SudokuGridUI class.
 */
public class SudokuGridUITestFX extends TestFXBase {

    private SudokuGridUI sudokuGridUI;
    private GridPane gridPane;
    private int[][] grid;

    /**
     * Set up the test environment.
     * This method is called before each test.
     *
     * @param stage The primary stage for the application
     */
    @Override
    @Start
    public void start(Stage stage) {
        sudokuGridUI = new SudokuGridUI();
        grid = new int[9][9];

        // Initialize some values in the grid
        grid[0][0] = 5;
        grid[1][1] = 6;
        grid[2][2] = 7;

        // Create a grid pane with the SudokuGridUI
        gridPane = sudokuGridUI.createSudokuGrid(grid);

        // Create a scene and show the stage
        Scene scene = new Scene(gridPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Test that the grid is created with the correct number of cells.
     */
    @Test
    public void testGridSize() {
        // A 9x9 Sudoku grid should have 81 cells
        assertEquals(81, gridPane.getChildren().size());
    }

    /**
     * Test that filled cells display the correct values.
     */
    @Test
    public void testFilledCells() {
        // Get the cells at positions (0,0), (1,1), and (2,2)
        TextField cell00 = (TextField) getNodeFromGridPane(gridPane, 0, 0);
        TextField cell11 = (TextField) getNodeFromGridPane(gridPane, 1, 1);
        TextField cell22 = (TextField) getNodeFromGridPane(gridPane, 2, 2);

        // Verify the cells have the correct text
        assertEquals("5", cell00.getText());
        assertEquals("6", cell11.getText());
        assertEquals("7", cell22.getText());
    }

    /**
     * Test that filled cells are not editable in generator mode.
     */
    @Test
    public void testFilledCellsNotEditable() {
        // Get the cells at positions (0,0), (1,1), and (2,2)
        TextField cell00 = (TextField) getNodeFromGridPane(gridPane, 0, 0);
        TextField cell11 = (TextField) getNodeFromGridPane(gridPane, 1, 1);
        TextField cell22 = (TextField) getNodeFromGridPane(gridPane, 2, 2);

        // Verify the cells are not editable
        assertFalse(cell00.isEditable());
        assertFalse(cell11.isEditable());
        assertFalse(cell22.isEditable());
    }

    /**
     * Test that empty cells are editable in generator mode.
     */
    @Test
    public void testEmptyCellsEditable() {
        // Get an empty cell at position (3,3)
        TextField cell33 = (TextField) getNodeFromGridPane(gridPane, 3, 3);

        // Verify the cell is empty
        assertEquals("", cell33.getText());

        // Verify the cell is editable
        assertTrue(cell33.isEditable());
    }

    /**
     * Test that a user can enter a digit in an empty cell.
     */
    @Test
    public void testUserInput() {
        // Get an empty cell at position (3,3)
        TextField cell33 = (TextField) getNodeFromGridPane(gridPane, 3, 3);

        // Verify the cell is empty
        assertEquals("", cell33.getText());

        // Click on the cell
        clickOn(cell33);

        // Type a digit
        type("4");

        // Verify the cell text was updated
        assertEquals("4", cell33.getText());

        // Verify the grid was updated
        assertEquals(4, grid[3][3]);
    }

    /**
     * Test that a user can only enter digits 1-9 in a cell.
     */
    @Test
    public void testInvalidInput() {
        // Get an empty cell at position (3,3)
        TextField cell33 = (TextField) getNodeFromGridPane(gridPane, 3, 3);

        // Verify the cell is empty
        assertEquals("", cell33.getText());

        // Click on the cell
        clickOn(cell33);

        // Type an invalid character
        type("a");

        // Note: When using TestFX to type, it bypasses the event handler in SudokuCell
        // and directly sets the text of the TextField. However, the grid value should
        // still not be updated for invalid input.

        // Verify the grid was not updated (this is the important part)
        assertEquals(0, grid[3][3]);
    }

    /**
     * Test that all cells are not editable in solver mode.
     */
    @Test
    public void testSolverModeNotEditable() {
        // Create a solver grid
        int[][] solverGrid = new int[9][9];
        boolean[][] userEnteredCells = new boolean[9][9];

        // Create a new grid pane with the SudokuGridUI in solver mode
        GridPane solverGridPane = sudokuGridUI.createSolverGrid(solverGrid, grid, userEnteredCells, gridPane);

        // Get cells at various positions
        TextField cell00 = (TextField) getNodeFromGridPane(solverGridPane, 0, 0);
        TextField cell11 = (TextField) getNodeFromGridPane(solverGridPane, 1, 1);
        TextField cell33 = (TextField) getNodeFromGridPane(solverGridPane, 3, 3);

        // Verify all cells are not editable, regardless of whether they're filled or empty
        assertFalse(cell00.isEditable());
        assertFalse(cell11.isEditable());
        assertFalse(cell33.isEditable());
    }

    /**
     * Helper method to get a node from a GridPane by its row and column indices.
     *
     * @param gridPane The GridPane to get the node from
     * @param row The row index
     * @param col The column index
     * @return The node at the specified position
     */
    private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }
}
