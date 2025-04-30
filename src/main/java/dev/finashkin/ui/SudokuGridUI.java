package dev.finashkin.ui;

import dev.finashkin.utils.BordersHelper;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Class responsible for creating and managing Sudoku grids in the UI.
 * This class provides methods to create grids for both the Generator and Solver tabs,
 * apply borders, and update the grid based on user input.
 */
public class SudokuGridUI {
    /** Helper class for applying borders to the Sudoku grid */
    private final BordersHelper bordersHelper = new BordersHelper();

    /** Class for creating and styling Sudoku cells */
    private final SudokuCell sudokuCell = new SudokuCell();

    /**
     * Creates the Sudoku grid with cells for the Generator tab.
     *
     * @param grid the 9x9 Sudoku grid
     * @return a GridPane containing the Sudoku grid
     */
    public GridPane createSudokuGrid(int[][] grid) {
        return createSudokuGrid(grid, Color.BLUE, Color.BLACK);
    }

    /**
     * Creates the Sudoku grid with cells for the Generator tab with custom colors.
     *
     * @param grid the 9x9 Sudoku grid
     * @param userEnteredDigitsColor the color for user-entered digits
     * @param generatedDigitsColor the color for generated digits
     * @return a GridPane containing the Sudoku grid
     */
    public GridPane createSudokuGrid(int[][] grid, Color userEnteredDigitsColor, Color generatedDigitsColor) {
        GridPane sudokuGrid = new GridPane();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = sudokuCell.createGeneratorCell(row, col, grid, userEnteredDigitsColor, generatedDigitsColor);
                sudokuGrid.add(cell, col, row);
            }
        }

        applyBorders(sudokuGrid);
        return sudokuGrid;
    }

    /**
     * Creates the Sudoku grid for the Solver tab.
     * This method copies the Sudoku from the Generator tab including user-entered values.
     *
     * @param solverGrid the 9x9 Sudoku grid for the solver
     * @param grid the original 9x9 Sudoku grid from the Generator tab
     * @param userEnteredCells array tracking which cells were user-entered
     * @param sudokuGrid the GridPane from the Generator tab
     * @return a GridPane containing the Sudoku grid for the Solver tab
     */
    public GridPane createSolverGrid(int[][] solverGrid, int[][] grid, boolean[][] userEnteredCells, GridPane sudokuGrid) {
        GridPane solverSudokuGrid = new GridPane();

        // Reset the userEnteredCells array
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                userEnteredCells[row][col] = false;
            }
        }

        // Copy the grid from the Generator tab
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                solverGrid[row][col] = grid[row][col];

                // Check if this cell was user-entered in the Generator tab
                // User-entered cells are those that were initially empty (editable) but now have a value
                if (sudokuGrid != null) {
                    for (Node node : sudokuGrid.getChildren()) {
                        if (node instanceof TextField cell && 
                            GridPane.getRowIndex(cell) == row && 
                            GridPane.getColumnIndex(cell) == col) {

                            if (cell.isEditable() && !cell.getText().isEmpty()) {
                                userEnteredCells[row][col] = true;
                            }
                            break;
                        }
                    }
                }

                TextField cell = sudokuCell.createSolverCell(row, col, solverGrid);
                solverSudokuGrid.add(cell, col, row);
            }
        }

        applyBorders(solverSudokuGrid);
        return solverSudokuGrid;
    }

    /**
     * Updates the grid with current values from UI.
     *
     * @param sudokuGrid the GridPane containing the Sudoku grid
     * @param grid the 9x9 Sudoku grid to update
     */
    public void updateGridFromUI(GridPane sudokuGrid, int[][] grid) {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField cell) {
                int row = GridPane.getRowIndex(cell);
                int col = GridPane.getColumnIndex(cell);
                String text = cell.getText().trim();

                if (!text.isEmpty()) {
                    try {
                        int value = Integer.parseInt(text);
                        if (value >= 1 && value <= 9) {
                            grid[row][col] = value;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore non-numeric input
                    }
                }
            }
        }
    }

    /**
     * Applies borders to the Sudoku grid cells.
     * This method applies different border styles to cells based on their position
     * to create the visual 3x3 box structure typical in Sudoku puzzles.
     *
     * @param gridPane the GridPane containing the Sudoku cells
     */
    private void applyBorders(GridPane gridPane) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = (TextField) gridPane.getChildren().get(row * 9 + col);
                bordersHelper.applyTopBorders(cell, row);
                bordersHelper.applyBottomBorders(cell, row);
                bordersHelper.applyRightBorders(cell, col, row);
                bordersHelper.applyLeftBorders(cell, col, row);
            }
        }
    }
}
