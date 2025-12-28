public class MoveInspector
{
    private MoveInspector(){}

    /**
     * Counts how many opponent pieces can be flipped upward from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countUpFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, -1, 0);
    }

    /**
     * Counts how many opponent pieces can be flipped diagonally up right from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countDiagonalUpRightFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, -1, 1);
    }

    /**
     * Counts how many opponent pieces can be flipped right from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countRightFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, 0, 1);
    }

    /**
     * Counts how many opponent pieces can be flipped diagonally down right from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countDiagonalDownRightFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, 1, 1);
    }

    /**
     * Counts how many opponent pieces can be flipped downward from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countDownFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, 1, 0);
    }

    /**
     * Counts how many opponent pieces can be flipped diagonally down left from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countDiagonalDownLeftFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, 1, -1);
    }

    /**
     * Counts how many opponent pieces can be flipped left from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countLeftFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, 0, -1);
    }

    /**
     * Counts how many opponent pieces can be flipped diagonally up left from the given cell.
     *
     * @param board The current board state
     * @param curPToken The token of the player making a move
     * @param row Starting row
     * @param col Starting column
     * @return Number of pieces flippable in the given direction
     */
    public static int countDiagonalUpLeftFlips(final SquareState[][] board,final SquareState curPToken,int row,int col)
    {
        return countFlipsInDirection(board, curPToken, row, col, -1, -1);
    }

    private static int countFlipsInDirection(final SquareState[][] board, final SquareState curPToken, int row, int col, int dRow, int dCol)
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
