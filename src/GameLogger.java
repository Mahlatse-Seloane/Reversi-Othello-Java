import java.util.ArrayList;

public class GameLogger
{
    private static final int CELL_WIDTH = 3; // Width of each cell’s content area
    private static final String CELL_FORMAT = "%" + CELL_WIDTH + "s|";
    private static final String ROW_LABEL_FORMAT = "%2d |"; // Width of each row’s index label

    private GameLogger() {}

    public static void logMoves(final Move chosenMove, final ArrayList<Move> flippedTokens, final String playerID)
    {
        if (chosenMove == null)
            throw new IllegalArgumentException("Chosen move cannot be null");

        if (flippedTokens == null || flippedTokens.isEmpty())
            throw new IllegalArgumentException("Flipped token coordinates cannot be null or empty");

        if (playerID == null || playerID.isEmpty())
            throw new IllegalArgumentException("Player ID cannot be null or empty");

        StringBuilder playerTurn = new StringBuilder("r" + chosenMove.row() + "c" + chosenMove.col() + " " + playerID + " ,");
        final int noOfMoves = flippedTokens.size();

        for (int i = 0; i < noOfMoves; i++)
        {
            Move flip = flippedTokens.removeFirst();
            playerTurn.append(" r").append(flip.row()).append("c").append(flip.col());
        }

        System.out.println(playerTurn);
    }

    public static void logGameResults(final EndResults results)
    {
        if (results == null)
            throw new IllegalArgumentException("Results cannot be null");

        System.out.println("alg1 = " + results.tokenCount().p1());
        System.out.println("alg2 = " + results.tokenCount().p2());
        System.out.println("win = " + results.winner());
        System.out.println("Reason: " + results.reason());
        System.out.println();
    }

    public static void printBoard(final SquareState[][] board, final int boardSize)
    {
        BoardValidator.validateBoard(board);

        // --- COLUMN HEADERS ---
        final String colHeaders = buildColumnHeaders(boardSize);
        final String separator = buildSeparator(boardSize);

        System.out.println(); // Newline before printing board
        System.out.println(" " + colHeaders);// Printing for column headers
        System.out.println(" " + separator); //Printing the separator with offset at beginning to align with header

        // --- BOARD ROWS ---
        for (int row = 0; row < boardSize; row++)
        {
            StringBuilder rowBuilder = new StringBuilder(String.format(ROW_LABEL_FORMAT, row));

            for (int col = 0; col < boardSize; col++)
            {
                String cellContent = cellContents(board, row, col);
                rowBuilder.append(String.format(CELL_FORMAT, cellContent));
            }

            System.out.println(rowBuilder);
            System.out.println(" " + separator);
        }
    }

    public static void printBoard(final SquareState[][] board, final int boardSize, final Move chosenMove, final ArrayList<Move> flippedTokens)
    {
        BoardValidator.validateBoard(board);

        // --- COLUMN HEADERS ---
        final String colHeaders = buildColumnHeaders(boardSize);
        final String separator = buildSeparator(boardSize);

        System.out.println(); // Newline before printing board
        System.out.println(" " + colHeaders);// Printing for column headers
        System.out.println(" " + separator); //Printing the separator with offset at beginning to align with header

        // --- BOARD ROWS ---
        for (int row = 0; row < boardSize; row++)
        {
            StringBuilder rowBuilder = new StringBuilder(String.format(ROW_LABEL_FORMAT, row));

            for (int col = 0; col < boardSize; col++)
            {
                String cellContent = cellContents(board, row, col);

                if (board[row][col] != SquareState.EMPTY)
                {
                    if (chosenMove != null && (chosenMove.row() == row && chosenMove.col() == col))
                        cellContent = "\u001B[32m" + cellContent + "\u001B[0m";

                    if (flippedTokens != null && !flippedTokens.isEmpty())
                    {
                        for (Move flip : flippedTokens)
                        {
                            if (flip.row() == row && flip.col() == col)
                            {
                                cellContent = "\u001B[31m" + cellContent + "\u001B[0m";
                                break;
                            }
                        }
                    }
                }

                rowBuilder.append(String.format(CELL_FORMAT, cellContent));
            }

            System.out.println(rowBuilder);
            System.out.println("   " + separator);
        }
    }

    private static String cellContents(final SquareState[][] board, final int row, final int col)
    {
        return switch (board[row][col])
        {
            case SquareState.EMPTY -> " ";
            case SquareState.WHITE -> " W ";
            case SquareState.BLACK -> " B ";
        };
    }

    private static String buildColumnHeaders(int boardSize)
    {
        // Build the line eparator dynamically based on cell width and board size
        final String COLUMN_HEADER_FORMAT = " %-" + (CELL_WIDTH - 1) + "d";
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
}