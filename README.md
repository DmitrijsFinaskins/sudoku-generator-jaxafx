# Sudoku Generator JavaFX

A JavaFX application for generating, solving, and exporting Sudoku puzzles.

## Overview

This application provides a user-friendly interface for working with Sudoku puzzles. It features:

- Random Sudoku puzzle generation
- Interactive puzzle solving
- Visual grid with proper Sudoku formatting (3x3 boxes)
- Validation of user solutions

## Screenshots

*Screenshots will be available in the final release.*

## Features

### Generator Tab
- Generate random Sudoku puzzles with varying difficulty levels (Easy, Medium, Hard)
- Reset the puzzle to start over
- Check your solution against the rules of Sudoku
- Customize colors for user-entered and generated digits
- Visual feedback for correct/incorrect solutions

### Solver Tab
- Solve puzzles created in the Generator tab
- Get solutions using the built-in solver algorithm
- Color-coded feedback showing correct and incorrect entries
- Displays the solution for the current puzzle
- Highlights user entries from the Generator tab

### Export Tab
- Export puzzles to PDF format
- Include solutions in the exported PDF (optional)
- Color-coded digits for better readability
- Professional formatting with proper Sudoku grid styling

## Requirements

- Java 21
- JavaFX 17
- Maven 3.6 or higher
- Apache PDFBox 3.0.4 (automatically managed by Maven)

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
6. Customize colors for user-entered and generated digits using the color pickers

### Solving a Puzzle
1. First generate a puzzle in the "Generator" tab and optionally enter some digits
2. Navigate to the "Solver" tab (the puzzle from the Generator tab will be automatically copied)
3. Click the "Solve" button to find the solution
4. Your entries from the Generator tab will be evaluated:
   - Correct entries will be highlighted in green
   - Incorrect entries will be replaced with correct values and highlighted in red
5. Empty cells will be filled with solution values highlighted in red

### Exporting a Puzzle
1. Navigate to the "Export" tab
2. Choose whether to include the solution in the exported PDF
3. Enter a filename for the PDF
4. Click the "Export to PDF" button
5. Select a location to save the PDF file
6. The exported PDF will include the puzzle and optionally its solution

## Technical Details

### Architecture
The application is built using:
- JavaFX for the user interface
- Modular Java structure
- Maven for dependency management and building

### Key Components
- `SudokuApp.java`: Main application class that handles UI layout and tab management
- `SudokuController.java`: Controller class that manages the core application logic and state
- `SudokuGridUI.java`: UI helper class for creating and managing Sudoku grid displays
- `SudokuCell.java`: Class for creating and styling individual Sudoku cells
- `BordersHelper.java`: Utility class for creating the visual 3x3 box borders of the Sudoku grid
- `SolveSudokuHelper.java`: Implements the backtracking algorithm for solving Sudoku puzzles
- `PdfExportService.java`: Service for exporting Sudoku puzzles to PDF format
- `SudokuValidator.java`: Validates Sudoku puzzles according to the rules
- `SudokuGenerator.java`: Generates random Sudoku puzzles with unique solutions
- `SolutionCounter.java`: Utility to count the number of possible solutions for a puzzle

## Development

### Building from Source
```
mvn clean install
```

### Running Tests
```
mvn test
```

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
