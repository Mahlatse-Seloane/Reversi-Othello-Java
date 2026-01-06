import java.util.ArrayList;

public class BoardRender
{
    private static final int CELL_WIDTH = 3; // Width of each cell’s content area
    private static final String CELL_FORMAT = "%" + CELL_WIDTH + "s|";
    private static final String ROW_LABEL_FORMAT = "%2d |"; // Width of each row’s index label

    private BoardRender(){}

    public static void printBoard(final SquareState[][] board, final BoardRenderContext context)
    {
        BoardValidator.validateBoard(board);

        ArrayList<Move> validMoves = new ArrayList<>(context.getValidMoves());
        ArrayList<Move> flippedTokens = new ArrayList<>(context.getFlippedTokens());
        Move chosenMove = context.getChosenMove();

        final int boardSize = board.length;
        final int maxValidMoves = (!validMoves.isEmpty()) ? validMoves.size() : 0;
        String padding = "   ";

        // --- COLUMN HEADERS ---
        final String colHeaders = buildColumnHeaders(boardSize);
        final String separator = buildSeparator(boardSize);

        System.out.println(padding + colHeaders);// Printing for column headers
        System.out.println(padding + separator); //Printing the separator with offset at beginning to align with header

        // --- BOARD ROWS ---
        for (int row = 0; row < boardSize; row++)
        {
            StringBuilder rowBuilder = new StringBuilder(String.format(ROW_LABEL_FORMAT, row));

            for (int col = 0; col < boardSize; col++)
            {
                String cellContent = getCellContents(board, row, col);

                if (board[row][col] == SquareState.EMPTY)
                {
                    if(context.showValidMoves())
                        cellContent = highlightValidMoves(row, col, cellContent, validMoves, maxValidMoves);
                }
                else
                {
                    if (context.showChosenMove() && (chosenMove.row() == row && chosenMove.col() == col))
                        cellContent = ConsoleColours.BRIGHT_GREEN + cellContent + ConsoleColours.BRIGHT_WHITE;

                    if (context.showFlippedTokens())
                        cellContent = highlightFlippedTokens(row, col, cellContent, flippedTokens);
                }

                rowBuilder.append(String.format(CELL_FORMAT, " " + cellContent + " "));
            }

            System.out.println(rowBuilder);
            System.out.println(padding + separator);
        }

        System.out.println(ConsoleColours.BRIGHT_WHITE);
    }

    private static String getCellContents(final SquareState[][] board, final int row, final int col)
    {
        return switch (board[row][col])
        {
            case SquareState.EMPTY -> SquareState.EMPTY.getSymbol();
            case SquareState.WHITE -> SquareState.WHITE.getSymbol();
            case SquareState.BLACK -> SquareState.BLACK.getSymbol();
        };
    }

    private static String buildColumnHeaders(int boardSize)
    {
        // Build the line eparator dynamically based on cell width and board size
        final String COLUMN_HEADER_FORMAT = "  %-" + (CELL_WIDTH - 1) + "d";
        StringBuilder colsHeader = new StringBuilder();
        for (int col = 0; col < boardSize; col++)
            colsHeader.append(String.format(COLUMN_HEADER_FORMAT, col));

        return colsHeader.toString();
    }

    private static String buildSeparator(int boardSize)
    {
        final int separatorLength = (CELL_WIDTH * boardSize) + (boardSize + 1);
        return "-".repeat(separatorLength);
    }

    private static String highlightValidMoves(final int row, final int col, final String cellContent, final ArrayList<Move> validMoves, final int maxValidMoves)
    {
        if (validMoves != null && !validMoves.isEmpty())
        {
            for (Move move : validMoves)
            {
                if (move.row() == row && move.col() == col)
                {
                    validMoves.remove(move);
                    return ConsoleColours.BRIGHT_MAGENTA + (maxValidMoves - validMoves.size()) + ConsoleColours.BRIGHT_WHITE;
                }
            }
        }

        return cellContent;
    }

    private static String highlightFlippedTokens(final int row, final int col, String cellContent, ArrayList<Move> flippedTokens)
    {
        if (flippedTokens != null && !flippedTokens.isEmpty())
        {
            for (Move flip : flippedTokens)
            {
                if (flip.row() == row && flip.col() == col)
                {
                    flippedTokens.remove(flip);
                    return ConsoleColours.BRIGHT_RED + cellContent + ConsoleColours.BRIGHT_WHITE;
                }
            }
        }

        return cellContent;
    }
}