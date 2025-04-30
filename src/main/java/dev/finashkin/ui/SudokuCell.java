package dev.finashkin.ui;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Class responsible for creating and styling Sudoku cells in the UI.
 * This class provides methods to create cells for both the Generator and Solver tabs.
 */
public class SudokuCell {
    /** CSS style for text fields */
    private static final String TEXTSTYLE = "-fx-font-size: 18px;";

    /**
     * Creates a cell for the Generator tab's Sudoku grid.
     * This method creates a text field for a specific position in the grid,
     * configures its appearance, and adds event handlers for user input.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param grid the 9x9 Sudoku grid
     * @return a TextField representing a single Sudoku cell
     */
    public TextField createGeneratorCell(int row, int col, int[][] grid) {
        return createGeneratorCell(row, col, grid, Color.BLUE, Color.BLACK);
    }

    /**
     * Creates a cell for the Generator tab's Sudoku grid with custom colors.
     * This method creates a text field for a specific position in the grid,
     * configures its appearance, and adds event handlers for user input.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param grid the 9x9 Sudoku grid
     * @param userEnteredDigitsColor the color for user-entered digits
     * @param generatedDigitsColor the color for generated digits
     * @return a TextField representing a single Sudoku cell
     */
    public TextField createGeneratorCell(int row, int col, int[][] grid, Color userEnteredDigitsColor, Color generatedDigitsColor) {
        TextField cell = new TextField();
        cell.setStyle(TEXTSTYLE);
        cell.setAlignment(Pos.CENTER);
        cell.setText((grid[row][col] == 0) ? "" : String.valueOf(grid[row][col]));
        cell.setPrefSize(40, 40);

        // Track if this is a cell that was originally empty (for user input)
        boolean isOriginallyEmpty = (grid[row][col] == 0);

        if (isOriginallyEmpty) {
            // Make the cell editable if it was originally empty
            cell.setEditable(true);

            // Add an event handler to capture the user's input
            cell.setOnKeyTyped(event -> {
                String input = event.getCharacter();
                if (input.matches("[1-9]")) {
                    // Allow only digits from 1 to 9
                    int value = Integer.parseInt(input);
                    // Update the grid with the user's input
                    grid[row][col] = value;

                    // Set the text directly (this will replace the default behavior)
                    // This will trigger the textProperty listener in SudokuApp
                    cell.setText(input);
                }
                event.consume();
            });

            // If the cell was originally empty but now has a value (user entered),
            // don't set the style here - let the updateCellColors method handle it
            // This ensures that the current color preference is used
        } else {
            // Make the cell non-editable if it's pre-filled
            cell.setEditable(false);

            // Don't set the style here - let the updateCellColors method handle it
            // This ensures that the current color preference is used for generated digits
        }

        cell.setAlignment(Pos.CENTER);
        return cell;
    }

    /**
     * Creates a cell for the Solver tab's Sudoku grid.
     * This method creates a text field for a specific position in the grid
     * and configures its appearance.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param grid the 9x9 Sudoku grid for the solver
     * @return a TextField representing a single Sudoku cell
     */
    public TextField createSolverCell(int row, int col, int[][] grid) {
        TextField cell = new TextField();
        cell.setStyle(TEXTSTYLE);
        cell.setAlignment(Pos.CENTER);
        cell.setText((grid[row][col] == 0) ? "" : String.valueOf(grid[row][col]));
        cell.setPrefSize(40, 40);

        // Make all cells non-editable in the Solver tab
        cell.setEditable(false);

        cell.setAlignment(Pos.CENTER);
        return cell;
    }
}
