public class GameLogger
{
    public static void printBoard(final SquareState[][] board,final int boardSize)
    {
        final int CELL_WIDTH = 3; // Width of each cell’s content area
        final String CELL_FORMAT = "%" + CELL_WIDTH + "s|";
        final String ROW_LABEL_FORMAT = "%2d |"; // Width of each row’s index label
        final String COLUMN_HEADER_FORMAT = "  %-" + (CELL_WIDTH-1) + "d";

        // Build the line eparator dynamically based on cell width and board size
        final int separatorLength = (CELL_WIDTH * boardSize) + (boardSize + 1);
        final String separator = "-".repeat(separatorLength);

        // --- COLUMN HEADERS ---
        StringBuilder colHeaders = new StringBuilder();
        for (int col = 0; col < boardSize; col++)
            colHeaders.append(String.format(COLUMN_HEADER_FORMAT, col));
        // ---                ---

        System.out.println(); // Newline before printing board
        System.out.println("   " + colHeaders);// Printing for column headers
        System.out.println("   " + separator); //Printing the separator with offset at beginning to align with header

        // --- BOARD ROWS ---
        for(int row = 0; row < boardSize; row++)
        {
            StringBuilder rowBuilder = new StringBuilder(String.format(ROW_LABEL_FORMAT, row));

            for (int col = 0; col < boardSize; col++)
            {
                String cellContent = switch(board[row][col])
                {
                    case SquareState.EMPTY -> "   ";
                    case SquareState.WHITE -> " W ";
                    case SquareState.BLACK -> " B ";
                };

                rowBuilder.append(String.format(CELL_FORMAT, cellContent));
            }

            System.out.println(rowBuilder);
            System.out.println("   " + separator);
        }
    }
}
