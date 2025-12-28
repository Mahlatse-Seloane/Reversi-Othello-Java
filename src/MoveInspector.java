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
    public static int countFlipsInDirection(final SquareState[][] board, final SquareState curPToken, int row, int col, final Direction dir)
    {
        return switch (dir)
        {
            case Direction.UP -> countFlipsAlongDirection(board, curPToken, row, col, Direction.UP);
            case Direction.DIAGONAL_UP_RIGHT -> countFlipsAlongDirection(board, curPToken, row, col, Direction.DIAGONAL_UP_RIGHT);
            case Direction.RIGHT -> countFlipsAlongDirection(board, curPToken, row, col, Direction.RIGHT);
            case Direction.DIAGONAL_DOWN_RIGHT -> countFlipsAlongDirection(board, curPToken, row, col, Direction.DIAGONAL_DOWN_RIGHT);
            case Direction.DOWN -> countFlipsAlongDirection(board, curPToken, row, col, Direction.DOWN);
            case Direction.DIAGONAL_DOWN_LEFT -> countFlipsAlongDirection(board, curPToken, row, col, Direction.DIAGONAL_DOWN_LEFT);
            case Direction.LEFT -> countFlipsAlongDirection(board, curPToken, row, col, Direction.LEFT);
            case Direction.DIAGONAL_UP_LEFT -> countFlipsAlongDirection(board, curPToken, row, col, Direction.DIAGONAL_UP_LEFT);
        };
    }

    private static int countFlipsAlongDirection(final SquareState[][] board, final SquareState curPToken, int row, int col, final Direction dir)
    {
        BoardValidator.validateBoard(board);
        BoardValidator.validateBounds(board.length, row, col);

        return countFlippableTokens(board, curPToken, row, col, dir);
    }

    private static int countFlippableTokens(final SquareState[][] board,final SquareState curPToken,int row,int col,final Direction dir)
    {
        final int boardSize = board.length;
        final int dRow = dir.getRowDelta(), dCol = dir.getColDelta();
        final int maxStep = calcMaxSteps(boardSize, row, col, dRow, dCol);

        if (maxStep < 2)
            return 0;

        final SquareState cell = board[row + dRow][col + dCol];
        if (cell == curPToken || cell == SquareState.EMPTY)
            return 0;

        return noOfPossibleFlips(board, curPToken, maxStep, row, col, dir);
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

    private static int noOfPossibleFlips(final SquareState[][] board, final SquareState curPToken, final int maxSteps, int row, int col, final Direction dir)
    {
        for (int i = 0; i < maxSteps; i++)
        {
            row += dir.getRowDelta();
            col += dir.getColDelta();
            final SquareState cell = board[row][col];

            if (cell == curPToken)
                return i; //return value is number of opponent tokens to be flipped
            else if (cell == SquareState.EMPTY)
                return 0;
        }

        return 0;
    }
}
