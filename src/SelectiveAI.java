import java.util.Arrays;

public class SelectiveAI extends PlayerType
{
    private int[][] possibleCaptures;

    public SelectiveAI()
    {
        setPlayerID("SelectiveAI");
    }

    @Override
    public int chooseMove(final SquareState[][] board,final  Move[] validMoves)
    {
        BoardValidator.validateBoard(board);
        if(validMoves == null || validMoves.length == 0)
            throw new IllegalArgumentException("Valid moves list cannot be null or empty");

        collectInformation(board, validMoves);
        return findMaxPossibleCaptures();
    }

    public int findMaxPossibleCaptures()
    {
        int maxPossibleCaptures = Arrays.stream(possibleCaptures[0]).sum();
        int index = 0;
        final int noOfValidMoves = possibleCaptures.length;

        for (int i = 1; i < noOfValidMoves; i++)
        {
            int noOfPossibleCaptures = Arrays.stream(possibleCaptures[i]).sum();
            if (noOfPossibleCaptures > maxPossibleCaptures)
            {
                index = i;
                maxPossibleCaptures = noOfPossibleCaptures;
            }
        }

        return index;
    }

    private void collectInformation(final SquareState[][] board, final Move[] validMoves)
    {
        possibleCaptures = new int[validMoves.length][10];

        int noOfValidMoves = validMoves.length;
        for (int i = 0; i < noOfValidMoves; i++)
        {
            int row = validMoves[i].row(), col = validMoves[i].col();
            possibleCaptures[i][0] = row;
            possibleCaptures[i][1] = col;

            for(int j = 1; j <= 8; j++)
                possibleCaptures[i][j+1] = MoveInspector.countFlipsInDirection(board, getPlayerToken(), row, col, j);
        }
    }
}
