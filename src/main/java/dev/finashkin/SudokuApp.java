package dev.finashkin;

import dev.finashkin.controller.SudokuController;
import dev.finashkin.ui.SudokuGridUI;
import dev.finashkin.utils.PdfExportService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;

import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 * Main application class for the Sudoku Generator.
 * This class extends JavaFX Application and provides functionality for
 * generating, displaying, and interacting with Sudoku puzzles.
 */
public class SudokuApp extends Application {
    /** JavaFX GridPane that displays the Sudoku grid in the UI */
    private GridPane sudokuGrid;

    /** JavaFX GridPane that displays the Sudoku grid in the Solver tab */
    private GridPane solverSudokuGrid;

    /** CSS style for text fields */
    private static final String TEXTSTYLE = "-fx-font-size: 18px;";

    /** Tab for the Sudoku generator functionality */
    private final Tab generatorTab = new Tab("Generator");

    /** Tab for the Sudoku solver functionality */
    private final Tab solverTab = new Tab("Solver");

    /** Tab for the Sudoku export functionality */
    private final Tab exporterTab = new Tab("Export");

    /** Controller for Sudoku application logic */
    private final SudokuController sudokuController;

    /** UI helper for Sudoku grids */
    private final SudokuGridUI sudokuGridUI = new SudokuGridUI();

    /** Service for exporting Sudoku puzzles to PDF */
    private final PdfExportService pdfExportService = new PdfExportService();

    /**
     * Constructor for the SudokuApp class.
     * Initializes the Sudoku controller.
     *
     * @throws NoSuchAlgorithmException if a secure random number generator is not available
     */
    public SudokuApp() throws NoSuchAlgorithmException {
        sudokuController = new SudokuController();
    }

    /**
     * Generates a new Sudoku puzzle.
     * This method creates an empty grid and populates it with a valid Sudoku solution.
     * To create a puzzle, call removeNumbers() after this method.
     */
    void generateSudoku() {
        sudokuController.generateSudoku();
    }

    /**
     * Solves the given Sudoku grid using a backtracking algorithm.
     * 
     * @param grid the 9x9 grid to solve
     * @return true if the grid was successfully solved, false otherwise
     */
    boolean solveSudoku(int[][] grid) {
        return sudokuController.solveSudoku(grid);
    }

    /**
     * Removes numbers from the grid to create a puzzle, keeping only a specified number of cells.
     * For normal cases, ensures that the resulting puzzle has a unique solution.
     * 
     * @param cellsToKeep the number of cells to keep in the grid
     */
    void removeNumbers(int cellsToKeep) {
        sudokuController.removeNumbers(cellsToKeep);
    }

    /**
     * Starts the JavaFX UI.
     * This method launches the JavaFX application.
     */
    private void startUI() {
        launch();
    }

    /**
     * Initializes the JavaFX application.
     * This method is called automatically by the JavaFX runtime.
     * It sets up the primary stage with tabs for different functionalities.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku App");
        TabPane tabPane = new TabPane();

        generatorTab.setContent(createGeneratorContent());
        generatorTab.setClosable(false); // Make the tab non-closable

        solverTab.setContent(createSolverContent());
        solverTab.setClosable(false); // Make the tab non-closable

        exporterTab.setContent(createExportContent());
        exporterTab.setClosable(false); // Make the tab non-closable

        tabPane.getTabs().addAll(generatorTab, solverTab, exporterTab);

        // Add a tab selection change listener to update the Solver and Export tabs when they're selected
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == solverTab) {
                // Update the Solver tab with the current state of the Generator tab
                solverTab.setContent(createSolverContent());
            } else if (newTab == exporterTab) {
                // Update the Export tab to refresh the preview
                exporterTab.setContent(createExportContent());
            }
        });

        Scene scene = new Scene(tabPane, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the content for the Generator tab.
     * This includes a left panel with buttons and the Sudoku grid.
     *
     * @return a GridPane containing the Generator tab content
     */
    private GridPane createGeneratorContent() {
        GridPane gridPane = new GridPane();
        VBox leftPanel = createLeftPanel();
        gridPane.add(leftPanel, 0, 0);
        sudokuGrid = createSudokuGrid();
        gridPane.add(sudokuGrid, 1, 0);
        return gridPane;
    }

    /**
     * Creates the left panel with control buttons.
     * This panel contains buttons for generating, resetting, and checking Sudoku puzzles.
     *
     * @return a VBox containing the control buttons
     */
    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(20); // 20 is the spacing between buttons
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");

        Button generateButton = new Button("Generate");
        generateButton.setFont(Font.font(16));
        Button resetButton = new Button("Reset");
        resetButton.setFont(Font.font(16));

        generateButton.setOnAction(actionEvent -> {
            generatorTab.setContent(createGeneratorContent());
            // Update the Solver tab with the new Sudoku
            solverTab.setContent(createSolverContent());
            // Update the Export tab with the new Sudoku
            exporterTab.setContent(createExportContent());
        });

        resetButton.setOnAction(event -> {
            // Clear user-entered digits only in editable cells
            for (Node node : sudokuGrid.getChildren()) {
                if (node instanceof TextField cell && cell.isEditable()) {

                    int row = GridPane.getRowIndex(cell);
                    int col = GridPane.getColumnIndex(cell);
                    sudokuController.getGrid()[row][col] = 0;
                    cell.setText("");
                    cell.setStyle(TEXTSTYLE);

                }
            }
        });

        Button checkButton = new Button("Check");
        checkButton.setFont(Font.font(16));

        checkButton.setOnAction(event -> {
            // Update the grid with current values from UI
            sudokuGridUI.updateGridFromUI(sudokuGrid, sudokuController.getGrid());

            // Check if the Sudoku is solved correctly
            if (sudokuController.isSolved(sudokuController.getGrid())) {
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText("Sudoku Solved!");
                alert.setContentText("You have successfully solved the Sudoku puzzle!");
                alert.showAndWait();
            } else {
                // Create custom buttons for the alert
                ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
                ButtonType showSolutionButton = new ButtonType("Show Solution", ButtonData.OTHER);

                // Show error message with custom buttons
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Not Solved");
                alert.setHeaderText("Sudoku Not Solved");
                alert.setContentText("The Sudoku puzzle is not solved correctly or some cells are empty.");
                alert.getButtonTypes().setAll(okButton, showSolutionButton);

                // Handle button clicks
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == showSolutionButton) {
                        // Switch to Solver tab
                        TabPane tabPane = (TabPane) solverTab.getTabPane();
                        tabPane.getSelectionModel().select(solverTab);
                    }
                });
            }
        });

        // Add difficulty selector
        VBox difficultyBox = new VBox(10);
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.setPadding(new Insets(10));
        difficultyBox.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 5px;");

        javafx.scene.control.Label difficultyLabel = new javafx.scene.control.Label("Difficulty:");
        difficultyLabel.setFont(Font.font(14));

        javafx.scene.control.RadioButton easyButton = new javafx.scene.control.RadioButton("Easy");
        javafx.scene.control.RadioButton mediumButton = new javafx.scene.control.RadioButton("Medium");
        javafx.scene.control.RadioButton hardButton = new javafx.scene.control.RadioButton("Hard");

        // Select the appropriate radio button based on the current difficulty level
        if (sudokuController.getDifficultyLevel() == 40) {
            easyButton.setSelected(true);
        } else if (sudokuController.getDifficultyLevel() == 25) {
            hardButton.setSelected(true);
        } else {
            // Default to Medium
            mediumButton.setSelected(true);
        }

        // Create toggle group
        javafx.scene.control.ToggleGroup difficultyGroup = new javafx.scene.control.ToggleGroup();
        easyButton.setToggleGroup(difficultyGroup);
        mediumButton.setToggleGroup(difficultyGroup);
        hardButton.setToggleGroup(difficultyGroup);

        // Add event handlers
        easyButton.setOnAction(event -> {
            sudokuController.setDifficultyLevel(40); // Easy - more cells visible
        });

        mediumButton.setOnAction(event -> {
            sudokuController.setDifficultyLevel(30); // Medium
        });

        hardButton.setOnAction(event -> {
            sudokuController.setDifficultyLevel(25); // Hard - fewer cells visible
        });

        difficultyBox.getChildren().addAll(difficultyLabel, easyButton, mediumButton, hardButton);

        // Add color settings
        VBox colorBox = new VBox(10);
        colorBox.setAlignment(Pos.CENTER);
        colorBox.setPadding(new Insets(10));
        colorBox.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 5px;");

        javafx.scene.control.Label colorLabel = new javafx.scene.control.Label("Color Settings:");
        colorLabel.setFont(Font.font(14));

        // User-entered digits color picker
        HBox userColorBox = new HBox(5);
        userColorBox.setAlignment(Pos.CENTER_LEFT);
        javafx.scene.control.Label userColorLabel = new javafx.scene.control.Label("User-entered digits colour:");
        ColorPicker userColorPicker = new ColorPicker(sudokuController.getUserEnteredDigitsColor());
        userColorPicker.setOnAction(event -> {
            sudokuController.setUserEnteredDigitsColor(userColorPicker.getValue());
            // Update cell colors without regenerating the grid
            updateCellColors();
        });
        userColorBox.getChildren().addAll(userColorLabel, userColorPicker);

        // Generated digits color picker
        HBox genColorBox = new HBox(5);
        genColorBox.setAlignment(Pos.CENTER_LEFT);
        javafx.scene.control.Label genColorLabel = new javafx.scene.control.Label("Generated digits colour:");
        ColorPicker genColorPicker = new ColorPicker(sudokuController.getGeneratedDigitsColor());
        genColorPicker.setOnAction(event -> {
            sudokuController.setGeneratedDigitsColor(genColorPicker.getValue());
            // Update cell colors without regenerating the grid
            updateCellColors();
        });
        genColorBox.getChildren().addAll(genColorLabel, genColorPicker);

        colorBox.getChildren().addAll(colorLabel, userColorBox, genColorBox);

        leftPanel.getChildren().addAll(generateButton, resetButton, checkButton, difficultyBox, colorBox);

        return leftPanel;
    }

    /**
     * Creates the Sudoku grid with cells.
     * This method generates a new Sudoku puzzle and creates a 9x9 grid of text fields.
     *
     * @return a GridPane containing the Sudoku grid
     */
    private GridPane createSudokuGrid() {
        generateSudoku();

        // Remove numbers to create a puzzle with a unique solution
        // Keep cells according to the current difficulty level
        removeNumbers(sudokuController.getDifficultyLevel());

        GridPane grid = sudokuGridUI.createSudokuGrid(
            sudokuController.getGrid(),
            sudokuController.getUserEnteredDigitsColor(),
            sudokuController.getGeneratedDigitsColor());

        // Add event listeners to all cells to update colors when text changes
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField cell) {
                // Add a listener for text changes
                cell.textProperty().addListener((observable, oldValue, newValue) -> {
                    // Update colors when text changes
                    updateCellColors();
                });
            }
        }

        return grid;
    }

    /**
     * Checks if the Sudoku grid is solved correctly.
     * This method verifies that all cells in the grid contain valid numbers
     * according to Sudoku rules (no repeating numbers in rows, columns, or 3x3 boxes).
     *
     * @param grid the 9x9 grid to check
     * @return true if the grid is solved correctly, false otherwise
     */
    boolean isSolved(int[][] grid) {
        return sudokuController.isSolved(grid);
    }

    /**
     * The main entry point for the application.
     * This method creates a new SudokuApp instance and starts the UI.
     *
     * @param args command line arguments (not used)
     * @throws NoSuchAlgorithmException if a secure random number generator is not available
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        SudokuApp app = new SudokuApp();
        app.startUI();
    }

    /**
     * Gets the current Sudoku grid.
     *
     * @return the 9x9 Sudoku grid
     */
    public int[][] getGrid() {
        return sudokuController.getGrid();
    }

    /**
     * Creates the content for the Solver tab.
     * This includes a left panel with buttons and the Sudoku grid.
     *
     * @return a GridPane containing the Solver tab content
     */
    private GridPane createSolverContent() {
        GridPane gridPane = new GridPane();
        VBox leftPanel = createSolverLeftPanel();
        gridPane.add(leftPanel, 0, 0);
        solverSudokuGrid = createSolverGrid();
        gridPane.add(solverSudokuGrid, 1, 0);
        return gridPane;
    }

    /**
     * Creates the left panel with control buttons for the Solver tab.
     * This panel contains a button for solving Sudoku puzzles.
     *
     * @return a VBox containing the control buttons
     */
    private VBox createSolverLeftPanel() {
        VBox leftPanel = new VBox(20); // 20 is the spacing between buttons
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");

        Button solveButton = new Button("Solve");
        solveButton.setFont(Font.font(16));

        solveButton.setOnAction(actionEvent -> {
            // Create a copy of the solver grid to avoid modifying the original
            int[][] gridCopy = sudokuController.copyGrid(sudokuController.getSolverGrid());

            // First try to solve with user entries
            boolean solved = solveSudoku(gridCopy);

            // If no solution found with user entries, ignore them and solve the original puzzle
            if (!solved) {
                System.out.println("No solution found with user entries. Ignoring incorrect entries...");

                // Create a clean copy without user entries
                int[][] cleanGrid = new int[9][9];
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        // Only copy non-user-entered cells (original puzzle cells)
                        if (!sudokuController.getUserEnteredCells()[row][col]) {
                            cleanGrid[row][col] = sudokuController.getSolverGrid()[row][col];
                        } else {
                            cleanGrid[row][col] = 0; // Clear user entries
                        }
                    }
                }

                // Solve the clean grid
                solved = solveSudoku(cleanGrid);

                if (solved) {
                    // Use the clean solution
                    gridCopy = cleanGrid;
                } else {
                    System.out.println("No solution found even without user entries!");
                    return; // Cannot proceed if the original puzzle has no solution
                }
            }

            // Update the solver grid with the solution
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    // Check if this cell was user-entered
                    if (sudokuController.getUserEnteredCells()[row][col]) {
                        // Check if the user-entered digit is correct
                        boolean isCorrect = (sudokuController.getSolverGrid()[row][col] == gridCopy[row][col]);

                        // Update the UI for user-entered cells
                        for (Node node : solverSudokuGrid.getChildren()) {
                            if (node instanceof TextField cell && 
                                GridPane.getRowIndex(cell) == row && 
                                GridPane.getColumnIndex(cell) == col) {

                                if (isCorrect) {
                                    // Correctly entered digits should be green
                                    cell.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");
                                } else {
                                    // Incorrectly entered digits should be overwritten and shown in red
                                    sudokuController.getSolverGrid()[row][col] = gridCopy[row][col];
                                    cell.setText(String.valueOf(gridCopy[row][col]));
                                    cell.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");
                                }
                                break;
                            }
                        }
                    }
                    // Update empty cells
                    else if (sudokuController.getSolverGrid()[row][col] == 0) {
                        sudokuController.getSolverGrid()[row][col] = gridCopy[row][col];

                        // Update the UI for empty cells
                        for (Node node : solverSudokuGrid.getChildren()) {
                            if (node instanceof TextField cell && 
                                GridPane.getRowIndex(cell) == row && 
                                GridPane.getColumnIndex(cell) == col) {

                                cell.setText(String.valueOf(gridCopy[row][col]));
                                // Highlight the solved numbers in red
                                cell.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");
                                break;
                            }
                        }
                    }
                }
            }
        });

        leftPanel.getChildren().add(solveButton);

        return leftPanel;
    }

    /**
     * Creates the Sudoku grid for the Solver tab.
     * This method copies the Sudoku from the Generator tab including user-entered values.
     *
     * @return a GridPane containing the Sudoku grid
     */
    private GridPane createSolverGrid() {
        return sudokuGridUI.createSolverGrid(
            sudokuController.getSolverGrid(), 
            sudokuController.getGrid(), 
            sudokuController.getUserEnteredCells(), 
            sudokuGrid);
    }

    /**
     * Updates the colors of cells in the Sudoku grid without regenerating the grid.
     * This method iterates through all cells and updates their colors based on
     * whether they are user-entered or generated.
     */
    private void updateCellColors() {
        Color userColor = sudokuController.getUserEnteredDigitsColor();
        Color genColor = sudokuController.getGeneratedDigitsColor();

        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField cell) {
                int row = GridPane.getRowIndex(cell);
                int col = GridPane.getColumnIndex(cell);

                // Skip empty cells
                if (cell.getText().isEmpty()) {
                    continue;
                }

                // Update color based on whether the cell is editable (user-entered) or not (generated)
                if (cell.isEditable()) {
                    // User-entered digit
                    String colorHex = String.format("#%02X%02X%02X", 
                        (int)(userColor.getRed() * 255), 
                        (int)(userColor.getGreen() * 255), 
                        (int)(userColor.getBlue() * 255));
                    cell.setStyle("-fx-font-size: 18px; -fx-text-fill: " + colorHex + ";");
                } else {
                    // Generated digit
                    String colorHex = String.format("#%02X%02X%02X", 
                        (int)(genColor.getRed() * 255), 
                        (int)(genColor.getGreen() * 255), 
                        (int)(genColor.getBlue() * 255));
                    cell.setStyle("-fx-font-size: 18px; -fx-text-fill: " + colorHex + ";");
                }
            }
        }
    }

    /**
     * Creates the content for the Export tab.
     * This includes options to export the current puzzle to PDF,
     * with settings for including the solution.
     *
     * @return a GridPane containing the Export tab content
     */
    private GridPane createExportContent() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(15);

        // Create left panel with export options
        VBox leftPanel = new VBox(15);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");

        // Title label
        Label titleLabel = new Label("Export Sudoku to PDF");
        titleLabel.setFont(Font.font(16));
        titleLabel.setStyle("-fx-font-weight: bold;");

        // Option to include solution
        CheckBox includeSolutionCheckBox = new CheckBox("Include solution");
        includeSolutionCheckBox.setSelected(true);

        // File name input
        HBox fileNameBox = new HBox(10);
        fileNameBox.setAlignment(Pos.CENTER_LEFT);
        Label fileNameLabel = new Label("File name:");
        TextField fileNameField = new TextField("sudoku_puzzle");
        fileNameBox.getChildren().addAll(fileNameLabel, fileNameField);

        // Export button
        Button exportButton = new Button("Export to PDF");
        exportButton.setFont(Font.font(14));

        // Status message
        Label statusLabel = new Label("");
        statusLabel.setWrapText(true);

        // Add components to left panel
        leftPanel.getChildren().addAll(
            titleLabel,
            includeSolutionCheckBox,
            fileNameBox,
            exportButton,
            statusLabel
        );

        // Add export functionality
        exportButton.setOnAction(event -> {
            // Get current puzzle from the Generator tab
            int[][] currentPuzzle = sudokuController.getGrid();

            // Check if there's a puzzle to export
            boolean hasPuzzle = false;
            for (int[] row : currentPuzzle) {
                for (int cell : row) {
                    if (cell != 0) {
                        hasPuzzle = true;
                        break;
                    }
                }
                if (hasPuzzle) break;
            }

            if (!hasPuzzle) {
                statusLabel.setText("No puzzle to export. Please generate a puzzle first.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Create a clean grid with only the original puzzle cells (non-editable cells)
            int[][] cleanGrid = new int[9][9];

            // Identify which cells are editable by checking the UI
            for (Node node : sudokuGrid.getChildren()) {
                if (node instanceof TextField cell) {
                    int row = GridPane.getRowIndex(cell);
                    int col = GridPane.getColumnIndex(cell);

                    // If the cell is not editable, it's part of the original puzzle
                    if (!cell.isEditable()) {
                        cleanGrid[row][col] = currentPuzzle[row][col];
                    }
                }
            }

            // Create a solution grid if requested
            int[][] solutionGrid = null;
            if (includeSolutionCheckBox.isSelected()) {
                // Solve the clean grid (original puzzle without user inputs)
                solutionGrid = sudokuController.copyGrid(cleanGrid);
                boolean solved = sudokuController.solveSudoku(solutionGrid);
                if (!solved) {
                    statusLabel.setText("Could not solve the original puzzle. This is unexpected and may indicate a bug.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            }

            // Set up file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName(fileNameField.getText() + ".pdf");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
            );

            // Show save dialog
            File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
            if (file != null) {
                // Export to PDF
                boolean success = pdfExportService.exportToPdf(
                    cleanGrid,
                    solutionGrid,
                    file.getAbsolutePath(),
                    includeSolutionCheckBox.isSelected(),
                    "Sudoku Puzzle",
                    sudokuController.getGeneratedDigitsColor(),
                    sudokuController.getUserEnteredDigitsColor()
                );

                if (success) {
                    statusLabel.setText("PDF exported successfully to: " + file.getAbsolutePath());
                    statusLabel.setStyle("-fx-text-fill: green;");
                } else {
                    statusLabel.setText("Failed to export PDF. Please try again.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });

        // Add a preview of the current puzzle
        GridPane previewGrid = new GridPane();
        previewGrid.setPadding(new Insets(10));
        previewGrid.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 5px;");

        Label previewLabel = new Label("Current Puzzle Preview");
        previewLabel.setFont(Font.font(14));
        previewLabel.setStyle("-fx-font-weight: bold;");

        // Add components to the main grid
        gridPane.add(leftPanel, 0, 0);
        gridPane.add(previewLabel, 1, 0);
        gridPane.add(previewGrid, 1, 1);

        // Update the preview with the current puzzle
        updatePreview(previewGrid);

        return gridPane;
    }

    /**
     * Updates the preview grid with the current puzzle.
     * This is a simplified version of the Sudoku grid for preview purposes.
     *
     * @param previewGrid the GridPane to update with the current puzzle
     */
    private void updatePreview(GridPane previewGrid) {
        previewGrid.getChildren().clear();

        int[][] currentPuzzle = sudokuController.getGrid();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = new TextField();
                cell.setPrefSize(25, 25);
                cell.setEditable(false);
                cell.setAlignment(Pos.CENTER);

                // Set cell value if not empty
                int value = currentPuzzle[row][col];
                if (value > 0) {
                    cell.setText(String.valueOf(value));
                }

                // Apply borders
                String borderStyle = "-fx-border-color: gray; -fx-border-width: 0.5;";
                if (row % 3 == 0) borderStyle += " -fx-border-width-top: 1.5;";
                if (row % 3 == 2) borderStyle += " -fx-border-width-bottom: 1.5;";
                if (col % 3 == 0) borderStyle += " -fx-border-width-left: 1.5;";
                if (col % 3 == 2) borderStyle += " -fx-border-width-right: 1.5;";

                cell.setStyle(borderStyle);

                previewGrid.add(cell, col, row);
            }
        }
    }
}
