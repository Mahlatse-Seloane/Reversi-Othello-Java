public class MoveInspector
{
    private MoveInspector(){}

    /** Counts how many opponent pieces can be flipped in a given direction starting from the given cell.
     *
     * @param board The current board state
     * @param row Starting row
     * @param col Starting column
     * @param dir The given direction
     * @param curPToken The token of the player making a move
     * @return Number of pieces flippable in the given direction
     */
    public static int countFlipsInDirection(final SquareState[][] board, final SquareState curPToken, int row, int col, final int dir)
    {
        return switch (dir)
        {
            case 1 -> countFlipsAlongDirection(board, curPToken, row, col, -1, 0);
            case 2 -> countFlipsAlongDirection(board, curPToken, row, col, -1, 1);
            case 3 -> countFlipsAlongDirection(board, curPToken, row, col, 0, 1);
            case 4 -> countFlipsAlongDirection(board, curPToken, row, col, 1, 1);
            case 5 -> countFlipsAlongDirection(board, curPToken, row, col, 1, 0);
            case 6 -> countFlipsAlongDirection(board, curPToken, row, col, 1, -1);
            case 7 -> countFlipsAlongDirection(board, curPToken, row, col, 0, -1);
            case 8 -> countFlipsAlongDirection(board, curPToken, row, col, -1, -1);
            default -> throw new IllegalStateException("Unexpected value: " + dir);
        };
    }

    private static int countFlipsAlongDirection(final SquareState[][] board, final SquareState curPToken, int row, int col, int dRow, int dCol)
    {
        BoardValidator.validateBoard(board);
        BoardValidator.validateBounds(board.length, row, col);

        return countFlippableTokens(board, curPToken, row, col, dRow, dCol);
    }

    private static int countFlippableTokens(final SquareState[][] board,final SquareState curPToken,int row,int col,final int dRow,final int dCol)
    {
        final int boardSize = board.length;
        final int maxStep = calcMaxSteps(boardSize, row, col, dRow, dCol);

        if (maxStep < 2)
            return 0;

        final SquareState cell = board[row + dRow][col + dCol];
        if (cell == curPToken || cell == SquareState.EMPTY)
            return 0;

        return noOfPossibleFlips(board, curPToken, maxStep, row, col, dRow, dCol);
    }

    private static int calcMaxSteps(final int boardSize, int row, int col, final int dRow, final int dCol)
    {
        final int maxVerticalSteps = (dRow <= 0) ? row : ((boardSize - 1) - row);
        final int maxHorizontalSteps = (dCol <= 0) ? col : ((boardSize - 1) - col);

        int maxSteps;
        final boolean isStraight = (dRow == 0 || dCol == 0);
        if (isStraight)
            maxSteps = (dRow == 0) ? maxHorizontalSteps : maxVerticalSteps;
        else
            maxSteps = Math.min(maxHorizontalSteps,maxVerticalSteps);

        return maxSteps;
    }

    private static int noOfPossibleFlips(final SquareState[][] board, final SquareState curPToken, final int maxSteps, int row, int col, final int dRow, final int dCol)
    {
        for (int i = 0; i < maxSteps; i++)
        {
            row += dRow;
            col += dCol;
            final SquareState cell = board[row][col];

            if (cell == curPToken)
                return i; //return value is number of opponent tokens to be flipped
            else if (cell == SquareState.EMPTY)
                return 0;
        }

        return 0;
    }
}
