package dev.finashkin.utils;

import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;

/**
 * Helper class for applying borders to Sudoku grid cells.
 * This class provides methods to apply different border styles to cells
 * based on their position in the grid, creating the visual 3x3 box structure
 * typical in Sudoku puzzles.
 */
public class BordersHelper {

    /**
     * Applies top borders to cells at the top of each 3x3 box.
     *
     * @param cell the TextField to apply the border to
     * @param row the row index of the cell
     */
    public void applyTopBorders(TextField cell, int row) {
        if (row % 3 == 0) {
            setTopBorder(cell);
        }
    }

    /**
     * Applies bottom borders to cells at the bottom of each 3x3 box.
     *
     * @param cell the TextField to apply the border to
     * @param row the row index of the cell
     */
    public void applyBottomBorders(TextField cell, int row) {
        if (row % 3 == 2) {
            setBottomBorder(cell);
        }
    }

    /**
     * Applies right borders to cells at the right edge of each 3x3 box.
     * Different border styles are applied based on the cell's position within the box.
     *
     * @param cell the TextField to apply the border to
     * @param col the column index of the cell
     * @param row the row index of the cell
     */
    public void applyRightBorders(TextField cell, int col, int row) {
        if (col % 3 == 2) {
            if (row % 3 == 1) {
                setRightBorder(cell);
            }
            if (row % 3 == 2) {
                setRightAndBottomBorders(cell);
            }
            if (row % 3 == 0) {
                setRightAndTopBorders(cell);
            }
        }
    }

    /**
     * Applies left borders to cells at the left edge of each 3x3 box.
     * Different border styles are applied based on the cell's position within the box.
     *
     * @param cell the TextField to apply the border to
     * @param col the column index of the cell
     * @param row the row index of the cell
     */
    public void applyLeftBorders(TextField cell, int col, int row) {
        if (col % 3 == 0) {
            if (row % 3 == 1) {
                setLeftBorder(cell);
            }
            if (row % 3 == 2) {
                setLeftAndBottomBorders(cell);
            }
            if (row % 3 == 0) {
                setTopAndLeftBorders(cell);
            }
        }
    }

    /**
     * Sets a top border on the specified cell.
     *
     * @param cell the TextField to apply the border to
     */
    void setTopBorder(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(2.0, 0, 0, 0) // Increase top border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }

    /**
     * Sets a bottom border on the specified cell.
     *
     * @param cell the TextField to apply the border to
     */
    void setBottomBorder(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(0, 0, 2.0, 0) // Increase bottom border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }

    /**
     * Sets a right border on the specified cell.
     *
     * @param cell the TextField to apply the border to
     */
    void setRightBorder(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(0, 2.0, 0, 0) // Increase right border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }

    /**
     * Sets both right and bottom borders on the specified cell.
     * Used for cells at the bottom-right corner of each 3x3 box.
     *
     * @param cell the TextField to apply the borders to
     */
    void setRightAndBottomBorders(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(0, 2.0, 2.0, 0) // Increase right and bottom border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }

    /**
     * Sets both right and top borders on the specified cell.
     * Used for cells at the top-right corner of each 3x3 box.
     *
     * @param cell the TextField to apply the borders to
     */
    void setRightAndTopBorders(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(2.0, 2.0, 0, 0) // Increase top and right border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }

    /**
     * Sets both top and left borders on the specified cell.
     * Used for cells at the top-left corner of each 3x3 box.
     *
     * @param cell the TextField to apply the borders to
     */
    void setTopAndLeftBorders(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(2.0, 0, 0, 2.0) // Increase top and left border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }

    /**
     * Sets a left border on the specified cell.
     *
     * @param cell the TextField to apply the border to
     */
    void setLeftBorder(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(0, 0, 0, 2.0) // Increase left border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }

    /**
     * Sets both left and bottom borders on the specified cell.
     * Used for cells at the bottom-left corner of each 3x3 box.
     *
     * @param cell the TextField to apply the borders to
     */
    void setLeftAndBottomBorders(TextField cell) {
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(0, 0, 2.0, 2.0) // Increase bottom and left border width
        );
        Border border = new Border(borderStroke);
        cell.setBorder(border);
    }
}
