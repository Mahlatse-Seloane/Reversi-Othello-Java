public class BoardValidator
{
    private BoardValidator(){}

    public static void validateBoard(final SquareState[][] board)
    {
        if (board == null)
            throw new IllegalArgumentException("Board cannot be null");

        final int boardSize = board.length;
        for (SquareState[] row : board)
        {
            if (row == null)
                throw new IllegalArgumentException("Board rows cannot be null");

            if (row.length != boardSize)
                throw new IllegalArgumentException("Board must be square");
        }
    }

    public static void validateBounds(final int boardSize,int row,int col)
    {
        if (row < 0 || row >= boardSize)
            throw new IndexOutOfBoundsException("Row: " + row + " is out the bounds.");
        if (col < 0 || col >= boardSize)
            throw new IndexOutOfBoundsException("Col: " + col + " is out the bounds.");
    }
}
