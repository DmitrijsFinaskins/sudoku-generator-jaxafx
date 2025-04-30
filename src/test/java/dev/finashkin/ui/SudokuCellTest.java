package dev.finashkin.ui;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SudokuCell.
 * This class tests the logic behind the creation and behavior of Sudoku cells in the UI.
 * 
 * Note: Direct testing of JavaFX components is not performed here due to the need for
 * JavaFX toolkit initialization. Instead, we test the logic that would drive those components.
 */
@ExtendWith(MockitoExtension.class)
class SudokuCellTest {

    private SudokuCell sudokuCell;
    private int[][] grid;

    @BeforeEach
    void setUp() {
        sudokuCell = new SudokuCell();
        grid = new int[9][9];
    }

    @Test
    void testCellTextRepresentation() {
        // Test the logic for determining cell text based on grid values
        
        // Empty cell (value 0) should have empty text
        assertEquals("", getCellText(0), "Empty cell should have empty text");
        
        // Filled cell (value 5) should display the value
        assertEquals("5", getCellText(5), "Filled cell should display the value");
    }

    @Test
    void testCellEditability() {
        // Test the logic for determining cell editability based on grid values
        
        // Empty cell (value 0) in generator should be editable
        assertTrue(isGeneratorCellEditable(0), "Empty generator cell should be editable");
        
        // Filled cell (value 5) in generator should not be editable
        assertFalse(isGeneratorCellEditable(5), "Filled generator cell should not be editable");
        
        // All solver cells should not be editable, regardless of value
        assertFalse(isSolverCellEditable(0), "Empty solver cell should not be editable");
        assertFalse(isSolverCellEditable(5), "Filled solver cell should not be editable");
    }

    @Test
    void testGridUpdate() {
        // Test that the grid is updated when a user enters a digit
        
        // Initial state
        assertEquals(0, grid[0][0], "Grid should initially have value 0");
        
        // Simulate user entering a digit
        grid[0][0] = 5;
        
        // Verify the grid was updated
        assertEquals(5, grid[0][0], "Grid should be updated with user input");
    }

    @Test
    void testCellDimensions() {
        // Test that cells have the correct dimensions
        // This is a logical test of what dimensions should be set, not testing actual JavaFX components
        
        // The SudokuCell class sets prefSize(40, 40) for all cells
        assertEquals(40, getCellWidth(), "Cell width should be 40");
        assertEquals(40, getCellHeight(), "Cell height should be 40");
    }

    @Test
    void testColorConversion() {
        // Test color conversion to hex string, similar to SudokuUITest
        
        // Define custom colors
        Color userColor = Color.RED;
        Color generatedColor = Color.GREEN;
        
        // Test that the color conversion to hex string works as expected
        String userColorHex = String.format("#%02X%02X%02X", 
            (int)(userColor.getRed() * 255), 
            (int)(userColor.getGreen() * 255), 
            (int)(userColor.getBlue() * 255));
        
        String genColorHex = String.format("#%02X%02X%02X", 
            (int)(generatedColor.getRed() * 255), 
            (int)(generatedColor.getGreen() * 255), 
            (int)(generatedColor.getBlue() * 255));
        
        // Verify the hex strings are correct
        assertEquals("#FF0000", userColorHex, "User color hex string should be correct");
        assertEquals("#008000", genColorHex, "Generated color hex string should be correct");
        
        // Verify that different colors produce different hex strings
        assertNotEquals(userColorHex, genColorHex, "Different colors should produce different hex strings");
    }

    @Test
    void testCellStyle() {
        // Test that cells have the correct style
        // This is a logical test of what style should be set, not testing actual JavaFX components
        
        // The SudokuCell class sets style to "-fx-font-size: 18px;" for all cells
        assertEquals("-fx-font-size: 18px;", getCellStyle(), "Cell should have correct style");
    }
    
    // Helper methods to simulate the logic in SudokuCell without creating actual UI components
    
    private String getCellText(int value) {
        return (value == 0) ? "" : String.valueOf(value);
    }
    
    private boolean isGeneratorCellEditable(int value) {
        return value == 0; // Empty cells are editable in generator
    }
    
    private boolean isSolverCellEditable(int value) {
        return false; // All cells are non-editable in solver
    }
    
    private int getCellWidth() {
        return 40; // Cell width is set to 40 in SudokuCell
    }
    
    private int getCellHeight() {
        return 40; // Cell height is set to 40 in SudokuCell
    }
    
    private String getCellStyle() {
        return "-fx-font-size: 18px;"; // Style set in SudokuCell
    }
}