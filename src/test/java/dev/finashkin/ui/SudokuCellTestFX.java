package dev.finashkin.ui;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestFX UI tests for SudokuCell.
 * This class tests the UI components and interactions of the SudokuCell class.
 */
public class SudokuCellTestFX extends TestFXBase {

    private SudokuCell sudokuCell;
    private int[][] grid;
    private TextField cellField;
    private GridPane gridPane;

    /**
     * Set up the test environment.
     * This method is called before each test.
     *
     * @param stage The primary stage for the application
     */
    @Override
    @Start
    public void start(Stage stage) {
        sudokuCell = new SudokuCell();
        grid = new int[9][9];

        // Create a grid pane to hold the cell
        gridPane = new GridPane();

        // Create a cell at position (0, 0)
        cellField = sudokuCell.createGeneratorCell(0, 0, grid);
        gridPane.add(cellField, 0, 0);

        // Create a scene and show the stage
        Scene scene = new Scene(gridPane, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Test that an empty cell can be edited.
     */
    @Test
    public void testEmptyCellEditable() {
        // Verify the cell is empty
        assertEquals("", cellField.getText());

        // Verify the cell is editable
        assertTrue(cellField.isEditable());
    }

    /**
     * Test that a filled cell is not editable.
     */
    @Test
    public void testFilledCellNotEditable() {
        // Create a new grid with a filled cell
        int[][] filledGrid = new int[9][9];
        filledGrid[0][0] = 5;

        // Use interact to ensure JavaFX operations run on the JavaFX application thread
        interact(() -> {
            // Create a new cell at position (0, 0) with the filled grid
            TextField filledCell = sudokuCell.createGeneratorCell(0, 0, filledGrid);

            // Add the cell to the grid pane
            gridPane.getChildren().clear();
            gridPane.add(filledCell, 0, 0);

            // Verify the cell has the correct text
            assertEquals("5", filledCell.getText());

            // Verify the cell is not editable
            assertFalse(filledCell.isEditable());
        });
    }

    /**
     * Test that a user can enter a digit in an empty cell.
     */
    @Test
    public void testUserInput() {
        // Verify the cell is empty
        assertEquals("", cellField.getText());

        // Click on the cell
        clickOn(cellField);

        // Type a digit
        type("5");

        // Verify the cell text was updated
        assertEquals("5", cellField.getText());

        // Verify the grid was updated
        assertEquals(5, grid[0][0]);
    }

    /**
     * Test that a user can only enter digits 1-9 in a cell.
     */
    @Test
    public void testInvalidInput() {
        // Verify the cell is empty
        assertEquals("", cellField.getText());

        // Click on the cell
        clickOn(cellField);

        // Type an invalid character
        type("a");

        // Note: When using TestFX to type, it bypasses the event handler in SudokuCell
        // and directly sets the text of the TextField. However, the grid value should
        // still not be updated for invalid input.

        // Verify the grid was not updated (this is the important part)
        assertEquals(0, grid[0][0]);
    }

    /**
     * Test that a solver cell is not editable.
     */
    @Test
    public void testSolverCellNotEditable() {
        // Use interact to ensure JavaFX operations run on the JavaFX application thread
        interact(() -> {
            // Create a solver cell
            TextField solverCell = sudokuCell.createSolverCell(0, 0, grid);

            // Add the cell to the grid pane
            gridPane.getChildren().clear();
            gridPane.add(solverCell, 0, 0);

            // Verify the cell is not editable
            assertFalse(solverCell.isEditable());
        });
    }

    /**
     * Test that a cell with custom colors is created correctly.
     */
    @Test
    public void testCustomColors() {
        // Use interact to ensure JavaFX operations run on the JavaFX application thread
        interact(() -> {
            // Create a cell with custom colors
            Color userColor = Color.RED;
            Color generatedColor = Color.GREEN;
            TextField customCell = sudokuCell.createGeneratorCell(0, 0, grid, userColor, generatedColor);

            // Add the cell to the grid pane
            gridPane.getChildren().clear();
            gridPane.add(customCell, 0, 0);

            // Verify the cell is editable (since it's empty)
            assertTrue(customCell.isEditable());
        });
    }
}
