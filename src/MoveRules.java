import java.util.ArrayList;

public class MoveRules
{
    private static final int[][] dir = {{-1, 0},{-1, 1},{0, 1},{1, 1},{1, 0},{1, -1},{0, -1},{-1, -1}};

    private MoveRules() {}

    public static Move[] findValidMoves(final SquareState[][] board, final SquareState curPToken)
    {
        BoardValidator.validateBoard(board);

        final int boardSize = board.length;
        ArrayList<Move> validMoves = new ArrayList<>();

        for (int row = 0; row < boardSize; row++)
        {
            for (int col = 0; col < boardSize; col++)
            {
                BoardValidator.validateBounds(boardSize,row,col);
                if (board[row][col] == SquareState.EMPTY)
                {
                    int noOfFlips = 0;
                    for (Direction dir : Direction.values())
                        noOfFlips += MoveInspector.countFlipsInDirection(board, curPToken, row, col, dir);

                    if (noOfFlips > 0)
                        validMoves.add(new Move(row,col));
                }
            }
        }

        return validMoves.toArray(Move[]::new);
    }

    public static Move[] findCapturedTokens(final SquareState[][] board, final Move chosenMove, final SquareState curPToken)
    {
        if(chosenMove == null)
            return new Move[0];

        ArrayList<Move> capturedTokens = new ArrayList<>();

        for (Direction dir : Direction.values())
        {
            int row = chosenMove.row(), col = chosenMove.col();
            int maxSteps = MoveInspector.countFlipsInDirection(board, curPToken, row, col, dir);

            for (int j = 0; j < maxSteps; j++)
            {
                row += dir.getRowDelta();
                col += dir.getColDelta();
                capturedTokens.add(new Move(row,col));
            }
        }

        return capturedTokens.toArray(Move[]::new);
    }
}
