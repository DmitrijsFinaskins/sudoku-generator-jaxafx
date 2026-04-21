# Sudoku Generator JavaFX

A JavaFX application for generating, solving, and exporting Sudoku puzzles.

## Overview

This application provides a user-friendly interface for working with Sudoku puzzles. It features:

- Random Sudoku puzzle generation with guaranteed unique solutions
- Three difficulty levels (Easy, Medium, Hard)
- Interactive puzzle solving with live validation
- A dedicated solver tab that grades your attempt and reveals the solution
- PDF export of puzzles (with or without solutions) via Apache PDFBox
- Customizable digit colors for both user input and generated clues
- Visual grid with proper Sudoku formatting (3x3 box separators)

## Screenshots

*Screenshots will be available in the final release.*

## Features

### Generator Tab
- Generate random Sudoku puzzles with three difficulty levels:
  - Easy (40 clues)
  - Medium (30 clues)
  - Hard (25 clues)
- Puzzles are generated with a unique solution (verified via a solution counter)
- Reset button clears user-entered digits while preserving the generated clues
- Check button validates the current grid against Sudoku rules and, on failure, offers to switch to the Solver tab to view the solution
- Customize colors for user-entered and generated digits via color pickers (cells update live)
- Secure randomness: uses `SecureRandom.getInstanceStrong()` for puzzle generation

### Solver Tab
- Automatically mirrors the current puzzle (and any digits you've entered) from the Generator tab
- Solves puzzles using a backtracking algorithm (`SudokuSolver` / `SolveSudokuHelper`)
- Color-coded feedback on user entries:
  - Correct user entries are highlighted in green
  - Incorrect user entries are overwritten with the correct value and highlighted in red
  - Empty cells are filled in with the solution, highlighted in red
- Robust fallback: if user entries make the puzzle unsolvable, they are ignored and the original puzzle is solved instead

### Export Tab
- Export the current puzzle to a PDF file via a native save dialog
- Optionally include the fully solved grid in the exported PDF
- Customizable file name
- Uses the same digit colors configured in the Generator tab
- Live preview of the current puzzle inside the tab
- PDF generation is powered by Apache PDFBox with proper 3x3 box borders and a professional layout

## Requirements

- Java 21
- JavaFX 17.0.2
- Maven 3.6 or higher
- Apache PDFBox 3.0.4 (automatically managed by Maven)

All runtime dependencies beyond the JDK are managed by Maven. Test dependencies include JUnit 5, Mockito, TestFX, and OpenJFX Monocle (for headless UI tests).

## Installation

1. Clone the repository:
```
git clone https://github.com/DmitrijsFinaskins/sudoku-generator-jaxafx.git
```

2. Navigate to the project directory:
```
cd sudoku-generator-jaxafx
```

3. Build the project with Maven:
```
mvn clean package
```

## Running the Application

After building, you can run the application using:

```
mvn javafx:run
```

Or directly with Java:

```
java -jar target/sudoku-app-1.0.0.jar
```

## How to Use

### Generating a Puzzle
1. Open the application
2. Navigate to the "Generator" tab
3. Select a difficulty level (Easy, Medium, or Hard)
4. Click the "Generate" button to create a new puzzle
5. Start solving by entering numbers in the empty cells
6. Optionally customize colors for user-entered and generated digits using the color pickers
7. Click "Check" at any time to verify your solution, or "Reset" to clear your entries

### Solving a Puzzle
1. First generate a puzzle in the "Generator" tab and optionally enter some digits
2. Navigate to the "Solver" tab — the current puzzle (and any entries) is automatically copied over
3. Click the "Solve" button to find the solution
4. Your entries from the Generator tab will be evaluated:
   - Correct entries are highlighted in green
   - Incorrect entries are replaced with correct values and highlighted in red
   - Empty cells are filled with solution values highlighted in red
5. If your entries make the puzzle unsolvable, they are discarded automatically and the original puzzle is solved instead

### Exporting a Puzzle
1. Navigate to the "Export" tab (a live preview of the current puzzle is shown)
2. Choose whether to include the solution in the exported PDF
3. Enter a filename for the PDF
4. Click the "Export to PDF" button
5. Select a location in the save dialog
6. The exported PDF will include the puzzle and, if selected, its fully solved counterpart

## Technical Details

### Architecture
The application is built using:
- JavaFX 17 for the user interface (single-module app declared in `module-info.java` as `dev.finashkin`)
- A layered structure separating UI, controller, model, and utility concerns
- Maven for dependency management, building, and running (via the JavaFX Maven plugin)
- Apache PDFBox for PDF export

### Project Structure
```
src/main/java/dev/finashkin/
├── SudokuApp.java                 # JavaFX Application entry point; builds tabs and wires events
├── controller/
│   └── SudokuController.java      # Mediates between UI and model; holds grids, difficulty, and colors
├── model/
│   ├── SudokuGenerator.java       # Generates complete grids and removes cells to produce puzzles
│   ├── SudokuSolver.java          # Solves puzzles (delegates to SolveSudokuHelper)
│   └── SudokuValidator.java       # Checks whether a 9x9 grid satisfies Sudoku rules
├── ui/
│   ├── SudokuGridUI.java          # Builds Generator/Solver GridPanes and keeps them in sync
│   └── SudokuCell.java            # Creates and styles individual cells (text fields)
└── utils/
    ├── BordersHelper.java         # Draws the thicker borders around 3x3 boxes
    ├── PdfExportService.java      # Renders the puzzle (and optional solution) to a PDF via PDFBox
    ├── SolveSudokuHelper.java     # Backtracking solver implementation
    └── SolutionCounter.java       # Counts solutions to ensure generated puzzles are unique
```

### Key Components
- `SudokuApp`: JavaFX `Application` subclass; builds the `TabPane`, creates Generator/Solver/Export content, and wires button handlers, color pickers, and tab-change listeners
- `SudokuController`: Holds the generator grid, solver grid, user-entered-cell tracking, difficulty level, and digit colors; exposes operations to the UI
- `SudokuGenerator`: Populates a complete valid grid using a seeded pattern with shuffled digits, then strips cells while preserving a unique solution (falls back to best effort when full removal isn't possible)
- `SudokuSolver` / `SolveSudokuHelper`: Recursive backtracking solver; `SudokuSolver` also validates the input grid before solving and provides a deep copy helper
- `SudokuValidator`: Verifies a completed 9x9 grid against Sudoku rules (rows, columns, and 3x3 boxes)
- `SolutionCounter`: Used during puzzle generation to confirm that a candidate removal still leaves exactly one solution
- `SudokuGridUI` / `SudokuCell` / `BordersHelper`: Build and style the interactive grid, including the visual 3x3 box separators
- `PdfExportService`: Renders the puzzle (and optional solution) to PDF, honoring the user's digit colors

## Development

### Building from Source
```
mvn clean install
```

### Running Tests
```
mvn test
```

The test suite covers the model (generator, solver, validator, edge cases), controller, utilities, and the UI layer (via TestFX running headlessly with OpenJFX Monocle). Headless execution is preconfigured in `pom.xml` under the Surefire plugin.

### Code Coverage
A JaCoCo profile is available for coverage reports:

```
mvn -P jacoco-coverage test
```

The HTML report is written to `target/site/jacoco/index.html`.

### Generating Documentation
You can generate Javadoc documentation using the standard javadoc tool that comes with the JDK:

```
javadoc -d docs -sourcepath src/main/java -subpackages dev.finashkin
```

This will generate HTML documentation in the `docs` directory.

Alternatively, if you have Maven configured with the Javadoc plugin, you can use:

```
mvn javadoc:javadoc
```

The Maven-generated documentation will be available in the `target/site/apidocs` directory.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License.

```
MIT License

Copyright (c) 2023 Dmitry Finashkin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Acknowledgments

- Thanks to all contributors who have helped with the development of this application
