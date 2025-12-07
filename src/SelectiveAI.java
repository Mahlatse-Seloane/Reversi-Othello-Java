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

            possibleCaptures[i][2] = MoveInspector.countUpFlips(board, getPlayerToken(), row, col); //Up
            possibleCaptures[i][3] = MoveInspector.countDiagonalUpRightFlips(board, getPlayerToken(), row, col); // Up-Right
            possibleCaptures[i][4] = MoveInspector.countRightFlips(board, getPlayerToken(), row, col); // Right
            possibleCaptures[i][5] = MoveInspector.countDiagonalDownRightFlips(board, getPlayerToken(), row, col); // Down-Right
            possibleCaptures[i][6] = MoveInspector.countDownFlips(board, getPlayerToken(), row, col); //Down
            possibleCaptures[i][7] = MoveInspector.countDiagonalDownLeftFlips(board, getPlayerToken(), row, col); // Down-Left
            possibleCaptures[i][8] = MoveInspector.countLeftFlips(board, getPlayerToken(), row, col); // Left
            possibleCaptures[i][9] = MoveInspector.countDiagonalUpLeftFlips(board, getPlayerToken(), row, col); // Up-Left
        }
    }
}
