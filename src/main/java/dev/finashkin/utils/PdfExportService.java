package dev.finashkin.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import javafx.scene.paint.Color;
import java.io.IOException;

/**
 * Service class for exporting Sudoku puzzles to PDF format.
 * This class provides methods to generate PDF files containing Sudoku puzzles,
 * with options to include solutions.
 */
public class PdfExportService {

    /** Default font for Sudoku cells */
    private static final PDFont CELL_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    /** Font for generated digits (pre-filled cells) */
    private static final PDFont GENERATED_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

    /** Font for title and headers */
    private static final PDFont TITLE_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

    /** Font for section headers */
    private static final PDFont HEADER_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

    /** Cell size in points */
    private static final float CELL_SIZE = 30f;

    /** Border width for cells */
    private static final float BORDER_WIDTH = 0.5f;

    /** Thicker border width for 3x3 box borders */
    private static final float THICK_BORDER_WIDTH = 1.5f;

    /** Font size for cell values */
    private static final float CELL_FONT_SIZE = 12f;

    /** Font size for title */
    private static final float TITLE_FONT_SIZE = 16f;

    /** Font size for headers */
    private static final float HEADER_FONT_SIZE = 14f;

    /** Font size for copyright notice */
    private static final float COPYRIGHT_FONT_SIZE = 8f;

    /** Copyright notice text */
    private static final String COPYRIGHT_NOTICE = "© Dmitrijs Finaskins dmitry@finashkin.dev";

    /**
     * Exports a Sudoku puzzle to a PDF file.
     * 
     * @param grid the 9x9 Sudoku grid to export
     * @param solutionGrid the 9x9 solution grid (can be null if includeSolution is false)
     * @param filePath the path where the PDF file will be saved
     * @param includeSolution whether to include the solution in the PDF
     * @param title the title for the PDF document
     * @param originalDigitsColor the color for originally placed digits
     * @param generatedDigitsColor the color for generated digits
     * @return true if the export was successful, false otherwise
     */
    /** Default color for solution digits */
    private static final Color SOLUTION_COLOR = Color.GREEN;

    public boolean exportToPdf(int[][] grid, int[][] solutionGrid, String filePath, 
                              boolean includeSolution, String title,
                              Color originalDigitsColor, Color generatedDigitsColor) {
        // Validate input parameters
        if (grid == null || filePath == null || title == null) {
            return false;
        }

        // Use default colors if not provided
        Color origColor = (originalDigitsColor != null) ? originalDigitsColor : Color.BLACK;
        Color genColor = (generatedDigitsColor != null) ? generatedDigitsColor : Color.BLACK;

        try (PDDocument document = new PDDocument()) {
            // Create a page for the puzzle
            PDPage puzzlePage = new PDPage(PDRectangle.A4);
            document.addPage(puzzlePage);

            // Get page dimensions
            float pageWidth = puzzlePage.getMediaBox().getWidth();
            float pageHeight = puzzlePage.getMediaBox().getHeight();

            // Create content stream for the puzzle page
            try (PDPageContentStream puzzleContentStream = new PDPageContentStream(document, puzzlePage)) {
                // Add title
                puzzleContentStream.beginText();
                puzzleContentStream.setFont(TITLE_FONT, TITLE_FONT_SIZE);
                float titleWidth = TITLE_FONT.getStringWidth(title) / 1000 * TITLE_FONT_SIZE;
                puzzleContentStream.newLineAtOffset((pageWidth - titleWidth) / 2, pageHeight - 50);
                puzzleContentStream.showText(title);
                puzzleContentStream.endText();

                // Add puzzle header
                puzzleContentStream.beginText();
                puzzleContentStream.setFont(HEADER_FONT, HEADER_FONT_SIZE);
                puzzleContentStream.newLineAtOffset(50, pageHeight - 80);
                puzzleContentStream.showText("Puzzle");
                puzzleContentStream.endText();

                // Draw the puzzle grid
                float startY = pageHeight - 100;
                drawSudokuGrid(puzzleContentStream, grid, pageWidth, startY, false, origColor, genColor);

                // Add copyright notice
                addCopyrightNotice(puzzleContentStream, pageWidth, pageHeight);
            }

            // Add solution on a separate page if requested
            if (includeSolution && solutionGrid != null) {
                // Create a new page for the solution
                PDPage solutionPage = new PDPage(PDRectangle.A4);
                document.addPage(solutionPage);

                // Create content stream for the solution page
                try (PDPageContentStream solutionContentStream = new PDPageContentStream(document, solutionPage)) {
                    // Add title for solution page
                    solutionContentStream.beginText();
                    solutionContentStream.setFont(TITLE_FONT, TITLE_FONT_SIZE);
                    float titleWidth = TITLE_FONT.getStringWidth(title + " - Solution") / 1000 * TITLE_FONT_SIZE;
                    solutionContentStream.newLineAtOffset((pageWidth - titleWidth) / 2, pageHeight - 50);
                    solutionContentStream.showText(title + " - Solution");
                    solutionContentStream.endText();

                    // Add solution header
                    solutionContentStream.beginText();
                    solutionContentStream.setFont(HEADER_FONT, HEADER_FONT_SIZE);
                    solutionContentStream.newLineAtOffset(50, pageHeight - 80);
                    solutionContentStream.showText("Solution");
                    solutionContentStream.endText();

                    // Draw the solution grid
                    float startY = pageHeight - 100;
                    drawSudokuGrid(solutionContentStream, solutionGrid, pageWidth, startY, true, origColor, genColor);

                    // Add a legend
                    drawLegend(solutionContentStream, pageWidth, startY - (9 * CELL_SIZE) - 30, origColor);

                    // Add copyright notice
                    addCopyrightNotice(solutionContentStream, pageWidth, pageHeight);
                }
            }

            document.save(filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Store the original grid for comparison when drawing the solution
    private int[][] originalGrid;

    /**
     * Draws a Sudoku grid on the PDF document.
     * 
     * @param contentStream the PDF content stream
     * @param grid the 9x9 Sudoku grid to draw
     * @param pageWidth the width of the page
     * @param startY the starting Y position for the grid
     * @param isSolution whether this grid is a solution (affects styling)
     * @param originalDigitsColor the color for originally placed digits
     * @param generatedDigitsColor the color for generated digits
     * @throws IOException if there's an error adding content to the document
     */
    private void drawSudokuGrid(PDPageContentStream contentStream, int[][] grid, float pageWidth, float startY, 
                               boolean isSolution, Color originalDigitsColor, Color generatedDigitsColor) 
            throws IOException {
        // Calculate starting position (centered on page)
        float startX = (pageWidth - (9 * CELL_SIZE)) / 2;

        // If this is the first grid (puzzle), store it for comparison
        if (!isSolution) {
            originalGrid = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    originalGrid[i][j] = grid[i][j];
                }
            }
        }

        // Draw cells and borders
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                float x = startX + (col * CELL_SIZE);
                float y = startY - (row * CELL_SIZE);

                // Draw cell borders
                drawCellBorders(contentStream, x, y, row, col);

                // Draw cell content if not empty
                int value = grid[row][col];
                if (value > 0) {
                    // Use appropriate font based on whether this is a solution
                    PDFont font = isSolution ? CELL_FONT : GENERATED_FONT;

                    // Set color based on whether this is a solution page and whether the digit is original
                    if (isSolution) {
                        // Check if this digit was in the original grid
                        boolean isOriginalDigit = (originalGrid != null && originalGrid[row][col] > 0);

                        if (isOriginalDigit) {
                            // This is an original digit
                            float[] rgb = new float[] {
                                (float) originalDigitsColor.getRed(),
                                (float) originalDigitsColor.getGreen(),
                                (float) originalDigitsColor.getBlue()
                            };
                            contentStream.setNonStrokingColor(rgb[0], rgb[1], rgb[2]);
                        } else {
                            // This is a solution digit
                            float[] rgb = new float[] {
                                (float) SOLUTION_COLOR.getRed(),
                                (float) SOLUTION_COLOR.getGreen(),
                                (float) SOLUTION_COLOR.getBlue()
                            };
                            contentStream.setNonStrokingColor(rgb[0], rgb[1], rgb[2]);
                        }
                    } else {
                        // On puzzle page, all digits use originalDigitsColor
                        float[] rgb = new float[] {
                            (float) originalDigitsColor.getRed(),
                            (float) originalDigitsColor.getGreen(),
                            (float) originalDigitsColor.getBlue()
                        };
                        contentStream.setNonStrokingColor(rgb[0], rgb[1], rgb[2]);
                    }

                    // Draw the cell value
                    String valueStr = String.valueOf(value);
                    float textWidth = font.getStringWidth(valueStr) / 1000 * CELL_FONT_SIZE;
                    float textX = x + (CELL_SIZE - textWidth) / 2;
                    float textY = y - CELL_SIZE + (CELL_SIZE - CELL_FONT_SIZE) / 2;

                    contentStream.beginText();
                    contentStream.setFont(font, CELL_FONT_SIZE);
                    contentStream.newLineAtOffset(textX, textY);
                    contentStream.showText(valueStr);
                    contentStream.endText();

                    // Reset color to black after drawing
                    contentStream.setNonStrokingColor(0, 0, 0);
                }
            }
        }
    }

    /**
     * Draws a legend explaining the colors used in the solution grid.
     * 
     * @param contentStream the PDF content stream
     * @param pageWidth the width of the page
     * @param startY the starting Y position for the legend
     * @param originalDigitsColor the color for originally placed digits
     * @throws IOException if there's an error adding content to the document
     */
    private void drawLegend(PDPageContentStream contentStream, float pageWidth, float startY, Color originalDigitsColor) 
            throws IOException {
        // Set up legend position
        float startX = 50;
        float lineHeight = 20;

        // Add legend title
        contentStream.beginText();
        contentStream.setFont(HEADER_FONT, 12);
        contentStream.newLineAtOffset(startX, startY);
        contentStream.showText("Legend:");
        contentStream.endText();

        // Add original digits legend item
        float[] origRgb = new float[] {
            (float) originalDigitsColor.getRed(),
            (float) originalDigitsColor.getGreen(),
            (float) originalDigitsColor.getBlue()
        };

        // Draw color box for original digits
        contentStream.setNonStrokingColor(origRgb[0], origRgb[1], origRgb[2]);
        contentStream.addRect(startX, startY - lineHeight, 15, 15);
        contentStream.fill();

        // Add text for original digits
        contentStream.beginText();
        contentStream.setNonStrokingColor(0, 0, 0); // Reset to black
        contentStream.setFont(CELL_FONT, 10);
        contentStream.newLineAtOffset(startX + 20, startY - lineHeight + 3);
        contentStream.showText("Originally placed digits");
        contentStream.endText();

        // Add solution digits legend item
        float[] solRgb = new float[] {
            (float) SOLUTION_COLOR.getRed(),
            (float) SOLUTION_COLOR.getGreen(),
            (float) SOLUTION_COLOR.getBlue()
        };

        // Draw color box for solution digits
        contentStream.setNonStrokingColor(solRgb[0], solRgb[1], solRgb[2]);
        contentStream.addRect(startX, startY - (2 * lineHeight), 15, 15);
        contentStream.fill();

        // Add text for solution digits
        contentStream.beginText();
        contentStream.setNonStrokingColor(0, 0, 0); // Reset to black
        contentStream.setFont(CELL_FONT, 10);
        contentStream.newLineAtOffset(startX + 20, startY - (2 * lineHeight) + 3);
        contentStream.showText("Solution digits");
        contentStream.endText();
    }

    /**
     * Adds copyright notice to the top and bottom right of a page.
     *
     * @param contentStream the PDF content stream
     * @param pageWidth the width of the page
     * @param pageHeight the height of the page
     * @throws IOException if there's an error adding content to the document
     */
    private void addCopyrightNotice(PDPageContentStream contentStream, float pageWidth, float pageHeight) 
            throws IOException {
        // Calculate the width of the copyright text
        float copyrightWidth = CELL_FONT.getStringWidth(COPYRIGHT_NOTICE) / 1000 * COPYRIGHT_FONT_SIZE;

        // Add copyright notice at the top right
        contentStream.beginText();
        contentStream.setFont(CELL_FONT, COPYRIGHT_FONT_SIZE);
        contentStream.newLineAtOffset(pageWidth - copyrightWidth - 20, pageHeight - 20);
        contentStream.showText(COPYRIGHT_NOTICE);
        contentStream.endText();

        // Add copyright notice at the bottom right
        contentStream.beginText();
        contentStream.setFont(CELL_FONT, COPYRIGHT_FONT_SIZE);
        contentStream.newLineAtOffset(pageWidth - copyrightWidth - 20, 20);
        contentStream.showText(COPYRIGHT_NOTICE);
        contentStream.endText();
    }

    /**
     * Draws the borders for a Sudoku cell, with thicker borders for 3x3 box boundaries.
     * 
     * @param contentStream the PDF content stream
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @param row the row index (0-8)
     * @param col the column index (0-8)
     * @throws IOException if there's an error adding content to the document
     */
    private void drawCellBorders(PDPageContentStream contentStream, float x, float y, int row, int col) throws IOException {
        // Draw top border
        float topBorderWidth = (row % 3 == 0) ? THICK_BORDER_WIDTH : BORDER_WIDTH;
        contentStream.setLineWidth(topBorderWidth);
        contentStream.moveTo(x, y);
        contentStream.lineTo(x + CELL_SIZE, y);
        contentStream.stroke();

        // Draw bottom border
        float bottomBorderWidth = (row % 3 == 2) ? THICK_BORDER_WIDTH : BORDER_WIDTH;
        contentStream.setLineWidth(bottomBorderWidth);
        contentStream.moveTo(x, y - CELL_SIZE);
        contentStream.lineTo(x + CELL_SIZE, y - CELL_SIZE);
        contentStream.stroke();

        // Draw left border
        float leftBorderWidth = (col % 3 == 0) ? THICK_BORDER_WIDTH : BORDER_WIDTH;
        contentStream.setLineWidth(leftBorderWidth);
        contentStream.moveTo(x, y);
        contentStream.lineTo(x, y - CELL_SIZE);
        contentStream.stroke();

        // Draw right border
        float rightBorderWidth = (col % 3 == 2) ? THICK_BORDER_WIDTH : BORDER_WIDTH;
        contentStream.setLineWidth(rightBorderWidth);
        contentStream.moveTo(x + CELL_SIZE, y);
        contentStream.lineTo(x + CELL_SIZE, y - CELL_SIZE);
        contentStream.stroke();
    }
}
