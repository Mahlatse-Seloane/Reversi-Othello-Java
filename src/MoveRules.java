import java.util.ArrayList;

public class MoveRules
{
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
                    {
                        noOfFlips += switch (i)
                        {
                            case 0 -> MoveInspector.countUpFlips(board,curPToken,row,col);
                            case 1 -> MoveInspector.countDiagonalUpRightFlips(board,curPToken,row,col);
                            case 2 -> MoveInspector.countRightFlips(board,curPToken,row,col);
                            case 3 -> MoveInspector.countDiagonalDownRightFlips(board,curPToken,row,col);
                            case 4 -> MoveInspector.countDownFlips(board,curPToken,row,col);
                            case 5 -> MoveInspector.countDiagonalDownLeftFlips(board,curPToken,row,col);
                            case 6 -> MoveInspector.countLeftFlips(board,curPToken,row,col);
                            case 7 -> MoveInspector.countDiagonalUpLeftFlips(board,curPToken,row,col);
                            default -> throw new IllegalStateException("Unexpected value: " + i);
                        };
                    }

                    if (noOfFlips > 0)
                        validMoves.add(new Move(row,col));
                }
            }
        }

        return validMoves.toArray(Move[]::new);
    }

}
