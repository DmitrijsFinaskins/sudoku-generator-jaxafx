package dev.finashkin.controller;

import dev.finashkin.model.SudokuGenerator;
import dev.finashkin.model.SudokuSolver;
import dev.finashkin.model.SudokuValidator;
import javafx.scene.paint.Color;

import java.security.NoSuchAlgorithmException;

/**
 * Controller class for the Sudoku application.
 * This class coordinates between the UI and model components,
 * handling the application logic for generating, solving, and validating Sudoku puzzles.
 */
public class SudokuController {
    /** The 9x9 Sudoku grid represented as a 2D array */
    private final int[][] grid;

    /** The 9x9 Sudoku grid for the solver tab */
    private final int[][] solverGrid;

    /** Tracks which cells in the solver grid were user-entered */
    private final boolean[][] userEnteredCells;

    /** Current difficulty level (number of cells to keep) */
    private int difficultyLevel = 30;

    /** Color for user-entered digits */
    private Color userEnteredDigitsColor = Color.BLUE;

    /** Color for generated digits */
    private Color generatedDigitsColor = Color.BLACK;

    /** Generator for Sudoku puzzles */
    private final SudokuGenerator sudokuGenerator;

    /** Solver for Sudoku puzzles */
    private final SudokuSolver sudokuSolver;

    /** Validator for Sudoku puzzles */
    private final SudokuValidator sudokuValidator;

    /**
     * Constructor for the SudokuController class.
     * Initializes the Sudoku grids and components.
     *
     * @throws NoSuchAlgorithmException if a secure random number generator is not available
     */
    public SudokuController() throws NoSuchAlgorithmException {
        grid = new int[9][9];
        solverGrid = new int[9][9];
        userEnteredCells = new boolean[9][9];
        sudokuGenerator = new SudokuGenerator();
        sudokuSolver = new SudokuSolver();
        sudokuValidator = new SudokuValidator();
    }

    /**
     * Generates a new Sudoku puzzle.
     * This method creates an empty grid and populates it with a valid Sudoku solution.
     * To create a puzzle, call removeNumbers() after this method.
     */
    public void generateSudoku() {
        sudokuGenerator.generateSudoku(grid);
    }

    /**
     * Solves the given Sudoku grid using a backtracking algorithm.
     * 
     * @param gridToSolve the 9x9 grid to solve
     * @return true if the grid was successfully solved, false otherwise
     */
    public boolean solveSudoku(int[][] gridToSolve) {
        return sudokuSolver.solveSudoku(gridToSolve);
    }

    /**
     * Removes numbers from the grid to create a puzzle, keeping only a specified number of cells.
     * For normal cases, ensures that the resulting puzzle has a unique solution.
     * 
     * @param cellsToKeep the number of cells to keep in the grid
     */
    public void removeNumbers(int cellsToKeep) {
        sudokuGenerator.removeNumbers(grid, cellsToKeep);
    }

    /**
     * Checks if the Sudoku grid is solved correctly.
     * This method verifies that all cells in the grid contain valid numbers
     * according to Sudoku rules (no repeating numbers in rows, columns, or 3x3 boxes).
     *
     * @param gridToCheck the 9x9 grid to check
     * @return true if the grid is solved correctly, false otherwise
     */
    public boolean isSolved(int[][] gridToCheck) {
        return sudokuValidator.isSolved(gridToCheck);
    }

    /**
     * Creates a copy of the grid to avoid modifying the original.
     * 
     * @param gridToCopy the original grid to copy
     * @return a new copy of the grid
     */
    public int[][] copyGrid(int[][] gridToCopy) {
        return sudokuSolver.copyGrid(gridToCopy);
    }

    /**
     * Gets the current Sudoku grid.
     *
     * @return the 9x9 Sudoku grid
     */
    public int[][] getGrid() {
        return this.grid;
    }

    /**
     * Gets the solver grid.
     *
     * @return the 9x9 solver grid
     */
    public int[][] getSolverGrid() {
        return this.solverGrid;
    }

    /**
     * Gets the user-entered cells array.
     *
     * @return the 9x9 boolean array tracking user-entered cells
     */
    public boolean[][] getUserEnteredCells() {
        return this.userEnteredCells;
    }

    /**
     * Gets the current difficulty level.
     *
     * @return the difficulty level (number of cells to keep)
     */
    public int getDifficultyLevel() {
        return this.difficultyLevel;
    }

    /**
     * Sets the difficulty level.
     *
     * @param difficultyLevel the new difficulty level
     */
    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Gets the color for user-entered digits.
     *
     * @return the color for user-entered digits
     */
    public Color getUserEnteredDigitsColor() {
        return this.userEnteredDigitsColor;
    }

    /**
     * Sets the color for user-entered digits.
     *
     * @param color the new color for user-entered digits
     */
    public void setUserEnteredDigitsColor(Color color) {
        this.userEnteredDigitsColor = color;
    }

    /**
     * Gets the color for generated digits.
     *
     * @return the color for generated digits
     */
    public Color getGeneratedDigitsColor() {
        return this.generatedDigitsColor;
    }

    /**
     * Sets the color for generated digits.
     *
     * @param color the new color for generated digits
     */
    public void setGeneratedDigitsColor(Color color) {
        this.generatedDigitsColor = color;
    }
}
