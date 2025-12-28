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
                    for(int i = 0; i < 8; i++)
                        noOfFlips += MoveInspector.countFlipsInDirection(board, curPToken, row, col, i);

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

        for(int i = 0; i < 8; i++)
        {
            int row = chosenMove.row(), col = chosenMove.col();
            int dRow = dir[i][0], dCol = dir[i][1];

            int maxSteps = MoveInspector.countFlipsInDirection(board, curPToken, row, col, i);

            for (int j = 0; j < maxSteps; j++)
            {
                row += dRow;
                col += dCol;
                capturedTokens.add(new Move(row,col));
            }
        }

        return capturedTokens.toArray(Move[]::new);
    }
}
