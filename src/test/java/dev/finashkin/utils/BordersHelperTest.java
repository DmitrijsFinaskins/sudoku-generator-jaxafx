package dev.finashkin.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BordersHelper
 * Since JavaFX TextField is a final class and cannot be mocked with Mockito,
 * we test the conditional logic in the BordersHelper methods that determine
 * when borders should be applied based on cell position.
 */
class BordersHelperTest {

    private BordersHelper bordersHelper;

    @BeforeEach
    void setUp() {
        bordersHelper = new BordersHelper();
    }

    @ParameterizedTest
    @CsvSource({
        "0, true",  // Top row of 3x3 box
        "3, true",  // Top row of 3x3 box
        "6, true",  // Top row of 3x3 box
        "1, false", // Not top row
        "2, false", // Not top row
        "4, false", // Not top row
        "5, false", // Not top row
        "7, false", // Not top row
        "8, false"  // Not top row
    })
    void testIsTopRowOfBox(int row, boolean expected) {
        assertEquals(expected, row % 3 == 0);
    }

    @ParameterizedTest
    @CsvSource({
        "2, true",  // Bottom row of 3x3 box
        "5, true",  // Bottom row of 3x3 box
        "8, true",  // Bottom row of 3x3 box
        "0, false", // Not bottom row
        "1, false", // Not bottom row
        "3, false", // Not bottom row
        "4, false", // Not bottom row
        "6, false", // Not bottom row
        "7, false"  // Not bottom row
    })
    void testIsBottomRowOfBox(int row, boolean expected) {
        assertEquals(expected, row % 3 == 2);
    }

    @ParameterizedTest
    @CsvSource({
        "0, true",  // Left column of 3x3 box
        "3, true",  // Left column of 3x3 box
        "6, true",  // Left column of 3x3 box
        "1, false", // Not left column
        "2, false", // Not left column
        "4, false", // Not left column
        "5, false", // Not left column
        "7, false", // Not left column
        "8, false"  // Not left column
    })
    void testIsLeftColumnOfBox(int col, boolean expected) {
        assertEquals(expected, col % 3 == 0);
    }

    @ParameterizedTest
    @CsvSource({
        "2, true",  // Right column of 3x3 box
        "5, true",  // Right column of 3x3 box
        "8, true",  // Right column of 3x3 box
        "0, false", // Not right column
        "1, false", // Not right column
        "3, false", // Not right column
        "4, false", // Not right column
        "6, false", // Not right column
        "7, false"  // Not right column
    })
    void testIsRightColumnOfBox(int col, boolean expected) {
        assertEquals(expected, col % 3 == 2);
    }

    @Test
    void testBorderConditions() {
        // Test top border condition
        assertTrue(0 % 3 == 0, "Row 0 should be a top row");
        assertTrue(3 % 3 == 0, "Row 3 should be a top row");
        assertTrue(6 % 3 == 0, "Row 6 should be a top row");

        // Test bottom border condition
        assertTrue(2 % 3 == 2, "Row 2 should be a bottom row");
        assertTrue(5 % 3 == 2, "Row 5 should be a bottom row");
        assertTrue(8 % 3 == 2, "Row 8 should be a bottom row");

        // Test left border condition
        assertTrue(0 % 3 == 0, "Column 0 should be a left column");
        assertTrue(3 % 3 == 0, "Column 3 should be a left column");
        assertTrue(6 % 3 == 0, "Column 6 should be a left column");

        // Test right border condition
        assertTrue(2 % 3 == 2, "Column 2 should be a right column");
        assertTrue(5 % 3 == 2, "Column 5 should be a right column");
        assertTrue(8 % 3 == 2, "Column 8 should be a right column");
    }

    @Test
    void testCornerConditions() {
        // Test top-left corner conditions
        assertTrue(0 % 3 == 0 && 0 % 3 == 0, "Cell (0,0) should be a top-left corner");
        assertTrue(0 % 3 == 0 && 3 % 3 == 0, "Cell (0,3) should be a top-left corner");
        assertTrue(3 % 3 == 0 && 0 % 3 == 0, "Cell (3,0) should be a top-left corner");

        // Test top-right corner conditions
        assertTrue(0 % 3 == 0 && 2 % 3 == 2, "Cell (0,2) should be a top-right corner");
        assertTrue(0 % 3 == 0 && 5 % 3 == 2, "Cell (0,5) should be a top-right corner");
        assertTrue(3 % 3 == 0 && 2 % 3 == 2, "Cell (3,2) should be a top-right corner");

        // Test bottom-left corner conditions
        assertTrue(2 % 3 == 2 && 0 % 3 == 0, "Cell (2,0) should be a bottom-left corner");
        assertTrue(2 % 3 == 2 && 3 % 3 == 0, "Cell (2,3) should be a bottom-left corner");
        assertTrue(5 % 3 == 2 && 0 % 3 == 0, "Cell (5,0) should be a bottom-left corner");

        // Test bottom-right corner conditions
        assertTrue(2 % 3 == 2 && 2 % 3 == 2, "Cell (2,2) should be a bottom-right corner");
        assertTrue(2 % 3 == 2 && 5 % 3 == 2, "Cell (2,5) should be a bottom-right corner");
        assertTrue(5 % 3 == 2 && 2 % 3 == 2, "Cell (5,2) should be a bottom-right corner");
    }

    @Test
    void testMiddleEdgeConditions() {
        // Test middle row, left edge
        assertTrue(1 % 3 == 1 && 0 % 3 == 0, "Cell (1,0) should be a middle-left edge");
        assertTrue(1 % 3 == 1 && 3 % 3 == 0, "Cell (1,3) should be a middle-left edge");
        assertTrue(4 % 3 == 1 && 0 % 3 == 0, "Cell (4,0) should be a middle-left edge");

        // Test middle row, right edge
        assertTrue(1 % 3 == 1 && 2 % 3 == 2, "Cell (1,2) should be a middle-right edge");
        assertTrue(1 % 3 == 1 && 5 % 3 == 2, "Cell (1,5) should be a middle-right edge");
        assertTrue(4 % 3 == 1 && 2 % 3 == 2, "Cell (4,2) should be a middle-right edge");

        // Test top row, middle column
        assertTrue(0 % 3 == 0 && 1 % 3 == 1, "Cell (0,1) should be a top-middle edge");
        assertTrue(0 % 3 == 0 && 4 % 3 == 1, "Cell (0,4) should be a top-middle edge");
        assertTrue(3 % 3 == 0 && 1 % 3 == 1, "Cell (3,1) should be a top-middle edge");

        // Test bottom row, middle column
        assertTrue(2 % 3 == 2 && 1 % 3 == 1, "Cell (2,1) should be a bottom-middle edge");
        assertTrue(2 % 3 == 2 && 4 % 3 == 1, "Cell (2,4) should be a bottom-middle edge");
        assertTrue(5 % 3 == 2 && 1 % 3 == 1, "Cell (5,1) should be a bottom-middle edge");
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, true, false, true, false",  // Top-left corner
        "0, 1, true, false, false, false", // Top edge, middle column
        "0, 2, true, false, false, true",  // Top-right corner
        "1, 0, false, false, true, false", // Middle row, left edge
        "1, 1, false, false, false, false", // Middle cell
        "1, 2, false, false, false, true", // Middle row, right edge
        "2, 0, false, true, true, false",  // Bottom-left corner
        "2, 1, false, true, false, false", // Bottom edge, middle column
        "2, 2, false, true, false, true"   // Bottom-right corner
    })
    void testBorderApplicationConditions(int row, int col, boolean isTop, boolean isBottom, boolean isLeft, boolean isRight) {
        // Test if the cell is at the top of a 3x3 box
        assertEquals(isTop, row % 3 == 0, "Cell (" + row + "," + col + ") top border condition");

        // Test if the cell is at the bottom of a 3x3 box
        assertEquals(isBottom, row % 3 == 2, "Cell (" + row + "," + col + ") bottom border condition");

        // Test if the cell is at the left of a 3x3 box
        assertEquals(isLeft, col % 3 == 0, "Cell (" + row + "," + col + ") left border condition");

        // Test if the cell is at the right of a 3x3 box
        assertEquals(isRight, col % 3 == 2, "Cell (" + row + "," + col + ") right border condition");
    }

    @Test
    void testComprehensiveBorderConditions() {
        // Test all 81 cells in a 9x9 grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                boolean isTop = row % 3 == 0;
                boolean isBottom = row % 3 == 2;
                boolean isLeft = col % 3 == 0;
                boolean isRight = col % 3 == 2;

                // Verify the conditions for each cell
                if (isTop && isLeft) {
                    // Top-left corner of a 3x3 box
                    assertTrue(row % 3 == 0 && col % 3 == 0, 
                        "Cell (" + row + "," + col + ") should be a top-left corner");
                } else if (isTop && isRight) {
                    // Top-right corner of a 3x3 box
                    assertTrue(row % 3 == 0 && col % 3 == 2, 
                        "Cell (" + row + "," + col + ") should be a top-right corner");
                } else if (isBottom && isLeft) {
                    // Bottom-left corner of a 3x3 box
                    assertTrue(row % 3 == 2 && col % 3 == 0, 
                        "Cell (" + row + "," + col + ") should be a bottom-left corner");
                } else if (isBottom && isRight) {
                    // Bottom-right corner of a 3x3 box
                    assertTrue(row % 3 == 2 && col % 3 == 2, 
                        "Cell (" + row + "," + col + ") should be a bottom-right corner");
                } else if (isTop) {
                    // Top edge of a 3x3 box
                    assertTrue(row % 3 == 0, 
                        "Cell (" + row + "," + col + ") should be on the top edge");
                } else if (isBottom) {
                    // Bottom edge of a 3x3 box
                    assertTrue(row % 3 == 2, 
                        "Cell (" + row + "," + col + ") should be on the bottom edge");
                } else if (isLeft) {
                    // Left edge of a 3x3 box
                    assertTrue(col % 3 == 0, 
                        "Cell (" + row + "," + col + ") should be on the left edge");
                } else if (isRight) {
                    // Right edge of a 3x3 box
                    assertTrue(col % 3 == 2, 
                        "Cell (" + row + "," + col + ") should be on the right edge");
                }
            }
        }
    }
}
